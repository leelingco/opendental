//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:34 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormApptEdit;
import OpenDental.FormAutoNoteCompose;
import OpenDental.FormExamSheets;
import OpenDental.FormPatFieldCheckEdit;
import OpenDental.FormPatFieldDateEdit;
import OpenDental.FormPatFieldEdit;
import OpenDental.FormPatFieldPickEdit;
import OpenDental.FormProcGroup;
import OpenDental.FormProcNoteAppend;
import OpenDental.FormRxSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.ChartModules;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.Family;
import OpenDentBusiness.OrionDPC;
import OpenDentBusiness.OrionProc;
import OpenDentBusiness.OrionProcs;
import OpenDentBusiness.OrionStatus;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PatFieldDefs;
import OpenDentBusiness.PatFields;
import OpenDentBusiness.PatFieldType;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.PlannedAppts;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcGroupItem;
import OpenDentBusiness.ProcGroupItems;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;
import OpenDentBusiness.Security;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.Userods;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormProcGroup  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butDelete;
    private ErrorProvider errorProvider2 = new ErrorProvider();
    private OpenDental.ODtextBox textNotes;
    private Label label15 = new Label();
    private Label label16 = new Label();
    private TextBox textUser = new TextBox();
    private OpenDental.UI.Button buttonUseAutoNote;
    public List<ClaimProcHist> HistList = new List<ClaimProcHist>();
    private OpenDental.UI.ODGrid gridProc;
    private OpenDental.UI.SignatureBoxWrapper signatureBoxWrapper;
    private Label label12 = new Label();
    private OpenDental.ValidDate textDateEntry;
    private Label label26 = new Label();
    private OpenDental.ValidDate textProcDate;
    private Label label2 = new Label();
    public Procedure GroupCur;
    public Procedure GroupOld;
    public List<ProcGroupItem> GroupItemList = new List<ProcGroupItem>();
    public List<Procedure> ProcList = new List<Procedure>();
    private List<Procedure> ProcListOld = new List<Procedure>();
    private List<OrionProc> OrionProcList = new List<OrionProc>();
    /**
    * This keeps the noteChanged event from erasing the signature when first loading.
    */
    private boolean IsStartingUp = new boolean();
    private boolean SigChanged = new boolean();
    private PatField[] PatFieldList = new PatField[]();
    private Patient PatCur;
    private Family FamCur;
    /**
    * Used when making an Rx.
    */
    public static boolean IsOpen = new boolean();
    public static long RxNum = new long();
    private OpenDental.UI.ODGrid gridPat;
    private OpenDental.UI.Button butRx;
    private OpenDental.UI.Button butExamSheets;
    private Label labelRepair = new Label();
    private System.Windows.Forms.Button butRepairY = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butRepairN = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOnCallY = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butOnCallN = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butEffectiveCommN = new System.Windows.Forms.Button();
    private Label labelOnCall = new Label();
    private Label labelEffectiveComm = new Label();
    private System.Windows.Forms.Button butEffectiveCommY = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridPlanned;
    private OpenDental.UI.Button butNew;
    private OpenDental.UI.Button butClear;
    private OpenDental.UI.Button butUp;
    private OpenDental.UI.Button butDown;
    private Panel panelPlanned = new Panel();
    private Label labelDPCpost = new Label();
    private ComboBox comboDPCpost = new ComboBox();
    private OpenDental.UI.Button butLock;
    private OpenDental.UI.Button butInvalidate;
    private OpenDental.UI.Button butAppend;
    private Label labelLocked = new Label();
    private Label labelInvalid = new Label();
    private DataTable TablePlanned = new DataTable();
    public FormProcGroup() throws Exception {
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Inserts are no longer done within this dialog, but must be done ahead of time from outside.You must specify a procedure to edit, and only the changes that are made in this dialog get saved.  Only used when double click in Account, Chart, TP, and in ContrChart.AddProcedure().  The procedure may be deleted if new, and user hits Cancel.
    */
    //Constructor from ProcEdit. Lots of this will need to be copied into the new Load function.
    /*public FormProcGroup(long groupNum) {
    			GroupCur=Procedures.GetOneProc(groupNum,true);
    			ProcGroupItem=ProcGroupItems.Refresh(groupNum);
    			//Proc
    			InitializeComponent();
    			Lan.F(this);
    		}*/
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProcGroup.class);
        this.label7 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.textUser = new System.Windows.Forms.TextBox();
        this.label12 = new System.Windows.Forms.Label();
        this.label26 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textProcDate = new OpenDental.ValidDate();
        this.signatureBoxWrapper = new OpenDental.UI.SignatureBoxWrapper();
        this.gridProc = new OpenDental.UI.ODGrid();
        this.textDateEntry = new OpenDental.ValidDate();
        this.buttonUseAutoNote = new OpenDental.UI.Button();
        this.textNotes = new OpenDental.ODtextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.gridPat = new OpenDental.UI.ODGrid();
        this.butRx = new OpenDental.UI.Button();
        this.butExamSheets = new OpenDental.UI.Button();
        this.labelRepair = new System.Windows.Forms.Label();
        this.butRepairY = new System.Windows.Forms.Button();
        this.butRepairN = new System.Windows.Forms.Button();
        this.butOnCallY = new System.Windows.Forms.Button();
        this.butOnCallN = new System.Windows.Forms.Button();
        this.butEffectiveCommN = new System.Windows.Forms.Button();
        this.labelOnCall = new System.Windows.Forms.Label();
        this.labelEffectiveComm = new System.Windows.Forms.Label();
        this.butEffectiveCommY = new System.Windows.Forms.Button();
        this.gridPlanned = new OpenDental.UI.ODGrid();
        this.butNew = new OpenDental.UI.Button();
        this.butClear = new OpenDental.UI.Button();
        this.butUp = new OpenDental.UI.Button();
        this.butDown = new OpenDental.UI.Button();
        this.panelPlanned = new System.Windows.Forms.Panel();
        this.labelDPCpost = new System.Windows.Forms.Label();
        this.comboDPCpost = new System.Windows.Forms.ComboBox();
        this.butLock = new OpenDental.UI.Button();
        this.butInvalidate = new OpenDental.UI.Button();
        this.butAppend = new OpenDental.UI.Button();
        this.labelLocked = new System.Windows.Forms.Label();
        this.labelInvalid = new System.Windows.Forms.Label();
        this.panelPlanned.SuspendLayout();
        this.SuspendLayout();
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(23, 78);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(73, 16);
        this.label7.TabIndex = 0;
        this.label7.Text = "&Notes";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(-17, 278);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(110, 41);
        this.label15.TabIndex = 79;
        this.label15.Text = "Signature /\r\nInitials";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(23, 55);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(73, 16);
        this.label16.TabIndex = 80;
        this.label16.Text = "User";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textUser
        //
        this.textUser.Location = new System.Drawing.Point(98, 52);
        this.textUser.Name = "textUser";
        this.textUser.ReadOnly = true;
        this.textUser.Size = new System.Drawing.Size(119, 20);
        this.textUser.TabIndex = 101;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(-25, 34);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(125, 14);
        this.label12.TabIndex = 96;
        this.label12.Text = "Date Entry";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label26
        //
        this.label26.Location = new System.Drawing.Point(177, 32);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(83, 18);
        this.label26.TabIndex = 97;
        this.label26.Text = "(for security)";
        this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(2, 14);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(96, 14);
        this.label2.TabIndex = 101;
        this.label2.Text = "Procedure Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProcDate
        //
        this.textProcDate.Location = new System.Drawing.Point(98, 12);
        this.textProcDate.Name = "textProcDate";
        this.textProcDate.ReadOnly = true;
        this.textProcDate.Size = new System.Drawing.Size(76, 20);
        this.textProcDate.TabIndex = 100;
        //
        // signatureBoxWrapper
        //
        this.signatureBoxWrapper.BackColor = System.Drawing.SystemColors.ControlDark;
        this.signatureBoxWrapper.setLabelText("Invalid Signature");
        this.signatureBoxWrapper.Location = new System.Drawing.Point(98, 276);
        this.signatureBoxWrapper.Name = "signatureBoxWrapper";
        this.signatureBoxWrapper.Size = new System.Drawing.Size(364, 81);
        this.signatureBoxWrapper.TabIndex = 194;
        this.signatureBoxWrapper.SignatureChanged += new System.EventHandler(this.signatureBoxWrapper_SignatureChanged);
        //
        // gridProc
        //
        this.gridProc.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridProc.setHScrollVisible(true);
        this.gridProc.Location = new System.Drawing.Point(10, 367);
        this.gridProc.Name = "gridProc";
        this.gridProc.setScrollValue(0);
        this.gridProc.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridProc.Size = new System.Drawing.Size(858, 222);
        this.gridProc.TabIndex = 193;
        this.gridProc.setTitle("Procedures");
        this.gridProc.setTranslationName("TableProg");
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(98, 32);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.ReadOnly = true;
        this.textDateEntry.Size = new System.Drawing.Size(76, 20);
        this.textDateEntry.TabIndex = 95;
        //
        // buttonUseAutoNote
        //
        this.buttonUseAutoNote.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.buttonUseAutoNote.setAutosize(true);
        this.buttonUseAutoNote.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.buttonUseAutoNote.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.buttonUseAutoNote.setCornerRadius(4F);
        this.buttonUseAutoNote.Location = new System.Drawing.Point(220, 51);
        this.buttonUseAutoNote.Name = "buttonUseAutoNote";
        this.buttonUseAutoNote.Size = new System.Drawing.Size(80, 22);
        this.buttonUseAutoNote.TabIndex = 106;
        this.buttonUseAutoNote.Text = "Auto Note";
        this.buttonUseAutoNote.Click += new System.EventHandler(this.buttonUseAutoNote_Click);
        //
        // textNotes
        //
        this.textNotes.AcceptsTab = true;
        this.textNotes.Location = new System.Drawing.Point(98, 72);
        this.textNotes.Name = "textNotes";
        this.textNotes.setQuickPasteType(OpenDentBusiness.QuickPasteType.Procedure);
        this.textNotes.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNotes.Size = new System.Drawing.Size(364, 200);
        this.textNotes.TabIndex = 1;
        this.textNotes.Text = "";
        this.textNotes.TextChanged += new System.EventHandler(this.textNotes_TextChanged);
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
        this.butDelete.Location = new System.Drawing.Point(19, 606);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(83, 24);
        this.butDelete.TabIndex = 8;
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
        this.butCancel.Location = new System.Drawing.Point(792, 609);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(76, 24);
        this.butCancel.TabIndex = 13;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(710, 609);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(76, 24);
        this.butOK.TabIndex = 12;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridPat
        //
        this.gridPat.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridPat.setHScrollVisible(false);
        this.gridPat.Location = new System.Drawing.Point(468, 276);
        this.gridPat.Name = "gridPat";
        this.gridPat.setScrollValue(0);
        this.gridPat.setSelectionMode(OpenDental.UI.GridSelectionMode.None);
        this.gridPat.Size = new System.Drawing.Size(400, 81);
        this.gridPat.TabIndex = 195;
        this.gridPat.setTitle("Patient Fields");
        this.gridPat.setTranslationName("TablePatient");
        this.gridPat.Visible = false;
        this.gridPat.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPat.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPat_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butRx
        //
        this.butRx.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRx.setAutosize(true);
        this.butRx.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRx.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRx.setCornerRadius(4F);
        this.butRx.Location = new System.Drawing.Point(792, 41);
        this.butRx.Name = "butRx";
        this.butRx.Size = new System.Drawing.Size(75, 24);
        this.butRx.TabIndex = 106;
        this.butRx.Text = "Rx";
        this.butRx.Visible = false;
        this.butRx.Click += new System.EventHandler(this.butRx_Click);
        //
        // butExamSheets
        //
        this.butExamSheets.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butExamSheets.setAutosize(true);
        this.butExamSheets.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butExamSheets.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butExamSheets.setCornerRadius(4F);
        this.butExamSheets.Location = new System.Drawing.Point(792, 14);
        this.butExamSheets.Name = "butExamSheets";
        this.butExamSheets.Size = new System.Drawing.Size(76, 24);
        this.butExamSheets.TabIndex = 106;
        this.butExamSheets.Text = "Exam Sheets";
        this.butExamSheets.Visible = false;
        this.butExamSheets.Click += new System.EventHandler(this.butExamSheets_Click);
        //
        // labelRepair
        //
        this.labelRepair.Location = new System.Drawing.Point(498, 85);
        this.labelRepair.Name = "labelRepair";
        this.labelRepair.Size = new System.Drawing.Size(90, 16);
        this.labelRepair.TabIndex = 196;
        this.labelRepair.Text = "Repair";
        this.labelRepair.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelRepair.Visible = false;
        //
        // butRepairY
        //
        this.butRepairY.Location = new System.Drawing.Point(590, 83);
        this.butRepairY.Name = "butRepairY";
        this.butRepairY.Size = new System.Drawing.Size(23, 20);
        this.butRepairY.TabIndex = 198;
        this.butRepairY.Text = "Y";
        this.butRepairY.UseVisualStyleBackColor = true;
        this.butRepairY.Visible = false;
        this.butRepairY.Click += new System.EventHandler(this.butRepairY_Click);
        //
        // butRepairN
        //
        this.butRepairN.Location = new System.Drawing.Point(614, 83);
        this.butRepairN.Name = "butRepairN";
        this.butRepairN.Size = new System.Drawing.Size(23, 20);
        this.butRepairN.TabIndex = 198;
        this.butRepairN.Text = "N";
        this.butRepairN.UseVisualStyleBackColor = true;
        this.butRepairN.Visible = false;
        this.butRepairN.Click += new System.EventHandler(this.butRepairN_Click);
        //
        // butOnCallY
        //
        this.butOnCallY.Location = new System.Drawing.Point(590, 41);
        this.butOnCallY.Name = "butOnCallY";
        this.butOnCallY.Size = new System.Drawing.Size(23, 20);
        this.butOnCallY.TabIndex = 198;
        this.butOnCallY.Text = "Y";
        this.butOnCallY.UseVisualStyleBackColor = true;
        this.butOnCallY.Visible = false;
        this.butOnCallY.Click += new System.EventHandler(this.butOnCallY_Click);
        //
        // butOnCallN
        //
        this.butOnCallN.Location = new System.Drawing.Point(614, 41);
        this.butOnCallN.Name = "butOnCallN";
        this.butOnCallN.Size = new System.Drawing.Size(23, 20);
        this.butOnCallN.TabIndex = 198;
        this.butOnCallN.Text = "N";
        this.butOnCallN.UseVisualStyleBackColor = true;
        this.butOnCallN.Visible = false;
        this.butOnCallN.Click += new System.EventHandler(this.butOnCallN_Click);
        //
        // butEffectiveCommN
        //
        this.butEffectiveCommN.Location = new System.Drawing.Point(614, 62);
        this.butEffectiveCommN.Name = "butEffectiveCommN";
        this.butEffectiveCommN.Size = new System.Drawing.Size(23, 20);
        this.butEffectiveCommN.TabIndex = 198;
        this.butEffectiveCommN.Text = "N";
        this.butEffectiveCommN.UseVisualStyleBackColor = true;
        this.butEffectiveCommN.Visible = false;
        this.butEffectiveCommN.Click += new System.EventHandler(this.butEffectiveCommN_Click);
        //
        // labelOnCall
        //
        this.labelOnCall.Location = new System.Drawing.Point(498, 43);
        this.labelOnCall.Name = "labelOnCall";
        this.labelOnCall.Size = new System.Drawing.Size(90, 16);
        this.labelOnCall.TabIndex = 196;
        this.labelOnCall.Text = "On Call";
        this.labelOnCall.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelOnCall.Visible = false;
        //
        // labelEffectiveComm
        //
        this.labelEffectiveComm.Location = new System.Drawing.Point(498, 64);
        this.labelEffectiveComm.Name = "labelEffectiveComm";
        this.labelEffectiveComm.Size = new System.Drawing.Size(90, 16);
        this.labelEffectiveComm.TabIndex = 196;
        this.labelEffectiveComm.Text = "Effective Comm";
        this.labelEffectiveComm.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelEffectiveComm.Visible = false;
        //
        // butEffectiveCommY
        //
        this.butEffectiveCommY.Location = new System.Drawing.Point(590, 62);
        this.butEffectiveCommY.Name = "butEffectiveCommY";
        this.butEffectiveCommY.Size = new System.Drawing.Size(23, 20);
        this.butEffectiveCommY.TabIndex = 198;
        this.butEffectiveCommY.Text = "Y";
        this.butEffectiveCommY.UseVisualStyleBackColor = true;
        this.butEffectiveCommY.Visible = false;
        this.butEffectiveCommY.Click += new System.EventHandler(this.butEffectiveCommY_Click);
        //
        // gridPlanned
        //
        this.gridPlanned.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridPlanned.setHScrollVisible(false);
        this.gridPlanned.Location = new System.Drawing.Point(0, 28);
        this.gridPlanned.Name = "gridPlanned";
        this.gridPlanned.setScrollValue(0);
        this.gridPlanned.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridPlanned.Size = new System.Drawing.Size(400, 131);
        this.gridPlanned.TabIndex = 204;
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
        // butNew
        //
        this.butNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNew.setAutosize(false);
        this.butNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNew.setCornerRadius(4F);
        this.butNew.Image = Resources.getAdd();
        this.butNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNew.Location = new System.Drawing.Point(43, 3);
        this.butNew.Name = "butNew";
        this.butNew.Size = new System.Drawing.Size(75, 23);
        this.butNew.TabIndex = 205;
        this.butNew.Text = "Add";
        this.butNew.Click += new System.EventHandler(this.butNew_Click);
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.setAutosize(false);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Image = Resources.getdeleteX();
        this.butClear.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butClear.Location = new System.Drawing.Point(123, 3);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(75, 23);
        this.butClear.TabIndex = 206;
        this.butClear.Text = "Delete";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // butUp
        //
        this.butUp.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butUp.setAutosize(false);
        this.butUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butUp.setCornerRadius(4F);
        this.butUp.Image = Resources.getup();
        this.butUp.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butUp.Location = new System.Drawing.Point(203, 3);
        this.butUp.Name = "butUp";
        this.butUp.Size = new System.Drawing.Size(75, 23);
        this.butUp.TabIndex = 207;
        this.butUp.Text = "&Up";
        this.butUp.Click += new System.EventHandler(this.butUp_Click);
        //
        // butDown
        //
        this.butDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDown.setAutosize(false);
        this.butDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDown.setCornerRadius(4F);
        this.butDown.Image = Resources.getdown();
        this.butDown.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDown.Location = new System.Drawing.Point(283, 3);
        this.butDown.Name = "butDown";
        this.butDown.Size = new System.Drawing.Size(75, 23);
        this.butDown.TabIndex = 208;
        this.butDown.Text = "&Down";
        this.butDown.Click += new System.EventHandler(this.butDown_Click);
        //
        // panelPlanned
        //
        this.panelPlanned.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.panelPlanned.Controls.Add(this.butDown);
        this.panelPlanned.Controls.Add(this.butUp);
        this.panelPlanned.Controls.Add(this.butClear);
        this.panelPlanned.Controls.Add(this.butNew);
        this.panelPlanned.Controls.Add(this.gridPlanned);
        this.panelPlanned.Location = new System.Drawing.Point(468, 111);
        this.panelPlanned.Name = "panelPlanned";
        this.panelPlanned.Size = new System.Drawing.Size(400, 159);
        this.panelPlanned.TabIndex = 199;
        this.panelPlanned.Visible = false;
        //
        // labelDPCpost
        //
        this.labelDPCpost.Location = new System.Drawing.Point(488, 15);
        this.labelDPCpost.Name = "labelDPCpost";
        this.labelDPCpost.Size = new System.Drawing.Size(100, 16);
        this.labelDPCpost.TabIndex = 201;
        this.labelDPCpost.Text = "DPC Post Visit";
        this.labelDPCpost.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        this.labelDPCpost.Visible = false;
        //
        // comboDPCpost
        //
        this.comboDPCpost.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboDPCpost.DropDownWidth = 177;
        this.comboDPCpost.FormattingEnabled = true;
        this.comboDPCpost.Location = new System.Drawing.Point(590, 14);
        this.comboDPCpost.MaxDropDownItems = 30;
        this.comboDPCpost.Name = "comboDPCpost";
        this.comboDPCpost.Size = new System.Drawing.Size(177, 21);
        this.comboDPCpost.TabIndex = 200;
        this.comboDPCpost.Visible = false;
        this.comboDPCpost.SelectionChangeCommitted += new System.EventHandler(this.comboDPCpost_SelectionChangeCommitted);
        //
        // butLock
        //
        this.butLock.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLock.setAutosize(true);
        this.butLock.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLock.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLock.setCornerRadius(4F);
        this.butLock.Location = new System.Drawing.Point(382, 5);
        this.butLock.Name = "butLock";
        this.butLock.Size = new System.Drawing.Size(80, 22);
        this.butLock.TabIndex = 204;
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
        this.butInvalidate.Location = new System.Drawing.Point(387, -9);
        this.butInvalidate.Name = "butInvalidate";
        this.butInvalidate.Size = new System.Drawing.Size(80, 22);
        this.butInvalidate.TabIndex = 205;
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
        this.butAppend.Location = new System.Drawing.Point(382, 50);
        this.butAppend.Name = "butAppend";
        this.butAppend.Size = new System.Drawing.Size(80, 22);
        this.butAppend.TabIndex = 203;
        this.butAppend.Text = "Append";
        this.butAppend.Visible = false;
        this.butAppend.Click += new System.EventHandler(this.butAppend_Click);
        //
        // labelLocked
        //
        this.labelLocked.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelLocked.ForeColor = System.Drawing.Color.DarkRed;
        this.labelLocked.Location = new System.Drawing.Point(342, 29);
        this.labelLocked.Name = "labelLocked";
        this.labelLocked.Size = new System.Drawing.Size(123, 18);
        this.labelLocked.TabIndex = 202;
        this.labelLocked.Text = "Locked";
        this.labelLocked.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        this.labelLocked.Visible = false;
        //
        // labelInvalid
        //
        this.labelInvalid.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelInvalid.ForeColor = System.Drawing.Color.DarkRed;
        this.labelInvalid.Location = new System.Drawing.Point(274, 7);
        this.labelInvalid.Name = "labelInvalid";
        this.labelInvalid.Size = new System.Drawing.Size(102, 18);
        this.labelInvalid.TabIndex = 206;
        this.labelInvalid.Text = "Invalid";
        this.labelInvalid.TextAlign = System.Drawing.ContentAlignment.BottomRight;
        this.labelInvalid.Visible = false;
        //
        // FormProcGroup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(880, 645);
        this.Controls.Add(this.labelInvalid);
        this.Controls.Add(this.butLock);
        this.Controls.Add(this.butInvalidate);
        this.Controls.Add(this.butAppend);
        this.Controls.Add(this.labelLocked);
        this.Controls.Add(this.buttonUseAutoNote);
        this.Controls.Add(this.labelDPCpost);
        this.Controls.Add(this.comboDPCpost);
        this.Controls.Add(this.panelPlanned);
        this.Controls.Add(this.butRepairN);
        this.Controls.Add(this.butEffectiveCommN);
        this.Controls.Add(this.butOnCallN);
        this.Controls.Add(this.butRepairY);
        this.Controls.Add(this.butEffectiveCommY);
        this.Controls.Add(this.butOnCallY);
        this.Controls.Add(this.labelRepair);
        this.Controls.Add(this.labelEffectiveComm);
        this.Controls.Add(this.labelOnCall);
        this.Controls.Add(this.gridPat);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textProcDate);
        this.Controls.Add(this.signatureBoxWrapper);
        this.Controls.Add(this.label26);
        this.Controls.Add(this.gridProc);
        this.Controls.Add(this.textDateEntry);
        this.Controls.Add(this.butRx);
        this.Controls.Add(this.butExamSheets);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.textUser);
        this.Controls.Add(this.textNotes);
        this.Controls.Add(this.label16);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProcGroup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Group Note";
        this.Load += new System.EventHandler(this.FormProcGroup_Load);
        this.panelPlanned.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formProcGroup_Load(Object sender, System.EventArgs e) throws Exception {
        IsOpen = true;
        IsStartingUp = true;
        //ProcList gets set in ContrChart where this form is created.
        PatCur = Patients.getPat(GroupCur.PatNum);
        FamCur = Patients.getFamily(GroupCur.PatNum);
        GroupOld = GroupCur.copy();
        ProcListOld = new List<Procedure>();
        for (int i = 0;i < ProcList.Count;i++)
        {
            ProcListOld.Add(ProcList[i].Copy());
        }
        modifyForOrionMode();
        textProcDate.Text = GroupCur.ProcDate.ToShortDateString();
        textDateEntry.Text = GroupCur.DateEntryC.ToShortDateString();
        textUser.Text = Userods.getName(GroupCur.UserNum);
        //might be blank. Will change automatically if user changes note or alters sig.
        textNotes.Text = GroupCur.Note;
        if (GroupCur.ProcStatus == ProcStat.EC && PrefC.getBool(PrefName.ProcLockingIsAllowed) && !GroupCur.IsLocked)
        {
            butLock.Visible = true;
        }
        else
        {
            butLock.Visible = false;
        } 
        if (GroupCur.IsLocked)
        {
            //Whether locking is currently allowed, this proc group may have been locked previously.
            butOK.Enabled = false;
            butDelete.Enabled = false;
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
            if (!Security.isAuthorized(Permissions.ProcComplEdit,GroupCur.DateEntryC))
            {
                butOK.Enabled = false;
                butDelete.Enabled = false;
            }
             
        } 
        if (GroupCur.ProcStatus == ProcStat.D)
        {
            //an invalidated proc
            labelInvalid.Visible = true;
            butInvalidate.Visible = false;
            labelLocked.Visible = false;
            butAppend.Visible = false;
            butOK.Enabled = false;
            butDelete.Enabled = false;
        }
         
        fillProcedures();
        textNotes.Select();
        String keyData = getSignatureKey();
        signatureBoxWrapper.fillSignature(GroupCur.SigIsTopaz,keyData,GroupCur.Signature);
        signatureBoxWrapper.BringToFront();
        fillPatientData();
        fillPlanned();
        textNotes.Select(textNotes.Text.Length, 0);
        IsStartingUp = false;
    }

    //string retVal=GroupCur.Note+GroupCur.UserNum.ToString();
    //MsgBoxCopyPaste msgb=new MsgBoxCopyPaste(retVal);
    //msgb.ShowDialog();
    private void fillPatientData() throws Exception {
        if (PatCur == null)
        {
            gridPat.beginUpdate();
            gridPat.getRows().Clear();
            gridPat.getColumns().Clear();
            gridPat.endUpdate();
            return ;
        }
         
        gridPat.beginUpdate();
        gridPat.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("",150);
        gridPat.getColumns().add(col);
        col = new ODGridColumn("",250);
        gridPat.getColumns().add(col);
        gridPat.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        PatFieldList = PatFields.refresh(PatCur.PatNum);
        List<DisplayField> fields = DisplayFields.getForCategory(DisplayFieldCategory.PatientInformation);
        for (int f = 0;f < fields.Count;f++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (StringSupport.equals(fields[f].Description, ""))
            {
            }
            else
            {
                //...
                if (StringSupport.equals(fields[f].InternalName, "PatFields"))
                {
                }
                else
                {
                    //don't add a cell
                    row.getCells().Add(fields[f].Description);
                } 
            } 
            InternalName __dummyScrutVar0 = fields[f].InternalName;
            //...
            if (__dummyScrutVar0.equals("PatFields"))
            {
                PatField field;
                for (int i = 0;i < PatFieldDefs.getList().Length;i++)
                {
                    if (i > 0)
                    {
                        row = new OpenDental.UI.ODGridRow();
                    }
                     
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
                    gridPat.getRows().add(row);
                }
            }
             
            if (StringSupport.equals(fields[f].InternalName, "PatFields"))
            {
            }
            else
            {
                //don't add the row here
                gridPat.getRows().add(row);
            } 
        }
        gridPat.endUpdate();
    }

    private void fillProcedures() throws Exception {
        gridProc.beginUpdate();
        gridProc.getColumns().Clear();
        ODGridColumn col;
        DisplayFields.refreshCache();
        //probably needs to be removed
        List<DisplayField> fields = DisplayFields.getForCategory(DisplayFieldCategory.ProcedureGroupNote);
        for (int i = 0;i < fields.Count;i++)
        {
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
             
            gridProc.getColumns().add(col);
        }
        gridProc.getRows().Clear();
        for (int i = 0;i < ProcList.Count;i++)
        {
            OpenDental.UI.ODGridRow row = new OpenDental.UI.ODGridRow();
            for (int f = 0;f < fields.Count;f++)
            {
                InternalName __dummyScrutVar1 = fields[f].InternalName;
                if (__dummyScrutVar1.equals("Date"))
                {
                    row.getCells().Add(ProcList[i].ProcDate.ToShortDateString());
                }
                else if (__dummyScrutVar1.equals("Th"))
                {
                    row.getCells().Add(Tooth.GetToothLabel(ProcList[i].ToothNum));
                }
                else if (__dummyScrutVar1.equals("Surf"))
                {
                    row.getCells().Add(ProcList[i].Surf.ToString());
                }
                else if (__dummyScrutVar1.equals("Description"))
                {
                    row.getCells().Add(ProcedureCodes.GetLaymanTerm(ProcList[i].CodeNum));
                }
                else if (__dummyScrutVar1.equals("Stat"))
                {
                    row.getCells().Add(ProcList[i].ProcStatus.ToString());
                }
                else if (__dummyScrutVar1.equals("Prov"))
                {
                    row.getCells().Add(Providers.GetAbbr(ProcList[i].ProvNum));
                }
                else if (__dummyScrutVar1.equals("Amount"))
                {
                    row.getCells().Add(ProcList[i].ProcFee.ToString("F"));
                }
                else if (__dummyScrutVar1.equals("ADA Code"))
                {
                    row.getCells().Add(ProcedureCodes.GetStringProcCode(ProcList[i].CodeNum));
                }
                else if (__dummyScrutVar1.equals("Stat 2"))
                {
                    row.getCells().Add(((OrionStatus)OrionProcList[i].Status2).ToString());
                }
                else if (__dummyScrutVar1.equals("On Call"))
                {
                    if (OrionProcList[i].IsOnCall)
                    {
                        row.getCells().add("Y");
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Effective Comm"))
                {
                    if (OrionProcList[i].IsEffectiveComm)
                    {
                        row.getCells().add("Y");
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Repair"))
                {
                    if (OrionProcList[i].IsRepair)
                    {
                        row.getCells().add("Y");
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("DPCpost"))
                {
                    row.getCells().Add(((OrionDPC)OrionProcList[i].DPCpost).ToString());
                }
                             
            }
            gridProc.getRows().add(row);
        }
        gridProc.endUpdate();
    }

    private void modifyForOrionMode() throws Exception {
        if (Programs.getUsingOrion())
        {
            OrionProcList = new List<OrionProc>();
            for (int i = 0;i < ProcList.Count;i++)
            {
                OrionProcList.Add(OrionProcs.GetOneByProcNum(ProcList[i].ProcNum));
            }
            labelOnCall.Visible = true;
            butOnCallY.Visible = true;
            butOnCallN.Visible = true;
            labelEffectiveComm.Visible = true;
            butEffectiveCommY.Visible = true;
            butEffectiveCommN.Visible = true;
            for (int i = 0;i < ProcList.Count;i++)
            {
                if (ProcedureCodes.GetProcCodeFromDb(ProcList[i].CodeNum).IsProsth)
                {
                    labelRepair.Visible = true;
                    butRepairY.Visible = true;
                    butRepairN.Visible = true;
                }
                 
            }
            butRx.Visible = true;
            butExamSheets.Visible = true;
            panelPlanned.Visible = true;
            gridPat.Visible = true;
            textProcDate.ReadOnly = false;
            labelDPCpost.Visible = true;
            comboDPCpost.Visible = true;
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
        }
        else
        {
            this.ClientSize = new System.Drawing.Size(556, 645);
        } 
    }

    private void refresh() throws Exception {
        fillPatientData();
        fillProcedures();
        fillPlanned();
    }

    private void fillPlanned() throws Exception {
        if (PatCur == null)
        {
            butNew.Enabled = false;
            butClear.Enabled = false;
            butUp.Enabled = false;
            butDown.Enabled = false;
            gridPlanned.Enabled = false;
            return ;
        }
        else
        {
            butNew.Enabled = true;
            butClear.Enabled = true;
            butUp.Enabled = true;
            butDown.Enabled = true;
            gridPlanned.Enabled = true;
        } 
        //Fill grid
        gridPlanned.beginUpdate();
        gridPlanned.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("TablePlannedAppts","#"), 15, HorizontalAlignment.Center);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","Min"),25);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","Procedures"),160);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","Note"),115);
        gridPlanned.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TablePlannedAppts","SchedBy"),50);
        gridPlanned.getColumns().add(col);
        gridPlanned.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        TablePlanned = ChartModules.getAll(PatCur.PatNum,false).Tables["Planned"];
        for (int i = 0;i < TablePlanned.Rows.Count;i++)
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
            row.getCells().Add(TablePlanned.Rows[i]["ItemOrder"].ToString());
            row.getCells().Add(TablePlanned.Rows[i]["minutes"].ToString());
            row.getCells().Add(TablePlanned.Rows[i]["ProcDescript"].ToString());
            row.getCells().Add(TablePlanned.Rows[i]["Note"].ToString());
            String text = new String();
            List<Procedure> procsList = Procedures.refresh(PatCur.PatNum);
            DateTime newDateSched = new DateTime();
            for (int p = 0;p < procsList.Count;p++)
            {
                if (procsList[p].PlannedAptNum == PIn.Long(TablePlanned.Rows[i]["AptNum"].ToString()))
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
            row.setColorText(Color.FromArgb(PIn.Int(TablePlanned.Rows[i]["colorText"].ToString())));
            row.setColorBackG(Color.FromArgb(PIn.Int(TablePlanned.Rows[i]["colorBackG"].ToString())));
            gridPlanned.getRows().add(row);
        }
        gridPlanned.endUpdate();
    }

    private void butNew_Click(Object sender, EventArgs e) throws Exception {
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
        plannedAppt.ItemOrder = TablePlanned.Rows.Count + 1;
        PlannedAppts.insert(plannedAppt);
        FormApptEdit FormApptEdit2 = new FormApptEdit(AptCur.AptNum);
        FormApptEdit2.IsNew = true;
        FormApptEdit2.ShowDialog();
        if (FormApptEdit2.DialogResult != DialogResult.OK)
        {
            //delete new appt, delete plannedappt, and unattach procs already handled in dialog
            refresh();
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
        refresh();
    }

    //if procs were added in appt, then this will display them
    private void butClear_Click(Object sender, EventArgs e) throws Exception {
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
            Appointments.Delete(PIn.Long(TablePlanned.Rows[gridPlanned.getSelectedIndices()[i]]["AptNum"].ToString()));
        }
        refresh();
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
         
        int idx = gridPlanned.getSelectedIndices()[0];
        if (idx == 0)
        {
            return ;
        }
         
        PlannedAppt planned;
        planned = PlannedAppts.GetOne(PIn.Long(TablePlanned.Rows[idx]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx - 1;
        PlannedAppts.update(planned);
        planned = PlannedAppts.GetOne(PIn.Long(TablePlanned.Rows[idx - 1]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx;
        PlannedAppts.update(planned);
        TablePlanned = ChartModules.getAll(PatCur.PatNum,false).Tables["Planned"];
        refresh();
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
         
        int idx = gridPlanned.getSelectedIndices()[0];
        if (idx == TablePlanned.Rows.Count - 1)
        {
            return ;
        }
         
        PlannedAppt planned;
        planned = PlannedAppts.GetOne(PIn.Long(TablePlanned.Rows[idx]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx + 1;
        PlannedAppts.update(planned);
        planned = PlannedAppts.GetOne(PIn.Long(TablePlanned.Rows[idx + 1]["PlannedApptNum"].ToString()));
        planned.ItemOrder = idx;
        PlannedAppts.update(planned);
        TablePlanned = ChartModules.getAll(PatCur.PatNum,false).Tables["Planned"];
        refresh();
        gridPlanned.setSelected(idx + 1,true);
    }

    private void gridPlanned_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long aptnum = PIn.Long(TablePlanned.Rows[e.getRow()]["AptNum"].ToString());
        FormApptEdit FormAE = new FormApptEdit(aptnum);
        FormAE.ShowDialog();
        if (FormAE.DialogResult == DialogResult.OK)
        {
            refresh();
        }
         
        for (int i = 0;i < TablePlanned.Rows.Count;i++)
        {
            if (TablePlanned.Rows[i]["AptNum"].ToString() == aptnum.ToString())
            {
                gridPlanned.setSelected(i,true);
            }
             
        }
    }

    private void butRx_Click(Object sender, EventArgs e) throws Exception {
        //only visible in Orion mode
        if (!Security.isAuthorized(Permissions.RxCreate))
        {
            return ;
        }
         
        FormRxSelect FormRS = new FormRxSelect(PatCur);
        FormRS.ShowDialog();
        if (FormRS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        SecurityLogs.makeLogEntry(Permissions.RxCreate,PatCur.PatNum,PatCur.getNameLF());
        RxPat Rx = RxPats.getRx(RxNum);
        if (!StringSupport.equals(textNotes.Text, ""))
        {
            textNotes.Text += "\r\n";
        }
         
        textNotes.Text += "Rx - " + Rx.Drug + " - #" + Rx.Disp;
        String rxNote = Pharmacies.getDescription(RxNum);
        if (!StringSupport.equals(rxNote, ""))
        {
            textNotes.Text += "\r\n" + rxNote;
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

    private void butExamSheets_Click(Object sender, EventArgs e) throws Exception {
        FormExamSheets fes = new FormExamSheets();
        fes.PatNum = GroupCur.PatNum;
        fes.ShowDialog();
    }

    //TODO: Print a note about Exam Sheet added.
    private void textNotes_TextChanged(Object sender, EventArgs e) throws Exception {
        //so this happens only if user changes the note
        if (!IsStartingUp && !SigChanged)
        {
            //and the original signature is still showing.
            //SigChanged=true;//happens automatically through the event.
            signatureBoxWrapper.clearSignature();
        }
         
    }

    private String getSignatureKey() throws Exception {
        String keyData = GroupCur.ProcDate.ToShortDateString();
        keyData += GroupCur.DateEntryC.ToShortDateString();
        keyData += GroupCur.UserNum.ToString();
        //Security.CurUser.UserName;
        keyData += GroupCur.Note;
        GroupItemList = ProcGroupItems.getForGroup(GroupCur.ProcNum);
        for (int i = 0;i < GroupItemList.Count;i++)
        {
            //Orders the list to ensure same key in all cases.
            keyData += GroupItemList[i].ProcGroupItemNum.ToString();
        }
        return keyData;
    }

    private void saveSignature() throws Exception {
        if (SigChanged)
        {
            String keyData = getSignatureKey();
            GroupCur.Signature = signatureBoxWrapper.getSignature(keyData);
            GroupCur.SigIsTopaz = signatureBoxWrapper.getSigIsTopaz();
        }
         
    }

    private void signatureBoxWrapper_SignatureChanged(Object sender, EventArgs e) throws Exception {
        GroupCur.UserNum = Security.getCurUser().UserNum;
        textUser.Text = Userods.getName(GroupCur.UserNum);
        SigChanged = true;
    }

    private void butOnCallY_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            OrionProcList[i].IsOnCall = true;
        }
        fillProcedures();
    }

    private void butOnCallN_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            OrionProcList[i].IsOnCall = false;
        }
        fillProcedures();
    }

    private void butEffectiveCommY_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            OrionProcList[i].IsEffectiveComm = true;
        }
        fillProcedures();
    }

    private void butEffectiveCommN_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            OrionProcList[i].IsEffectiveComm = false;
        }
        fillProcedures();
    }

    private void butRepairY_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            if (ProcedureCodes.GetProcCodeFromDb(ProcList[i].CodeNum).IsProsth)
            {
                //OrionProcList[i] corresponds to ProcList[i]
                OrionProcList[i].IsRepair = true;
            }
             
        }
        fillProcedures();
    }

    private void butRepairN_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            if (ProcedureCodes.GetProcCodeFromDb(ProcList[i].CodeNum).IsProsth)
            {
                //OrionProcList[i] corresponds to ProcList[i]
                OrionProcList[i].IsRepair = false;
            }
             
        }
        fillProcedures();
    }

    private void comboDPCpost_SelectionChangeCommitted(Object sender, EventArgs e) throws Exception {
        for (int i = 0;i < OrionProcList.Count;i++)
        {
            OrionProcList[i].DPCpost = (OrionDPC)comboDPCpost.SelectedIndex;
        }
        fillProcedures();
    }

    /**
    * This button is only visible if 1. Pref ProcLockingIsAllowed is true, 2. Proc isn't already locked, 3. Proc status is C.
    */
    private void butLock_Click(Object sender, EventArgs e) throws Exception {
        if (!entriesAreValid())
        {
            return ;
        }
         
        GroupCur.IsLocked = true;
        saveAndClose();
    }

    //saves all the other various changes that the user made
    /**
    * This button is only visible when proc IsLocked.
    */
    private void butInvalidate_Click(Object sender, EventArgs e) throws Exception {
        //What this will really do is "delete" the procedure.
        if (!Security.isAuthorized(Permissions.ProcDelete,GroupCur.DateEntryC))
        {
            return ;
        }
         
        try
        {
            Procedures.delete(GroupCur.ProcNum);
        }
        catch (Exception ex)
        {
            //also deletes any claimprocs (other than ins payments of course).
            MessageBox.Show(ex.Message);
            return ;
        }

        SecurityLogs.makeLogEntry(Permissions.ProcDelete,PatCur.PatNum,Lan.g(this,"Invalidated: ") + ProcedureCodes.getStringProcCode(GroupCur.CodeNum).ToString() + ", " + GroupCur.ProcDate.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    /**
    * This button is only visible when proc IsLocked.
    */
    private void butAppend_Click(Object sender, EventArgs e) throws Exception {
        FormProcNoteAppend formPNA = new FormProcNoteAppend();
        formPNA.ProcCur = GroupCur;
        formPNA.ShowDialog();
        if (formPNA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    //exit out of this window.  Change already saved, and OK button is disabled in this window, anyway.
    private void gridPat_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        String tag = gridPat.getRows().get___idx(e.getRow()).getTag().ToString();
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
        fillPatientData();
    }

    private boolean entriesAreValid() throws Exception {
        if (!StringSupport.equals(textProcDate.errorProvider1.GetError(textProcDate), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (!signatureBoxWrapper.getIsValid())
        {
            MsgBox.show(this,"Your signature is invalid. Please sign and click OK again.");
            return false;
        }
         
        return true;
    }

    private void saveAndClose() throws Exception {
        GroupCur.Note = textNotes.Text;
        GroupCur.ProcDate = PIn.Date(this.textProcDate.Text);
        for (int i = 0;i < ProcList.Count;i++)
        {
            ProcList[i].ProcDate = GroupCur.ProcDate;
        }
        try
        {
            saveSignature();
        }
        catch (Exception ex)
        {
            MessageBox.Show(Lan.g(this,"Error saving signature.") + "\r\n" + ex.Message);
        }

        Procedures.update(GroupCur,GroupOld);
        for (int i = 0;i < ProcList.Count;i++)
        {
            Procedures.Update(ProcList[i], ProcListOld[i]);
        }
        if (Programs.getUsingOrion())
        {
            for (int i = 0;i < OrionProcList.Count;i++)
            {
                OrionProcs.Update(OrionProcList[i]);
            }
        }
         
        DialogResult = DialogResult.OK;
        IsOpen = false;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Delete this group note?"))
        {
            return ;
        }
         
        Procedures.delete(GroupCur.ProcNum);
        for (int i = 0;i < GroupItemList.Count;i++)
        {
            ProcGroupItems.Delete(GroupItemList[i].ProcGroupItemNum);
        }
        DialogResult = DialogResult.OK;
        IsOpen = false;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!entriesAreValid())
        {
            return ;
        }
         
        saveAndClose();
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        if (GroupCur.getIsNew())
        {
            Procedures.delete(GroupCur.ProcNum);
            for (int i = 0;i < GroupItemList.Count;i++)
            {
                ProcGroupItems.Delete(GroupItemList[i].ProcGroupItemNum);
            }
        }
         
        DialogResult = DialogResult.Cancel;
        IsOpen = false;
    }

}


