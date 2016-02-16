//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:42 PM
//

package OpenDental;

import java.util.ArrayList;
import java.util.List;
import OpenDental.FormReqStudentEdit;
import OpenDental.FormReqStudentOne;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.__MultiODGridClickEventHandler;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.ReqStudents;

/**
* Summary description for FormBasicTemplate.
*/
public class FormReqStudentOne  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butCancel;
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    public long ProvNum = new long();
    private OpenDental.UI.ODGrid gridMain;
    private Provider prov;
    private DataTable table = new DataTable();
    //public bool IsSelectionMode;
    //<summary>If IsSelectionMode and DialogResult.OK, then this will contain the selected req.</summary>
    //public int SelectedReqStudentNum;
    /**
    * 
    */
    public FormReqStudentOne() throws Exception {
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormReqStudentOne.class);
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(738, 621);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 26);
        this.butCancel.TabIndex = 0;
        this.butCancel.Text = "&Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(19, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(698, 635);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Student Requirements");
        this.gridMain.setTranslationName("TableReqStudentOne");
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
        // FormReqStudentOne
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(825, 665);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormReqStudentOne";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Student Requirements - One";
        this.Load += new System.EventHandler(this.FormReqStudentOne_Load);
        this.ResumeLayout(false);
    }

    private void formReqStudentOne_Load(Object sender, EventArgs e) throws Exception {
        //if(IsSelectionMode){
        //}
        //else{
        //labelSelection.Visible=false;
        //butOK.Visible=false;
        //butCancel.Text=Lan.g(this,"Close");
        //}
        prov = Providers.getProv(ProvNum);
        Text = Lan.g(this,"Student Requirements - ") + Providers.getLongDesc(ProvNum);
        fillGrid();
    }

    private void fillGrid() throws Exception {
        table = ReqStudents.refreshOneStudent(ProvNum);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g("TableReqStudentOne","Course"),100);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableReqStudentOne","Requirement"),200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableReqStudentOne","Done"),40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableReqStudentOne","Patient"),140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g("TableReqStudentOne","Appointment"),190);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(table.Rows[i]["course"].ToString());
            row.getCells().Add(table.Rows[i]["requirement"].ToString());
            row.getCells().Add(table.Rows[i]["done"].ToString());
            row.getCells().Add(table.Rows[i]["patient"].ToString());
            row.getCells().Add(table.Rows[i]["appointment"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        //if(IsSelectionMode){
        //	if(table.Rows[e.Row]["appointment"].ToString()!=""){
        //		MsgBox.Show(this,"Already attached to an appointment.");
        //		return;
        //	}
        //	SelectedReqStudentNum=PIn.PInt(table.Rows[e.Row]["ReqStudentNum"].ToString());
        //	DialogResult=DialogResult.OK;
        //}
        //else{
        FormReqStudentEdit FormRSE = new FormReqStudentEdit();
        FormRSE.ReqCur = ReqStudents.GetOne(PIn.Long(table.Rows[e.getRow()]["ReqStudentNum"].ToString()));
        FormRSE.ShowDialog();
        if (FormRSE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        fillGrid();
    }

    //}
    //private void butOK_Click(object sender, System.EventArgs e) {
    //not accessible
    /*if(IsSelectionMode){
    				if(gridMain.GetSelectedIndex()==-1){
    					MsgBox.Show(this,"Please select a requirement first.");
    					return;
    				}
    				if(table.Rows[gridMain.GetSelectedIndex()]["appointment"].ToString()!="") {
    					MsgBox.Show(this,"Selected requirement is already attached to an appointment.");
    					return;
    				}
    				SelectedReqStudentNum=PIn.PInt(table.Rows[gridMain.GetSelectedIndex()]["ReqStudentNum"].ToString());
    				DialogResult=DialogResult.OK;
    			}*/
    //should never get to here.
    //}
    private void butCancel_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
    }

}


