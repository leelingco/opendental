//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.Lan;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

public class FormSheetSetup  extends Form 
{

    private boolean changed = new boolean();
    public FormSheetSetup() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReportSetup_Load(Object sender, EventArgs e) throws Exception {
        checkPatientFormsShowConsent.Checked = PrefC.getBool(PrefName.PatientFormsShowConsent);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (Prefs.UpdateBool(PrefName.PatientFormsShowConsent, checkPatientFormsShowConsent.Checked))
        {
            changed = true;
        }
         
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Prefs);
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.checkPatientFormsShowConsent = new System.Windows.Forms.CheckBox();
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
        this.butOK.Location = new System.Drawing.Point(233, 115);
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
        this.butCancel.Location = new System.Drawing.Point(314, 115);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // checkPatientFormsShowConsent
        //
        this.checkPatientFormsShowConsent.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPatientFormsShowConsent.Location = new System.Drawing.Point(34, 51);
        this.checkPatientFormsShowConsent.Name = "checkPatientFormsShowConsent";
        this.checkPatientFormsShowConsent.Size = new System.Drawing.Size(333, 17);
        this.checkPatientFormsShowConsent.TabIndex = 199;
        this.checkPatientFormsShowConsent.Text = "Patient Forms window  show consent forms";
        //
        // FormSheetSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(401, 151);
        this.Controls.Add(this.checkPatientFormsShowConsent);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormSheetSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patient Form Options";
        this.Load += new System.EventHandler(this.FormReportSetup_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.CheckBox checkPatientFormsShowConsent = new System.Windows.Forms.CheckBox();
}


