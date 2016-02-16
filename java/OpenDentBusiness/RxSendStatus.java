//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;


public enum RxSendStatus
{
    /**
    * 0
    */
    Unsent,
    /**
    * 1- This will never be used in production.  It was only used for proof of concept when building EHR.
    */
    InElectQueue,
    /**
    * 2
    */
    SentElect,
    /**
    * 3
    */
    Printed
}

