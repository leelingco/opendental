//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PlannedApptCrud   
{
    /**
    * Gets one PlannedAppt object from the database using the primary key.  Returns null if not found.
    */
    public static PlannedAppt selectOne(long plannedApptNum) throws Exception {
        String command = "SELECT * FROM plannedappt " + "WHERE PlannedApptNum = " + POut.Long(plannedApptNum);
        List<PlannedAppt> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one PlannedAppt object from the database using a query.
    */
    public static PlannedAppt selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PlannedAppt> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of PlannedAppt objects from the database using a query.
    */
    public static List<PlannedAppt> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PlannedAppt> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<PlannedAppt> tableToList(DataTable table) throws Exception {
        List<PlannedAppt> retVal = new List<PlannedAppt>();
        PlannedAppt plannedAppt = new PlannedAppt();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            plannedAppt = new PlannedAppt();
            plannedAppt.PlannedApptNum = PIn.Long(table.Rows[i]["PlannedApptNum"].ToString());
            plannedAppt.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            plannedAppt.AptNum = PIn.Long(table.Rows[i]["AptNum"].ToString());
            plannedAppt.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            retVal.Add(plannedAppt);
        }
        return retVal;
    }

    /**
    * Inserts one PlannedAppt into the database.  Returns the new priKey.
    */
    public static long insert(PlannedAppt plannedAppt) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            plannedAppt.PlannedApptNum = DbHelper.GetNextOracleKey("plannedappt", "PlannedApptNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(plannedAppt,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        plannedAppt.PlannedApptNum++;
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
            return insert(plannedAppt,false);
        } 
    }

    /**
    * Inserts one PlannedAppt into the database.  Provides option to use the existing priKey.
    */
    public static long insert(PlannedAppt plannedAppt, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            plannedAppt.PlannedApptNum = ReplicationServers.GetKey("plannedappt", "PlannedApptNum");
        }
         
        String command = "INSERT INTO plannedappt (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "PlannedApptNum,";
        }
         
        command += "PatNum,AptNum,ItemOrder) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(plannedAppt.PlannedApptNum) + ",";
        }
         
        command += POut.Long(plannedAppt.PatNum) + "," + POut.Long(plannedAppt.AptNum) + "," + POut.Int(plannedAppt.ItemOrder) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            plannedAppt.PlannedApptNum = Db.NonQ(command, true);
        } 
        return plannedAppt.PlannedApptNum;
    }

    /**
    * Updates one PlannedAppt in the database.
    */
    public static void update(PlannedAppt plannedAppt) throws Exception {
        String command = "UPDATE plannedappt SET " + "PatNum        =  " + POut.Long(plannedAppt.PatNum) + ", " + "AptNum        =  " + POut.Long(plannedAppt.AptNum) + ", " + "ItemOrder     =  " + POut.Int(plannedAppt.ItemOrder) + " " + "WHERE PlannedApptNum = " + POut.Long(plannedAppt.PlannedApptNum);
        Db.NonQ(command);
    }

    /**
    * Updates one PlannedAppt in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(PlannedAppt plannedAppt, PlannedAppt oldPlannedAppt) throws Exception {
        String command = "";
        if (plannedAppt.PatNum != oldPlannedAppt.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(plannedAppt.PatNum) + "";
        }
         
        if (plannedAppt.AptNum != oldPlannedAppt.AptNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AptNum = " + POut.Long(plannedAppt.AptNum) + "";
        }
         
        if (plannedAppt.ItemOrder != oldPlannedAppt.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.Int(plannedAppt.ItemOrder) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE plannedappt SET " + command + " WHERE PlannedApptNum = " + POut.Long(plannedAppt.PlannedApptNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one PlannedAppt from the database.
    */
    public static void delete(long plannedApptNum) throws Exception {
        String command = "DELETE FROM plannedappt " + "WHERE PlannedApptNum = " + POut.Long(plannedApptNum);
        Db.NonQ(command);
    }

}


