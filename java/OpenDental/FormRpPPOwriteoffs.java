//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormRpPPOwriteoffs;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

//using System.IO;
//using System.Text;
//using System.Xml.Serialization;
/**
* Summary description for FormBasicTemplate.
*/
public class FormRpPPOwriteoffs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private MonthCalendar date2 = new MonthCalendar();
    private MonthCalendar date1 = new MonthCalendar();
    private Label labelTO = new Label();
    private RadioButton radioIndividual = new RadioButton();
    private RadioButton radioGroup = new RadioButton();
    private TextBox textCarrier = new TextBox();
    private Label label1 = new Label();
    private GroupBox groupBox3 = new GroupBox();
    private Label label5 = new Label();
    private RadioButton radioWriteoffProc = new RadioButton();
    private RadioButton radioWriteoffPay = new RadioButton();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRpPPOwriteoffs() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.C("All", new System.Windows.Forms.Control[]{ butOK, butCancel });
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpPPOwriteoffs.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.date2 = new System.Windows.Forms.MonthCalendar();
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.labelTO = new System.Windows.Forms.Label();
        this.radioIndividual = new System.Windows.Forms.RadioButton();
        this.radioGroup = new System.Windows.Forms.RadioButton();
        this.textCarrier = new System.Windows.Forms.TextBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.label5 = new System.Windows.Forms.Label();
        this.radioWriteoffProc = new System.Windows.Forms.RadioButton();
        this.radioWriteoffPay = new System.Windows.Forms.RadioButton();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(625, 330);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(625, 289);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // date2
        //
        this.date2.Location = new System.Drawing.Point(289, 36);
        this.date2.MaxSelectionCount = 1;
        this.date2.Name = "date2";
        this.date2.TabIndex = 30;
        //
        // date1
        //
        this.date1.Location = new System.Drawing.Point(33, 36);
        this.date1.MaxSelectionCount = 1;
        this.date1.Name = "date1";
        this.date1.TabIndex = 29;
        //
        // labelTO
        //
        this.labelTO.Location = new System.Drawing.Point(223, 36);
        this.labelTO.Name = "labelTO";
        this.labelTO.Size = new System.Drawing.Size(51, 23);
        this.labelTO.TabIndex = 31;
        this.labelTO.Text = "TO";
        this.labelTO.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
        //
        // radioIndividual
        //
        this.radioIndividual.Checked = true;
        this.radioIndividual.Location = new System.Drawing.Point(33, 227);
        this.radioIndividual.Name = "radioIndividual";
        this.radioIndividual.Size = new System.Drawing.Size(200, 19);
        this.radioIndividual.TabIndex = 32;
        this.radioIndividual.TabStop = true;
        this.radioIndividual.Text = "Individual Claims";
        this.radioIndividual.UseVisualStyleBackColor = true;
        //
        // radioGroup
        //
        this.radioGroup.Location = new System.Drawing.Point(33, 250);
        this.radioGroup.Name = "radioGroup";
        this.radioGroup.Size = new System.Drawing.Size(200, 19);
        this.radioGroup.TabIndex = 33;
        this.radioGroup.Text = "Group by Carrier";
        this.radioGroup.UseVisualStyleBackColor = true;
        //
        // textCarrier
        //
        this.textCarrier.Location = new System.Drawing.Point(33, 309);
        this.textCarrier.Name = "textCarrier";
        this.textCarrier.Size = new System.Drawing.Size(178, 20);
        this.textCarrier.TabIndex = 34;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(32, 283);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(257, 22);
        this.label1.TabIndex = 35;
        this.label1.Text = "Enter a few letters of the carrier to limit results";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.label5);
        this.groupBox3.Controls.Add(this.radioWriteoffProc);
        this.groupBox3.Controls.Add(this.radioWriteoffPay);
        this.groupBox3.Location = new System.Drawing.Point(289, 227);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(281, 95);
        this.groupBox3.TabIndex = 48;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "Show Insurance Writeoffs";
        //
        // label5
        //
        this.label5.Location = new System.Drawing.Point(6, 71);
        this.label5.Name = "label5";
        this.label5.Size = new System.Drawing.Size(269, 17);
        this.label5.TabIndex = 2;
        this.label5.Text = "(this is discussed in the PPO section of the manual)";
        //
        // radioWriteoffProc
        //
        this.radioWriteoffProc.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.radioWriteoffProc.Location = new System.Drawing.Point(9, 41);
        this.radioWriteoffProc.Name = "radioWriteoffProc";
        this.radioWriteoffProc.Size = new System.Drawing.Size(244, 23);
        this.radioWriteoffProc.TabIndex = 1;
        this.radioWriteoffProc.Text = "Using procedure date.";
        this.radioWriteoffProc.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.radioWriteoffProc.UseVisualStyleBackColor = true;
        //
        // radioWriteoffPay
        //
        this.radioWriteoffPay.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.radioWriteoffPay.Checked = true;
        this.radioWriteoffPay.Location = new System.Drawing.Point(9, 20);
        this.radioWriteoffPay.Name = "radioWriteoffPay";
        this.radioWriteoffPay.Size = new System.Drawing.Size(244, 23);
        this.radioWriteoffPay.TabIndex = 0;
        this.radioWriteoffPay.TabStop = true;
        this.radioWriteoffPay.Text = "Using insurance payment date.";
        this.radioWriteoffPay.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.radioWriteoffPay.UseVisualStyleBackColor = true;
        //
        // FormRpPPOwriteoffs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(725, 379);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textCarrier);
        this.Controls.Add(this.radioGroup);
        this.Controls.Add(this.radioIndividual);
        this.Controls.Add(this.date2);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.labelTO);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpPPOwriteoffs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "PPO Writeoffs";
        this.Load += new System.EventHandler(this.FormRpPPOwriteoffs_Load);
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRpPPOwriteoffs_Load(Object sender, System.EventArgs e) throws Exception {
        date1.SelectionStart = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1)).AddMonths(-1);
        date2.SelectionStart = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1)).AddDays(-1);
        if (PrefC.getBool(PrefName.ReportsPPOwriteoffDefaultToProcDate))
        {
            radioWriteoffProc.Checked = true;
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (radioIndividual.Checked)
        {
            executeIndividual();
        }
        else
        {
            executeGroup();
        } 
    }

    private void executeIndividual() throws Exception {
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.addTitle(Lan.g(this,"PPO WRITEOFFS"));
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        report.AddSubTitle(date1.SelectionStart.ToShortDateString() + " - " + date2.SelectionStart.ToShortDateString());
        report.addSubTitle(Lan.g(this,"Individual Claims"));
        if (!StringSupport.equals(textCarrier.Text, ""))
        {
            report.addSubTitle(Lan.g(this,"Carrier like: ") + textCarrier.Text);
        }
         
        report.setQuery("SET @DateFrom=" + POut.Date(date1.SelectionStart) + ", @DateTo=" + POut.Date(date2.SelectionStart) + ", @CarrierName='%" + POut.String(textCarrier.Text) + "%';");
        if (radioWriteoffPay.Checked)
        {
            report.setQuery(report.getQuery() + "SELECT claimproc.DateCP,\r\n" + 
            "\t\t\t\t\tCONCAT(CONCAT(CONCAT(CONCAT(patient.LName,\', \'),patient.FName),\' \'),patient.MiddleI),\r\n" + 
            "\t\t\t\t\tcarrier.CarrierName,\r\n" + 
            "\t\t\t\t\tprovider.Abbr,\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled-claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tclaimproc.ClaimNum\r\n" + 
            "\t\t\t\t\tFROM claimproc,insplan,patient,carrier,provider\r\n" + 
            "\t\t\t\t\tWHERE provider.ProvNum = claimproc.ProvNum\r\n" + 
            "\t\t\t\t\tAND claimproc.PlanNum = insplan.PlanNum\r\n" + 
            "\t\t\t\t\tAND claimproc.PatNum = patient.PatNum\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierNum = insplan.CarrierNum\r\n" + 
            "\t\t\t\t\tAND (claimproc.Status=1 OR claimproc.Status=4) /*received or supplemental*/\r\n" + 
            "\t\t\t\t\tAND claimproc.DateCP >= @DateFrom\r\n" + 
            "\t\t\t\t\tAND claimproc.DateCP <= @DateTo\r\n" + 
            "\t\t\t\t\tAND insplan.PlanType=\'p\'\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierName LIKE @CarrierName\r\n" + 
            "\t\t\t\t\tGROUP BY claimproc.ClaimNum \r\n" + 
            "\t\t\t\t\tORDER BY claimproc.DateCP");
        }
        else
        {
            //use procedure date
            report.setQuery(report.getQuery() + "SELECT claimproc.ProcDate,\r\n" + 
            "\t\t\t\t\tCONCAT(CONCAT(CONCAT(CONCAT(patient.LName,\', \'),patient.FName),\' \'),patient.MiddleI),\r\n" + 
            "\t\t\t\t\tcarrier.CarrierName,\r\n" + 
            "\t\t\t\t\tprovider.Abbr,\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled-claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tclaimproc.ClaimNum\r\n" + 
            "\t\t\t\t\tFROM claimproc,insplan,patient,carrier,provider\r\n" + 
            "\t\t\t\t\tWHERE provider.ProvNum = claimproc.ProvNum\r\n" + 
            "\t\t\t\t\tAND claimproc.PlanNum = insplan.PlanNum\r\n" + 
            "\t\t\t\t\tAND claimproc.PatNum = patient.PatNum\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierNum = insplan.CarrierNum\r\n" + 
            "\t\t\t\t\tAND (claimproc.Status=1 OR claimproc.Status=4 OR claimproc.Status=0) /*received or supplemental or notreceived*/\r\n" + 
            "\t\t\t\t\tAND claimproc.ProcDate >= @DateFrom\r\n" + 
            "\t\t\t\t\tAND claimproc.ProcDate <= @DateTo\r\n" + 
            "\t\t\t\t\tAND insplan.PlanType=\'p\'\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierName LIKE @CarrierName\r\n" + 
            "\t\t\t\t\tGROUP BY claimproc.ClaimNum \r\n" + 
            "\t\t\t\t\tORDER BY claimproc.ProcDate");
        } 
        report.addColumn("Date",80,FieldValueType.Date);
        report.addColumn("Patient",120,FieldValueType.String);
        report.addColumn("Carrier",150,FieldValueType.String);
        report.addColumn("Provider",60,FieldValueType.String);
        report.addColumn("Stand Fee",80,FieldValueType.Number);
        report.addColumn("PPO Fee",80,FieldValueType.Number);
        report.addColumn("Writeoff",80,FieldValueType.Number);
        if (!report.submitQuery())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void executeGroup() throws Exception {
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.addTitle(Lan.g(this,"PPO WRITEOFFS"));
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        report.AddSubTitle(date1.SelectionStart.ToShortDateString() + " - " + date2.SelectionStart.ToShortDateString());
        report.addSubTitle(Lan.g(this,"Grouped by Carrier"));
        if (!StringSupport.equals(textCarrier.Text, ""))
        {
            report.addSubTitle(Lan.g(this,"Carrier like: ") + textCarrier.Text);
        }
         
        if (radioWriteoffPay.Checked)
        {
            report.setQuery("SET @DateFrom=" + POut.Date(date1.SelectionStart) + ", @DateTo=" + POut.Date(date2.SelectionStart) + ", @CarrierName='%" + POut.String(textCarrier.Text) + "%';" + "SELECT carrier.CarrierName,\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled-claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tclaimproc.ClaimNum\r\n" + 
            "\t\t\t\t\tFROM claimproc,insplan,carrier\r\n" + 
            "\t\t\t\t\tWHERE claimproc.PlanNum = insplan.PlanNum\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierNum = insplan.CarrierNum\r\n" + 
            "\t\t\t\t\tAND (claimproc.Status=1 OR claimproc.Status=4) /*received or supplemental*/\r\n" + 
            "\t\t\t\t\tAND claimproc.DateCP >= @DateFrom\r\n" + 
            "\t\t\t\t\tAND claimproc.DateCP <= @DateTo\r\n" + 
            "\t\t\t\t\tAND insplan.PlanType=\'p\'\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierName LIKE @CarrierName\r\n" + 
            "\t\t\t\t\tGROUP BY carrier.CarrierNum \r\n" + 
            "\t\t\t\t\tORDER BY carrier.CarrierName");
        }
        else
        {
            report.setQuery("SET @DateFrom=" + POut.Date(date1.SelectionStart) + ", @DateTo=" + POut.Date(date2.SelectionStart) + ", @CarrierName='%" + POut.String(textCarrier.Text) + "%';" + "SELECT carrier.CarrierName,\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.FeeBilled-claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tSUM(claimproc.WriteOff),\r\n" + 
            "\t\t\t\t\tclaimproc.ClaimNum\r\n" + 
            "\t\t\t\t\tFROM claimproc,insplan,carrier\r\n" + 
            "\t\t\t\t\tWHERE claimproc.PlanNum = insplan.PlanNum\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierNum = insplan.CarrierNum\r\n" + 
            "\t\t\t\t\tAND (claimproc.Status=1 OR claimproc.Status=4 OR claimproc.Status=0) /*received or supplemental or notreceived*/\r\n" + 
            "\t\t\t\t\tAND claimproc.ProcDate >= @DateFrom\r\n" + 
            "\t\t\t\t\tAND claimproc.ProcDate <= @DateTo\r\n" + 
            "\t\t\t\t\tAND insplan.PlanType=\'p\'\r\n" + 
            "\t\t\t\t\tAND carrier.CarrierName LIKE @CarrierName\r\n" + 
            "\t\t\t\t\tGROUP BY carrier.CarrierNum \r\n" + 
            "\t\t\t\t\tORDER BY carrier.CarrierName");
        } 
        report.addColumn("Carrier",180,FieldValueType.String);
        report.addColumn("Stand Fee",80,FieldValueType.Number);
        report.addColumn("PPO Fee",80,FieldValueType.Number);
        report.addColumn("Writeoff",80,FieldValueType.Number);
        if (!report.submitQuery())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


