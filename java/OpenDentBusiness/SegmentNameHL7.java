//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum SegmentNameHL7
{
    /**
    * The items in this enumeration can be freely rearranged without damaging the database.  But can't change spelling or remove existing item.Db Resource Appointment Information
    */
    AIG,
    /**
    * Location Resource Appointment Information
    */
    AIL,
    /**
    * Personnel Resource Appointment Information
    */
    AIP,
    /**
    * Diagnosis Information
    */
    DG1,
    /**
    * Event Type
    */
    EVN,
    /**
    * Financial Transaction Information
    */
    FT1,
    /**
    * Guarantor Information
    */
    GT1,
    /**
    * Insurance Information
    */
    IN1,
    /**
    * Message Acknowledgment
    */
    MSA,
    /**
    * Message Header
    */
    MSH,
    /**
    * Next of Kin
    */
    NK1,
    /**
    * Observations Request
    */
    OBR,
    /**
    * Observation Related to OBR
    */
    OBX,
    /**
    * Common Order.  Used in outgoing vaccinations VXUs as well as incoming lab result ORUs.
    */
    ORC,
    /**
    * Patient Identification
    */
    PID,
    /**
    * Patient additional demographics
    */
    PD1,
    /**
    * Patient Visit
    */
    PV1,
    /**
    * Pharmacy Administration Segment
    */
    RXA,
    /**
    * Pharmacy/Treatment Route
    */
    RXR,
    /**
    * Scheduling Activity Information
    */
    SCH,
    /**
    * This can happen for unsupported segments.
    */
    Unknown,
    /**
    * We use for PDF Data
    */
    ZX1
}

