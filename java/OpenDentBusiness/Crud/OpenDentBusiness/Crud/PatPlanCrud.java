//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PatPlanCrud   
{
    /**
    * Gets one PatPlan object from the database using the primary key.  Returns null if not found.
    */
    public static PatPlan selectOne(long patPlanNum) throws Exception {
        String command = "SELECT * FROM patplan " + "WHERE PatPlanNum = " + POut.Long(patPlanNum);
        List<PatPlan> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one PatPlan object from the database using a query.
    */
    public static PatPlan selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PatPlan> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of PatPlan objects from the database using a query.
    */
    public static List<PatPlan> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PatPlan> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<PatPlan> tableToList(DataTable table) throws Exception {
        List<PatPlan> retVal = new List<PatPlan>();
        PatPlan patPlan = new PatPlan();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            patPlan = new PatPlan();
            patPlan.PatPlanNum = PIn.Long(table.Rows[i]["PatPlanNum"].ToString());
            patPlan.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            patPlan.Ordinal = PIn.Byte(table.Rows[i]["Ordinal"].ToString());
            patPlan.IsPending = PIn.Bool(table.Rows[i]["IsPending"].ToString());
            patPlan.Relationship = (Relat)PIn.Int(table.Rows[i]["Relationship"].ToString());
            patPlan.PatID = PIn.String(table.Rows[i]["PatID"].ToString());
            patPlan.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
            retVal.Add(patPlan);
        }
        return retVal;
    }

    /**
    * Inserts one PatPlan into the database.  Returns the new priKey.
    */
    public static long insert(PatPlan patPlan) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            patPlan.PatPlanNum = DbHelper.GetNextOracleKey("patplan", "PatPlanNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(patPlan,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        patPlan.PatPlanNum++;
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
            return insert(patPlan,false);
        } 
    }

    /**
    * Inserts one PatPlan into the database.  Provides option to use the existing priKey.
    */
    public static long insert(PatPlan patPlan, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            patPlan.PatPlanNum = ReplicationServers.GetKey("patplan", "PatPlanNum");
        }
         
        String command = "INSERT INTO patplan (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "PatPlanNum,";
        }
         
        command += "PatNum,Ordinal,IsPending,Relationship,PatID,InsSubNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(patPlan.PatPlanNum) + ",";
        }
         
        command += POut.Long(patPlan.PatNum) + "," + POut.Byte(patPlan.Ordinal) + "," + POut.Bool(patPlan.IsPending) + "," + POut.Int((int)patPlan.Relationship) + "," + "'" + POut.String(patPlan.PatID) + "'," + POut.Long(patPlan.InsSubNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            patPlan.PatPlanNum = Db.NonQ(command, true);
        } 
        return patPlan.PatPlanNum;
    }

    /**
    * Updates one PatPlan in the database.
    */
    public static void update(PatPlan patPlan) throws Exception {
        String command = "UPDATE patplan SET " + "PatNum      =  " + POut.Long(patPlan.PatNum) + ", " + "Ordinal     =  " + POut.Byte(patPlan.Ordinal) + ", " + "IsPending   =  " + POut.Bool(patPlan.IsPending) + ", " + "Relationship=  " + POut.Int((int)patPlan.Relationship) + ", " + "PatID       = '" + POut.String(patPlan.PatID) + "', " + "InsSubNum   =  " + POut.Long(patPlan.InsSubNum) + " " + "WHERE PatPlanNum = " + POut.Long(patPlan.PatPlanNum);
        Db.NonQ(command);
    }

    /**
    * Updates one PatPlan in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(PatPlan patPlan, PatPlan oldPatPlan) throws Exception {
        String command = "";
        if (patPlan.PatNum != oldPatPlan.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(patPlan.PatNum) + "";
        }
         
        if (patPlan.Ordinal != oldPatPlan.Ordinal)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Ordinal = " + POut.Byte(patPlan.Ordinal) + "";
        }
         
        if (patPlan.IsPending != oldPatPlan.IsPending)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsPending = " + POut.Bool(patPlan.IsPending) + "";
        }
         
        if (patPlan.Relationship != oldPatPlan.Relationship)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Relationship = " + POut.Int((int)patPlan.Relationship) + "";
        }
         
        if (patPlan.PatID != oldPatPlan.PatID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatID = '" + POut.String(patPlan.PatID) + "'";
        }
         
        if (patPlan.InsSubNum != oldPatPlan.InsSubNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InsSubNum = " + POut.Long(patPlan.InsSubNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE patplan SET " + command + " WHERE PatPlanNum = " + POut.Long(patPlan.PatPlanNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one PatPlan from the database.
    */
    public static void delete(long patPlanNum) throws Exception {
        String command = "DELETE FROM patplan " + "WHERE PatPlanNum = " + POut.Long(patPlanNum);
        Db.NonQ(command);
    }

}


