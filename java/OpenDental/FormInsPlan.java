//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:16 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.Bridges.Iap;
import OpenDental.Bridges.Trojan;
import OpenDental.DataValid;
import OpenDental.Eclaims.Canadian;
import OpenDental.Eclaims.CanadianOutput;
import OpenDental.Eclaims.x270Controller;
import OpenDental.FormAuditOneType;
import OpenDental.FormBenefitElectHistory;
import OpenDental.FormCarrierEdit;
import OpenDental.FormCarriers;
import OpenDental.FormElectIDs;
import OpenDental.FormEligibilityResponseDisplay;
import OpenDental.FormFamilyMemberSelect;
import OpenDental.FormIap;
import OpenDental.FormInsAdj;
import OpenDental.FormInsBenefitNotes;
import OpenDental.FormInsBenefits;
import OpenDental.FormInsPlan;
import OpenDental.FormInsPlans;
import OpenDental.FormNotePick;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.CanSupTransTypes;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimForms;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clearinghouses;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.EclaimsCommBridge;
import OpenDentBusiness.ElectIDs;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.Employer;
import OpenDentBusiness.Employers;
import OpenDentBusiness.EnumCobRule;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.Family;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsFilingCodeC;
import OpenDentBusiness.InsFilingCodeSubtype;
import OpenDentBusiness.InsFilingCodeSubtypes;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramName;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.TelephoneNumbers;
import OpenDentBusiness.TrojanObject;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormInsPlan  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelGroupNum = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label28 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPhone = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textGroupName = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textGroupNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textAddress = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textCity = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textState = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textZip = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textElectID = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.TextBox textCarrier = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDate textDateEffect;
    private OpenDental.ValidDate textDateTerm;
    /**
    * The InsPlan is always inserted before opening this form.
    */
    public boolean IsNewPlan = new boolean();
    /**
    * The PatPlan is always inserted before opening this form.
    */
    public boolean IsNewPatPlan = new boolean();
    private System.Windows.Forms.TextBox textEmployer = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textAddress2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label21 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkRelease = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkNoSendElect = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label23 = new System.Windows.Forms.Label();
    private System.Windows.Forms.CheckBox checkClaimsUseUCR = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSubscriber = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupSubscriber = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textSubscriberID = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboLinked = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textLinkedNum = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ToolTip toolTip1 = new System.Windows.Forms.ToolTip();
    private System.Windows.Forms.CheckBox checkAssign = new System.Windows.Forms.CheckBox();
    /**
    * used in the emp dropdown logic
    */
    private String empOriginal = new String();
    /**
    * displayed from within code, not designer
    */
    private System.Windows.Forms.ListBox listEmps = new System.Windows.Forms.ListBox();
    private boolean mouseIsInListEmps = new boolean();
    private List<Carrier> similarCars = new List<Carrier>();
    private String carOriginal = new String();
    private System.Windows.Forms.ListBox listCars = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelCitySTZip = new System.Windows.Forms.Label();
    private boolean mouseIsInListCars = new boolean();
    private System.Windows.Forms.Label labelDrop = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDrop;
    private System.Windows.Forms.GroupBox groupCoPay = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private OpenDental.ODtextBox textSubscNote;
    private System.Windows.Forms.ComboBox comboCopay = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboFeeSched = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboClaimForm = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butSearch;
    /**
    * 
    */
    private InsPlan PlanCur;
    /**
    * This is a copy of PlanCur as it was originally when this form was opened.  This is needed to determine whether plan was changed.  However, this is also reset when 'pick from list' is used.
    */
    private InsPlan PlanCurOriginal;
    /**
    * This original planNum does not get reset when 'pick from list' is used.  This allows intelligent decisions about how to save changes.
    */
    private long PlanNumOriginal = new long();
    /**
    * 
    */
    private InsSub SubCur;
    private System.Windows.Forms.ComboBox comboElectIDdescript = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboAllowedFeeSched = new System.Windows.Forms.ComboBox();
    private OpenDental.UI.Button butLabel;
    private System.Windows.Forms.GroupBox groupRequestBen = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label labelTrojanID = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTrojanID = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butImportTrojan;
    private System.Windows.Forms.Label labelDivisionDash = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDivisionNo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelElectronicID = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butIapFind;
    private OpenDental.UI.Button butBenefitNotes;
    private System.Windows.Forms.CheckBox checkIsMedical = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkAlternateCode = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label label25 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboPlanType = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label label26 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Label label31 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label32 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label33 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label35 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelPatID = new System.Windows.Forms.Label();
    private Carrier CarrierCur;
    private System.Windows.Forms.ComboBox comboRelationship = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.CheckBox checkIsPending = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.TextBox textPatID = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butAdjAdd;
    private System.Windows.Forms.ListBox listAdj = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Panel panelPat = new System.Windows.Forms.Panel();
    private PatPlan PatPlanCur;
    private ArrayList AdjAL = new ArrayList();
    private OpenDental.ValidNumber textOrdinal;
    private OpenDental.UI.ODGrid gridBenefits;
    private TextBox textPlanNum = new TextBox();
    /**
    * This is the current benefit list that displays on the form.  It does not get saved to the database until this form closes.
    */
    private List<Benefit> benefitList = new List<Benefit>();
    //each item is a Benefit
    private List<Benefit> benefitListOld = new List<Benefit>();
    //private bool usesAnnivers;
    private Label label17 = new Label();
    private OpenDental.UI.Button butPick;
    private OpenDental.ODtextBox textPlanNote;
    private Label label18 = new Label();
    //<summary>Set to true if called from the list of insurance plans.  In this case, the planNum will be 0.  There will be no subscriber.  Benefits will be 'typical' rather than from one specific plan.  Upon saving, all similar plans will be set to be exactly the same as PlanCur.</summary>
    //public bool IsForAll;//Instead, just pass in a null subscriber.
    /**
    * Set to true from FormInsPlansMerge.  In this case, the insplan is read only, because it's much more complicated to allow user to change.
    */
    public boolean IsReadOnly = new boolean();
    private List<FeeSched> FeeSchedsStandard = new List<FeeSched>();
    private List<FeeSched> FeeSchedsCopay = new List<FeeSched>();
    private GroupBox groupPlan = new GroupBox();
    private List<FeeSched> FeeSchedsAllowed = new List<FeeSched>();
    private Panel panelPlan = new Panel();
    private Label label13 = new Label();
    private ComboBox comboFilingCode = new ComboBox();
    private GroupBox groupCarrier = new GroupBox();
    private Label labelDentaide = new Label();
    private OpenDental.ValidNumber textDentaide;
    private OpenDental.UI.Button butGetElectronic;
    private CheckBox checkShowBaseUnits = new CheckBox();
    private CheckBox checkCodeSubst = new CheckBox();
    private TextBox textElectBenLastDate = new TextBox();
    private Label labelHistElect = new Label();
    private OpenDental.UI.Button butHistoryElect;
    private ComboBox comboFilingCodeSubtype = new ComboBox();
    private Label label15 = new Label();
    private OpenDental.UI.Button butPickCarrier;
    private GroupBox groupCanadian = new GroupBox();
    private TextBox textPlanFlag = new TextBox();
    private Label label22 = new Label();
    private Label label24 = new Label();
    private RadioButton radioChangeAll = new RadioButton();
    private GroupBox groupChanges = new GroupBox();
    private RadioButton radioCreateNew = new RadioButton();
    private OpenDental.UI.Button butChange;
    private Label label19 = new Label();
    private TextBox textCanadianInstCode = new TextBox();
    private Label label9 = new Label();
    private TextBox textCanadianDiagCode = new TextBox();
    private CheckBox checkIsPMP = new CheckBox();
    private TextBox textBIN = new TextBox();
    private Label labelBIN = new Label();
    private ComboBox comboCobRule = new ComboBox();
    private Label label20 = new Label();
    private OpenDental.UI.Button butAudit;
    private CheckBox checkIsHidden = new CheckBox();
    //<summary>This is a field that is accessed only by clicking on the button because there's not room for it otherwise.  This variable should be treated just as if it was a visible textBox.</summary>
    //private string BenefitNotes;
    /**
    * Called from ContrFamily and FormInsPlans. Must pass in the plan, patPlan, and sub, although patPlan and sub can be null.
    */
    public FormInsPlan(InsPlan planCur, PatPlan patPlanCur, InsSub subCur) throws Exception {
        Cursor = Cursors.WaitCursor;
        initializeComponent();
        PlanCur = planCur;
        PatPlanCur = patPlanCur;
        SubCur = subCur;
        listEmps = new ListBox();
        listEmps.Location = new Point(panelPlan.Left + groupPlan.Left + textEmployer.Left, panelPlan.Top + groupPlan.Top + textEmployer.Bottom);
        listEmps.Size = new Size(231, 100);
        listEmps.Visible = false;
        listEmps.Click += new System.EventHandler(listEmps_Click);
        listEmps.DoubleClick += new System.EventHandler(listEmps_DoubleClick);
        listEmps.MouseEnter += new System.EventHandler(listEmps_MouseEnter);
        listEmps.MouseLeave += new System.EventHandler(listEmps_MouseLeave);
        Controls.Add(listEmps);
        listEmps.BringToFront();
        listCars = new ListBox();
        listCars.Location = new Point(panelPlan.Left + groupPlan.Left + groupCarrier.Left + textCarrier.Left, panelPlan.Top + groupPlan.Top + groupCarrier.Top + textCarrier.Bottom);
        listCars.Size = new Size(700, 100);
        listCars.HorizontalScrollbar = true;
        listCars.Visible = false;
        listCars.Click += new System.EventHandler(listCars_Click);
        listCars.DoubleClick += new System.EventHandler(listCars_DoubleClick);
        listCars.MouseEnter += new System.EventHandler(listCars_MouseEnter);
        listCars.MouseLeave += new System.EventHandler(listCars_MouseLeave);
        Controls.Add(listCars);
        listCars.BringToFront();
        //tbPercentPlan.CellClicked += new OpenDental.ContrTable.CellEventHandler(tbPercentPlan_CellClicked);
        //tbPercentPat.CellClicked += new OpenDental.ContrTable.CellEventHandler(tbPercentPat_CellClicked);
        Lan.f(this);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            labelPatID.Text = Lan.g(this,"Dependant Code");
            labelCitySTZip.Text = Lan.g(this,"City,Prov,Post");
            //Postal Code";
            butSearch.Visible = false;
            labelElectronicID.Text = "EDI Code";
            comboElectIDdescript.Visible = false;
            labelGroupNum.Text = Lan.g(this,"Plan Number");
            checkIsPMP.Checked = (planCur.CanadianPlanFlag != null && !StringSupport.equals(planCur.CanadianPlanFlag, ""));
        }
        else
        {
            labelDivisionDash.Visible = false;
            textDivisionNo.Visible = false;
            groupCanadian.Visible = false;
        } 
        if (CultureInfo.CurrentCulture.Name.Length >= 4 && StringSupport.equals(CultureInfo.CurrentCulture.Name.Substring(3), "GB"))
        {
            //en-GB
            labelCitySTZip.Text = Lan.g(this,"City,Postcode");
        }
         
        panelPat.BackColor = DefC.getLong()[((Enum)DefCat.MiscColors).ordinal()][0].ItemColor;
        //labelViewRequestDocument.Text="         ";
        //if(!PrefC.GetBool(PrefName.CustomizedForPracticeWeb")) {
        //	butEligibility.Visible=false;
        //	labelViewRequestDocument.Visible=false;
        //}
        Cursor = Cursors.Default;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormInsPlan.class);
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.labelGroupNum = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.labelCitySTZip = new System.Windows.Forms.Label();
        this.labelElectronicID = new System.Windows.Forms.Label();
        this.label28 = new System.Windows.Forms.Label();
        this.textCarrier = new System.Windows.Forms.TextBox();
        this.textPhone = new System.Windows.Forms.TextBox();
        this.textGroupName = new System.Windows.Forms.TextBox();
        this.textGroupNum = new System.Windows.Forms.TextBox();
        this.textAddress = new System.Windows.Forms.TextBox();
        this.textCity = new System.Windows.Forms.TextBox();
        this.textState = new System.Windows.Forms.TextBox();
        this.textZip = new System.Windows.Forms.TextBox();
        this.textElectID = new System.Windows.Forms.TextBox();
        this.textEmployer = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textAddress2 = new System.Windows.Forms.TextBox();
        this.label21 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.checkAssign = new System.Windows.Forms.CheckBox();
        this.checkRelease = new System.Windows.Forms.CheckBox();
        this.checkNoSendElect = new System.Windows.Forms.CheckBox();
        this.label23 = new System.Windows.Forms.Label();
        this.checkAlternateCode = new System.Windows.Forms.CheckBox();
        this.checkClaimsUseUCR = new System.Windows.Forms.CheckBox();
        this.label14 = new System.Windows.Forms.Label();
        this.textSubscriber = new System.Windows.Forms.TextBox();
        this.groupSubscriber = new System.Windows.Forms.GroupBox();
        this.label25 = new System.Windows.Forms.Label();
        this.textSubscriberID = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.comboLinked = new System.Windows.Forms.ComboBox();
        this.textLinkedNum = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.comboPlanType = new System.Windows.Forms.ComboBox();
        this.checkIsMedical = new System.Windows.Forms.CheckBox();
        this.textDivisionNo = new System.Windows.Forms.TextBox();
        this.labelDivisionDash = new System.Windows.Forms.Label();
        this.comboClaimForm = new System.Windows.Forms.ComboBox();
        this.comboFeeSched = new System.Windows.Forms.ComboBox();
        this.groupCoPay = new System.Windows.Forms.GroupBox();
        this.label12 = new System.Windows.Forms.Label();
        this.comboAllowedFeeSched = new System.Windows.Forms.ComboBox();
        this.label11 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.comboCopay = new System.Windows.Forms.ComboBox();
        this.comboElectIDdescript = new System.Windows.Forms.ComboBox();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.labelDrop = new System.Windows.Forms.Label();
        this.groupRequestBen = new System.Windows.Forms.GroupBox();
        this.labelHistElect = new System.Windows.Forms.Label();
        this.textElectBenLastDate = new System.Windows.Forms.TextBox();
        this.labelTrojanID = new System.Windows.Forms.Label();
        this.textTrojanID = new System.Windows.Forms.TextBox();
        this.label26 = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.comboRelationship = new System.Windows.Forms.ComboBox();
        this.label31 = new System.Windows.Forms.Label();
        this.checkIsPending = new System.Windows.Forms.CheckBox();
        this.label32 = new System.Windows.Forms.Label();
        this.label33 = new System.Windows.Forms.Label();
        this.listAdj = new System.Windows.Forms.ListBox();
        this.label35 = new System.Windows.Forms.Label();
        this.textPatID = new System.Windows.Forms.TextBox();
        this.labelPatID = new System.Windows.Forms.Label();
        this.panelPat = new System.Windows.Forms.Panel();
        this.textPlanNum = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.label18 = new System.Windows.Forms.Label();
        this.groupPlan = new System.Windows.Forms.GroupBox();
        this.textBIN = new System.Windows.Forms.TextBox();
        this.labelBIN = new System.Windows.Forms.Label();
        this.groupCarrier = new System.Windows.Forms.GroupBox();
        this.panelPlan = new System.Windows.Forms.Panel();
        this.comboCobRule = new System.Windows.Forms.ComboBox();
        this.label20 = new System.Windows.Forms.Label();
        this.groupCanadian = new System.Windows.Forms.GroupBox();
        this.label19 = new System.Windows.Forms.Label();
        this.textCanadianInstCode = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.textCanadianDiagCode = new System.Windows.Forms.TextBox();
        this.checkIsPMP = new System.Windows.Forms.CheckBox();
        this.label24 = new System.Windows.Forms.Label();
        this.label22 = new System.Windows.Forms.Label();
        this.textPlanFlag = new System.Windows.Forms.TextBox();
        this.labelDentaide = new System.Windows.Forms.Label();
        this.comboFilingCodeSubtype = new System.Windows.Forms.ComboBox();
        this.label15 = new System.Windows.Forms.Label();
        this.checkIsHidden = new System.Windows.Forms.CheckBox();
        this.checkCodeSubst = new System.Windows.Forms.CheckBox();
        this.checkShowBaseUnits = new System.Windows.Forms.CheckBox();
        this.comboFilingCode = new System.Windows.Forms.ComboBox();
        this.label13 = new System.Windows.Forms.Label();
        this.radioChangeAll = new System.Windows.Forms.RadioButton();
        this.groupChanges = new System.Windows.Forms.GroupBox();
        this.radioCreateNew = new System.Windows.Forms.RadioButton();
        this.gridBenefits = new OpenDental.UI.ODGrid();
        this.textDentaide = new OpenDental.ValidNumber();
        this.butPickCarrier = new OpenDental.UI.Button();
        this.butSearch = new OpenDental.UI.Button();
        this.butAudit = new OpenDental.UI.Button();
        this.butPick = new OpenDental.UI.Button();
        this.textPlanNote = new OpenDental.ODtextBox();
        this.butOK = new OpenDental.UI.Button();
        this.textOrdinal = new OpenDental.ValidNumber();
        this.butAdjAdd = new OpenDental.UI.Button();
        this.butDrop = new OpenDental.UI.Button();
        this.butLabel = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butHistoryElect = new OpenDental.UI.Button();
        this.butImportTrojan = new OpenDental.UI.Button();
        this.butGetElectronic = new OpenDental.UI.Button();
        this.butBenefitNotes = new OpenDental.UI.Button();
        this.butIapFind = new OpenDental.UI.Button();
        this.butChange = new OpenDental.UI.Button();
        this.textDateEffect = new OpenDental.ValidDate();
        this.textDateTerm = new OpenDental.ValidDate();
        this.textSubscNote = new OpenDental.ODtextBox();
        this.groupSubscriber.SuspendLayout();
        this.groupCoPay.SuspendLayout();
        this.groupRequestBen.SuspendLayout();
        this.panelPat.SuspendLayout();
        this.groupPlan.SuspendLayout();
        this.groupCarrier.SuspendLayout();
        this.panelPlan.SuspendLayout();
        this.groupCanadian.SuspendLayout();
        this.groupChanges.SuspendLayout();
        this.SuspendLayout();
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(7, 57);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(100, 15);
        this.label5.TabIndex = 5;
        this.label5.Text = "Effective Dates";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(182, 57);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(36, 15);
        this.label6.TabIndex = 6;
        this.label6.Text = "To";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(5, 34);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(95, 15);
        this.label7.TabIndex = 7;
        this.label7.Text = "Phone";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(16, 203);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(95, 15);
        this.label8.TabIndex = 8;
        this.label8.Text = "Group Name";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelGroupNum
        //
        this.labelGroupNum.Location = new System.Drawing.Point(16, 223);
        this.labelGroupNum.Name = "labelGroupNum";
        this.labelGroupNum.Size = new System.Drawing.Size(95, 15);
        this.labelGroupNum.TabIndex = 9;
        this.labelGroupNum.Text = "Group Num";
        this.labelGroupNum.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(5, 53);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(95, 15);
        this.label10.TabIndex = 10;
        this.label10.Text = "Address";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelCitySTZip
        //
        this.labelCitySTZip.Location = new System.Drawing.Point(5, 93);
        this.labelCitySTZip.Name = "labelCitySTZip";
        this.labelCitySTZip.Size = new System.Drawing.Size(95, 15);
        this.labelCitySTZip.TabIndex = 11;
        this.labelCitySTZip.Text = "City,ST,Zip";
        this.labelCitySTZip.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelElectronicID
        //
        this.labelElectronicID.Location = new System.Drawing.Point(4, 113);
        this.labelElectronicID.Name = "labelElectronicID";
        this.labelElectronicID.Size = new System.Drawing.Size(95, 15);
        this.labelElectronicID.TabIndex = 15;
        this.labelElectronicID.Text = "Electronic ID";
        this.labelElectronicID.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label28
        //
        this.label28.Location = new System.Drawing.Point(2, 78);
        this.label28.Name = "label28";
        this.label28.Size = new System.Drawing.Size(55, 41);
        this.label28.TabIndex = 28;
        this.label28.Text = "Note";
        this.label28.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCarrier
        //
        this.textCarrier.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textCarrier.Location = new System.Drawing.Point(102, 11);
        this.textCarrier.MaxLength = 50;
        this.textCarrier.Name = "textCarrier";
        this.textCarrier.Size = new System.Drawing.Size(273, 21);
        this.textCarrier.TabIndex = 0;
        this.textCarrier.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textCarrier_KeyUp);
        this.textCarrier.Leave += new System.EventHandler(this.textCarrier_Leave);
        //
        // textPhone
        //
        this.textPhone.Location = new System.Drawing.Point(102, 31);
        this.textPhone.MaxLength = 30;
        this.textPhone.Name = "textPhone";
        this.textPhone.Size = new System.Drawing.Size(157, 20);
        this.textPhone.TabIndex = 1;
        this.textPhone.TextChanged += new System.EventHandler(this.textPhone_TextChanged);
        //
        // textGroupName
        //
        this.textGroupName.Location = new System.Drawing.Point(112, 200);
        this.textGroupName.MaxLength = 50;
        this.textGroupName.Name = "textGroupName";
        this.textGroupName.Size = new System.Drawing.Size(193, 20);
        this.textGroupName.TabIndex = 2;
        //
        // textGroupNum
        //
        this.textGroupNum.Location = new System.Drawing.Point(112, 220);
        this.textGroupNum.MaxLength = 20;
        this.textGroupNum.Name = "textGroupNum";
        this.textGroupNum.Size = new System.Drawing.Size(129, 20);
        this.textGroupNum.TabIndex = 3;
        //
        // textAddress
        //
        this.textAddress.Location = new System.Drawing.Point(102, 51);
        this.textAddress.MaxLength = 60;
        this.textAddress.Name = "textAddress";
        this.textAddress.Size = new System.Drawing.Size(291, 20);
        this.textAddress.TabIndex = 2;
        this.textAddress.TextChanged += new System.EventHandler(this.textAddress_TextChanged);
        //
        // textCity
        //
        this.textCity.Location = new System.Drawing.Point(102, 91);
        this.textCity.MaxLength = 40;
        this.textCity.Name = "textCity";
        this.textCity.Size = new System.Drawing.Size(155, 20);
        this.textCity.TabIndex = 4;
        this.textCity.TextChanged += new System.EventHandler(this.textCity_TextChanged);
        //
        // textState
        //
        this.textState.Location = new System.Drawing.Point(257, 91);
        this.textState.MaxLength = 2;
        this.textState.Name = "textState";
        this.textState.Size = new System.Drawing.Size(65, 20);
        this.textState.TabIndex = 5;
        this.textState.TextChanged += new System.EventHandler(this.textState_TextChanged);
        //
        // textZip
        //
        this.textZip.Location = new System.Drawing.Point(322, 91);
        this.textZip.MaxLength = 10;
        this.textZip.Name = "textZip";
        this.textZip.Size = new System.Drawing.Size(71, 20);
        this.textZip.TabIndex = 6;
        //
        // textElectID
        //
        this.textElectID.Location = new System.Drawing.Point(102, 111);
        this.textElectID.MaxLength = 20;
        this.textElectID.Name = "textElectID";
        this.textElectID.Size = new System.Drawing.Size(54, 20);
        this.textElectID.TabIndex = 7;
        this.textElectID.Validating += new System.ComponentModel.CancelEventHandler(this.textElectID_Validating);
        //
        // textEmployer
        //
        this.textEmployer.Location = new System.Drawing.Point(112, 26);
        this.textEmployer.MaxLength = 40;
        this.textEmployer.Name = "textEmployer";
        this.textEmployer.Size = new System.Drawing.Size(291, 20);
        this.textEmployer.TabIndex = 0;
        this.textEmployer.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textEmployer_KeyUp);
        this.textEmployer.Leave += new System.EventHandler(this.textEmployer_Leave);
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(33, 28);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(78, 15);
        this.label16.TabIndex = 73;
        this.label16.Text = "Employer";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAddress2
        //
        this.textAddress2.Location = new System.Drawing.Point(102, 71);
        this.textAddress2.MaxLength = 60;
        this.textAddress2.Name = "textAddress2";
        this.textAddress2.Size = new System.Drawing.Size(291, 20);
        this.textAddress2.TabIndex = 3;
        this.textAddress2.TextChanged += new System.EventHandler(this.textAddress2_TextChanged);
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(5, 74);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(95, 15);
        this.label21.TabIndex = 79;
        this.label21.Text = "Address 2";
        this.label21.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 360);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(96, 16);
        this.label1.TabIndex = 91;
        this.label1.Text = "Fee Schedule";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkAssign
        //
        this.checkAssign.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkAssign.Location = new System.Drawing.Point(300, 54);
        this.checkAssign.Name = "checkAssign";
        this.checkAssign.Size = new System.Drawing.Size(199, 20);
        this.checkAssign.TabIndex = 4;
        this.checkAssign.Text = "Assignment of Benefits (pay dentist)";
        //
        // checkRelease
        //
        this.checkRelease.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkRelease.Location = new System.Drawing.Point(300, 36);
        this.checkRelease.Name = "checkRelease";
        this.checkRelease.Size = new System.Drawing.Size(199, 20);
        this.checkRelease.TabIndex = 3;
        this.checkRelease.Text = "Release of Information";
        //
        // checkNoSendElect
        //
        this.checkNoSendElect.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkNoSendElect.Location = new System.Drawing.Point(178, 133);
        this.checkNoSendElect.Name = "checkNoSendElect";
        this.checkNoSendElect.Size = new System.Drawing.Size(213, 17);
        this.checkNoSendElect.TabIndex = 8;
        this.checkNoSendElect.Text = "Don\'t Usually Send Electronically";
        //
        // label23
        //
        this.label23.Location = new System.Drawing.Point(17, 384);
        this.label23.Name = "label23";
        this.label23.Size = new System.Drawing.Size(95, 15);
        this.label23.TabIndex = 96;
        this.label23.Text = "Claim Form";
        this.label23.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkAlternateCode
        //
        this.checkAlternateCode.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkAlternateCode.Location = new System.Drawing.Point(115, 293);
        this.checkAlternateCode.Name = "checkAlternateCode";
        this.checkAlternateCode.Size = new System.Drawing.Size(275, 16);
        this.checkAlternateCode.TabIndex = 2;
        this.checkAlternateCode.Text = "Use Alternate Code (for some Medicaid plans)";
        //
        // checkClaimsUseUCR
        //
        this.checkClaimsUseUCR.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkClaimsUseUCR.Location = new System.Drawing.Point(115, 325);
        this.checkClaimsUseUCR.Name = "checkClaimsUseUCR";
        this.checkClaimsUseUCR.Size = new System.Drawing.Size(275, 16);
        this.checkClaimsUseUCR.TabIndex = 4;
        this.checkClaimsUseUCR.Text = "Claims show UCR fee, not billed fee";
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(20, 271);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(95, 16);
        this.label14.TabIndex = 104;
        this.label14.Text = "Plan Type";
        this.label14.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSubscriber
        //
        this.textSubscriber.Location = new System.Drawing.Point(109, 14);
        this.textSubscriber.Name = "textSubscriber";
        this.textSubscriber.ReadOnly = true;
        this.textSubscriber.Size = new System.Drawing.Size(298, 20);
        this.textSubscriber.TabIndex = 109;
        //
        // groupSubscriber
        //
        this.groupSubscriber.Controls.Add(this.butChange);
        this.groupSubscriber.Controls.Add(this.checkAssign);
        this.groupSubscriber.Controls.Add(this.label25);
        this.groupSubscriber.Controls.Add(this.checkRelease);
        this.groupSubscriber.Controls.Add(this.textSubscriber);
        this.groupSubscriber.Controls.Add(this.textSubscriberID);
        this.groupSubscriber.Controls.Add(this.label2);
        this.groupSubscriber.Controls.Add(this.textDateEffect);
        this.groupSubscriber.Controls.Add(this.label5);
        this.groupSubscriber.Controls.Add(this.textDateTerm);
        this.groupSubscriber.Controls.Add(this.label6);
        this.groupSubscriber.Controls.Add(this.textSubscNote);
        this.groupSubscriber.Controls.Add(this.label28);
        this.groupSubscriber.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupSubscriber.Location = new System.Drawing.Point(468, 94);
        this.groupSubscriber.Name = "groupSubscriber";
        this.groupSubscriber.Size = new System.Drawing.Size(502, 176);
        this.groupSubscriber.TabIndex = 2;
        this.groupSubscriber.TabStop = false;
        this.groupSubscriber.Text = "Subscriber Information";
        //
        // label25
        //
        this.label25.Location = new System.Drawing.Point(8, 18);
        this.label25.Name = "label25";
        this.label25.Size = new System.Drawing.Size(99, 15);
        this.label25.TabIndex = 115;
        this.label25.Text = "Name";
        this.label25.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSubscriberID
        //
        this.textSubscriberID.Location = new System.Drawing.Point(109, 34);
        this.textSubscriberID.MaxLength = 20;
        this.textSubscriberID.Name = "textSubscriberID";
        this.textSubscriberID.Size = new System.Drawing.Size(129, 20);
        this.textSubscriberID.TabIndex = 0;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 36);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(99, 15);
        this.label2.TabIndex = 114;
        this.label2.Text = "Subscriber ID";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboLinked
        //
        this.comboLinked.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboLinked.Location = new System.Drawing.Point(150, 240);
        this.comboLinked.MaxDropDownItems = 30;
        this.comboLinked.Name = "comboLinked";
        this.comboLinked.Size = new System.Drawing.Size(253, 21);
        this.comboLinked.TabIndex = 68;
        //
        // textLinkedNum
        //
        this.textLinkedNum.BackColor = System.Drawing.Color.White;
        this.textLinkedNum.Location = new System.Drawing.Point(112, 240);
        this.textLinkedNum.Multiline = true;
        this.textLinkedNum.Name = "textLinkedNum";
        this.textLinkedNum.ReadOnly = true;
        this.textLinkedNum.Size = new System.Drawing.Size(37, 21);
        this.textLinkedNum.TabIndex = 67;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(6, 242);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(104, 17);
        this.label4.TabIndex = 66;
        this.label4.Text = "Other Subscribers";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboPlanType
        //
        this.comboPlanType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPlanType.Location = new System.Drawing.Point(115, 270);
        this.comboPlanType.Name = "comboPlanType";
        this.comboPlanType.Size = new System.Drawing.Size(212, 21);
        this.comboPlanType.TabIndex = 1;
        this.comboPlanType.SelectionChangeCommitted += new System.EventHandler(this.comboPlanType_SelectionChangeCommitted);
        //
        // checkIsMedical
        //
        this.checkIsMedical.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsMedical.Location = new System.Drawing.Point(112, 9);
        this.checkIsMedical.Name = "checkIsMedical";
        this.checkIsMedical.Size = new System.Drawing.Size(202, 17);
        this.checkIsMedical.TabIndex = 113;
        this.checkIsMedical.Text = "Medical Insurance";
        //
        // textDivisionNo
        //
        this.textDivisionNo.Location = new System.Drawing.Point(296, 220);
        this.textDivisionNo.MaxLength = 20;
        this.textDivisionNo.Name = "textDivisionNo";
        this.textDivisionNo.Size = new System.Drawing.Size(107, 20);
        this.textDivisionNo.TabIndex = 3;
        //
        // labelDivisionDash
        //
        this.labelDivisionDash.Location = new System.Drawing.Point(242, 224);
        this.labelDivisionDash.Name = "labelDivisionDash";
        this.labelDivisionDash.Size = new System.Drawing.Size(53, 16);
        this.labelDivisionDash.TabIndex = 111;
        this.labelDivisionDash.Text = "Div. No.";
        this.labelDivisionDash.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClaimForm
        //
        this.comboClaimForm.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClaimForm.Location = new System.Drawing.Point(115, 381);
        this.comboClaimForm.MaxDropDownItems = 30;
        this.comboClaimForm.Name = "comboClaimForm";
        this.comboClaimForm.Size = new System.Drawing.Size(212, 21);
        this.comboClaimForm.TabIndex = 7;
        //
        // comboFeeSched
        //
        this.comboFeeSched.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboFeeSched.Location = new System.Drawing.Point(115, 359);
        this.comboFeeSched.MaxDropDownItems = 30;
        this.comboFeeSched.Name = "comboFeeSched";
        this.comboFeeSched.Size = new System.Drawing.Size(212, 21);
        this.comboFeeSched.TabIndex = 6;
        //
        // groupCoPay
        //
        this.groupCoPay.Controls.Add(this.label12);
        this.groupCoPay.Controls.Add(this.comboAllowedFeeSched);
        this.groupCoPay.Controls.Add(this.label11);
        this.groupCoPay.Controls.Add(this.label3);
        this.groupCoPay.Controls.Add(this.comboCopay);
        this.groupCoPay.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupCoPay.Location = new System.Drawing.Point(8, 409);
        this.groupCoPay.Name = "groupCoPay";
        this.groupCoPay.Size = new System.Drawing.Size(404, 99);
        this.groupCoPay.TabIndex = 8;
        this.groupCoPay.TabStop = false;
        this.groupCoPay.Text = "Other Fee Schedules";
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(1, 67);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(105, 27);
        this.label12.TabIndex = 111;
        this.label12.Text = "Carrier Allowed Amounts";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboAllowedFeeSched
        //
        this.comboAllowedFeeSched.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboAllowedFeeSched.Location = new System.Drawing.Point(107, 67);
        this.comboAllowedFeeSched.MaxDropDownItems = 30;
        this.comboAllowedFeeSched.Name = "comboAllowedFeeSched";
        this.comboAllowedFeeSched.Size = new System.Drawing.Size(212, 21);
        this.comboAllowedFeeSched.TabIndex = 1;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(1, 36);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(105, 26);
        this.label11.TabIndex = 109;
        this.label11.Text = "Patient Co-pay Amounts";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(1, 19);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(390, 17);
        this.label3.TabIndex = 106;
        this.label3.Text = "Don\'t use these unless you understand how they will affect your estimates";
        //
        // comboCopay
        //
        this.comboCopay.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCopay.Location = new System.Drawing.Point(107, 37);
        this.comboCopay.MaxDropDownItems = 30;
        this.comboCopay.Name = "comboCopay";
        this.comboCopay.Size = new System.Drawing.Size(212, 21);
        this.comboCopay.TabIndex = 0;
        //
        // comboElectIDdescript
        //
        this.comboElectIDdescript.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboElectIDdescript.Location = new System.Drawing.Point(156, 111);
        this.comboElectIDdescript.MaxDropDownItems = 30;
        this.comboElectIDdescript.Name = "comboElectIDdescript";
        this.comboElectIDdescript.Size = new System.Drawing.Size(237, 21);
        this.comboElectIDdescript.TabIndex = 125;
        this.comboElectIDdescript.SelectedIndexChanged += new System.EventHandler(this.comboElectIDdescript_SelectedIndexChanged);
        //
        // labelDrop
        //
        this.labelDrop.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelDrop.Location = new System.Drawing.Point(101, 72);
        this.labelDrop.Name = "labelDrop";
        this.labelDrop.Size = new System.Drawing.Size(554, 15);
        this.labelDrop.TabIndex = 124;
        this.labelDrop.Text = "Drop a plan when a patient changes carriers or is no longer covered.  This does n" + "ot delete the plan.";
        this.labelDrop.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupRequestBen
        //
        this.groupRequestBen.Controls.Add(this.butHistoryElect);
        this.groupRequestBen.Controls.Add(this.labelHistElect);
        this.groupRequestBen.Controls.Add(this.textElectBenLastDate);
        this.groupRequestBen.Controls.Add(this.butImportTrojan);
        this.groupRequestBen.Controls.Add(this.butGetElectronic);
        this.groupRequestBen.Controls.Add(this.butBenefitNotes);
        this.groupRequestBen.Controls.Add(this.butIapFind);
        this.groupRequestBen.Controls.Add(this.labelTrojanID);
        this.groupRequestBen.Controls.Add(this.textTrojanID);
        this.groupRequestBen.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupRequestBen.Location = new System.Drawing.Point(468, 269);
        this.groupRequestBen.Name = "groupRequestBen";
        this.groupRequestBen.Size = new System.Drawing.Size(502, 63);
        this.groupRequestBen.TabIndex = 10;
        this.groupRequestBen.TabStop = false;
        this.groupRequestBen.Text = "Request Electronic Benefits";
        //
        // labelHistElect
        //
        this.labelHistElect.Location = new System.Drawing.Point(3, 19);
        this.labelHistElect.Name = "labelHistElect";
        this.labelHistElect.Size = new System.Drawing.Size(84, 15);
        this.labelHistElect.TabIndex = 119;
        this.labelHistElect.Text = "Last Request";
        this.labelHistElect.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textElectBenLastDate
        //
        this.textElectBenLastDate.Location = new System.Drawing.Point(89, 17);
        this.textElectBenLastDate.MaxLength = 30;
        this.textElectBenLastDate.Name = "textElectBenLastDate";
        this.textElectBenLastDate.Size = new System.Drawing.Size(73, 20);
        this.textElectBenLastDate.TabIndex = 118;
        //
        // labelTrojanID
        //
        this.labelTrojanID.Location = new System.Drawing.Point(360, 18);
        this.labelTrojanID.Name = "labelTrojanID";
        this.labelTrojanID.Size = new System.Drawing.Size(23, 15);
        this.labelTrojanID.TabIndex = 9;
        this.labelTrojanID.Text = "ID";
        this.labelTrojanID.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textTrojanID
        //
        this.textTrojanID.Location = new System.Drawing.Point(387, 14);
        this.textTrojanID.MaxLength = 30;
        this.textTrojanID.Name = "textTrojanID";
        this.textTrojanID.Size = new System.Drawing.Size(109, 20);
        this.textTrojanID.TabIndex = 8;
        //
        // label26
        //
        this.label26.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label26.Location = new System.Drawing.Point(20, 22);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(148, 14);
        this.label26.TabIndex = 127;
        this.label26.Text = "Relationship to Subscriber";
        this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panel1
        //
        this.panel1.BackColor = System.Drawing.SystemColors.ControlText;
        this.panel1.Location = new System.Drawing.Point(0, 90);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(988, 2);
        this.panel1.TabIndex = 128;
        //
        // comboRelationship
        //
        this.comboRelationship.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboRelationship.Location = new System.Drawing.Point(170, 18);
        this.comboRelationship.MaxDropDownItems = 30;
        this.comboRelationship.Name = "comboRelationship";
        this.comboRelationship.Size = new System.Drawing.Size(151, 21);
        this.comboRelationship.TabIndex = 0;
        //
        // label31
        //
        this.label31.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label31.Location = new System.Drawing.Point(396, 23);
        this.label31.Name = "label31";
        this.label31.Size = new System.Drawing.Size(109, 14);
        this.label31.TabIndex = 130;
        this.label31.Text = "Order";
        this.label31.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsPending
        //
        this.checkIsPending.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsPending.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsPending.Location = new System.Drawing.Point(396, 42);
        this.checkIsPending.Name = "checkIsPending";
        this.checkIsPending.Size = new System.Drawing.Size(125, 16);
        this.checkIsPending.TabIndex = 3;
        this.checkIsPending.Text = "Pending";
        this.checkIsPending.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label32
        //
        this.label32.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label32.Location = new System.Drawing.Point(5, 94);
        this.label32.Name = "label32";
        this.label32.Size = new System.Drawing.Size(304, 19);
        this.label32.TabIndex = 134;
        this.label32.Text = "Insurance Plan Information";
        //
        // label33
        //
        this.label33.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label33.Location = new System.Drawing.Point(5, 0);
        this.label33.Name = "label33";
        this.label33.Size = new System.Drawing.Size(188, 19);
        this.label33.TabIndex = 135;
        this.label33.Text = "Patient Information";
        //
        // listAdj
        //
        this.listAdj.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.listAdj.Items.AddRange(new Object[]{ "03/05/2001       Ins Used:  $124.00       Ded Used:  $50.00", "03/05/2002       Ins Used:  $0.00       Ded Used:  $50.00" });
        this.listAdj.Location = new System.Drawing.Point(613, 28);
        this.listAdj.Name = "listAdj";
        this.listAdj.Size = new System.Drawing.Size(341, 56);
        this.listAdj.TabIndex = 137;
        this.listAdj.DoubleClick += new System.EventHandler(this.listAdj_DoubleClick);
        //
        // label35
        //
        this.label35.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label35.Location = new System.Drawing.Point(613, 8);
        this.label35.Name = "label35";
        this.label35.Size = new System.Drawing.Size(218, 17);
        this.label35.TabIndex = 138;
        this.label35.Text = "Adjustments to Insurance Benefits: ";
        this.label35.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textPatID
        //
        this.textPatID.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.textPatID.Location = new System.Drawing.Point(170, 40);
        this.textPatID.MaxLength = 100;
        this.textPatID.Name = "textPatID";
        this.textPatID.Size = new System.Drawing.Size(151, 20);
        this.textPatID.TabIndex = 1;
        //
        // labelPatID
        //
        this.labelPatID.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelPatID.Location = new System.Drawing.Point(30, 42);
        this.labelPatID.Name = "labelPatID";
        this.labelPatID.Size = new System.Drawing.Size(138, 16);
        this.labelPatID.TabIndex = 143;
        this.labelPatID.Text = "Optional Patient ID";
        this.labelPatID.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panelPat
        //
        this.panelPat.Controls.Add(this.comboRelationship);
        this.panelPat.Controls.Add(this.label33);
        this.panelPat.Controls.Add(this.textOrdinal);
        this.panelPat.Controls.Add(this.butAdjAdd);
        this.panelPat.Controls.Add(this.listAdj);
        this.panelPat.Controls.Add(this.label35);
        this.panelPat.Controls.Add(this.textPatID);
        this.panelPat.Controls.Add(this.labelPatID);
        this.panelPat.Controls.Add(this.labelDrop);
        this.panelPat.Controls.Add(this.butDrop);
        this.panelPat.Controls.Add(this.label26);
        this.panelPat.Controls.Add(this.label31);
        this.panelPat.Controls.Add(this.checkIsPending);
        this.panelPat.Location = new System.Drawing.Point(0, 0);
        this.panelPat.Name = "panelPat";
        this.panelPat.Size = new System.Drawing.Size(982, 90);
        this.panelPat.TabIndex = 15;
        //
        // textPlanNum
        //
        this.textPlanNum.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textPlanNum.Location = new System.Drawing.Point(310, 676);
        this.textPlanNum.Name = "textPlanNum";
        this.textPlanNum.Size = new System.Drawing.Size(100, 20);
        this.textPlanNum.TabIndex = 148;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(7, 13);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(94, 15);
        this.label17.TabIndex = 152;
        this.label17.Text = "Carrier";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(12, 563);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(272, 15);
        this.label18.TabIndex = 156;
        this.label18.Text = "Plan Note";
        this.label18.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupPlan
        //
        this.groupPlan.Controls.Add(this.textBIN);
        this.groupPlan.Controls.Add(this.labelBIN);
        this.groupPlan.Controls.Add(this.textDivisionNo);
        this.groupPlan.Controls.Add(this.textGroupName);
        this.groupPlan.Controls.Add(this.textEmployer);
        this.groupPlan.Controls.Add(this.groupCarrier);
        this.groupPlan.Controls.Add(this.checkIsMedical);
        this.groupPlan.Controls.Add(this.textGroupNum);
        this.groupPlan.Controls.Add(this.labelGroupNum);
        this.groupPlan.Controls.Add(this.label8);
        this.groupPlan.Controls.Add(this.comboLinked);
        this.groupPlan.Controls.Add(this.textLinkedNum);
        this.groupPlan.Controls.Add(this.label16);
        this.groupPlan.Controls.Add(this.label4);
        this.groupPlan.Controls.Add(this.labelDivisionDash);
        this.groupPlan.Location = new System.Drawing.Point(3, 3);
        this.groupPlan.Name = "groupPlan";
        this.groupPlan.Size = new System.Drawing.Size(425, 264);
        this.groupPlan.TabIndex = 0;
        this.groupPlan.TabStop = false;
        //
        // textBIN
        //
        this.textBIN.Location = new System.Drawing.Point(341, 200);
        this.textBIN.MaxLength = 20;
        this.textBIN.Name = "textBIN";
        this.textBIN.Size = new System.Drawing.Size(62, 20);
        this.textBIN.TabIndex = 115;
        //
        // labelBIN
        //
        this.labelBIN.Location = new System.Drawing.Point(307, 201);
        this.labelBIN.Name = "labelBIN";
        this.labelBIN.Size = new System.Drawing.Size(32, 16);
        this.labelBIN.TabIndex = 114;
        this.labelBIN.Text = "BIN";
        this.labelBIN.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupCarrier
        //
        this.groupCarrier.Controls.Add(this.butPickCarrier);
        this.groupCarrier.Controls.Add(this.textPhone);
        this.groupCarrier.Controls.Add(this.textAddress);
        this.groupCarrier.Controls.Add(this.comboElectIDdescript);
        this.groupCarrier.Controls.Add(this.textElectID);
        this.groupCarrier.Controls.Add(this.butSearch);
        this.groupCarrier.Controls.Add(this.textAddress2);
        this.groupCarrier.Controls.Add(this.textZip);
        this.groupCarrier.Controls.Add(this.checkNoSendElect);
        this.groupCarrier.Controls.Add(this.label10);
        this.groupCarrier.Controls.Add(this.textCity);
        this.groupCarrier.Controls.Add(this.label7);
        this.groupCarrier.Controls.Add(this.textCarrier);
        this.groupCarrier.Controls.Add(this.labelElectronicID);
        this.groupCarrier.Controls.Add(this.label21);
        this.groupCarrier.Controls.Add(this.label17);
        this.groupCarrier.Controls.Add(this.textState);
        this.groupCarrier.Controls.Add(this.labelCitySTZip);
        this.groupCarrier.Location = new System.Drawing.Point(10, 44);
        this.groupCarrier.Name = "groupCarrier";
        this.groupCarrier.Size = new System.Drawing.Size(402, 155);
        this.groupCarrier.TabIndex = 1;
        this.groupCarrier.TabStop = false;
        this.groupCarrier.Text = "Carrier";
        //
        // panelPlan
        //
        this.panelPlan.AutoScroll = true;
        this.panelPlan.AutoScrollMargin = new System.Drawing.Size(0, 10);
        this.panelPlan.BackColor = System.Drawing.SystemColors.Control;
        this.panelPlan.Controls.Add(this.comboCobRule);
        this.panelPlan.Controls.Add(this.label20);
        this.panelPlan.Controls.Add(this.groupCanadian);
        this.panelPlan.Controls.Add(this.comboFilingCodeSubtype);
        this.panelPlan.Controls.Add(this.label15);
        this.panelPlan.Controls.Add(this.checkIsHidden);
        this.panelPlan.Controls.Add(this.checkCodeSubst);
        this.panelPlan.Controls.Add(this.checkShowBaseUnits);
        this.panelPlan.Controls.Add(this.comboFeeSched);
        this.panelPlan.Controls.Add(this.groupPlan);
        this.panelPlan.Controls.Add(this.comboFilingCode);
        this.panelPlan.Controls.Add(this.groupCoPay);
        this.panelPlan.Controls.Add(this.comboPlanType);
        this.panelPlan.Controls.Add(this.label13);
        this.panelPlan.Controls.Add(this.comboClaimForm);
        this.panelPlan.Controls.Add(this.label1);
        this.panelPlan.Controls.Add(this.label14);
        this.panelPlan.Controls.Add(this.label23);
        this.panelPlan.Controls.Add(this.checkAlternateCode);
        this.panelPlan.Controls.Add(this.checkClaimsUseUCR);
        this.panelPlan.Location = new System.Drawing.Point(8, 116);
        this.panelPlan.Name = "panelPlan";
        this.panelPlan.Size = new System.Drawing.Size(454, 438);
        this.panelPlan.TabIndex = 0;
        //
        // comboCobRule
        //
        this.comboCobRule.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboCobRule.Location = new System.Drawing.Point(115, 512);
        this.comboCobRule.MaxDropDownItems = 30;
        this.comboCobRule.Name = "comboCobRule";
        this.comboCobRule.Size = new System.Drawing.Size(111, 21);
        this.comboCobRule.TabIndex = 167;
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(17, 515);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(95, 15);
        this.label20.TabIndex = 168;
        this.label20.Text = "COB Rule";
        this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupCanadian
        //
        this.groupCanadian.Controls.Add(this.label19);
        this.groupCanadian.Controls.Add(this.textCanadianInstCode);
        this.groupCanadian.Controls.Add(this.label9);
        this.groupCanadian.Controls.Add(this.textCanadianDiagCode);
        this.groupCanadian.Controls.Add(this.checkIsPMP);
        this.groupCanadian.Controls.Add(this.label24);
        this.groupCanadian.Controls.Add(this.label22);
        this.groupCanadian.Controls.Add(this.textPlanFlag);
        this.groupCanadian.Controls.Add(this.textDentaide);
        this.groupCanadian.Controls.Add(this.labelDentaide);
        this.groupCanadian.Location = new System.Drawing.Point(8, 600);
        this.groupCanadian.Name = "groupCanadian";
        this.groupCanadian.Size = new System.Drawing.Size(404, 129);
        this.groupCanadian.TabIndex = 12;
        this.groupCanadian.TabStop = false;
        this.groupCanadian.Text = "Canadian";
        //
        // label19
        //
        this.label19.Location = new System.Drawing.Point(37, 106);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(140, 19);
        this.label19.TabIndex = 173;
        this.label19.Text = "Institution Code";
        this.label19.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCanadianInstCode
        //
        this.textCanadianInstCode.Location = new System.Drawing.Point(181, 103);
        this.textCanadianInstCode.MaxLength = 20;
        this.textCanadianInstCode.Name = "textCanadianInstCode";
        this.textCanadianInstCode.Size = new System.Drawing.Size(88, 20);
        this.textCanadianInstCode.TabIndex = 172;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(37, 85);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(140, 19);
        this.label9.TabIndex = 171;
        this.label9.Text = "Diagnostic Code";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textCanadianDiagCode
        //
        this.textCanadianDiagCode.Location = new System.Drawing.Point(181, 82);
        this.textCanadianDiagCode.MaxLength = 20;
        this.textCanadianDiagCode.Name = "textCanadianDiagCode";
        this.textCanadianDiagCode.Size = new System.Drawing.Size(88, 20);
        this.textCanadianDiagCode.TabIndex = 170;
        //
        // checkIsPMP
        //
        this.checkIsPMP.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsPMP.Location = new System.Drawing.Point(18, 62);
        this.checkIsPMP.Name = "checkIsPMP";
        this.checkIsPMP.Size = new System.Drawing.Size(178, 17);
        this.checkIsPMP.TabIndex = 169;
        this.checkIsPMP.Text = "Is Provincial Medical Plan";
        this.checkIsPMP.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsPMP.UseVisualStyleBackColor = true;
        //
        // label24
        //
        this.label24.Location = new System.Drawing.Point(221, 39);
        this.label24.Name = "label24";
        this.label24.Size = new System.Drawing.Size(140, 19);
        this.label24.TabIndex = 168;
        this.label24.Text = "A, V, N, or blank";
        this.label24.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label22
        //
        this.label22.Location = new System.Drawing.Point(37, 41);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(140, 19);
        this.label22.TabIndex = 167;
        this.label22.Text = "Plan Flag";
        this.label22.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPlanFlag
        //
        this.textPlanFlag.Location = new System.Drawing.Point(181, 38);
        this.textPlanFlag.MaxLength = 20;
        this.textPlanFlag.Name = "textPlanFlag";
        this.textPlanFlag.Size = new System.Drawing.Size(37, 20);
        this.textPlanFlag.TabIndex = 1;
        //
        // labelDentaide
        //
        this.labelDentaide.Location = new System.Drawing.Point(37, 20);
        this.labelDentaide.Name = "labelDentaide";
        this.labelDentaide.Size = new System.Drawing.Size(140, 19);
        this.labelDentaide.TabIndex = 160;
        this.labelDentaide.Text = "Dentaide Card Sequence";
        this.labelDentaide.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboFilingCodeSubtype
        //
        this.comboFilingCodeSubtype.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboFilingCodeSubtype.Location = new System.Drawing.Point(115, 556);
        this.comboFilingCodeSubtype.MaxDropDownItems = 30;
        this.comboFilingCodeSubtype.Name = "comboFilingCodeSubtype";
        this.comboFilingCodeSubtype.Size = new System.Drawing.Size(212, 21);
        this.comboFilingCodeSubtype.TabIndex = 10;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(3, 558);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(110, 19);
        this.label15.TabIndex = 166;
        this.label15.Text = "Filing Code Subtype";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkIsHidden
        //
        this.checkIsHidden.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsHidden.Location = new System.Drawing.Point(115, 341);
        this.checkIsHidden.Name = "checkIsHidden";
        this.checkIsHidden.Size = new System.Drawing.Size(275, 16);
        this.checkIsHidden.TabIndex = 5;
        this.checkIsHidden.Text = "Hidden";
        //
        // checkCodeSubst
        //
        this.checkCodeSubst.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkCodeSubst.Location = new System.Drawing.Point(115, 309);
        this.checkCodeSubst.Name = "checkCodeSubst";
        this.checkCodeSubst.Size = new System.Drawing.Size(275, 16);
        this.checkCodeSubst.TabIndex = 3;
        this.checkCodeSubst.Text = "Don\'t Substitute Codes (e.g. posterior composites)";
        //
        // checkShowBaseUnits
        //
        this.checkShowBaseUnits.Location = new System.Drawing.Point(115, 582);
        this.checkShowBaseUnits.Name = "checkShowBaseUnits";
        this.checkShowBaseUnits.Size = new System.Drawing.Size(289, 17);
        this.checkShowBaseUnits.TabIndex = 11;
        this.checkShowBaseUnits.Text = "Claims show base units (Does not affect billed amount)";
        this.checkShowBaseUnits.UseVisualStyleBackColor = true;
        //
        // comboFilingCode
        //
        this.comboFilingCode.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboFilingCode.Location = new System.Drawing.Point(115, 534);
        this.comboFilingCode.MaxDropDownItems = 30;
        this.comboFilingCode.Name = "comboFilingCode";
        this.comboFilingCode.Size = new System.Drawing.Size(212, 21);
        this.comboFilingCode.TabIndex = 9;
        this.comboFilingCode.SelectionChangeCommitted += new System.EventHandler(this.comboFilingCode_SelectionChangeCommitted);
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(13, 536);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(100, 19);
        this.label13.TabIndex = 158;
        this.label13.Text = "Filing Code";
        this.label13.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // radioChangeAll
        //
        this.radioChangeAll.Location = new System.Drawing.Point(6, 25);
        this.radioChangeAll.Name = "radioChangeAll";
        this.radioChangeAll.Size = new System.Drawing.Size(211, 17);
        this.radioChangeAll.TabIndex = 158;
        this.radioChangeAll.Text = "Change Plan for all subscribers";
        this.radioChangeAll.UseVisualStyleBackColor = true;
        //
        // groupChanges
        //
        this.groupChanges.Controls.Add(this.radioCreateNew);
        this.groupChanges.Controls.Add(this.radioChangeAll);
        this.groupChanges.Location = new System.Drawing.Point(467, 653);
        this.groupChanges.Name = "groupChanges";
        this.groupChanges.Size = new System.Drawing.Size(240, 45);
        this.groupChanges.TabIndex = 159;
        this.groupChanges.TabStop = false;
        //
        // radioCreateNew
        //
        this.radioCreateNew.Checked = true;
        this.radioCreateNew.Location = new System.Drawing.Point(6, 8);
        this.radioCreateNew.Name = "radioCreateNew";
        this.radioCreateNew.Size = new System.Drawing.Size(211, 17);
        this.radioCreateNew.TabIndex = 159;
        this.radioCreateNew.TabStop = true;
        this.radioCreateNew.Text = "Create new Plan if needed";
        this.radioCreateNew.UseVisualStyleBackColor = true;
        //
        // gridBenefits
        //
        this.gridBenefits.setHScrollVisible(false);
        this.gridBenefits.Location = new System.Drawing.Point(468, 332);
        this.gridBenefits.Name = "gridBenefits";
        this.gridBenefits.setScrollValue(0);
        this.gridBenefits.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridBenefits.Size = new System.Drawing.Size(502, 326);
        this.gridBenefits.TabIndex = 146;
        this.gridBenefits.setTitle("Benefit Information");
        this.gridBenefits.setTranslationName("TableBenefits");
        this.gridBenefits.DoubleClick += new System.EventHandler(this.gridBenefits_DoubleClick);
        //
        // textDentaide
        //
        this.textDentaide.Location = new System.Drawing.Point(181, 17);
        this.textDentaide.setMaxVal(255);
        this.textDentaide.setMinVal(0);
        this.textDentaide.Name = "textDentaide";
        this.textDentaide.Size = new System.Drawing.Size(37, 20);
        this.textDentaide.TabIndex = 0;
        //
        // butPickCarrier
        //
        this.butPickCarrier.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickCarrier.setAutosize(true);
        this.butPickCarrier.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickCarrier.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickCarrier.setCornerRadius(3F);
        this.butPickCarrier.Location = new System.Drawing.Point(376, 11);
        this.butPickCarrier.Name = "butPickCarrier";
        this.butPickCarrier.Size = new System.Drawing.Size(19, 20);
        this.butPickCarrier.TabIndex = 153;
        this.butPickCarrier.Text = "...";
        this.butPickCarrier.Click += new System.EventHandler(this.butPickCarrier_Click);
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(86, 132);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(84, 20);
        this.butSearch.TabIndex = 124;
        this.butSearch.Text = "Search IDs";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butAudit
        //
        this.butAudit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAudit.setAutosize(true);
        this.butAudit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAudit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAudit.setCornerRadius(4F);
        this.butAudit.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAudit.Location = new System.Drawing.Point(229, 93);
        this.butAudit.Name = "butAudit";
        this.butAudit.Size = new System.Drawing.Size(69, 23);
        this.butAudit.TabIndex = 153;
        this.butAudit.Text = "Audit Trail";
        this.butAudit.Click += new System.EventHandler(this.butAudit_Click);
        //
        // butPick
        //
        this.butPick.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPick.setAutosize(true);
        this.butPick.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPick.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPick.setCornerRadius(4F);
        this.butPick.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPick.Location = new System.Drawing.Point(326, 93);
        this.butPick.Name = "butPick";
        this.butPick.Size = new System.Drawing.Size(90, 23);
        this.butPick.TabIndex = 153;
        this.butPick.Text = "Pick From List";
        this.butPick.Click += new System.EventHandler(this.butPick_Click);
        //
        // textPlanNote
        //
        this.textPlanNote.AcceptsTab = true;
        this.textPlanNote.DetectUrls = false;
        this.textPlanNote.Location = new System.Drawing.Point(14, 581);
        this.textPlanNote.Name = "textPlanNote";
        this.textPlanNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.InsPlan);
        this.textPlanNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textPlanNote.Size = new System.Drawing.Size(395, 85);
        this.textPlanNote.TabIndex = 1;
        this.textPlanNote.Text = "1 - InsPlan\n2\n3 lines will show here in 46 vert.\n4 lines will show here in 59 ver" + "t.\n5 lines in 72 vert\n6 in 85";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(810, 673);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textOrdinal
        //
        this.textOrdinal.Location = new System.Drawing.Point(508, 22);
        this.textOrdinal.setMaxVal(10);
        this.textOrdinal.setMinVal(1);
        this.textOrdinal.Name = "textOrdinal";
        this.textOrdinal.Size = new System.Drawing.Size(45, 20);
        this.textOrdinal.TabIndex = 2;
        //
        // butAdjAdd
        //
        this.butAdjAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdjAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdjAdd.setAutosize(true);
        this.butAdjAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdjAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdjAdd.setCornerRadius(4F);
        this.butAdjAdd.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.butAdjAdd.Location = new System.Drawing.Point(895, 6);
        this.butAdjAdd.Name = "butAdjAdd";
        this.butAdjAdd.Size = new System.Drawing.Size(59, 21);
        this.butAdjAdd.TabIndex = 4;
        this.butAdjAdd.Text = "Add";
        this.butAdjAdd.Click += new System.EventHandler(this.butAdjAdd_Click);
        //
        // butDrop
        //
        this.butDrop.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDrop.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDrop.setAutosize(true);
        this.butDrop.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDrop.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDrop.setCornerRadius(4F);
        this.butDrop.Location = new System.Drawing.Point(7, 67);
        this.butDrop.Name = "butDrop";
        this.butDrop.Size = new System.Drawing.Size(72, 21);
        this.butDrop.TabIndex = 5;
        this.butDrop.Text = "Drop";
        this.butDrop.Click += new System.EventHandler(this.butDrop_Click);
        //
        // butLabel
        //
        this.butLabel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLabel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butLabel.setAutosize(true);
        this.butLabel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLabel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLabel.setCornerRadius(4F);
        this.butLabel.Image = Resources.getbutLabel();
        this.butLabel.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butLabel.Location = new System.Drawing.Point(201, 673);
        this.butLabel.Name = "butLabel";
        this.butLabel.Size = new System.Drawing.Size(81, 24);
        this.butLabel.TabIndex = 125;
        this.butLabel.Text = "Label";
        this.butLabel.Click += new System.EventHandler(this.butLabel_Click);
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(13, 673);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(81, 24);
        this.butDelete.TabIndex = 112;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
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
        this.butCancel.Location = new System.Drawing.Point(896, 673);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 14;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butHistoryElect
        //
        this.butHistoryElect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHistoryElect.setAutosize(true);
        this.butHistoryElect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHistoryElect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHistoryElect.setCornerRadius(4F);
        this.butHistoryElect.Location = new System.Drawing.Point(89, 38);
        this.butHistoryElect.Name = "butHistoryElect";
        this.butHistoryElect.Size = new System.Drawing.Size(73, 21);
        this.butHistoryElect.TabIndex = 120;
        this.butHistoryElect.Text = "History";
        this.toolTip1.SetToolTip(this.butHistoryElect, "Edit all the similar plans at once");
        this.butHistoryElect.Click += new System.EventHandler(this.butHistoryElect_Click);
        //
        // butImportTrojan
        //
        this.butImportTrojan.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butImportTrojan.setAutosize(true);
        this.butImportTrojan.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butImportTrojan.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butImportTrojan.setCornerRadius(4F);
        this.butImportTrojan.Location = new System.Drawing.Point(294, 15);
        this.butImportTrojan.Name = "butImportTrojan";
        this.butImportTrojan.Size = new System.Drawing.Size(69, 21);
        this.butImportTrojan.TabIndex = 0;
        this.butImportTrojan.Text = "Trojan";
        this.toolTip1.SetToolTip(this.butImportTrojan, "Edit all the similar plans at once");
        this.butImportTrojan.Click += new System.EventHandler(this.butImportTrojan_Click);
        //
        // butGetElectronic
        //
        this.butGetElectronic.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGetElectronic.setAutosize(true);
        this.butGetElectronic.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGetElectronic.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGetElectronic.setCornerRadius(4F);
        this.butGetElectronic.Location = new System.Drawing.Point(11, 38);
        this.butGetElectronic.Name = "butGetElectronic";
        this.butGetElectronic.Size = new System.Drawing.Size(73, 21);
        this.butGetElectronic.TabIndex = 116;
        this.butGetElectronic.Text = "Request";
        this.toolTip1.SetToolTip(this.butGetElectronic, "Edit all the similar plans at once");
        this.butGetElectronic.Click += new System.EventHandler(this.butGetElectronic_Click);
        //
        // butBenefitNotes
        //
        this.butBenefitNotes.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBenefitNotes.setAutosize(true);
        this.butBenefitNotes.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBenefitNotes.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBenefitNotes.setCornerRadius(4F);
        this.butBenefitNotes.Location = new System.Drawing.Point(387, 38);
        this.butBenefitNotes.Name = "butBenefitNotes";
        this.butBenefitNotes.Size = new System.Drawing.Size(98, 21);
        this.butBenefitNotes.TabIndex = 2;
        this.butBenefitNotes.Text = "Trojan/IAP Note";
        this.toolTip1.SetToolTip(this.butBenefitNotes, "Edit all the similar plans at once");
        this.butBenefitNotes.Click += new System.EventHandler(this.butBenefitNotes_Click);
        //
        // butIapFind
        //
        this.butIapFind.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butIapFind.setAutosize(true);
        this.butIapFind.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butIapFind.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butIapFind.setCornerRadius(4F);
        this.butIapFind.Location = new System.Drawing.Point(294, 38);
        this.butIapFind.Name = "butIapFind";
        this.butIapFind.Size = new System.Drawing.Size(69, 21);
        this.butIapFind.TabIndex = 1;
        this.butIapFind.Text = "IAP";
        this.toolTip1.SetToolTip(this.butIapFind, "Edit all the similar plans at once");
        this.butIapFind.Click += new System.EventHandler(this.butIapFind_Click);
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(413, 13);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(73, 21);
        this.butChange.TabIndex = 121;
        this.butChange.Text = "Change";
        this.toolTip1.SetToolTip(this.butChange, "Change subscriber name");
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // textDateEffect
        //
        this.textDateEffect.Location = new System.Drawing.Point(109, 54);
        this.textDateEffect.Name = "textDateEffect";
        this.textDateEffect.Size = new System.Drawing.Size(72, 20);
        this.textDateEffect.TabIndex = 1;
        //
        // textDateTerm
        //
        this.textDateTerm.Location = new System.Drawing.Point(221, 54);
        this.textDateTerm.Name = "textDateTerm";
        this.textDateTerm.Size = new System.Drawing.Size(72, 20);
        this.textDateTerm.TabIndex = 2;
        //
        // textSubscNote
        //
        this.textSubscNote.AcceptsTab = true;
        this.textSubscNote.DetectUrls = false;
        this.textSubscNote.Location = new System.Drawing.Point(57, 75);
        this.textSubscNote.Name = "textSubscNote";
        this.textSubscNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.InsPlan);
        this.textSubscNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textSubscNote.Size = new System.Drawing.Size(439, 98);
        this.textSubscNote.TabIndex = 5;
        this.textSubscNote.Text = "1 - InsPlan subscriber\n2\n3 lines will show here in 46 vert.\n4 lines will show her" + "e in 59 vert.\n5 lines in 72 vert\n6 lines in 85 vert\n7 lines in 98";
        //
        // FormInsPlan
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(982, 700);
        this.Controls.Add(this.gridBenefits);
        this.Controls.Add(this.groupChanges);
        this.Controls.Add(this.panelPlan);
        this.Controls.Add(this.butAudit);
        this.Controls.Add(this.butPick);
        this.Controls.Add(this.textPlanNote);
        this.Controls.Add(this.label18);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textPlanNum);
        this.Controls.Add(this.panelPat);
        this.Controls.Add(this.butLabel);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label32);
        this.Controls.Add(this.groupRequestBen);
        this.Controls.Add(this.groupSubscriber);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormInsPlan";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Insurance Plan";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormInsPlan_Closing);
        this.Load += new System.EventHandler(this.FormInsPlan_Load);
        this.groupSubscriber.ResumeLayout(false);
        this.groupSubscriber.PerformLayout();
        this.groupCoPay.ResumeLayout(false);
        this.groupRequestBen.ResumeLayout(false);
        this.groupRequestBen.PerformLayout();
        this.panelPat.ResumeLayout(false);
        this.panelPat.PerformLayout();
        this.groupPlan.ResumeLayout(false);
        this.groupPlan.PerformLayout();
        this.groupCarrier.ResumeLayout(false);
        this.groupCarrier.PerformLayout();
        this.panelPlan.ResumeLayout(false);
        this.groupCanadian.ResumeLayout(false);
        this.groupCanadian.PerformLayout();
        this.groupChanges.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formInsPlan_Load(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        PlanNumOriginal = PlanCur.PlanNum;
        PlanCurOriginal = PlanCur.copy();
        long patPlanNum = 0;
        if (PatPlanCur != null)
        {
            patPlanNum = PatPlanCur.PatPlanNum;
        }
         
        if (SubCur == null)
        {
            //editing from big list
            butPick.Visible = false;
            //This prevents an infinite loop
            //groupRequestBen.Visible=false;//might try to make this functional later, but not now.
            //groupRequestBen:---------------------------------------------
            butGetElectronic.Visible = false;
            butHistoryElect.Visible = false;
            labelHistElect.Visible = false;
            textElectBenLastDate.Visible = false;
            butImportTrojan.Visible = false;
            butIapFind.Visible = false;
            textTrojanID.Enabled = false;
            //view only
            butBenefitNotes.Visible = false;
            //end of groupRequestBen
            groupSubscriber.Visible = false;
            //radioChangeAll.Checked=true;//this logic needs to be repeated in OK.
            groupChanges.Visible = false;
            //benefitList=Benefits.RefreshForAll(PlanCur);
            //if(IsReadOnly) {
            //	butOK.Enabled=false;
            //}
            butDelete.Visible = false;
        }
        else
        {
            //editing from a patient
            if (PrefC.getBool(PrefName.InsurancePlansShared))
            {
                radioChangeAll.Checked = true;
            }
             
        } 
        if (IsNewPlan)
        {
            //Regardless of whether from big list or from individual patient.  Overrides above settings.
            //radioCreateNew.Checked=true;//this logic needs to be repeated in OK.
            groupChanges.Visible = false;
            //because it wouldn't make sense to apply anything to "all"
            if (PrefC.getBool(PrefName.InsDefaultPPOpercent))
            {
                PlanCur.PlanType = "p";
            }
             
            PlanCur.CobRule = EnumCobRule.values()[PrefC.getInt(PrefName.InsDefaultCobRule)];
        }
         
        benefitList = Benefits.refreshForPlan(PlanCur.PlanNum,patPlanNum);
        benefitListOld = new List<Benefit>();
        for (int i = 0;i < benefitList.Count;i++)
        {
            benefitListOld.Add(benefitList[i].Copy());
        }
        textPlanNum.Visible = false;
        if (PrefC.getBool(PrefName.EasyHideCapitation))
        {
        }
         
        //groupCoPay.Visible=false;
        //comboCopay.Visible=false;
        if (PrefC.getBool(PrefName.EasyHideMedicaid))
        {
            checkAlternateCode.Visible = false;
        }
         
        Program ProgramCur = Programs.getCur(ProgramName.Trojan);
        if (ProgramCur != null && ProgramCur.Enabled)
        {
            textTrojanID.Text = PlanCur.TrojanID;
        }
        else
        {
            //labelTrojan.Visible=false;
            labelTrojanID.Visible = false;
            butImportTrojan.Visible = false;
            textTrojanID.Visible = false;
        } 
        ProgramCur = Programs.getCur(ProgramName.IAP);
        if (ProgramCur == null || !ProgramCur.Enabled)
        {
            //labelIAP.Visible=false;
            butIapFind.Visible = false;
        }
         
        if (!butIapFind.Visible && !butImportTrojan.Visible)
        {
            butBenefitNotes.Visible = false;
        }
         
        //FillPatData------------------------------
        if (PatPlanCur == null)
        {
            panelPat.Visible = false;
        }
        else
        {
            textOrdinal.Text = PatPlanCur.Ordinal.ToString();
            checkIsPending.Checked = PatPlanCur.IsPending;
            comboRelationship.Items.Clear();
            for (int i = 0;i < Enum.GetNames(Relat.class).Length;i++)
            {
                comboRelationship.Items.Add(Lan.g("enumRelat", Enum.GetNames(Relat.class)[i]));
                if (((Enum)PatPlanCur.Relationship).ordinal() == i)
                {
                    comboRelationship.SelectedIndex = i;
                }
                 
            }
            textPatID.Text = PatPlanCur.PatID;
            fillPatientAdjustments();
        } 
        if (SubCur != null)
        {
            textSubscriber.Text = Patients.getLim(SubCur.Subscriber).getNameLF();
            textSubscriberID.Text = SubCur.SubscriberID;
            if (SubCur.DateEffective.Year < 1880)
            {
                textDateEffect.Text = "";
            }
            else
            {
                textDateEffect.Text = SubCur.DateEffective.ToString("d");
            } 
            if (SubCur.DateTerm.Year < 1880)
            {
                textDateTerm.Text = "";
            }
            else
            {
                textDateTerm.Text = SubCur.DateTerm.ToString("d");
            } 
            checkRelease.Checked = SubCur.ReleaseInfo;
            checkAssign.Checked = SubCur.AssignBen;
            textSubscNote.Text = SubCur.SubscNote;
        }
         
        FeeSchedsStandard = FeeScheds.getListForType(FeeScheduleType.Normal,false);
        FeeSchedsCopay = FeeScheds.getListForType(FeeScheduleType.CoPay,false);
        FeeSchedsAllowed = FeeScheds.getListForType(FeeScheduleType.Allowed,false);
        //Clearinghouse clearhouse=Clearinghouses.GetDefault();
        //if(clearhouse==null || clearhouse.CommBridge!=EclaimsCommBridge.ClaimConnect) {
        //	butEligibility.Visible=false;
        //}
        fillFormWithPlanCur(false);
        fillBenefits();
        DateTime dateLast270 = Etranss.getLastDate270(PlanCur.PlanNum);
        if (dateLast270.Year < 1880)
        {
            textElectBenLastDate.Text = "";
        }
        else
        {
            textElectBenLastDate.Text = dateLast270.ToShortDateString();
        } 
        Cursor = Cursors.Default;
    }

    /**
    * Uses PlanCur to fill out the information on the form.  Called once on startup and also if user picks a plan from template list.  This does not fill from SubCur, unlike FillPlanCurFromForm().
    */
    private void fillFormWithPlanCur(boolean isPicked) throws Exception {
        Cursor = Cursors.WaitCursor;
        textEmployer.Text = Employers.getName(PlanCur.EmployerNum);
        textGroupName.Text = PlanCur.GroupName;
        textGroupNum.Text = PlanCur.GroupNum;
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            textBIN.Text = PlanCur.RxBIN;
        }
        else
        {
            labelBIN.Visible = false;
            textBIN.Visible = false;
        } 
        textDivisionNo.Text = PlanCur.DivisionNo;
        //only visible in Canada
        textTrojanID.Text = PlanCur.TrojanID;
        comboPlanType.Items.Clear();
        comboPlanType.Items.Add(Lan.g(this,"Category Percentage"));
        if (StringSupport.equals(PlanCur.PlanType, ""))
        {
            comboPlanType.SelectedIndex = 0;
        }
         
        comboPlanType.Items.Add(Lan.g(this,"PPO Percentage"));
        if (StringSupport.equals(PlanCur.PlanType, "p"))
        {
            comboPlanType.SelectedIndex = 1;
        }
         
        comboPlanType.Items.Add(Lan.g(this,"Medicaid or Flat Co-pay"));
        if (StringSupport.equals(PlanCur.PlanType, "f"))
        {
            comboPlanType.SelectedIndex = 2;
        }
         
        if (!PrefC.getBool(PrefName.EasyHideCapitation))
        {
            comboPlanType.Items.Add(Lan.g(this,"Capitation"));
            if (StringSupport.equals(PlanCur.PlanType, "c"))
                comboPlanType.SelectedIndex = 3;
             
        }
         
        checkAlternateCode.Checked = PlanCur.UseAltCode;
        checkCodeSubst.Checked = PlanCur.CodeSubstNone;
        checkIsMedical.Checked = PlanCur.IsMedical;
        if (!PrefC.getBool(PrefName.ShowFeatureMedicalInsurance))
        {
            checkIsMedical.Visible = false;
        }
         
        //This line prevents most users from modifying the Medical Insurance checkbox by accident, because most offices are dental only.
        checkClaimsUseUCR.Checked = PlanCur.ClaimsUseUCR;
        if (IsNewPlan && StringSupport.equals(PlanCur.PlanType, "") && PrefC.getBool(PrefName.InsDefaultShowUCRonClaims) && !isPicked)
        {
            checkClaimsUseUCR.Checked = true;
        }
         
        if (IsNewPlan && !PrefC.getBool(PrefName.InsDefaultAssignBen) && !isPicked)
        {
            checkAssign.Checked = false;
        }
         
        checkIsHidden.Checked = PlanCur.IsHidden;
        checkShowBaseUnits.Checked = PlanCur.ShowBaseUnits;
        comboFeeSched.Items.Clear();
        comboFeeSched.Items.Add(Lan.g(this,"none"));
        comboFeeSched.SelectedIndex = 0;
        for (int i = 0;i < FeeSchedsStandard.Count;i++)
        {
            comboFeeSched.Items.Add(FeeSchedsStandard[i].Description);
            if (FeeSchedsStandard[i].FeeSchedNum == PlanCur.FeeSched)
                comboFeeSched.SelectedIndex = i + 1;
             
        }
        comboClaimForm.Items.Clear();
        for (int i = 0;i < ClaimForms.getListShort().Length;i++)
        {
            comboClaimForm.Items.Add(ClaimForms.getListShort()[i].Description);
            if (ClaimForms.getListShort()[i].ClaimFormNum == PlanCur.ClaimFormNum)
            {
                comboClaimForm.SelectedIndex = i;
            }
             
        }
        if (comboClaimForm.Items.Count > 0 && comboClaimForm.SelectedIndex == -1)
        {
            for (int i = 0;i < ClaimForms.getListShort().Length;i++)
            {
                if (ClaimForms.getListShort()[i].ClaimFormNum == PrefC.getLong(PrefName.DefaultClaimForm))
                {
                    comboClaimForm.SelectedIndex = i;
                }
                 
            }
        }
         
        comboCopay.Items.Clear();
        comboCopay.Items.Add(Lan.g(this,"none"));
        comboCopay.SelectedIndex = 0;
        for (int i = 0;i < FeeSchedsCopay.Count;i++)
        {
            comboCopay.Items.Add(FeeSchedsCopay[i].Description);
            if (FeeSchedsCopay[i].FeeSchedNum == PlanCur.CopayFeeSched)
                comboCopay.SelectedIndex = i + 1;
             
        }
        comboAllowedFeeSched.Items.Clear();
        comboAllowedFeeSched.Items.Add(Lan.g(this,"none"));
        comboAllowedFeeSched.SelectedIndex = 0;
        for (int i = 0;i < FeeSchedsAllowed.Count;i++)
        {
            comboAllowedFeeSched.Items.Add(FeeSchedsAllowed[i].Description);
            if (FeeSchedsAllowed[i].FeeSchedNum == PlanCur.AllowedFeeSched)
            {
                comboAllowedFeeSched.SelectedIndex = i + 1;
            }
             
        }
        comboCobRule.Items.Clear();
        for (int i = 0;i < Enum.GetNames(EnumCobRule.class).Length;i++)
        {
            comboCobRule.Items.Add(Lan.g("enumEnumCobRule", Enum.GetNames(EnumCobRule.class)[i]));
        }
        comboCobRule.SelectedIndex = ((Enum)PlanCur.CobRule).ordinal();
        comboFilingCode.Items.Clear();
        comboFilingCodeSubtype.Items.Clear();
        for (int i = 0;i < InsFilingCodeC.getListt().Count;i++)
        {
            comboFilingCode.Items.Add(InsFilingCodeC.getListt()[i].Descript);
            if (PlanCur.FilingCode == InsFilingCodeC.getListt()[i].InsFilingCodeNum)
            {
                comboFilingCode.SelectedIndex = i;
                List<InsFilingCodeSubtype> subtypeList = InsFilingCodeSubtypes.getForInsFilingCode(PlanCur.FilingCode);
                for (int j = 0;j < subtypeList.Count;j++)
                {
                    comboFilingCodeSubtype.Items.Add(subtypeList[j].Descript);
                    if (PlanCur.FilingCodeSubtype == subtypeList[j].InsFilingCodeSubtypeNum)
                    {
                        comboFilingCodeSubtype.SelectedIndex = j;
                    }
                     
                }
            }
             
        }
        fillCarrier(PlanCur.CarrierNum);
        if (!Security.isAuthorized(Permissions.CarrierCreate,true))
        {
            textCarrier.Enabled = false;
            textPhone.Enabled = false;
            textAddress.Enabled = false;
            textAddress2.Enabled = false;
            textCity.Enabled = false;
            textState.Enabled = false;
            textZip.Enabled = false;
            textElectID.Enabled = false;
            butSearch.Enabled = false;
            checkNoSendElect.Enabled = false;
        }
         
        fillOtherSubscribers();
        textPlanNote.Text = PlanCur.PlanNote;
        if (PlanCur.DentaideCardSequence == 0)
        {
            textDentaide.Text = "";
        }
        else
        {
            textDentaide.Text = PlanCur.DentaideCardSequence.ToString();
        } 
        textPlanFlag.Text = PlanCur.CanadianPlanFlag;
        textCanadianDiagCode.Text = PlanCur.CanadianDiagnosticCode;
        textCanadianInstCode.Text = PlanCur.CanadianInstitutionCode;
        //if(PlanCur.BenefitNotes==""){
        //	butBenefitNotes.Enabled=false;
        //}
        Cursor = Cursors.Default;
    }

    private void fillOtherSubscribers() throws Exception {
        long excludeSub = -1;
        if (SubCur != null)
        {
            excludeSub = SubCur.InsSubNum;
        }
         
        //Even though this sub hasn't been updated to the database, this still works because SubCur.InsSubNum is valid and won't change.
        String[] arraySubs = InsSubs.getSubscribersForPlan(PlanCur.PlanNum,excludeSub);
        textLinkedNum.Text = arraySubs.Length.ToString();
        comboLinked.Items.Clear();
        for (int i = 0;i < arraySubs.Length;i++)
        {
            comboLinked.Items.Add(arraySubs[i]);
        }
        if (arraySubs.Length > 0)
        {
            comboLinked.SelectedIndex = 0;
        }
         
    }

    private void fillPatientAdjustments() throws Exception {
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(PatPlanCur.PatNum);
        AdjAL = new ArrayList();
        for (int i = 0;i < ClaimProcList.Count;i++)
        {
            //move selected claimprocs into ALAdj
            if (ClaimProcList[i].InsSubNum == SubCur.InsSubNum && ClaimProcList[i].Status == ClaimProcStatus.Adjustment)
            {
                AdjAL.Add(ClaimProcList[i]);
            }
             
        }
        listAdj.Items.Clear();
        String s = new String();
        for (int i = 0;i < AdjAL.Count;i++)
        {
            s = ((ClaimProc)AdjAL[i]).ProcDate.ToShortDateString() + "       Ins Used:  " + ((ClaimProc)AdjAL[i]).InsPayAmt.ToString("F") + "       Ded Used:  " + ((ClaimProc)AdjAL[i]).DedApplied.ToString("F");
            listAdj.Items.Add(s);
        }
    }

    /**
    * Fills the carrier fields on the form based on the specified carrierNum.
    */
    private void fillCarrier(long carrierNum) throws Exception {
        Carrier carrier = Carriers.getCarrier(carrierNum);
        textCarrier.Text = carrier.CarrierName;
        textPhone.Text = carrier.Phone;
        textAddress.Text = carrier.Address;
        textAddress2.Text = carrier.Address2;
        textCity.Text = carrier.City;
        textState.Text = carrier.State;
        textZip.Text = carrier.Zip;
        textElectID.Text = carrier.ElectID;
        fillPayor();
        checkNoSendElect.Checked = carrier.NoSendElect;
    }

    /**
    * Only called from FillCarrier and textElectID_Validating. Fills comboElectIDdescript as appropriate.
    */
    private void fillPayor() throws Exception {
        //textElectIDdescript.Text=ElectIDs.GetDescript(textElectID.Text);
        comboElectIDdescript.Items.Clear();
        String[] payorNames = ElectIDs.GetDescripts(textElectID.Text);
        if (payorNames.Length > 1)
        {
            comboElectIDdescript.Items.Add("multiple payors use this ID");
        }
         
        for (int i = 0;i < payorNames.Length;i++)
        {
            comboElectIDdescript.Items.Add(payorNames[i]);
        }
        if (payorNames.Length > 0)
        {
            comboElectIDdescript.SelectedIndex = 0;
        }
         
    }

    private void comboElectIDdescript_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        if (comboElectIDdescript.Items.Count > 0)
        {
            comboElectIDdescript.SelectedIndex = 0;
        }
         
    }

    //always show the first item in the list
    private void comboPlanType_SelectionChangeCommitted(Object sender, System.EventArgs e) throws Exception {
        //MessageBox.Show(InsPlans.Cur.PlanType+","+listPlanType.SelectedIndex.ToString());
        if ((StringSupport.equals(PlanCur.PlanType, "") || StringSupport.equals(PlanCur.PlanType, "p")) && (comboPlanType.SelectedIndex == 2 || comboPlanType.SelectedIndex == 3))
        {
            if (!MsgBox.show(this,true,"This will clear all percentages. Continue?"))
            {
                if (StringSupport.equals(PlanCur.PlanType, ""))
                {
                    comboPlanType.SelectedIndex = 0;
                }
                 
                if (StringSupport.equals(PlanCur.PlanType, "p"))
                {
                    comboPlanType.SelectedIndex = 1;
                }
                 
                return ;
            }
             
            for (int i = benefitList.Count - 1;i >= 0;i--)
            {
                //Loop through the list backwards so i will be valid.
                if (((Benefit)benefitList[i]).BenefitType == InsBenefitType.CoInsurance)
                {
                    benefitList.RemoveAt(i);
                }
                 
            }
            //benefitList=new ArrayList();
            fillBenefits();
        }
         
        SelectedIndex __dummyScrutVar0 = comboPlanType.SelectedIndex;
        if (__dummyScrutVar0.equals(0))
        {
            PlanCur.PlanType = "";
        }
        else if (__dummyScrutVar0.equals(1))
        {
            PlanCur.PlanType = "p";
        }
        else if (__dummyScrutVar0.equals(2))
        {
            PlanCur.PlanType = "f";
        }
        else if (__dummyScrutVar0.equals(3))
        {
            PlanCur.PlanType = "c";
        }
            
        if (PrefC.getBool(PrefName.InsDefaultShowUCRonClaims))
        {
            //otherwise, no automation on this field.
            if (StringSupport.equals(PlanCur.PlanType, ""))
            {
                checkClaimsUseUCR.Checked = true;
            }
            else
            {
                checkClaimsUseUCR.Checked = false;
            } 
        }
         
    }

    private void butAdjAdd_Click(Object sender, System.EventArgs e) throws Exception {
        ClaimProc ClaimProcCur = new ClaimProc();
        ClaimProcCur.PatNum = PatPlanCur.PatNum;
        ClaimProcCur.ProcDate = DateTime.Today;
        ClaimProcCur.Status = ClaimProcStatus.Adjustment;
        ClaimProcCur.PlanNum = PlanCur.PlanNum;
        ClaimProcCur.InsSubNum = SubCur.InsSubNum;
        FormInsAdj FormIA = new FormInsAdj(ClaimProcCur);
        FormIA.IsNew = true;
        FormIA.ShowDialog();
        fillPatientAdjustments();
    }

    private void listAdj_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        if (listAdj.SelectedIndex == -1)
        {
            return ;
        }
         
        FormInsAdj FormIA = new FormInsAdj((ClaimProc)AdjAL[listAdj.SelectedIndex]);
        FormIA.ShowDialog();
        fillPatientAdjustments();
    }

    /**
    * Button not visible if SubCur=null, editing from big list.
    */
    private void butPick_Click(Object sender, EventArgs e) throws Exception {
        FormInsPlans FormIP = new FormInsPlans();
        FormIP.empText = textEmployer.Text;
        FormIP.carrierText = textCarrier.Text;
        FormIP.IsSelectMode = true;
        FormIP.ShowDialog();
        if (FormIP.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        if (!IsNewPlan && !MsgBox.show(this,true,"Are you sure you want to use the selected plan?  You should NOT use this if the patient is changing insurance.  Use the Drop button instead."))
        {
            return ;
        }
         
        if (FormIP.SelectedPlan.PlanNum == 0)
        {
            //user clicked Blank
            PlanCur = new InsPlan();
            PlanCur.PlanNum = PlanNumOriginal;
        }
        else
        {
            //user selected an existing plan
            PlanCur = FormIP.SelectedPlan;
        } 
        fillFormWithPlanCur(true);
        //We need to pass patPlanNum in to RefreshForPlan to get patient level benefits:
        long patPlanNum = 0;
        if (PatPlanCur != null)
        {
            patPlanNum = PatPlanCur.PatPlanNum;
        }
         
        if (FormIP.SelectedPlan.PlanNum == 0)
        {
            //user clicked blank
            benefitList = new List<Benefit>();
        }
        else
        {
            //user selected an existing plan
            benefitList = Benefits.refreshForPlan(PlanCur.PlanNum,patPlanNum);
        } 
        fillBenefits();
        if (IsNewPlan || FormIP.SelectedPlan.PlanNum == 0)
        {
        }
        else
        {
            //New plan or user clicked blank.
            //Leave benefitListOld alone so that it will trigger deletion of the orphaned benefits later.
            //Replace benefitListOld so that we only cause changes to be save that are made after this point.
            benefitListOld = new List<Benefit>();
            for (int i = 0;i < benefitList.Count;i++)
            {
                benefitListOld.Add(benefitList[i].Copy());
            }
        } 
        //benefitListOld=new List<Benefit>(benefitList);//this was not the proper way to make a shallow copy.
        PlanCurOriginal = PlanCur.copy();
        fillOtherSubscribers();
    }

    //PlanNumOriginal is NOT reset here.
    //It's now similar to if we'd just opened a new form, except for SubCur still needs to be changed.
    private void textEmployer_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        //key up is used because that way it will trigger AFTER the textBox has been changed.
        if (e.KeyCode == Keys.Return)
        {
            listEmps.Visible = false;
            textGroupName.Focus();
            return ;
        }
         
        if (StringSupport.equals(textEmployer.Text, ""))
        {
            listEmps.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listEmps.Items.Count == 0)
            {
                return ;
            }
             
            if (listEmps.SelectedIndex == -1)
            {
                listEmps.SelectedIndex = 0;
                textEmployer.Text = listEmps.SelectedItem.ToString();
            }
            else if (listEmps.SelectedIndex == listEmps.Items.Count - 1)
            {
                listEmps.SelectedIndex = -1;
                textEmployer.Text = empOriginal;
            }
            else
            {
                listEmps.SelectedIndex++;
                textEmployer.Text = listEmps.SelectedItem.ToString();
            }  
            textEmployer.SelectionStart = textEmployer.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listEmps.Items.Count == 0)
            {
                return ;
            }
             
            if (listEmps.SelectedIndex == -1)
            {
                listEmps.SelectedIndex = listEmps.Items.Count - 1;
                textEmployer.Text = listEmps.SelectedItem.ToString();
            }
            else if (listEmps.SelectedIndex == 0)
            {
                listEmps.SelectedIndex = -1;
                textEmployer.Text = empOriginal;
            }
            else
            {
                listEmps.SelectedIndex--;
                textEmployer.Text = listEmps.SelectedItem.ToString();
            }  
            textEmployer.SelectionStart = textEmployer.Text.Length;
            return ;
        }
         
        if (textEmployer.Text.Length == 1)
        {
            textEmployer.Text = textEmployer.Text.ToUpper();
            textEmployer.SelectionStart = 1;
        }
         
        empOriginal = textEmployer.Text;
        //the original text is preserved when using up and down arrows
        listEmps.Items.Clear();
        List<Employer> similarEmps = Employers.GetSimilarNames(textEmployer.Text);
        for (int i = 0;i < similarEmps.Count;i++)
        {
            listEmps.Items.Add(similarEmps[i].EmpName);
        }
        int h = 13 * similarEmps.Count + 5;
        if (h > ClientSize.Height - listEmps.Top)
        {
            h = ClientSize.Height - listEmps.Top;
        }
         
        listEmps.Size = new Size(231, h);
        listEmps.Visible = true;
    }

    private void textEmployer_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListEmps)
        {
            return ;
        }
         
        listEmps.Visible = false;
    }

    private void listEmps_Click(Object sender, System.EventArgs e) throws Exception {
        textEmployer.Text = listEmps.SelectedItem.ToString();
        textEmployer.Focus();
        textEmployer.SelectionStart = textEmployer.Text.Length;
        listEmps.Visible = false;
    }

    private void listEmps_DoubleClick(Object sender, System.EventArgs e) throws Exception {
        //no longer used
        textEmployer.Text = listEmps.SelectedItem.ToString();
        textEmployer.Focus();
        textEmployer.SelectionStart = textEmployer.Text.Length;
        listEmps.Visible = false;
    }

    private void listEmps_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListEmps = true;
    }

    private void listEmps_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListEmps = false;
    }

    private void butPickCarrier_Click(Object sender, EventArgs e) throws Exception {
        FormCarriers formc = new FormCarriers();
        formc.IsSelectMode = true;
        formc.ShowDialog();
        if (formc.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillCarrier(formc.SelectedCarrier.CarrierNum);
    }

    private void textCarrier_KeyUp(Object sender, System.Windows.Forms.KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.Return)
        {
            if (listCars.SelectedIndex == -1)
            {
                textPhone.Focus();
            }
            else
            {
                FillCarrier(similarCars[listCars.SelectedIndex].CarrierNum);
                textCarrier.Focus();
                textCarrier.SelectionStart = textCarrier.Text.Length;
            } 
            listCars.Visible = false;
            return ;
        }
         
        if (StringSupport.equals(textCarrier.Text, ""))
        {
            listCars.Visible = false;
            return ;
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (listCars.Items.Count == 0)
            {
                return ;
            }
             
            if (listCars.SelectedIndex == -1)
            {
                listCars.SelectedIndex = 0;
                textCarrier.Text = similarCars[listCars.SelectedIndex].CarrierName;
            }
            else if (listCars.SelectedIndex == listCars.Items.Count - 1)
            {
                listCars.SelectedIndex = -1;
                textCarrier.Text = carOriginal;
            }
            else
            {
                listCars.SelectedIndex++;
                textCarrier.Text = similarCars[listCars.SelectedIndex].CarrierName;
            }  
            textCarrier.SelectionStart = textCarrier.Text.Length;
            return ;
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (listCars.Items.Count == 0)
            {
                return ;
            }
             
            if (listCars.SelectedIndex == -1)
            {
                listCars.SelectedIndex = listCars.Items.Count - 1;
                textCarrier.Text = similarCars[listCars.SelectedIndex].CarrierName;
            }
            else if (listCars.SelectedIndex == 0)
            {
                listCars.SelectedIndex = -1;
                textCarrier.Text = carOriginal;
            }
            else
            {
                listCars.SelectedIndex--;
                textCarrier.Text = similarCars[listCars.SelectedIndex].CarrierName;
            }  
            textCarrier.SelectionStart = textCarrier.Text.Length;
            return ;
        }
         
        if (textCarrier.Text.Length == 1)
        {
            textCarrier.Text = textCarrier.Text.ToUpper();
            textCarrier.SelectionStart = 1;
        }
         
        carOriginal = textCarrier.Text;
        //the original text is preserved when using up and down arrows
        listCars.Items.Clear();
        similarCars = Carriers.GetSimilarNames(textCarrier.Text);
        for (int i = 0;i < similarCars.Count;i++)
        {
            listCars.Items.Add(similarCars[i].CarrierName + ", " + similarCars[i].Phone + ", " + similarCars[i].Address + ", " + similarCars[i].Address2 + ", " + similarCars[i].City + ", " + similarCars[i].State + ", " + similarCars[i].Zip);
        }
        int h = 13 * similarCars.Count + 5;
        if (h > ClientSize.Height - listCars.Top)
        {
            h = ClientSize.Height - listCars.Top;
        }
         
        listCars.Size = new Size(listCars.Width, h);
        listCars.Visible = true;
    }

    private void textCarrier_Leave(Object sender, System.EventArgs e) throws Exception {
        if (mouseIsInListCars)
        {
            return ;
        }
         
        //or if user clicked on a different text box.
        if (listCars.SelectedIndex != -1)
        {
            FillCarrier(similarCars[listCars.SelectedIndex].CarrierNum);
        }
         
        listCars.Visible = false;
    }

    private void listCars_Click(Object sender, System.EventArgs e) throws Exception {
        FillCarrier(similarCars[listCars.SelectedIndex].CarrierNum);
        textCarrier.Focus();
        textCarrier.SelectionStart = textCarrier.Text.Length;
        listCars.Visible = false;
    }

    private void listCars_DoubleClick(Object sender, System.EventArgs e) throws Exception {
    }

    //no longer used
    private void listCars_MouseEnter(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListCars = true;
    }

    private void listCars_MouseLeave(Object sender, System.EventArgs e) throws Exception {
        mouseIsInListCars = false;
    }

    private void textPhone_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursor = textPhone.SelectionStart;
        int length = textPhone.Text.Length;
        textPhone.Text = TelephoneNumbers.AutoFormat(textPhone.Text);
        if (textPhone.Text.Length > length)
            cursor++;
         
        textPhone.SelectionStart = cursor;
    }

    private void textAddress_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textAddress.Text.Length == 1)
        {
            textAddress.Text = textAddress.Text.ToUpper();
            textAddress.SelectionStart = 1;
        }
         
    }

    private void textAddress2_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textAddress2.Text.Length == 1)
        {
            textAddress2.Text = textAddress2.Text.ToUpper();
            textAddress2.SelectionStart = 1;
        }
         
    }

    private void textCity_TextChanged(Object sender, System.EventArgs e) throws Exception {
        if (textCity.Text.Length == 1)
        {
            textCity.Text = textCity.Text.ToUpper();
            textCity.SelectionStart = 1;
        }
         
    }

    private void textState_TextChanged(Object sender, System.EventArgs e) throws Exception {
        //if USA or Canada, capitalize first 2 letters
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") || CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (textState.Text.Length == 1 || textState.Text.Length == 2)
            {
                textState.Text = textState.Text.ToUpper();
                textState.SelectionStart = 2;
            }
             
        }
        else
        {
            if (textState.Text.Length == 1)
            {
                textState.Text = textState.Text.ToUpper();
                textState.SelectionStart = 1;
            }
             
        } 
    }

    private void textElectID_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (StringSupport.equals(textElectID.Text, ""))
        {
            return ;
        }
         
        if (CultureInfo.CurrentCulture.Name.Length >= 4 && StringSupport.equals(CultureInfo.CurrentCulture.Name.Substring(3), "CA"))
        {
            //en-CA or fr-CA
            if (!Regex.IsMatch(textElectID.Text, "^[0-9]{6}$"))
            {
                if (!MsgBox.show(this,true,"Carrier ID should be six digits long.  Continue anyway?"))
                {
                    e.Cancel = true;
                    return ;
                }
                 
            }
             
        }
         
        //else{//anyplace including Canada
        String[] electIDs = ElectIDs.GetDescripts(textElectID.Text);
        if (electIDs.Length == 0)
        {
            //if none found in the predefined list
            if (!Carriers.ElectIdInUse(textElectID.Text))
            {
                if (!MsgBox.show(this,true,"Electronic ID not found. Continue anyway?"))
                {
                    e.Cancel = true;
                    return ;
                }
                 
            }
             
        }
         
        fillPayor();
    }

    //}
    private void butChange_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.InsPlanChangeSubsc))
        {
            return ;
        }
         
        try
        {
            InsSubs.validateNoKeys(SubCur.InsSubNum,false);
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Change subscriber?  This should not normally be needed."))
            {
                return ;
            }
             
        }
        catch (Exception ex)
        {
            if (PrefC.getBool(PrefName.SubscriberAllowChangeAlways))
            {
                DialogResult dres = MessageBox.Show(Lan.g(this,"Warning!  Do not change unless fixing database corruption.  ") + "\r\n" + ex.Message);
                if (dres != DialogResult.OK)
                {
                    return ;
                }
                 
            }
            else
            {
                MessageBox.Show(Lan.g(this,"Not allowed to change.") + "\r\n" + ex.Message);
                return ;
            } 
        }

        Family fam = Patients.getFamily(PatPlanCur.PatNum);
        FormFamilyMemberSelect FormF = new FormFamilyMemberSelect(fam);
        FormF.ShowDialog();
        if (FormF.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SubCur.Subscriber = FormF.SelectedPatNum;
        Patient subsc = Patients.getLim(FormF.SelectedPatNum);
        textSubscriber.Text = subsc.getNameLF();
        textSubscriberID.Text = subsc.SSN;
    }

    private void butSearch_Click(Object sender, System.EventArgs e) throws Exception {
        FormElectIDs FormE = new FormElectIDs();
        FormE.IsSelectMode = true;
        FormE.ShowDialog();
        if (FormE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        textElectID.Text = FormE.selectedID.PayorID;
        fillPayor();
    }

    //textElectIDdescript.Text=FormE.selectedID.CarrierName;
    private void comboFilingCode_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        comboFilingCodeSubtype.Items.Clear();
        List<InsFilingCodeSubtype> subtypeList = InsFilingCodeSubtypes.GetForInsFilingCode(InsFilingCodeC.getListt()[comboFilingCode.SelectedIndex].InsFilingCodeNum);
        for (int j = 0;j < subtypeList.Count;j++)
        {
            comboFilingCodeSubtype.Items.Add(subtypeList[j].Descript);
        }
    }

    private void butImportTrojan_Click(Object sender, System.EventArgs e) throws Exception {
        //If SubCur is null, this button is not visible to click.
        if (CovCats.getForEbenCat(EbenefitCategory.Diagnostic) == null || CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive) == null || CovCats.getForEbenCat(EbenefitCategory.Restorative) == null || CovCats.getForEbenCat(EbenefitCategory.Endodontics) == null || CovCats.getForEbenCat(EbenefitCategory.Periodontics) == null || CovCats.getForEbenCat(EbenefitCategory.Prosthodontics) == null || CovCats.getForEbenCat(EbenefitCategory.Crowns) == null || CovCats.getForEbenCat(EbenefitCategory.OralSurgery) == null || CovCats.getForEbenCat(EbenefitCategory.Orthodontics) == null)
        {
            MsgBox.show(this,"You must first set up your insurance categories with corresponding electronic benefit categories: Diagnostic,RoutinePreventive, Restorative, Endodontics, Periodontics, Crowns, OralSurgery, Orthodontics, and Prosthodontics");
            return ;
        }
         
        RegistryKey regKey = Registry.LocalMachine.OpenSubKey("Software\\TROJAN BENEFIT SERVICE");
        if (regKey == null)
        {
            //dmg Unix OS will exit here.
            MessageBox.Show("Trojan not installed properly.");
            return ;
        }
         
        //C:\ETW
        if (regKey.GetValue("INSTALLDIR") == null)
        {
            MessageBox.Show("Registry entry is missing and should be added manually.  LocalMachine\\Software\\TROJAN BENEFIT SERVICE. StringValue.  Name=\'INSTALLDIR\', value= path where the Trojan program is located.  Full path to directory, without trailing slash.");
            return ;
        }
         
        String file = CodeBase.ODFileUtils.CombinePaths(regKey.GetValue("INSTALLDIR").ToString(), "Planout.txt");
        if (!File.Exists(file))
        {
            MessageBox.Show(file + " not found.  You should export from Trojan first.");
            return ;
        }
         
        TrojanObject troj = Trojan.ProcessTextToObject(File.ReadAllText(file));
        textTrojanID.Text = troj.TROJANID;
        textEmployer.Text = troj.ENAME;
        textGroupName.Text = troj.PLANDESC;
        textPhone.Text = troj.ELIGPHONE;
        textGroupNum.Text = troj.POLICYNO;
        //checkNoSendElect.Checked=!troj.ECLAIMS;//Ignore this.  Even if Trojan says paper, most offices still send by clearinghouse.
        textElectID.Text = troj.PAYERID;
        textCarrier.Text = troj.MAILTO;
        textAddress.Text = troj.MAILTOST;
        textCity.Text = troj.MAILCITYONLY;
        textState.Text = troj.MAILSTATEONLY;
        textZip.Text = troj.MAILZIPONLY;
        if (!StringSupport.equals(SubCur.BenefitNotes, ""))
        {
            SubCur.BenefitNotes += "\r\n--------------------------------\r\n";
        }
         
        SubCur.BenefitNotes += troj.BenefitNotes;
        if (!StringSupport.equals(troj.PlanNote, ""))
        {
            if (StringSupport.equals(textPlanNote.Text, ""))
            {
                textPlanNote.Text = troj.PlanNote;
            }
            else
            {
                //must let user pick final note
                String[] noteArray = new String[2];
                noteArray[0] = textPlanNote.Text;
                noteArray[1] = troj.PlanNote;
                FormNotePick FormN = new FormNotePick(noteArray);
                FormN.UseTrojanImportDescription = true;
                FormN.ShowDialog();
                if (FormN.DialogResult == DialogResult.OK)
                {
                    textPlanNote.Text = FormN.SelectedNote;
                }
                 
            } 
        }
         
        //clear exising benefits from screen, not db:
        benefitList = new List<Benefit>();
        for (int i = 0;i < troj.BenefitList.Count;i++)
        {
            //if(fields[2]=="Anniversary year") {
            //	usesAnnivers=true;
            //	MessageBox.Show("Warning.  Plan uses Anniversary year rather than Calendar year.  Please verify the Plan Start Date.");
            //}
            troj.BenefitList[i].PlanNum = PlanCur.PlanNum;
            benefitList.Add(troj.BenefitList[i].Copy());
        }
        File.Delete(file);
        butBenefitNotes.Enabled = true;
        fillBenefits();
    }

    /*if(resetFeeSched){
    				FeeSchedsStandard=FeeScheds.GetListForType(FeeScheduleType.Normal,false);
    				FeeSchedsCopay=FeeScheds.GetListForType(FeeScheduleType.CoPay,false);
    				FeeSchedsAllowed=FeeScheds.GetListForType(FeeScheduleType.Allowed,false);
    				//if managed care, then do it a bit differently
    				comboFeeSched.Items.Clear();
    				comboFeeSched.Items.Add(Lan.g(this,"none"));
    				comboFeeSched.SelectedIndex=0;
    				for(int i=0;i<FeeSchedsStandard.Count;i++) {
    					comboFeeSched.Items.Add(FeeSchedsStandard[i].Description);
    					if(FeeSchedsStandard[i].FeeSchedNum==feeSchedNum)
    						comboFeeSched.SelectedIndex=i+1;
    				}
    				comboCopay.Items.Clear();
    				comboCopay.Items.Add(Lan.g(this,"none"));
    				comboCopay.SelectedIndex=0;
    				for(int i=0;i<FeeSchedsCopay.Count;i++) {
    					comboCopay.Items.Add(FeeSchedsCopay[i].Description);
    					//This will get set for managed care
    					//if(FeeSchedsCopay[i].DefNum==PlanCur.CopayFeeSched)
    					//	comboCopay.SelectedIndex=i+1;
    				}
    				comboAllowedFeeSched.Items.Clear();
    				comboAllowedFeeSched.Items.Add(Lan.g(this,"none"));
    				comboAllowedFeeSched.SelectedIndex=0;
    				for(int i=0;i<FeeSchedsAllowed.Count;i++) {
    					comboAllowedFeeSched.Items.Add(FeeSchedsAllowed[i].Description);
    					//I would have set allowed for PPO, but we are probably going to deprecate this when we do coverage tables.
    					//if(FeeSchedsAllowed[i].DefNum==PlanCur.AllowedFeeSched)
    					//	comboAllowedFeeSched.SelectedIndex=i+1;
    				}
    			}*/
    private void butIapFind_Click(Object sender, System.EventArgs e) throws Exception {
        //If SubCur is null, this button is not visible to click.
        FormIap FormI = new FormIap();
        FormI.ShowDialog();
        if (FormI.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        Benefit ben;
        //clear exising benefits from screen, not db:
        benefitList = new List<Benefit>();
        String plan = FormI.selectedPlan;
        String field = null;
        String[] splitField = new String[]();
        //if a field is a sentence with more than one word, we can split it for analysis
        int percent = new int();
        try
        {
            Iap.readRecord(plan);
            for (int i = 1;i < 122;i++)
            {
                field = Iap.readField(i);
                if (field == null)
                {
                    field = "";
                }
                 
                switch(i)
                {
                    default: 
                        break;
                    case Iap.Employer: 
                        //do nothing
                        if (!StringSupport.equals(SubCur.BenefitNotes, ""))
                        {
                            SubCur.BenefitNotes += "\r\n";
                        }
                         
                        SubCur.BenefitNotes += "Employer: " + field;
                        textEmployer.Text = field;
                        break;
                    case Iap.Phone: 
                        SubCur.BenefitNotes += "\r\n" + "Phone: " + field;
                        break;
                    case Iap.InsUnder: 
                        SubCur.BenefitNotes += "\r\n" + "InsUnder: " + field;
                        break;
                    case Iap.Carrier: 
                        SubCur.BenefitNotes += "\r\n" + "Carrier: " + field;
                        textCarrier.Text = field;
                        break;
                    case Iap.CarrierPh: 
                        SubCur.BenefitNotes += "\r\n" + "CarrierPh: " + field;
                        textPhone.Text = field;
                        break;
                    case Iap.Group: 
                        //seems to be used as groupnum
                        SubCur.BenefitNotes += "\r\n" + "Group: " + field;
                        textGroupNum.Text = field;
                        break;
                    case Iap.MailTo: 
                        //the carrier name again
                        SubCur.BenefitNotes += "\r\n" + "MailTo: " + field;
                        break;
                    case Iap.MailTo2: 
                        //address
                        SubCur.BenefitNotes += "\r\n" + "MailTo2: " + field;
                        textAddress.Text = field;
                        break;
                    case Iap.MailTo3: 
                        //address2
                        SubCur.BenefitNotes += "\r\n" + "MailTo3: " + field;
                        textAddress2.Text = field;
                        break;
                    case Iap.EClaims: 
                        SubCur.BenefitNotes += "\r\n" + "EClaims: " + field;
                        //this contains the PayorID at the end, but also a bunch of other drivel.
                        int payorIDloc = field.LastIndexOf("Payor ID#:");
                        if (payorIDloc != -1 && field.Length > payorIDloc + 10)
                        {
                            textElectID.Text = field.Substring(payorIDloc + 10);
                        }
                         
                        break;
                    case Iap.FAXClaims: 
                        SubCur.BenefitNotes += "\r\n" + "FAXClaims: " + field;
                        break;
                    case Iap.DMOOption: 
                        SubCur.BenefitNotes += "\r\n" + "DMOOption: " + field;
                        break;
                    case Iap.Medical: 
                        SubCur.BenefitNotes += "\r\n" + "Medical: " + field;
                        break;
                    case Iap.GroupNum: 
                        //not used.  They seem to use the group field instead
                        SubCur.BenefitNotes += "\r\n" + "GroupNum: " + field;
                        break;
                    case Iap.Phone2: 
                        //?
                        SubCur.BenefitNotes += "\r\n" + "Phone2: " + field;
                        break;
                    case Iap.Deductible: 
                        SubCur.BenefitNotes += "\r\n" + "Deductible: " + field;
                        if (field.StartsWith("$"))
                        {
                            splitField = field.Split(new char[]{ ' ' });
                            ben = new Benefit();
                            ben.BenefitType = InsBenefitType.Deductible;
                            ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.General).CovCatNum;
                            ben.PlanNum = PlanCur.PlanNum;
                            ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                            ben.MonetaryAmt = PIn.Double(splitField[0].Remove(0, 1));
                            //removes the $
                            benefitList.Add(ben.copy());
                        }
                         
                        break;
                    case Iap.FamilyDed: 
                        SubCur.BenefitNotes += "\r\n" + "FamilyDed: " + field;
                        break;
                    case Iap.Maximum: 
                        SubCur.BenefitNotes += "\r\n" + "Maximum: " + field;
                        if (field.StartsWith("$"))
                        {
                            splitField = field.Split(new char[]{ ' ' });
                            ben = new Benefit();
                            ben.BenefitType = InsBenefitType.Limitations;
                            ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.General).CovCatNum;
                            ben.PlanNum = PlanCur.PlanNum;
                            ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                            ben.MonetaryAmt = PIn.Double(splitField[0].Remove(0, 1));
                            //removes the $
                            benefitList.Add(ben.copy());
                        }
                         
                        break;
                    case Iap.BenefitYear: 
                        //text is too complex to parse
                        SubCur.BenefitNotes += "\r\n" + "BenefitYear: " + field;
                        break;
                    case Iap.DependentAge: 
                        //too complex to parse
                        SubCur.BenefitNotes += "\r\n" + "DependentAge: " + field;
                        break;
                    case Iap.Preventive: 
                        SubCur.BenefitNotes += "\r\n" + "Preventive: " + field;
                        splitField = field.Split(new char[]{ ' ' });
                        if (splitField.Length == 0 || !splitField[0].EndsWith("%"))
                        {
                            break;
                        }
                         
                        splitField[0] = splitField[0].Remove(splitField[0].Length - 1, 1);
                        //remove %
                        percent = PIn.Int(splitField[0]);
                        if (percent < 0 || percent > 100)
                        {
                            break;
                        }
                         
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum;
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        break;
                    case Iap.Basic: 
                        SubCur.BenefitNotes += "\r\n" + "Basic: " + field;
                        splitField = field.Split(new char[]{ ' ' });
                        if (splitField.Length == 0 || !splitField[0].EndsWith("%"))
                        {
                            break;
                        }
                         
                        splitField[0] = splitField[0].Remove(splitField[0].Length - 1, 1);
                        //remove %
                        percent = PIn.Int(splitField[0]);
                        if (percent < 0 || percent > 100)
                        {
                            break;
                        }
                         
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Restorative).CovCatNum;
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Endodontics).CovCatNum;
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Periodontics).CovCatNum;
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.OralSurgery).CovCatNum;
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        break;
                    case Iap.Major: 
                        SubCur.BenefitNotes += "\r\n" + "Major: " + field;
                        splitField = field.Split(new char[]{ ' ' });
                        if (splitField.Length == 0 || !splitField[0].EndsWith("%"))
                        {
                            break;
                        }
                         
                        splitField[0] = splitField[0].Remove(splitField[0].Length - 1, 1);
                        //remove %
                        percent = PIn.Int(splitField[0]);
                        if (percent < 0 || percent > 100)
                        {
                            break;
                        }
                         
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Prosthodontics).CovCatNum;
                        //includes crowns?
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        break;
                    case Iap.InitialPlacement: 
                        SubCur.BenefitNotes += "\r\n" + "InitialPlacement: " + field;
                        break;
                    case Iap.ExtractionClause: 
                        SubCur.BenefitNotes += "\r\n" + "ExtractionClause: " + field;
                        break;
                    case Iap.Replacement: 
                        SubCur.BenefitNotes += "\r\n" + "Replacement: " + field;
                        break;
                    case Iap.Other: 
                        SubCur.BenefitNotes += "\r\n" + "Other: " + field;
                        break;
                    case Iap.Orthodontics: 
                        SubCur.BenefitNotes += "\r\n" + "Orthodontics: " + field;
                        splitField = field.Split(new char[]{ ' ' });
                        if (splitField.Length == 0 || !splitField[0].EndsWith("%"))
                        {
                            break;
                        }
                         
                        splitField[0] = splitField[0].Remove(splitField[0].Length - 1, 1);
                        //remove %
                        percent = PIn.Int(splitField[0]);
                        if (percent < 0 || percent > 100)
                        {
                            break;
                        }
                         
                        ben = new Benefit();
                        ben.BenefitType = InsBenefitType.CoInsurance;
                        ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum;
                        ben.PlanNum = PlanCur.PlanNum;
                        ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                        ben.Percent = percent;
                        benefitList.Add(ben.copy());
                        break;
                    case Iap.Deductible2: 
                        SubCur.BenefitNotes += "\r\n" + "Deductible2: " + field;
                        break;
                    case Iap.Maximum2: 
                        //ortho Max
                        SubCur.BenefitNotes += "\r\n" + "Maximum2: " + field;
                        if (field.StartsWith("$"))
                        {
                            splitField = field.Split(new char[]{ ' ' });
                            ben = new Benefit();
                            ben.BenefitType = InsBenefitType.Limitations;
                            ben.CovCatNum = CovCats.getForEbenCat(EbenefitCategory.Orthodontics).CovCatNum;
                            ben.PlanNum = PlanCur.PlanNum;
                            ben.TimePeriod = BenefitTimePeriod.CalendarYear;
                            ben.MonetaryAmt = PIn.Double(splitField[0].Remove(0, 1));
                            //removes the $
                            benefitList.Add(ben.copy());
                        }
                         
                        break;
                    case Iap.PymtSchedule: 
                        SubCur.BenefitNotes += "\r\n" + "PymtSchedule: " + field;
                        break;
                    case Iap.AgeLimit: 
                        SubCur.BenefitNotes += "\r\n" + "AgeLimit: " + field;
                        break;
                    case Iap.SignatureonFile: 
                        SubCur.BenefitNotes += "\r\n" + "SignatureonFile: " + field;
                        break;
                    case Iap.StandardADAForm: 
                        SubCur.BenefitNotes += "\r\n" + "StandardADAForm: " + field;
                        break;
                    case Iap.CoordinationRule: 
                        SubCur.BenefitNotes += "\r\n" + "CoordinationRule: " + field;
                        break;
                    case Iap.CoordinationCOB: 
                        SubCur.BenefitNotes += "\r\n" + "CoordinationCOB: " + field;
                        break;
                    case Iap.NightguardsforBruxism: 
                        SubCur.BenefitNotes += "\r\n" + "NightguardsforBruxism: " + field;
                        break;
                    case Iap.OcclusalAdjustments: 
                        SubCur.BenefitNotes += "\r\n" + "OcclusalAdjustments: " + field;
                        break;
                    case Iap.XXXXXX: 
                        SubCur.BenefitNotes += "\r\n" + "XXXXXX: " + field;
                        break;
                    case Iap.TMJNonSurgical: 
                        SubCur.BenefitNotes += "\r\n" + "TMJNonSurgical: " + field;
                        break;
                    case Iap.Implants: 
                        SubCur.BenefitNotes += "\r\n" + "Implants: " + field;
                        break;
                    case Iap.InfectionControl: 
                        SubCur.BenefitNotes += "\r\n" + "InfectionControl: " + field;
                        break;
                    case Iap.Cleanings: 
                        SubCur.BenefitNotes += "\r\n" + "Cleanings: " + field;
                        break;
                    case Iap.OralEvaluation: 
                        SubCur.BenefitNotes += "\r\n" + "OralEvaluation: " + field;
                        break;
                    case Iap.Fluoride1200s: 
                        SubCur.BenefitNotes += "\r\n" + "Fluoride1200s: " + field;
                        break;
                    case Iap.Code0220: 
                        SubCur.BenefitNotes += "\r\n" + "Code0220: " + field;
                        break;
                    case Iap.Code0272_0274: 
                        SubCur.BenefitNotes += "\r\n" + "Code0272_0274: " + field;
                        break;
                    case Iap.Code0210: 
                        SubCur.BenefitNotes += "\r\n" + "Code0210: " + field;
                        break;
                    case Iap.Code0330: 
                        SubCur.BenefitNotes += "\r\n" + "Code0330: " + field;
                        break;
                    case Iap.SpaceMaintainers: 
                        SubCur.BenefitNotes += "\r\n" + "SpaceMaintainers: " + field;
                        break;
                    case Iap.EmergencyExams: 
                        SubCur.BenefitNotes += "\r\n" + "EmergencyExams: " + field;
                        break;
                    case Iap.EmergencyTreatment: 
                        SubCur.BenefitNotes += "\r\n" + "EmergencyTreatment: " + field;
                        break;
                    case Iap.Sealants1351: 
                        SubCur.BenefitNotes += "\r\n" + "Sealants1351: " + field;
                        break;
                    case Iap.Fillings2100: 
                        SubCur.BenefitNotes += "\r\n" + "Fillings2100: " + field;
                        break;
                    case Iap.Extractions: 
                        SubCur.BenefitNotes += "\r\n" + "Extractions: " + field;
                        break;
                    case Iap.RootCanals: 
                        SubCur.BenefitNotes += "\r\n" + "RootCanals: " + field;
                        break;
                    case Iap.MolarRootCanal: 
                        SubCur.BenefitNotes += "\r\n" + "MolarRootCanal: " + field;
                        break;
                    case Iap.OralSurgery: 
                        SubCur.BenefitNotes += "\r\n" + "OralSurgery: " + field;
                        break;
                    case Iap.ImpactionSoftTissue: 
                        SubCur.BenefitNotes += "\r\n" + "ImpactionSoftTissue: " + field;
                        break;
                    case Iap.ImpactionPartialBony: 
                        SubCur.BenefitNotes += "\r\n" + "ImpactionPartialBony: " + field;
                        break;
                    case Iap.ImpactionCompleteBony: 
                        SubCur.BenefitNotes += "\r\n" + "ImpactionCompleteBony: " + field;
                        break;
                    case Iap.SurgicalProceduresGeneral: 
                        SubCur.BenefitNotes += "\r\n" + "SurgicalProceduresGeneral: " + field;
                        break;
                    case Iap.PerioSurgicalPerioOsseous: 
                        SubCur.BenefitNotes += "\r\n" + "PerioSurgicalPerioOsseous: " + field;
                        break;
                    case Iap.SurgicalPerioOther: 
                        SubCur.BenefitNotes += "\r\n" + "SurgicalPerioOther: " + field;
                        break;
                    case Iap.RootPlaning: 
                        SubCur.BenefitNotes += "\r\n" + "RootPlaning: " + field;
                        break;
                    case Iap.Scaling4345: 
                        SubCur.BenefitNotes += "\r\n" + "Scaling4345: " + field;
                        break;
                    case Iap.PerioPx: 
                        SubCur.BenefitNotes += "\r\n" + "PerioPx: " + field;
                        break;
                    case Iap.PerioComment: 
                        SubCur.BenefitNotes += "\r\n" + "PerioComment: " + field;
                        break;
                    case Iap.IVSedation: 
                        SubCur.BenefitNotes += "\r\n" + "IVSedation: " + field;
                        break;
                    case Iap.General9220: 
                        SubCur.BenefitNotes += "\r\n" + "General9220: " + field;
                        break;
                    case Iap.Relines5700s: 
                        SubCur.BenefitNotes += "\r\n" + "Relines5700s: " + field;
                        break;
                    case Iap.StainlessSteelCrowns: 
                        SubCur.BenefitNotes += "\r\n" + "StainlessSteelCrowns: " + field;
                        break;
                    case Iap.Crowns2700s: 
                        SubCur.BenefitNotes += "\r\n" + "Crowns2700s: " + field;
                        break;
                    case Iap.Bridges6200: 
                        SubCur.BenefitNotes += "\r\n" + "Bridges6200: " + field;
                        break;
                    case Iap.Partials5200s: 
                        SubCur.BenefitNotes += "\r\n" + "Partials5200s: " + field;
                        break;
                    case Iap.Dentures5100s: 
                        SubCur.BenefitNotes += "\r\n" + "Dentures5100s: " + field;
                        break;
                    case Iap.EmpNumberXXX: 
                        SubCur.BenefitNotes += "\r\n" + "EmpNumberXXX: " + field;
                        break;
                    case Iap.DateXXX: 
                        SubCur.BenefitNotes += "\r\n" + "DateXXX: " + field;
                        break;
                    case Iap.Line4: 
                        //city state
                        SubCur.BenefitNotes += "\r\n" + "Line4: " + field;
                        field = field.Replace("  ", " ");
                        //get rid of double space before zip
                        splitField = field.Split(new char[]{ ' ' });
                        if (splitField.Length < 3)
                        {
                            break;
                        }
                         
                        textCity.Text = splitField[0].Replace(",", "");
                        //gets rid of the comma on the end of city
                        textState.Text = splitField[1];
                        textZip.Text = splitField[2];
                        break;
                    case Iap.Note: 
                        SubCur.BenefitNotes += "\r\n" + "Note: " + field;
                        break;
                    case Iap.Plan: 
                        //?
                        SubCur.BenefitNotes += "\r\n" + "Plan: " + field;
                        break;
                    case Iap.BuildUps: 
                        SubCur.BenefitNotes += "\r\n" + "BuildUps: " + field;
                        break;
                    case Iap.PosteriorComposites: 
                        SubCur.BenefitNotes += "\r\n" + "PosteriorComposites: " + field;
                        break;
                
                }
            }
            Iap.closeDatabase();
            butBenefitNotes.Enabled = true;
        }
        catch (ApplicationException ex)
        {
            Iap.closeDatabase();
            MessageBox.Show(ex.Message);
        }
        catch (Exception ex)
        {
            Iap.closeDatabase();
            MessageBox.Show("Error: " + ex.Message);
        }

        fillBenefits();
    }

    private void eligibilityCheckCanada() throws Exception {
        if (!fillPlanCurFromForm())
        {
            return ;
        }
         
        Carrier carrier = Carriers.getCarrier(PlanCur.CarrierNum);
        if (!carrier.IsCDA)
        {
            MsgBox.show(this,"Eligibility only supported for CDAnet carriers.");
            return ;
        }
         
        if ((carrier.CanadianSupportedTypes & CanSupTransTypes.EligibilityTransaction_08) != CanSupTransTypes.EligibilityTransaction_08)
        {
            MsgBox.show(this,"Eligibility not supported by this carrier.");
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        //string result="";
        DateTime date = DateTime.Today;
        Relat relat = (Relat)comboRelationship.SelectedIndex;
        String patID = textPatID.Text;
        try
        {
            CanadianOutput.sendElegibility(PatPlanCur.PatNum,PlanCur,date,relat,patID,true,SubCur);
        }
        catch (ApplicationException ex)
        {
            //   textElectID.Text,PatPlanCur.PatNum,textGroupNum.Text,textDivisionNo.Text,
            //textSubscriberID.Text,textPatID.Text,(Relat)comboRelationship.SelectedIndex,PlanCur.Subscriber,textDentaide.Text);
            //printout will happen in the line above.
            Cursor = Cursors.Default;
            MessageBox.Show(ex.Message);
            return ;
        }

        //PlanCur.BenefitNotes+=result;
        //butBenefitNotes.Enabled=true;
        Cursor = Cursors.Default;
        DateTime dateLast270 = Etranss.getLastDate270(PlanCur.PlanNum);
        if (dateLast270.Year < 1880)
        {
            textElectBenLastDate.Text = "";
        }
        else
        {
            textElectBenLastDate.Text = dateLast270.ToShortDateString();
        } 
    }

    /**
    * This button is only visible if Trojan or IAP is enabled.  Always active.  Button not visible if SubCur==null.
    */
    private void butBenefitNotes_Click(Object sender, System.EventArgs e) throws Exception {
        String otherBenNote = "";
        if (StringSupport.equals(SubCur.BenefitNotes, ""))
        {
            //try to find some other similar notes. Never includes the current subscriber.
            //List<long> samePlans=InsPlans.GetPlanNumsOfSamePlans(textEmployer.Text,textGroupName.Text,textGroupNum.Text,
            //	textDivisionNo.Text,textCarrier.Text,checkIsMedical.Checked,PlanCur.PlanNum,false);
            otherBenNote = InsSubs.getBenefitNotes(PlanCur.PlanNum,SubCur.InsSubNum);
            if (StringSupport.equals(otherBenNote, ""))
            {
                MsgBox.show(this,"No benefit note found.  Benefit notes are created when importing Trojan or IAP benefit information and are frequently read-only.  Store your own notes in the subscriber note instead.");
                return ;
            }
             
            MsgBox.show(this,"This plan does not have a benefit note, but a note was found for another subsriber of this plan.  You will be able to view this note, but not change it.");
        }
         
        FormInsBenefitNotes FormI = new FormInsBenefitNotes();
        if (!StringSupport.equals(SubCur.BenefitNotes, ""))
        {
            FormI.BenefitNotes = SubCur.BenefitNotes;
        }
        else
        {
            FormI.BenefitNotes = otherBenNote;
        } 
        FormI.ShowDialog();
        if (FormI.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        if (!StringSupport.equals(SubCur.BenefitNotes, ""))
        {
            SubCur.BenefitNotes = FormI.BenefitNotes;
        }
         
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        //this is a dual purpose button.  It sometimes deletes subscribers (inssubs), and sometimes the plan itself.
        if (IsNewPlan)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //original plan will get deleted in closing event.
        //1. Delete Subscriber---------------------------------------------------------------------------------------------------
        //Can only do this if there are other subscribers present.  If this is the last subscriber, then it attempts to delete the plan itself, down below.
        if (comboLinked.Items.Count > 0)
        {
            //Other subscribers are present.
            if (SubCur == null)
            {
                //viewing from big list
                MsgBox.show(this,"Subscribers must be removed individually before deleting plan.");
                return ;
            }
            else
            {
                //by dropping, then using this same delete button.
                //Came into here through a patient.
                if (PatPlanCur != null)
                {
                    if (!MsgBox.show(this,true,"All patients attached to this subscription will be dropped and the subscription for this plan will be deleted. Continue?"))
                    {
                        return ;
                    }
                     
                }
                 
                try
                {
                    //drop the plan
                    //detach subscriber.
                    InsSubs.delete(SubCur.InsSubNum);
                }
                catch (ApplicationException ex)
                {
                    //Checks dependencies first;  If none, deletes the inssub, claimprocs, patplans, and recomputes all estimates.
                    MessageBox.Show(ex.Message);
                    return ;
                }

                DialogResult = DialogResult.OK;
                return ;
            } 
        }
         
        //or
        //2. Delete the plan itself-------------------------------------------------------------------------------------------------
        //This is the only subscriber, so delete inssub and insplan
        //Or this is the big list and there are no subscribers, so just delete the insplan.
        if (!MsgBox.show(this,true,"Delete Plan?"))
        {
            return ;
        }
         
        try
        {
            InsPlans.delete(PlanCur);
        }
        catch (ApplicationException ex)
        {
            //Checks dependencies first;  If none, deletes insplan, inssub, benefits, claimprocs, patplans, and recomputes all estimates.
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butDrop_Click(Object sender, System.EventArgs e) throws Exception {
        //should we save the plan info first?  Probably not.
        //--
        //If they have a claim for this ins with today's date, don't let them drop.
        //We already have code in place to delete claimprocs when we drop ins, but the claimprocs attached to claims are protected.
        //The claim clearly needs to be deleted if they are dropping.  We need the user to delete the claim before they drop the plan.
        //We also have code in place to add new claimprocs when they add the correct insurance.
        List<Claim> claimList = Claims.refresh(PatPlanCur.PatNum);
        for (int j = 0;j < claimList.Count;j++)
        {
            if (claimList[j].PlanNum != PlanCur.PlanNum)
            {
                continue;
            }
             
            //different insplan
            if (claimList[j].DateService != DateTime.Today)
            {
                continue;
            }
             
            //not today
            //Patient currently has a claim for the insplan they are trying to drop
            MsgBox.show(this,"Please delete all of today's claims for this patient before dropping this plan.");
            return ;
        }
        PatPlans.delete(PatPlanCur.PatPlanNum);
        //Estimates recomputed within Delete()
        //PlanCur.ComputeEstimatesForCur();
        DialogResult = DialogResult.OK;
    }

    private void butLabel_Click(Object sender, System.EventArgs e) throws Exception {
        //LabelSingle label=new LabelSingle();
        PrintDocument pd = new PrintDocument();
        //only used to pass printerName
        if (!PrinterL.SetPrinter(pd, PrintSituation.LabelSingle, PatPlanCur.PatNum, textCarrier.Text + " insurance plan label printed"))
        {
            return ;
        }
         
        Carrier carrier = new Carrier();
        carrier.CarrierName = textCarrier.Text;
        carrier.Phone = textPhone.Text;
        carrier.Address = textAddress.Text;
        carrier.Address2 = textAddress2.Text;
        carrier.City = textCity.Text;
        carrier.State = textState.Text;
        carrier.Zip = textZip.Text;
        carrier.ElectID = textElectID.Text;
        carrier.NoSendElect = checkNoSendElect.Checked;
        try
        {
            carrier = Carriers.getIndentical(carrier);
        }
        catch (ApplicationException ex)
        {
            //the catch is just to display a message to the user.  It doesn't affect the success of the function.
            MessageBox.Show(ex.Message);
        }

        LabelSingle.printCarrier(carrier.CarrierNum);
    }

    //,pd.PrinterSettings.PrinterName);
    /**
    * This only fills the grid on the screen.  It does not get any data from the database.
    */
    private void fillBenefits() throws Exception {
        benefitList.Sort();
        gridBenefits.beginUpdate();
        gridBenefits.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Pat",28);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("Level",60);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("Type",70);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("Category",70);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("%",30);
        //,HorizontalAlignment.Right);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("Amt",40);
        //,HorizontalAlignment.Right);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("Time Period",80);
        gridBenefits.getColumns().add(col);
        col = new ODGridColumn("Quantity",115);
        gridBenefits.getColumns().add(col);
        gridBenefits.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < benefitList.Count;i++)
        {
            //bool allCalendarYear=true;
            //bool allServiceYear=true;
            /*if(((Benefit)benefitList[i]).TimePeriod==BenefitTimePeriod.CalendarYear){
            					allServiceYear=false;
            				}
            				if(((Benefit)benefitList[i]).TimePeriod==BenefitTimePeriod.ServiceYear) {
            					allCalendarYear=false;
            				}*/
            row = new OpenDental.UI.ODGridRow();
            if (benefitList[i].PatPlanNum == 0)
            {
                //attached to plan
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            if (benefitList[i].CoverageLevel == BenefitCoverageLevel.None)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(Lan.g("enumBenefitCoverageLevel", benefitList[i].CoverageLevel.ToString()));
            } 
            if (benefitList[i].BenefitType == InsBenefitType.CoInsurance && benefitList[i].Percent != -1)
            {
                row.getCells().add("%");
            }
            else
            {
                //else if(((Benefit)benefitList[i]).BenefitType==InsBenefitType.Limitations
                //	&& (((Benefit)benefitList[i]).TimePeriod==BenefitTimePeriod.ServiceYear
                //	|| ((Benefit)benefitList[i]).TimePeriod==BenefitTimePeriod.CalendarYear)
                //	&& ((Benefit)benefitList[i]).QuantityQualifier==BenefitQuantity.None) {//annual max
                //	row.Cells.Add(Lan.g(this,"Annual Max"));
                //}
                row.getCells().Add(Lan.g("enumInsBenefitType", benefitList[i].BenefitType.ToString()));
            } 
            if (((Benefit)benefitList[i]).CodeNum == 0)
            {
                row.getCells().Add(CovCats.GetDesc(benefitList[i].CovCatNum));
            }
            else
            {
                ProcedureCode proccode = ProcedureCodes.GetProcCode(benefitList[i].CodeNum);
                row.getCells().add(proccode.ProcCode + "-" + proccode.AbbrDesc);
            } 
            if (benefitList[i].Percent == -1)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(benefitList[i].Percent.ToString());
            } 
            if (benefitList[i].MonetaryAmt == -1)
            {
                //if(((Benefit)benefitList[i]).BenefitType==InsBenefitType.Deductible) {
                //	row.Cells.Add(((Benefit)benefitList[i]).MonetaryAmt.ToString("n0"));
                //}
                //else {
                row.getCells().add("");
            }
            else
            {
                //}
                row.getCells().Add(benefitList[i].MonetaryAmt.ToString("n0"));
            } 
            if (benefitList[i].TimePeriod == BenefitTimePeriod.None)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(Lan.g("enumBenefitTimePeriod", benefitList[i].TimePeriod.ToString()));
            } 
            if (benefitList[i].Quantity > 0)
            {
                if (benefitList[i].QuantityQualifier == BenefitQuantity.NumberOfServices && (benefitList[i].TimePeriod == BenefitTimePeriod.ServiceYear || benefitList[i].TimePeriod == BenefitTimePeriod.CalendarYear))
                {
                    row.getCells().Add(benefitList[i].Quantity.ToString() + " " + Lan.g(this,"times per year") + " ");
                }
                else
                {
                    row.getCells().Add(benefitList[i].Quantity.ToString() + " " + Lan.g("enumBenefitQuantity", benefitList[i].QuantityQualifier.ToString()));
                } 
            }
            else
            {
                row.getCells().add("");
            } 
            gridBenefits.getRows().add(row);
        }
        gridBenefits.endUpdate();
    }

    /*if(allCalendarYear){
    				checkCalendarYear.CheckState=CheckState.Checked;
    			}
    			else if(allServiceYear){
    				checkCalendarYear.CheckState=CheckState.Unchecked;
    			}
    			else{
    				checkCalendarYear.CheckState=CheckState.Indeterminate;
    			}*/
    private void gridBenefits_DoubleClick(Object sender, EventArgs e) throws Exception {
        long patPlanNum = 0;
        if (PatPlanCur != null)
        {
            patPlanNum = PatPlanCur.PatPlanNum;
        }
         
        FormInsBenefits FormI = new FormInsBenefits(PlanCur.PlanNum,patPlanNum);
        FormI.OriginalBenList = benefitList;
        FormI.Note = textSubscNote.Text;
        FormI.MonthRenew = PlanCur.MonthRenew;
        FormI.SubCur = SubCur;
        FormI.ShowDialog();
        if (FormI.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillBenefits();
        textSubscNote.Text = FormI.Note;
        PlanCur.MonthRenew = FormI.MonthRenew;
    }

    /**
    * Gets an employerNum based on the name entered. Called from FillCur
    */
    private void getEmployerNum() throws Exception {
        if (PlanCur.EmployerNum == 0)
        {
            //no employer was previously entered.
            if (StringSupport.equals(textEmployer.Text, ""))
            {
            }
            else
            {
                //no change
                PlanCur.EmployerNum = Employers.GetEmployerNum(textEmployer.Text);
            } 
        }
        else
        {
            //an employer was previously entered
            if (StringSupport.equals(textEmployer.Text, ""))
            {
                PlanCur.EmployerNum = 0;
            }
            else //if text has changed
            if (!StringSupport.equals(Employers.getName(PlanCur.EmployerNum), textEmployer.Text))
            {
                PlanCur.EmployerNum = Employers.GetEmployerNum(textEmployer.Text);
            }
              
        } 
    }

    private void butGetElectronic_Click(Object sender, EventArgs e) throws Exception {
        //button not visible if SubCur is null
        if (PrefC.getBool(PrefName.CustomizedForPracticeWeb))
        {
            eligibilityCheckDentalXchange();
            return ;
        }
         
        //Visible for everyone.
        Clearinghouse clearhouse = Clearinghouses.getDefaultDental();
        if (clearhouse == null)
        {
            MsgBox.show(this,"No clearinghouse is set as default.");
            return ;
        }
         
        if (clearhouse.CommBridge != EclaimsCommBridge.ClaimConnect && clearhouse.Eformat != ElectronicClaimFormat.Canadian)
        {
            MsgBox.show(this,"So far, eligibility checks only work with ClaimConnect and CDAnet.");
            return ;
        }
         
        if (clearhouse.Eformat == ElectronicClaimFormat.Canadian)
        {
            eligibilityCheckCanada();
            return ;
        }
         
        if (!fillPlanCurFromForm())
        {
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        try
        {
            x270Controller.requestBenefits(clearhouse,PlanCur,PatPlanCur.PatNum,CarrierCur,benefitList,PatPlanCur.PatPlanNum,SubCur);
        }
        catch (Exception ex)
        {
            //although many errors will be caught and result in a response etrans.
            //this also catches validation errors such as missing info.
            Cursor = Cursors.Default;
            if (ex.Message.Contains("AAA*N**79*"))
            {
                MsgBox.show(this,"There is a problem with your benefits request. The clearing house you are using (typically Claim Connect) may not support Real Time Eligibility " + "for this carrier. Please ensure the carrier's electronic ID is correct and that your clearing house supports Real Time Eligibility for this carrier.");
            }
            else
            {
                CodeBase.MsgBoxCopyPaste msgbox = new CodeBase.MsgBoxCopyPaste(ex.Message);
                msgbox.ShowDialog();
            } 
        }

        Cursor = Cursors.Default;
        DateTime dateLast270 = Etranss.getLastDate270(PlanCur.PlanNum);
        if (dateLast270.Year < 1880)
        {
            textElectBenLastDate.Text = "";
        }
        else
        {
            textElectBenLastDate.Text = dateLast270.ToShortDateString();
        } 
        fillBenefits();
    }

    private void butHistoryElect_Click(Object sender, EventArgs e) throws Exception {
        //button not visible if SubCur is null
        FormBenefitElectHistory formB = new FormBenefitElectHistory(PlanCur.PlanNum,PatPlanCur.PatPlanNum,SubCur.InsSubNum);
        formB.BenList = benefitList;
        formB.ShowDialog();
        DateTime dateLast270 = Etranss.getLastDate270(PlanCur.PlanNum);
        if (dateLast270.Year < 1880)
        {
            textElectBenLastDate.Text = "";
        }
        else
        {
            textElectBenLastDate.Text = dateLast270.ToShortDateString();
        } 
        fillBenefits();
    }

    //This is not our code.   Added SPK/AAD 10/06 for eligibility check.-------------------------------------------------------------------------
    private void eligibilityCheckDentalXchange() throws Exception {
        Cursor = Cursors.WaitCursor;
        OpenDental.com.dentalxchange.webservices.WebServiceService DCIService = new OpenDental.com.dentalxchange.webservices.WebServiceService();
        OpenDental.com.dentalxchange.webservices.Credentials DCICredential = new OpenDental.com.dentalxchange.webservices.Credentials();
        OpenDental.com.dentalxchange.webservices.Request DCIRequest = new OpenDental.com.dentalxchange.webservices.Request();
        OpenDental.com.dentalxchange.webservices.Response DCIResponse = new OpenDental.com.dentalxchange.webservices.Response();
        String loginID = new String();
        String passWord = new String();
        // Get Login / Password
        Clearinghouse dch = Clearinghouses.getDefaultDental();
        if (dch != null)
        {
            loginID = dch.LoginID;
            passWord = dch.Password;
        }
        else
        {
            loginID = "";
            passWord = "";
        } 
        if (StringSupport.equals(loginID, ""))
        {
            MessageBox.Show("ClaimConnect login ID and password are required to check eligibility.");
            Cursor = Cursors.Default;
            return ;
        }
         
        // Set Credentials
        DCICredential.setserviceID("DCI Web Service ID: 001513");
        DCICredential.setusername(loginID);
        // ABCuser
        DCICredential.setpassword(passWord);
        // testing1
        DCICredential.setclient("Practice-Web");
        DCICredential.setversion("1");
        // Set Request Document
        //textAddress.Text = PrepareEligibilityRequest();
        DCIRequest.setcontent(prepareEligibilityRequestDentalXchange(loginID,passWord));
        try
        {
            DCIResponse = DCIService.lookupEligibility(DCICredential,DCIRequest);
            //DisplayEligibilityStatus();
            ProcessEligibilityResponseDentalXchange(DCIResponse.getcontent().ToString());
        }
        catch (Exception __dummyCatchVar0)
        {
            //Exception ex) {
            // SPK /AAD 8/16/08 Display more user friendly error message
            MessageBox.Show("Error : Inadequate data for response. Payer site may be unavailable.");
        }

        Cursor = Cursors.Default;
    }

    private String prepareEligibilityRequestDentalXchange(String loginID, String passWord) throws Exception {
        DataTable table = new DataTable();
        String infoReceiverLastName = new String();
        String infoReceiverFirstName = new String();
        String practiceAddress1 = new String();
        String practiceAddress2 = new String();
        String practicePhone = new String();
        String practiceCity = new String();
        String practiceState = new String();
        String practiceZip = new String();
        String renderingProviderLastName = new String();
        String renderingProviderFirstName = new String();
        String GenderCode = new String();
        String TaxoCode = new String();
        String RelationShip = new String();
        XmlDocument doc = new XmlDocument();
        XmlNode EligNode = doc.CreateNode(XmlNodeType.Element, "EligRequest", "");
        doc.AppendChild(EligNode);
        // Prepare Namespace Attribute
        XmlAttribute nameSpaceAttribute = doc.CreateAttribute("xmlns", "xsi", "http://www.w3.org/2000/xmlns/");
        nameSpaceAttribute.Value = "http://www.w3.org/2001/XMLSchema-instance";
        doc.DocumentElement.SetAttributeNode(nameSpaceAttribute);
        // Prepare noNamespace Schema Location Attribute
        XmlAttribute noNameSpaceSchemaLocation = doc.CreateAttribute("xsi", "noNamespaceSchemaLocation", "http://www.w3.org/2001/XMLSchema-instance");
        //dmg Not sure what this is for. This path will not exist on Unix and will fail. In fact, this path
        //will either not exist or be read-only on most Windows boxes, so this path specification is probably
        //a bug, but has not caused any user complaints thus far.
        noNameSpaceSchemaLocation.Value = "D:\\eligreq.xsd";
        doc.DocumentElement.SetAttributeNode(noNameSpaceSchemaLocation);
        //  Prepare AuthInfo Node
        XmlNode AuthInfoNode = doc.CreateNode(XmlNodeType.Element, "AuthInfo", "");
        //  Create UserName / Password ChildNode for AuthInfoNode
        XmlNode UserName = doc.CreateNode(XmlNodeType.Element, "UserName", "");
        XmlNode Password = doc.CreateNode(XmlNodeType.Element, "Password", "");
        //  Set Value of UserID / Password
        UserName.InnerText = loginID;
        Password.InnerText = passWord;
        //  Append UserName / Password to AuthInfoNode
        AuthInfoNode.AppendChild(UserName);
        AuthInfoNode.AppendChild(Password);
        //  Append AuthInfoNode To EligNode
        EligNode.AppendChild(AuthInfoNode);
        //  Prepare Information Receiver Node
        XmlNode InfoReceiver = doc.CreateNode(XmlNodeType.Element, "InformationReceiver", "");
        XmlNode InfoAddress = doc.CreateNode(XmlNodeType.Element, "Address", "");
        XmlNode InfoAddressName = doc.CreateNode(XmlNodeType.Element, "Name", "");
        XmlNode InfoAddressFirstName = doc.CreateNode(XmlNodeType.Element, "FirstName", "");
        XmlNode InfoAddressLastName = doc.CreateNode(XmlNodeType.Element, "LastName", "");
        // Get Provider Information
        table = Providers.getDefaultPracticeProvider2();
        if (table.Rows.Count != 0)
        {
            infoReceiverFirstName = PIn.String(table.Rows[0][0].ToString());
            infoReceiverLastName = PIn.String(table.Rows[0][1].ToString());
            // Case statment for TaxoCode
            OpenDental.PIn.APPLY __dummyScrutVar2 = PIn.Long(table.Rows[0][2].ToString());
            if (__dummyScrutVar2.equals(1))
            {
                TaxoCode = "124Q00000X";
            }
            else if (__dummyScrutVar2.equals(2))
            {
                TaxoCode = "1223D0001X";
            }
            else if (__dummyScrutVar2.equals(3))
            {
                TaxoCode = "1223E0200X";
            }
            else if (__dummyScrutVar2.equals(4))
            {
                TaxoCode = "1223P0106X";
            }
            else if (__dummyScrutVar2.equals(5))
            {
                TaxoCode = "1223D0008X";
            }
            else if (__dummyScrutVar2.equals(6))
            {
                TaxoCode = "1223S0112X";
            }
            else if (__dummyScrutVar2.equals(7))
            {
                TaxoCode = "1223X0400X";
            }
            else if (__dummyScrutVar2.equals(8))
            {
                TaxoCode = "1223P0221X";
            }
            else if (__dummyScrutVar2.equals(9))
            {
                TaxoCode = "1223P0300X";
            }
            else if (__dummyScrutVar2.equals(10))
            {
                TaxoCode = "1223P0700X";
            }
            else
            {
                TaxoCode = "1223G0001X";
            }          
        }
        else
        {
            infoReceiverFirstName = "Unknown";
            infoReceiverLastName = "Unknown";
            TaxoCode = "Unknown";
        } 
            ;
        InfoAddressFirstName.InnerText = infoReceiverLastName;
        InfoAddressLastName.InnerText = infoReceiverFirstName;
        InfoAddressName.AppendChild(InfoAddressFirstName);
        InfoAddressName.AppendChild(InfoAddressLastName);
        XmlNode InfoAddressLine1 = doc.CreateNode(XmlNodeType.Element, "AddressLine1", "");
        XmlNode InfoAddressLine2 = doc.CreateNode(XmlNodeType.Element, "AddressLine2", "");
        XmlNode InfoPhone = doc.CreateNode(XmlNodeType.Element, "Phone", "");
        XmlNode InfoCity = doc.CreateNode(XmlNodeType.Element, "City", "");
        XmlNode InfoState = doc.CreateNode(XmlNodeType.Element, "State", "");
        XmlNode InfoZip = doc.CreateNode(XmlNodeType.Element, "Zip", "");
        //  Populate Practioner demographic from hash table
        practiceAddress1 = PrefC.getString(PrefName.PracticeAddress);
        practiceAddress2 = PrefC.getString(PrefName.PracticeAddress2);
        // Format Phone
        if (PrefC.getString(PrefName.PracticePhone).Length == 10)
        {
            practicePhone = PrefC.getString(PrefName.PracticePhone).Substring(0, 3) + "-" + PrefC.getString(PrefName.PracticePhone).Substring(3, 3) + "-" + PrefC.getString(PrefName.PracticePhone).Substring(6);
        }
        else
        {
            practicePhone = PrefC.getString(PrefName.PracticePhone);
        } 
        practiceCity = PrefC.getString(PrefName.PracticeCity);
        practiceState = PrefC.getString(PrefName.PracticeST);
        practiceZip = PrefC.getString(PrefName.PracticeZip);
        InfoAddressLine1.InnerText = practiceAddress1;
        InfoAddressLine2.InnerText = practiceAddress2;
        InfoPhone.InnerText = practicePhone;
        InfoCity.InnerText = practiceCity;
        InfoState.InnerText = practiceState;
        InfoZip.InnerText = practiceZip;
        InfoAddress.AppendChild(InfoAddressName);
        InfoAddress.AppendChild(InfoAddressLine1);
        InfoAddress.AppendChild(InfoAddressLine2);
        InfoAddress.AppendChild(InfoPhone);
        InfoAddress.AppendChild(InfoCity);
        InfoAddress.AppendChild(InfoState);
        InfoAddress.AppendChild(InfoZip);
        InfoReceiver.AppendChild(InfoAddress);
        //SPK / AAD 8/13/08 Add NPI -- Begin
        XmlNode InfoReceiverProviderNPI = doc.CreateNode(XmlNodeType.Element, "NPI", "");
        //Get Provider NPI #
        table = Providers.getDefaultPracticeProvider3();
        if (table.Rows.Count != 0)
        {
            InfoReceiverProviderNPI.InnerText = PIn.String(table.Rows[0][0].ToString());
        }
         
            ;
        InfoReceiver.AppendChild(InfoReceiverProviderNPI);
        //SPK / AAD 8/13/08 Add NPI -- End
        XmlNode InfoCredential = doc.CreateNode(XmlNodeType.Element, "Credential", "");
        XmlNode InfoCredentialType = doc.CreateNode(XmlNodeType.Element, "Type", "");
        XmlNode InfoCredentialValue = doc.CreateNode(XmlNodeType.Element, "Value", "");
        InfoCredentialType.InnerText = "TJ";
        InfoCredentialValue.InnerText = "123456789";
        InfoCredential.AppendChild(InfoCredentialType);
        InfoCredential.AppendChild(InfoCredentialValue);
        InfoReceiver.AppendChild(InfoCredential);
        XmlNode InfoTaxonomyCode = doc.CreateNode(XmlNodeType.Element, "TaxonomyCode", "");
        InfoTaxonomyCode.InnerText = TaxoCode;
        InfoReceiver.AppendChild(InfoTaxonomyCode);
        //  Append InfoReceiver To EligNode
        EligNode.AppendChild(InfoReceiver);
        //  Payer Info
        XmlNode InfoPayer = doc.CreateNode(XmlNodeType.Element, "Payer", "");
        XmlNode InfoPayerNEIC = doc.CreateNode(XmlNodeType.Element, "PayerNEIC", "");
        InfoPayerNEIC.InnerText = textElectID.Text;
        InfoPayer.AppendChild(InfoPayerNEIC);
        EligNode.AppendChild(InfoPayer);
        //  Patient
        XmlNode Patient = doc.CreateNode(XmlNodeType.Element, "Patient", "");
        XmlNode PatientName = doc.CreateNode(XmlNodeType.Element, "Name", "");
        XmlNode PatientFirstName = doc.CreateNode(XmlNodeType.Element, "FirstName", "");
        XmlNode PatientLastName = doc.CreateNode(XmlNodeType.Element, "LastName", "");
        XmlNode PatientDOB = doc.CreateNode(XmlNodeType.Element, "DOB", "");
        XmlNode PatientSubscriber = doc.CreateNode(XmlNodeType.Element, "SubscriberID", "");
        XmlNode PatientRelationship = doc.CreateNode(XmlNodeType.Element, "RelationshipCode", "");
        XmlNode PatientGender = doc.CreateNode(XmlNodeType.Element, "Gender", "");
        // Read Patient FName,LName,DOB, and Gender from Patient Table
        table = Patients.getPartialPatientData(PatPlanCur.PatNum);
        if (table.Rows.Count != 0)
        {
            PatientFirstName.InnerText = PIn.String(table.Rows[0][0].ToString());
            PatientLastName.InnerText = PIn.String(table.Rows[0][1].ToString());
            PatientDOB.InnerText = PIn.String(table.Rows[0][2].ToString());
            Text __dummyScrutVar3 = comboRelationship.Text;
            if (__dummyScrutVar3.equals("Self"))
            {
                RelationShip = "18";
            }
            else if (__dummyScrutVar3.equals("Spouse"))
            {
                RelationShip = "01";
            }
            else if (__dummyScrutVar3.equals("Child"))
            {
                RelationShip = "19";
            }
            else
            {
                RelationShip = "34";
            }   
            OpenDental.PIn.APPLY __dummyScrutVar4 = PIn.String(table.Rows[0][3].ToString());
            if (__dummyScrutVar4.equals("1"))
            {
                GenderCode = "F";
            }
            else
            {
                GenderCode = "M";
            } 
        }
        else
        {
            PatientFirstName.InnerText = "Unknown";
            PatientLastName.InnerText = "Unknown";
            PatientDOB.InnerText = "99/99/9999";
            RelationShip = "??";
            GenderCode = "?";
        } 
        PatientName.AppendChild(PatientFirstName);
        PatientName.AppendChild(PatientLastName);
        PatientSubscriber.InnerText = textSubscriberID.Text;
        PatientRelationship.InnerText = RelationShip;
        PatientGender.InnerText = GenderCode;
        Patient.AppendChild(PatientName);
        Patient.AppendChild(PatientDOB);
        Patient.AppendChild(PatientSubscriber);
        Patient.AppendChild(PatientRelationship);
        Patient.AppendChild(PatientGender);
        EligNode.AppendChild(Patient);
        //  Subscriber
        XmlNode Subscriber = doc.CreateNode(XmlNodeType.Element, "Subscriber", "");
        XmlNode SubscriberName = doc.CreateNode(XmlNodeType.Element, "Name", "");
        XmlNode SubscriberFirstName = doc.CreateNode(XmlNodeType.Element, "FirstName", "");
        XmlNode SubscriberLastName = doc.CreateNode(XmlNodeType.Element, "LastName", "");
        XmlNode SubscriberDOB = doc.CreateNode(XmlNodeType.Element, "DOB", "");
        XmlNode SubscriberSubscriber = doc.CreateNode(XmlNodeType.Element, "SubscriberID", "");
        XmlNode SubscriberRelationship = doc.CreateNode(XmlNodeType.Element, "RelationshipCode", "");
        XmlNode SubscriberGender = doc.CreateNode(XmlNodeType.Element, "Gender", "");
        // Read Subscriber FName,LName,DOB, and Gender from Patient Table
        table = Patients.getPartialPatientData2(PatPlanCur.PatNum);
        if (table.Rows.Count != 0)
        {
            SubscriberFirstName.InnerText = PIn.String(table.Rows[0][0].ToString());
            SubscriberLastName.InnerText = PIn.String(table.Rows[0][1].ToString());
            SubscriberDOB.InnerText = PIn.String(table.Rows[0][2].ToString());
            OpenDental.PIn.APPLY __dummyScrutVar5 = PIn.String(table.Rows[0][3].ToString());
            if (__dummyScrutVar5.equals("1"))
            {
                GenderCode = "F";
            }
            else
            {
                GenderCode = "M";
            } 
        }
        else
        {
            SubscriberFirstName.InnerText = "Unknown";
            SubscriberLastName.InnerText = "Unknown";
            SubscriberDOB.InnerText = "99/99/9999";
            GenderCode = "?";
        } 
        SubscriberName.AppendChild(SubscriberFirstName);
        SubscriberName.AppendChild(SubscriberLastName);
        SubscriberSubscriber.InnerText = textSubscriberID.Text;
        SubscriberRelationship.InnerText = RelationShip;
        SubscriberGender.InnerText = GenderCode;
        Subscriber.AppendChild(SubscriberName);
        Subscriber.AppendChild(SubscriberDOB);
        Subscriber.AppendChild(SubscriberSubscriber);
        Subscriber.AppendChild(SubscriberRelationship);
        Subscriber.AppendChild(SubscriberGender);
        EligNode.AppendChild(Subscriber);
        //  Prepare Information Receiver Node
        XmlNode RenderingProvider = doc.CreateNode(XmlNodeType.Element, "RenderingProvider", "");
        // SPK / AAD 8/13/08 Add Rendering Provider NPI It is same as Info Receiver NPI -- Start
        XmlNode RenderingProviderNPI = doc.CreateNode(XmlNodeType.Element, "NPI", "");
        // SPK / AAD 8/13/08 Add Rendering Provider NPI It is same as Info Receiver NPI -- End
        XmlNode RenderingAddress = doc.CreateNode(XmlNodeType.Element, "Address", "");
        XmlNode RenderingAddressName = doc.CreateNode(XmlNodeType.Element, "Name", "");
        XmlNode RenderingAddressFirstName = doc.CreateNode(XmlNodeType.Element, "FirstName", "");
        XmlNode RenderingAddressLastName = doc.CreateNode(XmlNodeType.Element, "LastName", "");
        // Get Rendering Provider first and lastname
        // Read Patient FName,LName,DOB, and Gender from Patient Table
        table = Providers.getPrimaryProviders(PatPlanCur.PatNum);
        if (table.Rows.Count != 0)
        {
            renderingProviderFirstName = PIn.String(table.Rows[0][0].ToString());
            renderingProviderLastName = PIn.String(table.Rows[0][1].ToString());
        }
        else
        {
            renderingProviderFirstName = infoReceiverFirstName;
            renderingProviderLastName = infoReceiverLastName;
        } 
            ;
        RenderingAddressFirstName.InnerText = renderingProviderFirstName;
        RenderingAddressLastName.InnerText = renderingProviderLastName;
        RenderingAddressName.AppendChild(RenderingAddressFirstName);
        RenderingAddressName.AppendChild(RenderingAddressLastName);
        XmlNode RenderingAddressLine1 = doc.CreateNode(XmlNodeType.Element, "AddressLine1", "");
        XmlNode RenderingAddressLine2 = doc.CreateNode(XmlNodeType.Element, "AddressLine2", "");
        XmlNode RenderingPhone = doc.CreateNode(XmlNodeType.Element, "Phone", "");
        XmlNode RenderingCity = doc.CreateNode(XmlNodeType.Element, "City", "");
        XmlNode RenderingState = doc.CreateNode(XmlNodeType.Element, "State", "");
        XmlNode RenderingZip = doc.CreateNode(XmlNodeType.Element, "Zip", "");
        RenderingProviderNPI.InnerText = InfoReceiverProviderNPI.InnerText;
        RenderingAddressLine1.InnerText = practiceAddress1;
        RenderingAddressLine2.InnerText = practiceAddress2;
        RenderingPhone.InnerText = practicePhone;
        RenderingCity.InnerText = practiceCity;
        RenderingState.InnerText = practiceState;
        RenderingZip.InnerText = practiceZip;
        RenderingAddress.AppendChild(RenderingAddressName);
        RenderingAddress.AppendChild(RenderingAddressLine1);
        RenderingAddress.AppendChild(RenderingAddressLine2);
        RenderingAddress.AppendChild(RenderingPhone);
        RenderingAddress.AppendChild(RenderingCity);
        RenderingAddress.AppendChild(RenderingState);
        RenderingAddress.AppendChild(RenderingZip);
        XmlNode RenderingCredential = doc.CreateNode(XmlNodeType.Element, "Credential", "");
        XmlNode RenderingCredentialType = doc.CreateNode(XmlNodeType.Element, "Type", "");
        XmlNode RenderingCredentialValue = doc.CreateNode(XmlNodeType.Element, "Value", "");
        RenderingCredentialType.InnerText = "TJ";
        RenderingCredentialValue.InnerText = "123456789";
        RenderingCredential.AppendChild(RenderingCredentialType);
        RenderingCredential.AppendChild(RenderingCredentialValue);
        XmlNode RenderingTaxonomyCode = doc.CreateNode(XmlNodeType.Element, "TaxonomyCode", "");
        RenderingTaxonomyCode.InnerText = TaxoCode;
        RenderingProvider.AppendChild(RenderingAddress);
        // SPK / AAD 8/13/08 Add Rendering Provider NPI It is same as Info Receiver NPI -- Start
        RenderingProvider.AppendChild(RenderingProviderNPI);
        // SPK / AAD 8/13/08 Add NPI -- End
        RenderingProvider.AppendChild(RenderingCredential);
        RenderingProvider.AppendChild(RenderingTaxonomyCode);
        //  Append RenderingProvider To EligNode
        EligNode.AppendChild(RenderingProvider);
        return doc.OuterXml;
    }

    private void processEligibilityResponseDentalXchange(String DCIResponse) throws Exception {
        XmlDocument doc = new XmlDocument();
        XmlNode IsEligibleNode = new XmlNode();
        String IsEligibleStatus = new String();
        doc.LoadXml(DCIResponse);
        IsEligibleNode = doc.SelectSingleNode("EligBenefitResponse/isEligible");
        InnerText __dummyScrutVar6 = IsEligibleNode.InnerText;
        if (__dummyScrutVar6.equals("0"))
        {
            // SPK
            // HINA Added 9/2.
            // Open new form to display complete response Detail
            Form formDisplayEligibilityResponse = new FormEligibilityResponseDisplay(doc,PatPlanCur.PatNum);
            formDisplayEligibilityResponse.ShowDialog();
        }
        else if (__dummyScrutVar6.equals("1"))
        {
            // SPK
            // Process Error code and Message Node AAD
            XmlNode ErrorCode = new XmlNode();
            XmlNode ErrorMessage = new XmlNode();
            ErrorCode = doc.SelectSingleNode("EligBenefitResponse/Response/ErrorCode");
            ErrorMessage = doc.SelectSingleNode("EligBenefitResponse/Response/ErrorMsg");
            IsEligibleStatus = textSubscriber.Text + " is Not Eligible. Error Code:";
            IsEligibleStatus += ErrorCode.InnerText + " Error Description:" + ErrorMessage.InnerText;
            MessageBox.Show(IsEligibleStatus);
        }
        else
        {
            IsEligibleStatus = textSubscriber.Text + " Eligibility status is Unknown";
            MessageBox.Show(IsEligibleStatus);
        }  
    }

    /**
    * Used from butGetElectronic_Click and from butOK_Click.  Returns false if unable to complete.  Also fills SubCur if not null.
    */
    private boolean fillPlanCurFromForm() throws Exception {
        if (!StringSupport.equals(textDateEffect.errorProvider1.GetError(textDateEffect), "") || !StringSupport.equals(textDateTerm.errorProvider1.GetError(textDateTerm), "") || !StringSupport.equals(textDentaide.errorProvider1.GetError(textDentaide), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (!StringSupport.equals(textPlanFlag.Text, "") && !StringSupport.equals(textPlanFlag.Text, "A") && !StringSupport.equals(textPlanFlag.Text, "V") && !StringSupport.equals(textPlanFlag.Text, "N"))
            {
                MsgBox.show(this,"Plan flag must be A, V, N, or blank.");
                return false;
            }
             
            if (StringSupport.equals(textPlanFlag.Text, ""))
            {
                if (checkIsPMP.Checked)
                {
                    MsgBox.show(this,"The provincial medical plan checkbox must be unchecked when the plan flag is blank.");
                    return false;
                }
                 
            }
            else
            {
                if (!checkIsPMP.Checked)
                {
                    MsgBox.show(this,"The provincial medical plan checkbox must be checked when the plan flag is not blank.");
                    return false;
                }
                 
                if (StringSupport.equals(textPlanFlag.Text, "A"))
                {
                    if (StringSupport.equals(textCanadianDiagCode.Text, "") || textCanadianDiagCode.Text != Canadian.TidyAN(textCanadianDiagCode.Text, textCanadianDiagCode.Text.Length, true))
                    {
                        MsgBox.show(this,"When plan flag is set to A, diagnostic code must be set and must be 6 characters or less in length.");
                        return false;
                    }
                     
                    if (StringSupport.equals(textCanadianInstCode.Text, "") || textCanadianInstCode.Text != Canadian.TidyAN(textCanadianInstCode.Text, textCanadianInstCode.Text.Length, true))
                    {
                        MsgBox.show(this,"When plan flag is set to A, institution code must be set and must be 6 characters or less in length.");
                        return false;
                    }
                     
                }
                 
            } 
        }
         
        if (StringSupport.equals(textSubscriberID.Text, "") && SubCur != null)
        {
            MsgBox.show(this,"Subscriber ID not allowed to be blank.");
            return false;
        }
         
        if (StringSupport.equals(textCarrier.Text, ""))
        {
            MsgBox.show(this,"Carrier not allowed to be blank.");
            return false;
        }
         
        if (PatPlanCur != null && !StringSupport.equals(textOrdinal.errorProvider1.GetError(textOrdinal), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (SubCur != null)
        {
            //Subscriber: Only changed when user clicks change button.
            SubCur.SubscriberID = textSubscriberID.Text;
            SubCur.DateEffective = PIn.Date(textDateEffect.Text);
            SubCur.DateTerm = PIn.Date(textDateTerm.Text);
            SubCur.ReleaseInfo = checkRelease.Checked;
            SubCur.AssignBen = checkAssign.Checked;
            SubCur.SubscNote = textSubscNote.Text;
        }
         
        //MonthRenew already handled inside benefit window.
        getEmployerNum();
        PlanCur.GroupName = textGroupName.Text;
        PlanCur.GroupNum = textGroupNum.Text;
        PlanCur.RxBIN = textBIN.Text;
        PlanCur.DivisionNo = textDivisionNo.Text;
        //only visible in Canada
        //carrier-----------------------------------------------------------------------------------------------------
        CarrierCur = new Carrier();
        CarrierCur.CarrierName = textCarrier.Text;
        CarrierCur.Phone = textPhone.Text;
        CarrierCur.Address = textAddress.Text;
        CarrierCur.Address2 = textAddress2.Text;
        CarrierCur.City = textCity.Text;
        CarrierCur.State = textState.Text;
        CarrierCur.Zip = textZip.Text;
        CarrierCur.ElectID = textElectID.Text;
        CarrierCur.NoSendElect = checkNoSendElect.Checked;
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            boolean carrierFound = true;
            try
            {
                CarrierCur = Carriers.getIndentical(CarrierCur);
            }
            catch (Exception __dummyCatchVar1)
            {
                //match not found
                carrierFound = false;
            }

            if (!carrierFound)
            {
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Carrier not found.  Create new carrier?"))
                {
                    return false;
                }
                 
                FormCarrierEdit formCE = new FormCarrierEdit();
                formCE.IsNew = true;
                formCE.CarrierCur = CarrierCur;
                formCE.ShowDialog();
                if (formCE.DialogResult != DialogResult.OK)
                {
                    return false;
                }
                 
            }
             
        }
        else
        {
            CarrierCur = Carriers.getIndentical(CarrierCur);
        } 
        PlanCur.CarrierNum = CarrierCur.CarrierNum;
        //plantype already handled.
        if (comboClaimForm.SelectedIndex != -1)
        {
            PlanCur.ClaimFormNum = ClaimForms.getListShort()[comboClaimForm.SelectedIndex].ClaimFormNum;
        }
         
        PlanCur.UseAltCode = checkAlternateCode.Checked;
        PlanCur.CodeSubstNone = checkCodeSubst.Checked;
        PlanCur.IsMedical = checkIsMedical.Checked;
        PlanCur.ClaimsUseUCR = checkClaimsUseUCR.Checked;
        PlanCur.IsHidden = checkIsHidden.Checked;
        PlanCur.ShowBaseUnits = checkShowBaseUnits.Checked;
        if (comboFeeSched.SelectedIndex == 0)
        {
            PlanCur.FeeSched = 0;
        }
        else
        {
            PlanCur.FeeSched = FeeSchedsStandard[comboFeeSched.SelectedIndex - 1].FeeSchedNum;
        } 
        if (comboCopay.SelectedIndex == 0)
        {
            PlanCur.CopayFeeSched = 0;
        }
        else
        {
            PlanCur.CopayFeeSched = FeeSchedsCopay[comboCopay.SelectedIndex - 1].FeeSchedNum;
        } 
        if (comboAllowedFeeSched.SelectedIndex == 0)
        {
            //percentage
            if (IsNewPlan && StringSupport.equals(PlanCur.PlanType, "") && PrefC.getBool(PrefName.AllowedFeeSchedsAutomate))
            {
                //add a fee schedule if needed
                FeeSched sched = FeeScheds.getByExactName(CarrierCur.CarrierName,FeeScheduleType.Allowed);
                if (sched == null)
                {
                    sched = new FeeSched();
                    sched.Description = CarrierCur.CarrierName;
                    sched.FeeSchedType = FeeScheduleType.Allowed;
                    //sched.IsNew=true;
                    sched.ItemOrder = FeeSchedC.getListLong().Count;
                    FeeScheds.insert(sched);
                    DataValid.setInvalid(InvalidType.FeeScheds);
                }
                 
                PlanCur.AllowedFeeSched = sched.FeeSchedNum;
            }
            else
            {
                PlanCur.AllowedFeeSched = 0;
            } 
        }
        else
        {
            PlanCur.AllowedFeeSched = FeeSchedsAllowed[comboAllowedFeeSched.SelectedIndex - 1].FeeSchedNum;
        } 
        PlanCur.CobRule = (EnumCobRule)comboCobRule.SelectedIndex;
        if (comboFilingCode.SelectedIndex == -1)
        {
            PlanCur.FilingCode = 0;
        }
        else
        {
            PlanCur.FilingCode = InsFilingCodeC.getListt()[comboFilingCode.SelectedIndex].InsFilingCodeNum;
        } 
        PlanCur.FilingCodeSubtype = 0;
        List<InsFilingCodeSubtype> subtypeList = InsFilingCodeSubtypes.getForInsFilingCode(PlanCur.FilingCode);
        if (comboFilingCodeSubtype.SelectedIndex != -1 && comboFilingCodeSubtype.SelectedIndex < subtypeList.Count)
        {
            PlanCur.FilingCodeSubtype = subtypeList[comboFilingCodeSubtype.SelectedIndex].InsFilingCodeSubtypeNum;
        }
         
        //Canadian------------------------------------------------------------------------------------------
        PlanCur.DentaideCardSequence = PIn.Byte(textDentaide.Text);
        PlanCur.CanadianPlanFlag = textPlanFlag.Text;
        //validated
        PlanCur.CanadianDiagnosticCode = textCanadianDiagCode.Text;
        //validated
        PlanCur.CanadianInstitutionCode = textCanadianInstCode.Text;
        //validated
        //Canadian end---------------------------------------------------------------------------------------
        PlanCur.TrojanID = textTrojanID.Text;
        PlanCur.PlanNote = textPlanNote.Text;
        return true;
    }

    private void butAudit_Click(Object sender, EventArgs e) throws Exception {
        List<Permissions> perms = new List<Permissions>();
        perms.Add(Permissions.InsPlanChangeCarrierName);
        FormAuditOneType FormA = new FormAuditOneType(0, perms, Lan.g(this,"All changes for InsPlan #") + " " + PlanCur.PlanNum, PlanCur.PlanNum);
        FormA.ShowDialog();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!fillPlanCurFromForm())
        {
            return ;
        }
         
        //also fills SubCur if not null
        //PatPlan-------------------------------------------------------------------------------------------
        if (PatPlanCur != null)
        {
            if (PIn.Long(textOrdinal.Text) != PatPlanCur.Ordinal)
            {
                //if ordinal changed
                PatPlanCur.Ordinal = (byte)(PatPlans.SetOrdinal(PatPlanCur.PatPlanNum, PIn.Int(textOrdinal.Text)));
            }
             
            PatPlanCur.IsPending = checkIsPending.Checked;
            PatPlanCur.Relationship = (Relat)comboRelationship.SelectedIndex;
            PatPlanCur.PatID = textPatID.Text;
            PatPlans.update(PatPlanCur);
        }
         
        //InsPlan-----------------------------------------------------------------------------------------
        if (SubCur == null)
        {
            //editing from big list.  No subscriber.  'pick from list' button not visible, making logic easier.
            //if(IsNewPlan) {//not yet implemented
            //	if(InsPlans.AreEqualValue(PlanCur,PlanCurOriginal)) {//If no changes
            //	}
            //	else {//changes were made
            //	}
            //}
            //else {//editing an existing plan from big list
            if (InsPlans.areEqualValue(PlanCur,PlanCurOriginal))
            {
                //If no changes
                //pick button doesn't complicate things.  Simply nothing to do.
                //Also, no SubCur, so just close the form.
                DialogResult = DialogResult.OK;
            }
            else
            {
                //changes were made
                InsPlans.update(PlanCur);
                DialogResult = DialogResult.OK;
            } 
        }
        else
        {
            //}
            //editing from within patient
            //Be very careful here.  User could have clicked 'pick from list' button, which would have changed PlanNum.
            //So we always compare with PlanNumOriginal.
            if (IsNewPlan)
            {
                if (InsPlans.areEqualValue(PlanCur,PlanCurOriginal))
                {
                    //New plan, no changes
                    if (PlanCur.PlanNum != PlanNumOriginal)
                    {
                        //clicked 'pick from list' button
                        //No need to update PlanCur because no changes.
                        //delete original plan.
                        InsPlans.delete(PlanNumOriginal);
                        //doesn't touch any other objects.
                        //do not need to update PlanCur because no changes were made.
                        SubCur.PlanNum = PlanCur.PlanNum;
                    }
                    else
                    {
                    } 
                }
                else
                {
                    //PatPlanCur.PlanNum=PlanCur.PlanNum;
                    //PatPlans.Update(PatPlanCur);
                    //When 'pick from list' button was pushed, benfitList was filled with benefits from the picked plan.
                    //Then, those benefits may or may not have been changed.
                    //benefitListOld will still contain the original defaults for a new plan, but they will be orphaned.
                    //So all the original benefits will be automatically deleted because they won't be found in the newlist.
                    //If any benefits were changed after picking, the synch further down will trigger updates for the benefits on the picked plan.
                    //new plan, no changes, not picked from list.
                    //do not need to update PlanCur because no changes were made.
                    //new plan, changes were made
                    if (PlanCur.PlanNum != PlanNumOriginal)
                    {
                        //clicked 'pick from list' button, and then made changes
                        if (radioChangeAll.Checked)
                        {
                            InsPlans.update(PlanCur);
                            //they might not realize that they would be changing an existing plan. Oh well.
                            InsPlans.delete(PlanNumOriginal);
                            //quick delete doesn't affect other objects.
                            SubCur.PlanNum = PlanCur.PlanNum;
                        }
                        else
                        {
                            //PatPlanCur.PlanNum=PlanCur.PlanNum;
                            //PatPlans.Update(PatPlanCur);
                            //Same logic applies to benefit list as the section above.
                            //option is checked for "create new plan if needed"
                            PlanCur.PlanNum = PlanNumOriginal;
                            InsPlans.update(PlanCur);
                            for (int i = 0;i < benefitList.Count;i++)
                            {
                                //no need to update PatPlan.  Same old PlanNum.
                                //When 'pick from list' button was pushed, benfitList was filled with benefits from the picked plan.
                                //benefitListOld was not touched and still contains the old benefits.  So the original benefits will be automatically deleted.
                                //We force copies to be made in the database, but with different PlanNums.
                                //Any other changes will be preserved.
                                if (benefitList[i].PlanNum > 0)
                                {
                                    benefitList[i].PlanNum = PlanCur.PlanNum;
                                    benefitList[i].BenefitNum = 0;
                                }
                                 
                            }
                        } 
                    }
                    else
                    {
                        //triggers insert during synch.
                        //new plan, changes made, not picked from list.
                        InsPlans.update(PlanCur);
                    } 
                } 
            }
            else
            {
                //editing an existing plan from within patient
                if (InsPlans.areEqualValue(PlanCur,PlanCurOriginal))
                {
                    //If no changes
                    if (PlanCur.PlanNum != PlanNumOriginal)
                    {
                        //clicked 'pick from list' button, then made no changes
                        //do not need to update PlanCur because no changes were made.
                        SubCur.PlanNum = PlanCur.PlanNum;
                    }
                    else
                    {
                    } 
                }
                else
                {
                    //PatPlanCur.PlanNum=PlanCur.PlanNum;
                    //PatPlans.Update(PatPlanCur);
                    //When 'pick from list' button was pushed, benefitListOld was filled with a shallow copy of the benefits from the picked list.
                    //So if any benefits were changed, the synch further down will trigger updates for the benefits on the picked plan.
                    //existing plan, no changes, not picked from list.
                    //do not need to update PlanCur because no changes were made.
                    //changes were made
                    if (PlanCur.PlanNum != PlanNumOriginal)
                    {
                        //clicked 'pick from list' button, and then made changes
                        if (radioChangeAll.Checked)
                        {
                            //warn user here?
                            InsPlans.update(PlanCur);
                            SubCur.PlanNum = PlanCur.PlanNum;
                        }
                        else
                        {
                            //PatPlanCur.PlanNum=PlanCur.PlanNum;
                            //PatPlans.Update(PatPlanCur);
                            //When 'pick from list' button was pushed, benefitListOld was filled with a shallow copy of the benefits from the picked list.
                            //So if any benefits were changed, the synch further down will trigger updates for the benefits on the picked plan.
                            //option is checked for "create new plan if needed"
                            if (comboLinked.Items.Count == 0)
                            {
                                //if this is the only subscriber
                                InsPlans.update(PlanCur);
                                SubCur.PlanNum = PlanCur.PlanNum;
                            }
                            else
                            {
                                //PatPlanCur.PlanNum=PlanCur.PlanNum;
                                //PatPlans.Update(PatPlanCur);
                                //When 'pick from list' button was pushed, benefitListOld was filled with a shallow copy of the benefits from the picked list.
                                //So if any benefits were changed, the synch further down will trigger updates for the benefits on the picked plan.
                                //if there are other subscribers
                                InsPlans.insert(PlanCur);
                                //this gives it a new primary key.
                                SubCur.PlanNum = PlanCur.PlanNum;
                                //PatPlanCur.PlanNum=PlanCur.PlanNum;
                                //PatPlans.Update(PatPlanCur);
                                //When 'pick from list' button was pushed, benefitListOld was filled with a shallow copy of the benefits from the picked list.
                                //We must clear the benefitListOld to prevent deletion of those benefits.
                                benefitListOld = new List<Benefit>();
                                for (int i = 0;i < benefitList.Count;i++)
                                {
                                    //Force copies to be made in the database, but with different PlanNum;
                                    if (benefitList[i].PlanNum > 0)
                                    {
                                        benefitList[i].PlanNum = PlanCur.PlanNum;
                                        benefitList[i].BenefitNum = 0;
                                    }
                                     
                                }
                            } 
                        } 
                    }
                    else
                    {
                        //triggers insert during synch.
                        //existing plan, changes made, not picked from list.
                        if (radioChangeAll.Checked)
                        {
                            InsPlans.update(PlanCur);
                        }
                        else
                        {
                            //option is checked for "create new plan if needed"
                            if (comboLinked.Items.Count == 0)
                            {
                                //if this is the only subscriber
                                InsPlans.update(PlanCur);
                            }
                            else
                            {
                                //if there are other subscribers
                                InsPlans.insert(PlanCur);
                                //this gives it a new primary key.
                                SubCur.PlanNum = PlanCur.PlanNum;
                                //PatPlanCur.PlanNum=PlanCur.PlanNum;
                                //PatPlans.Update(PatPlanCur);
                                //make copies of all the benefits
                                benefitListOld = new List<Benefit>();
                                for (int i = 0;i < benefitList.Count;i++)
                                {
                                    if (benefitList[i].PlanNum > 0)
                                    {
                                        benefitList[i].PlanNum = PlanCur.PlanNum;
                                        benefitList[i].BenefitNum = 0;
                                    }
                                     
                                }
                            } 
                        } 
                    } 
                } 
            } 
        } 
        //triggers insert.
        //PatPlanCur.InsSubNum is already set before opening this window.  There is no possible way to change it from within this window.  Even if PlanNum changes, it's still the same inssub.  And even if inssub.Subscriber changes, it's still the same inssub.  So no change to PatPlanCur.InsSubNum is ever require from within this window.
        //Synch benefits-----------------------------------------------------------------------------------------------
        Benefits.updateList(benefitListOld,benefitList);
        //Update SubCur if needed-------------------------------------------------------------------------------------
        if (SubCur != null)
        {
            //SubCur.PlanNum=PlanCur.PlanNum;//done above
            InsSubs.update(SubCur);
            //also saves the other fields besides PlanNum
            //Udate all claims, claimprocs, payplans, and etrans that are pointing at the inssub.InsSubNum since it may now be pointing at a new insplan.PlanNum.
            InsSubs.synchPlanNumsForNewPlan(SubCur);
            InsPlans.computeEstimatesForSubscriber(SubCur.Subscriber);
        }
         
        //Check for changes in the carrier
        if (PlanCur.CarrierNum != PlanCurOriginal.CarrierNum)
        {
            long patNum = 0;
            if (PatPlanCur != null)
            {
                //PatPlanCur will be null if editing insurance plans from Lists > Insurance Plans.
                patNum = PatPlanCur.PatNum;
            }
             
            String carrierNameOrig = Carriers.getCarrier(PlanCurOriginal.CarrierNum).CarrierName;
            String carrierNameNew = Carriers.getCarrier(PlanCur.CarrierNum).CarrierName;
            if (!StringSupport.equals(carrierNameOrig, carrierNameNew))
            {
                //The CarrierNum could have changed but the CarrierName might not have changed.  Only make an audit entry if the name changed.
                SecurityLogs.makeLogEntry(Permissions.InsPlanChangeCarrierName,patNum,Lan.g(this,"Carrier name changed in Edit Insurance Plan window from") + " " + carrierNameOrig + " " + Lan.g(this,"to") + " " + carrierNameNew,PlanCur.PlanNum);
            }
             
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formInsPlan_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        //So, user cancelled a new entry
        if (IsNewPlan)
        {
            //this would also be new coverage
            //warning: If user clicked 'pick from list' button, then we don't want to delete an existing plan used by others
            InsPlans.delete(PlanNumOriginal);
            //safe, does not delete other objects
            Benefits.deleteForPlan(PlanNumOriginal);
            if (SubCur != null)
            {
                InsSubs.delete(SubCur.InsSubNum);
            }
             
        }
         
        //else if(IsNewPatPlan){//but plan is not new
        if (IsNewPatPlan)
        {
            PatPlans.delete(PatPlanCur.PatPlanNum);
        }
         
    }

}


//no need to check dependencies.  Maintains ordinals and recomputes estimates.