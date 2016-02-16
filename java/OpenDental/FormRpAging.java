//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:03 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpAging;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.POut;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DatabaseType;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Ledgers;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProviderC;

/**
* 
*/
public class FormRpAging  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private FormQuery FormQuery2;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.ValidDate textDate;
    private System.Windows.Forms.ListBox listBillType = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.RadioButton radio30 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radio90 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radio60 = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.CheckBox checkIncludeNeg = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.CheckBox checkOnlyNeg = new System.Windows.Forms.CheckBox();
    private System.Windows.Forms.CheckBox checkExcludeInactive = new System.Windows.Forms.CheckBox();
    private ListBox listProv = new ListBox();
    private Label label3 = new Label();
    private CheckBox checkProvAll = new CheckBox();
    private CheckBox checkBillTypesAll = new CheckBox();
    private CheckBox checkBadAddress = new CheckBox();
    private System.Windows.Forms.RadioButton radioAny = new System.Windows.Forms.RadioButton();
    /**
    * 
    */
    public FormRpAging() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpAging.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.radio30 = new System.Windows.Forms.RadioButton();
        this.radio90 = new System.Windows.Forms.RadioButton();
        this.radio60 = new System.Windows.Forms.RadioButton();
        this.radioAny = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.textDate = new OpenDental.ValidDate();
        this.listBillType = new System.Windows.Forms.ListBox();
        this.label2 = new System.Windows.Forms.Label();
        this.checkIncludeNeg = new System.Windows.Forms.CheckBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.checkOnlyNeg = new System.Windows.Forms.CheckBox();
        this.checkExcludeInactive = new System.Windows.Forms.CheckBox();
        this.listProv = new System.Windows.Forms.ListBox();
        this.label3 = new System.Windows.Forms.Label();
        this.checkProvAll = new System.Windows.Forms.CheckBox();
        this.checkBillTypesAll = new System.Windows.Forms.CheckBox();
        this.checkBadAddress = new System.Windows.Forms.CheckBox();
        this.groupBox1.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(637, 396);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
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
        this.butOK.Location = new System.Drawing.Point(637, 362);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 3;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.radio30);
        this.groupBox1.Controls.Add(this.radio90);
        this.groupBox1.Controls.Add(this.radio60);
        this.groupBox1.Controls.Add(this.radioAny);
        this.groupBox1.Location = new System.Drawing.Point(57, 109);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(175, 120);
        this.groupBox1.TabIndex = 1;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Age of Account";
        //
        // radio30
        //
        this.radio30.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radio30.Location = new System.Drawing.Point(12, 44);
        this.radio30.Name = "radio30";
        this.radio30.Size = new System.Drawing.Size(152, 16);
        this.radio30.TabIndex = 1;
        this.radio30.Text = "Over 30 Days";
        //
        // radio90
        //
        this.radio90.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radio90.Location = new System.Drawing.Point(12, 90);
        this.radio90.Name = "radio90";
        this.radio90.Size = new System.Drawing.Size(152, 18);
        this.radio90.TabIndex = 3;
        this.radio90.Text = "Over 90 Days";
        //
        // radio60
        //
        this.radio60.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radio60.Location = new System.Drawing.Point(12, 66);
        this.radio60.Name = "radio60";
        this.radio60.Size = new System.Drawing.Size(152, 18);
        this.radio60.TabIndex = 2;
        this.radio60.Text = "Over 60 Days";
        //
        // radioAny
        //
        this.radioAny.Checked = true;
        this.radioAny.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioAny.Location = new System.Drawing.Point(12, 20);
        this.radioAny.Name = "radioAny";
        this.radioAny.Size = new System.Drawing.Size(152, 18);
        this.radioAny.TabIndex = 0;
        this.radioAny.TabStop = true;
        this.radioAny.Text = "Any Balance";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(8, 50);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(114, 14);
        this.label1.TabIndex = 11;
        this.label1.Text = "As Of Date";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textDate
        //
        this.textDate.Location = new System.Drawing.Point(126, 48);
        this.textDate.Name = "textDate";
        this.textDate.Size = new System.Drawing.Size(106, 20);
        this.textDate.TabIndex = 0;
        //
        // listBillType
        //
        this.listBillType.Location = new System.Drawing.Point(335, 69);
        this.listBillType.Name = "listBillType";
        this.listBillType.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listBillType.Size = new System.Drawing.Size(158, 186);
        this.listBillType.TabIndex = 2;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(332, 25);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(176, 16);
        this.label2.TabIndex = 14;
        this.label2.Text = "Billing Types";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkIncludeNeg
        //
        this.checkIncludeNeg.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkIncludeNeg.Location = new System.Drawing.Point(9, 28);
        this.checkIncludeNeg.Name = "checkIncludeNeg";
        this.checkIncludeNeg.Size = new System.Drawing.Size(194, 20);
        this.checkIncludeNeg.TabIndex = 17;
        this.checkIncludeNeg.Text = "Include negative balances";
        this.checkIncludeNeg.Click += new System.EventHandler(this.checkIncludeNeg_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.checkOnlyNeg);
        this.groupBox2.Controls.Add(this.checkIncludeNeg);
        this.groupBox2.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.groupBox2.Location = new System.Drawing.Point(57, 256);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(225, 84);
        this.groupBox2.TabIndex = 18;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Negative Balances";
        //
        // checkOnlyNeg
        //
        this.checkOnlyNeg.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkOnlyNeg.Location = new System.Drawing.Point(9, 55);
        this.checkOnlyNeg.Name = "checkOnlyNeg";
        this.checkOnlyNeg.Size = new System.Drawing.Size(210, 19);
        this.checkOnlyNeg.TabIndex = 18;
        this.checkOnlyNeg.Text = "Only show negative balances";
        this.checkOnlyNeg.Click += new System.EventHandler(this.checkOnlyNeg_Click);
        //
        // checkExcludeInactive
        //
        this.checkExcludeInactive.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkExcludeInactive.Location = new System.Drawing.Point(66, 363);
        this.checkExcludeInactive.Name = "checkExcludeInactive";
        this.checkExcludeInactive.Size = new System.Drawing.Size(248, 18);
        this.checkExcludeInactive.TabIndex = 19;
        this.checkExcludeInactive.Text = "Exclude inactive patients";
        //
        // listProv
        //
        this.listProv.Location = new System.Drawing.Point(544, 69);
        this.listProv.Name = "listProv";
        this.listProv.SelectionMode = System.Windows.Forms.SelectionMode.MultiExtended;
        this.listProv.Size = new System.Drawing.Size(163, 186);
        this.listProv.TabIndex = 39;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(541, 25);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(104, 16);
        this.label3.TabIndex = 38;
        this.label3.Text = "Providers";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // checkProvAll
        //
        this.checkProvAll.Checked = true;
        this.checkProvAll.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkProvAll.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkProvAll.Location = new System.Drawing.Point(544, 46);
        this.checkProvAll.Name = "checkProvAll";
        this.checkProvAll.Size = new System.Drawing.Size(145, 18);
        this.checkProvAll.TabIndex = 40;
        this.checkProvAll.Text = "All";
        this.checkProvAll.Click += new System.EventHandler(this.checkProvAll_Click);
        //
        // checkBillTypesAll
        //
        this.checkBillTypesAll.Checked = true;
        this.checkBillTypesAll.CheckState = System.Windows.Forms.CheckState.Checked;
        this.checkBillTypesAll.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkBillTypesAll.Location = new System.Drawing.Point(335, 46);
        this.checkBillTypesAll.Name = "checkBillTypesAll";
        this.checkBillTypesAll.Size = new System.Drawing.Size(145, 18);
        this.checkBillTypesAll.TabIndex = 41;
        this.checkBillTypesAll.Text = "All";
        this.checkBillTypesAll.Click += new System.EventHandler(this.checkBillTypesAll_Click);
        //
        // checkBadAddress
        //
        this.checkBadAddress.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.checkBadAddress.Location = new System.Drawing.Point(66, 390);
        this.checkBadAddress.Name = "checkBadAddress";
        this.checkBadAddress.Size = new System.Drawing.Size(248, 18);
        this.checkBadAddress.TabIndex = 43;
        this.checkBadAddress.Text = "Exclude bad addresses (no zipcode)";
        //
        // FormRpAging
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(742, 450);
        this.Controls.Add(this.checkBadAddress);
        this.Controls.Add(this.checkBillTypesAll);
        this.Controls.Add(this.checkProvAll);
        this.Controls.Add(this.listProv);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.checkExcludeInactive);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.textDate);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.listBillType);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox1);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpAging";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Aging of Accounts Receivable Report";
        this.Load += new System.EventHandler(this.FormAging_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formAging_Load(Object sender, System.EventArgs e) throws Exception {
        DateTime lastAgingDate = PrefC.getDate(PrefName.DateLastAging);
        if (lastAgingDate.Year < 1880)
        {
            textDate.Text = "";
        }
        else if (PrefC.getBool(PrefName.AgingCalculatedMonthlyInsteadOfDaily))
        {
            textDate.Text = lastAgingDate.ToShortDateString();
        }
        else
        {
            textDate.Text = DateTime.Today.ToShortDateString();
        }  
        for (int i = 0;i < DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()].Length;i++)
        {
            listBillType.Items.Add(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][i].ItemName);
        }
        if (listBillType.Items.Count > 0)
        {
            listBillType.SelectedIndex = 0;
        }
         
        listBillType.Visible = false;
        checkBillTypesAll.Checked = true;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            listProv.Items.Add(ProviderC.getListShort()[i].GetLongDesc());
        }
        if (listProv.Items.Count > 0)
        {
            listProv.SelectedIndex = 0;
        }
         
        checkProvAll.Checked = true;
        listProv.Visible = false;
    }

    private void checkBillTypesAll_Click(Object sender, EventArgs e) throws Exception {
        if (checkBillTypesAll.Checked)
        {
            listBillType.Visible = false;
        }
        else
        {
            listBillType.Visible = true;
        } 
    }

    private void checkProvAll_Click(Object sender, EventArgs e) throws Exception {
        if (checkProvAll.Checked)
        {
            listProv.Visible = false;
        }
        else
        {
            listProv.Visible = true;
        } 
    }

    private void checkIncludeNeg_Click(Object sender, System.EventArgs e) throws Exception {
        if (checkIncludeNeg.Checked)
        {
            checkOnlyNeg.Checked = false;
        }
         
    }

    private void checkOnlyNeg_Click(Object sender, System.EventArgs e) throws Exception {
        if (checkOnlyNeg.Checked)
        {
            checkIncludeNeg.Checked = false;
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (!checkBillTypesAll.Checked && listBillType.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"At least one billing type must be selected.");
            return ;
        }
         
        if (!checkProvAll.Checked && listProv.SelectedIndices.Count == 0)
        {
            MsgBox.show(this,"At least one provider must be selected.");
            return ;
        }
         
        if (!StringSupport.equals(textDate.errorProvider1.GetError(textDate), ""))
        {
            MsgBox.show(this,"Invalid date.");
            return ;
        }
         
        DateTime asOfDate = PIn.Date(textDate.Text);
        //The aging report always show historical numbers based on the date entered.
        Ledgers.ComputeAging(0, asOfDate, true);
        ReportSimpleGrid report = new ReportSimpleGrid();
        String cmd = "SELECT ";
        if (PrefC.getBool(PrefName.ReportsShowPatNum))
        {
            cmd += DbHelper.concat("Cast(PatNum AS CHAR)","'-'","LName","', '","FName","' '","MiddleI");
        }
        else
        {
            cmd += DbHelper.concat("LName","', '","FName","' '","MiddleI");
        } 
        cmd += ",Bal_0_30,Bal_31_60,Bal_61_90,BalOver90" + ",BalTotal " + ",InsEst" + ",BalTotal-InsEst AS ";
        //\"$pat\" ";
        if (OpenDentBusiness.DataConnection.DBtype == DatabaseType.MySql)
        {
            cmd += "$pat ";
        }
        else
        {
            //Oracle needs quotes.
            cmd += "\"$pat\" ";
        } 
        cmd += "FROM patient " + "WHERE ";
        if (checkExcludeInactive.Checked)
        {
            cmd += "(patstatus != 2) AND ";
        }
         
        if (checkBadAddress.Checked)
        {
            cmd += "(zip !='') AND ";
        }
         
        if (checkOnlyNeg.Checked)
        {
            cmd += "BalTotal < '-.005' ";
        }
        else
        {
            if (radioAny.Checked)
            {
                cmd += "(Bal_0_30 > '.005' OR Bal_31_60 > '.005' OR Bal_61_90 > '.005' OR BalOver90 > '.005'";
            }
            else if (radio30.Checked)
            {
                cmd += "(Bal_31_60 > '.005' OR Bal_61_90 > '.005' OR BalOver90 > '.005'";
            }
            else if (radio60.Checked)
            {
                cmd += "(Bal_61_90 > '.005' OR BalOver90 > '.005'";
            }
            else if (radio90.Checked)
            {
                cmd += "(BalOver90 > '.005'";
            }
                
            if (checkIncludeNeg.Checked)
            {
                cmd += " OR BalTotal < '-.005'";
            }
             
            cmd += ") ";
        } 
        if (!checkBillTypesAll.Checked)
        {
            for (int i = 0;i < listBillType.SelectedIndices.Count;i++)
            {
                if (i == 0)
                {
                    cmd += " AND (billingtype = ";
                }
                else
                {
                    cmd += " OR billingtype = ";
                } 
                cmd += POut.Long(DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][listBillType.SelectedIndices[i]].DefNum);
            }
            cmd += ") ";
        }
         
        if (!checkProvAll.Checked)
        {
            for (int i = 0;i < listProv.SelectedIndices.Count;i++)
            {
                if (i == 0)
                {
                    cmd += " AND (PriProv = ";
                }
                else
                {
                    cmd += " OR PriProv = ";
                } 
                cmd += POut.Long(ProviderC.getListShort()[listProv.SelectedIndices[i]].ProvNum);
            }
            cmd += ") ";
        }
         
        cmd += "ORDER BY LName,FName";
        report.Query = cmd;
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        FormQuery2.submitReportQuery();
        //Recompute aging in a non-historical way, so that the numbers are returned to the way they
        //are normally used in other parts of the program.
        Ledgers.runAging();
        //if(Prefs.UpdateString(PrefName.DateLastAging",POut.PDate(asOfDate,false))) {
        //	DataValid.SetInvalid(InvalidType.Prefs);
        //}
        report.Title = "AGING OF ACCOUNTS RECEIVABLE REPORT";
        report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
        report.SubTitle.Add("As of " + textDate.Text);
        if (radioAny.Checked)
        {
            report.SubTitle.Add("Any Balance");
        }
         
        if (radio30.Checked)
        {
            report.SubTitle.Add("Over 30 Days");
        }
         
        if (radio60.Checked)
        {
            report.SubTitle.Add("Over 60 Days");
        }
         
        if (radio90.Checked)
        {
            report.SubTitle.Add("Over 90 Days");
        }
         
        if (checkBillTypesAll.Checked)
        {
            report.SubTitle.Add("All Billing Types");
        }
        else
        {
            String subt = DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][listBillType.SelectedIndices[0]].ItemName;
            for (int i = 1;i < listBillType.SelectedIndices.Count;i++)
            {
                subt += ", " + DefC.getShort()[((Enum)DefCat.BillingTypes).ordinal()][listBillType.SelectedIndices[i]].ItemName;
            }
            report.SubTitle.Add(subt);
        } 
        //report.InitializeColumns(8);
        report.setColumn(this,0,"GUARANTOR",160);
        report.SetColumn(this, 1, "0-30 DAYS", 80, HorizontalAlignment.Right);
        report.SetColumn(this, 2, "30-60 DAYS", 80, HorizontalAlignment.Right);
        report.SetColumn(this, 3, "60-90 DAYS", 80, HorizontalAlignment.Right);
        report.SetColumn(this, 4, "> 90 DAYS", 80, HorizontalAlignment.Right);
        report.SetColumn(this, 5, "TOTAL", 85, HorizontalAlignment.Right);
        report.SetColumn(this, 6, "-INS EST", 85, HorizontalAlignment.Right);
        report.SetColumn(this, 7, "=PATIENT", 85, HorizontalAlignment.Right);
        FormQuery2.ShowDialog();
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
    }

}


/*///<summary></summary>
	public struct Aging{
		///<summary></summary>
		public double Charges;
		///<summary></summary>
		public double Payments;
		///<summary></summary>
		public double Aged;
	}*/