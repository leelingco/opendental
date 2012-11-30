package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.opendental.odweb.client.remoting.Serializing;

public class Laboratory {
		/** Primary key. */
		public int LaboratoryNum;
		/** Description of lab. */
		public String Description;
		/** Freeform text includes punctuation. */
		public String Phone;
		/** Any notes.  No practical limit to amount of text. */
		public String Notes;
		/** FK to sheetdef.SheetDefNum.  Lab slips can be set for individual laboratories.  If zero, then the default internal lab slip will be used instead of a custom lab slip. */
		public int Slip;
		/** . */
		public String Address;
		/** . */
		public String City;
		/** . */
		public String State;
		/** . */
		public String Zip;
		/** . */
		public String Email;
		/** . */
		public String WirelessPhone;

		/** Deep copy of object. */
		public Laboratory Copy() {
			Laboratory laboratory=new Laboratory();
			laboratory.LaboratoryNum=this.LaboratoryNum;
			laboratory.Description=this.Description;
			laboratory.Phone=this.Phone;
			laboratory.Notes=this.Notes;
			laboratory.Slip=this.Slip;
			laboratory.Address=this.Address;
			laboratory.City=this.City;
			laboratory.State=this.State;
			laboratory.Zip=this.Zip;
			laboratory.Email=this.Email;
			laboratory.WirelessPhone=this.WirelessPhone;
			return laboratory;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<Laboratory>");
			sb.append("<LaboratoryNum>").append(LaboratoryNum).append("</LaboratoryNum>");
			sb.append("<Description>").append(Serializing.EscapeForXml(Description)).append("</Description>");
			sb.append("<Phone>").append(Serializing.EscapeForXml(Phone)).append("</Phone>");
			sb.append("<Notes>").append(Serializing.EscapeForXml(Notes)).append("</Notes>");
			sb.append("<Slip>").append(Slip).append("</Slip>");
			sb.append("<Address>").append(Serializing.EscapeForXml(Address)).append("</Address>");
			sb.append("<City>").append(Serializing.EscapeForXml(City)).append("</City>");
			sb.append("<State>").append(Serializing.EscapeForXml(State)).append("</State>");
			sb.append("<Zip>").append(Serializing.EscapeForXml(Zip)).append("</Zip>");
			sb.append("<Email>").append(Serializing.EscapeForXml(Email)).append("</Email>");
			sb.append("<WirelessPhone>").append(Serializing.EscapeForXml(WirelessPhone)).append("</WirelessPhone>");
			sb.append("</Laboratory>");
			return sb.toString();
		}

		/** Sets all the variables on this object based on the values in the XML document.  Variables that are not in the XML document will be null or their default values.
		 * @param doc A parsed XML document.  Must be valid XML.  Does not need to contain a node for every variable on this object.
		 * @throws Exception DeserializeFromXml is entirely encased in a try catch and will throw exceptions if anything goes wrong. */
		public void DeserializeFromXml(Document doc) throws Exception {
			try {
				if(Serializing.GetXmlNodeValue(doc,"LaboratoryNum")!=null) {
					LaboratoryNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"LaboratoryNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"Description")!=null) {
					Description=Serializing.GetXmlNodeValue(doc,"Description");
				}
				if(Serializing.GetXmlNodeValue(doc,"Phone")!=null) {
					Phone=Serializing.GetXmlNodeValue(doc,"Phone");
				}
				if(Serializing.GetXmlNodeValue(doc,"Notes")!=null) {
					Notes=Serializing.GetXmlNodeValue(doc,"Notes");
				}
				if(Serializing.GetXmlNodeValue(doc,"Slip")!=null) {
					Slip=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"Slip"));
				}
				if(Serializing.GetXmlNodeValue(doc,"Address")!=null) {
					Address=Serializing.GetXmlNodeValue(doc,"Address");
				}
				if(Serializing.GetXmlNodeValue(doc,"City")!=null) {
					City=Serializing.GetXmlNodeValue(doc,"City");
				}
				if(Serializing.GetXmlNodeValue(doc,"State")!=null) {
					State=Serializing.GetXmlNodeValue(doc,"State");
				}
				if(Serializing.GetXmlNodeValue(doc,"Zip")!=null) {
					Zip=Serializing.GetXmlNodeValue(doc,"Zip");
				}
				if(Serializing.GetXmlNodeValue(doc,"Email")!=null) {
					Email=Serializing.GetXmlNodeValue(doc,"Email");
				}
				if(Serializing.GetXmlNodeValue(doc,"WirelessPhone")!=null) {
					WirelessPhone=Serializing.GetXmlNodeValue(doc,"WirelessPhone");
				}
			}
			catch(Exception e) {
				throw e;
			}
		}


}
