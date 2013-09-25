using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using OpenDentBusiness;

namespace OpenDental {
	public partial class FormEhrSettings:Form {
		private List<string> ListRecEncCodes;
		private List<string> ListRecPregCodes;
		private int OldEncListSelectedIdx;
		private int OldPregListSelectedIdx;
		private string NewEncCodeSystem;
		private string NewPregCodeSystem;

		public FormEhrSettings() {
			InitializeComponent();
			Lan.F(this);
		}

		private void FormEhrSettings_Load(object sender,EventArgs e) {
			checkMU2.Checked=PrefC.GetBool(PrefName.MeaningfulUseTwo);
			#region DefaultEncounterGroup
			FillRecEncCodesList();
			string defaultEncCode=PrefC.GetString(PrefName.CQMDefaultEncounterCodeValue);
			string defaultEncCodeSystem=PrefC.GetString(PrefName.CQMDefaultEncounterCodeSystem);
			NewEncCodeSystem=defaultEncCodeSystem;
			OldEncListSelectedIdx=-1;
			for(int i=0;i<ListRecEncCodes.Count;i++) {
				listEncCodes.Items.Add(ListRecEncCodes[i]);
				if(ListRecEncCodes[i]==defaultEncCode && defaultEncCodeSystem=="SNOMEDCT") {//just in case the same code can exist in multiple tables, make sure it is SNOMEDCT before setting list selected item
					listEncCodes.SetSelected(i,true);
					OldEncListSelectedIdx=i;
					if(i==0) {//if "none" set as default
						continue;
					}
					labelEncWarning.Visible=false;
					Snomed sEnc=Snomeds.GetByCode(listEncCodes.SelectedItem.ToString());
					if(sEnc==null) {
						MsgBox.Show(this,"The snomed table does not contain this code.  The code should be added to the snomed table by running the Code System Importer tool.");
					}
					else {
						textEncCodeDescript.Text=sEnc.Description;
					}
				}
			}
			if(listEncCodes.SelectedIndex==-1) {//default enc code set to code not in recommended list and not 'none'
				switch(defaultEncCodeSystem) {
					case "CDTCPT":
						ProcedureCode pEnc=ProcedureCodes.GetProcCode(defaultEncCode);
						if(pEnc!=null) {
							textEncCodeValue.Text=pEnc.ProcCode;
							textEncCodeDescript.Text=pEnc.Descript;
						}
						break;
					case "SNOMEDCT":
						Snomed sEnc=Snomeds.GetByCode(defaultEncCode);
						if(sEnc!=null) {
							textEncCodeValue.Text=sEnc.SnomedCode;
							textEncCodeDescript.Text=sEnc.Description;
						}						
						break;
					case "HCPCS":
						Hcpcs hEnc=Hcpcses.GetByCode(defaultEncCode);
						if(hEnc!=null) {
							textEncCodeValue.Text=hEnc.HcpcsCode;
							textEncCodeDescript.Text=hEnc.DescriptionShort;
						}
						break;
					default:
						break;
				}
			}
			#endregion
			#region DefaultPregnancyGroup
			FillRecPregCodesList();
			string defaultPregCode=PrefC.GetString(PrefName.PregnancyDefaultCodeValue);
			string defaultPregCodeSystem=PrefC.GetString(PrefName.PregnancyDefaultCodeSystem);
			NewPregCodeSystem=defaultPregCodeSystem;
			OldPregListSelectedIdx=-1;
			for(int i=0;i<ListRecPregCodes.Count;i++) {
				listPregCodes.Items.Add(ListRecPregCodes[i]);
				if(ListRecPregCodes[i]==defaultPregCode && defaultPregCodeSystem=="SNOMEDCT") {//just in case the same code can exist in multiple tables, make sure it is SNOMEDCT before setting list selected item
					listPregCodes.SetSelected(i,true);
					OldPregListSelectedIdx=i;
					if(i==0) {//if "none" set as default
						continue;
					}
					labelPregWarning.Visible=false;
					Snomed sPreg=Snomeds.GetByCode(listPregCodes.SelectedItem.ToString());
					if(sPreg==null) {
						MsgBox.Show(this,"The snomed table does not contain this code.  The code should be added to the snomed table by running the Code System Importer tool.");
					}
					else {
						textPregCodeDescript.Text=sPreg.Description;
					}
				}
			}
			if(listPregCodes.SelectedIndex==-1) {//default preg code set to code not in recommended list and not 'none'
				switch(defaultPregCodeSystem) {
					case "ICD9CM":
						ICD9 i9Preg=ICD9s.GetByCode(defaultPregCode);
						if(i9Preg!=null) {
							textPregCodeValue.Text=i9Preg.ICD9Code;
							textPregCodeDescript.Text=i9Preg.Description;
						}
						break;
					case "SNOMEDCT":
						Snomed sPreg=Snomeds.GetByCode(defaultPregCode);
						if(sPreg!=null) {
							textPregCodeValue.Text=sPreg.SnomedCode;
							textPregCodeDescript.Text=sPreg.Description;
						}
						break;
					case "ICD10CM":
						Icd10 i10Preg=Icd10s.GetByCode(defaultPregCode);
						if(i10Preg!=null) {
							textPregCodeValue.Text=i10Preg.Icd10Code;
							textPregCodeDescript.Text=i10Preg.Description;
						}
						break;
					default:
						break;
				}
			}
			#endregion
		}

		private void FillRecEncCodesList() {
			ListRecEncCodes=new List<string>();
			ListRecEncCodes.Add("none");
			ListRecEncCodes.Add("90526000");//Initial evaluation and management of healthy individual (procedure)
			ListRecEncCodes.Add("185349003");//Encounter for "check-up" (procedure)
			ListRecEncCodes.Add("185463005");//Visit out of hours (procedure)
			ListRecEncCodes.Add("185465003");//Weekend visit (procedure)
			ListRecEncCodes.Add("270427003");//Patient-initiated encounter (procedure)
			ListRecEncCodes.Add("270430005");//Provider-initiated encounter (procedure)
			ListRecEncCodes.Add("308335008");//Patient encounter procedure (procedure)
			ListRecEncCodes.Add("390906007");//Follow-up encounter (procedure)
			ListRecEncCodes.Add("406547006");//Urgent follow-up (procedure)
		}

		private void FillRecPregCodesList() {
			ListRecPregCodes=new List<string>();
			ListRecPregCodes.Add("none");
			ListRecPregCodes.Add("72892002");//Normal pregnancy (finding)
			ListRecPregCodes.Add("77386006");//Patient currently pregnant (finding)
			ListRecPregCodes.Add("83074005");//Unplanned pregnancy (finding)
			ListRecPregCodes.Add("169560008");//Pregnant - urine test confirms (finding)
			ListRecPregCodes.Add("169563005");//Pregnant - on history (finding)
			ListRecPregCodes.Add("169565003");//Pregnant - planned (finding)
			ListRecPregCodes.Add("237238006");//Pregnancy with uncertain dates (finding)
			ListRecPregCodes.Add("248985009");//Presentation of pregnancy (finding)
			ListRecPregCodes.Add("314204000");//Early stage of pregnancy (finding)
		}

		private void checkMU2_Click(object sender,EventArgs e) {
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				checkMU2.Checked=PrefC.GetBool(PrefName.MeaningfulUseTwo);
			}
		}

		private void listEncCodes_Click(object sender,EventArgs e) {
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				listEncCodes.SelectedIndex=OldEncListSelectedIdx;
				return;
			}
			NewEncCodeSystem="SNOMEDCT";
			textEncCodeValue.Text="";
			if(listEncCodes.SelectedIndex==0) {//none
				textEncCodeDescript.Clear();
				labelEncWarning.Visible=true;
			}
			else {
				Snomed sEnc=Snomeds.GetByCode(listEncCodes.SelectedItem.ToString());
				if(sEnc==null) {
					MsgBox.Show(this,"The snomed table does not contain this code.  The code should be added to the snomed table by running the Code System Importer tool.");
				}
				else {
					textEncCodeDescript.Text=sEnc.Description;
				}
				labelEncWarning.Visible=false;
			}
		}

		private void listPregCodes_Click(object sender,EventArgs e) {
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				listPregCodes.SelectedIndex=OldPregListSelectedIdx;
				return;
			}
			NewPregCodeSystem="SNOMEDCT";
			textPregCodeValue.Text="";
			if(listPregCodes.SelectedIndex==0) {//none
				textPregCodeDescript.Clear();
				labelPregWarning.Visible=true;
			}
			else {
				Snomed sPreg=Snomeds.GetByCode(listPregCodes.SelectedItem.ToString());
				if(sPreg==null) {
					MsgBox.Show(this,"The snomed table does not contain this code.  The code should be added to the snomed table by running the Code System Importer tool.");
				}
				else {
					textPregCodeDescript.Text=sPreg.Description;
				}
				labelPregWarning.Visible=false;
			}
		}

		private void butEncSnomed_Click(object sender,EventArgs e) {
			FormSnomeds formS=new FormSnomeds();
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				formS.IsSelectionMode=false;
			}
			else {
				formS.IsSelectionMode=true;
			}
			formS.ShowDialog();
			if(formS.DialogResult==DialogResult.OK) {
				NewEncCodeSystem="SNOMEDCT";
				for(int i=1;i<listEncCodes.Items.Count;i++) {
					if(formS.SelectedSnomed.SnomedCode==listEncCodes.Items[i].ToString()) {//if they go to snomed list and select one of the recommended codes, select in list
						listEncCodes.SetSelected(i,true);
						textEncCodeValue.Clear();
						textEncCodeDescript.Text=formS.SelectedSnomed.Description;
						labelEncWarning.Visible=false;
						return;
					}
				}
				listEncCodes.ClearSelected();
				textEncCodeValue.Text=formS.SelectedSnomed.SnomedCode;
				textEncCodeDescript.Text=formS.SelectedSnomed.Description;
				labelEncWarning.Visible=true;
			}
		}

		///<summary>The HCPCS code picker form has not been implemented, when it has this code may need massaged.</summary>
		private void butEncHcpcs_Click(object sender,EventArgs e) {
			//FormHcpcs formH=new FormHcpcs();
			//if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
			//	formH.IsSelectionMode=false;
			//}
			//else {
			//	formH.IsSelectionMode=true;
			//}
			//formH.ShowDialog();
			//if(formH.DialogResult==DialogResult.OK) {
			//	NewEncCodeSystem="HCPCS";
			//	listEncCodes.ClearSelected();
			//	textEncCodeValue.Text=formH.SelectedHcpcs.HcpcsCode;
			//	textEncCodeDescript.Text=formH.SelectedHcpcs.Description;
			//	labelEncounterWarning.Visible=true;
			//}
		}

		private void butEncCdtCpt_Click(object sender,EventArgs e) {
			FormProcCodes formP=new FormProcCodes();
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				formP.IsSelectionMode=false;
			}
			else {
				formP.IsSelectionMode=true;
			}
			formP.ShowDialog();
			if(formP.DialogResult==DialogResult.OK) {
				NewEncCodeSystem="CDTCPT";
				listEncCodes.ClearSelected();
				ProcedureCode procCur=ProcedureCodes.GetProcCode(formP.SelectedCodeNum);
				textEncCodeValue.Text=procCur.ProcCode;
				textEncCodeDescript.Text=procCur.Descript;
				//We might implement a CodeSystem column on the ProcCode table since it may have ICD9 and ICD10 codes in it.  If so, we can set the NewEncCodeSystem to the value in that new column.
				//NewEncCodeSystem=procCur.CodeSystem;
				labelEncWarning.Visible=true;
			}
		}

		private void butPregSnomed_Click(object sender,EventArgs e) {
			FormSnomeds formS=new FormSnomeds();
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				formS.IsSelectionMode=false;
			}
			else {
				formS.IsSelectionMode=true;
			}
			formS.ShowDialog();
			if(formS.DialogResult==DialogResult.OK) {
				NewPregCodeSystem="SNOMEDCT";
				for(int i=1;i<listPregCodes.Items.Count;i++) {
					if(formS.SelectedSnomed.SnomedCode==listPregCodes.Items[i].ToString()) {//if they go to snomed list and select one of the recommended codes, select in list
						listPregCodes.SetSelected(i,true);
						textPregCodeValue.Clear();
						textPregCodeDescript.Text=formS.SelectedSnomed.Description;
						labelPregWarning.Visible=false;
						return;
					}
				}
				listPregCodes.ClearSelected();
				textPregCodeValue.Text=formS.SelectedSnomed.SnomedCode;
				textPregCodeDescript.Text=formS.SelectedSnomed.Description;
				labelPregWarning.Visible=true;
			}
		}

		private void butPregIcd9_Click(object sender,EventArgs e) {
			FormIcd9s formI9=new FormIcd9s();
			if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
				formI9.IsSelectionMode=false;
			}
			else {
				formI9.IsSelectionMode=true;
			}
			formI9.ShowDialog();
			if(formI9.DialogResult==DialogResult.OK) {
				NewPregCodeSystem="ICD9CM";
				listPregCodes.ClearSelected();
				textPregCodeValue.Text=formI9.SelectedIcd9.ICD9Code;
				textPregCodeDescript.Text=formI9.SelectedIcd9.Description;
				labelPregWarning.Visible=true;
			}
		}

		///<summary>The ICD10 code picker form has not been implemented, when it has this code may need massaged.</summary>
		private void butPregIcd10_Click(object sender,EventArgs e) {
			//FormIcd10s formI10=new FormIcd10s();
			//if(!Security.IsAuthorized(Permissions.SecurityAdmin,false)) {
			//	formI10.IsSelectionMode=false;
			//}
			//else {
			//	formI10.IsSelectionMode=true;
			//}
			//formI10.ShowDialog();
			//if(formI10.DialogResult==DialogResult.OK) {
			//	NewPregCodeSystem="ICD10CM";
			//	listPregCodes.ClearSelected();
			//	textPregCodeValue.Text=formI10.SelectedIcd10.ICD9Code;
			//	textPregCodeDescript.Text=formI10.SelectedIcd10.Description;
			//	labelPregWarning.Visible=true;
			//}
		}

		private void butOK_Click(object sender,EventArgs e) {
			Prefs.UpdateBool(PrefName.MeaningfulUseTwo,checkMU2.Checked);
			Prefs.UpdateString(PrefName.CQMDefaultEncounterCodeSystem,NewEncCodeSystem);
			Prefs.UpdateString(PrefName.PregnancyDefaultCodeSystem,NewPregCodeSystem);
			if(listEncCodes.SelectedIndex==-1) {
				Prefs.UpdateString(PrefName.CQMDefaultEncounterCodeValue,textEncCodeValue.Text);
			}
			else {
				Prefs.UpdateString(PrefName.CQMDefaultEncounterCodeValue,listEncCodes.SelectedItem.ToString());
			}
			if(listPregCodes.SelectedIndex==-1) {
				Prefs.UpdateString(PrefName.PregnancyDefaultCodeValue,textPregCodeValue.Text);
			}
			else {
				Prefs.UpdateString(PrefName.PregnancyDefaultCodeValue,listPregCodes.SelectedItem.ToString());
			}
//TODO: Create a diseasedef object with this pregnancy code if one does not exist with DiseaseName="Pregnant (default EHR)". If that name already exists but the code is now different, intelligently rename the current def (like Pregnant (old default 1...N)).  If this code already exists in the diseasedef table, tack (default EHR) on the end of the DiseaseName. ItemOrder=0, IsHidden=0, ICD9Code/SnomedCode=textPregCodeValue/listPregCodes.SelectedItem.
//What to do if it is an ICD10? New column in diseasedef table?
			DialogResult=DialogResult.OK;
		}

		private void butCancel_Click(object sender,EventArgs e) {
			DialogResult=DialogResult.Cancel;
		}

	}
}