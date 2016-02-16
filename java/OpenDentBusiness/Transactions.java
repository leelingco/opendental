//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Transaction;

/**
* 
*/
public class Transactions   
{
    /**
    * Since transactions are always viewed individually, this function returns one transaction
    */
    public static Transaction getTrans(long transactionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Transaction>GetObject(MethodBase.GetCurrentMethod(), transactionNum);
        }
         
        return Crud.TransactionCrud.SelectOne(transactionNum);
    }

    /**
    * Gets one transaction directly from the database which has this deposit attached to it.  If none exist, then returns null.
    */
    public static Transaction getAttachedToDeposit(long depositNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Transaction>GetObject(MethodBase.GetCurrentMethod(), depositNum);
        }
         
        String command = "SELECT * FROM transaction " + "WHERE DepositNum=" + POut.long(depositNum);
        return Crud.TransactionCrud.SelectOne(command);
    }

    /**
    * Gets one transaction directly from the database which has this payment attached to it.  If none exist, then returns null.  There should never be more than one, so that's why it doesn't return more than one.
    */
    public static Transaction getAttachedToPayment(long payNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Transaction>GetObject(MethodBase.GetCurrentMethod(), payNum);
        }
         
        String command = "SELECT * FROM transaction " + "WHERE PayNum=" + POut.long(payNum);
        return Crud.TransactionCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(Transaction trans) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            trans.TransactionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), trans);
            return trans.TransactionNum;
        }
         
        return Crud.TransactionCrud.Insert(trans);
    }

    /**
    * 
    */
    public static void update(Transaction trans) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), trans);
            return ;
        }
         
        Crud.TransactionCrud.Update(trans);
    }

    /**
    * Also deletes all journal entries for the transaction.  Will later throw an error if journal entries attached to any reconciles.  Be sure to surround with try-catch.
    */
    public static void delete(Transaction trans) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), trans);
            return ;
        }
         
        String command = "SELECT IsLocked FROM journalentry j, reconcile r WHERE j.TransactionNum=" + POut.long(trans.TransactionNum) + " AND j.ReconcileNum = r.ReconcileNum";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count > 0)
        {
            if (PIn.Int(table.Rows[0][0].ToString()) == 1)
            {
                throw new ApplicationException(Lans.g("Transactions","Not allowed to delete transactions because it is attached to a reconcile that is locked."));
            }
             
        }
         
        command = "DELETE FROM journalentry WHERE TransactionNum=" + POut.long(trans.TransactionNum);
        Db.nonQ(command);
        command = "DELETE FROM transaction WHERE TransactionNum=" + POut.long(trans.TransactionNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static boolean isReconciled(Transaction trans) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), trans);
        }
         
        String command = "SELECT COUNT(*) FROM journalentry WHERE ReconcileNum !=0" + " AND TransactionNum=" + POut.long(trans.TransactionNum);
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

}


