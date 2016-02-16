//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:38 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.AutomationL;
import OpenDental.Bridges.ECW;
import OpenDental.CellEventArgs;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.DateTimeOD;
import OpenDental.FormAdjust;
import OpenDental.FormApptEdit;
import OpenDental.FormApptFieldEdit;
import OpenDental.FormApptFieldPickEdit;
import OpenDental.FormAuditOneType;
import OpenDental.FormCommItem;
import OpenDental.FormEhrAptObses;
import OpenDental.FormInsPlanSelect;
import OpenDental.FormLabCaseEdit;
import OpenDental.FormLabCaseSelect;
import OpenDental.FormProcCodes;
import OpenDental.FormProcEdit;
import OpenDental.FormProviderPick;
import OpenDental.FormReqAppt;
import OpenDental.FormTaskEdit;
import OpenDental.FormTaskListSelect;
import OpenDental.FormTxtMsgEdit;
import OpenDental.Lan;
import OpenDental.MigraDocHelper;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ProcedureL;
import OpenDental.Properties.Resources;
import OpenDental.TableTimeBar;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptField;
import OpenDentBusiness.ApptFieldDefs;
import OpenDentBusiness.ApptFields;
import OpenDentBusiness.ApptFieldType;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.CommItemTypeAuto;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.Commlogs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Employees;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fees;
import OpenDentBusiness.HL7.MessageConstructor;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7Defs;
import OpenDentBusiness.HL7MessageStatus;
import OpenDentBusiness.HL7Msg;
import OpenDentBusiness.HL7Msgs;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.LabCases;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.ProcApptColors;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.Tasks;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.UI.ApptDrawing;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;
import OpenDentBusiness.YN;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.ODGrid gridPatient;
    private OpenDental.UI.ODGrid gridComm;
    private IContainer components = new IContainer();
    private ComboBox comboConfirmed = new ComboBox();
    private ComboBox comboUnschedStatus = new ComboBox();
    private Label label4 = new Label();
    private ComboBox comboStatus = new ComboBox();
    private Label label5 = new Label();
    private Label labelStatus = new Label();
    private OpenDental.UI.Button butAudit;
    private OpenDental.UI.Button butTask;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butPin;
    private Label label24 = new Label();
    private CheckBox checkIsHygiene = new CheckBox();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private ComboBox comboAssistant = new ComboBox();
    private ComboBox comboProvHyg = new ComboBox();
    private ComboBox comboProvNum = new ComboBox();
    private Label label12 = new Label();
    private CheckBox checkIsNewPatient = new CheckBox();
    private Label label3 = new Label();
    private Label label2 = new Label();
    private OpenDental.UI.ODGrid gridProc;
    private System.Windows.Forms.Button butSlider = new System.Windows.Forms.Button();
    private TableTimeBar tbTime;
    private Label label6 = new Label();
    private TextBox textTime = new TextBox();
    private OpenDental.ODtextBox textNote;
    private Label labelApptNote = new Label();
    private OpenDental.UI.Button butAddComm;
    public boolean PinIsVisible = new boolean();
    public boolean PinClicked = new boolean();
    public boolean IsNew = new boolean();
    private DataSet DS = new DataSet();
    private Appointment AptCur;
    private Appointment AptOld;
    /**
    * The string time pattern in the current increment. Not in the 5 minute increment.
    */
    private StringBuilder strBTime = new StringBuilder();
    private boolean mouseIsDown = new boolean();
    private Point mouseOrigin = new Point();
    private Point sliderOrigin = new Point();
    private List<InsPlan> PlanList = new List<InsPlan>();
    private List<InsSub> SubList = new List<InsSub>();
    private Patient pat;
    private Family fam;
    private ToolTip toolTip1 = new ToolTip();
    private ContextMenu contextMenuTimeArrived = new ContextMenu();
    private MenuItem menuItemArrivedNow = new MenuItem();
    private ContextMenu contextMenuTimeSeated = new ContextMenu();
    private MenuItem menuItemSeatedNow = new MenuItem();
    private ContextMenu contextMenuTimeDismissed = new ContextMenu();
    private MenuItem menuItemDismissedNow = new MenuItem();
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butDeleteProc;
    private OpenDental.UI.Button butComplete;
    private CheckBox checkTimeLocked = new CheckBox();
    private OpenDental.UI.Button butPickHyg;
    private OpenDental.UI.Button butPickDentist;
    private OpenDental.UI.ODGrid gridFields;
    private TextBox textTimeAskedToArrive = new TextBox();
    private Label label8 = new Label();
    private OpenDental.UI.Button butPDF;
    /**
    * This is the way to pass a "signal" up to the parent form that OD is to close.
    */
    public boolean CloseOD = new boolean();
    private ListBox listQuickAdd = new ListBox();
    private Panel panel1 = new Panel();
    private TextBox textRequirement = new TextBox();
    private OpenDental.UI.Button butRequirement;
    private OpenDental.UI.Button butInsPlan2;
    private OpenDental.UI.Button butInsPlan1;
    private TextBox textInsPlan2 = new TextBox();
    private Label labelInsPlan2 = new Label();
    private TextBox textInsPlan1 = new TextBox();
    private Label labelInsPlan1 = new Label();
    private TextBox textTimeDismissed = new TextBox();
    private Label label7 = new Label();
    private TextBox textTimeSeated = new TextBox();
    private Label label1 = new Label();
    private TextBox textTimeArrived = new TextBox();
    private Label labelTimeArrived = new Label();
    private TextBox textLabCase = new TextBox();
    private OpenDental.UI.Button butLab;
    private Label label9 = new Label();
    private OpenDental.UI.Button butColorClear;
    private System.Windows.Forms.Button butColor = new System.Windows.Forms.Button();
    private OpenDental.UI.Button butText;
    private Label labelQuickAdd = new Label();
    private OpenDental.UI.Button butSyndromicObservations;
    private Label labelSyndromicObservations = new Label();
    /**
    * True if appt was double clicked on from the chart module gridProg.  Currently only used to trigger an appointment overlap check.
    */
    public boolean IsInChartModule = new boolean();
    /**
    * 
    */
    public FormApptEdit(long aptNum) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        DS = Appointments.getApptEdit(aptNum);
        AptCur = Appointments.TableToObject(DS.Tables["Appointment"]);
        AptOld = AptCur.clone();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptEdit.class);
        this.comboConfirmed = new System.Windows.Forms.ComboBox();
        this.comboUnschedStatus = new System.Windows.Forms.ComboBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboStatus = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.labelStatus = new System.Windows.Forms.Label();
        this.label24 = new System.Windows.Forms.Label();
        this.checkIsHygiene = new System.Windows.Forms.CheckBox();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.comboAssistant = new System.Windows.Forms.ComboBox();
        this.comboProvHyg = new System.Windows.Forms.ComboBox();
        this.comboProvNum = new System.Windows.Forms.ComboBox();
        this.label12 = new System.Windows.Forms.Label();
        this.checkIsNewPatient = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.labelApptNote = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.textTime = new System.Windows.Forms.TextBox();
        this.butSlider = new System.Windows.Forms.Button();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.checkTimeLocked = new System.Windows.Forms.CheckBox();
        this.contextMenuTimeArrived = new System.Windows.Forms.ContextMenu();
        this.menuItemArrivedNow = new System.Windows.Forms.MenuItem();
        this.contextMenuTimeSeated = new System.Windows.Forms.ContextMenu();
        this.menuItemSeatedNow = new System.Windows.Forms.MenuItem();
        this.contextMenuTimeDismissed = new System.Windows.Forms.ContextMenu();
        this.menuItemDismissedNow = new System.Windows.Forms.MenuItem();
        this.textTimeAskedToArrive = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.listQuickAdd = new System.Windows.Forms.ListBox();
        this.labelQuickAdd = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.labelSyndromicObservations = new System.Windows.Forms.Label();
        this.butSyndromicObservations = new OpenDental.UI.Button();
        this.label9 = new System.Windows.Forms.Label();
        this.butColorClear = new OpenDental.UI.Button();
        this.butColor = new System.Windows.Forms.Button();
        this.textRequirement = new System.Windows.Forms.TextBox();
        this.butRequirement = new OpenDental.UI.Button();
        this.butInsPlan2 = new OpenDental.UI.Button();
        this.butInsPlan1 = new OpenDental.UI.Button();
        this.textInsPlan2 = new System.Windows.Forms.TextBox();
        this.labelInsPlan2 = new System.Windows.Forms.Label();
        this.textInsPlan1 = new System.Windows.Forms.TextBox();
        this.labelInsPlan1 = new System.Windows.Forms.Label();
        this.textTimeDismissed = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.textTimeSeated = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textTimeArrived = new System.Windows.Forms.TextBox();
        this.labelTimeArrived = new System.Windows.Forms.Label();
        this.textLabCase = new System.Windows.Forms.TextBox();
        this.butLab = new OpenDental.UI.Button();
        this.butPickHyg = new OpenDental.UI.Button();
        this.butPickDentist = new OpenDental.UI.Button();
        this.gridFields = new OpenDental.UI.ODGrid();
        this.gridPatient = new OpenDental.UI.ODGrid();
        this.gridComm = new OpenDental.UI.ODGrid();
        this.gridProc = new OpenDental.UI.ODGrid();
        this.butPDF = new OpenDental.UI.Button();
        this.butComplete = new OpenDental.UI.Button();
        this.butDeleteProc = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.textNote = new OpenDental.ODtextBox();
        this.butAddComm = new OpenDental.UI.Button();
        this.tbTime = new OpenDental.TableTimeBar();
        this.butAudit = new OpenDental.UI.Button();
        this.butTask = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butPin = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butText = new OpenDental.UI.Button();
        this.panel1.SuspendLayout();
        this.SuspendLayout();
        //
        // comboConfirmed
        //
        this.comboConfirmed.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboConfirmed.Location = new System.Drawing.Point(114, 42);
        this.comboConfirmed.MaxDropDownItems = 30;
        this.comboConfirmed.Name = "comboConfirmed";
        this.comboConfirmed.Size = new System.Drawing.Size(126, 21);
        this.comboConfirmed.TabIndex = 84;
        //
        // comboUnschedStatus
        //
        this.comboUnschedStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboUnschedStatus.Location = new System.Drawing.Point(114, 21);
        this.comboUnschedStatus.MaxDropDownItems = 100;
        this.comboUnschedStatus.Name = "comboUnschedStatus";
        this.comboUnschedStatus.Size = new System.Drawing.Size(126, 21);
        this.comboUnschedStatus.TabIndex = 83;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(1, 24);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(111, 15);
        this.label4.TabIndex = 82;
        this.label4.Text = "Unscheduled Status";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboStatus
        //
        this.comboStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatus.Location = new System.Drawing.Point(114, 0);
        this.comboStatus.MaxDropDownItems = 10;
        this.comboStatus.Name = "comboStatus";
        this.comboStatus.Size = new System.Drawing.Size(126, 21);
        this.comboStatus.TabIndex = 81;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(1, 44);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(111, 16);
        this.label5.TabIndex = 80;
        this.label5.Text = "Confirmed";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelStatus
        //
        this.labelStatus.Location = new System.Drawing.Point(1, 3);
        this.labelStatus.Name = "labelStatus";
        this.labelStatus.Size = new System.Drawing.Size(111, 15);
        this.labelStatus.TabIndex = 79;
        this.labelStatus.Text = "Status";
        this.labelStatus.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label24
        //
        this.label24.Location = new System.Drawing.Point(128, 148);
        this.label24.Name = "label24";
        this.label24.Size = new System.Drawing.Size(113, 16);
        this.label24.TabIndex = 138;
        this.label24.Text = "(use hyg color)";
        //
        // checkIsHygiene
        //
        this.checkIsHygiene.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsHygiene.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsHygiene.Location = new System.Drawing.Point(23, 148);
        this.checkIsHygiene.Name = "checkIsHygiene";
        this.checkIsHygiene.Size = new System.Drawing.Size(104, 16);
        this.checkIsHygiene.TabIndex = 137;
        this.checkIsHygiene.Text = "Is Hygiene";
        this.checkIsHygiene.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(114, 83);
        this.comboClinic.MaxDropDownItems = 100;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(126, 21);
        this.comboClinic.TabIndex = 136;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(13, 86);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(98, 16);
        this.labelClinic.TabIndex = 135;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboAssistant
        //
        this.comboAssistant.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAssistant.Location = new System.Drawing.Point(114, 164);
        this.comboAssistant.MaxDropDownItems = 30;
        this.comboAssistant.Name = "comboAssistant";
        this.comboAssistant.Size = new System.Drawing.Size(126, 21);
        this.comboAssistant.TabIndex = 133;
        //
        // comboProvHyg
        //
        this.comboProvHyg.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvHyg.Location = new System.Drawing.Point(114, 126);
        this.comboProvHyg.MaxDropDownItems = 30;
        this.comboProvHyg.Name = "comboProvHyg";
        this.comboProvHyg.Size = new System.Drawing.Size(107, 21);
        this.comboProvHyg.TabIndex = 132;
        //
        // comboProvNum
        //
        this.comboProvNum.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvNum.Location = new System.Drawing.Point(114, 104);
        this.comboProvNum.MaxDropDownItems = 100;
        this.comboProvNum.Name = "comboProvNum";
        this.comboProvNum.Size = new System.Drawing.Size(107, 21);
        this.comboProvNum.TabIndex = 131;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(13, 167);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(98, 16);
        this.label12.TabIndex = 129;
        this.label12.Text = "Assistant";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsNewPatient
        //
        this.checkIsNewPatient.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsNewPatient.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsNewPatient.Location = new System.Drawing.Point(17, 64);
        this.checkIsNewPatient.Name = "checkIsNewPatient";
        this.checkIsNewPatient.Size = new System.Drawing.Size(110, 17);
        this.checkIsNewPatient.TabIndex = 128;
        this.checkIsNewPatient.Text = "New Patient";
        this.checkIsNewPatient.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(15, 128);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(98, 16);
        this.label3.TabIndex = 127;
        this.label3.Text = "Hygienist";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(14, 107);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(98, 16);
        this.label2.TabIndex = 126;
        this.label2.Text = "Dentist";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelApptNote
        //
        this.labelApptNote.Location = new System.Drawing.Point(20, 451);
        this.labelApptNote.Name = "labelApptNote";
        this.labelApptNote.Size = new System.Drawing.Size(197, 16);
        this.labelApptNote.TabIndex = 141;
        this.labelApptNote.Text = "Appointment Note";
        this.labelApptNote.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(-1, 190);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(113, 14);
        this.label6.TabIndex = 65;
        this.label6.Text = "Time Length";
        this.label6.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // textTime
        //
        this.textTime.Location = new System.Drawing.Point(114, 187);
        this.textTime.Name = "textTime";
        this.textTime.ReadOnly = true;
        this.textTime.Size = new System.Drawing.Size(66, 20);
        this.textTime.TabIndex = 62;
        //
        // butSlider
        //
        this.butSlider.BackColor = System.Drawing.SystemColors.ControlDark;
        this.butSlider.Location = new System.Drawing.Point(6, 90);
        this.butSlider.Name = "butSlider";
        this.butSlider.Size = new System.Drawing.Size(12, 15);
        this.butSlider.TabIndex = 60;
        this.butSlider.UseVisualStyleBackColor = false;
        this.butSlider.MouseDown += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseDown);
        this.butSlider.MouseMove += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseMove);
        this.butSlider.MouseUp += new System.Windows.Forms.MouseEventHandler(this.butSlider_MouseUp);
        //
        // checkTimeLocked
        //
        this.checkTimeLocked.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkTimeLocked.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkTimeLocked.Location = new System.Drawing.Point(-1, 210);
        this.checkTimeLocked.Name = "checkTimeLocked";
        this.checkTimeLocked.Size = new System.Drawing.Size(128, 16);
        this.checkTimeLocked.TabIndex = 148;
        this.checkTimeLocked.Text = "Time Locked";
        this.checkTimeLocked.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkTimeLocked.Click += new System.EventHandler(this.checkTimeLocked_Click);
        //
        // contextMenuTimeArrived
        //
        this.contextMenuTimeArrived.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemArrivedNow });
        //
        // menuItemArrivedNow
        //
        this.menuItemArrivedNow.Index = 0;
        this.menuItemArrivedNow.Text = "Now";
        this.menuItemArrivedNow.Click += new System.EventHandler(this.menuItemArrivedNow_Click);
        //
        // contextMenuTimeSeated
        //
        this.contextMenuTimeSeated.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSeatedNow });
        //
        // menuItemSeatedNow
        //
        this.menuItemSeatedNow.Index = 0;
        this.menuItemSeatedNow.Text = "Now";
        this.menuItemSeatedNow.Click += new System.EventHandler(this.menuItemSeatedNow_Click);
        //
        // contextMenuTimeDismissed
        //
        this.contextMenuTimeDismissed.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemDismissedNow });
        //
        // menuItemDismissedNow
        //
        this.menuItemDismissedNow.Index = 0;
        this.menuItemDismissedNow.Text = "Now";
        this.menuItemDismissedNow.Click += new System.EventHandler(this.menuItemDismissedNow_Click);
        //
        // textTimeAskedToArrive
        //
        this.textTimeAskedToArrive.Location = new System.Drawing.Point(114, 245);
        this.textTimeAskedToArrive.Name = "textTimeAskedToArrive";
        this.textTimeAskedToArrive.Size = new System.Drawing.Size(126, 20);
        this.textTimeAskedToArrive.TabIndex = 146;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(-1, 247);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(113, 18);
        this.label8.TabIndex = 160;
        this.label8.Text = "Time Ask To Arrive";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listQuickAdd
        //
        this.listQuickAdd.IntegralHeight = false;
        this.listQuickAdd.Location = new System.Drawing.Point(282, 48);
        this.listQuickAdd.Name = "listQuickAdd";
        this.listQuickAdd.Size = new System.Drawing.Size(150, 355);
        this.listQuickAdd.TabIndex = 163;
        this.listQuickAdd.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listQuickAdd_MouseDown);
        //
        // labelQuickAdd
        //
        this.labelQuickAdd.Location = new System.Drawing.Point(282, 7);
        this.labelQuickAdd.Name = "labelQuickAdd";
        this.labelQuickAdd.Size = new System.Drawing.Size(143, 39);
        this.labelQuickAdd.TabIndex = 162;
        this.labelQuickAdd.Text = "Single click on items in the list below to add them to this appointment.";
        //
        // panel1
        //
        this.panel1.AutoScroll = true;
        this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panel1.Controls.Add(this.labelSyndromicObservations);
        this.panel1.Controls.Add(this.butSyndromicObservations);
        this.panel1.Controls.Add(this.label9);
        this.panel1.Controls.Add(this.butColorClear);
        this.panel1.Controls.Add(this.butColor);
        this.panel1.Controls.Add(this.textRequirement);
        this.panel1.Controls.Add(this.butRequirement);
        this.panel1.Controls.Add(this.butInsPlan2);
        this.panel1.Controls.Add(this.butInsPlan1);
        this.panel1.Controls.Add(this.textInsPlan2);
        this.panel1.Controls.Add(this.labelInsPlan2);
        this.panel1.Controls.Add(this.textInsPlan1);
        this.panel1.Controls.Add(this.labelInsPlan1);
        this.panel1.Controls.Add(this.textTimeDismissed);
        this.panel1.Controls.Add(this.label7);
        this.panel1.Controls.Add(this.textTimeSeated);
        this.panel1.Controls.Add(this.label1);
        this.panel1.Controls.Add(this.textTimeArrived);
        this.panel1.Controls.Add(this.labelTimeArrived);
        this.panel1.Controls.Add(this.textLabCase);
        this.panel1.Controls.Add(this.butLab);
        this.panel1.Controls.Add(this.comboStatus);
        this.panel1.Controls.Add(this.checkIsNewPatient);
        this.panel1.Controls.Add(this.comboConfirmed);
        this.panel1.Controls.Add(this.label24);
        this.panel1.Controls.Add(this.textTimeAskedToArrive);
        this.panel1.Controls.Add(this.comboUnschedStatus);
        this.panel1.Controls.Add(this.label8);
        this.panel1.Controls.Add(this.checkIsHygiene);
        this.panel1.Controls.Add(this.comboClinic);
        this.panel1.Controls.Add(this.labelClinic);
        this.panel1.Controls.Add(this.label4);
        this.panel1.Controls.Add(this.comboAssistant);
        this.panel1.Controls.Add(this.butPickHyg);
        this.panel1.Controls.Add(this.comboProvHyg);
        this.panel1.Controls.Add(this.comboProvNum);
        this.panel1.Controls.Add(this.butPickDentist);
        this.panel1.Controls.Add(this.label5);
        this.panel1.Controls.Add(this.label12);
        this.panel1.Controls.Add(this.label3);
        this.panel1.Controls.Add(this.labelStatus);
        this.panel1.Controls.Add(this.label2);
        this.panel1.Controls.Add(this.textTime);
        this.panel1.Controls.Add(this.label6);
        this.panel1.Controls.Add(this.checkTimeLocked);
        this.panel1.Location = new System.Drawing.Point(21, 2);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(260, 447);
        this.panel1.TabIndex = 164;
        //
        // labelSyndromicObservations
        //
        this.labelSyndromicObservations.Location = new System.Drawing.Point(63, 462);
        this.labelSyndromicObservations.Name = "labelSyndromicObservations";
        this.labelSyndromicObservations.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        this.labelSyndromicObservations.Size = new System.Drawing.Size(174, 16);
        this.labelSyndromicObservations.TabIndex = 181;
        this.labelSyndromicObservations.Text = "(Syndromic Observations)";
        this.labelSyndromicObservations.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelSyndromicObservations.Visible = false;
        //
        // butSyndromicObservations
        //
        this.butSyndromicObservations.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSyndromicObservations.setAutosize(true);
        this.butSyndromicObservations.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSyndromicObservations.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSyndromicObservations.setCornerRadius(4F);
        this.butSyndromicObservations.Location = new System.Drawing.Point(15, 460);
        this.butSyndromicObservations.Name = "butSyndromicObservations";
        this.butSyndromicObservations.Size = new System.Drawing.Size(46, 20);
        this.butSyndromicObservations.TabIndex = 180;
        this.butSyndromicObservations.Text = "Obs";
        this.butSyndromicObservations.Visible = false;
        this.butSyndromicObservations.Click += new System.EventHandler(this.butSyndromicObservations_Click);
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(-1, 228);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(113, 14);
        this.label9.TabIndex = 179;
        this.label9.Text = "Color";
        this.label9.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        //
        // butColorClear
        //
        this.butColorClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColorClear.setAutosize(true);
        this.butColorClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColorClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColorClear.setCornerRadius(4F);
        this.butColorClear.Location = new System.Drawing.Point(137, 224);
        this.butColorClear.Name = "butColorClear";
        this.butColorClear.Size = new System.Drawing.Size(39, 20);
        this.butColorClear.TabIndex = 178;
        this.butColorClear.Text = "none";
        this.butColorClear.Click += new System.EventHandler(this.butColorClear_Click);
        //
        // butColor
        //
        this.butColor.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
        this.butColor.Location = new System.Drawing.Point(114, 225);
        this.butColor.Name = "butColor";
        this.butColor.Size = new System.Drawing.Size(21, 19);
        this.butColor.TabIndex = 177;
        this.butColor.Click += new System.EventHandler(this.butColor_Click);
        //
        // textRequirement
        //
        this.textRequirement.Location = new System.Drawing.Point(63, 408);
        this.textRequirement.Multiline = true;
        this.textRequirement.Name = "textRequirement";
        this.textRequirement.ReadOnly = true;
        this.textRequirement.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textRequirement.Size = new System.Drawing.Size(177, 53);
        this.textRequirement.TabIndex = 164;
        //
        // butRequirement
        //
        this.butRequirement.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRequirement.setAutosize(true);
        this.butRequirement.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRequirement.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRequirement.setCornerRadius(4F);
        this.butRequirement.Location = new System.Drawing.Point(15, 408);
        this.butRequirement.Name = "butRequirement";
        this.butRequirement.Size = new System.Drawing.Size(46, 20);
        this.butRequirement.TabIndex = 163;
        this.butRequirement.Text = "Req";
        this.butRequirement.Click += new System.EventHandler(this.butRequirement_Click);
        //
        // butInsPlan2
        //
        this.butInsPlan2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInsPlan2.setAutosize(false);
        this.butInsPlan2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInsPlan2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInsPlan2.setCornerRadius(2F);
        this.butInsPlan2.Location = new System.Drawing.Point(222, 387);
        this.butInsPlan2.Name = "butInsPlan2";
        this.butInsPlan2.Size = new System.Drawing.Size(18, 20);
        this.butInsPlan2.TabIndex = 176;
        this.butInsPlan2.Text = "...";
        this.butInsPlan2.Click += new System.EventHandler(this.butInsPlan2_Click);
        //
        // butInsPlan1
        //
        this.butInsPlan1.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInsPlan1.setAutosize(false);
        this.butInsPlan1.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInsPlan1.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInsPlan1.setCornerRadius(2F);
        this.butInsPlan1.Location = new System.Drawing.Point(222, 366);
        this.butInsPlan1.Name = "butInsPlan1";
        this.butInsPlan1.Size = new System.Drawing.Size(18, 20);
        this.butInsPlan1.TabIndex = 175;
        this.butInsPlan1.Text = "...";
        this.butInsPlan1.Click += new System.EventHandler(this.butInsPlan1_Click);
        //
        // textInsPlan2
        //
        this.textInsPlan2.Location = new System.Drawing.Point(63, 387);
        this.textInsPlan2.Name = "textInsPlan2";
        this.textInsPlan2.ReadOnly = true;
        this.textInsPlan2.Size = new System.Drawing.Size(158, 20);
        this.textInsPlan2.TabIndex = 174;
        //
        // labelInsPlan2
        //
        this.labelInsPlan2.Location = new System.Drawing.Point(2, 389);
        this.labelInsPlan2.Name = "labelInsPlan2";
        this.labelInsPlan2.Size = new System.Drawing.Size(59, 16);
        this.labelInsPlan2.TabIndex = 173;
        this.labelInsPlan2.Text = "InsPlan 2";
        this.labelInsPlan2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textInsPlan1
        //
        this.textInsPlan1.Location = new System.Drawing.Point(63, 366);
        this.textInsPlan1.Name = "textInsPlan1";
        this.textInsPlan1.ReadOnly = true;
        this.textInsPlan1.Size = new System.Drawing.Size(158, 20);
        this.textInsPlan1.TabIndex = 172;
        //
        // labelInsPlan1
        //
        this.labelInsPlan1.Location = new System.Drawing.Point(2, 368);
        this.labelInsPlan1.Name = "labelInsPlan1";
        this.labelInsPlan1.Size = new System.Drawing.Size(59, 16);
        this.labelInsPlan1.TabIndex = 171;
        this.labelInsPlan1.Text = "InsPlan 1";
        this.labelInsPlan1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTimeDismissed
        //
        this.textTimeDismissed.Location = new System.Drawing.Point(114, 305);
        this.textTimeDismissed.Name = "textTimeDismissed";
        this.textTimeDismissed.Size = new System.Drawing.Size(126, 20);
        this.textTimeDismissed.TabIndex = 170;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(-1, 307);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(113, 16);
        this.label7.TabIndex = 169;
        this.label7.Text = "Time Dismissed";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTimeSeated
        //
        this.textTimeSeated.Location = new System.Drawing.Point(114, 285);
        this.textTimeSeated.Name = "textTimeSeated";
        this.textTimeSeated.Size = new System.Drawing.Size(126, 20);
        this.textTimeSeated.TabIndex = 168;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(-1, 287);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(113, 16);
        this.label1.TabIndex = 166;
        this.label1.Text = "Time Seated";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTimeArrived
        //
        this.textTimeArrived.Location = new System.Drawing.Point(114, 265);
        this.textTimeArrived.Name = "textTimeArrived";
        this.textTimeArrived.Size = new System.Drawing.Size(126, 20);
        this.textTimeArrived.TabIndex = 167;
        //
        // labelTimeArrived
        //
        this.labelTimeArrived.Location = new System.Drawing.Point(-1, 267);
        this.labelTimeArrived.Name = "labelTimeArrived";
        this.labelTimeArrived.Size = new System.Drawing.Size(113, 16);
        this.labelTimeArrived.TabIndex = 165;
        this.labelTimeArrived.Text = "Time Arrived";
        this.labelTimeArrived.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textLabCase
        //
        this.textLabCase.AcceptsReturn = true;
        this.textLabCase.Location = new System.Drawing.Point(63, 330);
        this.textLabCase.Multiline = true;
        this.textLabCase.Name = "textLabCase";
        this.textLabCase.ReadOnly = true;
        this.textLabCase.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        this.textLabCase.Size = new System.Drawing.Size(177, 34);
        this.textLabCase.TabIndex = 162;
        //
        // butLab
        //
        this.butLab.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLab.setAutosize(true);
        this.butLab.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLab.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLab.setCornerRadius(4F);
        this.butLab.Location = new System.Drawing.Point(15, 330);
        this.butLab.Name = "butLab";
        this.butLab.Size = new System.Drawing.Size(46, 20);
        this.butLab.TabIndex = 161;
        this.butLab.Text = "Lab";
        this.butLab.Click += new System.EventHandler(this.butLab_Click);
        //
        // butPickHyg
        //
        this.butPickHyg.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickHyg.setAutosize(false);
        this.butPickHyg.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickHyg.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickHyg.setCornerRadius(2F);
        this.butPickHyg.Location = new System.Drawing.Point(222, 127);
        this.butPickHyg.Name = "butPickHyg";
        this.butPickHyg.Size = new System.Drawing.Size(18, 20);
        this.butPickHyg.TabIndex = 158;
        this.butPickHyg.Text = "...";
        this.butPickHyg.Click += new System.EventHandler(this.butPickHyg_Click);
        //
        // butPickDentist
        //
        this.butPickDentist.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickDentist.setAutosize(false);
        this.butPickDentist.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickDentist.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickDentist.setCornerRadius(2F);
        this.butPickDentist.Location = new System.Drawing.Point(222, 105);
        this.butPickDentist.Name = "butPickDentist";
        this.butPickDentist.Size = new System.Drawing.Size(18, 20);
        this.butPickDentist.TabIndex = 157;
        this.butPickDentist.Text = "...";
        this.butPickDentist.Click += new System.EventHandler(this.butPickDentist_Click);
        //
        // gridFields
        //
        this.gridFields.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridFields.setHScrollVisible(false);
        this.gridFields.Location = new System.Drawing.Point(21, 578);
        this.gridFields.Name = "gridFields";
        this.gridFields.setScrollValue(0);
        this.gridFields.Size = new System.Drawing.Size(259, 118);
        this.gridFields.TabIndex = 159;
        this.gridFields.setTitle("Appt Fields");
        this.gridFields.setTranslationName("FormApptEdit");
        this.gridFields.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridFields.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridFields_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridPatient
        //
        this.gridPatient.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridPatient.setHScrollVisible(false);
        this.gridPatient.Location = new System.Drawing.Point(282, 405);
        this.gridPatient.Name = "gridPatient";
        this.gridPatient.setScrollValue(0);
        this.gridPatient.Size = new System.Drawing.Size(258, 291);
        this.gridPatient.TabIndex = 0;
        this.gridPatient.setTitle("Patient Info");
        this.gridPatient.setTranslationName("TableApptPtInfo");
        this.gridPatient.MouseMove += new System.Windows.Forms.MouseEventHandler(this.gridPatient_MouseMove);
        //
        // gridComm
        //
        this.gridComm.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridComm.setHScrollVisible(false);
        this.gridComm.Location = new System.Drawing.Point(542, 405);
        this.gridComm.Name = "gridComm";
        this.gridComm.setScrollValue(0);
        this.gridComm.Size = new System.Drawing.Size(335, 291);
        this.gridComm.TabIndex = 1;
        this.gridComm.setTitle("Communications Log - Appointment Scheduling");
        this.gridComm.setTranslationName("TableCommLog");
        this.gridComm.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridComm.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridComm_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridComm.MouseMove += new System.Windows.Forms.MouseEventHandler(this.gridComm_MouseMove);
        //
        // gridProc
        //
        this.gridProc.setAllowSelection(false);
        this.gridProc.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProc.setHScrollVisible(false);
        this.gridProc.Location = new System.Drawing.Point(434, 28);
        this.gridProc.Name = "gridProc";
        this.gridProc.setScrollValue(0);
        this.gridProc.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProc.Size = new System.Drawing.Size(538, 375);
        this.gridProc.TabIndex = 139;
        this.gridProc.setTitle("Procedures on this Appointment");
        this.gridProc.setTranslationName("TableApptProcs");
        this.gridProc.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridProc.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridProc_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridProc.CellClick = __MultiODGridClickEventHandler.combine(this.gridProc.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
                this.gridProc_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butPDF
        //
        this.butPDF.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPDF.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPDF.setAutosize(true);
        this.butPDF.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPDF.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPDF.setCornerRadius(4F);
        this.butPDF.Location = new System.Drawing.Point(880, 484);
        this.butPDF.Name = "butPDF";
        this.butPDF.Size = new System.Drawing.Size(92, 24);
        this.butPDF.TabIndex = 161;
        this.butPDF.Text = "Notes PDF";
        this.butPDF.Visible = false;
        this.butPDF.Click += new System.EventHandler(this.butPDF_Click);
        //
        // butComplete
        //
        this.butComplete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butComplete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butComplete.setAutosize(true);
        this.butComplete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butComplete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butComplete.setCornerRadius(4F);
        this.butComplete.Location = new System.Drawing.Point(880, 510);
        this.butComplete.Name = "butComplete";
        this.butComplete.Size = new System.Drawing.Size(92, 24);
        this.butComplete.TabIndex = 155;
        this.butComplete.Text = "Finish && Send";
        this.butComplete.Visible = false;
        this.butComplete.Click += new System.EventHandler(this.butComplete_Click);
        //
        // butDeleteProc
        //
        this.butDeleteProc.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteProc.setAutosize(true);
        this.butDeleteProc.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteProc.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteProc.setCornerRadius(4F);
        this.butDeleteProc.Image = Resources.getdeleteX();
        this.butDeleteProc.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteProc.Location = new System.Drawing.Point(434, 2);
        this.butDeleteProc.Name = "butDeleteProc";
        this.butDeleteProc.Size = new System.Drawing.Size(75, 24);
        this.butDeleteProc.TabIndex = 154;
        this.butDeleteProc.Text = "Delete";
        this.butDeleteProc.Click += new System.EventHandler(this.butDeleteProc_Click);
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
        this.butAdd.Location = new System.Drawing.Point(510, 2);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 152;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // textNote
        //
        this.textNote.AcceptsTab = true;
        this.textNote.DetectUrls = false;
        this.textNote.Location = new System.Drawing.Point(21, 469);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Appointment);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(260, 106);
        this.textNote.TabIndex = 142;
        this.textNote.Text = "";
        //
        // butAddComm
        //
        this.butAddComm.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddComm.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddComm.setAutosize(true);
        this.butAddComm.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddComm.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddComm.setCornerRadius(4F);
        this.butAddComm.Image = Resources.getcommlog();
        this.butAddComm.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddComm.Location = new System.Drawing.Point(880, 405);
        this.butAddComm.Name = "butAddComm";
        this.butAddComm.Size = new System.Drawing.Size(92, 24);
        this.butAddComm.TabIndex = 143;
        this.butAddComm.Text = "Co&mm";
        this.butAddComm.Click += new System.EventHandler(this.butAddComm_Click);
        //
        // tbTime
        //
        this.tbTime.BackColor = System.Drawing.SystemColors.Window;
        this.tbTime.Location = new System.Drawing.Point(4, 6);
        this.tbTime.Name = "tbTime";
        this.tbTime.setScrollValue(150);
        this.tbTime.setSelectedIndices(new int[0]);
        this.tbTime.setSelectionMode(System.Windows.Forms.SelectionMode.None);
        this.tbTime.Size = new System.Drawing.Size(15, 561);
        this.tbTime.TabIndex = 59;
        //
        // butAudit
        //
        this.butAudit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAudit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAudit.setAutosize(true);
        this.butAudit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAudit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAudit.setCornerRadius(4F);
        this.butAudit.Location = new System.Drawing.Point(880, 536);
        this.butAudit.Name = "butAudit";
        this.butAudit.Size = new System.Drawing.Size(92, 24);
        this.butAudit.TabIndex = 125;
        this.butAudit.Text = "Audit Trail";
        this.butAudit.Click += new System.EventHandler(this.butAudit_Click);
        //
        // butTask
        //
        this.butTask.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTask.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butTask.setAutosize(true);
        this.butTask.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTask.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTask.setCornerRadius(4F);
        this.butTask.Location = new System.Drawing.Point(880, 562);
        this.butTask.Name = "butTask";
        this.butTask.Size = new System.Drawing.Size(92, 24);
        this.butTask.TabIndex = 124;
        this.butTask.Text = "To Task List";
        this.butTask.Click += new System.EventHandler(this.butTask_Click);
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
        this.butDelete.Location = new System.Drawing.Point(880, 614);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(92, 24);
        this.butDelete.TabIndex = 123;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butPin
        //
        this.butPin.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPin.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPin.setAutosize(true);
        this.butPin.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPin.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPin.setCornerRadius(4F);
        this.butPin.Image = ((System.Drawing.Image)(resources.GetObject("butPin.Image")));
        this.butPin.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPin.Location = new System.Drawing.Point(880, 588);
        this.butPin.Name = "butPin";
        this.butPin.Size = new System.Drawing.Size(92, 24);
        this.butPin.TabIndex = 122;
        this.butPin.Text = "&Pinboard";
        this.butPin.Click += new System.EventHandler(this.butPin_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(880, 640);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(92, 24);
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
        this.butCancel.Location = new System.Drawing.Point(880, 666);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(92, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butText
        //
        this.butText.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butText.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butText.setAutosize(true);
        this.butText.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butText.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butText.setCornerRadius(4F);
        this.butText.Image = Resources.getText();
        this.butText.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butText.Location = new System.Drawing.Point(880, 431);
        this.butText.Name = "butText";
        this.butText.Size = new System.Drawing.Size(92, 24);
        this.butText.TabIndex = 143;
        this.butText.Text = "Text";
        this.butText.Click += new System.EventHandler(this.butText_Click);
        //
        // FormApptEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(974, 698);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.listQuickAdd);
        this.Controls.Add(this.labelQuickAdd);
        this.Controls.Add(this.butPDF);
        this.Controls.Add(this.gridFields);
        this.Controls.Add(this.butComplete);
        this.Controls.Add(this.butDeleteProc);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.labelApptNote);
        this.Controls.Add(this.butText);
        this.Controls.Add(this.butAddComm);
        this.Controls.Add(this.butSlider);
        this.Controls.Add(this.tbTime);
        this.Controls.Add(this.gridPatient);
        this.Controls.Add(this.gridComm);
        this.Controls.Add(this.gridProc);
        this.Controls.Add(this.butAudit);
        this.Controls.Add(this.butTask);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butPin);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Appointment";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormApptEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormApptEdit_Load);
        this.panel1.ResumeLayout(false);
        this.panel1.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formApptEdit_Load(Object sender, System.EventArgs e) throws Exception {
        tbTime.CellClicked = __MultiCellEventHandler.combine(tbTime.CellClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, CellEventArgs e) throws Exception {
                tbTime_CellClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        if (IsNew)
        {
            if (!Security.isAuthorized(Permissions.AppointmentCreate))
            {
                DialogResult = DialogResult.Cancel;
                return ;
            }
             
        }
        else
        {
            if (!Security.isAuthorized(Permissions.AppointmentEdit))
            {
                butOK.Enabled = false;
                butDelete.Enabled = false;
                butPin.Enabled = false;
                butTask.Enabled = false;
                gridProc.Enabled = false;
                listQuickAdd.Enabled = false;
                butAdd.Enabled = false;
                butDeleteProc.Enabled = false;
            }
             
        } 
        //The four objects below are needed when adding procs to this appt.
        fam = Patients.getFamily(AptCur.PatNum);
        pat = fam.getPatient(AptCur.PatNum);
        SubList = InsSubs.refreshForFam(fam);
        PlanList = InsPlans.refreshForSubList(SubList);
        if (PrefC.getBool(PrefName.EasyHideDentalSchools))
        {
            butRequirement.Visible = false;
            textRequirement.Visible = false;
        }
         
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            butSyndromicObservations.Visible = true;
            labelSyndromicObservations.Visible = true;
        }
         
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            labelClinic.Visible = false;
            comboClinic.Visible = false;
        }
         
        if (PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            labelInsPlan1.Visible = false;
            textInsPlan1.Visible = false;
            butInsPlan1.Visible = false;
            labelInsPlan2.Visible = false;
            textInsPlan2.Visible = false;
            butInsPlan2.Visible = false;
        }
         
        if (!PinIsVisible)
        {
            butPin.Visible = false;
        }
         
        if (AptCur.AptStatus == ApptStatus.Planned)
        {
            Text = Lan.g(this,"Edit Planned Appointment") + " - " + DS.Tables["Patient"].Rows[0]["value"].ToString();
            labelStatus.Visible = false;
            comboStatus.Visible = false;
            butDelete.Visible = false;
        }
        else if (AptCur.AptStatus == ApptStatus.PtNote)
        {
            labelApptNote.Text = "Patient NOTE:";
            Text = Lan.g(this,"Edit Patient Note") + " - " + DS.Tables["Patient"].Rows[0]["value"].ToString() + " on " + AptCur.AptDateTime.DayOfWeek + ", " + AptCur.AptDateTime;
            comboStatus.Items.Add(Lan.g("enumApptStatus","Patient Note"));
            comboStatus.Items.Add(Lan.g("enumApptStatus","Completed Pt. Note"));
            comboStatus.SelectedIndex = ((Enum)AptCur.AptStatus).ordinal() - 7;
            labelQuickAdd.Visible = false;
            labelStatus.Visible = false;
            gridProc.Visible = false;
            listQuickAdd.Visible = false;
            butAdd.Visible = false;
            butDeleteProc.Visible = false;
        }
        else //textNote.Width = 400;
        if (AptCur.AptStatus == ApptStatus.PtNoteCompleted)
        {
            labelApptNote.Text = "Completed Patient NOTE:";
            Text = Lan.g(this,"Edit Completed Patient Note") + " - " + DS.Tables["Patient"].Rows[0]["value"].ToString() + " on " + AptCur.AptDateTime.DayOfWeek + ", " + AptCur.AptDateTime;
            comboStatus.Items.Add(Lan.g("enumApptStatus","Patient Note"));
            comboStatus.Items.Add(Lan.g("enumApptStatus","Completed Pt. Note"));
            comboStatus.SelectedIndex = ((Enum)AptCur.AptStatus).ordinal() - 7;
            labelQuickAdd.Visible = false;
            labelStatus.Visible = false;
            gridProc.Visible = false;
            listQuickAdd.Visible = false;
            butAdd.Visible = false;
            butDeleteProc.Visible = false;
        }
        else
        {
            //textNote.Width = 400;
            Text = Lan.g(this,"Edit Appointment") + " - " + DS.Tables["Patient"].Rows[0]["value"].ToString() + " on " + AptCur.AptDateTime.DayOfWeek + ", " + AptCur.AptDateTime;
            comboStatus.Items.Add(Lan.g("enumApptStatus","Scheduled"));
            comboStatus.Items.Add(Lan.g("enumApptStatus","Complete"));
            comboStatus.Items.Add(Lan.g("enumApptStatus","UnschedList"));
            comboStatus.Items.Add(Lan.g("enumApptStatus","ASAP"));
            comboStatus.Items.Add(Lan.g("enumApptStatus","Broken"));
            comboStatus.SelectedIndex = ((Enum)AptCur.AptStatus).ordinal() - 1;
        }   
        if (AptCur.AptStatus == ApptStatus.UnschedList)
        {
            if (Programs.usingEcwTightOrFullMode())
            {
                comboStatus.Enabled = true;
            }
            else if (HL7Defs.getOneDeepEnabled() != null && !HL7Defs.getOneDeepEnabled().ShowAppts)
            {
                comboStatus.Enabled = true;
            }
            else
            {
                comboStatus.Enabled = false;
            }  
        }
         
        //convert time pattern from 5 to current increment.
        strBTime = new StringBuilder();
        for (int i = 0;i < AptCur.Pattern.Length;i++)
        {
            strBTime.Append(AptCur.Pattern.Substring(i, 1));
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 10)
            {
                i++;
            }
             
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 15)
            {
                i++;
                i++;
            }
             
        }
        comboUnschedStatus.Items.Add(Lan.g(this,"none"));
        comboUnschedStatus.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()].Length;i++)
        {
            comboUnschedStatus.Items.Add(DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].DefNum == AptCur.UnschedStatus)
                comboUnschedStatus.SelectedIndex = i + 1;
             
        }
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()].Length;i++)
        {
            comboConfirmed.Items.Add(DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][i].DefNum == AptCur.Confirmed)
            {
                comboConfirmed.SelectedIndex = i;
            }
             
        }
        checkTimeLocked.Checked = AptCur.TimeLocked;
        textNote.Text = AptCur.Note;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()].Length;i++)
        {
            listQuickAdd.Items.Add(DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()][i].ItemName);
        }
        comboClinic.Items.Add(Lan.g(this,"none"));
        comboClinic.SelectedIndex = 0;
        for (int i = 0;i < Clinics.getList().Length;i++)
        {
            comboClinic.Items.Add(Clinics.getList()[i].Description);
            if (Clinics.getList()[i].ClinicNum == AptCur.ClinicNum)
                comboClinic.SelectedIndex = i + 1;
             
        }
        if (IsNew)
        {
            //Try to auto-select a provider when in Orion mode. Only for new appointments so we don't change historical data.
            AptCur.ProvNum = Providers.getOrionProvNum(AptCur.ProvNum);
        }
         
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNum.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == AptCur.ProvNum)
                comboProvNum.SelectedIndex = i;
             
        }
        comboProvHyg.Items.Add(Lan.g(this,"none"));
        comboProvHyg.SelectedIndex = 0;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvHyg.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == AptCur.ProvHyg)
                comboProvHyg.SelectedIndex = i + 1;
             
        }
        checkIsHygiene.Checked = AptCur.IsHygiene;
        comboAssistant.Items.Add(Lan.g(this,"none"));
        comboAssistant.SelectedIndex = 0;
        for (int i = 0;i < Employees.getListShort().Length;i++)
        {
            comboAssistant.Items.Add(Employees.getListShort()[i].FName);
            if (Employees.getListShort()[i].EmployeeNum == AptCur.Assistant)
                comboAssistant.SelectedIndex = i + 1;
             
        }
        textLabCase.Text = DS.Tables["Misc"].Rows[0]["labDescript"].ToString();
        textTimeArrived.ContextMenu = contextMenuTimeArrived;
        textTimeSeated.ContextMenu = contextMenuTimeSeated;
        textTimeDismissed.ContextMenu = contextMenuTimeDismissed;
        if (AptCur.DateTimeAskedToArrive.TimeOfDay > TimeSpan.FromHours(0))
        {
            textTimeAskedToArrive.Text = AptCur.DateTimeAskedToArrive.ToShortTimeString();
        }
         
        if (AptCur.DateTimeArrived.TimeOfDay > TimeSpan.FromHours(0))
        {
            textTimeArrived.Text = AptCur.DateTimeArrived.ToShortTimeString();
        }
         
        if (AptCur.DateTimeSeated.TimeOfDay > TimeSpan.FromHours(0))
        {
            textTimeSeated.Text = AptCur.DateTimeSeated.ToShortTimeString();
        }
         
        if (AptCur.DateTimeDismissed.TimeOfDay > TimeSpan.FromHours(0))
        {
            textTimeDismissed.Text = AptCur.DateTimeDismissed.ToShortTimeString();
        }
         
        textInsPlan1.Text = InsPlans.getCarrierName(AptCur.InsPlan1,PlanList);
        textInsPlan2.Text = InsPlans.getCarrierName(AptCur.InsPlan2,PlanList);
        textRequirement.Text = DS.Tables["Misc"].Rows[0]["requirements"].ToString();
        //IsNewPatient is set well before opening this form.
        checkIsNewPatient.Checked = AptCur.IsNewPatient;
        butColor.BackColor = AptCur.ColorOverride;
        if (ApptDrawing.MinPerIncr == 5)
        {
            tbTime.TopBorder[0, 12] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 24] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 36] = System.Drawing.Color.Black;
        }
        else if (ApptDrawing.MinPerIncr == 10)
        {
            tbTime.TopBorder[0, 6] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 12] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 18] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 24] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 30] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 36] = System.Drawing.Color.Black;
        }
        else if (ApptDrawing.MinPerIncr == 15)
        {
            tbTime.TopBorder[0, 4] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 8] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 12] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 16] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 20] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 24] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 28] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 32] = System.Drawing.Color.Black;
            tbTime.TopBorder[0, 36] = System.Drawing.Color.Black;
        }
           
        if (Programs.usingEcwTightOrFullMode())
        {
            //These buttons are ONLY for eCW, not any other HL7 interface.
            butComplete.Visible = true;
            butPDF.Visible = true;
            //for eCW, we need to hide some things--------------------
            if (ECW.AptNum == AptCur.AptNum)
            {
                butDelete.Visible = false;
            }
             
            butPin.Visible = false;
            butTask.Visible = false;
            butAddComm.Visible = false;
            if (HL7Msgs.messageWasSent(AptCur.AptNum))
            {
                butComplete.Text = "Revise";
                //if(!Security.IsAuthorized(Permissions.Setup,true)) {
                //	butComplete.Enabled=false;
                //	butPDF.Enabled=false;
                //}
                butOK.Enabled = false;
                gridProc.Enabled = false;
                listQuickAdd.Enabled = false;
                butAdd.Enabled = false;
                butDeleteProc.Enabled = false;
            }
            else
            {
                //hl7 was not sent for this appt
                butComplete.Text = "Finish && Send";
                if (ECW.AptNum != AptCur.AptNum)
                {
                    butComplete.Enabled = false;
                }
                 
                butPDF.Enabled = false;
            } 
        }
        else
        {
            butComplete.Visible = false;
            butPDF.Visible = false;
        } 
        //Hide text message button sometimes
        if (StringSupport.equals(pat.WirelessPhone, "") || !Programs.isEnabled(ProgramName.CallFire))
        {
            butText.Enabled = false;
        }
        else
        {
            //Pat has a wireless phone number and CallFire is enabled
            if (pat.TxtMsgOk == YN.Unknown)
            {
                butText.Enabled = !PrefC.getBool(PrefName.TextMsgOkStatusTreatAsNo);
            }
            else
            {
                //Not enabled since TxtMsgOk is ?? and "Treat ?? As No" is true.
                butText.Enabled = (pat.TxtMsgOk == YN.Yes);
            } 
        } 
        fillProcedures();
        setProceduresForECW();
        fillPatient();
        //Must be after FillProcedures(), so that the initial amount for the appointment can be calculated.
        fillTime();
        fillComm();
        fillFields();
        textNote.Focus();
        textNote.SelectionStart = 0;
        Plugins.hookAddCode(this,"FormApptEdit.Load_End",pat,butText);
    }

    /**
    * If an eCW program link is turned on, then this attaches completed procs with the same date as the appt.
    */
    private void setProceduresForECW() throws Exception {
        if (!Programs.usingEcwTightOrFullMode())
        {
            return ;
        }
         
        List<long> procNums = new List<long>();
        for (int i = 0;i < DS.Tables["Procedure"].Rows.Count;i++)
        {
            //this is a method that attaches very specific kinds of procedures to appt
            //loop through procs
            if (DS.Tables["Procedure"].Rows[i]["ProcStatus"].ToString() != (((Enum)ProcStat.C).ordinal()).ToString())
            {
                continue;
            }
             
            //must be complete proc
            if (PIn.DateT(DS.Tables["Procedure"].Rows[i]["ProcDate"].ToString()).Date != AptCur.AptDateTime.Date)
            {
                continue;
            }
             
            //must have same date as appt
            gridProc.setSelected(i,true);
            //harmless if already selected.
            procNums.Add(PIn.Long(DS.Tables["Procedure"].Rows[i]["ProcNum"].ToString()));
        }
        //Now attach the procedures to the appt in the database.
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        Procedures.AttachToApt(procNums, AptCur.AptNum, isPlanned);
    }

    private void butPickDentist_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formp = new FormProviderPick();
        if (comboProvNum.SelectedIndex > -1)
        {
            formp.SelectedProvNum = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
        }
         
        formp.ShowDialog();
        if (formp.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProvNum.SelectedIndex = Providers.getIndex(formp.SelectedProvNum);
    }

    private void butPickHyg_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formp = new FormProviderPick();
        if (comboProvHyg.SelectedIndex > 0)
        {
            formp.SelectedProvNum = ProviderC.getListShort()[comboProvHyg.SelectedIndex - 1].ProvNum;
        }
         
        formp.ShowDialog();
        if (formp.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProvHyg.SelectedIndex = Providers.getIndex(formp.SelectedProvNum) + 1;
    }

    private void butColor_Click(Object sender, EventArgs e) throws Exception {
        ColorDialog colorDialog1 = new ColorDialog();
        colorDialog1.Color = butColor.BackColor;
        colorDialog1.ShowDialog();
        butColor.BackColor = colorDialog1.Color;
    }

    private void butColorClear_Click(Object sender, EventArgs e) throws Exception {
        butColor.BackColor = System.Drawing.Color.FromArgb(0);
    }

    private void fillPatient() throws Exception {
        DataTable table = DS.Tables["Patient"];
        gridPatient.beginUpdate();
        gridPatient.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",120);
        //Add 2 blank columns
        gridPatient.getColumns().add(col);
        col = new ODGridColumn("",120);
        gridPatient.getColumns().add(col);
        gridPatient.getRows().Clear();
        ODGridRow row;
        for (int i = 1;i < table.Rows.Count;i++)
        {
            //starts with 1 to skip name
            row = new ODGridRow();
            row.getCells().Add(table.Rows[i]["field"].ToString());
            row.getCells().Add(table.Rows[i]["value"].ToString());
            gridPatient.getRows().add(row);
        }
        //Add a UI managed row to display the total fee for the selected procedures in this appointment.
        row = new ODGridRow();
        row.getCells().add(Lan.g(this,"Fee This Appt"));
        row.getCells().add("");
        //Calculated below
        gridPatient.getRows().add(row);
        calcPatientFeeThisAppt();
        gridPatient.endUpdate();
        gridPatient.scrollToEnd();
    }

    /**
    * Calculates the fee for this appointment using the highlighted procedures in the procedure list.
    */
    private void calcPatientFeeThisAppt() throws Exception {
        double feeThisAppt = 0;
        for (int i = 0;i < gridProc.getSelectedIndices().Length;i++)
        {
            feeThisAppt += PIn.Double(gridProc.getRows()[gridProc.getSelectedIndices()[i]].Cells[6].Text);
        }
        gridPatient.getRows()[gridPatient.getRows().Count - 1].Cells[1].Text = POut.Double(feeThisAppt);
        gridPatient.Invalidate();
    }

    private void fillFields() throws Exception {
        gridFields.beginUpdate();
        gridFields.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",100);
        gridFields.getColumns().add(col);
        col = new ODGridColumn("",100);
        gridFields.getColumns().add(col);
        gridFields.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < DS.Tables["ApptFields"].Rows.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(DS.Tables["ApptFields"].Rows[i]["FieldName"].ToString());
            row.getCells().Add(DS.Tables["ApptFields"].Rows[i]["FieldValue"].ToString());
            gridFields.getRows().add(row);
        }
        gridFields.endUpdate();
    }

    private void fillComm() throws Exception {
        gridComm.beginUpdate();
        gridComm.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableCommLog","DateTime"),80);
        gridComm.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableCommLog","Description"),80);
        gridComm.getColumns().add(col);
        gridComm.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < DS.Tables["Comm"].Rows.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(DS.Tables["Comm"].Rows[i]["commDateTime"].ToString());
            row.getCells().Add(DS.Tables["Comm"].Rows[i]["Note"].ToString());
            if (DS.Tables["Comm"].Rows[i]["CommType"].ToString() == Commlogs.getTypeAuto(CommItemTypeAuto.APPT).ToString())
            {
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][7].ItemColor);
            }
             
            gridComm.getRows().add(row);
        }
        gridComm.endUpdate();
        gridComm.scrollToEnd();
    }

    private void gridComm_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        Commlog item = Commlogs.GetOne(PIn.Long(DS.Tables["Comm"].Rows[e.getRow()]["CommlogNum"].ToString()));
        FormCommItem FormCI = new FormCommItem(item);
        FormCI.ShowDialog();
        DS.Tables.Remove("Comm");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Comm"].Copy());
        //AppointmentL.GetApptEditComm(AptCur.AptNum));
        fillComm();
    }

    private void fillProcedures() throws Exception {
        gridProc.beginUpdate();
        gridProc.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableApptProcs","Stat"),35);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Priority"),45);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Tth"),25);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Surf"),50);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Code"),50);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Description"),275);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Fee"), 60, HorizontalAlignment.Right);
        gridProc.getColumns().add(col);
        gridProc.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < DS.Tables["Procedure"].Rows.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["status"].ToString());
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["priority"].ToString());
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["toothNum"].ToString());
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["Surf"].ToString());
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["ProcCode"].ToString());
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["descript"].ToString());
            row.getCells().Add(DS.Tables["Procedure"].Rows[i]["fee"].ToString());
            gridProc.getRows().add(row);
        }
        gridProc.endUpdate();
        for (int i = 0;i < DS.Tables["Procedure"].Rows.Count;i++)
        {
            if (StringSupport.equals(DS.Tables["Procedure"].Rows[i]["attached"].ToString(), "1"))
            {
                gridProc.setSelected(i,true);
            }
             
        }
    }

    private void butAddComm_Click(Object sender, EventArgs e) throws Exception {
        Commlog CommlogCur = new Commlog();
        CommlogCur.PatNum = AptCur.PatNum;
        CommlogCur.CommDateTime = DateTime.Now;
        CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
        CommlogCur.UserNum = Security.getCurUser().UserNum;
        FormCommItem FormCI = new FormCommItem(CommlogCur);
        FormCI.IsNew = true;
        FormCI.ShowDialog();
        DS.Tables.Remove("Comm");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Comm"].Copy());
        //AppointmentL.GetApptEditComm(AptCur.AptNum));
        fillComm();
    }

    private void butText_Click(Object sender, EventArgs e) throws Exception {
        String message = new String();
        message = PrefC.getString(PrefName.ConfirmTextMessage);
        message = message.Replace("[NameF]", pat.getNameFirst());
        message = message.Replace("[NameFL]", pat.getNameFL());
        message = message.Replace("[date]", AptCur.AptDateTime.ToShortDateString());
        message = message.Replace("[time]", AptCur.AptDateTime.ToShortTimeString());
        FormTxtMsgEdit FormTME = new FormTxtMsgEdit();
        FormTME.PatNum = pat.PatNum;
        FormTME.WirelessPhone = pat.WirelessPhone;
        FormTME.Message = message;
        FormTME.TxtMsgOk = pat.TxtMsgOk;
        FormTME.ShowDialog();
    }

    private void gridProc_CellClick(Object sender, ODGridClickEventArgs e) throws Exception {
        boolean isSelected = false;
        for (int i = 0;i < gridProc.getSelectedIndices().Length;i++)
        {
            if (gridProc.getSelectedIndices()[i] == e.getRow())
            {
                isSelected = true;
            }
             
        }
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        List<long> procNums = new List<long>();
        procNums.Add(PIn.Long(DS.Tables["Procedure"].Rows[e.getRow()]["ProcNum"].ToString()));
        if (isSelected)
        {
            //gridProc.SetSelected(e.Row,false);
            Procedures.DetachFromApt(procNums, isPlanned);
        }
        else
        {
            //gridProc.SetSelected(e.Row,true);
            Procedures.AttachToApt(procNums, AptCur.AptNum, isPlanned);
        } 
        //if(!isPlanned) {
        //	List<string> procCodes=new List<string>();
        //	procCodes.Add(DS.Tables["Procedure"].Rows[e.Row]["ProcCode"].ToString());
        //	Recalls.SynchScheduledApptLazy(AptCur.PatNum,AptCur.AptDateTime,procCodes);//moved to closing event
        //}
        Recalls.synch(AptCur.PatNum);
        //Maybe we should move this to the closing event?
        //manually change existing table instead of refreshing from db?
        DS.Tables.Remove("Procedure");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
        fillProcedures();
        calculateTime();
        fillTime();
        calcPatientFeeThisAppt();
    }

    private void gridProc_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        long procNum = PIn.Long(DS.Tables["Procedure"].Rows[e.getRow()]["ProcNum"].ToString());
        Procedure proc = Procedures.getOneProc(procNum,true);
        FormProcEdit FormP = new FormProcEdit(proc,pat,fam);
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DS.Tables.Remove("Procedure");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
        fillProcedures();
        calculateTime();
        fillTime();
        //make sure the one we double clicked on is highlighted if found
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        for (int i = 0;i < DS.Tables["Procedure"].Rows.Count;i++)
        {
            if (StringSupport.equals(DS.Tables["Procedure"].Rows[i]["attached"].ToString(), "1"))
            {
                continue;
            }
             
            //if already attached, skip
            if (DS.Tables["Procedure"].Rows[i]["ProcNum"].ToString() == procNum.ToString())
            {
                Procedures.attachToApt(procNum,AptCur.AptNum,isPlanned);
                Recalls.synch(AptCur.PatNum);
                DS.Tables.Remove("Procedure");
                DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
                fillProcedures();
                calculateTime();
                fillTime();
                break;
            }
             
        }
    }

    /*private void butRemove_Click(object sender,EventArgs e) {
    			if(gridProc.SelectedIndices.Length==0){
    				MsgBox.Show(this,"Please select one or more procedures first.");
    				return;
    			}
    			List<int> procNums=new List<int>();
    			for(int i=0;i<gridProc.SelectedIndices.Length;i++){
    				procNums.Add(PIn.PInt(DS.Tables["Procedure"].Rows[gridProc.SelectedIndices[i]]["ProcNum"].ToString()));
    			}
    			bool isPlanned=AptCur.AptStatus==ApptStatus.Planned;
    			Procedures.DetachFromApt(procNums,isPlanned);
    			Recalls.Synch(AptCur.PatNum);//needs to be moved into Procedures.Delete
    			DS.Tables.Remove("Procedure");
    			DS.Tables.Add(Appointments.GetApptEdit(AptCur.AptNum).Tables["Procedure"].Clone());
    			FillProcedures();
    		}*/
    private void butDeleteProc_Click(Object sender, EventArgs e) throws Exception {
        //this button will not be enabled if user does not have permission for AppointmentEdit
        if (gridProc.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select one or more procedures first.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Permanently delete all selected procedure(s)?"))
        {
            return ;
        }
         
        int skipped = 0;
        int skippedSecurity = 0;
        try
        {
            for (int i = 0;i < gridProc.getSelectedIndices().Length;i++)
            {
                Procedure proc = Procedures.GetOneProc(PIn.Long(DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ProcNum"].ToString()), false);
                if (!Security.isAuthorized(Permissions.ProcComplEdit,proc.DateEntryC,true))
                {
                    if (proc.ProcStatus == ProcStat.C)
                    {
                        skipped++;
                        continue;
                    }
                     
                }
                 
                if (!Security.isAuthorized(Permissions.ProcDelete,proc.DateEntryC,true))
                {
                    skippedSecurity++;
                    continue;
                }
                 
                //also deletes the claimProcs and adjustments. Might throw exception.
                Procedures.Delete(PIn.Long(DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ProcNum"].ToString()));
                SecurityLogs.MakeLogEntry(Permissions.ProcDelete, AptCur.PatNum, DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ProcCode"].ToString() + ", " + proc.ProcFee.ToString("c"));
            }
        }
        catch (Exception ex)
        {
            Recalls.synch(AptCur.PatNum);
            //needs to be moved into Procedures.Delete
            DS.Tables.Remove("Procedure");
            DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
            fillProcedures();
            MessageBox.Show(ex.Message);
        }

        Recalls.synch(AptCur.PatNum);
        //needs to be moved into Procedures.Delete
        DS.Tables.Remove("Procedure");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
        fillProcedures();
        calculateTime();
        fillTime();
        if (skipped > 0)
        {
            MessageBox.Show(Lan.g(this,"Procedures skipped due to lack of permission to edit completed procedures: ") + skipped.ToString());
        }
         
        if (skippedSecurity > 0)
        {
            MessageBox.Show(Lan.g(this,"Procedures skipped due to lack of permission to delete procedures: ") + skippedSecurity.ToString());
        }
         
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        if (comboProvNum.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a dentist.");
            return ;
        }
         
        FormProcCodes FormP = new FormProcCodes();
        FormP.IsSelectionMode = true;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Procedure ProcCur;
        ProcCur = new Procedure();
        //going to be an insert, so no need to set Procedures.CurOld
        ProcCur.CodeNum = FormP.SelectedCodeNum;
        //procnum
        ProcCur.PatNum = AptCur.PatNum;
        //aptnum
        //proccode
        //ProcCur.CodeNum=ProcedureCodes.GetProcCode(ProcCur.OldCode).CodeNum;//already set
        ProcCur.ProcDate = DateTimeOD.getToday();
        ProcCur.DateTP = ProcCur.ProcDate;
        //int totUnits = ProcCur.BaseUnits + ProcCur.UnitQty;
        InsPlan priplan = null;
        InsSub prisub = null;
        //Family fam=Patients.GetFamily(AptCur.PatNum);
        //Patient pat=fam.GetPatient(AptCur.PatNum);
        //InsPlan[] planList=InsPlans.Refresh(fam);
        List<PatPlan> patPlanList = PatPlans.refresh(pat.PatNum);
        if (patPlanList.Count > 0)
        {
            prisub = InsSubs.GetSub(patPlanList[0].InsSubNum, SubList);
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
            feeSch = Fees.GetMedFeeSched(pat, PlanList, patPlanList, SubList);
        }
        else
        {
            feeSch = Fees.GetFeeSched(pat, PlanList, patPlanList, SubList);
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
        if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
        {
            //PPO
            double standardfee = Fees.getAmount0(ProcCur.CodeNum,Providers.getProv(Patients.getProvNum(pat)).FeeSched);
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
        //surf
        //ToothNum
        //Procedures.Cur.ToothRange
        //ProcCur.NoBillIns=ProcedureCodes.GetProcCode(ProcCur.ProcCode).NoBillIns;
        ProcCur.Priority = 0;
        ProcCur.ProcStatus = ProcStat.TP;
        long aptProvNum = ProviderC.getListShort()[0].ProvNum;
        if (comboProvNum.SelectedIndex != -1)
        {
            aptProvNum = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
        }
         
        long aptProvHyg = 0;
        if (comboProvHyg.SelectedIndex > 0)
        {
            aptProvHyg = ProviderC.getListShort()[comboProvHyg.SelectedIndex - 1].ProvNum;
        }
         
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).IsHygiene && aptProvHyg != 0)
        {
            ProcCur.ProvNum = aptProvHyg;
        }
        else
        {
            ProcCur.ProvNum = aptProvNum;
        } 
        if (ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault != 0)
        {
            //Override provider for procedures with a default provider
            ProcCur.ProvNum = ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault;
        }
         
        ProcCur.Note = "";
        ProcCur.ClinicNum = pat.ClinicNum;
        //dx
        //nextaptnum
        ProcCur.DateEntryC = DateTime.Now;
        ProcCur.BaseUnits = ProcedureCodes.getProcCode(ProcCur.CodeNum).BaseUnits;
        ProcCur.SiteNum = pat.SiteNum;
        ProcCur.RevCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).RevenueCodeDefault;
        ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
        Procedures.insert(ProcCur);
        List<Benefit> benefitList = Benefits.Refresh(patPlanList, SubList);
        Procedures.ComputeEstimates(ProcCur, pat.PatNum, new List<ClaimProc>(), true, PlanList, patPlanList, benefitList, pat.getAge(), SubList);
        FormProcEdit FormPE = new FormProcEdit(ProcCur,pat.copy(),fam);
        FormPE.IsNew = true;
        if (Programs.getUsingOrion())
        {
            FormPE.OrionProvNum = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
            FormPE.OrionDentist = true;
        }
         
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

            return ;
        }
        else if (Programs.getUsingOrion())
        {
        }
        else
        {
            //No need to synch with Orion mode.
            //Default is set to TP, so Synch is usually not needed.
            if (ProcCur.ProcStatus == ProcStat.C || ProcCur.ProcStatus == ProcStat.EC || ProcCur.ProcStatus == ProcStat.EO)
            {
                Recalls.synch(pat.PatNum);
            }
             
        }  
        /*
        			FormApptProcs FormAP=new FormApptProcs();
        			FormAP.AptCur=AptCur.Clone();
        			//but we do need the status to be accurate:
        			if (AptCur.AptStatus == ApptStatus.Planned) {
        				;
        			}
        			else if(comboStatus.SelectedIndex==-1) {
        				FormAP.AptCur.AptStatus=ApptStatus.Scheduled;
        			}
        			else if (AptCur.AptStatus == ApptStatus.PtNote | AptCur.AptStatus == ApptStatus.PtNoteCompleted){
        				FormAP.AptCur.AptStatus = (ApptStatus)comboStatus.SelectedIndex + 7;
        			}
        			else {
        				FormAP.AptCur.AptStatus=(ApptStatus)comboStatus.SelectedIndex+1;
        			}
        			FormAP.ShowDialog();
        			if(FormAP.DialogResult!=DialogResult.OK){
        				return;
        			}*/
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        Procedures.attachToApt(ProcCur.ProcNum,AptCur.AptNum,isPlanned);
        Recalls.synch(AptCur.PatNum);
        //might not be needed because TP?
        DS.Tables.Remove("Procedure");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
        fillProcedures();
        calculateTime();
        fillTime();
    }

    private void butQuickAdd_Click(Object sender, EventArgs e) throws Exception {
    }

    /*
    			if(AptCur.AptStatus==ApptStatus.Complete) {
    				//added procedures would be marked complete when form closes. We'll just stop it here.
    				if(!Security.IsAuthorized(Permissions.ProcComplCreate)) {
    					return;
    				}
    			}
    			FormApptQuickAdd formAq=new FormApptQuickAdd();
    			formAq.ParentFormLocation=this.Location;
    			formAq.ShowDialog();
    			if(formAq.DialogResult!=DialogResult.OK) {
    				return;
    			}
    			Procedures.SetDateFirstVisit(AptCur.AptDateTime.Date,1,pat);
    			List<PatPlan> PatPlanList=PatPlans.Refresh(AptCur.PatNum);
    			List<Benefit> benefitList=Benefits.Refresh(PatPlanList);
    			List<ClaimProc> ClaimProcList=ClaimProcs.Refresh(AptCur.PatNum);
    			List<long> selectedProcNums=new List<long>();//start with the originally selected list, then add the new ones.
    			for(int i=0;i<gridProc.SelectedIndices.Length;i++) {
    				selectedProcNums.Add(PIn.Long(DS.Tables["Procedure"].Rows[gridProc.SelectedIndices[i]]["ProcNum"].ToString()));
    			}
    			for(int i=0;i<formAq.SelectedCodeNums.Count;i++) {
    				Procedure ProcCur=new Procedure();
    				ProcCur.PatNum=AptCur.PatNum;
    				if(AptCur.AptStatus!=ApptStatus.Planned) {
    					ProcCur.AptNum=AptCur.AptNum;
    				}
    				ProcCur.CodeNum=formAq.SelectedCodeNums[i];
    				ProcCur.ProcDate=AptCur.AptDateTime.Date;
    				ProcCur.DateTP=AptCur.AptDateTime.Date;
    				InsPlan priplan=null;
    				if(PatPlanList.Count>0) {
    					priplan=InsPlans.GetPlan(PatPlanList[0].PlanNum,PlanList);
    				}
    				double insfee=Fees.GetAmount0(ProcCur.CodeNum,Fees.GetFeeSched(pat,PlanList,PatPlanList));
    				if(priplan!=null && priplan.PlanType=="p") {//PPO
    					double standardfee=Fees.GetAmount0(ProcCur.CodeNum,Providers.GetProv(Patients.GetProvNum(pat)).FeeSched);
    					if(standardfee>insfee) {
    						ProcCur.ProcFee=standardfee;
    					}
    					else {
    						ProcCur.ProcFee=insfee;
    					}
    				}
    				else {
    					ProcCur.ProcFee=insfee;
    				}
    				//surf
    				//toothnum
    				//toothrange
    				//priority
    				ProcCur.ProcStatus=ProcStat.TP;
    				//procnote
    				ProcCur.ProvNum=AptCur.ProvNum;
    				//Dx
    				ProcCur.ClinicNum=AptCur.ClinicNum;
    				ProcCur.SiteNum=pat.SiteNum;
    				if(AptCur.AptStatus==ApptStatus.Planned) {
    					ProcCur.PlannedAptNum=AptCur.AptNum;
    				}
    				ProcCur.MedicalCode=ProcedureCodes.GetProcCode(ProcCur.CodeNum).MedicalCode;
    				ProcCur.BaseUnits=ProcedureCodes.GetProcCode(ProcCur.CodeNum).BaseUnits;
    				Procedures.Insert(ProcCur);//recall synch not required
    				selectedProcNums.Add(ProcCur.ProcNum);
    				Procedures.ComputeEstimates(ProcCur,pat.PatNum,ClaimProcList,false,PlanList,PatPlanList,benefitList,pat.Age);
    			}
    			//listQuickAdd.SelectedIndex=-1;
    			DS.Tables.Remove("Procedure");
    			DS.Tables.Add(Appointments.GetApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
    			FillProcedures();
    			for(int i=0;i<gridProc.Rows.Count;i++) {
    				for(int j=0;j<selectedProcNums.Count;j++) {
    					if(selectedProcNums[j].ToString()==DS.Tables["Procedure"].Rows[i]["ProcNum"].ToString()) {
    						gridProc.SetSelected(i,true);
    					}
    				}
    			}
    			CalculateTime();
    			FillTime();
    			CalcPatientFeeThisAppt();*/
    private void fillTime() throws Exception {
        System.Drawing.Color provColor = System.Drawing.Color.Gray;
        if (comboProvNum.SelectedIndex != -1)
        {
            provColor = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvColor;
        }
         
        if (strBTime.Length > tbTime.MaxRows)
        {
            strBTime.Remove(tbTime.MaxRows - 1, strBTime.Length - tbTime.MaxRows + 1);
            //example: Remove(40-1,78-40+1), start at 39, remove 39.
            MsgBox.show(this,"Appointment time shortened.  10 and 15 minute increments allow longer appointments than 5 minute increments.");
        }
         
        for (int i = 0;i < strBTime.Length;i++)
        {
            if (StringSupport.equals(strBTime.ToString(i, 1), "X"))
            {
                tbTime.BackGColor[0, i] = provColor;
            }
            else
            {
                //.Cell[0,i]=strBTime.ToString(i,1);
                tbTime.BackGColor[0, i] = System.Drawing.Color.White;
            } 
        }
        for (int i = strBTime.Length;i < tbTime.MaxRows;i++)
        {
            //tbTime.Cell[0,i]="";
            tbTime.BackGColor[0, i] = System.Drawing.Color.FromName("Control");
        }
        tbTime.Refresh();
        butSlider.Location = new Point(tbTime.Location.X + 2, (tbTime.Location.Y + strBTime.Length * 14 + 1));
        textTime.Text = (strBTime.Length * ApptDrawing.MinPerIncr).ToString();
    }

    private void calculateTime() throws Exception {
        if (checkTimeLocked.Checked)
        {
            return ;
        }
         
        //We are using the providers selected for the appt rather than the providers for the procs.
        //Providers for the procs get reset when closing this form.
        long provDent = Patients.getProvNum(pat);
        long provHyg = Patients.getProvNum(pat);
        if (comboProvNum.SelectedIndex != -1)
        {
            provDent = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
            provHyg = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
        }
         
        if (comboProvHyg.SelectedIndex != 0)
        {
            provHyg = ProviderC.getListShort()[comboProvHyg.SelectedIndex - 1].ProvNum;
        }
         
        List<long> codeNums = new List<long>();
        for (int i = 0;i < gridProc.getSelectedIndices().Length;i++)
        {
            codeNums.Add(PIn.Long(DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["CodeNum"].ToString()));
        }
        strBTime = new StringBuilder(Appointments.CalculatePattern(provDent, provHyg, codeNums, false));
    }

    //Plugins.HookAddCode(this,"FormApptEdit.CalculateTime_end",strBTime,provDent,provHyg,codeNums);//set strBTime, but without using the 'new' keyword.--Hook removed.
    private void checkTimeLocked_Click(Object sender, EventArgs e) throws Exception {
        calculateTime();
        fillTime();
    }

    private void tbTime_CellClicked(Object sender, CellEventArgs e) throws Exception {
        if (e.getRow() < strBTime.Length)
        {
            if (strBTime[e.getRow()] == '/')
            {
                strBTime.Replace('/', 'X', e.getRow(), 1);
            }
            else
            {
                strBTime.Replace(strBTime[e.getRow()], '/', e.getRow(), 1);
            } 
        }
         
        fillTime();
    }

    private void butSlider_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = true;
        mouseOrigin = new Point(e.X + butSlider.Location.X, e.Y + butSlider.Location.Y);
        sliderOrigin = butSlider.Location;
    }

    private void butSlider_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (!mouseIsDown)
        {
            return ;
        }
         
        //tempPoint represents the new location of button of smooth dragging.
        Point tempPoint = new Point(sliderOrigin.X, sliderOrigin.Y + (e.Y + butSlider.Location.Y) - mouseOrigin.Y);
        int step = (int)(Math.Round((Decimal)(tempPoint.Y - tbTime.Location.Y) / 14));
        if (step == strBTime.Length)
        {
            return ;
        }
         
        if (step < 1)
        {
            return ;
        }
         
        if (step > tbTime.MaxRows - 1)
        {
            return ;
        }
         
        if (step > strBTime.Length)
        {
            strBTime.Append('/');
        }
         
        if (step < strBTime.Length)
        {
            strBTime.Remove(step, 1);
        }
         
        checkTimeLocked.Checked = true;
        fillTime();
    }

    private void butSlider_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        mouseIsDown = false;
    }

    private void gridComm_MouseMove(Object sender, MouseEventArgs e) throws Exception {
    }

    private void gridPatient_MouseMove(Object sender, MouseEventArgs e) throws Exception {
    }

    private void listQuickAdd_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (comboProvNum.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a dentist.");
            return ;
        }
         
        if (listQuickAdd.IndexFromPoint(e.X, e.Y) == -1)
        {
            return ;
        }
         
        if (AptCur.AptStatus == ApptStatus.Complete)
        {
            //added procedures would be marked complete when form closes. We'll just stop it here.
            if (!Security.isAuthorized(Permissions.ProcComplCreate))
            {
                return ;
            }
             
        }
         
        Procedures.SetDateFirstVisit(AptCur.AptDateTime.Date, 1, pat);
        List<PatPlan> PatPlanList = PatPlans.refresh(AptCur.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(PatPlanList, SubList);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(AptCur.PatNum);
        String[] codes = DefC.getShort()[((Enum)DefCat.ApptProcsQuickAdd).ordinal()][listQuickAdd.IndexFromPoint(e.X, e.Y)].ItemValue.Split(',');
        for (int i = 0;i < codes.Length;i++)
        {
            if (!ProcedureCodeC.getHList().ContainsKey(codes[i]))
            {
                MsgBox.show(this,"Definition contains invalid code.");
                return ;
            }
             
        }
        for (int i = 0;i < codes.Length;i++)
        {
            Procedure ProcCur = new Procedure();
            ProcCur.PatNum = AptCur.PatNum;
            if (AptCur.AptStatus != ApptStatus.Planned)
            {
                ProcCur.AptNum = AptCur.AptNum;
            }
             
            ProcCur.CodeNum = ProcedureCodes.GetProcCode(codes[i]).CodeNum;
            ProcCur.ProcDate = AptCur.AptDateTime.Date;
            ProcCur.DateTP = DateTimeOD.getToday();
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
                feeSch = Fees.GetMedFeeSched(pat, PlanList, PatPlanList, SubList);
            }
            else
            {
                feeSch = Fees.GetFeeSched(pat, PlanList, PatPlanList, SubList);
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
            if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
            {
                //PPO
                double standardfee = Fees.getAmount0(ProcCur.CodeNum,Providers.getProv(Patients.getProvNum(pat)).FeeSched);
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
            //surf
            //toothnum
            //toothrange
            //priority
            ProcCur.ProcStatus = ProcStat.TP;
            //procnote
            if (ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault == 0)
            {
                //Override ProvNum if there is a default provider for this proc
                ProcCur.ProvNum = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
            }
            else
            {
                //Normal behavior
                ProcCur.ProvNum = ProcedureCodes.getProcCode(ProcCur.CodeNum).ProvNumDefault;
            } 
            //New behavior for procs with default provider
            //Dx
            ProcCur.ClinicNum = AptCur.ClinicNum;
            ProcCur.SiteNum = pat.SiteNum;
            ProcCur.RevCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).RevenueCodeDefault;
            if (AptCur.AptStatus == ApptStatus.Planned)
            {
                ProcCur.PlannedAptNum = AptCur.AptNum;
            }
             
            ProcCur.BaseUnits = ProcedureCodes.getProcCode(ProcCur.CodeNum).BaseUnits;
            ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
            Procedures.insert(ProcCur);
            //recall synch not required
            if (Programs.getUsingOrion())
            {
                //Orion requires a DPC for every procedure. Force proc edit window open.
                FormProcEdit FormP = new FormProcEdit(ProcCur,pat.copy(),fam);
                FormP.IsNew = true;
                FormP.OrionDentist = true;
                FormP.ShowDialog();
                if (FormP.DialogResult == DialogResult.Cancel)
                {
                    try
                    {
                        Procedures.delete(ProcCur.ProcNum);
                    }
                    catch (Exception ex)
                    {
                        //also deletes the claimprocs
                        MessageBox.Show(ex.Message);
                    }
                
                }
                 
            }
             
            Procedures.ComputeEstimates(ProcCur, pat.PatNum, ClaimProcList, false, PlanList, PatPlanList, benefitList, pat.getAge(), SubList);
        }
        listQuickAdd.SelectedIndex = -1;
        String[] selectedProcs = new String[gridProc.getSelectedIndices().Length];
        for (int i = 0;i < selectedProcs.Length;i++)
        {
            selectedProcs[i] = DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ProcNum"].ToString();
        }
        DS.Tables.Remove("Procedure");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Procedure"].Copy());
        fillProcedures();
        for (int i = 0;i < gridProc.getRows().Count;i++)
        {
            for (int j = 0;j < selectedProcs.Length;j++)
            {
                if (selectedProcs[j] == DS.Tables["Procedure"].Rows[i]["ProcNum"].ToString())
                {
                    gridProc.setSelected(i,true);
                }
                 
            }
        }
        calculateTime();
        fillTime();
        calcPatientFeeThisAppt();
    }

    private void butLab_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(DS.Tables["Misc"].Rows[0]["LabCaseNum"].ToString(), "0"))
        {
            //no labcase
            //so let user pick one to add
            FormLabCaseSelect FormL = new FormLabCaseSelect();
            FormL.PatNum = AptCur.PatNum;
            FormL.IsPlanned = AptCur.AptStatus == ApptStatus.Planned;
            FormL.ShowDialog();
            if (FormL.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
            if (AptCur.AptStatus == ApptStatus.Planned)
            {
                LabCases.attachToPlannedAppt(FormL.SelectedLabCaseNum,AptCur.AptNum);
            }
            else
            {
                LabCases.attachToAppt(FormL.SelectedLabCaseNum,AptCur.AptNum);
            } 
        }
        else
        {
            //already a labcase attached
            FormLabCaseEdit FormLCE = new FormLabCaseEdit();
            FormLCE.CaseCur = LabCases.GetOne(PIn.Long(DS.Tables["Misc"].Rows[0]["LabCaseNum"].ToString()));
            FormLCE.ShowDialog();
            if (FormLCE.DialogResult != DialogResult.OK)
            {
                return ;
            }
             
        } 
        //Deleting or detaching labcase would have been done from in that window
        DS.Tables.Remove("Misc");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Misc"].Copy());
        textLabCase.Text = DS.Tables["Misc"].Rows[0]["labDescript"].ToString();
    }

    private void butInsPlan1_Click(Object sender, EventArgs e) throws Exception {
        FormInsPlanSelect FormIPS = new FormInsPlanSelect(AptCur.PatNum);
        FormIPS.ShowNoneButton = true;
        FormIPS.ViewRelat = false;
        FormIPS.ShowDialog();
        if (FormIPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (FormIPS.SelectedPlan == null)
        {
            AptCur.InsPlan1 = 0;
            textInsPlan1.Text = "";
            return ;
        }
         
        AptCur.InsPlan1 = FormIPS.SelectedPlan.PlanNum;
        textInsPlan1.Text = InsPlans.getCarrierName(AptCur.InsPlan1,PlanList);
    }

    private void butInsPlan2_Click(Object sender, EventArgs e) throws Exception {
        FormInsPlanSelect FormIPS = new FormInsPlanSelect(AptCur.PatNum);
        FormIPS.ShowNoneButton = true;
        FormIPS.ViewRelat = false;
        FormIPS.ShowDialog();
        if (FormIPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        if (FormIPS.SelectedPlan == null)
        {
            AptCur.InsPlan2 = 0;
            textInsPlan2.Text = "";
            return ;
        }
         
        AptCur.InsPlan2 = FormIPS.SelectedPlan.PlanNum;
        textInsPlan2.Text = InsPlans.getCarrierName(AptCur.InsPlan2,PlanList);
    }

    private void butRequirement_Click(Object sender, EventArgs e) throws Exception {
        FormReqAppt FormR = new FormReqAppt();
        FormR.AptNum = AptCur.AptNum;
        FormR.PatNum = AptCur.PatNum;
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DS.Tables.Remove("Misc");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["Misc"].Copy());
        textRequirement.Text = DS.Tables["Misc"].Rows[0]["requirements"].ToString();
    }

    private void butSyndromicObservations_Click(Object sender, EventArgs e) throws Exception {
        FormEhrAptObses formE = new FormEhrAptObses(AptCur);
        formE.ShowDialog();
    }

    private void menuItemArrivedNow_Click(Object sender, EventArgs e) throws Exception {
        textTimeArrived.Text = DateTime.Now.ToShortTimeString();
    }

    private void menuItemSeatedNow_Click(Object sender, EventArgs e) throws Exception {
        textTimeSeated.Text = DateTime.Now.ToShortTimeString();
    }

    private void menuItemDismissedNow_Click(Object sender, EventArgs e) throws Exception {
        textTimeDismissed.Text = DateTime.Now.ToShortTimeString();
    }

    private void gridFields_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        if (ApptFieldDefs.hasDuplicateFieldNames())
        {
            //Check for duplicate field names.
            MsgBox.show(this,"There are duplicate appointment field defs, go rename or delete the duplicates.");
            return ;
        }
         
        ApptField field = ApptFields.GetOne(PIn.Long(DS.Tables["ApptFields"].Rows[e.getRow()]["ApptFieldNum"].ToString()));
        if (field == null)
        {
            field = new ApptField();
            field.AptNum = AptCur.AptNum;
            field.FieldName = DS.Tables["ApptFields"].Rows[e.getRow()]["FieldName"].ToString();
            if (ApptFieldDefs.getListt()[e.getRow()].FieldType == ApptFieldType.Text)
            {
                FormApptFieldEdit formAF = new FormApptFieldEdit(field);
                formAF.IsNew = true;
                formAF.ShowDialog();
            }
            else if (ApptFieldDefs.getListt()[e.getRow()].FieldType == ApptFieldType.PickList)
            {
                FormApptFieldPickEdit formAF = new FormApptFieldPickEdit(field);
                formAF.IsNew = true;
                formAF.ShowDialog();
            }
              
        }
        else
        {
            if (ApptFieldDefs.getListt()[e.getRow()].FieldType == ApptFieldType.Text)
            {
                FormApptFieldEdit formAF = new FormApptFieldEdit(field);
                formAF.ShowDialog();
            }
            else if (ApptFieldDefs.getListt()[e.getRow()].FieldType == ApptFieldType.PickList)
            {
                FormApptFieldPickEdit formAF = new FormApptFieldPickEdit(field);
                formAF.ShowDialog();
            }
              
        } 
        DS.Tables.Remove("ApptFields");
        DS.Tables.Add(Appointments.getApptEdit(AptCur.AptNum).Tables["ApptFields"].Copy());
        fillFields();
    }

    /**
    * Called from butOK_Click and butPin_Click
    */
    private boolean updateToDB() throws Exception {
        DateTime dateTimeAskedToArrive = DateTime.MinValue;
        if (!StringSupport.equals(textTimeAskedToArrive.Text, ""))
        {
            try
            {
                dateTimeAskedToArrive = AptCur.AptDateTime.Date + DateTime.Parse(textTimeAskedToArrive.Text).TimeOfDay;
            }
            catch (Exception __dummyCatchVar0)
            {
                MsgBox.show(this,"Time Asked To Arrive invalid.");
                return false;
            }
        
        }
         
        DateTime dateTimeArrived = AptCur.AptDateTime.Date;
        if (!StringSupport.equals(textTimeArrived.Text, ""))
        {
            try
            {
                dateTimeArrived = AptCur.AptDateTime.Date + DateTime.Parse(textTimeArrived.Text).TimeOfDay;
            }
            catch (Exception __dummyCatchVar1)
            {
                MsgBox.show(this,"Time Arrived invalid.");
                return false;
            }
        
        }
         
        DateTime dateTimeSeated = AptCur.AptDateTime.Date;
        if (!StringSupport.equals(textTimeSeated.Text, ""))
        {
            try
            {
                dateTimeSeated = AptCur.AptDateTime.Date + DateTime.Parse(textTimeSeated.Text).TimeOfDay;
            }
            catch (Exception __dummyCatchVar2)
            {
                MsgBox.show(this,"Time Seated invalid.");
                return false;
            }
        
        }
         
        DateTime dateTimeDismissed = AptCur.AptDateTime.Date;
        if (!StringSupport.equals(textTimeDismissed.Text, ""))
        {
            try
            {
                dateTimeDismissed = AptCur.AptDateTime.Date + DateTime.Parse(textTimeDismissed.Text).TimeOfDay;
            }
            catch (Exception __dummyCatchVar3)
            {
                MsgBox.show(this,"Time Arrived invalid.");
                return false;
            }
        
        }
         
        //This change was just slightly too risky to make to 6.9, so 7.0 only
        //was not originally complete
        //making it complete
        if (AptCur.AptStatus != ApptStatus.Complete && AptCur.AptStatus != ApptStatus.PtNote && AptCur.AptStatus != ApptStatus.PtNoteCompleted && comboStatus.SelectedIndex == 1 && AptCur.AptDateTime.Date > DateTime.Today)
        {
            //and future appt
            MsgBox.show(this,"Not allowed to set complete future appointments.");
            return false;
        }
         
        String aptPattern = Appointments.ConvertPatternTo5(strBTime.ToString());
        //Only run appt overlap check if editing an appt from the chart module and eCW program link not enabled.
        if (IsInChartModule && !Programs.usingEcwTightOrFullMode())
        {
            List<Appointment> apptList = Appointments.getForPeriodList(AptCur.AptDateTime,AptCur.AptDateTime);
            if (DoesOverlap(aptPattern, apptList))
            {
                MsgBox.show(this,"Appointment is too long and would overlap another appointment.  Automatically shortened to fit.");
                do
                {
                    aptPattern = aptPattern.Substring(0, aptPattern.Length - 1);
                    if (aptPattern.Length == 1)
                    {
                        break;
                    }
                     
                }
                while (DoesOverlap(aptPattern, apptList));
            }
             
        }
         
        if (AptCur.AptStatus == ApptStatus.Planned)
        {
                ;
        }
        else if (comboStatus.SelectedIndex == -1)
        {
            AptCur.AptStatus = ApptStatus.Scheduled;
        }
        else if (AptCur.AptStatus == ApptStatus.PtNote | AptCur.AptStatus == ApptStatus.PtNoteCompleted)
        {
            AptCur.AptStatus = (ApptStatus)comboStatus.SelectedIndex + 7;
        }
        else
        {
            AptCur.AptStatus = (ApptStatus)comboStatus.SelectedIndex + 1;
        }   
        //set procs complete was moved further down
        //convert from current increment into 5 minute increment
        //MessageBox.Show(strBTime.ToString());
        AptCur.Pattern = aptPattern;
        if (comboUnschedStatus.SelectedIndex == 0)
        {
            //none
            AptCur.UnschedStatus = 0;
        }
        else
        {
            AptCur.UnschedStatus = DefC.getShort()[((Enum)DefCat.RecallUnschedStatus).ordinal()][comboUnschedStatus.SelectedIndex - 1].DefNum;
        } 
        if (comboConfirmed.SelectedIndex != -1)
        {
            AptCur.Confirmed = DefC.getShort()[((Enum)DefCat.ApptConfirmed).ordinal()][comboConfirmed.SelectedIndex].DefNum;
        }
         
        AptCur.TimeLocked = checkTimeLocked.Checked;
        AptCur.ColorOverride = butColor.BackColor;
        AptCur.Note = textNote.Text;
        if (comboClinic.SelectedIndex == 0)
        {
            //none
            AptCur.ClinicNum = 0;
        }
        else
        {
            AptCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        } 
        //there should always be a non-hidden primary provider for an appt.
        if (comboProvNum.SelectedIndex == -1)
        {
            AptCur.ProvNum = ProviderC.getListShort()[0].ProvNum;
        }
        else
        {
            AptCur.ProvNum = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
        } 
        if (comboProvHyg.SelectedIndex == 0)
        {
            //none
            AptCur.ProvHyg = 0;
        }
        else
        {
            AptCur.ProvHyg = ProviderC.getListShort()[comboProvHyg.SelectedIndex - 1].ProvNum;
        } 
        AptCur.IsHygiene = checkIsHygiene.Checked;
        if (comboAssistant.SelectedIndex == 0)
        {
            //none
            AptCur.Assistant = 0;
        }
        else
        {
            AptCur.Assistant = Employees.getListShort()[comboAssistant.SelectedIndex - 1].EmployeeNum;
        } 
        AptCur.IsNewPatient = checkIsNewPatient.Checked;
        AptCur.DateTimeAskedToArrive = dateTimeAskedToArrive;
        AptCur.DateTimeArrived = dateTimeArrived;
        AptCur.DateTimeSeated = dateTimeSeated;
        AptCur.DateTimeDismissed = dateTimeDismissed;
        //AptCur.InsPlan1 and InsPlan2 already handled
        //The ApptProcDescript region is also in FormProcEdit.SaveAndClose() and FormDatabaseMaintenance.butApptProcs_Click()  Make any changes there as well.
        AptCur.ProcDescript = "";
        AptCur.ProcsColored = "";
        for (int i = 0;i < gridProc.getSelectedIndices().Length;i++)
        {
            String procDescOne = "";
            String procCode = DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ProcCode"].ToString();
            if (i > 0)
            {
                AptCur.ProcDescript += ", ";
            }
             
            Rows.INDEXER.INDEXER.APPLY __dummyScrutVar0 = DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["TreatArea"].ToString();
            if (__dummyScrutVar0.equals("1"))
            {
                //TreatmentArea.Surf:
                procDescOne += "#" + Tooth.GetToothLabel(DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ToothNum"].ToString()) + "-" + DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["Surf"].ToString() + "-";
            }
            else //""#12-MOD-"
            if (__dummyScrutVar0.equals("2"))
            {
                //TreatmentArea.Tooth:
                procDescOne += "#" + Tooth.GetToothLabel(DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ToothNum"].ToString()) + "-";
            }
            else //"#12-"
            //area 3 or 0 (mouth)
            if (__dummyScrutVar0.equals("4"))
            {
                //TreatmentArea.Quad:
                procDescOne += DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["Surf"].ToString() + "-";
            }
            else //"UL-"
            if (__dummyScrutVar0.equals("5"))
            {
                //TreatmentArea.Sextant:
                procDescOne += "S" + DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["Surf"].ToString() + "-";
            }
            else //"S2-"
            if (__dummyScrutVar0.equals("6"))
            {
                //TreatmentArea.Arch:
                procDescOne += DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["Surf"].ToString() + "-";
            }
            else //"U-"
            if (__dummyScrutVar0.equals("7"))
            {
            }
            else
            {
            }      
            //TreatmentArea.ToothRange:
            //strLine+=table.Rows[j][13].ToString()+" ";//don't show range
            procDescOne += DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["AbbrDesc"].ToString();
            AptCur.ProcDescript += procDescOne;
            //Color and previous date are determined by ProcApptColor object
            ProcApptColor pac = ProcApptColors.getMatch(procCode);
            System.Drawing.Color pColor = System.Drawing.Color.Black;
            String prevDateString = "";
            if (pac != null)
            {
                pColor = pac.ColorText;
                if (pac.ShowPreviousDate)
                {
                    prevDateString = Procedures.getRecentProcDateString(AptCur.PatNum,AptCur.AptDateTime,pac.CodeRange);
                    if (!StringSupport.equals(prevDateString, ""))
                    {
                        prevDateString = " (" + prevDateString + ")";
                    }
                     
                }
                 
            }
             
            AptCur.ProcsColored += "<span color=\"" + pColor.ToArgb().ToString() + "\">" + procDescOne + prevDateString + "</span>";
        }
        boolean isPlanned = AptCur.AptStatus == ApptStatus.Planned;
        try
        {
            Appointments.update(AptCur,AptOld);
        }
        catch (ApplicationException ex)
        {
            //Appointments.UpdateAttached(AptCur.AptNum,procNums,isPlanned);
            MessageBox.Show(ex.Message);
            return false;
        }

        //if appointment is marked complete and any procedures are not,
        //then set the remaining procedures complete
        if (AptCur.AptStatus == ApptStatus.Complete)
        {
            boolean allProcsComplete = true;
            for (int i = 0;i < gridProc.getSelectedIndices().Length;i++)
            {
                if (!StringSupport.equals(DS.Tables["Procedure"].Rows[gridProc.getSelectedIndices()[i]]["ProcStatus"].ToString(), "2"))
                {
                    //Complete
                    allProcsComplete = false;
                    break;
                }
                 
            }
            if (!allProcsComplete)
            {
                if (!Security.isAuthorized(Permissions.ProcComplCreate,AptCur.AptDateTime))
                {
                    return false;
                }
                 
                List<PatPlan> PatPlanList = PatPlans.refresh(AptCur.PatNum);
                ProcedureL.SetCompleteInAppt(AptCur, PlanList, PatPlanList, pat.SiteNum, pat.getAge(), SubList);
                SecurityLogs.MakeLogEntry(Permissions.ProcComplCreate, pat.PatNum, AptCur.AptDateTime.ToShortDateString() + ", " + AptCur.ProcDescript + ", Procedures automatically set complete due to appt being set complete", 0);
            }
             
        }
        else
        {
            Procedures.setProvidersInAppointment(AptCur,Procedures.getProcsForSingle(AptCur.AptNum,false));
        } 
        //Do the appointment "break" automation for appointments that were just broken.
        if (AptCur.AptStatus == ApptStatus.Broken && AptOld.AptStatus != ApptStatus.Broken)
        {
            SecurityLogs.makeLogEntry(Permissions.AppointmentMove,pat.PatNum,AptCur.ProcDescript + ", " + AptCur.AptDateTime.ToString() + ", Broken by changing the Status in the Edit Appointment window.",AptCur.AptNum);
            if (PrefC.getBool(PrefName.BrokenApptCommLogNotAdjustment))
            {
                Commlog CommlogCur = new Commlog();
                CommlogCur.PatNum = pat.PatNum;
                CommlogCur.CommDateTime = DateTime.Now;
                CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
                CommlogCur.Note = Lan.g(this,"Appt BROKEN for ") + AptCur.ProcDescript + "  " + AptCur.AptDateTime.ToString();
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
                AdjustmentCur.ProvNum = AptCur.ProvNum;
                AdjustmentCur.PatNum = pat.PatNum;
                AdjustmentCur.AdjType = PrefC.getLong(PrefName.BrokenAppointmentAdjustmentType);
                AdjustmentCur.ClinicNum = pat.ClinicNum;
                FormAdjust FormA = new FormAdjust(pat,AdjustmentCur);
                FormA.IsNew = true;
                FormA.ShowDialog();
            } 
            AutomationL.trigger(AutomationTrigger.BreakAppointment,null,pat.PatNum);
        }
         
        return true;
    }

    /**
    * Tests all appts for the day, even not visible, to make sure AptCur doesn't overlap others. Pass in the pattern for the appt being edited and the list of appts to test against.
    */
    private boolean doesOverlap(String pattern, List<Appointment> apptList) throws Exception {
        DateTime aptDateTime = new DateTime();
        for (int i = 0;i < apptList.Count;i++)
        {
            if (apptList[i].AptNum == AptCur.AptNum)
            {
                continue;
            }
             
            if (apptList[i].Op != AptCur.Op)
            {
                continue;
            }
             
            aptDateTime = apptList[i].AptDateTime;
            if (aptDateTime.Date != AptCur.AptDateTime.Date)
            {
                continue;
            }
             
            //tests start time
            if (AptCur.AptDateTime.TimeOfDay >= aptDateTime.TimeOfDay && AptCur.AptDateTime.TimeOfDay < aptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(apptList[i].Pattern.Length * 5)))
            {
                return true;
            }
             
            //tests stop time
            if (AptCur.AptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(pattern.Length * 5)) > aptDateTime.TimeOfDay && AptCur.AptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(pattern.Length * 5)) <= aptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(apptList[i].Pattern.Length * 5)))
            {
                return true;
            }
             
            //tests engulf
            if (AptCur.AptDateTime.TimeOfDay <= aptDateTime.TimeOfDay && AptCur.AptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(pattern.Length * 5)) >= aptDateTime.TimeOfDay.Add(TimeSpan.FromMinutes(apptList[i].Pattern.Length * 5)))
            {
                return true;
            }
             
        }
        return false;
    }

    private void butPDF_Click(Object sender, EventArgs e) throws Exception {
        //this will only happen for eCW HL7 interface users.
        List<Procedure> procs = Procedures.getProcsForSingle(AptCur.AptNum,false);
        String duplicateProcs = ProcedureL.ProcsContainDuplicates(procs);
        if (!StringSupport.equals(duplicateProcs, ""))
        {
            MessageBox.Show(duplicateProcs);
            return ;
        }
         
        //Send DFT to eCW containing a dummy procedure with this appointment in a .pdf file.
        //no security
        String pdfDataStr = generateProceduresIntoPdf();
        if (HL7Defs.isExistingHL7Enabled())
        {
            //PDF messages do not contain FT1 segments, so proc list can be empty
            //MessageHL7 messageHL7=MessageConstructor.GenerateDFT(procs,EventTypeHL7.P03,pat,Patients.GetPat(pat.Guarantor),AptCur.AptNum,"progressnotes",pdfDataStr);
            MessageHL7 messageHL7 = MessageConstructor.GenerateDFT(new List<Procedure>(), EventTypeHL7.P03, pat, Patients.getPat(pat.Guarantor), AptCur.AptNum, "progressnotes", pdfDataStr);
            if (messageHL7 == null)
            {
                MsgBox.show(this,"There is no DFT message type defined for the enabled HL7 definition.");
                return ;
            }
             
            HL7Msg hl7Msg = new HL7Msg();
            //hl7Msg.AptNum=AptCur.AptNum;
            hl7Msg.AptNum = 0;
            //Prevents the appt complete button from changing to the "Revise" button prematurely.
            hl7Msg.HL7Status = HL7MessageStatus.OutPending;
            //it will be marked outSent by the HL7 service.
            hl7Msg.MsgText = messageHL7.toString();
            hl7Msg.PatNum = pat.PatNum;
            HL7Msgs.insert(hl7Msg);
        }
        else
        {
            ECW.sendHL7(AptCur.AptNum,AptCur.ProvNum,pat,pdfDataStr,"progressnotes",true);
        } 
        MsgBox.show(this,"Notes PDF sent.");
    }

    /**
    * Creates a new .pdf file containing all of the procedures attached to this appointment and
    * returns the contents of the .pdf file as a base64 encoded string.
    */
    private String generateProceduresIntoPdf() throws Exception {
        MigraDoc.DocumentObjectModel.Document doc = new MigraDoc.DocumentObjectModel.Document();
        doc.DefaultPageSetup.PageWidth = Unit.FromInch(8.5);
        doc.DefaultPageSetup.PageHeight = Unit.FromInch(11);
        doc.DefaultPageSetup.TopMargin = Unit.FromInch(.5);
        doc.DefaultPageSetup.LeftMargin = Unit.FromInch(.5);
        doc.DefaultPageSetup.RightMargin = Unit.FromInch(.5);
        MigraDoc.DocumentObjectModel.Section section = doc.AddSection();
        MigraDoc.DocumentObjectModel.Font headingFont = MigraDocHelper.CreateFont(13, true);
        MigraDoc.DocumentObjectModel.Font bodyFontx = MigraDocHelper.CreateFont(9, false);
        String text = new String();
        //Heading---------------------------------------------------------------------------------------------------------------
        Paragraph par = section.AddParagraph();
        ParagraphFormat parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Center;
        parformat.Font = MigraDocHelper.CreateFont(10, true);
        par.Format = parformat;
        text = Lan.g(this,"procedures").ToUpper();
        par.AddFormattedText(text, headingFont);
        par.AddLineBreak();
        text = pat.getNameFLFormal();
        par.AddFormattedText(text, headingFont);
        par.AddLineBreak();
        text = DateTime.Now.ToShortDateString();
        par.AddFormattedText(text, headingFont);
        par.AddLineBreak();
        par.AddLineBreak();
        //Procedure List--------------------------------------------------------------------------------------------------------
        OpenDental.UI.ODGrid gridProg = new OpenDental.UI.ODGrid();
        this.Controls.Add(gridProg);
        //Only added temporarily so that printing will work. Removed at end with Dispose().
        gridProg.beginUpdate();
        gridProg.getColumns().Clear();
        ODGridColumn col;
        List<DisplayField> fields = DisplayFields.getDefaultList(DisplayFieldCategory.None);
        for (int i = 0;i < fields.Count;i++)
        {
            if (StringSupport.equals(fields[i].InternalName, "User") || StringSupport.equals(fields[i].InternalName, "Signed"))
            {
                continue;
            }
             
            if (StringSupport.equals(fields[i].Description, ""))
            {
                col = new ODGridColumn(fields[i].InternalName, fields[i].ColumnWidth);
            }
            else
            {
                col = new ODGridColumn(fields[i].Description, fields[i].ColumnWidth);
            } 
            if (StringSupport.equals(fields[i].InternalName, "Amount"))
            {
                col.setTextAlign(HorizontalAlignment.Right);
            }
             
            if (StringSupport.equals(fields[i].InternalName, "ADA Code"))
            {
                col.setTextAlign(HorizontalAlignment.Center);
            }
             
            gridProg.getColumns().add(col);
        }
        gridProg.setNoteSpanStart(2);
        gridProg.setNoteSpanStop(7);
        gridProg.getRows().Clear();
        List<Procedure> procsForDay = Procedures.getProcsForPatByDate(AptCur.PatNum,AptCur.AptDateTime);
        for (int i = 0;i < procsForDay.Count;i++)
        {
            Procedure proc = procsForDay[i];
            ProcedureCode procCode = ProcedureCodes.getProcCodeFromDb(proc.CodeNum);
            Provider prov = Providers.getProv(proc.ProvNum);
            Userod usr = Userods.getUser(proc.UserNum);
            ODGridRow row = new ODGridRow();
            row.setColorLborder(System.Drawing.Color.Black);
            for (int f = 0;f < fields.Count;f++)
            {
                InternalName __dummyScrutVar1 = fields[f].InternalName;
                if (__dummyScrutVar1.equals("Date"))
                {
                    row.getCells().Add(proc.ProcDate.Date.ToShortDateString());
                }
                else if (__dummyScrutVar1.equals("Time"))
                {
                    row.getCells().Add(proc.ProcDate.ToString("h:mm") + proc.ProcDate.ToString("%t").ToLower());
                }
                else if (__dummyScrutVar1.equals("Th"))
                {
                    row.getCells().add(proc.ToothNum);
                }
                else if (__dummyScrutVar1.equals("Surf"))
                {
                    row.getCells().add(proc.Surf);
                }
                else if (__dummyScrutVar1.equals("Dx"))
                {
                    row.getCells().Add(proc.Dx.ToString());
                }
                else if (__dummyScrutVar1.equals("Description"))
                {
                    row.getCells().add((!StringSupport.equals(procCode.LaymanTerm, "")) ? procCode.LaymanTerm : procCode.Descript);
                }
                else if (__dummyScrutVar1.equals("Stat"))
                {
                    row.getCells().Add(Lans.g("enumProcStat", proc.ProcStatus.ToString()));
                }
                else if (__dummyScrutVar1.equals("Prov"))
                {
                    if (prov.Abbr.Length > 5)
                    {
                        row.getCells().Add(prov.Abbr.Substring(0, 5));
                    }
                    else
                    {
                        row.getCells().add(prov.Abbr);
                    } 
                }
                else if (__dummyScrutVar1.equals("Amount"))
                {
                    row.getCells().Add(proc.ProcFee.ToString("F"));
                }
                else if (__dummyScrutVar1.equals("ADA Code"))
                {
                    if (procCode.ProcCode.Length > 5 && procCode.ProcCode.StartsWith("D"))
                    {
                        row.getCells().Add(procCode.ProcCode.Substring(0, 5));
                    }
                    else
                    {
                        //Remove suffix from all D codes.
                        row.getCells().add(procCode.ProcCode);
                    } 
                }
                else if (__dummyScrutVar1.equals("User"))
                {
                    row.getCells().add(usr != null ? usr.UserName : "");
                }
                           
            }
            row.setNote(proc.Note);
            //Row text color.
            switch(proc.ProcStatus)
            {
                case TP: 
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][0].ItemColor);
                    break;
                case C: 
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][1].ItemColor);
                    break;
                case EC: 
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][2].ItemColor);
                    break;
                case EO: 
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][3].ItemColor);
                    break;
                case R: 
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][4].ItemColor);
                    break;
                case D: 
                    row.setColorText(System.Drawing.Color.Black);
                    break;
                case Cn: 
                    row.setColorText(DefC.getLong()[((Enum)DefCat.ProgNoteColors).ordinal()][22].ItemColor);
                    break;
            
            }
            row.setColorBackG(System.Drawing.Color.White);
            if (proc.ProcDate.Date == DateTime.Today)
            {
                row.setColorBackG(DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][6].ItemColor);
            }
             
            gridProg.getRows().add(row);
        }
        MigraDocHelper.DrawGrid(section, gridProg);
        MigraDoc.Rendering.PdfDocumentRenderer pdfRenderer = new MigraDoc.Rendering.PdfDocumentRenderer(true, PdfFontEmbedding.Always);
        pdfRenderer.Document = doc;
        pdfRenderer.RenderDocument();
        MemoryStream ms = new MemoryStream();
        pdfRenderer.PdfDocument.Save(ms);
        byte[] pdfBytes = ms.GetBuffer();
        //#region Remove when testing is complete.
        //string tempFilePath=Path.GetTempFileName();
        //File.WriteAllBytes(tempFilePath,pdfBytes);
        //#endregion
        String pdfDataStr = Convert.ToBase64String(pdfBytes);
        ms.Dispose();
        return pdfDataStr;
    }

    private void butComplete_Click(Object sender, EventArgs e) throws Exception {
        //This is only used with eCW HL7 interface.
        if (StringSupport.equals(butComplete.Text, "Finish && Send"))
        {
            List<Procedure> procs = Procedures.getProcsForSingle(AptCur.AptNum,false);
            String duplicateProcs = ProcedureL.ProcsContainDuplicates(procs);
            if (!StringSupport.equals(duplicateProcs, ""))
            {
                MessageBox.Show(duplicateProcs);
                return ;
            }
             
            /**
            * /check to make sure that the appointment and all attached procedures are marked complete as required.
            */
            //bool procsAreComplete=true;
            //for(int i=0;i<gridProc.SelectedIndices.Length;i++) {
            //  string procStat=gridProc.Rows[gridProc.SelectedIndices[i]].Cells[0].Text;
            //  if(procStat!="C") {
            //    procsAreComplete=false;
            //    break;
            //  }
            //}
            //if(!procsAreComplete) {
            //  MsgBox.Show(this,"The procedures must all have statuses set to complete first.");
            //  return;
            //}
            //user can only get this far if aptNum matches visit num previously passed in by eCW.
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Send attached procedures to eClinicalWorks and exit?"))
            {
                return ;
            }
             
            comboStatus.SelectedIndex = 1;
            //Set the appointment status to complete. This will trigger the procedures to be completed in UpdateToDB() as well.
            if (!updateToDB())
            {
                return ;
            }
             
            //Send DFT to eCW containing the attached procedures for this appointment in a .pdf file.
            String pdfDataStr = generateProceduresIntoPdf();
            if (HL7Defs.isExistingHL7Enabled())
            {
                //MessageConstructor.GenerateDFT(procs,EventTypeHL7.P03,pat,Patients.GetPat(pat.Guarantor),AptCur.AptNum,"progressnotes",pdfDataStr);
                MessageHL7 messageHL7 = MessageConstructor.GenerateDFT(procs, EventTypeHL7.P03, pat, Patients.getPat(pat.Guarantor), AptCur.AptNum, "progressnotes", pdfDataStr);
                if (messageHL7 == null)
                {
                    MsgBox.show(this,"There is no DFT message type defined for the enabled HL7 definition.");
                    return ;
                }
                 
                HL7Msg hl7Msg = new HL7Msg();
                hl7Msg.AptNum = AptCur.AptNum;
                hl7Msg.HL7Status = HL7MessageStatus.OutPending;
                //it will be marked outSent by the HL7 service.
                hl7Msg.MsgText = messageHL7.toString();
                hl7Msg.PatNum = pat.PatNum;
                HL7Msgs.insert(hl7Msg);
            }
            else
            {
                ECW.sendHL7(AptCur.AptNum,AptCur.ProvNum,pat,pdfDataStr,"progressnotes",false);
            } 
            CloseOD = true;
            if (IsNew)
            {
                SecurityLogs.MakeLogEntry(Permissions.AppointmentCreate, pat.PatNum, AptCur.AptDateTime.ToString() + ", " + AptCur.ProcDescript, AptCur.AptNum);
            }
             
            DialogResult = DialogResult.OK;
        }
        else if (StringSupport.equals(butComplete.Text, "Revise"))
        {
            if (!Security.isAuthorized(Permissions.EcwAppointmentRevise))
            {
                return ;
            }
             
            MsgBox.show(this,"Any changes that you make will not be sent to eCW.  You will also have to make the same changes in eCW.");
            //revise is only clickable if user has permission
            butOK.Enabled = true;
            gridProc.Enabled = true;
            listQuickAdd.Enabled = true;
            butAdd.Enabled = true;
            butDeleteProc.Enabled = true;
        }
          
    }

    private void butAudit_Click(Object sender, EventArgs e) throws Exception {
        List<Permissions> perms = new List<Permissions>();
        perms.Add(Permissions.AppointmentCreate);
        perms.Add(Permissions.AppointmentEdit);
        perms.Add(Permissions.AppointmentMove);
        FormAuditOneType FormA = new FormAuditOneType(pat.PatNum, perms, Lan.g(this,"Audit Trail for Appointment"), AptCur.AptNum);
        FormA.ShowDialog();
    }

    private void butTask_Click(Object sender, EventArgs e) throws Exception {
        if (!updateToDB())
        {
            return ;
        }
         
        FormTaskListSelect FormT = new FormTaskListSelect(TaskObjectType.Appointment);
        //,AptCur.AptNum);
        FormT.ShowDialog();
        if (FormT.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Task task = new Task();
        task.TaskListNum = -1;
        //don't show it in any list yet.
        Tasks.insert(task);
        Task taskOld = task.copy();
        task.KeyNum = AptCur.AptNum;
        task.ObjectType = TaskObjectType.Appointment;
        task.TaskListNum = FormT.SelectedTaskListNum;
        task.UserNum = Security.getCurUser().UserNum;
        FormTaskEdit FormTE = new FormTaskEdit(task,taskOld);
        FormTE.IsNew = true;
        FormTE.ShowDialog();
    }

    private void butPin_Click(Object sender, System.EventArgs e) throws Exception {
        if (!updateToDB())
            return ;
         
        PinClicked = true;
        DialogResult = DialogResult.OK;
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (AptCur.AptStatus == ApptStatus.PtNote || AptCur.AptStatus == ApptStatus.PtNoteCompleted)
        {
            if (!MsgBox.show(this,true,"Delete Patient Note?"))
            {
                return ;
            }
             
            if (!StringSupport.equals(textNote.Text, ""))
            {
                if (MessageBox.Show(Lan.g(this,"Save a copy of this note in CommLog? " + "\r\n" + "\r\n" + textNote.Text), "Question...", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    Commlog CommlogCur = new Commlog();
                    CommlogCur.PatNum = AptCur.PatNum;
                    CommlogCur.CommDateTime = DateTime.Now;
                    CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
                    CommlogCur.Note = "Deleted Pt NOTE from schedule, saved copy: ";
                    CommlogCur.Note += textNote.Text;
                    CommlogCur.UserNum = Security.getCurUser().UserNum;
                    //there is no dialog here because it is just a simple entry
                    Commlogs.insert(CommlogCur);
                }
                 
            }
             
        }
        else
        {
            //ordinary appointment
            if (MessageBox.Show(Lan.g(this,"Delete appointment?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
            if (!StringSupport.equals(textNote.Text, ""))
            {
                if (MessageBox.Show(Lan.g(this,"Save appointment note in CommLog? " + "\r\n" + "\r\n" + textNote.Text), "Question...", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    Commlog CommlogCur = new Commlog();
                    CommlogCur.PatNum = AptCur.PatNum;
                    CommlogCur.CommDateTime = DateTime.Now;
                    CommlogCur.CommType = Commlogs.getTypeAuto(CommItemTypeAuto.APPT);
                    CommlogCur.Note = "Deleted Appt. & saved note: ";
                    if (!StringSupport.equals(AptCur.ProcDescript, ""))
                    {
                        CommlogCur.Note += AptCur.ProcDescript + ": ";
                    }
                     
                    CommlogCur.Note += textNote.Text;
                    CommlogCur.UserNum = Security.getCurUser().UserNum;
                    //there is no dialog here because it is just a simple entry
                    Commlogs.insert(CommlogCur);
                }
                 
            }
             
        } 
        Appointments.delete(AptCur.AptNum);
        Recalls.synchScheduledApptFull(AptCur.PatNum);
        SecurityLogs.makeLogEntry(Permissions.AppointmentEdit,pat.PatNum,"Delete for date/time: " + AptCur.AptDateTime.ToString(),AptCur.AptNum);
        if (IsNew)
        {
            //The dialog is considered cancelled when a new appointment is immediately deleted.
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            DialogResult = DialogResult.OK;
        } 
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (comboProvNum.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a dentist.");
            return ;
        }
         
        if (!updateToDB())
        {
            return ;
        }
         
        if (IsNew)
        {
            SecurityLogs.makeLogEntry(Permissions.AppointmentEdit,pat.PatNum,"Create complete for date/time: " + AptCur.AptDateTime.ToString(),AptCur.AptNum);
        }
        else
        {
            SecurityLogs.MakeLogEntry(Permissions.AppointmentEdit, pat.PatNum, AptCur.AptDateTime.ToShortDateString() + ", " + AptCur.ProcDescript, AptCur.AptNum);
        } 
        List<String> procCodes = new List<String>();
        for (int i = 0;i < DS.Tables["Procedure"].Rows.Count;i++)
        {
            if (StringSupport.equals(DS.Tables["Procedure"].Rows[i]["attached"].ToString(), "0"))
            {
                continue;
            }
             
            //not attached
            procCodes.Add(DS.Tables["Procedure"].Rows[i]["ProcCode"].ToString());
        }
        //if(AptOld.AptStatus!=ApptStatus.Complete && AptCur.AptStatus==ApptStatus.Complete) {//user set appt complete
        //Recalls.SynchScheduledApptFull(AptCur.PatNum);
        //}
        //else {
        //This was causing bugs.  For example, when clicking ok on a completed appointment
        //	Recalls.SynchScheduledApptLazy(AptCur.PatNum,AptCur.AptDateTime,procCodes); //moved to closing event
        //}
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formApptEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (DialogResult != DialogResult.OK)
        {
            if (IsNew)
            {
                SecurityLogs.makeLogEntry(Permissions.AppointmentEdit,pat.PatNum,"Create cancel for date/time: " + AptCur.AptDateTime.ToString(),AptCur.AptNum);
                Appointments.delete(AptCur.AptNum);
            }
             
        }
         
        Recalls.synchScheduledApptFull(AptCur.PatNum);
    }

}


