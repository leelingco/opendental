//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.Patient;
import OpenDental.FormEhrExportCCD;

public class FormEhrExportCCD  extends Form 
{

    private Patient _patCur;
    private String _ccd = new String();
    public String getCCD() throws Exception {
        return _ccd;
    }

    public FormEhrExportCCD(Patient patCur) throws Exception {
        initializeComponent();
        Lan.F(this);
        _patCur = patCur;
        _ccd = "";
    }

    private void butCheckAll_Click(Object sender, EventArgs e) throws Exception {
        checkAllergy.Checked = true;
        checkEncounter.Checked = true;
        checkFunctionalStatus.Checked = true;
        checkImmunization.Checked = true;
        checkMedication.Checked = true;
        checkPlanOfCare.Checked = true;
        checkProblem.Checked = true;
        checkProcedure.Checked = true;
        checkResult.Checked = true;
        checkReferral.Checked = true;
        checkSocialHistory.Checked = true;
        checkVitalSign.Checked = true;
    }

    private void butCheckNone_Click(Object sender, EventArgs e) throws Exception {
        checkAllergy.Checked = false;
        checkEncounter.Checked = false;
        checkFunctionalStatus.Checked = false;
        checkImmunization.Checked = false;
        checkMedication.Checked = false;
        checkPlanOfCare.Checked = false;
        checkProblem.Checked = false;
        checkProcedure.Checked = false;
        checkResult.Checked = false;
        checkReferral.Checked = false;
        checkSocialHistory.Checked = false;
        checkVitalSign.Checked = false;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        _ccd = EhrCCD.GenerateClinicalSummary(_patCur, checkAllergy.Checked, checkEncounter.Checked, checkFunctionalStatus.Checked, checkImmunization.Checked, checkMedication.Checked, checkPlanOfCare.Checked, checkProblem.Checked, checkProcedure.Checked, checkReferral.Checked, checkResult.Checked, checkSocialHistory.Checked, checkVitalSign.Checked, textInstructions.Text);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEhrExportCCD.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkEncounter = new System.Windows.Forms.CheckBox();
        this.checkFunctionalStatus = new System.Windows.Forms.CheckBox();
        this.checkImmunization = new System.Windows.Forms.CheckBox();
        this.checkMedication = new System.Windows.Forms.CheckBox();
        this.checkAllergy = new System.Windows.Forms.CheckBox();
        this.checkPlanOfCare = new System.Windows.Forms.CheckBox();
        this.checkProblem = new System.Windows.Forms.CheckBox();
        this.checkProcedure = new System.Windows.Forms.CheckBox();
        this.checkResult = new System.Windows.Forms.CheckBox();
        this.checkSocialHistory = new System.Windows.Forms.CheckBox();
        this.checkVitalSign = new System.Windows.Forms.CheckBox();
        this.labelSections = new System.Windows.Forms.Label();
        this.butCheckAll = new OpenDental.UI.Button();
        this.butCheckNone = new OpenDental.UI.Button();
        this.textInstructions = new System.Windows.Forms.TextBox();
        this.labelInstructions = new System.Windows.Forms.Label();
        this.checkReferral = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(197, 360);
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
        this.butCancel.Location = new System.Drawing.Point(278, 360);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkEncounter
        //
        this.checkEncounter.Checked = true;
        this.checkEncounter.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkEncounter.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkEncounter.Location = new System.Drawing.Point(19, 40);
        this.checkEncounter.Name = "checkEncounter";
        this.checkEncounter.Size = new System.Drawing.Size(150, 18);
        this.checkEncounter.TabIndex = 120;
        this.checkEncounter.Text = "Encounter";
        //
        // checkFunctionalStatus
        //
        this.checkFunctionalStatus.Checked = true;
        this.checkFunctionalStatus.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkFunctionalStatus.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkFunctionalStatus.Location = new System.Drawing.Point(19, 64);
        this.checkFunctionalStatus.Name = "checkFunctionalStatus";
        this.checkFunctionalStatus.Size = new System.Drawing.Size(150, 18);
        this.checkFunctionalStatus.TabIndex = 121;
        this.checkFunctionalStatus.Text = "Functional Status";
        //
        // checkImmunization
        //
        this.checkImmunization.Checked = true;
        this.checkImmunization.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkImmunization.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkImmunization.Location = new System.Drawing.Point(19, 88);
        this.checkImmunization.Name = "checkImmunization";
        this.checkImmunization.Size = new System.Drawing.Size(150, 18);
        this.checkImmunization.TabIndex = 122;
        this.checkImmunization.Text = "Immunization";
        //
        // checkMedication
        //
        this.checkMedication.Checked = true;
        this.checkMedication.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkMedication.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkMedication.Location = new System.Drawing.Point(19, 112);
        this.checkMedication.Name = "checkMedication";
        this.checkMedication.Size = new System.Drawing.Size(150, 18);
        this.checkMedication.TabIndex = 123;
        this.checkMedication.Text = "Medication";
        //
        // checkAllergy
        //
        this.checkAllergy.Checked = true;
        this.checkAllergy.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkAllergy.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkAllergy.Location = new System.Drawing.Point(19, 16);
        this.checkAllergy.Name = "checkAllergy";
        this.checkAllergy.Size = new System.Drawing.Size(150, 18);
        this.checkAllergy.TabIndex = 124;
        this.checkAllergy.Text = "Allergy";
        //
        // checkPlanOfCare
        //
        this.checkPlanOfCare.Checked = true;
        this.checkPlanOfCare.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkPlanOfCare.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPlanOfCare.Location = new System.Drawing.Point(19, 136);
        this.checkPlanOfCare.Name = "checkPlanOfCare";
        this.checkPlanOfCare.Size = new System.Drawing.Size(150, 18);
        this.checkPlanOfCare.TabIndex = 125;
        this.checkPlanOfCare.Text = "Plan of Care";
        //
        // checkProblem
        //
        this.checkProblem.Checked = true;
        this.checkProblem.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkProblem.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkProblem.Location = new System.Drawing.Point(175, 16);
        this.checkProblem.Name = "checkProblem";
        this.checkProblem.Size = new System.Drawing.Size(150, 18);
        this.checkProblem.TabIndex = 126;
        this.checkProblem.Text = "Problem";
        //
        // checkProcedure
        //
        this.checkProcedure.Checked = true;
        this.checkProcedure.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkProcedure.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkProcedure.Location = new System.Drawing.Point(175, 40);
        this.checkProcedure.Name = "checkProcedure";
        this.checkProcedure.Size = new System.Drawing.Size(150, 18);
        this.checkProcedure.TabIndex = 127;
        this.checkProcedure.Text = "Procedure";
        //
        // checkResult
        //
        this.checkResult.Checked = true;
        this.checkResult.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkResult.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkResult.Location = new System.Drawing.Point(175, 64);
        this.checkResult.Name = "checkResult";
        this.checkResult.Size = new System.Drawing.Size(150, 18);
        this.checkResult.TabIndex = 129;
        this.checkResult.Text = "Result";
        //
        // checkSocialHistory
        //
        this.checkSocialHistory.Checked = true;
        this.checkSocialHistory.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkSocialHistory.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkSocialHistory.Location = new System.Drawing.Point(175, 112);
        this.checkSocialHistory.Name = "checkSocialHistory";
        this.checkSocialHistory.Size = new System.Drawing.Size(150, 18);
        this.checkSocialHistory.TabIndex = 130;
        this.checkSocialHistory.Text = "Social History";
        //
        // checkVitalSign
        //
        this.checkVitalSign.Checked = true;
        this.checkVitalSign.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkVitalSign.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkVitalSign.Location = new System.Drawing.Point(175, 136);
        this.checkVitalSign.Name = "checkVitalSign";
        this.checkVitalSign.Size = new System.Drawing.Size(150, 18);
        this.checkVitalSign.TabIndex = 131;
        this.checkVitalSign.Text = "Vital Sign";
        //
        // labelSections
        //
        this.labelSections.Location = new System.Drawing.Point(14, 19);
        this.labelSections.Name = "labelSections";
        this.labelSections.Size = new System.Drawing.Size(339, 26);
        this.labelSections.TabIndex = 132;
        this.labelSections.Text = "Choose the sections to include in the Continuity of Care Document.";
        //
        // butCheckAll
        //
        this.butCheckAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheckAll.setAutosize(true);
        this.butCheckAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheckAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheckAll.setCornerRadius(4F);
        this.butCheckAll.Location = new System.Drawing.Point(177, 163);
        this.butCheckAll.Name = "butCheckAll";
        this.butCheckAll.Size = new System.Drawing.Size(61, 24);
        this.butCheckAll.TabIndex = 133;
        this.butCheckAll.Text = "All";
        this.butCheckAll.Click += new System.EventHandler(this.butCheckAll_Click);
        //
        // butCheckNone
        //
        this.butCheckNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCheckNone.setAutosize(true);
        this.butCheckNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCheckNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCheckNone.setCornerRadius(4F);
        this.butCheckNone.Location = new System.Drawing.Point(244, 163);
        this.butCheckNone.Name = "butCheckNone";
        this.butCheckNone.Size = new System.Drawing.Size(60, 24);
        this.butCheckNone.TabIndex = 134;
        this.butCheckNone.Text = "None";
        this.butCheckNone.Click += new System.EventHandler(this.butCheckNone_Click);
        //
        // textInstructions
        //
        this.textInstructions.Location = new System.Drawing.Point(17, 272);
        this.textInstructions.Multiline = true;
        this.textInstructions.Name = "textInstructions";
        this.textInstructions.Size = new System.Drawing.Size(338, 73);
        this.textInstructions.TabIndex = 136;
        //
        // labelInstructions
        //
        this.labelInstructions.Location = new System.Drawing.Point(14, 249);
        this.labelInstructions.Name = "labelInstructions";
        this.labelInstructions.Size = new System.Drawing.Size(275, 20);
        this.labelInstructions.TabIndex = 135;
        this.labelInstructions.Text = "Instructions";
        this.labelInstructions.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // checkReferral
        //
        this.checkReferral.Checked = true;
        this.checkReferral.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkReferral.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkReferral.Location = new System.Drawing.Point(175, 88);
        this.checkReferral.Name = "checkReferral";
        this.checkReferral.Size = new System.Drawing.Size(150, 18);
        this.checkReferral.TabIndex = 137;
        this.checkReferral.Text = "Referral";
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.checkAllergy);
        this.groupBox1.Controls.Add(this.checkReferral);
        this.groupBox1.Controls.Add(this.checkEncounter);
        this.groupBox1.Controls.Add(this.checkFunctionalStatus);
        this.groupBox1.Controls.Add(this.checkImmunization);
        this.groupBox1.Controls.Add(this.butCheckNone);
        this.groupBox1.Controls.Add(this.checkMedication);
        this.groupBox1.Controls.Add(this.butCheckAll);
        this.groupBox1.Controls.Add(this.checkPlanOfCare);
        this.groupBox1.Controls.Add(this.checkProblem);
        this.groupBox1.Controls.Add(this.checkVitalSign);
        this.groupBox1.Controls.Add(this.checkProcedure);
        this.groupBox1.Controls.Add(this.checkSocialHistory);
        this.groupBox1.Controls.Add(this.checkResult);
        this.groupBox1.Location = new System.Drawing.Point(15, 48);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(338, 198);
        this.groupBox1.TabIndex = 138;
        this.groupBox1.TabStop = false;
        //
        // FormEhrExportCCD
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(365, 396);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.textInstructions);
        this.Controls.Add(this.labelInstructions);
        this.Controls.Add(this.labelSections);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MinimumSize = new System.Drawing.Size(100, 100);
        this.Name = "FormEhrExportCCD";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Export CCD";
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.CheckBox checkEncounter = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkFunctionalStatus = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkImmunization = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkMedication = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkAllergy = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkPlanOfCare = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkProblem = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkProcedure = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkResult = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkSocialHistory = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkVitalSign = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label labelSections = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCheckAll;
    private OpenDental.UI.Button butCheckNone;
    private System.Windows.Forms.TextBox textInstructions = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelInstructions = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkReferral = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
}


