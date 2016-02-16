//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.FormRpPHRawProc;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;

/**
* 
*/
public class FormRpPHRawProc  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.MonthCalendar date2 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.MonthCalendar date1 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.Label labelTO = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRpPHRawProc() throws Exception {
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

    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpPHRawProc.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.date2 = new System.Windows.Forms.MonthCalendar();
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.labelTO = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(523, 328);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 4;
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
        this.butOK.Location = new System.Drawing.Point(523, 296);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // date2
        //
        this.date2.Location = new System.Drawing.Point(290, 67);
        this.date2.MaxSelectionCount = 1;
        this.date2.Name = "date2";
        this.date2.TabIndex = 2;
        //
        // date1
        //
        this.date1.Location = new System.Drawing.Point(34, 67);
        this.date1.MaxSelectionCount = 1;
        this.date1.Name = "date1";
        this.date1.TabIndex = 1;
        //
        // labelTO
        //
        this.labelTO.Location = new System.Drawing.Point(214, 75);
        this.labelTO.Name = "labelTO";
        this.labelTO.Size = new System.Drawing.Size(73, 23);
        this.labelTO.TabIndex = 28;
        this.labelTO.Text = "TO";
        this.labelTO.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // FormRpPHRawProc
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(616, 366);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.date2);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.labelTO);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpPHRawProc";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Raw Procedure Data";
        this.Load += new System.EventHandler(this.FormRpPHRawScreen_Load);
        this.ResumeLayout(false);
    }

    private void formRpPHRawScreen_Load(Object sender, System.EventArgs e) throws Exception {
        DateTime today = DateTime.Today;
        //will start out 1st through 30th of previous month
        date1.SelectionStart = (new DateTime(today.Year, today.Month, 1)).AddMonths(-1);
        date2.SelectionStart = (new DateTime(today.Year, today.Month, 1)).AddDays(-1);
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT ScreenDate,ProvName,County,county.CountyCode,\r\n" + 
        "\t\t\t\tGradeSchool,school.SchoolCode,PlaceService,GradeLevel,Age,Birthdate,Race,Gender,Urgency,\r\n" + 
        "\t\t\t\tHasCaries,EarlyChildCaries,CariesExperience,ExistingSealants,NeedsSealants,MissingAllTeeth,\r\n" + 
        "\t\t\t\tComments FROM screen\r\n" + 
        "\t\t\t\tLEFT JOIN school ON screen.GradeSchool=school.SchoolName\r\n" + 
        "\t\t\t\tLEFT JOIN county ON screen.County=county.CountyName\r\n" + 
        "\t\t\t\tWHERE ScreenDate >= " + POut.Date(date1.SelectionStart) + " " + "AND ScreenDate <= " + POut.Date(date2.SelectionStart);
        FormQuery FormQuery2 = new FormQuery(report);
        FormQuery2.textTitle.Text = "RawProcedureData" + DateTime.Today.ToString("MMddyyyy");
        //FormQuery2.IsReport=true;
        //FormQuery2.SubmitReportQuery();
        FormQuery2.submitQuery();
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


