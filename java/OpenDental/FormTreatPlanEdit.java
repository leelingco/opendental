//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatientSelect;
import OpenDental.FormTreatPlanEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcTPs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.TreatPlan;
import OpenDentBusiness.TreatPlans;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTreatPlanEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateTP;
    private System.Windows.Forms.TextBox textHeading = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private TreatPlan PlanCur;
    private OpenDental.UI.Button butClearResponsParty;
    private OpenDental.UI.Button butPickResponsParty;
    private TextBox textResponsParty = new TextBox();
    private Label labelResponsParty = new Label();
    private OpenDental.UI.Button butDelete;
    /**
    * 
    */
    public FormTreatPlanEdit(TreatPlan planCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        PlanCur = planCur.copy();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTreatPlanEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textDateTP = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
        this.textHeading = new System.Windows.Forms.TextBox();
        this.textNote = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.butClearResponsParty = new OpenDental.UI.Button();
        this.butPickResponsParty = new OpenDental.UI.Button();
        this.textResponsParty = new System.Windows.Forms.TextBox();
        this.labelResponsParty = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(549, 366);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(549, 328);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textDateTP
        //
        this.textDateTP.Location = new System.Drawing.Point(136, 24);
        this.textDateTP.Name = "textDateTP";
        this.textDateTP.Size = new System.Drawing.Size(85, 20);
        this.textDateTP.TabIndex = 3;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(45, 26);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(89, 16);
        this.label1.TabIndex = 2;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textHeading
        //
        this.textHeading.Location = new System.Drawing.Point(136, 59);
        this.textHeading.Name = "textHeading";
        this.textHeading.Size = new System.Drawing.Size(489, 20);
        this.textHeading.TabIndex = 4;
        //
        // textNote
        //
        this.textNote.AcceptsReturn = true;
        this.textNote.Location = new System.Drawing.Point(136, 94);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.Size = new System.Drawing.Size(489, 181);
        this.textNote.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(45, 61);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(89, 16);
        this.label2.TabIndex = 6;
        this.label2.Text = "Heading";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(45, 96);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(89, 16);
        this.label3.TabIndex = 7;
        this.label3.Text = "Note";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
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
        this.butDelete.Location = new System.Drawing.Point(103, 366);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(86, 24);
        this.butDelete.TabIndex = 8;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butClearResponsParty
        //
        this.butClearResponsParty.setAdjustImageLocation(new System.Drawing.Point(1, 1));
        this.butClearResponsParty.setAutosize(true);
        this.butClearResponsParty.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearResponsParty.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearResponsParty.setCornerRadius(4F);
        this.butClearResponsParty.Image = Resources.getdeleteX();
        this.butClearResponsParty.Location = new System.Drawing.Point(385, 288);
        this.butClearResponsParty.Name = "butClearResponsParty";
        this.butClearResponsParty.Size = new System.Drawing.Size(25, 23);
        this.butClearResponsParty.TabIndex = 71;
        this.butClearResponsParty.TabStop = false;
        this.butClearResponsParty.Click += new System.EventHandler(this.butClearResponsParty_Click);
        //
        // butPickResponsParty
        //
        this.butPickResponsParty.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickResponsParty.setAutosize(true);
        this.butPickResponsParty.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickResponsParty.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickResponsParty.setCornerRadius(4F);
        this.butPickResponsParty.Location = new System.Drawing.Point(337, 288);
        this.butPickResponsParty.Name = "butPickResponsParty";
        this.butPickResponsParty.Size = new System.Drawing.Size(48, 23);
        this.butPickResponsParty.TabIndex = 70;
        this.butPickResponsParty.TabStop = false;
        this.butPickResponsParty.Text = "Pick";
        this.butPickResponsParty.Click += new System.EventHandler(this.butPickResponsParty_Click);
        //
        // textResponsParty
        //
        this.textResponsParty.AcceptsReturn = true;
        this.textResponsParty.Location = new System.Drawing.Point(137, 290);
        this.textResponsParty.Name = "textResponsParty";
        this.textResponsParty.ReadOnly = true;
        this.textResponsParty.Size = new System.Drawing.Size(198, 20);
        this.textResponsParty.TabIndex = 69;
        //
        // labelResponsParty
        //
        this.labelResponsParty.Location = new System.Drawing.Point(-3, 290);
        this.labelResponsParty.Name = "labelResponsParty";
        this.labelResponsParty.Size = new System.Drawing.Size(139, 17);
        this.labelResponsParty.TabIndex = 68;
        this.labelResponsParty.Text = "Responsible Party";
        this.labelResponsParty.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormTreatPlanEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(676, 420);
        this.Controls.Add(this.butClearResponsParty);
        this.Controls.Add(this.butPickResponsParty);
        this.Controls.Add(this.textResponsParty);
        this.Controls.Add(this.labelResponsParty);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.textHeading);
        this.Controls.Add(this.textDateTP);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTreatPlanEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Treatment Plan";
        this.Load += new System.EventHandler(this.FormTreatPlanEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formTreatPlanEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //this window never comes up for new TP.  Always saved ahead of time.
        if (!Security.isAuthorized(Permissions.TreatPlanEdit,PlanCur.DateTP))
        {
            butOK.Enabled = false;
            butDelete.Enabled = false;
            butPickResponsParty.Enabled = false;
            butClearResponsParty.Enabled = false;
        }
         
        textDateTP.Text = PlanCur.DateTP.ToShortDateString();
        textHeading.Text = PlanCur.Heading;
        textNote.Text = PlanCur.Note;
        if (PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            labelResponsParty.Visible = false;
            textResponsParty.Visible = false;
            butPickResponsParty.Visible = false;
            butClearResponsParty.Visible = false;
        }
         
        if (PlanCur.ResponsParty != 0)
        {
            textResponsParty.Text = Patients.getLim(PlanCur.ResponsParty).getNameLF();
        }
         
    }

    private void butPickResponsParty_Click(Object sender, EventArgs e) throws Exception {
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.SelectionModeOnly = true;
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PlanCur.ResponsParty = FormPS.SelectedPatNum;
        textResponsParty.Text = Patients.getLim(PlanCur.ResponsParty).getNameLF();
    }

    private void butClearResponsParty_Click(Object sender, EventArgs e) throws Exception {
        PlanCur.ResponsParty = 0;
        textResponsParty.Text = "";
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        //if(IsNew){
        //	DialogResult=DialogResult.Cancel;
        //	return;
        //}
        ProcTPs.deleteForTP(PlanCur.TreatPlanNum);
        try
        {
            TreatPlans.delete(PlanCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        TreatPlans.delete(PlanCur);
        SecurityLogs.makeLogEntry(Permissions.TreatPlanEdit,PlanCur.PatNum,"Delete TP: " + PlanCur.DateTP.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateTP.errorProvider1.GetError(textDateTP), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (StringSupport.equals(textDateTP.Text, ""))
        {
            MsgBox.show(this,"Please enter a date first.");
            return ;
        }
         
        PlanCur.DateTP = PIn.Date(textDateTP.Text);
        PlanCur.Heading = textHeading.Text;
        PlanCur.Note = textNote.Text;
        TreatPlans.update(PlanCur);
        SecurityLogs.makeLogEntry(Permissions.TreatPlanEdit,PlanCur.PatNum,"Edit TP: " + PlanCur.DateTP.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


