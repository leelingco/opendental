//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:05 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpClaimNotSent;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* 
*/
public class FormRpClaimNotSent  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.RadioButton radioRange = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioSingle = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.MonthCalendar date2 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.MonthCalendar date1 = new System.Windows.Forms.MonthCalendar();
    private System.Windows.Forms.Label labelTO = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpClaimNotSent() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpClaimNotSent.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.panel1 = new System.Windows.Forms.Panel();
        this.radioRange = new System.Windows.Forms.RadioButton();
        this.radioSingle = new System.Windows.Forms.RadioButton();
        this.date2 = new System.Windows.Forms.MonthCalendar();
        this.date1 = new System.Windows.Forms.MonthCalendar();
        this.labelTO = new System.Windows.Forms.Label();
        this.panel1.SuspendLayout();
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
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(523, 294);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // panel1
        //
        this.panel1.Controls.Add(this.radioRange);
        this.panel1.Controls.Add(this.radioSingle);
        this.panel1.Location = new System.Drawing.Point(19, 16);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(104, 60);
        this.panel1.TabIndex = 0;
        //
        // radioRange
        //
        this.radioRange.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioRange.Location = new System.Drawing.Point(8, 32);
        this.radioRange.Name = "radioRange";
        this.radioRange.Size = new System.Drawing.Size(88, 24);
        this.radioRange.TabIndex = 1;
        this.radioRange.Text = "Date Range";
        //
        // radioSingle
        //
        this.radioSingle.Checked = true;
        this.radioSingle.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioSingle.Location = new System.Drawing.Point(8, 8);
        this.radioSingle.Name = "radioSingle";
        this.radioSingle.Size = new System.Drawing.Size(88, 24);
        this.radioSingle.TabIndex = 0;
        this.radioSingle.TabStop = true;
        this.radioSingle.Text = "Single Date";
        this.radioSingle.CheckedChanged += new System.EventHandler(this.radioSingle_CheckedChanged);
        //
        // date2
        //
        this.date2.Location = new System.Drawing.Point(291, 112);
        this.date2.Name = "date2";
        this.date2.TabIndex = 2;
        this.date2.Visible = false;
        //
        // date1
        //
        this.date1.Location = new System.Drawing.Point(35, 112);
        this.date1.Name = "date1";
        this.date1.TabIndex = 1;
        //
        // labelTO
        //
        this.labelTO.Location = new System.Drawing.Point(217, 120);
        this.labelTO.Name = "labelTO";
        this.labelTO.Size = new System.Drawing.Size(70, 23);
        this.labelTO.TabIndex = 16;
        this.labelTO.Text = "TO";
        this.labelTO.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        this.labelTO.Visible = false;
        //
        // FormRpClaimNotSent
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(616, 366);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.date2);
        this.Controls.Add(this.date1);
        this.Controls.Add(this.labelTO);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpClaimNotSent";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Claims Not Sent Report";
        this.Load += new System.EventHandler(this.FormClaimNotSent_Load);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formClaimNotSent_Load(Object sender, System.EventArgs e) throws Exception {
        date1.SelectionStart = DateTime.Today;
        date2.SelectionStart = DateTime.Today;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT claim.dateservice,claim.claimnum,claim.claimtype,claim.claimstatus," + "CONCAT(CONCAT(CONCAT(CONCAT(patient.LName,', '),patient.FName),' '),patient.MiddleI),carrier.CarrierName,claim.claimfee " + "FROM patient,claim,insplan,carrier " + "WHERE patient.patnum=claim.patnum AND insplan.plannum=claim.plannum " + "AND insplan.CarrierNum=carrier.CarrierNum " + "AND (claim.claimstatus = 'U' OR claim.claimstatus = 'H' OR  claim.claimstatus = 'W')";
        if (radioRange.Checked == true)
        {
            report.Query += " AND claim.dateservice >= '" + date1.SelectionStart.ToString("yyyy-MM-dd") + "' " + "AND claim.dateservice <= '" + date2.SelectionStart.ToString("yyyy-MM-dd") + "'";
        }
        else
        {
            report.Query += " AND claim.dateservice = '" + date1.SelectionStart.ToString("yyyy-MM-dd") + "'";
        } 
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        DataTable tempT = report.getTempTable();
        report.TableQ = new DataTable(null);
        for (int i = 0;i < 6;i++)
        {
            //new table no name
            //add columns
            report.TableQ.Columns.Add(new System.Data.DataColumn());
        }
        //blank columns
        report.initializeColumns();
        for (int i = 0;i < tempT.Rows.Count;i++)
        {
            //loop through data rows
            DataRow row = report.TableQ.NewRow();
            //create new row called 'row' based on structure of TableQ
            row[0] = (PIn.Date(tempT.Rows[i][0].ToString())).ToShortDateString();
            //claim dateservice
            if (StringSupport.equals(PIn.String(tempT.Rows[i][2].ToString()), "P"))
                row[1] = "Primary";
             
            if (StringSupport.equals(PIn.String(tempT.Rows[i][2].ToString()), "S"))
                row[1] = "Secondary";
             
            if (StringSupport.equals(PIn.String(tempT.Rows[i][2].ToString()), "PreAuth"))
                row[1] = "PreAuth";
             
            if (StringSupport.equals(PIn.String(tempT.Rows[i][2].ToString()), "Other"))
                row[1] = "Other";
             
            if (tempT.Rows[i][3].ToString().Equals("H"))
                row[2] = "Holding";
            else //Claim Status
            if (tempT.Rows[i][3].ToString().Equals("W"))
                row[2] = "WaitQ";
            else
                //Claim Status, added SPK 7/15/04
                row[2] = "Unsent";  
            //Claim Status
            row[3] = tempT.Rows[i][4];
            //Patient name
            row[4] = tempT.Rows[i][5];
            //Ins Carrier
            row[5] = PIn.Double(tempT.Rows[i][6].ToString()).ToString("F");
            //claim fee
            report.ColTotal[5] += PIn.Decimal(tempT.Rows[i][6].ToString());
            report.TableQ.Rows.Add(row);
        }
        FormQuery2.resetGrid();
        //this is a method in FormQuery2;
        report.Title = "Claims Not Sent";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        if (radioRange.Checked == true)
        {
            report.SubTitle.Add(date1.SelectionStart.ToString("d") + " - " + date2.SelectionStart.ToString("d"));
        }
        else
        {
            report.SubTitle.Add(date1.SelectionStart.ToString("d"));
        } 
        report.getColPos()[0] = 20;
        report.getColPos()[1] = 145;
        report.getColPos()[2] = 270;
        report.getColPos()[3] = 395;
        report.getColPos()[4] = 520;
        report.getColPos()[5] = 645;
        report.getColPos()[6] = 770;
        report.getColCaption()[0] = "Date";
        report.getColCaption()[1] = "Type";
        report.getColCaption()[2] = "Claim Status";
        report.getColCaption()[3] = "Patient Name";
        report.getColCaption()[4] = "Insurance Carrier";
        report.getColCaption()[5] = "Amount";
        report.getColAlign()[5] = HorizontalAlignment.Right;
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void radioSingle_CheckedChanged(Object sender, System.EventArgs e) throws Exception {
        if (radioSingle.Checked == true)
        {
            date2.Visible = false;
            labelTO.Visible = false;
        }
        else
        {
            date2.Visible = true;
            labelTO.Visible = true;
        } 
    }

}


