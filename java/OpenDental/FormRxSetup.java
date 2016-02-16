//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:44 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormRxDefEdit;
import OpenDental.FormRxSetup;
import OpenDental.Lan;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.RxDef;
import OpenDentBusiness.RxDefs;

/**
* 
*/
public class FormRxSetup  extends System.Windows.Forms.Form 
{
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butAdd;
    private OpenDental.UI.Button butAdd2;
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    // Required designer variable.
    private RxDef[] RxDefList = new RxDef[]();
    /**
    * 
    */
    public FormRxSetup() throws Exception {
        initializeComponent();
        // Required for Windows Form Designer support
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

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormRxSetup.class);
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.butAdd2 = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
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
        this.butClose.Location = new System.Drawing.Point(850, 636);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 4;
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
        this.butAdd.Location = new System.Drawing.Point(548, 636);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(88, 26);
        this.butAdd.TabIndex = 14;
        this.butAdd.Text = "Add &New";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // butAdd2
        //
        this.butAdd2.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd2.setAutosize(true);
        this.butAdd2.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd2.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd2.setCornerRadius(4F);
        this.butAdd2.Image = Resources.getAdd();
        this.butAdd2.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd2.Location = new System.Drawing.Point(402, 636);
        this.butAdd2.Name = "butAdd2";
        this.butAdd2.Size = new System.Drawing.Size(88, 26);
        this.butAdd2.TabIndex = 16;
        this.butAdd2.Text = "Duplicate";
        this.butAdd2.Click += new System.EventHandler(this.butAdd2_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(913, 612);
        this.gridMain.TabIndex = 17;
        this.gridMain.setTitle("Prescriptions");
        this.gridMain.setTranslationName("TableRxSetup");
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
        // FormRxSetup
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(942, 674);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd2);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormRxSetup";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Rx Setup";
        this.Load += new System.EventHandler(this.FormRxSetup_Load);
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormRxSetup_Closing);
        this.ResumeLayout(false);
    }

    private void formRxSetup_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        RxDefList = RxDefs.refresh();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableRxSetup","Drug"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Controlled"), 70, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Sig"),320);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Disp"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Refills"),70);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableRxSetup","Notes"),300);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < RxDefList.Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(RxDefList[i].Drug);
            if (RxDefList[i].IsControlled)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            row.getCells().Add(RxDefList[i].Sig);
            row.getCells().Add(RxDefList[i].Disp);
            row.getCells().Add(RxDefList[i].Refills);
            row.getCells().Add(RxDefList[i].Notes);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormRxDefEdit FormE = new FormRxDefEdit(RxDefList[e.getRow()]);
        FormE.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        RxDef RxDefCur = new RxDef();
        RxDefs.insert(RxDefCur);
        //It gets deleted if user clicks cancel
        FormRxDefEdit FormE = new FormRxDefEdit(RxDefCur);
        FormE.IsNew = true;
        FormE.ShowDialog();
        fillGrid();
    }

    private void butAdd2_Click(Object sender, System.EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            MessageBox.Show(Lan.g(this,"Please select item first"));
            return ;
        }
         
        RxDef RxDefCur = RxDefList[gridMain.getSelectedIndex()].Copy();
        RxDefs.insert(RxDefCur);
        //Now it has a new id.  It gets deleted if user clicks cancel. Alerts not copied.
        FormRxDefEdit FormE = new FormRxDefEdit(RxDefCur);
        FormE.IsNew = true;
        FormE.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

    private void formRxSetup_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
    }

}


//SecurityLogs.MakeLogEntry("Prescription Setup","Altered Prescription Setup",user);