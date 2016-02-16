//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatientPortalSetup;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.Family;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import OpenDental.Properties.Resources;


/**
* DialogResult will be Abort if message was unable to be read. If message is read successfully (Ok or Cancel), then caller is responsible for updating SentOrReceived to read (where applicable).
*/
public class FormWebMailMessageEdit  extends Form 
{

    private EmailMessage _secureMessage;
    private EmailMessage _insecureMessage;
    private EmailAddress _emailAddressSender;
    private long _patNum = new long();
    private long _replyToEmailMessageNum = new long();
    private boolean _allowSendSecureMessage = true;
    private boolean _allowSendNotificationMessage = true;
    private List<Patient> _listPatients = null;
    /**
    * Default ctor. This implies that we are composing a new message, NOT replying to an existing message. Provider attached to this message should be Security.CurUser.ProvNum
    */
    public FormWebMailMessageEdit(long patNum) throws Exception {
        this(patNum, 0);
    }

    /**
    * This implies that we are replying to an existing message. Provider attached to this message will be the ProvNum attached to the original message. If this ProvNum does not match Security.CurUser.ProvNum then the send action will be blocked.
    */
    public FormWebMailMessageEdit(long patNum, long emailMessageNum) throws Exception {
        initializeComponent();
        Lan.F(this);
        _replyToEmailMessageNum = emailMessageNum;
        _patNum = patNum;
    }

    private void formWebMailMessageEdit_Load(Object sender, EventArgs e) throws Exception {
        verifyInputs();
    }

    private void blockSendSecureMessage(String reason) throws Exception {
        _allowSendSecureMessage = false;
        butSend.Enabled = false;
        butPreview.Enabled = false;
        labelNotification.Text = Lan.g(this,"Warning") + ": " + Lan.g(this,"Secure email send prevented") + " - " + Lan.g(this,reason);
        labelNotification.ForeColor = Color.Red;
    }

    private void blockSendNotificationMessage(String reason) throws Exception {
        _allowSendNotificationMessage = false;
        butSend.Enabled = false;
        butPreview.Enabled = false;
        labelNotification.Text = Lan.g(this,"Warning") + ": " + Lan.g(this,"Notification email send prevented") + " - " + Lan.g(this,reason);
        labelNotification.ForeColor = Color.Red;
    }

    public void allowSendMessages() throws Exception {
        _allowSendSecureMessage = true;
        _allowSendNotificationMessage = true;
        butSend.Enabled = true;
        butPreview.Enabled = true;
        labelNotification.ForeColor = SystemColors.ControlText;
        labelNotification.ForeColor = SystemColors.ControlText;
    }

    private void verifyInputs() throws Exception {
        allowSendMessages();
        long priProvNum = 0;
        long patNumSubj = _patNum;
        String notificationSubject = new String();
        String notificationBodyNoUrl = new String();
        String notificationURL = new String();
        Family family = null;
        Patient patCur = Patients.getPat(_patNum);
        comboRegardingPatient.Items.Clear();
        if (patCur == null)
        {
            blockSendSecureMessage("Patient is invalid.");
            _listPatients = null;
        }
        else
        {
            family = Patients.getFamily(_patNum);
            textTo.Text = patCur.getNameFL();
            Provider priProv = Providers.getProv(patCur.PriProv);
            if (priProv == null)
            {
                blockSendSecureMessage("Invalid primary provider for this patient.");
            }
            else
            {
                priProvNum = priProv.ProvNum;
                Provider userODProv = Providers.getProv(Security.getCurUser().ProvNum);
                if (userODProv == null)
                {
                    blockSendSecureMessage("Not logged in as valid provider. Login as patient's primary provider to send message.");
                }
                else if (userODProv.ProvNum != priProv.ProvNum)
                {
                    blockSendSecureMessage("The patient's primary provider does not match the provider attached to the user currently logged in. Login as patient's primary provider to send message.");
                }
                else
                {
                    textFrom.Text = priProv.getFormalName();
                }  
            } 
            if (StringSupport.equals(patCur.Email, ""))
            {
                blockSendNotificationMessage("Missing patient email. Setup patient email using Family module.");
            }
             
            if (StringSupport.equals(patCur.OnlinePassword, ""))
            {
                blockSendNotificationMessage("Patient has not been given online access. Setup patient online access using Family module.");
            }
             
        } 
        //We are replying to an existing message so verify that the provider linked to this message matches our currently logged in provider.
        //This is because web mail communications will be visible in the patients Chart Module.
        if (_replyToEmailMessageNum > 0)
        {
            EmailMessage replyToEmailMessage = EmailMessages.getOne(_replyToEmailMessageNum);
            if (replyToEmailMessage == null)
            {
                MsgBox.show(this,"Invalid input email message");
                DialogResult = DialogResult.Abort;
                return ;
            }
             
            //nothing to show so abort, caller should be waiting for abort to determine if message should be marked read
            textSubject.Text = replyToEmailMessage.Subject;
            if (replyToEmailMessage.Subject.IndexOf("RE:") != 0)
            {
                textSubject.Text = "RE: " + textSubject.Text;
            }
             
            patNumSubj = replyToEmailMessage.PatNumSubj;
            Patient patRegarding = Patients.getOnePat(family.ListPats,patNumSubj);
            textBody.Text = "\r\n\r\n-----" + Lan.g(this,"Original Message") + "-----\r\n" + (patRegarding == null ? "" : (Lan.g(this,"Regarding Patient") + ": " + patRegarding.getNameFL() + "\r\n")) + Lan.g(this,"From") + ": " + replyToEmailMessage.FromAddress + "\r\n" + Lan.g(this,"Sent") + ": " + replyToEmailMessage.MsgDateTime.ToShortDateString() + " " + replyToEmailMessage.MsgDateTime.ToShortTimeString() + "\r\n" + Lan.g(this,"To") + ": " + replyToEmailMessage.ToAddress + "\r\n" + Lan.g(this,"Subject") + ": " + replyToEmailMessage.Subject + "\r\n\r\n" + replyToEmailMessage.BodyText;
        }
         
        if (patCur == null || family == null)
        {
            blockSendSecureMessage("Patient's family not setup propertly. Make sure guarantor is valid.");
        }
        else
        {
            _listPatients = new List<Patient>();
            boolean isGuar = patCur.Guarantor == patCur.PatNum;
            for (int i = 0;i < family.ListPats.Length;i++)
            {
                Patient patFamilyMember = family.ListPats[i];
                if (isGuar || patFamilyMember.PatNum == patNumSubj)
                {
                    _listPatients.Add(patFamilyMember);
                    comboRegardingPatient.Items.Add(patFamilyMember.getNameFL());
                    if (patFamilyMember.PatNum == patNumSubj)
                    {
                        comboRegardingPatient.SelectedIndex = (comboRegardingPatient.Items.Count - 1);
                    }
                     
                }
                 
            }
        } 
        notificationSubject = PrefC.getString(PrefName.PatientPortalNotifySubject);
        notificationBodyNoUrl = PrefC.getString(PrefName.PatientPortalNotifyBody);
        notificationURL = PrefC.getString(PrefName.PatientPortalURL);
        _emailAddressSender = EmailAddresses.GetByClinic(0);
        //Default for clinic/practice.
        if (_emailAddressSender == null)
        {
            blockSendNotificationMessage("Practice email is not setup properly. Setup practice email in setup.");
        }
         
        if (StringSupport.equals(notificationSubject, ""))
        {
            blockSendNotificationMessage("Missing notification email subject. Create a subject in setup.");
        }
         
        if (StringSupport.equals(notificationBodyNoUrl, ""))
        {
            blockSendNotificationMessage("Missing notification email body. Create a body in setup.");
        }
         
        if (_allowSendSecureMessage)
        {
            _secureMessage = new EmailMessage();
            _secureMessage.FromAddress = textFrom.Text;
            _secureMessage.ToAddress = textTo.Text;
            _secureMessage.PatNum = patCur.PatNum;
            _secureMessage.SentOrReceived = EmailSentOrReceived.WebMailSent;
            //this is secure so mark as webmail sent
            _secureMessage.ProvNumWebMail = priProvNum;
        }
         
        if (_allowSendNotificationMessage)
        {
            _insecureMessage = new EmailMessage();
            _insecureMessage.FromAddress = _emailAddressSender.SenderAddress;
            _insecureMessage.ToAddress = patCur.Email;
            _insecureMessage.PatNum = patCur.PatNum;
            _insecureMessage.Subject = notificationSubject;
            _insecureMessage.BodyText = notificationBodyNoUrl.Replace("[URL]", notificationURL);
            _insecureMessage.SentOrReceived = EmailSentOrReceived.Sent;
        }
         
        //this is not secure so just mark as regular sent
        if (_allowSendSecureMessage && _allowSendNotificationMessage)
        {
            labelNotification.Text = Lan.g(this,"Notification email will be sent to patient") + ": " + patCur.Email;
        }
         
    }

    private boolean verifyOutputs() throws Exception {
        if (StringSupport.equals(textSubject.Text, ""))
        {
            MsgBox.show(this,"Enter a subject");
            textSubject.Focus();
            return false;
        }
         
        if (StringSupport.equals(textBody.Text, ""))
        {
            MsgBox.show(this,"Email body is empty");
            textBody.Focus();
            return false;
        }
         
        if (getPatNumSubj() <= 0)
        {
            MsgBox.show(this,"Select a valid patient");
            comboRegardingPatient.Focus();
            return false;
        }
         
        return true;
    }

    private long getPatNumSubj() throws Exception {
        try
        {
            if (_listPatients == null)
            {
                return 0;
            }
             
            return _listPatients[comboRegardingPatient.SelectedIndex].PatNum;
        }
        catch (Exception __dummyCatchVar0)
        {
            return 0;
        }
    
    }

    private void menuItemSetup_Click(Object sender, EventArgs e) throws Exception {
        FormPatientPortalSetup formPPS = new FormPatientPortalSetup();
        formPPS.ShowDialog();
        if (formPPS.DialogResult == DialogResult.OK)
        {
            verifyInputs();
        }
         
    }

    private void butPreview_Click(Object sender, EventArgs e) throws Exception {
        if (!verifyOutputs())
        {
            return ;
        }
         
        StringBuilder sb = new StringBuilder();
        sb.AppendLine("------ " + Lan.g(this,"Notification email that will be sent to the patient's email address:"));
        if (_allowSendNotificationMessage)
        {
            sb.AppendLine(Lan.g(this,"Subject") + ": " + _insecureMessage.Subject);
            sb.AppendLine(Lan.g(this,"Body") + ": " + _insecureMessage.BodyText);
        }
        else
        {
            sb.AppendLine(Lan.g(this,"------ " + Lan.g(this,"Notification email settings are not set up.  Click Setup from the web mail message edit window to set up notification emails") + " ------"));
        } 
        sb.AppendLine();
        sb.AppendLine("------ " + Lan.g(this,"Secure web mail message that will be sent to the patient's portal:"));
        sb.AppendLine(Lan.g(this,"Subject") + ": " + textSubject.Text);
        sb.AppendLine(Lan.g(this,"Body") + ": " + textBody.Text.Replace("\n", "\r\n"));
        CodeBase.MsgBoxCopyPaste msgBox = new CodeBase.MsgBoxCopyPaste(sb.ToString());
        msgBox.ShowDialog();
    }

    private void butSend_Click(Object sender, EventArgs e) throws Exception {
        if (!_allowSendSecureMessage)
        {
            MsgBox.show(this,"Send not permitted");
            return ;
        }
         
        if (!verifyOutputs())
        {
            return ;
        }
         
        //Insert the message. The patient will not see this as an actual email.
        //Rather, they must login to the patient portal (secured) and view the message that way.
        //This is how we get around sending the patient a secure message, which would be a hassle for all involved.
        _secureMessage.Subject = textSubject.Text;
        _secureMessage.BodyText = textBody.Text;
        _secureMessage.MsgDateTime = DateTime.Now;
        _secureMessage.PatNumSubj = getPatNumSubj();
        EmailMessages.insert(_secureMessage);
        if (_allowSendNotificationMessage)
        {
            //Send an insecure notification email to the patient.
            _insecureMessage.MsgDateTime = DateTime.Now;
            _insecureMessage.PatNumSubj = getPatNumSubj();
            try
            {
                EmailMessages.sendEmailUnsecure(_insecureMessage,_emailAddressSender);
                //Insert the notification email into the emailmessage table so we have a record that it was sent.
                EmailMessages.insert(_insecureMessage);
            }
            catch (Exception ex)
            {
                MsgBox.Show(this, ex.Message);
                return ;
            }
        
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
        this.components = new System.ComponentModel.Container();
        this.textTo = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textFrom = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textSubject = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.labelNotification = new System.Windows.Forms.Label();
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSetup = new System.Windows.Forms.MenuItem();
        this.label5 = new System.Windows.Forms.Label();
        this.comboRegardingPatient = new System.Windows.Forms.ComboBox();
        this.butPreview = new OpenDental.UI.Button();
        this.butSend = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.textBody = new OpenDental.ODtextBox();
        this.SuspendLayout();
        //
        // textTo
        //
        this.textTo.Location = new System.Drawing.Point(119, 30);
        this.textTo.Name = "textTo";
        this.textTo.ReadOnly = true;
        this.textTo.Size = new System.Drawing.Size(305, 20);
        this.textTo.TabIndex = 1;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(22, 33);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(92, 14);
        this.label1.TabIndex = 11;
        this.label1.Text = "To:";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFrom
        //
        this.textFrom.Location = new System.Drawing.Point(119, 57);
        this.textFrom.Name = "textFrom";
        this.textFrom.ReadOnly = true;
        this.textFrom.Size = new System.Drawing.Size(305, 20);
        this.textFrom.TabIndex = 2;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(22, 60);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(92, 14);
        this.label3.TabIndex = 13;
        this.label3.Text = "From:";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(22, 112);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(92, 14);
        this.label2.TabIndex = 13;
        this.label2.Text = "Message:";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubject
        //
        this.textSubject.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textSubject.Location = new System.Drawing.Point(119, 84);
        this.textSubject.Name = "textSubject";
        this.textSubject.Size = new System.Drawing.Size(670, 20);
        this.textSubject.TabIndex = 3;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(22, 87);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(92, 14);
        this.label4.TabIndex = 16;
        this.label4.Text = "Subject:";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelNotification
        //
        this.labelNotification.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.labelNotification.ForeColor = System.Drawing.SystemColors.ControlText;
        this.labelNotification.Location = new System.Drawing.Point(86, 391);
        this.labelNotification.Name = "labelNotification";
        this.labelNotification.Size = new System.Drawing.Size(541, 14);
        this.labelNotification.TabIndex = 17;
        this.labelNotification.Text = "Warning: Patient email is not setup properly. No notification email will be sent " + "to this patient.";
        this.labelNotification.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSetup });
        //
        // menuItemSetup
        //
        this.menuItemSetup.Index = 0;
        this.menuItemSetup.Text = "Setup";
        this.menuItemSetup.Click += new System.EventHandler(this.menuItemSetup_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(14, 6);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 14);
        this.label5.TabIndex = 19;
        this.label5.Text = "Regarding Patient:";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboRegardingPatient
        //
        this.comboRegardingPatient.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboRegardingPatient.FormattingEnabled = true;
        this.comboRegardingPatient.Location = new System.Drawing.Point(120, 4);
        this.comboRegardingPatient.MaxDropDownItems = 30;
        this.comboRegardingPatient.Name = "comboRegardingPatient";
        this.comboRegardingPatient.Size = new System.Drawing.Size(304, 21);
        this.comboRegardingPatient.TabIndex = 0;
        //
        // butPreview
        //
        this.butPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPreview.setAutosize(true);
        this.butPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreview.setCornerRadius(4F);
        this.butPreview.Image = Resources.getbutPreview();
        this.butPreview.Location = new System.Drawing.Point(44, 342);
        this.butPreview.Name = "butPreview";
        this.butPreview.Size = new System.Drawing.Size(69, 24);
        this.butPreview.TabIndex = 5;
        this.butPreview.Text = "&Cancel";
        this.butPreview.Click += new System.EventHandler(this.butPreview_Click);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(633, 386);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 24);
        this.butSend.TabIndex = 6;
        this.butSend.Text = "&Send";
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(714, 386);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 7;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // textBody
        //
        this.textBody.AcceptsTab = true;
        this.textBody.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textBody.DetectUrls = false;
        this.textBody.Location = new System.Drawing.Point(119, 112);
        this.textBody.Name = "textBody";
        this.textBody.setQuickPasteType(OpenDentBusiness.QuickPasteType.Email);
        this.textBody.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textBody.Size = new System.Drawing.Size(670, 254);
        this.textBody.TabIndex = 4;
        this.textBody.Text = "";
        //
        // FormWebMailMessageEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(818, 422);
        this.Controls.Add(this.comboRegardingPatient);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butPreview);
        this.Controls.Add(this.labelNotification);
        this.Controls.Add(this.textSubject);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textBody);
        this.Controls.Add(this.textFrom);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textTo);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.butCancel);
        this.Menu = this.mainMenu1;
        this.Name = "FormWebMailMessageEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Web Mail Message Edit";
        this.Load += new System.EventHandler(this.FormWebMailMessageEdit_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butSend;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textTo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textBody;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubject = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelNotification = new System.Windows.Forms.Label();
    private System.Windows.Forms.MainMenu mainMenu1 = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSetup = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.Button butPreview;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboRegardingPatient = new System.Windows.Forms.ComboBox();
}


