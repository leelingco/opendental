//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:06 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.FormRpFinanceCharge;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* 
*/
public class FormRpFinanceCharge  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.ComponentModel.Container components = null;
    private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpFinanceCharge() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpFinanceCharge.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.textDate = new OpenDental.ValidDate();
        this.label1 = new System.Windows.Forms.Label();
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
        this.butCancel.Location = new System.Drawing.Point(336, 176);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 19;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(336, 142);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 18;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(154, 62);
        this.textDate.Name = "textDate";
        this.textDate.ReadOnly = true;
        this.textDate.Size = new System.Drawing.Size(116, 20);
        this.textDate.TabIndex = 16;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 66);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(136, 14);
        this.label1.TabIndex = 17;
        this.label1.Text = "Date of Charges";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // FormRpFinanceCharge
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(436, 230);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpFinanceCharge";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Finance Charge Report";
        this.Load += new System.EventHandler(this.FormRpFinanceCharge_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formRpFinanceCharge_Load(Object sender, System.EventArgs e) throws Exception {
        textDate.Text = PrefC.getDate(PrefName.FinanceChargeLastRun).ToShortDateString();
    }

    /*if(DateTime.Today.Day > 15){
    				if(DateTime.Today.Month!=12){
    					textDate.Text=(new DateTime(DateTime.Today.Year,DateTime.Today.Month+1,1)).ToShortDateString();		
    				}
    				else{ 
    					textDate.Text=(new DateTime(DateTime.Today.Year+1,1,1)).ToShortDateString();	
    				}
    			}
    			else{
    				textDate.Text=(new DateTime(DateTime.Today.Year,DateTime.Today.Month,1)).ToShortDateString();
    			}	*/
    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //if(textDate.errorProvider1.GetError(textDate)!=""){
        //	MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
        //	return;
        //}
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT " + DbHelper.concat("patient.LName","', '","patient.FName","' '","patient.MiddleI") + ",adjamt " + "FROM patient,adjustment " + "WHERE patient.patnum=adjustment.patnum " + "AND adjustment.adjdate = " + POut.Date(PrefC.getDate(PrefName.FinanceChargeLastRun)) + "AND adjustment.adjtype = '" + POut.Long(PrefC.getLong(PrefName.FinanceChargeAdjustmentType)) + "'";
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "FINANCE CHARGE REPORT";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.SubTitle.Add("Date of Charges: " + PrefC.getDate(PrefName.FinanceChargeLastRun).ToShortDateString());
        report.setColumn(this,0,"Patient Name",180);
        report.SetColumn(this, 1, "Amount", 100, HorizontalAlignment.Right);
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


