//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:03 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormRpAppointments;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;

/**
* Summary description for FormRpApptWithPhones.
*/
public class FormRpAppointments  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAll;
    private System.Windows.Forms.ListBox listProv = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateTo;
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butTomorrow;
    private OpenDental.UI.Button butToday;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public FormRpAppointments() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpAppointments.class);
        this.butAll = new OpenDental.UI.Button();
        this.listProv = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.textDateTo = new OpenDental.ValidDate();
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.textDateFrom = new OpenDental.ValidDate();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butToday = new OpenDental.UI.Button();
        this.butTomorrow = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(28, 243);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(75, 26);
        this.butAll.TabIndex = 34;
        this.butAll.Text = "&All";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // listProv
        //
        this.listProv.Location = new System.Drawing.Point(27, 41);
        this.listProv.Name = "listProv";
        this.listProv.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProv.Size = new System.Drawing.Size(120, 186);
        this.listProv.TabIndex = 33;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(27, 18);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(104, 16);
        this.label1.TabIndex = 32;
        this.label1.Text = "Providers";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(92, 51);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(100, 20);
        this.textDateTo.TabIndex = 44;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(4, 52);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(82, 18);
        this.label3.TabIndex = 39;
        this.label3.Text = "To";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(6, 26);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(82, 18);
        this.label2.TabIndex = 37;
        this.label2.Text = "From";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(92, 24);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(100, 20);
        this.textDateFrom.TabIndex = 43;
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(502, 336);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 44;
        this.butCancel.Text = "&Cancel";
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(502, 296);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 43;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.butToday);
        this.groupBox1.Controls.Add(this.butTomorrow);
        this.groupBox1.Controls.Add(this.textDateFrom);
        this.groupBox1.Controls.Add(this.textDateTo);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Location = new System.Drawing.Point(200, 35);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(380, 158);
        this.groupBox1.TabIndex = 45;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Date Range";
        //
        // butToday
        //
        this.butToday.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butToday.setAutosize(true);
        this.butToday.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butToday.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butToday.setCornerRadius(4F);
        this.butToday.Location = new System.Drawing.Point(250, 23);
        this.butToday.Name = "butToday";
        this.butToday.Size = new System.Drawing.Size(96, 23);
        this.butToday.TabIndex = 46;
        this.butToday.Text = "Today";
        this.butToday.Click += new System.EventHandler(this.butToday_Click);
        //
        // butTomorrow
        //
        this.butTomorrow.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butTomorrow.setAutosize(true);
        this.butTomorrow.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butTomorrow.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butTomorrow.setCornerRadius(4F);
        this.butTomorrow.Location = new System.Drawing.Point(250, 50);
        this.butTomorrow.Name = "butTomorrow";
        this.butTomorrow.Size = new System.Drawing.Size(96, 23);
        this.butTomorrow.TabIndex = 45;
        this.butTomorrow.Text = "Tomorrow";
        this.butTomorrow.Click += new System.EventHandler(this.butTomorrow_Click);
        //
        // FormRpAppointments
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(645, 383);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butAll);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.label1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpAppointments";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointments Report";
        this.Load += new System.EventHandler(this.FormRpApptWithPhones_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formRpApptWithPhones_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            listProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
            listProv.SetSelected(i, true);
        }
        setTomorrow();
    }

    private void setTomorrow() throws Exception {
        textDateFrom.Text = DateTime.Today.AddDays(1).ToShortDateString();
        textDateTo.Text = DateTime.Today.AddDays(1).ToShortDateString();
    }

    private void butToday_Click(Object sender, System.EventArgs e) throws Exception {
        textDateFrom.Text = DateTime.Today.ToShortDateString();
        textDateTo.Text = DateTime.Today.ToShortDateString();
    }

    private void butTomorrow_Click(Object sender, System.EventArgs e) throws Exception {
        setTomorrow();
    }

    private void butAll_Click(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < listProv.Items.Count;i++)
        {
            listProv.SetSelected(i, true);
        }
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        //validate user input
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        if (textDateFrom.Text.Length == 0 || textDateTo.Text.Length == 0)
        {
            MessageBox.Show(Lan.g(this,"From and To dates are required."));
            return ;
        }
         
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = PIn.Date(textDateTo.Text);
        if (dateTo < dateFrom)
        {
            MessageBox.Show(Lan.g(this,"To date cannot be before From date."));
            return ;
        }
         
        if (listProv.SelectedIndices.Count == 0)
        {
            MessageBox.Show(Lan.g(this,"You must select at least one provider."));
            return ;
        }
         
        String whereProv = new String();
        //used as the provider portion of the where clauses.
        //each whereProv needs to be set up separately for each query
        whereProv = "(appointment.ProvNum IN (";
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            if (i > 0)
            {
                whereProv += ",";
            }
             
            whereProv += "'" + POut.Long(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum) + "'";
        }
        whereProv += ") ";
        whereProv += "OR appointment.ProvHyg IN (";
        for (int i = 0;i < listProv.SelectedIndices.Count;i++)
        {
            if (i > 0)
            {
                whereProv += ",";
            }
             
            whereProv += "'" + POut.Long(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum) + "'";
        }
        whereProv += ")) ";
        //create the report
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.setIsLandscape(true);
        report.setReportName("Appointments");
        report.addTitle("Appointments");
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        report.AddSubTitle(dateFrom.ToShortDateString() + " - " + dateTo.ToShortDateString());
        //setup query
        report.setQuery("SELECT appointment.AptDateTime, \r\n" + 
        "\t\t\t\ttrim(CONCAT(CONCAT(CONCAT(CONCAT(concat(patient.LName,\', \'),case when length(patient.Preferred) > 0 \r\n" + 
        "\t\t\t\tthen CONCAT(CONCAT(\'(\',patient.Preferred),\') \') else \'\' end),patient.fname), \' \'),patient.middlei))\r\n" + 
        "\t\t\t\tAS PatName,\r\n" + 
        "\t\t\t\tpatient.Birthdate,\r\n" + 
        "\t\t\t\tappointment.AptDateTime,\r\n" + 
        "\t\t\t\tlength(appointment.Pattern)*5,\r\n" + 
        "\t\t\t\tappointment.ProcDescript,\r\n" + 
        "\t\t\t\tpatient.HmPhone, patient.WkPhone, patient.WirelessPhone\r\n" + 
        "\t\t\t\tFROM appointment INNER JOIN patient ON appointment.PatNum = patient.PatNum\r\n" + 
        "\t\t\t\tWHERE appointment.AptDateTime between " + POut.Date(dateFrom) + " AND " + POut.Date(dateTo.AddDays(1)) + " AND " + "AptStatus != '" + ((Enum)ApptStatus.UnschedList).ordinal() + "' AND " + "AptStatus != '" + ((Enum)ApptStatus.Planned).ordinal() + "' AND " + whereProv + " " + "ORDER BY appointment.AptDateTime, 2");
        // add columns to report
        report.addColumn("Date",75,FieldValueType.Date);
        report.getLastRO(ReportObjectKind.FieldObject).setSuppressIfDuplicate(true);
        report.getLastRO(ReportObjectKind.FieldObject).setFormatString("d");
        report.addColumn("Patient",175,FieldValueType.String);
        report.addColumn("Age",45,FieldValueType.Age);
        // remove the total column
        //if(report.ReportObjects[report.ReportObjects.Count-1].SummarizedField == "Age")
        //	report.ReportObjects.RemoveAt(report.ReportObjects.Count-1);
        //report.GetLastRO(ReportObjectKind.FieldObject).FormatString = "###0";
        //report.GetLastRO(ReportObjectKind.FieldObject).TextAlign = ContentAlignment.MiddleCenter;
        //report.GetLastRO(ReportObjectKind.TextObject).TextAlign = ContentAlignment.MiddleCenter;
        report.addColumn("Time",65,FieldValueType.Date);
        report.getLastRO(ReportObjectKind.FieldObject).setFormatString("t");
        report.getLastRO(ReportObjectKind.TextObject).setTextAlign(ContentAlignment.MiddleRight);
        report.getLastRO(ReportObjectKind.FieldObject).setTextAlign(ContentAlignment.MiddleRight);
        report.addColumn("Length",60,FieldValueType.Integer);
        report.getLastRO(ReportObjectKind.TextObject).setLocation(new Point(report.getLastRO(ReportObjectKind.TextObject).getLocation().X + 6, report.getLastRO(ReportObjectKind.TextObject).getLocation().Y));
        report.getLastRO(ReportObjectKind.FieldObject).setLocation(new Point(report.getLastRO(ReportObjectKind.FieldObject).getLocation().X + 8, report.getLastRO(ReportObjectKind.FieldObject).getLocation().Y));
        report.addColumn("Description",170,FieldValueType.String);
        report.addColumn("Home Ph.",120,FieldValueType.String);
        report.addColumn("Work Ph.",120,FieldValueType.String);
        report.addColumn("Cell Ph.",120,FieldValueType.String);
        report.addPageNum();
        // execute query
        if (!report.submitQuery())
        {
            return ;
        }
         
        // display report
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        //FormR.MyReport=report;
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


