//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:33 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.AutomationL;
import OpenDental.ContrTable.__MultiCellEventHandler;
import OpenDental.DateTimeOD;
import OpenDental.FormAdjust;
import OpenDental.FormAutoCodeLessIntrusive;
import OpenDental.FormAutoNoteCompose;
import OpenDental.FormClaimProc;
import OpenDental.FormInsPlanSelect;
import OpenDental.FormPayment;
import OpenDental.FormProcCodes;
import OpenDental.FormProcEdit;
import OpenDental.FormProcEditExplain;
import OpenDental.FormProcNoteAppend;
import OpenDental.FormProviderPick;
import OpenDental.FormReferralsPatient;
import OpenDental.FormSites;
import OpenDental.FormSnomeds;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.RefAttach;
import OpenDental.RefAttaches;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Adjustments;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.AutoCode;
import OpenDentBusiness.AutoCodeItems;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Encounters;
import OpenDentBusiness.EnumProcDrugUnit;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fees;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.OrionDPC;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionProcs;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.PaySplits;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PlaceOfService;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.ProcApptColors;
import OpenDentBusiness.ProcCodeNotes;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodeC;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.ProcUnitQtyType;
import OpenDentBusiness.Programs;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.Referral;
import OpenDentBusiness.Referrals;
import OpenDentBusiness.ReferralToStatus;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Sites;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormProcEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelDate = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelAmount = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSurfaces = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDesc = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.TextBox textRange = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelTooth = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelRange = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelSurfaces = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupQuadrant = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioUR = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioUL = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioLL = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioLR = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupArch = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioU = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioL = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.GroupBox groupSextant = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioS1 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioS3 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioS2 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioS4 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioS5 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioS6 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    /**
    * Mostly used for permissions.
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Label labelClaim = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listBoxTeeth = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listBoxTeeth2 = new System.Windows.Forms.ListBox();
    private OpenDental.UI.Button butChange;
    //private ProcStat OriginalStatus;
    private ErrorProvider errorProvider2 = new ErrorProvider();
    private System.Windows.Forms.TextBox textTooth = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butEditAnyway;
    private System.Windows.Forms.Label labelDx = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboPlaceService = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelPlaceService = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butSetComplete;
    private System.Windows.Forms.Label labelPriority = new System.Windows.Forms.Label();
    private ProcedureCode ProcedureCode2;
    private System.Windows.Forms.Label labelSetComplete = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butAddEstimate;
    private Procedure ProcCur;
    private Procedure ProcOld;
    //private List<ClaimProc> ClaimProcList;
    private OpenDental.ValidDouble textProcFee;
    private System.Windows.Forms.CheckBox checkNoBillIns = new System.Windows.Forms.CheckBox();
    private OpenDental.ODtextBox textNotes;
    private List<ClaimProc> ClaimProcsForProc = new List<ClaimProc>();
    //private Adjustment[] AdjForProc;
    private ArrayList PaySplitsForProc = new ArrayList();
    private ArrayList AdjustmentsForProc = new ArrayList();
    private Patient PatCur;
    private Family FamCur;
    private OpenDental.UI.Button butAddAdjust;
    private OpenDental.TableProcAdj tbAdj;
    private OpenDental.TableProcPay tbPay;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private System.Windows.Forms.Label labelIncomplete = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateEntry;
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    /**
    * List of all payments (not paysplits) that this procedure is attached to.
    */
    private List<Payment> PaymentsForProc = new List<Payment>();
    private Userod CurUser;
    //private uint m_autoAPIMsg;//ENP
    private static final String APPBAR_AUTOMATION_API_MESSAGE = "EZNotes.AppBarStandalone.Auto.API.Message";
    private static final uint MSG_RESTORE = 2;
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textMedicalCode = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textDiagnosticCode = new System.Windows.Forms.TextBox();
    //ENP
    private static final uint MSG_GETLASTNOTE = 3;
    private System.Windows.Forms.CheckBox checkIsPrincDiag = new System.Windows.Forms.CheckBox();
    //ENP
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private Label label14 = new Label();
    private Label label15 = new Label();
    private Label label16 = new Label();
    private OpenDental.UI.Button butClearSig;
    private OpenDental.UI.SignatureBox sigBox;
    private List<Benefit> BenefitList = new List<Benefit>();
    private boolean SigChanged = new boolean();
    private ComboBox comboProvNum = new ComboBox();
    private ComboBox comboDx = new ComboBox();
    private ComboBox comboPriority = new ComboBox();
    private TextBox textUser = new TextBox();
    //private Label label17;
    //private Label label18;
    private Label label19 = new Label();
    private TextBox textCodeMod1 = new TextBox();
    private ComboBox comboBillingTypeTwo = new ComboBox();
    private Label labelBillingTypeTwo = new Label();
    private ComboBox comboBillingTypeOne = new ComboBox();
    private Label labelBillingTypeOne = new Label();
    private TextBox textCodeMod4 = new TextBox();
    private TextBox textCodeMod3 = new TextBox();
    private TextBox textCodeMod2 = new TextBox();
    private TextBox textRevCode = new TextBox();
    private Label label22 = new Label();
    private TextBox textUnitQty = new TextBox();
    private Label label21 = new Label();
    private OpenDental.UI.Button buttonUseAutoNote;
    private ToolTip toolTip1 = new ToolTip();
    private IContainer components = new IContainer();
    private OpenDental.ValidDate textDateTP;
    private Label label27 = new Label();
    private Label label26 = new Label();
    /**
    * This keeps the noteChanged event from erasing the signature when first loading.
    */
    private boolean IsStartingUp = new boolean();
    private List<Claim> ClaimList = new List<Claim>();
    //private OpenDental.UI.Button butTopazSign;
    private Panel panelSurfaces = new Panel();
    private OpenDental.UI.Button butD;
    private OpenDental.UI.Button butBF;
    private OpenDental.UI.Button butL;
    private OpenDental.UI.Button butM;
    private OpenDental.UI.Button butV;
    private OpenDental.UI.Button butOI;
    private OpenDental.UI.Button butTopazSign;
    private Label labelInvalidSig = new Label();
    private Control sigBoxTopaz = new Control();
    //private bool allowTopaz;
    private OpenDental.UI.Button butPickSite;
    private TextBox textSite = new TextBox();
    private Label labelSite = new Label();
    private OpenDental.UI.ODGrid gridIns;
    private boolean StartedAttachedToClaim = new boolean();
    public List<ClaimProcHist> HistList = new List<ClaimProcHist>();
    private OpenDental.UI.Button butPickProv;
    private CheckBox checkHideGraphics = new CheckBox();
    private Label label3 = new Label();
    private Label label4 = new Label();
    private OpenDental.ValidDate textDateOriginalProsth;
    private ListBox listProsth = new ListBox();
    private GroupBox groupProsth = new GroupBox();
    private CheckBox checkTypeCodeA = new CheckBox();
    private CheckBox checkTypeCodeB = new CheckBox();
    private CheckBox checkTypeCodeC = new CheckBox();
    private CheckBox checkTypeCodeE = new CheckBox();
    private CheckBox checkTypeCodeL = new CheckBox();
    private CheckBox checkTypeCodeX = new CheckBox();
    private CheckBox checkTypeCodeS = new CheckBox();
    private GroupBox groupCanadianProcTypeCode = new GroupBox();
    private Label labelDateStop = new Label();
    private Label labelDateSched = new Label();
    private Label labelDPC = new Label();
    private Label labelStatus = new Label();
    private ComboBox comboStatus = new ComboBox();
    private OpenDental.ValidDate textDateScheduled;
    private ComboBox comboDPC = new ComboBox();
    private OpenDental.ValidDate textDateStop;
    private CheckBox checkIsEffComm = new CheckBox();
    private CheckBox checkIsOnCall = new CheckBox();
    private CheckBox checkIsRepair = new CheckBox();
    public List<ClaimProcHist> LoopList = new List<ClaimProcHist>();
    private Label labelEndTime = new Label();
    private OpenDental.UI.Button butNow;
    private OpenDental.ValidDate textDate;
    private TextBox textTimeEnd = new TextBox();
    private Label labelScheduleBy = new Label();
    private OrionProc OrionProcCur;
    private OrionProc OrionProcOld;
    private DateTime CancelledScheduleByDate = new DateTime();
    public long OrionProvNum = new long();
    public boolean OrionDentist = new boolean();
    private TextBox textTimeStart = new TextBox();
    private Label labelStartTime = new Label();
    private Label labelDPCpost = new Label();
    private ComboBox comboDPCpost = new ComboBox();
    private ComboBox comboPrognosis = new ComboBox();
    private Label labelPrognosis = new Label();
    private ComboBox comboProcStatus = new ComboBox();
    private ComboBox comboDrugUnit = new ComboBox();
    private Label label1 = new Label();
    private Label label5 = new Label();
    private TextBox textDrugNDC = new TextBox();
    private Label label10 = new Label();
    private TextBox textDrugQty = new TextBox();
    private Label label13 = new Label();
    private TextBox textReferral = new TextBox();
    private OpenDental.UI.Button butReferral;
    private Label labelClaimNote = new Label();
    private OpenDental.ODtextBox textClaimNote;
    private TextBox textTimeFinal = new TextBox();
    private Label labelTimeFinal = new Label();
    private TabControl tabControl = new TabControl();
    private TabPage tabPageFinancial = new TabPage();
    private TabPage tabPageMedical = new TabPage();
    private TabPage tabPageMisc = new TabPage();
    private TabPage tabPageCanada = new TabPage();
    private TabPage tabPageOrion = new TabPage();
    private Label label17 = new Label();
    private ComboBox comboUnitType = new ComboBox();
    private Label labelCanadaLabFee2 = new Label();
    private Label labelCanadaLabFee1 = new Label();
    private OpenDental.ValidDouble textCanadaLabFee2;
    private OpenDental.ValidDouble textCanadaLabFee1;
    private List<InsSub> SubList = new List<InsSub>();
    private Label labelLocked = new Label();
    private OpenDental.UI.Button butAppend;
    private OpenDental.UI.Button butLock;
    private OpenDental.UI.Button butInvalidate;
    private Label label18 = new Label();
    private TextBox textBillingNote = new TextBox();
    private OpenDental.UI.Button butSearch;
    private OpenDental.UI.Button butSnomedBodySiteSelect;
    private Label label20 = new Label();
    private TextBox textSnomedBodySite = new TextBox();
    private List<Procedure> canadaLabFees = new List<Procedure>();
    private OpenDental.UI.Button butNoneSnomedBodySite;
    private Snomed _snomedBodySite = null;
    /**
    * Inserts are not done within this dialog, but must be done ahead of time from outside.  You must specify a procedure to edit, and only the changes that are made in this dialog get saved.  Only used when double click in Account, Chart, TP, and in ContrChart.AddProcedure().  The procedure may be deleted if new, and user hits Cancel.
    */
    public FormProcEdit(Procedure proc, Patient patCur, Family famCur) throws Exception {
        ProcCur = proc;
        ProcOld = proc.copy();
        PatCur = patCur;
        FamCur = famCur;
        SubList = InsSubs.refreshForFam(FamCur);
        PlanList = InsPlans.refreshForSubList(SubList);
        //HistList=null;
        //LoopList=null;
        initializeComponent();
        Lan.f(this);
        //allowTopaz=(Environment.OSVersion.Platform!=PlatformID.Unix && !CodeBase.ODEnvironment.Is64BitOperatingSystem());
        sigBox.setTabletState(1);
        //if(!allowTopaz) {
        //	butTopazSign.Visible=false;
        //	sigBox.Visible=true;
        //}
        //else{
        //Add signature box for Topaz signatures.
        sigBoxTopaz = CodeBase.TopazWrapper.getTopaz();
        sigBoxTopaz.Location = sigBox.Location;
        //this puts both boxes in the same spot.
        sigBoxTopaz.Name = "sigBoxTopaz";
        sigBoxTopaz.Size = new System.Drawing.Size(362, 79);
        sigBoxTopaz.TabIndex = 92;
        sigBoxTopaz.Text = "sigPlusNET1";
        sigBoxTopaz.Visible = false;
        Controls.Add(sigBoxTopaz);
        //It starts out accepting input. It will be set to 0 if a sig is already present.  It will be set back to 1 if note changes or if user clicks Clear.
        CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,1);
        //}
        if (!PrefC.getBool(PrefName.ShowFeatureMedicalInsurance))
        {
            tabControl.TabPages.Remove(tabPageMedical);
        }
         
    }

    //groupMedical.Visible=false;
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    private void initializeComponent() throws Exception {
        this.components = new System.ComponentModel.Container();
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcEdit.class);
        this.labelDate = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.labelTooth = new System.Windows.Forms.Label();
        this.labelSurfaces = new System.Windows.Forms.Label();
        this.labelAmount = new System.Windows.Forms.Label();
        this.textProc = new System.Windows.Forms.TextBox();
        this.textTooth = new System.Windows.Forms.TextBox();
        this.textSurfaces = new System.Windows.Forms.TextBox();
        this.label6 = new System.Windows.Forms.Label();
        this.textDesc = new System.Windows.Forms.TextBox();
        this.label7 = new System.Windows.Forms.Label();
        this.labelRange = new System.Windows.Forms.Label();
        this.textRange = new System.Windows.Forms.TextBox();
        this.groupQuadrant = new System.Windows.Forms.GroupBox();
        this.radioLR = new System.Windows.Forms.RadioButton();
        this.radioLL = new System.Windows.Forms.RadioButton();
        this.radioUL = new System.Windows.Forms.RadioButton();
        this.radioUR = new System.Windows.Forms.RadioButton();
        this.groupArch = new System.Windows.Forms.GroupBox();
        this.radioL = new System.Windows.Forms.RadioButton();
        this.radioU = new System.Windows.Forms.RadioButton();
        this.panelSurfaces = new System.Windows.Forms.Panel();
        this.groupSextant = new System.Windows.Forms.GroupBox();
        this.radioS6 = new System.Windows.Forms.RadioButton();
        this.radioS5 = new System.Windows.Forms.RadioButton();
        this.radioS4 = new System.Windows.Forms.RadioButton();
        this.radioS2 = new System.Windows.Forms.RadioButton();
        this.radioS3 = new System.Windows.Forms.RadioButton();
        this.radioS1 = new System.Windows.Forms.RadioButton();
        this.label9 = new System.Windows.Forms.Label();
        this.labelDx = new System.Windows.Forms.Label();
        this.panel1 = new System.Windows.Forms.Panel();
        this.textTimeFinal = new System.Windows.Forms.TextBox();
        this.labelTimeFinal = new System.Windows.Forms.Label();
        this.textTimeStart = new System.Windows.Forms.TextBox();
        this.textTimeEnd = new System.Windows.Forms.TextBox();
        this.label27 = new System.Windows.Forms.Label();
        this.label26 = new System.Windows.Forms.Label();
        this.listBoxTeeth = new System.Windows.Forms.ListBox();
        this.label12 = new System.Windows.Forms.Label();
        this.labelStartTime = new System.Windows.Forms.Label();
        this.labelEndTime = new System.Windows.Forms.Label();
        this.listBoxTeeth2 = new System.Windows.Forms.ListBox();
        this.textDrugQty = new System.Windows.Forms.TextBox();
        this.label10 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.textDrugNDC = new System.Windows.Forms.TextBox();
        this.comboDrugUnit = new System.Windows.Forms.ComboBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textRevCode = new System.Windows.Forms.TextBox();
        this.label22 = new System.Windows.Forms.Label();
        this.textUnitQty = new System.Windows.Forms.TextBox();
        this.label21 = new System.Windows.Forms.Label();
        this.textCodeMod4 = new System.Windows.Forms.TextBox();
        this.textCodeMod3 = new System.Windows.Forms.TextBox();
        this.textCodeMod2 = new System.Windows.Forms.TextBox();
        this.label19 = new System.Windows.Forms.Label();
        this.textCodeMod1 = new System.Windows.Forms.TextBox();
        this.checkIsPrincDiag = new System.Windows.Forms.CheckBox();
        this.label11 = new System.Windows.Forms.Label();
        this.textDiagnosticCode = new System.Windows.Forms.TextBox();
        this.label8 = new System.Windows.Forms.Label();
        this.textMedicalCode = new System.Windows.Forms.TextBox();
        this.labelClaim = new System.Windows.Forms.Label();
        this.comboPlaceService = new System.Windows.Forms.ComboBox();
        this.labelPlaceService = new System.Windows.Forms.Label();
        this.labelPriority = new System.Windows.Forms.Label();
        this.labelSetComplete = new System.Windows.Forms.Label();
        this.checkNoBillIns = new System.Windows.Forms.CheckBox();
        this.labelIncomplete = new System.Windows.Forms.Label();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.label14 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.comboPriority = new System.Windows.Forms.ComboBox();
        this.comboDx = new System.Windows.Forms.ComboBox();
        this.comboProvNum = new System.Windows.Forms.ComboBox();
        this.textUser = new System.Windows.Forms.TextBox();
        this.comboBillingTypeTwo = new System.Windows.Forms.ComboBox();
        this.labelBillingTypeTwo = new System.Windows.Forms.Label();
        this.comboBillingTypeOne = new System.Windows.Forms.ComboBox();
        this.labelBillingTypeOne = new System.Windows.Forms.Label();
        this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
        this.labelInvalidSig = new System.Windows.Forms.Label();
        this.textSite = new System.Windows.Forms.TextBox();
        this.labelSite = new System.Windows.Forms.Label();
        this.checkHideGraphics = new System.Windows.Forms.CheckBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.listProsth = new System.Windows.Forms.ListBox();
        this.groupProsth = new System.Windows.Forms.GroupBox();
        this.checkTypeCodeA = new System.Windows.Forms.CheckBox();
        this.checkTypeCodeB = new System.Windows.Forms.CheckBox();
        this.checkTypeCodeC = new System.Windows.Forms.CheckBox();
        this.checkTypeCodeE = new System.Windows.Forms.CheckBox();
        this.checkTypeCodeL = new System.Windows.Forms.CheckBox();
        this.checkTypeCodeX = new System.Windows.Forms.CheckBox();
        this.checkTypeCodeS = new System.Windows.Forms.CheckBox();
        this.groupCanadianProcTypeCode = new System.Windows.Forms.GroupBox();
        this.labelDPCpost = new System.Windows.Forms.Label();
        this.comboDPCpost = new System.Windows.Forms.ComboBox();
        this.labelScheduleBy = new System.Windows.Forms.Label();
        this.checkIsRepair = new System.Windows.Forms.CheckBox();
        this.checkIsEffComm = new System.Windows.Forms.CheckBox();
        this.checkIsOnCall = new System.Windows.Forms.CheckBox();
        this.comboDPC = new System.Windows.Forms.ComboBox();
        this.comboStatus = new System.Windows.Forms.ComboBox();
        this.labelStatus = new System.Windows.Forms.Label();
        this.labelDateStop = new System.Windows.Forms.Label();
        this.labelDateSched = new System.Windows.Forms.Label();
        this.labelDPC = new System.Windows.Forms.Label();
        this.comboPrognosis = new System.Windows.Forms.ComboBox();
        this.labelPrognosis = new System.Windows.Forms.Label();
        this.comboProcStatus = new System.Windows.Forms.ComboBox();
        this.label13 = new System.Windows.Forms.Label();
        this.textReferral = new System.Windows.Forms.TextBox();
        this.labelClaimNote = new System.Windows.Forms.Label();
        this.tabControl = new System.Windows.Forms.TabControl();
        this.tabPageFinancial = new System.Windows.Forms.TabPage();
        this.tabPageMedical = new System.Windows.Forms.TabPage();
        this.label17 = new System.Windows.Forms.Label();
        this.comboUnitType = new System.Windows.Forms.ComboBox();
        this.tabPageMisc = new System.Windows.Forms.TabPage();
        this.textBillingNote = new System.Windows.Forms.TextBox();
        this.label18 = new System.Windows.Forms.Label();
        this.tabPageCanada = new System.Windows.Forms.TabPage();
        this.labelCanadaLabFee2 = new System.Windows.Forms.Label();
        this.labelCanadaLabFee1 = new System.Windows.Forms.Label();
        this.tabPageOrion = new System.Windows.Forms.TabPage();
        this.labelLocked = new System.Windows.Forms.Label();
        this.gridIns = new OpenDental.UI.ODGrid();
        this.label20 = new System.Windows.Forms.Label();
        this.textSnomedBodySite = new System.Windows.Forms.TextBox();
        this.butSearch = new OpenDental.UI.Button();
        this.butLock = new OpenDental.UI.Button();
        this.butInvalidate = new OpenDental.UI.Button();
        this.butAppend = new OpenDental.UI.Button();
        this.butAddEstimate = new OpenDental.UI.Button();
        this.tbAdj = new OpenDental.TableProcAdj();
        this.tbPay = new OpenDental.TableProcPay();
        this.butAddAdjust = new OpenDental.UI.Button();
        this.butNoneSnomedBodySite = new OpenDental.UI.Button();
        this.butSnomedBodySiteSelect = new OpenDental.UI.Button();
        this.butPickSite = new OpenDental.UI.Button();
        this.textCanadaLabFee2 = new OpenDental.ValidDouble();
        this.textCanadaLabFee1 = new OpenDental.ValidDouble();
        this.textDateStop = new OpenDental.ValidDate();
        this.textDateScheduled = new OpenDental.ValidDate();
        this.textClaimNote = new OpenDental.ODtextBox();
        this.butReferral = new OpenDental.UI.Button();
        this.butPickProv = new OpenDental.UI.Button();
        this.butTopazSign = new OpenDental.UI.Button();
        this.buttonUseAutoNote = new OpenDental.UI.Button();
        this.sigBox = new OpenDental.UI.SignatureBox();
        this.butClearSig = new OpenDental.UI.Button();
        this.textDateOriginalProsth = new OpenDental.ValidDate();
        this.textNotes = new OpenDental.ODtextBox();
        this.butSetComplete = new OpenDental.UI.Button();
        this.butEditAnyway = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.butNow = new OpenDental.UI.Button();
        this.butD = new OpenDental.UI.Button();
        this.butBF = new OpenDental.UI.Button();
        this.butL = new OpenDental.UI.Button();
        this.butM = new OpenDental.UI.Button();
        this.butV = new OpenDental.UI.Button();
        this.butOI = new OpenDental.UI.Button();
        this.textDateTP = new OpenDental.ValidDate();
        this.textDateEntry = new OpenDental.ValidDate();
        this.textProcFee = new OpenDental.ValidDouble();
        this.butChange = new OpenDental.UI.Button();
        this.groupQuadrant.SuspendLayout();
        this.groupArch.SuspendLayout();
        this.panelSurfaces.SuspendLayout();
        this.groupSextant.SuspendLayout();
        this.panel1.SuspendLayout();
        this.groupProsth.SuspendLayout();
        this.groupCanadianProcTypeCode.SuspendLayout();
        this.tabControl.SuspendLayout();
        this.tabPageFinancial.SuspendLayout();
        this.tabPageMedical.SuspendLayout();
        this.tabPageMisc.SuspendLayout();
        this.tabPageCanada.SuspendLayout();
        this.tabPageOrion.SuspendLayout();
        this.SuspendLayout();
        //
        // labelDate
        //
        this.labelDate.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelDate.Location = new System.Drawing.Point(8, 44);
        this.labelDate.Name = "labelDate";
        this.labelDate.Size = new System.Drawing.Size(96, 14);
        this.labelDate.TabIndex = 0;
        this.labelDate.Text = "Date";
        this.labelDate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label2
        //
        this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label2.Location = new System.Drawing.Point(26, 63);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(79, 12);
        this.label2.TabIndex = 1;
        this.label2.Text = "Procedure";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelTooth
        //
        this.labelTooth.Location = new System.Drawing.Point(68, 107);
        this.labelTooth.Name = "labelTooth";
        this.labelTooth.Size = new System.Drawing.Size(36, 12);
        this.labelTooth.TabIndex = 2;
        this.labelTooth.Text = "Tooth";
        this.labelTooth.TextAlign = System.Drawing.ContentAlignment.TopRight;
        this.labelTooth.Visible = false;
        //
        // labelSurfaces
        //
        this.labelSurfaces.Location = new System.Drawing.Point(33, 135);
        this.labelSurfaces.Name = "labelSurfaces";
        this.labelSurfaces.Size = new System.Drawing.Size(73, 16);
        this.labelSurfaces.TabIndex = 3;
        this.labelSurfaces.Text = "Surfaces";
        this.labelSurfaces.TextAlign = System.Drawing.ContentAlignment.TopRight;
        this.labelSurfaces.Visible = false;
        //
        // labelAmount
        //
        this.labelAmount.Location = new System.Drawing.Point(30, 158);
        this.labelAmount.Name = "labelAmount";
        this.labelAmount.Size = new System.Drawing.Size(75, 16);
        this.labelAmount.TabIndex = 4;
        this.labelAmount.Text = "Amount";
        this.labelAmount.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textProc
        //
        this.textProc.Location = new System.Drawing.Point(106, 61);
        this.textProc.Name = "textProc";
        this.textProc.ReadOnly = true;
        this.textProc.Size = new System.Drawing.Size(76, 20);
        this.textProc.TabIndex = 6;
        //
        // textTooth
        //
        this.textTooth.Location = new System.Drawing.Point(106, 105);
        this.textTooth.Name = "textTooth";
        this.textTooth.Size = new System.Drawing.Size(35, 20);
        this.textTooth.TabIndex = 7;
        this.textTooth.Visible = false;
        this.textTooth.Validating += new System.ComponentModel.CancelEventHandler(this.textTooth_Validating);
        //
        // textSurfaces
        //
        this.textSurfaces.Location = new System.Drawing.Point(106, 133);
        this.textSurfaces.Name = "textSurfaces";
        this.textSurfaces.Size = new System.Drawing.Size(68, 20);
        this.textSurfaces.TabIndex = 4;
        this.textSurfaces.Visible = false;
        this.textSurfaces.TextChanged += new System.EventHandler(this.textSurfaces_TextChanged);
        this.textSurfaces.Validating += new System.ComponentModel.CancelEventHandler(this.textSurfaces_Validating);
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(0, 81);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(105, 16);
        this.label6.TabIndex = 13;
        this.label6.Text = "Description";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDesc
        //
        this.textDesc.BackColor = System.Drawing.SystemColors.Control;
        this.textDesc.Location = new System.Drawing.Point(106, 81);
        this.textDesc.Name = "textDesc";
        this.textDesc.Size = new System.Drawing.Size(216, 20);
        this.textDesc.TabIndex = 16;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(429, 163);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(73, 16);
        this.label7.TabIndex = 0;
        this.label7.Text = "&Notes";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelRange
        //
        this.labelRange.Location = new System.Drawing.Point(24, 107);
        this.labelRange.Name = "labelRange";
        this.labelRange.Size = new System.Drawing.Size(82, 16);
        this.labelRange.TabIndex = 33;
        this.labelRange.Text = "Tooth Range";
        this.labelRange.TextAlign = System.Drawing.ContentAlignment.TopRight;
        this.labelRange.Visible = false;
        //
        // textRange
        //
        this.textRange.Location = new System.Drawing.Point(106, 105);
        this.textRange.Name = "textRange";
        this.textRange.Size = new System.Drawing.Size(100, 20);
        this.textRange.TabIndex = 34;
        this.textRange.Visible = false;
        //
        // groupQuadrant
        //
        this.groupQuadrant.Controls.Add(this.radioLR);
        this.groupQuadrant.Controls.Add(this.radioLL);
        this.groupQuadrant.Controls.Add(this.radioUL);
        this.groupQuadrant.Controls.Add(this.radioUR);
        this.groupQuadrant.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupQuadrant.Location = new System.Drawing.Point(104, 99);
        this.groupQuadrant.Name = "groupQuadrant";
        this.groupQuadrant.Size = new System.Drawing.Size(108, 56);
        this.groupQuadrant.TabIndex = 36;
        this.groupQuadrant.TabStop = false;
        this.groupQuadrant.Text = "Quadrant";
        this.groupQuadrant.Visible = false;
        //
        // radioLR
        //
        this.radioLR.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioLR.Location = new System.Drawing.Point(12, 36);
        this.radioLR.Name = "radioLR";
        this.radioLR.Size = new System.Drawing.Size(40, 16);
        this.radioLR.TabIndex = 3;
        this.radioLR.Text = "LR";
        this.radioLR.Click += new System.EventHandler(this.radioLR_Click);
        //
        // radioLL
        //
        this.radioLL.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioLL.Location = new System.Drawing.Point(64, 36);
        this.radioLL.Name = "radioLL";
        this.radioLL.Size = new System.Drawing.Size(40, 16);
        this.radioLL.TabIndex = 1;
        this.radioLL.Text = "LL";
        this.radioLL.Click += new System.EventHandler(this.radioLL_Click);
        //
        // radioUL
        //
        this.radioUL.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioUL.Location = new System.Drawing.Point(64, 16);
        this.radioUL.Name = "radioUL";
        this.radioUL.Size = new System.Drawing.Size(40, 16);
        this.radioUL.TabIndex = 0;
        this.radioUL.Text = "UL";
        this.radioUL.Click += new System.EventHandler(this.radioUL_Click);
        //
        // radioUR
        //
        this.radioUR.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioUR.Location = new System.Drawing.Point(12, 16);
        this.radioUR.Name = "radioUR";
        this.radioUR.Size = new System.Drawing.Size(40, 16);
        this.radioUR.TabIndex = 0;
        this.radioUR.Text = "UR";
        this.radioUR.Click += new System.EventHandler(this.radioUR_Click);
        //
        // groupArch
        //
        this.groupArch.Controls.Add(this.radioL);
        this.groupArch.Controls.Add(this.radioU);
        this.groupArch.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupArch.Location = new System.Drawing.Point(104, 99);
        this.groupArch.Name = "groupArch";
        this.groupArch.Size = new System.Drawing.Size(60, 56);
        this.groupArch.TabIndex = 3;
        this.groupArch.TabStop = false;
        this.groupArch.Text = "Arch";
        this.groupArch.Visible = false;
        //
        // radioL
        //
        this.radioL.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioL.Location = new System.Drawing.Point(12, 36);
        this.radioL.Name = "radioL";
        this.radioL.Size = new System.Drawing.Size(28, 16);
        this.radioL.TabIndex = 1;
        this.radioL.Text = "L";
        this.radioL.Click += new System.EventHandler(this.radioL_Click);
        //
        // radioU
        //
        this.radioU.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioU.Location = new System.Drawing.Point(12, 16);
        this.radioU.Name = "radioU";
        this.radioU.Size = new System.Drawing.Size(32, 16);
        this.radioU.TabIndex = 0;
        this.radioU.Text = "U";
        this.radioU.Click += new System.EventHandler(this.radioU_Click);
        //
        // panelSurfaces
        //
        this.panelSurfaces.Controls.Add(this.butD);
        this.panelSurfaces.Controls.Add(this.butBF);
        this.panelSurfaces.Controls.Add(this.butL);
        this.panelSurfaces.Controls.Add(this.butM);
        this.panelSurfaces.Controls.Add(this.butV);
        this.panelSurfaces.Controls.Add(this.butOI);
        this.panelSurfaces.Location = new System.Drawing.Point(188, 106);
        this.panelSurfaces.Name = "panelSurfaces";
        this.panelSurfaces.Size = new System.Drawing.Size(96, 66);
        this.panelSurfaces.TabIndex = 100;
        this.panelSurfaces.Visible = false;
        //
        // groupSextant
        //
        this.groupSextant.Controls.Add(this.radioS6);
        this.groupSextant.Controls.Add(this.radioS5);
        this.groupSextant.Controls.Add(this.radioS4);
        this.groupSextant.Controls.Add(this.radioS2);
        this.groupSextant.Controls.Add(this.radioS3);
        this.groupSextant.Controls.Add(this.radioS1);
        this.groupSextant.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupSextant.Location = new System.Drawing.Point(104, 99);
        this.groupSextant.Name = "groupSextant";
        this.groupSextant.Size = new System.Drawing.Size(156, 56);
        this.groupSextant.TabIndex = 5;
        this.groupSextant.TabStop = false;
        this.groupSextant.Text = "Sextant";
        this.groupSextant.Visible = false;
        //
        // radioS6
        //
        this.radioS6.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioS6.Location = new System.Drawing.Point(12, 36);
        this.radioS6.Name = "radioS6";
        this.radioS6.Size = new System.Drawing.Size(36, 16);
        this.radioS6.TabIndex = 5;
        this.radioS6.Text = "6";
        this.radioS6.Click += new System.EventHandler(this.radioS6_Click);
        //
        // radioS5
        //
        this.radioS5.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioS5.Location = new System.Drawing.Point(60, 36);
        this.radioS5.Name = "radioS5";
        this.radioS5.Size = new System.Drawing.Size(36, 16);
        this.radioS5.TabIndex = 4;
        this.radioS5.Text = "5";
        this.radioS5.Click += new System.EventHandler(this.radioS5_Click);
        //
        // radioS4
        //
        this.radioS4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioS4.Location = new System.Drawing.Point(108, 36);
        this.radioS4.Name = "radioS4";
        this.radioS4.Size = new System.Drawing.Size(36, 16);
        this.radioS4.TabIndex = 1;
        this.radioS4.Text = "4";
        this.radioS4.Click += new System.EventHandler(this.radioS4_Click);
        //
        // radioS2
        //
        this.radioS2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioS2.Location = new System.Drawing.Point(60, 16);
        this.radioS2.Name = "radioS2";
        this.radioS2.Size = new System.Drawing.Size(36, 16);
        this.radioS2.TabIndex = 2;
        this.radioS2.Text = "2";
        this.radioS2.Click += new System.EventHandler(this.radioS2_Click);
        //
        // radioS3
        //
        this.radioS3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioS3.Location = new System.Drawing.Point(108, 16);
        this.radioS3.Name = "radioS3";
        this.radioS3.Size = new System.Drawing.Size(36, 16);
        this.radioS3.TabIndex = 0;
        this.radioS3.Text = "3";
        this.radioS3.Click += new System.EventHandler(this.radioS3_Click);
        //
        // radioS1
        //
        this.radioS1.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioS1.Location = new System.Drawing.Point(12, 16);
        this.radioS1.Name = "radioS1";
        this.radioS1.Size = new System.Drawing.Size(36, 16);
        this.radioS1.TabIndex = 0;
        this.radioS1.Text = "1";
        this.radioS1.Click += new System.EventHandler(this.radioS1_Click);
        //
        // label9
        //
        this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label9.Location = new System.Drawing.Point(5, 199);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(100, 14);
        this.label9.TabIndex = 45;
        this.label9.Text = "Provider";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDx
        //
        this.labelDx.Location = new System.Drawing.Point(5, 242);
        this.labelDx.Name = "labelDx";
        this.labelDx.Size = new System.Drawing.Size(100, 14);
        this.labelDx.TabIndex = 46;
        this.labelDx.Text = "Diagnosis";
        this.labelDx.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // panel1
        //
        this.panel1.AllowDrop = true;
        this.panel1.Controls.Add(this.textTimeFinal);
        this.panel1.Controls.Add(this.labelTimeFinal);
        this.panel1.Controls.Add(this.textTimeStart);
        this.panel1.Controls.Add(this.textTimeEnd);
        this.panel1.Controls.Add(this.textDate);
        this.panel1.Controls.Add(this.butNow);
        this.panel1.Controls.Add(this.panelSurfaces);
        this.panel1.Controls.Add(this.textDateTP);
        this.panel1.Controls.Add(this.label27);
        this.panel1.Controls.Add(this.label26);
        this.panel1.Controls.Add(this.listBoxTeeth);
        this.panel1.Controls.Add(this.textDesc);
        this.panel1.Controls.Add(this.textDateEntry);
        this.panel1.Controls.Add(this.label12);
        this.panel1.Controls.Add(this.label2);
        this.panel1.Controls.Add(this.labelTooth);
        this.panel1.Controls.Add(this.labelSurfaces);
        this.panel1.Controls.Add(this.labelAmount);
        this.panel1.Controls.Add(this.textSurfaces);
        this.panel1.Controls.Add(this.label6);
        this.panel1.Controls.Add(this.groupQuadrant);
        this.panel1.Controls.Add(this.textProcFee);
        this.panel1.Controls.Add(this.textTooth);
        this.panel1.Controls.Add(this.labelStartTime);
        this.panel1.Controls.Add(this.labelEndTime);
        this.panel1.Controls.Add(this.labelRange);
        this.panel1.Controls.Add(this.labelDate);
        this.panel1.Controls.Add(this.textProc);
        this.panel1.Controls.Add(this.listBoxTeeth2);
        this.panel1.Controls.Add(this.textRange);
        this.panel1.Controls.Add(this.butChange);
        this.panel1.Controls.Add(this.groupSextant);
        this.panel1.Controls.Add(this.groupArch);
        this.panel1.Location = new System.Drawing.Point(0, 0);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(397, 177);
        this.panel1.TabIndex = 2;
        //
        // textTimeFinal
        //
        this.textTimeFinal.Location = new System.Drawing.Point(314, 61);
        this.textTimeFinal.Name = "textTimeFinal";
        this.textTimeFinal.Size = new System.Drawing.Size(55, 20);
        this.textTimeFinal.TabIndex = 104;
        this.textTimeFinal.Visible = false;
        //
        // labelTimeFinal
        //
        this.labelTimeFinal.Location = new System.Drawing.Point(259, 65);
        this.labelTimeFinal.Name = "labelTimeFinal";
        this.labelTimeFinal.Size = new System.Drawing.Size(56, 14);
        this.labelTimeFinal.TabIndex = 103;
        this.labelTimeFinal.Text = "Final";
        this.labelTimeFinal.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelTimeFinal.Visible = false;
        //
        // textTimeStart
        //
        this.textTimeStart.Location = new System.Drawing.Point(236, 40);
        this.textTimeStart.Name = "textTimeStart";
        this.textTimeStart.Size = new System.Drawing.Size(55, 20);
        this.textTimeStart.TabIndex = 102;
        this.textTimeStart.TextChanged += new System.EventHandler(this.textTimeStart_TextChanged);
        //
        // textTimeEnd
        //
        this.textTimeEnd.Location = new System.Drawing.Point(314, 40);
        this.textTimeEnd.Name = "textTimeEnd";
        this.textTimeEnd.Size = new System.Drawing.Size(55, 20);
        this.textTimeEnd.TabIndex = 102;
        this.textTimeEnd.Visible = false;
        this.textTimeEnd.TextChanged += new System.EventHandler(this.textTimeEnd_TextChanged);
        //
        // label27
        //
        this.label27.Location = new System.Drawing.Point(34, 25);
        this.label27.Name = "label27";
        this.label27.Size = new System.Drawing.Size(70, 14);
        this.label27.TabIndex = 98;
        this.label27.Text = "Date TP";
        this.label27.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label26
        //
        this.label26.Location = new System.Drawing.Point(184, 3);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(125, 18);
        this.label26.TabIndex = 97;
        this.label26.Text = "(for security)";
        this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // listBoxTeeth
        //
        this.listBoxTeeth.AllowDrop = true;
        this.listBoxTeeth.ColumnWidth = 16;
        this.listBoxTeeth.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.listBoxTeeth.Items.AddRange(new Object[]{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" });
        this.listBoxTeeth.Location = new System.Drawing.Point(106, 101);
        this.listBoxTeeth.MultiColumn = true;
        this.listBoxTeeth.Name = "listBoxTeeth";
        this.listBoxTeeth.RightToLeft = System.Windows.Forms.RightToLeft.No;
        this.listBoxTeeth.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listBoxTeeth.Size = new System.Drawing.Size(272, 17);
        this.listBoxTeeth.TabIndex = 1;
        this.listBoxTeeth.Visible = false;
        this.listBoxTeeth.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listBoxTeeth_MouseDown);
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(-19, 3);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(125, 18);
        this.label12.TabIndex = 96;
        this.label12.Text = "Date Entry";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelStartTime
        //
        this.labelStartTime.Location = new System.Drawing.Point(181, 44);
        this.labelStartTime.Name = "labelStartTime";
        this.labelStartTime.Size = new System.Drawing.Size(56, 14);
        this.labelStartTime.TabIndex = 0;
        this.labelStartTime.Text = "Time Start";
        this.labelStartTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelEndTime
        //
        this.labelEndTime.Location = new System.Drawing.Point(259, 44);
        this.labelEndTime.Name = "labelEndTime";
        this.labelEndTime.Size = new System.Drawing.Size(56, 14);
        this.labelEndTime.TabIndex = 0;
        this.labelEndTime.Text = "End";
        this.labelEndTime.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelEndTime.Visible = false;
        //
        // listBoxTeeth2
        //
        this.listBoxTeeth2.ColumnWidth = 16;
        this.listBoxTeeth2.Items.AddRange(new Object[]{ "32", "31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20", "19", "18", "17" });
        this.listBoxTeeth2.Location = new System.Drawing.Point(106, 115);
        this.listBoxTeeth2.MultiColumn = true;
        this.listBoxTeeth2.Name = "listBoxTeeth2";
        this.listBoxTeeth2.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listBoxTeeth2.Size = new System.Drawing.Size(272, 17);
        this.listBoxTeeth2.TabIndex = 2;
        this.listBoxTeeth2.Visible = false;
        this.listBoxTeeth2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listBoxTeeth2_MouseDown);
        //
        // textDrugQty
        //
        this.textDrugQty.Location = new System.Drawing.Point(93, 189);
        this.textDrugQty.Name = "textDrugQty";
        this.textDrugQty.Size = new System.Drawing.Size(59, 20);
        this.textDrugQty.TabIndex = 174;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(18, 190);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(74, 16);
        this.label10.TabIndex = 173;
        this.label10.Text = "Drug Qty";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(13, 150);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(79, 16);
        this.label5.TabIndex = 170;
        this.label5.Text = "Drug NDC";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDrugNDC
        //
        this.textDrugNDC.Location = new System.Drawing.Point(93, 148);
        this.textDrugNDC.Name = "textDrugNDC";
        this.textDrugNDC.ReadOnly = true;
        this.textDrugNDC.Size = new System.Drawing.Size(109, 20);
        this.textDrugNDC.TabIndex = 171;
        this.textDrugNDC.Text = "12345678901";
        //
        // comboDrugUnit
        //
        this.comboDrugUnit.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDrugUnit.FormattingEnabled = true;
        this.comboDrugUnit.Location = new System.Drawing.Point(93, 168);
        this.comboDrugUnit.Name = "comboDrugUnit";
        this.comboDrugUnit.Size = new System.Drawing.Size(92, 21);
        this.comboDrugUnit.TabIndex = 169;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(18, 170);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(74, 16);
        this.label1.TabIndex = 168;
        this.label1.Text = "Drug Unit";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textRevCode
        //
        this.textRevCode.Location = new System.Drawing.Point(93, 128);
        this.textRevCode.MaxLength = 48;
        this.textRevCode.Name = "textRevCode";
        this.textRevCode.Size = new System.Drawing.Size(59, 20);
        this.textRevCode.TabIndex = 112;
        //
        // label22
        //
        this.label22.Location = new System.Drawing.Point(13, 130);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(79, 17);
        this.label22.TabIndex = 111;
        this.label22.Text = "Revenue Code";
        this.label22.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUnitQty
        //
        this.textUnitQty.Location = new System.Drawing.Point(93, 87);
        this.textUnitQty.MaxLength = 15;
        this.textUnitQty.Name = "textUnitQty";
        this.textUnitQty.Size = new System.Drawing.Size(29, 20);
        this.textUnitQty.TabIndex = 110;
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(16, 89);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(76, 17);
        this.label21.TabIndex = 108;
        this.label21.Text = "Unit Quantity";
        this.label21.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCodeMod4
        //
        this.textCodeMod4.Location = new System.Drawing.Point(180, 67);
        this.textCodeMod4.MaxLength = 2;
        this.textCodeMod4.Name = "textCodeMod4";
        this.textCodeMod4.Size = new System.Drawing.Size(29, 20);
        this.textCodeMod4.TabIndex = 106;
        this.textCodeMod4.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCodeMod3
        //
        this.textCodeMod3.Location = new System.Drawing.Point(151, 67);
        this.textCodeMod3.MaxLength = 2;
        this.textCodeMod3.Name = "textCodeMod3";
        this.textCodeMod3.Size = new System.Drawing.Size(29, 20);
        this.textCodeMod3.TabIndex = 105;
        this.textCodeMod3.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCodeMod2
        //
        this.textCodeMod2.Location = new System.Drawing.Point(122, 67);
        this.textCodeMod2.MaxLength = 2;
        this.textCodeMod2.Name = "textCodeMod2";
        this.textCodeMod2.Size = new System.Drawing.Size(29, 20);
        this.textCodeMod2.TabIndex = 104;
        this.textCodeMod2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label19
        //
        this.label19.Location = new System.Drawing.Point(17, 69);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(75, 17);
        this.label19.TabIndex = 102;
        this.label19.Text = "Mods";
        this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textCodeMod1
        //
        this.textCodeMod1.Location = new System.Drawing.Point(93, 67);
        this.textCodeMod1.MaxLength = 2;
        this.textCodeMod1.Name = "textCodeMod1";
        this.textCodeMod1.Size = new System.Drawing.Size(29, 20);
        this.textCodeMod1.TabIndex = 103;
        this.textCodeMod1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // checkIsPrincDiag
        //
        this.checkIsPrincDiag.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsPrincDiag.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsPrincDiag.Location = new System.Drawing.Point(30, 48);
        this.checkIsPrincDiag.Name = "checkIsPrincDiag";
        this.checkIsPrincDiag.Size = new System.Drawing.Size(76, 18);
        this.checkIsPrincDiag.TabIndex = 101;
        this.checkIsPrincDiag.Text = "Princ Diag";
        this.checkIsPrincDiag.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(13, 28);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(79, 16);
        this.label11.TabIndex = 99;
        this.label11.Text = "ICD-9 Code";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDiagnosticCode
        //
        this.textDiagnosticCode.Location = new System.Drawing.Point(93, 26);
        this.textDiagnosticCode.Name = "textDiagnosticCode";
        this.textDiagnosticCode.Size = new System.Drawing.Size(76, 20);
        this.textDiagnosticCode.TabIndex = 100;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(10, 9);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(82, 16);
        this.label8.TabIndex = 97;
        this.label8.Text = "Medical Code";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textMedicalCode
        //
        this.textMedicalCode.Location = new System.Drawing.Point(93, 6);
        this.textMedicalCode.Name = "textMedicalCode";
        this.textMedicalCode.Size = new System.Drawing.Size(76, 20);
        this.textMedicalCode.TabIndex = 98;
        //
        // labelClaim
        //
        this.labelClaim.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.labelClaim.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelClaim.Location = new System.Drawing.Point(111, 652);
        this.labelClaim.Name = "labelClaim";
        this.labelClaim.Size = new System.Drawing.Size(480, 44);
        this.labelClaim.TabIndex = 50;
        this.labelClaim.Text = "This procedure is attached to a claim, so certain fields should not be edited.  Y" + "ou should reprint the claim if any significant changes are made.";
        this.labelClaim.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        this.labelClaim.Visible = false;
        //
        // comboPlaceService
        //
        this.comboPlaceService.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPlaceService.Location = new System.Drawing.Point(119, 98);
        this.comboPlaceService.MaxDropDownItems = 30;
        this.comboPlaceService.Name = "comboPlaceService";
        this.comboPlaceService.Size = new System.Drawing.Size(177, 21);
        this.comboPlaceService.TabIndex = 6;
        //
        // labelPlaceService
        //
        this.labelPlaceService.Location = new System.Drawing.Point(4, 101);
        this.labelPlaceService.Name = "labelPlaceService";
        this.labelPlaceService.Size = new System.Drawing.Size(114, 16);
        this.labelPlaceService.TabIndex = 53;
        this.labelPlaceService.Text = "Place of Service";
        this.labelPlaceService.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelPriority
        //
        this.labelPriority.Location = new System.Drawing.Point(32, 263);
        this.labelPriority.Name = "labelPriority";
        this.labelPriority.Size = new System.Drawing.Size(72, 16);
        this.labelPriority.TabIndex = 56;
        this.labelPriority.Text = "Priority";
        this.labelPriority.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelSetComplete
        //
        this.labelSetComplete.Location = new System.Drawing.Point(724, 23);
        this.labelSetComplete.Name = "labelSetComplete";
        this.labelSetComplete.Size = new System.Drawing.Size(157, 16);
        this.labelSetComplete.TabIndex = 58;
        this.labelSetComplete.Text = "changes date and adds note.";
        //
        // checkNoBillIns
        //
        this.checkNoBillIns.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkNoBillIns.Location = new System.Drawing.Point(142, 12);
        this.checkNoBillIns.Name = "checkNoBillIns";
        this.checkNoBillIns.Size = new System.Drawing.Size(152, 18);
        this.checkNoBillIns.TabIndex = 9;
        this.checkNoBillIns.Text = "Do Not Bill to Ins";
        this.checkNoBillIns.ThreeState = true;
        this.checkNoBillIns.Click += new System.EventHandler(this.checkNoBillIns_Click);
        //
        // labelIncomplete
        //
        this.labelIncomplete.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelIncomplete.ForeColor = System.Drawing.Color.DarkRed;
        this.labelIncomplete.Location = new System.Drawing.Point(724, 138);
        this.labelIncomplete.Name = "labelIncomplete";
        this.labelIncomplete.Size = new System.Drawing.Size(123, 18);
        this.labelIncomplete.TabIndex = 73;
        this.labelIncomplete.Text = "Incomplete";
        this.labelIncomplete.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(106, 217);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(177, 21);
        this.comboClinic.TabIndex = 74;
        this.comboClinic.SelectionChangeCommitted += new System.EventHandler(this.comboClinic_SelectionChangeCommitted);
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(-10, 219);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(114, 16);
        this.labelClinic.TabIndex = 75;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(403, 20);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(99, 16);
        this.label14.TabIndex = 77;
        this.label14.Text = "Procedure Status";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(389, 327);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(110, 41);
        this.label15.TabIndex = 79;
        this.label15.Text = "Signature /\r\nInitials";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(429, 138);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(73, 16);
        this.label16.TabIndex = 80;
        this.label16.Text = "User";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboPriority
        //
        this.comboPriority.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPriority.Location = new System.Drawing.Point(106, 259);
        this.comboPriority.MaxDropDownItems = 30;
        this.comboPriority.Name = "comboPriority";
        this.comboPriority.Size = new System.Drawing.Size(177, 21);
        this.comboPriority.TabIndex = 98;
        //
        // comboDx
        //
        this.comboDx.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDx.Location = new System.Drawing.Point(106, 238);
        this.comboDx.MaxDropDownItems = 30;
        this.comboDx.Name = "comboDx";
        this.comboDx.Size = new System.Drawing.Size(177, 21);
        this.comboDx.TabIndex = 99;
        //
        // comboProvNum
        //
        this.comboProvNum.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvNum.Location = new System.Drawing.Point(106, 195);
        this.comboProvNum.MaxDropDownItems = 30;
        this.comboProvNum.Name = "comboProvNum";
        this.comboProvNum.Size = new System.Drawing.Size(158, 21);
        this.comboProvNum.TabIndex = 100;
        this.comboProvNum.SelectionChangeCommitted += new System.EventHandler(this.comboProvNum_SelectionChangeCommitted);
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(504, 137);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(116, 20);
        this.textUser.TabIndex = 101;
        //
        // comboBillingTypeTwo
        //
        this.comboBillingTypeTwo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboBillingTypeTwo.FormattingEnabled = true;
        this.comboBillingTypeTwo.Location = new System.Drawing.Point(119, 33);
        this.comboBillingTypeTwo.MaxDropDownItems = 30;
        this.comboBillingTypeTwo.Name = "comboBillingTypeTwo";
        this.comboBillingTypeTwo.Size = new System.Drawing.Size(198, 21);
        this.comboBillingTypeTwo.TabIndex = 102;
        //
        // labelBillingTypeTwo
        //
        this.labelBillingTypeTwo.Location = new System.Drawing.Point(15, 35);
        this.labelBillingTypeTwo.Name = "labelBillingTypeTwo";
        this.labelBillingTypeTwo.Size = new System.Drawing.Size(102, 16);
        this.labelBillingTypeTwo.TabIndex = 103;
        this.labelBillingTypeTwo.Text = "Billing Type 2";
        this.labelBillingTypeTwo.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboBillingTypeOne
        //
        this.comboBillingTypeOne.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboBillingTypeOne.FormattingEnabled = true;
        this.comboBillingTypeOne.Location = new System.Drawing.Point(119, 12);
        this.comboBillingTypeOne.MaxDropDownItems = 30;
        this.comboBillingTypeOne.Name = "comboBillingTypeOne";
        this.comboBillingTypeOne.Size = new System.Drawing.Size(198, 21);
        this.comboBillingTypeOne.TabIndex = 104;
        //
        // labelBillingTypeOne
        //
        this.labelBillingTypeOne.Location = new System.Drawing.Point(13, 14);
        this.labelBillingTypeOne.Name = "labelBillingTypeOne";
        this.labelBillingTypeOne.Size = new System.Drawing.Size(104, 16);
        this.labelBillingTypeOne.TabIndex = 105;
        this.labelBillingTypeOne.Text = "Billing Type 1";
        this.labelBillingTypeOne.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelInvalidSig
        //
        this.labelInvalidSig.BackColor = System.Drawing.SystemColors.Window;
        this.labelInvalidSig.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInvalidSig.Location = new System.Drawing.Point(589, 337);
        this.labelInvalidSig.Name = "labelInvalidSig";
        this.labelInvalidSig.Size = new System.Drawing.Size(196, 59);
        this.labelInvalidSig.TabIndex = 109;
        this.labelInvalidSig.Text = "Invalid Signature -  Note or user has changed since it was signed.";
        this.labelInvalidSig.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // textSite
        //
        this.textSite.AcceptsReturn = true;
        this.textSite.Location = new System.Drawing.Point(119, 77);
        this.textSite.Name = "textSite";
        this.textSite.ReadOnly = true;
        this.textSite.Size = new System.Drawing.Size(153, 20);
        this.textSite.TabIndex = 111;
        //
        // labelSite
        //
        this.labelSite.Location = new System.Drawing.Point(4, 78);
        this.labelSite.Name = "labelSite";
        this.labelSite.Size = new System.Drawing.Size(114, 17);
        this.labelSite.TabIndex = 110;
        this.labelSite.Text = "Site";
        this.labelSite.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkHideGraphics
        //
        this.checkHideGraphics.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkHideGraphics.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkHideGraphics.Location = new System.Drawing.Point(5, 178);
        this.checkHideGraphics.Name = "checkHideGraphics";
        this.checkHideGraphics.Size = new System.Drawing.Size(114, 18);
        this.checkHideGraphics.TabIndex = 162;
        this.checkHideGraphics.Text = "HideGraphics";
        this.checkHideGraphics.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(2, 14);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(90, 41);
        this.label3.TabIndex = 0;
        this.label3.Text = "Crown, Bridge, Denture, or RPD";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 61);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(84, 16);
        this.label4.TabIndex = 4;
        this.label4.Text = "Original Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listProsth
        //
        this.listProsth.Location = new System.Drawing.Point(91, 14);
        this.listProsth.Name = "listProsth";
        this.listProsth.Size = new System.Drawing.Size(163, 43);
        this.listProsth.TabIndex = 0;
        //
        // groupProsth
        //
        this.groupProsth.Controls.Add(this.listProsth);
        this.groupProsth.Controls.Add(this.textDateOriginalProsth);
        this.groupProsth.Controls.Add(this.label4);
        this.groupProsth.Controls.Add(this.label3);
        this.groupProsth.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupProsth.Location = new System.Drawing.Point(15, 283);
        this.groupProsth.Name = "groupProsth";
        this.groupProsth.Size = new System.Drawing.Size(269, 80);
        this.groupProsth.TabIndex = 7;
        this.groupProsth.TabStop = false;
        this.groupProsth.Text = "Prosthesis Replacement";
        //
        // checkTypeCodeA
        //
        this.checkTypeCodeA.Location = new System.Drawing.Point(10, 16);
        this.checkTypeCodeA.Name = "checkTypeCodeA";
        this.checkTypeCodeA.Size = new System.Drawing.Size(268, 17);
        this.checkTypeCodeA.TabIndex = 0;
        this.checkTypeCodeA.Text = "Not initial placement.  Repair of a prior service.";
        this.checkTypeCodeA.UseVisualStyleBackColor = true;
        //
        // checkTypeCodeB
        //
        this.checkTypeCodeB.Location = new System.Drawing.Point(10, 33);
        this.checkTypeCodeB.Name = "checkTypeCodeB";
        this.checkTypeCodeB.Size = new System.Drawing.Size(239, 17);
        this.checkTypeCodeB.TabIndex = 1;
        this.checkTypeCodeB.Text = "Temporary placement or service.";
        this.checkTypeCodeB.UseVisualStyleBackColor = true;
        //
        // checkTypeCodeC
        //
        this.checkTypeCodeC.Location = new System.Drawing.Point(10, 50);
        this.checkTypeCodeC.Name = "checkTypeCodeC";
        this.checkTypeCodeC.Size = new System.Drawing.Size(239, 17);
        this.checkTypeCodeC.TabIndex = 2;
        this.checkTypeCodeC.Text = "Correction of TMJ";
        this.checkTypeCodeC.UseVisualStyleBackColor = true;
        //
        // checkTypeCodeE
        //
        this.checkTypeCodeE.Location = new System.Drawing.Point(10, 67);
        this.checkTypeCodeE.Name = "checkTypeCodeE";
        this.checkTypeCodeE.Size = new System.Drawing.Size(268, 17);
        this.checkTypeCodeE.TabIndex = 3;
        this.checkTypeCodeE.Text = "Implant, or in conjunction with implants";
        this.checkTypeCodeE.UseVisualStyleBackColor = true;
        //
        // checkTypeCodeL
        //
        this.checkTypeCodeL.Location = new System.Drawing.Point(10, 84);
        this.checkTypeCodeL.Name = "checkTypeCodeL";
        this.checkTypeCodeL.Size = new System.Drawing.Size(113, 17);
        this.checkTypeCodeL.TabIndex = 4;
        this.checkTypeCodeL.Text = "Appliance lost";
        this.checkTypeCodeL.UseVisualStyleBackColor = true;
        //
        // checkTypeCodeX
        //
        this.checkTypeCodeX.Location = new System.Drawing.Point(10, 118);
        this.checkTypeCodeX.Name = "checkTypeCodeX";
        this.checkTypeCodeX.Size = new System.Drawing.Size(239, 17);
        this.checkTypeCodeX.TabIndex = 5;
        this.checkTypeCodeX.Text = "None of the above are applicable";
        this.checkTypeCodeX.UseVisualStyleBackColor = true;
        //
        // checkTypeCodeS
        //
        this.checkTypeCodeS.Location = new System.Drawing.Point(10, 101);
        this.checkTypeCodeS.Name = "checkTypeCodeS";
        this.checkTypeCodeS.Size = new System.Drawing.Size(113, 17);
        this.checkTypeCodeS.TabIndex = 6;
        this.checkTypeCodeS.Text = "Appliance stolen";
        this.checkTypeCodeS.UseVisualStyleBackColor = true;
        //
        // groupCanadianProcTypeCode
        //
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeS);
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeX);
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeL);
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeE);
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeC);
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeB);
        this.groupCanadianProcTypeCode.Controls.Add(this.checkTypeCodeA);
        this.groupCanadianProcTypeCode.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupCanadianProcTypeCode.Location = new System.Drawing.Point(18, 16);
        this.groupCanadianProcTypeCode.Name = "groupCanadianProcTypeCode";
        this.groupCanadianProcTypeCode.Size = new System.Drawing.Size(316, 142);
        this.groupCanadianProcTypeCode.TabIndex = 163;
        this.groupCanadianProcTypeCode.TabStop = false;
        this.groupCanadianProcTypeCode.Text = "Procedure Type Code";
        //
        // labelDPCpost
        //
        this.labelDPCpost.Location = new System.Drawing.Point(9, 28);
        this.labelDPCpost.Name = "labelDPCpost";
        this.labelDPCpost.Size = new System.Drawing.Size(100, 16);
        this.labelDPCpost.TabIndex = 107;
        this.labelDPCpost.Text = "DPC Post Visit";
        this.labelDPCpost.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboDPCpost
        //
        this.comboDPCpost.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDPCpost.DropDownWidth = 177;
        this.comboDPCpost.FormattingEnabled = true;
        this.comboDPCpost.Location = new System.Drawing.Point(111, 27);
        this.comboDPCpost.MaxDropDownItems = 30;
        this.comboDPCpost.Name = "comboDPCpost";
        this.comboDPCpost.Size = new System.Drawing.Size(177, 21);
        this.comboDPCpost.TabIndex = 106;
        //
        // labelScheduleBy
        //
        this.labelScheduleBy.Location = new System.Drawing.Point(193, 70);
        this.labelScheduleBy.Name = "labelScheduleBy";
        this.labelScheduleBy.Size = new System.Drawing.Size(148, 16);
        this.labelScheduleBy.TabIndex = 105;
        this.labelScheduleBy.Text = "No Schedule by Date";
        this.labelScheduleBy.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.labelScheduleBy.Visible = false;
        //
        // checkIsRepair
        //
        this.checkIsRepair.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsRepair.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsRepair.Location = new System.Drawing.Point(10, 145);
        this.checkIsRepair.Name = "checkIsRepair";
        this.checkIsRepair.Size = new System.Drawing.Size(114, 18);
        this.checkIsRepair.TabIndex = 104;
        this.checkIsRepair.Text = "Repair";
        this.checkIsRepair.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsEffComm
        //
        this.checkIsEffComm.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsEffComm.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsEffComm.Location = new System.Drawing.Point(10, 128);
        this.checkIsEffComm.Name = "checkIsEffComm";
        this.checkIsEffComm.Size = new System.Drawing.Size(114, 18);
        this.checkIsEffComm.TabIndex = 103;
        this.checkIsEffComm.Text = "Effective Comm";
        this.checkIsEffComm.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIsOnCall
        //
        this.checkIsOnCall.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.checkIsOnCall.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIsOnCall.Location = new System.Drawing.Point(10, 111);
        this.checkIsOnCall.Name = "checkIsOnCall";
        this.checkIsOnCall.Size = new System.Drawing.Size(114, 18);
        this.checkIsOnCall.TabIndex = 102;
        this.checkIsOnCall.Text = "On Call";
        this.checkIsOnCall.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboDPC
        //
        this.comboDPC.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDPC.DropDownWidth = 177;
        this.comboDPC.FormattingEnabled = true;
        this.comboDPC.Location = new System.Drawing.Point(111, 6);
        this.comboDPC.MaxDropDownItems = 30;
        this.comboDPC.Name = "comboDPC";
        this.comboDPC.Size = new System.Drawing.Size(177, 21);
        this.comboDPC.TabIndex = 8;
        this.comboDPC.SelectionChangeCommitted += new System.EventHandler(this.comboDPC_SelectionChangeCommitted);
        //
        // comboStatus
        //
        this.comboStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboStatus.DropDownWidth = 230;
        this.comboStatus.FormattingEnabled = true;
        this.comboStatus.Location = new System.Drawing.Point(111, 48);
        this.comboStatus.MaxDropDownItems = 30;
        this.comboStatus.Name = "comboStatus";
        this.comboStatus.Size = new System.Drawing.Size(230, 21);
        this.comboStatus.TabIndex = 7;
        this.comboStatus.SelectionChangeCommitted += new System.EventHandler(this.comboStatus_SelectionChangeCommitted);
        //
        // labelStatus
        //
        this.labelStatus.Location = new System.Drawing.Point(9, 49);
        this.labelStatus.Name = "labelStatus";
        this.labelStatus.Size = new System.Drawing.Size(100, 16);
        this.labelStatus.TabIndex = 3;
        this.labelStatus.Text = "Status";
        this.labelStatus.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDateStop
        //
        this.labelDateStop.Location = new System.Drawing.Point(11, 90);
        this.labelDateStop.Name = "labelDateStop";
        this.labelDateStop.Size = new System.Drawing.Size(100, 16);
        this.labelDateStop.TabIndex = 2;
        this.labelDateStop.Text = "Date Stop Clock";
        this.labelDateStop.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDateSched
        //
        this.labelDateSched.Location = new System.Drawing.Point(10, 70);
        this.labelDateSched.Name = "labelDateSched";
        this.labelDateSched.Size = new System.Drawing.Size(100, 16);
        this.labelDateSched.TabIndex = 1;
        this.labelDateSched.Text = "Scheduled By";
        this.labelDateSched.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelDPC
        //
        this.labelDPC.Location = new System.Drawing.Point(9, 7);
        this.labelDPC.Name = "labelDPC";
        this.labelDPC.Size = new System.Drawing.Size(100, 16);
        this.labelDPC.TabIndex = 0;
        this.labelDPC.Text = "DPC";
        this.labelDPC.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboPrognosis
        //
        this.comboPrognosis.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPrognosis.Location = new System.Drawing.Point(119, 55);
        this.comboPrognosis.MaxDropDownItems = 30;
        this.comboPrognosis.Name = "comboPrognosis";
        this.comboPrognosis.Size = new System.Drawing.Size(153, 21);
        this.comboPrognosis.TabIndex = 165;
        //
        // labelPrognosis
        //
        this.labelPrognosis.Location = new System.Drawing.Point(3, 57);
        this.labelPrognosis.Name = "labelPrognosis";
        this.labelPrognosis.Size = new System.Drawing.Size(114, 17);
        this.labelPrognosis.TabIndex = 166;
        this.labelPrognosis.Text = "Prognosis";
        this.labelPrognosis.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboProcStatus
        //
        this.comboProcStatus.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProcStatus.FormattingEnabled = true;
        this.comboProcStatus.Location = new System.Drawing.Point(504, 19);
        this.comboProcStatus.Name = "comboProcStatus";
        this.comboProcStatus.Size = new System.Drawing.Size(133, 21);
        this.comboProcStatus.TabIndex = 167;
        this.comboProcStatus.SelectionChangeCommitted += new System.EventHandler(this.comboProcStatus_SelectionChangeCommitted);
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(418, 80);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(84, 16);
        this.label13.TabIndex = 168;
        this.label13.Text = "Referral";
        this.label13.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textReferral
        //
        this.textReferral.BackColor = System.Drawing.SystemColors.Control;
        this.textReferral.ForeColor = System.Drawing.Color.DarkRed;
        this.textReferral.Location = new System.Drawing.Point(504, 77);
        this.textReferral.Name = "textReferral";
        this.textReferral.Size = new System.Drawing.Size(198, 20);
        this.textReferral.TabIndex = 169;
        this.textReferral.Text = "test";
        //
        // labelClaimNote
        //
        this.labelClaimNote.Location = new System.Drawing.Point(0, 364);
        this.labelClaimNote.Name = "labelClaimNote";
        this.labelClaimNote.Size = new System.Drawing.Size(104, 41);
        this.labelClaimNote.TabIndex = 174;
        this.labelClaimNote.Text = "E-claim Note (keep it very short)";
        this.labelClaimNote.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // tabControl
        //
        this.tabControl.Controls.Add(this.tabPageFinancial);
        this.tabControl.Controls.Add(this.tabPageMedical);
        this.tabControl.Controls.Add(this.tabPageMisc);
        this.tabControl.Controls.Add(this.tabPageCanada);
        this.tabControl.Controls.Add(this.tabPageOrion);
        this.tabControl.Location = new System.Drawing.Point(1, 424);
        this.tabControl.Name = "tabControl";
        this.tabControl.SelectedIndex = 0;
        this.tabControl.Size = new System.Drawing.Size(962, 244);
        this.tabControl.TabIndex = 175;
        //
        // tabPageFinancial
        //
        this.tabPageFinancial.Controls.Add(this.butAddEstimate);
        this.tabPageFinancial.Controls.Add(this.checkNoBillIns);
        this.tabPageFinancial.Controls.Add(this.tbAdj);
        this.tabPageFinancial.Controls.Add(this.tbPay);
        this.tabPageFinancial.Controls.Add(this.butAddAdjust);
        this.tabPageFinancial.Controls.Add(this.gridIns);
        this.tabPageFinancial.Location = new System.Drawing.Point(4, 22);
        this.tabPageFinancial.Name = "tabPageFinancial";
        this.tabPageFinancial.Padding = new System.Windows.Forms.Padding(3);
        this.tabPageFinancial.Size = new System.Drawing.Size(954, 218);
        this.tabPageFinancial.TabIndex = 0;
        this.tabPageFinancial.Text = "Financial";
        this.tabPageFinancial.UseVisualStyleBackColor = true;
        //
        // tabPageMedical
        //
        this.tabPageMedical.Controls.Add(this.butNoneSnomedBodySite);
        this.tabPageMedical.Controls.Add(this.butSnomedBodySiteSelect);
        this.tabPageMedical.Controls.Add(this.label20);
        this.tabPageMedical.Controls.Add(this.textSnomedBodySite);
        this.tabPageMedical.Controls.Add(this.label17);
        this.tabPageMedical.Controls.Add(this.comboUnitType);
        this.tabPageMedical.Controls.Add(this.textDrugQty);
        this.tabPageMedical.Controls.Add(this.label10);
        this.tabPageMedical.Controls.Add(this.label8);
        this.tabPageMedical.Controls.Add(this.label5);
        this.tabPageMedical.Controls.Add(this.textMedicalCode);
        this.tabPageMedical.Controls.Add(this.textDrugNDC);
        this.tabPageMedical.Controls.Add(this.textDiagnosticCode);
        this.tabPageMedical.Controls.Add(this.comboDrugUnit);
        this.tabPageMedical.Controls.Add(this.label11);
        this.tabPageMedical.Controls.Add(this.label1);
        this.tabPageMedical.Controls.Add(this.checkIsPrincDiag);
        this.tabPageMedical.Controls.Add(this.textRevCode);
        this.tabPageMedical.Controls.Add(this.textCodeMod1);
        this.tabPageMedical.Controls.Add(this.label22);
        this.tabPageMedical.Controls.Add(this.label19);
        this.tabPageMedical.Controls.Add(this.textUnitQty);
        this.tabPageMedical.Controls.Add(this.textCodeMod2);
        this.tabPageMedical.Controls.Add(this.label21);
        this.tabPageMedical.Controls.Add(this.textCodeMod3);
        this.tabPageMedical.Controls.Add(this.textCodeMod4);
        this.tabPageMedical.Location = new System.Drawing.Point(4, 22);
        this.tabPageMedical.Name = "tabPageMedical";
        this.tabPageMedical.Padding = new System.Windows.Forms.Padding(3);
        this.tabPageMedical.Size = new System.Drawing.Size(954, 218);
        this.tabPageMedical.TabIndex = 3;
        this.tabPageMedical.Text = "Medical";
        this.tabPageMedical.UseVisualStyleBackColor = true;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(16, 109);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(76, 17);
        this.label17.TabIndex = 176;
        this.label17.Text = "Unit Type";
        this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // comboUnitType
        //
        this.comboUnitType.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboUnitType.FormattingEnabled = true;
        this.comboUnitType.Location = new System.Drawing.Point(93, 107);
        this.comboUnitType.Name = "comboUnitType";
        this.comboUnitType.Size = new System.Drawing.Size(117, 21);
        this.comboUnitType.TabIndex = 175;
        //
        // tabPageMisc
        //
        this.tabPageMisc.Controls.Add(this.textBillingNote);
        this.tabPageMisc.Controls.Add(this.label18);
        this.tabPageMisc.Controls.Add(this.comboBillingTypeOne);
        this.tabPageMisc.Controls.Add(this.labelBillingTypeTwo);
        this.tabPageMisc.Controls.Add(this.comboBillingTypeTwo);
        this.tabPageMisc.Controls.Add(this.labelBillingTypeOne);
        this.tabPageMisc.Controls.Add(this.comboPrognosis);
        this.tabPageMisc.Controls.Add(this.labelPrognosis);
        this.tabPageMisc.Controls.Add(this.textSite);
        this.tabPageMisc.Controls.Add(this.labelSite);
        this.tabPageMisc.Controls.Add(this.butPickSite);
        this.tabPageMisc.Controls.Add(this.comboPlaceService);
        this.tabPageMisc.Controls.Add(this.labelPlaceService);
        this.tabPageMisc.Location = new System.Drawing.Point(4, 22);
        this.tabPageMisc.Name = "tabPageMisc";
        this.tabPageMisc.Size = new System.Drawing.Size(954, 218);
        this.tabPageMisc.TabIndex = 4;
        this.tabPageMisc.Text = "Misc";
        this.tabPageMisc.UseVisualStyleBackColor = true;
        //
        // textBillingNote
        //
        this.textBillingNote.Location = new System.Drawing.Point(119, 120);
        this.textBillingNote.Multiline = true;
        this.textBillingNote.Name = "textBillingNote";
        this.textBillingNote.Size = new System.Drawing.Size(259, 83);
        this.textBillingNote.TabIndex = 168;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(6, 122);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(111, 14);
        this.label18.TabIndex = 167;
        this.label18.Text = "Billing Note";
        this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // tabPageCanada
        //
        this.tabPageCanada.Controls.Add(this.labelCanadaLabFee2);
        this.tabPageCanada.Controls.Add(this.labelCanadaLabFee1);
        this.tabPageCanada.Controls.Add(this.groupCanadianProcTypeCode);
        this.tabPageCanada.Controls.Add(this.textCanadaLabFee2);
        this.tabPageCanada.Controls.Add(this.textCanadaLabFee1);
        this.tabPageCanada.Location = new System.Drawing.Point(4, 22);
        this.tabPageCanada.Name = "tabPageCanada";
        this.tabPageCanada.Padding = new System.Windows.Forms.Padding(3);
        this.tabPageCanada.Size = new System.Drawing.Size(954, 218);
        this.tabPageCanada.TabIndex = 1;
        this.tabPageCanada.Text = "Canada";
        this.tabPageCanada.UseVisualStyleBackColor = true;
        //
        // labelCanadaLabFee2
        //
        this.labelCanadaLabFee2.Location = new System.Drawing.Point(340, 37);
        this.labelCanadaLabFee2.Name = "labelCanadaLabFee2";
        this.labelCanadaLabFee2.Size = new System.Drawing.Size(75, 20);
        this.labelCanadaLabFee2.TabIndex = 167;
        this.labelCanadaLabFee2.Text = "Lab Fee 2";
        this.labelCanadaLabFee2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // labelCanadaLabFee1
        //
        this.labelCanadaLabFee1.Location = new System.Drawing.Point(340, 16);
        this.labelCanadaLabFee1.Name = "labelCanadaLabFee1";
        this.labelCanadaLabFee1.Size = new System.Drawing.Size(75, 20);
        this.labelCanadaLabFee1.TabIndex = 166;
        this.labelCanadaLabFee1.Text = "Lab Fee 1";
        this.labelCanadaLabFee1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // tabPageOrion
        //
        this.tabPageOrion.Controls.Add(this.labelDPCpost);
        this.tabPageOrion.Controls.Add(this.comboDPCpost);
        this.tabPageOrion.Controls.Add(this.labelDPC);
        this.tabPageOrion.Controls.Add(this.labelScheduleBy);
        this.tabPageOrion.Controls.Add(this.labelDateSched);
        this.tabPageOrion.Controls.Add(this.checkIsRepair);
        this.tabPageOrion.Controls.Add(this.labelDateStop);
        this.tabPageOrion.Controls.Add(this.checkIsEffComm);
        this.tabPageOrion.Controls.Add(this.labelStatus);
        this.tabPageOrion.Controls.Add(this.checkIsOnCall);
        this.tabPageOrion.Controls.Add(this.comboStatus);
        this.tabPageOrion.Controls.Add(this.comboDPC);
        this.tabPageOrion.Controls.Add(this.textDateStop);
        this.tabPageOrion.Controls.Add(this.textDateScheduled);
        this.tabPageOrion.Location = new System.Drawing.Point(4, 22);
        this.tabPageOrion.Name = "tabPageOrion";
        this.tabPageOrion.Padding = new System.Windows.Forms.Padding(3);
        this.tabPageOrion.Size = new System.Drawing.Size(954, 218);
        this.tabPageOrion.TabIndex = 2;
        this.tabPageOrion.Text = "Orion";
        this.tabPageOrion.UseVisualStyleBackColor = true;
        //
        // labelLocked
        //
        this.labelLocked.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelLocked.ForeColor = System.Drawing.Color.DarkRed;
        this.labelLocked.Location = new System.Drawing.Point(834, 115);
        this.labelLocked.Name = "labelLocked";
        this.labelLocked.Size = new System.Drawing.Size(123, 18);
        this.labelLocked.TabIndex = 176;
        this.labelLocked.Text = "Locked";
        this.labelLocked.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        this.labelLocked.Visible = false;
        //
        // gridIns
        //
        this.gridIns.setHScrollVisible(false);
        this.gridIns.Location = new System.Drawing.Point(3, 32);
        this.gridIns.Name = "gridIns";
        this.gridIns.setScrollValue(0);
        this.gridIns.Size = new System.Drawing.Size(949, 102);
        this.gridIns.TabIndex = 113;
        this.gridIns.setTitle("Insurance Estimates and Payments");
        this.gridIns.setTranslationName("TableProcIns");
        this.gridIns.setWrapText(false);
        this.gridIns.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridIns.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridIns_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // label20
        //
        this.label20.Location = new System.Drawing.Point(186, 7);
        this.label20.Name = "label20";
        this.label20.Size = new System.Drawing.Size(172, 20);
        this.label20.TabIndex = 178;
        this.label20.Text = "SNOMED CT Body Site";
        this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textSnomedBodySite
        //
        this.textSnomedBodySite.Location = new System.Drawing.Point(361, 7);
        this.textSnomedBodySite.Name = "textSnomedBodySite";
        this.textSnomedBodySite.ReadOnly = true;
        this.textSnomedBodySite.Size = new System.Drawing.Size(272, 20);
        this.textSnomedBodySite.TabIndex = 177;
        //
        // butSearch
        //
        this.butSearch.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSearch.setAutosize(true);
        this.butSearch.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSearch.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSearch.setCornerRadius(4F);
        this.butSearch.Location = new System.Drawing.Point(443, 232);
        this.butSearch.Name = "butSearch";
        this.butSearch.Size = new System.Drawing.Size(59, 24);
        this.butSearch.TabIndex = 180;
        this.butSearch.Text = "Search";
        this.butSearch.Click += new System.EventHandler(this.butSearch_Click);
        //
        // butLock
        //
        this.butLock.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLock.setAutosize(true);
        this.butLock.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLock.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLock.setCornerRadius(4F);
        this.butLock.Location = new System.Drawing.Point(874, 91);
        this.butLock.Name = "butLock";
        this.butLock.Size = new System.Drawing.Size(80, 22);
        this.butLock.TabIndex = 178;
        this.butLock.Text = "Lock";
        this.butLock.Click += new System.EventHandler(this.butLock_Click);
        //
        // butInvalidate
        //
        this.butInvalidate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butInvalidate.setAutosize(true);
        this.butInvalidate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butInvalidate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butInvalidate.setCornerRadius(4F);
        this.butInvalidate.Location = new System.Drawing.Point(879, 77);
        this.butInvalidate.Name = "butInvalidate";
        this.butInvalidate.Size = new System.Drawing.Size(80, 22);
        this.butInvalidate.TabIndex = 179;
        this.butInvalidate.Text = "Invalidate";
        this.butInvalidate.Visible = false;
        this.butInvalidate.Click += new System.EventHandler(this.butInvalidate_Click);
        //
        // butAppend
        //
        this.butAppend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAppend.setAutosize(true);
        this.butAppend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAppend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAppend.setCornerRadius(4F);
        this.butAppend.Location = new System.Drawing.Point(874, 136);
        this.butAppend.Name = "butAppend";
        this.butAppend.Size = new System.Drawing.Size(80, 22);
        this.butAppend.TabIndex = 177;
        this.butAppend.Text = "Append";
        this.butAppend.Visible = false;
        this.butAppend.Click += new System.EventHandler(this.butAppend_Click);
        //
        // butAddEstimate
        //
        this.butAddEstimate.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddEstimate.setAutosize(true);
        this.butAddEstimate.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddEstimate.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddEstimate.setCornerRadius(4F);
        this.butAddEstimate.Image = Resources.getAdd();
        this.butAddEstimate.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddEstimate.Location = new System.Drawing.Point(3, 6);
        this.butAddEstimate.Name = "butAddEstimate";
        this.butAddEstimate.Size = new System.Drawing.Size(111, 24);
        this.butAddEstimate.TabIndex = 60;
        this.butAddEstimate.Text = "Add Estimate";
        this.butAddEstimate.Click += new System.EventHandler(this.butAddEstimate_Click);
        //
        // tbAdj
        //
        this.tbAdj.BackColor = System.Drawing.SystemColors.Window;
        this.tbAdj.Location = new System.Drawing.Point(458, 137);
        this.tbAdj.Name = "tbAdj";
        this.tbAdj.setScrollValue(33);
        this.tbAdj.setSelectedIndices(new int[0]);
        this.tbAdj.setSelectionMode(System.Windows.Forms.SelectionMode.One);
        this.tbAdj.Size = new System.Drawing.Size(494, 72);
        this.tbAdj.TabIndex = 70;
        this.tbAdj.CellDoubleClicked = __MultiCellEventHandler.combine(this.tbAdj.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                this.tbAdj_CellDoubleClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // tbPay
        //
        this.tbPay.BackColor = System.Drawing.SystemColors.Window;
        this.tbPay.Location = new System.Drawing.Point(2, 137);
        this.tbPay.Name = "tbPay";
        this.tbPay.setScrollValue(33);
        this.tbPay.setSelectedIndices(new int[0]);
        this.tbPay.setSelectionMode(System.Windows.Forms.SelectionMode.One);
        this.tbPay.Size = new System.Drawing.Size(449, 72);
        this.tbPay.TabIndex = 71;
        this.tbPay.CellDoubleClicked = __MultiCellEventHandler.combine(this.tbPay.CellDoubleClicked,new OpenDental.ContrTable.CellEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.CellEventArgs e) throws Exception {
                this.tbPay_CellDoubleClicked(sender, e);
            }

            public List<OpenDental.ContrTable.CellEventHandler> getInvocationList() throws Exception {
                List<OpenDental.ContrTable.CellEventHandler> ret = new ArrayList<OpenDental.ContrTable.CellEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAddAdjust
        //
        this.butAddAdjust.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddAdjust.setAutosize(true);
        this.butAddAdjust.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddAdjust.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddAdjust.setCornerRadius(4F);
        this.butAddAdjust.Image = Resources.getAdd();
        this.butAddAdjust.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddAdjust.Location = new System.Drawing.Point(458, 6);
        this.butAddAdjust.Name = "butAddAdjust";
        this.butAddAdjust.Size = new System.Drawing.Size(126, 24);
        this.butAddAdjust.TabIndex = 72;
        this.butAddAdjust.Text = "Add Adjustment";
        this.butAddAdjust.Click += new System.EventHandler(this.butAddAdjust_Click);
        //
        // butNoneSnomedBodySite
        //
        this.butNoneSnomedBodySite.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNoneSnomedBodySite.setAutosize(true);
        this.butNoneSnomedBodySite.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNoneSnomedBodySite.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNoneSnomedBodySite.setCornerRadius(4F);
        this.butNoneSnomedBodySite.Location = new System.Drawing.Point(666, 6);
        this.butNoneSnomedBodySite.Name = "butNoneSnomedBodySite";
        this.butNoneSnomedBodySite.Size = new System.Drawing.Size(51, 22);
        this.butNoneSnomedBodySite.TabIndex = 180;
        this.butNoneSnomedBodySite.Text = "None";
        this.butNoneSnomedBodySite.Click += new System.EventHandler(this.butNoneSnomedBodySite_Click);
        //
        // butSnomedBodySiteSelect
        //
        this.butSnomedBodySiteSelect.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSnomedBodySiteSelect.setAutosize(true);
        this.butSnomedBodySiteSelect.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSnomedBodySiteSelect.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSnomedBodySiteSelect.setCornerRadius(4F);
        this.butSnomedBodySiteSelect.Location = new System.Drawing.Point(638, 6);
        this.butSnomedBodySiteSelect.Name = "butSnomedBodySiteSelect";
        this.butSnomedBodySiteSelect.Size = new System.Drawing.Size(22, 22);
        this.butSnomedBodySiteSelect.TabIndex = 179;
        this.butSnomedBodySiteSelect.Text = "...";
        this.butSnomedBodySiteSelect.Click += new System.EventHandler(this.butSnomedBodySiteSelect_Click);
        //
        // butPickSite
        //
        this.butPickSite.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickSite.setAutosize(true);
        this.butPickSite.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickSite.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickSite.setCornerRadius(2F);
        this.butPickSite.Location = new System.Drawing.Point(273, 76);
        this.butPickSite.Name = "butPickSite";
        this.butPickSite.Size = new System.Drawing.Size(19, 21);
        this.butPickSite.TabIndex = 112;
        this.butPickSite.TabStop = false;
        this.butPickSite.Text = "...";
        this.butPickSite.Click += new System.EventHandler(this.butPickSite_Click);
        //
        // textCanadaLabFee2
        //
        this.textCanadaLabFee2.Location = new System.Drawing.Point(421, 37);
        this.textCanadaLabFee2.Name = "textCanadaLabFee2";
        this.textCanadaLabFee2.Size = new System.Drawing.Size(68, 20);
        this.textCanadaLabFee2.TabIndex = 165;
        //
        // textCanadaLabFee1
        //
        this.textCanadaLabFee1.Location = new System.Drawing.Point(421, 16);
        this.textCanadaLabFee1.Name = "textCanadaLabFee1";
        this.textCanadaLabFee1.Size = new System.Drawing.Size(68, 20);
        this.textCanadaLabFee1.TabIndex = 164;
        //
        // textDateStop
        //
        this.textDateStop.Location = new System.Drawing.Point(111, 89);
        this.textDateStop.Name = "textDateStop";
        this.textDateStop.Size = new System.Drawing.Size(76, 20);
        this.textDateStop.TabIndex = 10;
        //
        // textDateScheduled
        //
        this.textDateScheduled.Location = new System.Drawing.Point(111, 69);
        this.textDateScheduled.Name = "textDateScheduled";
        this.textDateScheduled.ReadOnly = true;
        this.textDateScheduled.Size = new System.Drawing.Size(76, 20);
        this.textDateScheduled.TabIndex = 9;
        //
        // textClaimNote
        //
        this.textClaimNote.AcceptsTab = true;
        this.textClaimNote.DetectUrls = false;
        this.textClaimNote.Location = new System.Drawing.Point(106, 364);
        this.textClaimNote.MaxLength = 80;
        this.textClaimNote.Name = "textClaimNote";
        this.textClaimNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textClaimNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textClaimNote.Size = new System.Drawing.Size(277, 43);
        this.textClaimNote.TabIndex = 173;
        this.textClaimNote.Text = "";
        //
        // butReferral
        //
        this.butReferral.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butReferral.setAutosize(false);
        this.butReferral.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butReferral.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butReferral.setCornerRadius(2F);
        this.butReferral.Location = new System.Drawing.Point(707, 77);
        this.butReferral.Name = "butReferral";
        this.butReferral.Size = new System.Drawing.Size(18, 21);
        this.butReferral.TabIndex = 170;
        this.butReferral.Text = "...";
        this.butReferral.Click += new System.EventHandler(this.butReferral_Click);
        //
        // butPickProv
        //
        this.butPickProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProv.setAutosize(false);
        this.butPickProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProv.setCornerRadius(2F);
        this.butPickProv.Location = new System.Drawing.Point(265, 195);
        this.butPickProv.Name = "butPickProv";
        this.butPickProv.Size = new System.Drawing.Size(18, 21);
        this.butPickProv.TabIndex = 161;
        this.butPickProv.Text = "...";
        this.butPickProv.Click += new System.EventHandler(this.butPickProv_Click);
        //
        // butTopazSign
        //
        this.butTopazSign.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTopazSign.setAutosize(true);
        this.butTopazSign.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTopazSign.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTopazSign.setCornerRadius(4F);
        this.butTopazSign.Location = new System.Drawing.Point(873, 356);
        this.butTopazSign.Name = "butTopazSign";
        this.butTopazSign.Size = new System.Drawing.Size(81, 24);
        this.butTopazSign.TabIndex = 108;
        this.butTopazSign.Text = "Sign Topaz";
        this.butTopazSign.UseVisualStyleBackColor = true;
        this.butTopazSign.Click += new System.EventHandler(this.butTopazSign_Click);
        //
        // buttonUseAutoNote
        //
        this.buttonUseAutoNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonUseAutoNote.setAutosize(true);
        this.buttonUseAutoNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonUseAutoNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonUseAutoNote.setCornerRadius(4F);
        this.buttonUseAutoNote.Location = new System.Drawing.Point(622, 136);
        this.buttonUseAutoNote.Name = "buttonUseAutoNote";
        this.buttonUseAutoNote.Size = new System.Drawing.Size(80, 22);
        this.buttonUseAutoNote.TabIndex = 106;
        this.buttonUseAutoNote.Text = "Auto Note";
        this.buttonUseAutoNote.Click += new System.EventHandler(this.buttonUseAutoNote_Click);
        //
        // sigBox
        //
        this.sigBox.Location = new System.Drawing.Point(505, 325);
        this.sigBox.Name = "sigBox";
        this.sigBox.Size = new System.Drawing.Size(362, 79);
        this.sigBox.TabIndex = 86;
        this.sigBox.MouseUp += new System.Windows.Forms.MouseEventHandler(this.sigBox_MouseUp);
        //
        // butClearSig
        //
        this.butClearSig.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClearSig.setAutosize(true);
        this.butClearSig.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClearSig.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClearSig.setCornerRadius(4F);
        this.butClearSig.Location = new System.Drawing.Point(873, 325);
        this.butClearSig.Name = "butClearSig";
        this.butClearSig.Size = new System.Drawing.Size(81, 24);
        this.butClearSig.TabIndex = 85;
        this.butClearSig.Text = "Clear Sig";
        this.butClearSig.Click += new System.EventHandler(this.butClearSig_Click);
        //
        // textDateOriginalProsth
        //
        this.textDateOriginalProsth.Location = new System.Drawing.Point(91, 58);
        this.textDateOriginalProsth.Name = "textDateOriginalProsth";
        this.textDateOriginalProsth.Size = new System.Drawing.Size(73, 20);
        this.textDateOriginalProsth.TabIndex = 1;
        //
        // textNotes
        //
        this.textNotes.AcceptsTab = true;
        this.textNotes.BackColor = System.Drawing.SystemColors.Window;
        this.textNotes.DetectUrls = false;
        this.textNotes.Location = new System.Drawing.Point(504, 157);
        this.textNotes.Name = "textNotes";
        this.textNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNotes.Size = new System.Drawing.Size(450, 164);
        this.textNotes.TabIndex = 1;
        this.textNotes.Text = "";
        this.textNotes.TextChanged += new System.EventHandler(this.textNotes_TextChanged);
        //
        // butSetComplete
        //
        this.butSetComplete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSetComplete.setAutosize(true);
        this.butSetComplete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSetComplete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSetComplete.setCornerRadius(4F);
        this.butSetComplete.Location = new System.Drawing.Point(643, 19);
        this.butSetComplete.Name = "butSetComplete";
        this.butSetComplete.Size = new System.Drawing.Size(79, 22);
        this.butSetComplete.TabIndex = 54;
        this.butSetComplete.Text = "Set Complete";
        this.butSetComplete.Click += new System.EventHandler(this.butSetComplete_Click);
        //
        // butEditAnyway
        //
        this.butEditAnyway.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butEditAnyway.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butEditAnyway.setAutosize(true);
        this.butEditAnyway.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butEditAnyway.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butEditAnyway.setCornerRadius(4F);
        this.butEditAnyway.Location = new System.Drawing.Point(594, 671);
        this.butEditAnyway.Name = "butEditAnyway";
        this.butEditAnyway.Size = new System.Drawing.Size(104, 24);
        this.butEditAnyway.TabIndex = 51;
        this.butEditAnyway.Text = "&Edit Anyway";
        this.butEditAnyway.Visible = false;
        this.butEditAnyway.Click += new System.EventHandler(this.butEditAnyway_Click);
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
        this.butDelete.Location = new System.Drawing.Point(2, 671);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(83, 24);
        this.butDelete.TabIndex = 8;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(870, 671);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(76, 24);
        this.butCancel.TabIndex = 13;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(779, 671);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(76, 24);
        this.butOK.TabIndex = 12;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(106, 40);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(76, 20);
        this.textDate.TabIndex = 102;
        //
        // butNow
        //
        this.butNow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNow.setAutosize(false);
        this.butNow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNow.setCornerRadius(4F);
        this.butNow.Location = new System.Drawing.Point(369, 40);
        this.butNow.Name = "butNow";
        this.butNow.Size = new System.Drawing.Size(27, 20);
        this.butNow.TabIndex = 101;
        this.butNow.Text = "Now";
        this.butNow.UseVisualStyleBackColor = true;
        this.butNow.Visible = false;
        this.butNow.Click += new System.EventHandler(this.butNow_Click);
        //
        // butD
        //
        this.butD.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butD.setAutosize(true);
        this.butD.BackColor = System.Drawing.SystemColors.Control;
        this.butD.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butD.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butD.setCornerRadius(4F);
        this.butD.Location = new System.Drawing.Point(61, 23);
        this.butD.Name = "butD";
        this.butD.Size = new System.Drawing.Size(24, 20);
        this.butD.TabIndex = 27;
        this.butD.Text = "D";
        this.butD.UseVisualStyleBackColor = false;
        this.butD.Click += new System.EventHandler(this.butD_Click);
        //
        // butBF
        //
        this.butBF.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butBF.setAutosize(true);
        this.butBF.BackColor = System.Drawing.SystemColors.Control;
        this.butBF.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butBF.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butBF.setCornerRadius(4F);
        this.butBF.Location = new System.Drawing.Point(22, 3);
        this.butBF.Name = "butBF";
        this.butBF.Size = new System.Drawing.Size(28, 20);
        this.butBF.TabIndex = 28;
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
        this.butL.Location = new System.Drawing.Point(32, 43);
        this.butL.Name = "butL";
        this.butL.Size = new System.Drawing.Size(24, 20);
        this.butL.TabIndex = 29;
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
        this.butM.Location = new System.Drawing.Point(3, 23);
        this.butM.Name = "butM";
        this.butM.Size = new System.Drawing.Size(24, 20);
        this.butM.TabIndex = 25;
        this.butM.Text = "M";
        this.butM.UseVisualStyleBackColor = false;
        this.butM.Click += new System.EventHandler(this.butM_Click);
        //
        // butV
        //
        this.butV.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butV.setAutosize(true);
        this.butV.BackColor = System.Drawing.SystemColors.Control;
        this.butV.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butV.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butV.setCornerRadius(4F);
        this.butV.Location = new System.Drawing.Point(50, 3);
        this.butV.Name = "butV";
        this.butV.Size = new System.Drawing.Size(17, 20);
        this.butV.TabIndex = 30;
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
        this.butOI.Location = new System.Drawing.Point(27, 23);
        this.butOI.Name = "butOI";
        this.butOI.Size = new System.Drawing.Size(34, 20);
        this.butOI.TabIndex = 26;
        this.butOI.Text = "O/I";
        this.butOI.UseVisualStyleBackColor = false;
        this.butOI.Click += new System.EventHandler(this.butOI_Click);
        //
        // textDateTP
        //
        this.textDateTP.Location = new System.Drawing.Point(106, 21);
        this.textDateTP.Name = "textDateTP";
        this.textDateTP.Size = new System.Drawing.Size(76, 20);
        this.textDateTP.TabIndex = 99;
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(106, 1);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.ReadOnly = true;
        this.textDateEntry.Size = new System.Drawing.Size(76, 20);
        this.textDateEntry.TabIndex = 95;
        //
        // textProcFee
        //
        this.textProcFee.Location = new System.Drawing.Point(106, 155);
        this.textProcFee.Name = "textProcFee";
        this.textProcFee.Size = new System.Drawing.Size(68, 20);
        this.textProcFee.TabIndex = 6;
        this.textProcFee.Validating += new System.ComponentModel.CancelEventHandler(this.textProcFee_Validating);
        //
        // butChange
        //
        this.butChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChange.setAutosize(true);
        this.butChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChange.setCornerRadius(4F);
        this.butChange.Location = new System.Drawing.Point(184, 61);
        this.butChange.Name = "butChange";
        this.butChange.Size = new System.Drawing.Size(74, 20);
        this.butChange.TabIndex = 37;
        this.butChange.Text = "C&hange";
        this.butChange.Click += new System.EventHandler(this.butChange_Click);
        //
        // FormProcEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(962, 696);
        this.Controls.Add(this.butSearch);
        this.Controls.Add(this.butLock);
        this.Controls.Add(this.butInvalidate);
        this.Controls.Add(this.butAppend);
        this.Controls.Add(this.labelLocked);
        this.Controls.Add(this.tabControl);
        this.Controls.Add(this.labelClaimNote);
        this.Controls.Add(this.textClaimNote);
        this.Controls.Add(this.butReferral);
        this.Controls.Add(this.textReferral);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.comboProcStatus);
        this.Controls.Add(this.comboProvNum);
        this.Controls.Add(this.checkHideGraphics);
        this.Controls.Add(this.butPickProv);
        this.Controls.Add(this.labelInvalidSig);
        this.Controls.Add(this.butTopazSign);
        this.Controls.Add(this.buttonUseAutoNote);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.comboDx);
        this.Controls.Add(this.comboPriority);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.labelPriority);
        this.Controls.Add(this.labelDx);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.sigBox);
        this.Controls.Add(this.butClearSig);
        this.Controls.Add(this.groupProsth);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.labelIncomplete);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.butSetComplete);
        this.Controls.Add(this.butEditAnyway);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelSetComplete);
        this.Controls.Add(this.labelClaim);
        this.Controls.Add(this.panel1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Info";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormProcEdit_FormClosing);
        this.Load += new System.EventHandler(this.FormProcInfo_Load);
        this.groupQuadrant.ResumeLayout(false);
        this.groupArch.ResumeLayout(false);
        this.panelSurfaces.ResumeLayout(false);
        this.groupSextant.ResumeLayout(false);
        this.panel1.ResumeLayout(false);
        this.panel1.PerformLayout();
        this.groupProsth.ResumeLayout(false);
        this.groupProsth.PerformLayout();
        this.groupCanadianProcTypeCode.ResumeLayout(false);
        this.tabControl.ResumeLayout(false);
        this.tabPageFinancial.ResumeLayout(false);
        this.tabPageMedical.ResumeLayout(false);
        this.tabPageMedical.PerformLayout();
        this.tabPageMisc.ResumeLayout(false);
        this.tabPageMisc.PerformLayout();
        this.tabPageCanada.ResumeLayout(false);
        this.tabPageCanada.PerformLayout();
        this.tabPageOrion.ResumeLayout(false);
        this.tabPageOrion.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProcInfo_Load(Object sender, System.EventArgs e) throws Exception {
        //Set the title bar to show the patient's name much like the main screen does.
        this.Text += " - " + PatCur.getNameLF();
        //richTextBox1.Text="This is a test of the functions of a rich text box.";
        //webBrowser1.
        //richTextBox1.Select(10,4);
        //richTextBox1.SelectionFont=new Font(FontFamily.GenericMonospace,8);
        //richTextBox1.Select(22,9);
        //richTextBox1.SelectionFont=new Font(FontFamily.GenericMonospace,8,FontStyle.Underline);
        textDateEntry.Text = ProcCur.DateEntryC.ToShortDateString();
        if (PrefC.getBool(PrefName.EasyHidePublicHealth))
        {
            labelPlaceService.Visible = false;
            comboPlaceService.Visible = false;
            labelSite.Visible = false;
            textSite.Visible = false;
            butPickSite.Visible = false;
        }
         
        if (PrefC.getLong(PrefName.UseInternationalToothNumbers) == 1)
        {
            listBoxTeeth.Items.Clear();
            listBoxTeeth.Items.AddRange(new String[]{ "18", "17", "16", "15", "14", "13", "12", "11", "21", "22", "23", "24", "25", "26", "27", "28" });
            listBoxTeeth2.Items.Clear();
            listBoxTeeth2.Items.AddRange(new String[]{ "48", "47", "46", "45", "44", "43", "42", "41", "31", "32", "33", "34", "35", "36", "37", "38" });
        }
         
        if (PrefC.getLong(PrefName.UseInternationalToothNumbers) == 3)
        {
            listBoxTeeth.Items.Clear();
            listBoxTeeth.Items.AddRange(new String[]{ "8", "7", "6", "5", "4", "3", "2", "1", "1", "2", "3", "4", "5", "6", "7", "8" });
            listBoxTeeth2.Items.Clear();
            listBoxTeeth2.Items.AddRange(new String[]{ "8", "7", "6", "5", "4", "3", "2", "1", "1", "2", "3", "4", "5", "6", "7", "8" });
        }
         
        if (!Security.isAuthorized(Permissions.ProcEditShowFee,true))
        {
            labelAmount.Visible = false;
            textProcFee.Visible = false;
        }
         
        if (!Security.isAuthorized(Permissions.ProcedureNote,true))
        {
            textNotes.Enabled = false;
            buttonUseAutoNote.Enabled = false;
        }
         
        ClaimList = Claims.refresh(PatCur.PatNum);
        ProcedureCode2 = ProcedureCodes.getProcCode(ProcCur.CodeNum);
        if (ProcCur.ProcStatus == ProcStat.C && PrefC.getBool(PrefName.ProcLockingIsAllowed) && !ProcCur.IsLocked)
        {
            butLock.Visible = true;
        }
        else
        {
            butLock.Visible = false;
        } 
        if (IsNew)
        {
            if (ProcCur.ProcStatus == ProcStat.C)
            {
                if (!Security.isAuthorized(Permissions.ProcComplCreate))
                {
                    DialogResult = DialogResult.Cancel;
                    return ;
                }
                 
            }
             
        }
        else
        {
            //SetControls();
            //return;
            if (ProcCur.ProcStatus == ProcStat.C)
            {
                if (ProcCur.IsLocked)
                {
                    //Whether locking is currently allowed, this proc may have been locked previously.
                    butOK.Enabled = false;
                    //use this state to cascade permission to any form opened from here
                    butDelete.Enabled = false;
                    butChange.Enabled = false;
                    butEditAnyway.Enabled = false;
                    butSetComplete.Enabled = false;
                    butSnomedBodySiteSelect.Enabled = false;
                    butNoneSnomedBodySite.Enabled = false;
                    labelLocked.Visible = true;
                    butAppend.Visible = true;
                    textNotes.ReadOnly = true;
                    //just for visual cue.  No way to save changes, anyway.
                    textNotes.BackColor = SystemColors.Control;
                    butInvalidate.Visible = true;
                    butInvalidate.Location = butLock.Location;
                }
                else
                {
                    butInvalidate.Visible = false;
                    //because islocked overrides security:
                    if (!Security.isAuthorized(Permissions.ProcComplEdit,ProcCur.DateEntryC))
                    {
                        butOK.Enabled = false;
                        //use this state to cascade permission to any form opened from here
                        butDelete.Enabled = false;
                        butChange.Enabled = false;
                        butEditAnyway.Enabled = false;
                        butSetComplete.Enabled = false;
                    }
                     
                } 
            }
             
        } 
        //ClaimProcList=ClaimProcs.Refresh(PatCur.PatNum);
        ClaimProcsForProc = ClaimProcs.refreshForProc(ProcCur.ProcNum);
        PatPlanList = PatPlans.refresh(PatCur.PatNum);
        BenefitList = Benefits.refresh(PatPlanList,SubList);
        if (Procedures.isAttachedToClaim(ProcCur,ClaimProcsForProc))
        {
            StartedAttachedToClaim = true;
            //however, this doesn't stop someone from creating a claim while this window is open,
            //so this is checked at the end, too.
            panel1.Enabled = false;
            comboProcStatus.Enabled = false;
            checkNoBillIns.Enabled = false;
            butChange.Enabled = false;
            butDelete.Enabled = false;
            butEditAnyway.Visible = true;
            labelClaim.Visible = true;
            butSetComplete.Enabled = false;
        }
         
        if (PrefC.getBool(PrefName.EasyHideClinical))
        {
            labelDx.Visible = false;
            comboDx.Visible = false;
            labelPrognosis.Visible = false;
            comboPrognosis.Visible = false;
        }
         
        if (PrefC.getBool(PrefName.EasyHideMedicaid))
        {
            comboBillingTypeOne.Visible = false;
            labelBillingTypeOne.Visible = false;
            comboBillingTypeTwo.Visible = false;
            labelBillingTypeTwo.Visible = false;
        }
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            //groupCanadianProcType.Location=new Point(106,301);
            groupProsth.Visible = false;
            labelClaimNote.Visible = false;
            textClaimNote.Visible = false;
            butBF.Text = Lan.g(this,"B/V");
            //vestibular instead of facial
            butV.Text = Lan.g(this,"5");
            if (ProcedureCode2.IsCanadianLab)
            {
                //Prevent lab fees from having lab fees attached.
                labelCanadaLabFee1.Visible = false;
                textCanadaLabFee1.Visible = false;
                labelCanadaLabFee2.Visible = false;
                textCanadaLabFee2.Visible = false;
            }
            else
            {
                canadaLabFees = Procedures.getCanadianLabFees(ProcCur.ProcNum);
                if (canadaLabFees.Count > 0)
                {
                    textCanadaLabFee1.Text = canadaLabFees[0].ProcFee.ToString("n");
                    if (canadaLabFees[0].ProcStatus == ProcStat.C)
                    {
                        textCanadaLabFee1.ReadOnly = true;
                    }
                     
                }
                 
                if (canadaLabFees.Count > 1)
                {
                    textCanadaLabFee2.Text = canadaLabFees[1].ProcFee.ToString("n");
                    if (canadaLabFees[1].ProcStatus == ProcStat.C)
                    {
                        textCanadaLabFee2.ReadOnly = true;
                    }
                     
                }
                 
            } 
        }
        else
        {
            tabControl.Controls.Remove(tabPageCanada);
        } 
        //groupCanadianProcType.Visible=false;
        if (Programs.getUsingOrion())
        {
            if (IsNew)
            {
                OrionProcCur = new OrionProc();
                OrionProcCur.ProcNum = ProcCur.ProcNum;
                if (ProcCur.ProcStatus == ProcStat.EO)
                {
                    OrionProcCur.Status2 = OrionStatus.E;
                }
                else
                {
                    OrionProcCur.Status2 = OrionStatus.TP;
                } 
            }
            else
            {
                OrionProcCur = OrionProcs.getOneByProcNum(ProcCur.ProcNum);
                if (ProcCur.DateTP < MiscData.getNowDateTime().Date && (OrionProcCur.Status2 == OrionStatus.CA_EPRD || OrionProcCur.Status2 == OrionStatus.CA_PD || OrionProcCur.Status2 == OrionStatus.CA_Tx || OrionProcCur.Status2 == OrionStatus.R))
                {
                    //Not allowed to edit procedures with these statuses that are older than a day.
                    MsgBox.show(this,"You cannot edit refused or cancelled procedures.");
                    DialogResult = DialogResult.Cancel;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.C || OrionProcCur.Status2 == OrionStatus.CR || OrionProcCur.Status2 == OrionStatus.CS)
                {
                    textNotes.Enabled = false;
                }
                 
            } 
            textDateTP.ReadOnly = true;
            //panelOrion.Visible=true;
            butAddEstimate.Visible = false;
            checkNoBillIns.Visible = false;
            gridIns.Visible = false;
            butAddAdjust.Visible = false;
            tbPay.Visible = false;
            tbAdj.Visible = false;
            comboProcStatus.Enabled = false;
            labelAmount.Visible = false;
            textProcFee.Visible = false;
            labelPriority.Visible = false;
            comboPriority.Visible = false;
            butSetComplete.Visible = false;
            labelSetComplete.Visible = false;
        }
        else
        {
            tabControl.Controls.Remove(tabPageOrion);
        } 
        if (Programs.getUsingOrion() || PrefC.getBool(PrefName.ShowFeatureMedicalInsurance))
        {
            labelEndTime.Visible = true;
            textTimeEnd.Visible = true;
            butNow.Visible = true;
            labelTimeFinal.Visible = true;
            textTimeFinal.Visible = true;
        }
         
        if (PrefC.getBool(PrefName.ShowFeatureEhr))
        {
            textNotes.HideSelection = false;
        }
        else
        {
            //When text is selected programmatically using our Search function, this causes the selection to be visible to the users.
            butSearch.Visible = false;
        } 
        IsStartingUp = true;
        fillControlsOnStartup();
        setControlsUpperLeft();
        fillReferral();
        fillIns(false);
        fillPayments();
        fillAdj();
        IsStartingUp = false;
    }

    //string retVal=ProcCur.Note+ProcCur.UserNum.ToString();
    //MsgBoxCopyPaste msgb=new MsgBoxCopyPaste(retVal);
    //msgb.ShowDialog();
    /**
    * ONLY run on startup. Fills the basic controls, except not the ones in the upper left panel which are handled in SetControlsUpperLeft.
    */
    private void fillControlsOnStartup() throws Exception {
        comboProcStatus.Items.Clear();
        comboProcStatus.Items.Add(Lan.g(this,"Treatment Planned"));
        comboProcStatus.Items.Add(Lan.g(this,"Complete"));
        if (!PrefC.getBool(PrefName.EasyHideClinical))
        {
            comboProcStatus.Items.Add(Lan.g(this,"Existing-Current Prov"));
            comboProcStatus.Items.Add(Lan.g(this,"Existing-Other Prov"));
            comboProcStatus.Items.Add(Lan.g(this,"Referred Out"));
            //comboProcStatus.Items.Add(Lan.g(this,"Deleted"));
            comboProcStatus.Items.Add(Lan.g(this,"Condition"));
        }
         
        if (ProcCur.ProcStatus == ProcStat.TP)
        {
            comboProcStatus.SelectedIndex = 0;
        }
         
        if (ProcCur.ProcStatus == ProcStat.C)
        {
            comboProcStatus.SelectedIndex = 1;
        }
         
        if (!PrefC.getBool(PrefName.EasyHideClinical))
        {
            if (ProcCur.ProcStatus == ProcStat.EC)
            {
                comboProcStatus.SelectedIndex = 2;
            }
             
            if (ProcCur.ProcStatus == ProcStat.EO)
            {
                comboProcStatus.SelectedIndex = 3;
            }
             
            if (ProcCur.ProcStatus == ProcStat.R)
            {
                comboProcStatus.SelectedIndex = 4;
            }
             
            if (ProcCur.ProcStatus == ProcStat.Cn)
            {
                comboProcStatus.SelectedIndex = 5;
            }
             
        }
         
        if (ProcCur.ProcStatus == ProcStat.D && ProcCur.IsLocked)
        {
            //an invalidated proc
            comboProcStatus.Items.Clear();
            comboProcStatus.Items.Add(Lan.g(this,"Invalidated"));
            comboProcStatus.SelectedIndex = 0;
            comboProcStatus.Enabled = false;
            butInvalidate.Visible = false;
            butOK.Enabled = false;
            butDelete.Enabled = false;
            butChange.Enabled = false;
            butEditAnyway.Enabled = false;
            butSetComplete.Enabled = false;
            butAddEstimate.Enabled = false;
            butAddAdjust.Enabled = false;
        }
         
        //if clinical is hidden, then there's a chance that no item is selected at this point.
        comboDx.Items.Clear();
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()].Length;i++)
        {
            comboDx.Items.Add(DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()][i].DefNum == ProcCur.Dx)
                comboDx.SelectedIndex = i;
             
        }
        comboPrognosis.Items.Clear();
        comboPrognosis.Items.Add(Lan.g(this,"no prognosis"));
        comboPrognosis.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()].Length;i++)
        {
            comboPrognosis.Items.Add(DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()][i].DefNum == ProcCur.Prognosis)
                comboPrognosis.SelectedIndex = i + 1;
             
        }
        checkHideGraphics.Checked = ProcCur.HideGraphics;
        if (Programs.getUsingOrion() && this.IsNew && !OrionDentist)
        {
            ProcCur.ProvNum = Providers.getOrionProvNum(ProcCur.ProvNum);
        }
         
        //Returns 0 if logged in as non provider.
        //ProvNum of 0 will be required to change before closing form.
        comboProvNum.Items.Clear();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvNum.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == ProcCur.ProvNum)
            {
                comboProvNum.SelectedIndex = i;
            }
             
        }
        comboPriority.Items.Clear();
        comboPriority.Items.Add(Lan.g(this,"no priority"));
        comboPriority.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()].Length;i++)
        {
            comboPriority.Items.Add(DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][i].DefNum == ProcCur.Priority)
                comboPriority.SelectedIndex = i + 1;
             
        }
        comboBillingTypeOne.Items.Clear();
        comboBillingTypeOne.Items.Add(Lan.g(this,"none"));
        comboBillingTypeOne.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            comboBillingTypeOne.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].DefNum == ProcCur.BillingTypeOne)
                comboBillingTypeOne.SelectedIndex = i + 1;
             
        }
        comboBillingTypeTwo.Items.Clear();
        comboBillingTypeTwo.Items.Add(Lan.g(this,"none"));
        comboBillingTypeTwo.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            comboBillingTypeTwo.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].DefNum == ProcCur.BillingTypeTwo)
                comboBillingTypeTwo.SelectedIndex = i + 1;
             
        }
        textBillingNote.Text = ProcCur.BillingNote;
        textNotes.Text = ProcCur.Note;
        textNotes.Select(textNotes.Text.Length, 0);
        checkForCompleteNote();
        comboPlaceService.Items.Clear();
        comboPlaceService.Items.AddRange(Enum.GetNames(PlaceOfService.class));
        comboPlaceService.SelectedIndex = ((Enum)ProcCur.PlaceService).ordinal();
        //checkHideGraphical.Checked=ProcCur.HideGraphical;
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            comboClinic.Visible = false;
            labelClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add("none");
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (Clinics.getList()[i].ClinicNum == ProcCur.ClinicNum)
                {
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        } 
        textSite.Text = Sites.getDescription(ProcCur.SiteNum);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (ProcCur.CanadianTypeCodes == null || StringSupport.equals(ProcCur.CanadianTypeCodes, ""))
            {
                checkTypeCodeX.Checked = true;
            }
            else
            {
                if (ProcCur.CanadianTypeCodes.Contains("A"))
                {
                    checkTypeCodeA.Checked = true;
                }
                 
                if (ProcCur.CanadianTypeCodes.Contains("B"))
                {
                    checkTypeCodeB.Checked = true;
                }
                 
                if (ProcCur.CanadianTypeCodes.Contains("C"))
                {
                    checkTypeCodeC.Checked = true;
                }
                 
                if (ProcCur.CanadianTypeCodes.Contains("E"))
                {
                    checkTypeCodeE.Checked = true;
                }
                 
                if (ProcCur.CanadianTypeCodes.Contains("L"))
                {
                    checkTypeCodeL.Checked = true;
                }
                 
                if (ProcCur.CanadianTypeCodes.Contains("S"))
                {
                    checkTypeCodeS.Checked = true;
                }
                 
                if (ProcCur.CanadianTypeCodes.Contains("X"))
                {
                    checkTypeCodeX.Checked = true;
                }
                 
            } 
        }
        else
        {
            if (ProcedureCode2.IsProsth)
            {
                listProsth.Items.Add(Lan.g(this,"No"));
                listProsth.Items.Add(Lan.g(this,"Initial"));
                listProsth.Items.Add(Lan.g(this,"Replacement"));
                System.String __dummyScrutVar0 = ProcCur.Prosthesis;
                if (__dummyScrutVar0.equals(""))
                {
                    listProsth.SelectedIndex = 0;
                }
                else if (__dummyScrutVar0.equals("I"))
                {
                    listProsth.SelectedIndex = 1;
                }
                else if (__dummyScrutVar0.equals("R"))
                {
                    listProsth.SelectedIndex = 2;
                }
                   
                if (ProcCur.DateOriginalProsth.Year > 1880)
                {
                    textDateOriginalProsth.Text = ProcCur.DateOriginalProsth.ToShortDateString();
                }
                 
            }
            else
            {
                groupProsth.Visible = false;
            } 
        } 
        //medical
        textMedicalCode.Text = ProcCur.MedicalCode;
        textDiagnosticCode.Text = ProcCur.DiagnosticCode;
        checkIsPrincDiag.Checked = ProcCur.IsPrincDiag;
        textCodeMod1.Text = ProcCur.CodeMod1;
        textCodeMod2.Text = ProcCur.CodeMod2;
        textCodeMod3.Text = ProcCur.CodeMod3;
        textCodeMod4.Text = ProcCur.CodeMod4;
        textUnitQty.Text = ProcCur.UnitQty.ToString();
        comboUnitType.Items.Clear();
        _snomedBodySite = Snomeds.getByCode(ProcCur.SnomedBodySite);
        if (_snomedBodySite == null)
        {
            textSnomedBodySite.Text = "";
        }
        else
        {
            textSnomedBodySite.Text = _snomedBodySite.Description;
        } 
        for (int i = 0;i < Enum.GetNames(ProcUnitQtyType.class).Length;i++)
        {
            comboUnitType.Items.Add(Enum.GetNames(ProcUnitQtyType.class)[i]);
        }
        comboUnitType.SelectedIndex = ((Enum)ProcCur.UnitQtyType).ordinal();
        textRevCode.Text = ProcCur.RevCode;
        //DrugNDC is handled in SetControlsUpperLeft
        comboDrugUnit.Items.Clear();
        for (int i = 0;i < Enum.GetNames(EnumProcDrugUnit.class).Length;i++)
        {
            comboDrugUnit.Items.Add(Enum.GetNames(EnumProcDrugUnit.class)[i]);
        }
        comboDrugUnit.SelectedIndex = ((Enum)ProcCur.DrugUnit).ordinal();
        if (ProcCur.DrugQty != 0)
        {
            textDrugQty.Text = ProcCur.DrugQty.ToString();
        }
         
        textClaimNote.Text = ProcCur.ClaimNote;
        textUser.Text = Userods.getName(ProcCur.UserNum);
        //might be blank. Will change automatically if user changes note or alters sig.
        labelInvalidSig.Visible = false;
        sigBox.Visible = true;
        if (ProcCur.SigIsTopaz)
        {
            if (!StringSupport.equals(ProcCur.Signature, ""))
            {
                //if(allowTopaz){
                sigBox.Visible = false;
                sigBoxTopaz.Visible = true;
                CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,"0000000000000000");
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
                //high encryption
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
                //high compression
                CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,ProcCur.Signature);
                //older notes may have been signed with zeros due to a bug.  We still want to show the sig in that case.
                //but if a sig is not showing, then set the key string to try to get it to show.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazAutoKeyData(sigBoxTopaz,ProcCur.Note + ProcCur.UserNum.ToString());
                    CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,ProcCur.Signature);
                }
                 
                //If sig is not showing, then try encryption mode 3 for signatures signed with old SigPlusNet.dll.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,3);
                    //Unknown mode (told to use via TopazSystems)
                    CodeBase.TopazWrapper.setTopazSigString(sigBoxTopaz,ProcCur.Signature);
                }
                 
                //if still not showing, then it must be invalid
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
                CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,0);
            }
             
        }
        else
        {
            //}
            if (ProcCur.Signature != null && !StringSupport.equals(ProcCur.Signature, ""))
            {
                sigBox.Visible = true;
                if (sigBoxTopaz != null)
                {
                    sigBoxTopaz.Visible = false;
                }
                 
                sigBox.clearTablet();
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.setKeyString("0000000000000000");
                sigBox.setAutoKeyData(ProcCur.Note + ProcCur.UserNum.ToString());
                //sigBox.SetEncryptionMode(2);//high encryption
                //sigBox.SetSigCompressionMode(2);//high compression
                sigBox.setSigString(ProcCur.Signature);
                if (sigBox.numberOfTabletPoints() == 0)
                {
                    labelInvalidSig.Visible = true;
                }
                 
                sigBox.setTabletState(0);
            }
             
        } 
        //not accepting input.  To accept input, change the note, or clear the sig.
        if (Programs.getUsingOrion())
        {
            //panelOrion.Visible) {
            comboDPC.Items.Clear();
            //comboDPC.Items.AddRange(Enum.GetNames(typeof(OrionDPC)));
            comboDPC.Items.Add("Not Specified");
            comboDPC.Items.Add("None");
            comboDPC.Items.Add("1A-within 1 day");
            comboDPC.Items.Add("1B-within 30 days");
            comboDPC.Items.Add("1C-within 60 days");
            comboDPC.Items.Add("2-within 120 days");
            comboDPC.Items.Add("3-within 1 year");
            comboDPC.Items.Add("4-no further treatment/appt");
            comboDPC.Items.Add("5-no appointment needed");
            comboDPCpost.Items.Clear();
            comboDPCpost.Items.Add("Not Specified");
            comboDPCpost.Items.Add("None");
            comboDPCpost.Items.Add("1A-within 1 day");
            comboDPCpost.Items.Add("1B-within 30 days");
            comboDPCpost.Items.Add("1C-within 60 days");
            comboDPCpost.Items.Add("2-within 120 days");
            comboDPCpost.Items.Add("3-within 1 year");
            comboDPCpost.Items.Add("4-no further treatment/appt");
            comboDPCpost.Items.Add("5-no appointment needed");
            comboStatus.Items.Clear();
            comboStatus.Items.Add("TP-treatment planned");
            comboStatus.Items.Add("C-completed");
            comboStatus.Items.Add("E-existing prior to incarceration");
            comboStatus.Items.Add("R-refused treatment");
            comboStatus.Items.Add("RO-referred out to specialist");
            comboStatus.Items.Add("CS-completed by specialist");
            comboStatus.Items.Add("CR-completed by registry");
            comboStatus.Items.Add("CA_Tx-cancelled, tx plan changed");
            comboStatus.Items.Add("CA_EPRD-cancelled, eligible parole");
            comboStatus.Items.Add("CA_P/D-cancelled, parole/discharge");
            comboStatus.Items.Add("S-suspended, unacceptable plaque");
            comboStatus.Items.Add("ST-stop clock, multi visit");
            comboStatus.Items.Add("W-watch");
            comboStatus.Items.Add("A-alternative");
            comboStatus.SelectedIndex = 0;
            ProcedureCode pc = ProcedureCodes.getProcCodeFromDb(ProcCur.CodeNum);
            checkIsRepair.Visible = pc.IsProsth;
            //DateTP doesn't get set sometimes and calculations are made based on the DateTP. So set it to the current date as fail-safe.
            if (ProcCur.DateTP.Year < 1880)
            {
                textDateTP.Text = MiscData.getNowDateTime().ToShortDateString();
            }
            else
            {
                textDateTP.Text = ProcCur.DateTP.ToShortDateString();
            } 
            BitArray ba = new BitArray(new int[]{ ((Enum)OrionProcCur.Status2).ordinal() });
            for (int i = 0;i < ba.Length;i++)
            {
                //should nearly always be non-zero
                if (ba[i])
                {
                    comboStatus.SelectedIndex = i;
                    break;
                }
                 
            }
            if (!IsNew)
            {
                OrionProcOld = OrionProcCur.copy();
                comboDPC.SelectedIndex = ((Enum)OrionProcCur.DPC).ordinal();
                comboDPCpost.SelectedIndex = ((Enum)OrionProcCur.DPCpost).ordinal();
                if (OrionProcCur.DPC == OrionDPC.NotSpecified || OrionProcCur.DPC == OrionDPC.None || OrionProcCur.DPC == OrionDPC._4 || OrionProcCur.DPC == OrionDPC._5)
                {
                    labelScheduleBy.Visible = true;
                }
                 
                if (OrionProcCur.DateScheduleBy.Year > 1880)
                {
                    textDateScheduled.Text = OrionProcCur.DateScheduleBy.ToShortDateString();
                }
                 
                if (OrionProcCur.DateStopClock.Year > 1880)
                {
                    textDateStop.Text = OrionProcCur.DateStopClock.ToShortDateString();
                }
                 
                checkIsOnCall.Checked = OrionProcCur.IsOnCall;
                checkIsEffComm.Checked = OrionProcCur.IsEffectiveComm;
                checkIsRepair.Checked = OrionProcCur.IsRepair;
            }
            else
            {
                labelScheduleBy.Visible = true;
                comboDPC.SelectedIndex = 0;
                comboDPCpost.SelectedIndex = 0;
                textDateStop.Text = "";
            } 
        }
         
    }

    private void setSurfButtons() throws Exception {
        if (textSurfaces.Text.Contains("B") || textSurfaces.Text.Contains("F"))
            butBF.BackColor = Color.White;
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (textSurfaces.Text.Contains("V"))
                butBF.BackColor = Color.White;
             
        }
         
        if (textSurfaces.Text.Contains("O") || textSurfaces.Text.Contains("I"))
            butOI.BackColor = Color.White;
         
        if (textSurfaces.Text.Contains("M"))
            butM.BackColor = Color.White;
         
        if (textSurfaces.Text.Contains("D"))
            butD.BackColor = Color.White;
         
        if (textSurfaces.Text.Contains("L"))
            butL.BackColor = Color.White;
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (textSurfaces.Text.Contains("5"))
                butV.BackColor = Color.White;
             
        }
        else
        {
            if (textSurfaces.Text.Contains("V"))
                butV.BackColor = Color.White;
             
        } 
    }

    /**
    * Called on open and after changing code.  Sets the visibilities and the data of all the fields in the upper left panel.
    */
    private void setControlsUpperLeft() throws Exception {
        textDateTP.Text = ProcCur.DateTP.ToString("d");
        DateTime dateT = new DateTime();
        if (IsStartingUp)
        {
            textDate.Text = ProcCur.ProcDate.ToString("d");
            dateT = PIn.DateT(ProcCur.ProcTime.ToString());
            if (!StringSupport.equals(dateT.ToShortTimeString(), "12:00 AM"))
            {
                textTimeStart.Text += dateT.ToShortTimeString();
            }
             
            if (Programs.getUsingOrion() || PrefC.getBool(PrefName.ShowFeatureMedicalInsurance))
            {
                dateT = PIn.DateT(ProcCur.ProcTimeEnd.ToString());
                if (!StringSupport.equals(dateT.ToShortTimeString(), "12:00 AM"))
                {
                    textTimeEnd.Text = dateT.ToShortTimeString();
                }
                 
                updateFinalMin();
                if (ProcCur.DateTP.Year < 1880)
                {
                    textDateTP.Text = MiscData.getNowDateTime().ToShortDateString();
                }
                 
            }
             
        }
         
        textProc.Text = ProcedureCode2.ProcCode;
        textDesc.Text = ProcedureCode2.Descript;
        textDrugNDC.Text = ProcedureCode2.DrugNDC;
        switch(ProcedureCode2.TreatArea)
        {
            case Surf: 
                this.textTooth.Visible = true;
                this.labelTooth.Visible = true;
                this.textSurfaces.Visible = true;
                this.labelSurfaces.Visible = true;
                this.panelSurfaces.Visible = true;
                if (Tooth.isValidDB(ProcCur.ToothNum))
                {
                    errorProvider2.SetError(textTooth, "");
                    textTooth.Text = Tooth.toInternat(ProcCur.ToothNum);
                    textSurfaces.Text = Tooth.surfTidyFromDbToDisplay(ProcCur.Surf,ProcCur.ToothNum);
                    setSurfButtons();
                }
                else
                {
                    errorProvider2.SetError(textTooth, Lan.g(this,"Invalid tooth number."));
                    textTooth.Text = ProcCur.ToothNum;
                } 
                //textSurfaces.Text=Tooth.SurfTidy(ProcCur.Surf,"");//only valid toothnums allowed
                if (StringSupport.equals(textSurfaces.Text, ""))
                    errorProvider2.SetError(textSurfaces, "No surfaces selected.");
                else
                    errorProvider2.SetError(textSurfaces, ""); 
                break;
            case Tooth: 
                this.textTooth.Visible = true;
                this.labelTooth.Visible = true;
                if (Tooth.isValidDB(ProcCur.ToothNum))
                {
                    errorProvider2.SetError(textTooth, "");
                    textTooth.Text = Tooth.toInternat(ProcCur.ToothNum);
                }
                else
                {
                    errorProvider2.SetError(textTooth, Lan.g(this,"Invalid tooth number."));
                    textTooth.Text = ProcCur.ToothNum;
                } 
                break;
            case Mouth: 
                break;
            case Quad: 
                this.groupQuadrant.Visible = true;
                System.String __dummyScrutVar2 = ProcCur.Surf;
                if (__dummyScrutVar2.equals("UR"))
                {
                    this.radioUR.Checked = true;
                }
                else if (__dummyScrutVar2.equals("UL"))
                {
                    this.radioUL.Checked = true;
                }
                else if (__dummyScrutVar2.equals("LR"))
                {
                    this.radioLR.Checked = true;
                }
                else if (__dummyScrutVar2.equals("LL"))
                {
                    this.radioLL.Checked = true;
                }
                    
                break;
            case Sextant: 
                //default :
                this.groupSextant.Visible = true;
                System.String __dummyScrutVar3 = ProcCur.Surf;
                if (__dummyScrutVar3.equals("1"))
                {
                    this.radioS1.Checked = true;
                }
                else if (__dummyScrutVar3.equals("2"))
                {
                    this.radioS2.Checked = true;
                }
                else if (__dummyScrutVar3.equals("3"))
                {
                    this.radioS3.Checked = true;
                }
                else if (__dummyScrutVar3.equals("4"))
                {
                    this.radioS4.Checked = true;
                }
                else if (__dummyScrutVar3.equals("5"))
                {
                    this.radioS5.Checked = true;
                }
                else if (__dummyScrutVar3.equals("6"))
                {
                    this.radioS6.Checked = true;
                }
                      
                break;
            case Arch: 
                //default:
                this.groupArch.Visible = true;
                System.String __dummyScrutVar4 = ProcCur.Surf;
                if (__dummyScrutVar4.equals("U"))
                {
                    this.radioU.Checked = true;
                }
                else if (__dummyScrutVar4.equals("L"))
                {
                    this.radioL.Checked = true;
                }
                  
                break;
            case ToothRange: 
                this.labelRange.Visible = true;
                this.listBoxTeeth.Visible = true;
                this.listBoxTeeth2.Visible = true;
                listBoxTeeth.SelectionMode = SelectionMode.MultiExtended;
                listBoxTeeth2.SelectionMode = SelectionMode.MultiExtended;
                if (ProcCur.ToothRange == null)
                {
                    break;
                }
                 
                String[] sArray = ProcCur.ToothRange.Split(',');
                //in american
                int idxAmer = new int();
                for (int i = 0;i < sArray.Length;i++)
                {
                    idxAmer = Array.IndexOf(Tooth.labelsUniversal, sArray[i]);
                    if (idxAmer == -1)
                    {
                        continue;
                    }
                     
                    if (idxAmer < 16)
                    {
                        listBoxTeeth.SetSelected(idxAmer, true);
                    }
                    else if (idxAmer < 32)
                    {
                        //ignore anything after 32.
                        listBoxTeeth2.SetSelected(idxAmer - 16, true);
                    }
                      
                }
                break;
        
        }
        /*for(int j=0;j<listBoxTeeth.Items.Count;j++)  {
                      if(Tooth.ToInternat(sArray[i])==listBoxTeeth.Items[j].ToString())
        				 		    listBoxTeeth.SelectedItem=Tooth.ToInternat(sArray[i]);
        					  }
          			    for(int j=0;j<listBoxTeeth2.Items.Count;j++)  {
                      if(Tooth.ToInternat(sArray[i])==listBoxTeeth2.Items[j].ToString())
        				 		    listBoxTeeth2.SelectedItem=Tooth.ToInternat(sArray[i]);
                    }*/
        //end switch
        textProcFee.Text = ProcCur.ProcFee.ToString("n");
    }

    //end SetControls
    private void fillReferral() throws Exception {
        List<RefAttach> refsList = RefAttaches.RefreshFiltered(ProcCur.PatNum, false, ProcCur.ProcNum);
        if (refsList.Count == 0)
        {
            textReferral.Text = "";
        }
        else
        {
            Referral referral = Referrals.GetReferral(refsList[0].ReferralNum);
            textReferral.Text = referral.LName + ", ";
            if (refsList[0].DateProcComplete.Year < 1880)
            {
                textReferral.Text += refsList[0].RefDate.ToShortDateString();
            }
            else
            {
                textReferral.Text += Lan.g(this,"done:") + refsList[0].DateProcComplete.ToShortDateString();
            } 
            if (refsList[0].RefToStatus != ReferralToStatus.None)
            {
                textReferral.Text += refsList[0].RefToStatus.ToString();
            }
             
        } 
    }

    private void butReferral_Click(Object sender, EventArgs e) throws Exception {
        FormReferralsPatient formRP = new FormReferralsPatient();
        formRP.PatNum = ProcCur.PatNum;
        formRP.ProcNum = ProcCur.ProcNum;
        formRP.ShowDialog();
        fillReferral();
    }

    private void fillIns() throws Exception {
        fillIns(true);
    }

    private void fillIns(boolean refreshClaimProcsFirst) throws Exception {
        if (refreshClaimProcsFirst)
        {
            //ClaimProcList=ClaimProcs.Refresh(PatCur.PatNum);
            //}
            ClaimProcsForProc = ClaimProcs.refreshForProc(ProcCur.ProcNum);
        }
         
        gridIns.beginUpdate();
        gridIns.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProcIns","Ins Plan"),190);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Pri/Sec"), 50, HorizontalAlignment.Center);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Status"), 50, HorizontalAlignment.Center);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","NoBill"), 45, HorizontalAlignment.Center);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Copay"), 55, HorizontalAlignment.Right);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Deduct"), 55, HorizontalAlignment.Right);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Percent"), 55, HorizontalAlignment.Center);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Ins Est"), 55, HorizontalAlignment.Right);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Ins Pay"), 55, HorizontalAlignment.Right);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","WriteOff"), 55, HorizontalAlignment.Right);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Estimate Note"),100);
        gridIns.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProcIns","Remarks"),165);
        gridIns.getColumns().add(col);
        gridIns.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        checkNoBillIns.CheckState = CheckState.Unchecked;
        boolean allNoBillIns = true;
        InsPlan plan;
        for (int i = 0;i < ClaimProcsForProc.Count;i++)
        {
            //ODGridCell cell;
            if (ClaimProcsForProc[i].NoBillIns)
            {
                checkNoBillIns.CheckState = CheckState.Indeterminate;
            }
            else
            {
                allNoBillIns = false;
            } 
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(InsPlans.GetDescript(ClaimProcsForProc[i].PlanNum, FamCur, PlanList, ClaimProcsForProc[i].InsSubNum, SubList));
            plan = InsPlans.GetPlan(ClaimProcsForProc[i].PlanNum, PlanList);
            if (plan.IsMedical)
            {
                row.getCells().add("Med");
            }
            else if (ClaimProcsForProc[i].InsSubNum == PatPlans.getInsSubNum(PatPlanList,PatPlans.getOrdinal(PriSecMed.Primary,PatPlanList,PlanList,SubList)))
            {
                row.getCells().add("Pri");
            }
            else if (ClaimProcsForProc[i].InsSubNum == PatPlans.getInsSubNum(PatPlanList,PatPlans.getOrdinal(PriSecMed.Secondary,PatPlanList,PlanList,SubList)))
            {
                row.getCells().add("Sec");
            }
            else
            {
                row.getCells().add("");
            }   
            Status __dummyScrutVar5 = ClaimProcsForProc[i].Status;
            if (__dummyScrutVar5.equals(ClaimProcStatus.Received))
            {
                row.getCells().add("Recd");
            }
            else if (__dummyScrutVar5.equals(ClaimProcStatus.NotReceived))
            {
                row.getCells().add("NotRec");
            }
            else //adjustment would never show here
            if (__dummyScrutVar5.equals(ClaimProcStatus.Preauth))
            {
                row.getCells().add("PreA");
            }
            else if (__dummyScrutVar5.equals(ClaimProcStatus.Supplemental))
            {
                row.getCells().add("Supp");
            }
            else if (__dummyScrutVar5.equals(ClaimProcStatus.CapClaim))
            {
                row.getCells().add("CapClaim");
            }
            else if (__dummyScrutVar5.equals(ClaimProcStatus.Estimate))
            {
                row.getCells().add("Est");
            }
            else if (__dummyScrutVar5.equals(ClaimProcStatus.CapEstimate))
            {
                row.getCells().add("CapEst");
            }
            else if (__dummyScrutVar5.equals(ClaimProcStatus.CapComplete))
            {
                row.getCells().add("CapComp");
            }
            else
            {
                row.getCells().add("");
            }        
            if (ClaimProcsForProc[i].NoBillIns)
            {
                row.getCells().add("X");
                if (ClaimProcsForProc[i].Status != ClaimProcStatus.CapComplete && ClaimProcsForProc[i].Status != ClaimProcStatus.CapEstimate)
                {
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    row.getCells().add("");
                    gridIns.getRows().add(row);
                    continue;
                }
                 
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(ClaimProcs.GetCopayDisplay(ClaimProcsForProc[i]));
            double ded = ClaimProcs.GetDeductibleDisplay(ClaimProcsForProc[i]);
            if (ded > 0)
            {
                row.getCells().Add(ded.ToString("n"));
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(ClaimProcs.GetPercentageDisplay(ClaimProcsForProc[i]));
            row.getCells().Add(ClaimProcs.GetEstimateDisplay(ClaimProcsForProc[i]));
            if (ClaimProcsForProc[i].Status == ClaimProcStatus.Estimate || ClaimProcsForProc[i].Status == ClaimProcStatus.CapEstimate)
            {
                row.getCells().add("");
                row.getCells().Add(ClaimProcs.GetWriteOffEstimateDisplay(ClaimProcsForProc[i]));
            }
            else
            {
                row.getCells().Add(ClaimProcsForProc[i].InsPayAmt.ToString("n"));
                row.getCells().Add(ClaimProcsForProc[i].WriteOff.ToString("n"));
            } 
            row.getCells().Add(ClaimProcsForProc[i].EstimateNote);
            row.getCells().Add(ClaimProcsForProc[i].Remarks);
            gridIns.getRows().add(row);
        }
        gridIns.endUpdate();
        if (ClaimProcsForProc.Count == 0)
        {
            checkNoBillIns.CheckState = CheckState.Unchecked;
        }
        else if (allNoBillIns)
        {
            checkNoBillIns.CheckState = CheckState.Checked;
        }
          
    }

    private void gridIns_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        RefSupport refVar___0 = new RefSupport(LoopList);
        FormClaimProc FormC = new FormClaimProc(ClaimProcsForProc[e.getRow()], ProcCur, FamCur, PatCur, PlanList, HistList, refVar___0, PatPlanList, true, SubList);
        LoopList = refVar___0.getValue();
        if (!butOK.Enabled)
        {
            FormC.NoPermissionProc = true;
        }
         
        FormC.ShowDialog();
        fillIns();
    }

    void butNow_Click(Object sender, EventArgs e) throws Exception {
        if (StringSupport.equals(textTimeStart.Text.Trim(), ""))
        {
            textTimeStart.Text = MiscData.getNowDateTime().ToShortTimeString();
        }
        else
        {
            textTimeEnd.Text = MiscData.getNowDateTime().ToShortTimeString();
        } 
    }

    private void butAddEstimate_Click(Object sender, System.EventArgs e) throws Exception {
        FormInsPlanSelect FormIS = new FormInsPlanSelect(PatCur.PatNum);
        FormIS.ShowDialog();
        if (FormIS.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        InsPlan plan = FormIS.SelectedPlan;
        InsSub sub = FormIS.SelectedSub;
        List<Benefit> benList = Benefits.refresh(PatPlanList,SubList);
        ClaimProc cp = new ClaimProc();
        ClaimProcs.createEst(cp,ProcCur,plan,sub);
        if (StringSupport.equals(plan.PlanType, "c"))
        {
            //capitation
            double allowed = PIn.Double(textProcFee.Text);
            cp.BaseEst = allowed;
            cp.InsEstTotal = allowed;
            cp.CopayAmt = InsPlans.getCopay(ProcCur.CodeNum,plan.FeeSched,plan.CopayFeeSched,plan.CodeSubstNone,ProcCur.ToothNum);
            if (cp.CopayAmt > allowed)
            {
                //if the copay is greater than the allowed fee calculated above
                cp.CopayAmt = allowed;
            }
             
            //reduce the copay
            if (cp.CopayAmt == -1)
            {
                cp.CopayAmt = 0;
            }
             
            cp.WriteOffEst = cp.BaseEst - cp.CopayAmt;
            if (cp.WriteOffEst < 0)
            {
                cp.WriteOffEst = 0;
            }
             
            cp.WriteOff = cp.WriteOffEst;
            ClaimProcs.update(cp);
        }
         
        long patPlanNum = PatPlans.getPatPlanNum(sub.InsSubNum,PatPlanList);
        if (patPlanNum > 0)
        {
            double paidOtherInsTotal = ClaimProcs.getPaidOtherInsTotal(cp,PatPlanList);
            double writeOffOtherIns = ClaimProcs.getWriteOffOtherIns(cp,PatPlanList);
            ClaimProcs.ComputeBaseEst(cp, ProcCur.ProcFee, ProcCur.ToothNum, ProcCur.CodeNum, plan, patPlanNum, benList, HistList, LoopList, PatPlanList, paidOtherInsTotal, paidOtherInsTotal, PatCur.getAge(), writeOffOtherIns);
        }
         
        RefSupport refVar___1 = new RefSupport(LoopList);
        FormClaimProc FormC = new FormClaimProc(cp,ProcCur,FamCur,PatCur,PlanList,HistList,refVar___1,PatPlanList,true,SubList);
        LoopList = refVar___1.getValue();
        //FormC.NoPermission not needed because butAddEstimate not enabled
        FormC.ShowDialog();
        if (FormC.DialogResult == DialogResult.Cancel)
        {
            ClaimProcs.delete(cp);
        }
         
        fillIns();
    }

    private void fillPayments() throws Exception {
        PaySplit[] PaySplitList = PaySplits.refresh(ProcCur.PatNum);
        PaySplitsForProc = PaySplits.GetForProc(ProcCur.ProcNum, PaySplitList);
        List<long> payNums = new List<long>();
        for (int i = 0;i < PaySplitsForProc.Count;i++)
        {
            //[];
            payNums.Add(((PaySplit)PaySplitsForProc[i]).PayNum);
        }
        PaymentsForProc = Payments.GetPayments(payNums);
        tbPay.ResetRows(PaySplitsForProc.Count);
        Payment PaymentCur;
        for (int i = 0;i < PaySplitsForProc.Count;i++)
        {
            //used in loop
            tbPay.Cell[0, i] = ((PaySplit)PaySplitsForProc[i]).DatePay.ToShortDateString();
            tbPay.Cell[1, i] = ((PaySplit)PaySplitsForProc[i]).SplitAmt.ToString("F");
            tbPay.FontBold[1, i] = true;
            PaymentCur = Payments.getFromList(((PaySplit)PaySplitsForProc[i]).PayNum,PaymentsForProc);
            tbPay.Cell[2, i] = PaymentCur.PayAmt.ToString("F");
            tbPay.Cell[3, i] = PaymentCur.PayNote;
        }
        tbPay.SetGridColor(Color.LightGray);
        tbPay.layoutTables();
    }

    private void tbPay_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        Payment PaymentCur = Payments.getFromList(((PaySplit)PaySplitsForProc[e.getRow()]).PayNum,PaymentsForProc);
        FormPayment FormP = new FormPayment(PatCur,FamCur,PaymentCur);
        FormP.InitialPaySplit = ((PaySplit)PaySplitsForProc[e.getRow()]).SplitNum;
        FormP.ShowDialog();
        fillPayments();
    }

    private void fillAdj() throws Exception {
        Adjustment[] AdjustmentList = Adjustments.refresh(ProcCur.PatNum);
        AdjustmentsForProc = Adjustments.GetForProc(ProcCur.ProcNum, AdjustmentList);
        tbAdj.ResetRows(AdjustmentsForProc.Count);
        for (int i = 0;i < AdjustmentsForProc.Count;i++)
        {
            tbAdj.Cell[0, i] = ((Adjustment)AdjustmentsForProc[i]).AdjDate.ToShortDateString();
            tbAdj.Cell[1, i] = ((Adjustment)AdjustmentsForProc[i]).AdjAmt.ToString("F");
            tbAdj.FontBold[1, i] = true;
            tbAdj.Cell[2, i] = DefC.getName(DefCat.AdjTypes,((Adjustment)AdjustmentsForProc[i]).AdjType);
            tbAdj.Cell[3, i] = ((Adjustment)AdjustmentsForProc[i]).AdjNote;
        }
        tbAdj.SetGridColor(Color.LightGray);
        tbAdj.layoutTables();
    }

    private void butAddAdjust_Click(Object sender, System.EventArgs e) throws Exception {
        if (ProcCur.ProcStatus != ProcStat.C)
        {
            MsgBox.show(this,"Adjustments may only be added to completed procedures.");
            return ;
        }
         
        Adjustment adj = new Adjustment();
        adj.PatNum = PatCur.PatNum;
        adj.ProvNum = ProcCur.ProvNum;
        adj.DateEntry = DateTime.Today;
        //but will get overwritten to server date
        adj.AdjDate = DateTime.Today;
        adj.ProcDate = ProcCur.ProcDate;
        adj.ProcNum = ProcCur.ProcNum;
        adj.ClinicNum = ProcCur.ClinicNum;
        FormAdjust FormA = new FormAdjust(PatCur,adj);
        FormA.IsNew = true;
        FormA.ShowDialog();
        fillAdj();
    }

    private void tbAdj_CellDoubleClicked(Object sender, OpenDental.CellEventArgs e) throws Exception {
        FormAdjust FormA = new FormAdjust(PatCur,(Adjustment)AdjustmentsForProc[e.getRow()]);
        FormA.ShowDialog();
        fillAdj();
    }

    private void textProcFee_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (!StringSupport.equals(textProcFee.errorProvider1.GetError(textProcFee), ""))
        {
            return ;
        }
         
        double procFee = new double();
        if (StringSupport.equals(textProcFee.Text, ""))
        {
            procFee = 0;
        }
        else
        {
            procFee = PIn.Double(textProcFee.Text);
        } 
        if (ProcCur.ProcFee == procFee)
        {
            return ;
        }
         
        ProcCur.ProcFee = procFee;
        Procedures.computeEstimates(ProcCur,PatCur.PatNum,ClaimProcsForProc,false,PlanList,PatPlanList,BenefitList,PatCur.getAge(),SubList);
        fillIns();
    }

    private void textTooth_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        textTooth.Text = textTooth.Text.ToUpper();
        if (!Tooth.IsValidEntry(textTooth.Text))
            errorProvider2.SetError(textTooth, Lan.g(this,"Invalid tooth number."));
        else
            errorProvider2.SetError(textTooth, ""); 
    }

    private void textSurfaces_TextChanged(Object sender, System.EventArgs e) throws Exception {
        int cursorPos = textSurfaces.SelectionStart;
        textSurfaces.Text = textSurfaces.Text.ToUpper();
        textSurfaces.SelectionStart = cursorPos;
    }

    private void textSurfaces_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (Tooth.IsValidEntry(textTooth.Text))
        {
            textSurfaces.Text = Tooth.SurfTidyForDisplay(textSurfaces.Text, Tooth.FromInternat(textTooth.Text));
        }
        else
        {
            textSurfaces.Text = Tooth.SurfTidyForDisplay(textSurfaces.Text, "");
        } 
        if (StringSupport.equals(textSurfaces.Text, ""))
            errorProvider2.SetError(textSurfaces, "No surfaces selected.");
        else
            errorProvider2.SetError(textSurfaces, ""); 
    }

    private void listBoxTeeth2_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        listBoxTeeth.SelectedIndex = -1;
    }

    private void listBoxTeeth_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        listBoxTeeth2.SelectedIndex = -1;
    }

    private void butChange_Click(Object sender, System.EventArgs e) throws Exception {
        FormProcCodes FormP = new FormProcCodes();
        FormP.IsSelectionMode = true;
        FormP.ShowDialog();
        if (FormP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ProcedureCode procCodeOld = ProcedureCodes.getProcCode(ProcCur.CodeNum);
        ProcedureCode procCodeNew = ProcedureCodes.getProcCode(FormP.SelectedCodeNum);
        if (procCodeOld.TreatArea != procCodeNew.TreatArea)
        {
            MsgBox.show(this,"Not allowed due to treatment area mismatch.");
            return ;
        }
         
        ProcCur.CodeNum = FormP.SelectedCodeNum;
        ProcedureCode2 = ProcedureCodes.getProcCode(FormP.SelectedCodeNum);
        textDesc.Text = ProcedureCode2.Descript;
        long priSubNum = PatPlans.getInsSubNum(PatPlanList,1);
        InsSub prisub = InsSubs.getSub(priSubNum,SubList);
        //can handle an inssubnum=0
        //long priPlanNum=PatPlans.GetPlanNum(PatPlanList,1);
        InsPlan priplan = InsPlans.getPlan(prisub.PlanNum,PlanList);
        //can handle a plannum=0
        double insfee = Fees.getAmount0(ProcCur.CodeNum,Fees.getFeeSched(PatCur,PlanList,PatPlanList,SubList));
        if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
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
        switch(ProcedureCode2.TreatArea)
        {
            case Quad: 
                ProcCur.Surf = "UR";
                radioUR.Checked = true;
                break;
            case Sextant: 
                ProcCur.Surf = "1";
                radioS1.Checked = true;
                break;
            case Arch: 
                ProcCur.Surf = "U";
                radioU.Checked = true;
                break;
        
        }
        for (int i = 0;i < ClaimProcsForProc.Count;i++)
        {
            if (ClaimProcsForProc[i].ClaimPaymentNum != 0)
            {
                continue;
            }
             
            //this shouldn't be possible, but it's a good check to make.
            ClaimProcs.Delete(ClaimProcsForProc[i]);
        }
        //that way, completely new ones will be added back, and NoBillIns will be accurate.
        ClaimProcsForProc = new List<ClaimProc>();
        Procedures.computeEstimates(ProcCur,PatCur.PatNum,ClaimProcsForProc,false,PlanList,PatPlanList,BenefitList,PatCur.getAge(),SubList);
        fillIns();
        setControlsUpperLeft();
    }

    private void butEditAnyway_Click(Object sender, System.EventArgs e) throws Exception {
        DateTime dateOldestClaim = Procedures.getOldestClaimDate(ProcCur.ProcNum,ClaimProcsForProc);
        if (!Security.isAuthorized(Permissions.ClaimSentEdit,dateOldestClaim))
        {
            return ;
        }
         
        panel1.Enabled = true;
        comboProcStatus.Enabled = true;
        checkNoBillIns.Enabled = true;
        butDelete.Enabled = true;
    }

    //butChange.Enabled=true;//No. We no longer allow this because part of "change" is to delete all the claimprocs.  This is a terrible idea for a completed proc attached to a claim.
    //checkIsCovIns.Enabled=true;
    private void listProcStatus_Click(Object sender, EventArgs e) throws Exception {
    }

    private void comboProcStatus_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboProcStatus.SelectedIndex == 0)
        {
            //fee starts out 0 if EO, EC, etc.  This updates fee if changing to TP so it won't stay 0.
            ProcCur.ProcStatus = ProcStat.TP;
            if (ProcCur.ProcFee == 0)
            {
                double insfee = 0;
                boolean isMed = false;
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
                InsPlan priplan = null;
                InsSub prisub = null;
                if (PatPlanList.Count > 0)
                {
                    prisub = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
                    priplan = InsPlans.getPlan(prisub.PlanNum,PlanList);
                }
                 
                if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
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
                textProcFee.Text = ProcCur.ProcFee.ToString("f");
            }
             
        }
         
        if (comboProcStatus.SelectedIndex == 1)
        {
            //C
            if (!Security.isAuthorized(Permissions.ProcComplCreate))
            {
                //set it back to whatever it was before
                if (ProcCur.ProcStatus == ProcStat.TP)
                {
                    comboProcStatus.SelectedIndex = 0;
                }
                else if (PrefC.getBool(PrefName.EasyHideClinical))
                {
                    comboProcStatus.SelectedIndex = -1;
                }
                else
                {
                    //original status must not be visible
                    if (ProcCur.ProcStatus == ProcStat.EO)
                    {
                        comboProcStatus.SelectedIndex = 2;
                    }
                     
                    if (ProcCur.ProcStatus == ProcStat.EC)
                    {
                        comboProcStatus.SelectedIndex = 3;
                    }
                     
                    if (ProcCur.ProcStatus == ProcStat.R)
                    {
                        comboProcStatus.SelectedIndex = 4;
                    }
                     
                    if (ProcCur.ProcStatus == ProcStat.Cn)
                    {
                        comboProcStatus.SelectedIndex = 5;
                    }
                     
                }  
                return ;
            }
             
            Procedures.setDateFirstVisit(DateTimeOD.getToday(),2,PatCur);
            ProcCur.ProcStatus = ProcStat.C;
        }
         
        if (comboProcStatus.SelectedIndex == 2)
        {
            ProcCur.ProcStatus = ProcStat.EC;
        }
         
        if (comboProcStatus.SelectedIndex == 3)
        {
            ProcCur.ProcStatus = ProcStat.EO;
        }
         
        if (comboProcStatus.SelectedIndex == 4)
        {
            ProcCur.ProcStatus = ProcStat.R;
        }
         
        if (comboProcStatus.SelectedIndex == 5)
        {
            ProcCur.ProcStatus = ProcStat.Cn;
        }
         
        //If it's already locked, there's simply no way to save the changes made to this control.
        //If status was just changed to C, then we should show the lock button.
        if (ProcCur.ProcStatus == ProcStat.C)
        {
            if (PrefC.getBool(PrefName.ProcLockingIsAllowed) && !ProcCur.IsLocked)
            {
                butLock.Visible = true;
            }
             
        }
         
    }

    private void butSetComplete_Click(Object sender, System.EventArgs e) throws Exception {
        //can't get to here if attached to a claim, even if use the Edit Anyway button.
        if (ProcOld.ProcStatus == ProcStat.C)
        {
            MsgBox.show(this,"Procedure was already set complete.");
            return ;
        }
         
        if (!Security.isAuthorized(Permissions.ProcComplCreate))
        {
            return ;
        }
         
        //If user is trying to change status to complete and using eCW.
        if ((IsNew || ProcOld.ProcStatus != ProcStat.C) && Programs.usingEcwTightOrFullMode())
        {
            MsgBox.show(this,"Procedures cannot be set complete in this window.  Set the procedure complete by setting the appointment complete.");
            return ;
        }
         
        Procedures.setDateFirstVisit(DateTimeOD.getToday(),2,PatCur);
        if (ProcCur.AptNum != 0)
        {
            //if attached to an appointment
            Appointment apt = Appointments.getOneApt(ProcCur.AptNum);
            if (apt.AptDateTime.Date > MiscData.getNowDateTime().Date)
            {
                //if appointment is in the future
                MessageBox.Show(Lan.g(this,"Not allowed because procedure is attached to a future appointment with a date of ") + apt.AptDateTime.ToShortDateString());
                return ;
            }
             
            textDate.Text = apt.AptDateTime.ToShortDateString();
        }
        else
        {
            textDate.Text = DateTime.Today.ToShortDateString();
        } 
        if (ProcedureCode2.PaintType == ToothPaintingType.Extraction)
        {
            //if an extraction, then mark previous procs hidden
            //Procedures.SetHideGraphical(ProcCur);//might not matter anymore
            ToothInitials.setValue(ProcCur.PatNum,ProcCur.ToothNum,ToothInitialType.Missing);
        }
         
        //long provNum=ProviderC.List[comboProvNum.SelectedIndex].ProvNum;
        long provNum = ProcCur.ProvNum;
        textNotes.Text += ProcCodeNotes.getNote(provNum,ProcCur.CodeNum);
        Plugins.hookAddCode(this,"FormProcEdit.butSetComplete_Click_end",ProcCur,ProcOld,textNotes);
        comboProcStatus.SelectedIndex = -1;
        //radioStatusC.Checked=true;
        ProcCur.ProcStatus = ProcStat.C;
        ProcCur.SiteNum = PatCur.SiteNum;
        comboPlaceService.SelectedIndex = PrefC.getInt(PrefName.DefaultProcedurePlaceService);
        if (entriesAreValid())
        {
            saveAndClose();
        }
         
    }

    private void radioUR_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "UR";
    }

    private void radioUL_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "UL";
    }

    private void radioLR_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "LR";
    }

    private void radioLL_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "LL";
    }

    private void radioU_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "U";
    }

    private void radioL_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "L";
    }

    private void radioS1_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "1";
    }

    private void radioS2_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "2";
    }

    private void radioS3_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "3";
    }

    private void radioS4_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "4";
    }

    private void radioS5_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "5";
    }

    private void radioS6_Click(Object sender, System.EventArgs e) throws Exception {
        ProcCur.Surf = "6";
    }

    private void checkNoBillIns_Click(Object sender, System.EventArgs e) throws Exception {
        if (checkNoBillIns.CheckState == CheckState.Indeterminate)
        {
            //not allowed to set to indeterminate, so move on
            checkNoBillIns.CheckState = CheckState.Unchecked;
        }
         
        for (int i = 0;i < ClaimProcsForProc.Count;i++)
        {
            //ignore CapClaim,NotReceived,PreAuth,Recieved,Supplemental
            if (ClaimProcsForProc[i].Status == ClaimProcStatus.Estimate || ClaimProcsForProc[i].Status == ClaimProcStatus.CapComplete || ClaimProcsForProc[i].Status == ClaimProcStatus.CapEstimate)
            {
                if (checkNoBillIns.CheckState == CheckState.Checked)
                {
                    ClaimProcsForProc[i].NoBillIns = true;
                }
                else
                {
                    //unchecked
                    ClaimProcsForProc[i].NoBillIns = false;
                } 
                ClaimProcs.Update(ClaimProcsForProc[i]);
            }
             
        }
        //next line is needed to recalc BaseEst, etc, for claimprocs that are no longer NoBillIns
        //also, if they are NoBillIns, then it clears out the other values.
        Procedures.computeEstimates(ProcCur,PatCur.PatNum,ClaimProcsForProc,false,PlanList,PatPlanList,BenefitList,PatCur.getAge(),SubList);
        fillIns();
    }

    private void textNotes_TextChanged(Object sender, System.EventArgs e) throws Exception {
        checkForCompleteNote();
        //so this happens only if user changes the note
        if (!IsStartingUp && !SigChanged)
        {
            //and the original signature is still showing.
            sigBox.clearTablet();
            //if(allowTopaz){
            CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
            sigBoxTopaz.Visible = false;
            //until user explicitly starts it.
            //}
            sigBox.setTabletState(1);
            //on-screen box is now accepting input.
            SigChanged = true;
            ProcCur.UserNum = Security.getCurUser().UserNum;
            textUser.Text = Userods.getName(ProcCur.UserNum);
        }
         
    }

    private void checkForCompleteNote() throws Exception {
        if (textNotes.Text.IndexOf("\"\"") == -1)
        {
            //no occurances of ""
            labelIncomplete.Visible = false;
        }
        else
        {
            labelIncomplete.Visible = true;
        } 
    }

    private void butSearch_Click(Object sender, EventArgs e) throws Exception {
        InputBox input = new InputBox(Lan.g(this,"Search for"));
        input.ShowDialog();
        if (input.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        String searchText = input.textResult.Text;
        int index = textNotes.Find(input.textResult.Text);
        //Gets the location of the first character in the control.
        if (index < 0)
        {
            //-1 is returned when the text is not found.
            textNotes.DeselectAll();
            MessageBox.Show("\"" + searchText + "\"\r\n" + Lan.g(this,"was not found in the notes") + ".");
            return ;
        }
         
        textNotes.Select(index, searchText.Length);
    }

    private void butClearSig_Click(Object sender, EventArgs e) throws Exception {
        sigBox.clearTablet();
        sigBox.Visible = true;
        //if(allowTopaz) {
        CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
        sigBoxTopaz.Visible = false;
        //until user explicitly starts it.
        //}
        sigBox.setTabletState(1);
        //on-screen box is now accepting input.
        SigChanged = true;
        labelInvalidSig.Visible = false;
        ProcCur.UserNum = Security.getCurUser().UserNum;
        textUser.Text = Userods.getName(ProcCur.UserNum);
    }

    private void butTopazSign_Click(Object sender, EventArgs e) throws Exception {
        sigBox.Visible = false;
        sigBoxTopaz.Visible = true;
        //if(allowTopaz){
        CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
        CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
        CodeBase.TopazWrapper.setTopazState(sigBoxTopaz,1);
        //}
        SigChanged = true;
        labelInvalidSig.Visible = false;
        ProcCur.UserNum = Security.getCurUser().UserNum;
        textUser.Text = Userods.getName(ProcCur.UserNum);
    }

    private void sigBox_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        //this is done on mouse up so that the initial pen capture won't be delayed.
        //if accepting input.
        if (sigBox.getTabletState() == 1 && !SigChanged)
        {
            //and sig not changed yet
            //sigBox handles its own pen input.
            SigChanged = true;
            ProcCur.UserNum = Security.getCurUser().UserNum;
            textUser.Text = Userods.getName(ProcCur.UserNum);
        }
         
    }

    private void buttonUseAutoNote_Click(Object sender, EventArgs e) throws Exception {
        FormAutoNoteCompose FormA = new FormAutoNoteCompose();
        FormA.ShowDialog();
        if (FormA.DialogResult == DialogResult.OK)
        {
            textNotes.AppendText(FormA.CompletedNote);
        }
         
    }

    /*private void butShowMedical_Click(object sender,EventArgs e) {
    			if(groupMedical.Height<200) {
    				groupMedical.Height=200;
    				butShowMedical.Text="^";
    			}
    			else {
    				groupMedical.Height=170;
    				butShowMedical.Text="V";
    			}
    		}*/
    private void comboDPC_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        DateTime tempDate = PIn.Date(textDateTP.Text);
        SelectedIndex __dummyScrutVar7 = comboDPC.SelectedIndex;
        if (__dummyScrutVar7.equals(2))
        {
            tempDate = tempDate.Date.AddDays(1);
            if (CancelledScheduleByDate.Year > 1880 && CancelledScheduleByDate < tempDate)
            {
                tempDate = CancelledScheduleByDate;
            }
             
        }
        else if (__dummyScrutVar7.equals(3))
        {
            tempDate = tempDate.Date.AddDays(30);
            if (CancelledScheduleByDate.Year > 1880 && CancelledScheduleByDate < tempDate)
            {
                tempDate = CancelledScheduleByDate;
            }
             
        }
        else if (__dummyScrutVar7.equals(4))
        {
            tempDate = tempDate.Date.AddDays(60);
            if (CancelledScheduleByDate.Year > 1880 && CancelledScheduleByDate < tempDate)
            {
                tempDate = CancelledScheduleByDate;
            }
             
        }
        else if (__dummyScrutVar7.equals(5))
        {
            tempDate = tempDate.Date.AddDays(120);
            if (CancelledScheduleByDate.Year > 1880 && CancelledScheduleByDate < tempDate)
            {
                tempDate = CancelledScheduleByDate;
            }
             
        }
        else if (__dummyScrutVar7.equals(6))
        {
            tempDate = tempDate.Date.AddYears(1);
            if (CancelledScheduleByDate.Year > 1880 && CancelledScheduleByDate < tempDate)
            {
                tempDate = CancelledScheduleByDate;
            }
             
        }
             
        textDateScheduled.Text = tempDate.ToShortDateString();
        labelScheduleBy.Visible = false;
        if (comboDPC.SelectedIndex == 0 || comboDPC.SelectedIndex == 1 || comboDPC.SelectedIndex == 7 || comboDPC.SelectedIndex == 8)
        {
            textDateScheduled.Text = "";
            labelScheduleBy.Visible = true;
        }
         
    }

    private void comboStatus_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        SelectedIndex __dummyScrutVar8 = comboStatus.SelectedIndex;
        if (__dummyScrutVar8.equals(0))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(1))
        {
            if (!Security.isAuthorized(Permissions.ProcComplCreate))
            {
                //set it back to whatever it was before
                if (OrionProcCur.Status2 == OrionStatus.TP)
                {
                    comboStatus.SelectedIndex = 0;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.E)
                {
                    comboStatus.SelectedIndex = 2;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.R)
                {
                    comboStatus.SelectedIndex = 3;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.RO)
                {
                    comboStatus.SelectedIndex = 4;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.CS)
                {
                    comboStatus.SelectedIndex = 5;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.CR)
                {
                    comboStatus.SelectedIndex = 6;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.CA_Tx)
                {
                    comboStatus.SelectedIndex = 7;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.CA_EPRD)
                {
                    comboStatus.SelectedIndex = 8;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.CA_PD)
                {
                    comboStatus.SelectedIndex = 9;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.S)
                {
                    comboStatus.SelectedIndex = 10;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.ST)
                {
                    comboStatus.SelectedIndex = 11;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.W)
                {
                    comboStatus.SelectedIndex = 12;
                }
                 
                if (OrionProcCur.Status2 == OrionStatus.A)
                {
                    comboStatus.SelectedIndex = 13;
                }
                 
                return ;
            }
             
            comboProcStatus.SelectedIndex = 1;
            ProcCur.ProcStatus = ProcStat.C;
            textTimeStart.Text = MiscData.getNowDateTime().ToShortTimeString();
        }
        else if (__dummyScrutVar8.equals(2))
        {
            comboProcStatus.SelectedIndex = 3;
            ProcCur.ProcStatus = ProcStat.EO;
        }
        else if (__dummyScrutVar8.equals(3))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(4))
        {
            comboProcStatus.SelectedIndex = 4;
            ProcCur.ProcStatus = ProcStat.R;
        }
        else if (__dummyScrutVar8.equals(5))
        {
            comboProcStatus.SelectedIndex = 3;
            ProcCur.ProcStatus = ProcStat.EO;
        }
        else if (__dummyScrutVar8.equals(6))
        {
            comboProcStatus.SelectedIndex = 3;
            ProcCur.ProcStatus = ProcStat.EO;
        }
        else if (__dummyScrutVar8.equals(7))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(8))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(9))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(10))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(11))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
        else if (__dummyScrutVar8.equals(12))
        {
            comboProcStatus.SelectedIndex = 5;
            ProcCur.ProcStatus = ProcStat.Cn;
        }
        else if (__dummyScrutVar8.equals(13))
        {
            comboProcStatus.SelectedIndex = 0;
            ProcCur.ProcStatus = ProcStat.TP;
        }
                      
        OrionProcCur.Status2 = OrionStatus.values()[((int)(Math.Pow(2d, (double)(comboStatus.SelectedIndex))))];
        //Do not automatically set the stop clock date if status is set to treatment planned, existing, or watch.
        if (OrionProcCur.Status2 == OrionStatus.TP || OrionProcCur.Status2 == OrionStatus.E || OrionProcCur.Status2 == OrionStatus.W)
        {
            //Clear the stop the clock date if there was no stop the clock date defined in a previous edit. Therefore, for a new procedure, always clear.
            if (OrionProcCur.DateStopClock.Year < 1880)
            {
                textDateStop.Text = "";
            }
             
        }
        else
        {
            //Set the stop the clock date for all other statuses.
            //Use the previously set stop the clock date if one exists. Will never be true if this is a new procedure.
            if (OrionProcCur.DateStopClock.Year > 1880)
            {
                textDateStop.Text = OrionProcCur.DateStopClock.ToShortDateString();
            }
            else
            {
                //When the stop the clock date has not already been set, set to the ProcDate for the procedure.
                textDateStop.Text = textDate.Text.Trim();
            } 
        } 
    }

    private void textTimeStart_TextChanged(Object sender, EventArgs e) throws Exception {
        updateFinalMin();
    }

    private void textTimeEnd_TextChanged(Object sender, EventArgs e) throws Exception {
        updateFinalMin();
    }

    /**
    * Populates final time box with total number of minutes.
    */
    private void updateFinalMin() throws Exception {
        if (StringSupport.equals(textTimeStart.Text, "") || StringSupport.equals(textTimeEnd.Text, ""))
        {
            return ;
        }
         
        int startTime = 0;
        int stopTime = 0;
        try
        {
            startTime = PIn.Int(textTimeStart.Text);
        }
        catch (Exception __dummyCatchVar0)
        {
            try
            {
                //Try DateTime format.
                DateTime sTime = DateTime.Parse(textTimeStart.Text);
                startTime = (sTime.Hour * (int)Math.Pow(10, 2)) + sTime.Minute;
            }
            catch (Exception __dummyCatchVar1)
            {
                return ;
            }
        
        }

        try
        {
            //Not a valid time.
            stopTime = PIn.Int(textTimeEnd.Text);
        }
        catch (Exception __dummyCatchVar2)
        {
            try
            {
                //Try DateTime format.
                DateTime eTime = DateTime.Parse(textTimeEnd.Text);
                stopTime = (eTime.Hour * (int)Math.Pow(10, 2)) + eTime.Minute;
            }
            catch (Exception __dummyCatchVar3)
            {
                return ;
            }
        
        }

        //Not a valid time.
        int total = (((stopTime / 100) * 60) + (stopTime % 100)) - (((startTime / 100) * 60) + (startTime % 100));
        textTimeFinal.Text = total.ToString();
    }

    /**
    * Empty string is considered valid.
    */
    private boolean validateTime(String time) throws Exception {
        String militaryTime = time;
        if (StringSupport.equals(militaryTime, ""))
        {
            return true;
        }
         
        if (militaryTime.Length < 4)
        {
            militaryTime = militaryTime.PadLeft(4, '0');
        }
         
        try
        {
            //Test if user typed in military time. Ex: 0830 or 1536
            int hour = PIn.Int(militaryTime.Substring(0, 2));
            int minute = PIn.Int(militaryTime.Substring(2, 2));
            if (hour > 23)
            {
                return false;
            }
             
            if (minute > 59)
            {
                return false;
            }
             
            return true;
        }
        catch (Exception __dummyCatchVar4)
        {
        }

        try
        {
            //Test typical DateTime format. Ex: 1:00 PM
            DateTime.Parse(time);
            return true;
        }
        catch (Exception __dummyCatchVar5)
        {
            return false;
        }
    
    }

    /**
    * Returns min value if blank or invalid string passed in.
    */
    private DateTime parseTime(String time) throws Exception {
        String militaryTime = time;
        DateTime dTime = DateTime.MinValue;
        if (StringSupport.equals(militaryTime, ""))
        {
            return dTime;
        }
         
        if (militaryTime.Length < 4)
        {
            militaryTime = militaryTime.PadLeft(4, '0');
        }
         
        try
        {
            //Test if user typed in military time. Ex: 0830 or 1536
            int hour = PIn.Int(militaryTime.Substring(0, 2));
            int minute = PIn.Int(militaryTime.Substring(2, 2));
            dTime = new DateTime(1, 1, 1, hour, minute, 0);
            return dTime;
        }
        catch (Exception __dummyCatchVar6)
        {
        }

        try
        {
            return DateTime.Parse(time);
        }
        catch (Exception __dummyCatchVar7)
        {
        }

        return dTime;
    }

    //Test if user typed in a typical DateTime format. Ex: 1:00 PM
    private void updateSurf() throws Exception {
        if (!Tooth.IsValidEntry(textTooth.Text))
        {
            return ;
        }
         
        errorProvider2.SetError(textSurfaces, "");
        textSurfaces.Text = "";
        if (butM.BackColor == Color.White)
        {
            textSurfaces.AppendText("M");
        }
         
        if (butOI.BackColor == Color.White)
        {
            //if(ToothGraphic.IsAnterior(Tooth.FromInternat(textTooth.Text))) {
            if (Tooth.IsAnterior(Tooth.FromInternat(textTooth.Text)))
            {
                textSurfaces.AppendText("I");
            }
            else
            {
                textSurfaces.AppendText("O");
            } 
        }
         
        if (butD.BackColor == Color.White)
        {
            textSurfaces.AppendText("D");
        }
         
        if (butV.BackColor == Color.White)
        {
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                textSurfaces.AppendText("5");
            }
            else
            {
                textSurfaces.AppendText("V");
            } 
        }
         
        if (butBF.BackColor == Color.White)
        {
            //if(ToothGraphic.IsAnterior(Tooth.FromInternat(textTooth.Text))) {
            if (Tooth.IsAnterior(Tooth.FromInternat(textTooth.Text)))
            {
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    textSurfaces.AppendText("V");
                }
                else
                {
                    //vestibular
                    textSurfaces.AppendText("F");
                } 
            }
            else
            {
                textSurfaces.AppendText("B");
            } 
        }
         
        if (butL.BackColor == Color.White)
        {
            textSurfaces.AppendText("L");
        }
         
    }

    private void butM_Click(Object sender, EventArgs e) throws Exception {
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

    private void butOI_Click(Object sender, EventArgs e) throws Exception {
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

    private void butL_Click(Object sender, EventArgs e) throws Exception {
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

    private void butV_Click(Object sender, EventArgs e) throws Exception {
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

    private void butBF_Click(Object sender, EventArgs e) throws Exception {
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

    private void butD_Click(Object sender, EventArgs e) throws Exception {
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

    private void butPickSite_Click(Object sender, EventArgs e) throws Exception {
        FormSites FormS = new FormSites();
        FormS.IsSelectionMode = true;
        FormS.SelectedSiteNum = ProcCur.SiteNum;
        FormS.ShowDialog();
        if (FormS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ProcCur.SiteNum = FormS.SelectedSiteNum;
        textSite.Text = Sites.getDescription(ProcCur.SiteNum);
    }

    private void butPickProv_Click(Object sender, EventArgs e) throws Exception {
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
        ProcCur.ProvNum = formp.SelectedProvNum;
    }

    private void comboProvNum_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        ProcCur.ProvNum = ProviderC.getListShort()[comboProvNum.SelectedIndex].ProvNum;
        for (int i = 0;i < ClaimProcsForProc.Count;i++)
        {
            ClaimProcsForProc[i].ProvNum = ProcCur.ProvNum;
        }
    }

    /**
    * This button is only visible if 1. Pref ProcLockingIsAllowed is true, 2. Proc isn't already locked, 3. Proc status is C.
    */
    private void butLock_Click(Object sender, EventArgs e) throws Exception {
        if (!entriesAreValid())
        {
            return ;
        }
         
        ProcCur.IsLocked = true;
        saveAndClose();
        //saves all the other various changes that the user made
        DialogResult = DialogResult.OK;
    }

    /**
    * This button is only visible when proc IsLocked.
    */
    private void butInvalidate_Click(Object sender, EventArgs e) throws Exception {
        //What this will really do is "delete" the procedure.
        if (!Security.isAuthorized(Permissions.ProcDelete,ProcCur.DateEntryC))
        {
            return ;
        }
         
        if (Procedures.isAttachedToClaim(ProcCur,ClaimProcsForProc))
        {
            MsgBox.show(this,"This procedure is attached to a claim and cannot be invalidated without first deleting the claim.");
            return ;
        }
         
        try
        {
            Procedures.delete(ProcCur.ProcNum);
        }
        catch (Exception ex)
        {
            //also deletes any claimprocs (other than ins payments of course).
            MessageBox.Show(ex.Message);
            return ;
        }

        SecurityLogs.makeLogEntry(Permissions.ProcDelete,PatCur.PatNum,Lan.g(this,"Invalidated: ") + ProcedureCodes.getStringProcCode(ProcCur.CodeNum).ToString() + ", " + ProcCur.ProcDate.ToShortDateString() + ", " + ProcCur.ProcFee.ToString("c"));
        DialogResult = DialogResult.OK;
    }

    /**
    * This button is only visible when proc IsLocked.
    */
    private void butAppend_Click(Object sender, EventArgs e) throws Exception {
        FormProcNoteAppend formPNA = new FormProcNoteAppend();
        formPNA.ProcCur = ProcCur;
        formPNA.ShowDialog();
        if (formPNA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    //exit out of this window.  Change already saved, and OK button is disabled in this window, anyway.
    private void comboClinic_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        if (comboClinic.SelectedIndex == 0)
        {
            ProcCur.ClinicNum = 0;
        }
        else
        {
            ProcCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        } 
        for (int i = 0;i < ClaimProcsForProc.Count;i++)
        {
            ClaimProcsForProc[i].ClinicNum = ProcCur.ClinicNum;
        }
    }

    private void butSnomedBodySiteSelect_Click(Object sender, EventArgs e) throws Exception {
        FormSnomeds formS = new FormSnomeds();
        formS.IsSelectionMode = true;
        if (formS.ShowDialog() == DialogResult.OK)
        {
            _snomedBodySite = formS.SelectedSnomed;
            textSnomedBodySite.Text = _snomedBodySite.Description;
        }
         
    }

    private void butNoneSnomedBodySite_Click(Object sender, EventArgs e) throws Exception {
        _snomedBodySite = null;
        textSnomedBodySite.Text = "";
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //verified that this triggers a delete when window closed from all places where FormProcEdit is used, and where proc could be new.
        //If this is an existing completed proc, then this delete button is only enabled if the user has permission for ProcComplEdit based on the DateEntryC.
        if (!Security.isAuthorized(Permissions.ProcDelete,ProcCur.DateEntryC))
        {
            return ;
        }
         
        //This should be a much more lenient permission since completed procedures are already safeguarded.
        if (MessageBox.Show(Lan.g(this,"Delete Procedure?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
        {
            return ;
        }
         
        try
        {
            Procedures.delete(ProcCur.ProcNum);
            //also deletes the claimProcs and adjustments. Might throw exception.
            Recalls.synch(ProcCur.PatNum);
            //needs to be moved into Procedures.Delete
            SecurityLogs.makeLogEntry(Permissions.ProcDelete,ProcCur.PatNum,ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode + ", " + ProcCur.ProcFee.ToString("c"));
            DialogResult = DialogResult.OK;
            Plugins.hookAddCode(this,"FormProcEdit.butDelete_Click_end",ProcCur);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
        }
    
    }

    private boolean entriesAreValid() throws Exception {
        if (!StringSupport.equals(textDateTP.errorProvider1.GetError(textDateTP), "") || !StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textProcFee.errorProvider1.GetError(textProcFee), "") || !StringSupport.equals(textDateOriginalProsth.errorProvider1.GetError(textDateOriginalProsth), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return false;
        }
         
        if (StringSupport.equals(textDate.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"Please enter a date first."));
            return false;
        }
         
        //There have been 2 or 3 cases where a customer entered a note with thousands of new lines and when OD tries to display such a note in the chart, a GDI exception occurs because the progress notes grid is very tall and takes up too much video memory. To help prevent this issue, we block the user from entering any note where there are 50 or more consecutive new lines anywhere in the note. Any number of new lines less than 50 are considered to be intentional.
        StringBuilder tooManyNewLines = new StringBuilder();
        for (int i = 0;i < 50;i++)
        {
            tooManyNewLines.Append("\r\n");
        }
        if (textNotes.Text.Contains(tooManyNewLines.ToString()))
        {
            MsgBox.show(this,"The notes contain 50 or more consecutive blank lines. Probably unintentional and must be fixed.");
            return false;
        }
         
        if (Programs.getUsingOrion() || PrefC.getBool(PrefName.ShowFeatureMedicalInsurance))
        {
            if (!ValidateTime(textTimeStart.Text))
            {
                MessageBox.Show(Lan.g(this,"Start time is invalid."));
                return false;
            }
             
            if (!ValidateTime(textTimeEnd.Text))
            {
                MessageBox.Show(Lan.g(this,"End time is invalid."));
                return false;
            }
             
        }
        else
        {
            if (!StringSupport.equals(textTimeStart.Text, ""))
            {
                try
                {
                    DateTime.Parse(textTimeStart.Text);
                }
                catch (Exception __dummyCatchVar8)
                {
                    MessageBox.Show(Lan.g(this,"Start time is invalid."));
                    return false;
                }
            
            }
             
        } 
        try
        {
            int unitqty = int.Parse(textUnitQty.Text);
            if (unitqty < 1)
            {
                MsgBox.show(this,"Qty not valid.  Typical value is 1.");
                return false;
            }
             
        }
        catch (Exception __dummyCatchVar9)
        {
            MsgBox.show(this,"Qty not valid.  Typical value is 1.");
            return false;
        }

        if (ProcCur.ProvNum == 0)
        {
            //this works because ProvNum gets set when the user changes the combobox.
            MsgBox.show(this,"You must select a provider first.");
            return false;
        }
         
        if (!StringSupport.equals(errorProvider2.GetError(textSurfaces), "") || !StringSupport.equals(errorProvider2.GetError(textTooth), ""))
        {
            MsgBox.show(this,"Please fix tooth number or surfaces first.");
            return false;
        }
         
        if (!StringSupport.equals(textMedicalCode.Text, "") && !ProcedureCodeC.getHList().Contains(textMedicalCode.Text))
        {
            MsgBox.show(this,"Invalid medical code.  It must refer to an existing procedure code.");
            return false;
        }
         
        if (!StringSupport.equals(textDrugNDC.Text, ""))
        {
            if (comboDrugUnit.SelectedIndex == ((Enum)EnumProcDrugUnit.None).ordinal() || StringSupport.equals(textDrugQty.Text, ""))
            {
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Drug quantity and unit are not entered.  Continue anyway?"))
                {
                    return false;
                }
                 
            }
             
        }
         
        if (!StringSupport.equals(textDrugQty.Text, ""))
        {
            try
            {
                float.Parse(textDrugQty.Text);
            }
            catch (Exception __dummyCatchVar10)
            {
                MsgBox.show(this,"Please fix drug qty first.");
                return false;
            }
        
        }
         
        //If user is trying to change status to complete and using eCW.
        if (ProcCur.ProcStatus == ProcStat.C && (IsNew || ProcOld.ProcStatus != ProcStat.C) && Programs.usingEcwTightOrFullMode())
        {
            MsgBox.show(this,"Procedures cannot be set complete in this window.  Set the procedure complete by setting the appointment complete.");
            return false;
        }
         
        if (ProcOld.ProcStatus != ProcStat.C && ProcCur.ProcStatus == ProcStat.C)
        {
            //if status was changed to complete
            if (ProcCur.AptNum != 0)
            {
                //if attached to an appointment
                Appointment apt = Appointments.getOneApt(ProcCur.AptNum);
                if (apt.AptDateTime.Date > MiscData.getNowDateTime().Date)
                {
                    //if appointment is in the future
                    MessageBox.Show(Lan.g(this,"Not allowed because procedure is attached to a future appointment with a date of ") + apt.AptDateTime.ToShortDateString());
                    return false;
                }
                 
                textDate.Text = apt.AptDateTime.ToShortDateString();
            }
             
            if (!Security.IsAuthorized(Permissions.ProcComplCreate, PIn.Date(textDate.Text)))
            {
                return false;
            }
             
        }
        else //use the new date
        if (IsNew && ProcCur.ProcStatus == ProcStat.C)
        {
            //if new procedure is complete
            if (!Security.IsAuthorized(Permissions.ProcComplCreate, PIn.Date(textDate.Text)))
            {
                return false;
            }
             
        }
        else if (!IsNew)
        {
            //an old procedure
            if (ProcOld.ProcStatus == ProcStat.C)
            {
                //that was already complete
                //It's not possible for the user to get to this point unless they have permission for ProcComplEdit based on the DateEntryC.
                //The following 2 checks are not redundant because they check different dates.
                if (!Security.isAuthorized(Permissions.ProcComplEdit,ProcOld.ProcDate))
                {
                    return false;
                }
                 
                //block old date
                if (ProcCur.ProcStatus == ProcStat.C)
                {
                    //if it's still complete
                    if (!Security.IsAuthorized(Permissions.ProcComplEdit, PIn.Date(textDate.Text)))
                    {
                        return false;
                    }
                     
                }
                 
            }
             
        }
           
        //block new date, too
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            if (checkTypeCodeX.Checked)
            {
                if (checkTypeCodeA.Checked || checkTypeCodeB.Checked || checkTypeCodeC.Checked || checkTypeCodeE.Checked || checkTypeCodeL.Checked || checkTypeCodeS.Checked)
                {
                    MsgBox.show(this,"If type code 'none' is checked, no other type codes may be checked.");
                    return false;
                }
                 
            }
             
            if (ProcedureCode2.IsProsth && !checkTypeCodeA.Checked && !checkTypeCodeB.Checked && !checkTypeCodeC.Checked && !checkTypeCodeE.Checked && !checkTypeCodeL.Checked && !checkTypeCodeS.Checked && !checkTypeCodeX.Checked)
            {
                if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"At least one type code should be checked for prosthesis.  Continue anyway?"))
                {
                    return false;
                }
                 
            }
             
            if (!StringSupport.equals(textCanadaLabFee1.errorProvider1.GetError(textCanadaLabFee1), "") || !StringSupport.equals(textCanadaLabFee2.errorProvider1.GetError(textCanadaLabFee2), ""))
            {
                MessageBox.Show(Lan.g(this,"Please fix lab fees."));
                return false;
            }
             
        }
        else
        {
            if (ProcedureCode2.IsProsth)
            {
                if (listProsth.SelectedIndex == 0 || (listProsth.SelectedIndex == 2 && StringSupport.equals(textDateOriginalProsth.Text, "")))
                {
                    if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Prosthesis date not entered. Continue anyway?"))
                    {
                        return false;
                    }
                     
                }
                 
            }
             
        } 
        if (Programs.getUsingOrion())
        {
            //if(panelOrion.Visible) {
            if (comboStatus.SelectedIndex == -1)
            {
                MsgBox.show(this,"Invalid status.");
                return false;
            }
             
            if (!StringSupport.equals(textDateScheduled.errorProvider1.GetError(textDateScheduled), ""))
            {
                MsgBox.show(this,"Invalid schedule date.");
                return false;
            }
             
            if (!StringSupport.equals(textDateStop.errorProvider1.GetError(textDateStop), ""))
            {
                MsgBox.show(this,"Invalid stop clock date.");
                return false;
            }
             
        }
         
        if (ProcedureCode2.TreatArea == TreatmentArea.Quad)
        {
            if (!radioUL.Checked && !radioUR.Checked && !radioLL.Checked && !radioLR.Checked)
            {
                MsgBox.show(this,"Please select a quadrant.");
                return false;
            }
             
        }
         
        return true;
    }

    /**
    * MUST call EntriesAreValid first.  Used from OK_Click and from butSetComplete_Click
    */
    private void saveAndClose() throws Exception {
        if (StringSupport.equals(textProcFee.Text, ""))
        {
            textProcFee.Text = "0";
        }
         
        ProcCur.PatNum = PatCur.PatNum;
        //ProcCur.Code=this.textProc.Text;
        ProcedureCode2 = ProcedureCodes.GetProcCode(textProc.Text);
        ProcCur.CodeNum = ProcedureCode2.CodeNum;
        ProcCur.MedicalCode = textMedicalCode.Text;
        if (_snomedBodySite == null)
        {
            ProcCur.SnomedBodySite = "";
        }
        else
        {
            ProcCur.SnomedBodySite = _snomedBodySite.SnomedCode;
        } 
        ProcCur.DiagnosticCode = textDiagnosticCode.Text;
        ProcCur.IsPrincDiag = checkIsPrincDiag.Checked;
        ProcCur.CodeMod1 = textCodeMod1.Text;
        ProcCur.CodeMod2 = textCodeMod2.Text;
        ProcCur.CodeMod3 = textCodeMod3.Text;
        ProcCur.CodeMod4 = textCodeMod4.Text;
        ProcCur.UnitQty = PIn.Int(textUnitQty.Text);
        ProcCur.UnitQtyType = (ProcUnitQtyType)comboUnitType.SelectedIndex;
        ProcCur.RevCode = textRevCode.Text;
        ProcCur.DrugUnit = (EnumProcDrugUnit)comboDrugUnit.SelectedIndex;
        ProcCur.DrugQty = PIn.Float(textDrugQty.Text);
        if (ProcOld.ProcStatus != ProcStat.C && ProcCur.ProcStatus == ProcStat.C)
        {
            //Proc set complete.
            ProcCur.DateEntryC = DateTime.Now;
            //this triggers it to set to server time NOW().
            if (StringSupport.equals(ProcCur.DiagnosticCode, ""))
            {
                ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
            }
             
        }
         
        ProcCur.DateTP = PIn.Date(this.textDateTP.Text);
        ProcCur.ProcDate = PIn.Date(this.textDate.Text);
        DateTime dateT = PIn.DateT(this.textTimeStart.Text);
        ProcCur.ProcTime = new TimeSpan(dateT.Hour, dateT.Minute, 0);
        if (Programs.getUsingOrion() || PrefC.getBool(PrefName.ShowFeatureMedicalInsurance))
        {
            dateT = ParseTime(textTimeStart.Text);
            ProcCur.ProcTime = new TimeSpan(dateT.Hour, dateT.Minute, 0);
            dateT = ParseTime(textTimeEnd.Text);
            ProcCur.ProcTimeEnd = new TimeSpan(dateT.Hour, dateT.Minute, 0);
        }
         
        ProcCur.ProcFee = PIn.Double(textProcFee.Text);
        //ProcCur.LabFee=PIn.PDouble(textLabFee.Text);
        //ProcCur.LabProcCode=textLabCode.Text;
        //MessageBox.Show(ProcCur.ProcFee.ToString());
        //Dx taken care of when radio pushed
        switch(ProcedureCode2.TreatArea)
        {
            case Surf: 
                ProcCur.ToothNum = Tooth.FromInternat(textTooth.Text);
                ProcCur.Surf = Tooth.SurfTidyFromDisplayToDb(textSurfaces.Text, ProcCur.ToothNum);
                break;
            case Tooth: 
                ProcCur.Surf = "";
                ProcCur.ToothNum = Tooth.FromInternat(textTooth.Text);
                break;
            case Mouth: 
                ProcCur.Surf = "";
                ProcCur.ToothNum = "";
                break;
            case Quad: 
                //surf set when radio pushed
                ProcCur.ToothNum = "";
                break;
            case Sextant: 
                //surf taken care of when radio pushed
                ProcCur.ToothNum = "";
                break;
            case Arch: 
                //don't HAVE to select arch
                //taken care of when radio pushed
                ProcCur.ToothNum = "";
                break;
            case ToothRange: 
                if (listBoxTeeth.SelectedItems.Count < 1 && listBoxTeeth2.SelectedItems.Count < 1)
                {
                    MessageBox.Show(Lan.g(this,"Must pick at least 1 tooth"));
                    return ;
                }
                 
                String range = "";
                int idxAmer = new int();
                for (int j = 0;j < listBoxTeeth.SelectedIndices.Count;j++)
                {
                    idxAmer = listBoxTeeth.SelectedIndices[j];
                    if (j != 0)
                    {
                        range += ",";
                    }
                     
                    range += Tooth.labelsUniversal[idxAmer];
                }
                for (int j = 0;j < listBoxTeeth2.SelectedIndices.Count;j++)
                {
                    idxAmer = listBoxTeeth2.SelectedIndices[j] + 16;
                    if (j != 0)
                    {
                        range += ",";
                    }
                     
                    range += Tooth.labelsUniversal[idxAmer];
                }
                ProcCur.ToothRange = range;
                ProcCur.Surf = "";
                ProcCur.ToothNum = "";
                break;
        
        }
        //Status taken care of when list pushed
        ProcCur.Note = this.textNotes.Text;
        try
        {
            saveSignature();
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error saving signature.") + "\r\n" + ex.Message);
        }

        //and continue with the rest of this method
        ProcCur.HideGraphics = checkHideGraphics.Checked;
        //provnum already handled.
        //if(comboProvNum.SelectedIndex!=-1) {
        //	ProcCur.ProvNum=ProviderC.List[comboProvNum.SelectedIndex].ProvNum;
        //}
        //clinicNum already handled.
        if (comboDx.SelectedIndex != -1)
        {
            ProcCur.Dx = DefC.getShort()[((Enum)DefCat.Diagnosis).ordinal()][comboDx.SelectedIndex].DefNum;
        }
         
        if (comboPrognosis.SelectedIndex == 0)
        {
            ProcCur.Prognosis = 0;
        }
        else
        {
            ProcCur.Prognosis = DefC.getShort()[((Enum)DefCat.Prognosis).ordinal()][comboPrognosis.SelectedIndex - 1].DefNum;
        } 
        if (comboPriority.SelectedIndex == 0)
        {
            ProcCur.Priority = 0;
        }
        else
        {
            ProcCur.Priority = DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][comboPriority.SelectedIndex - 1].DefNum;
        } 
        ProcCur.PlaceService = (PlaceOfService)comboPlaceService.SelectedIndex;
        //if(comboClinic.SelectedIndex==0){
        //	ProcCur.ClinicNum=0;
        //}
        //else{
        //	ProcCur.ClinicNum=Clinics.List[comboClinic.SelectedIndex-1].ClinicNum;
        //}
        //site set when user picks from list.
        if (comboBillingTypeOne.SelectedIndex == 0)
        {
            ProcCur.BillingTypeOne = 0;
        }
        else
        {
            ProcCur.BillingTypeOne = DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][comboBillingTypeOne.SelectedIndex - 1].DefNum;
        } 
        if (comboBillingTypeTwo.SelectedIndex == 0)
        {
            ProcCur.BillingTypeTwo = 0;
        }
        else
        {
            ProcCur.BillingTypeTwo = DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][comboBillingTypeTwo.SelectedIndex - 1].DefNum;
        } 
        ProcCur.BillingNote = textBillingNote.Text;
        //ProcCur.HideGraphical=checkHideGraphical.Checked;
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            ProcCur.CanadianTypeCodes = "";
            if (checkTypeCodeA.Checked)
            {
                ProcCur.CanadianTypeCodes += "A";
            }
             
            if (checkTypeCodeB.Checked)
            {
                ProcCur.CanadianTypeCodes += "B";
            }
             
            if (checkTypeCodeC.Checked)
            {
                ProcCur.CanadianTypeCodes += "C";
            }
             
            if (checkTypeCodeE.Checked)
            {
                ProcCur.CanadianTypeCodes += "E";
            }
             
            if (checkTypeCodeL.Checked)
            {
                ProcCur.CanadianTypeCodes += "L";
            }
             
            if (checkTypeCodeS.Checked)
            {
                ProcCur.CanadianTypeCodes += "S";
            }
             
            if (checkTypeCodeX.Checked)
            {
                ProcCur.CanadianTypeCodes += "X";
            }
             
            double canadaLabFee1 = 0;
            if (!StringSupport.equals(textCanadaLabFee1.Text, ""))
            {
                canadaLabFee1 = PIn.Double(textCanadaLabFee1.Text);
            }
             
            if (canadaLabFee1 == 0)
            {
                if (textCanadaLabFee1.Visible && canadaLabFees.Count > 0)
                {
                    //Don't worry about deleting child lab fees if we are editing a lab fee. No such concept.
                    Procedures.Delete(canadaLabFees[0].ProcNum);
                }
                 
            }
            else
            {
                //canadaLabFee1!=0
                if (canadaLabFees.Count > 0)
                {
                    //Retain the old lab code if present.
                    Procedure labFee1Old = canadaLabFees[0].Copy();
                    canadaLabFees[0].ProcFee = canadaLabFee1;
                    Procedures.Update(canadaLabFees[0], labFee1Old);
                }
                else
                {
                    Procedure labFee1 = new Procedure();
                    labFee1.PatNum = ProcCur.PatNum;
                    labFee1.ProcDate = ProcCur.ProcDate;
                    labFee1.ProcFee = canadaLabFee1;
                    labFee1.ProcStatus = ProcCur.ProcStatus;
                    labFee1.ProvNum = ProcCur.ProvNum;
                    labFee1.DateEntryC = DateTime.Now;
                    labFee1.ClinicNum = ProcCur.ClinicNum;
                    labFee1.ProcNumLab = ProcCur.ProcNum;
                    labFee1.CodeNum = ProcedureCodes.getCodeNum("99111");
                    if (labFee1.CodeNum == 0)
                    {
                        //Code does not exist.
                        ProcedureCode code99111 = new ProcedureCode();
                        code99111.ProcCode = "99111";
                        code99111.Descript = "+L Commercial Laboratory Procedures";
                        code99111.AbbrDesc = "Lab Fee";
                        code99111.ProcCat = DefC.getByExactNameNeverZero(DefCat.ProcCodeCats,"Adjunctive General Services");
                        ProcedureCodes.insert(code99111);
                        labFee1.CodeNum = code99111.CodeNum;
                    }
                     
                    Procedures.insert(labFee1);
                } 
            } 
            double canadaLabFee2 = 0;
            if (!StringSupport.equals(textCanadaLabFee2.Text, ""))
            {
                canadaLabFee2 = PIn.Double(textCanadaLabFee2.Text);
            }
             
            if (canadaLabFee2 == 0)
            {
                if (textCanadaLabFee2.Visible && canadaLabFees.Count > 1)
                {
                    //Don't worry about deleting child lab fees if we are editing a lab fee. No such concept.
                    Procedures.Delete(canadaLabFees[1].ProcNum);
                }
                 
            }
            else
            {
                //canadaLabFee2!=0
                if (canadaLabFees.Count > 1)
                {
                    //Retain the old lab code if present.
                    Procedure labFee2Old = canadaLabFees[1].Copy();
                    canadaLabFees[1].ProcFee = canadaLabFee2;
                    Procedures.Update(canadaLabFees[1], labFee2Old);
                }
                else
                {
                    Procedure labFee2 = new Procedure();
                    labFee2.PatNum = ProcCur.PatNum;
                    labFee2.ProcDate = ProcCur.ProcDate;
                    labFee2.ProcFee = canadaLabFee2;
                    labFee2.ProcStatus = ProcCur.ProcStatus;
                    labFee2.ProvNum = ProcCur.ProvNum;
                    labFee2.DateEntryC = DateTime.Now;
                    labFee2.ClinicNum = ProcCur.ClinicNum;
                    labFee2.ProcNumLab = ProcCur.ProcNum;
                    labFee2.CodeNum = ProcedureCodes.getCodeNum("99111");
                    if (labFee2.CodeNum == 0)
                    {
                        //Code does not exist.
                        ProcedureCode code99111 = new ProcedureCode();
                        code99111.ProcCode = "99111";
                        code99111.Descript = "+L Commercial Laboratory Procedures";
                        code99111.AbbrDesc = "Lab Fee";
                        code99111.ProcCat = DefC.getByExactNameNeverZero(DefCat.ProcCodeCats,"Adjunctive General Services");
                        ProcedureCodes.insert(code99111);
                        labFee2.CodeNum = code99111.CodeNum;
                    }
                     
                    Procedures.insert(labFee2);
                } 
            } 
        }
        else
        {
            if (ProcedureCode2.IsProsth)
            {
                SelectedIndex __dummyScrutVar10 = listProsth.SelectedIndex;
                if (__dummyScrutVar10.equals(0))
                {
                    ProcCur.Prosthesis = "";
                }
                else if (__dummyScrutVar10.equals(1))
                {
                    ProcCur.Prosthesis = "I";
                }
                else if (__dummyScrutVar10.equals(2))
                {
                    ProcCur.Prosthesis = "R";
                }
                   
                ProcCur.DateOriginalProsth = PIn.Date(textDateOriginalProsth.Text);
            }
            else
            {
                ProcCur.Prosthesis = "";
                ProcCur.DateOriginalProsth = DateTime.MinValue;
            } 
        } 
        ProcCur.ClaimNote = textClaimNote.Text;
        //Last chance to run this code before Proc gets updated.
        if (Programs.getUsingOrion())
        {
            //Ask for an explanation. If they hit cancel here, return and don't save.
            OrionProcCur.DPC = (OrionDPC)comboDPC.SelectedIndex;
            OrionProcCur.DPCpost = (OrionDPC)comboDPCpost.SelectedIndex;
            OrionProcCur.DateScheduleBy = PIn.Date(textDateScheduled.Text);
            OrionProcCur.DateStopClock = PIn.Date(textDateStop.Text);
            OrionProcCur.IsOnCall = checkIsOnCall.Checked;
            OrionProcCur.IsEffectiveComm = checkIsEffComm.Checked;
            OrionProcCur.IsRepair = checkIsRepair.Checked;
            if (IsNew)
            {
                OrionProcs.insert(OrionProcCur);
            }
            else
            {
                //Is not new.
                if (!StringSupport.equals(FormProcEditExplain.getChanges(ProcCur,ProcOld,OrionProcCur,OrionProcOld), ""))
                {
                    //Checks if any changes were made. Also sets static variable Changes.
                    //If a day old and the orion procedure status did not go from TP to C, CS or CR, then show explaination window.
                    if ((ProcOld.DateTP.Date < MiscData.getNowDateTime().Date && (OrionProcOld.Status2 != OrionStatus.TP || (OrionProcCur.Status2 != OrionStatus.C && OrionProcCur.Status2 != OrionStatus.CS && OrionProcCur.Status2 != OrionStatus.CR))))
                    {
                        FormProcEditExplain FormP = new FormProcEditExplain();
                        FormP.dpcChange = (((Enum)OrionProcOld.DPC).ordinal() != ((Enum)OrionProcCur.DPC).ordinal());
                        if (FormP.ShowDialog() != DialogResult.OK)
                        {
                            return ;
                        }
                         
                        Procedure ProcPreExplain = ProcOld.copy();
                        ProcOld.Note = FormProcEditExplain.Explanation;
                        Procedures.update(ProcOld,ProcPreExplain);
                        Thread.Sleep(1100);
                    }
                     
                }
                 
                OrionProcs.update(OrionProcCur);
                //Date entry needs to be updated when status changes to cancelled or refused and at least a day old.
                if (ProcOld.DateTP.Date < MiscData.getNowDateTime().Date && OrionProcCur.Status2 == OrionStatus.CA_EPRD || OrionProcCur.Status2 == OrionStatus.CA_PD || OrionProcCur.Status2 == OrionStatus.CA_Tx || OrionProcCur.Status2 == OrionStatus.R)
                {
                    ProcCur.DateEntryC = MiscData.getNowDateTime().Date;
                }
                 
            } 
        }
         
        //End of "is not new."
        //The actual update----------------------------------------------------------------------------------------------------------------------------------
        Procedures.update(ProcCur,ProcOld);
        if (ProcCur.AptNum > 0 || ProcCur.PlannedAptNum > 0)
        {
            //Update the ProcDescript on the appointment if procedure is attached to one.
            //The ApptProcDescript region is also in FormApptEdit.UpdateToDB() and FormDatabaseMaintenance.butApptProcs_Click()  Make any changes there as well.
            Appointment apt;
            DataTable procTable = new DataTable();
            if (ProcCur.AptNum > 0)
            {
                apt = Appointments.getOneApt(ProcCur.AptNum);
                procTable = Appointments.GetProcTable(ProcCur.PatNum.ToString(), apt.AptNum.ToString(), (((Enum)apt.AptStatus).ordinal()).ToString(), apt.AptDateTime.ToString());
            }
            else
            {
                apt = Appointments.getOneApt(ProcCur.PlannedAptNum);
                procTable = Appointments.GetProcTable(ProcCur.PatNum.ToString(), ProcCur.PlannedAptNum.ToString(), (((Enum)apt.AptStatus).ordinal()).ToString(), apt.AptDateTime.ToString());
            } 
            Appointment aptOld = apt.clone();
            apt.ProcDescript = "";
            apt.ProcsColored = "";
            int count = 0;
            for (int i = 0;i < procTable.Rows.Count;i++)
            {
                if (!StringSupport.equals(procTable.Rows[i]["attached"].ToString(), "1"))
                {
                    continue;
                }
                 
                String procDescOne = "";
                String procCode = procTable.Rows[i]["ProcCode"].ToString();
                if (count > 0)
                {
                    apt.ProcDescript += ", ";
                }
                 
                Rows.INDEXER.INDEXER.APPLY __dummyScrutVar11 = procTable.Rows[i]["TreatArea"].ToString();
                if (__dummyScrutVar11.equals("1"))
                {
                    //TreatmentArea.Surf:
                    procDescOne += "#" + Tooth.GetToothLabel(procTable.Rows[i]["ToothNum"].ToString()) + "-" + procTable.Rows[i]["Surf"].ToString() + "-";
                }
                else //""#12-MOD-"
                if (__dummyScrutVar11.equals("2"))
                {
                    //TreatmentArea.Tooth:
                    procDescOne += "#" + Tooth.GetToothLabel(procTable.Rows[i]["ToothNum"].ToString()) + "-";
                }
                else //"#12-"
                //area 3 or 0 (mouth)
                if (__dummyScrutVar11.equals("4"))
                {
                    //TreatmentArea.Quad:
                    procDescOne += procTable.Rows[i]["Surf"].ToString() + "-";
                }
                else //"UL-"
                if (__dummyScrutVar11.equals("5"))
                {
                    //TreatmentArea.Sextant:
                    procDescOne += "S" + procTable.Rows[i]["Surf"].ToString() + "-";
                }
                else //"S2-"
                if (__dummyScrutVar11.equals("6"))
                {
                    //TreatmentArea.Arch:
                    procDescOne += procTable.Rows[i]["Surf"].ToString() + "-";
                }
                else //"U-"
                if (__dummyScrutVar11.equals("7"))
                {
                }
                else
                {
                }      
                //TreatmentArea.ToothRange:
                //strLine+=table.Rows[j][13].ToString()+" ";//don't show range
                procDescOne += procTable.Rows[i]["AbbrDesc"].ToString();
                apt.ProcDescript += procDescOne;
                //Color and previous date are determined by ProcApptColor object
                ProcApptColor pac = ProcApptColors.getMatch(procCode);
                System.Drawing.Color pColor = System.Drawing.Color.Black;
                String prevDateString = "";
                if (pac != null)
                {
                    pColor = pac.ColorText;
                    if (pac.ShowPreviousDate)
                    {
                        prevDateString = Procedures.getRecentProcDateString(apt.PatNum,apt.AptDateTime,pac.CodeRange);
                        if (!StringSupport.equals(prevDateString, ""))
                        {
                            prevDateString = " (" + prevDateString + ")";
                        }
                         
                    }
                     
                }
                 
                apt.ProcsColored += "<span color=\"" + pColor.ToArgb().ToString() + "\">" + procDescOne + prevDateString + "</span>";
                count++;
            }
            Appointments.update(apt,aptOld);
        }
         
        for (int i = 0;i < ClaimProcsForProc.Count;i++)
        {
            ClaimProcsForProc[i].ClinicNum = ProcCur.ClinicNum;
        }
        //Recall synch--------------------------------------------------------------------------------------------------------------------------------------
        Recalls.synch(ProcCur.PatNum);
        //Auto-insert default encounter ---------------------------------------------------------------------------------------------------------------------------
        if (ProcOld.ProcStatus != ProcStat.C && ProcCur.ProcStatus == ProcStat.C)
        {
            Encounters.insertDefaultEncounter(ProcCur.PatNum,ProcCur.ProvNum,ProcCur.ProcDate);
        }
         
        //Security logs------------------------------------------------------------------------------------------------------------------------------------
        if (ProcOld.ProcStatus != ProcStat.C && ProcCur.ProcStatus == ProcStat.C)
        {
            //if status was changed to complete
            SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode + ", " + ProcCur.ProcFee.ToString("c"));
            List<String> procCodeList = new List<String>();
            procCodeList.Add(ProcedureCodes.getStringProcCode(ProcCur.CodeNum));
            AutomationL.Trigger(AutomationTrigger.CompleteProcedure, procCodeList, ProcCur.PatNum);
        }
        else if (IsNew && ProcCur.ProcStatus == ProcStat.C)
        {
            //if new procedure is complete
            SecurityLogs.makeLogEntry(Permissions.ProcComplCreate,PatCur.PatNum,ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode + ", " + ProcCur.ProcFee.ToString("c"));
        }
        else if (!IsNew)
        {
            if (ProcOld.ProcStatus == ProcStat.C)
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplEdit,PatCur.PatNum,ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode + ", " + ProcCur.ProcFee.ToString("c"));
            }
             
        }
           
        if ((ProcCur.ProcStatus == ProcStat.C || ProcCur.ProcStatus == ProcStat.EC || ProcCur.ProcStatus == ProcStat.EO) && ProcedureCodes.getProcCode(ProcCur.CodeNum).PaintType == ToothPaintingType.Extraction)
        {
            //if an extraction, then mark previous procs hidden
            //Procedures.SetHideGraphical(ProcCur);//might not matter anymore
            ToothInitials.setValue(ProcCur.PatNum,ProcCur.ToothNum,ToothInitialType.Missing);
        }
         
        //Canadian lab fees complete-----------------------------------------------------------------------------------------------------------------------
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA") && ProcCur.ProcStatus == ProcStat.C)
        {
            //Canada
            Procedures.setCanadianLabFeesCompleteForProc(ProcCur);
        }
         
        //Autocodes----------------------------------------------------------------------------------------------------------------------------------------
        ProcOld = ProcCur.copy();
        //in case we now make more changes.
        //these areas have no autocodes
        if (ProcedureCode2.TreatArea == TreatmentArea.Mouth || ProcedureCode2.TreatArea == TreatmentArea.Quad || ProcedureCode2.TreatArea == TreatmentArea.Sextant)
        {
            DialogResult = DialogResult.OK;
            return ;
        }
         
        //this represents the suggested code based on the autocodes set up.
        long verifyCode = new long();
        AutoCode AutoCodeCur = null;
        if (ProcedureCode2.TreatArea == TreatmentArea.Arch)
        {
            if (StringSupport.equals(ProcCur.Surf, ""))
            {
                DialogResult = DialogResult.OK;
                return ;
            }
             
            if (StringSupport.equals(ProcCur.Surf, "U"))
            {
                RefSupport<AutoCode> refVar___2 = new RefSupport<AutoCode>();
                verifyCode = AutoCodeItems.verifyCode(ProcedureCode2.CodeNum,"1","",false,PatCur.PatNum,PatCur.getAge(),refVar___2);
                AutoCodeCur = refVar___2.getValue();
            }
            else
            {
                //max
                RefSupport<AutoCode> refVar___3 = new RefSupport<AutoCode>();
                verifyCode = AutoCodeItems.verifyCode(ProcedureCode2.CodeNum,"32","",false,PatCur.PatNum,PatCur.getAge(),refVar___3);
                AutoCodeCur = refVar___3.getValue();
            } 
        }
        else //mand
        if (ProcedureCode2.TreatArea == TreatmentArea.ToothRange)
        {
            //test for max or mand.
            if (listBoxTeeth.SelectedItems.Count < 1)
            {
                RefSupport<AutoCode> refVar___4 = new RefSupport<AutoCode>();
                verifyCode = AutoCodeItems.verifyCode(ProcedureCode2.CodeNum,"32","",false,PatCur.PatNum,PatCur.getAge(),refVar___4);
                AutoCodeCur = refVar___4.getValue();
            }
            else
            {
                //mand
                RefSupport<AutoCode> refVar___5 = new RefSupport<AutoCode>();
                verifyCode = AutoCodeItems.verifyCode(ProcedureCode2.CodeNum,"1","",false,PatCur.PatNum,PatCur.getAge(),refVar___5);
                AutoCodeCur = refVar___5.getValue();
            } 
        }
        else
        {
            //max
            //surf or tooth
            RefSupport<AutoCode> refVar___6 = new RefSupport<AutoCode>();
            verifyCode = AutoCodeItems.verifyCode(ProcedureCode2.CodeNum,ProcCur.ToothNum,ProcCur.Surf,false,PatCur.PatNum,PatCur.getAge(),refVar___6);
            AutoCodeCur = refVar___6.getValue();
        }  
        if (ProcedureCode2.CodeNum != verifyCode)
        {
            String desc = ProcedureCodes.getProcCode(verifyCode).Descript;
            FormAutoCodeLessIntrusive FormA = new FormAutoCodeLessIntrusive();
            FormA.mainText = ProcedureCodes.getProcCode(verifyCode).ProcCode + " (" + desc + ") " + Lan.g(this,"is the recommended procedure code for this procedure.  Change procedure code and fee?");
            FormA.ShowDialog();
            if (FormA.DialogResult != DialogResult.OK)
            {
                DialogResult = DialogResult.OK;
                return ;
            }
             
            //No longer allow users to hide auto code reminders from the procedure edit window.  A label lets them know to change it via Auto Codes.
            //if(FormA.CheckedBox){
            //  AutoCodeCur.LessIntrusive=true;
            //  AutoCodes.Update(AutoCodeCur);
            //  DataValid.SetInvalid(InvalidType.AutoCodes);
            //}
            ProcCur.CodeNum = verifyCode;
            //ProcedureCode2=ProcedureCodes.GetProcCode(ProcCur.CodeNum);
            //ProcCur.Code=verifyCode;
            InsSub prisub = null;
            InsPlan priplan = null;
            if (PatPlanList.Count > 0)
            {
                prisub = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
                priplan = InsPlans.getPlan(prisub.PlanNum,PlanList);
            }
             
            double insfee = Fees.getAmount0(ProcCur.CodeNum,Fees.getFeeSched(PatCur,PlanList,PatPlanList,SubList));
            if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
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
            //ProcCur.ProcFee=Fees.GetAmount0(ProcedureCode2.CodeNum,Fees.GetFeeSched(PatCur,PlanList,PatPlanList));
            Procedures.update(ProcCur,ProcOld);
            Recalls.synch(ProcCur.PatNum);
            if (ProcCur.ProcStatus == ProcStat.C)
            {
                SecurityLogs.makeLogEntry(Permissions.ProcComplEdit,PatCur.PatNum,PatCur.getNameLF() + ", " + ProcedureCode2.ProcCode + ", " + ProcCur.ProcFee.ToString("c"));
            }
             
        }
         
        DialogResult = DialogResult.OK;
    }

    //it is assumed that we will do an immediate refresh after closing this window.
    private void saveSignature() throws Exception {
        if (SigChanged)
        {
            //Topaz boxes are written in Windows native code.
            //if(allowTopaz && sigBoxTopaz.Visible){
            if (sigBoxTopaz.Visible)
            {
                ProcCur.SigIsTopaz = true;
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    ProcCur.Signature = "";
                    return ;
                }
                 
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,"0000000000000000");
                CodeBase.TopazWrapper.setTopazAutoKeyData(sigBoxTopaz,ProcCur.Note + ProcCur.UserNum.ToString());
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
                ProcCur.Signature = CodeBase.TopazWrapper.getTopazString(sigBoxTopaz);
            }
            else
            {
                ProcCur.SigIsTopaz = false;
                if (sigBox.numberOfTabletPoints() == 0)
                {
                    ProcCur.Signature = "";
                    return ;
                }
                 
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.setKeyString("0000000000000000");
                sigBox.setAutoKeyData(ProcCur.Note + ProcCur.UserNum.ToString());
                //sigBox.SetEncryptionMode(2);
                //sigBox.SetSigCompressionMode(2);
                ProcCur.Signature = sigBox.getSigString();
            } 
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!entriesAreValid())
        {
            return ;
        }
         
        if (Programs.getUsingOrion())
        {
            if (OrionProcOld != null && OrionProcOld.DateStopClock.Year > 1880)
            {
                if (PIn.Date(textDateStop.Text) > OrionProcOld.DateStopClock.Date)
                {
                    MsgBox.show(this,"Future date not allowed for Date Stop Clock.");
                    return ;
                }
                 
            }
            else if (PIn.Date(textDateStop.Text) > MiscData.getNowDateTime().Date)
            {
                MsgBox.show(this,"Future date not allowed for Date Stop Clock.");
                return ;
            }
              
            //Strange logic for Orion for setting sched by date to a scheduled date from a previously cancelled TP on the same tooth/surf.
            if (!StringSupport.equals(ProcCur.Surf, "") || !StringSupport.equals(textTooth.Text, "") || !StringSupport.equals(textSurfaces.Text, ""))
            {
                DataTable table = OrionProcs.GetCancelledScheduleDateByToothOrSurf(ProcCur.PatNum, textTooth.Text.ToString(), ProcCur.Surf);
                if (table.Rows.Count > 0)
                {
                    if (!StringSupport.equals(textDateScheduled.Text, "") && DateTime.Parse(textDateScheduled.Text) > PIn.DateT(table.Rows[0]["DateScheduleBy"].ToString()))
                    {
                        //If the cancelled sched by date is in the past then do nothing.
                        if (PIn.DateT(table.Rows[0]["DateScheduleBy"].ToString()) > MiscData.getNowDateTime().Date)
                        {
                            textDateScheduled.Text = ((DateTime)table.Rows[0]["DateScheduleBy"]).ToShortDateString();
                            CancelledScheduleByDate = DateTime.Parse(textDateScheduled.Text);
                            MsgBox.show(this,"Schedule by date cannot be later than: " + textDateScheduled.Text + ".");
                            return ;
                        }
                         
                    }
                     
                }
                 
            }
             
            if (comboDPC.SelectedIndex == 0)
            {
                MsgBox.show(this,"DPC should not be \"Not Specified\".");
                return ;
            }
             
        }
         
        saveAndClose();
        Plugins.hookAddCode(this,"FormProcEdit.butOK_Click_end",ProcCur);
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formProcEdit_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        //if(allowTopaz){
        //	if(sigBoxTopaz!=null) {
        //		sigBoxTopaz.Dispose();
        //	}
        //}
        if (DialogResult == DialogResult.OK)
        {
            //this catches date,prov,fee,status,etc for all claimProcs attached to this proc.
            if (!StartedAttachedToClaim && Procedures.isAttachedToClaim(ProcCur.ProcNum))
            {
                return ;
            }
             
            //unless they got attached to a claim while this window was open.  Then it doesn't touch them.
            Procedures.computeEstimates(ProcCur,PatCur.PatNum,ClaimProcsForProc,false,PlanList,PatPlanList,BenefitList,PatCur.getAge(),SubList);
            return ;
        }
         
        if (IsNew)
        {
            for (int i = 0;i < ClaimProcsForProc.Count;i++)
            {
                //if cancelling on a new procedure
                //delete any newly created claimprocs
                //if(ClaimProcsForProc[i].ProcNum==ProcCur.ProcNum) {
                ClaimProcs.Delete(ClaimProcsForProc[i]);
            }
        }
         
    }

}


//}