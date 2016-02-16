//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:07 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormQuery;
import OpenDental.FormRpProcCodes;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.ReportSimpleGrid;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;

/**
* 
*/
public class FormRpProcCodes  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    private System.Windows.Forms.ListBox listFeeSched = new System.Windows.Forms.ListBox();
    private System.Windows.Forms.Panel panel1 = new System.Windows.Forms.Panel();
    private System.Windows.Forms.RadioButton radioCategories = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioCode = new System.Windows.Forms.RadioButton();
    private System.ComponentModel.Container components = null;
    private Label label1 = new Label();
    private FormQuery FormQuery2;
    /**
    * 
    */
    public FormRpProcCodes() throws Exception {
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRpProcCodes.class);
        this.butCancel = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.listFeeSched = new System.Windows.Forms.ListBox();
        this.panel1 = new System.Windows.Forms.Panel();
        this.radioCategories = new System.Windows.Forms.RadioButton();
        this.radioCode = new System.Windows.Forms.RadioButton();
        this.label1 = new System.Windows.Forms.Label();
        this.panel1.SuspendLayout();
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
        this.butCancel.Location = new System.Drawing.Point(337, 276);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 3;
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
        this.butOK.Location = new System.Drawing.Point(337, 241);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 26);
        this.butOK.TabIndex = 2;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // listFeeSched
        //
        this.listFeeSched.Location = new System.Drawing.Point(33, 41);
        this.listFeeSched.Name = "listFeeSched";
        this.listFeeSched.ScrollAlwaysVisible = true;
        this.listFeeSched.Size = new System.Drawing.Size(129, 173);
        this.listFeeSched.TabIndex = 0;
        //
        // panel1
        //
        this.panel1.Controls.Add(this.radioCategories);
        this.panel1.Controls.Add(this.radioCode);
        this.panel1.Location = new System.Drawing.Point(206, 25);
        this.panel1.Name = "panel1";
        this.panel1.Size = new System.Drawing.Size(104, 60);
        this.panel1.TabIndex = 1;
        //
        // radioCategories
        //
        this.radioCategories.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioCategories.Location = new System.Drawing.Point(8, 32);
        this.radioCategories.Name = "radioCategories";
        this.radioCategories.Size = new System.Drawing.Size(88, 24);
        this.radioCategories.TabIndex = 1;
        this.radioCategories.Text = "Categories";
        //
        // radioCode
        //
        this.radioCode.Checked = true;
        this.radioCode.FlatStyle = System.Windows.Forms.FlatStyle.System;
        this.radioCode.Location = new System.Drawing.Point(8, 8);
        this.radioCode.Name = "radioCode";
        this.radioCode.Size = new System.Drawing.Size(88, 24);
        this.radioCode.TabIndex = 0;
        this.radioCode.TabStop = true;
        this.radioCode.Text = "Code";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(30, 17);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(132, 22);
        this.label1.TabIndex = 4;
        this.label1.Text = "Fee Schedule";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomLeft;
        //
        // FormRpProcCodes
        //
        this.AcceptButton = this.butOK;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butCancel;
        this.ClientSize = new System.Drawing.Size(423, 314);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.panel1);
        this.Controls.Add(this.listFeeSched);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butOK);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRpProcCodes";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Procedure Codes Report";
        this.Load += new System.EventHandler(this.FormRpProcCodes_Load);
        this.panel1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formRpProcCodes_Load(Object sender, System.EventArgs e) throws Exception {
        for (int i = 0;i < FeeSchedC.getListShort().Count;i++)
        {
            listFeeSched.Items.Add(FeeSchedC.getListShort()[i].Description);
        }
        listFeeSched.SelectedIndex = 0;
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        long feeSched = FeeSchedC.getListShort()[listFeeSched.SelectedIndex].FeeSchedNum;
        String catName = "";
        //string to hold current category name
        Fees fee = new Fees();
        ReportSimpleGrid report = new ReportSimpleGrid();
        report.Query = "SELECT procedurecode.ProcCode,fee.Amount,'     ',procedurecode.Descript," + "procedurecode.AbbrDesc FROM procedurecode,fee " + "WHERE procedurecode.CodeNum=fee.CodeNum AND fee.FeeSched='" + feeSched.ToString() + "' ORDER BY procedurecode.ProcCode";
        FormQuery2 = new FormQuery(report);
        FormQuery2.IsReport = true;
        if (radioCode.Checked == true)
        {
            FormQuery2.submitReportQuery();
            report.Title = "Procedure Codes";
            report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
            report.SubTitle.Add(FeeScheds.getDescription(feeSched));
            report.setColumn(this,0,"Code",70);
            report.SetColumn(this, 1, "Fee Amount", 70, HorizontalAlignment.Right);
            report.setColumn(this,2," ",80);
            //otherwise, the amount gets bunched up next to the description.
            report.setColumn(this,3,"Description",200);
            report.setColumn(this,4,"Abbr Description",200);
            FormQuery2.ShowDialog();
            DialogResult = DialogResult.OK;
        }
        else
        {
            //categories
            //report.SubmitTemp();//create TableTemp which is not actually used
            ProcedureCode[] ProcList = ProcedureCodes.getProcList();
            report.TableQ = new DataTable(null);
            for (int i = 0;i < 5;i++)
            {
                //add columns
                report.TableQ.Columns.Add(new System.Data.DataColumn());
            }
            //blank columns
            report.initializeColumns();
            DataRow row = report.TableQ.NewRow();
            //add first row by hand to get value for temp
            row[0] = DefC.GetName(DefCat.ProcCodeCats, ProcList[0].ProcCat);
            catName = row[0].ToString();
            row[1] = ProcList[0].ProcCode;
            row[2] = ProcList[0].Descript;
            row[3] = ProcList[0].AbbrDesc;
            row[4] = ((double)Fees.GetAmount0(ProcList[0].CodeNum, feeSched)).ToString("F");
            report.ColTotal[4] += PIn.Decimal(row[4].ToString());
            report.TableQ.Rows.Add(row);
            for (int i = 1;i < ProcList.Length;i++)
            {
                //loop through data rows
                row = report.TableQ.NewRow();
                //create new row called 'row' based on structure of TableQ
                row[0] = DefC.GetName(DefCat.ProcCodeCats, ProcList[i].ProcCat);
                if (StringSupport.equals(catName, row[0].ToString()))
                {
                    row[0] = "";
                }
                else
                {
                    catName = row[0].ToString();
                } 
                row[1] = ProcList[i].ProcCode.ToString();
                row[2] = ProcList[i].Descript;
                row[3] = ProcList[i].AbbrDesc.ToString();
                row[4] = ((double)Fees.GetAmount0(ProcList[i].CodeNum, feeSched)).ToString("F");
                //report.ColTotal[4]+=PIn.PDouble(row[4].ToString());
                report.TableQ.Rows.Add(row);
            }
            FormQuery2.resetGrid();
            //this is a method in FormQuery2;
            report.Title = "Procedure Codes";
            report.SubTitle.Add(PrefC.getString(PrefName.PracticeTitle));
            report.SubTitle.Add(FeeScheds.getDescription(feeSched));
            report.getColPos()[0] = 20;
            report.getColPos()[1] = 120;
            report.getColPos()[2] = 270;
            report.getColPos()[3] = 470;
            report.getColPos()[4] = 620;
            report.getColPos()[5] = 770;
            report.getColCaption()[0] = "Category";
            report.getColCaption()[1] = "Code";
            report.getColCaption()[2] = "Description";
            report.getColCaption()[3] = "Abbr Description";
            report.getColCaption()[4] = "Fee Amount";
            report.getColAlign()[4] = HorizontalAlignment.Right;
            FormQuery2.ShowDialog();
            DialogResult = DialogResult.OK;
        } 
    }

}


