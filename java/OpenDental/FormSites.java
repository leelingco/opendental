//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:50 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormSiteEdit;
import OpenDental.FormSites;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.Site;
import OpenDentBusiness.SiteC;
import OpenDentBusiness.Sites;

/**
* Summary description for FormBasicTemplate.
*/
public class FormSites  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butClose;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private OpenDental.UI.ODGrid gridMain;
    private boolean changed = new boolean();
    private OpenDental.UI.Button butOK;
    public boolean IsSelectionMode = new boolean();
    private OpenDental.UI.Button butNone;
    /**
    * Only used if IsSelectionMode.  On OK, contains selected siteNum.  Can be 0.  Can also be set ahead of time externally.
    */
    public long SelectedSiteNum = new long();
    /**
    * 
    */
    public FormSites() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormSites.class);
        this.label1 = new System.Windows.Forms.Label();
        this.butNone = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(17, 2);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(371, 18);
        this.label1.TabIndex = 11;
        this.label1.Text = "Used to keep track of multiple service locations for mobile clinics";
        //
        // butNone
        //
        this.butNone.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butNone.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butNone.setAutosize(true);
        this.butNone.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butNone.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butNone.setCornerRadius(4F);
        this.butNone.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butNone.Location = new System.Drawing.Point(504, 642);
        this.butNone.Name = "butNone";
        this.butNone.Size = new System.Drawing.Size(68, 24);
        this.butNone.TabIndex = 14;
        this.butNone.Text = "None";
        this.butNone.Click += new System.EventHandler(this.butNone_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(652, 612);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 13;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 23);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(608, 605);
        this.gridMain.TabIndex = 12;
        this.gridMain.setTitle("Sites");
        this.gridMain.setTranslationName(null);
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
        this.butAdd.Location = new System.Drawing.Point(17, 642);
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
        this.butClose.Location = new System.Drawing.Point(652, 642);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormSites
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(739, 674);
        this.Controls.Add(this.butNone);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormSites";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Sites";
        this.Load += new System.EventHandler(this.FormSites_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormSites_FormClosing);
        this.ResumeLayout(false);
    }

    private void formSites_Load(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            butClose.Text = Lan.g(this,"Cancel");
            if (!Security.isAuthorized(Permissions.Setup,true))
            {
                butAdd.Visible = false;
            }
             
        }
        else
        {
            butOK.Visible = false;
            butNone.Visible = false;
        } 
        fillGrid();
        if (SelectedSiteNum != 0)
        {
            for (int i = 0;i < SiteC.getList().Length;i++)
            {
                if (SiteC.getList()[i].SiteNum == SelectedSiteNum)
                {
                    gridMain.setSelected(i,true);
                    break;
                }
                 
            }
        }
         
    }

    private void fillGrid() throws Exception {
        Sites.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableSites","Description"),250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableSites","Note"),100);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < SiteC.getList().Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(SiteC.getList()[i].Description);
            row.getCells().Add(SiteC.getList()[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        //This button is not visible unless user has appropriate permission for setup.
        FormSiteEdit FormS = new FormSiteEdit();
        FormS.SiteCur = new Site();
        FormS.SiteCur.setIsNew(true);
        FormS.ShowDialog();
        fillGrid();
        changed = true;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            SelectedSiteNum = SiteC.getList()[e.getRow()].SiteNum;
            DialogResult = DialogResult.OK;
            return ;
        }
        else
        {
            FormSiteEdit FormS = new FormSiteEdit();
            FormS.SiteCur = SiteC.getList()[e.getRow()];
            FormS.ShowDialog();
            fillGrid();
            changed = true;
        } 
    }

    private void butNone_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless is selection mode
        SelectedSiteNum = 0;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //not even visible unless is selection mode
        if (gridMain.getSelectedIndex() == -1)
        {
            //	MsgBox.Show(this,"Please select an item first.");
            //	return;
            SelectedSiteNum = 0;
        }
        else
        {
            SelectedSiteNum = SiteC.getList()[gridMain.getSelectedIndex()].SiteNum;
        } 
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectionMode)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            Close();
        } 
    }

    private void formSites_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
        if (changed)
        {
            DataValid.setInvalid(InvalidType.Sites);
        }
         
    }

}


