//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DictCustomCrud   
{
    /**
    * Gets one DictCustom object from the database using the primary key.  Returns null if not found.
    */
    public static DictCustom selectOne(long dictCustomNum) throws Exception {
        String command = "SELECT * FROM dictcustom " + "WHERE DictCustomNum = " + POut.Long(dictCustomNum);
        List<DictCustom> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one DictCustom object from the database using a query.
    */
    public static DictCustom selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DictCustom> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of DictCustom objects from the database using a query.
    */
    public static List<DictCustom> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<DictCustom> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<DictCustom> tableToList(DataTable table) throws Exception {
        List<DictCustom> retVal = new List<DictCustom>();
        DictCustom dictCustom = new DictCustom();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            dictCustom = new DictCustom();
            dictCustom.DictCustomNum = PIn.Long(table.Rows[i]["DictCustomNum"].ToString());
            dictCustom.WordText = PIn.String(table.Rows[i]["WordText"].ToString());
            retVal.Add(dictCustom);
        }
        return retVal;
    }

    /**
    * Inserts one DictCustom into the database.  Returns the new priKey.
    */
    public static long insert(DictCustom dictCustom) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            dictCustom.DictCustomNum = DbHelper.GetNextOracleKey("dictcustom", "DictCustomNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(dictCustom,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        dictCustom.DictCustomNum++;
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
            return insert(dictCustom,false);
        } 
    }

    /**
    * Inserts one DictCustom into the database.  Provides option to use the existing priKey.
    */
    public static long insert(DictCustom dictCustom, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            dictCustom.DictCustomNum = ReplicationServers.GetKey("dictcustom", "DictCustomNum");
        }
         
        String command = "INSERT INTO dictcustom (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "DictCustomNum,";
        }
         
        command += "WordText) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(dictCustom.DictCustomNum) + ",";
        }
         
        command += "'" + POut.String(dictCustom.WordText) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            dictCustom.DictCustomNum = Db.NonQ(command, true);
        } 
        return dictCustom.DictCustomNum;
    }

    /**
    * Updates one DictCustom in the database.
    */
    public static void update(DictCustom dictCustom) throws Exception {
        String command = "UPDATE dictcustom SET " + "WordText     = '" + POut.String(dictCustom.WordText) + "' " + "WHERE DictCustomNum = " + POut.Long(dictCustom.DictCustomNum);
        Db.NonQ(command);
    }

    /**
    * Updates one DictCustom in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(DictCustom dictCustom, DictCustom oldDictCustom) throws Exception {
        String command = "";
        if (dictCustom.WordText != oldDictCustom.WordText)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "WordText = '" + POut.String(dictCustom.WordText) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE dictcustom SET " + command + " WHERE DictCustomNum = " + POut.Long(dictCustom.DictCustomNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one DictCustom from the database.
    */
    public static void delete(long dictCustomNum) throws Exception {
        String command = "DELETE FROM dictcustom " + "WHERE DictCustomNum = " + POut.Long(dictCustomNum);
        Db.NonQ(command);
    }

}


