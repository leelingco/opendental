//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:56 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CanadianNetworkCrud   
{
    /**
    * Gets one CanadianNetwork object from the database using the primary key.  Returns null if not found.
    */
    public static CanadianNetwork selectOne(long canadianNetworkNum) throws Exception {
        String command = "SELECT * FROM canadiannetwork " + "WHERE CanadianNetworkNum = " + POut.Long(canadianNetworkNum);
        List<CanadianNetwork> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one CanadianNetwork object from the database using a query.
    */
    public static CanadianNetwork selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CanadianNetwork> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of CanadianNetwork objects from the database using a query.
    */
    public static List<CanadianNetwork> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CanadianNetwork> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<CanadianNetwork> tableToList(DataTable table) throws Exception {
        List<CanadianNetwork> retVal = new List<CanadianNetwork>();
        CanadianNetwork canadianNetwork = new CanadianNetwork();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            canadianNetwork = new CanadianNetwork();
            canadianNetwork.CanadianNetworkNum = PIn.Long(table.Rows[i]["CanadianNetworkNum"].ToString());
            canadianNetwork.Abbrev = PIn.String(table.Rows[i]["Abbrev"].ToString());
            canadianNetwork.Descript = PIn.String(table.Rows[i]["Descript"].ToString());
            canadianNetwork.CanadianTransactionPrefix = PIn.String(table.Rows[i]["CanadianTransactionPrefix"].ToString());
            canadianNetwork.CanadianIsRprHandler = PIn.Bool(table.Rows[i]["CanadianIsRprHandler"].ToString());
            retVal.Add(canadianNetwork);
        }
        return retVal;
    }

    /**
    * Inserts one CanadianNetwork into the database.  Returns the new priKey.
    */
    public static long insert(CanadianNetwork canadianNetwork) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            canadianNetwork.CanadianNetworkNum = DbHelper.GetNextOracleKey("canadiannetwork", "CanadianNetworkNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(canadianNetwork,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        canadianNetwork.CanadianNetworkNum++;
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
            return insert(canadianNetwork,false);
        } 
    }

    /**
    * Inserts one CanadianNetwork into the database.  Provides option to use the existing priKey.
    */
    public static long insert(CanadianNetwork canadianNetwork, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            canadianNetwork.CanadianNetworkNum = ReplicationServers.GetKey("canadiannetwork", "CanadianNetworkNum");
        }
         
        String command = "INSERT INTO canadiannetwork (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "CanadianNetworkNum,";
        }
         
        command += "Abbrev,Descript,CanadianTransactionPrefix,CanadianIsRprHandler) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(canadianNetwork.CanadianNetworkNum) + ",";
        }
         
        command += "'" + POut.String(canadianNetwork.Abbrev) + "'," + "'" + POut.String(canadianNetwork.Descript) + "'," + "'" + POut.String(canadianNetwork.CanadianTransactionPrefix) + "'," + POut.Bool(canadianNetwork.CanadianIsRprHandler) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            canadianNetwork.CanadianNetworkNum = Db.NonQ(command, true);
        } 
        return canadianNetwork.CanadianNetworkNum;
    }

    /**
    * Updates one CanadianNetwork in the database.
    */
    public static void update(CanadianNetwork canadianNetwork) throws Exception {
        String command = "UPDATE canadiannetwork SET " + "Abbrev                   = '" + POut.String(canadianNetwork.Abbrev) + "', " + "Descript                 = '" + POut.String(canadianNetwork.Descript) + "', " + "CanadianTransactionPrefix= '" + POut.String(canadianNetwork.CanadianTransactionPrefix) + "', " + "CanadianIsRprHandler     =  " + POut.Bool(canadianNetwork.CanadianIsRprHandler) + " " + "WHERE CanadianNetworkNum = " + POut.Long(canadianNetwork.CanadianNetworkNum);
        Db.NonQ(command);
    }

    /**
    * Updates one CanadianNetwork in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(CanadianNetwork canadianNetwork, CanadianNetwork oldCanadianNetwork) throws Exception {
        String command = "";
        if (canadianNetwork.Abbrev != oldCanadianNetwork.Abbrev)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Abbrev = '" + POut.String(canadianNetwork.Abbrev) + "'";
        }
         
        if (canadianNetwork.Descript != oldCanadianNetwork.Descript)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Descript = '" + POut.String(canadianNetwork.Descript) + "'";
        }
         
        if (canadianNetwork.CanadianTransactionPrefix != oldCanadianNetwork.CanadianTransactionPrefix)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CanadianTransactionPrefix = '" + POut.String(canadianNetwork.CanadianTransactionPrefix) + "'";
        }
         
        if (canadianNetwork.CanadianIsRprHandler != oldCanadianNetwork.CanadianIsRprHandler)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CanadianIsRprHandler = " + POut.Bool(canadianNetwork.CanadianIsRprHandler) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE canadiannetwork SET " + command + " WHERE CanadianNetworkNum = " + POut.Long(canadianNetwork.CanadianNetworkNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one CanadianNetwork from the database.
    */
    public static void delete(long canadianNetworkNum) throws Exception {
        String command = "DELETE FROM canadiannetwork " + "WHERE CanadianNetworkNum = " + POut.Long(canadianNetworkNum);
        Db.NonQ(command);
    }

}


