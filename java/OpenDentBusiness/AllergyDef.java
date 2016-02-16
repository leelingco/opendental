//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.SnomedAllergy;
import OpenDentBusiness.TableBase;

/**
* An allergy definition.  Gets linked to an allergy and patient.  Allergies will not show in CCD messages unless they have a valid Medication (that has an RxNorm) or UniiCode.
*/
public class AllergyDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long AllergyDefNum = new long();
    /**
    * Name of the drug.  User can change this.  If an RxCui is present, the RxNorm string can be pulled from the in-memory table for UI display in addition to the Description.
    */
    public String Description = new String();
    /**
    * Because user can't delete.
    */
    public boolean IsHidden = new boolean();
    /**
    * The last date and time this row was altered.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Enum:SnomedAllergy SNOMED Allergy Type Code.  Only used to create CCD in FormSummaryOfCare.
    */
    public SnomedAllergy SnomedType = SnomedAllergy.None;
    /**
    * FK to Medication.MedicationNum.  Optional, only used with CCD messages.
    */
    public long MedicationNum = new long();
    /**
    * The Unii code for the Allergen.  Optional, but there must be either a MedicationNum or a UniiCode.  Used to create CCD in FormSummaryOfCare, or set during CCD allergy reconcile.
    */
    public String UniiCode = new String();
    /**
    * 
    */
    public AllergyDef copy() throws Exception {
        return (AllergyDef)this.MemberwiseClone();
    }

}


