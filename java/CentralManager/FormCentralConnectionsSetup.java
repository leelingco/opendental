//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:40 PM
//

package CentralManager;

import CentralManager.FormCentralConnectionEdit;
import CS2JNet.System.StringSupport;
import OpenDental.UI.ODGridClickEventArgs;
import OpenDental.UI.ODGridColumn;
import OpenDental.UI.ODGridRow;
import OpenDentBusiness.CentralConnection;
import OpenDentBusiness.CentralConnections;
import java.util.ArrayList;
import java.util.List;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormCentralConnectionsSetup  extends Form 
{

    private List<CentralConnection> ConnList = new List<CentralConnection>();
    public FormCentralConnectionsSetup() throws Exception {
        initializeComponent();
    }

    private void formAggPathSetup_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        ConnList = CentralConnections.refresh("");
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        col = new ODGridColumn("#",40);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Database",320);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Note",300);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        ODGridRow row;
        for (int i = 0;i < ConnList.Count;i++)
        {
            row = new ODGridRow();
            row.getCells().Add(ConnList[i].ItemOrder.ToString());
            if (StringSupport.equals(ConnList[i].DatabaseName, ""))
            {
                //uri
                row.getCells().Add(ConnList[i].ServiceURI);
            }
            else
            {
                row.getCells().Add(ConnList[i].ServerName + ", " + ConnList[i].DatabaseName);
            } 
            row.getCells().Add(ConnList[i].Note);
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, ODGridClickEventArgs e) throws Exception {
        FormCentralConnectionEdit formC = new FormCentralConnectionEdit();
        formC.CentralConnectionCur = ConnList[e.getRow()];
        formC.ShowDialog();
        fillGrid();
    }

    private void butAdd_Click(Object sender, EventArgs e) throws Exception {
        FormCentralConnectionEdit formC = new FormCentralConnectionEdit();
        CentralConnection cc = new CentralConnection();
        cc.setIsNew(true);
            ;
        formC.CentralConnectionCur = cc;
        formC.ShowDialog();
        fillGrid();
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.butCancel = new System.Windows.Forms.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butAdd = new System.Windows.Forms.Button();
        this.SuspendLayout();
        //
        // butCancel
        //
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.Location = new System.Drawing.Point(631, 434);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 2;
        this.butCancel.Text = "Close";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.MultiExtended);
        this.gridMain.Size = new System.Drawing.Size(613, 446);
        this.gridMain.TabIndex = 4;
        this.gridMain.setTitle("Connections");
        this.gridMain.setTranslationName("");
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
        // butAdd
        //
        this.butAdd.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAdd.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butAdd.Location = new System.Drawing.Point(631, 12);
        this.butAdd.Name = "butAdd";
        this.butAdd.Size = new System.Drawing.Size(75, 24);
        this.butAdd.TabIndex = 5;
        this.butAdd.Text = "Add";
        this.butAdd.Click += new System.EventHandler(this.butAdd_Click);
        //
        // FormCentralConnectionsSetup
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(718, 470);
        this.Controls.Add(this.butAdd);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butCancel);
        this.Name = "FormCentralConnectionsSetup";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Setup Connections";
        this.Load += new System.EventHandler(this.FormAggPathSetup_Load);
        this.ResumeLayout(false);
    }

    private System.Windows.Forms.Button butCancel = new System.Windows.Forms.Button();
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.Button butAdd = new System.Windows.Forms.Button();
}


