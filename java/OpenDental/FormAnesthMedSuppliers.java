//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:36 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormAnesthMedSuppliers;
import OpenDental.FormAnesthMedSuppliersEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;

/**
* Summary description for FormBasicTemplate.
*/
public class FormAnesthMedSuppliers  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butAddNew;
    private OpenDental.UI.Button butClose;
    public HorizontalAlignment textAlign = new HorizontalAlignment();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    //private bool changed;
    public boolean IsSelectionMode = new boolean();
    /**
    * Only used if IsSelectionMode.  On OK, contains selected anesthMedSuppliersNum.  Can be 0.  Can also be set ahead of time externally.
    */
    public int SelectedSupplierIDNum = new int();
    /**
    * 
    */
    public FormAnesthMedSuppliers() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormAnesthMedSuppliers.class);
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAddNew = new OpenDental.UI.Button();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(17, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(1030, 291);
        this.gridMain.TabIndex = 11;
        this.gridMain.setTitle("Anesthetic Medication Suppliers");
        this.gridMain.setTranslationName("TableAnesthMedSuppliers");
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
        // butAddNew
        //
        this.butAddNew.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddNew.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butAddNew.setAutosize(true);
        this.butAddNew.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddNew.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddNew.setCornerRadius(4F);
        this.butAddNew.Image = Resources.getAdd();
        this.butAddNew.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAddNew.Location = new System.Drawing.Point(17, 317);
        this.butAddNew.Name = "butAddNew";
        this.butAddNew.Size = new System.Drawing.Size(85, 24);
        this.butAddNew.TabIndex = 10;
        this.butAddNew.Text = "&Add New";
        this.butAddNew.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(955, 317);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormAnesthMedSuppliers
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(1058, 357);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAddNew);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormAnesthMedSuppliers";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Anesthetic Medication Suppliers";
        this.Load += new System.EventHandler(this.FormAnesthMedSuppliers_Load);
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormAnesthMedSuppliers_FormClosing);
        this.ResumeLayout(false);
    }

    private void formAnesthMedSuppliers_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        AnesthMedSuppliers.RefreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        textAlign = HorizontalAlignment.Center;
        ODGridColumn col = new ODGridColumn(Lan.g(this,"SupplierName"),200,textAlign);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Phone"),100,textAlign);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Fax"),100,textAlign);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"City"),140,textAlign);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"State"),160,textAlign);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"WebSite"),140,textAlign);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < AnesthMedSupplierC.Listt.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(AnesthMedSupplierC.Listt[i].SupplierName);
            row.getCells().Add(AnesthMedSupplierC.Listt[i].Phone);
            row.getCells().Add(AnesthMedSupplierC.Listt[i].Fax);
            row.getCells().Add(AnesthMedSupplierC.Listt[i].City);
            row.getCells().Add(AnesthMedSupplierC.Listt[i].State);
            row.getCells().Add(AnesthMedSupplierC.Listt[i].WebSite);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        AnesthMedSupplier supplCur = new AnesthMedSupplier();
        supplCur.IsNew = true;
        FormAnesthMedSuppliersEdit FormME = new FormAnesthMedSuppliersEdit();
        FormME.SupplCur = supplCur;
        FormME.ShowDialog();
        if (FormME.DialogResult == DialogResult.OK)
        {
            fillGrid();
        }
         
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        FormAnesthMedSuppliersEdit FormME = new FormAnesthMedSuppliersEdit();
        FormME.SupplCur = AnesthMedSupplierC.Listt[e.getRow()];
        FormME.ShowDialog();
        AnesthMedSuppliers.RefreshCache();
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
        Close();
    }

    private void formAnesthMedSuppliers_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


