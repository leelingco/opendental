//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* A list of medications, not attached to any particular patient.  Not allowed to delete if in use by a patient.  Not allowed to edit name once created due to possibility of damage to patient record.
*/
public class Medication  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MedicationNum = new long();
    /**
    * Name of the medication.  User can change this.  If an RxCui is present, the RxNorm string can be pulled from the in-memory table for UI display in addition to the MedName.
    */
    public String MedName = new String();
    /**
    * FK to medication.MedicationNum.  If this is a generic drug, then the GenericNum will be the same as the MedicationNum.
    */
    public long GenericNum = new long();
    /**
    * Notes.
    */
    public String Notes = new String();
    /**
    * The last date and time this row was altered.  Not user editable.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * RxNorm Code identifier.  We should have used a string type.  Used by EHR in CQM.  But the queries should use medicationpat.RxCui, NOT this RxCui, because all medicationpats (meds and orders) coming back from NewCrop will not have a FK to this medication table.  When this RxCui is modified by the user, then medicationpat.RxCui is automatically updated where medicationpat.MedicationNum matches this medication.
    */
    public long RxCui = new long();
}


