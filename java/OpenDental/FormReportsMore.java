//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:42 PM
//

package OpenDental;

import OpenDental.FormEhrPatientExport;
import OpenDental.FormPatListEHR2014;
import OpenDental.FormQuery;
import OpenDental.FormReferralProcTrack;
import OpenDental.FormReportSetup;
import OpenDental.FormReportsMore;
import OpenDental.FormReportsUds;
import OpenDental.FormRpAdjSheet;
import OpenDental.FormRpAging;
import OpenDental.FormRpAppointments;
import OpenDental.FormRpArizonaPrimaryCareEligibility;
import OpenDental.FormRpArizonaPrimaryCareEncounter;
import OpenDental.FormRpBirthday;
import OpenDental.FormRpCapitation;
import OpenDental.FormRpClaimNotSent;
import OpenDental.FormRpFinanceCharge;
import OpenDental.FormRpInsCo;
import OpenDental.FormRpInsOverpaid;
import OpenDental.FormRpLaserLabels;
import OpenDental.FormRpNewPatients;
import OpenDental.FormRpPatients;
import OpenDental.FormRpPayPlans;
import OpenDental.FormRpPaySheet;
import OpenDental.FormRpPHRawPop;
import OpenDental.FormRpPHRawScreen;
import OpenDental.FormRpPPOwriteoffs;
import OpenDental.FormRpPrescriptions;
import OpenDental.FormRpProcCodes;
import OpenDental.FormRpProcNotBilledIns;
import OpenDental.FormRpProcNote;
import OpenDental.FormRpProcSheet;
import OpenDental.FormRpProdInc;
import OpenDental.FormRpReceivablesBreakdown;
import OpenDental.FormRpReferralAnalysis;
import OpenDental.FormRpReferrals;
import OpenDental.FormRpRouting;
import OpenDental.FormRpUnearnedIncome;
import OpenDental.FormRpWriteoffSheet;
import OpenDental.FormSearchPatNotes;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.ReportModalSelection;
import OpenDentalWpf.WinDashboard;
import OpenDentBusiness.ClaimPayment;
import OpenDentBusiness.ClaimPayments;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.PatFieldDef;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReportsMore  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private Label label3 = new Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ListBoxClickable listLists;
    private OpenDental.UI.ListBoxClickable listPublicHealth;
    private OpenDental.UI.Button butUserQuery;
    private OpenDental.UI.Button butPW;
    private OpenDental.UI.ListBoxClickable listProdInc;
    private Label label4 = new Label();
    private OpenDental.UI.ListBoxClickable listDaily;
    private Label label5 = new Label();
    private Label label6 = new Label();
    private OpenDental.UI.Button butLaserLabels;
    private OpenDental.UI.ListBoxClickable listArizonaPrimaryCare;
    private Label labelArizonaPrimaryCare = new Label();
    private OpenDental.UI.ListBoxClickable listMonthly;
    private OpenDental.UI.Button butDashboard;
    private MenuStrip menuMain = new MenuStrip();
    private ToolStripMenuItem setupToolStripMenuItem = new ToolStripMenuItem();
    private OpenDental.UI.Button butUDS;
    private OpenDental.UI.Button butPatList;
    private OpenDental.UI.Button butPatExport;
    /**
    * After this form closes, this value is checked to see if any non-modal dialog boxes are needed.
    */
    public ReportModalSelection RpModalSelection = ReportModalSelection.None;
    /**
    * 
    */
    public FormReportsMore() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReportsMore.class);
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.labelArizonaPrimaryCare = new System.Windows.Forms.Label();
        this.listArizonaPrimaryCare = new OpenDental.UI.ListBoxClickable();
        this.butLaserLabels = new OpenDental.UI.Button();
        this.listDaily = new OpenDental.UI.ListBoxClickable();
        this.listProdInc = new OpenDental.UI.ListBoxClickable();
        this.butPW = new OpenDental.UI.Button();
        this.butUserQuery = new OpenDental.UI.Button();
        this.listPublicHealth = new OpenDental.UI.ListBoxClickable();
        this.listLists = new OpenDental.UI.ListBoxClickable();
        this.listMonthly = new OpenDental.UI.ListBoxClickable();
        this.butClose = new OpenDental.UI.Button();
        this.butDashboard = new OpenDental.UI.Button();
        this.menuMain = new System.Windows.Forms.MenuStrip();
        this.setupToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        this.butUDS = new OpenDental.UI.Button();
        this.butPatList = new OpenDental.UI.Button();
        this.butPatExport = new OpenDental.UI.Button();
        this.menuMain.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(312, 273);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(118, 18);
        this.label1.TabIndex = 2;
        this.label1.Text = "Public Health";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(312, 66);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(118, 18);
        this.label2.TabIndex = 4;
        this.label2.Text = "Lists";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(9, 299);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(118, 18);
        this.label3.TabIndex = 6;
        this.label3.Text = "Monthly";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(9, 66);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(207, 18);
        this.label4.TabIndex = 13;
        this.label4.Text = "Production and Income";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(9, 182);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(118, 18);
        this.label5.TabIndex = 15;
        this.label5.Text = "Daily";
        this.label5.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // label6
        //
        this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label6.Location = new System.Drawing.Point(9, 506);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(479, 100);
        this.label6.TabIndex = 17;
        this.label6.Text = resources.GetString("label6.Text");
        //
        // labelArizonaPrimaryCare
        //
        this.labelArizonaPrimaryCare.AutoSize = true;
        this.labelArizonaPrimaryCare.Location = new System.Drawing.Point(312, 333);
        this.labelArizonaPrimaryCare.Name = "labelArizonaPrimaryCare";
        this.labelArizonaPrimaryCare.Size = new System.Drawing.Size(104, 13);
        this.labelArizonaPrimaryCare.TabIndex = 20;
        this.labelArizonaPrimaryCare.Text = "Arizona Primary Care";
        this.labelArizonaPrimaryCare.Visible = false;
        //
        // listArizonaPrimaryCare
        //
        this.listArizonaPrimaryCare.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
        this.listArizonaPrimaryCare.FormattingEnabled = true;
        this.listArizonaPrimaryCare.ItemHeight = 15;
        this.listArizonaPrimaryCare.Location = new System.Drawing.Point(315, 351);
        this.listArizonaPrimaryCare.Name = "listArizonaPrimaryCare";
        this.listArizonaPrimaryCare.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listArizonaPrimaryCare.Size = new System.Drawing.Size(204, 34);
        this.listArizonaPrimaryCare.TabIndex = 19;
        this.listArizonaPrimaryCare.Visible = false;
        this.listArizonaPrimaryCare.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listArizonaPrimaryCare_MouseDown);
        //
        // butLaserLabels
        //
        this.butLaserLabels.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLaserLabels.setAutosize(true);
        this.butLaserLabels.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLaserLabels.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLaserLabels.setCornerRadius(4F);
        this.butLaserLabels.Location = new System.Drawing.Point(315, 35);
        this.butLaserLabels.Name = "butLaserLabels";
        this.butLaserLabels.Size = new System.Drawing.Size(75, 24);
        this.butLaserLabels.TabIndex = 18;
        this.butLaserLabels.Text = "Laser Labels";
        this.butLaserLabels.UseVisualStyleBackColor = true;
        this.butLaserLabels.Click += new System.EventHandler(this.butLaserLabels_Click);
        //
        // listDaily
        //
        this.listDaily.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
        this.listDaily.FormattingEnabled = true;
        this.listDaily.ItemHeight = 15;
        this.listDaily.Location = new System.Drawing.Point(12, 203);
        this.listDaily.Name = "listDaily";
        this.listDaily.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listDaily.Size = new System.Drawing.Size(204, 94);
        this.listDaily.TabIndex = 16;
        this.listDaily.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listDaily_MouseDown);
        //
        // listProdInc
        //
        this.listProdInc.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
        this.listProdInc.FormattingEnabled = true;
        this.listProdInc.ItemHeight = 15;
        this.listProdInc.Location = new System.Drawing.Point(12, 87);
        this.listProdInc.Name = "listProdInc";
        this.listProdInc.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listProdInc.Size = new System.Drawing.Size(204, 94);
        this.listProdInc.TabIndex = 14;
        this.listProdInc.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listProdInc_MouseDown);
        //
        // butPW
        //
        this.butPW.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPW.setAutosize(true);
        this.butPW.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPW.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPW.setCornerRadius(4F);
        this.butPW.Location = new System.Drawing.Point(135, 35);
        this.butPW.Name = "butPW";
        this.butPW.Size = new System.Drawing.Size(84, 24);
        this.butPW.TabIndex = 12;
        this.butPW.Text = "PW Reports";
        this.butPW.Click += new System.EventHandler(this.butPW_Click);
        //
        // butUserQuery
        //
        this.butUserQuery.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUserQuery.setAutosize(true);
        this.butUserQuery.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUserQuery.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUserQuery.setCornerRadius(4F);
        this.butUserQuery.Location = new System.Drawing.Point(12, 35);
        this.butUserQuery.Name = "butUserQuery";
        this.butUserQuery.Size = new System.Drawing.Size(84, 24);
        this.butUserQuery.TabIndex = 11;
        this.butUserQuery.Text = "User Query";
        this.butUserQuery.Click += new System.EventHandler(this.butUserQuery_Click);
        //
        // listPublicHealth
        //
        this.listPublicHealth.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
        this.listPublicHealth.FormattingEnabled = true;
        this.listPublicHealth.ItemHeight = 15;
        this.listPublicHealth.Location = new System.Drawing.Point(315, 294);
        this.listPublicHealth.Name = "listPublicHealth";
        this.listPublicHealth.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listPublicHealth.Size = new System.Drawing.Size(204, 34);
        this.listPublicHealth.TabIndex = 10;
        this.listPublicHealth.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listPublicHealth_MouseDown);
        //
        // listLists
        //
        this.listLists.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
        this.listLists.FormattingEnabled = true;
        this.listLists.ItemHeight = 15;
        this.listLists.Location = new System.Drawing.Point(315, 87);
        this.listLists.Name = "listLists";
        this.listLists.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listLists.Size = new System.Drawing.Size(204, 184);
        this.listLists.TabIndex = 9;
        this.listLists.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listLists_MouseDown);
        //
        // listMonthly
        //
        this.listMonthly.DrawMode = System.Windows.Forms.DrawMode.OwnerDrawFixed;
        this.listMonthly.FormattingEnabled = true;
        this.listMonthly.ItemHeight = 15;
        this.listMonthly.Location = new System.Drawing.Point(12, 320);
        this.listMonthly.Name = "listMonthly";
        this.listMonthly.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listMonthly.Size = new System.Drawing.Size(204, 169);
        this.listMonthly.TabIndex = 8;
        this.listMonthly.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listMonthly_MouseDown);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(558, 553);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butDashboard
        //
        this.butDashboard.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDashboard.setAutosize(true);
        this.butDashboard.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDashboard.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDashboard.setCornerRadius(4F);
        this.butDashboard.Location = new System.Drawing.Point(558, 35);
        this.butDashboard.Name = "butDashboard";
        this.butDashboard.Size = new System.Drawing.Size(75, 24);
        this.butDashboard.TabIndex = 21;
        this.butDashboard.Text = "Dashboard";
        this.butDashboard.UseVisualStyleBackColor = true;
        this.butDashboard.Click += new System.EventHandler(this.butDashboard_Click);
        //
        // menuMain
        //
        this.menuMain.Items.AddRange(new System.Windows.Forms.ToolStripItem[]{ this.setupToolStripMenuItem });
        this.menuMain.Location = new System.Drawing.Point(0, 0);
        this.menuMain.Name = "menuMain";
        this.menuMain.Size = new System.Drawing.Size(680, 24);
        this.menuMain.TabIndex = 22;
        //
        // setupToolStripMenuItem
        //
        this.setupToolStripMenuItem.Name = "setupToolStripMenuItem";
        this.setupToolStripMenuItem.Size = new System.Drawing.Size(50, 20);
        this.setupToolStripMenuItem.Text = "Setup";
        this.setupToolStripMenuItem.Click += new System.EventHandler(this.setupToolStripMenuItem_Click);
        //
        // butUDS
        //
        this.butUDS.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butUDS.setAutosize(true);
        this.butUDS.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUDS.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUDS.setCornerRadius(4F);
        this.butUDS.Location = new System.Drawing.Point(427, 35);
        this.butUDS.Name = "butUDS";
        this.butUDS.Size = new System.Drawing.Size(92, 24);
        this.butUDS.TabIndex = 18;
        this.butUDS.Text = "UDS Reporting";
        this.butUDS.UseVisualStyleBackColor = true;
        this.butUDS.Visible = false;
        this.butUDS.Click += new System.EventHandler(this.butUDS_Click);
        //
        // butPatList
        //
        this.butPatList.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPatList.setAutosize(true);
        this.butPatList.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPatList.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPatList.setCornerRadius(4F);
        this.butPatList.Location = new System.Drawing.Point(549, 87);
        this.butPatList.Name = "butPatList";
        this.butPatList.Size = new System.Drawing.Size(92, 24);
        this.butPatList.TabIndex = 23;
        this.butPatList.Text = "EHR Patient List";
        this.butPatList.Click += new System.EventHandler(this.butPatList_Click);
        //
        // butPatExport
        //
        this.butPatExport.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPatExport.setAutosize(true);
        this.butPatExport.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPatExport.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPatExport.setCornerRadius(4F);
        this.butPatExport.Location = new System.Drawing.Point(549, 117);
        this.butPatExport.Name = "butPatExport";
        this.butPatExport.Size = new System.Drawing.Size(92, 24);
        this.butPatExport.TabIndex = 24;
        this.butPatExport.Text = "EHR Pat Export";
        this.butPatExport.Visible = false;
        this.butPatExport.Click += new System.EventHandler(this.butPatExport_Click);
        //
        // FormReportsMore
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(680, 612);
        this.Controls.Add(this.butPatExport);
        this.Controls.Add(this.butPatList);
        this.Controls.Add(this.butDashboard);
        this.Controls.Add(this.labelArizonaPrimaryCare);
        this.Controls.Add(this.listArizonaPrimaryCare);
        this.Controls.Add(this.butUDS);
        this.Controls.Add(this.butLaserLabels);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.listDaily);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.listProdInc);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.butPW);
        this.Controls.Add(this.butUserQuery);
        this.Controls.Add(this.listPublicHealth);
        this.Controls.Add(this.listLists);
        this.Controls.Add(this.listMonthly);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.menuMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MainMenuStrip = this.menuMain;
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReportsMore";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reports";
        this.Load += new System.EventHandler(this.FormReportsMore_Load);
        this.menuMain.ResumeLayout(false);
        this.menuMain.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formReportsMore_Load(Object sender, EventArgs e) throws Exception {
        Plugins.hookAddCode(this,"FormReportsMore.FormReportsMore_Load_beginning");
        butPW.Visible = Programs.isEnabled(ProgramName.PracticeWebReports);
        //hiding fefature for 13.3
        //butPatList.Visible=PrefC.GetBool(PrefName.ShowFeatureEhr);
        butPatExport.Visible = PrefC.getBool(PrefName.ShowFeatureEhr);
        listProdInc.Items.AddRange(new String[]{ Lan.g(this,"Today"), Lan.g(this,"Yesterday"), Lan.g(this,"This Month"), Lan.g(this,"Last Month"), Lan.g(this,"This Year"), Lan.g(this,"More Options") });
        listDaily.Items.AddRange(new String[]{ Lan.g(this,"Adjustments"), Lan.g(this,"Payments"), Lan.g(this,"Procedures"), Lan.g(this,"Writeoffs"), Lan.g(this,"Incomplete Procedure Notes"), Lan.g(this,"Routing Slips") });
        listMonthly.Items.AddRange(new String[]{ Lan.g(this,"Aging of A/R"), Lan.g(this,"Claims Not Sent"), Lan.g(this,"Capitation Utilization"), Lan.g(this,"Finance Charge Report"), Lan.g(this,"Outstanding Insurance Claims"), Lan.g(this,"Procedures Not Billed to Insurance"), Lan.g(this,"PPO Writeoffs"), Lan.g(this,"Payment Plans"), Lan.g(this,"Receivable Breakdown"), Lan.g(this,"Unearned Income"), Lan.g(this,"Insurance Overpaid") });
        listLists.Items.AddRange(new String[]{ Lan.g(this,"Appointments"), Lan.g(this,"Birthdays"), Lan.g(this,"Insurance Plans"), Lan.g(this,"New Patients"), Lan.g(this,"Patients - Raw"), Lan.g(this,"Patients Notes"), Lan.g(this,"Prescriptions"), Lan.g(this,"Procedure Codes"), Lan.g(this,"Referrals - Raw"), Lan.g(this,"Referral Analysis"), Lan.g(this,"Referred Proc Tracking"), Lan.g(this,"Treatment Finder") });
        //Lan.g(this,"Treatment Plan Manager")//js too buggy
        listPublicHealth.Items.AddRange(new String[]{ Lan.g(this,"Raw Screening Data"), Lan.g(this,"Raw Population Data") });
        listArizonaPrimaryCare.Items.AddRange(new String[]{ Lan.g(this,"Eligibility File"), Lan.g(this,"Encounter File") });
        //Arizona primary care list and label must only be visible when the Arizona primary
        //care option is checked in the miscellaneous options.
        if (usingArizonaPrimaryCare())
        {
            labelArizonaPrimaryCare.Visible = true;
            listArizonaPrimaryCare.Visible = true;
        }
         
        //Notify user if partial batch ins payments exist.
        List<ClaimPayment> listClaimPay = ClaimPayments.GetForDateRange(DateTime.Now.AddMonths(-1), DateTime.Now, 0);
        for (int i = 0;i < listClaimPay.Count;i++)
        {
            if (listClaimPay[i].IsPartial)
            {
                MsgBox.show(this,"Reports will not be accurate until partial insurance payments are completed.");
                break;
            }
             
        }
    }

    /**
    * When using Arizona Primary Care, there must be a handful of pre-defined patient fields which are required  to generate the Arizona Primary Care reports. This function will return true if all of the required patient fields exist which are necessary to run the Arizona Primary Care reports. Otherwise, false is returned.
    */
    public static boolean usingArizonaPrimaryCare() throws Exception {
        PatFieldDefs.refreshCache();
        String[] patientFieldNames = new String[]{ "SPID#", "Eligibility Status", "Household Gross Income", "Household % of Poverty" };
        int[] fieldCounts = new int[patientFieldNames.Length];
        for (Object __dummyForeachVar0 : PatFieldDefs.getList())
        {
            PatFieldDef pfd = (PatFieldDef)__dummyForeachVar0;
            for (int i = 0;i < patientFieldNames.Length;i++)
            {
                if (pfd.FieldName.ToLower() == patientFieldNames[i].ToLower())
                {
                    fieldCounts[i]++;
                    break;
                }
                 
            }
        }
        for (int i = 0;i < fieldCounts.Length;i++)
        {
            //Each field must be defined exactly once. This verifies that each requied field
            //both exists and is not ambiguous with another field of the same name.
            if (fieldCounts[i] != 1)
            {
                return false;
            }
             
        }
        return true;
    }

    private void butUserQuery_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.UserQuery))
        {
            return ;
        }
         
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.Oracle)
        {
            MsgBox.show(this,"Not allowed while using Oracle.");
            return ;
        }
         
        FormQuery FormQuery2 = new FormQuery(null);
        FormQuery2.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.UserQuery, 0, "");
    }

    private void butPW_Click(Object sender, EventArgs e) throws Exception {
        try
        {
            Process.Start("PWReports.exe");
        }
        catch (Exception __dummyCatchVar0)
        {
            System.Windows.Forms.MessageBox.Show("PracticeWeb Reports module unavailable.");
        }

        SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Practice Web");
    }

    private void listProdInc_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        int selected = listProdInc.IndexFromPoint(e.Location);
        if (selected == -1)
        {
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.ReportProdInc))
        {
            return ;
        }
         
        FormRpProdInc FormPI = new FormRpProdInc();
        switch(selected)
        {
            case 0: 
                //Today
                FormPI.DailyMonthlyAnnual = "Daily";
                FormPI.DateStart = DateTime.Today;
                FormPI.DateEnd = DateTime.Today;
                break;
            case 1: 
                //Yesterday
                FormPI.DailyMonthlyAnnual = "Daily";
                if (DateTime.Today.DayOfWeek == DayOfWeek.Monday)
                {
                    FormPI.DateStart = DateTime.Today.AddDays(-3);
                    FormPI.DateEnd = DateTime.Today.AddDays(-3);
                }
                else
                {
                    FormPI.DateStart = DateTime.Today.AddDays(-1);
                    FormPI.DateEnd = DateTime.Today.AddDays(-1);
                } 
                break;
            case 2: 
                //This Month
                FormPI.DailyMonthlyAnnual = "Monthly";
                FormPI.DateStart = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1);
                FormPI.DateEnd = (new DateTime(DateTime.Today.AddMonths(1).Year, DateTime.Today.AddMonths(1).Month, 1)).AddDays(-1);
                break;
            case 3: 
                //Last Month
                FormPI.DailyMonthlyAnnual = "Monthly";
                FormPI.DateStart = new DateTime(DateTime.Today.AddMonths(-1).Year, DateTime.Today.AddMonths(-1).Month, 1);
                FormPI.DateEnd = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1)).AddDays(-1);
                break;
            case 4: 
                //This Year
                FormPI.DailyMonthlyAnnual = "Annual";
                FormPI.DateStart = new DateTime(DateTime.Today.Year, 1, 1);
                FormPI.DateEnd = new DateTime(DateTime.Today.Year, 12, 31);
                break;
            case 5: 
                break;
        
        }
        //More Options
        //do nothing
        FormPI.ShowDialog();
        SecurityLogs.MakeLogEntry(Permissions.ReportProdInc, 0, "");
    }

    private void listDaily_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        int selected = listDaily.IndexFromPoint(e.Location);
        if (selected == -1)
        {
            return ;
        }
         
        switch(selected)
        {
            case 0: 
                //Adjustments
                FormRpAdjSheet FormAdjSheet = new FormRpAdjSheet();
                FormAdjSheet.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Adjustments");
                break;
            case 1: 
                //Payments
                FormRpPaySheet FormPaySheet = new FormRpPaySheet();
                FormPaySheet.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Daily Payments");
                break;
            case 2: 
                //Procedures
                FormRpProcSheet FormProcSheet = new FormRpProcSheet();
                FormProcSheet.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Daily Procedures");
                break;
            case 3: 
                //Writeoffs
                FormRpWriteoffSheet FormW = new FormRpWriteoffSheet();
                FormW.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Daily Writeoffs");
                break;
            case 4: 
                //Incomplete Procedure Notes
                FormRpProcNote FormPN = new FormRpProcNote();
                FormPN.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Daily Procedure Notes");
                break;
            case 5: 
                //Routing Slips
                FormRpRouting FormR = new FormRpRouting();
                FormR.ShowDialog();
                break;
        
        }
    }

    private void listMonthly_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        int selected = listMonthly.IndexFromPoint(e.Location);
        if (selected == -1)
        {
            return ;
        }
         
        switch(selected)
        {
            case 0: 
                //Aging of Accounts Receivable Report
                if (!Security.isAuthorized(Permissions.ReportProdInc))
                {
                    return ;
                }
                 
                FormRpAging FormA = new FormRpAging();
                FormA.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Aging of A/R");
                break;
            case 1: 
                //Claims Not Sent
                FormRpClaimNotSent FormClaim = new FormRpClaimNotSent();
                FormClaim.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Claims Not Sent");
                break;
            case 2: 
                //Capitation Utilization
                FormRpCapitation FormC = new FormRpCapitation();
                FormC.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Capitation");
                break;
            case 3: 
                //Finance Charge Report
                FormRpFinanceCharge FormRpFinance = new FormRpFinanceCharge();
                FormRpFinance.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Finance Charges");
                break;
            case 4: 
                //Outstanding Insurance Claims
                RpModalSelection = ReportModalSelection.OutstandingIns;
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Outstanding Insurance Claims");
                Close();
                break;
            case 5: 
                //Procedures Not Billed to Insurance
                FormRpProcNotBilledIns FormProc = new FormRpProcNotBilledIns();
                FormProc.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Procedures not billed to insurance.");
                break;
            case 6: 
                //PPO Writeoffs
                FormRpPPOwriteoffs FormPPO = new FormRpPPOwriteoffs();
                FormPPO.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "PPO Writeoffs.");
                break;
            case 7: 
                //Payment Plans
                FormRpPayPlans FormPP = new FormRpPayPlans();
                FormPP.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Payment Plans.");
                break;
            case 8: 
                //Receivable Breakdown
                if (!Security.isAuthorized(Permissions.ReportProdInc))
                {
                    return ;
                }
                 
                FormRpReceivablesBreakdown FormRcv = new FormRpReceivablesBreakdown();
                FormRcv.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Receivable Breakdown.");
                break;
            case 9: 
                //Unearned Income
                FormRpUnearnedIncome FormU = new FormRpUnearnedIncome();
                FormU.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Unearned Income.");
                break;
            case 10: 
                //Insurance Overpaid
                FormRpInsOverpaid FormI = new FormRpInsOverpaid();
                FormI.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Insurance Overpaid.");
                break;
        
        }
    }

    private void listLists_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        int selected = listLists.IndexFromPoint(e.Location);
        if (selected == -1)
        {
            return ;
        }
         
        switch(selected)
        {
            case 0: 
                //Appointments
                FormRpAppointments FormA = new FormRpAppointments();
                FormA.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Appointments");
                break;
            case 1: 
                //Birthdays
                FormRpBirthday FormB = new FormRpBirthday();
                FormB.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Birthdays");
                break;
            case 2: 
                //Insurance Plans
                FormRpInsCo FormInsCo = new FormRpInsCo();
                FormInsCo.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Insurance Plans");
                break;
            case 3: 
                //New Patients
                FormRpNewPatients FormNewPats = new FormRpNewPatients();
                FormNewPats.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "New Patients");
                break;
            case 4: 
                //Patients - Raw
                if (!Security.isAuthorized(Permissions.UserQuery))
                {
                    return ;
                }
                 
                FormRpPatients FormPatients = new FormRpPatients();
                FormPatients.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Patients - Raw");
                break;
            case 5: 
                //Patient Notes
                if (!Security.isAuthorized(Permissions.UserQuery))
                {
                    return ;
                }
                 
                FormSearchPatNotes FormPN = new FormSearchPatNotes();
                FormPN.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Patient Notes");
                break;
            case 6: 
                //Prescriptions
                FormRpPrescriptions FormPrescript = new FormRpPrescriptions();
                FormPrescript.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Rx");
                break;
            case 7: 
                //Procedure Codes
                FormRpProcCodes FormProcCodes = new FormRpProcCodes();
                FormProcCodes.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Procedure Codes");
                break;
            case 8: 
                //Referrals - Raw
                if (!Security.isAuthorized(Permissions.UserQuery))
                {
                    return ;
                }
                 
                FormRpReferrals FormReferral = new FormRpReferrals();
                FormReferral.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Referrals - Raw");
                break;
            case 9: 
                //Referral Analysis
                FormRpReferralAnalysis FormRA = new FormRpReferralAnalysis();
                FormRA.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Referral Analysis");
                break;
            case 10: 
                //Referred Proc Tracking
                FormReferralProcTrack FormRP = new FormReferralProcTrack();
                FormRP.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "ReferredProcTracking");
                Close();
                break;
            case 11: 
                //Treatment Finder
                RpModalSelection = ReportModalSelection.TreatmentFinder;
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Treatment Finder");
                Close();
                break;
        
        }
    }

    //case 12://Treatment Plan Manager
    //  FormTxPlanManager FormTM=new FormTxPlanManager();
    //  FormTM.ShowDialog();
    //  SecurityLogs.MakeLogEntry(Permissions.Reports,0,"Treatment Plan Manager");
    //  break;
    private void listPublicHealth_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        int selected = listPublicHealth.IndexFromPoint(e.Location);
        if (selected == -1)
        {
            return ;
        }
         
        switch(selected)
        {
            case 0: 
                //Raw Screening Data
                if (!Security.isAuthorized(Permissions.UserQuery))
                {
                    return ;
                }
                 
                FormRpPHRawScreen FormPH = new FormRpPHRawScreen();
                FormPH.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "PH Raw Screening");
                break;
            case 1: 
                //Raw Population Data
                if (!Security.isAuthorized(Permissions.UserQuery))
                {
                    return ;
                }
                 
                FormRpPHRawPop FormPHR = new FormRpPHRawPop();
                FormPHR.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "PH Raw population");
                break;
        
        }
    }

    private void listArizonaPrimaryCare_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        int selected = this.listArizonaPrimaryCare.IndexFromPoint(e.Location);
        if (selected == -1)
        {
            return ;
        }
         
        switch(selected)
        {
            case 0: 
                //Elegibility File
                FormRpArizonaPrimaryCareEligibility frapce = new FormRpArizonaPrimaryCareEligibility();
                frapce.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Arizona Primary Care Eligibility");
                break;
            case 1: 
                //Encounter File
                FormRpArizonaPrimaryCareEncounter frapcn = new FormRpArizonaPrimaryCareEncounter();
                frapcn.ShowDialog();
                SecurityLogs.MakeLogEntry(Permissions.Reports, 0, "Arizona Primary Care Encounter");
                break;
        
        }
    }

    private void butLaserLabels_Click(Object sender, EventArgs e) throws Exception {
        FormRpLaserLabels LaserLabels = new FormRpLaserLabels();
        LaserLabels.ShowDialog();
    }

    private void butUDS_Click(Object sender, EventArgs e) throws Exception {
        //Recommend checking for user query permission, unless bringing up the preview window first, then the query view button prevents user from accessing user queries.
        //if(!Security.IsAuthorized(Permissions.UserQuery)) {
        //  return;
        //}
        //not visible
        FormReportsUds FormRU = new FormReportsUds();
        FormRU.ShowDialog();
    }

    private void butDashboard_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.ReportDashboard))
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        WinDashboard win = new WinDashboard();
        win.Show();
        Cursor = Cursors.Default;
    }

    private void setupToolStripMenuItem_Click(Object sender, EventArgs e) throws Exception {
        FormReportSetup formRS = new FormReportSetup();
        formRS.ShowDialog();
    }

    private void butPatList_Click(Object sender, EventArgs e) throws Exception {
        FormPatListEHR2014 FormPL = new FormPatListEHR2014();
        FormPL.ShowDialog();
    }

    private void butPatExport_Click(Object sender, EventArgs e) throws Exception {
        FormEhrPatientExport FormEhrPE = new FormEhrPatientExport();
        FormEhrPE.ShowDialog();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


