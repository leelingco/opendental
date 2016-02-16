//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.FormQuery;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.Properties.Resources;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrintSituation;
import OpenDentBusiness.ProcStat;
import OpenDental.FormReportsUds;

public class FormReportsUds  extends Form 
{

    private DateTime DateFrom = new DateTime();
    private DateTime DateTo = new DateTime();
    public FormReportsUds() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formReportsUds_Load(Object sender, EventArgs e) throws Exception {
        textFrom.Text = DateTime.Now.AddYears(-1).ToShortDateString();
        textTo.Text = DateTime.Now.ToShortDateString();
    }

    private void butPatByZip_Click(Object sender, EventArgs e) throws Exception {
        if (!dateIsValid())
        {
            return ;
        }
         
        ReportSimpleGrid report = new ReportSimpleGrid();
        //Column headings "Zip Code" and "Patients" are provided by the USD 2010 Manual.
        //Starts with five numbers
        report.Query = "SELECT SUBSTR(Zip,1,5) 'Zip Code',COUNT(*) 'Patients' " + "FROM patient pat " + "WHERE " + DbHelper.regexp("Zip","^[0-9]{5}") + " " + "AND PatNum IN ( " + "SELECT PatNum FROM procedurelog " + "WHERE ProcStatus=" + POut.Int(((Enum)ProcStat.C).ordinal()) + " " + "AND DateEntryC >= " + POut.Date(DateFrom) + " " + "AND DateEntryC <= " + POut.Date(DateTo) + ") " + "GROUP BY Zip " + "HAVING COUNT(*) >= 10 " + "ORDER BY Zip";
        //Has more than 10 patients in that zip code for the given time frame.
        FormQuery FormQ = new FormQuery(report);
        FormQ.IsReport = true;
        FormQ.submitQuery();
        FormQ.textQuery.Text = report.Query;
        report.Title = "Patients By ZIP CODE";
        report.SubTitle.Add("From " + DateFrom.ToShortDateString() + " to " + DateTo.ToShortDateString());
        report.Summary.Add("Other Zip Codes: " + Patients.getZipOther(DateFrom,DateTo));
        report.Summary.Add("Unknown Residence: " + Patients.getZipUnknown(DateFrom,DateTo));
        report.Summary.Add("TOTAL: " + Patients.getPatCount(DateFrom,DateTo));
        FormQ.ShowDialog();
    }

    private void but3A_Click(Object sender, EventArgs e) throws Exception {
        if (!dateIsValid())
        {
            return ;
        }
         
        Cursor.Current = Cursors.WaitCursor;
        PrintDocument pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pdAgeGender_PrintPage);
        OpenDental.UI.FormPrintPreview printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.Default, pd, 1, 0, "UDS reporting 3A-AgeGender printed");
        printPreview.ShowDialog();
    }

    private void pdAgeGender_PrintPage(Object sender, PrintPageEventArgs e) throws Exception {
        if (!dateIsValid())
        {
            return ;
        }
         
        Graphics g = e.Graphics;
        int width = e.PageBounds.Width;
        int height = e.PageBounds.Height;
        int[][] table3A = new int[39, 2];
        for (int i = 0;i < 25;i++)
        {
            //fields 1-25, index 0-24
            table3A[i, 0] = Patients.getAgeGenderCount(i,i + 1,PatientGender.Male,DateFrom,DateTo);
            table3A[i, 1] = Patients.getAgeGenderCount(i,i + 1,PatientGender.Female,DateFrom,DateTo);
        }
        int age = new int();
        for (int i = 0;i < 13;i++)
        {
            //fields 26-37, index 25-36
            age = 25 + 5 * i;
            table3A[25 + i, 0] = Patients.getAgeGenderCount(age,age + 5,PatientGender.Male,DateFrom,DateTo);
            //For i=0 give qty male ages 25-29
            table3A[25 + i, 1] = Patients.getAgeGenderCount(age,age + 5,PatientGender.Female,DateFrom,DateTo);
        }
        //For i=0 give qty female ages 25-29
        table3A[37, 0] = Patients.getAgeGenderCount(85,200,PatientGender.Male,DateFrom,DateTo);
        table3A[37, 1] = Patients.getAgeGenderCount(85,200,PatientGender.Female,DateFrom,DateTo);
        table3A[38, 0] = Patients.getAgeGenderCount(0,200,PatientGender.Male,DateFrom,DateTo);
        table3A[38, 1] = Patients.getAgeGenderCount(0,200,PatientGender.Female,DateFrom,DateTo);
        Bitmap bmp = Resources.getUDS3a();
        int xPos = (width - bmp.Width) / 2;
        int yPos = (height - bmp.Height) / 2;
        g.DrawImage(bmp, xPos, yPos, bmp.Width, bmp.Height);
        xPos = 540;
        String qty = new String();
        Font font = new Font(FontFamily.GenericSansSerif, 9);
        for (int i = 0;i < table3A.GetLength(0);i++)
        {
            if (i == table3A.GetLength(0) - 1)
            {
                font = new Font(FontFamily.GenericSansSerif, 9, FontStyle.Bold);
            }
             
            yPos = 245 + (int)(17.72 * i);
            qty = table3A[i, 0].ToString();
            g.DrawString(qty, font, Brushes.Black, xPos - g.MeasureString(qty, font).Width, yPos);
            //Aligns right
            qty = table3A[i, 1].ToString();
            g.DrawString(qty, font, Brushes.Black, xPos - g.MeasureString(qty, font).Width + 125, yPos);
        }
    }

    //Aligns right
    private boolean dateIsValid() throws Exception {
        DateFrom = PIn.Date(textFrom.Text);
        DateTo = PIn.Date(textTo.Text);
        if (DateFrom == DateTime.MinValue || DateTo == DateTime.MinValue)
        {
            MsgBox.show(this,"Please enter valid To and From dates.");
            return false;
        }
         
        return true;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReportsUds.class);
        this.label1 = new System.Windows.Forms.Label();
        this.textFrom = new System.Windows.Forms.TextBox();
        this.label2 = new System.Windows.Forms.Label();
        this.textTo = new System.Windows.Forms.TextBox();
        this.but9D = new OpenDental.UI.Button();
        this.but6A = new OpenDental.UI.Button();
        this.but5 = new OpenDental.UI.Button();
        this.but4 = new OpenDental.UI.Button();
        this.but3B = new OpenDental.UI.Button();
        this.butPatByZip = new OpenDental.UI.Button();
        this.but3A = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.label3 = new System.Windows.Forms.Label();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(-28, 23);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 4;
        this.label1.Text = "From";
        this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textFrom
        //
        this.textFrom.Location = new System.Drawing.Point(78, 20);
        this.textFrom.Name = "textFrom";
        this.textFrom.Size = new System.Drawing.Size(100, 20);
        this.textFrom.TabIndex = 5;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(197, 23);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(35, 13);
        this.label2.TabIndex = 4;
        this.label2.Text = "To";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textTo
        //
        this.textTo.Location = new System.Drawing.Point(238, 20);
        this.textTo.Name = "textTo";
        this.textTo.Size = new System.Drawing.Size(100, 20);
        this.textTo.TabIndex = 5;
        //
        // but9D
        //
        this.but9D.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but9D.setAutosize(true);
        this.but9D.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but9D.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but9D.setCornerRadius(4F);
        this.but9D.Location = new System.Drawing.Point(32, 246);
        this.but9D.Name = "but9D";
        this.but9D.Size = new System.Drawing.Size(146, 24);
        this.but9D.TabIndex = 3;
        this.but9D.Text = "9D";
        //
        // but6A
        //
        this.but6A.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but6A.setAutosize(true);
        this.but6A.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but6A.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but6A.setCornerRadius(4F);
        this.but6A.Location = new System.Drawing.Point(32, 216);
        this.but6A.Name = "but6A";
        this.but6A.Size = new System.Drawing.Size(146, 24);
        this.but6A.TabIndex = 3;
        this.but6A.Text = "6A";
        //
        // but5
        //
        this.but5.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but5.setAutosize(true);
        this.but5.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but5.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but5.setCornerRadius(4F);
        this.but5.Location = new System.Drawing.Point(32, 186);
        this.but5.Name = "but5";
        this.but5.Size = new System.Drawing.Size(146, 24);
        this.but5.TabIndex = 3;
        this.but5.Text = "5 - Staffing";
        //
        // but4
        //
        this.but4.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but4.setAutosize(true);
        this.but4.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but4.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but4.setCornerRadius(4F);
        this.but4.Location = new System.Drawing.Point(32, 156);
        this.but4.Name = "but4";
        this.but4.Size = new System.Drawing.Size(146, 24);
        this.but4.TabIndex = 3;
        this.but4.Text = "4 - Patient Characteristics";
        //
        // but3B
        //
        this.but3B.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but3B.setAutosize(true);
        this.but3B.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but3B.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but3B.setCornerRadius(4F);
        this.but3B.Location = new System.Drawing.Point(32, 126);
        this.but3B.Name = "but3B";
        this.but3B.Size = new System.Drawing.Size(146, 24);
        this.but3B.TabIndex = 3;
        this.but3B.Text = "3B - Race Ethicity";
        //
        // butPatByZip
        //
        this.butPatByZip.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPatByZip.setAutosize(true);
        this.butPatByZip.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPatByZip.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPatByZip.setCornerRadius(4F);
        this.butPatByZip.Location = new System.Drawing.Point(32, 66);
        this.butPatByZip.Name = "butPatByZip";
        this.butPatByZip.Size = new System.Drawing.Size(146, 24);
        this.butPatByZip.TabIndex = 3;
        this.butPatByZip.Text = "Patients by Zip Code";
        this.butPatByZip.Click += new System.EventHandler(this.butPatByZip_Click);
        //
        // but3A
        //
        this.but3A.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.but3A.setAutosize(true);
        this.but3A.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.but3A.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.but3A.setCornerRadius(4F);
        this.but3A.Location = new System.Drawing.Point(32, 96);
        this.but3A.Name = "but3A";
        this.but3A.Size = new System.Drawing.Size(146, 24);
        this.but3A.TabIndex = 3;
        this.but3A.Text = "3A - Age Gender";
        this.but3A.Click += new System.EventHandler(this.but3A_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(783, 514);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 2;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(391, 274);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(261, 128);
        this.label3.TabIndex = 6;
        this.label3.Text = resources.GetString("label3.Text");
        //
        // FormReportsUds
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(883, 565);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.textTo);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textFrom);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.but9D);
        this.Controls.Add(this.but6A);
        this.Controls.Add(this.but5);
        this.Controls.Add(this.but4);
        this.Controls.Add(this.but3B);
        this.Controls.Add(this.butPatByZip);
        this.Controls.Add(this.but3A);
        this.Controls.Add(this.butClose);
        this.Name = "FormReportsUds";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "UDS Reporting";
        this.Load += new System.EventHandler(this.FormReportsUds_Load);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button but3A;
    private OpenDental.UI.Button but3B;
    private OpenDental.UI.Button but4;
    private OpenDental.UI.Button but5;
    private OpenDental.UI.Button but6A;
    private OpenDental.UI.Button but9D;
    private OpenDental.UI.Button butPatByZip;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textTo = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
}


