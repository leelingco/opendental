//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrProvKeyCrud   
{
    /**
    * Gets one EhrProvKey object from the database using the primary key.  Returns null if not found.
    */
    public static EhrProvKey selectOne(long ehrProvKeyNum) throws Exception {
        String command = "SELECT * FROM ehrprovkey " + "WHERE EhrProvKeyNum = " + POut.Long(ehrProvKeyNum);
        List<EhrProvKey> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrProvKey object from the database using a query.
    */
    public static EhrProvKey selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrProvKey> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrProvKey objects from the database using a query.
    */
    public static List<EhrProvKey> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrProvKey> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrProvKey> tableToList(DataTable table) throws Exception {
        List<EhrProvKey> retVal = new List<EhrProvKey>();
        EhrProvKey ehrProvKey = new EhrProvKey();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrProvKey = new EhrProvKey();
            ehrProvKey.EhrProvKeyNum = PIn.Long(table.Rows[i]["EhrProvKeyNum"].ToString());
            ehrProvKey.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            ehrProvKey.LName = PIn.String(table.Rows[i]["LName"].ToString());
            ehrProvKey.FName = PIn.String(table.Rows[i]["FName"].ToString());
            ehrProvKey.ProvKey = PIn.String(table.Rows[i]["ProvKey"].ToString());
            ehrProvKey.FullTimeEquiv = PIn.Float(table.Rows[i]["FullTimeEquiv"].ToString());
            ehrProvKey.Notes = PIn.String(table.Rows[i]["Notes"].ToString());
            ehrProvKey.HasReportAccess = PIn.Bool(table.Rows[i]["HasReportAccess"].ToString());
            retVal.Add(ehrProvKey);
        }
        return retVal;
    }

    /**
    * Inserts one EhrProvKey into the database.  Returns the new priKey.
    */
    public static long insert(EhrProvKey ehrProvKey) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ehrProvKey.EhrProvKeyNum = DbHelper.GetNextOracleKey("ehrprovkey", "EhrProvKeyNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ehrProvKey,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrProvKey.EhrProvKeyNum++;
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
            return insert(ehrProvKey,false);
        } 
    }

    /**
    * Inserts one EhrProvKey into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrProvKey ehrProvKey, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ehrProvKey.EhrProvKeyNum = ReplicationServers.GetKey("ehrprovkey", "EhrProvKeyNum");
        }
         
        String command = "INSERT INTO ehrprovkey (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EhrProvKeyNum,";
        }
         
        command += "PatNum,LName,FName,ProvKey,FullTimeEquiv,Notes,HasReportAccess) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ehrProvKey.EhrProvKeyNum) + ",";
        }
         
        command += POut.Long(ehrProvKey.PatNum) + "," + "'" + POut.String(ehrProvKey.LName) + "'," + "'" + POut.String(ehrProvKey.FName) + "'," + "'" + POut.String(ehrProvKey.ProvKey) + "'," + POut.Float(ehrProvKey.FullTimeEquiv) + "," + "'" + POut.String(ehrProvKey.Notes) + "'," + POut.Bool(ehrProvKey.HasReportAccess) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ehrProvKey.EhrProvKeyNum = Db.NonQ(command, true);
        } 
        return ehrProvKey.EhrProvKeyNum;
    }

    /**
    * Updates one EhrProvKey in the database.
    */
    public static void update(EhrProvKey ehrProvKey) throws Exception {
        String command = "UPDATE ehrprovkey SET " + "PatNum         =  " + POut.Long(ehrProvKey.PatNum) + ", " + "LName          = '" + POut.String(ehrProvKey.LName) + "', " + "FName          = '" + POut.String(ehrProvKey.FName) + "', " + "ProvKey        = '" + POut.String(ehrProvKey.ProvKey) + "', " + "FullTimeEquiv  =  " + POut.Float(ehrProvKey.FullTimeEquiv) + ", " + "Notes          = '" + POut.String(ehrProvKey.Notes) + "', " + "HasReportAccess=  " + POut.Bool(ehrProvKey.HasReportAccess) + " " + "WHERE EhrProvKeyNum = " + POut.Long(ehrProvKey.EhrProvKeyNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EhrProvKey in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrProvKey ehrProvKey, EhrProvKey oldEhrProvKey) throws Exception {
        String command = "";
        if (ehrProvKey.PatNum != oldEhrProvKey.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(ehrProvKey.PatNum) + "";
        }
         
        if (ehrProvKey.LName != oldEhrProvKey.LName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LName = '" + POut.String(ehrProvKey.LName) + "'";
        }
         
        if (ehrProvKey.FName != oldEhrProvKey.FName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FName = '" + POut.String(ehrProvKey.FName) + "'";
        }
         
        if (ehrProvKey.ProvKey != oldEhrProvKey.ProvKey)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvKey = '" + POut.String(ehrProvKey.ProvKey) + "'";
        }
         
        if (ehrProvKey.FullTimeEquiv != oldEhrProvKey.FullTimeEquiv)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FullTimeEquiv = " + POut.Float(ehrProvKey.FullTimeEquiv) + "";
        }
         
        if (ehrProvKey.Notes != oldEhrProvKey.Notes)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Notes = '" + POut.String(ehrProvKey.Notes) + "'";
        }
         
        if (ehrProvKey.HasReportAccess != oldEhrProvKey.HasReportAccess)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HasReportAccess = " + POut.Bool(ehrProvKey.HasReportAccess) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrprovkey SET " + command + " WHERE EhrProvKeyNum = " + POut.Long(ehrProvKey.EhrProvKeyNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EhrProvKey from the database.
    */
    public static void delete(long ehrProvKeyNum) throws Exception {
        String command = "DELETE FROM ehrprovkey " + "WHERE EhrProvKeyNum = " + POut.Long(ehrProvKeyNum);
        Db.NonQ(command);
    }

}


