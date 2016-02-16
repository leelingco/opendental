//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:41 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormAutomation;
import OpenDental.FormAutomationEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Automation;
import OpenDentBusiness.AutomationAction;
import OpenDentBusiness.Automations;
import OpenDentBusiness.AutomationTrigger;
import OpenDentBusiness.DefC;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.SheetDefs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAutomation  extends System.Windows.Forms.Form 
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
    public FormAutomation() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAutomation.class);
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
        this.label1.Size = new System.Drawing.Size(485, 22);
        this.label1.TabIndex = 12;
        this.label1.Text = "One trigger event will cause one action to be taken";
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(21, 34);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(735, 394);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Automation");
        this.gridMain.setTranslationName("FormAutomation");
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
        this.butAdd.Location = new System.Drawing.Point(19, 451);
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
        this.butClose.Location = new System.Drawing.Point(681, 451);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormAutomation
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(788, 500);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAutomation";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Automation";
        this.Load += new System.EventHandler(this.FormAutomation_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormAutomation_FormClosing);
        this.ResumeLayout(false);
    }

    private void formAutomation_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Automations.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Description",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Trigger",150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Action",150);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Details",200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        String detail = new String();
        for (int i = 0;i < Automations.getListt().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Automations.getListt()[i].Description);
            if (Automations.getListt()[i].Autotrigger == AutomationTrigger.CompleteProcedure)
            {
                row.getCells().Add(Automations.getListt()[i].ProcCodes);
            }
            else
            {
                row.getCells().Add(Automations.getListt()[i].Autotrigger.ToString());
            } 
            row.getCells().Add(Automations.getListt()[i].AutoAction.ToString());
            //details:
            detail = "";
            if (Automations.getListt()[i].AutoAction == AutomationAction.CreateCommlog)
            {
                detail += DefC.GetName(DefCat.CommLogTypes, Automations.getListt()[i].CommType) + ".  " + Automations.getListt()[i].MessageContent;
            }
            else if (Automations.getListt()[i].AutoAction == AutomationAction.PrintPatientLetter)
            {
                detail += SheetDefs.GetDescription(Automations.getListt()[i].SheetDefNum);
            }
            else if (Automations.getListt()[i].AutoAction == AutomationAction.PrintReferralLetter)
            {
                detail += SheetDefs.GetDescription(Automations.getListt()[i].SheetDefNum);
            }
               
            row.getCells().add(detail);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormAutomationEdit FormA = new FormAutomationEdit(Automations.getListt()[e.getRow()]);
        FormA.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Automation auto = new Automation();
        Automations.insert(auto);
        //so that we can attach conditions
        FormAutomationEdit FormA = new FormAutomationEdit(auto);
        FormA.IsNew = true;
        FormA.ShowDialog();
        if (FormA.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
        changed = true;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formAutomation_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Automation);
        }
         
    }

}


