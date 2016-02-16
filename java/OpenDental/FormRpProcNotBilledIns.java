//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.FormRpProcNotBilledIns;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* 
*/
public class FormRpProcNotBilledIns  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.MonthCalendar date2 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.MonthCalendar date1 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.Label labelTO = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.Container components = null;
    private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpProcNotBilledIns() throws Exception {
        initializeComponent();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpProcNotBilledIns.class);
        this.date2 = new System.Windows.Forms.MonthCalendar();
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.labelTO = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // date2
        //
        this.date2.Location = new System.Drawing.Point(288, 112);
        this.date2.Name = "date2";
        this.date2.TabIndex = 2;
        //
        // date1
        //
        this.date1.Location = new System.Drawing.Point(32, 112);
        this.date1.Name = "date1";
        this.date1.TabIndex = 1;
        //
        // labelTO
        //
        this.labelTO.Location = new System.Drawing.Point(213, 120);
        this.labelTO.Name = "labelTO";
        this.labelTO.Size = new System.Drawing.Size(76, 23);
        this.labelTO.TabIndex = 10;
        this.labelTO.Text = "TO";
        this.labelTO.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(520, 328);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 4;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(520, 292);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormRpProcNotBilledIns
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(616, 366);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.date2);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.labelTO);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpProcNotBilledIns";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedures Not Billed to Insurance";
        this.Load += new System.EventHandler(this.FormProcNotAttach_Load);
        this.ResumeLayout(false);
    }

    private void formProcNotAttach_Load(Object sender, System.EventArgs e) throws Exception {
        date1.SelectionStart = DateTime.Today;
        date2.SelectionStart = DateTime.Today;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ReportSimpleGrid report = new ReportSimpleGrid();
        //if(radioRange.Checked){
        report.Query = "SELECT ";
        if (PrefC.getBool(PrefName.ReportsShowPatNum))
        {
            report.Query += DbHelper.concat("CAST(patient.PatNum AS CHAR)","'-'","patient.LName","', '","patient.FName","' '","patient.MiddleI");
        }
        else
        {
            report.Query += DbHelper.concat("patient.LName","', '","patient.FName","' '","patient.MiddleI");
        } 
        //estimate
        report.Query += " AS 'PatientName',procedurelog.ProcDate,procedurecode.Descript,procedurelog.ProcFee " + "FROM patient,procedurecode,procedurelog,claimproc,insplan " + "WHERE claimproc.procnum=procedurelog.procnum " + "AND patient.PatNum=procedurelog.PatNum " + "AND procedurelog.CodeNum=procedurecode.CodeNum " + "AND claimproc.PlanNum=insplan.PlanNum " + "AND insplan.IsMedical=0 " + "AND claimproc.NoBillIns=0 " + "AND procedurelog.ProcFee>0 " + "AND claimproc.Status=6 " + "AND procedurelog.procstatus=2 " + "AND procedurelog.ProcDate >= " + POut.Date(date1.SelectionStart) + " " + "AND procedurelog.ProcDate <= " + POut.Date(date2.SelectionStart) + " " + "GROUP BY procedurelog.ProcNum " + "ORDER BY patient.LName,patient.FName";
        /*}
        			else{
        				report.Query="SELECT CONCAT(patient.LName,', ',patient.FName,' ',patient.MiddleI),"
        					+"procedurelog.ProcDate,procedurecode.Descript,procedurelog.ProcFee FROM patient,procedurecode,"
        					+"procedurelog LEFT JOIN claimproc ON claimproc.procnum = procedurelog.procnum "
        					+"WHERE claimproc.procnum IS NULL "
        					+"&& patient.patnum=procedurelog.patnum && procedurelog.codenum=procedurecode.codenum "
        					+"&& patient.priplannum > 0 "
        					+"&& procedurelog.nobillins = 0 && procedurelog.procstatus = 2 "
        					+"&& procedurelog.ProcDate = '" + date1.SelectionStart.ToString("yyyy-MM-dd")+"'";
        			}*/
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "Procedures Not Billed to Insurance";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.SubTitle.Add(date1.SelectionStart.ToString("d") + " - " + date2.SelectionStart.ToString("d"));
        report.setColumn(this,0,"Patient Name",185);
        report.setColumn(this,1,"Procedure Date",185);
        report.setColumn(this,2,"Procedure Description",185);
        report.SetColumn(this, 3, "Procedure Amount", 185, HorizontalAlignment.Right);
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


