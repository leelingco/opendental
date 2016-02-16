//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum PatientRaceOld
{
    /**
    * Deprecated, use patientrace table instead.  Temporarily used for converting old patient races to patientrace entries and screening.  Race and ethnicity for patient. Used by public health.  The problem is that everyone seems to want different choices.  If we give these choices their own table, then we also need to include mapping functions.  These are currently used in ArizonaReports, HL7 w ECW, and EHR.  Foreign users would like their own mappings.0
    */
    Unknown,
    /**
    * 1
    */
    Multiracial,
    /**
    * 2
    */
    HispanicLatino,
    /**
    * 3
    */
    AfricanAmerican,
    /**
    * 4
    */
    White,
    /**
    * 5
    */
    HawaiiOrPacIsland,
    /**
    * 6
    */
    AmericanIndian,
    /**
    * 7
    */
    Asian,
    /**
    * 8
    */
    Other,
    /**
    * 9
    */
    Aboriginal,
    /**
    * 10 - Required by EHR.
    */
    BlackHispanic
}

