//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Medicationm;
import OpenDentBusiness.TableBase;

/**
* A list of medications, not attached to any particular patient. Patient portal version
*/
public class Medicationm  extends TableBase 
{
    /**
    * Primary key 1.
    */
    public long CustomerNum = new long();
    /**
    * Primary key 2.
    */
    public long MedicationNum = new long();
    /**
    * Name of the medication.
    */
    public String MedName = new String();
    /**
    * FK to medication.MedicationNum.  If this is a generic drug, then the GenericNum will be the same as the MedicationNum.
    */
    public long GenericNum = new long();
    /**
    * RxNorm Code identifier.  FK to an in-memory dictionary of RxCui/RxNorm mappings.
    */
    public long RxCui = new long();
    /**
    * 
    */
    public Medicationm copy() throws Exception {
        return (Medicationm)this.MemberwiseClone();
    }

}


