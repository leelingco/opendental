//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Deposit;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DepositCrud   
{
    /**
    * Gets one Deposit object from the database using the primary key.  Returns null if not found.
    */
    public static Deposit selectOne(long depositNum) throws Exception {
        String command = "SELECT * FROM deposit " + "WHERE DepositNum = " + POut.long(depositNum);
        List<Deposit> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Deposit object from the database using a query.
    */
    public static Deposit selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Deposit> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Deposit objects from the database using a query.
    */
    public static List<Deposit> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Deposit> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Deposit> tableToList(DataTable table) throws Exception {
        List<Deposit> retVal = new List<Deposit>();
        Deposit deposit;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            deposit = new Deposit();
            deposit.DepositNum = PIn.Long(table.Rows[i]["DepositNum"].ToString());
            deposit.DateDeposit = PIn.Date(table.Rows[i]["DateDeposit"].ToString());
            deposit.BankAccountInfo = PIn.String(table.Rows[i]["BankAccountInfo"].ToString());
            deposit.Amount = PIn.Double(table.Rows[i]["Amount"].ToString());
            deposit.Memo = PIn.String(table.Rows[i]["Memo"].ToString());
            retVal.Add(deposit);
        }
        return retVal;
    }

    /**
    * Inserts one Deposit into the database.  Returns the new priKey.
    */
    public static long insert(Deposit deposit) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            deposit.DepositNum = DbHelper.getNextOracleKey("deposit","DepositNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(deposit, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        deposit.DepositNum++;
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
            return Insert(deposit, false);
        } 
    }

    /**
    * Inserts one Deposit into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Deposit deposit, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            deposit.DepositNum = ReplicationServers.getKey("deposit","DepositNum");
        }
         
        String command = "INSERT INTO deposit (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "DepositNum,";
        }
         
        command += "DateDeposit,BankAccountInfo,Amount,Memo) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(deposit.DepositNum) + ",";
        }
         
        command += POut.date(deposit.DateDeposit) + "," + "'" + POut.string(deposit.BankAccountInfo) + "'," + "'" + POut.double(deposit.Amount) + "'," + "'" + POut.string(deposit.Memo) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            deposit.DepositNum = Db.nonQ(command,true);
        } 
        return deposit.DepositNum;
    }

    /**
    * Updates one Deposit in the database.
    */
    public static void update(Deposit deposit) throws Exception {
        String command = "UPDATE deposit SET " + "DateDeposit    =  " + POut.date(deposit.DateDeposit) + ", " + "BankAccountInfo= '" + POut.string(deposit.BankAccountInfo) + "', " + "Amount         = '" + POut.double(deposit.Amount) + "', " + "Memo           = '" + POut.string(deposit.Memo) + "' " + "WHERE DepositNum = " + POut.long(deposit.DepositNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Deposit in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Deposit deposit, Deposit oldDeposit) throws Exception {
        String command = "";
        if (deposit.DateDeposit != oldDeposit.DateDeposit)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateDeposit = " + POut.date(deposit.DateDeposit) + "";
        }
         
        if (!StringSupport.equals(deposit.BankAccountInfo, oldDeposit.BankAccountInfo))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BankAccountInfo = '" + POut.string(deposit.BankAccountInfo) + "'";
        }
         
        if (deposit.Amount != oldDeposit.Amount)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Amount = '" + POut.double(deposit.Amount) + "'";
        }
         
        if (!StringSupport.equals(deposit.Memo, oldDeposit.Memo))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Memo = '" + POut.string(deposit.Memo) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE deposit SET " + command + " WHERE DepositNum = " + POut.long(deposit.DepositNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Deposit from the database.
    */
    public static void delete(long depositNum) throws Exception {
        String command = "DELETE FROM deposit " + "WHERE DepositNum = " + POut.long(depositNum);
        Db.nonQ(command);
    }

}


