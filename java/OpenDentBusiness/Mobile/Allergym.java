//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Allergym;
import OpenDentBusiness.TableBase;

/**
* Links allergies to patients. Patient portal version
*/
public class Allergym  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long AllergyNum = new long();
    /**
    * FK to allergydef.AllergyDefNum
    */
    public long AllergyDefNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * 
    */
    public String Reaction = new String();
    /**
    * 
    */
    public boolean StatusIsActive = new boolean();
    /**
    * The historical date that the patient had the adverse reaction to this agent.
    */
    public DateTime DateAdverseReaction = new DateTime();
    /**
    * 
    */
    public Allergym copy() throws Exception {
        return (Allergym)this.MemberwiseClone();
    }

}


