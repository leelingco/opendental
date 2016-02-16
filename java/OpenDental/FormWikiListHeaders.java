//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental;

import OpenDental.DataValid;
import OpenDental.Lan;
import OpenDental.MsgBox;
import OpenDental.PIn;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.InvalidType;
import OpenDentBusiness.WikiListHeaderWidth;
import OpenDentBusiness.WikiListHeaderWidths;

public class FormWikiListHeaders  extends Form 
{

    public String WikiListCurName = new String();
    /**
    * Widths must always be specified.  Not optional.
    */
    private List<WikiListHeaderWidth> ListTableHeaders = new List<WikiListHeaderWidth>();
    public FormWikiListHeaders() throws Exception {
        initializeComponent();
        Lan.F(this);
    }

    private void formWikiListHeaders_Load(Object sender, EventArgs e) throws Exception {
        ListTableHeaders = WikiListHeaderWidths.getForList(WikiListCurName);
        fillGrid();
    }

    /**
    * Each row of data becomes a column in the grid. This pattern is only used in a few locations.
    */
    private void fillGrid() throws Exception {
        gridMain.beginUpdate();
        gridMain.getColumns().Clear();
        ODGridColumn col;
        for (int i = 0;i < ListTableHeaders.Count;i++)
        {
            col = new ODGridColumn("",75,true);
            //blank table-column names. List column names are contained in the cells of the table.
            gridMain.getColumns().add(col);
        }
        gridMain.getRows().Clear();
        OpenDental.UI.ODGridRow row0 = new OpenDental.UI.ODGridRow();
        OpenDental.UI.ODGridRow row1 = new OpenDental.UI.ODGridRow();
        for (int i = 0;i < ListTableHeaders.Count;i++)
        {
            row0.getCells().Add(ListTableHeaders[i].ColName);
            row1.getCells().Add(ListTableHeaders[i].ColWidth.ToString());
        }
        gridMain.getRows().add(row0);
        gridMain.getRows().add(row1);
        gridMain.endUpdate();
    }

    private void butOK_Click(Object sender, EventArgs e) throws Exception {
        //Set primary key to correct name-----------------------------------------------------------------------
        gridMain.getRows().get___idx(0).getCells()[0].Text = WikiListCurName + "Num";
        for (int i = 0;i < gridMain.getColumns().Count;i++)
        {
            //prevents exceptions from occuring when user tries to rename PK.
            //Validate column names---------------------------------------------------------------------------------
            //ODGridCell colNameCell in gridMain.Rows[0].Cells){
            if (Regex.IsMatch(gridMain.getRows().get___idx(0).getCells()[i].Text, "^\\d"))
            {
                MsgBox.show(this,"Column cannot start with numbers.");
                return ;
            }
             
            if (Regex.IsMatch(gridMain.getRows().get___idx(0).getCells()[i].Text, "\\s"))
            {
                MsgBox.show(this,"Column names cannot contain spaces.");
                return ;
            }
             
            if (Regex.IsMatch(gridMain.getRows().get___idx(0).getCells()[i].Text, "\\W"))
            {
                //W=non-word chars
                MsgBox.show(this,"Column names cannot contain special characters.");
                return ;
            }
             
        }
        for (int i = 0;i < gridMain.getColumns().Count;i++)
        {
            //Check for reserved words--------------------------------------------------------------------------------
            //ODGridCell colNameCell in gridMain.Rows[0].Cells){
            if (DbHelper.isMySQLReservedWord(gridMain.getRows().get___idx(0).getCells()[i].Text))
            {
                MessageBox.Show(Lan.g(this,"Column name is a reserved word in MySQL") + ":" + gridMain.getRows().get___idx(0).getCells()[i].Text);
                return ;
            }
             
        }
        //primary key is caught by duplicate column name logic.
        //Check for duplicates-----------------------------------------------------------------------------------
        List<String> listColNamesCheck = new List<String>();
        for (int i = 0;i < gridMain.getColumns().Count;i++)
        {
            //ODGridCell colNameCell in gridMain.Rows[0].Cells){
            if (listColNamesCheck.Contains(gridMain.getRows().get___idx(0).getCells()[i].Text))
            {
                MessageBox.Show(Lan.g(this,"Duplicate column name detected") + ":" + gridMain.getRows().get___idx(0).getCells()[i].Text);
                return ;
            }
             
            listColNamesCheck.Add(gridMain.getRows().get___idx(0).getCells()[i].Text);
        }
        for (int i = 0;i < gridMain.getColumns().Count;i++)
        {
            //Validate column widths---------------------------------------------------------------------------------
            //ODGridCell colNameCell in gridMain.Rows[0].Cells){
            if (Regex.IsMatch(gridMain.getRows().get___idx(1).getCells()[i].Text, "\\D"))
            {
                // "\D" matches any non-decimal character
                MsgBox.show(this,"Column widths must only contain positive integers.");
                return ;
            }
             
            if (gridMain.getRows().get___idx(1).getCells()[i].Text.Contains("-") || gridMain.getRows().get___idx(1).getCells()[i].Text.Contains(".") || gridMain.getRows().get___idx(1).getCells()[i].Text.Contains(","))
            {
                //inlcude the comma for international support. For instance Pi = 3.1415 or 3,1415 depending on your region
                MsgBox.show(this,"Column widths must only contain positive integers.");
                return ;
            }
             
        }
        for (int i = 0;i < ListTableHeaders.Count;i++)
        {
            //save values to List<WikiListHeaderWidth> TableHeaders
            ListTableHeaders[i].ColName = PIn.String(gridMain.getRows().get___idx(0).getCells()[i].Text);
            ListTableHeaders[i].ColWidth = PIn.Int(gridMain.getRows().get___idx(1).getCells()[i].Text);
        }
        try
        {
            //Save data to database-----------------------------------------------------------------------------------
            WikiListHeaderWidths.updateNamesAndWidths(WikiListCurName,ListTableHeaders);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message);
            //will throw exception if table schema has changed since the window was opened.
            DialogResult = DialogResult.Cancel;
        }

        DataValid.setInvalid(InvalidType.Wiki);
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
        this.butOK = new OpenDental.UI.Button();
        this.butCancel = new OpenDental.UI.Button();
        this.gridMain = new OpenDental.UI.ODGrid();
        this.SuspendLayout();
        //
        // butOK
        //
        this.butOK.setAdjustImageLocation(new System.Drawing.Point(0, 0));
        this.butOK.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
        this.butOK.setAutosize(true);
        this.butOK.setBtnShape(OpenDental.UI.enumType.BtnShape.Rectangle);
        this.butOK.setBtnStyle(OpenDental.UI.enumType.XPStyle.Silver);
        this.butOK.setCornerRadius(4F);
        this.butOK.Location = new System.Drawing.Point(698, 172);
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
        this.butCancel.Location = new System.Drawing.Point(779, 172);
        this.butCancel.Name = "butCancel";
        this.butCancel.Size = new System.Drawing.Size(75, 24);
        this.butCancel.TabIndex = 20;
        this.butCancel.Text = "Cancel";
        this.butCancel.Click += new System.EventHandler(this.butCancel_Click);
        //
        // gridMain
        //
        this.gridMain.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) | System.Windows.Forms.AnchorStyles.Left) | System.Windows.Forms.AnchorStyles.Right)));
        this.gridMain.setHScrollVisible(true);
        this.gridMain.Location = new System.Drawing.Point(12, 12);
        this.gridMain.Name = "gridMain";
        this.gridMain.setScrollValue(0);
        this.gridMain.setSelectionMode(OpenDental.UI.GridSelectionMode.OneCell);
        this.gridMain.Size = new System.Drawing.Size(842, 137);
        this.gridMain.TabIndex = 26;
        this.gridMain.setTitle("");
        this.gridMain.setTranslationName("");
        //
        // FormWikiListHeaders
        //
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
        this.ClientSize = new System.Drawing.Size(868, 223);
        this.Controls.Add(this.gridMain);
        this.Controls.Add(this.butOK);
        this.Controls.Add(this.butCancel);
        this.Name = "FormWikiListHeaders";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "Edit Wiki List Headers";
        this.Load += new System.EventHandler(this.FormWikiListHeaders_Load);
        this.ResumeLayout(false);
    }

    private OpenDental.UI.Button butOK;
    private OpenDental.UI.Button butCancel;
    private OpenDental.UI.ODGrid gridMain;
}


