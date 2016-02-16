//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.ActEncounterCode;
import OpenDental.ActTaskCode;
import OpenDental.FormAllergySetup;
import OpenDental.FormDiseaseDefs;
import OpenDental.FormIcd10s;
import OpenDental.FormIcd9s;
import OpenDental.FormLoincs;
import OpenDental.FormRxNorms;
import OpenDental.FormSnomeds;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.ObservationInterpretationNormality;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.OIDInternals;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RxNorm;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Security;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDental.Properties.Resources;

public class FormInfobutton  extends Form 
{

    public Patient PatCur;
    /**
    * Usually filled from within the form by using Patcur.PriProv
    */
    public Provider ProvCur;
    /**
    * Knowledge request objects, possible object types are: DiseaseDef, Medication, LabResult, ICD9, Icd10, Snomed, RxNorm, Loinc, or LabResult.  Should not break if unsupported objects are in list.
    */
    public List<Object> ListObjects = new List<Object>();
    //public List<DiseaseDef> ListProblems;//should this be named disease or problem? Also snomed/medication
    //public List<Medication> ListMedications;
    //public List<LabResult> ListLabResults;
    /**
    * //Used to add various codes that are not explicitly related to a problem, medication, or allergy.
    */
    //public List<Snomed> ListSnomed;
    private ActTaskCode ActTC = ActTaskCode.OE;
    //may need to make this public later.
    public ObservationInterpretationNormality ObsInterpretation = ObservationInterpretationNormality.A;
    public ActEncounterCode ActEC = ActEncounterCode.AMB;
    public boolean UseAge = new boolean();
    public boolean UseAgeGroup = new boolean();
    public boolean PerformerIsProvider = new boolean();
    public boolean RecipientIsProvider = new boolean();
    private CultureInfo[] arrayCultures = new CultureInfo[]();
    public FormInfobutton() throws Exception {
        ListObjects = new List<Object>();
        initializeComponent();
        Lan.F(this);
    }

    private void formInfobutton_Load(Object sender, EventArgs e) throws Exception {
        fillLanguageCombos();
        fillEncounterCombo();
        fillTaskCombo();
        fillContext();
        fillKnowledgeRequestitems();
    }

    private void fillLanguageCombos() throws Exception {
        arrayCultures = CultureInfo.GetCultures(CultureTypes.SpecificCultures);
        for (int i = 0;i < arrayCultures.Length;i++)
        {
            comboPatLang.Items.Add(arrayCultures[i].DisplayName);
            comboProvLang.Items.Add(arrayCultures[i].DisplayName);
        }
    }

    private void fillEncounterCombo() throws Exception {
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

    private void fillTaskCombo() throws Exception {
        comboTask.Items.Add("Order Entry");
        comboTask.Items.Add("Patient Documentation");
        comboTask.Items.Add("Patient Information Review");
    }

    private void fillContext() throws Exception {
        //Fill Patient-------------------------------------------------------------------------------------------------------------------
        if (PatCur != null)
        {
            textPatName.Text = PatCur.getNameFL();
            if (PatCur.Birthdate != DateTime.MinValue)
            {
                textPatBirth.Text = PatCur.Birthdate.ToShortDateString();
            }
             
            comboPatLang.SelectedIndex = comboPatLang.Items.IndexOf(System.Globalization.CultureInfo.CurrentCulture.DisplayName);
            switch(PatCur.Gender)
            {
                case Female: 
                    radioPatGenFem.Checked = true;
                    break;
                case Male: 
                    radioPatGenMale.Checked = true;
                    break;
                case Unknown: 
                default: 
                    radioPatGenUn.Checked = true;
                    break;
            
            }
        }
         
        //Fill Provider------------------------------------------------------------------------------------------------------------------
        if (ProvCur == null && PatCur != null)
        {
            ProvCur = Providers.getProv(PatCur.PriProv);
        }
         
        if (ProvCur == null)
        {
            ProvCur = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        }
         
        if (ProvCur != null)
        {
            textProvName.Text = ProvCur.getFormalName();
            textProvID.Text = ProvCur.NationalProvID;
            comboProvLang.SelectedIndex = comboPatLang.Items.IndexOf(System.Globalization.CultureInfo.CurrentCulture.DisplayName);
        }
         
        //Fill Organization--------------------------------------------------------------------------------------------------------------
        textOrgName.Text = PrefC.getString(PrefName.PracticeTitle);
        //Fill Encounter-----------------------------------------------------------------------------------------------------------------
        ActEC = ActEncounterCode.AMB;
        comboEncType.SelectedIndex = ((Enum)ActEC).ordinal();
        //ambulatory
        if (PatCur != null)
        {
            textEncLocID.Text = PatCur.ClinicNum.ToString();
        }
         
        //do not use to generate message if this value is zero.
        //Fill Requestor/Recievor--------------------------------------------------------------------------------------------------------
        radioReqProv.Checked = PerformerIsProvider;
        radioReqPat.Checked = !PerformerIsProvider;
        radioRecProv.Checked = RecipientIsProvider;
        radioRecPat.Checked = !RecipientIsProvider;
        //Fill Task Type-----------------------------------------------------------------------------------------------------------------
        ActTC = ActTaskCode.PATINFO;
        //may need to change this later.
        comboTask.SelectedIndex = ((Enum)ActTC).ordinal();
    }

    private void fillKnowledgeRequestitems() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("Type",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("CodeSystem",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Description",80);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListObjects.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            List.INDEXER.APPLY.APPLY.APPLY.INDEXER __dummyScrutVar1 = ListObjects[i].GetType().ToString().Split(new String[]{ "." }, StringSplitOptions.None)[1];
            if (__dummyScrutVar1.equals("DiseaseDef"))
            {
                if (!StringSupport.equals(((DiseaseDef)ListObjects[i]).ICD9Code, ""))
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case;
                    ICD9 icd9 = ICD9s.getByCode(((DiseaseDef)ListObjects[i]).ICD9Code);
                    row.getCells().add("Problem");
                    row.getCells().add(icd9.ICD9Code);
                    row.getCells().add("ICD9-CM");
                    row.getCells().add(icd9.Description);
                    row.setTag(icd9);
                    gridMain.getRows().add(row);
                }
                 
                if (!StringSupport.equals(((DiseaseDef)ListObjects[i]).SnomedCode, ""))
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    Snomed snomed = Snomeds.getByCode(((DiseaseDef)ListObjects[i]).SnomedCode);
                    row.getCells().add("Problem");
                    row.getCells().add(snomed.SnomedCode);
                    row.getCells().add("SNOMED CT");
                    row.getCells().add(snomed.Description);
                    row.setTag(snomed);
                    gridMain.getRows().add(row);
                }
                 
                if (!StringSupport.equals(((DiseaseDef)ListObjects[i]).Icd10Code, ""))
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    row.getCells().add("Problem");
                    Icd10 icd10 = Icd10s.getByCode(((DiseaseDef)ListObjects[i]).Icd10Code);
                    row.getCells().add(icd10.Icd10Code);
                    row.getCells().add("ICD10");
                    row.getCells().add(icd10.Description);
                    row.setTag(icd10);
                    gridMain.getRows().add(row);
                }
                 
                if (!StringSupport.equals(((DiseaseDef)ListObjects[i]).ICD9Code, "") && !StringSupport.equals(((DiseaseDef)ListObjects[i]).SnomedCode, "") && !StringSupport.equals(((DiseaseDef)ListObjects[i]).Icd10Code, ""))
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    row.getCells().add("Problem");
                    row.getCells().add("none");
                    row.getCells().add("none");
                    row.getCells().add(((DiseaseDef)ListObjects[i]).DiseaseName);
                    row.setTag(null);
                    gridMain.getRows().add(row);
                }
                 
                continue;
            }
            else //not break, because we have already added the rows.
            if (__dummyScrutVar1.equals("Medication"))
            {
                if (((Medication)ListObjects[i]).RxCui != 0)
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    row.getCells().add("Medication");
                    RxNorm rxNorm = RxNorms.GetByRxCUI(((Medication)ListObjects[i]).RxCui.ToString());
                    row.getCells().add(rxNorm.RxCui);
                    row.getCells().add("RxNorm");
                    row.getCells().add(rxNorm.Description);
                    row.setTag(rxNorm);
                    gridMain.getRows().add(row);
                }
                 
                if (((Medication)ListObjects[i]).RxCui == 0)
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    row.getCells().add("Medication");
                    row.getCells().add("none");
                    row.getCells().add("none");
                    row.getCells().add(((Medication)ListObjects[i]).MedName);
                    row.setTag(ListObjects[i]);
                    gridMain.getRows().add(row);
                }
                 
                continue;
            }
            else if (__dummyScrutVar1.equals("MedicationPat"))
            {
                if (((MedicationPat)ListObjects[i]).RxCui != 0)
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    row.getCells().add("Medication");
                    RxNorm rxNorm = RxNorms.GetByRxCUI(((MedicationPat)ListObjects[i]).RxCui.ToString());
                    row.getCells().add(rxNorm.RxCui);
                    row.getCells().add("RxNorm");
                    row.getCells().add(rxNorm.Description);
                    row.setTag(rxNorm);
                    gridMain.getRows().add(row);
                }
                 
                if (!StringSupport.equals(((MedicationPat)ListObjects[i]).MedDescript, ""))
                {
                    row = new OpenDental.UI.ODGridRow();
                    //just in case
                    row.getCells().add("Medication");
                    row.getCells().add("none");
                    row.getCells().add("none");
                    row.getCells().add(((MedicationPat)ListObjects[i]).MedDescript);
                    row.setTag(ListObjects[i]);
                    gridMain.getRows().add(row);
                }
                 
                continue;
            }
            else if (__dummyScrutVar1.equals("ICD9"))
            {
                ICD9 icd9Obj = (ICD9)ListObjects[i];
                row.getCells().add("Code");
                row.getCells().add(icd9Obj.ICD9Code);
                row.getCells().add("ICD9 CM");
                row.getCells().add(icd9Obj.Description);
                row.setTag(icd9Obj);
            }
            else if (__dummyScrutVar1.equals("Snomed"))
            {
                Snomed snomedObj = (Snomed)ListObjects[i];
                row.getCells().add("Code");
                row.getCells().add(snomedObj.SnomedCode);
                row.getCells().add("SNOMED CT");
                row.getCells().add(snomedObj.Description);
                row.setTag(snomedObj);
            }
            else if (__dummyScrutVar1.equals("Icd10"))
            {
                Icd10 icd10Obj = (Icd10)ListObjects[i];
                row.getCells().add("Code");
                row.getCells().add(icd10Obj.Icd10Code);
                row.getCells().add("ICD10 CM");
                row.getCells().add(icd10Obj.Description);
                row.setTag(icd10Obj);
            }
            else if (__dummyScrutVar1.equals("RxNorm"))
            {
                RxNorm rxNormObj = (RxNorm)ListObjects[i];
                row.getCells().add("Code");
                row.getCells().add(rxNormObj.RxCui);
                row.getCells().add("RxNorm");
                row.getCells().add(rxNormObj.Description);
                row.setTag(rxNormObj);
            }
            else //case "LabResult"://Deprecated
            //TODO
            //Loinc loincObj=(Loinc)ListObjects[i];
            //row.Cells.Add("Code");
            //row.Cells.Add(loincObj.LoincCode);
            //row.Cells.Add("LOINC");
            //row.Cells.Add(loincObj.NameShort);
            //row.Tag=loincObj;
            //break;
            if (__dummyScrutVar1.equals("EhrLabResult"))
            {
                EhrLabResult ehrLabResultObj = (EhrLabResult)ListObjects[i];
                row.getCells().add("Lab Result");
                if (!StringSupport.equals(ehrLabResultObj.ObservationIdentifierID, ""))
                {
                    //1st triplet
                    row.getCells().add(ehrLabResultObj.ObservationIdentifierID);
                    row.getCells().add("LOINC");
                    row.getCells().add(ehrLabResultObj.ObservationIdentifierText);
                }
                else if (!StringSupport.equals(ehrLabResultObj.ObservationIdentifierIDAlt, ""))
                {
                    //second triplet
                    row.getCells().add(ehrLabResultObj.ObservationIdentifierIDAlt);
                    row.getCells().add("LOINC");
                    row.getCells().add(ehrLabResultObj.ObservationIdentifierTextAlt);
                }
                else
                {
                    row.getCells().add("");
                    row.getCells().add("UNK");
                    row.getCells().add("Unknown");
                }  
                row.setTag(ehrLabResultObj);
            }
            else if (__dummyScrutVar1.equals("Loinc"))
            {
                Loinc loincObj = (Loinc)ListObjects[i];
                row.getCells().add("Code");
                row.getCells().add(loincObj.LoincCode);
                row.getCells().add("LOINC");
                row.getCells().add(loincObj.NameShort);
                row.setTag(loincObj);
            }
                     
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butPreview_Click(Object sender, EventArgs e) throws Exception {
        if (!isValidHL7DataSet())
        {
            return ;
        }
         
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(generateKnowledgeRequestNotification());
        msgbox.ShowDialog();
    }

    /**
    * Generates message box with all errors. Returns true if data passes validation or if user decides to "continue anyways".
    */
    private boolean isValidHL7DataSet() throws Exception {
        String warnings = "";
        //additional data that could be used but is not neccesary.
        String errors = "";
        //additional data that must be present in order to be compliant.
        String message = "";
        String bullet = "  - ";
        //should be used at the beggining of every warning/error
        //Patient information-------------------------------------------------------------------------------------------------
        if (PatCur == null)
        {
            //should never happen
            warnings += bullet + Lan.g(this,"No patient selected.") + "\r\n";
        }
        else
        {
            try
            {
                PatCur.Birthdate = PIn.Date(textPatBirth.Text);
            }
            catch (Exception __dummyCatchVar0)
            {
                warnings += bullet + Lan.g(this,"Birthday.") + "\r\n";
            }

            if (PatCur.Birthdate == DateTime.MinValue)
            {
                warnings += bullet + Lan.g(this,"Patient does not have a valid birthday.") + "\r\n";
            }
             
        } 
        //Provider information------------------------------------------------------------------------------------------------
        if (ProvCur == null)
        {
            warnings += bullet + Lan.g(this,"No provider selected.") + "\r\n";
        }
        else
        {
            if (StringSupport.equals(textProvID.Text, ""))
            {
                warnings += bullet + Lan.g(this,"No povider ID.") + "\r\n";
            }
             
        } 
        //Organization information--------------------------------------------------------------------------------------------
        if (StringSupport.equals(textOrgName.Text, ""))
        {
            warnings += bullet + Lan.g(this,"No organization name.") + "\r\n";
        }
         
        if (StringSupport.equals(textOrgID.Text, ""))
        {
            warnings += bullet + Lan.g(this,"No organization ID.") + "\r\n";
        }
         
        //Encounter information-----------------------------------------------------------------------------------------------
        if (StringSupport.equals(textEncLocID.Text, ""))
        {
            warnings += bullet + Lan.g(this,"No encounter location ID.") + "\r\n";
        }
         
        //Requestor information-----------------------------------------------------------------------------------------------
        if (radioReqPat.Checked && radioRecProv.Checked)
        {
            warnings += bullet + Lan.g(this,"It is uncommon for the requestor to be the patient and the recipient to be the provider.") + "\r\n";
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
        if (!StringSupport.equals(errors, ""))
        {
            message += Lan.g(this,"The following errors must be corrected in order to comply with HL7 standard:") + "\r\n";
            message += errors;
            message += "\r\n";
        }
         
        if (!StringSupport.equals(warnings, ""))
        {
            message += Lan.g(this,"Fixing the following warnings may provide better knowledge request results:") + "\r\n";
            message += warnings;
            message += "\r\n";
        }
         
        if (!StringSupport.equals(message, ""))
        {
            message += Lan.g(this,"Would you like to continue anyways?");
            if (MessageBox.Show(message, "", MessageBoxButtons.YesNo) != DialogResult.Yes)
            {
                return false;
            }
             
        }
         
        return true;
    }

    private String generateKnowledgeRequestNotification() throws Exception {
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
        XmlWriterSettings xmlSettings = new XmlWriterSettings();
        xmlSettings.Encoding = Encoding.UTF8;
        xmlSettings.OmitXmlDeclaration = true;
        xmlSettings.Indent = true;
        xmlSettings.IndentChars = "  ";
        StringBuilder strBuilder = new StringBuilder();
        XmlWriter w = XmlWriter.Create(strBuilder, xmlSettings);
        try
        {
            {
                w.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                w.WriteWhitespace("\r\n");
                w.WriteStartElement("knowledgeRequestNotification");
                w.WriteAttributeString("classCode", "ACT");
                w.WriteAttributeString("moodCode", "DEF");
                w.WriteStartElement("id");
                w.WriteAttributeString("value", knowledgeRequestIDHelper());
                w.WriteAttributeString("assigningAuthority", OIDInternals.getForType(IdentifierType.Root).IDRoot);
                w.WriteEndElement();
                //id
                w.WriteStartElement("effectiveTime");
                w.WriteAttributeString("value", DateTime.Now.ToString("yyyyMMddhhmmss"));
                w.WriteEndElement();
                //effectiveTime
                w.WriteStartElement("subject1");
                w.WriteAttributeString("typeCode", "SBJ");
                w.WriteStartElement("patientContext");
                w.WriteAttributeString("classCode", "PAT");
                w.WriteStartElement("patientPerson");
                w.WriteAttributeString("classCode", "PSN");
                w.WriteAttributeString("determinerCode", "INSTANCE");
                w.WriteStartElement("administrativeGenderCode");
                w.WriteAttributeString("code", administrativeGenderCodeHelper(PatCur.Gender));
                w.WriteAttributeString("codeSytem", "2.16.840.1.113883.5.1");
                w.WriteAttributeString("codeSystemName", "administrativeGender");
                w.WriteAttributeString("displayName", administrativeGenderNameHelper(PatCur.Gender));
                w.WriteEndElement();
                //administrativeGenderCode
                w.WriteEndElement();
                //patientPerson
                if (PatCur.Birthdate != DateTime.MinValue)
                {
                    w.WriteStartElement("subjectOf");
                    w.WriteAttributeString("typeCode", "SBJ");
                    if (UseAge || UseAge == UseAgeGroup)
                    {
                        //if true or both are false; field is required.
                        w.WriteStartElement("age");
                        w.WriteAttributeString("classCode", "OBS");
                        w.WriteAttributeString("moodCode", "DEF");
                        w.WriteStartElement("code");
                        w.WriteAttributeString("code", "30525-0");
                        w.WriteAttributeString("codeSytem", "2.16.840.1.113883.6.1");
                        w.WriteAttributeString("codeSystemName", "LN");
                        w.WriteAttributeString("displayName", "AGE");
                        w.WriteEndElement();
                        //code
                        w.WriteStartElement("value");
                        w.WriteAttributeString("value", PatCur.getAge().ToString());
                        w.WriteAttributeString("unit", "a");
                        w.WriteEndElement();
                        //value
                        w.WriteEndElement();
                    }
                     
                    //age
                    if (UseAgeGroup || UseAge == UseAgeGroup)
                    {
                        //if true or both are false; field is required.
                        w.WriteStartElement("ageGroup");
                        w.WriteAttributeString("classCode", "OBS");
                        w.WriteAttributeString("moodCode", "DEF");
                        w.WriteStartElement("code");
                        w.WriteAttributeString("code", "46251-5");
                        w.WriteAttributeString("codeSytem", "2.16.840.1.113883.6.1");
                        w.WriteAttributeString("codeSystemName", "LN");
                        w.WriteAttributeString("displayName", "Age Groups");
                        w.WriteEndElement();
                        //code
                        w.WriteStartElement("value");
                        w.WriteAttributeString("code", ageGroupCodeHelper(PatCur.Birthdate));
                        w.WriteAttributeString("codeSytem", "2.16.840.1.113883.6.177");
                        w.WriteAttributeString("codeSystemName", "MSH");
                        w.WriteAttributeString("displayName", ageGroupNameHelper(PatCur.Birthdate));
                        w.WriteEndElement();
                        //value
                        w.WriteEndElement();
                    }
                     
                    //ageGroup
                    w.WriteEndElement();
                }
                 
                //subjectOf
                w.WriteEndElement();
                //patientContext
                w.WriteEndElement();
                //subject1
                w.WriteStartElement("holder");
                w.WriteAttributeString("typeCode", "HLD");
                w.WriteStartElement("assignedEntity");
                w.WriteAttributeString("classCode", "ASSIGNED");
                w.WriteStartElement("name");
                w.WriteString(Security.getCurUser().UserName);
                w.WriteEndElement();
                //name
                w.WriteStartElement("certificateText");
                w.WriteString(Security.getCurUser().Password);
                w.WriteEndElement();
                //certificateText
                w.WriteStartElement("assignedAuthorizedPerson");
                w.WriteAttributeString("classCode", "PSN");
                w.WriteAttributeString("determinerCode", "INSTANCE");
                if (!StringSupport.equals(textProvID.Text, ""))
                {
                    w.WriteStartElement("id");
                    w.WriteAttributeString("value", textProvID.Text);
                    w.WriteEndElement();
                }
                 
                //id
                w.WriteEndElement();
                //assignedAuthorizedPerson
                if (!StringSupport.equals(textOrgID.Text, "") && !StringSupport.equals(textOrgName.Text, ""))
                {
                    w.WriteStartElement("representedOrganization");
                    w.WriteAttributeString("classCode", "ORG");
                    w.WriteAttributeString("determinerCode", "INSTANCE");
                    if (!StringSupport.equals(textOrgID.Text, ""))
                    {
                        w.WriteStartElement("id");
                        w.WriteAttributeString("value", textOrgID.Text);
                        w.WriteEndElement();
                    }
                     
                    //id
                    if (!StringSupport.equals(textOrgName.Text, ""))
                    {
                        w.WriteStartElement("name");
                        w.WriteAttributeString("value", textOrgName.Text);
                        w.WriteEndElement();
                    }
                     
                    //name
                    w.WriteEndElement();
                }
                 
                //representedOrganization
                w.WriteEndElement();
                //assignedEntity
                w.WriteEndElement();
                //holder
                //Performer (Requester)--------------------------------------------------------------------------------------------------------------------------
                w.WriteStartElement("performer");
                w.WriteAttributeString("typeCode", "PRF");
                if (radioReqProv.Checked)
                {
                    //----performer choice-----
                    w.WriteStartElement("healthCareProvider");
                    w.WriteAttributeString("classCode", "PROV");
                    w.WriteStartElement("code");
                    w.WriteAttributeString("code", "120000000X");
                    w.WriteAttributeString("codeSytem", "2.16.840.1.113883.6.101");
                    w.WriteAttributeString("codeSystemName", "NUCC Health Care Provider Taxonomy");
                    w.WriteAttributeString("displayName", "Dental Providers");
                    w.WriteEndElement();
                    //code
                    if ((!StringSupport.equals(comboProvLang.Text, "") && radioReqProv.Checked) || (StringSupport.equals(comboPatLang.Text, "") && radioReqPat.Checked))
                    {
                        //A missing languageCommunication field invalidates the entire Person class.
                        w.WriteStartElement("healthCarePerson");
                        w.WriteAttributeString("classCode", "PSN");
                        w.WriteAttributeString("determinerCode", "INSTANCE");
                        w.WriteStartElement("languageCommunication");
                        w.WriteStartElement("languageCommunicationCode");
                        w.WriteAttributeString("code", arrayCultures[comboProvLang.SelectedIndex].ThreeLetterISOLanguageName);
                        w.WriteAttributeString("codeSytem", "1.0.639.2");
                        w.WriteAttributeString("codeSystemName", "ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
                        w.WriteAttributeString("displayName", arrayCultures[comboProvLang.SelectedIndex].DisplayName);
                        w.WriteEndElement();
                        //languageCommunicationCode
                        w.WriteEndElement();
                        //languageCommunication
                        w.WriteEndElement();
                    }
                     
                    //healthCarePerson
                    //end if no language selected.
                    w.WriteEndElement();
                }
                else
                {
                    //healthCareProvider
                    //Performer is patient.
                    w.WriteStartElement("patient");
                    w.WriteAttributeString("classCode", "PAT");
                    if ((!StringSupport.equals(comboProvLang.Text, "") && radioRecProv.Checked) || (StringSupport.equals(comboPatLang.Text, "") && radioRecPat.Checked))
                    {
                        //A missing languageCommunication field invalidates the entire Person class.
                        w.WriteStartElement("patientPerson");
                        w.WriteAttributeString("classCode", "PSN");
                        w.WriteAttributeString("determinerCode", "INSTANCE");
                        w.WriteStartElement("languageCommunication");
                        w.WriteStartElement("languageCommunicationCode");
                        w.WriteAttributeString("code", arrayCultures[comboPatLang.SelectedIndex].ThreeLetterISOLanguageName);
                        w.WriteAttributeString("codeSytem", "1.0.639.2");
                        w.WriteAttributeString("codeSystemName", "ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
                        w.WriteAttributeString("displayName", arrayCultures[comboPatLang.SelectedIndex].DisplayName);
                        w.WriteEndElement();
                        //languageCommunicationCode
                        w.WriteEndElement();
                        //languageCommunication
                        w.WriteEndElement();
                    }
                     
                    //patientPerson
                    //end if no language selected.
                    w.WriteEndElement();
                } 
                //patient
                w.WriteEndElement();
                //performer
                //InformationRecipient--------------------------------------------------------------------------------------------------------------------------
                w.WriteStartElement("informationRecipient");
                if (radioRecProv.Checked)
                {
                    //----performer choice-----
                    w.WriteStartElement("healthCareProvider");
                    w.WriteAttributeString("classCode", "PROV");
                    w.WriteStartElement("code");
                    w.WriteAttributeString("code", "120000000X");
                    w.WriteAttributeString("codeSytem", "2.16.840.1.113883.6.101");
                    w.WriteAttributeString("codeSystemName", "NUCC Health Care Provider Taxonomy");
                    w.WriteAttributeString("displayName", "Dental Providers");
                    w.WriteEndElement();
                    //code
                    w.WriteStartElement("healthCarePerson");
                    w.WriteAttributeString("classCode", "PSN");
                    w.WriteAttributeString("determinerCode", "INSTANCE");
                    w.WriteStartElement("languageCommunication");
                    w.WriteStartElement("languageCommunicationCode");
                    w.WriteAttributeString("code", arrayCultures[comboProvLang.SelectedIndex].ThreeLetterISOLanguageName);
                    w.WriteAttributeString("codeSytem", "1.0.639.2");
                    w.WriteAttributeString("codeSystemName", "ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
                    w.WriteAttributeString("displayName", arrayCultures[comboProvLang.SelectedIndex].DisplayName);
                    w.WriteEndElement();
                    //languageCommunicationCode
                    w.WriteEndElement();
                    //languageCommunication
                    w.WriteEndElement();
                    //healthCarePerson
                    w.WriteEndElement();
                }
                else
                {
                    //healthCareProvider
                    //Performer is patient.
                    w.WriteStartElement("patient");
                    w.WriteAttributeString("classCode", "PAT");
                    w.WriteStartElement("patientPerson");
                    w.WriteAttributeString("classCode", "PSN");
                    w.WriteAttributeString("determinerCode", "INSTANCE");
                    w.WriteStartElement("languageCommunication");
                    w.WriteStartElement("languageCommunicationCode");
                    w.WriteAttributeString("code", arrayCultures[comboPatLang.SelectedIndex].ThreeLetterISOLanguageName);
                    w.WriteAttributeString("codeSytem", "1.0.639.2");
                    w.WriteAttributeString("codeSystemName", "ISO 639-2: Codes for the representation of names of languages -- Part 2: Alpha-3 code");
                    w.WriteAttributeString("displayName", arrayCultures[comboPatLang.SelectedIndex].DisplayName);
                    w.WriteEndElement();
                    //languageCommunicationCode
                    w.WriteEndElement();
                    //languageCommunication
                    w.WriteEndElement();
                    //patientPerson
                    w.WriteEndElement();
                } 
                //patient
                w.WriteEndElement();
                //informationRecipient
                w.WriteStartElement("subject2");
                w.WriteAttributeString("typeCode", "SUBJ");
                w.WriteStartElement("taskContext");
                w.WriteAttributeString("classCode", "ACT");
                w.WriteAttributeString("moodCode", "DEF");
                w.WriteStartElement("code");
                w.WriteAttributeString("code", actTaskCodeHelper());
                w.WriteAttributeString("codeSytem", "2.16.840.1.113883.5.4");
                w.WriteAttributeString("codeSystemName", "ActCode");
                w.WriteAttributeString("displayName", actTaskCodeNameHelper());
                w.WriteEndElement();
                //code
                w.WriteEndElement();
                //taskContext
                w.WriteEndElement();
                //subject2
                w.WriteStartElement("subject3");
                w.WriteAttributeString("typeCode", "SUBJ");
                w.WriteStartElement("subTopic");
                w.WriteAttributeString("classCode", "OBS");
                w.WriteAttributeString("moodCode", "DEF");
                w.WriteStartElement("code");
                w.WriteAttributeString("code", "KSUBT");
                w.WriteAttributeString("codeSytem", "2.16.840.1.113883.5.4");
                w.WriteAttributeString("codeSystemName", "ActCode");
                w.WriteAttributeString("displayName", "knowledge subtopic");
                w.WriteEndElement();
                //code
                //w.WriteStartElement("value");
                //	w.WriteAttributeString("code","TODO");
                //	w.WriteAttributeString("codeSytem","2.16.840.1.113883.6.177");
                //	w.WriteAttributeString("codeSystemName","MSH");
                //	w.WriteAttributeString("displayName","TODO");
                //w.WriteEndElement();//value
                w.WriteEndElement();
                //subTopic
                w.WriteEndElement();
                //subject3
                w.WriteStartElement("subject4");
                w.WriteAttributeString("typeCode", "SUBJ");
                w.WriteStartElement("mainSearchCriteria");
                w.WriteAttributeString("classCode", "OBS");
                w.WriteAttributeString("moodCode", "DEF");
                w.WriteStartElement("code");
                w.WriteAttributeString("code", "KSUBJ");
                w.WriteAttributeString("codeSytem", "2.16.840.1.113883.5.4");
                w.WriteAttributeString("codeSystemName", "ActCode");
                w.WriteAttributeString("displayName", "knowledge subject");
                w.WriteEndElement();
                //code
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
                w.WriteEndElement();
                //value
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
                w.WriteEndElement();
                //mainSearchCriteria
                w.WriteEndElement();
                //subject4
                w.WriteStartElement("componentOf");
                w.WriteAttributeString("typeCode", "COMP");
                w.WriteStartElement("encounter");
                w.WriteAttributeString("classCode", "ENC");
                w.WriteAttributeString("moodCode", "DEF");
                w.WriteStartElement("code");
                w.WriteAttributeString("code", encounterCodeHelper(ActEC));
                w.WriteAttributeString("codeSytem", "2.16.840.1.113883.5.4");
                w.WriteAttributeString("codeSystemName", "ActCode");
                w.WriteAttributeString("displayName", encounterCode(ActEC));
                w.WriteEndElement();
                //code
                if (!StringSupport.equals(textEncLocID.Text, ""))
                {
                    w.WriteStartElement("location");
                    w.WriteAttributeString("typeCode", "LOC");
                    w.WriteStartElement("serviceDeliveryLocation");
                    w.WriteAttributeString("typeCode", "SDLOC");
                    w.WriteAttributeString("id", textEncLocID.Text);
                    w.WriteEndElement();
                    //serviceDeliveryLocation
                    w.WriteEndElement();
                }
                 
                //location
                w.WriteEndElement();
                //encounter
                w.WriteEndElement();
                //componentOf
                w.WriteEndElement();
            }
        }
        finally
        {
            if (w != null)
                Disposable.mkDisposable(w).dispose();
             
        }
        return strBuilder.ToString();
    }

    //knowledgeRequestNotification
    private String knowledgeRequestIDAAHelper() throws Exception {
        if (!StringSupport.equals(PrefC.getString(PrefName.PracticeTitle), ""))
        {
            return PrefC.getString(PrefName.PracticeTitle);
        }
         
        return "Open Dental Software, version" + PrefC.getString(PrefName.ProgramVersion);
    }

    private String knowledgeRequestIDHelper() throws Exception {
        if (PatCur != null)
        {
            return "PT" + PatCur.PatNum + DateTime.Now.ToUniversalTime().ToString("yyyyMMddhhmmss");
        }
        else if (ProvCur != null)
        {
            return "PV" + ProvCur.ProvNum + DateTime.Now.ToUniversalTime().ToString("yyyyMMddhhmmss");
        }
        else
        {
            return "OD" + DateTime.Now.ToUniversalTime().ToString("yyyyMMddhhmmss");
        }  
    }

    private String encounterCodeHelper(ActEncounterCode aec) throws Exception {
        switch(aec)
        {
            case AMB: 
                return "AMB";
            case EMER: 
                return "EMER";
            case FLD: 
                return "FLD";
            case HH: 
                return "HH";
            case IMP: 
                return "IMP";
            case SS: 
                return "SS";
            case VR: 
                return "VR";
            default: 
                return "";
        
        }
    }

    private String encounterCode(ActEncounterCode aec) throws Exception {
        switch(aec)
        {
            case AMB: 
                return "ambulatory";
            case EMER: 
                return "emergency";
            case FLD: 
                return "field";
            case HH: 
                return "home health";
            case IMP: 
                return "inpatient encounter";
            case SS: 
                return "short stay";
            case VR: 
                return "virtual";
            default: 
                return "";
        
        }
    }

    public String observationInterpretationCodeHelper(ObservationInterpretationNormality oin) throws Exception {
        switch(oin)
        {
            case A: 
                return "A";
            case AA: 
                return "AA";
            case HH: 
                return "HH";
            case LL: 
                return "LL";
            case H: 
                return "H";
            case L: 
                return "L";
            case N: 
                return "N";
            default: 
                return "";
        
        }
    }

    public String observationInterpretationNameHelper(ObservationInterpretationNormality oin) throws Exception {
        switch(oin)
        {
            case A: 
                return "Abnormal";
            case AA: 
                return "Abnormal alert";
            case HH: 
                return "High alert";
            case LL: 
                return "Low alert";
            case H: 
                return "High";
            case L: 
                return "Low";
            case N: 
                return "Normal";
            default: 
                return "";
        
        }
    }

    /**
    * Returns thefirst level of ActTaskCode. OE, PATDOC, or PATINFO there are 35 total ActTaskCodes available.
    */
    public String actTaskCodeHelper() throws Exception {
        switch(ActTC)
        {
            case OE: 
                return "OE";
            case PATDOC: 
                return "PATDOC";
            case PATINFO: 
                return "PATINFO";
            default: 
                throw new NotImplementedException();
        
        }
    }

    /**
    * Returns thefirst level of ActTaskCode. OE, PATDOC, or PATINFO there are 35 total ActTaskCodes available.
    */
    public String actTaskCodeNameHelper() throws Exception {
        switch(ActTC)
        {
            case OE: 
                return "order entry task";
            case PATDOC: 
                return "patient documentation task";
            case PATINFO: 
                return "patient information review task";
            default: 
                throw new NotImplementedException();
        
        }
    }

    /**
    * Returns MeSH age group code based on birthdate. i.e. <2yrs==Infant==D007231
    */
    public String ageGroupCodeHelper(DateTime dateTime) throws Exception {
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
        if (PatCur.Birthdate.AddMonths(1) > DateTime.Now)
        {
            return "D007231";
        }
        else //less than 1mo old, newborn
        if (PatCur.Birthdate.AddYears(2) > DateTime.Now)
        {
            return "D007223";
        }
        else //less than 2 yrs old, Infant
        if (PatCur.Birthdate.AddYears(5) > DateTime.Now)
        {
            return "D007675";
        }
        else //2 to 5 yrs old, Preschool
        if (PatCur.Birthdate.AddYears(12) > DateTime.Now)
        {
            return "D002648";
        }
        else //6 to 12 yrs old, Child
        if (PatCur.Birthdate.AddYears(18) > DateTime.Now)
        {
            return "D000293";
        }
        else //13 to 18 yrs old, Adolescent
        if (PatCur.Birthdate.AddYears(44) > DateTime.Now)
        {
            return "D000328";
        }
        else //19 to 44 yrs old, Adult
        if (PatCur.Birthdate.AddYears(64) > DateTime.Now)
        {
            return "D008875";
        }
        else //45 to 64 yrs old, Middle Aged
        if (PatCur.Birthdate.AddYears(79) > DateTime.Now)
        {
            return "D000368";
        }
        else
        {
            return "D000369";
        }        
    }

    //65 to 79 yrs old, Aged
    //if(PatCur.Birthdate.AddYears(79)>DateTime.Now) {//80 yrs old or older, Aged, 80 and over
    /**
    * Returns MeSH age group name based on birthdate. i.e. <2yrs==Infant.
    */
    public String ageGroupNameHelper(DateTime dateTime) throws Exception {
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
        if (PatCur.Birthdate.AddMonths(1) > DateTime.Now)
        {
            return "Newborn";
        }
        else //less than 1mo old, newborn
        if (PatCur.Birthdate.AddYears(2) > DateTime.Now)
        {
            return "Infant";
        }
        else //less than 2 yrs old, Infant
        if (PatCur.Birthdate.AddYears(5) > DateTime.Now)
        {
            return "Preschool";
        }
        else //2 to 5 yrs old, Preschool
        if (PatCur.Birthdate.AddYears(12) > DateTime.Now)
        {
            return "Child";
        }
        else //6 to 12 yrs old, Child
        if (PatCur.Birthdate.AddYears(18) > DateTime.Now)
        {
            return "Adolescent";
        }
        else //13 to 18 yrs old, Adolescent
        if (PatCur.Birthdate.AddYears(44) > DateTime.Now)
        {
            return "Adult";
        }
        else //19 to 44 yrs old, Adult
        if (PatCur.Birthdate.AddYears(64) > DateTime.Now)
        {
            return "Middle Aged";
        }
        else //45 to 64 yrs old, Middle Aged
        if (PatCur.Birthdate.AddYears(79) > DateTime.Now)
        {
            return "Aged";
        }
        else
        {
            return "Aged, 80 and over";
        }        
    }

    //65 to 79 yrs old, Aged
    //if(PatCur.Birthdate.AddYears(79)>DateTime.Now) {//80 yrs old or older, Aged, 80 and over
    /**
    * The gender of a person used for adminstrative purposes (as opposed to clinical gender). Empty string/value is allowed.
    */
    public String administrativeGenderCodeHelper(PatientGender patientGender) throws Exception {
        switch(patientGender)
        {
            case Female: 
                return "F";
            case Male: 
                return "M";
            case Unknown: 
                return "UN";
            default: 
                return " ";
        
        }
    }

    //should never happen
    /**
    * The gender of a person used for adminstrative purposes (as opposed to clinical gender). Empty string/value is allowed.
    */
    public String administrativeGenderNameHelper(PatientGender patientGender) throws Exception {
        switch(patientGender)
        {
            case Female: 
                return "Female";
            case Male: 
                return "Male";
            case Unknown: 
                return "Undifferentiated";
            default: 
                return "";
        
        }
    }

    //should never happen
    private void comboEncType_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        ActEC = (ActEncounterCode)comboEncType.SelectedIndex;
    }

    private void comboTask_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        ActTC = (ActTaskCode)comboTask.SelectedIndex;
    }

    //private void butProbPick_Click(object sender,EventArgs e) {
    //	FormDiseaseDefs FormDD = new FormDiseaseDefs();
    //	FormDD.IsSelectionMode=true;
    //	FormDD.ShowDialog();
    //	if(FormDD.DialogResult!=DialogResult.OK) {
    //		return;
    //	}
    //	//ProblemCur=DiseaseDefs.GetItem(FormDD.SelectedDiseaseDefNum);
    //	//fillProblem();
    //}
    private void butAddDisease_Click(Object sender, EventArgs e) throws Exception {
        FormDiseaseDefs FormDD = new FormDiseaseDefs();
        FormDD.IsSelectionMode = true;
        FormDD.ShowDialog();
        if (FormDD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListObjects.Add(DiseaseDefs.getItem(FormDD.SelectedDiseaseDefNum));
        fillKnowledgeRequestitems();
    }

    private void butAddSnomed_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds FormS = new FormSnomeds();
        FormS.IsMultiSelectMode = true;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < FormS.ListSelectedSnomeds.Count;i++)
        {
            ListObjects.Add(FormS.ListSelectedSnomeds[i]);
        }
        fillKnowledgeRequestitems();
    }

    private void butAddRxNorm_Click(Object sender, EventArgs e) throws Exception {
        FormRxNorms FormRXN = new FormRxNorms();
        FormRXN.IsMultiSelectMode = true;
        FormRXN.ShowDialog();
        if (FormRXN.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        for (int i = 0;i < FormRXN.ListSelectedRxNorms.Count;i++)
        {
            ListObjects.Add(FormRXN.ListSelectedRxNorms[i]);
        }
        fillKnowledgeRequestitems();
    }

    private void butAddIcd9_Click(Object sender, EventArgs e) throws Exception {
        FormIcd9s FormI9 = new FormIcd9s();
        FormI9.IsSelectionMode = true;
        FormI9.ShowDialog();
        if (FormI9.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListObjects.Add(FormI9.SelectedIcd9);
        fillKnowledgeRequestitems();
    }

    private void butAddAllergy_Click(Object sender, EventArgs e) throws Exception {
        FormAllergySetup FormA = new FormAllergySetup();
        FormA.IsSelectionMode = true;
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListObjects.Add(AllergyDefs.getOne(FormA.SelectedAllergyDefNum));
        fillKnowledgeRequestitems();
    }

    private void butAddIcd10_Click(Object sender, EventArgs e) throws Exception {
        FormIcd10s FormI10 = new FormIcd10s();
        FormI10.IsSelectionMode = true;
        FormI10.ShowDialog();
        if (FormI10.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListObjects.Add(FormI10.SelectedIcd10);
        fillKnowledgeRequestitems();
    }

    private void butAddLoinc_Click(Object sender, EventArgs e) throws Exception {
        FormLoincs FormL = new FormLoincs();
        FormL.IsSelectionMode = true;
        FormL.ShowDialog();
        if (FormL.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListObjects.Add(FormL.SelectedLoinc);
        fillKnowledgeRequestitems();
    }

    private void butPreviewRequest_Click(Object sender, EventArgs e) throws Exception {
        KnowledgeRequestNotification.KnowledgeRequestNotification krn;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getTag() == null)
            {
                MsgBox.show(this,"Cannot search without a valid code.");
                continue;
            }
             
            krn = new KnowledgeRequestNotification.KnowledgeRequestNotification();
            krn.addObject(gridMain.getRows().get___idx(i).getTag());
            MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste("http://apps.nlm.nih.gov/medlineplus/services/mpconnect.cfm?" + krn.toUrl());
            msgbox.ShowDialog();
        }
    }

    //msgbox=new MsgBoxCopyPaste("http://apps2.nlm.nih.gov/medlineplus/services/servicedemo.cfm?"+krn.ToUrl());
    //msgbox.ShowDialog();
    //end gridMain.Rows
    private void butSend_Click(Object sender, EventArgs e) throws Exception {
        KnowledgeRequestNotification.KnowledgeRequestNotification krn;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getTag() == null)
            {
                MsgBox.show(this,"Cannot search without a valid code.");
                continue;
            }
             
            krn = new KnowledgeRequestNotification.KnowledgeRequestNotification();
            krn.addObject(gridMain.getRows().get___idx(i).getTag());
            krn.performerIsPatient = radioReqPat.Checked;
            krn.recipientIsPatient = radioRecPat.Checked;
            try
            {
                //FormInfobuttonResponse FormIR = new FormInfobuttonResponse();
                //FormIR.RawResponse=getWebResponse("http://apps2.nlm.nih.gov/medlineplus/services/mpconnect_service.cfm?"+krn.ToUrl());
                //FormIR.ShowDialog();
                //The lines commented out here launch the infobutton in the default browser.
                System.Diagnostics.Process.Start("http://apps.nlm.nih.gov/medlineplus/services/mpconnect.cfm?" + krn.toUrl());
            }
            catch (Exception ex)
            {
            }
        
        }
    }

    //System.Diagnostics.Process.Start("http://apps2.nlm.nih.gov/medlineplus/services/servicedemo.cfm?"+krn.ToUrl());
    //end gridMain.Rows
    /**
    * For More info goto: http://msdn.microsoft.com/en-us/library/456dfw4f(v=vs.110).aspx
    */
    private static String getWebResponse(String url) throws Exception {
        // Create a request for the URL.
        WebRequest request = WebRequest.Create(url);
        // If required by the server, set the credentials.
        //request.Credentials = CredentialCache.DefaultCredentials;
        // Get the response.
        WebResponse response = request.GetResponse();
        // Display the status.
        Console.WriteLine(((HttpWebResponse)response).StatusDescription);
        // Get the stream containing content returned by the server.
        Stream dataStream = response.GetResponseStream();
        // Open the stream using a StreamReader for easy access.
        StreamReader reader = new StreamReader(dataStream);
        // Read the content.
        String responseFromServer = reader.ReadToEnd();
        // Clean up the streams and the response.
        reader.Close();
        response.Close();
        return responseFromServer;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void groupBoxContext_Enter(Object sender, EventArgs e) throws Exception {
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.groupBoxContext = new System.Windows.Forms.GroupBox();
        this.groupBox7 = new System.Windows.Forms.GroupBox();
        this.textOrgID = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textOrgName = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.butProvSelect = new OpenDental.UI.Button();
        this.comboProvLang = new System.Windows.Forms.ComboBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textProvID = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textProvName = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.textEncLocID = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.comboEncType = new System.Windows.Forms.ComboBox();
        this.label26 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.butPatSelect = new OpenDental.UI.Button();
        this.comboPatLang = new System.Windows.Forms.ComboBox();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.radioPatGenUn = new System.Windows.Forms.RadioButton();
        this.radioPatGenFem = new System.Windows.Forms.RadioButton();
        this.radioPatGenMale = new System.Windows.Forms.RadioButton();
        this.label4 = new System.Windows.Forms.Label();
        this.textPatBirth = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textPatName = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.radioRecPat = new System.Windows.Forms.RadioButton();
        this.radioRecProv = new System.Windows.Forms.RadioButton();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radioReqPat = new System.Windows.Forms.RadioButton();
        this.radioReqProv = new System.Windows.Forms.RadioButton();
        this.comboTask = new System.Windows.Forms.ComboBox();
        this.label17 = new System.Windows.Forms.Label();
        this.butAddLoinc = new OpenDental.UI.Button();
        this.butAddIcd10 = new OpenDental.UI.Button();
        this.button3 = new OpenDental.UI.Button();
        this.butAddIcd9 = new OpenDental.UI.Button();
        this.butAddSnomed = new OpenDental.UI.Button();
        this.butAddAllergy = new OpenDental.UI.Button();
        this.butAddDisease = new OpenDental.UI.Button();
        this.butAddRxNorm = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butPreviewRequest = new OpenDental.UI.Button();
        this.butSend = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butPreview = new OpenDental.UI.Button();
        this.groupBoxContext.SuspendLayout();
        this.groupBox7.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBoxContext
        //
        this.groupBoxContext.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBoxContext.Controls.Add(this.groupBox7);
        this.groupBoxContext.Controls.Add(this.groupBox6);
        this.groupBoxContext.Controls.Add(this.groupBox5);
        this.groupBoxContext.Controls.Add(this.groupBox3);
        this.groupBoxContext.Controls.Add(this.groupBox2);
        this.groupBoxContext.Controls.Add(this.groupBox1);
        this.groupBoxContext.Controls.Add(this.comboTask);
        this.groupBoxContext.Controls.Add(this.label17);
        this.groupBoxContext.Location = new System.Drawing.Point(12, 12);
        this.groupBoxContext.Name = "groupBoxContext";
        this.groupBoxContext.Size = new System.Drawing.Size(564, 337);
        this.groupBoxContext.TabIndex = 4;
        this.groupBoxContext.TabStop = false;
        this.groupBoxContext.Text = "Context";
        this.groupBoxContext.Enter += new System.EventHandler(this.groupBoxContext_Enter);
        //
        // groupBox7
        //
        this.groupBox7.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox7.Controls.Add(this.textOrgID);
        this.groupBox7.Controls.Add(this.label9);
        this.groupBox7.Controls.Add(this.textOrgName);
        this.groupBox7.Controls.Add(this.label10);
        this.groupBox7.Location = new System.Drawing.Point(285, 115);
        this.groupBox7.Name = "groupBox7";
        this.groupBox7.Size = new System.Drawing.Size(273, 88);
        this.groupBox7.TabIndex = 171;
        this.groupBox7.TabStop = false;
        this.groupBox7.Text = "Organization";
        //
        // textOrgID
        //
        this.textOrgID.Location = new System.Drawing.Point(124, 38);
        this.textOrgID.Name = "textOrgID";
        this.textOrgID.Size = new System.Drawing.Size(137, 20);
        this.textOrgID.TabIndex = 167;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(27, 42);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(94, 16);
        this.label9.TabIndex = 168;
        this.label9.Text = "Organization ID";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textOrgName
        //
        this.textOrgName.Location = new System.Drawing.Point(124, 15);
        this.textOrgName.Name = "textOrgName";
        this.textOrgName.Size = new System.Drawing.Size(137, 20);
        this.textOrgName.TabIndex = 165;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(27, 19);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(94, 16);
        this.label10.TabIndex = 166;
        this.label10.Text = "Name";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox6
        //
        this.groupBox6.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox6.Controls.Add(this.butProvSelect);
        this.groupBox6.Controls.Add(this.comboProvLang);
        this.groupBox6.Controls.Add(this.label7);
        this.groupBox6.Controls.Add(this.textProvID);
        this.groupBox6.Controls.Add(this.label6);
        this.groupBox6.Controls.Add(this.textProvName);
        this.groupBox6.Controls.Add(this.label5);
        this.groupBox6.Location = new System.Drawing.Point(8, 115);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(273, 88);
        this.groupBox6.TabIndex = 164;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Provider";
        //
        // butProvSelect
        //
        this.butProvSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvSelect.setAutosize(true);
        this.butProvSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvSelect.setCornerRadius(4F);
        this.butProvSelect.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvSelect.Location = new System.Drawing.Point(9, 19);
        this.butProvSelect.Name = "butProvSelect";
        this.butProvSelect.Size = new System.Drawing.Size(29, 25);
        this.butProvSelect.TabIndex = 174;
        this.butProvSelect.Text = "...";
        //
        // comboProvLang
        //
        this.comboProvLang.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvLang.Location = new System.Drawing.Point(124, 61);
        this.comboProvLang.MaxDropDownItems = 30;
        this.comboProvLang.Name = "comboProvLang";
        this.comboProvLang.Size = new System.Drawing.Size(137, 21);
        this.comboProvLang.TabIndex = 171;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(27, 65);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(94, 16);
        this.label7.TabIndex = 170;
        this.label7.Text = "Language";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textProvID
        //
        this.textProvID.Location = new System.Drawing.Point(124, 38);
        this.textProvID.Name = "textProvID";
        this.textProvID.Size = new System.Drawing.Size(137, 20);
        this.textProvID.TabIndex = 167;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(27, 42);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(94, 16);
        this.label6.TabIndex = 168;
        this.label6.Text = "Provider ID";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textProvName
        //
        this.textProvName.Location = new System.Drawing.Point(124, 15);
        this.textProvName.Name = "textProvName";
        this.textProvName.Size = new System.Drawing.Size(137, 20);
        this.textProvName.TabIndex = 165;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(27, 19);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(94, 16);
        this.label5.TabIndex = 166;
        this.label5.Text = "Name";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox5
        //
        this.groupBox5.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox5.Controls.Add(this.textEncLocID);
        this.groupBox5.Controls.Add(this.label8);
        this.groupBox5.Controls.Add(this.comboEncType);
        this.groupBox5.Controls.Add(this.label26);
        this.groupBox5.Location = new System.Drawing.Point(8, 203);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(550, 66);
        this.groupBox5.TabIndex = 163;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Encounter";
        //
        // textEncLocID
        //
        this.textEncLocID.Location = new System.Drawing.Point(170, 37);
        this.textEncLocID.Name = "textEncLocID";
        this.textEncLocID.Size = new System.Drawing.Size(137, 20);
        this.textEncLocID.TabIndex = 169;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(6, 41);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(161, 16);
        this.label8.TabIndex = 170;
        this.label8.Text = "Service Delivery Location ID";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboEncType
        //
        this.comboEncType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboEncType.Location = new System.Drawing.Point(170, 13);
        this.comboEncType.MaxDropDownItems = 30;
        this.comboEncType.Name = "comboEncType";
        this.comboEncType.Size = new System.Drawing.Size(151, 21);
        this.comboEncType.TabIndex = 128;
        this.comboEncType.SelectedIndexChanged += new System.EventHandler(this.comboEncType_SelectedIndexChanged);
        //
        // label26
        //
        this.label26.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label26.Location = new System.Drawing.Point(6, 17);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(162, 14);
        this.label26.TabIndex = 129;
        this.label26.Text = "Type";
        this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox3
        //
        this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox3.Controls.Add(this.butPatSelect);
        this.groupBox3.Controls.Add(this.comboPatLang);
        this.groupBox3.Controls.Add(this.groupBox4);
        this.groupBox3.Controls.Add(this.label4);
        this.groupBox3.Controls.Add(this.textPatBirth);
        this.groupBox3.Controls.Add(this.label3);
        this.groupBox3.Controls.Add(this.textPatName);
        this.groupBox3.Controls.Add(this.label1);
        this.groupBox3.Location = new System.Drawing.Point(8, 19);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(550, 96);
        this.groupBox3.TabIndex = 163;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Patient";
        //
        // butPatSelect
        //
        this.butPatSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPatSelect.setAutosize(true);
        this.butPatSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPatSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPatSelect.setCornerRadius(4F);
        this.butPatSelect.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPatSelect.Location = new System.Drawing.Point(9, 19);
        this.butPatSelect.Name = "butPatSelect";
        this.butPatSelect.Size = new System.Drawing.Size(29, 25);
        this.butPatSelect.TabIndex = 173;
        this.butPatSelect.Text = "...";
        //
        // comboPatLang
        //
        this.comboPatLang.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPatLang.Location = new System.Drawing.Point(124, 64);
        this.comboPatLang.MaxDropDownItems = 30;
        this.comboPatLang.Name = "comboPatLang";
        this.comboPatLang.Size = new System.Drawing.Size(197, 21);
        this.comboPatLang.TabIndex = 172;
        //
        // groupBox4
        //
        this.groupBox4.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox4.Controls.Add(this.radioPatGenUn);
        this.groupBox4.Controls.Add(this.radioPatGenFem);
        this.groupBox4.Controls.Add(this.radioPatGenMale);
        this.groupBox4.Location = new System.Drawing.Point(327, 11);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(217, 74);
        this.groupBox4.TabIndex = 162;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Administrative Gender";
        //
        // radioPatGenUn
        //
        this.radioPatGenUn.Location = new System.Drawing.Point(6, 51);
        this.radioPatGenUn.Name = "radioPatGenUn";
        this.radioPatGenUn.Size = new System.Drawing.Size(205, 17);
        this.radioPatGenUn.TabIndex = 162;
        this.radioPatGenUn.Text = "Undifferentiated";
        this.radioPatGenUn.UseVisualStyleBackColor = true;
        //
        // radioPatGenFem
        //
        this.radioPatGenFem.Checked = true;
        this.radioPatGenFem.Location = new System.Drawing.Point(6, 15);
        this.radioPatGenFem.Name = "radioPatGenFem";
        this.radioPatGenFem.Size = new System.Drawing.Size(205, 17);
        this.radioPatGenFem.TabIndex = 161;
        this.radioPatGenFem.TabStop = true;
        this.radioPatGenFem.Text = "Female";
        this.radioPatGenFem.UseVisualStyleBackColor = true;
        //
        // radioPatGenMale
        //
        this.radioPatGenMale.Location = new System.Drawing.Point(6, 32);
        this.radioPatGenMale.Name = "radioPatGenMale";
        this.radioPatGenMale.Size = new System.Drawing.Size(205, 17);
        this.radioPatGenMale.TabIndex = 160;
        this.radioPatGenMale.Text = "Male";
        this.radioPatGenMale.UseVisualStyleBackColor = true;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(27, 69);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(94, 16);
        this.label4.TabIndex = 168;
        this.label4.Text = "Language";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPatBirth
        //
        this.textPatBirth.Location = new System.Drawing.Point(124, 42);
        this.textPatBirth.Name = "textPatBirth";
        this.textPatBirth.Size = new System.Drawing.Size(197, 20);
        this.textPatBirth.TabIndex = 165;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(27, 46);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(94, 16);
        this.label3.TabIndex = 166;
        this.label3.Text = "Birthday";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPatName
        //
        this.textPatName.Location = new System.Drawing.Point(124, 19);
        this.textPatName.Name = "textPatName";
        this.textPatName.Size = new System.Drawing.Size(197, 20);
        this.textPatName.TabIndex = 163;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(27, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(94, 16);
        this.label1.TabIndex = 164;
        this.label1.Text = "Name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.radioRecPat);
        this.groupBox2.Controls.Add(this.radioRecProv);
        this.groupBox2.Location = new System.Drawing.Point(150, 275);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(136, 54);
        this.groupBox2.TabIndex = 162;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Recipient";
        //
        // radioRecPat
        //
        this.radioRecPat.Checked = true;
        this.radioRecPat.Location = new System.Drawing.Point(6, 15);
        this.radioRecPat.Name = "radioRecPat";
        this.radioRecPat.Size = new System.Drawing.Size(124, 17);
        this.radioRecPat.TabIndex = 161;
        this.radioRecPat.TabStop = true;
        this.radioRecPat.Text = "Patient";
        this.radioRecPat.UseVisualStyleBackColor = true;
        //
        // radioRecProv
        //
        this.radioRecProv.Location = new System.Drawing.Point(6, 32);
        this.radioRecProv.Name = "radioRecProv";
        this.radioRecProv.Size = new System.Drawing.Size(124, 17);
        this.radioRecProv.TabIndex = 160;
        this.radioRecProv.Text = "Provider";
        this.radioRecProv.UseVisualStyleBackColor = true;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radioReqPat);
        this.groupBox1.Controls.Add(this.radioReqProv);
        this.groupBox1.Location = new System.Drawing.Point(8, 275);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(136, 54);
        this.groupBox1.TabIndex = 5;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Requestor";
        //
        // radioReqPat
        //
        this.radioReqPat.Checked = true;
        this.radioReqPat.Location = new System.Drawing.Point(6, 15);
        this.radioReqPat.Name = "radioReqPat";
        this.radioReqPat.Size = new System.Drawing.Size(124, 17);
        this.radioReqPat.TabIndex = 161;
        this.radioReqPat.TabStop = true;
        this.radioReqPat.Text = "Patient";
        this.radioReqPat.UseVisualStyleBackColor = true;
        //
        // radioReqProv
        //
        this.radioReqProv.Location = new System.Drawing.Point(6, 32);
        this.radioReqProv.Name = "radioReqProv";
        this.radioReqProv.Size = new System.Drawing.Size(124, 17);
        this.radioReqProv.TabIndex = 160;
        this.radioReqProv.Text = "Provider";
        this.radioReqProv.UseVisualStyleBackColor = true;
        //
        // comboTask
        //
        this.comboTask.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboTask.Location = new System.Drawing.Point(409, 280);
        this.comboTask.MaxDropDownItems = 30;
        this.comboTask.Name = "comboTask";
        this.comboTask.Size = new System.Drawing.Size(149, 21);
        this.comboTask.TabIndex = 128;
        this.comboTask.SelectedIndexChanged += new System.EventHandler(this.comboTask_SelectedIndexChanged);
        //
        // label17
        //
        this.label17.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label17.Location = new System.Drawing.Point(327, 282);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(76, 14);
        this.label17.TabIndex = 129;
        this.label17.Text = "Task Code";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butAddLoinc
        //
        this.butAddLoinc.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddLoinc.setAutosize(true);
        this.butAddLoinc.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddLoinc.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddLoinc.setCornerRadius(4F);
        this.butAddLoinc.Image = Resources.getAdd();
        this.butAddLoinc.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddLoinc.Location = new System.Drawing.Point(311, 384);
        this.butAddLoinc.Name = "butAddLoinc";
        this.butAddLoinc.Size = new System.Drawing.Size(94, 23);
        this.butAddLoinc.TabIndex = 207;
        this.butAddLoinc.Text = "Loinc";
        this.butAddLoinc.Click += new System.EventHandler(this.butAddLoinc_Click);
        //
        // butAddIcd10
        //
        this.butAddIcd10.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddIcd10.setAutosize(true);
        this.butAddIcd10.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddIcd10.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddIcd10.setCornerRadius(4F);
        this.butAddIcd10.Image = Resources.getAdd();
        this.butAddIcd10.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddIcd10.Location = new System.Drawing.Point(212, 384);
        this.butAddIcd10.Name = "butAddIcd10";
        this.butAddIcd10.Size = new System.Drawing.Size(94, 23);
        this.butAddIcd10.TabIndex = 206;
        this.butAddIcd10.Text = "Icd10";
        this.butAddIcd10.Click += new System.EventHandler(this.butAddIcd10_Click);
        //
        // button3
        //
        this.button3.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.button3.setAutosize(true);
        this.button3.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button3.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button3.setCornerRadius(4F);
        this.button3.Image = Resources.getAdd();
        this.button3.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.button3.Location = new System.Drawing.Point(312, 355);
        this.button3.Name = "button3";
        this.button3.Size = new System.Drawing.Size(94, 23);
        this.button3.TabIndex = 205;
        this.button3.Text = "Lab Result";
        this.button3.Visible = false;
        //
        // butAddIcd9
        //
        this.butAddIcd9.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddIcd9.setAutosize(true);
        this.butAddIcd9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddIcd9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddIcd9.setCornerRadius(4F);
        this.butAddIcd9.Image = Resources.getAdd();
        this.butAddIcd9.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddIcd9.Location = new System.Drawing.Point(112, 384);
        this.butAddIcd9.Name = "butAddIcd9";
        this.butAddIcd9.Size = new System.Drawing.Size(94, 23);
        this.butAddIcd9.TabIndex = 204;
        this.butAddIcd9.Text = "Icd9";
        this.butAddIcd9.Click += new System.EventHandler(this.butAddIcd9_Click);
        //
        // butAddSnomed
        //
        this.butAddSnomed.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddSnomed.setAutosize(true);
        this.butAddSnomed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddSnomed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddSnomed.setCornerRadius(4F);
        this.butAddSnomed.Image = Resources.getAdd();
        this.butAddSnomed.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddSnomed.Location = new System.Drawing.Point(12, 384);
        this.butAddSnomed.Name = "butAddSnomed";
        this.butAddSnomed.Size = new System.Drawing.Size(96, 23);
        this.butAddSnomed.TabIndex = 203;
        this.butAddSnomed.Text = "SNOMEDCT";
        this.butAddSnomed.Click += new System.EventHandler(this.butAddSnomed_Click);
        //
        // butAddAllergy
        //
        this.butAddAllergy.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddAllergy.setAutosize(true);
        this.butAddAllergy.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddAllergy.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddAllergy.setCornerRadius(4F);
        this.butAddAllergy.Image = Resources.getAdd();
        this.butAddAllergy.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddAllergy.Location = new System.Drawing.Point(212, 355);
        this.butAddAllergy.Name = "butAddAllergy";
        this.butAddAllergy.Size = new System.Drawing.Size(94, 23);
        this.butAddAllergy.TabIndex = 202;
        this.butAddAllergy.Text = "Allergy";
        this.butAddAllergy.Click += new System.EventHandler(this.butAddAllergy_Click);
        //
        // butAddDisease
        //
        this.butAddDisease.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddDisease.setAutosize(true);
        this.butAddDisease.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddDisease.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddDisease.setCornerRadius(4F);
        this.butAddDisease.Image = Resources.getAdd();
        this.butAddDisease.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddDisease.Location = new System.Drawing.Point(12, 355);
        this.butAddDisease.Name = "butAddDisease";
        this.butAddDisease.Size = new System.Drawing.Size(94, 23);
        this.butAddDisease.TabIndex = 200;
        this.butAddDisease.Text = "Problem";
        this.butAddDisease.Click += new System.EventHandler(this.butAddDisease_Click);
        //
        // butAddRxNorm
        //
        this.butAddRxNorm.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddRxNorm.setAutosize(true);
        this.butAddRxNorm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddRxNorm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddRxNorm.setCornerRadius(4F);
        this.butAddRxNorm.Image = Resources.getAdd();
        this.butAddRxNorm.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddRxNorm.Location = new System.Drawing.Point(112, 355);
        this.butAddRxNorm.Name = "butAddRxNorm";
        this.butAddRxNorm.Size = new System.Drawing.Size(94, 23);
        this.butAddRxNorm.TabIndex = 201;
        this.butAddRxNorm.Text = "RxNorm";
        this.butAddRxNorm.Click += new System.EventHandler(this.butAddRxNorm_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 413);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(564, 132);
        this.gridMain.TabIndex = 199;
        this.gridMain.setTitle("Knowledge Request Items");
        this.gridMain.setTranslationName("");
        this.gridMain.setWrapText(false);
        //
        // butPreviewRequest
        //
        this.butPreviewRequest.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreviewRequest.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPreviewRequest.setAutosize(true);
        this.butPreviewRequest.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreviewRequest.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreviewRequest.setCornerRadius(4F);
        this.butPreviewRequest.Location = new System.Drawing.Point(214, 553);
        this.butPreviewRequest.Name = "butPreviewRequest";
        this.butPreviewRequest.Size = new System.Drawing.Size(127, 24);
        this.butPreviewRequest.TabIndex = 198;
        this.butPreviewRequest.Text = "Preview Request";
        this.butPreviewRequest.Click += new System.EventHandler(this.butPreviewRequest_Click);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(420, 553);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 24);
        this.butSend.TabIndex = 3;
        this.butSend.Text = "&Send";
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(501, 553);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butPreview
        //
        this.butPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPreview.setAutosize(true);
        this.butPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreview.setCornerRadius(4F);
        this.butPreview.Location = new System.Drawing.Point(12, 553);
        this.butPreview.Name = "butPreview";
        this.butPreview.Size = new System.Drawing.Size(127, 24);
        this.butPreview.TabIndex = 8;
        this.butPreview.Text = "&Preview XML";
        this.butPreview.Visible = false;
        this.butPreview.Click += new System.EventHandler(this.butPreview_Click);
        //
        // FormInfobutton
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(588, 584);
        this.Controls.Add(this.butAddLoinc);
        this.Controls.Add(this.butAddIcd10);
        this.Controls.Add(this.button3);
        this.Controls.Add(this.butAddIcd9);
        this.Controls.Add(this.butAddSnomed);
        this.Controls.Add(this.butAddAllergy);
        this.Controls.Add(this.butAddDisease);
        this.Controls.Add(this.butAddRxNorm);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butPreviewRequest);
        this.Controls.Add(this.butPreview);
        this.Controls.Add(this.groupBoxContext);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.butCancel);
        this.Name = "FormInfobutton";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "InfoButton Portal";
        this.Load += new System.EventHandler(this.FormInfobutton_Load);
        this.groupBoxContext.ResumeLayout(false);
        this.groupBox7.ResumeLayout(false);
        this.groupBox7.PerformLayout();
        this.groupBox6.ResumeLayout(false);
        this.groupBox6.PerformLayout();
        this.groupBox5.ResumeLayout(false);
        this.groupBox5.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.groupBox4.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butSend;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.GroupBox groupBoxContext = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioReqPat = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioReqProv = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioRecPat = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioRecProv = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupBox7 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textOrgID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textOrgName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox6 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProvID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProvName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioPatGenUn = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioPatGenFem = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioPatGenMale = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatBirth = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textEncLocID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboEncType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label26 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProvLang = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPatLang = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboTask = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label17 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butPreviewRequest;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAddDisease;
    private OpenDental.UI.Button butAddRxNorm;
    private OpenDental.UI.Button butAddAllergy;
    private OpenDental.UI.Button butPatSelect;
    private OpenDental.UI.Button butAddSnomed;
    private OpenDental.UI.Button butAddIcd9;
    private OpenDental.UI.Button button3;
    private OpenDental.UI.Button butAddIcd10;
    private OpenDental.UI.Button butAddLoinc;
    private OpenDental.UI.Button butProvSelect;
    private OpenDental.UI.Button butPreview;
}


