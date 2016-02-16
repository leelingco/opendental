//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpPatients;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.ProviderC;

/**
* 
*/
public class FormRpPatients  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.TabControl tabPatients = new System.Windows.Forms.TabControl();
    private System.Windows.Forms.TabPage tabFilters = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.TabPage tabData = new System.Windows.Forms.TabPage();
    private System.Windows.Forms.ListBox ComboBox = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox ListPatientSelect = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox ListPrerequisites = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox ListReferredFromSelect = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox ListReferredToSelect = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox ListConditions = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ComboBox DropListFilter = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butAddFilter;
    private OpenDental.UI.Button butDeleteFilter;
    private System.Windows.Forms.TextBox TextSQL = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox TextBox = new System.Windows.Forms.TextBox();
    private OpenDental.ValidNumber TextValidAge;
    private OpenDental.ValidDate TextDate;
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelReferredTo = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelReferredFrom = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelHelp = new System.Windows.Forms.Label();
    //fields used in SELECT
    private System.ComponentModel.IContainer components = null;
    private FormQuery FormQuery2;
    private String SQLselect = new String();
    private String SQLfrom = new String();
    private String SQLwhereComparison = new String();
    private String SQLwhereRelation = new String();
    private String SQLgroup = new String();
    private String sItem = new String();
    //just used in local loops
    private String ProcLogLastDate = new String();
    private String ProcLogFirstDate = new String();
    private String[] PatFieldsSelected = new String[]();
    private String[] RefToFieldsSelected = new String[]();
    private String[] RefFromFieldsSelected = new String[]();
    private ArrayList ALpatFilter = new ArrayList();
    private ArrayList ALpatSelect = new ArrayList();
    private ArrayList ALrefToSelect = new ArrayList();
    private ArrayList ALrefFromSelect = new ArrayList();
    private ArrayList UsingInsPlans = new ArrayList();
    //this is outdated.
    private ArrayList UsingProcLogFirst = new ArrayList();
    private ArrayList UsingProcLogLast = new ArrayList();
    private ArrayList UsingRefDent = new ArrayList();
    private ArrayList UsingRefPat = new ArrayList();
    private ArrayList UsingRecall = new ArrayList();
    private boolean IsText = new boolean();
    private boolean IsDate = new boolean();
    private boolean IsDropDown = new boolean();
    private boolean NeedInsPlan = false;
    private boolean NeedRefDent = false;
    private boolean NeedRefPat = false;
    private boolean NeedProcLogLast = false;
    private boolean NeedProcLogFirst = false;
    private boolean NeedRecall = false;
    private boolean IsWhereRelation = false;
    private boolean PatSel = new boolean();
    private boolean RefToSel = new boolean();
    private System.Windows.Forms.TextBox textBox1 = new System.Windows.Forms.TextBox();
    private boolean RefFromSel = new boolean();
    /**
    * 
    */
    public FormRpPatients() throws Exception {
        initializeComponent();
        ALpatSelect = new ArrayList();
        ALpatFilter = new ArrayList();
        ALrefToSelect = new ArrayList();
        ALrefFromSelect = new ArrayList();
        UsingInsPlans = new ArrayList();
        UsingRefDent = new ArrayList();
        UsingRefPat = new ArrayList();
        UsingProcLogFirst = new ArrayList();
        UsingProcLogLast = new ArrayList();
        UsingRecall = new ArrayList();
        fill();
        SQLselect = "";
        SQLfrom = "FROM patient ";
        SQLwhereComparison = "";
        SQLwhereRelation = "";
        SQLgroup = "";
        ListConditions.SelectedIndex = 0;
        IsText = false;
        IsDate = false;
        IsDropDown = false;
        TextValidAge.setMinVal(0);
        TextValidAge.setMaxVal(125);
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpPatients.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.tabPatients = new System.Windows.Forms.TabControl();
        this.tabData = new System.Windows.Forms.TabPage();
        this.textBox1 = new System.Windows.Forms.TextBox();
        this.labelReferredFrom = new System.Windows.Forms.Label();
        this.ListReferredFromSelect = new System.Windows.Forms.ListBox();
        this.labelReferredTo = new System.Windows.Forms.Label();
        this.ListReferredToSelect = new System.Windows.Forms.ListBox();
        this.labelPatient = new System.Windows.Forms.Label();
        this.ListPatientSelect = new System.Windows.Forms.ListBox();
        this.tabFilters = new System.Windows.Forms.TabPage();
        this.labelHelp = new System.Windows.Forms.Label();
        this.ComboBox = new System.Windows.Forms.ListBox();
        this.TextDate = new OpenDental.ValidDate();
        this.TextValidAge = new OpenDental.ValidNumber();
        this.butDeleteFilter = new OpenDental.UI.Button();
        this.ListPrerequisites = new System.Windows.Forms.ListBox();
        this.butAddFilter = new OpenDental.UI.Button();
        this.ListConditions = new System.Windows.Forms.ListBox();
        this.TextBox = new System.Windows.Forms.TextBox();
        this.DropListFilter = new System.Windows.Forms.ComboBox();
        this.TextSQL = new System.Windows.Forms.TextBox();
        this.tabPatients.SuspendLayout();
        this.tabData.SuspendLayout();
        this.tabFilters.SuspendLayout();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(876, 664);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 3;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Enabled = false;
        this.butOK.Location = new System.Drawing.Point(876, 630);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // tabPatients
        //
        this.tabPatients.Controls.Add(this.tabData);
        this.tabPatients.Controls.Add(this.tabFilters);
        this.tabPatients.Location = new System.Drawing.Point(16, 6);
        this.tabPatients.Name = "tabPatients";
        this.tabPatients.SelectedIndex = 0;
        this.tabPatients.Size = new System.Drawing.Size(840, 544);
        this.tabPatients.TabIndex = 1;
        //
        // tabData
        //
        this.tabData.Controls.Add(this.textBox1);
        this.tabData.Controls.Add(this.labelReferredFrom);
        this.tabData.Controls.Add(this.ListReferredFromSelect);
        this.tabData.Controls.Add(this.labelReferredTo);
        this.tabData.Controls.Add(this.ListReferredToSelect);
        this.tabData.Controls.Add(this.labelPatient);
        this.tabData.Controls.Add(this.ListPatientSelect);
        this.tabData.Location = new System.Drawing.Point(4, 22);
        this.tabData.Name = "tabData";
        this.tabData.Size = new System.Drawing.Size(832, 518);
        this.tabData.TabIndex = 1;
        this.tabData.Text = "SELECT";
        //
        // textBox1
        //
        this.textBox1.BackColor = System.Drawing.SystemColors.Control;
        this.textBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
        this.textBox1.Location = new System.Drawing.Point(220, 20);
        this.textBox1.Multiline = true;
        this.textBox1.Name = "textBox1";
        this.textBox1.Size = new System.Drawing.Size(556, 38);
        this.textBox1.TabIndex = 13;
        this.textBox1.Text = resources.GetString("textBox1.Text");
        //
        // labelReferredFrom
        //
        this.labelReferredFrom.Location = new System.Drawing.Point(438, 72);
        this.labelReferredFrom.Name = "labelReferredFrom";
        this.labelReferredFrom.Size = new System.Drawing.Size(170, 14);
        this.labelReferredFrom.TabIndex = 12;
        this.labelReferredFrom.Text = "Referred From";
        this.labelReferredFrom.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // ListReferredFromSelect
        //
        this.ListReferredFromSelect.Location = new System.Drawing.Point(438, 86);
        this.ListReferredFromSelect.Name = "ListReferredFromSelect";
        this.ListReferredFromSelect.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.ListReferredFromSelect.Size = new System.Drawing.Size(170, 420);
        this.ListReferredFromSelect.TabIndex = 11;
        this.ListReferredFromSelect.SelectedIndexChanged += new System.EventHandler(this.ListReferredFromSelect_SelectedIndexChanged);
        //
        // labelReferredTo
        //
        this.labelReferredTo.Location = new System.Drawing.Point(220, 72);
        this.labelReferredTo.Name = "labelReferredTo";
        this.labelReferredTo.Size = new System.Drawing.Size(168, 14);
        this.labelReferredTo.TabIndex = 8;
        this.labelReferredTo.Text = "Referred To";
        this.labelReferredTo.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // ListReferredToSelect
        //
        this.ListReferredToSelect.Location = new System.Drawing.Point(220, 86);
        this.ListReferredToSelect.Name = "ListReferredToSelect";
        this.ListReferredToSelect.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.ListReferredToSelect.Size = new System.Drawing.Size(168, 420);
        this.ListReferredToSelect.TabIndex = 7;
        this.ListReferredToSelect.SelectedIndexChanged += new System.EventHandler(this.ListReferredToSelect_SelectedIndexChanged);
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(8, 8);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(170, 14);
        this.labelPatient.TabIndex = 4;
        this.labelPatient.Text = "Patient";
        this.labelPatient.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // ListPatientSelect
        //
        this.ListPatientSelect.Location = new System.Drawing.Point(8, 22);
        this.ListPatientSelect.Name = "ListPatientSelect";
        this.ListPatientSelect.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.ListPatientSelect.Size = new System.Drawing.Size(170, 485);
        this.ListPatientSelect.TabIndex = 3;
        this.ListPatientSelect.SelectedIndexChanged += new System.EventHandler(this.ListPatientSelect_SelectedIndexChanged);
        //
        // tabFilters
        //
        this.tabFilters.Controls.Add(this.labelHelp);
        this.tabFilters.Controls.Add(this.ComboBox);
        this.tabFilters.Controls.Add(this.TextDate);
        this.tabFilters.Controls.Add(this.TextValidAge);
        this.tabFilters.Controls.Add(this.butDeleteFilter);
        this.tabFilters.Controls.Add(this.ListPrerequisites);
        this.tabFilters.Controls.Add(this.butAddFilter);
        this.tabFilters.Controls.Add(this.ListConditions);
        this.tabFilters.Controls.Add(this.TextBox);
        this.tabFilters.Controls.Add(this.DropListFilter);
        this.tabFilters.Location = new System.Drawing.Point(4, 22);
        this.tabFilters.Name = "tabFilters";
        this.tabFilters.Size = new System.Drawing.Size(832, 518);
        this.tabFilters.TabIndex = 0;
        this.tabFilters.Text = "WHERE";
        //
        // labelHelp
        //
        this.labelHelp.Location = new System.Drawing.Point(360, 14);
        this.labelHelp.Name = "labelHelp";
        this.labelHelp.Size = new System.Drawing.Size(262, 18);
        this.labelHelp.TabIndex = 13;
        //
        // ComboBox
        //
        this.ComboBox.Location = new System.Drawing.Point(360, 40);
        this.ComboBox.Name = "ComboBox";
        this.ComboBox.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.ComboBox.Size = new System.Drawing.Size(262, 121);
        this.ComboBox.TabIndex = 12;
        this.ComboBox.Visible = false;
        this.ComboBox.SelectedIndexChanged += new System.EventHandler(this.ComboBox_SelectedIndexChanged);
        //
        // TextDate
        //
        this.TextDate.Location = new System.Drawing.Point(360, 40);
        this.TextDate.Name = "TextDate";
        this.TextDate.Size = new System.Drawing.Size(262, 20);
        this.TextDate.TabIndex = 11;
        //
        // TextValidAge
        //
        this.TextValidAge.Location = new System.Drawing.Point(360, 40);
        this.TextValidAge.setMaxVal(255);
        this.TextValidAge.setMinVal(0);
        this.TextValidAge.Name = "TextValidAge";
        this.TextValidAge.Size = new System.Drawing.Size(262, 20);
        this.TextValidAge.TabIndex = 10;
        this.TextValidAge.Visible = false;
        //
        // butDeleteFilter
        //
        this.butDeleteFilter.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDeleteFilter.setAutosize(true);
        this.butDeleteFilter.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDeleteFilter.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDeleteFilter.setCornerRadius(4F);
        this.butDeleteFilter.Enabled = false;
        this.butDeleteFilter.Image = ((System.Drawing.Image)(resources.GetObject("butDeleteFilter.Image")));
        this.butDeleteFilter.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDeleteFilter.Location = new System.Drawing.Point(10, 420);
        this.butDeleteFilter.Name = "butDeleteFilter";
        this.butDeleteFilter.RightToLeft = System.Windows.Forms.RightToLeft.No;
        this.butDeleteFilter.Size = new System.Drawing.Size(108, 26);
        this.butDeleteFilter.TabIndex = 8;
        this.butDeleteFilter.Text = "      Delete Row";
        this.butDeleteFilter.Click += new System.EventHandler(this.butDeleteFilter_Click);
        //
        // ListPrerequisites
        //
        this.ListPrerequisites.Location = new System.Drawing.Point(10, 200);
        this.ListPrerequisites.Name = "ListPrerequisites";
        this.ListPrerequisites.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.ListPrerequisites.Size = new System.Drawing.Size(608, 212);
        this.ListPrerequisites.TabIndex = 7;
        this.ListPrerequisites.SelectedIndexChanged += new System.EventHandler(this.ListPrerequisites_SelectedIndexChanged);
        //
        // butAddFilter
        //
        this.butAddFilter.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddFilter.setAutosize(true);
        this.butAddFilter.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddFilter.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddFilter.setCornerRadius(4F);
        this.butAddFilter.Enabled = false;
        this.butAddFilter.Location = new System.Drawing.Point(664, 40);
        this.butAddFilter.Name = "butAddFilter";
        this.butAddFilter.Size = new System.Drawing.Size(75, 24);
        this.butAddFilter.TabIndex = 6;
        this.butAddFilter.Text = "Add";
        this.butAddFilter.Click += new System.EventHandler(this.butAddFilter_Click);
        //
        // ListConditions
        //
        this.ListConditions.Items.AddRange(new Object[]{ "LIKE", "=", ">", "<", ">=", "<=", "<>" });
        this.ListConditions.Location = new System.Drawing.Point(232, 40);
        this.ListConditions.Name = "ListConditions";
        this.ListConditions.Size = new System.Drawing.Size(78, 95);
        this.ListConditions.TabIndex = 5;
        //
        // TextBox
        //
        this.TextBox.Location = new System.Drawing.Point(360, 40);
        this.TextBox.Name = "TextBox";
        this.TextBox.Size = new System.Drawing.Size(262, 20);
        this.TextBox.TabIndex = 2;
        this.TextBox.Visible = false;
        //
        // DropListFilter
        //
        this.DropListFilter.Location = new System.Drawing.Point(8, 40);
        this.DropListFilter.MaxDropDownItems = 45;
        this.DropListFilter.Name = "DropListFilter";
        this.DropListFilter.Size = new System.Drawing.Size(172, 21);
        this.DropListFilter.TabIndex = 1;
        this.DropListFilter.Text = "WHERE";
        this.DropListFilter.SelectedIndexChanged += new System.EventHandler(this.DropListFilter_SelectedIndexChanged);
        //
        // TextSQL
        //
        this.TextSQL.Location = new System.Drawing.Point(18, 560);
        this.TextSQL.Multiline = true;
        this.TextSQL.Name = "TextSQL";
        this.TextSQL.ReadOnly = true;
        this.TextSQL.Size = new System.Drawing.Size(840, 128);
        this.TextSQL.TabIndex = 38;
        //
        // FormRpPatients
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(962, 700);
        this.Controls.Add(this.tabPatients);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.TextSQL);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpPatients";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Patients Report";
        this.tabPatients.ResumeLayout(false);
        this.tabData.ResumeLayout(false);
        this.tabData.PerformLayout();
        this.tabFilters.ResumeLayout(false);
        this.tabFilters.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void fill() throws Exception {
        fillALpatSelect();
        fillALpatFilter();
        fillALrefToSelect();
        fillALrefFromSelect();
        fillRefToSelectList();
        fillRefFromSelectList();
        fillPatientSelectList();
        fillFilterDropList();
    }

    private void fillALpatSelect() throws Exception {
        ALpatSelect.Add("PatNum");
        ALpatSelect.Add("LName");
        ALpatSelect.Add("FName");
        ALpatSelect.Add("MiddleI");
        ALpatSelect.Add("Preferred");
        ALpatSelect.Add("Salutation");
        ALpatSelect.Add("Address");
        ALpatSelect.Add("Address2");
        ALpatSelect.Add("City");
        ALpatSelect.Add("State");
        ALpatSelect.Add("Zip");
        ALpatSelect.Add("HmPhone");
        ALpatSelect.Add("WkPhone");
        ALpatSelect.Add("WirelessPhone");
        ALpatSelect.Add("Birthdate");
        ALpatSelect.Add("Email");
        ALpatSelect.Add("SSN");
        ALpatSelect.Add("Gender");
        ALpatSelect.Add("PatStatus");
        ALpatSelect.Add("Position");
        ALpatSelect.Add("CreditType");
        ALpatSelect.Add("BillingType");
        ALpatSelect.Add("ChartNumber");
        ALpatSelect.Add("PriProv");
        ALpatSelect.Add("SecProv");
        ALpatSelect.Add("FeeSched");
        ALpatSelect.Add("ApptModNote");
        ALpatSelect.Add("AddrNote");
        ALpatSelect.Add("EstBalance");
        ALpatSelect.Add("FamFinUrgNote");
        ALpatSelect.Add("Guarantor");
        ALpatSelect.Add("ImageFolder");
        ALpatSelect.Add("MedUrgNote");
        //ALpatSelect.Add("NextAptNum");
        //ALpatSelect.Add("PriPlanNum");//Primary Carrier?
        //ALpatSelect.Add("PriRelationship");// ?
        //ALpatSelect.Add("SecPlanNum");//Secondary Carrier?
        //ALpatSelect.Add("SecRelationship");// ?
        //ALpatSelect.Add("RecallInterval"));
        ALpatSelect.Add("RecallStatus");
        ALpatSelect.Add("SchoolName");
        ALpatSelect.Add("StudentStatus");
        ALpatSelect.Add("MedicaidID");
        ALpatSelect.Add("Bal_0_30");
        ALpatSelect.Add("Bal_31_60");
        ALpatSelect.Add("Bal_61_90");
        ALpatSelect.Add("BalOver90");
        ALpatSelect.Add("InsEst");
        //ALpatSelect.Add("PrimaryTeeth");
        ALpatSelect.Add("BalTotal");
        ALpatSelect.Add("EmployerNum");
        //EmploymentNote
        //ALpatSelect.Add("Race"); //This column is depricated.
        ALpatSelect.Add("County");
        //ALpatSelect.Add("GradeSchool");
        ALpatSelect.Add("GradeLevel");
        ALpatSelect.Add("Urgency");
        ALpatSelect.Add("DateFirstVisit");
        //ALpatSelect.Add("PriPending");
        //ALpatSelect.Add("SecPending");
        ALpatSelect.Add("ClinicNum");
        ALpatSelect.Add("HasIns");
        ALpatSelect.Add("TrophyFolder");
        //ALpatSelect.Add("PlannedIsDone");
        //ALpatSelect.Add("PreMed");
        ALpatSelect.Add("Ward");
        //ALpatSelect.Add("PreferConfirmMethod");
        //ALpatSelect.Add("PreferContactMethod");
        //ALpatSelect.Add("PreferRecallMethod");
        //ALpatSelect.Add("SchedBeforeTime");
        //ALpatSelect.Add("SchedAfterTime");
        //ALpatSelect.Add("SchedDayOfWeek");
        //ALpatSelect.Add("Language");
        ALpatSelect.Add("AdmitDate");
        ALpatSelect.Add("Title");
        ALpatSelect.Add("PayPlanDue");
        ALpatSelect.Add("SiteNum");
        ALpatSelect.Add("DateTStamp");
        ALpatSelect.Add("ResponsParty");
    }

    //ALpatSelect.Add("CanadianEligibilityCode");
    //ALpatSelect.Add("AskToArriveEarly");
    private void fillALrefToSelect() throws Exception {
        ALrefToSelect.Add("LName");
        ALrefToSelect.Add("FName");
        ALrefToSelect.Add("MName");
        ALrefToSelect.Add("Title");
        ALrefToSelect.Add("Address");
        ALrefToSelect.Add("Address2");
        ALrefToSelect.Add("City");
        ALrefToSelect.Add("ST");
        ALrefToSelect.Add("Zip");
        ALrefToSelect.Add("Telephone");
        ALrefToSelect.Add("Phone2");
        ALrefToSelect.Add("Email");
        ALrefToSelect.Add("IsHidden");
        ALrefToSelect.Add("NotPerson");
        ALrefToSelect.Add("PatNum");
        ALrefToSelect.Add("ReferralNum");
        ALrefToSelect.Add("Specialty");
        ALrefToSelect.Add("SSN");
        ALrefToSelect.Add("UsingTIN");
        ALrefToSelect.Add("Note");
    }

    private void fillALrefFromSelect() throws Exception {
        ALrefFromSelect.Add("LName");
        ALrefFromSelect.Add("FName");
        ALrefFromSelect.Add("MName");
        ALrefFromSelect.Add("Title");
        ALrefFromSelect.Add("Address");
        ALrefFromSelect.Add("Address2");
        ALrefFromSelect.Add("City");
        ALrefFromSelect.Add("ST");
        ALrefFromSelect.Add("Zip");
        ALrefFromSelect.Add("Telephone");
        ALrefFromSelect.Add("Phone2");
        ALrefFromSelect.Add("Email");
        ALrefFromSelect.Add("IsHidden");
        ALrefFromSelect.Add("NotPerson");
        ALrefFromSelect.Add("PatNum");
        ALrefFromSelect.Add("ReferralNum");
        ALrefFromSelect.Add("Specialty");
        ALrefFromSelect.Add("SSN");
        ALrefFromSelect.Add("UsingTIN");
        ALrefFromSelect.Add("Note");
    }

    private void fillALpatFilter() throws Exception {
        //FillALpatFilter
        ALpatFilter.Add("Address");
        ALpatFilter.Add("Address2");
        ALpatFilter.Add("Age");
        ALpatFilter.Add("ApptModNote");
        ALpatFilter.Add("BillingType");
        ALpatFilter.Add("Birthdate");
        ALpatFilter.Add("Birthday");
        //new, need to add functionality
        ALpatFilter.Add("City");
        ALpatFilter.Add("ChartNumber");
        ALpatFilter.Add("CreditType");
        ALpatFilter.Add("Email");
        ALpatFilter.Add("EstBalance");
        ALpatFilter.Add("FamAddrNote");
        ALpatFilter.Add("FamFinUrgNote");
        ALpatFilter.Add("FeeSched");
        ALpatFilter.Add("First Visit Date");
        //new, need to add functionality
        ALpatFilter.Add("FName");
        ALpatFilter.Add("Gender");
        ALpatFilter.Add("HmPhone");
        ALpatFilter.Add("Last Visit Date");
        //new, need to add functionality
        ALpatFilter.Add("LName");
        ALpatFilter.Add("MedUrgNote");
        ALpatFilter.Add("MiddleI");
        ALpatFilter.Add("NextAptNum");
        ALpatFilter.Add("PatNum");
        ALpatFilter.Add("PatStatus");
        ALpatFilter.Add("Position");
        ALpatFilter.Add("Preferred");
        //ALpatFilter.Add("Primary Carrier");
        //ALpatFilter.Add("PriProv");
        //ALpatFilter.Add("PriRelationship");
        //ALpatFilter.Add("RecallInterval");
        ALpatFilter.Add("RecallStatus");
        ALpatFilter.Add("Referred From Dentist");
        //new, need to add functionality
        ALpatFilter.Add("Referred From Patient");
        //new, need to add functionality
        ALpatFilter.Add("Salutation");
        //ALpatFilter.Add("Secondary Carrier");
        ALpatFilter.Add("SecProv");
        ALpatFilter.Add("SecRelationship");
        ALpatFilter.Add("SchoolName");
        ALpatFilter.Add("SSN");
        ALpatFilter.Add("State");
        ALpatFilter.Add("StudentStatus");
        ALpatFilter.Add("WirelessPhone");
        ALpatFilter.Add("WkPhone");
        ALpatFilter.Add("Zip");
    }

    private void fillPatientSelectList() throws Exception {
        for (int i = 0;i < ALpatSelect.Count;i++)
        {
            ListPatientSelect.Items.Add(ALpatSelect[i]);
        }
        SQLselect = "";
    }

    private void fillRefToSelectList() throws Exception {
        for (int i = 0;i < ALrefToSelect.Count;i++)
        {
            ListReferredToSelect.Items.Add(ALrefToSelect[i]);
        }
        SQLselect = "";
    }

    private void fillRefFromSelectList() throws Exception {
        for (int i = 0;i < ALrefFromSelect.Count;i++)
        {
            ListReferredFromSelect.Items.Add(ALrefFromSelect[i]);
        }
        SQLselect = "";
    }

    private void fillFilterDropList() throws Exception {
        for (int i = 0;i < ALpatFilter.Count;i++)
        {
            DropListFilter.Items.Add(ALpatFilter[i]);
        }
    }

    private void fillSQLbox() throws Exception {
        TextSQL.Text = SQLselect + SQLfrom + SQLwhereRelation + SQLwhereComparison + SQLgroup;
    }

    private void createSQL() throws Exception {
        getTablesNeeded();
        createSQLselect();
        createSQLfrom();
        createSQLwhereRelation();
        createSQLwhereComparison();
        createSQLgroup();
        fillSQLbox();
    }

    private void getTablesNeeded() throws Exception {
        IsWhereRelation = false;
        NeedInsPlan = false;
        NeedRefDent = false;
        NeedRefPat = false;
        NeedProcLogFirst = false;
        NeedProcLogLast = false;
        NeedRecall = false;
        for (int i = 0;i < UsingInsPlans.Count;i++)
        {
            if ((boolean)UsingInsPlans[i])
            {
                NeedInsPlan = true;
                IsWhereRelation = true;
            }
            else if ((boolean)UsingRefDent[i])
            {
                NeedRefDent = true;
                IsWhereRelation = true;
            }
            else if ((boolean)UsingRefPat[i])
            {
                NeedRefPat = true;
                IsWhereRelation = true;
            }
            else if ((boolean)UsingProcLogFirst[i])
            {
                NeedProcLogFirst = true;
                IsWhereRelation = true;
            }
            else if ((boolean)UsingProcLogLast[i])
            {
                NeedProcLogLast = true;
                IsWhereRelation = true;
            }
            else if ((boolean)UsingRecall[i])
            {
                NeedRecall = true;
                IsWhereRelation = true;
            }
                  
        }
        for (int i = 0;i < ListPatientSelect.SelectedItems.Count;i++)
        {
            //end for
            String item = ListPatientSelect.SelectedItems[i].ToString();
            if (StringSupport.equals(item, "RecallStatus"))
            {
                NeedRecall = true;
                IsWhereRelation = true;
            }
             
        }
    }

    private void createSQLselect() throws Exception {
        PatSel = false;
        RefToSel = false;
        RefFromSel = false;
        SQLselect = "";
        PatFieldsSelected = new String[ListPatientSelect.SelectedItems.Count];
        RefToFieldsSelected = new String[ListReferredToSelect.SelectedItems.Count];
        RefFromFieldsSelected = new String[ListReferredFromSelect.SelectedItems.Count];
        if (ListPatientSelect.SelectedItems.Count > 0)
        {
            PatSel = true;
            SQLselect = "SELECT ";
            ListPatientSelect.SelectedItems.CopyTo(PatFieldsSelected, 0);
            for (int i = 0;i < PatFieldsSelected.Length;i++)
            {
                String field = PatFieldsSelected[i].ToString();
                if (StringSupport.equals(field, "RecallStatus"))
                {
                    SQLselect += "recall";
                }
                else
                {
                    SQLselect += "patient";
                } 
                SQLselect += "." + field;
                if (i != PatFieldsSelected.Length - 1)
                {
                    SQLselect += ",";
                }
                else
                {
                    SQLselect += " ";
                } 
            }
            butOK.Enabled = true;
        }
        else
        {
            SQLselect = "";
            butOK.Enabled = false;
        } 
        if (ListReferredToSelect.SelectedItems.Count > 0)
        {
            RefToSel = true;
            if (PatSel == false)
            {
                SQLselect = "SELECT ";
            }
            else
            {
                SQLselect += ",";
            } 
            ListReferredToSelect.SelectedItems.CopyTo(RefToFieldsSelected, 0);
            for (int i = 0;i < RefToFieldsSelected.Length;i++)
            {
                if (i != RefToFieldsSelected.Length - 1)
                {
                    SQLselect += "referral." + RefToFieldsSelected[i].ToString() + " " + RefToFieldsSelected[i].ToString() + "_1,";
                }
                else
                {
                    SQLselect += "referral." + RefToFieldsSelected[i].ToString() + " " + RefToFieldsSelected[i].ToString() + "_1 ";
                } 
            }
            butOK.Enabled = true;
        }
         
        if (ListReferredFromSelect.SelectedItems.Count > 0)
        {
            RefFromSel = true;
            if (PatSel == false && RefToSel == false)
            {
                SQLselect = "SELECT ";
            }
            else
            {
                SQLselect += ",";
            } 
            ListReferredFromSelect.SelectedItems.CopyTo(RefFromFieldsSelected, 0);
            for (int i = 0;i < RefFromFieldsSelected.Length;i++)
            {
                if (i != RefFromFieldsSelected.Length - 1)
                {
                    SQLselect += "referral." + RefFromFieldsSelected[i].ToString() + " " + RefFromFieldsSelected[i].ToString() + "_2,";
                }
                else
                {
                    SQLselect += "referral." + RefFromFieldsSelected[i].ToString() + " " + RefFromFieldsSelected[i].ToString() + "_2 ";
                } 
            }
            butOK.Enabled = true;
        }
         
    }

    private void createSQLfrom() throws Exception {
        SQLfrom = "FROM patient";
        if (RefToSel || RefFromSel || NeedRefPat || NeedRefDent)
        {
            SQLfrom += ",referral,refattach";
        }
         
        if (NeedRecall)
        {
            SQLfrom += ",recall";
        }
         
        if (NeedInsPlan)
        {
            SQLfrom += ",insplan";
        }
         
        if (NeedProcLogFirst || NeedProcLogLast)
        {
            SQLfrom += ",procedurelog";
        }
         
        SQLfrom += " ";
    }

    private void createSQLwhereRelation() throws Exception {
        boolean needAnd = false;
        if (!IsWhereRelation && !RefToSel && !RefFromSel)
        {
            SQLwhereRelation = "";
        }
        else
        {
            SQLwhereRelation = "WHERE ";
        } 
        if (RefToSel || RefFromSel)
        {
            if (RefToSel)
            {
                SQLwhereRelation += "patient.patnum=refattach.patnum AND referral.referralnum=refattach.referralnum AND refattach.isfrom='0' ";
            }
            else
            {
                SQLwhereRelation += "patient.patnum=refattach.patnum AND referral.referralnum=refattach.referralnum AND refattach.isfrom='1' ";
            } 
            needAnd = true;
        }
         
        if (NeedRefPat || NeedRefDent)
        {
            if (!RefToSel && !RefFromSel)
            {
                SQLwhereRelation += "patient.patnum=refattach.patnum AND refattach.referralnum=referral.referralnum ";
            }
             
            needAnd = true;
        }
         
        if (NeedInsPlan)
        {
            if (needAnd)
            {
                SQLwhereRelation += "AND (patient.priplannum=insplan.plannum OR patient.secplannum=insplan.plannum) ";
            }
            else
            {
                SQLwhereRelation += "(patient.priplannum=insplan.plannum OR patient.secplannum=insplan.plannum) ";
            } 
            needAnd = true;
        }
         
        if (NeedProcLogFirst || NeedProcLogLast)
        {
            if (needAnd)
            {
                SQLwhereRelation += "AND procedurelog.patnum=patient.patnum ";
            }
            else
            {
                SQLwhereRelation += "procedurelog.patnum=patient.patnum ";
            } 
            needAnd = true;
        }
         
        if (NeedRecall)
        {
            if (needAnd)
            {
                SQLwhereRelation += "AND ";
            }
             
            SQLwhereRelation += "recall.PatNum=patient.PatNum ";
            needAnd = true;
        }
         
    }

    private void createSQLwhereComparison() throws Exception {
        int count = 0;
        if (!IsWhereRelation && !RefToSel && !RefFromSel && ListPrerequisites.Items.Count > 0)
        {
            SQLwhereComparison = "WHERE ";
        }
        else
        {
            //HAVING statements will be in ListPrerequisites so that users can delete them if necessary.
            //If a HAVING statement is the first in the list, it must be skipped so that there is no leading AND.
            //The AND for HAVING statements will be taken care of in CreateSQLgroup().
            if (!StringSupport.equals(SQLwhereRelation, "") && ListPrerequisites.Items.Count > 0 && !StringSupport.equals(ListPrerequisites.Items[0].ToString().Substring(0, 1), "*"))
            {
                SQLwhereComparison = "AND ";
            }
            else
            {
                SQLwhereComparison = "";
            } 
        } 
        for (int i = 0;i < ListPrerequisites.Items.Count;i++)
        {
            if (StringSupport.equals(ListPrerequisites.Items[i].ToString().Substring(0, 1), "*"))
            {
            }
            else
            {
                //Skip HAVING statements which will have a leading asterisk. They will be taken care of in CreateSQLgroup().
                if (count == 0 && !IsWhereRelation)
                {
                    SQLwhereComparison += ListPrerequisites.Items[i].ToString();
                }
                else if (StringSupport.equals(ListPrerequisites.Items[i].ToString().Substring(0, 2), "OR"))
                {
                    SQLwhereComparison += " " + ListPrerequisites.Items[i].ToString();
                }
                else
                {
                    SQLwhereComparison += ((i == 0) ? " " : " AND ") + ListPrerequisites.Items[i].ToString();
                }  
                count++;
            } 
        }
        if (ListPatientSelect.SelectedItems.Count > 0 || ListReferredToSelect.SelectedItems.Count > 0 || ListReferredFromSelect.SelectedItems.Count > 0)
        {
            butOK.Enabled = true;
        }
         
    }

    private void createSQLgroup() throws Exception {
        if (NeedProcLogLast && !NeedProcLogFirst)
        {
            SQLgroup = " GROUP BY procedurelog.patnum HAVING " + ProcLogLastDate;
        }
        else if (NeedProcLogLast && NeedProcLogFirst)
        {
            SQLgroup = " GROUP BY procedurelog.patnum HAVING " + ProcLogLastDate + " AND " + ProcLogFirstDate;
        }
        else if (NeedProcLogFirst && !NeedProcLogLast)
        {
            SQLgroup = " GROUP BY procedurelog.patnum HAVING " + ProcLogFirstDate;
        }
        else
        {
            SQLgroup = "";
        }   
    }

    private void setListBoxConditions() throws Exception {
        ComboBox.Visible = true;
        TextDate.Visible = false;
        TextBox.Visible = false;
        TextValidAge.Visible = false;
        IsDropDown = true;
        IsDate = false;
        IsText = false;
        ListConditions.Enabled = true;
        ComboBox.SelectedIndex = -1;
        butAddFilter.Enabled = false;
        labelHelp.Visible = false;
    }

    private void setTextBoxConditions() throws Exception {
        TextBox.Clear();
        ListConditions.Enabled = true;
        TextBox.Visible = true;
        TextDate.Visible = false;
        ComboBox.Visible = false;
        TextValidAge.Visible = false;
        TextBox.Select();
        IsText = true;
        IsDate = false;
        IsDropDown = false;
        butAddFilter.Enabled = true;
        labelHelp.Visible = false;
    }

    private void setDateConditions() throws Exception {
        TextDate.Visible = true;
        TextBox.Visible = false;
        ComboBox.Visible = false;
        TextValidAge.Visible = false;
        IsDate = true;
        IsText = false;
        IsDropDown = false;
        TextDate.Clear();
        TextDate.Select();
        ListConditions.Enabled = true;
        butAddFilter.Enabled = true;
        labelHelp.Visible = true;
    }

    private void dropListFilter_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        SelectedItem.APPLY __dummyScrutVar0 = DropListFilter.SelectedItem.ToString();
        //case "RecallInterval":
        if (__dummyScrutVar0.equals("Address") || __dummyScrutVar0.equals("Address2") || __dummyScrutVar0.equals("ApptModNote") || __dummyScrutVar0.equals("ChartNumber") || __dummyScrutVar0.equals("City") || __dummyScrutVar0.equals("CreditType") || __dummyScrutVar0.equals("Email") || __dummyScrutVar0.equals("EstBalance") || __dummyScrutVar0.equals("FamAddrNote") || __dummyScrutVar0.equals("FamFinUrgNote") || __dummyScrutVar0.equals("FName") || __dummyScrutVar0.equals("HmPhone") || __dummyScrutVar0.equals("LName") || __dummyScrutVar0.equals("MedUrgNote") || __dummyScrutVar0.equals("MiddleI") || __dummyScrutVar0.equals("NextAptNum") || __dummyScrutVar0.equals("PatNum") || __dummyScrutVar0.equals("Preferred") || __dummyScrutVar0.equals("Salutation") || __dummyScrutVar0.equals("SchoolName") || __dummyScrutVar0.equals("State") || __dummyScrutVar0.equals("StudentStatus") || __dummyScrutVar0.equals("WirelessPhone") || __dummyScrutVar0.equals("WkPhone") || __dummyScrutVar0.equals("Zip"))
        {
            setTextBoxConditions();
        }
        else if (__dummyScrutVar0.equals("SSN"))
        {
            setTextBoxConditions();
            labelHelp.Visible = true;
            labelHelp.Text = "Type in SSN as 123456789";
        }
        else if (__dummyScrutVar0.equals("Primary Carrier") || __dummyScrutVar0.equals("Secondary Carrier"))
        {
            setTextBoxConditions();
            labelHelp.Visible = true;
            labelHelp.Text = "Type in Name of Insurance Company";
        }
        else if (__dummyScrutVar0.equals("Referred From Dentist"))
        {
            labelHelp.Visible = true;
            setTextBoxConditions();
            labelHelp.Text = "Type in last name of dentist";
        }
        else if (__dummyScrutVar0.equals("Referred From Patient"))
        {
            setTextBoxConditions();
            labelHelp.Visible = true;
            labelHelp.Text = "Type in last name of patient";
        }
        else if (__dummyScrutVar0.equals("Age"))
        {
            TextValidAge.Clear();
            ListConditions.Enabled = true;
            TextBox.Visible = false;
            TextDate.Visible = false;
            ComboBox.Visible = false;
            TextValidAge.Visible = true;
            TextValidAge.Select();
            IsText = false;
            IsDate = false;
            IsDropDown = false;
            butAddFilter.Enabled = true;
            labelHelp.Text = "Please Input a number";
        }
        else if (__dummyScrutVar0.equals("Birthdate") || __dummyScrutVar0.equals("Last Visit Date") || __dummyScrutVar0.equals("First Visit Date"))
        {
            setDateConditions();
            labelHelp.Text = "Type Date as mm/dd/yyyy";
        }
        else if (__dummyScrutVar0.equals("Birthday"))
        {
            setDateConditions();
            labelHelp.Text = "Type Date as mm/dd";
        }
        else if (__dummyScrutVar0.equals("PatStatus"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            ComboBox.Items.Add("Patient");
            ComboBox.Items.Add("NonPatient");
            ComboBox.Items.Add("Inactive");
            ComboBox.Items.Add("Archived");
            ComboBox.Items.Add("Deleted");
        }
        else if (__dummyScrutVar0.equals("Gender"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            ComboBox.Items.Add("Male");
            ComboBox.Items.Add("Female");
            ComboBox.Items.Add("Unknown");
        }
        else if (__dummyScrutVar0.equals("Position"))
        {
            setListBoxConditions();
            ListConditions.SelectedIndex = 1;
            ListConditions.Enabled = false;
            ComboBox.Items.Clear();
            ComboBox.Items.Add("Single");
            ComboBox.Items.Add("Married");
            ComboBox.Items.Add("Child");
        }
        else if (__dummyScrutVar0.equals("FeeSched"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
            {
                sItem = FeeSchedC.getListLong()[i].Description;
                if (FeeSchedC.getListLong()[i].IsHidden)
                {
                    sItem += "(hidden)";
                }
                 
                ComboBox.Items.Add(sItem);
            }
        }
        else if (__dummyScrutVar0.equals("BillingType"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            for (int i = 0;i < DefC.getLong()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
            {
                sItem = DefC.getLong()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName.ToString();
                if (DefC.getLong()[((Enum)DefCat.BillingTypes).ordinal()][i].IsHidden)
                    sItem += "(hidden)";
                 
                ComboBox.Items.Add(sItem);
            }
        }
        else if (__dummyScrutVar0.equals("RecallStatus"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            for (int i = 0;i < DefC.getLong()[((Enum)DefCat.RecallUnschedStatus).ordinal()].Length;i++)
            {
                sItem = DefC.getLong()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].ItemName.ToString();
                if (DefC.getLong()[((Enum)DefCat.RecallUnschedStatus).ordinal()][i].IsHidden)
                    sItem += "(hidden)";
                 
                ComboBox.Items.Add(sItem);
            }
        }
        else if (__dummyScrutVar0.equals("PriProv") || __dummyScrutVar0.equals("SecProv"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            for (int i = 0;i < ProviderC.getListLong().Count;i++)
            {
                sItem = ProviderC.getListLong()[i].LName + ", " + ProviderC.getListLong()[i].MI + " " + ProviderC.getListLong()[i].FName;
                if (ProviderC.getListLong()[i].IsHidden)
                    sItem += "(hidden)";
                 
                ComboBox.Items.Add(sItem);
            }
        }
        else if (__dummyScrutVar0.equals("PriRelationship") || __dummyScrutVar0.equals("SecRelationship"))
        {
            setListBoxConditions();
            ComboBox.Items.Clear();
            ComboBox.Items.Add("Self");
            ComboBox.Items.Add("Spouse");
            ComboBox.Items.Add("Child");
            ComboBox.Items.Add("Employee");
            ComboBox.Items.Add("HandicapDep");
            ComboBox.Items.Add("SignifOther");
            ComboBox.Items.Add("InjuredPlantiff");
            ComboBox.Items.Add("LifePartner");
            ComboBox.Items.Add("Dependent");
        }
                        
    }

    private void comboBox_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (ComboBox.SelectedItems.Count > 0)
            butAddFilter.Enabled = true;
        else
            butAddFilter.Enabled = false; 
    }

    private void listPrerequisites_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        butDeleteFilter.Enabled = false;
        if (ListPrerequisites.Items.Count > 0 && ListPrerequisites.SelectedItems.Count > 0)
        {
            butDeleteFilter.Enabled = true;
        }
         
    }

    private void listPatientSelect_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        createSQL();
    }

    private void listReferredToSelect_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        createSQL();
    }

    private void listReferredFromSelect_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        createSQL();
    }

    private void butAddFilter_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(TextDate.errorProvider1.GetError(TextDate), "") || !StringSupport.equals(TextValidAge.errorProvider1.GetError(TextValidAge), "") || (StringSupport.equals(TextValidAge.Text, "") && StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Age")) || (StringSupport.equals(TextDate.Text, "") && IsDate))
        {
            MessageBox.Show("Please fix data entry errors first.");
            return ;
        }
         
        UsingInsPlans.Add(false);
        UsingRefDent.Add(false);
        UsingRefPat.Add(false);
        UsingProcLogFirst.Add(false);
        UsingProcLogLast.Add(false);
        UsingRecall.Add(false);
        if (IsText)
        {
            if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Primary Carrier"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    //spaces around = are necessary
                    ListPrerequisites.Items.Add("insplan.Carrier LIKE '%" + TextBox.Text + "%'");
                }
                else
                {
                    ListPrerequisites.Items.Add("insplan.Carrier " + ListConditions.SelectedItem.ToString() + " '" + TextBox.Text + "'");
                } 
                UsingInsPlans[UsingInsPlans.Count - 1] = true;
            }
            else //end if(DropListFilter.SelectedItem.ToString()=="Primary Carrier")
            if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Secondary Carrier"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    ListPrerequisites.Items.Add("insplan.Carrier LIKE '%" + TextBox.Text + "%'");
                }
                else
                {
                    ListPrerequisites.Items.Add("insplan.Carrier " + ListConditions.SelectedItem.ToString() + " '" + TextBox.Text + "'");
                } 
                UsingInsPlans[UsingInsPlans.Count - 1] = true;
            }
            else //	end	else if(DropListFilter.SelectedItem.ToString()=="Secondary Carrier"){
            if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Referred From Dentist"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    ListPrerequisites.Items.Add("referral.patnum=0 AND referral.LName LIKE '%" + TextBox.Text + "%'");
                }
                else
                {
                    ListPrerequisites.Items.Add("referral.patnum=0 AND referral.LName " + ListConditions.SelectedItem.ToString() + " '" + TextBox.Text + "'");
                } 
                UsingRefDent[UsingInsPlans.Count - 1] = true;
            }
            else if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Referred From Patient"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    ListPrerequisites.Items.Add("referral.patnum > '0' AND referral.LName LIKE '%" + TextBox.Text + "%'");
                }
                else
                {
                    ListPrerequisites.Items.Add("referral.patnum > '0' AND referral.LName " + ListConditions.SelectedItem.ToString() + " '" + TextBox.Text + "'");
                } 
                UsingRefPat[UsingInsPlans.Count - 1] = true;
            }
            else
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    ListPrerequisites.Items.Add("patient." + DropListFilter.SelectedItem.ToString() + " LIKE '%" + TextBox.Text + "%'");
                }
                else
                {
                    ListPrerequisites.Items.Add("patient." + DropListFilter.SelectedItem.ToString() + " " + ListConditions.SelectedItem.ToString() + " '" + TextBox.Text + "'");
                } 
            }    
        }
        else //end if(isText)
        if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Age"))
        {
            if (ListConditions.SelectedIndex == 0)
            {
                ListPrerequisites.Items.Add("patient.BirthDate LIKE '%" + DateTime.Now.AddYears(-Convert.ToInt32(TextValidAge.Text)).ToString("yyyy-MM-dd") + "%'");
            }
            else if (StringSupport.equals(ListConditions.SelectedItem.ToString(), "<"))
            {
                ListPrerequisites.Items.Add("patient.Birthdate > '" + DateTime.Now.AddYears(-Convert.ToInt32(TextValidAge.Text)).ToString("yyyy-MM-dd") + "'");
            }
            else if (StringSupport.equals(ListConditions.SelectedItem.ToString(), ">"))
            {
                ListPrerequisites.Items.Add("patient.Birthdate < '" + DateTime.Now.AddYears(-Convert.ToInt32(TextValidAge.Text)).ToString("yyyy-MM-dd") + "'");
            }
            else if (StringSupport.equals(ListConditions.SelectedItem.ToString(), "<="))
            {
                ListPrerequisites.Items.Add("patient.Birthdate >= '" + DateTime.Now.AddYears(-Convert.ToInt32(TextValidAge.Text)).ToString("yyyy-MM-dd") + "'");
            }
            else if (StringSupport.equals(ListConditions.SelectedItem.ToString(), ">="))
            {
                ListPrerequisites.Items.Add("patient.Birthdate <= '" + DateTime.Now.AddYears(-Convert.ToInt32(TextValidAge.Text)).ToString("yyyy-MM-dd") + "'");
            }
            else
            {
                ListPrerequisites.Items.Add("patient.Birthdate " + ListConditions.SelectedItem.ToString() + " '" + DateTime.Now.AddYears(-Convert.ToInt32(TextValidAge.Text)).ToString("yyyy-MM-dd") + "'");
            }     
        }
        else if (IsDate)
        {
            if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "First Visit Date"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    //Add the HAVING statement to ListPrerequisites with a leading asterisk so that it shows up in the UI so that users can delete it.
                    //It is added with a leading asterisk so that it gets skipped in CreateSQLwhereComparison().
                    ListPrerequisites.Items.Add("*HAVING MIN(procdate) LIKE '%" + POut.Date(DateTime.Parse(TextDate.Text), false) + "%'");
                    //Set the class wide variable without the *HAVING portion. If ProcLogFirstDate has a value, it will be used in CreateSQLgroup().
                    ProcLogFirstDate = "MIN(procdate) LIKE '%" + POut.Date(DateTime.Parse(TextDate.Text), false) + "%'";
                }
                else
                {
                    ListPrerequisites.Items.Add("*HAVING MIN(procdate) " + ListConditions.SelectedItem.ToString() + " " + POut.Date(DateTime.Parse(TextDate.Text)));
                    ProcLogFirstDate = "MIN(procdate) " + ListConditions.SelectedItem.ToString() + " " + POut.Date(DateTime.Parse(TextDate.Text));
                } 
                UsingProcLogFirst[UsingInsPlans.Count - 1] = true;
            }
            else if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Last Visit Date"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    //See comment above where ProcLogFirstDate is handled regarding the reasoning for leading the having statement with an asterisk.
                    ListPrerequisites.Items.Add("*HAVING MAX(procdate) LIKE '%" + POut.Date(DateTime.Parse(TextDate.Text), false) + "%'");
                    ProcLogLastDate = "MAX(procdate) LIKE '%" + POut.Date(DateTime.Parse(TextDate.Text), false) + "%'";
                }
                else
                {
                    ListPrerequisites.Items.Add("*HAVING MAX(procdate) " + ListConditions.SelectedItem.ToString() + " " + POut.Date(DateTime.Parse(TextDate.Text)));
                    ProcLogLastDate = "MAX(procdate) " + ListConditions.SelectedItem.ToString() + " " + POut.Date(DateTime.Parse(TextDate.Text));
                } 
                UsingProcLogLast[UsingInsPlans.Count - 1] = true;
            }
            else if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "Birthday"))
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    ListPrerequisites.Items.Add("MONTH(Birthdate) " + "= '" + DateTime.Parse(TextDate.Text).Month.ToString() + "'");
                }
                else
                {
                    ListPrerequisites.Items.Add("SUBSTRING(Birthdate,6,5) " + ListConditions.SelectedItem.ToString() + " '" + DateTime.Parse(TextDate.Text).ToString("MM") + "-" + DateTime.Parse(TextDate.Text).ToString("dd") + "'");
                } 
            }
            else
            {
                if (ListConditions.SelectedIndex == 0)
                {
                    ListPrerequisites.Items.Add(DropListFilter.SelectedItem.ToString() + " Like '%" + POut.Date(DateTime.Parse(TextDate.Text), false) + "%'");
                }
                else
                {
                    ListPrerequisites.Items.Add(DropListFilter.SelectedItem.ToString() + " " + ListConditions.SelectedItem.ToString() + " " + POut.Date(DateTime.Parse(TextDate.Text)));
                } 
            }   
        }
        else //end else if(isDate)
        if (IsDropDown)
        {
            if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "FeeSched"))
            {
                sItem = "";
                for (int i = 0;i < ComboBox.SelectedIndices.Count;i++)
                {
                    if (i == 0)
                    {
                        sItem = "(";
                    }
                    else
                    {
                        sItem = "OR ";
                    } 
                    sItem += "patient.FeeSched " + ListConditions.SelectedItem.ToString() + " '" + FeeSchedC.getListLong()[ComboBox.SelectedIndices[i]].FeeSchedNum.ToString() + "'";
                    if (i == ComboBox.SelectedIndices.Count - 1)
                    {
                        sItem += ")";
                    }
                     
                    ListPrerequisites.Items.Add(sItem);
                }
            }
            else //end if
            if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "BillingType"))
            {
                sItem = "";
                for (int i = 0;i < ComboBox.SelectedIndices.Count;i++)
                {
                    if (i == 0)
                    {
                        sItem = "(";
                    }
                    else
                    {
                        sItem = "OR ";
                    } 
                    sItem += "patient.BillingType " + ListConditions.SelectedItem.ToString() + " '" + DefC.getLong()[((Enum)DefCat.BillingTypes).ordinal()][ComboBox.SelectedIndices[i]].DefNum.ToString() + "'";
                    if (i == ComboBox.SelectedIndices.Count - 1)
                    {
                        sItem += ")";
                    }
                     
                    ListPrerequisites.Items.Add(sItem);
                }
            }
            else if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "RecallStatus"))
            {
                sItem = "";
                for (int i = 0;i < ComboBox.SelectedIndices.Count;i++)
                {
                    if (i == 0)
                    {
                        sItem = "(";
                    }
                    else
                    {
                        sItem = "OR ";
                    } 
                    sItem += "recall.RecallStatus " + ListConditions.SelectedItem.ToString() + " '" + DefC.getLong()[((Enum)DefCat.RecallUnschedStatus).ordinal()][ComboBox.SelectedIndices[i]].DefNum.ToString() + "'";
                    if (i == ComboBox.SelectedIndices.Count - 1)
                    {
                        sItem += ")";
                    }
                     
                    ListPrerequisites.Items.Add(sItem);
                }
                UsingRecall[UsingRecall.Count - 1] = true;
            }
            else if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "PriProv"))
            {
                sItem = "";
                for (int i = 0;i < ComboBox.SelectedIndices.Count;i++)
                {
                    if (i == 0)
                    {
                        sItem = "(";
                    }
                    else
                    {
                        sItem = "OR ";
                    } 
                    sItem += "patient.PriProv " + ListConditions.SelectedItem.ToString() + " '" + ProviderC.getListLong()[ComboBox.SelectedIndices[i]].ProvNum.ToString() + "'";
                    if (i == ComboBox.SelectedIndices.Count - 1)
                    {
                        sItem += ")";
                    }
                     
                    ListPrerequisites.Items.Add(sItem);
                }
            }
            else if (StringSupport.equals(DropListFilter.SelectedItem.ToString(), "SecProv"))
            {
                sItem = "";
                for (int i = 0;i < ComboBox.SelectedIndices.Count;i++)
                {
                    if (i == 0)
                    {
                        sItem = "(";
                    }
                    else
                    {
                        sItem = "OR ";
                    } 
                    sItem += "patient.SecProv " + ListConditions.SelectedItem.ToString() + " '" + ProviderC.getListLong()[ComboBox.SelectedIndices[i]].ProvNum.ToString() + "'";
                    if (i == ComboBox.SelectedIndices.Count - 1)
                    {
                        sItem += ")";
                    }
                     
                    ListPrerequisites.Items.Add(sItem);
                }
            }
            else
            {
                for (int i = 0;i < ComboBox.SelectedItems.Count;i++)
                {
                    //PatStatus
                    //Gender
                    //Position
                    //PriRelationship
                    //SecRelationship
                    if (ListConditions.SelectedIndex == 0)
                    {
                        ListPrerequisites.Items.Add(DropListFilter.SelectedItem.ToString() + " LIKE '%" + ComboBox.SelectedIndices[i].ToString() + "%'");
                    }
                    else
                    {
                        ListPrerequisites.Items.Add(DropListFilter.SelectedItem.ToString() + " " + ListConditions.SelectedItem.ToString() + " '" + ComboBox.SelectedIndices[i].ToString() + "'");
                    } 
                }
            }     
            //end for
            ComboBox.SelectedIndex = -1;
            butAddFilter.Enabled = false;
        }
            
        //end else if(isDropDown)
        createSQL();
        fillSQLbox();
        ListConditions.Enabled = true;
        TextBox.Clear();
        TextDate.Clear();
        TextValidAge.Clear();
    }

    private void butDeleteFilter_Click(Object sender, System.EventArgs e) throws Exception {
        while (ListPrerequisites.SelectedIndices.Count > 0)
        {
            UsingInsPlans.RemoveAt(ListPrerequisites.SelectedIndices[0]);
            UsingRefDent.RemoveAt(ListPrerequisites.SelectedIndices[0]);
            UsingRefPat.RemoveAt(ListPrerequisites.SelectedIndices[0]);
            UsingProcLogFirst.RemoveAt(ListPrerequisites.SelectedIndices[0]);
            UsingProcLogLast.RemoveAt(ListPrerequisites.SelectedIndices[0]);
            ListPrerequisites.Items.RemoveAt(ListPrerequisites.SelectedIndices[0]);
        }
        createSQL();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = TextSQL.Text;
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = false;
        FormQuery2.submitQuery();
        FormQuery2.textQuery.Text = report.Query;
        FormQuery2.ShowDialog();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
    }

}


