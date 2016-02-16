//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:07 PM
//

package OpenDental;

import OpenDental.FormEmailAddressEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;

/**
* 
*/
public class FormEmailAddressEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
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
    private OpenDental.UI.Button butDelete;
    public EmailAddress EmailAddressCur;
    private GroupBox groupOutgoing = new GroupBox();
    private GroupBox groupIncoming = new GroupBox();
    private TextBox textSMTPserverIncoming = new TextBox();
    private Label label8 = new Label();
    private Label label10 = new Label();
    private TextBox textPortIncoming = new TextBox();
    private TextBox textBox5 = new TextBox();
    private Label label11 = new Label();
    private Label label9 = new Label();
    private Label label12 = new Label();
    public boolean IsNew = new boolean();
    /**
    * 
    */
    public FormEmailAddressEdit() throws Exception {
        initializeComponent();
        Lan.f(this);
        Lan.C(this, new System.Windows.Forms.Control[]{ textBox1, textBox5 });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmailAddressEdit.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textSMTPserver = new System.Windows.Forms.TextBox();
        this.textSender = new System.Windows.Forms.TextBox();
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
        this.butDelete = new OpenDental.UI.Button();
        this.groupOutgoing = new System.Windows.Forms.GroupBox();
        this.groupIncoming = new System.Windows.Forms.GroupBox();
        this.textSMTPserverIncoming = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textPortIncoming = new System.Windows.Forms.TextBox();
        this.textBox5 = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.groupOutgoing.SuspendLayout();
        this.groupIncoming.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(509, 384);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 7;
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
        this.butOK.Location = new System.Drawing.Point(428, 384);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 6;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 19);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(173, 20);
        this.label1.TabIndex = 2;
        this.label1.Text = "Outgoing SMTP Server";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 142);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(177, 20);
        this.label2.TabIndex = 3;
        this.label2.Text = "E-mail address of sender";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSMTPserver
        //
        this.textSMTPserver.Location = new System.Drawing.Point(185, 19);
        this.textSMTPserver.Name = "textSMTPserver";
        this.textSMTPserver.Size = new System.Drawing.Size(218, 20);
        this.textSMTPserver.TabIndex = 0;
        //
        // textSender
        //
        this.textSender.Location = new System.Drawing.Point(185, 142);
        this.textSender.Name = "textSender";
        this.textSender.Size = new System.Drawing.Size(218, 20);
        this.textSender.TabIndex = 5;
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(187, 42);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(198, 74);
        this.textBox1.TabIndex = 16;
        this.textBox1.Text = "smtp.comcast.net\r\nmailhost.mycompany.com \r\nmail.mycompany.com\r\nsmtp.gmail.com\r\nor" + " similar...";
        //
        // textUsername
        //
        this.textUsername.Location = new System.Drawing.Point(197, 6);
        this.textUsername.Name = "textUsername";
        this.textUsername.Size = new System.Drawing.Size(218, 20);
        this.textUsername.TabIndex = 1;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(97, 6);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(98, 20);
        this.label3.TabIndex = 18;
        this.label3.Text = "Username";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPassword
        //
        this.textPassword.Location = new System.Drawing.Point(197, 26);
        this.textPassword.Name = "textPassword";
        this.textPassword.PasswordChar = '*';
        this.textPassword.Size = new System.Drawing.Size(218, 20);
        this.textPassword.TabIndex = 2;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(18, 26);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(177, 20);
        this.label4.TabIndex = 20;
        this.label4.Text = "Password";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPort
        //
        this.textPort.Location = new System.Drawing.Point(185, 114);
        this.textPort.Name = "textPort";
        this.textPort.Size = new System.Drawing.Size(56, 20);
        this.textPort.TabIndex = 3;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(9, 114);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(174, 20);
        this.label5.TabIndex = 22;
        this.label5.Text = "Outgoing Port";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(247, 114);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(251, 20);
        this.label6.TabIndex = 23;
        this.label6.Text = "Usually 587.  Sometimes 25 or 465.";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // checkSSL
        //
        this.checkSSL.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkSSL.Location = new System.Drawing.Point(21, 47);
        this.checkSSL.Name = "checkSSL";
        this.checkSSL.Size = new System.Drawing.Size(190, 17);
        this.checkSSL.TabIndex = 4;
        this.checkSSL.Text = "Use SSL";
        this.checkSSL.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkSSL.UseVisualStyleBackColor = true;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(217, 48);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(251, 20);
        this.label7.TabIndex = 25;
        this.label7.Text = "For Gmail and some others";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(43, 384);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 6;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // groupOutgoing
        //
        this.groupOutgoing.Controls.Add(this.label9);
        this.groupOutgoing.Controls.Add(this.textSMTPserver);
        this.groupOutgoing.Controls.Add(this.label1);
        this.groupOutgoing.Controls.Add(this.label2);
        this.groupOutgoing.Controls.Add(this.label6);
        this.groupOutgoing.Controls.Add(this.textSender);
        this.groupOutgoing.Controls.Add(this.textPort);
        this.groupOutgoing.Controls.Add(this.textBox1);
        this.groupOutgoing.Controls.Add(this.label5);
        this.groupOutgoing.Location = new System.Drawing.Point(12, 73);
        this.groupOutgoing.Name = "groupOutgoing";
        this.groupOutgoing.Size = new System.Drawing.Size(572, 180);
        this.groupOutgoing.TabIndex = 26;
        this.groupOutgoing.TabStop = false;
        this.groupOutgoing.Text = "Outgoing Email Settings";
        //
        // groupIncoming
        //
        this.groupIncoming.Controls.Add(this.textSMTPserverIncoming);
        this.groupIncoming.Controls.Add(this.label8);
        this.groupIncoming.Controls.Add(this.label10);
        this.groupIncoming.Controls.Add(this.textPortIncoming);
        this.groupIncoming.Controls.Add(this.textBox5);
        this.groupIncoming.Controls.Add(this.label11);
        this.groupIncoming.Location = new System.Drawing.Point(12, 259);
        this.groupIncoming.Name = "groupIncoming";
        this.groupIncoming.Size = new System.Drawing.Size(572, 116);
        this.groupIncoming.TabIndex = 27;
        this.groupIncoming.TabStop = false;
        this.groupIncoming.Text = "Incoming Email Settings";
        //
        // textSMTPserverIncoming
        //
        this.textSMTPserverIncoming.Location = new System.Drawing.Point(185, 19);
        this.textSMTPserverIncoming.Name = "textSMTPserverIncoming";
        this.textSMTPserverIncoming.Size = new System.Drawing.Size(218, 20);
        this.textSMTPserverIncoming.TabIndex = 0;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(12, 19);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(173, 20);
        this.label8.TabIndex = 2;
        this.label8.Text = "Incoming POP3 Server";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(247, 88);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(251, 20);
        this.label10.TabIndex = 23;
        this.label10.Text = "Usually 110.  Sometimes 995.";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // textPortIncoming
        //
        this.textPortIncoming.Location = new System.Drawing.Point(185, 88);
        this.textPortIncoming.Name = "textPortIncoming";
        this.textPortIncoming.Size = new System.Drawing.Size(56, 20);
        this.textPortIncoming.TabIndex = 3;
        //
        // textBox5
        //
        this.textBox5.BackColor = System.Drawing.SystemColors.Control;
        this.textBox5.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox5.Location = new System.Drawing.Point(187, 42);
        this.textBox5.Multiline = true;
        this.textBox5.Name = "textBox5";
        this.textBox5.Size = new System.Drawing.Size(198, 48);
        this.textBox5.TabIndex = 16;
        this.textBox5.Text = "pop.secureserver.net\r\npop.gmail.com\r\nor similar...";
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(9, 88);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(174, 20);
        this.label11.TabIndex = 22;
        this.label11.Text = "Incoming Port";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(404, 142);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(159, 20);
        this.label9.TabIndex = 24;
        this.label9.Text = "(not used in encrypted email)";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(416, 6);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(178, 20);
        this.label12.TabIndex = 28;
        this.label12.Text = "(full email address)";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // FormEmailAddressEdit
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(600, 424);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.groupIncoming);
        this.Controls.Add(this.groupOutgoing);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.checkSSL);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.textUsername);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textPassword);
        this.Controls.Add(this.label3);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEmailAddressEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit E-mail Address";
        this.Load += new System.EventHandler(this.FormEmailAddress_Load);
        this.groupOutgoing.ResumeLayout(false);
        this.groupOutgoing.PerformLayout();
        this.groupIncoming.ResumeLayout(false);
        this.groupIncoming.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEmailAddress_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.SecurityAdmin,true))
        {
            textPassword.PasswordChar = '*';
        }
         
        if (EmailAddressCur != null)
        {
            textSMTPserver.Text = EmailAddressCur.SMTPserver;
            textUsername.Text = EmailAddressCur.EmailUsername;
            textPassword.Text = EmailAddressCur.EmailPassword;
            textPort.Text = EmailAddressCur.ServerPort.ToString();
            checkSSL.Checked = EmailAddressCur.UseSSL;
            textSender.Text = EmailAddressCur.SenderAddress;
            textSMTPserverIncoming.Text = EmailAddressCur.Pop3ServerIncoming;
            textPortIncoming.Text = EmailAddressCur.ServerPortIncoming.ToString();
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (EmailAddressCur.EmailAddressNum == PrefC.getLong(PrefName.EmailDefaultAddressNum))
        {
            MsgBox.show(this,"Cannot delete the default email address.");
            return ;
        }
         
        for (int i = 0;i < Clinics.getList().Length;i++)
        {
            Clinic clinic = Clinics.getList()[i];
            if (clinic.EmailAddressNum == EmailAddressCur.EmailAddressNum)
            {
                MessageBox.Show(Lan.g(this,"Cannot delete the email address because it is used by clinic") + " " + clinic.Description);
                return ;
            }
             
        }
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this email address?"))
        {
            return ;
        }
         
        EmailAddresses.delete(EmailAddressCur.EmailAddressNum);
        DialogResult = DialogResult.OK;
    }

    //OK triggers a refresh for the grid.
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        EmailAddressCur.SMTPserver = PIn.String(textSMTPserver.Text);
        EmailAddressCur.EmailUsername = PIn.String(textUsername.Text);
        EmailAddressCur.EmailPassword = PIn.String(textPassword.Text);
        try
        {
            EmailAddressCur.ServerPort = PIn.Int(textPort.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            MsgBox.show(this,"invalid outgoing port number.");
        }

        EmailAddressCur.UseSSL = checkSSL.Checked;
        EmailAddressCur.SenderAddress = PIn.String(textSender.Text);
        EmailAddressCur.Pop3ServerIncoming = PIn.String(textSMTPserverIncoming.Text);
        try
        {
            EmailAddressCur.ServerPortIncoming = PIn.Int(textPortIncoming.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            MsgBox.show(this,"invalid incoming port number.");
        }

        if (IsNew)
        {
            EmailAddresses.insert(EmailAddressCur);
        }
        else
        {
            EmailAddresses.update(EmailAddressCur);
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


