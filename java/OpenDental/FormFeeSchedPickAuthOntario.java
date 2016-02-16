//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDental.FormFeeSchedPickAuthOntario;

public class FormFeeSchedPickAuthOntario  extends Form 
{

    public String getODAMemberNumber() throws Exception {
        return textODAMemberNumber.Text;
    }

    public String getODAMemberPassword() throws Exception {
        return textODAMemberPassword.Text;
    }

    public FormFeeSchedPickAuthOntario() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formFeeSchedPickAuthOntario_Load(Object sender, EventArgs e) throws Exception {
        textODAMemberNumber.Text = PrefC.getString(PrefName.CanadaODAMemberNumber);
        textODAMemberPassword.Text = PrefC.getString(PrefName.CanadaODAMemberPass);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textODAMemberNumber.Text, ""))
        {
            MsgBox.show(this,"ODA Member Number cannot be blank.");
            return ;
        }
         
        if (StringSupport.equals(textODAMemberPassword.Text, ""))
        {
            MsgBox.show(this,"ODA Member Password cannot be blank.");
            return ;
        }
         
        Prefs.UpdateString(PrefName.CanadaODAMemberNumber, textODAMemberNumber.Text);
        Prefs.UpdateString(PrefName.CanadaODAMemberPass, textODAMemberPassword.Text);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormFeeSchedPickAuthOntario.class);
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textODAMemberNumber = new System.Windows.Forms.TextBox();
        this.textODAMemberPassword = new System.Windows.Forms.TextBox();
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
        this.butOK.Location = new System.Drawing.Point(146, 83);
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
        this.butCancel.Location = new System.Drawing.Point(227, 83);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(1, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(140, 16);
        this.label1.TabIndex = 4;
        this.label1.Text = "ODA Member Number";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textODAMemberNumber
        //
        this.textODAMemberNumber.Location = new System.Drawing.Point(141, 17);
        this.textODAMemberNumber.Name = "textODAMemberNumber";
        this.textODAMemberNumber.Size = new System.Drawing.Size(161, 20);
        this.textODAMemberNumber.TabIndex = 5;
        //
        // textODAMemberPassword
        //
        this.textODAMemberPassword.Location = new System.Drawing.Point(141, 43);
        this.textODAMemberPassword.Name = "textODAMemberPassword";
        this.textODAMemberPassword.Size = new System.Drawing.Size(161, 20);
        this.textODAMemberPassword.TabIndex = 7;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(1, 45);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(140, 16);
        this.label2.TabIndex = 6;
        this.label2.Text = "ODA Member Password";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // FormFeeSchedPickAuthOntario
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(327, 124);
        this.Controls.Add(this.textODAMemberPassword);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textODAMemberNumber);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormFeeSchedPickAuthOntario";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Fee Schedule Authorization for Ontario";
        this.Load += new System.EventHandler(this.FormFeeSchedPickAuthOntario_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textODAMemberNumber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textODAMemberPassword = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
}


