//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Reconcile;
import OpenDentBusiness.TableBase;

/**
* Used in the Accounting section.  Each row represents one reconcile.  Transactions will be attached to it.
*/
public class Reconcile  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ReconcileNum = new long();
    /**
    * FK to account.AccountNum
    */
    public long AccountNum = new long();
    /**
    * User enters starting balance here.
    */
    public double StartingBal = new double();
    /**
    * User enters ending balance here.
    */
    public double EndingBal = new double();
    /**
    * The date that the reconcile was performed.
    */
    public DateTime DateReconcile = new DateTime();
    /**
    * If StartingBal + sum of entries selected = EndingBal, then user can lock.  Unlock requires special permission, which nobody will have by default.
    */
    public boolean IsLocked = new boolean();
    /**
    * 
    */
    public Reconcile copy() throws Exception {
        Reconcile r = new Reconcile();
        r.ReconcileNum = ReconcileNum;
        r.AccountNum = AccountNum;
        r.StartingBal = StartingBal;
        r.EndingBal = EndingBal;
        r.DateReconcile = DateReconcile;
        r.IsLocked = IsLocked;
        return r;
    }

}


