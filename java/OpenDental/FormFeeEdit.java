//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:09 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormFeeEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.Fee;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.SecurityLogs;

/**
* 
*/
public class FormFeeEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDouble textFee;
    private System.Windows.Forms.CheckBox checkDefFee = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkDefCov = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public Fee FeeCur;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormFeeEdit() throws Exception {
        //(Fee feeCur){
        initializeComponent();
        // Required for Windows Form Designer support
        //FeeCur=feeCur;
        //checkDefFee.Checked=FeeCur.UseDefaultFee;
        //checkDefCov.Checked=FeeCur.UseDefaultCov;
        //exclude:
        Lan.F(this, new System.Windows.Forms.Control[]{ this.checkDefCov, this.checkDefFee });
    }

    /**
    * 
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeeEdit.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textFee = new OpenDental.ValidDouble();
        this.checkDefFee = new System.Windows.Forms.CheckBox();
        this.checkDefCov = new System.Windows.Forms.CheckBox();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(2, 26);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(72, 16);
        this.label1.TabIndex = 0;
        this.label1.Text = "Fee";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textFee
        //
        this.textFee.Location = new System.Drawing.Point(78, 22);
        this.textFee.Name = "textFee";
        this.textFee.Size = new System.Drawing.Size(72, 20);
        this.textFee.TabIndex = 0;
        //
        // checkDefFee
        //
        this.checkDefFee.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkDefFee.Location = new System.Drawing.Point(54, 54);
        this.checkDefFee.Name = "checkDefFee";
        this.checkDefFee.Size = new System.Drawing.Size(172, 24);
        this.checkDefFee.TabIndex = 1;
        this.checkDefFee.Text = "Use Default Fee (not visible)";
        this.checkDefFee.Visible = false;
        //
        // checkDefCov
        //
        this.checkDefCov.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkDefCov.Location = new System.Drawing.Point(54, 84);
        this.checkDefCov.Name = "checkDefCov";
        this.checkDefCov.Size = new System.Drawing.Size(208, 24);
        this.checkDefCov.TabIndex = 2;
        this.checkDefCov.Text = "Use Default Coverage (not visible)";
        this.checkDefCov.Visible = false;
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(16, 132);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(512, 208);
        this.textBox1.TabIndex = 29;
        this.textBox1.Text = resources.GetString("textBox1.Text");
        this.textBox1.Visible = false;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(282, 54);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(282, 92);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormFeeEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(374, 132);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.textFee);
        this.Controls.Add(this.checkDefCov);
        this.Controls.Add(this.checkDefFee);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormFeeEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Fee";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormFeeEdit_Closing);
        this.Load += new System.EventHandler(this.FormFeeEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formFeeEdit_Load(Object sender, System.EventArgs e) throws Exception {
        Location = new Point(Location.X - 190, Location.Y - 20);
        textFee.Text = FeeCur.Amount.ToString("F");
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textFee.errorProvider1.GetError(textFee), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry error first."));
            return ;
        }
         
        if (StringSupport.equals(textFee.Text, ""))
        {
            Fees.delete(FeeCur);
        }
        else
        {
            FeeCur.Amount = PIn.Double(textFee.Text);
            //Fee object always created and inserted externally first
            Fees.update(FeeCur);
        } 
        SecurityLogs.MakeLogEntry(Permissions.ProcFeeEdit, 0, Lan.g(this,"Procedure") + ": " + ProcedureCodes.getStringProcCode(FeeCur.CodeNum) + ", " + Lan.g(this,"Fee: ") + "" + FeeCur.Amount.ToString("c") + ", " + Lan.g(this,"Fee Schedule") + ": " + FeeScheds.getDescription(FeeCur.FeeSched) + ". " + Lan.g(this,"Manual edit in Edit Fee window."), FeeCur.CodeNum);
        //==Michael - 2/15/2013 - Could be from FormProcCodeEdit, FormClaimProcEdit, or FormProcCodes.
        //FeeCur.UseDefaultCov=checkDefCov.Checked;
        //FeeCur.UseDefaultFee=checkDefFee.Checked;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formFeeEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            Fees.delete(FeeCur);
        }
         
    }

}


