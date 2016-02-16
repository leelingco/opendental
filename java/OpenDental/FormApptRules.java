//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:39 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormApptRuleEdit;
import OpenDental.FormApptRules;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.AppointmentRule;
import OpenDentBusiness.AppointmentRuleC;
import OpenDentBusiness.AppointmentRules;
import OpenDentBusiness.InvalidType;

/**
* Summary description for FormBasicTemplate.
*/
public class FormApptRules  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private Label label1 = new Label();
    private boolean changed = new boolean();
    /**
    * 
    */
    public FormApptRules() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormApptRules.class);
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(21, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(485, 48);
        this.label1.TabIndex = 12;
        this.label1.Text = resources.GetString("label1.Text");
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(21, 59);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(485, 369);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Appointment Rules");
        this.gridMain.setTranslationName("TablePayPeriods");
        this.gridMain.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridMain.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, ODGridClickEventArgs e) throws Exception {
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
        this.butAdd.Location = new System.Drawing.Point(19, 451);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(80, 26);
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
        this.butClose.Location = new System.Drawing.Point(432, 451);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormApptRules
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(539, 500);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormApptRules";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Appointment Rules";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormPayPeriods_FormClosing);
        this.Load += new System.EventHandler(this.FormApptRules_Load);
        this.ResumeLayout(false);
    }

    private void formApptRules_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        AppointmentRules.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Description",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Start Code",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("End Code",100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Enabled", 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < AppointmentRuleC.getList().Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(AppointmentRuleC.getList()[i].RuleDesc);
            row.getCells().Add(AppointmentRuleC.getList()[i].CodeStart);
            row.getCells().Add(AppointmentRuleC.getList()[i].CodeEnd);
            if (AppointmentRuleC.getList()[i].IsEnabled)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormApptRuleEdit FormA = new FormApptRuleEdit(AppointmentRuleC.getList()[e.getRow()]);
        FormA.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        AppointmentRule aptRule = new AppointmentRule();
        aptRule.IsEnabled = true;
        FormApptRuleEdit FormA = new FormApptRuleEdit(aptRule);
        FormA.IsNew = true;
        FormA.ShowDialog();
        if (FormA.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillGrid();
        changed = true;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formPayPeriods_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Views);
        }
         
    }

}


