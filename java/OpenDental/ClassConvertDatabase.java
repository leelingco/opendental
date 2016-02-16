//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormInsPlanConvert_7_5_17;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Cache;
import OpenDentBusiness.ConvertDatabases;
import OpenDentBusiness.DatabaseMaintenance;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;
import OpenDentBusiness.YN;


/**
* 
*/
public class ClassConvertDatabase   
{

    private System.Version FromVersion = new System.Version();
    private System.Version ToVersion = new System.Version();
    /**
    * Return false to indicate exit app.  Only called when program first starts up at the beginning of FormOpenDental.PrefsStartup.
    */
    public boolean convert(String fromVersion, String toVersion, boolean silent) throws Exception {
        FromVersion = new Version(fromVersion);
        ToVersion = new Version(toVersion);
        //Application.ProductVersion);
        if (FromVersion >= new Version("3.4.0") && PrefC.getBool(PrefName.CorruptedDatabase))
        {
            MsgBox.show(this,"Your database is corrupted because a conversion failed.  Please contact us.  This database is unusable and you will need to restore from a backup.");
            return false;
        }
         
        //shuts program down.
        if (FromVersion == ToVersion)
        {
            return true;
        }
         
        //no conversion necessary
        if (FromVersion.CompareTo(ToVersion) > 0)
        {
            return true;
        }
         
        //"Cannot convert database to an older version."
        //no longer necessary to catch it here.  It will be handled soon enough in CheckProgramVersion
        if (FromVersion < new Version("2.8.0"))
        {
            MsgBox.show(this,"This database is too old to easily convert in one step. Please upgrade to 2.1 if necessary, then to 2.8.  Then you will be able to upgrade to this version. We apologize for the inconvenience.");
            return false;
        }
         
        if (FromVersion < new Version("6.6.2"))
        {
            MsgBox.show(this,"This database is too old to easily convert in one step. Please upgrade to 11.1 first.  Then you will be able to upgrade to this version. We apologize for the inconvenience.");
            return false;
        }
         
        if (FromVersion < new Version("3.0.1"))
        {
            MsgBox.show(this,"This is an old database.  The conversion must be done using MySQL 4.1 (not MySQL 5.0) or it will fail.");
        }
         
        if (StringSupport.equals(FromVersion.ToString(), "2.9.0.0") || StringSupport.equals(FromVersion.ToString(), "3.0.0.0") || StringSupport.equals(FromVersion.ToString(), "4.7.0.0"))
        {
            MsgBox.show(this,"Cannot convert this database version which was only for development purposes.");
            return false;
        }
         
        if (FromVersion > new Version("4.7.0") && FromVersion.Build == 0)
        {
            MsgBox.show(this,"Cannot convert this database version which was only for development purposes.");
            return false;
        }
         
        if (FromVersion >= ConvertDatabases.LatestVersion)
        {
            return true;
        }
         
        //no conversion necessary
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            MsgBox.show(this,"Web client cannot convert database.  Must be using a direct connection.");
            return false;
        }
         
        if (ReplicationServers.serverIsBlocked())
        {
            MsgBox.show(this,"This replication server is blocked from performing updates.");
            return false;
        }
         
        //using web service
        if (!StringSupport.equals(PrefC.getString(PrefName.WebServiceServerName), "") && !CodeBase.ODEnvironment.IdIsThisComputer(PrefC.getString(PrefName.WebServiceServerName).ToLower()))
        {
            //and not on web server
            MessageBox.Show(Lan.g(this,"Updates are only allowed from the web server: ") + PrefC.getString(PrefName.WebServiceServerName));
            return false;
        }
         
        //If MyISAM and InnoDb mix, then try to fix
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
        {
            //not for Oracle
            String namesInnodb = DatabaseMaintenance.getInnodbTableNames();
            //Or possibly some other format.
            int numMyisam = DatabaseMaintenance.getMyisamTableCount();
            if (!StringSupport.equals(namesInnodb, "") && numMyisam > 0)
            {
                MessageBox.Show(Lan.g(this,"A mixture of database tables in InnoDB and MyISAM format were found.  A database backup will now be made, and then the following InnoDB tables will be converted to MyISAM format: ") + namesInnodb);
                try
                {
                    MiscData.makeABackup();
                }
                catch (Exception e)
                {
                    //Does not work for Oracle, due to some MySQL specific commands inside.
                    Cursor.Current = Cursors.Default;
                    if (!StringSupport.equals(e.Message, ""))
                    {
                        MessageBox.Show(e.Message);
                    }
                     
                    MsgBox.show(this,"Backup failed. Your database has not been altered.");
                    return false;
                }

                if (!DatabaseMaintenance.convertTablesToMyisam())
                {
                    MessageBox.Show(Lan.g(this,"Failed to convert InnoDB tables to MyISAM format. Please contact support."));
                    return false;
                }
                 
                MessageBox.Show(Lan.g(this,"All tables converted to MyISAM format successfully."));
                namesInnodb = "";
            }
             
            if (StringSupport.equals(namesInnodb, "") && numMyisam > 0)
            {
                //if all tables are myisam
                //but default storage engine is innodb, then kick them out.
                if (!StringSupport.equals(DatabaseMaintenance.getStorageEngineDefaultName().ToUpper(), "MYISAM"))
                {
                    //Probably InnoDB but could be another format.
                    MessageBox.Show(Lan.g(this,"The database tables are in MyISAM format, but the default database engine format is InnoDB. You must change the default storage engine within the my.ini (or my.cnf) file on the database server and restart MySQL in order to fix this problem. Exiting."));
                    return false;
                }
                 
            }
             
        }
         
        if (!silent && MessageBox.Show(Lan.g(this,"Your database will now be converted") + "\r" + Lan.g(this,"from version") + " " + FromVersion.ToString() + "\r" + Lan.g(this,"to version") + " " + ToVersion.ToString() + "\r" + Lan.g(this,"The conversion works best if you are on the server.  Depending on the speed of your computer, it can be as fast as a few seconds, or it can take as long as 10 minutes."), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return false;
        }
         
        //If user clicks cancel, then close the program
        Cursor.Current = Cursors.WaitCursor;
        if (OpenDentBusiness.DataConnection.DBtype != DatabaseType.MySql && !MsgBox.show(this,true,"If you have not made a backup, please Cancel and backup before continuing.  Continue?"))
        {
            return false;
        }
         
        try
        {
            if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
            {
                MiscData.makeABackup();
            }
             
        }
        catch (Exception e)
        {
            //Does not work for Oracle, due to some MySQL specific commands inside.
            Cursor.Current = Cursors.Default;
            if (!StringSupport.equals(e.Message, ""))
            {
                MessageBox.Show(e.Message);
            }
             
            MsgBox.show(this,"Backup failed. Your database has not been altered.");
            return false;
        }

        try
        {
            if (FromVersion < new Version("7.5.17"))
            {
                //Insurance Plan schema conversion
                Cursor.Current = Cursors.Default;
                YN InsPlanConverstion_7_5_17_AutoMergeYN = YN.Unknown;
                if (FromVersion < new Version("7.5.1"))
                {
                    FormInsPlanConvert_7_5_17 form = new FormInsPlanConvert_7_5_17();
                    if (PrefC.getBoolSilent(PrefName.InsurancePlansShared,true))
                    {
                        form.InsPlanConverstion_7_5_17_AutoMergeYN = YN.Yes;
                    }
                    else
                    {
                        form.InsPlanConverstion_7_5_17_AutoMergeYN = YN.No;
                    } 
                    form.ShowDialog();
                    if (form.DialogResult == DialogResult.Cancel)
                    {
                        MessageBox.Show("Your database has not been altered.");
                        return false;
                    }
                     
                    InsPlanConverstion_7_5_17_AutoMergeYN = form.InsPlanConverstion_7_5_17_AutoMergeYN;
                }
                 
                ConvertDatabases.set_7_5_17_AutoMerge(InsPlanConverstion_7_5_17_AutoMergeYN);
                //does nothing if this pref is already present for some reason.
                Cursor.Current = Cursors.WaitCursor;
            }
             
            if (FromVersion >= new Version("3.4.0"))
            {
                Prefs.updateBool(PrefName.CorruptedDatabase,true);
            }
             
            ConvertDatabases.FromVersion = FromVersion;
            ConvertDatabases.to2_8_2();
            //begins going through the chain of conversion steps
            Cursor.Current = Cursors.Default;
            if (FromVersion >= new Version("3.4.0"))
            {
                //CacheL.Refresh(InvalidType.Prefs);//or it won't know it has to update in the next line.
                Prefs.updateBool(PrefName.CorruptedDatabase,false,true);
            }
             
            //more forceful refresh in order to properly change flag
            if (!silent)
            {
                MsgBox.show(this,"Database update successful");
            }
             
            Cache.refresh(InvalidType.Prefs);
            return true;
        }
        catch (System.IO.FileNotFoundException e)
        {
            MessageBox.Show(e.FileName + " " + Lan.g(this,"could not be found. Your database has not been altered and is still usable if you uninstall this version, then reinstall the previous version."));
            if (FromVersion >= new Version("3.4.0"))
            {
                Prefs.updateBool(PrefName.CorruptedDatabase,false);
            }
             
            return false;
        }
        catch (System.IO.DirectoryNotFoundException __dummyCatchVar0)
        {
            //Prefs.Refresh();
            MessageBox.Show(Lan.g(this,"ConversionFiles folder could not be found. Your database has not been altered and is still usable if you uninstall this version, then reinstall the previous version."));
            if (FromVersion >= new Version("3.4.0"))
            {
                Prefs.updateBool(PrefName.CorruptedDatabase,false);
            }
             
            return false;
        }
        catch (Exception ex)
        {
            //Prefs.Refresh();
            //	MessageBox.Show();
            MessageBox.Show(ex.Message + "\r\n\r\n" + Lan.g(this,"Conversion unsuccessful. Your database is now corrupted and you cannot use it.  Please contact us."));
            return false;
        }
    
    }

}
//Then, application will exit, and database will remain tagged as corrupted.

//Then, application will exit, and database will remain tagged as corrupted.