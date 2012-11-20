package com.opendental.odweb.client.tabletypes;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.opendental.odweb.client.remoting.Serializing;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

public class JournalEntry {
		/** Primary key. */
		public int JournalEntryNum;
		/** FK to transaction.TransactionNum */
		public int TransactionNum;
		/** FK to account.AccountNum */
		public int AccountNum;
		/** Always the same for all journal entries within one transaction. */
		public Date DateDisplayed;
		/** Negative numbers never allowed. */
		public double DebitAmt;
		/** Negative numbers never allowed. */
		public double CreditAmt;
		/** . */
		public String Memo;
		/** A human-readable description of the splits.  Used only for display purposes. */
		public String Splits;
		/** Any user-defined string.  Usually a check number, but can also be D for deposit, Adj, etc. */
		public String CheckNumber;
		/** FK to reconcile.ReconcileNum. 0 if not attached to a reconcile. Not allowed to alter amounts if attached. */
		public int ReconcileNum;

		/** Deep copy of object. */
		public JournalEntry Copy() {
			JournalEntry journalentry=new JournalEntry();
			journalentry.JournalEntryNum=this.JournalEntryNum;
			journalentry.TransactionNum=this.TransactionNum;
			journalentry.AccountNum=this.AccountNum;
			journalentry.DateDisplayed=this.DateDisplayed;
			journalentry.DebitAmt=this.DebitAmt;
			journalentry.CreditAmt=this.CreditAmt;
			journalentry.Memo=this.Memo;
			journalentry.Splits=this.Splits;
			journalentry.CheckNumber=this.CheckNumber;
			journalentry.ReconcileNum=this.ReconcileNum;
			return journalentry;
		}

		/** Serialize the object into XML. */
		public String SerializeToXml() {
			StringBuilder sb=new StringBuilder();
			sb.append("<JournalEntry>");
			sb.append("<JournalEntryNum>").append(JournalEntryNum).append("</JournalEntryNum>");
			sb.append("<TransactionNum>").append(TransactionNum).append("</TransactionNum>");
			sb.append("<AccountNum>").append(AccountNum).append("</AccountNum>");
			sb.append("<DateDisplayed>").append(DateTimeFormat.getFormat("yyyyMMddHHmmss").format(DateDisplayed)).append("</DateDisplayed>");
			sb.append("<DebitAmt>").append(DebitAmt).append("</DebitAmt>");
			sb.append("<CreditAmt>").append(CreditAmt).append("</CreditAmt>");
			sb.append("<Memo>").append(Serializing.EscapeForXml(Memo)).append("</Memo>");
			sb.append("<Splits>").append(Serializing.EscapeForXml(Splits)).append("</Splits>");
			sb.append("<CheckNumber>").append(Serializing.EscapeForXml(CheckNumber)).append("</CheckNumber>");
			sb.append("<ReconcileNum>").append(ReconcileNum).append("</ReconcileNum>");
			sb.append("</JournalEntry>");
			return sb.toString();
		}

		/** Sets the variables for this object based on the values from the XML.
		 * @param xml The XML passed in must be valid and contain a node for every variable on this object.
		 * @throws Exception Deserialize is encased in a try catch and will pass any thrown exception on. */
		public void DeserializeFromXml(String xml) throws Exception {
			try {
				Document doc=XMLParser.parse(xml);
				if(Serializing.GetXmlNodeValue(doc,"JournalEntryNum")!=null) {
					JournalEntryNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"JournalEntryNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"TransactionNum")!=null) {
					TransactionNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"TransactionNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"AccountNum")!=null) {
					AccountNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"AccountNum"));
				}
				if(Serializing.GetXmlNodeValue(doc,"DateDisplayed")!=null) {
					DateDisplayed=DateTimeFormat.getFormat("yyyyMMddHHmmss").parseStrict(Serializing.GetXmlNodeValue(doc,"DateDisplayed"));
				}
				if(Serializing.GetXmlNodeValue(doc,"DebitAmt")!=null) {
					DebitAmt=Double.valueOf(Serializing.GetXmlNodeValue(doc,"DebitAmt"));
				}
				if(Serializing.GetXmlNodeValue(doc,"CreditAmt")!=null) {
					CreditAmt=Double.valueOf(Serializing.GetXmlNodeValue(doc,"CreditAmt"));
				}
				if(Serializing.GetXmlNodeValue(doc,"Memo")!=null) {
					Memo=Serializing.GetXmlNodeValue(doc,"Memo");
				}
				if(Serializing.GetXmlNodeValue(doc,"Splits")!=null) {
					Splits=Serializing.GetXmlNodeValue(doc,"Splits");
				}
				if(Serializing.GetXmlNodeValue(doc,"CheckNumber")!=null) {
					CheckNumber=Serializing.GetXmlNodeValue(doc,"CheckNumber");
				}
				if(Serializing.GetXmlNodeValue(doc,"ReconcileNum")!=null) {
					ReconcileNum=Integer.valueOf(Serializing.GetXmlNodeValue(doc,"ReconcileNum"));
				}
			}
			catch(Exception e) {
				throw e;
			}
		}


}