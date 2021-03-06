//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:07 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class SecurityLogHashCrud   
{
    /**
    * Gets one SecurityLogHash object from the database using the primary key.  Returns null if not found.
    */
    public static SecurityLogHash selectOne(long securityLogHashNum) throws Exception {
        String command = "SELECT * FROM securityloghash " + "WHERE SecurityLogHashNum = " + POut.Long(securityLogHashNum);
        List<SecurityLogHash> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one SecurityLogHash object from the database using a query.
    */
    public static SecurityLogHash selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<SecurityLogHash> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of SecurityLogHash objects from the database using a query.
    */
    public static List<SecurityLogHash> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<SecurityLogHash> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<SecurityLogHash> tableToList(DataTable table) throws Exception {
        List<SecurityLogHash> retVal = new List<SecurityLogHash>();
        SecurityLogHash securityLogHash = new SecurityLogHash();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            securityLogHash = new SecurityLogHash();
            securityLogHash.SecurityLogHashNum = PIn.Long(table.Rows[i]["SecurityLogHashNum"].ToString());
            securityLogHash.SecurityLogNum = PIn.Long(table.Rows[i]["SecurityLogNum"].ToString());
            securityLogHash.LogHash = PIn.String(table.Rows[i]["LogHash"].ToString());
            retVal.Add(securityLogHash);
        }
        return retVal;
    }

    /**
    * Inserts one SecurityLogHash into the database.  Returns the new priKey.
    */
    public static long insert(SecurityLogHash securityLogHash) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            securityLogHash.SecurityLogHashNum = DbHelper.GetNextOracleKey("securityloghash", "SecurityLogHashNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(securityLogHash,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        securityLogHash.SecurityLogHashNum++;
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
            return insert(securityLogHash,false);
        } 
    }

    /**
    * Inserts one SecurityLogHash into the database.  Provides option to use the existing priKey.
    */
    public static long insert(SecurityLogHash securityLogHash, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            securityLogHash.SecurityLogHashNum = ReplicationServers.GetKey("securityloghash", "SecurityLogHashNum");
        }
         
        String command = "INSERT INTO securityloghash (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "SecurityLogHashNum,";
        }
         
        command += "SecurityLogNum,LogHash) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(securityLogHash.SecurityLogHashNum) + ",";
        }
         
        command += POut.Long(securityLogHash.SecurityLogNum) + "," + "'" + POut.String(securityLogHash.LogHash) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            securityLogHash.SecurityLogHashNum = Db.NonQ(command, true);
        } 
        return securityLogHash.SecurityLogHashNum;
    }

    /**
    * Updates one SecurityLogHash in the database.
    */
    public static void update(SecurityLogHash securityLogHash) throws Exception {
        String command = "UPDATE securityloghash SET " + "SecurityLogNum    =  " + POut.Long(securityLogHash.SecurityLogNum) + ", " + "LogHash           = '" + POut.String(securityLogHash.LogHash) + "' " + "WHERE SecurityLogHashNum = " + POut.Long(securityLogHash.SecurityLogHashNum);
        Db.NonQ(command);
    }

    /**
    * Updates one SecurityLogHash in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(SecurityLogHash securityLogHash, SecurityLogHash oldSecurityLogHash) throws Exception {
        String command = "";
        if (securityLogHash.SecurityLogNum != oldSecurityLogHash.SecurityLogNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SecurityLogNum = " + POut.Long(securityLogHash.SecurityLogNum) + "";
        }
         
        if (securityLogHash.LogHash != oldSecurityLogHash.LogHash)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LogHash = '" + POut.String(securityLogHash.LogHash) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE securityloghash SET " + command + " WHERE SecurityLogHashNum = " + POut.Long(securityLogHash.SecurityLogHashNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one SecurityLogHash from the database.
    */
    public static void delete(long securityLogHashNum) throws Exception {
        String command = "DELETE FROM securityloghash " + "WHERE SecurityLogHashNum = " + POut.Long(securityLogHashNum);
        Db.NonQ(command);
    }

}


