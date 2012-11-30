package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.opendental.odweb.client.remoting.Serializing;

public class ApptField {
		/** Primary key. */
		public int ApptFieldNum;
		/** FK to appointment.AptNum */
		public int AptNum;
		/** FK to apptfielddef.FieldName.  The full name is shown here for ease of use when running queries.  But the user is only allowed to change fieldNames in the patFieldDef setup window. */
		public String FieldName;
		/** Any text that the user types in.  Will later allow some automation. */
		public String FieldValue;

		/** Deep copy of object. */
		public ApptField Copy() {
			ApptField apptfield=new ApptField();
			apptfield.ApptFieldNum=this.ApptFieldNum;
			apptfield.AptNum=this.AptNum;
			apptfield.FieldName=this.FieldName;
			apptfield.FieldValue=this.FieldValue;
			return apptfield;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<ApptField>");
			sb.append("<ApptFieldNum>").append(ApptFieldNum).append("</ApptFieldNum>");
			sb.append("<AptNum>").append(AptNum).append("</AptNum>");
			sb.append("<FieldName>").append(Serializing.EscapeForXml(FieldName)).append("</FieldName>");
			sb.append("<FieldValue>").append(Serializing.EscapeForXml(FieldValue)).append("</FieldValue>");
			sb.append("</ApptField>");
			return sb.toString();
		}

		/** Sets all the variables on this object based on the values in the XML document.  Variables that are not in the XML document will be null or their default values.
		 * @param doc A parsed XML document.  Must be valid XML.  Does not need to contain a node for every variable on this object.
		 * @throws Exception DeserializeFromXml is entirely encased in a try catch and will throw exceptions if anything goes wrong. */
		public void DeserializeFromXml(Document doc) throws Exception {
			try {
				if(Serializing.GetXmlNodeValue(doc,"ApptFieldNum")!=null) {
					ApptFieldNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"ApptFieldNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"AptNum")!=null) {
					AptNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"AptNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"FieldName")!=null) {
					FieldName=Serializing.GetXmlNodeValue(doc,"FieldName");
				}
				if(Serializing.GetXmlNodeValue(doc,"FieldValue")!=null) {
					FieldValue=Serializing.GetXmlNodeValue(doc,"FieldValue");
				}
			}
			catch(Exception e) {
				throw e;
			}
		}


}
