//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineObsIdentifier
{
    /**
    * Corresponds to HL7 value set NIP003 (http://hl7v2-iz-testing.nist.gov/mu-immunization/).
    * This code set is a subset of LOINC codes.  Used in HL7 OBX-3.0 - LOINC code 29768-9.  Date vaccine information statement published:TmStp:Pt:Patient:Qn: (default)
    */
    DatePublished,
    /**
    * 1 - LOINC code 29769-7.  Date vaccine information statement presented:TmStp:Pt:Patient:Qn:
    */
    DatePresented,
    /**
    * 2 - LOINC code 30944-3.  Date of vaccination temporary contraindication and or precaution expiration:TmStp:Pt:Patient:Qn:
    */
    DatePrecautionExpiration,
    /**
    * 3 - LOINC code 30945-0.  Vaccination contraindication and or precaution:Find:Pt:Patient:Nom:
    */
    Precaution,
    /**
    * 4 - LOINC code 30946-8.  Date vaccination contraindication and or precaution effective:TmStp:Pt:Patient:Qn:
    */
    DatePrecautionEffective,
    /**
    * 5 - LOINC code 30956-7.  Type:ID:Pt:Vaccine:Nom:
    */
    TypeOf,
    /**
    * 6 - LOINC code 30963-3.  Funds vaccine purchased with:Find:Pt:Patient:Nom:
    */
    FundsPurchasedWith,
    /**
    * 7 - LOINC code 30973-2.  Dose number:Num:Pt:Patient:Qn:
    */
    DoseNumber,
    /**
    * 8 - LOINC code 30979-9.  Vaccines due next:Cmplx:Pt:Patient:Set:
    */
    NextDue,
    /**
    * 9 - LOINC code 30980-7.  Date vaccine due:TmStp:Pt:Patient:Qn:
    */
    DateDue,
    /**
    * 10 - LOINC code 30981-5.  Earliest date to give:TmStp:Pt:Patient:Qn:
    */
    DateEarliestAdminister,
    /**
    * 11 - LOINC code 30982-3.  Reason applied by forcast logic to project this vaccine:Find:Pt:Patient:Nom:
    */
    ReasonForcast,
    /**
    * 12 - LOINC code 31044-1.  Reaction:Find:Pt:Patient:Nom:
    */
    Reaction,
    /**
    * 13 - LOINC code 38890-0.  Vaccine component type:ID:Pt:Vaccine:Nom:
    */
    ComponentType,
    /**
    * 14 - LOINC code 46249-9.  Vaccination take-response type:Prid:Pt:Patient:Nom:
    */
    TakeResponseType,
    /**
    * 15 - LOINC code 46250-7.  Vaccination take-response date:TmStp:Pt:Patient:Qn:
    */
    DateTakeResponse,
    /**
    * 16 - LOINC code 59779-9.  Immunization schedule used:Find:Pt:Patient:Nom:
    */
    ScheduleUsed,
    /**
    * 17 - LOINC code 59780-7.  Immunization series:Find:Pt:Patient:Nom:
    */
    Series,
    /**
    * 18 - LOINC code 59781-5.  Dose validity:Find:Pt:Patient:Ord:
    */
    DoseValidity,
    /**
    * 19 - LOINC code 59782-3.  Number of doses in primary immunization series:Num:Pt:Patient:Qn:
    */
    NumDosesPrimary,
    /**
    * 20 - LOINC code 59783-1.  Status in immunization series:Find:Pt:Patient:Nom:
    */
    StatusInSeries,
    /**
    * 21 - LOINC code 59784-9.  Disease with presumed immunity:Find:Pt:Patient:Nom:
    */
    DiseaseWithImmunity,
    /**
    * 22 - LOINC code 59785-6.  Indication for Immunization:Find:Pt:Patient:Nom:
    */
    Indication,
    /**
    * 23 - LOINC code 64994-7.  Vaccine fund pgm elig cat
    */
    FundPgmEligCat,
    /**
    * 24 - LOINC code 69764-9.  Document type
    */
    DocumentType
}

