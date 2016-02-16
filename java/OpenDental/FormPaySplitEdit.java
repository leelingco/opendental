//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:28 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormPatientSelect;
import OpenDental.FormPayPlanSelect;
import OpenDental.FormPaySplitEdit;
import OpenDental.FormProcSelect;
import OpenDental.FormProviderPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDentBusiness.Adjustment;
import OpenDentBusiness.Adjustments;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Family;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PayPlanCharges;
import OpenDentBusiness.PayPlans;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.PaySplits;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Tooth;

/**
* Summary description for FormPaySplitEdit.
*/
public class FormPaySplitEdit  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button ButCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butRemainder;
    private System.Windows.Forms.Label label5 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listPatient = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label labelAmount = new System.Windows.Forms.Label();
    private OpenDental.ValidDouble textAmount;
    private System.ComponentModel.Container components = null;
    // Required designer variable.
    /**
    * 
    */
    public boolean IsNew = new boolean();
    private System.Windows.Forms.Label labelRemainder = new System.Windows.Forms.Label();
    /**
    * The value needed to make the splits balance.
    */
    public double Remain = new double();
    private System.Windows.Forms.CheckBox checkPayPlan = new System.Windows.Forms.CheckBox();
    /**
    * 
    */
    public PaySplit PaySplitCur;
    private OpenDental.UI.Button butDelete;
    private OpenDental.ValidDate textProcDate;
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDatePay;
    private System.Windows.Forms.TextBox textPatient = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.CheckBox checkPatOtherFam = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupPatient = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupProcedure = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butAttach;
    private OpenDental.UI.Button butDetach;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label8 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label9 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label10 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label11 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label12 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label13 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProcFee = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcInsPaid = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcInsEst = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcAdj = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcPrevPaid = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label labelProcRemain = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProcDate2 = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcDescription = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcProv = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label14 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textProcTooth = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textProcPaidHere = new System.Windows.Forms.TextBox();
    //private Patient PatCur;
    private Family FamCur;
    private Procedure ProcCur;
    //<summary>Used if changing the Patient from another family.</summary>
    //private int OriginalPatNum;
    private double ProcFee = new double();
    private double ProcWriteoff = new double();
    private double ProcInsPaid = new double();
    private double ProcInsEst = new double();
    private double ProcAdj = new double();
    private double ProcPrevPaid = new double();
    private System.Windows.Forms.Label label15 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateEntry;
    private TextBox textProcWriteoff = new TextBox();
    private Label label16 = new Label();
    private Label label17 = new Label();
    private ComboBox comboUnearnedTypes = new ComboBox();
    private ComboBox comboProvider = new ComboBox();
    private OpenDental.UI.Button butPickProv;
    private ComboBox comboClinic = new ComboBox();
    private Label labelClinic = new Label();
    private double ProcPaidHere = new double();
    /**
    * 
    */
    public FormPaySplitEdit(Family famCur) throws Exception {
        //PaySplit paySplitCur,Family famCur){
        initializeComponent();
        FamCur = famCur;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPaySplitEdit.class);
        this.labelRemainder = new System.Windows.Forms.Label();
        this.label5 = new System.Windows.Forms.Label();
        this.listPatient = new System.Windows.Forms.ListBox();
        this.labelAmount = new System.Windows.Forms.Label();
        this.checkPayPlan = new System.Windows.Forms.CheckBox();
        this.label7 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textPatient = new System.Windows.Forms.TextBox();
        this.checkPatOtherFam = new System.Windows.Forms.CheckBox();
        this.groupPatient = new System.Windows.Forms.GroupBox();
        this.groupProcedure = new System.Windows.Forms.GroupBox();
        this.textProcWriteoff = new System.Windows.Forms.TextBox();
        this.label16 = new System.Windows.Forms.Label();
        this.textProcTooth = new System.Windows.Forms.TextBox();
        this.label14 = new System.Windows.Forms.Label();
        this.textProcProv = new System.Windows.Forms.TextBox();
        this.textProcDescription = new System.Windows.Forms.TextBox();
        this.textProcDate2 = new System.Windows.Forms.TextBox();
        this.labelProcRemain = new System.Windows.Forms.Label();
        this.textProcPaidHere = new System.Windows.Forms.TextBox();
        this.textProcPrevPaid = new System.Windows.Forms.TextBox();
        this.textProcAdj = new System.Windows.Forms.TextBox();
        this.textProcInsEst = new System.Windows.Forms.TextBox();
        this.textProcInsPaid = new System.Windows.Forms.TextBox();
        this.textProcFee = new System.Windows.Forms.TextBox();
        this.label13 = new System.Windows.Forms.Label();
        this.label12 = new System.Windows.Forms.Label();
        this.label11 = new System.Windows.Forms.Label();
        this.label10 = new System.Windows.Forms.Label();
        this.label9 = new System.Windows.Forms.Label();
        this.label8 = new System.Windows.Forms.Label();
        this.label6 = new System.Windows.Forms.Label();
        this.label4 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butDetach = new OpenDental.UI.Button();
        this.butAttach = new OpenDental.UI.Button();
        this.label15 = new System.Windows.Forms.Label();
        this.label17 = new System.Windows.Forms.Label();
        this.textDateEntry = new OpenDental.ValidDate();
        this.textProcDate = new OpenDental.ValidDate();
        this.textDatePay = new OpenDental.ValidDate();
        this.butDelete = new OpenDental.UI.Button();
        this.textAmount = new OpenDental.ValidDouble();
        this.butRemainder = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.ButCancel = new OpenDental.UI.Button();
        this.comboUnearnedTypes = new System.Windows.Forms.ComboBox();
        this.comboProvider = new System.Windows.Forms.ComboBox();
        this.butPickProv = new OpenDental.UI.Button();
        this.comboClinic = new System.Windows.Forms.ComboBox();
        this.labelClinic = new System.Windows.Forms.Label();
        this.groupPatient.SuspendLayout();
        this.groupProcedure.SuspendLayout();
        this.SuspendLayout();
        //
        // labelRemainder
        //
        this.labelRemainder.Location = new System.Drawing.Point(5, 336);
        this.labelRemainder.Name = "labelRemainder";
        this.labelRemainder.Size = new System.Drawing.Size(119, 88);
        this.labelRemainder.TabIndex = 5;
        this.labelRemainder.Text = "The Remainder button will calculate the value needed to make the splits balance.";
        this.labelRemainder.Visible = false;
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(33, 144);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(95, 16);
        this.label5.TabIndex = 10;
        this.label5.Text = "Provider";
        this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // listPatient
        //
        this.listPatient.Location = new System.Drawing.Point(11, 34);
        this.listPatient.Name = "listPatient";
        this.listPatient.Size = new System.Drawing.Size(192, 108);
        this.listPatient.TabIndex = 3;
        this.listPatient.MouseDown += new System.Windows.Forms.MouseEventHandler(this.listPatient_MouseDown);
        //
        // labelAmount
        //
        this.labelAmount.Location = new System.Drawing.Point(23, 96);
        this.labelAmount.Name = "labelAmount";
        this.labelAmount.Size = new System.Drawing.Size(104, 16);
        this.labelAmount.TabIndex = 15;
        this.labelAmount.Text = "Amount";
        this.labelAmount.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkPayPlan
        //
        this.checkPayPlan.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPayPlan.Location = new System.Drawing.Point(255, 497);
        this.checkPayPlan.Name = "checkPayPlan";
        this.checkPayPlan.Size = new System.Drawing.Size(198, 18);
        this.checkPayPlan.TabIndex = 20;
        this.checkPayPlan.Text = "Attached to Payment Plan";
        this.checkPayPlan.Click += new System.EventHandler(this.checkPayPlan_Click);
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(12, 73);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(116, 16);
        this.label7.TabIndex = 24;
        this.label7.Text = "(procedure date)";
        this.label7.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(0, 48);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(127, 16);
        this.label1.TabIndex = 23;
        this.label1.Text = "Payment Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textPatient
        //
        this.textPatient.Location = new System.Drawing.Point(11, 33);
        this.textPatient.Name = "textPatient";
        this.textPatient.Size = new System.Drawing.Size(238, 20);
        this.textPatient.TabIndex = 111;
        //
        // checkPatOtherFam
        //
        this.checkPatOtherFam.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkPatOtherFam.Location = new System.Drawing.Point(11, 15);
        this.checkPatOtherFam.Name = "checkPatOtherFam";
        this.checkPatOtherFam.Size = new System.Drawing.Size(192, 17);
        this.checkPatOtherFam.TabIndex = 110;
        this.checkPatOtherFam.TabStop = false;
        this.checkPatOtherFam.Text = "Is from another family";
        this.checkPatOtherFam.Click += new System.EventHandler(this.checkPatOtherFam_Click);
        //
        // groupPatient
        //
        this.groupPatient.Controls.Add(this.listPatient);
        this.groupPatient.Controls.Add(this.textPatient);
        this.groupPatient.Controls.Add(this.checkPatOtherFam);
        this.groupPatient.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupPatient.Location = new System.Drawing.Point(480, 26);
        this.groupPatient.Name = "groupPatient";
        this.groupPatient.Size = new System.Drawing.Size(265, 157);
        this.groupPatient.TabIndex = 112;
        this.groupPatient.TabStop = false;
        this.groupPatient.Text = "Patient";
        //
        // groupProcedure
        //
        this.groupProcedure.Controls.Add(this.textProcWriteoff);
        this.groupProcedure.Controls.Add(this.label16);
        this.groupProcedure.Controls.Add(this.textProcTooth);
        this.groupProcedure.Controls.Add(this.label14);
        this.groupProcedure.Controls.Add(this.textProcProv);
        this.groupProcedure.Controls.Add(this.textProcDescription);
        this.groupProcedure.Controls.Add(this.textProcDate2);
        this.groupProcedure.Controls.Add(this.labelProcRemain);
        this.groupProcedure.Controls.Add(this.textProcPaidHere);
        this.groupProcedure.Controls.Add(this.textProcPrevPaid);
        this.groupProcedure.Controls.Add(this.textProcAdj);
        this.groupProcedure.Controls.Add(this.textProcInsEst);
        this.groupProcedure.Controls.Add(this.textProcInsPaid);
        this.groupProcedure.Controls.Add(this.textProcFee);
        this.groupProcedure.Controls.Add(this.label13);
        this.groupProcedure.Controls.Add(this.label12);
        this.groupProcedure.Controls.Add(this.label11);
        this.groupProcedure.Controls.Add(this.label10);
        this.groupProcedure.Controls.Add(this.label9);
        this.groupProcedure.Controls.Add(this.label8);
        this.groupProcedure.Controls.Add(this.label6);
        this.groupProcedure.Controls.Add(this.label4);
        this.groupProcedure.Controls.Add(this.label3);
        this.groupProcedure.Controls.Add(this.label2);
        this.groupProcedure.Controls.Add(this.butDetach);
        this.groupProcedure.Controls.Add(this.butAttach);
        this.groupProcedure.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupProcedure.Location = new System.Drawing.Point(130, 228);
        this.groupProcedure.Name = "groupProcedure";
        this.groupProcedure.Size = new System.Drawing.Size(615, 225);
        this.groupProcedure.TabIndex = 113;
        this.groupProcedure.TabStop = false;
        this.groupProcedure.Text = "Procedure";
        //
        // textProcWriteoff
        //
        this.textProcWriteoff.Location = new System.Drawing.Point(477, 48);
        this.textProcWriteoff.Name = "textProcWriteoff";
        this.textProcWriteoff.ReadOnly = true;
        this.textProcWriteoff.Size = new System.Drawing.Size(76, 20);
        this.textProcWriteoff.TabIndex = 50;
        this.textProcWriteoff.Text = "5.00";
        this.textProcWriteoff.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label16
        //
        this.label16.Location = new System.Drawing.Point(369, 50);
        this.label16.Name = "label16";
        this.label16.Size = new System.Drawing.Size(104, 16);
        this.label16.TabIndex = 49;
        this.label16.Text = "- Writeoff";
        this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProcTooth
        //
        this.textProcTooth.Location = new System.Drawing.Point(115, 95);
        this.textProcTooth.Name = "textProcTooth";
        this.textProcTooth.ReadOnly = true;
        this.textProcTooth.Size = new System.Drawing.Size(43, 20);
        this.textProcTooth.TabIndex = 46;
        //
        // label14
        //
        this.label14.Location = new System.Drawing.Point(9, 98);
        this.label14.Name = "label14";
        this.label14.Size = new System.Drawing.Size(104, 16);
        this.label14.TabIndex = 45;
        this.label14.Text = "Tooth";
        this.label14.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textProcProv
        //
        this.textProcProv.Location = new System.Drawing.Point(115, 75);
        this.textProcProv.Name = "textProcProv";
        this.textProcProv.ReadOnly = true;
        this.textProcProv.Size = new System.Drawing.Size(76, 20);
        this.textProcProv.TabIndex = 44;
        //
        // textProcDescription
        //
        this.textProcDescription.Location = new System.Drawing.Point(115, 115);
        this.textProcDescription.Name = "textProcDescription";
        this.textProcDescription.ReadOnly = true;
        this.textProcDescription.Size = new System.Drawing.Size(241, 20);
        this.textProcDescription.TabIndex = 43;
        //
        // textProcDate2
        //
        this.textProcDate2.Location = new System.Drawing.Point(115, 55);
        this.textProcDate2.Name = "textProcDate2";
        this.textProcDate2.ReadOnly = true;
        this.textProcDate2.Size = new System.Drawing.Size(76, 20);
        this.textProcDate2.TabIndex = 42;
        //
        // labelProcRemain
        //
        this.labelProcRemain.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        this.labelProcRemain.Location = new System.Drawing.Point(478, 175);
        this.labelProcRemain.Name = "labelProcRemain";
        this.labelProcRemain.Size = new System.Drawing.Size(73, 18);
        this.labelProcRemain.TabIndex = 41;
        this.labelProcRemain.Text = "$85.00";
        this.labelProcRemain.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textProcPaidHere
        //
        this.textProcPaidHere.Location = new System.Drawing.Point(477, 148);
        this.textProcPaidHere.Name = "textProcPaidHere";
        this.textProcPaidHere.ReadOnly = true;
        this.textProcPaidHere.Size = new System.Drawing.Size(76, 20);
        this.textProcPaidHere.TabIndex = 40;
        this.textProcPaidHere.Text = "0.00";
        this.textProcPaidHere.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textProcPrevPaid
        //
        this.textProcPrevPaid.Location = new System.Drawing.Point(477, 128);
        this.textProcPrevPaid.Name = "textProcPrevPaid";
        this.textProcPrevPaid.ReadOnly = true;
        this.textProcPrevPaid.Size = new System.Drawing.Size(76, 20);
        this.textProcPrevPaid.TabIndex = 39;
        this.textProcPrevPaid.Text = "0.00";
        this.textProcPrevPaid.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textProcAdj
        //
        this.textProcAdj.Location = new System.Drawing.Point(477, 108);
        this.textProcAdj.Name = "textProcAdj";
        this.textProcAdj.ReadOnly = true;
        this.textProcAdj.Size = new System.Drawing.Size(76, 20);
        this.textProcAdj.TabIndex = 38;
        this.textProcAdj.Text = "10.00";
        this.textProcAdj.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textProcInsEst
        //
        this.textProcInsEst.Location = new System.Drawing.Point(477, 88);
        this.textProcInsEst.Name = "textProcInsEst";
        this.textProcInsEst.ReadOnly = true;
        this.textProcInsEst.Size = new System.Drawing.Size(76, 20);
        this.textProcInsEst.TabIndex = 37;
        this.textProcInsEst.Text = "55.00";
        this.textProcInsEst.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textProcInsPaid
        //
        this.textProcInsPaid.Location = new System.Drawing.Point(477, 68);
        this.textProcInsPaid.Name = "textProcInsPaid";
        this.textProcInsPaid.ReadOnly = true;
        this.textProcInsPaid.Size = new System.Drawing.Size(76, 20);
        this.textProcInsPaid.TabIndex = 36;
        this.textProcInsPaid.Text = "45.00";
        this.textProcInsPaid.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // textProcFee
        //
        this.textProcFee.Location = new System.Drawing.Point(477, 28);
        this.textProcFee.Name = "textProcFee";
        this.textProcFee.ReadOnly = true;
        this.textProcFee.Size = new System.Drawing.Size(76, 20);
        this.textProcFee.TabIndex = 35;
        this.textProcFee.Text = "200.00";
        this.textProcFee.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
        //
        // label13
        //
        this.label13.Location = new System.Drawing.Point(369, 150);
        this.label13.Name = "label13";
        this.label13.Size = new System.Drawing.Size(104, 16);
        this.label13.TabIndex = 34;
        this.label13.Text = "- Paid Here";
        this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label12
        //
        this.label12.Location = new System.Drawing.Point(369, 176);
        this.label12.Name = "label12";
        this.label12.Size = new System.Drawing.Size(104, 16);
        this.label12.TabIndex = 33;
        this.label12.Text = "= Remaining";
        this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label11
        //
        this.label11.Location = new System.Drawing.Point(346, 130);
        this.label11.Name = "label11";
        this.label11.Size = new System.Drawing.Size(127, 16);
        this.label11.TabIndex = 32;
        this.label11.Text = "- Previously Paid";
        this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label10
        //
        this.label10.Location = new System.Drawing.Point(369, 110);
        this.label10.Name = "label10";
        this.label10.Size = new System.Drawing.Size(104, 16);
        this.label10.TabIndex = 31;
        this.label10.Text = "- Adjustments";
        this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label9
        //
        this.label9.Location = new System.Drawing.Point(369, 90);
        this.label9.Name = "label9";
        this.label9.Size = new System.Drawing.Size(104, 16);
        this.label9.TabIndex = 30;
        this.label9.Text = "- Ins Est";
        this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label8
        //
        this.label8.Location = new System.Drawing.Point(369, 70);
        this.label8.Name = "label8";
        this.label8.Size = new System.Drawing.Size(104, 16);
        this.label8.TabIndex = 29;
        this.label8.Text = "- Ins Paid";
        this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(369, 30);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(104, 16);
        this.label6.TabIndex = 28;
        this.label6.Text = "Fee";
        this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(9, 78);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(104, 16);
        this.label4.TabIndex = 27;
        this.label4.Text = "Provider";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(9, 118);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(104, 16);
        this.label3.TabIndex = 26;
        this.label3.Text = "Description";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(8, 57);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(104, 16);
        this.label2.TabIndex = 25;
        this.label2.Text = "Procedure Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // butDetach
        //
        this.butDetach.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDetach.setAutosize(true);
        this.butDetach.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDetach.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDetach.setCornerRadius(4F);
        this.butDetach.Location = new System.Drawing.Point(99, 21);
        this.butDetach.Name = "butDetach";
        this.butDetach.Size = new System.Drawing.Size(75, 24);
        this.butDetach.TabIndex = 9;
        this.butDetach.Text = "Detach";
        this.butDetach.Click += new System.EventHandler(this.butDetach_Click);
        //
        // butAttach
        //
        this.butAttach.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAttach.setAutosize(true);
        this.butAttach.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAttach.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAttach.setCornerRadius(4F);
        this.butAttach.Location = new System.Drawing.Point(12, 21);
        this.butAttach.Name = "butAttach";
        this.butAttach.Size = new System.Drawing.Size(75, 24);
        this.butAttach.TabIndex = 8;
        this.butAttach.Text = "Attach";
        this.butAttach.Click += new System.EventHandler(this.butAttach_Click);
        //
        // label15
        //
        this.label15.Location = new System.Drawing.Point(1, 24);
        this.label15.Name = "label15";
        this.label15.Size = new System.Drawing.Size(127, 16);
        this.label15.TabIndex = 115;
        this.label15.Text = "Entry Date";
        this.label15.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label17
        //
        this.label17.Location = new System.Drawing.Point(3, 121);
        this.label17.Name = "label17";
        this.label17.Size = new System.Drawing.Size(124, 16);
        this.label17.TabIndex = 116;
        this.label17.Text = "Unearned Type";
        this.label17.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateEntry
        //
        this.textDateEntry.Location = new System.Drawing.Point(129, 22);
        this.textDateEntry.Name = "textDateEntry";
        this.textDateEntry.ReadOnly = true;
        this.textDateEntry.Size = new System.Drawing.Size(92, 20);
        this.textDateEntry.TabIndex = 114;
        //
        // textProcDate
        //
        this.textProcDate.Location = new System.Drawing.Point(129, 70);
        this.textProcDate.Name = "textProcDate";
        this.textProcDate.Size = new System.Drawing.Size(92, 20);
        this.textProcDate.TabIndex = 25;
        //
        // textDatePay
        //
        this.textDatePay.Location = new System.Drawing.Point(129, 46);
        this.textDatePay.Name = "textDatePay";
        this.textDatePay.ReadOnly = true;
        this.textDatePay.Size = new System.Drawing.Size(92, 20);
        this.textDatePay.TabIndex = 22;
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
        this.butDelete.Location = new System.Drawing.Point(48, 496);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(85, 24);
        this.butDelete.TabIndex = 21;
        this.butDelete.Text = "&Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // textAmount
        //
        this.textAmount.Location = new System.Drawing.Point(129, 94);
        this.textAmount.Name = "textAmount";
        this.textAmount.Size = new System.Drawing.Size(77, 20);
        this.textAmount.TabIndex = 1;
        this.textAmount.Validating += new System.ComponentModel.CancelEventHandler(this.textAmount_Validating);
        //
        // butRemainder
        //
        this.butRemainder.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRemainder.setAutosize(true);
        this.butRemainder.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRemainder.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRemainder.setCornerRadius(4F);
        this.butRemainder.Location = new System.Drawing.Point(5, 304);
        this.butRemainder.Name = "butRemainder";
        this.butRemainder.Size = new System.Drawing.Size(92, 24);
        this.butRemainder.TabIndex = 7;
        this.butRemainder.Text = "&Remainder";
        this.butRemainder.Visible = false;
        this.butRemainder.Click += new System.EventHandler(this.butRemainder_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(606, 496);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 5;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // ButCancel
        //
        this.ButCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.ButCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.ButCancel.setAutosize(true);
        this.ButCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.ButCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.ButCancel.setCornerRadius(4F);
        this.ButCancel.Location = new System.Drawing.Point(699, 496);
        this.ButCancel.Name = "ButCancel";
        this.ButCancel.Size = new System.Drawing.Size(75, 24);
        this.ButCancel.TabIndex = 6;
        this.ButCancel.Text = "&Cancel";
        this.ButCancel.Click += new System.EventHandler(this.ButCancel_Click);
        //
        // comboUnearnedTypes
        //
        this.comboUnearnedTypes.FormattingEnabled = true;
        this.comboUnearnedTypes.Location = new System.Drawing.Point(129, 118);
        this.comboUnearnedTypes.Name = "comboUnearnedTypes";
        this.comboUnearnedTypes.Size = new System.Drawing.Size(165, 21);
        this.comboUnearnedTypes.TabIndex = 117;
        //
        // comboProvider
        //
        this.comboProvider.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboProvider.FormattingEnabled = true;
        this.comboProvider.Location = new System.Drawing.Point(129, 143);
        this.comboProvider.Name = "comboProvider";
        this.comboProvider.Size = new System.Drawing.Size(145, 21);
        this.comboProvider.TabIndex = 118;
        //
        // butPickProv
        //
        this.butPickProv.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPickProv.setAutosize(false);
        this.butPickProv.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPickProv.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPickProv.setCornerRadius(2F);
        this.butPickProv.Location = new System.Drawing.Point(276, 143);
        this.butPickProv.Name = "butPickProv";
        this.butPickProv.Size = new System.Drawing.Size(18, 20);
        this.butPickProv.TabIndex = 158;
        this.butPickProv.Text = "...";
        this.butPickProv.Click += new System.EventHandler(this.butPickProv_Click);
        //
        // comboClinic
        //
        this.comboClinic.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        this.comboClinic.FormattingEnabled = true;
        this.comboClinic.Location = new System.Drawing.Point(129, 168);
        this.comboClinic.Name = "comboClinic";
        this.comboClinic.Size = new System.Drawing.Size(165, 21);
        this.comboClinic.TabIndex = 160;
        //
        // labelClinic
        //
        this.labelClinic.Location = new System.Drawing.Point(3, 171);
        this.labelClinic.Name = "labelClinic";
        this.labelClinic.Size = new System.Drawing.Size(124, 16);
        this.labelClinic.TabIndex = 159;
        this.labelClinic.Text = "Clinic";
        this.labelClinic.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormPaySplitEdit
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(801, 541);
        this.Controls.Add(this.comboClinic);
        this.Controls.Add(this.labelClinic);
        this.Controls.Add(this.butPickProv);
        this.Controls.Add(this.comboProvider);
        this.Controls.Add(this.comboUnearnedTypes);
        this.Controls.Add(this.label17);
        this.Controls.Add(this.textDateEntry);
        this.Controls.Add(this.label15);
        this.Controls.Add(this.groupProcedure);
        this.Controls.Add(this.groupPatient);
        this.Controls.Add(this.textProcDate);
        this.Controls.Add(this.textDatePay);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.textAmount);
        this.Controls.Add(this.butRemainder);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.ButCancel);
        this.Controls.Add(this.label7);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.checkPayPlan);
        this.Controls.Add(this.labelAmount);
        this.Controls.Add(this.label5);
        this.Controls.Add(this.labelRemainder);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Location = new System.Drawing.Point(0, 400);
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPaySplitEdit";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Payment Split";
        this.Load += new System.EventHandler(this.FormPaySplitEdit_Load);
        this.groupPatient.ResumeLayout(false);
        this.groupPatient.PerformLayout();
        this.groupProcedure.ResumeLayout(false);
        this.groupProcedure.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPaySplitEdit_Load(Object sender, System.EventArgs e) throws Exception {
        if (PaySplitCur == null)
        {
            MessageBox.Show("Split cannot be null.");
        }
         
        //just for debugging
        textDateEntry.Text = PaySplitCur.DateEntry.ToShortDateString();
        textDatePay.Text = PaySplitCur.DatePay.ToShortDateString();
        textProcDate.Text = PaySplitCur.ProcDate.ToShortDateString();
        textAmount.Text = PaySplitCur.SplitAmt.ToString("F");
        comboUnearnedTypes.Items.Add(Lan.g(this,"none"));
        comboUnearnedTypes.SelectedIndex = 0;
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.PaySplitUnearnedType).ordinal()].Length;i++)
        {
            comboUnearnedTypes.Items.Add(DefC.getShort()[((Enum)DefCat.PaySplitUnearnedType).ordinal()][i].ItemName);
            if (DefC.getShort()[((Enum)DefCat.PaySplitUnearnedType).ordinal()][i].DefNum == PaySplitCur.UnearnedType)
            {
                comboUnearnedTypes.SelectedIndex = i + 1;
            }
             
        }
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            comboProvider.Items.Add(ProviderC.getListShort()[i].Abbr);
            if (ProviderC.getListShort()[i].ProvNum == PaySplitCur.ProvNum)
            {
                comboProvider.SelectedIndex = i;
            }
             
        }
        if (PrefC.getBool(PrefName.EasyNoClinics))
        {
            labelClinic.Visible = false;
            comboClinic.Visible = false;
        }
        else
        {
            comboClinic.Items.Add("none");
            comboClinic.SelectedIndex = 0;
            for (int i = 0;i < Clinics.getList().Length;i++)
            {
                comboClinic.Items.Add(Clinics.getList()[i].Description);
                if (Clinics.getList()[i].ClinicNum == PaySplitCur.ClinicNum)
                {
                    comboClinic.SelectedIndex = i + 1;
                }
                 
            }
        } 
        if (PaySplitCur.ProvNum == 0)
        {
            comboProvider.SelectedIndex = 0;
        }
         
        if (PaySplitCur.PayPlanNum == 0)
        {
            checkPayPlan.Checked = false;
        }
        else
        {
            checkPayPlan.Checked = true;
        } 
        fillPatient();
        fillProcedure();
    }

    private void butRemainder_Click(Object sender, System.EventArgs e) throws Exception {
        textAmount.Text = Remain.ToString("F");
    }

    /**
    * PaySplit.Patient is one value that is always kept in synch with the display.  If program changes PaySplit.Patient, then it will run this method to update the display.  If user changes display, then _MouseDown is run to update the PaySplit.Patient.
    */
    private void fillPatient() throws Exception {
        listPatient.Items.Clear();
        for (int i = 0;i < FamCur.ListPats.Length;i++)
        {
            listPatient.Items.Add(FamCur.getNameInFamLFI(i));
            if (PaySplitCur.PatNum == FamCur.ListPats[i].PatNum)
            {
                listPatient.SelectedIndex = i;
            }
             
        }
        //this can happen if user unchecks the "Is From Other Fam" box. Need to reset.
        if (PaySplitCur.PatNum == 0)
        {
            listPatient.SelectedIndex = 0;
            //the initial patient will be the first patient in the family, usually guarantor
            PaySplitCur.PatNum = FamCur.ListPats[0].PatNum;
        }
         
        if (listPatient.SelectedIndex == -1)
        {
            //patient not in family
            checkPatOtherFam.Checked = true;
            textPatient.Visible = true;
            listPatient.Visible = false;
            textPatient.Text = Patients.getLim(PaySplitCur.PatNum).getNameLF();
        }
        else
        {
            //show the family list that was just filled
            checkPatOtherFam.Checked = false;
            textPatient.Visible = false;
            listPatient.Visible = true;
        } 
    }

    private void checkPatOtherFam_Click(Object sender, System.EventArgs e) throws Exception {
        //this happens after the check change has been registered
        if (checkPatOtherFam.Checked)
        {
            FormPatientSelect FormPS = new FormPatientSelect();
            FormPS.SelectionModeOnly = true;
            FormPS.ShowDialog();
            if (FormPS.DialogResult != DialogResult.OK)
            {
                checkPatOtherFam.Checked = false;
                return ;
            }
             
            PaySplitCur.PatNum = FormPS.SelectedPatNum;
        }
        else
        {
            //switch to family view
            PaySplitCur.PatNum = 0;
        } 
        //this will reset the selected patient to current patient
        fillPatient();
    }

    private void listPatient_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e) throws Exception {
        if (listPatient.SelectedIndex == -1)
        {
            return ;
        }
         
        PaySplitCur.PatNum = FamCur.ListPats[listPatient.SelectedIndex].PatNum;
    }

    private void fillProcedure() throws Exception {
        if (PaySplitCur.ProcNum == 0)
        {
            textProcDate2.Text = "";
            textProcProv.Text = "";
            textProcTooth.Text = "";
            textProcDescription.Text = "";
            ProcFee = 0;
            textProcFee.Text = "";
            ProcWriteoff = 0;
            textProcWriteoff.Text = "";
            ProcInsPaid = 0;
            textProcInsPaid.Text = "";
            ProcInsEst = 0;
            textProcInsEst.Text = "";
            ProcAdj = 0;
            textProcAdj.Text = "";
            ProcPrevPaid = 0;
            textProcPrevPaid.Text = "";
            ProcPaidHere = 0;
            textProcPaidHere.Text = "";
            labelProcRemain.Text = "";
            return ;
        }
         
        //butAttach.Enabled=true;
        //butDetach.Enabled=false;
        //ComputeProcTotals();
        ProcCur = Procedures.getOneProc(PaySplitCur.ProcNum,false);
        List<ClaimProc> ClaimProcList = ClaimProcs.refresh(ProcCur.PatNum);
        Adjustment[] AdjustmentList = Adjustments.refresh(ProcCur.PatNum);
        PaySplit[] PaySplitList = PaySplits.refresh(ProcCur.PatNum);
        //textProcDate.Text=ProcCur.ProcDate.ToShortDateString();
        textProcDate2.Text = ProcCur.ProcDate.ToShortDateString();
        textProcProv.Text = Providers.getAbbr(ProcCur.ProvNum);
        textProcTooth.Text = Tooth.toInternat(ProcCur.ToothNum);
        textProcDescription.Text = ProcedureCodes.getProcCode(ProcCur.CodeNum).Descript;
        ProcFee = ProcCur.ProcFee;
        ProcWriteoff = -ClaimProcs.ProcWriteoff(ClaimProcList, ProcCur.ProcNum);
        ProcInsPaid = -ClaimProcs.ProcInsPay(ClaimProcList, ProcCur.ProcNum);
        ProcInsEst = -ClaimProcs.ProcEstNotReceived(ClaimProcList, ProcCur.ProcNum);
        ProcAdj = Adjustments.GetTotForProc(ProcCur.ProcNum, AdjustmentList);
        //next line will still work even if IsNew
        ProcPrevPaid = -PaySplits.GetTotForProc(ProcCur.ProcNum, PaySplitList, PaySplitCur.SplitNum);
        textProcFee.Text = ProcFee.ToString("F");
        if (ProcWriteoff == 0)
        {
            textProcWriteoff.Text = "";
        }
        else
        {
            textProcWriteoff.Text = ProcWriteoff.ToString("F");
        } 
        if (ProcInsPaid == 0)
        {
            textProcInsPaid.Text = "";
        }
        else
        {
            textProcInsPaid.Text = ProcInsPaid.ToString("F");
        } 
        if (ProcInsEst == 0)
        {
            textProcInsEst.Text = "";
        }
        else
        {
            textProcInsEst.Text = ProcInsEst.ToString("F");
        } 
        if (ProcAdj == 0)
        {
            textProcAdj.Text = "";
        }
        else
        {
            textProcAdj.Text = ProcAdj.ToString("F");
        } 
        if (ProcPrevPaid == 0)
        {
            textProcPrevPaid.Text = "";
        }
        else
        {
            textProcPrevPaid.Text = ProcPrevPaid.ToString("F");
        } 
        computeProcTotals();
    }

    //butAttach.Enabled=false;
    //butDetach.Enabled=true;
    /**
    * Does not alter any of the proc amounts except PaidHere and Remaining.
    */
    private void computeProcTotals() throws Exception {
        ProcPaidHere = 0;
        if (StringSupport.equals(textAmount.errorProvider1.GetError(textAmount), ""))
        {
            ProcPaidHere = -PIn.Double(textAmount.Text);
        }
         
        if (ProcPaidHere == 0)
        {
            textProcPaidHere.Text = "";
        }
        else
        {
            textProcPaidHere.Text = ProcPaidHere.ToString("F");
        } 
        //most of these are negative values, so add
        double remain = ProcFee + ProcWriteoff + ProcInsPaid + ProcInsEst + ProcAdj + ProcPrevPaid + ProcPaidHere;
        labelProcRemain.Text = remain.ToString("c");
    }

    private void textAmount_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        //can not use textAmount_TextChanged without redesigning the validDouble control
        computeProcTotals();
    }

    private void butAttach_Click(Object sender, System.EventArgs e) throws Exception {
        FormProcSelect FormPS = new FormProcSelect(PaySplitCur.PatNum);
        FormPS.ShowDialog();
        if (FormPS.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        PaySplitCur.ProcNum = FormPS.SelectedProcNum;
        fillProcedure();
        textProcDate.Text = ProcCur.ProcDate.ToShortDateString();
    }

    private void butDetach_Click(Object sender, System.EventArgs e) throws Exception {
        PaySplitCur.ProcNum = 0;
        fillProcedure();
    }

    private void checkPayPlan_Click(Object sender, System.EventArgs e) throws Exception {
        if (checkPayPlan.Checked)
        {
            if (checkPatOtherFam.Checked)
            {
                //prevents a bug.
                checkPayPlan.Checked = false;
                return ;
            }
             
            //PayPlan[] planListAll=PayPlans.Refresh(FamCur.List[listPatient.SelectedIndex].PatNum,0);
            List<PayPlan> payPlanList = PayPlans.GetValidPlansNoIns(FamCur.ListPats[listPatient.SelectedIndex].PatNum);
            if (payPlanList.Count == 0)
            {
                //no valid plans
                MsgBox.show(this,"The selected patient is not the guarantor for any payment plans.");
                checkPayPlan.Checked = false;
                return ;
            }
             
            if (payPlanList.Count == 1)
            {
                //if there is only one valid payplan
                PaySplitCur.PayPlanNum = payPlanList[0].PayPlanNum;
                return ;
            }
             
            //more than one valid PayPlan
            List<PayPlanCharge> chargeList = PayPlanCharges.Refresh(FamCur.ListPats[listPatient.SelectedIndex].PatNum);
            FormPayPlanSelect FormPPS = new FormPayPlanSelect(payPlanList, chargeList);
            //FormPPS.ValidPlans=payPlanList;
            FormPPS.ShowDialog();
            if (FormPPS.DialogResult == DialogResult.Cancel)
            {
                checkPayPlan.Checked = false;
                return ;
            }
             
            PaySplitCur.PayPlanNum = payPlanList[FormPPS.IndexSelected].PayPlanNum;
        }
        else
        {
            //payPlan unchecked
            PaySplitCur.PayPlanNum = 0;
        } 
    }

    private void butPickProv_Click(Object sender, EventArgs e) throws Exception {
        FormProviderPick formp = new FormProviderPick();
        if (comboProvider.SelectedIndex > -1)
        {
            formp.SelectedProvNum = ProviderC.getListShort()[comboProvider.SelectedIndex].ProvNum;
        }
         
        formp.ShowDialog();
        if (formp.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        comboProvider.SelectedIndex = Providers.getIndex(formp.SelectedProvNum);
    }

    private void butDelete_Click(Object sender, System.EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Delete Item?"))
        {
            return ;
        }
         
        PaySplitCur = null;
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textAmount.errorProvider1.GetError(textAmount), "") || !StringSupport.equals(textDatePay.errorProvider1.GetError(textDatePay), "") || !StringSupport.equals(textProcDate.errorProvider1.GetError(textProcDate), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        DateTime procDate = PIn.Date(textProcDate.Text);
        if (procDate > DateTime.Today)
        {
            MsgBox.show(this,"Date may not be in the future.");
            return ;
        }
         
        Double amount = PIn.Double(textAmount.Text);
        if (amount == 0)
        {
            MsgBox.show(this,"Please enter an amount");
            return ;
        }
         
        PaySplitCur.DatePay = PIn.Date(textDatePay.Text);
        //gets overwritten anyway
        PaySplitCur.ProcDate = procDate;
        PaySplitCur.SplitAmt = amount;
        if (comboProvider.SelectedIndex > -1)
        {
            //if none selected, we won't silently change
            PaySplitCur.ProvNum = ProviderC.getListShort()[comboProvider.SelectedIndex].ProvNum;
        }
         
        if (comboUnearnedTypes.SelectedIndex == 0)
        {
            PaySplitCur.UnearnedType = 0;
        }
        else
        {
            PaySplitCur.UnearnedType = DefC.getShort()[((Enum)DefCat.PaySplitUnearnedType).ordinal()][comboUnearnedTypes.SelectedIndex - 1].DefNum;
        } 
        if (!PrefC.getBool(PrefName.EasyNoClinics))
        {
            if (comboClinic.SelectedIndex == 0)
            {
                //none
                PaySplitCur.ClinicNum = 0;
            }
            else
            {
                PaySplitCur.ClinicNum = Clinics.getList()[comboClinic.SelectedIndex - 1].ClinicNum;
            } 
        }
         
        //if(!checkPatOtherFam.Checked){
        //This is still needed because it might be zero:
        //	PaySplitCur.PatNum=FamCur.List[listPatient.SelectedIndex].PatNum;
        //}
        //PayPlanNum already handled
        //PaySplitCur.InsertOrUpdate(IsNew);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsNew)
        {
            PaySplitCur = null;
        }
         
        DialogResult = DialogResult.Cancel;
    }

}


