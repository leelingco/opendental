//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EmailMessageUid;
import OpenDentBusiness.TableBase;

/**
* Used to track which email messages have been downloaded into the inbox for a particular recipient address.
* Not linked to the email message itself because no link is needed.
* If we decide to add a foreign key to a EmailMessage later, we should consider what do to when an email message is deleted (set the foreign key to 0 perhaps).
*/
public class EmailMessageUid  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmailMessageUidNum = new long();
    /**
    * The unique id for the associated EmailMessage.
    */
    public String MsgId = new String();
    /**
    * Copied from the EmailAddress.EmailUsername field when a message is received into the inbox.
    * Similar to the ToAddress of the EmailMessage, except the ToAddress could contain multiple recipient addresses
    * or group email address instead. The recipient address helps match the EmailMessageUid to a particular EmailAddress.
    */
    public String RecipientAddress = new String();
    public EmailMessageUid clone() {
        try
        {
            return (EmailMessageUid)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


