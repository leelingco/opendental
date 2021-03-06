//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class JournalEntryCrud   
{
    /**
    * Gets one JournalEntry object from the database using the primary key.  Returns null if not found.
    */
    public static JournalEntry selectOne(long journalEntryNum) throws Exception {
        String command = "SELECT * FROM journalentry " + "WHERE JournalEntryNum = " + POut.long(journalEntryNum);
        List<JournalEntry> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one JournalEntry object from the database using a query.
    */
    public static JournalEntry selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<JournalEntry> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of JournalEntry objects from the database using a query.
    */
    public static List<JournalEntry> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<JournalEntry> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<JournalEntry> tableToList(DataTable table) throws Exception {
        List<JournalEntry> retVal = new List<JournalEntry>();
        JournalEntry journalEntry;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            journalEntry = new JournalEntry();
            journalEntry.JournalEntryNum = PIn.Long(table.Rows[i]["JournalEntryNum"].ToString());
            journalEntry.TransactionNum = PIn.Long(table.Rows[i]["TransactionNum"].ToString());
            journalEntry.AccountNum = PIn.Long(table.Rows[i]["AccountNum"].ToString());
            journalEntry.DateDisplayed = PIn.Date(table.Rows[i]["DateDisplayed"].ToString());
            journalEntry.DebitAmt = PIn.Double(table.Rows[i]["DebitAmt"].ToString());
            journalEntry.CreditAmt = PIn.Double(table.Rows[i]["CreditAmt"].ToString());
            journalEntry.Memo = PIn.String(table.Rows[i]["Memo"].ToString());
            journalEntry.Splits = PIn.String(table.Rows[i]["Splits"].ToString());
            journalEntry.CheckNumber = PIn.String(table.Rows[i]["CheckNumber"].ToString());
            journalEntry.ReconcileNum = PIn.Long(table.Rows[i]["ReconcileNum"].ToString());
            retVal.Add(journalEntry);
        }
        return retVal;
    }

    /**
    * Inserts one JournalEntry into the database.  Returns the new priKey.
    */
    public static long insert(JournalEntry journalEntry) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            journalEntry.JournalEntryNum = DbHelper.getNextOracleKey("journalentry","JournalEntryNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(journalEntry, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        journalEntry.JournalEntryNum++;
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
            return Insert(journalEntry, false);
        } 
    }

    /**
    * Inserts one JournalEntry into the database.  Provides option to use the existing priKey.
    */
    public static long insert(JournalEntry journalEntry, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            journalEntry.JournalEntryNum = ReplicationServers.getKey("journalentry","JournalEntryNum");
        }
         
        String command = "INSERT INTO journalentry (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "JournalEntryNum,";
        }
         
        command += "TransactionNum,AccountNum,DateDisplayed,DebitAmt,CreditAmt,Memo,Splits,CheckNumber,ReconcileNum) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(journalEntry.JournalEntryNum) + ",";
        }
         
        command += POut.long(journalEntry.TransactionNum) + "," + POut.long(journalEntry.AccountNum) + "," + POut.date(journalEntry.DateDisplayed) + "," + "'" + POut.double(journalEntry.DebitAmt) + "'," + "'" + POut.double(journalEntry.CreditAmt) + "'," + "'" + POut.string(journalEntry.Memo) + "'," + "'" + POut.string(journalEntry.Splits) + "'," + "'" + POut.string(journalEntry.CheckNumber) + "'," + POut.long(journalEntry.ReconcileNum) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            journalEntry.JournalEntryNum = Db.nonQ(command,true);
        } 
        return journalEntry.JournalEntryNum;
    }

    /**
    * Updates one JournalEntry in the database.
    */
    public static void update(JournalEntry journalEntry) throws Exception {
        String command = "UPDATE journalentry SET " + "TransactionNum =  " + POut.long(journalEntry.TransactionNum) + ", " + "AccountNum     =  " + POut.long(journalEntry.AccountNum) + ", " + "DateDisplayed  =  " + POut.date(journalEntry.DateDisplayed) + ", " + "DebitAmt       = '" + POut.double(journalEntry.DebitAmt) + "', " + "CreditAmt      = '" + POut.double(journalEntry.CreditAmt) + "', " + "Memo           = '" + POut.string(journalEntry.Memo) + "', " + "Splits         = '" + POut.string(journalEntry.Splits) + "', " + "CheckNumber    = '" + POut.string(journalEntry.CheckNumber) + "', " + "ReconcileNum   =  " + POut.long(journalEntry.ReconcileNum) + " " + "WHERE JournalEntryNum = " + POut.long(journalEntry.JournalEntryNum);
        Db.nonQ(command);
    }

    /**
    * Updates one JournalEntry in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(JournalEntry journalEntry, JournalEntry oldJournalEntry) throws Exception {
        String command = "";
        if (journalEntry.TransactionNum != oldJournalEntry.TransactionNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TransactionNum = " + POut.long(journalEntry.TransactionNum) + "";
        }
         
        if (journalEntry.AccountNum != oldJournalEntry.AccountNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AccountNum = " + POut.long(journalEntry.AccountNum) + "";
        }
         
        if (journalEntry.DateDisplayed != oldJournalEntry.DateDisplayed)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateDisplayed = " + POut.date(journalEntry.DateDisplayed) + "";
        }
         
        if (journalEntry.DebitAmt != oldJournalEntry.DebitAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DebitAmt = '" + POut.double(journalEntry.DebitAmt) + "'";
        }
         
        if (journalEntry.CreditAmt != oldJournalEntry.CreditAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CreditAmt = '" + POut.double(journalEntry.CreditAmt) + "'";
        }
         
        if (!StringSupport.equals(journalEntry.Memo, oldJournalEntry.Memo))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Memo = '" + POut.string(journalEntry.Memo) + "'";
        }
         
        if (!StringSupport.equals(journalEntry.Splits, oldJournalEntry.Splits))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Splits = '" + POut.string(journalEntry.Splits) + "'";
        }
         
        if (!StringSupport.equals(journalEntry.CheckNumber, oldJournalEntry.CheckNumber))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CheckNumber = '" + POut.string(journalEntry.CheckNumber) + "'";
        }
         
        if (journalEntry.ReconcileNum != oldJournalEntry.ReconcileNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ReconcileNum = " + POut.long(journalEntry.ReconcileNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE journalentry SET " + command + " WHERE JournalEntryNum = " + POut.long(journalEntry.JournalEntryNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one JournalEntry from the database.
    */
    public static void delete(long journalEntryNum) throws Exception {
        String command = "DELETE FROM journalentry " + "WHERE JournalEntryNum = " + POut.long(journalEntryNum);
        Db.nonQ(command);
    }

}


