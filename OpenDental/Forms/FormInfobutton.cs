using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Xml;
using OpenDentBusiness;
using CodeBase;
using System.Globalization;
using System.Xml.XPath;
using System.IO;
using OpenDental.UI;

namespace OpenDental {
	public partial class FormInfobutton:Form {
		public Patient PatCur;
		///<summary>Usually filled from within the form by using Patcur.PriProv</summary>
		public Provider ProvCur;
		///<summary>Knowledge request objects, possible object types are: DiseaseDef, Medication, LabResult, ICD9, Icd10, Snomed, RxNorm, Loinc, or LabResult.  Should not break if unsupported objects are in list.</summary>
		public List<object> ListObjects;
		//public List<DiseaseDef> ListProblems;//should this be named disease or problem? Also snomed/medication
		//public List<Medication> ListMedications;
		//public List<LabResult> ListLabResults;
		/////<summary>Used to add various codes that are not explicitly related to a problem, medication, or allergy.</summary>
		//public List<Snomed> ListSnomed;
		private ActTaskCode ActTC;//may need to make this public later.
		public ObservationInterpretationNormality ObsInterpretation;
		public ActEncounterCode ActEC;
		public bool UseAge;
		public bool UseAgeGroup;
		public bool PerformerIsProvider;
		public bool RecipientIsProvider;
		private CultureInfo[] arrayCultures;

		public FormInfobutton() {
			ListObjects=new List<object>();
			InitializeComponent();
			Lan.F(this);
		}

		private void FormInfobutton_Load(object sender,EventArgs e) {
#if DEBUG
			//Create an EhrLab with EhrLabResults attached so that we can add then to the List of objects to testing.
			#region EhrLab
			EhrLab ehrLab=EhrLabs.ProcessHl7Message(@"MSH|^~\&|^2.16.840.1.113883.3.72.5.20^ISO|^2.16.840.1.113883.3.72.5.21^ISO||^2.16.840.1.113883.3.72.5.23^ISO|20110531140551-0500||ORU^R01^ORU_R01|NIST-LRI-GU-002.00|T|2.5.1|||AL|NE|||||LRI_Common_Component^^2.16.840.1.113883.9.16^ISO~LRI_GU_Component^^2.16.840.1.113883.9.12^ISO~LRI_RU_Component^^2.16.840.1.113883.9.14^ISO
PID|1||PATID1234^^^&2.16.840.1.113883.3.72.5.30.2&ISO^MR||Jones^William^A||19610615|M||2106-3^White^HL70005
ORC|RE|ORD666555^^2.16.840.1.113883.3.72.5.24^ISO|R-991133^^2.16.840.1.113883.3.72.5.25^ISO|GORD874233^^2.16.840.1.113883.3.72.5.24^ISO||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI
OBR|1|ORD666555^^2.16.840.1.113883.3.72.5.24^ISO|R-991133^^2.16.840.1.113883.3.72.5.25^ISO|57021-8^CBC W Auto Differential panel in Blood^LN^4456544^CBC^99USI^^^CBC W Auto Differential panel in Blood|||20110103143428-0800|||||||||57422^Radon^Nicholas^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI||||||20110104170028-0800|||F|||10093^Deluca^Naddy^^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^NPI|||||||||||||||||||||CC^Carbon Copy^HL70507
OBX|1|NM|26453-1^Erythrocytes [#/volume] in Blood^LN^^^^^^Erythrocytes [#/volume] in Blood||4.41|10*6/uL^million per microliter^UCUM|4.3 to 6.2|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|2|NM|718-7^Hemoglobin [Mass/volume] in Blood^LN^^^^^^Hemoglobin [Mass/volume] in Blood||12.5|g/mL^grams per milliliter^UCUM|13 to 18|L|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|3|NM|20570-8^Hematocrit [Volume Fraction] of Blood^LN^^^^^^Hematocrit [Volume Fraction] of Blood||41|%^percent^UCUM|40 to 52|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|4|NM|26464-8^Leukocytes [#/volume] in Blood^LN^^^^^^Leukocytes [#/volume] in Blood||105600|{cells}/uL^cells per microliter^UCUM|4300 to 10800|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|5|NM|26515-7^Platelets [#/volume] in Blood^LN^^^^^^Platelets [#/volume] in Blood||210000|{cells}/uL^cells per microliter^UCUM|150000 to 350000|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|6|NM|30428-7^Erythrocyte mean corpuscular volume [Entitic volume]^LN^^^^^^Erythrocyte mean corpuscular volume [Entitic volume]||91|fL^femtoliter^UCUM|80 to 95|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|7|NM|28539-5^Erythrocyte mean corpuscular hemoglobin [Entitic mass]^LN^^^^^^Erythrocyte mean corpuscular hemoglobin [Entitic mass]||29|pg/{cell}^picograms per cell^UCUM|27 to 31|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|8|NM|28540-3^Erythrocyte mean corpuscular hemoglobin concentration [Mass/volume]^LN^^^^^^Erythrocyte mean corpuscular hemoglobin concentration [Mass/volume]||32.4|g/dL^grams per deciliter^UCUM|32 to 36|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|9|NM|30385-9^Erythrocyte distribution width [Ratio]^LN^^^^^^Erythrocyte distribution width [Ratio]||10.5|%^percent^UCUM|10.2 to 14.5|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|10|NM|26444-0^Basophils [#/volume] in Blood^LN^^^^^^Basophils [#/volume] in Blood||0.1|10*3/uL^thousand per microliter^UCUM|0 to 0.3|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|11|NM|30180-4^Basophils/100 leukocytes in Blood^LN^^^^^^Basophils/100 leukocytes in Blood||0.1|%^percent^UCUM|0 to 2|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|12|NM|26484-6^Monocytes [#/volume] in Blood^LN^^^^^^Monocytes [#/volume] in Blood||3|10*3/uL^thousand per microliter^UCUM|0.0 to 13.0|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|13|NM|26485-3^Monocytes/100 leukocytes in Blood^LN^^^^^^Monocytes/100 leukocytes in Blood||3|%^percent^UCUM|0 to 10|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|14|NM|26449-9^Eosinophils [#/volume] in Blood^LN^^^^^^Eosinophils [#/volume] in Blood||2.1|10*3/uL^thousand per microliter^UCUM|0.0 to 0.45|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|15|NM|26450-7^Eosinophils/100 leukocytes in Blood^LN^^^^^^Eosinophils/100 leukocytes in Blood||2|%^percent^UCUM|0 to 6|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|16|NM|26474-7^Lymphocytes [#/volume] in Blood^LN^^^^^^Lymphocytes [#/volume] in Blood||41.2|10*3/uL^thousand per microliter^UCUM|1.0 to 4.8|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|17|NM|26478-8^Lymphocytes/100 leukocytes in Blood^LN^^^^^^Lymphocytes/100 leukocytes in Blood||39|%^percent^UCUM|15.0 to 45.0|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|18|NM|26499-4^Neutrophils [#/volume] in Blood^LN^^^^^^Neutrophils [#/volume] in Blood||58|10*3/uL^thousand per microliter^UCUM|1.5 to 7.0|HH|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|19|NM|26511-6^Neutrophils/100 leukocytes in Blood^LN^^^^^^Neutrophils/100 leukocytes in Blood||55|%^percent^UCUM|50 to 73|N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|20|CWE|38892-6^Anisocytosis [Presence] in Blood^LN^^^^^^Anisocytosis [Presence] in Blood||260348001^Present ++ out of ++++^SCT^^^^^^Moderate Anisocytosis|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|21|CWE|30400-6^Hypochromia [Presence] in Blood^LN^^^^^^Hypochromia [Presence] in Blood||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|22|CWE|30424-6^Macrocytes [Presence] in Blood^LN^^^^^^Macrocytes [Presence] in Blood||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|23|CWE|30434-5^Microcytes [Presence] in Blood^LN^^^^^^Microcytes [Presence] in Blood||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|24|CWE|779-9^Poikilocytosis [Presence] in Blood by Light microscopy^LN^^^^^^Poikilocytosis [Presence] in Blood by Light microscopy||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|25|CWE|10378-8^Polychromasia [Presence] in Blood by Light microscopy^LN^^^^^^Polychromasia [Presence] in Blood by Light microscopy||260415000^not detected^SCT^^^^^^None seen|||N|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|26|TX|6742-1^Erythrocyte morphology finding [Identifier] in Blood^LN^^^^^^Erythrocyte morphology finding [Identifier] in Blood||Many spherocytes present.|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|27|TX|11156-7^Leukocyte morphology finding [Identifier] in Blood^LN^^^^^^Leukocyte morphology finding [Identifier] in Blood||Reactive morphology in lymphoid cells.|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
OBX|28|TX|11125-2^Platelet morphology finding [Identifier] in Blood^LN^^^^^^Platelet morphology finding [Identifier] in Blood||Platelets show defective granulation.|||A|||F|||20110103143428-0800|||||20110103163428-0800||||Century Hospital^^^^^&2.16.840.1.113883.3.72.5.30.1&ISO^XX^^^987|2070 Test Park^^Los Angeles^CA^90067^^B|2343242^Knowsalot^Phil^^^Dr.^^^&2.16.840.1.113883.3.72.5.30.1&ISO^L^^^DN
SPM|1|||119297000^BLD^SCT^^^^^^Blood|||||||||||||20110103143428-0800
",PatCur)[0];
			#endregion
			foreach(EhrLabResult ehrLR in ehrLab.ListEhrLabResults) {
				ListObjects.Add(ehrLR);
			}
#endif
			fillLanguageCombos();
			fillEncounterCombo();
			fillTaskCombo();
			fillContext();
			fillKnowledgeRequestitems();
		}

		private void fillLanguageCombos() {
			arrayCultures = CultureInfo.GetCultures(CultureTypes.SpecificCultures);
			for(int i=0;i<arrayCultures.Length;i++) {
				comboPatLang.Items.Add(arrayCultures[i].DisplayName);
				comboProvLang.Items.Add(arrayCultures[i].DisplayName);
			}
		}

		private void fillEncounterCombo() {
			//for(int i=0;i<Enum.GetValues(typeof(ActEncounterCode)).Length;i++){
			//  comboEncType.Items.Add(Enum.GetName(typeof(ActEncounterCode),i));
			//}
			comboEncType.Items.Add("ambulatory");
			comboEncType.Items.Add("emergency");
			comboEncType.Items.Add("field");
			comboEncType.Items.Add("home health");
			comboEncType.Items.Add("inpatient encounter");
			comboEncType.Items.Add("short stay");
			comboEncType.Items.Add("virtual");
		}

		private void fillTaskCombo() {
			comboTask.Items.Add("Order Entry");
			comboTask.Items.Add("Patient Documentation");
			comboTask.Items.Add("Patient Information Review");
		}

		private void fillContext() {
			//Fill Patient-------------------------------------------------------------------------------------------------------------------
			if(PatCur!=null) {
				textPatName.Text=PatCur.GetNameFL();
				if(PatCur.Birthdate!=DateTime.MinValue) {
					textPatBirth.Text=PatCur.Birthdate.ToShortDateString();
				}
				comboPatLang.SelectedIndex=comboPatLang.Items.IndexOf(System.Globalization.CultureInfo.CurrentCulture.DisplayName);
				switch(PatCur.Gender) {
					case PatientGender.Female:
						radioPatGenFem.Checked=true;
						break;
					case PatientGender.Male:
						radioPatGenMale.Checked=true;
						break;
					case PatientGender.Unknown:
					default:
						radioPatGenUn.Checked=true;
						break;
				}
			}
			//Fill Provider------------------------------------------------------------------------------------------------------------------
			if(ProvCur==null && PatCur!=null) {
				ProvCur=Providers.GetProv(PatCur.PriProv);
			}
			if(ProvCur==null) {
				ProvCur=Providers.GetProv(PrefC.GetLong(PrefName.PracticeDefaultProv));
			}
			if(ProvCur!=null) {
				textProvName.Text=ProvCur.GetFormalName();
				textProvID.Text=ProvCur.NationalProvID;
				comboProvLang.SelectedIndex=comboPatLang.Items.IndexOf(System.Globalization.CultureInfo.CurrentCulture.DisplayName);
			}
			//Fill Organization--------------------------------------------------------------------------------------------------------------
			textOrgName.Text=PrefC.GetString(PrefName.PracticeTitle);
			//Fill Encounter-----------------------------------------------------------------------------------------------------------------
			ActEC=ActEncounterCode.AMB;
			comboEncType.SelectedIndex=(int)ActEC;//ambulatory
			if(PatCur!=null) {
				textEncLocID.Text=PatCur.ClinicNum.ToString();//do not use to generate message if this value is zero.
			}
			//Fill Requestor/Recievor--------------------------------------------------------------------------------------------------------
			radioReqProv.Checked=PerformerIsProvider;
			radioReqPat.Checked=!PerformerIsProvider;
			radioRecProv.Checked=RecipientIsProvider;
			radioRecPat.Checked=!RecipientIsProvider;
			//Fill Task Type-----------------------------------------------------------------------------------------------------------------
			ActTC=ActTaskCode.PATINFO;//may need to change this later.
			comboTask.SelectedIndex=(int)ActTC;
		}

		private void fillKnowledgeRequestitems() {
			gridMain.BeginUpdate();
			gridMain.Columns.Clear();
			ODGridColumn col;
			col=new ODGridColumn("Type",80);
			gridMain.Columns.Add(col);
			col=new ODGridColumn("Code",80);
			gridMain.Columns.Add(col);
			col=new ODGridColumn("CodeSystem",80);
			gridMain.Columns.Add(col);
			col=new ODGridColumn("Description",80);
			gridMain.Columns.Add(col);
			gridMain.Rows.Clear();
			ODGridRow row;
			for(int i=0;i<ListObjects.Count;i++) {
				row=new ODGridRow();
				switch(ListObjects[i].GetType().ToString().Split(new string[]{"."},StringSplitOptions.None)[1]) {
					case "DiseaseDef":
						if(((DiseaseDef)ListObjects[i]).ICD9Code!="") {
							row=new ODGridRow();//just in case;
							ICD9 icd9=ICD9s.GetByCode(((DiseaseDef)ListObjects[i]).ICD9Code);
							row.Cells.Add("Problem");
							row.Cells.Add(icd9.ICD9Code);
							row.Cells.Add("ICD9-CM");
							row.Cells.Add(icd9.Description);
							row.Tag=icd9;
							gridMain.Rows.Add(row);
						}
						if(((DiseaseDef)ListObjects[i]).SnomedCode!="") {
							row=new ODGridRow();//just in case
							Snomed snomed=Snomeds.GetByCode(((DiseaseDef)ListObjects[i]).SnomedCode);
							row.Cells.Add("Problem");
							row.Cells.Add(snomed.SnomedCode);
							row.Cells.Add("SNOMED CT");
							row.Cells.Add(snomed.Description);
							row.Tag=snomed;
							gridMain.Rows.Add(row);
						}
						if(((DiseaseDef)ListObjects[i]).Icd10Code!="") {
							row=new ODGridRow();//just in case
							row.Cells.Add("Problem");
							Icd10 icd10=Icd10s.GetByCode(((DiseaseDef)ListObjects[i]).Icd10Code);
							row.Cells.Add(icd10.Icd10Code);
							row.Cells.Add("SNOMED CT");
							row.Cells.Add(icd10.Description);
							row.Tag=icd10;
							gridMain.Rows.Add(row);
						}
						if(((DiseaseDef)ListObjects[i]).ICD9Code!=""
							&& ((DiseaseDef)ListObjects[i]).SnomedCode!=""
							&& ((DiseaseDef)ListObjects[i]).Icd10Code!="") 
						{
							row=new ODGridRow();//just in case
							row.Cells.Add("Problem");
							row.Cells.Add("none");
							row.Cells.Add("none");
							row.Cells.Add(((DiseaseDef)ListObjects[i]).DiseaseName);
							row.Tag=null;
							gridMain.Rows.Add(row);
						}
						continue;//not break, because we have already added the rows.
					case "Medication":
						if(((Medication)ListObjects[i]).RxCui!=0) {
							row=new ODGridRow();//just in case
							row.Cells.Add("Medication");
							RxNorm rxNorm=RxNorms.GetByRxCUI(((Medication)ListObjects[i]).RxCui.ToString());
							row.Cells.Add(rxNorm.RxCui);
							row.Cells.Add("RxNorm");
							row.Cells.Add(rxNorm.Description);
							row.Tag=rxNorm;
							gridMain.Rows.Add(row);
						}
						if(((Medication)ListObjects[i]).RxCui==0) {
							row=new ODGridRow();//just in case
							row.Cells.Add("Medication");
							row.Cells.Add("none");
							row.Cells.Add("none");
							row.Cells.Add(((Medication)ListObjects[i]).MedName);
							row.Tag=ListObjects[i];
							gridMain.Rows.Add(row);
						}
						continue;
					case "ICD9":
						ICD9 icd9Obj=(ICD9)ListObjects[i];
						row.Cells.Add("Code");
						row.Cells.Add(icd9Obj.ICD9Code);
						row.Cells.Add("ICD9 CM");
						row.Cells.Add(icd9Obj.Description);
						row.Tag=icd9Obj;
						break;
					case "Snomed":
						Snomed snomedObj=(Snomed)ListObjects[i];
						row.Cells.Add("Code");
						row.Cells.Add(snomedObj.SnomedCode);
						row.Cells.Add("SNOMED CT");
						row.Cells.Add(snomedObj.Description);
						row.Tag=snomedObj;
						break;
					case "Icd10":
						Icd10 icd10Obj=(Icd10)ListObjects[i];
						row.Cells.Add("Code");
						row.Cells.Add(icd10Obj.Icd10Code);
						row.Cells.Add("ICD10 CM");
						row.Cells.Add(icd10Obj.Description);
						row.Tag=icd10Obj;
						break;
					case "RxNorm":
						RxNorm rxNormObj=(RxNorm)ListObjects[i];
						row.Cells.Add("Code");
						row.Cells.Add(rxNormObj.RxCui);
						row.Cells.Add("RxNorm");
						row.Cells.Add(rxNormObj.Description);
						row.Tag=rxNormObj;
						break;
					//case "LabResult"://Deprecated
						//TODO
						//Loinc loincObj=(Loinc)ListObjects[i];
						//row.Cells.Add("Code");
						//row.Cells.Add(loincObj.LoincCode);
						//row.Cells.Add("LOINC");
						//row.Cells.Add(loincObj.NameShort);
						//row.Tag=loincObj;
						//break;
					case "EhrLabResult":
						EhrLabResult ehrLabResultObj=(EhrLabResult)ListObjects[i];
						row.Cells.Add("Lab Result");
						if(ehrLabResultObj.ObservationIdentifierID!="") {//1st triplet
							row.Cells.Add(ehrLabResultObj.ObservationIdentifierID);
							row.Cells.Add("LOINC");
							row.Cells.Add(ehrLabResultObj.ObservationIdentifierText);
						}
						else if(ehrLabResultObj.ObservationIdentifierIDAlt!="") {//second triplet
							row.Cells.Add(ehrLabResultObj.ObservationIdentifierIDAlt);
							row.Cells.Add("LOINC");
							row.Cells.Add(ehrLabResultObj.ObservationIdentifierTextAlt);
						}
						else {
							row.Cells.Add("");
							row.Cells.Add("UNK");
							row.Cells.Add("Unknown");
						}
						row.Tag=ehrLabResultObj;
						break;
					case "Loinc":
						Loinc loincObj=(Loinc)ListObjects[i];
						row.Cells.Add("Code");
						row.Cells.Add(loincObj.LoincCode);
						row.Cells.Add("LOINC");
						row.Cells.Add(loincObj.NameShort);
						row.Tag=loincObj;
						break;
				}
				gridMain.Rows.Add(row);
			}
			gridMain.EndUpdate();
		}

		private void butPreview_Click(object sender,EventArgs e) {
			if(!isValidHL7DataSet()) {
				return;
			}
			MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste(GenerateKnowledgeRequestNotification());
			msgbox.ShowDialog();
		}

		///<summary>Generates message box with all errors. Returns true if data passes validation or if user decides to "continue anyways".</summary>
		private bool isValidHL7DataSet() {
			string warnings="";//additional data that could be used but is not neccesary.
			string errors="";//additional data that must be present in order to be compliant.
			string message="";
			string bullet="  - ";//should be used at the beggining of every warning/error
			//Patient information-------------------------------------------------------------------------------------------------
			if(PatCur==null) {//should never happen
				warnings+=bullet+Lan.g(this,"No patient selected.")+"\r\n";
			}
			else {
				try {
					PatCur.Birthdate=PIn.Date(textPatBirth.Text);
				}
				catch {

					warnings+=bullet+Lan.g(this,"Birthday.")+"\r\n";
				}
				if(PatCur.Birthdate==DateTime.MinValue) {
					warnings+=bullet+Lan.g(this,"Patient does not have a valid birthday.")+"\r\n";
				}
			}
			//Provider information------------------------------------------------------------------------------------------------
			if(ProvCur==null) {
				warnings+=bullet+Lan.g(this,"No provider selected.")+"\r\n";
			}
			else {
				if(textProvID.Text=="") {
					warnings+=bullet+Lan.g(this,"No povider ID.")+"\r\n";
				}
			}
			//Organization information--------------------------------------------------------------------------------------------
			if(textOrgName.Text=="") {
				warnings+=bullet+Lan.g(this,"No organization name.")+"\r\n";
			}
			if(textOrgID.Text=="") {
				warnings+=bullet+Lan.g(this,"No organization ID.")+"\r\n";
			}
			//Encounter information-----------------------------------------------------------------------------------------------
			if(textEncLocID.Text=="") {
				warnings+=bullet+Lan.g(this,"No encounter location ID.")+"\r\n";
			}
			//Requestor information-----------------------------------------------------------------------------------------------
			if(radioReqPat.Checked && radioRecProv.Checked) {
				warnings+=bullet+Lan.g(this,"It is uncommon for the requestor to be the patient and the recipient to be the provider.")+"\r\n";
			}
			//Recipient information-----------------------------------------------------------------------------------------------
			//Problem, Medication, Lab Result information-------------------------------------------------------------------------
			//switch(""){//tabControl1.SelectedTab.Name) {
			//	case "tabProblem"://------------------------------------------------------------------------------------------------
			//		if(ProblemCur==null) {
			//			errors+=bullet+Lan.g(this,"No problem is selected.")+"\r\n";
			//		}
			//		else {
			//			if(textProbSnomedCode.Text=="") {
			//				errors+=bullet+Lan.g(this,"No SNOMED CT problem code.")+"\r\n";
			//				break;
			//			}
			//			if(textProbSnomedCode.Text!=ProblemCur.SnomedCode) {
			//				warnings+=bullet+Lan.g(this,"SNOMED CT problem code has been manualy altered.")+"\r\n";
			//			}
			//			if(Snomeds.GetByCode(textProbSnomedCode.Text)==null) {
			//				errors+=bullet+Lan.g(this,"SNOMED CT problem code does not exist in database.")+"\r\n";
			//			}
			//		}
			//		break;
			//	case "tabMedication"://---------------------------------------------------------------------------------------------
			//		if(MedicationCur==null) {
			//			errors+=bullet+Lan.g(this,"No medication is selected.")+"\r\n";
			//		}
			//		else {
			//			if(textMedSnomedCode.Text=="") {
			//				errors+=bullet+Lan.g(this,"No SNOMED CT medication code.")+"\r\n";
			//			}
			//			//if(textProbSnomedCode.Text!=MedicationCur.SnomedCode) {
			//			//  warnings+=bullet+Lan.g(this,"SNOMED CT medication code has been manualy altered.")+"\r\n";
			//			//}
			//		}
			//		break;
			//	case "tabLabResult"://----------------------------------------------------------------------------------------------
			//		if(LabCur==null) {
			//			errors+=bullet+Lan.g(this,"No lab result is selected.")+"\r\n";
			//		}
			//		else {
			//			if(textMedSnomedCode.Text=="") {
			//				errors+=bullet+Lan.g(this,"No SNOMED CT lab result code.")+"\r\n";
			//			}
			//			//if(textProbSnomedCode.Text!=LabCur.SnomedCode) {
			//			//  warnings+=bullet+Lan.g(this,"SNOMED CT lab result code has been manualy altered.")+"\r\n";
			//			//}
			//		}
			//		break;
			//	default://----------------------------------------------------------------------------------------------------------
			//		errors+=bullet+Lan.g(this,"Problem, medication, or lab result not selected.")+"\r\n";
			//		break;
			//}
			//Generate messagebox-------------------------------------------------------------------------------------------------
			if(errors!="") {
				message+=Lan.g(this,"The following errors must be corrected in order to comply with HL7 standard:")+"\r\n";
				message+=errors;
				message+="\r\n";
			}
			if(warnings!="") {
				message+=Lan.g(this,"Fixing the following warnings may provide better knowledge request results:")+"\r\n";
				message+=warnings;
				message+="\r\n";
			}
			if(message!="") {
				message+=Lan.g(this,"Would you like to continue anyways?");
				if(MessageBox.Show(message,"",MessageBoxButtons.YesNo)!=DialogResult.Yes) {
					return false;
				}
			}
			return true;
		}

		private string GenerateKnowledgeRequestNotification() {
//			KnowledgeRequestNotification.KnowledgeRequestNotification krn = new KnowledgeRequestNotification.KnowledgeRequestNotification();
//#if DEBUG
//			krn.subject4List.Add(
//				new KnowledgeRequestNotification.Subject3(
//					new KnowledgeRequestNotification.Value("191166001","2.16.840.1.113883.6.96","SNOMEDCT","[X]Megaloblastic anemia NOS (disorder)"
//			)));
//			krn.subject4List[0].mainSearchCriteria.originalText="Anemia";
//			return krn.ToXml();
//#endif
			//old code below this line.
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="  ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter w=XmlWriter.Create(strBuilder,xmlSettings)) {
				w.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				w.WriteWhitespace("\r\n");
				w.WriteStartElement("knowledgeRequestNotification");
					w.WriteAttributeString("classCode","ACT");
					w.WriteAttributeString("moodCode","DEF");
					w.WriteStartElement("id");
						w.WriteAttributeString("value",knowledgeRequestIDHelper());
						w.WriteAttributeString("assigningAuthority",knowledgeRequestIDAAHelper());
					w.WriteEndElement();//id
					w.WriteStartElement("effectiveTime");
						w.WriteAttributeString("value",DateTime.Now.ToString("yyyyMMddhhmmss"));
					w.WriteEndElement();//effectiveTime
					w.WriteStartElement("subject1");
						w.WriteAttributeString("typeCode","SBJ");
						w.WriteStartElement("patientContext");
							w.WriteAttributeString("classCode","PAT");
							w.WriteStartElement("patientPerson");
								w.WriteAttributeString("classCode","PSN");
								w.WriteAttributeString("determinerCode","INSTANCE");
								w.WriteStartElement("administrativeGenderCode");
									w.WriteAttributeString("code",administrativeGenderCodeHelper(PatCur.Gender));
									w.WriteAttributeString("codeSytem","2.16.840.1.113883.5.1");
									w.WriteAttributeString("codeSystemName","administrativeGender");
									w.WriteAttributeString("displayName",administrativeGenderNameHelper(PatCur.Gender));
								w.WriteEndElement();//administrativeGenderCode
							w.WriteEndElement();//patientPerson
						if(PatCur.Birthdate!=DateTime.MinValue){
							w.WriteStartElement("subjectOf");
								w.WriteAttributeString("typeCode","SBJ");
							if(UseAge || UseAge==UseAgeGroup) {//if true or both are false; field is required.
								w.WriteStartElement("age");
									w.WriteAttributeString("classCode","OBS");
									w.WriteAttributeString("moodCode","DEF");
									w.WriteStartElement("code");
										w.WriteAttributeString("code","30525-0");
										w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.1");
										w.WriteAttributeString("codeSystemName","LN");
										w.WriteAttributeString("displayName","AGE");
									w.WriteEndElement();//code
									w.WriteStartElement("value");
										w.WriteAttributeString("value",PatCur.Age.ToString());
										w.WriteAttributeString("unit","a");
									w.WriteEndElement();//value
								w.WriteEndElement();//age
							}
							if(UseAgeGroup || UseAge==UseAgeGroup) {//if true or both are false; field is required.
								w.WriteStartElement("ageGroup");
									w.WriteAttributeString("classCode","OBS");
									w.WriteAttributeString("moodCode","DEF");
									w.WriteStartElement("code");
										w.WriteAttributeString("code","46251-5");
										w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.1");
										w.WriteAttributeString("codeSystemName","LN");
										w.WriteAttributeString("displayName","Age Groups");
									w.WriteEndElement();//code
									w.WriteStartElement("value");
										w.WriteAttributeString("code",AgeGroupCodeHelper(PatCur.Birthdate));
										w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.177");
										w.WriteAttributeString("codeSystemName","MSH");
										w.WriteAttributeString("displayName",AgeGroupNameHelper(PatCur.Birthdate));
									w.WriteEndElement();//value
								w.WriteEndElement();//ageGroup
							}
							w.WriteEndElement();//subjectOf
						}
						w.WriteEndElement();//patientContext
					w.WriteEndElement();//subject1
					w.WriteStartElement("holder");
						w.WriteAttributeString("typeCode","HLD");
						w.WriteStartElement("assignedEntity");
							w.WriteAttributeString("classCode","ASSIGNED");
							w.WriteStartElement("name");
								w.WriteString(Security.CurUser.UserName);
							w.WriteEndElement();//name
							w.WriteStartElement("certificateText");
								w.WriteString(Security.CurUser.Password);
							w.WriteEndElement();//certificateText
							w.WriteStartElement("assignedAuthorizedPerson");
								w.WriteAttributeString("classCode","PSN");
								w.WriteAttributeString("determinerCode","INSTANCE");
							if(textProvID.Text!=""){
								w.WriteStartElement("id");
									w.WriteAttributeString("value",textProvID.Text);
								w.WriteEndElement();//id
								}
							w.WriteEndElement();//assignedAuthorizedPerson
						if(textOrgID.Text!="" && textOrgName.Text!=""){
							w.WriteStartElement("representedOrganization");
								w.WriteAttributeString("classCode","ORG");
								w.WriteAttributeString("determinerCode","INSTANCE");
							if(textOrgID.Text!=""){
								w.WriteStartElement("id");
									w.WriteAttributeString("value",textOrgID.Text);
								w.WriteEndElement();//id
							}
							if(textOrgName.Text!=""){
								w.WriteStartElement("name");
									w.WriteAttributeString("value",textOrgName.Text);
								w.WriteEndElement();//name
							}
							w.WriteEndElement();//representedOrganization
						}
						w.WriteEndElement();//assignedEntity
					w.WriteEndElement();//holder
				//Performer (Requester)--------------------------------------------------------------------------------------------------------------------------
					w.WriteStartElement("performer");
						w.WriteAttributeString("typeCode","PRF");						
					if(radioReqProv.Checked) {//----performer choice-----
						w.WriteStartElement("healthCareProvider");
							w.WriteAttributeString("classCode","PROV");
							w.WriteStartElement("code");
								w.WriteAttributeString("code","120000000X");
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.101");
								w.WriteAttributeString("codeSystemName","NUCC Health Care Provider Taxonomy");
								w.WriteAttributeString("displayName","Dental Providers");
							w.WriteEndElement();//code
						if((comboProvLang.Text!="" && radioReqProv.Checked) 
							|| (comboPatLang.Text=="" && radioReqPat.Checked))
						{//A missing languageCommunication field invalidates the entire Person class.
							w.WriteStartElement("healthCarePerson");
								w.WriteAttributeString("classCode","PSN");
								w.WriteAttributeString("determinerCode","INSTANCE");
								w.WriteStartElement("languageCommunication");
									w.WriteStartElement("languageCommunicationCode");
										w.WriteAttributeString("code",arrayCultures[comboProvLang.SelectedIndex].ThreeLetterISOLanguageName);
										w.WriteAttributeString("codeSytem","1.0.639.2");
										w.WriteAttributeString("codeSystemName","ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
										w.WriteAttributeString("displayName",arrayCultures[comboProvLang.SelectedIndex].DisplayName);
									w.WriteEndElement();//languageCommunicationCode
								w.WriteEndElement();//languageCommunication
							w.WriteEndElement();//healthCarePerson
							}//end if no language selected.
						w.WriteEndElement();//healthCareProvider
					}
					else {//Performer is patient.
						w.WriteStartElement("patient");
							w.WriteAttributeString("classCode","PAT");
						if((comboProvLang.Text!="" && radioRecProv.Checked) 
							|| (comboPatLang.Text=="" && radioRecPat.Checked))
						{//A missing languageCommunication field invalidates the entire Person class.
							w.WriteStartElement("patientPerson");
								w.WriteAttributeString("classCode","PSN");
								w.WriteAttributeString("determinerCode","INSTANCE");
								w.WriteStartElement("languageCommunication");
									w.WriteStartElement("languageCommunicationCode");
										w.WriteAttributeString("code",arrayCultures[comboPatLang.SelectedIndex].ThreeLetterISOLanguageName);
										w.WriteAttributeString("codeSytem","1.0.639.2");
										w.WriteAttributeString("codeSystemName","ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
										w.WriteAttributeString("displayName",arrayCultures[comboPatLang.SelectedIndex].DisplayName);
									w.WriteEndElement();//languageCommunicationCode
								w.WriteEndElement();//languageCommunication
							w.WriteEndElement();//patientPerson
							}//end if no language selected.
						w.WriteEndElement();//patient
					}
					w.WriteEndElement();//performer
				//InformationRecipient--------------------------------------------------------------------------------------------------------------------------
					w.WriteStartElement("informationRecipient");	
					if(radioRecProv.Checked) {//----performer choice-----
						w.WriteStartElement("healthCareProvider");
							w.WriteAttributeString("classCode","PROV");
							w.WriteStartElement("code");
								w.WriteAttributeString("code","120000000X");
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.101");
								w.WriteAttributeString("codeSystemName","NUCC Health Care Provider Taxonomy");
								w.WriteAttributeString("displayName","Dental Providers");
							w.WriteEndElement();//code
							w.WriteStartElement("healthCarePerson");
								w.WriteAttributeString("classCode","PSN");
								w.WriteAttributeString("determinerCode","INSTANCE");
								w.WriteStartElement("languageCommunication");
									w.WriteStartElement("languageCommunicationCode");
										w.WriteAttributeString("code",arrayCultures[comboProvLang.SelectedIndex].ThreeLetterISOLanguageName);
										w.WriteAttributeString("codeSytem","1.0.639.2");
										w.WriteAttributeString("codeSystemName","ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
										w.WriteAttributeString("displayName",arrayCultures[comboProvLang.SelectedIndex].DisplayName);
									w.WriteEndElement();//languageCommunicationCode
								w.WriteEndElement();//languageCommunication
							w.WriteEndElement();//healthCarePerson
						w.WriteEndElement();//healthCareProvider
					}
					else {//Performer is patient.
						w.WriteStartElement("patient");
							w.WriteAttributeString("classCode","PAT");
							w.WriteStartElement("patientPerson");
								w.WriteAttributeString("classCode","PSN");
								w.WriteAttributeString("determinerCode","INSTANCE");
								w.WriteStartElement("languageCommunication");
									w.WriteStartElement("languageCommunicationCode");
										w.WriteAttributeString("code",arrayCultures[comboPatLang.SelectedIndex].ThreeLetterISOLanguageName);
										w.WriteAttributeString("codeSytem","1.0.639.2");
										w.WriteAttributeString("codeSystemName","ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
										w.WriteAttributeString("displayName",arrayCultures[comboPatLang.SelectedIndex].DisplayName);
									w.WriteEndElement();//languageCommunicationCode
								w.WriteEndElement();//languageCommunication
							w.WriteEndElement();//patientPerson
						w.WriteEndElement();//patient
					}
					w.WriteEndElement();//informationRecipient
					w.WriteStartElement("subject2");
						w.WriteAttributeString("typeCode","SUBJ");
						w.WriteStartElement("taskContext");
							w.WriteAttributeString("classCode","ACT");
							w.WriteAttributeString("moodCode","DEF");
							w.WriteStartElement("code");
								w.WriteAttributeString("code",ActTaskCodeHelper());
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.5.4");
								w.WriteAttributeString("codeSystemName","ActCode");
								w.WriteAttributeString("displayName",ActTaskCodeNameHelper());
							w.WriteEndElement();//code
						w.WriteEndElement();//taskContext
					w.WriteEndElement();//subject2
					w.WriteStartElement("subject3");
						w.WriteAttributeString("typeCode","SUBJ");
						w.WriteStartElement("subTopic");
							w.WriteAttributeString("classCode","OBS");
							w.WriteAttributeString("moodCode","DEF");
							w.WriteStartElement("code");
								w.WriteAttributeString("code","KSUBT");
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.5.4");
								w.WriteAttributeString("codeSystemName","ActCode");
								w.WriteAttributeString("displayName","knowledge subtopic");
							w.WriteEndElement();//code
							w.WriteStartElement("value");
								w.WriteAttributeString("code","TODO");
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.177");
								w.WriteAttributeString("codeSystemName","MSH");
								w.WriteAttributeString("displayName","TODO");
							w.WriteEndElement();//value
						w.WriteEndElement();//subTopic
					w.WriteEndElement();//subject3
					w.WriteStartElement("subject4");
						w.WriteAttributeString("typeCode","SUBJ");
						w.WriteStartElement("mainSearchCriteria");
							w.WriteAttributeString("classCode","OBS");
							w.WriteAttributeString("moodCode","DEF");
							w.WriteStartElement("code");
								w.WriteAttributeString("code","KSUBJ");
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.5.4");
								w.WriteAttributeString("codeSystemName","ActCode");
								w.WriteAttributeString("displayName","knowledge subject");
							w.WriteEndElement();//code
							w.WriteStartElement("value");
						//switch(tabControl1.SelectedTab.Name) {
						//	case "tabProblem"://------------------------------------------------------------------------------------------------
						//		w.WriteAttributeString("code","TODO:SNOMED CT Problem Code.");
						//		w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.96");//HL7 OID for SNOMED Clinical Terms
						//		w.WriteAttributeString("codeSystemName","snomed-CT");//HL7 name for SNOMED Clinical Terms
						//		w.WriteAttributeString("displayName","TODO:SNOMED CT Problem Name");
						//		break;
						//	case "tabMedication"://---------------------------------------------------------------------------------------------
						//		w.WriteAttributeString("code","TODO:SNOMED CT Medication Code.");
						//		w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.96");//HL7 OID for SNOMED Clinical Terms
						//		w.WriteAttributeString("codeSystemName","snomed-CT");//HL7 name for SNOMED Clinical Terms
						//		w.WriteAttributeString("displayName","TODO: SNOMED CT Medication Name.");
						//		break;
						//	case "tabLabResult"://----------------------------------------------------------------------------------------------
						//		w.WriteAttributeString("code","TODO: SNOMED CT Lab Results Code??");
						//		w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.96");//HL7 OID for SNOMED Clinical Terms
						//		w.WriteAttributeString("codeSystemName","snomed-CT");//HL7 name for SNOMED Clinical Terms
						//		w.WriteAttributeString("displayName","TODO: SNOMED CT Lab Results Name??");
						//		break;
						//	default://----------------------------------------------------------------------------------------------------------
						//		//either no tab is selected or the tab names above are misspelled.
						//		//w.WriteAttributeString("code","TODO: ");
						//		//w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.96");//HL7 OID for SNOMED Clinical Terms
						//		//w.WriteAttributeString("codeSystemName","snomed-CT");//HL7 name for SNOMED Clinical Terms
						//		//w.WriteAttributeString("displayName","TODO: ");
						//		break;
						//}
							w.WriteEndElement();//value
						//if(tabControl1.SelectedTab.Name=="tabLabResult"){
						//	w.WriteStartElement("subject");
						//		w.WriteAttributeString("typeCode","SUBJ");
						//		w.WriteStartElement("severityObservation");
						//			w.WriteAttributeString("classCode","OBS");
						//			w.WriteAttributeString("moodCode","DEF");
						//			w.WriteStartElement("code");
						//				w.WriteAttributeString("code","SEV");
						//				w.WriteAttributeString("codeSytem","2.16.840.1.113883.5.4");
						//				w.WriteAttributeString("codeSystemName","ActCode");
						//				w.WriteAttributeString("displayName","Severity Observation");
						//			w.WriteEndElement();//code
						//			w.WriteStartElement("interpretationCode");
						//				w.WriteAttributeString("code",ObservationInterpretationCodeHelper(ObsInterpretation));
						//				w.WriteAttributeString("codeSytem","");
						//				w.WriteAttributeString("codeSystemName","");
						//				w.WriteAttributeString("displayName",ObservationInterpretationNameHelper(ObsInterpretation));
						//			w.WriteEndElement();//value
						//		w.WriteEndElement();//severityObservation
						//	w.WriteEndElement();//subject
						//}
						w.WriteEndElement();//mainSearchCriteria
					w.WriteEndElement();//subject4
					w.WriteStartElement("componentOf");
						w.WriteAttributeString("typeCode","COMP");
						w.WriteStartElement("encounter");
							w.WriteAttributeString("classCode","ENC");
							w.WriteAttributeString("moodCode","DEF");
							w.WriteStartElement("code");
								w.WriteAttributeString("code",EncounterCodeHelper(ActEC));
								w.WriteAttributeString("codeSytem","2.16.840.1.113883.5.4");
								w.WriteAttributeString("codeSystemName","ActCode");
								w.WriteAttributeString("displayName",EncounterCode(ActEC));
							w.WriteEndElement();//code
						if(textEncLocID.Text!=""){
							w.WriteStartElement("location");
							w.WriteAttributeString("typeCode","LOC");
								w.WriteStartElement("serviceDeliveryLocation");
									w.WriteAttributeString("typeCode","SDLOC");
									w.WriteAttributeString("id",textEncLocID.Text);
								w.WriteEndElement();//serviceDeliveryLocation
							w.WriteEndElement();//location
							}
						w.WriteEndElement();//encounter
					w.WriteEndElement();//componentOf
				w.WriteEndElement();//knowledgeRequestNotification
			}
			return strBuilder.ToString();
		}

		#region helper Functions Start

		private string knowledgeRequestIDAAHelper() {
			if(PrefC.GetString(PrefName.PracticeTitle)!="") {
				return PrefC.GetString(PrefName.PracticeTitle);
			}
			return "Open Dental Software, version"+PrefC.GetString(PrefName.ProgramVersion);
		}

		private string knowledgeRequestIDHelper() {
			if(PatCur!=null) {
				return "PT"+PatCur.PatNum+DateTime.Now.ToUniversalTime().ToString("yyyyMMddhhmmss");
			}
			else if(ProvCur!=null) {
				return "PV"+ProvCur.ProvNum+DateTime.Now.ToUniversalTime().ToString("yyyyMMddhhmmss");
			}
			else {
				return "OD"+DateTime.Now.ToUniversalTime().ToString("yyyyMMddhhmmss");
			}
		}

		private string EncounterCodeHelper(ActEncounterCode aec) {
			switch(aec) {
				case ActEncounterCode.AMB:
					return "AMB";
				case ActEncounterCode.EMER:
					return "EMER";
				case ActEncounterCode.FLD:
					return "FLD";
				case ActEncounterCode.HH:
					return "HH";
				case ActEncounterCode.IMP:
					return "IMP";
				case ActEncounterCode.SS:
					return "SS";
				case ActEncounterCode.VR:
					return "VR";
				default:
					return "";
			}
		}

		private string EncounterCode(ActEncounterCode aec) {
			switch(aec) {
				case ActEncounterCode.AMB:
					return "ambulatory";
				case ActEncounterCode.EMER:
					return "emergency";
				case ActEncounterCode.FLD:
					return "field";
				case ActEncounterCode.HH:
					return "home health";
				case ActEncounterCode.IMP:
					return "inpatient encounter";
				case ActEncounterCode.SS:
					return "short stay";
				case ActEncounterCode.VR:
					return "virtual";
				default:
					return "";
			}
		}

		public string ObservationInterpretationCodeHelper(ObservationInterpretationNormality oin) {
			switch(oin) {
				case ObservationInterpretationNormality.A:
					return "A";
				case ObservationInterpretationNormality.AA:
					return "AA";
				case ObservationInterpretationNormality.HH:
					return "HH";
				case ObservationInterpretationNormality.LL:
					return "LL";
				case ObservationInterpretationNormality.H:
					return "H";
				case ObservationInterpretationNormality.L:
					return "L";
				case ObservationInterpretationNormality.N:
					return "N";
				default:
					return "";
			}
		}

		public string ObservationInterpretationNameHelper(ObservationInterpretationNormality oin) {
			switch(oin) {
				case ObservationInterpretationNormality.A:
					return "Abnormal";
				case ObservationInterpretationNormality.AA:
					return "Abnormal alert";
				case ObservationInterpretationNormality.HH:
					return "High alert";
				case ObservationInterpretationNormality.LL:
					return "Low alert";
				case ObservationInterpretationNormality.H:
					return "High";
				case ObservationInterpretationNormality.L:
					return "Low";
				case ObservationInterpretationNormality.N:
					return "Normal";
				default:
					return "";
			}
		}

		/// <summary>Returns thefirst level of ActTaskCode. OE, PATDOC, or PATINFO there are 35 total ActTaskCodes available.</summary>
		public string ActTaskCodeHelper() {
			switch(ActTC) {
				case ActTaskCode.OE:
					return "OE";
				case ActTaskCode.PATDOC:
					return "PATDOC";
				case ActTaskCode.PATINFO:
					return "PATINFO";
				default:
					throw new NotImplementedException();
			}
		}

		/// <summary>Returns thefirst level of ActTaskCode. OE, PATDOC, or PATINFO there are 35 total ActTaskCodes available.</summary>
		public string ActTaskCodeNameHelper() {
			switch(ActTC) {
				case ActTaskCode.OE:
					return "order entry task";
				case ActTaskCode.PATDOC:
					return "patient documentation task";
				case ActTaskCode.PATINFO:
					return "patient information review task";
				default:
					throw new NotImplementedException();
			}
		}

		///<summary>Returns MeSH age group code based on birthdate. i.e. &lt;2yrs==Infant==D007231</summary>
		public string AgeGroupCodeHelper(DateTime dateTime) {
			#region MeSH (Medical Subject Headers) codes used for age groups.
			//*NEWRECORD
			//RECTYPE = D
			//MH = Infant, NewbornGM = birth to 1 month age group
			//MS = An infant during the first month after birth.
			//UI = D007231
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Infant
			//GM = 1 month to 2 year age group; + includes birth to 2 years; for birth to 1 month, use Infant, Newborn +
			//MS = A child between 1 and 23 months of age.
			//UI = D007223
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Child, Preschool
			//GM = 2-5 age group; for 1 month to 2 years use Infant +
			//MS = A child between the ages of 2 and 5.
			//UI = D002675
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Child
			//MH = ChildGM = 6-12 age group; for 2-5 use Child, Preschool; + includes birth to 18 year age group
			//MS = A person 6 to 12 years of age. An individual 2 to 5 years old is CHILD, PRESCHOOL.
			//UI = D002648
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Adolescent
			//AN = age 13-18 yr; IM as psychol & sociol entity; check tag ADOLESCENT for NIM; Manual 18.5.12, 34.9.5
			//MS = A person 13 to 18 years of age.
			//UI = D000293
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Adult
			//GM = 19-44 age group; older than 44, use Middle Age, Aged +, or + for all
			//MS = A person having attained full growth or maturity. Adults are of 19 through 44 years of age. For a person between 19 and 24 years of age, YOUNG ADULT is available.
			//UI = D000328
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Middle Aged
			//AN = age 45-64; IM as psychol, sociol entity: Manual 18.5.12; NIM as check tag; Manual 34.10 for indexing examples
			//UI = D008875
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Aged
			//GM = 65 and older; consider also Aged, 80 and over
			//MS = A person 65 through 79 years of age. For a person older than 79 years, AGED, 80 AND OVER is available.
			//UI = D000368
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Aged, 80 and over
			//GM = consider also Aged + (65 and older)
			//MS = A person 80 years of age and older.
			//UI = D000369
			#endregion
			if(PatCur.Birthdate.AddMonths(1)>DateTime.Now) {//less than 1mo old, newborn
				return "D007231";
			}
			else if(PatCur.Birthdate.AddYears(2)>DateTime.Now) {//less than 2 yrs old, Infant
				return "D007223";
			}
			else if(PatCur.Birthdate.AddYears(5)>DateTime.Now) {//2 to 5 yrs old, Preschool
				return "D007675";
			}
			else if(PatCur.Birthdate.AddYears(12)>DateTime.Now) {//6 to 12 yrs old, Child
				return "D002648";
			}
			else if(PatCur.Birthdate.AddYears(18)>DateTime.Now) {//13 to 18 yrs old, Adolescent
				return "D000293";
			}
			else if(PatCur.Birthdate.AddYears(44)>DateTime.Now) {//19 to 44 yrs old, Adult
				return "D000328";
			}
			else if(PatCur.Birthdate.AddYears(64)>DateTime.Now) {//45 to 64 yrs old, Middle Aged
				return "D008875";
			}
			else if(PatCur.Birthdate.AddYears(79)>DateTime.Now) {//65 to 79 yrs old, Aged
				return "D000368";
			}
			else { //if(PatCur.Birthdate.AddYears(79)>DateTime.Now) {//80 yrs old or older, Aged, 80 and over
				return "D000369";
			}
		}

		///<summary>Returns MeSH age group name based on birthdate. i.e. &lt;2yrs==Infant.</summary>
		public string AgeGroupNameHelper(DateTime dateTime) {
			#region MeSH (Medical Subject Headers) codes used for age groups.
			//*NEWRECORD
			//RECTYPE = D
			//MH = Infant, NewbornGM = birth to 1 month age group
			//MS = An infant during the first month after birth.
			//UI = D007231
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Infant
			//GM = 1 month to 2 year age group; + includes birth to 2 years; for birth to 1 month, use Infant, Newborn +
			//MS = A child between 1 and 23 months of age.
			//UI = D007223
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Child, Preschool
			//GM = 2-5 age group; for 1 month to 2 years use Infant +
			//MS = A child between the ages of 2 and 5.
			//UI = D002675
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Child
			//MH = ChildGM = 6-12 age group; for 2-5 use Child, Preschool; + includes birth to 18 year age group
			//MS = A person 6 to 12 years of age. An individual 2 to 5 years old is CHILD, PRESCHOOL.
			//UI = D002648
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Adolescent
			//AN = age 13-18 yr; IM as psychol & sociol entity; check tag ADOLESCENT for NIM; Manual 18.5.12, 34.9.5
			//MS = A person 13 to 18 years of age.
			//UI = D000293
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Adult
			//GM = 19-44 age group; older than 44, use Middle Age, Aged +, or + for all
			//MS = A person having attained full growth or maturity. Adults are of 19 through 44 years of age. For a person between 19 and 24 years of age, YOUNG ADULT is available.
			//UI = D000328
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Middle Aged
			//AN = age 45-64; IM as psychol, sociol entity: Manual 18.5.12; NIM as check tag; Manual 34.10 for indexing examples
			//UI = D008875
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Aged
			//GM = 65 and older; consider also Aged, 80 and over
			//MS = A person 65 through 79 years of age. For a person older than 79 years, AGED, 80 AND OVER is available.
			//UI = D000368
			//
			//*NEWRECORD
			//RECTYPE = D
			//MH = Aged, 80 and over
			//GM = consider also Aged + (65 and older)
			//MS = A person 80 years of age and older.
			//UI = D000369
			#endregion
			if(PatCur.Birthdate.AddMonths(1)>DateTime.Now) {//less than 1mo old, newborn
				return "Newborn";
			}
			else if(PatCur.Birthdate.AddYears(2)>DateTime.Now) {//less than 2 yrs old, Infant
				return "Infant";
			}
			else if(PatCur.Birthdate.AddYears(5)>DateTime.Now) {//2 to 5 yrs old, Preschool
				return "Preschool";
			}
			else if(PatCur.Birthdate.AddYears(12)>DateTime.Now) {//6 to 12 yrs old, Child
				return "Child";
			}
			else if(PatCur.Birthdate.AddYears(18)>DateTime.Now) {//13 to 18 yrs old, Adolescent
				return "Adolescent";
			}
			else if(PatCur.Birthdate.AddYears(44)>DateTime.Now) {//19 to 44 yrs old, Adult
				return "Adult";
			}
			else if(PatCur.Birthdate.AddYears(64)>DateTime.Now) {//45 to 64 yrs old, Middle Aged
				return "Middle Aged";
			}
			else if(PatCur.Birthdate.AddYears(79)>DateTime.Now) {//65 to 79 yrs old, Aged
				return "Aged";
			}
			else { //if(PatCur.Birthdate.AddYears(79)>DateTime.Now) {//80 yrs old or older, Aged, 80 and over
				return "Aged, 80 and over";
			}
		}

		///<summary>The gender of a person used for adminstrative purposes (as opposed to clinical gender). Empty string/value is allowed.</summary>
		public string administrativeGenderCodeHelper(PatientGender patientGender) {
			switch(patientGender) {
				case PatientGender.Female:
					return "F";
				case PatientGender.Male:
					return "M";
				case PatientGender.Unknown:
					return "UN";
				default://should never happen
					return " ";
			}
		} 

		///<summary>The gender of a person used for adminstrative purposes (as opposed to clinical gender). Empty string/value is allowed.</summary>
		public string administrativeGenderNameHelper(PatientGender patientGender) {
			switch(patientGender) {
				case PatientGender.Female:
					return "Female";
				case PatientGender.Male:
					return "Male";
				case PatientGender.Unknown:
					return "Undifferentiated";
				default://should never happen
					return "";
			}
		}

		#endregion

		private void comboEncType_SelectedIndexChanged(object sender,EventArgs e) {
			ActEC=(ActEncounterCode)comboEncType.SelectedIndex;
		}

		private void comboTask_SelectedIndexChanged(object sender,EventArgs e) {
			ActTC=(ActTaskCode)comboTask.SelectedIndex;
		}

		private void butProbPick_Click(object sender,EventArgs e) {
			FormDiseaseDefs FormDD = new FormDiseaseDefs();
			FormDD.IsSelectionMode=true;
			FormDD.ShowDialog();
			if(FormDD.DialogResult!=DialogResult.OK) {
				return;
			}
			//ProblemCur=DiseaseDefs.GetItem(FormDD.SelectedDiseaseDefNum);
			//fillProblem();
		}

		private void butPreviewRequest_Click(object sender,EventArgs e) {
			KnowledgeRequestNotification.KnowledgeRequestNotification krn;
			for(int i=0;i<gridMain.Rows.Count;i++){
				if(gridMain.Rows[i].Tag==null){
					MsgBox.Show(this,"Cannot search without a valid code.");
					continue;
				}
				krn = new KnowledgeRequestNotification.KnowledgeRequestNotification();
				krn.AddObject(gridMain.Rows[i].Tag);
				MsgBoxCopyPaste msgbox=new MsgBoxCopyPaste("http://apps.nlm.nih.gov/medlineplus/services/mpconnect.cfm?"+krn.ToUrl());
				msgbox.ShowDialog();
				msgbox=new MsgBoxCopyPaste("http://apps2.nlm.nih.gov/medlineplus/services/servicedemo.cfm?"+krn.ToUrl());
				msgbox.ShowDialog();
			}//end gridMain.Rows

		}

		private void butSend_Click(object sender,EventArgs e) {
			KnowledgeRequestNotification.KnowledgeRequestNotification krn;
			for(int i=0;i<gridMain.Rows.Count;i++) {
				if(gridMain.Rows[i].Tag==null) {
					MsgBox.Show(this,"Cannot search without a valid code.");
					continue;
				}
				krn = new KnowledgeRequestNotification.KnowledgeRequestNotification();
				krn.AddObject(gridMain.Rows[i].Tag);
				krn.performerIsPatient=radioReqPat.Checked;
				krn.recipientIsPatient=radioRecPat.Checked;
				try {
					System.Diagnostics.Process.Start("http://apps.nlm.nih.gov/medlineplus/services/mpconnect.cfm?"+krn.ToUrl());
					System.Diagnostics.Process.Start("http://apps2.nlm.nih.gov/medlineplus/services/servicedemo.cfm?"+krn.ToUrl());
				}
				catch(Exception ex) { }
			}//end gridMain.Rows

		}

		private void butCancel_Click(object sender,EventArgs e) {
			DialogResult=DialogResult.Cancel;
		}

		private void groupBoxContext_Enter(object sender,EventArgs e) {

		}

	}

	///<summary>Only enumerating the highest level task codes, OE, PATDOC, and PATINFO., Enum generated from HL7 ActTaskCode [2.16.840.1.113883.1.11.19846] which is a subset of ActCode [OID=2.16.840.1.113883.5.4] documentation published 20120831 10:21 AM.</summary>
	public enum ActTaskCode {
		///<summary>0 - order entry task</summary>
		OE,
		/////<summary>1 - laboratory test order entry task</summary>
		//LABOE,
		/////<summary>2 - medication order entry task</summary>
		//MEDOE,
		///<summary>1 - patient documentation task</summary>
		PATDOC,
		/////<summary>4 - allergy list review</summary>
		//ALLERLREV,
		/////<summary>5 - clinical note entry task</summary>
		//CLINNOTEE,
		/////<summary>6 - diagnosis list entry task</summary>
		//DIAGLISTE,
		/////<summary>7 - discharge summary entry task</summary>
		//DISCHSUME,
		/////<summary>8 - pathology report entry task</summary>
		//PATREPE,
		/////<summary>9 - problem list entry task</summary>
		//PROBLISTE,
		/////<summary>10 - radiology report entry task</summary>
		//RADREPE,
		/////<summary>11 - immunization list review</summary>
		//IMMLREV,
		/////<summary>12 - reminder list review</summary>
		//REMLREV,
		/////<summary>13 - wellness reminder list review</summary>
		//WELLREMLREV,
		///<summary>2 - patient information review task</summary>
		PATINFO
		/////<summary>15 - allergy list entry</summary>
		//ALLERLE,
		/////<summary>16 - clinical note review task</summary>
		//CLINNOTEREV,
		/////<summary>17 - discharge summary review task</summary>
		//DISCHSUMREV,
		/////<summary>18 - diagnosis list review task</summary>
		//DIAGLISTREV,
		/////<summary>19 - immunization list entry</summary>
		//IMMLE,
		/////<summary>20 - laboratory results review task</summary>
		//LABRREV,
		/////<summary>21 - microbiology results review task</summary>
		//MICRORREV,
		/////<summary>22 - microbiology organisms results review task</summary>
		//MICROORGRREV,
		/////<summary>23 - microbiology sensitivity test results review task</summary>
		//MICROSENSRREV,
		/////<summary>24 - medication list review task</summary>
		//MLREV,
		/////<summary>25 - medication administration record work list review task</summary>
		//MARWLREV,
		/////<summary>26 - orders review task</summary>
		//OREV,
		/////<summary>27 - pathology report review task</summary>
		//PATREPREV,
		/////<summary>28 - problem list review task</summary>
		//PROBLISTREV,
		/////<summary>29 - radiology report review task</summary>
		//RADREPREV,
		/////<summary>30 - reminder list entry</summary>
		//REMLE,
		/////<summary>31 - wellness reminder list entry</summary>
		//WELLREMLE,
		/////<summary>32 - risk assessment instrument task</summary>
		//RISKASSESS,
		/////<summary>33 - falls risk assessment instrument task</summary>
		//FALLRISK
	}

	///<summary>Enum generated from HL7 ActEncounterCode [2.16.840.1.113883.1.11.13955] which is a subset of ActCode [OID=2.16.840.1.113883.5.4] documentation published 20120831 10:21 AM.</summary>
	public enum ActEncounterCode {
		///<summary>0 - ambulatory</summary>
		AMB,
		///<summary>1 - emergency</summary>
		EMER,
		///<summary>2 - field</summary>
		FLD,
		///<summary>3 - home health</summary>
		HH,
		///<summary>4 - inpatient encounter</summary>
		IMP,
		///<summary>5 - short stay</summary>
		SS,
		///<summary>6 - virtual</summary>
		VR
	}

	///<summary>Normality, Abnormality, Alert. Concepts in this category are mutually exclusive, i.e., at most one is allowed. Enum generated from HL7 _ObservationInterpretationNormality [2.16.840.1.113883.1.11.10206] which is a subset of ObservationInterpretation [OID=2.16.840.1.113883.5.83] documentation published 20120831 10:21 AM.</summary>
	public enum ObservationInterpretationNormality {
		///<summary>0 - Abnormal - Abnormal (for nominal observations, all service types) </summary>
		A,
		///<summary>1 - Abnormal alert - Abnormal alert (for nominal observations and all service types) </summary>
		AA,
		///<summary>2 - High alert - Above upper alert threshold (for quantitative observations) </summary>
		HH,
		///<summary>3 - Low alert - Below lower alert threshold (for quantitative observations) </summary>
		LL,
		///<summary>4 - High - Above high normal (for quantitative observations) </summary>
		H,
		///<summary>5 - Low - Below low normal (for quantitative observations) </summary>
		L,
		///<summary>6 - Normal - Normal (for all service types) </summary>
		N 
	}

}

namespace KnowledgeRequestNotification {

	///<summary>This class represents the root of the KnowledgeRequestNotificatio.</summary>
	public class KnowledgeRequestNotification {
		///<summary>Classification code.  Static field "ACT".  A record of something that is being done, has been done, can be done, or is intended or requested to be done.  Cardinality [1..1]</summary>
		public static string classCode="ACT";		//1..1
		///<summary>Static field "DEF".  A definition of a service (master).  Cardinality [1..1]</summary>
		public static string moodCode="DEF";						//1..1
		///<summary>List of globally unique identifiers of this knowledge request.  Cardinality [0..*]</summary>
		public List<Id> IdList=new List<Id>();	//0..*
		///<summary>Creation time of the knowledge request.  Must be formatted "yyyyMMddhhmmss" when used.  Cardinality [0..1]</summary>
		public DateTime effectiveTime;					//0..1 "yyyyMMddhhmmss"
		///<summary>Patient context information. Cardinality [0..1]</summary>
		public Subject subject1;								//0..1
		///<summary>Not fully implemented. Implemented enough to work.</summary>
		public bool performerIsPatient;
		///<summary>Not fully implemented. Implemented enough to work.</summary>
		public bool recipientIsPatient;

		//public Performer performer;

		//public InformationRecipient informationRecipient;


		///<summary>Task context information. Cardinality [0..1]</summary>
		public Subject1 subject2;								//0..1
		///<summary>Sub-topic information. Cardinality [0..1]</summary>
		public Subject2 subject3;								//0..1
		///<summary>Conatins a list of MainSearchCriteria: represents the main subject of interest in a knowledge request (e.g., a medication, a lab test result, a disease in the patient's problem list). When multiple multiple search criteria are present, knowledge resources MAY determine whether to join the multiple instances using the AND vs. OR Boolean operator. Cardinality[1..*]</summary>
		public List<Subject3> subject4List;			//1..*
		///<summary>Contains encounter information, type and location.  Cardinality[0..1]</summary>
		public Component1 componentOf;					//0..1

		public KnowledgeRequestNotification() {
			classCode="ACT";
			moodCode="DEF";
			IdList=new List<Id>();
			effectiveTime=DateTime.Now;
			subject1=new Subject();
			subject2=new Subject1();
			subject3=new Subject2();
			subject4List=new List<Subject3>();
			componentOf=new Component1();
		}

		public void AddObject(object obj) {
			switch(obj.GetType().Name) {
				case "Snomed":
					AddCode((Snomed)obj);
					break;
				case "ICD9":
					AddCode((ICD9)obj);
					break;
				case "Icd10":
					AddCode((Icd10)obj);
					break;
				case "RxNorm":
					AddCode((RxNorm)obj);
					break;
				case "Loinc":
					AddCode((Loinc)obj);
					break;
				//case "LabResult"://Deprecated
				//	AddLabResult((LabResult)obj);
				//	break;
				case "EhrLabResult":
					AddLabResult((EhrLabResult)obj);
					break;
			}
		}

		public void AddCode(Snomed snomed) {
			subject4List.Add(new Subject3(new Value(snomed.SnomedCode,"2.16.840.1.113883.6.96","SNOMEDCT",snomed.Description)));
			subject4List[subject4List.Count-1].mainSearchCriteria.originalText=snomed.Description;
		}

		public void AddCode(ICD9 icd9) {
			subject4List.Add(new Subject3(new Value(icd9.ICD9Code,"2.16.840.1.113883.6.103","ICD9CM",icd9.Description)));
			subject4List[subject4List.Count-1].mainSearchCriteria.originalText=icd9.Description;
		}

		public void AddCode(Icd10 icd10) {
			subject4List.Add(new Subject3(new Value(icd10.Icd10Code,"2.16.840.1.113883.6.90","ICD10CM",icd10.Description)));
			subject4List[subject4List.Count-1].mainSearchCriteria.originalText=icd10.Description;
		}

		public void AddCode(RxNorm rxNorm) {
			subject4List.Add(new Subject3(new Value(rxNorm.RxCui,"2.16.840.1.113883.6.88","RxNorm",rxNorm.Description)));
			subject4List[subject4List.Count-1].mainSearchCriteria.originalText=rxNorm.Description;
		}

		public void AddCode(Loinc loinc) {
			//TODO: lab values? no, add LabResult Instead
			subject4List.Add(new Subject3(new Value(loinc.LoincCode,"2.16.840.1.113883.6.1","LOINC",loinc.NameShort)));
			subject4List[subject4List.Count-1].mainSearchCriteria.originalText=loinc.NameShort;
		}

		//public void AddLabResult(LabResult labResult) {
		//	if(labResult.TestID==null || labResult.TestID=="") {
		//		return;
		//	}
		//	Loinc loinc=Loincs.GetByCode(labResult.TestID);
		//	subject4List.Add(new Subject3(new Value(loinc.LoincCode,"2.16.840.1.113883.6.1","LOINC",loinc.NameShort)));
		//	subject4List[subject4List.Count-1].mainSearchCriteria.originalText=loinc.NameShort;
		//	subject4List[subject4List.Count-1].mainSearchCriteria.subject.severityObservation.observationInterpretationNormality.SetInterpretation(labResult.AbnormalFlag);
		//}

		public void AddLabResult(EhrLabResult ehrLabResult) {
			if(ehrLabResult.ObservationIdentifierID!="") {//1st triplet
				subject4List.Add(new Subject3(new Value(ehrLabResult.ObservationIdentifierID,"2.16.840.1.113883.6.1","LOINC",ehrLabResult.ObservationIdentifierText)));
				subject4List[subject4List.Count-1].mainSearchCriteria.originalText=ehrLabResult.ObservationIdentifierTextOriginal;
			}
			if(ehrLabResult.ObservationIdentifierIDAlt!="") {//2nd triplet
				subject4List.Add(new Subject3(new Value(ehrLabResult.ObservationIdentifierIDAlt,"2.16.840.1.113883.6.1","LOINC",ehrLabResult.ObservationIdentifierTextAlt)));
				subject4List[subject4List.Count-1].mainSearchCriteria.originalText=ehrLabResult.ObservationIdentifierTextOriginal;
			}
		}

		public string ToXml() {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="  ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter w=XmlWriter.Create(strBuilder,xmlSettings)) {
				w.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				w.WriteWhitespace("\r\n");
				//TODO:Implement more fields, this is just
				w.WriteStartElement("knowledgeRequestNotification");
					w.WriteAttributeString("classCode","ACT");
					w.WriteAttributeString("moodCode","DEF");
					//id
					//effectiveTime
					//subject1
					//holder
					//performer
					//informationRecipient
					//subject2
					//subject3
					w.WriteRaw(Subject3.ToXml(subject4List));
					//componentOf
				w.WriteEndElement();//knowledgeRequestNotification
			}
			return strBuilder.ToString();
		}

		public string ToUrl() {
			StringBuilder strB=new StringBuilder();
			strB.Append((effectiveTime.Year>1880?"knowledgeRequestNotification.effectiveTime.v="+effectiveTime.ToString("yyyyMMddhhmmss")+"&":""));
			//holder
			//assignedEntity
			//patientPerson
			//age
			//ageGroup
			//taskContext
			//subTopic
			for(int i=0;i<subject4List.Count;i++) {
				strB.Append("mainSearchCriteria.v.c"+(i==0?"":""+i)+"="+subject4List[i].mainSearchCriteria.value.code+"&");
				strB.Append("mainSearchCriteria.v.cs"+(i==0?"":""+i)+"="+subject4List[i].mainSearchCriteria.value.codeSystem+"&");
				strB.Append("mainSearchCriteria.v.dn"+(i==0?"":""+i)+"="+subject4List[i].mainSearchCriteria.value.displayName+"&");
				if(subject4List[i].mainSearchCriteria.originalText!=subject4List[i].mainSearchCriteria.value.displayName) {//original text only if different than display name.
					strB.Append("mainSearchCriteria.v.ot"+(i==0?"":""+i)+"="+subject4List[i].mainSearchCriteria.originalText+"&");
				}
				//severityObservation
			}
			//informationRecipient
			strB.Append("informationRecipient="+(recipientIsPatient?"PAT&":"PROV&"));
			//performer
			strB.Append("performer="+(performerIsPatient?"PAT&":"PROV&"));
			//encounter
			//serviceDeliveryLocation
			return strB.ToString().Replace(" ","%20");
		}

		public string ToUrl(string xml) {
			StringBuilder strBuilder=new StringBuilder();
			//TODO later, maybe
			//XmlDocument doc=new XmlDocument();
			//doc.LoadXml(xml);
			//XmlNode node=doc.SelectSingleNode("//Error");
			//if(node!=null) {
			//	throw new Exception(node.InnerText);
			//}
			return strBuilder.ToString();
		}

	}

	///<summary>Represents the globally unique instance identifier of a knowledge request.  0..*</summary>
	public class Id {

	}

	///<summary>Patient context information. Cardinality [0..1]</summary>
	public class Subject {

	}

	/////<summary>Patient context information. Cardinality [0..1]</summary>
	//public class Performer {
	//	public string typeCode;
	//}

	/////<summary>Patient context information. Cardinality [0..1]</summary>
	//public class InformationRecipient {

	//}

	///<summary>Task context information. Cardinality [0..1]</summary>
	public class Subject1 {

	}

	///<summary>Sub-topic information. Cardinality [0..1]</summary>
	public class Subject2 {

	}

	///<summary>Mostly just a main search criteria.</summary>
	public class Subject3 {
		public string typeCode;
		public MainSearchCriteria mainSearchCriteria;

		public Subject3() {
			typeCode="SUBJ";
			mainSearchCriteria=new MainSearchCriteria();
		}

		public Subject3(Value value) {
			typeCode="SUBJ";
			mainSearchCriteria=new MainSearchCriteria(value);
		}

		public static string ToXml(List<Subject3> subject4List) {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="  ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter w=XmlWriter.Create(strBuilder,xmlSettings)) {
				for(int i=0;i<subject4List.Count;i++){
					w.WriteStartElement("subject4");
						w.WriteAttributeString("typeCode",subject4List[i].typeCode);
						w.WriteRaw(subject4List[i].mainSearchCriteria.ToXml());
					w.WriteEndElement();
				}//end subject4List
			}//end using
			return strBuilder.ToString();
		}
	}

	public class MainSearchCriteria {
		///<summary>Static field "OBS".  Observation.  Cardinality [1..1]</summary>
		public static string classCode;
		///<summary>Static field "DEF".  A definition of a service (master).  Cardinality [1..1]</summary>
		public static string moodCode;
		///<summary>Static field.  This defines the value as being a knowledge subject.  Cardinality [1..1]</summary>
		public Code code;
		///<summary>Contains information on the snomed in question, icd9, icd10 ... etc code.  The "value" of the "code".  Cardinality [1..1]</summary>
		public Value value;
		///<summary>Represents the human readable representation of the code as displayed to the user in the CIS and SHOULD be used only if different than the displayName</summary>
		public string originalText;
		///<summary>Contains SeverityObservation:specifies the interpretation of a laboratory test result (e.g., 'high', 'low', 'abnormal', 'normal'). This class MAY be used to support implementations where the MainSearchCriteria consists of a laboratory test result. Supports questions such as "what are the causes of high serum potassium?</summary>
		public Subject4 subject;

		public MainSearchCriteria() {
			classCode="OBS";
			moodCode="DEF";
			code=new Code("KSUBJ","2.16.840.1.113883.6.96","SNOMEDCT","knowledge subject");
			value=new Value();
			subject = new Subject4();
		}

		public MainSearchCriteria(Value val) {
			classCode="OBS";
			moodCode="DEF";
			code=new Code("KSUBJ","2.16.840.1.113883.6.96","SNOMEDCT","knowledge subject");
			value=val;
			subject = new Subject4();
		}

		public string ToXml() {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="  ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter w=XmlWriter.Create(strBuilder,xmlSettings)) {
				w.WriteStartElement("mainSearchCriteria");
					w.WriteAttributeString("classCode",classCode);
					w.WriteAttributeString("moodCode",moodCode);
					w.WriteRaw(code.ToXml());
					w.WriteRaw(value.ToXml());
					if(originalText!="" && originalText!=null && originalText!=value.displayName){
						w.WriteStartElement("originalText");
							w.WriteString(originalText);
						w.WriteEndElement();//originalText
					}
				w.WriteEndElement();//mainSearchCriteria
			}
			return strBuilder.ToString();
		}

	}//MainSearchCriteria class

	public class Subject4 {
		public static string typeCode;
		public SeverityObservation severityObservation;

		public Subject4(){
			typeCode="SUBJ";
			severityObservation=new SeverityObservation();
		}
	}

	public class SeverityObservation {
		public static string classCode;
		public static string moodCode;
		public Code code;
		public ObservationInterpretationNormality observationInterpretationNormality;

		public SeverityObservation() {
			classCode="OBS";
			moodCode="DEF";
			code=new Code("SeverityObservationType","2.16.840.1.113883.5.6","ActClass","SeverityObservationType");
			observationInterpretationNormality=null;
		}
	}

	///<summary>Normality, Abnormality, Alert. Concepts in this category are mutually exclusive, i.e., at most one is allowed. Enum generated from HL7 _ObservationInterpretationNormality [2.16.840.1.113883.1.11.10206] which is a subset of ObservationInterpretation [OID=2.16.840.1.113883.5.83] documentation published 20120831 10:21 AM.</summary>
	public class ObservationInterpretationNormality {
		public string code;
		public static string codeSystem;
		public static string codeSystemName;
		public string displayName;

		public ObservationInterpretationNormality(){
			code="";
			codeSystem="2.16.840.1.113883.5.83";
			codeSystemName="ObservationInterpretation";
			displayName="";
		}

		public void SetInterpretation(LabAbnormalFlag laf) {
			switch(laf) {
				case LabAbnormalFlag.Above:
					SetInterpretation("H");
					return;
				case LabAbnormalFlag.Below:
					SetInterpretation("L");
					return;
				case LabAbnormalFlag.Normal:
					SetInterpretation("N");
					return;
				case LabAbnormalFlag.None:
				default:
					SetInterpretation("");
					return;
			}
		}

		///<summary>Set InterpretaionCodes Normal, Abnormal, High, Low, Alert.</summary>
		/// <param name="code">Allowed values: A, AA, HH, LL, H, L, N.</param>
		public void SetInterpretation(string interpretationCode){
			if(interpretationCode==null){
				code="";
				displayName="";
				return;
			}
			switch(interpretationCode){
				case "A":	///<summary>0 - Abnormal - Abnormal (for nominal observations, all service types) </summary>
					displayName="Abnormal";
					break;
				case "AA":///<summary>1 - Abnormal alert - Abnormal alert (for nominal observations and all service types) </summary>
					displayName="Abnormal alert";
					break;
				case "HH":///<summary>2 - High alert - Above upper alert threshold (for quantitative observations) </summary>
					displayName="High alert";
					break;
				case "LL":///<summary>3 - Low alert - Below lower alert threshold (for quantitative observations) </summary>
					displayName="Low alert";
					break;
				case "H":	///<summary>4 - High - Above high normal (for quantitative observations) </summary>
					displayName="High";
					break;
				case "L":	///<summary>5 - Low - Below low normal (for quantitative observations) </summary>
					displayName="Low";
					break;
				case "N":	///<summary>6 - Normal - Normal (for all service types) </summary>
					displayName="Normal";
					break;
				default:
					code="";
					displayName="";
					return;
			}
			code=interpretationCode;
		}
			
	}
	

	public class Code {
		///<summary>The actual code snomed, icd-9, or incd10, etc code. Example: 95521008</summary>
		public string code;
		///<summary>The HL7-OID of the code system used. Example: 2.16.840.1.113883.6.96 if using SNOMEDCT</summary>
		public string codeSystem;
		///<summary>The human readable name of the code system used. Example: SNOMEDCT</summary>
		public string codeSystemName;
		///<summary>The human readable name of the code.  Example: "Abnormal jaw movement (disorder)"</summary>
		public string displayName;

		public Code(string c,string cs,string csn,string dn) {
			code=c;
			codeSystem=cs;
			codeSystemName=csn;
			displayName=dn;
		}

		public string ToXml() {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="  ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter w=XmlWriter.Create(strBuilder,xmlSettings)) {
				w.WriteStartElement("code");
					w.WriteAttributeString("code",code);
					w.WriteAttributeString("codeSystem",codeSystem);
					w.WriteAttributeString("codeSystemName",codeSystemName);
					w.WriteAttributeString("displayName",displayName);
				w.WriteEndElement();//code
			}
			return strBuilder.ToString(); ;
		}

	}

	public class Value {
		///<summary>The actual code snomed, icd-9, or incd10, etc code. Example: 95521008</summary>
		public string code;
		///<summary>The HL7-OID of the code system used. Example: 2.16.840.1.113883.6.96 if using SNOMEDCT</summary>
		public string codeSystem;
		///<summary>The human readable name of the code system used. Example: SNOMEDCT</summary>
		public string codeSystemName;
		///<summary>The human readable name of the code.  Example: "Abnormal jaw movement (disorder)"</summary>
		public string displayName;

		public Value(string c,string cs,string csn,string dn) {
			code=c;
			codeSystem=cs;
			codeSystemName=csn;
			displayName=dn;
		}

		public Value() {

		}

		public string ToXml() {
			XmlWriterSettings xmlSettings=new XmlWriterSettings();
			xmlSettings.Encoding=Encoding.UTF8;
			xmlSettings.OmitXmlDeclaration=true;
			xmlSettings.Indent=true;
			xmlSettings.IndentChars="  ";
			StringBuilder strBuilder=new StringBuilder();
			using(XmlWriter w=XmlWriter.Create(strBuilder,xmlSettings)) {
				w.WriteStartElement("value");
					w.WriteAttributeString("code",code);
					w.WriteAttributeString("codeSystem",codeSystem);
					w.WriteAttributeString("codeSystemName",codeSystemName);
					w.WriteAttributeString("displayName",displayName);
				w.WriteEndElement();//code
				
			}
			return strBuilder.ToString();
		}

	}//value class

	///<summary>Encounter location and type.</summary>
	public class Component1 {

	}

}