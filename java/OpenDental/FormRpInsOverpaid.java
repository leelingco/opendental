//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:06 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.FormRpInsOverpaid;
import OpenDental.Lan;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* 
*/
public class FormRpInsOverpaid  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private Label label1 = new Label();
    private RadioButton radioGroupByProc = new RadioButton();
    private RadioButton radioGroupByPat = new RadioButton();
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRpInsOverpaid() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpInsOverpaid.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.radioGroupByProc = new System.Windows.Forms.RadioButton();
        this.radioGroupByPat = new System.Windows.Forms.RadioButton();
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
        this.butCancel.Location = new System.Drawing.Point(336, 131);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 19;
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
        this.butOK.Location = new System.Drawing.Point(240, 131);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 18;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 20);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(390, 64);
        this.label1.TabIndex = 20;
        this.label1.Text = "Helps find situations where the insurance payment plus any writeoff exceeds the f" + "ee.  See the manual for suggestions on how to handle the results.";
        //
        // radioGroupByProc
        //
        this.radioGroupByProc.Checked = true;
        this.radioGroupByProc.Location = new System.Drawing.Point(72, 71);
        this.radioGroupByProc.Name = "radioGroupByProc";
        this.radioGroupByProc.Size = new System.Drawing.Size(160, 17);
        this.radioGroupByProc.TabIndex = 21;
        this.radioGroupByProc.TabStop = true;
        this.radioGroupByProc.Text = "Group by procedure (default)";
        this.radioGroupByProc.UseVisualStyleBackColor = true;
        //
        // radioGroupByPat
        //
        this.radioGroupByPat.Location = new System.Drawing.Point(72, 94);
        this.radioGroupByPat.Name = "radioGroupByPat";
        this.radioGroupByPat.Size = new System.Drawing.Size(160, 17);
        this.radioGroupByPat.TabIndex = 21;
        this.radioGroupByPat.Text = "Group by patient and date";
        this.radioGroupByPat.UseVisualStyleBackColor = true;
        //
        // FormRpInsOverpaid
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(436, 171);
        this.Controls.Add(this.radioGroupByPat);
        this.Controls.Add(this.radioGroupByProc);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpInsOverpaid";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Overpaid Report";
        this.Load += new System.EventHandler(this.FormRpInsOverpaid_Load);
        this.ResumeLayout(false);
    }

    private void formRpInsOverpaid_Load(Object sender, System.EventArgs e) throws Exception {
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        Cursor = Cursors.WaitCursor;
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT procedurelog.PatNum," + DbHelper.concat("patient.LName","', '","patient.FName") + " patname,\r\n" + 
        "procedurelog.ProcDate,\r\n" + 
        "SUM(procedurelog.ProcFee) \"$sumfee\",\r\n" + 
        "SUM((SELECT SUM(claimproc.InsPayAmt + claimproc.Writeoff) FROM claimproc WHERE claimproc.ProcNum=procedurelog.ProcNum)) AS\r\n" + 
        "\"$PaidAndWriteoff\"\r\n" + 
        "FROM procedurelog\r\n" + 
        "LEFT JOIN procedurecode ON procedurelog.CodeNum=procedurecode.CodeNum\r\n" + 
        "LEFT JOIN patient ON patient.PatNum=procedurelog.PatNum\r\n" + 
        "WHERE procedurelog.ProcStatus=2/*complete*/\r\n" + 
        "AND procedurelog.ProcFee > 0";
        //Negative proc fees should not show up on this report.  We have one office that uses negative proc fees as internal adjustments.
        if (radioGroupByProc.Checked)
        {
            report.Query += "\r\n" + 
            "GROUP BY procedurelog.ProcNum";
        }
        else if (radioGroupByPat.Checked)
        {
            report.Query += "\r\n" + 
            "GROUP BY procedurelog.PatNum,procedurelog.ProcDate";
        }
          
        report.Query += "\r\n" + 
        "HAVING ROUND($sumfee,3) < ROUND($PaidAndWriteoff,3)\r\n" + 
        "ORDER BY patname,ProcDate";
        FormQuery FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "INSURANCE OVERPAID REPORT";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.setColumn(this,0,"PatNum",60);
        report.setColumn(this,1,"Pat Name",150);
        report.setColumn(this,2,"Date",80);
        report.SetColumn(this, 3, "Fee", 80, HorizontalAlignment.Right);
        report.SetColumn(this, 4, "InsPd+W/O", 90, HorizontalAlignment.Right);
        Cursor = Cursors.Default;
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


