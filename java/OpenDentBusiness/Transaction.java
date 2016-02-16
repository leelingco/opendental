//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.Transaction;

/**
* Used in the accounting section of the program.  Each row is one transaction in the ledger, and must always have at least two splits.  All splits must always add up to zero.
*/
public class Transaction  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TransactionNum = new long();
    /**
    * Not user editable.  Server time.
    */
    public DateTime DateTimeEntry = new DateTime();
    /**
    * FK to user.UserNum.
    */
    public long UserNum = new long();
    /**
    * FK to deposit.DepositNum.  Will eventually be replaced by a source document table, and deposits will just be one of many types.
    */
    public long DepositNum = new long();
    /**
    * FK to payment.PayNum.  Like DepositNum, it will eventually be replaced by a source document table, and payments will just be one of many types.
    */
    public long PayNum = new long();
    /**
    * 
    */
    public Transaction copy() throws Exception {
        Transaction t = new Transaction();
        t.TransactionNum = TransactionNum;
        t.DateTimeEntry = DateTimeEntry;
        t.UserNum = UserNum;
        t.DepositNum = DepositNum;
        t.PayNum = PayNum;
        return t;
    }

}


