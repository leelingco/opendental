//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:56 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Bridges.QuickBooks;
import OpenDental.DataValid;
import OpenDental.FormDepositEdit;
import OpenDental.FormSheetFillEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.Properties.Resources;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AccountingSoftware;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Deposit;
import OpenDentBusiness.Deposits;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.JournalEntries;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.Transaction;
import OpenDentBusiness.Transactions;

/**
* Summary description for FormBasicTemplate.
*/
public class FormDepositEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listPayType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private Deposit DepositCur;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBankAccountInfo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAmount = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupSelect = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.ODGrid gridPat;
    private OpenDental.UI.ODGrid gridIns;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private ClaimPayment[] ClaimPayList = new ClaimPayment[]();
    private OpenDental.ValidDate textDateStart;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butRefresh;
    private List<Payment> PatPayList = new List<Payment>();
    private ComboBox comboDepositAccount = new ComboBox();
    private Label labelDepositAccount = new Label();
    private boolean changed = new boolean();
    private TextBox textDepositAccount = new TextBox();
    /**
    * Only used if linking to accounts
    */
    private long[] DepositAccounts = new long[]();
    private Label labelIncomeAccountQB = new Label();
    private ComboBox comboIncomeAccountQB = new ComboBox();
    /**
    * Only used if linking to QB account.
    */
    private List<String> DepositAccountsQB = new List<String>();
    private OpenDental.UI.Button butSendQB;
    private TextBox textMemo = new TextBox();
    private Label labelMemo = new Label();
    /**
    * Only used if linking to QB account.
    */
    private List<String> IncomeAccountsQB = new List<String>();
    /**
    * True if the accounting software pref is set to QuickBooks.
    */
    private boolean IsQuickBooks = new boolean();
    /**
    * 
    */
    public FormDepositEdit(Deposit depositCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        DepositCur = depositCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormDepositEdit.class);
        this.groupSelect = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.listPayType = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textBankAccountInfo = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.textAmount = new System.Windows.Forms.TextBox();
        this.comboDepositAccount = new System.Windows.Forms.ComboBox();
        this.labelDepositAccount = new System.Windows.Forms.Label();
        this.textDepositAccount = new System.Windows.Forms.TextBox();
        this.labelIncomeAccountQB = new System.Windows.Forms.Label();
        this.comboIncomeAccountQB = new System.Windows.Forms.ComboBox();
        this.textMemo = new System.Windows.Forms.TextBox();
        this.labelMemo = new System.Windows.Forms.Label();
        this.butSendQB = new OpenDental.UI.Button();
        this.gridIns = new OpenDental.UI.ODGrid();
        this.butPrint = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.gridPat = new OpenDental.UI.ODGrid();
        this.butRefresh = new OpenDental.UI.Button();
        this.textDateStart = new OpenDental.ValidDate();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupSelect.SuspendLayout();
        this.SuspendLayout();
        //
        // groupSelect
        //
        this.groupSelect.Controls.Add(this.butRefresh);
        this.groupSelect.Controls.Add(this.textDateStart);
        this.groupSelect.Controls.Add(this.label5);
        this.groupSelect.Controls.Add(this.comboClinic);
        this.groupSelect.Controls.Add(this.labelClinic);
        this.groupSelect.Controls.Add(this.listPayType);
        this.groupSelect.Controls.Add(this.label2);
        this.groupSelect.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupSelect.Location = new System.Drawing.Point(602, 296);
        this.groupSelect.Name = "groupSelect";
        this.groupSelect.Size = new System.Drawing.Size(204, 324);
        this.groupSelect.TabIndex = 99;
        this.groupSelect.TabStop = false;
        this.groupSelect.Text = "Show";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(14, 14);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(118, 15);
        this.label5.TabIndex = 104;
        this.label5.Text = "Start Date";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(14, 67);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(180, 21);
        this.comboClinic.TabIndex = 94;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(14, 51);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(102, 15);
        this.labelClinic.TabIndex = 93;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listPayType
        //
        this.listPayType.Location = new System.Drawing.Point(14, 111);
        this.listPayType.Name = "listPayType";
        this.listPayType.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listPayType.Size = new System.Drawing.Size(134, 173);
        this.listPayType.TabIndex = 96;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 89);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(171, 18);
        this.label2.TabIndex = 97;
        this.label2.Text = "Patient Payment Types";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(602, 8);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(95, 15);
        this.label1.TabIndex = 102;
        this.label1.Text = "Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(602, 83);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(127, 15);
        this.label3.TabIndex = 104;
        this.label3.Text = "Bank Account Info";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textBankAccountInfo
        //
        this.textBankAccountInfo.Location = new System.Drawing.Point(602, 100);
        this.textBankAccountInfo.Multiline = true;
        this.textBankAccountInfo.Name = "textBankAccountInfo";
        this.textBankAccountInfo.Size = new System.Drawing.Size(289, 59);
        this.textBankAccountInfo.TabIndex = 105;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(602, 46);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(127, 15);
        this.label4.TabIndex = 106;
        this.label4.Text = "Amount";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(602, 63);
        this.textAmount.Name = "textAmount";
        this.textAmount.ReadOnly = true;
        this.textAmount.Size = new System.Drawing.Size(94, 20);
        this.textAmount.TabIndex = 107;
        //
        // comboDepositAccount
        //
        this.comboDepositAccount.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDepositAccount.FormattingEnabled = true;
        this.comboDepositAccount.Location = new System.Drawing.Point(602, 230);
        this.comboDepositAccount.Name = "comboDepositAccount";
        this.comboDepositAccount.Size = new System.Drawing.Size(289, 21);
        this.comboDepositAccount.TabIndex = 110;
        //
        // labelDepositAccount
        //
        this.labelDepositAccount.Location = new System.Drawing.Point(602, 215);
        this.labelDepositAccount.Name = "labelDepositAccount";
        this.labelDepositAccount.Size = new System.Drawing.Size(289, 13);
        this.labelDepositAccount.TabIndex = 111;
        this.labelDepositAccount.Text = "Deposit into Account";
        this.labelDepositAccount.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textDepositAccount
        //
        this.textDepositAccount.Location = new System.Drawing.Point(602, 261);
        this.textDepositAccount.Name = "textDepositAccount";
        this.textDepositAccount.ReadOnly = true;
        this.textDepositAccount.Size = new System.Drawing.Size(289, 20);
        this.textDepositAccount.TabIndex = 112;
        //
        // labelIncomeAccountQB
        //
        this.labelIncomeAccountQB.Location = new System.Drawing.Point(602, 256);
        this.labelIncomeAccountQB.Name = "labelIncomeAccountQB";
        this.labelIncomeAccountQB.Size = new System.Drawing.Size(289, 11);
        this.labelIncomeAccountQB.TabIndex = 114;
        this.labelIncomeAccountQB.Text = "Income Account";
        this.labelIncomeAccountQB.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelIncomeAccountQB.Visible = false;
        //
        // comboIncomeAccountQB
        //
        this.comboIncomeAccountQB.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboIncomeAccountQB.FormattingEnabled = true;
        this.comboIncomeAccountQB.Location = new System.Drawing.Point(602, 271);
        this.comboIncomeAccountQB.Name = "comboIncomeAccountQB";
        this.comboIncomeAccountQB.Size = new System.Drawing.Size(289, 21);
        this.comboIncomeAccountQB.TabIndex = 113;
        this.comboIncomeAccountQB.Visible = false;
        //
        // textMemo
        //
        this.textMemo.Location = new System.Drawing.Point(602, 177);
        this.textMemo.Multiline = true;
        this.textMemo.Name = "textMemo";
        this.textMemo.Size = new System.Drawing.Size(289, 35);
        this.textMemo.TabIndex = 117;
        //
        // labelMemo
        //
        this.labelMemo.Location = new System.Drawing.Point(602, 160);
        this.labelMemo.Name = "labelMemo";
        this.labelMemo.Size = new System.Drawing.Size(127, 15);
        this.labelMemo.TabIndex = 116;
        this.labelMemo.Text = "Memo";
        this.labelMemo.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSendQB
        //
        this.butSendQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSendQB.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSendQB.setAutosize(true);
        this.butSendQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSendQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSendQB.setCornerRadius(4F);
        this.butSendQB.Location = new System.Drawing.Point(816, 301);
        this.butSendQB.Name = "butSendQB";
        this.butSendQB.Size = new System.Drawing.Size(75, 24);
        this.butSendQB.TabIndex = 115;
        this.butSendQB.Text = "&Send QB";
        this.butSendQB.Click += new System.EventHandler(this.butSendQB_Click);
        //
        // gridIns
        //
        this.gridIns.setHScrollVisible(false);
        this.gridIns.Location = new System.Drawing.Point(8, 319);
        this.gridIns.Name = "gridIns";
        this.gridIns.setScrollValue(0);
        this.gridIns.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridIns.Size = new System.Drawing.Size(584, 301);
        this.gridIns.TabIndex = 109;
        this.gridIns.setTitle("Insurance Payments");
        this.gridIns.setTranslationName("TableDepositSlipIns");
        this.gridIns.CellClick = __MultiODGridClickEventHandler.combine(this.gridIns.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridIns_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridIns.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridIns_MouseUp);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(511, 631);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(81, 24);
        this.butPrint.TabIndex = 108;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(602, 25);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(94, 20);
        this.textDate.TabIndex = 103;
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
        this.butDelete.Location = new System.Drawing.Point(7, 631);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(85, 24);
        this.butDelete.TabIndex = 101;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridPat
        //
        this.gridPat.setHScrollVisible(false);
        this.gridPat.Location = new System.Drawing.Point(8, 12);
        this.gridPat.Name = "gridPat";
        this.gridPat.setScrollValue(0);
        this.gridPat.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridPat.Size = new System.Drawing.Size(584, 299);
        this.gridPat.TabIndex = 100;
        this.gridPat.setTitle("Patient Payments");
        this.gridPat.setTranslationName("TableDepositSlipPat");
        this.gridPat.CellClick = __MultiODGridClickEventHandler.combine(this.gridPat.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPat_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridPat.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridPat_MouseUp);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(13, 291);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(75, 24);
        this.butRefresh.TabIndex = 106;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(14, 31);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(94, 20);
        this.textDateStart.TabIndex = 105;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(712, 631);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
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
        this.butCancel.Location = new System.Drawing.Point(805, 631);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormDepositEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(897, 667);
        this.Controls.Add(this.textMemo);
        this.Controls.Add(this.labelMemo);
        this.Controls.Add(this.butSendQB);
        this.Controls.Add(this.textDepositAccount);
        this.Controls.Add(this.labelDepositAccount);
        this.Controls.Add(this.comboDepositAccount);
        this.Controls.Add(this.gridIns);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.textBankAccountInfo);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.gridPat);
        this.Controls.Add(this.groupSelect);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.labelIncomeAccountQB);
        this.Controls.Add(this.comboIncomeAccountQB);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.KeyPreview = true;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormDepositEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Deposit Slip";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormDepositEdit_Closing);
        this.Load += new System.EventHandler(this.FormDepositEdit_Load);
        this.groupSelect.ResumeLayout(false);
        this.groupSelect.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formDepositEdit_Load(Object sender, System.EventArgs e) throws Exception {
        butSendQB.Visible = false;
        IsQuickBooks = PrefC.getInt(PrefName.AccountingSoftware) == ((Enum)AccountingSoftware.QuickBooks).ordinal();
        if (IsNew)
        {
            if (!Security.IsAuthorized(Permissions.DepositSlips, DateTime.Today))
            {
                //we will check the date again when saving
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
        }
        else
        {
            //We enforce security here based on date displayed, not date entered
            if (!Security.isAuthorized(Permissions.DepositSlips,DepositCur.DateDeposit))
            {
                butOK.Enabled = false;
                butDelete.Enabled = false;
            }
             
        } 
        if (IsNew)
        {
            textDateStart.Text = PIn.Date(PrefC.getString(PrefName.DateDepositsStarted)).ToShortDateString();
            if (PrefC.getBool(PrefName.EasyNoClinics))
            {
                comboClinic.Visible = false;
                labelClinic.Visible = false;
            }
             
            comboClinic.Items.Clear();
            comboClinic.Items.Add(Lan.g(this,"all"));
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
            }
            for (int i = 0;i < DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()].Length;i++)
            {
                listPayType.Items.Add(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].ItemName);
                listPayType.SetSelected(i, true);
            }
            textDepositAccount.Visible = false;
            //this is never visible for new. It's a description if already attached.
            if (Accounts.depositsLinked() && !IsQuickBooks)
            {
                DepositAccounts = Accounts.getDepositAccounts();
                for (int i = 0;i < DepositAccounts.Length;i++)
                {
                    comboDepositAccount.Items.Add(Accounts.GetDescript(DepositAccounts[i]));
                }
                comboDepositAccount.SelectedIndex = 0;
            }
            else
            {
                labelDepositAccount.Visible = false;
                comboDepositAccount.Visible = false;
            } 
        }
        else
        {
            //Not new.
            groupSelect.Visible = false;
            gridIns.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
            gridPat.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
            //we never again let user change the deposit linking again from here.
            //They need to detach it from within the transaction
            //Might be enhanced later to allow, but that's very complex.
            Transaction trans = Transactions.getAttachedToDeposit(DepositCur.DepositNum);
            if (trans == null)
            {
                labelDepositAccount.Visible = false;
                comboDepositAccount.Visible = false;
                textDepositAccount.Visible = false;
            }
            else
            {
                comboDepositAccount.Enabled = false;
                labelDepositAccount.Text = Lan.g(this,"Deposited into Account");
                List<JournalEntry> jeL = JournalEntries.getForTrans(trans.TransactionNum);
                for (int i = 0;i < jeL.Count;i++)
                {
                    if (Accounts.GetAccount(jeL[i].AccountNum).AcctType == AccountType.Asset)
                    {
                        comboDepositAccount.Items.Add(Accounts.GetDescript(jeL[i].AccountNum));
                        comboDepositAccount.SelectedIndex = 0;
                        textDepositAccount.Text = jeL[i].DateDisplayed.ToShortDateString() + " " + jeL[i].DebitAmt.ToString("c");
                        break;
                    }
                     
                }
            } 
        } 
        if (IsQuickBooks)
        {
            //If in QuickBooks mode, always show deposit and income accounts so that users can send old deposits into QB.
            labelIncomeAccountQB.Visible = true;
            comboIncomeAccountQB.Visible = true;
            comboIncomeAccountQB.Items.Clear();
            textDepositAccount.Visible = false;
            labelDepositAccount.Visible = true;
            labelDepositAccount.Text = Lan.g(this,"Deposit into Account");
            comboDepositAccount.Visible = true;
            comboDepositAccount.Enabled = true;
            comboDepositAccount.Items.Clear();
            if (Accounts.depositsLinked())
            {
                if (!IsNew)
                {
                    butSendQB.Visible = true;
                }
                 
                DepositAccountsQB = Accounts.getDepositAccountsQB();
                for (int i = 0;i < DepositAccountsQB.Count;i++)
                {
                    comboDepositAccount.Items.Add(DepositAccountsQB[i]);
                }
                comboDepositAccount.SelectedIndex = 0;
                IncomeAccountsQB = Accounts.getIncomeAccountsQB();
                for (int i = 0;i < IncomeAccountsQB.Count;i++)
                {
                    comboIncomeAccountQB.Items.Add(IncomeAccountsQB[i]);
                }
                comboIncomeAccountQB.SelectedIndex = 0;
            }
             
        }
         
        textDate.Text = DepositCur.DateDeposit.ToShortDateString();
        textAmount.Text = DepositCur.Amount.ToString("F");
        textBankAccountInfo.Text = DepositCur.BankAccountInfo;
        textMemo.Text = DepositCur.Memo;
        fillGrids();
        if (IsNew)
        {
            gridPat.setSelected(true);
            gridIns.setSelected(true);
        }
         
        computeAmt();
    }

    /**
    * 
    */
    private void fillGrids() throws Exception {
        if (IsNew)
        {
            DateTime dateStart = PIn.Date(textDateStart.Text);
            long clinicNum = 0;
            if (comboClinic.SelectedIndex != 0)
            {
                clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
            }
             
            List<long> payTypes = new List<long>();
            for (int i = 0;i < listPayType.SelectedIndices.Count;i++)
            {
                //[listPayType.SelectedIndices.Count];
                payTypes.Add(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][listPayType.SelectedIndices[i]].DefNum);
            }
            PatPayList = Payments.GetForDeposit(dateStart, clinicNum, payTypes);
            ClaimPayList = ClaimPayments.getForDeposit(dateStart,clinicNum);
        }
        else
        {
            PatPayList = Payments.getForDeposit(DepositCur.DepositNum);
            ClaimPayList = ClaimPayments.getForDeposit(DepositCur.DepositNum);
        } 
        //Fill Patient Payment Grid---------------------------------------
        List<long> patNums = new List<long>();
        for (int i = 0;i < PatPayList.Count;i++)
        {
            patNums.Add(PatPayList[i].PatNum);
        }
        Patient[] pats = Patients.GetMultPats(patNums);
        gridPat.beginUpdate();
        gridPat.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableDepositSlipPat","Date"),80);
        gridPat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipPat","Patient"),130);
        gridPat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipPat","Type"),90);
        gridPat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipPat","Check Number"),95);
        gridPat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipPat","Bank-Branch"),80);
        gridPat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipPat","Amount"),80);
        gridPat.getColumns().add(col);
        gridPat.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < PatPayList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(PatPayList[i].PayDate.ToShortDateString());
            row.getCells().Add(Patients.GetOnePat(pats, PatPayList[i].PatNum).GetNameLF());
            row.getCells().Add(DefC.GetName(DefCat.PaymentTypes, PatPayList[i].PayType));
            row.getCells().Add(PatPayList[i].CheckNum);
            row.getCells().Add(PatPayList[i].BankBranch);
            row.getCells().Add(PatPayList[i].PayAmt.ToString("F"));
            gridPat.getRows().add(row);
        }
        gridPat.endUpdate();
        //Fill Insurance Payment Grid-------------------------------------
        gridIns.beginUpdate();
        gridIns.getColumns().Clear();
        col = new ODGridColumn(Lan.g("TableDepositSlipIns","Date"),80);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipIns","Carrier"),220);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipIns","Check Number"),95);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipIns","Bank-Branch"),80);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableDepositSlipIns","Amount"),90);
        gridIns.getColumns().add(col);
        gridIns.getRows().Clear();
        for (int i = 0;i < ClaimPayList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ClaimPayList[i].CheckDate.ToShortDateString());
            row.getCells().Add(ClaimPayList[i].CarrierName);
            row.getCells().Add(ClaimPayList[i].CheckNum);
            row.getCells().Add(ClaimPayList[i].BankBranch);
            row.getCells().Add(ClaimPayList[i].CheckAmt.ToString("F"));
            gridIns.getRows().add(row);
        }
        gridIns.endUpdate();
    }

    /**
    * Usually run after any selected items changed. Recalculates amt based on selected items.  May get fired twice when click and mouse up, harmless.
    */
    private void computeAmt() throws Exception {
        if (!IsNew)
        {
            return ;
        }
         
        double amount = 0;
        for (int i = 0;i < gridPat.getSelectedIndices().Length;i++)
        {
            amount += (double)PatPayList[gridPat.getSelectedIndices()[i]].PayAmt;
        }
        for (int i = 0;i < gridIns.getSelectedIndices().Length;i++)
        {
            amount += (double)ClaimPayList[gridIns.getSelectedIndices()[i]].CheckAmt;
        }
        textAmount.Text = amount.ToString("F");
        DepositCur.Amount = (double)amount;
    }

    private void gridPat_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        computeAmt();
    }

    private void gridPat_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        computeAmt();
    }

    private void gridIns_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        computeAmt();
    }

    private void gridIns_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        computeAmt();
    }

    /**
    * Remember that this can only happen if IsNew
    */
    private void butRefresh_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateStart.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        fillGrids();
        gridPat.setSelected(true);
        gridIns.setSelected(true);
        computeAmt();
        if (comboClinic.SelectedIndex == 0)
        {
            textBankAccountInfo.Text = PrefC.getString(PrefName.PracticeBankNumber);
        }
        else
        {
            textBankAccountInfo.Text = Clinics.getList()[comboClinic.SelectedIndex - 1].BankNumber;
        } 
        if (Prefs.UpdateString(PrefName.DateDepositsStarted, POut.Date(PIn.Date(textDateStart.Text), false)))
        {
            changed = true;
        }
         
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //If deposit is attached to a transaction which is more than 48 hours old, then not allowed to delete.
        //This is hard coded.  User would have to delete or detach from within transaction rather than here.
        Transaction trans = Transactions.getAttachedToDeposit(DepositCur.DepositNum);
        if (trans != null)
        {
            if (trans.DateTimeEntry < MiscData.getNowDateTime().AddDays(-2))
            {
                MsgBox.show(this,"Not allowed to delete.  This deposit is already attached to an accounting transaction.  You will need to detach it from within the accounting section of the program.");
                return ;
            }
             
            if (Transactions.isReconciled(trans))
            {
                MsgBox.show(this,"Not allowed to delete.  This deposit is attached to an accounting transaction that has been reconciled.  You will need to detach it from within the accounting section of the program.");
                return ;
            }
             
            try
            {
                Transactions.delete(trans);
            }
            catch (ApplicationException ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }
        
        }
         
        if (!MsgBox.show(this,true,"Delete?"))
        {
            return ;
        }
         
        Deposits.delete(DepositCur);
        DialogResult = DialogResult.OK;
    }

    private void butSendQB_Click(Object sender, EventArgs e) throws Exception {
        CreateDepositQB(DepositAccountsQB[comboDepositAccount.SelectedIndex], IncomeAccountsQB[comboIncomeAccountQB.SelectedIndex], DepositCur.Amount, textMemo.Text, false);
    }

    /**
    * Returns true if a deposit was created OR if the user clicked continue anyway on pop up.
    */
    private boolean createDepositQB(String depositAccount, String incomeAccount, double amount, String memo, boolean allowContinue) throws Exception {
        try
        {
            Cursor.Current = Cursors.WaitCursor;
            QuickBooks.CreateDeposit(DepositAccountsQB[comboDepositAccount.SelectedIndex], IncomeAccountsQB[comboIncomeAccountQB.SelectedIndex], DepositCur.Amount, memo);
            Cursor.Current = Cursors.Default;
            MsgBox.show(this,"Deposit successfully sent to QuickBooks.");
            butSendQB.Enabled = false;
        }
        catch (Exception ex)
        {
            //Don't let user send same deposit more than once.
            Cursor.Current = Cursors.Default;
            if (allowContinue)
            {
                if (MessageBox.Show(ex.Message + "\r\n\r\nA deposit has not been created in QuickBooks, continue anyway?", "QuickBooks Deposit Create Failed", MessageBoxButtons.YesNo) != DialogResult.Yes)
                {
                    return false;
                }
                 
            }
            else
            {
                MessageBox.Show(ex.Message, "QuickBooks Deposit Create Failed");
                return false;
            } 
        }

        return true;
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        if (IsNew)
        {
            if (!saveToDB())
            {
                return ;
            }
             
        }
        else
        {
            //not new
            //Only allowed to change date and bank account info, NOT attached checks.
            //We enforce security here based on date displayed, not date entered.
            //If user is trying to change date without permission:
            DateTime date = PIn.Date(textDate.Text);
            if (Security.isAuthorized(Permissions.DepositSlips,date,true))
            {
                if (!saveToDB())
                {
                    return ;
                }
                 
            }
             
        } 
        //if security.NotAuthorized, then it simply skips the save process before printing
        SheetDef sheetDef = null;
        List<SheetDef> depositSheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.DepositSlip);
        if (depositSheetDefs.Count > 0)
        {
            sheetDef = depositSheetDefs[0];
            SheetDefs.getFieldsAndParameters(sheetDef);
        }
        else
        {
            sheetDef = SheetsInternal.depositSlip();
        } 
        Sheet sheet = SheetUtil.CreateSheet(sheetDef, 0);
        SheetParameter.setParameter(sheet,"DepositNum",DepositCur.DepositNum);
        SheetFiller.fillFields(sheet);
        SheetUtil.CalculateHeights(sheet, this.CreateGraphics());
        FormSheetFillEdit FormSF = new FormSheetFillEdit(sheet);
        FormSF.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    //this is imporant, since we don't want to insert the deposit slip twice.
    /**
    * Saves the selected rows to database.  MUST close window after this.
    */
    private boolean saveToDB() throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        //Prevent backdating----------------------------------------------------------------------------------------
        DateTime date = PIn.Date(textDate.Text);
        if (IsNew)
        {
            if (!Security.isAuthorized(Permissions.DepositSlips,date))
            {
                return false;
            }
             
        }
        else
        {
            //We enforce security here based on date displayed, not date entered
            if (!Security.isAuthorized(Permissions.DepositSlips,date))
            {
                return false;
            }
             
        } 
        DepositCur.DateDeposit = PIn.Date(textDate.Text);
        //amount already handled.
        DepositCur.BankAccountInfo = PIn.String(textBankAccountInfo.Text);
        DepositCur.Memo = PIn.String(textMemo.Text);
        if (IsNew)
        {
            if (gridPat.getSelectedIndices().Length + gridIns.getSelectedIndices().Length > 18)
            {
                if (!MsgBox.show(this,MsgBoxButtons.YesNo,"No more than 18 items will fit on a QuickBooks deposit slip. Continue anyway?"))
                {
                    return false;
                }
                 
            }
             
            //Deposits.Insert(DepositCur);
            if (Accounts.depositsLinked() && DepositCur.Amount > 0)
            {
                if (IsQuickBooks)
                {
                    //Create a deposit in QuickBooks.
                    if (!CreateDepositQB(DepositAccountsQB[comboDepositAccount.SelectedIndex], IncomeAccountsQB[comboIncomeAccountQB.SelectedIndex], DepositCur.Amount, textMemo.Text, true))
                    {
                        return false;
                    }
                     
                    Deposits.insert(DepositCur);
                }
                else
                {
                    Deposits.insert(DepositCur);
                    //create a transaction here
                    Transaction trans = new Transaction();
                    trans.DepositNum = DepositCur.DepositNum;
                    trans.UserNum = Security.getCurUser().UserNum;
                    Transactions.insert(trans);
                    //first the deposit entry
                    JournalEntry je = new JournalEntry();
                    je.AccountNum = DepositAccounts[comboDepositAccount.SelectedIndex];
                    je.CheckNumber = Lan.g(this,"DEP");
                    je.DateDisplayed = DepositCur.DateDeposit;
                    //it would be nice to add security here.
                    je.DebitAmt = DepositCur.Amount;
                    je.Memo = Lan.g(this,"Deposit");
                    je.Splits = Accounts.getDescript(PrefC.getLong(PrefName.AccountingIncomeAccount));
                    je.TransactionNum = trans.TransactionNum;
                    JournalEntries.insert(je);
                    //then, the income entry
                    je = new JournalEntry();
                    je.AccountNum = PrefC.getLong(PrefName.AccountingIncomeAccount);
                    //je.CheckNumber=;
                    je.DateDisplayed = DepositCur.DateDeposit;
                    //it would be nice to add security here.
                    je.CreditAmt = DepositCur.Amount;
                    je.Memo = Lan.g(this,"Deposit");
                    je.Splits = Accounts.GetDescript(DepositAccounts[comboDepositAccount.SelectedIndex]);
                    je.TransactionNum = trans.TransactionNum;
                    JournalEntries.insert(je);
                } 
            }
            else
            {
                //Deposits are not linked or deposit amount is not greater than 0.
                Deposits.insert(DepositCur);
            } 
        }
        else
        {
            Deposits.update(DepositCur);
        } 
        if (IsNew)
        {
            for (int i = 0;i < gridPat.getSelectedIndices().Length;i++)
            {
                //never allowed to change or attach more checks after initial creation of deposit slip
                PatPayList[gridPat.getSelectedIndices()[i]].DepositNum = DepositCur.DepositNum;
                Payments.Update(PatPayList[gridPat.getSelectedIndices()[i]], false);
            }
            for (int i = 0;i < gridIns.getSelectedIndices().Length;i++)
            {
                ClaimPayList[gridIns.getSelectedIndices()[i]].DepositNum = DepositCur.DepositNum;
                ClaimPayments.Update(ClaimPayList[gridIns.getSelectedIndices()[i]]);
            }
        }
         
        if (IsNew)
        {
            SecurityLogs.MakeLogEntry(Permissions.DepositSlips, 0, DepositCur.DateDeposit.ToShortDateString() + " New " + DepositCur.Amount.ToString("c"));
        }
        else
        {
            SecurityLogs.MakeLogEntry(Permissions.AdjustmentEdit, 0, DepositCur.DateDeposit.ToShortDateString() + " " + DepositCur.Amount.ToString("c"));
        } 
        return true;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveToDB())
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            //no need to worry about deposit links, since none made yet.
            Deposits.delete(DepositCur);
        }
         
        DialogResult = DialogResult.Cancel;
    }

    private void formDepositEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

}


