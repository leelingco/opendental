//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.FormWikiTableHeaders;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.MsgBoxButtons;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import java.util.ArrayList;
import java.util.List;
import OpenDental.Properties.Resources;
import OpenDental.UI.__MultiODGridClickEventHandler;

public class FormWikiTableEdit  extends Form 
{

    /**
    * Both an incoming and outgoing parameter.
    */
    public String Markup = new String();
    private DataTable Table = new DataTable();
    private List<String> ColNames = new List<String>();
    /**
    * Widths must always be specified.  Not optional.
    */
    private List<int> ColWidths = new List<int>();
    /**
    * This is passed in from the calling form.  It is used when deciding whether to allow user to add tableviews.  Blocks them if more than one table in page.
    */
    public int CountTablesInPage = new int();
    public boolean IsNew = new boolean();
    public FormWikiTableEdit() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiTableEdit_Load(Object sender, EventArgs e) throws Exception {
        //strategy: when form loads, process incoming markup into column names, column widths, and a data table.
        //grid will need to be filled repeatedly, and it will pull from the above objects rather than the original markup
        //When user clicks ok, the objects are transformed back into markup.
        parseMarkup();
        fillGrid();
    }

    /**
    * Happens on Load, and will also happen when user manually edits markup.  Recursive.
    */
    private void parseMarkup() throws Exception {
        //{|
        //!Width="100"|Column Heading 1!!Width="150"|Column Heading 2!!Width="75"|Column Heading 3
        //|-
        //|Cell 1||Cell 2||Cell 3
        //|-
        //|Cell A||Cell B||Cell C
        //|}
        Table = new DataTable();
        ColNames = new List<String>();
        ColWidths = new List<int>();
        DataRow row = new DataRow();
        String[] cells = new String[]();
        String[] lines = Markup.Split(new String[]{ "{|\r\n", "\r\n|-\r\n", "\r\n|}" }, StringSplitOptions.RemoveEmptyEntries);
        for (int i = 0;i < lines.Length;i++)
        {
            if (lines[i].StartsWith("!"))
            {
                //header
                lines[i] = lines[i].Substring(1);
                //strips off the leading !
                cells = lines[i].Split(new String[]{ "!!" }, StringSplitOptions.None);
                for (int c = 0;c < cells.Length;c++)
                {
                    String colName = "";
                    if (!Regex.IsMatch(cells[c], "^(Width=\")\\d+\"\\|"))
                    {
                        //e.g. Width="90"|
                        MessageBox.Show("Table is corrupt.  Each header must start with Width=\"#\"|.  Please manually edit the markup in the following window.");
                        manuallyEdit();
                        return ;
                    }
                     
                    String width = cells[c].Substring(7);
                    //90"|Column Heading 1
                    width = width.Substring(0, width.IndexOf("\""));
                    //90
                    ColWidths.Add(PIn.Int(width));
                    colName = cells[c].Substring(cells[c].IndexOf("|") + 1);
                    ColNames.Add(colName);
                    Table.Columns.Add("");
                }
                continue;
            }
             
            //must be an empty string because Table object does not allow duplicate column names.
            if (StringSupport.equals(lines[i].Trim(), "|-"))
            {
                continue;
            }
             
            //totally ignore these rows
            //normal row.  Headers will have already been filled
            lines[i] = lines[i].Substring(1);
            //strips off the leading |
            cells = lines[i].Split(new String[]{ "||" }, StringSplitOptions.None);
            if (cells.Length != ColNames.Count || cells.Length != ColWidths.Count)
            {
                MessageBox.Show("Table is corrupt.  There are " + ColNames.Count.ToString() + " columns, but row " + ((i - 1) / 2).ToString() + " has " + cells.Length.ToString() + " cells.  Please manually edit the markup in the following window.");
                manuallyEdit();
                return ;
            }
             
            row = Table.NewRow();
            for (int c = 0;c < cells.Length;c++)
            {
                row[c] = cells[c];
            }
            Table.Rows.Add(row);
        }
    }

    private void manuallyEdit() throws Exception {
        CodeBase.MsgBoxCopyPaste msgbox = new CodeBase.MsgBoxCopyPaste(Markup);
        msgbox.ShowDialog();
        if (msgbox.DialogResult == DialogResult.OK)
        {
            Markup = msgbox.textMain.Text;
            parseMarkup();
        }
        else
        {
            //try again
            DialogResult = DialogResult.Cancel;
        } 
    }

    /**
    * 
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        for (int c = 0;c < ColNames.Count;c++)
        {
            col = new ODGridColumn(ColNames[c], ColWidths[c], true);
            gridMain.getColumns().add(col);
        }
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row;
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            row = new OpenDental.UI.ODGridRow();
            for (int c = 0;c < ColNames.Count;c++)
            {
                row.getCells().Add(Table.Rows[i][c].ToString());
            }
            gridMain.getRows().add(row);
        }
        gridMain.endUpdate();
    }

    private void gridMain_CellDoubleClick(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
    }

    //new big window to edit row
    private void gridMain_CellTextChanged(Object sender, EventArgs e) throws Exception {
    }

    private void gridMain_CellLeave(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception {
        Table.Rows[e.getRow()][e.getCol()] = gridMain.getRows().get___idx(e.getRow()).getCells()[e.getCol()].Text;
    }

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
    /**
    * Happens when user clicks OK.  Also happens when user wants to manually edit markup.
    */
    private String generateMarkup() throws Exception {
        StringBuilder strb = new StringBuilder();
        strb.AppendLine("{|");
        strb.Append("!");
        for (int c = 0;c < ColWidths.Count;c++)
        {
            if (c > 0)
            {
                strb.Append("!!");
            }
             
            if (ColWidths[c] > 0)
            {
                //otherwise, no width specified for this column.  Dynamic.
                strb.Append("Width=\"" + ColWidths[c].ToString() + "\"|");
            }
             
            strb.Append(ColNames[c]);
        }
        strb.AppendLine();
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            strb.AppendLine("|-");
            strb.Append("|");
            for (int c = 0;c < ColWidths.Count;c++)
            {
                if (c > 0)
                {
                    strb.Append("||");
                }
                 
                strb.Append(Table.Rows[i][c].ToString());
            }
            strb.AppendLine();
        }
        strb.Append("|}");
        return strb.ToString();
    }

    private void butManEdit_Click(Object sender, EventArgs e) throws Exception {
        //PumpGridIntoTable();
        Markup = generateMarkup();
        CodeBase.MsgBoxCopyPaste msgbox = new CodeBase.MsgBoxCopyPaste(Markup);
        msgbox.ShowDialog();
        if (msgbox.DialogResult != DialogResult.OK)
        {
            return ;
        }
         
        Markup = msgbox.textMain.Text;
        parseMarkup();
        fillGrid();
    }

    private void butColumnLeft_Click(Object sender, EventArgs e) throws Exception {
        //check to make sure you're not already at the left
        //we are guaranteed to have the Table and the gridMain synched.  Same # of rows and columns.
        //swap ColNames
        //swap ColWidths
        //Loop through table rows.
        //  Swap 2 cells.  Remember one of the first as part of the swap.
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a column first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().X == 0)
        {
            return ;
        }
         
        //Row is already on the left.
        String colName = new String();
        colName = ColNames[gridMain.getSelectedCell().X];
        ColNames[gridMain.getSelectedCell().X] = ColNames[gridMain.getSelectedCell().X - 1];
        ColNames[gridMain.getSelectedCell().X - 1] = colName;
        int width = new int();
        width = ColWidths[gridMain.getSelectedCell().X];
        ColWidths[gridMain.getSelectedCell().X] = ColWidths[gridMain.getSelectedCell().X - 1];
        ColWidths[gridMain.getSelectedCell().X - 1] = width;
        String cellText = new String();
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            cellText = Table.Rows[i][gridMain.getSelectedCell().X].ToString();
            Table.Rows[i][gridMain.getSelectedCell().X] = Table.Rows[i][gridMain.getSelectedCell().X - 1];
            Table.Rows[i][gridMain.getSelectedCell().X - 1] = cellText;
        }
        Point newCellSelected = new Point(gridMain.getSelectedCell().X - 1, gridMain.getSelectedCell().Y);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butColumnRight_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a column first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().X == Table.Columns.Count - 1)
        {
            return ;
        }
         
        //Row is already on the right.
        String colName = new String();
        colName = ColNames[gridMain.getSelectedCell().X];
        ColNames[gridMain.getSelectedCell().X] = ColNames[gridMain.getSelectedCell().X + 1];
        ColNames[gridMain.getSelectedCell().X + 1] = colName;
        int width = new int();
        width = ColWidths[gridMain.getSelectedCell().X];
        ColWidths[gridMain.getSelectedCell().X] = ColWidths[gridMain.getSelectedCell().X + 1];
        ColWidths[gridMain.getSelectedCell().X + 1] = width;
        String cellText = new String();
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            cellText = Table.Rows[i][gridMain.getSelectedCell().X].ToString();
            Table.Rows[i][gridMain.getSelectedCell().X] = Table.Rows[i][gridMain.getSelectedCell().X + 1];
            Table.Rows[i][gridMain.getSelectedCell().X + 1] = cellText;
        }
        Point newCellSelected = new Point(gridMain.getSelectedCell().X + 1, gridMain.getSelectedCell().Y);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butHeaders_Click(Object sender, EventArgs e) throws Exception {
        FormWikiTableHeaders FormWTH = new FormWikiTableHeaders();
        FormWTH.ColNames = ColNames;
        //Just passes the reference to the list in memory, so no need to "collect" the changes afterwords.
        FormWTH.ColWidths = ColWidths;
        //Just passes the reference to the list in memory, so no need to "collect" the changes afterwords.
        FormWTH.ShowDialog();
        fillGrid();
    }

    private void butColumnAdd_Click(Object sender, EventArgs e) throws Exception {
        int index = new int();
        if (gridMain.getSelectedCell().X == -1)
        {
            index = Table.Columns.Count - 1;
        }
        else
        {
            index = gridMain.getSelectedCell().X;
        } 
        Table.Columns.Add();
        ColNames.Insert(index + 1, "Header" + (Table.Columns.Count));
        ColWidths.Insert(index + 1, 100);
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            for (int j = gridMain.getColumns().Count - 1;j > index;j--)
            {
                Table.Rows[i][j + 1] = Table.Rows[i][j];
            }
            Table.Rows[i][index + 1] = "";
        }
        Point newCellSelected = new Point(index, gridMain.getSelectedCell().Y);
        if (gridMain.getSelectedCell().X == gridMain.getColumns().Count - 1)
        {
            //only if this is the last column
            newCellSelected = new Point(index + 1, gridMain.getSelectedCell().Y);
        }
         
        //shift the selected column to the right
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        if (newCellSelected.Y > -1)
        {
            gridMain.setSelected(newCellSelected);
        }
         
    }

    private void butColumnDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedCell().X == -1)
        {
            MsgBox.show(this,"Please select a column first.");
            return ;
        }
         
        if (gridMain.getColumns().Count == 1)
        {
            MsgBox.show(this,"Cannot delete last column.");
            return ;
        }
         
        ColNames.RemoveAt(gridMain.getSelectedCell().X);
        ColWidths.RemoveAt(gridMain.getSelectedCell().X);
        Table.Columns.RemoveAt(gridMain.getSelectedCell().X);
        Point newCellSelected = new Point(Math.Max(gridMain.getSelectedCell().X - 1, 0), gridMain.getSelectedCell().Y);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butRowUp_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedCell().Y == -1)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().Y == 0)
        {
            return ;
        }
         
        //Row is already at the top.
        DataRow row = Table.NewRow();
        for (int i = 0;i < Table.Columns.Count;i++)
        {
            row[i] = Table.Rows[gridMain.getSelectedCell().Y][i];
        }
        Table.Rows.InsertAt(row, gridMain.getSelectedCell().Y - 1);
        Table.Rows.RemoveAt(gridMain.getSelectedCell().Y + 1);
        Point newCellSelected = new Point(gridMain.getSelectedCell().X, gridMain.getSelectedCell().Y - 1);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butRowDown_Click(Object sender, EventArgs e) throws Exception {
        //Table.Rows.InsertAt
        //DataRow row=Table.Rows[i];
        //Table.Rows.RemoveAt
        if (gridMain.getSelectedCell().Y == -1)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        if (gridMain.getSelectedCell().Y == Table.Rows.Count - 1)
        {
            return ;
        }
         
        //Row is already at the bottom.
        DataRow row = Table.NewRow();
        for (int i = 0;i < Table.Columns.Count;i++)
        {
            row[i] = Table.Rows[gridMain.getSelectedCell().Y + 1][i];
        }
        Table.Rows.InsertAt(row, gridMain.getSelectedCell().Y);
        Table.Rows.RemoveAt(gridMain.getSelectedCell().Y + 2);
        Point newCellSelected = new Point(gridMain.getSelectedCell().X, gridMain.getSelectedCell().Y + 1);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butRowAdd_Click(Object sender, EventArgs e) throws Exception {
        //DataRow row=Table.NewRow();
        //Table.Rows.InsertAt(row,i);
        Point selectedCell = new Point();
        if (gridMain.getSelectedCell().Y == -1)
        {
            selectedCell = new Point(0, Table.Rows.Count - 1);
        }
        else
        {
            selectedCell = gridMain.getSelectedCell();
        } 
        DataRow row = Table.NewRow();
        Table.Rows.InsertAt(row, selectedCell.Y + 1);
        Point newCellSelected = new Point(selectedCell.X, selectedCell.Y + 1);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butRowDelete_Click(Object sender, EventArgs e) throws Exception {
        if (gridMain.getSelectedCell().Y == -1)
        {
            MsgBox.show(this,"Please select a row first.");
            return ;
        }
         
        if (gridMain.getRows().Count == 1)
        {
            MsgBox.show(this,"Cannot delete last row.");
            return ;
        }
         
        Table.Rows.RemoveAt(gridMain.getSelectedCell().Y);
        Point newCellSelected = new Point(gridMain.getSelectedCell().X, Math.Max(gridMain.getSelectedCell().Y - 1, 0));
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        if (newCellSelected.X > -1 && newCellSelected.Y > -1)
        {
            gridMain.setSelected(newCellSelected);
        }
         
    }

    private void butDelete_Click(Object sender, EventArgs e) throws Exception {
        if (!MsgBox.show(this,MsgBoxButtons.OKCancel,"Delete this entire table?"))
        {
            return ;
        }
         
        if (IsNew)
        {
            DialogResult = DialogResult.Cancel;
        }
        else
        {
            Markup = null;
            DialogResult = DialogResult.OK;
        } 
    }

    private void butPaste_Click(Object sender, EventArgs e) throws Exception {
        Point pointStarting = gridMain.getSelectedCell();
        int colsNeeded = 0;
        int rowsNeeded = 0;
        //the incoming text is not necessarily rectangular
        List<List<String>> listTblBuilder = new List<List<String>>();
        //tableBuilder[Y][X] to access cell.
        String clipBoardText = Clipboard.GetText();
        String[] arrayTableRows = clipBoardText.Split(new String[]{ "\r\n" }, StringSplitOptions.None);
        rowsNeeded = arrayTableRows.Length;
        List<String> listCurrentRow = new List<String>();
        for (int i = 0;i < arrayTableRows.Length;i++)
        {
            listCurrentRow = new List<String>();
            //currentRow[X] to access cell data
            String[] rowCells = arrayTableRows[i].Split('\t');
            for (Object __dummyForeachVar0 : rowCells)
            {
                String cell = (String)__dummyForeachVar0;
                listCurrentRow.Add(cell);
            }
            listTblBuilder.Add(listCurrentRow);
            colsNeeded = Math.Max(listCurrentRow.Count, colsNeeded);
        }
        //At this point:
        //colsNeeded = number of columns needed
        //rowsNeeded = number of rows needed
        //access data as arrayTblBuilder[Y][X], arrayTblBuilder contains all of the table data in a potentially uneven array (technically a list),
        //Check for enough columns---------------------------------------------------------------------------------------------------------
        if (pointStarting.X + colsNeeded > Table.Columns.Count)
        {
            MessageBox.Show(this, Lan.g(this,"Additional columns required to paste") + ": " + (pointStarting.X + colsNeeded - gridMain.getColumns().Count));
            return ;
        }
         
        //Check for Content----------------------------------------------------------------------------------------------------------------
        boolean contentExists = false;
        for (int x = 0;x + pointStarting.X < Table.Columns.Count;x++)
        {
            for (int y = 0;y + pointStarting.Y < Table.Rows.Count;y++)
            {
                contentExists = contentExists || !Table.Rows[pointStarting.Y + y][pointStarting.X + x].ToString().Equals("");
            }
        }
        if (contentExists)
        {
            if (!MsgBox.show(this,MsgBoxButtons.YesNo,"Would you like to continue and overwrite existing content?"))
            {
                return ;
            }
             
        }
         
        //Add New Rows---------------------------------------------------------------------------------------------------------------------
        //Must be after check for existing content, otherwise you will add rows when they are not necessary.
        if (Table.Rows.Count < pointStarting.Y + rowsNeeded)
        {
            int newRowsNeededCount = (pointStarting.Y + rowsNeeded) - Table.Rows.Count;
            for (int i = 0;i < newRowsNeededCount;i++)
            {
                Table.Rows.Add(Table.NewRow());
            }
        }
         
        for (int i = 0;i < listTblBuilder.Count;i++)
        {
            for (int j = 0;j < listTblBuilder[i].Count;j++)
            {
                //Paste new data into data Table---------------------------------------------------------------------------------------------------
                //gridMain.Rows[startingPoint.Y+i].Cells[startingPoint.X+j].Text=tableBuilder[i][j];
                Table.Rows[pointStarting.Y + i][pointStarting.X + j] = listTblBuilder[i][j];
            }
        }
        //Redraw Grid
        Point newCellSelected = new Point(gridMain.getSelectedCell().X, gridMain.getSelectedCell().Y);
        fillGrid();
        //gridMain.SelectedCell gets cleared.
        gridMain.setSelected(newCellSelected);
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        for (int h = 0;h < gridMain.getColumns().Count;h++)
        {
            //PumpGridIntoTable();
            //loops through every header in the main grid
            String s = gridMain.getColumns().get___idx(h).getHeading().ToString();
            s = s.Replace("&", "&amp;");
            s = s.Replace("&amp;<", "&lt;");
            //because "&" was changed to "&amp;" in the line above.
            s = s.Replace("&amp;>", "&gt;");
            //because "&" was changed to "&amp;" in the line above.
            s = "<body>" + s + "</body>";
            //Surround with body tags to make it valid xml
            XmlDocument doc = new XmlDocument();
            StringReader reader = new StringReader(s);
            try
            {
                {
                    try
                    {
                        doc.Load(reader);
                    }
                    catch (Exception __dummyCatchVar0)
                    {
                        //Loading this document provides error checking
                        MessageBox.Show(Lan.g(this,"The header for column " + (h + 1) + " is invalid."));
                        return ;
                    }
                
                }
            }
            finally
            {
                if (reader != null)
                    Disposable.mkDisposable(reader).dispose();
                 
            }
        }
        for (int i = 0;i < Table.Rows.Count;i++)
        {
            for (int j = 0;j < Table.Columns.Count;j++)
            {
                //loops through each row in the table
                //loops through each column in the row
                XmlDocument doc = new XmlDocument();
                String s = Table.Rows[i][j].ToString();
                s = s.Replace("&", "&amp;");
                s = s.Replace("&amp;<", "&lt;");
                //because "&" was changed to "&amp;" in the line above.
                s = s.Replace("&amp;>", "&gt;");
                //because "&" was changed to "&amp;" in the line above.
                s = "<body>" + s + "</body>";
                StringReader reader = new StringReader(s);
                try
                {
                    {
                        try
                        {
                            doc.Load(reader);
                        }
                        catch (Exception __dummyCatchVar1)
                        {
                            MessageBox.Show(Lan.g(this,"The cell at column " + (j + 1) + ", row " + (i + 1) + " is invalid"));
                            return ;
                        }
                    
                    }
                }
                finally
                {
                    if (reader != null)
                        Disposable.mkDisposable(reader).dispose();
                     
                }
            }
        }
        Markup = generateMarkup();
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
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.butColumnDelete = new OpenDental.UI.Button();
        this.butHeaders = new OpenDental.UI.Button();
        this.butColumnInsert = new OpenDental.UI.Button();
        this.butColumnRight = new OpenDental.UI.Button();
        this.butColumnLeft = new OpenDental.UI.Button();
        this.groupRow = new System.Windows.Forms.GroupBox();
        this.butRowDelete = new OpenDental.UI.Button();
        this.butRowInsert = new OpenDental.UI.Button();
        this.butRowDown = new OpenDental.UI.Button();
        this.butRowUp = new OpenDental.UI.Button();
        this.butDelete = new OpenDental.UI.Button();
        this.butManEdit = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.butPaste = new OpenDental.UI.Button();
        this.groupBox1.SuspendLayout();
        this.groupRow.SuspendLayout();
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
        this.groupBox1.Location = new System.Drawing.Point(861, 66);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(88, 141);
        this.groupBox1.TabIndex = 28;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "Column";
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
        this.butHeaders.Text = "Headers";
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
        this.butColumnInsert.Text = "Add Column";
        this.butColumnInsert.Click += new System.EventHandler(this.butColumnAdd_Click);
        //
        // butColumnRight
        //
        this.butColumnRight.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butColumnRight.setAutosize(true);
        this.butColumnRight.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butColumnRight.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butColumnRight.setCornerRadius(4F);
        this.butColumnRight.Location = new System.Drawing.Point(47, 19);
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
        // groupRow
        //
        this.groupRow.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.groupRow.Controls.Add(this.butRowDelete);
        this.groupRow.Controls.Add(this.butRowInsert);
        this.groupRow.Controls.Add(this.butRowDown);
        this.groupRow.Controls.Add(this.butRowUp);
        this.groupRow.Location = new System.Drawing.Point(861, 214);
        this.groupRow.Name = "groupRow";
        this.groupRow.Size = new System.Drawing.Size(88, 141);
        this.groupRow.TabIndex = 32;
        this.groupRow.TabStop = false;
        this.groupRow.Text = "Row";
        //
        // butRowDelete
        //
        this.butRowDelete.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRowDelete.setAutosize(true);
        this.butRowDelete.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRowDelete.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRowDelete.setCornerRadius(4F);
        this.butRowDelete.Location = new System.Drawing.Point(8, 109);
        this.butRowDelete.Name = "butRowDelete";
        this.butRowDelete.Size = new System.Drawing.Size(71, 24);
        this.butRowDelete.TabIndex = 32;
        this.butRowDelete.Text = "Delete";
        this.butRowDelete.Click += new System.EventHandler(this.butRowDelete_Click);
        //
        // butRowInsert
        //
        this.butRowInsert.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRowInsert.setAutosize(true);
        this.butRowInsert.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRowInsert.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRowInsert.setCornerRadius(4F);
        this.butRowInsert.Location = new System.Drawing.Point(8, 79);
        this.butRowInsert.Name = "butRowInsert";
        this.butRowInsert.Size = new System.Drawing.Size(71, 24);
        this.butRowInsert.TabIndex = 31;
        this.butRowInsert.Text = "Add Row";
        this.butRowInsert.Click += new System.EventHandler(this.butRowAdd_Click);
        //
        // butRowDown
        //
        this.butRowDown.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRowDown.setAutosize(true);
        this.butRowDown.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRowDown.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRowDown.setCornerRadius(4F);
        this.butRowDown.Location = new System.Drawing.Point(8, 49);
        this.butRowDown.Name = "butRowDown";
        this.butRowDown.Size = new System.Drawing.Size(44, 24);
        this.butRowDown.TabIndex = 30;
        this.butRowDown.Text = "Down";
        this.butRowDown.Click += new System.EventHandler(this.butRowDown_Click);
        //
        // butRowUp
        //
        this.butRowUp.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butRowUp.setAutosize(true);
        this.butRowUp.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butRowUp.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butRowUp.setCornerRadius(4F);
        this.butRowUp.Location = new System.Drawing.Point(8, 19);
        this.butRowUp.Name = "butRowUp";
        this.butRowUp.Size = new System.Drawing.Size(44, 24);
        this.butRowUp.TabIndex = 29;
        this.butRowUp.Text = "Up";
        this.butRowUp.Click += new System.EventHandler(this.butRowUp_Click);
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
        // butManEdit
        //
        this.butManEdit.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butManEdit.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butManEdit.setAutosize(true);
        this.butManEdit.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butManEdit.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butManEdit.setCornerRadius(4F);
        this.butManEdit.Location = new System.Drawing.Point(865, 12);
        this.butManEdit.Name = "butManEdit";
        this.butManEdit.Size = new System.Drawing.Size(75, 24);
        this.butManEdit.TabIndex = 27;
        this.butManEdit.Text = "Man Edit";
        this.butManEdit.Click += new System.EventHandler(this.butManEdit_Click);
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
        this.gridMain.Size = new System.Drawing.Size(842, 574);
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
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(784, 589);
        this.butOK.Name = "butOK";
        this.butOK.Size = new System.Drawing.Size(75, 24);
        this.butOK.TabIndex = 21;
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
        this.butCancel.Location = new System.Drawing.Point(865, 589);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 20;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // butPaste
        //
        this.butPaste.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butPaste.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        this.butPaste.setAutosize(true);
        this.butPaste.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butPaste.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butPaste.setCornerRadius(4F);
        this.butPaste.Location = new System.Drawing.Point(861, 361);
        this.butPaste.Name = "butPaste";
        this.butPaste.Size = new System.Drawing.Size(88, 24);
        this.butPaste.TabIndex = 37;
        this.butPaste.Text = "Paste Cells";
        this.butPaste.Click += new System.EventHandler(this.butPaste_Click);
        //
        // FormWikiTableEdit
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(952, 613);
        this.Controls.Add(this.butPaste);
        this.Controls.Add(this.butDelete);
        this.Controls.Add(this.groupRow);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butManEdit);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiTableEdit";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Wiki Table";
        this.Load += new System.EventHandler(this.FormWikiTableEdit_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupRow.ResumeLayout(false);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
    private OpenDental.UI.Button butManEdit;
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butHeaders;
    private OpenDental.UI.Button butColumnRight;
    private OpenDental.UI.Button butColumnLeft;
    private System.Windows.Forms.GroupBox groupRow = new System.Windows.Forms.GroupBox();
    private OpenDental.UI.Button butRowDelete;
    private OpenDental.UI.Button butRowInsert;
    private OpenDental.UI.Button butRowDown;
    private OpenDental.UI.Button butRowUp;
    private OpenDental.UI.Button butColumnDelete;
    private OpenDental.UI.Button butColumnInsert;
    private OpenDental.UI.Button butDelete;
    private OpenDental.UI.Button butPaste;
}


