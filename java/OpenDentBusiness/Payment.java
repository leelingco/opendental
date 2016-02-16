//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* A patient payment.  Always has at least one split.
*/
public class Payment  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PayNum = new long();
    /**
    * FK to definition.DefNum.  This will be 0 if this is an income transfer to another provider.
    */
    public long PayType = new long();
    /**
    * The date that the payment displays on the patient account.
    */
    public DateTime PayDate = new DateTime();
    /**
    * Amount of the payment.  Must equal the sum of the splits.
    */
    public double PayAmt = new double();
    /**
    * Check number is optional.
    */
    public String CheckNum = new String();
    /**
    * Bank-branch for checks.
    */
    public String BankBranch = new String();
    /**
    * Any admin note.  Not for patient to see.  Length 4000.
    */
    public String PayNote = new String();
    /**
    * Set to true to indicate that a payment is split.  Just makes a few functions easier.  Might be eliminated.
    */
    public boolean IsSplit = new boolean();
    /**
    * FK to patient.PatNum.  The patient where the payment entry will show.  But only the splits affect account balances.  This has a value even if the 'payment' is actually an income transfer to another provider.
    */
    public long PatNum = new long();
    /**
    * FK to clinic.ClinicNum.  Can be 0. Copied from patient.ClinicNum when creating payment, but user can override.  Not used in provider income transfers.  Cannot be used in financial reporting when grouping by clinic, because payments may be split between clinics.
    */
    public long ClinicNum = new long();
    /**
    * The date that this payment was entered.  Not user editable.
    */
    public DateTime DateEntry = new DateTime();
    /**
    * FK to deposit.DepositNum.  0 if not attached to any deposits.  Cash does not usually get attached to a deposit; only checks.
    */
    public long DepositNum = new long();
    /**
    * Text of printed receipt if the payment was done electronically. Allows reprinting if needed. Only used for PayConnect at the moment, but plans to use for XCharge as well.
    */
    public String Receipt = new String();
    /**
    * True if this was an automatically added recurring CC charge rather then one entered by the user.  This was set to true for all historical entries before version 11.1, but will be accurate after that.
    */
    public boolean IsRecurringCC = new boolean();
}


