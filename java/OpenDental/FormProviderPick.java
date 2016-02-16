//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:35 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormProviderPick;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;

/**
* Pick a provider from the list.
*/
public class FormProviderPick  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    //private bool changed;
    //private User user;
    //private DataTable table;
    private OpenDental.UI.Button butOK;
    /**
    * This can be set ahead of time to preselect a provider.  After closing with OK, this will have the selected provider number.
    */
    public long SelectedProvNum = new long();
    /**
    * 
    */
    public FormProviderPick() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormProviderPick.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butClose.Location = new System.Drawing.Point(411, 628);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(82, 24);
        this.butClose.TabIndex = 3;
        this.butClose.Text = "&Cancel";
        this.butClose.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(16, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(362, 642);
        this.gridMain.TabIndex = 13;
        this.gridMain.setTitle("Providers");
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(411, 596);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(82, 24);
        this.butOK.TabIndex = 14;
        this.butOK.Text = "OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // FormProviderPick
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(514, 670);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormProviderPick";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Providers";
        this.Load += new System.EventHandler(this.FormProviderSelect_Load);
        this.ResumeLayout(false);
    }

    private void formProviderSelect_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
        if (SelectedProvNum != 0)
        {
            gridMain.setSelected(Providers.getIndex(SelectedProvNum),true);
        }
         
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableProviders","Abbrev"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProviders","Last Name"),90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableProviders","First Name"),90);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ProviderC.getListShort().Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ProviderC.getListShort()[i].Abbr);
            row.getCells().Add(ProviderC.getListShort()[i].LName);
            row.getCells().Add(ProviderC.getListShort()[i].FName);
            //wanted to do a background color here, but grid couldn't handle it.
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        SelectedProvNum = ProviderC.getListShort()[e.getRow()].ProvNum;
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MsgBox.show(this,"Please select a provider first.");
            return ;
        }
         
        SelectedProvNum = ProviderC.getListShort()[gridMain.getSelectedIndex()].ProvNum;
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


