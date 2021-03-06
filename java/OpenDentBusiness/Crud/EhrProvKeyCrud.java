//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:02 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrProvKey;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrProvKeyCrud   
{
    /**
    * Gets one EhrProvKey object from the database using the primary key.  Returns null if not found.
    */
    public static EhrProvKey selectOne(long ehrProvKeyNum) throws Exception {
        String command = "SELECT * FROM ehrprovkey " + "WHERE EhrProvKeyNum = " + POut.long(ehrProvKeyNum);
        List<EhrProvKey> list = tableToList(Db.getTable(command));
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
         
        List<EhrProvKey> list = tableToList(Db.getTable(command));
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
         
        List<EhrProvKey> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrProvKey> tableToList(DataTable table) throws Exception {
        List<EhrProvKey> retVal = new List<EhrProvKey>();
        EhrProvKey ehrProvKey;
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
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            ehrProvKey.EhrProvKeyNum = DbHelper.getNextOracleKey("ehrprovkey","EhrProvKeyNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(ehrProvKey, true);
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
            return Insert(ehrProvKey, false);
        } 
    }

    /**
    * Inserts one EhrProvKey into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrProvKey ehrProvKey, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            ehrProvKey.EhrProvKeyNum = ReplicationServers.getKey("ehrprovkey","EhrProvKeyNum");
        }
         
        String command = "INSERT INTO ehrprovkey (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EhrProvKeyNum,";
        }
         
        command += "PatNum,LName,FName,ProvKey,FullTimeEquiv,Notes,HasReportAccess) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(ehrProvKey.EhrProvKeyNum) + ",";
        }
         
        command += POut.long(ehrProvKey.PatNum) + "," + "'" + POut.string(ehrProvKey.LName) + "'," + "'" + POut.string(ehrProvKey.FName) + "'," + "'" + POut.string(ehrProvKey.ProvKey) + "'," + POut.float(ehrProvKey.FullTimeEquiv) + "," + "'" + POut.string(ehrProvKey.Notes) + "'," + POut.bool(ehrProvKey.HasReportAccess) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            ehrProvKey.EhrProvKeyNum = Db.nonQ(command,true);
        } 
        return ehrProvKey.EhrProvKeyNum;
    }

    /**
    * Updates one EhrProvKey in the database.
    */
    public static void update(EhrProvKey ehrProvKey) throws Exception {
        String command = "UPDATE ehrprovkey SET " + "PatNum         =  " + POut.long(ehrProvKey.PatNum) + ", " + "LName          = '" + POut.string(ehrProvKey.LName) + "', " + "FName          = '" + POut.string(ehrProvKey.FName) + "', " + "ProvKey        = '" + POut.string(ehrProvKey.ProvKey) + "', " + "FullTimeEquiv  =  " + POut.float(ehrProvKey.FullTimeEquiv) + ", " + "Notes          = '" + POut.string(ehrProvKey.Notes) + "', " + "HasReportAccess=  " + POut.bool(ehrProvKey.HasReportAccess) + " " + "WHERE EhrProvKeyNum = " + POut.long(ehrProvKey.EhrProvKeyNum);
        Db.nonQ(command);
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
             
            command += "PatNum = " + POut.long(ehrProvKey.PatNum) + "";
        }
         
        if (!StringSupport.equals(ehrProvKey.LName, oldEhrProvKey.LName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LName = '" + POut.string(ehrProvKey.LName) + "'";
        }
         
        if (!StringSupport.equals(ehrProvKey.FName, oldEhrProvKey.FName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FName = '" + POut.string(ehrProvKey.FName) + "'";
        }
         
        if (!StringSupport.equals(ehrProvKey.ProvKey, oldEhrProvKey.ProvKey))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvKey = '" + POut.string(ehrProvKey.ProvKey) + "'";
        }
         
        if (ehrProvKey.FullTimeEquiv != oldEhrProvKey.FullTimeEquiv)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FullTimeEquiv = " + POut.float(ehrProvKey.FullTimeEquiv) + "";
        }
         
        if (!StringSupport.equals(ehrProvKey.Notes, oldEhrProvKey.Notes))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Notes = '" + POut.string(ehrProvKey.Notes) + "'";
        }
         
        if (ehrProvKey.HasReportAccess != oldEhrProvKey.HasReportAccess)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HasReportAccess = " + POut.bool(ehrProvKey.HasReportAccess) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrprovkey SET " + command + " WHERE EhrProvKeyNum = " + POut.long(ehrProvKey.EhrProvKeyNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one EhrProvKey from the database.
    */
    public static void delete(long ehrProvKeyNum) throws Exception {
        String command = "DELETE FROM ehrprovkey " + "WHERE EhrProvKeyNum = " + POut.long(ehrProvKeyNum);
        Db.nonQ(command);
    }

}


