//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:20 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormMedicationEdit;
import OpenDental.FormRxNorms;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.RxNorms;

/**
* Summary description for FormBasicTemplate.
*/
public class FormMedicationEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textGenericName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textMedName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.ODtextBox textNotes;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboPatients = new System.Windows.Forms.ComboBox();
    /**
    * 
    */
    public boolean GenericOnly = new boolean();
    private System.Windows.Forms.Label labelBrands = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelPatients = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboBrands = new System.Windows.Forms.ComboBox();
    //does not allow changing to non generic.
    /**
    * 
    */
    private String[] _patNameMeds = new String[]();
    private String[] _patNameAllergies = new String[]();
    /**
    * 
    */
    private String[] Brands = new String[]();
    private Label label5 = new Label();
    private TextBox textRxNormDesc = new TextBox();
    private OpenDental.UI.Button butRxNormSelect;
    private Label labelPatientAllergy = new Label();
    private ComboBox comboPatientAllergy = new ComboBox();
    public Medication MedicationCur;
    /**
    * 
    */
    public FormMedicationEdit() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Clean up any resources being used.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing)
        {
            if (components != null)
            {
                components.Dispose();
            }
             
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormMedicationEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textGenericName = new System.Windows.Forms.TextBox();
        this.textMedName = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textNotes = new OpenDental.ODtextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.comboPatients = new System.Windows.Forms.ComboBox();
        this.labelPatients = new System.Windows.Forms.Label();
        this.labelBrands = new System.Windows.Forms.Label();
        this.comboBrands = new System.Windows.Forms.ComboBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.labelPatientAllergy = new System.Windows.Forms.Label();
        this.comboPatientAllergy = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.textRxNormDesc = new System.Windows.Forms.TextBox();
        this.butRxNormSelect = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(535, 466);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(535, 428);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(20, 70);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(127, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "Generic name";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textGenericName
        //
        this.textGenericName.Location = new System.Drawing.Point(148, 67);
        this.textGenericName.Name = "textGenericName";
        this.textGenericName.ReadOnly = true;
        this.textGenericName.Size = new System.Drawing.Size(248, 20);
        this.textGenericName.TabIndex = 3;
        //
        // textMedName
        //
        this.textMedName.Location = new System.Drawing.Point(148, 42);
        this.textMedName.Name = "textMedName";
        this.textMedName.Size = new System.Drawing.Size(434, 20);
        this.textMedName.TabIndex = 0;
        this.textMedName.TextChanged += new System.EventHandler(this.textMedName_TextChanged);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(20, 45);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(127, 17);
        this.label2.TabIndex = 6;
        this.label2.Text = "Drug name";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(20, 102);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(127, 47);
        this.label3.TabIndex = 8;
        this.label3.Text = "Notes\r\n(for generic only)";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textNotes
        //
        this.textNotes.AcceptsTab = true;
        this.textNotes.DetectUrls = false;
        this.textNotes.Location = new System.Drawing.Point(147, 103);
        this.textNotes.Name = "textNotes";
        this.textNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.MedicationEdit);
        this.textNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNotes.Size = new System.Drawing.Size(459, 194);
        this.textNotes.TabIndex = 9;
        this.textNotes.Text = "";
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
        this.butDelete.Location = new System.Drawing.Point(43, 459);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 26);
        this.butDelete.TabIndex = 32;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(399, 67);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(208, 31);
        this.label4.TabIndex = 33;
        this.label4.Text = "Make sure you spell it right, because you can\'t ever change it.";
        //
        // comboPatients
        //
        this.comboPatients.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPatients.Location = new System.Drawing.Point(144, 21);
        this.comboPatients.MaxDropDownItems = 30;
        this.comboPatients.Name = "comboPatients";
        this.comboPatients.Size = new System.Drawing.Size(299, 21);
        this.comboPatients.TabIndex = 34;
        //
        // labelPatients
        //
        this.labelPatients.Location = new System.Drawing.Point(6, 24);
        this.labelPatients.Name = "labelPatients";
        this.labelPatients.Size = new System.Drawing.Size(137, 17);
        this.labelPatients.TabIndex = 35;
        this.labelPatients.Text = "Patient medication";
        this.labelPatients.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelBrands
        //
        this.labelBrands.Location = new System.Drawing.Point(6, 78);
        this.labelBrands.Name = "labelBrands";
        this.labelBrands.Size = new System.Drawing.Size(137, 17);
        this.labelBrands.TabIndex = 37;
        this.labelBrands.Text = "Brands";
        this.labelBrands.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboBrands
        //
        this.comboBrands.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboBrands.Location = new System.Drawing.Point(144, 75);
        this.comboBrands.MaxDropDownItems = 30;
        this.comboBrands.Name = "comboBrands";
        this.comboBrands.Size = new System.Drawing.Size(299, 21);
        this.comboBrands.TabIndex = 36;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.labelPatientAllergy);
        this.groupBox1.Controls.Add(this.comboPatientAllergy);
        this.groupBox1.Controls.Add(this.labelPatients);
        this.groupBox1.Controls.Add(this.comboPatients);
        this.groupBox1.Controls.Add(this.labelBrands);
        this.groupBox1.Controls.Add(this.comboBrands);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(43, 303);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(462, 108);
        this.groupBox1.TabIndex = 38;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Dependencies";
        //
        // labelPatientAllergy
        //
        this.labelPatientAllergy.Location = new System.Drawing.Point(6, 51);
        this.labelPatientAllergy.Name = "labelPatientAllergy";
        this.labelPatientAllergy.Size = new System.Drawing.Size(137, 17);
        this.labelPatientAllergy.TabIndex = 39;
        this.labelPatientAllergy.Text = "Patient allergy";
        this.labelPatientAllergy.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboPatientAllergy
        //
        this.comboPatientAllergy.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPatientAllergy.Location = new System.Drawing.Point(144, 48);
        this.comboPatientAllergy.MaxDropDownItems = 30;
        this.comboPatientAllergy.Name = "comboPatientAllergy";
        this.comboPatientAllergy.Size = new System.Drawing.Size(299, 21);
        this.comboPatientAllergy.TabIndex = 38;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(20, 19);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(127, 17);
        this.label5.TabIndex = 6;
        this.label5.Text = "RxNorm";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textRxNormDesc
        //
        this.textRxNormDesc.Location = new System.Drawing.Point(148, 16);
        this.textRxNormDesc.Name = "textRxNormDesc";
        this.textRxNormDesc.ReadOnly = true;
        this.textRxNormDesc.Size = new System.Drawing.Size(434, 20);
        this.textRxNormDesc.TabIndex = 0;
        this.textRxNormDesc.TextChanged += new System.EventHandler(this.textMedName_TextChanged);
        //
        // butRxNormSelect
        //
        this.butRxNormSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRxNormSelect.setAutosize(true);
        this.butRxNormSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRxNormSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRxNormSelect.setCornerRadius(4F);
        this.butRxNormSelect.Location = new System.Drawing.Point(585, 15);
        this.butRxNormSelect.Name = "butRxNormSelect";
        this.butRxNormSelect.Size = new System.Drawing.Size(22, 22);
        this.butRxNormSelect.TabIndex = 2;
        this.butRxNormSelect.Text = "...";
        this.butRxNormSelect.Click += new System.EventHandler(this.butRxNorm_Click);
        //
        // FormMedicationEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(653, 506);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textRxNormDesc);
        this.Controls.Add(this.textMedName);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textGenericName);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butRxNormSelect);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormMedicationEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Medication";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormMedicationEdit_Closing);
        this.Load += new System.EventHandler(this.MedicationEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void medicationEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //Medications.Refresh() should have already been run.
        fillForm();
    }

    private void fillForm() throws Exception {
        textMedName.Text = MedicationCur.MedName;
        if (!IsNew)
        {
            textMedName.ReadOnly = true;
        }
         
        if (MedicationCur.MedicationNum == MedicationCur.GenericNum)
        {
            textGenericName.Text = MedicationCur.MedName;
            textNotes.Text = MedicationCur.Notes;
            textNotes.ReadOnly = false;
            Brands = Medications.getBrands(MedicationCur.MedicationNum);
            comboBrands.Items.Clear();
            comboBrands.Items.AddRange(Brands);
            if (Brands.Length > 0)
            {
                comboBrands.SelectedIndex = 0;
            }
             
        }
        else
        {
            textGenericName.Text = Medications.getMedication(MedicationCur.GenericNum).MedName;
            textNotes.Text = Medications.getMedication(MedicationCur.GenericNum).Notes;
            textNotes.ReadOnly = true;
            Brands = new String[0];
            comboBrands.Visible = false;
            labelBrands.Visible = false;
        } 
        _patNameMeds = Medications.getPatNamesForMed(MedicationCur.MedicationNum);
        comboPatients.Items.Clear();
        comboPatients.Items.AddRange(_patNameMeds);
        if (_patNameMeds.Length > 0)
        {
            comboPatients.SelectedIndex = 0;
        }
         
        AllergyDef alD = AllergyDefs.getAllergyDefFromMedication(MedicationCur.MedicationNum);
        if (alD != null)
        {
            _patNameAllergies = Allergies.getPatNamesForAllergy(alD.AllergyDefNum);
            comboPatientAllergy.Items.Clear();
            comboPatientAllergy.Items.AddRange(_patNameAllergies);
            if (_patNameAllergies.Length > 0)
            {
                comboPatientAllergy.SelectedIndex = 0;
            }
             
        }
         
        textRxNormDesc.Text = RxNorms.GetDescByRxCui(MedicationCur.RxCui.ToString());
    }

    private void textMedName_TextChanged(Object sender, System.EventArgs e) throws Exception {
        //this causes immediate display update with each keypress
        if (MedicationCur.MedicationNum == MedicationCur.GenericNum)
        {
            textGenericName.Text = textMedName.Text;
        }
         
    }

    private void butRxNorm_Click(Object sender, EventArgs e) throws Exception {
        FormRxNorms FormRN = new FormRxNorms();
        FormRN.IsSelectionMode = true;
        FormRN.ShowDialog();
        if (FormRN.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        MedicationCur.RxCui = PIn.Long(FormRN.SelectedRxNorm.RxCui);
        textRxNormDesc.Text = RxNorms.GetDescByRxCui(MedicationCur.RxCui.ToString());
        if (IsNew)
        {
            textMedName.Text = RxNorms.GetDescByRxCui(MedicationCur.RxCui.ToString());
        }
         
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!IsNew)
        {
            //Only ask user if they want to delete if not new.
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Delete this medication?"))
            {
                return ;
            }
             
        }
         
        try
        {
            Medications.delete(MedicationCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //generic num already handled
        MedicationCur.MedName = textMedName.Text;
        if (StringSupport.equals(MedicationCur.MedName, ""))
        {
            MsgBox.show(this,"Not allowed to save a medication without a Drug Name.");
            return ;
        }
         
        if (MedicationCur.MedicationNum == MedicationCur.GenericNum)
        {
            MedicationCur.Notes = textNotes.Text;
        }
        else
        {
            MedicationCur.Notes = "";
        } 
        //MedicationCur has its RxCui set when the butRxNormSelect button is pressed.
        Medications.update(MedicationCur);
        MedicationPats.updateRxCuiForMedication(MedicationCur.MedicationNum,MedicationCur.RxCui);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formMedicationEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
            return ;
         
        if (IsNew)
        {
            try
            {
                Medications.delete(MedicationCur);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"The medication failed to delete due to existing dependencies.");
            }
        
        }
         
    }

}


