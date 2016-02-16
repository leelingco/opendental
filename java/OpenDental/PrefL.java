//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.ClassConvertDatabase;
import OpenDental.FormUpdate;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDentBusiness.Cache;
import OpenDentBusiness.DatabaseMaintenance;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.DocumentMisc;
import OpenDentBusiness.DocumentMiscs;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;

public class PrefL   
{
    /**
    * This ONLY runs when first opening the program.  It returns true if either no conversion is necessary, or if conversion was successful.  False for other situations like corrupt db, trying to convert to older version, etc.  Silent mode is ONLY used for internal tools, NEVER with the main program.
    */
    public static boolean convertDB(boolean silent, String toVersion) throws Exception {
        ClassConvertDatabase ClassConvertDatabase2 = new ClassConvertDatabase();
        String pref = PrefC.getString(PrefName.DataBaseVersion);
        //(Pref)PrefC.HList["DataBaseVersion"];
        //Debug.WriteLine(pref.PrefName+","+pref.ValueString);
        if (ClassConvertDatabase2.convert(pref,toVersion,silent))
        {
            return true;
        }
        else
        {
            //((Pref)PrefC.HList["DataBaseVersion"]).ValueString)) {
            Application.Exit();
            return false;
        } 
    }

    /**
    * This ONLY runs when first opening the program.  It returns true if either no conversion is necessary, or if conversion was successful.  False for other situations like corrupt db, trying to convert to older version, etc.
    */
    public static boolean convertDB() throws Exception {
        return ConvertDB(false, Application.ProductVersion);
    }

    public static boolean copyFromHereToUpdateFiles(Version versionCurrent) throws Exception {
        String folderUpdate = "";
        if (PrefC.getAtoZfolderUsed())
        {
            folderUpdate = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"UpdateFiles");
        }
        else
        {
            folderUpdate = CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), "UpdateFiles");
        } 
        if (Directory.Exists(folderUpdate))
        {
            try
            {
                Directory.Delete(folderUpdate, true);
            }
            catch (Exception __dummyCatchVar0)
            {
                MessageBox.Show(Lan.g("Prefs","Please delete this folder and then re-open the program: ") + folderUpdate);
                return false;
            }

            //wait a bit so that CreateDirectory won't malfunction.
            DateTime now = DateTime.Now;
            while (Directory.Exists(folderUpdate) && DateTime.Now < now.AddSeconds(10))
            {
                //up to 10 seconds
                Application.DoEvents();
            }
            if (Directory.Exists(folderUpdate))
            {
                MessageBox.Show(Lan.g("Prefs","Please delete this folder and then re-open the program: ") + folderUpdate);
                return false;
            }
             
        }
         
        Directory.CreateDirectory(folderUpdate);
        DirectoryInfo dirInfo = new DirectoryInfo(Application.StartupPath);
        FileInfo[] appfiles = dirInfo.GetFiles();
        for (int i = 0;i < appfiles.Length;i++)
        {
            if (StringSupport.equals(appfiles[i].Name, "FreeDentalConfig.xml"))
            {
                continue;
            }
             
            //skip this one.
            if (StringSupport.equals(appfiles[i].Name, "OpenDentalServerConfig.xml"))
            {
                continue;
            }
             
            //skip also
            if (appfiles[i].Name.StartsWith("openlog"))
            {
                continue;
            }
             
            //these can be big and are irrelevant
            if (appfiles[i].Name.Contains("__"))
            {
                continue;
            }
             
            //double underscore
            //So that plugin dlls can purposely skip the file copy.
            //include UpdateFileCopier
            File.Copy(appfiles[i].FullName, CodeBase.ODFileUtils.CombinePaths(folderUpdate, appfiles[i].Name));
        }
        //Create a simple manifest file so that we know what version the files are for.
        File.WriteAllText(CodeBase.ODFileUtils.combinePaths(folderUpdate,"Manifest.txt"), versionCurrent.ToString(3));
        if (PrefC.getAtoZfolderUsed())
        {
        }
        else
        {
            //nothing more to do
            //zip and save to db
            ZipFile zipFile = new ZipFile();
            zipFile.AddDirectory(folderUpdate);
            MemoryStream memStream = new MemoryStream();
            zipFile.Save(memStream);
            zipFile.Dispose();
            memStream.Position = 0;
            byte[] zipFileBytes = memStream.GetBuffer();
            String zipFileBytesBase64 = Convert.ToBase64String(zipFileBytes);
            memStream.Dispose();
            int length = zipFileBytesBase64.Length;
            DocumentMiscs.setUpdateFilesZip(zipFileBytesBase64);
        } 
        return true;
    }

    /**
    * Called in two places.  Once from FormOpenDental.PrefsStartup, and also from FormBackups after a restore.
    */
    public static boolean checkProgramVersion() throws Exception {
        if (PrefC.getBool(PrefName.UpdateWindowShowsClassicView))
        {
            return checkProgramVersionClassic();
        }
         
        Version storedVersion = new Version(PrefC.getString(PrefName.ProgramVersion));
        Version currentVersion = new Version(Application.ProductVersion);
        String database = "";
        //string command="";
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
        {
            database = MiscData.getCurrentDatabase();
        }
         
        if (storedVersion < currentVersion)
        {
            //There are two different situations where this might happen.
            if (StringSupport.equals(PrefC.getString(PrefName.UpdateInProgressOnComputerName), ""))
            {
                //1. Just performed an update from this workstation on another database.
                //This is very common for admins when viewing slighly older databases.
                //There should be no annoying behavior here.  So do nothing.
                //Excluding this in debug allows us to view slightly older databases without accidentally altering them.
                Prefs.UpdateString(PrefName.ProgramVersion, currentVersion.ToString());
                Cache.refresh(InvalidType.Prefs);
                return true;
            }
             
            //and 2a. Just performed an update from this workstation on this database.
            //or 2b. Just performed an update from this workstation for multiple databases.
            //In both 2a and 2b, we already downloaded Setup file to correct location for this db, so skip 1 above.
            //This computer just performed an update, but none of the other computers has updated yet.
            //So attempt to stash all files that are in the Application directory.
            if (!copyFromHereToUpdateFiles(currentVersion))
            {
                Application.Exit();
                return false;
            }
             
            Prefs.UpdateString(PrefName.ProgramVersion, currentVersion.ToString());
            Prefs.updateString(PrefName.UpdateInProgressOnComputerName,"");
            //now, other workstations will be allowed to update.
            Cache.refresh(InvalidType.Prefs);
        }
         
        if (storedVersion > currentVersion)
        {
            //This is the update sequence for both a direct workstation, and for a ClientWeb workstation.
            String folderUpdate = "";
            if (PrefC.getAtoZfolderUsed())
            {
                folderUpdate = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"UpdateFiles");
            }
            else
            {
                //images in db
                folderUpdate = CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), "UpdateFiles");
                if (Directory.Exists(folderUpdate))
                {
                    Directory.Delete(folderUpdate, true);
                }
                 
                DocumentMisc docmisc = DocumentMiscs.getUpdateFilesZip();
                if (docmisc != null)
                {
                    byte[] rawBytes = Convert.FromBase64String(docmisc.RawBase64);
                    ZipFile unzipped = ZipFile.Read(rawBytes);
                    try
                    {
                        {
                            unzipped.ExtractAll(folderUpdate);
                        }
                    }
                    finally
                    {
                        if (unzipped != null)
                            Disposable.mkDisposable(unzipped).dispose();
                         
                    }
                }
                 
            } 
            //look at the manifest to see if it's the version we need
            String manifestVersion = "";
            try
            {
                manifestVersion = File.ReadAllText(CodeBase.ODFileUtils.combinePaths(folderUpdate,"Manifest.txt"));
            }
            catch (Exception __dummyCatchVar1)
            {
            }

            //fail silently
            if (!StringSupport.equals(manifestVersion, storedVersion.ToString(3)))
            {
                //manifest version is wrong
                //No point trying the Setup.exe because that's probably wrong too.
                //Just go straight to downloading and running the Setup.exe.
                String manpath = CodeBase.ODFileUtils.combinePaths(folderUpdate,"Manifest.txt");
                if (MessageBox.Show(Lan.g("Prefs","The expected version information was not found in this file: ") + manpath + ".  " + Lan.g("Prefs","There is probably a permission issue on that folder which should be fixed. ") + "\r\n\r\n" + Lan.g("Prefs","The suggested solution is to return to the computer where the update was just run.  Go to Help | Update | Setup, and click the Recopy button.") + "\r\n\r\n" + Lan.g("Prefs","If, instead, you click OK in this window, then a fresh Setup file will be downloaded and run."), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
                {
                    //they don't want to download again.
                    Application.Exit();
                    return false;
                }
                 
                downloadAndRunSetup(storedVersion,currentVersion);
                Application.Exit();
                return false;
            }
             
            //manifest version matches
            if (MessageBox.Show(Lan.g("Prefs","Files will now be copied.") + "\r\n" + Lan.g("Prefs","Workstation version will be updated from ") + currentVersion.ToString(3) + Lan.g("Prefs"," to ") + storedVersion.ToString(3), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                //they don't want to update for some reason.
                Application.Exit();
                return false;
            }
             
            String tempDir = Path.GetTempPath();
            //copy UpdateFileCopier.exe to the temp directory
            //source
            File.Copy(CodeBase.ODFileUtils.combinePaths(folderUpdate,"UpdateFileCopier.exe"), CodeBase.ODFileUtils.combinePaths(tempDir,"UpdateFileCopier.exe"), true);
            //dest
            //overwrite
            //wait a moment to make sure the file was copied
            Thread.Sleep(500);
            //launch UpdateFileCopier to copy all files to here.
            int processId = Process.GetCurrentProcess().Id;
            String appDir = Application.StartupPath;
            String startFileName = CodeBase.ODFileUtils.combinePaths(tempDir,"UpdateFileCopier.exe");
            //pass the source directory to the file copier.
            //and the processId of Open Dental.
            String arguments = "\"" + folderUpdate + "\"" + " " + processId.ToString() + " \"" + appDir + "\"";
            //and the directory where OD is running
            Process.Start(startFileName, arguments);
            Application.Exit();
            return false;
        }
         
        return true;
    }

    //always exits, whether launch of setup worked or not
    /**
    * If AtoZ.manifest was wrong, or if user is not using AtoZ, then just download again.  Will use dir selected by user.  If an appropriate download is not available, it will fail and inform user.
    */
    private static void downloadAndRunSetup(Version storedVersion, Version currentVersion) throws Exception {
        String patchName = "Setup.exe";
        String updateUri = PrefC.getString(PrefName.UpdateWebsitePath);
        String updateCode = PrefC.getString(PrefName.UpdateCode);
        String updateInfoMajor = "";
        String updateInfoMinor = "";
        RefSupport<String> refVar___0 = new RefSupport<String>();
        RefSupport<String> refVar___1 = new RefSupport<String>();
        boolean boolVar___0 = !FormUpdate.shouldDownloadUpdate(updateUri,updateCode,refVar___0,refVar___1);
        updateInfoMajor = refVar___0.getValue();
        updateInfoMinor = refVar___1.getValue();
        if (boolVar___0)
        {
            return ;
        }
         
        if (MessageBox.Show(Lan.g("Prefs","Setup file will now be downloaded.") + "\r\n" + Lan.g("Prefs","Workstation version will be updated from ") + currentVersion.ToString(3) + Lan.g("Prefs"," to ") + storedVersion.ToString(3), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        //they don't want to update for some reason.
        FolderBrowserDialog dlg = new FolderBrowserDialog();
        dlg.SelectedPath = ImageStore.getPreferredAtoZpath();
        dlg.Description = Lan.g("Prefs","Setup.exe will be downloaded to the folder you select below");
        if (dlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        //app will exit
        String tempFile = CodeBase.ODFileUtils.CombinePaths(dlg.SelectedPath, patchName);
        //ODFileUtils.CombinePaths(Path.GetTempPath(),patchName);
        //Source URI
        FormUpdate.downloadInstallPatchFromURI(updateUri + updateCode + "/" + patchName,tempFile,true,false,null);
        //Local destination file.
        File.Delete(tempFile);
    }

    //Cleanup install file.
    /**
    * This ONLY runs when first opening the program.  Gets run early in the sequence. Returns false if the program should exit.
    */
    public static boolean checkMySqlVersion() throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype != DatabaseType.MySql)
        {
            return true;
        }
         
        boolean hasBackup = false;
        String thisVersion = MiscData.getMySqlVersion();
        float floatVersion = PIn.Float(thisVersion.Substring(0, 3));
        if (floatVersion < 5.0f)
        {
            //We will force users to upgrade to 5.0, but not yet to 5.5
            MessageBox.Show(Lan.g("Prefs","Your version of MySQL won't work with this program") + ": " + thisVersion + ".  " + Lan.g("Prefs","You should upgrade to MySQL 5.0 using the installer on our website."));
            Application.Exit();
            return false;
        }
         
        if (!PrefC.containsKey("MySqlVersion"))
        {
        }
        else //db has not yet been updated to store this pref
        //We're going to skip this.  We will recommend that people first upgrade OD, then MySQL, so this won't be an issue.
        if (Prefs.UpdateString(PrefName.MySqlVersion, floatVersion.ToString("f1")))
        {
            if (!MsgBox.show("Prefs",MsgBoxButtons.OKCancel,"Tables will now be backed up, optimized, and repaired.  This will take a minute or two.  Continue?"))
            {
                Application.Exit();
                return false;
            }
             
            try
            {
                DatabaseMaintenance.backupRepairAndOptimize();
                hasBackup = true;
            }
            catch (Exception e)
            {
                if (!StringSupport.equals(e.Message, ""))
                {
                    MessageBox.Show(e.Message);
                }
                 
                MsgBox.show("Prefs","Backup failed. Your database has not been altered.");
                Application.Exit();
                return false;
            }
        
        }
          
        //but this should never happen
        if (PrefC.containsKey("DatabaseConvertedForMySql41"))
        {
            return true;
        }
         
        //already converted
        if (!MsgBox.show("Prefs",true,"Your database will now be converted for use with MySQL 4.1."))
        {
            Application.Exit();
            return false;
        }
         
        try
        {
            //ClassConvertDatabase CCD=new ClassConvertDatabase();
            if (!hasBackup)
            {
                //A backup could have been made if the tables were optimized and repaired above.
                MiscData.makeABackup();
            }
             
        }
        catch (Exception e)
        {
            if (!StringSupport.equals(e.Message, ""))
            {
                MessageBox.Show(e.Message);
            }
             
            MsgBox.show("Prefs","Backup failed. Your database has not been altered.");
            Application.Exit();
            return false;
        }

        //but this should never happen
        MessageBox.Show("Backup performed");
        Prefs.convertToMySqlVersion41();
        MessageBox.Show("converted");
        return true;
    }

    //Refresh();
    /**
    * This runs when first opening the program.  If MySql is not at 5.5 or higher, it reminds the user, but does not force them to upgrade.
    */
    public static void mySqlVersion55Remind() throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype != DatabaseType.MySql)
        {
            return ;
        }
         
        String thisVersion = MiscData.getMySqlVersion();
        float floatVersion = PIn.Float(thisVersion.Substring(0, 3));
        //doing it this way will be needed later to handle two digit version numbers.
        //We may then combine them into a single float, remembering to left pad single digit minor versions.
        //int majVer=int.Parse(thisVersion.Split(',','.')[0]);
        //int minVer=int.Parse(thisVersion.Split(',','.')[1]);
        //if((majVer<5 || (majVer=5 && minVer<5))	&& !Programs.IsEnabled(ProgramName.eClinicalWorks)){//Do not show msg if MySQL version is 5.5 or greater or eCW is enabled
        if (floatVersion < 5.5f && !Programs.isEnabled(ProgramName.eClinicalWorks))
        {
            //Do not show msg if MySQL version is 5.5 or greater or eCW is enabled
            MsgBox.show("Prefs","You should upgrade to MySQL 5.5 using the installer posted on our website.  It's not urgent, but until you upgrade, you are likely to get a few errors each day which will require restarting the MySQL service.");
        }
         
    }

    /**
    * Essentially no changes have been made to this since version 6.5.
    */
    private static boolean checkProgramVersionClassic() throws Exception {
        Version storedVersion = new Version(PrefC.getString(PrefName.ProgramVersion));
        Version currentVersion = new Version(Application.ProductVersion);
        String database = MiscData.getCurrentDatabase();
        if (storedVersion < currentVersion)
        {
            Prefs.UpdateString(PrefName.ProgramVersion, currentVersion.ToString());
            Cache.refresh(InvalidType.Prefs);
        }
         
        if (storedVersion > currentVersion)
        {
            if (PrefC.getAtoZfolderUsed())
            {
                String setupBinPath = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"Setup.exe");
                if (File.Exists(setupBinPath))
                {
                    if (MessageBox.Show("You are attempting to run version " + currentVersion.ToString(3) + ",\r\n" + "But the database " + database + "\r\n" + "is already using version " + storedVersion.ToString(3) + ".\r\n" + "A newer version must have already been installed on at least one computer.\r\n" + "The setup program stored in your A to Z folder will now be launched.\r\n" + "Or, if you hit Cancel, then you will have the option to download again.", "", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
                    {
                        if (MessageBox.Show("Download again?", "", MessageBoxButtons.OKCancel) == DialogResult.OK)
                        {
                            FormUpdate FormU = new FormUpdate();
                            FormU.ShowDialog();
                        }
                         
                        Application.Exit();
                        return false;
                    }
                     
                    try
                    {
                        Process.Start(setupBinPath);
                    }
                    catch (Exception __dummyCatchVar2)
                    {
                        MessageBox.Show("Could not launch Setup.exe");
                    }
                
                }
                else if (MessageBox.Show("A newer version has been installed on at least one computer," + "but Setup.exe could not be found in any of the following paths: " + ImageStore.getPreferredAtoZpath() + ".  Download again?", "", MessageBoxButtons.OKCancel) == DialogResult.OK)
                {
                    FormUpdate FormU = new FormUpdate();
                    FormU.ShowDialog();
                }
                  
            }
            else
            {
                //Not using image path.
                //perform program update automatically.
                String patchName = "Setup.exe";
                String updateUri = PrefC.getString(PrefName.UpdateWebsitePath);
                String updateCode = PrefC.getString(PrefName.UpdateCode);
                String updateInfoMajor = "";
                String updateInfoMinor = "";
                RefSupport<String> refVar___2 = new RefSupport<String>();
                RefSupport<String> refVar___3 = new RefSupport<String>();
                boolean boolVar___1 = FormUpdate.shouldDownloadUpdate(updateUri,updateCode,refVar___2,refVar___3);
                updateInfoMajor = refVar___2.getValue();
                updateInfoMinor = refVar___3.getValue();
                if (boolVar___1)
                {
                    if (MessageBox.Show(updateInfoMajor + Lan.g("Prefs","Perform program update now?"), "", MessageBoxButtons.YesNo) == DialogResult.Yes)
                    {
                        String tempFile = CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), patchName);
                        //Resort to a more common temp file name.
                        //Source URI
                        FormUpdate.downloadInstallPatchFromURI(updateUri + updateCode + "/" + patchName,tempFile,true,true,null);
                        //Local destination file.
                        File.Delete(tempFile);
                    }
                     
                }
                 
            } 
            //Cleanup install file.
            Application.Exit();
            return false;
        }
         
        return true;
    }

}


//always exits, whether launch of setup worked or not