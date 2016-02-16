//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum HL7MessageStatus
{
    /**
    * 0
    */
    OutPending,
    /**
    * 1
    */
    OutSent,
    /**
    * 2-Tried to send, but there was a problem.  Will keep trying.
    */
    OutFailed,
    /**
    * 3
    */
    InProcessed,
    /**
    * 4
    */
    InFailed
}

