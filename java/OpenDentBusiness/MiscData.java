//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:54 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Miscellaneous database functions.
*/
public class MiscData   
{
    /**
    * Gets the current date/Time direcly from the server.  Mostly used to prevent uesr from altering the workstation date to bypass security.
    */
    public static DateTime getNowDateTime() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DateTime>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT NOW()";
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            command = "SELECT CURRENT_TIMESTAMP FROM DUAL";
        }
         
        DataTable table = Db.getTable(command);
        return PIn.DateT(table.Rows[0][0].ToString());
    }

    /**
    * Gets the current date/Time with milliseconds directly from server.  In Mysql we must query the server until the second rolls over, which may take up to one second.  Used to confirm synchronization in time for EHR.
    */
    public static DateTime getNowDateTimeWithMilli() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DateTime>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = new String();
        String dbtime = new String();
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.MySql)
        {
            command = "SELECT NOW()";
            //Only up to 1 second precision pre-Mysql 5.6.4.  Does not round milliseconds.
            dbtime = Db.getScalar(command);
            int secondInit = PIn.dateT(dbtime).Second;
            int secondCur = new int();
            do
            {
                //Continue querying server for current time until second changes (milliseconds will be close to 0)
                dbtime = Db.getScalar(command);
                secondCur = PIn.dateT(dbtime).Second;
            }
            while (secondInit == secondCur);
        }
        else
        {
            command = "SELECT CURRENT_TIMESTAMP(3) FROM DUAL";
            //Timestamp with milliseconds
            dbtime = Db.getScalar(command);
        } 
        return PIn.dateT(dbtime);
    }

    /**
    * Used in MakeABackup to ensure a unique backup database name.
    */
    private static boolean contains(String[] arrayToSearch, String valueToTest) throws Exception {
        //No need to check RemotingRole; no call to db.
        String compare = new String();
        for (int i = 0;i < arrayToSearch.Length;i++)
        {
            compare = arrayToSearch[i];
            if (StringSupport.equals(arrayToSearch[i], valueToTest))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Backs up the database to the same directory as the original just in case the user did not have sense enough to do a backup first.
    */
    public static long makeABackup() throws Exception {
        //This function should always make the backup on the server itself, and since no directories are
        //referred to (all handled with MySQL), this function will always be referred to the server from
        //client machines.
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        //only used in two places: upgrading version, and upgrading mysql version.
        //Both places check first to make sure user is using mysql.
        //we have to be careful to throw an exception if the backup is failing.
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        String command = "SELECT database()";
        DataTable table = dcon.getTable(command);
        String oldDb = PIn.String(table.Rows[0][0].ToString());
        String newDb = oldDb + "backup_" + DateTime.Today.ToString("MM_dd_yyyy");
        command = "SHOW DATABASES";
        table = dcon.getTable(command);
        String[] databases = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            databases[i] = table.Rows[i][0].ToString();
        }
        if (Contains(databases, newDb))
        {
            //if the new database name already exists
            //find a unique one
            int uniqueID = 1;
            String originalNewDb = newDb;
            do
            {
                newDb = originalNewDb + "_" + uniqueID.ToString();
                uniqueID++;
            }
            while (Contains(databases, newDb));
        }
         
        command = "CREATE DATABASE " + newDb + " CHARACTER SET utf8";
        dcon.nonQ(command);
        command = "SHOW TABLES";
        table = dcon.getTable(command);
        String[] tableName = new String[table.Rows.Count];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            tableName[i] = table.Rows[i][0].ToString();
        }
        //switch to using the new database
        OpenDentBusiness.DataConnection newDcon = new OpenDentBusiness.DataConnection(newDb);
        for (int i = 0;i < tableName.Length;i++)
        {
            command = "SHOW CREATE TABLE " + oldDb + "." + tableName[i];
            table = newDcon.getTable(command);
            command = PIn.byteArray(table.Rows[0][1]);
            newDcon.nonQ(command);
            //this has to be run using connection with new database
            command = "INSERT INTO " + newDb + "." + tableName[i] + " SELECT * FROM " + oldDb + "." + tableName[i];
            newDcon.nonQ(command);
        }
        return 0;
    }

    public static String getCurrentDatabase() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT database()";
        DataTable table = Db.getTable(command);
        return PIn.String(table.Rows[0][0].ToString());
    }

    public static String getMySqlVersion() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT @@version";
        DataTable table = Db.getTable(command);
        return PIn.String(table.Rows[0][0].ToString());
    }

    /**
    * Gets the human readable host name of the database server, even when using the middle-tier.  This will return an empty string if Dns lookup fails.
    */
    public static String getODServer() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod());
        }
         
        //string command="SELECT @@hostname";//This command fails in MySQL 5.0.22 (the version of MySQL 5.0 we used to use), because the hostname variable was added in MySQL 5.0.38.
        //string rawHostName=DataConnection.GetServerName();//This could be a human readable name, or it might be "localhost" or "127.0.0.1" or another IP address.
        //return Dns.GetHostEntry(rawHostName).HostName;//Return the human readable name (full domain name) corresponding to the rawHostName.
        //Had to strip off the port, caused Dns.GetHostEntry to fail and is not needed to get the hostname
        String rawHostName = OpenDentBusiness.DataConnection.getServerName().Split(':')[0];
        //This could be a human readable name, or it might be "localhost" or "127.0.0.1" or another IP address.
        String retval = "";
        try
        {
            retval = Dns.GetHostEntry(rawHostName).HostName;
        }
        catch (Exception ex)
        {
        }

        return retval;
    }

    //Return the human readable name (full domain name) corresponding to the rawHostName.
    /**
    * Returns a collection of unique AtoZ folders for the array of dbnames passed in.  It will not include the current AtoZ folder for this database, even if shared by another db.  This is used for the feature that updates multiple databases simultaneously.
    */
    public static List<String> getAtoZforDb(String[] dbNames) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("MiscData.GetAtoZforDb failed.  Updates not allowed from ClientWeb.");
        }
         
        List<String> retval = new List<String>();
        OpenDentBusiness.DataConnection dcon = null;
        String atozName = new String();
        String atozThisDb = PrefC.getString(PrefName.DocPath);
        for (int i = 0;i < dbNames.Length;i++)
        {
            try
            {
                dcon = new OpenDentBusiness.DataConnection(dbNames[i]);
                String command = "SELECT ValueString FROM preference WHERE PrefName='DocPath'";
                atozName = dcon.getScalar(command);
                if (retval.Contains(atozName))
                {
                    continue;
                }
                 
                if (StringSupport.equals(atozName, atozThisDb))
                {
                    continue;
                }
                 
                retval.Add(atozName);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        return retval;
    }

    //don't add it to the list
    public static void lockWorkstationsForDbs(String[] dbNames) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("MiscData.LockWorkstationsForDbs failed.  Updates not allowed from ClientWeb.");
        }
         
        OpenDentBusiness.DataConnection dcon = null;
        for (int i = 0;i < dbNames.Length;i++)
        {
            try
            {
                dcon = new OpenDentBusiness.DataConnection(dbNames[i]);
                String command = "UPDATE preference SET ValueString ='" + POut.String(Environment.MachineName) + "' WHERE PrefName='UpdateInProgressOnComputerName'";
                dcon.nonQ(command);
            }
            catch (Exception __dummyCatchVar1)
            {
            }
        
        }
    }

    public static void unlockWorkstationsForDbs(String[] dbNames) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("MiscData.UnlockWorkstationsForDbs failed.  Updates not allowed from ClientWeb.");
        }
         
        OpenDentBusiness.DataConnection dcon = null;
        for (int i = 0;i < dbNames.Length;i++)
        {
            try
            {
                dcon = new OpenDentBusiness.DataConnection(dbNames[i]);
                String command = "UPDATE preference SET ValueString =''" + " WHERE PrefName='UpdateInProgressOnComputerName'";
                dcon.nonQ(command);
            }
            catch (Exception __dummyCatchVar2)
            {
            }
        
        }
    }

    public static void setSqlMode() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "SET GLOBAL sql_mode=''";
        //in case user did not use our my.ini file.
        //A slightly more elegant approach could require less user permissions.  It could first check SELECT @@GLOBAL.sql_mode,
        //and then only attempt to set if it was not blank already.
        Db.nonQ(command);
    }

}


