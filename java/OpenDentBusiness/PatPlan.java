//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PatPlan;
import OpenDentBusiness.Relat;
import OpenDentBusiness.TableBase;

/**
* Each row represents the linking of one insplan to one patient for current coverage.  Dropping a plan will delete the entry in this table.  Deleting a plan will delete the actual insplan (if no dependencies).
*/
public class PatPlan  extends TableBase 
{
    /**
    * Primary key
    */
    public long PatPlanNum = new long();
    /**
    * FK to  patient.PatNum.  The patient who currently has the insurance.  Not the same as the subscriber.
    */
    public long PatNum = new long();
    /**
    * Number like 1, 2, 3, etc.  Represents primary ins, secondary ins, tertiary ins, etc. 0 is not used
    */
    public byte Ordinal = new byte();
    /**
    * For informational purposes only. For now, we lose the previous feature which let us set isPending without entering a plan.  Now, you have to enter the plan in order to check this box.
    */
    public boolean IsPending = new boolean();
    /**
    * Enum:Relat Remember that this may need to be changed in the Claim also, if already created.
    */
    public Relat Relationship = Relat.Self;
    /**
    * An optional patient ID which will override the insplan.SubscriberID on eclaims.  For Canada, this holds the Dependent Code, C17 and E17, and in that use it doesn't override subscriber id, but instead supplements it.
    */
    public String PatID = new String();
    /**
    * FK to inssub.InsSubNum.  Gives info about the subscriber.
    */
    public long InsSubNum = new long();
    /**
    * 
    */
    public PatPlan copy() throws Exception {
        return (PatPlan)this.MemberwiseClone();
    }

}


