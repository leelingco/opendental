//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum MessageTypeHL7
{
    /**
    * The items in this enumeration can be freely rearranged without damaging the database.  But can't change spelling or remove existing item.Use this for unsupported message types
    */
    NotDefined,
    /**
    * Message Acknowledgment
    */
    ACK,
    /**
    * Demographics - A01,A04,A08,A28,A31
    */
    ADT,
    /**
    * Detailed Financial Transaction - P03
    */
    DFT,
    /**
    * Unsolicited Observation Message - R01
    */
    ORU,
    /**
    * Scheduling - S12,S13,S14,S15,S22
    */
    SIU,
    /**
    * Unsolicited Vaccination Record Update - V04
    */
    VXU
}

