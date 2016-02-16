//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;


public enum SheetTypeEnum
{
    /**
    * Different types of sheets that can be used.0-Requires SheetParameter for PatNum. Does not get saved to db.
    */
    LabelPatient,
    /**
    * 1-Requires SheetParameter for CarrierNum. Does not get saved to db.
    */
    LabelCarrier,
    /**
    * 2-Requires SheetParameter for ReferralNum. Does not get saved to db.
    */
    LabelReferral,
    /**
    * 3-Requires SheetParameters for PatNum,ReferralNum.
    */
    ReferralSlip,
    /**
    * 4-Requires SheetParameter for AptNum. Does not get saved to db.
    */
    LabelAppointment,
    /**
    * 5-Requires SheetParameter for RxNum.
    */
    Rx,
    /**
    * 6-Requires SheetParameter for PatNum.
    */
    Consent,
    /**
    * 7-Requires SheetParameter for PatNum.
    */
    PatientLetter,
    /**
    * 8-Requires SheetParameters for PatNum,ReferralNum.
    */
    ReferralLetter,
    /**
    * 9-Requires SheetParameter for PatNum.
    */
    PatientForm,
    /**
    * 10-Requires SheetParameter for AptNum.  Does not get saved to db.
    */
    RoutingSlip,
    /**
    * 11-Requires SheetParameter for PatNum.
    */
    MedicalHistory,
    /**
    * 12-Requires SheetParameter for PatNum, LabCaseNum.
    */
    LabSlip,
    /**
    * 13-Requires SheetParameter for PatNum.
    */
    ExamSheet,
    /**
    * 14-Requires SheetParameter for PatNum.
    */
    DepositSlip
}

