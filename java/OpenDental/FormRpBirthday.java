//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:05 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.DataValid;
import OpenDental.FormRpBirthday;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.Properties.Resources;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDental.ReportingOld2.FormReportLikeCrystal;
import OpenDental.ReportingOld2.ReportLikeCrystal;
import OpenDental.ReportingOld2.ReportObjectKind;
import OpenDental.Shared;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.PrintSituation;

/**
* Summary description for FormRpApptWithPhones.
*/
public class FormRpBirthday  extends System.Windows.Forms.Form 
{
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butLeft;
    private OpenDental.UI.Button butMonth;
    private System.Windows.Forms.TextBox textDateFrom = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.TextBox textDateTo = new System.Windows.Forms.TextBox();
    private GroupBox groupBox4 = new GroupBox();
    private Label label4 = new Label();
    private OpenDental.UI.Button butPostcards;
    private int pagesPrinted = new int();
    private ErrorProvider errorProvider1 = new ErrorProvider();
    private DataTable BirthdayTable = new DataTable();
    private int patientsPrinted = new int();
    private PrintDocument pd = new PrintDocument();
    private TextBox textPostcardMsg = new TextBox();
    private OpenDental.UI.FormPrintPreview printPreview;
    private String cultureDateFormat = new String();
    /**
    * 
    */
    public FormRpBirthday() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        OpenDental.UI.Button butSave;
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpBirthday.class);
        this.label3 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.textDateTo = new System.Windows.Forms.TextBox();
        this.butRight = new OpenDental.UI.Button();
        this.butMonth = new OpenDental.UI.Button();
        this.butLeft = new OpenDental.UI.Button();
        this.textDateFrom = new System.Windows.Forms.TextBox();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.textPostcardMsg = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.butPostcards = new OpenDental.UI.Button();
        butSave = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.SuspendLayout();
        //
        // butSave
        //
        butSave.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        butSave.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        butSave.setAutosize(true);
        butSave.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        butSave.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        butSave.setCornerRadius(4F);
        butSave.Location = new System.Drawing.Point(202, 30);
        butSave.Name = "butSave";
        butSave.Size = new System.Drawing.Size(87, 26);
        butSave.TabIndex = 44;
        butSave.Text = "Save Msg";
        butSave.Click += new System.EventHandler(this.butSave_Click);
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(20, 99);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(82, 18);
        this.label3.TabIndex = 39;
        this.label3.Text = "To";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(22, 73);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(82, 18);
        this.label2.TabIndex = 37;
        this.label2.Text = "From";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
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
        this.butCancel.Location = new System.Drawing.Point(546, 216);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 44;
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
        this.butOK.Location = new System.Drawing.Point(546, 176);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 43;
        this.butOK.Text = "Report";
        this.butOK.Click += new System.EventHandler(this.butReport_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.textDateTo);
        this.groupBox1.Controls.Add(this.butRight);
        this.groupBox1.Controls.Add(this.butMonth);
        this.groupBox1.Controls.Add(this.butLeft);
        this.groupBox1.Controls.Add(this.label3);
        this.groupBox1.Controls.Add(this.label2);
        this.groupBox1.Controls.Add(this.textDateFrom);
        this.groupBox1.Location = new System.Drawing.Point(21, 22);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(286, 131);
        this.groupBox1.TabIndex = 45;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Date Range (without the year)";
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(110, 97);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(71, 20);
        this.textDateTo.TabIndex = 50;
        this.textDateTo.Validating += new System.ComponentModel.CancelEventHandler(this.textDateTo_Validating);
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(206, 28);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(45, 26);
        this.butRight.TabIndex = 49;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butMonth
        //
        this.butMonth.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butMonth.setAutosize(true);
        this.butMonth.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butMonth.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butMonth.setCornerRadius(4F);
        this.butMonth.Location = new System.Drawing.Point(96, 28);
        this.butMonth.Name = "butMonth";
        this.butMonth.Size = new System.Drawing.Size(101, 26);
        this.butMonth.TabIndex = 48;
        this.butMonth.Text = "Next Month";
        this.butMonth.Click += new System.EventHandler(this.butMonth_Click);
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(42, 28);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(45, 26);
        this.butLeft.TabIndex = 47;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(110, 70);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(71, 20);
        this.textDateFrom.TabIndex = 46;
        this.textDateFrom.Validating += new System.ComponentModel.CancelEventHandler(this.textDateFrom_Validating);
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.textPostcardMsg);
        this.groupBox4.Controls.Add(butSave);
        this.groupBox4.Controls.Add(this.label4);
        this.groupBox4.Controls.Add(this.butPostcards);
        this.groupBox4.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox4.Location = new System.Drawing.Point(332, 22);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(306, 131);
        this.groupBox4.TabIndex = 46;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "Postcards";
        //
        // textPostcardMsg
        //
        this.textPostcardMsg.AcceptsReturn = true;
        this.textPostcardMsg.Location = new System.Drawing.Point(10, 30);
        this.textPostcardMsg.Multiline = true;
        this.textPostcardMsg.Name = "textPostcardMsg";
        this.textPostcardMsg.Size = new System.Drawing.Size(186, 87);
        this.textPostcardMsg.TabIndex = 45;
        this.textPostcardMsg.Text = "Dear ?FName,  Happy ?AgeOrdinal Birthday!  Now, you\'re ?Age years old.";
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(7, 12);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(158, 17);
        this.label4.TabIndex = 18;
        this.label4.Text = "Message";
        this.label4.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // butPostcards
        //
        this.butPostcards.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPostcards.setAutosize(true);
        this.butPostcards.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPostcards.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPostcards.setCornerRadius(4F);
        this.butPostcards.Image = Resources.getbutPrintSmall();
        this.butPostcards.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butPostcards.Location = new System.Drawing.Point(202, 91);
        this.butPostcards.Name = "butPostcards";
        this.butPostcards.Size = new System.Drawing.Size(87, 26);
        this.butPostcards.TabIndex = 16;
        this.butPostcards.Text = "Preview";
        this.butPostcards.Click += new System.EventHandler(this.butPostcards_Click);
        //
        // FormRpBirthday
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(660, 264);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.Name = "FormRpBirthday";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Birthday Report";
        this.Load += new System.EventHandler(this.FormRpBirthday_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox1.PerformLayout();
        this.groupBox4.ResumeLayout(false);
        this.groupBox4.PerformLayout();
        this.ResumeLayout(false);
    }

    private void formRpBirthday_Load(Object sender, System.EventArgs e) throws Exception {
        if (CultureInfo.CurrentCulture.Name.EndsWith("US"))
        {
            //United States
            cultureDateFormat = "MM/dd";
        }
        else
        {
            cultureDateFormat = "dd/MM";
        } 
        setNextMonth();
        textPostcardMsg.Text = PrefC.getString(PrefName.BirthdayPostcardMsg);
    }

    private void butLeft_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        DateTime dateFrom = DateTime.ParseExact(textDateFrom.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        DateTime dateTo = DateTime.ParseExact(textDateTo.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        boolean toLastDay = false;
        if (CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month) == dateTo.Day)
        {
            toLastDay = true;
        }
         
        textDateFrom.Text = dateFrom.AddMonths(-1).ToString(cultureDateFormat);
        dateTo = dateTo.AddMonths(-1);
        textDateTo.Text = dateTo.ToString(cultureDateFormat);
        if (toLastDay)
        {
            textDateTo.Text = (new DateTime(dateTo.Year, dateTo.Month, CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month))).ToString(cultureDateFormat);
        }
         
    }

    private void butMonth_Click(Object sender, System.EventArgs e) throws Exception {
        setNextMonth();
    }

    private void butRight_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        DateTime dateFrom = DateTime.ParseExact(textDateFrom.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        DateTime dateTo = DateTime.ParseExact(textDateTo.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        //textDateFrom.Text=dateFrom.AddMonths(-1).ToShortDateString();
        //textDateTo.Text=dateTo.AddMonths(-1).ToShortDateString();
        boolean toLastDay = false;
        if (CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month) == dateTo.Day)
        {
            toLastDay = true;
        }
         
        textDateFrom.Text = dateFrom.AddMonths(1).ToString(cultureDateFormat);
        dateTo = dateTo.AddMonths(1);
        textDateTo.Text = dateTo.ToString(cultureDateFormat);
        if (toLastDay)
        {
            textDateTo.Text = (new DateTime(dateTo.Year, dateTo.Month, CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month))).ToString(cultureDateFormat);
        }
         
    }

    private void setNextMonth() throws Exception {
        textDateFrom.Text = (new DateTime(DateTime.Today.AddMonths(1).Year, DateTime.Today.AddMonths(1).Month, 1)).ToString(cultureDateFormat);
        textDateTo.Text = (new DateTime(DateTime.Today.AddMonths(2).Year, DateTime.Today.AddMonths(2).Month, 1)).AddDays(-1).ToString(cultureDateFormat);
        errorProvider1.SetError(textDateFrom, "");
        errorProvider1.SetError(textDateTo, "");
    }

    private void textDateFrom_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        try
        {
            DateTime date = DateTime.ParseExact(textDateFrom.Text, cultureDateFormat, CultureInfo.CurrentCulture);
            textDateFrom.Text = date.ToString(cultureDateFormat);
            errorProvider1.SetError(textDateFrom, "");
        }
        catch (Exception __dummyCatchVar0)
        {
            errorProvider1.SetError(textDateFrom, "Invalid Date");
        }
    
    }

    private void textDateTo_Validating(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        try
        {
            DateTime date = DateTime.ParseExact(textDateTo.Text, cultureDateFormat, CultureInfo.CurrentCulture);
            textDateTo.Text = date.ToString(cultureDateFormat);
            //allows users in other countries to set their own format
            errorProvider1.SetError(textDateTo, "");
        }
        catch (Exception __dummyCatchVar1)
        {
            errorProvider1.SetError(textDateTo, "Invalid Date");
        }
    
    }

    private void butSave_Click(Object sender, EventArgs e) throws Exception {
        if (Prefs.UpdateString(PrefName.BirthdayPostcardMsg, textPostcardMsg.Text))
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
    }

    private void butPostcards_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        DateTime dateFrom = DateTime.ParseExact(textDateFrom.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        DateTime dateTo = DateTime.ParseExact(textDateTo.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        //DateTime dateFrom=PIn.PDate(textDateFrom.Text);
        //DateTime dateTo=PIn.PDate(textDateTo.Text);
        if (dateTo < dateFrom)
        {
            MsgBox.show(this,"To date cannot be before From date.");
            return ;
        }
         
        BirthdayTable = Patients.getBirthdayList(dateFrom,dateTo);
        if (BirthdayTable.Rows.Count == 0)
        {
            MsgBox.show(this,"No postcards to preview.");
            return ;
        }
         
        pagesPrinted = 0;
        patientsPrinted = 0;
        pd = new PrintDocument();
        pd.PrintPage += new PrintPageEventHandler(this.pdCards_PrintPage);
        pd.OriginAtMargins = true;
        pd.DefaultPageSettings.Margins = new Margins(0, 0, 0, 0);
        if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 1)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("Postcard", 400, 600);
            pd.DefaultPageSettings.Landscape = true;
        }
        else if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 3)
        {
            pd.DefaultPageSettings.PaperSize = new PaperSize("Postcard", 850, 1100);
        }
        else
        {
            //4
            pd.DefaultPageSettings.PaperSize = new PaperSize("Postcard", 850, 1100);
            pd.DefaultPageSettings.Landscape = true;
        }  
        printPreview = new OpenDental.UI.FormPrintPreview(PrintSituation.Postcard, pd, (int)Math.Ceiling((double)BirthdayTable.Rows.Count / (double)PrefC.getLong(PrefName.RecallPostcardsPerSheet)), 0, "Birthday report postcards printed");
        printPreview.ShowDialog();
    }

    /**
    * raised for each page to be printed.
    */
    private void pdCards_PrintPage(Object sender, PrintPageEventArgs ev) throws Exception {
        int totalPages = (int)Math.Ceiling((double)BirthdayTable.Rows.Count / (double)PrefC.getLong(PrefName.RecallPostcardsPerSheet));
        Graphics g = ev.Graphics;
        float yPos = 0;
        //these refer to the upper left origin of each postcard
        float xPos = 0;
        String str = new String();
        int age = new int();
        DateTime birthdate = new DateTime();
        while (yPos < ev.PageBounds.Height - 100 && patientsPrinted < BirthdayTable.Rows.Count)
        {
            //Return Address--------------------------------------------------------------------------
            if (PrefC.getBool(PrefName.RecallCardsShowReturnAdd))
            {
                str = PrefC.getString(PrefName.PracticeTitle) + "\r\n";
                g.DrawString(str, new Font(FontFamily.GenericSansSerif, 9, FontStyle.Bold), Brushes.Black, xPos + 45, yPos + 60);
                str = PrefC.getString(PrefName.PracticeAddress) + "\r\n";
                if (!StringSupport.equals(PrefC.getString(PrefName.PracticeAddress2), ""))
                {
                    str += PrefC.getString(PrefName.PracticeAddress2) + "\r\n";
                }
                 
                str += PrefC.getString(PrefName.PracticeCity) + ",  " + PrefC.getString(PrefName.PracticeST) + "  " + PrefC.getString(PrefName.PracticeZip) + "\r\n";
                String phone = PrefC.getString(PrefName.PracticePhone);
                if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US") && phone.Length == 10)
                {
                    str += "(" + phone.Substring(0, 3) + ")" + phone.Substring(3, 3) + "-" + phone.Substring(6);
                }
                else
                {
                    //any other phone format
                    str += phone;
                } 
                g.DrawString(str, new Font(FontFamily.GenericSansSerif, 8), Brushes.Black, xPos + 45, yPos + 75);
            }
             
            //Body text-------------------------------------------------------------------------------
            str = textPostcardMsg.Text;
            if (!StringSupport.equals(BirthdayTable.Rows[patientsPrinted]["Preferred"].ToString(), ""))
            {
                str = str.Replace("?FName", BirthdayTable.Rows[patientsPrinted]["Preferred"].ToString());
            }
            else
            {
                str = str.Replace("?FName", BirthdayTable.Rows[patientsPrinted]["FName"].ToString());
            } 
            birthdate = PIn.Date(BirthdayTable.Rows[patientsPrinted]["Birthdate"].ToString());
            //age=Shared.DateToAge(birthdate,PIn.PDate(textDateTo.Text).AddDays(1));//age on the day after the range
            age = PIn.Int(BirthdayTable.Rows[patientsPrinted]["Age"].ToString());
            str = str.Replace("?AgeOrdinal", Shared.numberToOrdinal(age));
            str = str.Replace("?Age", age.ToString());
            g.DrawString(str, new Font(FontFamily.GenericSansSerif, 10), Brushes.Black, new RectangleF(xPos + 45, yPos + 180, 250, 190));
            //Patient's Address-----------------------------------------------------------------------
            //+BirthdayTable.Rows[patientsPrinted]["MiddleI"].ToString()+" "
            str = BirthdayTable.Rows[patientsPrinted]["FName"].ToString() + " " + BirthdayTable.Rows[patientsPrinted]["LName"].ToString() + "\r\n" + BirthdayTable.Rows[patientsPrinted]["Address"].ToString() + "\r\n";
            if (!StringSupport.equals(BirthdayTable.Rows[patientsPrinted]["Address2"].ToString(), ""))
            {
                str += BirthdayTable.Rows[patientsPrinted]["Address2"].ToString() + "\r\n";
            }
             
            str += BirthdayTable.Rows[patientsPrinted]["City"].ToString() + ", " + BirthdayTable.Rows[patientsPrinted]["State"].ToString() + "   " + BirthdayTable.Rows[patientsPrinted]["Zip"].ToString() + "\r\n";
            g.DrawString(str, new Font(FontFamily.GenericSansSerif, 11), Brushes.Black, xPos + 320, yPos + 240);
            if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 1)
            {
                yPos += 400;
            }
            else if (PrefC.getLong(PrefName.RecallPostcardsPerSheet) == 3)
            {
                yPos += 366;
            }
            else
            {
                //4
                xPos += 550;
                if (xPos > 1000)
                {
                    xPos = 0;
                    yPos += 425;
                }
                 
            }  
            patientsPrinted++;
        }
        //while
        pagesPrinted++;
        if (pagesPrinted == totalPages)
        {
            ev.HasMorePages = false;
            pagesPrinted = 0;
            patientsPrinted = 0;
        }
        else
        {
            ev.HasMorePages = true;
        } 
    }

    private void butReport_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(errorProvider1.GetError(textDateTo), ""))
        {
            MsgBox.show(this,"Please fix data entry errors first.");
            return ;
        }
         
        DateTime dateFrom = DateTime.ParseExact(textDateFrom.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        DateTime dateTo = DateTime.ParseExact(textDateTo.Text, cultureDateFormat, CultureInfo.CurrentCulture);
        if (dateTo < dateFrom)
        {
            MsgBox.show(this,"To date cannot be before From date.");
            return ;
        }
         
        ReportLikeCrystal report = new ReportLikeCrystal();
        report.setReportName(Lan.g(this,"Birthdays"));
        report.addTitle(Lan.g(this,"Birthdays"));
        report.addSubTitle(PrefC.getString(PrefName.PracticeTitle));
        report.AddSubTitle(dateFrom.ToString(cultureDateFormat) + " - " + dateTo.ToString(cultureDateFormat));
        /*report.Query=@"SELECT LName,FName,Address,Address2,City,State,Zip,Birthdate,Birthdate
        				FROM patient 
        				WHERE SUBSTRING(Birthdate,6,5) >= '"+dateFrom.ToString("MM-dd")+"' "
        				+"AND SUBSTRING(Birthdate,6,5) <= '"+dateTo.ToString("MM-dd")+"' "
        				+"AND PatStatus=0	ORDER BY LName,FName";*/
        report.addColumn("LName",90,FieldValueType.String);
        report.addColumn("FName",90,FieldValueType.String);
        report.addColumn("Preferred",90,FieldValueType.String);
        report.addColumn("Address",90,FieldValueType.String);
        report.addColumn("Address2",90,FieldValueType.String);
        report.addColumn("City",75,FieldValueType.String);
        report.addColumn("State",60,FieldValueType.String);
        report.addColumn("Zip",75,FieldValueType.String);
        report.addColumn("Birthdate",75,FieldValueType.Date);
        report.getLastRO(ReportObjectKind.FieldObject).setFormatString("d");
        report.addColumn("Age",45,FieldValueType.Integer);
        report.addPageNum();
        report.setReportTable(Patients.getBirthdayList(dateFrom,dateTo));
        //if(!report.SubmitQuery()){
        //	return;
        //}
        FormReportLikeCrystal FormR = new FormReportLikeCrystal(report);
        FormR.ShowDialog();
        DialogResult = DialogResult.OK;
    }

}


