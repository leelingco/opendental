//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:17 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.AutomationL;
import OpenDental.Bridges.ECW;
import OpenDental.ChartLayoutHelper;
import OpenDental.ContrChart;
import OpenDental.DateTimeOD;
import OpenDental.FormApptEdit;
import OpenDental.FormChartView;
import OpenDental.FormChartViewDateFilter;
import OpenDental.FormCommItem;
import OpenDental.FormEHR;
import OpenDental.FormEhrProvKeysCustomer;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormExamSheets;
import OpenDental.FormFormPatEdit;
import OpenDental.FormImageViewer;
import OpenDental.FormLabCaseEdit;
import OpenDental.FormMedical;
import OpenDental.FormOpenDental;
import OpenDental.FormOrthoChart;
import OpenDental.FormPatFieldCheckEdit;
import OpenDental.FormPatFieldDateEdit;
import OpenDental.FormPatFieldEdit;
import OpenDental.FormPatFieldPickEdit;
import OpenDental.FormPatientEdit;
import OpenDental.FormPatientPortal;
import OpenDental.FormPayorTypes;
import OpenDental.FormPerio;
import OpenDental.FormPhoneNumbersManage;
import OpenDental.FormProcCodes;
import OpenDental.FormProcEdit;
import OpenDental.FormProcEditAll;
import OpenDental.FormProcGroup;
import OpenDental.FormReference;
import OpenDental.FormReferenceEntryEdit;
import OpenDental.FormReferralsPatient;
import OpenDental.FormRegistrationKeyEdit;
import OpenDental.FormRpPrintPreview;
import OpenDental.FormRxEdit;
import OpenDental.FormRxSelect;
import OpenDental.FormSheetFillEdit;
import OpenDental.FormTaskEdit;
import OpenDental.FormToothChartingBig;
import OpenDental.FormWebMailMessageEdit;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.NewCrop.AccountRequest;
import OpenDental.NewCrop.Credentials;
import OpenDental.NewCrop.PatientInformationRequester;
import OpenDental.NewCrop.PatientRequest;
import OpenDental.NewCrop.PrescriptionHistoryRequest;
import OpenDental.NewCrop.Result;
import OpenDental.NewCrop.StatusType;
import OpenDental.NewCrop.Update1;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.PrinterL;
import OpenDental.ProcedureL;
import OpenDental.ProgramL;
import OpenDental.Properties.Resources;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.Reporting.Allocators.MyAllocator1_ProviderPayment;
import OpenDental.SheetFiller;
import OpenDental.SheetUtil;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.AutoCodeItems;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.ChartModuleComponentsToLoad;
import OpenDentBusiness.ChartModules;
import OpenDentBusiness.ChartView;
import OpenDentBusiness.ChartViewDates;
import OpenDentBusiness.ChartViewObjs;
import OpenDentBusiness.ChartViewProcStat;
import OpenDentBusiness.ChartViews;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.CommSentOrReceived;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.CustRefEntries;
import OpenDentBusiness.CustRefEntry;
import OpenDentBusiness.CustReferences;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Documents;
import OpenDentBusiness.EhrFormResult;
import OpenDentBusiness.EhrProvKey;
import OpenDentBusiness.EhrProvKeys;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.EmailSentOrReceived;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Encounters;
import OpenDentBusiness.ErxLog;
import OpenDentBusiness.ErxLogs;
import OpenDentBusiness.ErxXml;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FormPat;
import OpenDentBusiness.FormPats;
import OpenDentBusiness.HL7.MessageConstructor;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.HL7Msgs;
import OpenDentBusiness.ImageCategorySpecial;
import OpenDentBusiness.ImageHelper;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionProcs;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.PatFields;
import OpenDentBusiness.PatFieldType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientLogic;
import OpenDentBusiness.PatientNote;
import OpenDentBusiness.PatientNotes;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PayorTypes;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.PlannedAppts;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.ProcButton;
import OpenDentBusiness.ProcButtonItems;
import OpenDentBusiness.ProcButtons;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcGroupItem;
import OpenDentBusiness.ProcGroupItems;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.RegistrationKey;
import OpenDentBusiness.RegistrationKeys;
import OpenDentBusiness.RxNorm;
import OpenDentBusiness.RxNorms;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.RxSendStatus;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sheet;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetInternalType;
import OpenDentBusiness.SheetParameter;
import OpenDentBusiness.Sheets;
import OpenDentBusiness.SheetsInternal;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.TerminalActives;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;
import OpenDentBusiness.ToothNumberingNomenclature;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.YN;
import SparksToothChart.__MultiToothChartDrawEventHandler;
import SparksToothChart.ToothChartDirectX;
import SparksToothChart.ToothChartDrawEventArgs;
import SparksToothChart.ToothGraphic;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrChart  extends System.Windows.Forms.UserControl 
{
    private OpenDental.UI.Button butAddProc;
    private OpenDental.UI.Button butM;
    private OpenDental.UI.Button butOI;
    private OpenDental.UI.Button butD;
    private OpenDental.UI.Button butBF;
    private OpenDental.UI.Button butL;
    private OpenDental.UI.Button butV;
    private System.Windows.Forms.TextBox textSurf = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioEntryTP = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioEntryEO = new System.Windows.Forms.RadioButton();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.RadioButton radioEntryEC = new System.Windows.Forms.RadioButton();
    private ProcStat newStatus = ProcStat.TP;
    private OpenDental.UI.Button button1;
    private System.Windows.Forms.RadioButton radioEntryC = new System.Windows.Forms.RadioButton();
    //private bool dataValid=false;
    private System.Windows.Forms.ListBox listDx = new System.Windows.Forms.ListBox();
    private int[] hiLightedRows = new int[1];
    //private ContrApptSingle ApptPlanned;
    private System.Windows.Forms.CheckBox checkDone = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.RadioButton radioEntryR = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkNotes = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkShowR = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.Button butShowNone;
    private OpenDental.UI.Button butShowAll;
    private System.Windows.Forms.CheckBox checkShowE = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkShowC = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkShowTP = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkRx = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.TextBox textProcCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butNew;
    private OpenDental.UI.Button butClear;
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.Label labelDx = new System.Windows.Forms.Label();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private System.Windows.Forms.ComboBox comboPriority = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ContextMenu menuProgRight = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.MenuItem menuItemPrintProg = new System.Windows.Forms.MenuItem();
    private System.Windows.Forms.TabPage tabPage1 = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabPage2 = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabPage4 = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabControl tabControlImages = new System.Windows.Forms.TabControl();
    private System.Windows.Forms.Panel panelImages = new System.Windows.Forms.Panel();
    /**
    * public for plugins
    */
    public boolean TreatmentNoteChanged = new boolean();
    /**
    * Keeps track of which tab is selected. It's the index of the selected tab.
    */
    private int selectedImageTab = 0;
    private boolean MouseIsDownOnImageSplitter = new boolean();
    private int ImageSplitterOriginalY = new int();
    private int OriginalImageMousePos = new int();
    private System.Windows.Forms.ImageList imageListThumbnails = new System.Windows.Forms.ImageList();
    private System.Windows.Forms.ListView listViewImages = new System.Windows.Forms.ListView();
    /**
    * The indices of the image categories which are visible in Chart.
    */
    private ArrayList visImageCats = new ArrayList();
    /**
    * The indices within Documents.List[i] of docs which are visible in Chart.
    */
    private ArrayList visImages = new ArrayList();
    /**
    * Full path to the patient folder, including \ on the end
    */
    private String patFolder = new String();
    private OpenDental.ODtextBox textTreatmentNotes;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkToday = new System.Windows.Forms.CheckBox();
    private FormImageViewer formImageViewer;
    private Family FamCur;
    private Patient PatCur;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    /**
    * For one patient. Allows highlighting rows.
    */
    private Appointment[] ApptList = new Appointment[]();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private int pagesPrinted = new int();
    private System.Windows.Forms.CheckBox checkShowTeeth = new System.Windows.Forms.CheckBox();
    //used in printing progress notes
    private boolean headingPrinted = new boolean();
    private int headingPrintH = new int();
    private OpenDentBusiness.Document[] DocumentList = new OpenDentBusiness.Document[]();
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private MenuItem menuItemSetComplete = new MenuItem();
    private MenuItem menuItemEditSelected = new MenuItem();
    private MenuItem menuItemGroupSelected = new MenuItem();
    private OpenDental.UI.Button butPin;
    private ListBox listButtonCats = new ListBox();
    private ListView listViewButtons = new ListView();
    private List<Benefit> BenefitList = new List<Benefit>();
    private ImageList imageListProcButtons = new ImageList();
    private ColumnHeader columnHeader1 = new ColumnHeader();
    private TabControl tabProc = new TabControl();
    private TabPage tabEnterTx = new TabPage();
    private TabPage tabMissing = new TabPage();
    private ProcButton[] ProcButtonList = new ProcButton[]();
    private TabPage tabPrimary = new TabPage();
    private TabPage tabMovements = new TabPage();
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butUnhide;
    private Label label5 = new Label();
    private ListBox listHidden = new ListBox();
    private OpenDental.UI.Button butEdentulous;
    private Label label7 = new Label();
    private OpenDental.UI.Button butNotMissing;
    private OpenDental.UI.Button butMissing;
    private OpenDental.UI.Button butHidden;
    private GroupBox groupBox3 = new GroupBox();
    private Label label8 = new Label();
    private GroupBox groupBox4 = new GroupBox();
    private OpenDental.ValidDouble textTipB;
    private Label label11 = new Label();
    private OpenDental.ValidDouble textTipM;
    private Label label12 = new Label();
    private OpenDental.ValidDouble textRotate;
    private Label label15 = new Label();
    private OpenDental.ValidDouble textShiftB;
    private Label label10 = new Label();
    private OpenDental.ValidDouble textShiftO;
    private Label label9 = new Label();
    private OpenDental.ValidDouble textShiftM;
    private OpenDental.UI.Button butRotatePlus;
    private OpenDental.UI.Button butMixed;
    private OpenDental.UI.Button butAllPrimary;
    private OpenDental.UI.Button butAllPerm;
    private GroupBox groupBox5 = new GroupBox();
    private OpenDental.UI.Button butPerm;
    private OpenDental.UI.Button butPrimary;
    private int SelectedProcTab = new int();
    private OpenDental.UI.Button butTipBplus;
    private OpenDental.UI.Button butTipMplus;
    private OpenDental.UI.Button butShiftBplus;
    private OpenDental.UI.Button butShiftOplus;
    private OpenDental.UI.Button butShiftMplus;
    private Label label16 = new Label();
    private OpenDental.UI.Button butApplyMovements;
    private OpenDental.UI.Button butTipBminus;
    private OpenDental.UI.Button butTipMminus;
    private OpenDental.UI.Button butRotateMinus;
    private OpenDental.UI.Button butShiftBminus;
    private OpenDental.UI.Button butShiftOminus;
    private OpenDental.UI.Button butShiftMminus;
    private OpenDental.UI.ODGrid gridProg;
    private OpenDental.UI.ODGrid gridPtInfo;
    private CheckBox checkComm = new CheckBox();
    private List<ToothInitial> ToothInitialList = new List<ToothInitial>();
    private MenuItem menuItemPrintDay = new MenuItem();
    /**
    * a list of the hidden teeth as strings. Includes "1"-"32", and "A"-"Z"
    */
    private ArrayList HiddenTeeth = new ArrayList();
    private CheckBox checkAudit = new CheckBox();
    //<summary>This date will usually have minVal except while the hospital print function is running.</summary>
    //private DateTime hospitalDate;
    private PatientNote PatientNoteCur;
    private DataSet DataSetMain = new DataSet();
    private MenuItem menuItemLabFee = new MenuItem();
    private MenuItem menuItemLabFeeDetach = new MenuItem();
    private MenuItem menuItemDelete = new MenuItem();
    private SparksToothChart.ToothChartWrapper toothChart;
    private Panel panelQuickButtons = new Panel();
    private OpenDental.UI.Button buttonCSeal;
    private OpenDental.UI.Button buttonCMO;
    private OpenDental.UI.Button buttonCMOD;
    private OpenDental.UI.Button buttonCO;
    private Label label23 = new Label();
    private OpenDental.UI.Button buttonCDO;
    private OpenDental.UI.Button butCOB;
    private OpenDental.UI.Button butCOL;
    private OpenDental.UI.Button butML;
    private OpenDental.UI.Button butDL;
    /**
    * A subset of DataSetMain.  The procedures that need to be drawn in the graphical tooth chart.
    */
    List<DataRow> ProcList = new List<DataRow>();
    //private int lastPatNum;
    private OpenDental.UI.Button butCMDL;
    private Label label1 = new Label();
    private TabPage tabPlanned = new TabPage();
    private TabPage tabShow = new TabPage();
    private GroupBox groupBox7 = new GroupBox();
    private GroupBox groupBox6 = new GroupBox();
    private CheckBox checkAppt = new CheckBox();
    private CheckBox checkLabCase = new CheckBox();
    private OpenDental.UI.Button buttonCMODL;
    private OpenDental.UI.Button buttonCMODB;
    private OpenDental.UI.Button butAddKey;
    //private MenuItem menuItemDeleteSelected;
    private CheckBox checkCommFamily = new CheckBox();
    private OpenDental.UI.Button butForeignKey;
    private CheckBox checkTasks = new CheckBox();
    private CheckBox checkEmail = new CheckBox();
    private long PrevPtNum = new long();
    private CheckBox checkSheets = new CheckBox();
    private TabPage tabDraw = new TabPage();
    private RadioButton radioPointer = new RadioButton();
    private RadioButton radioEraser = new RadioButton();
    private RadioButton radioPen = new RadioButton();
    private Panel panelDrawColor = new Panel();
    private GroupBox groupBox8 = new GroupBox();
    private Panel panelTPlight = new Panel();
    private Panel panelTPdark = new Panel();
    private Label label18 = new Label();
    private OpenDental.UI.Button butColorOther;
    private Panel panelRdark = new Panel();
    private Label label21 = new Label();
    private Panel panelRlight = new Panel();
    private Panel panelEOdark = new Panel();
    private Label label20 = new Label();
    private Panel panelEOlight = new Panel();
    private Panel panelECdark = new Panel();
    private Label label19 = new Label();
    private Panel panelEClight = new Panel();
    private Panel panelCdark = new Panel();
    private Label label17 = new Label();
    private Panel panelClight = new Panel();
    private RadioButton radioColorChanger = new RadioButton();
    private Panel panelBlack = new Panel();
    private Label label22 = new Label();
    private Panel panelQuickPasteAmalgam = new Panel();
    private OpenDental.UI.Button buttonAMODB;
    private OpenDental.UI.Button buttonAMODL;
    private OpenDental.UI.Button buttonAOB;
    private OpenDental.UI.Button buttonAOL;
    private OpenDental.UI.Button buttonAO;
    private OpenDental.UI.Button buttonAMO;
    private OpenDental.UI.Button buttonAMOD;
    private OpenDental.UI.Button buttonADO;
    private Label label24 = new Label();
    private OpenDental.UI.ODGrid gridPlanned;
    private ContextMenu menuConsent = new ContextMenu();
    private RadioButton radioEntryCn = new RadioButton();
    private CheckBox checkShowCn = new CheckBox();
    private int Chartscrollval = new int();
    private OpenDental.UI.Button butECWdown;
    private OpenDental.UI.Button butECWup;
    private System.Windows.Forms.WebBrowser webBrowserEcw = new System.Windows.Forms.WebBrowser();
    private Panel panelEcw = new Panel();
    private Label labelECWerror = new Label();
    private ContextMenu menuToothChart = new ContextMenu();
    private MenuItem menuItemChartBig = new MenuItem();
    private MenuItem menuItemChartSave = new MenuItem();
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    private PatField[] PatFieldList = new PatField[]();
    private OpenDental.UI.Button butChartViewDown;
    private OpenDental.UI.Button butChartViewUp;
    private OpenDental.UI.Button butChartViewAdd;
    private Label labelCustView = new Label();
    private OpenDental.UI.ODGrid gridChartViews;
    private boolean InitializedOnStartup = new boolean();
    //<summary>Can be null if user has not set up any views.  Defaults to first in list when starting up.</summary>
    private ChartView ChartViewCurDisplay;
    private TabPage tabPatInfo = new TabPage();
    private ListBox listProcStatusCodes = new ListBox();
    private boolean chartCustViewChanged = new boolean();
    private ComboBox comboPrognosis = new ComboBox();
    private Label labelPrognosis = new Label();
    //		private TextBox textShowDateRange;
    //		private UI.Button butShowDateRange;
    private long orionProvNum = new long();
    private DateTime ShowDateStart = new DateTime();
    private OpenDental.UI.Button butShowDateRange;
    private TextBox textShowDateRange = new TextBox();
    private TabPage tabCustomer = new TabPage();
    private OpenDental.UI.ODGrid gridCustomerViews;
    private Label labelTimes = new Label();
    private Label labelMonth1 = new Label();
    private Label labelMonth2 = new Label();
    private Label labelMonth3 = new Label();
    private Label labelMonthAvg = new Label();
    private TextBox textMonthAvg = new TextBox();
    private TextBox textMonth3 = new TextBox();
    private TextBox textMonth2 = new TextBox();
    private TextBox textMonth1 = new TextBox();
    private ListBox listCommonProcs = new ListBox();
    private Label labelCommonProc = new Label();
    private DateTime ShowDateEnd = new DateTime();
    private Label label2 = new Label();
    private Label labelMonth0 = new Label();
    private TextBox textMonth0 = new TextBox();
    private OpenDental.UI.Button butPhoneNums;
    private boolean IsDistributorKey = new boolean();
    static /* [UNSUPPORTED] 'extern' modifier not supported */ boolean internetSetCookie(String lpszUrlName, String lbszCookieName, String lpszCookieData) throws Exception ;

    /**
    * 
    */
    public ContrChart() throws Exception {
        Logger.openlog.log("Initializing chart module...",Severity.INFO);
        initializeComponent();
        tabControlImages.DrawItem += new DrawItemEventHandler(OnDrawItem);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            panelQuickButtons.Enabled = false;
            butBF.Text = Lan.g(this,"B/V");
            //vestibular instead of facial
            butV.Text = Lan.g(this,"5");
        }
        else
        {
            menuItemLabFee.Visible = false;
            menuItemLabFeeDetach.Visible = false;
        } 
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
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ContrChart.class);
        SparksToothChart.ToothChartData toothChartData1 = new SparksToothChart.ToothChartData();
        this.textSurf = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.radioEntryCn = new System.Windows.Forms.RadioButton();
        this.radioEntryR = new System.Windows.Forms.RadioButton();
        this.radioEntryC = new System.Windows.Forms.RadioButton();
        this.radioEntryEO = new System.Windows.Forms.RadioButton();
        this.radioEntryEC = new System.Windows.Forms.RadioButton();
        this.radioEntryTP = new System.Windows.Forms.RadioButton();
        this.listDx = new System.Windows.Forms.ListBox();
        this.labelDx = new System.Windows.Forms.Label();
        this.checkDone = new System.Windows.Forms.CheckBox();
        this.listViewButtons = new System.Windows.Forms.ListView();
        this.columnHeader1 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
        this.imageListProcButtons = new System.Windows.Forms.ImageList(this.components);
        this.listButtonCats = new System.Windows.Forms.ListBox();
        this.comboPriority = new System.Windows.Forms.ComboBox();
        this.checkToday = new System.Windows.Forms.CheckBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textProcCode = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.checkAudit = new System.Windows.Forms.CheckBox();
        this.checkComm = new System.Windows.Forms.CheckBox();
        this.checkShowTeeth = new System.Windows.Forms.CheckBox();
        this.checkNotes = new System.Windows.Forms.CheckBox();
        this.checkRx = new System.Windows.Forms.CheckBox();
        this.checkShowR = new System.Windows.Forms.CheckBox();
        this.checkShowE = new System.Windows.Forms.CheckBox();
        this.checkShowC = new System.Windows.Forms.CheckBox();
        this.checkShowTP = new System.Windows.Forms.CheckBox();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.menuProgRight = new System.Windows.Forms.ContextMenu();
        this.menuItemDelete = new System.Windows.Forms.MenuItem();
        this.menuItemSetComplete = new System.Windows.Forms.MenuItem();
        this.menuItemEditSelected = new System.Windows.Forms.MenuItem();
        this.menuItemGroupSelected = new System.Windows.Forms.MenuItem();
        this.menuItemPrintProg = new System.Windows.Forms.MenuItem();
        this.menuItemPrintDay = new System.Windows.Forms.MenuItem();
        this.menuItemLabFeeDetach = new System.Windows.Forms.MenuItem();
        this.menuItemLabFee = new System.Windows.Forms.MenuItem();
        this.tabControlImages = new System.Windows.Forms.TabControl();
        this.tabPage1 = new System.Windows.Forms.TabPage();
        this.tabPage2 = new System.Windows.Forms.TabPage();
        this.tabPage4 = new System.Windows.Forms.TabPage();
        this.panelImages = new System.Windows.Forms.Panel();
        this.listViewImages = new System.Windows.Forms.ListView();
        this.imageListThumbnails = new System.Windows.Forms.ImageList(this.components);
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.tabProc = new System.Windows.Forms.TabControl();
        this.tabEnterTx = new System.Windows.Forms.TabPage();
        this.comboPrognosis = new System.Windows.Forms.ComboBox();
        this.labelPrognosis = new System.Windows.Forms.Label();
        this.panelQuickButtons = new System.Windows.Forms.Panel();
        this.panelQuickPasteAmalgam = new System.Windows.Forms.Panel();
        this.buttonAMODB = new OpenDental.UI.Button();
        this.buttonAMODL = new OpenDental.UI.Button();
        this.buttonAOB = new OpenDental.UI.Button();
        this.buttonAOL = new OpenDental.UI.Button();
        this.buttonAO = new OpenDental.UI.Button();
        this.buttonAMO = new OpenDental.UI.Button();
        this.buttonAMOD = new OpenDental.UI.Button();
        this.buttonADO = new OpenDental.UI.Button();
        this.label24 = new System.Windows.Forms.Label();
        this.buttonCMODB = new OpenDental.UI.Button();
        this.buttonCMODL = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.butCMDL = new OpenDental.UI.Button();
        this.butML = new OpenDental.UI.Button();
        this.butDL = new OpenDental.UI.Button();
        this.butCOB = new OpenDental.UI.Button();
        this.butCOL = new OpenDental.UI.Button();
        this.buttonCSeal = new OpenDental.UI.Button();
        this.buttonCMO = new OpenDental.UI.Button();
        this.buttonCMOD = new OpenDental.UI.Button();
        this.buttonCO = new OpenDental.UI.Button();
        this.label23 = new System.Windows.Forms.Label();
        this.buttonCDO = new OpenDental.UI.Button();
        this.butD = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.butBF = new OpenDental.UI.Button();
        this.butL = new OpenDental.UI.Button();
        this.butM = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butAddProc = new OpenDental.UI.Button();
        this.butV = new OpenDental.UI.Button();
        this.butOI = new OpenDental.UI.Button();
        this.tabMissing = new System.Windows.Forms.TabPage();
        this.butUnhide = new OpenDental.UI.Button();
        this.label5 = new System.Windows.Forms.Label();
        this.listHidden = new System.Windows.Forms.ListBox();
        this.butEdentulous = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label7 = new System.Windows.Forms.Label();
        this.butNotMissing = new OpenDental.UI.Button();
        this.butMissing = new OpenDental.UI.Button();
        this.butHidden = new OpenDental.UI.Button();
        this.tabMovements = new System.Windows.Forms.TabPage();
        this.label16 = new System.Windows.Forms.Label();
        this.butApplyMovements = new OpenDental.UI.Button();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.butTipBplus = new OpenDental.UI.Button();
        this.butTipBminus = new OpenDental.UI.Button();
        this.butTipMplus = new OpenDental.UI.Button();
        this.butTipMminus = new OpenDental.UI.Button();
        this.butRotatePlus = new OpenDental.UI.Button();
        this.butRotateMinus = new OpenDental.UI.Button();
        this.textTipB = new OpenDental.ValidDouble();
        this.label11 = new System.Windows.Forms.Label();
        this.textTipM = new OpenDental.ValidDouble();
        this.label12 = new System.Windows.Forms.Label();
        this.textRotate = new OpenDental.ValidDouble();
        this.label15 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.butShiftBplus = new OpenDental.UI.Button();
        this.butShiftBminus = new OpenDental.UI.Button();
        this.butShiftOplus = new OpenDental.UI.Button();
        this.butShiftOminus = new OpenDental.UI.Button();
        this.butShiftMplus = new OpenDental.UI.Button();
        this.butShiftMminus = new OpenDental.UI.Button();
        this.textShiftB = new OpenDental.ValidDouble();
        this.label10 = new System.Windows.Forms.Label();
        this.textShiftO = new OpenDental.ValidDouble();
        this.label9 = new System.Windows.Forms.Label();
        this.textShiftM = new OpenDental.ValidDouble();
        this.label8 = new System.Windows.Forms.Label();
        this.tabPrimary = new System.Windows.Forms.TabPage();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.butPerm = new OpenDental.UI.Button();
        this.butPrimary = new OpenDental.UI.Button();
        this.butMixed = new OpenDental.UI.Button();
        this.butAllPrimary = new OpenDental.UI.Button();
        this.butAllPerm = new OpenDental.UI.Button();
        this.tabPlanned = new System.Windows.Forms.TabPage();
        this.butDown = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butPin = new OpenDental.UI.Button();
        this.butClear = new OpenDental.UI.Button();
        this.butNew = new OpenDental.UI.Button();
        this.gridPlanned = new OpenDental.UI.ODGrid();
        this.tabShow = new System.Windows.Forms.TabPage();
        this.butShowDateRange = new OpenDental.UI.Button();
        this.textShowDateRange = new System.Windows.Forms.TextBox();
        this.listProcStatusCodes = new System.Windows.Forms.ListBox();
        this.gridChartViews = new OpenDental.UI.ODGrid();
        this.labelCustView = new System.Windows.Forms.Label();
        this.butChartViewDown = new OpenDental.UI.Button();
        this.butChartViewUp = new OpenDental.UI.Button();
        this.butChartViewAdd = new OpenDental.UI.Button();
        this.groupBox7 = new System.Windows.Forms.GroupBox();
        this.checkSheets = new System.Windows.Forms.CheckBox();
        this.checkTasks = new System.Windows.Forms.CheckBox();
        this.checkEmail = new System.Windows.Forms.CheckBox();
        this.checkCommFamily = new System.Windows.Forms.CheckBox();
        this.checkAppt = new System.Windows.Forms.CheckBox();
        this.checkLabCase = new System.Windows.Forms.CheckBox();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.checkShowCn = new System.Windows.Forms.CheckBox();
        this.butShowAll = new OpenDental.UI.Button();
        this.butShowNone = new OpenDental.UI.Button();
        this.tabDraw = new System.Windows.Forms.TabPage();
        this.radioColorChanger = new System.Windows.Forms.RadioButton();
        this.groupBox8 = new System.Windows.Forms.GroupBox();
        this.panelBlack = new System.Windows.Forms.Panel();
        this.label22 = new System.Windows.Forms.Label();
        this.butColorOther = new OpenDental.UI.Button();
        this.panelRdark = new System.Windows.Forms.Panel();
        this.label21 = new System.Windows.Forms.Label();
        this.panelRlight = new System.Windows.Forms.Panel();
        this.panelEOdark = new System.Windows.Forms.Panel();
        this.label20 = new System.Windows.Forms.Label();
        this.panelEOlight = new System.Windows.Forms.Panel();
        this.panelECdark = new System.Windows.Forms.Panel();
        this.label19 = new System.Windows.Forms.Label();
        this.panelEClight = new System.Windows.Forms.Panel();
        this.panelCdark = new System.Windows.Forms.Panel();
        this.label17 = new System.Windows.Forms.Label();
        this.panelClight = new System.Windows.Forms.Panel();
        this.panelTPdark = new System.Windows.Forms.Panel();
        this.label18 = new System.Windows.Forms.Label();
        this.panelTPlight = new System.Windows.Forms.Panel();
        this.panelDrawColor = new System.Windows.Forms.Panel();
        this.radioEraser = new System.Windows.Forms.RadioButton();
        this.radioPen = new System.Windows.Forms.RadioButton();
        this.radioPointer = new System.Windows.Forms.RadioButton();
        this.tabPatInfo = new System.Windows.Forms.TabPage();
        this.tabCustomer = new System.Windows.Forms.TabPage();
        this.labelMonth0 = new System.Windows.Forms.Label();
        this.textMonth0 = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.labelCommonProc = new System.Windows.Forms.Label();
        this.labelTimes = new System.Windows.Forms.Label();
        this.labelMonth1 = new System.Windows.Forms.Label();
        this.labelMonth2 = new System.Windows.Forms.Label();
        this.labelMonth3 = new System.Windows.Forms.Label();
        this.labelMonthAvg = new System.Windows.Forms.Label();
        this.textMonthAvg = new System.Windows.Forms.TextBox();
        this.textMonth3 = new System.Windows.Forms.TextBox();
        this.textMonth2 = new System.Windows.Forms.TextBox();
        this.textMonth1 = new System.Windows.Forms.TextBox();
        this.listCommonProcs = new System.Windows.Forms.ListBox();
        this.gridCustomerViews = new OpenDental.UI.ODGrid();
        this.menuConsent = new System.Windows.Forms.ContextMenu();
        this.panelEcw = new System.Windows.Forms.Panel();
        this.labelECWerror = new System.Windows.Forms.Label();
        this.webBrowserEcw = new System.Windows.Forms.WebBrowser();
        this.butECWdown = new OpenDental.UI.Button();
        this.butECWup = new OpenDental.UI.Button();
        this.menuToothChart = new System.Windows.Forms.ContextMenu();
        this.menuItemChartBig = new System.Windows.Forms.MenuItem();
        this.menuItemChartSave = new System.Windows.Forms.MenuItem();
        this.toothChart = new SparksToothChart.ToothChartWrapper();
        this.gridProg = new OpenDental.UI.ODGrid();
        this.gridPtInfo = new OpenDental.UI.ODGrid();
        this.butForeignKey = new OpenDental.UI.Button();
        this.butAddKey = new OpenDental.UI.Button();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.button1 = new OpenDental.UI.Button();
        this.textTreatmentNotes = new OpenDental.ODtextBox();
        this.butPhoneNums = new OpenDental.UI.Button();
        this.groupBox2.SuspendLayout();
        this.tabControlImages.SuspendLayout();
        this.panelImages.SuspendLayout();
        this.tabProc.SuspendLayout();
        this.tabEnterTx.SuspendLayout();
        this.panelQuickButtons.SuspendLayout();
        this.panelQuickPasteAmalgam.SuspendLayout();
        this.tabMissing.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.tabMovements.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.tabPrimary.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.tabPlanned.SuspendLayout();
        this.tabShow.SuspendLayout();
        this.groupBox7.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.tabDraw.SuspendLayout();
        this.groupBox8.SuspendLayout();
        this.tabCustomer.SuspendLayout();
        this.panelEcw.SuspendLayout();
        this.SuspendLayout();
        //
        // textSurf
        //
        this.textSurf.BackColor = System.Drawing.SystemColors.Window;
        this.textSurf.Location = new System.Drawing.Point(8, 2);
        this.textSurf.Name = "textSurf";
        this.textSurf.ReadOnly = true;
        this.textSurf.Size = new System.Drawing.Size(72, 20);
        this.textSurf.TabIndex = 25;
        //
        // groupBox2
        //
        this.groupBox2.BackColor = System.Drawing.SystemColors.Window;
        this.groupBox2.Controls.Add(this.radioEntryCn);
        this.groupBox2.Controls.Add(this.radioEntryR);
        this.groupBox2.Controls.Add(this.radioEntryC);
        this.groupBox2.Controls.Add(this.radioEntryEO);
        this.groupBox2.Controls.Add(this.radioEntryEC);
        this.groupBox2.Controls.Add(this.radioEntryTP);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(1, 85);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(88, 110);
        this.groupBox2.TabIndex = 35;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Entry Status";
        //
        // radioEntryCn
        //
        this.radioEntryCn.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioEntryCn.Location = new System.Drawing.Point(2, 91);
        this.radioEntryCn.Name = "radioEntryCn";
        this.radioEntryCn.Size = new System.Drawing.Size(75, 16);
        this.radioEntryCn.TabIndex = 5;
        this.radioEntryCn.Text = "Condition";
        this.radioEntryCn.CheckedChanged += new System.EventHandler(this.radioEntryCn_CheckedChanged);
        //
        // radioEntryR
        //
        this.radioEntryR.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioEntryR.Location = new System.Drawing.Point(2, 76);
        this.radioEntryR.Name = "radioEntryR";
        this.radioEntryR.Size = new System.Drawing.Size(75, 16);
        this.radioEntryR.TabIndex = 4;
        this.radioEntryR.Text = "Referred";
        this.radioEntryR.CheckedChanged += new System.EventHandler(this.radioEntryR_CheckedChanged);
        //
        // radioEntryC
        //
        this.radioEntryC.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioEntryC.Location = new System.Drawing.Point(2, 31);
        this.radioEntryC.Name = "radioEntryC";
        this.radioEntryC.Size = new System.Drawing.Size(74, 16);
        this.radioEntryC.TabIndex = 3;
        this.radioEntryC.Text = "Complete";
        this.radioEntryC.CheckedChanged += new System.EventHandler(this.radioEntryC_CheckedChanged);
        //
        // radioEntryEO
        //
        this.radioEntryEO.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioEntryEO.Location = new System.Drawing.Point(2, 61);
        this.radioEntryEO.Name = "radioEntryEO";
        this.radioEntryEO.Size = new System.Drawing.Size(72, 16);
        this.radioEntryEO.TabIndex = 2;
        this.radioEntryEO.Text = "ExistOther";
        this.radioEntryEO.CheckedChanged += new System.EventHandler(this.radioEntryEO_CheckedChanged);
        //
        // radioEntryEC
        //
        this.radioEntryEC.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioEntryEC.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.radioEntryEC.Location = new System.Drawing.Point(2, 46);
        this.radioEntryEC.Name = "radioEntryEC";
        this.radioEntryEC.Size = new System.Drawing.Size(84, 16);
        this.radioEntryEC.TabIndex = 1;
        this.radioEntryEC.Text = "ExistCurProv";
        this.radioEntryEC.CheckedChanged += new System.EventHandler(this.radioEntryEC_CheckedChanged);
        //
        // radioEntryTP
        //
        this.radioEntryTP.Checked = true;
        this.radioEntryTP.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioEntryTP.Location = new System.Drawing.Point(2, 16);
        this.radioEntryTP.Name = "radioEntryTP";
        this.radioEntryTP.Size = new System.Drawing.Size(77, 16);
        this.radioEntryTP.TabIndex = 0;
        this.radioEntryTP.TabStop = true;
        this.radioEntryTP.Text = "TreatPlan";
        this.radioEntryTP.CheckedChanged += new System.EventHandler(this.radioEntryTP_CheckedChanged);
        //
        // listDx
        //
        this.listDx.Location = new System.Drawing.Point(91, 16);
        this.listDx.Name = "listDx";
        this.listDx.Size = new System.Drawing.Size(94, 134);
        this.listDx.TabIndex = 46;
        this.listDx.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listDx_MouseDown);
        //
        // labelDx
        //
        this.labelDx.Location = new System.Drawing.Point(89, -2);
        this.labelDx.Name = "labelDx";
        this.labelDx.Size = new System.Drawing.Size(100, 18);
        this.labelDx.TabIndex = 47;
        this.labelDx.Text = "Diagnosis";
        this.labelDx.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkDone
        //
        this.checkDone.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkDone.Location = new System.Drawing.Point(413, 5);
        this.checkDone.Name = "checkDone";
        this.checkDone.Size = new System.Drawing.Size(67, 16);
        this.checkDone.TabIndex = 0;
        this.checkDone.Text = "Done";
        this.checkDone.Click += new System.EventHandler(this.checkDone_Click);
        //
        // listViewButtons
        //
        this.listViewButtons.Activation = System.Windows.Forms.ItemActivation.OneClick;
        this.listViewButtons.AutoArrange = false;
        this.listViewButtons.Columns.AddRange(new System.Windows.Forms.ColumnHeader[]{ this.columnHeader1 });
        this.listViewButtons.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.None;
        this.listViewButtons.Location = new System.Drawing.Point(311, 40);
        this.listViewButtons.MultiSelect = false;
        this.listViewButtons.Name = "listViewButtons";
        this.listViewButtons.Size = new System.Drawing.Size(178, 192);
        this.listViewButtons.SmallImageList = this.imageListProcButtons;
        this.listViewButtons.TabIndex = 188;
        this.listViewButtons.UseCompatibleStateImageBehavior = false;
        this.listViewButtons.View = System.Windows.Forms.View.Details;
        this.listViewButtons.Click += new System.EventHandler(this.listViewButtons_Click);
        //
        // columnHeader1
        //
        this.columnHeader1.Width = 155;
        //
        // imageListProcButtons
        //
        this.imageListProcButtons.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListProcButtons.ImageStream")));
        this.imageListProcButtons.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListProcButtons.Images.SetKeyName(0, "deposit.gif");
        //
        // listButtonCats
        //
        this.listButtonCats.IntegralHeight = false;
        this.listButtonCats.Location = new System.Drawing.Point(187, 40);
        this.listButtonCats.MultiColumn = true;
        this.listButtonCats.Name = "listButtonCats";
        this.listButtonCats.Size = new System.Drawing.Size(122, 192);
        this.listButtonCats.TabIndex = 59;
        this.listButtonCats.Click += new System.EventHandler(this.listButtonCats_Click);
        //
        // comboPriority
        //
        this.comboPriority.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriority.Location = new System.Drawing.Point(91, 211);
        this.comboPriority.MaxDropDownItems = 40;
        this.comboPriority.Name = "comboPriority";
        this.comboPriority.Size = new System.Drawing.Size(96, 21);
        this.comboPriority.TabIndex = 54;
        //
        // checkToday
        //
        this.checkToday.Checked = true;
        this.checkToday.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkToday.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkToday.Location = new System.Drawing.Point(1, 194);
        this.checkToday.Name = "checkToday";
        this.checkToday.Size = new System.Drawing.Size(80, 18);
        this.checkToday.TabIndex = 58;
        this.checkToday.Text = "Today";
        this.checkToday.CheckedChanged += new System.EventHandler(this.checkToday_CheckedChanged);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(89, 192);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(79, 17);
        this.label6.TabIndex = 57;
        this.label6.Text = "Priority";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textProcCode
        //
        this.textProcCode.Location = new System.Drawing.Point(330, 3);
        this.textProcCode.Name = "textProcCode";
        this.textProcCode.Size = new System.Drawing.Size(108, 20);
        this.textProcCode.TabIndex = 50;
        this.textProcCode.Text = "Type Proc Code";
        this.textProcCode.TextChanged += new System.EventHandler(this.textProcCode_TextChanged);
        this.textProcCode.Enter += new System.EventHandler(this.textProcCode_Enter);
        this.textProcCode.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textProcCode_KeyDown);
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(282, 5);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(45, 17);
        this.label14.TabIndex = 51;
        this.label14.Text = "Or";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(308, 21);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(128, 18);
        this.label13.TabIndex = 49;
        this.label13.Text = "Or Single Click:";
        this.label13.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkAudit
        //
        this.checkAudit.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkAudit.Location = new System.Drawing.Point(154, 170);
        this.checkAudit.Name = "checkAudit";
        this.checkAudit.Size = new System.Drawing.Size(73, 13);
        this.checkAudit.TabIndex = 17;
        this.checkAudit.Text = "Audit";
        this.checkAudit.Click += new System.EventHandler(this.checkAudit_Click);
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
        // checkShowTeeth
        //
        this.checkShowTeeth.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowTeeth.Location = new System.Drawing.Point(154, 154);
        this.checkShowTeeth.Name = "checkShowTeeth";
        this.checkShowTeeth.Size = new System.Drawing.Size(104, 13);
        this.checkShowTeeth.TabIndex = 15;
        this.checkShowTeeth.Text = "Selected Teeth";
        this.checkShowTeeth.Click += new System.EventHandler(this.checkShowTeeth_Click);
        //
        // checkNotes
        //
        this.checkNotes.AllowDrop = true;
        this.checkNotes.Checked = true;
        this.checkNotes.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkNotes.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkNotes.Location = new System.Drawing.Point(15, 105);
        this.checkNotes.Name = "checkNotes";
        this.checkNotes.Size = new System.Drawing.Size(102, 13);
        this.checkNotes.TabIndex = 11;
        this.checkNotes.Text = "Proc Notes";
        this.checkNotes.Click += new System.EventHandler(this.checkNotes_Click);
        //
        // checkRx
        //
        this.checkRx.Checked = true;
        this.checkRx.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkRx.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkRx.Location = new System.Drawing.Point(10, 114);
        this.checkRx.Name = "checkRx";
        this.checkRx.Size = new System.Drawing.Size(102, 13);
        this.checkRx.TabIndex = 8;
        this.checkRx.Text = "Rx";
        this.checkRx.Click += new System.EventHandler(this.checkRx_Click);
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
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Pat.gif");
        this.imageListMain.Images.SetKeyName(1, "Rx.gif");
        this.imageListMain.Images.SetKeyName(2, "Probe.gif");
        this.imageListMain.Images.SetKeyName(3, "Anesth.gif");
        this.imageListMain.Images.SetKeyName(4, "commlog.gif");
        //
        // menuProgRight
        //
        this.menuProgRight.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemDelete, this.menuItemSetComplete, this.menuItemEditSelected, this.menuItemGroupSelected, this.menuItemPrintProg, this.menuItemPrintDay, this.menuItemLabFeeDetach, this.menuItemLabFee });
        //
        // menuItemDelete
        //
        this.menuItemDelete.Index = 0;
        this.menuItemDelete.Text = "Delete";
        this.menuItemDelete.Click += new System.EventHandler(this.menuItemDelete_Click);
        //
        // menuItemSetComplete
        //
        this.menuItemSetComplete.Index = 1;
        this.menuItemSetComplete.Text = "Set Complete";
        this.menuItemSetComplete.Click += new System.EventHandler(this.menuItemSetComplete_Click);
        //
        // menuItemEditSelected
        //
        this.menuItemEditSelected.Index = 2;
        this.menuItemEditSelected.Text = "Edit All";
        this.menuItemEditSelected.Click += new System.EventHandler(this.menuItemEditSelected_Click);
        //
        // menuItemGroupSelected
        //
        this.menuItemGroupSelected.Index = 3;
        this.menuItemGroupSelected.Text = "Group Note";
        this.menuItemGroupSelected.Click += new System.EventHandler(this.menuItemGroupSelected_Click);
        //
        // menuItemPrintProg
        //
        this.menuItemPrintProg.Index = 4;
        this.menuItemPrintProg.Text = "Print Progress Notes ...";
        this.menuItemPrintProg.Click += new System.EventHandler(this.menuItemPrintProg_Click);
        //
        // menuItemPrintDay
        //
        this.menuItemPrintDay.Index = 5;
        this.menuItemPrintDay.Text = "Print Day for Hospital";
        this.menuItemPrintDay.Click += new System.EventHandler(this.menuItemPrintDay_Click);
        //
        // menuItemLabFeeDetach
        //
        this.menuItemLabFeeDetach.Index = 6;
        this.menuItemLabFeeDetach.Text = "Detach Lab Fee";
        this.menuItemLabFeeDetach.Click += new System.EventHandler(this.menuItemLabFeeDetach_Click);
        //
        // menuItemLabFee
        //
        this.menuItemLabFee.Index = 7;
        this.menuItemLabFee.Text = "Attach Lab Fee";
        this.menuItemLabFee.Click += new System.EventHandler(this.menuItemLabFee_Click);
        //
        // tabControlImages
        //
        this.tabControlImages.Alignment = System.Windows.Forms.TabAlignment.Bottom;
        this.tabControlImages.Controls.Add(this.tabPage1);
        this.tabControlImages.Controls.Add(this.tabPage2);
        this.tabControlImages.Controls.Add(this.tabPage4);
        this.tabControlImages.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.tabControlImages.DrawMode = System.Windows.Forms.TabDrawMode.OwnerDrawFixed;
        this.tabControlImages.ItemSize = new System.Drawing.Size(42, 22);
        this.tabControlImages.Location = new System.Drawing.Point(0, 681);
        this.tabControlImages.Name = "tabControlImages";
        this.tabControlImages.SelectedIndex = 0;
        this.tabControlImages.Size = new System.Drawing.Size(939, 27);
        this.tabControlImages.TabIndex = 185;
        this.tabControlImages.MouseDown += new System.Windows.Forms.MouseEventHandler(this.tabControlImages_MouseDown);
        //
        // tabPage1
        //
        this.tabPage1.Location = new System.Drawing.Point(4, 4);
        this.tabPage1.Name = "tabPage1";
        this.tabPage1.Size = new System.Drawing.Size(931, 0);
        this.tabPage1.TabIndex = 0;
        this.tabPage1.Text = "BW\'s";
        //
        // tabPage2
        //
        this.tabPage2.Location = new System.Drawing.Point(4, 4);
        this.tabPage2.Name = "tabPage2";
        this.tabPage2.Size = new System.Drawing.Size(931, 0);
        this.tabPage2.TabIndex = 1;
        this.tabPage2.Text = "Pano";
        //
        // tabPage4
        //
        this.tabPage4.Location = new System.Drawing.Point(4, 4);
        this.tabPage4.Name = "tabPage4";
        this.tabPage4.Size = new System.Drawing.Size(931, 0);
        this.tabPage4.TabIndex = 3;
        this.tabPage4.Text = "tabPage4";
        //
        // panelImages
        //
        this.panelImages.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panelImages.Controls.Add(this.listViewImages);
        this.panelImages.Dock = System.Windows.Forms.DockStyle.Bottom;
        this.panelImages.ForeColor = System.Drawing.SystemColors.ControlText;
        this.panelImages.Location = new System.Drawing.Point(0, 592);
        this.panelImages.Name = "panelImages";
        this.panelImages.Padding = new System.Windows.Forms.Padding(0, 4, 0, 0);
        this.panelImages.Size = new System.Drawing.Size(939, 89);
        this.panelImages.TabIndex = 186;
        this.panelImages.Visible = false;
        this.panelImages.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panelImages_MouseDown);
        this.panelImages.MouseLeave += new System.EventHandler(this.panelImages_MouseLeave);
        this.panelImages.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panelImages_MouseMove);
        this.panelImages.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panelImages_MouseUp);
        //
        // listViewImages
        //
        this.listViewImages.Activation = System.Windows.Forms.ItemActivation.TwoClick;
        this.listViewImages.Dock = System.Windows.Forms.DockStyle.Fill;
        this.listViewImages.HideSelection = false;
        this.listViewImages.LabelWrap = false;
        this.listViewImages.LargeImageList = this.imageListThumbnails;
        this.listViewImages.Location = new System.Drawing.Point(0, 4);
        this.listViewImages.MultiSelect = false;
        this.listViewImages.Name = "listViewImages";
        this.listViewImages.Size = new System.Drawing.Size(937, 83);
        this.listViewImages.TabIndex = 0;
        this.listViewImages.UseCompatibleStateImageBehavior = false;
        this.listViewImages.DoubleClick += new System.EventHandler(this.listViewImages_DoubleClick);
        //
        // imageListThumbnails
        //
        this.imageListThumbnails.ColorDepth = System.Windows.Forms.ColorDepth.Depth32Bit;
        this.imageListThumbnails.ImageSize = new System.Drawing.Size(100, 100);
        this.imageListThumbnails.TransparentColor = System.Drawing.Color.Transparent;
        //
        // pd2
        //
        this.pd2.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.pd2_PrintPage);
        //
        // tabProc
        //
        this.tabProc.Controls.Add(this.tabEnterTx);
        this.tabProc.Controls.Add(this.tabMissing);
        this.tabProc.Controls.Add(this.tabMovements);
        this.tabProc.Controls.Add(this.tabPrimary);
        this.tabProc.Controls.Add(this.tabPlanned);
        this.tabProc.Controls.Add(this.tabShow);
        this.tabProc.Controls.Add(this.tabDraw);
        this.tabProc.Controls.Add(this.tabPatInfo);
        this.tabProc.Controls.Add(this.tabCustomer);
        this.tabProc.Location = new System.Drawing.Point(415, 28);
        this.tabProc.Name = "tabProc";
        this.tabProc.SelectedIndex = 0;
        this.tabProc.Size = new System.Drawing.Size(524, 259);
        this.tabProc.TabIndex = 190;
        this.tabProc.MouseDown += new System.Windows.Forms.MouseEventHandler(this.tabProc_MouseDown);
        //
        // tabEnterTx
        //
        this.tabEnterTx.Controls.Add(this.comboPrognosis);
        this.tabEnterTx.Controls.Add(this.labelPrognosis);
        this.tabEnterTx.Controls.Add(this.panelQuickButtons);
        this.tabEnterTx.Controls.Add(this.listDx);
        this.tabEnterTx.Controls.Add(this.listViewButtons);
        this.tabEnterTx.Controls.Add(this.groupBox2);
        this.tabEnterTx.Controls.Add(this.listButtonCats);
        this.tabEnterTx.Controls.Add(this.butD);
        this.tabEnterTx.Controls.Add(this.comboPriority);
        this.tabEnterTx.Controls.Add(this.textSurf);
        this.tabEnterTx.Controls.Add(this.textDate);
        this.tabEnterTx.Controls.Add(this.butBF);
        this.tabEnterTx.Controls.Add(this.checkToday);
        this.tabEnterTx.Controls.Add(this.butL);
        this.tabEnterTx.Controls.Add(this.label6);
        this.tabEnterTx.Controls.Add(this.butM);
        this.tabEnterTx.Controls.Add(this.butOK);
        this.tabEnterTx.Controls.Add(this.butAddProc);
        this.tabEnterTx.Controls.Add(this.butV);
        this.tabEnterTx.Controls.Add(this.textProcCode);
        this.tabEnterTx.Controls.Add(this.butOI);
        this.tabEnterTx.Controls.Add(this.label14);
        this.tabEnterTx.Controls.Add(this.labelDx);
        this.tabEnterTx.Controls.Add(this.label13);
        this.tabEnterTx.Location = new System.Drawing.Point(4, 22);
        this.tabEnterTx.Name = "tabEnterTx";
        this.tabEnterTx.Padding = new System.Windows.Forms.Padding(3);
        this.tabEnterTx.Size = new System.Drawing.Size(516, 233);
        this.tabEnterTx.TabIndex = 0;
        this.tabEnterTx.Text = "Enter Treatment";
        this.tabEnterTx.UseVisualStyleBackColor = true;
        //
        // comboPrognosis
        //
        this.comboPrognosis.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPrognosis.Location = new System.Drawing.Point(91, 174);
        this.comboPrognosis.MaxDropDownItems = 40;
        this.comboPrognosis.Name = "comboPrognosis";
        this.comboPrognosis.Size = new System.Drawing.Size(96, 21);
        this.comboPrognosis.TabIndex = 199;
        //
        // labelPrognosis
        //
        this.labelPrognosis.Location = new System.Drawing.Point(89, 155);
        this.labelPrognosis.Name = "labelPrognosis";
        this.labelPrognosis.Size = new System.Drawing.Size(79, 17);
        this.labelPrognosis.TabIndex = 200;
        this.labelPrognosis.Text = "Prognosis";
        this.labelPrognosis.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // panelQuickButtons
        //
        this.panelQuickButtons.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
        this.panelQuickButtons.Controls.Add(this.panelQuickPasteAmalgam);
        this.panelQuickButtons.Controls.Add(this.buttonCMODB);
        this.panelQuickButtons.Controls.Add(this.buttonCMODL);
        this.panelQuickButtons.Controls.Add(this.label1);
        this.panelQuickButtons.Controls.Add(this.butCMDL);
        this.panelQuickButtons.Controls.Add(this.butML);
        this.panelQuickButtons.Controls.Add(this.butDL);
        this.panelQuickButtons.Controls.Add(this.butCOB);
        this.panelQuickButtons.Controls.Add(this.butCOL);
        this.panelQuickButtons.Controls.Add(this.buttonCSeal);
        this.panelQuickButtons.Controls.Add(this.buttonCMO);
        this.panelQuickButtons.Controls.Add(this.buttonCMOD);
        this.panelQuickButtons.Controls.Add(this.buttonCO);
        this.panelQuickButtons.Controls.Add(this.label23);
        this.panelQuickButtons.Controls.Add(this.buttonCDO);
        this.panelQuickButtons.Location = new System.Drawing.Point(311, 41);
        this.panelQuickButtons.Name = "panelQuickButtons";
        this.panelQuickButtons.Size = new System.Drawing.Size(175, 191);
        this.panelQuickButtons.TabIndex = 198;
        this.panelQuickButtons.Visible = false;
        this.panelQuickButtons.Paint += new System.Windows.Forms.PaintEventHandler(this.panelQuickButtons_Paint);
        //
        // panelQuickPasteAmalgam
        //
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAMODB);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAMODL);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAOB);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAOL);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAO);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAMO);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonAMOD);
        this.panelQuickPasteAmalgam.Controls.Add(this.buttonADO);
        this.panelQuickPasteAmalgam.Controls.Add(this.label24);
        this.panelQuickPasteAmalgam.Location = new System.Drawing.Point(0, 106);
        this.panelQuickPasteAmalgam.Name = "panelQuickPasteAmalgam";
        this.panelQuickPasteAmalgam.Size = new System.Drawing.Size(173, 80);
        this.panelQuickPasteAmalgam.TabIndex = 221;
        //
        // buttonAMODB
        //
        this.buttonAMODB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAMODB.setAutosize(true);
        this.buttonAMODB.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAMODB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAMODB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAMODB.setCornerRadius(4F);
        this.buttonAMODB.Location = new System.Drawing.Point(94, 37);
        this.buttonAMODB.Name = "buttonAMODB";
        this.buttonAMODB.Size = new System.Drawing.Size(43, 18);
        this.buttonAMODB.TabIndex = 227;
        this.buttonAMODB.Text = "MODB";
        this.buttonAMODB.UseVisualStyleBackColor = false;
        this.buttonAMODB.Click += new System.EventHandler(this.buttonAMODB_Click);
        //
        // buttonAMODL
        //
        this.buttonAMODL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAMODL.setAutosize(true);
        this.buttonAMODL.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAMODL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAMODL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAMODL.setCornerRadius(4F);
        this.buttonAMODL.Location = new System.Drawing.Point(52, 37);
        this.buttonAMODL.Name = "buttonAMODL";
        this.buttonAMODL.Size = new System.Drawing.Size(42, 18);
        this.buttonAMODL.TabIndex = 226;
        this.buttonAMODL.Text = "MODL";
        this.buttonAMODL.UseVisualStyleBackColor = false;
        this.buttonAMODL.Click += new System.EventHandler(this.buttonAMODL_Click);
        //
        // buttonAOB
        //
        this.buttonAOB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAOB.setAutosize(true);
        this.buttonAOB.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAOB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAOB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAOB.setCornerRadius(4F);
        this.buttonAOB.Location = new System.Drawing.Point(26, 37);
        this.buttonAOB.Name = "buttonAOB";
        this.buttonAOB.Size = new System.Drawing.Size(26, 18);
        this.buttonAOB.TabIndex = 225;
        this.buttonAOB.Text = "OB";
        this.buttonAOB.UseVisualStyleBackColor = false;
        this.buttonAOB.Click += new System.EventHandler(this.buttonAOB_Click);
        //
        // buttonAOL
        //
        this.buttonAOL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAOL.setAutosize(true);
        this.buttonAOL.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAOL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAOL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAOL.setCornerRadius(4F);
        this.buttonAOL.Location = new System.Drawing.Point(0, 37);
        this.buttonAOL.Name = "buttonAOL";
        this.buttonAOL.Size = new System.Drawing.Size(26, 18);
        this.buttonAOL.TabIndex = 224;
        this.buttonAOL.Text = "OL";
        this.buttonAOL.UseVisualStyleBackColor = false;
        this.buttonAOL.Click += new System.EventHandler(this.buttonAOL_Click);
        //
        // buttonAO
        //
        this.buttonAO.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAO.setAutosize(true);
        this.buttonAO.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAO.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAO.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAO.setCornerRadius(4F);
        this.buttonAO.Location = new System.Drawing.Point(62, 19);
        this.buttonAO.Name = "buttonAO";
        this.buttonAO.Size = new System.Drawing.Size(18, 18);
        this.buttonAO.TabIndex = 223;
        this.buttonAO.Text = "O";
        this.buttonAO.UseVisualStyleBackColor = false;
        this.buttonAO.Click += new System.EventHandler(this.buttonAO_Click);
        //
        // buttonAMO
        //
        this.buttonAMO.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAMO.setAutosize(true);
        this.buttonAMO.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAMO.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAMO.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAMO.setCornerRadius(4F);
        this.buttonAMO.Location = new System.Drawing.Point(0, 19);
        this.buttonAMO.Name = "buttonAMO";
        this.buttonAMO.Size = new System.Drawing.Size(27, 18);
        this.buttonAMO.TabIndex = 222;
        this.buttonAMO.Text = "MO";
        this.buttonAMO.UseVisualStyleBackColor = false;
        this.buttonAMO.Click += new System.EventHandler(this.buttonAMO_Click);
        //
        // buttonAMOD
        //
        this.buttonAMOD.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonAMOD.setAutosize(true);
        this.buttonAMOD.BackColor = System.Drawing.SystemColors.Control;
        this.buttonAMOD.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonAMOD.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonAMOD.setCornerRadius(4F);
        this.buttonAMOD.Location = new System.Drawing.Point(27, 19);
        this.buttonAMOD.Name = "buttonAMOD";
        this.buttonAMOD.Size = new System.Drawing.Size(36, 18);
        this.buttonAMOD.TabIndex = 221;
        this.buttonAMOD.Text = "MOD";
        this.buttonAMOD.UseVisualStyleBackColor = false;
        this.buttonAMOD.Click += new System.EventHandler(this.buttonAMOD_Click);
        //
        // buttonADO
        //
        this.buttonADO.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonADO.setAutosize(true);
        this.buttonADO.BackColor = System.Drawing.SystemColors.Control;
        this.buttonADO.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonADO.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonADO.setCornerRadius(4F);
        this.buttonADO.Location = new System.Drawing.Point(80, 19);
        this.buttonADO.Name = "buttonADO";
        this.buttonADO.Size = new System.Drawing.Size(26, 18);
        this.buttonADO.TabIndex = 220;
        this.buttonADO.Text = "DO";
        this.buttonADO.UseVisualStyleBackColor = false;
        this.buttonADO.Click += new System.EventHandler(this.buttonADO_Click);
        //
        // label24
        //
        this.label24.Location = new System.Drawing.Point(4, 2);
        this.label24.Name = "label24";
        this.label24.Size = new System.Drawing.Size(56, 13);
        this.label24.TabIndex = 219;
        this.label24.Text = "Amalgam";
        //
        // buttonCMODB
        //
        this.buttonCMODB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCMODB.setAutosize(true);
        this.buttonCMODB.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCMODB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCMODB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCMODB.setCornerRadius(4F);
        this.buttonCMODB.Location = new System.Drawing.Point(93, 35);
        this.buttonCMODB.Name = "buttonCMODB";
        this.buttonCMODB.Size = new System.Drawing.Size(43, 18);
        this.buttonCMODB.TabIndex = 220;
        this.buttonCMODB.Text = "MODB";
        this.buttonCMODB.UseVisualStyleBackColor = false;
        this.buttonCMODB.Click += new System.EventHandler(this.buttonCMODB_Click);
        //
        // buttonCMODL
        //
        this.buttonCMODL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCMODL.setAutosize(true);
        this.buttonCMODL.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCMODL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCMODL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCMODL.setCornerRadius(4F);
        this.buttonCMODL.Location = new System.Drawing.Point(51, 35);
        this.buttonCMODL.Name = "buttonCMODL";
        this.buttonCMODL.Size = new System.Drawing.Size(42, 18);
        this.buttonCMODL.TabIndex = 219;
        this.buttonCMODL.Text = "MODL";
        this.buttonCMODL.UseVisualStyleBackColor = false;
        this.buttonCMODL.Click += new System.EventHandler(this.buttonCMODL_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(3, 65);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(84, 13);
        this.label1.TabIndex = 214;
        this.label1.Text = "Ant. Composite";
        //
        // butCMDL
        //
        this.butCMDL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCMDL.setAutosize(true);
        this.butCMDL.BackColor = System.Drawing.SystemColors.Control;
        this.butCMDL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCMDL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCMDL.setCornerRadius(4F);
        this.butCMDL.Location = new System.Drawing.Point(26, 82);
        this.butCMDL.Name = "butCMDL";
        this.butCMDL.Size = new System.Drawing.Size(33, 18);
        this.butCMDL.TabIndex = 213;
        this.butCMDL.Text = "MDL";
        this.butCMDL.UseVisualStyleBackColor = false;
        this.butCMDL.Click += new System.EventHandler(this.butCMDL_Click);
        //
        // butML
        //
        this.butML.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butML.setAutosize(true);
        this.butML.BackColor = System.Drawing.SystemColors.Control;
        this.butML.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butML.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butML.setCornerRadius(4F);
        this.butML.Location = new System.Drawing.Point(59, 82);
        this.butML.Name = "butML";
        this.butML.Size = new System.Drawing.Size(26, 18);
        this.butML.TabIndex = 212;
        this.butML.Text = "ML";
        this.butML.UseVisualStyleBackColor = false;
        this.butML.Click += new System.EventHandler(this.butML_Click);
        //
        // butDL
        //
        this.butDL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDL.setAutosize(true);
        this.butDL.BackColor = System.Drawing.SystemColors.Control;
        this.butDL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDL.setCornerRadius(4F);
        this.butDL.Location = new System.Drawing.Point(0, 82);
        this.butDL.Name = "butDL";
        this.butDL.Size = new System.Drawing.Size(26, 18);
        this.butDL.TabIndex = 211;
        this.butDL.Text = "DL";
        this.butDL.UseVisualStyleBackColor = false;
        this.butDL.Click += new System.EventHandler(this.butDL_Click);
        //
        // butCOB
        //
        this.butCOB.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCOB.setAutosize(true);
        this.butCOB.BackColor = System.Drawing.SystemColors.Control;
        this.butCOB.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCOB.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCOB.setCornerRadius(4F);
        this.butCOB.Location = new System.Drawing.Point(26, 35);
        this.butCOB.Name = "butCOB";
        this.butCOB.Size = new System.Drawing.Size(26, 18);
        this.butCOB.TabIndex = 210;
        this.butCOB.Text = "OB";
        this.butCOB.UseVisualStyleBackColor = false;
        this.butCOB.Click += new System.EventHandler(this.butCOB_Click);
        //
        // butCOL
        //
        this.butCOL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCOL.setAutosize(true);
        this.butCOL.BackColor = System.Drawing.SystemColors.Control;
        this.butCOL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCOL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCOL.setCornerRadius(4F);
        this.butCOL.Location = new System.Drawing.Point(0, 35);
        this.butCOL.Name = "butCOL";
        this.butCOL.Size = new System.Drawing.Size(26, 18);
        this.butCOL.TabIndex = 209;
        this.butCOL.Text = "OL";
        this.butCOL.UseVisualStyleBackColor = false;
        this.butCOL.Click += new System.EventHandler(this.butCOL_Click);
        //
        // buttonCSeal
        //
        this.buttonCSeal.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCSeal.setAutosize(true);
        this.buttonCSeal.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCSeal.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCSeal.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCSeal.setCornerRadius(4F);
        this.buttonCSeal.Location = new System.Drawing.Point(136, 17);
        this.buttonCSeal.Name = "buttonCSeal";
        this.buttonCSeal.Size = new System.Drawing.Size(32, 18);
        this.buttonCSeal.TabIndex = 203;
        this.buttonCSeal.Text = "Seal";
        this.buttonCSeal.UseVisualStyleBackColor = false;
        this.buttonCSeal.Click += new System.EventHandler(this.buttonCSeal_Click);
        //
        // buttonCMO
        //
        this.buttonCMO.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCMO.setAutosize(true);
        this.buttonCMO.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCMO.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCMO.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCMO.setCornerRadius(4F);
        this.buttonCMO.Location = new System.Drawing.Point(0, 17);
        this.buttonCMO.Name = "buttonCMO";
        this.buttonCMO.Size = new System.Drawing.Size(27, 18);
        this.buttonCMO.TabIndex = 202;
        this.buttonCMO.Text = "MO";
        this.buttonCMO.UseVisualStyleBackColor = false;
        this.buttonCMO.Click += new System.EventHandler(this.buttonCMO_Click);
        //
        // buttonCMOD
        //
        this.buttonCMOD.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCMOD.setAutosize(true);
        this.buttonCMOD.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCMOD.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCMOD.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCMOD.setCornerRadius(4F);
        this.buttonCMOD.Location = new System.Drawing.Point(26, 17);
        this.buttonCMOD.Name = "buttonCMOD";
        this.buttonCMOD.Size = new System.Drawing.Size(36, 18);
        this.buttonCMOD.TabIndex = 201;
        this.buttonCMOD.Text = "MOD";
        this.buttonCMOD.UseVisualStyleBackColor = false;
        this.buttonCMOD.Click += new System.EventHandler(this.buttonCMOD_Click);
        //
        // buttonCO
        //
        this.buttonCO.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCO.setAutosize(true);
        this.buttonCO.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCO.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCO.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCO.setCornerRadius(4F);
        this.buttonCO.Location = new System.Drawing.Point(62, 17);
        this.buttonCO.Name = "buttonCO";
        this.buttonCO.Size = new System.Drawing.Size(18, 18);
        this.buttonCO.TabIndex = 200;
        this.buttonCO.Text = "O";
        this.buttonCO.UseVisualStyleBackColor = false;
        this.buttonCO.Click += new System.EventHandler(this.buttonCO_Click);
        //
        // label23
        //
        this.label23.Location = new System.Drawing.Point(4, 1);
        this.label23.Name = "label23";
        this.label23.Size = new System.Drawing.Size(88, 13);
        this.label23.TabIndex = 198;
        this.label23.Text = "Post. Composite";
        //
        // buttonCDO
        //
        this.buttonCDO.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonCDO.setAutosize(true);
        this.buttonCDO.BackColor = System.Drawing.SystemColors.Control;
        this.buttonCDO.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonCDO.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonCDO.setCornerRadius(4F);
        this.buttonCDO.Location = new System.Drawing.Point(80, 17);
        this.buttonCDO.Name = "buttonCDO";
        this.buttonCDO.Size = new System.Drawing.Size(26, 18);
        this.buttonCDO.TabIndex = 197;
        this.buttonCDO.Text = "DO";
        this.buttonCDO.UseVisualStyleBackColor = false;
        this.buttonCDO.Click += new System.EventHandler(this.buttonCDO_Click);
        //
        // butD
        //
        this.butD.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butD.setAutosize(true);
        this.butD.BackColor = System.Drawing.SystemColors.Control;
        this.butD.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butD.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butD.setCornerRadius(4F);
        this.butD.Location = new System.Drawing.Point(61, 43);
        this.butD.Name = "butD";
        this.butD.Size = new System.Drawing.Size(24, 20);
        this.butD.TabIndex = 20;
        this.butD.Text = "D";
        this.butD.UseVisualStyleBackColor = false;
        this.butD.Click += new System.EventHandler(this.butD_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(0, 211);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(89, 20);
        this.textDate.TabIndex = 55;
        //
        // butBF
        //
        this.butBF.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBF.setAutosize(true);
        this.butBF.BackColor = System.Drawing.SystemColors.Control;
        this.butBF.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBF.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBF.setCornerRadius(4F);
        this.butBF.Location = new System.Drawing.Point(22, 23);
        this.butBF.Name = "butBF";
        this.butBF.Size = new System.Drawing.Size(28, 20);
        this.butBF.TabIndex = 21;
        this.butBF.Text = "B/F";
        this.butBF.UseVisualStyleBackColor = false;
        this.butBF.Click += new System.EventHandler(this.butBF_Click);
        //
        // butL
        //
        this.butL.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butL.setAutosize(true);
        this.butL.BackColor = System.Drawing.SystemColors.Control;
        this.butL.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butL.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butL.setCornerRadius(4F);
        this.butL.Location = new System.Drawing.Point(32, 63);
        this.butL.Name = "butL";
        this.butL.Size = new System.Drawing.Size(24, 20);
        this.butL.TabIndex = 22;
        this.butL.Text = "L";
        this.butL.UseVisualStyleBackColor = false;
        this.butL.Click += new System.EventHandler(this.butL_Click);
        //
        // butM
        //
        this.butM.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butM.setAutosize(true);
        this.butM.BackColor = System.Drawing.SystemColors.Control;
        this.butM.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butM.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butM.setCornerRadius(4F);
        this.butM.Location = new System.Drawing.Point(3, 43);
        this.butM.Name = "butM";
        this.butM.Size = new System.Drawing.Size(24, 20);
        this.butM.TabIndex = 18;
        this.butM.Text = "M";
        this.butM.UseVisualStyleBackColor = false;
        this.butM.Click += new System.EventHandler(this.butM_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(442, 1);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(44, 23);
        this.butOK.TabIndex = 52;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butAddProc
        //
        this.butAddProc.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddProc.setAutosize(true);
        this.butAddProc.BackColor = System.Drawing.SystemColors.Control;
        this.butAddProc.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddProc.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddProc.setCornerRadius(4F);
        this.butAddProc.Location = new System.Drawing.Point(191, 1);
        this.butAddProc.Name = "butAddProc";
        this.butAddProc.Size = new System.Drawing.Size(89, 23);
        this.butAddProc.TabIndex = 17;
        this.butAddProc.Text = "Procedure List";
        this.butAddProc.UseVisualStyleBackColor = false;
        this.butAddProc.Click += new System.EventHandler(this.butAddProc_Click);
        //
        // butV
        //
        this.butV.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butV.setAutosize(true);
        this.butV.BackColor = System.Drawing.SystemColors.Control;
        this.butV.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butV.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butV.setCornerRadius(4F);
        this.butV.Location = new System.Drawing.Point(50, 23);
        this.butV.Name = "butV";
        this.butV.Size = new System.Drawing.Size(17, 20);
        this.butV.TabIndex = 24;
        this.butV.Text = "V";
        this.butV.UseVisualStyleBackColor = false;
        this.butV.Click += new System.EventHandler(this.butV_Click);
        //
        // butOI
        //
        this.butOI.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOI.setAutosize(true);
        this.butOI.BackColor = System.Drawing.SystemColors.Control;
        this.butOI.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOI.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOI.setCornerRadius(4F);
        this.butOI.Location = new System.Drawing.Point(27, 43);
        this.butOI.Name = "butOI";
        this.butOI.Size = new System.Drawing.Size(34, 20);
        this.butOI.TabIndex = 19;
        this.butOI.Text = "O/I";
        this.butOI.UseVisualStyleBackColor = false;
        this.butOI.Click += new System.EventHandler(this.butOI_Click);
        //
        // tabMissing
        //
        this.tabMissing.Controls.Add(this.butUnhide);
        this.tabMissing.Controls.Add(this.label5);
        this.tabMissing.Controls.Add(this.listHidden);
        this.tabMissing.Controls.Add(this.butEdentulous);
        this.tabMissing.Controls.Add(this.groupBox1);
        this.tabMissing.Location = new System.Drawing.Point(4, 22);
        this.tabMissing.Name = "tabMissing";
        this.tabMissing.Padding = new System.Windows.Forms.Padding(3);
        this.tabMissing.Size = new System.Drawing.Size(516, 233);
        this.tabMissing.TabIndex = 1;
        this.tabMissing.Text = "Missing Teeth";
        this.tabMissing.UseVisualStyleBackColor = true;
        //
        // butUnhide
        //
        this.butUnhide.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUnhide.setAutosize(true);
        this.butUnhide.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUnhide.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUnhide.setCornerRadius(4F);
        this.butUnhide.Location = new System.Drawing.Point(307, 113);
        this.butUnhide.Name = "butUnhide";
        this.butUnhide.Size = new System.Drawing.Size(71, 23);
        this.butUnhide.TabIndex = 20;
        this.butUnhide.Text = "Unhide";
        this.butUnhide.Click += new System.EventHandler(this.butUnhide_Click);
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(304, 12);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(147, 17);
        this.label5.TabIndex = 19;
        this.label5.Text = "Hidden Teeth";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listHidden
        //
        this.listHidden.FormattingEnabled = true;
        this.listHidden.Location = new System.Drawing.Point(307, 33);
        this.listHidden.Name = "listHidden";
        this.listHidden.Size = new System.Drawing.Size(94, 69);
        this.listHidden.TabIndex = 18;
        //
        // butEdentulous
        //
        this.butEdentulous.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEdentulous.setAutosize(true);
        this.butEdentulous.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEdentulous.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEdentulous.setCornerRadius(4F);
        this.butEdentulous.Location = new System.Drawing.Point(31, 113);
        this.butEdentulous.Name = "butEdentulous";
        this.butEdentulous.Size = new System.Drawing.Size(82, 23);
        this.butEdentulous.TabIndex = 16;
        this.butEdentulous.Text = "Edentulous";
        this.butEdentulous.Click += new System.EventHandler(this.butEdentulous_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label7);
        this.groupBox1.Controls.Add(this.butNotMissing);
        this.groupBox1.Controls.Add(this.butMissing);
        this.groupBox1.Controls.Add(this.butHidden);
        this.groupBox1.Location = new System.Drawing.Point(20, 12);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(267, 90);
        this.groupBox1.TabIndex = 0;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Set Selected Teeth";
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(115, 46);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(146, 17);
        this.label7.TabIndex = 20;
        this.label7.Text = "(including numbers)";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butNotMissing
        //
        this.butNotMissing.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNotMissing.setAutosize(true);
        this.butNotMissing.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNotMissing.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNotMissing.setCornerRadius(4F);
        this.butNotMissing.Location = new System.Drawing.Point(11, 53);
        this.butNotMissing.Name = "butNotMissing";
        this.butNotMissing.Size = new System.Drawing.Size(82, 23);
        this.butNotMissing.TabIndex = 15;
        this.butNotMissing.Text = "Not Missing";
        this.butNotMissing.Click += new System.EventHandler(this.butNotMissing_Click);
        //
        // butMissing
        //
        this.butMissing.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMissing.setAutosize(true);
        this.butMissing.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMissing.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMissing.setCornerRadius(4F);
        this.butMissing.Location = new System.Drawing.Point(11, 21);
        this.butMissing.Name = "butMissing";
        this.butMissing.Size = new System.Drawing.Size(82, 23);
        this.butMissing.TabIndex = 14;
        this.butMissing.Text = "Missing";
        this.butMissing.Click += new System.EventHandler(this.butMissing_Click);
        //
        // butHidden
        //
        this.butHidden.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHidden.setAutosize(true);
        this.butHidden.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHidden.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHidden.setCornerRadius(4F);
        this.butHidden.Location = new System.Drawing.Point(172, 21);
        this.butHidden.Name = "butHidden";
        this.butHidden.Size = new System.Drawing.Size(82, 23);
        this.butHidden.TabIndex = 17;
        this.butHidden.Text = "Hidden";
        this.butHidden.Click += new System.EventHandler(this.butHidden_Click);
        //
        // tabMovements
        //
        this.tabMovements.Controls.Add(this.label16);
        this.tabMovements.Controls.Add(this.butApplyMovements);
        this.tabMovements.Controls.Add(this.groupBox4);
        this.tabMovements.Controls.Add(this.groupBox3);
        this.tabMovements.Location = new System.Drawing.Point(4, 22);
        this.tabMovements.Name = "tabMovements";
        this.tabMovements.Size = new System.Drawing.Size(516, 233);
        this.tabMovements.TabIndex = 3;
        this.tabMovements.Text = "Movements";
        this.tabMovements.UseVisualStyleBackColor = true;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(180, 183);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(267, 18);
        this.label16.TabIndex = 29;
        this.label16.Text = "(if you typed in changes)";
        this.label16.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butApplyMovements
        //
        this.butApplyMovements.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butApplyMovements.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butApplyMovements.setAutosize(true);
        this.butApplyMovements.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butApplyMovements.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butApplyMovements.setCornerRadius(4F);
        this.butApplyMovements.Location = new System.Drawing.Point(404, 154);
        this.butApplyMovements.Name = "butApplyMovements";
        this.butApplyMovements.Size = new System.Drawing.Size(68, 23);
        this.butApplyMovements.TabIndex = 16;
        this.butApplyMovements.Text = "Apply";
        this.butApplyMovements.Click += new System.EventHandler(this.butApplyMovements_Click);
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.butTipBplus);
        this.groupBox4.Controls.Add(this.butTipBminus);
        this.groupBox4.Controls.Add(this.butTipMplus);
        this.groupBox4.Controls.Add(this.butTipMminus);
        this.groupBox4.Controls.Add(this.butRotatePlus);
        this.groupBox4.Controls.Add(this.butRotateMinus);
        this.groupBox4.Controls.Add(this.textTipB);
        this.groupBox4.Controls.Add(this.label11);
        this.groupBox4.Controls.Add(this.textTipM);
        this.groupBox4.Controls.Add(this.label12);
        this.groupBox4.Controls.Add(this.textRotate);
        this.groupBox4.Controls.Add(this.label15);
        this.groupBox4.Location = new System.Drawing.Point(255, 12);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(207, 109);
        this.groupBox4.TabIndex = 15;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Rotate/Tip degrees";
        //
        // butTipBplus
        //
        this.butTipBplus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTipBplus.setAutosize(true);
        this.butTipBplus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTipBplus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTipBplus.setCornerRadius(4F);
        this.butTipBplus.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butTipBplus.Location = new System.Drawing.Point(159, 76);
        this.butTipBplus.Name = "butTipBplus";
        this.butTipBplus.Size = new System.Drawing.Size(31, 23);
        this.butTipBplus.TabIndex = 34;
        this.butTipBplus.Text = "+";
        this.butTipBplus.Click += new System.EventHandler(this.butTipBplus_Click);
        //
        // butTipBminus
        //
        this.butTipBminus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTipBminus.setAutosize(true);
        this.butTipBminus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTipBminus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTipBminus.setCornerRadius(4F);
        this.butTipBminus.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butTipBminus.Location = new System.Drawing.Point(122, 76);
        this.butTipBminus.Name = "butTipBminus";
        this.butTipBminus.Size = new System.Drawing.Size(31, 23);
        this.butTipBminus.TabIndex = 35;
        this.butTipBminus.Text = "-";
        this.butTipBminus.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.butTipBminus.Click += new System.EventHandler(this.butTipBminus_Click);
        //
        // butTipMplus
        //
        this.butTipMplus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTipMplus.setAutosize(true);
        this.butTipMplus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTipMplus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTipMplus.setCornerRadius(4F);
        this.butTipMplus.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butTipMplus.Location = new System.Drawing.Point(159, 47);
        this.butTipMplus.Name = "butTipMplus";
        this.butTipMplus.Size = new System.Drawing.Size(31, 23);
        this.butTipMplus.TabIndex = 32;
        this.butTipMplus.Text = "+";
        this.butTipMplus.Click += new System.EventHandler(this.butTipMplus_Click);
        //
        // butTipMminus
        //
        this.butTipMminus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTipMminus.setAutosize(true);
        this.butTipMminus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTipMminus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTipMminus.setCornerRadius(4F);
        this.butTipMminus.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butTipMminus.Location = new System.Drawing.Point(122, 47);
        this.butTipMminus.Name = "butTipMminus";
        this.butTipMminus.Size = new System.Drawing.Size(31, 23);
        this.butTipMminus.TabIndex = 33;
        this.butTipMminus.Text = "-";
        this.butTipMminus.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.butTipMminus.Click += new System.EventHandler(this.butTipMminus_Click);
        //
        // butRotatePlus
        //
        this.butRotatePlus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRotatePlus.setAutosize(true);
        this.butRotatePlus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRotatePlus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRotatePlus.setCornerRadius(4F);
        this.butRotatePlus.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butRotatePlus.Location = new System.Drawing.Point(159, 18);
        this.butRotatePlus.Name = "butRotatePlus";
        this.butRotatePlus.Size = new System.Drawing.Size(31, 23);
        this.butRotatePlus.TabIndex = 30;
        this.butRotatePlus.Text = "+";
        this.butRotatePlus.Click += new System.EventHandler(this.butRotatePlus_Click);
        //
        // butRotateMinus
        //
        this.butRotateMinus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRotateMinus.setAutosize(true);
        this.butRotateMinus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRotateMinus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRotateMinus.setCornerRadius(4F);
        this.butRotateMinus.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butRotateMinus.Location = new System.Drawing.Point(122, 18);
        this.butRotateMinus.Name = "butRotateMinus";
        this.butRotateMinus.Size = new System.Drawing.Size(31, 23);
        this.butRotateMinus.TabIndex = 31;
        this.butRotateMinus.Text = "-";
        this.butRotateMinus.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.butRotateMinus.Click += new System.EventHandler(this.butRotateMinus_Click);
        //
        // textTipB
        //
        this.textTipB.Location = new System.Drawing.Point(72, 77);
        this.textTipB.Name = "textTipB";
        this.textTipB.Size = new System.Drawing.Size(38, 20);
        this.textTipB.TabIndex = 29;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(3, 77);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(68, 18);
        this.label11.TabIndex = 28;
        this.label11.Text = "Labial Tip";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTipM
        //
        this.textTipM.Location = new System.Drawing.Point(72, 49);
        this.textTipM.Name = "textTipM";
        this.textTipM.Size = new System.Drawing.Size(38, 20);
        this.textTipM.TabIndex = 25;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(3, 49);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(68, 18);
        this.label12.TabIndex = 24;
        this.label12.Text = "Mesial Tip";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRotate
        //
        this.textRotate.Location = new System.Drawing.Point(72, 20);
        this.textRotate.Name = "textRotate";
        this.textRotate.Size = new System.Drawing.Size(38, 20);
        this.textRotate.TabIndex = 21;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(3, 20);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(68, 18);
        this.label15.TabIndex = 20;
        this.label15.Text = "Rotate";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.butShiftBplus);
        this.groupBox3.Controls.Add(this.butShiftBminus);
        this.groupBox3.Controls.Add(this.butShiftOplus);
        this.groupBox3.Controls.Add(this.butShiftOminus);
        this.groupBox3.Controls.Add(this.butShiftMplus);
        this.groupBox3.Controls.Add(this.butShiftMminus);
        this.groupBox3.Controls.Add(this.textShiftB);
        this.groupBox3.Controls.Add(this.label10);
        this.groupBox3.Controls.Add(this.textShiftO);
        this.groupBox3.Controls.Add(this.label9);
        this.groupBox3.Controls.Add(this.textShiftM);
        this.groupBox3.Controls.Add(this.label8);
        this.groupBox3.Location = new System.Drawing.Point(20, 12);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(207, 109);
        this.groupBox3.TabIndex = 0;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Shift millimeters";
        //
        // butShiftBplus
        //
        this.butShiftBplus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShiftBplus.setAutosize(true);
        this.butShiftBplus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShiftBplus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShiftBplus.setCornerRadius(4F);
        this.butShiftBplus.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butShiftBplus.Location = new System.Drawing.Point(158, 76);
        this.butShiftBplus.Name = "butShiftBplus";
        this.butShiftBplus.Size = new System.Drawing.Size(31, 23);
        this.butShiftBplus.TabIndex = 40;
        this.butShiftBplus.Text = "+";
        this.butShiftBplus.Click += new System.EventHandler(this.butShiftBplus_Click);
        //
        // butShiftBminus
        //
        this.butShiftBminus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShiftBminus.setAutosize(true);
        this.butShiftBminus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShiftBminus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShiftBminus.setCornerRadius(4F);
        this.butShiftBminus.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butShiftBminus.Location = new System.Drawing.Point(121, 76);
        this.butShiftBminus.Name = "butShiftBminus";
        this.butShiftBminus.Size = new System.Drawing.Size(31, 23);
        this.butShiftBminus.TabIndex = 41;
        this.butShiftBminus.Text = "-";
        this.butShiftBminus.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.butShiftBminus.Click += new System.EventHandler(this.butShiftBminus_Click);
        //
        // butShiftOplus
        //
        this.butShiftOplus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShiftOplus.setAutosize(true);
        this.butShiftOplus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShiftOplus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShiftOplus.setCornerRadius(4F);
        this.butShiftOplus.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butShiftOplus.Location = new System.Drawing.Point(158, 47);
        this.butShiftOplus.Name = "butShiftOplus";
        this.butShiftOplus.Size = new System.Drawing.Size(31, 23);
        this.butShiftOplus.TabIndex = 38;
        this.butShiftOplus.Text = "+";
        this.butShiftOplus.Click += new System.EventHandler(this.butShiftOplus_Click);
        //
        // butShiftOminus
        //
        this.butShiftOminus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShiftOminus.setAutosize(true);
        this.butShiftOminus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShiftOminus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShiftOminus.setCornerRadius(4F);
        this.butShiftOminus.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butShiftOminus.Location = new System.Drawing.Point(121, 47);
        this.butShiftOminus.Name = "butShiftOminus";
        this.butShiftOminus.Size = new System.Drawing.Size(31, 23);
        this.butShiftOminus.TabIndex = 39;
        this.butShiftOminus.Text = "-";
        this.butShiftOminus.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.butShiftOminus.Click += new System.EventHandler(this.butShiftOminus_Click);
        //
        // butShiftMplus
        //
        this.butShiftMplus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShiftMplus.setAutosize(true);
        this.butShiftMplus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShiftMplus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShiftMplus.setCornerRadius(4F);
        this.butShiftMplus.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butShiftMplus.Location = new System.Drawing.Point(158, 18);
        this.butShiftMplus.Name = "butShiftMplus";
        this.butShiftMplus.Size = new System.Drawing.Size(31, 23);
        this.butShiftMplus.TabIndex = 36;
        this.butShiftMplus.Text = "+";
        this.butShiftMplus.Click += new System.EventHandler(this.butShiftMplus_Click);
        //
        // butShiftMminus
        //
        this.butShiftMminus.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShiftMminus.setAutosize(true);
        this.butShiftMminus.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShiftMminus.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShiftMminus.setCornerRadius(4F);
        this.butShiftMminus.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butShiftMminus.Location = new System.Drawing.Point(121, 18);
        this.butShiftMminus.Name = "butShiftMminus";
        this.butShiftMminus.Size = new System.Drawing.Size(31, 23);
        this.butShiftMminus.TabIndex = 37;
        this.butShiftMminus.Text = "-";
        this.butShiftMminus.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.butShiftMminus.Click += new System.EventHandler(this.butShiftMminus_Click);
        //
        // textShiftB
        //
        this.textShiftB.Location = new System.Drawing.Point(72, 77);
        this.textShiftB.Name = "textShiftB";
        this.textShiftB.Size = new System.Drawing.Size(38, 20);
        this.textShiftB.TabIndex = 29;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(3, 77);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(68, 18);
        this.label10.TabIndex = 28;
        this.label10.Text = "Labial";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textShiftO
        //
        this.textShiftO.Location = new System.Drawing.Point(72, 49);
        this.textShiftO.Name = "textShiftO";
        this.textShiftO.Size = new System.Drawing.Size(38, 20);
        this.textShiftO.TabIndex = 25;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(3, 49);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(68, 18);
        this.label9.TabIndex = 24;
        this.label9.Text = "Occlusal";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textShiftM
        //
        this.textShiftM.Location = new System.Drawing.Point(72, 20);
        this.textShiftM.Name = "textShiftM";
        this.textShiftM.Size = new System.Drawing.Size(38, 20);
        this.textShiftM.TabIndex = 21;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(3, 20);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(68, 18);
        this.label8.TabIndex = 20;
        this.label8.Text = "Mesial";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // tabPrimary
        //
        this.tabPrimary.Controls.Add(this.groupBox5);
        this.tabPrimary.Controls.Add(this.butMixed);
        this.tabPrimary.Controls.Add(this.butAllPrimary);
        this.tabPrimary.Controls.Add(this.butAllPerm);
        this.tabPrimary.Location = new System.Drawing.Point(4, 22);
        this.tabPrimary.Name = "tabPrimary";
        this.tabPrimary.Size = new System.Drawing.Size(516, 233);
        this.tabPrimary.TabIndex = 2;
        this.tabPrimary.Text = "Primary";
        this.tabPrimary.UseVisualStyleBackColor = true;
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.butPerm);
        this.groupBox5.Controls.Add(this.butPrimary);
        this.groupBox5.Location = new System.Drawing.Point(20, 12);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(153, 90);
        this.groupBox5.TabIndex = 21;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Set Selected Teeth";
        //
        // butPerm
        //
        this.butPerm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPerm.setAutosize(true);
        this.butPerm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPerm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPerm.setCornerRadius(4F);
        this.butPerm.Location = new System.Drawing.Point(11, 53);
        this.butPerm.Name = "butPerm";
        this.butPerm.Size = new System.Drawing.Size(82, 23);
        this.butPerm.TabIndex = 15;
        this.butPerm.Text = "Permanent";
        this.butPerm.Click += new System.EventHandler(this.butPerm_Click);
        //
        // butPrimary
        //
        this.butPrimary.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrimary.setAutosize(true);
        this.butPrimary.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrimary.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrimary.setCornerRadius(4F);
        this.butPrimary.Location = new System.Drawing.Point(11, 21);
        this.butPrimary.Name = "butPrimary";
        this.butPrimary.Size = new System.Drawing.Size(82, 23);
        this.butPrimary.TabIndex = 14;
        this.butPrimary.Text = "Primary";
        this.butPrimary.Click += new System.EventHandler(this.butPrimary_Click);
        //
        // butMixed
        //
        this.butMixed.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMixed.setAutosize(true);
        this.butMixed.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMixed.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMixed.setCornerRadius(4F);
        this.butMixed.Location = new System.Drawing.Point(334, 33);
        this.butMixed.Name = "butMixed";
        this.butMixed.Size = new System.Drawing.Size(107, 23);
        this.butMixed.TabIndex = 20;
        this.butMixed.Text = "Set Mixed Dentition";
        this.butMixed.Click += new System.EventHandler(this.butMixed_Click);
        //
        // butAllPrimary
        //
        this.butAllPrimary.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAllPrimary.setAutosize(true);
        this.butAllPrimary.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAllPrimary.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAllPrimary.setCornerRadius(4F);
        this.butAllPrimary.Location = new System.Drawing.Point(201, 33);
        this.butAllPrimary.Name = "butAllPrimary";
        this.butAllPrimary.Size = new System.Drawing.Size(107, 23);
        this.butAllPrimary.TabIndex = 19;
        this.butAllPrimary.Text = "Set All Primary";
        this.butAllPrimary.Click += new System.EventHandler(this.butAllPrimary_Click);
        //
        // butAllPerm
        //
        this.butAllPerm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAllPerm.setAutosize(true);
        this.butAllPerm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAllPerm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAllPerm.setCornerRadius(4F);
        this.butAllPerm.Location = new System.Drawing.Point(201, 65);
        this.butAllPerm.Name = "butAllPerm";
        this.butAllPerm.Size = new System.Drawing.Size(107, 23);
        this.butAllPerm.TabIndex = 18;
        this.butAllPerm.Text = "Set All Permanent";
        this.butAllPerm.Click += new System.EventHandler(this.butAllPerm_Click);
        //
        // tabPlanned
        //
        this.tabPlanned.BackColor = System.Drawing.Color.White;
        this.tabPlanned.Controls.Add(this.butDown);
        this.tabPlanned.Controls.Add(this.butUp);
        this.tabPlanned.Controls.Add(this.butPin);
        this.tabPlanned.Controls.Add(this.butClear);
        this.tabPlanned.Controls.Add(this.butNew);
        this.tabPlanned.Controls.Add(this.checkDone);
        this.tabPlanned.Controls.Add(this.gridPlanned);
        this.tabPlanned.Location = new System.Drawing.Point(4, 22);
        this.tabPlanned.Name = "tabPlanned";
        this.tabPlanned.Size = new System.Drawing.Size(516, 233);
        this.tabPlanned.TabIndex = 4;
        this.tabPlanned.Text = "Planned Appts";
        this.tabPlanned.UseVisualStyleBackColor = true;
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(true);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(325, 1);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(75, 23);
        this.butDown.TabIndex = 195;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.setAutosize(true);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(244, 1);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(75, 23);
        this.butUp.TabIndex = 194;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butPin
        //
        this.butPin.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPin.setAutosize(true);
        this.butPin.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPin.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPin.setCornerRadius(4F);
        this.butPin.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPin.Location = new System.Drawing.Point(163, 1);
        this.butPin.Name = "butPin";
        this.butPin.Size = new System.Drawing.Size(75, 23);
        this.butPin.TabIndex = 6;
        this.butPin.Text = "Pin Board";
        this.butPin.Click += new System.EventHandler(this.butPin_Click);
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Image = Resources.getdeleteX();
        this.butClear.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClear.Location = new System.Drawing.Point(82, 1);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(75, 23);
        this.butClear.TabIndex = 5;
        this.butClear.Text = "Delete";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.setAutosize(true);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Image = Resources.getAdd();
        this.butNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNew.Location = new System.Drawing.Point(1, 1);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(75, 23);
        this.butNew.TabIndex = 4;
        this.butNew.Text = "Add";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // gridPlanned
        //
        this.gridPlanned.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridPlanned.setHScrollVisible(false);
        this.gridPlanned.Location = new System.Drawing.Point(0, 25);
        this.gridPlanned.Name = "gridPlanned";
        this.gridPlanned.setScrollValue(0);
        this.gridPlanned.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridPlanned.Size = new System.Drawing.Size(516, 208);
        this.gridPlanned.TabIndex = 193;
        this.gridPlanned.setTitle("Planned Appointments");
        this.gridPlanned.setTranslationName("TablePlannedAppts");
        this.gridPlanned.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPlanned.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPlanned_CellDoubleClick(sender, e);
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
        this.tabShow.BackColor = System.Drawing.Color.White;
        this.tabShow.Controls.Add(this.butShowDateRange);
        this.tabShow.Controls.Add(this.textShowDateRange);
        this.tabShow.Controls.Add(this.listProcStatusCodes);
        this.tabShow.Controls.Add(this.gridChartViews);
        this.tabShow.Controls.Add(this.labelCustView);
        this.tabShow.Controls.Add(this.butChartViewDown);
        this.tabShow.Controls.Add(this.butChartViewUp);
        this.tabShow.Controls.Add(this.butChartViewAdd);
        this.tabShow.Controls.Add(this.groupBox7);
        this.tabShow.Controls.Add(this.groupBox6);
        this.tabShow.Controls.Add(this.checkShowTeeth);
        this.tabShow.Controls.Add(this.checkNotes);
        this.tabShow.Controls.Add(this.checkAudit);
        this.tabShow.Controls.Add(this.butShowAll);
        this.tabShow.Controls.Add(this.butShowNone);
        this.tabShow.Location = new System.Drawing.Point(4, 22);
        this.tabShow.Name = "tabShow";
        this.tabShow.Size = new System.Drawing.Size(516, 233);
        this.tabShow.TabIndex = 5;
        this.tabShow.Text = "Show";
        this.tabShow.UseVisualStyleBackColor = true;
        //
        // butShowDateRange
        //
        this.butShowDateRange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowDateRange.setAutosize(true);
        this.butShowDateRange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowDateRange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowDateRange.setCornerRadius(4F);
        this.butShowDateRange.Location = new System.Drawing.Point(273, 186);
        this.butShowDateRange.Name = "butShowDateRange";
        this.butShowDateRange.Size = new System.Drawing.Size(24, 22);
        this.butShowDateRange.TabIndex = 47;
        this.butShowDateRange.Text = "...";
        this.butShowDateRange.UseVisualStyleBackColor = true;
        this.butShowDateRange.Click += new System.EventHandler(this.butShowDateRange_Click);
        //
        // textShowDateRange
        //
        this.textShowDateRange.Font = new System.Drawing.Font("Microsoft Sans Serif", 7.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textShowDateRange.Location = new System.Drawing.Point(144, 188);
        this.textShowDateRange.Name = "textShowDateRange";
        this.textShowDateRange.ReadOnly = true;
        this.textShowDateRange.Size = new System.Drawing.Size(125, 19);
        this.textShowDateRange.TabIndex = 46;
        //
        // listProcStatusCodes
        //
        this.listProcStatusCodes.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listProcStatusCodes.ColumnWidth = 60;
        this.listProcStatusCodes.FormattingEnabled = true;
        this.listProcStatusCodes.IntegralHeight = false;
        this.listProcStatusCodes.Location = new System.Drawing.Point(6, 156);
        this.listProcStatusCodes.MultiColumn = true;
        this.listProcStatusCodes.Name = "listProcStatusCodes";
        this.listProcStatusCodes.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProcStatusCodes.Size = new System.Drawing.Size(134, 74);
        this.listProcStatusCodes.TabIndex = 45;
        this.listProcStatusCodes.Visible = false;
        this.listProcStatusCodes.MouseUp += new System.Windows.Forms.MouseEventHandler(this.listProcStatusCodes_MouseUp);
        //
        // gridChartViews
        //
        this.gridChartViews.setHScrollVisible(false);
        this.gridChartViews.Location = new System.Drawing.Point(303, 8);
        this.gridChartViews.Name = "gridChartViews";
        this.gridChartViews.setScrollValue(0);
        this.gridChartViews.Size = new System.Drawing.Size(191, 173);
        this.gridChartViews.TabIndex = 44;
        this.gridChartViews.setTitle("Chart Views");
        this.gridChartViews.setTranslationName("GridChartViews");
        this.gridChartViews.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridChartViews.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridChartViews_DoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridChartViews.CellClick = __MultiODGridClickEventHandler.combine(this.gridChartViews.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridChartViews_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // labelCustView
        //
        this.labelCustView.AutoSize = true;
        this.labelCustView.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelCustView.ForeColor = System.Drawing.Color.Red;
        this.labelCustView.Location = new System.Drawing.Point(160, 211);
        this.labelCustView.Name = "labelCustView";
        this.labelCustView.Size = new System.Drawing.Size(96, 16);
        this.labelCustView.TabIndex = 43;
        this.labelCustView.Text = "Custom View";
        this.labelCustView.Visible = false;
        //
        // butChartViewDown
        //
        this.butChartViewDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChartViewDown.setAutosize(true);
        this.butChartViewDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChartViewDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChartViewDown.setCornerRadius(4F);
        this.butChartViewDown.Image = Resources.getdown();
        this.butChartViewDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butChartViewDown.Location = new System.Drawing.Point(426, 195);
        this.butChartViewDown.Name = "butChartViewDown";
        this.butChartViewDown.Size = new System.Drawing.Size(68, 24);
        this.butChartViewDown.TabIndex = 41;
        this.butChartViewDown.Text = "&Down";
        this.butChartViewDown.Click += new System.EventHandler(this.butChartViewDown_Click);
        //
        // butChartViewUp
        //
        this.butChartViewUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChartViewUp.setAutosize(true);
        this.butChartViewUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChartViewUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChartViewUp.setCornerRadius(4F);
        this.butChartViewUp.Image = Resources.getup();
        this.butChartViewUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butChartViewUp.Location = new System.Drawing.Point(367, 195);
        this.butChartViewUp.Name = "butChartViewUp";
        this.butChartViewUp.Size = new System.Drawing.Size(54, 24);
        this.butChartViewUp.TabIndex = 42;
        this.butChartViewUp.Text = "&Up";
        this.butChartViewUp.Click += new System.EventHandler(this.butChartViewUp_Click);
        //
        // butChartViewAdd
        //
        this.butChartViewAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChartViewAdd.setAutosize(true);
        this.butChartViewAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChartViewAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChartViewAdd.setCornerRadius(4F);
        this.butChartViewAdd.Image = Resources.getAdd();
        this.butChartViewAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butChartViewAdd.Location = new System.Drawing.Point(303, 195);
        this.butChartViewAdd.Name = "butChartViewAdd";
        this.butChartViewAdd.Size = new System.Drawing.Size(59, 24);
        this.butChartViewAdd.TabIndex = 40;
        this.butChartViewAdd.Text = "&Add";
        this.butChartViewAdd.Click += new System.EventHandler(this.butChartViewAdd_Click);
        //
        // groupBox7
        //
        this.groupBox7.Controls.Add(this.checkSheets);
        this.groupBox7.Controls.Add(this.checkTasks);
        this.groupBox7.Controls.Add(this.checkEmail);
        this.groupBox7.Controls.Add(this.checkCommFamily);
        this.groupBox7.Controls.Add(this.checkAppt);
        this.groupBox7.Controls.Add(this.checkLabCase);
        this.groupBox7.Controls.Add(this.checkRx);
        this.groupBox7.Controls.Add(this.checkComm);
        this.groupBox7.Location = new System.Drawing.Point(144, 4);
        this.groupBox7.Name = "groupBox7";
        this.groupBox7.Size = new System.Drawing.Size(125, 148);
        this.groupBox7.TabIndex = 19;
        this.groupBox7.TabStop = false;
        this.groupBox7.Text = "Object Types";
        //
        // checkSheets
        //
        this.checkSheets.Checked = true;
        this.checkSheets.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkSheets.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkSheets.Location = new System.Drawing.Point(10, 130);
        this.checkSheets.Name = "checkSheets";
        this.checkSheets.Size = new System.Drawing.Size(102, 13);
        this.checkSheets.TabIndex = 219;
        this.checkSheets.Text = "Sheets";
        this.checkSheets.Click += new System.EventHandler(this.checkSheets_Click);
        //
        // checkTasks
        //
        this.checkTasks.Checked = true;
        this.checkTasks.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkTasks.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkTasks.Location = new System.Drawing.Point(10, 66);
        this.checkTasks.Name = "checkTasks";
        this.checkTasks.Size = new System.Drawing.Size(102, 13);
        this.checkTasks.TabIndex = 218;
        this.checkTasks.Text = "Tasks";
        this.checkTasks.Click += new System.EventHandler(this.checkTasks_Click);
        //
        // checkEmail
        //
        this.checkEmail.Checked = true;
        this.checkEmail.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkEmail.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkEmail.Location = new System.Drawing.Point(10, 82);
        this.checkEmail.Name = "checkEmail";
        this.checkEmail.Size = new System.Drawing.Size(102, 13);
        this.checkEmail.TabIndex = 217;
        this.checkEmail.Text = "Email";
        this.checkEmail.Click += new System.EventHandler(this.checkEmail_Click);
        //
        // checkCommFamily
        //
        this.checkCommFamily.Checked = true;
        this.checkCommFamily.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkCommFamily.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkCommFamily.Location = new System.Drawing.Point(26, 49);
        this.checkCommFamily.Name = "checkCommFamily";
        this.checkCommFamily.Size = new System.Drawing.Size(88, 13);
        this.checkCommFamily.TabIndex = 20;
        this.checkCommFamily.Text = "Family";
        this.checkCommFamily.Click += new System.EventHandler(this.checkCommFamily_Click);
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
        this.checkLabCase.Location = new System.Drawing.Point(10, 98);
        this.checkLabCase.Name = "checkLabCase";
        this.checkLabCase.Size = new System.Drawing.Size(102, 13);
        this.checkLabCase.TabIndex = 17;
        this.checkLabCase.Text = "Lab Cases";
        this.checkLabCase.Click += new System.EventHandler(this.checkLabCase_Click);
        //
        // groupBox6
        //
        this.groupBox6.Controls.Add(this.checkShowCn);
        this.groupBox6.Controls.Add(this.checkShowE);
        this.groupBox6.Controls.Add(this.checkShowR);
        this.groupBox6.Controls.Add(this.checkShowC);
        this.groupBox6.Controls.Add(this.checkShowTP);
        this.groupBox6.Location = new System.Drawing.Point(6, 4);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(121, 99);
        this.groupBox6.TabIndex = 18;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Procedures";
        //
        // checkShowCn
        //
        this.checkShowCn.Checked = true;
        this.checkShowCn.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowCn.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowCn.Location = new System.Drawing.Point(9, 81);
        this.checkShowCn.Name = "checkShowCn";
        this.checkShowCn.Size = new System.Drawing.Size(101, 13);
        this.checkShowCn.TabIndex = 15;
        this.checkShowCn.Text = "Conditions";
        this.checkShowCn.Click += new System.EventHandler(this.checkShowCn_Click);
        //
        // butShowAll
        //
        this.butShowAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowAll.setAutosize(true);
        this.butShowAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowAll.setCornerRadius(4F);
        this.butShowAll.Location = new System.Drawing.Point(6, 129);
        this.butShowAll.Name = "butShowAll";
        this.butShowAll.Size = new System.Drawing.Size(53, 23);
        this.butShowAll.TabIndex = 12;
        this.butShowAll.Text = "All";
        this.butShowAll.Click += new System.EventHandler(this.butShowAll_Click);
        //
        // butShowNone
        //
        this.butShowNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butShowNone.setAutosize(true);
        this.butShowNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butShowNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butShowNone.setCornerRadius(4F);
        this.butShowNone.Location = new System.Drawing.Point(69, 129);
        this.butShowNone.Name = "butShowNone";
        this.butShowNone.Size = new System.Drawing.Size(58, 23);
        this.butShowNone.TabIndex = 13;
        this.butShowNone.Text = "None";
        this.butShowNone.Click += new System.EventHandler(this.butShowNone_Click);
        //
        // tabDraw
        //
        this.tabDraw.Controls.Add(this.radioColorChanger);
        this.tabDraw.Controls.Add(this.groupBox8);
        this.tabDraw.Controls.Add(this.panelDrawColor);
        this.tabDraw.Controls.Add(this.radioEraser);
        this.tabDraw.Controls.Add(this.radioPen);
        this.tabDraw.Controls.Add(this.radioPointer);
        this.tabDraw.Location = new System.Drawing.Point(4, 22);
        this.tabDraw.Name = "tabDraw";
        this.tabDraw.Size = new System.Drawing.Size(516, 233);
        this.tabDraw.TabIndex = 6;
        this.tabDraw.Text = "Draw";
        this.tabDraw.UseVisualStyleBackColor = true;
        //
        // radioColorChanger
        //
        this.radioColorChanger.Location = new System.Drawing.Point(14, 70);
        this.radioColorChanger.Name = "radioColorChanger";
        this.radioColorChanger.Size = new System.Drawing.Size(122, 17);
        this.radioColorChanger.TabIndex = 5;
        this.radioColorChanger.TabStop = true;
        this.radioColorChanger.Text = "Color Changer";
        this.radioColorChanger.UseVisualStyleBackColor = true;
        this.radioColorChanger.Click += new System.EventHandler(this.radioColorChanger_Click);
        //
        // groupBox8
        //
        this.groupBox8.Controls.Add(this.panelBlack);
        this.groupBox8.Controls.Add(this.label22);
        this.groupBox8.Controls.Add(this.butColorOther);
        this.groupBox8.Controls.Add(this.panelRdark);
        this.groupBox8.Controls.Add(this.label21);
        this.groupBox8.Controls.Add(this.panelRlight);
        this.groupBox8.Controls.Add(this.panelEOdark);
        this.groupBox8.Controls.Add(this.label20);
        this.groupBox8.Controls.Add(this.panelEOlight);
        this.groupBox8.Controls.Add(this.panelECdark);
        this.groupBox8.Controls.Add(this.label19);
        this.groupBox8.Controls.Add(this.panelEClight);
        this.groupBox8.Controls.Add(this.panelCdark);
        this.groupBox8.Controls.Add(this.label17);
        this.groupBox8.Controls.Add(this.panelClight);
        this.groupBox8.Controls.Add(this.panelTPdark);
        this.groupBox8.Controls.Add(this.label18);
        this.groupBox8.Controls.Add(this.panelTPlight);
        this.groupBox8.Location = new System.Drawing.Point(160, 11);
        this.groupBox8.Name = "groupBox8";
        this.groupBox8.Size = new System.Drawing.Size(157, 214);
        this.groupBox8.TabIndex = 4;
        this.groupBox8.TabStop = false;
        this.groupBox8.Text = "Set Color";
        //
        // panelBlack
        //
        this.panelBlack.BackColor = System.Drawing.Color.Black;
        this.panelBlack.Location = new System.Drawing.Point(95, 147);
        this.panelBlack.Name = "panelBlack";
        this.panelBlack.Size = new System.Drawing.Size(22, 22);
        this.panelBlack.TabIndex = 194;
        this.panelBlack.Click += new System.EventHandler(this.panelBlack_Click);
        //
        // label22
        //
        this.label22.Location = new System.Drawing.Point(11, 150);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(82, 17);
        this.label22.TabIndex = 193;
        this.label22.Text = "Black";
        this.label22.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butColorOther
        //
        this.butColorOther.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColorOther.setAutosize(true);
        this.butColorOther.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColorOther.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColorOther.setCornerRadius(4F);
        this.butColorOther.Location = new System.Drawing.Point(95, 179);
        this.butColorOther.Name = "butColorOther";
        this.butColorOther.Size = new System.Drawing.Size(50, 24);
        this.butColorOther.TabIndex = 192;
        this.butColorOther.Text = "Other";
        this.butColorOther.Click += new System.EventHandler(this.butColorOther_Click);
        //
        // panelRdark
        //
        this.panelRdark.BackColor = System.Drawing.Color.Black;
        this.panelRdark.Location = new System.Drawing.Point(95, 121);
        this.panelRdark.Name = "panelRdark";
        this.panelRdark.Size = new System.Drawing.Size(22, 22);
        this.panelRdark.TabIndex = 18;
        this.panelRdark.Click += new System.EventHandler(this.panelRdark_Click);
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(11, 124);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(82, 17);
        this.label21.TabIndex = 17;
        this.label21.Text = "Referred";
        this.label21.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelRlight
        //
        this.panelRlight.BackColor = System.Drawing.Color.Black;
        this.panelRlight.Location = new System.Drawing.Point(123, 121);
        this.panelRlight.Name = "panelRlight";
        this.panelRlight.Size = new System.Drawing.Size(22, 22);
        this.panelRlight.TabIndex = 16;
        this.panelRlight.Click += new System.EventHandler(this.panelRlight_Click);
        //
        // panelEOdark
        //
        this.panelEOdark.BackColor = System.Drawing.Color.Black;
        this.panelEOdark.Location = new System.Drawing.Point(95, 95);
        this.panelEOdark.Name = "panelEOdark";
        this.panelEOdark.Size = new System.Drawing.Size(22, 22);
        this.panelEOdark.TabIndex = 15;
        this.panelEOdark.Click += new System.EventHandler(this.panelEOdark_Click);
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(11, 98);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(82, 17);
        this.label20.TabIndex = 14;
        this.label20.Text = "ExistOther";
        this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelEOlight
        //
        this.panelEOlight.BackColor = System.Drawing.Color.Black;
        this.panelEOlight.Location = new System.Drawing.Point(123, 95);
        this.panelEOlight.Name = "panelEOlight";
        this.panelEOlight.Size = new System.Drawing.Size(22, 22);
        this.panelEOlight.TabIndex = 13;
        this.panelEOlight.Click += new System.EventHandler(this.panelEOlight_Click);
        //
        // panelECdark
        //
        this.panelECdark.BackColor = System.Drawing.Color.Black;
        this.panelECdark.Location = new System.Drawing.Point(95, 69);
        this.panelECdark.Name = "panelECdark";
        this.panelECdark.Size = new System.Drawing.Size(22, 22);
        this.panelECdark.TabIndex = 12;
        this.panelECdark.Click += new System.EventHandler(this.panelECdark_Click);
        //
        // label19
        //
        this.label19.Location = new System.Drawing.Point(11, 72);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(82, 17);
        this.label19.TabIndex = 11;
        this.label19.Text = "ExistCurProv";
        this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelEClight
        //
        this.panelEClight.BackColor = System.Drawing.Color.Black;
        this.panelEClight.Location = new System.Drawing.Point(123, 69);
        this.panelEClight.Name = "panelEClight";
        this.panelEClight.Size = new System.Drawing.Size(22, 22);
        this.panelEClight.TabIndex = 10;
        this.panelEClight.Click += new System.EventHandler(this.panelEClight_Click);
        //
        // panelCdark
        //
        this.panelCdark.BackColor = System.Drawing.Color.Black;
        this.panelCdark.Location = new System.Drawing.Point(95, 43);
        this.panelCdark.Name = "panelCdark";
        this.panelCdark.Size = new System.Drawing.Size(22, 22);
        this.panelCdark.TabIndex = 9;
        this.panelCdark.Click += new System.EventHandler(this.panelCdark_Click);
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(11, 46);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(82, 17);
        this.label17.TabIndex = 8;
        this.label17.Text = "Complete";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelClight
        //
        this.panelClight.BackColor = System.Drawing.Color.Black;
        this.panelClight.Location = new System.Drawing.Point(123, 43);
        this.panelClight.Name = "panelClight";
        this.panelClight.Size = new System.Drawing.Size(22, 22);
        this.panelClight.TabIndex = 7;
        this.panelClight.Click += new System.EventHandler(this.panelClight_Click);
        //
        // panelTPdark
        //
        this.panelTPdark.BackColor = System.Drawing.Color.Black;
        this.panelTPdark.Location = new System.Drawing.Point(95, 17);
        this.panelTPdark.Name = "panelTPdark";
        this.panelTPdark.Size = new System.Drawing.Size(22, 22);
        this.panelTPdark.TabIndex = 6;
        this.panelTPdark.Click += new System.EventHandler(this.panelTPdark_Click);
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(11, 20);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(82, 17);
        this.label18.TabIndex = 5;
        this.label18.Text = "TreatPlan";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelTPlight
        //
        this.panelTPlight.BackColor = System.Drawing.Color.Black;
        this.panelTPlight.Location = new System.Drawing.Point(123, 17);
        this.panelTPlight.Name = "panelTPlight";
        this.panelTPlight.Size = new System.Drawing.Size(22, 22);
        this.panelTPlight.TabIndex = 4;
        this.panelTPlight.Click += new System.EventHandler(this.panelTPlight_Click);
        //
        // panelDrawColor
        //
        this.panelDrawColor.BackColor = System.Drawing.Color.Black;
        this.panelDrawColor.Location = new System.Drawing.Point(13, 101);
        this.panelDrawColor.Name = "panelDrawColor";
        this.panelDrawColor.Size = new System.Drawing.Size(22, 22);
        this.panelDrawColor.TabIndex = 3;
        this.panelDrawColor.DoubleClick += new System.EventHandler(this.panelDrawColor_DoubleClick);
        //
        // radioEraser
        //
        this.radioEraser.Location = new System.Drawing.Point(14, 51);
        this.radioEraser.Name = "radioEraser";
        this.radioEraser.Size = new System.Drawing.Size(122, 17);
        this.radioEraser.TabIndex = 2;
        this.radioEraser.TabStop = true;
        this.radioEraser.Text = "Eraser";
        this.radioEraser.UseVisualStyleBackColor = true;
        this.radioEraser.Click += new System.EventHandler(this.radioEraser_Click);
        //
        // radioPen
        //
        this.radioPen.Location = new System.Drawing.Point(14, 32);
        this.radioPen.Name = "radioPen";
        this.radioPen.Size = new System.Drawing.Size(122, 17);
        this.radioPen.TabIndex = 1;
        this.radioPen.TabStop = true;
        this.radioPen.Text = "Pen";
        this.radioPen.UseVisualStyleBackColor = true;
        this.radioPen.Click += new System.EventHandler(this.radioPen_Click);
        //
        // radioPointer
        //
        this.radioPointer.Checked = true;
        this.radioPointer.Location = new System.Drawing.Point(14, 13);
        this.radioPointer.Name = "radioPointer";
        this.radioPointer.Size = new System.Drawing.Size(122, 17);
        this.radioPointer.TabIndex = 0;
        this.radioPointer.TabStop = true;
        this.radioPointer.Text = "Pointer";
        this.radioPointer.UseVisualStyleBackColor = true;
        this.radioPointer.Click += new System.EventHandler(this.radioPointer_Click);
        //
        // tabPatInfo
        //
        this.tabPatInfo.Location = new System.Drawing.Point(4, 22);
        this.tabPatInfo.Name = "tabPatInfo";
        this.tabPatInfo.Size = new System.Drawing.Size(516, 233);
        this.tabPatInfo.TabIndex = 7;
        this.tabPatInfo.Text = "Pat Info";
        this.tabPatInfo.UseVisualStyleBackColor = true;
        //
        // tabCustomer
        //
        this.tabCustomer.Controls.Add(this.labelMonth0);
        this.tabCustomer.Controls.Add(this.textMonth0);
        this.tabCustomer.Controls.Add(this.label2);
        this.tabCustomer.Controls.Add(this.labelCommonProc);
        this.tabCustomer.Controls.Add(this.labelTimes);
        this.tabCustomer.Controls.Add(this.labelMonth1);
        this.tabCustomer.Controls.Add(this.labelMonth2);
        this.tabCustomer.Controls.Add(this.labelMonth3);
        this.tabCustomer.Controls.Add(this.labelMonthAvg);
        this.tabCustomer.Controls.Add(this.textMonthAvg);
        this.tabCustomer.Controls.Add(this.textMonth3);
        this.tabCustomer.Controls.Add(this.textMonth2);
        this.tabCustomer.Controls.Add(this.textMonth1);
        this.tabCustomer.Controls.Add(this.listCommonProcs);
        this.tabCustomer.Controls.Add(this.gridCustomerViews);
        this.tabCustomer.Location = new System.Drawing.Point(4, 22);
        this.tabCustomer.Name = "tabCustomer";
        this.tabCustomer.Padding = new System.Windows.Forms.Padding(3);
        this.tabCustomer.Size = new System.Drawing.Size(516, 233);
        this.tabCustomer.TabIndex = 8;
        this.tabCustomer.Text = "Customer";
        this.tabCustomer.UseVisualStyleBackColor = true;
        //
        // labelMonth0
        //
        this.labelMonth0.Location = new System.Drawing.Point(340, 109);
        this.labelMonth0.Name = "labelMonth0";
        this.labelMonth0.Size = new System.Drawing.Size(72, 20);
        this.labelMonth0.TabIndex = 62;
        this.labelMonth0.Text = "month 0";
        this.labelMonth0.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMonth0
        //
        this.textMonth0.Location = new System.Drawing.Point(413, 110);
        this.textMonth0.Name = "textMonth0";
        this.textMonth0.ReadOnly = true;
        this.textMonth0.Size = new System.Drawing.Size(50, 20);
        this.textMonth0.TabIndex = 61;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(340, 163);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(170, 41);
        this.label2.TabIndex = 60;
        this.label2.Text = "(Avg is based on entire family call history excluding first two months)";
        //
        // labelCommonProc
        //
        this.labelCommonProc.Location = new System.Drawing.Point(201, 11);
        this.labelCommonProc.Name = "labelCommonProc";
        this.labelCommonProc.Size = new System.Drawing.Size(123, 16);
        this.labelCommonProc.TabIndex = 59;
        this.labelCommonProc.Text = "Quick add:";
        this.labelCommonProc.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // labelTimes
        //
        this.labelTimes.Location = new System.Drawing.Point(347, 11);
        this.labelTimes.Name = "labelTimes";
        this.labelTimes.Size = new System.Drawing.Size(120, 16);
        this.labelTimes.TabIndex = 58;
        this.labelTimes.Text = "Total time for family:";
        this.labelTimes.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMonth1
        //
        this.labelMonth1.Location = new System.Drawing.Point(340, 83);
        this.labelMonth1.Name = "labelMonth1";
        this.labelMonth1.Size = new System.Drawing.Size(72, 20);
        this.labelMonth1.TabIndex = 57;
        this.labelMonth1.Text = "month 1";
        this.labelMonth1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMonth2
        //
        this.labelMonth2.Location = new System.Drawing.Point(340, 57);
        this.labelMonth2.Name = "labelMonth2";
        this.labelMonth2.Size = new System.Drawing.Size(72, 20);
        this.labelMonth2.TabIndex = 56;
        this.labelMonth2.Text = "month 2";
        this.labelMonth2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMonth3
        //
        this.labelMonth3.Location = new System.Drawing.Point(340, 31);
        this.labelMonth3.Name = "labelMonth3";
        this.labelMonth3.Size = new System.Drawing.Size(72, 20);
        this.labelMonth3.TabIndex = 55;
        this.labelMonth3.Text = "month 3";
        this.labelMonth3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelMonthAvg
        //
        this.labelMonthAvg.Location = new System.Drawing.Point(340, 135);
        this.labelMonthAvg.Name = "labelMonthAvg";
        this.labelMonthAvg.Size = new System.Drawing.Size(72, 20);
        this.labelMonthAvg.TabIndex = 54;
        this.labelMonthAvg.Text = "Avg";
        this.labelMonthAvg.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMonthAvg
        //
        this.textMonthAvg.Location = new System.Drawing.Point(413, 136);
        this.textMonthAvg.Name = "textMonthAvg";
        this.textMonthAvg.ReadOnly = true;
        this.textMonthAvg.Size = new System.Drawing.Size(50, 20);
        this.textMonthAvg.TabIndex = 50;
        //
        // textMonth3
        //
        this.textMonth3.Location = new System.Drawing.Point(413, 32);
        this.textMonth3.Name = "textMonth3";
        this.textMonth3.ReadOnly = true;
        this.textMonth3.Size = new System.Drawing.Size(50, 20);
        this.textMonth3.TabIndex = 49;
        //
        // textMonth2
        //
        this.textMonth2.Location = new System.Drawing.Point(413, 58);
        this.textMonth2.Name = "textMonth2";
        this.textMonth2.ReadOnly = true;
        this.textMonth2.Size = new System.Drawing.Size(50, 20);
        this.textMonth2.TabIndex = 48;
        //
        // textMonth1
        //
        this.textMonth1.Location = new System.Drawing.Point(413, 84);
        this.textMonth1.Name = "textMonth1";
        this.textMonth1.ReadOnly = true;
        this.textMonth1.Size = new System.Drawing.Size(50, 20);
        this.textMonth1.TabIndex = 47;
        //
        // listCommonProcs
        //
        this.listCommonProcs.FormattingEnabled = true;
        this.listCommonProcs.Items.AddRange(new Object[]{ "Monthly Maintenance", "Monthly Mobile", "Monthly E-Mail Support", "Monthly EHR", "Data Conversion", "Trial Conversion", "Demo", "Online Training", "Additional Online Training", "eCW Online Training", "eCW Installation Verify", "Programming", "Query Programming" });
        this.listCommonProcs.Location = new System.Drawing.Point(203, 30);
        this.listCommonProcs.Name = "listCommonProcs";
        this.listCommonProcs.Size = new System.Drawing.Size(131, 173);
        this.listCommonProcs.TabIndex = 46;
        this.listCommonProcs.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listCommonProcs_MouseDown);
        //
        // gridCustomerViews
        //
        this.gridCustomerViews.setHScrollVisible(false);
        this.gridCustomerViews.Location = new System.Drawing.Point(6, 30);
        this.gridCustomerViews.Name = "gridCustomerViews";
        this.gridCustomerViews.setScrollValue(0);
        this.gridCustomerViews.Size = new System.Drawing.Size(191, 173);
        this.gridCustomerViews.TabIndex = 45;
        this.gridCustomerViews.setTitle("Chart Views");
        this.gridCustomerViews.setTranslationName("GridChartViews");
        this.gridCustomerViews.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridCustomerViews.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridCustomerViews_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridCustomerViews.CellClick = __MultiODGridClickEventHandler.combine(this.gridCustomerViews.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridCustomerViews_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // menuConsent
        //
        this.menuConsent.Popup += new System.EventHandler(this.menuConsent_Popup);
        //
        // panelEcw
        //
        this.panelEcw.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panelEcw.Controls.Add(this.labelECWerror);
        this.panelEcw.Controls.Add(this.webBrowserEcw);
        this.panelEcw.Controls.Add(this.butECWdown);
        this.panelEcw.Controls.Add(this.butECWup);
        this.panelEcw.Location = new System.Drawing.Point(444, 521);
        this.panelEcw.Name = "panelEcw";
        this.panelEcw.Size = new System.Drawing.Size(373, 65);
        this.panelEcw.TabIndex = 197;
        //
        // labelECWerror
        //
        this.labelECWerror.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.labelECWerror.Location = new System.Drawing.Point(25, 22);
        this.labelECWerror.Name = "labelECWerror";
        this.labelECWerror.Size = new System.Drawing.Size(314, 27);
        this.labelECWerror.TabIndex = 199;
        this.labelECWerror.Text = "Error:";
        this.labelECWerror.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // webBrowserEcw
        //
        this.webBrowserEcw.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.webBrowserEcw.Location = new System.Drawing.Point(1, 11);
        this.webBrowserEcw.MinimumSize = new System.Drawing.Size(20, 20);
        this.webBrowserEcw.Name = "webBrowserEcw";
        this.webBrowserEcw.Size = new System.Drawing.Size(370, 52);
        this.webBrowserEcw.TabIndex = 198;
        this.webBrowserEcw.Url = new System.Uri("", System.UriKind.Relative);
        //
        // butECWdown
        //
        this.butECWdown.setAdjustImageLocation(new System.Drawing.Point(0, -1));
        this.butECWdown.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butECWdown.setAutosize(true);
        this.butECWdown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butECWdown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butECWdown.setCornerRadius(2F);
        this.butECWdown.Image = Resources.getarrowDownTriangle();
        this.butECWdown.Location = new System.Drawing.Point(321, 1);
        this.butECWdown.Name = "butECWdown";
        this.butECWdown.Size = new System.Drawing.Size(24, 9);
        this.butECWdown.TabIndex = 197;
        this.butECWdown.UseVisualStyleBackColor = true;
        this.butECWdown.Click += new System.EventHandler(this.butECWdown_Click);
        //
        // butECWup
        //
        this.butECWup.setAdjustImageLocation(new System.Drawing.Point(0, -1));
        this.butECWup.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butECWup.setAutosize(true);
        this.butECWup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butECWup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butECWup.setCornerRadius(2F);
        this.butECWup.Image = Resources.getarrowUpTriangle();
        this.butECWup.Location = new System.Drawing.Point(346, 1);
        this.butECWup.Name = "butECWup";
        this.butECWup.Size = new System.Drawing.Size(24, 9);
        this.butECWup.TabIndex = 196;
        this.butECWup.UseVisualStyleBackColor = true;
        this.butECWup.Click += new System.EventHandler(this.butECWup_Click);
        //
        // menuToothChart
        //
        this.menuToothChart.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemChartBig, this.menuItemChartSave });
        this.menuToothChart.Popup += new System.EventHandler(this.menuToothChart_Popup);
        //
        // menuItemChartBig
        //
        this.menuItemChartBig.Index = 0;
        this.menuItemChartBig.Text = "Show Big";
        this.menuItemChartBig.Click += new System.EventHandler(this.menuItemChartBig_Click);
        //
        // menuItemChartSave
        //
        this.menuItemChartSave.Index = 1;
        this.menuItemChartSave.Text = "Save to Images";
        this.menuItemChartSave.Click += new System.EventHandler(this.menuItemChartSave_Click);
        //
        // toothChart
        //
        this.toothChart.setAutoFinish(false);
        this.toothChart.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChart.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChart.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChart.setDeviceFormat(null);
        this.toothChart.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChart.Location = new System.Drawing.Point(0, 26);
        this.toothChart.Name = "toothChart";
        this.toothChart.setPerioMode(false);
        this.toothChart.setPreferredPixelFormatNumber(0);
        this.toothChart.Size = new System.Drawing.Size(410, 307);
        this.toothChart.TabIndex = 194;
        toothChartData1.setSizeControl(new System.Drawing.Size(410, 307));
        this.toothChart.setTcData(toothChartData1);
        this.toothChart.setUseHardware(false);
        this.toothChart.SegmentDrawn = __MultiToothChartDrawEventHandler.combine(this.toothChart.SegmentDrawn,new SparksToothChart.ToothChartDrawEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ToothChartDrawEventArgs e) throws Exception {
                this.toothChart_SegmentDrawn(sender, e);
            }

            public List<SparksToothChart.ToothChartDrawEventHandler> getInvocationList() throws Exception {
                List<SparksToothChart.ToothChartDrawEventHandler> ret = new ArrayList<SparksToothChart.ToothChartDrawEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridProg
        //
        this.gridProg.setAllowSortingByColumn(true);
        this.gridProg.setHScrollVisible(true);
        this.gridProg.Location = new System.Drawing.Point(415, 291);
        this.gridProg.Name = "gridProg";
        this.gridProg.setScrollValue(0);
        this.gridProg.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProg.Size = new System.Drawing.Size(524, 227);
        this.gridProg.TabIndex = 192;
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
        this.gridProg.CellClick = __MultiODGridClickEventHandler.combine(this.gridProg.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridProg_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridProg.KeyDown += new System.Windows.Forms.KeyEventHandler(this.gridProg_KeyDown);
        this.gridProg.MouseUp += new System.Windows.Forms.MouseEventHandler(this.gridProg_MouseUp);
        //
        // gridPtInfo
        //
        this.gridPtInfo.setHScrollVisible(false);
        this.gridPtInfo.Location = new System.Drawing.Point(0, 405);
        this.gridPtInfo.Name = "gridPtInfo";
        this.gridPtInfo.setScrollValue(0);
        this.gridPtInfo.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridPtInfo.Size = new System.Drawing.Size(411, 325);
        this.gridPtInfo.TabIndex = 193;
        this.gridPtInfo.setTitle("Patient Info");
        this.gridPtInfo.setTranslationName("TableChartPtInfo");
        this.gridPtInfo.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPtInfo.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPtInfo_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butForeignKey
        //
        this.butForeignKey.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butForeignKey.setAutosize(true);
        this.butForeignKey.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butForeignKey.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butForeignKey.setCornerRadius(4F);
        this.butForeignKey.Enabled = false;
        this.butForeignKey.Location = new System.Drawing.Point(253, 424);
        this.butForeignKey.Name = "butForeignKey";
        this.butForeignKey.Size = new System.Drawing.Size(75, 14);
        this.butForeignKey.TabIndex = 196;
        this.butForeignKey.Text = "Foreign Key";
        this.butForeignKey.UseVisualStyleBackColor = true;
        this.butForeignKey.Click += new System.EventHandler(this.butForeignKey_Click);
        //
        // butAddKey
        //
        this.butAddKey.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddKey.setAutosize(true);
        this.butAddKey.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddKey.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddKey.setCornerRadius(4F);
        this.butAddKey.Enabled = false;
        this.butAddKey.Location = new System.Drawing.Point(334, 424);
        this.butAddKey.Name = "butAddKey";
        this.butAddKey.Size = new System.Drawing.Size(78, 14);
        this.butAddKey.TabIndex = 195;
        this.butAddKey.Text = "USA Key";
        this.butAddKey.UseVisualStyleBackColor = true;
        this.butAddKey.Click += new System.EventHandler(this.butAddKey_Click);
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(939, 25);
        this.ToolBarMain.TabIndex = 177;
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
        // button1
        //
        this.button1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.button1.setAutosize(true);
        this.button1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.button1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.button1.setCornerRadius(4F);
        this.button1.Location = new System.Drawing.Point(127, 692);
        this.button1.Name = "button1";
        this.button1.Size = new System.Drawing.Size(75, 23);
        this.button1.TabIndex = 36;
        this.button1.Text = "invisible";
        this.button1.Visible = false;
        this.button1.Click += new System.EventHandler(this.button1_Click);
        //
        // textTreatmentNotes
        //
        this.textTreatmentNotes.Location = new System.Drawing.Point(0, 334);
        this.textTreatmentNotes.Multiline = true;
        this.textTreatmentNotes.Name = "textTreatmentNotes";
        this.textTreatmentNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.ChartTreatment);
        this.textTreatmentNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textTreatmentNotes.Size = new System.Drawing.Size(411, 71);
        this.textTreatmentNotes.TabIndex = 187;
        this.textTreatmentNotes.TextChanged += new System.EventHandler(this.textTreatmentNotes_TextChanged);
        this.textTreatmentNotes.Leave += new System.EventHandler(this.textTreatmentNotes_Leave);
        //
        // butPhoneNums
        //
        this.butPhoneNums.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPhoneNums.setAutosize(true);
        this.butPhoneNums.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPhoneNums.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPhoneNums.setCornerRadius(4F);
        this.butPhoneNums.Enabled = false;
        this.butPhoneNums.Location = new System.Drawing.Point(172, 424);
        this.butPhoneNums.Name = "butPhoneNums";
        this.butPhoneNums.Size = new System.Drawing.Size(75, 14);
        this.butPhoneNums.TabIndex = 198;
        this.butPhoneNums.Text = "Phone Nums";
        this.butPhoneNums.UseVisualStyleBackColor = true;
        this.butPhoneNums.Click += new System.EventHandler(this.butPhoneNums_Click);
        //
        // ContrChart
        //
        this.Controls.Add(this.butPhoneNums);
        this.Controls.Add(this.panelEcw);
        this.Controls.Add(this.butForeignKey);
        this.Controls.Add(this.butAddKey);
        this.Controls.Add(this.toothChart);
        this.Controls.Add(this.gridProg);
        this.Controls.Add(this.tabProc);
        this.Controls.Add(this.panelImages);
        this.Controls.Add(this.tabControlImages);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.button1);
        this.Controls.Add(this.textTreatmentNotes);
        this.Controls.Add(this.gridPtInfo);
        this.Name = "ContrChart";
        this.Size = new System.Drawing.Size(939, 708);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ContrChart_Layout);
        this.Resize += new System.EventHandler(this.ContrChart_Resize);
        this.groupBox2.ResumeLayout(false);
        this.tabControlImages.ResumeLayout(false);
        this.panelImages.ResumeLayout(false);
        this.tabProc.ResumeLayout(false);
        this.tabEnterTx.ResumeLayout(false);
        this.tabEnterTx.PerformLayout();
        this.panelQuickButtons.ResumeLayout(false);
        this.panelQuickPasteAmalgam.ResumeLayout(false);
        this.tabMissing.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.tabMovements.ResumeLayout(false);
        this.groupBox4.ResumeLayout(false);
        this.groupBox4.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.tabPrimary.ResumeLayout(false);
        this.groupBox5.ResumeLayout(false);
        this.tabPlanned.ResumeLayout(false);
        this.tabShow.ResumeLayout(false);
        this.tabShow.PerformLayout();
        this.groupBox7.ResumeLayout(false);
        this.groupBox6.ResumeLayout(false);
        this.tabDraw.ResumeLayout(false);
        this.groupBox8.ResumeLayout(false);
        this.tabCustomer.ResumeLayout(false);
        this.tabCustomer.PerformLayout();
        this.panelEcw.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void contrChart_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
        gridProg.Height = ClientSize.Height - tabControlImages.Height - gridProg.Location.Y + 1;
        gridPtInfo.Height = tabControlImages.Top - gridPtInfo.Top;
        if (panelImages.Visible)
        {
            gridProg.Height -= (panelImages.Height + 2);
            gridPtInfo.Height -= (panelImages.Height + 2);
        }
         
        gridProg.Invalidate();
        gridPtInfo.Invalidate();
    }

    private void contrChart_Resize(Object sender, EventArgs e) throws Exception {
        ChartLayoutHelper.Resize(gridProg, panelImages, panelEcw, tabControlImages, ClientSize, gridPtInfo, toothChart, textTreatmentNotes);
    }

    /**
    * 
    */
    public void initializeOnStartup() throws Exception {
        if (InitializedOnStartup)
        {
            return ;
        }
         
        InitializedOnStartup = true;
        newStatus = ProcStat.TP;
        ChartLayoutHelper.InitializeOnStartup(this, tabProc, gridProg, panelEcw, tabControlImages, ClientSize, gridPtInfo, toothChart, textTreatmentNotes, butECWup, butECWdown, tabPatInfo);
        //can't use Lan.F
        //textProcCode is handled in ClearButtons()
        Lan.C(this, new Control[]{ checkDone, butNew, butClear, checkShowTP, checkShowC, checkShowE, checkShowR, checkRx, checkNotes, labelDx, butM, butOI, butD, butL, butBF, butV, groupBox2, radioEntryTP, radioEntryC, radioEntryEC, radioEntryEO, radioEntryR, checkToday, labelDx, label6, butAddProc, label14, butOK, label13, tabEnterTx, tabMissing, tabMovements, tabPrimary, tabPlanned, tabShow, tabDraw }, true);
        layoutToolBar();
        //ComputerPref localComputerPrefs=ComputerPrefs.GetForLocalComputer();
        this.toothChart.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat(ComputerPrefs.getLocalComputer().DirectXFormat));
        this.toothChart.setDrawMode(ComputerPrefs.getLocalComputer().GraphicsSimple);
        //triggers ResetControls.
        Plugins.hookAddCode(this,"ContrChart.InitializeOnStartup_end",PatCur);
    }

    /**
    * Called every time prefs are changed from any workstation.
    */
    public void initializeLocalData() throws Exception {
        IsDistributorKey = PrefC.getBool(PrefName.DistributorKey);
        if (!IsDistributorKey)
        {
            butAddKey.Visible = false;
            butForeignKey.Visible = false;
            butPhoneNums.Visible = false;
            tabProc.TabPages.Remove(tabCustomer);
        }
         
        //ComputerPref computerPref=ComputerPrefs.GetForLocalComputer();
        toothChart.setToothNumberingNomenclature(ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
        toothChart.setUseHardware(ComputerPrefs.getLocalComputer().GraphicsUseHardware);
        toothChart.setPreferredPixelFormatNumber(ComputerPrefs.getLocalComputer().PreferredPixelFormatNum);
        toothChart.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat(ComputerPrefs.getLocalComputer().DirectXFormat));
        //Must be last preference set here, because this causes the
        //pixel format to be recreated.
        toothChart.setDrawMode(ComputerPrefs.getLocalComputer().GraphicsSimple);
        //The preferred pixel format number changes to the selected pixel format number after a context is chosen.
        ComputerPrefs.getLocalComputer().PreferredPixelFormatNum = toothChart.getPreferredPixelFormatNumber();
        ComputerPrefs.update(ComputerPrefs.getLocalComputer());
        if (PatCur != null)
        {
            fillToothChart(true);
        }
         
        if (PrefC.getBoolSilent(PrefName.ChartQuickAddHideAmalgam,true))
        {
            panelQuickPasteAmalgam.Visible = false;
        }
        else
        {
            panelQuickPasteAmalgam.Visible = true;
        } 
        if (ToolButItems.List != null)
        {
            layoutToolBar();
            if (PatCur == null)
            {
                if (usingEcwTightOrFull())
                {
                    if (!Environment.Is64BitOperatingSystem)
                    {
                        ToolBarMain.getButtons().get___idx("Rx").setEnabled(false);
                    }
                     
                }
                else
                {
                    //eRx already disabled because it is never enabled for eCW Tight or Full
                    ToolBarMain.getButtons().get___idx("Rx").setEnabled(false);
                    ToolBarMain.getButtons().get___idx("eRx").setEnabled(false);
                } 
                ToolBarMain.getButtons().get___idx("LabCase").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Perio").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Ortho").setEnabled(false);
                ToolBarMain.getButtons().get___idx("Consent").setEnabled(false);
                ToolBarMain.getButtons().get___idx("ToothChart").setEnabled(false);
                ToolBarMain.getButtons().get___idx("ExamSheet").setEnabled(false);
                if (usingEcwTight())
                {
                    ToolBarMain.getButtons().get___idx("Commlog").setEnabled(false);
                    webBrowserEcw.Url = null;
                }
                 
                if (PrefC.getBool(PrefName.ShowFeatureEhr))
                {
                    ToolBarMain.getButtons().get___idx("EHR").setEnabled(false);
                }
                 
                if (ToolBarMain.getButtons().get___idx("HL7") != null)
                {
                    ToolBarMain.getButtons().get___idx("HL7").setEnabled(false);
                }
                 
            }
             
        }
         
    }

    /**
    * This reduces the number of places where Programs.UsingEcwTight() is called.  This helps with organization.  All calls from ContrChart must pass through here.  They also must have been checked to not involve the Orion bridge or layout logic.
    */
    private boolean usingEcwTight() throws Exception {
        return Programs.usingEcwTightMode();
    }

    /**
    * This reduces the number of places where Programs.UsingEcwTightOrFull() is called.  This helps with organization.  All calls from ContrChart must pass through here.  They also must have been checked to not involve the Orion bridge or layout logic.
    */
    private boolean usingEcwTightOrFull() throws Exception {
        return Programs.usingEcwTightOrFullMode();
    }

    /**
    * Causes the toolbars to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        OpenDental.UI.ODToolBarButton button;
        if (usingEcwTightOrFull())
        {
            if (!Environment.Is64BitOperatingSystem)
            {
                ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"New Rx"), 1, "", "Rx"));
            }
             
        }
        else
        {
            //don't add eRx
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"New Rx"), 1, "", "Rx"));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"eRx"), 1, "", "eRx"));
        } 
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"LabCase"), -1, "", "LabCase"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Perio Chart"), 2, "", "Perio"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Ortho Chart"), -1, "", "Ortho"));
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Consent"), -1, "", "Consent");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
        button.setDropDownMenu(menuConsent);
        ToolBarMain.getButtons().add(button);
        //if(PrefC.GetBool(PrefName.ToothChartMoveMenuToRight)) {
        //	ToolBarMain.Buttons.Add(new ODToolBarButton("                   .",-1,"",""));
        //}
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Tooth Chart"), -1, "", "ToothChart");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.DropDownButton);
        button.setDropDownMenu(menuToothChart);
        ToolBarMain.getButtons().add(button);
        button = new OpenDental.UI.ODToolBarButton(Lan.g(this,"Exam Sheet"), -1, "", "ExamSheet");
        button.setStyle(OpenDental.UI.ODToolBarButtonStyle.PushButton);
        ToolBarMain.getButtons().add(button);
        if (usingEcwTight())
        {
            //button will show in this toolbar instead of the usual one.
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Commlog"), 4, Lan.g(this,"New Commlog Entry"), "Commlog"));
        }
         
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("EHR", -1, "", "EHR"));
        }
         
        if (HL7Defs.isExistingHL7Enabled())
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(HL7Defs.getOneDeepEnabled().Description, -1, "", "HL7"));
        }
         
        ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.ChartModule);
        for (int i = 0;i < toolButItems.Count;i++)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
        }
        ToolBarMain.Invalidate();
        Plugins.hookAddCode(this,"ContrChart.LayoutToolBar_end",PatCur);
    }

    /**
    * This function does not follow our usual pattern. This function is just like ModuleSelected() but also pulls down prescription data for the current patient from the NewCrop web service. It was created to limit the number of times that NewCrop prescriptions are refreshed. Each time that NewCrop data is refreshed, the user must wait at least a few seconds. This function allows the chart to fully load, then a wait cursor displays while the web service call is being performed. If new data is pulled in from NewCrop, then the module is refreshed again to show the new prescriptions. Only called from FormOpenDental when the Chart module button is clicked or when a new patient is selected from within the Chart.
    */
    public void moduleSelectedNewCrop(long patNum) throws Exception {
        moduleSelected(patNum);
        this.Cursor = Cursors.WaitCursor;
        Application.DoEvents();
        if (newCropRefreshPrescriptions())
        {
            moduleSelected(patNum);
        }
         
        this.Cursor = Cursors.Default;
    }

    /**
    * 
    */
    public void moduleSelected(long patNum) throws Exception {
        moduleSelected(patNum,true);
    }

    /**
    * Only use this overload when isFullRefresh is set to false.  This is ONLY in a few places and only for eCW at this point.  Speeds things up by refreshing less data.
    */
    public void moduleSelected(long patNum, boolean isFullRefresh) throws Exception {
        easyHideClinicalData();
        refreshModuleData(patNum,isFullRefresh);
        refreshModuleScreen();
        Plugins.hookAddCode(this,"ContrChart.ModuleSelected_end",patNum);
    }

    /**
    * 
    */
    public void moduleUnselected() throws Exception {
        //toothChart.Dispose();?
        if (FamCur == null)
            return ;
         
        if (TreatmentNoteChanged)
        {
            PatientNoteCur.Treatment = textTreatmentNotes.Text;
            PatientNotes.update(PatientNoteCur,PatCur.Guarantor);
            TreatmentNoteChanged = false;
        }
         
        FamCur = null;
        PatCur = null;
        PlanList = null;
        SubList = null;
        Plugins.hookAddCode(this,"ContrChart.ModuleUnselected_end");
    }

    /**
    * isFullRefresh is ONLY for eCW at this point.
    */
    private void refreshModuleData(long patNum, boolean isFullRefresh) throws Exception {
        if (patNum == 0)
        {
            PatCur = null;
            FamCur = null;
            return ;
        }
         
        if (!isFullRefresh)
        {
            return ;
        }
         
        FamCur = Patients.getFamily(patNum);
        PatCur = FamCur.getPatient(patNum);
        SubList = InsSubs.refreshForFam(FamCur);
        PlanList = InsPlans.refreshForSubList(SubList);
        PatPlanList = PatPlans.refresh(patNum);
        BenefitList = Benefits.refresh(PatPlanList,SubList);
        //todo: track down where this is altered.  Optimize for eCW:
        PatientNoteCur = PatientNotes.refresh(patNum,PatCur.Guarantor);
        if (PrefC.getAtoZfolderUsed())
        {
            patFolder = ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath());
        }
         
        //GetImageFolder();
        DocumentList = Documents.getAllWithPat(patNum);
        //todo: might change for planned appt:
        ApptList = Appointments.getForPat(patNum);
        //todo: refresh as needed elsewhere:
        ToothInitialList = ToothInitials.refresh(patNum);
        //todo: optimize for Full mode:
        PatFieldList = PatFields.refresh(patNum);
    }

    private void refreshModuleScreen() throws Exception {
        //ParentForm.Text=Patients.GetMainTitle(PatCur);
        if (PatCur == null)
        {
            //groupShow.Enabled=false;
            gridPtInfo.Enabled = false;
            //tabPlanned.Enabled=false;
            toothChart.Enabled = false;
            gridProg.Enabled = false;
            //if(UsingEcwTight()) {
            if (usingEcwTightOrFull())
            {
                if (!Environment.Is64BitOperatingSystem)
                {
                    ToolBarMain.getButtons().get___idx("Rx").setEnabled(false);
                }
                 
            }
            else
            {
                //eRx already disabled because it is never enabled for eCW Tight or Full
                ToolBarMain.getButtons().get___idx("Rx").setEnabled(false);
                ToolBarMain.getButtons().get___idx("eRx").setEnabled(false);
            } 
            ToolBarMain.getButtons().get___idx("LabCase").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Perio").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Ortho").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Consent").setEnabled(false);
            ToolBarMain.getButtons().get___idx("ToothChart").setEnabled(false);
            ToolBarMain.getButtons().get___idx("ExamSheet").setEnabled(false);
            if (usingEcwTight())
            {
                ToolBarMain.getButtons().get___idx("Commlog").setEnabled(false);
                webBrowserEcw.Url = null;
            }
             
            //if(FormOpenDental.ObjSomeEhrSuperClass!=null) {//didn't work
            if (ToolBarMain.getButtons().get___idx("EHR") != null)
            {
                ToolBarMain.getButtons().get___idx("EHR").setEnabled(false);
            }
             
            if (ToolBarMain.getButtons().get___idx("HL7") != null)
            {
                ToolBarMain.getButtons().get___idx("HL7").setEnabled(false);
            }
             
            tabProc.Enabled = false;
            butAddKey.Enabled = false;
            butForeignKey.Enabled = false;
            butPhoneNums.Enabled = false;
        }
        else
        {
            //groupShow.Enabled=true;
            gridPtInfo.Enabled = true;
            //groupPlanned.Enabled=true;
            toothChart.Enabled = true;
            gridProg.Enabled = true;
            //if(UsingEcwTight()) {
            if (usingEcwTightOrFull())
            {
                if (!Environment.Is64BitOperatingSystem)
                {
                    ToolBarMain.getButtons().get___idx("Rx").setEnabled(true);
                }
                 
            }
            else
            {
                //don't enable eRx
                ToolBarMain.getButtons().get___idx("Rx").setEnabled(true);
                ToolBarMain.getButtons().get___idx("eRx").setEnabled(true);
            } 
            ToolBarMain.getButtons().get___idx("LabCase").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Perio").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Ortho").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Consent").setEnabled(true);
            ToolBarMain.getButtons().get___idx("ToothChart").setEnabled(true);
            ToolBarMain.getButtons().get___idx("ExamSheet").setEnabled(true);
            if (usingEcwTightOrFull())
            {
                if (usingEcwTight())
                {
                    ToolBarMain.getButtons().get___idx("Commlog").setEnabled(true);
                }
                 
                //the following sequence also gets repeated after exiting the Rx window to refresh.
                String strAppServer = "";
                try
                {
                    if (ECW.UserId == 0 || String.IsNullOrEmpty(ECW.EcwConfigPath))
                    {
                        webBrowserEcw.Url = null;
                        labelECWerror.Text = "This panel does not display unless\r\nOpen Dental is launched from inside eCW";
                        labelECWerror.Visible = true;
                    }
                    else
                    {
                        String uristring = ProgramProperties.getPropVal(Programs.getProgramNum(ProgramName.eClinicalWorks),"MedicalPanelUrl");
                        String path = "";
                        if (StringSupport.equals(uristring, ""))
                        {
                            XmlTextReader reader = new XmlTextReader(ECW.EcwConfigPath);
                            while (reader.Read())
                            {
                                if (StringSupport.equals(reader.Name.ToString(), "server"))
                                {
                                    strAppServer = reader.ReadString().Trim();
                                }
                                 
                            }
                            path = "http://" + strAppServer + "/mobiledoc/jsp/dashboard/Overview.jsp" + "?ptId=" + PatCur.PatNum.ToString() + "&panelName=overview&pnencid=" + ECW.AptNum.ToString() + "&context=progressnotes&TrUserId=" + ECW.UserId.ToString();
                            //set cookie
                            if (!String.IsNullOrEmpty(ECW.JSessionId))
                            {
                                internetSetCookie("http://" + strAppServer,null,"JSESSIONID = " + ECW.JSessionId);
                            }
                             
                            if (!String.IsNullOrEmpty(ECW.JSessionIdSSO))
                            {
                                internetSetCookie("http://" + strAppServer,null,"JSESSIONIDSSO = " + ECW.JSessionIdSSO);
                            }
                             
                            if (!String.IsNullOrEmpty(ECW.LBSessionId))
                            {
                                internetSetCookie("http://" + strAppServer,null,"LBSESSIONID = " + ECW.LBSessionId);
                            }
                             
                        }
                        else
                        {
                            path = uristring + "?ptId=" + PatCur.PatNum.ToString() + "&panelName=overview&pnencid=" + ECW.AptNum.ToString() + "&context=progressnotes&TrUserId=" + ECW.UserId.ToString();
                            //parse out with regex: uristring
                            Match match = Regex.Match(uristring, "\\b([^:]+://[^/]+)/");
                            //http://servername
                            if (!match.Success || match.Groups.Count < 2)
                            {
                                throw new Exception("Invalid URL saved in the Medical Panel URL field of the eClinicalWorks program link.");
                            }
                             
                            //if no match, or match but no group 1 to grab
                            String cookieUrl = match.Groups[1].Value;
                            //set cookie
                            if (!String.IsNullOrEmpty(ECW.JSessionId))
                            {
                                internetSetCookie(cookieUrl,null,"JSESSIONID = " + ECW.JSessionId);
                            }
                             
                            if (!String.IsNullOrEmpty(ECW.JSessionIdSSO))
                            {
                                internetSetCookie(cookieUrl,null,"JSESSIONIDSSO = " + ECW.JSessionIdSSO);
                            }
                             
                            if (!String.IsNullOrEmpty(ECW.LBSessionId))
                            {
                                internetSetCookie(cookieUrl,null,"LBSESSIONID = " + ECW.LBSessionId);
                            }
                             
                        } 
                        //navigate
                        webBrowserEcw.Navigate(path);
                        labelECWerror.Visible = false;
                    } 
                }
                catch (Exception ex)
                {
                    webBrowserEcw.Url = null;
                    labelECWerror.Text = "Error: " + ex.Message;
                    labelECWerror.Visible = true;
                }
            
            }
             
            if (PrefC.getBool(PrefName.ShowFeatureEhr))
            {
                //didn't work either
                //if(ToolBarMain.Buttons["EHR"]!=null) {
                ToolBarMain.getButtons().get___idx("EHR").setEnabled(true);
            }
             
            if (ToolBarMain.getButtons().get___idx("HL7") != null)
            {
                ToolBarMain.getButtons().get___idx("HL7").setEnabled(true);
            }
             
            tabProc.Enabled = true;
            butAddKey.Enabled = true;
            butForeignKey.Enabled = true;
            butPhoneNums.Enabled = true;
            if (PrevPtNum != PatCur.PatNum)
            {
                //reset to TP status on every new patient selected
                if (PrefC.getBool(PrefName.AutoResetTPEntryStatus))
                {
                    radioEntryTP.Select();
                }
                 
                PrevPtNum = PatCur.PatNum;
            }
             
        } 
        if (Programs.getUsingOrion())
        {
            radioEntryC.Visible = false;
            radioEntryEC.Visible = false;
            radioEntryR.Visible = false;
            radioEntryCn.Visible = false;
            radioEntryEO.Location = new Point(radioEntryEO.Location.X, 31);
            groupBox2.Height = 54;
            menuItemSetComplete.Visible = false;
        }
         
        ToolBarMain.Invalidate();
        clearButtons();
        fillChartViewsGrid();
        if (IsDistributorKey)
        {
            fillCustomerTab();
        }
         
        fillProgNotes();
        fillPlanned();
        fillPtInfo();
        fillDxProcImage();
        fillImages();
    }

    private void easyHideClinicalData() throws Exception {
        if (PrefC.getBool(PrefName.EasyHideClinical))
        {
            gridPtInfo.Visible = false;
            checkShowE.Visible = false;
            checkShowR.Visible = false;
            checkRx.Visible = false;
            checkComm.Visible = false;
            checkNotes.Visible = false;
            butShowNone.Visible = false;
            butShowAll.Visible = false;
            //panelEnterTx.Visible=false;//next line changes it, though
            radioEntryEC.Visible = false;
            radioEntryEO.Visible = false;
            radioEntryR.Visible = false;
            labelDx.Visible = false;
            listDx.Visible = false;
            labelPrognosis.Visible = false;
            comboPrognosis.Visible = false;
        }
        else
        {
            gridPtInfo.Visible = true;
            checkShowE.Visible = true;
            checkShowR.Visible = true;
            checkRx.Visible = true;
            checkComm.Visible = true;
            checkNotes.Visible = true;
            butShowNone.Visible = true;
            butShowAll.Visible = true;
            radioEntryEC.Visible = true;
            radioEntryEO.Visible = true;
            radioEntryR.Visible = true;
            labelDx.Visible = true;
            listDx.Visible = true;
            labelPrognosis.Visible = true;
            comboPrognosis.Visible = true;
        } 
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            //standard predefined button
            Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
            if (__dummyScrutVar0.equals("Rx"))
            {
                tool_Rx_Click();
            }
            else if (__dummyScrutVar0.equals("eRx"))
            {
                tool_eRx_Click();
            }
            else if (__dummyScrutVar0.equals("LabCase"))
            {
                tool_LabCase_Click();
            }
            else if (__dummyScrutVar0.equals("Perio"))
            {
                tool_Perio_Click();
            }
            else if (__dummyScrutVar0.equals("Ortho"))
            {
                tool_Ortho_Click();
            }
            else if (__dummyScrutVar0.equals("Anesthesia"))
            {
                tool_Anesthesia_Click();
            }
            else if (__dummyScrutVar0.equals("Consent"))
            {
                tool_Consent_Click();
            }
            else if (__dummyScrutVar0.equals("Commlog"))
            {
                //only for eCW
                tool_Commlog_Click();
            }
            else if (__dummyScrutVar0.equals("ToothChart"))
            {
                tool_ToothChart_Click();
            }
            else if (__dummyScrutVar0.equals("ExamSheet"))
            {
                tool_ExamSheet_Click();
            }
            else if (__dummyScrutVar0.equals("EHR"))
            {
                tool_EHR_Click(false);
            }
            else if (__dummyScrutVar0.equals("HL7"))
            {
                tool_HL7_Click();
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
        OpenDental.PatientSelectedEventArgs eArgs = new OpenDental.PatientSelectedEventArgs(pat);
        if (PatientSelected != null)
        {
            PatientSelected.invoke(this,eArgs);
        }
         
    }

    private void tool_Rx_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.RxCreate))
        {
            return ;
        }
         
        if (usingEcwTightOrFull() && ECW.UserId != 0)
        {
            VBbridges.Ecw.LoadRxForm((int)ECW.UserId, ECW.EcwConfigPath, (int)ECW.AptNum);
            try
            {
                //refresh the right panel:
                String strAppServer = VBbridges.Ecw.GetAppServer((int)ECW.UserId, ECW.EcwConfigPath);
                webBrowserEcw.Url = new Uri("http://" + strAppServer + "/mobiledoc/jsp/dashboard/Overview.jsp?ptId=" + PatCur.PatNum.ToString() + "&panelName=overview&pnencid=" + ECW.AptNum.ToString() + "&context=progressnotes&TrUserId=" + ECW.UserId.ToString());
                labelECWerror.Visible = false;
            }
            catch (Exception ex)
            {
                webBrowserEcw.Url = null;
                labelECWerror.Text = "Error: " + ex.Message;
                labelECWerror.Visible = true;
            }
        
        }
        else
        {
            FormRxSelect FormRS = new FormRxSelect(PatCur);
            FormRS.ShowDialog();
            if (FormRS.DialogResult != DialogResult.OK)
                return ;
             
            moduleSelected(PatCur.PatNum);
            SecurityLogs.makeLogEntry(Permissions.RxCreate,PatCur.PatNum,"Created prescription.");
        } 
    }

    /**
    * Returns false if account ID is blank or not in format of 1 or more digits, followed by 3 random alpha-numberic characters, followed by a 2 digit checksum. Only returns true when the NewCrop Account ID is one that was created by OD.
    */
    private boolean newCropIsAccountIdValid() throws Exception {
        boolean validKey = false;
        String newCropAccountId = PrefC.getString(PrefName.NewCropAccountId);
        if (Regex.IsMatch(newCropAccountId, "[0-9]+\\-[0-9A-Za-z]{3}[0-9]{2}"))
        {
            //Must contain at least 1 digit for patnum, 1 dash, 3 random alpha-numeric characters, then 2 digits for checksum.
            //Verify key checksum to make certain that this key was generated by OD and not a reseller.
            long patNum = PIn.Long(newCropAccountId.Substring(0, newCropAccountId.IndexOf('-')));
            long checkSum = patNum;
            checkSum += Convert.ToByte(newCropAccountId[newCropAccountId.IndexOf('-') + 1]) * 3;
            checkSum += Convert.ToByte(newCropAccountId[newCropAccountId.IndexOf('-') + 2]) * 5;
            checkSum += Convert.ToByte(newCropAccountId[newCropAccountId.IndexOf('-') + 3]) * 7;
            if ((checkSum % 100).ToString().PadLeft(2, '0') == newCropAccountId.Substring(newCropAccountId.Length - 2))
            {
                validKey = true;
            }
             
        }
         
        return validKey;
    }

    /**
    * Returns true if new information was pulled back from NewCrop.
    */
    private boolean newCropRefreshPrescriptions() throws Exception {
        Program programNewCrop = Programs.getCur(ProgramName.NewCrop);
        if (!programNewCrop.Enabled)
        {
            return false;
        }
         
        if (PatCur == null)
        {
            return false;
        }
         
        String newCropAccountId = PrefC.getString(PrefName.NewCropAccountId);
        if (StringSupport.equals(newCropAccountId, ""))
        {
            return false;
        }
         
        //We check for NewCropAccountID validity below, but we also need to be sure to exit this check for resellers if blank.
        if (!newCropIsAccountIdValid())
        {
            //The NewCropAccountID will be invalid for resellers, because the checksum will be wrong.
            //Therefore, resellers should be allowed to continue if both the NewCropName and NewCropPassword are specified. NewCrop does not allow blank passwords.
            if (StringSupport.equals(PrefC.getString(PrefName.NewCropName), "") || StringSupport.equals(PrefC.getString(PrefName.NewCropPassword), ""))
            {
                return false;
            }
             
        }
         
        Update1 wsNewCrop = new Update1();
        //New Crop web services interface.
        Credentials credentials = new Credentials();
        AccountRequest accountRequest = new AccountRequest();
        PatientRequest patientRequest = new PatientRequest();
        PrescriptionHistoryRequest prescriptionHistoryRequest = new PrescriptionHistoryRequest();
        PatientInformationRequester patientInfoRequester = new PatientInformationRequester();
        Result response = new Result();
        credentials.setPartnerName(ErxXml.getNewCropPartnerName());
        credentials.setName(ErxXml.getNewCropAccountName());
        credentials.setPassword(ErxXml.getNewCropAccountPasssword());
        accountRequest.setAccountId(newCropAccountId);
        accountRequest.setSiteId("1");
        //Accounts are always created with SiteId=1.
        patientRequest.setPatientId(POut.Long(PatCur.PatNum));
        prescriptionHistoryRequest.setStartHistory(new DateTime(2012, 11, 2));
        //Only used for archived prescriptions. This is the date of first release for NewCrop integration.
        prescriptionHistoryRequest.setEndHistory(DateTime.Now);
        //Only used for archived prescriptions.
        //Prescription Archive Status Values:
        //N = Not archived (i.e. Current Medication)
        //Y = Archived (i.e. Previous Mediation)
        //% = Both Not Archived and Archived
        //Note: This field will contain values other than Y,N in future releases.
        prescriptionHistoryRequest.setPrescriptionArchiveStatus("N");
        //Prescription Status Values:
        //C = Completed Prescription
        //P = Pending Medication
        //% = Both C and P.
        prescriptionHistoryRequest.setPrescriptionStatus("C");
        //Prescription Sub Status Values:
        //% = All meds (Returns all meds regardless of the sub status)
        //A = NS (Returns only meds that have a 'NS' - Needs staff sub status)
        //U = DR (Returns only meds that have a 'DR' - Needs doctor review sub status)
        //P = Renewal Request that has been selected for processing on the NewCrop screens - it has not yet been denied, denied and re-written or accepted
        //S = Standard Rx (Returns only meds that have an 'InProc' - InProcess sub status)
        //D = DrugSet source - indicates the prescription was created by selecting the medication from the DrugSet selection box on the ComposeRx page
        //O = Outside Prescription - indicates the prescription was created on the MedEntry page, not prescribed.
        prescriptionHistoryRequest.setPrescriptionSubStatus("S");
        patientInfoRequester.setUserType("Staff");
        //Allowed values: Doctor,Staff
        if (Security.getCurUser().ProvNum != 0)
        {
            //If the current OD user is associated to a doctor, then the request is from a doctor, otherwise from a staff member.
            patientInfoRequester.setUserType("Doctor");
        }
         
        patientInfoRequester.setUserId(POut.Long(Security.getCurUser().UserNum));
        //Send the request to NewCrop. Always returns all current medications, and returns medications between the StartHistory and EndHistory dates if requesting archived medications.
        //The patientIdType parameter was added for another vendor and is not often used. We do not use this field. We must pass empty string.
        //The includeSchema parameter is useful for first-time debugging, but in release mode, we should pass N for no.
        wsNewCrop.Timeout = 3000;
        try
        {
            //3 second. The default is 100 seconds, but we cannot wait that long, because prescriptions are checked each time the Chart is refreshed. 1 second is too little, 2 seconds works most of the time. 3 seconds is safe.
            response = wsNewCrop.getPatientFullMedicationHistory6(credentials,accountRequest,patientRequest,prescriptionHistoryRequest,patientInfoRequester,"","N");
        }
        catch (Exception __dummyCatchVar0)
        {
            //An exception is thrown when the timeout is reached, or when the NewCrop servers are not accessible (because the servers are down, or because local internet is down).
            //We used to show a popup here, but users found it annoying when the NewCrop severs were down.
            //Instead, we now silently log a warning message into the Application log within the system EventViewer, so it is not annoying, but there is a way for the user to know if there was a problem.
            if (!EventLog.SourceExists("OpenDental"))
            {
                EventLog.CreateEventSource("OpenDental", "Application");
            }
             
            EventLog.WriteEntry("OpenDental", "Failed to communicate with NewCrop to retrieve completed prescription information. " + "A firewall or antivirus application might be blocking Open Dental, or this computer might not be able to see secure.newcropaccounts.com due to a network name resolution (DNS) issue. " + "If you do not use electronic prescriptions, consider disabling the NewCrop program link in Setup | Program Links.", EventLogEntryType.Warning);
            return false;
        }

        //response.Message = Error message if error.
        //response.RowCount = Number of prescription records returned.
        //response.Status = Status of request. "OK" = success.
        //response.Timing = Not sure what this is for. Tells us how quickly the server responded to the request?
        //response.XmlResponse = The XML data returned, encoded in base 64.
        if (response.getStatus() != StatusType.OK)
        {
            return false;
        }
         
        //Other statuses include Fail (ex if credentials are invalid), NotFound (ex if patientId invalid or accoundId invalid), Unknown (no known examples yet)
        //For now we simply abort gracefully.
        byte[] xmlResponseBytes = Convert.FromBase64String(response.getXmlResponse());
        String xmlResponse = Encoding.UTF8.GetString(xmlResponseBytes);
        XmlDocument xml = new XmlDocument();
        try
        {
            xml.LoadXml(xmlResponse);
        }
        catch (Exception __dummyCatchVar1)
        {
            return false;
        }

        //In case NewCrop returns invalid XML.
        //abort gracefully
        DateTime rxStartDateT = PrefC.getDateT(PrefName.ElectronicRxDateStartedUsing131);
        XmlNode nodeNewDataSet = xml.FirstChild;
        for (Object __dummyForeachVar1 : nodeNewDataSet.ChildNodes)
        {
            XmlNode nodeTable = (XmlNode)__dummyForeachVar1;
            RxPat rxOld = null;
            MedicationPat medOrderOld = null;
            RxPat rx = new RxPat();
            //rx.IsControlled not important.  Only used in sending, but this Rx was already sent.
            rx.Disp = "";
            rx.DosageCode = "";
            rx.Drug = "";
            rx.Notes = "";
            rx.Refills = "";
            rx.SendStatus = RxSendStatus.SentElect;
            rx.Sig = "";
            String additionalSig = "";
            boolean isProv = true;
            long rxCui = 0;
            String strDrugName = "";
            String strGenericName = "";
            String strProvNumOrNpi = "";
            for (Object __dummyForeachVar0 : nodeTable.ChildNodes)
            {
                //We used to send ProvNum in LicensedPrescriber.ID to NewCrop, but now we send NPI. We will receive ProvNum for older prescriptions.
                XmlNode nodeRxFieldParent = (XmlNode)__dummyForeachVar0;
                XmlNode nodeRxField = nodeRxFieldParent.FirstChild;
                if (nodeRxField == null)
                {
                    continue;
                }
                 
                Name.APPLY __dummyScrutVar1 = nodeRxFieldParent.Name.ToLower();
                if (__dummyScrutVar1.equals("dispense"))
                {
                    //ex 5.555
                    rx.Disp = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("druginfo"))
                {
                    //ex lisinopril 5 mg Tab
                    rx.Drug = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("drugname"))
                {
                    //ex lisinopril
                    strDrugName = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("externalpatientid"))
                {
                    //patnum passed back from the compose request that initiated this prescription
                    rx.PatNum = PIn.Long(nodeRxField.Value);
                }
                else if (__dummyScrutVar1.equals("externalphysicianid"))
                {
                    //NPI passed back from the compose request that initiated this prescription.  For older prescriptions, this will be ProvNum.
                    strProvNumOrNpi = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("externaluserid"))
                {
                    //The person who ordered the prescription. Is a ProvNum when provider, or an EmployeeNum when an employee. If EmployeeNum, then is prepended with "emp" because of how we sent it to NewCrop in the first place.
                    if (nodeRxField.Value.StartsWith("emp"))
                    {
                        isProv = false;
                    }
                     
                }
                else if (__dummyScrutVar1.equals("genericname"))
                {
                    strGenericName = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("patientfriendlysig"))
                {
                    //The concat of all the codified fields.
                    rx.Sig = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("pharmacyncpdp"))
                {
                }
                else //ex 9998888
                //We will use this information in the future to find a pharmacy already entered into OD, or to create one dynamically if it does not exist.
                //rx.PharmacyNum;//Get the pharmacy where pharmacy.PharmID = node.Value
                if (__dummyScrutVar1.equals("prescriptiondate"))
                {
                    rx.RxDate = PIn.DateT(nodeRxField.Value);
                }
                else if (__dummyScrutVar1.equals("prescriptionguid"))
                {
                    //32 characters with 4 hyphens. ex ba4d4a84-af0a-4cbf-9437-36feda97d1b6
                    rx.NewCropGuid = nodeRxField.Value;
                    rxOld = RxPats.GetRxNewCrop(nodeRxField.Value);
                    medOrderOld = MedicationPats.GetMedicationOrderByNewCropGuid(nodeRxField.Value);
                }
                else if (__dummyScrutVar1.equals("prescriptionnotes"))
                {
                    //from the Additional Sig box at the bottom
                    additionalSig = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("refills"))
                {
                    //ex 1
                    rx.Refills = nodeRxField.Value;
                }
                else if (__dummyScrutVar1.equals("rxcui"))
                {
                    //ex 311354
                    rxCui = PIn.Long(nodeRxField.Value);
                }
                              
            }
            //The RxCui is not returned with all prescriptions, so it can be zero (not set).
            //end inner foreach
            if (rx.RxDate < rxStartDateT)
            {
                continue;
            }
             
            //Ignore prescriptions created before version 13.1.14, because those prescriptions were entered manually by the user.
            if (!StringSupport.equals(additionalSig, ""))
            {
                if (!StringSupport.equals(rx.Sig, ""))
                {
                    //If patient friend SIG is present.
                    rx.Sig += " ";
                }
                 
                rx.Sig += additionalSig;
            }
             
            //Determine the provider. This is a mess, because we used to send ProvNum in the outgoing XML LicensedPrescriber.ID,
            //but now we send NPI to avoid multiple billing charges for two provider records with the same NPI
            //(the same doctor entered multiple times, for example, one provider for each clinic).
            ErxLog erxLog = ErxLogs.getLatestForPat(rx.PatNum,rx.RxDate);
            //Locate the original request corresponding to this prescription.
            if ((erxLog == null || erxLog.ProvNum == 0))
            {
                //Not found or the provnum is unknown.
                //The erxLog.ProvNum will be 0 for prescriptions fetched from NewCrop before version 13.3. Could also happen if
                //prescriptions were created when NewCrop was brand new (right before ErxLog was created),
                //or if someone lost a database and they are downloading all the prescriptions from scratch again.
                if (rxOld == null)
                {
                    for (int j = 0;j < ProviderC.getListShort().Count;j++)
                    {
                        //The prescription is being dowloaded for the first time, or is being downloaded again after it was deleted manually by the user.
                        //Try to locate a visible provider matching the NPI on the prescription.
                        if (strProvNumOrNpi.Length == 10 && StringSupport.equals(ProviderC.getListShort()[j].NationalProvID, strProvNumOrNpi))
                        {
                            rx.ProvNum = ProviderC.getListShort()[j].ProvNum;
                            break;
                        }
                         
                    }
                    if (rx.ProvNum == 0)
                    {
                        for (int j = 0;j < ProviderC.getListLong().Count;j++)
                        {
                            //No visible provider found matching the NPI on the prescription.
                            //Try finding a hidden provider matching the NPI on the prescription, or a matching provnum.
                            if (strProvNumOrNpi.Length == 10 && StringSupport.equals(ProviderC.getListLong()[j].NationalProvID, strProvNumOrNpi))
                            {
                                rx.ProvNum = ProviderC.getListLong()[j].ProvNum;
                                break;
                            }
                             
                            if (StringSupport.equals(ProviderC.getListLong()[j].ProvNum.ToString(), strProvNumOrNpi))
                            {
                                rx.ProvNum = ProviderC.getListLong()[j].ProvNum;
                                break;
                            }
                             
                        }
                    }
                     
                    //If rx.ProvNum is still zero, then that means the provider NPI/ProvNum has been modified or somehow deleted (for example, database was lost) for the provider record originally used.
                    if (rx.ProvNum == 0)
                    {
                        //Catch all
                        if (PatCur.PriProv != 0)
                        {
                            rx.ProvNum = PatCur.PriProv;
                        }
                        else
                        {
                            rx.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
                        } 
                    }
                     
                }
                else
                {
                    //The prescription has already been downloaded in the past.
                    rx.ProvNum = rxOld.ProvNum;
                } 
            }
            else
            {
                //Preserve the provnum if already in the database, because it may have already been corrected by the user after the previous download.
                rx.ProvNum = erxLog.ProvNum;
            } 
            if (rxOld == null)
            {
                rx.setIsNew(true);
                //Might not be necessary, but does not hurt.
                RxPats.insert(rx);
            }
            else
            {
                //The prescription was already in our database. Update it.
                rx.RxNum = rxOld.RxNum;
                RxPats.update(rx);
            } 
            //If rxCui==0, then NewCrop did not provide an RxCui.  Attempt to locate an RxCui using the other provided drug information.  An RxCui is not required for our program.  Meds missing an RxCui are not exported in CCD messages.
            if (rxCui == 0 && !StringSupport.equals(strDrugName, ""))
            {
                List<RxNorm> listRxNorms = RxNorms.getListByCodeOrDesc(strDrugName,true,true);
                //Exact case insensitive match ignoring numbers.
                if (listRxNorms.Count > 0)
                {
                    rxCui = PIn.Long(listRxNorms[0].RxCui);
                }
                 
            }
             
            //If rxCui==0, then NewCrop did not provide an RxCui and we could not locate an RxCui by DrugName.  Try searching by GenericName.
            if (rxCui == 0 && !StringSupport.equals(strGenericName, ""))
            {
                List<RxNorm> listRxNorms = RxNorms.getListByCodeOrDesc(strGenericName,true,true);
                //Exact case insensitive match ignoring numbers.
                if (listRxNorms.Count > 0)
                {
                    rxCui = PIn.Long(listRxNorms[0].RxCui);
                }
                 
            }
             
            //If rxCui==0, then NewCrop did not provide an RxCui and we could not locate an RxCui by DrugName or GenericName.
            if (rxCui == 0)
            {
            }
             
            //We may need to enhance in future to support more advanced RxNorm searches.
            //For example: DrugName=Cafatine, DrugInfo=Cafatine 1 mg-100 mg Tab, GenericName=ergotamine-caffeine.
            //This drug could not be found by DrugName nor GenericName, but could be found when the GenericName was split by non-alpha characters, then the words in the generic name were swapped.
            //Namely, "caffeine ergotamine" is in the RxNorm table.
            MedicationPats.insertOrUpdateMedOrderForRx(rx,rxCui,isProv);
        }
        return true;
    }

    //MedicationNum of 0, because we do not want to bloat the medication list in OD. In this special situation, we instead set the MedDescript, RxCui and NewCropGuid columns.
    //end foreach
    private void tool_eRx_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.RxCreate))
        {
            return ;
        }
         
        Program programNewCrop = Programs.getCur(ProgramName.NewCrop);
        String newCropAccountId = PrefC.getString(PrefName.NewCropAccountId);
        if (StringSupport.equals(newCropAccountId, ""))
        {
            //NewCrop has not been enabled yet.
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Are you sure you want to enable NewCrop electronic prescriptions?  The cost is $15/month for each prescribing provider.  NewCrop only works for the United States and its territories, including Puerto Rico."))
            {
                return ;
            }
             
            //prepare the xml document to send--------------------------------------------------------------------------------------
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = ("    ");
            StringBuilder strbuild = new StringBuilder();
            XmlWriter writer = XmlWriter.Create(strbuild, settings);
            try
            {
                {
                    writer.WriteStartElement("CustomerIdRequest");
                    writer.WriteStartElement("RegistrationKey");
                    writer.WriteString(PrefC.getString(PrefName.RegistrationKey));
                    writer.WriteEndElement();
                    writer.WriteEndElement();
                }
            }
            finally
            {
                if (writer != null)
                    Disposable.mkDisposable(writer).dispose();
                 
            }
            OpenDental.customerUpdates.Service1 updateService = new OpenDental.customerUpdates.Service1();
            updateService.setUrl(PrefC.getString(PrefName.UpdateServerAddress));
            if (!StringSupport.equals(PrefC.getString(PrefName.UpdateWebProxyAddress), ""))
            {
                IWebProxy proxy = new WebProxy(PrefC.getString(PrefName.UpdateWebProxyAddress));
                ICredentials cred = new NetworkCredential(PrefC.getString(PrefName.UpdateWebProxyUserName), PrefC.getString(PrefName.UpdateWebProxyPassword));
                proxy.Credentials = cred;
                updateService.Proxy = proxy;
            }
             
            String patNum = "";
            try
            {
                String result = updateService.RequestCustomerID(strbuild.ToString());
                //may throw error
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(result);
                XmlNode node = doc.SelectSingleNode("//CustomerIdResponse");
                if (node != null)
                {
                    patNum = node.InnerText;
                }
                 
                if (StringSupport.equals(patNum, ""))
                {
                    throw new ApplicationException("Failed to validate registration key.");
                }
                 
                newCropAccountId = patNum;
                newCropAccountId += "-" + CodeBase.MiscUtils.createRandomAlphaNumericString(3);
                long checkSum = PIn.Long(patNum);
                checkSum += Convert.ToByte(newCropAccountId[newCropAccountId.IndexOf('-') + 1]) * 3;
                checkSum += Convert.ToByte(newCropAccountId[newCropAccountId.IndexOf('-') + 2]) * 5;
                checkSum += Convert.ToByte(newCropAccountId[newCropAccountId.IndexOf('-') + 3]) * 7;
                newCropAccountId += (checkSum % 100).ToString().PadLeft(2, '0');
                Prefs.updateString(PrefName.NewCropAccountId,newCropAccountId);
                programNewCrop.Enabled = true;
                Programs.update(programNewCrop);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }
        
        }
        else
        {
            //newCropAccountId!=""
            if (!programNewCrop.Enabled)
            {
                MessageBox.Show(Lan.g(this,"Electronic prescriptions are currently disabled.") + "\r\n" + Lan.g(this,"To enable, go to Setup | Program Links | NewCrop."));
                return ;
            }
             
            if (!newCropIsAccountIdValid())
            {
                String newCropName = PrefC.getString(PrefName.NewCropName);
                String newCropPassword = PrefC.getString(PrefName.NewCropPassword);
                if (StringSupport.equals(newCropName, "") || StringSupport.equals(newCropPassword, ""))
                {
                    //NewCrop does not allow blank passwords.
                    MsgBox.show(this,"NewCropName preference and NewCropPassword preference must not be blank when using a NewCrop AccountID provided by a reseller.");
                    return ;
                }
                 
            }
             
        } 
        //Validation------------------------------------------------------------------------------------------------------------------------------------------------------
        if (Security.getCurUser().EmployeeNum == 0 && Security.getCurUser().ProvNum == 0)
        {
            MsgBox.show(this,"This user must be associated with either a provider or an employee.  The security admin must make this change before this user can submit prescriptions.");
            return ;
        }
         
        if (PatCur == null)
        {
            MsgBox.show(this,"No patient selected.");
            return ;
        }
         
        String practicePhone = PrefC.getString(PrefName.PracticePhone);
        if (!Regex.IsMatch(practicePhone, "^[0-9]{10}$"))
        {
            //"^[0-9]{10}(x[0-9]+)?$")) {
            MsgBox.show(this,"Practice phone must be exactly 10 digits.");
            return ;
        }
         
        if (practicePhone.StartsWith("555"))
        {
            MsgBox.show(this,"Practice phone cannot start with 555.");
            return ;
        }
         
        if (Regex.IsMatch(practicePhone, "^[0-9]{3}555[0-9]{4}$"))
        {
            MsgBox.show(this,"Practice phone cannot contain 555 in the middle 3 digits.");
            return ;
        }
         
        String practiceFax = PrefC.getString(PrefName.PracticeFax);
        if (!Regex.IsMatch(practiceFax, "^[0-9]{10}(x[0-9]+)?$"))
        {
            MsgBox.show(this,"Practice fax must be exactly 10 digits.");
            return ;
        }
         
        if (practiceFax.StartsWith("555"))
        {
            MsgBox.show(this,"Practice fax cannot start with 555.");
            return ;
        }
         
        if (Regex.IsMatch(practiceFax, "^[0-9]{3}555[0-9]{4}$"))
        {
            MsgBox.show(this,"Practice fax cannot contain 555 in the middle 3 digits.");
            return ;
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeAddress), ""))
        {
            MsgBox.show(this,"Practice address blank.");
            return ;
        }
         
        if (Regex.IsMatch(PrefC.getString(PrefName.PracticeAddress), ".*P\\.?O\\.? .*", RegexOptions.IgnoreCase))
        {
            MsgBox.show(this,"Practice address cannot be a PO BOX.");
            return ;
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeCity), ""))
        {
            MsgBox.show(this,"Practice city blank.");
            return ;
        }
         
        List<String> stateCodes = new List<String>(new String[]{ "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY", "DC", "AS", "FM", "GU", "MH", "MP", "PW", "PR", "UM", "VI" });
        //50 States.
        //US Districts
        //US territories. Reference http://www.itl.nist.gov/fipspubs/fip5-2.htm
        if (stateCodes.IndexOf(PrefC.getString(PrefName.PracticeST)) < 0)
        {
            MsgBox.show(this,"Practice state abbreviation invalid.");
            return ;
        }
         
        String practiceZip = Regex.Replace(PrefC.getString(PrefName.PracticeZip), "[^0-9]*", "");
        //Zip with all non-numeric characters removed.
        if (practiceZip.Length != 9)
        {
            MsgBox.show(this,"Practice zip must be 9 digits.");
            return ;
        }
         
        if (!PrefC.getBool(PrefName.EasyNoClinics) && PatCur.ClinicNum != 0)
        {
            //Using clinics and the patient is assigned to a clinic.
            Clinic clinic = Clinics.getClinic(PatCur.ClinicNum);
            if (!Regex.IsMatch(clinic.Phone, "^[0-9]{10}?$"))
            {
                MessageBox.Show(Lan.g(this,"Clinic phone must be exactly 10 digits") + ": " + clinic.Description);
                return ;
            }
             
            if (clinic.Phone.StartsWith("555"))
            {
                MessageBox.Show(Lan.g(this,"Clinic phone cannot start with 555") + ": " + clinic.Description);
                return ;
            }
             
            if (Regex.IsMatch(clinic.Phone, "^[0-9]{3}555[0-9]{4}$"))
            {
                MessageBox.Show(Lan.g(this,"Clinic phone cannot contain 555 in the middle 3 digits") + ": " + clinic.Description);
                return ;
            }
             
            if (!Regex.IsMatch(clinic.Fax, "^[0-9]{10}?$"))
            {
                MessageBox.Show(Lan.g(this,"Clinic fax must be exactly 10 digits") + ": " + clinic.Description);
                return ;
            }
             
            if (clinic.Fax.StartsWith("555"))
            {
                MessageBox.Show(Lan.g(this,"Clinic fax cannot start with 555") + ": " + clinic.Description);
                return ;
            }
             
            if (Regex.IsMatch(clinic.Fax, "^[0-9]{3}555[0-9]{4}$"))
            {
                MessageBox.Show(Lan.g(this,"Clinic fax cannot contain 555 in the middle 3 digits") + ": " + clinic.Description);
                return ;
            }
             
            if (StringSupport.equals(clinic.Address, ""))
            {
                MessageBox.Show(Lan.g(this,"Clinic address blank") + ": " + clinic.Description);
                return ;
            }
             
            if (Regex.IsMatch(clinic.Address, ".*P\\.?O\\.? .*", RegexOptions.IgnoreCase))
            {
                MessageBox.Show(Lan.g(this,"Clinic address cannot be a PO BOX") + ": " + clinic.Description);
                return ;
            }
             
            if (StringSupport.equals(clinic.City, ""))
            {
                MessageBox.Show(Lan.g(this,"Clinic city blank") + ": " + clinic.Description);
                return ;
            }
             
            if (stateCodes.IndexOf(clinic.State) < 0)
            {
                MessageBox.Show(Lan.g(this,"Clinic state abbreviation invalid") + ": " + clinic.Description);
                return ;
            }
             
            String clinicZip = Regex.Replace(clinic.Zip, "[^0-9]*", "");
            //Zip with all non-numeric characters removed.
            if (clinicZip.Length != 9)
            {
                MessageBox.Show(Lan.g(this,"Clinic zip must be 9 digits") + ": " + clinic.Description);
                return ;
            }
             
        }
         
        Provider prov = null;
        if (Security.getCurUser().ProvNum != 0)
        {
            prov = Providers.getProv(Security.getCurUser().ProvNum);
        }
        else
        {
            prov = Providers.getProv(PatCur.PriProv);
        } 
        if (prov.IsNotPerson)
        {
            MessageBox.Show(Lan.g(this,"Provider must be a person") + ": " + prov.Abbr);
            return ;
        }
         
        String fname = prov.FName.Trim();
        if (StringSupport.equals(fname, ""))
        {
            MessageBox.Show(Lan.g(this,"Provider first name missing") + ": " + prov.Abbr);
            return ;
        }
         
        if (!StringSupport.equals(Regex.Replace(fname, "[^A-Za-z\\-]*", ""), fname))
        {
            MessageBox.Show(Lan.g(this,"Provider first name can only contain letters and dashes.") + ": " + prov.Abbr);
            return ;
        }
         
        String lname = prov.LName.Trim();
        if (StringSupport.equals(lname, ""))
        {
            MessageBox.Show(Lan.g(this,"Provider last name missing") + ": " + prov.Abbr);
            return ;
        }
         
        if (!StringSupport.equals(Regex.Replace(lname, "[^A-Za-z\\-]*", ""), lname))
        {
            //Will catch situations such as "Dale Jr. III" and "Ross DMD".
            MessageBox.Show(Lan.g(this,"Provider last name can only contain letters and dashes.  Use the suffix box for I, II, III, Jr, or Sr") + ": " + prov.Abbr);
            return ;
        }
         
        //prov.Suffix is not validated here. In ErxXml.cs, the suffix is converted to the appropriate suffix enumeration value, or defaults to DDS if the suffix does not make sense.
        if (!StringSupport.equals(prov.DEANum.ToLower(), "none") && !Regex.IsMatch(prov.DEANum, "^[A-Za-z]{2}[0-9]{7}$"))
        {
            MessageBox.Show(Lan.g(this,"Provider DEA Number must be 2 letters followed by 7 digits.  If no DEA Number, enter NONE.") + ": " + prov.Abbr);
            return ;
        }
         
        String npi = Regex.Replace(prov.NationalProvID, "[^0-9]*", "");
        //NPI with all non-numeric characters removed.
        if (npi.Length != 10)
        {
            MessageBox.Show(Lan.g(this,"Provider NPI must be exactly 10 digits") + ": " + prov.Abbr);
            return ;
        }
         
        if (StringSupport.equals(prov.StateLicense, ""))
        {
            MessageBox.Show(Lan.g(this,"Provider state license missing") + ": " + prov.Abbr);
            return ;
        }
         
        if (stateCodes.IndexOf(prov.StateWhereLicensed) < 0)
        {
            MessageBox.Show(Lan.g(this,"Provider state where licensed invalid") + ": " + prov.Abbr);
            return ;
        }
         
        Employee emp = null;
        if (Security.getCurUser().EmployeeNum != 0)
        {
            emp = Employees.getEmp(Security.getCurUser().EmployeeNum);
            if (StringSupport.equals(emp.LName, ""))
            {
                //Checked in UI, but check here just in case this database was converted from another software.
                MessageBox.Show(Lan.g(this,"Employee last name missing for user") + ": " + Security.getCurUser().UserName);
                return ;
            }
             
            if (StringSupport.equals(emp.FName, ""))
            {
                //Not validated in UI.
                MessageBox.Show(Lan.g(this,"Employee first name missing for user") + ": " + Security.getCurUser().UserName);
                return ;
            }
             
        }
         
        if (PatCur.Birthdate.Year < 1880)
        {
            MsgBox.show(this,"Patient birthdate missing.");
            return ;
        }
         
        if (!StringSupport.equals(PatCur.State, "") && stateCodes.IndexOf(PatCur.State) < 0)
        {
            MsgBox.show(this,"Patient state abbreviation invalid");
            return ;
        }
         
        //FormErx formErx=new FormErx();
        //formErx.prov=prov;
        //formErx.emp=emp;
        //formErx.pat=PatCur;
        //formErx.ShowDialog();
        String clickThroughXml = ErxXml.buildClickThroughXml(prov,emp,PatCur);
        String xmlBase64 = System.Web.HttpUtility.HtmlEncode(Convert.ToBase64String(ASCIIEncoding.ASCII.GetBytes(clickThroughXml)));
        xmlBase64 = xmlBase64.Replace("+", "%2B");
        //A common base 64 character which needs to be escaped within URLs.
        xmlBase64 = xmlBase64.Replace("/", "%2F");
        //A common base 64 character which needs to be escaped within URLs.
        xmlBase64 = xmlBase64.Replace("=", "%3D");
        //Base 64 strings usually end in '=', which could mean a new parameter definition within the URL so we escape.
        String postdata = "RxInput=base64:" + xmlBase64;
        byte[] PostDataBytes = System.Text.Encoding.UTF8.GetBytes(postdata);
        String additionalHeaders = "Content-Type: application/x-www-form-urlencoded\r\n";
        InternetExplorer IEControl = new InternetExplorer();
        IWebBrowserApp IE = (IWebBrowserApp)IEControl;
        IE.Visible = true;
        String newCropUrl = "https://secure.newcropaccounts.com/interfacev7/rxentry.aspx";
        IE.Navigate(newCropUrl, null, null, PostDataBytes, additionalHeaders);
        ErxLog erxLog = new ErxLog();
        erxLog.PatNum = PatCur.PatNum;
        erxLog.MsgText = clickThroughXml;
        erxLog.ProvNum = prov.ProvNum;
        ErxLogs.insert(erxLog);
    }

    private void tool_LabCase_Click() throws Exception {
        LabCase lab = new LabCase();
        lab.PatNum = PatCur.PatNum;
        lab.ProvNum = Patients.getProvNum(PatCur);
        lab.DateTimeCreated = MiscData.getNowDateTime();
        LabCases.insert(lab);
        //it will be deleted inside the form if user clicks cancel.
        //We need the primary key in order to attach lab slip.
        FormLabCaseEdit FormL = new FormLabCaseEdit();
        FormL.CaseCur = lab;
        FormL.IsNew = true;
        FormL.ShowDialog();
        //needs to always refresh due to complex ok/cancel
        moduleSelected(PatCur.PatNum);
    }

    private void tool_Perio_Click() throws Exception {
        FormPerio FormP = new FormPerio(PatCur);
        FormP.ShowDialog();
    }

    private void tool_Ortho_Click() throws Exception {
        FormOrthoChart FormOC = new FormOrthoChart(PatCur);
        FormOC.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void tool_Anesthesia_Click() throws Exception {
    }

    /*
    			AnestheticData AnestheticDataCur;
    			AnestheticDataCur = new AnestheticData();
    			FormAnestheticRecord FormAR = new FormAnestheticRecord(PatCur, AnestheticDataCur);
    			FormAR.ShowDialog();
    			PatCur = Patients.GetPat(Convert.ToInt32(PatCur.PatNum));
    			OnPatientSelected(Convert.ToInt32(PatCur.PatNum), Convert.ToString(PatCur), true, Convert.ToString(PatCur));
    			FillPtInfo();
    			return;*/
    private void tool_Consent_Click() throws Exception {
        List<SheetDef> listSheets = SheetDefs.getCustomForType(SheetTypeEnum.Consent);
        if (listSheets.Count > 0)
        {
            MsgBox.show(this,"Please use dropdown list.");
            return ;
        }
         
        SheetDef sheetDef = SheetsInternal.getSheetDef(SheetInternalType.Consent);
        Sheet sheet = SheetUtil.createSheet(sheetDef,PatCur.PatNum);
        SheetParameter.setParameter(sheet,"PatNum",PatCur.PatNum);
        SheetFiller.fillFields(sheet);
        Graphics g = this.CreateGraphics();
        SheetUtil.calculateHeights(sheet,g);
        g.Dispose();
        FormSheetFillEdit FormS = new FormSheetFillEdit(sheet);
        FormS.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void tool_ToothChart_Click() throws Exception {
        if (Programs.getUsingOrion())
        {
            menuItemChartSave_Click(this,new EventArgs());
            return ;
        }
         
        MsgBox.show(this,"Please use dropdown list.");
        return ;
    }

    private void tool_ExamSheet_Click() throws Exception {
        FormExamSheets fes = new FormExamSheets();
        fes.PatNum = PatCur.PatNum;
        fes.ShowDialog();
        refreshModuleScreen();
    }

    /**
    * Only used for eCW tight.  Everyone else has the commlog button up in the main toolbar.
    */
    private void tool_Commlog_Click() throws Exception {
        Commlog CommlogCur = new Commlog();
        CommlogCur.PatNum = PatCur.PatNum;
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
        CommlogCur.Mode_ = CommItemMode.Phone;
        CommlogCur.SentOrReceived = CommSentOrReceived.Received;
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        FormCommItem FormCI = new FormCommItem(CommlogCur);
        FormCI.IsNew = true;
        FormCI.ShowDialog();
        if (FormCI.DialogResult == DialogResult.OK)
        {
            moduleSelected(PatCur.PatNum);
        }
         
    }

    private void tool_EHR_Click(boolean onLoadShowOrders) throws Exception {
        //Quarterly key check was removed from here so that any customer can use EHR tools
        //But we require a EHR subscription for them to obtain their MU reports.
        FormEHR FormE = new FormEHR();
        FormE.PatNum = PatCur.PatNum;
        FormE.PatNotCur = PatientNoteCur;
        FormE.PatFamCur = FamCur;
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (FormE.ResultOnClosing == EhrFormResult.PatientSelect)
        {
            onPatientSelected(Patients.getPat(FormE.PatNum));
            moduleSelected(FormE.PatNum);
        }
         
    }

    //private void Tool_EHR_Click_old(bool onLoadShowOrders) {
    //	#if EHRTEST
    //		//so we can step through for debugging.
    //	/*
    //		EhrQuarterlyKey keyThisQ=EhrQuarterlyKeys.GetKeyThisQuarter();
    //		if(keyThisQ==null) {
    //			MessageBox.Show("No quarterly key entered for this quarter.");
    //			return;
    //		}
    //		if(!((FormEHR)FormOpenDental.FormEHR).QuarterlyKeyIsValid((DateTime.Today.Year-2000).ToString(),EhrQuarterlyKeys.MonthToQuarter(DateTime.Today.Month).ToString(),
    //			PrefC.GetString(PrefName.PracticeTitle),keyThisQ.KeyValue)) {
    //			MessageBox.Show("Invalid quarterly key.");
    //			return;
    //		}
    //	*/
    //		((FormEHR)FormOpenDental.FormEHR).PatNum=PatCur.PatNum;
    //		((FormEHR)FormOpenDental.FormEHR).OnShowLaunchOrders=onLoadShowOrders;
    //		((FormEHR)FormOpenDental.FormEHR).ShowDialog();
    //		if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.None) {
    //			//return;
    //		}
    //		if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.RxEdit) {
    //			FormRxEdit FormRXE=new FormRxEdit(PatCur,RxPats.GetRx(((FormEHR)FormOpenDental.FormEHR).LaunchRxNum));
    //			FormRXE.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);//recursive.  The only way out of the loop is EhrFormResult.None.
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.RxSelect) {
    //			FormRxSelect FormRS=new FormRxSelect(PatCur);
    //			FormRS.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.Medical) {
    //			FormMedical formM=new FormMedical(PatientNoteCur,PatCur);
    //			formM.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.PatientEdit) {
    //			FormPatientEdit formP=new FormPatientEdit(PatCur,FamCur);
    //			formP.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.Online) {
    //			FormEhrOnlineAccess formO=new FormEhrOnlineAccess();
    //			formO.PatCur=PatCur;
    //			formO.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.MedReconcile) {
    //			FormMedicationReconcile FormMR=new FormMedicationReconcile();
    //			FormMR.PatCur=PatCur;
    //			FormMR.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.Referrals) {
    //			FormReferralsPatient formRP=new FormReferralsPatient();
    //			formRP.PatNum=PatCur.PatNum;
    //			formRP.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(false);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.MedicationPatEdit) {
    //			FormMedPat formMP=new FormMedPat();
    //			formMP.MedicationPatCur=MedicationPats.GetOne(((FormEHR)FormOpenDental.FormEHR).LaunchMedicationPatNum);
    //			formMP.ShowDialog();
    //			ModuleSelected(PatCur.PatNum);
    //			Tool_EHR_Click(true);
    //		}
    //		else if(((FormEHR)FormOpenDental.FormEHR).ResultOnClosing==EhrFormResult.MedicationPatNew) {
    //			//This cannot happen unless a provider is logged in with a valid ehr key
    //			FormMedications FormM=new FormMedications();
    //			FormM.IsSelectionMode=true;
    //			FormM.ShowDialog();
    //			if(FormM.DialogResult==DialogResult.OK) {
    //				Medication med=Medications.GetMedicationFromDb(FormM.SelectedMedicationNum);
    //				if(med.RxCui==0 //if the med has no Cui, it won't trigger an alert
    //					|| RxAlertL.DisplayAlerts(PatCur.PatNum,med.RxCui,0))//user sees alert and wants to continue
    //				{
    //					MedicationPat medicationPat=new MedicationPat();
    //					medicationPat.PatNum=PatCur.PatNum;
    //					medicationPat.MedicationNum=FormM.SelectedMedicationNum;
    //					medicationPat.ProvNum=Security.CurUser.ProvNum;
    //					medicationPat.DateStart=DateTime.Today;
    //					FormMedPat FormMP=new FormMedPat();
    //					FormMP.MedicationPatCur=medicationPat;
    //					FormMP.IsNew=true;
    //					FormMP.IsNewMedOrder=true;
    //					FormMP.ShowDialog();
    //					if(FormMP.DialogResult==DialogResult.OK) {
    //						ModuleSelected(PatCur.PatNum);
    //					}
    //				}
    //			}
    //			Tool_EHR_Click(true);
    //		}
    //	//#else
    //	//TODO:
    //		//Type type=FormOpenDental.AssemblyEHR.GetType("OpenDental.ObjSomeEhrSuperClass");//namespace.class
    //		object[] args;
    //		EhrQuarterlyKey keyThisQ=EhrQuarterlyKeys.GetKeyThisQuarter();
    //		if(keyThisQ==null) {
    //			MessageBox.Show("No quarterly key entered for this quarter.");
    //			return;
    //		}
    //		args=new object[] { (DateTime.Today.Year-2000).ToString(),EhrQuarterlyKeys.MonthToQuarter(DateTime.Today.Month).ToString(),
    //			PrefC.GetString(PrefName.PracticeTitle),keyThisQ.KeyValue };
    //		FormEHR Ehr = new FormEHR();
    //		Ehr.PatNum=PatCur.PatNum;
    //		Ehr.PatNotCur=PatientNoteCur;
    //		Ehr.PatFamCur=FamCur;
    //		Ehr.ShowDialog();
    //		if(!(bool)type.InvokeMember("QuarterlyKeyIsValid",System.Reflection.BindingFlags.InvokeMethod,null,FormOpenDental.ObjSomeEhrSuperClass,args)) {
    //			MessageBox.Show("Invalid quarterly key.");
    //			return;
    //		}
    //		//args=new object[] {PatCur.PatNum};
    //		//type.InvokeMember("PatNum",System.Reflection.BindingFlags.SetField,null,FormOpenDental.ObjSomeEhrSuperClass,args);
    //		//type.InvokeMember("ShowDialog",System.Reflection.BindingFlags.InvokeMethod,null,FormOpenDental.ObjSomeEhrSuperClass,null);
    //		//if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.None) {
    //		//	return;
    //		//}
    //		//if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.RxEdit) {
    //		//	long launchRxNum=(long)type.InvokeMember("LaunchRxNum",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null);
    //		//	FormRxEdit FormRXE=new FormRxEdit(PatCur,RxPats.GetRx(launchRxNum));
    //		//	FormRXE.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.RxSelect) {
    //		//	FormRxSelect FormRS=new FormRxSelect(PatCur);
    //		//	FormRS.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.Medical) {
    //		//	FormMedical formM=new FormMedical(PatientNoteCur,PatCur);
    //		//	formM.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.PatientEdit) {
    //		//	FormPatientEdit formP=new FormPatientEdit(PatCur,FamCur);
    //		//	formP.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.Online) {
    //		//	FormPatientPortal formPP=new FormPatientPortal();
    //		//	formPP.PatCur=PatCur;
    //		//	formPP.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.MedReconcile) {
    //		//	FormMedicationReconcile FormMR=new FormMedicationReconcile();
    //		//	FormMR.PatCur=PatCur;
    //		//	FormMR.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.Referrals) {
    //		//	FormReferralsPatient formRP=new FormReferralsPatient();
    //		//	formRP.PatNum=PatCur.PatNum;
    //		//	formRP.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(false);
    //		//}
    //		//else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.MedicationPatEdit) {
    //		//	long medicationPatNum=(long)type.InvokeMember("LaunchMedicationPatNum",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null);
    //		//	FormMedPat formMP=new FormMedPat();
    //		//	formMP.MedicationPatCur=MedicationPats.GetOne(medicationPatNum);
    //		//	formMP.ShowDialog();
    //		//	ModuleSelected(PatCur.PatNum);
    //		//	Tool_EHR_Click(true);
    //		//}
    //		/*No longer allowed to create medication orders from the MedicalOrder (CPOE) window.
    //		else if(((EhrFormResult)type.InvokeMember("ResultOnClosing",System.Reflection.BindingFlags.GetField,null,FormOpenDental.ObjSomeEhrSuperClass,null))==EhrFormResult.MedicationPatNew) {
    //			//This cannot happen unless a provider is logged in with a valid ehr key
    //			FormMedications FormM=new FormMedications();
    //			FormM.IsSelectionMode=true;
    //			FormM.ShowDialog();
    //			if(FormM.DialogResult==DialogResult.OK) {
    //				Medication med=Medications.GetMedicationFromDb(FormM.SelectedMedicationNum);
    //				if(med.RxCui==0 //if the med has no Cui, it won't trigger an alert
    //					|| RxAlertL.DisplayAlerts(PatCur.PatNum,med.RxCui,0))//user sees alert and wants to continue
    //				{
    //					MedicationPat medicationPat=new MedicationPat();
    //					medicationPat.PatNum=PatCur.PatNum;
    //					medicationPat.MedicationNum=FormM.SelectedMedicationNum;
    //					medicationPat.ProvNum=Security.CurUser.ProvNum;
    //					FormMedPat FormMP=new FormMedPat();
    //					FormMP.MedicationPatCur=medicationPat;
    //					FormMP.IsNew=true;
    //					FormMP.ShowDialog();
    //					if(FormMP.DialogResult==DialogResult.OK) {
    //						ModuleSelected(PatCur.PatNum);
    //					}
    //				}
    //			}
    //			Tool_EHR_Click(true);
    //		}*/
    //	#endif
    //}
    private void tool_HL7_Click() throws Exception {
        DataRow row = new DataRow();
        if (gridProg.getSelectedIndices().Length == 0)
        {
            for (int i = 0;i < gridProg.getRows().Count;i++)
            {
                //autoselect procedures
                //loop through every line showing in progress notes
                row = (DataRow)gridProg.getRows().get___idx(i).getTag();
                if (StringSupport.equals(row["ProcNum"].ToString(), "0"))
                {
                    continue;
                }
                 
                //ignore non-procedures
                //May want to ignore procs with zero fee?
                //if((decimal)row["chargesDouble"]==0) {
                //  continue;//ignore zero fee procedures, but user can explicitly select them
                //}
                if (PIn.Date(row["ProcDate"].ToString()) == DateTime.Today && PIn.Int(row["ProcStatus"].ToString()) == ((Enum)ProcStat.C).ordinal())
                {
                    gridProg.setSelected(i,true);
                }
                 
            }
            if (gridProg.getSelectedIndices().Length == 0)
            {
                //if still none selected
                MsgBox.show(this,"Please select procedures first.");
                return ;
            }
             
        }
         
        List<Procedure> procs = new List<Procedure>();
        boolean allAreProcedures = true;
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            if (StringSupport.equals(row["ProcNum"].ToString(), "0"))
            {
                allAreProcedures = false;
            }
            else
            {
                procs.Add(Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), false));
            } 
        }
        if (!allAreProcedures)
        {
            MsgBox.show(this,"You can only select procedures.");
            return ;
        }
         
        long aptNum = 0;
        for (int i = 0;i < procs.Count;i++)
        {
            if (procs[i].AptNum == 0)
            {
                continue;
            }
             
            aptNum = procs[i].AptNum;
            break;
        }
        //todo: compare with: Bridges.ECW.AptNum, no need to generate PDF segment, pdfs only with eCW and this button not available with eCW integration
        MessageHL7 messageHL7 = MessageConstructor.GenerateDFT(procs, EventTypeHL7.P03, PatCur, FamCur.ListPats[0], aptNum, "treatment", "PDF Segment");
        if (messageHL7 == null)
        {
            MsgBox.show(this,"There is no DFT message type defined for the enabled HL7 definition.");
            return ;
        }
         
        HL7Msg hl7Msg = new HL7Msg();
        hl7Msg.AptNum = aptNum;
        hl7Msg.HL7Status = HL7MessageStatus.OutPending;
        //it will be marked outSent by the HL7 service.
        hl7Msg.MsgText = messageHL7.toString();
        hl7Msg.PatNum = PatCur.PatNum;
        HL7Msgs.insert(hl7Msg);
    }

    private void menuConsent_Popup(Object sender, EventArgs e) throws Exception {
        menuConsent.MenuItems.Clear();
        List<SheetDef> listSheets = SheetDefs.getCustomForType(SheetTypeEnum.Consent);
        MenuItem menuItem = new MenuItem();
        for (int i = 0;i < listSheets.Count;i++)
        {
            menuItem = new MenuItem(listSheets[i].Description);
            menuItem.Tag = listSheets[i];
            menuItem.Click += new EventHandler(menuConsent_Click);
            menuConsent.MenuItems.Add(menuItem);
        }
    }

    private void menuConsent_Click(Object sender, EventArgs e) throws Exception {
        SheetDef sheetDef = (SheetDef)(((MenuItem)sender).Tag);
        SheetDefs.getFieldsAndParameters(sheetDef);
        Sheet sheet = SheetUtil.createSheet(sheetDef,PatCur.PatNum);
        SheetParameter.setParameter(sheet,"PatNum",PatCur.PatNum);
        SheetFiller.fillFields(sheet);
        Graphics g = this.CreateGraphics();
        SheetUtil.calculateHeights(sheet,g);
        g.Dispose();
        FormSheetFillEdit FormS = new FormSheetFillEdit(sheet);
        FormS.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void menuToothChart_Popup(Object sender, EventArgs e) throws Exception {
    }

    //ComputerPref computerPref=ComputerPrefs.GetForLocalComputer();
    //only enable the big button if 3D graphics
    /*if(computerPref.GraphicsSimple) {
    				menuItemChartBig.Enabled=false;
    			}
    			else {
    				menuItemChartBig.Enabled=true;
    			}*/
    private void menuItemChartBig_Click(Object sender, EventArgs e) throws Exception {
        FormToothChartingBig FormT = new FormToothChartingBig(checkShowTeeth.Checked, ToothInitialList, ProcList);
        FormT.Show();
    }

    private void menuItemChartSave_Click(Object sender, EventArgs e) throws Exception {
        long defNum = DefC.getImageCat(ImageCategorySpecial.T);
        if (defNum == 0)
        {
            //no category set for Tooth Charts.
            MessageBox.Show(Lan.g(this,"No Def set for Tooth Charts."));
            return ;
        }
         
        Point origin = this.PointToScreen(toothChart.Location);
        Bitmap chartBitmap = new Bitmap(toothChart.Width, toothChart.Height, System.Drawing.Imaging.PixelFormat.Format32bppArgb);
        Graphics g = Graphics.FromImage(chartBitmap);
        g.CopyFromScreen(origin, new Point(0, 0), toothChart.Size, CopyPixelOperation.SourceCopy);
        g.Dispose();
        try
        {
            ImageStore.import(chartBitmap,defNum,ImageType.Photo,PatCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Unable to save file: ") + ex.Message);
            chartBitmap.Dispose();
            chartBitmap = null;
            return ;
        }

        MsgBox.show(this,"Saved.");
        chartBitmap.Dispose();
        chartBitmap = null;
    }

    public void fillPtInfo() throws Exception {
        if (Plugins.hookMethod(this,"ContrChart.FillPtInfo",PatCur))
        {
            return ;
        }
         
        ChartLayoutHelper.GridPtInfoSetSize(gridPtInfo, tabControlImages);
        textTreatmentNotes.Text = "";
        if (PatCur == null)
        {
            gridPtInfo.beginUpdate();
            gridPtInfo.getRows().Clear();
            gridPtInfo.getColumns().Clear();
            gridPtInfo.endUpdate();
            return ;
        }
        else
        {
            textTreatmentNotes.Text = PatientNoteCur.Treatment;
            textTreatmentNotes.Enabled = true;
            textTreatmentNotes.Select(textTreatmentNotes.Text.Length + 2, 1);
            textTreatmentNotes.ScrollToCaret();
            TreatmentNoteChanged = false;
        } 
        gridPtInfo.beginUpdate();
        gridPtInfo.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        //Lan.g("TableChartPtInfo",""),);
        gridPtInfo.getColumns().add(col);
        col = new ODGridColumn("",300);
        gridPtInfo.getColumns().add(col);
        gridPtInfo.getRows().Clear();
        OpenDental.UI.ODGridCell cell;
        OpenDental.UI.ODGridRow row;
        List<DisplayField> fields = DisplayFields.getForCategory(DisplayFieldCategory.ChartPatientInformation);
        for (int f = 0;f < fields.Count;f++)
        {
            row = new OpenDental.UI.ODGridRow();
            //within a case statement, the row may be re-instantiated if needed, effectively removing the first cell added here:
            if (StringSupport.equals(fields[f].Description, ""))
            {
                row.getCells().Add(fields[f].InternalName);
            }
            else
            {
                row.getCells().Add(fields[f].Description);
            } 
            int ordinal = 0;
            InternalName __dummyScrutVar2 = fields[f].InternalName;
            if (__dummyScrutVar2.equals("Age"))
            {
                row.getCells().add(PatientLogic.dateToAgeString(PatCur.Birthdate,PatCur.DateTimeDeceased));
            }
            else if (__dummyScrutVar2.equals("ABC0"))
            {
                row.getCells().add(PatCur.CreditType);
            }
            else if (__dummyScrutVar2.equals("Billing Type"))
            {
                row.getCells().add(DefC.getName(DefCat.BillingTypes,PatCur.BillingType));
            }
            else if (__dummyScrutVar2.equals("Referred From"))
            {
                List<RefAttach> RefAttachList = RefAttaches.Refresh(PatCur.PatNum);
                String referral = "";
                for (int i = 0;i < RefAttachList.Count;i++)
                {
                    if (RefAttachList[i].IsFrom)
                    {
                        referral = Referrals.GetNameLF(RefAttachList[i].ReferralNum);
                        break;
                    }
                     
                }
                if (StringSupport.equals(referral, ""))
                {
                    referral = "??";
                }
                 
                row.getCells().add(referral);
                row.setTag(null);
            }
            else if (__dummyScrutVar2.equals("Date First Visit"))
            {
                if (PatCur.DateFirstVisit.Year < 1880)
                {
                    row.getCells().add("??");
                }
                else if (PatCur.DateFirstVisit == DateTime.Today)
                {
                    row.getCells().add(Lan.g("TableChartPtInfo","NEW PAT"));
                }
                else
                {
                    row.getCells().Add(PatCur.DateFirstVisit.ToShortDateString());
                }  
                row.setTag(null);
            }
            else if (__dummyScrutVar2.equals("Prov. (Pri, Sec)"))
            {
                if (PatCur.SecProv != 0)
                {
                    row.getCells().add(Providers.getAbbr(PatCur.PriProv) + ", " + Providers.getAbbr(PatCur.SecProv));
                }
                else
                {
                    row.getCells().add(Providers.getAbbr(PatCur.PriProv) + ", " + Lan.g("TableChartPtInfo","None"));
                } 
                row.setTag(null);
            }
            else if (__dummyScrutVar2.equals("Pri Ins"))
            {
                String name = new String();
                ordinal = PatPlans.getOrdinal(PriSecMed.Primary,PatPlanList,PlanList,SubList);
                if (ordinal > 0)
                {
                    InsSub sub = InsSubs.getSub(PatPlans.getInsSubNum(PatPlanList,ordinal),SubList);
                    name = InsPlans.getCarrierName(sub.PlanNum,PlanList);
                    if (PatPlanList[0].IsPending)
                    {
                        name += Lan.g("TableChartPtInfo"," (pending)");
                    }
                     
                    row.getCells().add(name);
                }
                else
                {
                    row.getCells().add("");
                } 
                row.setTag(null);
            }
            else if (__dummyScrutVar2.equals("Sec Ins"))
            {
                ordinal = PatPlans.getOrdinal(PriSecMed.Secondary,PatPlanList,PlanList,SubList);
                if (ordinal > 0)
                {
                    InsSub sub = InsSubs.getSub(PatPlans.getInsSubNum(PatPlanList,ordinal),SubList);
                    name = InsPlans.getCarrierName(sub.PlanNum,PlanList);
                    if (PatPlanList[1].IsPending)
                    {
                        name += Lan.g("TableChartPtInfo"," (pending)");
                    }
                     
                    row.getCells().add(name);
                }
                else
                {
                    row.getCells().add("");
                } 
                row.setTag(null);
            }
            else if (__dummyScrutVar2.equals("Payor Types"))
            {
                row.setTag("Payor Types");
                row.getCells().add(PayorTypes.getCurrentDescription(PatCur.PatNum));
            }
            else if (__dummyScrutVar2.equals("Registration Keys"))
            {
                //Not even available to most users.
                RegistrationKey[] keys = RegistrationKeys.getForPatient(PatCur.PatNum);
                for (int i = 0;i < keys.Length;i++)
                {
                    row = new OpenDental.UI.ODGridRow();
                    row.getCells().add(Lan.g("TableChartPtInfo","Registration Key"));
                    String str = keys[i].RegKey.Substring(0, 4) + "-" + keys[i].RegKey.Substring(4, 4) + "-" + keys[i].RegKey.Substring(8, 4) + "-" + keys[i].RegKey.Substring(12, 4);
                    if (keys[i].IsForeign)
                    {
                        str += "\r\nForeign";
                    }
                    else
                    {
                        str += "\r\nUSA";
                    } 
                    str += "\r\nStarted: " + keys[i].DateStarted.ToShortDateString();
                    if (keys[i].DateDisabled.Year > 1880)
                    {
                        str += "\r\nDisabled: " + keys[i].DateDisabled.ToShortDateString();
                    }
                     
                    if (keys[i].DateEnded.Year > 1880)
                    {
                        str += "\r\nEnded: " + keys[i].DateEnded.ToShortDateString();
                    }
                     
                    if (!StringSupport.equals(keys[i].Note, ""))
                    {
                        str += keys[i].Note;
                    }
                     
                    row.getCells().add(str);
                    row.setTag(keys[i].Copy());
                    gridPtInfo.getRows().add(row);
                }
            }
            else if (__dummyScrutVar2.equals("Ehr Provider Keys"))
            {
                //Not even available to most users.
                List<EhrProvKey> listProvKeys = EhrProvKeys.refreshForFam(PatCur.Guarantor);
                String desc = "";
                for (int i = 0;i < listProvKeys.Count;i++)
                {
                    if (i > 0)
                    {
                        desc += "\r\n";
                    }
                     
                    desc += listProvKeys[i].LName + ", " + listProvKeys[i].FName + ", " + (listProvKeys[i].HasReportAccess ? "reports, " : "no reports, ") + listProvKeys[i].ProvKey;
                }
                row.getCells().add(desc);
                row.setColorBackG(Color.PowderBlue);
                row.setTag("EhrProvKeys");
            }
            else if (__dummyScrutVar2.equals("Premedicate"))
            {
                if (PatCur.Premed)
                {
                    row = new OpenDental.UI.ODGridRow();
                    row.getCells().add("");
                    cell = new OpenDental.UI.ODGridCell();
                    if (StringSupport.equals(fields[f].Description, ""))
                    {
                        cell.setText(fields[f].InternalName);
                    }
                    else
                    {
                        cell.setText(fields[f].Description);
                    } 
                    cell.setColorText(Color.Red);
                    cell.setBold(YN.Yes);
                    row.getCells().Add(cell);
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                    row.setTag("med");
                    gridPtInfo.getRows().add(row);
                }
                 
            }
            else if (__dummyScrutVar2.equals("Problems"))
            {
                List<Disease> DiseaseList = Diseases.refresh(PatCur.PatNum,true);
                row = new OpenDental.UI.ODGridRow();
                cell = new OpenDental.UI.ODGridCell();
                if (StringSupport.equals(fields[f].Description, ""))
                {
                    cell.setText(fields[f].InternalName);
                }
                else
                {
                    cell.setText(fields[f].Description);
                } 
                cell.setBold(YN.Yes);
                row.getCells().Add(cell);
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                row.setTag("med");
                if (DiseaseList.Count > 0)
                {
                    row.getCells().add("");
                    gridPtInfo.getRows().add(row);
                }
                else
                {
                    row.getCells().add(Lan.g("TableChartPtInfo","none"));
                } 
                for (int i = 0;i < DiseaseList.Count;i++)
                {
                    //Add a new row for each med.
                    row = new OpenDental.UI.ODGridRow();
                    if (DiseaseList[i].DiseaseDefNum != 0)
                    {
                        cell = new OpenDental.UI.ODGridCell(DiseaseDefs.GetName(DiseaseList[i].DiseaseDefNum));
                        cell.setColorText(Color.Red);
                        cell.setBold(YN.Yes);
                        row.getCells().Add(cell);
                        row.getCells().Add(DiseaseList[i].PatNote);
                    }
                    else
                    {
                        row.getCells().add("");
                        cell = new OpenDental.UI.ODGridCell(DiseaseDefs.GetItem(DiseaseList[i].DiseaseDefNum).DiseaseName);
                        cell.setColorText(Color.Red);
                        cell.setBold(YN.Yes);
                        row.getCells().Add(cell);
                    } 
                    //row.Cells.Add(DiseaseList[i].PatNote);//no place to show a pat note
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                    row.setTag("med");
                    if (i != DiseaseList.Count - 1)
                    {
                        gridPtInfo.getRows().add(row);
                    }
                     
                }
            }
            else if (__dummyScrutVar2.equals("Med Urgent"))
            {
                cell = new OpenDental.UI.ODGridCell();
                cell.setText(PatCur.MedUrgNote);
                cell.setColorText(Color.Red);
                cell.setBold(YN.Yes);
                row.getCells().Add(cell);
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                row.setTag("med");
            }
            else if (__dummyScrutVar2.equals("Medical Summary"))
            {
                row.getCells().add(PatientNoteCur.Medical);
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                row.setTag("med");
            }
            else if (__dummyScrutVar2.equals("Service Notes"))
            {
                row.getCells().add(PatientNoteCur.Service);
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                row.setTag("med");
            }
            else if (__dummyScrutVar2.equals("Medications"))
            {
                Medications.refresh();
                List<MedicationPat> medList = MedicationPats.refresh(PatCur.PatNum,false);
                row = new OpenDental.UI.ODGridRow();
                cell = new OpenDental.UI.ODGridCell();
                if (StringSupport.equals(fields[f].Description, ""))
                {
                    cell.setText(fields[f].InternalName);
                }
                else
                {
                    cell.setText(fields[f].Description);
                } 
                cell.setBold(YN.Yes);
                row.getCells().Add(cell);
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                row.setTag("med");
                if (medList.Count > 0)
                {
                    row.getCells().add("");
                    gridPtInfo.getRows().add(row);
                }
                else
                {
                    row.getCells().add(Lan.g("TableChartPtInfo","none"));
                } 
                String text = new String();
                Medication med;
                for (int i = 0;i < medList.Count;i++)
                {
                    row = new OpenDental.UI.ODGridRow();
                    if (medList[i].MedicationNum == 0)
                    {
                        //NewCrop medication order.
                        row.getCells().Add(medList[i].MedDescript);
                    }
                    else
                    {
                        med = Medications.GetMedication(medList[i].MedicationNum);
                        text = med.MedName;
                        if (med.MedicationNum != med.GenericNum)
                        {
                            text += "(" + Medications.getMedication(med.GenericNum).MedName + ")";
                        }
                         
                        row.getCells().add(text);
                    } 
                    text = medList[i].PatNote;
                    String noteMedGeneric = "";
                    if (medList[i].MedicationNum != 0)
                    {
                        noteMedGeneric = Medications.GetGeneric(medList[i].MedicationNum).Notes;
                    }
                     
                    if (!StringSupport.equals(noteMedGeneric, ""))
                    {
                        text += "(" + noteMedGeneric + ")";
                    }
                     
                    row.getCells().add(text);
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                    row.setTag("med");
                    if (i != medList.Count - 1)
                    {
                        gridPtInfo.getRows().add(row);
                    }
                     
                }
            }
            else if (__dummyScrutVar2.equals("Allergies"))
            {
                List<Allergy> allergyList = Allergies.getAll(PatCur.PatNum,false);
                row = new OpenDental.UI.ODGridRow();
                cell = new OpenDental.UI.ODGridCell();
                if (StringSupport.equals(fields[f].Description, ""))
                {
                    cell.setText(fields[f].InternalName);
                }
                else
                {
                    cell.setText(fields[f].Description);
                } 
                cell.setBold(YN.Yes);
                row.getCells().Add(cell);
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                row.setTag("med");
                if (allergyList.Count > 0)
                {
                    row.getCells().add("");
                    gridPtInfo.getRows().add(row);
                }
                else
                {
                    row.getCells().add(Lan.g("TableChartPtInfo","none"));
                } 
                for (int i = 0;allergyList.Count > i;i++)
                {
                    row = new OpenDental.UI.ODGridRow();
                    cell = new OpenDental.UI.ODGridCell(AllergyDefs.GetOne(allergyList[i].AllergyDefNum).Description);
                    cell.setBold(YN.Yes);
                    cell.setColorText(Color.Red);
                    row.getCells().Add(cell);
                    row.getCells().Add(allergyList[i].Reaction);
                    row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][3].ItemColor);
                    row.setTag("med");
                    if (i != allergyList.Count - 1)
                    {
                        gridPtInfo.getRows().add(row);
                    }
                     
                }
            }
            else if (__dummyScrutVar2.equals("PatFields"))
            {
                PatField field;
                for (int i = 0;i < PatFieldDefs.getList().Length;i++)
                {
                    row = new OpenDental.UI.ODGridRow();
                    row.getCells().Add(PatFieldDefs.getList()[i].FieldName);
                    field = PatFields.GetByName(PatFieldDefs.getList()[i].FieldName, PatFieldList);
                    if (field == null)
                    {
                        row.getCells().add("");
                    }
                    else
                    {
                        if (PatFieldDefs.getList()[i].FieldType == PatFieldType.Checkbox)
                        {
                            row.getCells().add("X");
                        }
                        else
                        {
                            row.getCells().add(field.FieldValue);
                        } 
                    } 
                    row.setTag("PatField" + i.ToString());
                    gridPtInfo.getRows().add(row);
                }
            }
            else if (__dummyScrutVar2.equals("Birthdate"))
            {
                if (PatCur.Birthdate.Year < 1880)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(PatCur.Birthdate.ToShortDateString());
                } 
            }
            else if (__dummyScrutVar2.equals("City"))
            {
                row.getCells().add(PatCur.City);
            }
            else if (__dummyScrutVar2.equals("AskToArriveEarly"))
            {
                if (PatCur.AskToArriveEarly == 0)
                {
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(PatCur.AskToArriveEarly.ToString() + " minute(s)");
                } 
            }
            else if (__dummyScrutVar2.equals("Super Head"))
            {
                if (PatCur.SuperFamily != 0)
                {
                    Patient tempSuper = Patients.getPat(PatCur.SuperFamily);
                    row.getCells().add(tempSuper.getNameLF() + " (" + tempSuper.PatNum + ")");
                }
                else
                {
                    continue;
                } 
            }
            else //do not allow this row to be added if there is no data to in the row.
            if (__dummyScrutVar2.equals("Patient Portal"))
            {
                row.setTag("Patient Portal");
                if (StringSupport.equals(PatCur.OnlinePassword, ""))
                {
                    row.getCells().add(Lan.g(this,"No access"));
                }
                else
                {
                    row.getCells().add(Lan.g(this,"Online"));
                } 
            }
            else if (__dummyScrutVar2.equals("References"))
            {
                List<CustRefEntry> custREList = CustRefEntries.getEntryListForCustomer(PatCur.PatNum);
                if (custREList.Count == 0)
                {
                    row.getCells().add(Lan.g("TablePatient","None"));
                    row.setTag("References");
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                }
                else
                {
                    row.getCells().add(Lan.g("TablePatient",""));
                    row.setTag("References");
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                    gridPtInfo.getRows().add(row);
                } 
                for (int i = 0;i < custREList.Count;i++)
                {
                    row = new OpenDental.UI.ODGridRow();
                    row.getCells().Add(custREList[i].DateEntry.ToShortDateString());
                    row.getCells().Add(CustReferences.GetCustNameFL(custREList[i].PatNumRef));
                    row.setTag(custREList[i]);
                    row.setColorBackG(DefC.getShort()[((Enum)DefCat.MiscColors).ordinal()][8].ItemColor);
                    if (i < custREList.Count - 1)
                    {
                        gridPtInfo.getRows().add(row);
                    }
                     
                }
            }
                                     
            if (StringSupport.equals(fields[f].InternalName, "PatFields") || StringSupport.equals(fields[f].InternalName, "Premedicate") || StringSupport.equals(fields[f].InternalName, "Registration Keys"))
            {
            }
            else
            {
                //For fields that might have zero rows, we can't add the row here.  Adding rows is instead done in the case clause.
                //But some fields that are based on lists will always have one row, even if there are no items in the list.
                //Do not add those kinds here.
                gridPtInfo.getRows().add(row);
            } 
        }
        gridPtInfo.endUpdate();
    }

    private void textTreatmentNotes_TextChanged(Object sender, System.EventArgs e) throws Exception {
        TreatmentNoteChanged = true;
    }

    private void textTreatmentNotes_Leave(Object sender, System.EventArgs e) throws Exception {
        //need to skip this if selecting another module. Handled in ModuleUnselected due to click event
        if (FamCur == null)
            return ;
         
        if (TreatmentNoteChanged)
        {
            PatientNoteCur.Treatment = textTreatmentNotes.Text;
            PatientNotes.update(PatientNoteCur,PatCur.Guarantor);
            TreatmentNoteChanged = false;
        }
         
    }

    /**
    * The supplied procedure row must include these columns: isLocked,ProcDate,ProcStatus,ProcCode,Surf,ToothNum, and ToothRange, all in raw database format.
    */
    private boolean shouldDisplayProc(DataRow row) throws Exception {
        //if printing for hospital
        /*
        			if(hospitalDate.Year > 1880) {
        				if(hospitalDate.Date != PIn.DateT(row["ProcDate"].ToString()).Date) {
        					return false;
        				}
        				if(row["ProcStatus"].ToString() != ((int)ProcStat.C).ToString()) {
        					return false;
        				}
        			}*/
        if (checkShowTeeth.Checked)
        {
            //Only show selected teeth
            boolean showProc = false;
            //ArrayList selectedTeeth = new ArrayList();//integers 1-32
            //for(int i = 0;i < toothChart.SelectedTeeth.Count;i++) {
            //	selectedTeeth.Add(Tooth.ToInt(toothChart.SelectedTeeth[i]));
            //}
            TreatArea __dummyScrutVar3 = ProcedureCodes.GetProcCode(row["ProcCode"].ToString()).TreatArea;
            if (__dummyScrutVar3.equals(TreatmentArea.Arch))
            {
                for (int s = 0;s < toothChart.getSelectedTeeth().Count;s++)
                {
                    if (StringSupport.equals(row["Surf"].ToString(), "U") && Tooth.IsMaxillary(toothChart.getSelectedTeeth()[s]))
                    {
                        showProc = true;
                    }
                    else if (StringSupport.equals(row["Surf"].ToString(), "L") && !Tooth.IsMaxillary(toothChart.getSelectedTeeth()[s]))
                    {
                        showProc = true;
                    }
                      
                }
            }
            else if (__dummyScrutVar3.equals(TreatmentArea.Mouth) || __dummyScrutVar3.equals(TreatmentArea.None) || __dummyScrutVar3.equals(TreatmentArea.Sextant))
            {
                //nobody will miss it
                showProc = false;
            }
            else if (__dummyScrutVar3.equals(TreatmentArea.Quad))
            {
                for (int s = 0;s < toothChart.getSelectedTeeth().Count;s++)
                {
                    if (StringSupport.equals(row["Surf"].ToString(), "UR") && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) >= 1 && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) <= 8)
                    {
                        showProc = true;
                    }
                    else if (StringSupport.equals(row["Surf"].ToString(), "UL") && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) >= 9 && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) <= 16)
                    {
                        showProc = true;
                    }
                    else if (StringSupport.equals(row["Surf"].ToString(), "LL") && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) >= 17 && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) <= 24)
                    {
                        showProc = true;
                    }
                    else if (StringSupport.equals(row["Surf"].ToString(), "LR") && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) >= 25 && Tooth.ToInt(toothChart.getSelectedTeeth()[s]) <= 32)
                    {
                        showProc = true;
                    }
                        
                }
            }
            else if (__dummyScrutVar3.equals(TreatmentArea.Surf) || __dummyScrutVar3.equals(TreatmentArea.Tooth))
            {
                for (int s = 0;s < toothChart.getSelectedTeeth().Count;s++)
                {
                    if (row["ToothNum"].ToString() == toothChart.getSelectedTeeth()[s])
                    {
                        showProc = true;
                    }
                     
                }
            }
            else if (__dummyScrutVar3.equals(TreatmentArea.ToothRange))
            {
                String[] range = row["ToothRange"].ToString().Split(',');
                for (int s = 0;s < toothChart.getSelectedTeeth().Count;s++)
                {
                    for (int r = 0;r < range.Length;r++)
                    {
                        if (range[r] == toothChart.getSelectedTeeth()[s])
                        {
                            showProc = true;
                        }
                         
                    }
                }
            }
                 
            if (!showProc)
            {
                return false;
            }
             
        }
         
        boolean isLocked = (StringSupport.equals(row["isLocked"].ToString(), "X"));
        if (!procStatDesired((ProcStat)PIn.Long(row["ProcStatus"].ToString()),isLocked))
        {
            return false;
        }
         
        if (Programs.getUsingOrion())
        {
            if (!OrionProcStatDesired((row["orionStatus2"].ToString())))
            {
                return false;
            }
             
        }
         
        return true;
    }

    // Put check for showing hygine in here
    // Put check for showing films in here
    /**
    * Checks ProcStat passed to see if one of the check boxes on the form contains a check for the ps passed. For example if ps is TP and the checkShowTP.Checked is true it will return true.
    */
    private boolean procStatDesired(ProcStat ps, boolean isLocked) throws Exception {
        switch(ps)
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
                if (checkAudit.Checked || (checkShowC.Checked && isLocked))
                {
                    return true;
                }
                 
                break;
            case Cn: 
                if (checkShowCn.Checked)
                {
                    return true;
                }
                 
                break;
        
        }
        return false;
    }

    //TODO: if proc Date is within show date range; return true;
    private boolean orionProcStatDesired(String status2) throws Exception {
        //We ought to include procs with no status2 in case one slips through the cracks and for testing.
        if (StringSupport.equals(status2, OrionStatus.None.ToString()) || listProcStatusCodes.SelectedItems.Count == 0)
        {
            return true;
        }
         
        for (int i = 0;i < listProcStatusCodes.SelectedItems.Count;i++)
        {
            //Convert the graphical status "os" into a single string status "status2".
            //Not needed because we never translate orion fields to other languages.
            /*
            			string status2="";
            			if(os==Lans.g("enumStatus2",OrionStatus.TP.ToString())) {
            				status2=OrionStatus.TP.ToString();
            			}
            			 * etc*/
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), status2))
            {
                return true;
            }
             
        }
        return false;
    }

    private void fillProgNotes() throws Exception {
        fillProgNotes(false);
    }

    private void fillProgNotes(boolean retainSelection) throws Exception {
        Plugins.hookAddCode(this,"ContrChart.FillProgNotes_begin");
        //ArrayList selectedTeeth=new ArrayList();//integers 1-32
        //for(int i=0;i<toothChart.SelectedTeeth.Count;i++) {
        //	selectedTeeth.Add(Tooth.ToInt(toothChart.SelectedTeeth[i]));
        //}
        //List<string> selectedTeeth=new List<string>(toothChart.SelectedTeeth);
        if (Programs.getUsingOrion())
        {
            listProcStatusCodes.Visible = true;
            if (listProcStatusCodes.Items.Count == 0)
            {
                String[] statusNames = Enum.GetNames(OrionStatus.class);
                for (int i = 1;i < statusNames.Length;i++)
                {
                    listProcStatusCodes.Items.Add(statusNames[i]);
                }
            }
             
        }
         
        gridProg.beginUpdate();
        gridProg.getColumns().Clear();
        ODGridColumn col;
        List<DisplayField> fields = new List<DisplayField>();
        //DisplayFields.RefreshCache();
        if (gridChartViews.getRows().Count == 0)
        {
            //No chart views, Use default values.
            fields = DisplayFields.getDefaultList(DisplayFieldCategory.None);
            gridProg.setTitle("Progress Notes");
            if (!chartCustViewChanged)
            {
                checkSheets.Checked = true;
                checkTasks.Checked = true;
                checkEmail.Checked = true;
                checkCommFamily.Checked = true;
                checkAppt.Checked = true;
                checkLabCase.Checked = true;
                checkRx.Checked = true;
                checkComm.Checked = true;
                checkShowTP.Checked = true;
                checkShowC.Checked = true;
                checkShowE.Checked = true;
                checkShowR.Checked = true;
                checkShowCn.Checked = true;
                checkNotes.Checked = true;
                checkNotes.Checked = true;
                checkShowTeeth.Checked = false;
                checkAudit.Checked = false;
                textShowDateRange.Text = "All Dates";
            }
             
        }
        else
        {
            if (ChartViewCurDisplay == null)
            {
                ChartViewCurDisplay = ChartViews.getListt()[0];
            }
             
            fields = DisplayFields.getForChartView(ChartViewCurDisplay.ChartViewNum);
            gridProg.setTitle(ChartViewCurDisplay.Description);
            if (!chartCustViewChanged)
            {
                checkSheets.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.Sheets) == ChartViewObjs.Sheets;
                checkTasks.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.Tasks) == ChartViewObjs.Tasks;
                checkEmail.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.Email) == ChartViewObjs.Email;
                checkCommFamily.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.CommLogFamily) == ChartViewObjs.CommLogFamily;
                checkAppt.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.Appointments) == ChartViewObjs.Appointments;
                checkLabCase.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.LabCases) == ChartViewObjs.LabCases;
                checkRx.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.Rx) == ChartViewObjs.Rx;
                checkComm.Checked = (ChartViewCurDisplay.ObjectTypes & ChartViewObjs.CommLog) == ChartViewObjs.CommLog;
                checkShowTP.Checked = (ChartViewCurDisplay.ProcStatuses & ChartViewProcStat.TP) == ChartViewProcStat.TP;
                checkShowC.Checked = (ChartViewCurDisplay.ProcStatuses & ChartViewProcStat.C) == ChartViewProcStat.C;
                checkShowE.Checked = (ChartViewCurDisplay.ProcStatuses & ChartViewProcStat.EC) == ChartViewProcStat.EC;
                checkShowR.Checked = (ChartViewCurDisplay.ProcStatuses & ChartViewProcStat.R) == ChartViewProcStat.R;
                checkShowCn.Checked = (ChartViewCurDisplay.ProcStatuses & ChartViewProcStat.Cn) == ChartViewProcStat.Cn;
                checkShowTeeth.Checked = ChartViewCurDisplay.SelectedTeethOnly;
                checkNotes.Checked = ChartViewCurDisplay.ShowProcNotes;
                checkAudit.Checked = ChartViewCurDisplay.IsAudit;
                setDateRange();
                fillDateRange();
                gridChartViews.setSelected(ChartViewCurDisplay.ItemOrder,true);
                if (IsDistributorKey)
                {
                    gridCustomerViews.setSelected(ChartViewCurDisplay.ItemOrder,true);
                }
                 
                if (Programs.getUsingOrion())
                {
                    listProcStatusCodes.ClearSelected();
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.TP) == OrionStatus.TP)
                    {
                        listProcStatusCodes.SetSelected(0, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.C) == OrionStatus.C)
                    {
                        listProcStatusCodes.SetSelected(1, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.E) == OrionStatus.E)
                    {
                        listProcStatusCodes.SetSelected(2, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.R) == OrionStatus.R)
                    {
                        listProcStatusCodes.SetSelected(3, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.RO) == OrionStatus.RO)
                    {
                        listProcStatusCodes.SetSelected(4, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.CS) == OrionStatus.CS)
                    {
                        listProcStatusCodes.SetSelected(5, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.CR) == OrionStatus.CR)
                    {
                        listProcStatusCodes.SetSelected(6, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.CA_Tx) == OrionStatus.CA_Tx)
                    {
                        listProcStatusCodes.SetSelected(7, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.CA_EPRD) == OrionStatus.CA_EPRD)
                    {
                        listProcStatusCodes.SetSelected(8, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.CA_PD) == OrionStatus.CA_PD)
                    {
                        listProcStatusCodes.SetSelected(9, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.S) == OrionStatus.S)
                    {
                        listProcStatusCodes.SetSelected(10, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.ST) == OrionStatus.ST)
                    {
                        listProcStatusCodes.SetSelected(11, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.W) == OrionStatus.W)
                    {
                        listProcStatusCodes.SetSelected(12, true);
                    }
                     
                    if ((ChartViewCurDisplay.OrionStatusFlags & OrionStatus.A) == OrionStatus.A)
                    {
                        listProcStatusCodes.SetSelected(13, true);
                    }
                     
                }
                 
            }
            else
            {
                gridChartViews.setSelected(false);
                if (IsDistributorKey)
                {
                    gridCustomerViews.setSelected(false);
                }
                 
            } 
        } 
        DataSetMain = null;
        if (PatCur != null)
        {
            if (usingEcwTight())
            {
                //ecw customers
                //showAppointments
                //showCommLog.  The button is in a different toolbar.
                //showCompleted
                //showConditions
                //checkEmail.Checked,      //showEmail
                //showExisting
                //checkCommFamily.Checked,	//showFamilyCommLog
                //showFormPat
                //showLabCases
                //showProcNotes
                //showReferred
                //showRX
                //showSheets, consent
                //checkTasks.Checked,			//showTasks (for now)
                ChartModuleComponentsToLoad componentsToLoad = new ChartModuleComponentsToLoad(checkAppt.Checked, checkComm.Checked, checkShowC.Checked, checkShowCn.Checked, false, checkShowE.Checked, false, false, checkLabCase.Checked, checkNotes.Checked, checkShowR.Checked, checkRx.Checked, checkSheets.Checked, false, checkShowTP.Checked);
                //showTreatPlan
                DataSetMain = ChartModules.GetAll(PatCur.PatNum, checkAudit.Checked, componentsToLoad);
            }
            else
            {
                //showConditions
                //all other customers and ecw full users
                //DataSetMain=ChartModules.GetAll(PatCur.PatNum,checkAudit.Checked);
                //showAppointments
                //showCommLog
                //showCompleted
                //showConditions
                //showEmail
                //showExisting
                //showFamilyCommLog
                //showFormPat
                //showLabCases
                //showProcNotes
                //showReferred
                //showRX
                //showSheets, consent
                //showTasks
                DataSetMain = ChartModules.GetAll(PatCur.PatNum, checkAudit.Checked, new ChartModuleComponentsToLoad(checkAppt.Checked, checkComm.Checked, checkShowC.Checked, checkShowCn.Checked, checkEmail.Checked, checkShowE.Checked, checkCommFamily.Checked, true, checkLabCase.Checked, checkNotes.Checked, checkShowR.Checked, checkRx.Checked, checkSheets.Checked, checkTasks.Checked, checkShowTP.Checked));
            } 
        }
         
        for (int i = 0;i < fields.Count;i++)
        {
            //showTreatPlan
            if (StringSupport.equals(fields[i].Description, ""))
            {
                col = new ODGridColumn(fields[i].InternalName, fields[i].ColumnWidth);
            }
            else
            {
                col = new ODGridColumn(fields[i].Description, fields[i].ColumnWidth);
            } 
            if (StringSupport.equals(fields[i].InternalName, "Th"))
            {
                col.setSortingStrategy(GridSortingStrategy.ToothNumberParse);
            }
             
            if (StringSupport.equals(fields[i].InternalName, "Date"))
            {
                col.setSortingStrategy(GridSortingStrategy.DateParse);
            }
             
            if (StringSupport.equals(fields[i].InternalName, "Amount"))
            {
                col.setSortingStrategy(GridSortingStrategy.AmountParse);
                col.setTextAlign(HorizontalAlignment.Right);
            }
             
            if (StringSupport.equals(fields[i].InternalName, "ADA Code") || StringSupport.equals(fields[i].InternalName, "User") || StringSupport.equals(fields[i].InternalName, "Signed") || StringSupport.equals(fields[i].InternalName, "Locked"))
            {
                col.setTextAlign(HorizontalAlignment.Center);
            }
             
            gridProg.getColumns().add(col);
        }
        if (gridProg.getColumns().Count < 3)
        {
            //0 wouldn't be possible.
            gridProg.setNoteSpanStart(0);
            gridProg.setNoteSpanStop(gridProg.getColumns().Count - 1);
        }
        else
        {
            gridProg.setNoteSpanStart(2);
            if (gridProg.getColumns().Count > 7)
            {
                gridProg.setNoteSpanStop(7);
            }
            else
            {
                gridProg.setNoteSpanStop(gridProg.getColumns().Count - 1);
            } 
        } 
        gridProg.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        //Type type;
        if (DataSetMain == null)
        {
            gridProg.endUpdate();
            fillToothChart(false);
            return ;
        }
         
        //?
        DataTable table = DataSetMain.Tables["ProgNotes"];
        List<ProcGroupItem> procGroupItems = ProcGroupItems.refresh(PatCur.PatNum);
        ProcList = new List<DataRow>();
        List<long> procNumList = new List<long>();
        //a list of all procNums of procs that will be visible
        boolean showGroupNote = new boolean();
        if (checkShowTeeth.Checked)
        {
            for (int i = 0;i < table.Rows.Count;i++)
            {
                //we will want to see groupnotes that are attached to any procs that should be visible.
                //loop through all rows in table.
                if (StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
                {
                    continue;
                }
                 
                //if this is not a procedure
                if (StringSupport.equals(table.Rows[i]["ProcCode"].ToString(), ProcedureCodes.GroupProcCode))
                {
                    continue;
                }
                 
                //skip procgroups
                if (ShouldDisplayProc(table.Rows[i]))
                {
                    procNumList.Add(PIn.Long(table.Rows[i]["ProcNum"].ToString()));
                }
                 
            }
        }
         
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //remember that procnum
            if (!StringSupport.equals(table.Rows[i]["ProcNum"].ToString(), "0"))
            {
                //if this is a procedure
                //if it's a group note and we are viewing by tooth number
                if (StringSupport.equals(table.Rows[i]["ProcCode"].ToString(), ProcedureCodes.GroupProcCode) && checkShowTeeth.Checked)
                {
                    //consult the list of previously obtained procedures and ProcGroupItems to see if this procgroup should be visible.
                    showGroupNote = false;
                    for (int j = 0;j < procGroupItems.Count;j++)
                    {
                        //loop through all procGroupItems for the patient.
                        if (procGroupItems[j].GroupNum == PIn.Long(table.Rows[i]["ProcNum"].ToString()))
                        {
                            for (int k = 0;k < procNumList.Count;k++)
                            {
                                //if this item is associated with this group note
                                //check all of the visible procs
                                if (procNumList[k] == procGroupItems[j].ProcNum)
                                {
                                    //if this group note is associated with a visible proc
                                    showGroupNote = true;
                                }
                                 
                            }
                        }
                         
                    }
                    if (!showGroupNote)
                    {
                        continue;
                    }
                     
                }
                else
                {
                    //don't show it in the grid
                    //procedure or group note, not viewing by tooth number
                    if (ShouldDisplayProc(table.Rows[i]))
                    {
                        ProcList.Add(table.Rows[i]);
                    }
                    else
                    {
                        continue;
                    } 
                } 
            }
            else //show it in the graphical tooth chart
            //show it in the grid below
            //don't show it in the grid
            if (!StringSupport.equals(table.Rows[i]["CommlogNum"].ToString(), "0"))
            {
                //if this is a commlog
                if (!checkComm.Checked)
                {
                    continue;
                }
                 
                if (table.Rows[i]["PatNum"].ToString() != PatCur.PatNum.ToString())
                {
                    //if this is a different family member
                    if (!checkCommFamily.Checked)
                    {
                        continue;
                    }
                     
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
            else if (!StringSupport.equals(table.Rows[i]["TaskNum"].ToString(), "0"))
            {
                //if this is a TaskItem
                if (!checkTasks.Checked)
                {
                    continue;
                }
                 
                if (table.Rows[i]["PatNum"].ToString() != PatCur.PatNum.ToString())
                {
                    //if this is a different family member
                    if (!checkCommFamily.Checked)
                    {
                        continue;
                    }
                     
                }
                 
            }
            else //uses same check box as commlog
            if (!StringSupport.equals(table.Rows[i]["EmailMessageNum"].ToString(), "0"))
            {
                //if this is an Email
                if (!checkEmail.Checked)
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
            else if (!StringSupport.equals(table.Rows[i]["SheetNum"].ToString(), "0"))
            {
                //if this is a sheet
                if (!checkSheets.Checked)
                {
                    continue;
                }
                 
            }
                    
            if (ShowDateStart.Year > 1880 && ((DateTime)(table.Rows[i]["ProcDate"])).Date < ShowDateStart.Date)
            {
                continue;
            }
             
            if (ShowDateEnd.Year > 1880 && ((DateTime)(table.Rows[i]["ProcDate"])).Date > ShowDateEnd.Date)
            {
                continue;
            }
             
            row = new OpenDental.UI.ODGridRow();
            row.setColorLborder(Color.Black);
            for (int f = 0;f < fields.Count;f++)
            {
                //remember that columns that start with lowercase are already altered for display rather than being raw data.
                InternalName __dummyScrutVar5 = fields[f].InternalName;
                if (__dummyScrutVar5.equals("Date"))
                {
                    row.getCells().Add(table.Rows[i]["procDate"].ToString());
                }
                else if (__dummyScrutVar5.equals("Time"))
                {
                    row.getCells().Add(table.Rows[i]["procTime"].ToString());
                }
                else if (__dummyScrutVar5.equals("Th"))
                {
                    row.getCells().Add(table.Rows[i]["toothNum"].ToString());
                }
                else if (__dummyScrutVar5.equals("Surf"))
                {
                    row.getCells().Add(table.Rows[i]["surf"].ToString());
                }
                else if (__dummyScrutVar5.equals("Dx"))
                {
                    row.getCells().Add(table.Rows[i]["dx"].ToString());
                }
                else if (__dummyScrutVar5.equals("Description"))
                {
                    row.getCells().Add(table.Rows[i]["description"].ToString());
                }
                else if (__dummyScrutVar5.equals("Stat"))
                {
                    row.getCells().Add(table.Rows[i]["procStatus"].ToString());
                }
                else if (__dummyScrutVar5.equals("Prov"))
                {
                    row.getCells().Add(table.Rows[i]["prov"].ToString());
                }
                else if (__dummyScrutVar5.equals("Amount"))
                {
                    row.getCells().Add(table.Rows[i]["procFee"].ToString());
                }
                else if (__dummyScrutVar5.equals("ADA Code"))
                {
                    row.getCells().Add(table.Rows[i]["ProcCode"].ToString());
                }
                else if (__dummyScrutVar5.equals("User"))
                {
                    row.getCells().Add(table.Rows[i]["user"].ToString());
                }
                else if (__dummyScrutVar5.equals("Signed"))
                {
                    row.getCells().Add(table.Rows[i]["signature"].ToString());
                }
                else if (__dummyScrutVar5.equals("Priority"))
                {
                    row.getCells().Add(table.Rows[i]["priority"].ToString());
                }
                else if (__dummyScrutVar5.equals("Date Entry"))
                {
                    row.getCells().Add(table.Rows[i]["dateEntryC"].ToString());
                }
                else if (__dummyScrutVar5.equals("Prognosis"))
                {
                    row.getCells().Add(table.Rows[i]["prognosis"].ToString());
                }
                else if (__dummyScrutVar5.equals("Date TP"))
                {
                    row.getCells().Add(table.Rows[i]["dateTP"].ToString());
                }
                else if (__dummyScrutVar5.equals("End Time"))
                {
                    row.getCells().Add(table.Rows[i]["procTimeEnd"].ToString());
                }
                else if (__dummyScrutVar5.equals("Quadrant"))
                {
                    row.getCells().Add(table.Rows[i]["quadrant"].ToString());
                }
                else if (__dummyScrutVar5.equals("Schedule By"))
                {
                    row.getCells().Add(table.Rows[i]["orionDateScheduleBy"].ToString());
                }
                else if (__dummyScrutVar5.equals("Stop Clock"))
                {
                    row.getCells().Add(table.Rows[i]["orionDateStopClock"].ToString());
                }
                else if (__dummyScrutVar5.equals("DPC"))
                {
                    row.getCells().Add(table.Rows[i]["orionDPC"].ToString());
                }
                else if (__dummyScrutVar5.equals("Effective Comm"))
                {
                    row.getCells().Add(table.Rows[i]["orionIsEffectiveComm"].ToString());
                }
                else if (__dummyScrutVar5.equals("On Call"))
                {
                    row.getCells().Add(table.Rows[i]["orionIsOnCall"].ToString());
                }
                else if (__dummyScrutVar5.equals("Stat 2"))
                {
                    row.getCells().Add(table.Rows[i]["orionStatus2"].ToString());
                }
                else if (__dummyScrutVar5.equals("DPCpost"))
                {
                    row.getCells().Add(table.Rows[i]["orionDPCpost"].ToString());
                }
                else if (__dummyScrutVar5.equals("Length"))
                {
                    row.getCells().Add(table.Rows[i]["length"].ToString());
                }
                else if (__dummyScrutVar5.equals("Abbr"))
                {
                    row.getCells().Add(table.Rows[i]["AbbrDesc"].ToString());
                }
                else if (__dummyScrutVar5.equals("Locked"))
                {
                    row.getCells().Add(table.Rows[i]["isLocked"].ToString());
                }
                else
                {
                    row.getCells().add("");
                }                            
            }
            if (checkNotes.Checked)
            {
                row.setNote(table.Rows[i]["note"].ToString());
            }
             
            row.setColorText(Color.FromArgb(PIn.Int(table.Rows[i]["colorText"].ToString())));
            row.setColorBackG(Color.FromArgb(PIn.Int(table.Rows[i]["colorBackG"].ToString())));
            row.setTag(table.Rows[i]);
            gridProg.getRows().add(row);
        }
        ChartLayoutHelper.SetGridProgWidth(gridProg, ClientSize, panelEcw, textTreatmentNotes, toothChart);
        gridProg.endUpdate();
        if (Chartscrollval == 0)
        {
            gridProg.scrollToEnd();
        }
        else
        {
            gridProg.setScrollValue(Chartscrollval);
            Chartscrollval = 0;
        } 
        fillToothChart(retainSelection);
    }

    private void fillChartViewsGrid() throws Exception {
        if (PatCur == null)
        {
            butChartViewAdd.Enabled = false;
            butChartViewDown.Enabled = false;
            butChartViewUp.Enabled = false;
            gridChartViews.Enabled = false;
            return ;
        }
        else
        {
            butChartViewAdd.Enabled = true;
            butChartViewDown.Enabled = true;
            butChartViewUp.Enabled = true;
            gridChartViews.Enabled = true;
        } 
        ChartViews.refreshCache();
        gridChartViews.beginUpdate();
        gridChartViews.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("GridChartViews","F#"),25);
        gridChartViews.getColumns().add(col);
        col = new ODGridColumn(Lan.g("GridChartViews","View"),0);
        gridChartViews.getColumns().add(col);
        gridChartViews.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ChartViews.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //assign hot keys F1-F12
            if (i < 11)
            {
                row.getCells().add("F" + (i + 1));
            }
             
            row.getCells().Add(ChartViews.getListt()[i].Description);
            gridChartViews.getRows().add(row);
        }
        gridChartViews.endUpdate();
    }

    /**
    * FillChartViewsGrid should be called first to synch the grids thus having the chart view cache already refreshed.
    */
    private void fillCustomerViewsGrid() throws Exception {
        //ChartViews.RefreshCache();
        gridCustomerViews.beginUpdate();
        gridCustomerViews.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("GridCustomerViews","F#"),25);
        gridCustomerViews.getColumns().add(col);
        col = new ODGridColumn(Lan.g("GridCustomerViews","View"),0);
        gridCustomerViews.getColumns().add(col);
        gridCustomerViews.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ChartViews.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            //assign hot keys F1-F12
            if (i < 11)
            {
                row.getCells().add("F" + (i + 1));
            }
             
            row.getCells().Add(ChartViews.getListt()[i].Description);
            gridCustomerViews.getRows().add(row);
        }
        gridCustomerViews.endUpdate();
    }

    private void fillCustomerTab() throws Exception {
        fillCustomerViewsGrid();
        if (PatCur == null)
        {
            gridCustomerViews.Enabled = false;
            listCommonProcs.Enabled = false;
            labelMonth1.Text = "";
            labelMonth2.Text = "";
            labelMonth3.Text = "";
        }
        else
        {
            //Monthly call time breakdown.
            DateTime startDate = new DateTime(1, 1, 1);
            Procedure firstProc = Procedures.getFirstCompletedProcForFamily(PatCur.Guarantor);
            if (firstProc != null)
            {
                startDate = firstProc.ProcDate;
            }
             
            DateTime month0 = DateTime.Now;
            DateTime month1 = DateTime.Now.AddMonths(-1);
            DateTime month2 = DateTime.Now.AddMonths(-2);
            DateTime month3 = DateTime.Now.AddMonths(-3);
            //Set the month labels.
            labelMonth0.Text = CultureInfo.CurrentCulture.DateTimeFormat.GetAbbreviatedMonthName(month0.Month);
            labelMonth1.Text = CultureInfo.CurrentCulture.DateTimeFormat.GetAbbreviatedMonthName(month1.Month);
            labelMonth2.Text = CultureInfo.CurrentCulture.DateTimeFormat.GetAbbreviatedMonthName(month2.Month);
            labelMonth3.Text = CultureInfo.CurrentCulture.DateTimeFormat.GetAbbreviatedMonthName(month3.Month);
            List<Commlog> commlogsList = Commlogs.getTimedCommlogsForPat(PatCur.Guarantor);
            TimeSpan month0Span = new TimeSpan();
            TimeSpan month1Span = new TimeSpan();
            TimeSpan month2Span = new TimeSpan();
            TimeSpan month3Span = new TimeSpan();
            TimeSpan totalSpan = new TimeSpan();
            int avgCount = 0;
            boolean addToAvg = true;
            for (int i = 0;i < commlogsList.Count;i++)
            {
                //Add up the length of time each call took within the corresponding month.
                DateTime tempDateTime = commlogsList[i].CommDateTime;
                DateTime tempTimeEnd = commlogsList[i].DateTimeEnd;
                TimeSpan tempSpan = tempTimeEnd - tempDateTime;
                if (tempDateTime.Year == month0.Year && tempDateTime.Month == month0.Month)
                {
                    month0Span = month0Span.Add(tempSpan);
                    addToAvg = false;
                }
                else //Avg should not include this months numbers.
                if (tempDateTime.Year == month1.Year && tempDateTime.Month == month1.Month)
                {
                    month1Span = month1Span.Add(tempSpan);
                }
                else if (tempDateTime.Year == month2.Year && tempDateTime.Month == month2.Month)
                {
                    month2Span = month2Span.Add(tempSpan);
                }
                else if (tempDateTime.Year == month3.Year && tempDateTime.Month == month3.Month)
                {
                    month3Span = month3Span.Add(tempSpan);
                }
                    
                //Take current commlog and see if its greater than or equal to two months after first completed proc date.
                if (new DateTime(tempDateTime.Year, tempDateTime.Month, 1) >= new DateTime(startDate.AddMonths(2).Year, startDate.AddMonths(2).Month, 1) && addToAvg)
                {
                    totalSpan = totalSpan.Add(tempSpan);
                    avgCount++;
                }
                 
                addToAvg = true;
            }
            if (month0Span.Hours >= 3)
            {
                textMonth0.BackColor = Color.Red;
                textMonth0.ForeColor = Color.White;
                textMonth0.Font = new Font(textMonth1.Font, FontStyle.Bold);
            }
            else
            {
                textMonth0.ForeColor = Color.Black;
                textMonth0.BackColor = SystemColors.Control;
                textMonth0.Font = new Font(textMonth1.Font, FontStyle.Regular);
            } 
            if (month1Span.Hours >= 3)
            {
                textMonth1.BackColor = Color.Red;
                textMonth1.ForeColor = Color.White;
                textMonth1.Font = new Font(textMonth1.Font, FontStyle.Bold);
            }
            else
            {
                textMonth1.ForeColor = Color.Black;
                textMonth1.BackColor = SystemColors.Control;
                textMonth1.Font = new Font(textMonth1.Font, FontStyle.Regular);
            } 
            if (month2Span.Hours >= 3)
            {
                textMonth2.BackColor = Color.Red;
                textMonth2.ForeColor = Color.White;
                textMonth2.Font = new Font(textMonth2.Font, FontStyle.Bold);
            }
            else
            {
                textMonth2.ForeColor = Color.Black;
                textMonth2.BackColor = SystemColors.Control;
                textMonth2.Font = new Font(textMonth2.Font, FontStyle.Regular);
            } 
            if (month3Span.Hours >= 3)
            {
                textMonth3.BackColor = Color.Red;
                textMonth3.ForeColor = Color.White;
                textMonth3.Font = new Font(textMonth3.Font, FontStyle.Bold);
            }
            else
            {
                textMonth3.ForeColor = Color.Black;
                textMonth3.BackColor = SystemColors.Control;
                textMonth3.Font = new Font(textMonth3.Font, FontStyle.Regular);
            } 
            //Set the text of the boxes.
            textMonth0.Text = month0Span.ToStringHmm();
            textMonth1.Text = month1Span.ToStringHmm();
            textMonth2.Text = month2Span.ToStringHmm();
            textMonth3.Text = month3Span.ToStringHmm();
            if (avgCount > 0)
            {
                int test = (int)totalSpan.TotalMinutes / avgCount;
                textMonthAvg.Text = (new TimeSpan(0, (int)totalSpan.TotalMinutes / avgCount, 0)).ToStringHmm();
            }
            else
            {
                textMonthAvg.Text = "";
            } 
        } 
    }

    private void setChartView(ChartView chartView) throws Exception {
        ChartViewCurDisplay = chartView;
        labelCustView.Visible = false;
        chartCustViewChanged = false;
        fillProgNotes();
    }

    /**
    * This is, of course, called when module refreshed.  But it's also called when user sets missing teeth or tooth movements.  In that case, the Progress notes are not refreshed, so it's a little faster.  This also fills in the movement amounts.
    */
    private void fillToothChart(boolean retainSelection) throws Exception {
        Cursor = Cursors.WaitCursor;
        toothChart.SuspendLayout();
        toothChart.setColorBackground(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][10].ItemColor);
        toothChart.setColorText(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][11].ItemColor);
        toothChart.setColorTextHighlight(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][12].ItemColor);
        toothChart.setColorBackHighlight(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][13].ItemColor);
        //remember which teeth were selected
        //ArrayList selectedTeeth=new ArrayList();//integers 1-32
        //for(int i=0;i<toothChart.SelectedTeeth.Length;i++) {
        //	selectedTeeth.Add(Tooth.ToInt(toothChart.SelectedTeeth[i]));
        //}
        List<String> selectedTeeth = new List<String>(toothChart.getSelectedTeeth());
        toothChart.resetTeeth();
        if (PatCur == null)
        {
            toothChart.ResumeLayout();
            fillMovementsAndHidden();
            Cursor = Cursors.Default;
            return ;
        }
         
        for (int i = 0;i < ToothInitialList.Count;i++)
        {
            //primary teeth need to be set before resetting selected teeth, because some of them might be primary.
            //primary teeth also need to be set before initial list so that we can set a primary tooth missing.
            if (ToothInitialList[i].InitialType == ToothInitialType.Primary)
            {
                toothChart.SetPrimary(ToothInitialList[i].ToothNum);
            }
             
        }
        if (checkShowTeeth.Checked || retainSelection)
        {
            for (int i = 0;i < selectedTeeth.Count;i++)
            {
                toothChart.SetSelected(selectedTeeth[i], true);
            }
        }
         
        for (int i = 0;i < ToothInitialList.Count;i++)
        {
            InitialType __dummyScrutVar6 = ToothInitialList[i].InitialType;
            if (__dummyScrutVar6.equals(ToothInitialType.Missing))
            {
                toothChart.SetMissing(ToothInitialList[i].ToothNum);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.Hidden))
            {
                toothChart.SetHidden(ToothInitialList[i].ToothNum);
            }
            else //case ToothInitialType.Primary:
            //	break;
            if (__dummyScrutVar6.equals(ToothInitialType.Rotate))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, ToothInitialList[i].Movement, 0, 0, 0, 0, 0);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.TipM))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, ToothInitialList[i].Movement, 0, 0, 0, 0);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.TipB))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, ToothInitialList[i].Movement, 0, 0, 0);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.ShiftM))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, ToothInitialList[i].Movement, 0, 0);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.ShiftO))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, 0, ToothInitialList[i].Movement, 0);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.ShiftB))
            {
                toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, 0, 0, ToothInitialList[i].Movement);
            }
            else if (__dummyScrutVar6.equals(ToothInitialType.Drawing))
            {
                toothChart.AddDrawingSegment(ToothInitialList[i].Copy());
            }
                     
        }
        drawProcGraphics();
        toothChart.ResumeLayout();
        fillMovementsAndHidden();
        Cursor = Cursors.Default;
    }

    private void drawProcGraphics() throws Exception {
        //this requires: ProcStatus, ProcCode, ToothNum, HideGraphics, Surf, and ToothRange.  All need to be raw database values.
        String[] teeth = new String[]();
        Color cLight = Color.White;
        Color cDark = Color.White;
        for (int i = 0;i < ProcList.Count;i++)
        {
            if (StringSupport.equals(ProcList[i]["HideGraphics"].ToString(), "1"))
            {
                continue;
            }
             
            if (ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).PaintType == ToothPaintingType.Extraction && (PIn.Long(ProcList[i]["ProcStatus"].ToString()) == ((Enum)ProcStat.C).ordinal() || PIn.Long(ProcList[i]["ProcStatus"].ToString()) == ((Enum)ProcStat.EC).ordinal() || PIn.Long(ProcList[i]["ProcStatus"].ToString()) == ((Enum)ProcStat.EO).ordinal()))
            {
                continue;
            }
             
            //prevents the red X. Missing teeth already handled.
            if (ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).GraphicColor == Color.FromArgb(0))
            {
                switch((ProcStat)PIn.Long(ProcList[i]["ProcStatus"].ToString()))
                {
                    case C: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][1].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][6].ItemColor;
                        break;
                    case TP: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][0].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][5].ItemColor;
                        break;
                    case EC: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][2].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][7].ItemColor;
                        break;
                    case EO: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][3].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][8].ItemColor;
                        break;
                    case R: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][4].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][9].ItemColor;
                        break;
                    case Cn: 
                        cDark = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][16].ItemColor;
                        cLight = DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][17].ItemColor;
                        break;
                
                }
            }
            else
            {
                cDark = ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).GraphicColor;
                cLight = ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).GraphicColor;
            } 
            PaintType __dummyScrutVar8 = ProcedureCodes.GetProcCode(ProcList[i]["ProcCode"].ToString()).PaintType;
            if (__dummyScrutVar8.equals(ToothPaintingType.BridgeDark))
            {
                if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, ProcList[i]["ToothNum"].ToString()))
                {
                    toothChart.SetPontic(ProcList[i]["ToothNum"].ToString(), cDark);
                }
                else
                {
                    toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cDark);
                } 
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.BridgeLight))
            {
                if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, ProcList[i]["ToothNum"].ToString()))
                {
                    toothChart.SetPontic(ProcList[i]["ToothNum"].ToString(), cLight);
                }
                else
                {
                    toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cLight);
                } 
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.CrownDark))
            {
                toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.CrownLight))
            {
                toothChart.SetCrown(ProcList[i]["ToothNum"].ToString(), cLight);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.DentureDark))
            {
                if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "U"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 2).ToString();
                    }
                }
                else if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "L"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 18).ToString();
                    }
                }
                else
                {
                    teeth = ProcList[i]["ToothRange"].ToString().Split(new char[]{ ',' });
                }  
                for (int t = 0;t < teeth.Length;t++)
                {
                    if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, teeth[t]))
                    {
                        toothChart.SetPontic(teeth[t], cDark);
                    }
                    else
                    {
                        toothChart.SetCrown(teeth[t], cDark);
                    } 
                }
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.DentureLight))
            {
                if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "U"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 2).ToString();
                    }
                }
                else if (StringSupport.equals(ProcList[i]["Surf"].ToString(), "L"))
                {
                    teeth = new String[14];
                    for (int t = 0;t < 14;t++)
                    {
                        teeth[t] = (t + 18).ToString();
                    }
                }
                else
                {
                    teeth = ProcList[i]["ToothRange"].ToString().Split(new char[]{ ',' });
                }  
                for (int t = 0;t < teeth.Length;t++)
                {
                    if (ToothInitials.ToothIsMissingOrHidden(ToothInitialList, teeth[t]))
                    {
                        toothChart.SetPontic(teeth[t], cLight);
                    }
                    else
                    {
                        toothChart.SetCrown(teeth[t], cLight);
                    } 
                }
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.Extraction))
            {
                toothChart.SetBigX(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.FillingDark))
            {
                toothChart.SetSurfaceColors(ProcList[i]["ToothNum"].ToString(), ProcList[i]["Surf"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.FillingLight))
            {
                toothChart.SetSurfaceColors(ProcList[i]["ToothNum"].ToString(), ProcList[i]["Surf"].ToString(), cLight);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.Implant))
            {
                toothChart.SetImplant(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.PostBU))
            {
                toothChart.SetBU(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.RCT))
            {
                toothChart.SetRCT(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.Sealant))
            {
                toothChart.SetSealant(ProcList[i]["ToothNum"].ToString(), cDark);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.Veneer))
            {
                toothChart.SetVeneer(ProcList[i]["ToothNum"].ToString(), cLight);
            }
            else if (__dummyScrutVar8.equals(ToothPaintingType.Watch))
            {
                toothChart.SetWatch(ProcList[i]["ToothNum"].ToString(), cDark);
            }
                           
        }
    }

    private void checkToday_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        if (checkToday.Checked)
        {
            textDate.Text = DateTime.Today.ToShortDateString();
        }
        else
        {
        } 
    }

    //
    /**
    * Gets run with each ModuleSelected.  Fills Dx, Prognosis, Priorities, ProcButtons, Date, and Image categories
    */
    private void fillDxProcImage() throws Exception {
        //if(textDate.errorProvider1.GetError(textDate)==""){
        if (checkToday.Checked)
        {
            //textDate.Text=="" ||
            textDate.Text = DateTime.Today.ToShortDateString();
        }
         
        //}
        listDx.Items.Clear();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()].Length;i++)
        {
            //move to instantClasses?
            this.listDx.Items.Add(DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()][i].ItemName);
        }
        int selectedPrognosis = comboPrognosis.SelectedIndex;
        //retain prognosis selection
        comboPrognosis.Items.Clear();
        comboPrognosis.Items.Add(Lan.g(this,"no prognosis"));
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()].Length;i++)
        {
            comboPrognosis.Items.Add(DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()][i].ItemName);
        }
        int selectedPriority = comboPriority.SelectedIndex;
        //retain current selection
        comboPriority.Items.Clear();
        comboPriority.Items.Add(Lan.g(this,"no priority"));
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()].Length;i++)
        {
            this.comboPriority.Items.Add(DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][i].ItemName);
        }
        if (selectedPrognosis > 0 && selectedPrognosis < comboPrognosis.Items.Count)
        {
            comboPrognosis.SelectedIndex = selectedPrognosis;
        }
        else
        {
            comboPrognosis.SelectedIndex = 0;
        } 
        if (selectedPriority > 0 && selectedPriority < comboPriority.Items.Count)
            //set the selected to what it was before.
            comboPriority.SelectedIndex = selectedPriority;
        else
            comboPriority.SelectedIndex = 0; 
        //or just set to no priority
        int selectedButtonCat = listButtonCats.SelectedIndex;
        listButtonCats.Items.Clear();
        listButtonCats.Items.Add(Lan.g(this,"Quick Buttons"));
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()].Length;i++)
        {
            listButtonCats.Items.Add(DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()][i].ItemName);
        }
        if (selectedButtonCat < listButtonCats.Items.Count)
        {
            listButtonCats.SelectedIndex = selectedButtonCat;
        }
         
        if (listButtonCats.SelectedIndex == -1 && listButtonCats.Items.Count > 0)
        {
            listButtonCats.SelectedIndex = 0;
        }
         
        fillProcButtons();
        int selectedImageTab = tabControlImages.SelectedIndex;
        //retains current selection
        tabControlImages.TabPages.Clear();
        TabPage page = new TabPage();
        page = new TabPage();
        page.Text = Lan.g(this,"All");
        tabControlImages.TabPages.Add(page);
        visImageCats = new ArrayList();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()].Length;i++)
        {
            if (StringSupport.equals(DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].ItemValue, "X") || StringSupport.equals(DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].ItemValue, "XP"))
            {
                //if tagged to show in Chart
                visImageCats.Add(i);
                page = new TabPage();
                page.Text = DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][i].ItemName;
                tabControlImages.TabPages.Add(page);
            }
             
        }
        if (selectedImageTab < tabControlImages.TabCount)
        {
            tabControlImages.SelectedIndex = selectedImageTab;
        }
         
        panelTPdark.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][0].ItemColor;
        panelCdark.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][1].ItemColor;
        panelECdark.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][2].ItemColor;
        panelEOdark.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][3].ItemColor;
        panelRdark.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][4].ItemColor;
        panelTPlight.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][5].ItemColor;
        panelClight.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][6].ItemColor;
        panelEClight.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][7].ItemColor;
        panelEOlight.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][8].ItemColor;
        panelRlight.BackColor = DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][9].ItemColor;
    }

    private void fillProcButtons() throws Exception {
        listViewButtons.Items.Clear();
        imageListProcButtons.Images.Clear();
        panelQuickButtons.Visible = false;
        if (listButtonCats.SelectedIndex == -1)
        {
            ProcButtonList = new ProcButton[0];
            return ;
        }
         
        if (listButtonCats.SelectedIndex == 0)
        {
            panelQuickButtons.Visible = true;
            panelQuickButtons.Location = listViewButtons.Location;
            panelQuickButtons.Size = listViewButtons.Size;
            ProcButtonList = new ProcButton[0];
            return ;
        }
         
        ProcButtons.refreshCache();
        ProcButtonList = ProcButtons.GetForCat(DefC.getShort()[((Enum)DefCat.ProcButtonCats).ordinal()][listButtonCats.SelectedIndex - 1].DefNum);
        ListViewItem item = new ListViewItem();
        for (int i = 0;i < ProcButtonList.Length;i++)
        {
            if (!StringSupport.equals(ProcButtonList[i].ButtonImage, ""))
            {
                //image keys are simply the ProcButtonNum
                imageListProcButtons.Images.Add(ProcButtonList[i].ProcButtonNum.ToString(), PIn.Bitmap(ProcButtonList[i].ButtonImage));
            }
             
            item = new ListViewItem(new String[]{ ProcButtonList[i].Description }, ProcButtonList[i].ProcButtonNum.ToString());
            listViewButtons.Items.Add(item);
        }
    }

    /**
    * Gets run on ModuleSelected and each time a different images tab is selected. It first creates any missing thumbnails, then displays them. So it will be faster after the first time.
    */
    private void fillImages() throws Exception {
        visImages = new ArrayList();
        listViewImages.Items.Clear();
        imageListThumbnails.Images.Clear();
        if (!PrefC.getAtoZfolderUsed())
        {
            return ;
        }
         
        //Don't show any images if there is no document path.
        if (PatCur == null)
        {
            return ;
        }
         
        if (StringSupport.equals(patFolder, ""))
        {
            return ;
        }
         
        if (!panelImages.Visible)
        {
            return ;
        }
         
        for (int i = 0;i < DocumentList.Length;i++)
        {
            if (!visImageCats.Contains(DefC.GetOrder(DefCat.ImageCats, DocumentList[i].DocCategory)))
            {
                continue;
            }
             
            //if category not visible, continue
            if (tabControlImages.SelectedIndex > 0)
            {
                //any category except 'all'
                if (DocumentList[i].DocCategory != DefC.getShort()[((Enum)DefCat.ImageCats).ordinal()][(int)visImageCats[tabControlImages.SelectedIndex - 1]].DefNum)
                {
                    continue;
                }
                 
            }
             
            //if not in category, continue
            //Documents.Cur=DocumentList[i];
            imageListThumbnails.Images.Add(Documents.GetThumbnail(DocumentList[i], patFolder, imageListThumbnails.ImageSize.Width));
            visImages.Add(i);
            ListViewItem item = new ListViewItem(DocumentList[i].DateCreated.ToShortDateString() + ": " + DocumentList[i].Description, imageListThumbnails.Images.Count - 1);
            //item.ToolTipText=patFolder+DocumentList[i].FileName;//not supported by Mono
            listViewImages.Items.Add(item);
        }
    }

    //for
    private void clearButtons() throws Exception {
        //unfortunately, these colors no longer show since the XP button style was introduced.
        butM.BackColor = Color.FromName("Control");
            ;
        butOI.BackColor = Color.FromName("Control");
        butD.BackColor = Color.FromName("Control");
        butL.BackColor = Color.FromName("Control");
        butBF.BackColor = Color.FromName("Control");
        butV.BackColor = Color.FromName("Control");
        textSurf.Text = "";
        listDx.SelectedIndex = -1;
        //listProcButtons.SelectedIndex=-1;
        listViewButtons.SelectedIndices.Clear();
        textProcCode.Text = Lan.g(this,"Type Proc Code");
    }

    private void updateSurf() throws Exception {
        textSurf.Text = "";
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butM.BackColor == Color.White)
        {
            textSurf.AppendText("M");
        }
         
        if (butOI.BackColor == Color.White)
        {
            if (ToothGraphic.IsAnterior(toothChart.getSelectedTeeth()[0]))
            {
                textSurf.AppendText("I");
            }
            else
            {
                textSurf.AppendText("O");
            } 
        }
         
        if (butD.BackColor == Color.White)
        {
            textSurf.AppendText("D");
        }
         
        if (butV.BackColor == Color.White)
        {
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                textSurf.AppendText("5");
            }
            else
            {
                textSurf.AppendText("V");
            } 
        }
         
        if (butBF.BackColor == Color.White)
        {
            if (ToothGraphic.IsAnterior(toothChart.getSelectedTeeth()[0]))
            {
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    textSurf.AppendText("V");
                }
                else
                {
                    //vestibular
                    textSurf.AppendText("F");
                } 
            }
            else
            {
                textSurf.AppendText("B");
            } 
        }
         
        if (butL.BackColor == Color.White)
        {
            textSurf.AppendText("L");
        }
         
    }

    private void butBF_Click(Object sender, System.EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butBF.BackColor == Color.White)
        {
            butBF.BackColor = SystemColors.Control;
        }
        else
        {
            butBF.BackColor = Color.White;
        } 
        updateSurf();
    }

    private void butV_Click(Object sender, System.EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butV.BackColor == Color.White)
        {
            butV.BackColor = SystemColors.Control;
        }
        else
        {
            butV.BackColor = Color.White;
        } 
        updateSurf();
    }

    private void butM_Click(Object sender, System.EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butM.BackColor == Color.White)
        {
            butM.BackColor = SystemColors.Control;
        }
        else
        {
            butM.BackColor = Color.White;
        } 
        updateSurf();
    }

    private void butOI_Click(Object sender, System.EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butOI.BackColor == Color.White)
        {
            butOI.BackColor = SystemColors.Control;
        }
        else
        {
            butOI.BackColor = Color.White;
        } 
        updateSurf();
    }

    private void butD_Click(Object sender, System.EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butD.BackColor == Color.White)
        {
            butD.BackColor = SystemColors.Control;
        }
        else
        {
            butD.BackColor = Color.White;
        } 
        updateSurf();
    }

    private void butL_Click(Object sender, System.EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            return ;
        }
         
        if (butL.BackColor == Color.White)
        {
            butL.BackColor = SystemColors.Control;
        }
        else
        {
            butL.BackColor = Color.White;
        } 
        updateSurf();
    }

    private void gridProg_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Chartscrollval = gridProg.getScrollValue();
        DataRow row = (DataRow)gridProg.getRows().get___idx(e.getRow()).getTag();
        if (!StringSupport.equals(row["ProcNum"].ToString(), "0"))
        {
            if (checkAudit.Checked)
            {
                MsgBox.show(this,"Not allowed to edit procedures when in audit mode.");
                return ;
            }
             
            Procedure proc = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), true);
            if (StringSupport.equals(ProcedureCodes.getStringProcCode(proc.CodeNum), ProcedureCodes.GroupProcCode))
            {
                FormProcGroup FormP = new FormProcGroup();
                List<ProcGroupItem> groupItemList = ProcGroupItems.getForGroup(proc.ProcNum);
                List<Procedure> procList = new List<Procedure>();
                for (int i = 0;i < groupItemList.Count;i++)
                {
                    procList.Add(Procedures.GetOneProc(groupItemList[i].ProcNum, false));
                }
                FormP.GroupCur = proc;
                FormP.GroupItemList = groupItemList;
                FormP.ProcList = procList;
                FormP.ShowDialog();
                if (FormP.DialogResult == DialogResult.OK)
                {
                    moduleSelected(PatCur.PatNum);
                    fillProgNotes();
                }
                 
                return ;
            }
            else
            {
                FormProcEdit FormP = new FormProcEdit(proc,PatCur,FamCur);
                Plugins.hookAddCode(this,"ContrChart.gridProg_CellDoubleClick_proc",proc,FormP);
                if (!FormP.IsDisposed)
                {
                    //Form might be disposed by the above hook.
                    FormP.ShowDialog();
                }
                 
                Plugins.hookAddCode(this,"ContrChart.gridProg_CellDoubleClick_proc2",proc,FormP);
                if (FormP.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
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
        else //needs to always refresh due to complex ok/cancel
        if (!StringSupport.equals(row["TaskNum"].ToString(), "0"))
        {
            Task task = Tasks.GetOne(PIn.Long(row["TaskNum"].ToString()));
            if (task == null)
            {
                MsgBox.show(this,"This task has been deleted by another user.");
            }
            else
            {
                FormTaskEdit FormT = new FormTaskEdit(task,task.copy());
                FormT.Closing += new CancelEventHandler(TaskGoToEvent);
                FormT.Show();
            } 
        }
        else //non-modal
        if (!StringSupport.equals(row["AptNum"].ToString(), "0"))
        {
            //Appointment apt=Appointments.GetOneApt(
            FormApptEdit FormA = new FormApptEdit(PIn.Long(row["AptNum"].ToString()));
            //PinIsVisible=false
            FormA.IsInChartModule = true;
            FormA.ShowDialog();
            if (FormA.CloseOD)
            {
                ((Form)this.Parent).Close();
                return ;
            }
             
            if (FormA.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(row["EmailMessageNum"].ToString(), "0"))
        {
            EmailMessage msg = EmailMessages.GetOne(PIn.Long(row["EmailMessageNum"].ToString()));
            if (msg.SentOrReceived == EmailSentOrReceived.WebMailReceived || msg.SentOrReceived == EmailSentOrReceived.WebMailRecdRead || msg.SentOrReceived == EmailSentOrReceived.WebMailSent || msg.SentOrReceived == EmailSentOrReceived.WebMailSentRead)
            {
                //web mail uses special secure messaging portal
                FormWebMailMessageEdit FormWMME = new FormWebMailMessageEdit(PatCur.PatNum,msg.EmailMessageNum);
                if (FormWMME.ShowDialog() != DialogResult.OK)
                {
                    return ;
                }
                 
            }
            else
            {
                FormEmailMessageEdit FormE = new FormEmailMessageEdit(msg);
                FormE.ShowDialog();
                if (FormE.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
            } 
        }
        else if (!StringSupport.equals(row["SheetNum"].ToString(), "0"))
        {
            Sheet sheet = Sheets.GetSheet(PIn.Long(row["SheetNum"].ToString()));
            FormSheetFillEdit FormSFE = new FormSheetFillEdit(sheet);
            FormSFE.ShowDialog();
            if (FormSFE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(row["FormPatNum"].ToString(), "0"))
        {
            FormPat form = FormPats.GetOne(PIn.Long(row["FormPatNum"].ToString()));
            FormFormPatEdit FormP = new FormFormPatEdit();
            FormP.FormPatCur = form;
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.OK)
            {
                moduleSelected(PatCur.PatNum);
            }
             
        }
                 
        //Why is this called here and down 3 lines? Do we need the Allocator, or should we return here?
        moduleSelected(PatCur.PatNum);
        MyAllocator1_ProviderPayment.allocateWithToolCheck(this.PatCur.Guarantor);
    }

    public void taskGoToEvent(Object sender, CancelEventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        FormTaskEdit FormT = (FormTaskEdit)sender;
        TaskObjectType GotoType = FormT.GotoType;
        long keyNum = FormT.GotoKeyNum;
        if (GotoType == TaskObjectType.None)
        {
            moduleSelected(PatCur.PatNum);
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
    /**
    * Sets many fields for a new procedure, then displays it for editing before inserting it into the db.  No need to worry about ProcOld because it's an insert, not an update.
    */
    private void addProcedure(Procedure ProcCur) throws Exception {
        //procnum
        ProcCur.PatNum = PatCur.PatNum;
        //aptnum
        //proccode
        //ProcCur.CodeNum=ProcedureCodes.GetProcCode(ProcCur.OldCode).CodeNum;//already set
        if (newStatus == ProcStat.EO)
        {
            ProcCur.ProcDate = DateTime.MinValue;
        }
        else if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            ProcCur.ProcDate = DateTimeOD.getToday();
        }
        else
        {
            ProcCur.ProcDate = PIn.Date(textDate.Text);
        }  
        ProcCur.DateTP = ProcCur.ProcDate;
        if (newStatus == ProcStat.R || newStatus == ProcStat.EO || newStatus == ProcStat.EC)
        {
            ProcCur.ProcFee = 0;
        }
        else
        {
            //int totUnits = ProcCur.BaseUnits + ProcCur.UnitQty;
            InsPlan priplan = null;
            InsSub prisub = null;
            if (PatPlanList.Count > 0)
            {
                prisub = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
                priplan = InsPlans.getPlan(prisub.PlanNum,PlanList);
            }
             
            //Check if it's a medical procedure.
            double insfee = new double();
            boolean isMed = false;
            ProcCur.MedicalCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).MedicalCode;
            if (ProcCur.MedicalCode != null && !StringSupport.equals(ProcCur.MedicalCode, ""))
            {
                isMed = true;
            }
             
            //Get fee schedule for medical or dental.
            long feeSch = new long();
            if (isMed)
            {
                feeSch = Fees.getMedFeeSched(PatCur,PlanList,PatPlanList,SubList);
            }
            else
            {
                feeSch = Fees.getFeeSched(PatCur,PlanList,PatPlanList,SubList);
            } 
            //Get the fee amount for medical or dental.
            if (PrefC.getBool(PrefName.MedicalFeeUsedForNewProcs) && isMed)
            {
                insfee = Fees.getAmount0(ProcedureCodes.getProcCode(ProcCur.MedicalCode).CodeNum,feeSch);
            }
            else
            {
                insfee = Fees.getAmount0(ProcCur.CodeNum,feeSch);
            } 
            if (priplan != null && StringSupport.equals(priplan.PlanType, "p") && !isMed)
            {
                //PPO
                double standardfee = Fees.getAmount0(ProcCur.CodeNum,Providers.getProv(Patients.getProvNum(PatCur)).FeeSched);
                if (standardfee > insfee)
                {
                    ProcCur.ProcFee = standardfee;
                }
                else
                {
                    ProcCur.ProcFee = insfee;
                } 
            }
            else
            {
                ProcCur.ProcFee = insfee;
            } 
        } 
        //ProcCur.OverridePri=-1;
        //ProcCur.OverrideSec=-1;
        //surf
        //ToothNum
        //Procedures.Cur.ToothRange
        //ProcCur.NoBillIns=ProcedureCodes.GetProcCode(ProcCur.ProcCode).NoBillIns;
        if (comboPriority.SelectedIndex == 0)
        {
            ProcCur.Priority = 0;
        }
        else
        {
            ProcCur.Priority = DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][comboPriority.SelectedIndex - 1].DefNum;
        } 
        ProcCur.ProcStatus = newStatus;
        long provPri = PatCur.PriProv;
        long provSec = PatCur.SecProv;
        for (int i = 0;i < ApptList.Length;i++)
        {
            if (ApptList[i].AptDateTime.Date == DateTime.Today && ApptList[i].AptStatus != ApptStatus.Planned)
            {
                provPri = ApptList[i].ProvNum;
                provSec = ApptList[i].ProvHyg;
                break;
            }
             
        }
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).IsHygiene && provSec != 0)
        {
            ProcCur.ProvNum = provSec;
        }
        else
        {
            ProcCur.ProvNum = provPri;
        } 
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault != 0)
        {
            //override provnum if there is a default for this proc
            ProcCur.ProvNum = ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault;
        }
         
        if (newStatus == ProcStat.C)
        {
            ProcCur.Note = ProcCodeNotes.getNote(ProcCur.ProvNum,ProcCur.CodeNum);
        }
        else
        {
            ProcCur.Note = "";
        } 
        ProcCur.ClinicNum = PatCur.ClinicNum;
        if (listDx.SelectedIndex != -1)
        {
            ProcCur.Dx = DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()][listDx.SelectedIndex].DefNum;
        }
         
        if (comboPrognosis.SelectedIndex == 0)
        {
            ProcCur.Prognosis = 0;
        }
        else
        {
            ProcCur.Prognosis = DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()][comboPrognosis.SelectedIndex - 1].DefNum;
        } 
        //nextaptnum
        ProcCur.DateEntryC = DateTime.Now;
        ProcCur.BaseUnits = ProcedureCodes.getProcCode(ProcCur.CodeNum).BaseUnits;
        ProcCur.SiteNum = PatCur.SiteNum;
        ProcCur.RevCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).RevenueCodeDefault;
        ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
        Procedures.insert(ProcCur);
        if ((ProcCur.ProcStatus == ProcStat.C || ProcCur.ProcStatus == ProcStat.EC || ProcCur.ProcStatus == ProcStat.EO) && ProcedureCodes.getProcCode(ProcCur.CodeNum).PaintType == ToothPaintingType.Extraction)
        {
            //if an extraction, then mark previous procs hidden
            //Procedures.SetHideGraphical(ProcCur);//might not matter anymore
            ToothInitials.setValue(PatCur.PatNum,ProcCur.ToothNum,ToothInitialType.Missing);
        }
         
        Procedures.ComputeEstimates(ProcCur, PatCur.PatNum, new List<ClaimProc>(), true, PlanList, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        FormProcEdit FormPE = new FormProcEdit(ProcCur,PatCur.copy(),FamCur);
        FormPE.IsNew = true;
        FormPE.ShowDialog();
        if (FormPE.DialogResult == DialogResult.Cancel)
        {
            try
            {
                //any created claimprocs are automatically deleted from within procEdit window.
                Procedures.delete(ProcCur.ProcNum);
            }
            catch (Exception ex)
            {
                //also deletes the claimprocs
                MessageBox.Show(ex.Message);
            }
        
        }
        else if (usingEcwTight())
        {
        }
        else //do not synch recall.  Too slow
        if (Programs.getUsingOrion())
        {
        }
        else //No need to synch with Orion mode.
        if (newStatus == ProcStat.C || newStatus == ProcStat.EC || newStatus == ProcStat.EO)
        {
            //only run Recalls for completed, existing current, or existing other
            //Auto-insert default encounter for provider.
            if (newStatus == ProcStat.C)
            {
                Encounters.insertDefaultEncounter(ProcCur.PatNum,ProcCur.ProvNum,ProcCur.ProcDate);
            }
             
            Recalls.synch(PatCur.PatNum);
        }
            
    }

    /**
    * No user dialog is shown.  This only works for some kinds of procedures.  Set the codeNum first.
    */
    private void addQuick(Procedure ProcCur) throws Exception {
        Plugins.hookAddCode(this,"ContrChart.AddQuick_begin",ProcCur);
        //procnum
        ProcCur.PatNum = PatCur.PatNum;
        //aptnum
        //ProcCur.CodeNum=ProcedureCodes.GetProcCode(ProcCur.OldCode).CodeNum;//already set
        if (newStatus == ProcStat.EO)
        {
            ProcCur.ProcDate = DateTime.MinValue;
        }
        else if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            ProcCur.ProcDate = DateTimeOD.getToday();
        }
        else
        {
            ProcCur.ProcDate = PIn.Date(textDate.Text);
        }  
        ProcCur.DateTP = ProcCur.ProcDate;
        if (newStatus == ProcStat.R || newStatus == ProcStat.EO || newStatus == ProcStat.EC)
        {
            ProcCur.ProcFee = 0;
        }
        else
        {
            InsPlan priplan = null;
            InsSub prisub = null;
            if (PatPlanList.Count > 0)
            {
                prisub = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
                priplan = InsPlans.getPlan(prisub.PlanNum,PlanList);
            }
             
            //Check if it's a medical procedure.
            double insfee = new double();
            boolean isMed = false;
            ProcCur.MedicalCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).MedicalCode;
            if (ProcCur.MedicalCode != null && !StringSupport.equals(ProcCur.MedicalCode, ""))
            {
                isMed = true;
            }
             
            //Get fee schedule for medical or dental.
            long feeSch = new long();
            if (isMed)
            {
                feeSch = Fees.getMedFeeSched(PatCur,PlanList,PatPlanList,SubList);
            }
            else
            {
                feeSch = Fees.getFeeSched(PatCur,PlanList,PatPlanList,SubList);
            } 
            //Get the fee amount for medical or dental.
            if (PrefC.getBool(PrefName.MedicalFeeUsedForNewProcs) && isMed)
            {
                insfee = Fees.getAmount0(ProcedureCodes.getProcCode(ProcCur.MedicalCode).CodeNum,feeSch);
            }
            else
            {
                insfee = Fees.getAmount0(ProcCur.CodeNum,feeSch);
            } 
            if (priplan != null && StringSupport.equals(priplan.PlanType, "p") && !isMed)
            {
                //PPO
                double standardfee = Fees.getAmount0(ProcCur.CodeNum,Providers.getProv(Patients.getProvNum(PatCur)).FeeSched);
                if (standardfee > insfee)
                {
                    ProcCur.ProcFee = standardfee;
                }
                else
                {
                    ProcCur.ProcFee = insfee;
                } 
            }
            else
            {
                ProcCur.ProcFee = insfee;
            } 
        } 
        //surf
        //toothnum
        //ToothRange
        if (comboPriority.SelectedIndex == 0)
        {
            ProcCur.Priority = 0;
        }
        else
        {
            ProcCur.Priority = DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][comboPriority.SelectedIndex - 1].DefNum;
        } 
        ProcCur.ProcStatus = newStatus;
        long provPri = PatCur.PriProv;
        long provSec = PatCur.SecProv;
        for (int i = 0;i < ApptList.Length;i++)
        {
            if (ApptList[i].AptDateTime.Date == DateTime.Today && ApptList[i].AptStatus != ApptStatus.Planned)
            {
                provPri = ApptList[i].ProvNum;
                provSec = ApptList[i].ProvHyg;
                break;
            }
             
        }
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).IsHygiene && provSec != 0)
        {
            ProcCur.ProvNum = provSec;
        }
        else
        {
            ProcCur.ProvNum = provPri;
        } 
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault != 0)
        {
            //override provnum if there is a default for this proc
            ProcCur.ProvNum = ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault;
        }
         
        if (newStatus == ProcStat.C)
        {
            ProcCur.Note = ProcCodeNotes.getNote(ProcCur.ProvNum,ProcCur.CodeNum);
        }
        else
        {
            ProcCur.Note = "";
        } 
        ProcCur.ClinicNum = PatCur.ClinicNum;
        if (listDx.SelectedIndex != -1)
        {
            ProcCur.Dx = DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()][listDx.SelectedIndex].DefNum;
        }
         
        if (comboPrognosis.SelectedIndex == 0)
        {
            ProcCur.Prognosis = 0;
        }
        else
        {
            ProcCur.Prognosis = DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()][comboPrognosis.SelectedIndex - 1].DefNum;
        } 
        ProcCur.BaseUnits = ProcedureCodes.getProcCode(ProcCur.CodeNum).BaseUnits;
        ProcCur.SiteNum = PatCur.SiteNum;
        ProcCur.RevCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).RevenueCodeDefault;
        //nextaptnum
        ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
        Procedures.insert(ProcCur);
        if ((ProcCur.ProcStatus == ProcStat.C || ProcCur.ProcStatus == ProcStat.EC || ProcCur.ProcStatus == ProcStat.EO) && ProcedureCodes.getProcCode(ProcCur.CodeNum).PaintType == ToothPaintingType.Extraction)
        {
            //if an extraction, then mark previous procs hidden
            //Procedures.SetHideGraphical(ProcCur);//might not matter anymore
            ToothInitials.setValue(PatCur.PatNum,ProcCur.ToothNum,ToothInitialType.Missing);
        }
         
        if (usingEcwTight())
        {
        }
        else //do not synch recall.  Too slow
        if (Programs.getUsingOrion())
        {
        }
        else //No need to synch with Orion mode.
        if (newStatus == ProcStat.C || newStatus == ProcStat.EC || newStatus == ProcStat.EO)
        {
            //only run Recalls for completed, existing current, or existing other
            //Auto-insert default encounter
            if (newStatus == ProcStat.C)
            {
                Encounters.insertDefaultEncounter(ProcCur.PatNum,ProcCur.ProvNum,ProcCur.ProcDate);
            }
             
            Recalls.synch(PatCur.PatNum);
        }
           
        Procedures.ComputeEstimates(ProcCur, PatCur.PatNum, new List<ClaimProc>(), true, PlanList, PatPlanList, BenefitList, PatCur.getAge(), SubList);
        if (Programs.getUsingOrion())
        {
            //Orion requires a DPC to be set. Force the proc edit window open so they can set it.
            FormProcEdit FormP = new FormProcEdit(ProcCur,PatCur.copy(),FamCur);
            FormP.IsNew = true;
            FormP.OrionProvNum = Providers.getOrionProvNum(ProcCur.ProvNum);
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.Cancel)
            {
                try
                {
                    //any created claimprocs are automatically deleted from within procEdit window.
                    Procedures.delete(ProcCur.ProcNum);
                }
                catch (Exception ex)
                {
                    //also deletes the claimprocs
                    MessageBox.Show(ex.Message);
                }
            
            }
            else
            {
            } 
        }
         
    }

    //Do not synch. Recalls based on ScheduleByDate reports in Orion mode.
    //Recalls.Synch(PatCur.PatNum);
    private void butAddProc_Click(Object sender, System.EventArgs e) throws Exception {
        orionProvNum = 0;
        if (newStatus == ProcStat.C)
        {
            if (!Security.IsAuthorized(Permissions.ProcComplCreate, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        }
         
        boolean isValid = new boolean();
        TreatmentArea tArea = TreatmentArea.None;
        FormProcCodes FormP = new FormProcCodes();
        FormP.IsSelectionMode = true;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        List<String> procCodes = new List<String>();
        Procedures.setDateFirstVisit(DateTimeOD.getToday(),1,PatCur);
        Procedure ProcCur;
        for (int n = 0;n == 0 || n < toothChart.getSelectedTeeth().Count;n++)
        {
            isValid = true;
            ProcCur = new Procedure();
            //going to be an insert, so no need to set Procedures.CurOld
            //Procedure
            ProcCur.CodeNum = FormP.SelectedCodeNum;
            //Procedures.Cur.ProcCode=ProcButtonItems.CodeList[i];
            tArea = ProcedureCodes.getProcCode(ProcCur.CodeNum).TreatArea;
            if ((tArea == TreatmentArea.Arch || tArea == TreatmentArea.Mouth || tArea == TreatmentArea.Quad || tArea == TreatmentArea.Sextant || tArea == TreatmentArea.ToothRange) && n > 0)
            {
                continue;
            }
            else //the only two left are tooth and surf
            //only entered if n=0, so they don't get entered more than once.
            if (tArea == TreatmentArea.Quad)
            {
                //This is optimized for single proc like a space maintainer.  User can select a tooth to set quadrant.
                if (toothChart.getSelectedTeeth().Count > 0)
                {
                    ProcCur.Surf = Tooth.GetQuadrant(toothChart.getSelectedTeeth()[0]);
                }
                 
                addProcedure(ProcCur);
            }
            else if (tArea == TreatmentArea.Surf)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    isValid = false;
                }
                else
                {
                    ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                } 
                //Procedures.Cur=ProcCur;
                if (StringSupport.equals(textSurf.Text, ""))
                {
                    isValid = false;
                }
                else
                {
                    ProcCur.Surf = Tooth.SurfTidyFromDisplayToDb(textSurf.Text, ProcCur.ToothNum);
                } 
                if (isValid)
                {
                    addQuick(ProcCur);
                }
                else
                {
                    addProcedure(ProcCur);
                } 
            }
            else if (tArea == TreatmentArea.Tooth)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    //Procedures.Cur=ProcCur;
                    addProcedure(ProcCur);
                }
                else
                {
                    ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                    //Procedures.Cur=ProcCur;
                    addQuick(ProcCur);
                } 
            }
            else if (tArea == TreatmentArea.ToothRange)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    //Procedures.Cur=ProcCur;
                    addProcedure(ProcCur);
                }
                else
                {
                    ProcCur.ToothRange = "";
                    for (int b = 0;b < toothChart.getSelectedTeeth().Count;b++)
                    {
                        if (b != 0)
                            ProcCur.ToothRange += ",";
                         
                        ProcCur.ToothRange += toothChart.getSelectedTeeth()[b];
                    }
                    //Procedures.Cur=ProcCur;
                    addProcedure(ProcCur);
                } 
            }
            else //it's nice to see the procedure to verify the range
            if (tArea == TreatmentArea.Arch)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    //Procedures.Cur=ProcCur;
                    addProcedure(ProcCur);
                    continue;
                }
                 
                if (Tooth.IsMaxillary(toothChart.getSelectedTeeth()[0]))
                {
                    ProcCur.Surf = "U";
                }
                else
                {
                    ProcCur.Surf = "L";
                } 
                //Procedures.Cur=ProcCur;
                addQuick(ProcCur);
            }
            else if (tArea == TreatmentArea.Sextant)
            {
                //Procedures.Cur=ProcCur;
                addProcedure(ProcCur);
            }
            else
            {
                //mouth
                //Procedures.Cur=ProcCur;
                addQuick(ProcCur);
            }       
            procCodes.Add(ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode);
        }
        //for n
        //this was requiring too many irrelevant queries and going too slowly   //ModuleSelected(PatCur.PatNum);
        String teeth = "";
        if (newStatus == ProcStat.C)
        {
            for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
            {
                if (i > 0)
                {
                    teeth += ", ";
                }
                 
                teeth += toothChart.getSelectedTeeth()[i].ToString();
            }
        }
         
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
        clearButtons();
        fillProgNotes();
        if (newStatus == ProcStat.C)
        {
            String descript = "";
            if (procCodes.Count != 0)
            {
                //probably overkill
                descript = ProcedureCodes.GetProcCode(procCodes[0]).Descript;
            }
             
            if (!StringSupport.equals(teeth, ""))
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,descript + " created for the following teeth: " + teeth);
            }
            else
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,descript);
            } 
            AutomationL.Trigger(AutomationTrigger.CompleteProcedure, procCodes, PatCur.PatNum);
        }
         
    }

    private void listDx_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
    }

    //newDx=Defs.Defns[(int)DefCat.Diagnosis][listDx.IndexFromPoint(e.X,e.Y)].DefNum;
    private void gridProg_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Delete || e.KeyCode == Keys.Back)
        {
            deleteRows();
        }
         
    }

    private void deleteRows() throws Exception {
        if (gridProg.getSelectedIndices().Length == 0)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Delete Selected Item(s)?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        int skippedSecurity = 0;
        int skippedC = 0;
        int skippedComlog = 0;
        int skippedLabCases = 0;
        int skippedSheets = 0;
        DataRow row = new DataRow();
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            if (!StringSupport.equals(row["ProcNum"].ToString(), "0"))
            {
                if (PIn.Long(row["ProcStatus"].ToString()) == ((Enum)ProcStat.C).ordinal() || PIn.Bool(row["IsLocked"].ToString()))
                {
                    //takes care of locked group notes and invalidated (deleted and locked) procs
                    skippedC++;
                }
                else
                {
                    try
                    {
                        DateTime dateEntryC = DateTime.MinValue;
                        if (!StringSupport.equals(row["dateEntryC"].ToString(), ""))
                        {
                            dateEntryC = DateTime.Parse(row["dateEntryC"].ToString());
                        }
                         
                        if (!Security.isAuthorized(Permissions.ProcDelete,dateEntryC,true))
                        {
                            skippedSecurity++;
                            continue;
                        }
                         
                        Procedures.Delete(PIn.Long(row["ProcNum"].ToString()));
                        //also deletes the claimprocs
                        SecurityLogs.MakeLogEntry(Permissions.ProcDelete, PatCur.PatNum, row["ProcCode"].ToString() + ", " + PIn.Double(row["procFee"].ToString()).ToString("c"));
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                
                } 
            }
            else if (!StringSupport.equals(row["RxNum"].ToString(), "0"))
            {
                RxPats.Delete(PIn.Long(row["RxNum"].ToString()));
            }
            else if (!StringSupport.equals(row["CommlogNum"].ToString(), "0"))
            {
                skippedComlog++;
            }
            else if (!StringSupport.equals(row["LabCaseNum"].ToString(), "0"))
            {
                skippedLabCases++;
            }
            else if (!StringSupport.equals(row["SheetNum"].ToString(), "0"))
            {
                skippedSheets++;
            }
                 
        }
        Recalls.synch(PatCur.PatNum);
        if (skippedC > 0)
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete completed procedures from here.") + "\r" + skippedC.ToString() + " " + Lan.g(this,"item(s) skipped."));
        }
         
        if (skippedComlog > 0)
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete commlog entries from here.") + "\r" + skippedComlog.ToString() + " " + Lan.g(this,"item(s) skipped."));
        }
         
        if (skippedSecurity > 0)
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete procedures due to security.") + "\r" + skippedSecurity.ToString() + " " + Lan.g(this,"item(s) skipped."));
        }
         
        if (skippedLabCases > 0)
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete lab case entries from here.") + "\r" + skippedLabCases.ToString() + " " + Lan.g(this,"item(s) skipped."));
        }
         
        if (skippedSheets > 0)
        {
            MessageBox.Show(Lan.g(this,"Not allowed to delete sheets from here.") + "\r" + skippedSheets.ToString() + " " + Lan.g(this,"item(s) skipped."));
        }
         
        moduleSelected(PatCur.PatNum);
    }

    private void radioEntryEO_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        newStatus = ProcStat.EO;
    }

    private void radioEntryEC_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        newStatus = ProcStat.EC;
    }

    private void radioEntryTP_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        newStatus = ProcStat.TP;
    }

    private void radioEntryC_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        newStatus = ProcStat.C;
    }

    private void radioEntryR_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        newStatus = ProcStat.R;
    }

    private void radioEntryCn_CheckedChanged(Object sender, EventArgs e) throws Exception {
        newStatus = ProcStat.Cn;
    }

    private void listButtonCats_Click(Object sender, EventArgs e) throws Exception {
        fillProcButtons();
    }

    private void listViewButtons_Click(Object sender, EventArgs e) throws Exception {
        if (newStatus == ProcStat.C)
        {
            if (!Security.isAuthorized(Permissions.ProcComplCreate))
            {
                return ;
            }
             
        }
         
        if (listViewButtons.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        ProcButton ProcButtonCur = ProcButtonList[listViewButtons.SelectedIndices[0]];
        procButtonClicked(ProcButtonCur.ProcButtonNum,"");
    }

    /**
    * If quickbutton, then pass the code in and set procButtonNum to 0.
    */
    private void procButtonClicked(long procButtonNum, String quickcode) throws Exception {
        orionProvNum = 0;
        if (newStatus == ProcStat.C)
        {
            if (!Security.IsAuthorized(Permissions.ProcComplCreate, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        }
         
        Procedures.setDateFirstVisit(DateTimeOD.getToday(),1,PatCur);
        boolean isValid = new boolean();
        TreatmentArea tArea = TreatmentArea.None;
        int quadCount = 0;
        //automates quadrant codes.
        long[] codeList = new long[]();
        long[] autoCodeList = new long[]();
        if (procButtonNum == 0)
        {
            codeList = new long[1];
            codeList[0] = ProcedureCodes.getCodeNum(quickcode);
            autoCodeList = new long[0];
        }
        else
        {
            codeList = ProcButtonItems.getCodeNumListForButton(procButtonNum);
            autoCodeList = ProcButtonItems.getAutoListForButton(procButtonNum);
        } 
        //if(codeList.
        List<String> procCodes = new List<String>();
        Procedure ProcCur = null;
        //"Bug fix" for Dr. Lazar-------------
        boolean isPeriapicalSix = false;
        if (codeList.Length == 6)
        {
            //quick check before checking all codes. So that the program isn't slowed down too much.
            String tempVal = "";
            for (Object __dummyForeachVar2 : codeList)
            {
                long code = (Long)__dummyForeachVar2;
                tempVal += ProcedureCodes.getProcCode(code).AbbrDesc;
            }
            if (StringSupport.equals(tempVal, "PAPA+PA+PA+PA+PA+"))
            {
                isPeriapicalSix = true;
                toothChart.getSelectedTeeth().Clear();
            }
             
        }
         
        for (int i = 0;i < codeList.Length;i++)
        {
            for (int n = 0;n == 0 || n < toothChart.getSelectedTeeth().Count;n++)
            {
                //set tooth numbers later
                //needs to loop at least once, regardless of whether any teeth are selected.
                isValid = true;
                ProcCur = new Procedure();
                //insert, so no need to set CurOld
                ProcCur.CodeNum = ProcedureCodes.GetProcCode(codeList[i]).CodeNum;
                tArea = ProcedureCodes.getProcCode(ProcCur.CodeNum).TreatArea;
                //"Bug fix" for Dr. Lazar-------------
                if (isPeriapicalSix)
                {
                    //PA code is already set to treatment area mouth by default.
                    ProcCur.ToothNum = ",8,14,19,24,30".Split(',')[i];
                    //first code has tooth num "";
                    if (i == 0)
                    {
                        tArea = TreatmentArea.Mouth;
                    }
                    else
                    {
                        tArea = TreatmentArea.Tooth;
                    } 
                }
                 
                if ((tArea == TreatmentArea.Arch || tArea == TreatmentArea.Mouth || tArea == TreatmentArea.Quad || tArea == TreatmentArea.Sextant || tArea == TreatmentArea.ToothRange) && n > 0)
                {
                    continue;
                }
                else //the only two left are tooth and surf
                //only entered if n=0, so they don't get entered more than once.
                if (tArea == TreatmentArea.Quad)
                {
                    switch(quadCount % 4)
                    {
                        case 0: 
                            ProcCur.Surf = "UR";
                            break;
                        case 1: 
                            ProcCur.Surf = "UL";
                            break;
                        case 2: 
                            ProcCur.Surf = "LL";
                            break;
                        case 3: 
                            ProcCur.Surf = "LR";
                            break;
                    
                    }
                    quadCount++;
                    addQuick(ProcCur);
                }
                else if (tArea == TreatmentArea.Surf)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        isValid = false;
                    }
                    else
                    {
                        ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                    } 
                    if (StringSupport.equals(textSurf.Text, ""))
                    {
                        isValid = false;
                    }
                    else
                    {
                        ProcCur.Surf = Tooth.SurfTidyFromDisplayToDb(textSurf.Text, ProcCur.ToothNum);
                    } 
                    //it's ok if toothnum is not valid.
                    if (isValid)
                    {
                        addQuick(ProcCur);
                    }
                    else
                    {
                        addProcedure(ProcCur);
                    } 
                }
                else if (tArea == TreatmentArea.Tooth)
                {
                    if (isPeriapicalSix)
                    {
                        addQuick(ProcCur);
                    }
                    else if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        addProcedure(ProcCur);
                    }
                    else
                    {
                        ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                        addQuick(ProcCur);
                    }  
                }
                else if (tArea == TreatmentArea.ToothRange)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        addProcedure(ProcCur);
                    }
                    else
                    {
                        ProcCur.ToothRange = "";
                        for (int b = 0;b < toothChart.getSelectedTeeth().Count;b++)
                        {
                            if (b != 0)
                                ProcCur.ToothRange += ",";
                             
                            ProcCur.ToothRange += toothChart.getSelectedTeeth()[b];
                        }
                        addQuick(ProcCur);
                    } 
                }
                else if (tArea == TreatmentArea.Arch)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        addProcedure(ProcCur);
                        continue;
                    }
                     
                    if (Tooth.IsMaxillary(toothChart.getSelectedTeeth()[0]))
                    {
                        ProcCur.Surf = "U";
                    }
                    else
                    {
                        ProcCur.Surf = "L";
                    } 
                    addQuick(ProcCur);
                }
                else if (tArea == TreatmentArea.Sextant)
                {
                    addProcedure(ProcCur);
                }
                else
                {
                    //mouth
                    addQuick(ProcCur);
                }       
                procCodes.Add(ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode);
            }
        }
        //n selected teeth
        //end Part 1 checking for ProcCodes, now will check for AutoCodes
        String toothNum = new String();
        String surf = new String();
        boolean isAdditional = new boolean();
        for (int i = 0;i < autoCodeList.Length;i++)
        {
            for (int n = 0;n == 0 || n < toothChart.getSelectedTeeth().Count;n++)
            {
                //long orionProvNum=0;
                isValid = true;
                if (toothChart.getSelectedTeeth().Count != 0)
                    toothNum = toothChart.getSelectedTeeth()[n];
                else
                    toothNum = ""; 
                surf = textSurf.Text;
                isAdditional = n != 0;
                ProcCur = new Procedure();
                //this will be an insert, so no need to set CurOld
                ProcCur.CodeNum = AutoCodeItems.GetCodeNum(autoCodeList[i], toothNum, surf, isAdditional, PatCur.PatNum, PatCur.getAge());
                tArea = ProcedureCodes.getProcCode(ProcCur.CodeNum).TreatArea;
                if ((tArea == TreatmentArea.Arch || tArea == TreatmentArea.Mouth || tArea == TreatmentArea.Quad || tArea == TreatmentArea.Sextant || tArea == TreatmentArea.ToothRange) && n > 0)
                {
                    continue;
                }
                else //the only two left are tooth and surf
                //only entered if n=0, so they don't get entered more than once.
                if (tArea == TreatmentArea.Quad)
                {
                    switch(quadCount % 4)
                    {
                        case 0: 
                            ProcCur.Surf = "UR";
                            break;
                        case 1: 
                            ProcCur.Surf = "UL";
                            break;
                        case 2: 
                            ProcCur.Surf = "LL";
                            break;
                        case 3: 
                            ProcCur.Surf = "LR";
                            break;
                    
                    }
                    quadCount++;
                    addQuick(ProcCur);
                }
                else if (tArea == TreatmentArea.Surf)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        isValid = false;
                    }
                    else
                    {
                        ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                    } 
                    if (StringSupport.equals(textSurf.Text, ""))
                    {
                        isValid = false;
                    }
                    else
                    {
                        ProcCur.Surf = Tooth.SurfTidyFromDisplayToDb(textSurf.Text, ProcCur.ToothNum);
                    } 
                    //it's ok if toothnum is invalid
                    if (isValid)
                    {
                        addQuick(ProcCur);
                    }
                    else
                    {
                        addProcedure(ProcCur);
                    } 
                }
                else if (tArea == TreatmentArea.Tooth)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        addProcedure(ProcCur);
                    }
                    else
                    {
                        ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                        addQuick(ProcCur);
                    } 
                }
                else if (tArea == TreatmentArea.ToothRange)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        addProcedure(ProcCur);
                    }
                    else
                    {
                        ProcCur.ToothRange = "";
                        for (int b = 0;b < toothChart.getSelectedTeeth().Count;b++)
                        {
                            if (b != 0)
                                ProcCur.ToothRange += ",";
                             
                            ProcCur.ToothRange += toothChart.getSelectedTeeth()[b];
                        }
                        addQuick(ProcCur);
                    } 
                }
                else if (tArea == TreatmentArea.Arch)
                {
                    if (toothChart.getSelectedTeeth().Count == 0)
                    {
                        addProcedure(ProcCur);
                        continue;
                    }
                     
                    if (Tooth.IsMaxillary(toothChart.getSelectedTeeth()[0]))
                    {
                        ProcCur.Surf = "U";
                    }
                    else
                    {
                        ProcCur.Surf = "L";
                    } 
                    addQuick(ProcCur);
                }
                else if (tArea == TreatmentArea.Sextant)
                {
                    addProcedure(ProcCur);
                }
                else
                {
                    //mouth
                    addQuick(ProcCur);
                }       
                procCodes.Add(ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode);
            }
        }
        //n selected teeth
        //orionProvNum=ProcCur.ProvNum;
        //for i
        //this was requiring too many irrelevant queries and going too slowly   //ModuleSelected(PatCur.PatNum);
        String teeth = "";
        if (newStatus == ProcStat.C)
        {
            for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
            {
                if (i > 0)
                {
                    teeth += ", ";
                }
                 
                teeth += toothChart.getSelectedTeeth()[i].ToString();
            }
        }
         
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
        clearButtons();
        fillProgNotes();
        if (newStatus == ProcStat.C)
        {
            String descript = "";
            if (ProcCur != null)
            {
                //probably overkill
                descript = ProcedureCodes.getProcCode(ProcCur.CodeNum).Descript;
            }
             
            if (!StringSupport.equals(teeth, ""))
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,descript + " created for the following teeth: " + teeth);
            }
            else
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,descript);
            } 
            AutomationL.Trigger(AutomationTrigger.CompleteProcedure, procCodes, PatCur.PatNum);
        }
         
    }

    private void textProcCode_TextChanged(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textProcCode.Text, "d"))
        {
            textProcCode.Text = "D";
            textProcCode.SelectionStart = 1;
        }
         
    }

    private void textProcCode_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Return)
        {
            enterTypedCode();
        }
         
    }

    private void textProcCode_Enter(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textProcCode.Text, Lan.g(this,"Type Proc Code")))
        {
            textProcCode.Text = "";
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        enterTypedCode();
    }

    private void enterTypedCode() throws Exception {
        //orionProcNum=0;
        if (newStatus == ProcStat.C)
        {
            if (!Security.IsAuthorized(Permissions.ProcComplCreate, PIn.Date(textDate.Text)))
            {
                return ;
            }
             
        }
         
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && Regex.IsMatch(textProcCode.Text, "^\\d{4}$"))
        {
            //if exactly 4 digits
            if (!ProcedureCodeC.getHList().ContainsKey(textProcCode.Text))
            {
                //4 digit code is not found
                textProcCode.Text = "D" + textProcCode.Text;
            }
            else
            {
                //or if it's a 4 digit code that's hidden, also add the D
                ProcedureCode procCode = ProcedureCodes.GetProcCode(textProcCode.Text);
                if (DefC.getHidden(DefCat.ProcCodeCats,procCode.ProcCat))
                {
                    textProcCode.Text = "D" + textProcCode.Text;
                }
                 
            } 
        }
         
        if (!ProcedureCodeC.getHList().ContainsKey(textProcCode.Text))
        {
            MessageBox.Show(Lan.g(this,"Invalid code."));
            //textProcCode.Text="";
            textProcCode.SelectionStart = textProcCode.Text.Length;
            return ;
        }
         
        if (DefC.GetHidden(DefCat.ProcCodeCats, ProcedureCodes.GetProcCode(textProcCode.Text).ProcCat))
        {
            //if the category is hidden
            MessageBox.Show(Lan.g(this,"Code is in a hidden category and cannot be added from here."));
            textProcCode.SelectionStart = textProcCode.Text.Length;
            return ;
        }
         
        List<String> procCodes = new List<String>();
        Procedures.setDateFirstVisit(DateTimeOD.getToday(),1,PatCur);
        TreatmentArea tArea = TreatmentArea.None;
        Procedure ProcCur;
        for (int n = 0;n == 0 || n < toothChart.getSelectedTeeth().Count;n++)
        {
            //always loops at least once.
            ProcCur = new Procedure();
            //this will be an insert, so no need to set CurOld
            ProcCur.CodeNum = ProcedureCodes.GetCodeNum(textProcCode.Text);
            boolean isValid = true;
            tArea = ProcedureCodes.getProcCode(ProcCur.CodeNum).TreatArea;
            if ((tArea == TreatmentArea.Arch || tArea == TreatmentArea.Mouth || tArea == TreatmentArea.Quad || tArea == TreatmentArea.Sextant || tArea == TreatmentArea.ToothRange) && n > 0)
            {
                continue;
            }
            else //the only two left are tooth and surf
            //only entered if n=0, so they don't get entered more than once.
            if (tArea == TreatmentArea.Quad)
            {
                //This is optimized for single proc like a space maintainer.  User can select a tooth to set quadrant.
                if (toothChart.getSelectedTeeth().Count > 0)
                {
                    ProcCur.Surf = Tooth.GetQuadrant(toothChart.getSelectedTeeth()[0]);
                    addQuick(ProcCur);
                }
                else
                {
                    addProcedure(ProcCur);
                } 
            }
            else if (tArea == TreatmentArea.Surf)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    isValid = false;
                }
                else
                {
                    ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                } 
                if (StringSupport.equals(textSurf.Text, ""))
                {
                    isValid = false;
                }
                else
                {
                    ProcCur.Surf = Tooth.SurfTidyFromDisplayToDb(textSurf.Text, ProcCur.ToothNum);
                } 
                //it's ok if toothnum is invalid
                if (isValid)
                {
                    addQuick(ProcCur);
                }
                else
                {
                    addProcedure(ProcCur);
                } 
            }
            else if (tArea == TreatmentArea.Tooth)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    addProcedure(ProcCur);
                }
                else
                {
                    ProcCur.ToothNum = toothChart.getSelectedTeeth()[n];
                    addQuick(ProcCur);
                } 
            }
            else if (tArea == TreatmentArea.ToothRange)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    addProcedure(ProcCur);
                }
                else
                {
                    ProcCur.ToothRange = "";
                    for (int b = 0;b < toothChart.getSelectedTeeth().Count;b++)
                    {
                        if (b != 0)
                            ProcCur.ToothRange += ",";
                         
                        ProcCur.ToothRange += toothChart.getSelectedTeeth()[b];
                    }
                    addQuick(ProcCur);
                } 
            }
            else if (tArea == TreatmentArea.Arch)
            {
                if (toothChart.getSelectedTeeth().Count == 0)
                {
                    addProcedure(ProcCur);
                    continue;
                }
                 
                if (Tooth.IsMaxillary(toothChart.getSelectedTeeth()[0]))
                {
                    ProcCur.Surf = "U";
                }
                else
                {
                    ProcCur.Surf = "L";
                } 
                addQuick(ProcCur);
            }
            else if (tArea == TreatmentArea.Sextant)
            {
                addProcedure(ProcCur);
            }
            else
            {
                //mouth
                addQuick(ProcCur);
            }       
            procCodes.Add(ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode);
        }
        //n selected teeth
        //this was requiring too many irrelevant queries and going too slowly   //ModuleSelected(PatCur.PatNum);
        String teeth = "";
        if (newStatus == ProcStat.C)
        {
            for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
            {
                //string teeth = "";
                if (i > 0)
                {
                    teeth += ", ";
                }
                 
                teeth += toothChart.getSelectedTeeth()[i].ToString();
            }
        }
         
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
        clearButtons();
        fillProgNotes();
        textProcCode.Text = "";
        textProcCode.Select();
        if (newStatus == ProcStat.C)
        {
            String descript = "";
            if (procCodes.Count != 0)
            {
                //probably overkill
                descript = ProcedureCodes.GetProcCode(procCodes[0]).Descript;
            }
             
            if (!StringSupport.equals(teeth, ""))
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,descript + " created for the following teeth: " + teeth);
            }
            else
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,descript);
            } 
            AutomationL.Trigger(AutomationTrigger.CompleteProcedure, procCodes, PatCur.PatNum);
        }
         
    }

    private void butMissing_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Missing);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butNotMissing_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.ClearValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Missing);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butEdentulous_Click(Object sender, EventArgs e) throws Exception {
        ToothInitials.clearAllValuesForType(PatCur.PatNum,ToothInitialType.Missing);
        for (int i = 1;i <= 32;i++)
        {
            ToothInitials.SetValueQuick(PatCur.PatNum, i.ToString(), ToothInitialType.Missing, 0);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butHidden_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Hidden);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butUnhide_Click(Object sender, EventArgs e) throws Exception {
        if (listHidden.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select an item from the list first.");
            return ;
        }
         
        ToothInitials.clearValue(PatCur.PatNum,(String)HiddenTeeth[listHidden.SelectedIndex],ToothInitialType.Hidden);
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void fillMovementsAndHidden() throws Exception {
        if (tabProc.Height < 50)
        {
            return ;
        }
         
        //skip if the tab control is short(not visible){
        if (tabProc.SelectedIndex == 2)
        {
            //movements tab
            if (toothChart.getSelectedTeeth().Count == 0)
            {
                textShiftM.Text = "";
                textShiftO.Text = "";
                textShiftB.Text = "";
                textRotate.Text = "";
                textTipM.Text = "";
                textTipB.Text = "";
                return ;
            }
             
            textShiftM.Text = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[0], ToothInitialType.ShiftM).ToString();
            textShiftO.Text = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[0], ToothInitialType.ShiftO).ToString();
            textShiftB.Text = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[0], ToothInitialType.ShiftB).ToString();
            textRotate.Text = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[0], ToothInitialType.Rotate).ToString();
            textTipM.Text = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[0], ToothInitialType.TipM).ToString();
            textTipB.Text = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[0], ToothInitialType.TipB).ToString();
            //At this point, all 6 blanks have either a number or 0.
            //As we go through this loop, none of the values will change.
            //The only thing that will happen is that some of them will become blank.
            String move = new String();
            for (int i = 1;i < toothChart.getSelectedTeeth().Count;i++)
            {
                move = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftM).ToString();
                if (!StringSupport.equals(textShiftM.Text, move))
                {
                    textShiftM.Text = "";
                }
                 
                move = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftO).ToString();
                if (!StringSupport.equals(textShiftO.Text, move))
                {
                    textShiftO.Text = "";
                }
                 
                move = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftB).ToString();
                if (!StringSupport.equals(textShiftB.Text, move))
                {
                    textShiftB.Text = "";
                }
                 
                move = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[i], ToothInitialType.Rotate).ToString();
                if (!StringSupport.equals(textRotate.Text, move))
                {
                    textRotate.Text = "";
                }
                 
                move = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[i], ToothInitialType.TipM).ToString();
                if (!StringSupport.equals(textTipM.Text, move))
                {
                    textTipM.Text = "";
                }
                 
                move = ToothInitials.GetMovement(ToothInitialList, toothChart.getSelectedTeeth()[i], ToothInitialType.TipB).ToString();
                if (!StringSupport.equals(textTipB.Text, move))
                {
                    textTipB.Text = "";
                }
                 
            }
        }
         
        //if movements tab
        if (tabProc.SelectedIndex == 1)
        {
            //missing teeth
            listHidden.Items.Clear();
            HiddenTeeth = ToothInitials.getHiddenTeeth(ToothInitialList);
            for (int i = 0;i < HiddenTeeth.Count;i++)
            {
                listHidden.Items.Add(Tooth.toInternat((String)HiddenTeeth[i]));
            }
        }
         
    }

    private void butShiftMminus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftM, -2);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butShiftMplus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftM, 2);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butShiftOminus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftO, -2);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butShiftOplus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftO, 2);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butShiftBminus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftB, -2);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butShiftBplus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftB, 2);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butRotateMinus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Rotate, -20);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butRotatePlus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Rotate, 20);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butTipMminus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.TipM, -10);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butTipMplus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.TipM, 10);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butTipBminus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.TipB, -10);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butTipBplus_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.AddMovement(ToothInitialList, PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.TipB, 10);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butApplyMovements_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textShiftM.errorProvider1.GetError(textShiftM), "") || !StringSupport.equals(textShiftO.errorProvider1.GetError(textShiftO), "") || !StringSupport.equals(textShiftB.errorProvider1.GetError(textShiftB), "") || !StringSupport.equals(textRotate.errorProvider1.GetError(textRotate), "") || !StringSupport.equals(textTipM.errorProvider1.GetError(textTipM), "") || !StringSupport.equals(textTipB.errorProvider1.GetError(textTipB), ""))
        {
            MsgBox.show(this,"Please fix errors first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            if (!StringSupport.equals(textShiftM.Text, ""))
            {
                ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftM, PIn.Float(textShiftM.Text));
            }
             
            if (!StringSupport.equals(textShiftO.Text, ""))
            {
                ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftO, PIn.Float(textShiftO.Text));
            }
             
            if (!StringSupport.equals(textShiftB.Text, ""))
            {
                ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.ShiftB, PIn.Float(textShiftB.Text));
            }
             
            if (!StringSupport.equals(textRotate.Text, ""))
            {
                ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Rotate, PIn.Float(textRotate.Text));
            }
             
            if (!StringSupport.equals(textTipM.Text, ""))
            {
                ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.TipM, PIn.Float(textTipM.Text));
            }
             
            if (!StringSupport.equals(textTipB.Text, ""))
            {
                ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.TipB, PIn.Float(textTipB.Text));
            }
             
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(true);
    }

    private void butPrimary_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            ToothInitials.SetValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Primary);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butPerm_Click(Object sender, EventArgs e) throws Exception {
        if (toothChart.getSelectedTeeth().Count == 0)
        {
            MsgBox.show(this,"Please select teeth first.");
            return ;
        }
         
        for (int i = 0;i < toothChart.getSelectedTeeth().Count;i++)
        {
            if (Tooth.IsPrimary(toothChart.getSelectedTeeth()[i]))
            {
                ToothInitials.ClearValue(PatCur.PatNum, Tooth.PriToPerm(toothChart.getSelectedTeeth()[i]), ToothInitialType.Primary);
            }
            else
            {
                ToothInitials.ClearValue(PatCur.PatNum, toothChart.getSelectedTeeth()[i], ToothInitialType.Primary);
            } 
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butAllPrimary_Click(Object sender, EventArgs e) throws Exception {
        ToothInitials.clearAllValuesForType(PatCur.PatNum,ToothInitialType.Primary);
        for (int i = 1;i <= 32;i++)
        {
            ToothInitials.SetValueQuick(PatCur.PatNum, i.ToString(), ToothInitialType.Primary, 0);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butAllPerm_Click(Object sender, EventArgs e) throws Exception {
        ToothInitials.clearAllValuesForType(PatCur.PatNum,ToothInitialType.Primary);
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void butMixed_Click(Object sender, EventArgs e) throws Exception {
        ToothInitials.clearAllValuesForType(PatCur.PatNum,ToothInitialType.Primary);
        String[] priTeeth = new String[]{ "1", "2", "4", "5", "6", "11", "12", "13", "15", "16", "17", "18", "20", "21", "22", "27", "28", "29", "31", "32" };
        for (int i = 0;i < priTeeth.Length;i++)
        {
            ToothInitials.SetValueQuick(PatCur.PatNum, priTeeth[i], ToothInitialType.Primary, 0);
        }
        ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
        fillToothChart(false);
    }

    private void fillPlanned() throws Exception {
        if (PatCur == null)
        {
            checkDone.Checked = false;
            butNew.Enabled = false;
            butPin.Enabled = false;
            butClear.Enabled = false;
            butUp.Enabled = false;
            butDown.Enabled = false;
            gridPlanned.Enabled = false;
            return ;
        }
        else
        {
            butNew.Enabled = true;
            butPin.Enabled = true;
            butClear.Enabled = true;
            butUp.Enabled = true;
            butDown.Enabled = true;
            gridPlanned.Enabled = true;
        } 
        if (PatCur.PlannedIsDone)
        {
            checkDone.Checked = true;
        }
        else
        {
            checkDone.Checked = false;
        } 
        //Fill grid
        gridPlanned.beginUpdate();
        gridPlanned.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TablePlannedAppts","#"), 25, HorizontalAlignment.Center);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","Min"),35);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","Procedures"),175);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","Note"),175);
        gridPlanned.getColumns().add(col);
        if (Programs.getUsingOrion())
        {
            col = new ODGridColumn(Lan.g("TablePlannedAppts","SchedBy"),80);
        }
        else
        {
            col = new ODGridColumn(Lan.g("TablePlannedAppts","DateSched"),80);
        } 
        gridPlanned.getColumns().add(col);
        gridPlanned.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        DataTable table = DataSetMain.Tables["Planned"];
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //This gets done in the business layer:
            /*
            			bool iochanged=false;
            			for(int i=0;i<table.Rows.Count;i++) {
            				if(table.Rows[i]["ItemOrder"].ToString()!=i.ToString()) {
            					PlannedAppt planned=PlannedAppts.CreateObject(PIn.PLong(table.Rows[i]["PlannedApptNum"].ToString()));
            					planned.ItemOrder=i;
            					PlannedAppts.InsertOrUpdate(planned);
            					iochanged=true;
            				}
            			}
            			if(iochanged) {
            				DataSetMain=ChartModules.GetAll(PatCur.PatNum,checkAudit.Checked);
            				table=DataSetMain.Tables["Planned"];
            			}*/
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["ItemOrder"].ToString());
            row.getCells().Add(table.Rows[i]["minutes"].ToString());
            row.getCells().Add(table.Rows[i]["ProcDescript"].ToString());
            row.getCells().Add(table.Rows[i]["Note"].ToString());
            if (Programs.getUsingOrion())
            {
                String text = new String();
                List<Procedure> procsList = Procedures.refresh(PatCur.PatNum);
                DateTime newDateSched = new DateTime();
                for (int p = 0;p < procsList.Count;p++)
                {
                    if (procsList[p].PlannedAptNum == PIn.Long(table.Rows[i]["AptNum"].ToString()))
                    {
                        OrionProc op = OrionProcs.GetOneByProcNum(procsList[p].ProcNum);
                        if (op != null && op.DateScheduleBy.Year > 1880)
                        {
                            if (newDateSched.Year < 1880)
                            {
                                newDateSched = op.DateScheduleBy;
                            }
                            else
                            {
                                if (op.DateScheduleBy < newDateSched)
                                {
                                    newDateSched = op.DateScheduleBy;
                                }
                                 
                            } 
                        }
                         
                    }
                     
                }
                if (newDateSched.Year > 1880)
                {
                    text = newDateSched.ToShortDateString();
                }
                else
                {
                    text = "None";
                } 
                row.getCells().add(text);
            }
            else
            {
                row.getCells().Add(table.Rows[i]["dateSched"].ToString());
            } 
            row.setColorText(Color.FromArgb(PIn.Int(table.Rows[i]["colorText"].ToString())));
            row.setColorBackG(Color.FromArgb(PIn.Int(table.Rows[i]["colorBackG"].ToString())));
            gridPlanned.getRows().add(row);
        }
        gridPlanned.endUpdate();
    }

    private void butNew_Click(Object sender, System.EventArgs e) throws Exception {
        /*if(ApptPlanned.Visible){
        				if(MessageBox.Show(Lan.g(this,"Replace existing planned appointment?")
        					,"",MessageBoxButtons.OKCancel)!=DialogResult.OK)
        					return;
        				//Procedures.UnattachProcsInPlannedAppt(ApptPlanned.Info.MyApt.AptNum);
        				AppointmentL.Delete(PIn.PInt(ApptPlanned.DataRoww["AptNum"].ToString()));
        			}*/
        Appointment AptCur = new Appointment();
        AptCur.PatNum = PatCur.PatNum;
        AptCur.ProvNum = PatCur.PriProv;
        AptCur.ClinicNum = PatCur.ClinicNum;
        AptCur.AptStatus = ApptStatus.Planned;
        AptCur.AptDateTime = DateTimeOD.getToday();
        AptCur.Pattern = "/X/";
        AptCur.TimeLocked = PrefC.getBool(PrefName.AppointmentTimeIsLocked);
        Appointments.insert(AptCur);
        PlannedAppt plannedAppt = new PlannedAppt();
        plannedAppt.AptNum = AptCur.AptNum;
        plannedAppt.PatNum = PatCur.PatNum;
        plannedAppt.ItemOrder = DataSetMain.Tables["Planned"].Rows.Count + 1;
        PlannedAppts.insert(plannedAppt);
        FormApptEdit FormApptEdit2 = new FormApptEdit(AptCur.AptNum);
        FormApptEdit2.IsNew = true;
        FormApptEdit2.ShowDialog();
        if (FormApptEdit2.DialogResult != DialogResult.OK)
        {
            //delete new appt, delete plannedappt, and unattach procs already handled in dialog
            fillPlanned();
            return ;
        }
         
        List<Procedure> myProcList = Procedures.refresh(PatCur.PatNum);
        boolean allProcsHyg = true;
        for (int i = 0;i < myProcList.Count;i++)
        {
            if (myProcList[i].PlannedAptNum != AptCur.AptNum)
                continue;
             
            //only concerned with procs on this plannedAppt
            if (!ProcedureCodes.GetProcCode(myProcList[i].CodeNum).IsHygiene)
            {
                allProcsHyg = false;
                break;
            }
             
        }
        if (allProcsHyg && PatCur.SecProv != 0)
        {
            Appointment aptOld = AptCur.clone();
            AptCur.ProvNum = PatCur.SecProv;
            Appointments.update(AptCur,aptOld);
        }
         
        Patient patOld = PatCur.copy();
        //PatCur.NextAptNum=AptCur.AptNum;
        PatCur.PlannedIsDone = false;
        Patients.update(PatCur,patOld);
        moduleSelected(PatCur.PatNum);
    }

    //if procs were added in appt, then this will display them
    /**
    * 
    */
    private void butClear_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridPlanned.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item first");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Delete planned appointment(s)?"))
        {
            return ;
        }
         
        for (int i = 0;i < gridPlanned.getSelectedIndices().Length;i++)
        {
            Appointments.Delete(PIn.Long(DataSetMain.Tables["Planned"].Rows[gridPlanned.getSelectedIndices()[i]]["AptNum"].ToString()));
        }
        moduleSelected(PatCur.PatNum);
    }

    /**
    * 
    */
    private void butPin_Click(Object sender, EventArgs e) throws Exception {
        if (gridPlanned.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item first");
            return ;
        }
         
        List<long> aptNums = new List<long>();
        for (int i = 0;i < gridPlanned.getSelectedIndices().Length;i++)
        {
            aptNums.Add(PIn.Long(DataSetMain.Tables["Planned"].Rows[gridPlanned.getSelectedIndices()[i]]["AptNum"].ToString()));
        }
        GotoModule.PinToAppt(aptNums, 0);
    }

    private void butUp_Click(Object sender, EventArgs e) throws Exception {
        if (gridPlanned.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        if (gridPlanned.getSelectedIndices().Length > 1)
        {
            MsgBox.show(this,"Please only select one item first.");
            return ;
        }
         
        DataTable table = DataSetMain.Tables["Planned"];
        int idx = gridPlanned.getSelectedIndices()[0];
        if (idx == 0)
        {
            return ;
        }
         
        PlannedAppt planned;
        planned = PlannedAppts.GetOne(PIn.Long(table.Rows[idx]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx - 1;
        PlannedAppts.update(planned);
        planned = PlannedAppts.GetOne(PIn.Long(table.Rows[idx - 1]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx;
        PlannedAppts.update(planned);
        DataSetMain = ChartModules.GetAll(PatCur.PatNum, checkAudit.Checked);
        fillPlanned();
        gridPlanned.setSelected(idx - 1,true);
    }

    private void butDown_Click(Object sender, EventArgs e) throws Exception {
        if (gridPlanned.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        if (gridPlanned.getSelectedIndices().Length > 1)
        {
            MsgBox.show(this,"Please only select one item first.");
            return ;
        }
         
        DataTable table = DataSetMain.Tables["Planned"];
        int idx = gridPlanned.getSelectedIndices()[0];
        if (idx == table.Rows.Count - 1)
        {
            return ;
        }
         
        PlannedAppt planned;
        planned = PlannedAppts.GetOne(PIn.Long(table.Rows[idx]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx + 1;
        PlannedAppts.update(planned);
        planned = PlannedAppts.GetOne(PIn.Long(table.Rows[idx + 1]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx;
        PlannedAppts.update(planned);
        DataSetMain = ChartModules.GetAll(PatCur.PatNum, checkAudit.Checked);
        fillPlanned();
        gridPlanned.setSelected(idx + 1,true);
    }

    private void gridPlanned_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long aptnum = PIn.Long(DataSetMain.Tables["Planned"].Rows[e.getRow()]["AptNum"].ToString());
        FormApptEdit FormAE = new FormApptEdit(aptnum);
        FormAE.ShowDialog();
        if (Programs.getUsingOrion())
        {
            if (FormAE.DialogResult == DialogResult.OK)
            {
                moduleSelected(PatCur.PatNum);
            }
             
        }
        else
        {
            //if procs were added in appt, then this will display them*/
            moduleSelected(PatCur.PatNum);
        } 
        for (int i = 0;i < DataSetMain.Tables["Planned"].Rows.Count;i++)
        {
            //if procs were added in appt, then this will display them*/
            if (DataSetMain.Tables["Planned"].Rows[i]["AptNum"].ToString() == aptnum.ToString())
            {
                gridPlanned.setSelected(i,true);
            }
             
        }
    }

    private void checkDone_Click(Object sender, System.EventArgs e) throws Exception {
        Patient oldPat = PatCur.copy();
        if (checkDone.Checked)
        {
            if (DataSetMain.Tables["Planned"].Rows.Count > 0)
            {
                if (!MsgBox.show(this,true,"ALL planned appointment(s) will be deleted. Continue?"))
                {
                    checkDone.Checked = false;
                    return ;
                }
                 
                for (int i = 0;i < DataSetMain.Tables["Planned"].Rows.Count;i++)
                {
                    Appointments.Delete(PIn.Long(DataSetMain.Tables["Planned"].Rows[i]["AptNum"].ToString()));
                }
            }
             
            PatCur.PlannedIsDone = true;
            Patients.update(PatCur,oldPat);
        }
        else
        {
            PatCur.PlannedIsDone = false;
            Patients.update(PatCur,oldPat);
        } 
        moduleSelected(PatCur.PatNum);
    }

    private void button1_Click(Object sender, System.EventArgs e) throws Exception {
    }

    //sometimes used for testing purposes
    private void checkShowTP_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkShowC_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkShowE_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkShowR_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkShowCn_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkNotes_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkAppt_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkComm_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkCommFamily_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkLabCase_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkRx_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkTasks_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkEmail_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkSheets_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void checkShowTeeth_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        if (checkShowTeeth.Checked)
        {
            checkShowTP.Checked = true;
            checkShowC.Checked = true;
            checkShowE.Checked = true;
            checkShowR.Checked = true;
            checkShowCn.Checked = true;
            checkNotes.Checked = true;
            checkAppt.Checked = false;
            checkComm.Checked = false;
            checkCommFamily.Checked = false;
            checkLabCase.Checked = false;
            checkRx.Checked = false;
            checkEmail.Checked = false;
            checkTasks.Checked = false;
            checkSheets.Checked = false;
        }
        else
        {
            checkShowTP.Checked = true;
            checkShowC.Checked = true;
            checkShowE.Checked = true;
            checkShowR.Checked = true;
            checkShowCn.Checked = true;
            checkNotes.Checked = true;
            checkAppt.Checked = true;
            checkComm.Checked = true;
            checkCommFamily.Checked = true;
            checkLabCase.Checked = true;
            checkRx.Checked = true;
            checkEmail.Checked = true;
            checkTasks.Checked = true;
            checkSheets.Checked = true;
        } 
        fillProgNotes(true);
    }

    private void checkAudit_Click(Object sender, EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void butShowAll_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        checkShowTP.Checked = true;
        checkShowC.Checked = true;
        checkShowE.Checked = true;
        checkShowR.Checked = true;
        checkShowCn.Checked = true;
        checkNotes.Checked = true;
        checkAppt.Checked = true;
        checkComm.Checked = true;
        checkCommFamily.Checked = true;
        checkLabCase.Checked = true;
        checkRx.Checked = true;
        checkShowTeeth.Checked = false;
        checkTasks.Checked = true;
        checkEmail.Checked = true;
        checkSheets.Checked = true;
        fillProgNotes();
    }

    private void butShowNone_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        checkShowTP.Checked = false;
        checkShowC.Checked = false;
        checkShowE.Checked = false;
        checkShowR.Checked = false;
        checkShowCn.Checked = false;
        checkNotes.Checked = false;
        checkAppt.Checked = false;
        checkComm.Checked = false;
        checkCommFamily.Checked = false;
        checkLabCase.Checked = false;
        checkRx.Checked = false;
        checkShowTeeth.Checked = false;
        checkTasks.Checked = false;
        checkEmail.Checked = false;
        checkSheets.Checked = false;
        fillProgNotes();
    }

    private void radioPointer_Click(Object sender, EventArgs e) throws Exception {
        toothChart.setCursorTool(SparksToothChart.CursorTool.Pointer);
    }

    private void radioPen_Click(Object sender, EventArgs e) throws Exception {
        toothChart.setCursorTool(SparksToothChart.CursorTool.Pen);
    }

    private void radioEraser_Click(Object sender, EventArgs e) throws Exception {
        toothChart.setCursorTool(SparksToothChart.CursorTool.Eraser);
    }

    private void radioColorChanger_Click(Object sender, EventArgs e) throws Exception {
        toothChart.setCursorTool(SparksToothChart.CursorTool.ColorChanger);
    }

    private void panelDrawColor_DoubleClick(Object sender, EventArgs e) throws Exception {
    }

    //do nothing
    private void toothChart_SegmentDrawn(Object sender, ToothChartDrawEventArgs e) throws Exception {
        if (radioPen.Checked)
        {
            ToothInitial ti = new ToothInitial();
            ti.DrawingSegment = e.getDrawingSegement();
            ti.InitialType = ToothInitialType.Drawing;
            ti.PatNum = PatCur.PatNum;
            ti.ColorDraw = panelDrawColor.BackColor;
            ToothInitials.insert(ti);
            ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
            fillToothChart(true);
        }
        else if (radioEraser.Checked)
        {
            for (int i = ToothInitialList.Count - 1;i >= 0;i--)
            {
                //for(int i=0;i<ToothInitialList.Count;i++){
                //go backwards
                if (ToothInitialList[i].InitialType != ToothInitialType.Drawing)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(ToothInitialList[i].DrawingSegment, e.getDrawingSegement()))
                {
                    continue;
                }
                 
                ToothInitials.Delete(ToothInitialList[i]);
                ToothInitialList.RemoveAt(i);
            }
        }
        else //no need to refresh since that's handled by the toothchart.
        if (radioColorChanger.Checked)
        {
            for (int i = 0;i < ToothInitialList.Count;i++)
            {
                if (ToothInitialList[i].InitialType != ToothInitialType.Drawing)
                {
                    continue;
                }
                 
                if (!StringSupport.equals(ToothInitialList[i].DrawingSegment, e.getDrawingSegement()))
                {
                    continue;
                }
                 
                ToothInitialList[i].ColorDraw = panelDrawColor.BackColor;
                ToothInitials.Update(ToothInitialList[i]);
                fillToothChart(true);
            }
        }
           
    }

    private void panelTPdark_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelTPdark.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelTPlight_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelTPlight.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelCdark_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelCdark.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelClight_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelClight.BackColor;
    }

    private void panelECdark_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelECdark.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelEClight_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelEClight.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelEOdark_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelEOdark.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelEOlight_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelEOlight.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelRdark_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelRdark.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelRlight_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = panelRlight.BackColor;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void panelBlack_Click(Object sender, EventArgs e) throws Exception {
        panelDrawColor.BackColor = Color.Black;
        toothChart.setColorDrawing(Color.Black);
    }

    private void butColorOther_Click(Object sender, EventArgs e) throws Exception {
        ColorDialog cd = new ColorDialog();
        cd.Color = butColorOther.BackColor;
        if (cd.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        panelDrawColor.BackColor = cd.Color;
        toothChart.setColorDrawing(panelDrawColor.BackColor);
    }

    private void listProcStatusCodes_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (gridChartViews.getRows().Count > 0)
        {
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillProgNotes();
    }

    private void gridPtInfo_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (Plugins.hookMethod(this,"ContrChart.gridPtInfo_CellDoubleClick",PatCur,FamCur,e,PatientNoteCur))
        {
            return ;
        }
         
        if (TerminalActives.patIsInUse(PatCur.PatNum))
        {
            MsgBox.show(this,"Patient is currently entering info at a reception terminal.  Please try again later.");
            return ;
        }
         
        if (gridPtInfo.getRows().get___idx(e.getRow()).getTag() != null)
        {
            if (StringSupport.equals(gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString(), "med"))
            {
                FormMedical FormM = new FormMedical(PatientNoteCur,PatCur);
                FormM.ShowDialog();
                moduleSelected(PatCur.PatNum);
                return ;
            }
             
            if (gridPtInfo.getRows().get___idx(e.getRow()).getTag().GetType() == RegistrationKey.class)
            {
                FormRegistrationKeyEdit FormR = new FormRegistrationKeyEdit();
                FormR.RegKey = (RegistrationKey)gridPtInfo.getRows().get___idx(e.getRow()).getTag();
                FormR.ShowDialog();
                fillPtInfo();
                return ;
            }
             
            if (StringSupport.equals(gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString(), "EhrProvKeys"))
            {
                FormEhrProvKeysCustomer FormPK = new FormEhrProvKeysCustomer();
                FormPK.Guarantor = PatCur.Guarantor;
                FormPK.ShowDialog();
                moduleSelected(PatCur.PatNum);
                return ;
            }
             
            if (StringSupport.equals(gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString(), "Referral"))
            {
                //RefAttach refattach=(RefAttach)gridPat.Rows[e.Row].Tag;
                FormReferralsPatient FormRE = new FormReferralsPatient();
                FormRE.PatNum = PatCur.PatNum;
                FormRE.ShowDialog();
                moduleSelected(PatCur.PatNum);
                return ;
            }
             
            if (StringSupport.equals(gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString(), "References"))
            {
                FormReference FormR = new FormReference();
                FormR.ShowDialog();
                if (FormR.GotoPatNum != 0)
                {
                    Patient pat = Patients.getPat(FormR.GotoPatNum);
                    onPatientSelected(pat);
                    GotoModule.gotoFamily(FormR.GotoPatNum);
                    return ;
                }
                 
                if (FormR.DialogResult != DialogResult.OK)
                {
                    return ;
                }
                 
                for (int i = 0;i < FormR.SelectedCustRefs.Count;i++)
                {
                    CustRefEntry custEntry = new CustRefEntry();
                    custEntry.DateEntry = DateTime.Now;
                    custEntry.PatNumCust = PatCur.PatNum;
                    custEntry.PatNumRef = FormR.SelectedCustRefs[i].PatNum;
                    CustRefEntries.insert(custEntry);
                }
                fillPtInfo();
                return ;
            }
             
            if (StringSupport.equals(gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString(), "Patient Portal"))
            {
                FormPatientPortal FormPP = new FormPatientPortal();
                FormPP.PatCur = PatCur;
                FormPP.ShowDialog();
                if (FormPP.DialogResult == DialogResult.OK)
                {
                    fillPtInfo();
                }
                 
                return ;
            }
             
            if (StringSupport.equals(gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString(), "Payor Types"))
            {
                FormPayorTypes FormPT = new FormPayorTypes();
                FormPT.PatCur = PatCur;
                FormPT.ShowDialog();
                if (FormPT.DialogResult == DialogResult.OK)
                {
                    fillPtInfo();
                }
                 
                return ;
            }
             
            if (gridPtInfo.getRows().get___idx(e.getRow()).getTag().GetType() == CustRefEntry.class)
            {
                FormReferenceEntryEdit FormRE = new FormReferenceEntryEdit((CustRefEntry)gridPtInfo.getRows().get___idx(e.getRow()).getTag());
                FormRE.ShowDialog();
                fillPtInfo();
                return ;
            }
            else
            {
                //patfield
                String tag = gridPtInfo.getRows().get___idx(e.getRow()).getTag().ToString();
                tag = tag.Substring(8);
                //strips off all but the number: PatField1
                int index = PIn.Int(tag);
                PatField field = PatFields.GetByName(PatFieldDefs.getList()[index].FieldName, PatFieldList);
                if (field == null)
                {
                    field = new PatField();
                    field.PatNum = PatCur.PatNum;
                    field.FieldName = PatFieldDefs.getList()[index].FieldName;
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Text)
                    {
                        FormPatFieldEdit FormPF = new FormPatFieldEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.PickList)
                    {
                        FormPatFieldPickEdit FormPF = new FormPatFieldPickEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Date)
                    {
                        FormPatFieldDateEdit FormPF = new FormPatFieldDateEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Checkbox)
                    {
                        FormPatFieldCheckEdit FormPF = new FormPatFieldCheckEdit(field);
                        FormPF.IsNew = true;
                        FormPF.ShowDialog();
                    }
                     
                }
                else
                {
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Text)
                    {
                        FormPatFieldEdit FormPF = new FormPatFieldEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.PickList)
                    {
                        FormPatFieldPickEdit FormPF = new FormPatFieldPickEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Date)
                    {
                        FormPatFieldDateEdit FormPF = new FormPatFieldDateEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                    if (PatFieldDefs.getList()[index].FieldType == PatFieldType.Checkbox)
                    {
                        FormPatFieldCheckEdit FormPF = new FormPatFieldCheckEdit(field);
                        FormPF.ShowDialog();
                    }
                     
                } 
            } 
        }
        else
        {
            String email = PatCur.Email;
            long siteNum = PatCur.SiteNum;
            FormPatientEdit FormP = new FormPatientEdit(PatCur,FamCur);
            FormP.IsNew = false;
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.OK)
            {
                onPatientSelected(PatCur);
            }
             
        } 
        moduleSelected(PatCur.PatNum);
    }

    private void toothChart_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "";
        //if(toothChart.SelectedTeeth.Length==1) {
        //butO.BackColor=SystemColors.Control;
        //butB.BackColor=SystemColors.Control;
        //butF.BackColor=SystemColors.Control;
        //if(Tooth.IsAnterior(toothChart.SelectedTeeth[0])) {
        //butB.Text="";
        //butO.Text="";
        //butB.Enabled=false;
        //butO.Enabled=false;
        //butF.Text="F";
        //butI.Text="I";
        //butF.Enabled=true;
        //butI.Enabled=true;
        //}
        //else {
        //butB.Text="B";
        //butO.Text="O";
        //butB.Enabled=true;
        //butO.Enabled=true;
        //butF.Text="";
        //butI.Text="";
        //butF.Enabled=false;
        //butI.Enabled=false;
        //}
        //}
        if (checkShowTeeth.Checked)
        {
            fillProgNotes();
        }
         
        fillMovementsAndHidden();
    }

    private void gridProg_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Right)
        {
            if (PrefC.getBool(PrefName.EasyHideHospitals))
            {
                menuItemPrintDay.Visible = false;
            }
            else
            {
                menuItemPrintDay.Visible = true;
            } 
            Plugins.hookAddCode(this,"ContrChart.gridProg_MouseUp_end",menuProgRight,gridProg,PatCur);
            menuProgRight.Show(gridProg, new Point(e.X, e.Y));
        }
         
    }

    private void menuItemPrintProg_Click(Object sender, System.EventArgs e) throws Exception {
        pagesPrinted = 0;
        headingPrinted = false;
        printReport(false);
    }

    private void menuItemPrintDay_Click(Object sender, EventArgs e) throws Exception {
        if (gridProg.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one item first.");
            return ;
        }
         
        DataRow row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[0]].Tag;
        //hospitalDate=PIn.DateT(row["ProcDate"].ToString());
        //Store the state of all checkboxes in temporary variables
        boolean showRx = this.checkRx.Checked;
        boolean showComm = this.checkComm.Checked;
        boolean showApt = this.checkAppt.Checked;
        boolean showEmail = this.checkEmail.Checked;
        boolean showTask = this.checkTasks.Checked;
        boolean showLab = this.checkLabCase.Checked;
        boolean showSheets = this.checkSheets.Checked;
        boolean showTeeth = this.checkShowTeeth.Checked;
        boolean showAudit = this.checkAudit.Checked;
        DateTime showDateStart = ShowDateStart;
        DateTime showDateEnd = ShowDateEnd;
        boolean showTP = this.checkShowTP.Checked;
        boolean showComplete = this.checkShowC.Checked;
        boolean showExist = this.checkShowE.Checked;
        boolean showRefer = this.checkShowR.Checked;
        boolean showCond = this.checkShowCn.Checked;
        boolean showProcNote = this.checkNotes.Checked;
        boolean customView = this.chartCustViewChanged;
        //Set the checkboxes to desired values for print out
        checkRx.Checked = false;
        checkComm.Checked = false;
        checkAppt.Checked = false;
        checkEmail.Checked = false;
        checkTasks.Checked = false;
        checkLabCase.Checked = false;
        checkSheets.Checked = false;
        checkShowTeeth.Checked = false;
        checkAudit.Checked = false;
        ShowDateStart = PIn.DateT(row["ProcDate"].ToString());
        ShowDateEnd = PIn.DateT(row["ProcDate"].ToString());
        checkShowTP.Checked = false;
        checkShowC.Checked = true;
        checkShowE.Checked = false;
        checkShowR.Checked = false;
        checkShowCn.Checked = false;
        checkNotes.Checked = true;
        chartCustViewChanged = true;
        //custom view will not reset the check boxes so we force it true.
        //Fill progress notes with only desired rows to be printed, then print.
        fillProgNotes();
        if (gridProg.getRows().Count == 0)
        {
            MsgBox.show(this,"No completed procedures or notes to print");
        }
        else
        {
            try
            {
                pagesPrinted = 0;
                headingPrinted = false;
                printDay(false);
            }
            catch (Exception __dummyCatchVar2)
            {
            }
        
        } 
        //Set Date values and checkboxes back to original values, then refill progress notes.
        //hospitalDate=DateTime.MinValue;
        checkRx.Checked = showRx;
        checkComm.Checked = showComm;
        checkAppt.Checked = showApt;
        checkEmail.Checked = showEmail;
        checkTasks.Checked = showTask;
        checkLabCase.Checked = showLab;
        checkSheets.Checked = showSheets;
        checkShowTeeth.Checked = showTeeth;
        checkAudit.Checked = showAudit;
        ShowDateStart = showDateStart;
        ShowDateEnd = showDateEnd;
        checkShowTP.Checked = showTP;
        checkShowC.Checked = showComplete;
        checkShowE.Checked = showExist;
        checkShowR.Checked = showRefer;
        checkShowCn.Checked = showCond;
        checkNotes.Checked = showProcNote;
        chartCustViewChanged = customView;
        fillProgNotes();
    }

    private void menuItemDelete_Click(Object sender, EventArgs e) throws Exception {
        deleteRows();
    }

    private void menuItemSetComplete_Click(Object sender, EventArgs e) throws Exception {
        //moved down so we can have the date first
        //if(!Security.IsAuthorized(Permissions.ProcComplCreate)) {
        //	return;
        //}
        if (gridProg.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select an item first.");
            return ;
        }
         
        if (checkAudit.Checked)
        {
            MsgBox.show(this,"Not allowed in audit mode.");
            return ;
        }
         
        DataRow row = new DataRow();
        Appointment apt;
        //One appointment-------------------------------------------------------------------------------------------------------------
        if (gridProg.getSelectedIndices().Length == 1 && !StringSupport.equals(((DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[0]].Tag)["AptNum"].ToString(), "0"))
        {
            if (!Security.isAuthorized(Permissions.AppointmentEdit))
            {
                return ;
            }
             
            apt = Appointments.GetOneApt(PIn.Long(((DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[0]].Tag)["AptNum"].ToString()));
            if (apt.AptStatus == ApptStatus.Complete)
            {
                MsgBox.show(this,"Already complete.");
                return ;
            }
             
            if (apt.AptStatus == ApptStatus.PtNote || apt.AptStatus == ApptStatus.PtNoteCompleted || apt.AptStatus == ApptStatus.Planned || apt.AptStatus == ApptStatus.UnschedList)
            {
                MsgBox.show(this,"Not allowed for that status.");
                return ;
            }
             
            if (!Security.isAuthorized(Permissions.ProcComplCreate,apt.AptDateTime))
            {
                return ;
            }
             
            if (apt.AptDateTime.Date > DateTime.Today)
            {
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Appointment is in the future.  Set complete anyway?"))
                {
                    return ;
                }
                 
            }
            else if (!MsgBox.show(this,true,"Set appointment complete?"))
            {
                return ;
            }
              
            InsSub sub1 = InsSubs.getSub(PatPlans.getInsSubNum(PatPlanList,PatPlans.getOrdinal(PriSecMed.Primary,PatPlanList,PlanList,SubList)),SubList);
            InsSub sub2 = InsSubs.getSub(PatPlans.getInsSubNum(PatPlanList,PatPlans.getOrdinal(PriSecMed.Secondary,PatPlanList,PlanList,SubList)),SubList);
            Appointments.setAptStatusComplete(apt.AptNum,sub1.PlanNum,sub2.PlanNum);
            ProcedureL.setCompleteInAppt(apt,PlanList,PatPlanList,PatCur.SiteNum,PatCur.getAge(),SubList);
            //loops through each proc
            SecurityLogs.makeLogEntry(Permissions.AppointmentEdit,apt.PatNum,apt.ProcDescript + ", " + apt.AptDateTime.ToString() + ", Set Complete",apt.AptNum);
            Recalls.synch(PatCur.PatNum);
            Recalls.synchScheduledApptFull(PatCur.PatNum);
            moduleSelected(PatCur.PatNum);
            return ;
        }
         
        //Multiple procedures------------------------------------------------------------------------------------------------------
        if (!PrefC.getBool(PrefName.AllowSettingProcsComplete))
        {
            MsgBox.show(this,"Only single appointments may be set complete.  If you want to be able to set procedures complete, you must turn on that option in Setup Modules.");
            return ;
        }
         
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            //check to make sure we don't have non-procedures
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            if (StringSupport.equals(row["ProcNum"].ToString(), "0") || StringSupport.equals(row["ProcCode"].ToString(), "~GRP~"))
            {
                MsgBox.show(this,"Only procedures or single appointments may be set complete.");
                return ;
            }
             
        }
        Procedure procCur;
        Procedure procOld;
        ProcedureCode procCode;
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            //this loop is just for security:
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            procCur = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), true);
            if (procCur.ProcStatus == ProcStat.C)
            {
                continue;
            }
             
            //because it will be skipped below anyway
            if (procCur.AptNum != 0)
            {
                //if attached to an appointment
                apt = Appointments.getOneApt(procCur.AptNum);
                if (apt.AptDateTime.Date > MiscData.getNowDateTime().Date)
                {
                    MessageBox.Show(Lan.g(this,"Not allowed because a procedure is attached to a future appointment with a date of ") + apt.AptDateTime.ToShortDateString());
                    return ;
                }
                 
                if (!Security.isAuthorized(Permissions.ProcComplCreate,apt.AptDateTime))
                {
                    return ;
                }
                 
            }
            else
            {
                if (!Security.IsAuthorized(Permissions.ProcComplCreate, PIn.Date(textDate.Text)))
                {
                    return ;
                }
                 
            } 
        }
        List<String> procCodeList = new List<String>();
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            //for automation
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            apt = null;
            procCur = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), true);
            if (procCur.ProcStatus == ProcStat.C)
            {
                continue;
            }
             
            //don't allow setting a procedure complete again.  Important for security reasons.
            procOld = procCur.copy();
            procCode = ProcedureCodes.getProcCode(procCur.CodeNum);
            procCodeList.Add(ProcedureCodes.getStringProcCode(procCur.CodeNum));
            if (procOld.ProcStatus != ProcStat.C)
            {
                //if procedure was already complete, then don't add more notes.
                procCur.Note += ProcCodeNotes.getNote(procCur.ProvNum,procCur.CodeNum);
            }
             
            //note wasn't complete, so add notes
            procCur.DateEntryC = DateTime.Now;
            if (procCur.AptNum != 0)
            {
                //if attached to an appointment
                apt = Appointments.getOneApt(procCur.AptNum);
                procCur.ProcDate = apt.AptDateTime;
                procCur.ClinicNum = apt.ClinicNum;
                procCur.PlaceService = Clinics.getPlaceService(apt.ClinicNum);
            }
            else
            {
                procCur.ProcDate = PIn.Date(textDate.Text);
                procCur.PlaceService = (PlaceOfService)PrefC.getLong(PrefName.DefaultProcedurePlaceService);
            } 
            procCur.SiteNum = PatCur.SiteNum;
            Procedures.setDateFirstVisit(procCur.ProcDate,2,PatCur);
            if (procCode.PaintType == ToothPaintingType.Extraction)
            {
                //if an extraction, then mark previous procs hidden
                //Procedures.SetHideGraphical(procCur);//might not matter anymore
                ToothInitials.setValue(PatCur.PatNum,procCur.ToothNum,ToothInitialType.Missing);
            }
             
            procCur.ProcStatus = ProcStat.C;
            if (StringSupport.equals(procCur.DiagnosticCode, ""))
            {
                procCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
            }
             
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canada
                Procedures.setCanadianLabFeesCompleteForProc(procCur);
            }
             
            Plugins.hookAddCode(this,"ContrChart.menuItemSetComplete_Click_procLoop",procCur,procOld);
            Procedures.update(procCur,procOld);
            //Tried to move it to the business layer, but too complex for now.
            //Procedures.SetComplete(
            //	((Procedure)gridProg.Rows[gridProg.SelectedIndices[i]].Tag).ProcNum,PIn.PDate(textDate.Text));
            Procedures.ComputeEstimates(procCur, procCur.PatNum, ClaimProcList, false, PlanList, PatPlanList, BenefitList, PatCur.getAge(), SubList);
            //Auto insert encounter. Have to run this every time because entries might not all be on the same date or provider.
            if (procOld.ProcStatus != ProcStat.C && procCur.ProcStatus == ProcStat.C)
            {
                Encounters.insertDefaultEncounter(procCur.PatNum,procCur.ProvNum,procCur.ProcDate);
            }
             
        }
        AutomationL.Trigger(AutomationTrigger.CompleteProcedure, procCodeList, PatCur.PatNum);
        Recalls.synch(PatCur.PatNum);
        //if(skipped>0){
        //	MessageBox.Show(Lan.g(this,".")+"\r\n"
        //		+skipped.ToString()+" "+Lan.g(this,"procedures(s) skipped."));
        //}
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemEditSelected_Click(Object sender, EventArgs e) throws Exception {
        if (gridProg.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select procedures first.");
            return ;
        }
         
        DataRow row = new DataRow();
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            if (StringSupport.equals(row["ProcNum"].ToString(), "0"))
            {
                MsgBox.show(this,"Only procedures may be edited.");
                return ;
            }
             
        }
        List<Procedure> proclist = new List<Procedure>();
        Procedure proc;
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            proc = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), false);
            proclist.Add(proc);
        }
        FormProcEditAll FormP = new FormProcEditAll();
        FormP.ProcList = proclist;
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.OK)
        {
            moduleSelected(PatCur.PatNum);
        }
         
    }

    private void menuItemGroupSelected_Click(Object sender, EventArgs e) throws Exception {
        DataRow row = new DataRow();
        if (gridProg.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select procedures to attach a group note to.");
            return ;
        }
         
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            if (StringSupport.equals(row["ProcNum"].ToString(), "0"))
            {
                //This is not a procedure.
                MsgBox.show(this,"You may only attach a group note to procedures.");
                return ;
            }
             
        }
        List<Procedure> proclist = new List<Procedure>();
        Procedure proc;
        for (int i = 0;i < gridProg.getSelectedIndices().Length;i++)
        {
            //Create proclist from selected items.
            row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[i]].Tag;
            proc = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), true);
            proclist.Add(proc);
        }
        //Validate the list of procedures------------------------------------------------------------------------------------
        DateTime procDate = proclist[0].ProcDate;
        long clinicNum = proclist[0].ClinicNum;
        long provNum = proclist[0].ProvNum;
        for (int i = 0;i < proclist.Count;i++)
        {
            //starts at 0 to check procStatus
            if (StringSupport.equals(ProcedureCodes.GetStringProcCode(proclist[i].CodeNum), ProcedureCodes.GroupProcCode))
            {
                MsgBox.show(this,"You cannot attach a group note to another group note.");
                return ;
            }
             
            if (proclist[i].IsLocked)
            {
                MsgBox.show(this,"Procedures cannot be locked.");
                return ;
            }
             
            if (proclist[i].ProcDate != procDate)
            {
                MsgBox.show(this,"Procedures must have the same date to attach a group note.");
                return ;
            }
             
            if (proclist[i].ProcStatus != ProcStat.C)
            {
                MsgBox.show(this,"Procedures must be complete to attach a group note.");
                return ;
            }
             
            if (proclist[i].ClinicNum != clinicNum)
            {
                MsgBox.show(this,"Procedures must have the same clinic to attach a group note.");
                return ;
            }
             
            if (proclist[i].ProvNum != provNum)
            {
                MsgBox.show(this,"Procedures must have the same provider to attach a group note.");
                return ;
            }
             
        }
        //Procedures are valid. Create new Procedure "group" and ProcGroupItems-------------------------------------------------------
        Procedure group = new Procedure();
        group.PatNum = PatCur.PatNum;
        group.ProcStatus = ProcStat.EC;
        group.DateEntryC = DateTime.Now;
        group.ProcDate = procDate;
        group.ProvNum = provNum;
        group.ClinicNum = clinicNum;
        group.CodeNum = ProcedureCodes.getCodeNum(ProcedureCodes.GroupProcCode);
        if (PrefC.getBool(PrefName.ProcGroupNoteDoesAggregate))
        {
            String aggNote = "";
            for (int i = 0;i < proclist.Count;i++)
            {
                if (i > 0 && !StringSupport.equals(proclist[i - 1].Note, ""))
                {
                    aggNote += "\r\n";
                }
                 
                aggNote += proclist[i].Note;
            }
            group.Note = aggNote;
        }
        else
        {
            group.Note = ProcCodeNotes.getNote(group.ProvNum,group.CodeNum);
        } 
        group.setIsNew(true);
        Procedures.insert(group);
        List<ProcGroupItem> groupItemList = new List<ProcGroupItem>();
        ProcGroupItem groupItem;
        for (int i = 0;i < proclist.Count;i++)
        {
            groupItem = new ProcGroupItem();
            groupItem.ProcNum = proclist[i].ProcNum;
            groupItem.GroupNum = group.ProcNum;
            ProcGroupItems.insert(groupItem);
            groupItemList.Add(groupItem);
        }
        if (Programs.getUsingOrion())
        {
            OrionProc op = new OrionProc();
            op.ProcNum = group.ProcNum;
            op.Status2 = OrionStatus.C;
            OrionProcs.insert(op);
        }
         
        FormProcGroup FormP = new FormProcGroup();
        FormP.GroupCur = group;
        FormP.GroupItemList = groupItemList;
        FormP.ProcList = proclist;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (PrefC.getBool(PrefName.ProcGroupNoteDoesAggregate))
        {
            for (int i = 0;i < proclist.Count;i++)
            {
                //remove the notes from all the attached procs
                Procedure oldProc = proclist[i].Copy();
                Procedure changedProc = proclist[i].Copy();
                changedProc.Note = "";
                Procedures.update(changedProc,oldProc);
            }
        }
         
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemLabFee_Click(Object sender, EventArgs e) throws Exception {
        if (gridProg.getSelectedIndices().Length < 2 || gridProg.getSelectedIndices().Length > 3)
        {
            MsgBox.show(this,"Please select two or three procedures, one regular and the other one or two lab.");
            return ;
        }
         
        //One check that is not made is whether a lab proc is already attached to a different proc.
        DataRow row1 = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[0]].Tag;
        DataRow row2 = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[1]].Tag;
        DataRow row3 = null;
        if (gridProg.getSelectedIndices().Length == 3)
        {
            row3 = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[2]].Tag;
        }
         
        if (StringSupport.equals(row1["ProcNum"].ToString(), "0") || StringSupport.equals(row2["ProcNum"].ToString(), "0") || (row3 != null && StringSupport.equals(row3["ProcNum"].ToString(), "0")))
        {
            MsgBox.show(this,"All selected items must be procedures.");
            return ;
        }
         
        List<long> procNumsReg = new List<long>();
        List<long> procNumsLab = new List<long>();
        if (ProcedureCodes.GetProcCode(row1["ProcCode"].ToString()).IsCanadianLab)
        {
            procNumsLab.Add(PIn.Long(row1["ProcNum"].ToString()));
        }
        else
        {
            procNumsReg.Add(PIn.Long(row1["ProcNum"].ToString()));
        } 
        if (ProcedureCodes.GetProcCode(row2["ProcCode"].ToString()).IsCanadianLab)
        {
            procNumsLab.Add(PIn.Long(row2["ProcNum"].ToString()));
        }
        else
        {
            procNumsReg.Add(PIn.Long(row2["ProcNum"].ToString()));
        } 
        if (row3 != null)
        {
            if (ProcedureCodes.GetProcCode(row3["ProcCode"].ToString()).IsCanadianLab)
            {
                procNumsLab.Add(PIn.Long(row3["ProcNum"].ToString()));
            }
            else
            {
                procNumsReg.Add(PIn.Long(row3["ProcNum"].ToString()));
            } 
        }
         
        if (procNumsReg.Count == 0)
        {
            MsgBox.show(this,"One of the selected procedures must be a regular non-lab procedure as defined in Procedure Codes.");
            return ;
        }
         
        if (procNumsReg.Count > 1)
        {
            MsgBox.show(this,"Only one of the selected procedures may be a regular non-lab procedure as defined in Procedure Codes.");
            return ;
        }
         
        //We only alter the lab procedure(s), not the regular procedure.
        Procedure procLab;
        Procedure procOld;
        for (int i = 0;i < procNumsLab.Count;i++)
        {
            procLab = Procedures.GetOneProc(procNumsLab[i], false);
            procOld = procLab.copy();
            procLab.ProcNumLab = procNumsReg[0];
            Procedures.update(procLab,procOld);
        }
        moduleSelected(PatCur.PatNum);
    }

    private void menuItemLabFeeDetach_Click(Object sender, EventArgs e) throws Exception {
        if (gridProg.getSelectedIndices().Length != 1)
        {
            MsgBox.show(this,"Please select exactly one lab procedure first.");
            return ;
        }
         
        DataRow row = (DataRow)gridProg.getRows()[gridProg.getSelectedIndices()[0]].Tag;
        if (StringSupport.equals(row["ProcNum"].ToString(), "0"))
        {
            MsgBox.show(this,"Please select a lab procedure first.");
            return ;
        }
         
        if (StringSupport.equals(row["ProcNumLab"].ToString(), "0"))
        {
            MsgBox.show(this,"The selected procedure is not attached as a lab procedure.");
            return ;
        }
         
        Procedure procLab = Procedures.GetOneProc(PIn.Long(row["ProcNum"].ToString()), false);
        Procedure procOld = procLab.copy();
        procLab.ProcNumLab = 0;
        Procedures.update(procLab,procOld);
        moduleSelected(PatCur.PatNum);
    }

    /**
    * Preview is only used for debugging.
    */
    public void printReport(boolean justPreview) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        //pd2.DefaultPageSettings.Margins=new Margins(50,50,40,25);
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        try
        {
            if (justPreview)
            {
                FormRpPrintPreview pView = new FormRpPrintPreview();
                pView.printPreviewControl2.Document = pd2;
                pView.ShowDialog();
            }
            else
            {
                if (PrinterL.SetPrinter(pd2, PrintSituation.Default, PatCur.PatNum, "Progress notes printed"))
                {
                    pd2.Print();
                }
                 
            } 
        }
        catch (Exception __dummyCatchVar3)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = new Rectangle(50, 40, 800, 1000);
        //1035);//Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        if (!headingPrinted)
        {
            text = "Chart Progress Notes";
            g.DrawString(text, headingFont, Brushes.Black, 400 - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            text = PatCur.getNameFL();
            g.DrawString(text, subHeadingFont, Brushes.Black, 400 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = DateTime.Today.ToShortDateString();
            g.DrawString(text, subHeadingFont, Brushes.Black, 400 - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 30;
            headingPrinted = true;
            headingPrintH = yPos;
        }
         
        yPos = gridProg.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    /**
    * Preview is only used for debugging.
    */
    public void printDay(boolean justPreview) throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPageDay);
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.OriginAtMargins = true;
        if (pd2.DefaultPageSettings.PrintableArea.Height == 0)
        {
            pd2.DefaultPageSettings.PaperSize = new PaperSize("default", 850, 1100);
        }
         
        try
        {
            if (justPreview)
            {
                FormRpPrintPreview pView = new FormRpPrintPreview();
                pView.printPreviewControl2.Document = pd2;
                pView.ShowDialog();
            }
            else
            {
                if (PrinterL.SetPrinter(pd2, PrintSituation.Default, PatCur.PatNum, "Day report for hospital printed"))
                {
                    pd2.Print();
                }
                 
            } 
        }
        catch (Exception __dummyCatchVar4)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd2_PrintPageDay(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        Rectangle bounds = new Rectangle(50, 40, 800, 1000);
        //Some printers can handle up to 1042
        Graphics g = e.Graphics;
        String text = new String();
        Font headingFont = new Font("Arial", 13, FontStyle.Bold);
        Font subHeadingFont = new Font("Arial", 10, FontStyle.Bold);
        int yPos = bounds.Top;
        int center = bounds.X + bounds.Width / 2;
        if (!headingPrinted)
        {
            text = "Chart Progress Notes";
            g.DrawString(text, headingFont, Brushes.Black, center - g.MeasureString(text, headingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, headingFont).Height;
            //practice
            text = PrefC.getString(PrefName.PracticeTitle);
            if (!PrefC.getBool(PrefName.EasyNoClinics))
            {
                DataRow row = new DataRow();
                long procNum = new long();
                long clinicNum = new long();
                for (int i = 0;i < gridProg.getRows().Count;i++)
                {
                    row = (DataRow)gridProg.getRows().get___idx(i).getTag();
                    procNum = PIn.Long(row["ProcNum"].ToString());
                    if (procNum == 0)
                    {
                        continue;
                    }
                     
                    clinicNum = Procedures.getClinicNum(procNum);
                    if (clinicNum != 0)
                    {
                        //The first clinicNum that's encountered
                        text = Clinics.getDesc(clinicNum);
                        break;
                    }
                     
                }
            }
             
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            //name
            text = PatCur.getNameFL();
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = "Birthdate: " + PatCur.Birthdate.ToShortDateString();
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = "Printed: " + DateTime.Today.ToShortDateString();
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += (int)g.MeasureString(text, subHeadingFont).Height;
            text = "Ward: " + PatCur.Ward;
            g.DrawString(text, subHeadingFont, Brushes.Black, center - g.MeasureString(text, subHeadingFont).Width / 2, yPos);
            yPos += 20;
            //Patient images are not shown when the A to Z folders are disabled.
            if (PrefC.getAtoZfolderUsed())
            {
                Bitmap picturePat = new Bitmap();
                RefSupport<Bitmap> refVar___0 = new RefSupport<Bitmap>();
                boolean patientPictExists = Documents.getPatPict(PatCur.PatNum,ImageStore.getPatientFolder(PatCur,ImageStore.getPreferredAtoZpath()),refVar___0);
                picturePat = refVar___0.getValue();
                if (picturePat != null)
                {
                    //Successfully loaded a patient picture?
                    Bitmap thumbnail = ImageHelper.GetThumbnail(picturePat, 80);
                    g.DrawImage(thumbnail, center - 40, yPos);
                }
                 
                if (patientPictExists)
                {
                    yPos += 80;
                }
                 
                yPos += 30;
                headingPrinted = true;
                headingPrintH = yPos;
            }
             
        }
         
        yPos = gridProg.printPage(g,pagesPrinted,bounds,headingPrintH);
        pagesPrinted++;
        if (yPos == -1)
        {
            e.HasMorePages = true;
        }
        else
        {
            g.DrawString("Signature_________________________________________________________", subHeadingFont, Brushes.Black, 160, yPos + 20);
            e.HasMorePages = false;
        } 
        g.Dispose();
    }

    /**
    * Draws one button for the tabControlImages.
    */
    private void onDrawItem(Object sender, DrawItemEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        Pen penBlue = new Pen(Color.FromArgb(97, 136, 173));
        Pen penRed = new Pen(Color.FromArgb(140, 51, 46));
        Pen penOrange = new Pen(Color.FromArgb(250, 176, 3), 2);
        Pen penDkOrange = new Pen(Color.FromArgb(227, 119, 4));
        SolidBrush brBlack = new SolidBrush(Color.Black);
        int selected = tabControlImages.TabPages.IndexOf(tabControlImages.SelectedTab);
        Rectangle bounds = e.Bounds;
        Rectangle rect = new Rectangle(bounds.X + 2, bounds.Y + 1, bounds.Width - 5, bounds.Height - 4);
        if (e.Index == selected)
        {
            g.FillRectangle(new SolidBrush(Color.White), rect);
            //g.DrawRectangle(penBlue,rect);
            g.DrawLine(penOrange, rect.X, rect.Bottom - 1, rect.Right, rect.Bottom - 1);
            g.DrawLine(penDkOrange, rect.X + 1, rect.Bottom, rect.Right - 2, rect.Bottom);
            g.DrawString(tabControlImages.TabPages[e.Index].Text, Font, brBlack, bounds.X + 3, bounds.Y + 6);
        }
        else
        {
            g.DrawString(tabControlImages.TabPages[e.Index].Text, Font, brBlack, bounds.X, bounds.Y);
        } 
    }

    private void panelImages_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (e.Y > 3)
        {
            return ;
        }
         
        MouseIsDownOnImageSplitter = true;
        ImageSplitterOriginalY = panelImages.Top;
        OriginalImageMousePos = panelImages.Top + e.Y;
    }

    private void panelImages_MouseLeave(Object sender, System.EventArgs e) throws Exception {
    }

    //not needed.
    private void panelImages_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!MouseIsDownOnImageSplitter)
        {
            if (e.Y <= 3)
            {
                panelImages.Cursor = Cursors.HSplit;
            }
            else
            {
                panelImages.Cursor = Cursors.Default;
            } 
            return ;
        }
         
        //panelNewTop
        int panelNewH = panelImages.Bottom - (ImageSplitterOriginalY + (panelImages.Top + e.Y) - OriginalImageMousePos);
        //-top
        if (panelNewH < 10)
            //cTeeth.Bottom)
            panelNewH = 10;
         
        //cTeeth.Bottom;//keeps it from going too low
        if (panelNewH > panelImages.Bottom - toothChart.Bottom)
            panelNewH = panelImages.Bottom - toothChart.Bottom;
         
        //keeps it from going too high
        panelImages.Height = panelNewH;
        if (usingEcwTightOrFull())
        {
            //this might belong in ChartLayoutHelper
            if (panelImages.Visible)
            {
                panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1 - (panelImages.Height + 2);
            }
            else
            {
                panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1;
            } 
        }
         
    }

    private void panelImages_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!MouseIsDownOnImageSplitter)
        {
            return ;
        }
         
        MouseIsDownOnImageSplitter = false;
    }

    private void tabProc_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        ChartLayoutHelper.tabProc_MouseDown(SelectedProcTab, gridProg, tabProc, ClientSize, e);
        SelectedProcTab = tabProc.SelectedIndex;
        fillMovementsAndHidden();
    }

    private void tabControlImages_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (selectedImageTab == -1)
        {
            selectedImageTab = tabControlImages.SelectedIndex;
            return ;
        }
         
        Rectangle rect = tabControlImages.GetTabRect(selectedImageTab);
        if (rect.Contains(e.X, e.Y))
        {
            //clicked on the already selected tab
            if (panelImages.Visible)
            {
                panelImages.Visible = false;
            }
            else
            {
                panelImages.Visible = true;
            } 
        }
        else
        {
            //clicked on a new tab
            if (!panelImages.Visible)
            {
                panelImages.Visible = true;
            }
             
        } 
        selectedImageTab = tabControlImages.SelectedIndex;
        fillImages();
        //it will not actually fill the images unless panelImages is visible
        if (usingEcwTightOrFull())
        {
            if (panelImages.Visible)
            {
                panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1 - (panelImages.Height + 2);
            }
            else
            {
                panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1;
            } 
        }
         
    }

    private void listViewImages_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listViewImages.SelectedIndices.Count == 0)
        {
            return ;
        }
         
        //clicked on white space.
        OpenDentBusiness.Document DocCur = DocumentList[(int)visImages[listViewImages.SelectedIndices[0]]];
        if (!ImageHelper.hasImageExtension(DocCur.FileName))
        {
            try
            {
                Process.Start(CodeBase.ODFileUtils.combinePaths(patFolder,DocCur.FileName));
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            return ;
        }
         
        if (formImageViewer == null || !formImageViewer.Visible)
        {
            formImageViewer = new FormImageViewer();
            formImageViewer.Show();
        }
         
        if (formImageViewer.WindowState == FormWindowState.Minimized)
        {
            formImageViewer.WindowState = FormWindowState.Normal;
        }
         
        formImageViewer.BringToFront();
        formImageViewer.setImage(DocCur,PatCur.getNameLF() + " - " + DocCur.DateCreated.ToShortDateString() + ": " + DocCur.Description);
    }

    private void gridChartViews_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        chartViewsCellClicked(e);
    }

    private void gridChartViews_DoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        chartViewsDoubleClicked(e);
    }

    private void gridCustomerViews_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        chartViewsCellClicked(e);
    }

    private void gridCustomerViews_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        chartViewsDoubleClicked(e);
    }

    private void chartViewsCellClicked(OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SetChartView(ChartViews.getListt()[e.getRow()]);
        gridChartViews.setSelected(e.getRow(),true);
        if (IsDistributorKey)
        {
            gridCustomerViews.setSelected(e.getRow(),true);
        }
         
    }

    private void chartViewsDoubleClicked(OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        int count = gridChartViews.getRows().Count;
        FormChartView FormC = new FormChartView();
        FormC.ChartViewCur = ChartViews.getListt()[e.getRow()];
        FormC.ShowDialog();
        fillChartViewsGrid();
        if (IsDistributorKey)
        {
            fillCustomerViewsGrid();
        }
         
        if (gridChartViews.getRows().Count == 0)
        {
            fillProgNotes();
            return ;
        }
         
        //deleted last view, so display default
        if (gridChartViews.getRows().Count == count)
        {
            gridChartViews.setSelected(FormC.ChartViewCur.ItemOrder,true);
            SetChartView(ChartViews.getListt()[FormC.ChartViewCur.ItemOrder]);
        }
        else if (gridChartViews.getRows().Count > 0)
        {
            for (int i = 0;i < ChartViews.getListt().Count;i++)
            {
                ChartViews.getListt()[i].ItemOrder = i;
                ChartViews.Update(ChartViews.getListt()[i]);
            }
            if (FormC.ChartViewCur.ItemOrder != 0)
            {
                gridChartViews.setSelected(FormC.ChartViewCur.ItemOrder - 1,true);
                SetChartView(ChartViews.getListt()[FormC.ChartViewCur.ItemOrder - 1]);
            }
            else
            {
                gridChartViews.setSelected(0,true);
                if (IsDistributorKey)
                {
                }
                 
                SetChartView(ChartViews.getListt()[0]);
            } 
        }
          
        if (IsDistributorKey)
        {
            gridCustomerViews.setSelected(gridChartViews.getSelectedIndex(),true);
        }
         
    }

    private void butChartViewAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        int count = gridChartViews.getRows().Count;
        int selectedIndex = gridChartViews.getSelectedIndex();
        FormChartView FormChartAdd = new FormChartView();
        FormChartAdd.ChartViewCur = new ChartView();
        FormChartAdd.ChartViewCur.setIsNew(true);
        FormChartAdd.ChartViewCur.ItemOrder = count;
        if (checkAppt.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 1;
        }
         
        if (checkComm.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 2;
        }
         
        if (checkCommFamily.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 4;
        }
         
        if (checkTasks.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 8;
        }
         
        if (checkEmail.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 16;
        }
         
        if (checkLabCase.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 32;
        }
         
        if (checkRx.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 64;
        }
         
        if (checkSheets.Checked)
        {
            FormChartAdd.ChartViewCur.ObjectTypes += 128;
        }
         
        if (checkShowTP.Checked)
        {
            FormChartAdd.ChartViewCur.ProcStatuses += 1;
        }
         
        if (checkShowC.Checked)
        {
            FormChartAdd.ChartViewCur.ProcStatuses += 2;
        }
         
        if (checkShowE.Checked)
        {
            FormChartAdd.ChartViewCur.ProcStatuses += 4;
        }
         
        if (checkShowR.Checked)
        {
            FormChartAdd.ChartViewCur.ProcStatuses += 16;
        }
         
        if (checkShowCn.Checked)
        {
            FormChartAdd.ChartViewCur.ProcStatuses += 64;
        }
         
        FormChartAdd.ChartViewCur.SelectedTeethOnly = checkShowTeeth.Checked;
        FormChartAdd.ChartViewCur.ShowProcNotes = checkNotes.Checked;
        FormChartAdd.ChartViewCur.IsAudit = checkAudit.Checked;
        for (int i = 0;i < listProcStatusCodes.SelectedItems.Count;i++)
        {
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "TP"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.TP;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "C"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.C;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "E"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.E;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "R"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.R;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "RO"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.RO;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "CS"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.CS;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "CR"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.CR;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "CA_Tx"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.CA_Tx;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "CA_EPRD"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.CA_EPRD;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "CA_PD"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.CA_PD;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "S"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.S;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "ST"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.ST;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "W"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.W;
            }
             
            if (StringSupport.equals(listProcStatusCodes.SelectedItems[i].ToString(), "A"))
            {
                FormChartAdd.ChartViewCur.OrionStatusFlags |= OrionStatus.A;
            }
             
        }
        FormChartAdd.ShowDialog();
        fillChartViewsGrid();
        if (IsDistributorKey)
        {
            fillCustomerViewsGrid();
        }
         
        int count2 = gridChartViews.getRows().Count;
        if (count2 == 0)
        {
            return ;
        }
         
        if (count2 == count)
        {
            if (selectedIndex != -1)
            {
                gridChartViews.setSelected(selectedIndex,true);
                if (IsDistributorKey)
                {
                    gridCustomerViews.setSelected(selectedIndex,true);
                }
                 
                SetChartView(ChartViews.getListt()[selectedIndex]);
            }
             
        }
        else
        {
            FormChartAdd.ChartViewCur.ItemOrder = count;
            ChartViews.update(FormChartAdd.ChartViewCur);
            fillChartViewsGrid();
            if (IsDistributorKey)
            {
                fillCustomerViewsGrid();
            }
             
            SetChartView(ChartViews.getListt()[count]);
            gridChartViews.setSelected(count,true);
            if (IsDistributorKey)
            {
                gridCustomerViews.setSelected(selectedIndex,true);
            }
             
        } 
    }

    private void butChartViewUp_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        if (gridChartViews.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a view first.");
            return ;
        }
         
        int oldIdx = new int();
        int newIdx = new int();
        ChartView oldItem;
        ChartView newItem;
        if (gridChartViews.getSelectedIndex() != -1)
        {
            oldIdx = gridChartViews.getSelectedIndex();
            if (oldIdx == 0)
            {
                return ;
            }
             
            //can't move up any more
            newIdx = oldIdx - 1;
            for (int i = 0;i < ChartViews.getListt().Count;i++)
            {
                if (ChartViews.getListt()[i].ItemOrder == oldIdx)
                {
                    oldItem = ChartViews.getListt()[i];
                    newItem = ChartViews.getListt()[newIdx];
                    oldItem.ItemOrder = newItem.ItemOrder;
                    newItem.ItemOrder += 1;
                    ChartViews.update(oldItem);
                    ChartViews.update(newItem);
                }
                 
            }
            fillChartViewsGrid();
            gridChartViews.setSelected(newIdx,true);
            if (IsDistributorKey)
            {
                fillCustomerViewsGrid();
                gridCustomerViews.setSelected(newIdx,true);
            }
             
            SetChartView(ChartViews.getListt()[newIdx]);
        }
         
    }

    private void butChartViewDown_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        if (gridChartViews.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select a view first.");
            return ;
        }
         
        int oldIdx = new int();
        int newIdx = new int();
        ChartView oldItem;
        ChartView newItem;
        if (gridChartViews.getSelectedIndex() != -1)
        {
            oldIdx = gridChartViews.getSelectedIndex();
            if (oldIdx == ChartViews.getListt().Count - 1)
            {
                return ;
            }
             
            //can't move down any more
            newIdx = oldIdx + 1;
            for (int i = 0;i < ChartViews.getListt().Count;i++)
            {
                if (ChartViews.getListt()[i].ItemOrder == newIdx)
                {
                    newItem = ChartViews.getListt()[i];
                    oldItem = ChartViews.getListt()[oldIdx];
                    newItem.ItemOrder = oldItem.ItemOrder;
                    oldItem.ItemOrder += 1;
                    ChartViews.update(oldItem);
                    ChartViews.update(newItem);
                }
                 
            }
            fillChartViewsGrid();
            gridChartViews.setSelected(newIdx,true);
            if (IsDistributorKey)
            {
                fillCustomerViewsGrid();
                gridCustomerViews.setSelected(newIdx,true);
            }
             
            SetChartView(ChartViews.getListt()[newIdx]);
        }
         
    }

    public void functionKeyPressContrChart(Keys keys) throws Exception {
        Keys __dummyScrutVar11 = keys;
        if (__dummyScrutVar11.equals(Keys.F1))
        {
            if (gridChartViews.getRows().Count > 0)
            {
                gridChartViews.setSelected(0,true);
                SetChartView(ChartViews.getListt()[0]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 0)
            {
                gridCustomerViews.setSelected(0,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F2))
        {
            if (gridChartViews.getRows().Count > 1)
            {
                gridChartViews.setSelected(1,true);
                SetChartView(ChartViews.getListt()[1]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 1)
            {
                gridCustomerViews.setSelected(1,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F3))
        {
            if (gridChartViews.getRows().Count > 2)
            {
                gridChartViews.setSelected(2,true);
                SetChartView(ChartViews.getListt()[2]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 2)
            {
                gridCustomerViews.setSelected(2,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F4))
        {
            if (gridChartViews.getRows().Count > 3)
            {
                gridChartViews.setSelected(3,true);
                SetChartView(ChartViews.getListt()[3]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 3)
            {
                gridCustomerViews.setSelected(3,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F5))
        {
            if (gridChartViews.getRows().Count > 4)
            {
                gridChartViews.setSelected(4,true);
                SetChartView(ChartViews.getListt()[4]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 4)
            {
                gridCustomerViews.setSelected(4,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F6))
        {
            if (gridChartViews.getRows().Count > 5)
            {
                gridChartViews.setSelected(5,true);
                SetChartView(ChartViews.getListt()[5]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 5)
            {
                gridCustomerViews.setSelected(5,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F7))
        {
            if (gridChartViews.getRows().Count > 6)
            {
                gridChartViews.setSelected(6,true);
                SetChartView(ChartViews.getListt()[6]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 6)
            {
                gridCustomerViews.setSelected(6,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F8))
        {
            if (gridChartViews.getRows().Count > 7)
            {
                gridChartViews.setSelected(7,true);
                SetChartView(ChartViews.getListt()[7]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 7)
            {
                gridCustomerViews.setSelected(7,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F9))
        {
            if (gridChartViews.getRows().Count > 8)
            {
                gridChartViews.setSelected(8,true);
                SetChartView(ChartViews.getListt()[8]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 8)
            {
                gridCustomerViews.setSelected(8,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F10))
        {
            if (gridChartViews.getRows().Count > 9)
            {
                gridChartViews.setSelected(9,true);
                SetChartView(ChartViews.getListt()[9]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 9)
            {
                gridCustomerViews.setSelected(9,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F11))
        {
            if (gridChartViews.getRows().Count > 10)
            {
                gridChartViews.setSelected(10,true);
                SetChartView(ChartViews.getListt()[10]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 10)
            {
                gridCustomerViews.setSelected(10,true);
            }
             
        }
        else if (__dummyScrutVar11.equals(Keys.F12))
        {
            if (gridChartViews.getRows().Count > 11)
            {
                gridChartViews.setSelected(11,true);
                SetChartView(ChartViews.getListt()[11]);
            }
             
            if (IsDistributorKey && gridCustomerViews.getRows().Count > 11)
            {
                gridCustomerViews.setSelected(11,true);
            }
             
        }
                    
    }

    private void listCommonProcs_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        if (listCommonProcs.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a procedure.");
            return ;
        }
         
        String procCode = "";
        double procFee = 0;
        //Hard coded internal procedures.
        SelectedIndex __dummyScrutVar12 = listCommonProcs.SelectedIndex;
        if (__dummyScrutVar12.equals(0))
        {
            //Monthly Maintenance
            procCode = "001";
            procFee = 149;
        }
        else if (__dummyScrutVar12.equals(1))
        {
            //Monthly Mobile
            procCode = "027";
            procFee = 10;
        }
        else if (__dummyScrutVar12.equals(2))
        {
            //Monthly E-Mail Support
            procCode = "008";
            procFee = 89;
        }
        else if (__dummyScrutVar12.equals(3))
        {
            //Monthly EHR
            procCode = "029";
        }
        else if (__dummyScrutVar12.equals(4))
        {
            //Data Conversion
            procCode = "005";
            procFee = 700;
        }
        else if (__dummyScrutVar12.equals(5))
        {
            //Trial Conversion
            procCode = "N5641";
        }
        else if (__dummyScrutVar12.equals(6))
        {
            //Demo
            procCode = "018";
        }
        else if (__dummyScrutVar12.equals(7))
        {
            //Online Training
            procCode = "N1254";
        }
        else if (__dummyScrutVar12.equals(8))
        {
            //Additional Online Training
            procCode = "N8989";
            procFee = 50;
        }
        else if (__dummyScrutVar12.equals(9))
        {
            //eCW Online Training
            procCode = "eCW1";
        }
        else if (__dummyScrutVar12.equals(10))
        {
            //eCW Installation Verification
            procCode = "eCW2";
        }
        else if (__dummyScrutVar12.equals(11))
        {
            //Programming
            procCode = "007";
        }
        else if (__dummyScrutVar12.equals(12))
        {
            //Query Programming
            procCode = "023";
            procFee = 90;
        }
                     
        //Simply add the procedure to the customers account.
        Procedure proc = new Procedure();
        proc.CodeNum = ProcedureCodes.getCodeNum(procCode);
        proc.DateEntryC = DateTimeOD.getToday();
        proc.PatNum = PatCur.PatNum;
        proc.ProcDate = DateTime.Now;
        proc.DateTP = DateTime.Now;
        proc.ProcFee = procFee;
        proc.ProcStatus = ProcStat.TP;
        proc.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
        proc.MedicalCode = ProcedureCodes.getProcCode(proc.CodeNum).MedicalCode;
        proc.BaseUnits = ProcedureCodes.getProcCode(proc.CodeNum).BaseUnits;
        Procedures.insert(proc);
        //no recall synch needed because dental offices don't use this feature
        listCommonProcs.SelectedIndex = -1;
        fillProgNotes();
    }

    private void butBig_Click(Object sender, EventArgs e) throws Exception {
    }

    private void butECWup_Click(Object sender, EventArgs e) throws Exception {
        panelEcw.Location = toothChart.Location;
        if (panelImages.Visible)
        {
            panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1 - (panelImages.Height + 2);
        }
        else
        {
            panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1;
        } 
        butECWdown.Visible = true;
        butECWup.Visible = false;
    }

    private void butECWdown_Click(Object sender, EventArgs e) throws Exception {
        panelEcw.Location = new Point(524 + 2, textTreatmentNotes.Bottom + 1);
        if (panelImages.Visible)
        {
            panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1 - (panelImages.Height + 2);
        }
        else
        {
            panelEcw.Height = tabControlImages.Top - panelEcw.Top + 1;
        } 
        butECWdown.Visible = false;
        butECWup.Visible = true;
    }

    private void panelQuickButtons_Paint(Object sender, PaintEventArgs e) throws Exception {
    }

    private void buttonCDO_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "DO";
        ProcButtonClicked(0, "D2392");
    }

    private void buttonCMOD_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MOD";
        ProcButtonClicked(0, "D2393");
    }

    private void buttonCO_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "O";
        ProcButtonClicked(0, "D2391");
    }

    private void buttonCMO_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MO";
        ProcButtonClicked(0, "D2392");
    }

    private void butCOL_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "OL";
        ProcButtonClicked(0, "D2392");
    }

    private void butCOB_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "OB";
        ProcButtonClicked(0, "D2392");
    }

    private void butDL_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "DL";
        ProcButtonClicked(0, "D2331");
    }

    private void butML_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "ML";
        ProcButtonClicked(0, "D2331");
    }

    private void buttonCSeal_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "";
        ProcButtonClicked(0, "D1351");
    }

    private void buttonADO_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "DO";
        ProcButtonClicked(0, "D2150");
    }

    private void buttonAMOD_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MOD";
        ProcButtonClicked(0, "D2160");
    }

    private void buttonAO_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "O";
        ProcButtonClicked(0, "D2140");
    }

    private void buttonAMO_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MO";
        ProcButtonClicked(0, "D2150");
    }

    private void butCMDL_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MDL";
        ProcButtonClicked(0, "D2332");
    }

    private void buttonAOL_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "OL";
        ProcButtonClicked(0, "D2150");
    }

    private void buttonAOB_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "OB";
        ProcButtonClicked(0, "D2150");
    }

    private void buttonAMODL_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MODL";
        ProcButtonClicked(0, "D2161");
    }

    private void buttonAMODB_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MODB";
        ProcButtonClicked(0, "D2161");
    }

    private void buttonCMODL_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MODL";
        ProcButtonClicked(0, "D2394");
    }

    private void buttonCMODB_Click(Object sender, EventArgs e) throws Exception {
        textSurf.Text = "MODB";
        ProcButtonClicked(0, "D2394");
    }

    private void butAddKey_Click(Object sender, EventArgs e) throws Exception {
        RegistrationKey key = new RegistrationKey();
        key.PatNum = PatCur.PatNum;
        //Notes are not commonly necessary, because most customers have only one office (thus only 1 key is necessary).
        //A tech can edit the note later after it is added if necessary.
        key.Note = "";
        key.DateStarted = DateTime.Today;
        key.IsForeign = false;
        key.VotesAllotted = 100;
        RegistrationKeys.insert(key);
        fillPtInfo();
    }

    //Refresh registration key list in patient info grid.
    private void butForeignKey_Click(Object sender, EventArgs e) throws Exception {
        RegistrationKey key = new RegistrationKey();
        key.PatNum = PatCur.PatNum;
        key.Note = "";
        key.DateStarted = DateTime.Today;
        key.IsForeign = true;
        key.VotesAllotted = 100;
        RegistrationKeys.insert(key);
        fillPtInfo();
    }

    private void butPhoneNums_Click(Object sender, EventArgs e) throws Exception {
        if (FormOpenDental.CurPatNum == 0)
        {
            MsgBox.show(this,"Please select a patient first.");
            return ;
        }
         
        FormPhoneNumbersManage FormM = new FormPhoneNumbersManage();
        FormM.PatNum = FormOpenDental.CurPatNum;
        FormM.ShowDialog();
    }

    private void butLoadDirectX_Click(Object sender, EventArgs e) throws Exception {
    }

    //toothChart.LoadDirectX();
    private void gridProg_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        DataTable progNotes = DataSetMain.Tables["ProgNotes"];
        //DataRow rowClicked=progNotes.Rows[e.Row];
        DataRow rowClicked = (DataRow)gridProg.getRows().get___idx(e.getRow()).getTag();
        long procNum = PIn.Long(rowClicked["ProcNum"].ToString());
        if (procNum == 0)
        {
            return ;
        }
         
        //if not a procedure
        long codeNum = PIn.Long(rowClicked["CodeNum"].ToString());
        if (!StringSupport.equals(ProcedureCodes.getStringProcCode(codeNum), ProcedureCodes.GroupProcCode))
        {
            return ;
        }
         
        //if not a group note
        List<ProcGroupItem> groupItemList = ProcGroupItems.getForGroup(procNum);
        for (int i = 0;i < gridProg.getRows().Count;i++)
        {
            //for(int i=0;i<progNotes.Rows.Count;i++){
            DataRow row = (DataRow)gridProg.getRows().get___idx(i).getTag();
            if (StringSupport.equals(row["ProcNum"].ToString(), "0"))
            {
                continue;
            }
             
            long procnum = PIn.Long(row["ProcNum"].ToString());
            for (int j = 0;j < groupItemList.Count;j++)
            {
                if (procnum == groupItemList[j].ProcNum)
                {
                    gridProg.setSelected(i,true);
                }
                 
            }
        }
    }

    /**
    * This does not currently handle custom views.
    */
    private void setDateRange() throws Exception {
        switch(ChartViewCurDisplay.DatesShowing)
        {
            case All: 
                ShowDateStart = DateTime.MinValue;
                ShowDateEnd = DateTime.MinValue;
                break;
            case Today: 
                //interpreted as empty.  We want to show all future dates.
                ShowDateStart = DateTime.Today;
                ShowDateEnd = DateTime.Today;
                break;
            case Yesterday: 
                ShowDateStart = DateTime.Today.AddDays(-1);
                ShowDateEnd = DateTime.Today.AddDays(-1);
                break;
            case ThisYear: 
                ShowDateStart = new DateTime(DateTime.Today.Year, 1, 1);
                ShowDateEnd = new DateTime(DateTime.Today.Year, 12, 31);
                break;
            case LastYear: 
                ShowDateStart = new DateTime(DateTime.Today.Year - 1, 1, 1);
                ShowDateEnd = new DateTime(DateTime.Today.Year - 1, 12, 31);
                break;
        
        }
    }

    /**
    * This method is used to set the Date Range filter start and stop dates based on either a custom date range or DatesShowing property of chart view.
    */
    private void fillDateRange() throws Exception {
        textShowDateRange.Text = "";
        if (ShowDateStart.Year > 1880)
        {
            textShowDateRange.Text += ShowDateStart.ToShortDateString();
        }
         
        if (ShowDateEnd.Year > 1880 && ShowDateStart != ShowDateEnd)
        {
            if (!StringSupport.equals(textShowDateRange.Text, ""))
            {
                textShowDateRange.Text += "-";
            }
             
            textShowDateRange.Text += ShowDateEnd.ToShortDateString();
        }
         
        if (StringSupport.equals(textShowDateRange.Text, ""))
        {
            textShowDateRange.Text = Lan.g(this,"All Dates");
        }
         
    }

    private void butShowDateRange_Click(Object sender, EventArgs e) throws Exception {
        FormChartViewDateFilter FormC = new FormChartViewDateFilter();
        FormC.DateStart = ShowDateStart;
        FormC.DateEnd = ShowDateEnd;
        FormC.ShowDialog();
        if (FormC.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ShowDateStart = FormC.DateStart;
        ShowDateEnd = FormC.DateEnd;
        if (gridChartViews.getRows().Count > 0)
        {
            //enable custom view label
            labelCustView.Visible = true;
        }
         
        chartCustViewChanged = true;
        fillDateRange();
        fillProgNotes();
    }

}


/*
		private void XrayLinkBtn_Click(object sender, System.EventArgs e)	// TJE
		{
			if (!Patients.PatIsLoaded || Patients.Cur.PatNum<1)
				return;
			VQLink.VQStart(false,"",0,0);
		}
		private void SetPanelCol(Panel p, char c)	// TJE
		{
			if (c != '0')
				p.BackColor=SystemColors.ActiveCaption;
			else
				p.BackColor=SystemColors.ActiveBorder;
		}
		private void VQUpdatePatient()	// TJE
		{
			String	s;
			if (!Patients.PatIsLoaded || Patients.Cur.PatNum<1)	
				s="";
			else
				s=VQLink.SearchTStatus(Patients.Cur.PatNum);
			if (s.Length>=32) 
			{
				SetPanelCol(tooth11,s[0]);
				SetPanelCol(tooth12,s[1]);
				SetPanelCol(tooth13,s[2]);
				SetPanelCol(tooth14,s[3]);
				SetPanelCol(tooth15,s[4]);
				SetPanelCol(tooth16,s[5]);
				SetPanelCol(tooth17,s[6]);
				SetPanelCol(tooth18,s[7]);
				SetPanelCol(tooth21,s[8]);
				SetPanelCol(tooth22,s[9]);
				SetPanelCol(tooth23,s[10]);
				SetPanelCol(tooth24,s[11]);
				SetPanelCol(tooth25,s[12]);
				SetPanelCol(tooth26,s[13]);
				SetPanelCol(tooth27,s[14]);
				SetPanelCol(tooth28,s[15]);
				SetPanelCol(tooth31,s[16]);
				SetPanelCol(tooth32,s[17]);
				SetPanelCol(tooth33,s[18]);
				SetPanelCol(tooth34,s[19]);
				SetPanelCol(tooth35,s[20]);
				SetPanelCol(tooth36,s[21]);
				SetPanelCol(tooth37,s[22]);
				SetPanelCol(tooth38,s[23]);
				SetPanelCol(tooth41,s[24]);
				SetPanelCol(tooth42,s[25]);
				SetPanelCol(tooth43,s[26]);
				SetPanelCol(tooth44,s[27]);
				SetPanelCol(tooth45,s[28]);
				SetPanelCol(tooth46,s[29]);
				SetPanelCol(tooth47,s[30]);
				SetPanelCol(tooth48,s[31]);
			}
			if (s.Length>=32+6) 
			{
				SetPanelCol(toothpanos,s[32]);
				SetPanelCol(toothcephs,s[33]);
				if (s[34]!='0' | s[35]!='0' | s[36]!='0' | s[37]!='0') 
				{
					SetPanelCol(toothbw,'1');
					SetPanelCol(toothbwfloat,'1');
				}
				else
				{
					SetPanelCol(toothbw,'0');
					SetPanelCol(toothbwfloat,'0');
				}
			}
			if (s.Length>=32+6+9) 
			{
				if (s[39]!='0' | s[40]!='0' | s[41]!='0' | s[43]!='0') 
					SetPanelCol(toothcolors,'1');
				else
					SetPanelCol(toothcolors,'0');
				SetPanelCol(toothxrays,s[42]);
				SetPanelCol(toothpanos,s[44]);
				SetPanelCol(toothcephs,s[45]);
				SetPanelCol(toothdocs,s[46]);
			}
			if (s.Length>=32+6+9+1) 
			{
				SetPanelCol(toothfiles,s[47]);
			}
		}
		private void tooth18_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos(((Panel)sender).Name.Substring(5,2),VisiQuick.spf_tinymode+VisiQuick.spf_single,0);	
		}
		private void toothbwfloat_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos("",VisiQuick.spf_tinymode+VisiQuick.spf_2horizontal,VisiQuick.spi_bitewings);
		}
		private void toothbw_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos("",VisiQuick.npi_xrayview,VisiQuick.spi_bitewings);
		}
		private void toothxrays_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.VQStart(false,"",0,VisiQuick.npi_xrayview);
		}
		private void toothcolors_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.VQStart(false,"",0,VisiQuick.npi_colorview);
		}
		private void toothpanos_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos("",VisiQuick.spf_single,VisiQuick.spi_panview);
		}
		private void toothcephs_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos("",VisiQuick.spf_single,VisiQuick.spi_cephview);
		}
		private void toothdocs_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos("",VisiQuick.spf_single,VisiQuick.spi_docview);
		}
		private void toothfiles_Click(object sender, System.EventArgs e)	// TJE
		{
			VQLink.SearchPhotos("",VisiQuick.spf_single,VisiQuick.spi_fileview);
		}
		*/
//end class