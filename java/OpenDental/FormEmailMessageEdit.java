//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:07 PM
//

package OpenDental;

import CodeBase.MsgBoxCopyPaste;
import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormEhrLabOrderImport;
import OpenDental.FormEhrSummaryOfCare;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormEmailTemplateEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.EmailTemplate;
import OpenDentBusiness.EmailTemplates;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;

//using OpenDental.Reporting;
//using Indy.Sockets.IndySMTP;
//using Indy.Sockets.IndyMessage;
/**
* Summary description for FormBasicTemplate.
*/
public class FormEmailMessageEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubject = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textToAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textFromAddress = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butSend;
    private System.Windows.Forms.TextBox textMsgDateTime = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butDeleteTemplate;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textBodyText;
    private System.Windows.Forms.ListBox listTemplates = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butInsert;
    private System.Windows.Forms.Label labelSent = new System.Windows.Forms.Label();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private boolean templatesChanged = new boolean();
    private System.Windows.Forms.Panel panelTemplates = new System.Windows.Forms.Panel();
    private boolean messageChanged = new boolean();
    private OpenDental.UI.Button butSave;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butAttach;
    private ListBox listAttachments = new ListBox();
    private ContextMenu contextMenuAttachments = new ContextMenu();
    private MenuItem menuItemOpen = new MenuItem();
    private MenuItem menuItemRename = new MenuItem();
    private MenuItem menuItemRemove = new MenuItem();
    private OpenDental.UI.Button buttonFuchsMailDSF;
    private OpenDental.UI.Button buttonFuchsMailDMF;
    //private int PatNum;
    private EmailMessage MessageCur;
    private Label labelDecrypt = new Label();
    private OpenDental.UI.Button butDecrypt;
    private TextBox textSentOrReceived = new TextBox();
    private Label label5 = new Label();
    private WebBrowser webBrowser = new WebBrowser();
    private OpenDental.UI.Button butEncryptAndSend;
    private OpenDental.UI.Button butRawMessage;
    /**
    * Used when attaching to get AtoZ folder, and when sending to get Clinic.
    */
    private Patient _patCur;
    /**
    * 
    */
    public FormEmailMessageEdit(EmailMessage messageCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        Lan.f(this);
        MessageCur = messageCur.copy();
        _patCur = Patients.getPat(messageCur.PatNum);
        //we could just as easily pass this in.
        listAttachments.ContextMenu = contextMenuAttachments;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmailMessageEdit.class);
        this.label2 = new System.Windows.Forms.Label();
        this.textSubject = new System.Windows.Forms.TextBox();
        this.textToAddress = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textFromAddress = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textMsgDateTime = new System.Windows.Forms.TextBox();
        this.labelSent = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.listTemplates = new System.Windows.Forms.ListBox();
        this.panelTemplates = new System.Windows.Forms.Panel();
        this.butInsert = new OpenDental.UI.Button();
        this.butDeleteTemplate = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.listAttachments = new System.Windows.Forms.ListBox();
        this.contextMenuAttachments = new System.Windows.Forms.ContextMenu();
        this.menuItemOpen = new System.Windows.Forms.MenuItem();
        this.menuItemRename = new System.Windows.Forms.MenuItem();
        this.menuItemRemove = new System.Windows.Forms.MenuItem();
        this.labelDecrypt = new System.Windows.Forms.Label();
        this.textSentOrReceived = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.butEncryptAndSend = new OpenDental.UI.Button();
        this.webBrowser = new System.Windows.Forms.WebBrowser();
        this.butDecrypt = new OpenDental.UI.Button();
        this.buttonFuchsMailDMF = new OpenDental.UI.Button();
        this.buttonFuchsMailDSF = new OpenDental.UI.Button();
        this.butAttach = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butSave = new OpenDental.UI.Button();
        this.textBodyText = new OpenDental.ODtextBox();
        this.butSend = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butRawMessage = new OpenDental.UI.Button();
        this.panelTemplates.SuspendLayout();
        this.SuspendLayout();
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(210, 85);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(69, 14);
        this.label2.TabIndex = 3;
        this.label2.Text = "Subject:";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSubject
        //
        this.textSubject.Location = new System.Drawing.Point(278, 83);
        this.textSubject.Name = "textSubject";
        this.textSubject.Size = new System.Drawing.Size(328, 20);
        this.textSubject.TabIndex = 2;
        //
        // textToAddress
        //
        this.textToAddress.Location = new System.Drawing.Point(278, 62);
        this.textToAddress.Name = "textToAddress";
        this.textToAddress.Size = new System.Drawing.Size(328, 20);
        this.textToAddress.TabIndex = 3;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(206, 66);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(71, 14);
        this.label1.TabIndex = 9;
        this.label1.Text = "To:";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFromAddress
        //
        this.textFromAddress.Location = new System.Drawing.Point(278, 41);
        this.textFromAddress.Name = "textFromAddress";
        this.textFromAddress.ReadOnly = true;
        this.textFromAddress.Size = new System.Drawing.Size(328, 20);
        this.textFromAddress.TabIndex = 4;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(206, 45);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(71, 14);
        this.label3.TabIndex = 11;
        this.label3.Text = "From:";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMsgDateTime
        //
        this.textMsgDateTime.BackColor = System.Drawing.SystemColors.Control;
        this.textMsgDateTime.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textMsgDateTime.ForeColor = System.Drawing.Color.Red;
        this.textMsgDateTime.Location = new System.Drawing.Point(278, 25);
        this.textMsgDateTime.Name = "textMsgDateTime";
        this.textMsgDateTime.Size = new System.Drawing.Size(253, 13);
        this.textMsgDateTime.TabIndex = 12;
        this.textMsgDateTime.Text = "Unsent";
        //
        // labelSent
        //
        this.labelSent.Location = new System.Drawing.Point(207, 24);
        this.labelSent.Name = "labelSent";
        this.labelSent.Size = new System.Drawing.Size(71, 14);
        this.labelSent.TabIndex = 13;
        this.labelSent.Text = "Date / Time:";
        this.labelSent.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(8, 7);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(124, 14);
        this.label4.TabIndex = 18;
        this.label4.Text = "E-mail Template";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listTemplates
        //
        this.listTemplates.Location = new System.Drawing.Point(10, 26);
        this.listTemplates.Name = "listTemplates";
        this.listTemplates.Size = new System.Drawing.Size(164, 277);
        this.listTemplates.TabIndex = 0;
        this.listTemplates.TabStop = false;
        this.listTemplates.DoubleClick += new System.EventHandler(this.listTemplates_DoubleClick);
        //
        // panelTemplates
        //
        this.panelTemplates.Controls.Add(this.butInsert);
        this.panelTemplates.Controls.Add(this.butDeleteTemplate);
        this.panelTemplates.Controls.Add(this.butAdd);
        this.panelTemplates.Controls.Add(this.label4);
        this.panelTemplates.Controls.Add(this.listTemplates);
        this.panelTemplates.Location = new System.Drawing.Point(8, 9);
        this.panelTemplates.Name = "panelTemplates";
        this.panelTemplates.Size = new System.Drawing.Size(180, 370);
        this.panelTemplates.TabIndex = 0;
        //
        // butInsert
        //
        this.butInsert.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInsert.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butInsert.setAutosize(true);
        this.butInsert.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInsert.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInsert.setCornerRadius(4F);
        this.butInsert.Image = Resources.getRight();
        this.butInsert.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butInsert.Location = new System.Drawing.Point(102, 305);
        this.butInsert.Name = "butInsert";
        this.butInsert.Size = new System.Drawing.Size(74, 26);
        this.butInsert.TabIndex = 2;
        this.butInsert.Text = "Insert";
        this.butInsert.Click += new System.EventHandler(this.butInsert_Click);
        //
        // butDeleteTemplate
        //
        this.butDeleteTemplate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteTemplate.setAutosize(true);
        this.butDeleteTemplate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteTemplate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteTemplate.setCornerRadius(4F);
        this.butDeleteTemplate.Image = Resources.getdeleteX();
        this.butDeleteTemplate.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteTemplate.Location = new System.Drawing.Point(7, 339);
        this.butDeleteTemplate.Name = "butDeleteTemplate";
        this.butDeleteTemplate.Size = new System.Drawing.Size(75, 26);
        this.butDeleteTemplate.TabIndex = 3;
        this.butDeleteTemplate.Text = "Delete";
        this.butDeleteTemplate.Click += new System.EventHandler(this.butDeleteTemplate_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(7, 305);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 1;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // listAttachments
        //
        this.listAttachments.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.listAttachments.Location = new System.Drawing.Point(612, 21);
        this.listAttachments.Name = "listAttachments";
        this.listAttachments.Size = new System.Drawing.Size(315, 82);
        this.listAttachments.TabIndex = 0;
        this.listAttachments.TabStop = false;
        this.listAttachments.DoubleClick += new System.EventHandler(this.listAttachments_DoubleClick);
        this.listAttachments.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listAttachments_MouseDown);
        //
        // contextMenuAttachments
        //
        this.contextMenuAttachments.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemOpen, this.menuItemRename, this.menuItemRemove });
        this.contextMenuAttachments.Popup += new System.EventHandler(this.contextMenuAttachments_Popup);
        //
        // menuItemOpen
        //
        this.menuItemOpen.Index = 0;
        this.menuItemOpen.Text = "Open";
        this.menuItemOpen.Click += new System.EventHandler(this.menuItemOpen_Click);
        //
        // menuItemRename
        //
        this.menuItemRename.Index = 1;
        this.menuItemRename.Text = "Rename";
        this.menuItemRename.Click += new System.EventHandler(this.menuItemRename_Click);
        //
        // menuItemRemove
        //
        this.menuItemRemove.Index = 2;
        this.menuItemRemove.Text = "Remove";
        this.menuItemRemove.Click += new System.EventHandler(this.menuItemRemove_Click);
        //
        // labelDecrypt
        //
        this.labelDecrypt.Location = new System.Drawing.Point(5, 401);
        this.labelDecrypt.Name = "labelDecrypt";
        this.labelDecrypt.Size = new System.Drawing.Size(267, 59);
        this.labelDecrypt.TabIndex = 31;
        this.labelDecrypt.Text = "Previous attempts to decrypt this message have failed.\r\nDecryption usually fails " + "when your private decryption key is not installed on the local computer.\r\nUse th" + "e Decrypt button to try again.";
        this.labelDecrypt.Visible = false;
        //
        // textSentOrReceived
        //
        this.textSentOrReceived.BackColor = System.Drawing.SystemColors.Control;
        this.textSentOrReceived.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textSentOrReceived.Location = new System.Drawing.Point(278, 6);
        this.textSentOrReceived.Name = "textSentOrReceived";
        this.textSentOrReceived.Size = new System.Drawing.Size(253, 13);
        this.textSentOrReceived.TabIndex = 34;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(194, 5);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(84, 14);
        this.label5.TabIndex = 35;
        this.label5.Text = "Sent/Received:";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butEncryptAndSend
        //
        this.butEncryptAndSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEncryptAndSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butEncryptAndSend.setAutosize(true);
        this.butEncryptAndSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEncryptAndSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEncryptAndSend.setCornerRadius(4F);
        this.butEncryptAndSend.Location = new System.Drawing.Point(659, 635);
        this.butEncryptAndSend.Name = "butEncryptAndSend";
        this.butEncryptAndSend.Size = new System.Drawing.Size(106, 25);
        this.butEncryptAndSend.TabIndex = 8;
        this.butEncryptAndSend.Text = "Encrypt and Send";
        this.butEncryptAndSend.Click += new System.EventHandler(this.butEncryptAndSend_Click);
        //
        // webBrowser
        //
        this.webBrowser.AllowNavigation = false;
        this.webBrowser.AllowWebBrowserDrop = false;
        this.webBrowser.Location = new System.Drawing.Point(226, 348);
        this.webBrowser.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowser.Name = "webBrowser";
        this.webBrowser.ScriptErrorsSuppressed = true;
        this.webBrowser.Size = new System.Drawing.Size(46, 25);
        this.webBrowser.TabIndex = 0;
        this.webBrowser.TabStop = false;
        this.webBrowser.Visible = false;
        this.webBrowser.WebBrowserShortcutsEnabled = false;
        //
        // butDecrypt
        //
        this.butDecrypt.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDecrypt.setAutosize(true);
        this.butDecrypt.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDecrypt.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDecrypt.setCornerRadius(4F);
        this.butDecrypt.Location = new System.Drawing.Point(8, 463);
        this.butDecrypt.Name = "butDecrypt";
        this.butDecrypt.Size = new System.Drawing.Size(75, 25);
        this.butDecrypt.TabIndex = 7;
        this.butDecrypt.Text = "Decrypt";
        this.butDecrypt.Visible = false;
        this.butDecrypt.Click += new System.EventHandler(this.butDecrypt_Click);
        //
        // buttonFuchsMailDMF
        //
        this.buttonFuchsMailDMF.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonFuchsMailDMF.setAutosize(true);
        this.buttonFuchsMailDMF.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonFuchsMailDMF.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonFuchsMailDMF.setCornerRadius(4F);
        this.buttonFuchsMailDMF.Location = new System.Drawing.Point(197, 156);
        this.buttonFuchsMailDMF.Name = "buttonFuchsMailDMF";
        this.buttonFuchsMailDMF.Size = new System.Drawing.Size(75, 22);
        this.buttonFuchsMailDMF.TabIndex = 30;
        this.buttonFuchsMailDMF.Text = "To DMF";
        this.buttonFuchsMailDMF.Visible = false;
        this.buttonFuchsMailDMF.Click += new System.EventHandler(this.buttonFuchsMailDMF_Click);
        //
        // buttonFuchsMailDSF
        //
        this.buttonFuchsMailDSF.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonFuchsMailDSF.setAutosize(true);
        this.buttonFuchsMailDSF.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonFuchsMailDSF.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonFuchsMailDSF.setCornerRadius(4F);
        this.buttonFuchsMailDSF.Location = new System.Drawing.Point(197, 128);
        this.buttonFuchsMailDSF.Name = "buttonFuchsMailDSF";
        this.buttonFuchsMailDSF.Size = new System.Drawing.Size(75, 22);
        this.buttonFuchsMailDSF.TabIndex = 29;
        this.buttonFuchsMailDSF.Text = "To DSF";
        this.buttonFuchsMailDSF.Visible = false;
        this.buttonFuchsMailDSF.Click += new System.EventHandler(this.buttonFuchsMailDSF_Click);
        //
        // butAttach
        //
        this.butAttach.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAttach.setAutosize(true);
        this.butAttach.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAttach.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAttach.setCornerRadius(4F);
        this.butAttach.Location = new System.Drawing.Point(612, 0);
        this.butAttach.Name = "butAttach";
        this.butAttach.Size = new System.Drawing.Size(75, 20);
        this.butAttach.TabIndex = 5;
        this.butAttach.Text = "Attach...";
        this.butAttach.Click += new System.EventHandler(this.butAttach_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(8, 635);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 26);
        this.butDelete.TabIndex = 11;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butSave
        //
        this.butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSave.setAutosize(true);
        this.butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSave.setCornerRadius(4F);
        this.butSave.Location = new System.Drawing.Point(278, 635);
        this.butSave.Name = "butSave";
        this.butSave.Size = new System.Drawing.Size(75, 25);
        this.butSave.TabIndex = 6;
        this.butSave.Text = "Save";
        this.butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // textBodyText
        //
        this.textBodyText.AcceptsTab = true;
        this.textBodyText.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.textBodyText.DetectUrls = false;
        this.textBodyText.Location = new System.Drawing.Point(278, 109);
        this.textBodyText.Name = "textBodyText";
        this.textBodyText.setQuickPasteType(OpenDentBusiness.QuickPasteType.Email);
        this.textBodyText.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textBodyText.Size = new System.Drawing.Size(649, 517);
        this.textBodyText.TabIndex = 1;
        this.textBodyText.Text = "";
        this.textBodyText.TextChanged += new System.EventHandler(this.textBodyText_TextChanged);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(771, 635);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(75, 25);
        this.butSend.TabIndex = 9;
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(852, 635);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 10;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butRawMessage
        //
        this.butRawMessage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRawMessage.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butRawMessage.setAutosize(true);
        this.butRawMessage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRawMessage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRawMessage.setCornerRadius(4F);
        this.butRawMessage.Location = new System.Drawing.Point(138, 635);
        this.butRawMessage.Name = "butRawMessage";
        this.butRawMessage.Size = new System.Drawing.Size(89, 26);
        this.butRawMessage.TabIndex = 36;
        this.butRawMessage.Text = "Raw Message";
        this.butRawMessage.Visible = false;
        this.butRawMessage.Click += new System.EventHandler(this.butRawMessage_Click);
        //
        // FormEmailMessageEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(941, 672);
        this.Controls.Add(this.butRawMessage);
        this.Controls.Add(this.butEncryptAndSend);
        this.Controls.Add(this.webBrowser);
        this.Controls.Add(this.textSentOrReceived);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.butDecrypt);
        this.Controls.Add(this.labelDecrypt);
        this.Controls.Add(this.buttonFuchsMailDMF);
        this.Controls.Add(this.buttonFuchsMailDSF);
        this.Controls.Add(this.listAttachments);
        this.Controls.Add(this.butAttach);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butSave);
        this.Controls.Add(this.panelTemplates);
        this.Controls.Add(this.textBodyText);
        this.Controls.Add(this.textMsgDateTime);
        this.Controls.Add(this.textFromAddress);
        this.Controls.Add(this.textToAddress);
        this.Controls.Add(this.textSubject);
        this.Controls.Add(this.labelSent);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.MinimumSize = new System.Drawing.Size(875, 575);
        this.Name = "FormEmailMessageEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit E-mail Message";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormEmailMessageEdit_Closing);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormEmailMessageEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormEmailMessageEdit_Load);
        this.panelTemplates.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formEmailMessageEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup,true))
        {
            butAdd.Enabled = false;
            butDeleteTemplate.Enabled = false;
        }
         
        Cursor = Cursors.WaitCursor;
        refreshAll();
        Cursor = Cursors.Default;
    }

    private void refreshAll() throws Exception {
        if (MessageCur.SentOrReceived != EmailSentOrReceived.Neither)
        {
            //sent or received (not composing)
            panelTemplates.Visible = false;
            textMsgDateTime.Text = MessageCur.MsgDateTime.ToString();
            textMsgDateTime.ForeColor = Color.Black;
            butAttach.Enabled = false;
            butEncryptAndSend.Enabled = false;
            //not allowed to send again.
            butSend.Enabled = false;
            //not allowed to send again.
            butSave.Enabled = false;
            //not allowed to save changes.
            butCancel.Text = "Close";
        }
         
        //When opening an email from FormEmailInbox, the email status will change to read automatically, and changing the text on the cancel button helps convey that to the user.
        textSentOrReceived.Text = MessageCur.SentOrReceived.ToString();
        textFromAddress.Text = MessageCur.FromAddress;
        textToAddress.Text = MessageCur.ToAddress;
        textSubject.Text = MessageCur.Subject;
        textBodyText.Text = MessageCur.BodyText;
        fillList();
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            buttonFuchsMailDMF.Visible = true;
            buttonFuchsMailDSF.Visible = true;
        }
         
        labelDecrypt.Visible = false;
        butDecrypt.Visible = false;
        if (MessageCur.SentOrReceived == EmailSentOrReceived.ReceivedEncrypted)
        {
            labelDecrypt.Visible = true;
            butDecrypt.Visible = true;
        }
         
        //For all email received types, we disable most of the controls and put the form into a mostly read-only state.
        //There is no reason a user should ever edit a received message. The user can copy the content and send a new email if needed (perhaps we will have forward capabilities in the future).
        if (MessageCur.SentOrReceived == EmailSentOrReceived.ReceivedEncrypted || MessageCur.SentOrReceived == EmailSentOrReceived.ReceivedDirect || MessageCur.SentOrReceived == EmailSentOrReceived.ReadDirect || MessageCur.SentOrReceived == EmailSentOrReceived.Received || MessageCur.SentOrReceived == EmailSentOrReceived.Read || MessageCur.SentOrReceived == EmailSentOrReceived.WebMailReceived || MessageCur.SentOrReceived == EmailSentOrReceived.WebMailRecdRead)
        {
            butRawMessage.Visible = true;
            textBodyText.ReadOnly = true;
            textBodyText.setSpellCheckIsEnabled(false);
            //Prevents slowness when resizing the window, because the spell checker runs each time the resize event is fired.
            //If an html body is received, then we display the body using a webbrowser control, so the user sees the message formatted as intended.
            if (MessageCur.BodyText.Trim().StartsWith("<html>"))
            {
                textBodyText.Visible = false;
                webBrowser.DocumentText = MessageCur.BodyText;
                webBrowser.Location = textBodyText.Location;
                webBrowser.Size = textBodyText.Size;
                webBrowser.Anchor = textBodyText.Anchor;
                webBrowser.Visible = true;
            }
             
        }
         
        fillAttachments();
        textBodyText.Select();
    }

    private void fillAttachments() throws Exception {
        listAttachments.Items.Clear();
        for (int i = 0;i < MessageCur.Attachments.Count;i++)
        {
            listAttachments.Items.Add(MessageCur.Attachments[i].DisplayedFileName);
        }
        if (MessageCur.Attachments.Count > 0)
        {
            listAttachments.SelectedIndex = 0;
        }
         
    }

    private void fillList() throws Exception {
        listTemplates.Items.Clear();
        for (int i = 0;i < EmailTemplates.getList().Length;i++)
        {
            listTemplates.Items.Add(EmailTemplates.getList()[i].Subject);
        }
    }

    private void listTemplates_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listTemplates.SelectedIndex == -1)
        {
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormEmailTemplateEdit FormE = new FormEmailTemplateEdit();
        FormE.ETcur = EmailTemplates.getList()[listTemplates.SelectedIndex];
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EmailTemplates.refreshCache();
        templatesChanged = true;
        fillList();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormEmailTemplateEdit FormE = new FormEmailTemplateEdit();
        FormE.IsNew = true;
        FormE.ETcur = new EmailTemplate();
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        EmailTemplates.refreshCache();
        templatesChanged = true;
        fillList();
    }

    private void butDeleteTemplate_Click(Object sender, System.EventArgs e) throws Exception {
        if (listTemplates.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Delete e-mail template?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        EmailTemplates.Delete(EmailTemplates.getList()[listTemplates.SelectedIndex]);
        EmailTemplates.refreshCache();
        templatesChanged = true;
        fillList();
    }

    private void butInsert_Click(Object sender, System.EventArgs e) throws Exception {
        if (listTemplates.SelectedIndex == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        if (messageChanged)
        {
            if (MessageBox.Show(Lan.g(this,"Replace exising e-mail text with text from the template?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
         
        textSubject.Text = EmailTemplates.getList()[listTemplates.SelectedIndex].Subject;
        textBodyText.Text = EmailTemplates.getList()[listTemplates.SelectedIndex].BodyText;
        messageChanged = false;
    }

    private void textBodyText_TextChanged(Object sender, System.EventArgs e) throws Exception {
        messageChanged = true;
    }

    private void butAttach_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog dlg = new OpenFileDialog();
        dlg.Multiselect = true;
        //PatCur=Patients.GetPat(MessageCur.PatNum);
        if (!StringSupport.equals(_patCur.ImageFolder, ""))
        {
            if (PrefC.getAtoZfolderUsed())
            {
                dlg.InitialDirectory = CodeBase.ODFileUtils.CombinePaths(ImageStore.getPreferredAtoZpath(), _patCur.ImageFolder.Substring(0, 1).ToUpper(), _patCur.ImageFolder);
            }
            else
            {
                //Use the OS default directory for this type of file viewer.
                dlg.InitialDirectory = "";
            } 
        }
         
        if (dlg.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        Random rnd = new Random();
        String newName = new String();
        EmailAttach attach;
        String attachPath = EmailMessages.getEmailAttachPath();
        try
        {
            for (int i = 0;i < dlg.FileNames.Length;i++)
            {
                //copy the file
                newName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + Path.GetExtension(dlg.FileNames[i]);
                File.Copy(dlg.FileNames[i], CodeBase.ODFileUtils.combinePaths(attachPath,newName));
                //create the attachment
                attach = new EmailAttach();
                attach.DisplayedFileName = Path.GetFileName(dlg.FileNames[i]);
                attach.ActualFileName = newName;
                MessageCur.Attachments.Add(attach);
            }
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }

        fillAttachments();
    }

    private void contextMenuAttachments_Popup(Object sender, EventArgs e) throws Exception {
        if (listAttachments.SelectedIndex == -1)
        {
            menuItemOpen.Enabled = false;
            menuItemRename.Enabled = false;
            menuItemRemove.Enabled = false;
        }
        else
        {
            menuItemOpen.Enabled = true;
            if (MessageCur.MsgDateTime.Year > 1880)
            {
                //if sent or received
                menuItemRename.Enabled = false;
                menuItemRemove.Enabled = false;
            }
            else
            {
                //composing
                menuItemRename.Enabled = true;
                menuItemRemove.Enabled = true;
            } 
        } 
    }

    private void menuItemOpen_Click(Object sender, EventArgs e) throws Exception {
        openFile();
    }

    private void menuItemRename_Click(Object sender, EventArgs e) throws Exception {
        InputBox input = new InputBox(Lan.g(this,"Filename"));
        input.textResult.Text = MessageCur.Attachments[listAttachments.SelectedIndex].DisplayedFileName;
        input.ShowDialog();
        if (input.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        MessageCur.Attachments[listAttachments.SelectedIndex].DisplayedFileName = input.textResult.Text;
        fillAttachments();
    }

    private void menuItemRemove_Click(Object sender, EventArgs e) throws Exception {
        MessageCur.Attachments.RemoveAt(listAttachments.SelectedIndex);
        fillAttachments();
    }

    private void listAttachments_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (listAttachments.SelectedIndex == -1)
        {
            return ;
        }
         
        openFile();
    }

    private void openFile() throws Exception {
        String strFilePathAttach = CodeBase.ODFileUtils.CombinePaths(EmailMessages.getEmailAttachPath(), MessageCur.Attachments[listAttachments.SelectedIndex].ActualFileName);
        try
        {
            if (EhrCCD.IsCcdEmailAttachment(MessageCur.Attachments[listAttachments.SelectedIndex]))
            {
                String strTextXml = File.ReadAllText(strFilePathAttach);
                if (EhrCCD.isCCD(strTextXml))
                {
                    Patient patEmail = null;
                    //Will be null for most email messages.
                    if (MessageCur.SentOrReceived == EmailSentOrReceived.ReadDirect || MessageCur.SentOrReceived == EmailSentOrReceived.ReceivedDirect)
                    {
                        patEmail = _patCur;
                    }
                     
                    //Only allow reconcile if received via Direct.
                    String strAlterateFilPathXslCCD = "";
                    for (int i = 0;i < MessageCur.Attachments.Count;i++)
                    {
                        //Try to find a corresponding stylesheet. This will only be used in the event that the default stylesheet cannot be loaded from the EHR dll.
                        if (StringSupport.equals(Path.GetExtension(MessageCur.Attachments[i].ActualFileName).ToLower(), ".xsl"))
                        {
                            strAlterateFilPathXslCCD = CodeBase.ODFileUtils.CombinePaths(EmailMessages.getEmailAttachPath(), MessageCur.Attachments[i].ActualFileName);
                            break;
                        }
                         
                    }
                    FormEhrSummaryOfCare.displayCCD(strTextXml,patEmail,strAlterateFilPathXslCCD);
                    return ;
                }
                 
            }
            else if (detectORU_R01Message(strFilePathAttach))
            {
                FormEhrLabOrderImport FormELOI = new FormEhrLabOrderImport();
                FormELOI.Hl7LabMessage = File.ReadAllText(strFilePathAttach);
                FormELOI.ShowDialog();
                return ;
            }
              
            //We have to create a copy of the file because the name is different.
            //There is also a high probability that the attachment no longer exists if
            //the A to Z folders are disabled, since the file will have originally been
            //placed in the temporary directory.
            String tempFile = CodeBase.ODFileUtils.CombinePaths(Path.GetTempPath(), MessageCur.Attachments[listAttachments.SelectedIndex].DisplayedFileName);
            File.Copy(strFilePathAttach, tempFile, true);
            Process.Start(tempFile);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    /**
    * Attempts to parse message and detects if it is an ORU_R01 HL7 message.  Returns false if it fails, or does not detect message type.
    */
    private boolean detectORU_R01Message(String strFilePathAttach) throws Exception {
        try
        {
            String[] ArrayMSHFields = File.ReadAllText(strFilePathAttach).Split(new String[]{ "\r\n" }, StringSplitOptions.RemoveEmptyEntries)[0].Split('|');
            if (!StringSupport.equals(ArrayMSHFields[8], "ORU^R01^ORU_R01"))
            {
                return false;
            }
             
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }

    private void listAttachments_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        //A right click also needs to select an items so that the context menu will work properly.
        if (e.Button == MouseButtons.Right)
        {
            int clickedIndex = listAttachments.IndexFromPoint(e.X, e.Y);
            if (clickedIndex != -1)
            {
                listAttachments.SelectedIndex = clickedIndex;
            }
             
        }
         
    }

    private void buttonFuchsMailDSF_Click(Object sender, EventArgs e) throws Exception {
        textSubject.Text = "Statement to DSF";
        textBodyText.Text = "For accounting, sent statment to skimom@springfielddental.net" + textBodyText.Text;
        textToAddress.Text = "skimom@springfielddental.net";
        messageChanged = false;
    }

    private void buttonFuchsMailDMF_Click(Object sender, EventArgs e) throws Exception {
        textToAddress.Text = "smilecouple@yahoo.com";
        textSubject.Text = "Statement to DMF";
        textBodyText.Text = "For accounting, sent statment to smilecouple@yahoo.com" + textBodyText.Text;
        messageChanged = false;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            //It will be deleted in the FormClosing() Event.
            if (MsgBox.show(this,true,"Delete this email?"))
            {
                EmailMessages.delete(MessageCur);
                DialogResult = DialogResult.OK;
            }
             
        } 
    }

    private void butRawMessage_Click(Object sender, EventArgs e) throws Exception {
        MsgBoxCopyPaste msgbox = new MsgBoxCopyPaste(MessageCur.RawEmailIn);
        msgbox.ShowDialog();
    }

    private void butDecrypt_Click(Object sender, EventArgs e) throws Exception {
        if (!EmailMessages.isDirectAddressTrusted(MessageCur.FromAddress))
        {
            //Not trusted yet.
            String strTrustMessage = Lan.g(this,"The sender address must be added to your trusted addresses before you can decrypt the email") + ". " + Lan.g(this,"Add") + " " + MessageCur.FromAddress + " " + Lan.g(this,"to trusted addresses") + "?";
            if (MessageBox.Show(strTrustMessage, "", MessageBoxButtons.OKCancel) == DialogResult.OK)
            {
                Cursor = Cursors.WaitCursor;
                EmailMessages.tryAddTrustDirect(MessageCur.FromAddress);
                Cursor = Cursors.Default;
                if (!EmailMessages.isDirectAddressTrusted(MessageCur.FromAddress))
                {
                    MsgBox.show(this,"Failed to trust sender because a valid certificate could not be located.");
                    return ;
                }
                 
            }
             
        }
         
        Cursor = Cursors.WaitCursor;
        EmailAddress emailAddress = getEmailAddress();
        try
        {
            MessageCur = EmailMessages.processRawEmailMessage(MessageCur.BodyText,MessageCur.EmailMessageNum,emailAddress);
            //If decryption is successful, sets status to ReceivedDirect.
            //The Direct message was decrypted.
            EmailMessages.updateSentOrReceivedRead(MessageCur);
            //Mark read, because we are already viewing the message within the current window.
            refreshAll();
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Decryption failed.") + "\r\n" + ex.Message);
        }

        //Error=InvalidEncryption: means that someone used the wrong certificate when sending the email to this inbox, and we tried to decrypt with a different certificate.
        //Error=NoTrustedRecipients: means the sender is not added to the trust anchors in mmc.
        Cursor = Cursors.Default;
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        //this will not be available if already sent.
        saveMsg();
        DialogResult = DialogResult.OK;
    }

    private void saveMsg() throws Exception {
        //allowed to save message with invalid fields, so no validation here.  Only validate when sending.
        MessageCur.FromAddress = textFromAddress.Text;
        MessageCur.ToAddress = textToAddress.Text;
        MessageCur.Subject = textSubject.Text;
        MessageCur.BodyText = textBodyText.Text;
        MessageCur.MsgDateTime = DateTime.Now;
        //Notice that SentOrReceived does not change here.
        if (IsNew)
        {
            EmailMessages.insert(MessageCur);
        }
        else
        {
            EmailMessages.update(MessageCur);
        } 
    }

    private EmailAddress getEmailAddress() throws Exception {
        if (_patCur == null)
        {
            return EmailAddresses.GetByClinic(0);
        }
         
        return EmailAddresses.getByClinic(_patCur.ClinicNum);
    }

    //can happen if sending deposit slip by email
    //gets the practice default address
    private void butEncryptAndSend_Click(Object sender, EventArgs e) throws Exception {
        //this will not be available if already sent.
        if (StringSupport.equals(textFromAddress.Text, "") || StringSupport.equals(textToAddress.Text, ""))
        {
            MessageBox.Show("Addresses not allowed to be blank.");
            return ;
        }
         
        EmailAddress emailAddressFrom = getEmailAddress();
        if (StringSupport.equals(emailAddressFrom.SMTPserver, ""))
        {
            MsgBox.show(this,"The email address in email setup must have an SMTP server.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        MessageCur.SentOrReceived = EmailSentOrReceived.SentDirect;
        saveMsg();
        try
        {
            String strErrors = EmailMessages.sendEmailDirect(MessageCur,emailAddressFrom);
            if (!StringSupport.equals(strErrors, ""))
            {
                Cursor = Cursors.Default;
                MessageBox.Show(strErrors);
                return ;
            }
            else
            {
                MsgBox.show(this,"Sent");
            } 
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    /**
    * 
    */
    private void butSend_Click(Object sender, System.EventArgs e) throws Exception {
        //this will not be available if already sent.
        if (StringSupport.equals(textFromAddress.Text, "") || StringSupport.equals(textToAddress.Text, ""))
        {
            MessageBox.Show("Addresses not allowed to be blank.");
            return ;
        }
         
        if (EhrCCD.hasCcdEmailAttachment(MessageCur))
        {
            MsgBox.show(this,"The email has a summary of care attachment which may contain sensitive patient data.  Use the Encrypt and Send button instead.");
            return ;
        }
         
        EmailAddress emailAddress = getEmailAddress();
        if (StringSupport.equals(emailAddress.SMTPserver, ""))
        {
            MsgBox.show(this,"The email address in email setup must have an SMTP server.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        MessageCur.SentOrReceived = EmailSentOrReceived.Sent;
        saveMsg();
        try
        {
            EmailMessages.sendEmailUnsecure(MessageCur,emailAddress);
            MsgBox.show(this,"Sent");
        }
        catch (Exception ex)
        {
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        Cursor = Cursors.Default;
        //MessageCur.MsgDateTime=DateTime.Now;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formEmailMessageEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (templatesChanged)
        {
            DataValid.setInvalid(InvalidType.Email);
        }
         
    }

    private void formEmailMessageEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            EmailMessages.delete(MessageCur);
        }
         
    }

}


