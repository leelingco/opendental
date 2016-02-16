//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.TableBase;

/**
* Multiple subscribers can have the same insurance plan.  But the patplan table is still what determines coverage for individual patients.
*/
public class InsSub  extends TableBase 
{
    /**
    * Primary key.
    */
    public long InsSubNum = new long();
    /**
    * FK to insplan.PlanNum.
    */
    public long PlanNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long Subscriber = new long();
    /**
    * Date plan became effective.
    */
    public DateTime DateEffective = new DateTime();
    /**
    * Date plan was terminated
    */
    public DateTime DateTerm = new DateTime();
    /**
    * Release of information signature is on file.
    */
    public boolean ReleaseInfo = new boolean();
    /**
    * Assignment of benefits signature is on file.  For Canada, this handles Payee Code, F01.  Option to pay other third party is not included.
    */
    public boolean AssignBen = new boolean();
    /**
    * Usually SSN, but can also be changed by user.  No dashes. Not allowed to be blank.
    */
    public String SubscriberID = new String();
    /**
    * User doesn't usually put these in.  Only used when automatically requesting benefits, such as with Trojan.  All the benefits get stored here in text form for later reference.  Not at plan level because might be specific to subscriber.  If blank, we try to display a benefitNote for another subscriber to the plan.
    */
    public String BenefitNotes = new String();
    /**
    * Use to store any other info that affects coverage.
    */
    public String SubscNote = new String();
    /**
    * Returns a copy of this InsPlan.
    */
    public InsSub copy() throws Exception {
        return (InsSub)this.MemberwiseClone();
    }

}


