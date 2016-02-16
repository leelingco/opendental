//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDentBusiness.EhrCode;
import OpenDentBusiness.EhrCodes;
import OpenDentBusiness.EhrNotPerformed;
import OpenDentBusiness.EhrNotPerformedItem;
import OpenDentBusiness.EhrNotPerformeds;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;
import OpenDental.FormEhrNotPerformedEdit;

public class FormEhrNotPerformedEdit  extends Form 
{

    public EhrNotPerformed EhrNotPerfCur;
    public int SelectedItemIndex = new int();
    public boolean IsDateReadOnly = new boolean();
    private List<EhrCode> listEhrCodesReason = new List<EhrCode>();
    public FormEhrNotPerformedEdit() throws Exception {
        initializeComponent();
        SelectedItemIndex = -1;
        //this will be set to the value of the enum EhrNotPerformedItem when this form is called
        Lan.F(this);
    }

    /**
    * If using the Add button on FormEhrNotPerformed, an input box will allow the user to select from the list of available items that are not being performed.  The SelectedItemIndex will hold the index of the item selected wich corresponds to the enum EhrNotPerformedItem.  We will use this selected item index to set the EhrNotPerformed code and code system.
    */
    private void formEhrNotPerformedEdit_Load(Object sender, EventArgs e) throws Exception {
        if (IsDateReadOnly)
        {
            textDate.ReadOnly = true;
        }
         
        List<String> listValueSetOIDs = new List<String>();
        switch(SelectedItemIndex)
        {
            case 0: 
                //BMIExam
                listValueSetOIDs = new List<String>{ "2.16.840.1.113883.3.600.1.681" };
                break;
            case 1: 
                //'BMI LOINC Value' value set
                //InfluenzaVaccination
                listValueSetOIDs = new List<String>{ "2.16.840.1.113883.3.526.3.402", "2.16.840.1.113883.3.526.3.1254" };
                //'Influenza Vaccination' and 'Influenza Vaccine' value sets
                radioMedReason.Visible = true;
                radioPatReason.Visible = true;
                radioSysReason.Visible = true;
                break;
            case 2: 
                //TobaccoScreening
                listValueSetOIDs = new List<String>{ "2.16.840.1.113883.3.526.3.1278" };
                break;
            case 3: 
                //'Tobacco Use Screening' value set
                //DocumentCurrentMeds
                listValueSetOIDs = new List<String>{ "2.16.840.1.113883.3.600.1.462" };
                break;
            default: 
                break;
        
        }
        //'Current Medications Documented SNMD' value set
        //should never happen
        List<EhrCode> listEhrCodes = EhrCodes.GetForValueSetOIDs(listValueSetOIDs, true);
        if (listEhrCodes.Count == 0)
        {
            //This should only happen if the EHR.dll does not exist or if the codes in the ehrcode list do not exist in the corresponding table
            MsgBox.show(this,"The codes used for Not Performed items do not exist in the table in your database.  You should run the Code System Importer tool in Setup | EHR.");
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (EhrNotPerfCur.getIsNew())
        {
            //if new, CodeValue and CodeSystem are not set, might have to select one
            if (listEhrCodes.Count == 1)
            {
                //only one code in the selected value set, use it
                EhrNotPerfCur.CodeValue = listEhrCodes[0].CodeValue;
                EhrNotPerfCur.CodeSystem = listEhrCodes[0].CodeSystem;
            }
            else
            {
                List<String> listCodeDescripts = new List<String>();
                for (int i = 0;i < listEhrCodes.Count;i++)
                {
                    listCodeDescripts.Add(listEhrCodes[i].CodeValue + " - " + listEhrCodes[i].Description);
                }
                InputBox chooseItem = new InputBox(Lan.g(this,"Select the " + Enum.GetNames(EhrNotPerformedItem.class)[SelectedItemIndex] + " not being performed from the list below."), listCodeDescripts);
                if (SelectedItemIndex == ((Enum)EhrNotPerformedItem.InfluenzaVaccination).ordinal())
                {
                    chooseItem.comboSelection.DropDownWidth = 730;
                }
                 
                if (chooseItem.ShowDialog() != DialogResult.OK)
                {
                    DialogResult = DialogResult.Cancel;
                    return ;
                }
                 
                if (chooseItem.comboSelection.SelectedIndex == -1)
                {
                    MsgBox.show(this,"You must select the " + Enum.GetNames(EhrNotPerformedItem.class)[SelectedItemIndex] + " not being performed.");
                    DialogResult = DialogResult.Cancel;
                    return ;
                }
                 
                EhrNotPerfCur.CodeValue = listEhrCodes[chooseItem.comboSelection.SelectedIndex].CodeValue;
                EhrNotPerfCur.CodeSystem = listEhrCodes[chooseItem.comboSelection.SelectedIndex].CodeSystem;
            } 
        }
         
        for (int i = 0;i < listEhrCodes.Count;i++)
        {
            if (StringSupport.equals(listEhrCodes[i].CodeValue, EhrNotPerfCur.CodeValue) && StringSupport.equals(listEhrCodes[i].CodeSystem, EhrNotPerfCur.CodeSystem))
            {
                textDescription.Text = listEhrCodes[i].Description;
            }
             
        }
        textCode.Text = EhrNotPerfCur.CodeValue;
        textCodeSystem.Text = EhrNotPerfCur.CodeSystem;
        textDate.Text = EhrNotPerfCur.DateEntry.ToShortDateString();
        textNote.Text = EhrNotPerfCur.Note;
        fillReasonList();
        if (comboCodeReason.SelectedIndex > 0)
        {
            textCodeSystemReason.Text = listEhrCodesReason[comboCodeReason.SelectedIndex - 1].CodeSystem;
            textDescriptionReason.Text = listEhrCodesReason[comboCodeReason.SelectedIndex - 1].Description;
        }
         
    }

    private void fillReasonList() throws Exception {
        List<String> listValueSetOIDsReason = new List<String>();
        String medicalReason = "2.16.840.1.113883.3.526.3.1007";
        //'Medical Reason' value set
        String patientReason = "2.16.840.1.113883.3.526.3.1008";
        //'Patient Reason' value set
        String systemReason = "2.16.840.1.113883.3.526.3.1009";
        //'System Reason' value set
        String patientRefusedReason = "2.16.840.1.113883.3.600.1.1503";
        //'Patient Reason Refused' value set
        String medicalOrOtherReason = "2.16.840.1.113883.3.600.1.1502";
        //'Medical or Other reason not done' value set
        String limitedLifeExpectancy = "2.16.840.1.113883.3.526.3.1259";
        //'Limited Life Expectancy' value set
        switch(SelectedItemIndex)
        {
            case 0: 
                //BMIExam
                listValueSetOIDsReason = new List<String>{ patientRefusedReason, medicalOrOtherReason };
                break;
            case 1: 
                //InfluenzaVaccination
                if (radioPatReason.Checked)
                {
                    listValueSetOIDsReason = new List<String>{ patientReason };
                }
                else if (radioSysReason.Checked)
                {
                    listValueSetOIDsReason = new List<String>{ systemReason };
                }
                else if (radioMedReason.Checked)
                {
                    listValueSetOIDsReason = new List<String>{ medicalReason };
                }
                else
                {
                    //if new or loading a previously saved item not performed, no radio is selected, set the appropriate radio and fill the list
                    if (EhrNotPerfCur.getIsNew())
                    {
                        radioMedReason.Checked = true;
                        listValueSetOIDsReason = new List<String>{ medicalReason };
                    }
                    else
                    {
                        //default to medical reason list if new and no radio selected yet
                        //if previously saved, find the sub list this reason belongs to
                        List<List<String>> listSublists = new List<List<String>>{ new List<String>{ medicalReason }, new List<String>{ patientReason }, new List<String>{ systemReason } };
                        boolean found = false;
                        for (int i = 0;i < listSublists.Count;i++)
                        {
                            listEhrCodesReason = EhrCodes.GetForValueSetOIDs(listSublists[i], true);
                            for (int j = 0;j < listEhrCodesReason.Count;j++)
                            {
                                if (StringSupport.equals(listEhrCodesReason[j].CodeValue, EhrNotPerfCur.CodeValueReason) && StringSupport.equals(listEhrCodesReason[j].CodeSystem, EhrNotPerfCur.CodeSystemReason))
                                {
                                    found = true;
                                    break;
                                }
                                 
                            }
                            if (found)
                            {
                                if (i == 0)
                                {
                                    radioMedReason.Checked = true;
                                }
                                else if (i == 1)
                                {
                                    radioPatReason.Checked = true;
                                }
                                else
                                {
                                    radioSysReason.Checked = true;
                                }  
                                listValueSetOIDsReason = listSublists[i];
                                break;
                            }
                             
                        }
                    } 
                }   
                break;
            case 2: 
                //TobaccoScreening
                listValueSetOIDsReason = new List<String>{ medicalReason, limitedLifeExpectancy };
                break;
            case 3: 
                //DocumentCurrentMeds
                listValueSetOIDsReason = new List<String>{ medicalOrOtherReason };
                break;
            default: 
                break;
        
        }
        //should never happen
        listEhrCodesReason = EhrCodes.GetForValueSetOIDs(listValueSetOIDsReason, true);
        //these are all SNOMEDCT codes and will only show if they exist in the snomed table.
        if (listEhrCodesReason.Count == 0)
        {
            MsgBox.show(this,"There are no codes in the database for reasons not performed.  You must run the Code System Importer tool in Setup | EHR to import the SNOMEDCT table in order to enter a valid reason.");
        }
         
        comboCodeReason.Items.Clear();
        comboCodeReason.Items.Add("none");
        comboCodeReason.SelectedIndex = 0;
        for (int i = 0;i < listEhrCodesReason.Count;i++)
        {
            //default to 'none' if no reason set for the not performed item
            comboCodeReason.Items.Add(listEhrCodesReason[i].CodeValue);
            if (StringSupport.equals(EhrNotPerfCur.CodeValueReason, listEhrCodesReason[i].CodeValue) && StringSupport.equals(EhrNotPerfCur.CodeSystemReason, listEhrCodesReason[i].CodeSystem))
            {
                comboCodeReason.SelectedIndex = i + 1;
                //+1 for 'none'
                textCodeSystemReason.Text = listEhrCodesReason[i].CodeSystem;
                textDescriptionReason.Text = listEhrCodesReason[i].Description;
            }
             
        }
    }

    private void radioReasonMed_Click(Object sender, EventArgs e) throws Exception {
        textCodeSystemReason.Clear();
        textDescriptionReason.Clear();
        fillReasonList();
    }

    private void radioReasonPat_Click(Object sender, EventArgs e) throws Exception {
        textCodeSystemReason.Clear();
        textDescriptionReason.Clear();
        fillReasonList();
    }

    private void radioReasonSys_Click(Object sender, EventArgs e) throws Exception {
        textCodeSystemReason.Clear();
        textDescriptionReason.Clear();
        fillReasonList();
    }

    private void comboReasonCode_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboCodeReason.SelectedIndex == 0)
        {
            textCodeSystemReason.Clear();
            textDescriptionReason.Clear();
        }
        else
        {
            textCodeSystemReason.Text = listEhrCodesReason[comboCodeReason.SelectedIndex - 1].CodeSystem;
            textDescriptionReason.Text = listEhrCodesReason[comboCodeReason.SelectedIndex - 1].Description;
        } 
    }

    private void comboCodeReason_DropDown(Object sender, EventArgs e) throws Exception {
        int selectedIndex = comboCodeReason.SelectedIndex;
        comboCodeReason.Items.Clear();
        comboCodeReason.Items.Add("none");
        for (int i = 0;i < listEhrCodesReason.Count;i++)
        {
            comboCodeReason.Items.Add(listEhrCodesReason[i].CodeValue + " - " + listEhrCodesReason[i].Description);
        }
        comboCodeReason.SelectedIndex = selectedIndex;
    }

    private void comboCodeReason_DropDownClosed(Object sender, EventArgs e) throws Exception {
        int selectedIndex = comboCodeReason.SelectedIndex;
        comboCodeReason.Items.Clear();
        comboCodeReason.Items.Add("none");
        for (int i = 0;i < listEhrCodesReason.Count;i++)
        {
            comboCodeReason.Items.Add(listEhrCodesReason[i].CodeValue);
        }
        comboCodeReason.SelectedIndex = selectedIndex;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (EhrNotPerfCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete?"))
        {
            return ;
        }
         
        Vitalsign vitCur = Vitalsigns.getFromEhrNotPerformedNum(EhrNotPerfCur.EhrNotPerformedNum);
        if (vitCur != null)
        {
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Deleting this will remove it from the vitalsign exam it refers to.\r\nDelete anyway?"))
            {
                return ;
            }
             
            vitCur.EhrNotPerformedNum = 0;
            Vitalsigns.update(vitCur);
        }
         
        EhrNotPerformeds.delete(EhrNotPerfCur.EhrNotPerformedNum);
        DialogResult = DialogResult.Cancel;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //validate--------------------------------------
        DateTime date = new DateTime();
        if (StringSupport.equals(textDate.Text, ""))
        {
            MsgBox.show(this,"Please enter a date.");
            return ;
        }
         
        try
        {
            date = DateTime.Parse(textDate.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"Please fix date first.");
            return ;
        }

        //we force the date to match the item not being performed (like vitalsign exam) by making the date text box read only if launched from other item.  Users can still manually add a not performed item from FormEhrNotPerformed by pressing Add and choose any valid date they wish, but it will not be linked to an item.
        String codeValReas = "";
        String codeSysReas = "";
        if (comboCodeReason.SelectedIndex < 1)
        {
            //selected 'none' or possibly still -1 (although -1 should never happen)
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"If you do not select one of the reasons provided it may be harder to meet your Clinical Quality Measures.  Are you sure you want to continue without selecting a valid reason for not performing the " + Enum.GetNames(EhrNotPerformedItem.class)[SelectedItemIndex] + "?"))
            {
                return ;
            }
             
            codeValReas = "";
            codeSysReas = "";
        }
        else
        {
            codeValReas = listEhrCodesReason[comboCodeReason.SelectedIndex - 1].CodeValue;
            //SelectedIndex-1 to account for 'none'
            codeSysReas = listEhrCodesReason[comboCodeReason.SelectedIndex - 1].CodeSystem;
        } 
        //save--------------------------------------
        EhrNotPerfCur.DateEntry = date;
        EhrNotPerfCur.CodeValueReason = codeValReas;
        EhrNotPerfCur.CodeSystemReason = codeSysReas;
        EhrNotPerfCur.Note = textNote.Text;
        if (EhrNotPerfCur.getIsNew())
        {
            EhrNotPerfCur.EhrNotPerformedNum = EhrNotPerformeds.insert(EhrNotPerfCur);
        }
        else
        {
            EhrNotPerformeds.update(EhrNotPerfCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrNotPerformedEdit.class);
        this.textDate = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupItem = new System.Windows.Forms.GroupBox();
        this.textDescription = new OpenDental.ODtextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.labelBPsExamCode = new System.Windows.Forms.Label();
        this.textCodeSystem = new System.Windows.Forms.TextBox();
        this.labelBMIExamCode = new System.Windows.Forms.Label();
        this.textCode = new System.Windows.Forms.TextBox();
        this.groupReason = new System.Windows.Forms.GroupBox();
        this.radioMedReason = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.radioSysReason = new System.Windows.Forms.RadioButton();
        this.label2 = new System.Windows.Forms.Label();
        this.radioPatReason = new System.Windows.Forms.RadioButton();
        this.label4 = new System.Windows.Forms.Label();
        this.comboCodeReason = new System.Windows.Forms.ComboBox();
        this.textDescriptionReason = new OpenDental.ODtextBox();
        this.textCodeSystemReason = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.butOK = new System.Windows.Forms.Button();
        this.butCancel = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.textNote = new OpenDental.ODtextBox();
        this.groupItem.SuspendLayout();
        this.groupReason.SuspendLayout();
        this.SuspendLayout();
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(110, 18);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(80, 20);
        this.textDate.TabIndex = 143;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(12, 20);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(95, 17);
        this.label5.TabIndex = 144;
        this.label5.Text = "Date";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupItem
        //
        this.groupItem.Controls.Add(this.textDescription);
        this.groupItem.Controls.Add(this.label3);
        this.groupItem.Controls.Add(this.labelBPsExamCode);
        this.groupItem.Controls.Add(this.textCodeSystem);
        this.groupItem.Controls.Add(this.labelBMIExamCode);
        this.groupItem.Controls.Add(this.textCode);
        this.groupItem.Location = new System.Drawing.Point(12, 42);
        this.groupItem.Name = "groupItem";
        this.groupItem.Size = new System.Drawing.Size(353, 125);
        this.groupItem.TabIndex = 145;
        this.groupItem.TabStop = false;
        this.groupItem.Text = "Item Not Performed";
        //
        // textDescription
        //
        this.textDescription.AcceptsTab = true;
        this.textDescription.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textDescription.DetectUrls = false;
        this.textDescription.Location = new System.Drawing.Point(98, 65);
        this.textDescription.Name = "textDescription";
        this.textDescription.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textDescription.ReadOnly = true;
        this.textDescription.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textDescription.Size = new System.Drawing.Size(245, 51);
        this.textDescription.TabIndex = 141;
        this.textDescription.Text = "";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 68);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(83, 17);
        this.label3.TabIndex = 142;
        this.label3.Text = "Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelBPsExamCode
        //
        this.labelBPsExamCode.Location = new System.Drawing.Point(12, 44);
        this.labelBPsExamCode.Name = "labelBPsExamCode";
        this.labelBPsExamCode.Size = new System.Drawing.Size(83, 17);
        this.labelBPsExamCode.TabIndex = 140;
        this.labelBPsExamCode.Text = "Code System";
        this.labelBPsExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCodeSystem
        //
        this.textCodeSystem.Location = new System.Drawing.Point(98, 41);
        this.textCodeSystem.Name = "textCodeSystem";
        this.textCodeSystem.ReadOnly = true;
        this.textCodeSystem.Size = new System.Drawing.Size(100, 20);
        this.textCodeSystem.TabIndex = 139;
        //
        // labelBMIExamCode
        //
        this.labelBMIExamCode.Location = new System.Drawing.Point(12, 20);
        this.labelBMIExamCode.Name = "labelBMIExamCode";
        this.labelBMIExamCode.Size = new System.Drawing.Size(83, 17);
        this.labelBMIExamCode.TabIndex = 138;
        this.labelBMIExamCode.Text = "Code";
        this.labelBMIExamCode.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCode
        //
        this.textCode.Location = new System.Drawing.Point(98, 17);
        this.textCode.Name = "textCode";
        this.textCode.ReadOnly = true;
        this.textCode.Size = new System.Drawing.Size(100, 20);
        this.textCode.TabIndex = 137;
        //
        // groupReason
        //
        this.groupReason.Controls.Add(this.radioMedReason);
        this.groupReason.Controls.Add(this.label1);
        this.groupReason.Controls.Add(this.radioSysReason);
        this.groupReason.Controls.Add(this.label2);
        this.groupReason.Controls.Add(this.radioPatReason);
        this.groupReason.Controls.Add(this.label4);
        this.groupReason.Controls.Add(this.comboCodeReason);
        this.groupReason.Controls.Add(this.textDescriptionReason);
        this.groupReason.Controls.Add(this.textCodeSystemReason);
        this.groupReason.Location = new System.Drawing.Point(12, 173);
        this.groupReason.Name = "groupReason";
        this.groupReason.Size = new System.Drawing.Size(353, 125);
        this.groupReason.TabIndex = 146;
        this.groupReason.TabStop = false;
        this.groupReason.Text = "Reason Not Performed";
        //
        // radioMedReason
        //
        this.radioMedReason.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioMedReason.Location = new System.Drawing.Point(19, 16);
        this.radioMedReason.Name = "radioMedReason";
        this.radioMedReason.Size = new System.Drawing.Size(100, 16);
        this.radioMedReason.TabIndex = 156;
        this.radioMedReason.Text = "Medical Reason";
        this.radioMedReason.Visible = false;
        this.radioMedReason.Click += new System.EventHandler(this.radioReasonMed_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 84);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(83, 17);
        this.label1.TabIndex = 151;
        this.label1.Text = "Description";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // radioSysReason
        //
        this.radioSysReason.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioSysReason.Location = new System.Drawing.Point(231, 16);
        this.radioSysReason.Name = "radioSysReason";
        this.radioSysReason.Size = new System.Drawing.Size(100, 16);
        this.radioSysReason.TabIndex = 158;
        this.radioSysReason.Text = "System Reason";
        this.radioSysReason.Visible = false;
        this.radioSysReason.Click += new System.EventHandler(this.radioReasonSys_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 62);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(83, 17);
        this.label2.TabIndex = 150;
        this.label2.Text = "Code System";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // radioPatReason
        //
        this.radioPatReason.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioPatReason.Location = new System.Drawing.Point(125, 16);
        this.radioPatReason.Name = "radioPatReason";
        this.radioPatReason.Size = new System.Drawing.Size(100, 16);
        this.radioPatReason.TabIndex = 157;
        this.radioPatReason.Text = "Patient Reason";
        this.radioPatReason.Visible = false;
        this.radioPatReason.Click += new System.EventHandler(this.radioReasonPat_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(12, 37);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(83, 17);
        this.label4.TabIndex = 149;
        this.label4.Text = "Code";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboCodeReason
        //
        this.comboCodeReason.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCodeReason.DropDownWidth = 385;
        this.comboCodeReason.Location = new System.Drawing.Point(98, 35);
        this.comboCodeReason.MaxDropDownItems = 30;
        this.comboCodeReason.Name = "comboCodeReason";
        this.comboCodeReason.Size = new System.Drawing.Size(100, 21);
        this.comboCodeReason.TabIndex = 148;
        this.comboCodeReason.DropDown += new System.EventHandler(this.comboCodeReason_DropDown);
        this.comboCodeReason.SelectionChangeCommitted += new System.EventHandler(this.comboReasonCode_SelectionChangeCommitted);
        this.comboCodeReason.DropDownClosed += new System.EventHandler(this.comboCodeReason_DropDownClosed);
        //
        // textDescriptionReason
        //
        this.textDescriptionReason.AcceptsTab = true;
        this.textDescriptionReason.BackColor = System.Drawing.SystemColors.ControlLightLight;
        this.textDescriptionReason.DetectUrls = false;
        this.textDescriptionReason.Location = new System.Drawing.Point(98, 82);
        this.textDescriptionReason.Name = "textDescriptionReason";
        this.textDescriptionReason.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textDescriptionReason.ReadOnly = true;
        this.textDescriptionReason.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textDescriptionReason.Size = new System.Drawing.Size(245, 34);
        this.textDescriptionReason.TabIndex = 147;
        this.textDescriptionReason.Text = "";
        //
        // textCodeSystemReason
        //
        this.textCodeSystemReason.Location = new System.Drawing.Point(98, 60);
        this.textCodeSystemReason.Name = "textCodeSystemReason";
        this.textCodeSystemReason.ReadOnly = true;
        this.textCodeSystemReason.Size = new System.Drawing.Size(100, 20);
        this.textCodeSystemReason.TabIndex = 146;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(12, 307);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(95, 17);
        this.label6.TabIndex = 152;
        this.label6.Text = "Note";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butOK
        //
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.Location = new System.Drawing.Point(209, 379);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 23);
        this.butOK.TabIndex = 153;
        this.butOK.Text = "&OK";
        this.butOK.UseVisualStyleBackColor = true;
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(290, 379);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 23);
        this.butCancel.TabIndex = 154;
        this.butCancel.Text = "&Cancel";
        this.butCancel.UseVisualStyleBackColor = true;
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butDelete
        //
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.Location = new System.Drawing.Point(12, 379);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 23);
        this.butDelete.TabIndex = 155;
        this.butDelete.TabStop = false;
        this.butDelete.Text = "&Delete";
        this.butDelete.UseVisualStyleBackColor = true;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textNote
        //
        this.textNote.AcceptsTab = true;
        this.textNote.DetectUrls = false;
        this.textNote.Location = new System.Drawing.Point(110, 305);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(245, 60);
        this.textNote.TabIndex = 147;
        this.textNote.Text = "";
        //
        // FormEhrNotPerformedEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(377, 414);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.groupReason);
        this.Controls.Add(this.groupItem);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label5);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormEhrNotPerformedEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Not Performed Item with Reason";
        this.Load += new System.EventHandler(this.FormEhrNotPerformedEdit_Load);
        this.groupItem.ResumeLayout(false);
        this.groupItem.PerformLayout();
        this.groupReason.ResumeLayout(false);
        this.groupReason.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.TextBox textDate = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupItem = new System.Windows.Forms.GroupBox();
    private OpenDental.ODtextBox textDescription;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelBPsExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCodeSystem = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelBMIExamCode = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupReason = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboCodeReason = new System.Windows.Forms.ComboBox();
    private OpenDental.ODtextBox textDescriptionReason;
    private System.Windows.Forms.TextBox textCodeSystemReason = new System.Windows.Forms.TextBox();
    private OpenDental.ODtextBox textNote;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butOK = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.RadioButton radioMedReason = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioSysReason = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioPatReason = new System.Windows.Forms.RadioButton();
}


