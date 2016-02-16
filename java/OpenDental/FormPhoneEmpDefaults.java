//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:10 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormPhoneEmpDefaultEdit;
import OpenDental.FormPhoneEmpDefaults;
import OpenDental.Lan;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.PhoneEmpStatusOverride;

/**
* Summary description for FormBasicTemplate.
*/
public class FormPhoneEmpDefaults  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.ODGrid gridMain;
    private IContainer components = new IContainer();
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.Button butAdd;
    private List<PhoneEmpDefault> ListPED = new List<PhoneEmpDefault>();
    /**
    * 
    */
    public FormPhoneEmpDefaults() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormPhoneEmpDefaults.class);
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
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
        this.butClose.Location = new System.Drawing.Point(879, 546);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 11;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // butAdd
        //
        this.butAdd.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.setAutosize(true);
        this.butAdd.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAdd.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAdd.setCornerRadius(4F);
        this.butAdd.Location = new System.Drawing.Point(446, 546);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 12;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(8, 14);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(946, 524);
        this.gridMain.TabIndex = 1;
        this.gridMain.setTitle("Phone Settings");
        this.gridMain.setTranslationName("");
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
        // FormPhoneEmpDefaults
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(966, 582);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Controls.Add(this.gridMain);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormPhoneEmpDefaults";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Phone Settings";
        this.Load += new System.EventHandler(this.FormAccountPick_Load);
        this.ResumeLayout(false);
    }

    private void formAccountPick_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("EmployeeNum",80);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("EmpName",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("IsGraphed", 65, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("HasColor", 60, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("RingGroup",65);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("PhoneExt",55);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("StatusOverride",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Notes",250);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("ComputerName",90);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Private", 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Triage", 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        ListPED = PhoneEmpDefaults.refresh();
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < ListPED.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(ListPED[i].EmployeeNum.ToString());
            row.getCells().Add(ListPED[i].EmpName);
            row.getCells().add(ListPED[i].IsGraphed ? "X" : "");
            row.getCells().add(ListPED[i].HasColor ? "X" : "");
            row.getCells().Add(ListPED[i].RingGroups.ToString());
            row.getCells().Add(ListPED[i].PhoneExt.ToString());
            if (ListPED[i].StatusOverride == PhoneEmpStatusOverride.None)
            {
                row.getCells().add("");
            }
            else
            {
                row.getCells().Add(ListPED[i].StatusOverride.ToString());
            } 
            row.getCells().Add(ListPED[i].Notes);
            row.getCells().Add(ListPED[i].ComputerName);
            row.getCells().add(ListPED[i].IsPrivateScreen ? "X" : "");
            row.getCells().add(ListPED[i].IsTriageOperator ? "X" : "");
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormPhoneEmpDefaultEdit formPED = new FormPhoneEmpDefaultEdit();
        formPED.PedCur = ListPED[e.getRow()];
        formPED.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormPhoneEmpDefaultEdit formPED = new FormPhoneEmpDefaultEdit();
        formPED.PedCur = new PhoneEmpDefault();
        formPED.IsNew = true;
        formPED.ShowDialog();
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        Close();
    }

}


