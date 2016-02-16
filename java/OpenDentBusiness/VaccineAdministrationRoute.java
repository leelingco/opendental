//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;


public enum VaccineAdministrationRoute
{
    /**
    * Exported in HL7 RXR-1.  Corresponds to HL7 table 0162 (guide page 200).0 - No code.  Default.  Not sent in HL7 messages.  Used in UI only.
    */
    None,
    /**
    * 1 - Code ID.
    */
    Intradermal,
    /**
    * 2 - Code IM.
    */
    Intramuscular,
    /**
    * 3 - Code NS.
    */
    Nasal,
    /**
    * 4 - Code IV.
    */
    Intravenous,
    /**
    * 5 - Code PO.
    */
    Oral,
    /**
    * 6 - Code OTH.
    */
    Other,
    /**
    * 7 - Code SC.
    */
    Subcutaneous,
    /**
    * 8 - Code TD.
    */
    Transdermal
}

