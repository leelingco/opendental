//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.TableBase;

/**
* One of the dated charges attached to a payment plan.  This has nothing to do with payments, but rather just causes the amount due to increase on the date of the charge.  The amount of the charge is the sum of the principal and the interest.
*/
public class PayPlanCharge  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PayPlanChargeNum = new long();
    /**
    * FK to payplan.PayPlanNum.
    */
    public long PayPlanNum = new long();
    /**
    * FK to patient.PatNum.  The guarantor account that each charge will affect.
    */
    public long Guarantor = new long();
    /**
    * FK to patient.PatNum.  The patient account that the principal gets removed from.
    */
    public long PatNum = new long();
    /**
    * The date that the charge will show on the patient account.  Any charge with a future date will not show on the account yet and will not affect the balance.
    */
    public DateTime ChargeDate = new DateTime();
    /**
    * The principal portion of this payment.
    */
    public double Principal = new double();
    /**
    * The interest portion of this payment.
    */
    public double Interest = new double();
    /**
    * Any note about this particular payment plan charge
    */
    public String Note = new String();
    /**
    * FK to provider.ProvNum.  Since there is no ProvNum field at the payplan level, the provider must be the same for all payplancharges.  It's initially assigned as the patient priProv.  Payments applied should be to this provnum, although the current user interface does not help with this.
    */
    public long ProvNum = new long();
    /**
    * FK to clinic.ClinicNum.  Since there is no ClincNum field at the payplan level, the clinic must be the same for all payplancharges.  It's initially assigned using the patient clinic.  Payments applied should be to this clinic, although the current user interface does not help with this.
    */
    public long ClinicNum = new long();
    /**
    * 
    */
    public PayPlanCharge copy() throws Exception {
        return (PayPlanCharge)this.MemberwiseClone();
    }

}


