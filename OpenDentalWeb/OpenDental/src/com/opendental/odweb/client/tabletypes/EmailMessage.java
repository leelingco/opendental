package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.opendental.odweb.client.remoting.Serializing;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

public class EmailMessage {
		/** Primary key. */
		public int EmailMessageNum;
		/** FK to patient.PatNum */
		public int PatNum;
		/** Single valid email address. Bcc field might be added later, although it won't be very useful.  We will never allow visible cc for privacy reasons. */
		public String ToAddress;
		/** Valid email address. */
		public String FromAddress;
		/** Subject line. */
		public String Subject;
		/** Body of the email */
		public String BodyText;
		/** Date and time the message was sent. Automated at the UI level. */
		public Date MsgDateTime;
		/** 0=neither, 1=sent, 2=received. */
		public CommSentOrReceived SentOrReceived;

		/** Deep copy of object. */
		public EmailMessage Copy() {
			EmailMessage emailmessage=new EmailMessage();
			emailmessage.EmailMessageNum=this.EmailMessageNum;
			emailmessage.PatNum=this.PatNum;
			emailmessage.ToAddress=this.ToAddress;
			emailmessage.FromAddress=this.FromAddress;
			emailmessage.Subject=this.Subject;
			emailmessage.BodyText=this.BodyText;
			emailmessage.MsgDateTime=this.MsgDateTime;
			emailmessage.SentOrReceived=this.SentOrReceived;
			return emailmessage;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<EmailMessage>");
			sb.append("<EmailMessageNum>").append(EmailMessageNum).append("</EmailMessageNum>");
			sb.append("<PatNum>").append(PatNum).append("</PatNum>");
			sb.append("<ToAddress>").append(Serializing.EscapeForXml(ToAddress)).append("</ToAddress>");
			sb.append("<FromAddress>").append(Serializing.EscapeForXml(FromAddress)).append("</FromAddress>");
			sb.append("<Subject>").append(Serializing.EscapeForXml(Subject)).append("</Subject>");
			sb.append("<BodyText>").append(Serializing.EscapeForXml(BodyText)).append("</BodyText>");
			sb.append("<MsgDateTime>").append(DateTimeFormat.getFormat("yyyyMMddHHmmss").format(MsgDateTime)).append("</MsgDateTime>");
			sb.append("<SentOrReceived>").append(SentOrReceived.ordinal()).append("</SentOrReceived>");
			sb.append("</EmailMessage>");
			return sb.toString();
		}

		/** Sets all the variables on this object based on the values in the XML document.  Variables that are not in the XML document will be null or their default values.
		 * @param doc A parsed XML document.  Must be valid XML.  Does not need to contain a node for every variable on this object.
		 * @throws Exception DeserializeFromXml is entirely encased in a try catch and will throw exceptions if anything goes wrong. */
		public void DeserializeFromXml(Document doc) throws Exception {
			try {
				if(Serializing.GetXmlNodeValue(doc,"EmailMessageNum")!=null) {
					EmailMessageNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"EmailMessageNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"PatNum")!=null) {
					PatNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"PatNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"ToAddress")!=null) {
					ToAddress=Serializing.GetXmlNodeValue(doc,"ToAddress");
				}
				if(Serializing.GetXmlNodeValue(doc,"FromAddress")!=null) {
					FromAddress=Serializing.GetXmlNodeValue(doc,"FromAddress");
				}
				if(Serializing.GetXmlNodeValue(doc,"Subject")!=null) {
					Subject=Serializing.GetXmlNodeValue(doc,"Subject");
				}
				if(Serializing.GetXmlNodeValue(doc,"BodyText")!=null) {
					BodyText=Serializing.GetXmlNodeValue(doc,"BodyText");
				}
				if(Serializing.GetXmlNodeValue(doc,"MsgDateTime")!=null) {
					MsgDateTime=DateTimeFormat.getFormat("yyyyMMddHHmmss").parseStrict(Serializing.GetXmlNodeValue(doc,"MsgDateTime"));
				}
				if(Serializing.GetXmlNodeValue(doc,"SentOrReceived")!=null) {
					SentOrReceived=CommSentOrReceived.values()[Integer.valueOf(Serializing.GetXmlNodeValue(doc,"SentOrReceived"))];
				}
			}
			catch(Exception e) {
				throw e;
			}
		}

		/** 0=neither, 1=sent, 2=received. */
		public enum CommSentOrReceived {
			/** 0 */
			Neither,
			/** 1 */
			Sent,
			/** 2 */
			Received
		}


}
