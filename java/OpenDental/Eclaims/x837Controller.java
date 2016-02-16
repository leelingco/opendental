//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.MsgBox;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.X837_4010;
import OpenDentBusiness.X837_5010;

/**
* 
*/
public class x837Controller   
{
    /**
    * 
    */
    public x837Controller() throws Exception {
    }

    /**
    * Gets the filename for this batch. Used when saving or when rolling back.
    */
    private static String getFileName(Clearinghouse clearhouse, int batchNum) throws Exception {
        String saveFolder = clearhouse.ExportPath;
        if (!Directory.Exists(saveFolder))
        {
            MessageBox.Show(saveFolder + " not found.");
            return "";
        }
         
        if (clearhouse.CommBridge == EclaimsCommBridge.RECS)
        {
            if (File.Exists(CodeBase.ODFileUtils.combinePaths(saveFolder,"ecs.txt")))
            {
                MsgBox.show("FormClaimsSend","You must send your existing claims from the RECS program before you can create another batch.");
                return "";
            }
             
            return CodeBase.ODFileUtils.combinePaths(saveFolder,"ecs.txt");
        }
        else
        {
            return CodeBase.ODFileUtils.combinePaths(saveFolder,"claims" + batchNum.ToString() + ".txt");
        } 
    }

    //prevents overwriting an existing ecs.txt.
    /**
    * If file creation was successful but communications failed, then this deletes the X12 file.  This is not used in the Tesia bridge because of the unique filenaming.
    */
    public static void rollback(Clearinghouse clearhouse, int batchNum) throws Exception {
        if (clearhouse.CommBridge == EclaimsCommBridge.RECS)
        {
        }
        else
        {
            //A RECS rollback never deletes the file, because there is only one
            //This is a Windows extension, so we do not need to worry about Unix path separator characters.
            File.Delete(CodeBase.ODFileUtils.combinePaths(clearhouse.ExportPath,"claims" + batchNum.ToString() + ".txt"));
        } 
    }

    /**
    * Called from Eclaims and includes multiple claims.  Returns the string that was sent.  The string needs to be parsed to determine the transaction numbers used for each claim.
    */
    public static String sendBatch(List<ClaimSendQueueItem> queueItems, int batchNum, Clearinghouse clearhouse, EnumClaimMedType medType) throws Exception {
        //each batch is already guaranteed to be specific to one clearinghouse, one clinic, and one EnumClaimMedType
        //Clearinghouse clearhouse=ClearinghouseL.GetClearinghouse(queueItems[0].ClearinghouseNum);
        String saveFile = getFileName(clearhouse,batchNum);
        if (StringSupport.equals(saveFile, ""))
        {
            return "";
        }
         
        StreamWriter sw = new StreamWriter(saveFile, false, Encoding.ASCII);
        try
        {
            {
                if (clearhouse.Eformat == ElectronicClaimFormat.x837D_4010)
                {
                    X837_4010.GenerateMessageText(sw, clearhouse, batchNum, queueItems);
                }
                else
                {
                    //Any of the 3 kinds of 5010
                    X837_5010.GenerateMessageText(sw, clearhouse, batchNum, queueItems, medType);
                } 
            }
        }
        finally
        {
            if (sw != null)
                Disposable.mkDisposable(sw).dispose();
             
        }
        if (clearhouse.CommBridge == EclaimsCommBridge.PostnTrack)
        {
            //need to clear out all CRLF from entire file
            String strFile = "";
            StreamReader sr = new StreamReader(saveFile, Encoding.ASCII);
            try
            {
                {
                    strFile = sr.ReadToEnd();
                }
            }
            finally
            {
                if (sr != null)
                    Disposable.mkDisposable(sr).dispose();
                 
            }
            strFile = strFile.Replace("\r", "");
            strFile = strFile.Replace("\n", "");
            StreamWriter sw = new StreamWriter(saveFile, false, Encoding.ASCII);
            try
            {
                {
                    sw.Write(strFile);
                }
            }
            finally
            {
                if (sw != null)
                    Disposable.mkDisposable(sw).dispose();
                 
            }
        }
         
        copyToArchive(saveFile);
        return File.ReadAllText(saveFile);
    }

    /**
    * Copies the given file to an archive directory within the same directory as the file.
    */
    private static void copyToArchive(String fileName) throws Exception {
        String direct = Path.GetDirectoryName(fileName);
        String fileOnly = Path.GetFileName(fileName);
        String archiveDir = CodeBase.ODFileUtils.combinePaths(direct,"archive");
        if (!Directory.Exists(archiveDir))
        {
            Directory.CreateDirectory(archiveDir);
        }
         
        File.Copy(fileName, CodeBase.ODFileUtils.combinePaths(archiveDir,fileOnly), true);
    }

}


