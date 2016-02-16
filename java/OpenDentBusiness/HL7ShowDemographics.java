//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum HL7ShowDemographics
{
    /**
    * Cannot see or change.
    */
    Hide,
    /**
    * Can see, but not change.
    */
    Show,
    /**
    * Can change, but not add patients.  Might get overwritten by next incoming message.
    */
    Change,
    /**
    * Can change and add patients.  Might get overwritten by next incoming message.
    */
    ChangeAndAdd
}

