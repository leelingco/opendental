//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum DataTypeHL7
{
    /**
    * Data types are listed in HL7 docs section 2.15.  The items in this enumeration can be freely rearranged without damaging the database.  But can't change spelling or remove existing item.Coded with no exceptions.  Examples: ProcCode (Dxxxx) or TreatmentArea like tooth^surface
    */
    CNE,
    /**
    * Composite price.  Example: 125.00
    */
    CP,
    /**
    * Coded with exceptions.  Example: Race: American Indian or Alaska Native,Asian,Black or African American,Native Hawaiian or Other Pacific,White, Hispanic,Other Race.
    */
    CWE,
    /**
    * Extended composite ID with check digit.  Example: patient.PatNum or patient.ChartNumber or appointment.AptNum.
    */
    CX,
    /**
    * Date.  yyyyMMdd.  4,6,or 8
    */
    DT,
    /**
    * Date/Time.  yyyyMMddHHmmss etc.  Allowed 4,6,8,10,12,14.  Possibly more, but unlikely.
    */
    DTM,
    /**
    * Entity identifier.  Example: appointment.AptNum
    */
    EI,
    /**
    * Hierarchic designator.  Application identifier.  Example: "OD" for OpenDental.
    */
    HD,
    /**
    * Coded value for HL7 defined tables.  Must include TableId.  Example: 0003 is eCW's event type table id.
    */
    ID,
    /**
    * Coded value for user-defined tables.  Example: Administrative Sex, F=Female, M=Male,U=Unknown.
    */
    IS,
    /**
    * Message type.  Example: composed of messageType^eventType like DFT^P03
    */
    MSG,
    /**
    * Numeric.  Example: transaction quantity of 1.0
    */
    NM,
    /**
    * Processing type.  Examples: P-Production, T-Test.
    */
    PT,
    /**
    * Sequence ID.  Example: for repeating segments number that begins with 1.
    */
    SI,
    /**
    * String, alphanumeric.  Example: SSN or patient.FeeSchedule
    */
    ST,
    /**
    * Timing quantity.  Example: for eCW appointment ^^duration^startTime^endTime like ^^1200^20120101112000^20120101114000 for 20 minute (1200 second) appointment starting at 11:20 on 01/01/2012
    */
    TQ,
    /**
    * Version identifier.  Example: 2.3
    */
    VID,
    /**
    * Extended address.  Example: Addr1^Addr2^City^State^Zip like 120 Main St.^Suite 3^Salem^OR^97302
    */
    XAD,
    /**
    * Extended composite ID number and name for person.  Example: provider.EcwID^provider.LName^provider.FName^provider.MI
    */
    XCN,
    /**
    * Extended person name.  Composite data type.  Example: LName^FName^MI).
    */
    XPN,
    /**
    * Extended telecommunication number.  Example: 5033635432
    */
    XTN
}

