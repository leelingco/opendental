//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineRefusalReason
{
    /**
    * Exported in HL7 RXA-18.  Corresponds to CDC code set NIP002 (http://hl7v2-iz-testing.nist.gov/mu-immunization/).0 - No code.  Default.  Not sent in HL7 messages.  Only used in UI.
    */
    None,
    /**
    * 1 - Code 00
    */
    ParentalDecision,
    /**
    * 2 - Code 01
    */
    ReligiousExemption,
    /**
    * 3 - Code 02
    */
    Other,
    /**
    * 4 - Code 03
    */
    PatientDecision
}

