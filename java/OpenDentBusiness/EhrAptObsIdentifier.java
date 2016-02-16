//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;


public enum EhrAptObsIdentifier
{
    /**
    * 0 - Body temperature:Temp:Enctrfrst:Patient:Qn:	Loinc code 11289-6.
    */
    BodyTemp,
    /**
    * 1 - Illness or injury onset date and time:TmStp:Pt:Patient:Qn:	Loinc code 11368-8.
    */
    DateIllnessOrInjury,
    /**
    * 2 - Age Time Patient Reported	Loinc code 21612-7.
    */
    PatientAge,
    /**
    * 3 - Diagnosis.preliminary:Imp:Pt:Patient:Nom:	Loinc code 44833-2.
    */
    PrelimDiag,
    /**
    * 4 - Triage note:Find:Pt:Emergency department:Doc:	Loinc code 54094-8.
    */
    TriageNote,
    /**
    * 5 - Oxygen saturation:MFr:Pt:BldA:Qn:Pulse oximetry	Loinc code 59408-5.
    */
    OxygenSaturation,
    /**
    * 6 - Chief complaint:Find:Pt:Patient:Nom:Reported	Loinc code 8661-1.
    */
    CheifComplaint,
    /**
    * 7 - Treating Facility Identifier	PHINQUESTION code SS001.
    */
    TreatFacilityID,
    /**
    * 8 - Treating Facility Location	PHINQUESTION code SS002.
    */
    TreatFacilityLocation,
    /**
    * 9 - Facility / Visit Type	PHINQUESTION code SS003.
    */
    VisitType
}

