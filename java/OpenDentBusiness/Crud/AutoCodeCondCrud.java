//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:57:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AutoCodeCond;
import OpenDentBusiness.AutoCondition;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AutoCodeCondCrud   
{
    /**
    * Gets one AutoCodeCond object from the database using the primary key.  Returns null if not found.
    */
    public static AutoCodeCond selectOne(long autoCodeCondNum) throws Exception {
        String command = "SELECT * FROM autocodecond " + "WHERE AutoCodeCondNum = " + POut.long(autoCodeCondNum);
        List<AutoCodeCond> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one AutoCodeCond object from the database using a query.
    */
    public static AutoCodeCond selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<AutoCodeCond> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of AutoCodeCond objects from the database using a query.
    */
    public static List<AutoCodeCond> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<AutoCodeCond> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<AutoCodeCond> tableToList(DataTable table) throws Exception {
        List<AutoCodeCond> retVal = new List<AutoCodeCond>();
        AutoCodeCond autoCodeCond;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            autoCodeCond = new AutoCodeCond();
            autoCodeCond.AutoCodeCondNum = PIn.Long(table.Rows[i]["AutoCodeCondNum"].ToString());
            autoCodeCond.AutoCodeItemNum = PIn.Long(table.Rows[i]["AutoCodeItemNum"].ToString());
            autoCodeCond.Cond = (AutoCondition)PIn.Int(table.Rows[i]["Cond"].ToString());
            retVal.Add(autoCodeCond);
        }
        return retVal;
    }

    /**
    * Inserts one AutoCodeCond into the database.  Returns the new priKey.
    */
    public static long insert(AutoCodeCond autoCodeCond) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            autoCodeCond.AutoCodeCondNum = DbHelper.getNextOracleKey("autocodecond","AutoCodeCondNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(autoCodeCond, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        autoCodeCond.AutoCodeCondNum++;
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
            return Insert(autoCodeCond, false);
        } 
    }

    /**
    * Inserts one AutoCodeCond into the database.  Provides option to use the existing priKey.
    */
    public static long insert(AutoCodeCond autoCodeCond, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            autoCodeCond.AutoCodeCondNum = ReplicationServers.getKey("autocodecond","AutoCodeCondNum");
        }
         
        String command = "INSERT INTO autocodecond (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "AutoCodeCondNum,";
        }
         
        command += "AutoCodeItemNum,Cond) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(autoCodeCond.AutoCodeCondNum) + ",";
        }
         
        command += POut.long(autoCodeCond.AutoCodeItemNum) + "," + POut.int(((Enum)autoCodeCond.Cond).ordinal()) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            autoCodeCond.AutoCodeCondNum = Db.nonQ(command,true);
        } 
        return autoCodeCond.AutoCodeCondNum;
    }

    /**
    * Updates one AutoCodeCond in the database.
    */
    public static void update(AutoCodeCond autoCodeCond) throws Exception {
        String command = "UPDATE autocodecond SET " + "AutoCodeItemNum=  " + POut.long(autoCodeCond.AutoCodeItemNum) + ", " + "Cond           =  " + POut.int(((Enum)autoCodeCond.Cond).ordinal()) + " " + "WHERE AutoCodeCondNum = " + POut.long(autoCodeCond.AutoCodeCondNum);
        Db.nonQ(command);
    }

    /**
    * Updates one AutoCodeCond in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(AutoCodeCond autoCodeCond, AutoCodeCond oldAutoCodeCond) throws Exception {
        String command = "";
        if (autoCodeCond.AutoCodeItemNum != oldAutoCodeCond.AutoCodeItemNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AutoCodeItemNum = " + POut.long(autoCodeCond.AutoCodeItemNum) + "";
        }
         
        if (autoCodeCond.Cond != oldAutoCodeCond.Cond)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Cond = " + POut.int(((Enum)autoCodeCond.Cond).ordinal()) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE autocodecond SET " + command + " WHERE AutoCodeCondNum = " + POut.long(autoCodeCond.AutoCodeCondNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one AutoCodeCond from the database.
    */
    public static void delete(long autoCodeCondNum) throws Exception {
        String command = "DELETE FROM autocodecond " + "WHERE AutoCodeCondNum = " + POut.long(autoCodeCondNum);
        Db.nonQ(command);
    }

}

