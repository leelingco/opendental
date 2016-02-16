//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RepeatCharge;
import OpenDentBusiness.TableBase;

/**
* Each row represents one charge that will be added monthly.
*/
public class RepeatCharge  extends TableBase 
{
    /**
    * Primary key
    */
    public long RepeatChargeNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to procedurecode.ProcCode.  The code that will be added to the account as a completed procedure.
    */
    public String ProcCode = new String();
    /**
    * The amount that will be charged.  The amount from the procedurecode will not be used.  This way, a repeating charge cannot be accidentally altered.
    */
    public double ChargeAmt = new double();
    /**
    * The date of the first charge.  Charges will always be added on the same day of the month as the start date.  If more than one month goes by, then multiple charges will be added.
    */
    public DateTime DateStart = new DateTime();
    /**
    * The last date on which a charge is allowed.  So if you want 12 charges, and the start date is 8/1/05, then the stop date should be 7/1/05, not 8/1/05.  Can be blank (0001-01-01) to represent a perpetual repeating charge.
    */
    public DateTime DateStop = new DateTime();
    /**
    * Any note for internal use.
    */
    public String Note = new String();
    /**
    * Indicates that the note should be copied to the corresponding procedure billing note.
    */
    public boolean CopyNoteToProc = new boolean();
    /**
    * Set to true to have a claim automatically created for the patient with the procedure that is attached to this repeating charge.
    */
    public boolean CreatesClaim = new boolean();
    /**
    * Defaulted to true.  Set to false to disable the repeating charge.  This allows patients to have repeating charges in their history that are not active.  Used mainly for repeating charges with notes that should not be deleted.
    */
    public boolean IsEnabled = new boolean();
    /**
    * 
    */
    public RepeatCharge copy() throws Exception {
        return (RepeatCharge)this.MemberwiseClone();
    }

}


