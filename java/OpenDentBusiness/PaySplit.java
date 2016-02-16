//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.TableBase;

/**
* Always attached to a payment.  Always affects exactly one patient account and one provider.
*/
public class PaySplit  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SplitNum = new long();
    /**
    * Amount of split.
    */
    public double SplitAmt = new double();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Procedure date.  Typically only used if tied to a procedure.  In older versions (before 7.0), this was the date that showed on the account.  Frequently the same as the date of the payment, but not necessarily.  Not when the payment was made.  This is what the aging will be based on in a future version.
    */
    public DateTime ProcDate = new DateTime();
    /**
    * FK to payment.PayNum.  Every paysplit must be linked to a payment.
    */
    public long PayNum = new long();
    /**
    * No longer used.
    */
    public boolean IsDiscount = new boolean();
    /**
    * No longer used
    */
    public byte DiscountType = new byte();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * FK to payplan.PayPlanNum.  0 if not attached to a payplan.
    */
    public long PayPlanNum = new long();
    /**
    * Date always in perfect synch with Payment date.
    */
    public DateTime DatePay = new DateTime();
    /**
    * FK to procedurelog.ProcNum.  0 if not attached to a procedure.
    */
    public long ProcNum = new long();
    /**
    * Date this paysplit was created.  User not allowed to edit.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * FK to definition.DefNum.  Usually 0 unless this is a special unearned split.
    */
    public long UnearnedType = new long();
    /**
    * FK to clinic.ClinicNum.  Can be 0.  Need not match the ClinicNum of the Payment, because a payment can be split between clinics.
    */
    public long ClinicNum = new long();
    /**
    * Returns a copy of this PaySplit.
    */
    public PaySplit copy() throws Exception {
        return (PaySplit)this.MemberwiseClone();
    }

}


