//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:06 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.FormRpInsCo;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* 
*/
public class FormRpInsCo  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.Label labelPatName = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textBoxCarrier = new System.Windows.Forms.TextBox();
    private System.ComponentModel.Container components = null;
    private FormQuery FormQuery2;
    private String carrier = new String();
    /**
    * 
    */
    public FormRpInsCo() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpInsCo.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.labelPatName = new System.Windows.Forms.Label();
        this.textBoxCarrier = new System.Windows.Forms.TextBox();
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
        this.butCancel.Location = new System.Drawing.Point(490, 173);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(490, 138);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // labelPatName
        //
        this.labelPatName.Location = new System.Drawing.Point(24, 7);
        this.labelPatName.Name = "labelPatName";
        this.labelPatName.Size = new System.Drawing.Size(256, 70);
        this.labelPatName.TabIndex = 37;
        this.labelPatName.Text = "Enter the first few letters of the Insurance Carrier name, or leave blank to view" + " all carriers:";
        this.labelPatName.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textBoxCarrier
        //
        this.textBoxCarrier.Location = new System.Drawing.Point(281, 33);
        this.textBoxCarrier.Name = "textBoxCarrier";
        this.textBoxCarrier.Size = new System.Drawing.Size(100, 20);
        this.textBoxCarrier.TabIndex = 0;
        //
        // FormRpInsCo
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(591, 229);
        this.Controls.Add(this.labelPatName);
        this.Controls.Add(this.textBoxCarrier);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpInsCo";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Insurance Plan Report";
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        carrier = PIn.String(textBoxCarrier.Text);
        ReportSimpleGrid report = new ReportSimpleGrid();
        /*
        SELECT insplan.subscriber,insplan.carrier,patient.hmphone,
        insplan.groupname FROM insplan,patient WHERE insplan.subscriber=patient.patnum 
        && insplan.carrier like +carrier+'%'
        Order By patient.lname,patient.fname
        */
        //,patplan "//we only include patplan to make sure insurance is active for a patient.  We don't want any info from patplan.
        //+"AND insplan.PlanNum=patplan.PlanNum "
        //+"AND patplan.PatNum=patient.PatNum "
        //+"AND patplan.Ordinal=1 "
        report.Query = "SELECT carrier.CarrierName" + ",CONCAT(CONCAT(CONCAT(CONCAT(patient.LName,', '),patient.FName),' '),patient.MiddleI) AS SubscriberName,carrier.Phone," + "insplan.Groupname " + "FROM insplan,inssub,patient,carrier " + "WHERE inssub.Subscriber=patient.PatNum " + "AND inssub.PlanNum=insplan.PlanNum " + "AND EXISTS (SELECT * FROM patplan WHERE patplan.InsSubNum=inssub.InsSubNum) " + "AND carrier.CarrierNum=insplan.CarrierNum " + "AND carrier.CarrierName LIKE '" + carrier + "%' " + "ORDER BY carrier.CarrierName,patient.LName";
        //Debug.WriteLine(report.Query);
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "Insurance Plan List";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.setColumn(this,0,"Carrier Name",230);
        report.setColumn(this,1,"Subscriber Name",175);
        report.setColumn(this,2,"Carrier Phone#",175);
        report.setColumn(this,3,"Group Name",165);
        report.Summary.Add(Lan.g(this,"Total: ") + report.TableQ.Rows.Count.ToString());
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


