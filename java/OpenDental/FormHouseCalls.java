//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:27 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormHouseCalls;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.HouseCallsQueries;
import OpenDentBusiness.Program;
import OpenDentBusiness.ProgramProperties;
import OpenDentBusiness.ProgramProperty;
import OpenDentBusiness.Providers;

/**
* 
*/
public class FormHouseCalls  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private OpenDental.ValidDate textDateFrom;
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDateTo;
    private OpenDental.UI.Button butAll;
    private OpenDental.UI.Button but7;
    private System.Windows.Forms.RadioButton radioConfirm = new System.Windows.Forms.RadioButton();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public Program ProgramCur;
    /**
    * 
    */
    public FormHouseCalls() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormHouseCalls.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.butAll = new OpenDental.UI.Button();
        this.but7 = new OpenDental.UI.Button();
        this.textDateFrom = new OpenDental.ValidDate();
        this.label2 = new System.Windows.Forms.Label();
        this.label1 = new System.Windows.Forms.Label();
        this.textDateTo = new OpenDental.ValidDate();
        this.radioConfirm = new System.Windows.Forms.RadioButton();
        this.groupBox2.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(359, 267);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(359, 226);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butAll);
        this.groupBox2.Controls.Add(this.but7);
        this.groupBox2.Controls.Add(this.textDateFrom);
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Controls.Add(this.label1);
        this.groupBox2.Controls.Add(this.textDateTo);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(42, 72);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(393, 109);
        this.groupBox2.TabIndex = 8;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Date Range";
        //
        // butAll
        //
        this.butAll.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAll.setAutosize(true);
        this.butAll.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAll.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAll.setCornerRadius(4F);
        this.butAll.Location = new System.Drawing.Point(263, 61);
        this.butAll.Name = "butAll";
        this.butAll.Size = new System.Drawing.Size(93, 23);
        this.butAll.TabIndex = 8;
        this.butAll.Text = "All Future";
        this.butAll.Click += new System.EventHandler(this.butAll_Click);
        //
        // but7
        //
        this.but7.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but7.setAutosize(true);
        this.but7.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but7.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but7.setCornerRadius(4F);
        this.but7.Location = new System.Drawing.Point(263, 35);
        this.but7.Name = "but7";
        this.but7.Size = new System.Drawing.Size(93, 23);
        this.but7.TabIndex = 7;
        this.but7.Text = "Next 7 Days";
        this.but7.Click += new System.EventHandler(this.but7_Click);
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(137, 37);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(84, 20);
        this.textDateFrom.TabIndex = 3;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(30, 64);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 4;
        this.label2.Text = "To Date";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(30, 36);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 2;
        this.label1.Text = "From Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(137, 65);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(85, 20);
        this.textDateTo.TabIndex = 5;
        //
        // radioConfirm
        //
        this.radioConfirm.Checked = true;
        this.radioConfirm.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioConfirm.Location = new System.Drawing.Point(43, 30);
        this.radioConfirm.Name = "radioConfirm";
        this.radioConfirm.Size = new System.Drawing.Size(329, 21);
        this.radioConfirm.TabIndex = 9;
        this.radioConfirm.TabStop = true;
        this.radioConfirm.Text = "Confirm Appointments";
        //
        // FormHouseCalls
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(486, 318);
        this.Controls.Add(this.radioConfirm);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormHouseCalls";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "House Calls";
        this.Load += new System.EventHandler(this.FormHouseCalls_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formHouseCalls_Load(Object sender, System.EventArgs e) throws Exception {
        textDateFrom.Text = DateTime.Today.AddDays(1).ToShortDateString();
        textDateTo.Text = DateTime.Today.AddDays(7).ToShortDateString();
    }

    private void but7_Click(Object sender, System.EventArgs e) throws Exception {
        textDateFrom.Text = DateTime.Today.AddDays(1).ToShortDateString();
        textDateTo.Text = DateTime.Today.AddDays(7).ToShortDateString();
    }

    private void butAll_Click(Object sender, System.EventArgs e) throws Exception {
        textDateFrom.Text = DateTime.Today.AddDays(1).ToShortDateString();
        textDateTo.Text = "";
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        DateTime FromDate = new DateTime();
        DateTime ToDate = new DateTime();
        if (StringSupport.equals(textDateFrom.Text, ""))
        {
            MessageBox.Show(Lan.g(this,"From Date cannot be left blank."));
            return ;
        }
         
        FromDate = PIn.Date(textDateFrom.Text);
        if (StringSupport.equals(textDateTo.Text, ""))
            ToDate = DateTime.MaxValue.AddDays(-1);
        else
            ToDate = PIn.Date(textDateTo.Text); 
        //Create the file and first row--------------------------------------------------------
        ArrayList ForProgram = ProgramProperties.getForProgram(ProgramCur.ProgramNum);
        ProgramProperty PPCur = ProgramProperties.getCur(ForProgram,"Export Path");
        String fileName = PPCur.PropertyValue + "Appt.txt";
        if (!Directory.Exists(PPCur.PropertyValue))
        {
            Directory.CreateDirectory(PPCur.PropertyValue);
        }
         
        StreamWriter sr = File.CreateText(fileName);
        sr.WriteLine("\"LastName\",\"FirstName\",\"PatientNumber\",\"HomePhone\",\"WorkNumber\"," + "\"EmailAddress\",\"SendEmail\",\"Address\",\"Address2\",\"City\",\"State\",\"Zip\"," + "\"ApptDate\",\"ApptTime\",\"ApptReason\",\"DoctorNumber\",\"DoctorName\",\"IsNewPatient\",\"WirelessPhone\"");
        DataTable table = HouseCallsQueries.getHouseCalls(FromDate,ToDate);
        boolean usePatNum = false;
        PPCur = ProgramProperties.getCur(ForProgram,"Enter 0 to use PatientNum, or 1 to use ChartNum");
            ;
        if (StringSupport.equals(PPCur.PropertyValue, "0"))
        {
            usePatNum = true;
        }
         
        DateTime aptDT = new DateTime();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][0].ToString())) + "\",");
            //0-LastName
            if (!StringSupport.equals(table.Rows[i][2].ToString(), ""))
            {
                //if Preferred Name exists
                sr.Write("\"" + Dequote(PIn.String(table.Rows[i][2].ToString())) + "\",");
            }
            else
            {
                //2-PrefName
                sr.Write("\"" + Dequote(PIn.String(table.Rows[i][1].ToString())) + "\",");
            } 
            //1-FirstName
            if (usePatNum)
            {
                sr.Write("\"" + table.Rows[i][3].ToString() + "\",");
            }
            else
            {
                //3-PatNum
                sr.Write("\"" + Dequote(PIn.String(table.Rows[i][4].ToString())) + "\",");
            } 
            //4-ChartNumber
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][5].ToString())) + "\",");
            //5-HomePhone
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][6].ToString())) + "\",");
            //6-WorkNumber
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][7].ToString())) + "\",");
            //7-EmailAddress
            if (!StringSupport.equals(table.Rows[i][7].ToString(), ""))
            {
                //if an email exists
                sr.Write("\"T\",");
            }
            else
            {
                //SendEmail
                sr.Write("\"F\",");
            } 
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][8].ToString())) + "\",");
            //8-Address
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][9].ToString())) + "\",");
            //9-Address2
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][10].ToString())) + "\",");
            //10-City
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][11].ToString())) + "\",");
            //11-State
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][12].ToString())) + "\",");
            //12-Zip
            aptDT = PIn.DateT(table.Rows[i][13].ToString());
            sr.Write("\"" + aptDT.ToString("MM/dd/yyyy") + "\",");
            //13-ApptDate
            sr.Write("\"" + aptDT.ToString("hh:mm tt") + "\",");
            //13-ApptTime eg 01:30 PM
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][14].ToString())) + "\",");
            //14-ApptReason
            sr.Write("\"" + table.Rows[i][15].ToString() + "\",");
            //15-DoctorNumber. might possibly be 0
            //15-DoctorName. Can handle 0 without any problem.
            sr.Write("\"" + Dequote(Providers.GetLName(PIn.Long(table.Rows[i][15].ToString()))) + "\",");
            if (StringSupport.equals(table.Rows[i][16].ToString(), "1"))
            {
                //16-IsNewPatient
                sr.Write("\"T\",");
            }
            else
            {
                //SendEmail
                sr.Write("\"F\",");
            } 
            sr.Write("\"" + Dequote(PIn.String(table.Rows[i][17].ToString())) + "\"");
            //17-WirelessPhone
            sr.WriteLine();
        }
        //Must be last.
        sr.Close();
        MessageBox.Show("Done");
        DialogResult = DialogResult.OK;
    }

    private String dequote(String inputStr) throws Exception {
        return inputStr.Replace("\"", "");
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


