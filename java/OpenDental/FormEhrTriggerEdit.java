//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormAllergySetup;
import OpenDental.FormCDSILabResult;
import OpenDental.FormDiseaseDefs;
import OpenDental.FormIcd9s;
import OpenDental.FormMedications;
import OpenDental.FormRxNorms;
import OpenDental.FormSnomeds;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.Cvxs;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrTrigger;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.MatchCardinality;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.Security;
import OpenDentBusiness.Snomeds;
import OpenDental.Properties.Resources;

public class FormEhrTriggerEdit  extends Form 
{

    public boolean IsNew = new boolean();
    public EhrTrigger EhrTriggerCur;
    public FormEhrTriggerEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrTriggerEdit_Load(Object sender, EventArgs e) throws Exception {
        if (!CDSPermissions.getForUser(Security.getCurUser().UserNum).EditBibliography)
        {
            textBibliography.Enabled = false;
            textInstruction.Enabled = false;
        }
         
        textDescription.Text = EhrTriggerCur.Description;
        textBibliography.Text = EhrTriggerCur.Bibliography;
        textInstruction.Text = EhrTriggerCur.Instructions;
        fillComboCardinality();
        fillGrid();
    }

    private void fillComboCardinality() throws Exception {
        String[] names = Enum.GetNames(MatchCardinality.class);
        for (int i = 0;i < names.Length;i++)
        {
            comboCardinality.Items.Add(names[i]);
        }
        comboCardinality.SelectedIndex = ((Enum)EhrTriggerCur.Cardinality).ordinal();
    }

    private void comboCardinality_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        EhrTriggerCur.Cardinality = (MatchCardinality)comboCardinality.SelectedIndex;
        switch(EhrTriggerCur.Cardinality)
        {
            case One: 
                labelCardinality.Text = "For this trigger to provide Clinical Decision Support, only one of the conditions below must be met.";
                break;
            case OneOfEachCategory: 
                labelCardinality.Text = "For this trigger to provide Clinical Decision Support, at least one condition from each category must be met. Categories are Problem, Medication, Allergy, Demographics, Lab Results, and Vital Signs.";
                break;
            case TwoOrMore: 
                labelCardinality.Text = "For this trigger to provide Clinical Decision Support, any two of the conditions below must be met.";
                break;
            case All: 
                labelCardinality.Text = "For this trigger to provide Clinical Decision Support, all of the conditions below must be met.";
                break;
        
        }
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Category",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Code",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("CodeSystem",120);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Op+Value",80);//Example: >=150
        //gridMain.Columns.Add(col);
        col = new ODGridColumn("Description",250);
        //Also includes values for labloinc and demographics and vitals. Example: ">150, BP Systolic"
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        //EhrTriggerCur.ProblemDefNumList-----------------------------------------------------------------------------------------------------------------------
        String[] arrayString = EhrTriggerCur.ProblemDefNumList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Problem");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("Problem Def");
            row.getCells().Add(DiseaseDefs.GetItem(PIn.Long(arrayString[i])).DiseaseName);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.ProblemIcd9List---------------------------------------------------------------------------------------------------------------------------
        arrayString = EhrTriggerCur.ProblemIcd9List.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Problem");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("ICD9 CM");
            row.getCells().Add(ICD9s.GetByCode(arrayString[i]).Description);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.ProblemIcd10List;
        arrayString = EhrTriggerCur.ProblemIcd10List.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Problem");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("ICD10 CM");
            row.getCells().Add(Icd10s.GetByCode(arrayString[i]).Description);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.ProblemSnomedList;
        arrayString = EhrTriggerCur.ProblemSnomedList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Problem");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("SNOMED CT");
            row.getCells().Add(Snomeds.GetByCode(arrayString[i]).Description);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.MedicationNumList
        arrayString = EhrTriggerCur.MedicationNumList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Medication");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("Medication Def");
            row.getCells().Add(Medications.GetDescription(PIn.Long(arrayString[i])));
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.RxCuiList
        arrayString = EhrTriggerCur.RxCuiList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Medication");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("RxCui");
            row.getCells().Add(RxNorms.GetByRxCUI(arrayString[i]).Description);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.CvxList
        arrayString = EhrTriggerCur.CvxList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Medication");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("Cvx");
            row.getCells().Add(Cvxs.GetByCode(arrayString[i]).Description);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.AllergyDefNumList
        arrayString = EhrTriggerCur.AllergyDefNumList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add("Allergy");
            row.getCells().Add(arrayString[i]);
            row.getCells().add("Allergy Def");
            row.getCells().Add(AllergyDefs.GetOne(PIn.Long(arrayString[i])).Description);
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.DemographicsList
        arrayString = EhrTriggerCur.DemographicsList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            String[] arrayStringElements = arrayString[i].Split(new String[]{ "," }, StringSplitOptions.RemoveEmptyEntries);
            System.Array<System.String>.INDEXER __dummyScrutVar1 = arrayStringElements[0];
            if (__dummyScrutVar1.equals("age"))
            {
                row.getCells().add("Demographic");
                row.getCells().add("30525-0");
                row.getCells().add("LOINC");
                row.getCells().add("Age" + arrayStringElements[1]);
                //Example "Age>55"
                gridMain.getRows().add(row);
            }
            else if (__dummyScrutVar1.equals("gender"))
            {
                row.getCells().add("Demographic");
                row.getCells().add("46098-0");
                row.getCells().add("LOINC");
                row.getCells().add("Gender:" + arrayString[i].Replace("gender,", ""));
                //Example "Gender:Male, Female, Unknown/Undifferentiated"
                gridMain.getRows().add(row);
            }
            else
            {
                continue;
            }  
        }
        //should never happen
        //next trigger
        //EhrTriggerCur.LabLoincList
        arrayString = EhrTriggerCur.LabLoincList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            Loinc _loincTemp = Loincs.GetByCode(arrayString[i]);
            //.Split(new string[] { ";" },StringSplitOptions.None)[0]);
            if (_loincTemp == null)
            {
                continue;
            }
             
            row.getCells().add("Laboratory");
            row.getCells().add(_loincTemp.LoincCode);
            row.getCells().add("LOINC");
            row.getCells().add(_loincTemp.NameShort);
            //switch(arrayString[i].Split(new string[] { ";" },StringSplitOptions.RemoveEmptyEntries).Length) {
            //	case 1://loinc only comparison
            //		row.Cells.Add(_loincTemp.NameShort);
            //		break;
            //	case 2://microbiology or unitless lab.
            //		Snomed _snomedTemp=Snomeds.GetByCode(arrayString[i].Split(new string[] { ";" },StringSplitOptions.None)[1]);
            //		row.Cells.Add(_loincTemp.NameShort+", "
            //			+(_snomedTemp==null?arrayString[i].Split(new string[] { ";" },StringSplitOptions.None)[1]:_snomedTemp.Description));//Example: Bacteria Identified, Campylobacter jenuni
            //		break;
            //	case 3://"traditional lab results"
            //		row.Cells.Add(_loincTemp.NameShort+" "
            //	+arrayString[i].Split(new string[] { ";" },StringSplitOptions.None)[1]+" "//example: >150 or a snomed code if microbiology
            //	+arrayString[i].Split(new string[] { ";" },StringSplitOptions.None)[2]    //example: mg/dL or blank
            //			);
            //		break;
            //	default://should never happen. Will display blank.
            //		row.Cells.Add("");
            //		break;
            //}
            gridMain.getRows().add(row);
        }
        //EhrTriggerCur.VitalLoincList
        arrayString = EhrTriggerCur.VitalLoincList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < arrayString.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            String[] arrayStringElements = arrayString[i].Split(new String[]{ "," }, StringSplitOptions.RemoveEmptyEntries);
            System.Array<System.String>.INDEXER __dummyScrutVar2 = arrayStringElements[0];
            if (__dummyScrutVar2.equals("height"))
            {
                row.getCells().add("Vitals");
                row.getCells().add("8302-2");
                row.getCells().add("LOINC");
                row.getCells().add("Height" + arrayString[i].Replace("height,", "") + " in.");
                //Example "Age>55"
                gridMain.getRows().add(row);
            }
            else if (__dummyScrutVar2.equals("weight"))
            {
                row.getCells().add("Vitals");
                row.getCells().add("29463-7");
                row.getCells().add("LOINC");
                row.getCells().add("Weight:" + arrayString[i].Replace("weight,", ""));
                //Example "Gender:Male, Female, Unknown/Undifferentiated"
                gridMain.getRows().add(row);
            }
            else if (__dummyScrutVar2.equals("bp???"))
            {
                row.getCells().add("Vitals");
                row.getCells().add("???There are two.");
                row.getCells().add("LOINC");
                row.getCells().add("???");
                //Example "Gender:Male, Female, Unknown/Undifferentiated"
                gridMain.getRows().add(row);
            }
            else if (__dummyScrutVar2.equals("BMI"))
            {
                row.getCells().add("Vitals");
                row.getCells().add("39156-5");
                row.getCells().add("LOINC");
                row.getCells().add("BMI" + arrayString[i].Replace("BMI,", "").Replace("%", "") + "%");
                //Example "Gender:Male, Female, Unknown/Undifferentiated"
                gridMain.getRows().add(row);
            }
            else
            {
                continue;
            }    
        }
        //should never happen
        //next trigger
        //End trigger fields.
        gridMain.endUpdate();
    }

    private void butAddProblem_Click(Object sender, EventArgs e) throws Exception {
        FormDiseaseDefs FormDD = new FormDiseaseDefs();
        FormDD.IsSelectionMode = true;
        FormDD.ShowDialog();
        if (FormDD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DiseaseDef diseaseDef = DiseaseDefs.getItem(FormDD.SelectedDiseaseDefNum);
        //DiseaseDefNum
        if (!EhrTriggerCur.ProblemDefNumList.Contains(" " + diseaseDef.DiseaseDefNum + " "))
        {
            EhrTriggerCur.ProblemDefNumList += " " + diseaseDef.DiseaseDefNum + " ";
        }
         
        //Icd9Num
        if (!StringSupport.equals(diseaseDef.ICD9Code, "") && !EhrTriggerCur.ProblemIcd9List.Contains(" " + diseaseDef.ICD9Code + " "))
        {
            EhrTriggerCur.ProblemIcd9List += " " + diseaseDef.ICD9Code + " ";
        }
         
        //Icd10Num
        if (!StringSupport.equals(diseaseDef.Icd10Code, "") && !EhrTriggerCur.ProblemIcd9List.Contains(" " + diseaseDef.Icd10Code + " "))
        {
            EhrTriggerCur.ProblemIcd10List += " " + diseaseDef.Icd10Code + " ";
        }
         
        //Snomed
        if (!StringSupport.equals(diseaseDef.SnomedCode, "") && !EhrTriggerCur.ProblemIcd9List.Contains(" " + diseaseDef.SnomedCode + " "))
        {
            EhrTriggerCur.ProblemSnomedList += " " + diseaseDef.SnomedCode + " ";
        }
         
        fillGrid();
    }

    private void butAddIcd9_Click(Object sender, EventArgs e) throws Exception {
        FormIcd9s FormI9 = new FormIcd9s();
        FormI9.IsSelectionMode = true;
        FormI9.ShowDialog();
        if (FormI9.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrTriggerCur.ProblemIcd9List += " " + FormI9.SelectedIcd9.ICD9Code + " ";
        fillGrid();
    }

    private void butAddIcd10_Click(Object sender, EventArgs e) throws Exception {
    }

    //FormIcd10s FormI10=new FormIcd10s();
    //FormI10.IsSelectionMode=true;
    //FormI10.ShowDialog();
    //if(FormI10.DialogResult!=DialogResult.OK) {
    //	return;
    //}
    //EhrTriggerCur.ProblemIcd10List+=" "+FormI10.SelectedI10.ICD9Code+" ";
    //FillGrid();
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
            EhrTriggerCur.ProblemSnomedList += " " + FormS.ListSelectedSnomeds[i].SnomedCode + " ";
        }
        fillGrid();
    }

    private void butAddMed_Click(Object sender, EventArgs e) throws Exception {
        FormMedications FormM = new FormMedications();
        FormM.IsSelectionMode = true;
        FormM.ShowDialog();
        if (FormM.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Medication m = Medications.getMedication(FormM.SelectedMedicationNum);
        EhrTriggerCur.MedicationNumList += " " + m.MedicationNum + " ";
        if (m.RxCui != 0)
        {
            EhrTriggerCur.RxCuiList += " " + m.RxCui + " ";
        }
         
        fillGrid();
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
            EhrTriggerCur.RxCuiList += " " + FormRXN.ListSelectedRxNorms[i].RxCui + " ";
        }
        fillGrid();
    }

    private void butAddCvx_Click(Object sender, EventArgs e) throws Exception {
    }

    //FormCvxs FormC=new FormCvxs();
    //FormC.IsSelectionMode=true;
    //FormC.ShowDialog();
    //if(FormC.DialogResult!=DialogResult.OK) {
    //	return;
    //}
    //EhrTriggerCur.CvxList+=" "+FormC.SelectedCvx.CvxCode+" ";
    //FillGrid();
    private void butAddAllergy_Click(Object sender, EventArgs e) throws Exception {
        FormAllergySetup FormAS = new FormAllergySetup();
        FormAS.IsSelectionMode = true;
        FormAS.ShowDialog();
        if (FormAS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrTriggerCur.AllergyDefNumList += " " + FormAS.SelectedAllergyDefNum + " ";
        fillGrid();
    }

    private void butAddAge_Click(Object sender, EventArgs e) throws Exception {
        //30525-0 = Age (Actual). There are 3 other age LOINCS that should also be checked.  Stored as " Age(Operand)(Value) "
        //21611-7 = Estimated
        //21612-7 = Reported
        //29553-5 = Calculated
        InputBox IB = new InputBox(Lan.g(this,"Input age criterion as (operand)(value). Examples: <18, >55, =22, <=35."));
        IB.ShowDialog();
        if (!Regex.IsMatch(IB.textResult.Text, "^(<|<=|>|>=|=)\\d+$"))
        {
            //Starts with <,>,=,<=, or >= followed by numbers, and nothing else.
            MsgBox.show(this,"Invalid format.");
            return ;
        }
         
        EhrTriggerCur.DemographicsList += " age," + IB.textResult.Text.Trim() + " ";
        fillGrid();
    }

    private void butAddGender_Click(Object sender, EventArgs e) throws Exception {
        //46098-0 = Gender. There are 3 other age LOINCS that should also be checked.  Stored as " Age(Operand)(Value) "
        //Other Gender LoincCodes include 21840-4,46607-8,54131-8, and 72143-1
        InputBox IB = new InputBox(Lan.g(this,"Input genders.  Example: male,female,unknown"));
        //Fill inputBox with current gender codes.---------------------------------------------------------
        if (EhrTriggerCur.DemographicsList.Contains("gender"))
        {
            String[] arrayString = EhrTriggerCur.DemographicsList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
            for (int i = 0;i < arrayString.Length;i++)
            {
                if (arrayString[i].StartsWith("gender"))
                {
                    IB.textResult.Text = arrayString[i].Replace("gender,", "");
                    break;
                }
                 
            }
        }
         
        //end if gender
        IB.ShowDialog();
        if (!StringSupport.equals(IB.textResult.Text, "") && !Regex.IsMatch(IB.textResult.Text, "^(male|female|unknown)(,(male|female|unknown)){0,2}$"))
        {
            //m,f,u optionally followed by a comma delimited list with optional white space after comma.
            MsgBox.show(this,"Invalid format.");
            return ;
        }
         
        //remove current gender codes-------------------
        if (EhrTriggerCur.DemographicsList.Contains("gender"))
        {
            String[] arrayString = EhrTriggerCur.DemographicsList.Split(new String[]{ " " }, StringSplitOptions.RemoveEmptyEntries);
            for (int i = 0;i < arrayString.Length;i++)
            {
                if (arrayString[i].StartsWith("gender"))
                {
                    EhrTriggerCur.DemographicsList = EhrTriggerCur.DemographicsList.Replace(" " + arrayString[i] + " ", "");
                    continue;
                }
                 
            }
        }
         
        if (StringSupport.equals(IB.textResult.Text, ""))
        {
            fillGrid();
            return ;
        }
         
        //Add new gender codes.
        EhrTriggerCur.DemographicsList += " gender," + IB.textResult.Text.Trim() + " ";
        fillGrid();
    }

    private void butAddLab_Click(Object sender, EventArgs e) throws Exception {
        FormCDSILabResult FormCLR = new FormCDSILabResult();
        FormCLR.ShowDialog();
        if (FormCLR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EhrTriggerCur.LabLoincList += " " + FormCLR.ResultCDSITriggerText + " ";
        fillGrid();
    }

    private void butAddHeight_Click(Object sender, EventArgs e) throws Exception {
        //8302-2 = height
        InputBox IB = new InputBox(Lan.g(this,"Input height criterion as (operand)(value in inches). Examples: >80, <=48.5"));
        IB.ShowDialog();
        if (!Regex.IsMatch(IB.textResult.Text, "^^(<|<=|>|>=|=)(\\d)+(.(\\d)+)*$"))
        {
            //Starts with <,>,=,<=, or >= followed by a float, and nothing else.
            MsgBox.show(this,"Invalid format.");
            return ;
        }
         
        EhrTriggerCur.VitalLoincList += " height," + IB.textResult.Text.Trim() + " ";
        fillGrid();
    }

    private void butAddWeight_Click(Object sender, EventArgs e) throws Exception {
        //29463-7 = weight
        InputBox IB = new InputBox(Lan.g(this,"Input weight criterion as (operand)(value). Examples: <=99.5, >=300"));
        IB.ShowDialog();
        if (!Regex.IsMatch(IB.textResult.Text, "^(<|<=|>|>=|=)(\\d)+(.(\\d)+)*$"))
        {
            //Starts with <,>,=,<=, or >= followed by a float, and nothing else.
            MsgBox.show(this,"Invalid format.");
            return ;
        }
         
        EhrTriggerCur.VitalLoincList += " weight," + IB.textResult.Text.Trim() + " ";
        fillGrid();
    }

    private void butAddBP_Click(Object sender, EventArgs e) throws Exception {
    }

    //TODO:
    //InputBox IB=new InputBox(Lan.g(this,"Input BP criterion."));
    //IB.ShowDialog();
    //if(false && !Regex.IsMatch(IB.textResult.Text,@"^(<|<=|>|>=|=)\d+$")) {//Starts with <,>,=,<=, or >= followed by numbers, and nothing else.
    //	MsgBox.Show(this,"Invalid format.");
    //	return;
    //}
    //EhrTriggerCur.VitalLoincList+= " BP???,"+IB.textResult.Text.Trim()+" ";
    //FillGrid();
    private void butAddBMI_Click(Object sender, EventArgs e) throws Exception {
        //39156-5 = BMI
        InputBox IB = new InputBox(Lan.g(this,"Input BMI criterion. Examples: <5, >=27.5%"));
        IB.ShowDialog();
        if (!Regex.IsMatch(IB.textResult.Text, "^(<|<=|>|>=|=)(\\d)+(.(\\d)+)*(%){0,1}$"))
        {
            //operand followed by valid float followed by an optional percent sign.
            MsgBox.show(this,"Invalid format.");
            return ;
        }
         
        EhrTriggerCur.VitalLoincList += " BMI," + IB.textResult.Text.Trim() + " ";
        fillGrid();
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        EhrTriggers.delete(EhrTriggerCur.EhrTriggerNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        EhrTriggerCur.Description = textDescription.Text;
        EhrTriggerCur.Instructions = textInstruction.Text;
        EhrTriggerCur.Bibliography = textBibliography.Text;
        if (IsNew)
        {
            EhrTriggers.insert(EhrTriggerCur);
        }
        else
        {
            EhrTriggers.update(EhrTriggerCur);
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
        this.butCancel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butAddSnomed = new OpenDental.UI.Button();
        this.butAddIcd10 = new OpenDental.UI.Button();
        this.butAddIcd9 = new OpenDental.UI.Button();
        this.butAddProblem = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butAddCvx = new OpenDental.UI.Button();
        this.butAddRxNorm = new OpenDental.UI.Button();
        this.butAddMed = new OpenDental.UI.Button();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.butAddAllergy = new OpenDental.UI.Button();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.butAddGender = new OpenDental.UI.Button();
        this.butAddAge = new OpenDental.UI.Button();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.butAddLab = new OpenDental.UI.Button();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.butAddBMI = new OpenDental.UI.Button();
        this.butAddBP = new OpenDental.UI.Button();
        this.butAddWeight = new OpenDental.UI.Button();
        this.butAddHeight = new OpenDental.UI.Button();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboCardinality = new System.Windows.Forms.ComboBox();
        this.labelCardinality = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textInstruction = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textBibliography = new System.Windows.Forms.TextBox();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(652, 616);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(12, 616);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 122;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(571, 616);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 121;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butAddSnomed);
        this.groupBox1.Controls.Add(this.butAddIcd10);
        this.groupBox1.Controls.Add(this.butAddIcd9);
        this.groupBox1.Controls.Add(this.butAddProblem);
        this.groupBox1.Location = new System.Drawing.Point(12, 72);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(114, 132);
        this.groupBox1.TabIndex = 123;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Problems";
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
        this.butAddSnomed.Location = new System.Drawing.Point(6, 102);
        this.butAddSnomed.Name = "butAddSnomed";
        this.butAddSnomed.Size = new System.Drawing.Size(99, 23);
        this.butAddSnomed.TabIndex = 206;
        this.butAddSnomed.Text = "SNOMEDCT";
        this.butAddSnomed.Click += new System.EventHandler(this.butAddSnomed_Click);
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
        this.butAddIcd10.Location = new System.Drawing.Point(6, 73);
        this.butAddIcd10.Name = "butAddIcd10";
        this.butAddIcd10.Size = new System.Drawing.Size(99, 23);
        this.butAddIcd10.TabIndex = 205;
        this.butAddIcd10.Text = "ICD10";
        this.butAddIcd10.Click += new System.EventHandler(this.butAddIcd10_Click);
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
        this.butAddIcd9.Location = new System.Drawing.Point(6, 44);
        this.butAddIcd9.Name = "butAddIcd9";
        this.butAddIcd9.Size = new System.Drawing.Size(99, 23);
        this.butAddIcd9.TabIndex = 204;
        this.butAddIcd9.Text = "ICD9";
        this.butAddIcd9.Click += new System.EventHandler(this.butAddIcd9_Click);
        //
        // butAddProblem
        //
        this.butAddProblem.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddProblem.setAutosize(true);
        this.butAddProblem.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddProblem.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddProblem.setCornerRadius(4F);
        this.butAddProblem.Image = Resources.getAdd();
        this.butAddProblem.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddProblem.Location = new System.Drawing.Point(6, 15);
        this.butAddProblem.Name = "butAddProblem";
        this.butAddProblem.Size = new System.Drawing.Size(99, 23);
        this.butAddProblem.TabIndex = 203;
        this.butAddProblem.Text = "Problem";
        this.butAddProblem.Click += new System.EventHandler(this.butAddProblem_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(138, 77);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(589, 392);
        this.gridMain.TabIndex = 202;
        this.gridMain.setTitle("Trigger Conditions");
        this.gridMain.setTranslationName("");
        this.gridMain.setWrapText(false);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butAddCvx);
        this.groupBox2.Controls.Add(this.butAddRxNorm);
        this.groupBox2.Controls.Add(this.butAddMed);
        this.groupBox2.Location = new System.Drawing.Point(12, 205);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(114, 102);
        this.groupBox2.TabIndex = 207;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Medications";
        //
        // butAddCvx
        //
        this.butAddCvx.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddCvx.setAutosize(true);
        this.butAddCvx.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddCvx.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddCvx.setCornerRadius(4F);
        this.butAddCvx.Image = Resources.getAdd();
        this.butAddCvx.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddCvx.Location = new System.Drawing.Point(6, 73);
        this.butAddCvx.Name = "butAddCvx";
        this.butAddCvx.Size = new System.Drawing.Size(99, 23);
        this.butAddCvx.TabIndex = 205;
        this.butAddCvx.Text = "Cvx";
        this.butAddCvx.Click += new System.EventHandler(this.butAddCvx_Click);
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
        this.butAddRxNorm.Location = new System.Drawing.Point(6, 44);
        this.butAddRxNorm.Name = "butAddRxNorm";
        this.butAddRxNorm.Size = new System.Drawing.Size(99, 23);
        this.butAddRxNorm.TabIndex = 204;
        this.butAddRxNorm.Text = "RxNorm";
        this.butAddRxNorm.Click += new System.EventHandler(this.butAddRxNorm_Click);
        //
        // butAddMed
        //
        this.butAddMed.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddMed.setAutosize(true);
        this.butAddMed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddMed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddMed.setCornerRadius(4F);
        this.butAddMed.Image = Resources.getAdd();
        this.butAddMed.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddMed.Location = new System.Drawing.Point(6, 15);
        this.butAddMed.Name = "butAddMed";
        this.butAddMed.Size = new System.Drawing.Size(99, 23);
        this.butAddMed.TabIndex = 203;
        this.butAddMed.Text = "Med";
        this.butAddMed.Click += new System.EventHandler(this.butAddMed_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.butAddAllergy);
        this.groupBox3.Location = new System.Drawing.Point(12, 309);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(114, 45);
        this.groupBox3.TabIndex = 208;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Allergies";
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
        this.butAddAllergy.Location = new System.Drawing.Point(6, 15);
        this.butAddAllergy.Name = "butAddAllergy";
        this.butAddAllergy.Size = new System.Drawing.Size(99, 23);
        this.butAddAllergy.TabIndex = 203;
        this.butAddAllergy.Text = "Allergy";
        this.butAddAllergy.Click += new System.EventHandler(this.butAddAllergy_Click);
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.butAddGender);
        this.groupBox4.Controls.Add(this.butAddAge);
        this.groupBox4.Location = new System.Drawing.Point(12, 356);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(114, 73);
        this.groupBox4.TabIndex = 209;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Demographics";
        //
        // butAddGender
        //
        this.butAddGender.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddGender.setAutosize(true);
        this.butAddGender.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddGender.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddGender.setCornerRadius(4F);
        this.butAddGender.Image = Resources.getAdd();
        this.butAddGender.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddGender.Location = new System.Drawing.Point(6, 44);
        this.butAddGender.Name = "butAddGender";
        this.butAddGender.Size = new System.Drawing.Size(99, 23);
        this.butAddGender.TabIndex = 204;
        this.butAddGender.Text = "Gender";
        this.butAddGender.Click += new System.EventHandler(this.butAddGender_Click);
        //
        // butAddAge
        //
        this.butAddAge.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddAge.setAutosize(true);
        this.butAddAge.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddAge.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddAge.setCornerRadius(4F);
        this.butAddAge.Image = Resources.getAdd();
        this.butAddAge.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddAge.Location = new System.Drawing.Point(6, 15);
        this.butAddAge.Name = "butAddAge";
        this.butAddAge.Size = new System.Drawing.Size(99, 23);
        this.butAddAge.TabIndex = 203;
        this.butAddAge.Text = "Age";
        this.butAddAge.Click += new System.EventHandler(this.butAddAge_Click);
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.butAddLab);
        this.groupBox5.Location = new System.Drawing.Point(12, 431);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(114, 45);
        this.groupBox5.TabIndex = 210;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Lab Results";
        //
        // butAddLab
        //
        this.butAddLab.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddLab.setAutosize(true);
        this.butAddLab.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddLab.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddLab.setCornerRadius(4F);
        this.butAddLab.Image = Resources.getAdd();
        this.butAddLab.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddLab.Location = new System.Drawing.Point(6, 15);
        this.butAddLab.Name = "butAddLab";
        this.butAddLab.Size = new System.Drawing.Size(99, 23);
        this.butAddLab.TabIndex = 203;
        this.butAddLab.Text = "Lab";
        this.butAddLab.Click += new System.EventHandler(this.butAddLab_Click);
        //
        // groupBox6
        //
        this.groupBox6.Controls.Add(this.butAddBMI);
        this.groupBox6.Controls.Add(this.butAddBP);
        this.groupBox6.Controls.Add(this.butAddWeight);
        this.groupBox6.Controls.Add(this.butAddHeight);
        this.groupBox6.Location = new System.Drawing.Point(12, 478);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(114, 132);
        this.groupBox6.TabIndex = 211;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Vital Signs";
        //
        // butAddBMI
        //
        this.butAddBMI.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddBMI.setAutosize(true);
        this.butAddBMI.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddBMI.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddBMI.setCornerRadius(4F);
        this.butAddBMI.Image = Resources.getAdd();
        this.butAddBMI.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddBMI.Location = new System.Drawing.Point(6, 102);
        this.butAddBMI.Name = "butAddBMI";
        this.butAddBMI.Size = new System.Drawing.Size(99, 23);
        this.butAddBMI.TabIndex = 206;
        this.butAddBMI.Text = "BMI";
        this.butAddBMI.Click += new System.EventHandler(this.butAddBMI_Click);
        //
        // butAddBP
        //
        this.butAddBP.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddBP.setAutosize(true);
        this.butAddBP.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddBP.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddBP.setCornerRadius(4F);
        this.butAddBP.Enabled = false;
        this.butAddBP.Image = Resources.getAdd();
        this.butAddBP.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddBP.Location = new System.Drawing.Point(6, 73);
        this.butAddBP.Name = "butAddBP";
        this.butAddBP.Size = new System.Drawing.Size(99, 23);
        this.butAddBP.TabIndex = 205;
        this.butAddBP.Text = "BP";
        this.butAddBP.Click += new System.EventHandler(this.butAddBP_Click);
        //
        // butAddWeight
        //
        this.butAddWeight.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddWeight.setAutosize(true);
        this.butAddWeight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddWeight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddWeight.setCornerRadius(4F);
        this.butAddWeight.Image = Resources.getAdd();
        this.butAddWeight.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddWeight.Location = new System.Drawing.Point(6, 44);
        this.butAddWeight.Name = "butAddWeight";
        this.butAddWeight.Size = new System.Drawing.Size(99, 23);
        this.butAddWeight.TabIndex = 204;
        this.butAddWeight.Text = "Weight";
        this.butAddWeight.Click += new System.EventHandler(this.butAddWeight_Click);
        //
        // butAddHeight
        //
        this.butAddHeight.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddHeight.setAutosize(true);
        this.butAddHeight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddHeight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddHeight.setCornerRadius(4F);
        this.butAddHeight.Image = Resources.getAdd();
        this.butAddHeight.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddHeight.Location = new System.Drawing.Point(6, 15);
        this.butAddHeight.Name = "butAddHeight";
        this.butAddHeight.Size = new System.Drawing.Size(99, 23);
        this.butAddHeight.TabIndex = 203;
        this.butAddHeight.Text = "Height";
        this.butAddHeight.Click += new System.EventHandler(this.butAddHeight_Click);
        //
        // textDescription
        //
        this.textDescription.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textDescription.Location = new System.Drawing.Point(138, 12);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(388, 20);
        this.textDescription.TabIndex = 213;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(35, 14);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 212;
        this.label2.Text = "Description";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCardinality
        //
        this.comboCardinality.FormattingEnabled = true;
        this.comboCardinality.Location = new System.Drawing.Point(138, 44);
        this.comboCardinality.Name = "comboCardinality";
        this.comboCardinality.Size = new System.Drawing.Size(158, 21);
        this.comboCardinality.TabIndex = 216;
        this.comboCardinality.SelectedIndexChanged += new System.EventHandler(this.comboCardinality_SelectedIndexChanged);
        //
        // labelCardinality
        //
        this.labelCardinality.Location = new System.Drawing.Point(302, 35);
        this.labelCardinality.Name = "labelCardinality";
        this.labelCardinality.Size = new System.Drawing.Size(425, 39);
        this.labelCardinality.TabIndex = 214;
        this.labelCardinality.Text = "For this trigger to provide Clinical Decision Support, only one of the conditions" + " below must be met.";
        this.labelCardinality.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label4
        //
        this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label4.Location = new System.Drawing.Point(45, 46);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(90, 17);
        this.label4.TabIndex = 215;
        this.label4.Text = "Cardinality";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label3.Location = new System.Drawing.Point(138, 478);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(89, 17);
        this.label3.TabIndex = 218;
        this.label3.Text = "Instructions";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textInstruction
        //
        this.textInstruction.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textInstruction.Location = new System.Drawing.Point(233, 478);
        this.textInstruction.Multiline = true;
        this.textInstruction.Name = "textInstruction";
        this.textInstruction.Size = new System.Drawing.Size(494, 84);
        this.textInstruction.TabIndex = 217;
        //
        // label1
        //
        this.label1.Anchor = System.Windows.Forms.AnchorStyles.Top;
        this.label1.Location = new System.Drawing.Point(138, 568);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(89, 17);
        this.label1.TabIndex = 220;
        this.label1.Text = "Bibliography";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBibliography
        //
        this.textBibliography.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.textBibliography.Location = new System.Drawing.Point(233, 568);
        this.textBibliography.Multiline = true;
        this.textBibliography.Name = "textBibliography";
        this.textBibliography.Size = new System.Drawing.Size(494, 42);
        this.textBibliography.TabIndex = 219;
        //
        // FormEhrTriggerEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(739, 652);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textBibliography);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textInstruction);
        this.Controls.Add(this.comboCardinality);
        this.Controls.Add(this.labelCardinality);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.groupBox6);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormEhrTriggerEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "EHR Trigger Edit";
        this.Load += new System.EventHandler(this.FormEhrTriggerEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.groupBox4.ResumeLayout(false);
        this.groupBox5.ResumeLayout(false);
        this.groupBox6.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddSnomed;
    private OpenDental.UI.Button butAddIcd10;
    private OpenDental.UI.Button butAddIcd9;
    private OpenDental.UI.Button butAddProblem;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddCvx;
    private OpenDental.UI.Button butAddRxNorm;
    private OpenDental.UI.Button butAddMed;
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddAllergy;
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddGender;
    private OpenDental.UI.Button butAddAge;
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddLab;
    private System.Windows.Forms.GroupBox groupBox6 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAddWeight;
    private OpenDental.UI.Button butAddHeight;
    private OpenDental.UI.Button butAddBMI;
    private OpenDental.UI.Button butAddBP;
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboCardinality = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelCardinality = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textInstruction = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBibliography = new System.Windows.Forms.TextBox();
}


