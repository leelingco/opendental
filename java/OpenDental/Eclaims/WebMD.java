//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.StringSupport;
import OpenDental.Eclaims.x837Controller;
import OpenDentBusiness.Clearinghouse;

/**
* Summary description for WebMD.
*/
public class WebMD   
{
    /**
    * 
    */
    public WebMD() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed. If they failed, a rollback will happen automatically by deleting the previously created X12 file. The batchnum is supplied for the possible rollback.  Also used for mail retrieval.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        String arguments = "";
        try
        {
            if (!Directory.Exists(clearhouse.ExportPath))
            {
                throw new Exception("Clearinghouse export path is invalid.");
            }
             
            if (!Directory.Exists(clearhouse.ResponsePath))
            {
                throw new Exception("Clearinghouse response path is invalid.");
            }
             
            if (!File.Exists(clearhouse.ClientProgram))
            {
                throw new Exception("Client program not installed properly.");
            }
             
            //upload claims path
            //Mail path
            //vendor number.
            //Client number. Assigned by us, and we have to coordinate for all other 'vendors' of Open Dental, because there is only one vendor number for OD for now.
            arguments = CodeBase.ODFileUtils.removeTrailingSeparators(clearhouse.ExportPath) + "\\" + "*.* " + CodeBase.ODFileUtils.removeTrailingSeparators(clearhouse.ResponsePath) + " " + "316 " + clearhouse.LoginID + " " + clearhouse.Password;
            //call the WebMD client program
            Process process = Process.Start(clearhouse.ClientProgram, arguments);
            process.EnableRaisingEvents = true;
            process.WaitForExit();
            //delete the uploaded claims
            String[] files = Directory.GetFiles(clearhouse.ExportPath);
            for (int i = 0;i < files.Length;i++)
            {
                //string t=files[i];
                File.Delete(files[i]);
            }
            //rename the downloaded mail files to end with txt
            files = Directory.GetFiles(clearhouse.ResponsePath);
            for (int i = 0;i < files.Length;i++)
            {
                //string t=files[i];
                if (!StringSupport.equals(Path.GetExtension(files[i]), ".txt"))
                {
                    File.Move(files[i], files[i] + ".txt");
                }
                 
            }
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
            //+"\r\n"+clearhouse.ClientProgram+" "+arguments);
            if (batchNum != 0)
            {
                x837Controller.rollback(clearhouse,batchNum);
            }
             
            return false;
        }

        return true;
    }

    /*  Use Launch instead
    		///<summary>Retrieves any waiting reports from this clearinghouse. Returns true if the communications were successful, and false if they failed.</summary>
    		public static bool Retrieve(Clearinghouse clearhouse){
    			bool retVal=true;
    			try{
    								
    			}
    			catch(Exception ex){
    				MessageBox.Show(ex.Message);
    				retVal=false;
    			}
    			return retVal;
    		}*/
    private static final String vendorId = "";
    //TODO: Get from Emdeon!
    private static final String testMode = "true";
    //TODO: Set to 'false' on release.
    private static final String emdeonServerUrl = "";
    //TODO: Get from Emdeon!
    private static void submitBatch(Clearinghouse clearhouse, int batchNum) throws Exception {
        String[] files = Directory.GetFiles(clearhouse.ExportPath);
        for (int i = 0;i < files.Length;i++)
        {
            ZipFile zip = null;
            try
            {
                zip = new ZipFile();
                zip.AddFile(files[i]);
                MemoryStream ms = new MemoryStream();
                zip.Save(ms);
                String fileTextZippedBase64 = Convert.ToBase64String(ms.GetBuffer());
                FileInfo fi = new FileInfo(files[i]);
                //TODO: Is this the right number to use?
                String claimXML = "<?xml version=\"1.0\" ?>" + "<claim_submission_api xmlns=\"Emdeon_claim_submission_api\" revision=\"001\">" + "<authentication>" + "<vendor_id>" + vendorId + "</vendor_id>" + "<user_id>" + clearhouse.LoginID + "</user_id>" + "<password>" + clearhouse.Password + "</password>" + "</authentication>" + "<transaction>" + "<trace_id>" + batchNum + "</trace_id>" + "<trx_type>submit_claim_file_request</trx_type>" + "<test_mode>" + testMode + "</test_mode>" + "<trx_data>" + "<claim_file>" + "<file_name>" + Path.GetFileName(files[i]) + "</file_name>" + "<file_format>DCDS2</file_format>" + "<file_size>" + fi.Length + "</file_size>" + "<file_compression>pkzip</file_compression>" + "<file_encoding>base64</file_encoding>" + "<file_data>" + fileTextZippedBase64 + "</file_data>" + "</claim_file>" + "</trx_data>" + "</transaction>" + "</claim_submission_api>";
                byte[] claimXMLbytes = Encoding.UTF8.GetBytes(claimXML);
                WebClient myWebClient = new WebClient();
                myWebClient.Headers.Add("Content-Type", "text/xml");
                byte[] responseBytes = myWebClient.UploadData(emdeonServerUrl, claimXMLbytes);
            }
            finally
            {
                if (zip != null)
                {
                    zip.Dispose();
                }
                 
            }
        }
    }

}


