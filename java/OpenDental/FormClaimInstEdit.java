//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:49 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.ClaimL;
import OpenDental.ClearinghouseL;
import OpenDental.Eclaims.Canadian;
import OpenDental.Eclaims.Eclaims;
import OpenDental.FormClaimInstEdit;
import OpenDental.FormClaimPrint;
import OpenDental.FormClaimProc;
import OpenDental.FormEtransEdit;
import OpenDental.FormInsPlanSelect;
import OpenDental.FormPayment;
import OpenDental.LabelSingle;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.PrinterL;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimCondCodeLog;
import OpenDentBusiness.ClaimCondCodeLogs;
import OpenDentBusiness.ClaimForm;
import OpenDentBusiness.ClaimForms;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.ClaimSendQueueItem;
import OpenDentBusiness.ClaimValCodeLog;
import OpenDentBusiness.ClaimValCodeLogs;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ElectronicClaimFormat;
import OpenDentBusiness.Etrans;
import OpenDentBusiness.Etranss;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Tooth;

/*=============================================================================================================
Open Dental GPL license Copyright (C) 2003  Jordan Sparks, DMD.  http://www.open-dent.com,  www.docsparks.com
See header in FormOpenDental.cs for complete text.  Redistributions must retain this text.
===============================================================================================================*/
/**
* 
*/
public class FormClaimInstEdit  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPreAuth = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDate textDateRec;
    private OpenDental.ValidDate textDateSent;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    private OpenDental.UI.Button butOK;
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textPlan = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textClaimFee = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDouble textDedApplied;
    private OpenDental.ValidDouble textInsPayAmt;
    //private double ClaimFee;
    //private double PriInsPayEstSubtotal;
    //private double SecInsPayEstSubtotal;
    //private double PriInsPayEst;
    //private double SecInsPayEst;
    private OpenDental.UI.Button butCancel;
    private System.Windows.Forms.Label label21 = new System.Windows.Forms.Label();
    //private FormClaimSupplemental FormCS=new FormClaimSupplemental();
    private System.Windows.Forms.Label labelPreAuthNum = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelDateService = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboProvBill = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboProvTreat = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ListBox listClaimStatus = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.ListBox listClaimType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ComboBox comboPatRelat = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.ComboBox comboPatRelat2 = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.TextBox textPlan2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textInsPayEst = new System.Windows.Forms.TextBox();
    private OpenDental.ValidDouble textWriteOff;
    private OpenDental.UI.Button butPreview;
    private OpenDental.UI.Button butPrint;
    private OpenDental.UI.Button butOtherNone;
    private OpenDental.UI.Button butOtherCovChange;
    //private double DedAdjPerc;
    private List<ClaimProc> ClaimProcsForClaim = new List<ClaimProc>();
    /**
    * All claimprocs for the patient. Used to calculate remaining benefits, etc.
    */
    private List<ClaimProc> ClaimProcList = new List<ClaimProc>();
    /**
    * List of all procedures for this patient.  Used to get descriptions, etc.
    */
    private List<Procedure> ProcList = new List<Procedure>();
    private Patient PatCur;
    private Family FamCur;
    private List<InsPlan> PlanList = new List<InsPlan>();
    private OpenDental.ValidDate textDateService;
    private DataTable tablePayments = new DataTable();
    //ClaimPayment[] ClaimPaymentList;
    private System.Windows.Forms.ComboBox comboClinic = new System.Windows.Forms.ComboBox();
    private System.Windows.Forms.Label labelClinic = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butLabel;
    //private User user;
    /**
    * When user first opens form, if the claim is S or R, and the user does not have permission, the user is informed, and this is set to true.
    */
    private boolean notAuthorized = new boolean();
    private List<PatPlan> PatPlanList = new List<PatPlan>();
    private OpenDental.UI.ODGrid gridProc;
    private OpenDental.UI.Button butSend;
    private Claim ClaimCur;
    private GroupBox groupValueCodes = new GroupBox();
    private Label label15 = new Label();
    private Label label14 = new Label();
    private Label label13 = new Label();
    private Label label12 = new Label();
    private Label label11 = new Label();
    private Label label22 = new Label();
    private Label label25 = new Label();
    private TextBox textVC9Amount = new TextBox();
    private TextBox textVC6Amount = new TextBox();
    private TextBox textVC3Amount = new TextBox();
    private TextBox textVC0Amount = new TextBox();
    private TextBox textVC9Code = new TextBox();
    private TextBox textVC6Code = new TextBox();
    private TextBox textVC3Code = new TextBox();
    private TextBox textVC0Code = new TextBox();
    private Label label26 = new Label();
    private Label label30 = new Label();
    private Label label31 = new Label();
    private Label label32 = new Label();
    private Label label33 = new Label();
    private Label label34 = new Label();
    private Label label35 = new Label();
    private TextBox textVC10Amount = new TextBox();
    private TextBox textVC7Amount = new TextBox();
    private TextBox textVC4Amount = new TextBox();
    private TextBox textVC1Amount = new TextBox();
    private TextBox textVC10Code = new TextBox();
    private TextBox textVC7Code = new TextBox();
    private TextBox textVC4Code = new TextBox();
    private TextBox textVC1Code = new TextBox();
    private Label label17 = new Label();
    private Label label19 = new Label();
    private Label label23 = new Label();
    private Label label24 = new Label();
    private Label label28 = new Label();
    private Label label29 = new Label();
    private Label label27 = new Label();
    private TextBox textVC11Amount = new TextBox();
    private TextBox textVC8Amount = new TextBox();
    private TextBox textVC5Amount = new TextBox();
    private TextBox textVC2Amount = new TextBox();
    private TextBox textVC11Code = new TextBox();
    private TextBox textVC8Code = new TextBox();
    private TextBox textVC5Code = new TextBox();
    private TextBox textVC2Code = new TextBox();
    private Label label36 = new Label();
    private Label label37 = new Label();
    private Label label38 = new Label();
    private Label label39 = new Label();
    private Label label40 = new Label();
    private Label label41 = new Label();
    private ClaimForm ClaimFormCur;
    private Panel panelBottomEdge = new Panel();
    private Panel panelRightEdge = new Panel();
    private GroupBox groupBox1 = new GroupBox();
    private TextBox textCode10 = new TextBox();
    private TextBox textCode9 = new TextBox();
    private TextBox textCode8 = new TextBox();
    private TextBox textCode7 = new TextBox();
    private TextBox textCode6 = new TextBox();
    private TextBox textCode5 = new TextBox();
    private TextBox textCode4 = new TextBox();
    private TextBox textCode3 = new TextBox();
    private TextBox textCode2 = new TextBox();
    private TextBox textCode1 = new TextBox();
    private TextBox textCode0 = new TextBox();
    private Label label60 = new Label();
    private Label label59 = new Label();
    private Label label58 = new Label();
    private Label label57 = new Label();
    private Label label56 = new Label();
    private Label label55 = new Label();
    private Label label54 = new Label();
    private Label label53 = new Label();
    private Label label52 = new Label();
    private Label label51 = new Label();
    private Label label50 = new Label();
    private List<ClaimValCodeLog> ClaimValCodes = new List<ClaimValCodeLog>();
    private ClaimCondCodeLog CurCondCodeLog;
    private List<Claim> ClaimList = new List<Claim>();
    private OpenDental.UI.Button butHistory;
    private boolean doubleClickWarningAlreadyDisplayed = false;
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * 
    */
    public FormClaimInstEdit(Claim claimCur, Patient patCur, Family famCur) throws Exception {
        PatCur = patCur;
        FamCur = famCur;
        ClaimCur = claimCur;
        if (ClaimCur.ClaimForm != 0)
        {
            ClaimFormCur = ClaimForms.getClaimForm(ClaimCur.ClaimForm);
            ClaimValCodes = ClaimValCodeLogs.GetValCodes(ClaimCur.ClaimNum);
            CurCondCodeLog = ClaimCondCodeLogs.GetOne(ClaimCur.ClaimNum);
        }
         
        initializeComponent();
        // Required for Windows Form Designer support
        //tbPay.CellDoubleClicked += new OpenDental.ContrTable.CellEventHandler(tbPay_CellDoubleClicked);
        //tbProc.CellClicked += new OpenDental.ContrTable.CellEventHandler(tbProc_CellClicked);
        //tbPay.CellClicked += new OpenDental.ContrTable.CellEventHandler(tbPay_CellClicked);
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormClaimInstEdit.class);
        this.label3 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.labelDateService = new System.Windows.Forms.Label();
        this.labelPreAuthNum = new System.Windows.Forms.Label();
        this.textInsPayEst = new System.Windows.Forms.TextBox();
        this.textPreAuth = new System.Windows.Forms.TextBox();
        this.textClaimFee = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textPlan = new System.Windows.Forms.TextBox();
        this.label21 = new System.Windows.Forms.Label();
        this.comboProvBill = new System.Windows.Forms.ComboBox();
        this.comboProvTreat = new System.Windows.Forms.ComboBox();
        this.listClaimStatus = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.listClaimType = new System.Windows.Forms.ListBox();
        this.label9 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.comboPatRelat = new System.Windows.Forms.ComboBox();
        this.label5 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.butOtherNone = new OpenDental.UI.Button();
        this.butOtherCovChange = new OpenDental.UI.Button();
        this.comboPatRelat2 = new System.Windows.Forms.ComboBox();
        this.label10 = new System.Windows.Forms.Label();
        this.textPlan2 = new System.Windows.Forms.TextBox();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.groupValueCodes = new System.Windows.Forms.GroupBox();
        this.textVC11Amount = new System.Windows.Forms.TextBox();
        this.textVC8Amount = new System.Windows.Forms.TextBox();
        this.textVC5Amount = new System.Windows.Forms.TextBox();
        this.textVC2Amount = new System.Windows.Forms.TextBox();
        this.textVC11Code = new System.Windows.Forms.TextBox();
        this.textVC8Code = new System.Windows.Forms.TextBox();
        this.textVC5Code = new System.Windows.Forms.TextBox();
        this.textVC2Code = new System.Windows.Forms.TextBox();
        this.label36 = new System.Windows.Forms.Label();
        this.label37 = new System.Windows.Forms.Label();
        this.label38 = new System.Windows.Forms.Label();
        this.label39 = new System.Windows.Forms.Label();
        this.label40 = new System.Windows.Forms.Label();
        this.label41 = new System.Windows.Forms.Label();
        this.textVC10Amount = new System.Windows.Forms.TextBox();
        this.textVC7Amount = new System.Windows.Forms.TextBox();
        this.textVC4Amount = new System.Windows.Forms.TextBox();
        this.textVC1Amount = new System.Windows.Forms.TextBox();
        this.textVC10Code = new System.Windows.Forms.TextBox();
        this.textVC7Code = new System.Windows.Forms.TextBox();
        this.textVC4Code = new System.Windows.Forms.TextBox();
        this.textVC1Code = new System.Windows.Forms.TextBox();
        this.label17 = new System.Windows.Forms.Label();
        this.label19 = new System.Windows.Forms.Label();
        this.label23 = new System.Windows.Forms.Label();
        this.label24 = new System.Windows.Forms.Label();
        this.label28 = new System.Windows.Forms.Label();
        this.label29 = new System.Windows.Forms.Label();
        this.label27 = new System.Windows.Forms.Label();
        this.label26 = new System.Windows.Forms.Label();
        this.label25 = new System.Windows.Forms.Label();
        this.textVC9Amount = new System.Windows.Forms.TextBox();
        this.textVC6Amount = new System.Windows.Forms.TextBox();
        this.textVC3Amount = new System.Windows.Forms.TextBox();
        this.textVC0Amount = new System.Windows.Forms.TextBox();
        this.textVC9Code = new System.Windows.Forms.TextBox();
        this.textVC6Code = new System.Windows.Forms.TextBox();
        this.textVC3Code = new System.Windows.Forms.TextBox();
        this.textVC0Code = new System.Windows.Forms.TextBox();
        this.label22 = new System.Windows.Forms.Label();
        this.label15 = new System.Windows.Forms.Label();
        this.label14 = new System.Windows.Forms.Label();
        this.label13 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label30 = new System.Windows.Forms.Label();
        this.label31 = new System.Windows.Forms.Label();
        this.label32 = new System.Windows.Forms.Label();
        this.label33 = new System.Windows.Forms.Label();
        this.label34 = new System.Windows.Forms.Label();
        this.label35 = new System.Windows.Forms.Label();
        this.panelBottomEdge = new System.Windows.Forms.Panel();
        this.panelRightEdge = new System.Windows.Forms.Panel();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label60 = new System.Windows.Forms.Label();
        this.label59 = new System.Windows.Forms.Label();
        this.label58 = new System.Windows.Forms.Label();
        this.label57 = new System.Windows.Forms.Label();
        this.label56 = new System.Windows.Forms.Label();
        this.label55 = new System.Windows.Forms.Label();
        this.label54 = new System.Windows.Forms.Label();
        this.label53 = new System.Windows.Forms.Label();
        this.label52 = new System.Windows.Forms.Label();
        this.label51 = new System.Windows.Forms.Label();
        this.label50 = new System.Windows.Forms.Label();
        this.textCode10 = new System.Windows.Forms.TextBox();
        this.textCode9 = new System.Windows.Forms.TextBox();
        this.textCode8 = new System.Windows.Forms.TextBox();
        this.textCode7 = new System.Windows.Forms.TextBox();
        this.textCode6 = new System.Windows.Forms.TextBox();
        this.textCode5 = new System.Windows.Forms.TextBox();
        this.textCode4 = new System.Windows.Forms.TextBox();
        this.textCode3 = new System.Windows.Forms.TextBox();
        this.textCode2 = new System.Windows.Forms.TextBox();
        this.textCode1 = new System.Windows.Forms.TextBox();
        this.textCode0 = new System.Windows.Forms.TextBox();
        this.butHistory = new OpenDental.UI.Button();
        this.butSend = new OpenDental.UI.Button();
        this.butLabel = new OpenDental.UI.Button();
        this.textDateService = new OpenDental.ValidDate();
        this.textWriteOff = new OpenDental.ValidDouble();
        this.textInsPayAmt = new OpenDental.ValidDouble();
        this.textDedApplied = new OpenDental.ValidDouble();
        this.textDateSent = new OpenDental.ValidDate();
        this.textDateRec = new OpenDental.ValidDate();
        this.butPreview = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.gridProc = new OpenDental.UI.ODGrid();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupValueCodes.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(241, 98);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(94, 15);
        this.label3.TabIndex = 2;
        this.label3.Text = "Billing Dentist";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(2, 135);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(108, 16);
        this.label6.TabIndex = 5;
        this.label6.Text = "Date Received";
        this.label6.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(5, 114);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(104, 16);
        this.label8.TabIndex = 7;
        this.label8.Text = "DateSent";
        this.label8.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelDateService
        //
        this.labelDateService.Location = new System.Drawing.Point(3, 94);
        this.labelDateService.Name = "labelDateService";
        this.labelDateService.Size = new System.Drawing.Size(107, 16);
        this.labelDateService.TabIndex = 8;
        this.labelDateService.Text = "Date of Service";
        this.labelDateService.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // labelPreAuthNum
        //
        this.labelPreAuthNum.Location = new System.Drawing.Point(199, 142);
        this.labelPreAuthNum.Name = "labelPreAuthNum";
        this.labelPreAuthNum.Size = new System.Drawing.Size(138, 16);
        this.labelPreAuthNum.TabIndex = 11;
        this.labelPreAuthNum.Text = "PreAuth Number";
        this.labelPreAuthNum.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textInsPayEst
        //
        this.textInsPayEst.Location = new System.Drawing.Point(511, 519);
        this.textInsPayEst.Name = "textInsPayEst";
        this.textInsPayEst.ReadOnly = true;
        this.textInsPayEst.Size = new System.Drawing.Size(51, 20);
        this.textInsPayEst.TabIndex = 40;
        this.textInsPayEst.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textPreAuth
        //
        this.textPreAuth.Location = new System.Drawing.Point(336, 138);
        this.textPreAuth.Name = "textPreAuth";
        this.textPreAuth.Size = new System.Drawing.Size(170, 20);
        this.textPreAuth.TabIndex = 1;
        //
        // textClaimFee
        //
        this.textClaimFee.Location = new System.Drawing.Point(349, 519);
        this.textClaimFee.Name = "textClaimFee";
        this.textClaimFee.ReadOnly = true;
        this.textClaimFee.Size = new System.Drawing.Size(63, 20);
        this.textClaimFee.TabIndex = 51;
        this.textClaimFee.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label1
        //
        this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.label1.Location = new System.Drawing.Point(229, 522);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(116, 15);
        this.label1.TabIndex = 50;
        this.label1.Text = "Totals";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPlan
        //
        this.textPlan.Location = new System.Drawing.Point(8, 20);
        this.textPlan.Name = "textPlan";
        this.textPlan.ReadOnly = true;
        this.textPlan.Size = new System.Drawing.Size(253, 20);
        this.textPlan.TabIndex = 1;
        //
        // label21
        //
        this.label21.Location = new System.Drawing.Point(234, 119);
        this.label21.Name = "label21";
        this.label21.Size = new System.Drawing.Size(102, 15);
        this.label21.TabIndex = 93;
        this.label21.Text = "Treating Dentist";
        this.label21.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // comboProvBill
        //
        this.comboProvBill.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvBill.Location = new System.Drawing.Point(336, 94);
        this.comboProvBill.Name = "comboProvBill";
        this.comboProvBill.Size = new System.Drawing.Size(100, 21);
        this.comboProvBill.TabIndex = 97;
        //
        // comboProvTreat
        //
        this.comboProvTreat.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvTreat.Location = new System.Drawing.Point(336, 116);
        this.comboProvTreat.Name = "comboProvTreat";
        this.comboProvTreat.Size = new System.Drawing.Size(100, 21);
        this.comboProvTreat.TabIndex = 99;
        //
        // listClaimStatus
        //
        this.listClaimStatus.Location = new System.Drawing.Point(111, 8);
        this.listClaimStatus.Name = "listClaimStatus";
        this.listClaimStatus.Size = new System.Drawing.Size(120, 82);
        this.listClaimStatus.TabIndex = 103;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 8);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 14);
        this.label2.TabIndex = 104;
        this.label2.Text = "Claim Status";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // listClaimType
        //
        this.listClaimType.ForeColor = System.Drawing.SystemColors.WindowText;
        this.listClaimType.Location = new System.Drawing.Point(336, 24);
        this.listClaimType.Name = "listClaimType";
        this.listClaimType.Size = new System.Drawing.Size(98, 69);
        this.listClaimType.TabIndex = 108;
        this.listClaimType.MouseUp += new System.Windows.Forms.MouseEventHandler(this.listClaimType_MouseUp);
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(239, 26);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(95, 17);
        this.label9.TabIndex = 109;
        this.label9.Text = "Claim Type";
        this.label9.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.comboPatRelat);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.textPlan);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(523, 2);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(269, 70);
        this.groupBox2.TabIndex = 110;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Insurance Plan";
        //
        // comboPatRelat
        //
        this.comboPatRelat.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPatRelat.Location = new System.Drawing.Point(90, 43);
        this.comboPatRelat.Name = "comboPatRelat";
        this.comboPatRelat.Size = new System.Drawing.Size(151, 21);
        this.comboPatRelat.TabIndex = 3;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 46);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(85, 17);
        this.label5.TabIndex = 2;
        this.label5.Text = "Relationship";
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.butOtherNone);
        this.groupBox3.Controls.Add(this.butOtherCovChange);
        this.groupBox3.Controls.Add(this.comboPatRelat2);
        this.groupBox3.Controls.Add(this.label10);
        this.groupBox3.Controls.Add(this.textPlan2);
        this.groupBox3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox3.Location = new System.Drawing.Point(523, 73);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(269, 85);
        this.groupBox3.TabIndex = 111;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Other Coverage";
        //
        // butOtherNone
        //
        this.butOtherNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOtherNone.setAutosize(true);
        this.butOtherNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOtherNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOtherNone.setCornerRadius(4F);
        this.butOtherNone.Location = new System.Drawing.Point(183, 9);
        this.butOtherNone.Name = "butOtherNone";
        this.butOtherNone.Size = new System.Drawing.Size(78, 24);
        this.butOtherNone.TabIndex = 5;
        this.butOtherNone.Text = "None";
        this.butOtherNone.Click += new System.EventHandler(this.butOtherNone_Click);
        //
        // butOtherCovChange
        //
        this.butOtherCovChange.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOtherCovChange.setAutosize(true);
        this.butOtherCovChange.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOtherCovChange.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOtherCovChange.setCornerRadius(4F);
        this.butOtherCovChange.Location = new System.Drawing.Point(101, 9);
        this.butOtherCovChange.Name = "butOtherCovChange";
        this.butOtherCovChange.Size = new System.Drawing.Size(78, 24);
        this.butOtherCovChange.TabIndex = 4;
        this.butOtherCovChange.Text = "Change";
        this.butOtherCovChange.Click += new System.EventHandler(this.butOtherCovChange_Click);
        //
        // comboPatRelat2
        //
        this.comboPatRelat2.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboPatRelat2.Location = new System.Drawing.Point(90, 57);
        this.comboPatRelat2.Name = "comboPatRelat2";
        this.comboPatRelat2.Size = new System.Drawing.Size(151, 21);
        this.comboPatRelat2.TabIndex = 3;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(6, 60);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(84, 17);
        this.label10.TabIndex = 2;
        this.label10.Text = "Relationship";
        //
        // textPlan2
        //
        this.textPlan2.Location = new System.Drawing.Point(8, 34);
        this.textPlan2.Name = "textPlan2";
        this.textPlan2.ReadOnly = true;
        this.textPlan2.Size = new System.Drawing.Size(253, 20);
        this.textPlan2.TabIndex = 1;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Enabled = false;
        this.comboClinic.Location = new System.Drawing.Point(336, 2);
        this.comboClinic.MaxDropDownItems = 100;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(126, 21);
        this.comboClinic.TabIndex = 121;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(235, 6);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(98, 16);
        this.labelClinic.TabIndex = 120;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupValueCodes
        //
        this.groupValueCodes.Controls.Add(this.textVC11Amount);
        this.groupValueCodes.Controls.Add(this.textVC8Amount);
        this.groupValueCodes.Controls.Add(this.textVC5Amount);
        this.groupValueCodes.Controls.Add(this.textVC2Amount);
        this.groupValueCodes.Controls.Add(this.textVC11Code);
        this.groupValueCodes.Controls.Add(this.textVC8Code);
        this.groupValueCodes.Controls.Add(this.textVC5Code);
        this.groupValueCodes.Controls.Add(this.textVC2Code);
        this.groupValueCodes.Controls.Add(this.label36);
        this.groupValueCodes.Controls.Add(this.label37);
        this.groupValueCodes.Controls.Add(this.label38);
        this.groupValueCodes.Controls.Add(this.label39);
        this.groupValueCodes.Controls.Add(this.label40);
        this.groupValueCodes.Controls.Add(this.label41);
        this.groupValueCodes.Controls.Add(this.textVC10Amount);
        this.groupValueCodes.Controls.Add(this.textVC7Amount);
        this.groupValueCodes.Controls.Add(this.textVC4Amount);
        this.groupValueCodes.Controls.Add(this.textVC1Amount);
        this.groupValueCodes.Controls.Add(this.textVC10Code);
        this.groupValueCodes.Controls.Add(this.textVC7Code);
        this.groupValueCodes.Controls.Add(this.textVC4Code);
        this.groupValueCodes.Controls.Add(this.textVC1Code);
        this.groupValueCodes.Controls.Add(this.label17);
        this.groupValueCodes.Controls.Add(this.label19);
        this.groupValueCodes.Controls.Add(this.label23);
        this.groupValueCodes.Controls.Add(this.label24);
        this.groupValueCodes.Controls.Add(this.label28);
        this.groupValueCodes.Controls.Add(this.label29);
        this.groupValueCodes.Controls.Add(this.label27);
        this.groupValueCodes.Controls.Add(this.label26);
        this.groupValueCodes.Controls.Add(this.label25);
        this.groupValueCodes.Controls.Add(this.textVC9Amount);
        this.groupValueCodes.Controls.Add(this.textVC6Amount);
        this.groupValueCodes.Controls.Add(this.textVC3Amount);
        this.groupValueCodes.Controls.Add(this.textVC0Amount);
        this.groupValueCodes.Controls.Add(this.textVC9Code);
        this.groupValueCodes.Controls.Add(this.textVC6Code);
        this.groupValueCodes.Controls.Add(this.textVC3Code);
        this.groupValueCodes.Controls.Add(this.textVC0Code);
        this.groupValueCodes.Controls.Add(this.label22);
        this.groupValueCodes.Controls.Add(this.label15);
        this.groupValueCodes.Controls.Add(this.label14);
        this.groupValueCodes.Controls.Add(this.label13);
        this.groupValueCodes.Controls.Add(this.label12);
        this.groupValueCodes.Controls.Add(this.label11);
        this.groupValueCodes.Location = new System.Drawing.Point(480, 165);
        this.groupValueCodes.Name = "groupValueCodes";
        this.groupValueCodes.Size = new System.Drawing.Size(434, 114);
        this.groupValueCodes.TabIndex = 130;
        this.groupValueCodes.TabStop = false;
        this.groupValueCodes.Text = "Value Codes";
        //
        // textVC11Amount
        //
        this.textVC11Amount.Location = new System.Drawing.Point(343, 90);
        this.textVC11Amount.Name = "textVC11Amount";
        this.textVC11Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC11Amount.TabIndex = 56;
        this.textVC11Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC8Amount
        //
        this.textVC8Amount.Location = new System.Drawing.Point(343, 71);
        this.textVC8Amount.Name = "textVC8Amount";
        this.textVC8Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC8Amount.TabIndex = 55;
        this.textVC8Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC5Amount
        //
        this.textVC5Amount.Location = new System.Drawing.Point(343, 52);
        this.textVC5Amount.Name = "textVC5Amount";
        this.textVC5Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC5Amount.TabIndex = 54;
        this.textVC5Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC2Amount
        //
        this.textVC2Amount.Location = new System.Drawing.Point(343, 33);
        this.textVC2Amount.Name = "textVC2Amount";
        this.textVC2Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC2Amount.TabIndex = 53;
        this.textVC2Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC11Code
        //
        this.textVC11Code.Location = new System.Drawing.Point(313, 90);
        this.textVC11Code.MaxLength = 2;
        this.textVC11Code.Name = "textVC11Code";
        this.textVC11Code.Size = new System.Drawing.Size(26, 20);
        this.textVC11Code.TabIndex = 52;
        this.textVC11Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC8Code
        //
        this.textVC8Code.Location = new System.Drawing.Point(313, 71);
        this.textVC8Code.MaxLength = 2;
        this.textVC8Code.Name = "textVC8Code";
        this.textVC8Code.Size = new System.Drawing.Size(26, 20);
        this.textVC8Code.TabIndex = 51;
        this.textVC8Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC5Code
        //
        this.textVC5Code.Location = new System.Drawing.Point(313, 52);
        this.textVC5Code.MaxLength = 2;
        this.textVC5Code.Name = "textVC5Code";
        this.textVC5Code.Size = new System.Drawing.Size(26, 20);
        this.textVC5Code.TabIndex = 50;
        this.textVC5Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC2Code
        //
        this.textVC2Code.Location = new System.Drawing.Point(313, 33);
        this.textVC2Code.MaxLength = 2;
        this.textVC2Code.Name = "textVC2Code";
        this.textVC2Code.Size = new System.Drawing.Size(26, 20);
        this.textVC2Code.TabIndex = 49;
        this.textVC2Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label36
        //
        this.label36.AutoSize = true;
        this.label36.Location = new System.Drawing.Point(355, 18);
        this.label36.Name = "label36";
        this.label36.Size = new System.Drawing.Size(42, 13);
        this.label36.TabIndex = 48;
        this.label36.Text = "amount";
        //
        // label37
        //
        this.label37.AutoSize = true;
        this.label37.Location = new System.Drawing.Point(311, 18);
        this.label37.Name = "label37";
        this.label37.Size = new System.Drawing.Size(31, 13);
        this.label37.TabIndex = 47;
        this.label37.Text = "code";
        //
        // label38
        //
        this.label38.AutoSize = true;
        this.label38.Location = new System.Drawing.Point(292, 94);
        this.label38.Name = "label38";
        this.label38.Size = new System.Drawing.Size(13, 13);
        this.label38.TabIndex = 46;
        this.label38.Text = "d";
        //
        // label39
        //
        this.label39.AutoSize = true;
        this.label39.Location = new System.Drawing.Point(292, 75);
        this.label39.Name = "label39";
        this.label39.Size = new System.Drawing.Size(13, 13);
        this.label39.TabIndex = 45;
        this.label39.Text = "c";
        //
        // label40
        //
        this.label40.AutoSize = true;
        this.label40.Location = new System.Drawing.Point(292, 56);
        this.label40.Name = "label40";
        this.label40.Size = new System.Drawing.Size(13, 13);
        this.label40.TabIndex = 44;
        this.label40.Text = "b";
        //
        // label41
        //
        this.label41.AutoSize = true;
        this.label41.Location = new System.Drawing.Point(292, 37);
        this.label41.Name = "label41";
        this.label41.Size = new System.Drawing.Size(13, 13);
        this.label41.TabIndex = 43;
        this.label41.Text = "a";
        //
        // textVC10Amount
        //
        this.textVC10Amount.Location = new System.Drawing.Point(203, 89);
        this.textVC10Amount.Name = "textVC10Amount";
        this.textVC10Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC10Amount.TabIndex = 42;
        this.textVC10Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC7Amount
        //
        this.textVC7Amount.Location = new System.Drawing.Point(203, 70);
        this.textVC7Amount.Name = "textVC7Amount";
        this.textVC7Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC7Amount.TabIndex = 41;
        this.textVC7Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC4Amount
        //
        this.textVC4Amount.Location = new System.Drawing.Point(203, 51);
        this.textVC4Amount.Name = "textVC4Amount";
        this.textVC4Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC4Amount.TabIndex = 40;
        this.textVC4Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC1Amount
        //
        this.textVC1Amount.Location = new System.Drawing.Point(203, 32);
        this.textVC1Amount.Name = "textVC1Amount";
        this.textVC1Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC1Amount.TabIndex = 39;
        this.textVC1Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC10Code
        //
        this.textVC10Code.Location = new System.Drawing.Point(173, 89);
        this.textVC10Code.MaxLength = 2;
        this.textVC10Code.Name = "textVC10Code";
        this.textVC10Code.Size = new System.Drawing.Size(26, 20);
        this.textVC10Code.TabIndex = 38;
        this.textVC10Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC7Code
        //
        this.textVC7Code.Location = new System.Drawing.Point(173, 70);
        this.textVC7Code.MaxLength = 2;
        this.textVC7Code.Name = "textVC7Code";
        this.textVC7Code.Size = new System.Drawing.Size(26, 20);
        this.textVC7Code.TabIndex = 37;
        this.textVC7Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC4Code
        //
        this.textVC4Code.Location = new System.Drawing.Point(173, 51);
        this.textVC4Code.MaxLength = 2;
        this.textVC4Code.Name = "textVC4Code";
        this.textVC4Code.Size = new System.Drawing.Size(26, 20);
        this.textVC4Code.TabIndex = 36;
        this.textVC4Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC1Code
        //
        this.textVC1Code.Location = new System.Drawing.Point(173, 32);
        this.textVC1Code.MaxLength = 2;
        this.textVC1Code.Name = "textVC1Code";
        this.textVC1Code.Size = new System.Drawing.Size(26, 20);
        this.textVC1Code.TabIndex = 35;
        this.textVC1Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label17
        //
        this.label17.AutoSize = true;
        this.label17.Location = new System.Drawing.Point(215, 17);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(42, 13);
        this.label17.TabIndex = 34;
        this.label17.Text = "amount";
        //
        // label19
        //
        this.label19.AutoSize = true;
        this.label19.Location = new System.Drawing.Point(171, 17);
        this.label19.Name = "label19";
        this.label19.Size = new System.Drawing.Size(31, 13);
        this.label19.TabIndex = 33;
        this.label19.Text = "code";
        //
        // label23
        //
        this.label23.AutoSize = true;
        this.label23.Location = new System.Drawing.Point(152, 93);
        this.label23.Name = "label23";
        this.label23.Size = new System.Drawing.Size(13, 13);
        this.label23.TabIndex = 32;
        this.label23.Text = "d";
        //
        // label24
        //
        this.label24.AutoSize = true;
        this.label24.Location = new System.Drawing.Point(152, 74);
        this.label24.Name = "label24";
        this.label24.Size = new System.Drawing.Size(13, 13);
        this.label24.TabIndex = 31;
        this.label24.Text = "c";
        //
        // label28
        //
        this.label28.AutoSize = true;
        this.label28.Location = new System.Drawing.Point(152, 55);
        this.label28.Name = "label28";
        this.label28.Size = new System.Drawing.Size(13, 13);
        this.label28.TabIndex = 30;
        this.label28.Text = "b";
        //
        // label29
        //
        this.label29.AutoSize = true;
        this.label29.Location = new System.Drawing.Point(152, 36);
        this.label29.Name = "label29";
        this.label29.Size = new System.Drawing.Size(13, 13);
        this.label29.TabIndex = 29;
        this.label29.Text = "a";
        //
        // label27
        //
        this.label27.AutoSize = true;
        this.label27.Location = new System.Drawing.Point(292, 16);
        this.label27.Name = "label27";
        this.label27.Size = new System.Drawing.Size(19, 13);
        this.label27.TabIndex = 28;
        this.label27.Text = "41";
        //
        // label26
        //
        this.label26.AutoSize = true;
        this.label26.Location = new System.Drawing.Point(149, 16);
        this.label26.Name = "label26";
        this.label26.Size = new System.Drawing.Size(19, 13);
        this.label26.TabIndex = 27;
        this.label26.Text = "40";
        //
        // label25
        //
        this.label25.AutoSize = true;
        this.label25.Location = new System.Drawing.Point(12, 16);
        this.label25.Name = "label25";
        this.label25.Size = new System.Drawing.Size(19, 13);
        this.label25.TabIndex = 18;
        this.label25.Text = "39";
        //
        // textVC9Amount
        //
        this.textVC9Amount.Location = new System.Drawing.Point(66, 88);
        this.textVC9Amount.Name = "textVC9Amount";
        this.textVC9Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC9Amount.TabIndex = 17;
        this.textVC9Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC6Amount
        //
        this.textVC6Amount.Location = new System.Drawing.Point(66, 69);
        this.textVC6Amount.Name = "textVC6Amount";
        this.textVC6Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC6Amount.TabIndex = 16;
        this.textVC6Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC3Amount
        //
        this.textVC3Amount.Location = new System.Drawing.Point(66, 50);
        this.textVC3Amount.Name = "textVC3Amount";
        this.textVC3Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC3Amount.TabIndex = 15;
        this.textVC3Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC0Amount
        //
        this.textVC0Amount.Location = new System.Drawing.Point(66, 31);
        this.textVC0Amount.Name = "textVC0Amount";
        this.textVC0Amount.Size = new System.Drawing.Size(66, 20);
        this.textVC0Amount.TabIndex = 14;
        this.textVC0Amount.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textVC9Code
        //
        this.textVC9Code.Location = new System.Drawing.Point(36, 88);
        this.textVC9Code.MaxLength = 2;
        this.textVC9Code.Name = "textVC9Code";
        this.textVC9Code.Size = new System.Drawing.Size(26, 20);
        this.textVC9Code.TabIndex = 13;
        this.textVC9Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC6Code
        //
        this.textVC6Code.Location = new System.Drawing.Point(36, 69);
        this.textVC6Code.MaxLength = 2;
        this.textVC6Code.Name = "textVC6Code";
        this.textVC6Code.Size = new System.Drawing.Size(26, 20);
        this.textVC6Code.TabIndex = 12;
        this.textVC6Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC3Code
        //
        this.textVC3Code.Location = new System.Drawing.Point(36, 50);
        this.textVC3Code.MaxLength = 2;
        this.textVC3Code.Name = "textVC3Code";
        this.textVC3Code.Size = new System.Drawing.Size(26, 20);
        this.textVC3Code.TabIndex = 11;
        this.textVC3Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textVC0Code
        //
        this.textVC0Code.Location = new System.Drawing.Point(36, 31);
        this.textVC0Code.MaxLength = 2;
        this.textVC0Code.Name = "textVC0Code";
        this.textVC0Code.Size = new System.Drawing.Size(26, 20);
        this.textVC0Code.TabIndex = 10;
        this.textVC0Code.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // label22
        //
        this.label22.AutoSize = true;
        this.label22.Location = new System.Drawing.Point(78, 16);
        this.label22.Name = "label22";
        this.label22.Size = new System.Drawing.Size(42, 13);
        this.label22.TabIndex = 7;
        this.label22.Text = "amount";
        //
        // label15
        //
        this.label15.AutoSize = true;
        this.label15.Location = new System.Drawing.Point(34, 16);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(31, 13);
        this.label15.TabIndex = 4;
        this.label15.Text = "code";
        //
        // label14
        //
        this.label14.AutoSize = true;
        this.label14.Location = new System.Drawing.Point(15, 92);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(13, 13);
        this.label14.TabIndex = 3;
        this.label14.Text = "d";
        //
        // label13
        //
        this.label13.AutoSize = true;
        this.label13.Location = new System.Drawing.Point(15, 73);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(13, 13);
        this.label13.TabIndex = 2;
        this.label13.Text = "c";
        //
        // label12
        //
        this.label12.AutoSize = true;
        this.label12.Location = new System.Drawing.Point(15, 54);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(13, 13);
        this.label12.TabIndex = 1;
        this.label12.Text = "b";
        //
        // label11
        //
        this.label11.AutoSize = true;
        this.label11.Location = new System.Drawing.Point(15, 35);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(13, 13);
        this.label11.TabIndex = 0;
        this.label11.Text = "a";
        //
        // label30
        //
        this.label30.AutoSize = true;
        this.label30.Location = new System.Drawing.Point(358, 16);
        this.label30.Name = "label30";
        this.label30.Size = new System.Drawing.Size(42, 13);
        this.label30.TabIndex = 48;
        this.label30.Text = "amount";
        //
        // label31
        //
        this.label31.AutoSize = true;
        this.label31.Location = new System.Drawing.Point(314, 16);
        this.label31.Name = "label31";
        this.label31.Size = new System.Drawing.Size(31, 13);
        this.label31.TabIndex = 47;
        this.label31.Text = "code";
        //
        // label32
        //
        this.label32.AutoSize = true;
        this.label32.Location = new System.Drawing.Point(295, 92);
        this.label32.Name = "label32";
        this.label32.Size = new System.Drawing.Size(13, 13);
        this.label32.TabIndex = 46;
        this.label32.Text = "d";
        //
        // label33
        //
        this.label33.AutoSize = true;
        this.label33.Location = new System.Drawing.Point(295, 73);
        this.label33.Name = "label33";
        this.label33.Size = new System.Drawing.Size(13, 13);
        this.label33.TabIndex = 45;
        this.label33.Text = "c";
        //
        // label34
        //
        this.label34.AutoSize = true;
        this.label34.Location = new System.Drawing.Point(295, 54);
        this.label34.Name = "label34";
        this.label34.Size = new System.Drawing.Size(13, 13);
        this.label34.TabIndex = 44;
        this.label34.Text = "b";
        //
        // label35
        //
        this.label35.AutoSize = true;
        this.label35.Location = new System.Drawing.Point(295, 35);
        this.label35.Name = "label35";
        this.label35.Size = new System.Drawing.Size(13, 13);
        this.label35.TabIndex = 43;
        this.label35.Text = "a";
        //
        // panelBottomEdge
        //
        this.panelBottomEdge.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panelBottomEdge.Location = new System.Drawing.Point(300, 700);
        this.panelBottomEdge.Name = "panelBottomEdge";
        this.panelBottomEdge.Size = new System.Drawing.Size(682, 1);
        this.panelBottomEdge.TabIndex = 131;
        this.panelBottomEdge.Visible = false;
        //
        // panelRightEdge
        //
        this.panelRightEdge.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        this.panelRightEdge.Location = new System.Drawing.Point(982, 200);
        this.panelRightEdge.Name = "panelRightEdge";
        this.panelRightEdge.Size = new System.Drawing.Size(1, 500);
        this.panelRightEdge.TabIndex = 132;
        this.panelRightEdge.Visible = false;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label60);
        this.groupBox1.Controls.Add(this.label59);
        this.groupBox1.Controls.Add(this.label58);
        this.groupBox1.Controls.Add(this.label57);
        this.groupBox1.Controls.Add(this.label56);
        this.groupBox1.Controls.Add(this.label55);
        this.groupBox1.Controls.Add(this.label54);
        this.groupBox1.Controls.Add(this.label53);
        this.groupBox1.Controls.Add(this.label52);
        this.groupBox1.Controls.Add(this.label51);
        this.groupBox1.Controls.Add(this.label50);
        this.groupBox1.Controls.Add(this.textCode10);
        this.groupBox1.Controls.Add(this.textCode9);
        this.groupBox1.Controls.Add(this.textCode8);
        this.groupBox1.Controls.Add(this.textCode7);
        this.groupBox1.Controls.Add(this.textCode6);
        this.groupBox1.Controls.Add(this.textCode5);
        this.groupBox1.Controls.Add(this.textCode4);
        this.groupBox1.Controls.Add(this.textCode3);
        this.groupBox1.Controls.Add(this.textCode2);
        this.groupBox1.Controls.Add(this.textCode1);
        this.groupBox1.Controls.Add(this.textCode0);
        this.groupBox1.Location = new System.Drawing.Point(5, 165);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(433, 67);
        this.groupBox1.TabIndex = 131;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Condition Codes";
        //
        // label60
        //
        this.label60.AutoSize = true;
        this.label60.Location = new System.Drawing.Point(398, 19);
        this.label60.Name = "label60";
        this.label60.Size = new System.Drawing.Size(19, 13);
        this.label60.TabIndex = 78;
        this.label60.Text = "28";
        //
        // label59
        //
        this.label59.AutoSize = true;
        this.label59.Location = new System.Drawing.Point(360, 19);
        this.label59.Name = "label59";
        this.label59.Size = new System.Drawing.Size(19, 13);
        this.label59.TabIndex = 77;
        this.label59.Text = "27";
        //
        // label58
        //
        this.label58.AutoSize = true;
        this.label58.Location = new System.Drawing.Point(322, 19);
        this.label58.Name = "label58";
        this.label58.Size = new System.Drawing.Size(19, 13);
        this.label58.TabIndex = 76;
        this.label58.Text = "26";
        //
        // label57
        //
        this.label57.AutoSize = true;
        this.label57.Location = new System.Drawing.Point(284, 19);
        this.label57.Name = "label57";
        this.label57.Size = new System.Drawing.Size(19, 13);
        this.label57.TabIndex = 75;
        this.label57.Text = "25";
        //
        // label56
        //
        this.label56.AutoSize = true;
        this.label56.Location = new System.Drawing.Point(246, 19);
        this.label56.Name = "label56";
        this.label56.Size = new System.Drawing.Size(19, 13);
        this.label56.TabIndex = 74;
        this.label56.Text = "24";
        //
        // label55
        //
        this.label55.AutoSize = true;
        this.label55.Location = new System.Drawing.Point(209, 19);
        this.label55.Name = "label55";
        this.label55.Size = new System.Drawing.Size(19, 13);
        this.label55.TabIndex = 73;
        this.label55.Text = "23";
        //
        // label54
        //
        this.label54.AutoSize = true;
        this.label54.Location = new System.Drawing.Point(170, 19);
        this.label54.Name = "label54";
        this.label54.Size = new System.Drawing.Size(19, 13);
        this.label54.TabIndex = 72;
        this.label54.Text = "22";
        //
        // label53
        //
        this.label53.AutoSize = true;
        this.label53.Location = new System.Drawing.Point(132, 19);
        this.label53.Name = "label53";
        this.label53.Size = new System.Drawing.Size(19, 13);
        this.label53.TabIndex = 71;
        this.label53.Text = "21";
        //
        // label52
        //
        this.label52.AutoSize = true;
        this.label52.Location = new System.Drawing.Point(94, 19);
        this.label52.Name = "label52";
        this.label52.Size = new System.Drawing.Size(19, 13);
        this.label52.TabIndex = 70;
        this.label52.Text = "20";
        //
        // label51
        //
        this.label51.AutoSize = true;
        this.label51.Location = new System.Drawing.Point(56, 19);
        this.label51.Name = "label51";
        this.label51.Size = new System.Drawing.Size(19, 13);
        this.label51.TabIndex = 69;
        this.label51.Text = "19";
        //
        // label50
        //
        this.label50.AutoSize = true;
        this.label50.Location = new System.Drawing.Point(18, 19);
        this.label50.Name = "label50";
        this.label50.Size = new System.Drawing.Size(19, 13);
        this.label50.TabIndex = 68;
        this.label50.Text = "18";
        //
        // textCode10
        //
        this.textCode10.Location = new System.Drawing.Point(394, 37);
        this.textCode10.MaxLength = 2;
        this.textCode10.Name = "textCode10";
        this.textCode10.Size = new System.Drawing.Size(26, 20);
        this.textCode10.TabIndex = 67;
        this.textCode10.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode9
        //
        this.textCode9.Location = new System.Drawing.Point(356, 37);
        this.textCode9.MaxLength = 2;
        this.textCode9.Name = "textCode9";
        this.textCode9.Size = new System.Drawing.Size(26, 20);
        this.textCode9.TabIndex = 66;
        this.textCode9.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode8
        //
        this.textCode8.Location = new System.Drawing.Point(318, 37);
        this.textCode8.MaxLength = 2;
        this.textCode8.Name = "textCode8";
        this.textCode8.Size = new System.Drawing.Size(26, 20);
        this.textCode8.TabIndex = 65;
        this.textCode8.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode7
        //
        this.textCode7.Location = new System.Drawing.Point(280, 37);
        this.textCode7.MaxLength = 2;
        this.textCode7.Name = "textCode7";
        this.textCode7.Size = new System.Drawing.Size(26, 20);
        this.textCode7.TabIndex = 64;
        this.textCode7.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode6
        //
        this.textCode6.Location = new System.Drawing.Point(242, 37);
        this.textCode6.MaxLength = 2;
        this.textCode6.Name = "textCode6";
        this.textCode6.Size = new System.Drawing.Size(26, 20);
        this.textCode6.TabIndex = 63;
        this.textCode6.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode5
        //
        this.textCode5.Location = new System.Drawing.Point(205, 37);
        this.textCode5.MaxLength = 2;
        this.textCode5.Name = "textCode5";
        this.textCode5.Size = new System.Drawing.Size(26, 20);
        this.textCode5.TabIndex = 62;
        this.textCode5.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode4
        //
        this.textCode4.Location = new System.Drawing.Point(166, 37);
        this.textCode4.MaxLength = 2;
        this.textCode4.Name = "textCode4";
        this.textCode4.Size = new System.Drawing.Size(26, 20);
        this.textCode4.TabIndex = 61;
        this.textCode4.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode3
        //
        this.textCode3.Location = new System.Drawing.Point(128, 37);
        this.textCode3.MaxLength = 2;
        this.textCode3.Name = "textCode3";
        this.textCode3.Size = new System.Drawing.Size(26, 20);
        this.textCode3.TabIndex = 60;
        this.textCode3.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode2
        //
        this.textCode2.Location = new System.Drawing.Point(90, 37);
        this.textCode2.MaxLength = 2;
        this.textCode2.Name = "textCode2";
        this.textCode2.Size = new System.Drawing.Size(26, 20);
        this.textCode2.TabIndex = 59;
        this.textCode2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode1
        //
        this.textCode1.Location = new System.Drawing.Point(52, 37);
        this.textCode1.MaxLength = 2;
        this.textCode1.Name = "textCode1";
        this.textCode1.Size = new System.Drawing.Size(26, 20);
        this.textCode1.TabIndex = 58;
        this.textCode1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // textCode0
        //
        this.textCode0.Location = new System.Drawing.Point(14, 37);
        this.textCode0.MaxLength = 2;
        this.textCode0.Name = "textCode0";
        this.textCode0.Size = new System.Drawing.Size(26, 20);
        this.textCode0.TabIndex = 57;
        this.textCode0.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
        //
        // butHistory
        //
        this.butHistory.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHistory.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butHistory.setAutosize(true);
        this.butHistory.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHistory.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHistory.setCornerRadius(4F);
        this.butHistory.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butHistory.Location = new System.Drawing.Point(595, 706);
        this.butHistory.Name = "butHistory";
        this.butHistory.Size = new System.Drawing.Size(86, 24);
        this.butHistory.TabIndex = 136;
        this.butHistory.Text = "History";
        this.butHistory.Click += new System.EventHandler(this.butHistory_Click);
        //
        // butSend
        //
        this.butSend.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butSend.setAutosize(true);
        this.butSend.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butSend.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butSend.setCornerRadius(4F);
        this.butSend.Image = ((System.Drawing.Image)(resources.GetObject("butSend.Image")));
        this.butSend.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butSend.Location = new System.Drawing.Point(503, 706);
        this.butSend.Name = "butSend";
        this.butSend.Size = new System.Drawing.Size(86, 24);
        this.butSend.TabIndex = 130;
        this.butSend.Text = "Send";
        this.butSend.Click += new System.EventHandler(this.butSend_Click);
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
        this.butLabel.Location = new System.Drawing.Point(163, 706);
        this.butLabel.Name = "butLabel";
        this.butLabel.Size = new System.Drawing.Size(81, 24);
        this.butLabel.TabIndex = 126;
        this.butLabel.Text = "Label";
        this.butLabel.Click += new System.EventHandler(this.butLabel_Click);
        //
        // textDateService
        //
        this.textDateService.Location = new System.Drawing.Point(111, 91);
        this.textDateService.Name = "textDateService";
        this.textDateService.Size = new System.Drawing.Size(82, 20);
        this.textDateService.TabIndex = 119;
        //
        // textWriteOff
        //
        this.textWriteOff.Location = new System.Drawing.Point(611, 519);
        this.textWriteOff.Name = "textWriteOff";
        this.textWriteOff.ReadOnly = true;
        this.textWriteOff.Size = new System.Drawing.Size(55, 20);
        this.textWriteOff.TabIndex = 113;
        this.textWriteOff.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textInsPayAmt
        //
        this.textInsPayAmt.Location = new System.Drawing.Point(561, 519);
        this.textInsPayAmt.Name = "textInsPayAmt";
        this.textInsPayAmt.ReadOnly = true;
        this.textInsPayAmt.Size = new System.Drawing.Size(51, 20);
        this.textInsPayAmt.TabIndex = 6;
        this.textInsPayAmt.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textDedApplied
        //
        this.textDedApplied.Location = new System.Drawing.Point(461, 519);
        this.textDedApplied.Name = "textDedApplied";
        this.textDedApplied.ReadOnly = true;
        this.textDedApplied.Size = new System.Drawing.Size(51, 20);
        this.textDedApplied.TabIndex = 4;
        this.textDedApplied.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textDateSent
        //
        this.textDateSent.Location = new System.Drawing.Point(111, 112);
        this.textDateSent.Name = "textDateSent";
        this.textDateSent.Size = new System.Drawing.Size(82, 20);
        this.textDateSent.TabIndex = 6;
        //
        // textDateRec
        //
        this.textDateRec.Location = new System.Drawing.Point(111, 133);
        this.textDateRec.Name = "textDateRec";
        this.textDateRec.Size = new System.Drawing.Size(82, 20);
        this.textDateRec.TabIndex = 7;
        //
        // butPreview
        //
        this.butPreview.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPreview.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPreview.setAutosize(true);
        this.butPreview.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPreview.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPreview.setCornerRadius(4F);
        this.butPreview.Image = Resources.getbutPreview();
        this.butPreview.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPreview.Location = new System.Drawing.Point(250, 706);
        this.butPreview.Name = "butPreview";
        this.butPreview.Size = new System.Drawing.Size(92, 24);
        this.butPreview.TabIndex = 115;
        this.butPreview.Text = "P&review";
        this.butPreview.Click += new System.EventHandler(this.butPreview_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(347, 706);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(86, 24);
        this.butPrint.TabIndex = 114;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.ButPrint_Click);
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
        this.butDelete.Location = new System.Drawing.Point(5, 706);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(91, 24);
        this.butDelete.TabIndex = 106;
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
        this.butCancel.Location = new System.Drawing.Point(866, 706);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 15;
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
        this.butOK.Location = new System.Drawing.Point(779, 706);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 14;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridProc
        //
        this.gridProc.setHScrollVisible(false);
        this.gridProc.Location = new System.Drawing.Point(2, 315);
        this.gridProc.Name = "gridProc";
        this.gridProc.setScrollValue(0);
        this.gridProc.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridProc.Size = new System.Drawing.Size(977, 200);
        this.gridProc.TabIndex = 128;
        this.gridProc.setTitle("Procedures");
        this.gridProc.setTranslationName("TableClaimProc");
        this.gridProc.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridProc.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridProc_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormClaimInstEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.AutoScroll = true;
        this.ClientSize = new System.Drawing.Size(984, 737);
        this.ControlBox = false;
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.groupValueCodes);
        this.Controls.Add(this.butHistory);
        this.Controls.Add(this.panelRightEdge);
        this.Controls.Add(this.panelBottomEdge);
        this.Controls.Add(this.butSend);
        this.Controls.Add(this.gridProc);
        this.Controls.Add(this.butLabel);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.textDateService);
        this.Controls.Add(this.textWriteOff);
        this.Controls.Add(this.textInsPayEst);
        this.Controls.Add(this.textInsPayAmt);
        this.Controls.Add(this.textClaimFee);
        this.Controls.Add(this.textDedApplied);
        this.Controls.Add(this.textPreAuth);
        this.Controls.Add(this.textDateSent);
        this.Controls.Add(this.textDateRec);
        this.Controls.Add(this.butPreview);
        this.Controls.Add(this.butPrint);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.listClaimType);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listClaimStatus);
        this.Controls.Add(this.comboProvTreat);
        this.Controls.Add(this.comboProvBill);
        this.Controls.Add(this.label21);
        this.Controls.Add(this.labelPreAuthNum);
        this.Controls.Add(this.labelDateService);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.label6);
        this.Controls.Add(this.label8);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormClaimInstEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Institutional \'Claim\'";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormClaimEdit_Closing);
        this.Load += new System.EventHandler(this.FormClaimInstEdit_Load);
        this.Shown += new System.EventHandler(this.FormClaimEdit_Shown);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.groupValueCodes.ResumeLayout(false);
        this.groupValueCodes.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formClaimEdit_Shown(Object sender, EventArgs e) throws Exception {
    }

    //this needs to be removed.
    private void formClaimInstEdit_Load(Object sender, System.EventArgs e) throws Exception {
        //the control box (x to close window) is not shown in this form because new users are temped to use it as an OK button, causing errors.
        if (System.Windows.Forms.Screen.PrimaryScreen.WorkingArea.Height < this.Height)
        {
            this.Height = System.Windows.Forms.Screen.PrimaryScreen.WorkingArea.Height;
        }
         
    }

    //make this window as tall as possible.
    /*
    			if(IsNew){
    				//butCheckAdd.Enabled=false; //button was removed.
    				groupEnterPayment.Enabled=false;
    			}
    			else if(ClaimCur.ClaimStatus=="S" || ClaimCur.ClaimStatus=="R"){//sent or received
    				if(!Security.IsAuthorized(Permissions.ClaimSentEdit,ClaimCur.DateSent)){
    					butOK.Enabled=false;
    					butDelete.Enabled=false;
    					//butPrint.Enabled=false;//allowed to print, but just won't save changes.
    					notAuthorized=true;
    					groupEnterPayment.Enabled=false;
    					gridPay.Enabled=false;
    					gridProc.Enabled=false;
    					listClaimStatus.Enabled=false;
    					//butCheckAdd.Enabled=false; //button was removed.
    				}
    			}
    			if(ClaimCur.ClaimType=="PreAuth"){
    				labelPreAuthNum.Visible=false;
    				textPreAuth.Visible=false;
    				textDateService.Visible=false;
    				labelDateService.Visible=false;
    				label20.Visible=false;//warning when delete
    				textReasonUnder.Visible=false;
    				label4.Visible=false;//reason under
    				butPayTotal.Visible=false;
    				butPaySupp.Visible=false;
    				butSplit.Visible=false;
          }
    			if(PrefC.GetBool(PrefName.EasyNoClinics)){
    				labelClinic.Visible=false;
    				comboClinic.Visible=false;
    			}
    			listClaimType.Items.Add(Lan.g(this,"Primary"));
    			listClaimType.Items.Add(Lan.g(this,"Secondary"));
    			listClaimType.Items.Add(Lan.g(this,"PreAuth"));
    			listClaimType.Items.Add(Lan.g(this,"Other"));
    			listClaimType.Items.Add(Lan.g(this,"Capitation"));
    			listClaimStatus.Items.Add(Lan.g(this,"Unsent"));
    			listClaimStatus.Items.Add(Lan.g(this,"Hold until Pri received"));
    			listClaimStatus.Items.Add(Lan.g(this,"Waiting to Send"));//2
    			listClaimStatus.Items.Add(Lan.g(this,"Probably Sent"));
    			listClaimStatus.Items.Add(Lan.g(this,"Sent - Verified"));
    			listClaimStatus.Items.Add(Lan.g(this,"Received"));
    			string[] enumRelat=Enum.GetNames(typeof(Relat));
    			for(int i=0;i<enumRelat.Length;i++){;
    				comboPatRelat.Items.Add(Lan.g("enumRelat",enumRelat[i]));
    				comboPatRelat2.Items.Add(Lan.g("enumRelat",enumRelat[i]));
    			}
          ClaimList=Claims.Refresh(PatCur.PatNum); 
          ClaimProcList=ClaimProcs.Refresh(PatCur.PatNum);
    			ProcList=Procedures.Refresh(PatCur.PatNum);
    			SubList=InsSubs.RefreshForFam(FamCur);
    			PlanList=InsPlans.RefreshForSubList(SubList);
    			PatPlanList=PatPlans.Refresh(PatCur.PatNum);
    			if(InsPlans.GetPlan(ClaimCur.PlanNum,PlanList).PlanType=="p"){//ppo
    				butPayTotal.Enabled=false;	
    			}
    			//this section used to be "supplemental"---------------------------------------------------------------------------------
    			textRefNum.Text=ClaimCur.RefNumString;
    			string[] enumPlaceOfService=Enum.GetNames(typeof(PlaceOfService));
    			for(int i=0;i<enumPlaceOfService.Length;i++) {
    				comboPlaceService.Items.Add(Lan.g("enumPlaceOfService",enumPlaceOfService[i]));
    			}
    			comboPlaceService.SelectedIndex=(int)ClaimCur.PlaceService;
    			string[] enumYN=Enum.GetNames(typeof(YN));
    			for(int i=0;i<enumYN.Length;i++) {
    				comboEmployRelated.Items.Add(Lan.g("enumYN",enumYN[i]));
    			}
    			comboEmployRelated.SelectedIndex=(int)ClaimCur.EmployRelated;
    			comboAccident.Items.Add(Lan.g(this,"No"));
    			comboAccident.Items.Add(Lan.g(this,"Auto"));
    			comboAccident.Items.Add(Lan.g(this,"Employment"));
    			comboAccident.Items.Add(Lan.g(this,"Other"));
    			switch(ClaimCur.AccidentRelated) {
    				case "":
    					comboAccident.SelectedIndex=0;
    					break;
    				case "A":
    					comboAccident.SelectedIndex=1;
    					break;
    				case "E":
    					comboAccident.SelectedIndex=2;
    					break;
    				case "O":
    					comboAccident.SelectedIndex=3;
    					break;
    			}
    			//accident date is further down
    			textAccidentST.Text=ClaimCur.AccidentST;
    			textRefProv.Text=Referrals.GetNameLF(ClaimCur.ReferringProv);
    			if(ClaimCur.ReferringProv==0){
    				butReferralEdit.Enabled=false;
    			}
    			else{
    				butReferralEdit.Enabled=true;
    			}
    			FillForm();*/
    /**
    * 
    */
    public void fillForm() throws Exception {
    }

    /*
    			this.Text=Lan.g(this,"Edit Claim")+" - "+PatCur.GetNameLF();
    			if(ClaimValCodes!=null){
    				for(int i=0;i<ClaimValCodes.Count;i++){
    					ClaimValCodeLog vc = (ClaimValCodeLog)ClaimValCodes[i];
    					TextBox code = (TextBox)Controls.Find("textVC" + i + "Code", true)[0];
    					code.Text=vc.ValCode.ToString();
    					TextBox amount = (TextBox)Controls.Find("textVC" + i + "Amount", true)[0];
    					amount.Text=vc.ValAmount.ToString();
    				}
    			}
    			if(CurCondCodeLog!=null && CurCondCodeLog.ClaimNum!=0){
    				textCode0.Text=CurCondCodeLog.Code0.ToString();
    				textCode1.Text=CurCondCodeLog.Code1.ToString();
    				textCode2.Text=CurCondCodeLog.Code2.ToString();
    				textCode3.Text=CurCondCodeLog.Code3.ToString();
    				textCode4.Text=CurCondCodeLog.Code4.ToString();
    				textCode5.Text=CurCondCodeLog.Code5.ToString();
    				textCode6.Text=CurCondCodeLog.Code6.ToString();
    				textCode7.Text=CurCondCodeLog.Code7.ToString();
    				textCode8.Text=CurCondCodeLog.Code8.ToString();
    				textCode9.Text=CurCondCodeLog.Code9.ToString();
    				textCode10.Text=CurCondCodeLog.Code10.ToString();
    			}
    			if(ClaimCur.DateService.Year<1880) {
    				textDateService.Text="";
    			}
    			else {
    				textDateService.Text=ClaimCur.DateService.ToShortDateString();
    			}
    			if(ClaimCur.DateSent.Year<1880) {
    				textDateSent.Text="";
    			}
    			else {
    				textDateSent.Text=ClaimCur.DateSent.ToShortDateString();
    			}
    			if(ClaimCur.DateReceived.Year<1880) {
    				textDateRec.Text="";
    			}
    			else {
    				textDateRec.Text=ClaimCur.DateReceived.ToShortDateString();
    			}
    			switch(ClaimCur.ClaimStatus){
    				case "U"://unsent
    					listClaimStatus.SelectedIndex=0;
    					break;
    				case "H"://hold until pri received
    					listClaimStatus.SelectedIndex=1;
    					break;
    				case "W"://waiting to be sent
    					listClaimStatus.SelectedIndex=2;
    					break;
    				case "P"://probably sent
    					listClaimStatus.SelectedIndex=3;
    					break;
    				case "S"://sent-verified
    					listClaimStatus.SelectedIndex=4;
    					break;
    				case "R"://received
    					listClaimStatus.SelectedIndex=5;
    					break;
    			}
    			comboClinic.Items.Clear();
    			comboClinic.Items.Add(Lan.g(this,"none"));
    			comboClinic.SelectedIndex=0;
    			for(int i=0;i<Clinics.List.Length;i++){
    				comboClinic.Items.Add(Clinics.List[i].Description);
    				if(Clinics.List[i].ClinicNum==ClaimCur.ClinicNum)
    					comboClinic.SelectedIndex=i+1;
    			}
    			switch(ClaimCur.ClaimType){
    				case "P":
    					listClaimType.SelectedIndex=0;
    					break;
    				case "S":
    					listClaimType.SelectedIndex=1;
    					break;
    				case "PreAuth":
    					listClaimType.SelectedIndex=2;
    					break;
    				case "Other":
    					listClaimType.SelectedIndex=3;
    					break;
    				case "Cap":
    					listClaimType.SelectedIndex=4;
    					break;
    			}
    			comboProvBill.Items.Clear();
    			for(int i=0;i<ProviderC.ListShort.Count;i++){
    				comboProvBill.Items.Add(ProviderC.ListShort[i].Abbr);
    				if(ProviderC.ListShort[i].ProvNum==ClaimCur.ProvBill) {
    					comboProvBill.SelectedIndex=i;
    				}
    			}
    			if(comboProvBill.Items.Count>0 && comboProvBill.SelectedIndex==-1)
    				comboProvBill.SelectedIndex=0;
    			comboProvTreat.Items.Clear();
    			for(int i=0;i<ProviderC.ListShort.Count;i++){
    				comboProvTreat.Items.Add(ProviderC.ListShort[i].Abbr);
    				if(ProviderC.ListShort[i].ProvNum==ClaimCur.ProvTreat) {
    					comboProvTreat.SelectedIndex=i;
    				}
    			}
    			if(comboProvTreat.Items.Count>0 && comboProvTreat.SelectedIndex==-1) {
    				comboProvTreat.SelectedIndex=0;
    			}
    			textPreAuth.Text=ClaimCur.PreAuthString;
    			textPlan.Text=InsPlans.GetDescript(ClaimCur.PlanNum,FamCur,PlanList,ClaimCur.InsSubNum,SubList);
    			comboPatRelat.SelectedIndex=(int)ClaimCur.PatRelat;
    			textPlan2.Text=InsPlans.GetDescript(ClaimCur.PlanNum2,FamCur,PlanList,ClaimCur.InsSubNum2,SubList);
    			comboPatRelat2.SelectedIndex=(int)ClaimCur.PatRelat2;
    			if(textPlan2.Text==""){
    				comboPatRelat2.Visible=false;
    				label10.Visible=false;
    			}
    			else{
    				comboPatRelat2.Visible=true;
    				label10.Visible=true;
    			}
    			switch(ClaimCur.IsProsthesis){
    				case "N"://no
    					radioProsthN.Checked=true;
    					break;
    				case "I"://initial
    					radioProsthI.Checked=true;
    					break;
    				case "R"://replacement
    					radioProsthR.Checked=true;
    					break;
    			}
    			if(ClaimCur.PriorDate.Year < 1860)
    				textPriorDate.Text="";
    			else
    				textPriorDate.Text=ClaimCur.PriorDate.ToShortDateString();
    			textReasonUnder.Text=ClaimCur.ReasonUnderPaid;
    			textNote.Text=ClaimCur.ClaimNote;
    			checkIsOrtho.Checked=ClaimCur.IsOrtho;
    			textOrthoRemainM.Text=ClaimCur.OrthoRemainM.ToString();
    			if(ClaimCur.OrthoDate.Year < 1860){
    				textOrthoDate.Text="";
    			}
    			else{
    				textOrthoDate.Text=ClaimCur.OrthoDate.ToShortDateString();
    			}
    			//Canadian------------------------------------------------------------------
    			//(there's also a FillCanadian section for fields that do not collide with USA fields)
    			if(CultureInfo.CurrentCulture.Name.EndsWith("CA")) {//Canadian. en-CA or fr-CA
    				if(ClaimCur.AccidentDate.Year<1880) {
    					textCanadianAccidentDate.Text="";
    				}
    				else {
    					textCanadianAccidentDate.Text=ClaimCur.AccidentDate.ToShortDateString();
    				}
    				checkCanadianIsOrtho.Checked=ClaimCur.IsOrtho;
    			}
    			else {
    				if(ClaimCur.AccidentDate.Year<1880) {
    					textAccidentDate.Text="";
    				}
    				else {
    					textAccidentDate.Text=ClaimCur.AccidentDate.ToShortDateString();
    				}
    				checkIsOrtho.Checked=ClaimCur.IsOrtho;
    			}
    			textCanadaTransRefNum.Text=ClaimCur.CanadaTransRefNum;
    			groupCanadaOrthoPredeterm.Enabled=(ClaimCur.ClaimType=="PreAuth");
    			if(ClaimCur.CanadaEstTreatStartDate.Year<1880) {
    				textDateCanadaEstTreatStartDate.Text="";
    			}
    			else {
    				textDateCanadaEstTreatStartDate.Text=ClaimCur.CanadaEstTreatStartDate.ToShortDateString();
    			}
    			if(ClaimCur.CanadaInitialPayment==0) {
    				textCanadaInitialPayment.Text="";
    			}
    			else {
    				textCanadaInitialPayment.Text=ClaimCur.CanadaInitialPayment.ToString("F");
    			}
    			textCanadaExpectedPayCycle.Text=ClaimCur.CanadaPaymentMode.ToString("#");
    			textCanadaTreatDuration.Text=ClaimCur.CanadaTreatDuration.ToString("##");
    			textCanadaNumPaymentsAnticipated.Text=ClaimCur.CanadaNumAnticipatedPayments.ToString("##");
    			if(ClaimCur.CanadaAnticipatedPayAmount==0) {
    				textCanadaAnticipatedPayAmount.Text="";
    			}
    			else {
    				textCanadaAnticipatedPayAmount.Text=ClaimCur.CanadaAnticipatedPayAmount.ToString("F");
    			}
    			//attachments------------------
    			textRadiographs.Text=ClaimCur.Radiographs.ToString();
    			textAttachImages.Text=ClaimCur.AttachedImages.ToString();
    			textAttachModels.Text=ClaimCur.AttachedModels.ToString();
    			checkAttachEoB.Checked=false;
    			checkAttachNarrative.Checked=false;
    			checkAttachPerio.Checked=false;
    			checkAttachMisc.Checked=false;
    			radioAttachMail.Checked=true;
    			string[] flags=ClaimCur.AttachedFlags.Split(',');
    			for(int i=0;i<flags.Length;i++){
    				switch(flags[i]){
    					case "EoB":
    						checkAttachEoB.Checked=true;
    						break;
    					case "Note":
    						checkAttachNarrative.Checked=true;
    						break;
    					case "Perio":
    						checkAttachPerio.Checked=true;
    						break;
    					case "Misc":
    						checkAttachMisc.Checked=true;
    						break;
    					case "Mail":
    						radioAttachMail.Checked=true;
    						break;
    					case "Elect":
    						radioAttachElect.Checked=true;
    						break;
    				}
    			}
    			textAttachID.Text=ClaimCur.AttachmentID;
    			FillGrids();
    			FillAttachments();*/
    private void listClaimType_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        //not allowed to change claim type
        System.String __dummyScrutVar0 = ClaimCur.ClaimType;
        if (__dummyScrutVar0.equals("P"))
        {
            listClaimType.SelectedIndex = 0;
        }
        else if (__dummyScrutVar0.equals("S"))
        {
            listClaimType.SelectedIndex = 1;
        }
        else if (__dummyScrutVar0.equals("PreAuth"))
        {
            listClaimType.SelectedIndex = 2;
        }
        else if (__dummyScrutVar0.equals("Other"))
        {
            listClaimType.SelectedIndex = 3;
        }
        else if (__dummyScrutVar0.equals("Cap"))
        {
            listClaimType.SelectedIndex = 4;
        }
             
    }

    private void butRecalc_Click(Object sender, System.EventArgs e) throws Exception {
        if (!claimIsValid())
        {
            return ;
        }
         
        List<Benefit> benefitList = Benefits.refresh(PatPlanList,SubList);
        ClaimL.CalculateAndUpdate(ProcList, PlanList, ClaimCur, PatPlanList, benefitList, PatCur.getAge(), SubList);
        ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        fillGrids();
    }

    private void fillGrids() throws Exception {
        //must run claimprocs.refresh separately beforehand
        //also recalculates totals because user might have changed certain items.
        double claimFee = 0;
        double dedApplied = 0;
        double insPayEst = 0;
        double insPayAmt = 0;
        double writeOff = 0;
        double labFees = 0;
        gridProc.beginUpdate();
        gridProc.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableClaimProc","#"),25);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Date"),66);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Prov"),50);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Code"),50);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Tth"),25);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Description"),130);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Fee Billed"), 62, HorizontalAlignment.Right);
        gridProc.getColumns().add(col);
        if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
        {
            //Canadian. en-CA or fr-CA
            col = new ODGridColumn(Lan.g("TableClaimProc","Labs"), 50, HorizontalAlignment.Right);
            gridProc.getColumns().add(col);
        }
         
        col = new ODGridColumn(Lan.g("TableClaimProc","Deduct"), 50, HorizontalAlignment.Right);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Ins Est"), 50, HorizontalAlignment.Right);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Ins Pay"), 50, HorizontalAlignment.Right);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","WriteOff"), 54, HorizontalAlignment.Right);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Status"), 50, HorizontalAlignment.Center);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Pmt"), 40, HorizontalAlignment.Center);
        gridProc.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableClaimProc","Remarks"),145);
        gridProc.getColumns().add(col);
        gridProc.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        Procedure ProcCur;
        ClaimProcsForClaim = ClaimProcs.refreshForClaim(ClaimCur.ClaimNum);
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (ClaimProcsForClaim[i].LineNumber == 0)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ClaimProcsForClaim[i].LineNumber.ToString());
            } 
            row.getCells().Add(ClaimProcsForClaim[i].ProcDate.ToShortDateString());
            row.getCells().add(Providers.getAbbr(((ClaimProc)ClaimProcsForClaim[i]).ProvNum));
            if (ClaimProcsForClaim[i].ProcNum == 0)
            {
                row.getCells().add("");
                //code
                row.getCells().add("");
                //tooth
                if (ClaimProcsForClaim[i].Status == ClaimProcStatus.NotReceived)
                    row.getCells().add(Lan.g(this,"Estimate"));
                else
                    row.getCells().add(Lan.g(this,"Total Payment")); 
            }
            else
            {
                ProcCur = Procedures.GetProcFromList(ProcList, ClaimProcsForClaim[i].ProcNum);
                row.getCells().Add(ClaimProcsForClaim[i].CodeSent);
                row.getCells().add(Tooth.toInternat(ProcCur.ToothNum));
                row.getCells().add(ProcedureCodes.getProcCode(ProcCur.CodeNum).Descript);
            } 
            row.getCells().Add(ClaimProcsForClaim[i].FeeBilled.ToString("F"));
            if (CultureInfo.CurrentCulture.Name.EndsWith("CA"))
            {
                //Canadian. en-CA or fr-CA
                double labFeesForProc = 0;
                List<Procedure> labFeeProcs = Procedures.GetCanadianLabFees(ClaimProcsForClaim[i].ProcNum, ProcList);
                for (int j = 0;j < labFeeProcs.Count;j++)
                {
                    labFeesForProc += (double)labFeeProcs[j].ProcFee;
                }
                row.getCells().Add(labFeesForProc.ToString("F"));
                labFees += labFeesForProc;
            }
             
            row.getCells().Add(ClaimProcsForClaim[i].DedApplied.ToString("F"));
            row.getCells().Add(ClaimProcsForClaim[i].InsPayEst.ToString("F"));
            row.getCells().Add(ClaimProcsForClaim[i].InsPayAmt.ToString("F"));
            row.getCells().Add(ClaimProcsForClaim[i].WriteOff.ToString("F"));
            Status __dummyScrutVar1 = ClaimProcsForClaim[i].Status;
            if (__dummyScrutVar1.equals(ClaimProcStatus.Received))
            {
                row.getCells().add("Recd");
            }
            else if (__dummyScrutVar1.equals(ClaimProcStatus.NotReceived))
            {
                row.getCells().add("");
            }
            else //adjustment would never show here
            if (__dummyScrutVar1.equals(ClaimProcStatus.Preauth))
            {
                row.getCells().add("PreA");
            }
            else if (__dummyScrutVar1.equals(ClaimProcStatus.Supplemental))
            {
                row.getCells().add("Supp");
            }
            else if (__dummyScrutVar1.equals(ClaimProcStatus.CapClaim))
            {
                row.getCells().add("Cap");
            }
            else if (__dummyScrutVar1.equals(ClaimProcStatus.Estimate))
            {
                row.getCells().add("");
                MessageBox.Show("error. Estimate loaded.");
            }
            else if (__dummyScrutVar1.equals(ClaimProcStatus.CapEstimate))
            {
                row.getCells().add("");
                MessageBox.Show("error. CapEstimate loaded.");
            }
            else if (__dummyScrutVar1.equals(ClaimProcStatus.CapComplete))
            {
                row.getCells().add("");
                MessageBox.Show("error. CapComplete loaded.");
            }
            else
            {
                //Estimate would never show here
                //Cap would never show here
                row.getCells().add("");
            }        
            if (ClaimProcsForClaim[i].ClaimPaymentNum > 0)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(ClaimProcsForClaim[i].Remarks);
            claimFee += ClaimProcsForClaim[i].FeeBilled;
            dedApplied += ClaimProcsForClaim[i].DedApplied;
            insPayEst += ClaimProcsForClaim[i].InsPayEst;
            insPayAmt += ClaimProcsForClaim[i].InsPayAmt;
            //if(ClaimProcsForClaim[i].Status==ClaimProcStatus.Received
            //	|| ClaimProcsForClaim[i].Status==ClaimProcStatus.Supplemental) {
            writeOff += ClaimProcsForClaim[i].WriteOff;
            //}
            gridProc.getRows().add(row);
        }
        gridProc.endUpdate();
        if (StringSupport.equals(ClaimCur.ClaimType, "Cap"))
        {
            //zero out ins info if Cap.  This keeps it from affecting the balance.  It could be slightly improved later if there is enough demand to show the inspayamt in the Account module.
            insPayEst = 0;
            insPayAmt = 0;
        }
         
        ClaimCur.ClaimFee = claimFee;
        ClaimCur.DedApplied = dedApplied;
        ClaimCur.InsPayEst = insPayEst;
        ClaimCur.InsPayAmt = insPayAmt;
        ClaimCur.WriteOff = writeOff;
        textClaimFee.Text = ClaimCur.ClaimFee.ToString("F");
        textDedApplied.Text = ClaimCur.DedApplied.ToString("F");
        textInsPayEst.Text = ClaimCur.InsPayEst.ToString("F");
        textInsPayAmt.Text = ClaimCur.InsPayAmt.ToString("F");
        textWriteOff.Text = writeOff.ToString("F");
    }

    /*
    			//payments
    			gridPay.BeginUpdate();
    			gridPay.Columns.Clear();
    			col=new ODGridColumn(Lan.g("TableClaimPay","Date"),70);
    			gridPay.Columns.Add(col);
    			col=new ODGridColumn(Lan.g("TableClaimPay","Amount"),80,HorizontalAlignment.Right);
    			gridPay.Columns.Add(col);
    			col=new ODGridColumn(Lan.g("TableClaimPay","Check Num"),100);
    			gridPay.Columns.Add(col);
    			col=new ODGridColumn(Lan.g("TableClaimPay","Bank/Branch"),100);
    			gridPay.Columns.Add(col);
    			col=new ODGridColumn(Lan.g("TableClaimPay","Note"),180);
    			gridPay.Columns.Add(col);
    			gridPay.Rows.Clear();
    			tablePayments=ClaimPayments.GetForClaim(ClaimCur.ClaimNum);
    			for(int i=0;i<tablePayments.Rows.Count;i++){
    				row=new ODGridRow();
    				row.Cells.Add(tablePayments.Rows[i]["checkDate"].ToString());
    				row.Cells.Add(tablePayments.Rows[i]["amount"].ToString());
    				row.Cells.Add(tablePayments.Rows[i]["CheckNum"].ToString());
    				row.Cells.Add(tablePayments.Rows[i]["BankBranch"].ToString());
    				row.Cells.Add(tablePayments.Rows[i]["Note"].ToString());
    				gridPay.Rows.Add(row);
    			}
    			gridPay.EndUpdate();*/
    private void gridProc_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!doubleClickWarningAlreadyDisplayed)
        {
            doubleClickWarningAlreadyDisplayed = true;
            if (!MsgBox.show(this,true,"If you are trying to enter payment information, please use the payments buttons at the upper right.\r\nThen, don't forget to finish by creating the check using the button below this section.\r\nYou should probably click cancel unless you are just editing estimates.\r\nContinue anyway?"))
            {
                return ;
            }
             
        }
         
        List<ClaimProcHist> histList = null;
        List<ClaimProcHist> loopList = null;
        RefSupport<List<ClaimProcHist>> refVar___0 = new RefSupport<List<ClaimProcHist>>(loopList);
        FormClaimProc FormCP = new FormClaimProc(ClaimProcsForClaim[e.getRow()], null, FamCur, PatCur, PlanList, histList, refVar___0, PatPlanList, true, SubList);
        loopList = refVar___0.getValue();
        FormCP.IsInClaim = true;
        FormCP.ShowDialog();
        if (FormCP.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        fillGrids();
    }

    private void butOtherCovChange_Click(Object sender, System.EventArgs e) throws Exception {
        FormInsPlanSelect FormIPS = new FormInsPlanSelect(PatCur.PatNum);
        FormIPS.ShowDialog();
        if (FormIPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ClaimCur.PlanNum2 = FormIPS.SelectedPlan.PlanNum;
        ClaimCur.InsSubNum2 = FormIPS.SelectedSub.InsSubNum;
        textPlan2.Text = InsPlans.getDescript(ClaimCur.PlanNum2,FamCur,PlanList,ClaimCur.InsSubNum2,SubList);
        if (StringSupport.equals(textPlan2.Text, ""))
        {
            comboPatRelat2.Visible = false;
            label10.Visible = false;
        }
        else
        {
            comboPatRelat2.Visible = true;
            label10.Visible = true;
        } 
    }

    private void butOtherNone_Click(Object sender, System.EventArgs e) throws Exception {
        ClaimCur.PlanNum2 = 0;
        ClaimCur.InsSubNum2 = 0;
        ClaimCur.PatRelat2 = Relat.Self;
        textPlan2.Text = "";
        comboPatRelat2.Visible = false;
        label10.Visible = false;
    }

    private void butLabel_Click(Object sender, System.EventArgs e) throws Exception {
        //LabelSingle label=new LabelSingle();
        PrintDocument pd = new PrintDocument();
        //only used to pass printerName
        if (!PrinterL.SetPrinter(pd, PrintSituation.LabelSingle))
        {
            return ;
        }
         
        //ask if print secondary?
        InsPlan planCur = InsPlans.getPlan(ClaimCur.PlanNum,PlanList);
        Carrier carrierCur = Carriers.getCarrier(planCur.CarrierNum);
        LabelSingle.printCarrier(carrierCur.CarrierNum);
    }

    //pd.PrinterSettings.PrinterName);
    private void butPreview_Click(Object sender, System.EventArgs e) throws Exception {
        if (!claimIsValid())
        {
            return ;
        }
         
        updateClaim();
        FormClaimPrint FormCP = new FormClaimPrint();
        FormCP.ThisPatNum = ClaimCur.PatNum;
        FormCP.ThisClaimNum = ClaimCur.ClaimNum;
        FormCP.PrintImmediately = false;
        FormCP.ShowDialog();
        if (FormCP.DialogResult == DialogResult.OK)
        {
            //status will have changed to sent.
            ClaimCur = Claims.getClaim(ClaimCur.ClaimNum);
        }
         
        ClaimList = Claims.refresh(PatCur.PatNum);
        ClaimProcList = ClaimProcs.refresh(PatCur.PatNum);
        fillForm();
    }

    //no need to FillCanadian.  Nothing has changed.
    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (!claimIsValid())
        {
            return ;
        }
         
        updateClaim();
        PrintDocument pd = new PrintDocument();
        if (!PrinterL.SetPrinter(pd, PrintSituation.Claim))
        {
            return ;
        }
         
        FormClaimPrint FormCP = new FormClaimPrint();
        FormCP.ThisPatNum = ClaimCur.PatNum;
        FormCP.ThisClaimNum = ClaimCur.ClaimNum;
        if (!FormCP.PrintImmediate(pd.PrinterSettings.PrinterName, pd.PrinterSettings.Copies))
        {
            return ;
        }
         
        if (!notAuthorized)
        {
            //if already sent, we want to block users from changing sent date without permission.
            //also changes claimstatus to sent, and date:
            Etranss.SetClaimSentOrPrinted(ClaimCur.ClaimNum, ClaimCur.PatNum, 0, EtransType.ClaimPrinted, 0);
        }
         
        //ClaimCur.ClaimStatus="S";
        //ClaimCur.DateSent=DateTime.Today;
        //Claims.Update(ClaimCur);
        DialogResult = DialogResult.OK;
    }

    private void butSend_Click(Object sender, EventArgs e) throws Exception {
        if (!claimIsValid())
        {
            return ;
        }
         
        updateClaim();
        ClaimSendQueueItem[] listQueue = Claims.GetQueueList(ClaimCur.ClaimNum, ClaimCur.ClinicNum);
        if (listQueue[0].NoSendElect)
        {
            MsgBox.show(this,"This carrier is marked to not receive e-claims.");
            return ;
        }
         
        //Later: we need to let user send anyway, using all 0's for electronic id.
        String warnings = new String();
        RefSupport<String> refVar___1 = new RefSupport<String>();
        String missingData = Eclaims.GetMissingData(listQueue[0], refVar___1);
        warnings = refVar___1.getValue();
        if (!StringSupport.equals(missingData, ""))
        {
            MessageBox.Show(Lan.g(this,"Cannot send claim until missing data is fixed:") + "\r\n" + missingData);
            return ;
        }
         
        Cursor = Cursors.WaitCursor;
        Clearinghouse clearhouse = ClearinghouseL.GetClearinghouse(listQueue[0].ClearinghouseNum);
        if (clearhouse.Eformat == ElectronicClaimFormat.Canadian)
        {
            try
            {
                Canadian.SendClaim(listQueue[0], true);
            }
            catch (Exception ex)
            {
                //ignore the etransNum result
                Cursor = Cursors.Default;
                MessageBox.Show(ex.Message);
                return ;
            }
        
        }
        else
        {
            List<ClaimSendQueueItem> queueItems = new List<ClaimSendQueueItem>();
            queueItems.Add(listQueue[0]);
            Eclaims.SendBatches(queueItems);
        } 
        //this also calls SetClaimSentOrPrinted which creates the etrans entry.
        Cursor = Cursors.Default;
        DialogResult = DialogResult.OK;
    }

    private void butHistory_Click(Object sender, EventArgs e) throws Exception {
        List<Etrans> etransList = Etranss.getHistoryOneClaim(ClaimCur.ClaimNum);
        if (etransList.Count == 0)
        {
            MsgBox.show(this,"No history found of sent e-claim.");
            return ;
        }
         
        FormEtransEdit FormE = new FormEtransEdit();
        FormE.EtransCur = etransList[0];
        FormE.ShowDialog();
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //jump straight to Closing, where the claimprocs will be changed
        if (!claimIsValid())
        {
            return ;
        }
         
        updateClaim();
        boolean paymentIsAttached = false;
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            if (ClaimProcsForClaim[i].ClaimPaymentNum > 0)
            {
                paymentIsAttached = true;
            }
             
        }
        if (paymentIsAttached)
        {
            MessageBox.Show(Lan.g(this,"You cannot delete this claim while any insurance checks are attached.  You will have to detach all insurance checks first."));
            return ;
        }
         
        if (StringSupport.equals(ClaimCur.ClaimStatus, "R"))
        {
            MessageBox.Show(Lan.g(this,"You cannot delete this claim while status is Received.  You will have to change the status first."));
            return ;
        }
         
        if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
        {
            if (MessageBox.Show(Lan.g(this,"Delete PreAuthorization?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        }
        else
        {
            if (MessageBox.Show(Lan.g(this,"Delete Claim?"), "", MessageBoxButtons.OKCancel) != DialogResult.OK)
            {
                return ;
            }
             
        } 
        Procedure proc;
        //all preauth claimprocs are just duplicates
        if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth") || StringSupport.equals(ClaimCur.ClaimType, "Cap"))
        {
            for (int i = 0;i < ClaimProcsForClaim.Count;i++)
            {
                //all cap claimprocs are just duplicates
                //ClaimProcs.Cur=ClaimProcs.ForClaim[i];
                ClaimProcs.Delete(ClaimProcsForClaim[i]);
            }
        }
        else
        {
            //all other claim types use original estimate claimproc.
            List<Benefit> benList = Benefits.refresh(PatPlanList,SubList);
            InsPlan plan = InsPlans.getPlan(ClaimCur.PlanNum,PlanList);
            for (int i = 0;i < ClaimProcsForClaim.Count;i++)
            {
                //ClaimProcs.Cur=ClaimProcs.ForClaim[i];
                //supplementals are duplicate
                if (ClaimProcsForClaim[i].Status == ClaimProcStatus.Supplemental || ClaimProcsForClaim[i].ProcNum == 0)
                {
                    //total payments get deleted
                    ClaimProcs.Delete(ClaimProcsForClaim[i]);
                    continue;
                }
                 
                //so only changed back to estimate if attached to a proc
                ClaimProcsForClaim[i].Status = ClaimProcStatus.Estimate;
                ClaimProcsForClaim[i].ClaimNum = 0;
                proc = Procedures.GetProcFromList(ProcList, ClaimProcsForClaim[i].ProcNum);
                //We're not going to bother to also get paidOtherInsBaseEst:
                double paidOtherInsTotal = ClaimProcs.GetPaidOtherInsTotal(ClaimProcsForClaim[i], PatPlanList);
                double writeOffOtherIns = ClaimProcs.GetWriteOffOtherIns(ClaimProcsForClaim[i], PatPlanList);
                if (StringSupport.equals(ClaimCur.ClaimType, "P") && PatPlanList.Count > 0)
                {
                    ClaimProcs.ComputeBaseEst(ClaimProcsForClaim[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, plan, PatPlanList[0].PatPlanNum, benList, null, null, PatPlanList, 0, 0, PatCur.getAge(), 0);
                }
                else if (StringSupport.equals(ClaimCur.ClaimType, "S") && PatPlanList.Count > 1)
                {
                    ClaimProcs.ComputeBaseEst(ClaimProcsForClaim[i], proc.ProcFee, proc.ToothNum, proc.CodeNum, plan, PatPlanList[1].PatPlanNum, benList, null, null, PatPlanList, paidOtherInsTotal, paidOtherInsTotal, PatCur.getAge(), writeOffOtherIns);
                }
                  
                ClaimProcsForClaim[i].InsPayEst = 0;
                ClaimProcs.Update(ClaimProcsForClaim[i]);
            }
        } 
        Claims.delete(ClaimCur);
        SecurityLogs.makeLogEntry(Permissions.ClaimSentEdit,ClaimCur.PatNum,Lan.g(this,"Delete Claim") + ", " + PatCur.getNameLF() + "" + Lan.g(this,"Date of service: ") + ClaimCur.DateService.ToShortDateString());
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!claimIsValid())
        {
            return ;
        }
         
        //if status is received, all claimprocs must also be received.
        if (listClaimStatus.SelectedIndex == 5)
        {
            boolean allReceived = true;
            for (int i = 0;i < ClaimProcsForClaim.Count;i++)
            {
                if (((ClaimProc)ClaimProcsForClaim[i]).Status == ClaimProcStatus.NotReceived)
                {
                    allReceived = false;
                }
                 
            }
            if (!allReceived)
            {
                if (!MsgBox.show(this,true,"All items will be marked received.  Continue?"))
                {
                    return ;
                }
                 
                for (int i = 0;i < ClaimProcsForClaim.Count;i++)
                {
                    if (ClaimProcsForClaim[i].Status == ClaimProcStatus.NotReceived)
                    {
                        //ClaimProcs.Cur=(ClaimProc)ClaimProcs.ForClaim[i];
                        ClaimProcsForClaim[i].Status = ClaimProcStatus.Received;
                        ClaimProcsForClaim[i].DateEntry = DateTime.Now;
                        //date it was set rec'd
                        ClaimProcs.Update(ClaimProcsForClaim[i]);
                    }
                     
                }
            }
             
        }
        else
        {
            //claim is any status except received
            boolean anyReceived = false;
            for (int i = 0;i < ClaimProcsForClaim.Count;i++)
            {
                if (((ClaimProc)ClaimProcsForClaim[i]).Status == ClaimProcStatus.Received)
                {
                    anyReceived = true;
                }
                 
            }
            if (anyReceived)
            {
                //Too dangerous to automatically set items not received because I would have to check for attachments to checks, etc.
                //Also too annoying to block user.
                //So just warn user.
                if (!MsgBox.show(this,true,"Some of the items are marked received.  This is not a good idea since it will cause them to show in the Account as a 'payment'.  Continue anyway?"))
                {
                    return ;
                }
                 
            }
             
        } 
        //if status is received and there is no received date
        if (listClaimStatus.SelectedIndex == 5 && StringSupport.equals(textDateRec.Text, ""))
        {
            textDateRec.Text = DateTime.Today.ToShortDateString();
        }
         
        updateClaim();
        if (listClaimStatus.SelectedIndex == 2)
        {
            //waiting to send
            ClaimSendQueueItem[] listQueue = Claims.GetQueueList(ClaimCur.ClaimNum, ClaimCur.ClinicNum);
            if (listQueue[0].NoSendElect)
            {
                DialogResult = DialogResult.OK;
                return ;
            }
             
            String warnings = new String();
            RefSupport<String> refVar___2 = new RefSupport<String>();
            String missingData = Eclaims.GetMissingData(listQueue[0], refVar___2);
            warnings = refVar___2.getValue();
            if (!StringSupport.equals(missingData, ""))
            {
                MessageBox.Show(Lan.g(this,"Cannot send claim until missing data is fixed:") + "\r\n" + missingData);
                DialogResult = DialogResult.OK;
                return ;
            }
             
        }
         
        //if(MsgBox.Show(this,true,"Send electronic claim immediately?")){
        //	List<ClaimSendQueueItem> queueItems=new List<ClaimSendQueueItem>();
        //	queueItems.Add(listQueue[0]);
        //	Eclaims.Eclaims.SendBatches(queueItems);//this also calls SetClaimSentOrPrinted which creates the etrans entry.
        //}
        if (listClaimStatus.SelectedIndex == 5)
        {
            //Received
            if (PrefC.getBool(PrefName.ProviderIncomeTransferShows))
            {
                Payment PaymentCur = new Payment();
                PaymentCur.PayDate = DateTime.Today;
                PaymentCur.PatNum = PatCur.PatNum;
                PaymentCur.ClinicNum = PatCur.ClinicNum;
                PaymentCur.PayType = 0;
                //txfr
                PaymentCur.DateEntry = DateTime.Today;
                //So that it will show properly in the new window.
                Payments.insert(PaymentCur);
                FormPayment Formp = new FormPayment(PatCur,FamCur,PaymentCur);
                Formp.IsNew = true;
                Formp.ShowDialog();
            }
             
        }
         
        DialogResult = DialogResult.OK;
    }

    /**
    * Also handles Canadian warnings.
    */
    private boolean claimIsValid() throws Exception {
        if (!StringSupport.equals(textDateService.errorProvider1.GetError(textDateSent), "") || !StringSupport.equals(textDateSent.errorProvider1.GetError(textDateSent), "") || !StringSupport.equals(textDateRec.errorProvider1.GetError(textDateRec), "") || !StringSupport.equals(textDedApplied.errorProvider1.GetError(textDedApplied), "") || !StringSupport.equals(textInsPayAmt.errorProvider1.GetError(textInsPayAmt), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (StringSupport.equals(textDateService.Text, "") && !StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
        {
            MsgBox.show(this,"Please enter a date of service");
            return false;
        }
         
        //received
        //sent
        if ((listClaimStatus.SelectedIndex == 5 || listClaimStatus.SelectedIndex == 4) && StringSupport.equals(textDateSent.Text, ""))
        {
            MsgBox.show(this,"Please enter date sent.");
            return false;
        }
         
        if (StringSupport.equals(ClaimCur.ClaimType, "PreAuth"))
        {
            boolean preauthChanged = false;
            for (int i = 0;i < ClaimProcsForClaim.Count;i++)
            {
                if (ClaimProcsForClaim[i].Status != ClaimProcStatus.Preauth)
                {
                    ClaimProcsForClaim[i].Status = ClaimProcStatus.Preauth;
                    ClaimProcs.Update(ClaimProcsForClaim[i]);
                    preauthChanged = true;
                }
                 
            }
            if (preauthChanged)
            {
                //don't bother to refresh
                fillGrids();
                MsgBox.show(this,"Status of procedures was changed back to preauth to match status of claim.");
                return false;
            }
             
        }
         
        return true;
    }

    /**
    * Updates this claim to the database.
    */
    private void updateClaim() throws Exception {
        if (notAuthorized)
        {
            return ;
        }
         
        //patnum
        ClaimCur.DateService = PIn.Date(textDateService.Text);
        if (StringSupport.equals(textDateSent.Text, ""))
        {
            ClaimCur.DateSent = DateTime.MinValue;
        }
        else
        {
            ClaimCur.DateSent = PIn.Date(textDateSent.Text);
        } 
        boolean wasSentOrReceived = false;
        SelectedIndex __dummyScrutVar2 = listClaimStatus.SelectedIndex;
        if (__dummyScrutVar2.equals(0))
        {
            ClaimCur.ClaimStatus = "U";
        }
        else if (__dummyScrutVar2.equals(1))
        {
            ClaimCur.ClaimStatus = "H";
        }
        else if (__dummyScrutVar2.equals(2))
        {
            ClaimCur.ClaimStatus = "W";
        }
        else if (__dummyScrutVar2.equals(3))
        {
            ClaimCur.ClaimStatus = "P";
        }
        else if (__dummyScrutVar2.equals(4))
        {
            ClaimCur.ClaimStatus = "S";
            wasSentOrReceived = true;
        }
        else if (__dummyScrutVar2.equals(5))
        {
            ClaimCur.ClaimStatus = "R";
            wasSentOrReceived = true;
        }
              
        if (StringSupport.equals(textDateRec.Text, ""))
        {
            ClaimCur.DateReceived = DateTime.MinValue;
        }
        else
        {
            ClaimCur.DateReceived = PIn.Date(textDateRec.Text);
        } 
        //planNum
        //patRelats will always be selected
        ClaimCur.PatRelat = (Relat)comboPatRelat.SelectedIndex;
        ClaimCur.PatRelat2 = (Relat)comboPatRelat2.SelectedIndex;
        if (comboProvTreat.SelectedIndex != -1)
        {
            ClaimCur.ProvTreat = ProviderC.getListShort()[comboProvTreat.SelectedIndex].ProvNum;
        }
         
        ClaimCur.PreAuthString = textPreAuth.Text;
        if (comboProvBill.SelectedIndex != -1)
        {
            ClaimCur.ProvBill = ProviderC.getListShort()[comboProvBill.SelectedIndex].ProvNum;
        }
         
        if (comboClinic.SelectedIndex == 0)
        {
            //none
            ClaimCur.ClinicNum = 0;
        }
        else
        {
            ClaimCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
        } 
        Claims.update(ClaimCur);
        if (ClaimValCodes != null)
        {
            for (int i = 0;i < ClaimValCodes.Count;i++)
            {
                //update existing Value Code pairs
                ClaimValCodeLog vc = (ClaimValCodeLog)ClaimValCodes[i];
                TextBox code = (TextBox)Controls.Find("textVC" + i + "Code", true)[0];
                vc.ValCode = code.Text.ToString();
                TextBox amount = (TextBox)Controls.Find("textVC" + i + "Amount", true)[0];
                String amt = amount.Text;
                if (StringSupport.equals(amt, ""))
                {
                    amt = "0";
                }
                 
                vc.ValAmount = Double.Parse(amt);
            }
            for (int i = (ClaimValCodes.Count);i < 12;i++)
            {
                //add new Value Code pairs
                ClaimValCodeLog vc = new ClaimValCodeLog();
                TextBox code = (TextBox)Controls.Find("textVC" + i + "Code", true)[0];
                vc.ValCode = code.Text.ToString();
                TextBox amount = (TextBox)Controls.Find("textVC" + i + "Amount", true)[0];
                String amt = amount.Text;
                if (StringSupport.equals(amt, ""))
                {
                    amt = "0";
                }
                 
                vc.ValAmount = Double.Parse(amt);
                vc.ClaimNum = ClaimCur.ClaimNum;
                vc.ClaimValCodeLogNum = 0;
                if (!StringSupport.equals(vc.ValCode, "") || vc.ValAmount != 0)
                {
                    ClaimValCodes.Add(vc);
                }
                 
            }
            ClaimValCodeLogs.updateList(ClaimValCodes);
        }
         
        if (wasSentOrReceived)
        {
            SecurityLogs.makeLogEntry(Permissions.ClaimSentEdit,ClaimCur.PatNum,PatCur.getNameLF() + ", " + Lan.g(this,"Date of service: ") + ClaimCur.DateService.ToShortDateString());
        }
         
    }

    //cancel does not cancel in some circumstances because cur gets updated in some areas.
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formClaimEdit_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            if (ClaimCur.InsPayAmt > 0)
            {
                MsgBox.show(this,"Not allowed to cancel because an insurance payment was entered.  Either click OK, or zero out the insurance payments.");
                e.Cancel = true;
                return ;
            }
             
            for (int i = 0;i < ClaimProcsForClaim.Count;i++)
            {
                if (ClaimProcsForClaim[i].Status == ClaimProcStatus.CapClaim || ClaimProcsForClaim[i].Status == ClaimProcStatus.Preauth)
                {
                    ClaimProcs.Delete(ClaimProcsForClaim[i]);
                }
                else if (ClaimProcsForClaim[i].Status == ClaimProcStatus.NotReceived)
                {
                    ClaimProcsForClaim[i].Status = ClaimProcStatus.Estimate;
                    ClaimProcsForClaim[i].ClaimNum = 0;
                    ClaimProcsForClaim[i].InsPayEst = 0;
                    ClaimProcs.Update(ClaimProcsForClaim[i]);
                }
                  
            }
            Claims.delete(ClaimCur);
        }
         
    }

}


//does not do any validation.  Also deletes the claimcanadian.