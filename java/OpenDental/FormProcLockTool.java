//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

public class FormProcLockTool  extends Form 
{

    public FormProcLockTool() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formProcLockTool_Load(Object sender, EventArgs e) throws Exception {
        textDate1.Text = DateTime.Today.AddDays(-3).ToShortDateString();
        textDate2.Text = DateTime.Today.AddDays(-1).ToShortDateString();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        if (!StringSupport.equals(textDate1.errorProvider1.GetError(textDate1), "") || !StringSupport.equals(textDate2.errorProvider1.GetError(textDate2), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        DateTime date1 = PIn.Date(textDate1.Text);
        DateTime date2 = PIn.Date(textDate2.Text);
        if (date1 > date2)
        {
            MsgBox.show(this,"Date 1 cannot be greater than Date 2.");
            return ;
        }
         
        if (date1.AddDays(7) < date2)
        {
            if (!Security.isAuthorized(Permissions.SecurityAdmin,true))
            {
                MsgBox.show(this,"Admin permission is required for date spans greater than 7 days.");
                return ;
            }
             
        }
         
        Procedures.lock(date1,date2);
        if (date1.AddDays(7) < date2)
        {
            SecurityLogs.MakeLogEntry(Permissions.SecurityAdmin, 0, "Proc Lock Tool " + date1.ToShortDateString() + " - " + date2.ToShortDateString());
        }
        else
        {
            SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Proc Lock Tool " + date1.ToShortDateString() + " - " + date2.ToShortDateString());
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
        this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
        this.textDate1 = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
        this.textDate2 = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
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
        this.butOK.Location = new System.Drawing.Point(156, 134);
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
        this.butCancel.Location = new System.Drawing.Point(247, 134);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textDate1
        //
        this.textDate1.Location = new System.Drawing.Point(25, 72);
        this.textDate1.Name = "textDate1";
        this.textDate1.Size = new System.Drawing.Size(80, 20);
        this.textDate1.TabIndex = 5;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(22, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(154, 32);
        this.label1.TabIndex = 4;
        this.label1.Text = "Lock all procedures completed between these two dates";
        //
        // textDate2
        //
        this.textDate2.Location = new System.Drawing.Point(174, 72);
        this.textDate2.Name = "textDate2";
        this.textDate2.Size = new System.Drawing.Size(80, 20);
        this.textDate2.TabIndex = 7;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(106, 72);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(69, 19);
        this.label2.TabIndex = 6;
        this.label2.Text = "through";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // FormProcLockTool
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(334, 170);
        this.Controls.Add(this.textDate2);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textDate1);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormProcLockTool";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Lock Tool";
        this.Load += new System.EventHandler(this.FormProcLockTool_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.BackgroundWorker backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
    private OpenDental.ValidDate textDate1;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDate2;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


