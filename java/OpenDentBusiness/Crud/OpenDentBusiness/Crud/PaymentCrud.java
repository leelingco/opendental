//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PaymentCrud   
{
    /**
    * Gets one Payment object from the database using the primary key.  Returns null if not found.
    */
    public static Payment selectOne(long payNum) throws Exception {
        String command = "SELECT * FROM payment " + "WHERE PayNum = " + POut.Long(payNum);
        List<Payment> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Payment object from the database using a query.
    */
    public static Payment selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Payment> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Payment objects from the database using a query.
    */
    public static List<Payment> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Payment> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Payment> tableToList(DataTable table) throws Exception {
        List<Payment> retVal = new List<Payment>();
        Payment payment = new Payment();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            payment = new Payment();
            payment.PayNum = PIn.Long(table.Rows[i]["PayNum"].ToString());
            payment.PayType = PIn.Long(table.Rows[i]["PayType"].ToString());
            payment.PayDate = PIn.Date(table.Rows[i]["PayDate"].ToString());
            payment.PayAmt = PIn.Double(table.Rows[i]["PayAmt"].ToString());
            payment.CheckNum = PIn.String(table.Rows[i]["CheckNum"].ToString());
            payment.BankBranch = PIn.String(table.Rows[i]["BankBranch"].ToString());
            payment.PayNote = PIn.String(table.Rows[i]["PayNote"].ToString());
            payment.IsSplit = PIn.Bool(table.Rows[i]["IsSplit"].ToString());
            payment.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            payment.ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
            payment.DateEntry = PIn.Date(table.Rows[i]["DateEntry"].ToString());
            payment.DepositNum = PIn.Long(table.Rows[i]["DepositNum"].ToString());
            payment.Receipt = PIn.String(table.Rows[i]["Receipt"].ToString());
            payment.IsRecurringCC = PIn.Bool(table.Rows[i]["IsRecurringCC"].ToString());
            retVal.Add(payment);
        }
        return retVal;
    }

    /**
    * Inserts one Payment into the database.  Returns the new priKey.
    */
    public static long insert(Payment payment) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            payment.PayNum = DbHelper.GetNextOracleKey("payment", "PayNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(payment,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        payment.PayNum++;
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
            return insert(payment,false);
        } 
    }

    /**
    * Inserts one Payment into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Payment payment, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            payment.PayNum = ReplicationServers.GetKey("payment", "PayNum");
        }
         
        String command = "INSERT INTO payment (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "PayNum,";
        }
         
        command += "PayType,PayDate,PayAmt,CheckNum,BankBranch,PayNote,IsSplit,PatNum,ClinicNum,DateEntry,DepositNum,Receipt,IsRecurringCC) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(payment.PayNum) + ",";
        }
         
        command += POut.Long(payment.PayType) + "," + POut.Date(payment.PayDate) + "," + "'" + POut.Double(payment.PayAmt) + "'," + "'" + POut.String(payment.CheckNum) + "'," + "'" + POut.String(payment.BankBranch) + "'," + "'" + POut.String(payment.PayNote) + "'," + POut.Bool(payment.IsSplit) + "," + POut.Long(payment.PatNum) + "," + POut.Long(payment.ClinicNum) + "," + DbHelper.Now() + "," + POut.Long(payment.DepositNum) + "," + DbHelper.ParamChar + "paramReceipt," + POut.Bool(payment.IsRecurringCC) + ")";
        if (payment.Receipt == null)
        {
            payment.Receipt = "";
        }
         
        OdSqlParameter paramReceipt = new OdSqlParameter("paramReceipt", OdDbType.Text, payment.Receipt);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramReceipt);
        }
        else
        {
            payment.PayNum = Db.NonQ(command, true, paramReceipt);
        } 
        return payment.PayNum;
    }

    /**
    * Updates one Payment in the database.
    */
    public static void update(Payment payment) throws Exception {
        //DateEntry not allowed to change
        //DepositNum excluded from update
        String command = "UPDATE payment SET " + "PayType      =  " + POut.Long(payment.PayType) + ", " + "PayDate      =  " + POut.Date(payment.PayDate) + ", " + "PayAmt       = '" + POut.Double(payment.PayAmt) + "', " + "CheckNum     = '" + POut.String(payment.CheckNum) + "', " + "BankBranch   = '" + POut.String(payment.BankBranch) + "', " + "PayNote      = '" + POut.String(payment.PayNote) + "', " + "IsSplit      =  " + POut.Bool(payment.IsSplit) + ", " + "PatNum       =  " + POut.Long(payment.PatNum) + ", " + "ClinicNum    =  " + POut.Long(payment.ClinicNum) + ", " + "Receipt      =  " + DbHelper.ParamChar + "paramReceipt, " + "IsRecurringCC=  " + POut.Bool(payment.IsRecurringCC) + " " + "WHERE PayNum = " + POut.Long(payment.PayNum);
        if (payment.Receipt == null)
        {
            payment.Receipt = "";
        }
         
        OdSqlParameter paramReceipt = new OdSqlParameter("paramReceipt", OdDbType.Text, payment.Receipt);
        Db.NonQ(command, paramReceipt);
    }

    /**
    * Updates one Payment in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Payment payment, Payment oldPayment) throws Exception {
        String command = "";
        if (payment.PayType != oldPayment.PayType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayType = " + POut.Long(payment.PayType) + "";
        }
         
        if (payment.PayDate != oldPayment.PayDate)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayDate = " + POut.Date(payment.PayDate) + "";
        }
         
        if (payment.PayAmt != oldPayment.PayAmt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayAmt = '" + POut.Double(payment.PayAmt) + "'";
        }
         
        if (payment.CheckNum != oldPayment.CheckNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CheckNum = '" + POut.String(payment.CheckNum) + "'";
        }
         
        if (payment.BankBranch != oldPayment.BankBranch)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BankBranch = '" + POut.String(payment.BankBranch) + "'";
        }
         
        if (payment.PayNote != oldPayment.PayNote)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayNote = '" + POut.String(payment.PayNote) + "'";
        }
         
        if (payment.IsSplit != oldPayment.IsSplit)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsSplit = " + POut.Bool(payment.IsSplit) + "";
        }
         
        if (payment.PatNum != oldPayment.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(payment.PatNum) + "";
        }
         
        if (payment.ClinicNum != oldPayment.ClinicNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClinicNum = " + POut.Long(payment.ClinicNum) + "";
        }
         
        //DateEntry not allowed to change
        //DepositNum excluded from update
        if (payment.Receipt != oldPayment.Receipt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Receipt = " + DbHelper.ParamChar + "paramReceipt";
        }
         
        if (payment.IsRecurringCC != oldPayment.IsRecurringCC)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsRecurringCC = " + POut.Bool(payment.IsRecurringCC) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (payment.Receipt == null)
        {
            payment.Receipt = "";
        }
         
        OdSqlParameter paramReceipt = new OdSqlParameter("paramReceipt", OdDbType.Text, payment.Receipt);
        command = "UPDATE payment SET " + command + " WHERE PayNum = " + POut.Long(payment.PayNum);
        Db.NonQ(command, paramReceipt);
    }

    /**
    * Deletes one Payment from the database.
    */
    public static void delete(long payNum) throws Exception {
        String command = "DELETE FROM payment " + "WHERE PayNum = " + POut.Long(payNum);
        Db.NonQ(command);
    }

}


