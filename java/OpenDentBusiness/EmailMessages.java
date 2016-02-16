//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.EhrSummaryCcd;
import OpenDentBusiness.EhrSummaryCcds;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailAttaches;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailMessageUid;
import OpenDentBusiness.EmailMessageUids;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* An email message is always attached to a patient.
*/
public class EmailMessages   
{
    /**
    * Used to cache DirectAgent objects, because creating a new DirectAgent object takes up to 10 seconds. If we did not cache, then inbox load would be slow and so would Direct message sending.
    */
    private static Hashtable HashDirectAgents = new Hashtable();
    private static Object _lockEmailReceive = new Object();
    private static boolean _isReceivingEmail = false;
    /**
    * Gets one email message from the database.
    */
    public static EmailMessage getOne(long msgNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EmailMessage>GetObject(MethodBase.GetCurrentMethod(), msgNum);
        }
         
        String command = "SELECT * FROM emailmessage WHERE EmailMessageNum = " + POut.long(msgNum);
        EmailMessage emailMessage = Crud.EmailMessageCrud.SelectOne(msgNum);
        if (emailMessage != null)
        {
            command = "SELECT * FROM emailattach WHERE EmailMessageNum = " + POut.long(msgNum);
            emailMessage.Attachments = Crud.EmailAttachCrud.SelectMany(command);
        }
         
        return emailMessage;
    }

    /**
    * Gets all inbox email messages where EmailMessage.RecipientAddress==emailAddressInbox OR EmailMessage.ProvNumWebMail==provNum.
    */
    public static List<EmailMessage> getInboxForAddress(String emailAddressInbox, long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EmailMessage>>GetObject(MethodBase.GetCurrentMethod(), emailAddressInbox);
        }
         
        //must match one of these EmailSentOrReceived statuses
        //can belong to either the RecipientAddress OR the ProvNumWebMail
        String command = "SELECT * FROM emailmessage " + "WHERE SentOrReceived IN (" + POut.int(((Enum)EmailSentOrReceived.Read).ordinal()) + "," + POut.int(((Enum)EmailSentOrReceived.Received).ordinal()) + "," + POut.int(((Enum)EmailSentOrReceived.ReceivedEncrypted).ordinal()) + "," + POut.int(((Enum)EmailSentOrReceived.ReceivedDirect).ordinal()) + "," + POut.int(((Enum)EmailSentOrReceived.ReadDirect).ordinal()) + "," + POut.int(((Enum)EmailSentOrReceived.WebMailRecdRead).ordinal()) + "," + POut.int(((Enum)EmailSentOrReceived.WebMailReceived).ordinal()) + ") AND (RecipientAddress='" + POut.String(emailAddressInbox.Trim()) + "' OR ProvNumWebMail=" + POut.long(provNum) + ") " + "ORDER BY MsgDateTime";
        List<EmailMessage> retVal = Crud.EmailMessageCrud.SelectMany(command);
        for (int i = 0;i < retVal.Count;i++)
        {
            command = "SELECT * FROM emailattach WHERE EmailMessageNum = " + POut.Long(retVal[i].EmailMessageNum);
            retVal[i].Attachments = Crud.EmailAttachCrud.SelectMany(command);
        }
        return retVal;
    }

    /**
    * 
    */
    public static void update(EmailMessage message) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), message);
            return ;
        }
         
        Crud.EmailMessageCrud.Update(message);
        //now, delete all attachments and recreate.
        String command = "DELETE FROM emailattach WHERE EmailMessageNum=" + POut.long(message.EmailMessageNum);
        Db.nonQ(command);
        for (int i = 0;i < message.Attachments.Count;i++)
        {
            message.Attachments[i].EmailMessageNum = message.EmailMessageNum;
            EmailAttaches.Insert(message.Attachments[i]);
        }
    }

    /**
    * Updates SentOrReceived and saves changes to db.  Better than using Update(), because does not delete and add attachments back into db.
    */
    public static void updateSentOrReceivedRead(EmailMessage emailMessage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), emailMessage);
            return ;
        }
         
        EmailSentOrReceived sentOrReceived = emailMessage.SentOrReceived;
        if (emailMessage.SentOrReceived == EmailSentOrReceived.Received)
        {
            sentOrReceived = EmailSentOrReceived.Read;
        }
        else if (emailMessage.SentOrReceived == EmailSentOrReceived.WebMailReceived)
        {
            sentOrReceived = EmailSentOrReceived.WebMailRecdRead;
        }
        else if (emailMessage.SentOrReceived == EmailSentOrReceived.ReceivedDirect)
        {
            sentOrReceived = EmailSentOrReceived.ReadDirect;
        }
           
        if (sentOrReceived == emailMessage.SentOrReceived)
        {
            return ;
        }
         
        //Nothing to do.
        String command = "UPDATE emailmessage SET SentOrReceived=" + POut.int(((Enum)sentOrReceived).ordinal()) + " WHERE EmailMessageNum=" + POut.long(emailMessage.EmailMessageNum);
        Db.nonQ(command);
    }

    /**
    * Updates SentOrReceived and saves changes to db.  Better than using Update(), because does not delete and add attachments back into db.
    */
    public static void updateSentOrReceivedUnread(EmailMessage emailMessage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), emailMessage);
            return ;
        }
         
        EmailSentOrReceived sentOrReceived = emailMessage.SentOrReceived;
        if (emailMessage.SentOrReceived == EmailSentOrReceived.Read)
        {
            sentOrReceived = EmailSentOrReceived.Received;
        }
        else if (emailMessage.SentOrReceived == EmailSentOrReceived.WebMailRecdRead)
        {
            sentOrReceived = EmailSentOrReceived.WebMailReceived;
        }
        else if (emailMessage.SentOrReceived == EmailSentOrReceived.ReadDirect)
        {
            sentOrReceived = EmailSentOrReceived.ReceivedDirect;
        }
           
        if (sentOrReceived == emailMessage.SentOrReceived)
        {
            return ;
        }
         
        //Nothing to do.
        String command = "UPDATE emailmessage SET SentOrReceived=" + POut.int(((Enum)sentOrReceived).ordinal()) + " WHERE EmailMessageNum=" + POut.long(emailMessage.EmailMessageNum);
        Db.nonQ(command);
    }

    /**
    * Updates SentOrReceived and saves changes to db.  Better than using Update(), because does not delete and add attachments back into db.
    */
    public static void updatePatNum(EmailMessage emailMessage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), emailMessage);
            return ;
        }
         
        String command = "UPDATE emailmessage SET PatNum=" + POut.long(emailMessage.PatNum) + " WHERE EmailMessageNum=" + POut.long(emailMessage.EmailMessageNum);
        Db.nonQ(command);
        if (emailMessage.Attachments == null)
        {
            return ;
        }
         
        for (int i = 0;i < emailMessage.Attachments.Count;i++)
        {
            EhrSummaryCcd ehrSummaryCcd = EhrSummaryCcds.GetOneForEmailAttach(emailMessage.Attachments[i].EmailAttachNum);
            if (ehrSummaryCcd != null)
            {
                ehrSummaryCcd.PatNum = emailMessage.PatNum;
                EhrSummaryCcds.update(ehrSummaryCcd);
            }
             
        }
    }

    /**
    * 
    */
    public static long insert(EmailMessage message) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            message.EmailMessageNum = Meth.GetLong(MethodBase.GetCurrentMethod(), message);
            return message.EmailMessageNum;
        }
         
        Crud.EmailMessageCrud.Insert(message);
        for (int i = 0;i < message.Attachments.Count;i++)
        {
            //now, insert all the attaches.
            message.Attachments[i].EmailMessageNum = message.EmailMessageNum;
            EmailAttaches.Insert(message.Attachments[i]);
        }
        return message.EmailMessageNum;
    }

    /**
    * 
    */
    public static void delete(EmailMessage message) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), message);
            return ;
        }
         
        if (message.EmailMessageNum == 0)
        {
            return ;
        }
         
        //this prevents deletion of all commlog entries of something goes wrong.
        String command = "DELETE FROM emailmessage WHERE EmailMessageNum=" + POut.long(message.EmailMessageNum);
        Db.nonQ(command);
    }

    /**
    * Encrypts the message, verifies trust, locates the public encryption key for the To address (if already stored locally), etc.  emailMessage.
    * Use this polymorphism when the attachments have already been saved to the email attachments folder in file form.  patNum can be 0.
    * Returns an empty string upon success, or an error string if there were errors.  It is possible that the email was sent to some trusted recipients and not sent to untrusted recipients (in which case there would be errors but some recipients would receive successfully).
    * Surround with a try catch.
    */
    public static String sendEmailDirect(EmailMessage emailMessage, EmailAddress emailAddressFrom) throws Exception {
        //No need to check RemotingRole; no call to db.
        emailMessage.FromAddress = emailAddressFrom.EmailUsername.Trim();
        //Cannot be emailAddressFrom.SenderAddress, or else will not find the correct encryption certificate.  Used in ConvertEmailMessageToMessage().
        //Start by converting the emailMessage to an unencrypted message using the Direct libraries. The email must be in this form to carry out encryption.
        Health.Direct.Common.Mail.Message msgUnencrypted = convertEmailMessageToMessage(emailMessage,true);
        Health.Direct.Agent.MessageEnvelope msgEnvelopeUnencrypted = new Health.Direct.Agent.MessageEnvelope(msgUnencrypted);
        Health.Direct.Agent.OutgoingMessage outMsgUnencrypted = new Health.Direct.Agent.OutgoingMessage(msgEnvelopeUnencrypted);
        String strErrors = sendEmailDirect(outMsgUnencrypted,emailAddressFrom);
        return strErrors;
    }

    /**
    * outMsgDirect must be unencrypted, because this function will encrypt.  Encrypts the message, verifies trust, locates the public encryption key for the To address (if already stored locally), etc.
    * patNum can be zero.  emailSentOrReceived must be either SentDirect or a Direct Ack type such as AckDirectProcessed.
    * Returns an empty string upon success, or an error string if there were errors.  It is possible that the email was sent to some trusted recipients and not sent to untrusted recipients (in which case there would be errors but some recipients would receive successfully).
    */
    private static String sendEmailDirect(Health.Direct.Agent.OutgoingMessage outMsgUnencrypted, EmailAddress emailAddressFrom) throws Exception {
        //No need to check RemotingRole; no call to db.
        String strErrors = "";
        String strSenderAddress = emailAddressFrom.EmailUsername.Trim();
        //Cannot be emailAddressFrom.SenderAddress, or else will not find the right encryption certificate.
        Health.Direct.Agent.DirectAgent directAgent = getDirectAgentForEmailAddress(strSenderAddress);
        for (int i = 0;i < outMsgUnencrypted.Recipients.Count;i++)
        {
            //Locate or discover public certificates for each receiver for encryption purposes.
            if (outMsgUnencrypted.Recipients[i].Certificates != null)
            {
                continue;
            }
             
            try
            {
                //The certificate(s) for this recipient were already located somehow. Skip.
                int certNewCount = FindPublicCertForAddress(outMsgUnencrypted.Recipients[i].Address.Trim());
                if (certNewCount != 0)
                {
                    //If the certificate is already in the local public store or if one was discovered over the internet.
                    String strSenderDomain = strSenderAddress.Substring(strSenderAddress.IndexOf("@") + 1);
                    //For example, if strSenderAddress is ehr@opendental.com, then this will be opendental.com
                    //Refresh the directAgent class using the updated list of public certs while leaving everything else alone. This must be done, or else the certificate will not be found when encrypting the outgoing email.
                    directAgent = new Health.Direct.Agent.DirectAgent(strSenderDomain, directAgent.PrivateCertResolver, Health.Direct.Common.Certificates.SystemX509Store.OpenExternal().CreateResolver(), directAgent.TrustAnchors);
                    directAgent.EncryptMessages = true;
                    HashDirectAgents[strSenderDomain] = directAgent;
                }
                 
            }
            catch (Exception ex)
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += ex.Message;
            }
        
        }
        Health.Direct.Agent.OutgoingMessage outMsgEncrypted = null;
        try
        {
            outMsgEncrypted = directAgent.ProcessOutgoing(outMsgUnencrypted);
        }
        catch (Exception ex)
        {
            //This is where encryption and trust verification occurs.
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += ex.Message;
            return strErrors;
        }

        //Cannot recover from an encryption error.
        outMsgEncrypted.Message.SubjectValue = "Encrypted Message";
        //Prevents a warning in the transport testing tool (TTT). http://tools.ietf.org/html/rfc5322#section-3.6.5
        EmailMessage emailMessageEncrypted = ConvertMessageToEmailMessage(outMsgEncrypted.Message, false);
        //No point in saving the encrypted attachment, because nobody can read it and it will bloat the OpenDentImages folder.
        NameValueCollection nameValueCollectionHeaders = new NameValueCollection();
        for (int i = 0;i < outMsgEncrypted.Message.Headers.Count;i++)
        {
            nameValueCollectionHeaders.Add(outMsgEncrypted.Message.Headers[i].Name, outMsgEncrypted.Message.Headers[i].ValueRaw);
        }
        byte[] arrayEncryptedBody = Encoding.UTF8.GetBytes(outMsgEncrypted.Message.Body.Text);
        //The bytes of the encrypted and base 64 encoded body string.  No need to call Tidy() here because this body text will be in base64.
        MemoryStream ms = new MemoryStream(arrayEncryptedBody);
        ms.Position = 0;
        //The memory stream for the alternate view must be mime (not an entire email), based on AlternateView use example http://msdn.microsoft.com/en-us/library/system.net.mail.mailmessage.alternateviews.aspx
        AlternateView alternateView = new AlternateView(ms, outMsgEncrypted.Message.ContentType);
        //Causes the receiver to recognize this email as an encrypted email.
        alternateView.TransferEncoding = TransferEncoding.SevenBit;
        sendEmailUnsecure(emailMessageEncrypted,emailAddressFrom,nameValueCollectionHeaders,alternateView);
        //Not really unsecure in this spot, because the message is already encrypted.
        ms.Dispose();
        return strErrors;
    }

    /**
    * Used for creating encrypted Message Disposition Notification (MDN) ack messages for Direct.
    * An ack must be sent when a message is received/processed, and other acks are supposed be sent when other events occur (but are not required).
    * For example, when the user reads a decrypted message we must send an ack with notification type of Displayed (not required).
    */
    private static String sendAckDirect(Health.Direct.Agent.IncomingMessage inMsg, EmailAddress emailAddressFrom, long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //The CreateAcks() function handles the case where the incoming message is an MDN, in which case we do not reply with anything.
        //The CreateAcks() function also takes care of figuring out where to send the MDN, because the rules are complicated.
        //According to http://wiki.directproject.org/Applicability+Statement+for+Secure+Health+Transport+Working+Version#x3.0%20Message%20Disposition%20Notification,
        //The MDN must be sent to the first available of: Disposition-Notification-To header, MAIL FROM SMTP command, Sender header, From header.
        Health.Direct.Common.Mail.Notifications.MDNStandard.NotificationType notificationType = Health.Direct.Common.Mail.Notifications.MDNStandard.NotificationType.Failed;
        notificationType = Health.Direct.Common.Mail.Notifications.MDNStandard.NotificationType.Processed;
        IEnumerable<Health.Direct.Common.Mail.Notifications.NotificationMessage> notificationMsgs = inMsg.CreateAcks("OpenDental " + Assembly.GetExecutingAssembly().GetName().Version, "", notificationType);
        if (notificationMsgs == null)
        {
            return "";
        }
         
        String strErrorsAll = "";
        for (Object __dummyForeachVar0 : notificationMsgs)
        {
            Health.Direct.Common.Mail.Notifications.NotificationMessage notificationMsg = (Health.Direct.Common.Mail.Notifications.NotificationMessage)__dummyForeachVar0;
            String strErrors = "";
            try
            {
                //According to RFC3798, section 3 - Format of a Message Disposition Notification http://tools.ietf.org/html/rfc3798#page-3
                //A message disposition notification is a MIME message with a top-level
                //content-type of multipart/report (defined in [RFC-REPORT]).  When
                //multipart/report content is used to transmit an MDN:
                //(a)  The report-type parameter of the multipart/report content is "disposition-notification".
                //(b)  The first component of the multipart/report contains a human-readable explanation of the MDN, as described in [RFC-REPORT].
                //(c)  The second component of the multipart/report is of content-type message/disposition-notification, described in section 3.1 of this document.
                //(d)  If the original message or a portion of the message is to be returned to the sender, it appears as the third component of the multipart/report.
                //     The decision of whether or not to return the message or part of the message is up to the MUA generating the MDN.  However, in the case of
                //     encrypted messages requesting MDNs, encrypted message text MUST be returned, if it is returned at all, only in its original encrypted form.
                Health.Direct.Agent.OutgoingMessage outMsgDirect = new Health.Direct.Agent.OutgoingMessage(notificationMsg);
                if (notificationMsg.ToValue.Trim().ToLower() == notificationMsg.FromValue.Trim().ToLower())
                {
                    continue;
                }
                 
                //Do not send an ack to self.
                EmailMessage emailMessage = ConvertMessageToEmailMessage(outMsgDirect.Message, false);
                emailMessage.PatNum = patNum;
                //First save the ack message to the database in case their is a failure sending the email. This way we can remember to try and send it again later, based on SentOrRecieved.
                emailMessage.SentOrReceived = EmailSentOrReceived.AckDirectNotSent;
                MemoryStream ms = new MemoryStream();
                notificationMsg.Save(ms);
                byte[] arrayMdnMessageBytes = ms.ToArray();
                emailMessage.BodyText = Encoding.UTF8.GetString(arrayMdnMessageBytes);
                ms.Dispose();
                insert(emailMessage);
            }
            catch (Exception ex)
            {
                strErrors = ex.Message;
            }

            if (!StringSupport.equals(strErrorsAll, ""))
            {
                strErrorsAll += "\r\n";
            }
             
            strErrorsAll += strErrors;
        }
        try
        {
            sendOldestUnsentAck(emailAddressFrom);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return strErrorsAll;
    }

    //Send the ack(s) we created above.
    //Not critical to send the acks here, because they will be sent later if they failed now.
    /**
    * Gets the oldest Direct Ack (MDN) from the db which has not been sent yet and attempts to send it.
    * If the Ack fails to send, then it remains in the database with status AckDirectNotSent, so that another attempt will be made when this function is called again.
    * This function throttles the Ack responses to prevent the email host from flagging the emailAddressFrom as a spam account.  The throttle speed is one Ack per 60 seconds (to mimic human behavior).
    * Throws exceptions.
    */
    public static void sendOldestUnsentAck(EmailAddress emailAddressFrom) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = new String();
        //Get the time that the last Direct Ack was sent for the From address.
        command = DbHelper.limitOrderBy("SELECT MsgDateTime FROM emailmessage " + "WHERE FromAddress='" + POut.String(emailAddressFrom.EmailUsername.Trim()) + "' AND SentOrReceived=" + POut.Long(((Enum)EmailSentOrReceived.AckDirectProcessed).ordinal()) + " " + "ORDER BY MsgDateTime DESC",1);
        DateTime dateTimeLastAck = PIn.dateT(Db.getScalar(command));
        //dateTimeLastAck will be 0001-01-01 if there is not yet any sent Acks.
        if ((DateTime.Now - dateTimeLastAck).TotalSeconds < 60)
        {
            return ;
        }
         
        //Our last Ack sent was less than 15 seconds ago.  Abort sending Acks right now.
        //Get the oldest Ack for the From address which has not been sent yet.
        command = DbHelper.limitOrderBy("SELECT * FROM emailmessage " + "WHERE FromAddress='" + POut.String(emailAddressFrom.EmailUsername.Trim()) + "' AND SentOrReceived=" + POut.Long(((Enum)EmailSentOrReceived.AckDirectNotSent).ordinal()) + " " + "ORDER BY EmailMessageNum",1);
        //The oldest Ack is the one that was recorded first.  EmailMessageNum is better than using MsgDateTime, because MsgDateTime is only accurate down to the second.
        List<EmailMessage> listEmailMessageUnsentAcks = Crud.EmailMessageCrud.SelectMany(command);
        if (listEmailMessageUnsentAcks.Count < 1)
        {
            return ;
        }
         
        //No Acks to send.
        EmailMessage emailMessageAck = listEmailMessageUnsentAcks[0];
        String strRawEmailAck = emailMessageAck.BodyText;
        //Not really body text.  The entire raw Ack is saved here, and we use it to reconstruct the Ack email completely.
        Health.Direct.Agent.MessageEnvelope messageEnvelopeMdn = new Health.Direct.Agent.MessageEnvelope(strRawEmailAck);
        Health.Direct.Agent.OutgoingMessage outMsgDirect = new Health.Direct.Agent.OutgoingMessage(messageEnvelopeMdn);
        try
        {
            String strErrors = sendEmailDirect(outMsgDirect,emailAddressFrom);
            //Encryption is performed in this step. Throws an exception if unable to send (i.e. when internet down).
            if (StringSupport.equals(strErrors, ""))
            {
                emailMessageAck.SentOrReceived = EmailSentOrReceived.AckDirectProcessed;
                emailMessageAck.MsgDateTime = DateTime.Now;
                //Update the time, otherwise the throttle will not work properly.
                update(emailMessageAck);
            }
             
        }
        catch (Exception __dummyCatchVar1)
        {
        }
    
    }

    /**
    * Call to cleanup newlines within a string before including in an email. The RFC 822 guide states that every single line in a raw email message must end with \r\n, also known as CRLF.
    * Certain email providers will reject outgoing email from us if we have any lines ending with \n or \r. Email providers that we know care: Prosites. Other email providers seem to handle
    * all different types of newlines, even though \r or \n by itself is not standard. This function replaces all \r and \n with \r\n.
    */
    public static String tidy(String str) throws Exception {
        //This function assumes the worst case, which is a string that has all 3 types of newlines: \r, \n and \r\n
        //We will first convert \r\n and \r into \n so that all our line endings are the same. Then replace \n with \r\n to make the newlines proper.
        String retVal = str.Replace("\r\n", "\n");
        //We must replace the two character newline first so that our following replacements do not create extra newlines.
        retVal = retVal.Replace("\r", "\n");
        //After this step, all newlines are in the form \n.
        retVal = retVal.Replace("\n", "\r\n");
        return retVal;
    }

    //After this step, all newlines will be in form \r\n.
    /**
    * This is used from wherever email needs to be sent throughout the program.  If a message must be encrypted, then encrypt it before calling this function.  nameValueCollectionHeaders can be null.
    */
    private static void sendEmailUnsecure(EmailMessage emailMessage, EmailAddress emailAddress, NameValueCollection nameValueCollectionHeaders, AlternateView... arrayAlternateViews) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (emailAddress.ServerPort == 465)
        {
            //implicit
            //uses System.Web.Mail, which is marked as deprecated, but still supports implicit
            System.Web.Mail.MailMessage message = new System.Web.Mail.MailMessage();
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpserver", emailAddress.SMTPserver);
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpserverport", "465");
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/sendusing", "2");
            //sendusing: cdoSendUsingPort, value 2, for sending the message using the network.
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpauthenticate", "1");
            //0=anonymous,1=clear text auth,2=context
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/sendusername", emailAddress.EmailUsername.Trim());
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/sendpassword", emailAddress.EmailPassword);
            //if(PrefC.GetBool(PrefName.EmailUseSSL)) {
            message.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpusessl", "true");
            //false was also tested and does not work
            message.From = emailMessage.FromAddress.Trim();
            message.To = emailMessage.ToAddress.Trim();
            message.Subject = tidy(emailMessage.Subject);
            message.Body = tidy(emailMessage.BodyText);
            //message.Cc=;
            //message.Bcc=;
            //message.UrlContentBase=;
            //message.UrlContentLocation=;
            message.BodyEncoding = System.Text.Encoding.UTF8;
            message.BodyFormat = System.Web.Mail.MailFormat.Text;
            //or .Html
            if (nameValueCollectionHeaders != null)
            {
                String[] arrayHeaderKeys = nameValueCollectionHeaders.AllKeys;
                for (int i = 0;i < arrayHeaderKeys.Length;i++)
                {
                    //Needed for Direct Acks to work.
                    message.Headers.Add(arrayHeaderKeys[i], nameValueCollectionHeaders[arrayHeaderKeys[i]]);
                }
            }
             
            //TODO: We need to add some kind of alternatve view or similar replacement for outgoing Direct messages to work with SSL. Write the body to a temporary file and attach with the correct mime type and name?
            String attachPath = EmailMessages.getEmailAttachPath();
            System.Web.Mail.MailAttachment attach = new System.Web.Mail.MailAttachment();
            for (int i = 0;i < emailMessage.Attachments.Count;i++)
            {
                //foreach (string sSubstr in sAttach.Split(delim)){
                attach = new System.Web.Mail.MailAttachment(CodeBase.ODFileUtils.CombinePaths(attachPath, emailMessage.Attachments[i].ActualFileName));
                //no way to set displayed filename
                message.Attachments.Add(attach);
            }
            System.Web.Mail.SmtpMail.SmtpServer = emailAddress.SMTPserver + ":465";
            //"smtp.gmail.com:465";
            System.Web.Mail.SmtpMail.Send(message);
        }
        else
        {
            //explicit default port 587
            SmtpClient client = new SmtpClient(emailAddress.SMTPserver, emailAddress.ServerPort);
            //The default credentials are not used by default, according to:
            //http://msdn2.microsoft.com/en-us/library/system.net.mail.smtpclient.usedefaultcredentials.aspx
            client.Credentials = new NetworkCredential(emailAddress.EmailUsername.Trim(), emailAddress.EmailPassword);
            client.DeliveryMethod = SmtpDeliveryMethod.Network;
            client.EnableSsl = emailAddress.UseSSL;
            client.Timeout = 180000;
            //3 minutes
            MailMessage message = new MailMessage();
            message.From = new MailAddress(emailMessage.FromAddress.Trim());
            message.To.Add(emailMessage.ToAddress.Trim());
            message.Subject = tidy(emailMessage.Subject);
            message.Body = tidy(emailMessage.BodyText);
            message.IsBodyHtml = false;
            if (nameValueCollectionHeaders != null)
            {
                message.Headers.Add(nameValueCollectionHeaders);
            }
             
            for (int i = 0;i < arrayAlternateViews.Length;i++)
            {
                //Needed for Direct Acks to work.
                //Needed for Direct messages to be interpreted encrypted on the receiver's end.
                message.AlternateViews.Add(arrayAlternateViews[i]);
            }
            String attachPath = EmailMessages.getEmailAttachPath();
            Attachment attach = new Attachment();
            for (int i = 0;i < emailMessage.Attachments.Count;i++)
            {
                attach = new Attachment(CodeBase.ODFileUtils.CombinePaths(attachPath, emailMessage.Attachments[i].ActualFileName));
                //@"C:\OpenDentalData\EmailAttachments\1");
                attach.Name = emailMessage.Attachments[i].DisplayedFileName;
                //"canadian.gif";
                message.Attachments.Add(attach);
            }
            client.Send(message);
        } 
    }

    /**
    * This is used from wherever unencrypted email needs to be sent throughout the program.  If a message must be encrypted, then encrypt it before calling this function.
    * Surround with a try catch.
    */
    public static void sendEmailUnsecure(EmailMessage emailMessage, EmailAddress emailAddress) throws Exception {
        //No need to check RemotingRole; no call to db.
        sendEmailUnsecure(emailMessage,emailAddress,null);
    }

    /**
    * Fetches up to fetchCount number of messages from a POP3 server.  Set fetchCount=0 for all messages.  Typically, fetchCount is 0 or 1.
    * Example host name, pop3.live.com. Port is Normally 110 for plain POP3, 995 for SSL POP3.
    */
    public static List<EmailMessage> receiveFromInbox(int receiveCount, EmailAddress emailAddressInbox) throws Exception {
        List<EmailMessage> retVal = new List<EmailMessage>();
        if (_isReceivingEmail)
        {
            return retVal;
        }
         
        //Already in the process of receving email. This can happen if the user clicks the refresh button at the same time the main polling thread is receiving.
        _isReceivingEmail = true;
        try
        {
            synchronized (_lockEmailReceive)
            {
                {
                    retVal = receiveFromInboxThreadSafe(receiveCount,emailAddressInbox);
                }
            }
        }
        catch (Exception __dummyCatchVar2)
        {
            throw __dummyCatchVar2;
        }
        finally
        {
            _isReceivingEmail = false;
        }
        return retVal;
    }

    /**
    * Fetches up to fetchCount number of messages from a POP3 server.  Set fetchCount=0 for all messages.  Typically, fetchCount is 0 or 1.
    * Example host name, pop3.live.com. Port is Normally 110 for plain POP3, 995 for SSL POP3.
    */
    private static List<EmailMessage> receiveFromInboxThreadSafe(int receiveCount, EmailAddress emailAddressInbox) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<EmailMessage> retVal = new List<EmailMessage>();
        //This code is modified from the example at: http://hpop.sourceforge.net/exampleFetchAllMessages.php
        OpenPop.Pop3.Pop3Client client = new OpenPop.Pop3.Pop3Client();
        try
        {
            {
                //The client disconnects from the server when being disposed.
                client.Connect(emailAddressInbox.Pop3ServerIncoming, emailAddressInbox.ServerPortIncoming, emailAddressInbox.UseSSL, 180000, 180000, null);
                //3 minute timeout, just as for sending emails.
                client.Authenticate(emailAddressInbox.EmailUsername.Trim(), emailAddressInbox.EmailPassword, OpenPop.Pop3.AuthenticationMethod.UsernameAndPassword);
                List<String> listMsgUids = client.GetMessageUids();
                //Get all unique identifiers for each email in the inbox.
                List<EmailMessageUid> listDownloadedMsgUids = EmailMessageUids.GetForRecipientAddress(emailAddressInbox.EmailUsername.Trim());
                int msgDownloadedCount = 0;
                for (int i = 0;i < listMsgUids.Count;i++)
                {
                    int msgIndex = i + 1;
                    //The message indicies are 1-based.
                    String strMsgUid = listMsgUids[i];
                    if (strMsgUid.Length == 0)
                    {
                        //Message Uids are commonly used, but are optional according to the RFC822 email standard.
                        //Uids are assgined by the sending client application, so they could be anything, but are supposed to be unique.
                        //Additionally, most email servers are probably smart enough to create a Uid for any message where the Uid is missing.
                        //In the worst case scenario, we create a Uid for the message based off of the message header information, which takes a little extra time,
                        //but is better than downloading old messages again, especially if some of those messages contain large attachments.
                        OpenPop.Mime.Header.MessageHeader messageHeader = client.GetMessageHeaders(msgIndex);
                        //Takes 1-2 seconds to get this information from the server.  The message, minus body and minus attachments.
                        strMsgUid = messageHeader.DateSent.ToString("yyyyMMddHHmmss") + emailAddressInbox.EmailUsername.Trim() + messageHeader.From.Address + messageHeader.Subject;
                    }
                    else if (strMsgUid.Length > 4000)
                    {
                        //The EmailMessageUid.MsgId field is only 4000 characters in size.
                        strMsgUid = strMsgUid.Substring(0, 4000);
                    }
                      
                    //Skip any email messages matching Uids which have been previously downloaded.
                    boolean isDownloaded = false;
                    for (int j = 0;j < listDownloadedMsgUids.Count;j++)
                    {
                        if (StringSupport.equals(listDownloadedMsgUids[j].MsgId, strMsgUid))
                        {
                            isDownloaded = true;
                            break;
                        }
                         
                    }
                    if (isDownloaded)
                    {
                        continue;
                    }
                     
                    try
                    {
                        //At this point, we know that the email is one which we have not downloaded yet.
                        OpenPop.Mime.Message openPopMsg = client.GetMessage(msgIndex);
                        //This is where the entire raw email is downloaded.
                        String strRawEmail = openPopMsg.MessagePart.BodyEncoding.GetString(openPopMsg.RawMessage);
                        EmailMessage emailMessage = ProcessRawEmailMessage(strRawEmail, 0, emailAddressInbox);
                        //Inserts to db.
                        EmailMessageUid emailMessageUid = new EmailMessageUid();
                        emailMessageUid.RecipientAddress = emailMessage.RecipientAddress.Trim();
                        emailMessageUid.MsgId = strMsgUid;
                        EmailMessageUids.insert(emailMessageUid);
                        //Remember Uid was downloaded, to avoid email duplication the next time the inbox is refreshed.
                        retVal.Add(emailMessage);
                        msgDownloadedCount++;
                    }
                    catch (ThreadAbortException __dummyCatchVar3)
                    {
                        throw __dummyCatchVar3;
                    }
                    catch (Exception __dummyCatchVar4)
                    {
                    }

                    //This can happen if the application is exiting. We need to leave right away so the program does not lock up.
                    //Otherwise, this loop could continue for a while if there are a lot of messages to download.
                    //If one particular email fails to download, then skip it for now and move on to the next email.
                    if (receiveCount > 0 && msgDownloadedCount >= receiveCount)
                    {
                        break;
                    }
                     
                }
            }
        }
        finally
        {
            if (client != null)
                Disposable.mkDisposable(client).dispose();
             
        }
        //Since this function is fired automatically based on the inbox check interval, we also try to send the oldest unsent Ack.
        //The goal is to keep trying to send the Acks at a reasonable interval until they are successfully delivered.
        sendOldestUnsentAck(emailAddressInbox);
        return retVal;
    }

    private static Health.Direct.Agent.DirectAgent getDirectAgentForEmailAddress(String strEmailAddress) throws Exception {
        //No need to check RemotingRole; no call to db.
        String domain = strEmailAddress.Substring(strEmailAddress.IndexOf("@") + 1);
        //For example, if ToAddress is ehr@opendental.com, then this will be opendental.com
        Health.Direct.Agent.DirectAgent directAgent = (Health.Direct.Agent.DirectAgent)HashDirectAgents[domain];
        if (directAgent == null)
        {
            try
            {
                directAgent = new Health.Direct.Agent.DirectAgent(domain);
            }
            catch (Exception ex)
            {
                if (ex.Message.Contains("cannot find the file specified"))
                {
                    //A typical exception when the 3 required certificate stores needed for Direct have not been created on one particular client machine.
                    Health.Direct.Common.Certificates.SystemX509Store.OpenAnchorEdit().Dispose();
                    //Create the NHINDAnchor certificate store if it does not already exist on the local machine.
                    Health.Direct.Common.Certificates.SystemX509Store.OpenExternalEdit().Dispose();
                    //Create the NHINDExternal certificate store if it does not already exist on the local machine.
                    Health.Direct.Common.Certificates.SystemX509Store.OpenPrivateEdit().Dispose();
                    //Create the NHINDPrivate certificate store if it does not already exist on the local machine.
                    directAgent = new Health.Direct.Agent.DirectAgent(domain);
                }
                else
                {
                    throw ex;
                } 
            }

            //Try again.
            directAgent.EncryptMessages = true;
            //The Transport Testing Tool (TTT) complained when we sent a message that was not wrapped.
            //Specifically, the tool looks for the headers Orig-Date and Message-Id after the message is decrypted.
            //See http://tools.ietf.org/html/rfc5322#section-3.6.1 and http://tools.ietf.org/html/rfc5322#section-3.6.4 for details about these two header fields.
            directAgent.WrapMessages = true;
            HashDirectAgents[domain] = directAgent;
        }
         
        return directAgent;
    }

    public static boolean isDirectAddressTrusted(String strAddressTest) throws Exception {
        //No need to check RemotingRole; no call to db.
        Health.Direct.Common.Certificates.SystemX509Store storeAnchors = Health.Direct.Common.Certificates.SystemX509Store.OpenAnchorEdit();
        //Open for read and write.  Corresponds to NHINDAnchors/Certificates.
        if (getValidCertForAddressFromStore(storeAnchors,strAddressTest,false) == null)
        {
            return false;
        }
         
        return true;
    }

    //Look for domain level and address level trust certificates (anchors).
    //None found.
    public static void tryAddTrustDirect(String strAddressTest) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (isDirectAddressTrusted(strAddressTest))
        {
            return ;
        }
         
        try
        {
            //Already trusted.
            if (findPublicCertForAddress(strAddressTest) == 0)
            {
                return ;
            }
             
        }
        catch (Exception __dummyCatchVar5)
        {
            return ;
        }

        //Could not find certificate.
        //Cannot trust because we need the certificate to trust.
        //Possibly a network failure.
        Health.Direct.Common.Certificates.SystemX509Store storePublicCerts = Health.Direct.Common.Certificates.SystemX509Store.OpenExternalEdit();
        //Open for read and write.  Corresponds to NHINDExternal/Certificates.
        X509Certificate2 cert = getValidCertForAddressFromStore(storePublicCerts,strAddressTest,false);
        if (cert == null)
        {
            return ;
        }
         
        //Should never happen, but just in case.
        Health.Direct.Common.Certificates.SystemX509Store storeAnchors = Health.Direct.Common.Certificates.SystemX509Store.OpenAnchorEdit();
        //Open for read and write.  Corresponds to NHINDAnchors/Certificates.
        storeAnchors.Add(cert);
        //Adds to NHINDAnchors/Certificates within the windows certificate store manager (mmc).
        //Clear all cached DirectAgent instances to force trust anchors to reload.
        HashDirectAgents.Clear();
    }

    /**
    * Set isAddressSpecific if you need to allow/prefer domain certificates over email address specific certificates.
    */
    private static X509Certificate2 getValidCertForAddressFromStore(Health.Direct.Common.Certificates.SystemX509Store store, String strAddressTest, boolean isAddressSpecific) throws Exception {
        //No need to check RemotingRole; no call to db.
        X509Certificate2Collection collectionCerts = null;
        MailAddress mailAddressQuery = new MailAddress(strAddressTest);
        Health.Direct.Common.Certificates.ICertificateResolver certResolverLocalCache = store.CreateResolver();
        if (certResolverLocalCache == null)
        {
            return null;
        }
         
        collectionCerts = certResolverLocalCache.GetCertificates(mailAddressQuery);
        if (collectionCerts == null)
        {
            return null;
        }
         
        List<X509Certificate2> listDomainCerts = new List<X509Certificate2>();
        List<X509Certificate2> listAddressCerts = new List<X509Certificate2>();
        for (int i = 0;i < collectionCerts.Count;i++)
        {
            if (DateTime.Now < collectionCerts[i].NotBefore || DateTime.Now > collectionCerts[i].NotAfter)
            {
                continue;
            }
             
            //If the certificate is not yet valid or is expired, then ignore.
            String strCertSubjectName = collectionCerts[i].Subject.Trim().ToLower();
            if (strCertSubjectName.Contains("e=" + strAddressTest.ToLower()))
            {
                //Address specific
                listAddressCerts.Add(collectionCerts[i]);
            }
            else
            {
                listDomainCerts.Add(collectionCerts[i]);
            } 
        }
        if (!isAddressSpecific && listDomainCerts.Count > 0)
        {
            return listDomainCerts[0];
        }
         
        //Domain certificates allowed/preferred and there is one.
        if (listAddressCerts.Count > 0)
        {
            return listAddressCerts[0];
        }
         
        return null;
    }

    //A certificate was found in the local store, but it was a domain level certificate and was not for the specific address provided.
    /**
    * First attemtps to find the public certificate for the provided address in the public certificate store and returns the located certificate if found.
    * If the public certificate could not be found from the public certificate store, then this function will search the internet for the hosted public certificate.
    * If a public certificate is discovered from the Internet, then it will be added to the public certificate store, but the trust for any certificate must be added separately.
    * Returns the number of new public certificates discovered (0,1,or 2 (if one for the address and domain separately)), or -1 if the certificate is already in the local store of public certificates.
    * Throws exceptions when no certificates were found or if there was a network failure.
    */
    private static int findPublicCertForAddress(String strAddressTest) throws Exception {
        //No need to check RemotingRole; no call to db.
        Health.Direct.Common.Certificates.SystemX509Store storePublicCerts = Health.Direct.Common.Certificates.SystemX509Store.OpenExternalEdit();
        //Open for read and write.  Corresponds to NHINDExternal/Certificates.
        if (getValidCertForAddressFromStore(storePublicCerts,strAddressTest,true) != null)
        {
            return -1;
        }
         
        //Address specific (excludes domain level certificates).
        //The certificate was found in the local certificate store within Windows and is already loaded into memory for the specific recipient address given.  No need to query the Internet.
        //Cert not found locally.  Attempt to discover the certificate for the exact recipient address provided (below) before using a domain level certificate.
        //An address specific certificate was not found in the local certificate store.  Attempt to discover an address specific certificate by querying the Internet.
        //It may be useful in the future to attempt communicating with a secondary DNS server if the primary DNS is not available.
        //const string strDnsServer = "184.73.237.102";//Amazon - This is the DNS server used within the Direct resolverPlugins test project. Appears to have worked the best for them, compared to the others listed below, but was not accessible.
        //const string strDnsServer = "10.110.22.16";//This address was tried in the Direct resolverPlugins test project and is commented out, implying that it might not be the best DNS server to use.
        //const string strDnsServer = "207.170.210.162";//This address was tried in the Direct resolverPlugins test project and is commented out, implying that it might not be the best DNS server to use.
        ;
        //Google - This address was tried in the Direct resolverPlugins test project and is commented out, implying that it might not be the best DNS server to use.
        IPAddress ipAddressGlobalDnsServer = IPAddress.Parse(strGlobalDnsServer);
        MailAddress mailAddressQuery = new MailAddress(strAddressTest);
        //Attempt to discover the certificate via DNS.
        Health.Direct.Common.Certificates.ICertificateResolver certResolverInternetDns = new Health.Direct.Common.Certificates.DnsCertResolver(ipAddressGlobalDnsServer);
        X509Certificate2Collection collectionCerts = certResolverInternetDns.GetCertificates(mailAddressQuery);
        //Can return null.
        List<X509Certificate2> listCertsDiscoveredActive = new List<X509Certificate2>();
        List<X509Certificate2> listCertsDiscoveredInactive = new List<X509Certificate2>();
        if (collectionCerts != null)
        {
            for (int i = 0;i < collectionCerts.Count;i++)
            {
                //Certificates found via DNS. Remove any invalid or expired certificates.
                if (DateTime.Now < collectionCerts[i].NotBefore || DateTime.Now > collectionCerts[i].NotAfter)
                {
                    //If the certificate is not yet valid or is expired, then discard so we can possibly discover a better certificate below.
                    listCertsDiscoveredInactive.Add(collectionCerts[i]);
                    continue;
                }
                 
                listCertsDiscoveredActive.Add(collectionCerts[i]);
            }
        }
         
        if (listCertsDiscoveredActive.Count == 0)
        {
            //A valid certificate was not found via DNS.  Attempt to locate via LDAP.
            Health.Direct.Common.Certificates.ICertificateResolver certResolverInternetLdap = new Health.Direct.ResolverPlugins.LdapCertResolver(ipAddressGlobalDnsServer, TimeSpan.FromMinutes(3));
            collectionCerts = certResolverInternetLdap.GetCertificates(mailAddressQuery);
            //Can return null.
            if (collectionCerts != null)
            {
                for (int i = 0;i < collectionCerts.Count;i++)
                {
                    if (DateTime.Now < collectionCerts[i].NotBefore || DateTime.Now > collectionCerts[i].NotAfter)
                    {
                        //If the certificate is not yet valid or is expired, then discard.
                        listCertsDiscoveredInactive.Add(collectionCerts[i]);
                        continue;
                    }
                     
                    listCertsDiscoveredActive.Add(collectionCerts[i]);
                }
            }
             
        }
         
        if (listCertsDiscoveredActive.Count == 0)
        {
            //A certificate was not discovered via DNS or LDAP.
            String strErrorMessage = Lans.g("EmailMessages","No active certificates discovered for recipient") + " " + strAddressTest;
            if (listCertsDiscoveredInactive.Count > 0)
            {
                strErrorMessage += "\r\n" + Lans.g("EmailMessages","Inactive certificates discovered") + ": " + listCertsDiscoveredInactive.Count;
            }
             
            throw new ApplicationException(strErrorMessage);
        }
         
        //A certificate was discovered via DNS or LDAP.  Save it locally for later reference.
        storePublicCerts.Add(listCertsDiscoveredActive);
        return listCertsDiscoveredActive.Count;
    }

    //Write the discovered certificate to the Windows certificate store for future reference.
    /**
    * Converts any raw email message (encrypted or not) into an EmailMessage object, and saves any email attachments to the emailattach table in the db.
    * The emailMessageNum will be used to set EmailMessage.EmailMessageNum.  If emailMessageNum is 0, then the EmailMessage will be inserted into the db, otherwise the EmailMessage will be updated in the db.
    * If the raw message is encrypted, then will attempt to decrypt.  If decryption fails, then the EmailMessage SentOrReceived will be ReceivedEncrypted and the EmailMessage body will be set to the entire contents of the raw email.  If decryption succeeds, then EmailMessage SentOrReceived will be set to ReceivedDirect, the EmailMessage body will contain the decrypted body text, and a Direct Ack "processed" message will be sent back to the sender using the email settings from emailAddressReceiver.
    */
    public static EmailMessage processRawEmailMessage(String strRawEmail, long emailMessageNum, EmailAddress emailAddressReceiver) throws Exception {
        //No need to check RemotingRole; no call to db.
        Health.Direct.Agent.IncomingMessage inMsg = null;
        try
        {
            inMsg = new Health.Direct.Agent.IncomingMessage(strRawEmail);
        }
        catch (Exception ex)
        {
            throw new ApplicationException("Failed to parse raw email message.\r\n" + ex.Message);
        }

        //Used to parse all email (encrypted or not).
        boolean isEncrypted = false;
        if (inMsg.Message.ContentType.ToLower().Contains("application/pkcs7-mime"))
        {
            //The email MIME/body is encrypted (known as S/MIME). Treated as a Direct message.
            isEncrypted = true;
        }
         
        EmailMessage emailMessage = null;
        if (isEncrypted)
        {
            emailMessage = ConvertMessageToEmailMessage(inMsg.Message, false);
            //Exclude attachments until we decrypt.
            emailMessage.RawEmailIn = strRawEmail;
            //This raw email is encrypted.
            emailMessage.EmailMessageNum = emailMessageNum;
            emailMessage.SentOrReceived = EmailSentOrReceived.ReceivedEncrypted;
            //The entire contents of the email are saved in the emailMessage.BodyText field, so that if decryption fails, the email will still be saved to the db for decryption later if possible.
            emailMessage.BodyText = strRawEmail;
            emailMessage.RecipientAddress = emailAddressReceiver.EmailUsername.Trim();
            try
            {
                Health.Direct.Agent.DirectAgent directAgent = GetDirectAgentForEmailAddress(inMsg.Message.ToValue.Trim());
                //throw new ApplicationException("test decryption failure");
                inMsg = directAgent.ProcessIncoming(inMsg);
                //Decrypts, valudates trust, etc.
                emailMessage = ConvertMessageToEmailMessage(inMsg.Message, true);
                //If the message was wrapped, then the To, From, Subject and Date can change after decyption. We also need to create the attachments for the decrypted message.
                emailMessage.RawEmailIn = inMsg.SerializeMessage();
                //Now that we have decrypted, we must get the raw email contents differently (cannot use strRawEmail).
                emailMessage.EmailMessageNum = emailMessageNum;
                emailMessage.SentOrReceived = EmailSentOrReceived.ReceivedDirect;
                emailMessage.RecipientAddress = emailAddressReceiver.EmailUsername.Trim();
            }
            catch (Exception ex)
            {
                //SentOrReceived will be ReceivedEncrypted, indicating to the calling code that decryption failed.
                if (emailMessageNum == 0)
                {
                    EmailMessages.insert(emailMessage);
                    return emailMessage;
                }
                 
                throw ex;
            }
        
        }
        else
        {
            //If the message was just downloaded, then this function was called from the inbox, simply return the inserted email without an exception (it can be decypted later manually by the user).
            //Do not update if emailMessageNum<>0, because nothing changed (was encrypted and still is).
            //Throw an exception if trying to decrypt an email that was already in the database, so the user can see the error message in the UI.
            //Unencrypted
            emailMessage = ConvertMessageToEmailMessage(inMsg.Message, true);
            emailMessage.RawEmailIn = strRawEmail;
            emailMessage.EmailMessageNum = emailMessageNum;
            emailMessage.SentOrReceived = EmailSentOrReceived.Received;
            emailMessage.RecipientAddress = emailAddressReceiver.EmailUsername.Trim();
        } 
        EhrSummaryCcd ehrSummaryCcd = null;
        if (isEncrypted)
        {
            for (int i = 0;i < emailMessage.Attachments.Count;i++)
            {
                if (!StringSupport.equals(Path.GetExtension(emailMessage.Attachments[i].ActualFileName).ToLower(), ".xml"))
                {
                    continue;
                }
                 
                String strAttachPath = getEmailAttachPath();
                String strAttachFile = CodeBase.ODFileUtils.CombinePaths(strAttachPath, emailMessage.Attachments[i].ActualFileName);
                String strAttachText = File.ReadAllText(strAttachFile);
                if (EhrCCD.isCCD(strAttachText))
                {
                    if (emailMessage.PatNum == 0)
                    {
                        try
                        {
                            XmlDocument xmlDocCcd = new XmlDocument();
                            xmlDocCcd.LoadXml(strAttachText);
                            emailMessage.PatNum = EhrCCD.getCCDpat(xmlDocCcd);
                        }
                        catch (Exception __dummyCatchVar6)
                        {
                        }
                    
                    }
                     
                    // A match is not guaranteed, which is why we have a button to allow the user to change the patient.
                    //Invalid XML.  Cannot match patient.
                    ehrSummaryCcd = new EhrSummaryCcd();
                    ehrSummaryCcd.ContentSummary = strAttachText;
                    ehrSummaryCcd.DateSummary = DateTime.Today;
                    ehrSummaryCcd.EmailAttachNum = i;
                    //Temporary value, so we can locate the FK down below.
                    ehrSummaryCcd.PatNum = emailMessage.PatNum;
                    break;
                }
                 
            }
        }
         
        //We can only handle one CCD message per email, because we only have one patnum field per email record and the ehrsummaryccd record requires a patnum.
        if (emailMessageNum == 0)
        {
            EmailMessages.insert(emailMessage);
        }
        else
        {
            //Also inserts all of the attachments in emailMessage.Attachments after setting each attachment EmailMessageNum properly.
            EmailMessages.update(emailMessage);
        } 
        //Also deletes all previous attachments, then recreates all of the attachments in emailMessage.Attachments after setting each attachment EmailMessageNum properly.
        if (ehrSummaryCcd != null)
        {
            ehrSummaryCcd.EmailAttachNum = emailMessage.Attachments[(int)ehrSummaryCcd.EmailAttachNum].EmailAttachNum;
            EhrSummaryCcds.insert(ehrSummaryCcd);
        }
         
        if (isEncrypted)
        {
            //Send a Message Disposition Notification (MDN) message to the sender, as required by the Direct messaging specifications.
            //The MDN will be attached to the same patient as the incoming message.
            sendAckDirect(inMsg,emailAddressReceiver,emailMessage.PatNum);
        }
         
        return emailMessage;
    }

    /**
    * Converts the Health.Direct.Common.Mail.Message into an OD EmailMessage.  The Direct library is used for both encrypted and unencrypted email.  Set hasAttachments to false to exclude attachments.
    */
    private static EmailMessage convertMessageToEmailMessage(Health.Direct.Common.Mail.Message message, boolean hasAttachments) throws Exception {
        //No need to check RemotingRole; no call to db.
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.FromAddress = message.FromValue.Trim();
        if (message.DateValue != null)
        {
            //Is null when sending, but should not be null when receiving.
            //The received email message date must be in a very specific format and must match the RFC822 standard.  Is a required field for RFC822.  http://tools.ietf.org/html/rfc822
            //We need the received time from the server, so we can quickly identify messages which have already been downloaded and to avoid downloading duplicates.
            //Examples: "3 Dec 2013 17:10:37 -0800", "10 Dec 2013 17:10:37 -0800", "Tue, 5 Nov 2013 17:10:37 +0000 (UTC)", "Tue, 12 Nov 2013 17:10:37 +0000 (UTC)"
            if (message.DateValue.Contains(","))
            {
                try
                {
                    //The day-of-week, comma and following space are optional. Examples: "Tue, 3 Dec 2013 17:10:37 +0000", "Tue, 12 Nov 2013 17:10:37 +0000 (UTC)"
                    emailMessage.MsgDateTime = DateTime.ParseExact(message.DateValue.Substring(0, 31), "ddd, d MMM yyyy HH:mm:ss zzz", System.Globalization.CultureInfo.CurrentCulture.DateTimeFormat);
                }
                catch (Exception __dummyCatchVar7)
                {
                    emailMessage.MsgDateTime = DateTime.ParseExact(message.DateValue.Substring(0, 30), "ddd, d MMM yyyy HH:mm:ss zzz", System.Globalization.CultureInfo.CurrentCulture.DateTimeFormat);
                }
            
            }
            else
            {
                try
                {
                    //Examples: "3 Dec 2013 17:10:37 -0800", "12 Nov 2013 17:10:37 -0800 (UTC)"
                    emailMessage.MsgDateTime = DateTime.ParseExact(message.DateValue.Substring(0, 26), "d MMM yyyy HH:mm:ss zzz", System.Globalization.CultureInfo.CurrentCulture.DateTimeFormat);
                }
                catch (Exception __dummyCatchVar8)
                {
                    emailMessage.MsgDateTime = DateTime.ParseExact(message.DateValue.Substring(0, 25), "d MMM yyyy HH:mm:ss zzz", System.Globalization.CultureInfo.CurrentCulture.DateTimeFormat);
                }
            
            } 
        }
        else
        {
            //Sending the email.
            emailMessage.MsgDateTime = DateTime.Now;
        } 
        emailMessage.Subject = Tidy(message.SubjectValue);
        emailMessage.ToAddress = message.ToValue.Trim();
        List<Health.Direct.Common.Mime.MimeEntity> listMimeParts = new List<Health.Direct.Common.Mime.MimeEntity>();
        //We want to treat one part and multiple part emails the same way below, so we make our own list.  If GetParts() is called when IsMultiPart is false, then an exception will be thrown by the Direct library.
        Health.Direct.Common.Mime.MimeEntity mimeEntity = null;
        try
        {
            mimeEntity = message.ExtractMimeEntity();
        }
        catch (Exception __dummyCatchVar9)
        {
            emailMessage.BodyText = ProcessMimeTextPart(message.Body.Text);
            return emailMessage;
        }

        if (message.IsMultiPart)
        {
            for (Object __dummyForeachVar1 : mimeEntity.GetParts())
            {
                Health.Direct.Common.Mime.MimeEntity mimePart = (Health.Direct.Common.Mime.MimeEntity)__dummyForeachVar1;
                listMimeParts.Add(mimePart);
            }
        }
        else
        {
            //Single body part.
            listMimeParts.Add(mimeEntity);
        } 
        List<Health.Direct.Common.Mime.MimeEntity> listMimeBodyTextParts = new List<Health.Direct.Common.Mime.MimeEntity>();
        List<Health.Direct.Common.Mime.MimeEntity> listMimeAttachParts = new List<Health.Direct.Common.Mime.MimeEntity>();
        for (int i = 0;i < listMimeParts.Count;i++)
        {
            Health.Direct.Common.Mime.MimeEntity mimePart = listMimeParts[i];
            if (mimePart.ContentDisposition == null || !mimePart.ContentDisposition.ToLower().Contains("attachment"))
            {
                //Not an email attachment.  Treat as body text.
                listMimeBodyTextParts.Add(mimePart);
            }
            else
            {
                listMimeAttachParts.Add(mimePart);
            } 
        }
        String strTextPartBoundary = "";
        if (listMimeBodyTextParts.Count > 1)
        {
            strTextPartBoundary = message.ParsedContentType.Boundary;
        }
         
        StringBuilder sbBodyText = new StringBuilder();
        for (int i = 0;i < listMimeBodyTextParts.Count;i++)
        {
            if (!StringSupport.equals(strTextPartBoundary, ""))
            {
                //For incoming Direct Ack messages.
                sbBodyText.Append("\r\n--" + strTextPartBoundary + "\r\n");
                sbBodyText.Append(listMimeBodyTextParts[i].ToString());
            }
            else
            {
                //Includes not only the body text, but also content type and content disposition.
                sbBodyText.Append(ProcessMimeTextPart(listMimeBodyTextParts[i].Body.Text));
            } 
        }
        if (!StringSupport.equals(strTextPartBoundary, ""))
        {
            sbBodyText.Append("\r\n--" + strTextPartBoundary + "--\r\n");
        }
         
        emailMessage.BodyText = sbBodyText.ToString();
        emailMessage.Attachments = new List<EmailAttach>();
        if (!hasAttachments)
        {
            return emailMessage;
        }
         
        for (int i = 0;i < listMimeAttachParts.Count;i++)
        {
            Health.Direct.Common.Mime.MimeEntity mimePartAttach = listMimeAttachParts[i];
            String strAttachText = mimePartAttach.Body.Text;
            try
            {
                if (mimePartAttach.ContentTransferEncoding.ToLower().Contains("base64"))
                {
                    strAttachText = Encoding.UTF8.GetString(Convert.FromBase64String(mimePartAttach.Body.Text));
                }
                 
            }
            catch (Exception __dummyCatchVar10)
            {
            }

            EmailAttach emailAttach = CreateAttachInAttachPath(mimePartAttach.ParsedContentType.Name, strAttachText);
            if (StringSupport.equals(mimePartAttach.ParsedContentType.Name.ToLower(), "smime.p7m"))
            {
                //encrypted attachment
                message.ContentType = "application/pkcs7-mime; name=smime.p7m; boundary=" + strTextPartBoundary + ";";
            }
             
            emailMessage.Attachments.Add(emailAttach);
        }
        return emailMessage;
    }

    //The attachment EmailMessageNum is set when the emailMessage is inserted/updated below.
    /**
    * Converts our internal EmailMessage object to a Direct message object.  Used for outgoing email.  Wraps the message.
    */
    private static Health.Direct.Common.Mail.Message convertEmailMessageToMessage(EmailMessage emailMessage, boolean hasAttachments) throws Exception {
        //No need to check RemotingRole; no call to db.
        //We need to use emailAddressFrom.Username instead of emailAddressFrom.SenderAddress, because of how strict encryption is for matching the name to the certificate.
        Health.Direct.Common.Mail.Message message = new Health.Direct.Common.Mail.Message(emailMessage.ToAddress.Trim(), emailMessage.FromAddress.Trim());
        String subject = tidy(emailMessage.Subject);
        if (!StringSupport.equals(subject, ""))
        {
            Health.Direct.Common.Mime.Header headerSubject = new Health.Direct.Common.Mime.Header("Subject", subject);
            message.Headers.Add(headerSubject);
        }
         
        //The Transport Testing Tool (TTT) complained when we sent a message that was not wrapped.
        //It appears that wrapped messages are preferred when sending a message, although support for incoming wrapped messages is optional (unwrapped is required).  We support both unwrapped and wrapped.
        //Specifically, the tool looks for the headers Orig-Date and Message-Id after the message is decrypted, so we need to include these two headers before encrypting an outgoing email.
        //The message date must be in a very specific format and must match the RFC822 standard.  Is a required field for RFC822.  http://tools.ietf.org/html/rfc822
        String strOrigDate = DateTime.Now.ToString("ddd, dd MMM yyyy HH:mm:ss zzz");
        //Example: "Tue, 12 Nov 2013 17:10:37 +08:00", which has an extra colon in the Zulu offset.
        strOrigDate = strOrigDate.Remove(strOrigDate.LastIndexOf(':'), 1);
        //Remove the colon from the Zulu offset, as required by the RFC 822 message format.
        message.Date = new Health.Direct.Common.Mime.Header("Date", strOrigDate);
        //http://tools.ietf.org/html/rfc5322#section-3.6.1
        message.AssignMessageID();
        //http://tools.ietf.org/html/rfc5322#section-3.6.4
        String strBoundry = "";
        List<Health.Direct.Common.Mime.MimeEntity> listMimeParts = new List<Health.Direct.Common.Mime.MimeEntity>();
        String bodyText = tidy(emailMessage.BodyText);
        if (bodyText.Trim().Length > 4 && bodyText.Trim().StartsWith("--") && bodyText.Trim().EndsWith("--"))
        {
            //The body text is multi-part.
            strBoundry = bodyText.Trim().Split(new String[]{ "\r\n", "\r", "\n" }, StringSplitOptions.None)[0];
            String[] arrayBodyTextParts = bodyText.Trim().TrimEnd('-').Split(new String[]{ strBoundry }, StringSplitOptions.RemoveEmptyEntries);
            for (int i = 0;i < arrayBodyTextParts.Length;i++)
            {
                Health.Direct.Common.Mime.MimeEntity mimeEntityBodyText = new Health.Direct.Common.Mime.MimeEntity(arrayBodyTextParts[i]);
                mimeEntityBodyText.ContentType = "text/plain;";
                listMimeParts.Add(mimeEntityBodyText);
            }
        }
        else
        {
            Health.Direct.Common.Mime.MimeEntity mimeEntityBodyText = new Health.Direct.Common.Mime.MimeEntity(bodyText);
            mimeEntityBodyText.ContentType = "text/plain;";
            listMimeParts.Add(mimeEntityBodyText);
        } 
        if (hasAttachments && emailMessage.Attachments != null && emailMessage.Attachments.Count > 0)
        {
            String strAttachPath = getEmailAttachPath();
            for (int i = 0;i < emailMessage.Attachments.Count;i++)
            {
                String strAttachFileName = emailMessage.Attachments[i].DisplayedFileName;
                String strAttachFile = CodeBase.ODFileUtils.CombinePaths(strAttachPath, emailMessage.Attachments[i].ActualFileName);
                String strAttachText = File.ReadAllText(strAttachFile);
                Health.Direct.Common.Mime.MimeEntity mimeEntityAttach = new Health.Direct.Common.Mime.MimeEntity(Convert.ToBase64String(Encoding.UTF8.GetBytes(strAttachText)));
                mimeEntityAttach.ContentDisposition = "attachment;";
                mimeEntityAttach.ContentTransferEncoding = "base64;";
                if (StringSupport.equals(Path.GetExtension(emailMessage.Attachments[i].ActualFileName).ToLower(), ".xml") || StringSupport.equals(Path.GetExtension(emailMessage.Attachments[i].ActualFileName).ToLower(), ".xsl"))
                {
                    mimeEntityAttach.ContentType = "text/xml;";
                }
                else
                {
                    mimeEntityAttach.ContentType = "text/plain;";
                } 
                mimeEntityAttach.ContentType += " name=" + strAttachFileName + ";";
                listMimeParts.Add(mimeEntityAttach);
            }
        }
         
        if (StringSupport.equals(strBoundry, ""))
        {
            strBoundry = CodeBase.MiscUtils.createRandomAlphaNumericString(32);
        }
         
        if (listMimeParts.Count == 1)
        {
            //Single body part
            message.Body = listMimeParts[0].Body;
        }
        else if (listMimeParts.Count > 1)
        {
            //multiple body parts
            message.SetParts(listMimeParts, "multipart/mixed; boundary=" + strBoundry + ";");
        }
          
        return message;
    }

    /**
    * Creates a new file inside of the email attachment path (inside OpenDentImages) and returns an EmailAttach object referencing the new file, but with EmailMessageNum set to zero so it can be set later.
    */
    private static EmailAttach createAttachInAttachPath(String strAttachFileName, String strAttachText) throws Exception {
        //No need to check RemotingRole; no call to db.
        String strAttachFileNameAdjusted = strAttachFileName;
        if (String.IsNullOrEmpty(strAttachFileName))
        {
            strAttachFileNameAdjusted = CodeBase.MiscUtils.createRandomAlphaNumericString(8) + ".txt";
        }
         
        //just in case
        String strAttachPath = getEmailAttachPath();
        String strAttachFile = CodeBase.ODFileUtils.CombinePaths(strAttachPath, DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + "_" + strAttachFileNameAdjusted);
        while (File.Exists(strAttachFile))
        {
            strAttachFile = CodeBase.ODFileUtils.CombinePaths(strAttachPath, DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + "_" + strAttachFileNameAdjusted);
        }
        File.WriteAllText(strAttachFile, strAttachText);
        EmailAttach emailAttach = new EmailAttach();
        emailAttach.ActualFileName = Path.GetFileName(strAttachFile);
        emailAttach.DisplayedFileName = Path.GetFileName(strAttachFileNameAdjusted);
        return emailAttach;
    }

    //shorter name, excludes date and time stamp info.
    private static String processMimeTextPart(String strBody) throws Exception {
        //No need to check RemotingRole; no call to db.
        //For unencrypted emails from GoDaddy, the body text is html, but each line is wrapped at 75 characters and an extra '=' is appended.
        //Our algorithm to handle the extra equal signs is more generic, in case GoDaddy every changes their wrap character count, or in case other email providers manimulate the email body in a similar manner.
        boolean isWrappedEmail = true;
        String[] arrayMimeBodyLines = strBody.Split(new String[]{ "\r\n", "\r", "\n" }, StringSplitOptions.None);
        int lastTextLineIndex = arrayMimeBodyLines.Length - 1;
        while (arrayMimeBodyLines[lastTextLineIndex].Length == 0 && lastTextLineIndex > 0)
        {
            //GoDaddy emails also have trailing blank lines after the body text, which we need to ignore when determining if the email is wrapped or not.
            lastTextLineIndex--;
        }
        for (int i = 0;i < lastTextLineIndex;i++)
        {
            //Ignore the last line in this consideration, because it is almost always a shorter line than the wrapped lines.
            if (arrayMimeBodyLines[i].Length < 50)
            {
                //Why would any email provider wrap an email to less than 50 characters?
                isWrappedEmail = false;
                break;
            }
             
            if (i > 0 && arrayMimeBodyLines[i].Length != arrayMimeBodyLines[i - 1].Length)
            {
                //Wrapped email messages have lines of the same length (excluding the last line)
                isWrappedEmail = false;
                break;
            }
             
            if (!arrayMimeBodyLines[i].EndsWith("="))
            {
                isWrappedEmail = false;
                break;
            }
             
        }
        String retVal = strBody;
        if (isWrappedEmail)
        {
            StringBuilder sbBodyText = new StringBuilder();
            for (int i = 0;i < lastTextLineIndex;i++)
            {
                sbBodyText.Append(arrayMimeBodyLines[i].Substring(0, arrayMimeBodyLines[i].Length - 1));
            }
            for (int i = lastTextLineIndex;i < arrayMimeBodyLines.Length;i++)
            {
                //The whole line, excluding the last character.
                //Copy the the last text line and all trailing blank lines as is.
                sbBodyText.Append(arrayMimeBodyLines[i]);
            }
            retVal = sbBodyText.ToString();
        }
         
        return retVal;
    }

    public static String getEmailAttachPath() throws Exception {
        //No need to check RemotingRole; no call to db.
        String attachPath = new String();
        if (PrefC.getAtoZfolderUsed())
        {
            attachPath = CodeBase.ODFileUtils.combinePaths(ImageStore.getPreferredAtoZpath(),"EmailAttachments");
            if (!Directory.Exists(attachPath))
            {
                Directory.CreateDirectory(attachPath);
            }
             
        }
        else
        {
            //For users who have the A to Z folders disabled, there is no defined image path, so we
            //have to use a temp path.  This means that the attachments might be available immediately afterward,
            //but probably not later.
            attachPath = Path.GetTempPath();
        } 
        return attachPath;
    }

    /**
    * Can throw an exception if there is a permission issue saving the file.
    */
    public static void createAttachmentFromText(EmailMessage emailMessage, String strAttachText, String strDisplayFileName) throws Exception {
        //No need to check RemotingRole; no call to db.
        Random rnd = new Random();
        EmailAttach emailAttach;
        //create the attachment
        emailAttach = new EmailAttach();
        emailAttach.DisplayedFileName = strDisplayFileName;
        emailAttach.ActualFileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + Path.GetExtension(strDisplayFileName);
        //To make unique.
        String strAttachFilePath = CodeBase.ODFileUtils.combinePaths(EmailMessages.getEmailAttachPath(),emailAttach.ActualFileName);
        File.WriteAllText(strAttachFilePath, strAttachText);
        emailMessage.Attachments.Add(emailAttach);
    }

    /**
    * This method is only for ehr testing purposes, and it always uses the hidden pref EHREmailToAddress to send to.  For privacy reasons, this cannot be used with production patient info.  AttachName should include extension.
    */
    public static void sendTestUnsecure(String subjectAndBody, String attachName, String attachContents) throws Exception {
        //No need to check RemotingRole; no call to db.
        sendTestUnsecure(subjectAndBody,attachName,attachContents,"","");
    }

    /**
    * This method is only for ehr testing purposes, and it always uses the hidden pref EHREmailToAddress to send to.  For privacy reasons, this cannot be used with production patient info.  AttachName should include extension.
    */
    public static void sendTestUnsecure(String subjectAndBody, String attachName1, String attachContents1, String attachName2, String attachContents2) throws Exception {
        //No need to check RemotingRole; no call to db.
        String strTo = PrefC.getString(PrefName.EHREmailToAddress);
        if (StringSupport.equals(strTo, ""))
        {
            throw new ApplicationException("This feature cannot be used except in a test environment because email is not secure.");
        }
         
        EmailAddress emailAddressFrom = EmailAddresses.GetByClinic(0);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.FromAddress = emailAddressFrom.EmailUsername.Trim();
        emailMessage.ToAddress = strTo.Trim();
        emailMessage.Subject = subjectAndBody;
        emailMessage.BodyText = subjectAndBody;
        if (!StringSupport.equals(attachName1, ""))
        {
            EmailAttach emailAttach = createAttachInAttachPath(attachName1,attachContents1);
            emailMessage.Attachments.Add(emailAttach);
        }
         
        if (!StringSupport.equals(attachName2, ""))
        {
            EmailAttach emailAttach = createAttachInAttachPath(attachName2,attachContents2);
            emailMessage.Attachments.Add(emailAttach);
        }
         
        sendEmailUnsecure(emailMessage,emailAddressFrom);
        insert(emailMessage);
    }

    /**
    * Receives one email from the inbox, and returns the contents of the attachment as a string.  Will throw an exception if anything goes wrong, so surround with a try-catch.
    */
    public static String receiveOneForEhrTest() throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(PrefC.getString(PrefName.EHREmailToAddress), ""))
        {
            throw new ApplicationException("This feature cannot be used except in a test environment because email is not secure.");
        }
         
        //this pref is hidden, so no practical way for user to turn this on.
        if (StringSupport.equals(PrefC.getString(PrefName.EHREmailPOPserver), ""))
        {
            throw new ApplicationException("No POP server set up.");
        }
         
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.Pop3ServerIncoming = PrefC.getString(PrefName.EHREmailPOPserver);
        emailAddress.ServerPortIncoming = PrefC.getInt(PrefName.EHREmailPort);
        emailAddress.EmailUsername = PrefC.getString(PrefName.EHREmailFromAddress);
        emailAddress.EmailPassword = PrefC.getString(PrefName.EHREmailPassword);
        List<EmailMessage> emailMessages = receiveFromInbox(1,emailAddress);
        if (emailMessages.Count == 0)
        {
            throw new Exception("Inbox empty.");
        }
         
        EmailMessage emailMessage = emailMessages[0];
        if (emailMessage.Attachments == null || emailMessage.Attachments.Count == 0)
        {
            throw new Exception("No attachments");
        }
         
        String strAttachFile = CodeBase.ODFileUtils.CombinePaths(getEmailAttachPath(), emailMessage.Attachments[0].ActualFileName);
        return File.ReadAllText(strAttachFile);
    }

    private static String getTestEmail1() throws Exception {
        return "This is a multipart message in MIME format.\r\n" + 
        "\r\n" + 
        "------=_NextPart_000_0074_01CC35A4.193BF450\r\n" + 
        "Content-Type: multipart/alternative;\r\n" + 
        "\tboundary=\"----=_NextPart_001_0075_01CC35A4.193BF450\"\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "------=_NextPart_001_0075_01CC35A4.193BF450\r\n" + 
        "Content-Type: text/plain;\r\n" + 
        "\tcharset=\"us-ascii\"\r\n" + 
        "Content-Transfer-Encoding: 7bit\r\n" + 
        "\r\n" + 
        "test\r\n" + 
        "\r\n" + 
        "\r\n" + 
        "------=_NextPart_001_0075_01CC35A4.193BF450\r\n" + 
        "Content-Type: text/html;\r\n" + 
        "\tcharset=\"us-ascii\"\r\n" + 
        "Content-Transfer-Encoding: quoted-printable\r\n" + 
        "\r\n" + 
        "<html xmlns:v=3D\"urn:schemas-microsoft-com:vml\" =\r\n" + 
        "xmlns:o=3D\"urn:schemas-microsoft-com:office:office\" =\r\n" + 
        "xmlns:w=3D\"urn:schemas-microsoft-com:office:word\" =\r\n" + 
        "xmlns:m=3D\"http://schemas.microsoft.com/office/2004/12/omml\" =\r\n" + 
        "xmlns=3D\"http://www.w3.org/TR/REC-html40\"><head><meta =\r\n" + 
        "http-equiv=3DContent-Type content=3D\"text/html; =\r\n" + 
        "charset=3Dus-ascii\"><meta name=3DGenerator content=3D\"Microsoft Word 14 =\r\n" + 
        "(filtered medium)\"><style><!--\r\n" + 
        "/* Font Definitions */\r\n" + 
        "@font-face\r\n" + 
        "\t{font-family:Calibri;\r\n" + 
        "\tpanose-1:2 15 5 2 2 2 4 3 2 4;}\r\n" + 
        "/* Style Definitions */\r\n" + 
        "p.MsoNormal, li.MsoNormal, div.MsoNormal\r\n" + 
        "\t{margin:0in;\r\n" + 
        "\tmargin-bottom:.0001pt;\r\n" + 
        "\tfont-size:11.0pt;\r\n" + 
        "\tfont-family:\"Calibri\",\"sans-serif\";}\r\n" + 
        "a:link, span.MsoHyperlink\r\n" + 
        "\t{mso-style-priority:99;\r\n" + 
        "\tcolor:blue;\r\n" + 
        "\ttext-decoration:underline;}\r\n" + 
        "a:visited, span.MsoHyperlinkFollowed\r\n" + 
        "\t{mso-style-priority:99;\r\n" + 
        "\tcolor:purple;\r\n" + 
        "\ttext-decoration:underline;}\r\n" + 
        "span.EmailStyle17\r\n" + 
        "\t{mso-style-type:personal-compose;\r\n" + 
        "\tfont-family:\"Calibri\",\"sans-serif\";\r\n" + 
        "\tcolor:windowtext;}\r\n" + 
        "..MsoChpDefault\r\n" + 
        "\t{mso-style-type:export-only;\r\n" + 
        "\tfont-family:\"Calibri\",\"sans-serif\";}\r\n" + 
        "@page WordSection1\r\n" + 
        "\t{size:8.5in 11.0in;\r\n" + 
        "\tmargin:1.0in 1.0in 1.0in 1.0in;}\r\n" + 
        "div.WordSection1\r\n" + 
        "\t{page:WordSection1;}\r\n" + 
        "--></style><!--[if gte mso 9]><xml>\r\n" + 
        "<o:shapedefaults v:ext=3D\"edit\" spidmax=3D\"1026\" />\r\n" + 
        "</xml><![endif]--><!--[if gte mso 9]><xml>\r\n" + 
        "<o:shapelayout v:ext=3D\"edit\">\r\n" + 
        "<o:idmap v:ext=3D\"edit\" data=3D\"1\" />\r\n" + 
        "</o:shapelayout></xml><![endif]--></head><body lang=3DEN-US link=3Dblue =\r\n" + 
        "vlink=3Dpurple><div class=3DWordSection1><p =\r\n" + 
        "class=3DMsoNormal>test<o:p></o:p></p></div></body></html>\r\n" + 
        "------=_NextPart_001_0075_01CC35A4.193BF450--\r\n" + 
        "\r\n" + 
        "------=_NextPart_000_0074_01CC35A4.193BF450\r\n" + 
        "Content-Type: text/plain;\r\n" + 
        "\tname=\"SarahEbbert_v4.txt\"\r\n" + 
        "Content-Transfer-Encoding: quoted-printable\r\n" + 
        "Content-Disposition: attachment;\r\n" + 
        "\tfilename=\"SarahEbbert_v4.txt\"\r\n" + 
        "\r\n" + 
        "<?xml version=3D\"1.0\" encoding=3D\"UTF-8\"?>\r\n" + 
        "<ClinicalDocument xmlns=3D\"urn:hl7-org:v3\">\r\n" + 
        "   <typeId extension=3D\"POCD_HD0000040\" root=3D\"2.16.840.1.113883.1.3\" =\r\n" + 
        "/>\r\n" + 
        "   <templateId root=3D\"2.16.840.1.113883.10.20.1\" />\r\n" + 
        "   <id />\r\n" + 
        "   <code code=3D\"34133-9\" codeSystemName=3D\"LOINC\" =\r\n" + 
        "codeSystem=3D\"2.16.840.1.113883.6.1\" displayName=3D\"Summary of episode =\r\n" + 
        "note\" />\r\n" + 
        "   <documentationOf>\r\n" + 
        "      <serviceEvent classCode=3D\"PCPR\">\r\n" + 
        "         <effectiveTime>\r\n" + 
        "            <high value=3D\"20110628075321-0700\" />\r\n" + 
        "            <low value=3D\"19621008000000-0700\" />\r\n" + 
        "         </effectiveTime>\r\n" + 
        "      </serviceEvent>\r\n" + 
        "   </documentationOf>\r\n" + 
        "   <languageCode value=3D\"en-US\" />\r\n" + 
        "   <templateId root=3D\"2.16.840.1.113883.10.20.1\" />\r\n" + 
        "   <effectiveTime value=3D\"20110628075321-0700\" />\r\n" + 
        "   <recordTarget>\r\n" + 
        "      <patientRole>\r\n" + 
        "         <id value=3D\"7\" />\r\n" + 
        "         <addr use=3D\"HP\">\r\n" + 
        "            <streetAddressLine>856 Salt Street</streetAddressLine>\r\n" + 
        "            <streetAddressLine></streetAddressLine>\r\n" + 
        "            <city>Shawville</city>\r\n" + 
        "            <state>PA</state>\r\n" + 
        "            <country></country>\r\n" + 
        "         </addr>\r\n" + 
        "         <patient>\r\n" + 
        "            <name use=3D\"L\">\r\n" + 
        "               <given>Sarah</given>\r\n" + 
        "               <given></given>\r\n" + 
        "               <family>Ebbert</family>\r\n" + 
        "               <suffix qualifier=3D\"TITLE\"></suffix>\r\n" + 
        "            </name>\r\n" + 
        "         </patient>\r\n" + 
        "      </patientRole>\r\n" + 
        "      <text>\r\n" + 
        "         <table width=3D\"100%\" border=3D\"1\">\r\n" + 
        "            <thead>\r\n" + 
        "               <tr>\r\n" + 
        "                  <th>Name</th>\r\n" + 
        "                  <th>Date of Birth</th>\r\n" + 
        "                  <th>Gender</th>\r\n" + 
        "                  <th>Identification Number</th>\r\n" + 
        "                  <th>Identification Number Type</th>\r\n" + 
        "                  <th>Address/Phone</th>\r\n" + 
        "               </tr>\r\n" + 
        "            </thead>\r\n" + 
        "            <tbody>\r\n" + 
        "               <tr>\r\n" + 
        "                  <td>Ebbert, Sarah </td>\r\n" + 
        "                  <td>10/08/1962</td>\r\n" + 
        "                  <td>Female</td>\r\n" + 
        "                  <td>7</td>\r\n" + 
        "                  <td>Open Dental PatNum</td>\r\n" + 
        "                  <td>856 Salt Street=20\r\n" + 
        "Shawville, PA\r\n" + 
        "16873\r\n" + 
        "(814)645-6489</td>\r\n" + 
        "               </tr>\r\n" + 
        "            </tbody>\r\n" + 
        "         </table>\r\n" + 
        "      </text>\r\n" + 
        "   </recordTarget>\r\n" + 
        "   <author>\r\n" + 
        "      <assignedAuthor>\r\n" + 
        "         <assignedPerson>\r\n" + 
        "            <name>Auto Generated</name>\r\n" + 
        "         </assignedPerson>\r\n" + 
        "      </assignedAuthor>\r\n" + 
        "   </author>\r\n" + 
        "   <component>\r\n" + 
        "      <!--Problems-->\r\n" + 
        "      <section>\r\n" + 
        "         <templateId root=3D\"2.16.840.1.113883.10.20.1.11\" =\r\n" + 
        "assigningAuthorityName=3D\"HL7 CCD\" />\r\n" + 
        "         <!--Problems section template-->\r\n" + 
        "         <code code=3D\"11450-4\" codeSystemName=3D\"LOINC\" =\r\n" + 
        "codeSystem=3D\"2.16.840.1.113883.6.1\" displayName=3D\"Problem list\" />\r\n" + 
        "         <title>Problems</title>\r\n" + 
        "         <text>\r\n" + 
        "            <table width=3D\"100%\" border=3D\"1\">\r\n" + 
        "               <thead>\r\n" + 
        "                  <tr>\r\n" + 
        "                     <th>ICD-9 Code</th>\r\n" + 
        "                     <th>Patient Problem</th>\r\n" + 
        "                     <th>Date Diagnosed</th>\r\n" + 
        "                     <th>Status</th>\r\n" + 
        "                  </tr>\r\n" + 
        "               </thead>\r\n" + 
        "               <tbody>\r\n" + 
        "                  <tr ID=3D\"CondID-1\">\r\n" + 
        "                     <td>272.4</td>\r\n" + 
        "                     <td>OTHER AND UNSPECIFIED HYPERLIPIDEMIA</td>\r\n" + 
        "                     <td>07/05/2006</td>\r\n" + 
        "                     <td>Active</td>\r\n" + 
        "                  </tr>\r\n" + 
        "                  <tr ID=3D\"CondID-1\">\r\n" + 
        "                     <td>401.9</td>\r\n" + 
        "                     <td>UNSPECIFIED ESSENTIAL HYPERTENSION</td>\r\n" + 
        "                     <td>07/05/2006</td>\r\n" + 
        "                     <td>Active</td>\r\n" + 
        "                  </tr>\r\n" + 
        "               </tbody>\r\n" + 
        "            </table>\r\n" + 
        "         </text>\r\n" + 
        "      </section>\r\n" + 
        "      <component>\r\n" + 
        "         <!--Alerts-->\r\n" + 
        "         <section>\r\n" + 
        "            <templateId root=3D\"2.16.840.1.113883.10.20.1.2\" =\r\n" + 
        "assigningAuthorityName=3D\"HL7 CCD\" />\r\n" + 
        "            <!--Alerts section template-->\r\n" + 
        "            <code code=3D\"48765-2\" codeSystemName=3D\"LOINC\" =\r\n" + 
        "codeSystem=3D\"2.16.840.1.113883.6.1\" displayName=3D\"Allergies, adverse =\r\n" + 
        "reactions, alerts\" />\r\n" + 
        "            <title>Allergies and Adverse Reactions</title>\r\n" + 
        "            <text>\r\n" + 
        "               <table width=3D\"100%\" border=3D\"1\">\r\n" + 
        "                  <thead>\r\n" + 
        "                     <tr>\r\n" + 
        "                        <th>SNOMED Allergy Type Code</th>\r\n" + 
        "                        <th>Medication/Agent Allergy</th>\r\n" + 
        "                        <th>Reaction</th>\r\n" + 
        "                        <th>Adverse Event Date</th>\r\n" + 
        "                     </tr>\r\n" + 
        "                  </thead>\r\n" + 
        "                  <tbody>\r\n" + 
        "                     <tr>\r\n" + 
        "                        <td>416098002 - Drug allergy (disorder)</td>\r\n" + 
        "                        <td>617314 - Lipitor</td>\r\n" + 
        "                        <td>Rash and anaphylaxis</td>\r\n" + 
        "                        <td>05/22/1998</td>\r\n" + 
        "                     </tr>\r\n" + 
        "                  </tbody>\r\n" + 
        "               </table>\r\n" + 
        "            </text>\r\n" + 
        "         </section>\r\n" + 
        "         <component>\r\n" + 
        "            <!--Medications-->\r\n" + 
        "            <section>\r\n" + 
        "               <templateId root=3D\"2.16.840.1.113883.10.20.1.8\" =\r\n" + 
        "assigningAuthorityName=3D\"HL7 CCD\" />\r\n" + 
        "               <!--Medications section template-->\r\n" + 
        "               <code code=3D\"10160-0\" codeSystemName=3D\"LOINC\" =\r\n" + 
        "codeSystem=3D\"2.16.840.1.113883.6.1\" displayName=3D\"History of =\r\n" + 
        "medication use\" />\r\n" + 
        "               <title>Medications</title>\r\n" + 
        "               <text>\r\n" + 
        "                  <table width=3D\"100%\" border=3D\"1\">\r\n" + 
        "                     <thead>\r\n" + 
        "                        <tr>\r\n" + 
        "                           <th>RxNorm Code</th>\r\n" + 
        "                           <th>Product</th>\r\n" + 
        "                           <th>Generic Name</th>\r\n" + 
        "                           <th>Brand Name</th>\r\n" + 
        "                           <th>Instructions</th>\r\n" + 
        "                           <th>Date Started</th>\r\n" + 
        "                           <th>Status</th>\r\n" + 
        "                        </tr>\r\n" + 
        "                     </thead>\r\n" + 
        "                     <tbody>\r\n" + 
        "                        <tr>\r\n" + 
        "                           <td>617314</td>\r\n" + 
        "                           <td>Medication</td>\r\n" + 
        "                           <td>atorvastatin calcium</td>\r\n" + 
        "                           <td>Lipitor</td>\r\n" + 
        "                           <td>10 mg, 1 Tablet, Q Day</td>\r\n" + 
        "                           <td>07/05/2006</td>\r\n" + 
        "                           <td>Active</td>\r\n" + 
        "                        </tr>\r\n" + 
        "                        <tr>\r\n" + 
        "                           <td>200801</td>\r\n" + 
        "                           <td>Medication</td>\r\n" + 
        "                           <td>furosemide</td>\r\n" + 
        "                           <td>Lasix</td>\r\n" + 
        "                           <td>20 mg, 1 Tablet, BID</td>\r\n" + 
        "                           <td>07/05/2006</td>\r\n" + 
        "                           <td>Active</td>\r\n" + 
        "                        </tr>\r\n" + 
        "                        <tr>\r\n" + 
        "                           <td>628958</td>\r\n" + 
        "                           <td>Medication</td>\r\n" + 
        "                           <td>potassium chloride</td>\r\n" + 
        "                           <td>Klor-Con</td>\r\n" + 
        "                           <td>10 mEq, 1 Tablet, BID</td>\r\n" + 
        "                           <td>07/05/2006</td>\r\n" + 
        "                           <td>Active</td>\r\n" + 
        "                        </tr>\r\n" + 
        "                     </tbody>\r\n" + 
        "                  </table>\r\n" + 
        "               </text>\r\n" + 
        "            </section>\r\n" + 
        "            <component>\r\n" + 
        "               <!--Results-->\r\n" + 
        "               <section>\r\n" + 
        "                  <templateId root=3D\"2.16.840.1.113883.10.20.1.14\" =\r\n" + 
        "assigningAuthorityName=3D\"HL7 CCD\" />\r\n" + 
        "                  <!--Relevant diagnostic tests and/or labratory data-->\r\n" + 
        "                  <code code=3D\"30954-2\" codeSystemName=3D\"LOINC\" =\r\n" + 
        "codeSystem=3D\"2.16.840.1.113883.6.1\" displayName=3D\"Allergies, adverse =\r\n" + 
        "reactions, alerts\" />\r\n" + 
        "                  <title>Results</title>\r\n" + 
        "                  <text>\r\n" + 
        "                     <table width=3D\"100%\" border=3D\"1\">\r\n" + 
        "                        <thead>\r\n" + 
        "                           <tr>\r\n" + 
        "                              <th>LOINC Code</th>\r\n" + 
        "                              <th>Test</th>\r\n" + 
        "                              <th>Result</th>\r\n" + 
        "                              <th>Abnormal Flag</th>\r\n" + 
        "                              <th>Date Performed</th>\r\n" + 
        "                           </tr>\r\n" + 
        "                        </thead>\r\n" + 
        "                        <tbody>\r\n" + 
        "                           <tr>\r\n" + 
        "                              <td>2823-3</td>\r\n" + 
        "                              <td>Potassium</td>\r\n" + 
        "                              <td>Normal</td>\r\n" + 
        "                              <td>02/15/2009</td>\r\n" + 
        "                           </tr>\r\n" + 
        "                           <tr>\r\n" + 
        "                              <td>14647-2</td>\r\n" + 
        "                              <td>Total cholesterol</td>\r\n" + 
        "                              <td>Normal</td>\r\n" + 
        "                              <td>07/15/2009</td>\r\n" + 
        "                           </tr>\r\n" + 
        "                           <tr>\r\n" + 
        "                              <td>14646-4</td>\r\n" + 
        "                              <td>HDL cholesterol</td>\r\n" + 
        "                              <td>Normal</td>\r\n" + 
        "                              <td>07/15/2009</td>\r\n" + 
        "                           </tr>\r\n" + 
        "                           <tr>\r\n" + 
        "                              <td>2089-1</td>\r\n" + 
        "                              <td>LDL cholesterol</td>\r\n" + 
        "                              <td>Above</td>\r\n" + 
        "                              <td>07/15/2009</td>\r\n" + 
        "                           </tr>\r\n" + 
        "                           <tr>\r\n" + 
        "                              <td>14927-8</td>\r\n" + 
        "                              <td>Triglycerides</td>\r\n" + 
        "                              <td>Above</td>\r\n" + 
        "                              <td>07/15/2009</td>\r\n" + 
        "                           </tr>\r\n" + 
        "                        </tbody>\r\n" + 
        "                     </table>\r\n" + 
        "                  </text>\r\n" + 
        "               </section>\r\n" + 
        "            </component>\r\n" + 
        "         </component>\r\n" + 
        "      </component>\r\n" + 
        "   </component>\r\n" + 
        "</ClinicalDocument>\r\n" + 
        "------=_NextPart_000_0074_01CC35A4.193BF450--";
    }

    //No need to check RemotingRole; no call to db.
    private static String getTestEmail2() throws Exception {
        return "This is a multi-part message in MIME format.\r\n" + 
        "--------------070304090505090508040909\r\n" + 
        "Content-Type: text/plain; charset=ISO-8859-1; format=flowed\r\n" + 
        "Content-Transfer-Encoding: 7bit\r\n" + 
        "\r\n" + 
        "Clinical Exchange Test\r\n" + 
        "\r\n" + 
        "--------------070304090505090508040909\r\n" + 
        "Content-Type: text/plain;\r\n" + 
        " name=\"SarahEbbert_v4.txt\"\r\n" + 
        "Content-Transfer-Encoding: base64\r\n" + 
        "Content-Disposition: attachment;\r\n" + 
        " filename=\"SarahEbbert_v4.txt\"\r\n" + 
        "\r\n" + 
        "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjxDbGluaWNhbERvY3Vt\r\n" + 
        "ZW50IHhtbG5zPSJ1cm46aGw3LW9yZzp2MyI+DQogICA8dHlwZUlkIGV4dGVuc2lvbj0iUE9D\r\n" + 
        "RF9IRDAwMDAwNDAiIHJvb3Q9IjIuMTYuODQwLjEuMTEzODgzLjEuMyIgLz4NCiAgIDx0ZW1w\r\n" + 
        "bGF0ZUlkIHJvb3Q9IjIuMTYuODQwLjEuMTEzODgzLjEwLjIwLjEiIC8+DQogICA8aWQgLz4N\r\n" + 
        "CiAgIDxjb2RlIGNvZGU9IjM0MTMzLTkiIGNvZGVTeXN0ZW1OYW1lPSJMT0lOQyIgY29kZVN5\r\n" + 
        "c3RlbT0iMi4xNi44NDAuMS4xMTM4ODMuNi4xIiBkaXNwbGF5TmFtZT0iU3VtbWFyeSBvZiBl\r\n" + 
        "cGlzb2RlIG5vdGUiIC8+DQogICA8ZG9jdW1lbnRhdGlvbk9mPg0KICAgICAgPHNlcnZpY2VF\r\n" + 
        "dmVudCBjbGFzc0NvZGU9IlBDUFIiPg0KICAgICAgICAgPGVmZmVjdGl2ZVRpbWU+DQogICAg\r\n" + 
        "ICAgICAgICA8aGlnaCB2YWx1ZT0iMjAxMTA2MjgwNzUzMjEtMDcwMCIgLz4NCiAgICAgICAg\r\n" + 
        "ICAgIDxsb3cgdmFsdWU9IjE5NjIxMDA4MDAwMDAwLTA3MDAiIC8+DQogICAgICAgICA8L2Vm\r\n" + 
        "ZmVjdGl2ZVRpbWU+DQogICAgICA8L3NlcnZpY2VFdmVudD4NCiAgIDwvZG9jdW1lbnRhdGlv\r\n" + 
        "bk9mPg0KICAgPGxhbmd1YWdlQ29kZSB2YWx1ZT0iZW4tVVMiIC8+DQogICA8dGVtcGxhdGVJ\r\n" + 
        "ZCByb290PSIyLjE2Ljg0MC4xLjExMzg4My4xMC4yMC4xIiAvPg0KICAgPGVmZmVjdGl2ZVRp\r\n" + 
        "bWUgdmFsdWU9IjIwMTEwNjI4MDc1MzIxLTA3MDAiIC8+DQogICA8cmVjb3JkVGFyZ2V0Pg0K\r\n" + 
        "ICAgICAgPHBhdGllbnRSb2xlPg0KICAgICAgICAgPGlkIHZhbHVlPSI3IiAvPg0KICAgICAg\r\n" + 
        "ICAgPGFkZHIgdXNlPSJIUCI+DQogICAgICAgICAgICA8c3RyZWV0QWRkcmVzc0xpbmU+ODU2\r\n" + 
        "IFNhbHQgU3RyZWV0PC9zdHJlZXRBZGRyZXNzTGluZT4NCiAgICAgICAgICAgIDxzdHJlZXRB\r\n" + 
        "ZGRyZXNzTGluZT48L3N0cmVldEFkZHJlc3NMaW5lPg0KICAgICAgICAgICAgPGNpdHk+U2hh\r\n" + 
        "d3ZpbGxlPC9jaXR5Pg0KICAgICAgICAgICAgPHN0YXRlPlBBPC9zdGF0ZT4NCiAgICAgICAg\r\n" + 
        "ICAgIDxjb3VudHJ5PjwvY291bnRyeT4NCiAgICAgICAgIDwvYWRkcj4NCiAgICAgICAgIDxw\r\n" + 
        "YXRpZW50Pg0KICAgICAgICAgICAgPG5hbWUgdXNlPSJMIj4NCiAgICAgICAgICAgICAgIDxn\r\n" + 
        "aXZlbj5TYXJhaDwvZ2l2ZW4+DQogICAgICAgICAgICAgICA8Z2l2ZW4+PC9naXZlbj4NCiAg\r\n" + 
        "ICAgICAgICAgICAgIDxmYW1pbHk+RWJiZXJ0PC9mYW1pbHk+DQogICAgICAgICAgICAgICA8\r\n" + 
        "c3VmZml4IHF1YWxpZmllcj0iVElUTEUiPjwvc3VmZml4Pg0KICAgICAgICAgICAgPC9uYW1l\r\n" + 
        "Pg0KICAgICAgICAgPC9wYXRpZW50Pg0KICAgICAgPC9wYXRpZW50Um9sZT4NCiAgICAgIDx0\r\n" + 
        "ZXh0Pg0KICAgICAgICAgPHRhYmxlIHdpZHRoPSIxMDAlIiBib3JkZXI9IjEiPg0KICAgICAg\r\n" + 
        "ICAgICAgPHRoZWFkPg0KICAgICAgICAgICAgICAgPHRyPg0KICAgICAgICAgICAgICAgICAg\r\n" + 
        "PHRoPk5hbWU8L3RoPg0KICAgICAgICAgICAgICAgICAgPHRoPkRhdGUgb2YgQmlydGg8L3Ro\r\n" + 
        "Pg0KICAgICAgICAgICAgICAgICAgPHRoPkdlbmRlcjwvdGg+DQogICAgICAgICAgICAgICAg\r\n" + 
        "ICA8dGg+SWRlbnRpZmljYXRpb24gTnVtYmVyPC90aD4NCiAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "aD5JZGVudGlmaWNhdGlvbiBOdW1iZXIgVHlwZTwvdGg+DQogICAgICAgICAgICAgICAgICA8\r\n" + 
        "dGg+QWRkcmVzcy9QaG9uZTwvdGg+DQogICAgICAgICAgICAgICA8L3RyPg0KICAgICAgICAg\r\n" + 
        "ICAgPC90aGVhZD4NCiAgICAgICAgICAgIDx0Ym9keT4NCiAgICAgICAgICAgICAgIDx0cj4N\r\n" + 
        "CiAgICAgICAgICAgICAgICAgIDx0ZD5FYmJlcnQsIFNhcmFoIDwvdGQ+DQogICAgICAgICAg\r\n" + 
        "ICAgICAgICA8dGQ+MTAvMDgvMTk2MjwvdGQ+DQogICAgICAgICAgICAgICAgICA8dGQ+RmVt\r\n" + 
        "YWxlPC90ZD4NCiAgICAgICAgICAgICAgICAgIDx0ZD43PC90ZD4NCiAgICAgICAgICAgICAg\r\n" + 
        "ICAgIDx0ZD5PcGVuIERlbnRhbCBQYXROdW08L3RkPg0KICAgICAgICAgICAgICAgICAgPHRk\r\n" + 
        "Pjg1NiBTYWx0IFN0cmVldCANClNoYXd2aWxsZSwgUEENCjE2ODczDQooODE0KTY0NS02NDg5\r\n" + 
        "PC90ZD4NCiAgICAgICAgICAgICAgIDwvdHI+DQogICAgICAgICAgICA8L3Rib2R5Pg0KICAg\r\n" + 
        "ICAgICAgPC90YWJsZT4NCiAgICAgIDwvdGV4dD4NCiAgIDwvcmVjb3JkVGFyZ2V0Pg0KICAg\r\n" + 
        "PGF1dGhvcj4NCiAgICAgIDxhc3NpZ25lZEF1dGhvcj4NCiAgICAgICAgIDxhc3NpZ25lZFBl\r\n" + 
        "cnNvbj4NCiAgICAgICAgICAgIDxuYW1lPkF1dG8gR2VuZXJhdGVkPC9uYW1lPg0KICAgICAg\r\n" + 
        "ICAgPC9hc3NpZ25lZFBlcnNvbj4NCiAgICAgIDwvYXNzaWduZWRBdXRob3I+DQogICA8L2F1\r\n" + 
        "dGhvcj4NCiAgIDxjb21wb25lbnQ+DQogICAgICA8IS0tUHJvYmxlbXMtLT4NCiAgICAgIDxz\r\n" + 
        "ZWN0aW9uPg0KICAgICAgICAgPHRlbXBsYXRlSWQgcm9vdD0iMi4xNi44NDAuMS4xMTM4ODMu\r\n" + 
        "MTAuMjAuMS4xMSIgYXNzaWduaW5nQXV0aG9yaXR5TmFtZT0iSEw3IENDRCIgLz4NCiAgICAg\r\n" + 
        "ICAgIDwhLS1Qcm9ibGVtcyBzZWN0aW9uIHRlbXBsYXRlLS0+DQogICAgICAgICA8Y29kZSBj\r\n" + 
        "b2RlPSIxMTQ1MC00IiBjb2RlU3lzdGVtTmFtZT0iTE9JTkMiIGNvZGVTeXN0ZW09IjIuMTYu\r\n" + 
        "ODQwLjEuMTEzODgzLjYuMSIgZGlzcGxheU5hbWU9IlByb2JsZW0gbGlzdCIgLz4NCiAgICAg\r\n" + 
        "ICAgIDx0aXRsZT5Qcm9ibGVtczwvdGl0bGU+DQogICAgICAgICA8dGV4dD4NCiAgICAgICAg\r\n" + 
        "ICAgIDx0YWJsZSB3aWR0aD0iMTAwJSIgYm9yZGVyPSIxIj4NCiAgICAgICAgICAgICAgIDx0\r\n" + 
        "aGVhZD4NCiAgICAgICAgICAgICAgICAgIDx0cj4NCiAgICAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "aD5JQ0QtOSBDb2RlPC90aD4NCiAgICAgICAgICAgICAgICAgICAgIDx0aD5QYXRpZW50IFBy\r\n" + 
        "b2JsZW08L3RoPg0KICAgICAgICAgICAgICAgICAgICAgPHRoPkRhdGUgRGlhZ25vc2VkPC90\r\n" + 
        "aD4NCiAgICAgICAgICAgICAgICAgICAgIDx0aD5TdGF0dXM8L3RoPg0KICAgICAgICAgICAg\r\n" + 
        "ICAgICAgPC90cj4NCiAgICAgICAgICAgICAgIDwvdGhlYWQ+DQogICAgICAgICAgICAgICA8\r\n" + 
        "dGJvZHk+DQogICAgICAgICAgICAgICAgICA8dHIgSUQ9IkNvbmRJRC0xIj4NCiAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgIDx0ZD4yNzIuNDwvdGQ+DQogICAgICAgICAgICAgICAgICAgICA8dGQ+\r\n" + 
        "T1RIRVIgQU5EIFVOU1BFQ0lGSUVEIEhZUEVSTElQSURFTUlBPC90ZD4NCiAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgIDx0ZD4wNy8wNS8yMDA2PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "ZD5BY3RpdmU8L3RkPg0KICAgICAgICAgICAgICAgICAgPC90cj4NCiAgICAgICAgICAgICAg\r\n" + 
        "ICAgIDx0ciBJRD0iQ29uZElELTEiPg0KICAgICAgICAgICAgICAgICAgICAgPHRkPjQwMS45\r\n" + 
        "PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgIDx0ZD5VTlNQRUNJRklFRCBFU1NFTlRJQUwg\r\n" + 
        "SFlQRVJURU5TSU9OPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgIDx0ZD4wNy8wNS8yMDA2\r\n" + 
        "PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgIDx0ZD5BY3RpdmU8L3RkPg0KICAgICAgICAg\r\n" + 
        "ICAgICAgICAgPC90cj4NCiAgICAgICAgICAgICAgIDwvdGJvZHk+DQogICAgICAgICAgICA8\r\n" + 
        "L3RhYmxlPg0KICAgICAgICAgPC90ZXh0Pg0KICAgICAgPC9zZWN0aW9uPg0KICAgICAgPGNv\r\n" + 
        "bXBvbmVudD4NCiAgICAgICAgIDwhLS1BbGVydHMtLT4NCiAgICAgICAgIDxzZWN0aW9uPg0K\r\n" + 
        "ICAgICAgICAgICAgPHRlbXBsYXRlSWQgcm9vdD0iMi4xNi44NDAuMS4xMTM4ODMuMTAuMjAu\r\n" + 
        "MS4yIiBhc3NpZ25pbmdBdXRob3JpdHlOYW1lPSJITDcgQ0NEIiAvPg0KICAgICAgICAgICAg\r\n" + 
        "PCEtLUFsZXJ0cyBzZWN0aW9uIHRlbXBsYXRlLS0+DQogICAgICAgICAgICA8Y29kZSBjb2Rl\r\n" + 
        "PSI0ODc2NS0yIiBjb2RlU3lzdGVtTmFtZT0iTE9JTkMiIGNvZGVTeXN0ZW09IjIuMTYuODQw\r\n" + 
        "LjEuMTEzODgzLjYuMSIgZGlzcGxheU5hbWU9IkFsbGVyZ2llcywgYWR2ZXJzZSByZWFjdGlv\r\n" + 
        "bnMsIGFsZXJ0cyIgLz4NCiAgICAgICAgICAgIDx0aXRsZT5BbGxlcmdpZXMgYW5kIEFkdmVy\r\n" + 
        "c2UgUmVhY3Rpb25zPC90aXRsZT4NCiAgICAgICAgICAgIDx0ZXh0Pg0KICAgICAgICAgICAg\r\n" + 
        "ICAgPHRhYmxlIHdpZHRoPSIxMDAlIiBib3JkZXI9IjEiPg0KICAgICAgICAgICAgICAgICAg\r\n" + 
        "PHRoZWFkPg0KICAgICAgICAgICAgICAgICAgICAgPHRyPg0KICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICAgPHRoPlNOT01FRCBBbGxlcmd5IFR5cGUgQ29kZTwvdGg+DQogICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICA8dGg+TWVkaWNhdGlvbi9BZ2VudCBBbGxlcmd5PC90aD4NCiAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgIDx0aD5SZWFjdGlvbjwvdGg+DQogICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICA8dGg+QWR2ZXJzZSBFdmVudCBEYXRlPC90aD4NCiAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "IDwvdHI+DQogICAgICAgICAgICAgICAgICA8L3RoZWFkPg0KICAgICAgICAgICAgICAgICAg\r\n" + 
        "PHRib2R5Pg0KICAgICAgICAgICAgICAgICAgICAgPHRyPg0KICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICAgPHRkPjQxNjA5ODAwMiAtIERydWcgYWxsZXJneSAoZGlzb3JkZXIpPC90ZD4NCiAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICAgIDx0ZD42MTczMTQgLSBMaXBpdG9yPC90ZD4NCiAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgIDx0ZD5SYXNoIGFuZCBhbmFwaHlsYXhpczwvdGQ+DQogICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICA8dGQ+MDUvMjIvMTk5ODwvdGQ+DQogICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICA8L3RyPg0KICAgICAgICAgICAgICAgICAgPC90Ym9keT4NCiAgICAgICAgICAg\r\n" + 
        "ICAgIDwvdGFibGU+DQogICAgICAgICAgICA8L3RleHQ+DQogICAgICAgICA8L3NlY3Rpb24+\r\n" + 
        "DQogICAgICAgICA8Y29tcG9uZW50Pg0KICAgICAgICAgICAgPCEtLU1lZGljYXRpb25zLS0+\r\n" + 
        "DQogICAgICAgICAgICA8c2VjdGlvbj4NCiAgICAgICAgICAgICAgIDx0ZW1wbGF0ZUlkIHJv\r\n" + 
        "b3Q9IjIuMTYuODQwLjEuMTEzODgzLjEwLjIwLjEuOCIgYXNzaWduaW5nQXV0aG9yaXR5TmFt\r\n" + 
        "ZT0iSEw3IENDRCIgLz4NCiAgICAgICAgICAgICAgIDwhLS1NZWRpY2F0aW9ucyBzZWN0aW9u\r\n" + 
        "IHRlbXBsYXRlLS0+DQogICAgICAgICAgICAgICA8Y29kZSBjb2RlPSIxMDE2MC0wIiBjb2Rl\r\n" + 
        "U3lzdGVtTmFtZT0iTE9JTkMiIGNvZGVTeXN0ZW09IjIuMTYuODQwLjEuMTEzODgzLjYuMSIg\r\n" + 
        "ZGlzcGxheU5hbWU9Ikhpc3Rvcnkgb2YgbWVkaWNhdGlvbiB1c2UiIC8+DQogICAgICAgICAg\r\n" + 
        "ICAgICA8dGl0bGU+TWVkaWNhdGlvbnM8L3RpdGxlPg0KICAgICAgICAgICAgICAgPHRleHQ+\r\n" + 
        "DQogICAgICAgICAgICAgICAgICA8dGFibGUgd2lkdGg9IjEwMCUiIGJvcmRlcj0iMSI+DQog\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICA8dGhlYWQ+DQogICAgICAgICAgICAgICAgICAgICAgICA8\r\n" + 
        "dHI+DQogICAgICAgICAgICAgICAgICAgICAgICAgICA8dGg+UnhOb3JtIENvZGU8L3RoPg0K\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRoPlByb2R1Y3Q8L3RoPg0KICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgPHRoPkdlbmVyaWMgTmFtZTwvdGg+DQogICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICA8dGg+QnJhbmQgTmFtZTwvdGg+DQogICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICA8dGg+SW5zdHJ1Y3Rpb25zPC90aD4NCiAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgIDx0aD5EYXRlIFN0YXJ0ZWQ8L3RoPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "PHRoPlN0YXR1czwvdGg+DQogICAgICAgICAgICAgICAgICAgICAgICA8L3RyPg0KICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgPC90aGVhZD4NCiAgICAgICAgICAgICAgICAgICAgIDx0Ym9keT4N\r\n" + 
        "CiAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4NCiAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgIDx0ZD42MTczMTQ8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPk1l\r\n" + 
        "ZGljYXRpb248L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPmF0b3J2YXN0\r\n" + 
        "YXRpbiBjYWxjaXVtPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5MaXBp\r\n" + 
        "dG9yPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4xMCBtZywgMSBUYWJs\r\n" + 
        "ZXQsIFEgRGF5PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4wNy8wNS8y\r\n" + 
        "MDA2PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5BY3RpdmU8L3RkPg0K\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4NCiAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "IDx0cj4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4yMDA4MDE8L3RkPg0KICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICAgICAgPHRkPk1lZGljYXRpb248L3RkPg0KICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgPHRkPmZ1cm9zZW1pZGU8L3RkPg0KICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgPHRkPkxhc2l4PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "IDx0ZD4yMCBtZywgMSBUYWJsZXQsIEJJRDwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICA8dGQ+MDcvMDUvMjAwNjwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICA8\r\n" + 
        "dGQ+QWN0aXZlPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+DQogICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICA8dHI+DQogICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+\r\n" + 
        "NjI4OTU4PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5NZWRpY2F0aW9u\r\n" + 
        "PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5wb3Rhc3NpdW0gY2hsb3Jp\r\n" + 
        "ZGU8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPktsb3ItQ29uPC90ZD4N\r\n" + 
        "CiAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4xMCBtRXEsIDEgVGFibGV0LCBCSUQ8\r\n" + 
        "L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPjA3LzA1LzIwMDY8L3RkPg0K\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPkFjdGl2ZTwvdGQ+DQogICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICA8L3RyPg0KICAgICAgICAgICAgICAgICAgICAgPC90Ym9keT4NCiAg\r\n" + 
        "ICAgICAgICAgICAgICAgIDwvdGFibGU+DQogICAgICAgICAgICAgICA8L3RleHQ+DQogICAg\r\n" + 
        "ICAgICAgICA8L3NlY3Rpb24+DQogICAgICAgICAgICA8Y29tcG9uZW50Pg0KICAgICAgICAg\r\n" + 
        "ICAgICAgPCEtLVJlc3VsdHMtLT4NCiAgICAgICAgICAgICAgIDxzZWN0aW9uPg0KICAgICAg\r\n" + 
        "ICAgICAgICAgICAgPHRlbXBsYXRlSWQgcm9vdD0iMi4xNi44NDAuMS4xMTM4ODMuMTAuMjAu\r\n" + 
        "MS4xNCIgYXNzaWduaW5nQXV0aG9yaXR5TmFtZT0iSEw3IENDRCIgLz4NCiAgICAgICAgICAg\r\n" + 
        "ICAgICAgIDwhLS1SZWxldmFudCBkaWFnbm9zdGljIHRlc3RzIGFuZC9vciBsYWJyYXRvcnkg\r\n" + 
        "ZGF0YS0tPg0KICAgICAgICAgICAgICAgICAgPGNvZGUgY29kZT0iMzA5NTQtMiIgY29kZVN5\r\n" + 
        "c3RlbU5hbWU9IkxPSU5DIiBjb2RlU3lzdGVtPSIyLjE2Ljg0MC4xLjExMzg4My42LjEiIGRp\r\n" + 
        "c3BsYXlOYW1lPSJBbGxlcmdpZXMsIGFkdmVyc2UgcmVhY3Rpb25zLCBhbGVydHMiIC8+DQog\r\n" + 
        "ICAgICAgICAgICAgICAgICA8dGl0bGU+UmVzdWx0czwvdGl0bGU+DQogICAgICAgICAgICAg\r\n" + 
        "ICAgICA8dGV4dD4NCiAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSB3aWR0aD0iMTAwJSIg\r\n" + 
        "Ym9yZGVyPSIxIj4NCiAgICAgICAgICAgICAgICAgICAgICAgIDx0aGVhZD4NCiAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgIDx0cj4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "IDx0aD5MT0lOQyBDb2RlPC90aD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "aD5UZXN0PC90aD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0aD5SZXN1bHQ8\r\n" + 
        "L3RoPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRoPkFibm9ybWFsIEZsYWc8\r\n" + 
        "L3RoPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRoPkRhdGUgUGVyZm9ybWVk\r\n" + 
        "PC90aD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+DQogICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICA8L3RoZWFkPg0KICAgICAgICAgICAgICAgICAgICAgICAgPHRib2R5Pg0K\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPg0KICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgPHRkPjI4MjMtMzwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICA8dGQ+UG90YXNzaXVtPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "ZD5Ob3JtYWw8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPjAyLzE1\r\n" + 
        "LzIwMDk8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4NCiAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgIDx0cj4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "IDx0ZD4xNDY0Ny0yPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5U\r\n" + 
        "b3RhbCBjaG9sZXN0ZXJvbDwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8\r\n" + 
        "dGQ+Tm9ybWFsPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4wNy8x\r\n" + 
        "NS8yMDA5PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+DQogICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgICA8dHI+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "ICA8dGQ+MTQ2NDYtNDwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+\r\n" + 
        "SERMIGNob2xlc3Rlcm9sPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "ZD5Ob3JtYWw8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPjA3LzE1\r\n" + 
        "LzIwMDk8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4NCiAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgICAgIDx0cj4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAg\r\n" + 
        "IDx0ZD4yMDg5LTE8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPkxE\r\n" + 
        "TCBjaG9sZXN0ZXJvbDwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+\r\n" + 
        "QWJvdmU8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPjA3LzE1LzIw\r\n" + 
        "MDk8L3RkPg0KICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4NCiAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgICAgICAgIDx0cj4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0\r\n" + 
        "ZD4xNDkyNy04PC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5Ucmln\r\n" + 
        "bHljZXJpZGVzPC90ZD4NCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD5BYm92\r\n" + 
        "ZTwvdGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+MDcvMTUvMjAwOTwv\r\n" + 
        "dGQ+DQogICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPg0KICAgICAgICAgICAgICAg\r\n" + 
        "ICAgICAgICAgPC90Ym9keT4NCiAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+DQogICAg\r\n" + 
        "ICAgICAgICAgICAgICA8L3RleHQ+DQogICAgICAgICAgICAgICA8L3NlY3Rpb24+DQogICAg\r\n" + 
        "ICAgICAgICA8L2NvbXBvbmVudD4NCiAgICAgICAgIDwvY29tcG9uZW50Pg0KICAgICAgPC9j\r\n" + 
        "b21wb25lbnQ+DQogICA8L2NvbXBvbmVudD4NCjwvQ2xpbmljYWxEb2N1bWVudD4=\r\n" + 
        "--------------070304090505090508040909--\r\n" + 
        "\r\n";
    }

}


//No need to check RemotingRole; no call to db.