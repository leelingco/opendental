//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:06 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpNewPatients;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.Properties.Resources;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;

/**
* 
*/
public class FormRpNewPatients  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.ComponentModel.Container components = null;
    private ListBox listProv = new ListBox();
    private Label label1 = new Label();
    private GroupBox groupBox2 = new GroupBox();
    private OpenDental.UI.Button butRight;
    private OpenDental.UI.Button butThis;
    private Label label2 = new Label();
    private OpenDental.ValidDate textDateFrom;
    private OpenDental.ValidDate textDateTo;
    private Label label3 = new Label();
    private OpenDental.UI.Button butLeft;
    private TextBox textToday = new TextBox();
    private Label label4 = new Label();
    private CheckBox checkAddress = new CheckBox();
    private CheckBox checkProd = new CheckBox();
    private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpNewPatients() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpNewPatients.class);
        this.listProv = new System.Windows.Forms.ListBox();
        this.label1 = new System.Windows.Forms.Label();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.textToday = new System.Windows.Forms.TextBox();
        this.label4 = new System.Windows.Forms.Label();
        this.checkAddress = new System.Windows.Forms.CheckBox();
        this.checkProd = new System.Windows.Forms.CheckBox();
        this.butRight = new OpenDental.UI.Button();
        this.butThis = new OpenDental.UI.Button();
        this.textDateFrom = new OpenDental.ValidDate();
        this.textDateTo = new OpenDental.ValidDate();
        this.butLeft = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupBox2.SuspendLayout();
        this.SuspendLayout();
        //
        // listProv
        //
        this.listProv.Location = new System.Drawing.Point(42, 61);
        this.listProv.Name = "listProv";
        this.listProv.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProv.Size = new System.Drawing.Size(165, 186);
        this.listProv.TabIndex = 36;
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(41, 42);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(104, 16);
        this.label1.TabIndex = 35;
        this.label1.Text = "Providers";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.butRight);
        this.groupBox2.Controls.Add(this.butThis);
        this.groupBox2.Controls.Add(this.label2);
        this.groupBox2.Controls.Add(this.textDateFrom);
        this.groupBox2.Controls.Add(this.textDateTo);
        this.groupBox2.Controls.Add(this.label3);
        this.groupBox2.Controls.Add(this.butLeft);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(271, 55);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(281, 144);
        this.groupBox2.TabIndex = 46;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Date Range";
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(9, 79);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(82, 18);
        this.label2.TabIndex = 37;
        this.label2.Text = "From";
        this.label2.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(7, 105);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(82, 18);
        this.label3.TabIndex = 39;
        this.label3.Text = "To";
        this.label3.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textToday
        //
        this.textToday.Location = new System.Drawing.Point(366, 29);
        this.textToday.Name = "textToday";
        this.textToday.ReadOnly = true;
        this.textToday.Size = new System.Drawing.Size(100, 20);
        this.textToday.TabIndex = 45;
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(237, 32);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(127, 20);
        this.label4.TabIndex = 44;
        this.label4.Text = "Today\'s Date";
        this.label4.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // checkAddress
        //
        this.checkAddress.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkAddress.Location = new System.Drawing.Point(42, 273);
        this.checkAddress.Name = "checkAddress";
        this.checkAddress.Size = new System.Drawing.Size(402, 35);
        this.checkAddress.TabIndex = 47;
        this.checkAddress.Text = "Include address information.  Doesn\'t fit easily on a printout, but useful if you" + " are exporting for letter merge.";
        this.checkAddress.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkAddress.UseVisualStyleBackColor = true;
        //
        // checkProd
        //
        this.checkProd.CheckAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkProd.Location = new System.Drawing.Point(42, 313);
        this.checkProd.Name = "checkProd";
        this.checkProd.Size = new System.Drawing.Size(381, 22);
        this.checkProd.TabIndex = 48;
        this.checkProd.Text = "Exclude patients with no production.";
        this.checkProd.TextAlign = System.Drawing.ContentAlignment.TopLeft;
        this.checkProd.UseVisualStyleBackColor = true;
        //
        // butRight
        //
        this.butRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRight.setAutosize(true);
        this.butRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRight.setCornerRadius(4F);
        this.butRight.Image = Resources.getRight();
        this.butRight.Location = new System.Drawing.Point(205, 33);
        this.butRight.Name = "butRight";
        this.butRight.Size = new System.Drawing.Size(45, 24);
        this.butRight.TabIndex = 46;
        this.butRight.Click += new System.EventHandler(this.butRight_Click);
        //
        // butThis
        //
        this.butThis.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butThis.setAutosize(true);
        this.butThis.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butThis.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butThis.setCornerRadius(4F);
        this.butThis.Location = new System.Drawing.Point(95, 33);
        this.butThis.Name = "butThis";
        this.butThis.Size = new System.Drawing.Size(101, 24);
        this.butThis.TabIndex = 45;
        this.butThis.Text = "This Month";
        this.butThis.Click += new System.EventHandler(this.butThis_Click);
        //
        // textDateFrom
        //
        this.textDateFrom.Location = new System.Drawing.Point(95, 77);
        this.textDateFrom.Name = "textDateFrom";
        this.textDateFrom.Size = new System.Drawing.Size(100, 20);
        this.textDateFrom.TabIndex = 43;
        //
        // textDateTo
        //
        this.textDateTo.Location = new System.Drawing.Point(95, 104);
        this.textDateTo.Name = "textDateTo";
        this.textDateTo.Size = new System.Drawing.Size(100, 20);
        this.textDateTo.TabIndex = 44;
        //
        // butLeft
        //
        this.butLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butLeft.setAutosize(true);
        this.butLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butLeft.setCornerRadius(4F);
        this.butLeft.Image = Resources.getLeft();
        this.butLeft.Location = new System.Drawing.Point(41, 33);
        this.butLeft.Name = "butLeft";
        this.butLeft.Size = new System.Drawing.Size(45, 24);
        this.butLeft.TabIndex = 44;
        this.butLeft.Click += new System.EventHandler(this.butLeft_Click);
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
        this.butCancel.Location = new System.Drawing.Point(542, 316);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 4;
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
        this.butOK.Location = new System.Drawing.Point(542, 284);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormRpNewPatients
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(651, 368);
        this.Controls.Add(this.checkProd);
        this.Controls.Add(this.checkAddress);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.textToday);
        this.Controls.Add(this.label4);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpNewPatients";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "New Patients";
        this.Load += new System.EventHandler(this.FormNewPatients_Load);
        this.groupBox2.ResumeLayout(false);
        this.groupBox2.PerformLayout();
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formNewPatients_Load(Object sender, System.EventArgs e) throws Exception {
        textToday.Text = DateTime.Today.ToShortDateString();
        //always defaults to the current month
        textDateFrom.Text = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1)).ToShortDateString();
        textDateTo.Text = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, DateTime.DaysInMonth(DateTime.Today.Year, DateTime.Today.Month))).ToShortDateString();
        listProv.Items.Add(Lan.g(this,"all"));
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            listProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        listProv.SetSelected(0, true);
    }

    private void butThis_Click(Object sender, EventArgs e) throws Exception {
        textDateFrom.Text = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1)).ToShortDateString();
        textDateTo.Text = (new DateTime(DateTime.Today.Year, DateTime.Today.Month, DateTime.DaysInMonth(DateTime.Today.Year, DateTime.Today.Month))).ToShortDateString();
    }

    private void butLeft_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = PIn.Date(textDateTo.Text);
        boolean toLastDay = false;
        if (CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month) == dateTo.Day)
        {
            toLastDay = true;
        }
         
        textDateFrom.Text = dateFrom.AddMonths(-1).ToShortDateString();
        textDateTo.Text = dateTo.AddMonths(-1).ToShortDateString();
        dateTo = PIn.Date(textDateTo.Text);
        if (toLastDay)
        {
            textDateTo.Text = (new DateTime(dateTo.Year, dateTo.Month, CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month))).ToShortDateString();
        }
         
    }

    private void butRight_Click(Object sender, EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = PIn.Date(textDateTo.Text);
        boolean toLastDay = false;
        if (CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month) == dateTo.Day)
        {
            toLastDay = true;
        }
         
        textDateFrom.Text = dateFrom.AddMonths(1).ToShortDateString();
        textDateTo.Text = dateTo.AddMonths(1).ToShortDateString();
        dateTo = PIn.Date(textDateTo.Text);
        if (toLastDay)
        {
            textDateTo.Text = (new DateTime(dateTo.Year, dateTo.Month, CultureInfo.CurrentCulture.Calendar.GetDaysInMonth(dateTo.Year, dateTo.Month))).ToShortDateString();
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!StringSupport.equals(textDateFrom.errorProvider1.GetError(textDateFrom), "") || !StringSupport.equals(textDateTo.errorProvider1.GetError(textDateTo), ""))
        {
            MessageBox.Show(Lan.g(this,"Please fix data entry errors first."));
            return ;
        }
         
        if (listProv.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"At least one provider must be selected.");
            return ;
        }
         
        if (listProv.SelectedIndices[0] == 0 && listProv.SelectedIndices.Count > 1)
        {
            MsgBox.show(this,"You cannot select 'all' providers as well as specific providers.");
            return ;
        }
         
        DateTime dateFrom = PIn.Date(textDateFrom.Text);
        DateTime dateTo = PIn.Date(textDateTo.Text);
        String whereProv = "";
        if (listProv.SelectedIndices[0] != 0)
        {
            for (int i = 0;i < listProv.SelectedIndices.Count;i++)
            {
                if (i == 0)
                {
                    whereProv += " WHERE (";
                }
                else
                {
                    whereProv += "OR ";
                } 
                whereProv += "patient.PriProv = " + POut.Long(ProviderC.getListShort()[listProv.SelectedIndices[i] - 1].ProvNum) + " ";
            }
            whereProv += ") ";
        }
         
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SET @pos=0;\r\n" + 
        "SELECT @pos:=@pos+1 patCount,result.* FROM (SELECT dateFirstProc,patient.LName,patient.FName," + DbHelper.concat("referral.LName","IF(referral.FName='','',',')","referral.FName") + " refname,SUM(procedurelog.ProcFee) ";
        //\"$HowMuch\"";
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
        {
            report.Query += "$HowMuch";
        }
        else
        {
            //Oracle needs quotes.
            report.Query += "\"$HowMuch\"";
        } 
        if (checkAddress.Checked)
        {
            report.Query += ",patient.Preferred,patient.Address,patient.Address2,patient.City,patient.State,patient.Zip";
        }
         
        report.Query += " FROM\r\n" + 
        "\t\t\t\t(SELECT PatNum, MIN(ProcDate) dateFirstProc FROM procedurelog\r\n" + 
        "\t\t\t\tWHERE ProcStatus=2 GROUP BY PatNum\r\n" + 
        "\t\t\t\tHAVING dateFirstProc >= " + POut.Date(dateFrom) + " " + "AND DATE(dateFirstProc) <= " + POut.Date(dateTo) + " ) table1 " + "INNER JOIN patient ON table1.PatNum=patient.PatNum \r\n" + 
        "\t\t\t\tLEFT JOIN procedurelog ON patient.PatNum=procedurelog.PatNum AND procedurelog.ProcStatus=2\r\n" + 
        "\t\t\t\tLEFT JOIN refattach ON patient.PatNum=refattach.PatNum AND refattach.IsFrom=1\r\n" + 
        "\t\t\t\tAND refattach.ItemOrder=(SELECT MIN(ra.ItemOrder) FROM refattach ra WHERE ra.PatNum=refattach.PatNum AND ra.IsFrom=1)\r\n" + 
        "\t\t\t\tLEFT JOIN referral ON referral.ReferralNum=refattach.ReferralNum " + whereProv;
        report.Query += "GROUP BY patient.LName,patient.FName,patient.PatNum," + DbHelper.concat("referral.LName","IF(referral.FName='','',',')","referral.FName");
        if (checkAddress.Checked)
        {
            report.Query += ",patient.Preferred,patient.Address,patient.Address2,patient.City,patient.State,patient.Zip ";
        }
         
        if (checkProd.Checked)
        {
            if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
            {
                report.Query += "HAVING $HowMuch > 0 ";
            }
            else
            {
                //Oracle needs quotes.
                report.Query += "HAVING \"$HowMuch\" > 0 ";
            } 
        }
         
        report.Query += "ORDER BY dateFirstProc,patient.LName,patient.FName) result";
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        report.Title = "New Patients";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        if (listProv.SelectedIndices[0] == 0)
        {
            report.SubTitle.Add(Lan.g(this,"All Providers"));
            report.SubTitle.Add(dateFrom.ToString("d") + " - " + dateTo.ToString("d"));
        }
        else if (listProv.SelectedIndices.Count == 1)
        {
            report.SubTitle.Add(Lan.g(this,"Prov: ") + ProviderC.getListShort()[listProv.SelectedIndices[0] - 1].GetLongDesc());
            report.SubTitle.Add(dateFrom.ToString("d") + " - " + dateTo.ToString("d"));
        }
        else
        {
            //I'm too lazy to build a description for multiple providers as well as ensure that it fits the space.
            report.SubTitle.Add(dateFrom.ToString("d") + " - " + dateTo.ToString("d"));
        }  
        report.setColumnPos(this,0,"#",40);
        report.setColumnPos(this,1,"Date",120);
        report.setColumnPos(this,2,"Last Name",210);
        report.setColumnPos(this,3,"First Name",300);
        report.setColumnPos(this,4,"Referral",380);
        report.SetColumnPos(this, 5, "Production", 450, HorizontalAlignment.Right);
        if (checkAddress.Checked)
        {
            report.setColumnPos(this,6,"Pref'd",500);
            report.setColumnPos(this,7,"Address",570);
            report.setColumnPos(this,8,"Add2",630);
            report.setColumnPos(this,9,"City",680);
            report.setColumnPos(this,10,"ST",730);
            report.setColumnPos(this,11,"Zip",880);
        }
         
        //off the right side
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


