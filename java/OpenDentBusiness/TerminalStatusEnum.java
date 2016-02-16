//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum TerminalStatusEnum
{
    /**
    * Indicates at what point the patient is in the sequence. 0=standby, 1=PatientInfo, 2=Medical, 3=UpdateOnly.0
    */
    Standby,
    /**
    * 1
    */
    PatientInfo,
    /**
    * 2
    */
    Medical,
    /**
    * 3. Only the patient info tab will be visible.  This is just to let patient up date their address and phone number.
    */
    UpdateOnly
}

