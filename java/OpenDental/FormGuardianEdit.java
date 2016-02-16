//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormFamilyMemberSelect;
import OpenDental.Lan;
import OpenDentBusiness.Family;
import OpenDentBusiness.Guardian;
import OpenDentBusiness.GuardianRelationship;
import OpenDentBusiness.Guardians;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.Patients;
import OpenDental.FormGuardianEdit;
import OpenDental.Properties.Resources;

public class FormGuardianEdit  extends Form 
{

    private Guardian _guardianCur;
    private Family _fam;
    private List<String> _listRelationshipNames = new List<String>();
    public FormGuardianEdit(Guardian guardianCur, Family fam) throws Exception {
        initializeComponent();
        Lan.F(this);
        _guardianCur = guardianCur;
        _fam = fam;
    }

    private void formGuardianEdit_Load(Object sender, EventArgs e) throws Exception {
        textPatient.Text = _fam.getNameInFamFL(_guardianCur.PatNumChild);
        if (_guardianCur.PatNumGuardian != 0)
        {
            textFamilyMember.Text = _fam.getNameInFamFL(_guardianCur.PatNumGuardian);
        }
         
        Patient patChild = Patients.getPat(_guardianCur.PatNumChild);
        if (_guardianCur.getIsNew())
        {
            if (patChild.Position == PatientPosition.Child)
            {
                //True by default if entering relationship from a child patient, because this is how the old guardian feature worked before we added support for other relationship types.
                checkIsGuardian.Checked = true;
            }
             
        }
        else
        {
            //Existing guardian record.
            checkIsGuardian.Checked = _guardianCur.IsGuardian;
        } 
        _listRelationshipNames = new List<String>(Enum.GetNames(GuardianRelationship.class));
        _listRelationshipNames.Sort();
        for (int i = 0;i < _listRelationshipNames.Count;i++)
        {
            comboRelationship.Items.Add(Lan.g("enumGuardianRelationship", _listRelationshipNames[i]));
            if (_listRelationshipNames[i] == _guardianCur.Relationship.ToString())
            {
                comboRelationship.SelectedIndex = i;
            }
             
        }
    }

    private void butPick_Click(Object sender, EventArgs e) throws Exception {
        FormFamilyMemberSelect FormF = new FormFamilyMemberSelect(_fam);
        FormF.ShowDialog();
        if (FormF.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        _guardianCur.PatNumGuardian = FormF.SelectedPatNum;
        textFamilyMember.Text = _fam.getNameInFamFL(_guardianCur.PatNumGuardian);
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (_guardianCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            Guardians.delete(_guardianCur.GuardianNum);
            DialogResult = DialogResult.OK;
        } 
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //PatNumChild already set
        //PatNumGuardian already set
        _guardianCur.IsGuardian = checkIsGuardian.Checked;
        String relatName = comboRelationship.Items[comboRelationship.SelectedIndex].ToString();
        List<String> listRelationshipNamesRaw = new List<String>(Enum.GetNames(GuardianRelationship.class));
        _guardianCur.Relationship = (GuardianRelationship)listRelationshipNamesRaw.IndexOf(relatName);
        if (_guardianCur.getIsNew())
        {
            Guardians.insert(_guardianCur);
        }
        else
        {
            Guardians.update(_guardianCur);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormGuardianEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textFamilyMember = new System.Windows.Forms.TextBox();
        this.comboRelationship = new System.Windows.Forms.ComboBox();
        this.checkIsGuardian = new System.Windows.Forms.CheckBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.butPick = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(2, 17);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(111, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "Patient";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(113, 16);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(214, 20);
        this.textPatient.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 42);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(111, 16);
        this.label2.TabIndex = 7;
        this.label2.Text = "Family Member";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(2, 86);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(111, 16);
        this.label3.TabIndex = 8;
        this.label3.Text = "Relationship";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFamilyMember
        //
        this.textFamilyMember.Location = new System.Drawing.Point(113, 41);
        this.textFamilyMember.Name = "textFamilyMember";
        this.textFamilyMember.ReadOnly = true;
        this.textFamilyMember.Size = new System.Drawing.Size(214, 20);
        this.textFamilyMember.TabIndex = 10;
        //
        // comboRelationship
        //
        this.comboRelationship.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboRelationship.FormattingEnabled = true;
        this.comboRelationship.Location = new System.Drawing.Point(113, 85);
        this.comboRelationship.Name = "comboRelationship";
        this.comboRelationship.Size = new System.Drawing.Size(214, 21);
        this.comboRelationship.TabIndex = 69;
        //
        // checkIsGuardian
        //
        this.checkIsGuardian.Location = new System.Drawing.Point(2, 64);
        this.checkIsGuardian.Name = "checkIsGuardian";
        this.checkIsGuardian.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.checkIsGuardian.Size = new System.Drawing.Size(125, 18);
        this.checkIsGuardian.TabIndex = 70;
        this.checkIsGuardian.Text = "Guardian";
        this.checkIsGuardian.UseVisualStyleBackColor = true;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(133, 64);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(245, 16);
        this.label4.TabIndex = 71;
        this.label4.Text = "Shows relationship on appointments";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
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
        this.butDelete.Location = new System.Drawing.Point(12, 128);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 68;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butPick
        //
        this.butPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick.setAutosize(true);
        this.butPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick.setCornerRadius(4F);
        this.butPick.Location = new System.Drawing.Point(331, 41);
        this.butPick.Name = "butPick";
        this.butPick.Size = new System.Drawing.Size(27, 20);
        this.butPick.TabIndex = 67;
        this.butPick.TabStop = false;
        this.butPick.Text = "...";
        this.butPick.Click += new System.EventHandler(this.butPick_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(222, 128);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
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
        this.butCancel.Location = new System.Drawing.Point(303, 128);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormGuardianEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(390, 164);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.checkIsGuardian);
        this.Controls.Add(this.comboRelationship);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butPick);
        this.Controls.Add(this.textFamilyMember);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(368, 189);
        this.Name = "FormGuardianEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Family Relationship";
        this.Load += new System.EventHandler(this.FormGuardianEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFamilyMember = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butPick;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.ComboBox comboRelationship = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.CheckBox checkIsGuardian = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
}


