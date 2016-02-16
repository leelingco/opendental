//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.ClaimPaySplit;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDental.FormInsPayFix;

public class FormInsPayFix  extends Form 
{

    public FormInsPayFix() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void butRun_Click(Object sender, EventArgs e) throws Exception {
        List<ClaimPaySplit> splits = Claims.getInsPayNotAttachedForFixTool();
        if (splits.Count == 0)
        {
            MsgBox.show(this,"There are currently no insurance payments that are not attached to an insurance check.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        for (int i = 0;i < splits.Count;i++)
        {
            Claim claim = Claims.GetClaim(splits[i].ClaimNum);
            ClaimPayment cp = new ClaimPayment();
            cp.CheckDate = claim.DateService;
            cp.CheckAmt = splits[i].InsPayAmt;
            cp.ClinicNum = claim.ClinicNum;
            cp.CarrierName = splits[i].Carrier;
            ClaimPayments.insert(cp);
            List<ClaimProc> claimP = ClaimProcs.RefreshForClaim(splits[i].ClaimNum);
            for (int j = 0;j < claimP.Count;j++)
            {
                claimP[j].DateCP = claim.DateService;
                claimP[j].ClaimPaymentNum = cp.ClaimPaymentNum;
                ClaimProcs.Update(claimP[j]);
            }
        }
        Cursor = Cursors.Default;
        MessageBox.Show(Lan.g(this,"Insurance checks created: ") + splits.Count);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsPayFix.class);
        this.textBoxDescript = new System.Windows.Forms.TextBox();
        this.butRun = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // textBoxDescript
        //
        this.textBoxDescript.BackColor = System.Drawing.SystemColors.Control;
        this.textBoxDescript.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBoxDescript.Location = new System.Drawing.Point(12, 12);
        this.textBoxDescript.Multiline = true;
        this.textBoxDescript.Name = "textBoxDescript";
        this.textBoxDescript.Size = new System.Drawing.Size(399, 72);
        this.textBoxDescript.TabIndex = 4;
        this.textBoxDescript.Text = resources.GetString("textBoxDescript.Text");
        //
        // butRun
        //
        this.butRun.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRun.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butRun.setAutosize(true);
        this.butRun.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRun.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRun.setCornerRadius(4F);
        this.butRun.Location = new System.Drawing.Point(240, 146);
        this.butRun.Name = "butRun";
        this.butRun.Size = new System.Drawing.Size(75, 24);
        this.butRun.TabIndex = 3;
        this.butRun.Text = "&Run";
        this.butRun.Click += new System.EventHandler(this.butRun_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(336, 146);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Cancel";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormInsPayFix
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(436, 197);
        this.Controls.Add(this.textBoxDescript);
        this.Controls.Add(this.butRun);
        this.Controls.Add(this.butClose);
        this.Name = "FormInsPayFix";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Payment Fix";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butRun;
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textBoxDescript = new System.Windows.Forms.TextBox();
}


