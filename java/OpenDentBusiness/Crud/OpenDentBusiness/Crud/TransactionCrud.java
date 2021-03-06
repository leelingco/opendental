//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:08 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class TransactionCrud   
{
    /**
    * Gets one Transaction object from the database using the primary key.  Returns null if not found.
    */
    public static Transaction selectOne(long transactionNum) throws Exception {
        String command = "SELECT * FROM transaction " + "WHERE TransactionNum = " + POut.Long(transactionNum);
        List<Transaction> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Transaction object from the database using a query.
    */
    public static Transaction selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Transaction> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Transaction objects from the database using a query.
    */
    public static List<Transaction> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Transaction> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Transaction> tableToList(DataTable table) throws Exception {
        List<Transaction> retVal = new List<Transaction>();
        Transaction transaction = new Transaction();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            transaction = new Transaction();
            transaction.TransactionNum = PIn.Long(table.Rows[i]["TransactionNum"].ToString());
            transaction.DateTimeEntry = PIn.DateT(table.Rows[i]["DateTimeEntry"].ToString());
            transaction.UserNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
            transaction.DepositNum = PIn.Long(table.Rows[i]["DepositNum"].ToString());
            transaction.PayNum = PIn.Long(table.Rows[i]["PayNum"].ToString());
            retVal.Add(transaction);
        }
        return retVal;
    }

    /**
    * Inserts one Transaction into the database.  Returns the new priKey.
    */
    public static long insert(Transaction transaction) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            transaction.TransactionNum = DbHelper.GetNextOracleKey("transaction", "TransactionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(transaction,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        transaction.TransactionNum++;
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
            return insert(transaction,false);
        } 
    }

    /**
    * Inserts one Transaction into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Transaction transaction, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            transaction.TransactionNum = ReplicationServers.GetKey("transaction", "TransactionNum");
        }
         
        String command = "INSERT INTO transaction (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "TransactionNum,";
        }
         
        command += "DateTimeEntry,UserNum,DepositNum,PayNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(transaction.TransactionNum) + ",";
        }
         
        command += DbHelper.Now() + "," + POut.Long(transaction.UserNum) + "," + POut.Long(transaction.DepositNum) + "," + POut.Long(transaction.PayNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            transaction.TransactionNum = Db.NonQ(command, true);
        } 
        return transaction.TransactionNum;
    }

    /**
    * Updates one Transaction in the database.
    */
    public static void update(Transaction transaction) throws Exception {
        //DateTimeEntry not allowed to change
        String command = "UPDATE transaction SET " + "UserNum       =  " + POut.Long(transaction.UserNum) + ", " + "DepositNum    =  " + POut.Long(transaction.DepositNum) + ", " + "PayNum        =  " + POut.Long(transaction.PayNum) + " " + "WHERE TransactionNum = " + POut.Long(transaction.TransactionNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Transaction in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Transaction transaction, Transaction oldTransaction) throws Exception {
        String command = "";
        //DateTimeEntry not allowed to change
        if (transaction.UserNum != oldTransaction.UserNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UserNum = " + POut.Long(transaction.UserNum) + "";
        }
         
        if (transaction.DepositNum != oldTransaction.DepositNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DepositNum = " + POut.Long(transaction.DepositNum) + "";
        }
         
        if (transaction.PayNum != oldTransaction.PayNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayNum = " + POut.Long(transaction.PayNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE transaction SET " + command + " WHERE TransactionNum = " + POut.Long(transaction.TransactionNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Transaction from the database.
    */
    public static void delete(long transactionNum) throws Exception {
        String command = "DELETE FROM transaction " + "WHERE TransactionNum = " + POut.Long(transactionNum);
        Db.NonQ(command);
    }

}


