//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:55 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AutoCodeCondCrud   
{
    /**
    * Gets one AutoCodeCond object from the database using the primary key.  Returns null if not found.
    */
    public static AutoCodeCond selectOne(long autoCodeCondNum) throws Exception {
        String command = "SELECT * FROM autocodecond " + "WHERE AutoCodeCondNum = " + POut.Long(autoCodeCondNum);
        List<AutoCodeCond> list = TableToList(Db.GetTable(command));
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
         
        List<AutoCodeCond> list = TableToList(Db.GetTable(command));
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
         
        List<AutoCodeCond> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<AutoCodeCond> tableToList(DataTable table) throws Exception {
        List<AutoCodeCond> retVal = new List<AutoCodeCond>();
        AutoCodeCond autoCodeCond = new AutoCodeCond();
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
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            autoCodeCond.AutoCodeCondNum = DbHelper.GetNextOracleKey("autocodecond", "AutoCodeCondNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(autoCodeCond,true);
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
            return insert(autoCodeCond,false);
        } 
    }

    /**
    * Inserts one AutoCodeCond into the database.  Provides option to use the existing priKey.
    */
    public static long insert(AutoCodeCond autoCodeCond, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            autoCodeCond.AutoCodeCondNum = ReplicationServers.GetKey("autocodecond", "AutoCodeCondNum");
        }
         
        String command = "INSERT INTO autocodecond (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "AutoCodeCondNum,";
        }
         
        command += "AutoCodeItemNum,Cond) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(autoCodeCond.AutoCodeCondNum) + ",";
        }
         
        command += POut.Long(autoCodeCond.AutoCodeItemNum) + "," + POut.Int((int)autoCodeCond.Cond) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            autoCodeCond.AutoCodeCondNum = Db.NonQ(command, true);
        } 
        return autoCodeCond.AutoCodeCondNum;
    }

    /**
    * Updates one AutoCodeCond in the database.
    */
    public static void update(AutoCodeCond autoCodeCond) throws Exception {
        String command = "UPDATE autocodecond SET " + "AutoCodeItemNum=  " + POut.Long(autoCodeCond.AutoCodeItemNum) + ", " + "Cond           =  " + POut.Int((int)autoCodeCond.Cond) + " " + "WHERE AutoCodeCondNum = " + POut.Long(autoCodeCond.AutoCodeCondNum);
        Db.NonQ(command);
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
             
            command += "AutoCodeItemNum = " + POut.Long(autoCodeCond.AutoCodeItemNum) + "";
        }
         
        if (autoCodeCond.Cond != oldAutoCodeCond.Cond)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Cond = " + POut.Int((int)autoCodeCond.Cond) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE autocodecond SET " + command + " WHERE AutoCodeCondNum = " + POut.Long(autoCodeCond.AutoCodeCondNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one AutoCodeCond from the database.
    */
    public static void delete(long autoCodeCondNum) throws Exception {
        String command = "DELETE FROM autocodecond " + "WHERE AutoCodeCondNum = " + POut.Long(autoCodeCondNum);
        Db.NonQ(command);
    }

}


