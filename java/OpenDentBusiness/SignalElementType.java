//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum SignalElementType
{
    /**
    * 0=User,1=Extra,2=Message.0-To and From lists.  Not tied in any way to the users that are part of security.
    */
    User,
    /**
    * Typically used to insert "family" before "phone" signals.
    */
    Extra,
    /**
    * Elements of this type show in the last column and trigger the message to be sent.
    */
    Message
}

