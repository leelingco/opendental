//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:55 PM
//

package OpenDental;

import OpenDental.FormCustomerManagement;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.RegistrationKeys;

/**
* 
*/
public class FormCustomerManagement  extends System.Windows.Forms.Form 
{
    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private ContextMenu contextMain = new ContextMenu();
    private MenuItem menuItemGoTo = new MenuItem();
    /**
    * Required designer variable.
    */
    private System.ComponentModel.Container components = null;
    /**
    * This will only contain a value if the user clicked GoTo.
    */
    public long SelectedPatNum = new long();
    private Label label1 = new Label();
    private DataTable TableRegKeys = new DataTable();
    /**
    * 
    */
    public FormCustomerManagement() throws Exception {
        //
        // Required for Windows Form Designer support
        //
        initializeComponent();
        Lan.f(this);
        gridMain.ContextMenu = contextMain;
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(FormCustomerManagement.class);
        this.contextMain = new System.Windows.Forms.ContextMenu();
        this.menuItemGoTo = new System.Windows.Forms.MenuItem();
        this.label1 = new System.Windows.Forms.Label();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.SuspendLayout();
        //
        // contextMain
        //
        this.contextMain.MenuItems.AddRange(new System.Windows.Forms.MenuItem[]{ this.menuItemGoTo });
        //
        // menuItemGoTo
        //
        this.menuItemGoTo.Index = 0;
        this.menuItemGoTo.Text = "GoTo";
        this.menuItemGoTo.Click += new System.EventHandler(this.menuItemGoTo_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(666, 19);
        this.label1.TabIndex = 18;
        this.label1.Text = "Below is a list of customers who have active registration keys where no family me" + "mbers have an active repeating charge. \r\n";
        //
        // gridMain
        //
        this.gridMain.setHScrollVisible(false);
        this.gridMain.Location = new System.Drawing.Point(12, 31);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.Size = new System.Drawing.Size(666, 623);
        this.gridMain.TabIndex = 2;
        this.gridMain.setTitle("Customers");
        this.gridMain.setTranslationName(null);
        //
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(714, 628);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 26);
        this.butClose.TabIndex = 0;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // FormCustomerManagement
        //
        this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
        this.ClientSize = new System.Drawing.Size(801, 666);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        this.MaximizeBox = false;
        this.MinimizeBox = false;
        this.Name = "FormCustomerManagement";
        this.ShowInTaskbar = false;
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Customer Management";
        this.Load += new System.EventHandler(this.FormCustomerManagement_Load);
        this.ResumeLayout(false);
    }

    private void formCustomerManagement_Load(Object sender, EventArgs e) throws Exception {
        fillGrid();
    }

    private void fillGrid() throws Exception {
        Cursor.Current = Cursors.WaitCursor;
        TableRegKeys = RegistrationKeys.getAllWithoutCharges();
        Cursor.Current = Cursors.Default;
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn("PatNum",60);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("RegKey",140);
        gridMain.getColumns().add(col);
        col = new ODGridColumn("Family",200);
        gridMain.getColumns().add(col);
        //col=new ODGridColumn("Repeating Charge",150);
        //gridMain.Columns.Add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < TableRegKeys.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(TableRegKeys.Rows[i]["PatNum"].ToString());
            row.getCells().Add(TableRegKeys.Rows[i]["RegKey"].ToString());
            row.getCells().Add(TableRegKeys.Rows[i]["LName"].ToString() + ", " + TableRegKeys.Rows[i]["FName"].ToString());
            //row.Cells.Add(table.Rows[i]["dateStop"].ToString());
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void menuItemGoTo_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedIndex() == -1)
        {
            return ;
        }
         
        SelectedPatNum = PIn.Long(TableRegKeys.Rows[gridMain.getSelectedIndex()]["PatNum"].ToString());
        Close();
    }

    private void butOK_Click(Object sender, System.EventArgs e) throws Exception {
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
        Close();
    }

}


