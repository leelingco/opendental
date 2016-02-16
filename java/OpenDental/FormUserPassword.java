//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:57 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormUserPassword;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.Userods;

/**
* Summary description for FormBasicTemplate.
*/
public class FormUserPassword  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPassword = new System.Windows.Forms.TextBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private boolean IsCreate = new boolean();
    private TextBox textUserName = new TextBox();
    private Label label3 = new Label();
    private TextBox textCurrent = new TextBox();
    private Label labelCurrent = new Label();
    private CheckBox checkShow = new CheckBox();
    /**
    * 
    */
    public String hashedResult = new String();
    public boolean IsInSecurityWindow = new boolean();
    /**
    * Set true if creating rather than changing a password.
    */
    public FormUserPassword(boolean isCreate, String username) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        IsCreate = isCreate;
        textUserName.Text = username;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormUserPassword.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.textUserName = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textCurrent = new System.Windows.Forms.TextBox();
        this.labelCurrent = new System.Windows.Forms.Label();
        this.checkShow = new System.Windows.Forms.CheckBox();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(13, 80);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(157, 18);
        this.label1.TabIndex = 2;
        this.label1.Text = "New Password";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(172, 79);
        this.textPassword.Name = "textPassword";
        this.textPassword.PasswordChar = '*';
        this.textPassword.Size = new System.Drawing.Size(203, 20);
        this.textPassword.TabIndex = 1;
        //
        // textUserName
        //
        this.textUserName.Location = new System.Drawing.Point(172, 23);
        this.textUserName.Name = "textUserName";
        this.textUserName.ReadOnly = true;
        this.textUserName.Size = new System.Drawing.Size(203, 20);
        this.textUserName.TabIndex = 5;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(13, 24);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(157, 18);
        this.label3.TabIndex = 6;
        this.label3.Text = "User";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCurrent
        //
        this.textCurrent.Location = new System.Drawing.Point(172, 51);
        this.textCurrent.Name = "textCurrent";
        this.textCurrent.PasswordChar = '*';
        this.textCurrent.Size = new System.Drawing.Size(203, 20);
        this.textCurrent.TabIndex = 0;
        //
        // labelCurrent
        //
        this.labelCurrent.Location = new System.Drawing.Point(13, 52);
        this.labelCurrent.Name = "labelCurrent";
        this.labelCurrent.Size = new System.Drawing.Size(157, 18);
        this.labelCurrent.TabIndex = 8;
        this.labelCurrent.Text = "Current Password";
        this.labelCurrent.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkShow
        //
        this.checkShow.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShow.Location = new System.Drawing.Point(82, 107);
        this.checkShow.Name = "checkShow";
        this.checkShow.Size = new System.Drawing.Size(104, 18);
        this.checkShow.TabIndex = 9;
        this.checkShow.Text = "Show";
        this.checkShow.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkShow.UseVisualStyleBackColor = true;
        this.checkShow.Click += new System.EventHandler(this.checkShow_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(264, 158);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
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
        this.butCancel.Location = new System.Drawing.Point(357, 158);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormUserPassword
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(484, 209);
        this.Controls.Add(this.checkShow);
        this.Controls.Add(this.textCurrent);
        this.Controls.Add(this.labelCurrent);
        this.Controls.Add(this.textUserName);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormUserPassword";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Change Password";
        this.Load += new System.EventHandler(this.FormUserPassword_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formUserPassword_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsCreate)
        {
            Text = Lan.g(this,"Create Password");
        }
         
        if (IsInSecurityWindow)
        {
            labelCurrent.Visible = false;
            textCurrent.Visible = false;
        }
         
    }

    private void checkShow_Click(Object sender, EventArgs e) throws Exception {
        //char ch=textPassword.PasswordChar;
        if (checkShow.Checked)
        {
            textPassword.PasswordChar = '\0';
        }
        else
        {
            textPassword.PasswordChar = '*';
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!IsInSecurityWindow && !StringSupport.equals(Userods.EncryptPassword(textCurrent.Text), Security.getCurUser().Password))
        {
            MsgBox.show(this,"Current password incorrect.");
            return ;
        }
         
        if (PrefC.getBool(PrefName.PasswordsMustBeStrong))
        {
            String explanation = Userods.IsPasswordStrong(textPassword.Text);
            if (!StringSupport.equals(explanation, ""))
            {
                MessageBox.Show(explanation);
                return ;
            }
             
        }
         
        if (StringSupport.equals(textPassword.Text, ""))
        {
            hashedResult = "";
        }
        else
        {
            hashedResult = Userods.EncryptPassword(textPassword.Text);
        } 
        //MessageBox.Show(hashedResult);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


