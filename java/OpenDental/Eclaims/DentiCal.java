//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;

import OpenDental.Lan;
import OpenDentBusiness.Clearinghouse;

/**
* aka Denti-Cal.
*/
public class DentiCal   
{
    private static String remoteHost = "ftp.delta.org";
    private static Clearinghouse clearinghouse = null;
    /**
    * 
    */
    public DentiCal() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed. Both sends and retrieves.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        clearinghouse = clearhouse;
        //Before this function is called, the X12 file for the current batch has already been generated in
        //the clearinghouse export folder. The export folder will also contain batch files which have failed
        //to upload from previous attempts and we must attempt to upload these older batch files again if
        //there are any.
        //Step 1: Retrieve reports regarding the existing pending claim statuses.
        //Step 2: Send new claims in a new batch.
        boolean success = true;
        //Connect to the Denti-Cal SFTP server.
        Session session = null;
        Channel channel = null;
        ChannelSftp ch = null;
        JSch jsch = new JSch();
        try
        {
            session = jsch.getSession(clearinghouse.LoginID, remoteHost);
            session.setPassword(clearinghouse.Password);
            Hashtable config = new Hashtable();
            config.Add("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ch = (ChannelSftp)channel;
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g("DentiCal","Connection Failed") + ": " + ex.Message);
            return false;
        }

        try
        {
            String homeDir = "/Home/" + clearhouse.LoginID + "/";
            //At this point we are connected to the Denti-Cal SFTP server.
            if (batchNum == 0)
            {
                //Retrieve reports.
                if (!Directory.Exists(clearhouse.ResponsePath))
                {
                    throw new Exception("Clearinghouse response path is invalid.");
                }
                 
                //Only retrieving reports so do not send new claims.
                String retrievePath = homeDir + "out/";
                Tamir.SharpSsh.java.util.Vector fileList = ch.ls(retrievePath);
                for (int i = 0;i < fileList.Count;i++)
                {
                    String listItem = fileList[i].ToString().Trim();
                    if (listItem[0] == 'd')
                    {
                        continue;
                    }
                     
                    //Skip directories and focus on files.
                    Match fileNameMatch = Regex.Match(listItem, ".*\\s+(.*)$");
                    String getFileName = fileNameMatch.Result("$1");
                    String getFilePath = retrievePath + getFileName;
                    String exportFilePath = CodeBase.ODFileUtils.combinePaths(clearhouse.ResponsePath,getFileName);
                    Tamir.SharpSsh.java.io.InputStream fileStream = null;
                    FileStream exportFileStream = null;
                    try
                    {
                        fileStream = ch.get(getFilePath);
                        exportFileStream = File.Open(exportFilePath, FileMode.Create, FileAccess.Write);
                        //Creates or overwrites.
                        byte[] dataBytes = new byte[4096];
                        int numBytes = fileStream.Read(dataBytes, 0, dataBytes.Length);
                        while (numBytes > 0)
                        {
                            exportFileStream.Write(dataBytes, 0, numBytes);
                            numBytes = fileStream.Read(dataBytes, 0, dataBytes.Length);
                        }
                    }
                    catch (Exception __dummyCatchVar0)
                    {
                        success = false;
                    }
                    finally
                    {
                        if (exportFileStream != null)
                        {
                            exportFileStream.Dispose();
                        }
                         
                        if (fileStream != null)
                        {
                            fileStream.Dispose();
                        }
                         
                    }
                    if (success)
                    {
                        try
                        {
                            //Removed the processed report from the Denti-Cal SFTP so it does not get processed again in the future.
                            ch.rm(getFilePath);
                        }
                        catch (Exception __dummyCatchVar1)
                        {
                        }
                    
                    }
                     
                }
            }
            else
            {
                //Send batch of claims.
                if (!Directory.Exists(clearhouse.ExportPath))
                {
                    throw new Exception("Clearinghouse export path is invalid.");
                }
                 
                String[] files = Directory.GetFiles(clearhouse.ExportPath);
                for (int i = 0;i < files.Length;i++)
                {
                    //First upload the batch file to a temporary file name. Denti-Cal does not process file names unless they start with the Login ID.
                    //Uploading to a temporary file and then renaming the file allows us to avoid partial file uploads if there is connection loss.
                    String tempRemoteFilePath = homeDir + "in/temp_" + Path.GetFileName(files[i]);
                    ch.put(files[i], tempRemoteFilePath);
                    //Denti-Cal requires the file name to start with the Login ID followed by a period and end with a .txt extension.
                    //The middle part of the file name can be anything.
                    String remoteFilePath = homeDir + "in/" + clearhouse.LoginID + "." + Path.GetFileName(files[i]);
                    ch.rename(tempRemoteFilePath, remoteFilePath);
                    File.Delete(files[i]);
                }
            } 
        }
        catch (Exception __dummyCatchVar2)
        {
            //Remove the processed file.
            success = false;
        }
        finally
        {
            //Disconnect from the Denti-Cal SFTP server.
            channel.disconnect();
            ch.disconnect();
            session.disconnect();
        }
        return success;
    }

}


