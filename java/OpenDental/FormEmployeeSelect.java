//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:08 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.DataValid;
import OpenDental.FormEmployeeEdit;
import OpenDental.FormEmployeeSelect;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.InvalidType;

/**
* 
*/
public class FormEmployeeSelect  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private System.ComponentModel.Container components = null;
    private OpenDental.UI.Button butAdd;
    //private ArrayList ALemployees;
    private OpenDental.UI.ODGrid gridMain;
    private GroupBox groupBox1 = new GroupBox();
    private OpenDental.UI.Button butDelete;
    private Label label1 = new Label();
    private boolean isChanged = new boolean();
    /**
    * 
    */
    public FormEmployeeSelect() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormEmployeeSelect.class);
        this.butClose = new OpenDental.UI.Button();
        this.butAdd = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.label1 = new System.Windows.Forms.Label();
        this.butDelete = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
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
        this.butClose.Location = new System.Drawing.Point(358, 446);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 16;
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
        this.butAdd.Location = new System.Drawing.Point(12, 446);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(78, 26);
        this.butAdd.TabIndex = 21;
        this.butAdd.Text = "&Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(268, 418);
        this.gridMain.TabIndex = 22;
        this.gridMain.setTitle("");
        this.gridMain.setTranslationName("FormEmployees");
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
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.label1);
        this.groupBox1.Controls.Add(this.butDelete);
        this.groupBox1.Location = new System.Drawing.Point(327, 170);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(117, 87);
        this.groupBox1.TabIndex = 23;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Advanced Tools";
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(6, 16);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(108, 29);
        this.label1.TabIndex = 24;
        this.label1.Text = "Delete all unused employees";
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(9, 47);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(97, 26);
        this.butDelete.TabIndex = 17;
        this.butDelete.Text = "Delete All";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // FormEmployeeSelect
        //
        this.AcceptButton = this.butClose;
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.CancelButton = this.butClose;
        this.ClientSize = new System.Drawing.Size(456, 486);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormEmployeeSelect";
        this.ShowInTaskbar = false;
        this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Employees";
        this.Closing += new System.ComponentModel.CancelEventHandler(this.FormEmployee_Closing);
        this.Load += new System.EventHandler(this.FormEmployeeSelect_Load);
        this.groupBox1.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private void formEmployeeSelect_Load(Object sender, System.EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Employees.refreshCache();
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("FormEmployeeSelect","FName"),75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormEmployeeSelect","LName"),75);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormEmployeeSelect","MiddleI"),50);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("FormEmployeeSelect","Hidden"), 50, HorizontalAlignment.Center);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < Employees.getListLong().Length;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(Employees.getListLong()[i].FName);
            row.getCells().Add(Employees.getListLong()[i].LName);
            row.getCells().Add(Employees.getListLong()[i].MiddleI);
            if (Employees.getListLong()[i].IsHidden)
            {
                row.getCells().add("X");
            }
            else
            {
                row.getCells().add("");
            } 
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void butAdd_Click(Object sender, System.EventArgs e) throws Exception {
        FormEmployeeEdit FormEE = new FormEmployeeEdit();
        FormEE.EmployeeCur = new Employee();
        FormEE.IsNew = true;
        FormEE.ShowDialog();
        fillGrid();
        isChanged = true;
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        long empNum = Employees.getListLong()[e.getRow()].EmployeeNum;
        FormEmployeeEdit FormEE = new FormEmployeeEdit();
        FormEE.EmployeeCur = Employees.getListLong()[e.getRow()];
        FormEE.ShowDialog();
        fillGrid();
        isChanged = true;
        for (int i = 0;i < Employees.getListLong().Length;i++)
        {
            if (Employees.getListLong()[i].EmployeeNum == empNum)
            {
                gridMain.setSelected(i,true);
            }
             
        }
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,true,"Schedules may be lost.  Continue?"))
        {
            return ;
        }
         
        for (int i = 0;i < Employees.getListLong().Length;i++)
        {
            try
            {
                Employees.Delete(Employees.getListLong()[i].EmployeeNum);
            }
            catch (Exception __dummyCatchVar0)
            {
            }
        
        }
        fillGrid();
    }

    private void butClose_Click(Object sender, System.EventArgs e) throws Exception {
        this.Close();
    }

    private void formEmployee_Closing(Object sender, System.ComponentModel.CancelEventArgs e) throws Exception {
        if (isChanged)
        {
            DataValid.setInvalid(InvalidType.Employees);
        }
         
    }

}


