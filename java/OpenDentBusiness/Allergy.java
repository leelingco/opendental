//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Allergy;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* An allergy attached to a patient and linked to an AllergyDef.
*/
public class Allergy  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AllergyNum = new long();
    /**
    * FK to allergydef.AllergyDefNum
    */
    public long AllergyDefNum = new long();
    /**
    * FK to patient.PatNum
    */
    public long PatNum = new long();
    /**
    * Adverse reaction description.
    */
    public String Reaction = new String();
    /**
    * True if still an active allergy.  False helps hide it from the list of active allergies.
    */
    public boolean StatusIsActive = new boolean();
    /**
    * To be used for synch with web server for CertTimelyAccess.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * The historical date that the patient had the adverse reaction to this agent.
    */
    public DateTime DateAdverseReaction = new DateTime();
    /**
    * Snomed code for reaction.  Optional and independent of the Reaction text field.  Not needed for reporting.  Only used for CCD export/import.
    */
    public String SnomedReaction = new String();
    /**
    * 
    */
    public Allergy copy() throws Exception {
        return (Allergy)this.MemberwiseClone();
    }

}


