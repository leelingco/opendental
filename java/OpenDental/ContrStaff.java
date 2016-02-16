//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:19 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.__MultiPatientSelectedEventHandler;
import OpenDental.ContrStaff;
import OpenDental.DataValid;
import OpenDental.DateTimeOD;
import OpenDental.FormAccounting;
import OpenDental.FormBackup;
import OpenDental.FormBilling;
import OpenDental.FormBillingClinic;
import OpenDental.FormBillingOptions;
import OpenDental.FormClaimPayList;
import OpenDental.FormClaimsSend;
import OpenDental.FormDeposits;
import OpenDental.FormEmailInbox;
import OpenDental.FormSupplyInventory;
import OpenDental.FormTasks;
import OpenDental.FormTimeCard;
import OpenDental.FormTimeCardManage;
import OpenDental.GotoModule;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PayPeriods;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.Phones;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.SigElement;
import OpenDentBusiness.SigElementDef;
import OpenDentBusiness.SigElementDefs;
import OpenDentBusiness.SigElements;
import OpenDentBusiness.SignalElementType;
import OpenDentBusiness.Signalod;
import OpenDentBusiness.Signalods;
import OpenDentBusiness.SignalType;
import OpenDentBusiness.Statements;
import OpenDentBusiness.TimeClockStatus;

/**
* 
*/
public class ContrStaff  extends System.Windows.Forms.UserControl 
{
    private OpenDental.UI.Button butTimeCard;
    private System.Windows.Forms.ListBox listStatus = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label textTime = new System.Windows.Forms.Label();
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    private OpenDental.UI.Button butClockIn;
    private OpenDental.UI.Button butClockOut;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.TextBox textMessage = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private OpenDental.UI.Button butSendClaims;
    private OpenDental.UI.Button butTasks;
    private OpenDental.UI.Button butBackup;
    private OpenDental.UI.ODGrid gridEmp;
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butDeposit;
    private OpenDental.UI.Button butBreaks;
    private OpenDental.UI.Button butBilling;
    private OpenDental.UI.Button butAccounting;
    private Label label7 = new Label();
    private ListBox listMessages = new ListBox();
    private Label label5 = new Label();
    private ListBox listExtras = new ListBox();
    private Label label4 = new Label();
    private ListBox listFrom = new ListBox();
    private Label label3 = new Label();
    private ListBox listTo = new ListBox();
    private Label label1 = new Label();
    private OpenDental.UI.ODGrid gridMessages;
    private CheckBox checkIncludeAck = new CheckBox();
    /**
    * Server time minus local computer time, usually +/- 1 or 2 minutes
    */
    private TimeSpan TimeDelta = new TimeSpan();
    private OpenDental.UI.Button butSend;
    private Label label6 = new Label();
    private ComboBox comboViewUser = new ComboBox();
    private Label labelDays = new Label();
    private TextBox textDays = new TextBox();
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    /**
    * Collection of Signals
    */
    private List<Signalod> SignalList = new List<Signalod>();
    private SigElementDef[] sigElementDefUser = new SigElementDef[]();
    private SigElementDef[] sigElementDefExtras = new SigElementDef[]();
    private Label labelSending = new Label();
    private Timer timerSending = new Timer();
    private ErrorProvider errorProvider1 = new ErrorProvider();
    private OpenDental.UI.Button butAck;
    private SigElementDef[] sigElementDefMessages = new SigElementDef[]();
    private OpenDental.UI.Button butSupply;
    private Employee EmployeeCur;
    private FormBilling FormB;
    private OpenDental.UI.Button butClaimPay;
    private OpenDental.UI.Button butManage;
    private long PatCurNum = new long();
    private OpenDental.UI.Button butEmailInbox;
    //private bool InitializedOnStartup;
    /**
    * This is public so that FormOpenDental can access it.
    */
    public FormTasks FormT;
    /**
    * 
    */
    public ContrStaff() throws Exception {
        Logger.openlog.log("Initializing management module...",Severity.INFO);
        initializeComponent();
        this.listStatus.Click += new System.EventHandler(this.listStatus_Click);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ContrStaff.class);
        this.listStatus = new System.Windows.Forms.ListBox();
        this.textTime = new System.Windows.Forms.Label();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butManage = new OpenDental.UI.Button();
        this.butBreaks = new OpenDental.UI.Button();
        this.gridEmp = new OpenDental.UI.ODGrid();
        this.label2 = new System.Windows.Forms.Label();
        this.butClockOut = new OpenDental.UI.Button();
        this.butTimeCard = new OpenDental.UI.Button();
        this.butClockIn = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.listMessages = new System.Windows.Forms.ListBox();
        this.butSend = new OpenDental.UI.Button();
        this.butAck = new OpenDental.UI.Button();
        this.labelSending = new System.Windows.Forms.Label();
        this.textDays = new System.Windows.Forms.TextBox();
        this.labelDays = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.comboViewUser = new System.Windows.Forms.ComboBox();
        this.gridMessages = new OpenDental.UI.ODGrid();
        this.checkIncludeAck = new System.Windows.Forms.CheckBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.listExtras = new System.Windows.Forms.ListBox();
        this.label4 = new System.Windows.Forms.Label();
        this.listFrom = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.listTo = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textMessage = new System.Windows.Forms.TextBox();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.butEmailInbox = new OpenDental.UI.Button();
        this.butSupply = new OpenDental.UI.Button();
        this.butClaimPay = new OpenDental.UI.Button();
        this.butBilling = new OpenDental.UI.Button();
        this.butAccounting = new OpenDental.UI.Button();
        this.butBackup = new OpenDental.UI.Button();
        this.butDeposit = new OpenDental.UI.Button();
        this.butSendClaims = new OpenDental.UI.Button();
        this.butTasks = new OpenDental.UI.Button();
        this.timerSending = new System.Windows.Forms.Timer(this.components);
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // listStatus
        //
        this.listStatus.Location = new System.Drawing.Point(367, 192);
        this.listStatus.Name = "listStatus";
        this.listStatus.Size = new System.Drawing.Size(107, 43);
        this.listStatus.TabIndex = 12;
        //
        // textTime
        //
        this.textTime.Font = new System.Drawing.Font("Microsoft Sans Serif", 13F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textTime.Location = new System.Drawing.Point(365, 113);
        this.textTime.Name = "textTime";
        this.textTime.Size = new System.Drawing.Size(109, 21);
        this.textTime.TabIndex = 17;
        this.textTime.Text = "12:00:00 PM";
        this.textTime.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // timer1
        //
        this.timer1.Enabled = true;
        this.timer1.Interval = 1000;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butManage);
        this.groupBox1.Controls.Add(this.butBreaks);
        this.groupBox1.Controls.Add(this.gridEmp);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.listStatus);
        this.groupBox1.Controls.Add(this.butClockOut);
        this.groupBox1.Controls.Add(this.butTimeCard);
        this.groupBox1.Controls.Add(this.textTime);
        this.groupBox1.Controls.Add(this.butClockIn);
        this.groupBox1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox1.Location = new System.Drawing.Point(349, 5);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(510, 247);
        this.groupBox1.TabIndex = 18;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Time Clock";
        //
        // butManage
        //
        this.butManage.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butManage.setAutosize(true);
        this.butManage.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butManage.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butManage.setCornerRadius(4F);
        this.butManage.Location = new System.Drawing.Point(366, 13);
        this.butManage.Name = "butManage";
        this.butManage.Size = new System.Drawing.Size(108, 25);
        this.butManage.TabIndex = 23;
        this.butManage.Text = "Manage";
        this.butManage.Click += new System.EventHandler(this.butManage_Click);
        //
        // butBreaks
        //
        this.butBreaks.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBreaks.setAutosize(true);
        this.butBreaks.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBreaks.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBreaks.setCornerRadius(4F);
        this.butBreaks.Location = new System.Drawing.Point(366, 67);
        this.butBreaks.Name = "butBreaks";
        this.butBreaks.Size = new System.Drawing.Size(108, 25);
        this.butBreaks.TabIndex = 22;
        this.butBreaks.Text = "View Breaks";
        this.butBreaks.Click += new System.EventHandler(this.butBreaks_Click);
        //
        // gridEmp
        //
        this.gridEmp.setAllowSelection(false);
        this.gridEmp.setHScrollVisible(false);
        this.gridEmp.Location = new System.Drawing.Point(22, 22);
        this.gridEmp.Name = "gridEmp";
        this.gridEmp.setScrollValue(0);
        this.gridEmp.Size = new System.Drawing.Size(303, 213);
        this.gridEmp.TabIndex = 21;
        this.gridEmp.setTitle("Employee");
        this.gridEmp.setTranslationName("TableEmpClock");
        this.gridEmp.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridEmp.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEmp_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridEmp.CellClick = __MultiODGridClickEventHandler.combine(this.gridEmp.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridEmp_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(376, 94);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(88, 19);
        this.label2.TabIndex = 20;
        this.label2.Text = "Server Time";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // butClockOut
        //
        this.butClockOut.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClockOut.setAutosize(true);
        this.butClockOut.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClockOut.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClockOut.setCornerRadius(4F);
        this.butClockOut.Location = new System.Drawing.Point(366, 164);
        this.butClockOut.Name = "butClockOut";
        this.butClockOut.Size = new System.Drawing.Size(108, 25);
        this.butClockOut.TabIndex = 14;
        this.butClockOut.Text = "Clock Out For:";
        this.butClockOut.Click += new System.EventHandler(this.butClockOut_Click);
        //
        // butTimeCard
        //
        this.butTimeCard.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTimeCard.setAutosize(true);
        this.butTimeCard.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTimeCard.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTimeCard.setCornerRadius(4F);
        this.butTimeCard.Location = new System.Drawing.Point(366, 40);
        this.butTimeCard.Name = "butTimeCard";
        this.butTimeCard.Size = new System.Drawing.Size(108, 25);
        this.butTimeCard.TabIndex = 16;
        this.butTimeCard.Text = "View Time Card";
        this.butTimeCard.Click += new System.EventHandler(this.butTimeCard_Click);
        //
        // butClockIn
        //
        this.butClockIn.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClockIn.setAutosize(true);
        this.butClockIn.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClockIn.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClockIn.setCornerRadius(4F);
        this.butClockIn.Location = new System.Drawing.Point(366, 137);
        this.butClockIn.Name = "butClockIn";
        this.butClockIn.Size = new System.Drawing.Size(108, 25);
        this.butClockIn.TabIndex = 11;
        this.butClockIn.Text = "Clock In";
        this.butClockIn.Click += new System.EventHandler(this.butClockIn_Click);
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox2.Controls.Add(this.listMessages);
        this.groupBox2.Controls.Add(this.butSend);
        this.groupBox2.Controls.Add(this.butAck);
        this.groupBox2.Controls.Add(this.labelSending);
        this.groupBox2.Controls.Add(this.textDays);
        this.groupBox2.Controls.Add(this.labelDays);
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.comboViewUser);
        this.groupBox2.Controls.Add(this.gridMessages);
        this.groupBox2.Controls.Add(this.checkIncludeAck);
        this.groupBox2.Controls.Add(this.label7);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.listExtras);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.listFrom);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.listTo);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.textMessage);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(3, 252);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(902, 447);
        this.groupBox2.TabIndex = 19;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Messaging";
        //
        // listMessages
        //
        this.listMessages.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listMessages.FormattingEnabled = true;
        this.listMessages.Location = new System.Drawing.Point(252, 35);
        this.listMessages.Name = "listMessages";
        this.listMessages.Size = new System.Drawing.Size(98, 355);
        this.listMessages.TabIndex = 10;
        this.listMessages.Click += new System.EventHandler(this.listMessages_Click);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Location = new System.Drawing.Point(252, 417);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(98, 25);
        this.butSend.TabIndex = 15;
        this.butSend.Text = "Send Text";
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
        //
        // butAck
        //
        this.butAck.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAck.setAutosize(true);
        this.butAck.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAck.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAck.setCornerRadius(4F);
        this.butAck.Location = new System.Drawing.Point(645, 10);
        this.butAck.Name = "butAck";
        this.butAck.Size = new System.Drawing.Size(67, 22);
        this.butAck.TabIndex = 25;
        this.butAck.Text = "Ack";
        this.butAck.Click += new System.EventHandler(this.butAck_Click);
        //
        // labelSending
        //
        this.labelSending.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelSending.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.5F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelSending.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
        this.labelSending.Location = new System.Drawing.Point(251, 393);
        this.labelSending.Name = "labelSending";
        this.labelSending.Size = new System.Drawing.Size(100, 21);
        this.labelSending.TabIndex = 24;
        this.labelSending.Text = "Sending";
        this.labelSending.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        this.labelSending.Visible = false;
        //
        // textDays
        //
        this.textDays.Location = new System.Drawing.Point(594, 12);
        this.textDays.Name = "textDays";
        this.textDays.Size = new System.Drawing.Size(45, 20);
        this.textDays.TabIndex = 19;
        this.textDays.Visible = false;
        this.textDays.TextChanged += new System.EventHandler(this.textDays_TextChanged);
        //
        // labelDays
        //
        this.labelDays.Location = new System.Drawing.Point(531, 14);
        this.labelDays.Name = "labelDays";
        this.labelDays.Size = new System.Drawing.Size(61, 16);
        this.labelDays.TabIndex = 18;
        this.labelDays.Text = "Days";
        this.labelDays.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelDays.Visible = false;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(725, 14);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(57, 16);
        this.label6.TabIndex = 17;
        this.label6.Text = "To User";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboViewUser
        //
        this.comboViewUser.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboViewUser.FormattingEnabled = true;
        this.comboViewUser.Location = new System.Drawing.Point(783, 11);
        this.comboViewUser.Name = "comboViewUser";
        this.comboViewUser.Size = new System.Drawing.Size(114, 21);
        this.comboViewUser.TabIndex = 16;
        this.comboViewUser.SelectionChangeCommitted += new System.EventHandler(this.comboViewUser_SelectionChangeCommitted);
        //
        // gridMessages
        //
        this.gridMessages.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMessages.setHScrollVisible(false);
        this.gridMessages.Location = new System.Drawing.Point(356, 35);
        this.gridMessages.Name = "gridMessages";
        this.gridMessages.setScrollValue(0);
        this.gridMessages.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMessages.Size = new System.Drawing.Size(540, 406);
        this.gridMessages.TabIndex = 13;
        this.gridMessages.setTitle("Message History");
        this.gridMessages.setTranslationName("TableTextMessages");
        //
        // checkIncludeAck
        //
        this.checkIncludeAck.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludeAck.Location = new System.Drawing.Point(356, 16);
        this.checkIncludeAck.Name = "checkIncludeAck";
        this.checkIncludeAck.Size = new System.Drawing.Size(173, 18);
        this.checkIncludeAck.TabIndex = 14;
        this.checkIncludeAck.Text = "Include Acknowledged";
        this.checkIncludeAck.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIncludeAck.UseVisualStyleBackColor = true;
        this.checkIncludeAck.Click += new System.EventHandler(this.checkIncludeAck_Click);
        //
        // label7
        //
        this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label7.Location = new System.Drawing.Point(6, 402);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(100, 16);
        this.label7.TabIndex = 12;
        this.label7.Text = "Text Message";
        this.label7.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(250, 16);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 16);
        this.label5.TabIndex = 9;
        this.label5.Text = "Message (&& Send)";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listExtras
        //
        this.listExtras.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listExtras.FormattingEnabled = true;
        this.listExtras.Location = new System.Drawing.Point(171, 35);
        this.listExtras.Name = "listExtras";
        this.listExtras.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listExtras.Size = new System.Drawing.Size(75, 355);
        this.listExtras.TabIndex = 8;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(169, 16);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(78, 16);
        this.label4.TabIndex = 7;
        this.label4.Text = "Extras";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listFrom
        //
        this.listFrom.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listFrom.FormattingEnabled = true;
        this.listFrom.Location = new System.Drawing.Point(90, 35);
        this.listFrom.Name = "listFrom";
        this.listFrom.Size = new System.Drawing.Size(75, 355);
        this.listFrom.TabIndex = 6;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(88, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(78, 16);
        this.label3.TabIndex = 5;
        this.label3.Text = "From";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listTo
        //
        this.listTo.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.listTo.FormattingEnabled = true;
        this.listTo.Location = new System.Drawing.Point(9, 35);
        this.listTo.Name = "listTo";
        this.listTo.Size = new System.Drawing.Size(75, 355);
        this.listTo.TabIndex = 4;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(7, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(78, 16);
        this.label1.TabIndex = 3;
        this.label1.Text = "To";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textMessage
        //
        this.textMessage.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textMessage.Location = new System.Drawing.Point(9, 421);
        this.textMessage.Name = "textMessage";
        this.textMessage.Size = new System.Drawing.Size(237, 20);
        this.textMessage.TabIndex = 1;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.butEmailInbox);
        this.groupBox3.Controls.Add(this.butSupply);
        this.groupBox3.Controls.Add(this.butClaimPay);
        this.groupBox3.Controls.Add(this.butBilling);
        this.groupBox3.Controls.Add(this.butAccounting);
        this.groupBox3.Controls.Add(this.butBackup);
        this.groupBox3.Controls.Add(this.butDeposit);
        this.groupBox3.Controls.Add(this.butSendClaims);
        this.groupBox3.Controls.Add(this.butTasks);
        this.groupBox3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox3.Location = new System.Drawing.Point(34, 5);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(286, 166);
        this.groupBox3.TabIndex = 23;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Daily";
        //
        // butEmailInbox
        //
        this.butEmailInbox.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEmailInbox.setAutosize(true);
        this.butEmailInbox.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEmailInbox.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEmailInbox.setCornerRadius(4F);
        this.butEmailInbox.Image = Resources.getemail1();
        this.butEmailInbox.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butEmailInbox.Location = new System.Drawing.Point(148, 97);
        this.butEmailInbox.Name = "butEmailInbox";
        this.butEmailInbox.Size = new System.Drawing.Size(104, 26);
        this.butEmailInbox.TabIndex = 28;
        this.butEmailInbox.Text = "Email Inbox";
        this.butEmailInbox.Click += new System.EventHandler(this.butEmailInbox_Click);
        //
        // butSupply
        //
        this.butSupply.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSupply.setAutosize(true);
        this.butSupply.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSupply.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSupply.setCornerRadius(4F);
        this.butSupply.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSupply.Location = new System.Drawing.Point(16, 123);
        this.butSupply.Name = "butSupply";
        this.butSupply.Size = new System.Drawing.Size(104, 26);
        this.butSupply.TabIndex = 26;
        this.butSupply.Text = "SupplyInventory";
        this.butSupply.Click += new System.EventHandler(this.butSupply_Click);
        //
        // butClaimPay
        //
        this.butClaimPay.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClaimPay.setAutosize(true);
        this.butClaimPay.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClaimPay.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClaimPay.setCornerRadius(4F);
        this.butClaimPay.Location = new System.Drawing.Point(16, 45);
        this.butClaimPay.Name = "butClaimPay";
        this.butClaimPay.Size = new System.Drawing.Size(104, 26);
        this.butClaimPay.TabIndex = 25;
        this.butClaimPay.Text = "Batch Ins";
        this.butClaimPay.Click += new System.EventHandler(this.butClaimPay_Click);
        //
        // butBilling
        //
        this.butBilling.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBilling.setAutosize(true);
        this.butBilling.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBilling.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBilling.setCornerRadius(4F);
        this.butBilling.Location = new System.Drawing.Point(16, 71);
        this.butBilling.Name = "butBilling";
        this.butBilling.Size = new System.Drawing.Size(104, 26);
        this.butBilling.TabIndex = 25;
        this.butBilling.Text = "Billing";
        this.butBilling.Click += new System.EventHandler(this.butBilling_Click);
        //
        // butAccounting
        //
        this.butAccounting.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAccounting.setAutosize(true);
        this.butAccounting.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAccounting.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAccounting.setCornerRadius(4F);
        this.butAccounting.Image = ((System.Drawing.Image)(resources.GetObject("butAccounting.Image")));
        this.butAccounting.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAccounting.Location = new System.Drawing.Point(148, 71);
        this.butAccounting.Name = "butAccounting";
        this.butAccounting.Size = new System.Drawing.Size(104, 26);
        this.butAccounting.TabIndex = 24;
        this.butAccounting.Text = "Accounting";
        this.butAccounting.Click += new System.EventHandler(this.butAccounting_Click);
        //
        // butBackup
        //
        this.butBackup.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBackup.setAutosize(true);
        this.butBackup.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBackup.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBackup.setCornerRadius(4F);
        this.butBackup.Image = ((System.Drawing.Image)(resources.GetObject("butBackup.Image")));
        this.butBackup.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butBackup.Location = new System.Drawing.Point(148, 45);
        this.butBackup.Name = "butBackup";
        this.butBackup.Size = new System.Drawing.Size(104, 26);
        this.butBackup.TabIndex = 22;
        this.butBackup.Text = "Backup";
        this.butBackup.Click += new System.EventHandler(this.butBackup_Click);
        //
        // butDeposit
        //
        this.butDeposit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeposit.setAutosize(true);
        this.butDeposit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeposit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeposit.setCornerRadius(4F);
        this.butDeposit.Image = ((System.Drawing.Image)(resources.GetObject("butDeposit.Image")));
        this.butDeposit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeposit.Location = new System.Drawing.Point(16, 97);
        this.butDeposit.Name = "butDeposit";
        this.butDeposit.Size = new System.Drawing.Size(104, 26);
        this.butDeposit.TabIndex = 23;
        this.butDeposit.Text = "Deposits";
        this.butDeposit.Click += new System.EventHandler(this.butDeposit_Click);
        //
        // butSendClaims
        //
        this.butSendClaims.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSendClaims.setAutosize(true);
        this.butSendClaims.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSendClaims.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSendClaims.setCornerRadius(4F);
        this.butSendClaims.Image = ((System.Drawing.Image)(resources.GetObject("butSendClaims.Image")));
        this.butSendClaims.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSendClaims.Location = new System.Drawing.Point(16, 19);
        this.butSendClaims.Name = "butSendClaims";
        this.butSendClaims.Size = new System.Drawing.Size(104, 26);
        this.butSendClaims.TabIndex = 20;
        this.butSendClaims.Text = "Send Claims";
        this.butSendClaims.Click += new System.EventHandler(this.butSendClaims_Click);
        //
        // butTasks
        //
        this.butTasks.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butTasks.setAutosize(true);
        this.butTasks.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTasks.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTasks.setCornerRadius(4F);
        this.butTasks.Image = ((System.Drawing.Image)(resources.GetObject("butTasks.Image")));
        this.butTasks.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butTasks.Location = new System.Drawing.Point(148, 19);
        this.butTasks.Name = "butTasks";
        this.butTasks.Size = new System.Drawing.Size(104, 26);
        this.butTasks.TabIndex = 21;
        this.butTasks.Text = "Tasks";
        this.butTasks.Click += new System.EventHandler(this.butTasks_Click);
        //
        // timerSending
        //
        this.timerSending.Interval = 1000;
        this.timerSending.Tick += new System.EventHandler(this.timerSending_Tick);
        //
        // ContrStaff
        //
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Name = "ContrStaff";
        this.Size = new System.Drawing.Size(908, 702);
        this.Load += new System.EventHandler(this.ContrStaff_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void contrStaff_Load(Object sender, System.EventArgs e) throws Exception {
    }

    /**
    * Only gets run on startup.
    */
    public void initializeOnStartup() throws Exception {
        //if(InitializedOnStartup) {
        //	return;
        //}
        //InitializedOnStartup=true;
        //can't use Lan.F
        Lan.C(this, new Control[]{ groupBox2, label1, butSend, groupBox1, butTimeCard, label2, butClockIn, butClockOut, butSendClaims, butBilling, butDeposit, butSupply, butTasks, butBackup, butAccounting, butBreaks, label3, label4, label5, label7, labelSending, checkIncludeAck, labelDays, butAck, label6 });
        refreshFullMessages();
    }

    //after this, messages just get added to the list.
    //But if checkIncludeAck is clicked,then it does RefreshMessages again.
    /**
    * 
    */
    public void moduleSelected(long patNum) throws Exception {
        PatCurNum = patNum;
        refreshModuleData(patNum);
        refreshModuleScreen();
        Plugins.hookAddCode(this,"ContrStaff.ModuleSelected_end",patNum);
    }

    /**
    * 
    */
    public void moduleUnselected() throws Exception {
        //this is not getting triggered yet.
        Plugins.hookAddCode(this,"ContrStaff.ModuleUnselected_end");
    }

    private void refreshModuleData(long patNum) throws Exception {
        TimeDelta = MiscData.getNowDateTime() - DateTime.Now;
        Employees.refreshCache();
    }

    //RefreshModulePatient(patNum);
    private void refreshModuleScreen() throws Exception {
        textTime.Text = (DateTime.Now + TimeDelta).ToLongTimeString();
        fillEmps();
        fillMessageDefs();
        if (Security.isAuthorized(Permissions.TimecardsEditAll,true))
        {
            butManage.Enabled = true;
        }
        else
        {
            butManage.Enabled = false;
        } 
    }

    /*
    		///<summary>Here so it's parallel with other modules.</summary>
    		private void RefreshModulePatient(int patNum){
    			PatCurNum=patNum;
    			if(patNum==0){
    				OnPatientSelected(patNum,"",false,"");
    			}
    			else{
    				Patient pat=Patients.GetPat(patNum);
    				OnPatientSelected(patNum,pat.GetNameLF(),pat.Email!="",pat.ChartNumber);
    			}
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

    private void butSendClaims_Click(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        FormClaimsSend FormCS = new FormClaimsSend();
        FormCS.ShowDialog();
        if (FormCS.GotoPatNum != 0 && FormCS.GotoClaimNum != 0)
        {
            Patient pat = Patients.getPat(FormCS.GotoPatNum);
            onPatientSelected(pat);
            GotoModule.gotoClaim(FormCS.GotoClaimNum);
        }
         
        Cursor = Cursors.Default;
    }

    private void butClaimPay_Click(Object sender, EventArgs e) throws Exception {
        FormClaimPayList FormCPL = new FormClaimPayList();
        FormCPL.ShowDialog();
        if (FormCPL.GotoPatNum != 0 && FormCPL.GotoClaimNum != 0)
        {
            Patient pat = Patients.getPat(FormCPL.GotoPatNum);
            onPatientSelected(pat);
            GotoModule.gotoClaim(FormCPL.GotoClaimNum);
        }
         
    }

    private void butBilling_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Billing))
        {
            return ;
        }
         
        boolean unsentStatementsExist = Statements.unsentStatementsExist();
        if (unsentStatementsExist)
        {
            if (!PrefC.getBool(PrefName.EasyNoClinics))
            {
                //Using clinics.
                //Have user choose which clinic they want to see billing for.
                FormBillingClinic FormBC = new FormBillingClinic();
                FormBC.ShowDialog();
                if (FormBC.DialogResult == DialogResult.OK)
                {
                    if (Statements.unsentClinicStatementsExist(FormBC.ClinicNum))
                    {
                        //Check if clinic has unsent bills.
                        showBilling(FormBC.ClinicNum);
                    }
                    else
                    {
                        //Clinic has unsent bills.  Simply show billing window.
                        //No unsent bills for clinic.  Show billing options to generate a billing list.
                        showBillingOptions(FormBC.ClinicNum);
                    } 
                }
                 
            }
            else
            {
                //Not using clinics and has unsent bills.  Simply show billing window.
                ShowBilling(0);
            } 
        }
        else
        {
            //No unsent statements exist.  Have user create a billing list.
            ShowBillingOptions(0);
        } 
        SecurityLogs.MakeLogEntry(Permissions.Billing, 0, "");
    }

    /**
    * Shows FormBilling and displays warning message if needed.  Pass 0 to show all clinics.
    */
    private void showBilling(long clinicNum) throws Exception {
        boolean isFirstShow = false;
        if (FormB == null || FormB.IsDisposed)
        {
            isFirstShow = true;
            FormB = new FormBilling();
            FormB.GoToChanged = __MultiPatientSelectedEventHandler.combine(FormB.GoToChanged,new PatientSelectedEventHandler() 
              { 
                public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                    formBilling_GoToChanged(sender, e);
                }

                public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                    List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                    ret.add(this);
                    return ret;
                }
            
              });
            FormB.ClinicNum = clinicNum;
        }
         
        FormB.Show();
        FormB.BringToFront();
        if (isFirstShow)
        {
            MsgBox.show(this,"These unsent bills must either be sent or deleted before a new list can be created.");
        }
         
    }

    /**
    * Shows FormBillingOptions and FormBilling if needed.  Pass 0 to show all clinics.
    */
    private void showBillingOptions(long clinicNum) throws Exception {
        FormBillingOptions FormBO = new FormBillingOptions();
        FormBO.ClinicNum = clinicNum;
        FormBO.ShowDialog();
        if (FormBO.DialogResult == DialogResult.OK)
        {
            FormB = new FormBilling();
            FormB.GoToChanged = __MultiPatientSelectedEventHandler.combine(FormB.GoToChanged,new PatientSelectedEventHandler() 
              { 
                public System.Void invoke(System.Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
                    formBilling_GoToChanged(sender, e);
                }

                public List<PatientSelectedEventHandler> getInvocationList() throws Exception {
                    List<PatientSelectedEventHandler> ret = new ArrayList<PatientSelectedEventHandler>();
                    ret.add(this);
                    return ret;
                }
            
              });
            FormB.ClinicNum = FormBO.ClinicNum;
            FormB.Show();
        }
         
    }

    private void formBilling_GoToChanged(Object sender, OpenDental.PatientSelectedEventArgs e) throws Exception {
        onPatientSelected(e.getPat());
        GotoModule.GotoAccount(0);
    }

    private void butDeposit_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.IsAuthorized(Permissions.DepositSlips, DateTime.Today))
        {
            return ;
        }
         
        FormDeposits FormD = new FormDeposits();
        FormD.ShowDialog();
    }

    private void butAccounting_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Accounting))
        {
            return ;
        }
         
        FormAccounting FormA = new FormAccounting();
        FormA.Show();
    }

    private void butBackup_Click(Object sender, System.EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.Backup))
        {
            return ;
        }
         
        FormBackup FormB = new FormBackup();
        FormB.ShowDialog();
        if (FormB.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        //ok signifies that a database was restored
        onPatientSelected(null);
        //ParentForm.Text=PrefC.GetString(PrefName.MainWindowTitle");
        DataValid.setInvalid(true);
        moduleSelected(PatCurNum);
    }

    private void butTasks_Click(Object sender, System.EventArgs e) throws Exception {
        launchTaskWindow(false);
    }

    /*  //This is the old code exactly how it was before making the task window non-modal in case issues arise.
    			FormTasks FormT=new FormTasks();
    			FormT.ShowDialog();
    			if(FormT.GotoType==TaskObjectType.Patient){
    				if(FormT.GotoKeyNum!=0){
    					Patient pat=Patients.GetPat(FormT.GotoKeyNum);
    					OnPatientSelected(pat);
    					GotoModule.GotoAccount(0);
    				}
    			}
    			if(FormT.GotoType==TaskObjectType.Appointment){
    				if(FormT.GotoKeyNum!=0){
    					Appointment apt=Appointments.GetOneApt(FormT.GotoKeyNum);
    					if(apt==null){
    						MsgBox.Show(this,"Appointment has been deleted, so it's not available.");
    						return;
    						//this could be a little better, because window has closed, but they will learn not to push that button.
    					}
    					DateTime dateSelected=DateTime.MinValue;
    					if(apt.AptStatus==ApptStatus.Planned || apt.AptStatus==ApptStatus.UnschedList){
    						//I did not add feature to put planned or unsched apt on pinboard.
    						MsgBox.Show(this,"Cannot navigate to appointment.  Use the Other Appointments button.");
    						//return;
    					}
    					else{
    						dateSelected=apt.AptDateTime;
    					}
    					Patient pat=Patients.GetPat(apt.PatNum);
    					OnPatientSelected(pat);
    					GotoModule.GotoAppointment(dateSelected,apt.AptNum);
    				}
    			}
    			*/
    /**
    * Only used internally to launch the task window with the Triage task list.
    */
    public void jumpToTriageTaskWindow() throws Exception {
        launchTaskWindow(true);
    }

    /**
    * Used to launch the task window preloaded with a certain task list open.  IsTriage is only used at OD HQ.
    */
    private void launchTaskWindow(boolean isTriage) throws Exception {
        if (FormT == null || FormT.IsDisposed)
        {
            FormT = new FormTasks();
        }
         
        FormT.Show();
        if (isTriage)
        {
            FormT.showTriage();
        }
         
        if (FormT.WindowState == FormWindowState.Minimized)
        {
            FormT.WindowState = FormWindowState.Normal;
        }
         
        FormT.BringToFront();
    }

    private void butSupply_Click(Object sender, EventArgs e) throws Exception {
        FormSupplyInventory FormS = new FormSupplyInventory();
        FormS.ShowDialog();
    }

    private void butEmailInbox_Click(Object sender, EventArgs e) throws Exception {
        FormEmailInbox formEI = new FormEmailInbox();
        formEI.ShowDialog();
    }

    //private void butClear_Click(object sender, System.EventArgs e) {
    //textMessage.Clear();
    //textMessage.Select();
    //}
    private void fillEmps() throws Exception {
        gridEmp.beginUpdate();
        gridEmp.getColumns().Clear();
        OpenDental.UI.ODGridColumn col = new OpenDental.UI.ODGridColumn(Lan.g("TableEmpClock","Employee"),180);
        gridEmp.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableEmpClock","Status"),104);
        gridEmp.getColumns().add(col);
        gridEmp.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < Employees.getListShort().Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Employees.GetNameFL(Employees.getListShort()[i]));
            row.getCells().Add(Employees.getListShort()[i].ClockStatus);
            gridEmp.getRows().add(row);
        }
        gridEmp.endUpdate();
        listStatus.Items.Clear();
        for (int i = 0;i < Enum.GetNames(TimeClockStatus.class).Length;i++)
        {
            listStatus.Items.Add(Lan.g("enumTimeClockStatus", Enum.GetNames(TimeClockStatus.class)[i]));
        }
        for (int i = 0;i < Employees.getListShort().Length;i++)
        {
            if (Employees.getListShort()[i].EmployeeNum == Security.getCurUser().EmployeeNum)
            {
                selectEmpI(i);
                return ;
            }
             
        }
        selectEmpI(-1);
    }

    /**
    * -1 is also valid.
    */
    private void selectEmpI(int index) throws Exception {
        gridEmp.setSelected(false);
        if (index == -1)
        {
            butClockIn.Enabled = false;
            butClockOut.Enabled = false;
            butTimeCard.Enabled = false;
            butBreaks.Enabled = false;
            listStatus.Enabled = false;
            return ;
        }
         
        gridEmp.setSelected(index,true);
        EmployeeCur = Employees.getListShort()[index];
        ClockEvent clockEvent = ClockEvents.getLastEvent(EmployeeCur.EmployeeNum);
        if (clockEvent == null)
        {
            //new employee.  They need to clock in.
            butClockIn.Enabled = true;
            butClockOut.Enabled = false;
            butTimeCard.Enabled = true;
            butBreaks.Enabled = true;
            listStatus.SelectedIndex = ((Enum)TimeClockStatus.Home).ordinal();
            listStatus.Enabled = false;
        }
        else if (clockEvent.ClockStatus == TimeClockStatus.Break)
        {
            //only incomplete breaks will have been returned.
            //clocked out for break, but not clocked back in
            butClockIn.Enabled = true;
            butClockOut.Enabled = false;
            butTimeCard.Enabled = true;
            butBreaks.Enabled = true;
            listStatus.SelectedIndex = ((Enum)TimeClockStatus.Break).ordinal();
            listStatus.Enabled = false;
        }
        else
        {
            //normal clock in/out
            if (clockEvent.TimeDisplayed2.Year < 1880)
            {
                //clocked in to work, but not clocked back out.
                butClockIn.Enabled = false;
                butClockOut.Enabled = true;
                butTimeCard.Enabled = true;
                butBreaks.Enabled = true;
                listStatus.Enabled = true;
            }
            else
            {
                //clocked out for home or lunch.  Need to clock back in.
                butClockIn.Enabled = true;
                butClockOut.Enabled = false;
                butTimeCard.Enabled = true;
                butBreaks.Enabled = true;
                listStatus.SelectedIndex = ((Enum)clockEvent.ClockStatus).ordinal();
                listStatus.Enabled = false;
            } 
        }  
    }

    private void gridEmp_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.TimecardSecurityEnabled))
        {
            if (Security.getCurUser().EmployeeNum != Employees.getListShort()[e.getRow()].EmployeeNum)
            {
                if (!Security.isAuthorized(Permissions.TimecardsEditAll))
                {
                    selectEmpI(-1);
                    return ;
                }
                 
            }
             
        }
         
        selectEmpI(e.getRow());
    }

    private void listStatus_Click(Object sender, System.EventArgs e) throws Exception {
    }

    //
    private void butClockIn_Click(Object sender, System.EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.DockPhonePanelShow) && !Security.isAuthorized(Permissions.TimecardsEditAll,true))
        {
            //Check if the employee set their ext to 0 in the phoneempdefault table.
            if (PhoneEmpDefaults.getByExtAndEmp(0,EmployeeCur.EmployeeNum) == null)
            {
                MessageBox.Show("Not allowed.  Use the small phone panel or the \"Big\" phone window to clock in.\r\nIf you are trying to clock in as a \"floater\", you need to set your extension to 0 first before using this Clock In button.");
                return ;
            }
             
        }
         
        try
        {
            ClockEvents.clockIn(EmployeeCur.EmployeeNum);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        EmployeeCur.ClockStatus = Lan.g(this,"Working");
        Employees.update(EmployeeCur);
        moduleSelected(PatCurNum);
        if (PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            Phones.setPhoneStatus(ClockStatusEnum.Available,Phones.getExtensionForEmp(EmployeeCur.EmployeeNum),EmployeeCur.EmployeeNum);
        }
         
    }

    private void butClockOut_Click(Object sender, System.EventArgs e) throws Exception {
        if (PrefC.getBool(PrefName.DockPhonePanelShow) && !Security.isAuthorized(Permissions.TimecardsEditAll,true))
        {
            //Check if the employee set their ext to 0 in the phoneempdefault table.
            if (PhoneEmpDefaults.getByExtAndEmp(0,EmployeeCur.EmployeeNum) == null)
            {
                MessageBox.Show("Not allowed.  Use the small phone panel or the \"Big\" phone window to clock out.\r\nIf you are trying to clock out as a \"floater\", you need to set your extension to 0 first before using this Clock Out For: button.");
                return ;
            }
             
        }
         
        if (listStatus.SelectedIndex == -1)
        {
            MsgBox.show(this,"Please select a status first.");
            return ;
        }
         
        try
        {
            ClockEvents.clockOut(EmployeeCur.EmployeeNum,(TimeClockStatus)listStatus.SelectedIndex);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        EmployeeCur.ClockStatus = Lan.g("enumTimeClockStatus", ((TimeClockStatus)listStatus.SelectedIndex).ToString());
        Employees.update(EmployeeCur);
        moduleSelected(PatCurNum);
        if (PrefC.getBool(PrefName.DockPhonePanelShow))
        {
            Phones.setPhoneStatus(Phones.getClockStatusFromEmp(EmployeeCur.ClockStatus),Phones.getExtensionForEmp(EmployeeCur.EmployeeNum),EmployeeCur.EmployeeNum);
        }
         
    }

    private void timer1_Tick(Object sender, System.EventArgs e) throws Exception {
        //this will happen once a second
        if (this.Visible)
        {
            textTime.Text = (DateTime.Now + TimeDelta).ToLongTimeString();
        }
         
    }

    private void butManage_Click(Object sender, EventArgs e) throws Exception {
        FormTimeCardManage FormTCM = new FormTimeCardManage();
        FormTCM.ShowDialog();
    }

    private void gridEmp_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (PayPeriods.getList().Length == 0)
        {
            MsgBox.show(this,"The adminstrator needs to setup pay periods first.");
            return ;
        }
         
        if (!butTimeCard.Enabled)
        {
            return ;
        }
         
        FormTimeCard FormTC = new FormTimeCard();
        FormTC.EmployeeCur = Employees.getListShort()[e.getRow()];
        FormTC.ShowDialog();
        moduleSelected(PatCurNum);
    }

    private void butTimeCard_Click(Object sender, System.EventArgs e) throws Exception {
        if (PayPeriods.getList().Length == 0)
        {
            MsgBox.show(this,"The adminstrator needs to setup pay periods first.");
            return ;
        }
         
        FormTimeCard FormTC = new FormTimeCard();
        FormTC.EmployeeCur = EmployeeCur;
        FormTC.ShowDialog();
        moduleSelected(PatCurNum);
    }

    private void butBreaks_Click(Object sender, EventArgs e) throws Exception {
        if (PayPeriods.getList().Length == 0)
        {
            MsgBox.show(this,"The adminstrator needs to setup pay periods first.");
            return ;
        }
         
        FormTimeCard FormTC = new FormTimeCard();
        FormTC.EmployeeCur = EmployeeCur;
        FormTC.IsBreaks = true;
        FormTC.ShowDialog();
        moduleSelected(PatCurNum);
    }

    /**
    * Gets run with each module selected.  Should be very fast.
    */
    private void fillMessageDefs() throws Exception {
        sigElementDefUser = SigElementDefs.getSubList(SignalElementType.User);
        sigElementDefExtras = SigElementDefs.getSubList(SignalElementType.Extra);
        sigElementDefMessages = SigElementDefs.getSubList(SignalElementType.Message);
        listTo.Items.Clear();
        for (int i = 0;i < sigElementDefUser.Length;i++)
        {
            listTo.Items.Add(sigElementDefUser[i].SigText);
        }
        listFrom.Items.Clear();
        for (int i = 0;i < sigElementDefUser.Length;i++)
        {
            listFrom.Items.Add(sigElementDefUser[i].SigText);
        }
        listExtras.Items.Clear();
        for (int i = 0;i < sigElementDefExtras.Length;i++)
        {
            listExtras.Items.Add(sigElementDefExtras[i].SigText);
        }
        listMessages.Items.Clear();
        for (int i = 0;i < sigElementDefMessages.Length;i++)
        {
            listMessages.Items.Add(sigElementDefMessages[i].SigText);
        }
        comboViewUser.Items.Clear();
        comboViewUser.Items.Add(Lan.g(this,"all"));
        for (int i = 0;i < sigElementDefUser.Length;i++)
        {
            comboViewUser.Items.Add(sigElementDefUser[i].SigText);
        }
        comboViewUser.SelectedIndex = 0;
    }

    /**
    * Gets all new data from the database for the text messages.  Not sure yet if this will also reset the lights along the left.
    */
    private void refreshFullMessages() throws Exception {
        SignalList = Signalods.refreshFullText(DateTimeOD.getToday());
        //since midnight this morning.
        fillMessages();
    }

    /**
    * This does not refresh any data, just fills the grid.
    */
    private void fillMessages() throws Exception {
        if (textDays.Visible && !StringSupport.equals(errorProvider1.GetError(textDays), ""))
        {
            return ;
        }
         
        long[] selected = new long[gridMessages.getSelectedIndices().Length];
        for (int i = 0;i < selected.Length;i++)
        {
            selected[i] = SignalList[gridMessages.getSelectedIndices()[i]].SignalNum;
        }
        gridMessages.beginUpdate();
        gridMessages.getColumns().Clear();
        OpenDental.UI.ODGridColumn col = new OpenDental.UI.ODGridColumn(Lan.g("TableTextMessages","To"),60);
        gridMessages.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTextMessages","From"),60);
        gridMessages.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTextMessages","Sent"),63);
        gridMessages.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTextMessages","Ack'd"),63);
        col.setTextAlign(HorizontalAlignment.Center);
        gridMessages.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTextMessages","Text"),274);
        gridMessages.getColumns().add(col);
        gridMessages.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Signalod sig;
        String str = new String();
        for (int i = 0;i < SignalList.Count;i++)
        {
            sig = SignalList[i];
            if (checkIncludeAck.Checked)
            {
                //if this is acked
                if (sig.AckTime.Year > 1880 && sig.AckTime < DateTime.Today.AddDays(1 - PIn.Long(textDays.Text)))
                {
                    continue;
                }
                 
            }
            else
            {
                //user does not want to include acked
                if (sig.AckTime.Year > 1880)
                {
                    continue;
                }
                 
            } 
            //if this is acked
            //blank user always shows
            //anything other than 'all'
            //for startup
            if (!StringSupport.equals(sig.ToUser, "") && comboViewUser.SelectedIndex != 0 && sigElementDefUser != null && !StringSupport.equals(sigElementDefUser[comboViewUser.SelectedIndex - 1].SigText, sig.ToUser))
            {
                continue;
            }
             
            //and users don't match
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(sig.ToUser);
            row.getCells().add(sig.FromUser);
            if (sig.SigDateTime.Date == DateTime.Today)
            {
                row.getCells().Add(sig.SigDateTime.ToShortTimeString());
            }
            else
            {
                row.getCells().Add(sig.SigDateTime.ToShortDateString() + "\r\n" + sig.SigDateTime.ToShortTimeString());
            } 
            if (sig.AckTime.Year > 1880)
            {
                //ok
                if (sig.AckTime.Date == DateTime.Today)
                {
                    row.getCells().Add(sig.AckTime.ToShortTimeString());
                }
                else
                {
                    row.getCells().Add(sig.AckTime.ToShortDateString() + "\r\n" + sig.AckTime.ToShortTimeString());
                } 
            }
            else
            {
                row.getCells().add("");
            } 
            str = sig.SigText;
            for (int e = 0;e < sig.ElementList.Length;e++)
            {
                if (SigElementDefs.GetElement(sig.ElementList[e].SigElementDefNum).SigElementType == SignalElementType.User)
                {
                    continue;
                }
                 
                //we already have text copies of the users.
                if (!StringSupport.equals(str, ""))
                {
                    str += ".  ";
                }
                 
                str += SigElementDefs.GetElement(sig.ElementList[e].SigElementDefNum).SigText;
            }
            row.getCells().add(str);
            row.setTag(sig.copy());
            gridMessages.getRows().add(row);
            if (Array.IndexOf(selected, sig.SignalNum) != -1)
            {
                gridMessages.SetSelected(gridMessages.getRows().Count - 1, true);
            }
             
        }
        gridMessages.endUpdate();
    }

    private void butSend_Click(Object sender, System.EventArgs e) throws Exception {
        //if(listTo.SelectedIndex==-1){
        //	MsgBox.Show(this,"Please select who to send the message to.");
        //	return;
        //}
        if (StringSupport.equals(textMessage.Text, ""))
        {
            MsgBox.show(this,"Please type in a message first.");
            return ;
        }
         
        Signalod sig = new Signalod();
        sig.SigType = SignalType.Button;
        sig.SigText = textMessage.Text;
        if (listTo.SelectedIndex != -1)
        {
            sig.ToUser = sigElementDefUser[listTo.SelectedIndex].SigText;
        }
         
        if (listFrom.SelectedIndex != -1)
        {
            sig.FromUser = sigElementDefUser[listFrom.SelectedIndex].SigText;
        }
         
        Signalods.insert(sig);
        SigElement element;
        if (listTo.SelectedIndex != -1)
        {
            element = new SigElement();
            element.SigElementDefNum = sigElementDefUser[listTo.SelectedIndex].SigElementDefNum;
            element.SignalNum = sig.SignalNum;
            SigElements.insert(element);
        }
         
        textMessage.Text = "";
        listFrom.SelectedIndex = -1;
        listTo.SelectedIndex = -1;
        listExtras.SelectedIndex = -1;
        listMessages.SelectedIndex = -1;
        labelSending.Visible = true;
        timerSending.Enabled = true;
    }

    private void listMessages_Click(Object sender, EventArgs e) throws Exception {
        if (listMessages.SelectedIndex == -1)
        {
            return ;
        }
         
        Signalod sig = new Signalod();
        sig.SigType = SignalType.Button;
        sig.SigText = textMessage.Text;
        if (listTo.SelectedIndex != -1)
        {
            sig.ToUser = sigElementDefUser[listTo.SelectedIndex].SigText;
        }
         
        if (listFrom.SelectedIndex != -1)
        {
            sig.FromUser = sigElementDefUser[listFrom.SelectedIndex].SigText;
        }
         
        //need to do this all as a transaction, so need to do a writelock on the signal table first.
        //alternatively, we could just make sure not to retrieve any signals that were less the 300ms old.
        Signalods.insert(sig);
        SigElement element;
        if (listTo.SelectedIndex != -1)
        {
            element = new SigElement();
            element.SigElementDefNum = sigElementDefUser[listTo.SelectedIndex].SigElementDefNum;
            element.SignalNum = sig.SignalNum;
            SigElements.insert(element);
        }
         
        //We do not insert an element for From
        if (listExtras.SelectedIndex != -1)
        {
            element = new SigElement();
            element.SigElementDefNum = sigElementDefExtras[listExtras.SelectedIndex].SigElementDefNum;
            element.SignalNum = sig.SignalNum;
            SigElements.insert(element);
        }
         
        element = new SigElement();
        element.SigElementDefNum = sigElementDefMessages[listMessages.SelectedIndex].SigElementDefNum;
        element.SignalNum = sig.SignalNum;
        SigElements.insert(element);
        //reset the controls
        textMessage.Text = "";
        listFrom.SelectedIndex = -1;
        listTo.SelectedIndex = -1;
        listExtras.SelectedIndex = -1;
        listMessages.SelectedIndex = -1;
        labelSending.Visible = true;
        timerSending.Enabled = true;
    }

    /**
    * This processes timed messages coming in from the main form.  Buttons are handled in the main form, and then sent here for further display.  The list gets filtered before display.
    */
    public void logMsgs(List<Signalod> signalList) throws Exception {
        for (int i = 0;i < signalList.Count;i++)
        {
            if (signalList[i].AckTime.Year > 1880)
            {
                for (int s = 0;s < SignalList.Count;s++)
                {
                    //if ack
                    //then find the original
                    if (((Signalod)SignalList[s]).SignalNum == signalList[i].SignalNum)
                    {
                        //alter the original
                        ((Signalod)SignalList[s]).AckTime = signalList[i].AckTime;
                        break;
                    }
                     
                }
            }
            else
            {
                //out of s loop.
                SignalList.Add(signalList[i].Copy());
            } 
        }
        SignalList.Sort();
        fillMessages();
    }

    private void butAck_Click(Object sender, EventArgs e) throws Exception {
        if (gridMessages.getSelectedIndices().Length == 0)
        {
            MsgBox.show(this,"Please select at least one item first.");
            return ;
        }
         
        Signalod sig;
        for (int i = gridMessages.getSelectedIndices().Length - 1;i >= 0;i--)
        {
            //go backwards so that we can remove rows without problems.
            sig = (Signalod)gridMessages.getRows()[gridMessages.getSelectedIndices()[i]].Tag;
            if (sig.AckTime.Year > 1880)
            {
                continue;
            }
             
            //totally ignore if trying to ack a previously acked signal
            sig.AckTime = DateTime.Now + TimeDelta;
            Signalods.update(sig);
            //change the grid temporarily until the next timer event.  This makes it feel more responsive.
            if (checkIncludeAck.Checked)
            {
                gridMessages.getRows()[gridMessages.getSelectedIndices()[i]].Cells[3].Text = sig.AckTime.ToShortTimeString();
            }
            else
            {
                try
                {
                    gridMessages.getRows().RemoveAt(gridMessages.getSelectedIndices()[i]);
                }
                catch (Exception __dummyCatchVar0)
                {
                        ;
                }
            
            } 
        }
        //do nothing
        gridMessages.setSelected(false);
    }

    //gridMessages.Invalidate();
    private void checkIncludeAck_Click(Object sender, EventArgs e) throws Exception {
        if (checkIncludeAck.Checked)
        {
            textDays.Text = "1";
            labelDays.Visible = true;
            textDays.Visible = true;
        }
        else
        {
            labelDays.Visible = false;
            textDays.Visible = false;
            SignalList = Signalods.refreshFullText(DateTimeOD.getToday());
        } 
        //since midnight this morning.
        fillMessages();
    }

    private void textDays_TextChanged(Object sender, EventArgs e) throws Exception {
        if (!textDays.Visible)
        {
            errorProvider1.SetError(textDays, "");
            return ;
        }
         
        try
        {
            int days = int.Parse(textDays.Text);
            errorProvider1.SetError(textDays, "");
            SignalList = Signalods.RefreshFullText(DateTimeOD.getToday().AddDays(-days));
            fillMessages();
        }
        catch (Exception __dummyCatchVar1)
        {
            errorProvider1.SetError(textDays, Lan.g(this,"Invalid number.  Usually 1 or 2."));
        }
    
    }

    private void comboViewUser_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        fillMessages();
    }

    private void timerSending_Tick(Object sender, EventArgs e) throws Exception {
        labelSending.Visible = false;
        timerSending.Enabled = false;
    }

}


