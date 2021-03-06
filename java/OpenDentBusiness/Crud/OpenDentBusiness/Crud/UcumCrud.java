//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:09 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class UcumCrud   
{
    /**
    * Gets one Ucum object from the database using the primary key.  Returns null if not found.
    */
    public static Ucum selectOne(long ucumNum) throws Exception {
        String command = "SELECT * FROM ucum " + "WHERE UcumNum = " + POut.Long(ucumNum);
        List<Ucum> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Ucum object from the database using a query.
    */
    public static Ucum selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Ucum> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Ucum objects from the database using a query.
    */
    public static List<Ucum> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Ucum> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Ucum> tableToList(DataTable table) throws Exception {
        List<Ucum> retVal = new List<Ucum>();
        Ucum ucum = new Ucum();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ucum = new Ucum();
            ucum.UcumNum = PIn.Long(table.Rows[i]["UcumNum"].ToString());
            ucum.UcumCode = PIn.String(table.Rows[i]["UcumCode"].ToString());
            ucum.Description = PIn.String(table.Rows[i]["Description"].ToString());
            ucum.IsInUse = PIn.Bool(table.Rows[i]["IsInUse"].ToString());
            retVal.Add(ucum);
        }
        return retVal;
    }

    /**
    * Inserts one Ucum into the database.  Returns the new priKey.
    */
    public static long insert(Ucum ucum) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            ucum.UcumNum = DbHelper.GetNextOracleKey("ucum", "UcumNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(ucum,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ucum.UcumNum++;
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
            return insert(ucum,false);
        } 
    }

    /**
    * Inserts one Ucum into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Ucum ucum, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            ucum.UcumNum = ReplicationServers.GetKey("ucum", "UcumNum");
        }
         
        String command = "INSERT INTO ucum (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "UcumNum,";
        }
         
        command += "UcumCode,Description,IsInUse) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(ucum.UcumNum) + ",";
        }
         
        command += "'" + POut.String(ucum.UcumCode) + "'," + "'" + POut.String(ucum.Description) + "'," + POut.Bool(ucum.IsInUse) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            ucum.UcumNum = Db.NonQ(command, true);
        } 
        return ucum.UcumNum;
    }

    /**
    * Updates one Ucum in the database.
    */
    public static void update(Ucum ucum) throws Exception {
        String command = "UPDATE ucum SET " + "UcumCode   = '" + POut.String(ucum.UcumCode) + "', " + "Description= '" + POut.String(ucum.Description) + "', " + "IsInUse    =  " + POut.Bool(ucum.IsInUse) + " " + "WHERE UcumNum = " + POut.Long(ucum.UcumNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Ucum in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Ucum ucum, Ucum oldUcum) throws Exception {
        String command = "";
        if (ucum.UcumCode != oldUcum.UcumCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UcumCode = '" + POut.String(ucum.UcumCode) + "'";
        }
         
        if (ucum.Description != oldUcum.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(ucum.Description) + "'";
        }
         
        if (ucum.IsInUse != oldUcum.IsInUse)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsInUse = " + POut.Bool(ucum.IsInUse) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ucum SET " + command + " WHERE UcumNum = " + POut.Long(ucum.UcumNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Ucum from the database.
    */
    public static void delete(long ucumNum) throws Exception {
        String command = "DELETE FROM ucum " + "WHERE UcumNum = " + POut.Long(ucumNum);
        Db.NonQ(command);
    }

}


