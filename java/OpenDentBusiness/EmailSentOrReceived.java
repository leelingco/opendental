//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum EmailSentOrReceived
{
    /**
    * 0=Neither, 1=Sent, 2=Receivedceived. 3=Read, 4=WebMailReceived, 5=WebMailRecdRead, 6=WebMailSent, 7=WebMailSentRead0 Unsent
    */
    Neither,
    /**
    * 1 For regular email only.
    */
    Sent,
    /**
    * 2 For regular email only.  Shows in Inbox.  Once it's attached to a patient it will also show in Chart module.
    */
    Received,
    /**
    * 3 For received regular email only.  Has been read.  Shows in Inbox.  Once it's attached to a patient it will also show in Chart module.
    */
    Read,
    /**
    * 4 WebMail received from patient portal.  Shows in OD Inbox and in pt Chart module.  Also shows in PP as a sent and unread WebMail msg.
    */
    WebMailReceived,
    /**
    * 5 WebMail received from patient portal that has been marked read.  Shows in the OD Inbox and in pt Chart module.  Also shows in PP as a sent and read WebMail.
    */
    WebMailRecdRead,
    /**
    * 6 Webmail sent from provider to patient.  Shows in Chart module and also shows in PP as a received and unread WebMail msg.
    */
    WebMailSent,
    /**
    * 7 Webmail sent from provider to patient and read by patient.  Shows in Chart module and also shows in PP as a received and read WebMail msg.
    */
    WebMailSentRead,
    /**
    * 8 Sent and encrypted using Direct. Required for counting messages in EHR modules g.1 and g.2, Automated Measure Calculation.
    */
    SentDirect,
    /**
    * 9 Received email matches application/pkcs7-mime mime type, but could not be decrypted.  Shows in Inbox.  The user can decrypt from FormEmailMessageEdit.  If the user has the correct private key, then the status will change to Read.
    */
    ReceivedEncrypted,
    /**
    * 10 Received email matches application/pkcs7-mime mime type and has been decrypted.  Shows in Inbox.  Once it's attached to a patient it will also show in Chart module.  When viewing inside of FormEmailMessageEdit, the XML body of the message shows as xhtml instead of raw.  Still need to work on supporting collapsing and expanding, as required for meaningful use in 2014.
    */
    ReceivedDirect,
    /**
    * 11 For received direct messages.  Has been read.  Shows in Inbox.  Once it's attached to a patient it will also show in Chart module.  When viewing inside of FormEmailMessageEdit, the XML body of the message shows as xhtml.
    */
    ReadDirect,
    /**
    * 12 Message Delivery Notification (MDN) processed.  Always outgoing.  Indicates to sender that a Direct message was received and decrypted, but not necessarily displayed for the user.  Does not show in patient Chart.  Attached to the same patient as the incoming email which caused the MDN to be sent.
    */
    AckDirectProcessed,
    /**
    * 13 Message Delivery Notification (MDN) created and saved to db, but not sent yet.  Does not show in patient Chart.  Attached to the same patient as the incoming email which caused the MDN to be created.
    * This status is used to try resending MDNs if they fail to send.  The MDN is saved to the db so the unset MDNs can be found easily, and also because MDNs are hard to rebuild again later.
    */
    AckDirectNotSent
}

