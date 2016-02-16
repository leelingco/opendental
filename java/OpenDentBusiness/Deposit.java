//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Deposit;
import OpenDentBusiness.TableBase;

/**
* A deposit slip.  Contains multiple insurance and patient checks.
*/
public class Deposit  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DepositNum = new long();
    /**
    * The date of the deposit.
    */
    public DateTime DateDeposit = new DateTime();
    /**
    * User editable.  Usually includes name on the account and account number.  Possibly the bank name as well.
    */
    public String BankAccountInfo = new String();
    /**
    * Total amount of the deposit. User not allowed to directly edit.
    */
    public double Amount = new double();
    /**
    * Short description to help identify the deposit.
    */
    public String Memo = new String();
    /**
    * 
    */
    public Deposit copy() throws Exception {
        return (Deposit)this.MemberwiseClone();
    }

}


