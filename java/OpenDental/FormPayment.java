//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:27 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.FormCreditRecurringDateChoose;
import OpenDental.FormPayConnect;
import OpenDental.FormPayConnectSetup;
import OpenDental.FormPayment;
import OpenDental.FormPayPlanSelect;
import OpenDental.FormPaySplitEdit;
import OpenDental.FormXchargeSetup;
import OpenDental.FormXchargeTrans;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PayConnectService.transType;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Account;
import OpenDentBusiness.AccountingAutoPay;
import OpenDentBusiness.AccountingAutoPays;
import OpenDentBusiness.Accounts;
import OpenDentBusiness.AccountType;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.CreditCard;
import OpenDentBusiness.CreditCards;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Deposits;
import OpenDentBusiness.Family;
import OpenDentBusiness.JournalEntries;
import OpenDentBusiness.JournalEntry;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.PatientNotes;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PayPlanCharges;
import OpenDentBusiness.PayPlans;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.PaySplits;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Transaction;
import OpenDentBusiness.Transactions;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormPayment  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textCheckNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textBankBranch = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTotal = new System.Windows.Forms.TextBox();
    private IContainer components = new IContainer();
    /**
    * 
    */
    public boolean IsNew = false;
    private OpenDental.ValidDate textDate;
    private OpenDental.ValidDouble textAmount;
    //private Adjustments Adjustments=new Adjustments();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listPayType = new System.Windows.Forms.ListBox();
    private double tot = 0;
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDeleteAll;
    //private double[] startBal;
    //private double[] newBal;
    //private int patI;
    //private int paymentCount;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.CheckBox checkPayPlan = new System.Windows.Forms.CheckBox();
    private OpenDental.ODtextBox textNote;
    //(not including discounts)
    //private bool NoPermission=false;
    private Patient PatCur;
    private Family FamCur;
    //private PaySplit[] PaySplitPaymentList;
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPaidBy = new System.Windows.Forms.TextBox();
    private Payment PaymentCur;
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateEntry;
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private Label labelDepositAccount = new Label();
    private ComboBox comboDepositAccount = new ComboBox();
    /**
    * Set this value to a PaySplitNum if you want one of the splits highlighted when opening this form.
    */
    public long InitialPaySplit = new long();
    /**
    * A current list of splits showing on the left grid.
    */
    private List<PaySplit> SplitList = new List<PaySplit>();
    /**
    * The original splits that existed when this window was opened.  Empty for new payments.
    */
    private List<PaySplit> SplitListOld = new List<PaySplit>();
    private OpenDental.UI.ODGrid gridMain;
    private Panel panelXcharge = new Panel();
    private ContextMenu contextMenuXcharge = new ContextMenu();
    private MenuItem menuXcharge = new MenuItem();
    private TextBox textDepositAccount = new TextBox();
    private OpenDental.UI.ODGrid gridBal;
    private long[] DepositAccounts = new long[]();
    private TextBox textFamStart = new TextBox();
    private Label label10 = new Label();
    private TextBox textFamEnd = new TextBox();
    private OpenDental.UI.Button butPay;
    private TextBox textDeposit = new TextBox();
    private Label labelDeposit = new Label();
    private TextBox textFamAfterIns = new TextBox();
    private CheckBox checkPayTypeNone = new CheckBox();
    private OpenDental.UI.Button butPayConnect;
    private ContextMenu contextMenuPayConnect = new ContextMenu();
    private MenuItem menuPayConnect = new MenuItem();
    private ComboBox comboCreditCards = new ComboBox();
    private Label labelCreditCards = new Label();
    /**
    * This table gets created and filled once at the beginning.  After that, only the last column gets carefully updated.
    */
    private DataTable tableBalances = new DataTable();
    private Program prog;
    private ProgramProperty prop;
    private CheckBox checkRecurring = new CheckBox();
    private boolean payConnectWarn = new boolean();
    private List<CreditCard> creditCards = new List<CreditCard>();
    private CheckBox checkBalanceGroupByProv = new CheckBox();
    /**
    * The local override path or normal path for X-Charge.
    */
    private String xPath = new String();
    /**
    * PatCur and FamCur are not for the PatCur of the payment.  They are for the patient and family from which this window was accessed.
    */
    public FormPayment(Patient patCur, Family famCur, Payment paymentCur) throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
        PatCur = patCur;
        FamCur = famCur;
        PaymentCur = paymentCur;
        Lan.f(this);
        panelXcharge.ContextMenu = contextMenuXcharge;
        butPayConnect.ContextMenu = contextMenuPayConnect;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayment.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textCheckNum = new System.Windows.Forms.TextBox();
        this.textBankBranch = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textTotal = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.listPayType = new System.Windows.Forms.ListBox();
        this.label9 = new System.Windows.Forms.Label();
        this.checkPayPlan = new System.Windows.Forms.CheckBox();
        this.textPaidBy = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.labelDepositAccount = new System.Windows.Forms.Label();
        this.comboDepositAccount = new System.Windows.Forms.ComboBox();
        this.panelXcharge = new System.Windows.Forms.Panel();
        this.contextMenuXcharge = new System.Windows.Forms.ContextMenu();
        this.menuXcharge = new System.Windows.Forms.MenuItem();
        this.textDepositAccount = new System.Windows.Forms.TextBox();
        this.textFamStart = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textFamEnd = new System.Windows.Forms.TextBox();
        this.textDeposit = new System.Windows.Forms.TextBox();
        this.labelDeposit = new System.Windows.Forms.Label();
        this.textFamAfterIns = new System.Windows.Forms.TextBox();
        this.checkPayTypeNone = new System.Windows.Forms.CheckBox();
        this.contextMenuPayConnect = new System.Windows.Forms.ContextMenu();
        this.menuPayConnect = new System.Windows.Forms.MenuItem();
        this.comboCreditCards = new System.Windows.Forms.ComboBox();
        this.labelCreditCards = new System.Windows.Forms.Label();
        this.checkRecurring = new System.Windows.Forms.CheckBox();
        this.checkBalanceGroupByProv = new System.Windows.Forms.CheckBox();
        this.gridBal = new OpenDental.UI.ODGrid();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butPayConnect = new OpenDental.UI.Button();
        this.butPay = new OpenDental.UI.Button();
        this.textDateEntry = new OpenDental.ValidDate();
        this.textNote = new OpenDental.ODtextBox();
        this.textAmount = new OpenDental.ValidDouble();
        this.textDate = new OpenDental.ValidDate();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butDeleteAll = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(404, 2);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(154, 16);
        this.label1.TabIndex = 7;
        this.label1.Text = "Payment Type";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(12, 152);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(92, 16);
        this.label2.TabIndex = 8;
        this.label2.Text = "Note";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(4, 134);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 16);
        this.label3.TabIndex = 9;
        this.label3.Text = "Bank-Branch";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(4, 114);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(100, 16);
        this.label4.TabIndex = 10;
        this.label4.Text = "Check #";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(4, 94);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 16);
        this.label5.TabIndex = 11;
        this.label5.Text = "Amount";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(4, 74);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(100, 16);
        this.label6.TabIndex = 12;
        this.label6.Text = "Payment Date";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCheckNum
        //
        this.textCheckNum.Location = new System.Drawing.Point(106, 110);
        this.textCheckNum.Name = "textCheckNum";
        this.textCheckNum.Size = new System.Drawing.Size(100, 20);
        this.textCheckNum.TabIndex = 1;
        //
        // textBankBranch
        //
        this.textBankBranch.Location = new System.Drawing.Point(106, 130);
        this.textBankBranch.Name = "textBankBranch";
        this.textBankBranch.Size = new System.Drawing.Size(100, 20);
        this.textBankBranch.TabIndex = 2;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(212, 464);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(286, 14);
        this.label7.TabIndex = 18;
        this.label7.Text = "(must match total amount of payment)";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textTotal
        //
        this.textTotal.Location = new System.Drawing.Point(425, 438);
        this.textTotal.Name = "textTotal";
        this.textTotal.ReadOnly = true;
        this.textTotal.Size = new System.Drawing.Size(67, 20);
        this.textTotal.TabIndex = 19;
        this.textTotal.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(324, 442);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(100, 16);
        this.label8.TabIndex = 22;
        this.label8.Text = "Total Splits";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listPayType
        //
        this.listPayType.Location = new System.Drawing.Point(407, 39);
        this.listPayType.Name = "listPayType";
        this.listPayType.Size = new System.Drawing.Size(120, 95);
        this.listPayType.TabIndex = 4;
        this.listPayType.Click += new System.EventHandler(this.listPayType_Click);
        //
        // label9
        //
        this.label9.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label9.Location = new System.Drawing.Point(97, 512);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(126, 37);
        this.label9.TabIndex = 28;
        this.label9.Text = "Deletes entire payment and all splits";
        this.label9.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkPayPlan
        //
        this.checkPayPlan.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPayPlan.Location = new System.Drawing.Point(694, 116);
        this.checkPayPlan.Name = "checkPayPlan";
        this.checkPayPlan.Size = new System.Drawing.Size(196, 18);
        this.checkPayPlan.TabIndex = 30;
        this.checkPayPlan.Text = "Attached to Payment Plan";
        this.checkPayPlan.Click += new System.EventHandler(this.checkPayPlan_Click);
        //
        // textPaidBy
        //
        this.textPaidBy.Location = new System.Drawing.Point(106, 30);
        this.textPaidBy.Name = "textPaidBy";
        this.textPaidBy.ReadOnly = true;
        this.textPaidBy.Size = new System.Drawing.Size(242, 20);
        this.textPaidBy.TabIndex = 32;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(4, 32);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(100, 16);
        this.label11.TabIndex = 33;
        this.label11.Text = "Paid By";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(106, 8);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(198, 21);
        this.comboClinic.TabIndex = 92;
        this.comboClinic.SelectionChangeCommitted += new System.EventHandler(this.comboClinic_SelectionChangeCommitted);
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(16, 12);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(86, 14);
        this.labelClinic.TabIndex = 91;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(4, 54);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(100, 16);
        this.label12.TabIndex = 94;
        this.label12.Text = "Entry Date";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelDepositAccount
        //
        this.labelDepositAccount.Location = new System.Drawing.Point(407, 138);
        this.labelDepositAccount.Name = "labelDepositAccount";
        this.labelDepositAccount.Size = new System.Drawing.Size(260, 17);
        this.labelDepositAccount.TabIndex = 114;
        this.labelDepositAccount.Text = "Pay into Account";
        this.labelDepositAccount.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // comboDepositAccount
        //
        this.comboDepositAccount.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDepositAccount.FormattingEnabled = true;
        this.comboDepositAccount.Location = new System.Drawing.Point(407, 157);
        this.comboDepositAccount.Name = "comboDepositAccount";
        this.comboDepositAccount.Size = new System.Drawing.Size(260, 21);
        this.comboDepositAccount.TabIndex = 113;
        //
        // panelXcharge
        //
        this.panelXcharge.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("panelXcharge.BackgroundImage")));
        this.panelXcharge.Location = new System.Drawing.Point(694, 12);
        this.panelXcharge.Name = "panelXcharge";
        this.panelXcharge.Size = new System.Drawing.Size(59, 26);
        this.panelXcharge.TabIndex = 118;
        this.panelXcharge.MouseClick += new System.Windows.Forms.MouseEventHandler(this.panelXcharge_MouseClick);
        //
        // contextMenuXcharge
        //
        this.contextMenuXcharge.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuXcharge });
        //
        // menuXcharge
        //
        this.menuXcharge.Index = 0;
        this.menuXcharge.Text = "Settings";
        this.menuXcharge.Click += new System.EventHandler(this.menuXcharge_Click);
        //
        // textDepositAccount
        //
        this.textDepositAccount.Location = new System.Drawing.Point(407, 181);
        this.textDepositAccount.Name = "textDepositAccount";
        this.textDepositAccount.ReadOnly = true;
        this.textDepositAccount.Size = new System.Drawing.Size(260, 20);
        this.textDepositAccount.TabIndex = 119;
        //
        // textFamStart
        //
        this.textFamStart.Location = new System.Drawing.Point(773, 438);
        this.textFamStart.Name = "textFamStart";
        this.textFamStart.ReadOnly = true;
        this.textFamStart.Size = new System.Drawing.Size(60, 20);
        this.textFamStart.TabIndex = 121;
        this.textFamStart.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(672, 441);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(100, 16);
        this.label10.TabIndex = 122;
        this.label10.Text = "Family Total";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textFamEnd
        //
        this.textFamEnd.Location = new System.Drawing.Point(893, 438);
        this.textFamEnd.Name = "textFamEnd";
        this.textFamEnd.ReadOnly = true;
        this.textFamEnd.Size = new System.Drawing.Size(60, 20);
        this.textFamEnd.TabIndex = 123;
        this.textFamEnd.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textDeposit
        //
        this.textDeposit.Location = new System.Drawing.Point(694, 158);
        this.textDeposit.Name = "textDeposit";
        this.textDeposit.ReadOnly = true;
        this.textDeposit.Size = new System.Drawing.Size(100, 20);
        this.textDeposit.TabIndex = 125;
        //
        // labelDeposit
        //
        this.labelDeposit.ForeColor = System.Drawing.Color.Firebrick;
        this.labelDeposit.Location = new System.Drawing.Point(691, 139);
        this.labelDeposit.Name = "labelDeposit";
        this.labelDeposit.Size = new System.Drawing.Size(199, 16);
        this.labelDeposit.TabIndex = 126;
        this.labelDeposit.Text = "Attached to deposit";
        this.labelDeposit.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textFamAfterIns
        //
        this.textFamAfterIns.Location = new System.Drawing.Point(833, 438);
        this.textFamAfterIns.Name = "textFamAfterIns";
        this.textFamAfterIns.ReadOnly = true;
        this.textFamAfterIns.Size = new System.Drawing.Size(60, 20);
        this.textFamAfterIns.TabIndex = 127;
        this.textFamAfterIns.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // checkPayTypeNone
        //
        this.checkPayTypeNone.Location = new System.Drawing.Point(407, 21);
        this.checkPayTypeNone.Name = "checkPayTypeNone";
        this.checkPayTypeNone.Size = new System.Drawing.Size(204, 18);
        this.checkPayTypeNone.TabIndex = 128;
        this.checkPayTypeNone.Text = "None (Income Transfer)";
        this.checkPayTypeNone.UseVisualStyleBackColor = true;
        this.checkPayTypeNone.CheckedChanged += new System.EventHandler(this.checkPayTypeNone_CheckedChanged);
        this.checkPayTypeNone.Click += new System.EventHandler(this.checkPayTypeNone_Click);
        //
        // contextMenuPayConnect
        //
        this.contextMenuPayConnect.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuPayConnect });
        //
        // menuPayConnect
        //
        this.menuPayConnect.Index = 0;
        this.menuPayConnect.Text = "Settings";
        this.menuPayConnect.Click += new System.EventHandler(this.menuPayConnect_Click);
        //
        // comboCreditCards
        //
        this.comboCreditCards.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCreditCards.Location = new System.Drawing.Point(694, 65);
        this.comboCreditCards.MaxDropDownItems = 30;
        this.comboCreditCards.Name = "comboCreditCards";
        this.comboCreditCards.Size = new System.Drawing.Size(198, 21);
        this.comboCreditCards.TabIndex = 130;
        //
        // labelCreditCards
        //
        this.labelCreditCards.Location = new System.Drawing.Point(694, 45);
        this.labelCreditCards.Name = "labelCreditCards";
        this.labelCreditCards.Size = new System.Drawing.Size(198, 17);
        this.labelCreditCards.TabIndex = 131;
        this.labelCreditCards.Text = "Credit Card";
        this.labelCreditCards.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkRecurring
        //
        this.checkRecurring.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkRecurring.Location = new System.Drawing.Point(694, 97);
        this.checkRecurring.Name = "checkRecurring";
        this.checkRecurring.Size = new System.Drawing.Size(196, 18);
        this.checkRecurring.TabIndex = 132;
        this.checkRecurring.Text = "Apply to Recurring Charge";
        //
        // checkBalanceGroupByProv
        //
        this.checkBalanceGroupByProv.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBalanceGroupByProv.Location = new System.Drawing.Point(675, 208);
        this.checkBalanceGroupByProv.Name = "checkBalanceGroupByProv";
        this.checkBalanceGroupByProv.Size = new System.Drawing.Size(294, 20);
        this.checkBalanceGroupByProv.TabIndex = 133;
        this.checkBalanceGroupByProv.Text = "Group balances by provider instead of clinic, provider";
        this.checkBalanceGroupByProv.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkBalanceGroupByProv.UseVisualStyleBackColor = true;
        this.checkBalanceGroupByProv.CheckedChanged += new System.EventHandler(this.checkBalanceGroupByProv_CheckedChanged);
        //
        // gridBal
        //
        this.gridBal.setHScrollVisible(false);
        this.gridBal.Location = new System.Drawing.Point(588, 234);
        this.gridBal.Name = "gridBal";
        this.gridBal.setScrollValue(0);
        this.gridBal.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridBal.Size = new System.Drawing.Size(381, 198);
        this.gridBal.TabIndex = 120;
        this.gridBal.setTitle("Family Balances");
        this.gridBal.setTranslationName("TablePaymentBal");
        this.gridBal.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridBal.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridBal_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(7, 234);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(575, 198);
        this.gridMain.TabIndex = 116;
        this.gridMain.setTitle("Payment Splits (optional)");
        this.gridMain.setTranslationName("TablePaySplits");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butPayConnect
        //
        this.butPayConnect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPayConnect.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPayConnect.setAutosize(false);
        this.butPayConnect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPayConnect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPayConnect.setCornerRadius(4F);
        this.butPayConnect.Location = new System.Drawing.Point(782, 13);
        this.butPayConnect.Name = "butPayConnect";
        this.butPayConnect.Size = new System.Drawing.Size(75, 24);
        this.butPayConnect.TabIndex = 129;
        this.butPayConnect.Text = "PayConnect";
        this.butPayConnect.Click += new System.EventHandler(this.butPayConnect_Click);
        //
        // butPay
        //
        this.butPay.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPay.setAutosize(true);
        this.butPay.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPay.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPay.setCornerRadius(4F);
        this.butPay.Image = Resources.getLeft();
        this.butPay.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPay.Location = new System.Drawing.Point(588, 208);
        this.butPay.Name = "butPay";
        this.butPay.Size = new System.Drawing.Size(79, 24);
        this.butPay.TabIndex = 124;
        this.butPay.Text = "Pay";
        this.butPay.Click += new System.EventHandler(this.butPay_Click);
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(106, 50);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.ReadOnly = true;
        this.textDateEntry.Size = new System.Drawing.Size(100, 20);
        this.textDateEntry.TabIndex = 93;
        //
        // textNote
        //
        this.textNote.Location = new System.Drawing.Point(106, 152);
        this.textNote.MaxLength = 4000;
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Payment);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(290, 80);
        this.textNote.TabIndex = 3;
        this.textNote.Text = "";
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(106, 90);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(84, 20);
        this.textAmount.TabIndex = 0;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(106, 70);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(100, 20);
        this.textDate.TabIndex = 4;
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
        this.butCancel.Location = new System.Drawing.Point(887, 523);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 9;
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
        this.butOK.Location = new System.Drawing.Point(806, 523);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 8;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butDeleteAll
        //
        this.butDeleteAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDeleteAll.setAutosize(true);
        this.butDeleteAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteAll.setCornerRadius(4F);
        this.butDeleteAll.Image = Resources.getdeleteX();
        this.butDeleteAll.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteAll.Location = new System.Drawing.Point(7, 523);
        this.butDeleteAll.Name = "butDeleteAll";
        this.butDeleteAll.Size = new System.Drawing.Size(84, 24);
        this.butDeleteAll.TabIndex = 7;
        this.butDeleteAll.Text = "&Delete";
        this.butDeleteAll.Click += new System.EventHandler(this.butDeleteAll_Click);
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
        this.butAdd.Location = new System.Drawing.Point(7, 435);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(92, 24);
        this.butAdd.TabIndex = 30;
        this.butAdd.Text = "&Add Split";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormPayment
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(974, 562);
        this.Controls.Add(this.checkBalanceGroupByProv);
        this.Controls.Add(this.checkRecurring);
        this.Controls.Add(this.labelCreditCards);
        this.Controls.Add(this.comboCreditCards);
        this.Controls.Add(this.butPayConnect);
        this.Controls.Add(this.checkPayTypeNone);
        this.Controls.Add(this.textFamAfterIns);
        this.Controls.Add(this.textDeposit);
        this.Controls.Add(this.labelDeposit);
        this.Controls.Add(this.butPay);
        this.Controls.Add(this.textFamEnd);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.textFamStart);
        this.Controls.Add(this.gridBal);
        this.Controls.Add(this.textDepositAccount);
        this.Controls.Add(this.panelXcharge);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.labelDepositAccount);
        this.Controls.Add(this.comboDepositAccount);
        this.Controls.Add(this.textDateEntry);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.textPaidBy);
        this.Controls.Add(this.label11);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.textTotal);
        this.Controls.Add(this.textBankBranch);
        this.Controls.Add(this.textCheckNum);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butDeleteAll);
        this.Controls.Add(this.checkPayPlan);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.listPayType);
        this.Controls.Add(this.label8);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPayment";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Payment";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormPayment_Closing);
        this.Load += new System.EventHandler(this.FormPayment_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPayment_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            checkPayTypeNone.Enabled = true;
            if (!Security.isAuthorized(Permissions.PaymentCreate))
            {
                //date not checked here
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
        }
        else
        {
            checkPayTypeNone.Enabled = false;
            checkRecurring.Checked = PaymentCur.IsRecurringCC;
            if (!Security.isAuthorized(Permissions.PaymentEdit,PaymentCur.PayDate))
            {
                butOK.Enabled = false;
                butDeleteAll.Enabled = false;
                butAdd.Enabled = false;
                gridMain.Enabled = false;
                butPay.Enabled = false;
                checkRecurring.Enabled = false;
            }
             
        } 
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
            checkBalanceGroupByProv.Visible = false;
        }
        else
        {
            comboClinic.Items.Clear();
            comboClinic.Items.Add(Lan.g(this,"none"));
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (Clinics.getList()[i].ClinicNum == PaymentCur.ClinicNum)
                {
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        } 
        creditCards = CreditCards.refresh(PatCur.PatNum);
        for (int i = 0;i < creditCards.Count;i++)
        {
            comboCreditCards.Items.Add(creditCards[i].CCNumberMasked);
        }
        comboCreditCards.Items.Add("New card");
        comboCreditCards.SelectedIndex = 0;
        tableBalances = Patients.getPaymentStartingBalances(PatCur.Guarantor,PaymentCur.PayNum);
        //this works even if patient not in family
        textPaidBy.Text = FamCur.getNameInFamFL(PaymentCur.PatNum);
        textDateEntry.Text = PaymentCur.DateEntry.ToShortDateString();
        textDate.Text = PaymentCur.PayDate.ToShortDateString();
        textAmount.Text = PaymentCur.PayAmt.ToString("F");
        textCheckNum.Text = PaymentCur.CheckNum;
        textBankBranch.Text = PaymentCur.BankBranch;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()].Length;i++)
        {
            listPayType.Items.Add(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][i].DefNum == PaymentCur.PayType)
            {
                listPayType.SelectedIndex = i;
            }
             
        }
        if (PaymentCur.PayType == 0)
        {
            checkPayTypeNone.Checked = true;
        }
         
        //if(listPayType.SelectedIndex==-1) {
        //	listPayType.SelectedIndex=0;
        //}
        textNote.Text = PaymentCur.PayNote;
        if (PaymentCur.DepositNum == 0)
        {
            labelDeposit.Visible = false;
            textDeposit.Visible = false;
        }
        else
        {
            textDeposit.Text = Deposits.getOne(PaymentCur.DepositNum).DateDeposit.ToShortDateString();
            textAmount.ReadOnly = true;
            textAmount.BackColor = SystemColors.Control;
            butPay.Enabled = false;
        } 
        SplitList = PaySplits.getForPayment(PaymentCur.PayNum);
        //Count might be 0
        SplitListOld = new List<PaySplit>();
        for (int i = 0;i < SplitList.Count;i++)
        {
            //SplitListOld.AddRange(SplitList);//Do NOT do this.  It's a shallow copy only.  Not what we want.
            SplitListOld.Add(SplitList[i].Copy());
        }
        if (IsNew)
        {
            List<PayPlan> payPlanList = PayPlans.getValidPlansNoIns(PatCur.PatNum);
            if (payPlanList.Count == 0)
            {
            }
            else //
            if (payPlanList.Count == 1)
            {
                //if there is only one valid payplan
                if (!PayPlans.PlanIsPaidOff(payPlanList[0].PayPlanNum))
                {
                    addOneSplit();
                    //the amount and date will be updated upon closing
                    SplitList[SplitList.Count - 1].PayPlanNum = payPlanList[0].PayPlanNum;
                    SetPaySplitProvAndClinicForPayPlan(SplitList[SplitList.Count - 1]);
                }
                 
            }
            else
            {
                List<PayPlanCharge> chargeList = PayPlanCharges.refresh(PatCur.PatNum);
                //enhancement needed to weed out payment plans that are all paid off
                //more than one valid PayPlan
                FormPayPlanSelect FormPPS = new FormPayPlanSelect(payPlanList, chargeList);
                FormPPS.ShowDialog();
                if (FormPPS.DialogResult == DialogResult.OK)
                {
                    //return PayPlanList[FormPPS.IndexSelected].Clone();
                    addOneSplit();
                    //the amount and date will be updated upon closing
                    SplitList[SplitList.Count - 1].PayPlanNum = payPlanList[FormPPS.IndexSelected].PayPlanNum;
                    SetPaySplitProvAndClinicForPayPlan(SplitList[SplitList.Count - 1]);
                }
                 
            }  
        }
         
        /*
        				PayPlan payPlanCur=GetValidPlan(PatCur.PatNum,false);// PayPlans.GetValidPlan(PatCur.PatNum,false);
        				if(payPlanCur!=null) {//a valid payPlan was located
        					AddOneSplit();//the amount and date will be updated upon closing
        					SplitList[SplitList.Count-1].PayPlanNum=payPlanCur.PayPlanNum;
        				}*/
        fillMain();
        if (InitialPaySplit != 0)
        {
            for (int i = 0;i < SplitList.Count;i++)
            {
                if (InitialPaySplit == SplitList[i].SplitNum)
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
        }
         
        if (IsNew)
        {
            //Fill comboDepositAccount based on autopay for listPayType.SelectedIndex
            setComboDepositAccounts();
            textDepositAccount.Visible = false;
        }
        else
        {
            //put a description in the textbox.  If the user clicks on the same or another item in listPayType,
            //then the textbox will go away, and be replaced by comboDepositAccount.
            labelDepositAccount.Visible = false;
            comboDepositAccount.Visible = false;
            Transaction trans = Transactions.getAttachedToPayment(PaymentCur.PayNum);
            if (trans == null)
            {
                textDepositAccount.Visible = false;
            }
            else
            {
                //add only the description based on PaymentCur attached to transaction
                List<JournalEntry> jeL = JournalEntries.getForTrans(trans.TransactionNum);
                for (int i = 0;i < jeL.Count;i++)
                {
                    Account account = Accounts.GetAccount(jeL[i].AccountNum);
                    //The account could be null if the AccountNum was never set correctly due to the automatic payment entry setup missing an income account from older versions.
                    if (account != null && account.AcctType == AccountType.Asset)
                    {
                        textDepositAccount.Text = jeL[i].DateDisplayed.ToShortDateString();
                        if (jeL[i].DebitAmt > 0)
                        {
                            textDepositAccount.Text += " " + jeL[i].DebitAmt.ToString("c");
                        }
                        else
                        {
                            //negative
                            textDepositAccount.Text += " " + (-jeL[i].CreditAmt).ToString("c");
                        } 
                        break;
                    }
                     
                }
            } 
        } 
        checkUIState();
    }

    private void checkUIState() throws Exception {
        Program progXcharge = Programs.getCur(ProgramName.Xcharge);
        Program progPayConnect = Programs.getCur(ProgramName.PayConnect);
        if (progXcharge == null || progPayConnect == null)
        {
            //Should not happen.
            panelXcharge.Visible = (progXcharge != null);
            butPayConnect.Visible = (progPayConnect != null);
            return ;
        }
         
        panelXcharge.Visible = false;
        butPayConnect.Visible = false;
        if (!progPayConnect.Enabled && !progXcharge.Enabled)
        {
            //if neither enabled
            //show both so user can pick
            panelXcharge.Visible = true;
            butPayConnect.Visible = true;
        }
         
        //show if enabled.  User could have both enabled.
        if (progPayConnect.Enabled)
        {
            butPayConnect.Visible = true;
        }
        else if (progXcharge.Enabled)
        {
            panelXcharge.Visible = true;
        }
          
    }

    /**
    * This does not make any calls to db (except one tiny one).  Simply refreshes screen for SplitList.
    */
    private void fillMain() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePaySplits","Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaySplits","Prov"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaySplits","Clinic"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaySplits","Patient"),130);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaySplits","Procedure"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaySplits","Amount"), 60, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaySplits","Unearned"),50);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        tot = 0;
        Procedure proc;
        String procDesc = new String();
        for (int i = 0;i < SplitList.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(SplitList[i].ProcDate.ToShortDateString());
            row.getCells().Add(Providers.GetAbbr(SplitList[i].ProvNum));
            row.getCells().Add(Clinics.GetDesc(SplitList[i].ClinicNum));
            row.getCells().Add(FamCur.GetNameInFamFL(SplitList[i].PatNum));
            if (SplitList[i].ProcNum > 0)
            {
                proc = Procedures.GetOneProc(SplitList[i].ProcNum, false);
                procDesc = Procedures.getDescription(proc);
                row.getCells().add(procDesc);
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(SplitList[i].SplitAmt.ToString("F"));
            row.getCells().Add(DefC.GetName(DefCat.PaySplitUnearnedType, SplitList[i].UnearnedType));
            //handles 0 just fine
            tot += SplitList[i].SplitAmt;
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        textTotal.Text = tot.ToString("F");
        if (SplitList.Count == 1)
        {
            checkPayPlan.Enabled = true;
            if (((PaySplit)SplitList[0]).PayPlanNum > 0)
            {
                checkPayPlan.Checked = true;
            }
            else
            {
                checkPayPlan.Checked = false;
            } 
        }
        else
        {
            checkPayPlan.Checked = false;
            checkPayPlan.Enabled = false;
        } 
        fillGridBal();
    }

    /**
    * 
    */
    private void fillGridBal() throws Exception {
        //can't do this: SplitList=PaySplits.GetForPayment(PaymentCur.PayNum);//Count might be 0
        //too slow: tableBalances=Patients.GetPaymentStartingBalances(PatCur.Guarantor,PaymentCur.PayNum,checkBalanceGroupByProv.Checked);
        double famstart = 0;
        for (int i = 0;i < tableBalances.Rows.Count;i++)
        {
            famstart += PIn.Double(tableBalances.Rows[i]["StartBal"].ToString());
        }
        textFamStart.Text = famstart.ToString("N");
        double famafterins = 0;
        for (int i = 0;i < tableBalances.Rows.Count;i++)
        {
            famafterins += PIn.Double(tableBalances.Rows[i]["AfterIns"].ToString());
        }
        if (!PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            textFamAfterIns.Text = famafterins.ToString("N");
        }
         
        for (int i = 0;i < tableBalances.Rows.Count;i++)
        {
            //compute ending balances-----------------------------------------------------------------------------
            if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                tableBalances.Rows[i]["EndBal"] = tableBalances.Rows[i]["StartBal"].ToString();
            }
            else
            {
                tableBalances.Rows[i]["EndBal"] = tableBalances.Rows[i]["AfterIns"].ToString();
            } 
        }
        double amt = new double();
        for (int i = 0;i < SplitList.Count;i++)
        {
            for (int f = 0;f < tableBalances.Rows.Count;f++)
            {
                //loop through each current paysplit that's showing
                //loop through the balances on the right
                if (tableBalances.Rows[f]["PatNum"].ToString() != SplitList[i].PatNum.ToString())
                {
                    continue;
                }
                 
                if (tableBalances.Rows[f]["ProvNum"].ToString() != SplitList[i].ProvNum.ToString())
                {
                    continue;
                }
                 
                if (checkBalanceGroupByProv.Checked)
                {
                }
                else
                {
                    //more inclusive.  Multiple clinics from left will be included as long as the prov matches.
                    //box not checked, so filter by clinic
                    if (tableBalances.Rows[f]["ClinicNum"].ToString() != SplitList[i].ClinicNum.ToString())
                    {
                        continue;
                    }
                     
                } 
                //sum up the amounts from the grid at the left which we want to apply to the grid on the right.
                amt = PIn.Double(tableBalances.Rows[f]["EndBal"].ToString()) - SplitList[i].SplitAmt;
                //this is summing over multiple i and f loops.  NOT elegantly.
                tableBalances.Rows[f]["EndBal"] = amt.ToString("N");
            }
        }
        double famend = 0;
        for (int i = 0;i < tableBalances.Rows.Count;i++)
        {
            famend += PIn.Double(tableBalances.Rows[i]["EndBal"].ToString());
        }
        textFamEnd.Text = famend.ToString("N");
        //fill grid--------------------------------------------------------------------------------------------
        gridBal.beginUpdate();
        gridBal.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePaymentBal","Prov"),60);
        gridBal.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentBal","Clinic"),60);
        gridBal.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentBal","Patient"),62);
        gridBal.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentBal","Start"), 60, HorizontalAlignment.Right);
        gridBal.getColumns().add(col);
        if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
        {
            col = new ODGridColumn("",60);
        }
        else
        {
            col = new ODGridColumn(Lan.g("TablePaymentBal","After Ins"), 60, HorizontalAlignment.Right);
        } 
        gridBal.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentBal","End"), 60, HorizontalAlignment.Right);
        gridBal.getColumns().add(col);
        gridBal.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < tableBalances.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Providers.GetAbbr(PIn.Long(tableBalances.Rows[i]["ProvNum"].ToString())));
            if (checkBalanceGroupByProv.Checked)
            {
                row.getCells().add("");
            }
            else
            {
                //show blank.  Value in datatable will be a random clinic.
                row.getCells().Add(Clinics.GetDesc(PIn.Long(tableBalances.Rows[i]["ClinicNum"].ToString())));
            } 
            if (StringSupport.equals(tableBalances.Rows[i]["Preferred"].ToString(), ""))
            {
                row.getCells().Add(tableBalances.Rows[i]["FName"].ToString());
            }
            else
            {
                row.getCells().add("'" + tableBalances.Rows[i]["Preferred"].ToString() + "'");
            } 
            row.getCells().Add(PIn.Double(tableBalances.Rows[i]["StartBal"].ToString()).ToString("N"));
            if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PIn.Double(tableBalances.Rows[i]["AfterIns"].ToString()).ToString("N"));
            } 
            row.getCells().Add(PIn.Double(tableBalances.Rows[i]["EndBal"].ToString()).ToString("N"));
            //row.ColorBackG=SystemColors.Control;//Color.FromArgb(240,240,240);
            gridBal.getRows().add(row);
        }
        gridBal.endUpdate();
    }

    private void checkBalanceGroupByProv_CheckedChanged(Object sender, EventArgs e) throws Exception {
        if (checkBalanceGroupByProv.Checked)
        {
            butPay.Enabled = false;
        }
        else
        {
            butPay.Enabled = true;
        } 
        tableBalances = Patients.GetPaymentStartingBalances(PatCur.Guarantor, PaymentCur.PayNum, checkBalanceGroupByProv.Checked);
        fillGridBal();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormPaySplitEdit FormPS = new FormPaySplitEdit(FamCur);
        FormPS.PaySplitCur = SplitList[e.getRow()];
        FormPS.Remain = PaymentCur.PayAmt - PIn.Double(textTotal.Text) + SplitList[e.getRow()].SplitAmt;
        FormPS.ShowDialog();
        if (FormPS.PaySplitCur == null)
        {
            //user deleted
            SplitList.RemoveAt(e.getRow());
        }
         
        //if(FormPS.ShowDialog()==DialogResult.OK){
        fillMain();
    }

    //}
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        PaySplit PaySplitCur = new PaySplit();
        PaySplitCur.PayNum = PaymentCur.PayNum;
        PaySplitCur.DateEntry = MiscData.getNowDateTime();
        //just a nicity for the user.  Insert uses server time.
        PaySplitCur.DatePay = PIn.Date(textDate.Text);
        //this may be updated upon closing
        PaySplitCur.ProcDate = PIn.Date(textDate.Text);
        //this may be updated upon closing
        PaySplitCur.ProvNum = Patients.getProvNum(PatCur);
        PaySplitCur.PatNum = PatCur.PatNum;
        PaySplitCur.ClinicNum = PaymentCur.ClinicNum;
        FormPaySplitEdit FormPS = new FormPaySplitEdit(FamCur);
        FormPS.PaySplitCur = PaySplitCur;
        FormPS.IsNew = true;
        FormPS.Remain = PaymentCur.PayAmt - PIn.Double(textTotal.Text);
        if (FormPS.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        SplitList.Add(PaySplitCur);
        fillMain();
    }

    private void butPay_Click(Object sender, EventArgs e) throws Exception {
        if (gridBal.getSelectedIndices().Length == 0)
        {
            gridBal.setSelected(true);
        }
         
        SplitList.Clear();
        double amt = new double();
        PaySplit split;
        for (int i = 0;i < gridBal.getSelectedIndices().Length;i++)
        {
            if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                amt = PIn.Double(tableBalances.Rows[gridBal.getSelectedIndices()[i]]["StartBal"].ToString());
            }
            else
            {
                amt = PIn.Double(tableBalances.Rows[gridBal.getSelectedIndices()[i]]["AfterIns"].ToString());
            } 
            if (amt == 0)
            {
                continue;
            }
             
            split = new PaySplit();
            split.PatNum = PIn.Long(tableBalances.Rows[gridBal.getSelectedIndices()[i]]["PatNum"].ToString());
            split.PayNum = PaymentCur.PayNum;
            split.ProcDate = PaymentCur.PayDate;
            //this may be updated upon closing
            split.DatePay = PaymentCur.PayDate;
            //this may be updated upon closing
            split.ProvNum = PIn.Long(tableBalances.Rows[gridBal.getSelectedIndices()[i]]["ProvNum"].ToString());
            split.ClinicNum = PIn.Long(tableBalances.Rows[gridBal.getSelectedIndices()[i]]["ClinicNum"].ToString());
            split.SplitAmt = amt;
            SplitList.Add(split);
        }
        fillMain();
        textAmount.Text = textTotal.Text;
    }

    private void checkPayPlan_Click(Object sender, System.EventArgs e) throws Exception {
        //*****if there is more than one split, then this checkbox is not even available.
        if (SplitList.Count == 0)
        {
            addOneSplit();
            //won't use returned value
            fillMain();
            checkPayPlan.Checked = true;
        }
         
        //now there is exactly one.  The amount will be updated as the form closes.
        if (checkPayPlan.Checked)
        {
            //PayPlan payPlanCur=PayPlans.GetValidPlan(SplitList[0].PatNum);
            List<PayPlan> payPlanList = PayPlans.GetValidPlansNoIns(SplitList[0].PatNum);
            if (payPlanList.Count == 0)
            {
                MsgBox.show(this,"The selected patient is not the guarantor for any payment plans.");
                checkPayPlan.Checked = false;
                return ;
            }
            else if (payPlanList.Count == 1)
            {
                //if there is only one valid payplan
                SplitList[0].PayPlanNum = payPlanList[0].PayPlanNum;
                SetPaySplitProvAndClinicForPayPlan(SplitList[0]);
            }
            else
            {
                //multiple valid plans
                List<PayPlanCharge> chargeList = PayPlanCharges.Refresh(SplitList[0].PatNum);
                //enhancement needed to weed out payment plans that are all paid off
                //more than one valid PayPlan
                FormPayPlanSelect FormPPS = new FormPayPlanSelect(payPlanList, chargeList);
                FormPPS.ShowDialog();
                if (FormPPS.DialogResult == DialogResult.OK)
                {
                    SplitList[0].PayPlanNum = payPlanList[FormPPS.IndexSelected].PayPlanNum;
                    SetPaySplitProvAndClinicForPayPlan(SplitList[0]);
                }
                else
                {
                    checkPayPlan.Checked = false;
                    return ;
                } 
            }  
        }
        else
        {
            /*
            				if(payPlanCur==null){//no valid plans
            					MsgBox.Show(this,"The selected patient is not the guarantor for any payment plans.");
            					checkPayPlan.Checked=false;
            					return;
            				}
            				SplitList[0].PayPlanNum=payPlanCur.PayPlanNum;*/
            //payPlan unchecked
            SplitList[0].PayPlanNum = 0;
        } 
        //User can go in and manually edit the provider and clinic if they need to at this point.
        fillMain();
    }

    /**
    * Adds one split to work with.  Called when checkPayPlan click, or upon load if auto attaching to payplan.
    */
    private void addOneSplit() throws Exception {
        PaySplit PaySplitCur = new PaySplit();
        PaySplitCur.PatNum = PatCur.PatNum;
        PaySplitCur.PayNum = PaymentCur.PayNum;
        PaySplitCur.ProcDate = PaymentCur.PayDate;
        //this may be updated upon closing
        PaySplitCur.DatePay = PaymentCur.PayDate;
        //this may be updated upon closing
        PaySplitCur.ProvNum = Patients.getProvNum(PatCur);
        PaySplitCur.ClinicNum = PaymentCur.ClinicNum;
        PaySplitCur.SplitAmt = PIn.Double(textAmount.Text);
        SplitList.Add(PaySplitCur);
    }

    /**
    * Updates the passed in paysplit with the provider and clinic that is set for the payment plan charges.  PayPlanNum should already be set for the split.
    */
    private void setPaySplitProvAndClinicForPayPlan(PaySplit split) throws Exception {
        if (split.PayPlanNum == 0)
        {
            return ;
        }
         
        //PayPlanNum was not set, this should never happen.
        List<PayPlanCharge> charges = PayPlanCharges.getForPayPlan(split.PayPlanNum);
        //The payment plan doesn't save/store this information
        if (charges.Count > 0)
        {
            //All charges linked to a payplan share the same clinic and provider.  Just use the first one in the list.
            if (charges[0].ProvNum > 0)
            {
                //This should never fail.
                split.ProvNum = charges[0].ProvNum;
            }
             
            if (charges[0].ClinicNum > 0)
            {
                //It is possible to not set a clinic for pay plan charges.
                split.ClinicNum = charges[0].ClinicNum;
            }
             
        }
         
    }

    private void listPayType_Click(Object sender, EventArgs e) throws Exception {
        textDepositAccount.Visible = false;
        setComboDepositAccounts();
    }

    /**
    * Called from all 3 places where listPayType gets changed.
    */
    private void setComboDepositAccounts() throws Exception {
        if (listPayType.SelectedIndex == -1)
        {
            return ;
        }
         
        AccountingAutoPay autoPay = AccountingAutoPays.GetForPayType(DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][listPayType.SelectedIndex].DefNum);
        if (autoPay == null)
        {
            labelDepositAccount.Visible = false;
            comboDepositAccount.Visible = false;
        }
        else
        {
            labelDepositAccount.Visible = true;
            comboDepositAccount.Visible = true;
            DepositAccounts = AccountingAutoPays.getPickListAccounts(autoPay);
            comboDepositAccount.Items.Clear();
            for (int i = 0;i < DepositAccounts.Length;i++)
            {
                comboDepositAccount.Items.Add(Accounts.GetDescript(DepositAccounts[i]));
            }
            if (comboDepositAccount.Items.Count > 0)
            {
                comboDepositAccount.SelectedIndex = 0;
            }
             
        } 
    }

    private void panelXcharge_MouseClick(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        if (StringSupport.equals(textAmount.Text, ""))
        {
            MsgBox.show(this,"Please enter an amount first.");
            return ;
        }
         
        if (!hasXCharge())
        {
            return ;
        }
         
        boolean needToken = false;
        boolean newCard = false;
        boolean hasXToken = false;
        boolean notRecurring = false;
        prop = (ProgramProperty)ProgramProperties.getForProgram(prog.ProgramNum)[0];
        //still need to add functionality for accountingAutoPay
        listPayType.SelectedIndex = DefC.GetOrder(DefCat.PaymentTypes, PIn.Long(prop.PropertyValue));
        setComboDepositAccounts();
        /*XCharge.exe [/TRANSACTIONTYPE:type] [/AMOUNT:amount] [/ACCOUNT:account] [/EXP:exp]
        				[“/TRACK:track”] [/ZIP:zip] [/ADDRESS:address] [/RECEIPT:receipt] [/CLERK:clerk]
        				[/APPROVALCODE:approval] [/AUTOPROCESS] [/AUTOCLOSE] [/STAYONTOP] [/MID]
        				[/RESULTFILE:”C:\Program Files\X-Charge\LocalTran\XCResult.txt”*/
        ProcessStartInfo info = new ProcessStartInfo(xPath);
        Patient pat = Patients.getPat(PaymentCur.PatNum);
        PatientNote patnote = PatientNotes.refresh(pat.PatNum,pat.Guarantor);
        String resultfile = Path.Combine(Path.GetDirectoryName(xPath), "XResult.txt");
        File.Delete(resultfile);
        //delete the old result file.
        info.Arguments = "";
        double amt = PIn.Double(textAmount.Text);
        if (amt < 0)
        {
            //X-Charge always wants a positive number, even for returns.
            amt *= -1;
        }
         
        info.Arguments += "/AMOUNT:" + amt.ToString("F2") + " /LOCKAMOUNT ";
        CreditCard CCard = null;
        List<CreditCard> creditCards = CreditCards.refresh(PatCur.PatNum);
        for (int i = 0;i < creditCards.Count;i++)
        {
            if (i == comboCreditCards.SelectedIndex)
            {
                CCard = creditCards[i];
            }
             
        }
        //Show window to lock in the transaction type.
        FormXchargeTrans FormXT = new FormXchargeTrans();
        FormXT.ShowDialog();
        if (FormXT.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        int tranType = FormXT.TransactionType;
        String cashBack = FormXT.CashBackAmount.ToString("F2");
        if (CCard != null)
        {
            //Have credit card on file
            if (!StringSupport.equals(CCard.XChargeToken, ""))
            {
                //Recurring charge
                hasXToken = true;
                if (CreditCards.isDuplicateXChargeToken(CCard.XChargeToken))
                {
                    MsgBox.show(this,"This card shares a token with another card. Delete it from the Credit Card Manage window and re-add it.");
                    return ;
                }
                 
            }
            else
            {
                /*       ***** An example of how recurring charges work***** 
                					C:\Program Files\X-Charge\XCharge.exe /TRANSACTIONTYPE:Purchase /LOCKTRANTYPE
                					/AMOUNT:10.00 /LOCKAMOUNT /XCACCOUNTID:XAW0JWtx5kjG8 /RECEIPT:RC001
                					/LOCKRECEIPT /CLERK:Clerk /LOCKCLERK /RESULTFILE:C:\ResultFile.txt /USERID:system
                					/PASSWORD:system /STAYONTOP /AUTOPROCESS /AUTOCLOSE /HIDEMAINWINDOW
                					/RECURRING /SMALLWINDOW /NORESULTDIALOG
                					*/
                //Not recurring charge, on file and might need a token.
                notRecurring = true;
                if (!PrefC.getBool(PrefName.StoreCCnumbers))
                {
                    //Use token only if user has has pref unchecked in module setup (allow store credit card nums).
                    needToken = true;
                }
                 
            } 
        }
        else
        {
            //Will create a token from result file so credit card info isn't saved in our db.
            //Add card option was selected in credit card drop down. No other possibility.
            newCard = true;
        } 
        info.Arguments += getXChargeTransactionTypeCommands(tranType,hasXToken,notRecurring,CCard,cashBack);
        if (newCard)
        {
            info.Arguments += "\"/ZIP:" + pat.Zip + "\" ";
            info.Arguments += "\"/ADDRESS:" + pat.Address + "\" ";
        }
        else
        {
            if (CCard.CCExpiration != null && CCard.CCExpiration.Year > 2005)
            {
                info.Arguments += "/EXP:" + CCard.CCExpiration.ToString("MMyy") + " ";
            }
             
            if (!StringSupport.equals(CCard.Zip, ""))
            {
                info.Arguments += "\"/ZIP:" + CCard.Zip + "\" ";
            }
            else
            {
                info.Arguments += "\"/ZIP:" + pat.Zip + "\" ";
            } 
            if (!StringSupport.equals(CCard.Address, ""))
            {
                info.Arguments += "\"/ADDRESS:" + CCard.Address + "\" ";
            }
            else
            {
                info.Arguments += "\"/ADDRESS:" + pat.Address + "\" ";
            } 
            if (hasXToken)
            {
                //Special parameter for tokens.
                info.Arguments += "/RECURRING ";
            }
             
        } 
        info.Arguments += "/RECEIPT:Pat" + PaymentCur.PatNum.ToString() + " ";
        //aka invoice#
        info.Arguments += "\"/CLERK:" + Security.getCurUser().UserName + "\" /LOCKCLERK ";
        info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
        info.Arguments += "/USERID:" + ProgramProperties.getPropVal(prog.ProgramNum,"Username") + " ";
        info.Arguments += "/PASSWORD:" + ProgramProperties.getPropVal(prog.ProgramNum,"Password") + " ";
        info.Arguments += "/PARTIALAPPROVALSUPPORT:T ";
        info.Arguments += "/AUTOCLOSE ";
        info.Arguments += "/HIDEMAINWINDOW ";
        info.Arguments += "/SMALLWINDOW ";
        info.Arguments += "/GETXCACCOUNTID ";
        info.Arguments += "/NORESULTDIALOG ";
        Cursor = Cursors.WaitCursor;
        Process process = new Process();
        process.StartInfo = info;
        process.EnableRaisingEvents = true;
        process.Start();
        while (!process.HasExited)
        {
            Application.DoEvents();
        }
        Thread.Sleep(200);
        //Wait 2/10 second to give time for file to be created.
        Cursor = Cursors.Default;
        String resulttext = "";
        String line = "";
        boolean showApprovedAmtNotice = false;
        boolean xAdjust = false;
        boolean xReturn = false;
        boolean xVoid = false;
        double approvedAmt = 0;
        double additionalFunds = 0;
        String xChargeToken = "";
        String accountMasked = "";
        String expiration = "";
        TextReader reader = new StreamReader(resultfile);
        try
        {
            {
                line = reader.ReadLine();
                while (line != null)
                {
                    /*Example of successful transaction:
                    					RESULT=SUCCESS
                    					TYPE=Purchase
                    					APPROVALCODE=000064
                    					ACCOUNT=XXXXXXXXXXXX6781
                    					ACCOUNTTYPE=VISA*
                    					AMOUNT=1.77
                    					AVSRESULT=Y
                    					CVRESULT=M
                    				*/
                    if (!StringSupport.equals(resulttext, ""))
                    {
                        resulttext += "\r\n";
                    }
                     
                    resulttext += line;
                    if (line.StartsWith("RESULT="))
                    {
                        if (!StringSupport.equals(line, "RESULT=SUCCESS"))
                        {
                            while (line != null)
                            {
                                //Charge was a failure and there might be a description as to why it failed. Continue to loop through line.
                                line = reader.ReadLine();
                                resulttext += "\r\n" + line;
                            }
                            needToken = false;
                            //Don't update CCard due to failure
                            newCard = false;
                            break;
                        }
                         
                        //Don't insert CCard due to failure
                        if (tranType == 1)
                        {
                            xReturn = true;
                        }
                         
                        if (tranType == 6)
                        {
                            xAdjust = true;
                        }
                         
                        if (tranType == 7)
                        {
                            xVoid = true;
                        }
                         
                    }
                     
                    if (line.StartsWith("APPROVEDAMOUNT="))
                    {
                        approvedAmt = PIn.Double(line.Substring(15));
                        if (approvedAmt != amt)
                        {
                            showApprovedAmtNotice = true;
                        }
                         
                    }
                     
                    if (line.StartsWith("XCACCOUNTID="))
                    {
                        xChargeToken = PIn.String(line.Substring(12));
                    }
                     
                    if (line.StartsWith("ACCOUNT="))
                    {
                        accountMasked = PIn.String(line.Substring(8));
                    }
                     
                    if (line.StartsWith("EXPIRATION="))
                    {
                        expiration = PIn.String(line.Substring(11));
                    }
                     
                    if (line.StartsWith("ADDITIONALFUNDSREQUIRED="))
                    {
                        additionalFunds = PIn.Double(line.Substring(24));
                    }
                     
                    line = reader.ReadLine();
                }
                if (needToken && !StringSupport.equals(xChargeToken, ""))
                {
                    //Only way this code can be hit is if they have set up a credit card and it does not have a token.
                    //So we'll use the created token from result file and assign it to the coresponding account.
                    //Also will delete the credit card number and replace it with secure masked number.
                    CCard.XChargeToken = xChargeToken;
                    CCard.CCNumberMasked = accountMasked;
                    CCard.CCExpiration = new DateTime(Convert.ToInt32("20" + expiration.Substring(2, 2)), Convert.ToInt32(expiration.Substring(0, 2)), 1);
                    CreditCards.update(CCard);
                }
                 
                if (newCard)
                {
                    if (StringSupport.equals(xChargeToken, ""))
                    {
                        //Shouldn't happen again but leaving just in case.
                        MsgBox.show(this,"X-Charge didn't return a token so credit card information couldn't be saved.");
                    }
                    else
                    {
                        if (FormXT.SaveToken)
                        {
                            CCard = new CreditCard();
                            List<CreditCard> itemOrderCount = CreditCards.refresh(PatCur.PatNum);
                            CCard.ItemOrder = itemOrderCount.Count;
                            CCard.PatNum = PatCur.PatNum;
                            CCard.CCExpiration = new DateTime(Convert.ToInt32("20" + expiration.Substring(2, 2)), Convert.ToInt32(expiration.Substring(0, 2)), 1);
                            CCard.XChargeToken = xChargeToken;
                            CCard.CCNumberMasked = accountMasked;
                            CreditCards.insert(CCard);
                        }
                         
                    } 
                }
                 
            }
        }
        finally
        {
            if (reader != null)
                Disposable.mkDisposable(reader).dispose();
             
        }
        if (showApprovedAmtNotice && !xVoid && !xAdjust && !xReturn)
        {
            MessageBox.Show(Lan.g(this,"The amount you typed in: ") + amt.ToString("C") + "\r\n" + Lan.g(this,"does not match the approved amount returned: ") + approvedAmt.ToString("C") + "\r\n" + Lan.g(this,"The amount will be changed to reflect the approved amount charged."), "Alert", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            textAmount.Text = approvedAmt.ToString("F");
        }
         
        if (xAdjust)
        {
            MessageBox.Show(Lan.g(this,"The amount will be changed to the X-Charge approved amount: ") + approvedAmt.ToString("C"));
            textNote.Text = "";
            textAmount.Text = approvedAmt.ToString("F");
        }
        else if (xReturn)
        {
            textAmount.Text = "-" + approvedAmt.ToString("F");
        }
        else if (xVoid)
        {
            if (MsgBox.show(this,MsgBoxButtons.YesNo,"The void was successful, remove this payment entry from Open Dental?"))
            {
                butDeleteAll_Click(sender, e);
            }
             
        }
           
        if (additionalFunds > 0)
        {
            MessageBox.Show(Lan.g(this,"Additional funds required: ") + additionalFunds.ToString("C"));
        }
         
        if (!StringSupport.equals(textNote.Text, ""))
        {
            textNote.Text += "\r\n";
        }
         
        textNote.Text += resulttext;
    }

    private void voidXChargeTransaction(String transID, String amount, boolean isDebit) throws Exception {
        ProcessStartInfo info = new ProcessStartInfo(prog.Path);
        String resultfile = Path.Combine(Path.GetDirectoryName(prog.Path), "XResult.txt");
        File.Delete(resultfile);
        //delete the old result file.
        info.Arguments = "";
        if (isDebit)
        {
            info.Arguments += "/TRANSACTIONTYPE:DEBITRETURN /LOCKTRANTYPE ";
        }
        else
        {
            info.Arguments += "/TRANSACTIONTYPE:VOID /LOCKTRANTYPE ";
        } 
        info.Arguments += "/XCTRANSACTIONID:" + transID + " /LOCKXCTRANSACTIONID ";
        info.Arguments += "/AMOUNT:" + amount + " /LOCKAMOUNT ";
        info.Arguments += "/RECEIPT:Pat" + PaymentCur.PatNum.ToString() + " ";
        //aka invoice#
        info.Arguments += "\"/CLERK:" + Security.getCurUser().UserName + "\" /LOCKCLERK ";
        info.Arguments += "/RESULTFILE:\"" + resultfile + "\" ";
        info.Arguments += "/USERID:" + ProgramProperties.getPropVal(prog.ProgramNum,"Username") + " ";
        info.Arguments += "/PASSWORD:" + ProgramProperties.getPropVal(prog.ProgramNum,"Password") + " ";
        info.Arguments += "/AUTOCLOSE ";
        info.Arguments += "/HIDEMAINWINDOW /SMALLWINDOW ";
        if (!isDebit)
        {
            info.Arguments += "/AUTOPROCESS ";
        }
         
        Cursor = Cursors.WaitCursor;
        Process process = new Process();
        process.StartInfo = info;
        process.EnableRaisingEvents = true;
        process.Start();
        while (!process.HasExited)
        {
            Application.DoEvents();
        }
        Thread.Sleep(200);
        //Wait 2/10 second to give time for file to be created.
        Cursor = Cursors.Default;
    }

    private boolean hasXCharge() throws Exception {
        prog = Programs.getCur(ProgramName.Xcharge);
        xPath = Programs.getProgramPath(prog);
        if (prog == null)
        {
            MsgBox.show(this,"X-Charge entry is missing from the database.");
            return false;
        }
         
        //should never happen
        if (!prog.Enabled)
        {
            if (Security.isAuthorized(Permissions.Setup))
            {
                FormXchargeSetup FormX = new FormXchargeSetup();
                FormX.ShowDialog();
            }
             
            return false;
        }
         
        if (!File.Exists(xPath))
        {
            MsgBox.show(this,"Path is not valid.");
            if (Security.isAuthorized(Permissions.Setup))
            {
                FormXchargeSetup FormX = new FormXchargeSetup();
                FormX.ShowDialog();
            }
             
            return false;
        }
         
        return true;
    }

    private String getXChargeTransactionTypeCommands(int tranType, boolean hasXToken, boolean notRecurring, CreditCard CCard, String cashBack) throws Exception {
        String tranText = "";
        switch(tranType)
        {
            case 0: 
                tranText += "/TRANSACTIONTYPE:PURCHASE /LOCKTRANTYPE ";
                if (hasXToken)
                {
                    tranText += "/XCACCOUNTID:" + CCard.XChargeToken + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                if (notRecurring)
                {
                    tranText += "/ACCOUNT:" + CCard.CCNumberMasked + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                break;
            case 1: 
                tranText += "/TRANSACTIONTYPE:RETURN /LOCKTRANTYPE ";
                if (hasXToken)
                {
                    tranText += "/XCACCOUNTID:" + CCard.XChargeToken + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                if (notRecurring)
                {
                    tranText += "/ACCOUNT:" + CCard.CCNumberMasked + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                break;
            case 2: 
                tranText += "/TRANSACTIONTYPE:DEBITPURCHASE /LOCKTRANTYPE ";
                tranText += "/CASHBACK:" + cashBack + " ";
                break;
            case 3: 
                tranText += "/TRANSACTIONTYPE:DEBITRETURN /LOCKTRANTYPE ";
                break;
            case 4: 
                tranText += "/TRANSACTIONTYPE:FORCE /LOCKTRANTYPE ";
                break;
            case 5: 
                tranText += "/TRANSACTIONTYPE:PREAUTH /LOCKTRANTYPE ";
                if (hasXToken)
                {
                    tranText += "/XCACCOUNTID:" + CCard.XChargeToken + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                if (notRecurring)
                {
                    tranText += "/ACCOUNT:" + CCard.CCNumberMasked + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                break;
            case 6: 
                tranText += "/TRANSACTIONTYPE:ADJUSTMENT /LOCKTRANTYPE ";
                String adjustTransactionID = "";
                String[] noteSplit = Regex.Split(textNote.Text, "\r\n");
                for (Object __dummyForeachVar0 : noteSplit)
                {
                    String XCTrans = (String)__dummyForeachVar0;
                    if (XCTrans.StartsWith("XCTRANSACTIONID="))
                    {
                        adjustTransactionID = XCTrans.Substring(16);
                    }
                     
                }
                if (!StringSupport.equals(adjustTransactionID, ""))
                {
                    tranText += "/XCTRANSACTIONID:" + adjustTransactionID + " ";
                    tranText += "/AUTOPROCESS ";
                }
                 
                break;
            case 7: 
                tranText += "/TRANSACTIONTYPE:VOID /LOCKTRANTYPE ";
                break;
        
        }
        return tranText;
    }

    private void butPayConnect_Click(Object sender, EventArgs e) throws Exception {
        Program prog = Programs.getCur(ProgramName.PayConnect);
        if (!prog.Enabled)
        {
            FormPayConnectSetup fpcs = new FormPayConnectSetup();
            fpcs.ShowDialog();
            checkUIState();
            return ;
        }
         
        if (StringSupport.equals(textAmount.Text, "") || StringSupport.equals(textAmount.Text, "0.00"))
        {
            MsgBox.show(this,"Please enter an amount first.");
            return ;
        }
         
        CreditCard CCard = null;
        List<CreditCard> creditCards = CreditCards.refresh(PatCur.PatNum);
        for (int i = 0;i < creditCards.Count;i++)
        {
            if (i == comboCreditCards.SelectedIndex)
            {
                CCard = creditCards[i];
            }
             
        }
        FormPayConnect FormP;
        FormP = new FormPayConnect(PaymentCur, PatCur, textAmount.Text, CCard);
        FormP.ShowDialog();
        ArrayList props = ProgramProperties.getForProgram(prog.ProgramNum);
        ProgramProperty prop = null;
        for (int i = 0;i < props.Count;i++)
        {
            ProgramProperty curProp = (ProgramProperty)props[i];
            if (StringSupport.equals(curProp.PropertyDesc, "PaymentType"))
            {
                prop = curProp;
                break;
            }
             
        }
        //still need to add functionality for accountingAutoPay
        listPayType.SelectedIndex = DefC.GetOrder(DefCat.PaymentTypes, PIn.Long(prop.PropertyValue));
        setComboDepositAccounts();
        if (FormP.getResponse() != null)
        {
            textNote.Text += ((StringSupport.equals(textNote.Text, "")) ? "" : Environment.NewLine) + Lan.g(this,"Transaction Type") + ": " + Enum.GetName(transType.class, FormP.getTranType()) + Environment.NewLine + Lan.g(this,"Status") + ": " + FormP.getResponse().getStatus().getdescription();
            if (FormP.getResponse().getStatus().getcode() == 0)
            {
                //The transaction succeeded.
                payConnectWarn = true;
                //Show a warning if user cancels out of window.
                textNote.Text += Environment.NewLine + Lan.g(this,"Amount") + ": " + FormP.getAmountCharged() + Environment.NewLine + Lan.g(this,"Auth Code") + ": " + FormP.getResponse().getAuthCode() + Environment.NewLine + Lan.g(this,"Ref Number") + ": " + FormP.getResponse().getRefNumber();
                textNote.Select(textNote.Text.Length - 1, 0);
                textNote.ScrollToCaret();
                //Scroll to the end of the text box to see the newest notes.
                if (FormP.getTranType() == transType.VOID || FormP.getTranType() == transType.RETURN)
                {
                    textAmount.Text = "-" + FormP.getAmountCharged();
                }
                else if (FormP.getTranType() == transType.AUTH)
                {
                    textAmount.Text = FormP.getAmountCharged();
                }
                else if (FormP.getTranType() == transType.SALE)
                {
                    textAmount.Text = FormP.getAmountCharged();
                    PaymentCur.Receipt = FormP.getReceiptStr();
                }
                   
            }
             
        }
         
        //There is only a receipt when a sale takes place.
        if (FormP.getResponse() == null || FormP.getResponse().getStatus().getcode() != 0)
        {
            //The transaction failed.
            if (FormP.getTranType() == transType.SALE || FormP.getTranType() == transType.AUTH)
            {
                textAmount.Text = FormP.getAmountCharged();
            }
             
        }
         
    }

    //Preserve the amount so the user can try the payment again more easily.
    private void menuXcharge_Click(Object sender, EventArgs e) throws Exception {
        if (Security.isAuthorized(Permissions.Setup))
        {
            FormXchargeSetup FormX = new FormXchargeSetup();
            if (FormX.ShowDialog() == DialogResult.OK)
            {
                checkUIState();
            }
             
        }
         
    }

    private void menuPayConnect_Click(Object sender, EventArgs e) throws Exception {
        if (Security.isAuthorized(Permissions.Setup))
        {
            FormPayConnectSetup fpcs = new FormPayConnectSetup();
            if (fpcs.ShowDialog() == DialogResult.OK)
            {
                checkUIState();
            }
             
        }
         
    }

    private void gridBal_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        MsgBox.show(this,"This grid is not editable.  Family balances are altered by using splits in the grid to the left.");
    }

    private void comboClinic_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboClinic.SelectedIndex == 0)
        {
            PaymentCur.ClinicNum = 0;
        }
        else
        {
            PaymentCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        } 
        if (SplitList.Count > 0)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Change clinic for all splits?"))
            {
                return ;
            }
             
            for (int i = 0;i < SplitList.Count;i++)
            {
                SplitList[i].ClinicNum = PaymentCur.ClinicNum;
            }
            fillMain();
        }
         
    }

    private void checkPayTypeNone_CheckedChanged(Object sender, EventArgs e) throws Exception {
        //this fires before the click event.  The Checked property also reflects the new value.
        if (checkPayTypeNone.Checked)
        {
            listPayType.Visible = false;
            panelXcharge.Visible = false;
            butPay.Text = Lan.g(this,"Transfer");
        }
        else
        {
            listPayType.Visible = true;
            panelXcharge.Visible = true;
            butPay.Text = Lan.g(this,"Pay");
        } 
    }

    private void checkPayTypeNone_Click(Object sender, EventArgs e) throws Exception {
    }

    //The Checked property reflects the new value.
    //Only possible if IsNew.
    private void butDeleteAll_Click(Object sender, System.EventArgs e) throws Exception {
        if (textDeposit.Visible)
        {
            //this will get checked again by the middle layer
            MsgBox.show(this,"This payment is attached to a deposit.  Not allowed to delete.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"This will delete the entire payment and all splits."))
        {
            return ;
        }
         
        //If payment is attached to a transaction which is more than 48 hours old, then not allowed to delete.
        //This is hard coded.  User would have to delete or detach from within transaction rather than here.
        Transaction trans = Transactions.getAttachedToPayment(PaymentCur.PayNum);
        if (trans != null)
        {
            if (trans.DateTimeEntry < MiscData.getNowDateTime().AddDays(-2))
            {
                MsgBox.show(this,"Not allowed to delete.  This payment is already attached to an accounting transaction.  You will need to detach it from within the accounting section of the program.");
                return ;
            }
             
            if (Transactions.isReconciled(trans))
            {
                MsgBox.show(this,"Not allowed to delete.  This payment is attached to an accounting transaction that has been reconciled.  You will need to detach it from within the accounting section of the program.");
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
         
        try
        {
            Payments.delete(PaymentCur);
        }
        catch (ApplicationException ex)
        {
            //error if attached to deposit slip
            MessageBox.Show(ex.Message);
            return ;
        }

        SecurityLogs.makeLogEntry(Permissions.PaymentEdit,PaymentCur.PatNum,"Delete for: " + Patients.getLim(PaymentCur.PatNum).getNameLF() + ", " + PaymentCur.PayAmt.ToString("c"));
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textAmount.errorProvider1.GetError(textAmount), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        if (checkPayTypeNone.Checked)
        {
            if (PIn.Double(textAmount.Text) != 0)
            {
                MsgBox.show(this,"Amount must be zero for a transfer.");
                return ;
            }
             
        }
        else
        {
            if (StringSupport.equals(textAmount.Text, ""))
            {
                MessageBox.Show(Lan.g(this,"Please enter an amount."));
                return ;
            }
             
            if (PIn.Double(textAmount.Text) == 0)
            {
                MessageBox.Show(Lan.g(this,"Amount must not be zero unless this is a transfer."));
                return ;
            }
             
            if (listPayType.SelectedIndex == -1)
            {
                MsgBox.show(this,"A payment type must be selected.");
            }
             
        } 
        if (IsNew)
        {
            //prevents backdating of initial payment
            if (!Security.IsAuthorized(Permissions.PaymentCreate, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        }
        else
        {
            //Editing an old entry will already be blocked if the date was too old, and user will not be able to click OK button
            //This catches it if user changed the date to be older.
            if (!Security.IsAuthorized(Permissions.PaymentEdit, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        } 
        boolean accountingSynchRequired = false;
        double accountingOldAmt = PaymentCur.PayAmt;
        long accountingNewAcct = -1;
        //the old acctNum will be retrieved inside the validation code.
        if (textDepositAccount.Visible)
        {
            accountingNewAcct = -1;
        }
        else //indicates no change
        if (comboDepositAccount.Visible && comboDepositAccount.Items.Count > 0 && comboDepositAccount.SelectedIndex != -1)
        {
            accountingNewAcct = DepositAccounts[comboDepositAccount.SelectedIndex];
        }
        else
        {
            //neither textbox nor combo visible. Or something's wrong with combobox
            accountingNewAcct = 0;
        }  
        try
        {
            accountingSynchRequired = Payments.ValidateLinkedEntries(accountingOldAmt, PIn.Double(textAmount.Text), IsNew, PaymentCur.PayNum, accountingNewAcct);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        //not able to alter, so must not allow user to continue.
        PaymentCur.PayAmt = PIn.Double(textAmount.Text);
        //handles blank
        PaymentCur.PayDate = PIn.Date(textDate.Text);
        //User chose to have a recurring payment so we need to know if the card has recurring setup and which month to apply the payment to.
        if (IsNew && checkRecurring.Checked && comboCreditCards.SelectedIndex != creditCards.Count)
        {
            //Check if a recurring charge is setup for the selected card.
            if (creditCards[comboCreditCards.SelectedIndex].ChargeAmt == 0 || creditCards[comboCreditCards.SelectedIndex].DateStart.Year < 1880)
            {
                MsgBox.show(this,"The selected credit card has not been setup for recurring charges.");
                return ;
            }
             
            //Check if a stop date was set and if that date falls in on today or in the past.
            if (creditCards[comboCreditCards.SelectedIndex].DateStop.Year > 1880 && creditCards[comboCreditCards.SelectedIndex].DateStop <= DateTime.Now)
            {
                MsgBox.show(this,"This card is no longer accepting recurring charges based on the stop date.");
                return ;
            }
             
            //Have the user decide what month to apply the recurring charge towards.
            FormCreditRecurringDateChoose formDateChoose = new FormCreditRecurringDateChoose(creditCards[comboCreditCards.SelectedIndex]);
            formDateChoose.ShowDialog();
            if (formDateChoose.DialogResult != DialogResult.OK)
            {
                MsgBox.show(this,"Uncheck the \"Apply to Recurring Charge\" box.");
                return ;
            }
             
            //This will change the PayDate to work better with the recurring charge automation.  User was notified in previous window.
            PaymentCur.PayDate = formDateChoose.PayDate;
        }
        else if (IsNew && checkRecurring.Checked && comboCreditCards.SelectedIndex == creditCards.Count)
        {
            MsgBox.show(this,"Cannot apply a recurring charge to a new card.");
            return ;
        }
          
        PaymentCur.CheckNum = textCheckNum.Text;
        PaymentCur.BankBranch = textBankBranch.Text;
        PaymentCur.PayNote = textNote.Text;
        PaymentCur.IsRecurringCC = checkRecurring.Checked;
        if (checkPayTypeNone.Checked)
        {
            PaymentCur.PayType = 0;
        }
        else
        {
            PaymentCur.PayType = DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][listPayType.SelectedIndex].DefNum;
        } 
        //PaymentCur.PatNum=PatCur.PatNum;//this is already done before opening this window.
        //PaymentCur.ClinicNum already handled
        if (SplitList.Count == 0)
        {
            if (Payments.allocationRequired(PaymentCur.PayAmt,PaymentCur.PatNum) && MsgBox.show(this,true,"Apply part of payment to other family members?"))
            {
                SplitList = Payments.allocate(PaymentCur);
            }
            else
            {
                //PayAmt needs to be set first
                //Either no allocation required, or user does not want to allocate.  Just add one split.
                PaySplit split = new PaySplit();
                split.PatNum = PaymentCur.PatNum;
                split.ClinicNum = PaymentCur.ClinicNum;
                split.PayNum = PaymentCur.PayNum;
                split.ProcDate = PaymentCur.PayDate;
                split.DatePay = PaymentCur.PayDate;
                split.ProvNum = Patients.getProvNum(PatCur);
                split.SplitAmt = PaymentCur.PayAmt;
                SplitList.Add(split);
            } 
        }
        else //if one split
        if (SplitList.Count == 1 && PIn.Double(textAmount.Text) != SplitList[0].SplitAmt)
        {
            //and amount doesn't match payment
            SplitList[0].SplitAmt = PIn.Double(textAmount.Text);
        }
        else //make amounts match
        //if one split
        if (SplitList.Count == 1 && PaymentCur.PayDate != SplitList[0].ProcDate && SplitList[0].ProcNum == 0)
        {
            //not attached to procedure
            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Change split date to match payment date?"))
            {
                SplitList[0].ProcDate = PaymentCur.PayDate;
            }
             
        }
        else if (PaymentCur.PayAmt != PIn.Double(textTotal.Text))
        {
            MsgBox.show(this,"Split totals must equal payment amount.");
            return ;
        }
            
        //work on reallocation schemes here later
        if (SplitList.Count > 1)
        {
            PaymentCur.IsSplit = true;
        }
        else
        {
            PaymentCur.IsSplit = false;
        } 
        try
        {
            Payments.update(PaymentCur,true);
        }
        catch (ApplicationException ex)
        {
            //this catches bad dates.
            MessageBox.Show(ex.Message);
            return ;
        }

        for (int i = 0;i < SplitList.Count;i++)
        {
            //Set all DatePays the same.
            SplitList[i].DatePay = PaymentCur.PayDate;
        }
        PaySplits.updateList(SplitListOld,SplitList);
        //Accounting synch is done here.  All validation was done further up
        //If user is trying to change the amount or linked account of an entry that was already copied and linked to accounting section
        if (accountingSynchRequired)
        {
            Payments.AlterLinkedEntries(accountingOldAmt, PIn.Double(textAmount.Text), IsNew, PaymentCur.PayNum, accountingNewAcct, PaymentCur.PayDate, FamCur.getNameInFamFL(PaymentCur.PatNum));
        }
         
        if (IsNew)
        {
            SecurityLogs.makeLogEntry(Permissions.PaymentCreate,PaymentCur.PatNum,Patients.getLim(PaymentCur.PatNum).getNameLF() + ", " + PaymentCur.PayAmt.ToString("c"));
        }
        else
        {
            SecurityLogs.makeLogEntry(Permissions.PaymentEdit,PaymentCur.PatNum,Patients.getLim(PaymentCur.PatNum).getNameLF() + ", " + PaymentCur.PayAmt.ToString("c"));
        } 
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formPayment_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (payConnectWarn)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"The credit card payment that was already processed will have to be voided manually through the web interface.  Continue anyway?"))
            {
                e.Cancel = true;
                return ;
            }
             
        }
         
        if (IsNew)
        {
            Payments.delete(PaymentCur);
            //Void the transaction in X-Charge if there was one.
            String transactionID = "";
            String amount = "";
            boolean isDebit = false;
            String[] noteSplit = Regex.Split(textNote.Text, "\r\n");
            for (Object __dummyForeachVar1 : noteSplit)
            {
                String XCTrans = (String)__dummyForeachVar1;
                if (XCTrans.StartsWith("XCTRANSACTIONID="))
                {
                    transactionID = XCTrans.Substring(16);
                }
                 
                if (XCTrans.StartsWith("APPROVEDAMOUNT="))
                {
                    amount = XCTrans.Substring(15);
                }
                 
                if (XCTrans.StartsWith("TYPE="))
                {
                    if (StringSupport.equals(XCTrans.Substring(5), "Debit Purchase"))
                    {
                        isDebit = true;
                    }
                     
                }
                 
            }
            if (!StringSupport.equals(transactionID, ""))
            {
                if (hasXCharge())
                {
                    voidXChargeTransaction(transactionID,amount,isDebit);
                }
                 
            }
             
        }
         
    }

}


//else if(PaymentCur.PayAmt!=tot){
//	MessageBox.Show(Lan.g(this,"Splits have been altered.  Payment must match splits."));
//	e.Cancel=true;
//	return;
//}