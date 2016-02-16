//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Links medications to patients.  For ehr, some of these can be considered 'medication orders', but only if they contain a PatNote (instructions), a ProvNum, and a DateStart.
*/
public class MedicationPat  extends TableBase 
{
    /**
    * Primary key.
    */
    public long MedicationPatNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to medication.MedicationNum.  If 0, implies that the medication order came from NewCrop.
    */
    public long MedicationNum = new long();
    /**
    * Medication notes specific to this patient.
    */
    public String PatNote = new String();
    /**
    * The last date and time this row was altered.  Not user editable.  Will be set to NOW by OD if this patient gets an OnlinePassword assigned.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Date that the medication was started.  Can be minval if unknown.
    */
    public DateTime DateStart = new DateTime();
    /**
    * Date that the medication was stopped.  Can be minval if unknown.  If minval, then the medication is not "discontinued".  If prior to today, then the medication is "discontinued".  If today or a future date, then not discontinued yet.
    */
    public DateTime DateStop = new DateTime();
    /**
    * FK to provider.ProvNum. Can be 0. Gets set to the patient's primary provider when adding a new med.  If adding the med from EHR, gets set to the ProvNum of the logged-in user.
    */
    public long ProvNum = new long();
    /**
    * Only use when MedicationNum=0.  For medication orders pulled back from NewCrop during synch.
    */
    public String MedDescript = new String();
    /**
    * For NewCrop medical orders, corresponds to the RxCui of the prescription (NewCrop only returns a value sometimes).  Otherwise, this field is synched with the medication.RxCui field based on medication.MedicationNum.  We should have used a string type.  The only purpose of this field is so that when CCDs are created, we have structured data to put in the XML, not just plain text.  Allergies exported in CCD do not look at this table, but only at the medication table.  Medications require MedicationPat.RxCui or Medication.RxCui to be exported on CCD.
    */
    public long RxCui = new long();
    /**
    * Only use when MedicationNum=0.  For medication orders pulled back from NewCrop during synch.  The NewCrop GUID which uniquely identifies the prescription corresponding to the medical order. Allows us to update existing NewCrop medical orders when refreshing prescriptions in the Chart (similar to how prescriptions are updated).
    */
    public String NewCropGuid = new String();
    /**
    * If NewCrop is used to prescribe a medication, a medication order is imported automatically into Open Dental.  If a provider is logged in, then this is CPOE (Computerized Provider Order Entry), and this will be true.   Or, if a provider is logged in and Rx entered through OD, it's also CPOE.  If a staff person is logged in, and enters an Rx through NewCrop or OD, then this is non-CPOE, so false.
    */
    public boolean IsCpoe = new boolean();
}


