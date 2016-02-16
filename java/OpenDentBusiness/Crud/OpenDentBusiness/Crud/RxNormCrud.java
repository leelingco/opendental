//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:06 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class RxNormCrud   
{
    /**
    * Gets one RxNorm object from the database using the primary key.  Returns null if not found.
    */
    public static RxNorm selectOne(long rxNormNum) throws Exception {
        String command = "SELECT * FROM rxnorm " + "WHERE RxNormNum = " + POut.Long(rxNormNum);
        List<RxNorm> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one RxNorm object from the database using a query.
    */
    public static RxNorm selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<RxNorm> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of RxNorm objects from the database using a query.
    */
    public static List<RxNorm> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<RxNorm> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<RxNorm> tableToList(DataTable table) throws Exception {
        List<RxNorm> retVal = new List<RxNorm>();
        RxNorm rxNorm = new RxNorm();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            rxNorm = new RxNorm();
            rxNorm.RxNormNum = PIn.Long(table.Rows[i]["RxNormNum"].ToString());
            rxNorm.RxCui = PIn.String(table.Rows[i]["RxCui"].ToString());
            rxNorm.MmslCode = PIn.String(table.Rows[i]["MmslCode"].ToString());
            rxNorm.Description = PIn.String(table.Rows[i]["Description"].ToString());
            retVal.Add(rxNorm);
        }
        return retVal;
    }

    /**
    * Inserts one RxNorm into the database.  Returns the new priKey.
    */
    public static long insert(RxNorm rxNorm) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            rxNorm.RxNormNum = DbHelper.GetNextOracleKey("rxnorm", "RxNormNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(rxNorm,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        rxNorm.RxNormNum++;
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
            return insert(rxNorm,false);
        } 
    }

    /**
    * Inserts one RxNorm into the database.  Provides option to use the existing priKey.
    */
    public static long insert(RxNorm rxNorm, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            rxNorm.RxNormNum = ReplicationServers.GetKey("rxnorm", "RxNormNum");
        }
         
        String command = "INSERT INTO rxnorm (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "RxNormNum,";
        }
         
        command += "RxCui,MmslCode,Description) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(rxNorm.RxNormNum) + ",";
        }
         
        command += "'" + POut.String(rxNorm.RxCui) + "'," + "'" + POut.String(rxNorm.MmslCode) + "'," + "'" + POut.String(rxNorm.Description) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            rxNorm.RxNormNum = Db.NonQ(command, true);
        } 
        return rxNorm.RxNormNum;
    }

    /**
    * Updates one RxNorm in the database.
    */
    public static void update(RxNorm rxNorm) throws Exception {
        String command = "UPDATE rxnorm SET " + "RxCui      = '" + POut.String(rxNorm.RxCui) + "', " + "MmslCode   = '" + POut.String(rxNorm.MmslCode) + "', " + "Description= '" + POut.String(rxNorm.Description) + "' " + "WHERE RxNormNum = " + POut.Long(rxNorm.RxNormNum);
        Db.NonQ(command);
    }

    /**
    * Updates one RxNorm in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(RxNorm rxNorm, RxNorm oldRxNorm) throws Exception {
        String command = "";
        if (rxNorm.RxCui != oldRxNorm.RxCui)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RxCui = '" + POut.String(rxNorm.RxCui) + "'";
        }
         
        if (rxNorm.MmslCode != oldRxNorm.MmslCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MmslCode = '" + POut.String(rxNorm.MmslCode) + "'";
        }
         
        if (rxNorm.Description != oldRxNorm.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(rxNorm.Description) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE rxnorm SET " + command + " WHERE RxNormNum = " + POut.Long(rxNorm.RxNormNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one RxNorm from the database.
    */
    public static void delete(long rxNormNum) throws Exception {
        String command = "DELETE FROM rxnorm " + "WHERE RxNormNum = " + POut.Long(rxNormNum);
        Db.NonQ(command);
    }

}


