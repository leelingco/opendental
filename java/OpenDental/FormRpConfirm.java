//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:05 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpConfirm;
import OpenDental.MsgBox;
import OpenDental.ReportSimpleGrid;

/**
* 
*/
public class FormRpConfirm  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.Container components = null;
    private ArrayList ALSelect = new ArrayList();
    private ArrayList ALSelect2 = new ArrayList();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listSelect = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listSelect2 = new System.Windows.Forms.ListBox();
    private long[] AptNums = new long[]();
    /**
    * 
    */
    public FormRpConfirm(long[] aptNums) throws Exception {
        initializeComponent();
        AptNums = (long[])aptNums.Clone();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpConfirm.class);
        this.labelPatient = new System.Windows.Forms.Label();
        this.listSelect = new System.Windows.Forms.ListBox();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.listSelect2 = new System.Windows.Forms.ListBox();
        this.SuspendLayout();
        //
        // labelPatient
        //
        this.labelPatient.Location = new System.Drawing.Point(12, 16);
        this.labelPatient.Name = "labelPatient";
        this.labelPatient.Size = new System.Drawing.Size(170, 14);
        this.labelPatient.TabIndex = 6;
        this.labelPatient.Text = "Standard Fields";
        this.labelPatient.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // listSelect
        //
        this.listSelect.Location = new System.Drawing.Point(12, 30);
        this.listSelect.Name = "listSelect";
        this.listSelect.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listSelect.Size = new System.Drawing.Size(170, 355);
        this.listSelect.TabIndex = 5;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(434, 362);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 8;
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
        this.butOK.Location = new System.Drawing.Point(434, 326);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 7;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(208, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(170, 14);
        this.label1.TabIndex = 10;
        this.label1.Text = "Other Available Fields";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopCenter;
        //
        // listSelect2
        //
        this.listSelect2.Location = new System.Drawing.Point(208, 30);
        this.listSelect2.Name = "listSelect2";
        this.listSelect2.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listSelect2.Size = new System.Drawing.Size(170, 355);
        this.listSelect2.TabIndex = 9;
        //
        // FormRpConfirm
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(538, 408);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listSelect2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelPatient);
        this.Controls.Add(this.listSelect);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpConfirm";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Confirmation Report";
        this.Load += new System.EventHandler(this.FormRpConfirm_Load);
        this.ResumeLayout(false);
    }

    private void formRpConfirm_Load(Object sender, System.EventArgs e) throws Exception {
        ALSelect = new ArrayList();
        ALSelect.Add("LName");
        ALSelect.Add("FName");
        ALSelect.Add("MiddleI");
        ALSelect.Add("Preferred");
        ALSelect.Add("Salutation");
        ALSelect.Add("Address");
        ALSelect.Add("Address2");
        ALSelect.Add("City");
        ALSelect.Add("State");
        ALSelect.Add("Zip");
        ALSelect.Add("HmPhone");
        ALSelect.Add("WkPhone");
        ALSelect.Add("WirelessPhone");
        ALSelect.Add("Birthdate");
        ALSelect.Add("AptDateTime");
        ALSelect2 = new ArrayList();
        ALSelect2.Add("BillingType");
        ALSelect2.Add("CreditType");
        ALSelect2.Add("SSN");
        ALSelect2.Add("ChartNumber");
        ALSelect2.Add("FeeSched");
        ALSelect2.Add("Position");
        ALSelect2.Add("Gender");
        ALSelect2.Add("PatStatus");
        ALSelect2.Add("PatNum");
        ALSelect2.Add("Email");
        ALSelect2.Add("EstBalance");
        ALSelect2.Add("AddrNote");
        ALSelect2.Add("FamFinUrgNote");
        ALSelect2.Add("MedUrgNote");
        ALSelect2.Add("ApptModNote");
        ALSelect2.Add("PriCarrier");
        ALSelect2.Add("PriRelationship");
        ALSelect2.Add("SecCarrier");
        ALSelect2.Add("SecRelationship");
        //ALSelect2.Add("RecallInterval");
        ALSelect2.Add("StudentStatus");
        ALSelect2.Add("SchoolName");
        ALSelect2.Add("PriProv");
        ALSelect2.Add("SecProv");
        //ALSelect2.Add("NextAptNum");
        ALSelect2.Add("Guarantor");
        ALSelect2.Add("ImageFolder");
        for (int i = 0;i < ALSelect.Count;i++)
        {
            listSelect.Items.Add(ALSelect[i]);
            listSelect.SetSelected(i, true);
        }
        for (int i = 0;i < ALSelect2.Count;i++)
        {
            listSelect2.Items.Add(ALSelect2[i]);
        }
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        String[] fieldsSelected = new String[listSelect.SelectedItems.Count + listSelect2.SelectedItems.Count];
        if (listSelect.SelectedItems.Count == 0 && listSelect2.SelectedItems.Count == 0)
        {
            MsgBox.show(this,"At least one field must be selected.");
            return ;
        }
         
        listSelect.SelectedItems.CopyTo(fieldsSelected, 0);
        listSelect2.SelectedItems.CopyTo(fieldsSelected, listSelect.SelectedItems.Count);
        String command = "SELECT ";
        for (int i = 0;i < fieldsSelected.Length;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            if (StringSupport.equals(fieldsSelected[i], "AptDateTime"))
            {
                command += "appointment.AptDateTime";
            }
            else if (StringSupport.equals(fieldsSelected[i], "PriCarrier"))
            {
                command += "(SELECT carrier.CarrierName FROM patplan,inssub,insplan,carrier WHERE patplan.PatNum=patient.PatNum " + "AND patplan.InsSubNum=inssub.InsSubNum AND inssub.PlanNum=insplan.PlanNum AND insplan.CarrierNum=carrier.CarrierNum AND patplan.Ordinal=1) PriCarrier";
            }
            else if (StringSupport.equals(fieldsSelected[i], "PriRelationship"))
            {
                command += "(SELECT Relationship FROM patplan WHERE patplan.PatNum=patient.PatNum AND patplan.Ordinal=1) PriRelationship";
            }
            else if (StringSupport.equals(fieldsSelected[i], "SecCarrier"))
            {
                command += "(SELECT carrier.CarrierName FROM patplan,inssub,insplan,carrier WHERE patplan.PatNum=patient.PatNum " + "AND patplan.InsSubNum=inssub.InsSubNum AND inssub.PlanNum=insplan.PlanNum AND insplan.CarrierNum=carrier.CarrierNum AND patplan.Ordinal=2) SecCarrier";
            }
            else if (StringSupport.equals(fieldsSelected[i], "SecRelationship"))
            {
                command += "(SELECT Relationship FROM patplan WHERE patplan.PatNum=patient.PatNum AND patplan.Ordinal=2) SecRelationship";
            }
            else
            {
                command += "patient." + fieldsSelected[i];
            }     
        }
        command += " FROM patient,appointment " + "WHERE patient.PatNum=appointment.PatNum AND(";
        for (int i = 0;i < AptNums.Length;i++)
        {
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " appointment.AptNum='" + AptNums[i] + "'";
        }
        command += ")";
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = command;
        FormQuery FormQ = new FormQuery(report);
        FormQ.IsReport = false;
        FormQ.submitQuery();
        FormQ.textQuery.Text = report.Query;
        FormQ.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


