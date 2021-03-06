//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:56 PM
//

package OpenDentBusiness;


public enum ResponseDenyCodeType
{
    /**
    * 
    */
    PatientUnknownToThePrescriber,
    /**
    * 
    */
    PatientNeverUnderPrescriberCare,
    /**
    * 
    */
    PatientNoLongerUnderPrescriberCare,
    /**
    * 
    */
    PatientHasRequestedRefillTooSoon,
    /**
    * 
    */
    MedicationNeverPrescribedForThePatient,
    /**
    * 
    */
    PatientShouldContactPrescriberFirst,
    /**
    * 
    */
    RefillNotAppropriate,
    /**
    * 
    */
    PatientHasPickedUpPrescription,
    /**
    * 
    */
    PatientHasPickedUpPartialFillOfPrescription,
    /**
    * 
    */
    PatientHasNotPickedUpPrescriptionDrugReturnedToStock,
    /**
    * 
    */
    ChangeNotAppropriate,
    /**
    * 
    */
    PatientNeedsAppointment,
    /**
    * 
    */
    PrescriberNotAssociatedWithThisPracticeOrLocation,
    /**
    * 
    */
    NoAttemptWillBeMadeToObtainPriorAuthorization,
    /**
    * 
    */
    DeniedNewPrescriptionToFollow
}

