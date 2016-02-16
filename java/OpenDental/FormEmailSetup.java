//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:07 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.FormEmailSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormEmailSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBox6 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSMTPserver = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSender = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private TextBox textUsername = new TextBox();
    private Label label3 = new Label();
    private TextBox textPassword = new TextBox();
    private Label label4 = new Label();
    private TextBox textPort = new TextBox();
    private Label label5 = new Label();
    private Label label6 = new Label();
    private CheckBox checkSSL = new CheckBox();
    private Label label7 = new Label();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormEmailSetup() throws Exception {
        initializeComponent();
        Lan.f(this);
        Lan.C(this, new System.Windows.Forms.Control[]{ textBox6, textBox1 });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmailSetup.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textSMTPserver = new System.Windows.Forms.TextBox();
        this.textSender = new System.Windows.Forms.TextBox();
        this.textBox6 = new System.Windows.Forms.TextBox();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.textUsername = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textPassword = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textPort = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.checkSSL = new System.Windows.Forms.CheckBox();
        this.label7 = new System.Windows.Forms.Label();
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(634, 382);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
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
        this.butOK.Location = new System.Drawing.Point(634, 340);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(70, 61);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(140, 16);
        this.label1.TabIndex = 2;
        this.label1.Text = "SMTP Server";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(31, 302);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(177, 20);
        this.label2.TabIndex = 3;
        this.label2.Text = "E-mail address of sender";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSMTPserver
        //
        this.textSMTPserver.Location = new System.Drawing.Point(210, 58);
        this.textSMTPserver.Name = "textSMTPserver";
        this.textSMTPserver.Size = new System.Drawing.Size(218, 20);
        this.textSMTPserver.TabIndex = 0;
        //
        // textSender
        //
        this.textSender.Location = new System.Drawing.Point(210, 299);
        this.textSender.Name = "textSender";
        this.textSender.Size = new System.Drawing.Size(340, 20);
        this.textSender.TabIndex = 1;
        //
        // textBox6
        //
        this.textBox6.BackColor = System.Drawing.SystemColors.Control;
        this.textBox6.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox6.Location = new System.Drawing.Point(39, 18);
        this.textBox6.Multiline = true;
        this.textBox6.Name = "textBox6";
        this.textBox6.Size = new System.Drawing.Size(589, 30);
        this.textBox6.TabIndex = 15;
        this.textBox6.Text = "There is no way to receive e-mail from within Open Dental yet.  These settings ar" + "e for outgoing e-mail.";
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(212, 81);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(198, 74);
        this.textBox1.TabIndex = 16;
        this.textBox1.Text = "smtp.comcast.net\r\nmailhost.mycompany.com \r\nmail.mycompany.com\r\nsmtp.gmail.com\r\nor" + " similar...";
        //
        // textUsername
        //
        this.textUsername.Location = new System.Drawing.Point(210, 161);
        this.textUsername.Name = "textUsername";
        this.textUsername.Size = new System.Drawing.Size(218, 20);
        this.textUsername.TabIndex = 17;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(31, 164);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(177, 20);
        this.label3.TabIndex = 18;
        this.label3.Text = "Username";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(210, 187);
        this.textPassword.Name = "textPassword";
        this.textPassword.PasswordChar = '*';
        this.textPassword.Size = new System.Drawing.Size(218, 20);
        this.textPassword.TabIndex = 19;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(31, 190);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(177, 20);
        this.label4.TabIndex = 20;
        this.label4.Text = "Password";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPort
        //
        this.textPort.Location = new System.Drawing.Point(210, 213);
        this.textPort.Name = "textPort";
        this.textPort.Size = new System.Drawing.Size(56, 20);
        this.textPort.TabIndex = 21;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(31, 216);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(177, 20);
        this.label5.TabIndex = 22;
        this.label5.Text = "Port";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(272, 216);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(251, 20);
        this.label6.TabIndex = 23;
        this.label6.Text = "Usually 587.  Sometimes 25 or 465.";
        //
        // checkSSL
        //
        this.checkSSL.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkSSL.Location = new System.Drawing.Point(12, 239);
        this.checkSSL.Name = "checkSSL";
        this.checkSSL.Size = new System.Drawing.Size(212, 17);
        this.checkSSL.TabIndex = 24;
        this.checkSSL.Text = "Use SSL";
        this.checkSSL.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkSSL.UseVisualStyleBackColor = true;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(230, 240);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(251, 20);
        this.label7.TabIndex = 25;
        this.label7.Text = "For Gmail and some others";
        //
        // FormEmailSetup
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(739, 432);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.checkSSL);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.textPort);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textUsername);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textBox1);
        this.Controls.Add(this.textBox6);
        this.Controls.Add(this.textSender);
        this.Controls.Add(this.textSMTPserver);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEmailSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup E-mail";
        this.Load += new System.EventHandler(this.FormEmailSetup_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEmailSetup_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin,true))
        {
            textPassword.PasswordChar = '*';
        }
         
        textSMTPserver.Text = PrefC.getString(PrefName.EmailSMTPserver);
        textUsername.Text = PrefC.getString(PrefName.EmailUsername);
        textPassword.Text = PrefC.getString(PrefName.EmailPassword);
        textPort.Text = PrefC.getString(PrefName.EmailPort);
        checkSSL.Checked = PrefC.getBool(PrefName.EmailUseSSL);
        textSender.Text = PrefC.getString(PrefName.EmailSenderAddress);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        Prefs.UpdateString(PrefName.EmailSMTPserver, textSMTPserver.Text);
        Prefs.UpdateString(PrefName.EmailUsername, textUsername.Text);
        Prefs.UpdateString(PrefName.EmailPassword, textPassword.Text);
        try
        {
            Prefs.UpdateLong(PrefName.EmailPort, PIn.Long(textPort.Text));
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"invalid port number.");
        }

        Prefs.UpdateBool(PrefName.EmailUseSSL, checkSSL.Checked);
        Prefs.UpdateString(PrefName.EmailSenderAddress, textSender.Text);
        DataValid.setInvalid(InvalidType.Prefs);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


