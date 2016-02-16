//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:22 PM
//

package OpenDental;

import OpenDental.FormPasswordReset;
import OpenDental.Lan;

/**
* 
*/
public class FormPasswordReset  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.TextBox textMasterPass = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCancel;
    private Label label2 = new Label();
    private String masterHash = new String();
    /**
    * 
    */
    public FormPasswordReset() throws Exception {
        initializeComponent();
        Lan.f(this);
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPasswordReset.class);
        this.textMasterPass = new System.Windows.Forms.TextBox();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // textMasterPass
        //
        this.textMasterPass.Location = new System.Drawing.Point(120, 58);
        this.textMasterPass.MaxLength = 100;
        this.textMasterPass.Name = "textMasterPass";
        this.textMasterPass.Size = new System.Drawing.Size(212, 20);
        this.textMasterPass.TabIndex = 35;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(384, 96);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(76, 26);
        this.butOK.TabIndex = 37;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 60);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 50);
        this.label1.TabIndex = 38;
        this.label1.Text = "Master Password (you must call us)";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(384, 132);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(76, 26);
        this.butCancel.TabIndex = 39;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(54, 5);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(406, 26);
        this.label2.TabIndex = 40;
        this.label2.Text = "This form is obsolete and cannot be accessed from anywhere.";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // FormPasswordReset
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(478, 176);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textMasterPass);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPasswordReset";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reset Password";
        this.Load += new System.EventHandler(this.FormRP_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRP_Load(Object sender, System.EventArgs e) throws Exception {
    }

    //it does not compromise security to include the hash to the master password in the code
    //because the user must still enter the password, not the hash.
    //masterHash="78sfTin/RP0rI84zv2Xc8Q==";
    //version 3.5: "1251671001032231238111186944262869879186";
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
    }

    //No longer functional
    //Debug.WriteLine(Userods.EncryptPassword(textMasterPass.Text));
    //if(!Userods.CheckTypedPassword(textMasterPass.Text,masterHash)){
    //	MessageBox.Show(Lan.g(this,"Master password incorrect."));
    //	return;
    //}
    //Security.ResetPassword();
    //PermissionsOld.Refresh();
    //MessageBox.Show(Lan.g(this,"Security Administration permission has been reset."));
    //DialogResult=DialogResult.OK;
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


