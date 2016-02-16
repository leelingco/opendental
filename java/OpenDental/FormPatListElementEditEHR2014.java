//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import EhrLaboratories.HL70125;
import OpenDental.FormAllergySetup;
import OpenDental.FormCommPrefPicker;
import OpenDental.FormDiseaseDefs;
import OpenDental.FormIcd9s;
import OpenDental.FormLoincs;
import OpenDental.FormMedications;
import OpenDental.FormSnomeds;
import OpenDental.MsgBox;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.ContactMethod;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.EhrOperand;
import OpenDentBusiness.EhrPatListElement2014;
import OpenDentBusiness.EhrRestrictionType;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Ucum;
import OpenDentBusiness.Ucums;

public class FormPatListElementEditEHR2014  extends Form 
{

    public EhrPatListElement2014 Element;
    public boolean IsNew = new boolean();
    public boolean Delete = new boolean();
    private List<Ucum> _listUCUM = new List<Ucum>();
    public FormPatListElementEditEHR2014() throws Exception {
        initializeComponent();
    }

    private void formPatListElementEdit_Load(Object sender, EventArgs e) throws Exception {
        listRestriction.Items.Clear();
        for (int i = 0;i < Enum.GetNames(EhrRestrictionType.class).Length;i++)
        {
            listRestriction.Items.Add(Enum.GetNames(EhrRestrictionType.class)[i]);
        }
        listRestriction.SelectedIndex = ((Enum)Element.Restriction).ordinal();
        listOperand.SelectedIndex = ((Enum)Element.Operand).ordinal();
        textCompareString.Text = Element.CompareString;
        if (Element.Restriction == EhrRestrictionType.Problem && !IsNew)
        {
            textCompareString.Text = "";
            //clear text box for simplicity
            if (ICD9s.codeExists(Element.CompareString))
            {
                textCompareString.Text = Element.CompareString;
            }
            else if (Snomeds.codeExists(Element.CompareString))
            {
                textSNOMED.Text = Element.CompareString;
            }
            else
            {
                MsgBox.show(this,"Problem code provided is not an existing ICD9 or SNOMED code.");
            }  
        }
         
        //no harm in continuing since this form is error checked on OK click.
        fillCombos();
        if (!IsNew)
        {
            comboUnits.Text = Element.LabValueUnits;
            comboLabValueType.SelectedIndex = ((Enum)Element.LabValueType).ordinal();
        }
         
        textLabValue.Text = Element.LabValue;
        if (Element.StartDate.Year > 1880)
        {
            textDateStart.Text = Element.StartDate.ToShortDateString();
        }
         
        if (Element.EndDate.Year > 1880)
        {
            textDateStop.Text = Element.EndDate.ToShortDateString();
        }
         
        changeLayout();
    }

    private void fillCombos() throws Exception {
        //Units of measure----------------------------------------
        _listUCUM = Ucums.getAll();
        if (_listUCUM.Count == 0)
        {
            MsgBox.show(this,"Units of measure have not been imported. Go to the code system importer window to import UCUM codes to continue.");
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        int _tempSelectedIndex = 0;
        for (int i = 0;i < _listUCUM.Count;i++)
        {
            comboUnits.Items.Add(_listUCUM[i].UcumCode);
            if (StringSupport.equals(_listUCUM[i].UcumCode, "mg/dL"))
            {
                //arbitrarily chosen common unit of measure.
                _tempSelectedIndex = i;
            }
             
        }
        comboUnits.SelectedIndex = _tempSelectedIndex;
        //Lab Value Type.  Based off of the HL70125 data type (Value Type)----------------------------------------
        comboLabValueType.Items.Add("Coded Entry");
        comboLabValueType.Items.Add("Coded with Exceptions");
        comboLabValueType.Items.Add("Date");
        comboLabValueType.Items.Add("Formatted Text");
        comboLabValueType.Items.Add("Numeric");
        //default
        comboLabValueType.Items.Add("Structured Numeric");
        comboLabValueType.Items.Add("String Data");
        comboLabValueType.Items.Add("Time");
        comboLabValueType.Items.Add("Time Stamp");
        comboLabValueType.Items.Add("Text Data");
        comboLabValueType.SelectedIndex = 4;
    }

    //Numeric.  This is what we used to assume all lab results were like.
    private void listRestriction_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        changeLayout();
    }

    private void changeLayout() throws Exception {
        //All controls' visibility set to false, then set to true below if needed.
        //Labels
        labelOperand.Visible = false;
        labelCompareString.Visible = false;
        labelSNOMED.Visible = false;
        labelLabValue.Visible = false;
        labelAfterDate.Visible = false;
        labelBeforeDate.Visible = false;
        labelExample.Visible = false;
        labelProblemSuggest.Visible = false;
        labelLabValueType.Visible = false;
        //TextBoxes
        textCompareString.Visible = false;
        textSNOMED.Visible = false;
        textLabValue.Visible = false;
        textDateStart.Visible = false;
        textDateStop.Visible = false;
        //Buttons
        butPicker.Visible = false;
        butSNOMED.Visible = false;
        butProblem.Visible = false;
        //ComboBoxes
        comboUnits.Visible = false;
        comboLabValueType.Visible = false;
        //ListBoxes
        listOperand.Visible = false;
        SelectedIndex __dummyScrutVar0 = listRestriction.SelectedIndex;
        if (__dummyScrutVar0.equals(0))
        {
            //Birthdate------------------------------------------------------------------------------------------------------------
            //Labels
            labelOperand.Visible = true;
            labelCompareString.Visible = true;
            labelCompareString.Text = "Enter age";
            labelExample.Visible = true;
            labelExample.Text = "Ex: 22";
            //TextBoxes
            textCompareString.Visible = true;
            //ListBoxes
            listOperand.Visible = true;
        }
        else if (__dummyScrutVar0.equals(1))
        {
            //Disease--------------------------------------------------------------------------------------------------------------
            //Labels
            labelCompareString.Visible = true;
            labelCompareString.Text = "ICD9 Code";
            labelSNOMED.Visible = true;
            labelSNOMED.Text = "SNOMED Code";
            labelAfterDate.Visible = true;
            labelBeforeDate.Visible = true;
            labelProblemSuggest.Visible = true;
            //TextBoxes
            textCompareString.Visible = true;
            textSNOMED.Visible = true;
            textDateStart.Visible = true;
            textDateStop.Visible = true;
            //Buttons
            butPicker.Visible = true;
            butSNOMED.Visible = true;
            butProblem.Visible = true;
        }
        else if (__dummyScrutVar0.equals(2))
        {
            //Medication-----------------------------------------------------------------------------------------------------------
            //Labels
            labelCompareString.Visible = true;
            labelCompareString.Text = "Medication Name";
            labelAfterDate.Visible = true;
            labelBeforeDate.Visible = true;
            labelExample.Visible = true;
            labelExample.Text = "Ex: Albuterol";
            //TextBoxes
            textCompareString.Visible = true;
            textDateStart.Visible = true;
            textDateStop.Visible = true;
            //Buttons
            butPicker.Visible = true;
        }
        else if (__dummyScrutVar0.equals(3))
        {
            //LabResult------------------------------------------------------------------------------------------------------------
            //Labels
            labelOperand.Visible = true;
            labelCompareString.Visible = true;
            labelCompareString.Text = "Loinc Code";
            labelLabValue.Visible = true;
            labelAfterDate.Visible = true;
            labelBeforeDate.Visible = true;
            labelExample.Visible = true;
            labelExample.Text = "Ex: 1004-1";
            labelLabValueType.Visible = true;
            //TextBoxes
            textCompareString.Visible = true;
            textLabValue.Visible = true;
            textDateStart.Visible = true;
            textDateStop.Visible = true;
            //Buttons
            butPicker.Visible = true;
            //ComboBoxes
            comboUnits.Visible = true;
            comboLabValueType.Visible = true;
            //ListBoxes
            listOperand.Visible = true;
        }
        else if (__dummyScrutVar0.equals(4))
        {
            //Gender---------------------------------------------------------------------------------------------------------------
            //Labels
            labelCompareString.Visible = true;
            labelCompareString.Text = "For display and sorting";
        }
        else if (__dummyScrutVar0.equals(5))
        {
            //CommPref-------------------------------------------------------------------------------------------------------------
            //Labels
            labelCompareString.Visible = true;
            labelCompareString.Text = "Communication Preference";
            labelExample.Visible = true;
            labelExample.Text = "Ex: WirelessPh";
            //TextBoxes
            textCompareString.Visible = true;
            //Buttons
            butPicker.Visible = true;
        }
        else if (__dummyScrutVar0.equals(6))
        {
            //Allergy--------------------------------------------------------------------------------------------------------------
            //Labels
            labelCompareString.Visible = true;
            labelCompareString.Text = "Allergy Description (exact)";
            labelAfterDate.Visible = true;
            labelBeforeDate.Visible = true;
            //TextBoxes
            textCompareString.Visible = true;
            textDateStart.Visible = true;
            textDateStop.Visible = true;
            //Buttons
            butPicker.Visible = true;
        }
        else
        {
        }       
    }

    //Default--------------------------------------------------------------------------------------------------------------
    //Nothing will show
    //labelCompareString.Visible=true;
    //textCompareString.Visible=true;
    //labelExample.Visible=true;
    //labelLabValue.Visible=false;
    //textLabValue.Visible=false;
    //labelOperand.Visible=false;
    //listOperand.Visible=false;
    //if(listRestriction.SelectedIndex==0) {//Birthdate
    //  labelCompareString.Text="Enter age";
    //  labelExample.Text="Ex: 22";
    //  labelSNOMED.Visible=false;
    //  labelOperand.Visible=true;
    //  listOperand.Visible=true;
    //  butPicker.Visible=false;
    //  labelAfterDate.Visible=false;
    //  labelBeforeDate.Visible=false;
    //  textDateStart.Visible=false;
    //  textDateStop.Visible=false;
    //}
    //if(listRestriction.SelectedIndex==1) {//Disease/Problem
    //  labelCompareString.Text="ICD9 code";
    //  labelCompareString.Text="ICD9 code";
    //  labelExample.Text="Ex: 414.0";
    //  labelSNOMED.Visible=false;
    //  butPicker.Visible=true;
    //  labelAfterDate.Visible=true;
    //  labelBeforeDate.Visible=true;
    //  textDateStart.Visible=true;
    //  textDateStop.Visible=true;
    //}
    //if(listRestriction.SelectedIndex==2) {//Medication
    //  labelCompareString.Text="Medication name";
    //  labelExample.Text="Ex: Coumadin";
    //  labelSNOMED.Visible=false;
    //  butPicker.Visible=true;
    //  labelAfterDate.Visible=true;
    //  labelBeforeDate.Visible=true;
    //  textDateStart.Visible=true;
    //  textDateStop.Visible=true;
    //}
    //if(listRestriction.SelectedIndex==3) {//LabResult
    //  labelCompareString.Text="Test name (exact)";
    //  labelExample.Text="Ex: HDL-cholesterol";
    //  labelSNOMED.Visible=false;
    //  labelLabValue.Visible=true;
    //  textLabValue.Visible=true;
    //  labelOperand.Visible=true;
    //  listOperand.Visible=true;
    //  butPicker.Visible=false;
    //  labelAfterDate.Visible=true;
    //  labelBeforeDate.Visible=true;
    //  textDateStart.Visible=true;
    //  textDateStop.Visible=true;
    //}
    //if(listRestriction.SelectedIndex==4) {//Gender
    //  labelCompareString.Text="For display and sorting";
    //  labelExample.Visible=false;
    //  labelSNOMED.Visible=false;
    //  textCompareString.Visible=false;
    //  butPicker.Visible=false;
    //  labelAfterDate.Visible=false;
    //  labelBeforeDate.Visible=false;
    //  textDateStart.Visible=false;
    //  textDateStop.Visible=false;
    //}
    //if(listRestriction.SelectedIndex==5) {//CommPref
    //  labelCompareString.Text="Communication Method (exact)";
    //  labelExample.Text="Ex: WirelessPh";
    //  labelSNOMED.Visible=false;
    //  textCompareString.Visible=true;
    //  butPicker.Visible=true;
    //  labelAfterDate.Visible=false;
    //  labelBeforeDate.Visible=false;
    //  textDateStart.Visible=false;
    //  textDateStop.Visible=false;
    //}
    //if(listRestriction.SelectedIndex==6) {//Allergy
    //  labelCompareString.Text="Allergy Description (exact)";
    //  labelExample.Visible=false;
    //  textCompareString.Visible=true;
    //  butPicker.Visible=true;
    //  labelAfterDate.Visible=true;
    //  labelBeforeDate.Visible=true;
    //  textDateStart.Visible=true;
    //  textDateStop.Visible=true;
    //}
    private boolean isValid() throws Exception {
        int index = listRestriction.SelectedIndex;
        if (index != 3)
        {
            //Not LabResult
            textLabValue.Text = "";
        }
         
        switch(index)
        {
            case 0: 
                try
                {
                    //Birthdate------------------------------------------------------------------------------------------------------------
                    Convert.ToInt32(textCompareString.Text);
                }
                catch (Exception __dummyCatchVar0)
                {
                    //used intead of PIn so that an empty string is not evaluated as 0
                    MsgBox.show(this,"Please enter a valid age.");
                    return false;
                }

                break;
            case 1: 
                //Disease--------------------------------------------------------------------------------------------------------------
                if (StringSupport.equals(textCompareString.Text, "") && StringSupport.equals(textSNOMED.Text, ""))
                {
                    MsgBox.show(this,"Please enter a valid SNOMED CT or ICD9 code.");
                    return false;
                }
                 
                if (!StringSupport.equals(textCompareString.Text, ""))
                {
                    if (!ICD9s.CodeExists(textCompareString.Text))
                    {
                        MsgBox.show(this,"ICD9 code does not exist in database, pick from list.");
                        return false;
                    }
                     
                }
                 
                if (!StringSupport.equals(textSNOMED.Text, ""))
                {
                    if (!Snomeds.CodeExists(textSNOMED.Text))
                    {
                        MsgBox.show(this,"SNOMED CT code does not exist in database, pick from list.");
                        return false;
                    }
                     
                }
                 
                if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
                {
                    MsgBox.show(this,"Please fix date entry errors.");
                    return false;
                }
                 
                break;
            case 2: 
                //Medication-----------------------------------------------------------------------------------------------------------
                if (StringSupport.equals(textCompareString.Text, ""))
                {
                    MsgBox.show(this,"Please enter a valid medication.");
                    return false;
                }
                 
                if (Medications.GetMedicationFromDbByName(textCompareString.Text) == null)
                {
                    MsgBox.show(this,"Medication does not exist in database, pick from list.");
                    return false;
                }
                 
                if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
                {
                    MsgBox.show(this,"Please fix date entry errors.");
                    return false;
                }
                 
                break;
            case 3: 
                //LabResult------------------------------------------------------------------------------------------------------------
                if (StringSupport.equals(textCompareString.Text, ""))
                {
                    MsgBox.show(this,"Please select a valid Loinc Code.");
                    return false;
                }
                 
                //if(Loincs.GetByCode(textCompareString.Text)==null) {
                //	MsgBox.Show(this,"Loinc code does not exist in database, pick from list.");
                //	return false;
                //}
                if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
                {
                    MsgBox.show(this,"Please fix date entry errors.");
                    return false;
                }
                 
                break;
            case 4: 
                //Gender---------------------------------------------------------------------------------------------------------------
                textCompareString.Text = "";
                break;
            case 5: 
                //CommPref-------------------------------------------------------------------------------------------------------------
                if (StringSupport.equals(textCompareString.Text, ""))
                {
                    MsgBox.show(this,"Please enter a communication preference.");
                    return false;
                }
                 
                if (!isContactMethod(textCompareString.Text))
                {
                    MsgBox.show(this,"Communication preference not defined, pick from list.");
                    return false;
                }
                 
                break;
            case 6: 
                //Allergy--------------------------------------------------------------------------------------------------------------
                if (StringSupport.equals(textCompareString.Text, ""))
                {
                    MsgBox.show(this,"Please enter a valid allergy.");
                    return false;
                }
                 
                if (AllergyDefs.GetByDescription(textCompareString.Text) == null)
                {
                    MsgBox.show(this,"Allergy does not exist in database, pick from list.");
                    return false;
                }
                 
                if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") || !StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
                {
                    MsgBox.show(this,"Please fix date entry errors.");
                    return false;
                }
                 
                break;
        
        }
        return true;
    }

    /**
    * Returns true if string matches ContactMethod enum names.
    */
    private boolean isContactMethod(String contactMethodName) throws Exception {
        String[] contactNames = Enum.GetNames(ContactMethod.class);
        for (int i = 0;i < contactNames.Length;i++)
        {
            if (StringSupport.equals(contactNames[i], contactMethodName))
            {
                return true;
            }
             
        }
        return false;
    }

    private void butPicker_Click(Object sender, EventArgs e) throws Exception {
        SelectedIndex __dummyScrutVar2 = listRestriction.SelectedIndex;
        if (__dummyScrutVar2.equals(0))
        {
        }
        else //Birthdate
        //Not visible
        if (__dummyScrutVar2.equals(1))
        {
            //problem
            if (sender.Equals(butPicker))
            {
                FormIcd9s FormI9 = new FormIcd9s();
                FormI9.IsSelectionMode = true;
                FormI9.ShowDialog();
                if (FormI9.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                textCompareString.Text = FormI9.SelectedIcd9.ICD9Code;
                textSNOMED.Text = "";
            }
            else if (sender.Equals(butSNOMED))
            {
                FormSnomeds FormS = new FormSnomeds();
                FormS.IsSelectionMode = true;
                FormS.ShowDialog();
                if (FormS.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                textSNOMED.Text = FormS.SelectedSnomed.SnomedCode;
                textCompareString.Text = "";
            }
              
        }
        else if (__dummyScrutVar2.equals(2))
        {
            //Medication
            FormMedications FormM = new FormMedications();
            FormM.IsSelectionMode = true;
            FormM.ShowDialog();
            if (FormM.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            textCompareString.Text = Medications.getNameOnly(FormM.SelectedMedicationNum);
        }
        else if (__dummyScrutVar2.equals(3))
        {
            //LabResult
            FormLoincs FormL = new FormLoincs();
            FormL.IsSelectionMode = true;
            FormL.ShowDialog();
            if (FormL.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            textCompareString.Text = FormL.SelectedLoinc.LoincCode;
            comboUnits.Text = FormL.SelectedLoinc.UnitsUCUM;
        }
        else //may be valued, may be blank.
        if (__dummyScrutVar2.equals(4))
        {
        }
        else //Gender
        //Not visible
        if (__dummyScrutVar2.equals(5))
        {
            //Comm preference
            FormCommPrefPicker FormCPP = new FormCommPrefPicker();
            FormCPP.ShowDialog();
            if (FormCPP.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            textCompareString.Text = Enum.GetName(ContactMethod.class, FormCPP.ContMethCur);
        }
        else if (__dummyScrutVar2.equals(6))
        {
            //Alergy
            FormAllergySetup FormAS = new FormAllergySetup();
            FormAS.IsSelectionMode = true;
            FormAS.ShowDialog();
            if (FormAS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            textCompareString.Text = AllergyDefs.getDescription(FormAS.SelectedAllergyDefNum);
        }
        else
        {
        }       
    }

    //should never happen
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!IsNew)
        {
            Delete = true;
        }
         
        DialogResult = DialogResult.Cancel;
    }

    private void textCompareString_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textCompareString.Text, ""))
        {
            textSNOMED.Text = "";
        }
         
    }

    private void textSNOMED_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textSNOMED.Text, ""))
        {
            textCompareString.Text = "";
        }
         
    }

    private void butProblem_Click(Object sender, EventArgs e) throws Exception {
        FormDiseaseDefs FormDD = new FormDiseaseDefs();
        FormDD.IsSelectionMode = true;
        FormDD.ShowDialog();
        if (FormDD.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DiseaseDef dis = DiseaseDefs.getItem(FormDD.SelectedDiseaseDefNum);
        if (!StringSupport.equals(dis.SnomedCode, ""))
        {
            textSNOMED.Text = dis.SnomedCode;
        }
        else if (!StringSupport.equals(dis.ICD9Code, ""))
        {
            textCompareString.Text = dis.ICD9Code;
            MsgBox.show(this,"Selected problem does not have a valid SNOMED CT code.");
        }
        else
        {
            MsgBox.show(this,"Selected problem does not have a valid SNOMED CT code.");
        }  
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!isValid())
        {
            return ;
        }
         
        Element.Restriction = (EhrRestrictionType)listRestriction.SelectedIndex;
        Element.Operand = (EhrOperand)listOperand.SelectedIndex;
        Element.CompareString = textCompareString.Text;
        if (Element.Restriction == EhrRestrictionType.Problem && StringSupport.equals(textCompareString.Text, ""))
        {
            Element.CompareString = textSNOMED.Text;
        }
         
        Element.LabValue = textLabValue.Text;
        Element.LabValueType = (HL70125)comboLabValueType.SelectedIndex;
        Element.LabValueUnits = comboUnits.Text;
        try
        {
            //UCUM units or blank.
            Element.StartDate = DateTime.Parse(textDateStart.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            Element.StartDate = DateTime.MinValue;
        }

        try
        {
            Element.EndDate = DateTime.Parse(textDateStop.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            Element.EndDate = DateTime.MinValue;
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
        this.labelOperand = new System.Windows.Forms.Label();
        this.butCancel = new System.Windows.Forms.Button();
        this.butOK = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.labelRestriction = new System.Windows.Forms.Label();
        this.labelCompareString = new System.Windows.Forms.Label();
        this.textCompareString = new System.Windows.Forms.TextBox();
        this.labelLabValue = new System.Windows.Forms.Label();
        this.textLabValue = new System.Windows.Forms.TextBox();
        this.listRestriction = new System.Windows.Forms.ListBox();
        this.listOperand = new System.Windows.Forms.ListBox();
        this.labelExample = new System.Windows.Forms.Label();
        this.labelAfterDate = new System.Windows.Forms.Label();
        this.labelBeforeDate = new System.Windows.Forms.Label();
        this.butPicker = new System.Windows.Forms.Button();
        this.butSNOMED = new System.Windows.Forms.Button();
        this.textSNOMED = new System.Windows.Forms.TextBox();
        this.labelSNOMED = new System.Windows.Forms.Label();
        this.butProblem = new System.Windows.Forms.Button();
        this.textDateStop = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.labelProblemSuggest = new System.Windows.Forms.Label();
        this.labelLabValueType = new System.Windows.Forms.Label();
        this.comboUnits = new System.Windows.Forms.ComboBox();
        this.comboLabValueType = new System.Windows.Forms.ComboBox();
        this.SuspendLayout();
        //
        // labelOperand
        //
        this.labelOperand.Location = new System.Drawing.Point(123, 113);
        this.labelOperand.Name = "labelOperand";
        this.labelOperand.Size = new System.Drawing.Size(61, 17);
        this.labelOperand.TabIndex = 10;
        this.labelOperand.Text = "Operand";
        this.labelOperand.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(409, 337);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(328, 337);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(24, 337);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 8;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // labelRestriction
        //
        this.labelRestriction.Location = new System.Drawing.Point(109, 12);
        this.labelRestriction.Name = "labelRestriction";
        this.labelRestriction.Size = new System.Drawing.Size(75, 17);
        this.labelRestriction.TabIndex = 13;
        this.labelRestriction.Text = "Restriction";
        this.labelRestriction.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCompareString
        //
        this.labelCompareString.Location = new System.Drawing.Point(7, 163);
        this.labelCompareString.Name = "labelCompareString";
        this.labelCompareString.Size = new System.Drawing.Size(177, 17);
        this.labelCompareString.TabIndex = 9;
        this.labelCompareString.Text = "Compare string";
        this.labelCompareString.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCompareString
        //
        this.textCompareString.Location = new System.Drawing.Point(185, 162);
        this.textCompareString.Name = "textCompareString";
        this.textCompareString.Size = new System.Drawing.Size(137, 20);
        this.textCompareString.TabIndex = 1;
        this.textCompareString.TextChanged += new System.EventHandler(this.textCompareString_TextChanged);
        //
        // labelLabValue
        //
        this.labelLabValue.Location = new System.Drawing.Point(60, 212);
        this.labelLabValue.Name = "labelLabValue";
        this.labelLabValue.Size = new System.Drawing.Size(124, 17);
        this.labelLabValue.TabIndex = 14;
        this.labelLabValue.Text = "Lab value";
        this.labelLabValue.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelLabValue.Visible = false;
        //
        // textLabValue
        //
        this.textLabValue.Location = new System.Drawing.Point(185, 210);
        this.textLabValue.Name = "textLabValue";
        this.textLabValue.Size = new System.Drawing.Size(137, 20);
        this.textLabValue.TabIndex = 2;
        this.textLabValue.Visible = false;
        //
        // listRestriction
        //
        this.listRestriction.FormattingEnabled = true;
        this.listRestriction.Items.AddRange(new Object[]{ "Birthdate", "Disease", "Medication", "Lab result", "Gender", "Allergy", "Comm Pref" });
        this.listRestriction.Location = new System.Drawing.Point(185, 12);
        this.listRestriction.Name = "listRestriction";
        this.listRestriction.Size = new System.Drawing.Size(75, 95);
        this.listRestriction.TabIndex = 18;
        this.listRestriction.SelectedIndexChanged += new System.EventHandler(this.listRestriction_SelectedIndexChanged);
        //
        // listOperand
        //
        this.listOperand.FormattingEnabled = true;
        this.listOperand.Items.AddRange(new Object[]{ "GreaterThan", "LessThan", "Equals" });
        this.listOperand.Location = new System.Drawing.Point(185, 113);
        this.listOperand.Name = "listOperand";
        this.listOperand.Size = new System.Drawing.Size(75, 43);
        this.listOperand.TabIndex = 19;
        //
        // labelExample
        //
        this.labelExample.Location = new System.Drawing.Point(362, 163);
        this.labelExample.Name = "labelExample";
        this.labelExample.Size = new System.Drawing.Size(119, 17);
        this.labelExample.TabIndex = 20;
        this.labelExample.Text = "Example";
        this.labelExample.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelAfterDate
        //
        this.labelAfterDate.Location = new System.Drawing.Point(7, 267);
        this.labelAfterDate.Name = "labelAfterDate";
        this.labelAfterDate.Size = new System.Drawing.Size(177, 17);
        this.labelAfterDate.TabIndex = 22;
        this.labelAfterDate.Text = "After Date";
        this.labelAfterDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBeforeDate
        //
        this.labelBeforeDate.Location = new System.Drawing.Point(7, 289);
        this.labelBeforeDate.Name = "labelBeforeDate";
        this.labelBeforeDate.Size = new System.Drawing.Size(177, 17);
        this.labelBeforeDate.TabIndex = 24;
        this.labelBeforeDate.Text = "Before Date";
        this.labelBeforeDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butPicker
        //
        this.butPicker.Location = new System.Drawing.Point(328, 160);
        this.butPicker.Name = "butPicker";
        this.butPicker.Size = new System.Drawing.Size(28, 23);
        this.butPicker.TabIndex = 25;
        this.butPicker.Text = "...";
        this.butPicker.UseVisualStyleBackColor = true;
        this.butPicker.Click += new System.EventHandler(this.butPicker_Click);
        //
        // butSNOMED
        //
        this.butSNOMED.Location = new System.Drawing.Point(328, 184);
        this.butSNOMED.Name = "butSNOMED";
        this.butSNOMED.Size = new System.Drawing.Size(80, 23);
        this.butSNOMED.TabIndex = 31;
        this.butSNOMED.Text = "SNOMED CT";
        this.butSNOMED.UseVisualStyleBackColor = true;
        this.butSNOMED.Click += new System.EventHandler(this.butPicker_Click);
        //
        // textSNOMED
        //
        this.textSNOMED.Location = new System.Drawing.Point(185, 186);
        this.textSNOMED.Name = "textSNOMED";
        this.textSNOMED.Size = new System.Drawing.Size(137, 20);
        this.textSNOMED.TabIndex = 32;
        this.textSNOMED.TextChanged += new System.EventHandler(this.textSNOMED_TextChanged);
        //
        // labelSNOMED
        //
        this.labelSNOMED.Location = new System.Drawing.Point(7, 187);
        this.labelSNOMED.Name = "labelSNOMED";
        this.labelSNOMED.Size = new System.Drawing.Size(177, 17);
        this.labelSNOMED.TabIndex = 33;
        this.labelSNOMED.Text = "SNOMED CT Code";
        this.labelSNOMED.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butProblem
        //
        this.butProblem.Location = new System.Drawing.Point(414, 184);
        this.butProblem.Name = "butProblem";
        this.butProblem.Size = new System.Drawing.Size(70, 23);
        this.butProblem.TabIndex = 34;
        this.butProblem.Text = "Problem";
        this.butProblem.UseVisualStyleBackColor = true;
        this.butProblem.Click += new System.EventHandler(this.butProblem_Click);
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(185, 288);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.Size = new System.Drawing.Size(137, 20);
        this.textDateStop.TabIndex = 27;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(185, 264);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(137, 20);
        this.textDateStart.TabIndex = 26;
        //
        // labelProblemSuggest
        //
        this.labelProblemSuggest.Location = new System.Drawing.Point(328, 243);
        this.labelProblemSuggest.Name = "labelProblemSuggest";
        this.labelProblemSuggest.Size = new System.Drawing.Size(146, 65);
        this.labelProblemSuggest.TabIndex = 35;
        this.labelProblemSuggest.Text = "Note: Picking from problem list above will provide the best results.";
        this.labelProblemSuggest.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelLabValueType
        //
        this.labelLabValueType.Location = new System.Drawing.Point(60, 238);
        this.labelLabValueType.Name = "labelLabValueType";
        this.labelLabValueType.Size = new System.Drawing.Size(124, 17);
        this.labelLabValueType.TabIndex = 37;
        this.labelLabValueType.Text = "Lab Value Type";
        this.labelLabValueType.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelLabValueType.Visible = false;
        //
        // comboUnits
        //
        this.comboUnits.FormattingEnabled = true;
        this.comboUnits.Location = new System.Drawing.Point(328, 209);
        this.comboUnits.Name = "comboUnits";
        this.comboUnits.Size = new System.Drawing.Size(80, 21);
        this.comboUnits.TabIndex = 226;
        //
        // comboLabValueType
        //
        this.comboLabValueType.FormattingEnabled = true;
        this.comboLabValueType.Location = new System.Drawing.Point(185, 237);
        this.comboLabValueType.Name = "comboLabValueType";
        this.comboLabValueType.Size = new System.Drawing.Size(137, 21);
        this.comboLabValueType.TabIndex = 227;
        //
        // FormPatListElementEditEHR2014
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(496, 372);
        this.Controls.Add(this.comboLabValueType);
        this.Controls.Add(this.comboUnits);
        this.Controls.Add(this.labelLabValueType);
        this.Controls.Add(this.labelProblemSuggest);
        this.Controls.Add(this.butProblem);
        this.Controls.Add(this.labelSNOMED);
        this.Controls.Add(this.textSNOMED);
        this.Controls.Add(this.butSNOMED);
        this.Controls.Add(this.textDateStop);
        this.Controls.Add(this.textDateStart);
        this.Controls.Add(this.butPicker);
        this.Controls.Add(this.labelBeforeDate);
        this.Controls.Add(this.labelAfterDate);
        this.Controls.Add(this.labelExample);
        this.Controls.Add(this.listOperand);
        this.Controls.Add(this.listRestriction);
        this.Controls.Add(this.textLabValue);
        this.Controls.Add(this.labelLabValue);
        this.Controls.Add(this.labelRestriction);
        this.Controls.Add(this.textCompareString);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelCompareString);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.labelOperand);
        this.Name = "FormPatListElementEditEHR2014";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "PatList Element Edit";
        this.Load += new System.EventHandler(this.FormPatListElementEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.Label labelOperand = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelRestriction = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelCompareString = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCompareString = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelLabValue = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textLabValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ListBox listRestriction = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listOperand = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelExample = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelAfterDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBeforeDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butPicker = new System.Windows.Forms.Button();
    private OpenDental.ValidDate textDateStart;
    private OpenDental.ValidDate textDateStop;
    private System.Windows.Forms.Button butSNOMED = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textSNOMED = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelSNOMED = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butProblem = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label labelProblemSuggest = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelLabValueType = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboUnits = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboLabValueType = new System.Windows.Forms.ComboBox();
}


