//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RefAttach;
import OpenDentBusiness.ReferralToStatus;
import OpenDentBusiness.TableBase;

/**
* Attaches a referral to a patient.
*/
public class RefAttach  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RefAttachNum = new long();
    /**
    * FK to referral.ReferralNum.
    */
    public long ReferralNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * Order to display in patient info. One-based.  Will be automated more in future.
    */
    public int ItemOrder = new int();
    /**
    * Date of referral.
    */
    public DateTime RefDate = new DateTime();
    //
    /**
    * true=from, false=to
    */
    public boolean IsFrom = new boolean();
    /**
    * Enum:ReferralToStatus 0=None,1=Declined,2=Scheduled,3=Consulted,4=InTreatment,5=Complete.
    */
    public ReferralToStatus RefToStatus = ReferralToStatus.None;
    /**
    * Why the patient was referred out, or less commonly, the circumstances of the referral source.
    */
    public String Note = new String();
    /**
    * Used to track ehr events.  All outgoing referrals default to true.  The incoming ones get a popup asking if it's a transition of care.
    */
    public boolean IsTransitionOfCare = new boolean();
    /**
    * FK to procedurelog.ProcNum
    */
    public long ProcNum = new long();
    /**
    * .
    */
    public DateTime DateProcComplete = new DateTime();
    /**
    * FK to provider.ProvNum.  Used when refering out a patient to track the reffering provider for EHR meaningfull use.
    */
    public long ProvNum = new long();
    /**
    * Returns a copy of this RefAttach.
    */
    public RefAttach copy() throws Exception {
        return (RefAttach)this.MemberwiseClone();
    }

}


