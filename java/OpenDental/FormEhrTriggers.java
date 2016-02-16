//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDental.FormCDSSetup;
import OpenDental.FormEhrTriggerEdit;
import OpenDental.Lan;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.CDSPermissions;
import OpenDentBusiness.EhrTrigger;
import OpenDentBusiness.EhrTriggers;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormEhrTriggers  extends Form 
{

    public List<EhrTrigger> ListEhrTriggers = new List<EhrTrigger>();
    public FormEhrTriggers() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formEhrTriggers_Load(Object sender, EventArgs e) throws Exception {
        mainMenu1.MenuItems[0].Enabled = false;
        butAddTrigger.Enabled = false;
        gridMain.Enabled = false;
        if (CDSPermissions.getForUser(Security.getCurUser().UserNum).SetupCDS || Security.isAuthorized(Permissions.SecurityAdmin,true))
        {
            mainMenu1.MenuItems[0].Enabled = true;
            butAddTrigger.Enabled = true;
            gridMain.Enabled = true;
        }
         
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("Description",200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Cardinality",140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Trigger Categories",200);
        gridMain.getColumns().add(col);
        ListEhrTriggers = EhrTriggers.getAll();
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListEhrTriggers.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListEhrTriggers[i].Description);
            row.getCells().Add(ListEhrTriggers[i].Cardinality.ToString());
            row.getCells().Add(ListEhrTriggers[i].GetTriggerCategories());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAddTrigger_Click(Object sender, EventArgs e) throws Exception {
        FormEhrTriggerEdit FormETE = new FormEhrTriggerEdit();
        FormETE.EhrTriggerCur = new EhrTrigger();
        FormETE.IsNew = true;
        FormETE.ShowDialog();
        if (FormETE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListEhrTriggers = EhrTriggers.getAll();
        fillGrid();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormEhrTriggerEdit FormETE = new FormEhrTriggerEdit();
        FormETE.EhrTriggerCur = ListEhrTriggers[e.getRow()];
        FormETE.ShowDialog();
        if (FormETE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        ListEhrTriggers = EhrTriggers.getAll();
        fillGrid();
    }

    private void menuItemSettings_Click(Object sender, EventArgs e) throws Exception {
        FormCDSSetup FormCS = new FormCDSSetup();
        FormCS.ShowDialog();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.components = new System.ComponentModel.Container();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAddTrigger = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
        this.menuItemSettings = new System.Windows.Forms.MenuItem();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(683, 560);
        this.gridMain.TabIndex = 199;
        this.gridMain.setTitle("EHR Triggers");
        this.gridMain.setTranslationName("");
        this.gridMain.setWrapText(false);
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
        // butAddTrigger
        //
        this.butAddTrigger.setAdjustImageLocation(new System.Drawing.Point(0, 1));
        this.butAddTrigger.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddTrigger.setAutosize(true);
        this.butAddTrigger.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddTrigger.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddTrigger.setCornerRadius(4F);
        this.butAddTrigger.Image = Resources.getAdd();
        this.butAddTrigger.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddTrigger.Location = new System.Drawing.Point(701, 12);
        this.butAddTrigger.Name = "butAddTrigger";
        this.butAddTrigger.Size = new System.Drawing.Size(75, 23);
        this.butAddTrigger.TabIndex = 201;
        this.butAddTrigger.Text = "Add";
        this.butAddTrigger.Click += new System.EventHandler(this.butAddTrigger_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(701, 548);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // mainMenu1
        //
        this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemSettings });
        //
        // menuItemSettings
        //
        this.menuItemSettings.Index = 0;
        this.menuItemSettings.Text = "Setup";
        this.menuItemSettings.Click += new System.EventHandler(this.menuItemSettings_Click);
        //
        // FormEhrTriggers
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(788, 584);
        this.Controls.Add(this.butAddTrigger);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Menu = this.mainMenu1;
        this.Name = "FormEhrTriggers";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "EHR Triggers";
        this.Load += new System.EventHandler(this.FormEhrTriggers_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAddTrigger;
    private System.Windows.Forms.MainMenu mainMenu1 = new System.Windows.Forms.MainMenu();
    private System.Windows.Forms.MenuItem menuItemSettings = new System.Windows.Forms.MenuItem();
}


