//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:53 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormClockEventEdit;
import OpenDental.FormTimeAdjustEdit;
import OpenDental.FormTimeCard;
import OpenDental.Lan;
import OpenDental.ObjectDateComparer;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.Employee;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.PayPeriods;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Security;
import OpenDentBusiness.TimeAdjust;
import OpenDentBusiness.TimeAdjusts;
import OpenDentBusiness.TimeCardRules;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTimeCard  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelRegularTime = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private OpenDental.UI.Button butClose;
    private System.Windows.Forms.TextBox textTotal = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Timer timer1 = new System.Windows.Forms.Timer();
    /**
    * True to default to viewing breaks. False for regular hours.
    */
    public boolean IsBreaks = new boolean();
    /**
    * Server time minus local computer time, usually +/- 1 or 2 minutes
    */
    private TimeSpan TimeDelta = new TimeSpan();
    private OpenDental.UI.ODGrid gridMain;
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butLeft;
    private Label label4 = new Label();
    private TextBox textDateStart = new TextBox();
    private TextBox textDatePaycheck = new TextBox();
    private TextBox textDateStop = new TextBox();
    private List<ClockEvent> ClockEventList = new List<ClockEvent>();
    private OpenDental.UI.Button butAdj;
    public int SelectedPayPeriod = new int();
    private Label labelOvertime = new Label();
    private TextBox textOvertime = new TextBox();
    private OpenDental.UI.Button butCalcWeekOT;
    private OpenDental.UI.Button butPrint;
    private List<TimeAdjust> TimeAdjustList = new List<TimeAdjust>();
    private PrintDocument pd = new PrintDocument();
    private int linesPrinted = new int();
    private GroupBox groupBox2 = new GroupBox();
    private RadioButton radioBreaks = new RadioButton();
    private RadioButton radioTimeCard = new RadioButton();
    //private OpenDental.UI.PrintPreview printPreview;
    /**
    * An array list of ojects representing the rows in the table. Can be either clockEvents or timeAdjusts.
    */
    private ArrayList mergedAL = new ArrayList();
    /**
    * The running weekly total, whether it gets displayed or not.
    */
    private TimeSpan[] WeeklyTotals = new TimeSpan[]();
    private TextBox textOvertime2 = new TextBox();
    private TextBox textTotal2 = new TextBox();
    public Employee EmployeeCur;
    private OpenDental.UI.Button butCalcDaily;
    private TextBox textRateTwo2 = new TextBox();
    private Label labelRateTwo = new Label();
    private TextBox textRateTwo = new TextBox();
    private boolean cannotEdit = new boolean();
    /**
    * 
    */
    public FormTimeCard() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTimeCard.class);
        this.textTotal = new System.Windows.Forms.TextBox();
        this.labelRegularTime = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.timer1 = new System.Windows.Forms.Timer(this.components);
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textDatePaycheck = new System.Windows.Forms.TextBox();
        this.textDateStop = new System.Windows.Forms.TextBox();
        this.textDateStart = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.labelOvertime = new System.Windows.Forms.Label();
        this.textOvertime = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.radioBreaks = new System.Windows.Forms.RadioButton();
        this.radioTimeCard = new System.Windows.Forms.RadioButton();
        this.textOvertime2 = new System.Windows.Forms.TextBox();
        this.textTotal2 = new System.Windows.Forms.TextBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.textRateTwo2 = new System.Windows.Forms.TextBox();
        this.labelRateTwo = new System.Windows.Forms.Label();
        this.textRateTwo = new System.Windows.Forms.TextBox();
        this.butCalcDaily = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butCalcWeekOT = new OpenDental.UI.Button();
        this.butAdj = new OpenDental.UI.Button();
        this.butRight = new OpenDental.UI.Button();
        this.butLeft = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // textTotal
        //
        this.textTotal.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textTotal.Location = new System.Drawing.Point(491, 643);
        this.textTotal.Name = "textTotal";
        this.textTotal.Size = new System.Drawing.Size(66, 20);
        this.textTotal.TabIndex = 3;
        this.textTotal.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // labelRegularTime
        //
        this.labelRegularTime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelRegularTime.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelRegularTime.Location = new System.Drawing.Point(385, 644);
        this.labelRegularTime.Name = "labelRegularTime";
        this.labelRegularTime.Size = new System.Drawing.Size(100, 17);
        this.labelRegularTime.TabIndex = 4;
        this.labelRegularTime.Text = "Regular Time";
        this.labelRegularTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(146, 8);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(96, 18);
        this.label2.TabIndex = 6;
        this.label2.Text = "Start Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(143, 28);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(99, 18);
        this.label3.TabIndex = 8;
        this.label3.Text = "End Date";
        this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // timer1
        //
        this.timer1.Enabled = true;
        this.timer1.Interval = 1000;
        this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textDatePaycheck);
        this.groupBox1.Controls.Add(this.textDateStop);
        this.groupBox1.Controls.Add(this.textDateStart);
        this.groupBox1.Controls.Add(this.butRight);
        this.groupBox1.Controls.Add(this.butLeft);
        this.groupBox1.Controls.Add(this.label4);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Location = new System.Drawing.Point(18, 3);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(659, 51);
        this.groupBox1.TabIndex = 14;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Pay Period";
        //
        // textDatePaycheck
        //
        this.textDatePaycheck.Location = new System.Drawing.Point(473, 19);
        this.textDatePaycheck.Name = "textDatePaycheck";
        this.textDatePaycheck.ReadOnly = true;
        this.textDatePaycheck.Size = new System.Drawing.Size(100, 20);
        this.textDatePaycheck.TabIndex = 14;
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(244, 28);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.ReadOnly = true;
        this.textDateStop.Size = new System.Drawing.Size(100, 20);
        this.textDateStop.TabIndex = 13;
        //
        // textDateStart
        //
        this.textDateStart.Location = new System.Drawing.Point(244, 8);
        this.textDateStart.Name = "textDateStart";
        this.textDateStart.ReadOnly = true;
        this.textDateStart.Size = new System.Drawing.Size(100, 20);
        this.textDateStart.TabIndex = 12;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(354, 19);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(117, 18);
        this.label4.TabIndex = 9;
        this.label4.Text = "Paycheck Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelOvertime
        //
        this.labelOvertime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelOvertime.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelOvertime.Location = new System.Drawing.Point(385, 664);
        this.labelOvertime.Name = "labelOvertime";
        this.labelOvertime.Size = new System.Drawing.Size(100, 17);
        this.labelOvertime.TabIndex = 17;
        this.labelOvertime.Text = "Overtime";
        this.labelOvertime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textOvertime
        //
        this.textOvertime.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textOvertime.Location = new System.Drawing.Point(491, 663);
        this.textOvertime.Name = "textOvertime";
        this.textOvertime.Size = new System.Drawing.Size(66, 20);
        this.textOvertime.TabIndex = 16;
        this.textOvertime.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox2.Controls.Add(this.radioBreaks);
        this.groupBox2.Controls.Add(this.radioTimeCard);
        this.groupBox2.Location = new System.Drawing.Point(747, 3);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(122, 51);
        this.groupBox2.TabIndex = 20;
        this.groupBox2.TabStop = false;
        //
        // radioBreaks
        //
        this.radioBreaks.Location = new System.Drawing.Point(14, 27);
        this.radioBreaks.Name = "radioBreaks";
        this.radioBreaks.Size = new System.Drawing.Size(97, 19);
        this.radioBreaks.TabIndex = 1;
        this.radioBreaks.Text = "Breaks";
        this.radioBreaks.UseVisualStyleBackColor = true;
        this.radioBreaks.Click += new System.EventHandler(this.radioBreaks_Click);
        //
        // radioTimeCard
        //
        this.radioTimeCard.Checked = true;
        this.radioTimeCard.Location = new System.Drawing.Point(14, 10);
        this.radioTimeCard.Name = "radioTimeCard";
        this.radioTimeCard.Size = new System.Drawing.Size(97, 19);
        this.radioTimeCard.TabIndex = 0;
        this.radioTimeCard.TabStop = true;
        this.radioTimeCard.Text = "Time Card";
        this.radioTimeCard.UseVisualStyleBackColor = true;
        this.radioTimeCard.Click += new System.EventHandler(this.radioTimeCard_Click);
        //
        // textOvertime2
        //
        this.textOvertime2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textOvertime2.Location = new System.Drawing.Point(563, 663);
        this.textOvertime2.Name = "textOvertime2";
        this.textOvertime2.Size = new System.Drawing.Size(66, 20);
        this.textOvertime2.TabIndex = 22;
        this.textOvertime2.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textTotal2
        //
        this.textTotal2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textTotal2.Location = new System.Drawing.Point(563, 643);
        this.textTotal2.Name = "textTotal2";
        this.textTotal2.Size = new System.Drawing.Size(66, 20);
        this.textTotal2.TabIndex = 21;
        this.textTotal2.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(18, 60);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(851, 580);
        this.gridMain.TabIndex = 13;
        this.gridMain.setTitle("Time Card");
        this.gridMain.setTranslationName("TableTimeCard");
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
        // textRateTwo2
        //
        this.textRateTwo2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textRateTwo2.Location = new System.Drawing.Point(563, 683);
        this.textRateTwo2.Name = "textRateTwo2";
        this.textRateTwo2.Size = new System.Drawing.Size(66, 20);
        this.textRateTwo2.TabIndex = 26;
        this.textRateTwo2.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // labelRateTwo
        //
        this.labelRateTwo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelRateTwo.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelRateTwo.Location = new System.Drawing.Point(385, 684);
        this.labelRateTwo.Name = "labelRateTwo";
        this.labelRateTwo.Size = new System.Drawing.Size(100, 17);
        this.labelRateTwo.TabIndex = 25;
        this.labelRateTwo.Text = "Rate2";
        this.labelRateTwo.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRateTwo
        //
        this.textRateTwo.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textRateTwo.Location = new System.Drawing.Point(491, 683);
        this.textRateTwo.Name = "textRateTwo";
        this.textRateTwo.Size = new System.Drawing.Size(66, 20);
        this.textRateTwo.TabIndex = 24;
        this.textRateTwo.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // butCalcDaily
        //
        this.butCalcDaily.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCalcDaily.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCalcDaily.setAutosize(true);
        this.butCalcDaily.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCalcDaily.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCalcDaily.setCornerRadius(4F);
        this.butCalcDaily.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCalcDaily.Location = new System.Drawing.Point(139, 661);
        this.butCalcDaily.Name = "butCalcDaily";
        this.butCalcDaily.Size = new System.Drawing.Size(78, 24);
        this.butCalcDaily.TabIndex = 23;
        this.butCalcDaily.Text = "Calc Daily";
        this.butCalcDaily.Click += new System.EventHandler(this.butCalcDaily_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrint();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(691, 661);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(86, 24);
        this.butPrint.TabIndex = 19;
        this.butPrint.Text = "Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // butCalcWeekOT
        //
        this.butCalcWeekOT.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCalcWeekOT.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCalcWeekOT.setAutosize(true);
        this.butCalcWeekOT.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCalcWeekOT.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCalcWeekOT.setCornerRadius(4F);
        this.butCalcWeekOT.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butCalcWeekOT.Location = new System.Drawing.Point(223, 661);
        this.butCalcWeekOT.Name = "butCalcWeekOT";
        this.butCalcWeekOT.Size = new System.Drawing.Size(90, 24);
        this.butCalcWeekOT.TabIndex = 18;
        this.butCalcWeekOT.Text = "Calc Week OT";
        this.butCalcWeekOT.Click += new System.EventHandler(this.butCalcWeekOT_Click);
        //
        // butAdj
        //
        this.butAdj.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdj.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdj.setAutosize(true);
        this.butAdj.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdj.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdj.setCornerRadius(4F);
        this.butAdj.Image = Resources.getAdd();
        this.butAdj.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdj.Location = new System.Drawing.Point(18, 661);
        this.butAdj.Name = "butAdj";
        this.butAdj.Size = new System.Drawing.Size(115, 24);
        this.butAdj.TabIndex = 15;
        this.butAdj.Text = "Add Adjustment";
        this.butAdj.Click += new System.EventHandler(this.butAdj_Click);
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(63, 18);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(39, 24);
        this.butRight.TabIndex = 11;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(13, 18);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(39, 24);
        this.butLeft.TabIndex = 10;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(794, 661);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormTimeCard
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(891, 703);
        this.Controls.Add(this.textRateTwo2);
        this.Controls.Add(this.labelRateTwo);
        this.Controls.Add(this.textRateTwo);
        this.Controls.Add(this.butCalcDaily);
        this.Controls.Add(this.textOvertime2);
        this.Controls.Add(this.textTotal2);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butCalcWeekOT);
        this.Controls.Add(this.labelOvertime);
        this.Controls.Add(this.textOvertime);
        this.Controls.Add(this.butAdj);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.labelRegularTime);
        this.Controls.Add(this.textTotal);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTimeCard";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Time Card";
        this.Load += new System.EventHandler(this.FormTimeCard_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formTimeCard_Load(Object sender, System.EventArgs e) throws Exception {
        initialize(DateTimeOD.getToday());
    }

    public void initialize(DateTime dateInitial) throws Exception {
        //Check to see if the employee currently logged in can edit this time-card.
        cannotEdit = Security.getCurUser() != null && Security.getCurUser().EmployeeNum == EmployeeCur.EmployeeNum && PrefC.getBool(PrefName.TimecardSecurityEnabled) && PrefC.getBool(PrefName.TimecardUsersDontEditOwnCard);
        if (cannotEdit)
        {
            butAdj.Enabled = false;
            butCalcWeekOT.Enabled = false;
        }
         
        //butCompute.Enabled=false;
        Text = Lan.g(this,"Time Card for") + " " + EmployeeCur.FName + " " + EmployeeCur.LName + (cannotEdit ? " - You cannot modify your timecard" : "");
        TimeDelta = MiscData.getNowDateTime() - DateTime.Now;
        if (SelectedPayPeriod == 0)
        {
            SelectedPayPeriod = PayPeriods.getForDate(dateInitial);
        }
         
        if (IsBreaks)
        {
            textOvertime.Visible = false;
            labelOvertime.Visible = false;
            butCalcWeekOT.Visible = false;
            //butCompute.Visible=false;
            butAdj.Visible = false;
            labelRateTwo.Visible = false;
            textRateTwo.Visible = false;
            textRateTwo2.Visible = false;
        }
         
        radioTimeCard.Checked = !IsBreaks;
        radioBreaks.Checked = IsBreaks;
        fillPayPeriod();
        fillMain(true);
    }

    private void butLeft_Click(Object sender, EventArgs e) throws Exception {
        if (SelectedPayPeriod == 0)
        {
            return ;
        }
         
        SelectedPayPeriod--;
        fillPayPeriod();
        fillMain(true);
    }

    private void butRight_Click(Object sender, EventArgs e) throws Exception {
        if (SelectedPayPeriod == PayPeriods.getList().Length - 1)
        {
            return ;
        }
         
        SelectedPayPeriod++;
        fillPayPeriod();
        fillMain(true);
    }

    /**
    * SelectedPayPeriod should already be set.  This simply fills the screen with that data.
    */
    private void fillPayPeriod() throws Exception {
        textDateStart.Text = PayPeriods.getList()[SelectedPayPeriod].DateStart.ToShortDateString();
        textDateStop.Text = PayPeriods.getList()[SelectedPayPeriod].DateStop.ToShortDateString();
        if (PayPeriods.getList()[SelectedPayPeriod].DatePaycheck.Year < 1880)
        {
            textDatePaycheck.Text = "";
        }
        else
        {
            textDatePaycheck.Text = PayPeriods.getList()[SelectedPayPeriod].DatePaycheck.ToShortDateString();
        } 
    }

    private void radioTimeCard_Click(Object sender, EventArgs e) throws Exception {
        IsBreaks = false;
        textOvertime.Visible = true;
        labelOvertime.Visible = true;
        butCalcDaily.Visible = true;
        //butDaily.Visible=true;
        butCalcWeekOT.Visible = true;
        //butCompute.Visible=true;
        butAdj.Visible = true;
        labelRateTwo.Visible = true;
        textRateTwo.Visible = true;
        textRateTwo2.Visible = true;
        fillMain(true);
    }

    private void radioBreaks_Click(Object sender, EventArgs e) throws Exception {
        IsBreaks = true;
        textOvertime.Visible = false;
        labelOvertime.Visible = false;
        butCalcDaily.Visible = false;
        //butDaily.Visible=false;
        butCalcWeekOT.Visible = false;
        //butCompute.Visible=false;
        butAdj.Visible = false;
        labelRateTwo.Visible = false;
        textRateTwo.Visible = false;
        textRateTwo2.Visible = false;
        //butDaily.Visible=false;
        fillMain(true);
    }

    private DateTime getDateForRow(int i) throws Exception {
        if (mergedAL[i].GetType() == ClockEvent.class)
        {
            return ((ClockEvent)mergedAL[i]).TimeDisplayed1.Date;
        }
        else if (mergedAL[i].GetType() == TimeAdjust.class)
        {
            return ((TimeAdjust)mergedAL[i]).TimeEntry.Date;
        }
          
        return DateTime.MinValue;
    }

    /**
    * fromDB is set to false when it is refreshing every second so that there will be no extra network traffic.
    */
    private void fillMain(boolean fromDB) throws Exception {
        if (fromDB)
        {
            ClockEventList = ClockEvents.Refresh(EmployeeCur.EmployeeNum, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text), IsBreaks);
            if (IsBreaks)
            {
                TimeAdjustList = new List<TimeAdjust>();
            }
            else
            {
                TimeAdjustList = TimeAdjusts.Refresh(EmployeeCur.EmployeeNum, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
            } 
        }
         
        mergedAL = new ArrayList();
        for (int i = 0;i < ClockEventList.Count;i++)
        {
            mergedAL.Add(ClockEventList[i]);
        }
        for (int i = 0;i < TimeAdjustList.Count;i++)
        {
            mergedAL.Add(TimeAdjustList[i]);
        }
        IComparer myComparer = new ObjectDateComparer();
        mergedAL.Sort(myComparer);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Date"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Weekday"),70);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn(Lan.g(this,"Altered"),50,HorizontalAlignment.Center);//use red now instead of separate col
        //gridMain.Columns.Add(col);
        if (IsBreaks)
        {
            col = new ODGridColumn(Lan.g(this,"Out"), 60, HorizontalAlignment.Right);
            gridMain.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"In"), 60, HorizontalAlignment.Right);
            gridMain.getColumns().add(col);
        }
        else
        {
            col = new ODGridColumn(Lan.g(this,"In"), 60, HorizontalAlignment.Right);
            gridMain.getColumns().add(col);
            col = new ODGridColumn(Lan.g(this,"Out"), 60, HorizontalAlignment.Right);
            gridMain.getColumns().add(col);
        } 
        col = new ODGridColumn(Lan.g(this,"Total"), 50, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Adjust"), 55, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Rate2"), 55, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Overtime"), 55, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Daily"), 50, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Weekly"), 50, HorizontalAlignment.Right);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Note"),5);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        WeeklyTotals = new TimeSpan[mergedAL.Count];
        TimeSpan alteredSpan = new TimeSpan(0);
        //used to display altered times
        TimeSpan oneSpan = new TimeSpan(0);
        //used to sum one pair of clock-in/clock-out
        TimeSpan oneAdj = new TimeSpan();
        TimeSpan oneOT = new TimeSpan();
        TimeSpan daySpan = new TimeSpan(0);
        //used for daily totals.
        TimeSpan weekSpan = new TimeSpan(0);
        //used for weekly totals.
        if (mergedAL.Count > 0)
        {
            weekSpan = ClockEvents.getWeekTotal(EmployeeCur.EmployeeNum,getDateForRow(0));
        }
         
        TimeSpan periodSpan = new TimeSpan(0);
        //used to add up totals for entire page.
        TimeSpan otspan = new TimeSpan(0);
        //overtime for the entire period
        TimeSpan rate2span = new TimeSpan(0);
        //rate2 hours total
        Calendar cal = CultureInfo.CurrentCulture.Calendar;
        CalendarWeekRule rule = CalendarWeekRule.FirstFullWeek;
        //CultureInfo.CurrentCulture.DateTimeFormat.CalendarWeekRule;
        DateTime curDate = DateTime.MinValue;
        DateTime previousDate = DateTime.MinValue;
        Type type = new Type();
        ClockEvent clock;
        TimeAdjust adjust;
        for (int i = 0;i < mergedAL.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            type = mergedAL[i].GetType();
            row.setTag(mergedAL[i]);
            previousDate = curDate;
            //clock event row---------------------------------------------------------------------------------------------
            if (type == ClockEvent.class)
            {
                clock = (ClockEvent)mergedAL[i];
                curDate = clock.TimeDisplayed1.Date;
                if (curDate == previousDate)
                {
                    row.getCells().add("");
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(curDate.ToShortDateString());
                    row.getCells().Add(curDate.DayOfWeek.ToString());
                } 
                //altered--------------------------------------
                //string str="";
                //if(clock.TimeEntered1!=clock.TimeDisplayed1){
                //	if(IsBreaks){
                //		str=Lan.g(this,"out");
                //	}
                //	else{
                //		str=Lan.g(this,"in");
                //	}
                //}
                //if(clock.TimeEntered2!=clock.TimeDisplayed2){
                //	if(str!="") {
                //		str+="/";
                //	}
                //	if(IsBreaks){
                //		str+=Lan.g(this,"in");
                //	}
                //	else{
                //		str+=Lan.g(this,"out");
                //	}
                //}
                //row.Cells.Add(str);
                //status--------------------------------------
                //row.Cells.Add(clock.ClockStatus.ToString());
                //in------------------------------------------
                row.getCells().Add(clock.TimeDisplayed1.ToShortTimeString());
                if (clock.TimeEntered1 != clock.TimeDisplayed1)
                {
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                 
                //out-----------------------------
                if (clock.TimeDisplayed2.Year < 1880)
                {
                    row.getCells().add("");
                }
                else
                {
                    //not clocked out yet
                    row.getCells().Add(clock.TimeDisplayed2.ToShortTimeString());
                    if (clock.TimeEntered2 != clock.TimeDisplayed2)
                    {
                        row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                    }
                     
                } 
                //total-------------------------------
                if (IsBreaks)
                {
                    //breaks
                    if (clock.TimeDisplayed2.Year < 1880)
                    {
                        row.getCells().add("");
                    }
                    else
                    {
                        oneSpan = clock.TimeDisplayed2 - clock.TimeDisplayed1;
                        row.getCells().add(ClockEvents.format(oneSpan));
                        daySpan += oneSpan;
                        periodSpan += oneSpan;
                    } 
                }
                else
                {
                    //regular hours
                    if (clock.TimeDisplayed2.Year < 1880)
                    {
                        row.getCells().add("");
                    }
                    else
                    {
                        oneSpan = clock.TimeDisplayed2 - clock.TimeDisplayed1;
                        row.getCells().add(ClockEvents.format(oneSpan));
                        daySpan += oneSpan;
                        weekSpan += oneSpan;
                        periodSpan += oneSpan;
                    } 
                } 
                //Adjust---------------------------------
                oneAdj = TimeSpan.Zero;
                if (clock.AdjustIsOverridden)
                {
                    oneAdj += clock.Adjust;
                }
                else
                {
                    oneAdj += clock.AdjustAuto;
                } 
                //typically zero
                daySpan += oneAdj;
                weekSpan += oneAdj;
                periodSpan += oneAdj;
                row.getCells().add(ClockEvents.format(oneAdj));
                if (clock.AdjustIsOverridden)
                {
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                 
                //Rate2---------------------------------
                if (clock.Rate2Hours != TimeSpan.FromHours(-1))
                {
                    rate2span += clock.Rate2Hours;
                    row.getCells().add(ClockEvents.format(clock.Rate2Hours));
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                else
                {
                    rate2span += clock.Rate2Auto;
                    row.getCells().add(ClockEvents.format(clock.Rate2Auto));
                } 
                //Overtime------------------------------
                oneOT = TimeSpan.Zero;
                if (clock.OTimeHours != TimeSpan.FromHours(-1))
                {
                    //overridden
                    oneOT = clock.OTimeHours;
                }
                else
                {
                    oneOT = clock.OTimeAuto;
                } 
                //typically zero
                otspan += oneOT;
                daySpan -= oneOT;
                weekSpan -= oneOT;
                periodSpan -= oneOT;
                row.getCells().add(ClockEvents.format(oneOT));
                if (clock.OTimeHours != TimeSpan.FromHours(-1))
                {
                    //overridden
                    row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                }
                 
                //Daily-----------------------------------
                //if this is the last entry for a given date
                //if this is the last row
                if (i == mergedAL.Count - 1 || getDateForRow(i + 1) != curDate)
                {
                    //or the next row is a different date
                    if (IsBreaks)
                    {
                        if (clock.TimeDisplayed2.Year < 1880)
                        {
                            //if they have not clocked back in yet from break
                            //display the timespan of oneSpan using current time as the other number.
                            oneSpan = DateTime.Now - clock.TimeDisplayed1 + TimeDelta;
                            row.getCells().Add(oneSpan.ToStringHmmss());
                            daySpan += oneSpan;
                        }
                        else
                        {
                            row.getCells().add(ClockEvents.format(daySpan));
                        } 
                    }
                    else
                    {
                        row.getCells().add(ClockEvents.format(daySpan));
                    } 
                    daySpan = new TimeSpan(0);
                }
                else
                {
                    //not the last entry for the day
                    row.getCells().add("");
                } 
                //Weekly-------------------------------------
                WeeklyTotals[i] = weekSpan;
                if (IsBreaks)
                {
                    row.getCells().add("");
                }
                else //if this is the last entry for a given week
                //if this is the last row
                //or the next row has a
                if (i == mergedAL.Count - 1 || cal.GetWeekOfYear(getDateForRow(i + 1), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) != cal.GetWeekOfYear(clock.TimeDisplayed1.Date, rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
                {
                    //different week of year
                    row.getCells().add(ClockEvents.format(weekSpan));
                    weekSpan = new TimeSpan(0);
                }
                else
                {
                    //row.Cells.Add(ClockEvents.Format(weekSpan));
                    row.getCells().add("");
                }  
                //Note-----------------------------------------
                row.getCells().add(clock.Note);
            }
            else //adjustment row--------------------------------------------------------------------------------------
            if (type == TimeAdjust.class)
            {
                adjust = (TimeAdjust)mergedAL[i];
                curDate = adjust.TimeEntry.Date;
                if (curDate == previousDate)
                {
                    row.getCells().add("");
                    row.getCells().add("");
                }
                else
                {
                    row.getCells().Add(curDate.ToShortDateString());
                    row.getCells().Add(curDate.DayOfWeek.ToString());
                } 
                //altered--------------------------------------
                //row.Cells.Add(Lan.g(this,"Adjust"));//2
                //row.ColorText=Color.Red;
                //status--------------------------------------
                //row.Cells.Add("");//3
                //in/out------------------------------------------
                row.getCells().add("");
                //4
                //time-----------------------------
                row.getCells().add("(Adjust)");
                //Out column
                row.getCells()[row.getCells().Count - 1].ColorText = Color.Red;
                //total-------------------------------
                row.getCells().add("");
                //
                //Adjust------------------------------
                daySpan += adjust.RegHours;
                //might be negative
                weekSpan += adjust.RegHours;
                periodSpan += adjust.RegHours;
                row.getCells().add(ClockEvents.format(adjust.RegHours));
                //6
                //Rate2-------------------------------
                row.getCells().add("");
                //
                //Overtime------------------------------
                otspan += adjust.OTimeHours;
                row.getCells().add(ClockEvents.format(adjust.OTimeHours));
                //7
                //Daily-----------------------------------
                //if this is the last entry for a given date
                //if this is the last row
                if (i == mergedAL.Count - 1 || getDateForRow(i + 1) != curDate)
                {
                    //or the next row is a different date
                    row.getCells().add(ClockEvents.format(daySpan));
                    //
                    daySpan = new TimeSpan(0);
                }
                else
                {
                    row.getCells().add("");
                } 
                //Weekly-------------------------------------
                WeeklyTotals[i] = weekSpan;
                if (IsBreaks)
                {
                    row.getCells().add("");
                }
                else //if this is the last entry for a given week
                //if this is the last row
                //or the next row has a
                if (i == mergedAL.Count - 1 || cal.GetWeekOfYear(getDateForRow(i + 1), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) != cal.GetWeekOfYear(adjust.TimeEntry.Date, rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
                {
                    //different week of year
                    OpenDental.UI.ODGridCell cell = new OpenDental.UI.ODGridCell(ClockEvents.format(weekSpan));
                    cell.setColorText(Color.Black);
                    row.getCells().Add(cell);
                    weekSpan = new TimeSpan(0);
                }
                else
                {
                    row.getCells().add("");
                }  
                //Note-----------------------------------------
                row.getCells().add(adjust.Note);
            }
              
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        if (IsBreaks)
        {
            labelRegularTime.Visible = false;
            labelOvertime.Visible = false;
            labelRateTwo.Visible = false;
            textTotal.Visible = false;
            textTotal2.Visible = false;
            textOvertime.Visible = false;
            textOvertime2.Visible = false;
            textRateTwo.Visible = false;
            textRateTwo2.Visible = false;
        }
        else
        {
            labelRegularTime.Visible = true;
            labelOvertime.Visible = true;
            labelRateTwo.Visible = true;
            textTotal.Visible = true;
            textTotal2.Visible = true;
            textOvertime.Visible = true;
            textOvertime2.Visible = true;
            textRateTwo.Visible = true;
            textRateTwo2.Visible = true;
            textTotal.Text = periodSpan.ToStringHmm();
            textOvertime.Text = otspan.ToStringHmm();
            textRateTwo.Text = rate2span.ToStringHmm();
            textTotal2.Text = periodSpan.TotalHours.ToString("n");
            textOvertime2.Text = otspan.TotalHours.ToString("n");
            textRateTwo2.Text = rate2span.TotalHours.ToString("n");
        } 
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (cannotEdit)
        {
            return ;
        }
         
        timer1.Enabled = false;
        if (gridMain.getRows().get___idx(e.getRow()).getTag().GetType() == TimeAdjust.class)
        {
            if (!Security.isAuthorized(Permissions.TimecardsEditAll))
            {
                timer1.Enabled = true;
                return ;
            }
             
            TimeAdjust adjust = (TimeAdjust)gridMain.getRows().get___idx(e.getRow()).getTag();
            FormTimeAdjustEdit FormT = new FormTimeAdjustEdit(adjust);
            FormT.ShowDialog();
        }
        else
        {
            ClockEvent ce = (ClockEvent)gridMain.getRows().get___idx(e.getRow()).getTag();
            FormClockEventEdit FormCEE = new FormClockEventEdit(ce);
            FormCEE.ShowDialog();
        } 
        fillMain(true);
        timer1.Enabled = true;
    }

    private void butAdj_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.TimecardsEditAll))
        {
            return ;
        }
         
        TimeAdjust adjust = new TimeAdjust();
        adjust.EmployeeNum = EmployeeCur.EmployeeNum;
        DateTime dateStop = PIn.Date(textDateStop.Text);
        if (DateTime.Today <= dateStop && DateTime.Today >= PIn.Date(textDateStart.Text))
        {
            adjust.TimeEntry = DateTime.Now;
        }
        else
        {
            adjust.TimeEntry = new DateTime(dateStop.Year, dateStop.Month, dateStop.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second);
        } 
        FormTimeAdjustEdit FormT = new FormTimeAdjustEdit(adjust);
        FormT.IsNew = true;
        FormT.ShowDialog();
        if (FormT.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillMain(true);
    }

    private void butCalcWeekOT_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.TimecardsEditAll))
        {
            return ;
        }
         
        try
        {
            TimeCardRules.CalculateWeeklyOvertime(EmployeeCur, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, ex.Message);
        }

        fillMain(true);
    }

    private void butCalcDaily_Click(Object sender, EventArgs e) throws Exception {
        //not even visible if viewing breaks.
        if (!Security.isAuthorized(Permissions.TimecardsEditAll))
        {
            return ;
        }
         
        String errors = TimeCardRules.ValidatePayPeriod(EmployeeCur, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
        if (!StringSupport.equals(errors, ""))
        {
            MessageBox.Show(this, errors);
            return ;
        }
         
        try
        {
            TimeCardRules.CalculateDailyOvertime(EmployeeCur, PIn.Date(textDateStart.Text), PIn.Date(textDateStop.Text));
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, ex.Message);
        }

        fillMain(true);
    }

    private void butPrint_Click(Object sender, EventArgs e) throws Exception {
        linesPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pd_PrintPage);
        pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        pd.OriginAtMargins = true;
        try
        {
            if (PrinterL.SetPrinter(pd, PrintSituation.Default, 0, "Time card for " + EmployeeCur.LName + "," + EmployeeCur.FName + " printed"))
            {
                pd.Print();
            }
             
        }
        catch (Exception __dummyCatchVar0)
        {
            MessageBox.Show(Lan.g(this,"Printer not available"));
        }
    
    }

    /**
    * raised for each page to be printed.
    */
    private void pd_PrintPage(Object sender, PrintPageEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        float yPos = 75;
        float xPos = 55;
        String str = new String();
        Font font = new Font(FontFamily.GenericSansSerif, 8);
        Font fontTitle = new Font(FontFamily.GenericSansSerif, 11, FontStyle.Bold);
        Font fontHeader = new Font(FontFamily.GenericSansSerif, 8, FontStyle.Bold);
        SolidBrush brush = new SolidBrush(Color.Black);
        Pen pen = new Pen(Color.Black);
        //Title
        str = EmployeeCur.FName + " " + EmployeeCur.LName;
        g.DrawString(str, fontTitle, brush, xPos, yPos);
        yPos += 30;
        //define columns
        int[] colW = new int[11];
        colW[0] = 70;
        //date
        colW[1] = 70;
        //weekday
        //colW[2]=50;//altered
        colW[2] = 60;
        //in
        colW[3] = 60;
        //out
        colW[4] = 50;
        //total
        colW[5] = 50;
        //adjust
        colW[6] = 50;
        //rate2
        colW[7] = 55;
        //overtime
        colW[8] = 50;
        //daily
        colW[9] = 50;
        //weekly
        colW[10] = 160;
        //note
        int[] colPos = new int[colW.Length + 1];
        colPos[0] = 45;
        for (int i = 1;i < colPos.Length;i++)
        {
            colPos[i] = colPos[i - 1] + colW[i - 1];
        }
        String[] ColCaption = new String[11];
        ColCaption[0] = Lan.g(this,"Date");
        ColCaption[1] = Lan.g(this,"Weekday");
        //ColCaption[2]=Lan.g(this,"Altered");
        ColCaption[2] = Lan.g(this,"In");
        ColCaption[3] = Lan.g(this,"Out");
        ColCaption[4] = Lan.g(this,"Total");
        ColCaption[5] = Lan.g(this,"Adjust");
        ColCaption[6] = Lan.g(this,"Rate 2");
        ColCaption[7] = Lan.g(this,"Overtime");
        ColCaption[8] = Lan.g(this,"Daily");
        ColCaption[9] = Lan.g(this,"Weekly");
        ColCaption[10] = Lan.g(this,"Note");
        //column headers-----------------------------------------------------------------------------------------
        e.Graphics.FillRectangle(Brushes.LightGray, colPos[0], yPos, colPos[colPos.Length - 1] - colPos[0], 18);
        e.Graphics.DrawRectangle(pen, colPos[0], yPos, colPos[colPos.Length - 1] - colPos[0], 18);
        for (int i = 1;i < colPos.Length;i++)
        {
            e.Graphics.DrawLine(new Pen(Color.Black), colPos[i], yPos, colPos[i], yPos + 18);
        }
        for (int i = 0;i < ColCaption.Length;i++)
        {
            //Prints the Column Titles
            e.Graphics.DrawString(ColCaption[i], fontHeader, brush, colPos[i] + 2, yPos + 1);
        }
        yPos += 18;
        while (yPos < e.PageBounds.Height - 75 - 50 - 32 - 16 && linesPrinted < gridMain.getRows().Count)
        {
            for (int i = 0;i < colPos.Length - 1;i++)
            {
                e.Graphics.DrawString(gridMain.getRows().get___idx(linesPrinted).getCells()[i].Text, font, brush, new RectangleF(colPos[i] + 2, yPos, colPos[i + 1] - colPos[i] - 5, font.GetHeight(e.Graphics)));
            }
            for (int i = 0;i < colPos.Length;i++)
            {
                //Column lines
                e.Graphics.DrawLine(Pens.Gray, colPos[i], yPos + 16, colPos[i], yPos);
            }
            linesPrinted++;
            yPos += 16;
            e.Graphics.DrawLine(new Pen(Color.Gray), colPos[0], yPos, colPos[colPos.Length - 1], yPos);
        }
        //bottom line
        //e.Graphics.DrawLine(new Pen(Color.Gray),colPos[0],yPos,colPos[colPos.Length-1],yPos);
        //totals will print on every page for simplicity
        yPos += 10;
        g.DrawString(Lan.g(this,"Regular Time") + ": " + textTotal.Text + " (" + textTotal2.Text + ")", fontHeader, brush, xPos, yPos);
        yPos += 16;
        g.DrawString(Lan.g(this,"Overtime") + ": " + textOvertime.Text + " (" + textOvertime2.Text + ")", fontHeader, brush, xPos, yPos);
        yPos += 16;
        g.DrawString(Lan.g(this,"Rate 2 Time") + ": " + textRateTwo.Text + " (" + textRateTwo2.Text + ")", fontHeader, brush, xPos, yPos);
        if (linesPrinted == gridMain.getRows().Count)
        {
            e.HasMorePages = false;
        }
        else
        {
            e.HasMorePages = true;
        } 
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void timer1_Tick(Object sender, System.EventArgs e) throws Exception {
        if (IsBreaks)
        {
            fillMain(false);
        }
         
    }

}


