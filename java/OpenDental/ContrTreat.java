//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:20 PM
//

package OpenDental;

import CodeBase.Logger;
import CodeBase.Logger.Severity;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Bridges.ECW;
import OpenDental.ClaimL;
import OpenDental.ContrTreat;
import OpenDental.DateTimeOD;
import OpenDental.FormClaimEdit;
import OpenDental.FormEmailMessageEdit;
import OpenDental.FormInsPlanSelect;
import OpenDental.FormProcEdit;
import OpenDental.FormProcTPEdit;
import OpenDental.FormRpPrintPreview;
import OpenDental.FormTPsign;
import OpenDental.FormTreatPlanEdit;
import OpenDental.Lan;
import OpenDental.MigraDocHelper;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PatientSelectedEventHandler;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.ProcedureL;
import OpenDental.ProgramL;
import OpenDental.ToolButItem;
import OpenDental.ToolButItems;
import OpenDental.TpRow;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.__MultiODToolBarButtonClickEventHandler;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ComputerPrefs;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFieldCategory;
import OpenDentBusiness.DisplayFields;
import OpenDentBusiness.EmailAddresses;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
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
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.ProcTP;
import OpenDentBusiness.ProcTPs;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Security;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;
import OpenDentBusiness.ToothNumberingNomenclature;
import OpenDentBusiness.ToothPaintingType;
import OpenDentBusiness.TreatmentArea;
import OpenDentBusiness.TreatPlan;
import OpenDentBusiness.TreatPlans;
import OpenDentBusiness.YN;
import SparksToothChart.ToothChartDirectX;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class ContrTreat  extends System.Windows.Forms.UserControl 
{
    //private AxFPSpread.AxvaSpread axvaSpread2;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.ComponentModel.IContainer components = new System.ComponentModel.IContainer();
    // Required designer variable.
    private System.Windows.Forms.ListBox listSetPr = new System.Windows.Forms.ListBox();
    //<summary></summary>
    //public static ArrayList TPLines2;
    //private bool[] selectedPrs;//had to use this because of deficiency in available Listbox events.
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label16 = new System.Windows.Forms.Label();
    //private int linesPrinted=0;
    /**
    * 
    */
    public FormRpPrintPreview pView;
    //		private System.Windows.Forms.PrintDialog printDialog2;
    //private bool headingPrinted;
    //private bool graphicsPrinted;
    //private bool mainPrinted;
    //private bool benefitsPrinted;
    //private bool notePrinted;
    private double[] ColTotal = new double[]();
    private System.Drawing.Font bodyFont = new System.Drawing.Font("Arial", 9);
    private System.Drawing.Font nameFont = new System.Drawing.Font("Arial", 9, FontStyle.Bold);
    //private Font headingFont=new Font("Arial",13,FontStyle.Bold);
    private System.Drawing.Font subHeadingFont = new System.Drawing.Font("Arial", 10, FontStyle.Bold);
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private System.Drawing.Font totalFont = new System.Drawing.Font("Arial", 9, FontStyle.Bold);
    //private int yPos=938;
    //private	int xPos=25;
    private System.Windows.Forms.TextBox textPriMax = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSecUsed = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSecDed = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSecMax = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPriRem = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPriPend = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPriUsed = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPriDed = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSecRem = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textSecPend = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPriDedRem = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label18 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSecDedRem = new System.Windows.Forms.TextBox();
    private OpenDental.UI.ODToolBar ToolBarMain;
    private ArrayList ALPreAuth = new ArrayList();
    /**
    * This is a list of all procedures for the patient.
    */
    private List<Procedure> ProcList = new List<Procedure>();
    /**
    * This is a filtered list containing only TP procedures.  It's also already sorted by priority and tooth number.
    */
    private Procedure[] ProcListTP = new Procedure[]();
    private System.Windows.Forms.CheckBox checkShowCompleted = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupShow = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkShowIns = new System.Windows.Forms.CheckBox();
    private List<ClaimProc> ClaimProcList = new List<ClaimProc>();
    private Family FamCur;
    private Patient PatCur;
    private System.Windows.Forms.CheckBox checkShowFees = new System.Windows.Forms.CheckBox();
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.ODGrid gridPreAuth;
    private List<InsPlan> InsPlanList = new List<InsPlan>();
    private List<InsSub> SubList = new List<InsSub>();
    private OpenDental.UI.ODGrid gridPlans;
    private TreatPlan[] PlanList = new TreatPlan[]();
    /**
    * A list of all ProcTP objects for this patient.
    */
    private ProcTP[] ProcTPList = new ProcTP[]();
    private System.Windows.Forms.TextBox textNote = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.ImageList imageListMain = new System.Windows.Forms.ImageList();
    /**
    * A list of all ProcTP objects for the selected tp.
    */
    private ProcTP[] ProcTPSelectList = new ProcTP[]();
    /**
    * 
    */
    public PatientSelectedEventHandler PatientSelected = null;
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private List<Benefit> BenefitList = new List<Benefit>();
    private List<Procedure> ProcListFiltered = new List<Procedure>();
    /**
    * Only used for printing graphical chart.
    */
    private List<ToothInitial> ToothInitialList = new List<ToothInitial>();
    /**
    * Only used for printing graphical chart.
    */
    private SparksToothChart.ToothChartWrapper toothChart;
    private CheckBox checkShowSubtotals = new CheckBox();
    private CheckBox checkShowMaxDed = new CheckBox();
    /**
    * Only used for printing graphical chart.
    */
    private Bitmap chartBitmap = new Bitmap();
    //private int headingPrintH;
    private CheckBox checkShowTotals = new CheckBox();
    //private int pagesPrinted;
    private CheckBox checkShowDiscount = new CheckBox();
    private List<Claim> ClaimList = new List<Claim>();
    private boolean InitializedOnStartup = new boolean();
    private List<ClaimProcHist> HistList = new List<ClaimProcHist>();
    private TextBox textFamPriDed = new TextBox();
    private TextBox textFamSecDed = new TextBox();
    private Label label2 = new Label();
    private GroupBox groupBoxFamilyIns = new GroupBox();
    private GroupBox groupBoxIndIns = new GroupBox();
    private Label label3 = new Label();
    private Label label4 = new Label();
    private TextBox textFamSecMax = new TextBox();
    private Label label5 = new Label();
    private TextBox textFamPriMax = new TextBox();
    private List<ClaimProcHist> LoopList = new List<ClaimProcHist>();
    private boolean checkShowInsNotAutomatic = new boolean();
    private List<TpRow> RowsMain = new List<TpRow>();
    /**
    * 
    */
    public ContrTreat() throws Exception {
        Logger.openlog.log("Initializing treatment module...",Severity.INFO);
        initializeComponent();
    }

    // This call is required by the Windows.Forms Form Designer.
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(ContrTreat.class);
        this.label1 = new System.Windows.Forms.Label();
        this.listSetPr = new System.Windows.Forms.ListBox();
        this.groupShow = new System.Windows.Forms.GroupBox();
        this.checkShowDiscount = new System.Windows.Forms.CheckBox();
        this.checkShowTotals = new System.Windows.Forms.CheckBox();
        this.checkShowMaxDed = new System.Windows.Forms.CheckBox();
        this.checkShowSubtotals = new System.Windows.Forms.CheckBox();
        this.checkShowFees = new System.Windows.Forms.CheckBox();
        this.checkShowIns = new System.Windows.Forms.CheckBox();
        this.checkShowCompleted = new System.Windows.Forms.CheckBox();
        this.label10 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.label14 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.textPriMax = new System.Windows.Forms.TextBox();
        this.textSecUsed = new System.Windows.Forms.TextBox();
        this.textSecDed = new System.Windows.Forms.TextBox();
        this.textSecMax = new System.Windows.Forms.TextBox();
        this.textPriRem = new System.Windows.Forms.TextBox();
        this.textPriPend = new System.Windows.Forms.TextBox();
        this.textPriUsed = new System.Windows.Forms.TextBox();
        this.textPriDed = new System.Windows.Forms.TextBox();
        this.textSecRem = new System.Windows.Forms.TextBox();
        this.textSecPend = new System.Windows.Forms.TextBox();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.textPriDedRem = new System.Windows.Forms.TextBox();
        this.label18 = new System.Windows.Forms.Label();
        this.textSecDedRem = new System.Windows.Forms.TextBox();
        this.textNote = new System.Windows.Forms.TextBox();
        this.imageListMain = new System.Windows.Forms.ImageList(this.components);
        this.textFamPriDed = new System.Windows.Forms.TextBox();
        this.textFamSecDed = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.groupBoxFamilyIns = new System.Windows.Forms.GroupBox();
        this.textFamPriMax = new System.Windows.Forms.TextBox();
        this.label3 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.textFamSecMax = new System.Windows.Forms.TextBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBoxIndIns = new System.Windows.Forms.GroupBox();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.ToolBarMain = new OpenDental.UI.ODToolBar();
        this.gridPreAuth = new OpenDental.UI.ODGrid();
        this.gridPlans = new OpenDental.UI.ODGrid();
        this.groupShow.SuspendLayout();
        this.groupBoxFamilyIns.SuspendLayout();
        this.groupBoxIndIns.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(755, 167);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(97, 15);
        this.label1.TabIndex = 4;
        this.label1.Text = "Set Priority";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // listSetPr
        //
        this.listSetPr.Location = new System.Drawing.Point(757, 184);
        this.listSetPr.Name = "listSetPr";
        this.listSetPr.SelectionMode = System.Windows.Forms.SelectionMode.None;
        this.listSetPr.Size = new System.Drawing.Size(70, 212);
        this.listSetPr.TabIndex = 5;
        this.listSetPr.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listSetPr_MouseDown);
        //
        // groupShow
        //
        this.groupShow.Controls.Add(this.checkShowDiscount);
        this.groupShow.Controls.Add(this.checkShowTotals);
        this.groupShow.Controls.Add(this.checkShowMaxDed);
        this.groupShow.Controls.Add(this.checkShowSubtotals);
        this.groupShow.Controls.Add(this.checkShowFees);
        this.groupShow.Controls.Add(this.checkShowIns);
        this.groupShow.Controls.Add(this.checkShowCompleted);
        this.groupShow.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupShow.Location = new System.Drawing.Point(466, 25);
        this.groupShow.Name = "groupShow";
        this.groupShow.Size = new System.Drawing.Size(173, 138);
        this.groupShow.TabIndex = 59;
        this.groupShow.TabStop = false;
        this.groupShow.Text = "Show";
        //
        // checkShowDiscount
        //
        this.checkShowDiscount.Checked = true;
        this.checkShowDiscount.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowDiscount.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowDiscount.Location = new System.Drawing.Point(31, 84);
        this.checkShowDiscount.Name = "checkShowDiscount";
        this.checkShowDiscount.Size = new System.Drawing.Size(131, 17);
        this.checkShowDiscount.TabIndex = 25;
        this.checkShowDiscount.Text = "Discount (PPO)";
        this.checkShowDiscount.Click += new System.EventHandler(this.checkShowDiscount_Click);
        //
        // checkShowTotals
        //
        this.checkShowTotals.Checked = true;
        this.checkShowTotals.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowTotals.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowTotals.Location = new System.Drawing.Point(31, 118);
        this.checkShowTotals.Name = "checkShowTotals";
        this.checkShowTotals.Size = new System.Drawing.Size(128, 15);
        this.checkShowTotals.TabIndex = 24;
        this.checkShowTotals.Text = "Totals";
        this.checkShowTotals.Click += new System.EventHandler(this.checkShowTotals_Click);
        //
        // checkShowMaxDed
        //
        this.checkShowMaxDed.Checked = true;
        this.checkShowMaxDed.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowMaxDed.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowMaxDed.Location = new System.Drawing.Point(15, 33);
        this.checkShowMaxDed.Name = "checkShowMaxDed";
        this.checkShowMaxDed.Size = new System.Drawing.Size(154, 17);
        this.checkShowMaxDed.TabIndex = 23;
        this.checkShowMaxDed.Text = "Use Ins Max and Deduct";
        this.checkShowMaxDed.Click += new System.EventHandler(this.checkShowMaxDed_Click);
        //
        // checkShowSubtotals
        //
        this.checkShowSubtotals.Checked = true;
        this.checkShowSubtotals.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowSubtotals.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowSubtotals.Location = new System.Drawing.Point(31, 101);
        this.checkShowSubtotals.Name = "checkShowSubtotals";
        this.checkShowSubtotals.Size = new System.Drawing.Size(128, 17);
        this.checkShowSubtotals.TabIndex = 22;
        this.checkShowSubtotals.Text = "Subtotals";
        this.checkShowSubtotals.Click += new System.EventHandler(this.checkShowSubtotals_Click);
        //
        // checkShowFees
        //
        this.checkShowFees.Checked = true;
        this.checkShowFees.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowFees.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowFees.Location = new System.Drawing.Point(15, 50);
        this.checkShowFees.Name = "checkShowFees";
        this.checkShowFees.Size = new System.Drawing.Size(146, 17);
        this.checkShowFees.TabIndex = 20;
        this.checkShowFees.Text = "Fees";
        this.checkShowFees.Click += new System.EventHandler(this.checkShowFees_Click);
        //
        // checkShowIns
        //
        this.checkShowIns.Checked = true;
        this.checkShowIns.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowIns.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowIns.Location = new System.Drawing.Point(31, 67);
        this.checkShowIns.Name = "checkShowIns";
        this.checkShowIns.Size = new System.Drawing.Size(131, 17);
        this.checkShowIns.TabIndex = 19;
        this.checkShowIns.Text = "Insurance Estimates";
        this.checkShowIns.Click += new System.EventHandler(this.checkShowIns_Click);
        //
        // checkShowCompleted
        //
        this.checkShowCompleted.Checked = true;
        this.checkShowCompleted.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkShowCompleted.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkShowCompleted.Location = new System.Drawing.Point(15, 16);
        this.checkShowCompleted.Name = "checkShowCompleted";
        this.checkShowCompleted.Size = new System.Drawing.Size(154, 17);
        this.checkShowCompleted.TabIndex = 18;
        this.checkShowCompleted.Text = "Graphical Completed Tx";
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(73, 16);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(60, 15);
        this.label10.TabIndex = 31;
        this.label10.Text = "Primary";
        this.label10.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(4, 37);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(66, 15);
        this.label11.TabIndex = 32;
        this.label11.Text = "Annual Max";
        this.label11.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(4, 57);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(66, 15);
        this.label12.TabIndex = 33;
        this.label12.Text = "Deductible";
        this.label12.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(4, 97);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(66, 15);
        this.label13.TabIndex = 34;
        this.label13.Text = "Ins Used";
        this.label13.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(4, 137);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(66, 15);
        this.label14.TabIndex = 35;
        this.label14.Text = "Remaining";
        this.label14.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(4, 117);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(66, 15);
        this.label15.TabIndex = 36;
        this.label15.Text = "Pending";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(130, 16);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(60, 14);
        this.label16.TabIndex = 37;
        this.label16.Text = "Secondary";
        this.label16.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // textPriMax
        //
        this.textPriMax.BackColor = System.Drawing.Color.White;
        this.textPriMax.Location = new System.Drawing.Point(71, 35);
        this.textPriMax.Name = "textPriMax";
        this.textPriMax.ReadOnly = true;
        this.textPriMax.Size = new System.Drawing.Size(60, 20);
        this.textPriMax.TabIndex = 38;
        this.textPriMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecUsed
        //
        this.textSecUsed.BackColor = System.Drawing.Color.White;
        this.textSecUsed.Location = new System.Drawing.Point(130, 95);
        this.textSecUsed.Name = "textSecUsed";
        this.textSecUsed.ReadOnly = true;
        this.textSecUsed.Size = new System.Drawing.Size(60, 20);
        this.textSecUsed.TabIndex = 39;
        this.textSecUsed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecDed
        //
        this.textSecDed.BackColor = System.Drawing.Color.White;
        this.textSecDed.Location = new System.Drawing.Point(130, 55);
        this.textSecDed.Name = "textSecDed";
        this.textSecDed.ReadOnly = true;
        this.textSecDed.Size = new System.Drawing.Size(60, 20);
        this.textSecDed.TabIndex = 40;
        this.textSecDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecMax
        //
        this.textSecMax.BackColor = System.Drawing.Color.White;
        this.textSecMax.Location = new System.Drawing.Point(130, 35);
        this.textSecMax.Name = "textSecMax";
        this.textSecMax.ReadOnly = true;
        this.textSecMax.Size = new System.Drawing.Size(60, 20);
        this.textSecMax.TabIndex = 41;
        this.textSecMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriRem
        //
        this.textPriRem.BackColor = System.Drawing.Color.White;
        this.textPriRem.Location = new System.Drawing.Point(71, 135);
        this.textPriRem.Name = "textPriRem";
        this.textPriRem.ReadOnly = true;
        this.textPriRem.Size = new System.Drawing.Size(60, 20);
        this.textPriRem.TabIndex = 42;
        this.textPriRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriPend
        //
        this.textPriPend.BackColor = System.Drawing.Color.White;
        this.textPriPend.Location = new System.Drawing.Point(71, 115);
        this.textPriPend.Name = "textPriPend";
        this.textPriPend.ReadOnly = true;
        this.textPriPend.Size = new System.Drawing.Size(60, 20);
        this.textPriPend.TabIndex = 43;
        this.textPriPend.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriUsed
        //
        this.textPriUsed.BackColor = System.Drawing.Color.White;
        this.textPriUsed.Location = new System.Drawing.Point(71, 95);
        this.textPriUsed.Name = "textPriUsed";
        this.textPriUsed.ReadOnly = true;
        this.textPriUsed.Size = new System.Drawing.Size(60, 20);
        this.textPriUsed.TabIndex = 44;
        this.textPriUsed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriDed
        //
        this.textPriDed.BackColor = System.Drawing.Color.White;
        this.textPriDed.Location = new System.Drawing.Point(71, 55);
        this.textPriDed.Name = "textPriDed";
        this.textPriDed.ReadOnly = true;
        this.textPriDed.Size = new System.Drawing.Size(60, 20);
        this.textPriDed.TabIndex = 45;
        this.textPriDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecRem
        //
        this.textSecRem.BackColor = System.Drawing.Color.White;
        this.textSecRem.Location = new System.Drawing.Point(130, 135);
        this.textSecRem.Name = "textSecRem";
        this.textSecRem.ReadOnly = true;
        this.textSecRem.Size = new System.Drawing.Size(60, 20);
        this.textSecRem.TabIndex = 46;
        this.textSecRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textSecPend
        //
        this.textSecPend.BackColor = System.Drawing.Color.White;
        this.textSecPend.Location = new System.Drawing.Point(130, 115);
        this.textSecPend.Name = "textSecPend";
        this.textSecPend.ReadOnly = true;
        this.textSecPend.Size = new System.Drawing.Size(60, 20);
        this.textSecPend.TabIndex = 47;
        this.textSecPend.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPriDedRem
        //
        this.textPriDedRem.BackColor = System.Drawing.Color.White;
        this.textPriDedRem.Location = new System.Drawing.Point(71, 75);
        this.textPriDedRem.Name = "textPriDedRem";
        this.textPriDedRem.ReadOnly = true;
        this.textPriDedRem.Size = new System.Drawing.Size(60, 20);
        this.textPriDedRem.TabIndex = 51;
        this.textPriDedRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label18
        //
        this.label18.Location = new System.Drawing.Point(2, 77);
        this.label18.Name = "label18";
        this.label18.Size = new System.Drawing.Size(69, 15);
        this.label18.TabIndex = 50;
        this.label18.Text = "Ded Remain";
        this.label18.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSecDedRem
        //
        this.textSecDedRem.BackColor = System.Drawing.Color.White;
        this.textSecDedRem.Location = new System.Drawing.Point(130, 75);
        this.textSecDedRem.Name = "textSecDedRem";
        this.textSecDedRem.ReadOnly = true;
        this.textSecDedRem.Size = new System.Drawing.Size(60, 20);
        this.textSecDedRem.TabIndex = 52;
        this.textSecDedRem.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textNote
        //
        this.textNote.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textNote.BackColor = System.Drawing.Color.White;
        this.textNote.Location = new System.Drawing.Point(0, 654);
        this.textNote.Multiline = true;
        this.textNote.Name = "textNote";
        this.textNote.ReadOnly = true;
        this.textNote.Size = new System.Drawing.Size(698, 52);
        this.textNote.TabIndex = 54;
        //
        // imageListMain
        //
        this.imageListMain.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageListMain.ImageStream")));
        this.imageListMain.TransparentColor = System.Drawing.Color.Transparent;
        this.imageListMain.Images.SetKeyName(0, "");
        this.imageListMain.Images.SetKeyName(1, "");
        this.imageListMain.Images.SetKeyName(2, "");
        this.imageListMain.Images.SetKeyName(3, "Add.gif");
        //
        // textFamPriDed
        //
        this.textFamPriDed.BackColor = System.Drawing.Color.White;
        this.textFamPriDed.Location = new System.Drawing.Point(72, 55);
        this.textFamPriDed.Name = "textFamPriDed";
        this.textFamPriDed.ReadOnly = true;
        this.textFamPriDed.Size = new System.Drawing.Size(60, 20);
        this.textFamPriDed.TabIndex = 65;
        this.textFamPriDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textFamSecDed
        //
        this.textFamSecDed.BackColor = System.Drawing.Color.White;
        this.textFamSecDed.Location = new System.Drawing.Point(131, 55);
        this.textFamSecDed.Name = "textFamSecDed";
        this.textFamSecDed.ReadOnly = true;
        this.textFamSecDed.Size = new System.Drawing.Size(60, 20);
        this.textFamSecDed.TabIndex = 64;
        this.textFamSecDed.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(4, 57);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(66, 15);
        this.label2.TabIndex = 63;
        this.label2.Text = "Fam Ded";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBoxFamilyIns
        //
        this.groupBoxFamilyIns.Controls.Add(this.textFamPriMax);
        this.groupBoxFamilyIns.Controls.Add(this.textFamPriDed);
        this.groupBoxFamilyIns.Controls.Add(this.label3);
        this.groupBoxFamilyIns.Controls.Add(this.label4);
        this.groupBoxFamilyIns.Controls.Add(this.textFamSecMax);
        this.groupBoxFamilyIns.Controls.Add(this.label5);
        this.groupBoxFamilyIns.Controls.Add(this.textFamSecDed);
        this.groupBoxFamilyIns.Controls.Add(this.label2);
        this.groupBoxFamilyIns.Location = new System.Drawing.Point(746, 405);
        this.groupBoxFamilyIns.Name = "groupBoxFamilyIns";
        this.groupBoxFamilyIns.Size = new System.Drawing.Size(193, 80);
        this.groupBoxFamilyIns.TabIndex = 66;
        this.groupBoxFamilyIns.TabStop = false;
        this.groupBoxFamilyIns.Text = "Family Insurance";
        //
        // textFamPriMax
        //
        this.textFamPriMax.BackColor = System.Drawing.Color.White;
        this.textFamPriMax.Location = new System.Drawing.Point(72, 35);
        this.textFamPriMax.Name = "textFamPriMax";
        this.textFamPriMax.ReadOnly = true;
        this.textFamPriMax.Size = new System.Drawing.Size(60, 20);
        this.textFamPriMax.TabIndex = 69;
        this.textFamPriMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(74, 16);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(60, 15);
        this.label3.TabIndex = 66;
        this.label3.Text = "Primary";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(4, 37);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(66, 15);
        this.label4.TabIndex = 67;
        this.label4.Text = "Annual Max";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textFamSecMax
        //
        this.textFamSecMax.BackColor = System.Drawing.Color.White;
        this.textFamSecMax.Location = new System.Drawing.Point(131, 35);
        this.textFamSecMax.Name = "textFamSecMax";
        this.textFamSecMax.ReadOnly = true;
        this.textFamSecMax.Size = new System.Drawing.Size(60, 20);
        this.textFamSecMax.TabIndex = 70;
        this.textFamSecMax.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(131, 16);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(60, 14);
        this.label5.TabIndex = 68;
        this.label5.Text = "Secondary";
        this.label5.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // groupBoxIndIns
        //
        this.groupBoxIndIns.Controls.Add(this.textPriDed);
        this.groupBoxIndIns.Controls.Add(this.textPriUsed);
        this.groupBoxIndIns.Controls.Add(this.textPriDedRem);
        this.groupBoxIndIns.Controls.Add(this.textPriPend);
        this.groupBoxIndIns.Controls.Add(this.textPriRem);
        this.groupBoxIndIns.Controls.Add(this.textPriMax);
        this.groupBoxIndIns.Controls.Add(this.textSecRem);
        this.groupBoxIndIns.Controls.Add(this.label10);
        this.groupBoxIndIns.Controls.Add(this.textSecPend);
        this.groupBoxIndIns.Controls.Add(this.label11);
        this.groupBoxIndIns.Controls.Add(this.label18);
        this.groupBoxIndIns.Controls.Add(this.label12);
        this.groupBoxIndIns.Controls.Add(this.label13);
        this.groupBoxIndIns.Controls.Add(this.textSecDedRem);
        this.groupBoxIndIns.Controls.Add(this.label14);
        this.groupBoxIndIns.Controls.Add(this.label15);
        this.groupBoxIndIns.Controls.Add(this.textSecMax);
        this.groupBoxIndIns.Controls.Add(this.label16);
        this.groupBoxIndIns.Controls.Add(this.textSecDed);
        this.groupBoxIndIns.Controls.Add(this.textSecUsed);
        this.groupBoxIndIns.Location = new System.Drawing.Point(746, 485);
        this.groupBoxIndIns.Name = "groupBoxIndIns";
        this.groupBoxIndIns.Size = new System.Drawing.Size(193, 160);
        this.groupBoxIndIns.TabIndex = 67;
        this.groupBoxIndIns.TabStop = false;
        this.groupBoxIndIns.Text = "Individual Insurance";
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(0, 169);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(745, 482);
        this.gridMain.TabIndex = 59;
        this.gridMain.setTitle("Procedures");
        this.gridMain.setTranslationName("TableTP");
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
        this.gridMain.CellClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // ToolBarMain
        //
        this.ToolBarMain.Dock = System.Windows.Forms.DockStyle.Top;
        this.ToolBarMain.setImageList(this.imageListMain);
        this.ToolBarMain.Location = new System.Drawing.Point(0, 0);
        this.ToolBarMain.Name = "ToolBarMain";
        this.ToolBarMain.Size = new System.Drawing.Size(939, 25);
        this.ToolBarMain.TabIndex = 58;
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
        // gridPreAuth
        //
        this.gridPreAuth.setHScrollVisible(false);
        this.gridPreAuth.Location = new System.Drawing.Point(659, 29);
        this.gridPreAuth.Name = "gridPreAuth";
        this.gridPreAuth.setScrollValue(0);
        this.gridPreAuth.Size = new System.Drawing.Size(252, 134);
        this.gridPreAuth.TabIndex = 62;
        this.gridPreAuth.setTitle("Pre Authorizations");
        this.gridPreAuth.setTranslationName("TablePreAuth");
        this.gridPreAuth.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPreAuth.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPreAuth_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridPreAuth.CellClick = __MultiODGridClickEventHandler.combine(this.gridPreAuth.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPreAuth_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridPlans
        //
        this.gridPlans.setHScrollVisible(false);
        this.gridPlans.Location = new System.Drawing.Point(0, 29);
        this.gridPlans.Name = "gridPlans";
        this.gridPlans.setScrollValue(0);
        this.gridPlans.Size = new System.Drawing.Size(460, 134);
        this.gridPlans.TabIndex = 60;
        this.gridPlans.setTitle("Treatment Plans");
        this.gridPlans.setTranslationName("TableTPList");
        this.gridPlans.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridPlans.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPlans_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridPlans.CellClick = __MultiODGridClickEventHandler.combine(this.gridPlans.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridPlans_CellClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // ContrTreat
        //
        this.Controls.Add(this.groupBoxIndIns);
        this.Controls.Add(this.groupBoxFamilyIns);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.listSetPr);
        this.Controls.Add(this.ToolBarMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridPreAuth);
        this.Controls.Add(this.groupShow);
        this.Controls.Add(this.gridPlans);
        this.Controls.Add(this.textNote);
        this.Name = "ContrTreat";
        this.Size = new System.Drawing.Size(939, 708);
        this.groupShow.ResumeLayout(false);
        this.groupBoxFamilyIns.ResumeLayout(false);
        this.groupBoxFamilyIns.PerformLayout();
        this.groupBoxIndIns.ResumeLayout(false);
        this.groupBoxIndIns.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    /**
    * Only called on startup, but after local data loaded from db.
    */
    public void initializeOnStartup() throws Exception {
        if (InitializedOnStartup)
        {
            return ;
        }
         
        InitializedOnStartup = true;
        checkShowCompleted.Checked = PrefC.getBool(PrefName.TreatPlanShowCompleted);
        //checkShowIns.Checked=PrefC.GetBool(PrefName.TreatPlanShowIns");
        //checkShowDiscount.Checked=PrefC.GetBool(PrefName.TreatPlanShowDiscount");
        //showHidden=true;//shows hidden priorities
        //can't use Lan.F(this);
        //checkShowStandard,
        Lan.C(this, new Control[]{ label1, groupShow, checkShowCompleted, checkShowIns, checkShowDiscount, checkShowMaxDed, checkShowFees, checkShowSubtotals, checkShowTotals, label3, label10, label16, label11, label12, label18, label13, label15, label14, label2 });
        layoutToolBar();
    }

    //redundant?
    /**
    * Called every time local data is changed from any workstation.  Refreshes priority lists and lays out the toolbar.
    */
    public void initializeLocalData() throws Exception {
        listSetPr.Items.Clear();
        listSetPr.Items.Add(Lan.g(this,"no priority"));
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()].Length;i++)
        {
            listSetPr.Items.Add(DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][i].ItemName);
        }
        layoutToolBar();
        if (PrefC.getBool(PrefName.EasyHideInsurance))
        {
            checkShowIns.Visible = false;
            checkShowIns.Checked = false;
            checkShowDiscount.Visible = false;
            checkShowDiscount.Checked = false;
            checkShowMaxDed.Visible = false;
        }
        else
        {
            //checkShowMaxDed.Checked=false;
            checkShowIns.Visible = true;
            checkShowDiscount.Visible = true;
            checkShowMaxDed.Visible = true;
        } 
    }

    /**
    * Causes the toolbar to be laid out again.
    */
    public void layoutToolBar() throws Exception {
        ToolBarMain.getButtons().Clear();
        //ODToolBarButton button;
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"PreAuthorization"), -1, "", "PreAuth"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Update Fees"), 1, "", "Update"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Save TP"), 3, "", "Create"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Print TP"), 2, "", "Print"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Email TP"), -1, "", "Email"));
        ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(Lan.g(this,"Sign TP"), -1, "", "Sign"));
        ArrayList toolButItems = ToolButItems.getForToolBar(ToolBarsAvail.TreatmentPlanModule);
        for (int i = 0;i < toolButItems.Count;i++)
        {
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle.Separator));
            ToolBarMain.getButtons().add(new OpenDental.UI.ODToolBarButton(((ToolButItem)toolButItems[i]).ButtonText, -1, "", ((ToolButItem)toolButItems[i]).ProgramNum));
        }
        ToolBarMain.Invalidate();
        Plugins.hookAddCode(this,"ContrTreat.LayoutToolBar_end",PatCur);
    }

    /**
    * 
    */
    public void moduleSelected(long patNum) throws Exception {
        refreshModuleData(patNum);
        refreshModuleScreen();
        Plugins.hookAddCode(this,"ContrTreat.ModuleSelected_end",patNum);
    }

    /**
    * 
    */
    public void moduleUnselected() throws Exception {
        FamCur = null;
        PatCur = null;
        InsPlanList = null;
        //Claims.List=null;
        //ClaimProcs.List=null;
        //from FillMain:
        ProcList = null;
        ProcListTP = null;
        //Procedures.HList=null;
        //Procedures.MissingTeeth=null;
        Plugins.hookAddCode(this,"ContrTreat.ModuleUnselected_end");
    }

    private void refreshModuleData(long patNum) throws Exception {
        if (patNum != 0)
        {
            FamCur = Patients.getFamily(patNum);
            PatCur = FamCur.getPatient(patNum);
            SubList = InsSubs.refreshForFam(FamCur);
            InsPlanList = InsPlans.refreshForSubList(SubList);
            PatPlanList = PatPlans.refresh(patNum);
            BenefitList = Benefits.refresh(PatPlanList,SubList);
            ClaimList = Claims.refresh(PatCur.PatNum);
            HistList = ClaimProcs.getHistList(PatCur.PatNum,BenefitList,PatPlanList,InsPlanList,DateTimeOD.getToday(),SubList);
        }
         
    }

    private void refreshModuleScreen() throws Exception {
        //ParentForm.Text=Patients.GetMainTitle(PatCur);
        if (PatCur != null)
        {
            gridMain.Enabled = true;
            groupShow.Enabled = true;
            listSetPr.Enabled = true;
            //panelSide.Enabled=true;
            ToolBarMain.getButtons().get___idx("PreAuth").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Update").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Create").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Print").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Email").setEnabled(true);
            ToolBarMain.getButtons().get___idx("Sign").setEnabled(true);
            ToolBarMain.Invalidate();
            if (PatPlanList.Count == 0)
            {
                //patient doesn't have insurance
                checkShowIns.Checked = false;
                checkShowDiscount.Checked = false;
                checkShowMaxDed.Visible = false;
            }
            else
            {
                //patient has insurance
                if (!PrefC.getBool(PrefName.EasyHideInsurance))
                {
                    //if insurance isn't hidden
                    checkShowMaxDed.Visible = true;
                    if (checkShowFees.Checked)
                    {
                        //if fees are showing
                        if (!checkShowInsNotAutomatic)
                        {
                            checkShowIns.Checked = true;
                        }
                         
                        InsSub sub = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
                        InsPlan plan = InsPlans.getPlan(sub.PlanNum,InsPlanList);
                        if (StringSupport.equals(plan.PlanType, "p") || StringSupport.equals(plan.PlanType, "c"))
                        {
                            //ppo or cap
                            checkShowDiscount.Checked = true;
                        }
                        else
                        {
                            checkShowDiscount.Checked = false;
                        } 
                    }
                     
                }
                 
            } 
        }
        else
        {
            gridMain.Enabled = false;
            groupShow.Enabled = false;
            listSetPr.Enabled = false;
            //panelSide.Enabled=false;
            ToolBarMain.getButtons().get___idx("PreAuth").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Update").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Create").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Print").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Email").setEnabled(false);
            ToolBarMain.getButtons().get___idx("Sign").setEnabled(false);
            ToolBarMain.Invalidate();
        } 
        //listPreAuth.Enabled=false;
        fillPlans();
        fillMain();
        fillSummary();
        fillPreAuth();
    }

    //FillMisc();
    private void toolBarMain_ButtonClick(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception {
        if (e.getButton().getTag().GetType() == String.class)
        {
            //standard predefined button
            Object.APPLY __dummyScrutVar0 = e.getButton().getTag().ToString();
            if (__dummyScrutVar0.equals("PreAuth"))
            {
                toolBarMainPreAuth_Click();
            }
            else if (__dummyScrutVar0.equals("Update"))
            {
                toolBarMainUpdate_Click();
            }
            else if (__dummyScrutVar0.equals("Create"))
            {
                toolBarMainCreate_Click();
            }
            else if (__dummyScrutVar0.equals("Print"))
            {
                toolBarMainPrint_Click();
            }
            else if (__dummyScrutVar0.equals("Email"))
            {
                toolBarMainEmail_Click();
            }
            else if (__dummyScrutVar0.equals("Sign"))
            {
                toolBarMainSign_Click();
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

    private void fillPlans() throws Exception {
        gridPlans.beginUpdate();
        gridPlans.getColumns().Clear();
        OpenDental.UI.ODGridColumn col = new OpenDental.UI.ODGridColumn(Lan.g("TableTPList","Date"),80);
        gridPlans.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTPList","Heading"),310);
        gridPlans.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TableTPList","Signed"), 80, HorizontalAlignment.Center);
        gridPlans.getColumns().add(col);
        gridPlans.getRows().Clear();
        if (PatCur == null)
        {
            gridPlans.endUpdate();
            return ;
        }
         
        ProcList = Procedures.refresh(PatCur.PatNum);
        ProcListTP = Procedures.getListTP(ProcList);
        //sorted by priority, then toothnum
        PlanList = TreatPlans.refresh(PatCur.PatNum);
        ProcTPList = ProcTPs.refresh(PatCur.PatNum);
        OpenDental.UI.ODGridRow row;
        row = new OpenDental.UI.ODGridRow();
        row.getCells().add("");
        //date empty
        row.getCells().add(Lan.g(this,"Default"));
        gridPlans.getRows().add(row);
        String str = new String();
        for (int i = 0;i < PlanList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(PlanList[i].DateTP.ToShortDateString());
            str = PlanList[i].Heading;
            if (PlanList[i].ResponsParty != 0)
            {
                str += "\r\n" + Lan.g(this,"Responsible Party: ") + Patients.GetLim(PlanList[i].ResponsParty).GetNameLF();
            }
             
            row.getCells().add(str);
            if (StringSupport.equals(PlanList[i].Signature, ""))
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().add("X");
            } 
            gridPlans.getRows().add(row);
        }
        gridPlans.endUpdate();
        gridPlans.setSelected(0,true);
    }

    private void fillMain() throws Exception {
        fillMainData();
        fillMainDisplay();
    }

    /**
    * Fills RowsMain list for gridMain display.
    */
    private void fillMainData() throws Exception {
        if (PatCur == null)
        {
            return ;
        }
         
        double fee = new double();
        double priIns = new double();
        double secIns = new double();
        double discount = new double();
        double pat = new double();
        double subfee = 0;
        double subpriIns = 0;
        double subsecIns = 0;
        double subdiscount = 0;
        double subpat = 0;
        double totFee = 0;
        double totPriIns = 0;
        double totSecIns = 0;
        double totDiscount = 0;
        double totPat = 0;
        long feeSched = Providers.getProv(Patients.getProvNum(PatCur)).FeeSched;
        //for standard fee
        RowsMain = new List<TpRow>();
        TpRow row;
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            //current treatplan selected
            InsPlan PriPlanCur = null;
            InsSub PriSubCur = null;
            if (PatPlanList.Count > 0)
            {
                //primary
                PriSubCur = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
                PriPlanCur = InsPlans.getPlan(PriSubCur.PlanNum,InsPlanList);
            }
             
            InsPlan SecPlanCur = null;
            InsSub SecSubCur = null;
            if (PatPlanList.Count > 1)
            {
                //secondary
                SecSubCur = InsSubs.GetSub(PatPlanList[1].InsSubNum, SubList);
                SecPlanCur = InsPlans.getPlan(SecSubCur.PlanNum,InsPlanList);
            }
             
            ClaimProc claimproc;
            //holds the estimate.
            String descript = new String();
            //One thing to watch out for here is that we must be absolutely sure to include all claimprocs for the procedures listed,
            //regardless of status.  Needed for Procedures.ComputeEstimates.  This should be fine.
            ClaimProcList = ClaimProcs.refreshForTP(PatCur.PatNum);
            List<ClaimProc> claimProcListOld = new List<ClaimProc>();
            for (int i = 0;i < ClaimProcList.Count;i++)
            {
                //This didn't work:(ClaimProcList);//make a copy
                claimProcListOld.Add(ClaimProcList[i].Copy());
            }
            LoopList = new List<ClaimProcHist>();
            for (int i = 0;i < ProcListTP.Length;i++)
            {
                RefSupport refVar___0 = new RefSupport(ClaimProcList);
                Procedures.ComputeEstimates(ProcListTP[i], PatCur.PatNum, refVar___0, false, InsPlanList, PatPlanList, BenefitList, HistList, LoopList, false, PatCur.getAge(), SubList);
                ClaimProcList = refVar___0.getValue();
                //then, add this information to loopList so that the next procedure is aware of it.
                LoopList.AddRange(ClaimProcs.GetHistForProc(ClaimProcList, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
            }
            //save changes in the list to the database
            RefSupport refVar___1 = new RefSupport(ClaimProcList);
            ClaimProcs.Synch(refVar___1, claimProcListOld);
            ClaimProcList = refVar___1.getValue();
            //claimProcList=ClaimProcs.RefreshForTP(PatCur.PatNum);
            String estimateNote = new String();
            for (int i = 0;i < ProcListTP.Length;i++)
            {
                row = new TpRow();
                fee = (double)ProcListTP[i].ProcFee;
                int qty = ProcListTP[i].BaseUnits + ProcListTP[i].UnitQty;
                if (qty > 0)
                {
                    fee *= qty;
                }
                 
                subfee += fee;
                totFee += fee;
                String showPriDeduct = "";
                String showSecDeduct = "";
                if (PatPlanList.Count > 0)
                {
                    //Primary
                    claimproc = ClaimProcs.GetEstimate(ClaimProcList, ProcListTP[i].ProcNum, PriPlanCur.PlanNum, PatPlanList[0].InsSubNum);
                    if (claimproc == null)
                    {
                        priIns = 0;
                    }
                    else
                    {
                        if (checkShowMaxDed.Checked)
                        {
                            //whether visible or not
                            priIns = (double)ClaimProcs.getInsEstTotal(claimproc);
                            double ded = ClaimProcs.getDeductibleDisplay(claimproc);
                            if (ded > 0)
                            {
                                showPriDeduct = "\r\n" + Lan.g(this,"Pri Deduct Applied: ") + ded.ToString("c");
                            }
                             
                        }
                        else
                        {
                            priIns = (double)claimproc.BaseEst;
                        } 
                    } 
                }
                else
                {
                    //no primary ins
                    priIns = 0;
                } 
                if (PatPlanList.Count > 1)
                {
                    //Secondary
                    claimproc = ClaimProcs.GetEstimate(ClaimProcList, ProcListTP[i].ProcNum, SecPlanCur.PlanNum, PatPlanList[1].InsSubNum);
                    if (claimproc == null)
                    {
                        secIns = 0;
                    }
                    else
                    {
                        if (checkShowMaxDed.Checked)
                        {
                            secIns = (double)ClaimProcs.getInsEstTotal(claimproc);
                            double ded = (double)ClaimProcs.getDeductibleDisplay(claimproc);
                            if (ded > 0)
                            {
                                showSecDeduct = "\r\n" + Lan.g(this,"Sec Deduct Applied: ") + ded.ToString("c");
                            }
                             
                        }
                        else
                        {
                            secIns = (double)claimproc.BaseEst;
                        } 
                    } 
                }
                else
                {
                    //secondary
                    //no secondary ins
                    secIns = 0;
                } 
                subpriIns += priIns;
                totPriIns += priIns;
                subsecIns += secIns;
                totSecIns += secIns;
                discount = (double)ClaimProcs.GetTotalWriteOffEstimateDisplay(ClaimProcList, ProcListTP[i].ProcNum);
                subdiscount += discount;
                totDiscount += discount;
                pat = fee - priIns - secIns - discount;
                if (pat < 0)
                {
                    pat = 0;
                }
                 
                subpat += pat;
                totPat += pat;
                //Fill TpRow object with information.
                row.Priority = (DefC.GetName(DefCat.TxPriorities, ProcListTP[i].Priority));
                row.Tth = (Tooth.ToInternat(ProcListTP[i].ToothNum));
                if (ProcedureCodes.GetProcCode(ProcListTP[i].CodeNum).TreatArea == TreatmentArea.Surf)
                {
                    row.Surf = (Tooth.SurfTidyFromDbToDisplay(ProcListTP[i].Surf, ProcListTP[i].ToothNum));
                }
                else
                {
                    row.Surf = (ProcListTP[i].Surf);
                } 
                //I think this will properly allow UR, L, etc.
                row.Code = (ProcedureCodes.GetProcCode(ProcListTP[i].CodeNum).ProcCode);
                descript = ProcedureCodes.GetLaymanTerm(ProcListTP[i].CodeNum);
                if (!StringSupport.equals(ProcListTP[i].ToothRange, ""))
                {
                    descript += " #" + Tooth.FormatRangeForDisplay(ProcListTP[i].ToothRange);
                }
                 
                if (checkShowMaxDed.Checked)
                {
                    estimateNote = ClaimProcs.GetEstimateNotes(ProcListTP[i].ProcNum, ClaimProcList);
                    if (!StringSupport.equals(estimateNote, ""))
                    {
                        descript += "\r\n" + estimateNote;
                    }
                     
                }
                 
                row.Description = (descript);
                if (!StringSupport.equals(showPriDeduct, ""))
                {
                    row.Description += showPriDeduct;
                }
                 
                if (!StringSupport.equals(showSecDeduct, ""))
                {
                    row.Description += showSecDeduct;
                }
                 
                row.Prognosis = DefC.GetName(DefCat.Prognosis, PIn.Long(ProcListTP[i].Prognosis.ToString()));
                row.Dx = DefC.GetValue(DefCat.Diagnosis, PIn.Long(ProcListTP[i].Dx.ToString()));
                row.Fee = fee;
                row.PriIns = priIns;
                row.SecIns = secIns;
                row.Discount = discount;
                row.Pat = pat;
                row.ColorText = DefC.GetColor(DefCat.TxPriorities, ProcListTP[i].Priority);
                if (row.ColorText == System.Drawing.Color.White)
                {
                    row.ColorText = System.Drawing.Color.Black;
                }
                 
                row.Tag = ProcListTP[i].Copy();
                RowsMain.Add(row);
                /*
                					if(ProcListTP[i].LabProcCode!=""){
                						row=new ODGridRow();
                						row.Cells.Add("");//done
                						row.Cells.Add(DefB.GetName(DefCat.TxPriorities,ProcListTP[i].Priority));//priority
                						row.Cells.Add(Tooth.ToInternat(ProcListTP[i].ToothNum));//toothnum
                						row.Cells.Add("");//surf
                						row.Cells.Add(ProcListTP[i].LabProcCode);//proccode
                						row.Cells.Add("    "+ProcedureCodes.GetLaymanTerm(ProcListTP[i].LabProcCode));//descript (indented)
                						if(checkShowStandard.Checked) {
                							row.Cells.Add("");//standard
                						}
                						fee=ProcListTP[i].LabFee;
                						subfee+=fee;
                						totFee+=fee;
                				//possibly incomplete. Insurance not considered
                						pat=fee;//-priIns-secIns;
                						if(pat<0) {
                							pat=0;
                						}
                						subpat+=pat;
                						totPat+=pat;
                						if(checkShowFees.Checked) {
                							row.Cells.Add(ProcListTP[i].LabFee.ToString("F"));//fee
                						}
                						if(checkShowIns.Checked) {
                							row.Cells.Add("");//pri
                							row.Cells.Add("");//sec
                							row.Cells.Add("");//pat portion
                						}
                						row.ColorText=DefB.GetColor(DefCat.TxPriorities,ProcListTP[i].Priority);
                						if(row.ColorText==Color.White) {
                							row.ColorText=Color.Black;
                						}
                						row.Tag=ProcListTP[i].Clone();
                						gridMain.Rows.Add(row);
                					}*/
                if (checkShowSubtotals.Checked && (i == ProcListTP.Length - 1 || ProcListTP[i + 1].Priority != ProcListTP[i].Priority))
                {
                    row = new TpRow();
                    row.Description = "Subtotal";
                    row.Fee = subfee;
                    row.PriIns = subpriIns;
                    row.SecIns = subsecIns;
                    row.Discount = subdiscount;
                    row.Pat = subpat;
                    row.ColorText = DefC.GetColor(DefCat.TxPriorities, ProcListTP[i].Priority);
                    if (row.ColorText == System.Drawing.Color.White)
                    {
                        row.ColorText = System.Drawing.Color.Black;
                    }
                     
                    row.Bold = true;
                    row.ColorLborder = System.Drawing.Color.Black;
                    RowsMain.Add(row);
                    subfee = 0;
                    subpriIns = 0;
                    subsecIns = 0;
                    subdiscount = 0;
                    subpat = 0;
                }
                 
            }
            //for(int i=0;i<ProcListTP.Length
            textNote.Text = PrefC.getString(PrefName.TreatmentPlanNote);
        }
        else
        {
            //any except current tp selected
            ProcTPSelectList = ProcTPs.GetListForTP(PlanList[gridPlans.getSelectedIndices()[0] - 1].TreatPlanNum, ProcTPList);
            boolean isDone = new boolean();
            for (int i = 0;i < ProcTPSelectList.Length;i++)
            {
                row = new TpRow();
                isDone = false;
                for (int j = 0;j < ProcList.Count;j++)
                {
                    if (ProcList[j].ProcNum == ProcTPSelectList[i].ProcNumOrig)
                    {
                        if (ProcList[j].ProcStatus == ProcStat.C)
                        {
                            isDone = true;
                        }
                         
                    }
                     
                }
                if (isDone)
                {
                    row.Done = "X";
                }
                 
                row.Priority = DefC.GetName(DefCat.TxPriorities, ProcTPSelectList[i].Priority);
                row.Tth = ProcTPSelectList[i].ToothNumTP;
                row.Surf = ProcTPSelectList[i].Surf;
                row.Code = ProcTPSelectList[i].ProcCode;
                row.Description = ProcTPSelectList[i].Descript;
                row.Fee = (double)ProcTPSelectList[i].FeeAmt;
                //Fee
                subfee += (double)ProcTPSelectList[i].FeeAmt;
                totFee += (double)ProcTPSelectList[i].FeeAmt;
                row.PriIns = (double)ProcTPSelectList[i].PriInsAmt;
                //PriIns
                subpriIns += (double)ProcTPSelectList[i].PriInsAmt;
                totPriIns += (double)ProcTPSelectList[i].PriInsAmt;
                row.SecIns = (double)ProcTPSelectList[i].SecInsAmt;
                //SecIns
                subsecIns += (double)ProcTPSelectList[i].SecInsAmt;
                totSecIns += (double)ProcTPSelectList[i].SecInsAmt;
                row.Discount = (double)ProcTPSelectList[i].Discount;
                //Discount
                subdiscount += (double)ProcTPSelectList[i].Discount;
                totDiscount += (double)ProcTPSelectList[i].Discount;
                row.Pat = (double)ProcTPSelectList[i].PatAmt;
                //Pat
                subpat += (double)ProcTPSelectList[i].PatAmt;
                totPat += (double)ProcTPSelectList[i].PatAmt;
                row.Prognosis = ProcTPSelectList[i].Prognosis;
                //Prognosis
                row.Dx = ProcTPSelectList[i].Dx;
                //if(checkShowStandard.Checked) {
                //	standard=Fees.GetAmount0(ProcedureCodes.GetCodeNum(ProcTPSelectList[i].ProcCode),feeSched);
                //	row.Cells.Add(standard.ToString("F"));//standard
                //	substandard+=standard;
                //	totStandard+=standard;
                //}
                row.ColorText = DefC.GetColor(DefCat.TxPriorities, ProcTPSelectList[i].Priority);
                if (row.ColorText == System.Drawing.Color.White)
                {
                    row.ColorText = System.Drawing.Color.Black;
                }
                 
                row.Tag = ProcTPSelectList[i].Copy();
                RowsMain.Add(row);
                if (checkShowSubtotals.Checked && (i == ProcTPSelectList.Length - 1 || ProcTPSelectList[i + 1].Priority != ProcTPSelectList[i].Priority))
                {
                    row = new TpRow();
                    row.Description = "Subtotal";
                    row.Fee = subfee;
                    row.PriIns = subpriIns;
                    row.SecIns = subsecIns;
                    row.Discount = subdiscount;
                    row.Pat = subpat;
                    //if(checkShowStandard.Checked) {
                    //	row.Cells.Add(substandard.ToString("F"));//standard
                    //}
                    row.ColorText = DefC.GetColor(DefCat.TxPriorities, ProcTPSelectList[i].Priority);
                    if (row.ColorText == System.Drawing.Color.White)
                    {
                        row.ColorText = System.Drawing.Color.Black;
                    }
                     
                    row.Bold = true;
                    row.ColorLborder = System.Drawing.Color.Black;
                    RowsMain.Add(row);
                    //substandard=0;
                    subfee = 0;
                    subpriIns = 0;
                    subsecIns = 0;
                    subdiscount = 0;
                    subpat = 0;
                }
                 
            }
            textNote.Text = PlanList[gridPlans.getSelectedIndices()[0] - 1].Note;
        } 
        if (checkShowTotals.Checked)
        {
            row = new TpRow();
            row.Description = "Total";
            row.Fee = totFee;
            row.PriIns = totPriIns;
            row.SecIns = totSecIns;
            row.Discount = totDiscount;
            row.Pat = totPat;
            //if(checkShowStandard.Checked) {
            //	row.Cells.Add(totStandard.ToString("F"));//standard
            //}
            row.Bold = true;
            row.ColorText = System.Drawing.Color.Black;
            RowsMain.Add(row);
        }
         
    }

    private void fillMainDisplay() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        OpenDental.UI.ODGridColumn col;
        DisplayFields.refreshCache();
        //probably needs to be removed.
        List<DisplayField> fields = DisplayFields.getForCategory(DisplayFieldCategory.TreatmentPlanModule);
        for (int i = 0;i < fields.Count;i++)
        {
            if (StringSupport.equals(fields[i].Description, ""))
            {
                col = new OpenDental.UI.ODGridColumn(fields[i].InternalName, fields[i].ColumnWidth);
            }
            else
            {
                col = new OpenDental.UI.ODGridColumn(fields[i].Description, fields[i].ColumnWidth);
            } 
            if (StringSupport.equals(fields[i].InternalName, "Fee") && !checkShowFees.Checked)
            {
                continue;
            }
             
            if ((StringSupport.equals(fields[i].InternalName, "Pri Ins") || StringSupport.equals(fields[i].InternalName, "Sec Ins")) && !checkShowIns.Checked)
            {
                continue;
            }
             
            if (StringSupport.equals(fields[i].InternalName, "Discount") && !checkShowDiscount.Checked)
            {
                continue;
            }
             
            if (StringSupport.equals(fields[i].InternalName, "Pat") && !checkShowIns.Checked)
            {
                continue;
            }
             
            if (StringSupport.equals(fields[i].InternalName, "Fee") || StringSupport.equals(fields[i].InternalName, "Pri Ins") || StringSupport.equals(fields[i].InternalName, "Sec Ins") || StringSupport.equals(fields[i].InternalName, "Discount") || StringSupport.equals(fields[i].InternalName, "Pat"))
            {
                col.setTextAlign(HorizontalAlignment.Right);
            }
             
            gridMain.getColumns().add(col);
        }
        gridMain.getRows().Clear();
        if (PatCur == null)
        {
            gridMain.endUpdate();
            return ;
        }
         
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RowsMain.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int j = 0;j < fields.Count;j++)
            {
                InternalName __dummyScrutVar1 = fields[j].InternalName;
                if (__dummyScrutVar1.equals("Done"))
                {
                    if (RowsMain[i].Done != null)
                    {
                        row.getCells().Add(RowsMain[i].Done.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Priority"))
                {
                    if (RowsMain[i].Priority != null)
                    {
                        row.getCells().Add(RowsMain[i].Priority.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Tth"))
                {
                    if (RowsMain[i].Tth != null)
                    {
                        row.getCells().Add(RowsMain[i].Tth.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Surf"))
                {
                    if (RowsMain[i].Surf != null)
                    {
                        row.getCells().Add(RowsMain[i].Surf.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Code"))
                {
                    if (RowsMain[i].Code != null)
                    {
                        row.getCells().Add(RowsMain[i].Code.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Description"))
                {
                    if (RowsMain[i].Description != null)
                    {
                        row.getCells().Add(RowsMain[i].Description.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Fee"))
                {
                    if (checkShowFees.Checked)
                    {
                        row.getCells().Add(RowsMain[i].Fee.ToString("F"));
                    }
                     
                }
                else if (__dummyScrutVar1.equals("Pri Ins"))
                {
                    if (checkShowIns.Checked)
                    {
                        row.getCells().Add(RowsMain[i].PriIns.ToString("F"));
                    }
                     
                }
                else if (__dummyScrutVar1.equals("Sec Ins"))
                {
                    if (checkShowIns.Checked)
                    {
                        row.getCells().Add(RowsMain[i].SecIns.ToString("F"));
                    }
                     
                }
                else if (__dummyScrutVar1.equals("Discount"))
                {
                    if (checkShowDiscount.Checked)
                    {
                        row.getCells().Add(RowsMain[i].Discount.ToString("F"));
                    }
                     
                }
                else if (__dummyScrutVar1.equals("Pat"))
                {
                    if (checkShowIns.Checked)
                    {
                        row.getCells().Add(RowsMain[i].Pat.ToString("F"));
                    }
                     
                }
                else if (__dummyScrutVar1.equals("Prognosis"))
                {
                    if (RowsMain[i].Prognosis != null)
                    {
                        row.getCells().Add(RowsMain[i].Prognosis.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                else if (__dummyScrutVar1.equals("Dx"))
                {
                    if (RowsMain[i].Dx != null)
                    {
                        row.getCells().Add(RowsMain[i].Dx.ToString());
                    }
                    else
                    {
                        row.getCells().add("");
                    } 
                }
                             
            }
            if (RowsMain[i].ColorText != null)
            {
                row.setColorText(RowsMain[i].ColorText);
            }
             
            if (RowsMain[i].ColorLborder != null)
            {
                row.setColorLborder(RowsMain[i].ColorLborder);
            }
             
            if (RowsMain[i].Tag != null)
            {
                row.setTag(RowsMain[i].Tag);
            }
             
            row.setBold(RowsMain[i].Bold);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillSummary() throws Exception {
        textFamPriMax.Text = "";
        textFamPriDed.Text = "";
        textFamSecMax.Text = "";
        textFamSecDed.Text = "";
        textPriMax.Text = "";
        textPriDed.Text = "";
        textPriDedRem.Text = "";
        textPriUsed.Text = "";
        textPriPend.Text = "";
        textPriRem.Text = "";
        textSecMax.Text = "";
        textSecDed.Text = "";
        textSecDedRem.Text = "";
        textSecUsed.Text = "";
        textSecPend.Text = "";
        textSecRem.Text = "";
        if (PatCur == null)
        {
            return ;
        }
         
        double maxFam = 0;
        double maxInd = 0;
        double ded = 0;
        double dedFam = 0;
        double dedRem = 0;
        double remain = 0;
        double pend = 0;
        double used = 0;
        InsPlan PlanCur;
        //=new InsPlan();
        InsSub SubCur;
        if (PatPlanList.Count > 0)
        {
            SubCur = InsSubs.GetSub(PatPlanList[0].InsSubNum, SubList);
            PlanCur = InsPlans.getPlan(SubCur.PlanNum,InsPlanList);
            pend = InsPlans.GetPendingDisplay(HistList, DateTime.Today, PlanCur, PatPlanList[0].PatPlanNum, -1, PatCur.PatNum, PatPlanList[0].InsSubNum, BenefitList);
            used = InsPlans.GetInsUsedDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, -1, InsPlanList, BenefitList, PatCur.PatNum, PatPlanList[0].InsSubNum);
            textPriPend.Text = pend.ToString("F");
            textPriUsed.Text = used.ToString("F");
            maxFam = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, true);
            maxInd = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, false);
            if (maxFam == -1)
            {
                textFamPriMax.Text = "";
            }
            else
            {
                textFamPriMax.Text = maxFam.ToString("F");
            } 
            if (maxInd == -1)
            {
                //if annual max is blank
                textPriMax.Text = "";
                textPriRem.Text = "";
            }
            else
            {
                remain = maxInd - used - pend;
                if (remain < 0)
                {
                    remain = 0;
                }
                 
                //textFamPriMax.Text=max.ToString("F");
                textPriMax.Text = maxInd.ToString("F");
                textPriRem.Text = remain.ToString("F");
            } 
            //deductible:
            ded = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, BenefitCoverageLevel.Individual);
            dedFam = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, BenefitCoverageLevel.Family);
            if (ded != -1)
            {
                textPriDed.Text = ded.ToString("F");
                dedRem = InsPlans.GetDedRemainDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[0].PatPlanNum, -1, InsPlanList, PatCur.PatNum, ded, dedFam);
                textPriDedRem.Text = dedRem.ToString("F");
            }
             
            if (dedFam != -1)
            {
                textFamPriDed.Text = dedFam.ToString("F");
            }
             
        }
         
        if (PatPlanList.Count > 1)
        {
            SubCur = InsSubs.GetSub(PatPlanList[1].InsSubNum, SubList);
            PlanCur = InsPlans.getPlan(SubCur.PlanNum,InsPlanList);
            pend = InsPlans.GetPendingDisplay(HistList, DateTime.Today, PlanCur, PatPlanList[1].PatPlanNum, -1, PatCur.PatNum, PatPlanList[1].InsSubNum, BenefitList);
            textSecPend.Text = pend.ToString("F");
            used = InsPlans.GetInsUsedDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, -1, InsPlanList, BenefitList, PatCur.PatNum, PatPlanList[1].InsSubNum);
            textSecUsed.Text = used.ToString("F");
            //max=Benefits.GetAnnualMaxDisplay(BenefitList,PlanCur.PlanNum,PatPlanList[1].PatPlanNum);
            maxFam = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, true);
            maxInd = Benefits.GetAnnualMaxDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, false);
            if (maxFam == -1)
            {
                textFamSecMax.Text = "";
            }
            else
            {
                textFamSecMax.Text = maxFam.ToString("F");
            } 
            if (maxInd == -1)
            {
                //if annual max is blank
                textSecMax.Text = "";
                textSecRem.Text = "";
            }
            else
            {
                remain = maxInd - used - pend;
                if (remain < 0)
                {
                    remain = 0;
                }
                 
                //textFamSecMax.Text=max.ToString("F");
                textSecMax.Text = maxInd.ToString("F");
                textSecRem.Text = remain.ToString("F");
            } 
            //deductible:
            ded = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, BenefitCoverageLevel.Individual);
            dedFam = Benefits.GetDeductGeneralDisplay(BenefitList, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, BenefitCoverageLevel.Family);
            if (ded != -1)
            {
                textSecDed.Text = ded.ToString("F");
                dedRem = InsPlans.GetDedRemainDisplay(HistList, DateTime.Today, PlanCur.PlanNum, PatPlanList[1].PatPlanNum, -1, InsPlanList, PatCur.PatNum, ded, dedFam);
                textSecDedRem.Text = dedRem.ToString("F");
            }
             
            if (dedFam != -1)
            {
                textFamSecDed.Text = dedFam.ToString("F");
            }
             
        }
         
    }

    private void fillPreAuth() throws Exception {
        gridPreAuth.beginUpdate();
        gridPreAuth.getColumns().Clear();
        OpenDental.UI.ODGridColumn col = new OpenDental.UI.ODGridColumn(Lan.g("TablePreAuth","Date Sent"),80);
        gridPreAuth.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TablePreAuth","Carrier"),100);
        gridPreAuth.getColumns().add(col);
        col = new OpenDental.UI.ODGridColumn(Lan.g("TablePreAuth","Status"),53);
        gridPreAuth.getColumns().add(col);
        gridPreAuth.getRows().Clear();
        if (PatCur == null)
        {
            gridPreAuth.endUpdate();
            return ;
        }
         
        ALPreAuth = new ArrayList();
        for (int i = 0;i < ClaimList.Count;i++)
        {
            if (StringSupport.equals(ClaimList[i].ClaimType, "PreAuth"))
            {
                ALPreAuth.Add(ClaimList[i]);
            }
             
        }
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ALPreAuth.Count;i++)
        {
            InsPlan PlanCur = InsPlans.getPlan(((Claim)ALPreAuth[i]).PlanNum,InsPlanList);
            row = new OpenDental.UI.ODGridRow();
            if (((Claim)ALPreAuth[i]).DateSent.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(((Claim)ALPreAuth[i]).DateSent.ToShortDateString());
            } 
            row.getCells().add(Carriers.getName(PlanCur.CarrierNum));
            row.getCells().Add(((Claim)ALPreAuth[i]).ClaimStatus.ToString());
            gridPreAuth.getRows().add(row);
        }
        gridPreAuth.endUpdate();
    }

    private void gridMain_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        gridPreAuth.setSelected(false);
    }

    //is this a desirable behavior?
    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridMain.getRows().get___idx(e.getRow()).getTag() == null)
        {
            return ;
        }
         
        //user double clicked on a subtotal row
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            //current plan
            Procedure ProcCur = Procedures.getOneProc(((Procedure)gridMain.getRows().get___idx(e.getRow()).getTag()).ProcNum,true);
            //generate a new loop list containing only the procs before this one in it
            LoopList = new List<ClaimProcHist>();
            for (int i = 0;i < ProcListTP.Length;i++)
            {
                if (ProcListTP[i].ProcNum == ProcCur.ProcNum)
                {
                    break;
                }
                 
                LoopList.AddRange(ClaimProcs.GetHistForProc(ClaimProcList, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
            }
            FormProcEdit FormPE = new FormProcEdit(ProcCur,PatCur,FamCur);
            FormPE.LoopList = LoopList;
            FormPE.HistList = HistList;
            FormPE.ShowDialog();
            moduleSelected(PatCur.PatNum);
            for (int i = 0;i < gridMain.getRows().Count;i++)
            {
                if (gridMain.getRows().get___idx(i).getTag() != null && ((Procedure)gridMain.getRows().get___idx(i).getTag()).ProcNum == ProcCur.ProcNum)
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
            return ;
        }
         
        //any other TP
        ProcTP procT = (ProcTP)gridMain.getRows().get___idx(e.getRow()).getTag();
        DateTime dateTP = PlanList[gridPlans.getSelectedIndices()[0] - 1].DateTP;
        FormProcTPEdit FormP = new FormProcTPEdit(procT,dateTP);
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        int selectedPlanI = gridPlans.getSelectedIndices()[0];
        long selectedProc = procT.ProcTPNum;
        moduleSelected(PatCur.PatNum);
        gridPlans.setSelected(selectedPlanI,true);
        fillMain();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getTag() != null && ((ProcTP)gridMain.getRows().get___idx(i).getTag()).ProcTPNum == selectedProc)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void gridPlans_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        fillMain();
        gridPreAuth.setSelected(false);
    }

    private void gridPlans_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (e.getRow() == 0)
        {
            return ;
        }
         
        //there is nothing to edit if user clicks on current.
        long tpNum = PlanList[e.getRow() - 1].TreatPlanNum;
        FormTreatPlanEdit FormT = new FormTreatPlanEdit(PlanList[e.getRow() - 1]);
        FormT.ShowDialog();
        moduleSelected(PatCur.PatNum);
        for (int i = 0;i < PlanList.Length;i++)
        {
            if (PlanList[i].TreatPlanNum == tpNum)
            {
                gridPlans.setSelected(i + 1,true);
            }
             
        }
        fillMain();
    }

    private void listSetPr_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        int clickedRow = listSetPr.IndexFromPoint(e.X, e.Y);
        if (clickedRow == -1)
        {
            return ;
        }
         
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //current TP
                //Procedure ProcCur;
                //Procedure ProcOld;
                //loop through the main list of selected procs
                if (gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag == null)
                {
                    continue;
                }
                 
                //user must have highlighted a subtotal row, so ignore
                //ProcCur=(Procedure)gridMain.Rows[gridMain.SelectedIndices[i]].Tag;
                //ProcOld=ProcCur.Clone();
                if (clickedRow == 0)
                {
                    //set priority to "no priority"
                    //ProcCur.Priority=0;
                    Procedures.UpdatePriority(((Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag).ProcNum, 0);
                }
                else
                {
                    //ProcCur.Priority=DefC.Short[(int)DefCat.TxPriorities][clickedRow-1].DefNum;
                    Procedures.UpdatePriority(((Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag).ProcNum, DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][clickedRow - 1].DefNum);
                } 
            }
            //Procedures.Update(ProcCur,ProcOld);//no recall synch required
            moduleSelected(PatCur.PatNum);
        }
        else
        {
            //any other TP
            DateTime dateTP = PlanList[gridPlans.getSelectedIndices()[0] - 1].DateTP;
            if (!Security.isAuthorized(Permissions.TreatPlanEdit,dateTP))
            {
                return ;
            }
             
            int selectedTP = gridPlans.getSelectedIndices()[0];
            ProcTP proc;
            for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
            {
                //loop through the main list of selected procTPs
                if (gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag == null)
                {
                    continue;
                }
                 
                //user must have highlighted a subtotal row, so ignore
                proc = (ProcTP)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag;
                if (clickedRow == 0)
                {
                    //set priority to "no priority"
                    proc.Priority = 0;
                }
                else
                {
                    proc.Priority = DefC.getShort()[((Enum)DefCat.TxPriorities).ordinal()][clickedRow - 1].DefNum;
                } 
                ProcTPs.insertOrUpdate(proc,false);
            }
            moduleSelected(PatCur.PatNum);
            gridPlans.setSelected(selectedTP,true);
            fillMain();
        } 
    }

    private void checkShowMaxDed_Click(Object sender, EventArgs e) throws Exception {
        fillMain();
    }

    private void checkShowFees_Click(Object sender, EventArgs e) throws Exception {
        if (checkShowFees.Checked)
        {
            //checkShowStandard.Checked=true;
            if (!checkShowInsNotAutomatic)
            {
                checkShowIns.Checked = true;
            }
             
            //if(PrefC.GetBool(PrefName.TreatPlanShowDiscount")){
            checkShowDiscount.Checked = true;
            //}
            checkShowSubtotals.Checked = true;
            checkShowTotals.Checked = true;
        }
        else
        {
            //checkShowStandard.Checked=false;
            if (!checkShowInsNotAutomatic)
            {
                checkShowIns.Checked = false;
            }
             
            checkShowDiscount.Checked = false;
            checkShowSubtotals.Checked = false;
            checkShowTotals.Checked = false;
        } 
        fillMain();
    }

    private void checkShowStandard_Click(Object sender, EventArgs e) throws Exception {
        fillMain();
    }

    private void checkShowIns_Click(Object sender, EventArgs e) throws Exception {
        if (!checkShowIns.Checked)
        {
            if (MsgBox.show(this,MsgBoxButtons.YesNo,"Turn off automatic checking of this box for the rest of this session?"))
            {
                checkShowInsNotAutomatic = true;
            }
             
        }
         
        fillMain();
    }

    private void checkShowDiscount_Click(Object sender, EventArgs e) throws Exception {
        fillMain();
    }

    private void checkShowSubtotals_Click(Object sender, EventArgs e) throws Exception {
        fillMain();
    }

    private void checkShowTotals_Click(Object sender, EventArgs e) throws Exception {
        fillMain();
    }

    private void toolBarMainPrint_Click() throws Exception {
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            if (checkShowDiscount.Checked || checkShowIns.Checked)
            {
                if (MessageBox.Show(this, String.Format(Lan.g(this,"Do you want to remove insurance estimates and PPO discounts from printed treatment plan?")), "Open Dental", MessageBoxButtons.YesNo, MessageBoxIcon.Question) != DialogResult.No)
                {
                    checkShowDiscount.Checked = false;
                    checkShowIns.Checked = false;
                    fillMain();
                }
                 
            }
             
        }
         
        prepImageForPrinting();
        MigraDoc.DocumentObjectModel.Document doc = createDocument();
        MigraDoc.Rendering.Printing.MigraDocPrintDocument printdoc = new MigraDoc.Rendering.Printing.MigraDocPrintDocument();
        MigraDoc.Rendering.DocumentRenderer renderer = new MigraDoc.Rendering.DocumentRenderer(doc);
        renderer.PrepareDocument();
        printdoc.Renderer = renderer;
        //we might want to surround some of this with a try-catch
        if (PrinterL.SetPrinter(pd2, PrintSituation.TPPerio, PatCur.PatNum, "Treatment plan for printed"))
        {
            printdoc.PrinterSettings = pd2.PrinterSettings;
            printdoc.Print();
        }
         
    }

    private void toolBarMainEmail_Click() throws Exception {
        if (PrefC.getBool(PrefName.FuchsOptionsOn))
        {
            if (checkShowDiscount.Checked || checkShowIns.Checked)
            {
                if (MessageBox.Show(this, String.Format(Lan.g(this,"Do you want to remove insurance estimates and PPO discounts from e-mailed treatment plan?")), "Open Dental", MessageBoxButtons.YesNo, MessageBoxIcon.Question) != DialogResult.No)
                {
                    checkShowDiscount.Checked = false;
                    checkShowIns.Checked = false;
                    fillMain();
                }
                 
            }
             
        }
         
        prepImageForPrinting();
        String attachPath = EmailMessages.getEmailAttachPath();
        Random rnd = new Random();
        String fileName = DateTime.Now.ToString("yyyyMMdd") + "_" + DateTime.Now.TimeOfDay.Ticks.ToString() + rnd.Next(1000).ToString() + ".pdf";
        String filePathAndName = CodeBase.ODFileUtils.combinePaths(attachPath,fileName);
        MigraDoc.Rendering.PdfDocumentRenderer pdfRenderer = new MigraDoc.Rendering.PdfDocumentRenderer(true, PdfFontEmbedding.Always);
        pdfRenderer.Document = createDocument();
        pdfRenderer.RenderDocument();
        pdfRenderer.PdfDocument.Save(filePathAndName);
        //Process.Start(filePathAndName);
        EmailMessage message = new EmailMessage();
        message.PatNum = PatCur.PatNum;
        message.ToAddress = PatCur.Email;
        message.FromAddress = EmailAddresses.getByClinic(PatCur.ClinicNum).SenderAddress;
        message.Subject = Lan.g(this,"Treatment Plan");
        EmailAttach attach = new EmailAttach();
        attach.DisplayedFileName = "TreatmentPlan.pdf";
        attach.ActualFileName = fileName;
        message.Attachments.Add(attach);
        FormEmailMessageEdit FormE = new FormEmailMessageEdit(message);
        FormE.IsNew = true;
        FormE.ShowDialog();
    }

    //if(FormE.DialogResult==DialogResult.OK) {
    //	RefreshCurrentModule();
    //}
    private void prepImageForPrinting() throws Exception {
        //linesPrinted=0;
        ColTotal = new double[10];
        //headingPrinted=false;
        //graphicsPrinted=false;
        //mainPrinted=false;
        //benefitsPrinted=false;
        //notePrinted=false;
        //pagesPrinted=0;
        if (PrefC.getBool(PrefName.TreatPlanShowGraphics))
        {
            //prints the graphical tooth chart and legend
            //Panel panelHide=new Panel();
            //panelHide.Size=new Size(600,500);
            //panelHide.BackColor=this.BackColor;
            //panelHide.SendToBack();
            //this.Controls.Add(panelHide);
            toothChart = new SparksToothChart.ToothChartWrapper();
            toothChart.setColorBackground(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][14].ItemColor);
            toothChart.setColorText(DefC.getLong()[((Enum)DefCat.ChartGraphicColors).ordinal()][15].ItemColor);
            //toothChart.TaoRenderEnabled=true;
            //toothChart.TaoInitializeContexts();
            toothChart.Size = new Size(500, 370);
            //toothChart.Location=new Point(-600,-500);//off the visible screen
            //toothChart.SendToBack();
            //ComputerPref computerPref=ComputerPrefs.GetForLocalComputer();
            toothChart.setUseHardware(ComputerPrefs.getLocalComputer().GraphicsUseHardware);
            toothChart.setToothNumberingNomenclature(ToothNumberingNomenclature.values()[PrefC.getInt(PrefName.UseInternationalToothNumbers)]);
            toothChart.setPreferredPixelFormatNumber(ComputerPrefs.getLocalComputer().PreferredPixelFormatNum);
            toothChart.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat(ComputerPrefs.getLocalComputer().DirectXFormat));
            //Must be last setting set for preferences, because
            //this is the line where the device pixel format is
            //recreated.
            //The preferred pixel format number changes to the selected pixel format number after a context is chosen.
            toothChart.setDrawMode(ComputerPrefs.getLocalComputer().GraphicsSimple);
            ComputerPrefs.getLocalComputer().PreferredPixelFormatNum = toothChart.getPreferredPixelFormatNumber();
            ComputerPrefs.update(ComputerPrefs.getLocalComputer());
            this.Controls.Add(toothChart);
            toothChart.BringToFront();
            toothChart.resetTeeth();
            ToothInitialList = ToothInitials.refresh(PatCur.PatNum);
            for (int i = 0;i < ToothInitialList.Count;i++)
            {
                //first, primary.  That way, you can still set a primary tooth missing afterwards.
                if (ToothInitialList[i].InitialType == ToothInitialType.Primary)
                {
                    toothChart.SetPrimary(ToothInitialList[i].ToothNum);
                }
                 
            }
            for (int i = 0;i < ToothInitialList.Count;i++)
            {
                InitialType __dummyScrutVar2 = ToothInitialList[i].InitialType;
                if (__dummyScrutVar2.equals(ToothInitialType.Missing))
                {
                    toothChart.SetMissing(ToothInitialList[i].ToothNum);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.Hidden))
                {
                    toothChart.SetHidden(ToothInitialList[i].ToothNum);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.Rotate))
                {
                    toothChart.MoveTooth(ToothInitialList[i].ToothNum, ToothInitialList[i].Movement, 0, 0, 0, 0, 0);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.TipM))
                {
                    toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, ToothInitialList[i].Movement, 0, 0, 0, 0);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.TipB))
                {
                    toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, ToothInitialList[i].Movement, 0, 0, 0);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.ShiftM))
                {
                    toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, ToothInitialList[i].Movement, 0, 0);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.ShiftO))
                {
                    toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, 0, ToothInitialList[i].Movement, 0);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.ShiftB))
                {
                    toothChart.MoveTooth(ToothInitialList[i].ToothNum, 0, 0, 0, 0, 0, ToothInitialList[i].Movement);
                }
                else if (__dummyScrutVar2.equals(ToothInitialType.Drawing))
                {
                    toothChart.AddDrawingSegment(ToothInitialList[i].Copy());
                }
                         
            }
            computeProcListFiltered();
            drawProcsGraphics();
            toothChart.setAutoFinish(true);
            chartBitmap = toothChart.getBitmap();
            toothChart.Dispose();
        }
         
    }

    private MigraDoc.DocumentObjectModel.Document createDocument() throws Exception {
        MigraDoc.DocumentObjectModel.Document doc = new MigraDoc.DocumentObjectModel.Document();
        doc.DefaultPageSetup.PageWidth = Unit.FromInch(8.5);
        doc.DefaultPageSetup.PageHeight = Unit.FromInch(11);
        doc.DefaultPageSetup.TopMargin = Unit.FromInch(.5);
        doc.DefaultPageSetup.LeftMargin = Unit.FromInch(.5);
        doc.DefaultPageSetup.RightMargin = Unit.FromInch(.5);
        MigraDoc.DocumentObjectModel.Section section = doc.AddSection();
        String text = new String();
        MigraDoc.DocumentObjectModel.Font headingFont = MigraDocHelper.CreateFont(13, true);
        MigraDoc.DocumentObjectModel.Font bodyFontx = MigraDocHelper.CreateFont(9, false);
        MigraDoc.DocumentObjectModel.Font nameFontx = MigraDocHelper.CreateFont(9, true);
        MigraDoc.DocumentObjectModel.Font totalFontx = MigraDocHelper.CreateFont(9, true);
        //Heading---------------------------------------------------------------------------------------------------------------
        Paragraph par = section.AddParagraph();
        ParagraphFormat parformat = new ParagraphFormat();
        parformat.Alignment = ParagraphAlignment.Center;
        parformat.Font = MigraDocHelper.CreateFont(10, true);
        par.Format = parformat;
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            //current TP
            text = Lan.g(this,"Proposed Treatment Plan");
        }
        else
        {
            text = PlanList[gridPlans.getSelectedIndices()[0] - 1].Heading;
        } 
        par.AddFormattedText(text, headingFont);
        par.AddLineBreak();
        if (PatCur.ClinicNum == 0)
        {
            text = PrefC.getString(PrefName.PracticeTitle);
            par.AddText(text);
            par.AddLineBreak();
            text = PrefC.getString(PrefName.PracticePhone);
        }
        else
        {
            Clinic clinic = Clinics.getClinic(PatCur.ClinicNum);
            text = clinic.Description;
            par.AddText(text);
            par.AddLineBreak();
            text = clinic.Phone;
        } 
        if (text.Length == 10 && StringSupport.equals(Application.CurrentCulture.Name, "en-US"))
        {
            text = "(" + text.Substring(0, 3) + ")" + text.Substring(3, 3) + "-" + text.Substring(6);
        }
         
        par.AddText(text);
        par.AddLineBreak();
        text = PatCur.getNameFL() + ", DOB " + PatCur.Birthdate.ToShortDateString();
        par.AddText(text);
        par.AddLineBreak();
        if (gridPlans.getSelectedIndices()[0] > 0)
        {
            //not the default plan
            if (PlanList[gridPlans.getSelectedIndices()[0] - 1].ResponsParty != 0)
            {
                text = Lan.g(this,"Responsible Party: ") + Patients.GetLim(PlanList[gridPlans.getSelectedIndices()[0] - 1].ResponsParty).GetNameFL();
                par.AddText(text);
                par.AddLineBreak();
            }
             
        }
         
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            //default TP
            text = DateTime.Today.ToShortDateString();
        }
        else
        {
            text = PlanList[gridPlans.getSelectedIndices()[0] - 1].DateTP.ToShortDateString();
        } 
        par.AddText(text);
        //Graphics---------------------------------------------------------------------------------------------------------------
        TextFrame frame = new TextFrame();
        int widthDoc = MigraDocHelper.getDocWidth();
        if (PrefC.getBool(PrefName.TreatPlanShowGraphics))
        {
            frame = MigraDocHelper.CreateContainer(section);
            MigraDocHelper.drawString(frame,Lan.g(this,"Your") + "\r\n" + Lan.g(this,"Right"),bodyFontx,new RectangleF(widthDoc / 2 - toothChart.Width / 2 - 50, toothChart.Height / 2 - 10, 50, 100));
            MigraDocHelper.DrawBitmap(frame, chartBitmap, widthDoc / 2 - toothChart.Width / 2, 0);
            MigraDocHelper.drawString(frame,Lan.g(this,"Your") + "\r\n" + Lan.g(this,"Left"),bodyFontx,new RectangleF(widthDoc / 2 + toothChart.Width / 2 + 17, toothChart.Height / 2 - 10, 50, 100));
            if (checkShowCompleted.Checked)
            {
                float yPos = toothChart.Height + 15;
                float xPos = 225;
                MigraDocHelper.FillRectangle(frame, DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][3].ItemColor, xPos, yPos, 14, 14);
                xPos += 16;
                MigraDocHelper.drawString(frame,Lan.g(this,"Existing"),bodyFontx,xPos,yPos);
                Graphics g = this.CreateGraphics();
                //for measuring strings.
                xPos += (int)g.MeasureString(Lan.g(this,"Existing"), bodyFont).Width + 23;
                //The Complete work is actually a combination of EC and C. Usually same color.
                //But just in case they are different, this will show it.
                MigraDocHelper.FillRectangle(frame, DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][2].ItemColor, xPos, yPos, 7, 14);
                xPos += 7;
                MigraDocHelper.FillRectangle(frame, DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][1].ItemColor, xPos, yPos, 7, 14);
                xPos += 9;
                MigraDocHelper.drawString(frame,Lan.g(this,"Complete"),bodyFontx,xPos,yPos);
                xPos += (int)g.MeasureString(Lan.g(this,"Complete"), bodyFont).Width + 23;
                MigraDocHelper.FillRectangle(frame, DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][4].ItemColor, xPos, yPos, 14, 14);
                xPos += 16;
                MigraDocHelper.drawString(frame,Lan.g(this,"Referred Out"),bodyFontx,xPos,yPos);
                xPos += (int)g.MeasureString(Lan.g(this,"Referred Out"), bodyFont).Width + 23;
                MigraDocHelper.FillRectangle(frame, DefC.getShort()[((Enum)DefCat.ChartGraphicColors).ordinal()][0].ItemColor, xPos, yPos, 14, 14);
                xPos += 16;
                MigraDocHelper.drawString(frame,Lan.g(this,"Treatment Planned"),bodyFontx,xPos,yPos);
                g.Dispose();
            }
             
        }
         
        MigraDocHelper.InsertSpacer(section, 10);
        MigraDocHelper.DrawGrid(section, gridMain);
        //Print benefits----------------------------------------------------------------------------------------------------
        if (checkShowIns.Checked)
        {
            OpenDental.UI.ODGrid gridFamIns = new OpenDental.UI.ODGrid();
            this.Controls.Add(gridFamIns);
            gridFamIns.beginUpdate();
            gridFamIns.getColumns().Clear();
            OpenDental.UI.ODGridColumn col = new OpenDental.UI.ODGridColumn("",140);
            gridFamIns.getColumns().add(col);
            col = new OpenDental.UI.ODGridColumn(Lan.g(this,"Primary"), 70, HorizontalAlignment.Right);
            gridFamIns.getColumns().add(col);
            col = new OpenDental.UI.ODGridColumn(Lan.g(this,"Secondary"), 70, HorizontalAlignment.Right);
            gridFamIns.getColumns().add(col);
            gridFamIns.getRows().Clear();
            OpenDental.UI.ODGridRow row;
            //Annual Family Max--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Family Maximum"));
            row.getCells().Add(textFamPriMax.Text);
            row.getCells().Add(textFamSecMax.Text);
            gridFamIns.getRows().add(row);
            //Family Deductible--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Family Deductible"));
            row.getCells().Add(textFamPriDed.Text);
            row.getCells().Add(textFamSecDed.Text);
            gridFamIns.getRows().add(row);
            //Print Family Insurance-----------------------
            MigraDocHelper.InsertSpacer(section, 15);
            par = section.AddParagraph();
            par.Format.Alignment = ParagraphAlignment.Center;
            par.AddFormattedText(Lan.g(this,"Family Dental Insurance Benefits"), totalFontx);
            MigraDocHelper.InsertSpacer(section, 2);
            MigraDocHelper.DrawGrid(section, gridFamIns);
            gridFamIns.Dispose();
            //Individual Insurance---------------------
            OpenDental.UI.ODGrid gridIns = new OpenDental.UI.ODGrid();
            this.Controls.Add(gridIns);
            gridIns.beginUpdate();
            gridIns.getColumns().Clear();
            col = new OpenDental.UI.ODGridColumn("",140);
            gridIns.getColumns().add(col);
            col = new OpenDental.UI.ODGridColumn(Lan.g(this,"Primary"), 70, HorizontalAlignment.Right);
            gridIns.getColumns().add(col);
            col = new OpenDental.UI.ODGridColumn(Lan.g(this,"Secondary"), 70, HorizontalAlignment.Right);
            gridIns.getColumns().add(col);
            gridIns.getRows().Clear();
            //Annual Max--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Annual Maximum"));
            row.getCells().Add(textPriMax.Text);
            row.getCells().Add(textSecMax.Text);
            gridIns.getRows().add(row);
            //Deductible--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Deductible"));
            row.getCells().Add(textPriDed.Text);
            row.getCells().Add(textSecDed.Text);
            gridIns.getRows().add(row);
            //Deductible Remaining--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Deductible Remaining"));
            row.getCells().Add(textPriDedRem.Text);
            row.getCells().Add(textSecDedRem.Text);
            gridIns.getRows().add(row);
            //Insurance Used--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Insurance Used"));
            row.getCells().Add(textPriUsed.Text);
            row.getCells().Add(textSecUsed.Text);
            gridIns.getRows().add(row);
            //Pending--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Pending"));
            row.getCells().Add(textPriPend.Text);
            row.getCells().Add(textSecPend.Text);
            gridIns.getRows().add(row);
            //Remaining--------------------------
            row = new OpenDental.UI.ODGridRow();
            row.getCells().add(Lan.g(this,"Remaining"));
            row.getCells().Add(textPriRem.Text);
            row.getCells().Add(textSecRem.Text);
            gridIns.getRows().add(row);
            gridIns.endUpdate();
            //Print Individual Insurance-------------------------
            MigraDocHelper.InsertSpacer(section, 15);
            par = section.AddParagraph();
            par.Format.Alignment = ParagraphAlignment.Center;
            par.AddFormattedText(Lan.g(this,"Individual Dental Insurance Benefits"), totalFontx);
            MigraDocHelper.InsertSpacer(section, 2);
            MigraDocHelper.DrawGrid(section, gridIns);
            gridIns.Dispose();
        }
         
        //Note------------------------------------------------------------------------------------------------------------
        String note = "";
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            //current TP
            note = PrefC.getString(PrefName.TreatmentPlanNote);
        }
        else
        {
            note = PlanList[gridPlans.getSelectedIndices()[0] - 1].Note;
        } 
        char nbsp = '\u00A0';
        //to prevent collapsing of multiple spaces to single spaces.  We only do double spaces to leave single spaces in place.
        note = note.Replace("  ", nbsp.ToString() + nbsp.ToString());
        MigraDocHelper.InsertSpacer(section, 20);
        par = section.AddParagraph(note);
        par.Format.Font = bodyFontx;
        par.Format.Borders.Color = Colors.Gray;
        par.Format.Borders.DistanceFromLeft = Unit.FromInch(.05);
        par.Format.Borders.DistanceFromRight = Unit.FromInch(.05);
        par.Format.Borders.DistanceFromTop = Unit.FromInch(.05);
        par.Format.Borders.DistanceFromBottom = Unit.FromInch(.05);
        //Signature-----------------------------------------------------------------------------------------------------------
        //can't be default TP
        if (gridPlans.getSelectedIndices()[0] != 0 && !StringSupport.equals(PlanList[gridPlans.getSelectedIndices()[0] - 1].Signature, ""))
        {
            System.Drawing.Bitmap sigBitmap = null;
            List<ProcTP> proctpList = ProcTPs.RefreshForTP(PlanList[gridPlans.getSelectedIndices()[0] - 1].TreatPlanNum);
            if (PlanList[gridPlans.getSelectedIndices()[0] - 1].SigIsTopaz)
            {
                Control sigBoxTopaz = CodeBase.TopazWrapper.getTopaz();
                sigBoxTopaz.Size = new System.Drawing.Size(362, 79);
                Controls.Add(sigBoxTopaz);
                CodeBase.TopazWrapper.clearTopaz(sigBoxTopaz);
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,0);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,0);
                String keystring = TreatPlans.GetHashString(PlanList[gridPlans.getSelectedIndices()[0] - 1], proctpList);
                CodeBase.TopazWrapper.setTopazKeyString(sigBoxTopaz,keystring);
                CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,2);
                //high encryption
                CodeBase.TopazWrapper.setTopazCompressionMode(sigBoxTopaz,2);
                //high compression
                CodeBase.TopazWrapper.SetTopazSigString(sigBoxTopaz, PlanList[gridPlans.getSelectedIndices()[0] - 1].Signature);
                sigBoxTopaz.Refresh();
                //If sig is not showing, then try encryption mode 3 for signatures signed with old SigPlusNet.dll.
                if (CodeBase.TopazWrapper.getTopazNumberOfTabletPoints(sigBoxTopaz) == 0)
                {
                    CodeBase.TopazWrapper.setTopazEncryptionMode(sigBoxTopaz,3);
                    //Unknown mode (told to use via TopazSystems)
                    CodeBase.TopazWrapper.SetTopazSigString(sigBoxTopaz, PlanList[gridPlans.getSelectedIndices()[0] - 1].Signature);
                }
                 
                sigBitmap = new Bitmap(362, 79);
                sigBoxTopaz.DrawToBitmap(sigBitmap, new Rectangle(0, 0, 362, 79));
                //GetBitmap would probably work.
                Controls.Remove(sigBoxTopaz);
                sigBoxTopaz.Dispose();
            }
            else
            {
                OpenDental.UI.SignatureBox sigBox = new OpenDental.UI.SignatureBox();
                sigBox.Size = new System.Drawing.Size(362, 79);
                sigBox.clearTablet();
                //sigBox.SetSigCompressionMode(0);
                //sigBox.SetEncryptionMode(0);
                sigBox.SetKeyString(TreatPlans.GetHashString(PlanList[gridPlans.getSelectedIndices()[0] - 1], proctpList));
                //"0000000000000000");
                //sigBox.SetAutoKeyData(ProcCur.Note+ProcCur.UserNum.ToString());
                //sigBox.SetEncryptionMode(2);//high encryption
                //sigBox.SetSigCompressionMode(2);//high compression
                sigBox.SetSigString(PlanList[gridPlans.getSelectedIndices()[0] - 1].Signature);
                //if(sigBox.NumberOfTabletPoints()==0) {
                //	labelInvalidSig.Visible=true;
                //}
                //sigBox.SetTabletState(0);//not accepting input.  To accept input, change the note, or clear the sig.
                sigBitmap = (Bitmap)sigBox.getSigImage(true);
            } 
            if (sigBitmap != null)
            {
                frame = MigraDocHelper.CreateContainer(section);
                MigraDocHelper.DrawBitmap(frame, sigBitmap, widthDoc / 2 - sigBitmap.Width / 2, 20);
            }
             
        }
         
        return doc;
    }

    /**
    * Just used for printing the 3D chart.
    */
    private void computeProcListFiltered() throws Exception {
        ProcListFiltered = new List<Procedure>();
        for (int i = 0;i < ProcList.Count;i++)
        {
            //first, add all completed work and conditions. C,EC,EO, and Referred
            if (ProcList[i].ProcStatus == ProcStat.C || ProcList[i].ProcStatus == ProcStat.EC || ProcList[i].ProcStatus == ProcStat.EO)
            {
                if (checkShowCompleted.Checked)
                {
                    ProcListFiltered.Add(ProcList[i]);
                }
                 
            }
             
            if (ProcList[i].ProcStatus == ProcStat.R)
            {
                //always show all referred
                ProcListFiltered.Add(ProcList[i]);
            }
             
            if (ProcList[i].ProcStatus == ProcStat.Cn)
            {
                //always show all conditions.
                ProcListFiltered.Add(ProcList[i]);
            }
             
        }
        //then add whatever is showing on the selected TP
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            for (int i = 0;i < ProcListTP.Length;i++)
            {
                //current plan
                if (ProcListTP[i].HideGraphics)
                {
                    continue;
                }
                 
                ProcListFiltered.Add(ProcListTP[i]);
            }
        }
        else
        {
            Procedure procDummy;
            for (int i = 0;i < ProcTPSelectList.Length;i++)
            {
                //not a real procedure.  Just used to help display on graphical chart
                procDummy = new Procedure();
                for (int j = 0;j < ProcList.Count;j++)
                {
                    //this next loop is a way to get missing fields like tooth range.  Could be improved.
                    if (ProcList[j].ProcNum == ProcTPSelectList[i].ProcNumOrig)
                    {
                        //but remember that even if the procedure is found, Status might have been altered
                        procDummy = ProcList[j].Copy();
                    }
                     
                }
                if (Tooth.IsValidEntry(ProcTPSelectList[i].ToothNumTP))
                {
                    procDummy.ToothNum = Tooth.FromInternat(ProcTPSelectList[i].ToothNumTP);
                }
                 
                if (ProcedureCodes.GetProcCode(ProcTPSelectList[i].ProcCode).TreatArea == TreatmentArea.Surf)
                {
                    procDummy.Surf = Tooth.SurfTidyFromDisplayToDb(ProcTPSelectList[i].Surf, procDummy.ToothNum);
                }
                else
                {
                    procDummy.Surf = ProcTPSelectList[i].Surf;
                } 
                //for quad, arch, etc.
                if (procDummy.ToothRange == null)
                {
                    procDummy.ToothRange = "";
                }
                 
                //procDummy.HideGraphical??
                procDummy.ProcStatus = ProcStat.TP;
                procDummy.CodeNum = ProcedureCodes.GetProcCode(ProcTPSelectList[i].ProcCode).CodeNum;
                ProcListFiltered.Add(procDummy);
            }
        } 
        ProcListFiltered.Sort(CompareProcListFiltered);
    }

    private int compareProcListFiltered(Procedure proc1, Procedure proc2) throws Exception {
        int dateFilter = proc1.ProcDate.CompareTo(proc2.ProcDate);
        if (dateFilter != 0)
        {
            return dateFilter;
        }
         
        //Dates are the same, filter by ProcStatus.
        int xIdx = getProcStatusIdx(proc1.ProcStatus);
        int yIdx = getProcStatusIdx(proc2.ProcStatus);
        return xIdx.CompareTo(yIdx);
    }

    /**
    * Returns index for sorting based on this order: Cn,TP,R,EO,EC,C,D
    */
    private int getProcStatusIdx(ProcStat procStat) throws Exception {
        switch(procStat)
        {
            case Cn: 
                return 0;
            case TP: 
                return 1;
            case R: 
                return 2;
            case EO: 
                return 3;
            case EC: 
                return 4;
            case C: 
                return 5;
            case D: 
                return 6;
        
        }
        return 0;
    }

    private void drawProcsGraphics() throws Exception {
        Procedure proc;
        String[] teeth = new String[]();
        System.Drawing.Color cLight = System.Drawing.Color.White;
        System.Drawing.Color cDark = System.Drawing.Color.White;
        for (int i = 0;i < ProcListFiltered.Count;i++)
        {
            proc = ProcListFiltered[i];
            //if(proc.ProcStatus!=procStat) {
            //  continue;
            //}
            if (proc.HideGraphics)
            {
                continue;
            }
             
            if (ProcedureCodes.getProcCode(proc.CodeNum).PaintType == ToothPaintingType.Extraction && (proc.ProcStatus == ProcStat.C || proc.ProcStatus == ProcStat.EC || proc.ProcStatus == ProcStat.EO))
            {
                continue;
            }
             
            //prevents the red X. Missing teeth already handled.
            if (ProcedureCodes.getProcCode(proc.CodeNum).GraphicColor == System.Drawing.Color.FromArgb(0))
            {
                switch(proc.ProcStatus)
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
                cDark = ProcedureCodes.getProcCode(proc.CodeNum).GraphicColor;
                cLight = ProcedureCodes.getProcCode(proc.CodeNum).GraphicColor;
            } 
            switch(ProcedureCodes.getProcCode(proc.CodeNum).PaintType)
            {
                case BridgeDark: 
                    if (ToothInitials.toothIsMissingOrHidden(ToothInitialList,proc.ToothNum))
                    {
                        toothChart.SetPontic(proc.ToothNum, cDark);
                    }
                    else
                    {
                        toothChart.SetCrown(proc.ToothNum, cDark);
                    } 
                    break;
                case BridgeLight: 
                    if (ToothInitials.toothIsMissingOrHidden(ToothInitialList,proc.ToothNum))
                    {
                        toothChart.SetPontic(proc.ToothNum, cLight);
                    }
                    else
                    {
                        toothChart.SetCrown(proc.ToothNum, cLight);
                    } 
                    break;
                case CrownDark: 
                    toothChart.SetCrown(proc.ToothNum, cDark);
                    break;
                case CrownLight: 
                    toothChart.SetCrown(proc.ToothNum, cLight);
                    break;
                case DentureDark: 
                    if (StringSupport.equals(proc.Surf, "U"))
                    {
                        teeth = new String[14];
                        for (int t = 0;t < 14;t++)
                        {
                            teeth[t] = (t + 2).ToString();
                        }
                    }
                    else if (StringSupport.equals(proc.Surf, "L"))
                    {
                        teeth = new String[14];
                        for (int t = 0;t < 14;t++)
                        {
                            teeth[t] = (t + 18).ToString();
                        }
                    }
                    else
                    {
                        teeth = proc.ToothRange.Split(new char[]{ ',' });
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
                    break;
                case DentureLight: 
                    if (StringSupport.equals(proc.Surf, "U"))
                    {
                        teeth = new String[14];
                        for (int t = 0;t < 14;t++)
                        {
                            teeth[t] = (t + 2).ToString();
                        }
                    }
                    else if (StringSupport.equals(proc.Surf, "L"))
                    {
                        teeth = new String[14];
                        for (int t = 0;t < 14;t++)
                        {
                            teeth[t] = (t + 18).ToString();
                        }
                    }
                    else
                    {
                        teeth = proc.ToothRange.Split(new char[]{ ',' });
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
                    break;
                case Extraction: 
                    toothChart.SetBigX(proc.ToothNum, cDark);
                    break;
                case FillingDark: 
                    toothChart.SetSurfaceColors(proc.ToothNum, proc.Surf, cDark);
                    break;
                case FillingLight: 
                    toothChart.SetSurfaceColors(proc.ToothNum, proc.Surf, cLight);
                    break;
                case Implant: 
                    toothChart.SetImplant(proc.ToothNum, cDark);
                    break;
                case PostBU: 
                    toothChart.SetBU(proc.ToothNum, cDark);
                    break;
                case RCT: 
                    toothChart.SetRCT(proc.ToothNum, cDark);
                    break;
                case Sealant: 
                    toothChart.SetSealant(proc.ToothNum, cDark);
                    break;
                case Veneer: 
                    toothChart.SetVeneer(proc.ToothNum, cLight);
                    break;
                case Watch: 
                    toothChart.SetWatch(proc.ToothNum, cDark);
                    break;
            
            }
        }
    }

    private void toolBarMainUpdate_Click() throws Exception {
        if (gridPlans.getSelectedIndices()[0] != 0)
        {
            MsgBox.show(this,"The update fee utility only works on the current treatment plan, not any saved plans.");
            return ;
        }
         
        if (!MsgBox.show(this,true,"Update all fees and insurance estimates on this treatment plan to the current fees for this patient?"))
        {
            return ;
        }
         
        Procedure procCur;
        //Procedure procOld
        //Find the primary plan------------------------------------------------------------------
        long priSubNum = PatPlans.getInsSubNum(PatPlanList,PatPlans.getOrdinal(PriSecMed.Primary,PatPlanList,InsPlanList,SubList));
        InsSub prisub = InsSubs.getSub(priSubNum,SubList);
        long priPlanNum = prisub.PlanNum;
        InsPlan priplan = InsPlans.getPlan(priPlanNum,InsPlanList);
        //can handle a plannum=0
        double standardfee = new double();
        double insfee = new double();
        List<ClaimProc> claimProcList = ClaimProcs.refreshForTP(PatCur.PatNum);
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            procCur = ProcListTP[i];
            //procOld=procCur.Clone();
            //first the fees
            //Check if it's a medical procedure.
            boolean isMed = false;
            if (procCur.MedicalCode != null && !StringSupport.equals(procCur.MedicalCode, ""))
            {
                isMed = true;
            }
             
            //Get fee schedule for medical or dental.
            long feeSch = new long();
            if (isMed)
            {
                feeSch = Fees.getMedFeeSched(PatCur,InsPlanList,PatPlanList,SubList);
            }
            else
            {
                feeSch = Fees.getFeeSched(PatCur,InsPlanList,PatPlanList,SubList);
            } 
            //Get the fee amount for medical or dental.
            if (PrefC.getBool(PrefName.MedicalFeeUsedForNewProcs) && isMed)
            {
                insfee = Fees.getAmount0(ProcedureCodes.getProcCode(procCur.MedicalCode).CodeNum,feeSch);
            }
            else
            {
                insfee = Fees.getAmount0(procCur.CodeNum,feeSch);
            } 
            if (PrefC.getBool(PrefName.MedicalFeeUsedForNewProcs) && isMed)
            {
                procCur.ProcFee = insfee;
            }
            else if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
            {
                //PPO
                standardfee = Fees.getAmount0(procCur.CodeNum,Providers.getProv(Patients.getProvNum(PatCur)).FeeSched);
                if (standardfee > insfee)
                {
                    procCur.ProcFee = standardfee;
                }
                else
                {
                    procCur.ProcFee = insfee;
                } 
            }
            else
            {
                procCur.ProcFee = insfee;
            }  
            Procedures.ComputeEstimates(procCur, PatCur.PatNum, claimProcList, false, InsPlanList, PatPlanList, BenefitList, PatCur.getAge(), SubList);
            Procedures.updateFee(procCur.ProcNum,procCur.ProcFee);
        }
        //Procedures.Update(procCur,procOld);//no recall synch required
        moduleSelected(PatCur.PatNum);
    }

    private void toolBarMainCreate_Click() throws Exception {
        //Save TP
        if (gridPlans.getSelectedIndices()[0] != 0)
        {
            MsgBox.show(this,"The default TP must be selected before saving a TP.  You can highlight some procedures in the default TP to save a TP with only those procedures in it.");
            return ;
        }
         
        //Check for duplicate procedures on the appointment before sending the DFT to eCW.
        if (Programs.usingEcwTightOrFullMode() && ECW.AptNum != 0)
        {
            List<Procedure> procs = Procedures.getProcsForSingle(ECW.AptNum,false);
            String duplicateProcs = ProcedureL.ProcsContainDuplicates(procs);
            if (!StringSupport.equals(duplicateProcs, ""))
            {
                MessageBox.Show(duplicateProcs);
                return ;
            }
             
        }
         
        if (gridMain.getSelectedIndices().Length == 0)
        {
            gridMain.setSelected(true);
        }
         
        TreatPlan tp = new TreatPlan();
        tp.Heading = Lan.g(this,"Proposed Treatment Plan");
        tp.DateTP = DateTimeOD.getToday();
        tp.PatNum = PatCur.PatNum;
        tp.Note = PrefC.getString(PrefName.TreatmentPlanNote);
        tp.ResponsParty = PatCur.ResponsParty;
        TreatPlans.insert(tp);
        ProcTP procTP;
        Procedure proc;
        int itemNo = 0;
        List<Procedure> procList = new List<Procedure>();
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag == null)
            {
                continue;
            }
             
            //user must have highlighted a subtotal row.
            proc = (Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag;
            procList.Add(proc);
            procTP = new ProcTP();
            procTP.TreatPlanNum = tp.TreatPlanNum;
            procTP.PatNum = PatCur.PatNum;
            procTP.ProcNumOrig = proc.ProcNum;
            procTP.ItemOrder = itemNo;
            procTP.Priority = proc.Priority;
            procTP.ToothNumTP = Tooth.toInternat(proc.ToothNum);
            if (ProcedureCodes.getProcCode(proc.CodeNum).TreatArea == TreatmentArea.Surf)
            {
                procTP.Surf = Tooth.surfTidyFromDbToDisplay(proc.Surf,proc.ToothNum);
            }
            else
            {
                procTP.Surf = proc.Surf;
            } 
            //for UR, L, etc.
            procTP.ProcCode = ProcedureCodes.getStringProcCode(proc.CodeNum);
            procTP.Descript = RowsMain[gridMain.getSelectedIndices()[i]].Description;
            if (checkShowFees.Checked)
            {
                procTP.FeeAmt = PIn.Double(RowsMain[gridMain.getSelectedIndices()[i]].Fee.ToString());
            }
             
            if (checkShowIns.Checked)
            {
                procTP.PriInsAmt = PIn.Double(RowsMain[gridMain.getSelectedIndices()[i]].PriIns.ToString());
                procTP.SecInsAmt = PIn.Double(RowsMain[gridMain.getSelectedIndices()[i]].SecIns.ToString());
            }
             
            if (checkShowDiscount.Checked)
            {
                procTP.Discount = PIn.Double(RowsMain[gridMain.getSelectedIndices()[i]].Discount.ToString());
            }
             
            if (checkShowIns.Checked)
            {
                procTP.PatAmt = PIn.Double(RowsMain[gridMain.getSelectedIndices()[i]].Pat.ToString());
            }
             
            procTP.Prognosis = RowsMain[gridMain.getSelectedIndices()[i]].Prognosis;
            procTP.Dx = RowsMain[gridMain.getSelectedIndices()[i]].Dx;
            ProcTPs.insertOrUpdate(procTP,true);
            itemNo++;
        }
        /*
        				proc=(Procedure)gridMain.Rows[gridMain.SelectedIndices[i]].Tag;
        				procTP=new ProcTP();
        				procTP.TreatPlanNum=tp.TreatPlanNum;
        				procTP.PatNum=PatCur.PatNum;
        				procTP.ProcNumOrig=proc.ProcNum;
        				procTP.ItemOrder=itemNo;
        				procTP.Priority=proc.Priority;
        				procTP.ToothNumTP="";
        				procTP.Surf="";
        				procTP.Code=proc.LabProcCode;
        				procTP.Descript=gridMain.Rows[gridMain.SelectedIndices[i]]
        					.Cells[gridMain.Columns.GetIndex(Lan.g("TableTP","Description"))].Text;
        				if(checkShowFees.Checked) {
        					procTP.FeeAmt=PIn.PDouble(gridMain.Rows[gridMain.SelectedIndices[i]]
        						.Cells[gridMain.Columns.GetIndex(Lan.g("TableTP","Fee"))].Text);
        				}
        				if(checkShowIns.Checked) {
        					procTP.PriInsAmt=PIn.PDouble(gridMain.Rows[gridMain.SelectedIndices[i]]
        						.Cells[gridMain.Columns.GetIndex(Lan.g("TableTP","Pri Ins"))].Text);
        					procTP.SecInsAmt=PIn.PDouble(gridMain.Rows[gridMain.SelectedIndices[i]]
        						.Cells[gridMain.Columns.GetIndex(Lan.g("TableTP","Sec Ins"))].Text);
        					procTP.PatAmt=PIn.PDouble(gridMain.Rows[gridMain.SelectedIndices[i]]
        						.Cells[gridMain.Columns.GetIndex(Lan.g("TableTP","Pat"))].Text);
        				}
        				ProcTPs.InsertOrUpdate(procTP,true);
        				itemNo++;*/
        //Send TP DFT HL7 message to ECW with embedded PDF when using tight or full integration only.
        if (Programs.usingEcwTightOrFullMode() && ECW.AptNum != 0)
        {
            prepImageForPrinting();
            MigraDoc.Rendering.PdfDocumentRenderer pdfRenderer = new MigraDoc.Rendering.PdfDocumentRenderer(true, PdfFontEmbedding.Always);
            pdfRenderer.Document = createDocument();
            pdfRenderer.RenderDocument();
            MemoryStream ms = new MemoryStream();
            pdfRenderer.PdfDocument.Save(ms);
            byte[] pdfBytes = ms.GetBuffer();
            //#region Remove when testing is complete.
            //string tempFilePath=Path.GetTempFileName();
            //File.WriteAllBytes(tempFilePath,pdfBytes);
            //#endregion
            String pdfDataStr = Convert.ToBase64String(pdfBytes);
            if (HL7Defs.isExistingHL7Enabled())
            {
                //DFT messages that are PDF's only and do not include FT1 segments, so proc list can be empty
                //MessageConstructor.GenerateDFT(procList,EventTypeHL7.P03,PatCur,Patients.GetPat(PatCur.Guarantor),Bridges.ECW.AptNum,"treatment",pdfDataStr);
                MessageHL7 messageHL7 = MessageConstructor.GenerateDFT(new List<Procedure>(), EventTypeHL7.P03, PatCur, Patients.getPat(PatCur.Guarantor), ECW.AptNum, "treatment", pdfDataStr);
                if (messageHL7 == null)
                {
                    MsgBox.show(this,"There is no DFT message type defined for the enabled HL7 definition.");
                    return ;
                }
                 
                HL7Msg hl7Msg = new HL7Msg();
                hl7Msg.AptNum = 0;
                //Prevents the appt complete button from changing to the "Revise" button prematurely.
                hl7Msg.HL7Status = HL7MessageStatus.OutPending;
                //it will be marked outSent by the HL7 service.
                hl7Msg.MsgText = messageHL7.toString();
                hl7Msg.PatNum = PatCur.PatNum;
                HL7Msgs.insert(hl7Msg);
            }
            else
            {
                ECW.sendHL7(ECW.AptNum,PatCur.PriProv,PatCur,pdfDataStr,"treatment",true);
            } 
        }
         
        moduleSelected(PatCur.PatNum);
        for (int i = 0;i < PlanList.Length;i++)
        {
            if (PlanList[i].TreatPlanNum == tp.TreatPlanNum)
            {
                gridPlans.setSelected(i + 1,true);
                fillMain();
            }
             
        }
    }

    private void toolBarMainSign_Click() throws Exception {
        if (gridPlans.getSelectedIndices()[0] == 0)
        {
            MsgBox.show(this,"You may only sign a saved TP, not the default TP.");
            return ;
        }
         
        prepImageForPrinting();
        MigraDoc.DocumentObjectModel.Document doc = createDocument();
        MigraDoc.Rendering.Printing.MigraDocPrintDocument printdoc = new MigraDoc.Rendering.Printing.MigraDocPrintDocument();
        MigraDoc.Rendering.DocumentRenderer renderer = new MigraDoc.Rendering.DocumentRenderer(doc);
        renderer.PrepareDocument();
        printdoc.Renderer = renderer;
        FormTPsign FormT = new FormTPsign();
        FormT.Document = printdoc;
        FormT.TotalPages = renderer.FormattedDocument.PageCount;
        FormT.TPcur = PlanList[gridPlans.getSelectedIndices()[0] - 1];
        FormT.ShowDialog();
        long tpNum = PlanList[gridPlans.getSelectedIndices()[0] - 1].TreatPlanNum;
        moduleSelected(PatCur.PatNum);
        for (int i = 0;i < PlanList.Length;i++)
        {
            if (PlanList[i].TreatPlanNum == tpNum)
            {
                gridPlans.setSelected(i + 1,true);
            }
             
        }
        fillMain();
    }

    /**
    * Similar method in Account
    */
    private boolean checkClearinghouseDefaults() throws Exception {
        if (PrefC.getInt(PrefName.ClearinghouseDefaultDent) == 0)
        {
            MsgBox.show(this,"No default dental clearinghouse defined.");
            return false;
        }
         
        if (PrefC.getBool(PrefName.ShowFeatureMedicalInsurance) && PrefC.getInt(PrefName.ClearinghouseDefaultMed) == 0)
        {
            MsgBox.show(this,"No default medical clearinghouse defined.");
            return false;
        }
         
        return true;
    }

    private void toolBarMainPreAuth_Click() throws Exception {
        if (!checkClearinghouseDefaults())
        {
            return ;
        }
         
        if (gridPlans.getSelectedIndices()[0] != 0)
        {
            MsgBox.show(this,"You can only send a preauth from the current TP, not a saved TP.");
            return ;
        }
         
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canada
            int numLabProcsUnselected = 0;
            List<int> selectedIndices = new List<int>(gridMain.getSelectedIndices());
            for (int i = 0;i < selectedIndices.Count;i++)
            {
                Procedure proc = ((Procedure)gridMain.getRows()[selectedIndices[i]].Tag);
                if (proc != null)
                {
                    ProcedureCode procCode = ProcedureCodes.getProcCodeFromDb(proc.CodeNum);
                    if (procCode.IsCanadianLab)
                    {
                        gridMain.SetSelected(selectedIndices[i], false);
                        //deselect
                        numLabProcsUnselected++;
                    }
                     
                }
                 
            }
            if (numLabProcsUnselected > 0)
            {
                MessageBox.Show(Lan.g(this,"Number of lab fee procedures unselected") + ": " + numLabProcsUnselected.ToString());
            }
             
            if (gridMain.getSelectedIndices().Length > 7)
            {
                List<int> selectedIndicies = new List<int>(gridMain.getSelectedIndices());
                selectedIndicies.Sort();
                for (int i = 0;i < selectedIndicies.Count;i++)
                {
                    //Unselect all but the first 7 procedures with the smallest index numbers.
                    gridMain.SetSelected(selectedIndicies[i], (i < 7));
                }
                MsgBox.show(this,"Only the first 7 procedures will be selected.  You will need to create another preauth for the remaining procedures.");
            }
             
        }
         
        boolean procsSelected = false;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag != null)
            {
                procsSelected = true;
            }
             
        }
        if (!procsSelected)
        {
            MessageBox.Show(Lan.g(this,"Please select procedures first."));
            return ;
        }
         
        Claim ClaimCur = new Claim();
        FormInsPlanSelect FormIPS = new FormInsPlanSelect(PatCur.PatNum);
        FormIPS.ViewRelat = true;
        FormIPS.ShowDialog();
        if (FormIPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ClaimCur.PatNum = PatCur.PatNum;
        ClaimCur.ClaimStatus = "W";
        ClaimCur.DateSent = DateTimeOD.getToday();
        ClaimCur.PlanNum = FormIPS.SelectedPlan.PlanNum;
        ClaimCur.InsSubNum = FormIPS.SelectedSub.InsSubNum;
        ClaimCur.ProvTreat = 0;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag == null)
            {
                continue;
            }
             
            //skip any hightlighted subtotal lines
            if (ClaimCur.ProvTreat == 0)
            {
                //makes sure that at least one prov is set
                ClaimCur.ProvTreat = ((Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag).ProvNum;
            }
             
            if (!Providers.getIsSec(((Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag).ProvNum))
            {
                ClaimCur.ProvTreat = ((Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag).ProvNum;
            }
             
        }
        ClaimCur.ClinicNum = PatCur.ClinicNum;
        if (Providers.getIsSec(ClaimCur.ProvTreat))
        {
            ClaimCur.ProvTreat = PatCur.PriProv;
        }
         
        //OK if 0, because auto select first in list when open claim
        ClaimCur.ProvBill = Providers.getBillingProvNum(ClaimCur.ProvTreat,ClaimCur.ClinicNum);
        ClaimCur.EmployRelated = YN.No;
        ClaimCur.ClaimType = "PreAuth";
        //this could be a little better if we automate figuring out the patrelat
        //instead of making the user enter it:
        ClaimCur.PatRelat = FormIPS.PatRelat;
        Claims.insert(ClaimCur);
        Procedure ProcCur;
        ClaimProc ClaimProcCur;
        ClaimProc cpExisting;
        for (int i = 0;i < gridMain.getSelectedIndices().Length;i++)
        {
            if (gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag == null)
            {
                continue;
            }
             
            //skip any highlighted subtotal lines
            ProcCur = (Procedure)gridMain.getRows()[gridMain.getSelectedIndices()[i]].Tag;
            ClaimProcCur = new ClaimProc();
            ClaimProcCur.ProcNum = ProcCur.ProcNum;
            ClaimProcCur.ClaimNum = ClaimCur.ClaimNum;
            ClaimProcCur.PatNum = PatCur.PatNum;
            ClaimProcCur.ProvNum = ProcCur.ProvNum;
            ClaimProcCur.Status = ClaimProcStatus.Preauth;
            ClaimProcCur.FeeBilled = ProcCur.ProcFee;
            ClaimProcCur.PlanNum = FormIPS.SelectedPlan.PlanNum;
            ClaimProcCur.InsSubNum = FormIPS.SelectedSub.InsSubNum;
            cpExisting = ClaimProcs.getEstimate(ClaimProcList,ProcCur.ProcNum,FormIPS.SelectedPlan.PlanNum,FormIPS.SelectedSub.InsSubNum);
            if (cpExisting != null)
            {
                ClaimProcCur.InsPayEst = cpExisting.InsPayEst;
            }
             
            if (FormIPS.SelectedPlan.UseAltCode && (!StringSupport.equals(ProcedureCodes.getProcCode(ProcCur.CodeNum).AlternateCode1, "")))
            {
                ClaimProcCur.CodeSent = ProcedureCodes.getProcCode(ProcCur.CodeNum).AlternateCode1;
            }
            else if (FormIPS.SelectedPlan.IsMedical && !StringSupport.equals(ProcCur.MedicalCode, ""))
            {
                ClaimProcCur.CodeSent = ProcCur.MedicalCode;
            }
            else
            {
                ClaimProcCur.CodeSent = ProcedureCodes.getStringProcCode(ProcCur.CodeNum);
                if (ClaimProcCur.CodeSent.Length > 5 && StringSupport.equals(ClaimProcCur.CodeSent.Substring(0, 1), "D"))
                {
                    ClaimProcCur.CodeSent = ClaimProcCur.CodeSent.Substring(0, 5);
                }
                 
                if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
                {
                    //Canadian. en-CA or fr-CA
                    if (ClaimProcCur.CodeSent.Length > 5)
                    {
                        //In Canadian electronic claims, codes can contain letters or numbers and cannot be longer than 5 characters.
                        ClaimProcCur.CodeSent = ClaimProcCur.CodeSent.Substring(0, 5);
                    }
                     
                }
                 
            }  
            ClaimProcCur.LineNumber = (byte)(i + 1);
            ClaimProcs.insert(ClaimProcCur);
        }
        //ProcCur.Update(ProcOld);
        ProcList = Procedures.refresh(PatCur.PatNum);
        //ClaimProcList=ClaimProcs.Refresh(PatCur.PatNum);
        ClaimL.calculateAndUpdate(ProcList,InsPlanList,ClaimCur,PatPlanList,BenefitList,PatCur.getAge(),SubList);
        FormClaimEdit FormCE = new FormClaimEdit(ClaimCur,PatCur,FamCur);
        //FormCE.CalculateEstimates(
        FormCE.IsNew = true;
        //this causes it to delete the claim if cancelling.
        FormCE.ShowDialog();
        moduleSelected(PatCur.PatNum);
    }

    private void gridPreAuth_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Claim claim = Claims.getClaim(((Claim)ALPreAuth[e.getRow()]).ClaimNum);
        //gets attached images.
        FormClaimEdit FormC = new FormClaimEdit(claim,PatCur,FamCur);
        //FormClaimEdit2.IsPreAuth=true;
        FormC.ShowDialog();
        if (FormC.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        moduleSelected(PatCur.PatNum);
    }

    private void gridPreAuth_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (gridPlans.getSelectedIndices()[0] != 0)
        {
            return ;
        }
         
        gridMain.setSelected(false);
        Claim ClaimCur = (Claim)ALPreAuth[e.getRow()];
        List<ClaimProc> ClaimProcsForClaim = ClaimProcs.refreshForClaim(ClaimCur.ClaimNum);
        Procedure proc;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            //ProcListTP.Length;i++){
            if (gridMain.getRows().get___idx(i).getTag() == null)
            {
                continue;
            }
             
            //must be a subtotal row
            proc = (Procedure)gridMain.getRows().get___idx(i).getTag();
            for (int j = 0;j < ClaimProcsForClaim.Count;j++)
            {
                if (proc.ProcNum == ClaimProcsForClaim[j].ProcNum)
                {
                    gridMain.setSelected(i,true);
                }
                 
            }
        }
    }

}


