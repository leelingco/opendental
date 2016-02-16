//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:09 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class XChargeTransactionCrud   
{
    /**
    * Gets one XChargeTransaction object from the database using the primary key.  Returns null if not found.
    */
    public static XChargeTransaction selectOne(long xChargeTransactionNum) throws Exception {
        String command = "SELECT * FROM xchargetransaction " + "WHERE XChargeTransactionNum = " + POut.Long(xChargeTransactionNum);
        List<XChargeTransaction> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one XChargeTransaction object from the database using a query.
    */
    public static XChargeTransaction selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<XChargeTransaction> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of XChargeTransaction objects from the database using a query.
    */
    public static List<XChargeTransaction> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<XChargeTransaction> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<XChargeTransaction> tableToList(DataTable table) throws Exception {
        List<XChargeTransaction> retVal = new List<XChargeTransaction>();
        XChargeTransaction xChargeTransaction = new XChargeTransaction();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            xChargeTransaction = new XChargeTransaction();
            xChargeTransaction.XChargeTransactionNum = PIn.Long(table.Rows[i]["XChargeTransactionNum"].ToString());
            xChargeTransaction.TransType = PIn.String(table.Rows[i]["TransType"].ToString());
            xChargeTransaction.Amount = PIn.Double(table.Rows[i]["Amount"].ToString());
            xChargeTransaction.CCEntry = PIn.String(table.Rows[i]["CCEntry"].ToString());
            xChargeTransaction.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            xChargeTransaction.Result = PIn.String(table.Rows[i]["Result"].ToString());
            xChargeTransaction.ClerkID = PIn.String(table.Rows[i]["ClerkID"].ToString());
            xChargeTransaction.ResultCode = PIn.String(table.Rows[i]["ResultCode"].ToString());
            xChargeTransaction.Expiration = PIn.String(table.Rows[i]["Expiration"].ToString());
            xChargeTransaction.CCType = PIn.String(table.Rows[i]["CCType"].ToString());
            xChargeTransaction.CreditCardNum = PIn.String(table.Rows[i]["CreditCardNum"].ToString());
            xChargeTransaction.BatchNum = PIn.String(table.Rows[i]["BatchNum"].ToString());
            xChargeTransaction.ItemNum = PIn.String(table.Rows[i]["ItemNum"].ToString());
            xChargeTransaction.ApprCode = PIn.String(table.Rows[i]["ApprCode"].ToString());
            xChargeTransaction.TransactionDateTime = PIn.DateT(table.Rows[i]["TransactionDateTime"].ToString());
            retVal.Add(xChargeTransaction);
        }
        return retVal;
    }

    /**
    * Inserts one XChargeTransaction into the database.  Returns the new priKey.
    */
    public static long insert(XChargeTransaction xChargeTransaction) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            xChargeTransaction.XChargeTransactionNum = DbHelper.GetNextOracleKey("xchargetransaction", "XChargeTransactionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(xChargeTransaction,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        xChargeTransaction.XChargeTransactionNum++;
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
            return insert(xChargeTransaction,false);
        } 
    }

    /**
    * Inserts one XChargeTransaction into the database.  Provides option to use the existing priKey.
    */
    public static long insert(XChargeTransaction xChargeTransaction, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            xChargeTransaction.XChargeTransactionNum = ReplicationServers.GetKey("xchargetransaction", "XChargeTransactionNum");
        }
         
        String command = "INSERT INTO xchargetransaction (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "XChargeTransactionNum,";
        }
         
        command += "TransType,Amount,CCEntry,PatNum,Result,ClerkID,ResultCode,Expiration,CCType,CreditCardNum,BatchNum,ItemNum,ApprCode,TransactionDateTime) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(xChargeTransaction.XChargeTransactionNum) + ",";
        }
         
        command += "'" + POut.String(xChargeTransaction.TransType) + "'," + "'" + POut.Double(xChargeTransaction.Amount) + "'," + "'" + POut.String(xChargeTransaction.CCEntry) + "'," + POut.Long(xChargeTransaction.PatNum) + "," + "'" + POut.String(xChargeTransaction.Result) + "'," + "'" + POut.String(xChargeTransaction.ClerkID) + "'," + "'" + POut.String(xChargeTransaction.ResultCode) + "'," + "'" + POut.String(xChargeTransaction.Expiration) + "'," + "'" + POut.String(xChargeTransaction.CCType) + "'," + "'" + POut.String(xChargeTransaction.CreditCardNum) + "'," + "'" + POut.String(xChargeTransaction.BatchNum) + "'," + "'" + POut.String(xChargeTransaction.ItemNum) + "'," + "'" + POut.String(xChargeTransaction.ApprCode) + "'," + POut.DateT(xChargeTransaction.TransactionDateTime) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            xChargeTransaction.XChargeTransactionNum = Db.NonQ(command, true);
        } 
        return xChargeTransaction.XChargeTransactionNum;
    }

    /**
    * Updates one XChargeTransaction in the database.
    */
    public static void update(XChargeTransaction xChargeTransaction) throws Exception {
        String command = "UPDATE xchargetransaction SET " + "TransType            = '" + POut.String(xChargeTransaction.TransType) + "', " + "Amount               = '" + POut.Double(xChargeTransaction.Amount) + "', " + "CCEntry              = '" + POut.String(xChargeTransaction.CCEntry) + "', " + "PatNum               =  " + POut.Long(xChargeTransaction.PatNum) + ", " + "Result               = '" + POut.String(xChargeTransaction.Result) + "', " + "ClerkID              = '" + POut.String(xChargeTransaction.ClerkID) + "', " + "ResultCode           = '" + POut.String(xChargeTransaction.ResultCode) + "', " + "Expiration           = '" + POut.String(xChargeTransaction.Expiration) + "', " + "CCType               = '" + POut.String(xChargeTransaction.CCType) + "', " + "CreditCardNum        = '" + POut.String(xChargeTransaction.CreditCardNum) + "', " + "BatchNum             = '" + POut.String(xChargeTransaction.BatchNum) + "', " + "ItemNum              = '" + POut.String(xChargeTransaction.ItemNum) + "', " + "ApprCode             = '" + POut.String(xChargeTransaction.ApprCode) + "', " + "TransactionDateTime  =  " + POut.DateT(xChargeTransaction.TransactionDateTime) + " " + "WHERE XChargeTransactionNum = " + POut.Long(xChargeTransaction.XChargeTransactionNum);
        Db.NonQ(command);
    }

    /**
    * Updates one XChargeTransaction in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(XChargeTransaction xChargeTransaction, XChargeTransaction oldXChargeTransaction) throws Exception {
        String command = "";
        if (xChargeTransaction.TransType != oldXChargeTransaction.TransType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TransType = '" + POut.String(xChargeTransaction.TransType) + "'";
        }
         
        if (xChargeTransaction.Amount != oldXChargeTransaction.Amount)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Amount = '" + POut.Double(xChargeTransaction.Amount) + "'";
        }
         
        if (xChargeTransaction.CCEntry != oldXChargeTransaction.CCEntry)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CCEntry = '" + POut.String(xChargeTransaction.CCEntry) + "'";
        }
         
        if (xChargeTransaction.PatNum != oldXChargeTransaction.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(xChargeTransaction.PatNum) + "";
        }
         
        if (xChargeTransaction.Result != oldXChargeTransaction.Result)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Result = '" + POut.String(xChargeTransaction.Result) + "'";
        }
         
        if (xChargeTransaction.ClerkID != oldXChargeTransaction.ClerkID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClerkID = '" + POut.String(xChargeTransaction.ClerkID) + "'";
        }
         
        if (xChargeTransaction.ResultCode != oldXChargeTransaction.ResultCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ResultCode = '" + POut.String(xChargeTransaction.ResultCode) + "'";
        }
         
        if (xChargeTransaction.Expiration != oldXChargeTransaction.Expiration)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Expiration = '" + POut.String(xChargeTransaction.Expiration) + "'";
        }
         
        if (xChargeTransaction.CCType != oldXChargeTransaction.CCType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CCType = '" + POut.String(xChargeTransaction.CCType) + "'";
        }
         
        if (xChargeTransaction.CreditCardNum != oldXChargeTransaction.CreditCardNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CreditCardNum = '" + POut.String(xChargeTransaction.CreditCardNum) + "'";
        }
         
        if (xChargeTransaction.BatchNum != oldXChargeTransaction.BatchNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BatchNum = '" + POut.String(xChargeTransaction.BatchNum) + "'";
        }
         
        if (xChargeTransaction.ItemNum != oldXChargeTransaction.ItemNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemNum = '" + POut.String(xChargeTransaction.ItemNum) + "'";
        }
         
        if (xChargeTransaction.ApprCode != oldXChargeTransaction.ApprCode)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ApprCode = '" + POut.String(xChargeTransaction.ApprCode) + "'";
        }
         
        if (xChargeTransaction.TransactionDateTime != oldXChargeTransaction.TransactionDateTime)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TransactionDateTime = " + POut.DateT(xChargeTransaction.TransactionDateTime) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE xchargetransaction SET " + command + " WHERE XChargeTransactionNum = " + POut.Long(xChargeTransaction.XChargeTransactionNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one XChargeTransaction from the database.
    */
    public static void delete(long xChargeTransactionNum) throws Exception {
        String command = "DELETE FROM xchargetransaction " + "WHERE XChargeTransactionNum = " + POut.Long(xChargeTransactionNum);
        Db.NonQ(command);
    }

}

