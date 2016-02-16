//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.TableBase;

/**
* Each row represents a single check from the insurance company.  The amount may be split between patients using claimprocs.  The amount of the check must always exactly equal the sum of all the claimprocs attached to it.  There might be only one claimproc.
*/
public class ClaimPayment  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClaimPaymentNum = new long();
    /**
    * Date the check was entered into this system, not the date on the check.
    */
    public DateTime CheckDate = new DateTime();
    /**
    * The amount of the check.
    */
    public Double CheckAmt = new Double();
    /**
    * The check number.
    */
    public String CheckNum = new String();
    /**
    * Bank and branch.
    */
    public String BankBranch = new String();
    /**
    * Note for this check if needed.
    */
    public String Note = new String();
    /**
    * FK to clinic.ClinicNum.  0 if no clinic.
    */
    public long ClinicNum = new long();
    /**
    * FK to deposit.DepositNum.  0 if not attached to any deposits.
    */
    public long DepositNum = new long();
    /**
    * Descriptive name of the carrier just for reporting purposes.  We use this because the CarrierNums could conceivably be different for the different claimprocs attached.
    */
    public String CarrierName = new String();
    /**
    * Date that the carrier issued the check. Date on the check.
    */
    public DateTime DateIssued = new DateTime();
    /**
    * .
    */
    public boolean IsPartial = new boolean();
    /**
    * Returns a copy of this ClaimPayment.
    */
    public ClaimPayment copy() throws Exception {
        return (ClaimPayment)this.MemberwiseClone();
    }

}


