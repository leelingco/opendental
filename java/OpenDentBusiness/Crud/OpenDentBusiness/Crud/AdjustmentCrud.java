//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:55 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class AdjustmentCrud   
{
    /**
    * Gets one Adjustment object from the database using the primary key.  Returns null if not found.
    */
    public static Adjustment selectOne(long adjNum) throws Exception {
        String command = "SELECT * FROM adjustment " + "WHERE AdjNum = " + POut.Long(adjNum);
        List<Adjustment> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Adjustment object from the database using a query.
    */
    public static Adjustment selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Adjustment> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Adjustment objects from the database using a query.
    */
    public static List<Adjustment> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Adjustment> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Adjustment> tableToList(DataTable table) throws Exception {
        List<Adjustment> retVal = new List<Adjustment>();
        Adjustment adjustment = new Adjustment();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            adjustment = new Adjustment();
            adjustment.AdjNum = PIn.Long(table.Rows[i]["AdjNum"].ToString());
            adjustment.AdjDate = PIn.Date(table.Rows[i]["AdjDate"].ToString());
            adjustment.AdjAmt = PIn.Double(table.Rows[i]["AdjAmt"].ToString());
            adjustment.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            adjustment.AdjType = PIn.Long(table.Rows[i]["AdjType"].ToString());
            adjustment.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            adjustment.AdjNote = PIn.String(table.Rows[i]["AdjNote"].ToString());
            adjustment.ProcDate = PIn.Date(table.Rows[i]["ProcDate"].ToString());
            adjustment.ProcNum = PIn.Long(table.Rows[i]["ProcNum"].ToString());
            adjustment.DateEntry = PIn.Date(table.Rows[i]["DateEntry"].ToString());
            adjustment.ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
            adjustment.StatementNum = PIn.Long(table.Rows[i]["StatementNum"].ToString());
            retVal.Add(adjustment);
        }
        return retVal;
    }

    /**
    * Inserts one Adjustment into the database.  Returns the new priKey.
    */
    public static long insert(Adjustment adjustment) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            adjustment.AdjNum = DbHelper.GetNextOracleKey("adjustment", "AdjNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(adjustment,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        adjustment.AdjNum++;
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
            return insert(adjustment,false);
        } 
    }

    /**
    * Inserts one Adjustment into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Adjustment adjustment, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            adjustment.AdjNum = ReplicationServers.GetKey("adjustment", "AdjNum");
        }
         
        String command = "INSERT INTO adjustment (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "AdjNum,";
        }
         
        command += "AdjDate,AdjAmt,PatNum,AdjType,ProvNum,AdjNote,ProcDate,ProcNum,DateEntry,ClinicNum,StatementNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(adjustment.AdjNum) + ",";
        }
         
        command += POut.Date(adjustment.AdjDate) + "," + "'" + POut.Double(adjustment.AdjAmt) + "'," + POut.Long(adjustment.PatNum) + "," + POut.Long(adjustment.AdjType) + "," + POut.Long(adjustment.ProvNum) + "," + "'" + POut.String(adjustment.AdjNote) + "'," + POut.Date(adjustment.ProcDate) + "," + POut.Long(adjustment.ProcNum) + "," + DbHelper.Now() + "," + POut.Long(adjustment.ClinicNum) + "," + POut.Long(adjustment.StatementNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            adjustment.AdjNum = Db.NonQ(command, true);
        } 
        return adjustment.AdjNum;
    }

    /**
    * Updates one Adjustment in the database.
    */
    public static void update(Adjustment adjustment) throws Exception {
        //DateEntry not allowed to change
        String command = "UPDATE adjustment SET " + "AdjDate     =  " + POut.Date(adjustment.AdjDate) + ", " + "AdjAmt      = '" + POut.Double(adjustment.AdjAmt) + "', " + "PatNum      =  " + POut.Long(adjustment.PatNum) + ", " + "AdjType     =  " + POut.Long(adjustment.AdjType) + ", " + "ProvNum     =  " + POut.Long(adjustment.ProvNum) + ", " + "AdjNote     = '" + POut.String(adjustment.AdjNote) + "', " + "ProcDate    =  " + POut.Date(adjustment.ProcDate) + ", " + "ProcNum     =  " + POut.Long(adjustment.ProcNum) + ", " + "ClinicNum   =  " + POut.Long(adjustment.ClinicNum) + ", " + "StatementNum=  " + POut.Long(adjustment.StatementNum) + " " + "WHERE AdjNum = " + POut.Long(adjustment.AdjNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Adjustment in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Adjustment adjustment, Adjustment oldAdjustment) throws Exception {
        String command = "";
        if (adjustment.AdjDate != oldAdjustment.AdjDate)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AdjDate = " + POut.Date(adjustment.AdjDate) + "";
        }
         
        if (adjustment.AdjAmt != oldAdjustment.AdjAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AdjAmt = '" + POut.Double(adjustment.AdjAmt) + "'";
        }
         
        if (adjustment.PatNum != oldAdjustment.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(adjustment.PatNum) + "";
        }
         
        if (adjustment.AdjType != oldAdjustment.AdjType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AdjType = " + POut.Long(adjustment.AdjType) + "";
        }
         
        if (adjustment.ProvNum != oldAdjustment.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.Long(adjustment.ProvNum) + "";
        }
         
        if (adjustment.AdjNote != oldAdjustment.AdjNote)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AdjNote = '" + POut.String(adjustment.AdjNote) + "'";
        }
         
        if (adjustment.ProcDate != oldAdjustment.ProcDate)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProcDate = " + POut.Date(adjustment.ProcDate) + "";
        }
         
        if (adjustment.ProcNum != oldAdjustment.ProcNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProcNum = " + POut.Long(adjustment.ProcNum) + "";
        }
         
        //DateEntry not allowed to change
        if (adjustment.ClinicNum != oldAdjustment.ClinicNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClinicNum = " + POut.Long(adjustment.ClinicNum) + "";
        }
         
        if (adjustment.StatementNum != oldAdjustment.StatementNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "StatementNum = " + POut.Long(adjustment.StatementNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE adjustment SET " + command + " WHERE AdjNum = " + POut.Long(adjustment.AdjNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Adjustment from the database.
    */
    public static void delete(long adjNum) throws Exception {
        String command = "DELETE FROM adjustment " + "WHERE AdjNum = " + POut.Long(adjNum);
        Db.NonQ(command);
    }

}

