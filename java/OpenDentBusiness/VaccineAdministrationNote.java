//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineAdministrationNote
{
    /**
    * Exported in HL7 RXA-9.  Corresponds to CDC code set NIP001 (http://hl7v2-iz-testing.nist.gov/mu-immunization/).0 - Code 00.  Default.
    */
    NewRecord,
    /**
    * 1 - Code 01
    */
    HistoricalSourceUnknown,
    /**
    * 2 - Code 02
    */
    HistoricalOtherProvider,
    /**
    * 3 - Code 03
    */
    HistoricalParentsWrittenRecord,
    /**
    * 4 - Code 04
    */
    HistoricalParentsRecall,
    /**
    * 5 - Code 05
    */
    HistoricalOtherRegistry,
    /**
    * 6 - Code 06
    */
    HistoricalBirthCertificate,
    /**
    * 7 - Code 07
    */
    HistoricalSchoolRecord,
    /**
    * 8 - Code 08
    */
    HistoricalPublicAgency
}

