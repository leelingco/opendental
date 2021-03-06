//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:07 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class SnomedCrud   
{
    /**
    * Gets one Snomed object from the database using the primary key.  Returns null if not found.
    */
    public static Snomed selectOne(long snomedNum) throws Exception {
        String command = "SELECT * FROM snomed " + "WHERE SnomedNum = " + POut.Long(snomedNum);
        List<Snomed> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Snomed object from the database using a query.
    */
    public static Snomed selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Snomed> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Snomed objects from the database using a query.
    */
    public static List<Snomed> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Snomed> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Snomed> tableToList(DataTable table) throws Exception {
        List<Snomed> retVal = new List<Snomed>();
        Snomed snomed = new Snomed();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            snomed = new Snomed();
            snomed.SnomedNum = PIn.Long(table.Rows[i]["SnomedNum"].ToString());
            snomed.SnomedCode = PIn.String(table.Rows[i]["SnomedCode"].ToString());
            snomed.Description = PIn.String(table.Rows[i]["Description"].ToString());
            retVal.Add(snomed);
        }
        return retVal;
    }

    /**
    * Inserts one Snomed into the database.  Returns the new priKey.
    */
    public static long insert(Snomed snomed) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            snomed.SnomedNum = DbHelper.GetNextOracleKey("snomed", "SnomedNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(snomed,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        snomed.SnomedNum++;
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
            return insert(snomed,false);
        } 
    }

    /**
    * Inserts one Snomed into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Snomed snomed, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            snomed.SnomedNum = ReplicationServers.GetKey("snomed", "SnomedNum");
        }
         
        String command = "INSERT INTO snomed (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "SnomedNum,";
        }
         
        command += "SnomedCode,Description) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(snomed.SnomedNum) + ",";
        }
         
        command += "'" + POut.String(snomed.SnomedCode) + "'," + "'" + POut.String(snomed.Description) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            snomed.SnomedNum = Db.NonQ(command, true);
        } 
        return snomed.SnomedNum;
    }

    /**
    * Updates one Snomed in the database.
    */
    public static void update(Snomed snomed) throws Exception {
        String command = "UPDATE snomed SET " + "SnomedCode = '" + POut.String(snomed.SnomedCode) + "', " + "Description= '" + POut.String(snomed.Description) + "' " + "WHERE SnomedNum = " + POut.Long(snomed.SnomedNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Snomed in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Snomed snomed, Snomed oldSnomed) throws Exception {
        String command = "";
        if (snomed.SnomedCode != oldSnomed.SnomedCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SnomedCode = '" + POut.String(snomed.SnomedCode) + "'";
        }
         
        if (snomed.Description != oldSnomed.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(snomed.Description) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE snomed SET " + command + " WHERE SnomedNum = " + POut.Long(snomed.SnomedNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Snomed from the database.
    */
    public static void delete(long snomedNum) throws Exception {
        String command = "DELETE FROM snomed " + "WHERE SnomedNum = " + POut.Long(snomedNum);
        Db.NonQ(command);
    }

}


