//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.AllergyDefm;
import OpenDentBusiness.SnomedAllergy;
import OpenDentBusiness.TableBase;

/**
* A list of diseases that can be assigned to patients.
*/
public class AllergyDefm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long AllergyDefNum = new long();
    /**
    * 
    */
    public String Description = new String();
    /**
    * Enum:SnomedAllergy SNOMED Allergy Type Code.
    */
    public SnomedAllergy Snomed = SnomedAllergy.None;
    /**
    * FK to Medication.MedicationNum. Optional.
    */
    public long MedicationNum = new long();
    /**
    * 
    */
    public AllergyDefm copy() throws Exception {
        return (AllergyDefm)this.MemberwiseClone();
    }

}


