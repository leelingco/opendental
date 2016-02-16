//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.FormWikiListHeaders;
import OpenDental.FormWikiListItemEdit;
import OpenDental.InputBox;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Permissions;
import OpenDentBusiness.Security;
import OpenDentBusiness.WikiListHeaderWidth;
import OpenDentBusiness.WikiListHeaderWidths;
import OpenDentBusiness.WikiLists;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiListEdit  extends Form 
{

    /**
    * Name of the wiki list being manipulated. This does not include the "wikilist" prefix. i.e. "networkdevices" not "wikilistnetworkdevices"
    */
    public String WikiListCurName = new String();
    public boolean IsNew = new boolean();
    private DataTable Table = new DataTable();
    public FormWikiListEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiListEdit_Load(Object sender, EventArgs e) throws Exception {
        if (!WikiLists.checkExists(WikiListCurName))
        {
            IsNew = true;
            WikiLists.createNewWikiList(WikiListCurName);
        }
         
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
        ActiveControl = textSearch;
    }

    //start in search box.
    /**
    * 
    */
    private void fillGrid() throws Exception {
        List<WikiListHeaderWidth> colHeaderWidths = WikiListHeaderWidths.getForList(WikiListCurName);
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        for (int c = 0;c < Table.Columns.Count;c++)
        {
            int colWidth = 100;
            for (Object __dummyForeachVar0 : colHeaderWidths)
            {
                //100 = default value in case something is malformed in the database.
                WikiListHeaderWidth colHead = (WikiListHeaderWidth)__dummyForeachVar0;
                if (StringSupport.equals(colHead.ColName, Table.Columns[c].ColumnName))
                {
                    colWidth = colHead.ColWidth;
                    break;
                }
                 
            }
            col = new ODGridColumn(Table.Columns[c].ColumnName, colWidth, false);
            gridMain.getColumns().add(col);
        }
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int c = 0;c < Table.Columns.Count;c++)
            {
                row.getCells().Add(Table.Rows[i][c].ToString());
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
        gridMain.setTitle(WikiListCurName);
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        FormWikiListItemEdit FormWLIE = new FormWikiListItemEdit();
        FormWLIE.WikiListCurName = WikiListCurName;
        FormWLIE.ItemNum = PIn.Long(Table.Rows[e.getRow()][0].ToString());
        FormWLIE.ShowDialog();
        //saving occurs from within the form.
        if (FormWLIE.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
    }

    private void gridMain_CellTextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void gridMain_CellLeave(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    /*
    			Table.Rows[e.Row][e.Col]=gridMain.Rows[e.Row].Cells[e.Col].Text;
    			Point cellSelected=new Point(gridMain.SelectedCell.X,gridMain.SelectedCell.Y);
    			FillGrid();//gridMain.SelectedCell gets cleared.
    			gridMain.SetSelected(cellSelected);*/
    /*No longer necessary because gridMain_CellLeave does this as text is changed.
    		///<summary>This is done before generating markup, when adding or removing rows or columns, and when changing from "none" view to another view.  FillGrid can't be done until this is done.</summary>
    		private void PumpGridIntoTable() {
    			//table and grid will only have the same numbers of rows and columns if the view is none.
    			//Otherwise, table may have more columns
    			//So this is only allowed when switching from the none view to some other view.
    			if(ViewShowing!=0) {
    				return;
    			}
    			for(int i=0;i<Table.Rows.Count;i++) {
    				for(int c=0;c<Table.Columns.Count;c++) {
    					Table.Rows[i][c]=gridMain.Rows[i].Cells[c].Text;
    				}
    			}
    		}*/
    private void butColumnLeft_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //gives a message box if no permission
        if (gridMain.getSelectedCell().X == -1)
        {
            return ;
        }
         
        Point pointNewSelectedCell = gridMain.getSelectedCell();
        pointNewSelectedCell.X = Math.Max(1, pointNewSelectedCell.X - 1);
        WikiLists.ShiftColumnLeft(WikiListCurName, Table.Columns[gridMain.getSelectedCell().X].ColumnName);
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
        gridMain.setSelected(pointNewSelectedCell);
    }

    private void butColumnRight_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //gives a message box if no permission
        if (gridMain.getSelectedCell().X == -1)
        {
            return ;
        }
         
        Point pointNewSelectedCell = gridMain.getSelectedCell();
        pointNewSelectedCell.X = Math.Min(gridMain.getColumns().Count - 1, pointNewSelectedCell.X + 1);
        WikiLists.ShiftColumnRight(WikiListCurName, Table.Columns[gridMain.getSelectedCell().X].ColumnName);
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
        gridMain.setSelected(pointNewSelectedCell);
    }

    private void butHeaders_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //gives a message box if no permission
        FormWikiListHeaders FormWLH = new FormWikiListHeaders();
        FormWLH.WikiListCurName = WikiListCurName;
        FormWLH.ShowDialog();
        if (FormWLH.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
    }

    private void butColumnAdd_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //gives a message box if no permission
        WikiLists.addColumn(WikiListCurName);
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
    }

    private void butColumnDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //gives a message box if no permission
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Select cell in column to be deleted first.");
            return ;
        }
         
        if (!WikiLists.CheckColumnEmpty(WikiListCurName, Table.Columns[gridMain.getSelectedCell().X].ColumnName))
        {
            MsgBox.show(this,"Column cannot be deleted because it conatins data.");
            return ;
        }
         
        WikiLists.DeleteColumn(WikiListCurName, Table.Columns[gridMain.getSelectedCell().X].ColumnName);
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
    }

    private void butAddItem_Click(Object sender, EventArgs e) throws Exception {
        FormWikiListItemEdit FormWLIE = new FormWikiListItemEdit();
        FormWLIE.WikiListCurName = WikiListCurName;
        FormWLIE.ItemNum = WikiLists.addItem(WikiListCurName);
        FormWLIE.ShowDialog();
        if (FormWLIE.DialogResult != DialogResult.OK)
        {
            WikiLists.deleteItem(FormWLIE.WikiListCurName,FormWLIE.ItemNum);
            return ;
        }
         
        //delete new item because dialog was not OK'ed.
        Table = WikiLists.getByName(WikiListCurName);
        fillGrid();
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            if (gridMain.getRows().get___idx(i).getCells()[0].Text == FormWLIE.ItemNum.ToString())
            {
                gridMain.getRows().get___idx(i).setColorBackG(Color.FromArgb(255, 255, 128));
                gridMain.scrollToIndex(i);
            }
             
        }
    }

    private void textSearch_TextChanged(Object sender, EventArgs e) throws Exception {
        boolean isScrollSet = false;
        for (int i = 0;i < gridMain.getRows().Count;i++)
        {
            gridMain.getRows().get___idx(i).setColorBackG(Color.White);
            //set all rows back to white.
            if (StringSupport.equals(textSearch.Text, ""))
            {
                continue;
            }
             
            for (int j = 0;j < gridMain.getColumns().Count;j++)
            {
                if (gridMain.getRows().get___idx(i).getCells()[j].Text.ToUpper().Contains(textSearch.Text.ToUpper()))
                {
                    gridMain.getRows().get___idx(i).setColorBackG(Color.FromArgb(255, 255, 128));
                    if (!isScrollSet)
                    {
                        //scroll to the first match in the list.
                        gridMain.scrollToIndex(i);
                        isScrollSet = true;
                    }
                     
                    break;
                }
                 
            }
        }
        //next row
        //end i
        //end i
        gridMain.Invalidate();
    }

    private void butRenameList_Click(Object sender, EventArgs e) throws Exception {
        //Logic copied from FormWikiLists.butAdd_Click()---------------------
        InputBox inputListName = new InputBox("New List Name");
        inputListName.ShowDialog();
        if (inputListName.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        //Format input as it would be saved in the database--------------------------------------------
        inputListName.textResult.Text = inputListName.textResult.Text.ToLower().Replace(" ", "");
        //Validate list name---------------------------------------------------------------------------
        if (DbHelper.isMySQLReservedWord(inputListName.textResult.Text))
        {
            //Can become an issue when retrieving column header names.
            MsgBox.show(this,"List name is a reserved word in MySQL.");
            return ;
        }
         
        if (StringSupport.equals(inputListName.textResult.Text, ""))
        {
            MsgBox.show(this,"List name cannot be blank.");
            return ;
        }
         
        if (WikiLists.CheckExists(inputListName.textResult.Text))
        {
            MsgBox.show(this,"List name already exists.");
            return ;
        }
         
        try
        {
            WikiLists.Rename(WikiListCurName, inputListName.textResult.Text);
            WikiListCurName = inputListName.textResult.Text;
            Table = WikiLists.getByName(WikiListCurName);
            fillGrid();
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, ex.Message);
        }
    
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!Security.isAuthorized(Permissions.WikiListSetup))
        {
            return ;
        }
         
        //gives a message box if no permission
        if (gridMain.getRows().Count > 0)
        {
            MsgBox.show(this,"Cannot delete a non-empty list.  Remove all items first and try again.");
            return ;
        }
         
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this entire list and all references to it?"))
        {
            return ;
        }
         
        WikiLists.deleteList(WikiListCurName);
        //Someday, if we have links to lists, then this is where we would update all the wikipages containing those links.  Remove links to data that was contained in the table.
        DialogResult = DialogResult.OK;
    }

    private void butClose_Click(Object sender, EventArgs e) throws Exception {
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
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butColumnDelete = new OpenDental.UI.Button();
        this.butHeaders = new OpenDental.UI.Button();
        this.butColumnInsert = new OpenDental.UI.Button();
        this.butColumnRight = new OpenDental.UI.Button();
        this.butColumnLeft = new OpenDental.UI.Button();
        this.butAddItem = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butClose = new OpenDental.UI.Button();
        this.label1 = new System.Windows.Forms.Label();
        this.textSearch = new System.Windows.Forms.TextBox();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.butRenameList = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.SuspendLayout();
        //
        // groupBox1
        //
        this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox1.Controls.Add(this.butColumnDelete);
        this.groupBox1.Controls.Add(this.butHeaders);
        this.groupBox1.Controls.Add(this.butColumnInsert);
        this.groupBox1.Controls.Add(this.butColumnRight);
        this.groupBox1.Controls.Add(this.butColumnLeft);
        this.groupBox1.Location = new System.Drawing.Point(861, 84);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(87, 141);
        this.groupBox1.TabIndex = 28;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Columns";
        //
        // butColumnDelete
        //
        this.butColumnDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColumnDelete.setAutosize(true);
        this.butColumnDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColumnDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColumnDelete.setCornerRadius(4F);
        this.butColumnDelete.Location = new System.Drawing.Point(8, 109);
        this.butColumnDelete.Name = "butColumnDelete";
        this.butColumnDelete.Size = new System.Drawing.Size(71, 24);
        this.butColumnDelete.TabIndex = 34;
        this.butColumnDelete.Text = "Delete";
        this.butColumnDelete.Click += new System.EventHandler(this.butColumnDelete_Click);
        //
        // butHeaders
        //
        this.butHeaders.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butHeaders.setAutosize(true);
        this.butHeaders.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butHeaders.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butHeaders.setCornerRadius(4F);
        this.butHeaders.Location = new System.Drawing.Point(8, 49);
        this.butHeaders.Name = "butHeaders";
        this.butHeaders.Size = new System.Drawing.Size(71, 24);
        this.butHeaders.TabIndex = 31;
        this.butHeaders.Text = "Edit";
        this.butHeaders.Click += new System.EventHandler(this.butHeaders_Click);
        //
        // butColumnInsert
        //
        this.butColumnInsert.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColumnInsert.setAutosize(true);
        this.butColumnInsert.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColumnInsert.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColumnInsert.setCornerRadius(4F);
        this.butColumnInsert.Location = new System.Drawing.Point(8, 79);
        this.butColumnInsert.Name = "butColumnInsert";
        this.butColumnInsert.Size = new System.Drawing.Size(71, 24);
        this.butColumnInsert.TabIndex = 33;
        this.butColumnInsert.Text = "Add";
        this.butColumnInsert.Click += new System.EventHandler(this.butColumnAdd_Click);
        //
        // butColumnRight
        //
        this.butColumnRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColumnRight.setAutosize(true);
        this.butColumnRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColumnRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColumnRight.setCornerRadius(4F);
        this.butColumnRight.Location = new System.Drawing.Point(49, 19);
        this.butColumnRight.Name = "butColumnRight";
        this.butColumnRight.Size = new System.Drawing.Size(30, 24);
        this.butColumnRight.TabIndex = 30;
        this.butColumnRight.Text = "R";
        this.butColumnRight.Click += new System.EventHandler(this.butColumnRight_Click);
        //
        // butColumnLeft
        //
        this.butColumnLeft.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColumnLeft.setAutosize(true);
        this.butColumnLeft.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColumnLeft.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColumnLeft.setCornerRadius(4F);
        this.butColumnLeft.Location = new System.Drawing.Point(8, 19);
        this.butColumnLeft.Name = "butColumnLeft";
        this.butColumnLeft.Size = new System.Drawing.Size(30, 24);
        this.butColumnLeft.TabIndex = 29;
        this.butColumnLeft.Text = "L";
        this.butColumnLeft.Click += new System.EventHandler(this.butColumnLeft_Click);
        //
        // butAddItem
        //
        this.butAddItem.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butAddItem.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butAddItem.setAutosize(true);
        this.butAddItem.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butAddItem.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butAddItem.setCornerRadius(4F);
        this.butAddItem.Location = new System.Drawing.Point(8, 19);
        this.butAddItem.Name = "butAddItem";
        this.butAddItem.Size = new System.Drawing.Size(71, 24);
        this.butAddItem.TabIndex = 31;
        this.butAddItem.Text = "Add";
        this.butAddItem.Click += new System.EventHandler(this.butAddItem_Click);
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
        this.butDelete.Location = new System.Drawing.Point(12, 589);
        this.butDelete.Name = "butDelete";
        this.butDelete.Size = new System.Drawing.Size(75, 24);
        this.butDelete.TabIndex = 36;
        this.butDelete.Text = "Delete";
        this.butDelete.Click += new System.EventHandler(this.butDelete_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setEditableAcceptsCR(true);
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(12, 32);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(843, 554);
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
        // butClose
        //
        this.butClose.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butClose.setAutosize(true);
        this.butClose.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butClose.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butClose.setCornerRadius(4F);
        this.butClose.Location = new System.Drawing.Point(865, 589);
        this.butClose.Name = "butClose";
        this.butClose.Size = new System.Drawing.Size(75, 24);
        this.butClose.TabIndex = 20;
        this.butClose.Text = "Close";
        this.butClose.Click += new System.EventHandler(this.butClose_Click);
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(12, 9);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(120, 16);
        this.label1.TabIndex = 38;
        this.label1.Text = "Search";
        this.label1.TextAlign = System.Drawing.ContentAlignment.TopRight;
        //
        // textSearch
        //
        this.textSearch.Location = new System.Drawing.Point(135, 6);
        this.textSearch.Name = "textSearch";
        this.textSearch.Size = new System.Drawing.Size(218, 20);
        this.textSearch.TabIndex = 37;
        this.textSearch.TextChanged += new System.EventHandler(this.textSearch_TextChanged);
        //
        // groupBox2
        //
        this.groupBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox2.Controls.Add(this.butAddItem);
        this.groupBox2.Location = new System.Drawing.Point(861, 231);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(87, 51);
        this.groupBox2.TabIndex = 35;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "Rows";
        //
        // groupBox3
        //
        this.groupBox3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupBox3.Controls.Add(this.butRenameList);
        this.groupBox3.Location = new System.Drawing.Point(861, 27);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(87, 51);
        this.groupBox3.TabIndex = 36;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "List";
        //
        // butRenameList
        //
        this.butRenameList.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRenameList.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butRenameList.setAutosize(true);
        this.butRenameList.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRenameList.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRenameList.setCornerRadius(4F);
        this.butRenameList.Location = new System.Drawing.Point(8, 19);
        this.butRenameList.Name = "butRenameList";
        this.butRenameList.Size = new System.Drawing.Size(71, 24);
        this.butRenameList.TabIndex = 31;
        this.butRenameList.Text = "Rename";
        this.butRenameList.Click += new System.EventHandler(this.butRenameList_Click);
        //
        // FormWikiListEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(952, 613);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.textSearch);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butClose);
        this.Name = "FormWikiListEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Wiki List";
        this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        this.Load += new System.EventHandler(this.FormWikiListEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private OpenDental.UI.Button butClose;
    private OpenDental.UI.ODGrid gridMain;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butHeaders;
    private OpenDental.UI.Button butColumnRight;
    private OpenDental.UI.Button butColumnLeft;
    private OpenDental.UI.Button butAddItem;
    private OpenDental.UI.Button butColumnDelete;
    private OpenDental.UI.Button butColumnInsert;
    private OpenDental.UI.Button butDelete;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.TextBox textSearch = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butRenameList;
}


