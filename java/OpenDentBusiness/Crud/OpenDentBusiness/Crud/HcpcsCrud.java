//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:01 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class HcpcsCrud   
{
    /**
    * Gets one Hcpcs object from the database using the primary key.  Returns null if not found.
    */
    public static Hcpcs selectOne(long hcpcsNum) throws Exception {
        String command = "SELECT * FROM hcpcs " + "WHERE HcpcsNum = " + POut.Long(hcpcsNum);
        List<Hcpcs> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Hcpcs object from the database using a query.
    */
    public static Hcpcs selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Hcpcs> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Hcpcs objects from the database using a query.
    */
    public static List<Hcpcs> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Hcpcs> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Hcpcs> tableToList(DataTable table) throws Exception {
        List<Hcpcs> retVal = new List<Hcpcs>();
        Hcpcs hcpcs = new Hcpcs();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            hcpcs = new Hcpcs();
            hcpcs.HcpcsNum = PIn.Long(table.Rows[i]["HcpcsNum"].ToString());
            hcpcs.HcpcsCode = PIn.String(table.Rows[i]["HcpcsCode"].ToString());
            hcpcs.DescriptionShort = PIn.String(table.Rows[i]["DescriptionShort"].ToString());
            retVal.Add(hcpcs);
        }
        return retVal;
    }

    /**
    * Inserts one Hcpcs into the database.  Returns the new priKey.
    */
    public static long insert(Hcpcs hcpcs) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            hcpcs.HcpcsNum = DbHelper.GetNextOracleKey("hcpcs", "HcpcsNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(hcpcs,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        hcpcs.HcpcsNum++;
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
            return insert(hcpcs,false);
        } 
    }

    /**
    * Inserts one Hcpcs into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Hcpcs hcpcs, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            hcpcs.HcpcsNum = ReplicationServers.GetKey("hcpcs", "HcpcsNum");
        }
         
        String command = "INSERT INTO hcpcs (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "HcpcsNum,";
        }
         
        command += "HcpcsCode,DescriptionShort) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(hcpcs.HcpcsNum) + ",";
        }
         
        command += "'" + POut.String(hcpcs.HcpcsCode) + "'," + "'" + POut.String(hcpcs.DescriptionShort) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            hcpcs.HcpcsNum = Db.NonQ(command, true);
        } 
        return hcpcs.HcpcsNum;
    }

    /**
    * Updates one Hcpcs in the database.
    */
    public static void update(Hcpcs hcpcs) throws Exception {
        String command = "UPDATE hcpcs SET " + "HcpcsCode       = '" + POut.String(hcpcs.HcpcsCode) + "', " + "DescriptionShort= '" + POut.String(hcpcs.DescriptionShort) + "' " + "WHERE HcpcsNum = " + POut.Long(hcpcs.HcpcsNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Hcpcs in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Hcpcs hcpcs, Hcpcs oldHcpcs) throws Exception {
        String command = "";
        if (hcpcs.HcpcsCode != oldHcpcs.HcpcsCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HcpcsCode = '" + POut.String(hcpcs.HcpcsCode) + "'";
        }
         
        if (hcpcs.DescriptionShort != oldHcpcs.DescriptionShort)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DescriptionShort = '" + POut.String(hcpcs.DescriptionShort) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE hcpcs SET " + command + " WHERE HcpcsNum = " + POut.Long(hcpcs.HcpcsNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Hcpcs from the database.
    */
    public static void delete(long hcpcsNum) throws Exception {
        String command = "DELETE FROM hcpcs " + "WHERE HcpcsNum = " + POut.Long(hcpcsNum);
        Db.NonQ(command);
    }

}


