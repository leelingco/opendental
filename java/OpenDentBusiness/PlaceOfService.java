//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum PlaceOfService
{
    //<summary>Used in the appointment edit window.</summary>
    /*public enum LabCaseOld{
    		///<summary>0</summary>
    		None,
    		///<summary>1</summary>
    		Sent,
    		///<summary>2</summary>
    		Received,
    		///<summary>3</summary>
    		QualityChecked};*/
    /**
    * 0. CPT code 11
    */
    Office,
    /**
    * 1. CPT code 12
    */
    PatientsHome,
    /**
    * 2. CPT code 21
    */
    InpatHospital,
    /**
    * 3. CPT code 22
    */
    OutpatHospital,
    /**
    * 4. CPT code 31
    */
    SkilledNursFac,
    /**
    * 5. CPT code 33.  In X12, a similar code AdultLivCareFac 35 is mentioned.
    */
    CustodialCareFacility,
    /**
    * 6. CPT code ?.  We use 11 for office.
    */
    OtherLocation,
    /**
    * 7. CPT code 15
    */
    MobileUnit,
    /**
    * 8. CPT code 03
    */
    School,
    /**
    * 9. CPT code 26
    */
    MilitaryTreatFac,
    /**
    * 10. CPT code 50
    */
    FederalHealthCenter,
    /**
    * 11. CPT code 71
    */
    PublicHealthClinic,
    /**
    * 12. CPT code 72
    */
    RuralHealthClinic,
    /**
    * 13. CPT code 23
    */
    EmergencyRoomHospital,
    /**
    * 14. CPT code 24
    */
    AmbulatorySurgicalCenter
}

