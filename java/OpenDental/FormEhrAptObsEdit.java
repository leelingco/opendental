//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormIcd10s;
import OpenDental.FormIcd9s;
import OpenDental.FormLoincs;
import OpenDental.FormSnomeds;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EhrAptObs;
import OpenDentBusiness.EhrAptObses;
import OpenDentBusiness.EhrAptObsIdentifier;
import OpenDentBusiness.EhrAptObsType;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Ucums;
import OpenDental.FormEhrAptObsEdit;
import OpenDental.Properties.Resources;

public class FormEhrAptObsEdit  extends Form 
{

    private EhrAptObs _ehrAptObsCur = null;
    private Appointment _appt = null;
    private String _strValCodeSystem = "";
    private Loinc _loincValue = null;
    private Snomed _snomedValue = null;
    private ICD9 _icd9Value = null;
    private Icd10 _icd10Value = null;
    public FormEhrAptObsEdit(EhrAptObs ehrAptObs) throws Exception {
        initializeComponent();
        Lan.F(this);
        _ehrAptObsCur = ehrAptObs;
    }

    private void formEhrAptObsEdit_Load(Object sender, EventArgs e) throws Exception {
        _appt = Appointments.getOneApt(_ehrAptObsCur.AptNum);
        comboObservationQuestion.Items.Clear();
        String[] arrayQuestionNames = Enum.GetNames(EhrAptObsIdentifier.class);
        for (int i = 0;i < arrayQuestionNames.Length;i++)
        {
            comboObservationQuestion.Items.Add(arrayQuestionNames[i]);
            EhrAptObsIdentifier ehrAptObsIdentifier = EhrAptObsIdentifier.values()[i];
            if (_ehrAptObsCur.IdentifyingCode == ehrAptObsIdentifier)
            {
                comboObservationQuestion.SelectedIndex = i;
            }
             
        }
        listValueType.Items.Clear();
        String[] arrayValueTypeNames = Enum.GetNames(EhrAptObsType.class);
        for (int i = 0;i < arrayValueTypeNames.Length;i++)
        {
            listValueType.Items.Add(arrayValueTypeNames[i]);
            EhrAptObsType ehrAptObsType = EhrAptObsType.values()[i];
            if (_ehrAptObsCur.ValType == ehrAptObsType)
            {
                listValueType.SelectedIndex = i;
            }
             
        }
        if (_ehrAptObsCur.ValType == EhrAptObsType.Coded)
        {
            _strValCodeSystem = _ehrAptObsCur.ValCodeSystem;
            if (StringSupport.equals(_ehrAptObsCur.ValCodeSystem, "LOINC"))
            {
                _loincValue = Loincs.getByCode(_ehrAptObsCur.ValReported);
                textValue.Text = _loincValue.NameShort;
            }
            else if (StringSupport.equals(_ehrAptObsCur.ValCodeSystem, "SNOMEDCT"))
            {
                _snomedValue = Snomeds.getByCode(_ehrAptObsCur.ValReported);
                textValue.Text = _snomedValue.Description;
            }
            else if (StringSupport.equals(_ehrAptObsCur.ValCodeSystem, "ICD9"))
            {
                _icd9Value = ICD9s.getByCode(_ehrAptObsCur.ValReported);
                textValue.Text = _icd9Value.Description;
            }
            else if (StringSupport.equals(_ehrAptObsCur.ValCodeSystem, "ICD10"))
            {
                _icd10Value = Icd10s.getByCode(_ehrAptObsCur.ValReported);
                textValue.Text = _icd10Value.Description;
            }
                
        }
        else
        {
            textValue.Text = _ehrAptObsCur.ValReported;
        } 
        comboUnits.Items.Clear();
        comboUnits.Items.Add("none");
        comboUnits.SelectedIndex = 0;
        List<String> listUcumCodes = Ucums.getAllCodes();
        for (int i = 0;i < listUcumCodes.Count;i++)
        {
            String ucumCode = listUcumCodes[i];
            comboUnits.Items.Add(ucumCode);
            if (StringSupport.equals(ucumCode, _ehrAptObsCur.UcumCode))
            {
                comboUnits.SelectedIndex = i + 1;
            }
             
        }
        setFlags();
    }

    private void listValueType_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        textValue.Text = "";
        _loincValue = null;
        _snomedValue = null;
        _icd9Value = null;
        _icd10Value = null;
        setFlags();
    }

    private void setFlags() throws Exception {
        labelValue.Text = "Value";
        textValue.ReadOnly = false;
        butPickValueLoinc.Enabled = false;
        butPickValueSnomedct.Enabled = false;
        butPickValueIcd9.Enabled = false;
        butPickValueIcd10.Enabled = false;
        if (listValueType.SelectedIndex == ((Enum)EhrAptObsType.Coded).ordinal())
        {
            labelValue.Text = _strValCodeSystem + " Value";
            textValue.ReadOnly = true;
            butPickValueLoinc.Enabled = true;
            butPickValueSnomedct.Enabled = true;
            butPickValueIcd9.Enabled = true;
            butPickValueIcd10.Enabled = true;
        }
         
        if (listValueType.SelectedIndex == ((Enum)EhrAptObsType.Numeric).ordinal())
        {
            comboUnits.Enabled = true;
        }
        else
        {
            comboUnits.Enabled = false;
        } 
        if (listValueType.SelectedIndex == ((Enum)EhrAptObsType.Address).ordinal())
        {
            labelValue.Text = "Facility Address";
            textValue.ReadOnly = true;
            String sendingFacilityName = PrefC.getString(PrefName.PracticeTitle);
            String sendingFacilityAddress1 = PrefC.getString(PrefName.PracticeAddress);
            String sendingFacilityAddress2 = PrefC.getString(PrefName.PracticeAddress2);
            String sendingFacilityCity = PrefC.getString(PrefName.PracticeCity);
            String sendingFacilityState = PrefC.getString(PrefName.PracticeST);
            String sendingFacilityZip = PrefC.getString(PrefName.PracticeZip);
            if (!PrefC.getBool(PrefName.EasyNoClinics) && _appt.ClinicNum != 0)
            {
                //Using clinics and a clinic is assigned.
                Clinic clinic = Clinics.getClinic(_appt.ClinicNum);
                sendingFacilityName = clinic.Description;
                sendingFacilityAddress1 = clinic.Address;
                sendingFacilityAddress2 = clinic.Address2;
                sendingFacilityCity = clinic.City;
                sendingFacilityState = clinic.State;
                sendingFacilityZip = clinic.Zip;
            }
             
            textValue.Text = sendingFacilityAddress1 + " " + sendingFacilityAddress2 + " " + sendingFacilityCity + " " + sendingFacilityState + " " + sendingFacilityZip;
        }
         
    }

    private void butPickValueLoinc_Click(Object sender, EventArgs e) throws Exception {
        FormLoincs formL = new FormLoincs();
        formL.IsSelectionMode = true;
        if (formL.ShowDialog() == DialogResult.OK)
        {
            _loincValue = formL.SelectedLoinc;
            textValue.Text = _loincValue.NameShort;
            _strValCodeSystem = "LOINC";
            labelValue.Text = _strValCodeSystem + " Value";
        }
         
    }

    private void butPickValueSnomedct_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds formS = new FormSnomeds();
        formS.IsSelectionMode = true;
        if (formS.ShowDialog() == DialogResult.OK)
        {
            _snomedValue = formS.SelectedSnomed;
            textValue.Text = _snomedValue.Description;
            _strValCodeSystem = "SNOMEDCT";
            labelValue.Text = _strValCodeSystem + " Value";
        }
         
    }

    private void butPickValueIcd9_Click(Object sender, EventArgs e) throws Exception {
        FormIcd9s formI = new FormIcd9s();
        formI.IsSelectionMode = true;
        if (formI.ShowDialog() == DialogResult.OK)
        {
            _icd9Value = formI.SelectedIcd9;
            textValue.Text = _icd9Value.Description;
            _strValCodeSystem = "ICD9";
            labelValue.Text = _strValCodeSystem + " Value";
        }
         
    }

    private void butPickValueIcd10_Click(Object sender, EventArgs e) throws Exception {
        FormIcd10s formI = new FormIcd10s();
        formI.IsSelectionMode = true;
        if (formI.ShowDialog() == DialogResult.OK)
        {
            _icd10Value = formI.SelectedIcd10;
            textValue.Text = _icd10Value.Description;
            _strValCodeSystem = "ICD10";
            labelValue.Text = _strValCodeSystem + " Value";
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (_ehrAptObsCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            EhrAptObses.delete(_ehrAptObsCur.EhrAptObsNum);
            _ehrAptObsCur.EhrAptObsNum = 0;
            //Signal to the calling code that the object has been deleted.
            DialogResult = DialogResult.OK;
        } 
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        EhrAptObsIdentifier ehrAptObsId = (EhrAptObsIdentifier)comboObservationQuestion.SelectedIndex;
        if (listValueType.SelectedIndex == ((Enum)EhrAptObsType.Address).ordinal() && ehrAptObsId != EhrAptObsIdentifier.TreatFacilityLocation || listValueType.SelectedIndex != ((Enum)EhrAptObsType.Address).ordinal() && ehrAptObsId == EhrAptObsIdentifier.TreatFacilityLocation)
        {
            MsgBox.show(this,"Value type Address must be used with question TreatFacilityLocation.");
            return ;
        }
         
        if (listValueType.SelectedIndex == ((Enum)EhrAptObsType.Coded).ordinal() && _loincValue == null && _snomedValue == null && _icd9Value == null && _icd10Value == null)
        {
            MsgBox.show(this,"Missing value code.");
            return ;
        }
         
        if (listValueType.SelectedIndex != ((Enum)EhrAptObsType.Coded).ordinal() && StringSupport.equals(textValue.Text, ""))
        {
            MsgBox.show(this,"Missing value.");
            return ;
        }
         
        _ehrAptObsCur.IdentifyingCode = ehrAptObsId;
        _ehrAptObsCur.ValType = (EhrAptObsType)listValueType.SelectedIndex;
        if (_ehrAptObsCur.ValType == EhrAptObsType.Coded)
        {
            _ehrAptObsCur.ValCodeSystem = _strValCodeSystem;
            if (StringSupport.equals(_strValCodeSystem, "LOINC"))
            {
                _ehrAptObsCur.ValReported = _loincValue.LoincCode;
            }
            else if (StringSupport.equals(_strValCodeSystem, "SNOMEDCT"))
            {
                _ehrAptObsCur.ValReported = _snomedValue.SnomedCode;
            }
            else if (StringSupport.equals(_strValCodeSystem, "ICD9"))
            {
                _ehrAptObsCur.ValReported = _icd9Value.ICD9Code;
            }
            else if (StringSupport.equals(_strValCodeSystem, "ICD10"))
            {
                _ehrAptObsCur.ValReported = _icd10Value.Icd10Code;
            }
                
        }
        else if (_ehrAptObsCur.ValType == EhrAptObsType.Address)
        {
            _ehrAptObsCur.ValCodeSystem = "";
            _ehrAptObsCur.ValReported = "";
        }
        else
        {
            _ehrAptObsCur.ValCodeSystem = "";
            _ehrAptObsCur.ValReported = textValue.Text;
        }  
        _ehrAptObsCur.UcumCode = "";
        if (comboUnits.Enabled)
        {
            _ehrAptObsCur.UcumCode = comboUnits.Items[comboUnits.SelectedIndex].ToString();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrAptObsEdit.class);
        this.comboUnits = new System.Windows.Forms.ComboBox();
        this.labelValueUnitsNote = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textValue = new System.Windows.Forms.TextBox();
        this.labelValue = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.listValueType = new System.Windows.Forms.ListBox();
        this.butPickValueIcd10 = new OpenDental.UI.Button();
        this.butPickValueIcd9 = new OpenDental.UI.Button();
        this.butPickValueSnomedct = new OpenDental.UI.Button();
        this.butPickValueLoinc = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.comboObservationQuestion = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // comboUnits
        //
        this.comboUnits.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboUnits.FormattingEnabled = true;
        this.comboUnits.Location = new System.Drawing.Point(143, 165);
        this.comboUnits.Name = "comboUnits";
        this.comboUnits.Size = new System.Drawing.Size(113, 21);
        this.comboUnits.TabIndex = 303;
        //
        // labelValueUnitsNote
        //
        this.labelValueUnitsNote.Location = new System.Drawing.Point(262, 165);
        this.labelValueUnitsNote.Name = "labelValueUnitsNote";
        this.labelValueUnitsNote.Size = new System.Drawing.Size(260, 17);
        this.labelValueUnitsNote.TabIndex = 298;
        this.labelValueUnitsNote.Text = "(Only needed if Value Type is Numeric)";
        this.labelValueUnitsNote.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(11, 165);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(131, 17);
        this.label5.TabIndex = 294;
        this.label5.Text = "Value Units";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textValue
        //
        this.textValue.Location = new System.Drawing.Point(143, 139);
        this.textValue.Name = "textValue";
        this.textValue.Size = new System.Drawing.Size(309, 20);
        this.textValue.TabIndex = 291;
        //
        // labelValue
        //
        this.labelValue.Location = new System.Drawing.Point(8, 139);
        this.labelValue.Name = "labelValue";
        this.labelValue.Size = new System.Drawing.Size(134, 17);
        this.labelValue.TabIndex = 290;
        this.labelValue.Text = "Value";
        this.labelValue.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(5, 64);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(137, 17);
        this.label1.TabIndex = 287;
        this.label1.Text = "Value Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listValueType
        //
        this.listValueType.FormattingEnabled = true;
        this.listValueType.Location = new System.Drawing.Point(143, 64);
        this.listValueType.Name = "listValueType";
        this.listValueType.Size = new System.Drawing.Size(113, 69);
        this.listValueType.TabIndex = 286;
        this.listValueType.SelectedIndexChanged += new System.EventHandler(this.listValueType_SelectedIndexChanged);
        //
        // butPickValueIcd10
        //
        this.butPickValueIcd10.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickValueIcd10.setAutosize(true);
        this.butPickValueIcd10.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickValueIcd10.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickValueIcd10.setCornerRadius(4F);
        this.butPickValueIcd10.Location = new System.Drawing.Point(339, 99);
        this.butPickValueIcd10.Name = "butPickValueIcd10";
        this.butPickValueIcd10.Size = new System.Drawing.Size(48, 20);
        this.butPickValueIcd10.TabIndex = 312;
        this.butPickValueIcd10.Text = "ICD10";
        this.butPickValueIcd10.Click += new System.EventHandler(this.butPickValueIcd10_Click);
        //
        // butPickValueIcd9
        //
        this.butPickValueIcd9.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickValueIcd9.setAutosize(true);
        this.butPickValueIcd9.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickValueIcd9.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickValueIcd9.setCornerRadius(4F);
        this.butPickValueIcd9.Location = new System.Drawing.Point(339, 73);
        this.butPickValueIcd9.Name = "butPickValueIcd9";
        this.butPickValueIcd9.Size = new System.Drawing.Size(48, 20);
        this.butPickValueIcd9.TabIndex = 311;
        this.butPickValueIcd9.Text = "ICD9";
        this.butPickValueIcd9.Click += new System.EventHandler(this.butPickValueIcd9_Click);
        //
        // butPickValueSnomedct
        //
        this.butPickValueSnomedct.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickValueSnomedct.setAutosize(true);
        this.butPickValueSnomedct.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickValueSnomedct.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickValueSnomedct.setCornerRadius(4F);
        this.butPickValueSnomedct.Location = new System.Drawing.Point(259, 99);
        this.butPickValueSnomedct.Name = "butPickValueSnomedct";
        this.butPickValueSnomedct.Size = new System.Drawing.Size(74, 20);
        this.butPickValueSnomedct.TabIndex = 310;
        this.butPickValueSnomedct.Text = "SNOMEDCT";
        this.butPickValueSnomedct.Click += new System.EventHandler(this.butPickValueSnomedct_Click);
        //
        // butPickValueLoinc
        //
        this.butPickValueLoinc.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickValueLoinc.setAutosize(true);
        this.butPickValueLoinc.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickValueLoinc.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickValueLoinc.setCornerRadius(4F);
        this.butPickValueLoinc.Location = new System.Drawing.Point(259, 73);
        this.butPickValueLoinc.Name = "butPickValueLoinc";
        this.butPickValueLoinc.Size = new System.Drawing.Size(74, 20);
        this.butPickValueLoinc.TabIndex = 309;
        this.butPickValueLoinc.Text = "LOINC";
        this.butPickValueLoinc.Click += new System.EventHandler(this.butPickValueLoinc_Click);
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
        this.butDelete.Location = new System.Drawing.Point(5, 236);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(92, 24);
        this.butDelete.TabIndex = 302;
        this.butDelete.Text = "&Delete";
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
        this.butOK.Location = new System.Drawing.Point(362, 234);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 285;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(443, 234);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 284;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // comboObservationQuestion
        //
        this.comboObservationQuestion.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboObservationQuestion.FormattingEnabled = true;
        this.comboObservationQuestion.Location = new System.Drawing.Point(143, 37);
        this.comboObservationQuestion.Name = "comboObservationQuestion";
        this.comboObservationQuestion.Size = new System.Drawing.Size(113, 21);
        this.comboObservationQuestion.TabIndex = 314;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 37);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(140, 17);
        this.label2.TabIndex = 313;
        this.label2.Text = "Observation Question";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormEhrAptObsEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(526, 272);
        this.Controls.Add(this.comboObservationQuestion);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butPickValueIcd10);
        this.Controls.Add(this.butPickValueIcd9);
        this.Controls.Add(this.butPickValueSnomedct);
        this.Controls.Add(this.butPickValueLoinc);
        this.Controls.Add(this.comboUnits);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.labelValueUnitsNote);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textValue);
        this.Controls.Add(this.labelValue);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listValueType);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(100, 100);
        this.Name = "FormEhrAptObsEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Appointment Observation";
        this.Load += new System.EventHandler(this.FormEhrAptObsEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private System.Windows.Forms.ComboBox comboUnits = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelValueUnitsNote = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textValue = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelValue = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listValueType = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butPickValueLoinc;
    private OpenDental.UI.Button butPickValueSnomedct;
    private OpenDental.UI.Button butPickValueIcd9;
    private OpenDental.UI.Button butPickValueIcd10;
    private System.Windows.Forms.ComboBox comboObservationQuestion = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


