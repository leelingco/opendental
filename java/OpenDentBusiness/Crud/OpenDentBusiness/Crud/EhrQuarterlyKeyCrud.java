//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrQuarterlyKeyCrud   
{
    /**
    * Gets one EhrQuarterlyKey object from the database using the primary key.  Returns null if not found.
    */
    public static EhrQuarterlyKey selectOne(long ehrQuarterlyKeyNum) throws Exception {
        String command = "SELECT * FROM ehrquarterlykey " + "WHERE EhrQuarterlyKeyNum = " + POut.Long(ehrQuarterlyKeyNum);
        List<EhrQuarterlyKey> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrQuarterlyKey object from the database using a query.
    */
    public static EhrQuarterlyKey selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrQuarterlyKey> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrQuarterlyKey objects from the database using a query.
    */
    public static List<EhrQuarterlyKey> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrQuarterlyKey> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrQuarterlyKey> tableToList(DataTable table) throws Exception {
        List<EhrQuarterlyKey> retVal = new List<EhrQuarterlyKey>();
        EhrQuarterlyKey ehrQuarterlyKey = new EhrQuarterlyKey();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrQuarterlyKey = new EhrQuarterlyKey();
            ehrQuarterlyKey.EhrQuarterlyKeyNum = PIn.Long(table.Rows[i]["EhrQuarterlyKeyNum"].ToString());
            ehrQuarterlyKey.YearValue = PIn.Int(table.Rows[i]["YearValue"].ToString());
            ehrQuarterlyKey.QuarterValue = PIn.Int(table.Rows[i]["QuarterValue"].ToString());
            ehrQuarterlyKey.PracticeName = PIn.String(table.Rows[i]["PracticeName"].ToString());
            ehrQuarterlyKey.KeyValue = PIn.String(table.Rows[i]["KeyValue"].ToString());
            ehrQuarterlyKey.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            ehrQuarterlyKey.Notes = PIn.String(table.Rows[i]["Notes"].ToString());
            retVal.Add(ehrQuarterlyKey);
        }
        return retVal;
    }

    /**
    * Inserts one EhrQuarterlyKey into the database.  Returns the new priKey.
    */
    public static long insert(EhrQuarterlyKey ehrQuarterlyKey) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrQuarterlyKey.EhrQuarterlyKeyNum = DbHelper.GetNextOracleKey("ehrquarterlykey", "EhrQuarterlyKeyNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrQuarterlyKey,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrQuarterlyKey.EhrQuarterlyKeyNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return insert(ehrQuarterlyKey,false);
        } 
    }

    /**
    * Inserts one EhrQuarterlyKey into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrQuarterlyKey ehrQuarterlyKey, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrQuarterlyKey.EhrQuarterlyKeyNum = ReplicationServers.GetKey("ehrquarterlykey", "EhrQuarterlyKeyNum");
        }
         
        String command = "INSERT INTO ehrquarterlykey (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EhrQuarterlyKeyNum,";
        }
         
        command += "YearValue,QuarterValue,PracticeName,KeyValue,PatNum,Notes) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrQuarterlyKey.EhrQuarterlyKeyNum) + ",";
        }
         
        command += POut.Int(ehrQuarterlyKey.YearValue) + "," + POut.Int(ehrQuarterlyKey.QuarterValue) + "," + "'" + POut.String(ehrQuarterlyKey.PracticeName) + "'," + "'" + POut.String(ehrQuarterlyKey.KeyValue) + "'," + POut.Long(ehrQuarterlyKey.PatNum) + "," + "'" + POut.String(ehrQuarterlyKey.Notes) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ehrQuarterlyKey.EhrQuarterlyKeyNum = Db.NonQ(command, true);
        } 
        return ehrQuarterlyKey.EhrQuarterlyKeyNum;
    }

    /**
    * Updates one EhrQuarterlyKey in the database.
    */
    public static void update(EhrQuarterlyKey ehrQuarterlyKey) throws Exception {
        String command = "UPDATE ehrquarterlykey SET " + "YearValue         =  " + POut.Int(ehrQuarterlyKey.YearValue) + ", " + "QuarterValue      =  " + POut.Int(ehrQuarterlyKey.QuarterValue) + ", " + "PracticeName      = '" + POut.String(ehrQuarterlyKey.PracticeName) + "', " + "KeyValue          = '" + POut.String(ehrQuarterlyKey.KeyValue) + "', " + "PatNum            =  " + POut.Long(ehrQuarterlyKey.PatNum) + ", " + "Notes             = '" + POut.String(ehrQuarterlyKey.Notes) + "' " + "WHERE EhrQuarterlyKeyNum = " + POut.Long(ehrQuarterlyKey.EhrQuarterlyKeyNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EhrQuarterlyKey in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrQuarterlyKey ehrQuarterlyKey, EhrQuarterlyKey oldEhrQuarterlyKey) throws Exception {
        String command = "";
        if (ehrQuarterlyKey.YearValue != oldEhrQuarterlyKey.YearValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "YearValue = " + POut.Int(ehrQuarterlyKey.YearValue) + "";
        }
         
        if (ehrQuarterlyKey.QuarterValue != oldEhrQuarterlyKey.QuarterValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "QuarterValue = " + POut.Int(ehrQuarterlyKey.QuarterValue) + "";
        }
         
        if (ehrQuarterlyKey.PracticeName != oldEhrQuarterlyKey.PracticeName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PracticeName = '" + POut.String(ehrQuarterlyKey.PracticeName) + "'";
        }
         
        if (ehrQuarterlyKey.KeyValue != oldEhrQuarterlyKey.KeyValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "KeyValue = '" + POut.String(ehrQuarterlyKey.KeyValue) + "'";
        }
         
        if (ehrQuarterlyKey.PatNum != oldEhrQuarterlyKey.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(ehrQuarterlyKey.PatNum) + "";
        }
         
        if (ehrQuarterlyKey.Notes != oldEhrQuarterlyKey.Notes)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Notes = '" + POut.String(ehrQuarterlyKey.Notes) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrquarterlykey SET " + command + " WHERE EhrQuarterlyKeyNum = " + POut.Long(ehrQuarterlyKey.EhrQuarterlyKeyNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EhrQuarterlyKey from the database.
    */
    public static void delete(long ehrQuarterlyKeyNum) throws Exception {
        String command = "DELETE FROM ehrquarterlykey " + "WHERE EhrQuarterlyKeyNum = " + POut.Long(ehrQuarterlyKeyNum);
        Db.NonQ(command);
    }

}


