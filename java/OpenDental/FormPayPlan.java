//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:28 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormInsPlanSelect;
import OpenDental.FormPatientSelect;
import OpenDental.FormPayment;
import OpenDental.FormPaymentPlanOptions;
import OpenDental.FormPayPlan;
import OpenDental.FormPayPlanChargeEdit;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDental.ReportingOld2.ReportObject;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDental.ReportingOld2.Section;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.AccountModules;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Payment;
import OpenDentBusiness.Payments;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PayPlanCharges;
import OpenDentBusiness.PayPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.YN;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPayPlan  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.ValidDate textDate;
    private OpenDental.ValidDouble textAmount;
    private OpenDental.ValidDate textDateFirstPay;
    private OpenDental.ValidDouble textAPR;
    private OpenDental.ValidNum textTerm;
    private OpenDental.UI.Button butPrint;
    private System.Windows.Forms.TextBox textGuarantor = new System.Windows.Forms.TextBox();
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private OpenDental.UI.Button butGoToGuar;
    private OpenDental.UI.Button butGoToPat;
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private OpenDental.ValidDouble textDownPayment;
    private System.Drawing.Printing.PrintDocument pd2 = new System.Drawing.Printing.PrintDocument();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    /**
    * Go to the specified patnum.  Upon dialog close, if this number is not 0, then patients.Cur will be changed to this new patnum, and Account refreshed to the new patient.
    */
    public long GotoPatNum = new long();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    //private double amtPaid;
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTotalCost = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butDelete;
    private OpenDental.ODtextBox textNote;
    private Patient PatCur;
    private System.Windows.Forms.TextBox textAccumulatedDue = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butCreateSched;
    private OpenDental.ValidDouble textPeriodPayment;
    private PayPlan PayPlanCur;
    private OpenDental.UI.Button butChangeGuar;
    private System.Windows.Forms.TextBox textInsPlan = new System.Windows.Forms.TextBox();
    private OpenDental.UI.Button butChangePlan;
    private System.Windows.Forms.CheckBox checkIns = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.Label labelGuarantor = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label labelInsPlan = new System.Windows.Forms.Label();
    /**
    * Only used for new payment plan.  Pass in the starting amount.
    */
    public double TotalAmt = new double();
    /**
    * Family for the patient of this payplan.  Used to display insurance info.
    */
    private Family FamCur;
    /**
    * Used to display insurance info.
    */
    private List<InsPlan> InsPlanList = new List<InsPlan>();
    private OpenDental.UI.ODGrid gridCharges;
    private OpenDental.UI.Button butClear;
    private OpenDental.UI.Button butAdd;
    private System.Windows.Forms.TextBox textAmtPaid = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textPrincPaid = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    //private List<PayPlanCharge> ChargeList;
    private double AmtPaid = new double();
    private DataTable table = new DataTable();
    private double TotPrinc = new double();
    private double TotInt = new double();
    private Label label1 = new Label();
    private OpenDental.ValidDouble textCompletedAmt;
    private Label label3 = new Label();
    private OpenDental.UI.Button butPickProv;
    private ComboBox comboProv = new ComboBox();
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private Label label16 = new Label();
    private GroupBox groupBox1 = new GroupBox();
    private double TotPrincInt = new double();
    private OpenDental.UI.Button butMoreOptions;
    private List<InsSub> SubList = new List<InsSub>();
    /**
    * This form is reused as long as this parent form remains open.
    */
    private FormPaymentPlanOptions FormPayPlanOpts;
    /**
    * The supplied payment plan should already have been saved in the database.
    */
    public FormPayPlan(Patient patCur, PayPlan payPlanCur) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        PatCur = patCur.copy();
        PayPlanCur = payPlanCur.copy();
        FamCur = Patients.getFamily(PatCur.PatNum);
        SubList = InsSubs.refreshForFam(FamCur);
        InsPlanList = InsPlans.refreshForSubList(SubList);
        FormPayPlanOpts = new FormPaymentPlanOptions();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPayPlan.class);
        this.labelGuarantor = new System.Windows.Forms.Label();
        this.textGuarantor = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label7 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butMoreOptions = new OpenDental.UI.Button();
        this.textAPR = new OpenDental.ValidDouble();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.textPeriodPayment = new OpenDental.ValidDouble();
        this.textTerm = new OpenDental.ValidNum();
        this.textDownPayment = new OpenDental.ValidDouble();
        this.label11 = new System.Windows.Forms.Label();
        this.textDateFirstPay = new OpenDental.ValidDate();
        this.textAmount = new OpenDental.ValidDouble();
        this.butCreateSched = new OpenDental.UI.Button();
        this.textTotalCost = new System.Windows.Forms.TextBox();
        this.label15 = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.label9 = new System.Windows.Forms.Label();
        this.pd2 = new System.Drawing.Printing.PrintDocument();
        this.label12 = new System.Windows.Forms.Label();
        this.textAmtPaid = new System.Windows.Forms.TextBox();
        this.textAccumulatedDue = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.textInsPlan = new System.Windows.Forms.TextBox();
        this.labelInsPlan = new System.Windows.Forms.Label();
        this.checkIns = new System.Windows.Forms.CheckBox();
        this.textPrincPaid = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.comboProv = new System.Windows.Forms.ComboBox();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.label16 = new System.Windows.Forms.Label();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butPickProv = new OpenDental.UI.Button();
        this.textCompletedAmt = new OpenDental.ValidDouble();
        this.butAdd = new OpenDental.UI.Button();
        this.butClear = new OpenDental.UI.Button();
        this.butChangePlan = new OpenDental.UI.Button();
        this.gridCharges = new OpenDental.UI.ODGrid();
        this.textNote = new OpenDental.ODtextBox();
        this.butDelete = new OpenDental.UI.Button();
        this.butGoToPat = new OpenDental.UI.Button();
        this.butGoToGuar = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.butChangeGuar = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butPrint = new OpenDental.UI.Button();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // labelGuarantor
        //
        this.labelGuarantor.Location = new System.Drawing.Point(28, 32);
        this.labelGuarantor.Name = "labelGuarantor";
        this.labelGuarantor.Size = new System.Drawing.Size(126, 17);
        this.labelGuarantor.TabIndex = 2;
        this.labelGuarantor.Text = "Guarantor";
        this.labelGuarantor.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textGuarantor
        //
        this.textGuarantor.Location = new System.Drawing.Point(156, 32);
        this.textGuarantor.Name = "textGuarantor";
        this.textGuarantor.ReadOnly = true;
        this.textGuarantor.Size = new System.Drawing.Size(199, 20);
        this.textGuarantor.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(21, 190);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(133, 17);
        this.label2.TabIndex = 5;
        this.label2.Text = "Date of Agreement";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(5, 14);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(134, 17);
        this.label4.TabIndex = 10;
        this.label4.Text = "Total Amount";
        this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(5, 36);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(135, 17);
        this.label5.TabIndex = 12;
        this.label5.Text = "Date of First Payment";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(3, 80);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(138, 17);
        this.label6.TabIndex = 14;
        this.label6.Text = "APR (for example 0 or 18)";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(8, 40);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(122, 17);
        this.label7.TabIndex = 16;
        this.label7.Text = "Payment Amt";
        this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(7, 18);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(124, 17);
        this.label8.TabIndex = 19;
        this.label8.Text = "Number of Payments";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butMoreOptions);
        this.groupBox2.Controls.Add(this.textAPR);
        this.groupBox2.Controls.Add(this.groupBox3);
        this.groupBox2.Controls.Add(this.textDownPayment);
        this.groupBox2.Controls.Add(this.label11);
        this.groupBox2.Controls.Add(this.label6);
        this.groupBox2.Controls.Add(this.textDateFirstPay);
        this.groupBox2.Controls.Add(this.label5);
        this.groupBox2.Controls.Add(this.label4);
        this.groupBox2.Controls.Add(this.textAmount);
        this.groupBox2.Controls.Add(this.butCreateSched);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(14, 210);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(415, 170);
        this.groupBox2.TabIndex = 22;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Terms";
        //
        // butMoreOptions
        //
        this.butMoreOptions.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMoreOptions.setAutosize(true);
        this.butMoreOptions.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMoreOptions.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMoreOptions.setCornerRadius(4F);
        this.butMoreOptions.Location = new System.Drawing.Point(240, 110);
        this.butMoreOptions.Name = "butMoreOptions";
        this.butMoreOptions.Size = new System.Drawing.Size(99, 24);
        this.butMoreOptions.TabIndex = 45;
        this.butMoreOptions.Text = "More Options";
        this.butMoreOptions.Click += new System.EventHandler(this.butMoreOptions_Click);
        //
        // textAPR
        //
        this.textAPR.Location = new System.Drawing.Point(142, 78);
        this.textAPR.Name = "textAPR";
        this.textAPR.Size = new System.Drawing.Size(47, 20);
        this.textAPR.TabIndex = 15;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.label7);
        this.groupBox3.Controls.Add(this.textPeriodPayment);
        this.groupBox3.Controls.Add(this.textTerm);
        this.groupBox3.Controls.Add(this.label8);
        this.groupBox3.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox3.Location = new System.Drawing.Point(9, 101);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(225, 64);
        this.groupBox3.TabIndex = 23;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Either";
        //
        // textPeriodPayment
        //
        this.textPeriodPayment.Location = new System.Drawing.Point(133, 39);
        this.textPeriodPayment.Name = "textPeriodPayment";
        this.textPeriodPayment.Size = new System.Drawing.Size(85, 20);
        this.textPeriodPayment.TabIndex = 17;
        this.textPeriodPayment.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textPeriodPayment_KeyPress);
        //
        // textTerm
        //
        this.textTerm.Location = new System.Drawing.Point(133, 17);
        this.textTerm.setMaxVal(255);
        this.textTerm.setMinVal(0);
        this.textTerm.Name = "textTerm";
        this.textTerm.Size = new System.Drawing.Size(47, 20);
        this.textTerm.TabIndex = 18;
        this.textTerm.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textTerm_KeyPress);
        //
        // textDownPayment
        //
        this.textDownPayment.Location = new System.Drawing.Point(142, 56);
        this.textDownPayment.Name = "textDownPayment";
        this.textDownPayment.Size = new System.Drawing.Size(85, 20);
        this.textDownPayment.TabIndex = 22;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(4, 59);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(136, 17);
        this.label11.TabIndex = 21;
        this.label11.Text = "Down Payment";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateFirstPay
        //
        this.textDateFirstPay.Location = new System.Drawing.Point(142, 34);
        this.textDateFirstPay.Name = "textDateFirstPay";
        this.textDateFirstPay.Size = new System.Drawing.Size(85, 20);
        this.textDateFirstPay.TabIndex = 13;
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(142, 13);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(85, 20);
        this.textAmount.TabIndex = 11;
        this.textAmount.Validating += new System.ComponentModel.CancelEventHandler(this.textAmount_Validating);
        //
        // butCreateSched
        //
        this.butCreateSched.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCreateSched.setAutosize(true);
        this.butCreateSched.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCreateSched.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCreateSched.setCornerRadius(4F);
        this.butCreateSched.Location = new System.Drawing.Point(240, 138);
        this.butCreateSched.Name = "butCreateSched";
        this.butCreateSched.Size = new System.Drawing.Size(99, 24);
        this.butCreateSched.TabIndex = 42;
        this.butCreateSched.Text = "Create Schedule";
        this.butCreateSched.Click += new System.EventHandler(this.butCreateSched_Click);
        //
        // textTotalCost
        //
        this.textTotalCost.Location = new System.Drawing.Point(156, 385);
        this.textTotalCost.Name = "textTotalCost";
        this.textTotalCost.ReadOnly = true;
        this.textTotalCost.Size = new System.Drawing.Size(85, 20);
        this.textTotalCost.TabIndex = 35;
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(19, 385);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(134, 17);
        this.label15.TabIndex = 34;
        this.label15.Text = "Total Cost of Loan";
        this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(156, 10);
        this.textPatient.Name = "textPatient";
        this.textPatient.ReadOnly = true;
        this.textPatient.Size = new System.Drawing.Size(199, 20);
        this.textPatient.TabIndex = 25;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(30, 10);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(124, 17);
        this.label9.TabIndex = 24;
        this.label9.Text = "Patient";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(22, 431);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(133, 17);
        this.label12.TabIndex = 30;
        this.label12.Text = "Paid so far";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textAmtPaid
        //
        this.textAmtPaid.Location = new System.Drawing.Point(156, 429);
        this.textAmtPaid.Name = "textAmtPaid";
        this.textAmtPaid.ReadOnly = true;
        this.textAmtPaid.Size = new System.Drawing.Size(85, 20);
        this.textAmtPaid.TabIndex = 31;
        //
        // textAccumulatedDue
        //
        this.textAccumulatedDue.Location = new System.Drawing.Point(156, 407);
        this.textAccumulatedDue.Name = "textAccumulatedDue";
        this.textAccumulatedDue.ReadOnly = true;
        this.textAccumulatedDue.Size = new System.Drawing.Size(85, 20);
        this.textAccumulatedDue.TabIndex = 33;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(20, 409);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(135, 17);
        this.label13.TabIndex = 32;
        this.label13.Text = "Accumulated Due";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(23, 507);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(148, 17);
        this.label10.TabIndex = 37;
        this.label10.Text = "Note";
        this.label10.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textInsPlan
        //
        this.textInsPlan.Location = new System.Drawing.Point(156, 167);
        this.textInsPlan.Name = "textInsPlan";
        this.textInsPlan.ReadOnly = true;
        this.textInsPlan.Size = new System.Drawing.Size(199, 20);
        this.textInsPlan.TabIndex = 43;
        //
        // labelInsPlan
        //
        this.labelInsPlan.Location = new System.Drawing.Point(21, 167);
        this.labelInsPlan.Name = "labelInsPlan";
        this.labelInsPlan.Size = new System.Drawing.Size(132, 17);
        this.labelInsPlan.TabIndex = 42;
        this.labelInsPlan.Text = "Insurance Plan";
        this.labelInsPlan.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // checkIns
        //
        this.checkIns.Location = new System.Drawing.Point(156, 148);
        this.checkIns.Name = "checkIns";
        this.checkIns.Size = new System.Drawing.Size(268, 18);
        this.checkIns.TabIndex = 46;
        this.checkIns.Text = "Use for tracking expected insurance payments";
        this.checkIns.Click += new System.EventHandler(this.checkIns_Click);
        //
        // textPrincPaid
        //
        this.textPrincPaid.Location = new System.Drawing.Point(156, 451);
        this.textPrincPaid.Name = "textPrincPaid";
        this.textPrincPaid.ReadOnly = true;
        this.textPrincPaid.Size = new System.Drawing.Size(85, 20);
        this.textPrincPaid.TabIndex = 56;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(22, 453);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(133, 17);
        this.label14.TabIndex = 55;
        this.label14.Text = "Principal paid so far";
        this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(4, 475);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(151, 17);
        this.label1.TabIndex = 57;
        this.label1.Text = "Tx Completed Amt";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(244, 474);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(180, 40);
        this.label3.TabIndex = 59;
        this.label3.Text = "This should usually match the total amount of the pay plan.";
        //
        // comboProv
        //
        this.comboProv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProv.Location = new System.Drawing.Point(142, 14);
        this.comboProv.MaxDropDownItems = 30;
        this.comboProv.Name = "comboProv";
        this.comboProv.Size = new System.Drawing.Size(158, 21);
        this.comboProv.TabIndex = 169;
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.Location = new System.Drawing.Point(142, 39);
        this.comboClinic.MaxDropDownItems = 30;
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(177, 21);
        this.comboClinic.TabIndex = 167;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(26, 41);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(114, 16);
        this.labelClinic.TabIndex = 168;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(41, 18);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(100, 16);
        this.label16.TabIndex = 166;
        this.label16.Text = "Provider";
        this.label16.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.comboClinic);
        this.groupBox1.Controls.Add(this.butPickProv);
        this.groupBox1.Controls.Add(this.label16);
        this.groupBox1.Controls.Add(this.comboProv);
        this.groupBox1.Controls.Add(this.labelClinic);
        this.groupBox1.Location = new System.Drawing.Point(14, 76);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(347, 65);
        this.groupBox1.TabIndex = 171;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Same for all charges";
        //
        // butPickProv
        //
        this.butPickProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProv.setAutosize(false);
        this.butPickProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProv.setCornerRadius(2F);
        this.butPickProv.Location = new System.Drawing.Point(301, 14);
        this.butPickProv.Name = "butPickProv";
        this.butPickProv.Size = new System.Drawing.Size(18, 21);
        this.butPickProv.TabIndex = 170;
        this.butPickProv.Text = "...";
        //
        // textCompletedAmt
        //
        this.textCompletedAmt.Location = new System.Drawing.Point(156, 473);
        this.textCompletedAmt.Name = "textCompletedAmt";
        this.textCompletedAmt.Size = new System.Drawing.Size(85, 20);
        this.textCompletedAmt.TabIndex = 58;
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(435, 611);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(84, 24);
        this.butAdd.TabIndex = 54;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClear
        //
        this.butClear.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClear.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butClear.setAutosize(true);
        this.butClear.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClear.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClear.setCornerRadius(4F);
        this.butClear.Location = new System.Drawing.Point(534, 611);
        this.butClear.Name = "butClear";
        this.butClear.Size = new System.Drawing.Size(99, 24);
        this.butClear.TabIndex = 53;
        this.butClear.Text = "Clear Schedule";
        this.butClear.Click += new System.EventHandler(this.butClear_Click);
        //
        // butChangePlan
        //
        this.butChangePlan.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangePlan.setAutosize(true);
        this.butChangePlan.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangePlan.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangePlan.setCornerRadius(4F);
        this.butChangePlan.Location = new System.Drawing.Point(354, 166);
        this.butChangePlan.Name = "butChangePlan";
        this.butChangePlan.Size = new System.Drawing.Size(75, 22);
        this.butChangePlan.TabIndex = 44;
        this.butChangePlan.Text = "C&hange";
        this.butChangePlan.Click += new System.EventHandler(this.butChangePlan_Click);
        //
        // gridCharges
        //
        this.gridCharges.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridCharges.setHScrollVisible(false);
        this.gridCharges.Location = new System.Drawing.Point(435, 9);
        this.gridCharges.Name = "gridCharges";
        this.gridCharges.setScrollValue(0);
        this.gridCharges.Size = new System.Drawing.Size(536, 596);
        this.gridCharges.TabIndex = 41;
        this.gridCharges.setTitle("Amortization Schedule");
        this.gridCharges.setTranslationName("PayPlanAmortization");
        this.gridCharges.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridCharges.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridCharges_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // textNote
        //
        this.textNote.AcceptsTab = true;
        this.textNote.DetectUrls = false;
        this.textNote.Location = new System.Drawing.Point(22, 528);
        this.textNote.Name = "textNote";
        this.textNote.setQuickPasteType(OpenDentBusiness.QuickPasteType.PayPlan);
        this.textNote.ScrollBars = System.Windows.Forms.RichTextBoxScrollBars.Vertical;
        this.textNote.Size = new System.Drawing.Size(392, 121);
        this.textNote.TabIndex = 40;
        this.textNote.Text = "";
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
        this.butDelete.Location = new System.Drawing.Point(22, 660);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(84, 24);
        this.butDelete.TabIndex = 38;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butGoToPat
        //
        this.butGoToPat.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGoToPat.setAutosize(true);
        this.butGoToPat.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGoToPat.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGoToPat.setCornerRadius(4F);
        this.butGoToPat.Location = new System.Drawing.Point(354, 9);
        this.butGoToPat.Name = "butGoToPat";
        this.butGoToPat.Size = new System.Drawing.Size(75, 22);
        this.butGoToPat.TabIndex = 27;
        this.butGoToPat.Text = "&Go To";
        this.butGoToPat.Click += new System.EventHandler(this.butGoToPat_Click);
        //
        // butGoToGuar
        //
        this.butGoToGuar.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butGoToGuar.setAutosize(true);
        this.butGoToGuar.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butGoToGuar.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butGoToGuar.setCornerRadius(4F);
        this.butGoToGuar.Location = new System.Drawing.Point(354, 31);
        this.butGoToGuar.Name = "butGoToGuar";
        this.butGoToGuar.Size = new System.Drawing.Size(75, 22);
        this.butGoToGuar.TabIndex = 23;
        this.butGoToGuar.Text = "Go &To";
        this.butGoToGuar.Click += new System.EventHandler(this.butGoTo_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(156, 189);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(85, 20);
        this.textDate.TabIndex = 7;
        //
        // butChangeGuar
        //
        this.butChangeGuar.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butChangeGuar.setAutosize(true);
        this.butChangeGuar.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butChangeGuar.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butChangeGuar.setCornerRadius(4F);
        this.butChangeGuar.Location = new System.Drawing.Point(354, 53);
        this.butChangeGuar.Name = "butChangeGuar";
        this.butChangeGuar.Size = new System.Drawing.Size(75, 22);
        this.butChangeGuar.TabIndex = 4;
        this.butChangeGuar.Text = "C&hange";
        this.butChangeGuar.Click += new System.EventHandler(this.butChangeGuar_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(787, 660);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
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
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(880, 660);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butPrint
        //
        this.butPrint.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPrint.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        this.butPrint.setAutosize(true);
        this.butPrint.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPrint.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPrint.setCornerRadius(4F);
        this.butPrint.Image = Resources.getbutPrintSmall();
        this.butPrint.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPrint.Location = new System.Drawing.Point(563, 660);
        this.butPrint.Name = "butPrint";
        this.butPrint.Size = new System.Drawing.Size(85, 24);
        this.butPrint.TabIndex = 20;
        this.butPrint.Text = "&Print";
        this.butPrint.Click += new System.EventHandler(this.butPrint_Click);
        //
        // FormPayPlan
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(974, 698);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCompletedAmt);
        this.Controls.Add(this.textPrincPaid);
        this.Controls.Add(this.label14);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClear);
        this.Controls.Add(this.checkIns);
        this.Controls.Add(this.butChangePlan);
        this.Controls.Add(this.textInsPlan);
        this.Controls.Add(this.labelInsPlan);
        this.Controls.Add(this.gridCharges);
        this.Controls.Add(this.textNote);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textAccumulatedDue);
        this.Controls.Add(this.textAmtPaid);
        this.Controls.Add(this.butGoToPat);
        this.Controls.Add(this.textPatient);
        this.Controls.Add(this.butGoToGuar);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butChangeGuar);
        this.Controls.Add(this.textGuarantor);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.label10);
        this.Controls.Add(this.label13);
        this.Controls.Add(this.label12);
        this.Controls.Add(this.label9);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.labelGuarantor);
        this.Controls.Add(this.textTotalCost);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.butPrint);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPayPlan";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Payment Plan";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormPayPlan_Closing);
        this.Load += new System.EventHandler(this.FormPayPlan_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.groupBox3.ResumeLayout(false);
        this.groupBox3.PerformLayout();
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPayPlan_Load(Object sender, System.EventArgs e) throws Exception {
        textPatient.Text = Patients.getLim(PayPlanCur.PatNum).getNameLF();
        textGuarantor.Text = Patients.getLim(PayPlanCur.Guarantor).getNameLF();
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            if (IsNew && ProviderC.getListShort()[i].ProvNum == PatCur.PriProv)
            {
                //new payment plans default to pri prov
                comboProv.SelectedIndex = i;
            }
             
        }
        //but if not new, then the provider will be selected in FillCharges().
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            labelClinic.Visible = false;
            comboClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add("none");
            if (IsNew)
            {
                comboClinic.SelectedIndex = 0;
            }
            else
            {
            } 
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                //this is for patients with no clinic assigned, an unusual situation.
                //we don't want to do this.  The -1 indicates to pull clinic from charges on first loop in FillCharges().
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (IsNew && Clinics.getList()[i].ClinicNum == PatCur.ClinicNum)
                {
                    //new payment plans default to pat clinic
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        } 
        textDate.Text = PayPlanCur.PayPlanDate.ToShortDateString();
        if (IsNew)
        {
            textAmount.Text = TotalAmt.ToString("f");
            //it won't get filled in FillCharges because there are no charges yet
            textDateFirstPay.Text = PayPlanCur.PayPlanDate.ToShortDateString();
        }
         
        textAPR.Text = PayPlanCur.APR.ToString();
        AmtPaid = PayPlans.getAmtPaid(PayPlanCur.PayPlanNum);
        textAmtPaid.Text = AmtPaid.ToString("f");
        textCompletedAmt.Text = PayPlanCur.CompletedAmt.ToString("f");
        textNote.Text = PayPlanCur.Note;
        if (PayPlanCur.PlanNum == 0)
        {
            labelInsPlan.Visible = false;
            textInsPlan.Visible = false;
            butChangePlan.Visible = false;
        }
        else
        {
            textInsPlan.Text = InsPlans.getDescript(PayPlanCur.PlanNum,FamCur,InsPlanList,PayPlanCur.InsSubNum,SubList);
            checkIns.Checked = true;
            labelGuarantor.Visible = false;
            textGuarantor.Visible = false;
            butGoToGuar.Visible = false;
            butChangeGuar.Visible = false;
        } 
        fillCharges();
    }

    /**
    * Called 5 times.  This also fills prov and clinic based on the first charge if not new.
    */
    private void fillCharges() throws Exception {
        table = AccountModules.getPayPlanAmort(PayPlanCur.PayPlanNum).Tables["payplanamort"];
        gridCharges.beginUpdate();
        gridCharges.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn(Lan.g("PayPlanAmortization","Date"), 65, HorizontalAlignment.Right);
        gridCharges.getColumns().add(col);
        col = new ODGridColumn(Lan.g("PayPlanAmortization","Description"),220);
        gridCharges.getColumns().add(col);
        col = new ODGridColumn(Lan.g("PayPlanAmortization","Charges"), 60, HorizontalAlignment.Right);
        gridCharges.getColumns().add(col);
        col = new ODGridColumn(Lan.g("PayPlanAmortization","Credits"), 60, HorizontalAlignment.Right);
        gridCharges.getColumns().add(col);
        col = new ODGridColumn(Lan.g("PayPlanAmortization","Balance"), 60, HorizontalAlignment.Right);
        gridCharges.getColumns().add(col);
        col = new ODGridColumn("",147);
        //filler
        gridCharges.getColumns().add(col);
        gridCharges.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["date"].ToString());
            row.getCells().Add(table.Rows[i]["description"].ToString());
            row.getCells().Add(table.Rows[i]["charges"].ToString());
            row.getCells().Add(table.Rows[i]["credits"].ToString());
            row.getCells().Add(table.Rows[i]["balance"].ToString());
            row.getCells().add("");
            row.setColorText(Color.FromArgb(PIn.Int(table.Rows[i]["colorText"].ToString())));
            //not the last row
            if (i < table.Rows.Count - 1 && ((DateTime)table.Rows[i]["DateTime"]).Date <= DateTime.Today && ((DateTime)table.Rows[i + 1]["DateTime"]).Date > DateTime.Today)
            {
                row.setColorLborder(Color.Black);
                row.getCells()[4].Bold = YN.Yes;
            }
             
            gridCharges.getRows().add(row);
        }
        //The code below is not very efficient, but it doesn't matter
        //List<PayPlanCharge> ChargeListAll=PayPlanCharges.Refresh(PayPlanCur.Guarantor);
        List<PayPlanCharge> ChargeList = PayPlanCharges.getForPayPlan(PayPlanCur.PayPlanNum);
        TotPrinc = 0;
        TotInt = 0;
        for (int i = 0;i < ChargeList.Count;i++)
        {
            TotPrinc += ChargeList[i].Principal;
            TotInt += ChargeList[i].Interest;
        }
        TotPrincInt = TotPrinc + TotInt;
        if (ChargeList.Count == 0)
        {
        }
        else
        {
            //don't damage what's already present in textAmount.Text
            textAmount.Text = TotPrinc.ToString("f");
        } 
        textTotalCost.Text = TotPrincInt.ToString("f");
        if (ChargeList.Count > 0)
        {
            textDateFirstPay.Text = ChargeList[0].ChargeDate.ToShortDateString();
        }
        else
        {
        } 
        //don't damage what's already in textDateFirstPay.Text
        gridCharges.endUpdate();
        textAccumulatedDue.Text = PayPlans.GetAccumDue(PayPlanCur.PayPlanNum, ChargeList).ToString("f");
        textPrincPaid.Text = PayPlans.GetPrincPaid(AmtPaid, PayPlanCur.PayPlanNum, ChargeList).ToString("f");
        if (!IsNew && ChargeList.Count > 0)
        {
            if (comboProv.SelectedIndex == -1)
            {
                //This avoids resetting the combo every time FillCharges is run.
                comboProv.SelectedIndex = Providers.GetIndex(ChargeList[0].ProvNum);
            }
             
            //could still be -1
            if (!PrefC.getBool(PrefName.EasyNoClinics) && comboClinic.SelectedIndex == -1)
            {
                if (ChargeList[0].ClinicNum == 0)
                {
                    comboClinic.SelectedIndex = 0;
                }
                else
                {
                    comboClinic.SelectedIndex = Clinics.GetIndex(ChargeList[0].ClinicNum) + 1;
                } 
            }
             
        }
         
    }

    private void butGoToPat_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveData())
        {
            return ;
        }
         
        GotoPatNum = PayPlanCur.PatNum;
        DialogResult = DialogResult.OK;
    }

    private void butGoTo_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveData())
        {
            return ;
        }
         
        GotoPatNum = PayPlanCur.Guarantor;
        DialogResult = DialogResult.OK;
    }

    private void butChangeGuar_Click(Object sender, System.EventArgs e) throws Exception {
        if (PayPlans.getAmtPaid(PayPlanCur.PayPlanNum) != 0)
        {
            MsgBox.show(this,"Not allowed to change the guarantor because payments are attached.");
            return ;
        }
         
        if (table.Rows.Count > 0)
        {
            MsgBox.show(this,"Not allowed to change the guarantor without first clearing the amortization schedule.");
            return ;
        }
         
        FormPatientSelect FormPS = new FormPatientSelect();
        FormPS.SelectionModeOnly = true;
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PayPlanCur.Guarantor = FormPS.SelectedPatNum;
        textGuarantor.Text = Patients.getLim(PayPlanCur.Guarantor).getNameLF();
    }

    private void checkIns_Click(Object sender, System.EventArgs e) throws Exception {
        if (PayPlans.getAmtPaid(PayPlanCur.PayPlanNum) != 0)
        {
            MsgBox.show(this,"Not allowed because payments are attached.");
            checkIns.Checked = !checkIns.Checked;
            return ;
        }
         
        if (table.Rows.Count > 0)
        {
            MsgBox.show(this,"Not allowed without first clearing the amortization schedule.");
            checkIns.Checked = !checkIns.Checked;
            return ;
        }
         
        if (checkIns.Checked)
        {
            FormInsPlanSelect FormI = new FormInsPlanSelect(PayPlanCur.PatNum);
            FormI.ShowDialog();
            if (FormI.DialogResult == DialogResult.Cancel)
            {
                checkIns.Checked = false;
                return ;
            }
             
            PayPlanCur.PlanNum = FormI.SelectedPlan.PlanNum;
            PayPlanCur.InsSubNum = FormI.SelectedSub.InsSubNum;
            PayPlanCur.Guarantor = PayPlanCur.PatNum;
            textInsPlan.Text = InsPlans.getDescript(PayPlanCur.PlanNum,FamCur,InsPlanList,PayPlanCur.InsSubNum,SubList);
            labelGuarantor.Visible = false;
            textGuarantor.Visible = false;
            butGoToGuar.Visible = false;
            butChangeGuar.Visible = false;
            labelInsPlan.Visible = true;
            textInsPlan.Visible = true;
            butChangePlan.Visible = true;
        }
        else
        {
            //not insurance
            PayPlanCur.Guarantor = PayPlanCur.PatNum;
            textGuarantor.Text = Patients.getLim(PayPlanCur.Guarantor).getNameLF();
            PayPlanCur.PlanNum = 0;
            PayPlanCur.InsSubNum = 0;
            labelGuarantor.Visible = true;
            textGuarantor.Visible = true;
            butGoToGuar.Visible = true;
            butChangeGuar.Visible = true;
            labelInsPlan.Visible = false;
            textInsPlan.Visible = false;
            butChangePlan.Visible = false;
        } 
    }

    private void textAmount_Validating(Object sender, CancelEventArgs e) throws Exception {
        if (StringSupport.equals(textCompletedAmt.Text, ""))
        {
            return ;
        }
         
        if (textCompletedAmt.Text == textAmount.Text)
        {
            return ;
        }
         
        if (MsgBox.show(this,MsgBoxButtons.YesNo,"Change Tx Completed Amt to match?"))
        {
            textCompletedAmt.Text = textAmount.Text;
        }
         
    }

    private void butChangePlan_Click(Object sender, System.EventArgs e) throws Exception {
        FormInsPlanSelect FormI = new FormInsPlanSelect(PayPlanCur.PatNum);
        FormI.ShowDialog();
        if (FormI.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        PayPlanCur.PlanNum = FormI.SelectedPlan.PlanNum;
        PayPlanCur.InsSubNum = FormI.SelectedSub.InsSubNum;
        textInsPlan.Text = InsPlans.GetDescript(PayPlanCur.PlanNum, Patients.getFamily(PayPlanCur.PatNum), new List<InsPlan>(), PayPlanCur.InsSubNum, new List<InsSub>());
    }

    private void textTerm_KeyPress(Object sender, System.Windows.Forms.KeyPressEventArgs e) throws Exception {
        textPeriodPayment.Text = "";
    }

    private void textPeriodPayment_KeyPress(Object sender, System.Windows.Forms.KeyPressEventArgs e) throws Exception {
        textTerm.Text = "";
    }

    private void butMoreOptions_Click(Object sender, EventArgs e) throws Exception {
        FormPayPlanOpts.ShowDialog();
    }

    private void butCreateSched_Click(Object sender, System.EventArgs e) throws Exception {
        //this is also where the terms get saved
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textAmount.errorProvider1.GetError(textAmount), "") || !StringSupport.equals(textDateFirstPay.errorProvider1.GetError(textDateFirstPay), "") || !StringSupport.equals(textDownPayment.errorProvider1.GetError(textDownPayment), "") || !StringSupport.equals(textAPR.errorProvider1.GetError(textAPR), "") || !StringSupport.equals(textTerm.errorProvider1.GetError(textTerm), "") || !StringSupport.equals(textPeriodPayment.errorProvider1.GetError(textPeriodPayment), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        if (StringSupport.equals(textAmount.Text, "") || PIn.Double(textAmount.Text) == 0)
        {
            MsgBox.show(this,"Please enter an amount first.");
            return ;
        }
         
        if (StringSupport.equals(textDateFirstPay.Text, ""))
        {
            textDateFirstPay.Text = DateTime.Today.ToShortDateString();
        }
         
        if (StringSupport.equals(textDownPayment.Text, ""))
        {
            textDownPayment.Text = "0";
        }
         
        if (StringSupport.equals(textAPR.Text, ""))
        {
            textAPR.Text = "0";
        }
         
        if (StringSupport.equals(textTerm.Text, "") && StringSupport.equals(textPeriodPayment.Text, ""))
        {
            MsgBox.show(this,"Please enter a term or payment amount first.");
            return ;
        }
         
        if (StringSupport.equals(textTerm.Text, "") && PIn.Double(textPeriodPayment.Text) == 0)
        {
            MsgBox.show(this,"Payment cannot be 0.");
            return ;
        }
         
        if (StringSupport.equals(textPeriodPayment.Text, "") && PIn.Long(textTerm.Text) < 1)
        {
            MsgBox.show(this,"Term cannot be less than 1.");
            return ;
        }
         
        if (table.Rows.Count > 0)
        {
            if (!MsgBox.show(this,true,"Replace existing amortization schedule?"))
            {
                return ;
            }
             
            PayPlanCharges.deleteAllInPlan(PayPlanCur.PayPlanNum);
        }
         
        PayPlanCharge ppCharge;
        //down payment
        double downpayment = PIn.Double(textDownPayment.Text);
        if (downpayment != 0)
        {
            ppCharge = new PayPlanCharge();
            ppCharge.PayPlanNum = PayPlanCur.PayPlanNum;
            ppCharge.Guarantor = PayPlanCur.Guarantor;
            ppCharge.PatNum = PayPlanCur.PatNum;
            ppCharge.ChargeDate = DateTimeOD.getToday();
            ppCharge.Interest = 0;
            ppCharge.Principal = downpayment;
            ppCharge.Note = Lan.g(this,"Downpayment");
            ppCharge.ProvNum = PatCur.PriProv;
            //will be changed at the end.
            ppCharge.ClinicNum = PatCur.ClinicNum;
            //will be changed at the end.
            PayPlanCharges.insert(ppCharge);
        }
         
        double principal = PIn.Double(textAmount.Text) - PIn.Double(textDownPayment.Text);
        double APR = PIn.Double(textAPR.Text);
        double periodRate = new double();
        double periodPayment = new double();
        if (APR == 0)
        {
            periodRate = 0;
        }
        else
        {
            if (FormPayPlanOpts.radioWeekly.Checked)
            {
                periodRate = APR / 100 / 52;
            }
            else if (FormPayPlanOpts.radioEveryOtherWeek.Checked)
            {
                periodRate = APR / 100 / 26;
            }
            else if (FormPayPlanOpts.radioOrdinalWeekday.Checked)
            {
                periodRate = APR / 100 / 12;
            }
            else if (FormPayPlanOpts.radioMonthly.Checked)
            {
                periodRate = APR / 100 / 12;
            }
            else
            {
                //quarterly
                periodRate = APR / 100 / 4;
            }    
        } 
        int roundDec = CultureInfo.CurrentCulture.NumberFormat.NumberDecimalDigits;
        if (!StringSupport.equals(textTerm.Text, ""))
        {
            //Use term to determine period payment
            double term = PIn.Double(textTerm.Text);
            if (APR == 0)
            {
                periodPayment = Decimal.Round((double)(principal / term), roundDec);
            }
            else
            {
                periodPayment = Decimal.Round((double)(principal * periodRate / (1 - Math.Pow(1 + periodRate, -term))), roundDec);
            } 
        }
        else
        {
            //Use period payment supplied
            periodPayment = PIn.Decimal(textPeriodPayment.Text);
        } 
        double principalDecrementing = (double)principal;
        //the principal which will be decreased to zero in the loop.
        double periodPrincipal = new double();
        DateTime firstDate = PIn.Date(textDateFirstPay.Text);
        int countCharges = 0;
        while (principalDecrementing != 0 && countCharges < 100)
        {
            //the 100 limit prevents infinite loop
            ppCharge = new PayPlanCharge();
            ppCharge.PayPlanNum = PayPlanCur.PayPlanNum;
            ppCharge.Guarantor = PayPlanCur.Guarantor;
            ppCharge.PatNum = PayPlanCur.PatNum;
            if (FormPayPlanOpts.radioWeekly.Checked)
            {
                ppCharge.ChargeDate = firstDate.AddDays(7 * countCharges);
            }
            else if (FormPayPlanOpts.radioEveryOtherWeek.Checked)
            {
                ppCharge.ChargeDate = firstDate.AddDays(14 * countCharges);
            }
            else if (FormPayPlanOpts.radioOrdinalWeekday.Checked)
            {
                //First/second/etc Mon/Tue/etc of month
                DateTime roughMonth = firstDate.AddMonths(1 * countCharges);
                //this just gets us into the correct month and year
                DayOfWeek dayOfWeekFirstDate = firstDate.DayOfWeek;
                //find the starting point for the given month: the first day that matches day of week
                DayOfWeek dayOfWeekFirstMonth = (new DateTime(roughMonth.Year, roughMonth.Month, 1)).DayOfWeek;
                if (dayOfWeekFirstMonth == dayOfWeekFirstDate)
                {
                    //1st is the proper day of the week
                    ppCharge.ChargeDate = new DateTime(roughMonth.Year, roughMonth.Month, 1);
                }
                else if (dayOfWeekFirstMonth < dayOfWeekFirstDate)
                {
                    //Example, 1st is a Tues (2), but we need to start on a Thursday (4)
                    ppCharge.ChargeDate = new DateTime(roughMonth.Year, roughMonth.Month, dayOfWeekFirstDate - dayOfWeekFirstMonth + 1);
                }
                else
                {
                    //4-2+1=3.  The 3rd is a Thursday
                    //Example, 1st is a Thursday (4), but we need to start on a Monday (1)
                    ppCharge.ChargeDate = new DateTime(roughMonth.Year, roughMonth.Month, 7 - (dayOfWeekFirstMonth - dayOfWeekFirstDate) + 1);
                }  
                //7-(4-1)+1=5.  The 5th is a Monday
                int ordinalOfMonth = getOrdinalOfMonth(firstDate);
                //for example 3 if it's supposed to be the 3rd Friday of each month
                ppCharge.ChargeDate = ppCharge.ChargeDate.AddDays(7 * (ordinalOfMonth - 1));
            }
            else //to get to the 3rd Friday, and starting from the 1st Friday, we add 2 weeks.
            if (FormPayPlanOpts.radioMonthly.Checked)
            {
                ppCharge.ChargeDate = firstDate.AddMonths(1 * countCharges);
            }
            else
            {
                //quarterly
                ppCharge.ChargeDate = firstDate.AddMonths(3 * countCharges);
            }    
            ppCharge.Interest = Math.Round(((double)principalDecrementing * periodRate), roundDec);
            //2 decimals
            periodPrincipal = periodPayment - (double)ppCharge.Interest;
            ppCharge.Principal = (double)periodPrincipal;
            ppCharge.ProvNum = PatCur.PriProv;
            if (principalDecrementing < -.03m)
            {
                //principalDecrementing is a significantly negative number, so this charge does not get added.
                //js not sure when this would ever happen.  Needs better comments some day.
                //the negative amount instead gets subtracted from the previous charge entered.
                //List<PayPlanCharge> ChargeListAll=PayPlanCharges.Refresh(PayPlanCur.Guarantor);
                List<PayPlanCharge> ChargeList = PayPlanCharges.getForPayPlan(PayPlanCur.PayPlanNum);
                ppCharge = ChargeList[ChargeList.Count - 1].Copy();
                ppCharge.Principal += (double)principalDecrementing;
                PayPlanCharges.update(ppCharge);
                break;
            }
             
            principalDecrementing -= periodPrincipal;
            if (principalDecrementing < periodPrincipal / 3m)
            {
                //we are on the last loop because the remaining princ will be less than 1/3 of a monthly payment.
                //if(principalDecrementing<1m && principalDecrementing>-1m){
                //If this is based on # of payments, the amount will be pennies,
                //but if this is based on monthly amount, then the last payment could be any odd number at all.
                //Alter this principal
                periodPrincipal += principalDecrementing;
                ppCharge.Principal = (double)periodPrincipal;
                //and alter the interest by the opposite amount to keep the payment the same.
                //So in the end, the pennies got absorbed by changing the interest.
                if (APR != 0)
                {
                    ppCharge.Interest -= (double)principalDecrementing;
                }
                 
                principalDecrementing = 0;
            }
             
            //this will prevent another loop
            PayPlanCharges.insert(ppCharge);
            countCharges++;
        }
        fillCharges();
        textNote.Text += DateTime.Today.ToShortDateString() + " - Date of Agreement: " + textDate.Text + ", Total Amount: " + textAmount.Text + ", APR: " + textAPR.Text + ", Total Cost of Loan: " + textTotalCost.Text;
    }

    /**
    * For example, date is the 3rd Friday of the month, then this returns 3.
    */
    private int getOrdinalOfMonth(DateTime date) throws Exception {
        if (date.AddDays(-28).Month == date.Month)
        {
            return 4;
        }
        else //treat a 5 like a 4
        if (date.AddDays(-21).Month == date.Month)
        {
            return 4;
        }
        else //4
        if (date.AddDays(-14).Month == date.Month)
        {
            return 3;
        }
           
        if (date.AddDays(-7).Month == date.Month)
        {
            return 2;
        }
         
        return 1;
    }

    private void gridCharges_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (!StringSupport.equals(table.Rows[e.getRow()]["PayPlanChargeNum"].ToString(), "0"))
        {
            PayPlanCharge charge = PayPlanCharges.GetOne(PIn.Long(table.Rows[e.getRow()]["PayPlanChargeNum"].ToString()));
            FormPayPlanChargeEdit FormP = new FormPayPlanChargeEdit(charge);
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.Cancel)
            {
                return ;
            }
             
        }
        else if (!StringSupport.equals(table.Rows[e.getRow()]["PayNum"].ToString(), "0"))
        {
            Payment pay = Payments.GetPayment(PIn.Long(table.Rows[e.getRow()]["PayNum"].ToString()));
            /*if(pay.PayType==0){//provider income transfer. I don't think this is possible, but you never know.
            					FormProviderIncTrans FormPIT=new FormProviderIncTrans();
            					FormPIT.PatNum=PatCur.PatNum;
            					FormPIT.PaymentCur=pay;
            					FormPIT.IsNew=false;
            					FormPIT.ShowDialog();
            					if(FormPIT.DialogResult==DialogResult.Cancel){
            						return;
            					}
            				}
            				else{*/
            FormPayment FormPayment2 = new FormPayment(PatCur,FamCur,pay);
            FormPayment2.IsNew = false;
            FormPayment2.ShowDialog();
            if (FormPayment2.DialogResult == DialogResult.Cancel)
            {
                return ;
            }
             
        }
          
        //}
        fillCharges();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        PayPlanCharge ppCharge = new PayPlanCharge();
        ppCharge.PayPlanNum = PayPlanCur.PayPlanNum;
        ppCharge.Guarantor = PayPlanCur.Guarantor;
        ppCharge.ChargeDate = DateTime.Today;
        ppCharge.ProvNum = PatCur.PriProv;
        //will be changed at the end.
        ppCharge.ClinicNum = PatCur.ClinicNum;
        //will be changed at the end.
        FormPayPlanChargeEdit FormP = new FormPayPlanChargeEdit(ppCharge);
        FormP.IsNew = true;
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillCharges();
    }

    private void butClear_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Clear all charges from amortization schedule?"))
        {
            return ;
        }
         
        PayPlanCharges.deleteAllInPlan(PayPlanCur.PayPlanNum);
        fillCharges();
    }

    private void butPrint_Click(Object sender, System.EventArgs e) throws Exception {
        if (!saveData())
        {
            return ;
        }
         
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.addTitle("Payment Plan Terms");
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        report.AddSubTitle(DateTime.Today.ToShortDateString());
        String sectName = "Report Header";
        Section section = report.getSections().get___idx("Report Header");
        //int sectIndex=report.Sections.GetIndexOfKind(AreaSectionKind.ReportHeader);
        Size size = new Size(300, 20);
        //big enough for any text
        Font font = new Font("Tahoma", 9);
        ContentAlignment alignL = ContentAlignment.MiddleLeft;
        ContentAlignment alignR = ContentAlignment.MiddleRight;
        int yPos = 140;
        int space = 30;
        int x1 = 175;
        int x2 = 275;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Patient",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, textPatient.Text, font, alignR));
        yPos += space;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Guarantor",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, textGuarantor.Text, font, alignR));
        yPos += space;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Date of Agreement",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, PayPlanCur.PayPlanDate.ToString("d"), font, alignR));
        yPos += space;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Principal",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, TotPrinc.ToString("n"), font, alignR));
        yPos += space;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Annual Percentage Rate",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, PayPlanCur.APR.ToString("f1"), font, alignR));
        yPos += space;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Total Finance Charges",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, TotInt.ToString("n"), font, alignR));
        yPos += space;
        report.getReportObjects().add(new ReportObject(sectName,new Point(x1, yPos),size,"Total Cost of Loan",font,alignL));
        report.getReportObjects().add(new ReportObject(sectName, new Point(x2, yPos), size, TotPrincInt.ToString("n"), font, alignR));
        yPos += space;
        section.setHeight(yPos + 30);
        report.addColumn("ChargeDate",80,FieldValueType.Date);
        //move the first column more to the middle
        report.getLastRO(ReportObjectKind.TextObject).setLocation(new Point(150, 0));
        report.getLastRO(ReportObjectKind.TextObject).setStaticText("Date");
        report.getLastRO(ReportObjectKind.FieldObject).setLocation(new Point(150, 0));
        report.addColumn("Description",150,FieldValueType.String);
        report.addColumn("Charges",70,FieldValueType.Number);
        report.addColumn("Credits",70,FieldValueType.Number);
        report.addColumn("Balance",70,FieldValueType.String);
        //report.AddColumn("Note",300,FieldValueType.String);
        //report.GetLastRO(ReportObjectKind.TextObject).Location=new Point(report.GetLastRO(ReportObjectKind.TextObject).Location.X+20,0);
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleRight);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleRight);
        //report.Query="SELECT ChargeDate,Principal,Interest,Principal+Interest,Note "
        //	+"FROM payplancharge WHERE PayPlanNum="+POut.PInt(PayPlanCur.PayPlanNum)
        //	+" ORDER BY ChargeDate";
        //if(!report.SubmitQuery()){
        //	return;
        //}
        //report.Sections[
        DataTable tbl = new DataTable();
        tbl.Columns.Add("date");
        tbl.Columns.Add("description");
        tbl.Columns.Add("charges");
        tbl.Columns.Add("credits");
        tbl.Columns.Add("balance");
        DataRow row = new DataRow();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = tbl.NewRow();
            row["date"] = table.Rows[i]["date"].ToString();
            row["description"] = table.Rows[i]["description"].ToString();
            row["charges"] = table.Rows[i]["charges"].ToString();
            row["credits"] = table.Rows[i]["credits"].ToString();
            row["balance"] = table.Rows[i]["balance"].ToString();
            tbl.Rows.Add(row);
        }
        report.setReportTable(tbl);
        //yPos+=60;
        report.getReportObjects().add(new ReportObject("Report Footer",new Point(x1, 70),size,"Signature of Guarantor:",font,alignL));
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        FormR.ShowDialog();
    }

    private void pd2_PrintPage(Object sender, System.Drawing.Printing.PrintPageEventArgs e) throws Exception {
        int xPos = 15;
        //starting pos
        int yPos = (int)27.5;
        //starting pos
        e.Graphics.DrawString("Payment Plan Truth in Lending Statement", new Font("Arial", 8), Brushes.Black, (float)xPos, (float)yPos);
    }

    //e.Graphics.DrawImage(imageTemp,xPos,yPos);
    /**
    * 
    */
    private boolean saveData() throws Exception {
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), "") || !StringSupport.equals(textAPR.errorProvider1.GetError(textAPR), "") || !StringSupport.equals(textCompletedAmt.errorProvider1.GetError(textCompletedAmt), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return false;
        }
         
        if (table.Rows.Count == 0)
        {
            MsgBox.show(this,"An amortization schedule must be created first.");
            return false;
        }
         
        if (comboProv.SelectedIndex == -1)
        {
            MsgBox.show(this,"A provider must be selected first.");
            return false;
        }
         
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            if (comboClinic.SelectedIndex == -1)
            {
                MsgBox.show(this,"A clinic must be selected first.");
                return false;
            }
             
        }
         
        if (StringSupport.equals(textAPR.Text, ""))
        {
            textAPR.Text = "0";
        }
         
        //PatNum not editable.
        //Guarantor set already
        PayPlanCur.PayPlanDate = PIn.Date(textDate.Text);
        PayPlanCur.APR = PIn.Double(textAPR.Text);
        PayPlanCur.Note = textNote.Text;
        PayPlanCur.CompletedAmt = PIn.Double(textCompletedAmt.Text);
        //PlanNum set already
        PayPlans.update(PayPlanCur);
        //always saved to db before opening this form
        long provNum = ProviderC.getListShort()[comboProv.SelectedIndex].ProvNum;
        //already verified that there's a provider selected
        long clinicNum = 0;
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            if (comboClinic.SelectedIndex == 0)
            {
                clinicNum = 0;
            }
            else
            {
                clinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
            } 
        }
         
        PayPlanCharges.setProvAndClinic(PayPlanCur.PayPlanNum,provNum,clinicNum);
        return true;
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Delete payment plan?"))
        {
            return ;
        }
         
        try
        {
            //later improvement if needed: possibly prevent deletion of some charges like older ones.
            PayPlans.delete(PayPlanCur);
        }
        catch (ApplicationException ex)
        {
            MessageBox.Show(ex.Message);
            return ;
        }

        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (textCompletedAmt.Text != textAmount.Text)
        {
            if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Tx Completed Amt and Total Amount do not match, continue?"))
            {
                return ;
            }
             
        }
         
        if (!saveData())
        {
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

    private void formPayPlan_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (DialogResult == DialogResult.OK)
        {
            return ;
        }
         
        if (IsNew)
        {
            try
            {
                PayPlans.delete(PayPlanCur);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                e.Cancel = true;
                return ;
            }
        
        }
         
    }

}


