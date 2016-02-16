//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:32 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.x837Controller;
import OpenDental.EmdeonITS.ITSReturn;
import OpenDental.EmdeonITS.ITSWS;
import OpenDental.EmdeonITSTest.ITSReturn;
import OpenDental.EmdeonITSTest.ITSWS;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.EnumClaimMedType;

public class EmdeonMedical   
{
    private static String emdeonITSUrlTest = "https://cert.its.emdeon.com/ITS/ITSWS.asmx";
    //test url
    private static String emdeonITSUrl = "https://its.emdeon.com/ITS/ITSWS.asmx";
    //production url
    /**
    * 
    */
    public EmdeonMedical() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed. If they failed, a rollback will happen automatically by deleting the previously created X12 file. The batchnum is supplied for the possible rollback.  Also used for mail retrieval.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum, EnumClaimMedType medType) throws Exception {
        String batchFile = "";
        try
        {
            if (!Directory.Exists(clearhouse.ExportPath))
            {
                throw new Exception("Clearinghouse export path is invalid.");
            }
             
            //We make sure to only send the X12 batch file for the current batch, so that if there is a failure, then we know
            //for sure that we need to reverse the batch. This will also help us avoid any exterraneous/old batch files in the
            //same directory which might be left if there is a permission issue when trying to delete the batch files after processing.
            batchFile = Path.Combine(clearhouse.ExportPath, "claims" + batchNum + ".txt");
            //byte[] fileBytes=File.ReadAllBytes(batchFile);//unused
            MemoryStream zipMemoryStream = new MemoryStream();
            ZipFile tempZip = new ZipFile();
            tempZip.AddFile(batchFile, "");
            tempZip.Save(zipMemoryStream);
            tempZip.Dispose();
            zipMemoryStream.Position = 0;
            byte[] zipFileBytes = zipMemoryStream.GetBuffer();
            String zipFileBytesBase64 = Convert.ToBase64String(zipFileBytes);
            zipMemoryStream.Dispose();
            if (StringSupport.equals(clearhouse.ISA15, "P"))
            {
                //production interface
                String messageType = "MCD";
                //medical
                if (medType == EnumClaimMedType.Institutional)
                {
                    messageType = "HCD";
                }
                else if (medType == EnumClaimMedType.Dental)
                {
                }
                  
                //messageType="DCD";//not used/tested yet, but planned for future.
                ITSWS itsws = new ITSWS();
                itsws.setUrl(emdeonITSUrl);
                ITSReturn response = itsws.PutFileExt(clearhouse.LoginID, clearhouse.Password, messageType, Path.GetFileName(batchFile), zipFileBytesBase64);
                if (response.getErrorCode() != 0)
                {
                    throw new Exception("Emdeon rejected all claims in the current batch file " + batchFile + ". Error number from Emdeon: " + response.getErrorCode() + ". Error message from Emdeon: " + response.getResponse());
                }
                 
            }
            else
            {
                //Batch submission successful.
                //test interface
                String messageType = "MCT";
                //medical
                if (medType == EnumClaimMedType.Institutional)
                {
                    messageType = "HCT";
                }
                else if (medType == EnumClaimMedType.Dental)
                {
                }
                  
                //messageType="DCT";//not used/tested yet, but planned for future.
                ITSWS itswsTest = new ITSWS();
                itswsTest.setUrl(emdeonITSUrlTest);
                ITSReturn responseTest = itswsTest.PutFileExt(clearhouse.LoginID, clearhouse.Password, messageType, Path.GetFileName(batchFile), zipFileBytesBase64);
                if (responseTest.getErrorCode() != 0)
                {
                    throw new Exception("Emdeon rejected all claims in the current batch file " + batchFile + ". Error number from Emdeon: " + responseTest.getErrorCode() + ". Error message from Emdeon: " + responseTest.getResponse());
                }
                 
            } 
        }
        catch (Exception e)
        {
            //Batch submission successful.
            MessageBox.Show(e.Message);
            x837Controller.rollback(clearhouse,batchNum);
            return false;
        }
        finally
        {
            try
            {
                if (!StringSupport.equals(batchFile, ""))
                {
                    File.Delete(batchFile);
                }
                 
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show("Failed to remove batch file " + batchFile + ". Probably due to a permission issue. Check folder permissions and manually delete.");
            }
        
        }
        return true;
    }

    public static boolean retrieve(Clearinghouse clearhouse) throws Exception {
        try
        {
            if (!Directory.Exists(clearhouse.ResponsePath))
            {
                throw new Exception("Clearinghouse response path is invalid.");
            }
             
            boolean reportsDownloaded = false;
            if (StringSupport.equals(clearhouse.ISA15, "P"))
            {
                //production interface
                String[] messageTypes = new String[]{ "MCD", "HCD" };
                for (int i = 0;i < messageTypes.Length;i++)
                {
                    //Medical
                    //Institutional
                    //"DCD"  //Dental. Planned for future.
                    ITSWS itsws = new ITSWS();
                    itsws.setUrl(emdeonITSUrl);
                    //Download the most up to date reports, but do not delete them from the server yet.
                    ITSReturn response = itsws.GetFile(clearhouse.LoginID, clearhouse.Password, messageTypes[i] + "G");
                    if (response.getErrorCode() == 0)
                    {
                        //Report retrieval successful.
                        String reportFileDataBase64 = response.getResponse();
                        byte[] reportFileDataBytes = Convert.FromBase64String(reportFileDataBase64);
                        String reportFilePath = CodeBase.ODFileUtils.createRandomFile(clearhouse.ResponsePath,".zip");
                        File.WriteAllBytes(reportFilePath, reportFileDataBytes);
                        reportsDownloaded = true;
                        //Now that the file has been saved, remove the report file from the Emdeon production server.
                        //If deleting the report fails, we don't care because that will simply mean that we download it again next time.
                        //Thus we don't need to check the status after this next call.
                        itsws.GetFile(clearhouse.LoginID, clearhouse.Password, messageTypes[i] + "D");
                    }
                    else if (response.getErrorCode() != 209)
                    {
                        throw new Exception("Failed to get reports. Error number from Emdeon: " + response.getErrorCode() + ". Error message from Emdeon: " + response.getResponse());
                    }
                      
                }
            }
            else
            {
                //Report retrieval failure, excluding the error that can be returned when the mailbox is empty.
                //test interface
                String[] messageTypes = new String[]{ "MCT", "HCT" };
                for (int i = 0;i < messageTypes.Length;i++)
                {
                    //Medical
                    //Institutional
                    //"DCT"  //Dental. Planned for future.
                    ITSWS itswsTest = new ITSWS();
                    itswsTest.setUrl(emdeonITSUrlTest);
                    //Download the most up to date reports, but do not delete them from the server yet.
                    ITSReturn responseTest = itswsTest.GetFile(clearhouse.LoginID, clearhouse.Password, messageTypes[i] + "G");
                    if (responseTest.getErrorCode() == 0)
                    {
                        //Report retrieval successful.
                        String reportFileDataBase64 = responseTest.getResponse();
                        byte[] reportFileDataBytes = Convert.FromBase64String(reportFileDataBase64);
                        String reportFilePath = CodeBase.ODFileUtils.createRandomFile(clearhouse.ResponsePath,".zip");
                        File.WriteAllBytes(reportFilePath, reportFileDataBytes);
                        reportsDownloaded = true;
                        //Now that the file has been saved, remove the report file from the Emdeon test server.
                        //If deleting the report fails, we don't care because that will simply mean that we download it again next time.
                        //Thus we don't need to check the status after this next call.
                        itswsTest.GetFile(clearhouse.LoginID, clearhouse.Password, messageTypes[i] + "D");
                    }
                    else if (responseTest.getErrorCode() != 209)
                    {
                        throw new Exception("Failed to get reports. Error number from Emdeon: " + responseTest.getErrorCode() + ". Error message from Emdeon: " + responseTest.getResponse());
                    }
                      
                }
            } 
            //Report retrieval failure, excluding the error that can be returned when the mailbox is empty.
            if (!reportsDownloaded)
            {
                MessageBox.Show("Report mailbox is empty.");
            }
             
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return false;
        }

        return true;
    }

}


