//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:55 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormPayPeriodEdit;
import OpenDental.FormTimeCardRuleEdit;
import OpenDental.FormTimeCardSetup;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.PayPeriod;
import OpenDentBusiness.PayPeriods;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.TimeCardRule;
import OpenDentBusiness.TimeCardRules;

/**
* Summary description for FormBasicTemplate.
*/
public class FormTimeCardSetup  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private CheckBox checkUseDecimal = new CheckBox();
    private OpenDental.UI.ODGrid gridRules;
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butAddRule;
    private CheckBox checkAdjOverBreaks = new CheckBox();
    private Label label2 = new Label();
    private TextBox textADPCompanyCode = new TextBox();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormTimeCardSetup() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormTimeCardSetup.class);
        this.checkUseDecimal = new System.Windows.Forms.CheckBox();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.checkAdjOverBreaks = new System.Windows.Forms.CheckBox();
        this.butAddRule = new OpenDental.UI.Button();
        this.gridRules = new OpenDental.UI.ODGrid();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.label2 = new System.Windows.Forms.Label();
        this.textADPCompanyCode = new System.Windows.Forms.TextBox();
        this.groupBox1.SuspendLayout();
        this.SuspendLayout();
        //
        // checkUseDecimal
        //
        this.checkUseDecimal.Location = new System.Drawing.Point(12, 19);
        this.checkUseDecimal.Name = "checkUseDecimal";
        this.checkUseDecimal.Size = new System.Drawing.Size(295, 18);
        this.checkUseDecimal.TabIndex = 12;
        this.checkUseDecimal.Text = "Use decimal format rather than colon format";
        this.checkUseDecimal.UseVisualStyleBackColor = true;
        this.checkUseDecimal.Click += new System.EventHandler(this.checkUseDecimal_Click);
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.groupBox1.Controls.Add(this.checkAdjOverBreaks);
        this.groupBox1.Controls.Add(this.checkUseDecimal);
        this.groupBox1.Location = new System.Drawing.Point(19, 526);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(391, 74);
        this.groupBox1.TabIndex = 14;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Options";
        //
        // checkAdjOverBreaks
        //
        this.checkAdjOverBreaks.Location = new System.Drawing.Point(12, 43);
        this.checkAdjOverBreaks.Name = "checkAdjOverBreaks";
        this.checkAdjOverBreaks.Size = new System.Drawing.Size(354, 18);
        this.checkAdjOverBreaks.TabIndex = 13;
        this.checkAdjOverBreaks.Text = "Calc Daily button makes adjustments if breaks over 30 minutes.";
        this.checkAdjOverBreaks.UseVisualStyleBackColor = true;
        this.checkAdjOverBreaks.Click += new System.EventHandler(this.checkAdjOverBreaks_Click);
        //
        // butAddRule
        //
        this.butAddRule.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddRule.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAddRule.setAutosize(true);
        this.butAddRule.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddRule.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddRule.setCornerRadius(4F);
        this.butAddRule.Image = Resources.getAdd();
        this.butAddRule.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddRule.Location = new System.Drawing.Point(305, 494);
        this.butAddRule.Name = "butAddRule";
        this.butAddRule.Size = new System.Drawing.Size(80, 24);
        this.butAddRule.TabIndex = 15;
        this.butAddRule.Text = "Add";
        this.butAddRule.Click += new System.EventHandler(this.butAddRule_Click);
        //
        // gridRules
        //
        this.gridRules.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridRules.setHScrollVisible(false);
        this.gridRules.Location = new System.Drawing.Point(305, 12);
        this.gridRules.Name = "gridRules";
        this.gridRules.setScrollValue(0);
        this.gridRules.Size = new System.Drawing.Size(489, 478);
        this.gridRules.TabIndex = 13;
        this.gridRules.setTitle("Rules");
        this.gridRules.setTranslationName("FormTimeCardSetup");
        this.gridRules.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridRules.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridRules_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(19, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(272, 478);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Pay Periods");
        this.gridMain.setTranslationName("TablePayPeriods");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(19, 494);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 24);
        this.butAdd.TabIndex = 10;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(719, 604);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label2
        //
        this.label2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.label2.Location = new System.Drawing.Point(14, 608);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(119, 16);
        this.label2.TabIndex = 17;
        this.label2.Text = "ADP Company Code";
        this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        //
        // textADPCompanyCode
        //
        this.textADPCompanyCode.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.textADPCompanyCode.Location = new System.Drawing.Point(133, 606);
        this.textADPCompanyCode.Name = "textADPCompanyCode";
        this.textADPCompanyCode.Size = new System.Drawing.Size(97, 20);
        this.textADPCompanyCode.TabIndex = 16;
        //
        // FormTimeCardSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(822, 636);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.textADPCompanyCode);
        this.Controls.Add(this.butAddRule);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridRules);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormTimeCardSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Time Card Setup";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormPayPeriods_FormClosing);
        this.Load += new System.EventHandler(this.FormPayPeriods_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private void formPayPeriods_Load(Object sender, System.EventArgs e) throws Exception {
        checkUseDecimal.Checked = PrefC.getBool(PrefName.TimeCardsUseDecimalInsteadOfColon);
        checkAdjOverBreaks.Checked = PrefC.getBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks);
        Employees.refreshCache();
        fillGrid();
        fillRules();
        textADPCompanyCode.Text = PrefC.getString(PrefName.ADPCompanyCode);
    }

    private void fillGrid() throws Exception {
        PayPeriods.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Start Date",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("End Date",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Paycheck Date",100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < PayPeriods.getList().Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(PayPeriods.getList()[i].DateStart.ToShortDateString());
            row.getCells().Add(PayPeriods.getList()[i].DateStop.ToShortDateString());
            if (PayPeriods.getList()[i].DatePaycheck.Year < 1880)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(PayPeriods.getList()[i].DatePaycheck.ToShortDateString());
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void fillRules() throws Exception {
        TimeCardRules.refreshCache();
        gridRules.beginUpdate();
        gridRules.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Employee",150);
        gridRules.getColumns().add(col);
        col = new ODGridColumn("OT before x Time",105);
        gridRules.getColumns().add(col);
        col = new ODGridColumn("OT after x Time",100);
        gridRules.getColumns().add(col);
        col = new ODGridColumn("OT after x Hours",110);
        gridRules.getColumns().add(col);
        gridRules.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < TimeCardRules.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            if (TimeCardRules.getListt()[i].EmployeeNum == 0)
            {
                row.getCells().add(Lan.g(this,"All Employees"));
            }
            else
            {
                Employee emp = Employees.GetEmp(TimeCardRules.getListt()[i].EmployeeNum);
                row.getCells().add(emp.FName + " " + emp.LName);
            } 
            row.getCells().Add(TimeCardRules.getListt()[i].BeforeTimeOfDay.ToStringHmm());
            row.getCells().Add(TimeCardRules.getListt()[i].AfterTimeOfDay.ToStringHmm());
            row.getCells().Add(TimeCardRules.getListt()[i].OverHoursPerDay.ToStringHmm());
            gridRules.getRows().add(row);
        }
        gridRules.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormPayPeriodEdit FormP = new FormPayPeriodEdit(PayPeriods.getList()[e.getRow()]);
        FormP.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        PayPeriod payPeriodCur = new PayPeriod();
        if (PayPeriods.getList().Length == 0)
        {
            payPeriodCur.DateStart = DateTime.Today;
        }
        else
        {
            payPeriodCur.DateStart = PayPeriods.getList()[PayPeriods.getList().Length - 1].DateStop.AddDays(1);
        } 
        payPeriodCur.DateStop = payPeriodCur.DateStart.AddDays(14);
        payPeriodCur.DatePaycheck = payPeriodCur.DateStop.AddDays(4);
        FormPayPeriodEdit FormP = new FormPayPeriodEdit(payPeriodCur);
        FormP.IsNew = true;
        FormP.ShowDialog();
        if (FormP.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillGrid();
        changed = true;
    }

    private void checkUseDecimal_Click(Object sender, EventArgs e) throws Exception {
        if (Prefs.UpdateBool(PrefName.TimeCardsUseDecimalInsteadOfColon, checkUseDecimal.Checked))
        {
            changed = true;
        }
         
    }

    private void checkAdjOverBreaks_Click(Object sender, EventArgs e) throws Exception {
        if (Prefs.UpdateBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks, checkAdjOverBreaks.Checked))
        {
            changed = true;
        }
         
    }

    private void butAddRule_Click(Object sender, EventArgs e) throws Exception {
        TimeCardRule rule = new TimeCardRule();
        rule.setIsNew(true);
        FormTimeCardRuleEdit FormT = new FormTimeCardRuleEdit();
        FormT.timeCardRule = rule;
        FormT.ShowDialog();
        fillRules();
        changed = true;
    }

    private void gridRules_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormTimeCardRuleEdit FormT = new FormTimeCardRuleEdit();
        FormT.timeCardRule = TimeCardRules.getListt()[e.getRow()];
        FormT.ShowDialog();
        fillRules();
        changed = true;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        //Pref pattern below is followed from FormMisc and allows additional prefs to be updated.
        boolean changed = false;
        if (Prefs.UpdateString(PrefName.ADPCompanyCode, textADPCompanyCode.Text))
        {
            //| Prefs.UpdateString(PrefName.ADPCompanyCode,textADPCompanyCode.Text)
            changed = true;
        }
         
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Prefs);
        }
         
        Close();
    }

    private void formPayPeriods_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Employees,InvalidType.Prefs,InvalidType.TimeCardRules);
        }
         
    }

}


