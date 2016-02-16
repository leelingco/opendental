//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:06 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormElectIDEdit;
import OpenDental.FormElectIDs;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.ElectID;
import OpenDentBusiness.ElectIDs;

/**
* Summary description for FormBasicTemplate.
*/
public class FormElectIDs  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.Button butOK;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * 
    */
    public boolean IsSelectMode = new boolean();
    private OpenDental.UI.ODGrid gridElectIDs;
    private OpenDental.UI.Button butAdd;
    /**
    * 
    */
    public ElectID selectedID;
    /**
    * 
    */
    public FormElectIDs() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormElectIDs.class);
        this.gridElectIDs = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // gridElectIDs
        //
        this.gridElectIDs.setAllowSelection(false);
        this.gridElectIDs.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridElectIDs.setHScrollVisible(false);
        this.gridElectIDs.Location = new System.Drawing.Point(7, 12);
        this.gridElectIDs.Name = "gridElectIDs";
        this.gridElectIDs.setScrollValue(0);
        this.gridElectIDs.Size = new System.Drawing.Size(879, 617);
        this.gridElectIDs.TabIndex = 140;
        this.gridElectIDs.setTitle("");
        this.gridElectIDs.setTranslationName("TableApptProcs");
        this.gridElectIDs.CellDoubleClick = __MultiODGridClickEventHandler.combine(this.gridElectIDs.CellDoubleClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridElectIDs_CellDoubleClick(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        this.gridElectIDs.CellClick = __MultiODGridClickEventHandler.combine(this.gridElectIDs.CellClick,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridElectIDs_CellClick(sender, e);
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
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(721, 635);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 25);
        this.butOK.TabIndex = 1;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        this.butCancel.Location = new System.Drawing.Point(808, 635);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 25);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
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
        this.butAdd.Location = new System.Drawing.Point(409, 635);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 25);
        this.butAdd.TabIndex = 141;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormElectIDs
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(892, 674);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridElectIDs);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormElectIDs";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Electronic Payer ID\'s";
        this.Load += new System.EventHandler(this.FormElectIDs_Load);
        this.ResumeLayout(false);
    }

    private void formElectIDs_Load(Object sender, System.EventArgs e) throws Exception {
        FillElectIDs(0);
        butAdd.Visible = (!IsSelectMode);
    }

    private void fillElectIDs(long electIDSelect) throws Exception {
        ElectIDs.refreshCache();
        gridElectIDs.beginUpdate();
        gridElectIDs.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableApptProcs","Carrier"),320);
        gridElectIDs.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Payer ID"),80);
        gridElectIDs.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Is Medicaid"), 70, HorizontalAlignment.Center);
        gridElectIDs.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableApptProcs","Comments"),390);
        gridElectIDs.getColumns().add(col);
        gridElectIDs.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        int selectedIndex = -1;
        for (int i = 0;i < ElectIDs.getList().Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ElectIDs.getList()[i].CarrierName);
            row.getCells().Add(ElectIDs.getList()[i].PayorID);
            row.getCells().add(ElectIDs.getList()[i].IsMedicaid ? "X" : "");
            row.getCells().Add(ElectIDs.getList()[i].Comments);
            gridElectIDs.getRows().add(row);
            if (ElectIDs.getList()[i].ElectIDNum == electIDSelect)
            {
                selectedIndex = i;
            }
             
        }
        gridElectIDs.endUpdate();
        gridElectIDs.setSelected(selectedIndex,true);
    }

    private void gridElectIDs_CellClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        gridElectIDs.setSelected(e.getRow(),true);
    }

    private void gridElectIDs_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        if (IsSelectMode)
        {
            selectedID = ElectIDs.getList()[e.getRow()];
            DialogResult = DialogResult.OK;
        }
        else
        {
            FormElectIDEdit FormEdit = new FormElectIDEdit();
            FormEdit.electIDCur = ElectIDs.getList()[e.getRow()];
            if (FormEdit.ShowDialog() == DialogResult.OK)
            {
                fillElectIDs(FormEdit.electIDCur.ElectIDNum);
            }
             
        } 
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormElectIDEdit FormEdit = new FormElectIDEdit();
        FormEdit.electIDCur = new ElectID();
        FormEdit.electIDCur.setIsNew(true);
        if (FormEdit.ShowDialog() == DialogResult.OK)
        {
            fillElectIDs(FormEdit.electIDCur.ElectIDNum);
        }
         
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        if (IsSelectMode && gridElectIDs.getSelectedIndices().Length < 1)
        {
            MessageBox.Show(Lan.g(this,"Please select an item first."));
            return ;
        }
         
        selectedID = ElectIDs.getList()[gridElectIDs.getSelectedIndices()[0]];
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


