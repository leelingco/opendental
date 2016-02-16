//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineCompletionStatus
{
    /**
    * Exported in HL7 RXA-20.  Corresponds to HL7 table 0322 (guide page 225).0 - Code CP.  Default.
    */
    Complete,
    /**
    * 1 - Code RE
    */
    Refused,
    /**
    * 2 - Code NA
    */
    NotAdministered,
    /**
    * 3 - Code PA
    */
    PartiallyAdministered
}

