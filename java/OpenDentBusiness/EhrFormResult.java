//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;


public enum EhrFormResult
{
    /**
    * When FormEHR closes, the result will be one of these.  Different results will lead to different behaviors.
    */
    None,
    RxSelect,
    RxEdit,
    Medical,
    PatientEdit,
    Online,
    MedReconcile,
    Referrals,
    MedicationPatNew,
    MedicationPatEdit,
    PatientSelect
}

//When this form closes, OD will set the current patient to PatNum