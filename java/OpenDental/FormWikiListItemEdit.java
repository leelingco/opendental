//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiLists;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiListItemEdit  extends Form 
{

    /**
    * Name of the wiki list.
    */
    public String WikiListCurName = new String();
    public long ItemNum = new long();
    public boolean IsNew = new boolean();
    /**
    * Creating a data table containing only one item allows us to use column names.
    */
    DataTable TableItem = new DataTable();
    public FormWikiListItemEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiListEdit_Load(Object sender, EventArgs e) throws Exception {
        TableItem = WikiLists.getItem(WikiListCurName,ItemNum);
        //Show the PK in the title bar for informational purposes.  We don't put it in the grid because user can't change it.
        this.Text = this.Text + " - " + TableItem.Columns[0] + " " + TableItem.Rows[0][0].ToString();
        //OK to use 0 indices here. If this fails something else is wrong.
        fillGrid();
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col = new ODGridColumn(Lan.g(this,"Column"),200);
        gridMain.getColumns().add(col);
        col = new ODGridColumn(Lan.g(this,"Value"),400);
        col.setIsEditable(true);
        gridMain.getColumns().add(col);
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 1;i < TableItem.Columns.Count;i++)
        {
            //Start at 1 since row 0 (PK) goes in the title bar.
            row = new OpenDental.UI.ODGridRow();
            row.getCells().Add(TableItem.Columns[i].ColumnName);
            row.getCells().Add(TableItem.Rows[0][i].ToString());
            if (i == 0)
            {
                row.setColorBackG(Color.Gray);
            }
             
            //darken the PK to imply that it cannot be edited.
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setTitle("Edit List Item");
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //remove this
    private void gridMain_CellTextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void gridMain_CellLeave(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            //Save data from grid into table. No call to DB, so this should be safe.
            TableItem.Rows[0][i + 1] = gridMain.getRows().get___idx(i).getCells()[1].Text;
        }
    }

    //Column 0 of TableItems.Rows[0] is in the title bar, so it is off from the grid by 1.
    //FillGrid();//Causes errors with tabbing between cells. We put the PK in the title bar to fix this (now it doesn't need to be refreshed).
    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //might want to implement a new security permission.
        //maybe require all empty or admin priv
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this list item and all references to it?"))
        {
            return ;
        }
         
        WikiLists.deleteItem(WikiListCurName,ItemNum);
        DialogResult = DialogResult.OK;
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        WikiLists.updateItem(WikiListCurName,TableItem);
        DialogResult = DialogResult.OK;
    }

    private void butCancel_Click(Object sender, EventArgs e) throws Exception {
        DialogResult = DialogResult.Cancel;
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
        this.butDelete = new OpenDental.UI.Button();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butDelete
        //
        this.butDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butDelete.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        this.butDelete.setAutosize(true);
        this.butDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butDelete.setCornerRadius(4F);
        this.butDelete.Image = Resources.getdeleteX();
        this.butDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
        this.butDelete.Location = new System.Drawing.Point(12, 653);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 36;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(456, 653);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 38;
        this.butOK.Text = "&OK";
        this.butOK.Click += new System.EventHandler(this.butOK_Click);
        //
        // butCancel
        //
        this.butCancel.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butCancel.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butCancel.setAutosize(true);
        this.butCancel.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butCancel.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butCancel.setCornerRadius(4F);
        this.butCancel.Location = new System.Drawing.Point(537, 653);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 37;
        this.butCancel.Text = "&Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setEditableAcceptsCR(true);
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(600, 638);
        this.gridMain.TabIndex = 26;
        this.gridMain.setTitle("");
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
        this.gridMain.CellTextChanged += new System.EventHandler(this.gridMain_CellTextChanged);
        this.gridMain.CellLeave = __MultiODGridClickEventHandler.combine(this.gridMain.CellLeave,new OpenDental.UI.ODGridClickEventHandler() 
          { 
            public System.Void invoke(System.Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
                this.gridMain_CellLeave(sender, e);
            }

            public List<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception {
                List<OpenDental.UI.ODGridClickEventHandler> ret = new ArrayList<OpenDental.UI.ODGridClickEventHandler>();
                ret.add(this);
                return ret;
            }
        
          });
        //
        // FormWikiListItemEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(624, 677);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.gridMain);
        this.Name = "FormWikiListItemEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Wiki List Item";
        this.Load += new System.EventHandler(this.FormWikiListEdit_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
}


