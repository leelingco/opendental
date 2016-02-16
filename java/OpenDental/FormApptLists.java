//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:39 PM
//

package OpenDental;

import OpenDental.ApptListSelection;
import OpenDental.FormApptLists;
import OpenDental.Lan;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptLists  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRecall;
    private OpenDental.UI.Button butConfirm;
    private OpenDental.UI.Button butPlanned;
    private OpenDental.UI.Button butUnsched;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butASAP;
    private Label label4 = new Label();
    /**
    * After this window closes, if dialog result is OK, this will contain which list was selected.
    */
    public ApptListSelection SelectionResult = ApptListSelection.Recall;
    /**
    * 
    */
    public FormApptLists() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptLists.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.butASAP = new OpenDental.UI.Button();
        this.butUnsched = new OpenDental.UI.Button();
        this.butPlanned = new OpenDental.UI.Button();
        this.butConfirm = new OpenDental.UI.Button();
        this.butRecall = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(130, 83);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(458, 34);
        this.label1.TabIndex = 2;
        this.label1.Text = "A list of due dates for patients who have previously been in for an exam or clean" + "ing";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(130, 142);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(458, 44);
        this.label2.TabIndex = 3;
        this.label2.Text = "A list of scheduled appointments.  These patients need to be reminded about their" + " appointments.";
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(130, 204);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(458, 47);
        this.label3.TabIndex = 4;
        this.label3.Text = "Planned appointments are created in the Chart module.  Every patient should have " + "one or be marked done.  This list is work that has been planned but never schedu" + "led.";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(130, 266);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(458, 47);
        this.label5.TabIndex = 6;
        this.label5.Text = "A short list of appointments that need to be followed up on.   Keep this list sho" + "rt by deleting any that don\'t get scheduled quickly.  You would then track them " + " using the Planned Appointment Tracker";
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(27, 19);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(519, 31);
        this.label6.TabIndex = 7;
        this.label6.Text = "These lists may be used for calling patients, sending postcards, running reports," + " etc..  Make sure to make good Comm Log entries for everything.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(130, 328);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(458, 47);
        this.label4.TabIndex = 12;
        this.label4.Text = "The short call list.  A list of appointments where the patient is available on sh" + "ort notice.  To show on this list, the status of the appointment needs to be ASA" + "P instead of Scheduled.";
        //
        // butASAP
        //
        this.butASAP.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butASAP.setAutosize(true);
        this.butASAP.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butASAP.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butASAP.setCornerRadius(4F);
        this.butASAP.Location = new System.Drawing.Point(28, 326);
        this.butASAP.Name = "butASAP";
        this.butASAP.Size = new System.Drawing.Size(100, 26);
        this.butASAP.TabIndex = 13;
        this.butASAP.Text = "ASAP";
        this.butASAP.Click += new System.EventHandler(this.butASAP_Click);
        //
        // butUnsched
        //
        this.butUnsched.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUnsched.setAutosize(true);
        this.butUnsched.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUnsched.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUnsched.setCornerRadius(4F);
        this.butUnsched.Location = new System.Drawing.Point(28, 264);
        this.butUnsched.Name = "butUnsched";
        this.butUnsched.Size = new System.Drawing.Size(100, 26);
        this.butUnsched.TabIndex = 11;
        this.butUnsched.Text = "Unscheduled";
        this.butUnsched.Click += new System.EventHandler(this.butUnsched_Click);
        //
        // butPlanned
        //
        this.butPlanned.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPlanned.setAutosize(true);
        this.butPlanned.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPlanned.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPlanned.setCornerRadius(4F);
        this.butPlanned.Location = new System.Drawing.Point(28, 202);
        this.butPlanned.Name = "butPlanned";
        this.butPlanned.Size = new System.Drawing.Size(100, 26);
        this.butPlanned.TabIndex = 10;
        this.butPlanned.Text = "Planned Tracker";
        this.butPlanned.Click += new System.EventHandler(this.butPlanned_Click);
        //
        // butConfirm
        //
        this.butConfirm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butConfirm.setAutosize(true);
        this.butConfirm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butConfirm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butConfirm.setCornerRadius(4F);
        this.butConfirm.Location = new System.Drawing.Point(28, 140);
        this.butConfirm.Name = "butConfirm";
        this.butConfirm.Size = new System.Drawing.Size(100, 26);
        this.butConfirm.TabIndex = 9;
        this.butConfirm.Text = "Confirmations";
        this.butConfirm.Click += new System.EventHandler(this.butConfirm_Click);
        //
        // butRecall
        //
        this.butRecall.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRecall.setAutosize(true);
        this.butRecall.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRecall.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRecall.setCornerRadius(4F);
        this.butRecall.Location = new System.Drawing.Point(28, 78);
        this.butRecall.Name = "butRecall";
        this.butRecall.Size = new System.Drawing.Size(100, 26);
        this.butRecall.TabIndex = 8;
        this.butRecall.Text = "Recall";
        this.butRecall.Click += new System.EventHandler(this.butRecall_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(561, 404);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormApptLists
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(666, 455);
        this.Controls.Add(this.butASAP);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butUnsched);
        this.Controls.Add(this.butPlanned);
        this.Controls.Add(this.butConfirm);
        this.Controls.Add(this.butRecall);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptLists";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointment Lists";
        this.Load += new System.EventHandler(this.FormApptLists_Load);
        this.ResumeLayout(false);
    }

    private void formApptLists_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void butRecall_Click(Object sender, System.EventArgs e) throws Exception {
        SelectionResult = ApptListSelection.Recall;
        DialogResult = DialogResult.OK;
    }

    private void butConfirm_Click(Object sender, System.EventArgs e) throws Exception {
        SelectionResult = ApptListSelection.Confirm;
        DialogResult = DialogResult.OK;
    }

    private void butPlanned_Click(Object sender, System.EventArgs e) throws Exception {
        SelectionResult = ApptListSelection.Planned;
        DialogResult = DialogResult.OK;
    }

    private void butUnsched_Click(Object sender, System.EventArgs e) throws Exception {
        SelectionResult = ApptListSelection.Unsched;
        DialogResult = DialogResult.OK;
    }

    private void butASAP_Click(Object sender, EventArgs e) throws Exception {
        SelectionResult = ApptListSelection.ASAP;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


