//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:33 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Bridges.QuickBooks;
import OpenDental.DataValid;
import OpenDental.FormAccountingAutoPayEdit;
import OpenDental.FormAccountingSetup;
import OpenDental.FormAccountPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.AccountingAutoPay;
import OpenDentBusiness.AccountingAutoPays;
import OpenDentBusiness.AccountingSoftware;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAccountingSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private OpenDental.UI.Button butAdd;
    private GroupBox groupBox1 = new GroupBox();
    private Label label2 = new Label();
    private OpenDental.UI.Button butChange;
    private Label label3 = new Label();
    private TextBox textAccountInc = new TextBox();
    private OpenDental.UI.Button butRemove;
    private ListBox listAccountsDep = new ListBox();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * Each item in the array list is an int for an AccountNum for the deposit accounts.
    */
    private ArrayList depAL = new ArrayList();
    private GroupBox groupAutomaticPayment = new GroupBox();
    private OpenDental.UI.Button butChangeCash;
    private Label label4 = new Label();
    private TextBox textAccountCashInc = new TextBox();
    private Label label5 = new Label();
    private OpenDental.UI.Button butAddPay;
    private long PickedDepAccountNum = new long();
    //private ArrayList cashAL;
    private long PickedPayAccountNum = new long();
    private OpenDental.UI.ODGrid gridMain;
    private ListBox listSoftware = new ListBox();
    private Label labelSoftware = new Label();
    private Panel panelQB = new Panel();
    private Panel panelOD = new Panel();
    private GroupBox groupQB = new GroupBox();
    private Label labelDepositsQB = new Label();
    private ListBox listBoxDepositAccountsQB = new ListBox();
    private OpenDental.UI.Button butRemoveDepositQB;
    private OpenDental.UI.Button butAddDepositQB;
    private Label label7 = new Label();
    private OpenDental.UI.Button butConnectQB;
    private OpenDental.UI.Button butBrowseQB;
    private Label labelCompanyFile = new Label();
    private TextBox textCompanyFileQB = new TextBox();
    private Label labelWarning = new Label();
    private Label labelIncomeAccountQB = new Label();
    private Label labelConnectQB = new Label();
    /**
    * Arraylist of AccountingAutoPays.
    */
    private List<AccountingAutoPay> payList = new List<AccountingAutoPay>();
    private AccountingSoftware AcctSoftware = AccountingSoftware.OpenDental;
    private Label labelQuickBooksTitle = new Label();
    private OpenDental.UI.Button butRemoveIncomeQB;
    private OpenDental.UI.Button butAddIncomeQB;
    private ListBox listBoxIncomeAccountsQB = new ListBox();
    private List<String> listDepositAccountsQB = new List<String>();
    private List<String> listIncomeAccountsQB = new List<String>();
    /**
    * 
    */
    public FormAccountingSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAccountingSetup.class);
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.listAccountsDep = new System.Windows.Forms.ListBox();
        this.butRemove = new OpenDental.UI.Button();
        this.butChange = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.textAccountInc = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butAdd = new OpenDental.UI.Button();
        this.groupAutomaticPayment = new System.Windows.Forms.GroupBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butChangeCash = new OpenDental.UI.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.textAccountCashInc = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.butAddPay = new OpenDental.UI.Button();
        this.listSoftware = new System.Windows.Forms.ListBox();
        this.labelSoftware = new System.Windows.Forms.Label();
        this.panelQB = new System.Windows.Forms.Panel();
        this.groupQB = new System.Windows.Forms.GroupBox();
        this.butRemoveIncomeQB = new OpenDental.UI.Button();
        this.butAddIncomeQB = new OpenDental.UI.Button();
        this.listBoxIncomeAccountsQB = new System.Windows.Forms.ListBox();
        this.labelQuickBooksTitle = new System.Windows.Forms.Label();
        this.labelConnectQB = new System.Windows.Forms.Label();
        this.labelIncomeAccountQB = new System.Windows.Forms.Label();
        this.labelWarning = new System.Windows.Forms.Label();
        this.butConnectQB = new OpenDental.UI.Button();
        this.butBrowseQB = new OpenDental.UI.Button();
        this.labelCompanyFile = new System.Windows.Forms.Label();
        this.textCompanyFileQB = new System.Windows.Forms.TextBox();
        this.listBoxDepositAccountsQB = new System.Windows.Forms.ListBox();
        this.butRemoveDepositQB = new OpenDental.UI.Button();
        this.butAddDepositQB = new OpenDental.UI.Button();
        this.label7 = new System.Windows.Forms.Label();
        this.labelDepositsQB = new System.Windows.Forms.Label();
        this.panelOD = new System.Windows.Forms.Panel();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupAutomaticPayment.SuspendLayout();
        this.panelQB.SuspendLayout();
        this.groupQB.SuspendLayout();
        this.panelOD.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 61);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(168, 53);
        this.label1.TabIndex = 2;
        this.label1.Text = "User will get to pick from this list of accounts to deposit into";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.listAccountsDep);
        this.groupBox1.Controls.Add(this.butRemove);
        this.groupBox1.Controls.Add(this.butChange);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.textAccountInc);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.butAdd);
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Location = new System.Drawing.Point(0, 0);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(519, 222);
        this.groupBox1.TabIndex = 32;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Automatic Deposit Entries";
        //
        // listAccountsDep
        //
        this.listAccountsDep.FormattingEnabled = true;
        this.listAccountsDep.Location = new System.Drawing.Point(182, 61);
        this.listAccountsDep.Name = "listAccountsDep";
        this.listAccountsDep.Size = new System.Drawing.Size(230, 108);
        this.listAccountsDep.TabIndex = 37;
        //
        // butRemove
        //
        this.butRemove.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemove.setAutosize(true);
        this.butRemove.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemove.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemove.setCornerRadius(4F);
        this.butRemove.Location = new System.Drawing.Point(417, 91);
        this.butRemove.Name = "butRemove";
        this.butRemove.Size = new System.Drawing.Size(75, 24);
        this.butRemove.TabIndex = 36;
        this.butRemove.Text = "Remove";
        this.butRemove.Click += new System.EventHandler(this.butRemove_Click);
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(418, 176);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(75, 24);
        this.butChange.TabIndex = 35;
        this.butChange.Text = "Change";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(12, 179);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(168, 19);
        this.label3.TabIndex = 33;
        this.label3.Text = "Income Account";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAccountInc
        //
        this.textAccountInc.Location = new System.Drawing.Point(182, 179);
        this.textAccountInc.Name = "textAccountInc";
        this.textAccountInc.ReadOnly = true;
        this.textAccountInc.Size = new System.Drawing.Size(230, 20);
        this.textAccountInc.TabIndex = 34;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(19, 26);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(492, 27);
        this.label2.TabIndex = 32;
        this.label2.Text = "Every time a deposit is created, an accounting transaction will also be automatic" + "ally created.";
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(418, 61);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 30;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // groupAutomaticPayment
        //
        this.groupAutomaticPayment.Controls.Add(this.gridMain);
        this.groupAutomaticPayment.Controls.Add(this.butChangeCash);
        this.groupAutomaticPayment.Controls.Add(this.label4);
        this.groupAutomaticPayment.Controls.Add(this.textAccountCashInc);
        this.groupAutomaticPayment.Controls.Add(this.label5);
        this.groupAutomaticPayment.Controls.Add(this.butAddPay);
        this.groupAutomaticPayment.Location = new System.Drawing.Point(27, 280);
        this.groupAutomaticPayment.Name = "groupAutomaticPayment";
        this.groupAutomaticPayment.Size = new System.Drawing.Size(519, 353);
        this.groupAutomaticPayment.TabIndex = 33;
        this.groupAutomaticPayment.TabStop = false;
        this.groupAutomaticPayment.Text = "Automatic Payment Entries";
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(22, 112);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(471, 177);
        this.gridMain.TabIndex = 40;
        this.gridMain.setTitle("Auto Payment Entries");
        this.gridMain.setTranslationName("TableAccountingAutoPay");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butChangeCash
        //
        this.butChangeCash.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangeCash.setAutosize(true);
        this.butChangeCash.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangeCash.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangeCash.setCornerRadius(4F);
        this.butChangeCash.Location = new System.Drawing.Point(418, 307);
        this.butChangeCash.Name = "butChangeCash";
        this.butChangeCash.Size = new System.Drawing.Size(75, 24);
        this.butChangeCash.TabIndex = 35;
        this.butChangeCash.Text = "Change";
        this.butChangeCash.Click += new System.EventHandler(this.butChangeCash_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(12, 310);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(168, 19);
        this.label4.TabIndex = 33;
        this.label4.Text = "Income Account";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAccountCashInc
        //
        this.textAccountCashInc.Location = new System.Drawing.Point(182, 310);
        this.textAccountCashInc.Name = "textAccountCashInc";
        this.textAccountCashInc.ReadOnly = true;
        this.textAccountCashInc.Size = new System.Drawing.Size(230, 20);
        this.textAccountCashInc.TabIndex = 34;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(19, 26);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(492, 47);
        this.label5.TabIndex = 32;
        this.label5.Text = resources.GetString("label5.Text");
        //
        // butAddPay
        //
        this.butAddPay.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddPay.setAutosize(true);
        this.butAddPay.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddPay.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddPay.setCornerRadius(4F);
        this.butAddPay.Location = new System.Drawing.Point(418, 83);
        this.butAddPay.Name = "butAddPay";
        this.butAddPay.Size = new System.Drawing.Size(75, 24);
        this.butAddPay.TabIndex = 30;
        this.butAddPay.Text = "Add";
        this.butAddPay.Click += new System.EventHandler(this.butAddPay_Click);
        //
        // listSoftware
        //
        this.listSoftware.FormattingEnabled = true;
        this.listSoftware.Items.AddRange(new Object[]{ "Open Dental", "QuickBooks" });
        this.listSoftware.Location = new System.Drawing.Point(585, 53);
        this.listSoftware.Name = "listSoftware";
        this.listSoftware.Size = new System.Drawing.Size(102, 30);
        this.listSoftware.TabIndex = 34;
        this.listSoftware.Click += new System.EventHandler(this.listSoftware_Click);
        //
        // labelSoftware
        //
        this.labelSoftware.Location = new System.Drawing.Point(552, 32);
        this.labelSoftware.Name = "labelSoftware";
        this.labelSoftware.Size = new System.Drawing.Size(135, 19);
        this.labelSoftware.TabIndex = 38;
        this.labelSoftware.Text = "Deposit Software";
        this.labelSoftware.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelQB
        //
        this.panelQB.Controls.Add(this.groupQB);
        this.panelQB.Location = new System.Drawing.Point(27, 12);
        this.panelQB.Name = "panelQB";
        this.panelQB.Size = new System.Drawing.Size(218, 225);
        this.panelQB.TabIndex = 39;
        //
        // groupQB
        //
        this.groupQB.Controls.Add(this.butRemoveIncomeQB);
        this.groupQB.Controls.Add(this.butAddIncomeQB);
        this.groupQB.Controls.Add(this.listBoxIncomeAccountsQB);
        this.groupQB.Controls.Add(this.labelQuickBooksTitle);
        this.groupQB.Controls.Add(this.labelConnectQB);
        this.groupQB.Controls.Add(this.labelIncomeAccountQB);
        this.groupQB.Controls.Add(this.labelWarning);
        this.groupQB.Controls.Add(this.butConnectQB);
        this.groupQB.Controls.Add(this.butBrowseQB);
        this.groupQB.Controls.Add(this.labelCompanyFile);
        this.groupQB.Controls.Add(this.textCompanyFileQB);
        this.groupQB.Controls.Add(this.listBoxDepositAccountsQB);
        this.groupQB.Controls.Add(this.butRemoveDepositQB);
        this.groupQB.Controls.Add(this.butAddDepositQB);
        this.groupQB.Controls.Add(this.label7);
        this.groupQB.Controls.Add(this.labelDepositsQB);
        this.groupQB.Location = new System.Drawing.Point(0, 0);
        this.groupQB.Name = "groupQB";
        this.groupQB.Size = new System.Drawing.Size(519, 606);
        this.groupQB.TabIndex = 0;
        this.groupQB.TabStop = false;
        this.groupQB.Text = "QuickBooks";
        //
        // butRemoveIncomeQB
        //
        this.butRemoveIncomeQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemoveIncomeQB.setAutosize(true);
        this.butRemoveIncomeQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemoveIncomeQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemoveIncomeQB.setCornerRadius(4F);
        this.butRemoveIncomeQB.Location = new System.Drawing.Point(418, 336);
        this.butRemoveIncomeQB.Name = "butRemoveIncomeQB";
        this.butRemoveIncomeQB.Size = new System.Drawing.Size(75, 24);
        this.butRemoveIncomeQB.TabIndex = 56;
        this.butRemoveIncomeQB.Text = "Remove";
        this.butRemoveIncomeQB.Click += new System.EventHandler(this.butRemoveIncomeQB_Click);
        //
        // butAddIncomeQB
        //
        this.butAddIncomeQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddIncomeQB.setAutosize(true);
        this.butAddIncomeQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddIncomeQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddIncomeQB.setCornerRadius(4F);
        this.butAddIncomeQB.Location = new System.Drawing.Point(418, 306);
        this.butAddIncomeQB.Name = "butAddIncomeQB";
        this.butAddIncomeQB.Size = new System.Drawing.Size(75, 24);
        this.butAddIncomeQB.TabIndex = 55;
        this.butAddIncomeQB.Text = "Add";
        this.butAddIncomeQB.Click += new System.EventHandler(this.butAddIncomeQB_Click);
        //
        // listBoxIncomeAccountsQB
        //
        this.listBoxIncomeAccountsQB.FormattingEnabled = true;
        this.listBoxIncomeAccountsQB.Location = new System.Drawing.Point(182, 306);
        this.listBoxIncomeAccountsQB.Name = "listBoxIncomeAccountsQB";
        this.listBoxIncomeAccountsQB.Size = new System.Drawing.Size(230, 108);
        this.listBoxIncomeAccountsQB.TabIndex = 54;
        //
        // labelQuickBooksTitle
        //
        this.labelQuickBooksTitle.Location = new System.Drawing.Point(15, 20);
        this.labelQuickBooksTitle.Name = "labelQuickBooksTitle";
        this.labelQuickBooksTitle.Size = new System.Drawing.Size(478, 34);
        this.labelQuickBooksTitle.TabIndex = 53;
        this.labelQuickBooksTitle.Text = "QuickBooks and the QuickBooks Foundation Class must be installed on this computer" + ". \r\nGo to the QuickBooks page on our website to download the QBFC installer.";
        this.labelQuickBooksTitle.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelConnectQB
        //
        this.labelConnectQB.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelConnectQB.Location = new System.Drawing.Point(15, 82);
        this.labelConnectQB.Name = "labelConnectQB";
        this.labelConnectQB.Size = new System.Drawing.Size(397, 46);
        this.labelConnectQB.TabIndex = 52;
        this.labelConnectQB.Text = "Push the connect button with QuickBooks running in the background if this is your" + " first time using QuickBooks with Open Dental. (Only need to do this once)";
        this.labelConnectQB.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelIncomeAccountQB
        //
        this.labelIncomeAccountQB.Location = new System.Drawing.Point(3, 307);
        this.labelIncomeAccountQB.Name = "labelIncomeAccountQB";
        this.labelIncomeAccountQB.Size = new System.Drawing.Size(177, 53);
        this.labelIncomeAccountQB.TabIndex = 51;
        this.labelIncomeAccountQB.Text = "User will get to pick from this list of income accounts.";
        this.labelIncomeAccountQB.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelWarning
        //
        this.labelWarning.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelWarning.Location = new System.Drawing.Point(35, 569);
        this.labelWarning.Name = "labelWarning";
        this.labelWarning.Size = new System.Drawing.Size(448, 27);
        this.labelWarning.TabIndex = 50;
        this.labelWarning.Text = "Open Dental will run faster if your QuickBooks company file is open in the backgr" + "ound.";
        this.labelWarning.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butConnectQB
        //
        this.butConnectQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butConnectQB.setAutosize(true);
        this.butConnectQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butConnectQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butConnectQB.setCornerRadius(4F);
        this.butConnectQB.Location = new System.Drawing.Point(418, 93);
        this.butConnectQB.Name = "butConnectQB";
        this.butConnectQB.Size = new System.Drawing.Size(75, 24);
        this.butConnectQB.TabIndex = 49;
        this.butConnectQB.Text = "Connect";
        this.butConnectQB.Click += new System.EventHandler(this.butConnectQB_Click);
        //
        // butBrowseQB
        //
        this.butBrowseQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBrowseQB.setAutosize(true);
        this.butBrowseQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBrowseQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBrowseQB.setCornerRadius(4F);
        this.butBrowseQB.Location = new System.Drawing.Point(418, 57);
        this.butBrowseQB.Name = "butBrowseQB";
        this.butBrowseQB.Size = new System.Drawing.Size(75, 24);
        this.butBrowseQB.TabIndex = 48;
        this.butBrowseQB.Text = "Browse";
        this.butBrowseQB.Click += new System.EventHandler(this.butBrowseQB_Click);
        //
        // labelCompanyFile
        //
        this.labelCompanyFile.Location = new System.Drawing.Point(12, 59);
        this.labelCompanyFile.Name = "labelCompanyFile";
        this.labelCompanyFile.Size = new System.Drawing.Size(105, 19);
        this.labelCompanyFile.TabIndex = 46;
        this.labelCompanyFile.Text = "Company File";
        this.labelCompanyFile.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCompanyFileQB
        //
        this.textCompanyFileQB.Location = new System.Drawing.Point(123, 59);
        this.textCompanyFileQB.Name = "textCompanyFileQB";
        this.textCompanyFileQB.Size = new System.Drawing.Size(289, 20);
        this.textCompanyFileQB.TabIndex = 47;
        //
        // listBoxDepositAccountsQB
        //
        this.listBoxDepositAccountsQB.FormattingEnabled = true;
        this.listBoxDepositAccountsQB.Location = new System.Drawing.Point(182, 191);
        this.listBoxDepositAccountsQB.Name = "listBoxDepositAccountsQB";
        this.listBoxDepositAccountsQB.Size = new System.Drawing.Size(230, 108);
        this.listBoxDepositAccountsQB.TabIndex = 44;
        //
        // butRemoveDepositQB
        //
        this.butRemoveDepositQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemoveDepositQB.setAutosize(true);
        this.butRemoveDepositQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemoveDepositQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemoveDepositQB.setCornerRadius(4F);
        this.butRemoveDepositQB.Location = new System.Drawing.Point(418, 220);
        this.butRemoveDepositQB.Name = "butRemoveDepositQB";
        this.butRemoveDepositQB.Size = new System.Drawing.Size(75, 24);
        this.butRemoveDepositQB.TabIndex = 43;
        this.butRemoveDepositQB.Text = "Remove";
        this.butRemoveDepositQB.Click += new System.EventHandler(this.butRemoveDepositQB_Click);
        //
        // butAddDepositQB
        //
        this.butAddDepositQB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddDepositQB.setAutosize(true);
        this.butAddDepositQB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddDepositQB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddDepositQB.setCornerRadius(4F);
        this.butAddDepositQB.Location = new System.Drawing.Point(418, 190);
        this.butAddDepositQB.Name = "butAddDepositQB";
        this.butAddDepositQB.Size = new System.Drawing.Size(75, 24);
        this.butAddDepositQB.TabIndex = 39;
        this.butAddDepositQB.Text = "Add";
        this.butAddDepositQB.Click += new System.EventHandler(this.butAddDepositQB_Click);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(3, 191);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(177, 53);
        this.label7.TabIndex = 38;
        this.label7.Text = "User will get to pick from this list of accounts to deposit to.";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelDepositsQB
        //
        this.labelDepositsQB.Location = new System.Drawing.Point(12, 152);
        this.labelDepositsQB.Name = "labelDepositsQB";
        this.labelDepositsQB.Size = new System.Drawing.Size(492, 27);
        this.labelDepositsQB.TabIndex = 33;
        this.labelDepositsQB.Text = "Every time a deposit is created, a deposit will be created within QuickBooks usin" + "g these settings.\r\n(Commas must be removed from account names within QuickBooks)" + "";
        this.labelDepositsQB.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // panelOD
        //
        this.panelOD.Controls.Add(this.groupBox1);
        this.panelOD.Location = new System.Drawing.Point(281, 15);
        this.panelOD.Name = "panelOD";
        this.panelOD.Size = new System.Drawing.Size(265, 201);
        this.panelOD.TabIndex = 40;
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(607, 565);
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
        this.butCancel.Location = new System.Drawing.Point(607, 606);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // FormAccountingSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(715, 657);
        this.Controls.Add(this.panelOD);
        this.Controls.Add(this.panelQB);
        this.Controls.Add(this.labelSoftware);
        this.Controls.Add(this.listSoftware);
        this.Controls.Add(this.groupAutomaticPayment);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAccountingSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Accounting";
        this.Load += new System.EventHandler(this.FormAccountingSetup_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupAutomaticPayment.ResumeLayout(false);
        this.groupAutomaticPayment.PerformLayout();
        this.panelQB.ResumeLayout(false);
        this.groupQB.ResumeLayout(false);
        this.groupQB.PerformLayout();
        this.panelOD.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formAccountingSetup_Load(Object sender, EventArgs e) throws Exception {
        AcctSoftware = AccountingSoftware.values()[PrefC.getInt(PrefName.AccountingSoftware)];
        if (AcctSoftware == AccountingSoftware.QuickBooks)
        {
            panelLayoutQB();
        }
        else
        {
            panelLayoutOD();
        } 
        listSoftware.SelectedIndex = ((Enum)AcctSoftware).ordinal();
    }

    private void fillDepList() throws Exception {
        listAccountsDep.Items.Clear();
        for (int i = 0;i < depAL.Count;i++)
        {
            listAccountsDep.Items.Add(Accounts.getDescript((long)depAL[i]));
        }
    }

    private void fillPayGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableAccountingAutoPay","Payment Type"),200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAccountingAutoPay","Pick List"),250);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < payList.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(DefC.GetName(DefCat.PaymentTypes, payList[i].PayType));
            row.getCells().Add(AccountingAutoPays.GetPickListDesc(payList[i]));
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillQBLists() throws Exception {
        listBoxDepositAccountsQB.Items.Clear();
        for (int i = 0;i < listDepositAccountsQB.Count;i++)
        {
            listBoxDepositAccountsQB.Items.Add(listDepositAccountsQB[i]);
        }
        listBoxIncomeAccountsQB.Items.Clear();
        for (int i = 0;i < listIncomeAccountsQB.Count;i++)
        {
            listBoxIncomeAccountsQB.Items.Add(listIncomeAccountsQB[i]);
        }
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormAccountPick FormA = new FormAccountPick();
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        depAL.Add(FormA.SelectedAccount.AccountNum);
        fillDepList();
    }

    private void butRemove_Click(Object sender, EventArgs e) throws Exception {
        if (listAccountsDep.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        depAL.RemoveAt(listAccountsDep.SelectedIndex);
        fillDepList();
    }

    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        FormAccountPick FormA = new FormAccountPick();
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PickedDepAccountNum = FormA.SelectedAccount.AccountNum;
        textAccountInc.Text = Accounts.getDescript(PickedDepAccountNum);
    }

    private void butBrowseQB_Click(Object sender, EventArgs e) throws Exception {
        OpenFileDialog fdlg = new OpenFileDialog();
        fdlg.Title = "QuickBooks Company File";
        fdlg.InitialDirectory = "C:\\";
        fdlg.Filter = "QuickBooks|*.qbw";
        fdlg.RestoreDirectory = true;
        if (fdlg.ShowDialog() == DialogResult.OK)
        {
            textCompanyFileQB.Text = fdlg.FileName;
        }
         
    }

    private void butConnectQB_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textCompanyFileQB.Text.Trim(), ""))
        {
            MsgBox.show(this,"Browse to your QuickBooks company file first.");
            return ;
        }
         
        Cursor.Current = Cursors.WaitCursor;
        String result = QuickBooks.TestConnection(textCompanyFileQB.Text);
        Cursor.Current = Cursors.Default;
        MessageBox.Show(result);
    }

    private void butAddDepositQB_Click(Object sender, EventArgs e) throws Exception {
        List<String> depositList = getAccountsQB();
        if (depositList != null)
        {
            listDepositAccountsQB.AddRange(depositList);
            fillQBLists();
        }
         
    }

    private void butRemoveDepositQB_Click(Object sender, EventArgs e) throws Exception {
        if (listBoxDepositAccountsQB.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        listDepositAccountsQB.RemoveAt(listBoxDepositAccountsQB.SelectedIndex);
        fillQBLists();
    }

    private void butAddIncomeQB_Click(Object sender, EventArgs e) throws Exception {
        List<String> incomeList = getAccountsQB();
        if (incomeList != null)
        {
            listIncomeAccountsQB.AddRange(incomeList);
            fillQBLists();
        }
         
    }

    private void butRemoveIncomeQB_Click(Object sender, EventArgs e) throws Exception {
        if (listBoxIncomeAccountsQB.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        listIncomeAccountsQB.RemoveAt(listBoxIncomeAccountsQB.SelectedIndex);
        fillQBLists();
    }

    /**
    * Launches the account pick window and lets user choose several accounts.  Returns null if anything went wrong or user canceled out.
    */
    private List<String> getAccountsQB() throws Exception {
        if (StringSupport.equals(textCompanyFileQB.Text.Trim(), ""))
        {
            MsgBox.show(this,"Browse to your QuickBooks company file first.");
            return null;
        }
         
        if (Prefs.UpdateString(PrefName.QuickBooksCompanyFile, textCompanyFileQB.Text))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        FormAccountPick FormA = new FormAccountPick();
        FormA.IsQuickBooks = true;
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return null;
        }
         
        if (FormA.SelectedAccountsQB != null)
        {
            return FormA.SelectedAccountsQB;
        }
         
        return null;
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        FormAccountingAutoPayEdit FormA = new FormAccountingAutoPayEdit();
        FormA.AutoPayCur = payList[e.getRow()];
        FormA.ShowDialog();
        if (FormA.AutoPayCur == null)
        {
            //user deleted
            payList.RemoveAt(e.getRow());
        }
         
        fillPayGrid();
    }

    private void listSoftware_Click(Object sender, EventArgs e) throws Exception {
        int index = listSoftware.SelectedIndex;
        if (index == -1)
        {
            return ;
        }
         
        if (index == 0)
        {
            //Open Dental
            panelLayoutOD();
            return ;
        }
        else
        {
            //QuickBooks
            panelLayoutQB();
            return ;
        } 
    }

    /**
    * Changes the window visually for Open Dental accounting users.
    */
    private void panelLayoutOD() throws Exception {
        groupAutomaticPayment.Visible = true;
        panelQB.Visible = false;
        panelOD.Visible = true;
        panelOD.Location = new Point(27, 27);
        panelOD.Size = new Size(519, 222);
        groupAutomaticPayment.Visible = true;
        //Update the grids for this layout.
        String depStr = PrefC.getString(PrefName.AccountingDepositAccounts);
        String[] depStrArray = depStr.Split(new char[]{ ',' });
        depAL = new ArrayList();
        for (int i = 0;i < depStrArray.Length;i++)
        {
            if (StringSupport.equals(depStrArray[i], ""))
            {
                continue;
            }
             
            depAL.Add(PIn.Long(depStrArray[i]));
        }
        fillDepList();
        PickedDepAccountNum = PrefC.getLong(PrefName.AccountingIncomeAccount);
        textAccountInc.Text = Accounts.getDescript(PickedDepAccountNum);
        //pay----------------------------------------------------------
        payList = new List<AccountingAutoPay>();
        payList.AddRange(AccountingAutoPays.getListt());
        //Count might be 0
        fillPayGrid();
        PickedPayAccountNum = PrefC.getLong(PrefName.AccountingCashIncomeAccount);
        textAccountCashInc.Text = Accounts.getDescript(PickedPayAccountNum);
    }

    /**
    * Changes the window visually for QuickBooks users.
    */
    private void panelLayoutQB() throws Exception {
        groupAutomaticPayment.Visible = false;
        panelOD.Visible = false;
        panelQB.Visible = true;
        panelQB.Location = new Point(27, 27);
        panelQB.Size = new Size(519, 606);
        groupAutomaticPayment.Visible = false;
        textCompanyFileQB.Text = PrefC.getString(PrefName.QuickBooksCompanyFile);
        String acctStr = PrefC.getString(PrefName.QuickBooksIncomeAccount);
        String[] acctStrArray = acctStr.Split(new char[]{ ',' });
        listIncomeAccountsQB = new List<String>();
        for (int i = 0;i < acctStrArray.Length;i++)
        {
            if (StringSupport.equals(acctStrArray[i], ""))
            {
                continue;
            }
             
            listIncomeAccountsQB.Add(acctStrArray[i]);
        }
        String depStr = PrefC.getString(PrefName.QuickBooksDepositAccounts);
        String[] depStrArray = depStr.Split(new char[]{ ',' });
        listDepositAccountsQB = new List<String>();
        for (int i = 0;i < depStrArray.Length;i++)
        {
            if (StringSupport.equals(depStrArray[i], ""))
            {
                continue;
            }
             
            listDepositAccountsQB.Add(depStrArray[i]);
        }
        fillQBLists();
    }

    private void butAddPay_Click(Object sender, EventArgs e) throws Exception {
        AccountingAutoPay autoPay = new AccountingAutoPay();
        FormAccountingAutoPayEdit FormA = new FormAccountingAutoPayEdit();
        FormA.AutoPayCur = autoPay;
        FormA.IsNew = true;
        if (FormA.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        payList.Add(autoPay);
        fillPayGrid();
    }

    private void butChangeCash_Click(Object sender, EventArgs e) throws Exception {
        FormAccountPick FormA = new FormAccountPick();
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PickedPayAccountNum = FormA.SelectedAccount.AccountNum;
        textAccountCashInc.Text = Accounts.getDescript(PickedPayAccountNum);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listSoftware.SelectedIndex == -1)
        {
            MsgBox.show(this,"Must select an accounting software.");
            return ;
        }
         
        if (listSoftware.SelectedIndex == 0)
        {
            //Open Dental
            String depStr = "";
            for (int i = 0;i < depAL.Count;i++)
            {
                if (i > 0)
                {
                    depStr += ",";
                }
                 
                depStr += depAL[i].ToString();
            }
            if (Prefs.updateString(PrefName.AccountingDepositAccounts,depStr))
            {
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
            if (Prefs.updateLong(PrefName.AccountingIncomeAccount,PickedDepAccountNum))
            {
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
            //pay------------------------------------------------------------------------------------------
            AccountingAutoPays.saveList(payList);
            //just deletes them all and starts over
            DataValid.setInvalid(InvalidType.AccountingAutoPays);
            if (Prefs.updateLong(PrefName.AccountingCashIncomeAccount,PickedPayAccountNum))
            {
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
        }
        else
        {
            //QuickBooks
            String depStr = "";
            for (int i = 0;i < listBoxDepositAccountsQB.Items.Count;i++)
            {
                if (i > 0)
                {
                    depStr += ",";
                }
                 
                depStr += listBoxDepositAccountsQB.Items[i].ToString();
            }
            String incomeStr = "";
            for (int i = 0;i < listBoxIncomeAccountsQB.Items.Count;i++)
            {
                if (i > 0)
                {
                    incomeStr += ",";
                }
                 
                incomeStr += listBoxIncomeAccountsQB.Items[i].ToString();
            }
            if (Prefs.UpdateString(PrefName.QuickBooksCompanyFile, textCompanyFileQB.Text) | Prefs.updateString(PrefName.QuickBooksDepositAccounts,depStr) | Prefs.updateString(PrefName.QuickBooksIncomeAccount,incomeStr))
            {
                DataValid.setInvalid(InvalidType.Prefs);
            }
             
        } 
        //Update the selected accounting software.
        if (Prefs.UpdateInt(PrefName.AccountingSoftware, listSoftware.SelectedIndex))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


