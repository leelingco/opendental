//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:39 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DateTimeOD;
import OpenDental.FormReconcileEdit;
import OpenDental.FormReconciles;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Reconcile;
import OpenDentBusiness.Reconciles;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReconciles  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid grid;
    private Reconcile[] RList = new Reconcile[]();
    private long AccountNum = new long();
    /**
    * 
    */
    public FormReconciles(long accountNum) throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        AccountNum = accountNum;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReconciles.class);
        this.grid = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // grid
        //
        this.grid.setHScrollVisible(false);
        this.grid.Location = new System.Drawing.Point(18, 13);
        this.grid.Name = "grid";
        this.grid.setScrollValue(0);
        this.grid.Size = new System.Drawing.Size(191, 450);
        this.grid.TabIndex = 1;
        this.grid.setTitle("Existing Reconciles");
        this.grid.setTranslationName("TableReconciles");
        this.grid.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.grid.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.grid_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(242, 509);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(18, 475);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(77, 26);
        this.butAdd.TabIndex = 98;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormReconciles
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(346, 546);
        this.Controls.Add(this.grid);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.butAdd);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReconciles";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Reconciles";
        this.Load += new System.EventHandler(this.FormReconciles_Load);
        this.ResumeLayout(false);
    }

    private void formReconciles_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        RList = Reconciles.getList(AccountNum);
        grid.beginUpdate();
        grid.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableReconciles","Date"),80);
        grid.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableReconciles","Ending Bal"), 100, HorizontalAlignment.Right);
        grid.getColumns().add(col);
        grid.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(RList[i].DateReconcile.ToShortDateString());
            row.getCells().Add(RList[i].EndingBal.ToString("F"));
            grid.getRows().add(row);
        }
        grid.endUpdate();
        grid.scrollToEnd();
    }

    private void grid_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormReconcileEdit FormR = new FormReconcileEdit(RList[e.getRow()]);
        FormR.ShowDialog();
        if (FormR.DialogResult == DialogResult.Cancel)
        {
            return ;
        }
         
        fillGrid();
    }

    /**
    * 
    */
    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        Reconcile rec = new Reconcile();
        rec.DateReconcile = DateTimeOD.getToday();
        rec.AccountNum = AccountNum;
        Reconciles.insert(rec);
        FormReconcileEdit FormR = new FormReconcileEdit(rec);
        FormR.IsNew = true;
        FormR.ShowDialog();
        if (FormR.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


