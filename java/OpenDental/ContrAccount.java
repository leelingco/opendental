//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:13 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ClaimL;
import OpenDental.ContrAccount;
import OpenDental.DateTimeOD;
import OpenDental.FormAdjust;
import OpenDental.FormApptEdit;
import OpenDental.FormClaimCreate;
import OpenDental.FormClaimEdit;
import OpenDental.FormCommItem;
import OpenDental.FormCreditCardManage;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormFormPatEdit;
import OpenDental.FormInstallmentPlanEdit;
import OpenDental.FormLabCaseEdit;
import OpenDental.FormPayment;
import OpenDental.FormPayPlan;
import OpenDental.FormProcEdit;
import OpenDental.FormRepeatChargeEdit;
import OpenDental.FormRpStatement;
import OpenDental.FormRxEdit;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormStatementOptions;
import OpenDental.FormTaskEdit;
import OpenDental.FormTrojanCollect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PatientSelectedEventArgs;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.ProgramL;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AccountModules;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Adjustments;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EnumClaimMedType;
import OpenDentBusiness.Family;
import OpenDentBusiness.FormPat;
import OpenDentBusiness.FormPats;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.InstallmentPlan;
import OpenDentBusiness.InstallmentPlans;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.PatientNotes;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlans;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.RepeatCharge;
import OpenDentBusiness.RepeatCharges;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.Statement;
import OpenDentBusiness.StatementMode;
import OpenDentBusiness.Statements;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.YN;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrAccount  extends System.Windows.Forms.UserControl 
{
    private System.Windows.Forms.Label labelFamFinancial = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = null;
    // Required designer variable.
    private System.Windows.Forms.Label labelUrgFinNote = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textUrgFinNote;
    private System.Windows.Forms.ContextMenu contextMenuIns = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuInsOther = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuInsPri = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuInsSec = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.Panel panelSplitter = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelCommButs = new System.Windows.Forms.Panel();
    private OpenDental.ODtextBox textFinNotes;
    private System.Windows.Forms.ContextMenu contextMenuStatement = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemStatementWalkout = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.MenuItem menuItemStatementMore = new System.Windows.Forms.MenuItem();
    private OpenDental.UI.ODGrid gridComm;
    private OpenDental.UI.ODGrid gridAcctPat;
    private OpenDental.UI.ODGrid gridAccount;
    private OpenDental.UI.ODGrid gridRepeat;
    private System.Windows.Forms.MenuItem menuInsMedical = new System.Windows.Forms.MenuItem();
    private ContextMenu contextMenuRepeat = new ContextMenu();
    private MenuItem menuItemRepeatStand = new MenuItem();
    private MenuItem menuItemRepeatEmail = new MenuItem();
    private Panel panelProgNotes = new Panel();
    private OpenDental.UI.ODGrid gridProg;
    private GroupBox groupBox6 = new GroupBox();
    private CheckBox checkShowE = new CheckBox();
    private CheckBox checkShowR = new CheckBox();
    private CheckBox checkShowC = new CheckBox();
    private CheckBox checkShowTP = new CheckBox();
    private GroupBox groupBox7 = new GroupBox();
    private CheckBox checkAppt = new CheckBox();
    private CheckBox checkLabCase = new CheckBox();
    private CheckBox checkRx = new CheckBox();
    private CheckBox checkComm = new CheckBox();
    private CheckBox checkNotes = new CheckBox();
    private OpenDental.UI.Button butShowAll;
    private OpenDental.UI.Button butShowNone;
    private CheckBox checkExtraNotes = new CheckBox();
    private CheckBox checkShowTeeth = new CheckBox();
    private CheckBox checkAudit = new CheckBox();
    private Panel panelAging = new Panel();
    private Label labelBalance = new Label();
    private Label labelInsEst = new Label();
    private Label labelTotal = new Label();
    private Label label7 = new Label();
    private TextBox text0_30 = new TextBox();
    private Label label6 = new Label();
    private TextBox text31_60 = new TextBox();
    private Label label5 = new Label();
    private TextBox text61_90 = new TextBox();
    private Label label3 = new Label();
    private TextBox textOver90 = new TextBox();
    private Label label2 = new Label();
    private OpenDental.UI.Button butTrojan;
    private MenuItem menuItemStatementEmail = new MenuItem();
    private Label labelBalanceAmt = new Label();
    private TabControl tabControlShow = new TabControl();
    private TabPage tabMain = new TabPage();
    private TabPage tabShow = new TabPage();
    private OpenDental.UI.ODGrid gridPayPlan;
    private OpenDental.ValidDate textDateEnd;
    private OpenDental.ValidDate textDateStart;
    private Label labelEndDate = new Label();
    private Label labelStartDate = new Label();
    private OpenDental.UI.Button butRefresh;
    private OpenDental.UI.Button but90days;
    private OpenDental.UI.Button but45days;
    private OpenDental.UI.Button butDatesAll;
    private CheckBox checkShowDetail = new CheckBox();
    private OpenDental.UI.Button butToday;
    private CheckBox checkShowFamilyComm = new CheckBox();
    private FormPayPlan FormPayPlan2;
    private Label labelTotalAmt = new Label();
    private Label labelInsEstAmt = new Label();
    private Panel panelAgeLine = new Panel();
    private Panel panel2 = new Panel();
    private Panel panel1 = new Panel();
    private ToolTip toolTip1 = new ToolTip();
    private Label labelPatEstBal = new Label();
    private Label labelPatEstBalAmt = new Label();
    private Panel panelTotalOwes = new Panel();
    private Label label21 = new Label();
    private Label labelTotalPtOwes = new Label();
    private Label labelUnearnedAmt = new Label();
    private Label labelUnearned = new Label();
    private Label labelInsRem = new Label();
    private double PPBalanceTotal = new double();
    /**
    * This holds nearly all of the data needed for display.  It is retrieved in one call to the database.
    */
    private DataSet DataSetMain = new DataSet();
    private Family FamCur;
    /**
    * 
    */
    private Patient PatCur;
    private PatientNote PatientNoteCur;
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    private RepeatCharge[] RepeatChargeList = new RepeatCharge[]();
    private int OriginalMousePos = new int();
    private boolean MouseIsDownOnSplitter = new boolean();
    private int SplitterOriginalY = new int();
    private boolean FinNoteChanged = new boolean();
    private boolean CCChanged = new boolean();
    private boolean UrgFinNoteChanged = new boolean();
    private int Actscrollval = new int();
    /**
    * Set to true if this control is placed in the recall edit window. This affects the control behavior.
    */
    public boolean ViewingInRecall = false;
    private List<DisplayField> fieldsForMainGrid = new List<DisplayField>();
    private OpenDental.UI.Button butComm;
    private GroupBox groupBoxIndIns = new GroupBox();
    private TextBox textPriDed = new TextBox();
    private TextBox textPriUsed = new TextBox();
    private TextBox textPriDedRem = new TextBox();
    private TextBox textPriPend = new TextBox();
    private TextBox textPriRem = new TextBox();
    private TextBox textPriMax = new TextBox();
    private TextBox textSecRem = new TextBox();
    private Label label10 = new Label();
    private TextBox textSecPend = new TextBox();
    private Label label11 = new Label();
    private Label label18 = new Label();
    private Label label12 = new Label();
    private Label label13 = new Label();
    private TextBox textSecDedRem = new TextBox();
    private Label label14 = new Label();
    private Label label15 = new Label();
    private TextBox textSecMax = new TextBox();
    private Label label16 = new Label();
    private TextBox textSecDed = new TextBox();
    private TextBox textSecUsed = new TextBox();
    private GroupBox groupBoxFamilyIns = new GroupBox();
    private TextBox textFamPriMax = new TextBox();
    private TextBox textFamPriDed = new TextBox();
    private Label label4 = new Label();
    private Label label8 = new Label();
    private TextBox textFamSecMax = new TextBox();
    private Label label9 = new Label();
    private TextBox textFamSecDed = new TextBox();
    private Label label17 = new Label();
    private OpenDental.UI.Button butCreditCard;
    private MenuItem menuItemRepeatMobile = new MenuItem();
    private MenuItem menuItemReceipt = new MenuItem();
    private MenuItem menuItemRepeatCanada = new MenuItem();
    private MenuItem menuItemInvoice = new MenuItem();
    private boolean InitializedOnStartup = new boolean();
    /**
    * 
    */
    public ContrAccount() throws Exception {
        Logger.openlog.log("Initializing account module...",Severity.INFO);
        initializeComponent();
    }

    // This call is required by the Windows.Forms Form Designer.
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ContrAccount.class);
        this.labelFamFinancial = new System.Windows.Forms.Label();
        this.labelUrgFinNote = new System.Windows.Forms.Label();
        this.contextMenuIns = new System.Windows.Forms.ContextMenu();
        this.menuInsPri = new System.Windows.Forms.MenuItem();
        this.menuInsSec = new System.Windows.Forms.MenuItem();
        this.menuInsMedical = new System.Windows.Forms.MenuItem();
        this.menuInsOther = new System.Windows.Forms.MenuItem();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.panelSplitter = new System.Windows.Forms.Panel();
        this.panelCommButs = new System.Windows.Forms.Panel();
        this.contextMenuStatement = new System.Windows.Forms.ContextMenu();
        this.menuItemStatementWalkout = new System.Windows.Forms.MenuItem();
        this.menuItemStatementEmail = new System.Windows.Forms.MenuItem();
        this.menuItemReceipt = new System.Windows.Forms.MenuItem();
        this.menuItemInvoice = new System.Windows.Forms.MenuItem();
        this.menuItemStatementMore = new System.Windows.Forms.MenuItem();
        this.contextMenuRepeat = new System.Windows.Forms.ContextMenu();
        this.menuItemRepeatStand = new System.Windows.Forms.MenuItem();
        this.menuItemRepeatEmail = new System.Windows.Forms.MenuItem();
        this.menuItemRepeatMobile = new System.Windows.Forms.MenuItem();
        this.menuItemRepeatCanada = new System.Windows.Forms.MenuItem();
        this.panelProgNotes = new System.Windows.Forms.Panel();
        this.checkNotes = new System.Windows.Forms.CheckBox();
        this.groupBox7 = new System.Windows.Forms.GroupBox();
        this.checkShowTeeth = new System.Windows.Forms.CheckBox();
        this.checkAudit = new System.Windows.Forms.CheckBox();
        this.checkExtraNotes = new System.Windows.Forms.CheckBox();
        this.checkAppt = new System.Windows.Forms.CheckBox();
        this.checkLabCase = new System.Windows.Forms.CheckBox();
        this.checkRx = new System.Windows.Forms.CheckBox();
        this.checkComm = new System.Windows.Forms.CheckBox();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.checkShowE = new System.Windows.Forms.CheckBox();
        this.checkShowR = new System.Windows.Forms.CheckBox();
        this.checkShowC = new System.Windows.Forms.CheckBox();
        this.checkShowTP = new System.Windows.Forms.CheckBox();
        this.gridProg = new OpenDental.UI.ODGrid();
        this.panelAging = new System.Windows.Forms.Panel();
        this.labelInsRem = new System.Windows.Forms.Label();
        this.labelUnearnedAmt = new System.Windows.Forms.Label();
        this.labelUnearned = new System.Windows.Forms.Label();
        this.labelBalanceAmt = new System.Windows.Forms.Label();
        this.labelTotalAmt = new System.Windows.Forms.Label();
        this.panelTotalOwes = new System.Windows.Forms.Panel();
        this.label21 = new System.Windows.Forms.Label();
        this.labelTotalPtOwes = new System.Windows.Forms.Label();
        this.labelPatEstBalAmt = new System.Windows.Forms.Label();
        this.labelPatEstBal = new System.Windows.Forms.Label();
        this.panel2 = new System.Windows.Forms.Panel();
        this.panel1 = new System.Windows.Forms.Panel();
        this.panelAgeLine = new System.Windows.Forms.Panel();
        this.labelInsEstAmt = new System.Windows.Forms.Label();
        this.labelBalance = new System.Windows.Forms.Label();
        this.labelInsEst = new System.Windows.Forms.Label();
        this.labelTotal = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.text0_30 = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.text31_60 = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.text61_90 = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.textOver90 = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.tabControlShow = new System.Windows.Forms.TabControl();
        this.tabMain = new System.Windows.Forms.TabPage();
        this.gridAcctPat = new OpenDental.UI.ODGrid();
        this.tabShow = new System.Windows.Forms.TabPage();
        this.checkShowFamilyComm = new System.Windows.Forms.CheckBox();
        this.checkShowDetail = new System.Windows.Forms.CheckBox();
        this.labelEndDate = new System.Windows.Forms.Label();
        this.labelStartDate = new System.Windows.Forms.Label();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.groupBoxIndIns = new System.Windows.Forms.GroupBox();
        this.textPriDed = new System.Windows.Forms.TextBox();
        this.textPriUsed = new System.Windows.Forms.TextBox();
        this.textPriDedRem = new System.Windows.Forms.TextBox();
        this.textPriPend = new System.Windows.Forms.TextBox();
        this.textPriRem = new System.Windows.Forms.TextBox();
        this.textPriMax = new System.Windows.Forms.TextBox();
        this.textSecRem = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textSecPend = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label18 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.textSecDedRem = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.textSecMax = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textSecDed = new System.Windows.Forms.TextBox();
        this.textSecUsed = new System.Windows.Forms.TextBox();
        this.groupBoxFamilyIns = new System.Windows.Forms.GroupBox();
        this.textFamPriMax = new System.Windows.Forms.TextBox();
        this.textFamPriDed = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.textFamSecMax = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textFamSecDed = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.gridPayPlan = new OpenDental.UI.ODGrid();
        this.gridRepeat = new OpenDental.UI.ODGrid();
        this.gridAccount = new OpenDental.UI.ODGrid();
        this.gridComm = new OpenDental.UI.ODGrid();
        this.butCreditCard = new OpenDental.UI.Button();
        this.textUrgFinNote = new OpenDental.ODtextBox();
        this.textFinNotes = new OpenDental.ODtextBox();
        this.butToday = new OpenDental.UI.Button();
        this.butDatesAll = new OpenDental.UI.Button();
        this.but90days = new OpenDental.UI.Button();
        this.but45days = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.textDateEnd = new OpenDental.ValidDate();
        this.textDateStart = new OpenDental.ValidDate();
        this.butShowNone = new OpenDental.UI.Button();
        this.butShowAll = new OpenDental.UI.Button();
        this.butComm = new OpenDental.UI.Button();
        this.butTrojan = new OpenDental.UI.Button();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.panelCommButs.SuspendLayout();
        this.panelProgNotes.SuspendLayout();
        this.groupBox7.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.panelAging.SuspendLayout();
        this.panelTotalOwes.SuspendLayout();
        this.tabControlShow.SuspendLayout();
        this.tabMain.SuspendLayout();
        this.tabShow.SuspendLayout();
        this.groupBoxIndIns.SuspendLayout();
        this.groupBoxFamilyIns.SuspendLayout();
        this.SuspendLayout();
        //
        // labelFamFinancial
        //
        this.labelFamFinancial.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelFamFinancial.Location = new System.Drawing.Point(0, 318);
        this.labelFamFinancial.Name = "labelFamFinancial";
        this.labelFamFinancial.Size = new System.Drawing.Size(154, 16);
        this.labelFamFinancial.TabIndex = 9;
        this.labelFamFinancial.Text = "Family Financial Notes";
        this.labelFamFinancial.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // labelUrgFinNote
        //
        this.labelUrgFinNote.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelUrgFinNote.Location = new System.Drawing.Point(0, 0);
        this.labelUrgFinNote.Name = "labelUrgFinNote";
        this.labelUrgFinNote.Size = new System.Drawing.Size(165, 17);
        this.labelUrgFinNote.TabIndex = 10;
        this.labelUrgFinNote.Text = "Fam Urgent Fin Note";
        this.labelUrgFinNote.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // contextMenuIns
        //
        this.contextMenuIns.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuInsPri, this.menuInsSec, this.menuInsMedical, this.menuInsOther });
        //
        // menuInsPri
        //
        this.menuInsPri.Index = 0;
        this.menuInsPri.Text = "Primary";
        this.menuInsPri.Click += new System.EventHandler(this.menuInsPri_Click);
        //
        // menuInsSec
        //
        this.menuInsSec.Index = 1;
        this.menuInsSec.Text = "Secondary";
        this.menuInsSec.Click += new System.EventHandler(this.menuInsSec_Click);
        //
        // menuInsMedical
        //
        this.menuInsMedical.Index = 2;
        this.menuInsMedical.Text = "Medical";
        this.menuInsMedical.Click += new System.EventHandler(this.menuInsMedical_Click);
        //
        // menuInsOther
        //
        this.menuInsOther.Index = 3;
        this.menuInsOther.Text = "Other";
        this.menuInsOther.Click += new System.EventHandler(this.menuInsOther_Click);
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Pat.gif");
        this.imageListMain.Images.SetKeyName(1, "");
        this.imageListMain.Images.SetKeyName(2, "");
        this.imageListMain.Images.SetKeyName(3, "Umbrella.gif");
        this.imageListMain.Images.SetKeyName(4, "");
        //
        // panelSplitter
        //
        this.panelSplitter.Cursor = System.Windows.Forms.Cursors.SizeNS;
        this.panelSplitter.Location = new System.Drawing.Point(0, 425);
        this.panelSplitter.Name = "panelSplitter";
        this.panelSplitter.Size = new System.Drawing.Size(769, 5);
        this.panelSplitter.TabIndex = 49;
        this.panelSplitter.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseDown);
        this.panelSplitter.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseMove);
        this.panelSplitter.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panelSplitter_MouseUp);
        //
        // panelCommButs
        //
        this.panelCommButs.Controls.Add(this.butComm);
        this.panelCommButs.Controls.Add(this.butTrojan);
        this.panelCommButs.Location = new System.Drawing.Point(749, 526);
        this.panelCommButs.Name = "panelCommButs";
        this.panelCommButs.Size = new System.Drawing.Size(163, 63);
        this.panelCommButs.TabIndex = 69;
        //
        // contextMenuStatement
        //
        this.contextMenuStatement.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemStatementWalkout, this.menuItemStatementEmail, this.menuItemReceipt, this.menuItemInvoice, this.menuItemStatementMore });
        //
        // menuItemStatementWalkout
        //
        this.menuItemStatementWalkout.Index = 0;
        this.menuItemStatementWalkout.Text = "Walkout";
        this.menuItemStatementWalkout.Click += new System.EventHandler(this.menuItemStatementWalkout_Click);
        //
        // menuItemStatementEmail
        //
        this.menuItemStatementEmail.Index = 1;
        this.menuItemStatementEmail.Text = "Email";
        this.menuItemStatementEmail.Click += new System.EventHandler(this.menuItemStatementEmail_Click);
        //
        // menuItemReceipt
        //
        this.menuItemReceipt.Index = 2;
        this.menuItemReceipt.Text = "Receipt";
        this.menuItemReceipt.Click += new System.EventHandler(this.menuItemReceipt_Click);
        //
        // menuItemInvoice
        //
        this.menuItemInvoice.Index = 3;
        this.menuItemInvoice.Text = "Invoice";
        this.menuItemInvoice.Click += new System.EventHandler(this.menuItemInvoice_Click);
        //
        // menuItemStatementMore
        //
        this.menuItemStatementMore.Index = 4;
        this.menuItemStatementMore.Text = "More Options";
        this.menuItemStatementMore.Click += new System.EventHandler(this.menuItemStatementMore_Click);
        //
        // contextMenuRepeat
        //
        this.contextMenuRepeat.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemRepeatStand, this.menuItemRepeatEmail, this.menuItemRepeatMobile, this.menuItemRepeatCanada });
        //
        // menuItemRepeatStand
        //
        this.menuItemRepeatStand.Index = 0;
        this.menuItemRepeatStand.Text = "Standard Monthly";
        this.menuItemRepeatStand.Click += new System.EventHandler(this.MenuItemRepeatStand_Click);
        //
        // menuItemRepeatEmail
        //
        this.menuItemRepeatEmail.Index = 1;
        this.menuItemRepeatEmail.Text = "Email Monthly";
        this.menuItemRepeatEmail.Click += new System.EventHandler(this.MenuItemRepeatEmail_Click);
        //
        // menuItemRepeatMobile
        //
        this.menuItemRepeatMobile.Index = 2;
        this.menuItemRepeatMobile.Text = "Mobile Monthly";
        this.menuItemRepeatMobile.Click += new System.EventHandler(this.menuItemRepeatMobile_Click);
        //
        // menuItemRepeatCanada
        //
        this.menuItemRepeatCanada.Index = 3;
        this.menuItemRepeatCanada.Text = "Canada Monthly";
        this.menuItemRepeatCanada.Click += new System.EventHandler(this.menuItemRepeatCanada_Click);
        //
        // panelProgNotes
        //
        this.panelProgNotes.Controls.Add(this.butShowNone);
        this.panelProgNotes.Controls.Add(this.butShowAll);
        this.panelProgNotes.Controls.Add(this.checkNotes);
        this.panelProgNotes.Controls.Add(this.groupBox7);
        this.panelProgNotes.Controls.Add(this.groupBox6);
        this.panelProgNotes.Controls.Add(this.gridProg);
        this.panelProgNotes.Location = new System.Drawing.Point(0, 461);
        this.panelProgNotes.Name = "panelProgNotes";
        this.panelProgNotes.Size = new System.Drawing.Size(749, 227);
        this.panelProgNotes.TabIndex = 211;
        //
        // checkNotes
        //
        this.checkNotes.AllowDrop = true;
        this.checkNotes.Checked = true;
        this.checkNotes.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkNotes.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkNotes.Location = new System.Drawing.Point(624, 191);
        this.checkNotes.Name = "checkNotes";
        this.checkNotes.Size = new System.Drawing.Size(102, 13);
        this.checkNotes.TabIndex = 214;
        this.checkNotes.Text = "Proc Notes";
        this.checkNotes.Visible = false;
        this.checkNotes.Click += new System.EventHandler(this.checkNotes_Click);
        //
        // groupBox7
        //
        this.groupBox7.Controls.Add(this.checkShowTeeth);
        this.groupBox7.Controls.Add(this.checkAudit);
        this.groupBox7.Controls.Add(this.checkExtraNotes);
        this.groupBox7.Controls.Add(this.checkAppt);
        this.groupBox7.Controls.Add(this.checkLabCase);
        this.groupBox7.Controls.Add(this.checkRx);
        this.groupBox7.Controls.Add(this.checkComm);
        this.groupBox7.Location = new System.Drawing.Point(614, 88);
        this.groupBox7.Name = "groupBox7";
        this.groupBox7.Size = new System.Drawing.Size(121, 101);
        this.groupBox7.TabIndex = 213;
        this.groupBox7.TabStop = false;
        this.groupBox7.Text = "Object Types";
        this.groupBox7.Visible = false;
        //
        // checkShowTeeth
        //
        this.checkShowTeeth.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowTeeth.Location = new System.Drawing.Point(44, 63);
        this.checkShowTeeth.Name = "checkShowTeeth";
        this.checkShowTeeth.Size = new System.Drawing.Size(104, 13);
        this.checkShowTeeth.TabIndex = 217;
        this.checkShowTeeth.Text = "Selected Teeth";
        this.checkShowTeeth.Visible = false;
        //
        // checkAudit
        //
        this.checkAudit.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkAudit.Location = new System.Drawing.Point(44, 79);
        this.checkAudit.Name = "checkAudit";
        this.checkAudit.Size = new System.Drawing.Size(73, 13);
        this.checkAudit.TabIndex = 218;
        this.checkAudit.Text = "Audit";
        this.checkAudit.Visible = false;
        //
        // checkExtraNotes
        //
        this.checkExtraNotes.AllowDrop = true;
        this.checkExtraNotes.Checked = true;
        this.checkExtraNotes.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkExtraNotes.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkExtraNotes.Location = new System.Drawing.Point(9, 82);
        this.checkExtraNotes.Name = "checkExtraNotes";
        this.checkExtraNotes.Size = new System.Drawing.Size(102, 13);
        this.checkExtraNotes.TabIndex = 215;
        this.checkExtraNotes.Text = "Extra Notes";
        this.checkExtraNotes.Visible = false;
        this.checkExtraNotes.Click += new System.EventHandler(this.checkExtraNotes_Click);
        //
        // checkAppt
        //
        this.checkAppt.Checked = true;
        this.checkAppt.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkAppt.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkAppt.Location = new System.Drawing.Point(10, 17);
        this.checkAppt.Name = "checkAppt";
        this.checkAppt.Size = new System.Drawing.Size(102, 13);
        this.checkAppt.TabIndex = 20;
        this.checkAppt.Text = "Appointments";
        this.checkAppt.Click += new System.EventHandler(this.checkAppt_Click);
        //
        // checkLabCase
        //
        this.checkLabCase.Checked = true;
        this.checkLabCase.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkLabCase.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkLabCase.Location = new System.Drawing.Point(10, 49);
        this.checkLabCase.Name = "checkLabCase";
        this.checkLabCase.Size = new System.Drawing.Size(102, 13);
        this.checkLabCase.TabIndex = 17;
        this.checkLabCase.Text = "Lab Cases";
        this.checkLabCase.Click += new System.EventHandler(this.checkLabCase_Click);
        //
        // checkRx
        //
        this.checkRx.Checked = true;
        this.checkRx.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkRx.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkRx.Location = new System.Drawing.Point(10, 65);
        this.checkRx.Name = "checkRx";
        this.checkRx.Size = new System.Drawing.Size(102, 13);
        this.checkRx.TabIndex = 8;
        this.checkRx.Text = "Rx";
        this.checkRx.Click += new System.EventHandler(this.checkRx_Click);
        //
        // checkComm
        //
        this.checkComm.Checked = true;
        this.checkComm.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkComm.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkComm.Location = new System.Drawing.Point(10, 33);
        this.checkComm.Name = "checkComm";
        this.checkComm.Size = new System.Drawing.Size(102, 13);
        this.checkComm.TabIndex = 16;
        this.checkComm.Text = "Comm Log";
        this.checkComm.Click += new System.EventHandler(this.checkComm_Click);
        //
        // groupBox6
        //
        this.groupBox6.Controls.Add(this.checkShowE);
        this.groupBox6.Controls.Add(this.checkShowR);
        this.groupBox6.Controls.Add(this.checkShowC);
        this.groupBox6.Controls.Add(this.checkShowTP);
        this.groupBox6.Location = new System.Drawing.Point(614, 1);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(121, 85);
        this.groupBox6.TabIndex = 212;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Procedures";
        this.groupBox6.Visible = false;
        //
        // checkShowE
        //
        this.checkShowE.Checked = true;
        this.checkShowE.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowE.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowE.Location = new System.Drawing.Point(9, 49);
        this.checkShowE.Name = "checkShowE";
        this.checkShowE.Size = new System.Drawing.Size(101, 13);
        this.checkShowE.TabIndex = 10;
        this.checkShowE.Text = "Existing";
        this.checkShowE.Click += new System.EventHandler(this.checkShowE_Click);
        //
        // checkShowR
        //
        this.checkShowR.Checked = true;
        this.checkShowR.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowR.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowR.Location = new System.Drawing.Point(9, 65);
        this.checkShowR.Name = "checkShowR";
        this.checkShowR.Size = new System.Drawing.Size(101, 13);
        this.checkShowR.TabIndex = 14;
        this.checkShowR.Text = "Referred";
        this.checkShowR.Click += new System.EventHandler(this.checkShowR_Click);
        //
        // checkShowC
        //
        this.checkShowC.Checked = true;
        this.checkShowC.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowC.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowC.Location = new System.Drawing.Point(9, 33);
        this.checkShowC.Name = "checkShowC";
        this.checkShowC.Size = new System.Drawing.Size(101, 13);
        this.checkShowC.TabIndex = 9;
        this.checkShowC.Text = "Completed";
        this.checkShowC.Click += new System.EventHandler(this.checkShowC_Click);
        //
        // checkShowTP
        //
        this.checkShowTP.Checked = true;
        this.checkShowTP.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowTP.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowTP.Location = new System.Drawing.Point(9, 17);
        this.checkShowTP.Name = "checkShowTP";
        this.checkShowTP.Size = new System.Drawing.Size(101, 13);
        this.checkShowTP.TabIndex = 8;
        this.checkShowTP.Text = "Treat Plan";
        this.checkShowTP.Click += new System.EventHandler(this.checkShowTP_Click);
        //
        // gridProg
        //
        this.gridProg.setHScrollVisible(true);
        this.gridProg.Location = new System.Drawing.Point(3, 0);
        this.gridProg.Name = "gridProg";
        this.gridProg.setScrollValue(0);
        this.gridProg.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProg.Size = new System.Drawing.Size(603, 230);
        this.gridProg.TabIndex = 211;
        this.gridProg.setTitle("Progress Notes");
        this.gridProg.setTranslationName("TableProg");
        this.gridProg.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridProg.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridProg_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // panelAging
        //
        this.panelAging.Controls.Add(this.labelInsRem);
        this.panelAging.Controls.Add(this.labelUnearnedAmt);
        this.panelAging.Controls.Add(this.labelUnearned);
        this.panelAging.Controls.Add(this.labelBalanceAmt);
        this.panelAging.Controls.Add(this.labelTotalAmt);
        this.panelAging.Controls.Add(this.panelTotalOwes);
        this.panelAging.Controls.Add(this.labelPatEstBalAmt);
        this.panelAging.Controls.Add(this.labelPatEstBal);
        this.panelAging.Controls.Add(this.panel2);
        this.panelAging.Controls.Add(this.panel1);
        this.panelAging.Controls.Add(this.panelAgeLine);
        this.panelAging.Controls.Add(this.labelInsEstAmt);
        this.panelAging.Controls.Add(this.labelBalance);
        this.panelAging.Controls.Add(this.labelInsEst);
        this.panelAging.Controls.Add(this.labelTotal);
        this.panelAging.Controls.Add(this.label7);
        this.panelAging.Controls.Add(this.text0_30);
        this.panelAging.Controls.Add(this.label6);
        this.panelAging.Controls.Add(this.text31_60);
        this.panelAging.Controls.Add(this.label5);
        this.panelAging.Controls.Add(this.text61_90);
        this.panelAging.Controls.Add(this.label3);
        this.panelAging.Controls.Add(this.textOver90);
        this.panelAging.Controls.Add(this.label2);
        this.panelAging.Location = new System.Drawing.Point(0, 25);
        this.panelAging.Name = "panelAging";
        this.panelAging.Size = new System.Drawing.Size(749, 37);
        this.panelAging.TabIndex = 213;
        //
        // labelInsRem
        //
        this.labelInsRem.BackColor = System.Drawing.Color.LightGray;
        this.labelInsRem.Location = new System.Drawing.Point(700, 4);
        this.labelInsRem.Name = "labelInsRem";
        this.labelInsRem.Size = new System.Drawing.Size(45, 29);
        this.labelInsRem.TabIndex = 0;
        this.labelInsRem.Text = "Ins\r\nRem";
        this.labelInsRem.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        this.labelInsRem.MouseEnter += new System.EventHandler(this.labelInsRem_MouseEnter);
        this.labelInsRem.MouseLeave += new System.EventHandler(this.labelInsRem_MouseLeave);
        //
        // labelUnearnedAmt
        //
        this.labelUnearnedAmt.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelUnearnedAmt.ForeColor = System.Drawing.Color.Firebrick;
        this.labelUnearnedAmt.Location = new System.Drawing.Point(636, 18);
        this.labelUnearnedAmt.Name = "labelUnearnedAmt";
        this.labelUnearnedAmt.Size = new System.Drawing.Size(60, 13);
        this.labelUnearnedAmt.TabIndex = 228;
        this.labelUnearnedAmt.Text = "25000.00";
        this.labelUnearnedAmt.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelUnearned
        //
        this.labelUnearned.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelUnearned.ForeColor = System.Drawing.SystemColors.ControlText;
        this.labelUnearned.Location = new System.Drawing.Point(632, 2);
        this.labelUnearned.Name = "labelUnearned";
        this.labelUnearned.Size = new System.Drawing.Size(68, 13);
        this.labelUnearned.TabIndex = 227;
        this.labelUnearned.Text = "Unearned";
        this.labelUnearned.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelBalanceAmt
        //
        this.labelBalanceAmt.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelBalanceAmt.ForeColor = System.Drawing.Color.Firebrick;
        this.labelBalanceAmt.Location = new System.Drawing.Point(456, 16);
        this.labelBalanceAmt.Name = "labelBalanceAmt";
        this.labelBalanceAmt.Size = new System.Drawing.Size(80, 19);
        this.labelBalanceAmt.TabIndex = 60;
        this.labelBalanceAmt.Text = "25000.00";
        this.labelBalanceAmt.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // labelTotalAmt
        //
        this.labelTotalAmt.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotalAmt.ForeColor = System.Drawing.Color.Firebrick;
        this.labelTotalAmt.Location = new System.Drawing.Point(294, 16);
        this.labelTotalAmt.Name = "labelTotalAmt";
        this.labelTotalAmt.Size = new System.Drawing.Size(80, 19);
        this.labelTotalAmt.TabIndex = 61;
        this.labelTotalAmt.Text = "25000.00";
        this.labelTotalAmt.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // panelTotalOwes
        //
        this.panelTotalOwes.Controls.Add(this.label21);
        this.panelTotalOwes.Controls.Add(this.labelTotalPtOwes);
        this.panelTotalOwes.Location = new System.Drawing.Point(560, -38);
        this.panelTotalOwes.Name = "panelTotalOwes";
        this.panelTotalOwes.Size = new System.Drawing.Size(126, 37);
        this.panelTotalOwes.TabIndex = 226;
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(3, 0);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(123, 12);
        this.label21.TabIndex = 223;
        this.label21.Text = "TOTAL  Owed w/ Plan:";
        this.label21.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        this.toolTip1.SetToolTip(this.label21, "Total balance owed on all payment plans ");
        //
        // labelTotalPtOwes
        //
        this.labelTotalPtOwes.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelTotalPtOwes.ForeColor = System.Drawing.Color.Firebrick;
        this.labelTotalPtOwes.Location = new System.Drawing.Point(6, 12);
        this.labelTotalPtOwes.Name = "labelTotalPtOwes";
        this.labelTotalPtOwes.Size = new System.Drawing.Size(112, 23);
        this.labelTotalPtOwes.TabIndex = 222;
        this.labelTotalPtOwes.Text = "2500.00";
        this.labelTotalPtOwes.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelPatEstBalAmt
        //
        this.labelPatEstBalAmt.Location = new System.Drawing.Point(550, 19);
        this.labelPatEstBalAmt.Name = "labelPatEstBalAmt";
        this.labelPatEstBalAmt.Size = new System.Drawing.Size(65, 13);
        this.labelPatEstBalAmt.TabIndex = 89;
        this.labelPatEstBalAmt.Text = "25000.00";
        this.labelPatEstBalAmt.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelPatEstBal
        //
        this.labelPatEstBal.Location = new System.Drawing.Point(550, 3);
        this.labelPatEstBal.Name = "labelPatEstBal";
        this.labelPatEstBal.Size = new System.Drawing.Size(65, 13);
        this.labelPatEstBal.TabIndex = 88;
        this.labelPatEstBal.Text = "Pat Est Bal";
        this.labelPatEstBal.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // panel2
        //
        this.panel2.BackColor = System.Drawing.SystemColors.ControlDark;
        this.panel2.Location = new System.Drawing.Point(624, 3);
        this.panel2.Name = "panel2";
        this.panel2.Size = new System.Drawing.Size(2, 32);
        this.panel2.TabIndex = 87;
        //
        // panel1
        //
        this.panel1.BackColor = System.Drawing.SystemColors.ControlDark;
        this.panel1.Location = new System.Drawing.Point(541, 3);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(2, 32);
        this.panel1.TabIndex = 86;
        //
        // panelAgeLine
        //
        this.panelAgeLine.BackColor = System.Drawing.SystemColors.ControlDark;
        this.panelAgeLine.Location = new System.Drawing.Point(379, 2);
        this.panelAgeLine.Name = "panelAgeLine";
        this.panelAgeLine.Size = new System.Drawing.Size(2, 32);
        this.panelAgeLine.TabIndex = 63;
        //
        // labelInsEstAmt
        //
        this.labelInsEstAmt.Location = new System.Drawing.Point(387, 18);
        this.labelInsEstAmt.Name = "labelInsEstAmt";
        this.labelInsEstAmt.Size = new System.Drawing.Size(65, 13);
        this.labelInsEstAmt.TabIndex = 62;
        this.labelInsEstAmt.Text = "2500.00";
        this.labelInsEstAmt.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelBalance
        //
        this.labelBalance.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelBalance.Location = new System.Drawing.Point(454, 2);
        this.labelBalance.Name = "labelBalance";
        this.labelBalance.Size = new System.Drawing.Size(80, 13);
        this.labelBalance.TabIndex = 59;
        this.labelBalance.Text = "= Balance";
        this.labelBalance.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelInsEst
        //
        this.labelInsEst.Location = new System.Drawing.Point(387, 2);
        this.labelInsEst.Name = "labelInsEst";
        this.labelInsEst.Size = new System.Drawing.Size(65, 13);
        this.labelInsEst.TabIndex = 57;
        this.labelInsEst.Text = "- InsEst";
        this.labelInsEst.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // labelTotal
        //
        this.labelTotal.Location = new System.Drawing.Point(294, 2);
        this.labelTotal.Name = "labelTotal";
        this.labelTotal.Size = new System.Drawing.Size(80, 13);
        this.labelTotal.TabIndex = 55;
        this.labelTotal.Text = "Total";
        this.labelTotal.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(69, 2);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(55, 13);
        this.label7.TabIndex = 53;
        this.label7.Text = "0-30";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // text0_30
        //
        this.text0_30.Location = new System.Drawing.Point(67, 15);
        this.text0_30.Name = "text0_30";
        this.text0_30.ReadOnly = true;
        this.text0_30.Size = new System.Drawing.Size(55, 20);
        this.text0_30.TabIndex = 52;
        this.text0_30.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(122, 2);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(55, 13);
        this.label6.TabIndex = 51;
        this.label6.Text = "31-60";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // text31_60
        //
        this.text31_60.Location = new System.Drawing.Point(122, 15);
        this.text31_60.Name = "text31_60";
        this.text31_60.ReadOnly = true;
        this.text31_60.Size = new System.Drawing.Size(55, 20);
        this.text31_60.TabIndex = 50;
        this.text31_60.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(177, 2);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(55, 13);
        this.label5.TabIndex = 49;
        this.label5.Text = "61-90";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // text61_90
        //
        this.text61_90.Location = new System.Drawing.Point(177, 15);
        this.text61_90.Name = "text61_90";
        this.text61_90.ReadOnly = true;
        this.text61_90.Size = new System.Drawing.Size(55, 20);
        this.text61_90.TabIndex = 48;
        this.text61_90.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(232, 2);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(55, 13);
        this.label3.TabIndex = 47;
        this.label3.Text = "over 90";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textOver90
        //
        this.textOver90.Location = new System.Drawing.Point(232, 15);
        this.textOver90.Name = "textOver90";
        this.textOver90.ReadOnly = true;
        this.textOver90.Size = new System.Drawing.Size(55, 20);
        this.textOver90.TabIndex = 46;
        this.textOver90.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label2
        //
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label2.Location = new System.Drawing.Point(14, 2);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(50, 33);
        this.label2.TabIndex = 45;
        this.label2.Text = "Family\r\nAging";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // tabControlShow
        //
        this.tabControlShow.Controls.Add(this.tabMain);
        this.tabControlShow.Controls.Add(this.tabShow);
        this.tabControlShow.Location = new System.Drawing.Point(749, 27);
        this.tabControlShow.Name = "tabControlShow";
        this.tabControlShow.SelectedIndex = 0;
        this.tabControlShow.Size = new System.Drawing.Size(186, 497);
        this.tabControlShow.TabIndex = 216;
        //
        // tabMain
        //
        this.tabMain.BackColor = System.Drawing.Color.Transparent;
        this.tabMain.Controls.Add(this.butCreditCard);
        this.tabMain.Controls.Add(this.labelUrgFinNote);
        this.tabMain.Controls.Add(this.labelFamFinancial);
        this.tabMain.Controls.Add(this.textUrgFinNote);
        this.tabMain.Controls.Add(this.gridAcctPat);
        this.tabMain.Controls.Add(this.textFinNotes);
        this.tabMain.Location = new System.Drawing.Point(4, 22);
        this.tabMain.Name = "tabMain";
        this.tabMain.Padding = new System.Windows.Forms.Padding(3);
        this.tabMain.Size = new System.Drawing.Size(178, 471);
        this.tabMain.TabIndex = 0;
        this.tabMain.Text = "Main";
        this.tabMain.UseVisualStyleBackColor = true;
        //
        // gridAcctPat
        //
        this.gridAcctPat.setHScrollVisible(false);
        this.gridAcctPat.Location = new System.Drawing.Point(0, 135);
        this.gridAcctPat.Name = "gridAcctPat";
        this.gridAcctPat.setScrollValue(0);
        this.gridAcctPat.setSelectedRowColor(System.Drawing.Color.DarkSalmon);
        this.gridAcctPat.Size = new System.Drawing.Size(178, 180);
        this.gridAcctPat.TabIndex = 72;
        this.gridAcctPat.setTitle("Select Patient");
        this.gridAcctPat.setTranslationName("TableAccountPat");
        this.gridAcctPat.CellClick = __MultiODGridClickEventHandler.combine(this.gridAcctPat.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridAcctPat_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // tabShow
        //
        this.tabShow.BackColor = System.Drawing.Color.Transparent;
        this.tabShow.Controls.Add(this.checkShowFamilyComm);
        this.tabShow.Controls.Add(this.butToday);
        this.tabShow.Controls.Add(this.checkShowDetail);
        this.tabShow.Controls.Add(this.butDatesAll);
        this.tabShow.Controls.Add(this.but90days);
        this.tabShow.Controls.Add(this.but45days);
        this.tabShow.Controls.Add(this.butRefresh);
        this.tabShow.Controls.Add(this.labelEndDate);
        this.tabShow.Controls.Add(this.labelStartDate);
        this.tabShow.Controls.Add(this.textDateEnd);
        this.tabShow.Controls.Add(this.textDateStart);
        this.tabShow.Location = new System.Drawing.Point(4, 22);
        this.tabShow.Name = "tabShow";
        this.tabShow.Padding = new System.Windows.Forms.Padding(3);
        this.tabShow.Size = new System.Drawing.Size(178, 569);
        this.tabShow.TabIndex = 1;
        this.tabShow.Text = "Show";
        this.tabShow.UseVisualStyleBackColor = true;
        //
        // checkShowFamilyComm
        //
        this.checkShowFamilyComm.AutoSize = true;
        this.checkShowFamilyComm.Location = new System.Drawing.Point(8, 220);
        this.checkShowFamilyComm.Name = "checkShowFamilyComm";
        this.checkShowFamilyComm.Size = new System.Drawing.Size(152, 17);
        this.checkShowFamilyComm.TabIndex = 221;
        this.checkShowFamilyComm.Text = "Show Family Comm Entries";
        this.checkShowFamilyComm.UseVisualStyleBackColor = true;
        this.checkShowFamilyComm.Click += new System.EventHandler(this.checkShowFamilyComm_Click);
        //
        // checkShowDetail
        //
        this.checkShowDetail.Checked = true;
        this.checkShowDetail.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowDetail.Location = new System.Drawing.Point(8, 196);
        this.checkShowDetail.Name = "checkShowDetail";
        this.checkShowDetail.Size = new System.Drawing.Size(164, 18);
        this.checkShowDetail.TabIndex = 219;
        this.checkShowDetail.Text = "Show Proc Breakdowns";
        this.checkShowDetail.UseVisualStyleBackColor = true;
        this.checkShowDetail.Click += new System.EventHandler(this.checkShowDetail_Click);
        //
        // labelEndDate
        //
        this.labelEndDate.Location = new System.Drawing.Point(2, 34);
        this.labelEndDate.Name = "labelEndDate";
        this.labelEndDate.Size = new System.Drawing.Size(91, 14);
        this.labelEndDate.TabIndex = 211;
        this.labelEndDate.Text = "End Date";
        this.labelEndDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelStartDate
        //
        this.labelStartDate.Location = new System.Drawing.Point(8, 11);
        this.labelStartDate.Name = "labelStartDate";
        this.labelStartDate.Size = new System.Drawing.Size(84, 14);
        this.labelStartDate.TabIndex = 210;
        this.labelStartDate.Text = "Start Date";
        this.labelStartDate.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBoxIndIns
        //
        this.groupBoxIndIns.Controls.Add(this.textPriDed);
        this.groupBoxIndIns.Controls.Add(this.textPriUsed);
        this.groupBoxIndIns.Controls.Add(this.textPriDedRem);
        this.groupBoxIndIns.Controls.Add(this.textPriPend);
        this.groupBoxIndIns.Controls.Add(this.textPriRem);
        this.groupBoxIndIns.Controls.Add(this.textPriMax);
        this.groupBoxIndIns.Controls.Add(this.textSecRem);
        this.groupBoxIndIns.Controls.Add(this.label10);
        this.groupBoxIndIns.Controls.Add(this.textSecPend);
        this.groupBoxIndIns.Controls.Add(this.label11);
        this.groupBoxIndIns.Controls.Add(this.label18);
        this.groupBoxIndIns.Controls.Add(this.label12);
        this.groupBoxIndIns.Controls.Add(this.label13);
        this.groupBoxIndIns.Controls.Add(this.textSecDedRem);
        this.groupBoxIndIns.Controls.Add(this.label14);
        this.groupBoxIndIns.Controls.Add(this.label15);
        this.groupBoxIndIns.Controls.Add(this.textSecMax);
        this.groupBoxIndIns.Controls.Add(this.label16);
        this.groupBoxIndIns.Controls.Add(this.textSecDed);
        this.groupBoxIndIns.Controls.Add(this.textSecUsed);
        this.groupBoxIndIns.Location = new System.Drawing.Point(556, 144);
        this.groupBoxIndIns.Name = "groupBoxIndIns";
        this.groupBoxIndIns.Size = new System.Drawing.Size(193, 160);
        this.groupBoxIndIns.TabIndex = 219;
        this.groupBoxIndIns.TabStop = false;
        this.groupBoxIndIns.Text = "Individual Insurance";
        this.groupBoxIndIns.Visible = false;
        //
        // textPriDed
        //
        this.textPriDed.BackColor = System.Drawing.Color.White;
        this.textPriDed.Location = new System.Drawing.Point(71, 55);
        this.textPriDed.Name = "textPriDed";
        this.textPriDed.ReadOnly = true;
        this.textPriDed.Size = new System.Drawing.Size(60, 20);
        this.textPriDed.TabIndex = 45;
        this.textPriDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriUsed
        //
        this.textPriUsed.BackColor = System.Drawing.Color.White;
        this.textPriUsed.Location = new System.Drawing.Point(71, 95);
        this.textPriUsed.Name = "textPriUsed";
        this.textPriUsed.ReadOnly = true;
        this.textPriUsed.Size = new System.Drawing.Size(60, 20);
        this.textPriUsed.TabIndex = 44;
        this.textPriUsed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriDedRem
        //
        this.textPriDedRem.BackColor = System.Drawing.Color.White;
        this.textPriDedRem.Location = new System.Drawing.Point(71, 75);
        this.textPriDedRem.Name = "textPriDedRem";
        this.textPriDedRem.ReadOnly = true;
        this.textPriDedRem.Size = new System.Drawing.Size(60, 20);
        this.textPriDedRem.TabIndex = 51;
        this.textPriDedRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriPend
        //
        this.textPriPend.BackColor = System.Drawing.Color.White;
        this.textPriPend.Location = new System.Drawing.Point(71, 115);
        this.textPriPend.Name = "textPriPend";
        this.textPriPend.ReadOnly = true;
        this.textPriPend.Size = new System.Drawing.Size(60, 20);
        this.textPriPend.TabIndex = 43;
        this.textPriPend.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriRem
        //
        this.textPriRem.BackColor = System.Drawing.Color.White;
        this.textPriRem.Location = new System.Drawing.Point(71, 135);
        this.textPriRem.Name = "textPriRem";
        this.textPriRem.ReadOnly = true;
        this.textPriRem.Size = new System.Drawing.Size(60, 20);
        this.textPriRem.TabIndex = 42;
        this.textPriRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriMax
        //
        this.textPriMax.BackColor = System.Drawing.Color.White;
        this.textPriMax.Location = new System.Drawing.Point(71, 35);
        this.textPriMax.Name = "textPriMax";
        this.textPriMax.ReadOnly = true;
        this.textPriMax.Size = new System.Drawing.Size(60, 20);
        this.textPriMax.TabIndex = 38;
        this.textPriMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecRem
        //
        this.textSecRem.BackColor = System.Drawing.Color.White;
        this.textSecRem.Location = new System.Drawing.Point(130, 135);
        this.textSecRem.Name = "textSecRem";
        this.textSecRem.ReadOnly = true;
        this.textSecRem.Size = new System.Drawing.Size(60, 20);
        this.textSecRem.TabIndex = 46;
        this.textSecRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(73, 16);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(60, 15);
        this.label10.TabIndex = 31;
        this.label10.Text = "Primary";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textSecPend
        //
        this.textSecPend.BackColor = System.Drawing.Color.White;
        this.textSecPend.Location = new System.Drawing.Point(130, 115);
        this.textSecPend.Name = "textSecPend";
        this.textSecPend.ReadOnly = true;
        this.textSecPend.Size = new System.Drawing.Size(60, 20);
        this.textSecPend.TabIndex = 47;
        this.textSecPend.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(4, 37);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(66, 15);
        this.label11.TabIndex = 32;
        this.label11.Text = "Annual Max";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(2, 77);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(69, 15);
        this.label18.TabIndex = 50;
        this.label18.Text = "Ded Remain";
        this.label18.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(4, 57);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(66, 15);
        this.label12.TabIndex = 33;
        this.label12.Text = "Deductible";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(4, 97);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(66, 15);
        this.label13.TabIndex = 34;
        this.label13.Text = "Ins Used";
        this.label13.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSecDedRem
        //
        this.textSecDedRem.BackColor = System.Drawing.Color.White;
        this.textSecDedRem.Location = new System.Drawing.Point(130, 75);
        this.textSecDedRem.Name = "textSecDedRem";
        this.textSecDedRem.ReadOnly = true;
        this.textSecDedRem.Size = new System.Drawing.Size(60, 20);
        this.textSecDedRem.TabIndex = 52;
        this.textSecDedRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(4, 137);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(66, 15);
        this.label14.TabIndex = 35;
        this.label14.Text = "Remaining";
        this.label14.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(4, 117);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(66, 15);
        this.label15.TabIndex = 36;
        this.label15.Text = "Pending";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSecMax
        //
        this.textSecMax.BackColor = System.Drawing.Color.White;
        this.textSecMax.Location = new System.Drawing.Point(130, 35);
        this.textSecMax.Name = "textSecMax";
        this.textSecMax.ReadOnly = true;
        this.textSecMax.Size = new System.Drawing.Size(60, 20);
        this.textSecMax.TabIndex = 41;
        this.textSecMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(130, 16);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(60, 14);
        this.label16.TabIndex = 37;
        this.label16.Text = "Secondary";
        this.label16.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textSecDed
        //
        this.textSecDed.BackColor = System.Drawing.Color.White;
        this.textSecDed.Location = new System.Drawing.Point(130, 55);
        this.textSecDed.Name = "textSecDed";
        this.textSecDed.ReadOnly = true;
        this.textSecDed.Size = new System.Drawing.Size(60, 20);
        this.textSecDed.TabIndex = 40;
        this.textSecDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecUsed
        //
        this.textSecUsed.BackColor = System.Drawing.Color.White;
        this.textSecUsed.Location = new System.Drawing.Point(130, 95);
        this.textSecUsed.Name = "textSecUsed";
        this.textSecUsed.ReadOnly = true;
        this.textSecUsed.Size = new System.Drawing.Size(60, 20);
        this.textSecUsed.TabIndex = 39;
        this.textSecUsed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // groupBoxFamilyIns
        //
        this.groupBoxFamilyIns.Controls.Add(this.textFamPriMax);
        this.groupBoxFamilyIns.Controls.Add(this.textFamPriDed);
        this.groupBoxFamilyIns.Controls.Add(this.label4);
        this.groupBoxFamilyIns.Controls.Add(this.label8);
        this.groupBoxFamilyIns.Controls.Add(this.textFamSecMax);
        this.groupBoxFamilyIns.Controls.Add(this.label9);
        this.groupBoxFamilyIns.Controls.Add(this.textFamSecDed);
        this.groupBoxFamilyIns.Controls.Add(this.label17);
        this.groupBoxFamilyIns.Location = new System.Drawing.Point(556, 64);
        this.groupBoxFamilyIns.Name = "groupBoxFamilyIns";
        this.groupBoxFamilyIns.Size = new System.Drawing.Size(193, 80);
        this.groupBoxFamilyIns.TabIndex = 218;
        this.groupBoxFamilyIns.TabStop = false;
        this.groupBoxFamilyIns.Text = "Family Insurance";
        this.groupBoxFamilyIns.Visible = false;
        //
        // textFamPriMax
        //
        this.textFamPriMax.BackColor = System.Drawing.Color.White;
        this.textFamPriMax.Location = new System.Drawing.Point(72, 35);
        this.textFamPriMax.Name = "textFamPriMax";
        this.textFamPriMax.ReadOnly = true;
        this.textFamPriMax.Size = new System.Drawing.Size(60, 20);
        this.textFamPriMax.TabIndex = 69;
        this.textFamPriMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textFamPriDed
        //
        this.textFamPriDed.BackColor = System.Drawing.Color.White;
        this.textFamPriDed.Location = new System.Drawing.Point(72, 55);
        this.textFamPriDed.Name = "textFamPriDed";
        this.textFamPriDed.ReadOnly = true;
        this.textFamPriDed.Size = new System.Drawing.Size(60, 20);
        this.textFamPriDed.TabIndex = 65;
        this.textFamPriDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(74, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(60, 15);
        this.label4.TabIndex = 66;
        this.label4.Text = "Primary";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(4, 37);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(66, 15);
        this.label8.TabIndex = 67;
        this.label8.Text = "Annual Max";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textFamSecMax
        //
        this.textFamSecMax.BackColor = System.Drawing.Color.White;
        this.textFamSecMax.Location = new System.Drawing.Point(131, 35);
        this.textFamSecMax.Name = "textFamSecMax";
        this.textFamSecMax.ReadOnly = true;
        this.textFamSecMax.Size = new System.Drawing.Size(60, 20);
        this.textFamSecMax.TabIndex = 70;
        this.textFamSecMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(131, 16);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(60, 14);
        this.label9.TabIndex = 68;
        this.label9.Text = "Secondary";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textFamSecDed
        //
        this.textFamSecDed.BackColor = System.Drawing.Color.White;
        this.textFamSecDed.Location = new System.Drawing.Point(131, 55);
        this.textFamSecDed.Name = "textFamSecDed";
        this.textFamSecDed.ReadOnly = true;
        this.textFamSecDed.Size = new System.Drawing.Size(60, 20);
        this.textFamSecDed.TabIndex = 64;
        this.textFamSecDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(4, 57);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(66, 15);
        this.label17.TabIndex = 63;
        this.label17.Text = "Fam Ded";
        this.label17.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // gridPayPlan
        //
        this.gridPayPlan.setHScrollVisible(false);
        this.gridPayPlan.Location = new System.Drawing.Point(0, 144);
        this.gridPayPlan.Name = "gridPayPlan";
        this.gridPayPlan.setScrollValue(0);
        this.gridPayPlan.Size = new System.Drawing.Size(749, 93);
        this.gridPayPlan.TabIndex = 217;
        this.gridPayPlan.setTitle("Payment Plans");
        this.gridPayPlan.setTranslationName("TablePaymentPlans");
        this.gridPayPlan.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPayPlan.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPayPlan_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridRepeat
        //
        this.gridRepeat.setHScrollVisible(false);
        this.gridRepeat.Location = new System.Drawing.Point(0, 63);
        this.gridRepeat.Name = "gridRepeat";
        this.gridRepeat.setScrollValue(0);
        this.gridRepeat.Size = new System.Drawing.Size(749, 75);
        this.gridRepeat.TabIndex = 74;
        this.gridRepeat.setTitle("Repeating Charges");
        this.gridRepeat.setTranslationName("TableRepeatCharges");
        this.gridRepeat.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridRepeat.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridRepeat_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridAccount
        //
        this.gridAccount.setHScrollVisible(true);
        this.gridAccount.Location = new System.Drawing.Point(0, 243);
        this.gridAccount.Name = "gridAccount";
        this.gridAccount.setScrollValue(0);
        this.gridAccount.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridAccount.Size = new System.Drawing.Size(749, 179);
        this.gridAccount.TabIndex = 73;
        this.gridAccount.setTitle("Patient Account");
        this.gridAccount.setTranslationName("TableAccount");
        this.gridAccount.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridAccount.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridAccount_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridAccount.CellClick = __MultiODGridClickEventHandler.combine(this.gridAccount.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridAccount_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridComm
        //
        this.gridComm.setHScrollVisible(false);
        this.gridComm.Location = new System.Drawing.Point(0, 440);
        this.gridComm.Name = "gridComm";
        this.gridComm.setScrollValue(0);
        this.gridComm.Size = new System.Drawing.Size(749, 226);
        this.gridComm.TabIndex = 71;
        this.gridComm.setTitle("Communications Log");
        this.gridComm.setTranslationName("TableCommLogAccount");
        this.gridComm.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridComm.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridComm_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butCreditCard
        //
        this.butCreditCard.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCreditCard.setAutosize(true);
        this.butCreditCard.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCreditCard.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCreditCard.setCornerRadius(4F);
        this.butCreditCard.Location = new System.Drawing.Point(22, 103);
        this.butCreditCard.Name = "butCreditCard";
        this.butCreditCard.Size = new System.Drawing.Size(137, 26);
        this.butCreditCard.TabIndex = 216;
        this.butCreditCard.Text = "Credit Card Manage";
        this.butCreditCard.UseVisualStyleBackColor = true;
        this.butCreditCard.Click += new System.EventHandler(this.butCreditCard_Click);
        //
        // textUrgFinNote
        //
        this.textUrgFinNote.AcceptsTab = true;
        this.textUrgFinNote.BackColor = System.Drawing.Color.White;
        this.textUrgFinNote.DetectUrls = false;
        this.textUrgFinNote.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textUrgFinNote.ForeColor = System.Drawing.Color.Red;
        this.textUrgFinNote.Location = new System.Drawing.Point(0, 20);
        this.textUrgFinNote.Name = "textUrgFinNote";
        this.textUrgFinNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.None);
        this.textUrgFinNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textUrgFinNote.Size = new System.Drawing.Size(178, 77);
        this.textUrgFinNote.TabIndex = 11;
        this.textUrgFinNote.Text = "";
        this.textUrgFinNote.TextChanged += new System.EventHandler(this.textUrgFinNote_TextChanged);
        this.textUrgFinNote.Leave += new System.EventHandler(this.textUrgFinNote_Leave);
        //
        // textFinNotes
        //
        this.textFinNotes.AcceptsTab = true;
        this.textFinNotes.DetectUrls = false;
        this.textFinNotes.Location = new System.Drawing.Point(0, 337);
        this.textFinNotes.Name = "textFinNotes";
        this.textFinNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.FinancialNotes);
        this.textFinNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textFinNotes.Size = new System.Drawing.Size(178, 134);
        this.textFinNotes.TabIndex = 70;
        this.textFinNotes.Text = "";
        this.textFinNotes.TextChanged += new System.EventHandler(this.textFinNotes_TextChanged);
        this.textFinNotes.Leave += new System.EventHandler(this.textFinNotes_Leave);
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(95, 54);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(77, 24);
        this.butToday.TabIndex = 220;
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
        this.butDatesAll.Location = new System.Drawing.Point(95, 132);
        this.butDatesAll.Name = "butDatesAll";
        this.butDatesAll.Size = new System.Drawing.Size(77, 24);
        this.butDatesAll.TabIndex = 218;
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
        this.but90days.Location = new System.Drawing.Point(95, 106);
        this.but90days.Name = "but90days";
        this.but90days.Size = new System.Drawing.Size(77, 24);
        this.but90days.TabIndex = 217;
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
        this.but45days.Location = new System.Drawing.Point(95, 80);
        this.but45days.Name = "but45days";
        this.but45days.Size = new System.Drawing.Size(77, 24);
        this.but45days.TabIndex = 216;
        this.but45days.Text = "Last 45 Days";
        this.but45days.Click += new System.EventHandler(this.but45days_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.Location = new System.Drawing.Point(95, 158);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(77, 24);
        this.butRefresh.TabIndex = 214;
        this.butRefresh.Text = "Refresh";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // textDateEnd
        //
        this.textDateEnd.Location = new System.Drawing.Point(95, 31);
        this.textDateEnd.Name = "textDateEnd";
        this.textDateEnd.Size = new System.Drawing.Size(77, 20);
        this.textDateEnd.TabIndex = 213;
        //
        // textDateStart
        //
        this.textDateStart.BackColor = System.Drawing.SystemColors.Window;
        this.textDateStart.ForeColor = System.Drawing.SystemColors.WindowText;
        this.textDateStart.Location = new System.Drawing.Point(95, 8);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.Size = new System.Drawing.Size(77, 20);
        this.textDateStart.TabIndex = 212;
        //
        // butShowNone
        //
        this.butShowNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowNone.setAutosize(true);
        this.butShowNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowNone.setCornerRadius(4F);
        this.butShowNone.Location = new System.Drawing.Point(677, 207);
        this.butShowNone.Name = "butShowNone";
        this.butShowNone.Size = new System.Drawing.Size(58, 16);
        this.butShowNone.TabIndex = 216;
        this.butShowNone.Text = "None";
        this.butShowNone.Visible = false;
        this.butShowNone.Click += new System.EventHandler(this.butShowNone_Click);
        //
        // butShowAll
        //
        this.butShowAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowAll.setAutosize(true);
        this.butShowAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowAll.setCornerRadius(4F);
        this.butShowAll.Location = new System.Drawing.Point(614, 207);
        this.butShowAll.Name = "butShowAll";
        this.butShowAll.Size = new System.Drawing.Size(53, 16);
        this.butShowAll.TabIndex = 215;
        this.butShowAll.Text = "All";
        this.butShowAll.Visible = false;
        this.butShowAll.Click += new System.EventHandler(this.butShowAll_Click);
        //
        // butComm
        //
        this.butComm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butComm.setAutosize(true);
        this.butComm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butComm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butComm.setCornerRadius(4F);
        this.butComm.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butComm.Location = new System.Drawing.Point(3, 31);
        this.butComm.Name = "butComm";
        this.butComm.Size = new System.Drawing.Size(98, 26);
        this.butComm.TabIndex = 94;
        this.butComm.Text = "Questionnaire";
        this.butComm.Click += new System.EventHandler(this.butComm_Click);
        //
        // butTrojan
        //
        this.butTrojan.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTrojan.setAutosize(true);
        this.butTrojan.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTrojan.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTrojan.setCornerRadius(4F);
        this.butTrojan.Location = new System.Drawing.Point(3, 3);
        this.butTrojan.Name = "butTrojan";
        this.butTrojan.Size = new System.Drawing.Size(146, 25);
        this.butTrojan.TabIndex = 93;
        this.butTrojan.Text = "Send Transaction to Trojan";
        this.butTrojan.Click += new System.EventHandler(this.butTrojan_Click);
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(939, 25);
        this.ToolBarMain.TabIndex = 47;
        this.ToolBarMain.ButtonClick = __MultiODToolBarButtonClickEventHandler.combine(this.ToolBarMain.ButtonClick,new OpenDental.UI.ODToolBarButtonClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
                this.ToolBarMain_ButtonClick(sender, e);
            }

            public List<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODToolBarButtonClickEventHandler> ret = new ArrayList<OpenDental.UI.ODToolBarButtonClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // ContrAccount
        //
        this.Controls.Add(this.groupBoxIndIns);
        this.Controls.Add(this.groupBoxFamilyIns);
        this.Controls.Add(this.gridPayPlan);
        this.Controls.Add(this.tabControlShow);
        this.Controls.Add(this.panelAging);
        this.Controls.Add(this.panelProgNotes);
        this.Controls.Add(this.gridRepeat);
        this.Controls.Add(this.gridAccount);
        this.Controls.Add(this.gridComm);
        this.Controls.Add(this.panelSplitter);
        this.Controls.Add(this.panelCommButs);
        this.Controls.Add(this.ToolBarMain);
        this.Name = "ContrAccount";
        this.Size = new System.Drawing.Size(939, 732);
        this.Load += new System.EventHandler(this.ContrAccount_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ContrAccount_Layout);
        this.Resize += new System.EventHandler(this.ContrAccount_Resize);
        this.panelCommButs.ResumeLayout(false);
        this.panelProgNotes.ResumeLayout(false);
        this.groupBox7.ResumeLayout(false);
        this.groupBox6.ResumeLayout(false);
        this.panelAging.ResumeLayout(false);
        this.panelAging.PerformLayout();
        this.panelTotalOwes.ResumeLayout(false);
        this.tabControlShow.ResumeLayout(false);
        this.tabMain.ResumeLayout(false);
        this.tabShow.ResumeLayout(false);
        this.tabShow.PerformLayout();
        this.groupBoxIndIns.ResumeLayout(false);
        this.groupBoxIndIns.PerformLayout();
        this.groupBoxFamilyIns.ResumeLayout(false);
        this.groupBoxFamilyIns.PerformLayout();
        this.ResumeLayout(false);
    }

    /**
    * 
    */
    public void initializeOnStartup() throws Exception {
        if (InitializedOnStartup && !ViewingInRecall)
        {
            return ;
        }
         
        InitializedOnStartup = true;
        //can't use Lan.F(this);
        Lan.C(this, new Control[]{ labelStartDate, labelEndDate, label2, label7, label6, label5, label3, labelUrgFinNote, labelFamFinancial, gridAccount, gridAcctPat, gridComm, labelInsEst, labelBalance, labelPatEstBal, labelUnearned, labelInsRem, tabMain, tabShow, butToday, but45days, but90days, butDatesAll, butRefresh, butComm });
        layoutToolBar();
        if (ViewingInRecall)
        {
            panelSplitter.Top = 300;
        }
         
        //start the splitter higher for recall window.
        layoutPanels();
        checkShowFamilyComm.Checked = PrefC.getBoolSilent(PrefName.ShowAccountFamilyCommEntries,true);
        Plugins.hookAddCode(this,"ContrAccount.InitializeOnStartup_end");
    }

    private void contrAccount_Load(Object sender, System.EventArgs e) throws Exception {
        this.Parent.MouseWheel += new MouseEventHandler(Parent_MouseWheel);
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        OpenDental.UI.ODToolBarButton button;
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Payment"), 1, "", "Payment");
        //button.Style=ODToolBarButtonStyle.DropDownButton;
        //button.DropDownMenu=contextMenuPayment;
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Adjustment"), 2, "", "Adjustment"));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"New Claim"), 3, "", "Insurance");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
        button.setDropDownMenu(contextMenuIns);
        ToolBarMain.getButtons().add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Payment Plan"), -1, "", "PayPlan"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Installment Plan"), -1, "", "InstallPlan"));
        if (!PrefC.getBool(PrefName.EasyHideRepeatCharges))
        {
            button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Repeating Charge"), -1, "", "RepeatCharge");
            button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
            button.setDropDownMenu(contextMenuRepeat);
            ToolBarMain.getButtons().add(button);
        }
         
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Statement"), 4, "", "Statement");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
        button.setDropDownMenu(contextMenuStatement);
        ToolBarMain.getButtons().add(button);
        ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.AccountModule);
        for (int i = 0;i < toolButItems.Count;i++)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
        }
        ToolBarMain.Invalidate();
        Plugins.hookAddCode(this,"ContrAccount.LayoutToolBar_end",PatCur);
    }

    private void contrAccount_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
    }

    //see LayoutPanels()
    private void contrAccount_Resize(Object sender, EventArgs e) throws Exception {
        if (PrefC.hListIsNull())
        {
            return ;
        }
         
        //helps on startup.
        layoutPanels();
    }

    /**
    * This used to be a layout event, but that was making it get called far too frequently.  Now, this must explicitly and intelligently be called.
    */
    private void layoutPanels() throws Exception {
        gridAccount.Height = panelSplitter.Top - gridAccount.Location.Y + 1;
        gridAccount.Invalidate();
        gridComm.Top = panelSplitter.Bottom - 1;
        gridComm.Height = Height - gridComm.Top;
        gridComm.Invalidate();
        //panelCommButs.Top=Height-63;//panelSplitter.Bottom-1;
        panelProgNotes.Top = panelSplitter.Bottom - 1;
        panelProgNotes.Height = Height - panelProgNotes.Top;
        gridProg.Top = 0;
        gridProg.Height = panelProgNotes.Height;
        /*
        			panelBoldBalance.Left=329;
        			panelBoldBalance.Top=29;
        			panelInsInfoDetail.Top = panelBoldBalance.Top + panelBoldBalance.Height;
        			panelInsInfoDetail.Left = panelBoldBalance.Left + panelBoldBalance.Width - panelInsInfoDetail.Width;*/
        int left = textUrgFinNote.Left;
        //769;
        labelFamFinancial.Location = new Point(left, gridAcctPat.Bottom);
        textFinNotes.Location = new Point(left, labelFamFinancial.Bottom);
        //tabControlShow.Height=panelCommButs.Top-tabControlShow.Top;
        textFinNotes.Height = tabMain.Height - textFinNotes.Top;
    }

    /**
    * 
    */
    public void moduleSelected(long patNum) throws Exception {
        moduleSelected(patNum,false);
    }

    /**
    * 
    */
    public void moduleSelected(long patNum, boolean isSelectingFamily) throws Exception {
        refreshModuleData(patNum,isSelectingFamily);
        refreshModuleScreen(isSelectingFamily);
        Plugins.hookAddCode(this,"ContrAccount.ModuleSelected_end",patNum,isSelectingFamily);
    }

    /**
    * Used when jumping to this module and directly to a claim.
    */
    public void moduleSelected(long patNum, long claimNum) throws Exception {
        moduleSelected(patNum);
        DataTable table = DataSetMain.Tables["account"];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            if (!StringSupport.equals(table.Rows[i]["ClaimPaymentNum"].ToString(), "0"))
            {
                continue;
            }
             
            //claimpayment
            if (StringSupport.equals(table.Rows[i]["ClaimNum"].ToString(), "0"))
            {
                continue;
            }
             
            //not a claim or claimpayment
            long claimNumRow = PIn.Long(table.Rows[i]["ClaimNum"].ToString());
            if (claimNumRow != claimNum)
            {
                continue;
            }
             
            gridAccount.setSelected(i,true);
        }
    }

    /**
    * 
    */
    public void moduleUnselected() throws Exception {
        if (FamCur == null)
            return ;
         
        if (UrgFinNoteChanged)
        {
            //Patient tempPat=Patients.Cur;
            Patient patOld = FamCur.ListPats[0].Copy();
            //Patients.CurOld=Patients.Cur.Clone();//important
            FamCur.ListPats[0].FamFinUrgNote = textUrgFinNote.Text;
            Patients.Update(FamCur.ListPats[0], patOld);
            //Patients.GetFamily(tempPat.PatNum);
            UrgFinNoteChanged = false;
        }
         
        if (FinNoteChanged)
        {
            PatientNoteCur.FamFinancial = textFinNotes.Text;
            PatientNotes.update(PatientNoteCur,PatCur.Guarantor);
            FinNoteChanged = false;
        }
         
        //if(CCChanged){
        //  CCSave();
        //  CCChanged=false;
        //}
        FamCur = null;
        RepeatChargeList = null;
        Plugins.hookAddCode(this,"ContrAccount.ModuleUnselected_end");
    }

    /**
    * 
    */
    private void refreshModuleData(long patNum, boolean isSelectingFamily) throws Exception {
        if (patNum == 0)
        {
            PatCur = null;
            FamCur = null;
            DataSetMain = null;
            Plugins.hookAddCode(this,"ContrAccount.RefreshModuleData_null");
            return ;
        }
         
        DateTime fromDate = DateTime.MinValue;
        DateTime toDate = DateTime.MaxValue;
        if (StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), "") && StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            if (!StringSupport.equals(textDateStart.Text, ""))
            {
                fromDate = PIn.Date(textDateStart.Text);
            }
             
            if (!StringSupport.equals(textDateEnd.Text, ""))
            {
                toDate = PIn.Date(textDateEnd.Text);
            }
             
        }
         
        boolean viewingInRecall = ViewingInRecall;
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            panelTotalOwes.Top = -38;
            viewingInRecall = true;
        }
         
        DataSetMain = AccountModules.GetAll(patNum, viewingInRecall, fromDate, toDate, isSelectingFamily, checkShowDetail.Checked, true, true);
        FamCur = Patients.getFamily(patNum);
        //for now, have to get family after dataset due to aging calc.
        PatCur = FamCur.getPatient(patNum);
        PatientNoteCur = PatientNotes.refresh(PatCur.PatNum,PatCur.Guarantor);
        fillSummary();
        Plugins.hookAddCode(this,"ContrAccount.RefreshModuleData_end",FamCur,PatCur,DataSetMain,PPBalanceTotal,isSelectingFamily);
    }

    private void refreshModuleScreen(boolean isSelectingFamily) throws Exception {
        if (PatCur == null)
        {
            gridAccount.Enabled = false;
            ToolBarMain.getButtons().get___idx("Payment").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Adjustment").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Insurance").setEnabled(false);
            ToolBarMain.getButtons().get___idx("PayPlan").setEnabled(false);
            ToolBarMain.getButtons().get___idx("InstallPlan").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Statement").setEnabled(false);
            ToolBarMain.Invalidate();
            textUrgFinNote.Enabled = false;
            textFinNotes.Enabled = false;
            //butComm.Enabled=false;
            tabControlShow.Enabled = false;
            Plugins.hookAddCode(this,"ContrAccount.RefreshModuleScreen_null");
        }
        else
        {
            gridAccount.Enabled = true;
            ToolBarMain.getButtons().get___idx("Payment").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Adjustment").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Insurance").setEnabled(true);
            ToolBarMain.getButtons().get___idx("PayPlan").setEnabled(true);
            ToolBarMain.getButtons().get___idx("InstallPlan").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Statement").setEnabled(true);
            ToolBarMain.Invalidate();
            textUrgFinNote.Enabled = true;
            textFinNotes.Enabled = true;
            //butComm.Enabled=true;
            tabControlShow.Enabled = true;
        } 
        fillPats(isSelectingFamily);
        fillMisc();
        fillAging(isSelectingFamily);
        fillRepeatCharges();
        //must be in this order. 1.
        fillPaymentPlans();
        //2.
        fillMain();
        //3.
        layoutPanels();
        if (ViewingInRecall || PrefC.getBoolSilent(PrefName.FuchsOptionsOn,false))
        {
            panelProgNotes.Visible = true;
            fillProgNotes();
            if (PrefC.getBool(PrefName.FuchsOptionsOn) && PatCur != null)
            {
                //show prog note options
                groupBox6.Visible = true;
                groupBox7.Visible = true;
                butShowAll.Visible = true;
                butShowNone.Visible = true;
            }
             
        }
        else
        {
            //FillInsInfo();
            panelProgNotes.Visible = false;
            fillComm();
        } 
        Plugins.hookAddCode(this,"ContrAccount.RefreshModuleScreen_end",FamCur,PatCur,DataSetMain,PPBalanceTotal,isSelectingFamily);
    }

    //private void FillPatientButton() {
    //	Patients.AddPatsToMenu(menuPatient,new EventHandler(menuPatient_Click),PatCur,FamCur);
    //}
    private void fillPats(boolean isSelectingFamily) throws Exception {
        if (PatCur == null)
        {
            gridAcctPat.beginUpdate();
            gridAcctPat.getRows().Clear();
            gridAcctPat.endUpdate();
            return ;
        }
         
        gridAcctPat.beginUpdate();
        gridAcctPat.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableAccountPat","Patient"),105);
        gridAcctPat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableAccountPat","Bal"), 49, HorizontalAlignment.Right);
        gridAcctPat.getColumns().add(col);
        gridAcctPat.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DataTable table = DataSetMain.Tables["patient"];
        double bal = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            bal += (double)table.Rows[i]["balanceDouble"];
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["name"].ToString());
            row.getCells().Add(table.Rows[i]["balance"].ToString());
            if (i == 0 || i == table.Rows.Count - 1)
            {
                row.setBold(true);
            }
             
            gridAcctPat.getRows().add(row);
        }
        gridAcctPat.endUpdate();
        if (isSelectingFamily)
        {
            gridAcctPat.SetSelected(FamCur.ListPats.Length, true);
        }
        else
        {
            for (int i = 0;i < FamCur.ListPats.Length;i++)
            {
                if (FamCur.ListPats[i].PatNum == PatCur.PatNum)
                {
                    gridAcctPat.setSelected(i,true);
                }
                 
            }
        } 
        if (isSelectingFamily)
        {
            ToolBarMain.getButtons().get___idx("Insurance").setEnabled(false);
        }
        else
        {
            ToolBarMain.getButtons().get___idx("Insurance").setEnabled(true);
        } 
    }

    private void fillMisc() throws Exception {
        //textCC.Text="";
        //textCCexp.Text="";
        if (PatCur == null)
        {
            textUrgFinNote.Text = "";
            textFinNotes.Text = "";
        }
        else
        {
            textUrgFinNote.Text = FamCur.ListPats[0].FamFinUrgNote;
            textFinNotes.Text = PatientNoteCur.FamFinancial;
            if (!textFinNotes.Focused)
            {
                textFinNotes.SelectionStart = textFinNotes.Text.Length;
                //This will cause a crash if the richTextBox currently has focus. We don't know why.
                //Only happens if you call this during a Leave event, and only when moving between two ODtextBoxes.
                //Tested with two ordinary richTextBoxes, and the problem does not exist.
                //We may pursue fixing the root problem some day, but this workaround will do for now.
                textFinNotes.ScrollToCaret();
            }
             
            if (!textUrgFinNote.Focused)
            {
                textUrgFinNote.SelectionStart = 0;
                textUrgFinNote.ScrollToCaret();
            }
             
        } 
        //if(PrefC.GetBool(PrefName.StoreCCnumbers)) {
        //string cc=PatientNoteCur.CCNumber;
        //if(Regex.IsMatch(cc,@"^\d{16}$")){
        //  textCC.Text=cc.Substring(0,4)+"-"+cc.Substring(4,4)+"-"+cc.Substring(8,4)+"-"+cc.Substring(12,4);
        //}
        //else{
        //  textCC.Text=cc;
        //}
        //if(PatientNoteCur.CCExpiration.Year>2000){
        //  textCCexp.Text=PatientNoteCur.CCExpiration.ToString("MM/yy");
        //}
        //else{
        //  textCCexp.Text="";
        //}
        //}
        UrgFinNoteChanged = false;
        FinNoteChanged = false;
        CCChanged = false;
        if (ViewingInRecall)
        {
            textUrgFinNote.ReadOnly = true;
            textFinNotes.ReadOnly = true;
        }
        else
        {
            textUrgFinNote.ReadOnly = false;
            textFinNotes.ReadOnly = false;
        } 
    }

    private void fillAging(boolean isSelectingFamily) throws Exception {
        if (Plugins.hookMethod(this,"ContrAccount.FillAging",FamCur,PatCur,DataSetMain,isSelectingFamily))
        {
            return ;
        }
         
        if (PatCur != null)
        {
            textOver90.Text = FamCur.ListPats[0].BalOver90.ToString("F");
            text61_90.Text = FamCur.ListPats[0].Bal_61_90.ToString("F");
            text31_60.Text = FamCur.ListPats[0].Bal_31_60.ToString("F");
            text0_30.Text = FamCur.ListPats[0].Bal_0_30.ToString("F");
            double total = (double)FamCur.ListPats[0].BalTotal;
            labelTotalAmt.Text = total.ToString("F");
            labelInsEstAmt.Text = FamCur.ListPats[0].InsEst.ToString("F");
            labelBalanceAmt.Text = (total - (double)FamCur.ListPats[0].InsEst).ToString("F");
            labelPatEstBalAmt.Text = "";
            DataTable tableMisc = DataSetMain.Tables["misc"];
            if (!isSelectingFamily)
            {
                for (int i = 0;i < tableMisc.Rows.Count;i++)
                {
                    if (StringSupport.equals(tableMisc.Rows[i]["descript"].ToString(), "patInsEst"))
                    {
                        double estBal = (double)PatCur.EstBalance - PIn.Decimal(tableMisc.Rows[i]["value"].ToString());
                        labelPatEstBalAmt.Text = estBal.ToString("F");
                    }
                     
                }
            }
             
            labelUnearnedAmt.Text = "";
            for (int i = 0;i < tableMisc.Rows.Count;i++)
            {
                if (StringSupport.equals(tableMisc.Rows[i]["descript"].ToString(), "unearnedIncome"))
                {
                    double unearned = PIn.Decimal(tableMisc.Rows[i]["value"].ToString());
                    if (unearned != 0)
                    {
                        labelUnearnedAmt.Text = unearned.ToString("F");
                    }
                     
                }
                 
            }
            //labelInsLeft.Text=Lan.g(this,"Ins Left");
            //labelInsLeftAmt.Text="";//etc. Will be same for everyone
            Font fontBold = new Font(FontFamily.GenericSansSerif, 11, FontStyle.Bold);
            //In the new way of doing it, they are all visible and calculated identically,
            //but the emphasis simply changes by slight renaming of labels
            //and by font size changes.
            if (PrefC.getBool(PrefName.BalancesDontSubtractIns))
            {
                labelTotal.Text = Lan.g(this,"Balance");
                labelTotalAmt.Font = fontBold;
                labelTotalAmt.ForeColor = Color.Firebrick;
                panelAgeLine.Visible = true;
                //verical line
                labelInsEst.Text = Lan.g(this,"Ins Pending");
                labelBalance.Text = Lan.g(this,"After Ins");
                labelBalanceAmt.Font = this.Font;
                labelBalanceAmt.ForeColor = Color.Black;
            }
            else
            {
                //this is more common
                labelTotal.Text = Lan.g(this,"Total");
                labelTotalAmt.Font = this.Font;
                labelTotalAmt.ForeColor = Color.Black;
                panelAgeLine.Visible = false;
                labelInsEst.Text = Lan.g(this,"-InsEst");
                labelBalance.Text = Lan.g(this,"=Est Bal");
                labelBalanceAmt.Font = fontBold;
                labelBalanceAmt.ForeColor = Color.Firebrick;
                if (PrefC.getBool(PrefName.FuchsOptionsOn))
                {
                    labelTotal.Text = Lan.g(this,"Balance");
                    labelBalance.Text = Lan.g(this,"=Owed Now");
                    labelTotalAmt.Font = fontBold;
                }
                 
            } 
        }
        else
        {
            textOver90.Text = "";
            text61_90.Text = "";
            text31_60.Text = "";
            text0_30.Text = "";
            labelTotalAmt.Text = "";
            labelInsEstAmt.Text = "";
            labelBalanceAmt.Text = "";
            labelPatEstBalAmt.Text = "";
            labelUnearnedAmt.Text = "";
        } 
    }

    //labelInsLeftAmt.Text="";
    /**
    * 
    */
    private void fillRepeatCharges() throws Exception {
        if (PatCur == null)
        {
            gridRepeat.Visible = false;
            return ;
        }
         
        RepeatChargeList = RepeatCharges.refresh(PatCur.PatNum);
        if (RepeatChargeList.Length == 0)
        {
            gridRepeat.Visible = false;
            return ;
        }
         
        gridRepeat.Visible = true;
        gridRepeat.Height = 92;
        //=140;
        gridRepeat.beginUpdate();
        gridRepeat.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRepeatCharges","Description"),150);
        gridRepeat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRepeatCharges","Amount"), 60, HorizontalAlignment.Right);
        gridRepeat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRepeatCharges","Start Date"), 70, HorizontalAlignment.Center);
        gridRepeat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRepeatCharges","Stop Date"), 70, HorizontalAlignment.Center);
        gridRepeat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRepeatCharges","Enabled"), 55, HorizontalAlignment.Center);
        gridRepeat.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRepeatCharges","Note"),355);
        gridRepeat.getColumns().add(col);
        gridRepeat.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        ProcedureCode procCode;
        for (int i = 0;i < RepeatChargeList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            procCode = ProcedureCodes.GetProcCode(RepeatChargeList[i].ProcCode);
            row.getCells().add(procCode.Descript);
            row.getCells().Add(RepeatChargeList[i].ChargeAmt.ToString("F"));
            if (RepeatChargeList[i].DateStart.Year > 1880)
            {
                row.getCells().Add(RepeatChargeList[i].DateStart.ToShortDateString());
            }
            else
            {
                row.getCells().add("");
            } 
            if (RepeatChargeList[i].DateStop.Year > 1880)
            {
                row.getCells().Add(RepeatChargeList[i].DateStop.ToShortDateString());
            }
            else
            {
                row.getCells().add("");
            } 
            if (RepeatChargeList[i].IsEnabled)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(RepeatChargeList[i].Note);
            gridRepeat.getRows().add(row);
        }
        gridRepeat.endUpdate();
    }

    private void fillPaymentPlans() throws Exception {
        PPBalanceTotal = 0;
        if (PatCur == null)
        {
            gridPayPlan.Visible = false;
            return ;
        }
         
        DataTable table = DataSetMain.Tables["payplan"];
        if (table.Rows.Count == 0)
        {
            gridPayPlan.Visible = false;
            return ;
        }
         
        if (gridRepeat.Visible)
        {
            gridPayPlan.Location = new Point(0, gridRepeat.Bottom + 3);
        }
        else
        {
            gridPayPlan.Location = gridRepeat.Location;
        } 
        gridPayPlan.Visible = true;
        gridPayPlan.Height = 100;
        gridPayPlan.beginUpdate();
        gridPayPlan.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TablePaymentPlans","Date"),65);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Guarantor"),100);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Patient"),100);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Type"), 30, HorizontalAlignment.Center);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Principal"), 70, HorizontalAlignment.Right);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Total Cost"), 70, HorizontalAlignment.Right);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Paid"), 70, HorizontalAlignment.Right);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","PrincPaid"), 70, HorizontalAlignment.Right);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Balance"), 70, HorizontalAlignment.Right);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePaymentPlans","Due Now"), 70, HorizontalAlignment.Right);
        gridPayPlan.getColumns().add(col);
        col = new ODGridColumn("",70);
        //filler
        gridPayPlan.getColumns().add(col);
        gridPayPlan.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        OpenDental.UI.ODGridCell cell;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["date"].ToString());
            if (!StringSupport.equals(table.Rows[i]["InstallmentPlanNum"].ToString(), "0") && table.Rows[i]["PatNum"].ToString() != PatCur.Guarantor.ToString())
            {
                //Installment plan and not on guar
                cell = new OpenDental.UI.ODGridCell(((String)"Invalid Guarantor"));
                cell.setBold(YN.Yes);
                cell.setColorText(Color.Red);
            }
            else
            {
                cell = new OpenDental.UI.ODGridCell(table.Rows[i]["guarantor"].ToString());
            } 
            row.getCells().Add(cell);
            row.getCells().Add(table.Rows[i]["patient"].ToString());
            row.getCells().Add(table.Rows[i]["type"].ToString());
            row.getCells().Add(table.Rows[i]["principal"].ToString());
            row.getCells().Add(table.Rows[i]["totalCost"].ToString());
            row.getCells().Add(table.Rows[i]["paid"].ToString());
            row.getCells().Add(table.Rows[i]["princPaid"].ToString());
            row.getCells().Add(table.Rows[i]["balance"].ToString());
            cell = new OpenDental.UI.ODGridCell(table.Rows[i]["due"].ToString());
            if (!StringSupport.equals(table.Rows[i]["type"].ToString(), "Ins"))
            {
                cell.setBold(YN.Yes);
                cell.setColorText(Color.Red);
            }
             
            row.getCells().Add(cell);
            row.getCells().add("");
            gridPayPlan.getRows().add(row);
            PPBalanceTotal += (Convert.ToDecimal(PIn.Double(table.Rows[i]["balance"].ToString())));
        }
        gridPayPlan.endUpdate();
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            panelTotalOwes.Top = 1;
            labelTotalPtOwes.Text = (PPBalanceTotal + (double)FamCur.ListPats[0].BalTotal - (double)FamCur.ListPats[0].InsEst).ToString("F");
        }
         
    }

    /**
    * Fills the commlog grid on this form.  It does not refresh the data from the database.
    */
    private void fillComm() throws Exception {
        if (DataSetMain == null)
        {
            gridComm.beginUpdate();
            gridComm.getRows().Clear();
            gridComm.endUpdate();
            panelCommButs.Enabled = false;
            return ;
        }
         
        panelCommButs.Enabled = true;
        boolean isSelectingFamily = gridAcctPat.getSelectedIndex() == this.DataSetMain.Tables["patient"].Rows.Count - 1;
        gridComm.beginUpdate();
        gridComm.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableCommLogAccount","Date"),70);
        gridComm.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCommLogAccount","Time"),42);
        //,HorizontalAlignment.Right);
        gridComm.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCommLogAccount","Name"),80);
        gridComm.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCommLogAccount","Type"),80);
        gridComm.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCommLogAccount","Mode"),55);
        gridComm.getColumns().add(col);
        //col = new ODGridColumn(Lan.g("TableCommLogAccount", "Sent/Recd"), 75);
        //gridComm.Columns.Add(col);
        col = new ODGridColumn(Lan.g("TableCommLogAccount","Note"),455);
        gridComm.getColumns().add(col);
        gridComm.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DataTable table = DataSetMain.Tables["Commlog"];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //TODO: transition this to using the Tag object.
            //Skip commlog entries which belong to other family members per user option.
            //TODO: This does not handle jr./sr. family members. i.e. When there is a John and John Jr.
            //show family not checked
            //family not selected
            //not this patient
            if (!this.checkShowFamilyComm.Checked && !isSelectingFamily && !StringSupport.equals(table.Rows[i]["patName"].ToString(), PatCur.FName) && !StringSupport.equals(table.Rows[i]["patName"].ToString(), ""))
            {
                continue;
            }
             
            //No name; For example, formpat
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["commDate"].ToString());
            row.getCells().Add(table.Rows[i]["commTime"].ToString());
            if (isSelectingFamily)
            {
                row.getCells().Add(table.Rows[i]["patName"].ToString());
            }
            else
            {
                //one patient
                //Matching FName is not perfect because children can have the same names as parents.
                if (StringSupport.equals(table.Rows[i]["patName"].ToString(), PatCur.FName))
                {
                    //if this patient
                    row.getCells().add("");
                }
                else
                {
                    //other patient
                    row.getCells().Add(table.Rows[i]["patName"].ToString());
                } 
            } 
            row.getCells().Add(table.Rows[i]["commType"].ToString());
            row.getCells().Add(table.Rows[i]["mode"].ToString());
            //row.Cells.Add(table.Rows[i]["sentOrReceived"].ToString());
            row.getCells().Add(table.Rows[i]["Note"].ToString());
            gridComm.getRows().add(row);
        }
        gridComm.endUpdate();
        gridComm.scrollToEnd();
    }

    private void fillMain() throws Exception {
        if (gridPayPlan.Visible)
        {
            gridAccount.Location = new Point(0, gridPayPlan.Bottom + 3);
        }
        else if (gridRepeat.Visible)
        {
            gridAccount.Location = new Point(0, gridRepeat.Bottom + 3);
        }
        else
        {
            gridAccount.Location = gridRepeat.Location;
        }  
        gridAccount.beginUpdate();
        gridAccount.getColumns().Clear();
        ODGridColumn col;
        fieldsForMainGrid = DisplayFields.getForCategory(DisplayFieldCategory.AccountModule);
        HorizontalAlignment align = new HorizontalAlignment();
        for (int i = 0;i < fieldsForMainGrid.Count;i++)
        {
            align = HorizontalAlignment.Left;
            if (StringSupport.equals(fieldsForMainGrid[i].InternalName, "Charges") || StringSupport.equals(fieldsForMainGrid[i].InternalName, "Credits") || StringSupport.equals(fieldsForMainGrid[i].InternalName, "Balance"))
            {
                align = HorizontalAlignment.Right;
            }
             
            if (StringSupport.equals(fieldsForMainGrid[i].Description, ""))
            {
                col = new ODGridColumn(fieldsForMainGrid[i].InternalName, fieldsForMainGrid[i].ColumnWidth, align);
            }
            else
            {
                col = new ODGridColumn(fieldsForMainGrid[i].Description, fieldsForMainGrid[i].ColumnWidth, align);
            } 
            gridAccount.getColumns().add(col);
        }
        gridAccount.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DataTable table = null;
        if (PatCur == null)
        {
            table = new DataTable();
        }
        else
        {
            table = DataSetMain.Tables["account"];
        } 
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int f = 0;f < fieldsForMainGrid.Count;f++)
            {
                InternalName __dummyScrutVar0 = fieldsForMainGrid[f].InternalName;
                if (__dummyScrutVar0.equals("Date"))
                {
                    row.getCells().Add(table.Rows[i]["date"].ToString());
                }
                else if (__dummyScrutVar0.equals("Patient"))
                {
                    row.getCells().Add(table.Rows[i]["patient"].ToString());
                }
                else if (__dummyScrutVar0.equals("Prov"))
                {
                    row.getCells().Add(table.Rows[i]["prov"].ToString());
                }
                else if (__dummyScrutVar0.equals("Clinic"))
                {
                    row.getCells().Add(table.Rows[i]["clinic"].ToString());
                }
                else if (__dummyScrutVar0.equals("Code"))
                {
                    row.getCells().Add(table.Rows[i]["ProcCode"].ToString());
                }
                else if (__dummyScrutVar0.equals("Tth"))
                {
                    row.getCells().Add(table.Rows[i]["tth"].ToString());
                }
                else if (__dummyScrutVar0.equals("Description"))
                {
                    row.getCells().Add(table.Rows[i]["description"].ToString());
                }
                else if (__dummyScrutVar0.equals("Charges"))
                {
                    row.getCells().Add(table.Rows[i]["charges"].ToString());
                }
                else if (__dummyScrutVar0.equals("Credits"))
                {
                    row.getCells().Add(table.Rows[i]["credits"].ToString());
                }
                else if (__dummyScrutVar0.equals("Balance"))
                {
                    row.getCells().Add(table.Rows[i]["balance"].ToString());
                }
                          
            }
            row.setColorText(Color.FromArgb(PIn.Int(table.Rows[i]["colorText"].ToString())));
            //last row
            if (i == table.Rows.Count - 1 || (DateTime)table.Rows[i]["DateTime"] != (DateTime)table.Rows[i + 1]["DateTime"])
            {
                row.setColorLborder(Color.Black);
            }
             
            gridAccount.getRows().add(row);
        }
        gridAccount.endUpdate();
        if (Actscrollval == 0)
        {
            gridAccount.scrollToEnd();
        }
        else
        {
            gridAccount.setScrollValue(Actscrollval);
            Actscrollval = 0;
        } 
    }

    private void fillSummary() throws Exception {
        textFamPriMax.Text = "";
        textFamPriDed.Text = "";
        textFamSecMax.Text = "";
        textFamSecDed.Text = "";
        textPriMax.Text = "";
        textPriDed.Text = "";
        textPriDedRem.Text = "";
        textPriUsed.Text = "";
        textPriPend.Text = "";
        textPriRem.Text = "";
        textSecMax.Text = "";
        textSecDed.Text = "";
        textSecDedRem.Text = "";
        textSecUsed.Text = "";
        textSecPend.Text = "";
        textSecRem.Text = "";
        if (PatCur == null)
        {
            return ;
        }
         
        double maxFam = 0;
        double maxInd = 0;
        double ded = 0;
        double dedFam = 0;
        double dedRem = 0;
        double remain = 0;
        double pend = 0;
        double used = 0;
        InsPlan PlanCur;
        InsSub SubCur;
        List<InsSub> subList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> InsPlanList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> PatPlanList = PatPlans.refresh(PatCur.PatNum);
        List<Benefit> BenefitList = Benefits.Refresh(PatPlanList, subList);
        List<Claim> ClaimList = Claims.refresh(PatCur.PatNum);
        List<ClaimProcHist> HistList = ClaimProcs.GetHistList(PatCur.PatNum, BenefitList, PatPlanList, InsPlanList, DateTimeOD.getToday(), subList);
        if (PatPlanList.Count > 0)
        {
            SubCur = InsSubs.GetSub(PatPlanList[0].InsSubNum, subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, InsPlanList);
            pend = InsPlans.GetPendingDisplay(HistList, DateTime.Today, PlanCur, PatPlanList[0].PatPlanNum, -1, PatCur.PatNum, PatPlanList[0].InsSubNum, BenefitList);
            used = InsPlans.GetInsUsedDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, -1, InsPlanList, BenefitList, PatCur.PatNum, PatPlanList[0].InsSubNum);
            textPriPend.Text = pend.ToString("F");
            textPriUsed.Text = used.ToString("F");
            maxFam = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, true);
            maxInd = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, false);
            if (maxFam == -1)
            {
                textFamPriMax.Text = "";
            }
            else
            {
                textFamPriMax.Text = maxFam.ToString("F");
            } 
            if (maxInd == -1)
            {
                //if annual max is blank
                textPriMax.Text = "";
                textPriRem.Text = "";
            }
            else
            {
                remain = maxInd - used - pend;
                if (remain < 0)
                {
                    remain = 0;
                }
                 
                //textFamPriMax.Text=max.ToString("F");
                textPriMax.Text = maxInd.ToString("F");
                textPriRem.Text = remain.ToString("F");
            } 
            //deductible:
            ded = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, BenefitCoverageLevel.Individual);
            dedFam = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, BenefitCoverageLevel.Family);
            if (ded != -1)
            {
                textPriDed.Text = ded.ToString("F");
                dedRem = InsPlans.GetDedRemainDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, -1, InsPlanList, PatCur.PatNum, ded, dedFam);
                textPriDedRem.Text = dedRem.ToString("F");
            }
             
            if (dedFam != -1)
            {
                textFamPriDed.Text = dedFam.ToString("F");
            }
             
        }
         
        if (PatPlanList.Count > 1)
        {
            SubCur = InsSubs.GetSub(PatPlanList[1].InsSubNum, subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, InsPlanList);
            pend = InsPlans.GetPendingDisplay(HistList, DateTime.Today, PlanCur, PatPlanList[1].PatPlanNum, -1, PatCur.PatNum, PatPlanList[1].InsSubNum, BenefitList);
            textSecPend.Text = pend.ToString("F");
            used = InsPlans.GetInsUsedDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, -1, InsPlanList, BenefitList, PatCur.PatNum, PatPlanList[1].InsSubNum);
            textSecUsed.Text = used.ToString("F");
            //max=Benefits.GetAnnualMaxDisplay(BenefitList,PlanCur.PlanNum,PatPlanList[1].PatPlanNum);
            maxFam = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, true);
            maxInd = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, false);
            if (maxFam == -1)
            {
                textFamSecMax.Text = "";
            }
            else
            {
                textFamSecMax.Text = maxFam.ToString("F");
            } 
            if (maxInd == -1)
            {
                //if annual max is blank
                textSecMax.Text = "";
                textSecRem.Text = "";
            }
            else
            {
                remain = maxInd - used - pend;
                if (remain < 0)
                {
                    remain = 0;
                }
                 
                //textFamSecMax.Text=max.ToString("F");
                textSecMax.Text = maxInd.ToString("F");
                textSecRem.Text = remain.ToString("F");
            } 
            //deductible:
            ded = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, BenefitCoverageLevel.Individual);
            dedFam = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, BenefitCoverageLevel.Family);
            if (ded != -1)
            {
                textSecDed.Text = ded.ToString("F");
                dedRem = InsPlans.GetDedRemainDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, -1, InsPlanList, PatCur.PatNum, ded, dedFam);
                textSecDedRem.Text = dedRem.ToString("F");
            }
             
            if (dedFam != -1)
            {
                textFamSecDed.Text = dedFam.ToString("F");
            }
             
        }
         
    }

    private void gridAccount_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        DataTable table = DataSetMain.Tables["account"];
        //this seems to fire after a doubleclick, so this prevents error:
        if (e.getRow() >= table.Rows.Count)
        {
            return ;
        }
         
        if (ViewingInRecall)
            return ;
         
        if (!StringSupport.equals(table.Rows[e.getRow()]["ClaimNum"].ToString(), "0"))
        {
            //claims and claimpayments
            //Claim ClaimCur=Claims.GetClaim(
            //	arrayClaim[AcctLineList[e.Row].Index];
            String[] procsOnClaim = table.Rows[e.getRow()]["procsOnObj"].ToString().Split(',');
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //loop through all rows
                if (table.Rows[i]["ClaimNum"].ToString() == table.Rows[e.getRow()]["ClaimNum"].ToString())
                {
                    gridAccount.setSelected(i,true);
                }
                else //for the claim payments
                if (StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
                {
                    continue;
                }
                  
                for (int j = 0;j < procsOnClaim.Length;j++)
                {
                    //if not a procedure, then skip
                    if (table.Rows[i]["ProcNum"].ToString() == procsOnClaim[j])
                    {
                        gridAccount.setSelected(i,true);
                    }
                     
                }
            }
        }
         
        if (!StringSupport.equals(table.Rows[e.getRow()]["PayNum"].ToString(), "0"))
        {
            String[] procsOnPayment = table.Rows[e.getRow()]["procsOnObj"].ToString().Split(',');
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //loop through all rows
                if (table.Rows[i]["PayNum"].ToString() == table.Rows[e.getRow()]["PayNum"].ToString())
                {
                    gridAccount.setSelected(i,true);
                }
                 
                //for other splits in family view
                if (StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
                {
                    continue;
                }
                 
                for (int j = 0;j < procsOnPayment.Length;j++)
                {
                    //if not a procedure, then skip
                    if (table.Rows[i]["ProcNum"].ToString() == procsOnPayment[j])
                    {
                        gridAccount.setSelected(i,true);
                    }
                     
                }
            }
        }
         
    }

    private void gridAccount_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (ViewingInRecall)
            return ;
         
        Actscrollval = gridAccount.getScrollValue();
        DataTable table = DataSetMain.Tables["account"];
        if (!StringSupport.equals(table.Rows[e.getRow()]["ProcNum"].ToString(), "0"))
        {
            Procedure proc = Procedures.GetOneProc(PIn.Long(table.Rows[e.getRow()]["ProcNum"].ToString()), true);
            Patient pat = FamCur.getPatient(proc.PatNum);
            FormProcEdit FormPE = new FormProcEdit(proc,pat,FamCur);
            FormPE.ShowDialog();
        }
        else if (!StringSupport.equals(table.Rows[e.getRow()]["AdjNum"].ToString(), "0"))
        {
            Adjustment adj = Adjustments.GetOne(PIn.Long(table.Rows[e.getRow()]["AdjNum"].ToString()));
            FormAdjust FormAdj = new FormAdjust(PatCur,adj);
            FormAdj.ShowDialog();
        }
        else if (!StringSupport.equals(table.Rows[e.getRow()]["PayNum"].ToString(), "0"))
        {
            Payment PaymentCur = Payments.GetPayment(PIn.Long(table.Rows[e.getRow()]["PayNum"].ToString()));
            /*
            				if(PaymentCur.PayType==0){//provider income transfer
            					FormProviderIncTrans FormPIT=new FormProviderIncTrans();
            					FormPIT.PatNum=PatCur.PatNum;
            					FormPIT.PaymentCur=PaymentCur;
            					FormPIT.IsNew=false;
            					FormPIT.ShowDialog();
            				}
            				else{*/
            FormPayment FormPayment2 = new FormPayment(PatCur,FamCur,PaymentCur);
            FormPayment2.IsNew = false;
            FormPayment2.ShowDialog();
        }
        else //}
        if (!StringSupport.equals(table.Rows[e.getRow()]["ClaimNum"].ToString(), "0"))
        {
            //claims and claimpayments
            Claim claim = Claims.GetClaim(PIn.Long(table.Rows[e.getRow()]["ClaimNum"].ToString()));
            Patient pat = FamCur.getPatient(claim.PatNum);
            FormClaimEdit FormClaimEdit2 = new FormClaimEdit(claim,pat,FamCur);
            FormClaimEdit2.IsNew = false;
            FormClaimEdit2.ShowDialog();
        }
        else if (!StringSupport.equals(table.Rows[e.getRow()]["StatementNum"].ToString(), "0"))
        {
            Statement stmt = Statements.CreateObject(PIn.Long(table.Rows[e.getRow()]["StatementNum"].ToString()));
            FormStatementOptions FormS = new FormStatementOptions();
            FormS.StmtCur = stmt;
            FormS.ShowDialog();
        }
        else if (!StringSupport.equals(table.Rows[e.getRow()]["PayPlanNum"].ToString(), "0"))
        {
            PayPlan payplan = PayPlans.GetOne(PIn.Long(table.Rows[e.getRow()]["PayPlanNum"].ToString()));
            FormPayPlan2 = new FormPayPlan(PatCur,payplan);
            FormPayPlan2.ShowDialog();
            if (FormPayPlan2.GotoPatNum != 0)
            {
                moduleSelected(FormPayPlan2.GotoPatNum,false);
                return ;
            }
             
        }
              
        boolean isSelectingFamily = gridAcctPat.getSelectedIndex() == this.DataSetMain.Tables["patient"].Rows.Count - 1;
        moduleSelected(PatCur.PatNum,isSelectingFamily);
    }

    private void gridPayPlan_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        DataTable table = DataSetMain.Tables["payplan"];
        if (!StringSupport.equals(table.Rows[e.getRow()]["PayPlanNum"].ToString(), "0"))
        {
            //Payment plan
            PayPlan payplan = PayPlans.GetOne(PIn.Long(table.Rows[e.getRow()]["PayPlanNum"].ToString()));
            FormPayPlan2 = new FormPayPlan(PatCur,payplan);
            FormPayPlan2.ShowDialog();
            if (FormPayPlan2.GotoPatNum != 0)
            {
                moduleSelected(FormPayPlan2.GotoPatNum,false);
                return ;
            }
             
            boolean isSelectingFamily = gridAcctPat.getSelectedIndex() == this.DataSetMain.Tables["patient"].Rows.Count - 1;
            moduleSelected(PatCur.PatNum,isSelectingFamily);
        }
        else
        {
            //Installment Plan
            FormInstallmentPlanEdit FormIPE = new FormInstallmentPlanEdit();
            FormIPE.InstallmentPlanCur = InstallmentPlans.GetOne(PIn.Long(table.Rows[e.getRow()]["InstallmentPlanNum"].ToString()));
            FormIPE.IsNew = false;
            FormIPE.ShowDialog();
            moduleSelected(PatCur.PatNum);
        } 
    }

    private void gridAcctPat_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (ViewingInRecall)
        {
            return ;
        }
         
        if (e.getRow() == FamCur.ListPats.Length)
        {
            //last row
            OnPatientSelected(FamCur.ListPats[0]);
            ModuleSelected(FamCur.ListPats[0].PatNum, true);
        }
        else
        {
            OnPatientSelected(FamCur.ListPats[e.getRow()]);
            ModuleSelected(FamCur.ListPats[e.getRow()].PatNum);
        } 
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            //standard predefined button
            Object.APPLY __dummyScrutVar1 = e.getButton().getTag().ToString();
            //case "Patient":
            //	OnPat_Click();
            //	break;
            if (__dummyScrutVar1.equals("Payment"))
            {
                toolBarButPay_Click();
            }
            else if (__dummyScrutVar1.equals("Adjustment"))
            {
                toolBarButAdj_Click();
            }
            else if (__dummyScrutVar1.equals("Insurance"))
            {
                toolBarButIns_Click();
            }
            else if (__dummyScrutVar1.equals("PayPlan"))
            {
                toolBarButPayPlan_Click();
            }
            else if (__dummyScrutVar1.equals("InstallPlan"))
            {
                toolBarButInstallPlan_Click();
            }
            else if (__dummyScrutVar1.equals("RepeatCharge"))
            {
                toolBarButRepeatCharge_Click();
            }
            else if (__dummyScrutVar1.equals("Statement"))
            {
                toolBarButStatement_Click();
            }
                   
        }
        else if (e.getButton().getTag().GetType() == long.class)
        {
            ProgramL.execute((long)e.getButton().getTag(),PatCur);
        }
          
    }

    /**
    * 
    */
    private void onPatientSelected(Patient pat) throws Exception {
        PatientSelectedEventArgs eArgs = new OpenDental.PatientSelectedEventArgs(pat);
        if (PatientSelected != null)
        {
            PatientSelected.invoke(this,eArgs);
        }
         
    }

    private void toolBarButPay_Click() throws Exception {
        Payment PaymentCur = new Payment();
        PaymentCur.PayDate = DateTimeOD.getToday();
        PaymentCur.PatNum = PatCur.PatNum;
        PaymentCur.ClinicNum = PatCur.ClinicNum;
        PaymentCur.DateEntry = DateTimeOD.getToday();
        //So that it will show properly in the new window.
        if (DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()].Length > 0)
        {
            PaymentCur.PayType = DefC.getShort()[((Enum)DefCat.PaymentTypes).ordinal()][0].DefNum;
        }
         
        Payments.insert(PaymentCur);
        FormPayment FormPayment2 = new FormPayment(PatCur,FamCur,PaymentCur);
        FormPayment2.IsNew = true;
        FormPayment2.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void toolBarButAdj_Click() throws Exception {
        Adjustment AdjustmentCur = new Adjustment();
        AdjustmentCur.DateEntry = DateTime.Today;
        //cannot be changed. Handled automatically
        AdjustmentCur.AdjDate = DateTime.Today;
        AdjustmentCur.ProcDate = DateTime.Today;
        AdjustmentCur.ProvNum = PatCur.PriProv;
        AdjustmentCur.PatNum = PatCur.PatNum;
        AdjustmentCur.ClinicNum = PatCur.ClinicNum;
        FormAdjust FormAdjust2 = new FormAdjust(PatCur,AdjustmentCur);
        FormAdjust2.IsNew = true;
        FormAdjust2.ShowDialog();
        //Shared.ComputeBalances();
        moduleSelected(PatCur.PatNum);
    }

    private boolean checkClearinghouseDefaults() throws Exception {
        if (PrefC.getLong(PrefName.ClearinghouseDefaultDent) == 0)
        {
            MsgBox.show(this,"No default dental clearinghouse defined.");
            return false;
        }
         
        if (PrefC.getBool(PrefName.ShowFeatureMedicalInsurance) && PrefC.getLong(PrefName.ClearinghouseDefaultMed) == 0)
        {
            MsgBox.show(this,"No default medical clearinghouse defined.");
            return false;
        }
         
        return true;
    }

    private void toolBarButIns_Click() throws Exception {
        if (!checkClearinghouseDefaults())
        {
            return ;
        }
         
        List<PatPlan> PatPlanList = PatPlans.refresh(PatCur.PatNum);
        List<InsSub> SubList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> InsPlanList = InsPlans.RefreshForSubList(SubList);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        List<Benefit> BenefitList = Benefits.Refresh(PatPlanList, SubList);
        List<Procedure> procsForPat = Procedures.refresh(PatCur.PatNum);
        if (PatPlanList.Count == 0)
        {
            MsgBox.show(this,"Patient does not have insurance.");
            return ;
        }
         
        int countSelected = 0;
        DataTable table = DataSetMain.Tables["account"];
        InsPlan plan;
        InsSub sub;
        if (gridAccount.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //autoselect procedures
                //loop through every line showing on screen
                if (StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
                {
                    continue;
                }
                 
                //ignore non-procedures
                if ((double)table.Rows[i]["chargesDouble"] == 0)
                {
                    continue;
                }
                 
                //ignore zero fee procedures, but user can explicitly select them
                Procedure proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[i]["ProcNum"].ToString()));
                ProcedureCode procCode = ProcedureCodes.getProcCodeFromDb(proc.CodeNum);
                if (procCode.IsCanadianLab)
                {
                    continue;
                }
                 
                int ordinal = PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, InsPlanList, SubList);
                if (ordinal == 0)
                {
                    //No primary dental plan. Must be a medical plan.  Use the first medical plan instead.
                    ordinal = 1;
                }
                 
                sub = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, ordinal), SubList);
                if (Procedures.NeedsSent(proc.ProcNum, sub.InsSubNum, ClaimProcList))
                {
                    if (CultureInfo.CurrentCulture.Name.EndsWith("CA") && countSelected == 7)
                    {
                        //Canadian. en-CA or fr-CA
                        MsgBox.show(this,"Only the first 7 procedures will be automatically selected.  You will need to create another claim for the remaining procedures.");
                        continue;
                    }
                     
                    //only send 7.
                    countSelected++;
                    gridAccount.setSelected(i,true);
                }
                 
            }
            if (gridAccount.getSelectedIndices().Length == 0)
            {
                //if still none selected
                MessageBox.Show(Lan.g(this,"Please select procedures first."));
                return ;
            }
             
        }
         
        boolean allAreProcedures = true;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                allAreProcedures = false;
            }
             
        }
        if (!allAreProcedures)
        {
            MsgBox.show(this,"You can only select procedures.");
            return ;
        }
         
        //At this point, all selected items are procedures.
        InsCanadaValidateProcs(procsForPat, table);
        String claimType = "P";
        if (PatPlanList.Count == 1 && PatPlans.GetOrdinal(PriSecMed.Medical, PatPlanList, InsPlanList, SubList) > 0)
        {
            //if there's exactly one medical plan
            claimType = "Med";
        }
         
        Claim ClaimCur = CreateClaim(claimType, PatPlanList, InsPlanList, ClaimProcList, procsForPat, SubList);
        ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        if (ClaimCur.ClaimNum == 0)
        {
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        ClaimCur.ClaimStatus = "W";
        ClaimCur.DateSent = DateTimeOD.getToday();
        ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, ClaimCur, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        FormClaimEdit FormCE = new FormClaimEdit(ClaimCur,PatCur,FamCur);
        FormCE.IsNew = true;
        //this causes it to delete the claim if cancelling.
        FormCE.ShowDialog();
        if (FormCE.DialogResult != DialogResult.OK)
        {
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        //will have already been deleted
        //if there exists a secondary plan
        if (PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, InsPlanList, SubList) > 0 && !CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //And not Canada (don't create secondary claim for Canada)
            sub = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, InsPlanList, SubList)), SubList);
            plan = InsPlans.GetPlan(sub.PlanNum, InsPlanList);
            ClaimCur = CreateClaim("S", PatPlanList, InsPlanList, ClaimProcList, procsForPat, SubList);
            if (ClaimCur.ClaimNum == 0)
            {
                moduleSelected(PatCur.PatNum);
                return ;
            }
             
            ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
            ClaimCur.ClaimStatus = "H";
            ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, ClaimCur, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        }
         
        moduleSelected(PatCur.PatNum);
    }

    /**
    * The procsForPat variable is all of the current procedures for the current patient. The tableAccount variable is the table from the DataSetMain object containing the information for the account grid.
    */
    private void insCanadaValidateProcs(List<Procedure> procsForPat, DataTable tableAccount) throws Exception {
        int labProcsUnselected = 0;
        List<int> selectedIndicies = new List<int>(gridAccount.getSelectedIndices());
        for (int i = 0;i < selectedIndicies.Count;i++)
        {
            Procedure proc = Procedures.GetProcFromList(procsForPat, PIn.Long(tableAccount.Rows[selectedIndicies[i]]["ProcNum"].ToString()));
            ProcedureCode procCode = ProcedureCodes.getProcCodeFromDb(proc.CodeNum);
            if (procCode.IsCanadianLab)
            {
                gridAccount.SetSelected(selectedIndicies[i], false);
                labProcsUnselected++;
            }
             
        }
        if (labProcsUnselected > 0)
        {
            MessageBox.Show(Lan.g(this,"Number of lab fee procedures automatically unselected") + ": " + labProcsUnselected.ToString());
        }
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA") && gridAccount.getSelectedIndices().Length > 7)
        {
            //Canadian. en-CA or fr-CA
            selectedIndicies = new List<int>(gridAccount.getSelectedIndices());
            selectedIndicies.Sort();
            for (int i = 0;i < selectedIndicies.Count;i++)
            {
                //Unselect all but the first 7 procedures with the smallest index numbers.
                gridAccount.SetSelected(selectedIndicies[i], (i < 7));
            }
            MsgBox.show(this,"Only the first 7 procedures will be selected.  You will need to create another claim for the remaining procedures.");
        }
         
    }

    /**
    * The only validation that's been done is just to make sure that only procedures are selected.  All validation on the procedures selected is done here.  Creates and saves claim initially, attaching all selected procedures.  But it does not refresh any data. Does not do a final update of the new claim.  Does not enter fee amounts.  claimType=P,S,Med,or Other
    */
    private Claim createClaim(String claimType, List<PatPlan> PatPlanList, List<InsPlan> planList, List<ClaimProc> ClaimProcList, List<Procedure> procsForPat, List<InsSub> subList) throws Exception {
        long claimFormNum = 0;
        InsPlan PlanCur = new InsPlan();
        InsSub SubCur = new InsSub();
        Relat relatOther = Relat.Self;
        System.String __dummyScrutVar2 = claimType;
        if (__dummyScrutVar2.equals("P"))
        {
            SubCur = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, planList, subList)), subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, planList);
        }
        else if (__dummyScrutVar2.equals("S"))
        {
            SubCur = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, planList, subList)), subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, planList);
        }
        else if (__dummyScrutVar2.equals("Med"))
        {
            //It's already been verified that a med plan exists
            SubCur = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Medical, PatPlanList, planList, subList)), subList);
            PlanCur = InsPlans.GetPlan(SubCur.PlanNum, planList);
        }
        else if (__dummyScrutVar2.equals("Other"))
        {
            FormClaimCreate FormCC = new FormClaimCreate(PatCur.PatNum);
            FormCC.ShowDialog();
            if (FormCC.DialogResult != DialogResult.OK)
            {
                return new Claim();
            }
             
            PlanCur = FormCC.SelectedPlan;
            SubCur = FormCC.SelectedSub;
            relatOther = FormCC.PatRelat;
        }
            
        DataTable table = DataSetMain.Tables["account"];
        Procedure proc;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
            if (Procedures.NoBillIns(proc, ClaimProcList, PlanCur.PlanNum))
            {
                MsgBox.show(this,"Not allowed to send procedures to insurance that are marked 'Do not bill to ins'.");
                return new Claim();
            }
             
        }
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
            if (Procedures.IsAlreadyAttachedToClaim(proc, ClaimProcList, SubCur.InsSubNum))
            {
                MsgBox.show(this,"Not allowed to send a procedure to the same insurance company twice.");
                return new Claim();
            }
             
        }
        proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[0]]["ProcNum"].ToString()));
        long clinicNum = proc.ClinicNum;
        PlaceOfService placeService = proc.PlaceService;
        for (int i = 1;i < gridAccount.getSelectedIndices().Length;i++)
        {
            //skips 0
            proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
            if (clinicNum != proc.ClinicNum)
            {
                MsgBox.show(this,"All procedures do not have the same clinic.");
                return new Claim();
            }
             
            if (proc.PlaceService != placeService)
            {
                MsgBox.show(this,"All procedures do not have the same place of service.");
                return new Claim();
            }
             
        }
        ClaimProc[] claimProcs = new ClaimProc[gridAccount.getSelectedIndices().Length];
        //1:1 with selectedIndices
        long procNum = new long();
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            //loop through selected procs
            //and try to find an estimate that can be used
            procNum = PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString());
            claimProcs[i] = Procedures.GetClaimProcEstimate(procNum, ClaimProcList, PlanCur, SubCur.InsSubNum);
        }
        for (int i = 0;i < claimProcs.Length;i++)
        {
            //loop through each claimProc
            //and create any missing estimates. This handles claims to 3rd and 4th ins co's.
            if (claimProcs[i] == null)
            {
                claimProcs[i] = new ClaimProc();
                proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
                //1:1
                ClaimProcs.CreateEst(claimProcs[i], proc, PlanCur, SubCur);
            }
             
        }
        Claim ClaimCur = new Claim();
        Claims.insert(ClaimCur);
        for (int i = 0;i < claimProcs.Length;i++)
        {
            //to retreive a key for new Claim.ClaimNum
            //now, all claimProcs have a valid value
            //for any CapComplete, need to make a copy so that original doesn't get attached.
            if (claimProcs[i].Status == ClaimProcStatus.CapComplete)
            {
                claimProcs[i].ClaimNum = ClaimCur.ClaimNum;
                claimProcs[i] = claimProcs[i].Copy();
                claimProcs[i].WriteOff = 0;
                claimProcs[i].CopayAmt = -1;
                claimProcs[i].CopayOverride = -1;
                //status will get changed down below
                ClaimProcs.Insert(claimProcs[i]);
            }
             
        }
        //this makes a duplicate in db with different claimProcNum
        ClaimCur.PatNum = PatCur.PatNum;
        ClaimCur.DateService = claimProcs[claimProcs.Length - 1].ProcDate;
        ClaimCur.ClinicNum = clinicNum;
        ClaimCur.PlaceService = proc.PlaceService;
        //datesent
        ClaimCur.ClaimStatus = "U";
        //datereceived
        InsSub sub;
        ClaimCur.PlanNum = PlanCur.PlanNum;
        ClaimCur.InsSubNum = SubCur.InsSubNum;
        System.String __dummyScrutVar3 = claimType;
        if (__dummyScrutVar3.equals("P"))
        {
            ClaimCur.PatRelat = PatPlans.GetRelat(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, planList, subList));
            ClaimCur.ClaimType = "P";
            ClaimCur.InsSubNum2 = PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, planList, subList));
            sub = InsSubs.GetSub(ClaimCur.InsSubNum2, subList);
            if (sub.PlanNum > 0 && InsPlans.refreshOne(sub.PlanNum).IsMedical)
            {
                ClaimCur.PlanNum2 = 0;
                //no sec ins
                ClaimCur.PatRelat2 = Relat.Self;
            }
            else
            {
                ClaimCur.PlanNum2 = sub.PlanNum;
                //might be 0 if no sec ins
                ClaimCur.PatRelat2 = PatPlans.GetRelat(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, planList, subList));
            } 
        }
        else if (__dummyScrutVar3.equals("S"))
        {
            ClaimCur.PatRelat = PatPlans.GetRelat(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, planList, subList));
            ClaimCur.ClaimType = "S";
            ClaimCur.InsSubNum2 = PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, planList, subList));
            sub = InsSubs.GetSub(ClaimCur.InsSubNum2, subList);
            ClaimCur.PlanNum2 = sub.PlanNum;
            ClaimCur.PatRelat2 = PatPlans.GetRelat(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, planList, subList));
        }
        else if (__dummyScrutVar3.equals("Med"))
        {
            ClaimCur.PatRelat = PatPlans.GetFromList(PatPlanList, SubCur.InsSubNum).Relationship;
            ClaimCur.ClaimType = "Other";
            if (PrefC.getBool(PrefName.ClaimMedTypeIsInstWhenInsPlanIsMedical))
            {
                ClaimCur.MedType = EnumClaimMedType.Institutional;
            }
            else
            {
                ClaimCur.MedType = EnumClaimMedType.Medical;
            } 
        }
        else if (__dummyScrutVar3.equals("Other"))
        {
            ClaimCur.PatRelat = relatOther;
            ClaimCur.ClaimType = "Other";
            //plannum2 is not automatically filled in.
            ClaimCur.ClaimForm = claimFormNum;
            if (PlanCur.IsMedical)
            {
                if (PrefC.getBool(PrefName.ClaimMedTypeIsInstWhenInsPlanIsMedical))
                {
                    ClaimCur.MedType = EnumClaimMedType.Institutional;
                }
                else
                {
                    ClaimCur.MedType = EnumClaimMedType.Medical;
                } 
            }
             
        }
            
        if (StringSupport.equals(PlanCur.PlanType, "c"))
        {
            //if capitation
            ClaimCur.ClaimType = "Cap";
        }
         
        ClaimCur.ProvTreat = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[0]]["ProcNum"].ToString())).ProvNum;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
            if (!Providers.getIsSec(proc.ProvNum))
            {
                //if not a hygienist
                ClaimCur.ProvTreat = proc.ProvNum;
            }
             
        }
        if (Providers.getIsSec(ClaimCur.ProvTreat))
        {
            ClaimCur.ProvTreat = PatCur.PriProv;
        }
         
        //OK if 0, because auto select first in list when open claim
        //claimfee calcs in ClaimEdit
        //inspayest ''
        //inspayamt
        //ClaimCur.DedApplied=0;//calcs in ClaimEdit.
        //preauthstring, etc, etc
        ClaimCur.IsProsthesis = "N";
        //int clinicInsBillingProv=0;
        //bool useClinic=false;
        //if(ClaimCur.ClinicNum>0){
        //	useClinic=true;
        //	clinicInsBillingProv=Clinics.GetClinic(ClaimCur.ClinicNum).InsBillingProv;
        //}
        ClaimCur.ProvBill = Providers.getBillingProvNum(ClaimCur.ProvTreat,ClaimCur.ClinicNum);
        //,useClinic,clinicInsBillingProv);//OK if zero, because it will get fixed in claim
        ClaimCur.EmployRelated = YN.No;
        ClaimCur.ClaimForm = PlanCur.ClaimFormNum;
        //attach procedures
        Procedure ProcCur;
        for (int i = 0;i < claimProcs.Length;i++)
        {
            //for(int i=0;i<tbAccount.SelectedIndices.Length;i++){
            ProcCur = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
            //1:1
            //ClaimProc ClaimProcCur=new ClaimProc();
            //ClaimProcCur.ProcNum=ProcCur.ProcNum;
            claimProcs[i].ClaimNum = ClaimCur.ClaimNum;
            //ClaimProcCur.PatNum=Patients.Cur.PatNum;
            //ClaimProcCur.ProvNum=ProcCur.ProvNum;
            //ClaimProcs.Cur.FeeBilled=;//handle in call to ClaimL.CalculateAndUpdate()
            //inspayest ''
            //dedapplied ''
            if (StringSupport.equals(PlanCur.PlanType, "c"))
                //if capitation
                claimProcs[i].Status = ClaimProcStatus.CapClaim;
            else
                claimProcs[i].Status = ClaimProcStatus.NotReceived; 
            //inspayamt=0
            //remarks
            //claimpaymentnum=0
            //ClaimProcCur.PlanNum=Claims.Cur.PlanNum;
            //ClaimProcCur.DateCP=ProcCur.ProcDate;
            //writeoff handled in ClaimL.CalculateAndUpdate()
            if (PlanCur.UseAltCode && (!StringSupport.equals(ProcedureCodes.getProcCode(ProcCur.CodeNum).AlternateCode1, "")))
            {
                claimProcs[i].CodeSent = ProcedureCodes.getProcCode(ProcCur.CodeNum).AlternateCode1;
            }
            else if (PlanCur.IsMedical && !StringSupport.equals(ProcCur.MedicalCode, ""))
            {
                claimProcs[i].CodeSent = ProcCur.MedicalCode;
            }
            else
            {
                claimProcs[i].CodeSent = ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode;
                if (claimProcs[i].CodeSent.Length > 5 && StringSupport.equals(claimProcs[i].CodeSent.Substring(0, 1), "D"))
                {
                    claimProcs[i].CodeSent = claimProcs[i].CodeSent.Substring(0, 5);
                }
                 
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (claimProcs[i].CodeSent.Length > 5)
                    {
                        //In Canadian electronic claims, codes can contain letters or numbers and cannot be longer than 5 characters.
                        claimProcs[i].CodeSent = claimProcs[i].CodeSent.Substring(0, 5);
                    }
                     
                }
                 
            }  
            claimProcs[i].LineNumber = (byte)(i + 1);
            ClaimProcs.Update(claimProcs[i]);
        }
        return ClaimCur;
    }

    //for claimProc
    //return null;
    private void menuInsPri_Click(Object sender, System.EventArgs e) throws Exception {
        if (!checkClearinghouseDefaults())
        {
            return ;
        }
         
        List<PatPlan> PatPlanList = PatPlans.refresh(PatCur.PatNum);
        List<InsSub> SubList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> InsPlanList = InsPlans.RefreshForSubList(SubList);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        List<Benefit> BenefitList = Benefits.Refresh(PatPlanList, SubList);
        List<Procedure> procsForPat = Procedures.refresh(PatCur.PatNum);
        if (PatPlanList.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"Patient does not have insurance."));
            return ;
        }
         
        if (PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, InsPlanList, SubList) == 0)
        {
            MsgBox.show(this,"The patient does not have any dental insurance plans.");
            return ;
        }
         
        if (gridAccount.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select procedures first."));
            return ;
        }
         
        DataTable table = DataSetMain.Tables["account"];
        boolean allAreProcedures = true;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                allAreProcedures = false;
            }
             
        }
        if (!allAreProcedures)
        {
            MessageBox.Show(Lan.g(this,"You can only select procedures."));
            return ;
        }
         
        //At this point, all selected items are procedures.
        InsCanadaValidateProcs(procsForPat, table);
        Claim ClaimCur = CreateClaim("P", PatPlanList, InsPlanList, ClaimProcList, procsForPat, SubList);
        if (ClaimCur.ClaimNum == 0)
        {
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        ClaimCur.ClaimStatus = "W";
        ClaimCur.DateSent = DateTime.Today;
        //still have not saved some changes to the claim at this point
        FormClaimEdit FormCE = new FormClaimEdit(ClaimCur,PatCur,FamCur);
        ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, ClaimCur, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        FormCE.IsNew = true;
        //this causes it to delete the claim if cancelling.
        FormCE.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void menuInsSec_Click(Object sender, System.EventArgs e) throws Exception {
        if (!checkClearinghouseDefaults())
        {
            return ;
        }
         
        List<PatPlan> PatPlanList = PatPlans.refresh(PatCur.PatNum);
        List<InsSub> SubList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> InsPlanList = InsPlans.RefreshForSubList(SubList);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        List<Benefit> BenefitList = Benefits.Refresh(PatPlanList, SubList);
        List<Procedure> procsForPat = Procedures.refresh(PatCur.PatNum);
        if (PatPlanList.Count < 2)
        {
            MessageBox.Show(Lan.g(this,"Patient does not have secondary insurance."));
            return ;
        }
         
        if (PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, InsPlanList, SubList) == 0)
        {
            MsgBox.show(this,"Patient does not have secondary insurance.");
            return ;
        }
         
        if (gridAccount.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select procedures first."));
            return ;
        }
         
        DataTable table = DataSetMain.Tables["account"];
        boolean allAreProcedures = true;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                allAreProcedures = false;
            }
             
        }
        if (!allAreProcedures)
        {
            MessageBox.Show(Lan.g(this,"You can only select procedures."));
            return ;
        }
         
        //At this point, all selected items are procedures.
        InsCanadaValidateProcs(procsForPat, table);
        Claim ClaimCur = CreateClaim("S", PatPlanList, InsPlanList, ClaimProcList, procsForPat, SubList);
        if (ClaimCur.ClaimNum == 0)
        {
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        ClaimCur.ClaimStatus = "W";
        ClaimCur.DateSent = DateTimeOD.getToday();
        ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, ClaimCur, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        FormClaimEdit FormCE = new FormClaimEdit(ClaimCur,PatCur,FamCur);
        FormCE.IsNew = true;
        //this causes it to delete the claim if cancelling.
        FormCE.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void menuInsMedical_Click(Object sender, System.EventArgs e) throws Exception {
        if (!checkClearinghouseDefaults())
        {
            return ;
        }
         
        List<PatPlan> PatPlanList = PatPlans.refresh(PatCur.PatNum);
        List<InsSub> SubList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> InsPlanList = InsPlans.RefreshForSubList(SubList);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        List<Benefit> BenefitList = Benefits.Refresh(PatPlanList, SubList);
        List<Procedure> procsForPat = Procedures.refresh(PatCur.PatNum);
        long medSubNum = 0;
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            InsSub sub = InsSubs.GetSub(PatPlanList[i].InsSubNum, SubList);
            if (InsPlans.GetPlan(sub.PlanNum, InsPlanList).IsMedical)
            {
                medSubNum = sub.InsSubNum;
                break;
            }
             
        }
        if (medSubNum == 0)
        {
            MsgBox.show(this,"Patient does not have medical insurance.");
            return ;
        }
         
        DataTable table = DataSetMain.Tables["account"];
        Procedure proc;
        if (gridAccount.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //autoselect procedures
                //loop through every line showing on screen
                if (StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
                {
                    continue;
                }
                 
                //ignore non-procedures
                proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[i]["ProcNum"].ToString()));
                if (proc.ProcFee == 0)
                {
                    continue;
                }
                 
                //ignore zero fee procedures, but user can explicitly select them
                if (StringSupport.equals(proc.MedicalCode, ""))
                {
                    continue;
                }
                 
                //ignore non-medical procedures
                if (Procedures.NeedsSent(proc.ProcNum, medSubNum, ClaimProcList))
                {
                    gridAccount.setSelected(i,true);
                }
                 
            }
            if (gridAccount.getSelectedIndices().Length == 0)
            {
                //if still none selected
                MsgBox.show(this,"Please select procedures first.");
                return ;
            }
             
        }
         
        boolean allAreProcedures = true;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                allAreProcedures = false;
            }
             
        }
        if (!allAreProcedures)
        {
            MsgBox.show(this,"You can only select procedures.");
            return ;
        }
         
        Claim ClaimCur = CreateClaim("Med", PatPlanList, InsPlanList, ClaimProcList, procsForPat, SubList);
        if (ClaimCur.ClaimNum == 0)
        {
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        ClaimCur.ClaimStatus = "W";
        ClaimCur.DateSent = DateTimeOD.getToday();
        ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, ClaimCur, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        //still have not saved some changes to the claim at this point
        FormClaimEdit FormCE = new FormClaimEdit(ClaimCur,PatCur,FamCur);
        FormCE.IsNew = true;
        //this causes it to delete the claim if cancelling.
        FormCE.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void menuInsOther_Click(Object sender, System.EventArgs e) throws Exception {
        if (!checkClearinghouseDefaults())
        {
            return ;
        }
         
        List<PatPlan> PatPlanList = PatPlans.refresh(PatCur.PatNum);
        List<InsSub> SubList = InsSubs.refreshForFam(FamCur);
        List<InsPlan> InsPlanList = InsPlans.RefreshForSubList(SubList);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        List<Benefit> BenefitList = Benefits.Refresh(PatPlanList, SubList);
        List<Procedure> procsForPat = Procedures.refresh(PatCur.PatNum);
        if (gridAccount.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select procedures first."));
            return ;
        }
         
        DataTable table = DataSetMain.Tables["account"];
        boolean allAreProcedures = true;
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                allAreProcedures = false;
            }
             
        }
        if (!allAreProcedures)
        {
            MessageBox.Show(Lan.g(this,"You can only select procedures."));
            return ;
        }
         
        Claim ClaimCur = CreateClaim("Other", PatPlanList, InsPlanList, ClaimProcList, procsForPat, SubList);
        if (ClaimCur.ClaimNum == 0)
        {
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, ClaimCur, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        //still have not saved some changes to the claim at this point
        FormClaimEdit FormCE = new FormClaimEdit(ClaimCur,PatCur,FamCur);
        FormCE.IsNew = true;
        //this causes it to delete the claim if cancelling.
        FormCE.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void toolBarButPayPlan_Click() throws Exception {
        PayPlan payPlan = new PayPlan();
        payPlan.PatNum = PatCur.PatNum;
        payPlan.Guarantor = PatCur.Guarantor;
        payPlan.PayPlanDate = DateTimeOD.getToday();
        payPlan.CompletedAmt = PatCur.EstBalance;
        PayPlans.insert(payPlan);
        FormPayPlan FormPP = new FormPayPlan(PatCur,payPlan);
        FormPP.TotalAmt = PatCur.EstBalance;
        FormPP.IsNew = true;
        FormPP.ShowDialog();
        if (FormPP.GotoPatNum != 0)
        {
            moduleSelected(FormPP.GotoPatNum);
        }
        else
        {
            //switches to other patient.
            moduleSelected(PatCur.PatNum);
        } 
    }

    private void toolBarButInstallPlan_Click() throws Exception {
        if (InstallmentPlans.getOneForFam(PatCur.Guarantor) != null)
        {
            MsgBox.show(this,"Family already has an installment plan.");
            return ;
        }
         
        InstallmentPlan installPlan = new InstallmentPlan();
        installPlan.PatNum = PatCur.Guarantor;
        installPlan.DateAgreement = DateTime.Today;
        installPlan.DateFirstPayment = DateTime.Today;
        //InstallmentPlans.Insert(installPlan);
        FormInstallmentPlanEdit FormIPE = new FormInstallmentPlanEdit();
        FormIPE.InstallmentPlanCur = installPlan;
        FormIPE.IsNew = true;
        FormIPE.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void toolBarButRepeatCharge_Click() throws Exception {
        RepeatCharge repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.DateStart = DateTime.Today;
        FormRepeatChargeEdit FormR = new FormRepeatChargeEdit(repeat);
        FormR.IsNew = true;
        FormR.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemRepeatStand_Click(Object sender, System.EventArgs e) throws Exception {
        if (!ProcedureCodeC.getHList().ContainsKey("001"))
        {
            return ;
        }
         
        RepeatCharge repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.ProcCode = "001";
        repeat.ChargeAmt = 149;
        repeat.DateStart = DateTimeOD.getToday();
        repeat.DateStop = DateTimeOD.getToday().AddMonths(11);
        repeat.IsEnabled = true;
        RepeatCharges.insert(repeat);
        repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.ProcCode = "001";
        repeat.ChargeAmt = 99;
        repeat.DateStart = DateTimeOD.getToday().AddYears(1);
        repeat.IsEnabled = true;
        RepeatCharges.insert(repeat);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemRepeatEmail_Click(Object sender, System.EventArgs e) throws Exception {
        if (!ProcedureCodeC.getHList().ContainsKey("008"))
        {
            return ;
        }
         
        RepeatCharge repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.ProcCode = "008";
        repeat.ChargeAmt = 89;
        repeat.DateStart = DateTimeOD.getToday();
        repeat.IsEnabled = true;
        RepeatCharges.insert(repeat);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemRepeatMobile_Click(Object sender, EventArgs e) throws Exception {
        if (!ProcedureCodeC.getHList().ContainsKey("027"))
        {
            return ;
        }
         
        RepeatCharge repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.ProcCode = "027";
        repeat.ChargeAmt = 10;
        repeat.DateStart = DateTimeOD.getToday();
        repeat.IsEnabled = true;
        RepeatCharges.insert(repeat);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemRepeatCanada_Click(Object sender, EventArgs e) throws Exception {
        if (!ProcedureCodeC.getHList().ContainsKey("001"))
        {
            return ;
        }
         
        RepeatCharge repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.ProcCode = "001";
        repeat.ChargeAmt = 129;
        repeat.DateStart = DateTimeOD.getToday();
        repeat.DateStop = DateTimeOD.getToday().AddMonths(11);
        repeat.IsEnabled = true;
        RepeatCharges.insert(repeat);
        repeat = new RepeatCharge();
        repeat.PatNum = PatCur.PatNum;
        repeat.ProcCode = "001";
        repeat.ChargeAmt = 99;
        repeat.DateStart = DateTimeOD.getToday().AddYears(1);
        repeat.IsEnabled = true;
        RepeatCharges.insert(repeat);
        moduleSelected(PatCur.PatNum);
    }

    private void toolBarButStatement_Click() throws Exception {
        Statement stmt = new Statement();
        stmt.PatNum = PatCur.Guarantor;
        stmt.DateSent = DateTimeOD.getToday();
        stmt.IsSent = true;
        stmt.Mode_ = StatementMode.InPerson;
        stmt.HidePayment = false;
        stmt.SinglePatient = false;
        stmt.Intermingled = false;
        stmt.DateRangeFrom = DateTime.MinValue;
        if (PrefC.getBool(PrefName.IntermingleFamilyDefault))
        {
            stmt.Intermingled = true;
        }
         
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            stmt.DateRangeFrom = PIn.Date(DateTime.Today.AddDays(-45).ToShortDateString());
            stmt.DateRangeTo = PIn.Date(DateTime.Today.ToShortDateString());
        }
        else
        {
            if (StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), ""))
            {
                if (!StringSupport.equals(textDateStart.Text, ""))
                {
                    stmt.DateRangeFrom = PIn.Date(textDateStart.Text);
                }
                 
            }
             
        } 
        stmt.DateRangeTo = DateTimeOD.getToday();
        //This is needed for payment plan accuracy.//new DateTime(2200,1,1);
        if (StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            if (!StringSupport.equals(textDateEnd.Text, ""))
            {
                stmt.DateRangeTo = PIn.Date(textDateEnd.Text);
            }
             
        }
         
        stmt.Note = "";
        stmt.NoteBold = "";
        printStatement(stmt);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemStatementWalkout_Click(Object sender, System.EventArgs e) throws Exception {
        Statement stmt = new Statement();
        stmt.PatNum = PatCur.PatNum;
        stmt.DateSent = DateTimeOD.getToday();
        stmt.IsSent = true;
        stmt.Mode_ = StatementMode.InPerson;
        stmt.HidePayment = true;
        stmt.SinglePatient = true;
        stmt.Intermingled = false;
        stmt.IsReceipt = false;
        if (PrefC.getBool(PrefName.IntermingleFamilyDefault))
        {
            stmt.Intermingled = true;
            stmt.SinglePatient = false;
        }
         
        stmt.DateRangeFrom = DateTimeOD.getToday();
        stmt.DateRangeTo = DateTimeOD.getToday();
        stmt.Note = "";
        stmt.NoteBold = "";
        printStatement(stmt);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemStatementEmail_Click(Object sender, EventArgs e) throws Exception {
        Statement stmt = new Statement();
        stmt.PatNum = PatCur.Guarantor;
        stmt.DateSent = DateTimeOD.getToday();
        stmt.IsSent = true;
        stmt.Mode_ = StatementMode.Email;
        stmt.HidePayment = false;
        stmt.SinglePatient = false;
        stmt.Intermingled = false;
        stmt.IsReceipt = false;
        if (PrefC.getBool(PrefName.IntermingleFamilyDefault))
        {
            stmt.Intermingled = true;
        }
         
        stmt.DateRangeFrom = DateTime.MinValue;
        if (StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), ""))
        {
            if (!StringSupport.equals(textDateStart.Text, ""))
            {
                stmt.DateRangeFrom = PIn.Date(textDateStart.Text);
            }
             
        }
         
        stmt.DateRangeTo = new DateTime(2200, 1, 1);
        if (StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            if (!StringSupport.equals(textDateEnd.Text, ""))
            {
                stmt.DateRangeTo = PIn.Date(textDateEnd.Text);
            }
             
        }
         
        stmt.Note = "";
        stmt.NoteBold = "";
        //It's pointless to give the user the window to select statement options, because they could just as easily have hit the More Options dropdown, then Email from there.
        printStatement(stmt);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemReceipt_Click(Object sender, EventArgs e) throws Exception {
        Statement stmt = new Statement();
        stmt.PatNum = PatCur.PatNum;
        stmt.DateSent = DateTimeOD.getToday();
        stmt.IsSent = true;
        stmt.Mode_ = StatementMode.InPerson;
        stmt.HidePayment = true;
        stmt.SinglePatient = true;
        stmt.Intermingled = false;
        stmt.IsReceipt = true;
        if (PrefC.getBool(PrefName.IntermingleFamilyDefault))
        {
            stmt.Intermingled = true;
            stmt.SinglePatient = false;
        }
         
        stmt.DateRangeFrom = DateTimeOD.getToday();
        stmt.DateRangeTo = DateTimeOD.getToday();
        stmt.Note = "";
        stmt.NoteBold = "";
        printStatement(stmt);
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemInvoice_Click(Object sender, EventArgs e) throws Exception {
        DataTable table = DataSetMain.Tables["account"];
        if (gridAccount.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //autoselect procedures and adjustments
                //loop through every line showing on screen
                if (StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0") && StringSupport.equals(table.Rows[i]["AdjNum"].ToString(), "0"))
                {
                    continue;
                }
                 
                //ignore items that aren't procs or adjustments
                if (PIn.Date(table.Rows[i]["date"].ToString()) != DateTime.Today)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
                {
                    //if selected item is a procedure
                    Procedure proc = Procedures.GetOneProc(PIn.Long(table.Rows[i]["ProcNum"].ToString()), false);
                    if (proc.StatementNum != 0)
                    {
                        continue;
                    }
                     
                    //already attached so don't autoselect
                    if (proc.PatNum != PatCur.PatNum)
                    {
                        continue;
                    }
                     
                }
                else
                {
                    //item guaranteed to be a proc or adjustment, so must be adjustment
                    Adjustment adj = Adjustments.GetOne(PIn.Long(table.Rows[i]["AdjNum"].ToString()));
                    if (adj.StatementNum != 0)
                    {
                        continue;
                    }
                     
                    //already attached so don't autoselect
                    if (adj.PatNum != PatCur.PatNum)
                    {
                        continue;
                    }
                     
                } 
                gridAccount.setSelected(i,true);
            }
            if (gridAccount.getSelectedIndices().Length == 0)
            {
                //if still none selected
                MsgBox.show(this,"Please select procedures or adjustments first.");
                return ;
            }
             
        }
         
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0") && StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["AdjNum"].ToString(), "0"))
            {
                //the selected item is neither a procedure nor an adjustment
                MsgBox.show(this,"You can only select procedures or adjustments.");
                gridAccount.setSelected(false);
                return ;
            }
             
            if (!StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                //the selected item is a proc
                Procedure proc = Procedures.GetOneProc(PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()), false);
                if (proc.PatNum != PatCur.PatNum)
                {
                    MsgBox.show(this,"You can only select procedures or adjustments for a single patient on an invoice.");
                    gridAccount.setSelected(false);
                    return ;
                }
                 
                if (proc.StatementNum != 0)
                {
                    MsgBox.show(this,"Selected procedure(s) are already attached to an invoice.");
                    gridAccount.setSelected(false);
                    return ;
                }
                 
            }
            else
            {
                //the selected item must be an adjustment
                Adjustment adj = Adjustments.GetOne(PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["AdjNum"].ToString()));
                if (adj.PatNum != PatCur.PatNum)
                {
                    MsgBox.show(this,"You can only select procedures or adjustments for a single patient on an invoice.");
                    gridAccount.setSelected(false);
                    return ;
                }
                 
                if (adj.StatementNum != 0)
                {
                    MsgBox.show(this,"Selected adjustment(s) are already attached to an invoice.");
                    gridAccount.setSelected(false);
                    return ;
                }
                 
            } 
        }
        //At this point, all selected items are procedures or adjustments, and are not already attached, and are for a single patient.
        Statement stmt = new Statement();
        stmt.PatNum = PatCur.PatNum;
        stmt.DateSent = DateTimeOD.getToday();
        stmt.IsSent = false;
        stmt.Mode_ = StatementMode.InPerson;
        stmt.HidePayment = true;
        stmt.SinglePatient = true;
        stmt.Intermingled = false;
        stmt.IsReceipt = false;
        stmt.IsInvoice = true;
        stmt.DateRangeFrom = DateTime.MinValue;
        stmt.DateRangeTo = DateTimeOD.getToday();
        stmt.Note = PrefC.getString(PrefName.BillingDefaultsInvoiceNote);
        stmt.NoteBold = "";
        Statements.insert(stmt);
        stmt.setIsNew(true);
        List<Procedure> procsForPat = Procedures.refresh(PatCur.PatNum);
        for (int i = 0;i < gridAccount.getSelectedIndices().Length;i++)
        {
            if (!StringSupport.equals(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString(), "0"))
            {
                //if selected item is a procedure
                Procedure proc = Procedures.GetProcFromList(procsForPat, PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["ProcNum"].ToString()));
                Procedure oldProc = proc.copy();
                proc.StatementNum = stmt.StatementNum;
                Procedures.update(proc,oldProc);
            }
            else
            {
                //every selected item guaranteed to be a proc or adjustment, so must be adjustment
                Adjustment adj = Adjustments.GetOne(PIn.Long(table.Rows[gridAccount.getSelectedIndices()[i]]["AdjNum"].ToString()));
                adj.StatementNum = stmt.StatementNum;
                Adjustments.update(adj);
            } 
        }
        //All printing and emailing will be done from within the form:
        FormStatementOptions FormSO = new FormStatementOptions();
        FormSO.StmtCur = stmt;
        FormSO.ShowDialog();
        if (FormSO.DialogResult != DialogResult.OK)
        {
            Procedures.detachFromInvoice(stmt.StatementNum);
            Adjustments.detachFromInvoice(stmt.StatementNum);
            Statements.delete(stmt.StatementNum);
        }
         
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemStatementMore_Click(Object sender, System.EventArgs e) throws Exception {
        Statement stmt = new Statement();
        stmt.PatNum = PatCur.PatNum;
        stmt.DateSent = DateTime.Today;
        stmt.IsSent = false;
        stmt.Mode_ = StatementMode.InPerson;
        stmt.HidePayment = false;
        stmt.SinglePatient = false;
        stmt.Intermingled = false;
        stmt.IsReceipt = false;
        if (PrefC.getBool(PrefName.IntermingleFamilyDefault))
        {
            stmt.Intermingled = true;
        }
        else
        {
            stmt.Intermingled = false;
        } 
        stmt.DateRangeFrom = DateTime.MinValue;
        stmt.DateRangeFrom = DateTime.MinValue;
        if (StringSupport.equals(textDateStart.errorProvider1.GetError(textDateStart), ""))
        {
            if (!StringSupport.equals(textDateStart.Text, ""))
            {
                stmt.DateRangeFrom = PIn.Date(textDateStart.Text);
            }
             
        }
         
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            stmt.DateRangeFrom = DateTime.Today.AddDays(-90);
        }
         
        stmt.DateRangeTo = DateTime.Today;
        //Needed for payplan accuracy.//new DateTime(2200,1,1);
        if (StringSupport.equals(textDateEnd.errorProvider1.GetError(textDateEnd), ""))
        {
            if (!StringSupport.equals(textDateEnd.Text, ""))
            {
                stmt.DateRangeTo = PIn.Date(textDateEnd.Text);
            }
             
        }
         
        stmt.Note = "";
        stmt.NoteBold = "";
        //All printing and emailing will be done from within the form:
        FormStatementOptions FormSO = new FormStatementOptions();
        stmt.setIsNew(true);
        FormSO.StmtCur = stmt;
        FormSO.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    /**
    * Saves the statement.  Attaches a pdf to it by creating a doc object.  Prints it or emails it.
    */
    private void printStatement(Statement stmt) throws Exception {
        Cursor = Cursors.WaitCursor;
        Statements.insert(stmt);
        FormRpStatement FormST = new FormRpStatement();
        DataSet dataSet = AccountModules.getStatementDataSet(stmt);
        FormST.createStatementPdf(stmt,PatCur,FamCur,dataSet);
        //if(ImageStore.UpdatePatient == null){
        //	ImageStore.UpdatePatient = new FileStore.UpdatePatientDelegate(Patients.Update);
        //}
        Patient guar = Patients.getPat(stmt.PatNum);
        String guarFolder = ImageStore.getPatientFolder(guar,ImageStore.getPreferredAtoZpath());
        //OpenDental.Imaging.ImageStoreBase imageStore = OpenDental.Imaging.ImageStore.GetImageStore(guar);
        if (stmt.Mode_ == StatementMode.Email)
        {
            String attachPath = EmailMessages.getEmailAttachPath();
            Random rnd = new Random();
            String fileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + ".pdf";
            String filePathAndName = CodeBase.ODFileUtils.combinePaths(attachPath,fileName);
            File.Copy(ImageStore.getFilePath(Documents.getByNum(stmt.DocNum),guarFolder), filePathAndName);
            //Process.Start(filePathAndName);
            EmailMessage message = Statements.getEmailMessageForStatement(stmt,guar);
            EmailAttach attach = new EmailAttach();
            attach.DisplayedFileName = "Statement.pdf";
            attach.ActualFileName = fileName;
            message.Attachments.Add(attach);
            FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
            FormE.IsNew = true;
            FormE.ShowDialog();
            //If user clicked delete or cancel, delete pdf and statement
            if (FormE.DialogResult == DialogResult.Cancel)
            {
                Patient pat;
                String patFolder = new String();
                if (stmt.DocNum != 0)
                {
                    //delete the pdf
                    pat = Patients.getPat(stmt.PatNum);
                    patFolder = ImageStore.getPatientFolder(pat,ImageStore.getPreferredAtoZpath());
                    List<OpenDentBusiness.Document> listdocs = new List<OpenDentBusiness.Document>();
                    listdocs.Add(Documents.getByNum(stmt.DocNum));
                    try
                    {
                        ImageStore.DeleteDocuments(listdocs, patFolder);
                    }
                    catch (Exception __dummyCatchVar0)
                    {
                    }
                
                }
                 
                //Image could not be deleted, in use.
                //This should never get hit because the file was created by this user within this method.
                //If the doc cannot be deleted, then we will not stop them, they will have to manually delete it from the images module.
                //delete statement
                Procedures.detachFromInvoice(stmt.StatementNum);
                Adjustments.detachFromInvoice(stmt.StatementNum);
                Statements.deleteObject(stmt);
            }
             
        }
        else
        {
            //not email
            FormST.printStatement(stmt,false,dataSet,FamCur,PatCur);
        } 
        Cursor = Cursors.Default;
    }

    private void textUrgFinNote_TextChanged(Object sender, System.EventArgs e) throws Exception {
        UrgFinNoteChanged = true;
    }

    private void textFinNotes_TextChanged(Object sender, System.EventArgs e) throws Exception {
        FinNoteChanged = true;
    }

    //private void textCC_TextChanged(object sender,EventArgs e) {
    //  CCChanged=true;
    //  if(Regex.IsMatch(textCC.Text,@"^\d{4}$")
    //    || Regex.IsMatch(textCC.Text,@"^\d{4}-\d{4}$")
    //    || Regex.IsMatch(textCC.Text,@"^\d{4}-\d{4}-\d{4}$"))
    //  {
    //    textCC.Text=textCC.Text+"-";
    //    textCC.Select(textCC.Text.Length,0);
    //  }
    //}
    //private void textCCexp_TextChanged(object sender,EventArgs e) {
    //  CCChanged=true;
    //}
    private void textUrgFinNote_Leave(Object sender, System.EventArgs e) throws Exception {
        //need to skip this if selecting another module. Handled in ModuleUnselected due to click event
        if (FamCur == null)
            return ;
         
        if (UrgFinNoteChanged)
        {
            Patient PatOld = FamCur.ListPats[0].Copy();
            FamCur.ListPats[0].FamFinUrgNote = textUrgFinNote.Text;
            Patients.Update(FamCur.ListPats[0], PatOld);
            UrgFinNoteChanged = false;
        }
         
        moduleSelected(PatCur.PatNum);
    }

    private void textFinNotes_Leave(Object sender, System.EventArgs e) throws Exception {
        if (FamCur == null)
            return ;
         
        if (FinNoteChanged)
        {
            PatientNoteCur.FamFinancial = textFinNotes.Text;
            PatientNotes.update(PatientNoteCur,PatCur.Guarantor);
            FinNoteChanged = false;
            moduleSelected(PatCur.PatNum);
        }
         
    }

    //private void textCC_Leave(object sender,EventArgs e) {
    //  if(FamCur==null)
    //    return;
    //  if(CCChanged) {
    //    CCSave();
    //    CCChanged=false;
    //    ModuleSelected(PatCur.PatNum);
    //  }
    //}
    //private void textCCexp_Leave(object sender,EventArgs e) {
    //  if(FamCur==null)
    //    return;
    //  if(CCChanged){
    //    CCSave();
    //    CCChanged=false;
    //    ModuleSelected(PatCur.PatNum);
    //  }
    //}
    //private void CCSave(){
    //  string cc=textCC.Text;
    //  if(Regex.IsMatch(cc,@"^\d{4}-\d{4}-\d{4}-\d{4}$")){
    //    PatientNoteCur.CCNumber=cc.Substring(0,4)+cc.Substring(5,4)+cc.Substring(10,4)+cc.Substring(15,4);
    //  }
    //  else{
    //    PatientNoteCur.CCNumber=cc;
    //  }
    //  string exp=textCCexp.Text;
    //  if(Regex.IsMatch(exp,@"^\d\d[/\- ]\d\d$")){//08/07 or 08-07 or 08 07
    //    PatientNoteCur.CCExpiration=new DateTime(Convert.ToInt32("20"+exp.Substring(3,2)),Convert.ToInt32(exp.Substring(0,2)),1);
    //  }
    //  else if(Regex.IsMatch(exp,@"^\d{4}$")){//0807
    //    PatientNoteCur.CCExpiration=new DateTime(Convert.ToInt32("20"+exp.Substring(2,2)),Convert.ToInt32(exp.Substring(0,2)),1);
    //  }
    //  else if(exp=="") {
    //    PatientNoteCur.CCExpiration=new DateTime();//Allow the experation date to be deleted.
    //  }
    //  else {
    //    MsgBox.Show(this,"Expiration format invalid.");
    //  }
    //  PatientNotes.Update(PatientNoteCur,PatCur.Guarantor);
    //}
    private void butToday_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.ToShortDateString();
        textDateEnd.Text = DateTime.Today.ToShortDateString();
        moduleSelected(PatCur.PatNum);
    }

    private void but45days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-45).ToShortDateString();
        textDateEnd.Text = "";
        moduleSelected(PatCur.PatNum);
    }

    private void but90days_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = DateTime.Today.AddDays(-90).ToShortDateString();
        textDateEnd.Text = "";
        moduleSelected(PatCur.PatNum);
    }

    private void butDatesAll_Click(Object sender, EventArgs e) throws Exception {
        textDateStart.Text = "";
        textDateEnd.Text = "";
        moduleSelected(PatCur.PatNum);
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        moduleSelected(PatCur.PatNum);
    }

    private void checkShowDetail_Click(Object sender, EventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        moduleSelected(PatCur.PatNum);
    }

    //private void checkShowNotes_Click(object sender,EventArgs e) {
    //checkShowNotes.Tag="JustClicked";
    //RefreshModuleScreen();
    //checkShowNotes.Tag = "";
    //	ModuleSelected(PatCur.PatNum);
    //}
    private void panelSplitter_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        MouseIsDownOnSplitter = true;
        SplitterOriginalY = panelSplitter.Top;
        OriginalMousePos = panelSplitter.Top + e.Y;
    }

    private void panelSplitter_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!MouseIsDownOnSplitter)
            return ;
         
        int splitterNewLoc = SplitterOriginalY + (panelSplitter.Top + e.Y) - OriginalMousePos;
        if (splitterNewLoc < gridAcctPat.Bottom)
            splitterNewLoc = gridAcctPat.Bottom;
         
        //keeps it from going too high
        if (splitterNewLoc > Height)
            splitterNewLoc = Height - panelSplitter.Height;
         
        //keeps it from going off the bottom edge
        panelSplitter.Top = splitterNewLoc;
        layoutPanels();
    }

    private void panelSplitter_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        MouseIsDownOnSplitter = false;
    }

    //tbAccount.LayoutTables();
    private void butComm_Click(Object sender, System.EventArgs e) throws Exception {
        FormPat form = new FormPat();
        form.PatNum = PatCur.PatNum;
        form.FormDateTime = DateTime.Now;
        FormFormPatEdit FormP = new FormFormPatEdit();
        FormP.FormPatCur = form;
        FormP.IsNew = true;
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.OK)
        {
            moduleSelected(PatCur.PatNum);
        }
         
    }

    /*private void butTask_Click(object sender, System.EventArgs e) {
    			//FormTaskListSelect FormT=new FormTaskListSelect(TaskObjectType.Patient,PatCur.PatNum);
    			//FormT.ShowDialog();
    		}*/
    private void butCreditCard_Click(Object sender, EventArgs e) throws Exception {
        FormCreditCardManage FormCCM = new FormCreditCardManage(PatCur);
        FormCCM.ShowDialog();
    }

    private void butTrojan_Click(Object sender, EventArgs e) throws Exception {
        FormTrojanCollect FormT = new FormTrojanCollect();
        FormT.PatNum = PatCur.PatNum;
        FormT.ShowDialog();
    }

    private void gridComm_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //TODO: transition this to checking the Tag object.
        int row = e.getRow();
        if (!this.checkShowFamilyComm.Checked)
        {
            //if only showing entries for one patient instead of intermingled family entries
            int i = new int();
            for (row = 0, i = 0;row < DataSetMain.Tables["Commlog"].Rows.Count;row++)
            {
                //Matching FName is not perfect because children can have the same names as parents.
                //But it does currently match the logic for display, so it will at least select the right row when double clicked.
                if (StringSupport.equals(DataSetMain.Tables["Commlog"].Rows[row]["patName"].ToString(), PatCur.FName) || StringSupport.equals(DataSetMain.Tables["Commlog"].Rows[row]["patName"].ToString(), ""))
                {
                    if (i == e.getRow())
                    {
                        break;
                    }
                     
                    i++;
                }
                 
            }
        }
         
        if (!StringSupport.equals(DataSetMain.Tables["Commlog"].Rows[row]["CommlogNum"].ToString(), "0"))
        {
            Commlog CommlogCur = Commlogs.GetOne(PIn.Long(DataSetMain.Tables["Commlog"].Rows[row]["CommlogNum"].ToString()));
            FormCommItem FormCI = new FormCommItem(CommlogCur);
            FormCI.ShowDialog();
            if (FormCI.DialogResult == DialogResult.OK)
            {
                moduleSelected(PatCur.PatNum);
            }
             
        }
        else if (!StringSupport.equals(DataSetMain.Tables["Commlog"].Rows[row]["EmailMessageNum"].ToString(), "0"))
        {
            EmailMessage email = EmailMessages.GetOne(PIn.Long(DataSetMain.Tables["Commlog"].Rows[row]["EmailMessageNum"].ToString()));
            FormEmailMessageEdit FormE = new FormEmailMessageEdit(email);
            FormE.ShowDialog();
            if (FormE.DialogResult == DialogResult.OK)
            {
                moduleSelected(PatCur.PatNum);
            }
             
        }
        else if (!StringSupport.equals(DataSetMain.Tables["Commlog"].Rows[row]["FormPatNum"].ToString(), "0"))
        {
            FormPat form = FormPats.GetOne(PIn.Long(DataSetMain.Tables["Commlog"].Rows[row]["FormPatNum"].ToString()));
            FormFormPatEdit FormP = new FormFormPatEdit();
            FormP.FormPatCur = form;
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.OK)
            {
                moduleSelected(PatCur.PatNum);
            }
             
        }
        else if (!StringSupport.equals(DataSetMain.Tables["Commlog"].Rows[row]["SheetNum"].ToString(), "0"))
        {
            Sheet sheet = Sheets.GetSheet(PIn.Long(DataSetMain.Tables["Commlog"].Rows[row]["SheetNum"].ToString()));
            FormSheetFillEdit FormSFE = new FormSheetFillEdit(sheet);
            FormSFE.ShowDialog();
            if (FormSFE.DialogResult == DialogResult.OK)
            {
                moduleSelected(PatCur.PatNum);
            }
             
        }
            
    }

    private void parent_MouseWheel(Object sender, MouseEventArgs e) throws Exception {
        if (Visible)
        {
            this.OnMouseWheel(e);
        }
         
    }

    private void gridRepeat_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormRepeatChargeEdit FormR = new FormRepeatChargeEdit(RepeatChargeList[e.getRow()]);
        FormR.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    /**
    * The supplied procedure row must include these columns: ProcDate,ProcStatus,ProcCode,Surf,ToothNum, and ToothRange, all in raw database format.
    */
    private boolean shouldDisplayProc(DataRow row) throws Exception {
        switch((ProcStat)PIn.Long(row["ProcStatus"].ToString()))
        {
            case TP: 
                if (checkShowTP.Checked)
                {
                    return true;
                }
                 
                break;
            case C: 
                if (checkShowC.Checked)
                {
                    return true;
                }
                 
                break;
            case EC: 
                if (checkShowE.Checked)
                {
                    return true;
                }
                 
                break;
            case EO: 
                if (checkShowE.Checked)
                {
                    return true;
                }
                 
                break;
            case R: 
                if (checkShowR.Checked)
                {
                    return true;
                }
                 
                break;
            case D: 
                if (checkAudit.Checked)
                {
                    return true;
                }
                 
                break;
        
        }
        return false;
    }

    private void fillProgNotes() throws Exception {
        ArrayList selectedTeeth = new ArrayList();
        for (int i = 0;i < 32;i++)
        {
            //integers 1-32
            selectedTeeth.Add(i);
        }
        gridProg.beginUpdate();
        gridProg.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProg","Date"),67);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Th"),27);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Surf"),40);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Dx"),28);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Description"),218);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Stat"),25);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Prov"),42);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Amount"), 48, HorizontalAlignment.Right);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","ADA Code"), 62, HorizontalAlignment.Center);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","User"), 62, HorizontalAlignment.Center);
        gridProg.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProg","Signed"), 55, HorizontalAlignment.Center);
        gridProg.getColumns().add(col);
        gridProg.setNoteSpanStart(2);
        gridProg.setNoteSpanStop(7);
        gridProg.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        //Type type;
        if (DataSetMain == null)
        {
            gridProg.endUpdate();
            return ;
        }
         
        DataTable table = DataSetMain.Tables["ProgNotes"];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //ProcList = new List<DataRow>();
            if (!StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
            {
                //if this is a procedure
                if (ShouldDisplayProc(table.Rows[i]))
                {
                }
                else
                {
                    continue;
                } 
            }
            else //ProcList.Add(table.Rows[i]);//show it in the graphical tooth chart
            //and add it to the grid below.
            if (!StringSupport.equals(table.Rows[i]["CommlogNum"].ToString(), "0"))
            {
                //if this is a commlog
                if (!checkComm.Checked)
                {
                    continue;
                }
                 
            }
            else if (!StringSupport.equals(table.Rows[i]["RxNum"].ToString(), "0"))
            {
                //if this is an Rx
                if (!checkRx.Checked)
                {
                    continue;
                }
                 
            }
            else if (!StringSupport.equals(table.Rows[i]["LabCaseNum"].ToString(), "0"))
            {
                //if this is a LabCase
                if (!checkLabCase.Checked)
                {
                    continue;
                }
                 
            }
            else if (!StringSupport.equals(table.Rows[i]["AptNum"].ToString(), "0"))
            {
                //if this is an Appointment
                if (!checkAppt.Checked)
                {
                    continue;
                }
                 
            }
                 
            row = new OpenDental.UI.ODGridRow();
            row.setColorLborder(Color.Black);
            //remember that columns that start with lowercase are already altered for display rather than being raw data.
            row.getCells().Add(table.Rows[i]["procDate"].ToString());
            row.getCells().Add(table.Rows[i]["toothNum"].ToString());
            row.getCells().Add(table.Rows[i]["Surf"].ToString());
            row.getCells().Add(table.Rows[i]["dx"].ToString());
            row.getCells().Add(table.Rows[i]["description"].ToString());
            row.getCells().Add(table.Rows[i]["procStatus"].ToString());
            row.getCells().Add(table.Rows[i]["prov"].ToString());
            row.getCells().Add(table.Rows[i]["procFee"].ToString());
            row.getCells().Add(table.Rows[i]["ProcCode"].ToString());
            row.getCells().Add(table.Rows[i]["user"].ToString());
            row.getCells().Add(table.Rows[i]["signature"].ToString());
            if (checkNotes.Checked)
            {
                row.setNote(table.Rows[i]["note"].ToString());
            }
             
            row.setColorText(Color.FromArgb(PIn.Int(table.Rows[i]["colorText"].ToString())));
            row.setColorBackG(Color.FromArgb(PIn.Int(table.Rows[i]["colorBackG"].ToString())));
            row.setTag(table.Rows[i]);
            gridProg.getRows().add(row);
        }
        gridProg.endUpdate();
        gridProg.scrollToEnd();
    }

    private void gridProg_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //Chartscrollval = gridProg.ScrollValue;
        DataRow row = (DataRow)gridProg.getRows().get___idx(e.getRow()).getTag();
        if (!StringSupport.equals(row["ProcNum"].ToString(), "0"))
        {
            if (checkAudit.Checked)
            {
                MsgBox.show(this,"Not allowed to edit procedures when in audit mode.");
                return ;
            }
             
            Procedure proc = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), true);
            FormProcEdit FormP = new FormProcEdit(proc,PatCur,FamCur);
            FormP.ShowDialog();
            if (FormP.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(row["CommlogNum"].ToString(), "0"))
        {
            Commlog comm = Commlogs.GetOne(PIn.Long(row["CommlogNum"].ToString()));
            FormCommItem FormC = new FormCommItem(comm);
            FormC.ShowDialog();
            if (FormC.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(row["RxNum"].ToString(), "0"))
        {
            RxPat rx = RxPats.GetRx(PIn.Long(row["RxNum"].ToString()));
            FormRxEdit FormRxE = new FormRxEdit(PatCur,rx);
            FormRxE.ShowDialog();
            if (FormRxE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(row["LabCaseNum"].ToString(), "0"))
        {
            LabCase lab = LabCases.GetOne(PIn.Long(row["LabCaseNum"].ToString()));
            FormLabCaseEdit FormL = new FormLabCaseEdit();
            FormL.CaseCur = lab;
            FormL.ShowDialog();
        }
        else if (!StringSupport.equals(row["TaskNum"].ToString(), "0"))
        {
            Task task = Tasks.GetOne(PIn.Long(row["TaskNum"].ToString()));
            FormTaskEdit FormT = new FormTaskEdit(task,task.copy());
            FormT.Closing += new CancelEventHandler(TaskGoToEvent);
            FormT.Show();
        }
        else //non-modal
        if (!StringSupport.equals(row["AptNum"].ToString(), "0"))
        {
            //Appointment apt=Appointments.GetOneApt(
            FormApptEdit FormA = new FormApptEdit(PIn.Long(row["AptNum"].ToString()));
            //PinIsVisible=false
            FormA.ShowDialog();
            if (FormA.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(row["EmailMessageNum"].ToString(), "0"))
        {
            EmailMessage msg = EmailMessages.GetOne(PIn.Long(row["EmailMessageNum"].ToString()));
            FormEmailMessageEdit FormE = new FormEmailMessageEdit(msg);
            FormE.ShowDialog();
            if (FormE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
               
        moduleSelected(PatCur.PatNum);
    }

    public void taskGoToEvent(Object sender, CancelEventArgs e) throws Exception {
        FormTaskEdit FormT = (FormTaskEdit)sender;
        TaskObjectType GotoType = FormT.GotoType;
        long keyNum = FormT.GotoKeyNum;
        if (GotoType == TaskObjectType.None)
        {
            return ;
        }
         
        if (GotoType == TaskObjectType.Patient)
        {
            if (keyNum != 0)
            {
                Patient pat = Patients.getPat(keyNum);
                onPatientSelected(pat);
                moduleSelected(pat.PatNum);
                return ;
            }
             
        }
         
        if (GotoType == TaskObjectType.Appointment)
        {
            return ;
        }
         
    }

    //There's nothing to do here, since we're not in the appt module.
    private void checkShowTP_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkShowC_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkShowE_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkShowR_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkAppt_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkComm_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkLabCase_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkRx_Click(Object sender, EventArgs e) throws Exception {
        if (checkRx.Checked)
        {
            //since there is no double click event...this allows almost the same thing
            checkShowTP.Checked = false;
            checkShowC.Checked = false;
            checkShowE.Checked = false;
            checkShowR.Checked = false;
            checkNotes.Checked = true;
            checkRx.Checked = true;
            checkComm.Checked = false;
            checkAppt.Checked = false;
            checkLabCase.Checked = false;
            checkExtraNotes.Checked = false;
        }
         
        fillProgNotes();
    }

    private void checkExtraNotes_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void checkNotes_Click(Object sender, EventArgs e) throws Exception {
        fillProgNotes();
    }

    private void butShowNone_Click(Object sender, EventArgs e) throws Exception {
        checkShowTP.Checked = false;
        checkShowC.Checked = false;
        checkShowE.Checked = false;
        checkShowR.Checked = false;
        checkAppt.Checked = false;
        checkComm.Checked = false;
        checkLabCase.Checked = false;
        checkRx.Checked = false;
        checkShowTeeth.Checked = false;
        fillProgNotes();
    }

    private void butShowAll_Click(Object sender, EventArgs e) throws Exception {
        checkShowTP.Checked = true;
        checkShowC.Checked = true;
        checkShowE.Checked = true;
        checkShowR.Checked = true;
        checkAppt.Checked = true;
        checkComm.Checked = true;
        checkLabCase.Checked = true;
        checkRx.Checked = true;
        checkShowTeeth.Checked = false;
        fillProgNotes();
    }

    private void gridProg_MouseUp(Object sender, MouseEventArgs e) throws Exception {
    }

    private void checkShowFamilyComm_Click(Object sender, EventArgs e) throws Exception {
        fillComm();
    }

    private void labelInsRem_MouseEnter(Object sender, EventArgs e) throws Exception {
        groupBoxFamilyIns.Visible = true;
        groupBoxIndIns.Visible = true;
    }

    private void labelInsRem_MouseLeave(Object sender, EventArgs e) throws Exception {
        groupBoxFamilyIns.Visible = false;
        groupBoxIndIns.Visible = false;
    }

}


