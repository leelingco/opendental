//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.InstallmentPlan;
import OpenDentBusiness.TableBase;

/**
* Simpler than a payment plan.  Does not affect running account balances.  Allows override of finance charges.  Affects the "pay now" on statements.  Only one installmentplan is allowed for a family, attached to guarantor only.  This is loosely enforced.
*/
public class InstallmentPlan  extends TableBase 
{
    /**
    * Primary key.
    */
    public long InstallmentPlanNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Date payment plan agreement was made.
    */
    public DateTime DateAgreement = new DateTime();
    /**
    * Date of first payment.
    */
    public DateTime DateFirstPayment = new DateTime();
    /**
    * Amount of monthly payment.
    */
    public double MonthlyPayment = new double();
    /**
    * Annual Percentage Rate. e.g. 12.
    */
    public float APR = new float();
    /**
    * Note
    */
    public String Note = new String();
    /**
    * Returns a copy of this InstallmentPlan.
    */
    public InstallmentPlan copy() throws Exception {
        return (InstallmentPlan)this.MemberwiseClone();
    }

}


