//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:05 PM
//

package OpenDental;

import OpenDental.FormRpCapitation;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

//using System.IO;
//using System.Text;
//using System.Xml.Serialization;
/**
* Summary description for FormBasicTemplate.
*/
public class FormRpCapitation  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRpCapitation() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpCapitation.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
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
        this.butCancel.Location = new System.Drawing.Point(510, 338);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
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
        this.butOK.Location = new System.Drawing.Point(510, 297);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormRpCapitation
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(605, 386);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpCapitation";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Capitation Utilization Report";
        this.Load += new System.EventHandler(this.FormRpCapitation_Load);
        this.ResumeLayout(false);
    }

    private void formRpCapitation_Load(Object sender, System.EventArgs e) throws Exception {
        //the user never sees this dialog.
        executeReport();
        Close();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
    }

    private void executeReport() throws Exception {
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.setIsLandscape(true);
        report.addTitle("CAPITATION UTILIZATION");
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        //incomplete: Need more flexible default values, eg based on current date instead of fixed date:
        DateTime DateTimeFirst = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1);
        report.addParameter("carrier",FieldValueType.String,"","Enter a few letters of the name of the insurance carrier","carrier.CarrierName LIKE '%?%'");
        // SPK 8/04
        report.addParameter("date1",FieldValueType.Date,DateTimeFirst,"From Date","procedurelog.ProcDate >= '?'");
        report.addParameter("date2",FieldValueType.Date,DateTimeFirst.AddMonths(1).AddDays(-1),"To Date","procedurelog.ProcDate <= '?'");
        // added carrierNum, SPK
        report.setQuery("SELECT carrier.CarrierName,CONCAT(CONCAT(patSub.LName,\', \'),patSub.FName) \r\n" + 
        "\t\t\t\t,patSub.SSN,CONCAT(CONCAT(patPat.LName,\', \'),patPat.FName)\r\n" + 
        "\t\t\t\t,patPat.Birthdate,procedurecode.ProcCode,procedurecode.Descript\r\n" + 
        "\t\t\t\t,procedurelog.ToothNum,procedurelog.Surf,procedurelog.ProcDate\r\n" + 
        "\t\t\t\t,procedurelog.ProcFee,procedurelog.ProcFee-claimproc.WriteOff\r\n" + 
        "\t\t\t\tFROM procedurelog,patient AS patSub,patient AS patPat\r\n" + 
        "\t\t\t\t,insplan,inssub,carrier,procedurecode,claimproc\r\n" + 
        "\t\t\t\tWHERE procedurelog.PatNum = patPat.PatNum\r\n" + 
        "\t\t\t\tAND claimproc.InsSubNum = inssub.InsSubNum\r\n" + 
        "\t\t\t\tAND procedurelog.ProcNum = claimproc.ProcNum\r\n" + 
        "\t\t\t\tAND claimproc.PlanNum = insplan.PlanNum\r\n" + 
        "\t\t\t\tAND claimproc.Status = 7\r\n" + 
        "\t\t\t\tAND claimproc.NoBillIns = 0 \r\n" + 
        "\t\t\t\tAND inssub.Subscriber = patSub.PatNum\r\n" + 
        "\t\t\t\tAND insplan.CarrierNum = carrier.CarrierNum\t\r\n" + 
        "\t\t\t\tAND procedurelog.CodeNum = procedurecode.CodeNum\r\n" + 
        "\t\t\t\tAND ?carrier\r\n" + 
        "\t\t\t\tAND ?date1\r\n" + 
        "\t\t\t\tAND ?date2\r\n" + 
        "\t\t\t\tAND insplan.PlanType = \'c\'\r\n" + 
        "\t\t\t\tAND procedurelog.ProcStatus = 2");
        report.addColumn("Carrier",150,FieldValueType.String);
        report.getLastRO(ReportObjectKind.FieldObject).setSuppressIfDuplicate(true);
        report.addColumn("Subscriber",120,FieldValueType.String);
        report.getLastRO(ReportObjectKind.FieldObject).setSuppressIfDuplicate(true);
        report.addColumn("Subsc SSN",70,FieldValueType.String);
        report.getLastRO(ReportObjectKind.FieldObject).setSuppressIfDuplicate(true);
        report.addColumn("Patient",120,FieldValueType.String);
        report.addColumn("Pat DOB",80,FieldValueType.Date);
        report.addColumn("Code",50,FieldValueType.String);
        report.addColumn("Proc Description",120,FieldValueType.String);
        report.addColumn("Tth",30,FieldValueType.String);
        report.addColumn("Surf",40,FieldValueType.String);
        report.addColumn("Date",80,FieldValueType.Date);
        report.addColumn("UCR Fee",70,FieldValueType.Number);
        report.addColumn("Co-Pay",70,FieldValueType.Number);
        report.addPageNum();
        if (!report.submitQuery())
        {
            DialogResult = DialogResult.Cancel;
            return ;
        }
         
        //incomplete: Add functionality for using parameter values in textObjects, probably using inline XML:
        report.AddSubTitle(((DateTime)report.getParameterFields().get___idx("date1").getCurrentValues()[0]).ToShortDateString() + " - " + ((DateTime)report.getParameterFields().get___idx("date2").getCurrentValues()[0]).ToShortDateString());
        for (int i = 0;i < report.getReportTable().Rows.Count;i++)
        {
            //incomplete: Implement formulas for situations like this:
            if (PIn.Double(report.getReportTable().Rows[i][11].ToString()) == -1)
            {
                report.getReportTable().Rows[i][11] = "0";
            }
             
        }
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        //FormR.MyReport=report;
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


