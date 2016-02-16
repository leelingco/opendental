//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineObsType
{
    /**
    * Corresponds to HL7 table 0125.0 - Code CE.  Coded entry. (default)
    */
    Coded,
    /**
    * 1 - Code DT.  Date (no time).
    */
    Dated,
    /**
    * 2 - Code NM.  Numeric.
    */
    Numeric,
    /**
    * 3 - Code ST.  String.
    */
    Text,
    /**
    * 4 - Code TS.  Date and time.
    */
    DateAndTime
}

