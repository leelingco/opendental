//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum PatientGender
{
    /**
    * Known as administrativeGender (HL7 OID of 2.16.840.1.113883.5.1) Male=M, Female=F, Unknown=Undifferentiated=UN.
    */
    //known as administrativeGender HL7 OID of 2.16.840.1.113883.5.1
    /**
    * 0
    */
    Male,
    /**
    * 1
    */
    Female,
    /**
    * 2- Not a joke. Required by HIPAA for privacy.  Required by ehr to track missing entries. EHR/HL7 known as undifferentiated (UN).
    */
    Unknown
}

