//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PayPlan;
import OpenDentBusiness.TableBase;

/**
* Each row represents one signed agreement to make payments.
*/
public class PayPlan  extends TableBase 
{
    /**
    * Primary key
    */
    public long PayPlanNum = new long();
    /**
    * FK to patient.PatNum.  The patient who had the treatment done.
    */
    public long PatNum = new long();
    /**
    * FK to patient.PatNum.  The person responsible for the payments.  Does not need to be in the same family as the patient.  Will be 0 if PlanNum and InsSubNum have values.
    */
    public long Guarantor = new long();
    /**
    * Date that the payment plan will display in the account.
    */
    public DateTime PayPlanDate = new DateTime();
    /**
    * Annual percentage rate.  eg 18.  This does not take into consideration any late payments, but only the percentage used to calculate the amortization schedule.
    */
    public double APR = new double();
    /**
    * Generally used to archive the terms when the amortization schedule is created.
    */
    public String Note = new String();
    /**
    * FK to insplan.PlanNum.  Will be 0 if standard payment plan.  But if this is being used to track expected insurance payments, then this will be the foreign key to insplan.PlanNum, and Guarantor will be 0.
    */
    public long PlanNum = new long();
    /**
    * The amount of the treatment that has already been completed.  This should match the sum of the principal amounts for most situations.  But if the procedures have not yet been completed, and the payment plan is to make any sense, then this number must be changed.
    */
    public double CompletedAmt = new double();
    /**
    * FK to inssub.InsSubNum.  Will be 0 if standard payment plan.  But if this is being used to track expected insurance payments, then this will be the foreign key to inssub.InsSubNum, and Guarantor will be 0.
    */
    public long InsSubNum = new long();
    /**
    * 
    */
    public PayPlan copy() throws Exception {
        return (PayPlan)this.MemberwiseClone();
    }

}


