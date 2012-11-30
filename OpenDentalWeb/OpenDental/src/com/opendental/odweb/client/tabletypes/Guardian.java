package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.opendental.odweb.client.remoting.Serializing;

public class Guardian {
		/** Primary key. */
		public int GuardianNum;
		/** FK to patient.PatNum. */
		public int PatNumChild;
		/** FK to patient.PatNum. */
		public int PatNumGuardian;
		/** Enum:GuardianRelationship Father, Mother, Stepfather, Stepmother, Grandfather, Grandmother, Sitter. */
		public GuardianRelationship Relationship;

		/** Deep copy of object. */
		public Guardian Copy() {
			Guardian guardian=new Guardian();
			guardian.GuardianNum=this.GuardianNum;
			guardian.PatNumChild=this.PatNumChild;
			guardian.PatNumGuardian=this.PatNumGuardian;
			guardian.Relationship=this.Relationship;
			return guardian;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<Guardian>");
			sb.append("<GuardianNum>").append(GuardianNum).append("</GuardianNum>");
			sb.append("<PatNumChild>").append(PatNumChild).append("</PatNumChild>");
			sb.append("<PatNumGuardian>").append(PatNumGuardian).append("</PatNumGuardian>");
			sb.append("<Relationship>").append(Relationship.ordinal()).append("</Relationship>");
			sb.append("</Guardian>");
			return sb.toString();
		}

		/** Sets all the variables on this object based on the values in the XML document.  Variables that are not in the XML document will be null or their default values.
		 * @param doc A parsed XML document.  Must be valid XML.  Does not need to contain a node for every variable on this object.
		 * @throws Exception DeserializeFromXml is entirely encased in a try catch and will throw exceptions if anything goes wrong. */
		public void DeserializeFromXml(Document doc) throws Exception {
			try {
				if(Serializing.GetXmlNodeValue(doc,"GuardianNum")!=null) {
					GuardianNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"GuardianNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"PatNumChild")!=null) {
					PatNumChild=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"PatNumChild"));
				}
				if(Serializing.GetXmlNodeValue(doc,"PatNumGuardian")!=null) {
					PatNumGuardian=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"PatNumGuardian"));
				}
				if(Serializing.GetXmlNodeValue(doc,"Relationship")!=null) {
					Relationship=GuardianRelationship.values()[Integer.valueOf(Serializing.GetXmlNodeValue(doc,"Relationship"))];
				}
			}
			catch(Exception e) {
				throw e;
			}
		}

		/** Relationship of a child to a parent/guardian. */
		public enum GuardianRelationship {
			/** 0 */
			Father,
			/** 1 */
			Mother,
			/** 2 */
			Stepfather,
			/** 3 */
			Stepmother,
			/** 4 */
			Grandfather,
			/** 5 */
			Grandmother,
			/** 6 */
			Sitter
		}


}
