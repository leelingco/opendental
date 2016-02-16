//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.TableBase;

/**
* One electronic transaction.  Typically, one claim or response.  Or one benefit request or response.  Is constantly being expanded to include more types of transactions with clearinghouses.  Also stores printing of paper claims.  Sometimes stores a copy of what was sent.
*/
public class Etrans  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EtransNum = new long();
    /**
    * The date and time of the transaction.
    */
    public DateTime DateTimeTrans = new DateTime();
    /**
    * FK to clearinghouse.ClearinghouseNum .  Can be 0 if no clearinghouse was involved.
    */
    public long ClearingHouseNum = new long();
    /**
    * Enum:EtransType
    */
    public EtransType Etype = EtransType.ClaimSent;
    /**
    * FK to claim.ClaimNum if a claim. Otherwise 0.  Warning.  Original claim might have been deleted.  But if Canadian claim was successfully sent, then deletion will be blocked.
    */
    public long ClaimNum = new long();
    /**
    * For Canada.  Unique for every transaction sent.  Increments by one until 999999, then resets to 1.
    */
    public int OfficeSequenceNumber = new int();
    /**
    * For Canada.  Separate counter for each carrier.  Increments by one until 99999, then resets to 1.
    */
    public int CarrierTransCounter = new int();
    /**
    * For Canada.  If this claim includes secondary, then this is the counter for the secondary carrier.
    */
    public int CarrierTransCounter2 = new int();
    /**
    * FK to carrier.CarrierNum.
    */
    public long CarrierNum = new long();
    /**
    * FK to carrier.CarrierNum Only used if secondary insurance info is provided on a claim.  Necessary for Canada.
    */
    public long CarrierNum2 = new long();
    /**
    * FK to patient.PatNum This is useful in case the original claim has been deleted.  Now, we can still tell who the patient was.
    */
    public long PatNum = new long();
    /**
    * Maxes out at 999, then loops back to 1.  This is not a good key, but is a restriction of (canadian?).  So dates must also be used to isolate the correct BatchNumber key.  Specific to one clearinghouse.  Only used with e-claims.  Claim will have BatchNumber, and 997 will have matching BatchNumber. (In X12 lingo, it's a functional group number)
    */
    public int BatchNumber = new int();
    /**
    * A=Accepted, R=Rejected, blank if not able to parse.  More options will be added later.  The incoming 997 or 999 sets this flag automatically.  To find the 997 or 999, look for a matching BatchNumber with a similar date, since both the claims and the 997 or 999 will both have the same batch number.  The 997 or 999 does not have this flag set on itself.
    */
    public String AckCode = new String();
    /**
    * For sent e-claims, within each batch (functional group), each carrier gets it's own transaction set.  Since 997s and 999s acknowledge transaction sets rather than batches, we need to keep track of which transaction set each claim is part of as well as which batch it's part of.  This field can't be set as part of 997 or 999, because one 997 or 999 refers to multiple trans sets.
    */
    public int TransSetNum = new int();
    /**
    * Typical uses include indicating that the report was printed, the claim was resent, reason for rejection, etc.  For a 270, this contains the automatically generated short summary of the response.  The response could include the reason for failure, or it could be a short summary of the 271.
    */
    public String Note = new String();
    /**
    * FK to etransmessagetext.EtransMessageTextNum.  Can be 0 if there is no message text.  Multiple Etrans objects can refer to the same message text, very common in a batch.
    */
    public long EtransMessageTextNum = new long();
    /**
    * FK to etrans.EtransNum.  Only has a non-zero value if there exists an ack etrans, like a 997, 999, 277ack, 271, 835, or ackError.  There can be only one ack for any given etrans, but one ack can apply to multiple etran's that were sent as one batch.
    */
    public long AckEtransNum = new long();
    /**
    * FK to insplan.PlanNum.  Used if EtransType.BenefitInquiry270 and BenefitResponse271 and Eligibility_CA.
    */
    public long PlanNum = new long();
    /**
    * FK to inssub.InsSubNum.  Used if EtransType.BenefitInquiry270 and BenefitResponse271 and Eligibility_CA.
    */
    public long InsSubNum = new long();
    /**
    * 
    */
    public Etrans copy() throws Exception {
        return (Etrans)this.MemberwiseClone();
    }

}


