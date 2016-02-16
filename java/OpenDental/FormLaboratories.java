//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:18 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormLaboratories;
import OpenDental.FormLaboratoryEdit;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Laboratories;
import OpenDentBusiness.Laboratory;

/**
* Summary description for FormBasicTemplate.
*/
public class FormLaboratories  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butAdd;
    // Required designer variable.
    //private bool changed;
    private List<Laboratory> ListLabs = new List<Laboratory>();
    /**
    * 
    */
    public FormLaboratories() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormLaboratories.class);
        this.butClose = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new OpenDental.UI.Button();
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
        this.butClose.Location = new System.Drawing.Point(592, 469);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "&Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(33, 40);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(634, 334);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle("Dental Labs");
        this.gridMain.setTranslationName("TableLabs");
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
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Image = Resources.getAdd();
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(592, 394);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 26);
        this.butAdd.TabIndex = 2;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormLaboratories
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(719, 520);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormLaboratories";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Laboratories";
        this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FormLaboratories_FormClosing);
        this.Load += new System.EventHandler(this.FormLaboratories_Load);
        this.ResumeLayout(false);
    }

    private void formLaboratories_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ListLabs = Laboratories.refresh();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableLabs","Description"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabs","Phone"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableLabs","Notes"),200);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListLabs.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListLabs[i].Description);
            row.getCells().Add(ListLabs[i].Phone);
            row.getCells().Add(ListLabs[i].Notes);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormLaboratoryEdit FormL = new FormLaboratoryEdit();
        FormL.LabCur = ListLabs[e.getRow()];
        FormL.ShowDialog();
        //if(FormL.DialogResult==DialogResult.OK){
        //changed=true;
        fillGrid();
    }

    //}
    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormLaboratoryEdit FormL = new FormLaboratoryEdit();
        FormL.LabCur = new Laboratory();
        FormL.IsNew = true;
        FormL.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formLaboratories_FormClosing(Object sender, FormClosingEventArgs e) throws Exception {
    }

}


//if(changed){
//Labs are not global.
//DataValid.SetInvalid(InvalidTypes.Providers);
//}