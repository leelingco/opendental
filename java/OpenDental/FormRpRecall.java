//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:08 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpRecall;
import OpenDental.Lan;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;

/**
* 
*/
public class FormRpRecall  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label labelPatient = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.Container components = null;
    private FormQuery FormQuery2;
    private String SQLselect = new String();
    private String SQLfrom = new String();
    private String SQLwhere = new String();
    private String SQLstatement = new String();
    private String[] PatFieldsSelected = new String[]();
    private ArrayList ALpatSelect = new ArrayList();
    private ArrayList ALpatSelect2 = new ArrayList();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.ListBox listPatientSelect2 = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.ListBox listPatientSelect = new System.Windows.Forms.ListBox();
    private List<long> RecallNumList = new List<long>();
    /**
    * 
    */
    public FormRpRecall() throws Exception {
        initializeComponent();
        ALpatSelect = new ArrayList();
        SQLselect = "";
        SQLfrom = "FROM patient ";
        SQLwhere = "WHERE ";
        fill();
        Lan.f(this);
    }

    /**
    * 
    */
    public FormRpRecall(List<long> recallNumList) throws Exception {
        initializeComponent();
        RecallNumList = new List<long>(recallNumList);
        ALpatSelect = new ArrayList();
        ALpatSelect2 = new ArrayList();
        SQLselect = "";
        SQLfrom = "FROM patient ";
        SQLwhere = "WHERE ";
        fill();
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpRecall.class);
        this.labelPatient = new System.Windows.Forms.Label();
        this.listPatientSelect = new System.Windows.Forms.ListBox();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.listPatientSelect2 = new System.Windows.Forms.ListBox();
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
        // listPatientSelect
        //
        this.listPatientSelect.Location = new System.Drawing.Point(12, 30);
        this.listPatientSelect.Name = "listPatientSelect";
        this.listPatientSelect.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listPatientSelect.Size = new System.Drawing.Size(170, 355);
        this.listPatientSelect.TabIndex = 5;
        this.listPatientSelect.SelectedIndexChanged += new System.EventHandler(this.ListPatientSelect_SelectedIndexChanged);
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
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Enabled = false;
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
        // listPatientSelect2
        //
        this.listPatientSelect2.Location = new System.Drawing.Point(208, 30);
        this.listPatientSelect2.Name = "listPatientSelect2";
        this.listPatientSelect2.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listPatientSelect2.Size = new System.Drawing.Size(170, 355);
        this.listPatientSelect2.TabIndex = 9;
        this.listPatientSelect2.SelectedIndexChanged += new System.EventHandler(this.listPatientSelect2_SelectedIndexChanged);
        //
        // FormRpRecall
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(538, 408);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.listPatientSelect2);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.labelPatient);
        this.Controls.Add(this.listPatientSelect);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpRecall";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Recall Report";
        this.ResumeLayout(false);
    }

    private void fill() throws Exception {
        fillALpatSelect();
        fillPatientSelectList();
    }

    private void fillALpatSelect() throws Exception {
        ALpatSelect.Add(Lan.g(this,"LName"));
        ALpatSelect.Add(Lan.g(this,"FName"));
        ALpatSelect.Add(Lan.g(this,"MiddleI"));
        ALpatSelect.Add(Lan.g(this,"Preferred"));
        ALpatSelect.Add(Lan.g(this,"Salutation"));
        ALpatSelect.Add(Lan.g(this,"Address"));
        ALpatSelect.Add(Lan.g(this,"Address2"));
        ALpatSelect.Add(Lan.g(this,"City"));
        ALpatSelect.Add(Lan.g(this,"State"));
        ALpatSelect.Add(Lan.g(this,"Zip"));
        ALpatSelect.Add(Lan.g(this,"HmPhone"));
        ALpatSelect.Add(Lan.g(this,"WkPhone"));
        ALpatSelect.Add(Lan.g(this,"WirelessPhone"));
        ALpatSelect.Add(Lan.g(this,"Birthdate"));
        ALpatSelect.Add(Lan.g(this,"RecallStatus"));
        ALpatSelect.Add(Lan.g(this,"DateDue"));
        ALpatSelect2.Add(Lan.g(this,"BillingType"));
        ALpatSelect2.Add(Lan.g(this,"CreditType"));
        ALpatSelect2.Add(Lan.g(this,"SSN"));
        ALpatSelect2.Add(Lan.g(this,"ChartNumber"));
        ALpatSelect2.Add(Lan.g(this,"FeeSched"));
        ALpatSelect2.Add(Lan.g(this,"Position"));
        ALpatSelect2.Add(Lan.g(this,"Gender"));
        ALpatSelect2.Add(Lan.g(this,"PatStatus"));
        ALpatSelect2.Add(Lan.g(this,"PatNum"));
        ALpatSelect2.Add(Lan.g(this,"Email"));
        ALpatSelect2.Add(Lan.g(this,"EstBalance"));
        ALpatSelect2.Add(Lan.g(this,"AddrNote"));
        ALpatSelect2.Add(Lan.g(this,"FamFinUrgNote"));
        ALpatSelect2.Add(Lan.g(this,"MedUrgNote"));
        ALpatSelect2.Add(Lan.g(this,"ApptModNote"));
        //ALpatSelect2.Add(Lan.g(this,"PriPlanNum"));//Primary Carrier?
        //ALpatSelect2.Add(Lan.g(this,"PriRelationship"));// ?
        //ALpatSelect2.Add(Lan.g(this,"SecPlanNum"));//Secondary Carrier?
        //ALpatSelect2.Add(Lan.g(this,"SecRelationship"));// ?
        ALpatSelect2.Add(Lan.g(this,"RecallInterval"));
        ALpatSelect2.Add(Lan.g(this,"StudentStatus"));
        ALpatSelect2.Add(Lan.g(this,"SchoolName"));
        ALpatSelect2.Add(Lan.g(this,"PriProv"));
        ALpatSelect2.Add(Lan.g(this,"SecProv"));
        //ALpatSelect2.Add(Lan.g(this,"NextAptNum"));
        ALpatSelect2.Add(Lan.g(this,"Guarantor"));
        ALpatSelect2.Add(Lan.g(this,"ImageFolder"));
    }

    private void fillPatientSelectList() throws Exception {
        for (int i = 0;i < ALpatSelect.Count;i++)
        {
            listPatientSelect.Items.Add(ALpatSelect[i]);
            listPatientSelect.SetSelected(i, true);
        }
        for (int i = 0;i < ALpatSelect2.Count;i++)
        {
            listPatientSelect2.Items.Add(ALpatSelect2[i]);
        }
        SQLselect = "";
    }

    private void fillSQLstatement() throws Exception {
        SQLstatement = SQLselect + SQLfrom + SQLwhere;
    }

    private void createSQL() throws Exception {
        createSQLselect();
        createSQLfrom();
        createSQLwhere();
        fillSQLstatement();
    }

    private void createSQLselect() throws Exception {
        SQLselect = "";
        PatFieldsSelected = new String[listPatientSelect.SelectedItems.Count + listPatientSelect2.SelectedItems.Count];
        if (listPatientSelect.SelectedItems.Count == 0 && listPatientSelect2.SelectedItems.Count == 0)
        {
            butOK.Enabled = false;
            return ;
        }
         
        SQLselect = "SELECT ";
        listPatientSelect.SelectedItems.CopyTo(PatFieldsSelected, 0);
        listPatientSelect2.SelectedItems.CopyTo(PatFieldsSelected, listPatientSelect.SelectedItems.Count);
        for (int i = 0;i < PatFieldsSelected.Length;i++)
        {
            if (i > 0)
            {
                SQLselect += ",";
            }
             
            if (StringSupport.equals(PatFieldsSelected[i], "RecallInterval"))
            {
                SQLselect += "ROUND(recall.RecallInterval/65536) AS MonthInterval";
            }
            else //returns the months.  It will malfunction if a year is present
            if (StringSupport.equals(PatFieldsSelected[i], "DateDue") || StringSupport.equals(PatFieldsSelected[i], "RecallStatus"))
            {
                SQLselect += "recall." + PatFieldsSelected[i];
            }
            else
            {
                SQLselect += "patient." + PatFieldsSelected[i];
            }  
        }
        butOK.Enabled = true;
    }

    private void createSQLfrom() throws Exception {
        SQLfrom = " FROM patient,recall ";
    }

    private void createSQLwhere() throws Exception {
        SQLwhere = "WHERE patient.PatNum=recall.PatNum AND (";
        for (int i = 0;i < RecallNumList.Count;i++)
        {
            if (i > 0)
            {
                SQLwhere += " OR ";
            }
             
            SQLwhere += "recall.RecallNum=" + POut.Long(RecallNumList[i]);
        }
        SQLwhere += ")";
    }

    private void listPatientSelect_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        createSQL();
    }

    private void listPatientSelect2_SelectedIndexChanged(Object sender, System.EventArgs e) throws Exception {
        createSQL();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = SQLstatement;
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = false;
        FormQuery2.submitQuery();
        FormQuery2.textQuery.Text = report.Query;
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


