//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormCDSIntervention;
import OpenDental.FormDiseaseDefs;
import OpenDental.FormDiseaseEdit;
import OpenDental.FormEhrNotPerformedEdit;
import OpenDental.FormEhrSettings;
import OpenDental.FormInterventionEdit;
import OpenDental.FormMedPat;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.Cpt;
import OpenDentBusiness.Cpts;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EhrCode;
import OpenDentBusiness.EhrCodes;
import OpenDentBusiness.EhrNotPerformed;
import OpenDentBusiness.EhrNotPerformedItem;
import OpenDentBusiness.EhrNotPerformeds;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Hcpcses;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Intervention;
import OpenDentBusiness.InterventionCodeSet;
import OpenDentBusiness.Interventions;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormVitalsignEdit2014;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormVitalsignEdit2014  extends Form 
{

    public Vitalsign VitalsignCur;
    private Patient patCur;
    public int ageBeforeJanFirst = new int();
    private List<Loinc> listHeightCodes = new List<Loinc>();
    private List<Loinc> listWeightCodes = new List<Loinc>();
    private List<Loinc> listBMICodes = new List<Loinc>();
    private long pregDisDefNumCur = new long();
    //used to keep track of the def we will update VitalsignCur.PregDiseaseNum with
    private String pregDefaultText = new String();
    private String pregManualText = new String();
    private InterventionCodeSet intervCodeSet = InterventionCodeSet.AboveNormalWeight;
    private Dictionary<String, List<float>> _dictLMS = new Dictionary<String, List<float>>();
    public FormVitalsignEdit2014() throws Exception {
        initializeComponent();
    }

    private void formVitalsignEdit2014_Load(Object sender, EventArgs e) throws Exception {
        fillDictLMS();
        pregDefaultText = "A diagnosis of pregnancy with this code will be added to the patient's medical history with a start date equal to the date of this exam.";
        pregManualText = "Selecting a code that is not in the recommended list of pregnancy codes may not exclude this patient from certain CQM calculations.";
        labelPregNotice.Text = pregDefaultText;
        pregDisDefNumCur = 0;
        groupInterventions.Visible = false;
        patCur = Patients.getPat(VitalsignCur.PatNum);
        textDateTaken.Text = VitalsignCur.DateTaken.ToShortDateString();
        ageBeforeJanFirst = PIn.Date(textDateTaken.Text).Year - patCur.Birthdate.Year - 1;
        textHeight.Text = VitalsignCur.Height.ToString();
        textWeight.Text = VitalsignCur.Weight.ToString();
        calcBMI();
        textBPd.Text = VitalsignCur.BpDiastolic.ToString();
        textBPs.Text = VitalsignCur.BpSystolic.ToString();
        listHeightCodes = new List<Loinc>();
        listWeightCodes = new List<Loinc>();
        listBMICodes = new List<Loinc>();
        fillBMICodeLists();
        textBMIPercentileCode.Text = VitalsignCur.BMIExamCode;
        //Only ever going to be blank or LOINC 59576-9 - Body mass index (BMI) [Percentile] Per age and gender
        comboHeightExamCode.Items.Add("none");
        comboHeightExamCode.SelectedIndex = 0;
        for (int i = 0;i < listHeightCodes.Count;i++)
        {
            if (StringSupport.equals(listHeightCodes[i].NameShort, "") || listHeightCodes[i].NameLongCommon.Length < 30)
            {
                //30 is roughly the number of characters that will fit in the combo box
                comboHeightExamCode.Items.Add(listHeightCodes[i].NameLongCommon);
            }
            else
            {
                comboHeightExamCode.Items.Add(listHeightCodes[i].NameShort);
            } 
            if (i == 0 || StringSupport.equals(VitalsignCur.HeightExamCode, listHeightCodes[i].LoincCode))
            {
                comboHeightExamCode.SelectedIndex = i + 1;
            }
             
        }
        comboWeightExamCode.Items.Add("none");
        comboWeightExamCode.SelectedIndex = 0;
        for (int i = 0;i < listWeightCodes.Count;i++)
        {
            if (StringSupport.equals(listWeightCodes[i].NameShort, "") || listWeightCodes[i].NameLongCommon.Length < 30)
            {
                //30 is roughly the number of characters that will fit in the combo box
                comboWeightExamCode.Items.Add(listWeightCodes[i].NameLongCommon);
            }
            else
            {
                comboWeightExamCode.Items.Add(listWeightCodes[i].NameShort);
            } 
            if (i == 0 || StringSupport.equals(VitalsignCur.WeightExamCode, listWeightCodes[i].LoincCode))
            {
                comboWeightExamCode.SelectedIndex = i + 1;
            }
             
        }
        if (VitalsignCur.PregDiseaseNum > 0)
        {
            checkPregnant.Checked = true;
            setPregCodeAndDescript();
            //if after this function disdefNumCur=0, the PregDiseaseNum is pointing to an invalid disease or diseasedef, or the default is set to 'none'
            if (VitalsignCur.PregDiseaseNum == 0)
            {
                checkPregnant.Checked = false;
                textPregCode.Clear();
                textPregCodeDescript.Clear();
                labelPregNotice.Visible = false;
            }
            else if (pregDisDefNumCur > 0)
            {
                labelPregNotice.Visible = true;
                butChangeDefault.Text = "Go to Problem";
            }
              
        }
         
        if (VitalsignCur.EhrNotPerformedNum > 0)
        {
            checkNotPerf.Checked = true;
            EhrNotPerformed notPerfCur = EhrNotPerformeds.getOne(VitalsignCur.EhrNotPerformedNum);
            if (notPerfCur == null)
            {
                VitalsignCur.EhrNotPerformedNum = 0;
                //if this vital sign is pointing to an EhrNotPerformed item that no longer exists, we will just remove the pointer
                checkNotPerf.Checked = false;
            }
            else
            {
                textReasonCode.Text = notPerfCur.CodeValueReason;
                //all reasons not performed are snomed codes
                Snomed sCur = Snomeds.getByCode(notPerfCur.CodeValueReason);
                if (sCur != null)
                {
                    textReasonDescript.Text = sCur.Description;
                }
                 
            } 
        }
         
    }

    /**
    * Sets the pregnancy code and description text box with either the attached pregnancy dx if exists or the default preg dx set in FormEhrSettings or a manually selected def.  If the pregnancy diseasedef with the default pregnancy code and code system does not exist, it will be inserted.  The pregnancy problem will be inserted when closing if necessary.
    */
    private void setPregCodeAndDescript() throws Exception {
        labelPregNotice.Text = pregDefaultText;
        pregDisDefNumCur = 0;
        //this will be set to the correct problem def at the end of this function and will be the def of the problem we will insert/attach this exam to
        String pregCode = "";
        String descript = "";
        Disease disCur = null;
        DiseaseDef disdefCur = null;
        DateTime examDate = PIn.Date(textDateTaken.Text);
        //this may be different than the saved Vitalsign.DateTaken if user edited
        if (VitalsignCur.PregDiseaseNum > 0)
        {
            //already pointing to a disease, get that one
            disCur = Diseases.getOne(VitalsignCur.PregDiseaseNum);
            //get disease this vital sign is pointing to, see if it exists
            if (disCur == null)
            {
                VitalsignCur.PregDiseaseNum = 0;
            }
            else
            {
                if (examDate.Year < 1880 || disCur.DateStart > examDate.Date || (disCur.DateStop.Year > 1880 && disCur.DateStop < examDate.Date))
                {
                    VitalsignCur.PregDiseaseNum = 0;
                    disCur = null;
                }
                else
                {
                    disdefCur = DiseaseDefs.getItem(disCur.DiseaseDefNum);
                    if (disdefCur == null)
                    {
                        VitalsignCur.PregDiseaseNum = 0;
                        disCur = null;
                    }
                    else
                    {
                        //disease points to valid def
                        pregDisDefNumCur = disdefCur.DiseaseDefNum;
                    } 
                } 
            } 
        }
         
        if (VitalsignCur.PregDiseaseNum == 0)
        {
            //not currently attached to a disease
            if (examDate.Year > 1880)
            {
                //only try to find existing problem if a valid exam date is entered before checking the box, otherwise we do not know what date to compare to the active dates of the pregnancy dx
                List<DiseaseDef> listPregDisDefs = DiseaseDefs.getAllPregDiseaseDefs();
                List<Disease> listPatDiseases = Diseases.refresh(VitalsignCur.PatNum,true);
                for (int i = 0;i < listPatDiseases.Count;i++)
                {
                    //loop through all diseases for this patient, shouldn't be very many
                    //startdate for current disease is after the exam date set in form
                    if (listPatDiseases[i].DateStart > examDate.Date || (listPatDiseases[i].DateStop.Year > 1880 && listPatDiseases[i].DateStop < examDate.Date))
                    {
                        continue;
                    }
                     
                    for (int j = 0;j < listPregDisDefs.Count;j++)
                    {
                        //or current disease has a stop date and stop date before exam date
                        //loop through preg disease defs in the db, shouldn't be very many
                        if (listPatDiseases[i].DiseaseDefNum != listPregDisDefs[j].DiseaseDefNum)
                        {
                            continue;
                        }
                         
                        //see if this problem is a pregnancy problem
                        if (disCur == null || listPatDiseases[i].DateStart > disCur.DateStart)
                        {
                            //if we haven't found a disease match yet or this match is more recent (later start date)
                            disCur = listPatDiseases[i];
                            break;
                        }
                         
                    }
                }
            }
             
            if (disCur != null)
            {
                pregDisDefNumCur = disCur.DiseaseDefNum;
                VitalsignCur.PregDiseaseNum = disCur.DiseaseNum;
            }
            else
            {
                //we are going to insert either the default pregnancy problem or a manually selected problem
                //if preg dx doesn't exist, use the default pregnancy code if set to something other than blank or 'none'
                pregCode = PrefC.getString(PrefName.PregnancyDefaultCodeValue);
                //could be 'none' which disables the automatic dx insertion
                String pregCodeSys = PrefC.getString(PrefName.PregnancyDefaultCodeSystem);
                //if 'none' for code, code system will default to 'SNOMEDCT', display will be ""
                if (!StringSupport.equals(pregCode, "") && !StringSupport.equals(pregCode, "none"))
                {
                    //default pregnancy code set to a code other than 'none', should never be blank, we set in ConvertDB and don't allow blank
                    pregDisDefNumCur = DiseaseDefs.getNumFromCode(pregCode);
                    //see if the code is attached to a valid diseasedef
                    if (pregDisDefNumCur == 0)
                    {
                        //no diseasedef in db for the default code, create and insert def
                        disdefCur = new DiseaseDef();
                        disdefCur.DiseaseName = "Pregnant";
                        System.String __dummyScrutVar0 = pregCodeSys;
                        if (__dummyScrutVar0.equals("ICD9CM"))
                        {
                            disdefCur.ICD9Code = pregCode;
                        }
                        else if (__dummyScrutVar0.equals("ICD10CM"))
                        {
                            disdefCur.Icd10Code = pregCode;
                        }
                        else if (__dummyScrutVar0.equals("SNOMEDCT"))
                        {
                            disdefCur.SnomedCode = pregCode;
                        }
                           
                        pregDisDefNumCur = DiseaseDefs.insert(disdefCur);
                        DiseaseDefs.refreshCache();
                        DataValid.setInvalid(InvalidType.Diseases);
                        SecurityLogs.MakeLogEntry(Permissions.ProblemEdit, 0, disdefCur.DiseaseName + " added.");
                    }
                     
                }
                else if (StringSupport.equals(pregCode, "none"))
                {
                    //if pref for default preg dx is 'none', make user choose a problem from list
                    FormDiseaseDefs FormDD = new FormDiseaseDefs();
                    FormDD.IsSelectionMode = true;
                    FormDD.IsMultiSelect = false;
                    FormDD.ShowDialog();
                    if (FormDD.DialogResult != DialogResult.OK)
                    {
                        checkPregnant.Checked = false;
                        textPregCode.Clear();
                        textPregCodeDescript.Clear();
                        labelPregNotice.Visible = false;
                        butChangeDefault.Text = "Change Default";
                        return ;
                    }
                     
                    labelPregNotice.Text = pregManualText;
                    pregDisDefNumCur = FormDD.SelectedDiseaseDefNum;
                }
                  
            } 
        }
         
        if (pregDisDefNumCur == 0)
        {
            textPregCode.Clear();
            textPregCodeDescript.Clear();
            labelPregNotice.Visible = false;
            return ;
        }
         
        disdefCur = DiseaseDefs.getItem(pregDisDefNumCur);
        if (!StringSupport.equals(disdefCur.ICD9Code, ""))
        {
            ICD9 i9Preg = ICD9s.getByCode(disdefCur.ICD9Code);
            if (i9Preg != null)
            {
                pregCode = i9Preg.ICD9Code;
                descript = i9Preg.Description;
            }
             
        }
        else if (!StringSupport.equals(disdefCur.Icd10Code, ""))
        {
            Icd10 i10Preg = Icd10s.getByCode(disdefCur.Icd10Code);
            if (i10Preg != null)
            {
                pregCode = i10Preg.Icd10Code;
                descript = i10Preg.Description;
            }
             
        }
        else if (!StringSupport.equals(disdefCur.SnomedCode, ""))
        {
            Snomed sPreg = Snomeds.getByCode(disdefCur.SnomedCode);
            if (sPreg != null)
            {
                pregCode = sPreg.SnomedCode;
                descript = sPreg.Description;
            }
             
        }
           
        if (StringSupport.equals(pregCode, "none") || StringSupport.equals(pregCode, ""))
        {
            descript = disdefCur.DiseaseName;
        }
         
        textPregCode.Text = pregCode;
        textPregCodeDescript.Text = descript;
    }

    private void fillBMICodeLists() throws Exception {
        boolean isInLoincTable = true;
        //The list returned will only contain the Loincs that are actually in the loinc table.
        List<Loinc> listLoincs = Loincs.getForCodeList("59574-4,59575-1,59576-9");
        //Body mass index (BMI) [Percentile],Body mass index (BMI) [Percentile] Per age,Body mass index (BMI) [Percentile] Per age and gender
        if (listLoincs.Count < 3)
        {
            isInLoincTable = false;
        }
         
        listBMICodes.AddRange(listLoincs);
        listLoincs = Loincs.getForCodeList("8302-2,3137-7,3138-5,8306-3,8307-1,8308-9");
        //Body height,Body height Measured,Body height Stated,Body height --lying,Body height --pre surgery,Body height --standing
        if (listLoincs.Count < 6)
        {
            isInLoincTable = false;
        }
         
        listHeightCodes.AddRange(listLoincs);
        listLoincs = Loincs.getForCodeList("29463-7,18833-4,3141-9,3142-7,8350-1,8351-9");
        //Body weight,First Body weight,Body weight Measured,Body weight Stated,Body weight Measured --with clothes,Body weight Measured --without clothes
        if (listLoincs.Count < 6)
        {
            isInLoincTable = false;
        }
         
        listWeightCodes.AddRange(listLoincs);
        if (!isInLoincTable)
        {
            MsgBox.show(this,"The LOINC table does not contain one or more codes used to report vitalsign exam statistics.  The LOINC table should be updated by running the Code System Importer tool found in Setup | EHR.");
        }
         
    }

    /**
    * Fills the dictionary with gender (m or f) and age in months as the key and a list of three longs, the L=power of Box-Cox transformation, M=median, and S=generalized coefficient of variation, as the value.  The L, M, and S values are from the CDC website http://www.cdc.gov/nchs/data/series/sr_11/sr11_246.pdf page 178-186.
    */
    private void fillDictLMS() throws Exception {
        _dictLMS = new Dictionary<String, List<float>>();
        _dictLMS.Add("m36", new List<float>());
        _dictLMS.Add("m37", new List<float>());
        _dictLMS.Add("m38", new List<float>());
        _dictLMS.Add("m39", new List<float>());
        _dictLMS.Add("m40", new List<float>());
        _dictLMS.Add("m41", new List<float>());
        _dictLMS.Add("m42", new List<float>());
        _dictLMS.Add("m43", new List<float>());
        _dictLMS.Add("m44", new List<float>());
        _dictLMS.Add("m45", new List<float>());
        _dictLMS.Add("m46", new List<float>());
        _dictLMS.Add("m47", new List<float>());
        _dictLMS.Add("m48", new List<float>());
        _dictLMS.Add("m49", new List<float>());
        _dictLMS.Add("m50", new List<float>());
        _dictLMS.Add("m51", new List<float>());
        _dictLMS.Add("m52", new List<float>());
        _dictLMS.Add("m53", new List<float>());
        _dictLMS.Add("m54", new List<float>());
        _dictLMS.Add("m55", new List<float>());
        _dictLMS.Add("m56", new List<float>());
        _dictLMS.Add("m57", new List<float>());
        _dictLMS.Add("m58", new List<float>());
        _dictLMS.Add("m59", new List<float>());
        _dictLMS.Add("m60", new List<float>());
        _dictLMS.Add("m61", new List<float>());
        _dictLMS.Add("m62", new List<float>());
        _dictLMS.Add("m63", new List<float>());
        _dictLMS.Add("m64", new List<float>());
        _dictLMS.Add("m65", new List<float>());
        _dictLMS.Add("m66", new List<float>());
        _dictLMS.Add("m67", new List<float>());
        _dictLMS.Add("m68", new List<float>());
        _dictLMS.Add("m69", new List<float>());
        _dictLMS.Add("m70", new List<float>());
        _dictLMS.Add("m71", new List<float>());
        _dictLMS.Add("m72", new List<float>());
        _dictLMS.Add("m73", new List<float>());
        _dictLMS.Add("m74", new List<float>());
        _dictLMS.Add("m75", new List<float>());
        _dictLMS.Add("m76", new List<float>());
        _dictLMS.Add("m77", new List<float>());
        _dictLMS.Add("m78", new List<float>());
        _dictLMS.Add("m79", new List<float>());
        _dictLMS.Add("m80", new List<float>());
        _dictLMS.Add("m81", new List<float>());
        _dictLMS.Add("m82", new List<float>());
        _dictLMS.Add("m83", new List<float>());
        _dictLMS.Add("m84", new List<float>());
        _dictLMS.Add("m85", new List<float>());
        _dictLMS.Add("m86", new List<float>());
        _dictLMS.Add("m87", new List<float>());
        _dictLMS.Add("m88", new List<float>());
        _dictLMS.Add("m89", new List<float>());
        _dictLMS.Add("m90", new List<float>());
        _dictLMS.Add("m91", new List<float>());
        _dictLMS.Add("m92", new List<float>());
        _dictLMS.Add("m93", new List<float>());
        _dictLMS.Add("m94", new List<float>());
        _dictLMS.Add("m95", new List<float>());
        _dictLMS.Add("m96", new List<float>());
        _dictLMS.Add("m97", new List<float>());
        _dictLMS.Add("m98", new List<float>());
        _dictLMS.Add("m99", new List<float>());
        _dictLMS.Add("m100", new List<float>());
        _dictLMS.Add("m101", new List<float>());
        _dictLMS.Add("m102", new List<float>());
        _dictLMS.Add("m103", new List<float>());
        _dictLMS.Add("m104", new List<float>());
        _dictLMS.Add("m105", new List<float>());
        _dictLMS.Add("m106", new List<float>());
        _dictLMS.Add("m107", new List<float>());
        _dictLMS.Add("m108", new List<float>());
        _dictLMS.Add("m109", new List<float>());
        _dictLMS.Add("m110", new List<float>());
        _dictLMS.Add("m111", new List<float>());
        _dictLMS.Add("m112", new List<float>());
        _dictLMS.Add("m113", new List<float>());
        _dictLMS.Add("m114", new List<float>());
        _dictLMS.Add("m115", new List<float>());
        _dictLMS.Add("m116", new List<float>());
        _dictLMS.Add("m117", new List<float>());
        _dictLMS.Add("m118", new List<float>());
        _dictLMS.Add("m119", new List<float>());
        _dictLMS.Add("m120", new List<float>());
        _dictLMS.Add("m121", new List<float>());
        _dictLMS.Add("m122", new List<float>());
        _dictLMS.Add("m123", new List<float>());
        _dictLMS.Add("m124", new List<float>());
        _dictLMS.Add("m125", new List<float>());
        _dictLMS.Add("m126", new List<float>());
        _dictLMS.Add("m127", new List<float>());
        _dictLMS.Add("m128", new List<float>());
        _dictLMS.Add("m129", new List<float>());
        _dictLMS.Add("m130", new List<float>());
        _dictLMS.Add("m131", new List<float>());
        _dictLMS.Add("m132", new List<float>());
        _dictLMS.Add("m133", new List<float>());
        _dictLMS.Add("m134", new List<float>());
        _dictLMS.Add("m135", new List<float>());
        _dictLMS.Add("m136", new List<float>());
        _dictLMS.Add("m137", new List<float>());
        _dictLMS.Add("m138", new List<float>());
        _dictLMS.Add("m139", new List<float>());
        _dictLMS.Add("m140", new List<float>());
        _dictLMS.Add("m141", new List<float>());
        _dictLMS.Add("m142", new List<float>());
        _dictLMS.Add("m143", new List<float>());
        _dictLMS.Add("m144", new List<float>());
        _dictLMS.Add("m145", new List<float>());
        _dictLMS.Add("m146", new List<float>());
        _dictLMS.Add("m147", new List<float>());
        _dictLMS.Add("m148", new List<float>());
        _dictLMS.Add("m149", new List<float>());
        _dictLMS.Add("m150", new List<float>());
        _dictLMS.Add("m151", new List<float>());
        _dictLMS.Add("m152", new List<float>());
        _dictLMS.Add("m153", new List<float>());
        _dictLMS.Add("m154", new List<float>());
        _dictLMS.Add("m155", new List<float>());
        _dictLMS.Add("m156", new List<float>());
        _dictLMS.Add("m157", new List<float>());
        _dictLMS.Add("m158", new List<float>());
        _dictLMS.Add("m159", new List<float>());
        _dictLMS.Add("m160", new List<float>());
        _dictLMS.Add("m161", new List<float>());
        _dictLMS.Add("m162", new List<float>());
        _dictLMS.Add("m163", new List<float>());
        _dictLMS.Add("m164", new List<float>());
        _dictLMS.Add("m165", new List<float>());
        _dictLMS.Add("m166", new List<float>());
        _dictLMS.Add("m167", new List<float>());
        _dictLMS.Add("m168", new List<float>());
        _dictLMS.Add("m169", new List<float>());
        _dictLMS.Add("m170", new List<float>());
        _dictLMS.Add("m171", new List<float>());
        _dictLMS.Add("m172", new List<float>());
        _dictLMS.Add("m173", new List<float>());
        _dictLMS.Add("m174", new List<float>());
        _dictLMS.Add("m175", new List<float>());
        _dictLMS.Add("m176", new List<float>());
        _dictLMS.Add("m177", new List<float>());
        _dictLMS.Add("m178", new List<float>());
        _dictLMS.Add("m179", new List<float>());
        _dictLMS.Add("m180", new List<float>());
        _dictLMS.Add("m181", new List<float>());
        _dictLMS.Add("m182", new List<float>());
        _dictLMS.Add("m183", new List<float>());
        _dictLMS.Add("m184", new List<float>());
        _dictLMS.Add("m185", new List<float>());
        _dictLMS.Add("m186", new List<float>());
        _dictLMS.Add("m187", new List<float>());
        _dictLMS.Add("m188", new List<float>());
        _dictLMS.Add("m189", new List<float>());
        _dictLMS.Add("m190", new List<float>());
        _dictLMS.Add("m191", new List<float>());
        _dictLMS.Add("m192", new List<float>());
        _dictLMS.Add("m193", new List<float>());
        _dictLMS.Add("m194", new List<float>());
        _dictLMS.Add("m195", new List<float>());
        _dictLMS.Add("m196", new List<float>());
        _dictLMS.Add("m197", new List<float>());
        _dictLMS.Add("m198", new List<float>());
        _dictLMS.Add("m199", new List<float>());
        _dictLMS.Add("m200", new List<float>());
        _dictLMS.Add("m201", new List<float>());
        _dictLMS.Add("m202", new List<float>());
        _dictLMS.Add("m203", new List<float>());
        _dictLMS.Add("m204", new List<float>());
        _dictLMS.Add("m205", new List<float>());
        _dictLMS.Add("m206", new List<float>());
        _dictLMS.Add("m207", new List<float>());
        _dictLMS.Add("m208", new List<float>());
        _dictLMS.Add("m209", new List<float>());
        _dictLMS.Add("m210", new List<float>());
        _dictLMS.Add("m211", new List<float>());
        _dictLMS.Add("m212", new List<float>());
        _dictLMS.Add("m213", new List<float>());
        _dictLMS.Add("m214", new List<float>());
        _dictLMS.Add("m215", new List<float>());
        _dictLMS.Add("f36", new List<float>());
        _dictLMS.Add("f37", new List<float>());
        _dictLMS.Add("f38", new List<float>());
        _dictLMS.Add("f39", new List<float>());
        _dictLMS.Add("f40", new List<float>());
        _dictLMS.Add("f41", new List<float>());
        _dictLMS.Add("f42", new List<float>());
        _dictLMS.Add("f43", new List<float>());
        _dictLMS.Add("f44", new List<float>());
        _dictLMS.Add("f45", new List<float>());
        _dictLMS.Add("f46", new List<float>());
        _dictLMS.Add("f47", new List<float>());
        _dictLMS.Add("f48", new List<float>());
        _dictLMS.Add("f49", new List<float>());
        _dictLMS.Add("f50", new List<float>());
        _dictLMS.Add("f51", new List<float>());
        _dictLMS.Add("f52", new List<float>());
        _dictLMS.Add("f53", new List<float>());
        _dictLMS.Add("f54", new List<float>());
        _dictLMS.Add("f55", new List<float>());
        _dictLMS.Add("f56", new List<float>());
        _dictLMS.Add("f57", new List<float>());
        _dictLMS.Add("f58", new List<float>());
        _dictLMS.Add("f59", new List<float>());
        _dictLMS.Add("f60", new List<float>());
        _dictLMS.Add("f61", new List<float>());
        _dictLMS.Add("f62", new List<float>());
        _dictLMS.Add("f63", new List<float>());
        _dictLMS.Add("f64", new List<float>());
        _dictLMS.Add("f65", new List<float>());
        _dictLMS.Add("f66", new List<float>());
        _dictLMS.Add("f67", new List<float>());
        _dictLMS.Add("f68", new List<float>());
        _dictLMS.Add("f69", new List<float>());
        _dictLMS.Add("f70", new List<float>());
        _dictLMS.Add("f71", new List<float>());
        _dictLMS.Add("f72", new List<float>());
        _dictLMS.Add("f73", new List<float>());
        _dictLMS.Add("f74", new List<float>());
        _dictLMS.Add("f75", new List<float>());
        _dictLMS.Add("f76", new List<float>());
        _dictLMS.Add("f77", new List<float>());
        _dictLMS.Add("f78", new List<float>());
        _dictLMS.Add("f79", new List<float>());
        _dictLMS.Add("f80", new List<float>());
        _dictLMS.Add("f81", new List<float>());
        _dictLMS.Add("f82", new List<float>());
        _dictLMS.Add("f83", new List<float>());
        _dictLMS.Add("f84", new List<float>());
        _dictLMS.Add("f85", new List<float>());
        _dictLMS.Add("f86", new List<float>());
        _dictLMS.Add("f87", new List<float>());
        _dictLMS.Add("f88", new List<float>());
        _dictLMS.Add("f89", new List<float>());
        _dictLMS.Add("f90", new List<float>());
        _dictLMS.Add("f91", new List<float>());
        _dictLMS.Add("f92", new List<float>());
        _dictLMS.Add("f93", new List<float>());
        _dictLMS.Add("f94", new List<float>());
        _dictLMS.Add("f95", new List<float>());
        _dictLMS.Add("f96", new List<float>());
        _dictLMS.Add("f97", new List<float>());
        _dictLMS.Add("f98", new List<float>());
        _dictLMS.Add("f99", new List<float>());
        _dictLMS.Add("f100", new List<float>());
        _dictLMS.Add("f101", new List<float>());
        _dictLMS.Add("f102", new List<float>());
        _dictLMS.Add("f103", new List<float>());
        _dictLMS.Add("f104", new List<float>());
        _dictLMS.Add("f105", new List<float>());
        _dictLMS.Add("f106", new List<float>());
        _dictLMS.Add("f107", new List<float>());
        _dictLMS.Add("f108", new List<float>());
        _dictLMS.Add("f109", new List<float>());
        _dictLMS.Add("f110", new List<float>());
        _dictLMS.Add("f111", new List<float>());
        _dictLMS.Add("f112", new List<float>());
        _dictLMS.Add("f113", new List<float>());
        _dictLMS.Add("f114", new List<float>());
        _dictLMS.Add("f115", new List<float>());
        _dictLMS.Add("f116", new List<float>());
        _dictLMS.Add("f117", new List<float>());
        _dictLMS.Add("f118", new List<float>());
        _dictLMS.Add("f119", new List<float>());
        _dictLMS.Add("f120", new List<float>());
        _dictLMS.Add("f121", new List<float>());
        _dictLMS.Add("f122", new List<float>());
        _dictLMS.Add("f123", new List<float>());
        _dictLMS.Add("f124", new List<float>());
        _dictLMS.Add("f125", new List<float>());
        _dictLMS.Add("f126", new List<float>());
        _dictLMS.Add("f127", new List<float>());
        _dictLMS.Add("f128", new List<float>());
        _dictLMS.Add("f129", new List<float>());
        _dictLMS.Add("f130", new List<float>());
        _dictLMS.Add("f131", new List<float>());
        _dictLMS.Add("f132", new List<float>());
        _dictLMS.Add("f133", new List<float>());
        _dictLMS.Add("f134", new List<float>());
        _dictLMS.Add("f135", new List<float>());
        _dictLMS.Add("f136", new List<float>());
        _dictLMS.Add("f137", new List<float>());
        _dictLMS.Add("f138", new List<float>());
        _dictLMS.Add("f139", new List<float>());
        _dictLMS.Add("f140", new List<float>());
        _dictLMS.Add("f141", new List<float>());
        _dictLMS.Add("f142", new List<float>());
        _dictLMS.Add("f143", new List<float>());
        _dictLMS.Add("f144", new List<float>());
        _dictLMS.Add("f145", new List<float>());
        _dictLMS.Add("f146", new List<float>());
        _dictLMS.Add("f147", new List<float>());
        _dictLMS.Add("f148", new List<float>());
        _dictLMS.Add("f149", new List<float>());
        _dictLMS.Add("f150", new List<float>());
        _dictLMS.Add("f151", new List<float>());
        _dictLMS.Add("f152", new List<float>());
        _dictLMS.Add("f153", new List<float>());
        _dictLMS.Add("f154", new List<float>());
        _dictLMS.Add("f155", new List<float>());
        _dictLMS.Add("f156", new List<float>());
        _dictLMS.Add("f157", new List<float>());
        _dictLMS.Add("f158", new List<float>());
        _dictLMS.Add("f159", new List<float>());
        _dictLMS.Add("f160", new List<float>());
        _dictLMS.Add("f161", new List<float>());
        _dictLMS.Add("f162", new List<float>());
        _dictLMS.Add("f163", new List<float>());
        _dictLMS.Add("f164", new List<float>());
        _dictLMS.Add("f165", new List<float>());
        _dictLMS.Add("f166", new List<float>());
        _dictLMS.Add("f167", new List<float>());
        _dictLMS.Add("f168", new List<float>());
        _dictLMS.Add("f169", new List<float>());
        _dictLMS.Add("f170", new List<float>());
        _dictLMS.Add("f171", new List<float>());
        _dictLMS.Add("f172", new List<float>());
        _dictLMS.Add("f173", new List<float>());
        _dictLMS.Add("f174", new List<float>());
        _dictLMS.Add("f175", new List<float>());
        _dictLMS.Add("f176", new List<float>());
        _dictLMS.Add("f177", new List<float>());
        _dictLMS.Add("f178", new List<float>());
        _dictLMS.Add("f179", new List<float>());
        _dictLMS.Add("f180", new List<float>());
        _dictLMS.Add("f181", new List<float>());
        _dictLMS.Add("f182", new List<float>());
        _dictLMS.Add("f183", new List<float>());
        _dictLMS.Add("f184", new List<float>());
        _dictLMS.Add("f185", new List<float>());
        _dictLMS.Add("f186", new List<float>());
        _dictLMS.Add("f187", new List<float>());
        _dictLMS.Add("f188", new List<float>());
        _dictLMS.Add("f189", new List<float>());
        _dictLMS.Add("f190", new List<float>());
        _dictLMS.Add("f191", new List<float>());
        _dictLMS.Add("f192", new List<float>());
        _dictLMS.Add("f193", new List<float>());
        _dictLMS.Add("f194", new List<float>());
        _dictLMS.Add("f195", new List<float>());
        _dictLMS.Add("f196", new List<float>());
        _dictLMS.Add("f197", new List<float>());
        _dictLMS.Add("f198", new List<float>());
        _dictLMS.Add("f199", new List<float>());
        _dictLMS.Add("f200", new List<float>());
        _dictLMS.Add("f201", new List<float>());
        _dictLMS.Add("f202", new List<float>());
        _dictLMS.Add("f203", new List<float>());
        _dictLMS.Add("f204", new List<float>());
        _dictLMS.Add("f205", new List<float>());
        _dictLMS.Add("f206", new List<float>());
        _dictLMS.Add("f207", new List<float>());
        _dictLMS.Add("f208", new List<float>());
        _dictLMS.Add("f209", new List<float>());
        _dictLMS.Add("f210", new List<float>());
        _dictLMS.Add("f211", new List<float>());
        _dictLMS.Add("f212", new List<float>());
        _dictLMS.Add("f213", new List<float>());
        _dictLMS.Add("f214", new List<float>());
        _dictLMS.Add("f215", new List<float>());
    }

    private void calcBMI() throws Exception {
        labelWeightCode.Text = "";
        //BMI = (lbs*703)/(in^2)
        float height = new float();
        float weight = new float();
        try
        {
            height = float.Parse(textHeight.Text);
            weight = float.Parse(textWeight.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            textBMIExamCode.Text = "";
            return ;
        }

        if (height <= 0)
        {
            textBMIExamCode.Text = "";
            return ;
        }
         
        if (weight <= 0)
        {
            textBMIExamCode.Text = "";
            return ;
        }
         
        float bmi = Vitalsigns.calcBMI(weight,height);
        // ((float)(weight*703)/(height*height));
        textBMI.Text = bmi.ToString("n1");
        labelWeightCode.Text = calcOverUnderBMIHelper(bmi);
        int bmiPercentile = -1;
        if (ageBeforeJanFirst < 17 && ageBeforeJanFirst > 2)
        {
            //calc and show BMI percentile if patient is >= 3 and < 17 on 01/01 of the year of the exam
            bmiPercentile = calcBMIPercentile(bmi);
        }
         
        if (bmiPercentile > -1)
        {
            labelBMIPercentile.Visible = true;
            textBMIPercentile.Visible = true;
            labelBMIPercentileCode.Visible = true;
            textBMIPercentileCode.Visible = true;
            textBMIPercentile.Text = bmiPercentile.ToString();
            if (Loincs.getByCode("59576-9") != null)
            {
                textBMIPercentileCode.Text = "LOINC 59576-9";
            }
             
        }
         
        //Body mass index (BMI) [Percentile] Per age and gender, only code we will allow for percentile
        if (bmiPercentile == -1 || ageBeforeJanFirst >= 17 || ageBeforeJanFirst < 3)
        {
            labelBMIPercentile.Visible = false;
            textBMIPercentile.Visible = false;
            labelBMIPercentileCode.Visible = false;
            textBMIPercentileCode.Visible = false;
            textBMIPercentile.Text = bmiPercentile.ToString();
        }
         
        //We will set vitalsign.BMIPercentile=-1 if they are not in the age range or if there is an error calculating BMI percentile
        boolean isIntGroupVisible = false;
        String childGroupLabel = "Child Counseling for Nutrition and Physical Activity";
        String overUnderGroupLabel = "Intervention for BMI Above or Below Normal";
        if (ageBeforeJanFirst < 17)
        {
            isIntGroupVisible = true;
            if (!StringSupport.equals(groupInterventions.Text, childGroupLabel))
            {
                groupInterventions.Text = childGroupLabel;
            }
             
            fillGridInterventions(true);
        }
        else if (ageBeforeJanFirst >= 18 && !StringSupport.equals(labelWeightCode.Text, ""))
        {
            //if 18 or over and given an over/underweight code due to BMI
            isIntGroupVisible = true;
            if (!StringSupport.equals(groupInterventions.Text, overUnderGroupLabel))
            {
                groupInterventions.Text = overUnderGroupLabel;
            }
             
            fillGridInterventions(false);
        }
          
        groupInterventions.Visible = isIntGroupVisible;
        if (Loincs.getByCode("39156-5") != null)
        {
            textBMIExamCode.Text = "LOINC 39156-5";
        }
         
        return ;
    }

    //This is the only code allowed for the BMI procedure.  It is not stored with this vitalsign object, we will display it if they have the code in the loinc table and will calculate CQM's with the assumption that all vitalsign objects with valid height and weight are this code if they have it in the LOINC table.
    private String calcOverUnderBMIHelper(float bmi) throws Exception {
        if (ageBeforeJanFirst < 18)
        {
            //Do not clasify children as over/underweight
            intervCodeSet = InterventionCodeSet.Nutrition;
            return "";
        }
        else //we will sent Nutrition to FormInterventionEdit, but this could also be a physical activity intervention
        if (ageBeforeJanFirst < 65)
        {
            if (bmi < 18.5)
            {
                intervCodeSet = InterventionCodeSet.BelowNormalWeight;
                return "Underweight";
            }
            else if (bmi < 25)
            {
                return "";
            }
            else
            {
                intervCodeSet = InterventionCodeSet.AboveNormalWeight;
                return "Overweight";
            }  
        }
        else
        {
            if (bmi < 23)
            {
                intervCodeSet = InterventionCodeSet.BelowNormalWeight;
                return "Underweight";
            }
            else if (bmi < 30)
            {
                return "";
            }
            else
            {
                intervCodeSet = InterventionCodeSet.AboveNormalWeight;
                return "Overweight";
            }  
        }  
    }

    //do not save to DB until butOK_Click
    /**
    * Only called if patient is under 18 at the time of the exam.
    */
    private int calcBMIPercentile(float bmi) throws Exception {
        //get age at time of exam for BMI percentile in months.  Examples: 13 years 11 months = 13*12+11 = 167.
        DateTime dateExam = PIn.Date(textDateTaken.Text);
        if (dateExam == DateTime.MinValue || dateExam < patCur.Birthdate || patCur.Birthdate == DateTime.MinValue)
        {
            return -1;
        }
         
        int years = dateExam.Year - patCur.Birthdate.Year;
        if (dateExam.Month < patCur.Birthdate.Month || (dateExam.Month == patCur.Birthdate.Month && dateExam.Day < patCur.Birthdate.Day))
        {
            //have not had birthday this year
            years--;
        }
         
        int months = dateExam.Month - patCur.Birthdate.Month;
        if (patCur.Birthdate.Day > dateExam.Day)
        {
            months--;
        }
         
        if (months < 0)
        {
            months = months + 12;
        }
         
        months = months + (years * 12);
        String genderAgeGroup = "";
        if (patCur.Gender == PatientGender.Male)
        {
            genderAgeGroup = "m";
        }
        else if (patCur.Gender == PatientGender.Female)
        {
            genderAgeGroup = "f";
        }
        else
        {
            return -1;
        }  
        genderAgeGroup += months.ToString();
        //get L, M, and S for the patient's gender and age from dict
        List<float> listLMS = _dictLMS[genderAgeGroup];
        //use (((bmi/M)^L)-1)/(L*S) to get z-score
        float zScore = (((float)Math.Pow(bmi / listLMS[1], listLMS[0])) - 1) / (listLMS[0] * listLMS[2]);
        return getPercentile(zScore);
    }

    //use GetPercentile helper function to get percentile from z-score
    private static int getPercentile(float z) throws Exception {
        List<float> listPercentiles = new List<float>();
        listPercentiles.Add(-2.325f);
        //0th percentile
        listPercentiles.Add(-2.055f);
        //1st percentile
        listPercentiles.Add(-1.885f);
        //2nd percentile
        listPercentiles.Add(-1.755f);
        //3rd
        listPercentiles.Add(-1.645f);
        //4th
        listPercentiles.Add(-1.555f);
        //5th
        listPercentiles.Add(-1.475f);
        //6th
        listPercentiles.Add(-1.405f);
        //7th
        listPercentiles.Add(-1.345f);
        //8th
        listPercentiles.Add(-1.285f);
        //9th
        listPercentiles.Add(-1.225f);
        //10th
        listPercentiles.Add(-1.175f);
        //11th
        listPercentiles.Add(-1.125f);
        //12th
        listPercentiles.Add(-1.085f);
        //13th
        listPercentiles.Add(-1.035f);
        //14th
        listPercentiles.Add(-0.995f);
        //15th
        listPercentiles.Add(-0.955f);
        //16th
        listPercentiles.Add(-0.915f);
        //17th
        listPercentiles.Add(-0.875f);
        //18th
        listPercentiles.Add(-0.845f);
        //19th
        listPercentiles.Add(-0.805f);
        //20th
        listPercentiles.Add(-0.775f);
        //21st
        listPercentiles.Add(-0.735f);
        //22nd
        listPercentiles.Add(-0.705f);
        //23rd
        listPercentiles.Add(-0.675f);
        //24th
        listPercentiles.Add(-0.645f);
        //25th
        listPercentiles.Add(-0.615f);
        //26th
        listPercentiles.Add(-0.585f);
        //27th
        listPercentiles.Add(-0.555f);
        //28th
        listPercentiles.Add(-0.525f);
        //29th
        listPercentiles.Add(-0.495f);
        //30th
        listPercentiles.Add(-0.465f);
        //31st
        listPercentiles.Add(-0.435f);
        //32nd
        listPercentiles.Add(-0.415f);
        //33rd
        listPercentiles.Add(-0.385f);
        //34th
        listPercentiles.Add(-0.355f);
        //35th
        listPercentiles.Add(-0.335f);
        //36th
        listPercentiles.Add(-0.305f);
        //37th
        listPercentiles.Add(-0.275f);
        //38th
        listPercentiles.Add(-0.255f);
        //39th
        listPercentiles.Add(-0.225f);
        //40th
        listPercentiles.Add(-0.205f);
        //41st
        listPercentiles.Add(-0.175f);
        //42nd
        listPercentiles.Add(-0.155f);
        //43rd
        listPercentiles.Add(-0.125f);
        //44th
        listPercentiles.Add(-0.105f);
        //45th
        listPercentiles.Add(-0.075f);
        //46th
        listPercentiles.Add(-0.055f);
        //47th
        listPercentiles.Add(-0.025f);
        //48th
        listPercentiles.Add(-0.005f);
        //49th
        listPercentiles.Add(0.025f);
        //50th
        listPercentiles.Add(0.055f);
        //51st
        listPercentiles.Add(0.075f);
        //52nd
        listPercentiles.Add(0.105f);
        //53rd
        listPercentiles.Add(0.125f);
        //54th
        listPercentiles.Add(0.155f);
        //55th
        listPercentiles.Add(0.175f);
        //56th
        listPercentiles.Add(0.205f);
        //57th
        listPercentiles.Add(0.225f);
        //58th
        listPercentiles.Add(0.255f);
        //59th
        listPercentiles.Add(0.275f);
        //60th
        listPercentiles.Add(0.305f);
        //61st
        listPercentiles.Add(0.335f);
        //62nd
        listPercentiles.Add(0.355f);
        //63rd
        listPercentiles.Add(0.385f);
        //64th
        listPercentiles.Add(0.415f);
        //65th
        listPercentiles.Add(0.435f);
        //66th
        listPercentiles.Add(0.465f);
        //67th
        listPercentiles.Add(0.495f);
        //68th
        listPercentiles.Add(0.525f);
        //69th
        listPercentiles.Add(0.555f);
        //70th
        listPercentiles.Add(0.585f);
        //71st
        listPercentiles.Add(0.615f);
        //72nd
        listPercentiles.Add(0.645f);
        //73rd
        listPercentiles.Add(0.675f);
        //74th
        listPercentiles.Add(0.705f);
        //75th
        listPercentiles.Add(0.735f);
        //76th
        listPercentiles.Add(0.775f);
        //77th
        listPercentiles.Add(0.805f);
        //78th
        listPercentiles.Add(0.845f);
        //79th
        listPercentiles.Add(0.875f);
        //80th
        listPercentiles.Add(0.915f);
        //81st
        listPercentiles.Add(0.955f);
        //82nd
        listPercentiles.Add(0.995f);
        //83rd
        listPercentiles.Add(1.035f);
        //84th
        listPercentiles.Add(1.085f);
        //85th
        listPercentiles.Add(1.125f);
        //86th
        listPercentiles.Add(1.175f);
        //87th
        listPercentiles.Add(1.225f);
        //88th
        listPercentiles.Add(1.285f);
        //89th
        listPercentiles.Add(1.345f);
        //90th
        listPercentiles.Add(1.405f);
        //91st
        listPercentiles.Add(1.475f);
        //92nd
        listPercentiles.Add(1.555f);
        //93rd
        listPercentiles.Add(1.645f);
        //94th
        listPercentiles.Add(1.755f);
        //95th
        listPercentiles.Add(1.885f);
        //96th
        listPercentiles.Add(2.055f);
        //97th
        listPercentiles.Add(2.325f);
        //98th
        listPercentiles.Add(float.MaxValue);
        for (int i = 0;i < listPercentiles.Count;i++)
        {
            //99th
            if (z < listPercentiles[i])
            {
                return i;
            }
             
        }
        return -1;
    }

    private void fillGridInterventions(boolean isChild) throws Exception {
        DateTime examDate = PIn.Date(textDateTaken.Text);
        //this may be different than the saved VitalsignCur.DateTaken if user edited and has not hit ok to save
        List<Intervention> listIntervention = new List<Intervention>();
        if (isChild)
        {
            listIntervention = Interventions.refresh(VitalsignCur.PatNum,InterventionCodeSet.Nutrition);
            listIntervention.AddRange(Interventions.refresh(VitalsignCur.PatNum,InterventionCodeSet.PhysicalActivity));
        }
        else
        {
            listIntervention = Interventions.refresh(VitalsignCur.PatNum,InterventionCodeSet.AboveNormalWeight);
            listIntervention.AddRange(Interventions.refresh(VitalsignCur.PatNum,InterventionCodeSet.BelowNormalWeight));
        } 
        for (int i = listIntervention.Count - 1;i > -1;i--)
        {
            if (listIntervention[i].DateEntry.Date <= examDate.Date && listIntervention[i].DateEntry.Date > examDate.AddMonths(-6).Date)
            {
                continue;
            }
             
            listIntervention.RemoveAt(i);
        }
        List<MedicationPat> listMedPats = new List<MedicationPat>();
        if (!isChild)
        {
            listMedPats = MedicationPats.refresh(VitalsignCur.PatNum,true);
            for (int i = listMedPats.Count - 1;i > -1;i--)
            {
                if (listMedPats[i].DateStart.Date < examDate.AddMonths(-6).Date || listMedPats[i].DateStart.Date > examDate.Date)
                {
                    listMedPats.RemoveAt(i);
                }
                 
            }
            //if still meds that have start date within exam date and exam date -6 months, check the rxnorm against valid ehr meds
            if (listMedPats.Count > 0)
            {
                List<EhrCode> listEhrMeds = EhrCodes.GetForValueSetOIDs(new List<String>{ "2.16.840.1.113883.3.600.1.1498", "2.16.840.1.113883.3.600.1.1499" }, true);
                for (int i = listMedPats.Count - 1;i > -1;i--)
                {
                    //Above Normal Medications RxNorm Value Set, Below Normal Medications RxNorm Value Set
                    //listEhrMeds will only contain 7 medications for above/below normal weight and only if those exist in the rxnorm table
                    boolean found = false;
                    for (int j = 0;j < listEhrMeds.Count;j++)
                    {
                        if (listMedPats[i].RxCui.ToString() == listEhrMeds[j].CodeValue)
                        {
                            found = true;
                            break;
                        }
                         
                    }
                    if (!found)
                    {
                        listMedPats.RemoveAt(i);
                    }
                     
                }
            }
             
        }
         
        gridInterventions.beginUpdate();
        gridInterventions.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Date",70);
        gridInterventions.getColumns().add(col);
        col = new ODGridColumn("Intervention Type",115);
        gridInterventions.getColumns().add(col);
        col = new ODGridColumn("Code Description",200);
        gridInterventions.getColumns().add(col);
        gridInterventions.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < listIntervention.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listIntervention[i].DateEntry.ToShortDateString());
            row.getCells().Add(listIntervention[i].CodeSet.ToString());
            //Description of Intervention---------------------------------------------
            //to get description, first determine which table the code is from.  Interventions are allowed to be SNOMEDCT, ICD9, ICD10, HCPCS, or CPT.
            String descript = "";
            CodeSystem __dummyScrutVar1 = listIntervention[i].CodeSystem;
            if (__dummyScrutVar1.equals("SNOMEDCT"))
            {
                Snomed sCur = Snomeds.GetByCode(listIntervention[i].CodeValue);
                if (sCur != null)
                {
                    descript = sCur.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("ICD9CM"))
            {
                ICD9 i9Cur = ICD9s.GetByCode(listIntervention[i].CodeValue);
                if (i9Cur != null)
                {
                    descript = i9Cur.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("ICD10CM"))
            {
                Icd10 i10Cur = Icd10s.GetByCode(listIntervention[i].CodeValue);
                if (i10Cur != null)
                {
                    descript = i10Cur.Description;
                }
                 
            }
            else if (__dummyScrutVar1.equals("HCPCS"))
            {
                Hcpcs hCur = Hcpcses.GetByCode(listIntervention[i].CodeValue);
                if (hCur != null)
                {
                    descript = hCur.DescriptionShort;
                }
                 
            }
            else if (__dummyScrutVar1.equals("CPT"))
            {
                Cpt cptCur = Cpts.GetByCode(listIntervention[i].CodeValue);
                if (cptCur != null)
                {
                    descript = cptCur.Description;
                }
                 
            }
                 
            row.getCells().add(descript);
            row.setTag(listIntervention[i]);
            gridInterventions.getRows().add(row);
        }
        for (int i = 0;i < listMedPats.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(listMedPats[i].DateStart.ToShortDateString());
            if (listMedPats[i].RxCui == 314153 || listMedPats[i].RxCui == 692876)
            {
                row.getCells().Add(InterventionCodeSet.AboveNormalWeight.ToString() + " Medication");
            }
            else
            {
                row.getCells().Add(InterventionCodeSet.BelowNormalWeight.ToString() + " Medication");
            } 
            //Description of Medication----------------------------------------------
            //only meds in EHR table are from RxNorm table
            String descript = RxNorms.GetDescByRxCui(listMedPats[i].RxCui.ToString());
            row.getCells().add(descript);
            row.setTag(listMedPats[i]);
            gridInterventions.getRows().add(row);
        }
        gridInterventions.endUpdate();
    }

    private void textWeight_TextChanged(Object sender, EventArgs e) throws Exception {
        calcBMI();
    }

    private void textHeight_TextChanged(Object sender, EventArgs e) throws Exception {
        calcBMI();
    }

    private void textBPs_TextChanged(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textBPs.Text, "0"))
        {
            textBPsExamCode.Text = "";
            return ;
        }
         
        try
        {
            int.Parse(textBPs.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            textBPsExamCode.Text = "";
            return ;
        }

        if (Loincs.getByCode("8480-6") != null)
        {
            textBPsExamCode.Text = "LOINC 8480-6";
        }
         
    }

    //This is the only code allowed for the BP Systolic exam.  It is not stored with this vitalsign object, we will display it if they have the code in the loinc table and will calculate CQM's with the assumption that all vitalsign objects with valid Systolic BP are this code if they have it in the LOINC table.
    private void textBPd_TextChanged(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textBPd.Text, "0"))
        {
            textBPdExamCode.Text = "";
            return ;
        }
         
        try
        {
            int.Parse(textBPd.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            textBPdExamCode.Text = "";
            return ;
        }

        if (Loincs.getByCode("8462-4") != null)
        {
            textBPdExamCode.Text = "LOINC 8462-4";
        }
         
    }

    //This is the only code allowed for the BP Diastolic exam.  It is not stored with this vitalsign object, we will display it if they have the code in the loinc table and will calculate CQM's with the assumption that all vitalsign objects with valid Diastolic BP are this code if they have it in the LOINC table.
    /**
    * If they change the date of the exam and it is attached to a pregnancy problem and the date is now outside the active dates of the problem, tell them you are removing the problem and unchecking the pregnancy box.
    */
    private void textDateTaken_Leave(Object sender, EventArgs e) throws Exception {
        DateTime examDate = PIn.Date(textDateTaken.Text);
        ageBeforeJanFirst = examDate.Year - patCur.Birthdate.Year - 1;
        //This is how old this patient was before any birthday in the year the vital sign was taken, can be negative if patient born the year taken or if value in textDateTaken is empty or not a valid date
        if (!checkPregnant.Checked || VitalsignCur.PregDiseaseNum == 0)
        {
            calcBMI();
            return ;
        }
         
        //This will use new year taken to determine age at start of that year to show over/underweight if applicable using age specific criteria
        Disease disCur = Diseases.getOne(VitalsignCur.PregDiseaseNum);
        if (disCur != null)
        {
            //the currently attached preg disease is valid, will be null if they checked the box on new exam but haven't hit ok to save
            if (examDate.Date < disCur.DateStart || (disCur.DateStop.Year > 1880 && disCur.DateStop < examDate.Date))
            {
                //if this exam is not in the active date range of the problem
                if (!MsgBox.show(this,MsgBoxButtons.YesNo,"The exam date is no longer within the active dates of the attached pregnancy diagnosis.\r\n" + 
                "Do you want to remove the pregnancy diagnosis?"))
                {
                    textDateTaken.Focus();
                    return ;
                }
                 
            }
            else
            {
                calcBMI();
                return ;
            } 
        }
         
        //if we get here, either the pregnant check box is not checked, there is not a currently attached preg disease, there is an attached disease but this exam is no longer in the active dates and the user said to remove it, or the current PregDiseaseNum is invalid
        VitalsignCur.PregDiseaseNum = 0;
        checkPregnant.Checked = false;
        labelPregNotice.Visible = false;
        textPregCode.Clear();
        textPregCodeDescript.Clear();
        butChangeDefault.Text = "Change Default";
        calcBMI();
    }

    private void checkPregnant_Click(Object sender, EventArgs e) throws Exception {
        labelPregNotice.Visible = false;
        butChangeDefault.Text = "Change Default";
        textPregCode.Clear();
        textPregCodeDescript.Clear();
        if (!checkPregnant.Checked)
        {
            return ;
        }
         
        setPregCodeAndDescript();
        if (pregDisDefNumCur == 0)
        {
            checkPregnant.Checked = false;
            return ;
        }
         
        if (VitalsignCur.PregDiseaseNum > 0)
        {
            //if the current vital sign exam linked to a pregnancy dx set the Change Default button to "To Problem" so they can modify the existing problem.
            butChangeDefault.Text = "Go to Problem";
        }
        else
        {
            //only show warning if we are now attached to a preg dx, add it is not a previously existing problem so we have to insert it.
            labelPregNotice.Visible = true;
        } 
    }

    private void butChangeDefault_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(butChangeDefault.Text, "Go to Problem"))
        {
            //text is "To Problem" only when vital sign has a valid PregDiseaseNum and the preg box is checked
            Disease disCur = Diseases.getOne(VitalsignCur.PregDiseaseNum);
            if (disCur == null)
            {
                //should never happen, the only way the button will say "To Problem" is if this exam is pointing to a valid problem
                butChangeDefault.Text = "Change Default";
                labelPregNotice.Visible = false;
                textPregCode.Clear();
                textPregCodeDescript.Clear();
                VitalsignCur.PregDiseaseNum = 0;
                checkPregnant.Checked = false;
                return ;
            }
             
            FormDiseaseEdit FormDis = new FormDiseaseEdit(disCur);
            FormDis.IsNew = false;
            FormDis.ShowDialog();
            if (FormDis.DialogResult == DialogResult.OK)
            {
                VitalsignCur.PregDiseaseNum = Vitalsigns.getOne(VitalsignCur.VitalsignNum).PregDiseaseNum;
                //if unlinked in FormDiseaseEdit, refresh PregDiseaseNum from db
                if (VitalsignCur.PregDiseaseNum == 0)
                {
                    butChangeDefault.Text = "Change Default";
                    labelPregNotice.Visible = false;
                    textPregCode.Clear();
                    textPregCodeDescript.Clear();
                    checkPregnant.Checked = false;
                    return ;
                }
                 
                setPregCodeAndDescript();
                if (pregDisDefNumCur == 0)
                {
                    labelPregNotice.Visible = false;
                    butChangeDefault.Text = "Change Default";
                }
                 
            }
             
        }
        else
        {
            if (!Security.isAuthorized(Permissions.SecurityAdmin,false))
            {
                return ;
            }
             
            FormEhrSettings FormEhr = new FormEhrSettings();
            FormEhr.ShowDialog();
            if (FormEhr.DialogResult != DialogResult.OK || checkPregnant.Checked == false)
            {
                return ;
            }
             
            labelPregNotice.Visible = false;
            setPregCodeAndDescript();
            if (pregDisDefNumCur > 0)
            {
                labelPregNotice.Visible = true;
            }
             
        } 
    }

    private void checkNotPerf_Click(Object sender, EventArgs e) throws Exception {
        if (checkNotPerf.Checked)
        {
            FormEhrNotPerformedEdit FormNP = new FormEhrNotPerformedEdit();
            if (VitalsignCur.EhrNotPerformedNum == 0)
            {
                FormNP.EhrNotPerfCur = new EhrNotPerformed();
                FormNP.EhrNotPerfCur.setIsNew(true);
                FormNP.EhrNotPerfCur.PatNum = patCur.PatNum;
                FormNP.EhrNotPerfCur.ProvNum = patCur.PriProv;
                FormNP.SelectedItemIndex = ((Enum)EhrNotPerformedItem.BMIExam).ordinal();
                //The code and code value will be set in FormEhrNotPerformedEdit, set the selected index to the EhrNotPerformedItem enum index for BMIExam
                FormNP.EhrNotPerfCur.DateEntry = PIn.Date(textDateTaken.Text);
                FormNP.IsDateReadOnly = true;
            }
            else
            {
                //if this not performed item will be linked to this exam, force the dates to match.  User can change exam date and recheck the box to affect the not performed item date, but forcing them to be the same will allow us to avoid other complications.
                FormNP.EhrNotPerfCur = EhrNotPerformeds.getOne(VitalsignCur.EhrNotPerformedNum);
                FormNP.EhrNotPerfCur.setIsNew(false);
                FormNP.SelectedItemIndex = ((Enum)EhrNotPerformedItem.BMIExam).ordinal();
            } 
            FormNP.ShowDialog();
            if (FormNP.DialogResult == DialogResult.OK)
            {
                VitalsignCur.EhrNotPerformedNum = FormNP.EhrNotPerfCur.EhrNotPerformedNum;
                textReasonCode.Text = FormNP.EhrNotPerfCur.CodeValueReason;
                Snomed sCur = Snomeds.getByCode(FormNP.EhrNotPerfCur.CodeValueReason);
                if (sCur != null)
                {
                    textReasonDescript.Text = sCur.Description;
                }
                 
            }
            else
            {
                checkNotPerf.Checked = false;
                textReasonCode.Clear();
                textReasonDescript.Clear();
                if (EhrNotPerformeds.getOne(VitalsignCur.EhrNotPerformedNum) == null)
                {
                    //could be linked to a not performed item that no longer exists or has been deleted
                    VitalsignCur.EhrNotPerformedNum = 0;
                }
                 
            } 
        }
        else
        {
            textReasonCode.Clear();
            textReasonDescript.Clear();
        } 
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormInterventionEdit FormInt = new FormInterventionEdit();
        FormInt.InterventionCur = new Intervention();
        FormInt.InterventionCur.setIsNew(true);
        FormInt.InterventionCur.PatNum = patCur.PatNum;
        FormInt.InterventionCur.ProvNum = patCur.PriProv;
        FormInt.InterventionCur.DateEntry = PIn.Date(textDateTaken.Text);
        FormInt.InterventionCur.CodeSet = intervCodeSet;
        FormInt.IsAllTypes = false;
        FormInt.IsSelectionMode = true;
        FormInt.ShowDialog();
        if (FormInt.DialogResult == DialogResult.OK)
        {
            boolean child = ageBeforeJanFirst < 17;
            fillGridInterventions(child);
        }
         
    }

    private void gridInterventions_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Object objCur = gridInterventions.getRows().get___idx(e.getRow()).getTag();
        if (StringSupport.equals(objCur.GetType().Name, "Intervention"))
        {
            //grid can contain MedicationPat or Intervention objects, launch appropriate window
            FormInterventionEdit FormInt = new FormInterventionEdit();
            FormInt.InterventionCur = (Intervention)objCur;
            FormInt.IsAllTypes = false;
            FormInt.IsSelectionMode = false;
            FormInt.ShowDialog();
        }
         
        if (StringSupport.equals(objCur.GetType().Name, "MedicationPat"))
        {
            FormMedPat FormMP = new FormMedPat();
            FormMP.MedicationPatCur = (MedicationPat)objCur;
            FormMP.IsNew = false;
            FormMP.ShowDialog();
        }
         
        fillGridInterventions(ageBeforeJanFirst < 17);
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (VitalsignCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        if (VitalsignCur.EhrNotPerformedNum != 0)
        {
            EhrNotPerformeds.delete(VitalsignCur.EhrNotPerformedNum);
        }
         
        Vitalsigns.delete(VitalsignCur.VitalsignNum);
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        DateTime date = new DateTime();
        if (StringSupport.equals(textDateTaken.Text, ""))
        {
            MsgBox.show(this,"Please enter a date.");
            return ;
        }
         
        try
        {
            date = DateTime.Parse(textDateTaken.Text);
        }
        catch (Exception __dummyCatchVar3)
        {
            MsgBox.show(this,"Please fix date first.");
            return ;
        }

        if (date < patCur.Birthdate)
        {
            MsgBox.show(this,"Exam date cannot be before the patient's birthdate.");
            return ;
        }
         
        //validate height
        float height = 0;
        try
        {
            if (!StringSupport.equals(textHeight.Text, ""))
            {
                height = float.Parse(textHeight.Text);
            }
             
        }
        catch (Exception __dummyCatchVar4)
        {
            MsgBox.show(this,"Please fix height first.");
            return ;
        }

        if (height < 0)
        {
            MsgBox.show(this,"Please fix height first.");
            return ;
        }
         
        //validate weight
        float weight = 0;
        try
        {
            if (!StringSupport.equals(textWeight.Text, ""))
            {
                weight = float.Parse(textWeight.Text);
            }
             
        }
        catch (Exception __dummyCatchVar5)
        {
            MsgBox.show(this,"Please fix weight first.");
            return ;
        }

        if (weight < 0)
        {
            MsgBox.show(this,"Please fix weight first.");
            return ;
        }
         
        //validate bp
        int BPsys = 0;
        int BPdia = 0;
        try
        {
            if (!StringSupport.equals(textBPs.Text, ""))
            {
                BPsys = int.Parse(textBPs.Text);
            }
             
            if (!StringSupport.equals(textBPd.Text, ""))
            {
                BPdia = int.Parse(textBPd.Text);
            }
             
        }
        catch (Exception __dummyCatchVar6)
        {
            MsgBox.show(this,"Please fix BP first.");
            return ;
        }

        if (BPsys < 0 || BPdia < 0)
        {
            MsgBox.show(this,"Please fix BP first.");
            return ;
        }
         
        VitalsignCur.DateTaken = date;
        VitalsignCur.Height = height;
        VitalsignCur.Weight = weight;
        VitalsignCur.BpDiastolic = BPdia;
        VitalsignCur.BpSystolic = BPsys;
        VitalsignCur.BMIExamCode = "";
        if (textBMIPercentileCode.Visible && !StringSupport.equals(textBMIPercentileCode.Text, ""))
        {
            VitalsignCur.BMIExamCode = "59576-9";
        }
         
        //Body mass index (BMI) [Percentile] Per age and gender, only code used for percentile, only visible if under 17 at time of exam
        if (textBMIExamCode.Visible)
        {
            VitalsignCur.BMIPercentile = PIn.Int(textBMIPercentile.Text);
        }
         
        //could be -1 if not in age range or error calculating percentile
        if (comboHeightExamCode.SelectedIndex > 0)
        {
            VitalsignCur.HeightExamCode = listHeightCodes[comboHeightExamCode.SelectedIndex - 1].LoincCode;
        }
         
        if (comboWeightExamCode.SelectedIndex > 0)
        {
            VitalsignCur.WeightExamCode = listWeightCodes[comboWeightExamCode.SelectedIndex - 1].LoincCode;
        }
         
        Text __dummyScrutVar2 = labelWeightCode.Text;
        if (__dummyScrutVar2.equals("Overweight"))
        {
            if (Snomeds.getByCode("238131007") != null)
            {
                VitalsignCur.WeightCode = "238131007";
            }
             
        }
        else if (__dummyScrutVar2.equals("Underweight"))
        {
            if (Snomeds.getByCode("248342006") != null)
            {
                VitalsignCur.WeightCode = "248342006";
            }
             
        }
        else
        {
            VitalsignCur.WeightCode = "";
        }  
        if (checkPregnant.Checked)
        {
            //pregnant, add pregnant dx if necessary
            if (pregDisDefNumCur == 0)
            {
                //shouldn't happen, if checked this must be set to either an existing problem def or a new problem that requires inserting, return to form with checkPregnant unchecked
                MsgBox.show(this,"This exam must point to a valid pregnancy diagnosis.");
                checkPregnant.Checked = false;
                labelPregNotice.Visible = false;
                return ;
            }
             
            if (VitalsignCur.PregDiseaseNum == 0)
            {
                //insert new preg disease and update vitalsign to point to it
                Disease pregDisNew = new Disease();
                pregDisNew.PatNum = VitalsignCur.PatNum;
                pregDisNew.DiseaseDefNum = pregDisDefNumCur;
                pregDisNew.DateStart = VitalsignCur.DateTaken;
                pregDisNew.ProbStatus = ProblemStatus.Active;
                VitalsignCur.PregDiseaseNum = Diseases.insert(pregDisNew);
            }
            else
            {
                Disease disCur = Diseases.getOne(VitalsignCur.PregDiseaseNum);
                if (VitalsignCur.DateTaken < disCur.DateStart || (disCur.DateStop.Year > 1880 && VitalsignCur.DateTaken > disCur.DateStop))
                {
                    //the current exam is no longer within dates of preg problem, uncheck the pregnancy box and remove the pointer to the disease
                    MsgBox.show(this,"This exam is not within the active dates of the attached pregnancy problem.");
                    checkPregnant.Checked = false;
                    textPregCode.Clear();
                    textPregCodeDescript.Clear();
                    labelPregNotice.Visible = false;
                    VitalsignCur.PregDiseaseNum = 0;
                    butChangeDefault.Text = "Change Default";
                    return ;
                }
                 
            } 
        }
        else
        {
            //checkPregnant not checked
            VitalsignCur.PregDiseaseNum = 0;
        } 
        if (!checkNotPerf.Checked)
        {
            if (VitalsignCur.EhrNotPerformedNum != 0)
            {
                EhrNotPerformeds.delete(VitalsignCur.EhrNotPerformedNum);
                VitalsignCur.EhrNotPerformedNum = 0;
            }
             
        }
         
        if (VitalsignCur.getIsNew())
        {
            Vitalsigns.insert(VitalsignCur);
        }
        else
        {
            Vitalsigns.update(VitalsignCur);
        } 
        if (CDSPermissions.getForUser(Security.getCurUser().UserNum).ShowCDS && CDSPermissions.getForUser(Security.getCurUser().UserNum).VitalCDS)
        {
            FormCDSIntervention FormCDSI = new FormCDSIntervention();
            FormCDSI.ListCDSI = EhrTriggers.triggerMatch(VitalsignCur,Patients.getPat(VitalsignCur.PatNum));
            FormCDSI.showIfRequired(false);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormVitalsignEdit2014.class);
        this.label1 = new System.Windows.Forms.Label();
        this.labelBMI = new System.Windows.Forms.Label();
        this.labelBPs = new System.Windows.Forms.Label();
        this.labelWeight = new System.Windows.Forms.Label();
        this.labelHeight = new System.Windows.Forms.Label();
        this.textDateTaken = new System.Windows.Forms.TextBox();
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.textBPd = new System.Windows.Forms.TextBox();
        this.textBPs = new System.Windows.Forms.TextBox();
        this.textWeight = new System.Windows.Forms.TextBox();
        this.textHeight = new System.Windows.Forms.TextBox();
        this.textBMI = new System.Windows.Forms.TextBox();
        this.labelWeightCode = new System.Windows.Forms.Label();
        this.comboHeightExamCode = new System.Windows.Forms.ComboBox();
        this.comboWeightExamCode = new System.Windows.Forms.ComboBox();
        this.labelHeightExamCode = new System.Windows.Forms.Label();
        this.labelWeightExamCode = new System.Windows.Forms.Label();
        this.labelBMIPercentileCode = new System.Windows.Forms.Label();
        this.labelBMIExamCode = new System.Windows.Forms.Label();
        this.textBMIExamCode = new System.Windows.Forms.TextBox();
        this.labelBPd = new System.Windows.Forms.Label();
        this.labelBPdExamCode = new System.Windows.Forms.Label();
        this.textBPdExamCode = new System.Windows.Forms.TextBox();
        this.labelBPsExamCode = new System.Windows.Forms.Label();
        this.textBPsExamCode = new System.Windows.Forms.TextBox();
        this.checkPregnant = new System.Windows.Forms.CheckBox();
        this.textPregCode = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.groupExclusion = new System.Windows.Forms.GroupBox();
        this.butChangeDefault = new System.Windows.Forms.Button();
        this.textReasonDescript = new OpenDental.ODtextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.labelNotPerf = new System.Windows.Forms.Label();
        this.textReasonCode = new System.Windows.Forms.TextBox();
        this.textPregCodeDescript = new OpenDental.ODtextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.checkNotPerf = new System.Windows.Forms.CheckBox();
        this.labelPregNotice = new System.Windows.Forms.Label();
        this.groupInterventions = new System.Windows.Forms.GroupBox();
        this.gridInterventions = new OpenDental.UI.ODGrid();
        this.butAdd = new System.Windows.Forms.Button();
        this.textBMIPercentile = new System.Windows.Forms.TextBox();
        this.labelBMIPercentile = new System.Windows.Forms.Label();
        this.textBMIPercentileCode = new System.Windows.Forms.TextBox();
        this.groupExclusion.SuspendLayout();
        this.groupInterventions.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(26, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(93, 17);
        this.label1.TabIndex = 0;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBMI
        //
        this.labelBMI.Location = new System.Drawing.Point(26, 139);
        this.labelBMI.Name = "labelBMI";
        this.labelBMI.Size = new System.Drawing.Size(93, 17);
        this.labelBMI.TabIndex = 2;
        this.labelBMI.Text = "BMI";
        this.labelBMI.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBPs
        //
        this.labelBPs.Location = new System.Drawing.Point(26, 44);
        this.labelBPs.Name = "labelBPs";
        this.labelBPs.Size = new System.Drawing.Size(93, 17);
        this.labelBPs.TabIndex = 4;
        this.labelBPs.Text = "Systolic BP";
        this.labelBPs.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelWeight
        //
        this.labelWeight.Location = new System.Drawing.Point(26, 116);
        this.labelWeight.Name = "labelWeight";
        this.labelWeight.Size = new System.Drawing.Size(93, 17);
        this.labelWeight.TabIndex = 5;
        this.labelWeight.Text = "Weight (.lbs)";
        this.labelWeight.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelHeight
        //
        this.labelHeight.Location = new System.Drawing.Point(26, 92);
        this.labelHeight.Name = "labelHeight";
        this.labelHeight.Size = new System.Drawing.Size(93, 17);
        this.labelHeight.TabIndex = 6;
        this.labelHeight.Text = "Height (in.)";
        this.labelHeight.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTaken
        //
        this.textDateTaken.Location = new System.Drawing.Point(122, 18);
        this.textDateTaken.Name = "textDateTaken";
        this.textDateTaken.Size = new System.Drawing.Size(80, 20);
        this.textDateTaken.TabIndex = 0;
        this.textDateTaken.Leave += new System.EventHandler(this.textDateTaken_Leave);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(513, 579);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 6;
        this.butCancel.Text = "&Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(432, 579);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "&OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(10, 579);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 10;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "&Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textBPd
        //
        this.textBPd.Location = new System.Drawing.Point(122, 66);
        this.textBPd.Name = "textBPd";
        this.textBPd.Size = new System.Drawing.Size(39, 20);
        this.textBPd.TabIndex = 6;
        this.textBPd.TextChanged += new System.EventHandler(this.textBPd_TextChanged);
        //
        // textBPs
        //
        this.textBPs.Location = new System.Drawing.Point(122, 42);
        this.textBPs.Name = "textBPs";
        this.textBPs.Size = new System.Drawing.Size(40, 20);
        this.textBPs.TabIndex = 5;
        this.textBPs.TextChanged += new System.EventHandler(this.textBPs_TextChanged);
        //
        // textWeight
        //
        this.textWeight.Location = new System.Drawing.Point(122, 114);
        this.textWeight.Name = "textWeight";
        this.textWeight.Size = new System.Drawing.Size(56, 20);
        this.textWeight.TabIndex = 3;
        this.textWeight.TextChanged += new System.EventHandler(this.textWeight_TextChanged);
        //
        // textHeight
        //
        this.textHeight.Location = new System.Drawing.Point(122, 90);
        this.textHeight.Name = "textHeight";
        this.textHeight.Size = new System.Drawing.Size(56, 20);
        this.textHeight.TabIndex = 1;
        this.textHeight.TextChanged += new System.EventHandler(this.textHeight_TextChanged);
        //
        // textBMI
        //
        this.textBMI.Location = new System.Drawing.Point(122, 137);
        this.textBMI.Name = "textBMI";
        this.textBMI.ReadOnly = true;
        this.textBMI.Size = new System.Drawing.Size(40, 20);
        this.textBMI.TabIndex = 17;
        this.textBMI.TabStop = false;
        //
        // labelWeightCode
        //
        this.labelWeightCode.Location = new System.Drawing.Point(168, 139);
        this.labelWeightCode.Name = "labelWeightCode";
        this.labelWeightCode.Size = new System.Drawing.Size(82, 17);
        this.labelWeightCode.TabIndex = 24;
        this.labelWeightCode.Text = "Over/Under";
        this.labelWeightCode.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // comboHeightExamCode
        //
        this.comboHeightExamCode.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboHeightExamCode.Location = new System.Drawing.Point(434, 89);
        this.comboHeightExamCode.MaxDropDownItems = 30;
        this.comboHeightExamCode.Name = "comboHeightExamCode";
        this.comboHeightExamCode.Size = new System.Drawing.Size(158, 21);
        this.comboHeightExamCode.TabIndex = 2;
        //
        // comboWeightExamCode
        //
        this.comboWeightExamCode.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboWeightExamCode.Location = new System.Drawing.Point(434, 113);
        this.comboWeightExamCode.MaxDropDownItems = 30;
        this.comboWeightExamCode.Name = "comboWeightExamCode";
        this.comboWeightExamCode.Size = new System.Drawing.Size(158, 21);
        this.comboWeightExamCode.TabIndex = 4;
        //
        // labelHeightExamCode
        //
        this.labelHeightExamCode.Location = new System.Drawing.Point(301, 92);
        this.labelHeightExamCode.Name = "labelHeightExamCode";
        this.labelHeightExamCode.Size = new System.Drawing.Size(130, 17);
        this.labelHeightExamCode.TabIndex = 28;
        this.labelHeightExamCode.Text = "Height Code";
        this.labelHeightExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelWeightExamCode
        //
        this.labelWeightExamCode.Location = new System.Drawing.Point(301, 116);
        this.labelWeightExamCode.Name = "labelWeightExamCode";
        this.labelWeightExamCode.Size = new System.Drawing.Size(130, 17);
        this.labelWeightExamCode.TabIndex = 27;
        this.labelWeightExamCode.Text = "Weight Code";
        this.labelWeightExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBMIPercentileCode
        //
        this.labelBMIPercentileCode.Location = new System.Drawing.Point(301, 163);
        this.labelBMIPercentileCode.Name = "labelBMIPercentileCode";
        this.labelBMIPercentileCode.Size = new System.Drawing.Size(130, 17);
        this.labelBMIPercentileCode.TabIndex = 30;
        this.labelBMIPercentileCode.Text = "BMI Percentile Code";
        this.labelBMIPercentileCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelBMIPercentileCode.Visible = false;
        //
        // labelBMIExamCode
        //
        this.labelBMIExamCode.Location = new System.Drawing.Point(301, 139);
        this.labelBMIExamCode.Name = "labelBMIExamCode";
        this.labelBMIExamCode.Size = new System.Drawing.Size(130, 17);
        this.labelBMIExamCode.TabIndex = 32;
        this.labelBMIExamCode.Text = "BMI Code";
        this.labelBMIExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBMIExamCode
        //
        this.textBMIExamCode.Location = new System.Drawing.Point(434, 137);
        this.textBMIExamCode.Name = "textBMIExamCode";
        this.textBMIExamCode.ReadOnly = true;
        this.textBMIExamCode.Size = new System.Drawing.Size(95, 20);
        this.textBMIExamCode.TabIndex = 31;
        //
        // labelBPd
        //
        this.labelBPd.Location = new System.Drawing.Point(26, 68);
        this.labelBPd.Name = "labelBPd";
        this.labelBPd.Size = new System.Drawing.Size(93, 17);
        this.labelBPd.TabIndex = 33;
        this.labelBPd.Text = "Diastolic BP";
        this.labelBPd.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBPdExamCode
        //
        this.labelBPdExamCode.Location = new System.Drawing.Point(301, 68);
        this.labelBPdExamCode.Name = "labelBPdExamCode";
        this.labelBPdExamCode.Size = new System.Drawing.Size(130, 17);
        this.labelBPdExamCode.TabIndex = 35;
        this.labelBPdExamCode.Text = "Diastolic BP Code";
        this.labelBPdExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBPdExamCode
        //
        this.textBPdExamCode.Location = new System.Drawing.Point(434, 66);
        this.textBPdExamCode.Name = "textBPdExamCode";
        this.textBPdExamCode.ReadOnly = true;
        this.textBPdExamCode.Size = new System.Drawing.Size(95, 20);
        this.textBPdExamCode.TabIndex = 34;
        //
        // labelBPsExamCode
        //
        this.labelBPsExamCode.Location = new System.Drawing.Point(301, 44);
        this.labelBPsExamCode.Name = "labelBPsExamCode";
        this.labelBPsExamCode.Size = new System.Drawing.Size(130, 17);
        this.labelBPsExamCode.TabIndex = 37;
        this.labelBPsExamCode.Text = "Systolic BP Code";
        this.labelBPsExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBPsExamCode
        //
        this.textBPsExamCode.Location = new System.Drawing.Point(434, 42);
        this.textBPsExamCode.Name = "textBPsExamCode";
        this.textBPsExamCode.ReadOnly = true;
        this.textBPsExamCode.Size = new System.Drawing.Size(95, 20);
        this.textBPsExamCode.TabIndex = 36;
        //
        // checkPregnant
        //
        this.checkPregnant.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkPregnant.Location = new System.Drawing.Point(6, 16);
        this.checkPregnant.Name = "checkPregnant";
        this.checkPregnant.Size = new System.Drawing.Size(261, 44);
        this.checkPregnant.TabIndex = 25;
        this.checkPregnant.Text = "Height and Weight was not recorded because the patient is pregnant or has been pr" + "egnant any time during the measurement period.";
        this.checkPregnant.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkPregnant.UseVisualStyleBackColor = true;
        this.checkPregnant.Click += new System.EventHandler(this.checkPregnant_Click);
        //
        // textPregCode
        //
        this.textPregCode.Location = new System.Drawing.Point(377, 14);
        this.textPregCode.Name = "textPregCode";
        this.textPregCode.ReadOnly = true;
        this.textPregCode.Size = new System.Drawing.Size(100, 20);
        this.textPregCode.TabIndex = 128;
        //
        // label6
        //
        this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label6.Location = new System.Drawing.Point(273, 15);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(101, 17);
        this.label6.TabIndex = 129;
        this.label6.Text = "Pregnancy Code";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupExclusion
        //
        this.groupExclusion.Controls.Add(this.butChangeDefault);
        this.groupExclusion.Controls.Add(this.textReasonDescript);
        this.groupExclusion.Controls.Add(this.label4);
        this.groupExclusion.Controls.Add(this.labelNotPerf);
        this.groupExclusion.Controls.Add(this.textReasonCode);
        this.groupExclusion.Controls.Add(this.textPregCodeDescript);
        this.groupExclusion.Controls.Add(this.label3);
        this.groupExclusion.Controls.Add(this.checkNotPerf);
        this.groupExclusion.Controls.Add(this.labelPregNotice);
        this.groupExclusion.Controls.Add(this.label6);
        this.groupExclusion.Controls.Add(this.textPregCode);
        this.groupExclusion.Controls.Add(this.checkPregnant);
        this.groupExclusion.Location = new System.Drawing.Point(10, 186);
        this.groupExclusion.Name = "groupExclusion";
        this.groupExclusion.Size = new System.Drawing.Size(578, 180);
        this.groupExclusion.TabIndex = 23;
        this.groupExclusion.TabStop = false;
        this.groupExclusion.Text = "Exclusion From BMI Exam";
        //
        // butChangeDefault
        //
        this.butChangeDefault.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butChangeDefault.Location = new System.Drawing.Point(483, 12);
        this.butChangeDefault.Name = "butChangeDefault";
        this.butChangeDefault.Size = new System.Drawing.Size(89, 23);
        this.butChangeDefault.TabIndex = 143;
        this.butChangeDefault.Text = "Change Default";
        this.butChangeDefault.UseVisualStyleBackColor = true;
        this.butChangeDefault.Click += new System.EventHandler(this.butChangeDefault_Click);
        //
        // textReasonDescript
        //
        this.textReasonDescript.AcceptsTab = true;
        this.textReasonDescript.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textReasonDescript.DetectUrls = false;
        this.textReasonDescript.Location = new System.Drawing.Point(377, 133);
        this.textReasonDescript.Name = "textReasonDescript";
        this.textReasonDescript.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textReasonDescript.ReadOnly = true;
        this.textReasonDescript.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textReasonDescript.Size = new System.Drawing.Size(195, 40);
        this.textReasonDescript.TabIndex = 141;
        this.textReasonDescript.Text = "";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(273, 134);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(101, 17);
        this.label4.TabIndex = 142;
        this.label4.Text = "Description";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelNotPerf
        //
        this.labelNotPerf.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelNotPerf.Location = new System.Drawing.Point(273, 108);
        this.labelNotPerf.Name = "labelNotPerf";
        this.labelNotPerf.Size = new System.Drawing.Size(101, 17);
        this.labelNotPerf.TabIndex = 140;
        this.labelNotPerf.Text = "Reason Code";
        this.labelNotPerf.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textReasonCode
        //
        this.textReasonCode.Location = new System.Drawing.Point(377, 107);
        this.textReasonCode.Name = "textReasonCode";
        this.textReasonCode.ReadOnly = true;
        this.textReasonCode.Size = new System.Drawing.Size(100, 20);
        this.textReasonCode.TabIndex = 139;
        //
        // textPregCodeDescript
        //
        this.textPregCodeDescript.AcceptsTab = true;
        this.textPregCodeDescript.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textPregCodeDescript.DetectUrls = false;
        this.textPregCodeDescript.Location = new System.Drawing.Point(377, 40);
        this.textPregCodeDescript.Name = "textPregCodeDescript";
        this.textPregCodeDescript.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textPregCodeDescript.ReadOnly = true;
        this.textPregCodeDescript.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textPregCodeDescript.Size = new System.Drawing.Size(195, 40);
        this.textPregCodeDescript.TabIndex = 132;
        this.textPregCodeDescript.Text = "";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(273, 41);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(101, 17);
        this.label3.TabIndex = 133;
        this.label3.Text = "Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkNotPerf
        //
        this.checkNotPerf.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkNotPerf.Location = new System.Drawing.Point(6, 108);
        this.checkNotPerf.Name = "checkNotPerf";
        this.checkNotPerf.Size = new System.Drawing.Size(261, 31);
        this.checkNotPerf.TabIndex = 131;
        this.checkNotPerf.Text = "Height and Weight was not recorded for a medical or other reason.";
        this.checkNotPerf.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkNotPerf.UseVisualStyleBackColor = true;
        this.checkNotPerf.Click += new System.EventHandler(this.checkNotPerf_Click);
        //
        // labelPregNotice
        //
        this.labelPregNotice.ForeColor = System.Drawing.Color.Red;
        this.labelPregNotice.Location = new System.Drawing.Point(24, 61);
        this.labelPregNotice.Name = "labelPregNotice";
        this.labelPregNotice.Size = new System.Drawing.Size(243, 39);
        this.labelPregNotice.TabIndex = 130;
        this.labelPregNotice.Text = "A diagnosis of pregnancy with this code will be added to the patient\'s medical hi" + "story with a start date equal to the date of this exam.";
        this.labelPregNotice.Visible = false;
        //
        // groupInterventions
        //
        this.groupInterventions.Controls.Add(this.gridInterventions);
        this.groupInterventions.Controls.Add(this.butAdd);
        this.groupInterventions.Location = new System.Drawing.Point(10, 372);
        this.groupInterventions.Name = "groupInterventions";
        this.groupInterventions.Size = new System.Drawing.Size(578, 198);
        this.groupInterventions.TabIndex = 161;
        this.groupInterventions.TabStop = false;
        this.groupInterventions.Text = "Interventions";
        //
        // gridInterventions
        //
        this.gridInterventions.setHScrollVisible(false);
        this.gridInterventions.Location = new System.Drawing.Point(6, 19);
        this.gridInterventions.Name = "gridInterventions";
        this.gridInterventions.setScrollValue(0);
        this.gridInterventions.Size = new System.Drawing.Size(485, 169);
        this.gridInterventions.TabIndex = 161;
        this.gridInterventions.setTitle("Interventions and/or Medications");
        this.gridInterventions.setTranslationName(null);
        this.gridInterventions.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridInterventions.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridInterventions_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAdd
        //
        this.butAdd.Location = new System.Drawing.Point(497, 19);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 23);
        this.butAdd.TabIndex = 153;
        this.butAdd.Text = "Add";
        this.butAdd.UseVisualStyleBackColor = true;
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // textBMIPercentile
        //
        this.textBMIPercentile.Location = new System.Drawing.Point(122, 161);
        this.textBMIPercentile.Name = "textBMIPercentile";
        this.textBMIPercentile.ReadOnly = true;
        this.textBMIPercentile.Size = new System.Drawing.Size(40, 20);
        this.textBMIPercentile.TabIndex = 163;
        this.textBMIPercentile.TabStop = false;
        this.textBMIPercentile.Visible = false;
        //
        // labelBMIPercentile
        //
        this.labelBMIPercentile.Location = new System.Drawing.Point(26, 163);
        this.labelBMIPercentile.Name = "labelBMIPercentile";
        this.labelBMIPercentile.Size = new System.Drawing.Size(93, 17);
        this.labelBMIPercentile.TabIndex = 162;
        this.labelBMIPercentile.Text = "BMI Percentile";
        this.labelBMIPercentile.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelBMIPercentile.Visible = false;
        //
        // textBMIPercentileCode
        //
        this.textBMIPercentileCode.Location = new System.Drawing.Point(434, 161);
        this.textBMIPercentileCode.Name = "textBMIPercentileCode";
        this.textBMIPercentileCode.ReadOnly = true;
        this.textBMIPercentileCode.Size = new System.Drawing.Size(95, 20);
        this.textBMIPercentileCode.TabIndex = 164;
        this.textBMIPercentileCode.Visible = false;
        //
        // FormVitalsignEdit2014
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(602, 614);
        this.Controls.Add(this.textBMIPercentileCode);
        this.Controls.Add(this.textBMIPercentile);
        this.Controls.Add(this.labelBMIPercentile);
        this.Controls.Add(this.groupInterventions);
        this.Controls.Add(this.labelBPsExamCode);
        this.Controls.Add(this.textBPsExamCode);
        this.Controls.Add(this.labelBPdExamCode);
        this.Controls.Add(this.textBPdExamCode);
        this.Controls.Add(this.labelBPd);
        this.Controls.Add(this.labelBMIExamCode);
        this.Controls.Add(this.textBMIExamCode);
        this.Controls.Add(this.labelBMIPercentileCode);
        this.Controls.Add(this.labelHeightExamCode);
        this.Controls.Add(this.labelWeightExamCode);
        this.Controls.Add(this.comboWeightExamCode);
        this.Controls.Add(this.labelWeightCode);
        this.Controls.Add(this.comboHeightExamCode);
        this.Controls.Add(this.groupExclusion);
        this.Controls.Add(this.textBMI);
        this.Controls.Add(this.textBPd);
        this.Controls.Add(this.textBPs);
        this.Controls.Add(this.textHeight);
        this.Controls.Add(this.textWeight);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textDateTaken);
        this.Controls.Add(this.labelHeight);
        this.Controls.Add(this.labelWeight);
        this.Controls.Add(this.labelBPs);
        this.Controls.Add(this.labelBMI);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormVitalsignEdit2014";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Vitalsign";
        this.Load += new System.EventHandler(this.FormVitalsignEdit2014_Load);
        this.groupExclusion.ResumeLayout(false);
        this.groupExclusion.PerformLayout();
        this.groupInterventions.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBMI = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBPs = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelWeight = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelHeight = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDateTaken = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textBPd = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBPs = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textWeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textHeight = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBMI = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelWeightCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboHeightExamCode = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboWeightExamCode = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelHeightExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelWeightExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBMIPercentileCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBMIExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBMIExamCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelBPd = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBPdExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBPdExamCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelBPsExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBPsExamCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkPregnant = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textPregCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupExclusion = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkNotPerf = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label labelPregNotice = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textPregCodeDescript;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textReasonDescript;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelNotPerf = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textReasonCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Button butChangeDefault = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupInterventions = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.ODGrid gridInterventions;
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textBMIPercentile = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelBMIPercentile = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBMIPercentileCode = new System.Windows.Forms.TextBox();
}


