//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.TableBase;

/**
* Used in accounting to represent a single credit or debit entry.  There will always be at least 2 journal enties attached to every transaction.  All transactions balance to 0.
*/
public class JournalEntry  extends TableBase 
{
    /**
    * Primary key.
    */
    public long JournalEntryNum = new long();
    /**
    * FK to transaction.TransactionNum
    */
    public long TransactionNum = new long();
    /**
    * FK to account.AccountNum
    */
    public long AccountNum = new long();
    /**
    * Always the same for all journal entries within one transaction.
    */
    public DateTime DateDisplayed = new DateTime();
    /**
    * Negative numbers never allowed.
    */
    public double DebitAmt = new double();
    /**
    * Negative numbers never allowed.
    */
    public double CreditAmt = new double();
    /**
    * .
    */
    public String Memo = new String();
    /**
    * A human-readable description of the splits.  Used only for display purposes.
    */
    public String Splits = new String();
    /**
    * Any user-defined string.  Usually a check number, but can also be D for deposit, Adj, etc.
    */
    public String CheckNumber = new String();
    /**
    * FK to reconcile.ReconcileNum. 0 if not attached to a reconcile. Not allowed to alter amounts if attached.
    */
    public long ReconcileNum = new long();
    /**
    * 
    */
    public JournalEntry copy() throws Exception {
        return (JournalEntry)this.MemberwiseClone();
    }

}


