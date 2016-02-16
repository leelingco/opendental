//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.FormRpPHRawPop;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;

/**
* 
*/
public class FormRpPHRawPop  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.MonthCalendar date2 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.MonthCalendar date1 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.Label labelTO = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listAdjType = new System.Windows.Forms.ListBox();
    private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpPHRawPop() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpPHRawPop.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.date2 = new System.Windows.Forms.MonthCalendar();
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.labelTO = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.listAdjType = new System.Windows.Forms.ListBox();
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
        this.butCancel.Location = new System.Drawing.Point(665, 392);
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
        this.butOK.Location = new System.Drawing.Point(665, 360);
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
        this.labelTO.Location = new System.Drawing.Point(217, 75);
        this.labelTO.Name = "labelTO";
        this.labelTO.Size = new System.Drawing.Size(69, 23);
        this.labelTO.TabIndex = 28;
        this.labelTO.Text = "TO";
        this.labelTO.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(537, 24);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(195, 43);
        this.label1.TabIndex = 29;
        this.label1.Text = "Adjustment types for broken appointments (hold down the control key to select mul" + "tiple)";
        //
        // listAdjType
        //
        this.listAdjType.Location = new System.Drawing.Point(538, 67);
        this.listAdjType.Name = "listAdjType";
        this.listAdjType.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listAdjType.Size = new System.Drawing.Size(149, 238);
        this.listAdjType.TabIndex = 30;
        //
        // FormRpPHRawPop
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(765, 440);
        this.Controls.Add(this.listAdjType);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.date2);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.labelTO);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpPHRawPop";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Raw Population Data";
        this.Load += new System.EventHandler(this.FormRpPHRawPop_Load);
        this.ResumeLayout(false);
    }

    private void formRpPHRawPop_Load(Object sender, System.EventArgs e) throws Exception {
        DateTime today = DateTime.Today;
        //will start out 1st through 30th of previous month
        date1.SelectionStart = (new DateTime(today.Year, today.Month, 1)).AddMonths(-1);
        date2.SelectionStart = (new DateTime(today.Year, today.Month, 1)).AddDays(-1);
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()].Length;i++)
        {
            listAdjType.Items.Add(DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()][i].ItemName);
        }
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (listAdjType.SelectedIndices.Count == 0)
        {
            MessageBox.Show("At least one adjustment type must be selected.");
            return ;
        }
         
        ReportSimpleGrid report = new ReportSimpleGrid();
        String types = "";
        for (int i = 0;i < listAdjType.SelectedIndices.Count;i++)
        {
            if (i == 0)
            {
                types += "(";
            }
            else
            {
                types += "OR ";
            } 
            types += "AdjType='" + DefC.getShort()[((Enum)DefCat.AdjTypes).ordinal()][listAdjType.SelectedIndices[i]].DefNum.ToString() + "' ";
        }
        types += ")";
        report.Query = "\r\n" + 
        "\t\t\t\tCREATE TEMPORARY TABLE tempbroken(\r\n" + 
        "\t\t\t\t\tPatNum bigint unsigned NOT NULL,\r\n" + 
        "\t\t\t\t\tNumberBroken smallint NOT NULL,\r\n" + 
        "\t\t\t\t\tPRIMARY KEY (PatNum)\r\n" + 
        "\t\t\t\t);\r\n" + 
        "\t\t\t\tINSERT INTO tempbroken SELECT PatNum,COUNT(*)\r\n" + 
        "\t\t\t\tFROM adjustment WHERE " + types + "AND AdjDate >= " + POut.Date(date1.SelectionStart) + " " + "AND AdjDate <= " + POut.Date(date2.SelectionStart) + " " + "GROUP BY PatNum;\r\n" + 
        "\t\t\t\tSELECT patient.PatNum,MIN(procedurelog.ProcDate) AS ProcDate,\r\n" + 
        "\t\t\t\tCONCAT(CONCAT(provider.LName,\', \'),provider.FName) as ProvName,\r\n" + 
        "\t\t\t\tCounty,county.CountyCode,\r\n" + 
        "\t\t\t\tsite.Description AS gradeschool,site.Note AS schoolCode,GradeLevel,Birthdate,Race,Gender,Urgency,BillingType,\r\n" + 
        "\t\t\t\tpatient.PlannedIsDone,tempbroken.NumberBroken\r\n" + 
        "\t\t\t\tFROM patient\r\n" + 
        "\t\t\t\tLEFT JOIN procedurelog ON procedurelog.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tLEFT JOIN provider ON procedurelog.ProvNum=provider.ProvNum\r\n" + 
        "\t\t\t\tLEFT JOIN site ON patient.SiteNum=site.SiteNum\r\n" + 
        "\t\t\t\tLEFT JOIN county ON patient.County=county.CountyName\r\n" + 
        "\t\t\t\tLEFT JOIN tempbroken ON tempbroken.PatNum=patient.PatNum\r\n" + 
        "\t\t\t\tWHERE\t(procedurelog.ProcStatus=\'2\'\r\n" + 
        "\t\t\t\tAND procedurelog.ProcDate >= " + POut.Date(date1.SelectionStart) + " " + "AND procedurelog.ProcDate <= " + POut.Date(date2.SelectionStart) + " )" + "OR tempbroken.NumberBroken>0 " + "GROUP BY patient.PatNum\r\n" + 
        "\t\t\t\tORDER By ProcDate;\r\n" + 
        "\t\t\t\tDROP TABLE tempbroken;";
        /*
        CREATE TEMPORARY TABLE tempbroken(
          PatNum mediumint unsigned NOT NULL,
          NumberBroken smallint NOT NULL,
          PRIMARY KEY (PatNum)
        );
        INSERT INTO tempbroken
        SELECT PatNum,COUNT(*)
        FROM adjustment
        WHERE AdjType='14'
        && AdjDate='2004-05-03'
        GROUP BY PatNum;
        SELECT MIN(procedurelog.ProcDate) AS ProcDate,
        CONCAT(provider.LName,', ',provider.FName) as ProvName,
        County,county.CountyCode,
        GradeSchool,school.SchoolCode,GradeLevel,Birthdate,Race,Gender,Urgency,BillingType,
        patient.NextAptNum='-1' AS Done,tempbroken.NumberBroken
        FROM patient,procedurelog,provider,tempbroken
        LEFT JOIN school ON patient.GradeSchool=school.SchoolName
        LEFT JOIN county ON patient.County=county.CountyName
        WHERE procedurelog.ProcStatus='2'
        && patient.PatNum=procedurelog.PatNum
        && procedurelog.ProvNum=provider.ProvNum
        && tempbroken.PatNum=patient.PatNum
        && procedurelog.ProcDate >= '2004-05-03'
        && procedurelog.ProcDate <= '2004-05-03'
        GROUP BY procedurelog.PatNum
        ORDER By ProcDate;
        DROP TABLE tempbroken;
        */
        FormQuery2 = new FormQuery(report);
        FormQuery2.textTitle.Text = "RawPopulationData" + DateTime.Today.ToString("MMddyyyy");
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


