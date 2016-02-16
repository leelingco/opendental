//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:55 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AutoCodeItemCrud   
{
    /**
    * Gets one AutoCodeItem object from the database using the primary key.  Returns null if not found.
    */
    public static AutoCodeItem selectOne(long autoCodeItemNum) throws Exception {
        String command = "SELECT * FROM autocodeitem " + "WHERE AutoCodeItemNum = " + POut.Long(autoCodeItemNum);
        List<AutoCodeItem> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one AutoCodeItem object from the database using a query.
    */
    public static AutoCodeItem selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<AutoCodeItem> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of AutoCodeItem objects from the database using a query.
    */
    public static List<AutoCodeItem> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<AutoCodeItem> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<AutoCodeItem> tableToList(DataTable table) throws Exception {
        List<AutoCodeItem> retVal = new List<AutoCodeItem>();
        AutoCodeItem autoCodeItem = new AutoCodeItem();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            autoCodeItem = new AutoCodeItem();
            autoCodeItem.AutoCodeItemNum = PIn.Long(table.Rows[i]["AutoCodeItemNum"].ToString());
            autoCodeItem.AutoCodeNum = PIn.Long(table.Rows[i]["AutoCodeNum"].ToString());
            autoCodeItem.OldCode = PIn.String(table.Rows[i]["OldCode"].ToString());
            autoCodeItem.CodeNum = PIn.Long(table.Rows[i]["CodeNum"].ToString());
            retVal.Add(autoCodeItem);
        }
        return retVal;
    }

    /**
    * Inserts one AutoCodeItem into the database.  Returns the new priKey.
    */
    public static long insert(AutoCodeItem autoCodeItem) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            autoCodeItem.AutoCodeItemNum = DbHelper.GetNextOracleKey("autocodeitem", "AutoCodeItemNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(autoCodeItem,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        autoCodeItem.AutoCodeItemNum++;
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
            return insert(autoCodeItem,false);
        } 
    }

    /**
    * Inserts one AutoCodeItem into the database.  Provides option to use the existing priKey.
    */
    public static long insert(AutoCodeItem autoCodeItem, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            autoCodeItem.AutoCodeItemNum = ReplicationServers.GetKey("autocodeitem", "AutoCodeItemNum");
        }
         
        String command = "INSERT INTO autocodeitem (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "AutoCodeItemNum,";
        }
         
        command += "AutoCodeNum,OldCode,CodeNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(autoCodeItem.AutoCodeItemNum) + ",";
        }
         
        command += POut.Long(autoCodeItem.AutoCodeNum) + "," + "'" + POut.String(autoCodeItem.OldCode) + "'," + POut.Long(autoCodeItem.CodeNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            autoCodeItem.AutoCodeItemNum = Db.NonQ(command, true);
        } 
        return autoCodeItem.AutoCodeItemNum;
    }

    /**
    * Updates one AutoCodeItem in the database.
    */
    public static void update(AutoCodeItem autoCodeItem) throws Exception {
        String command = "UPDATE autocodeitem SET " + "AutoCodeNum    =  " + POut.Long(autoCodeItem.AutoCodeNum) + ", " + "OldCode        = '" + POut.String(autoCodeItem.OldCode) + "', " + "CodeNum        =  " + POut.Long(autoCodeItem.CodeNum) + " " + "WHERE AutoCodeItemNum = " + POut.Long(autoCodeItem.AutoCodeItemNum);
        Db.NonQ(command);
    }

    /**
    * Updates one AutoCodeItem in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(AutoCodeItem autoCodeItem, AutoCodeItem oldAutoCodeItem) throws Exception {
        String command = "";
        if (autoCodeItem.AutoCodeNum != oldAutoCodeItem.AutoCodeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AutoCodeNum = " + POut.Long(autoCodeItem.AutoCodeNum) + "";
        }
         
        if (autoCodeItem.OldCode != oldAutoCodeItem.OldCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OldCode = '" + POut.String(autoCodeItem.OldCode) + "'";
        }
         
        if (autoCodeItem.CodeNum != oldAutoCodeItem.CodeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CodeNum = " + POut.Long(autoCodeItem.CodeNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE autocodeitem SET " + command + " WHERE AutoCodeItemNum = " + POut.Long(autoCodeItem.AutoCodeItemNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one AutoCodeItem from the database.
    */
    public static void delete(long autoCodeItemNum) throws Exception {
        String command = "DELETE FROM autocodeitem " + "WHERE AutoCodeItemNum = " + POut.Long(autoCodeItemNum);
        Db.NonQ(command);
    }

}


