//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.TableBase;

/**
* Stores both sent and received emails, as well as saved emails which are still in composition.
*/
public class EmailMessage  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EmailMessageNum = new long();
    /**
    * FK to patient.PatNum. The patient whom is sending this message. May be sent by a guarantor on behalf of a dependent.
    */
    public long PatNum = new long();
    /**
    * Single valid email address. Bcc field might be added later, although it won't be very useful.  We will never allow visible cc for privacy reasons.  For web mail messages, this will not be an email address.  Instead, it will be the name of the corresponding patient or provider.
    */
    public String ToAddress = new String();
    /**
    * Valid email address.  For web mail messages, this will not be an email address.  Instead, it will be the name of the corresponding patient or provider.
    */
    public String FromAddress = new String();
    /**
    * Subject line.
    */
    public String Subject = new String();
    /**
    * Body of the email
    */
    public String BodyText = new String();
    /**
    * Date and time the message was sent. Automated at the UI level.
    */
    public DateTime MsgDateTime = new DateTime();
    /**
    * 0=neither, 1=sent, 2=received.
    */
    public EmailSentOrReceived SentOrReceived = EmailSentOrReceived.Neither;
    /**
    * Copied from the EmailAddress.EmailUsername field when a message is received into the inbox.
    * Similar to the ToAddress, except the ToAddress could contain multiple recipient addresses
    * or group email address instead. The recipient address helps match the an email to a particular EmailAddress.
    */
    public String RecipientAddress = new String();
    /**
    * For incomming email only.  The raw email contents.  Can be used for debugging if there are any issues parsing the content.
    * This will bloat the database a little bit, but we need it for now to ensure our inbox is working in real world scenarios.
    * We may remove later or move into a different table to increase the speed at which the inbox loads.
    */
    public String RawEmailIn = new String();
    /**
    * Not a database column.
    */
    public List<EmailAttach> Attachments = new List<EmailAttach>();
    /**
    * FK to provider.ProvNum.  The provider to whom this message was sent or from whom this message was sent.  Only used when EmailSentOrReceived is WebMailReceived, WebMailRecdRead, WebMailSent, or WebMailSentRead.  Will be 0 if not a web mail message.
    */
    public long ProvNumWebMail = new long();
    /**
    * FK to patient.PatNum. Represents the patient to whom this email message is addressed, or from whom it is being sent on behalf of. If guarantor is sending on behalf of self then this field will match PatNum field.
    */
    public long PatNumSubj = new long();
    /**
    * Constructor
    */
    public EmailMessage() throws Exception {
        Attachments = new List<EmailAttach>();
    }

    public EmailMessage copy() throws Exception {
        EmailMessage e = (EmailMessage)this.MemberwiseClone();
        e.Attachments = new List<EmailAttach>();
        for (int i = 0;i < Attachments.Count;i++)
        {
            e.Attachments.Add(Attachments[i].Copy());
        }
        return e;
    }

}


