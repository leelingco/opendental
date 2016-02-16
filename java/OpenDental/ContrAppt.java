//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:14 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.AppointmentL;
import OpenDental.ApptListSelection;
import OpenDental.ApptViewItemL;
import OpenDental.AutomationL;
import OpenDental.ContrAppt;
import OpenDental.ContrApptSingle;
import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormAdjust;
import OpenDental.FormApptEdit;
import OpenDental.FormApptLists;
import OpenDental.FormApptPrintSetup;
import OpenDental.FormApptsOther;
import OpenDental.FormASAP;
import OpenDental.FormBlockoutCutCopyPaste;
import OpenDental.FormCommItem;
import OpenDental.FormConfirmList;
import OpenDental.FormDefinitions;
import OpenDental.FormGraphEmployeeTime;
import OpenDental.FormLabCases;
import OpenDental.FormMonthView;
import OpenDental.FormPatientSelect;
import OpenDental.FormProviderPick;
import OpenDental.FormProvidersMultiPick;
import OpenDental.FormRecallList;
import OpenDental.FormRpPrintPreview;
import OpenDental.FormRpRouting;
import OpenDental.FormScheduleBlockEdit;
import OpenDental.FormScheduleDayEdit;
import OpenDental.FormTrackNext;
import OpenDental.FormUnsched;
import OpenDental.GotoModule;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PatientL;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.ProcedureL;
import OpenDental.ProgramL;
import OpenDental.Properties.Resources;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.PinBoard;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.AppointmentRuleC;
import OpenDentBusiness.AppointmentRules;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptField;
import OpenDentBusiness.ApptFields;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.ApptViewC;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Documents;
import OpenDentBusiness.Family;
import OpenDentBusiness.ImageStore;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.LabCase;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.OtherResult;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.ScheduleType;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;
import OpenDentBusiness.SheetTypeEnum;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.UI.ApptDrawing;
import OpenDentBusiness.UI.ApptSingleDrawing;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrAppt  extends System.Windows.Forms.UserControl 
{
    private OpenDental.ContrApptSheet ContrApptSheet2;
    private ContrApptSingle[] ContrApptSingle3 = new ContrApptSingle[]();
    //the '3' has no significance
    private System.Windows.Forms.MonthCalendar Calendar2 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.Label labelDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDate2 = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    // Required designer variable.
    private boolean mouseIsDown = false;
    /**
    * The point where the mouse was originally down.  In Appt Sheet coordinates
    */
    private Point mouseOrigin = new Point();
    /**
    * Control origin.  If moving an appointment, this is the location where the appointment was at the beginning of the drag.
    */
    private Point contOrigin = new Point();
    private ContrApptSingle TempApptSingle;
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    private boolean boolAptMoved = false;
    private OpenDental.UI.Button butToday;
    private System.Windows.Forms.Panel panelSheet = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelCalendar = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelArrows = new System.Windows.Forms.Panel();
    private System.Windows.Forms.VScrollBar vScrollBar1 = new System.Windows.Forms.VScrollBar();
    private System.Windows.Forms.Panel panelOps = new System.Windows.Forms.Panel();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private System.Windows.Forms.ListBox listConfirmed = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Button butComplete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butUnsched = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butDelete = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butBreak = new System.Windows.Forms.Button();
    /**
    * The actual operatoryNum of the clicked op.
    */
    public static long SheetClickedonOp = new long();
    /**
    * 
    */
    public static int SheetClickedonHour = new int();
    /**
    * 
    */
    public static int SheetClickedonMin = new int();
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private OpenDental.UI.Button butBack;
    private OpenDental.UI.Button butClearPin;
    private OpenDental.UI.Button butFwd;
    private System.Windows.Forms.Panel panelAptInfo = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private System.Windows.Forms.TextBox textLab = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProduction = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboView = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ContextMenu menuPatient = new System.Windows.Forms.ContextMenu();
    /**
    * 
    */
    public FormRpPrintPreview pView;
    private OpenDental.UI.Button butMakeAppt;
    private boolean cardPrintFamily = new boolean();
    private System.Windows.Forms.ContextMenu menuApt = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.ContextMenu menuBlockout = new System.Windows.Forms.ContextMenu();
    private System.Windows.Forms.ContextMenu menuWeeklyApt = new System.Windows.Forms.ContextMenu();
    private List<Schedule> SchedListPeriod = new List<Schedule>();
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    private OpenDental.UI.Button butSearch;
    private System.Windows.Forms.GroupBox groupSearch = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butSearchNext;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textBefore = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.RadioButton radioBeforeAM = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioBeforePM = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioAfterPM = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioAfterAM = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.TextBox textAfter = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSearchClose;
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button butSearchCloseX = new System.Windows.Forms.Button();
    private System.Windows.Forms.ListBox listProviders = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listSearchResults = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.DateTimePicker dateSearch = new System.Windows.Forms.DateTimePicker();
    private List<DateTime> SearchResults = new List<DateTime>();
    private OpenDental.UI.Button butRefresh;
    private boolean ResizingAppt = new boolean();
    private int ResizingOrigH = new int();
    //private bool isWeeklyView;
    public static DateTime WeekStartDate = new DateTime();
    public static DateTime WeekEndDate = new DateTime();
    private OpenDental.UI.Button butLab;
    /**
    * The index of the day as shown on the screen.  Only used in weekly view.
    */
    public static int SheetClickedonDay = new int();
    /**
    * 
    */
    private Panel infoBubble = new Panel();
    /**
    * The dataset that holds all the data (well, not quite all of it yet)
    */
    private DataSet DS = new DataSet();
    /**
    * If the user has done a blockout/copy, then this will contain the blockout that is on the "clipboard".
    */
    private Schedule BlockoutClipboard;
    /**
    * This has to be tracked globally because mouse might move directly from one appt to another without any break.  This is the only way to know if we are still over the same appt.
    */
    private long bubbleAptNum = new long();
    private DateTime bubbleTime = new DateTime();
    private Point bubbleLocation = new Point();
    private OpenDental.UI.ODGrid gridEmpSched;
    private OpenDental.UI.PictureBox PicturePat;
    //private string PatCurName;
    //private int PatCurNum;
    private Timer timerInfoBubble = new Timer();
    //private string PatCurChartNumber;
    private TabControl tabControl = new TabControl();
    private TabPage tabWaiting = new TabPage();
    private TabPage tabSched = new TabPage();
    private OpenDental.UI.ODGrid gridWaiting;
    private OpenDental.UI.Button butMonth;
    //<summary></summary>
    //public static Size PinboardSize=new Size(106,92);
    private PinBoard pinBoard;
    //private ContrApptSingle PinApptSingle;
    /**
    * Local computer time.  Used by waiting room feature as delta time for display refresh.
    */
    private DateTime LastTimeDataRetrieved = new DateTime();
    private Timer timerWaitingRoom = new Timer();
    private OpenDental.UI.Button butBackMonth;
    private OpenDental.UI.Button butFwdMonth;
    private OpenDental.UI.Button butBackWeek;
    private OpenDental.UI.Button butFwdWeek;
    private RadioButton radioDay = new RadioButton();
    private RadioButton radioWeek = new RadioButton();
    private boolean InitializedOnStartup = new boolean();
    private Patient PatCur;
    private FormRecallList FormRecallL;
    private OpenDental.UI.Button butGraph;
    private Timer timerTests = new Timer();
    //private int stressCounter;
    /**
    * When a popup happens durring attempted drag off pinboard, this helps cancel the drag.
    */
    private boolean CancelPinMouseDown = new boolean();
    private DateTime apptPrintStartTime = new DateTime();
    private DateTime apptPrintStopTime = new DateTime();
    private int apptPrintFontSize = new int();
    private OpenDental.UI.Button butProvPick;
    private int apptPrintColsPerPage = new int();
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butProvHygenist;
    private OpenDental.UI.Button butProvDentist;
    public List<Provider> ProviderList = new List<Provider>();
    private int pagesPrinted = new int();
    private int pageRow = new int();
    private OpenDental.UI.Button butFamRecall;
    private OpenDental.UI.Button butViewAppts;
    private OpenDental.UI.Button butMakeRecall;
    private Panel panelMakeButtons = new Panel();
    private int pageColumn = new int();
    /**
    * 
    */
    public ContrAppt() throws Exception {
        Logger.openlog.log("Initializing appointment module...",Severity.INFO);
        initializeComponent();
        // This call is required by the Windows.Forms Form Designer.
        menuWeeklyApt = new System.Windows.Forms.ContextMenu();
        infoBubble = new Panel();
        infoBubble.Visible = false;
        infoBubble.Size = new Size(200, 300);
        infoBubble.MouseMove += new MouseEventHandler(InfoBubble_MouseMove);
        infoBubble.BackColor = Color.FromArgb(255, 250, 190);
        PicturePat = new OpenDental.UI.PictureBox();
        PicturePat.Location = new Point(6, 17);
        PicturePat.Size = new Size(100, 100);
        PicturePat.BackColor = Color.FromArgb(232, 220, 190);
        PicturePat.setTextNullImage(Lan.g(this,"Patient Picture Unavailable"));
        PicturePat.MouseMove += new MouseEventHandler(PicturePat_MouseMove);
        infoBubble.Controls.Clear();
        infoBubble.Controls.Add(PicturePat);
        this.Controls.Add(infoBubble);
        ContrApptSheet2.MouseWheel += new MouseEventHandler(ContrApptSheet2_MouseWheel);
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

    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ContrAppt.class);
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.Calendar2 = new System.Windows.Forms.MonthCalendar();
        this.labelDate = new System.Windows.Forms.Label();
        this.labelDate2 = new System.Windows.Forms.Label();
        this.panelArrows = new System.Windows.Forms.Panel();
        this.butBackMonth = new OpenDental.UI.Button();
        this.butFwdMonth = new OpenDental.UI.Button();
        this.butBackWeek = new OpenDental.UI.Button();
        this.butFwdWeek = new OpenDental.UI.Button();
        this.butToday = new OpenDental.UI.Button();
        this.butBack = new OpenDental.UI.Button();
        this.butFwd = new OpenDental.UI.Button();
        this.panelSheet = new System.Windows.Forms.Panel();
        this.vScrollBar1 = new System.Windows.Forms.VScrollBar();
        this.ContrApptSheet2 = new OpenDental.ContrApptSheet();
        this.panelAptInfo = new System.Windows.Forms.Panel();
        this.listConfirmed = new System.Windows.Forms.ListBox();
        this.butComplete = new System.Windows.Forms.Button();
        this.butUnsched = new System.Windows.Forms.Button();
        this.butDelete = new System.Windows.Forms.Button();
        this.butBreak = new System.Windows.Forms.Button();
        this.panelCalendar = new System.Windows.Forms.Panel();
        this.radioWeek = new System.Windows.Forms.RadioButton();
        this.radioDay = new System.Windows.Forms.RadioButton();
        this.butGraph = new OpenDental.UI.Button();
        this.butMonth = new OpenDental.UI.Button();
        this.pinBoard = new OpenDental.UI.PinBoard();
        this.butLab = new OpenDental.UI.Button();
        this.butSearch = new OpenDental.UI.Button();
        this.textProduction = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textLab = new System.Windows.Forms.TextBox();
        this.comboView = new System.Windows.Forms.ComboBox();
        this.label2 = new System.Windows.Forms.Label();
        this.butClearPin = new OpenDental.UI.Button();
        this.panelOps = new System.Windows.Forms.Panel();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.menuApt = new System.Windows.Forms.ContextMenu();
        this.menuPatient = new System.Windows.Forms.ContextMenu();
        this.menuBlockout = new System.Windows.Forms.ContextMenu();
        this.groupSearch = new System.Windows.Forms.GroupBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butProvHygenist = new OpenDental.UI.Button();
        this.butProvDentist = new OpenDental.UI.Button();
        this.butProvPick = new OpenDental.UI.Button();
        this.butRefresh = new OpenDental.UI.Button();
        this.listSearchResults = new System.Windows.Forms.ListBox();
        this.listProviders = new System.Windows.Forms.ListBox();
        this.butSearchClose = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.textAfter = new System.Windows.Forms.TextBox();
        this.label11 = new System.Windows.Forms.Label();
        this.radioBeforePM = new System.Windows.Forms.RadioButton();
        this.radioBeforeAM = new System.Windows.Forms.RadioButton();
        this.textBefore = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.radioAfterAM = new System.Windows.Forms.RadioButton();
        this.radioAfterPM = new System.Windows.Forms.RadioButton();
        this.dateSearch = new System.Windows.Forms.DateTimePicker();
        this.label9 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.butSearchCloseX = new System.Windows.Forms.Button();
        this.butSearchNext = new OpenDental.UI.Button();
        this.timerInfoBubble = new System.Windows.Forms.Timer(this.components);
        this.tabControl = new System.Windows.Forms.TabControl();
        this.tabWaiting = new System.Windows.Forms.TabPage();
        this.gridWaiting = new OpenDental.UI.ODGrid();
        this.tabSched = new System.Windows.Forms.TabPage();
        this.gridEmpSched = new OpenDental.UI.ODGrid();
        this.timerWaitingRoom = new System.Windows.Forms.Timer(this.components);
        this.timerTests = new System.Windows.Forms.Timer(this.components);
        this.panelMakeButtons = new System.Windows.Forms.Panel();
        this.butMakeAppt = new OpenDental.UI.Button();
        this.butFamRecall = new OpenDental.UI.Button();
        this.butMakeRecall = new OpenDental.UI.Button();
        this.butViewAppts = new OpenDental.UI.Button();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.panelArrows.SuspendLayout();
        this.panelSheet.SuspendLayout();
        this.panelAptInfo.SuspendLayout();
        this.panelCalendar.SuspendLayout();
        this.groupSearch.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.panel1.SuspendLayout();
        this.tabControl.SuspendLayout();
        this.tabWaiting.SuspendLayout();
        this.tabSched.SuspendLayout();
        this.panelMakeButtons.SuspendLayout();
        this.SuspendLayout();
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "Pat.gif");
        this.imageListMain.Images.SetKeyName(1, "print.gif");
        this.imageListMain.Images.SetKeyName(2, "apptLists.gif");
        //
        // Calendar2
        //
        this.Calendar2.Location = new System.Drawing.Point(0, 24);
        this.Calendar2.Name = "Calendar2";
        this.Calendar2.ScrollChange = 1;
        this.Calendar2.TabIndex = 23;
        this.Calendar2.DateSelected += new System.Windows.Forms.DateRangeEventHandler(this.Calendar2_DateSelected);
        //
        // labelDate
        //
        this.labelDate.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDate.Location = new System.Drawing.Point(2, 4);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(56, 16);
        this.labelDate.TabIndex = 24;
        this.labelDate.Text = "Wed";
        //
        // labelDate2
        //
        this.labelDate2.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDate2.Location = new System.Drawing.Point(46, 4);
        this.labelDate2.Name = "labelDate2";
        this.labelDate2.Size = new System.Drawing.Size(100, 20);
        this.labelDate2.TabIndex = 25;
        this.labelDate2.Text = "-  Oct 20";
        //
        // panelArrows
        //
        this.panelArrows.Controls.Add(this.butBackMonth);
        this.panelArrows.Controls.Add(this.butFwdMonth);
        this.panelArrows.Controls.Add(this.butBackWeek);
        this.panelArrows.Controls.Add(this.butFwdWeek);
        this.panelArrows.Controls.Add(this.butToday);
        this.panelArrows.Controls.Add(this.butBack);
        this.panelArrows.Controls.Add(this.butFwd);
        this.panelArrows.Location = new System.Drawing.Point(1, 189);
        this.panelArrows.Name = "panelArrows";
        this.panelArrows.Size = new System.Drawing.Size(217, 24);
        this.panelArrows.TabIndex = 32;
        //
        // butBackMonth
        //
        this.butBackMonth.setAdjustImageLocation(new System.Drawing.Point(-3, -1));
        this.butBackMonth.setAutosize(true);
        this.butBackMonth.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBackMonth.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBackMonth.setCornerRadius(4F);
        this.butBackMonth.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butBackMonth.Image = ((System.Drawing.Image)(resources.GetObject("butBackMonth.Image")));
        this.butBackMonth.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butBackMonth.Location = new System.Drawing.Point(1, 0);
        this.butBackMonth.Name = "butBackMonth";
        this.butBackMonth.Size = new System.Drawing.Size(32, 22);
        this.butBackMonth.TabIndex = 57;
        this.butBackMonth.Text = "M";
        this.butBackMonth.Click += new System.EventHandler(this.butBackMonth_Click);
        //
        // butFwdMonth
        //
        this.butFwdMonth.setAdjustImageLocation(new System.Drawing.Point(5, -1));
        this.butFwdMonth.setAutosize(false);
        this.butFwdMonth.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwdMonth.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwdMonth.setCornerRadius(4F);
        this.butFwdMonth.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butFwdMonth.Image = ((System.Drawing.Image)(resources.GetObject("butFwdMonth.Image")));
        this.butFwdMonth.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butFwdMonth.Location = new System.Drawing.Point(188, 0);
        this.butFwdMonth.Name = "butFwdMonth";
        this.butFwdMonth.Size = new System.Drawing.Size(29, 22);
        this.butFwdMonth.TabIndex = 56;
        this.butFwdMonth.Text = "M";
        this.butFwdMonth.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butFwdMonth.Click += new System.EventHandler(this.butFwdMonth_Click);
        //
        // butBackWeek
        //
        this.butBackWeek.setAdjustImageLocation(new System.Drawing.Point(-3, -1));
        this.butBackWeek.setAutosize(true);
        this.butBackWeek.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBackWeek.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBackWeek.setCornerRadius(4F);
        this.butBackWeek.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butBackWeek.Image = ((System.Drawing.Image)(resources.GetObject("butBackWeek.Image")));
        this.butBackWeek.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butBackWeek.Location = new System.Drawing.Point(33, 0);
        this.butBackWeek.Name = "butBackWeek";
        this.butBackWeek.Size = new System.Drawing.Size(33, 22);
        this.butBackWeek.TabIndex = 55;
        this.butBackWeek.Text = "W";
        this.butBackWeek.Click += new System.EventHandler(this.butBackWeek_Click);
        //
        // butFwdWeek
        //
        this.butFwdWeek.setAdjustImageLocation(new System.Drawing.Point(5, -1));
        this.butFwdWeek.setAutosize(false);
        this.butFwdWeek.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwdWeek.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwdWeek.setCornerRadius(4F);
        this.butFwdWeek.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butFwdWeek.Image = ((System.Drawing.Image)(resources.GetObject("butFwdWeek.Image")));
        this.butFwdWeek.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butFwdWeek.Location = new System.Drawing.Point(158, 0);
        this.butFwdWeek.Name = "butFwdWeek";
        this.butFwdWeek.Size = new System.Drawing.Size(30, 22);
        this.butFwdWeek.TabIndex = 54;
        this.butFwdWeek.Text = "W";
        this.butFwdWeek.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butFwdWeek.Click += new System.EventHandler(this.butFwdWeek_Click);
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(false);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butToday.Location = new System.Drawing.Point(85, 0);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(54, 22);
        this.butToday.TabIndex = 29;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // butBack
        //
        this.butBack.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBack.setAutosize(true);
        this.butBack.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBack.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBack.setCornerRadius(4F);
        this.butBack.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butBack.Image = ((System.Drawing.Image)(resources.GetObject("butBack.Image")));
        this.butBack.Location = new System.Drawing.Point(66, 0);
        this.butBack.Name = "butBack";
        this.butBack.Size = new System.Drawing.Size(19, 22);
        this.butBack.TabIndex = 51;
        this.butBack.Click += new System.EventHandler(this.butBack_Click);
        //
        // butFwd
        //
        this.butFwd.setAdjustImageLocation(new System.Drawing.Point(5, -1));
        this.butFwd.setAutosize(false);
        this.butFwd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFwd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFwd.setCornerRadius(4F);
        this.butFwd.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butFwd.Image = ((System.Drawing.Image)(resources.GetObject("butFwd.Image")));
        this.butFwd.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butFwd.Location = new System.Drawing.Point(139, 0);
        this.butFwd.Name = "butFwd";
        this.butFwd.Size = new System.Drawing.Size(19, 22);
        this.butFwd.TabIndex = 53;
        this.butFwd.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butFwd.Click += new System.EventHandler(this.butFwd_Click);
        //
        // panelSheet
        //
        this.panelSheet.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panelSheet.Controls.Add(this.vScrollBar1);
        this.panelSheet.Controls.Add(this.ContrApptSheet2);
        this.panelSheet.Location = new System.Drawing.Point(0, 17);
        this.panelSheet.Name = "panelSheet";
        this.panelSheet.Size = new System.Drawing.Size(235, 726);
        this.panelSheet.TabIndex = 44;
        this.panelSheet.Resize += new System.EventHandler(this.panelSheet_Resize);
        //
        // vScrollBar1
        //
        this.vScrollBar1.Dock = System.Windows.Forms.DockStyle.Right;
        this.vScrollBar1.Location = new System.Drawing.Point(216, 0);
        this.vScrollBar1.Name = "vScrollBar1";
        this.vScrollBar1.Size = new System.Drawing.Size(17, 724);
        this.vScrollBar1.TabIndex = 23;
        this.vScrollBar1.Scroll += new System.Windows.Forms.ScrollEventHandler(this.vScrollBar1_Scroll);
        //
        // ContrApptSheet2
        //
        this.ContrApptSheet2.Location = new System.Drawing.Point(2, -550);
        this.ContrApptSheet2.Name = "ContrApptSheet2";
        this.ContrApptSheet2.Size = new System.Drawing.Size(60, 1728);
        this.ContrApptSheet2.TabIndex = 22;
        this.ContrApptSheet2.DoubleClick += new System.EventHandler(this.ContrApptSheet2_DoubleClick);
        this.ContrApptSheet2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.ContrApptSheet2_MouseDown);
        this.ContrApptSheet2.MouseLeave += new System.EventHandler(this.ContrApptSheet2_MouseLeave);
        this.ContrApptSheet2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.ContrApptSheet2_MouseMove);
        this.ContrApptSheet2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.ContrApptSheet2_MouseUp);
        //
        // panelAptInfo
        //
        this.panelAptInfo.Controls.Add(this.listConfirmed);
        this.panelAptInfo.Controls.Add(this.butComplete);
        this.panelAptInfo.Controls.Add(this.butUnsched);
        this.panelAptInfo.Controls.Add(this.butDelete);
        this.panelAptInfo.Controls.Add(this.butBreak);
        this.panelAptInfo.Location = new System.Drawing.Point(665, 404);
        this.panelAptInfo.Name = "panelAptInfo";
        this.panelAptInfo.Size = new System.Drawing.Size(107, 116);
        this.panelAptInfo.TabIndex = 45;
        //
        // listConfirmed
        //
        this.listConfirmed.IntegralHeight = false;
        this.listConfirmed.Location = new System.Drawing.Point(31, 2);
        this.listConfirmed.Name = "listConfirmed";
        this.listConfirmed.Size = new System.Drawing.Size(73, 111);
        this.listConfirmed.TabIndex = 75;
        this.listConfirmed.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listConfirmed_MouseDown);
        //
        // butComplete
        //
        this.butComplete.BackColor = System.Drawing.SystemColors.Control;
        this.butComplete.Image = ((System.Drawing.Image)(resources.GetObject("butComplete.Image")));
        this.butComplete.ImageAlign = System.Drawing.ContentAlignment.BottomRight;
        this.butComplete.Location = new System.Drawing.Point(2, 57);
        this.butComplete.Name = "butComplete";
        this.butComplete.Size = new System.Drawing.Size(28, 28);
        this.butComplete.TabIndex = 69;
        this.butComplete.TabStop = false;
        this.butComplete.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butComplete.UseVisualStyleBackColor = false;
        this.butComplete.Click += new System.EventHandler(this.butComplete_Click);
        //
        // butUnsched
        //
        this.butUnsched.BackColor = System.Drawing.SystemColors.Control;
        this.butUnsched.Image = ((System.Drawing.Image)(resources.GetObject("butUnsched.Image")));
        this.butUnsched.ImageAlign = System.Drawing.ContentAlignment.BottomRight;
        this.butUnsched.Location = new System.Drawing.Point(2, 1);
        this.butUnsched.Name = "butUnsched";
        this.butUnsched.Size = new System.Drawing.Size(28, 28);
        this.butUnsched.TabIndex = 68;
        this.butUnsched.TabStop = false;
        this.butUnsched.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUnsched.UseVisualStyleBackColor = false;
        this.butUnsched.Click += new System.EventHandler(this.butUnsched_Click);
        //
        // butDelete
        //
        this.butDelete.BackColor = System.Drawing.SystemColors.Control;
        this.butDelete.Image = ((System.Drawing.Image)(resources.GetObject("butDelete.Image")));
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.BottomRight;
        this.butDelete.Location = new System.Drawing.Point(2, 85);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(28, 28);
        this.butDelete.TabIndex = 66;
        this.butDelete.TabStop = false;
        this.butDelete.UseVisualStyleBackColor = false;
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butBreak
        //
        this.butBreak.BackColor = System.Drawing.SystemColors.Control;
        this.butBreak.Image = ((System.Drawing.Image)(resources.GetObject("butBreak.Image")));
        this.butBreak.ImageAlign = System.Drawing.ContentAlignment.BottomRight;
        this.butBreak.Location = new System.Drawing.Point(2, 29);
        this.butBreak.Name = "butBreak";
        this.butBreak.Size = new System.Drawing.Size(28, 28);
        this.butBreak.TabIndex = 65;
        this.butBreak.TabStop = false;
        this.butBreak.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butBreak.UseVisualStyleBackColor = false;
        this.butBreak.Click += new System.EventHandler(this.butBreak_Click);
        //
        // panelCalendar
        //
        this.panelCalendar.Controls.Add(this.radioWeek);
        this.panelCalendar.Controls.Add(this.panelArrows);
        this.panelCalendar.Controls.Add(this.radioDay);
        this.panelCalendar.Controls.Add(this.butGraph);
        this.panelCalendar.Controls.Add(this.butMonth);
        this.panelCalendar.Controls.Add(this.pinBoard);
        this.panelCalendar.Controls.Add(this.butLab);
        this.panelCalendar.Controls.Add(this.butSearch);
        this.panelCalendar.Controls.Add(this.textProduction);
        this.panelCalendar.Controls.Add(this.label7);
        this.panelCalendar.Controls.Add(this.textLab);
        this.panelCalendar.Controls.Add(this.comboView);
        this.panelCalendar.Controls.Add(this.label2);
        this.panelCalendar.Controls.Add(this.butClearPin);
        this.panelCalendar.Controls.Add(this.Calendar2);
        this.panelCalendar.Controls.Add(this.labelDate);
        this.panelCalendar.Controls.Add(this.labelDate2);
        this.panelCalendar.Location = new System.Drawing.Point(665, 28);
        this.panelCalendar.Name = "panelCalendar";
        this.panelCalendar.Size = new System.Drawing.Size(219, 375);
        this.panelCalendar.TabIndex = 46;
        //
        // radioWeek
        //
        this.radioWeek.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioWeek.Location = new System.Drawing.Point(43, 238);
        this.radioWeek.Name = "radioWeek";
        this.radioWeek.Size = new System.Drawing.Size(68, 16);
        this.radioWeek.TabIndex = 92;
        this.radioWeek.Text = "Week";
        this.radioWeek.Click += new System.EventHandler(this.radioWeek_Click);
        //
        // radioDay
        //
        this.radioDay.Checked = true;
        this.radioDay.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioDay.Location = new System.Drawing.Point(43, 218);
        this.radioDay.Name = "radioDay";
        this.radioDay.Size = new System.Drawing.Size(68, 16);
        this.radioDay.TabIndex = 91;
        this.radioDay.TabStop = true;
        this.radioDay.Text = "Day";
        this.radioDay.Click += new System.EventHandler(this.radioDay_Click);
        //
        // butGraph
        //
        this.butGraph.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGraph.setAutosize(true);
        this.butGraph.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGraph.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGraph.setCornerRadius(4F);
        this.butGraph.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butGraph.Location = new System.Drawing.Point(3, 309);
        this.butGraph.Name = "butGraph";
        this.butGraph.Size = new System.Drawing.Size(42, 24);
        this.butGraph.TabIndex = 78;
        this.butGraph.TabStop = false;
        this.butGraph.Text = "Emp";
        this.butGraph.Visible = false;
        this.butGraph.Click += new System.EventHandler(this.butGraph_Click);
        //
        // butMonth
        //
        this.butMonth.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMonth.setAutosize(false);
        this.butMonth.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMonth.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMonth.setCornerRadius(4F);
        this.butMonth.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butMonth.Location = new System.Drawing.Point(152, 1);
        this.butMonth.Name = "butMonth";
        this.butMonth.Size = new System.Drawing.Size(65, 22);
        this.butMonth.TabIndex = 79;
        this.butMonth.Text = "Month";
        this.butMonth.Visible = false;
        this.butMonth.Click += new System.EventHandler(this.butMonth_Click);
        //
        // pinBoard
        //
        this.pinBoard.Location = new System.Drawing.Point(119, 213);
        this.pinBoard.Name = "pinBoard";
        this.pinBoard.setSelectedIndex(-1);
        this.pinBoard.Size = new System.Drawing.Size(99, 96);
        this.pinBoard.TabIndex = 78;
        this.pinBoard.Text = "pinBoard";
        this.pinBoard.SelectedIndexChanged += new System.EventHandler(this.pinBoard_SelectedIndexChanged);
        this.pinBoard.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pinBoard_MouseDown);
        this.pinBoard.MouseMove += new System.Windows.Forms.MouseEventHandler(this.pinBoard_MouseMove);
        this.pinBoard.MouseUp += new System.Windows.Forms.MouseEventHandler(this.pinBoard_MouseUp);
        //
        // butLab
        //
        this.butLab.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLab.setAutosize(true);
        this.butLab.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLab.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLab.setCornerRadius(4F);
        this.butLab.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLab.Location = new System.Drawing.Point(3, 333);
        this.butLab.Name = "butLab";
        this.butLab.Size = new System.Drawing.Size(79, 21);
        this.butLab.TabIndex = 77;
        this.butLab.Text = "LabCases";
        this.butLab.Click += new System.EventHandler(this.butLab_Click);
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSearch.Location = new System.Drawing.Point(43, 285);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(75, 24);
        this.butSearch.TabIndex = 40;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // textProduction
        //
        this.textProduction.BackColor = System.Drawing.Color.White;
        this.textProduction.Location = new System.Drawing.Point(85, 353);
        this.textProduction.Name = "textProduction";
        this.textProduction.ReadOnly = true;
        this.textProduction.Size = new System.Drawing.Size(133, 20);
        this.textProduction.TabIndex = 38;
        this.textProduction.Text = "$100";
        this.textProduction.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(16, 357);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(68, 15);
        this.label7.TabIndex = 39;
        this.label7.Text = "Daily Prod";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLab
        //
        this.textLab.BackColor = System.Drawing.Color.White;
        this.textLab.Location = new System.Drawing.Point(85, 333);
        this.textLab.Name = "textLab";
        this.textLab.ReadOnly = true;
        this.textLab.Size = new System.Drawing.Size(133, 20);
        this.textLab.TabIndex = 36;
        this.textLab.Text = "All Received";
        this.textLab.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // comboView
        //
        this.comboView.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboView.Location = new System.Drawing.Point(85, 312);
        this.comboView.MaxDropDownItems = 30;
        this.comboView.Name = "comboView";
        this.comboView.Size = new System.Drawing.Size(133, 21);
        this.comboView.TabIndex = 35;
        this.comboView.SelectionChangeCommitted += new System.EventHandler(this.comboView_SelectionChangeCommitted);
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(17, 314);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(66, 16);
        this.label2.TabIndex = 34;
        this.label2.Text = "View";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // butClearPin
        //
        this.butClearPin.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearPin.setAutosize(true);
        this.butClearPin.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearPin.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearPin.setCornerRadius(4F);
        this.butClearPin.Image = ((System.Drawing.Image)(resources.GetObject("butClearPin.Image")));
        this.butClearPin.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClearPin.Location = new System.Drawing.Point(43, 260);
        this.butClearPin.Name = "butClearPin";
        this.butClearPin.Size = new System.Drawing.Size(75, 24);
        this.butClearPin.TabIndex = 33;
        this.butClearPin.Text = "Clear";
        this.butClearPin.Click += new System.EventHandler(this.butClearPin_Click);
        //
        // panelOps
        //
        this.panelOps.Location = new System.Drawing.Point(0, 0);
        this.panelOps.Name = "panelOps";
        this.panelOps.Size = new System.Drawing.Size(676, 17);
        this.panelOps.TabIndex = 48;
        //
        // toolTip1
        //
        this.toolTip1.AutoPopDelay = 5000;
        this.toolTip1.InitialDelay = 100;
        this.toolTip1.ReshowDelay = 100;
        //
        // pd2
        //
        this.pd2.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.pd2_PrintPage);
        //
        // groupSearch
        //
        this.groupSearch.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(192)))));
        this.groupSearch.Controls.Add(this.groupBox1);
        this.groupSearch.Controls.Add(this.butProvPick);
        this.groupSearch.Controls.Add(this.butRefresh);
        this.groupSearch.Controls.Add(this.listSearchResults);
        this.groupSearch.Controls.Add(this.listProviders);
        this.groupSearch.Controls.Add(this.butSearchClose);
        this.groupSearch.Controls.Add(this.groupBox2);
        this.groupSearch.Controls.Add(this.label8);
        this.groupSearch.Controls.Add(this.butSearchCloseX);
        this.groupSearch.Controls.Add(this.butSearchNext);
        this.groupSearch.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupSearch.Location = new System.Drawing.Point(380, 340);
        this.groupSearch.Name = "groupSearch";
        this.groupSearch.Size = new System.Drawing.Size(219, 366);
        this.groupSearch.TabIndex = 74;
        this.groupSearch.TabStop = false;
        this.groupSearch.Text = "Search For Opening";
        this.groupSearch.Visible = false;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butProvHygenist);
        this.groupBox1.Controls.Add(this.butProvDentist);
        this.groupBox1.Location = new System.Drawing.Point(130, 253);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(85, 63);
        this.groupBox1.TabIndex = 89;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Search by";
        //
        // butProvHygenist
        //
        this.butProvHygenist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvHygenist.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butProvHygenist.setAutosize(true);
        this.butProvHygenist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvHygenist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvHygenist.setCornerRadius(4F);
        this.butProvHygenist.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvHygenist.Location = new System.Drawing.Point(6, 37);
        this.butProvHygenist.Name = "butProvHygenist";
        this.butProvHygenist.Size = new System.Drawing.Size(73, 22);
        this.butProvHygenist.TabIndex = 92;
        this.butProvHygenist.Text = "Hygienists";
        this.butProvHygenist.Click += new System.EventHandler(this.butProvHygenist_Click);
        //
        // butProvDentist
        //
        this.butProvDentist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvDentist.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butProvDentist.setAutosize(true);
        this.butProvDentist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvDentist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvDentist.setCornerRadius(4F);
        this.butProvDentist.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvDentist.Location = new System.Drawing.Point(6, 14);
        this.butProvDentist.Name = "butProvDentist";
        this.butProvDentist.Size = new System.Drawing.Size(73, 22);
        this.butProvDentist.TabIndex = 91;
        this.butProvDentist.Text = "Dentists";
        this.butProvDentist.Click += new System.EventHandler(this.butProvDentist_Click);
        //
        // butProvPick
        //
        this.butProvPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butProvPick.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butProvPick.setAutosize(true);
        this.butProvPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butProvPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butProvPick.setCornerRadius(4F);
        this.butProvPick.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butProvPick.Location = new System.Drawing.Point(6, 340);
        this.butProvPick.Name = "butProvPick";
        this.butProvPick.Size = new System.Drawing.Size(82, 22);
        this.butProvPick.TabIndex = 88;
        this.butProvPick.Text = "Providers...";
        this.butProvPick.Click += new System.EventHandler(this.butProvPick_Click);
        //
        // butRefresh
        //
        this.butRefresh.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRefresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butRefresh.setAutosize(true);
        this.butRefresh.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRefresh.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRefresh.setCornerRadius(4F);
        this.butRefresh.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butRefresh.Location = new System.Drawing.Point(153, 318);
        this.butRefresh.Name = "butRefresh";
        this.butRefresh.Size = new System.Drawing.Size(62, 22);
        this.butRefresh.TabIndex = 88;
        this.butRefresh.Text = "Search";
        this.butRefresh.Click += new System.EventHandler(this.butRefresh_Click);
        //
        // listSearchResults
        //
        this.listSearchResults.IntegralHeight = false;
        this.listSearchResults.Location = new System.Drawing.Point(6, 32);
        this.listSearchResults.Name = "listSearchResults";
        this.listSearchResults.Size = new System.Drawing.Size(193, 134);
        this.listSearchResults.TabIndex = 87;
        this.listSearchResults.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listSearchResults_MouseDown);
        //
        // listProviders
        //
        this.listProviders.Location = new System.Drawing.Point(6, 269);
        this.listProviders.Name = "listProviders";
        this.listProviders.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listProviders.Size = new System.Drawing.Size(118, 69);
        this.listProviders.TabIndex = 86;
        //
        // butSearchClose
        //
        this.butSearchClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearchClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butSearchClose.setAutosize(true);
        this.butSearchClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearchClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearchClose.setCornerRadius(4F);
        this.butSearchClose.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSearchClose.Location = new System.Drawing.Point(153, 342);
        this.butSearchClose.Name = "butSearchClose";
        this.butSearchClose.Size = new System.Drawing.Size(62, 22);
        this.butSearchClose.TabIndex = 85;
        this.butSearchClose.Text = "Close";
        this.butSearchClose.Click += new System.EventHandler(this.butSearchClose_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.textAfter);
        this.groupBox2.Controls.Add(this.label11);
        this.groupBox2.Controls.Add(this.radioBeforePM);
        this.groupBox2.Controls.Add(this.radioBeforeAM);
        this.groupBox2.Controls.Add(this.textBefore);
        this.groupBox2.Controls.Add(this.label10);
        this.groupBox2.Controls.Add(this.panel1);
        this.groupBox2.Controls.Add(this.dateSearch);
        this.groupBox2.Controls.Add(this.label9);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(6, 168);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(193, 84);
        this.groupBox2.TabIndex = 84;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Date/Time Restrictions";
        //
        // textAfter
        //
        this.textAfter.Location = new System.Drawing.Point(57, 60);
        this.textAfter.Name = "textAfter";
        this.textAfter.Size = new System.Drawing.Size(44, 20);
        this.textAfter.TabIndex = 88;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(1, 62);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(53, 16);
        this.label11.TabIndex = 87;
        this.label11.Text = "After";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // radioBeforePM
        //
        this.radioBeforePM.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioBeforePM.Location = new System.Drawing.Point(151, 41);
        this.radioBeforePM.Name = "radioBeforePM";
        this.radioBeforePM.Size = new System.Drawing.Size(37, 15);
        this.radioBeforePM.TabIndex = 86;
        this.radioBeforePM.Text = "pm";
        //
        // radioBeforeAM
        //
        this.radioBeforeAM.Checked = true;
        this.radioBeforeAM.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioBeforeAM.Location = new System.Drawing.Point(108, 41);
        this.radioBeforeAM.Name = "radioBeforeAM";
        this.radioBeforeAM.Size = new System.Drawing.Size(37, 15);
        this.radioBeforeAM.TabIndex = 85;
        this.radioBeforeAM.TabStop = true;
        this.radioBeforeAM.Text = "am";
        //
        // textBefore
        //
        this.textBefore.Location = new System.Drawing.Point(57, 38);
        this.textBefore.Name = "textBefore";
        this.textBefore.Size = new System.Drawing.Size(44, 20);
        this.textBefore.TabIndex = 84;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(1, 40);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(53, 16);
        this.label10.TabIndex = 83;
        this.label10.Text = "Before";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panel1
        //
        this.panel1.Controls.Add(this.radioAfterAM);
        this.panel1.Controls.Add(this.radioAfterPM);
        this.panel1.Location = new System.Drawing.Point(105, 60);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(84, 20);
        this.panel1.TabIndex = 86;
        //
        // radioAfterAM
        //
        this.radioAfterAM.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioAfterAM.Location = new System.Drawing.Point(3, 2);
        this.radioAfterAM.Name = "radioAfterAM";
        this.radioAfterAM.Size = new System.Drawing.Size(37, 15);
        this.radioAfterAM.TabIndex = 89;
        this.radioAfterAM.Text = "am";
        //
        // radioAfterPM
        //
        this.radioAfterPM.Checked = true;
        this.radioAfterPM.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioAfterPM.Location = new System.Drawing.Point(46, 2);
        this.radioAfterPM.Name = "radioAfterPM";
        this.radioAfterPM.Size = new System.Drawing.Size(36, 15);
        this.radioAfterPM.TabIndex = 90;
        this.radioAfterPM.TabStop = true;
        this.radioAfterPM.Text = "pm";
        //
        // dateSearch
        //
        this.dateSearch.Format = System.Windows.Forms.DateTimePickerFormat.Short;
        this.dateSearch.Location = new System.Drawing.Point(57, 16);
        this.dateSearch.Name = "dateSearch";
        this.dateSearch.Size = new System.Drawing.Size(130, 20);
        this.dateSearch.TabIndex = 90;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(1, 19);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(54, 16);
        this.label9.TabIndex = 89;
        this.label9.Text = "After";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(6, 251);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(92, 16);
        this.label8.TabIndex = 80;
        this.label8.Text = "Providers";
        this.label8.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butSearchCloseX
        //
        this.butSearchCloseX.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
        this.butSearchCloseX.ForeColor = System.Drawing.SystemColors.Control;
        this.butSearchCloseX.Image = ((System.Drawing.Image)(resources.GetObject("butSearchCloseX.Image")));
        this.butSearchCloseX.Location = new System.Drawing.Point(185, 7);
        this.butSearchCloseX.Name = "butSearchCloseX";
        this.butSearchCloseX.Size = new System.Drawing.Size(16, 16);
        this.butSearchCloseX.TabIndex = 0;
        this.butSearchCloseX.Click += new System.EventHandler(this.butSearchCloseX_Click);
        //
        // butSearchNext
        //
        this.butSearchNext.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearchNext.setAutosize(true);
        this.butSearchNext.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearchNext.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearchNext.setCornerRadius(4F);
        this.butSearchNext.Image = ((System.Drawing.Image)(resources.GetObject("butSearchNext.Image")));
        this.butSearchNext.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.butSearchNext.Location = new System.Drawing.Point(111, 9);
        this.butSearchNext.Name = "butSearchNext";
        this.butSearchNext.Size = new System.Drawing.Size(71, 22);
        this.butSearchNext.TabIndex = 77;
        this.butSearchNext.Text = "More";
        this.butSearchNext.Click += new System.EventHandler(this.butSearchMore_Click);
        //
        // timerInfoBubble
        //
        this.timerInfoBubble.Interval = 300;
        this.timerInfoBubble.Tick += new System.EventHandler(this.timerInfoBubble_Tick);
        //
        // tabControl
        //
        this.tabControl.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.tabControl.Controls.Add(this.tabWaiting);
        this.tabControl.Controls.Add(this.tabSched);
        this.tabControl.Location = new System.Drawing.Point(665, 521);
        this.tabControl.Name = "tabControl";
        this.tabControl.SelectedIndex = 0;
        this.tabControl.Size = new System.Drawing.Size(219, 187);
        this.tabControl.TabIndex = 78;
        //
        // tabWaiting
        //
        this.tabWaiting.Controls.Add(this.gridWaiting);
        this.tabWaiting.Location = new System.Drawing.Point(4, 22);
        this.tabWaiting.Name = "tabWaiting";
        this.tabWaiting.Padding = new System.Windows.Forms.Padding(3);
        this.tabWaiting.Size = new System.Drawing.Size(211, 161);
        this.tabWaiting.TabIndex = 0;
        this.tabWaiting.Text = "Waiting";
        this.tabWaiting.UseVisualStyleBackColor = true;
        //
        // gridWaiting
        //
        this.gridWaiting.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridWaiting.setHScrollVisible(false);
        this.gridWaiting.Location = new System.Drawing.Point(0, 0);
        this.gridWaiting.Margin = new System.Windows.Forms.Padding(0);
        this.gridWaiting.Name = "gridWaiting";
        this.gridWaiting.setScrollValue(0);
        this.gridWaiting.Size = new System.Drawing.Size(211, 161);
        this.gridWaiting.TabIndex = 78;
        this.gridWaiting.setTitle("Waiting Room");
        this.gridWaiting.setTranslationName("TableApptWaiting");
        //
        // tabSched
        //
        this.tabSched.Controls.Add(this.gridEmpSched);
        this.tabSched.Location = new System.Drawing.Point(4, 22);
        this.tabSched.Name = "tabSched";
        this.tabSched.Padding = new System.Windows.Forms.Padding(3);
        this.tabSched.Size = new System.Drawing.Size(211, 161);
        this.tabSched.TabIndex = 1;
        this.tabSched.Text = "Emp";
        this.tabSched.UseVisualStyleBackColor = true;
        //
        // gridEmpSched
        //
        this.gridEmpSched.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridEmpSched.setHScrollVisible(true);
        this.gridEmpSched.Location = new System.Drawing.Point(0, 0);
        this.gridEmpSched.Margin = new System.Windows.Forms.Padding(0);
        this.gridEmpSched.Name = "gridEmpSched";
        this.gridEmpSched.setScrollValue(0);
        this.gridEmpSched.Size = new System.Drawing.Size(211, 161);
        this.gridEmpSched.TabIndex = 77;
        this.gridEmpSched.setTitle("Employee Schedules");
        this.gridEmpSched.setTranslationName("TableApptEmpSched");
        this.gridEmpSched.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridEmpSched.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEmpSched_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridEmpSched.DoubleClick += new System.EventHandler(this.gridEmpSched_DoubleClick);
        //
        // timerWaitingRoom
        //
        this.timerWaitingRoom.Enabled = true;
        this.timerWaitingRoom.Interval = 1000;
        this.timerWaitingRoom.Tick += new System.EventHandler(this.timerWaitingRoom_Tick);
        //
        // timerTests
        //
        this.timerTests.Tick += new System.EventHandler(this.timerTests_Tick);
        //
        // panelMakeButtons
        //
        this.panelMakeButtons.Controls.Add(this.butMakeAppt);
        this.panelMakeButtons.Controls.Add(this.butFamRecall);
        this.panelMakeButtons.Controls.Add(this.butMakeRecall);
        this.panelMakeButtons.Controls.Add(this.butViewAppts);
        this.panelMakeButtons.Location = new System.Drawing.Point(772, 404);
        this.panelMakeButtons.Name = "panelMakeButtons";
        this.panelMakeButtons.Size = new System.Drawing.Size(112, 116);
        this.panelMakeButtons.TabIndex = 82;
        //
        // butMakeAppt
        //
        this.butMakeAppt.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMakeAppt.setAutosize(true);
        this.butMakeAppt.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMakeAppt.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMakeAppt.setCornerRadius(4F);
        this.butMakeAppt.Image = ((System.Drawing.Image)(resources.GetObject("butMakeAppt.Image")));
        this.butMakeAppt.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butMakeAppt.Location = new System.Drawing.Point(5, 5);
        this.butMakeAppt.Name = "butMakeAppt";
        this.butMakeAppt.Size = new System.Drawing.Size(103, 24);
        this.butMakeAppt.TabIndex = 76;
        this.butMakeAppt.TabStop = false;
        this.butMakeAppt.Text = "Make Appt";
        this.butMakeAppt.Click += new System.EventHandler(this.butMakeAppt_Click);
        //
        // butFamRecall
        //
        this.butFamRecall.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butFamRecall.setAutosize(true);
        this.butFamRecall.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butFamRecall.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butFamRecall.setCornerRadius(4F);
        this.butFamRecall.Image = Resources.getbutRecall();
        this.butFamRecall.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butFamRecall.Location = new System.Drawing.Point(5, 57);
        this.butFamRecall.Name = "butFamRecall";
        this.butFamRecall.Size = new System.Drawing.Size(103, 24);
        this.butFamRecall.TabIndex = 81;
        this.butFamRecall.TabStop = false;
        this.butFamRecall.Text = "Fam Recall";
        this.butFamRecall.Click += new System.EventHandler(this.butFamRecall_Click);
        //
        // butMakeRecall
        //
        this.butMakeRecall.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMakeRecall.setAutosize(true);
        this.butMakeRecall.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMakeRecall.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMakeRecall.setCornerRadius(4F);
        this.butMakeRecall.Image = Resources.getbutRecall();
        this.butMakeRecall.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butMakeRecall.Location = new System.Drawing.Point(5, 31);
        this.butMakeRecall.Name = "butMakeRecall";
        this.butMakeRecall.Size = new System.Drawing.Size(103, 24);
        this.butMakeRecall.TabIndex = 79;
        this.butMakeRecall.TabStop = false;
        this.butMakeRecall.Text = "Make Recall";
        this.butMakeRecall.Click += new System.EventHandler(this.butMakeRecall_Click);
        //
        // butViewAppts
        //
        this.butViewAppts.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butViewAppts.setAutosize(true);
        this.butViewAppts.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butViewAppts.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butViewAppts.setCornerRadius(4F);
        this.butViewAppts.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butViewAppts.Location = new System.Drawing.Point(5, 83);
        this.butViewAppts.Name = "butViewAppts";
        this.butViewAppts.Size = new System.Drawing.Size(103, 24);
        this.butViewAppts.TabIndex = 80;
        this.butViewAppts.TabStop = false;
        this.butViewAppts.Text = "View Pat Appts";
        this.butViewAppts.Click += new System.EventHandler(this.butViewAppts_Click);
        //
        // ToolBarMain
        //
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(680, 2);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(203, 25);
        this.ToolBarMain.TabIndex = 73;
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
        // ContrAppt
        //
        this.Controls.Add(this.groupSearch);
        this.Controls.Add(this.panelMakeButtons);
        this.Controls.Add(this.tabControl);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.panelOps);
        this.Controls.Add(this.panelCalendar);
        this.Controls.Add(this.panelAptInfo);
        this.Controls.Add(this.panelSheet);
        this.Name = "ContrAppt";
        this.Size = new System.Drawing.Size(939, 708);
        this.Load += new System.EventHandler(this.ContrAppt_Load);
        this.Layout += new System.Windows.Forms.LayoutEventHandler(this.ContrAppt_Layout);
        this.Resize += new System.EventHandler(this.ContrAppt_Resize);
        this.panelArrows.ResumeLayout(false);
        this.panelSheet.ResumeLayout(false);
        this.panelAptInfo.ResumeLayout(false);
        this.panelCalendar.ResumeLayout(false);
        this.panelCalendar.PerformLayout();
        this.groupSearch.ResumeLayout(false);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.panel1.ResumeLayout(false);
        this.tabControl.ResumeLayout(false);
        this.tabWaiting.ResumeLayout(false);
        this.tabSched.ResumeLayout(false);
        this.panelMakeButtons.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void contrApptSheet2_MouseWheel(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        int max = vScrollBar1.Maximum - vScrollBar1.LargeChange;
        //panelTable.Height-panelScroll.Height+3;
        int newScrollVal = vScrollBar1.Value - (int)(e.Delta / 4);
        if (newScrollVal > max)
        {
            vScrollBar1.Value = max;
        }
        else if (newScrollVal < vScrollBar1.Minimum)
        {
            vScrollBar1.Value = vScrollBar1.Minimum;
        }
        else
        {
            vScrollBar1.Value = newScrollVal;
        }  
        ContrApptSheet2.Location = new Point(0, -vScrollBar1.Value);
    }

    /**
    * Overload used when jumping here from another module, and you want to place appointments on the pinboard.
    */
    public void moduleSelected(long patNum, List<long> pinAptNums) throws Exception {
        moduleSelected(patNum);
        SendToPinBoard(pinAptNums);
    }

    /**
    * 
    */
    public void moduleSelected(long patNum) throws Exception {
        refreshModuleDataPatient(patNum);
        refreshModuleDataPeriod();
        layoutScrollOpProv();
        refreshModuleScreenPatient();
        refreshModuleScreenPeriod();
        Plugins.hookAddCode(this,"ContrAppt.ModuleSelected_end",patNum);
    }

    /**
    * Refreshes everything except the patient info.
    */
    public void refreshPeriod() throws Exception {
        refreshModuleDataPeriod();
        layoutScrollOpProv();
        refreshModuleScreenPeriod();
    }

    /**
    * Fills PatCur from the database unless the patnum has not changed.
    */
    private void refreshModuleDataPatient(long patNum) throws Exception {
        if (patNum == 0)
        {
            PatCur = null;
            return ;
        }
         
        //if(PatCur !=null && PatCur.PatNum==patNum) {//if patient has not changed
        //  return;//don't do anything
        //}
        PatCur = Patients.getPat(patNum);
    }

    /**
    * Gets the entire DS for appointments and schedules.  Gets op and prov indices for current view.
    */
    private void refreshModuleDataPeriod() throws Exception {
        bubbleAptNum = 0;
        DateTime startDate = new DateTime();
        DateTime endDate = new DateTime();
        if (ApptDrawing.IsWeeklyView)
        {
            startDate = WeekStartDate;
            endDate = WeekEndDate;
        }
        else
        {
            startDate = AppointmentL.DateSelected;
            endDate = AppointmentL.DateSelected;
        } 
        if (startDate.Year < 1880 || endDate.Year < 1880)
        {
            return ;
        }
         
        //Calendar2.SetSelectionRange(startDate,endDate);
        if (PatCur == null)
        {
            //there cannot be a selected appointment if no patient is loaded.
            ContrApptSingle.SelectedAptNum = -1;
        }
         
        //fixes a minor bug.
        DS = Appointments.refreshPeriod(startDate,endDate);
        LastTimeDataRetrieved = DateTime.Now;
        SchedListPeriod = Schedules.ConvertTableToList(DS.Tables["Schedule"]);
        ApptViewItemL.GetForCurView(comboView.SelectedIndex - 1, ApptDrawing.IsWeeklyView, SchedListPeriod);
    }

    /**
    * Called from both ModuleSelected and from RefreshPeriod.  Do not call it from any event like Layout.  This also clears listConfirmed.
    */
    public void layoutScrollOpProv() throws Exception {
        //ModuleSelectedOld(int patNum){
        //the scrollbar logic cannot be moved to someplace where it will be activated while working in apptbook
        //RefreshVisops();//forces reset after changing databases
        if (DefC.getShort() != null)
        {
            ApptViewItemL.GetForCurView(comboView.SelectedIndex - 1, ApptDrawing.IsWeeklyView, SchedListPeriod);
            //refreshes visops,etc
            ApptDrawing.ApptSheetWidth = panelSheet.Width - vScrollBar1.Width;
            ApptDrawing.computeColWidth(0);
            ContrApptSheet2.Height = ApptDrawing.LineH * 24 * ApptDrawing.RowsPerHr;
        }
         
        this.SuspendLayout();
        vScrollBar1.Enabled = true;
        vScrollBar1.Minimum = 0;
        vScrollBar1.LargeChange = 12 * ApptDrawing.LineH;
        //12 rows
        vScrollBar1.Maximum = ContrApptSheet2.Height - panelSheet.Height + vScrollBar1.LargeChange;
        if (vScrollBar1.Maximum < 0)
        {
            vScrollBar1.Maximum = 0;
        }
         
        //Max is set again in Resize event
        vScrollBar1.SmallChange = 6 * ApptDrawing.LineH;
        //6 rows
        if (vScrollBar1.Value == 0)
        {
            //ApptViewC.List seems to already be not null by this point.
            int rowsPerHr = 60 / ApptDrawing.MinPerIncr * ApptDrawing.RowsPerIncr;
            //use the row setting from the first view.
            if (ApptViewC.getList().Length > 0)
            {
                rowsPerHr = 60 / ApptDrawing.MinPerIncr * ApptViewC.getList()[0].RowsPerIncr;
            }
             
            if (8 * rowsPerHr * ApptDrawing.LineH < vScrollBar1.Maximum - vScrollBar1.LargeChange)
            {
                vScrollBar1.Value = 8 * rowsPerHr * ApptDrawing.LineH;
            }
             
        }
         
        //8am
        if (vScrollBar1.Value > vScrollBar1.Maximum - vScrollBar1.LargeChange)
        {
            if (vScrollBar1.Maximum - vScrollBar1.LargeChange >= 0)
            {
                //but don't allow setting negative number
                vScrollBar1.Value = vScrollBar1.Maximum - vScrollBar1.LargeChange;
            }
             
        }
         
        ContrApptSheet2.Location = new Point(0, -vScrollBar1.Value);
        toolTip1.RemoveAll();
        for (int i = panelOps.Controls.Count - 1;i >= 0;i--)
        {
            //without this line, the program becomes sluggish.
            if (panelOps.Controls[i] != null)
            {
                panelOps.Controls[i].Dispose();
            }
             
        }
        panelOps.Controls.Clear();
        for (int i = 0;i < ApptDrawing.ProvCount;i++)
        {
            Panel panProv = new Panel();
            panProv.BackColor = ApptDrawing.VisProvs[i].ProvColor;
            panProv.Location = new Point(2 + (int)ApptDrawing.TimeWidth + (int)ApptDrawing.ProvWidth * i, 0);
            panProv.Width = (int)ApptDrawing.ProvWidth;
            if (i == 0)
            {
                //just looks a little nicer:
                panProv.Location = new Point(panProv.Location.X - 1, panProv.Location.Y);
                panProv.Width = panProv.Width + 1;
            }
             
            panProv.Height = 18;
            panProv.BorderStyle = BorderStyle.Fixed3D;
            panProv.ForeColor = Color.DarkGray;
            panelOps.Controls.Add(panProv);
            toolTip1.SetToolTip(panProv, ApptDrawing.VisProvs[i].Abbr);
        }
        Operatory curOp;
        if (ApptDrawing.IsWeeklyView)
        {
            for (int i = 0;i < ApptDrawing.NumOfWeekDaysToDisplay;i++)
            {
                Panel panOpName = new Panel();
                Label labOpName = new Label();
                labOpName.Text = WeekStartDate.AddDays(i).ToString("dddd-d");
                panOpName.Location = new Point(2 + (int)ApptDrawing.TimeWidth + i * (int)ApptDrawing.ColDayWidth, 0);
                panOpName.Width = (int)ApptDrawing.ColDayWidth;
                panOpName.Height = 18;
                panOpName.BorderStyle = BorderStyle.Fixed3D;
                panOpName.ForeColor = Color.DarkGray;
                panOpName.MouseDown += new System.Windows.Forms.MouseEventHandler(panOpName_MouseDown);
                panOpName.Tag = i;
                //stores the day index
                //add label within panOpName
                labOpName.Location = new Point(0, -2);
                labOpName.Width = panOpName.Width;
                labOpName.Height = 18;
                labOpName.TextAlign = ContentAlignment.MiddleCenter;
                labOpName.ForeColor = Color.Black;
                labOpName.MouseDown += new System.Windows.Forms.MouseEventHandler(panOpName_MouseDown);
                labOpName.Tag = i;
                //stores the day index
                panOpName.Controls.Add(labOpName);
                panelOps.Controls.Add(panOpName);
            }
        }
        else
        {
            for (int i = 0;i < ApptDrawing.ColCount;i++)
            {
                Panel panOpName = new Panel();
                Label labOpName = new Label();
                curOp = ApptDrawing.VisOps[i];
                labOpName.Text = curOp.OpName;
                if (curOp.ProvDentist != 0 && !curOp.IsHygiene)
                {
                    panOpName.BackColor = Providers.getColor(curOp.ProvDentist);
                }
                else if (curOp.ProvHygienist != 0 && curOp.IsHygiene)
                {
                    panOpName.BackColor = Providers.getColor(curOp.ProvHygienist);
                }
                  
                float xPos = ApptDrawing.TimeWidth + ApptDrawing.ProvWidth * ApptDrawing.ProvCount + i * ApptDrawing.ColWidth;
                panOpName.Location = new Point(2 + (int)xPos, 0);
                panOpName.Width = (int)ApptDrawing.ColWidth;
                panOpName.Height = 18;
                panOpName.BorderStyle = BorderStyle.Fixed3D;
                panOpName.ForeColor = Color.DarkGray;
                //add label within panOpName
                labOpName.Location = new Point(0, -2);
                labOpName.Width = panOpName.Width;
                labOpName.Height = 18;
                labOpName.TextAlign = ContentAlignment.MiddleCenter;
                labOpName.ForeColor = Color.Black;
                panOpName.Controls.Add(labOpName);
                panelOps.Controls.Add(panOpName);
            }
        } 
        this.ResumeLayout();
        listConfirmed.Items.Clear();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()].Length;i++)
        {
            this.listConfirmed.Items.Add(DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].ItemValue);
        }
    }

    /**
    * Refreshes the appointment info panel to the right if an appointment is currently selected.
    */
    private void refreshModuleScreenPatient() throws Exception {
        if (PatCur == null)
        {
            panelMakeButtons.Enabled = false;
        }
        else
        {
            /*butMakeAppt.Enabled=false;
            				butMakeRecall.Enabled=false;
            				butFamRecall.Enabled=false;
            				butViewAppts.Enabled=false;*/
            panelMakeButtons.Enabled = true;
        } 
        if (ContrApptSingle.SelectedAptNum > 0)
        {
            butUnsched.Enabled = true;
            butBreak.Enabled = true;
            butComplete.Enabled = true;
            butDelete.Enabled = true;
        }
        else
        {
            butUnsched.Enabled = false;
            butBreak.Enabled = false;
            butComplete.Enabled = false;
            butDelete.Enabled = false;
        } 
        if (panelAptInfo.Enabled && DS != null)
        {
            long aptconfirmed = 0;
            String tableAptNum = new String();
            if (pinBoard.getSelectedIndex() == -1)
            {
                for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
                {
                    //no pinboard appt selected
                    tableAptNum = DS.Tables["Appointments"].Rows[i]["AptNum"].ToString();
                    if (StringSupport.equals(tableAptNum, ContrApptSingle.SelectedAptNum.ToString()))
                    {
                        aptconfirmed = PIn.Long(DS.Tables["Appointments"].Rows[i]["Confirmed"].ToString());
                        break;
                    }
                     
                }
            }
            else
            {
                //pinboard appt selected
                aptconfirmed = PIn.Long(pinBoard.getSelectedAppt().DataRoww["Confirmed"].ToString());
            } 
            listConfirmed.SelectedIndex = DefC.getOrder(DefCat.ApptConfirmed,aptconfirmed);
        }
        else
        {
            //could be -1
            listConfirmed.SelectedIndex = -1;
        } 
    }

    /**
    * Redraws screen based on data already gathered.  RefreshModuleDataPeriod will have already retrieved the data from the db.
    */
    public void refreshModuleScreenPeriod() throws Exception {
        DateTime startDate = new DateTime();
        DateTime endDate = new DateTime();
        if (ApptDrawing.IsWeeklyView)
        {
            startDate = WeekStartDate;
            endDate = WeekEndDate;
        }
        else
        {
            startDate = AppointmentL.DateSelected;
            endDate = AppointmentL.DateSelected;
        } 
        if (startDate.Year < 1880 || endDate.Year < 1880)
        {
            return ;
        }
         
        Calendar2.SetSelectionRange(startDate, endDate);
        ApptDrawing.ProvBar = new int[ApptDrawing.VisProvs.Count];
        for (int i = 0;i < ApptDrawing.VisProvs.Count;i++)
        {
            ApptDrawing.ProvBar[i] = new int[24 * ApptDrawing.RowsPerHr];
        }
        //[144]; or 24*6
        if (ContrApptSingle3 != null)
        {
            for (int i = 0;i < ContrApptSingle3.Length;i++)
            {
                //I think this is not needed.
                if (ContrApptSingle3[i] != null)
                {
                    ContrApptSingle3[i].Dispose();
                }
                 
                ContrApptSingle3[i] = null;
            }
            ContrApptSingle3 = null;
        }
         
        labelDate.Text = startDate.ToString("ddd");
        labelDate2.Text = startDate.ToString("-  MMM d");
        ContrApptSheet2.Controls.Clear();
        ContrApptSingle3 = new ContrApptSingle[DS.Tables["Appointments"].Rows.Count];
        DataRow row = new DataRow();
        for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
        {
            row = DS.Tables["Appointments"].Rows[i];
            ContrApptSingle3[i] = new ContrApptSingle();
            ContrApptSingle3[i].Visible = false;
            if (ContrApptSingle.SelectedAptNum.ToString() == row["AptNum"].ToString())
            {
                //if this is the selected apt
                //if the selected patient was changed from another module, then deselect the apt.
                if (PatCur == null || PatCur.PatNum.ToString() != row["PatNum"].ToString())
                {
                    ContrApptSingle.SelectedAptNum = -1;
                }
                 
            }
             
            ContrApptSingle3[i].DataRoww = row;
            ContrApptSingle3[i].TableApptFields = DS.Tables["ApptFields"];
            ContrApptSingle3[i].TablePatFields = DS.Tables["PatFields"];
            ContrApptSingle3[i].PatternShowing = ApptSingleDrawing.GetPatternShowing(row["Pattern"].ToString());
            if (!ApptDrawing.IsWeeklyView)
            {
                ApptDrawing.provBarShading(row);
            }
             
            ContrApptSingle3[i].Location = ApptSingleDrawing.SetLocation(row, 0, ApptDrawing.VisOps.Count, 0);
            ContrApptSingle3[i].Size = ApptSingleDrawing.setSize(row);
            ContrApptSheet2.Controls.Add(ContrApptSingle3[i]);
        }
        //end for
        //PinApptSingle.Refresh();
        pinBoard.Invalidate();
        ApptDrawing.SchedListPeriod = SchedListPeriod;
        ContrApptSheet2.createShadow();
        createAptShadowsOnMain();
        ContrApptSheet2.drawShadow();
        List<LabCase> labCaseList = LabCases.getForPeriod(startDate,endDate);
        FillLab(labCaseList);
        fillProduction();
        fillEmpSched();
        fillWaitingRoom();
        layoutPanels();
    }

    /**
    * Only used when viewing weekly.
    */
    private void panOpName_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (e.Button != MouseButtons.Left)
        {
            return ;
        }
         
        int dayI = 0;
        if (sender.GetType() == Panel.class)
        {
            dayI = (int)((Panel)sender).Tag;
        }
        else
        {
            dayI = (int)((Label)sender).Tag;
        } 
        AppointmentL.DateSelected = WeekStartDate.AddDays(dayI);
        setWeeklyView(false);
    }

    /**
    * Sets the ContrApptSingle array to null.
    */
    public void moduleUnselected() throws Exception {
        ContrApptSheet2.Shadow = null;
        if (ContrApptSingle3 != null)
        {
            for (int i = 0;i < ContrApptSingle3.Length;i++)
            {
                //too complex?
                if (ContrApptSingle3[i] != null)
                {
                    ContrApptSingle3[i].Dispose();
                    ContrApptSingle3[i] = null;
                }
                 
            }
            ContrApptSingle3 = null;
        }
         
        Plugins.hookAddCode(this,"ContrAppt.ModuleUnselected_end");
    }

    /*
    		///<summary>Was RefreshModuleData and FillPatientButton.  Gets the data for the specified patient. Does not refresh any appointment data.  This function should always be called when the patient changes since that's all this function is responsible for.</summary>
    		private void RefreshModulePatient(int patNum){//
    			if(PatCur.PatNum==patNum) {//if patient has not changed
    				return;//don't do anything
    			}
    			PatCurNum=patNum;//might be zero
    			bool hasEmail;
    			string chartNumber;
    			if(PatCurNum==0){
    				PatCurName="";
    				PatCurChartNumber="";
    				butOther.Enabled=false;
    				hasEmail=false;
    				chartNumber="";
    			}
    			else{
    				Patient pat=Patients.GetPat(PatCurNum);
    				PatCurName=pat.GetNameLF();
    				PatCurChartNumber=pat.ChartNumber;
    				hasEmail=pat.Email!="";
    				chartNumber=pat.ChartNumber;
    				//family can wait until user clicks on downarrow.
    				PatientL.AddPatsToMenu(menuPatient,new EventHandler(menuPatient_Click),PatCurName,PatCurNum);
    				butOther.Enabled=true;
    			}
    			butUnsched.Enabled=butOther.Enabled;
    			butBreak.Enabled=butOther.Enabled;
    			butComplete.Enabled=butOther.Enabled;
    			butDelete.Enabled=butOther.Enabled;
    			//ParentForm.Text=Patients.GetMainTitle(PatCurName,PatCurNum,PatCurChartNumber);
    			if(panelAptInfo.Enabled && DS!=null) {
    				int aptconfirmed=0;
    				for(int i=0;i<DS.Tables["Appointments"].Rows.Count;i++) {
    					if(DS.Tables["Appointments"].Rows[i]["AptNum"].ToString()==ContrApptSingle.ClickedAptNum.ToString()) {
    						aptconfirmed=PIn.PInt(DS.Tables["Appointments"].Rows[i]["Confirmed"].ToString());
    						break;
    					}
    				}
    				listConfirmed.SelectedIndex=DefC.GetOrder(DefCat.ApptConfirmed,aptconfirmed);//could be -1
    			}
    			else {
    				listConfirmed.SelectedIndex=-1;
    			}
    //JSPARKS - THIS SHOULD BE MOVED TO BE CALLED EXPLICITLY FROM WITHIN VARIOUS PLACES IN THIS MODULE
    //JUST LIKE IN THE OTHER MODULES.  WHEN IT'S HERE, IT IS ALSO GETTING CALLED EVERY TIME MODULESELECTED
    //GETS TRIGGERED FROM PARENT FORM.
    			OnPatientSelected(PatCurNum,PatCurName,hasEmail,chartNumber);
    		}*/
    /**
    * Sends the PatientSelected event on up to the main form.  The only result is that the main window now knows the new patNum and patName.  Does nothing else.  Does not trigger any other methods to run which might cause a loop.  Only called from RefreshModulePatient, but it's separate so that it's the same as in the other modules.
    */
    private void onPatientSelected(Patient pat) throws Exception {
        OpenDental.PatientSelectedEventArgs eArgs = new OpenDental.PatientSelectedEventArgs(pat);
        if (PatientSelected != null)
        {
            PatientSelected.invoke(this,eArgs);
        }
         
    }

    /*This was resulting in too many firings of ModuleSelected
    		///<summary>Currently only used when comboView really does change.  Otherwise, just call ModuleSelected.  Triggered in FunctionKeyPress, SetView, and  FillViews</summary>
    		private void comboView_SelectedIndexChanged(object sender,System.EventArgs e) {
    			if(PatCur==null) {
    				ModuleSelected(0);
    			}
    			else {
    				ModuleSelected(PatCur.PatNum);
    			}
    		}*/
    /**
    * 
    */
    private void comboView_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        SetView(comboView.SelectedIndex, true);
    }

    /**
    * Activated anytime a Patient menu item is clicked.
    */
    private void menuPatient_Click(Object sender, System.EventArgs e) throws Exception {
        long newPatNum = PatientL.ButtonSelect(menuPatient, sender, null);
        moduleSelected(newPatNum);
    }

    /**
    * Not used
    */
    private void contrAppt_Layout(Object sender, System.Windows.Forms.LayoutEventArgs e) throws Exception {
    }

    //This event actually happens quite frequently: once for every appointment placed on the screen.
    //Moved it all to Resize event.
    /**
    * Might not be getting called enough.
    */
    private void contrAppt_Resize(Object sender, System.EventArgs e) throws Exception {
        try
        {
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        //This didn't work so well.  Very slow and caused program to not be able to unminimize
        //so it doesn't crash if not connected to DB
        //ModuleSelected();
        //even though the part above didn't work, we're going to try adding the stuff below.  It was important to get it out
        //of the Layout event, which fires very frequently.
        layoutPanels();
    }

    /**
    * This might not be getting called frequently enough.  Done on resize and when refreshing the period.  But it used to be done very very frequently.
    */
    private void layoutPanels() throws Exception {
        if (Calendar2.Width > panelCalendar.Width)
        {
            //for example, Chinese calendar
            panelCalendar.Width = Calendar2.Width + 1;
        }
         
        //panelAptInfo.Width=Calendar2.Width+1;
        //Assumes widths of the first 2 panels were set the same in the designer,
        ToolBarMain.Location = new Point(ClientSize.Width - panelCalendar.Width - 2, 0);
        panelCalendar.Location = new Point(ClientSize.Width - panelCalendar.Width - 2, ToolBarMain.Height);
        panelAptInfo.Location = new Point(ClientSize.Width - panelCalendar.Width - 2, ToolBarMain.Height + panelCalendar.Height);
        //butOther.Location=new Point(panelAptInfo.Location.X+32,panelAptInfo.Location.Y+84);
        panelMakeButtons.Location = new Point(panelAptInfo.Right + 2, panelAptInfo.Top);
        tabControl.Location = new Point(panelAptInfo.Left, panelAptInfo.Bottom + 1);
        panelSheet.Width = ClientSize.Width - panelCalendar.Width - 2;
        panelSheet.Height = ClientSize.Height - panelSheet.Location.Y;
        if (!DefC.getDefShortIsNull())
        {
            ApptViewItemL.GetForCurView(comboView.SelectedIndex - 1, ApptDrawing.IsWeeklyView, SchedListPeriod);
            //refreshes visops,etc
            ApptDrawing.ApptSheetWidth = panelSheet.Width - vScrollBar1.Width;
            ApptDrawing.computeColWidth(0);
        }
         
        panelOps.Width = panelSheet.Width;
    }

    /**
    * Called from FormOpenDental upon startup.
    */
    public void initializeOnStartup() throws Exception {
        //jsparks-
        //This method was inefficient and was causing 4 refreshes: RefreshPeriod, FillViews->comboView_SelectedIndexChanged, SetView?, SetWeeklyView.
        //This was especially inefficient, because after calling this method, FormOD was refreshing this module anyway.  So about 5 refreshes on startup.
        //Now, InitializedOnStartup remains false until the end of this method, preventing all refreshes when inside this method.
        //Verified that it only does one RefreshPeriod call to the db.
        if (InitializedOnStartup)
        {
            return ;
        }
         
        layoutPanels();
        ApptDrawing.RowsPerIncr = 1;
        AppointmentL.DateSelected = DateTime.Now;
        ContrApptSingle.SelectedAptNum = -1;
        //RefreshPeriod();//Don't think this is needed.
        fillViews();
        //This does a SetView which will be overridden in the next line.
        setView((int)ComputerPrefs.getLocalComputer().RecentApptView,false);
        menuWeeklyApt.MenuItems.Clear();
        menuWeeklyApt.MenuItems.Add(Lan.g(this,"Copy to Pinboard"), new EventHandler(menuWeekly_Click));
        menuApt.MenuItems.Clear();
        menuApt.MenuItems.Add(Lan.g(this,"Copy to Pinboard"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add("-");
        menuApt.MenuItems.Add(Lan.g(this,"Send to Unscheduled List"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Break Appointment"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Set Complete"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Delete"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Other Appointments"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add("-");
        menuApt.MenuItems.Add(Lan.g(this,"Print Label"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Print Card"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Print Card for Entire Family"), new EventHandler(menuApt_Click));
        menuApt.MenuItems.Add(Lan.g(this,"Routing Slip"), new EventHandler(menuApt_Click));
        menuBlockout.MenuItems.Clear();
        menuBlockout.MenuItems.Add(Lan.g(this,"Edit Blockout"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Cut Blockout"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Copy Blockout"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Paste Blockout"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Delete Blockout"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Add Blockout"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Blockout Cut-Copy-Paste"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Clear All Blockouts for Day"), new EventHandler(menuBlockout_Click));
        menuBlockout.MenuItems.Add(Lan.g(this,"Edit Blockout Types"), new EventHandler(menuBlockout_Click));
        //butTodayWk,
        Lan.C(this, new Control[]{ butToday, butSearch, butClearPin, label2, label7, butMakeAppt, butMakeRecall, butFamRecall, butViewAppts, radioDay, radioWeek, tabWaiting, tabSched, butLab, butBackWeek, butBackMonth, butFwdWeek, butFwdMonth, gridEmpSched, gridWaiting });
        layoutToolBar();
        //Appointment action buttons
        toolTip1.SetToolTip(butUnsched, Lan.g(this,"Send to Unscheduled List"));
        toolTip1.SetToolTip(butBreak, Lan.g(this,"Break"));
        toolTip1.SetToolTip(butComplete, Lan.g(this,"Set Complete"));
        toolTip1.SetToolTip(butDelete, Lan.g(this,"Delete"));
        //toolTip1.SetToolTip(butOther,Lan.g(this,"Other Appointments"));
        if (PrefC.getString(PrefName.RegistrationKey).StartsWith("UPR6J92T29"))
        {
            butGraph.Visible = true;
        }
         
        setWeeklyView(false);
        InitializedOnStartup = true;
    }

    //moved this down to prevent view setting from triggering ModuleSelected().
    /**
    * 
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        //ODToolBarButton button;
        //button=new ODToolBarButton("",0,Lan.g(this,"Select Patient"),"Patient");
        //button.Style=ODToolBarButtonStyle.DropDownButton;
        //button.DropDownMenu=menuPatient;
        //ToolBarMain.Buttons.Add(button);
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 2, Lan.g(this,"Appointment Lists"), "Lists"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton("", 1, Lan.g(this,"Print Schedule"), "Print"));
        ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.ApptModule);
        for (int i = 0;i < toolButItems.Count;i++)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
        }
        ToolBarMain.Invalidate();
        Plugins.hookAddCode(this,"ContrAppt.LayoutToolBar_end",PatCur);
    }

    /**
    * Not in use.  See InstantClasses instead.
    */
    private void contrAppt_Load(Object sender, System.EventArgs e) throws Exception {
    }

    /**
    * The key press from the main form is passed down to this module.
    */
    public void functionKeyPress(Keys keys) throws Exception {
        Keys __dummyScrutVar0 = keys;
        if (__dummyScrutVar0.equals(Keys.F1))
        {
            setView(1,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F2))
        {
            setView(2,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F3))
        {
            setView(3,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F4))
        {
            setView(4,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F5))
        {
            setView(5,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F6))
        {
            setView(6,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F7))
        {
            setView(7,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F8))
        {
            setView(8,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F9))
        {
            setView(9,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F10))
        {
            setView(10,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F11))
        {
            setView(11,true);
        }
        else if (__dummyScrutVar0.equals(Keys.F12))
        {
            setView(12,true);
        }
                    
    }

    /**
    * Sets the view to the specified index, checking for validity in the process.  Then, does a ModuleSelected().  If saveToDb, then it will remember the index for this workstation.
    */
    private void setView(int viewIndex, boolean saveToDb) throws Exception {
        if (viewIndex > ApptViewC.getList().Length)
        {
            return ;
        }
         
        comboView.SelectedIndex = viewIndex;
        if (comboView.SelectedIndex == -1)
        {
            comboView.SelectedIndex = 0;
        }
         
        if (!InitializedOnStartup)
        {
            return ;
        }
         
        //prevent ModuleSelected().
        if (saveToDb)
        {
            ComputerPrefs.getLocalComputer().RecentApptView = (byte)viewIndex;
            ComputerPrefs.update(ComputerPrefs.getLocalComputer());
        }
         
        if (PatCur == null)
        {
            ModuleSelected(0);
        }
        else
        {
            moduleSelected(PatCur.PatNum);
        } 
    }

    /**
    * Fills the comboView with the current list of views and then tries to reselect the previous selection.  Also called from FormOpenDental.RefreshLocalData().
    */
    public void fillViews() throws Exception {
        int selected = comboView.SelectedIndex;
        comboView.Items.Clear();
        comboView.Items.Add(Lan.g(this,"none"));
        String f = "";
        for (int i = 0;i < ApptViewC.getList().Length;i++)
        {
            if (i <= 12)
                f = "F" + (i + 1).ToString() + "-";
            else
                f = ""; 
            comboView.Items.Add(f + ApptViewC.getList()[i].Description);
        }
        setView(selected,false);
    }

    //this also triggers ModuleSelected()
    /**
    * Sets appointment data invalid on all other computers, causing them to refresh.  Does NOT refresh the data for this computer which must be done separately.
    */
    private void setInvalid() throws Exception {
        DataValid.setInvalid(AppointmentL.DateSelected);
    }

    /**
    * Clicked today.
    */
    private void butToday_Click(Object sender, System.EventArgs e) throws Exception {
        AppointmentL.DateSelected = DateTimeOD.getToday();
        SetWeeklyView(radioWeek.Checked);
    }

    /**
    * Clicked back one day.
    */
    private void butBack_Click(Object sender, System.EventArgs e) throws Exception {
        AppointmentL.DateSelected = AppointmentL.DateSelected.AddDays(-1);
        SetWeeklyView(radioWeek.Checked);
    }

    /**
    * Clicked forward one day.
    */
    private void butFwd_Click(Object sender, System.EventArgs e) throws Exception {
        AppointmentL.DateSelected = AppointmentL.DateSelected.AddDays(1);
        SetWeeklyView(radioWeek.Checked);
    }

    private void butBackMonth_Click(Object sender, EventArgs e) throws Exception {
        AppointmentL.DateSelected = AppointmentL.DateSelected.AddMonths(-1);
        SetWeeklyView(radioWeek.Checked);
    }

    private void butBackWeek_Click(Object sender, EventArgs e) throws Exception {
        AppointmentL.DateSelected = AppointmentL.DateSelected.AddDays(-7);
        SetWeeklyView(radioWeek.Checked);
    }

    private void butFwdWeek_Click(Object sender, EventArgs e) throws Exception {
        AppointmentL.DateSelected = AppointmentL.DateSelected.AddDays(7);
        SetWeeklyView(radioWeek.Checked);
    }

    private void butFwdMonth_Click(Object sender, EventArgs e) throws Exception {
        AppointmentL.DateSelected = AppointmentL.DateSelected.AddMonths(1);
        SetWeeklyView(radioWeek.Checked);
    }

    private void radioDay_Click(Object sender, EventArgs e) throws Exception {
        setWeeklyView(false);
    }

    private void radioWeek_Click(Object sender, EventArgs e) throws Exception {
        setWeeklyView(true);
    }

    /**
    * Clicked a date on the calendar.
    */
    private void calendar2_DateSelected(Object sender, System.Windows.Forms.DateRangeEventArgs e) throws Exception {
        AppointmentL.DateSelected = Calendar2.SelectionStart;
        SetWeeklyView(radioWeek.Checked);
    }

    /**
    * Switches between weekly view and daily view.  AppointmentL.DateSelected needs to be set first.  Calculates WeekStartDate and WeekEndDate based on AppointmentL.DateSelected.  Then calls either RefreshPeriod or ModuleSelected.
    */
    private void setWeeklyView(boolean isWeeklyView) throws Exception {
        //if the weekly view doesn't change, then just RefreshPeriod
        boolean weeklyViewChanged = false;
        if (isWeeklyView != ApptDrawing.IsWeeklyView)
        {
            weeklyViewChanged = true;
        }
         
        //for those few times when the radiobuttons aren't quite right:
        if (isWeeklyView)
        {
            radioWeek.Checked = true;
            butFwd.Enabled = false;
            butBack.Enabled = false;
        }
        else
        {
            radioDay.Checked = true;
            butFwd.Enabled = true;
            butBack.Enabled = true;
        } 
        WeekStartDate = AppointmentL.DateSelected.AddDays(1 - (int)AppointmentL.DateSelected.DayOfWeek).Date;
        WeekEndDate = WeekStartDate.AddDays(ApptDrawing.NumOfWeekDaysToDisplay - 1).Date;
        ApptDrawing.IsWeeklyView = isWeeklyView;
        if (!InitializedOnStartup)
        {
            return ;
        }
         
        //prevent refreshing repeatedly on startup
        if (weeklyViewChanged || isWeeklyView)
        {
            if (PatCur == null)
            {
                ModuleSelected(0);
            }
            else
            {
                moduleSelected(PatCur.PatNum);
            } 
        }
        else
        {
            refreshPeriod();
        } 
    }

    /**
    * Fills the lab summary for the day.
    */
    private void fillLab(List<LabCase> labCaseList) throws Exception {
        int notRec = 0;
        for (int i = 0;i < labCaseList.Count;i++)
        {
            if (labCaseList[i].DateTimeChecked.Year > 1880)
            {
                continue;
            }
             
            if (labCaseList[i].DateTimeRecd.Year > 1880)
            {
                continue;
            }
             
            notRec++;
        }
        if (notRec == 0)
        {
            textLab.Font = new Font(FontFamily.GenericSansSerif, 8, FontStyle.Regular);
            textLab.ForeColor = Color.Black;
            textLab.Text = Lan.g(this,"All Received");
        }
        else
        {
            textLab.Font = new Font(FontFamily.GenericSansSerif, 8, FontStyle.Bold);
            textLab.ForeColor = Color.DarkRed;
            textLab.Text = notRec.ToString() + Lan.g(this," NOT RECEIVED");
        } 
    }

    /**
    * Fills the production summary for the day.
    */
    private void fillProduction() throws Exception {
        boolean showProduction = false;
        for (int i = 0;i < ApptViewItemL.ApptRows.Count;i++)
        {
            if (StringSupport.equals(ApptViewItemL.ApptRows[i].ElementDesc, "Production"))
            {
                showProduction = true;
            }
             
        }
        if (!showProduction)
        {
            textProduction.Text = "";
            return ;
        }
         
        double grossproduction = 0;
        double netproduction = 0;
        int indexProv = new int();
        for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
        {
            indexProv = -1;
            if (StringSupport.equals(DS.Tables["Appointments"].Rows[i]["IsHygiene"].ToString(), "1"))
            {
                //if isHygiene
                if (StringSupport.equals(DS.Tables["Appointments"].Rows[i]["ProvHyg"].ToString(), "0"))
                {
                    //if no hyg prov set.
                    indexProv = ApptDrawing.GetIndexProv(PIn.Long(DS.Tables["Appointments"].Rows[i]["ProvNum"].ToString()));
                }
                else
                {
                    indexProv = ApptDrawing.GetIndexProv(PIn.Long(DS.Tables["Appointments"].Rows[i]["ProvHyg"].ToString()));
                } 
            }
            else
            {
                //not hyg
                indexProv = ApptDrawing.GetIndexProv(PIn.Long(DS.Tables["Appointments"].Rows[i]["ProvNum"].ToString()));
            } 
            if (indexProv == -1)
            {
                continue;
            }
             
            if (DS.Tables["Appointments"].Rows[i]["AptStatus"].ToString() != (((Enum)ApptStatus.Broken).ordinal()).ToString() && DS.Tables["Appointments"].Rows[i]["AptStatus"].ToString() != (((Enum)ApptStatus.UnschedList).ordinal()).ToString() && DS.Tables["Appointments"].Rows[i]["AptStatus"].ToString() != (((Enum)ApptStatus.PtNote).ordinal()).ToString() && DS.Tables["Appointments"].Rows[i]["AptStatus"].ToString() != (((Enum)ApptStatus.PtNoteCompleted).ordinal()).ToString())
            {
                //In order to get production numbers split by provider, it would require generating total production numbers
                //in another table from the business layer.  But that will only work if hyg procedures are appropriately assigned
                //when setting appointments.
                grossproduction += PIn.Decimal(DS.Tables["Appointments"].Rows[i]["productionVal"].ToString());
                netproduction += PIn.Decimal(DS.Tables["Appointments"].Rows[i]["productionVal"].ToString()) - PIn.Decimal(DS.Tables["Appointments"].Rows[i]["writeoffPPO"].ToString());
            }
             
        }
        textProduction.Text = grossproduction.ToString("c0");
        if (grossproduction != netproduction)
        {
            textProduction.Text += Lan.g(this,", net:") + netproduction.ToString("c0");
        }
         
    }

    /**
    * 
    */
    private void fillEmpSched() throws Exception {
        DataTable table = DS.Tables["EmpSched"];
        gridEmpSched.beginUpdate();
        gridEmpSched.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableApptEmpSched","Employee"),80);
        gridEmpSched.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptEmpSched","Schedule"),70);
        gridEmpSched.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptEmpSched","Notes"),100);
        gridEmpSched.getColumns().add(col);
        gridEmpSched.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["empName"].ToString());
            row.getCells().Add(table.Rows[i]["schedule"].ToString());
            row.getCells().Add(table.Rows[i]["Note"].ToString());
            gridEmpSched.getRows().add(row);
        }
        gridEmpSched.endUpdate();
    }

    /**
    * 
    */
    private void fillWaitingRoom() throws Exception {
        if (DS == null)
        {
            return ;
        }
         
        TimeSpan delta = DateTime.Now - LastTimeDataRetrieved;
        DataTable table = DS.Tables["WaitingRoom"];
        gridWaiting.beginUpdate();
        gridWaiting.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableApptWaiting","Patient"),130);
        gridWaiting.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptWaiting","Waited"), 100, HorizontalAlignment.Center);
        gridWaiting.getColumns().add(col);
        gridWaiting.getRows().Clear();
        DateTime waitTime = new DateTime();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["patName"].ToString());
            waitTime = DateTime.Parse(table.Rows[i]["waitTime"].ToString());
            //we ignore date
            waitTime += delta;
            row.getCells().Add(waitTime.ToString("H:mm:ss"));
            gridWaiting.getRows().add(row);
        }
        gridWaiting.endUpdate();
    }

    private void gridEmpSched_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    private void gridEmpSched_DoubleClick(Object sender, EventArgs e) throws Exception {
        if (ApptDrawing.IsWeeklyView)
        {
            MsgBox.show(this,"Not available in weekly view");
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.Schedules))
        {
            return ;
        }
         
        FormScheduleDayEdit FormS = new FormScheduleDayEdit(AppointmentL.DateSelected);
        FormS.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Schedules, 0, "");
        setWeeklyView(false);
    }

    //to refresh
    /**
    * Creates one bitmap image for each appointment if visible, and draws those bitmaps onto the main appt background image.
    */
    private void createAptShadowsOnMain() throws Exception {
        if (ContrApptSheet2.Shadow == null)
        {
            return ;
        }
         
        //if user resizes window to be very narrow
        Graphics grfx = Graphics.FromImage(ContrApptSheet2.Shadow);
        try
        {
            {
                for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
                {
                    ContrApptSingle3[i].CreateShadow();
                    if (ContrApptSingle3[i].Location.X >= ApptDrawing.TimeWidth + ApptDrawing.ProvWidth * ApptDrawing.ProvCount && ContrApptSingle3[i].Width > 3 && ContrApptSingle3[i].Shadow != null)
                    {
                        grfx.DrawImage(ContrApptSingle3[i].Shadow, ContrApptSingle3[i].Location.X, ContrApptSingle3[i].Location.Y);
                    }
                     
                    if (ContrApptSingle3[i].Shadow != null)
                    {
                        ContrApptSingle3[i].Shadow.Dispose();
                        ContrApptSingle3[i].Shadow = null;
                    }
                     
                }
            }
        }
        finally
        {
            if (grfx != null)
                Disposable.mkDisposable(grfx).dispose();
             
        }
    }

    /**
    * Gets the index within the array of appointment controls, based on the supplied primary key.
    */
    private int getIndex(long myAptNum) throws Exception {
        int retVal = -1;
        for (int i = 0;i < ContrApptSingle3.Length;i++)
        {
            if (ContrApptSingle3[i].DataRoww["AptNum"].ToString() == myAptNum.ToString())
            {
                retVal = i;
            }
             
        }
        return retVal;
    }

    private void sendToPinBoard(long aptNum) throws Exception {
        List<long> list = new List<long>();
        list.Add(aptNum);
        SendToPinBoard(list);
    }

    /**
    * Loads all info for for specified appointment into the control that displays the pinboard appointment. Runs RefreshModulePatient.  Sets pinboard appointment as selected.
    */
    private void sendToPinBoard(List<long> aptNums) throws Exception {
        if (aptNums.Count == 0)
        {
            return ;
        }
         
        DataRow row = null;
        for (int a = 0;a < aptNums.Count;a++)
        {
            row = null;
            for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
            {
                if (DS.Tables["Appointments"].Rows[i]["AptNum"].ToString() == aptNums[a].ToString())
                {
                    row = DS.Tables["Appointments"].Rows[i];
                    break;
                }
                 
            }
            DataTable tableApptFields = DS.Tables["ApptFields"];
            DataTable tablePatFields = DS.Tables["PatFields"];
            if (row == null)
            {
                DataTable tableAppts = Appointments.RefreshOneApt(aptNums[a], false).Tables["Appointments"];
                row = tableAppts.Rows[0];
                if (StringSupport.equals(row["AptStatus"].ToString(), "6"))
                {
                    //planned
                    //then do it again the right way
                    tableAppts = Appointments.RefreshOneApt(aptNums[a], true).Tables["Appointments"];
                    row = tableAppts.Rows[0];
                }
                 
                //The appt fields are not in DS.Tables["ApptFields"] since the appt is not visible on the schedule.
                tableApptFields = Appointments.getApptFields(tableAppts);
            }
             
            pinBoard.addAppointment(row,tableApptFields,tablePatFields);
        }
        //deal with this later:
        //try{
        //
        //}
        //catch(ApplicationException ex){
        //	MessageBox.Show(ex.Message);//more of a notification really.  It will still highlight that appointment.
        //return;
        //}
        //if aptNum is already in DS, then use that row.  Otherwise, get a new row.
        //it will set pt to the last appt on the pinboard.
        RefreshModuleDataPatient(PIn.Long(row["PatNum"].ToString()));
        onPatientSelected(PatCur);
        //RefreshModulePatient(PIn.PInt(row["PatNum"].ToString()));
        mouseIsDown = false;
        boolAptMoved = false;
        ContrApptSingle.SelectedAptNum = -1;
    }

    private void pinBoard_SelectedIndexChanged(Object sender, EventArgs e) throws Exception {
        RefreshModuleDataPatient(PIn.Long(pinBoard.getApptList()[pinBoard.getSelectedIndex()].DataRoww["PatNum"].ToString()));
        refreshModuleScreenPatient();
        CancelPinMouseDown = false;
        onPatientSelected(PatCur);
    }

    //The line above can trigger a popup dialog which can cause the tempAppt to get stuck to the mouse
    //RefreshModulePatient(PIn.PInt(pinBoard.ApptList[pinBoard.SelectedIndex].DataRoww["PatNum"].ToString()));
    //Since this is usually caused by user mouse, then it goes right into pinBoard_MouseDown().
    /**
    * Sets selected and prepares for drag.
    */
    private void pinBoard_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        if (pinBoard.getSelectedIndex() == -1)
        {
            return ;
        }
         
        if (mouseIsDown)
        {
            return ;
        }
         
        //User right clicked while draging appt around.
        if (e.Button == MouseButtons.Right)
        {
            ContextMenu cmen = new ContextMenu();
            MenuItem menuItemProv = new MenuItem(Lan.g(this,"Change Provider"));
            menuItemProv.Click += new EventHandler(menuItemProv_Click);
            cmen.MenuItems.Add(menuItemProv);
            cmen.Show(pinBoard, e.Location);
            return ;
        }
         
        if (CancelPinMouseDown)
        {
            //I'm worried that setting this to false in pinBoard_SelectedIndexChanged is not frequent enough,
            //because a mouse down could happen without the selected index changing.
            //But in that case, a popup would already have happened.
            //Worst case scenario is that user would have to try again.
            CancelPinMouseDown = false;
            return ;
        }
         
        mouseIsDown = true;
        //ContrApptSingle.PinBoardIsSelected=true;
        TempApptSingle = new ContrApptSingle();
        TempApptSingle.DataRoww = pinBoard.getSelectedAppt().DataRoww;
        TempApptSingle.TableApptFields = pinBoard.getSelectedAppt().TableApptFields;
        TempApptSingle.TablePatFields = pinBoard.getSelectedAppt().TablePatFields;
        TempApptSingle.Visible = false;
        Controls.Add(TempApptSingle);
        TempApptSingle.Location = ApptSingleDrawing.SetLocation(TempApptSingle.DataRoww, 0, ApptDrawing.VisOps.Count, 0);
        TempApptSingle.Size = ApptSingleDrawing.setSize(TempApptSingle.DataRoww);
        TempApptSingle.PatternShowing = ApptSingleDrawing.GetPatternShowing(TempApptSingle.DataRoww["Pattern"].ToString());
        TempApptSingle.createShadow();
        TempApptSingle.BringToFront();
        ContrApptSingle.SelectedAptNum = -1;
        //RefreshModulePatient(PIn.PInt(PinApptSingle.DataRoww["PatNum"].ToString()));//already done
        //PinApptSingle.CreateShadow();
        //PinApptSingle.Refresh();
        createAptShadowsOnMain();
        //to clear previous selection
        ContrApptSheet2.drawShadow();
        //mouseOrigin is in ContrAppt coordinates (essentially, the entire window)
        mouseOrigin.X = e.X + pinBoard.Location.X + panelCalendar.Location.X;
        //e.X+PinApptSingle.Location.X;
        mouseOrigin.Y = e.Y + pinBoard.getSelectedAppt().Location.Y + pinBoard.Location.Y + panelCalendar.Location.Y;
        //e.Y+PinApptSingle.Location.Y;
        contOrigin = new Point(pinBoard.Location.X + panelCalendar.Location.X, pinBoard.getSelectedAppt().Location.Y + pinBoard.Location.Y + panelCalendar.Location.Y);
    }

    //PinApptSingle.Location;
    void menuItemProv_Click(Object sender, EventArgs e) throws Exception {
        //throw new NotImplementedException();
        Appointment apt = Appointments.GetOneApt(PIn.Long(pinBoard.getSelectedAppt().DataRoww["AptNum"].ToString()));
        Appointment oldApt = apt.clone();
        if (apt == null)
        {
            MessageBox.Show("Appointment not found.");
            return ;
        }
         
        long provNum = apt.ProvNum;
        if (apt.IsHygiene)
        {
            provNum = apt.ProvHyg;
        }
         
        FormProviderPick formPick = new FormProviderPick();
        formPick.SelectedProvNum = provNum;
        formPick.ShowDialog();
        if (formPick.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (formPick.SelectedProvNum == provNum)
        {
            return ;
        }
         
        //provider not changed.
        if (apt.IsHygiene)
        {
            apt.ProvHyg = formPick.SelectedProvNum;
        }
        else
        {
            apt.ProvNum = formPick.SelectedProvNum;
        } 
        List<Procedure> procsForSingleApt = Procedures.getProcsForSingle(apt.AptNum,false);
        List<long> codeNums = new List<long>();
        for (int p = 0;p < procsForSingleApt.Count;p++)
        {
            codeNums.Add(procsForSingleApt[p].CodeNum);
        }
        String calcPattern = Appointments.CalculatePattern(apt.ProvNum, apt.ProvHyg, codeNums, true);
        if (!StringSupport.equals(apt.Pattern, calcPattern))
        {
            if (!apt.TimeLocked || MsgBox.show(this,MsgBoxButtons.YesNo,"Appointment length is locked.  Change length for new provider anyway?"))
            {
                apt.Pattern = calcPattern;
            }
             
        }
         
        Appointments.update(apt,oldApt);
        Procedures.SetProvidersInAppointment(apt, procsForSingleApt);
        moduleSelected(PatCur.PatNum);
        DataRow row = null;
        if (apt.AptStatus == ApptStatus.Planned)
        {
            row = Appointments.refreshOneApt(apt.AptNum,true).Tables["Appointments"].Rows[0];
        }
        else
        {
            row = Appointments.refreshOneApt(apt.AptNum,false).Tables["Appointments"].Rows[0];
        } 
        if (row == null)
        {
            return ;
        }
         
        pinBoard.resetData(row);
    }

    //MessageBox.Show(Providers.GetAbbr(apt.ProvNum));
    /**
    * Moves pinboard appt if mouse is down.
    */
    private void pinBoard_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
        {
            return ;
        }
         
        //not sure what this is:
        //if((Math.Abs(e.X+PinApptSingle.Location.X-mouseOrigin.X)<1)
        //	&&(Math.Abs(e.Y+PinApptSingle.Location.Y-mouseOrigin.Y)<1)){
        //	return;
        //}
        if (TempApptSingle.Location == new Point(0, 0))
        {
            TempApptSingle.Height = 1;
        }
         
        //to prevent flicker in UL corner
        TempApptSingle.Visible = true;
        boolAptMoved = true;
        TempApptSingle.Location = new Point(contOrigin.X + (e.X + pinBoard.Location.X + panelCalendar.Location.X) - mouseOrigin.X, contOrigin.Y + (e.Y + pinBoard.getSelectedAppt().Location.Y + pinBoard.Location.Y + panelCalendar.Location.Y) - mouseOrigin.Y);
        if (TempApptSingle.Height == 1)
        {
            TempApptSingle.Size = ApptSingleDrawing.setSize(TempApptSingle.DataRoww);
        }
         
    }

    /**
    * Usually happens after a pinboard appt has been dragged onto main appt sheet.
    */
    private void pinBoard_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (!boolAptMoved)
        {
            mouseIsDown = false;
            if (TempApptSingle != null)
            {
                TempApptSingle.Dispose();
            }
             
            return ;
        }
         
        if (TempApptSingle.Location.X > ContrApptSheet2.Width)
        {
            mouseIsDown = false;
            boolAptMoved = false;
            TempApptSingle.Dispose();
            return ;
        }
         
        //if Planned appt is on pinboard
        if (pinBoard.getSelectedAppt().DataRoww["AptStatus"].ToString() == (((Enum)ApptStatus.Planned).ordinal()).ToString() && !Security.isAuthorized(Permissions.AppointmentCreate))
        {
            //and no permission to create a new appt
            mouseIsDown = false;
            boolAptMoved = false;
            TempApptSingle.Dispose();
            return ;
        }
         
        //security prevents moving an appointment by preventing placing it on the pinboard, not here
        //We no longer ask user this question.  It just slows things down: "Move Appointment?"
        //convert loc to new time
        Appointment aptCur = Appointments.GetOneApt(PIn.Long(pinBoard.getSelectedAppt().DataRoww["AptNum"].ToString()));
        if (aptCur == null)
        {
            MsgBox.show(this,"This appointment has been deleted since it was moved to the pinboard. It will now be cleared from the pinboard.");
            mouseIsDown = false;
            boolAptMoved = false;
            TempApptSingle.Dispose();
            pinBoard.clearSelected();
            return ;
        }
         
        Appointment aptOld = aptCur.clone();
        RefreshModuleDataPatient(PIn.Long(pinBoard.getSelectedAppt().DataRoww["PatNum"].ToString()));
        //redundant?
        //Patient pat=Patients.GetPat(PIn.PInt(pinBoard.SelectedAppt.DataRoww["PatNum"].ToString()));
        if (aptCur.IsNewPatient && AppointmentL.DateSelected != aptCur.AptDateTime)
        {
            Procedures.setDateFirstVisit(AppointmentL.DateSelected,4,PatCur);
        }
         
        int tHr = ApptDrawing.ConvertToHour(TempApptSingle.Location.Y - ContrApptSheet2.Location.Y - panelSheet.Location.Y);
        int tMin = ApptDrawing.ConvertToMin(TempApptSingle.Location.Y - ContrApptSheet2.Location.Y - panelSheet.Location.Y);
        DateTime tDate = AppointmentL.DateSelected;
        if (ApptDrawing.IsWeeklyView)
        {
            tDate = WeekStartDate.AddDays(ApptDrawing.ConvertToDay(TempApptSingle.Location.X - ContrApptSheet2.Location.X));
        }
         
        DateTime fromDate = aptCur.AptDateTime.Date;
        aptCur.AptDateTime = new DateTime(tDate.Year, tDate.Month, tDate.Day, tHr, tMin, 0);
        if (AppointmentRuleC.getList().Length > 0)
        {
            //this is crude and temporary:
            List<long> aptNums = new List<long>();
            for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
            {
                aptNums.Add(PIn.Long(DS.Tables["Appointments"].Rows[i]["AptNum"].ToString()));
            }
            //ListDay[i].AptNum;
            List<Procedure> procsMultApts = Procedures.GetProcsMultApts(aptNums);
            Procedure[] procsForOne = Procedures.GetProcsOneApt(aptCur.AptNum, procsMultApts);
            ArrayList doubleBookedCodes = AppointmentL.GetDoubleBookedCodes(aptCur, DS.Tables["Appointments"].Copy(), procsMultApts, procsForOne);
            if (doubleBookedCodes.Count > 0)
            {
                //if some codes would be double booked
                if (AppointmentRules.isBlocked(doubleBookedCodes))
                {
                    MessageBox.Show(Lan.g(this,"Not allowed to double book:") + " " + AppointmentRules.getBlockedDescription(doubleBookedCodes));
                    mouseIsDown = false;
                    boolAptMoved = false;
                    TempApptSingle.Dispose();
                    return ;
                }
                 
            }
             
        }
         
        Operatory curOp = ApptDrawing.VisOps[ApptDrawing.ConvertToOp(TempApptSingle.Location.X - ContrApptSheet2.Location.X)];
        aptCur.Op = curOp.OperatoryNum;
        long assignedDent = Schedules.getAssignedProvNumForSpot(SchedListPeriod,curOp,false,aptCur.AptDateTime);
        long assignedHyg = Schedules.getAssignedProvNumForSpot(SchedListPeriod,curOp,true,aptCur.AptDateTime);
        List<Procedure> procsForSingleApt = null;
        if (aptCur.AptStatus != ApptStatus.PtNote && aptCur.AptStatus != ApptStatus.PtNoteCompleted)
        {
            if (PatCur.AskToArriveEarly > 0)
            {
                aptCur.DateTimeAskedToArrive = aptCur.AptDateTime.AddMinutes(-PatCur.AskToArriveEarly);
                MessageBox.Show(Lan.g(this,"Ask patient to arrive") + " " + PatCur.AskToArriveEarly + " " + Lan.g(this,"minutes early at") + " " + aptCur.DateTimeAskedToArrive.ToShortTimeString() + ".");
            }
            else
            {
                aptCur.DateTimeAskedToArrive = DateTime.MinValue;
            } 
            //if no dentist/hygienist is assigned to spot, then keep the original dentist/hygienist without prompt.  All appts must have prov.
            if ((assignedDent != 0 && assignedDent != aptCur.ProvNum) || (assignedHyg != 0 && assignedHyg != aptCur.ProvHyg))
            {
                if (MessageBox.Show(Lan.g(this,"Change provider?"), "", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    if (assignedDent != 0)
                    {
                        aptCur.ProvNum = assignedDent;
                    }
                     
                    if (assignedHyg != 0)
                    {
                        //the hygienist will only be changed if the spot has a hygienist.
                        aptCur.ProvHyg = assignedHyg;
                    }
                     
                    if (curOp.IsHygiene)
                    {
                        aptCur.IsHygiene = true;
                    }
                    else
                    {
                        //op not marked as hygiene op
                        if (assignedDent == 0)
                        {
                            //no dentist assigned
                            if (assignedHyg != 0)
                            {
                                //hyg is assigned (we don't really have to test for this)
                                aptCur.IsHygiene = true;
                            }
                             
                        }
                        else
                        {
                            //dentist is assigned
                            if (assignedHyg == 0)
                            {
                                //hyg is not assigned
                                aptCur.IsHygiene = false;
                            }
                             
                            //if both dentist and hyg are assigned, it's tricky
                            //only explicitly set it if user has a dentist assigned to the op
                            if (curOp.ProvDentist != 0)
                            {
                                aptCur.IsHygiene = false;
                            }
                             
                        } 
                    } 
                    boolean isplanned = aptCur.AptStatus == ApptStatus.Planned;
                    procsForSingleApt = Procedures.getProcsForSingle(aptCur.AptNum,isplanned);
                    List<long> codeNums = new List<long>();
                    for (int p = 0;p < procsForSingleApt.Count;p++)
                    {
                        codeNums.Add(procsForSingleApt[p].CodeNum);
                    }
                    String calcPattern = Appointments.CalculatePattern(aptCur.ProvNum, aptCur.ProvHyg, codeNums, true);
                    if (!StringSupport.equals(aptCur.Pattern, calcPattern) && !PrefC.getBool(PrefName.AppointmentTimeIsLocked))
                    {
                        if (aptCur.TimeLocked)
                        {
                            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Appointment length is locked.  Change length for new provider anyway?"))
                            {
                                aptCur.Pattern = calcPattern;
                            }
                             
                        }
                        else
                        {
                            //appt time not locked
                            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Change length for new provider?"))
                            {
                                aptCur.Pattern = calcPattern;
                            }
                             
                        } 
                    }
                     
                }
                 
            }
             
        }
         
        if (doesOverlap(aptCur))
        {
            int startingOp = ApptDrawing.getIndexOp(aptCur.Op);
            boolean stillOverlaps = true;
            for (int i = startingOp;i < ApptDrawing.VisOps.Count;i++)
            {
                aptCur.Op = ApptDrawing.VisOps[i].OperatoryNum;
                if (!doesOverlap(aptCur))
                {
                    stillOverlaps = false;
                    break;
                }
                 
            }
            if (stillOverlaps)
            {
                for (int i = startingOp;i >= 0;i--)
                {
                    aptCur.Op = ApptDrawing.VisOps[i].OperatoryNum;
                    if (!doesOverlap(aptCur))
                    {
                        stillOverlaps = false;
                        break;
                    }
                     
                }
            }
             
            if (stillOverlaps)
            {
                MessageBox.Show(Lan.g(this,"Appointment overlaps existing appointment."));
                mouseIsDown = false;
                boolAptMoved = false;
                TempApptSingle.Dispose();
                return ;
            }
             
        }
         
        Operatory opCur = Operatories.getOperatory(aptCur.Op);
        Operatory opOld = Operatories.getOperatory(aptOld.Op);
        if (opOld == null || opCur.SetProspective != opOld.SetProspective)
        {
            if (opCur.SetProspective && PatCur.PatStatus != PatientStatus.Prospective)
            {
                //Don't need to prompt if patient is already prospective.
                if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will be set to Prospective."))
                {
                    Patient patOld = PatCur.copy();
                    PatCur.PatStatus = PatientStatus.Prospective;
                    Patients.update(PatCur,patOld);
                }
                 
            }
            else if (!opCur.SetProspective && PatCur.PatStatus == PatientStatus.Prospective)
            {
                //Do we need to warn about changing FROM prospective? Assume so for now.
                if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will change from Prospective to Patient."))
                {
                    Patient patOld = PatCur.copy();
                    PatCur.PatStatus = PatientStatus.Patient;
                    Patients.update(PatCur,patOld);
                }
                 
            }
              
        }
         
        if (aptCur.AptStatus == ApptStatus.Broken)
        {
            aptCur.AptStatus = ApptStatus.Scheduled;
        }
         
        if (aptCur.AptStatus == ApptStatus.UnschedList)
        {
            aptCur.AptStatus = ApptStatus.Scheduled;
        }
         
        //original position of provider settings
        if (curOp.ClinicNum == 0)
        {
            aptCur.ClinicNum = PatCur.ClinicNum;
        }
        else
        {
            aptCur.ClinicNum = curOp.ClinicNum;
        } 
        if (aptCur.AptStatus == ApptStatus.Planned)
        {
            //if Planned appt is on pinboard
            long plannedAptNum = aptCur.AptNum;
            LabCase lab = LabCases.getForPlanned(aptCur.AptNum);
            aptCur.NextAptNum = aptCur.AptNum;
            aptCur.AptStatus = ApptStatus.Scheduled;
            aptCur.TimeLocked = PrefC.getBool(PrefName.AppointmentTimeIsLocked);
            try
            {
                Appointments.insert(aptCur);
                for (int i = 0;i < pinBoard.getSelectedAppt().TableApptFields.Rows.Count;i++)
                {
                    //now, aptnum is different.
                    //Duplicate the appointment fields.
                    //in SendToPinboard, TableApptFields is refreshed for just the one planned appt instead of all appts for day.
                    ApptField apptField = new ApptField();
                    apptField.AptNum = aptCur.AptNum;
                    apptField.FieldName = PIn.String(pinBoard.getSelectedAppt().TableApptFields.Rows[i]["FieldName"].ToString());
                    apptField.FieldValue = PIn.String(pinBoard.getSelectedAppt().TableApptFields.Rows[i]["FieldValue"].ToString());
                    ApptFields.insert(apptField);
                }
            }
            catch (ApplicationException ex)
            {
                //for(int i=0;i<pinBoard.SelectedAppt.TablePatFields.Rows.Count;i++) {//Duplicate the patient fields.
                //	PatField patField=new PatField();
                //	patField.PatNum=aptCur.PatNum;
                //	patField.FieldName=PIn.String(pinBoard.SelectedAppt.TablePatFields.Rows[i]["FieldName"].ToString());
                //	patField.FieldValue=PIn.String(pinBoard.SelectedAppt.TablePatFields.Rows[i]["FieldValue"].ToString());
                //	PatFields.Insert(patField);
                //}
                MessageBox.Show(ex.Message);
                return ;
            }

            SecurityLogs.MakeLogEntry(Permissions.AppointmentCreate, aptCur.PatNum, aptCur.AptDateTime.ToString() + ", " + aptCur.ProcDescript, aptCur.AptNum);
            List<Procedure> ProcList = Procedures.refresh(aptCur.PatNum);
            boolean procAlreadyAttached = false;
            for (int i = 0;i < ProcList.Count;i++)
            {
                if (ProcList[i].PlannedAptNum == plannedAptNum)
                {
                    //if on the planned apt
                    if (ProcList[i].AptNum > 0)
                    {
                        //already attached to another appt
                        procAlreadyAttached = true;
                    }
                    else
                    {
                        //only update procedures not already attached to another apt
                        Procedures.UpdateAptNum(ProcList[i].ProcNum, aptCur.AptNum);
                    } 
                }
                 
            }
            //.Update(ProcCur,ProcOld);//recall synch not required.
            if (procAlreadyAttached)
            {
                MessageBox.Show(Lan.g(this,"One or more procedures could not be scheduled because they were already attached to another appointment. Someone probably forgot to update the Next appointment in the Chart module."));
                FormApptEdit formAE = new FormApptEdit(aptCur.AptNum);
                checkStatus();
                formAE.IsNew = true;
                formAE.ShowDialog();
                //to force refresh of aptDescript
                if (formAE.DialogResult != DialogResult.OK)
                {
                    //apt gets deleted from within aptEdit window.
                    TempApptSingle.Dispose();
                    refreshModuleScreenPatient();
                    refreshPeriod();
                    mouseIsDown = false;
                    boolAptMoved = false;
                    return ;
                }
                 
            }
             
            if (lab != null)
            {
                LabCases.attachToAppt(lab.LabCaseNum,aptCur.AptNum);
            }
             
        }
        else
        {
            //if planned appointment is on pinboard
            //simple drag off pinboard to a new date/time
            aptCur.Confirmed = DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][0].DefNum;
            try
            {
                //Causes the confirmation status to be reset.
                Appointments.update(aptCur,aptOld);
                SecurityLogs.makeLogEntry(Permissions.AppointmentMove,aptCur.PatNum,aptCur.ProcDescript + ", from " + aptOld.AptDateTime.ToString() + ", to " + aptCur.AptDateTime.ToString(),aptCur.AptNum);
            }
            catch (ApplicationException ex)
            {
                MessageBox.Show(ex.Message);
                return ;
            }
        
        } 
        if (procsForSingleApt == null)
        {
            procsForSingleApt = Procedures.getProcsForSingle(aptCur.AptNum,false);
        }
         
        Procedures.SetProvidersInAppointment(aptCur, procsForSingleApt);
        TempApptSingle.Dispose();
        pinBoard.clearSelected();
        //PinApptSingle.Visible=false;
        //ContrApptSingle.PinBoardIsSelected=false;
        ContrApptSingle.SelectedAptNum = aptCur.AptNum;
        refreshModuleScreenPatient();
        //RefreshModulePatient(PatCurNum);
        refreshPeriod();
        //date moving to for this computer
        setInvalid();
        //date moving to for other computers
        AppointmentL.DateSelected = fromDate;
        setInvalid();
        //for date moved from for other computers.
        AppointmentL.DateSelected = aptCur.AptDateTime;
        mouseIsDown = false;
        boolAptMoved = false;
        List<String> procCodes = new List<String>();
        for (int i = 0;i < procsForSingleApt.Count;i++)
        {
            procCodes.Add(ProcedureCodes.getProcCode((long)procsForSingleApt[i].CodeNum).ProcCode);
        }
        //Recalls.SynchScheduledApptLazy(aptCur.PatNum, aptCur.AptDateTime, procCodes);
        Recalls.synchScheduledApptFull(aptCur.PatNum);
    }

    /**
    * Copied from FormApptsOther. Does not limit appointment creation, only warns user. This check should be run before creating a new appointment.
    */
    private void checkStatus() throws Exception {
        if (PatCur.PatStatus == PatientStatus.Inactive || PatCur.PatStatus == PatientStatus.Archived || PatCur.PatStatus == PatientStatus.Prospective)
        {
            MsgBox.show(this,"Warning. Patient is not active.");
        }
         
        if (PatCur.PatStatus == PatientStatus.Deceased)
        {
            MsgBox.show(this,"Warning. Patient is deceased.");
        }
         
    }

    /**
    * Called when releasing an appointment to make sure it does not overlap any other appointment.  Tests all appts for the day, even if not visible.
    */
    private boolean doesOverlap(Appointment aptCur) throws Exception {
        DateTime aptDateTime = new DateTime();
        for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
        {
            if (DS.Tables["Appointments"].Rows[i]["AptNum"].ToString() == aptCur.AptNum.ToString())
            {
                continue;
            }
             
            if (DS.Tables["Appointments"].Rows[i]["Op"].ToString() != aptCur.Op.ToString())
            {
                continue;
            }
             
            aptDateTime = PIn.DateT(DS.Tables["Appointments"].Rows[i]["AptDateTime"].ToString());
            if (aptDateTime.Date != aptCur.AptDateTime.Date)
            {
                continue;
            }
             
            //tests start time
            if (aptCur.AptDateTime.TimeOfDay >= aptDateTime.TimeOfDay && aptCur.AptDateTime.TimeOfDay < aptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(DS.Tables["Appointments"].Rows[i]["Pattern"].ToString().Length * 5)))
            {
                return true;
            }
             
            //Debug.WriteLine(TimeSpan.FromMinutes(ListDay[i].Pattern.Length*5).ToString());
            //tests stop time
            if (aptCur.AptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(aptCur.Pattern.Length * 5)) > aptDateTime.TimeOfDay && aptCur.AptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(aptCur.Pattern.Length * 5)) <= aptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(DS.Tables["Appointments"].Rows[i]["Pattern"].ToString().Length * 5)))
            {
                return true;
            }
             
            //tests engulf
            if (aptCur.AptDateTime.TimeOfDay <= aptDateTime.TimeOfDay && aptCur.AptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(aptCur.Pattern.Length * 5)) >= aptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(DS.Tables["Appointments"].Rows[i]["Pattern"].ToString().Length * 5)))
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Returns the apptNum of the appointment at these coordinates, or 0 if none.  This is new code which is going to replace some of the outdated code on this page.
    */
    private long hitTestAppt(Point point) throws Exception {
        if (ApptDrawing.VisOps.Count == 0)
        {
            return 0;
        }
         
        //no ops visible.
        int day = ApptDrawing.XPosToDay(point.X);
        if (ApptDrawing.IsWeeklyView)
        {
            //this is a workaround because we start on Monday:
            if (day == 6)
            {
                day = 0;
            }
            else
            {
                day = day + 1;
            } 
        }
         
        //if operatories were just hidden and VisOps is mismatched with ListShort
        int xOp = ApptDrawing.XPosToOpIdx(point.X);
        if (xOp > ApptDrawing.VisOps.Count - 1)
        {
            return 0;
        }
         
        long op = ApptDrawing.VisOps[xOp].OperatoryNum;
        int hour = ApptDrawing.YPosToHour(point.Y);
        int minute = ApptDrawing.YPosToMin(point.Y);
        TimeSpan time = new TimeSpan(hour, minute, 0);
        TimeSpan aptTime = new TimeSpan();
        int aptDayOfWeek = new int();
        for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
        {
            if (op.ToString() != DS.Tables["Appointments"].Rows[i]["Op"].ToString())
            {
                continue;
            }
             
            aptDayOfWeek = (int)PIn.DateT(DS.Tables["Appointments"].Rows[i]["AptDateTime"].ToString()).DayOfWeek;
            if (ApptDrawing.IsWeeklyView && aptDayOfWeek != day)
            {
                continue;
            }
             
            aptTime = PIn.DateT(DS.Tables["Appointments"].Rows[i]["AptDateTime"].ToString()).TimeOfDay;
            if (aptTime <= time && time < aptTime + TimeSpan.FromMinutes(DS.Tables["Appointments"].Rows[i]["Pattern"].ToString().Length * 5))
            {
                return PIn.Long(DS.Tables["Appointments"].Rows[i]["AptNum"].ToString());
            }
             
        }
        return 0;
    }

    /**
    * If the given point is in the bottom few pixels of an appointment, then this returns true.  Use HitTestAppt to figure out which appointment.
    */
    private boolean hitTestApptBottom(Point point) throws Exception {
        long aptnum = hitTestAppt(point);
        if (aptnum == 0)
        {
            return false;
        }
         
        //get the appointment control in order to measure
        ContrApptSingle apptSing = null;
        for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
        {
            //ListDay.Length;i++) {
            if (aptnum.ToString() == DS.Tables["Appointments"].Rows[i]["AptNum"].ToString())
            {
                apptSing = ContrApptSingle3[i];
            }
             
        }
        //find the bottom border of the appointment
        int bottom = apptSing.Bottom;
        if (point.Y > bottom - 8)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Mouse down event anywhere on the sheet.  Could be a blank space or on an actual appointment.
    */
    private void contrApptSheet2_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (infoBubble.Visible)
        {
            infoBubble.Visible = false;
        }
         
        if (ApptDrawing.VisOps.Count == 0)
        {
            return ;
        }
         
        //no ops visible.
        if (mouseIsDown)
        {
            return ;
        }
         
        //if user clicks right mouse button while dragging
        //some of this is a little redundant, but still necessary for now.
        SheetClickedonHour = ApptDrawing.YPosToHour(e.Y);
        SheetClickedonMin = ApptDrawing.YPosToMin(e.Y);
        TimeSpan sheetClickedOnTime = new TimeSpan(SheetClickedonHour, SheetClickedonMin, 0);
        ContrApptSingle.ClickedAptNum = HitTestAppt(e.Location);
        SheetClickedonOp = ApptDrawing.VisOps[ApptDrawing.XPosToOpIdx(e.X)].OperatoryNum;
        SheetClickedonDay = ApptDrawing.XPosToDay(e.X);
        if (!ApptDrawing.IsWeeklyView)
        {
            SheetClickedonDay = ((int)AppointmentL.DateSelected.DayOfWeek) - 1;
        }
         
        Graphics grfx = ContrApptSheet2.CreateGraphics();
        //if clicked on an appt-----------------------------------------------------------------------------------------------
        if (ContrApptSingle.ClickedAptNum != 0)
        {
            if (e.Button == MouseButtons.Left)
            {
                mouseIsDown = true;
            }
             
            int thisIndex = getIndex(ContrApptSingle.ClickedAptNum);
            pinBoard.setSelectedIndex(-1);
            //ContrApptSingle.PinBoardIsSelected=false;
            //unselects previously selected unless it's the same appt
            if (ContrApptSingle.SelectedAptNum != -1 && ContrApptSingle.SelectedAptNum != ContrApptSingle.ClickedAptNum)
            {
                int prevSel = getIndex(ContrApptSingle.SelectedAptNum);
                //has to be done before refresh prev:
                ContrApptSingle.SelectedAptNum = ContrApptSingle.ClickedAptNum;
                if (prevSel != -1)
                {
                    ContrApptSingle3[prevSel].CreateShadow();
                    if (ContrApptSingle3[prevSel].Shadow != null)
                    {
                        grfx.DrawImage(ContrApptSingle3[prevSel].Shadow, ContrApptSingle3[prevSel].Location.X, ContrApptSingle3[prevSel].Location.Y);
                    }
                     
                }
                 
            }
             
            //again, in case missed in loop above:
            ContrApptSingle.SelectedAptNum = ContrApptSingle.ClickedAptNum;
            ContrApptSingle3[thisIndex].CreateShadow();
            grfx.DrawImage(ContrApptSingle3[thisIndex].Shadow, ContrApptSingle3[thisIndex].Location.X, ContrApptSingle3[thisIndex].Location.Y);
            RefreshModuleDataPatient(PIn.Long(ContrApptSingle3[thisIndex].DataRoww["PatNum"].ToString()));
            refreshModuleScreenPatient();
            onPatientSelected(PatCur);
            //RefreshModulePatient(PIn.PInt(ContrApptSingle3[thisIndex].DataRoww["PatNum"].ToString()));
            if (e.Button == MouseButtons.Right)
            {
                //if(ApptDrawing.IsWeeklyView){
                //	menuWeeklyApt.Show(ContrApptSheet2,new Point(e.X,e.Y));
                //}
                //else{
                menuApt.Show(ContrApptSheet2, new Point(e.X, e.Y));
            }
            else
            {
                //}
                TempApptSingle = new ContrApptSingle();
                TempApptSingle.Visible = false;
                //otherwise I get a phantom appt while holding mouse down
                TempApptSingle.DataRoww = ContrApptSingle3[thisIndex].DataRoww;
                TempApptSingle.TableApptFields = ContrApptSingle3[thisIndex].TableApptFields;
                TempApptSingle.TablePatFields = ContrApptSingle3[thisIndex].TablePatFields;
                Controls.Add(TempApptSingle);
                TempApptSingle.Location = ApptSingleDrawing.SetLocation(TempApptSingle.DataRoww, 0, ApptDrawing.VisOps.Count, 0);
                TempApptSingle.Size = ApptSingleDrawing.setSize(TempApptSingle.DataRoww);
                TempApptSingle.PatternShowing = ApptSingleDrawing.GetPatternShowing(TempApptSingle.DataRoww["Pattern"].ToString());
                TempApptSingle.BringToFront();
                //mouseOrigin is in ApptSheet coordinates
                mouseOrigin = e.Location;
                contOrigin = ContrApptSingle3[thisIndex].Location;
                TempApptSingle.createShadow();
                if (HitTestApptBottom(e.Location))
                {
                    ResizingAppt = true;
                    ResizingOrigH = TempApptSingle.Height;
                }
                else
                {
                    ResizingAppt = false;
                } 
            } 
        }
        else
        {
            //not clicked on appt---------------------------------------------------------------------------------------------------
            if (e.Button == MouseButtons.Right)
            {
                int clickedOnBlockCount = 0;
                Schedule[] ListForType = Schedules.GetForType(SchedListPeriod, ScheduleType.Blockout, 0);
                for (int i = 0;i < ListForType.Length;i++)
                {
                    //List<ScheduleOp> listForSched;
                    if (ListForType[i].SchedDate.Date != WeekStartDate.AddDays(SheetClickedonDay).Date)
                    {
                        continue;
                    }
                     
                    if (ListForType[i].StartTime > sheetClickedOnTime || ListForType[i].StopTime <= sheetClickedOnTime)
                    {
                        continue;
                    }
                     
                    for (int p = 0;p < ListForType[i].Ops.Count;p++)
                    {
                        //listForSched=ScheduleOps.GetForSched(ListForType[i].ScheduleNum);
                        if (ListForType[i].Ops[p] == SheetClickedonOp)
                        {
                            clickedOnBlockCount++;
                            break;
                        }
                         
                    }
                }
                //out of ops loop
                if (clickedOnBlockCount > 0)
                {
                    menuBlockout.MenuItems[0].Enabled = true;
                    //Edit
                    menuBlockout.MenuItems[1].Enabled = true;
                    //Cut
                    menuBlockout.MenuItems[2].Enabled = true;
                    //Clone
                    menuBlockout.MenuItems[3].Enabled = false;
                    //paste. Can't paste on top of an existing blockout
                    menuBlockout.MenuItems[4].Enabled = true;
                    //Delete
                    if (clickedOnBlockCount > 1)
                    {
                        MsgBox.show(this,"There are multiple blockouts in this slot.  You should try to delete or move one of them.");
                    }
                     
                }
                else
                {
                    menuBlockout.MenuItems[0].Enabled = false;
                    //edit
                    menuBlockout.MenuItems[1].Enabled = false;
                    //edit
                    menuBlockout.MenuItems[2].Enabled = false;
                    //copy
                    if (BlockoutClipboard == null)
                    {
                        menuBlockout.MenuItems[3].Enabled = false;
                    }
                    else
                    {
                        //paste
                        menuBlockout.MenuItems[3].Enabled = true;
                    } 
                    menuBlockout.MenuItems[4].Enabled = false;
                } 
                //delete
                menuBlockout.Show(ContrApptSheet2, new Point(e.X, e.Y));
            }
             
        } 
        grfx.Dispose();
        pinBoard.Invalidate();
        //if(PinApptSingle.Visible){
        //	PinApptSingle.CreateShadow();
        //	PinApptSingle.Refresh();
        //}
        createAptShadowsOnMain();
    }

    /**
    * 
    */
    private void contrApptSheet2_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
        {
            InfoBubbleDraw(e.Location);
            //decide what the pointer should look like.
            if (HitTestApptBottom(e.Location))
            {
                Cursor = Cursors.SizeNS;
            }
            else
            {
                Cursor = Cursors.Default;
            } 
            return ;
        }
         
        //if resizing an appointment
        if (ResizingAppt)
        {
            //the 1 is an unknown factor
            TempApptSingle.Location = new Point(contOrigin.X + ContrApptSheet2.Location.X + panelSheet.Location.X + 1, contOrigin.Y + ContrApptSheet2.Location.Y + panelSheet.Location.Y + 1);
            //ditto
            TempApptSingle.Height = ResizingOrigH + e.Y - mouseOrigin.Y;
            if (TempApptSingle.Height < 4)
            {
                //unhandled exception if smaller.
                TempApptSingle.Height = 4;
            }
             
            TempApptSingle.createShadow();
            TempApptSingle.Visible = true;
            return ;
        }
         
        //dragging an appointment
        //if(ApptDrawing.IsWeeklyView){
        //	boolAptMoved=false;
        //	return;
        //}
        int thisIndex = getIndex(ContrApptSingle.SelectedAptNum);
        //enhances double clicking
        if ((Math.Abs(e.X - mouseOrigin.X) < 3) & (Math.Abs(e.Y - mouseOrigin.Y) < 3))
        {
            boolAptMoved = false;
            return ;
        }
         
        boolAptMoved = true;
        TempApptSingle.Location = new Point(contOrigin.X + e.X - mouseOrigin.X + ContrApptSheet2.Location.X + panelSheet.Location.X, contOrigin.Y + e.Y - mouseOrigin.Y + ContrApptSheet2.Location.Y + panelSheet.Location.Y);
        TempApptSingle.Visible = true;
    }

    /**
    * Used by parent form when a dialog needs to be displayed on the mouse down.
    */
    public void mouseUpForced() throws Exception {
        if (pinBoard.getSelectedIndex() != -1)
        {
            CancelPinMouseDown = true;
        }
         
        if (!mouseIsDown)
        {
            return ;
        }
         
        mouseIsDown = false;
        if (TempApptSingle != null)
        {
            TempApptSingle.Dispose();
            TempApptSingle = null;
        }
         
    }

    /**
    * Usually dropping an appointment to a new location.
    */
    private void contrApptSheet2_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
        {
            return ;
        }
         
        /*if(ApptDrawing.IsWeeklyView) {
        				ResizingAppt=false;
        				mouseIsDown=false;
        				TempApptSingle.Dispose();
        				return;
        			}*/
        int thisIndex = getIndex(ContrApptSingle.SelectedAptNum);
        Appointment aptOld;
        //Resizing-------------------------------------------------------------------------------------------------------------
        if (ResizingAppt)
        {
            if (!TempApptSingle.Visible)
            {
                //click with no drag
                ResizingAppt = false;
                mouseIsDown = false;
                TempApptSingle.Dispose();
                return ;
            }
             
            //convert Bottom to a time
            int hr = ApptDrawing.ConvertToHour(TempApptSingle.Bottom - ContrApptSheet2.Location.Y - panelSheet.Location.Y);
            int minute = ApptDrawing.ConvertToMin(TempApptSingle.Bottom - ContrApptSheet2.Location.Y - panelSheet.Location.Y);
            TimeSpan bottomSpan = new TimeSpan(hr, minute, 0);
            //subtract to get the new length of appt
            TimeSpan newspan = bottomSpan - PIn.DateT(TempApptSingle.DataRoww["AptDateTime"].ToString()).TimeOfDay;
            int newpatternL = (int)newspan.TotalMinutes / 5;
            if (newpatternL < ApptDrawing.MinPerIncr / 5)
            {
                //eg. if 1 < 10/5, would make appt too short.
                newpatternL = ApptDrawing.MinPerIncr / 5;
            }
            else //sets new pattern length at one increment, typically 2 or 3 5min blocks
            if (newpatternL > 78)
            {
                //max length of 390 minutes
                newpatternL = 78;
            }
              
            String pattern = TempApptSingle.DataRoww["Pattern"].ToString();
            if (newpatternL < pattern.Length)
            {
                //shorten to match new pattern length
                pattern = pattern.Substring(0, newpatternL);
            }
            else if (newpatternL > pattern.Length)
            {
                //make pattern longer.
                pattern = pattern.PadRight(newpatternL, '/');
            }
              
            //Now, check for overlap with other appts.
            DateTime aptDateTime = new DateTime();
            DateTime aptDateTimeCur = PIn.DateT(TempApptSingle.DataRoww["AptDateTime"].ToString());
            for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
            {
                if (DS.Tables["Appointments"].Rows[i]["AptNum"].ToString() == TempApptSingle.DataRoww["AptNum"].ToString())
                {
                    continue;
                }
                 
                if (DS.Tables["Appointments"].Rows[i]["Op"].ToString() != TempApptSingle.DataRoww["Op"].ToString())
                {
                    continue;
                }
                 
                aptDateTime = PIn.DateT(DS.Tables["Appointments"].Rows[i]["AptDateTime"].ToString());
                if (ApptDrawing.IsWeeklyView && aptDateTime.Date != aptDateTimeCur.Date)
                {
                    continue;
                }
                 
                if (aptDateTime < aptDateTimeCur)
                {
                    continue;
                }
                 
                //we don't care about appointments that are earlier than this one
                if (aptDateTime.TimeOfDay < aptDateTimeCur.TimeOfDay + TimeSpan.FromMinutes(5 * pattern.Length))
                {
                    newspan = aptDateTime.TimeOfDay - aptDateTimeCur.TimeOfDay;
                    newpatternL = (int)newspan.TotalMinutes / 5;
                    pattern = pattern.Substring(0, newpatternL);
                }
                 
            }
            if (StringSupport.equals(pattern, ""))
            {
                pattern = "///";
            }
             
            Appointments.SetPattern(PIn.Long(TempApptSingle.DataRoww["AptNum"].ToString()), pattern);
            ResizingAppt = false;
            mouseIsDown = false;
            TempApptSingle.Dispose();
            refreshModuleDataPatient(PatCur.PatNum);
            onPatientSelected(PatCur);
            //RefreshModulePatient(PatCurNum);
            refreshPeriod();
            bubbleAptNum = 0;
            setInvalid();
            return ;
        }
         
        if ((Math.Abs(e.X - mouseOrigin.X) < 7) && (Math.Abs(e.Y - mouseOrigin.Y) < 7))
        {
            boolAptMoved = false;
        }
         
        //it was a click with no drag-----------------------------------------------------------------------------------------
        if (!boolAptMoved)
        {
            mouseIsDown = false;
            TempApptSingle.Dispose();
            return ;
        }
         
        //dragging to pinboard, so place a copy there---------------------------------------------------------------------------
        if (TempApptSingle.Location.X > ContrApptSheet2.Width)
        {
            if (!Security.isAuthorized(Permissions.AppointmentMove))
            {
                mouseIsDown = false;
                TempApptSingle.Dispose();
                return ;
            }
             
            //cannot allow moving completed procedure because it could cause completed procs to change date.  Security must block this.
            if (StringSupport.equals(TempApptSingle.DataRoww["AptStatus"].ToString(), "2"))
            {
                //complete
                mouseIsDown = false;
                TempApptSingle.Dispose();
                MsgBox.show(this,"Not allowed to move completed appointments.");
                return ;
            }
             
            int prevSel = getIndex(ContrApptSingle.SelectedAptNum);
            List<long> list = new List<long>();
            list.Add(ContrApptSingle.SelectedAptNum);
            SendToPinBoard(list);
            //sets selectedAptNum=-1. do before refresh prev
            if (prevSel != -1)
            {
                createAptShadowsOnMain();
                ContrApptSheet2.drawShadow();
            }
             
            refreshModuleDataPatient(PatCur.PatNum);
            onPatientSelected(PatCur);
            //RefreshModulePatient(PatCurNum);
            TempApptSingle.Dispose();
            return ;
        }
         
        //moving to a new location-----------------------------------------------------------------------------------------------
        Appointment apt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
        aptOld = apt.clone();
        int tHr = ApptDrawing.ConvertToHour(TempApptSingle.Location.Y - ContrApptSheet2.Location.Y - panelSheet.Location.Y);
        int tMin = ApptDrawing.ConvertToMin(TempApptSingle.Location.Y - ContrApptSheet2.Location.Y - panelSheet.Location.Y);
        boolean timeWasMoved = tHr != apt.AptDateTime.Hour || tMin != apt.AptDateTime.Minute;
        if (timeWasMoved)
        {
            //no question for notes
            if (apt.AptStatus == ApptStatus.PtNote | apt.AptStatus == ApptStatus.PtNoteCompleted)
            {
                if (!Security.isAuthorized(Permissions.AppointmentMove))
                {
                    mouseIsDown = false;
                    boolAptMoved = false;
                    TempApptSingle.Dispose();
                    return ;
                }
                 
            }
            else
            {
                if (!Security.isAuthorized(Permissions.AppointmentMove) || !MsgBox.show(this,true,"Move Appointment?"))
                {
                    mouseIsDown = false;
                    boolAptMoved = false;
                    TempApptSingle.Dispose();
                    return ;
                }
                 
            } 
        }
         
        //convert loc to new date/time
        DateTime tDate = apt.AptDateTime.Date;
        if (ApptDrawing.IsWeeklyView)
        {
            tDate = WeekStartDate.AddDays(ApptDrawing.ConvertToDay(TempApptSingle.Location.X - ContrApptSheet2.Location.X));
        }
         
        apt.AptDateTime = new DateTime(tDate.Year, tDate.Month, tDate.Day, tHr, tMin, 0);
        List<Procedure> procsMultApts = null;
        Procedure[] procsForOne = null;
        if (AppointmentRuleC.getList().Length > 0)
        {
            List<long> aptNums = new List<long>();
            for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
            {
                aptNums.Add(PIn.Long(DS.Tables["Appointments"].Rows[i]["AptNum"].ToString()));
            }
            //ListDay[i].AptNum;
            procsMultApts = Procedures.GetProcsMultApts(aptNums);
        }
         
        if (AppointmentRuleC.getList().Length > 0)
        {
            long[] aptNums = new long[DS.Tables["Appointments"].Rows.Count];
            for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
            {
                aptNums[i] = PIn.Long(DS.Tables["Appointments"].Rows[i]["AptNum"].ToString());
            }
            //ListDay[i].AptNum;
            procsForOne = Procedures.GetProcsOneApt(apt.AptNum, procsMultApts);
            ArrayList doubleBookedCodes = AppointmentL.GetDoubleBookedCodes(apt, DS.Tables["Appointments"].Copy(), procsMultApts, procsForOne);
            if (doubleBookedCodes.Count > 0)
            {
                //if some codes would be double booked
                if (AppointmentRules.isBlocked(doubleBookedCodes))
                {
                    MessageBox.Show(Lan.g(this,"Not allowed to double book:") + " " + AppointmentRules.getBlockedDescription(doubleBookedCodes));
                    mouseIsDown = false;
                    boolAptMoved = false;
                    TempApptSingle.Dispose();
                    return ;
                }
                 
            }
             
        }
         
        Operatory curOp = ApptDrawing.VisOps[ApptDrawing.ConvertToOp(TempApptSingle.Location.X - ContrApptSheet2.Location.X)];
        apt.Op = curOp.OperatoryNum;
        //Set providers----------------------
        long assignedDent = Schedules.getAssignedProvNumForSpot(SchedListPeriod,curOp,false,apt.AptDateTime);
        long assignedHyg = Schedules.getAssignedProvNumForSpot(SchedListPeriod,curOp,true,apt.AptDateTime);
        List<Procedure> procsForSingleApt = null;
        if (apt.AptStatus != ApptStatus.PtNote && apt.AptStatus != ApptStatus.PtNoteCompleted)
        {
            if (timeWasMoved)
            {
                if (PatCur.AskToArriveEarly > 0)
                {
                    apt.DateTimeAskedToArrive = apt.AptDateTime.AddMinutes(-PatCur.AskToArriveEarly);
                    MessageBox.Show(Lan.g(this,"Ask patient to arrive") + " " + PatCur.AskToArriveEarly + " " + Lan.g(this,"minutes early at") + " " + apt.DateTimeAskedToArrive.ToShortTimeString() + ".");
                }
                else
                {
                    if (apt.DateTimeAskedToArrive.Year > 1880)
                    {
                        MessageBox.Show(Lan.g(this,"Time asked to arrive was ") + apt.DateTimeAskedToArrive.ToShortTimeString());
                    }
                     
                    //OK is only option
                    apt.DateTimeAskedToArrive = DateTime.MinValue;
                } 
            }
             
            //if no dentist/hygenist is assigned to spot, then keep the original dentist/hygenist without prompt.  All appts must have prov.
            if ((assignedDent != 0 && assignedDent != apt.ProvNum) || (assignedHyg != 0 && assignedHyg != apt.ProvHyg))
            {
                Object[] parameters3;
                //Only used in following plugin hook.
                if ((Plugins.hookMethod(this,"ContrAppt.ContrApptSheet2_MouseUp_apptProvChangeQuestion",parameters3)))
                {
                    apt = (Appointment)parameters3[0];
                    assignedDent = (long)parameters3[1];
                    assignedDent = (long)parameters3[2];
                    goto PluginApptProvChangeQuestionEnd
                }
                 
                if (MsgBox.show(this,MsgBoxButtons.YesNo,"Change provider?"))
                {
                    if (assignedDent != 0)
                    {
                        //the dentist will only be changed if the spot has a dentist.
                        apt.ProvNum = assignedDent;
                    }
                     
                    if (assignedHyg != 0)
                    {
                        //the hygienist will only be changed if the spot has a hygienist.
                        apt.ProvHyg = assignedHyg;
                    }
                     
                    if (curOp.IsHygiene)
                    {
                        apt.IsHygiene = true;
                    }
                    else
                    {
                        //op not marked as hygiene op
                        if (assignedDent == 0)
                        {
                            //no dentist assigned
                            if (assignedHyg != 0)
                            {
                                //hyg is assigned (we don't really have to test for this)
                                apt.IsHygiene = true;
                            }
                             
                        }
                        else
                        {
                            //dentist is assigned
                            if (assignedHyg == 0)
                            {
                                //hyg is not assigned
                                apt.IsHygiene = false;
                            }
                             
                            //if both dentist and hyg are assigned, it's tricky
                            //only explicitly set it if user has a dentist assigned to the op
                            if (curOp.ProvDentist != 0)
                            {
                                apt.IsHygiene = false;
                            }
                             
                        } 
                    } 
                    procsForSingleApt = Procedures.getProcsForSingle(apt.AptNum,false);
                    List<long> codeNums = new List<long>();
                    for (int p = 0;p < procsForSingleApt.Count;p++)
                    {
                        codeNums.Add(procsForSingleApt[p].CodeNum);
                    }
                    String calcPattern = Appointments.CalculatePattern(apt.ProvNum, apt.ProvHyg, codeNums, true);
                    if (!StringSupport.equals(apt.Pattern, calcPattern) && !PrefC.getBool(PrefName.AppointmentTimeIsLocked))
                    {
                        if (apt.TimeLocked)
                        {
                            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Appointment length is locked.  Change length for new provider anyway?"))
                            {
                                apt.Pattern = calcPattern;
                            }
                             
                        }
                        else
                        {
                            //appt time not locked
                            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Change length for new provider?"))
                            {
                                apt.Pattern = calcPattern;
                            }
                             
                        } 
                    }
                     
                }
                 
                PluginApptProvChangeQuestionEnd:{
                }
            }
             
        }
         
        if (doesOverlap(apt))
        {
            int startingOp = ApptDrawing.getIndexOp(apt.Op);
            boolean stillOverlaps = true;
            for (int i = startingOp;i < ApptDrawing.VisOps.Count;i++)
            {
                apt.Op = ApptDrawing.VisOps[i].OperatoryNum;
                if (!doesOverlap(apt))
                {
                    stillOverlaps = false;
                    break;
                }
                 
            }
            if (stillOverlaps)
            {
                for (int i = startingOp;i >= 0;i--)
                {
                    apt.Op = ApptDrawing.VisOps[i].OperatoryNum;
                    if (!doesOverlap(apt))
                    {
                        stillOverlaps = false;
                        break;
                    }
                     
                }
            }
             
            if (stillOverlaps)
            {
                MessageBox.Show(Lan.g(this,"Appointment overlaps existing appointment."));
                mouseIsDown = false;
                boolAptMoved = false;
                TempApptSingle.Dispose();
                return ;
            }
             
        }
         
        //end if DoesOverlap
        Operatory opCur = Operatories.getOperatory(apt.Op);
        Operatory opOld = Operatories.getOperatory(aptOld.Op);
        if (opOld == null || opCur.SetProspective != opOld.SetProspective)
        {
            if (opCur.SetProspective && PatCur.PatStatus != PatientStatus.Prospective)
            {
                //Don't need to prompt if patient is already prospective.
                if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will be set to Prospective."))
                {
                    Patient patOld = PatCur.copy();
                    PatCur.PatStatus = PatientStatus.Prospective;
                    Patients.update(PatCur,patOld);
                }
                 
            }
            else if (!opCur.SetProspective && PatCur.PatStatus == PatientStatus.Prospective)
            {
                //Do we need to warn about changing FROM prospective? Assume so for now.
                if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will change from Prospective to Patient."))
                {
                    Patient patOld = PatCur.copy();
                    PatCur.PatStatus = PatientStatus.Patient;
                    Patients.update(PatCur,patOld);
                }
                 
            }
              
        }
         
        Object[] parameters2;
        if ((Plugins.hookMethod(this,"ContrAppt.ContrApptSheet2_MouseUp_apptDoNotUnbreakApptSameDay",parameters2)))
        {
            apt.AptStatus = (ApptStatus)parameters2[2];
            goto PluginApptDoNotUnbreakApptSameDay
        }
         
        if (apt.AptStatus == ApptStatus.Broken && timeWasMoved)
        {
            apt.AptStatus = ApptStatus.Scheduled;
        }
         
        PluginApptDoNotUnbreakApptSameDay:{
        }
        //original location of provider code
        if (curOp.ClinicNum == 0)
        {
            apt.ClinicNum = PatCur.ClinicNum;
        }
        else
        {
            apt.ClinicNum = curOp.ClinicNum;
        } 
        if (apt.AptDateTime != aptOld.AptDateTime && apt.Confirmed != DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][0].DefNum && apt.AptDateTime.Date != DateTime.Today)
        {
            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Reset Confirmation Status?"))
            {
                apt.Confirmed = DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][0].DefNum;
            }
             
        }
         
        try
        {
            //Causes the confirmation status to be reset.
            Appointments.update(apt,aptOld);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
        }

        if (apt.AptStatus != ApptStatus.Complete)
        {
            if (procsForSingleApt == null)
            {
                procsForSingleApt = Procedures.getProcsForSingle(apt.AptNum,false);
            }
             
            Procedures.SetProvidersInAppointment(apt, procsForSingleApt);
        }
         
        SecurityLogs.makeLogEntry(Permissions.AppointmentMove,apt.PatNum,apt.ProcDescript + " from " + aptOld.AptDateTime.ToString() + ", to " + apt.AptDateTime.ToString(),apt.AptNum);
        refreshModuleDataPatient(PatCur.PatNum);
        onPatientSelected(PatCur);
        //RefreshModulePatient(PatCurNum);
        refreshPeriod();
        setInvalid();
        mouseIsDown = false;
        boolAptMoved = false;
        TempApptSingle.Dispose();
        List<String> procCodes = new List<String>();
        if (procsForSingleApt != null)
        {
            for (int i = 0;i < procsForSingleApt.Count;i++)
            {
                procCodes.Add(ProcedureCodes.getProcCode((long)procsForSingleApt[i].CodeNum).ProcCode);
            }
        }
         
        //Recalls.SynchScheduledApptLazy(apt.PatNum,apt.AptDateTime,procCodes);
        Recalls.synchScheduledApptFull(apt.PatNum);
    }

    private void contrApptSheet2_MouseLeave(Object sender, EventArgs e) throws Exception {
        infoBubbleDraw(new Point(-1, -1));
        timerInfoBubble.Enabled = false;
        //redundant?
        Cursor = Cursors.Default;
        Plugins.hookAddCode(this,"ContrAppt.ContrApptSheet2_MouseLeave_end");
    }

    /**
    * 
    */
    private void infoBubble_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        //Calculate the real point in sheet coordinates
        Point p = new Point(e.X + infoBubble.Left - ContrApptSheet2.Left - panelSheet.Left, e.Y + infoBubble.Top - ContrApptSheet2.Top - panelSheet.Top);
        infoBubbleDraw(p);
    }

    /**
    * 
    */
    private void picturePat_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        //Calculate the real point in sheet coordinates
        Point p = new Point(e.X + infoBubble.Left + PicturePat.Left - ContrApptSheet2.Left - panelSheet.Left, e.Y + infoBubble.Top + PicturePat.Top - ContrApptSheet2.Top - panelSheet.Top);
        infoBubbleDraw(p);
    }

    /**
    * Does a hit test to determine if over an appointment.  Fills the bubble with data and then positions it.
    */
    private void infoBubbleDraw(Point p) throws Exception {
        //remember where to draw for hover effect
        if (PrefC.getBool(PrefName.AppointmentBubblesDisabled))
        {
            infoBubble.Visible = false;
            timerInfoBubble.Enabled = false;
            return ;
        }
         
        bubbleLocation = p;
        long aptNum = hitTestAppt(p);
        if (aptNum == 0 || hitTestApptBottom(p))
        {
            if (infoBubble.Visible)
            {
                infoBubble.Visible = false;
                timerInfoBubble.Enabled = false;
            }
             
            return ;
        }
         
        if (aptNum != bubbleAptNum)
        {
            //reset timer for popup delay
            timerInfoBubble.Enabled = false;
            timerInfoBubble.Enabled = true;
            //delay for hover effect 0.28 sec
            bubbleTime = DateTime.Now;
            bubbleAptNum = aptNum;
            //most data is already present in DS.Appointment, but we do need to get the patient picture
            infoBubble.BackgroundImage = new Bitmap(infoBubble.Width, 800);
            Image img = infoBubble.BackgroundImage;
            //alias
            Graphics g = Graphics.FromImage(img);
            //infoBubble.BackgroundImage);
            g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
            g.SmoothingMode = SmoothingMode.HighQuality;
            g.FillRectangle(new SolidBrush(infoBubble.BackColor), 0, 0, img.Width, img.Height);
            DataRow row = null;
            for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
            {
                if (DS.Tables["Appointments"].Rows[i]["AptNum"].ToString() == aptNum.ToString())
                {
                    row = DS.Tables["Appointments"].Rows[i];
                }
                 
            }
            //row will never be null
            Font font = new Font(FontFamily.GenericSansSerif, 10f, FontStyle.Bold);
            float y = 0;
            Brush brush = Brushes.Black;
            g.DrawString(row["patientName"].ToString(), font, brush, 8, y);
            y += (int)g.MeasureString("X", font).Height;
            PicturePat.setImage(null);
            //PatCur==null ||
            if (!StringSupport.equals(row["ImageFolder"].ToString(), "") && PrefC.getAtoZfolderUsed())
            {
                try
                {
                    //Do not use patient image when A to Z folders are disabled.
                    //return;
                    Bitmap patPict = new Bitmap();
                    RefSupport<Bitmap> refVar___0 = new RefSupport<Bitmap>();
                    Documents.GetPatPict(PIn.Long(row["PatNum"].ToString()), CodeBase.ODFileUtils.CombinePaths(ImageStore.getPreferredAtoZpath(), row["ImageFolder"].ToString().Substring(0, 1).ToUpper(), row["ImageFolder"].ToString(), ""), refVar___0);
                    patPict = refVar___0.getValue();
                    PicturePat.setImage(patPict);
                }
                catch (Exception __dummyCatchVar1)
                {
                }
            
            }
             
            float x = 110;
            font = new Font(FontFamily.GenericSansSerif, 9f);
            float rowH = g.MeasureString("X", font).Height;
            y -= 3;
            g.DrawString(row["aptDay"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["aptDate"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["aptTime"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["aptLength"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["provider"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["production"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["confirmed"].ToString(), font, brush, x, y);
            y += rowH;
            y = 120;
            x = 2;
            if (row["AptStatus"].ToString() == (((Enum)ApptStatus.ASAP).ordinal()).ToString())
            {
                g.DrawString(Lan.g("enumApptStatus","ASAP"), font, Brushes.Red, x, y);
                y += rowH;
            }
             
            if (!StringSupport.equals(row["preMedFlag"].ToString(), ""))
            {
                g.DrawString(row["preMedFlag"].ToString(), font, Brushes.Red, x, y);
                y += rowH;
            }
             
            float h = new float();
            if (!StringSupport.equals(row["MedUrgNote"].ToString(), ""))
            {
                h = g.MeasureString(row["MedUrgNote"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["MedUrgNote"].ToString(), font, Brushes.Red, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            if (!StringSupport.equals(row["lab"].ToString(), ""))
            {
                g.DrawString(row["lab"].ToString(), font, Brushes.Red, x, y);
                y += rowH;
            }
             
            if (!StringSupport.equals(row["procs"].ToString(), ""))
            {
                h = g.MeasureString(row["procs"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["procs"].ToString(), font, brush, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            if (!StringSupport.equals(row["Note"].ToString(), ""))
            {
                h = g.MeasureString(row["Note"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["Note"].ToString(), font, Brushes.Blue, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            //patient info---------------------
            y += 3;
            g.DrawLine(new Pen(Brushes.Gray, 1.5f), 3, y, infoBubble.Width - 3, y);
            y += 2;
            g.DrawString(row["patNum"].ToString(), font, brush, x, y);
            y += rowH;
            if (!StringSupport.equals(row["chartNumber"].ToString(), ""))
            {
                g.DrawString(row["chartNumber"].ToString(), font, brush, x, y);
                y += rowH;
            }
             
            g.DrawString(row["billingType"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["age"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["hmPhone"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["wkPhone"].ToString(), font, brush, x, y);
            y += rowH;
            g.DrawString(row["wirelessPhone"].ToString(), font, brush, x, y);
            y += rowH;
            if (!StringSupport.equals(row["contactMethods"].ToString(), ""))
            {
                h = g.MeasureString(row["contactMethods"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["contactMethods"].ToString(), font, brush, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            if (!StringSupport.equals(row["insurance"].ToString(), ""))
            {
                //overkill since it's only one line
                h = g.MeasureString(row["insurance"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["insurance"].ToString(), font, brush, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            if (!StringSupport.equals(row["addrNote"].ToString(), ""))
            {
                h = g.MeasureString(row["addrNote"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["addrNote"].ToString(), font, Brushes.Red, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            if (!StringSupport.equals(row["famFinUrgNote"].ToString(), ""))
            {
                h = g.MeasureString(row["famFinUrgNote"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["famFinUrgNote"].ToString(), font, Brushes.Red, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            if (!StringSupport.equals(row["apptModNote"].ToString(), ""))
            {
                h = g.MeasureString(row["apptModNote"].ToString(), font, infoBubble.Width).Height;
                g.DrawString(row["apptModNote"].ToString(), font, Brushes.Red, new RectangleF(x, y, infoBubble.Width, h));
                y += h;
            }
             
            //other family members?
            g.DrawRectangle(Pens.Gray, 0, 0, infoBubble.Width - 1, (int)y + 4);
            g.Dispose();
            infoBubble.Size = new Size(infoBubble.Width, (int)y + 5);
            infoBubble.BringToFront();
        }
         
        int yval = p.Y + ContrApptSheet2.Top + panelSheet.Top + 10;
        if (yval > panelSheet.Bottom - infoBubble.Height)
        {
            yval = panelSheet.Bottom - infoBubble.Height;
        }
         
        infoBubble.Location = new Point(p.X + ContrApptSheet2.Left + panelSheet.Left + 10, yval);
        /*only show right away if option set for no delay, otherwise, it will not show
        			until mouse had hovered for at least 0.28 seconds(arbitrary #)
        			the timer fires at 0.30 seconds, so the difference was introduced because
        			of what seemed to be inconsistencies in the timer function */
        if (DateTime.Now.AddMilliseconds(-280) > bubbleTime | !PrefC.getBool(PrefName.ApptBubbleDelay))
        {
            infoBubble.Visible = true;
        }
        else
        {
            infoBubble.Visible = false;
        } 
    }

    /**
    * Double click on appt sheet or on a single appointment.
    */
    private void contrApptSheet2_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        mouseIsDown = false;
        //this logic is a little different than mouse down for now because on the first click of a
        //double click, an appointment control is created under the mouse.
        if (ContrApptSingle.ClickedAptNum != 0)
        {
            //on appt
            long patnum = PIn.Long(TempApptSingle.DataRoww["PatNum"].ToString());
            TempApptSingle.Dispose();
            if (Appointments.getOneApt(ContrApptSingle.ClickedAptNum) == null)
            {
                MsgBox.show(this,"Selected appointment no longer exists.");
                refreshModuleDataPeriod();
                refreshModuleScreenPeriod();
                return ;
            }
             
            //security handled inside the form
            FormApptEdit FormAE = new FormApptEdit(ContrApptSingle.ClickedAptNum);
            FormAE.ShowDialog();
            if (FormAE.DialogResult == DialogResult.OK)
            {
                Appointment apt = Appointments.getOneApt(ContrApptSingle.ClickedAptNum);
                if (apt != null && doesOverlap(apt))
                {
                    Appointment aptOld = apt.clone();
                    MsgBox.show(this,"Appointment is too long and would overlap another appointment.  Automatically shortened to fit.");
                    while (doesOverlap(apt))
                    {
                        apt.Pattern = apt.Pattern.Substring(0, apt.Pattern.Length - 1);
                        if (apt.Pattern.Length == 1)
                        {
                            break;
                        }
                         
                    }
                    try
                    {
                        Appointments.update(apt,aptOld);
                    }
                    catch (ApplicationException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                
                }
                 
                moduleSelected(patnum);
                //apt.PatNum);//apt might be null if user deleted appt.
                setInvalid();
            }
             
        }
        else
        {
            //not on apt, so trying to schedule an appointment---------------------------------------------------------------------
            if (!Security.isAuthorized(Permissions.AppointmentCreate))
            {
                return ;
            }
             
            FormPatientSelect FormPS = new FormPatientSelect();
            if (PatCur != null)
            {
                FormPS.InitialPatNum = PatCur.PatNum;
            }
             
            FormPS.ShowDialog();
            if (FormPS.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            if (PatCur == null || FormPS.SelectedPatNum != PatCur.PatNum)
            {
                //if the patient was changed
                refreshModuleDataPatient(FormPS.SelectedPatNum);
                onPatientSelected(PatCur);
            }
             
            //RefreshModulePatient(FormPS.SelectedPatNum);
            Appointment apt;
            if (FormPS.NewPatientAdded)
            {
                //Patient pat=Patients.GetPat(PatCurNum);
                apt = new Appointment();
                apt.PatNum = PatCur.PatNum;
                apt.IsNewPatient = true;
                apt.Pattern = "/X/";
                if (PatCur.PriProv == 0)
                {
                    apt.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
                }
                else
                {
                    apt.ProvNum = PatCur.PriProv;
                } 
                apt.ProvHyg = PatCur.SecProv;
                apt.AptStatus = ApptStatus.Scheduled;
                DateTime d = AppointmentL.DateSelected;
                if (ApptDrawing.IsWeeklyView)
                {
                    d = WeekStartDate.AddDays(SheetClickedonDay);
                }
                 
                //minutes always rounded down.
                int minutes = (int)(ContrAppt.SheetClickedonMin / ApptDrawing.MinPerIncr) * ApptDrawing.MinPerIncr;
                apt.AptDateTime = new DateTime(d.Year, d.Month, d.Day, ContrAppt.SheetClickedonHour, minutes, 0);
                if (PatCur.AskToArriveEarly > 0)
                {
                    apt.DateTimeAskedToArrive = apt.AptDateTime.AddMinutes(-PatCur.AskToArriveEarly);
                    MessageBox.Show(Lan.g(this,"Ask patient to arrive") + " " + PatCur.AskToArriveEarly + " " + Lan.g(this,"minutes early at") + " " + apt.DateTimeAskedToArrive.ToShortTimeString() + ".");
                }
                 
                apt.Op = SheetClickedonOp;
                Operatory curOp = Operatories.getOperatory(apt.Op);
                //New patient. Set to prospective if operatory is set to set prospective.
                if (curOp.SetProspective)
                {
                    if (MsgBox.show(this,MsgBoxButtons.OKCancel,"Patient's status will be set to Prospective."))
                    {
                        Patient patOld = PatCur.copy();
                        PatCur.PatStatus = PatientStatus.Prospective;
                        Patients.update(PatCur,patOld);
                    }
                     
                }
                 
                //if(curOp.ProvDentist!=0) {//if no dentist is assigned to op, then keep the original dentist.  All appts must have prov.
                //  apt.ProvNum=curOp.ProvDentist;
                //}
                //apt.ProvHyg=curOp.ProvHygienist;
                long assignedDent = Schedules.getAssignedProvNumForSpot(SchedListPeriod,curOp,false,apt.AptDateTime);
                long assignedHyg = Schedules.getAssignedProvNumForSpot(SchedListPeriod,curOp,true,apt.AptDateTime);
                if (assignedDent != 0)
                {
                    //if no dentist is assigned to op, then keep the original dentist.  All appts must have prov.
                    apt.ProvNum = assignedDent;
                }
                 
                apt.ProvHyg = assignedHyg;
                apt.IsHygiene = curOp.IsHygiene;
                apt.TimeLocked = PrefC.getBool(PrefName.AppointmentTimeIsLocked);
                if (curOp.ClinicNum == 0)
                {
                    apt.ClinicNum = PatCur.ClinicNum;
                }
                else
                {
                    apt.ClinicNum = curOp.ClinicNum;
                } 
                try
                {
                    Appointments.insert(apt);
                }
                catch (ApplicationException ex)
                {
                    MessageBox.Show(ex.Message);
                }

                FormApptEdit FormAE = new FormApptEdit(apt.AptNum);
                //this is where security log entry is made
                FormAE.IsNew = true;
                FormAE.ShowDialog();
                if (apt.IsNewPatient)
                {
                    AutomationL.trigger(AutomationTrigger.CreateApptNewPat,null,apt.PatNum);
                }
                 
                if (FormAE.DialogResult == DialogResult.OK)
                {
                    refreshModuleDataPatient(PatCur.PatNum);
                    onPatientSelected(PatCur);
                    //RefreshModulePatient(PatCurNum);
                    if (apt != null && doesOverlap(apt))
                    {
                        Appointment aptOld = apt.clone();
                        MsgBox.show(this,"Appointment is too long and would overlap another appointment.  Automatically shortened to fit.");
                        while (doesOverlap(apt))
                        {
                            apt.Pattern = apt.Pattern.Substring(0, apt.Pattern.Length - 1);
                            if (apt.Pattern.Length == 1)
                            {
                                break;
                            }
                             
                        }
                        try
                        {
                            Appointments.update(apt,aptOld);
                        }
                        catch (ApplicationException ex)
                        {
                            MessageBox.Show(ex.Message);
                        }
                    
                    }
                     
                    refreshPeriod();
                    setInvalid();
                }
                 
            }
            else
            {
                //new patient not added
                if (Appointments.hasPlannedEtc(PatCur.PatNum) | (Plugins.hookMethod(this,"ContrAppt.ContrApptSheet2_DoubleClick_apptOtherShow")))
                {
                    displayOtherDlg(true);
                }
                else
                {
                    FormApptsOther FormAO = new FormApptsOther(PatCur.PatNum);
                    //doesn't actually get shown
                    checkStatus();
                    FormAO.InitialClick = true;
                    FormAO.makeAppointment();
                    //if(FormAO.OResult==OtherResult.Cancel) {//this wasn't catching user hitting cancel from within appt edit window
                    //	return;
                    //}
                    if (FormAO.AptNumsSelected.Count > 0)
                    {
                        ContrApptSingle.SelectedAptNum = FormAO.AptNumsSelected[0];
                    }
                     
                    //RefreshModuleDataPatient(FormAO.SelectedPatNum);//patient won't have changed
                    //OnPatientSelected(PatCur.PatNum,PatCur.GetNameLF(),PatCur.Email!="",PatCur.ChartNumber);
                    apt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
                    if (apt != null && doesOverlap(apt))
                    {
                        Appointment aptOld = apt.clone();
                        MsgBox.show(this,"Appointment is too long and would overlap another appointment.  Automatically shortened to fit.");
                        while (doesOverlap(apt))
                        {
                            apt.Pattern = apt.Pattern.Substring(0, apt.Pattern.Length - 1);
                            if (apt.Pattern.Length == 1)
                            {
                                break;
                            }
                             
                        }
                        try
                        {
                            Appointments.update(apt,aptOld);
                        }
                        catch (ApplicationException ex)
                        {
                            MessageBox.Show(ex.Message);
                        }
                    
                    }
                     
                    refreshPeriod();
                    setInvalid();
                } 
            } 
        } 
    }

    /**
    * Displays the Other Appointments for the current patient, then refreshes screen as needed.  initialClick specifies whether the user doubleclicked on a blank time to get to this dialog.
    */
    private void displayOtherDlg(boolean initialClick) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        FormApptsOther FormAO = new FormApptsOther(PatCur.PatNum);
        FormAO.InitialClick = initialClick;
        FormAO.ShowDialog();
        if (FormAO.getOResult() == OtherResult.Cancel)
        {
            return ;
        }
         
        switch(FormAO.getOResult())
        {
            case CopyToPinBoard: 
                sendToPinBoard(FormAO.AptNumsSelected);
                refreshModuleDataPatient(FormAO.SelectedPatNum);
                onPatientSelected(PatCur);
                //RefreshModulePatient(FormAO.SelectedPatNum);
                refreshPeriod();
                break;
            case NewToPinBoard: 
                sendToPinBoard(FormAO.AptNumsSelected);
                refreshModuleDataPatient(FormAO.SelectedPatNum);
                onPatientSelected(PatCur);
                //RefreshModulePatient(FormAO.SelectedPatNum);
                refreshPeriod();
                break;
            case PinboardAndSearch: 
                sendToPinBoard(FormAO.AptNumsSelected);
                if (ApptDrawing.IsWeeklyView)
                {
                    break;
                }
                 
                dateSearch.Text = FormAO.DateJumpToString;
                if (!groupSearch.Visible)
                {
                    //if search not already visible
                    showSearch();
                }
                 
                doSearch();
                break;
            case CreateNew: 
                ContrApptSingle.SelectedAptNum = FormAO.AptNumsSelected[0];
                refreshModuleDataPatient(FormAO.SelectedPatNum);
                onPatientSelected(PatCur);
                //RefreshModulePatient(FormAO.SelectedPatNum);
                Appointment apt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
                if (apt != null && doesOverlap(apt))
                {
                    Appointment aptOld = apt.clone();
                    MsgBox.show(this,"Appointment is too long and would overlap another appointment.  Automatically shortened to fit.");
                    while (doesOverlap(apt))
                    {
                        apt.Pattern = apt.Pattern.Substring(0, apt.Pattern.Length - 1);
                        if (apt.Pattern.Length == 1)
                        {
                            break;
                        }
                         
                    }
                    try
                    {
                        Appointments.update(apt,aptOld);
                    }
                    catch (ApplicationException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                
                }
                 
                refreshPeriod();
                setInvalid();
                break;
            case GoTo: 
                ContrApptSingle.SelectedAptNum = FormAO.AptNumsSelected[0];
                AppointmentL.DateSelected = PIn.Date(FormAO.DateJumpToString);
                refreshModuleDataPatient(FormAO.SelectedPatNum);
                onPatientSelected(PatCur);
                //RefreshModulePatient(FormAO.SelectedPatNum);
                refreshPeriod();
                break;
        
        }
    }

    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            //standard predefined button
            Object.APPLY __dummyScrutVar2 = e.getButton().getTag().ToString();
            if (__dummyScrutVar2.equals("Lists"))
            {
                onLists_Click();
            }
            else if (__dummyScrutVar2.equals("Print"))
            {
                onPrint_Click();
            }
              
        }
        else if (e.getButton().getTag().GetType() == long.class)
        {
            if (PatCur != null)
            {
                Patient pat = Patients.getPat(PatCur.PatNum);
                ProgramL.execute((long)e.getButton().getTag(),pat);
            }
             
        }
          
    }

    /*private void OnPat_Click() {
    			FormPatientSelect formPS = new FormPatientSelect();
    			formPS.ShowDialog();
    			if(formPS.DialogResult!=DialogResult.OK){
    				return;
    			}
    			RefreshModulePatient(formPS.SelectedPatNum);
    			DisplayOtherDlg(false);
    		}*/
    private void onUnschedList_Click() throws Exception {
        Cursor = Cursors.WaitCursor;
        FormUnsched FormUnsched2 = new FormUnsched();
        FormUnsched2.ShowDialog();
        if (FormUnsched2.PinClicked)
        {
            sendToPinBoard(FormUnsched2.AptSelected);
        }
         
        if (FormUnsched2.SelectedPatNum != 0)
        {
            refreshModuleDataPatient(FormUnsched2.SelectedPatNum);
            onPatientSelected(PatCur);
        }
         
        //RefreshModulePatient(FormUnsched2.SelectedPatNum);
        Cursor = Cursors.Default;
    }

    private void onASAPList_Click() throws Exception {
        Cursor = Cursors.WaitCursor;
        FormASAP FormA = new FormASAP();
        FormA.ShowDialog();
        if (FormA.PinClicked)
        {
            sendToPinBoard(FormA.AptSelected);
        }
         
        if (FormA.SelectedPatNum != 0)
        {
            refreshModuleDataPatient(FormA.SelectedPatNum);
            onPatientSelected(PatCur);
        }
         
        //RefreshModulePatient(FormA.SelectedPatNum);
        Cursor = Cursors.Default;
    }

    private void onRecall_Click() throws Exception {
        //Cursor=Cursors.WaitCursor;
        if (FormRecallL == null || FormRecallL.IsDisposed)
        {
            FormRecallL = new FormRecallList();
        }
         
        FormRecallL.Show();
        if (FormRecallL.WindowState == FormWindowState.Minimized)
        {
            FormRecallL.WindowState = FormWindowState.Normal;
        }
         
        FormRecallL.BringToFront();
    }

    //if(FormRL.PinClicked){
    //	SendToPinBoard(FormRL.AptNumsSelected);
    //}
    //if(FormRL.SelectedPatNum!=0){
    //	RefreshModuleDataPatient(FormRL.SelectedPatNum);
    //	OnPatientSelected(PatCur.PatNum,PatCur.GetNameLF(),PatCur.Email!="",PatCur.ChartNumber);
    //RefreshModulePatient(FormRL.SelectedPatNum);
    //}
    //Cursor=Cursors.Default;
    private void onConfirm_Click() throws Exception {
        Cursor = Cursors.WaitCursor;
        FormConfirmList FormC = new FormConfirmList();
        FormC.ShowDialog();
        if (FormC.PinClicked)
        {
            sendToPinBoard(FormC.AptSelected);
        }
         
        if (FormC.SelectedPatNum != 0)
        {
            refreshModuleDataPatient(FormC.SelectedPatNum);
            onPatientSelected(PatCur);
        }
         
        //RefreshModulePatient(FormC.SelectedPatNum);
        Cursor = Cursors.Default;
    }

    private void onTrack_Click() throws Exception {
        Cursor = Cursors.WaitCursor;
        FormTrackNext FormTN = new FormTrackNext();
        FormTN.ShowDialog();
        if (FormTN.PinClicked)
        {
            sendToPinBoard(FormTN.AptSelected);
        }
         
        if (FormTN.SelectedPatNum != 0)
        {
            refreshModuleDataPatient(FormTN.SelectedPatNum);
            onPatientSelected(PatCur);
        }
         
        //RefreshModulePatient(FormTN.SelectedPatNum);
        Cursor = Cursors.Default;
    }

    private void onLists_Click() throws Exception {
        FormApptLists FormA = new FormApptLists();
        FormA.ShowDialog();
        if (FormA.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        switch(FormA.SelectionResult)
        {
            case Recall: 
                onRecall_Click();
                break;
            case Confirm: 
                onConfirm_Click();
                break;
            case Planned: 
                onTrack_Click();
                break;
            case Unsched: 
                onUnschedList_Click();
                break;
            case ASAP: 
                onASAPList_Click();
                break;
        
        }
    }

    private void onPrint_Click() throws Exception {
        if (PrinterSettings.InstalledPrinters.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"Printer not installed"));
        }
        else
        {
            FormApptPrintSetup FormAPS = new FormApptPrintSetup();
            FormAPS.ShowDialog();
            if (FormAPS.DialogResult == DialogResult.OK)
            {
                apptPrintStartTime = FormAPS.ApptPrintStartTime;
                apptPrintStopTime = FormAPS.ApptPrintStopTime;
                apptPrintFontSize = FormAPS.ApptPrintFontSize;
                apptPrintColsPerPage = FormAPS.ApptPrintColsPerPage;
                pagesPrinted = 0;
                pageRow = 0;
                pageColumn = 0;
                printReport();
                ApptDrawing.LineH = 12;
                //Reset the LineH to default.
                copyScheduleToClipboard();
            }
             
        } 
        ModuleSelected(0);
    }

    //Refresh the public variables in ApptDrawing.cs
    /**
    * 
    */
    public void printReport() throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintPage);
        //pd2.DefaultPageSettings.Margins= new Margins(10,40,40,60);
        if (!PrinterL.SetPrinter(pd2, PrintSituation.Appointments, 0, "Daily appointment view for " + apptPrintStartTime.ToShortDateString() + " printed"))
        {
            return ;
        }
         
        try
        {
            pd2.Print();
        }
        catch (Exception __dummyCatchVar2)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        printApptSchedule(sender,e);
    }

    private void printApptSchedule(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        //Logic needs to be added here for calculating if printing will fit on the page. Then call drawing in a loop for number of required pages.
        Rectangle pageBounds = e.PageBounds;
        int headerOffset = 75;
        int footerOffset = 40;
        int marginOffset = 30;
        //The extra 15 is for the right side of that page.  For some reason the right margin needs a little extra room.
        ApptDrawing.ApptSheetWidth = pageBounds.Width - ((marginOffset * 2) + 15);
        ApptDrawing.computeColWidth(apptPrintColsPerPage);
        ApptDrawing.setLineHeight(apptPrintFontSize);
        //Measure size the user set to determine the line height for printout.
        int startHour = apptPrintStartTime.Hour;
        int stopHour = apptPrintStopTime.Hour;
        if (stopHour == 0)
        {
            stopHour = 24;
        }
         
        float totalHeight = ApptDrawing.LineH * ApptDrawing.RowsPerHr * (stopHour - startHour);
        //Figure out how many pages are needed to print.
        int pagesAcross = (int)Math.Ceiling((double)ApptDrawing.VisOps.Count / (double)apptPrintColsPerPage);
        int pagesTall = (int)Math.Ceiling((double)totalHeight / (double)(pageBounds.Height - (headerOffset + footerOffset)));
        int totalPages = pagesAcross * pagesTall;
        if (ApptDrawing.IsWeeklyView)
        {
            pagesAcross = 1;
            totalPages = 1 * pagesTall;
        }
         
        //Decide what page currently on thus knowing what hours to print.
        int hoursPerPage = (int)Math.Floor((double)(pageBounds.Height - (headerOffset + footerOffset)) / (double)(ApptDrawing.LineH * ApptDrawing.RowsPerHr));
        int hourBegin = startHour;
        int hourEnd = hourBegin + hoursPerPage;
        if (pageRow > 0)
        {
            hourBegin = startHour + (hoursPerPage * pageRow);
            hourEnd = hourBegin + hoursPerPage;
        }
         
        if (hourEnd > stopHour)
        {
            //Don't show too many hours.
            hourEnd = stopHour;
        }
         
        ApptDrawing.ApptSheetHeight = ApptDrawing.LineH * ApptDrawing.RowsPerHr * (hourEnd - hourBegin);
        if (hourEnd > 23)
        {
            //Midnight must be 0.
            hourEnd = 0;
        }
         
        DateTime beginTime = new DateTime(1, 1, 1, hourBegin, 0, 0);
        DateTime endTime = new DateTime(1, 1, 1, hourEnd, 0, 0);
        e.Graphics.TranslateTransform(marginOffset, headerOffset);
        //Compensate for header and margin.
        ApptDrawing.drawAllButAppts(e.Graphics,false,beginTime,endTime,apptPrintColsPerPage,pageColumn,apptPrintFontSize,true);
        //Draw the appointments.
        //Clear out the ProvBar from previous page.
        ApptDrawing.ProvBar = new int[ApptDrawing.VisProvs.Count];
        for (int i = 0;i < ApptDrawing.VisProvs.Count;i++)
        {
            ApptDrawing.ProvBar[i] = new int[24 * ApptDrawing.RowsPerHr];
        }
        //[144]; or 24*6
        if (ContrApptSingle3 != null)
        {
            for (int i = 0;i < ContrApptSingle3.Length;i++)
            {
                //I think this is not needed.
                if (ContrApptSingle3[i] != null)
                {
                    ContrApptSingle3[i].Dispose();
                }
                 
                ContrApptSingle3[i] = null;
            }
            ContrApptSingle3 = null;
        }
         
        ContrApptSingle3 = new ContrApptSingle[DS.Tables["Appointments"].Rows.Count];
        for (int i = 0;i < DS.Tables["Appointments"].Rows.Count;i++)
        {
            DataRow dataRoww = DS.Tables["Appointments"].Rows[i];
            if (!ApptDrawing.IsWeeklyView)
            {
                ApptDrawing.provBarShading(dataRoww);
            }
             
            //Always fill prov bars.
            //Filter the list of appointments here for those those within the time frame.
            if (!ApptSingleDrawing.apptWithinTimeFrame(dataRoww,beginTime,endTime,apptPrintColsPerPage,pageColumn))
            {
                continue;
            }
             
            ContrApptSingle3[i] = new ContrApptSingle();
            ContrApptSingle3[i].Visible = false;
            ContrApptSingle3[i].DataRoww = dataRoww;
            ContrApptSingle3[i].TableApptFields = DS.Tables["ApptFields"];
            ContrApptSingle3[i].TablePatFields = DS.Tables["PatFields"];
            ContrApptSingle3[i].PatternShowing = ApptSingleDrawing.GetPatternShowing(dataRoww["Pattern"].ToString());
            ContrApptSingle3[i].Size = ApptSingleDrawing.setSize(dataRoww);
            ContrApptSingle3[i].Location = ApptSingleDrawing.setLocation(dataRoww,hourBegin,apptPrintColsPerPage,pageColumn);
            e.Graphics.ResetTransform();
            e.Graphics.TranslateTransform(ContrApptSingle3[i].Location.X + marginOffset, ContrApptSingle3[i].Location.Y + headerOffset);
            ApptSingleDrawing.DrawEntireAppt(e.Graphics, dataRoww, ContrApptSingle3[i].PatternShowing, ContrApptSingle3[i].Size.Width, ContrApptSingle3[i].Size.Height, false, false, -1, ApptViewItemL.ApptRows, ApptViewItemL.ApptViewCur, DS.Tables["ApptFields"], DS.Tables["PatFields"], apptPrintFontSize, true);
        }
        e.Graphics.ResetTransform();
        //Cover the portions of the appointments that don't belong on the page.
        e.Graphics.FillRectangle(new SolidBrush(Color.White), 0, 0, pageBounds.Width, headerOffset - 1);
        e.Graphics.FillRectangle(new SolidBrush(Color.White), 0, ApptDrawing.ApptSheetHeight + headerOffset, pageBounds.Width, totalHeight);
        //Draw the header
        DrawPrintingHeader(e.Graphics, totalPages, pageBounds.Width, pageBounds.Height);
        pagesPrinted++;
        pageColumn++;
        if (totalPages == pagesPrinted)
        {
            e.HasMorePages = false;
        }
        else
        {
            e.HasMorePages = true;
            if (pagesPrinted == pagesAcross * (pageRow + 1))
            {
                pageRow++;
                pageColumn = 0;
            }
             
        } 
    }

    /**
    * Header and footer for printing.
    */
    private void drawPrintingHeader(Graphics g, int totalPages, float pageWidth, float pageHeight) throws Exception {
        float xPos = 0;
        //starting pos
        float yPos = 25f;
        //starting pos
        //Print Title------------------------------------------------------------------------------
        String title = new String();
        String date = new String();
        if (ApptDrawing.IsWeeklyView)
        {
            title = Lan.g(this,"Weekly Appointments");
            date = WeekStartDate.DayOfWeek.ToString() + " " + WeekStartDate.ToShortDateString() + " - " + WeekEndDate.DayOfWeek.ToString() + " " + WeekEndDate.ToShortDateString();
        }
        else
        {
            title = Lan.g(this,"Daily Appointments");
            date = AppointmentL.DateSelected.DayOfWeek.ToString() + "   " + AppointmentL.DateSelected.ToShortDateString();
        } 
        Font titleFont = new Font("Arial", 12, FontStyle.Bold);
        float xTitle = (float)((pageWidth / 2) - ((g.MeasureString(title, titleFont).Width / 2)));
        g.DrawString(title, titleFont, Brushes.Black, xTitle, yPos);
        //centered
        //Print Date--------------------------------------------------------------------------------
        Font dateFont = new Font("Arial", 8, FontStyle.Regular);
        float xDate = (float)((pageWidth / 2) - ((g.MeasureString(date, dateFont).Width / 2)));
        yPos += 20;
        g.DrawString(date, dateFont, Brushes.Black, xDate, yPos);
        //centered
        //Col titles-----------------------------------------------------------------------------
        if (!ApptDrawing.IsWeeklyView)
        {
            String[] headers = new String[apptPrintColsPerPage];
            Font headerFont = new Font("Arial", 8);
            yPos += 15;
            xPos += (int)(ApptDrawing.TimeWidth + (ApptDrawing.ProvWidth * ApptDrawing.ProvCount) + 30);
            //30 for margins.
            int xCenter = 0;
            for (int i = 0;i < apptPrintColsPerPage;i++)
            {
                if (i == ApptDrawing.VisOps.Count)
                {
                    break;
                }
                 
                int k = apptPrintColsPerPage * pageColumn + i;
                if (k >= ApptDrawing.VisOps.Count)
                {
                    break;
                }
                 
                headers[i] = ApptDrawing.VisOps[k].OpName;
                if (g.MeasureString(headers[i], headerFont).Width > ApptDrawing.ColWidth)
                {
                    RectangleF rf = new RectangleF(xPos, yPos, ApptDrawing.ColWidth, g.MeasureString(headers[i], headerFont).Height);
                    g.DrawString(headers[i], headerFont, Brushes.Black, rf);
                }
                else
                {
                    xCenter = (int)((ApptDrawing.ColWidth / 2) - (g.MeasureString(headers[i], headerFont).Width / 2));
                    g.DrawString(headers[i], headerFont, Brushes.Black, (int)(xPos + xCenter), yPos);
                } 
                xPos += ApptDrawing.ColWidth;
            }
        }
        else
        {
            String columnDate = "";
            Font headerFont = new Font("Arial", 8);
            yPos += 15;
            xPos += (int)(ApptDrawing.TimeWidth)+30;
            //30 for margins.
            int xCenter = 0;
            int day = WeekStartDate.Day;
            int daysInMonth = DateTime.DaysInMonth(WeekStartDate.Year, WeekStartDate.Month);
            for (int i = 0;i < 7;i++)
            {
                switch(i)
                {
                    case 0: 
                        columnDate = "Monday-" + day;
                        break;
                    case 1: 
                        columnDate = "Tuesday-" + day;
                        break;
                    case 2: 
                        columnDate = "Wednesday-" + day;
                        break;
                    case 3: 
                        columnDate = "Thursday-" + day;
                        break;
                    case 4: 
                        columnDate = "Friday-" + day;
                        break;
                    case 5: 
                        columnDate = "Saturday-" + day;
                        break;
                    case 6: 
                        columnDate = "Sunday-" + day;
                        break;
                
                }
                day++;
                if (day > daysInMonth)
                {
                    day = 1;
                }
                 
                //Week contains days in the next month.
                xCenter = (int)((ApptDrawing.ColDayWidth / 2) - (g.MeasureString(columnDate, headerFont).Width / 2));
                g.DrawString(columnDate, headerFont, Brushes.Black, (int)(xPos + xCenter), yPos);
                xPos += ApptDrawing.ColDayWidth;
            }
        } 
        //Print Footer-----------------------------------------------------------------------------
        String page = (pagesPrinted + 1) + " / " + totalPages;
        float xPage = (float)(400 - ((g.MeasureString(page, dateFont).Width / 2)));
        yPos = pageHeight - 40;
        g.DrawString(page, dateFont, Brushes.Black, xPage, yPos);
    }

    /**
    * Sends an image of the current appointment schedule to the clipboard.  Some users 'paste' to their own editor for more control.
    */
    private void copyScheduleToClipboard() throws Exception {
        ArrayList aListStart = new ArrayList();
        ArrayList aListStop = new ArrayList();
        DateTime startTime = new DateTime();
        DateTime stopTime = new DateTime();
        for (int i = 0;i < SchedListPeriod.Count;i++)
        {
            if (SchedListPeriod[i].SchedType != ScheduleType.Provider)
            {
                continue;
            }
             
            if (SchedListPeriod[i].StartTime == TimeSpan.FromHours(0))
            {
                continue;
            }
             
            //ignore notes at midnight
            aListStart.Add(SchedListPeriod[i].SchedDate + SchedListPeriod[i].StartTime);
            aListStop.Add(SchedListPeriod[i].SchedDate + SchedListPeriod[i].StopTime);
        }
        if (aListStart.Count > 0)
        {
            //makes sure there is at least one timeblock
            startTime = (DateTime)aListStart[0];
            for (int i = 0;i < aListStart.Count;i++)
            {
                //if (A) OR (B AND C)
                if ((((DateTime)(aListStart[i])).Hour < startTime.Hour) || (((DateTime)(aListStart[i])).Hour == startTime.Hour && ((DateTime)(aListStart[i])).Minute < startTime.Minute))
                {
                    startTime = (DateTime)aListStart[i];
                }
                 
            }
            stopTime = (DateTime)aListStop[0];
            for (int i = 0;i < aListStop.Count;i++)
            {
                //if (A) OR (B AND C)
                if ((((DateTime)(aListStop[i])).Hour > stopTime.Hour) || (((DateTime)(aListStop[i])).Hour == stopTime.Hour && ((DateTime)(aListStop[i])).Minute > stopTime.Minute))
                {
                    stopTime = (DateTime)aListStop[i];
                }
                 
            }
        }
        else
        {
            //office is closed
            startTime = new DateTime(AppointmentL.DateSelected.Year, AppointmentL.DateSelected.Month, AppointmentL.DateSelected.Day, ApptDrawing.ConvertToHour(-ContrApptSheet2.Location.Y), ApptDrawing.ConvertToMin(-ContrApptSheet2.Location.Y), 0);
            if (ApptDrawing.ConvertToHour(-ContrApptSheet2.Location.Y) + 12 < 23)
            {
                //we will be adding an extra hour later
                //add 12 hours
                stopTime = new DateTime(AppointmentL.DateSelected.Year, AppointmentL.DateSelected.Month, AppointmentL.DateSelected.Day, ApptDrawing.ConvertToHour(-ContrApptSheet2.Location.Y) + 12, ApptDrawing.ConvertToMin(-ContrApptSheet2.Location.Y), 0);
            }
            else
            {
                stopTime = new DateTime(AppointmentL.DateSelected.Year, AppointmentL.DateSelected.Month, AppointmentL.DateSelected.Day, 22, ApptDrawing.ConvertToMin(-ContrApptSheet2.Location.Y), 0);
            } 
        } 
        int recX = 0;
        int recY = (int)(ApptDrawing.LineH * ApptDrawing.RowsPerHr * startTime.Hour);
        int recWidth = (int)ContrApptSheet2.Shadow.Width;
        int recHeight = (int)((ApptDrawing.LineH * ApptDrawing.RowsPerHr * (stopTime.Hour - startTime.Hour + 1)));
        Rectangle imageRect = new Rectangle(recX, recY, recWidth, recHeight);
        //Holds new dimensions for temp image
        Bitmap imageTemp = ContrApptSheet2.Shadow.Clone(imageRect, PixelFormat.DontCare);
        //Clones image and sets size to only show the time open for that day. (Needs to be rewritten)
        Clipboard.SetDataObject(imageTemp);
    }

    /**
    * Clears the pinboard.
    */
    private void butClearPin_Click(Object sender, System.EventArgs e) throws Exception {
        if (pinBoard.getApptList().Count == 0)
        {
            MsgBox.show(this,"There are no appointments on the pinboard to clear.");
            return ;
        }
         
        if (pinBoard.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select an appointment first.");
            return ;
        }
         
        DataRow row = pinBoard.getApptList()[pinBoard.getSelectedIndex()].DataRoww;
        pinBoard.clearSelected();
        ContrApptSingle.SelectedAptNum = -1;
        if (row["AptStatus"].ToString() == (((Enum)ApptStatus.UnschedList).ordinal()).ToString())
        {
            //unscheduled status
            if (PIn.DateT(row["AptDateTime"].ToString()).Year < 1880)
            {
                //Indicates that this was a brand new appt
                Appointment aptCur = Appointments.GetOneApt(PIn.Long(row["AptNum"].ToString()));
                if (aptCur.AptDateTime.Year > 1880)
                {
                }
                else
                {
                    //if date is now present
                    //don't do anything to db.  Appt removed from pinboard above, and Refresh will happen below.
                    Appointments.Delete(PIn.Long(row["AptNum"].ToString()));
                } 
            }
            else
            {
            } 
        }
        else //was actually on the unscheduled list
        //do nothing to database
        if (PIn.DateT(row["AptDateTime"].ToString()).Year > 1880)
        {
        }
        else //already scheduled
        //do nothing to database
        if (row["AptStatus"].ToString() == (((Enum)ApptStatus.Planned).ordinal()).ToString())
        {
        }
        else
        {
            //do nothing except remove it from pinboard
            //Not sure when this would apply, since new appts start out as unsched.  Maybe patient notes?  Leave it just in case.
            //this gets rid of new appointments that never made it off the pinboard
            Appointments.Delete(PIn.Long(row["AptNum"].ToString()));
        }   
        if (pinBoard.getSelectedIndex() == -1)
        {
            if (PatCur == null)
            {
                RefreshModuleDataPatient(0);
            }
            else
            {
                moduleSelected(PatCur.PatNum);
            } 
        }
        else
        {
            RefreshModuleDataPatient(PIn.Long(pinBoard.getApptList()[pinBoard.getSelectedIndex()].DataRoww["PatNum"].ToString()));
            onPatientSelected(PatCur);
        } 
    }

    //RefreshModulePatient(PIn.PInt(pinBoard.ApptList[pinBoard.SelectedIndex].DataRoww["PatNum"].ToString()));
    /**
    * The scrollbar has been moved by the user.
    */
    private void vScrollBar1_Scroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        if (e.Type == ScrollEventType.ThumbTrack)
        {
            //moving
            ContrApptSheet2.IsScrolling = true;
            ContrApptSheet2.Location = new Point(0, -e.NewValue);
        }
         
        if (e.Type == ScrollEventType.EndScroll)
        {
            //done moving
            ContrApptSheet2.IsScrolling = true;
            ContrApptSheet2.Location = new Point(0, -e.NewValue);
            ContrApptSheet2.IsScrolling = false;
            ContrApptSheet2.Select();
        }
         
    }

    /**
    * Occurs whenever the panel holding the appt sheet is resized.
    */
    private void panelSheet_Resize(Object sender, System.EventArgs e) throws Exception {
        vScrollBar1.Maximum = ContrApptSheet2.Height - panelSheet.Height + vScrollBar1.LargeChange;
    }

    private void menuWeekly_Click(Object sender, System.EventArgs e) throws Exception {
        Index __dummyScrutVar5 = ((MenuItem)sender).Index;
        if (__dummyScrutVar5.equals(0))
        {
            onCopyToPin_Click();
        }
         
    }

    private void menuApt_Click(Object sender, System.EventArgs e) throws Exception {
        Index __dummyScrutVar6 = ((MenuItem)sender).Index;
        if (__dummyScrutVar6.equals(0))
        {
            onCopyToPin_Click();
        }
        else //1: divider
        if (__dummyScrutVar6.equals(2))
        {
            onUnsched_Click();
        }
        else if (__dummyScrutVar6.equals(3))
        {
            onBreak_Click();
        }
        else if (__dummyScrutVar6.equals(4))
        {
            onComplete_Click();
        }
        else if (__dummyScrutVar6.equals(5))
        {
            onDelete_Click();
        }
        else if (__dummyScrutVar6.equals(6))
        {
            displayOtherDlg(false);
        }
        else //7: divider
        if (__dummyScrutVar6.equals(8))
        {
            printApptLabel();
        }
        else if (__dummyScrutVar6.equals(9))
        {
            cardPrintFamily = false;
            printApptCard();
        }
        else if (__dummyScrutVar6.equals(10))
        {
            //RIGHT HERE DUMMY
            cardPrintFamily = true;
            printApptCard();
        }
        else if (__dummyScrutVar6.equals(11))
        {
            //for now, this only allows one type of routing slip.  But it could be easily changed.
            FormRpRouting FormR = new FormRpRouting();
            FormR.AptNum = ContrApptSingle.ClickedAptNum;
            List<SheetDef> customSheetDefs = SheetDefs.getCustomForType(SheetTypeEnum.RoutingSlip);
            if (customSheetDefs.Count == 0)
            {
                FormR.SheetDefNum = 0;
            }
            else
            {
                FormR.SheetDefNum = customSheetDefs[0].SheetDefNum;
            } 
            FormR.ShowDialog();
        }
                  
    }

    private void menuBlockout_Click(Object sender, System.EventArgs e) throws Exception {
        Index __dummyScrutVar7 = ((MenuItem)sender).Index;
        if (__dummyScrutVar7.equals(0))
        {
            onBlockEdit_Click();
        }
        else if (__dummyScrutVar7.equals(1))
        {
            onBlockCut_Click();
        }
        else if (__dummyScrutVar7.equals(2))
        {
            onBlockCopy_Click();
        }
        else if (__dummyScrutVar7.equals(3))
        {
            onBlockPaste_Click();
        }
        else if (__dummyScrutVar7.equals(4))
        {
            onBlockDelete_Click();
        }
        else if (__dummyScrutVar7.equals(5))
        {
            onBlockAdd_Click();
        }
        else if (__dummyScrutVar7.equals(6))
        {
            onBlockCutCopyPaste_Click();
        }
        else if (__dummyScrutVar7.equals(7))
        {
            onClearBlockouts_Click();
        }
        else if (__dummyScrutVar7.equals(8))
        {
            onBlockTypes_Click();
        }
                 
    }

    /**
    * Sends current appointment to unscheduled list.
    */
    private void butUnsched_Click(Object sender, System.EventArgs e) throws Exception {
        onUnsched_Click();
    }

    private void butBreak_Click(Object sender, System.EventArgs e) throws Exception {
        onBreak_Click();
    }

    private void butComplete_Click(Object sender, System.EventArgs e) throws Exception {
        onComplete_Click();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        onDelete_Click();
    }

    private void butMakeAppt_Click(Object sender, System.EventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        if (Appointments.hasPlannedEtc(PatCur.PatNum))
        {
            displayOtherDlg(false);
            return ;
        }
         
        FormApptsOther FormAO = new FormApptsOther(PatCur.PatNum);
        //doesn't actually get shown
        checkStatus();
        FormAO.InitialClick = false;
        FormAO.makeAppointment();
        sendToPinBoard(FormAO.AptNumsSelected);
    }

    private void butMakeRecall_Click(Object sender, EventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        if (Appointments.hasPlannedEtc(PatCur.PatNum))
        {
            displayOtherDlg(false);
            return ;
        }
         
        FormApptsOther FormAO = new FormApptsOther(PatCur.PatNum);
        //doesn't actually get shown
        FormAO.InitialClick = false;
        FormAO.makeRecallAppointment();
        if (FormAO.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        sendToPinBoard(FormAO.AptNumsSelected);
        if (ApptDrawing.IsWeeklyView)
        {
            return ;
        }
         
        dateSearch.Text = FormAO.DateJumpToString;
        if (!groupSearch.Visible)
        {
            //if search not already visible
            showSearch();
        }
         
        doSearch();
    }

    private void butFamRecall_Click(Object sender, EventArgs e) throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        if (Appointments.hasPlannedEtc(PatCur.PatNum))
        {
            displayOtherDlg(false);
            return ;
        }
         
        FormApptsOther FormAO = new FormApptsOther(PatCur.PatNum);
        //doesn't actually get shown
        FormAO.InitialClick = false;
        FormAO.makeRecallFamily();
        if (FormAO.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        sendToPinBoard(FormAO.AptNumsSelected);
        if (ApptDrawing.IsWeeklyView)
        {
            return ;
        }
         
        dateSearch.Text = FormAO.DateJumpToString;
        if (!groupSearch.Visible)
        {
            //if search not already visible
            showSearch();
        }
         
        doSearch();
    }

    private void butViewAppts_Click(Object sender, EventArgs e) throws Exception {
        displayOtherDlg(false);
    }

    /*private void MakeAppointment(bool toPinboard) {
    			if(toPinboard) {
    				SendToPinBoard(FormAO.AptNumsSelected);
    				if(ApptDrawing.IsWeeklyView) {
    					break;
    				}
    				dateSearch.Text=FormAO.DateJumpToString;
    				if(!groupSearch.Visible) {//if search not already visible
    					ShowSearch();
    				}
    				DoSearch();
    			}
    			else {
    				RefreshModuleDataPatient(PatCur.PatNum);
    				OnPatientSelected(PatCur.PatNum,PatCur.GetNameLF(),PatCur.Email!="",PatCur.ChartNumber);
    				//RefreshModulePatient(FormAO.SelectedPatNum);
    				Appointment apt=Appointments.GetOneApt(ContrApptSingle.SelectedAptNum);
    				if(apt!=null && DoesOverlap(apt)) {
    					Appointment aptOld=apt.Clone();
    					MsgBox.Show(this,"Appointment is too long and would overlap another appointment.  Automatically shortened to fit.");
    					while(DoesOverlap(apt)) {
    						apt.Pattern=apt.Pattern.Substring(0,apt.Pattern.Length-1);
    						if(apt.Pattern.Length==1) {
    							break;
    						}
    					}
    					try {
    						Appointments.Update(apt,aptOld);
    					}
    					catch(ApplicationException ex) {
    						MessageBox.Show(ex.Message);
    					}
    				}
    				RefreshPeriod();
    				SetInvalid();
    			}
    		}*/
    private void onUnsched_Click() throws Exception {
        if (ContrApptSingle.SelectedAptNum == -1)
        {
            MsgBox.show(this,"Please select an appointment first.");
            return ;
        }
         
        Appointment apt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
        if (!Security.isAuthorized(Permissions.AppointmentMove))
        {
            return ;
        }
         
        if (apt.AptStatus == ApptStatus.PtNote | apt.AptStatus == ApptStatus.PtNoteCompleted)
        {
            return ;
        }
         
        if (MessageBox.Show(Lan.g(this,"Send Appointment to Unscheduled List?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        int thisI = getIndex(ContrApptSingle.SelectedAptNum);
        if (thisI == -1)
        {
            //selected appt is on a different day
            MsgBox.show(this,"Please select an appointment first.");
            return ;
        }
         
        Appointments.setAptStatus(ContrApptSingle.SelectedAptNum,ApptStatus.UnschedList);
        Patient pat = Patients.GetPat(PIn.Long(ContrApptSingle3[thisI].DataRoww["PatNum"].ToString()));
        SecurityLogs.MakeLogEntry(Permissions.AppointmentMove, pat.PatNum, ContrApptSingle3[thisI].DataRoww["procs"].ToString() + ", " + ContrApptSingle3[thisI].DataRoww["AptDateTime"].ToString() + ", Sent to Unscheduled List", PIn.Long(ContrApptSingle3[thisI].DataRoww["AptNum"].ToString()));
        moduleSelected(pat.PatNum);
        setInvalid();
        Recalls.synchScheduledApptFull(apt.PatNum);
    }

    private void onBreak_Click() throws Exception {
        if (!PrefC.getBool(PrefName.BrokenApptCommLogNotAdjustment) && PrefC.getLong(PrefName.BrokenAppointmentAdjustmentType) == 0)
        {
            MsgBox.show(this,"Broken appointment adjustment type is not setup yet.  Please go to Setup | Modules to fix this.");
            return ;
        }
         
        int thisI = getIndex(ContrApptSingle.SelectedAptNum);
        if (thisI == -1)
        {
            //selected appt is on a different day
            MsgBox.show(this,"Please select an appointment first.");
            return ;
        }
         
        Appointment apt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
        Patient pat = Patients.GetPat(PIn.Long(ContrApptSingle3[thisI].DataRoww["PatNum"].ToString()));
        if (!Security.isAuthorized(Permissions.AppointmentEdit))
        {
            return ;
        }
         
        if (apt.AptStatus == ApptStatus.PtNote || apt.AptStatus == ApptStatus.PtNoteCompleted)
        {
            MsgBox.show(this,"Only appointments may be broken, not notes.");
            return ;
        }
         
        if (PrefC.getBool(PrefName.BrokenApptCommLogNotAdjustment))
        {
            if (!MsgBox.show(this,true,"Break appointment?"))
            {
                return ;
            }
             
        }
         
        Appointments.setAptStatus(ContrApptSingle.SelectedAptNum,ApptStatus.Broken);
        SecurityLogs.MakeLogEntry(Permissions.AppointmentMove, pat.PatNum, ContrApptSingle3[thisI].DataRoww["procs"].ToString() + ", " + ContrApptSingle3[thisI].DataRoww["AptDateTime"].ToString() + ", Broken from the Appts module.", PIn.Long(ContrApptSingle3[thisI].DataRoww["AptNum"].ToString()));
        long provNum = PIn.Long(ContrApptSingle3[thisI].DataRoww["ProvNum"].ToString());
        //remember before ModuleSelected
        moduleSelected(pat.PatNum);
        setInvalid();
        if (PrefC.getBool(PrefName.BrokenApptCommLogNotAdjustment))
        {
            Commlog CommlogCur = new Commlog();
            CommlogCur.PatNum = pat.PatNum;
            CommlogCur.CommDateTime = DateTime.Now;
            CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
            CommlogCur.Note = Lan.g(this,"Appt BROKEN for ") + apt.ProcDescript + "  " + apt.AptDateTime.ToString();
            CommlogCur.Mode_ = CommItemMode.None;
            CommlogCur.UserNum = Security.getCurUser().UserNum;
            FormCommItem FormCI = new FormCommItem(CommlogCur);
            FormCI.IsNew = true;
            FormCI.ShowDialog();
        }
        else
        {
            Adjustment AdjustmentCur = new Adjustment();
            AdjustmentCur.DateEntry = DateTime.Today;
            AdjustmentCur.AdjDate = DateTime.Today;
            AdjustmentCur.ProcDate = DateTime.Today;
            AdjustmentCur.ProvNum = provNum;
            AdjustmentCur.PatNum = pat.PatNum;
            AdjustmentCur.AdjType = PrefC.getLong(PrefName.BrokenAppointmentAdjustmentType);
            AdjustmentCur.ClinicNum = pat.ClinicNum;
            FormAdjust FormA = new FormAdjust(pat,AdjustmentCur);
            FormA.IsNew = true;
            FormA.ShowDialog();
        } 
        AutomationL.trigger(AutomationTrigger.BreakAppointment,null,pat.PatNum);
        Recalls.synchScheduledApptFull(apt.PatNum);
    }

    private void onComplete_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.AppointmentEdit))
        {
            return ;
        }
         
        int thisI = getIndex(ContrApptSingle.SelectedAptNum);
        if (thisI == -1)
        {
            //selected appt is on a different day
            MsgBox.show(this,"Please select an appointment first.");
            return ;
        }
         
        Appointment apt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
        if (apt.AptDateTime.Date > DateTime.Today)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Appointment is in the future.  Set complete anyway?"))
            {
                return ;
            }
             
        }
         
        if (apt.AptStatus == ApptStatus.PtNoteCompleted)
        {
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.ProcComplCreate,apt.AptDateTime))
        {
            return ;
        }
         
        //Procedures.SetDateFirstVisit(Appointments.Cur.AptDateTime.Date);//done when making appt instead
        Family fam = Patients.getFamily(apt.PatNum);
        Patient pat = fam.getPatient(apt.PatNum);
        List<InsSub> SubList = InsSubs.refreshForFam(fam);
        List<InsPlan> PlanList = InsPlans.RefreshForSubList(SubList);
        List<PatPlan> PatPlanList = PatPlans.refresh(apt.PatNum);
        if (apt.AptStatus == ApptStatus.PtNote)
        {
            Appointments.setAptStatus(apt.AptNum,ApptStatus.PtNoteCompleted);
            SecurityLogs.MakeLogEntry(Permissions.AppointmentEdit, apt.PatNum, apt.AptDateTime.ToString() + ", Patient NOTE Set Complete", apt.AptNum);
        }
        else
        {
            //shouldn't ever happen, but don't allow procedures to be completed from notes
            InsSub sub1 = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Primary, PatPlanList, PlanList, SubList)), SubList);
            InsSub sub2 = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, PatPlans.GetOrdinal(PriSecMed.Secondary, PatPlanList, PlanList, SubList)), SubList);
            Appointments.setAptStatusComplete(apt.AptNum,sub1.PlanNum,sub2.PlanNum);
            ProcedureL.SetCompleteInAppt(apt, PlanList, PatPlanList, pat.SiteNum, pat.getAge(), SubList);
            //loops through each proc
            SecurityLogs.MakeLogEntry(Permissions.AppointmentEdit, apt.PatNum, ContrApptSingle3[getIndex(apt.AptNum)].DataRoww["procs"].ToString() + ", " + apt.AptDateTime.ToString() + ", Set Complete", apt.AptNum);
        } 
        Recalls.synchScheduledApptFull(apt.PatNum);
        moduleSelected(pat.PatNum);
        setInvalid();
        SecurityLogs.MakeLogEntry(Permissions.ProcComplCreate, pat.PatNum, apt.ProcDescript + ", " + apt.AptDateTime.ToShortDateString() + ", Procedures automatically set complete due to appt being set complete", 0);
    }

    private void onDelete_Click() throws Exception {
        long selectedAptNum = ContrApptSingle.SelectedAptNum;
        Appointment apt = Appointments.getOneApt(selectedAptNum);
        if (!Security.isAuthorized(Permissions.AppointmentEdit))
        {
            return ;
        }
         
        int thisI = getIndex(selectedAptNum);
        if (thisI == -1)
        {
            //selected appt is on a different day
            MsgBox.show(this,"Please select an appointment first.");
            return ;
        }
         
        if (apt.AptStatus == ApptStatus.PtNote | apt.AptStatus == ApptStatus.PtNoteCompleted)
        {
            if (!MsgBox.show(this,true,"Delete Patient Note?"))
            {
                return ;
            }
             
            if (!StringSupport.equals(apt.Note, ""))
            {
                if (MessageBox.Show(Lan.g(this,"Save a copy of this note in CommLog? ") + "\r\n" + "\r\n" + apt.Note, "Question...", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    Commlog CommlogCur = new Commlog();
                    CommlogCur.PatNum = apt.PatNum;
                    CommlogCur.CommDateTime = apt.AptDateTime;
                    CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
                    CommlogCur.Note = "Deleted Patient NOTE from schedule, saved copy: ";
                    CommlogCur.Note += apt.Note;
                    CommlogCur.UserNum = Security.getCurUser().UserNum;
                    //there is no dialog here because it is just a simple entry
                    Commlogs.insert(CommlogCur);
                }
                 
            }
             
            SecurityLogs.MakeLogEntry(Permissions.AppointmentEdit, PatCur.PatNum, ContrApptSingle3[thisI].DataRoww["procs"].ToString() + ", " + ContrApptSingle3[thisI].DataRoww["AptDateTime"].ToString() + ", " + "NOTE Deleted", PIn.Long(ContrApptSingle3[thisI].DataRoww["AptNum"].ToString()));
        }
        else
        {
            if (!MsgBox.show(this,true,"Delete Appointment?"))
            {
                return ;
            }
             
            if (!StringSupport.equals(apt.Note, ""))
            {
                if (MessageBox.Show(Lan.g(this,"Save appointment note in CommLog? ") + "\r\n" + "\r\n" + apt.Note, "Question...", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    Commlog CommlogCur = new Commlog();
                    CommlogCur.PatNum = apt.PatNum;
                    CommlogCur.CommDateTime = apt.AptDateTime;
                    CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
                    CommlogCur.Note = "Deleted Appointment & saved note: ";
                    if (!StringSupport.equals(apt.ProcDescript, ""))
                    {
                        CommlogCur.Note += apt.ProcDescript + ": ";
                    }
                     
                    CommlogCur.Note += apt.Note;
                    CommlogCur.UserNum = Security.getCurUser().UserNum;
                    //there is no dialog here because it is just a simple entry
                    Commlogs.insert(CommlogCur);
                }
                 
            }
             
            SecurityLogs.MakeLogEntry(Permissions.AppointmentEdit, PatCur.PatNum, ContrApptSingle3[thisI].DataRoww["procs"].ToString() + ", " + ContrApptSingle3[thisI].DataRoww["AptDateTime"].ToString() + ", " + "Deleted", PIn.Long(ContrApptSingle3[thisI].DataRoww["AptNum"].ToString()));
        } 
        Appointments.delete(selectedAptNum);
        ContrApptSingle.SelectedAptNum = -1;
        pinBoard.setSelectedIndex(-1);
        DataRow row = new DataRow();
        for (int i = 0;i < pinBoard.getApptList().Count;i++)
        {
            row = pinBoard.getApptList()[i].DataRoww;
            if (selectedAptNum.ToString() == row["AptNum"].ToString())
            {
                pinBoard.setSelectedIndex(i);
                pinBoard.clearSelected();
                pinBoard.setSelectedIndex(-1);
            }
             
        }
        //ContrApptSingle.PinBoardIsSelected=false;
        //PatCurNum=0;
        if (PatCur == null)
        {
            ModuleSelected(0);
        }
        else
        {
            moduleSelected(PatCur.PatNum);
        } 
        setInvalid();
        Recalls.synchScheduledApptFull(apt.PatNum);
    }

    private void printApptLabel() throws Exception {
        LabelSingle.printAppointment(ContrApptSingle.SelectedAptNum);
    }

    private void onBlockCopy_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        //not even enabled if not right click on a blockout
        Schedule SchedCur = getClickedBlockout();
        if (SchedCur == null)
        {
            MessageBox.Show("Blockout not found.");
            return ;
        }
         
        //should never happen
        BlockoutClipboard = SchedCur.copy();
    }

    private void onBlockCut_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        //not even enabled if not right click on a blockout
        Schedule SchedCur = getClickedBlockout();
        if (SchedCur == null)
        {
            MessageBox.Show("Blockout not found.");
            return ;
        }
         
        //should never happen
        BlockoutClipboard = SchedCur.copy();
        Schedules.delete(SchedCur);
        refreshPeriod();
    }

    private void onBlockPaste_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        Schedule sched = BlockoutClipboard.copy();
        sched.Ops = new List<long>();
        sched.Ops.Add(SheetClickedonOp);
        sched.SchedDate = AppointmentL.DateSelected;
        if (ApptDrawing.IsWeeklyView)
        {
            sched.SchedDate = WeekStartDate.AddDays(SheetClickedonDay);
        }
         
        TimeSpan span = sched.StopTime - sched.StartTime;
        TimeSpan timeOfDay = new TimeSpan(SheetClickedonHour, SheetClickedonMin, 0);
        timeOfDay = TimeSpan.FromMinutes(((int)Math.Round((double)timeOfDay.TotalMinutes / (double)ApptDrawing.MinPerIncr)) * ApptDrawing.MinPerIncr);
        sched.StartTime = timeOfDay;
        sched.StopTime = sched.StartTime.Add(span);
        if (sched.StopTime >= TimeSpan.FromDays(1))
        {
            //long span that spills over to next day
            MsgBox.show(this,"This Blockout would go past midnight.");
            return ;
        }
         
        sched.ScheduleNum = 0;
        //Because Schedules.Overlaps() ignores matching ScheduleNums and we used the Copy() function above. Also, we insert below, so a new key will be created anyway.
        if (Schedules.overlaps(sched))
        {
            MsgBox.show(this,"Blockouts not allowed to overlap.");
            return ;
        }
         
        Schedules.insert(sched,true);
        refreshPeriod();
    }

    private void onBlockEdit_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        //not even enabled if not right click on a blockout
        Schedule SchedCur = getClickedBlockout();
        if (SchedCur == null)
        {
            MessageBox.Show("Blockout not found.");
            return ;
        }
         
        //should never happen
        FormScheduleBlockEdit FormSB = new FormScheduleBlockEdit(SchedCur);
        FormSB.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Blockouts, 0, "Blockout edit.");
        refreshPeriod();
    }

    private Schedule getClickedBlockout() throws Exception {
        DateTime startDate = new DateTime();
        DateTime endDate = new DateTime();
        if (ApptDrawing.IsWeeklyView)
        {
            startDate = WeekStartDate;
            endDate = WeekEndDate;
        }
        else
        {
            startDate = AppointmentL.DateSelected;
            endDate = AppointmentL.DateSelected;
        } 
        //no need to do this since schedule is refreshed in RefreshPeriod().
        //SchedListPeriod=Schedules.RefreshPeriod(startDate,endDate);
        Schedule[] ListForType = Schedules.GetForType(SchedListPeriod, ScheduleType.Blockout, 0);
        //now find which blockout
        Schedule SchedCur = null;
        //date is irrelevant. This is just for the time:
        DateTime SheetClickedonTime = new DateTime(2000, 1, 1, SheetClickedonHour, SheetClickedonMin, 0);
        for (int i = 0;i < ListForType.Length;i++)
        {
            //skip if op doesn't match
            if (!ListForType[i].Ops.Contains(SheetClickedonOp))
            {
                continue;
            }
             
            if (ListForType[i].SchedDate.Date != WeekStartDate.AddDays(SheetClickedonDay))
            {
                continue;
            }
             
            if (ListForType[i].StartTime <= SheetClickedonTime.TimeOfDay && SheetClickedonTime.TimeOfDay < ListForType[i].StopTime)
            {
                SchedCur = ListForType[i];
                break;
            }
             
        }
        return SchedCur;
    }

    //might be null;
    private void onBlockDelete_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        Schedule SchedCur = getClickedBlockout();
        if (SchedCur == null)
        {
            MessageBox.Show("Blockout not found.");
            return ;
        }
         
        //should never happen
        Schedules.delete(SchedCur);
        SecurityLogs.MakeLogEntry(Permissions.Blockouts, 0, "Blockout delete.");
        refreshPeriod();
    }

    private void onBlockAdd_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        Schedule SchedCur = new Schedule();
        SchedCur.SchedDate = AppointmentL.DateSelected;
        if (ApptDrawing.IsWeeklyView)
        {
            SchedCur.SchedDate = WeekStartDate.AddDays(SheetClickedonDay);
        }
         
        SchedCur.SchedType = ScheduleType.Blockout;
        FormScheduleBlockEdit FormSB = new FormScheduleBlockEdit(SchedCur);
        FormSB.IsNew = true;
        FormSB.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Blockouts, 0, "Blockout add.");
        refreshPeriod();
    }

    private void onBlockCutCopyPaste_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        FormBlockoutCutCopyPaste FormB = new FormBlockoutCutCopyPaste();
        FormB.DateSelected = AppointmentL.DateSelected;
        if (ApptDrawing.IsWeeklyView)
        {
            FormB.DateSelected = WeekStartDate.AddDays(SheetClickedonDay);
        }
         
        if (comboView.SelectedIndex == 0)
        {
            FormB.ApptViewNumCur = 0;
        }
        else
        {
            FormB.ApptViewNumCur = ApptViewC.getList()[comboView.SelectedIndex - 1].ApptViewNum;
        } 
        FormB.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Blockouts, 0, "Blockout cut copy paste.");
        refreshPeriod();
    }

    private void onClearBlockouts_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Blockouts))
        {
            return ;
        }
         
        if (!MsgBox.show(this,true,"Clear all blockouts for day?"))
        {
            return ;
        }
         
        if (ApptDrawing.IsWeeklyView)
        {
            Schedules.ClearBlockoutsForDay(WeekStartDate.AddDays(SheetClickedonDay));
        }
        else
        {
            Schedules.clearBlockoutsForDay(AppointmentL.DateSelected);
        } 
        SecurityLogs.MakeLogEntry(Permissions.Blockouts, 0, "Blockout clear.");
        refreshPeriod();
    }

    private void onBlockTypes_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.Setup))
        {
            return ;
        }
         
        FormDefinitions FormD = new FormDefinitions(DefCat.BlockoutTypes);
        FormD.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.Setup, 0, "Definitions.");
        refreshPeriod();
    }

    private void onCopyToPin_Click() throws Exception {
        if (!Security.isAuthorized(Permissions.AppointmentMove))
        {
            return ;
        }
         
        //cannot allow moving completed procedure because it could cause completed procs to change date.  Security must block this.
        //ContrApptSingle3[thisIndex].DataRoww;
        Appointment appt = Appointments.getOneApt(ContrApptSingle.SelectedAptNum);
        if (appt == null)
        {
            MsgBox.show(this,"Appointment not found.");
            return ;
        }
         
        if (appt.AptStatus == ApptStatus.Complete)
        {
            MsgBox.show(this,"Not allowed to move completed appointments.");
            return ;
        }
         
        int prevSel = getIndex(ContrApptSingle.SelectedAptNum);
        sendToPinBoard(ContrApptSingle.SelectedAptNum);
        //sets selectedAptNum=-1. do before refresh prev
        if (prevSel != -1)
        {
            createAptShadowsOnMain();
            ContrApptSheet2.drawShadow();
        }
         
    }

    //RefreshModulePatient(PatCurNum);
    //RefreshPeriod();
    private void listConfirmed_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (listConfirmed.IndexFromPoint(e.X, e.Y) == -1)
        {
            return ;
        }
         
        if (ContrApptSingle.SelectedAptNum == -1)
        {
            return ;
        }
         
        long newStatus = DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][listConfirmed.IndexFromPoint(e.X, e.Y)].DefNum;
        Appointments.setConfirmed(ContrApptSingle.SelectedAptNum,newStatus);
        refreshPeriod();
        setInvalid();
    }

    private void butSearch_Click(Object sender, System.EventArgs e) throws Exception {
        if (pinBoard.getApptList().Count == 0)
        {
            MsgBox.show(this,"An appointment must be placed on the pinboard before a search can be done.");
            return ;
        }
         
        if (pinBoard.getSelectedIndex() == -1)
        {
            if (pinBoard.getApptList().Count == 1)
            {
                pinBoard.setSelectedIndex(0);
            }
            else
            {
                MsgBox.show(this,"An appointment on the pinboard must be selected before a search can be done.");
                return ;
            } 
        }
         
        if (!groupSearch.Visible)
        {
            //if search not already visible
            dateSearch.Text = DateTime.Today.ToShortDateString();
            showSearch();
        }
         
        doSearch();
    }

    /**
    * Positions the search box, fills it with initial data except date, and makes it visible.
    */
    private void showSearch() throws Exception {
        ProviderList = new List<Provider>();
        groupSearch.Location = new Point(panelCalendar.Location.X, panelCalendar.Location.Y + pinBoard.Bottom + 2);
        textBefore.Text = "";
        textAfter.Text = "";
        listProviders.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (StringSupport.equals(pinBoard.getSelectedAppt().DataRoww["IsHygiene"].ToString(), "1") && ProviderC.getListShort()[i].ProvNum.ToString() == pinBoard.getSelectedAppt().DataRoww["ProvHyg"].ToString())
            {
                listProviders.Items.Add(ProviderC.getListShort()[i].Abbr);
                //If their appiontment is hygine, the list will start with just their hygine provider
                ProviderList.Add(ProviderC.getListShort()[i]);
            }
            else if (StringSupport.equals(pinBoard.getSelectedAppt().DataRoww["IsHygiene"].ToString(), "0") && ProviderC.getListShort()[i].ProvNum.ToString() == pinBoard.getSelectedAppt().DataRoww["ProvNum"].ToString())
            {
                listProviders.Items.Add(ProviderC.getListShort()[i].Abbr);
                //If their appointment is not hygine, they will start with just their primary provider
                ProviderList.Add(ProviderC.getListShort()[i]);
            }
              
        }
        Plugins.hookAddCode(this,"ContrAppt.ShowSearch_end",listProviders,PIn.Long(pinBoard.getSelectedAppt().DataRoww["AptNum"].ToString()));
        groupSearch.Visible = true;
    }

    private void doSearch() throws Exception {
        Cursor = Cursors.WaitCursor;
        DateTime afterDate = new DateTime();
        try
        {
            afterDate = PIn.Date(dateSearch.Text);
            if (afterDate.Year < 1880)
            {
                throw new Exception();
            }
             
        }
        catch (Exception __dummyCatchVar3)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Invalid date.");
            return ;
        }

        TimeSpan beforeTime = new TimeSpan(0);
        if (!StringSupport.equals(textBefore.Text, ""))
        {
            try
            {
                String[] hrmin = textBefore.Text.Split(new char[]{ ':' }, StringSplitOptions.RemoveEmptyEntries);
                //doesn't work with foreign times.
                String hr = "0";
                if (hrmin.Length > 0)
                {
                    hr = hrmin[0];
                }
                 
                String min = "0";
                if (hrmin.Length > 1)
                {
                    min = hrmin[1];
                }
                 
                beforeTime = TimeSpan.FromHours(PIn.Double(hr)) + TimeSpan.FromMinutes(PIn.Double(min));
                if (radioBeforePM.Checked && beforeTime.Hours < 12)
                {
                    beforeTime = beforeTime + TimeSpan.FromHours(12);
                }
                 
            }
            catch (Exception __dummyCatchVar4)
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"Invalid time.");
                return ;
            }
        
        }
         
        TimeSpan afterTime = new TimeSpan(0);
        if (!StringSupport.equals(textAfter.Text, ""))
        {
            try
            {
                String[] hrmin = textAfter.Text.Split(new char[]{ ':' }, StringSplitOptions.RemoveEmptyEntries);
                //doesn't work with foreign times.
                String hr = "0";
                if (hrmin.Length > 0)
                {
                    hr = hrmin[0];
                }
                 
                String min = "0";
                if (hrmin.Length > 1)
                {
                    min = hrmin[1];
                }
                 
                afterTime = TimeSpan.FromHours(PIn.Double(hr)) + TimeSpan.FromMinutes(PIn.Double(min));
                if (radioAfterPM.Checked && afterTime.Hours < 12)
                {
                    afterTime = afterTime + TimeSpan.FromHours(12);
                }
                 
            }
            catch (Exception __dummyCatchVar5)
            {
                Cursor = Cursors.Default;
                MsgBox.show(this,"Invalid time.");
                return ;
            }
        
        }
         
        if (listProviders.Items.Count == 0)
        {
            Cursor = Cursors.Default;
            MsgBox.show(this,"Please pick a provider.");
            return ;
        }
         
        long[] providers = new long[listProviders.Items.Count];
        List<long> providerNums = new List<long>();
        for (int i = 0;i < providers.Length;i++)
        {
            providers[i] = ProviderList[i].ProvNum;
            providerNums.Add(ProviderList[i].ProvNum);
        }
        //providersList.Add(providers[i]);
        //the result might be empty
        SearchResults = AppointmentL.GetSearchResults(PIn.Long(pinBoard.getSelectedAppt().DataRoww["AptNum"].ToString()), afterDate, providerNums, 10, beforeTime, afterTime);
        listSearchResults.Items.Clear();
        for (int i = 0;i < SearchResults.Count;i++)
        {
            listSearchResults.Items.Add(SearchResults[i].ToString("ddd") + "\t" + SearchResults[i].ToShortDateString() + "     " + SearchResults[i].ToShortTimeString());
        }
        if (listSearchResults.Items.Count > 0)
        {
            listSearchResults.SetSelected(0, true);
            AppointmentL.DateSelected = SearchResults[0];
        }
         
        setWeeklyView(false);
        //jump to that day.
        Cursor = Cursors.Default;
    }

    //scroll to make visible?
    //highlight schedule?*/
    private void butSearchMore_Click(Object sender, System.EventArgs e) throws Exception {
        if (pinBoard.getSelectedAppt() == null)
        {
            MsgBox.show(this,"There is no appointment on the pinboard.");
            return ;
        }
         
        if (SearchResults.Count < 1)
        {
            return ;
        }
         
        dateSearch.Text = SearchResults[SearchResults.Count - 1].ToShortDateString();
        doSearch();
    }

    private void listSearchResults_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        int clickedI = listSearchResults.IndexFromPoint(e.X, e.Y);
        if (clickedI == -1)
        {
            return ;
        }
         
        AppointmentL.DateSelected = SearchResults[clickedI];
        setWeeklyView(false);
    }

    private void butRefresh_Click(Object sender, EventArgs e) throws Exception {
        if (pinBoard.getSelectedAppt() == null)
        {
            if (pinBoard.getApptList().Count > 0)
            {
                //if there are any appointments in the pinboard.
                pinBoard.setSelectedIndex(pinBoard.getApptList().Count - 1);
            }
            else
            {
                //select last appt
                MsgBox.show(this,"There are no appointments on the pinboard.");
                return ;
            } 
        }
         
        doSearch();
    }

    private void butSearchCloseX_Click(Object sender, System.EventArgs e) throws Exception {
        groupSearch.Visible = false;
    }

    private void butSearchClose_Click(Object sender, System.EventArgs e) throws Exception {
        groupSearch.Visible = false;
    }

    private void button1_Click_1(Object sender, System.EventArgs e) throws Exception {
        MessageBox.Show(Lan.g(this, this.GetType().Name));
    }

    private void butLab_Click(Object sender, EventArgs e) throws Exception {
        FormLabCases FormL = new FormLabCases();
        FormL.ShowDialog();
        if (FormL.GoToAptNum != 0)
        {
            Appointment apt = Appointments.getOneApt(FormL.GoToAptNum);
            Patient pat = Patients.getPat(apt.PatNum);
            //PatientSelectedEventArgs eArgs=new OpenDental.PatientSelectedEventArgs(pat.PatNum,pat.GetNameLF(),pat.Email!="",pat.ChartNumber);
            //if(PatientSelected!=null){
            //	PatientSelected(this,eArgs);
            //}
            //Contr_PatientSelected(this,eArgs);
            onPatientSelected(pat);
            GotoModule.gotoAppointment(apt.AptDateTime,apt.AptNum);
        }
         
    }

    /**
    * Happens once per minute.  It used to just move the red timebar down without querying the database.  But now it queries the database so that the waiting room list shows accurately.
    */
    public void tickRefresh() throws Exception {
        try
        {
            DateTime startDate = new DateTime();
            DateTime endDate = new DateTime();
            if (ApptDrawing.IsWeeklyView)
            {
                startDate = WeekStartDate;
                endDate = WeekEndDate;
            }
            else
            {
                startDate = AppointmentL.DateSelected;
                endDate = AppointmentL.DateSelected;
            } 
            if (PrefC.getBool(PrefName.ApptModuleRefreshesEveryMinute))
            {
                refreshPeriod();
            }
            else
            {
                ContrApptSheet2.createShadow();
                createAptShadowsOnMain();
                ContrApptSheet2.drawShadow();
            } 
        }
        catch (Exception __dummyCatchVar6)
        {
        }
    
    }

    //prevents rare malfunctions. For instance, during editing of views, if tickrefresh happens.
    //GC.Collect();
    /**
    * "Ganga's Code: Printing the Appointment Card - 9/9/2004"
    */
    private void printApptCard() throws Exception {
        pd2 = new PrintDocument();
        pd2.PrintPage += new PrintPageEventHandler(this.pd2_PrintApptCard);
        pd2.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd2.OriginAtMargins = true;
        //forces origin to upper left of actual page
        if (PrinterL.SetPrinter(pd2, PrintSituation.Postcard, PatCur.PatNum, "Appointment reminder postcard printed"))
        {
            pd2.Print();
        }
         
    }

    private void pd2_PrintApptCard(Object sender, PrintPageEventArgs ev) throws Exception {
        Graphics g = ev.Graphics;
        //Return Address--------------------------------------------------------------------------
        String str = PrefC.getString(PrefName.PracticeTitle) + "\r\n";
        g.DrawString(str, new Font(FontFamily.GenericSansSerif, 9, FontStyle.Bold), Brushes.Black, 60, 60);
        str = PrefC.getString(PrefName.PracticeAddress) + "\r\n";
        if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
        {
            str += PrefC.getString(PrefName.PracticeAddress2) + "\r\n";
        }
         
        str += PrefC.getString(PrefName.PracticeCity) + "  " + PrefC.getString(PrefName.PracticeST) + "  " + PrefC.getString(PrefName.PracticeZip) + "\r\n";
        String phone = PrefC.getString(PrefName.PracticePhone);
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && phone.Length == 10)
        {
            str += "(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6);
        }
        else
        {
            //any other phone format
            str += phone;
        } 
        g.DrawString(str, new Font(FontFamily.GenericSansSerif, 8), Brushes.Black, 60, 75);
        //Body text-------------------------------------------------------------------------------
        String name = new String();
        str = "Appointment Reminders:" + "\r\n\r\n";
        Appointment[] aptsOnePat = new Appointment[]();
        Family fam = Patients.getFamily(PatCur.PatNum);
        Patient pat = fam.getPatient(PatCur.PatNum);
        for (int i = 0;i < fam.ListPats.Length;i++)
        {
            if (!cardPrintFamily && fam.ListPats[i].PatNum != pat.PatNum)
            {
                continue;
            }
             
            name = fam.ListPats[i].FName;
            if (name.Length > 15)
            {
                //trim name so it won't be too long
                name = name.Substring(0, 15);
            }
             
            aptsOnePat = Appointments.GetForPat(fam.ListPats[i].PatNum);
            for (int a = 0;a < aptsOnePat.Length;a++)
            {
                if (aptsOnePat[a].AptDateTime.Date <= DateTime.Today)
                {
                    continue;
                }
                 
                //ignore old appts
                if (aptsOnePat[a].AptStatus != ApptStatus.Scheduled && aptsOnePat[a].AptStatus != ApptStatus.ASAP)
                {
                    continue;
                }
                 
                str += name + ": " + aptsOnePat[a].AptDateTime.ToShortDateString() + " " + aptsOnePat[a].AptDateTime.ToShortTimeString() + "\r\n";
            }
        }
        g.DrawString(str, new Font(FontFamily.GenericSansSerif, 9), Brushes.Black, 40, 180);
        //Patient's Address-----------------------------------------------------------------------
        Patient guar;
        if (cardPrintFamily)
        {
            guar = fam.ListPats[0].Copy();
        }
        else
        {
            guar = pat.copy();
        } 
        str = guar.FName + " " + guar.LName + "\r\n" + guar.Address + "\r\n";
        if (!StringSupport.equals(guar.Address2, ""))
        {
            str += guar.Address2 + "\r\n";
        }
         
        str += guar.City + "  " + guar.State + "  " + guar.Zip;
        g.DrawString(str, new Font(FontFamily.GenericSansSerif, 11), Brushes.Black, 300, 240);
        //CommLog entry---------------------------------------------------------------------------
        Commlog CommlogCur = new Commlog();
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.MISC);
        CommlogCur.Note = "Appointment card sent";
        CommlogCur.PatNum = pat.PatNum;
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        //there is no dialog here because it is just a simple entry
        Commlogs.insert(CommlogCur);
        ev.HasMorePages = false;
    }

    private void timerInfoBubble_Tick(Object sender, EventArgs e) throws Exception {
        infoBubbleDraw(bubbleLocation);
        timerInfoBubble.Enabled = false;
    }

    private void butMonth_Click(Object sender, EventArgs e) throws Exception {
        FormMonthView FormM = new FormMonthView();
        FormM.ShowDialog();
    }

    private void timerWaitingRoom_Tick(Object sender, EventArgs e) throws Exception {
        fillWaitingRoom();
    }

    private void timerTests_Tick(Object sender, EventArgs e) throws Exception {
    }

    //stress test #2:
    //ContrApptSheet2.CreateShadow();
    //CreateAptShadows();
    //ContrApptSheet2.DrawShadow();
    //stress test #3:
    //stressCounter++;
    //if(Math.IEEERemainder((double)stressCounter,500d)==0) {
    //	Debug.WriteLine("stress counter: "+stressCounter.ToString());
    //}
    //FillWaitingRoom();
    //stress test #4:
    //LayoutScrollOpProv();
    //if(PatCur!=null) {
    //	this.ModuleSelected(PatCur.PatNum);
    //}
    private void butGraph_Click(Object sender, EventArgs e) throws Exception {
        //only visible on computers at OD corporate.
        FormGraphEmployeeTime form = new FormGraphEmployeeTime();
        form.ShowDialog();
    }

    private void butProvPick_Click(Object sender, EventArgs e) throws Exception {
        FormProvidersMultiPick FormPMP = new FormProvidersMultiPick();
        FormPMP.SelectedProviders = ProviderList;
        FormPMP.ShowDialog();
        if (FormPMP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        listProviders.Items.Clear();
        for (int i = 0;i < FormPMP.SelectedProviders.Count;i++)
        {
            listProviders.Items.Add(FormPMP.SelectedProviders[i].Abbr);
        }
        ProviderList = FormPMP.SelectedProviders;
        if (pinBoard.getSelectedAppt() == null)
        {
            MsgBox.show(this,"There is no appointment on the pinboard.");
            return ;
        }
         
        doSearch();
    }

    private void butProvHygenist_Click(Object sender, EventArgs e) throws Exception {
        ProviderList = new List<Provider>();
        listProviders.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (ApptViewItemL.ProvIsInView(ProviderC.getListShort()[i].ProvNum))
            {
                if (ProviderC.getListShort()[i].IsSecondary)
                {
                    ProviderList.Add(ProviderC.getListShort()[i]);
                    listProviders.Items.Add(ProviderC.getListShort()[i].Abbr);
                }
                 
            }
             
        }
        if (pinBoard.getSelectedAppt() == null)
        {
            MsgBox.show(this,"There is no appointment on the pinboard.");
            return ;
        }
         
        doSearch();
    }

    private void butProvDentist_Click(Object sender, EventArgs e) throws Exception {
        ProviderList = new List<Provider>();
        listProviders.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            if (ApptViewItemL.ProvIsInView(ProviderC.getListShort()[i].ProvNum))
            {
                if (!ProviderC.getListShort()[i].IsSecondary)
                {
                    ProviderList.Add(ProviderC.getListShort()[i]);
                    listProviders.Items.Add(ProviderC.getListShort()[i].Abbr);
                }
                 
            }
             
        }
        if (pinBoard.getSelectedAppt() == null)
        {
            MsgBox.show(this,"There is no appointment on the pinboard.");
            return ;
        }
         
        doSearch();
    }

}


//private void butTest_Click(object sender,EventArgs e) {
//	timerTests.Enabled=!timerTests.Enabled;
//}
/*
		private void butStress_Click(object sender,EventArgs e) {
			timerStress.Enabled=true;
		}
		private void timerStress_Tick(object sender,EventArgs e) {
			
		}*/