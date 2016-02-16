//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class RecallCrud   
{
    /**
    * Gets one Recall object from the database using the primary key.  Returns null if not found.
    */
    public static Recall selectOne(long recallNum) throws Exception {
        String command = "SELECT * FROM recall " + "WHERE RecallNum = " + POut.Long(recallNum);
        List<Recall> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Recall object from the database using a query.
    */
    public static Recall selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Recall> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Recall objects from the database using a query.
    */
    public static List<Recall> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Recall> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Recall> tableToList(DataTable table) throws Exception {
        List<Recall> retVal = new List<Recall>();
        Recall recall = new Recall();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            recall = new Recall();
            recall.RecallNum = PIn.Long(table.Rows[i]["RecallNum"].ToString());
            recall.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            recall.DateDueCalc = PIn.Date(table.Rows[i]["DateDueCalc"].ToString());
            recall.DateDue = PIn.Date(table.Rows[i]["DateDue"].ToString());
            recall.DatePrevious = PIn.Date(table.Rows[i]["DatePrevious"].ToString());
            recall.RecallInterval = new Interval(PIn.Int(table.Rows[i]["RecallInterval"].ToString()));
            recall.RecallStatus = PIn.Long(table.Rows[i]["RecallStatus"].ToString());
            recall.Note = PIn.String(table.Rows[i]["Note"].ToString());
            recall.IsDisabled = PIn.Bool(table.Rows[i]["IsDisabled"].ToString());
            recall.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            recall.RecallTypeNum = PIn.Long(table.Rows[i]["RecallTypeNum"].ToString());
            recall.DisableUntilBalance = PIn.Double(table.Rows[i]["DisableUntilBalance"].ToString());
            recall.DisableUntilDate = PIn.Date(table.Rows[i]["DisableUntilDate"].ToString());
            recall.DateScheduled = PIn.Date(table.Rows[i]["DateScheduled"].ToString());
            retVal.Add(recall);
        }
        return retVal;
    }

    /**
    * Inserts one Recall into the database.  Returns the new priKey.
    */
    public static long insert(Recall recall) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            recall.RecallNum = DbHelper.GetNextOracleKey("recall", "RecallNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(recall,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        recall.RecallNum++;
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
            return insert(recall,false);
        } 
    }

    /**
    * Inserts one Recall into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Recall recall, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            recall.RecallNum = ReplicationServers.GetKey("recall", "RecallNum");
        }
         
        String command = "INSERT INTO recall (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "RecallNum,";
        }
         
        command += "PatNum,DateDueCalc,DateDue,DatePrevious,RecallInterval,RecallStatus,Note,IsDisabled,RecallTypeNum,DisableUntilBalance,DisableUntilDate,DateScheduled) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(recall.RecallNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += POut.Long(recall.PatNum) + "," + POut.Date(recall.DateDueCalc) + "," + POut.Date(recall.DateDue) + "," + POut.Date(recall.DatePrevious) + "," + POut.Int(recall.RecallInterval.ToInt()) + "," + POut.Long(recall.RecallStatus) + "," + "'" + POut.String(recall.Note) + "'," + POut.Bool(recall.IsDisabled) + "," + POut.Long(recall.RecallTypeNum) + "," + "'" + POut.Double(recall.DisableUntilBalance) + "'," + POut.Date(recall.DisableUntilDate) + "," + POut.Date(recall.DateScheduled) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            recall.RecallNum = Db.NonQ(command, true);
        } 
        return recall.RecallNum;
    }

    /**
    * Updates one Recall in the database.
    */
    public static void update(Recall recall) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE recall SET " + "PatNum             =  " + POut.Long(recall.PatNum) + ", " + "DateDueCalc        =  " + POut.Date(recall.DateDueCalc) + ", " + "DateDue            =  " + POut.Date(recall.DateDue) + ", " + "DatePrevious       =  " + POut.Date(recall.DatePrevious) + ", " + "RecallInterval     =  " + POut.Int(recall.RecallInterval.ToInt()) + ", " + "RecallStatus       =  " + POut.Long(recall.RecallStatus) + ", " + "Note               = '" + POut.String(recall.Note) + "', " + "IsDisabled         =  " + POut.Bool(recall.IsDisabled) + ", " + "RecallTypeNum      =  " + POut.Long(recall.RecallTypeNum) + ", " + "DisableUntilBalance= '" + POut.Double(recall.DisableUntilBalance) + "', " + "DisableUntilDate   =  " + POut.Date(recall.DisableUntilDate) + ", " + "DateScheduled      =  " + POut.Date(recall.DateScheduled) + " " + "WHERE RecallNum = " + POut.Long(recall.RecallNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Recall in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Recall recall, Recall oldRecall) throws Exception {
        String command = "";
        if (recall.PatNum != oldRecall.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(recall.PatNum) + "";
        }
         
        if (recall.DateDueCalc != oldRecall.DateDueCalc)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateDueCalc = " + POut.Date(recall.DateDueCalc) + "";
        }
         
        if (recall.DateDue != oldRecall.DateDue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateDue = " + POut.Date(recall.DateDue) + "";
        }
         
        if (recall.DatePrevious != oldRecall.DatePrevious)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DatePrevious = " + POut.Date(recall.DatePrevious) + "";
        }
         
        if (recall.RecallInterval != oldRecall.RecallInterval)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RecallInterval = " + POut.Int(recall.RecallInterval.ToInt()) + "";
        }
         
        if (recall.RecallStatus != oldRecall.RecallStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RecallStatus = " + POut.Long(recall.RecallStatus) + "";
        }
         
        if (recall.Note != oldRecall.Note)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.String(recall.Note) + "'";
        }
         
        if (recall.IsDisabled != oldRecall.IsDisabled)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsDisabled = " + POut.Bool(recall.IsDisabled) + "";
        }
         
        //DateTStamp can only be set by MySQL
        if (recall.RecallTypeNum != oldRecall.RecallTypeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "RecallTypeNum = " + POut.Long(recall.RecallTypeNum) + "";
        }
         
        if (recall.DisableUntilBalance != oldRecall.DisableUntilBalance)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DisableUntilBalance = '" + POut.Double(recall.DisableUntilBalance) + "'";
        }
         
        if (recall.DisableUntilDate != oldRecall.DisableUntilDate)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DisableUntilDate = " + POut.Date(recall.DisableUntilDate) + "";
        }
         
        if (recall.DateScheduled != oldRecall.DateScheduled)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateScheduled = " + POut.Date(recall.DateScheduled) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE recall SET " + command + " WHERE RecallNum = " + POut.Long(recall.RecallNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Recall from the database.
    */
    public static void delete(long recallNum) throws Exception {
        String command = "DELETE FROM recall " + "WHERE RecallNum = " + POut.Long(recallNum);
        Db.NonQ(command);
    }

}


