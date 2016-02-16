//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormRpPayPlans;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Reports;

/**
* Summary description for FormRpApptWithPhones.
*/
public class FormRpPayPlans  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    //private int pagesPrinted;
    private ErrorProvider errorProvider1 = new ErrorProvider();
    //private DataTable BirthdayTable;
    //private int patientsPrinted;
    //private PrintDocument pd;
    //private OpenDental.UI.PrintPreview printPreview;
    /**
    * 
    */
    public FormRpPayPlans() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpPayPlans.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(546, 216);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 44;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(546, 176);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 43;
        this.butOK.Text = "Report";
        this.butOK.Click += new System.EventHandler(this.butReport_Click);
        //
        // FormRpPayPlans
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(660, 264);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpPayPlans";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Payment Plans Report";
        this.Load += new System.EventHandler(this.FormRpPayPlans_Load);
        this.ResumeLayout(false);
    }

    private void formRpPayPlans_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void butReport_Click(Object sender, System.EventArgs e) throws Exception {
        //if(errorProvider1.GetError(textDateFrom) != ""
        //	|| errorProvider1.GetError(textDateTo) != "")
        //{
        //	MsgBox.Show(this,"Please fix data entry errors first.");
        //	return;
        //}
        //DateTime dateFrom=PIn.PDate(textDateFrom.Text);
        //DateTime dateTo=PIn.PDate(textDateTo.Text);
        //if(dateTo < dateFrom)
        //{
        //	MsgBox.Show(this,"To date cannot be before From date.");
        //	return;
        //}
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.setReportName(Lan.g(this,"PaymentPlans"));
        report.addTitle(Lan.g(this,"Payment Plans"));
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        report.AddSubTitle(DateTime.Today.ToShortDateString());
        DataTable table = new DataTable();
        //table.Columns.Add("date");
        table.Columns.Add("guarantor");
        table.Columns.Add("ins");
        table.Columns.Add("princ");
        table.Columns.Add("paid");
        table.Columns.Add("due");
        table.Columns.Add("dueTen");
        DataRow row = new DataRow();
        String datesql = "CURDATE()";
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.Oracle)
        {
            datesql = "(SELECT CURRENT_DATE FROM dual)";
        }
         
        String command = "SELECT FName,LName,MiddleI,PlanNum,Preferred,\r\n" + 
        "\t\t\t\t(SELECT SUM(Principal+Interest) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum\r\n" + 
        "\t\t\t\tAND ChargeDate <= " + datesql + ") \"_accumDue\",\r\n" + 
        "\t\t\t\t(SELECT SUM(Principal+Interest) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum\r\n" + 
        "\t\t\t\tAND ChargeDate <= " + DbHelper.DateAddDay(datesql, POut.Long(PrefC.getLong(PrefName.PayPlansBillInAdvanceDays))) + ") \"_dueTen\",\r\n" + 
        "\t\t\t\t(SELECT SUM(SplitAmt) FROM paysplit WHERE paysplit.PayPlanNum=payplan.PayPlanNum) \"_paid\",\r\n" + 
        "\t\t\t\t(SELECT SUM(Principal) FROM payplancharge WHERE payplancharge.PayPlanNum=payplan.PayPlanNum) \"_principal\"\r\n" + 
        "\t\t\t\tFROM payplan\r\n" + 
        "\t\t\t\tLEFT JOIN patient ON patient.PatNum=payplan.Guarantor " + "GROUP BY FName,LName,MiddleI,Preferred,payplan.PayPlanNum ORDER BY LName,FName";
        //WHERE SUBSTRING(Birthdate,6,5) >= '"+dateFrom.ToString("MM-dd")+"' "
        //+"AND SUBSTRING(Birthdate,6,5) <= '"+dateTo.ToString("MM-dd")+"' "
        DataTable raw = Reports.getTable(command);
        //DateTime payplanDate;
        Patient pat;
        double princ = new double();
        double paid = new double();
        double accumDue = new double();
        double dueTen = new double();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            princ = PIn.Double(raw.Rows[i]["_principal"].ToString());
            paid = PIn.Double(raw.Rows[i]["_paid"].ToString());
            accumDue = PIn.Double(raw.Rows[i]["_accumDue"].ToString());
            dueTen = PIn.Double(raw.Rows[i]["_dueTen"].ToString());
            row = table.NewRow();
            //payplanDate=PIn.PDate(raw.Rows[i]["PayPlanDate"].ToString());
            //row["date"]=raw.Rows[i]["PayPlanDate"].ToString();//payplanDate.ToShortDateString();
            pat = new Patient();
            pat.LName = raw.Rows[i]["LName"].ToString();
            pat.FName = raw.Rows[i]["FName"].ToString();
            pat.MiddleI = raw.Rows[i]["MiddleI"].ToString();
            pat.Preferred = raw.Rows[i]["Preferred"].ToString();
            row["guarantor"] = pat.getNameLF();
            if (StringSupport.equals(raw.Rows[i]["PlanNum"].ToString(), "0"))
            {
                row["ins"] = "";
            }
            else
            {
                row["ins"] = "X";
            } 
            row["princ"] = princ.ToString("f");
            row["paid"] = paid.ToString("f");
            row["due"] = (accumDue - paid).ToString("f");
            row["dueTen"] = (dueTen - paid).ToString("f");
            table.Rows.Add(row);
        }
        report.setReportTable(table);
        //report.AddColumn("Date",90,FieldValueType.Date);
        report.addColumn("Guarantor",160,FieldValueType.String);
        report.addColumn("Ins",40,FieldValueType.String);
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleCenter);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleCenter);
        report.addColumn("Princ",100,FieldValueType.String);
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleRight);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleRight);
        report.addColumn("Paid",100,FieldValueType.String);
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleRight);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleRight);
        report.addColumn("Due Now",100,FieldValueType.String);
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleRight);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleRight);
        report.addColumn("Due in " + PrefC.getLong(PrefName.PayPlansBillInAdvanceDays).ToString() + " Days",100,FieldValueType.String);
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleRight);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleRight);
        //report.GetLastRO(ReportObjectKind.FieldObject).FormatString="d";
        report.addPageNum();
        //report.SubmitQuery();
        //report.ReportTable=Patients.GetBirthdayList(dateFrom,dateTo);
        //if(!report.SubmitQuery()){
        //	return;
        //}
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


