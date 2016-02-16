//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:50 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormRpStatement;
import OpenDental.FormStatementOptions;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.AccountModules;
import OpenDentBusiness.Adjustments;
import OpenDentBusiness.Document;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.Statements;

/**
* 
*/
public class FormStatementOptions  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    // Required designer variable.
    private System.Windows.Forms.CheckBox checkHidePayment = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textNote;
    private OpenDental.UI.Button buttonFuchs1;
    private OpenDental.UI.Button buttonFuchs2;
    private OpenDental.UI.Button buttonFuchs3;
    private GroupBox groupFuchs = new GroupBox();
    private OpenDental.UI.Button butToday;
    private OpenDental.UI.Button butDatesAll;
    private OpenDental.UI.Button but90days;
    private OpenDental.UI.Button but45days;
    private Label labelEndDate = new Label();
    private Label labelStartDate = new Label();
    private OpenDental.ODtextBox textNoteBold;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private ListBox listMode = new ListBox();
    private CheckBox checkSinglePatient = new CheckBox();
    private CheckBox checkIntermingled = new CheckBox();
    private GroupBox groupDateRange = new GroupBox();
    private Label label4 = new Label();
    private CheckBox checkIsSent = new CheckBox();
    public Statement StmtCur;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butEmail;
    private OpenDental.UI.Button butPreview;
    private boolean initiallySent = new boolean();
    private TextBox textDateEnd = new TextBox();
    private TextBox textDateStart = new TextBox();
    private TextBox textDate = new TextBox();
    /**
    * This will be null for ordinary edits.  But sometimes this window is used to edit bulk statements.  In that case, this list contains the statements being edited.  Must contain at least one item.
    */
    public List<Statement> StmtList = new List<Statement>();
    private CheckBox checkIsReceipt = new CheckBox();
    private GroupBox groupInvoice = new GroupBox();
    private CheckBox checkIsInvoiceCopy = new CheckBox();
    private CheckBox checkIsInvoice = new CheckBox();
    private TextBox textInvoiceNum = new TextBox();
    private Label label5 = new Label();
    /**
    * js This is superfluous and should be removed some day.
    */
    private int electIndex = new int();
    /**
    * 
    */
    public FormStatementOptions() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormStatementOptions.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.checkHidePayment = new System.Windows.Forms.CheckBox();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.label3 = new System.Windows.Forms.Label();
        this.textNote = new OpenDental.ODtextBox();
        this.buttonFuchs1 = new OpenDental.UI.Button();
        this.buttonFuchs2 = new OpenDental.UI.Button();
        this.buttonFuchs3 = new OpenDental.UI.Button();
        this.groupFuchs = new System.Windows.Forms.GroupBox();
        this.butToday = new OpenDental.UI.Button();
        this.butDatesAll = new OpenDental.UI.Button();
        this.but90days = new OpenDental.UI.Button();
        this.but45days = new OpenDental.UI.Button();
        this.labelEndDate = new System.Windows.Forms.Label();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.textNoteBold = new OpenDental.ODtextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.listMode = new System.Windows.Forms.ListBox();
        this.checkIntermingled = new System.Windows.Forms.CheckBox();
        this.checkSinglePatient = new System.Windows.Forms.CheckBox();
        this.groupDateRange = new System.Windows.Forms.GroupBox();
        this.textDateEnd = new System.Windows.Forms.TextBox();
        this.textDateStart = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.checkIsSent = new System.Windows.Forms.CheckBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butEmail = new OpenDental.UI.Button();
        this.butPreview = new OpenDental.UI.Button();
        this.textDate = new System.Windows.Forms.TextBox();
        this.checkIsReceipt = new System.Windows.Forms.CheckBox();
        this.groupInvoice = new System.Windows.Forms.GroupBox();
        this.textInvoiceNum = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.checkIsInvoiceCopy = new System.Windows.Forms.CheckBox();
        this.checkIsInvoice = new System.Windows.Forms.CheckBox();
        this.groupFuchs.SuspendLayout();
        this.groupDateRange.SuspendLayout();
        this.groupInvoice.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(606, 536);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(514, 536);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // checkHidePayment
        //
        this.checkHidePayment.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkHidePayment.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkHidePayment.Location = new System.Drawing.Point(1, 120);
        this.checkHidePayment.Name = "checkHidePayment";
        this.checkHidePayment.Size = new System.Drawing.Size(158, 20);
        this.checkHidePayment.TabIndex = 11;
        this.checkHidePayment.Text = "Hide payment options";
        this.checkHidePayment.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(55, 290);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(90, 17);
        this.label3.TabIndex = 13;
        this.label3.Text = "Note";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(146, 290);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Statement);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(462, 147);
        this.textNote.TabIndex = 14;
        //
        // buttonFuchs1
        //
        this.buttonFuchs1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonFuchs1.setAutosize(true);
        this.buttonFuchs1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonFuchs1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonFuchs1.setCornerRadius(4F);
        this.buttonFuchs1.Location = new System.Drawing.Point(6, 19);
        this.buttonFuchs1.Name = "buttonFuchs1";
        this.buttonFuchs1.Size = new System.Drawing.Size(86, 24);
        this.buttonFuchs1.TabIndex = 18;
        this.buttonFuchs1.Text = "Ins. Not Paid";
        this.buttonFuchs1.Visible = false;
        this.buttonFuchs1.Click += new System.EventHandler(this.buttonFuchs1_Click);
        //
        // buttonFuchs2
        //
        this.buttonFuchs2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonFuchs2.setAutosize(true);
        this.buttonFuchs2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonFuchs2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonFuchs2.setCornerRadius(4F);
        this.buttonFuchs2.Location = new System.Drawing.Point(6, 46);
        this.buttonFuchs2.Name = "buttonFuchs2";
        this.buttonFuchs2.Size = new System.Drawing.Size(103, 24);
        this.buttonFuchs2.TabIndex = 19;
        this.buttonFuchs2.Text = "Ins. Paid, Bal. Left";
        this.buttonFuchs2.Visible = false;
        this.buttonFuchs2.Click += new System.EventHandler(this.buttonFuchs2_Click);
        //
        // buttonFuchs3
        //
        this.buttonFuchs3.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonFuchs3.setAutosize(true);
        this.buttonFuchs3.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonFuchs3.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonFuchs3.setCornerRadius(4F);
        this.buttonFuchs3.Location = new System.Drawing.Point(6, 73);
        this.buttonFuchs3.Name = "buttonFuchs3";
        this.buttonFuchs3.Size = new System.Drawing.Size(117, 24);
        this.buttonFuchs3.TabIndex = 20;
        this.buttonFuchs3.Text = "Ins. Paid, Credit Left";
        this.buttonFuchs3.Visible = false;
        this.buttonFuchs3.Click += new System.EventHandler(this.buttonFuchs3_Click);
        //
        // groupFuchs
        //
        this.groupFuchs.Controls.Add(this.buttonFuchs1);
        this.groupFuchs.Controls.Add(this.buttonFuchs3);
        this.groupFuchs.Controls.Add(this.buttonFuchs2);
        this.groupFuchs.Location = new System.Drawing.Point(297, 81);
        this.groupFuchs.Name = "groupFuchs";
        this.groupFuchs.Size = new System.Drawing.Size(129, 105);
        this.groupFuchs.TabIndex = 21;
        this.groupFuchs.TabStop = false;
        this.groupFuchs.Text = "Fuchs hidden options";
        this.groupFuchs.Visible = false;
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(75, 64);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(77, 24);
        this.butToday.TabIndex = 229;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // butDatesAll
        //
        this.butDatesAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDatesAll.setAutosize(true);
        this.butDatesAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDatesAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDatesAll.setCornerRadius(4F);
        this.butDatesAll.Location = new System.Drawing.Point(75, 142);
        this.butDatesAll.Name = "butDatesAll";
        this.butDatesAll.Size = new System.Drawing.Size(77, 24);
        this.butDatesAll.TabIndex = 228;
        this.butDatesAll.Text = "All Dates";
        this.butDatesAll.Click += new System.EventHandler(this.butDatesAll_Click);
        //
        // but90days
        //
        this.but90days.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but90days.setAutosize(true);
        this.but90days.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but90days.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but90days.setCornerRadius(4F);
        this.but90days.Location = new System.Drawing.Point(75, 116);
        this.but90days.Name = "but90days";
        this.but90days.Size = new System.Drawing.Size(77, 24);
        this.but90days.TabIndex = 227;
        this.but90days.Text = "Last 90 Days";
        this.but90days.Click += new System.EventHandler(this.but90days_Click);
        //
        // but45days
        //
        this.but45days.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but45days.setAutosize(true);
        this.but45days.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but45days.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but45days.setCornerRadius(4F);
        this.but45days.Location = new System.Drawing.Point(75, 90);
        this.but45days.Name = "but45days";
        this.but45days.Size = new System.Drawing.Size(77, 24);
        this.but45days.TabIndex = 226;
        this.but45days.Text = "Last 45 Days";
        this.but45days.Click += new System.EventHandler(this.but45days_Click);
        //
        // labelEndDate
        //
        this.labelEndDate.Location = new System.Drawing.Point(3, 44);
        this.labelEndDate.Name = "labelEndDate";
        this.labelEndDate.Size = new System.Drawing.Size(69, 14);
        this.labelEndDate.TabIndex = 222;
        this.labelEndDate.Text = "End Date";
        this.labelEndDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(3, 21);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(69, 14);
        this.labelStartDate.TabIndex = 221;
        this.labelStartDate.Text = "Start Date";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textNoteBold
        //
        this.textNoteBold.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textNoteBold.ForeColor = System.Drawing.Color.DarkRed;
        this.textNoteBold.Location = new System.Drawing.Point(146, 443);
        this.textNoteBold.Multiline = true;
        this.textNoteBold.Name = "textNoteBold";
        this.textNoteBold.setQuickPasteType(OpenDentBusiness.QuickPasteType.Statement);
        this.textNoteBold.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNoteBold.Size = new System.Drawing.Size(462, 74);
        this.textNoteBold.TabIndex = 231;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(35, 444);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(110, 17);
        this.label1.TabIndex = 230;
        this.label1.Text = "Bold Note";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(64, 62);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(81, 14);
        this.label2.TabIndex = 232;
        this.label2.Text = "Mode";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listMode
        //
        this.listMode.FormattingEnabled = true;
        this.listMode.Location = new System.Drawing.Point(146, 60);
        this.listMode.Name = "listMode";
        this.listMode.Size = new System.Drawing.Size(113, 56);
        this.listMode.TabIndex = 233;
        this.listMode.Click += new System.EventHandler(this.listMode_Click);
        //
        // checkIntermingled
        //
        this.checkIntermingled.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIntermingled.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIntermingled.Location = new System.Drawing.Point(1, 162);
        this.checkIntermingled.Name = "checkIntermingled";
        this.checkIntermingled.Size = new System.Drawing.Size(158, 20);
        this.checkIntermingled.TabIndex = 234;
        this.checkIntermingled.Text = "Intermingle family members";
        this.checkIntermingled.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIntermingled.Click += new System.EventHandler(this.checkIntermingled_Click);
        //
        // checkSinglePatient
        //
        this.checkSinglePatient.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkSinglePatient.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkSinglePatient.Location = new System.Drawing.Point(1, 141);
        this.checkSinglePatient.Name = "checkSinglePatient";
        this.checkSinglePatient.Size = new System.Drawing.Size(158, 20);
        this.checkSinglePatient.TabIndex = 235;
        this.checkSinglePatient.Text = "Single patient only";
        this.checkSinglePatient.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkSinglePatient.Click += new System.EventHandler(this.checkSinglePatient_Click);
        //
        // groupDateRange
        //
        this.groupDateRange.Controls.Add(this.textDateEnd);
        this.groupDateRange.Controls.Add(this.labelStartDate);
        this.groupDateRange.Controls.Add(this.textDateStart);
        this.groupDateRange.Controls.Add(this.labelEndDate);
        this.groupDateRange.Controls.Add(this.but45days);
        this.groupDateRange.Controls.Add(this.but90days);
        this.groupDateRange.Controls.Add(this.butDatesAll);
        this.groupDateRange.Controls.Add(this.butToday);
        this.groupDateRange.Location = new System.Drawing.Point(446, 12);
        this.groupDateRange.Name = "groupDateRange";
        this.groupDateRange.Size = new System.Drawing.Size(162, 174);
        this.groupDateRange.TabIndex = 236;
        this.groupDateRange.TabStop = false;
        this.groupDateRange.Text = "Date Range";
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(75, 41);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 246;
        this.textDateEnd.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textDateEnd_KeyDown);
        this.textDateEnd.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textDateEnd_KeyPress);
        this.textDateEnd.Validating += new System.ComponentModel.CancelEventHandler(this.textDateEnd_Validating);
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(75, 18);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 245;
        this.textDateStart.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textDateStart_KeyDown);
        this.textDateStart.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textDateStart_KeyPress);
        this.textDateStart.Validating += new System.ComponentModel.CancelEventHandler(this.textDateStart_Validating);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(67, 17);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(77, 14);
        this.label4.TabIndex = 237;
        this.label4.Text = "Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsSent
        //
        this.checkIsSent.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsSent.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsSent.Location = new System.Drawing.Point(1, 39);
        this.checkIsSent.Name = "checkIsSent";
        this.checkIsSent.Size = new System.Drawing.Size(159, 18);
        this.checkIsSent.TabIndex = 239;
        this.checkIsSent.Text = "Sent";
        this.checkIsSent.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsSent.Click += new System.EventHandler(this.checkIsSent_Click);
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
        this.butDelete.Location = new System.Drawing.Point(40, 536);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(79, 24);
        this.butDelete.TabIndex = 240;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(195, 536);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(79, 24);
        this.butPrint.TabIndex = 241;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butEmail
        //
        this.butEmail.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEmail.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEmail.setAutosize(true);
        this.butEmail.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEmail.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEmail.setCornerRadius(4F);
        this.butEmail.Image = Resources.getemail1();
        this.butEmail.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEmail.Location = new System.Drawing.Point(280, 536);
        this.butEmail.Name = "butEmail";
        this.butEmail.Size = new System.Drawing.Size(79, 24);
        this.butEmail.TabIndex = 242;
        this.butEmail.Text = "E-mail";
        this.butEmail.Click += new System.EventHandler(this.butEmail_Click);
        //
        // butPreview
        //
        this.butPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPreview.setAutosize(true);
        this.butPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreview.setCornerRadius(4F);
        this.butPreview.Image = Resources.getprintPreview20();
        this.butPreview.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPreview.Location = new System.Drawing.Point(365, 536);
        this.butPreview.Name = "butPreview";
        this.butPreview.Size = new System.Drawing.Size(79, 24);
        this.butPreview.TabIndex = 243;
        this.butPreview.Text = "View";
        this.butPreview.Click += new System.EventHandler(this.butPreview_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(146, 15);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(77, 20);
        this.textDate.TabIndex = 244;
        this.textDate.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textDate_KeyDown);
        this.textDate.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textDate_KeyPress);
        this.textDate.Validating += new System.ComponentModel.CancelEventHandler(this.textDate_Validating);
        //
        // checkIsReceipt
        //
        this.checkIsReceipt.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsReceipt.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsReceipt.Location = new System.Drawing.Point(1, 183);
        this.checkIsReceipt.Name = "checkIsReceipt";
        this.checkIsReceipt.Size = new System.Drawing.Size(158, 20);
        this.checkIsReceipt.TabIndex = 245;
        this.checkIsReceipt.Text = "Receipt";
        this.checkIsReceipt.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupInvoice
        //
        this.groupInvoice.Controls.Add(this.textInvoiceNum);
        this.groupInvoice.Controls.Add(this.label5);
        this.groupInvoice.Controls.Add(this.checkIsInvoiceCopy);
        this.groupInvoice.Controls.Add(this.checkIsInvoice);
        this.groupInvoice.Location = new System.Drawing.Point(12, 202);
        this.groupInvoice.Name = "groupInvoice";
        this.groupInvoice.Size = new System.Drawing.Size(247, 82);
        this.groupInvoice.TabIndex = 247;
        this.groupInvoice.TabStop = false;
        this.groupInvoice.Text = "Invoice";
        //
        // textInvoiceNum
        //
        this.textInvoiceNum.Location = new System.Drawing.Point(134, 55);
        this.textInvoiceNum.Name = "textInvoiceNum";
        this.textInvoiceNum.ReadOnly = true;
        this.textInvoiceNum.Size = new System.Drawing.Size(108, 20);
        this.textInvoiceNum.TabIndex = 250;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(13, 57);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(120, 14);
        this.label5.TabIndex = 249;
        this.label5.Text = "Invoice Number";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsInvoiceCopy
        //
        this.checkIsInvoiceCopy.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsInvoiceCopy.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsInvoiceCopy.Location = new System.Drawing.Point(5, 32);
        this.checkIsInvoiceCopy.Name = "checkIsInvoiceCopy";
        this.checkIsInvoiceCopy.Size = new System.Drawing.Size(142, 20);
        this.checkIsInvoiceCopy.TabIndex = 248;
        this.checkIsInvoiceCopy.Text = "Invoice Copy";
        this.checkIsInvoiceCopy.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsInvoice
        //
        this.checkIsInvoice.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsInvoice.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsInvoice.Location = new System.Drawing.Point(5, 12);
        this.checkIsInvoice.Name = "checkIsInvoice";
        this.checkIsInvoice.Size = new System.Drawing.Size(142, 20);
        this.checkIsInvoice.TabIndex = 247;
        this.checkIsInvoice.Text = "Invoice";
        this.checkIsInvoice.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsInvoice.Click += new System.EventHandler(this.checkIsInvoice_Click);
        //
        // FormStatementOptions
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(709, 576);
        this.Controls.Add(this.groupInvoice);
        this.Controls.Add(this.checkIsReceipt);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butPreview);
        this.Controls.Add(this.butEmail);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.checkIsSent);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.groupDateRange);
        this.Controls.Add(this.checkSinglePatient);
        this.Controls.Add(this.checkIntermingled);
        this.Controls.Add(this.listMode);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textNoteBold);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupFuchs);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.checkHidePayment);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormStatementOptions";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Statement";
        this.Load += new System.EventHandler(this.FormStatementOptions_Load);
        this.groupFuchs.ResumeLayout(false);
        this.groupDateRange.ResumeLayout(false);
        this.groupDateRange.PerformLayout();
        this.groupInvoice.ResumeLayout(false);
        this.groupInvoice.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formStatementOptions_Load(Object sender, System.EventArgs e) throws Exception {
        if (StmtList == null)
        {
            if (StmtCur.IsSent)
            {
                checkIsSent.Checked = true;
                initiallySent = true;
                setEnabled(false);
            }
             
            textDate.Text = StmtCur.DateSent.ToShortDateString();
            listMode.Items.Clear();
            for (int i = 0;i < Enum.GetNames(StatementMode.class).Length;i++)
            {
                listMode.Items.Add(Lan.g("enumStatementMode", Enum.GetNames(StatementMode.class)[i]));
                if (((Enum)StmtCur.Mode_).ordinal() == i)
                {
                    listMode.SelectedIndex = i;
                }
                 
                if (StringSupport.equals(Enum.GetNames(StatementMode.class)[i], "Electronic"))
                {
                    electIndex = i;
                }
                 
            }
            checkHidePayment.Checked = StmtCur.HidePayment;
            checkSinglePatient.Checked = StmtCur.SinglePatient;
            checkIntermingled.Checked = StmtCur.Intermingled;
            checkIsReceipt.Checked = StmtCur.IsReceipt;
            if (StmtCur.IsInvoice)
            {
                //If they got here with drop down menu invoice item.
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
                {
                    checkIsInvoiceCopy.Visible = false;
                }
                 
                checkIsInvoice.Checked = true;
                checkIsInvoiceCopy.Checked = StmtCur.IsInvoiceCopy;
                textInvoiceNum.Text = StmtCur.StatementNum.ToString();
                groupDateRange.Visible = false;
                checkIsReceipt.Visible = false;
                checkIntermingled.Visible = false;
                checkHidePayment.Checked = StmtCur.HidePayment;
            }
            else
            {
                groupInvoice.Visible = false;
            } 
            if (StmtCur.DateRangeFrom.Year > 1880)
            {
                textDateStart.Text = StmtCur.DateRangeFrom.ToShortDateString();
            }
             
            if (StmtCur.DateRangeTo.Year < 2100)
            {
                textDateEnd.Text = StmtCur.DateRangeTo.ToShortDateString();
            }
             
            if (PrefC.getBool(PrefName.FuchsOptionsOn))
            {
                textDateStart.Text = DateTime.Today.AddDays(-90).ToShortDateString();
                textDateEnd.Text = DateTime.Today.ToShortDateString();
                listMode.SelectedIndex = 0;
                checkIntermingled.Checked = true;
                groupFuchs.Visible = true;
                buttonFuchs1.Visible = true;
                buttonFuchs2.Visible = true;
                buttonFuchs3.Visible = true;
            }
             
            textNote.Text = StmtCur.Note;
            textNoteBold.Text = StmtCur.NoteBold;
        }
        else
        {
            //bulk edit
            //DateSent-------------------------------------------------------------------------------------
            textDate.Text = "?";
            boolean allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].DateSent != StmtList[i].DateSent)
                {
                    //if any are different from the first element
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                textDate.Text = StmtList[0].DateSent.ToShortDateString();
            }
             
            //IsSent----------------------------------------------------------------------------------------
            checkIsSent.ThreeState = true;
            checkIsSent.CheckState = CheckState.Indeterminate;
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].IsSent != StmtList[i].IsSent)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                checkIsSent.ThreeState = false;
                checkIsSent.CheckState = CheckState.Unchecked;
                checkIsSent.Checked = StmtList[0].IsSent;
            }
             
            //Mode------------------------------------------------------------------------------------------
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].Mode_ != StmtList[i].Mode_)
                {
                    allSame = false;
                }
                 
            }
            listMode.Items.Clear();
            for (int i = 0;i < Enum.GetNames(StatementMode.class).Length;i++)
            {
                listMode.Items.Add(Lan.g("enumStatementMode", Enum.GetNames(StatementMode.class)[i]));
                if (allSame && (int)StmtList[0].Mode_ == i)
                {
                    listMode.SelectedIndex = i;
                }
                 
            }
            //HidePayment------------------------------------------------------------------------------------
            checkHidePayment.ThreeState = true;
            checkHidePayment.CheckState = CheckState.Indeterminate;
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].HidePayment != StmtList[i].HidePayment)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                checkHidePayment.ThreeState = false;
                checkHidePayment.CheckState = CheckState.Unchecked;
                checkHidePayment.Checked = StmtList[0].HidePayment;
            }
             
            //SinglePatient------------------------------------------------------------------------------------
            checkSinglePatient.ThreeState = true;
            checkSinglePatient.CheckState = CheckState.Indeterminate;
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].SinglePatient != StmtList[i].SinglePatient)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                checkSinglePatient.ThreeState = false;
                checkSinglePatient.CheckState = CheckState.Unchecked;
                checkSinglePatient.Checked = StmtList[0].SinglePatient;
            }
             
            //Intermingled----------------------------------------------------------------------------------------
            checkIntermingled.ThreeState = true;
            checkIntermingled.CheckState = CheckState.Indeterminate;
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].Intermingled != StmtList[i].Intermingled)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                checkIntermingled.ThreeState = false;
                checkIntermingled.CheckState = CheckState.Unchecked;
                checkIntermingled.Checked = StmtList[0].Intermingled;
            }
             
            //DateStart-------------------------------------------------------------------------------------
            textDateStart.Text = "?";
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].DateRangeFrom != StmtList[i].DateRangeFrom)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                if (StmtList[0].DateRangeFrom.Year < 1880)
                {
                    textDateStart.Text = "";
                }
                else
                {
                    textDateStart.Text = StmtList[0].DateRangeFrom.ToShortDateString();
                } 
            }
             
            //DateEnd-------------------------------------------------------------------------------------
            textDateEnd.Text = "?";
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].DateRangeTo != StmtList[i].DateRangeTo)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                if (StmtList[0].DateRangeTo.Year < 1880)
                {
                    textDateEnd.Text = "";
                }
                else
                {
                    textDateEnd.Text = StmtList[0].DateRangeTo.ToShortDateString();
                } 
            }
             
            //Note----------------------------------------------------------------------------------------
            textNote.Text = "?";
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].Note != StmtList[i].Note)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                textNote.Text = StmtList[0].Note;
            }
             
            //NoteBold----------------------------------------------------------------------------------------
            textNoteBold.Text = "?";
            allSame = true;
            for (int i = 0;i < StmtList.Count;i++)
            {
                if (StmtList[0].NoteBold != StmtList[i].NoteBold)
                {
                    allSame = false;
                }
                 
            }
            if (allSame)
            {
                textNoteBold.Text = StmtList[0].NoteBold;
            }
             
            butEmail.Enabled = false;
            butPrint.Enabled = false;
            butPreview.Enabled = false;
        } 
        Plugins.hookAddCode(this,"FormStatementOptions_Load_end");
    }

    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void but45days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-45).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void but90days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-90).ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void butDatesAll_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = "";
        textDateEnd.Text = DateTime.Today.ToShortDateString();
    }

    private void buttonFuchs1_Click(Object sender, EventArgs e) throws Exception {
        textNote.Text = "Your insurance has not yet paid for your claims, they are still pending. This is to keep you informed of the status of your account. Thank You " + textNote.Text;
    }

    private void buttonFuchs2_Click(Object sender, EventArgs e) throws Exception {
        textNote.Text = "Your insurance paid and the remaining balance is yours. Thank You! " + textNote.Text;
    }

    private void buttonFuchs3_Click(Object sender, EventArgs e) throws Exception {
        textNote.Text = "This credit is on your account. We look forward to seeing you on your next appointment! " + textNote.Text;
    }

    private void checkIsSent_Click(Object sender, EventArgs e) throws Exception {
        if (initiallySent && !checkIsSent.Checked)
        {
            //user unchecks the Sent box in order to edit
            if (!MsgBox.show(this,true,"Warning.  This will immediately delete the archived copy of the statement.  Continue anyway?"))
            {
                checkIsSent.Checked = true;
                return ;
            }
             
            setEnabled(true);
            if (StmtCur.IsInvoice)
            {
                checkIsInvoiceCopy.Checked = false;
            }
             
            //Delete the archived copy of the statement
            if (StmtCur.DocNum != 0)
            {
                Patient pat = Patients.getPat(StmtCur.PatNum);
                String patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
                List<Document> listdocs = new List<Document>();
                listdocs.Add(Documents.getByNum(StmtCur.DocNum));
                try
                {
                    ImageStore.DeleteDocuments(listdocs, patFolder);
                }
                catch (Exception ex)
                {
                    //Image could not be deleted, in use.
                    MessageBox.Show(this, ex.Message);
                    return ;
                }
            
            }
             
        }
        else if (StmtList == null && StmtCur.IsInvoice && checkIsSent.Checked)
        {
            checkIsInvoiceCopy.Checked = true;
        }
          
    }

    private void setEnabled(boolean boolval) throws Exception {
        textDate.Enabled = boolval;
        listMode.Enabled = boolval;
        checkHidePayment.Enabled = boolval;
        checkSinglePatient.Enabled = boolval;
        checkIntermingled.Enabled = boolval;
        checkIsReceipt.Enabled = boolval;
        groupDateRange.Enabled = boolval;
        textNote.Enabled = boolval;
        textNoteBold.Enabled = boolval;
        groupInvoice.Enabled = boolval;
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        if (StmtCur.DocNum != 0 && checkIsSent.Checked && !StmtCur.IsInvoice)
        {
            //Invoices are always recreated on the fly in order to show "Copy" when needed.
            //launch existing archive pdf. User can click print from within Acrobat.
            Cursor = Cursors.WaitCursor;
            Patient pat = Patients.getPat(StmtCur.PatNum);
            String patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
            Process.Start(ImageStore.getFilePath(Documents.getByNum(StmtCur.DocNum),patFolder));
            Cursor = Cursors.Default;
        }
        else
        {
            //was not initially sent, or else user has unchecked the sent box
            if (initiallySent && checkIsSent.Checked && StmtCur.DocNum == 0 && !StmtCur.IsInvoice)
            {
                //for invoice, we don't notify user that it's a recreation
                MsgBox.show(this,"There was no archived image of this statement.  The printout will be based on current data.");
            }
             
            //So create an archive
            if (listMode.SelectedIndex == ((Enum)StatementMode.Email).ordinal())
            {
                listMode.SelectedIndex = ((Enum)StatementMode.InPerson).ordinal();
            }
             
            checkIsSent.Checked = true;
            Cursor = Cursors.WaitCursor;
            if (!saveToDb())
            {
                Cursor = Cursors.Default;
                return ;
            }
             
            FormRpStatement FormST = new FormRpStatement();
            Family fam = Patients.getFamily(StmtCur.PatNum);
            Patient pat = fam.getPatient(StmtCur.PatNum);
            DataSet dataSet = AccountModules.getStatementDataSet(StmtCur);
            FormST.createStatementPdf(StmtCur,pat,fam,dataSet);
            FormST.printStatement(StmtCur,false,dataSet,fam,pat);
            if (StmtCur.IsInvoice && checkIsInvoiceCopy.Visible)
            {
                //for foreign countries
                StmtCur.IsInvoiceCopy = true;
                Statements.update(StmtCur);
            }
             
            Cursor = Cursors.Default;
        } 
        DialogResult = DialogResult.OK;
    }

    private void butEmail_Click(Object sender, EventArgs e) throws Exception {
        if (StmtCur.DocNum != 0 && checkIsSent.Checked)
        {
            //remail existing archive pdf?
            //or maybe tell user they can't do that?
            MsgBox.show(this,"Email was already sent.");
            return ;
        }
        else
        {
            //was not initially sent, or else user has unchecked the sent box
            //So create an archive
            if (listMode.SelectedIndex != ((Enum)StatementMode.Email).ordinal())
            {
                listMode.SelectedIndex = ((Enum)StatementMode.Email).ordinal();
            }
             
            checkIsSent.Checked = true;
            Cursor = Cursors.WaitCursor;
            if (!saveToDb())
            {
                return ;
            }
             
            FormRpStatement FormST = new FormRpStatement();
            Family fam = Patients.getFamily(StmtCur.PatNum);
            Patient pat = fam.getPatient(StmtCur.PatNum);
            DataSet dataSet = AccountModules.getStatementDataSet(StmtCur);
            FormST.createStatementPdf(StmtCur,pat,fam,dataSet);
            if (!createEmailMessage())
            {
                Cursor = Cursors.Default;
                return ;
            }
             
            if (StmtCur.IsInvoice && checkIsInvoiceCopy.Visible)
            {
                //for foreign countries
                StmtCur.IsInvoiceCopy = true;
                Statements.update(StmtCur);
            }
             
            Cursor = Cursors.Default;
        } 
        DialogResult = DialogResult.OK;
    }

    /**
    * Also displays the dialog for the email.  Must have already created and attached the pdf.  Returns false if it could not create the email.
    */
    private boolean createEmailMessage() throws Exception {
        String attachPath = EmailMessages.getEmailAttachPath();
        Random rnd = new Random();
        String fileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + ".pdf";
        String filePathAndName = CodeBase.ODFileUtils.combinePaths(attachPath,fileName);
        if (!PrefC.getAtoZfolderUsed())
        {
            MsgBox.show(this,"Could not create email because no AtoZ folder.");
            return false;
        }
         
        Patient pat = Patients.getPat(StmtCur.PatNum);
        String oldPath = CodeBase.ODFileUtils.combinePaths(ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath()),Documents.getByNum(StmtCur.DocNum).FileName);
        File.Copy(oldPath, filePathAndName);
        //
        //Process.Start(filePathAndName);
        EmailMessage message = Statements.getEmailMessageForStatement(StmtCur,pat);
        EmailAttach attach = new EmailAttach();
        attach.DisplayedFileName = "Statement.pdf";
        attach.ActualFileName = fileName;
        message.Attachments.Add(attach);
        FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
        FormE.IsNew = true;
        FormE.ShowDialog();
        if (FormE.DialogResult == DialogResult.OK)
        {
            return true;
        }
         
        return false;
    }

    private void butPreview_Click(Object sender, EventArgs e) throws Exception {
        if (StmtCur.DocNum != 0 && checkIsSent.Checked)
        {
            //initiallySent && checkIsSent.Checked){
            //launch existing archive pdf
            Cursor = Cursors.WaitCursor;
            Patient pat = Patients.getPat(StmtCur.PatNum);
            String patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
            if (!File.Exists(ImageStore.getFilePath(Documents.getByNum(StmtCur.DocNum),patFolder)))
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"File not found: " + Documents.getByNum(StmtCur.DocNum).FileName);
                return ;
            }
             
            Process.Start(ImageStore.getFilePath(Documents.getByNum(StmtCur.DocNum),patFolder));
            Cursor = Cursors.Default;
        }
        else
        {
            //was not initially sent, or else user has unchecked the sent box
            //No archive to use, so just preview on the fly
            if (initiallySent && checkIsSent.Checked && StmtCur.DocNum == 0)
            {
                MsgBox.show(this,"There was no archived image of this statement.  The preview will be based on current data.");
            }
             
            Cursor = Cursors.WaitCursor;
            if (!saveToDb())
            {
                Cursor = Cursors.Default;
                return ;
            }
             
            FormRpStatement FormST = new FormRpStatement();
            Family fam = Patients.getFamily(StmtCur.PatNum);
            Patient pat = fam.getPatient(StmtCur.PatNum);
            DataSet dataSet = AccountModules.getStatementDataSet(StmtCur);
            try
            {
                //Would throw exception if no printer is installed.
                FormST.printStatement(StmtCur,true,dataSet,fam,pat);
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"No printers installed.");
                Cursor = Cursors.Default;
                return ;
            }

            FormST.ShowDialog();
            Cursor = Cursors.Default;
        } 
    }

    private void textDate_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "fr-CA") || StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-CA"))
        {
            return ;
        }
         
        //because they use - in their regular dates which interferes with this feature.
        if (e.KeyChar != '+' && e.KeyChar != '-')
        {
            return ;
        }
         
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(textDate.Text);
        }
        catch (Exception __dummyCatchVar1)
        {
            return ;
        }

        int caret = textDate.SelectionStart;
        if (e.KeyChar == '+')
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyChar == '-')
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        textDate.Text = dateDisplayed.ToShortDateString();
        textDate.SelectionStart = caret;
        e.Handled = true;
    }

    private void textDate_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode != Keys.Up && e.KeyCode != Keys.Down)
        {
            return ;
        }
         
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(textDate.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            return ;
        }

        int caret = textDate.SelectionStart;
        if (e.KeyCode == Keys.Up)
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyCode == Keys.Down)
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        textDate.Text = dateDisplayed.ToShortDateString();
        textDate.SelectionStart = caret;
        e.Handled = true;
    }

    private void textDateStart_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "fr-CA") || StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-CA"))
        {
            return ;
        }
         
        //because they use - in their regular dates which interferes with this feature.
        if (e.KeyChar != '+' && e.KeyChar != '-')
        {
            return ;
        }
         
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(textDateStart.Text);
        }
        catch (Exception __dummyCatchVar3)
        {
            return ;
        }

        int caret = textDateStart.SelectionStart;
        if (e.KeyChar == '+')
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyChar == '-')
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        textDateStart.Text = dateDisplayed.ToShortDateString();
        textDateStart.SelectionStart = caret;
        e.Handled = true;
    }

    private void textDateStart_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode != Keys.Up && e.KeyCode != Keys.Down)
        {
            return ;
        }
         
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(textDateStart.Text);
        }
        catch (Exception __dummyCatchVar4)
        {
            return ;
        }

        int caret = textDateStart.SelectionStart;
        if (e.KeyCode == Keys.Up)
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyCode == Keys.Down)
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        textDateStart.Text = dateDisplayed.ToShortDateString();
        textDateStart.SelectionStart = caret;
        e.Handled = true;
    }

    private void textDateEnd_KeyPress(Object sender, KeyPressEventArgs e) throws Exception {
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "fr-CA") || StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-CA"))
        {
            return ;
        }
         
        //because they use - in their regular dates which interferes with this feature.
        if (e.KeyChar != '+' && e.KeyChar != '-')
        {
            return ;
        }
         
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(textDateEnd.Text);
        }
        catch (Exception __dummyCatchVar5)
        {
            return ;
        }

        int caret = textDateEnd.SelectionStart;
        if (e.KeyChar == '+')
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyChar == '-')
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        textDateEnd.Text = dateDisplayed.ToShortDateString();
        textDateEnd.SelectionStart = caret;
        e.Handled = true;
    }

    private void textDateEnd_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode != Keys.Up && e.KeyCode != Keys.Down)
        {
            return ;
        }
         
        DateTime dateDisplayed = new DateTime();
        try
        {
            dateDisplayed = DateTime.Parse(textDateEnd.Text);
        }
        catch (Exception __dummyCatchVar6)
        {
            return ;
        }

        int caret = textDateEnd.SelectionStart;
        if (e.KeyCode == Keys.Up)
        {
            dateDisplayed = dateDisplayed.AddDays(1);
        }
         
        if (e.KeyCode == Keys.Down)
        {
            dateDisplayed = dateDisplayed.AddDays(-1);
        }
         
        textDateEnd.Text = dateDisplayed.ToShortDateString();
        textDateEnd.SelectionStart = caret;
        e.Handled = true;
    }

    private void textDate_Validating(Object sender, CancelEventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textDate.Text, ""))
            {
                return ;
            }
             
            boolean allNums = true;
            for (int i = 0;i < textDate.Text.Length;i++)
            {
                if (!Char.IsNumber(textDate.Text, i))
                {
                    allNums = false;
                }
                 
            }
            if (StringSupport.equals(CultureInfo.CurrentCulture.TwoLetterISOLanguageName, "en"))
            {
                if (allNums)
                {
                    if (textDate.Text.Length == 6)
                    {
                        textDate.Text = textDate.Text.Substring(0, 2) + "/" + textDate.Text.Substring(2, 2) + "/" + textDate.Text.Substring(4, 2);
                    }
                    else if (Text.Length == 8)
                    {
                        textDate.Text = textDate.Text.Substring(0, 2) + "/" + textDate.Text.Substring(2, 2) + "/" + textDate.Text.Substring(4, 4);
                    }
                      
                }
                 
            }
             
            if (DateTime.Parse(Text).Year > 1880)
            {
                textDate.Text = DateTime.Parse(textDate.Text).ToString("d");
            }
             
        }
        catch (Exception __dummyCatchVar7)
        {
        }
    
    }

    private void textDateStart_Validating(Object sender, CancelEventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textDateStart.Text, ""))
            {
                return ;
            }
             
            boolean allNums = true;
            for (int i = 0;i < textDateStart.Text.Length;i++)
            {
                if (!Char.IsNumber(textDateStart.Text, i))
                {
                    allNums = false;
                }
                 
            }
            if (StringSupport.equals(CultureInfo.CurrentCulture.TwoLetterISOLanguageName, "en"))
            {
                if (allNums)
                {
                    if (textDateStart.Text.Length == 6)
                    {
                        textDateStart.Text = textDateStart.Text.Substring(0, 2) + "/" + textDateStart.Text.Substring(2, 2) + "/" + textDateStart.Text.Substring(4, 2);
                    }
                    else if (Text.Length == 8)
                    {
                        textDateStart.Text = textDateStart.Text.Substring(0, 2) + "/" + textDateStart.Text.Substring(2, 2) + "/" + textDateStart.Text.Substring(4, 4);
                    }
                      
                }
                 
            }
             
            if (DateTime.Parse(Text).Year > 1880)
            {
                textDateStart.Text = DateTime.Parse(textDateStart.Text).ToString("d");
            }
             
        }
        catch (Exception __dummyCatchVar8)
        {
        }
    
    }

    private void textDateEnd_Validating(Object sender, CancelEventArgs e) throws Exception {
        try
        {
            if (StringSupport.equals(textDateEnd.Text, ""))
            {
                return ;
            }
             
            boolean allNums = true;
            for (int i = 0;i < textDateEnd.Text.Length;i++)
            {
                if (!Char.IsNumber(textDateEnd.Text, i))
                {
                    allNums = false;
                }
                 
            }
            if (StringSupport.equals(CultureInfo.CurrentCulture.TwoLetterISOLanguageName, "en"))
            {
                if (allNums)
                {
                    if (textDateEnd.Text.Length == 6)
                    {
                        textDateEnd.Text = textDateEnd.Text.Substring(0, 2) + "/" + textDateEnd.Text.Substring(2, 2) + "/" + textDateEnd.Text.Substring(4, 2);
                    }
                    else if (Text.Length == 8)
                    {
                        textDateEnd.Text = textDateEnd.Text.Substring(0, 2) + "/" + textDateEnd.Text.Substring(2, 2) + "/" + textDateEnd.Text.Substring(4, 4);
                    }
                      
                }
                 
            }
             
            if (DateTime.Parse(Text).Year > 1880)
            {
                textDateEnd.Text = DateTime.Parse(textDateEnd.Text).ToString("d");
            }
             
        }
        catch (Exception __dummyCatchVar9)
        {
        }
    
    }

    private void listMode_Click(Object sender, EventArgs e) throws Exception {
        if (listMode.SelectedIndex == electIndex)
        {
            //Selected electronic mode. Automatically select intermingling family and remove that as a selection option.
            checkSinglePatient.Checked = false;
            checkSinglePatient.Enabled = false;
            checkIntermingled.Checked = true;
            checkIntermingled.Enabled = false;
        }
        else
        {
            checkSinglePatient.Enabled = true;
            checkIntermingled.Enabled = true;
        } 
    }

    private void checkSinglePatient_Click(Object sender, EventArgs e) throws Exception {
        if (checkSinglePatient.Checked)
        {
            checkSinglePatient.Checked = true;
            checkIntermingled.Checked = false;
        }
        else
        {
            if (StmtCur.IsInvoice)
            {
                checkSinglePatient.Checked = true;
            }
             
        } 
    }

    private void checkIntermingled_Click(Object sender, EventArgs e) throws Exception {
        if (checkIntermingled.Checked)
        {
            checkSinglePatient.Checked = false;
            checkIntermingled.Checked = true;
        }
         
    }

    private void checkIsInvoice_Click(Object sender, EventArgs e) throws Exception {
        if (StmtCur.IsInvoice)
        {
            checkIsInvoice.Checked = true;
        }
         
    }

    //don't let them uncheck it.
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (StmtList == null && StmtCur.getIsNew())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        Patient pat;
        String patFolder = new String();
        if (StmtList == null)
        {
            if (StmtCur.DocNum != 0)
            {
                //deleted the pdf
                pat = Patients.getPat(StmtCur.PatNum);
                patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
                List<Document> listdocs = new List<Document>();
                listdocs.Add(Documents.getByNum(StmtCur.DocNum));
                try
                {
                    ImageStore.DeleteDocuments(listdocs, patFolder);
                }
                catch (Exception ex)
                {
                    //Image could not be deleted, in use.
                    MessageBox.Show(this, ex.Message);
                    return ;
                }
            
            }
             
            Procedures.detachFromInvoice(StmtCur.StatementNum);
            Adjustments.detachFromInvoice(StmtCur.StatementNum);
            Statements.deleteObject(StmtCur);
        }
        else
        {
            for (int i = 0;i < StmtList.Count;i++)
            {
                //bulk edit
                if (StmtList[i].DocNum != 0)
                {
                    //deleted the pdf
                    pat = Patients.GetPat(StmtList[i].PatNum);
                    patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
                    List<Document> listdocs = new List<Document>();
                    listdocs.Add(Documents.GetByNum(StmtList[i].DocNum));
                    try
                    {
                        ImageStore.DeleteDocuments(listdocs, patFolder);
                    }
                    catch (Exception ex)
                    {
                        //Image could not be deleted, in use.
                        MessageBox.Show(this, ex.Message);
                        return ;
                    }
                
                }
                 
                Statements.DeleteObject(StmtList[i]);
            }
        } 
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveToDb())
        {
            return ;
        }
         
        //if(StmtCur.Mode_==StatementMode.Email){
        //}
        DialogResult = DialogResult.OK;
    }

    private boolean saveToDb() throws Exception {
        boolean isError = new boolean();
        //Validate Date-------------------------------------------------------------------------------
        isError = false;
        if (StringSupport.equals(textDate.Text, ""))
        {
            //not allowed to be blank.  Other two dates are allowed to be blank.
            if (StmtList == null)
            {
                //if editing a List, blank indicates dates vary.
                MsgBox.show(this,"Please enter a Date.");
                return false;
            }
             
        }
        else
        {
            try
            {
                //"?" not allowed here
                DateTime.Parse(textDate.Text);
            }
            catch (Exception __dummyCatchVar10)
            {
                isError = true;
            }
        
        } 
        if (isError)
        {
            MsgBox.show(this,"Please fix Date.");
            return false;
        }
         
        //Validate DateStart-------------------------------------------------------------------------------
        isError = false;
        if (StringSupport.equals(textDateStart.Text, ""))
        {
        }
        else //no error
        if (StringSupport.equals(textDateStart.Text, "?"))
        {
            if (StmtList == null)
            {
                isError = true;
            }
             
        }
        else
        {
            try
            {
                DateTime.Parse(textDateStart.Text);
            }
            catch (Exception __dummyCatchVar11)
            {
                isError = true;
            }
        
        }  
        if (isError)
        {
            MsgBox.show(this,"Please fix Start Date.");
            return false;
        }
         
        //Validate DateEnd-------------------------------------------------------------------------------
        isError = false;
        if (StringSupport.equals(textDateEnd.Text, ""))
        {
        }
        else //no error
        if (StringSupport.equals(textDateEnd.Text, "?"))
        {
            if (StmtList == null)
            {
                isError = true;
            }
             
        }
        else
        {
            try
            {
                DateTime.Parse(textDateEnd.Text);
            }
            catch (Exception __dummyCatchVar12)
            {
                isError = true;
            }
        
        }  
        if (isError)
        {
            MsgBox.show(this,"Please fix End Date.");
            return false;
        }
         
        //if(  textDateStart.Text .errorProvider1.GetError(textDateStart)!=""
        //	|| textDateEnd.errorProvider1.GetError(textDateEnd)!=""
        //	|| textDate.errorProvider1.GetError(textDate)!="")
        //{
        //	MsgBox.Show(this,"Please fix data entry errors first.");
        //	return false;
        //}
        if (StmtList == null)
        {
            StmtCur.DateSent = PIn.Date(textDate.Text);
            StmtCur.IsSent = checkIsSent.Checked;
            StmtCur.Mode_ = (StatementMode)listMode.SelectedIndex;
            StmtCur.HidePayment = checkHidePayment.Checked;
            StmtCur.SinglePatient = checkSinglePatient.Checked;
            StmtCur.Intermingled = checkIntermingled.Checked;
            StmtCur.IsReceipt = checkIsReceipt.Checked;
            StmtCur.IsInvoice = checkIsInvoice.Checked;
            StmtCur.DateRangeFrom = PIn.Date(textDateStart.Text);
            //handles blank
            if (StringSupport.equals(textDateEnd.Text, ""))
            {
                StmtCur.DateRangeTo = new DateTime(2200, 1, 1);
            }
            else
            {
                //max val
                StmtCur.DateRangeTo = PIn.Date(textDateEnd.Text);
            } 
            StmtCur.Note = textNote.Text;
            StmtCur.NoteBold = textNoteBold.Text;
            StmtCur.IsInvoiceCopy = checkIsInvoiceCopy.Checked;
            if (StmtCur.IsInvoice || !StmtCur.getIsNew())
            {
                Statements.update(StmtCur);
                StmtCur.setIsNew(false);
            }
            else
            {
                //not an invoice and IsNew so insert
                StmtCur.StatementNum = Statements.insert(StmtCur);
                textInvoiceNum.Text = StmtCur.StatementNum.ToString();
                StmtCur.setIsNew(false);
            } 
        }
        else
        {
            for (int i = 0;i < StmtList.Count;i++)
            {
                //so that if we run this again, it will not do a second insert.
                if (!StringSupport.equals(textDate.Text, ""))
                {
                    StmtList[i].DateSent = PIn.Date(textDate.Text);
                }
                 
                if (checkIsSent.CheckState != CheckState.Indeterminate)
                {
                    StmtList[i].IsSent = checkIsSent.Checked;
                }
                 
                if (listMode.SelectedIndex != -1)
                {
                    StmtList[i].Mode_ = (StatementMode)listMode.SelectedIndex;
                }
                 
                if (checkHidePayment.CheckState != CheckState.Indeterminate)
                {
                    StmtList[i].HidePayment = checkHidePayment.Checked;
                }
                 
                if (checkSinglePatient.CheckState != CheckState.Indeterminate)
                {
                    StmtList[i].SinglePatient = checkSinglePatient.Checked;
                }
                 
                if (checkIntermingled.CheckState != CheckState.Indeterminate)
                {
                    StmtList[i].Intermingled = checkIntermingled.Checked;
                }
                 
                if (checkIsReceipt.CheckState != CheckState.Indeterminate)
                {
                    StmtList[i].IsReceipt = checkIsReceipt.Checked;
                }
                 
                if (!StringSupport.equals(textDateStart.Text, "?"))
                {
                    StmtList[i].DateRangeFrom = PIn.Date(textDateStart.Text);
                }
                 
                //handles blank
                if (!StringSupport.equals(textDateStart.Text, "?"))
                {
                    if (StringSupport.equals(textDateEnd.Text, ""))
                    {
                        StmtList[i].DateRangeTo = new DateTime(2200, 1, 1);
                    }
                    else
                    {
                        //max val
                        StmtList[i].DateRangeTo = PIn.Date(textDateEnd.Text);
                    } 
                }
                 
                if (!StringSupport.equals(textNote.Text, "?"))
                {
                    StmtList[i].Note = textNote.Text;
                }
                 
                if (!StringSupport.equals(textNoteBold.Text, "?"))
                {
                    StmtList[i].NoteBold = textNoteBold.Text;
                }
                 
                Statements.Update(StmtList[i]);
            }
        } 
        return true;
    }

    //never new
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


