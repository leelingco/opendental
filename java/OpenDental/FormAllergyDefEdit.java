//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormMedications;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Medications;
import OpenDentBusiness.SnomedAllergy;
import OpenDental.FormAllergyDefEdit;
import OpenDental.Properties.Resources;

public class FormAllergyDefEdit  extends Form 
{

    public AllergyDef AllergyDefCur;
    public FormAllergyDefEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formAllergyEdit_Load(Object sender, EventArgs e) throws Exception {
        if (!AllergyDefCur.getIsNew())
        {
            textDescription.Text = AllergyDefCur.Description;
            checkHidden.Checked = AllergyDefCur.IsHidden;
        }
         
        for (int i = 0;i < Enum.GetNames(SnomedAllergy.class).Length;i++)
        {
            comboSnomedAllergyType.Items.Add(Enum.GetNames(SnomedAllergy.class)[i]);
        }
        comboSnomedAllergyType.SelectedIndex = ((Enum)AllergyDefCur.SnomedType).ordinal();
        textMedication.Text = Medications.getDescription(AllergyDefCur.MedicationNum);
        textUnii.Text = AllergyDefCur.UniiCode;
    }

    private void butUniiToSelect_Click(Object sender, EventArgs e) throws Exception {
    }

    //FormSnomeds formS=new FormSnomeds();
    //formS.IsSelectionMode=true;
    //if(formS.ShowDialog()==DialogResult.OK) {
    //	snomedAllergicTo=formS.SelectedSnomed;
    //	//textSnomedAllergicTo.Text=snomedAllergicTo.Description;
    //}
    //TODO: Implement similar code for Unii
    private void butMedicationSelect_Click(Object sender, EventArgs e) throws Exception {
        FormMedications FormM = new FormMedications();
        FormM.IsSelectionMode = true;
        FormM.ShowDialog();
        if (FormM.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        AllergyDefCur.MedicationNum = FormM.SelectedMedicationNum;
        textMedication.Text = Medications.getDescription(AllergyDefCur.MedicationNum);
    }

    private void butNoneUniiTo_Click(Object sender, EventArgs e) throws Exception {
    }

    //TODO: Implement this
    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        AllergyDefCur.MedicationNum = 0;
        textMedication.Text = "";
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textDescription.Text.Trim(), ""))
        {
            MsgBox.show(this,"Description cannot be blank.");
            return ;
        }
         
        if (!StringSupport.equals(textUnii.Text, "") && !StringSupport.equals(textMedication.Text, ""))
        {
            MsgBox.show(this,"Only one code is allowed per allergy def.");
            return ;
        }
         
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder notAllowed = new StringBuilder();
        for (int i = 0;i < textUnii.Text.Length;i++)
        {
            if (validChars.IndexOf(textUnii.Text[i]) == -1)
            {
                //Not found.
                notAllowed.Append(textUnii.Text[i]);
            }
             
        }
        if (!StringSupport.equals(notAllowed.ToString(), ""))
        {
            MessageBox.Show(Lan.g(this,"UNII code has invalid characters: " + notAllowed));
            return ;
        }
         
        if (textUnii.Text.Length != 10)
        {
            MsgBox.show(this,"UNII code must be 10 characters in length.");
            return ;
        }
         
        AllergyDefCur.Description = textDescription.Text;
        AllergyDefCur.IsHidden = checkHidden.Checked;
        AllergyDefCur.SnomedType = (SnomedAllergy)comboSnomedAllergyType.SelectedIndex;
        AllergyDefCur.UniiCode = textUnii.Text;
        //if(snomedAllergicTo!=null) { //TODO: Do UNII check once the table is added
        //	AllergyDefCur.SnomedAllergyTo=snomedAllergicTo.SnomedCode;
        //}
        if (AllergyDefCur.getIsNew())
        {
            AllergyDefs.insert(AllergyDefCur);
        }
        else
        {
            AllergyDefs.update(AllergyDefCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!AllergyDefCur.getIsNew())
        {
            if (!AllergyDefs.defIsInUse(AllergyDefCur.AllergyDefNum))
            {
                AllergyDefs.delete(AllergyDefCur.AllergyDefNum);
            }
            else
            {
                MsgBox.show(this,"Cannot delete allergies in use.");
                return ;
            } 
        }
         
        DialogResult = DialogResult.Cancel;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAllergyDefEdit.class);
        this.labelDescription = new System.Windows.Forms.Label();
        this.textDescription = new System.Windows.Forms.TextBox();
        this.checkHidden = new System.Windows.Forms.CheckBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboSnomedAllergyType = new System.Windows.Forms.ComboBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textMedication = new System.Windows.Forms.TextBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textUnii = new System.Windows.Forms.TextBox();
        this.butNoneUnii = new OpenDental.UI.Button();
        this.butNone = new OpenDental.UI.Button();
        this.butUniiSelect = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butMedicationSelect = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // labelDescription
        //
        this.labelDescription.Location = new System.Drawing.Point(18, 27);
        this.labelDescription.Name = "labelDescription";
        this.labelDescription.Size = new System.Drawing.Size(130, 20);
        this.labelDescription.TabIndex = 6;
        this.labelDescription.Text = "Description";
        this.labelDescription.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDescription
        //
        this.textDescription.Location = new System.Drawing.Point(151, 27);
        this.textDescription.Name = "textDescription";
        this.textDescription.Size = new System.Drawing.Size(276, 20);
        this.textDescription.TabIndex = 7;
        //
        // checkHidden
        //
        this.checkHidden.Location = new System.Drawing.Point(33, 207);
        this.checkHidden.Name = "checkHidden";
        this.checkHidden.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkHidden.Size = new System.Drawing.Size(132, 24);
        this.checkHidden.TabIndex = 8;
        this.checkHidden.Text = "Is Hidden";
        this.checkHidden.UseVisualStyleBackColor = true;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 49);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(109, 20);
        this.label2.TabIndex = 20;
        this.label2.Text = "Medication";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboSnomedAllergyType
        //
        this.comboSnomedAllergyType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboSnomedAllergyType.FormattingEnabled = true;
        this.comboSnomedAllergyType.Location = new System.Drawing.Point(133, 22);
        this.comboSnomedAllergyType.Name = "comboSnomedAllergyType";
        this.comboSnomedAllergyType.Size = new System.Drawing.Size(276, 21);
        this.comboSnomedAllergyType.TabIndex = 19;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 21);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(123, 20);
        this.label3.TabIndex = 18;
        this.label3.Text = "Allergy Type";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMedication
        //
        this.textMedication.Location = new System.Drawing.Point(118, 49);
        this.textMedication.Name = "textMedication";
        this.textMedication.ReadOnly = true;
        this.textMedication.Size = new System.Drawing.Size(276, 20);
        this.textMedication.TabIndex = 7;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.groupBox2);
        this.groupBox1.Controls.Add(this.comboSnomedAllergyType);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(18, 57);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(518, 144);
        this.groupBox1.TabIndex = 21;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Only used in EHR for CCDs.  Most offices can ignore this section";
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textUnii);
        this.groupBox2.Controls.Add(this.butNoneUnii);
        this.groupBox2.Controls.Add(this.butNone);
        this.groupBox2.Controls.Add(this.butUniiSelect);
        this.groupBox2.Controls.Add(this.textMedication);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.butMedicationSelect);
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Location = new System.Drawing.Point(15, 55);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(493, 83);
        this.groupBox2.TabIndex = 26;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Allergen (only one)";
        //
        // textUnii
        //
        this.textUnii.Location = new System.Drawing.Point(118, 25);
        this.textUnii.Name = "textUnii";
        this.textUnii.Size = new System.Drawing.Size(276, 20);
        this.textUnii.TabIndex = 21;
        //
        // butNoneUnii
        //
        this.butNoneUnii.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNoneUnii.setAutosize(true);
        this.butNoneUnii.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNoneUnii.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNoneUnii.setCornerRadius(4F);
        this.butNoneUnii.Enabled = false;
        this.butNoneUnii.Location = new System.Drawing.Point(423, 24);
        this.butNoneUnii.Name = "butNoneUnii";
        this.butNoneUnii.Size = new System.Drawing.Size(51, 22);
        this.butNoneUnii.TabIndex = 24;
        this.butNoneUnii.Text = "None";
        this.butNoneUnii.Click += new System.EventHandler(this.butNoneUniiTo_Click);
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.Location = new System.Drawing.Point(423, 48);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(51, 22);
        this.butNone.TabIndex = 9;
        this.butNone.Text = "None";
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // butUniiSelect
        //
        this.butUniiSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUniiSelect.setAutosize(true);
        this.butUniiSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUniiSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUniiSelect.setCornerRadius(4F);
        this.butUniiSelect.Enabled = false;
        this.butUniiSelect.Location = new System.Drawing.Point(398, 24);
        this.butUniiSelect.Name = "butUniiSelect";
        this.butUniiSelect.Size = new System.Drawing.Size(22, 22);
        this.butUniiSelect.TabIndex = 23;
        this.butUniiSelect.Text = "...";
        this.butUniiSelect.Click += new System.EventHandler(this.butUniiToSelect_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 25);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(109, 20);
        this.label1.TabIndex = 22;
        this.label1.Text = "UNII";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butMedicationSelect
        //
        this.butMedicationSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMedicationSelect.setAutosize(true);
        this.butMedicationSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMedicationSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMedicationSelect.setCornerRadius(4F);
        this.butMedicationSelect.Location = new System.Drawing.Point(398, 48);
        this.butMedicationSelect.Name = "butMedicationSelect";
        this.butMedicationSelect.Size = new System.Drawing.Size(22, 22);
        this.butMedicationSelect.TabIndex = 3;
        this.butMedicationSelect.Text = "...";
        this.butMedicationSelect.Click += new System.EventHandler(this.butMedicationSelect_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(480, 245);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 9;
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
        this.butOK.Location = new System.Drawing.Point(399, 245);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
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
        this.butDelete.Location = new System.Drawing.Point(15, 245);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 2;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormAllergyDefEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(571, 284);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.checkHidden);
        this.Controls.Add(this.textDescription);
        this.Controls.Add(this.labelDescription);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butDelete);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormAllergyDefEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Allergy Def Edit";
        this.Load += new System.EventHandler(this.FormAllergyEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label labelDescription = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkHidden = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboSnomedAllergyType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMedication = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butMedicationSelect;
    private OpenDental.UI.Button butNone;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textUnii = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butUniiSelect;
    private OpenDental.UI.Button butNoneUnii;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
}


