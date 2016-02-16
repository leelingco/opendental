//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class ElectIDCrud   
{
    /**
    * Gets one ElectID object from the database using the primary key.  Returns null if not found.
    */
    public static ElectID selectOne(long electIDNum) throws Exception {
        String command = "SELECT * FROM electid " + "WHERE ElectIDNum = " + POut.Long(electIDNum);
        List<ElectID> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ElectID object from the database using a query.
    */
    public static ElectID selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ElectID> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ElectID objects from the database using a query.
    */
    public static List<ElectID> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ElectID> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ElectID> tableToList(DataTable table) throws Exception {
        List<ElectID> retVal = new List<ElectID>();
        ElectID electID = new ElectID();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            electID = new ElectID();
            electID.ElectIDNum = PIn.Long(table.Rows[i]["ElectIDNum"].ToString());
            electID.PayorID = PIn.String(table.Rows[i]["PayorID"].ToString());
            electID.CarrierName = PIn.String(table.Rows[i]["CarrierName"].ToString());
            electID.IsMedicaid = PIn.Bool(table.Rows[i]["IsMedicaid"].ToString());
            electID.ProviderTypes = PIn.String(table.Rows[i]["ProviderTypes"].ToString());
            electID.Comments = PIn.String(table.Rows[i]["Comments"].ToString());
            retVal.Add(electID);
        }
        return retVal;
    }

    /**
    * Inserts one ElectID into the database.  Returns the new priKey.
    */
    public static long insert(ElectID electID) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            electID.ElectIDNum = DbHelper.GetNextOracleKey("electid", "ElectIDNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(electID,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        electID.ElectIDNum++;
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
            return insert(electID,false);
        } 
    }

    /**
    * Inserts one ElectID into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ElectID electID, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            electID.ElectIDNum = ReplicationServers.GetKey("electid", "ElectIDNum");
        }
         
        String command = "INSERT INTO electid (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "ElectIDNum,";
        }
         
        command += "PayorID,CarrierName,IsMedicaid,ProviderTypes,Comments) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(electID.ElectIDNum) + ",";
        }
         
        command += "'" + POut.String(electID.PayorID) + "'," + "'" + POut.String(electID.CarrierName) + "'," + POut.Bool(electID.IsMedicaid) + "," + "'" + POut.String(electID.ProviderTypes) + "'," + "'" + POut.String(electID.Comments) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            electID.ElectIDNum = Db.NonQ(command, true);
        } 
        return electID.ElectIDNum;
    }

    /**
    * Updates one ElectID in the database.
    */
    public static void update(ElectID electID) throws Exception {
        String command = "UPDATE electid SET " + "PayorID      = '" + POut.String(electID.PayorID) + "', " + "CarrierName  = '" + POut.String(electID.CarrierName) + "', " + "IsMedicaid   =  " + POut.Bool(electID.IsMedicaid) + ", " + "ProviderTypes= '" + POut.String(electID.ProviderTypes) + "', " + "Comments     = '" + POut.String(electID.Comments) + "' " + "WHERE ElectIDNum = " + POut.Long(electID.ElectIDNum);
        Db.NonQ(command);
    }

    /**
    * Updates one ElectID in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ElectID electID, ElectID oldElectID) throws Exception {
        String command = "";
        if (electID.PayorID != oldElectID.PayorID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayorID = '" + POut.String(electID.PayorID) + "'";
        }
         
        if (electID.CarrierName != oldElectID.CarrierName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CarrierName = '" + POut.String(electID.CarrierName) + "'";
        }
         
        if (electID.IsMedicaid != oldElectID.IsMedicaid)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsMedicaid = " + POut.Bool(electID.IsMedicaid) + "";
        }
         
        if (electID.ProviderTypes != oldElectID.ProviderTypes)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProviderTypes = '" + POut.String(electID.ProviderTypes) + "'";
        }
         
        if (electID.Comments != oldElectID.Comments)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Comments = '" + POut.String(electID.Comments) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE electid SET " + command + " WHERE ElectIDNum = " + POut.Long(electID.ElectIDNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one ElectID from the database.
    */
    public static void delete(long electIDNum) throws Exception {
        String command = "DELETE FROM electid " + "WHERE ElectIDNum = " + POut.Long(electIDNum);
        Db.NonQ(command);
    }

}

