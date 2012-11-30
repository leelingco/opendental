package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.opendental.odweb.client.remoting.Serializing;

public class HL7DefMessage {
		/** Primary key. */
		public int HL7DefMessageNum;
		/** FK to HL7Def.HL7DefNum */
		public int HL7DefNum;
		/** Stored in db as string, but used in OD as enum MessageTypeHL7. Example: ADT */
		public MessageTypeHL7 MessageType;
		/** Stored in db as string, but used in OD as enum EventTypeHL7. Example: A04, which is only used iwth ADT/ACK. */
		public EventTypeHL7 EventType;
		/** Enum:InOutHL7 Incoming, Outgoing */
		public InOutHL7 InOrOut;
		/** The only purpose of this column is to let you change the order in the HL7 Def windows.  It's just for convenience. */
		public int ItemOrder;
		/** text */
		public String Note;

		/** Deep copy of object. */
		public HL7DefMessage Copy() {
			HL7DefMessage hl7defmessage=new HL7DefMessage();
			hl7defmessage.HL7DefMessageNum=this.HL7DefMessageNum;
			hl7defmessage.HL7DefNum=this.HL7DefNum;
			hl7defmessage.MessageType=this.MessageType;
			hl7defmessage.EventType=this.EventType;
			hl7defmessage.InOrOut=this.InOrOut;
			hl7defmessage.ItemOrder=this.ItemOrder;
			hl7defmessage.Note=this.Note;
			return hl7defmessage;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<HL7DefMessage>");
			sb.append("<HL7DefMessageNum>").append(HL7DefMessageNum).append("</HL7DefMessageNum>");
			sb.append("<HL7DefNum>").append(HL7DefNum).append("</HL7DefNum>");
			sb.append("<MessageType>").append(MessageType.ordinal()).append("</MessageType>");
			sb.append("<EventType>").append(EventType.ordinal()).append("</EventType>");
			sb.append("<InOrOut>").append(InOrOut.ordinal()).append("</InOrOut>");
			sb.append("<ItemOrder>").append(ItemOrder).append("</ItemOrder>");
			sb.append("<Note>").append(Serializing.EscapeForXml(Note)).append("</Note>");
			sb.append("</HL7DefMessage>");
			return sb.toString();
		}

		/** Sets all the variables on this object based on the values in the XML document.  Variables that are not in the XML document will be null or their default values.
		 * @param doc A parsed XML document.  Must be valid XML.  Does not need to contain a node for every variable on this object.
		 * @throws Exception DeserializeFromXml is entirely encased in a try catch and will throw exceptions if anything goes wrong. */
		public void DeserializeFromXml(Document doc) throws Exception {
			try {
				if(Serializing.GetXmlNodeValue(doc,"HL7DefMessageNum")!=null) {
					HL7DefMessageNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"HL7DefMessageNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"HL7DefNum")!=null) {
					HL7DefNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"HL7DefNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"MessageType")!=null) {
					MessageType=MessageTypeHL7.values()[Integer.valueOf(Serializing.GetXmlNodeValue(doc,"MessageType"))];
				}
				if(Serializing.GetXmlNodeValue(doc,"EventType")!=null) {
					EventType=EventTypeHL7.values()[Integer.valueOf(Serializing.GetXmlNodeValue(doc,"EventType"))];
				}
				if(Serializing.GetXmlNodeValue(doc,"InOrOut")!=null) {
					InOrOut=InOutHL7.values()[Integer.valueOf(Serializing.GetXmlNodeValue(doc,"InOrOut"))];
				}
				if(Serializing.GetXmlNodeValue(doc,"ItemOrder")!=null) {
					ItemOrder=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"ItemOrder"));
				}
				if(Serializing.GetXmlNodeValue(doc,"Note")!=null) {
					Note=Serializing.GetXmlNodeValue(doc,"Note");
				}
			}
			catch(Exception e) {
				throw e;
			}
		}

		/** The items in this enumeration can be freely rearranged without damaging the database.  But can't change spelling or remove existing item. */
		public enum MessageTypeHL7 {
			/** Demographics - A01,A04,A08,A28,A31 */
			ADT,
			/** Detailed Financial Transaction - P03 */
			DFT,
			/** Unsolicited Observation Message - R01 */
			ORU,
			/** Scheduling - S12,S13,S14,S15,S22 */
			SIU,
			/** Unsolicited Vaccination Record Update - V04 */
			VXU
		}

		/** The items in this enumeration can be freely rearranged without damaging the database.  But can't change spelling or remove existing item. */
		public enum EventTypeHL7 {
			/** Only used with ADT/ACK. */
			A04,
			/** Only used with DFT/Ack. */
			P03,
			/** Only used with SUI/ACK. */
			S12
		}

		/**  */
		public enum InOutHL7 {
			/** 0 */
			Incoming,
			/** 1 */
			Outgoing
		}


}
