package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.opendental.odweb.client.remoting.Serializing;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

public class Sheet {
		/** Primary key. */
		public int SheetNum;
		/** Enum:SheetTypeEnum */
		public SheetTypeEnum SheetType;
		/** FK to patient.PatNum.  A saved sheet is always attached to a patient (except deposit slip).  There are a few sheets that are so minor that they don't get saved, such as a Carrier label. */
		public int PatNum;
		/** The date and time of the sheet as it will be displayed in the commlog. */
		public Date DateTimeSheet;
		/** The default fontSize for the sheet.  The actual font must still be saved with each sheetField. */
		public float FontSize;
		/** The default fontName for the sheet.  The actual font must still be saved with each sheetField. */
		public String FontName;
		/** Width of the sheet in pixels, 100 pixels per inch. */
		public int Width;
		/** Height of the sheet in pixels, 100 pixels per inch. */
		public int Height;
		/** . */
		public boolean IsLandscape;
		/** An internal note for the use of the office staff regarding the sheet.  Not to be printed on the sheet in any way. */
		public String InternalNote;
		/** Copied from the SheetDef description. */
		public String Description;
		/** The order that this sheet will show in the patient terminal for the patient to fill out.  Or zero if not set. */
		public byte ShowInTerminal;
		/** True if this sheet was downloaded from the webforms service. */
		public boolean IsWebForm;

		/** Deep copy of object. */
		public Sheet Copy() {
			Sheet sheet=new Sheet();
			sheet.SheetNum=this.SheetNum;
			sheet.SheetType=this.SheetType;
			sheet.PatNum=this.PatNum;
			sheet.DateTimeSheet=this.DateTimeSheet;
			sheet.FontSize=this.FontSize;
			sheet.FontName=this.FontName;
			sheet.Width=this.Width;
			sheet.Height=this.Height;
			sheet.IsLandscape=this.IsLandscape;
			sheet.InternalNote=this.InternalNote;
			sheet.Description=this.Description;
			sheet.ShowInTerminal=this.ShowInTerminal;
			sheet.IsWebForm=this.IsWebForm;
			return sheet;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<Sheet>");
			sb.append("<SheetNum>").append(SheetNum).append("</SheetNum>");
			sb.append("<SheetType>").append(SheetType.ordinal()).append("</SheetType>");
			sb.append("<PatNum>").append(PatNum).append("</PatNum>");
			sb.append("<DateTimeSheet>").append(DateTimeFormat.getFormat("yyyyMMddHHmmss").format(DateTimeSheet)).append("</DateTimeSheet>");
			sb.append("<FontSize>").append(FontSize).append("</FontSize>");
			sb.append("<FontName>").append(Serializing.EscapeForXml(FontName)).append("</FontName>");
			sb.append("<Width>").append(Width).append("</Width>");
			sb.append("<Height>").append(Height).append("</Height>");
			sb.append("<IsLandscape>").append((IsLandscape)?1:0).append("</IsLandscape>");
			sb.append("<InternalNote>").append(Serializing.EscapeForXml(InternalNote)).append("</InternalNote>");
			sb.append("<Description>").append(Serializing.EscapeForXml(Description)).append("</Description>");
			sb.append("<ShowInTerminal>").append(ShowInTerminal).append("</ShowInTerminal>");
			sb.append("<IsWebForm>").append((IsWebForm)?1:0).append("</IsWebForm>");
			sb.append("</Sheet>");
			return sb.toString();
		}

		/** Sets all the variables on this object based on the values in the XML document.  Variables that are not in the XML document will be null or their default values.
		 * @param doc A parsed XML document.  Must be valid XML.  Does not need to contain a node for every variable on this object.
		 * @throws Exception DeserializeFromXml is entirely encased in a try catch and will throw exceptions if anything goes wrong. */
		public void DeserializeFromXml(Document doc) throws Exception {
			try {
				if(Serializing.GetXmlNodeValue(doc,"SheetNum")!=null) {
					SheetNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"SheetNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"SheetType")!=null) {
					SheetType=SheetTypeEnum.values()[Integer.valueOf(Serializing.GetXmlNodeValue(doc,"SheetType"))];
				}
				if(Serializing.GetXmlNodeValue(doc,"PatNum")!=null) {
					PatNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"PatNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"DateTimeSheet")!=null) {
					DateTimeSheet=DateTimeFormat.getFormat("yyyyMMddHHmmss").parseStrict(Serializing.GetXmlNodeValue(doc,"DateTimeSheet"));
				}
				if(Serializing.GetXmlNodeValue(doc,"FontSize")!=null) {
					FontSize=Float.valueOf(Serializing.GetXmlNodeValue(doc,"FontSize"));
				}
				if(Serializing.GetXmlNodeValue(doc,"FontName")!=null) {
					FontName=Serializing.GetXmlNodeValue(doc,"FontName");
				}
				if(Serializing.GetXmlNodeValue(doc,"Width")!=null) {
					Width=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"Width"));
				}
				if(Serializing.GetXmlNodeValue(doc,"Height")!=null) {
					Height=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"Height"));
				}
				if(Serializing.GetXmlNodeValue(doc,"IsLandscape")!=null) {
					IsLandscape=(Serializing.GetXmlNodeValue(doc,"IsLandscape")=="0")?false:true;
				}
				if(Serializing.GetXmlNodeValue(doc,"InternalNote")!=null) {
					InternalNote=Serializing.GetXmlNodeValue(doc,"InternalNote");
				}
				if(Serializing.GetXmlNodeValue(doc,"Description")!=null) {
					Description=Serializing.GetXmlNodeValue(doc,"Description");
				}
				if(Serializing.GetXmlNodeValue(doc,"ShowInTerminal")!=null) {
					ShowInTerminal=Byte.valueOf(Serializing.GetXmlNodeValue(doc,"ShowInTerminal"));
				}
				if(Serializing.GetXmlNodeValue(doc,"IsWebForm")!=null) {
					IsWebForm=(Serializing.GetXmlNodeValue(doc,"IsWebForm")=="0")?false:true;
				}
			}
			catch(Exception e) {
				throw e;
			}
		}

		/**  */
		public enum SheetTypeEnum {
			/**  */
			LabelPatient,
			/**  */
			LabelCarrier,
			/**  */
			LabelReferral,
			/**  */
			ReferralSlip,
			/**  */
			LabelAppointment,
			/**  */
			Rx,
			/** 6-Requires SheetParameter for PatNum. */
			Consent,
			/** 7-Requires SheetParameter for PatNum. */
			PatientLetter,
			/** 8-Requires SheetParameters for PatNum,ReferralNum. */
			ReferralLetter,
			/**  */
			PatientForm,
			/**  */
			RoutingSlip,
			/**  */
			MedicalHistory,
			/**  */
			LabSlip,
			/**  */
			ExamSheet,
			/** 14-Requires SheetParameter for PatNum. */
			DepositSlip
		}


}
