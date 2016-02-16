//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package EhrLaboratories;


public enum HL70125
{
    /**
    * Value Type.  OID:2.16.840.1.113883.12.125  HL70369 code:HL70125.  Source HL7 2.5.1 Labratory Reporting Interface documentation.
    * This enum is also used in FormPatListElementEditEHR2014.cs and assumes the order of this enum does not change. If it does, the combo box filled with these values must also be updated.0 - Coded Entry.
    * Usage: R Comment: When sending text data in OBX-5, use either the ST, TX or FT data types.
    */
    CE,
    /**
    * 1 - Coded with Exceptions.
    * Usage: R Data Type Flavor: CWE_CRO Comment: Data type to be used where it is important to communicate the coding system and coding system version with the coded result being reported. Pre-adopted from Version 2.6.  This Implementation Guide has specially constrained versions of the CWE data type in Section 2.2 through 2.4. The CWE_CR format shall be used for OBX-5. When sending text data in OBX-5, use either the ST, TX or FT data types.
    */
    CWE,
    /**
    * //Extended Composite ID With Check Digit.
    * //Usage: O 
    * //Data Type Flavor: Varies 
    * //Comment: Use the appropriate CX flavor (CX-GU or CX-NG or base standard) depending on the format of the observation value and as agreed to between the sender/receiver.
    */
    //CX,
    /**
    * 2 - Date.
    * Usage: R
    */
    DT,
    /**
    * //Encapsulated Data.
    * //Usage: O 
    * //Comment: When using the Source Application ID component it should use the HD data type formatting considerations outlined in the base standard, not the constrained HD definitions in this IG.
    */
    //ED,
    /**
    * 3 - Formatted Text (Display).
    * Usage: R Comment: Field using the FT data type to carry a text result value. This is intended for display. The text may contain formatting escape sequences as described in the data types section. Numeric results and numeric results with units of measure should not be reported as text. These should be reported as NM or SN numeric results, with the units of measure in OBX-6.
    */
    FT,
    /**
    * 4 - Numeric.
    * Usage: R Comment: Field using the NM data type to carry a numeric result value. The only non-numeric characters allowed in this field are a leading plus (+) or minus (-) sign. The structured numeric (SN) data type should be used for conveying inequalities, ranges, ratios, etc. The units for the numeric value should be reported in OBX-6.
    */
    NM,
    /**
    * //Reference Pointer.
    * //Usage: O 
    * //Comment: When using the Application ID component it should use the HD data type formatting considerations outlined in the base standard, not the constrained HD definitions in this IG.
    */
    //RP,
    /**
    * 5 - Structured Numeric.
    * Usage: R Comment: Field using the SN data type to carry a structured numeric result value. Structured numeric include intervals (^0^-^1), ratios (^1^/^2 or ^1^: ^2), inequalities (<^10), or categorical results (2^+). The units for the structured numeric value should be reported in OBX-6.
    */
    SN,
    /**
    * 6 - String Data.
    * Usage: R Comment: Field using the ST data type to carry a short text result value. Numeric results and numeric results with units of measure should not be reported as text. These shall be reported as NM or SN numeric results, with the units of measure in OBX-6.
    */
    ST,
    /**
    * 7 - Time.
    * Usage: R Comment: The timezone offset shall adhere to the use of the TimeZone Offset profile.
    */
    TM,
    /**
    * 8 - Time Stamp (Date and Time).
    * Usage: R Data Type Flavor: TS_0 Comment: The timezone offset shall adhere to the use of the TimeZone Offset profile and associated discussion if the granularity involves hh or “more”.
    */
    TS,
    /**
    * 9 - Text Data (Display).
    * Usage: R Comment: Field using the TX data type to carry a text result value this is intended for display. Numeric results and numeric results with units of measure should not be reported as text. These should be reported as NM or SN numeric results, with the units of measure in OBX-6.
    */
    TX
}

