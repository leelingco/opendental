//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PIn;
import OpenDental.UI.GridSortingStrategy;
import OpenDental.UI.ODGridColumnCollection;
import OpenDental.UI.ODGridRowCollection;
import OpenDentBusiness.Tooth;
import OpenDentBusiness.YN;

public class ODGrid  extends System.Windows.Forms.UserControl 
{
    private System.ComponentModel.Container components = null;
    private ODGridColumnCollection columns;
    public OpenDental.UI.ODGridClickEventHandler CellDoubleClick = null;
    public OpenDental.UI.ODGridClickEventHandler CellClick = null;
    public EventHandler CellTextChanged = null;
    public OpenDental.UI.ODGridClickEventHandler CellLeave = null;
    private String title = new String();
    private float cellFontSize = 8.5f;
    private int titleHeight = 18;
    private int headerHeight = 15;
    private Color cGridLine = Color.FromArgb(180, 180, 180);
    //Color.FromArgb(211,211,211);
    //Color.FromArgb(192,192,192);
    //Color.FromArgb(157,157,161);
    private Color cTitleBackG = Color.FromArgb(210, 210, 210);
    //(224,223,227);
    private Color cBlueOutline = Color.FromArgb(119, 119, 146);
    private System.Windows.Forms.VScrollBar vScroll = new System.Windows.Forms.VScrollBar();
    private System.Windows.Forms.HScrollBar hScroll = new System.Windows.Forms.HScrollBar();
    private ODGridRowCollection rows;
    private boolean IsUpdating = new boolean();
    /**
    * The total height of the grid.
    */
    private int GridH = new int();
    /**
    * The total width of the grid.
    */
    private int GridW = new int();
    /**
    * This array has one element for each row.  For each row, it keeps track of the vertical height of the row in pixels, not counting the note portion of the row.
    */
    private int[] RowHeights = new int[]();
    /**
    * This array has one element for each row.  For each row, it keeps track of the vertical height of only the note portion of the row in pixels.  Usually 0, unless you want notes showing.
    */
    private int[] NoteHeights = new int[]();
    /**
    * This array has one element for each row.  For each row, it keeps track of the vertical location at which to start drawing this row in pixels.  This makes it much easier to paint rows.
    */
    private int[] RowLocs = new int[]();
    private boolean hScrollVisible = new boolean();
    /**
    * Set at the very beginning of OnPaint.  Uses the ColWidth of each column to set up this array with one element for each column.  Contains the columns Pos for that column.
    */
    private int[] ColPos = new int[]();
    private ArrayList selectedIndices = new ArrayList();
    private Point selectedCell = new Point();
    private int MouseDownRow = new int();
    private int MouseDownCol = new int();
    private boolean ControlIsDown = new boolean();
    private boolean ShiftIsDown = new boolean();
    //private bool UpDownKey;
    private OpenDental.UI.GridSelectionMode selectionMode = OpenDental.UI.GridSelectionMode.None;
    private boolean MouseIsDown = new boolean();
    private boolean MouseIsOver = new boolean();
    //helps automate scrolling
    private String translationName = new String();
    private Color selectedRowColor = new Color();
    private boolean allowSelection = new boolean();
    private boolean wrapText = new boolean();
    private int noteSpanStart = new int();
    private int noteSpanStop = new int();
    private TextBox editBox = new TextBox();
    private MouseButtons lastButtonPressed = new MouseButtons();
    private ArrayList selectedIndicesWhenMouseDown = new ArrayList();
    private boolean allowSortingByColumn = new boolean();
    private boolean mouseIsDownInHeader = new boolean();
    /**
    * Typically -1 to show no triangle.  Or, specify a column to show a triangle.  The actual sorting happens at mouse down.
    */
    private int sortedByColumnIdx = new int();
    /**
    * True to show a triangle pointing up.  False to show a triangle pointing down.  Only works if sortedByColumnIdx is not -1.
    */
    private boolean sortedIsAscending = new boolean();
    //private List<List<int>> multiPageNoteHeights;//If NoteHeights[i] won't fit on one page, get various page heights here (printing).
    //private List<List<string>> multiPageNoteSection;//Section of the note that is split up across multiple pages.
    private int RowsPrinted = new int();
    /**
    * If we are part way through drawing a note when we reach the end of a page, this will contain the remainder of the note still to be printed.  If it is empty string, then we are not in the middle of a note.
    */
    private String NoteRemaining = new String();
    private Point oldSelectedCell = new Point();
    /**
    * Holds the amount of the grid that is hidden due to the user making the window too small.  We need to keep track of this so that when they resize the window the scroll bar will become visible again.
    */
    private int widthHidden = new int();
    /**
    * Is set when ComputeRows is called, then used . If any columns are editable HasEditableColumn is true.
    */
    private boolean HasEditableColumn = new boolean();
    /**
    * 
    */
    private static final int EDITABLE_ROW_HEIGHT = 19;
    private boolean editableAcceptsCR = new boolean();
    /**
    * 
    */
    public ODGrid() throws Exception {
        //InitializeComponent();// Required for Windows.Forms Class Composition Designer support
        //Add any constructor code after InitializeComponent call
        columns = new ODGridColumnCollection();
        rows = new ODGridRowCollection();
        vScroll = new VScrollBar();
        vScroll.Scroll += new ScrollEventHandler(vScroll_Scroll);
        vScroll.MouseEnter += new EventHandler(vScroll_MouseEnter);
        vScroll.MouseLeave += new EventHandler(vScroll_MouseLeave);
        vScroll.MouseMove += new MouseEventHandler(vScroll_MouseMove);
        hScroll = new HScrollBar();
        hScroll.Scroll += new ScrollEventHandler(hScroll_Scroll);
        hScroll.MouseEnter += new EventHandler(hScroll_MouseEnter);
        hScroll.MouseLeave += new EventHandler(hScroll_MouseLeave);
        hScroll.MouseMove += new MouseEventHandler(hScroll_MouseMove);
        this.Controls.Add(vScroll);
        this.Controls.Add(hScroll);
        selectedIndices = new ArrayList();
        selectedCell = new Point(-1, -1);
        selectionMode = OpenDental.UI.GridSelectionMode.One;
        selectedRowColor = Color.Silver;
        allowSelection = true;
        wrapText = true;
        noteSpanStart = 0;
        noteSpanStop = 0;
        sortedByColumnIdx = -1;
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
        components = new System.ComponentModel.Container();
    }

    /**
    * 
    */
    protected void onLoad(EventArgs e) throws Exception {
        super.OnLoad(e);
        if (this.Parent != null)
        {
            this.Parent.MouseWheel += new MouseEventHandler(Parent_MouseWheel);
            this.Parent.KeyDown += new KeyEventHandler(Parent_KeyDown);
            this.Parent.KeyUp += new KeyEventHandler(Parent_KeyUp);
        }
         
    }

    /**
    * 
    */
    protected void onResize(EventArgs e) throws Exception {
        super.OnResize(e);
        layoutScrollBars();
        Invalidate();
    }

    /**
    * Gets the collection of ODGridColumns assigned to the ODGrid control.
    */
    //[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
    //[Editor(typeof(System.ComponentModel.Design.CollectionEditor),typeof(System.Drawing.Design.UITypeEditor))]
    //[Browsable(false)]//only because MS is buggy.
    public ODGridColumnCollection getColumns() throws Exception {
        return columns;
    }

    /**
    * Gets the collection of ODGridRows assigned to the ODGrid control.
    */
    public ODGridRowCollection getRows() throws Exception {
        return rows;
    }

    /**
    * The title of the grid which shows across the top.
    */
    public String getTitle() throws Exception {
        return title;
    }

    public void setTitle(String value) throws Exception {
        title = value;
        Invalidate();
    }

    /**
    * Set true to show a horizontal scrollbar.  Vertical scrollbar always shows, but is disabled if not needed.  If hScroll is not visible, then grid will auto reset width to match width of columns.
    */
    public boolean getHScrollVisible() throws Exception {
        return hScrollVisible;
    }

    public void setHScrollVisible(boolean value) throws Exception {
        hScrollVisible = value;
        layoutScrollBars();
        Invalidate();
    }

    /**
    * The index of the row that is the first row displayed on the ODGrid. Also sets ScrollValue.
    */
    public void scrollToIndex(int index) throws Exception {
        if (index > getRows().Count)
        {
            return ;
        }
         
        setScrollValue(RowLocs[index]);
    }

    /**
    * Gets or sets the position of the vertical scrollbar.  Does all error checking and invalidates.
    */
    public int getScrollValue() throws Exception {
        return vScroll.Value;
    }

    public void setScrollValue(int value) throws Exception {
        if (!vScroll.Enabled)
        {
            return ;
        }
         
        int scrollValue = 0;
        if (value > vScroll.Maximum - vScroll.LargeChange)
        {
            scrollValue = vScroll.Maximum - vScroll.LargeChange;
        }
        else if (value < vScroll.Minimum)
        {
            scrollValue = vScroll.Minimum;
        }
        else
        {
            scrollValue = value;
        }  
        try
        {
            vScroll.Value = scrollValue;
        }
        catch (Exception e)
        {
            //This should never ever happen.
            //Showing a messagebox is NOT our normal way of handling errors on mouse events, but the user would get a popup for the unhandled exception, anyway.
            MessageBox.Show("Error: Invalid Scroll Value. \r\n" + "Scroll value from: " + vScroll.Value + "\r\n" + "Scroll value to: " + scrollValue + "\r\n" + "Min scroll value: " + vScroll.Minimum + "\r\n" + "Max scroll value: " + vScroll.Maximum + "\r\n" + "Large change value: " + vScroll.LargeChange + "\r\n\r\n" + e.ToString());
            vScroll.Value = vScroll.Minimum;
        }

        if (editBox != null)
        {
            editBox.Dispose();
        }
         
        Invalidate();
    }

    protected void onSizeChanged(EventArgs e) throws Exception {
        super.OnSizeChanged(e);
        setScrollValue(vScroll.Value);
    }

    /**
    * Holds the int values of the indices of the selected rows.  To set selected indices, use SetSelected().
    */
    public int[] getSelectedIndices() throws Exception {
        int[] retVal = new int[selectedIndices.Count];
        selectedIndices.CopyTo(retVal);
        Array.Sort(retVal);
        return retVal;
    }

    //they must be in numerical order
    /**
    * Holds the x,y values of the selected cell if in OneCell mode.  -1,-1 represents no cell selected.
    */
    public Point getSelectedCell() throws Exception {
        return selectedCell;
    }

    /**
    * 
    */
    public OpenDental.UI.GridSelectionMode getSelectionMode() throws Exception {
        return selectionMode;
    }

    public void setSelectionMode(OpenDental.UI.GridSelectionMode value) throws Exception {
        //if((GridSelectionMode)value==SelectionMode.MultiSimple){
        //	MessageBox.Show("MultiSimple not supported.");
        //	return;
        //}
        if ((OpenDental.UI.GridSelectionMode)value == OpenDental.UI.GridSelectionMode.OneCell)
        {
            selectedCell = new Point(-1, -1);
            //?
            selectedIndices = new ArrayList();
        }
         
        selectionMode = value;
    }

    /**
    * 
    */
    public boolean getAllowSelection() throws Exception {
        return allowSelection;
    }

    public void setAllowSelection(boolean value) throws Exception {
        allowSelection = value;
    }

    /**
    * Uniquely identifies the grid for translation to another language.
    */
    public String getTranslationName() throws Exception {
        return translationName;
    }

    public void setTranslationName(String value) throws Exception {
        translationName = value;
    }

    /**
    * The background color that is used for selected rows.
    */
    public Color getSelectedRowColor() throws Exception {
        return selectedRowColor;
    }

    public void setSelectedRowColor(Color value) throws Exception {
        selectedRowColor = value;
    }

    /**
    * Text within each cell will wrap, making some rows taller.
    */
    public boolean getWrapText() throws Exception {
        return wrapText;
    }

    public void setWrapText(boolean value) throws Exception {
        wrapText = value;
    }

    /**
    * The starting column for notes on each row.  Notes are not part of the main row, but are displayed on subsequent lines.
    */
    //typeof(int),0)]
    public int getNoteSpanStart() throws Exception {
        return noteSpanStart;
    }

    public void setNoteSpanStart(int value) throws Exception {
        noteSpanStart = value;
    }

    /**
    * The starting column for notes on each row.  Notes are not part of the main row, but are displayed on subsequent lines.  If this remains 0, then notes will be entirey skipped for this grid.
    */
    //typeof(int),0)]
    public int getNoteSpanStop() throws Exception {
        return noteSpanStop;
    }

    public void setNoteSpanStop(int value) throws Exception {
        noteSpanStop = value;
    }

    /**
    * Used when drawing to PDF. We need the width of all columns summed.
    */
    public int getWidthAllColumns() throws Exception {
        int retVal = 0;
        for (int i = 0;i < columns.Count;i++)
        {
            retVal += columns.get___idx(i).getColWidth();
        }
        return retVal;
    }

    /**
    * Set true to allow user to click on column headers to sort rows, alternating between ascending and descending.
    */
    public boolean getAllowSortingByColumn() throws Exception {
        return allowSortingByColumn;
    }

    public void setAllowSortingByColumn(boolean value) throws Exception {
        allowSortingByColumn = value;
        if (!allowSortingByColumn)
        {
            sortedByColumnIdx = -1;
        }
         
    }

    /**
    * Only affects grids with editable columns. True allows carriage returns within cells. Falses causes carriage returns to go to the next editable cell.
    */
    public boolean getEditableAcceptsCR() throws Exception {
        return editableAcceptsCR;
    }

    public void setEditableAcceptsCR(boolean value) throws Exception {
        editableAcceptsCR = value;
    }

    /**
    * Computes the position of each column and the overall width.  Called from endUpdate and also from OnPaint.
    */
    private void computeColumns() throws Exception {
        if (!hScrollVisible)
        {
            //this used to be in the resize logic
            int minGridW = 0;
            for (int i = 0;i < columns.Count;i++)
            {
                //sum of columns widths except last one.
                if (i < columns.Count - 1)
                {
                    minGridW += columns.get___idx(i).getColWidth();
                }
                 
            }
            if (widthHidden > 0)
            {
                this.Width -= widthHidden;
                //just for a few lines
                widthHidden = 0;
            }
             
            int minimumWidth = minGridW + 2 + vScroll.Width + 5;
            if (this.Width < minimumWidth)
            {
                //Trying to make it too narrow.
                widthHidden = minimumWidth - Width;
                //Keep track of how much of the grid is being hidden.
                this.Width = minimumWidth;
            }
            else //make it get stuck at the last column.  User doesn't notice the part that's sticking over to the right.
            if (columns.Count > 0)
            {
                //resize the last column automatically
                columns[columns.Count - 1].ColWidth = Width - 2 - vScroll.Width - minGridW;
            }
              
        }
         
        ColPos = new int[columns.Count];
        for (int i = 0;i < ColPos.Length;i++)
        {
            if (i == 0)
                ColPos[i] = 0;
            else
                ColPos[i] = ColPos[i - 1] + columns.get___idx(i - 1).getColWidth(); 
        }
        if (columns.Count > 0)
        {
            GridW = ColPos[ColPos.Length - 1] + columns[columns.Count - 1].ColWidth;
        }
         
    }

    /**
    * Called from PrintPage() and EndUpdate().  After adding rows to the grid, this calculates the height of each row because some rows may have text wrap and will take up more than one row.  Also, rows with notes, must be made much larger, because notes start on the second line.  If column images are used, rows will be enlarged to make space for the images.
    */
    private void computeRows(Graphics g) throws Exception {
        //Travis - 06/04/2013: Sometimes ComputeRows() will incorrectly measure the number of rows when the font of the cell will display in bold.  This can cause text that would be on a new line to not display.
        //This is because all textual measurements use cellFont which is never bold.  A possible solution would be to add another font that is bold to the current using statement.
        //Then before any text is measured, check if the cell is bold in order to pass the appropriate font.
        //using(Graphics g=this.CreateGraphics()) {
        Font cellFont = new Font(FontFamily.GenericSansSerif, cellFontSize);
        try
        {
            {
                RowHeights = new int[rows.Count];
                NoteHeights = new int[rows.Count];
                //multiPageNoteHeights=new List<List<int>>();
                //multiPageNoteSection=new List<List<string>>();
                //for(int i=0;i<rows.Count;i++) {
                //List<int> intList=new List<int>();
                //multiPageNoteHeights.Add(intList);
                //List<string> stringList=new List<string>();
                //multiPageNoteSection.Add(stringList);
                //}
                RowLocs = new int[rows.Count];
                GridH = 0;
                int cellH = new int();
                int noteW = 0;
                if (getNoteSpanStop() > 0 && getNoteSpanStart() < columns.Count)
                {
                    for (int i = getNoteSpanStart();i <= getNoteSpanStop();i++)
                    {
                        noteW += columns.get___idx(i).getColWidth();
                    }
                }
                 
                int imageH = 0;
                HasEditableColumn = false;
                for (int i = 0;i < columns.Count;i++)
                {
                    if (columns.get___idx(i).getIsEditable())
                    {
                        HasEditableColumn = true;
                    }
                     
                    if (columns.get___idx(i).getImageList() != null)
                    {
                        if (columns.get___idx(i).getImageList().ImageSize.Height > imageH)
                        {
                            imageH = columns.get___idx(i).getImageList().ImageSize.Height + 1;
                        }
                         
                    }
                     
                }
                for (int i = 0;i < rows.Count;i++)
                {
                    RowHeights[i] = 0;
                    if (wrapText)
                    {
                        for (int j = 0;j < rows.get___idx(i).getCells().Count;j++)
                        {
                            //find the tallest col
                            if (HasEditableColumn)
                            {
                                //doesn't seem to calculate right when it ends in a "\r\n". It doesn't make room for the new line. Make it, by adding another one for calculations.
                                cellH = (int)((1.03) * (float)(g.MeasureString(rows.get___idx(i).getCells()[j].Text + "\r\n", cellFont, columns.get___idx(j).getColWidth()).Height)) + 4;
                                //because textbox will be bigger
                                if (cellH < EDITABLE_ROW_HEIGHT)
                                {
                                    cellH = EDITABLE_ROW_HEIGHT;
                                }
                                 
                            }
                            else
                            {
                                //only used for single line text
                                cellH = (int)g.MeasureString(rows.get___idx(i).getCells()[j].Text, cellFont, columns.get___idx(j).getColWidth()).Height + 1;
                            } 
                            //if(rows[i].Height==0) {//not set
                            //  cellH=(int)g.MeasureString(rows[i].Cells[j].Text,cellFont,columns[j].ColWidth).Height+1;
                            //}
                            //else {
                            //  cellH=rows[i].Height;
                            //}
                            if (cellH > RowHeights[i])
                            {
                                RowHeights[i] = cellH;
                            }
                             
                        }
                        //Cameron 10/23/2013: Rows used to look like thick lines when the row height was 1.  When the height is less than 4, the row is not visible enough to select or edit.
                        //We will use the height of the string "Any" to determine a better row height so the user can see that it is an empty row.
                        //If, for whatever reason, their font really does return a row height less than 4, the following code will return that value anyway thus this change should be harmless.
                        if (RowHeights[i] < 4)
                        {
                            RowHeights[i] = (int)g.MeasureString("Any", cellFont, 100).Height + 1;
                        }
                         
                    }
                    else
                    {
                        //text not wrapping
                        if (HasEditableColumn)
                        {
                            RowHeights[i] = EDITABLE_ROW_HEIGHT;
                        }
                        else
                        {
                            RowHeights[i] = (int)g.MeasureString("Any", cellFont, 100).Height + 1;
                        } 
                    } 
                    //if(rows[i].Height==0) {//not set
                    //	RowHeights[i]=(int)g.MeasureString("Any",cellFont,100).Height+1;
                    //}
                    //else {
                    //	RowHeights[i]=rows[i].Height;
                    //}
                    if (imageH > RowHeights[i])
                    {
                        RowHeights[i] = imageH;
                    }
                     
                    if (noteW > 0 && !StringSupport.equals(rows.get___idx(i).getNote(), ""))
                    {
                        NoteHeights[i] = (int)g.MeasureString(rows.get___idx(i).getNote(), cellFont, noteW).Height;
                    }
                     
                    if (i == 0)
                    {
                        RowLocs[i] = 0;
                    }
                    else
                    {
                        RowLocs[i] = RowLocs[i - 1] + RowHeights[i - 1] + NoteHeights[i - 1];
                    } 
                    GridH += RowHeights[i] + NoteHeights[i];
                }
            }
        }
        finally
        {
            if (cellFont != null)
                Disposable.mkDisposable(cellFont).dispose();
             
        }
    }

    //}
    /**
    * Returns row. -1 if no valid row.  Supply the y position in pixels.
    */
    public int pointToRow(int y) throws Exception {
        if (y < 1 + titleHeight + headerHeight)
        {
            return -1;
        }
         
        for (int i = 0;i < rows.Count;i++)
        {
            if (y > -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[i] + RowHeights[i] + NoteHeights[i])
            {
                continue;
            }
             
            return i;
        }
        return -1;
    }

    //clicked below this row.
    /**
    * Returns col.  Supply the x position in pixels. -1 if no valid column.
    */
    public int pointToCol(int x) throws Exception {
        int colRight = new int();
        for (int i = 0;i < columns.Count;i++)
        {
            //the right edge of each column
            colRight = 0;
            for (int c = 0;c < i + 1;c++)
            {
                colRight += columns.get___idx(c).getColWidth();
            }
            if (x > -hScroll.Value + colRight)
            {
                continue;
            }
             
            return i;
        }
        return -1;
    }

    //clicked to the right of this col
    /**
    * 
    */
    protected void onPaintBackground(PaintEventArgs pea) throws Exception {
    }

    //base.OnPaintBackground (pea);
    //don't paint background.  This reduces flickering.
    /**
    * Runs any time the control is invalidated.
    */
    protected void onPaint(System.Windows.Forms.PaintEventArgs e) throws Exception {
        if (IsUpdating)
        {
            return ;
        }
         
        if (Width < 1 || Height < 1)
        {
            return ;
        }
         
        computeColumns();
        //it's only here because I can't figure out how to do it when columns are added. It will be removed.
        Bitmap doubleBuffer = new Bitmap(Width, Height, e.Graphics);
        Graphics g = Graphics.FromImage(doubleBuffer);
        try
        {
            {
                g.SmoothingMode = SmoothingMode.HighQuality;
                //for the up/down triangles
                //g.TextRenderingHint=TextRenderingHint.AntiAlias;//for accurate string measurements. Didn't work
                //g.TextRenderingHint=TextRenderingHint.SingleBitPerPixelGridFit;
                //float pagescale=g.PageScale;
                drawBackG(g);
                drawRows(g);
                drawTitleAndHeaders(g);
                //this will draw on top of any grid stuff
                drawOutline(g);
                e.Graphics.DrawImageUnscaled(doubleBuffer, 0, 0);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        doubleBuffer.Dispose();
        doubleBuffer = null;
    }

    /**
    * Draws a solid gray background.
    */
    private void drawBackG(Graphics g) throws Exception {
        //if(vScroll.Enabled){//all backg white, since no gray will show
        //	g.FillRectangle(new SolidBrush(Color.White),
        //		0,titleHeight+headerHeight+1,
        //		Width,this.Height-titleHeight-headerHeight-1);
        //}
        //else{
        g.FillRectangle(new SolidBrush(Color.FromArgb(224, 223, 227)), 0, titleHeight + headerHeight, Width, Height - titleHeight - headerHeight);
    }

    //}
    /**
    * Draws the background, lines, and text for all rows that are visible.
    */
    private void drawRows(Graphics g) throws Exception {
        Font cellFont = new Font(FontFamily.GenericSansSerif, cellFontSize);
        if (CultureInfo.CurrentCulture.Name.EndsWith("IN"))
        {
            cellFont.Dispose();
            cellFont = new Font("Arial", cellFontSize);
        }
         
        try
        {
            for (int i = 0;i < rows.Count;i++)
            {
                if (-vScroll.Value + RowLocs[i] + RowHeights[i] + NoteHeights[i] < 0)
                {
                    continue;
                }
                 
                //lower edge of row above top of grid area
                if (-vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[i] > Height)
                {
                    return ;
                }
                 
                //row below lower edge of control
                drawRow(i,g,cellFont);
            }
        }
        finally
        {
            if (cellFont != null)
            {
                cellFont.Dispose();
            }
             
        }
    }

    /**
    * Draws background, lines, image, and text for a single row.
    */
    private void drawRow(int rowI, Graphics g, Font cellFont) throws Exception {
        RectangleF textRect = new RectangleF();
        StringFormat format = new StringFormat();
        Pen gridPen = new Pen(this.cGridLine);
        Pen lowerPen = new Pen(this.cGridLine);
        if (rowI == rows.Count - 1)
        {
            //last row
            lowerPen = new Pen(Color.FromArgb(120, 120, 120));
        }
        else
        {
            if (rows.get___idx(rowI).getColorLborder() != Color.Empty)
            {
                lowerPen = new Pen(rows.get___idx(rowI).getColorLborder());
            }
             
        } 
        SolidBrush textBrush = new SolidBrush();
        //selected row color
        if (selectedIndices.Contains(rowI))
        {
            g.FillRectangle(new SolidBrush(selectedRowColor), 1, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + 1, GridW, RowHeights[rowI] + NoteHeights[rowI] - 1);
        }
        else //colored row background
        if (rows.get___idx(rowI).getColorBackG() != Color.White)
        {
            g.FillRectangle(new SolidBrush(rows.get___idx(rowI).getColorBackG()), 1, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + 1, GridW, RowHeights[rowI] + NoteHeights[rowI] - 1);
        }
        else
        {
            //normal row color
            //need to draw over the gray background
            //this is a really simple width value that always works well
            g.FillRectangle(new SolidBrush(rows.get___idx(rowI).getColorBackG()), 1, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + 1, GridW, RowHeights[rowI] + NoteHeights[rowI] - 1);
        }  
        if (selectionMode == OpenDental.UI.GridSelectionMode.OneCell && selectedCell.X != -1 && selectedCell.Y != -1 && selectedCell.Y == rowI)
        {
            g.FillRectangle(new SolidBrush(selectedRowColor), -hScroll.Value + 1 + ColPos[selectedCell.X], -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + 1, columns[selectedCell.X].ColWidth, RowHeights[rowI] + NoteHeights[rowI] - 1);
        }
         
        //lines for note section
        if (NoteHeights[rowI] > 0)
        {
            //left vertical gridline
            if (getNoteSpanStart() != 0)
            {
                g.DrawLine(gridPen, -hScroll.Value + 1 + ColPos[getNoteSpanStart()], -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI], -hScroll.Value + 1 + ColPos[getNoteSpanStart()], -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI] + NoteHeights[rowI]);
            }
             
            //Horizontal line which divides the main part of the row from the notes section of the row
            g.DrawLine(gridPen, -hScroll.Value + 1 + ColPos[0] + 1, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI], -hScroll.Value + 1 + ColPos[columns.Count - 1] + columns[columns.Count - 1].ColWidth, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI]);
        }
         
        for (int i = 0;i < columns.Count;i++)
        {
            //right vertical gridline
            if (rowI == 0)
            {
                g.DrawLine(gridPen, -hScroll.Value + 1 + ColPos[i] + columns.get___idx(i).getColWidth(), -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI], -hScroll.Value + 1 + ColPos[i] + columns.get___idx(i).getColWidth(), -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI]);
            }
            else
            {
                g.DrawLine(gridPen, -hScroll.Value + 1 + ColPos[i] + columns.get___idx(i).getColWidth(), -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + 1, -hScroll.Value + 1 + ColPos[i] + columns.get___idx(i).getColWidth(), -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI]);
            } 
            //lower horizontal gridline
            if (i == 0)
            {
                g.DrawLine(lowerPen, -hScroll.Value + 1 + ColPos[i], -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI] + NoteHeights[rowI], -hScroll.Value + 1 + ColPos[i] + columns.get___idx(i).getColWidth(), -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI] + NoteHeights[rowI]);
            }
            else
            {
                g.DrawLine(lowerPen, -hScroll.Value + 1 + ColPos[i] + 1, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI] + NoteHeights[rowI], -hScroll.Value + 1 + ColPos[i] + columns.get___idx(i).getColWidth(), -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI] + NoteHeights[rowI]);
            } 
            //text
            if (rows.get___idx(rowI).getCells().Count - 1 < i)
            {
                continue;
            }
             
            HorizontalAlignment __dummyScrutVar0 = columns.get___idx(i).getTextAlign();
            if (__dummyScrutVar0.equals(HorizontalAlignment.Left))
            {
                format.Alignment = StringAlignment.Near;
            }
            else if (__dummyScrutVar0.equals(HorizontalAlignment.Center))
            {
                format.Alignment = StringAlignment.Center;
            }
            else if (__dummyScrutVar0.equals(HorizontalAlignment.Right))
            {
                format.Alignment = StringAlignment.Far;
            }
               
            int vertical = -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + 1;
            int horizontal = -hScroll.Value + 1 + ColPos[i] + 1;
            int cellW = columns.get___idx(i).getColWidth();
            int cellH = RowHeights[rowI];
            if (HasEditableColumn)
            {
                //These cells are taller
                vertical += 2;
                //so this is to push text down to center it in the cell
                cellH -= 3;
            }
             
            //to keep it from spilling into the next cell
            if (columns.get___idx(i).getTextAlign() == HorizontalAlignment.Right)
            {
                if (HasEditableColumn)
                {
                    horizontal -= 4;
                    cellW += 2;
                }
                else
                {
                    horizontal -= 2;
                    cellW += 2;
                } 
            }
             
            textRect = new RectangleF(horizontal, vertical, cellW, cellH);
            if (rows.get___idx(rowI).getCells()[i].ColorText == Color.Empty)
            {
                textBrush = new SolidBrush(rows.get___idx(rowI).getColorText());
            }
            else
            {
                textBrush = new SolidBrush(rows.get___idx(rowI).getCells()[i].ColorText);
            } 
            if (rows.get___idx(rowI).getCells()[i].Bold == YN.Yes)
            {
                cellFont = new Font(cellFont, FontStyle.Bold);
            }
            else if (rows.get___idx(rowI).getCells()[i].Bold == YN.No)
            {
                cellFont = new Font(cellFont, FontStyle.Regular);
            }
            else
            {
                //unknown.  Use row bold
                if (rows.get___idx(rowI).getBold())
                {
                    cellFont = new Font(cellFont, FontStyle.Bold);
                }
                else
                {
                    cellFont = new Font(cellFont, FontStyle.Regular);
                } 
            }  
            if (columns.get___idx(i).getImageList() == null)
            {
                g.DrawString(rows.get___idx(rowI).getCells()[i].Text, cellFont, textBrush, textRect, format);
            }
            else
            {
                int imageIndex = -1;
                if (!StringSupport.equals(rows.get___idx(rowI).getCells()[i].Text, ""))
                {
                    imageIndex = PIn.Int(rows.get___idx(rowI).getCells()[i].Text);
                }
                 
                if (imageIndex != -1)
                {
                    Image img = columns.get___idx(i).getImageList().Images[imageIndex];
                    g.DrawImage(img, horizontal, vertical - 1);
                }
                 
            } 
        }
        //note text
        if (NoteHeights[rowI] > 0 && getNoteSpanStop() > 0 && getNoteSpanStart() < columns.Count)
        {
            int noteW = 0;
            for (int i = getNoteSpanStart();i <= getNoteSpanStop();i++)
            {
                noteW += columns.get___idx(i).getColWidth();
            }
            if (rows.get___idx(rowI).getBold())
            {
                cellFont = new Font(cellFont, FontStyle.Bold);
            }
            else
            {
                cellFont = new Font(cellFont, FontStyle.Regular);
            } 
            textBrush = new SolidBrush(rows.get___idx(rowI).getColorText());
            textRect = new RectangleF(-hScroll.Value + 1 + ColPos[getNoteSpanStart()] + 1, -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[rowI] + RowHeights[rowI] + 1, ColPos[getNoteSpanStop()] + columns.get___idx(getNoteSpanStop()).getColWidth() - ColPos[getNoteSpanStart()], NoteHeights[rowI]);
            format.Alignment = StringAlignment.Near;
            g.DrawString(rows.get___idx(rowI).getNote(), cellFont, textBrush, textRect, format);
        }
         
    }

    private void drawTitleAndHeaders(Graphics g) throws Exception {
        //Title----------------------------------------------------------------------------------------------------
        //Color.FromArgb(172,171,196)
        g.FillRectangle(new LinearGradientBrush(new Rectangle(0, 0, Width, titleHeight + 5), Color.White, Color.FromArgb(200, 200, 215), LinearGradientMode.Vertical), 0, 0, Width, titleHeight);
        Font titleFont = new Font(FontFamily.GenericSansSerif, 10, FontStyle.Bold);
        try
        {
            {
                g.DrawString(title, titleFont, Brushes.Black, Width / 2 - g.MeasureString(title, titleFont).Width / 2, 2);
            }
        }
        finally
        {
            if (titleFont != null)
                Disposable.mkDisposable(titleFont).dispose();
             
        }
        //Column Headers-----------------------------------------------------------------------------------------
        g.FillRectangle(new SolidBrush(this.cTitleBackG), 0, titleHeight, Width, headerHeight);
        //background
        g.DrawLine(new Pen(Color.FromArgb(102, 102, 122)), 0, titleHeight, Width, titleHeight);
        //line between title and headers
        Font headerFont = new Font(FontFamily.GenericSansSerif, 8.5f, FontStyle.Bold);
        try
        {
            {
                for (int i = 0;i < columns.Count;i++)
                {
                    if (i != 0)
                    {
                        //vertical lines separating column headers
                        g.DrawLine(new Pen(Color.FromArgb(120, 120, 120)), -hScroll.Value + 1 + ColPos[i], titleHeight + 3, -hScroll.Value + 1 + ColPos[i], titleHeight + headerHeight - 2);
                        g.DrawLine(new Pen(Color.White), -hScroll.Value + 1 + ColPos[i] + 1, titleHeight + 3, -hScroll.Value + 1 + ColPos[i] + 1, titleHeight + headerHeight - 2);
                    }
                     
                    g.DrawString(columns.get___idx(i).getHeading(), headerFont, Brushes.Black, -hScroll.Value + ColPos[i] + columns.get___idx(i).getColWidth() / 2 - g.MeasureString(columns.get___idx(i).getHeading(), headerFont).Width / 2, titleHeight + 2);
                    if (sortedByColumnIdx == i)
                    {
                        PointF p = new PointF(-hScroll.Value + 1 + ColPos[i] + 6, titleHeight + (float)headerHeight / 2f);
                        if (sortedIsAscending)
                        {
                            //pointing up
                            //LLstub
                            //LLbase
                            //LRbase
                            //LRstub
                            g.FillPolygon(Brushes.White, new PointF[]{ new PointF(p.X - 4.9f, p.Y + 2f), new PointF(p.X - 4.9f, p.Y + 2.5f), new PointF(p.X + 4.9f, p.Y + 2.5f), new PointF(p.X + 4.9f, p.Y + 2f), new PointF(p.X, p.Y - 2.8f) });
                            //Top
                            //LL
                            //LR
                            g.FillPolygon(Brushes.Black, new PointF[]{ new PointF(p.X - 4, p.Y + 2), new PointF(p.X + 4, p.Y + 2), new PointF(p.X, p.Y - 2) });
                        }
                        else
                        {
                            //Top
                            //pointing down
                            //shaped like home plate
                            //ULstub
                            //ULtop
                            //URtop
                            //URstub
                            g.FillPolygon(Brushes.White, new PointF[]{ new PointF(p.X - 4.9f, p.Y - 2f), new PointF(p.X - 4.9f, p.Y - 2.7f), new PointF(p.X + 4.9f, p.Y - 2.7f), new PointF(p.X + 4.9f, p.Y - 2f), new PointF(p.X, p.Y + 2.8f) });
                            //Bottom
                            //UL
                            //UR
                            g.FillPolygon(Brushes.Black, new PointF[]{ new PointF(p.X - 4, p.Y - 2), new PointF(p.X + 4, p.Y - 2), new PointF(p.X, p.Y + 2) });
                        } 
                    }
                     
                }
            }
        }
        finally
        {
            if (headerFont != null)
                Disposable.mkDisposable(headerFont).dispose();
             
        }
        //Bottom
        //line below headers
        g.DrawLine(new Pen(Color.FromArgb(120, 120, 120)), 0, titleHeight + headerHeight, Width, titleHeight + headerHeight);
    }

    /**
    * Draws outline around entire control.
    */
    private void drawOutline(Graphics g) throws Exception {
        if (hScroll.Visible)
        {
            //for the little square at the lower right between the two scrollbars
            g.FillRectangle(new SolidBrush(Color.FromKnownColor(KnownColor.Control)), Width - vScroll.Width - 1, Height - hScroll.Height - 1, vScroll.Width, hScroll.Height);
        }
         
        g.DrawRectangle(new Pen(this.cBlueOutline), 0, 0, Width - 1, Height - 1);
    }

    /**
    * 
    */
    protected void onCellDoubleClick(int col, int row) throws Exception {
        OpenDental.UI.ODGridClickEventArgs gArgs = new OpenDental.UI.ODGridClickEventArgs(col, row, MouseButtons.Left);
        if (CellDoubleClick != null)
        {
            CellDoubleClick.invoke(this,gArgs);
        }
         
    }

    /**
    * 
    */
    protected void onDoubleClick(EventArgs e) throws Exception {
        super.OnDoubleClick(e);
        if (MouseDownRow == -1)
        {
            return ;
        }
         
        //double click was in the title or header section
        if (MouseDownCol == -1)
        {
            return ;
        }
         
        //click was to the right of the columns
        onCellDoubleClick(MouseDownCol,MouseDownRow);
    }

    /**
    * 
    */
    protected void onCellClick(int col, int row, MouseButtons button) throws Exception {
        OpenDental.UI.ODGridClickEventArgs gArgs = new OpenDental.UI.ODGridClickEventArgs(col,row,button);
        if (CellClick != null)
        {
            CellClick.invoke(this,gArgs);
        }
         
    }

    /**
    * 
    */
    protected void onClick(EventArgs e) throws Exception {
        super.OnClick(e);
        if (MouseDownRow == -1)
        {
            return ;
        }
         
        //click was in the title or header section
        if (MouseDownCol == -1)
        {
            return ;
        }
         
        //click was to the right of the columns
        onCellClick(MouseDownCol,MouseDownRow,lastButtonPressed);
    }

    /**
    * Call this before adding any rows.  You would typically call Rows.Clear after this.
    */
    public void beginUpdate() throws Exception {
        IsUpdating = true;
    }

    /**
    * Must be called after adding rows.  This computes the columns, computes the rows, lays out the scrollbars, clears SelectedIndices, and invalidates.  Does not zero out scrollVal.  Sometimes, it seems like scrollVal needs to be reset somehow because it's an inappropriate number, and when you first grab the scrollbar, it jumps.  No time to investigate.
    */
    public void endUpdate() throws Exception {
        computeColumns();
        Graphics g = this.CreateGraphics();
        try
        {
            {
                computeRows(g);
            }
        }
        finally
        {
            if (g != null)
                Disposable.mkDisposable(g).dispose();
             
        }
        layoutScrollBars();
        //ScrollValue=0;
        selectedIndices = new ArrayList();
        selectedCell = new Point(-1, -1);
        if (editBox != null)
        {
            editBox.Dispose();
        }
         
        sortedByColumnIdx = -1;
        IsUpdating = false;
        Invalidate();
    }

    /**
    * If there are more pages to print, it returns -1.  If this is the last page, it returns the yPos of where the printing stopped.  Graphics will be paper, pageNumber resets some class level variables at page 0, bounds are used to contain the grid drawing, and marginTopFirstPage leaves room so as to not overwrite the title and subtitle.
    */
    public int printPage(Graphics g, int pageNumber, Rectangle bounds, int marginTopFirstPage) throws Exception {
        //Printers ignore TextRenderingHint.AntiAlias.
        //And they ignore SmoothingMode.HighQuality.
        //They seem to do font themselves instead of letting us have control.
        //g.TextRenderingHint=TextRenderingHint.AntiAlias;//an attempt to fix the printing measurements.
        //g.SmoothingMode=SmoothingMode.HighQuality;
        //g.PageUnit=GraphicsUnit.Display;
        //float pagescale=g.PageScale;
        //g.PixelOffsetMode=PixelOffsetMode.HighQuality;
        //g.
        if (RowsPrinted == 0)
        {
            //set row heights 4% larger when printing:
            computeRows(g);
        }
         
        int xPos = bounds.Left;
        //now, try to center in bounds
        if ((float)GridW < bounds.Width)
        {
            xPos = (int)(bounds.Left + bounds.Width / 2 - (float)GridW / 2);
        }
         
        StringFormat format = new StringFormat();
        Pen gridPen = new Pen();
        Pen lowerPen = new Pen();
        SolidBrush textBrush = new SolidBrush();
        RectangleF textRect = new RectangleF();
        Font cellFont = new Font(FontFamily.GenericSansSerif, cellFontSize);
        //Initialize our pens for drawing.
        gridPen = new Pen(this.cGridLine);
        lowerPen = new Pen(this.cGridLine);
        int yPos = bounds.Top;
        if (pageNumber == 0)
        {
            yPos = marginTopFirstPage;
            //Margin is lower because title and subtitle are printed externally.
            RowsPrinted = 0;
            NoteRemaining = "";
        }
         
        boolean isFirstRowOnPage = true;
        //helps with handling a very tall first row
        if (RowsPrinted == rows.Count - 1)
        {
            //last row
            lowerPen = new Pen(Color.FromArgb(120, 120, 120));
        }
        else
        {
            if (rows.get___idx(RowsPrinted).getColorLborder() != Color.Empty)
            {
                lowerPen = new Pen(rows.get___idx(RowsPrinted).getColorLborder());
            }
             
        } 
        try
        {
            //Print column headers on every page.
            g.FillRectangle(Brushes.LightGray, xPos + ColPos[0], yPos, (float)GridW, headerHeight);
            g.DrawRectangle(Pens.Black, xPos + ColPos[0], yPos, (float)GridW, headerHeight);
            for (int i = 1;i < ColPos.Length;i++)
            {
                g.DrawLine(Pens.Black, xPos + (float)ColPos[i], yPos, xPos + (float)ColPos[i], yPos + headerHeight);
            }
            Font headerFont = new Font(FontFamily.GenericSansSerif, 8.5f, FontStyle.Bold);
            try
            {
                {
                    for (int i = 0;i < columns.Count;i++)
                    {
                        g.DrawString(columns.get___idx(i).getHeading(), headerFont, Brushes.Black, xPos + (float)ColPos[i] + columns.get___idx(i).getColWidth() / 2 - g.MeasureString(columns.get___idx(i).getHeading(), headerFont).Width / 2, yPos);
                    }
                }
            }
            finally
            {
                if (headerFont != null)
                    Disposable.mkDisposable(headerFont).dispose();
                 
            }
            yPos += headerHeight;
            while (RowsPrinted < rows.Count)
            {
                if (StringSupport.equals(NoteRemaining, ""))
                {
                    //We are not in the middle of a note from a previous page. If we are in the middle of a note that will get printed next, as it is the next region of code (RowNotePart).
                    //Go to next page if it doesn't fit.
                    if (yPos + (float)RowHeights[RowsPrinted] > bounds.Bottom)
                    {
                        //The row is too tall to fit
                        if (isFirstRowOnPage)
                        {
                        }
                        else
                        {
                            break;
                        } 
                    }
                     
                    //todo some day: handle very tall first rows.  For now, print what we can.
                    //Go to next page.
                    //There is enough room to print this row.
                    //Draw the left vertical gridline
                    g.DrawLine(gridPen, xPos + ColPos[0], yPos, xPos + ColPos[0], yPos + (float)RowHeights[RowsPrinted]);
                    for (int i = 0;i < columns.Count;i++)
                    {
                        //Draw the other vertical gridlines
                        g.DrawLine(gridPen, xPos + (float)ColPos[i] + (float)columns.get___idx(i).getColWidth(), yPos, xPos + (float)ColPos[i] + (float)columns.get___idx(i).getColWidth(), yPos + (float)RowHeights[RowsPrinted]);
                        if (StringSupport.equals(rows.get___idx(RowsPrinted).getNote(), ""))
                        {
                            //End of row. Mark with a dark line (lowerPen).
                            //Horizontal line which divides the main part of the row from the notes section of the row one column at a time.
                            g.DrawLine(lowerPen, xPos + ColPos[0], yPos + (float)RowHeights[RowsPrinted], xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos + (float)RowHeights[RowsPrinted]);
                        }
                        else
                        {
                            //Middle of row. Still need to print the note part of the row. Mark with a medium line (gridPen).
                            //Horizontal line which divides the main part of the row from the notes section of the row one column at a time.
                            g.DrawLine(gridPen, xPos + ColPos[0], yPos + (float)RowHeights[RowsPrinted], xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos + (float)RowHeights[RowsPrinted]);
                        } 
                        //text
                        if (rows.get___idx(RowsPrinted).getCells().Count - 1 < i)
                        {
                            continue;
                        }
                         
                        HorizontalAlignment __dummyScrutVar1 = columns.get___idx(i).getTextAlign();
                        if (__dummyScrutVar1.equals(HorizontalAlignment.Left))
                        {
                            format.Alignment = StringAlignment.Near;
                        }
                        else if (__dummyScrutVar1.equals(HorizontalAlignment.Center))
                        {
                            format.Alignment = StringAlignment.Center;
                        }
                        else if (__dummyScrutVar1.equals(HorizontalAlignment.Right))
                        {
                            format.Alignment = StringAlignment.Far;
                        }
                           
                        if (rows.get___idx(RowsPrinted).getCells()[i].ColorText == Color.Empty)
                        {
                            textBrush = new SolidBrush(rows.get___idx(RowsPrinted).getColorText());
                        }
                        else
                        {
                            textBrush = new SolidBrush(rows.get___idx(RowsPrinted).getCells()[i].ColorText);
                        } 
                        if (rows.get___idx(RowsPrinted).getCells()[i].Bold == YN.Yes)
                        {
                            cellFont = new Font(cellFont, FontStyle.Bold);
                        }
                        else if (rows.get___idx(RowsPrinted).getCells()[i].Bold == YN.No)
                        {
                            cellFont = new Font(cellFont, FontStyle.Regular);
                        }
                        else
                        {
                            //unknown.  Use row bold
                            if (rows.get___idx(RowsPrinted).getBold())
                            {
                                cellFont = new Font(cellFont, FontStyle.Bold);
                            }
                            else
                            {
                                cellFont = new Font(cellFont, FontStyle.Regular);
                            } 
                        }  
                        //Some printers will malfunction (BSOD) if print bold colored fonts.  This prevents the error.
                        if (textBrush.Color != Color.Black && cellFont.Bold)
                        {
                            cellFont = new Font(cellFont, FontStyle.Regular);
                        }
                         
                        if (columns.get___idx(i).getTextAlign() == HorizontalAlignment.Right)
                        {
                            textRect = new RectangleF(xPos + (float)ColPos[i] - 2, yPos, (float)columns.get___idx(i).getColWidth() + 2, (float)RowHeights[RowsPrinted]);
                            //shift the rect to account for MS issue with strings of different lengths
                            //js- 5/2/11,I don't understand this.  I would like to research it.
                            textRect.Location = new PointF(textRect.X + g.MeasureString(rows.get___idx(RowsPrinted).getCells()[i].Text, cellFont).Width / textRect.Width, textRect.Y);
                        }
                        else
                        {
                            //g.DrawString(rows[RowsPrinted].Cells[i].Text,cellFont,textBrush,textRect,format);
                            textRect = new RectangleF(xPos + (float)ColPos[i], yPos, (float)columns.get___idx(i).getColWidth(), (float)RowHeights[RowsPrinted]);
                        } 
                        //g.DrawString(rows[RowsPrinted].Cells[i].Text,cellFont,textBrush,textRect,format);
                        g.DrawString(rows.get___idx(RowsPrinted).getCells()[i].Text, cellFont, textBrush, textRect, format);
                    }
                    yPos += (int)((float)RowHeights[RowsPrinted]);
                }
                 
                //Move yPos down the length of the row (not the note).
                if (StringSupport.equals(rows.get___idx(RowsPrinted).getNote(), ""))
                {
                    RowsPrinted++;
                    //There is no note. Go to next row.
                    isFirstRowOnPage = false;
                    continue;
                }
                 
                //Figure out how much vertical distance the rest of the note will take up.
                int noteHeight = new int();
                int noteW = 0;
                format.Alignment = StringAlignment.Near;
                for (int i = getNoteSpanStart();i <= getNoteSpanStop();i++)
                {
                    noteW += (int)((float)columns.get___idx(i).getColWidth());
                }
                if (StringSupport.equals(NoteRemaining, ""))
                {
                    //We are not in the middle of a note.
                    NoteRemaining = rows.get___idx(RowsPrinted).getNote();
                }
                 
                //The note remaining is the whole note.
                noteHeight = (int)g.MeasureString(NoteRemaining, cellFont, noteW, format).Height;
                //This is how much height the rest of the note will take.
                boolean roomForRestOfNote = false;
                //Test to see if there's enough room on the page for the rest of the note.
                if (yPos + noteHeight < bounds.Bottom)
                {
                    roomForRestOfNote = true;
                }
                 
                if (roomForRestOfNote)
                {
                    //There is enough room
                    //print it
                    //draw lines for the rest of the note
                    if (noteHeight > 0)
                    {
                        //left vertical gridline
                        if (getNoteSpanStart() != 0)
                        {
                            g.DrawLine(gridPen, xPos + (float)ColPos[getNoteSpanStart()], yPos, xPos + (float)ColPos[getNoteSpanStart()], yPos + noteHeight);
                        }
                         
                        //right vertical gridline
                        g.DrawLine(gridPen, xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos, xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos + noteHeight);
                        //left vertical gridline
                        g.DrawLine(gridPen, xPos + ColPos[0], yPos, xPos + ColPos[0], yPos + noteHeight);
                    }
                     
                    //lower horizontal gridline gets marked with the dark lowerPen since this is the end of a row
                    g.DrawLine(lowerPen, xPos + ColPos[0], yPos + noteHeight, xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos + noteHeight);
                    //note text
                    if (noteHeight > 0 && getNoteSpanStop() > 0 && getNoteSpanStart() < columns.Count)
                    {
                        if (rows.get___idx(RowsPrinted).getBold())
                        {
                            cellFont = new Font(cellFont, FontStyle.Bold);
                        }
                        else
                        {
                            cellFont = new Font(cellFont, FontStyle.Regular);
                        } 
                        textBrush = new SolidBrush(rows.get___idx(RowsPrinted).getColorText());
                        textRect = new RectangleF(xPos + (float)ColPos[getNoteSpanStart()] + 1, yPos, (float)ColPos[getNoteSpanStop()] + (float)columns.get___idx(getNoteSpanStop()).getColWidth() - (float)ColPos[getNoteSpanStart()], noteHeight);
                        g.DrawString(NoteRemaining, cellFont, textBrush, textRect, format);
                    }
                     
                    NoteRemaining = "";
                    RowsPrinted++;
                    isFirstRowOnPage = false;
                    yPos += noteHeight;
                }
                else
                {
                    //The rest of the note will not fit on this page.
                    //Print as much as you can.
                    noteHeight = bounds.Bottom - yPos;
                    //This is the amount of space remaining.
                    if (noteHeight < 15)
                    {
                        return -1;
                    }
                     
                    //If noteHeight is less than this we will get a negative value for the rectangle of space remaining because we subtract 15 from this for the rectangle size when using measureString. This is because one line takes 15, and if there is 1 pixel of height available, measureString will fill it with text, which will then get partly cut off. So when we use measureString we will subtract 15 from the noteHeight.
                    SizeF sizeF = new SizeF();
                    int charactersFitted = new int();
                    int linesFilled = new int();
                    String noteFitted = new String();
                    //This is the part of the note we will print.
                    //js- I'd like to incorporate ,StringFormat.GenericTypographic into the MeasureString, but can't find the overload.
                    RefSupport<int> refVar___0 = new RefSupport<int>();
                    RefSupport<int> refVar___1 = new RefSupport<int>();
                    sizeF = g.MeasureString(NoteRemaining, cellFont, new SizeF(noteW, noteHeight - 15), format, refVar___0, refVar___1);
                    charactersFitted = refVar___0.getValue();
                    linesFilled = refVar___1.getValue();
                    //Text that fits will be NoteRemaining.Substring(0,charactersFitted).
                    noteFitted = NoteRemaining.Substring(0, charactersFitted);
                    //draw lines for the part of the note that fits on this page
                    if (noteHeight > 0)
                    {
                        //left vertical gridline
                        if (getNoteSpanStart() != 0)
                        {
                            g.DrawLine(gridPen, xPos + (float)ColPos[getNoteSpanStart()], yPos, xPos + (float)ColPos[getNoteSpanStart()], yPos + noteHeight);
                        }
                         
                        //right vertical gridline
                        g.DrawLine(gridPen, xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos, xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos + noteHeight);
                        //left vertical gridline
                        g.DrawLine(gridPen, xPos + ColPos[0], yPos, xPos + ColPos[0], yPos + noteHeight);
                    }
                     
                    //lower horizontal gridline gets marked with gridPen since its still the middle of a row (still more note to print)
                    g.DrawLine(gridPen, xPos + ColPos[0], yPos + noteHeight, xPos + (float)ColPos[columns.Count - 1] + (float)columns[columns.Count - 1].ColWidth, yPos + noteHeight);
                    //note text
                    if (noteHeight > 0 && getNoteSpanStop() > 0 && getNoteSpanStart() < columns.Count)
                    {
                        if (rows.get___idx(RowsPrinted).getBold())
                        {
                            cellFont = new Font(cellFont, FontStyle.Bold);
                        }
                        else
                        {
                            cellFont = new Font(cellFont, FontStyle.Regular);
                        } 
                        textBrush = new SolidBrush(rows.get___idx(RowsPrinted).getColorText());
                        textRect = new RectangleF(xPos + (float)ColPos[getNoteSpanStart()] + 1, yPos, (float)ColPos[getNoteSpanStop()] + (float)columns.get___idx(getNoteSpanStop()).getColWidth() - (float)ColPos[getNoteSpanStart()], noteHeight);
                        g.DrawString(noteFitted, cellFont, textBrush, textRect, format);
                    }
                     
                    NoteRemaining = NoteRemaining.Substring(charactersFitted);
                    break;
                } 
            }
        }
        finally
        {
            if (cellFont != null)
            {
                cellFont.Dispose();
            }
             
        }
        if (RowsPrinted == rows.Count)
        {
            //done printing
            //set row heights back to screen heights.
            Graphics gfx = this.CreateGraphics();
            try
            {
                {
                    computeRows(gfx);
                }
            }
            finally
            {
                if (gfx != null)
                    Disposable.mkDisposable(gfx).dispose();
                 
            }
            return yPos;
        }
        else
        {
            return -1;
        } 
    }

    //more pages to print
    /**
    * Use to set a row selected or not.  Can handle values outside the acceptable range.
    */
    public void setSelected(int index, boolean setValue) throws Exception {
        if (setValue)
        {
            //select specified index
            if (selectionMode == OpenDental.UI.GridSelectionMode.None)
            {
                throw new Exception("Selection mode is none.");
            }
             
            if (index < 0 || index > rows.Count - 1)
            {
                return ;
            }
             
            //check to see if index is within the valid range of values
            //if not, then ignore.
            if (selectionMode == OpenDental.UI.GridSelectionMode.One)
            {
                selectedIndices.Clear();
            }
             
            //clear existing selection before assigning the new one.
            if (!selectedIndices.Contains(index))
            {
                selectedIndices.Add(index);
            }
             
        }
        else
        {
            //unselect specified index
            if (selectedIndices.Contains(index))
            {
                selectedIndices.Remove(index);
            }
             
        } 
        Invalidate();
    }

    /**
    * Allows setting multiple values all at once
    */
    public void setSelected(int[] iArray, boolean setValue) throws Exception {
        if (selectionMode == OpenDental.UI.GridSelectionMode.None)
        {
            throw new Exception("Selection mode is none.");
        }
         
        if (selectionMode == OpenDental.UI.GridSelectionMode.One)
        {
            throw new Exception("Selection mode is one.");
        }
         
        for (int i = 0;i < iArray.Length;i++)
        {
            if (setValue)
            {
                //select specified index
                if (iArray[i] < 0 || iArray[i] > rows.Count - 1)
                {
                    return ;
                }
                 
                //check to see if index is within the valid range of values
                //if not, then ignore.
                if (!selectedIndices.Contains(iArray[i]))
                {
                    selectedIndices.Add(iArray[i]);
                }
                 
            }
            else
            {
                //unselect specified index
                if (selectedIndices.Contains(iArray[i]))
                {
                    selectedIndices.Remove(iArray[i]);
                }
                 
            } 
        }
        Invalidate();
    }

    /**
    * Sets all rows to specified value.
    */
    public void setSelected(boolean setValue) throws Exception {
        if (selectionMode == OpenDental.UI.GridSelectionMode.None)
        {
            throw new Exception("Selection mode is none.");
        }
         
        if (selectionMode == OpenDental.UI.GridSelectionMode.One && setValue == true)
        {
            throw new Exception("Selection mode is one.");
        }
         
        if (selectionMode == OpenDental.UI.GridSelectionMode.OneCell)
        {
            throw new Exception("Selection mode is OneCell.");
        }
         
        selectedIndices.Clear();
        if (setValue)
        {
            for (int i = 0;i < rows.Count;i++)
            {
                //select all
                selectedIndices.Add(i);
            }
        }
         
        Invalidate();
    }

    /**
    * 
    */
    public void setSelected(Point setCell) throws Exception {
        if (selectionMode != OpenDental.UI.GridSelectionMode.OneCell)
        {
            throw new Exception("Selection mode must be OneCell.");
        }
         
        selectedCell = setCell;
        if (editBox != null)
        {
            editBox.Dispose();
        }
         
        if (getColumns()[selectedCell.X].IsEditable)
        {
            createEditBox();
        }
         
        Invalidate();
    }

    /**
    * If one row is selected, it returns the index to that row.  If more than one row are selected, it returns the first selected row.  Really only useful for SelectionMode.One.  If no rows selected, returns -1.
    */
    public int getSelectedIndex() throws Exception {
        if (selectedIndices.Count > 0)
        {
            return (int)selectedIndices[0];
        }
         
        return -1;
    }

    private void layoutScrollBars() throws Exception {
        vScroll.Location = new Point(this.Width - vScroll.Width - 1, titleHeight + headerHeight + 1);
        if (this.hScrollVisible)
        {
            hScroll.Visible = true;
            vScroll.Height = this.Height - titleHeight - headerHeight - hScroll.Height - 2;
            hScroll.Location = new Point(1, this.Height - hScroll.Height - 1);
            hScroll.Width = this.Width - vScroll.Width - 2;
            if (GridW < hScroll.Width)
            {
                hScroll.Value = 0;
                hScroll.Enabled = false;
            }
            else
            {
                hScroll.Enabled = true;
                hScroll.Minimum = 0;
                hScroll.Maximum = GridW;
                hScroll.LargeChange = (hScroll.Width < 0 ? 0 : hScroll.Width);
                //Don't allow negative width (will throw exception).
                hScroll.SmallChange = (int)(50);
            } 
        }
        else
        {
            hScroll.Visible = false;
            vScroll.Height = this.Height - titleHeight - headerHeight - 2;
        } 
        if (vScroll.Height <= 0)
        {
            return ;
        }
         
        //hScroll support incomplete
        if (GridH < vScroll.Height)
        {
            vScroll.Value = 0;
            vScroll.Enabled = false;
        }
        else
        {
            vScroll.Enabled = true;
            vScroll.Minimum = 0;
            vScroll.Maximum = GridH;
            vScroll.LargeChange = vScroll.Height;
            //it used to crash right here as it tried to assign a negative number.
            vScroll.SmallChange = (int)(14 * 3.4);
        } 
    }

    //it's not an even number so that it is obvious to user that rows moved
    //vScroll.Value=0;
    private void vScroll_Scroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        if (editBox != null)
        {
            editBox.Dispose();
        }
         
        Invalidate();
        this.Focus();
    }

    private void hScroll_Scroll(Object sender, System.Windows.Forms.ScrollEventArgs e) throws Exception {
        //if(UpDownKey) return;
        Invalidate();
        this.Focus();
    }

    /**
    * Usually called after entering a new list to automatically scroll to the end.
    */
    public void scrollToEnd() throws Exception {
        setScrollValue(vScroll.Maximum);
    }

    //this does all error checking and invalidates
    /**
    * Set sortedByColIdx to -1 to clear sorting. Copied from SortByColumn. No need to call fill grid after calling this.
    */
    public void sortForced(int sortedByColIdx, boolean isAsc) throws Exception {
        sortedIsAscending = isAsc;
        sortedByColumnIdx = sortedByColIdx;
        if (sortedByColIdx == -1)
        {
            return ;
        }
         
        List<OpenDental.UI.ODGridRow> rowsSorted = new List<OpenDental.UI.ODGridRow>();
        for (int i = 0;i < rows.Count;i++)
        {
            rowsSorted.Add(rows.get___idx(i));
        }
        if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.StringCompare)
        {
            rowsSorted.Sort(SortStringCompare);
        }
        else if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.DateParse)
        {
            rowsSorted.Sort(SortDateParse);
        }
        else if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.ToothNumberParse)
        {
            rowsSorted.Sort(SortToothNumberParse);
        }
        else if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.AmountParse)
        {
            rowsSorted.Sort(SortAmountParse);
        }
            
        beginUpdate();
        rows.Clear();
        for (int i = 0;i < rowsSorted.Count;i++)
        {
            rows.Add(rowsSorted[i]);
        }
        endUpdate();
        sortedByColumnIdx = sortedByColIdx;
    }

    //Must be set again since set to -1 in EndUpdate();
    /**
    * Gets run on mouse down on a column header.
    */
    private void sortByColumn(int mouseDownCol) throws Exception {
        if (mouseDownCol == -1)
        {
            return ;
        }
         
        if (sortedByColumnIdx == mouseDownCol)
        {
            //already sorting by this column
            sortedIsAscending = !sortedIsAscending;
        }
        else
        {
            //switch ascending/descending.
            sortedIsAscending = true;
            //start out ascending
            sortedByColumnIdx = mouseDownCol;
        } 
        List<OpenDental.UI.ODGridRow> rowsSorted = new List<OpenDental.UI.ODGridRow>();
        for (int i = 0;i < rows.Count;i++)
        {
            rowsSorted.Add(rows.get___idx(i));
        }
        if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.StringCompare)
        {
            rowsSorted.Sort(SortStringCompare);
        }
        else if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.DateParse)
        {
            rowsSorted.Sort(SortDateParse);
        }
        else if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.ToothNumberParse)
        {
            rowsSorted.Sort(SortToothNumberParse);
        }
        else if (columns.get___idx(sortedByColumnIdx).getSortingStrategy() == GridSortingStrategy.AmountParse)
        {
            rowsSorted.Sort(SortAmountParse);
        }
            
        beginUpdate();
        rows.Clear();
        for (int i = 0;i < rowsSorted.Count;i++)
        {
            rows.Add(rowsSorted[i]);
        }
        endUpdate();
        sortedByColumnIdx = mouseDownCol;
    }

    //Must be set again since set to -1 in EndUpdate();
    private int sortStringCompare(OpenDental.UI.ODGridRow row1, OpenDental.UI.ODGridRow row2) throws Exception {
        return (sortedIsAscending ? 1 : -1) * row1.getCells()[sortedByColumnIdx].Text.CompareTo(row2.getCells()[sortedByColumnIdx].Text);
    }

    private int sortDateParse(OpenDental.UI.ODGridRow row1, OpenDental.UI.ODGridRow row2) throws Exception {
        String raw1 = row1.getCells()[sortedByColumnIdx].Text;
        String raw2 = row2.getCells()[sortedByColumnIdx].Text;
        DateTime date1 = DateTime.MinValue;
        DateTime date2 = DateTime.MinValue;
        if (!StringSupport.equals(raw1, ""))
        {
            try
            {
                date1 = DateTime.Parse(raw1);
            }
            catch (Exception __dummyCatchVar0)
            {
                return 0;
            }
        
        }
         
        //shouldn't happen
        if (!StringSupport.equals(raw2, ""))
        {
            try
            {
                date2 = DateTime.Parse(raw2);
            }
            catch (Exception __dummyCatchVar1)
            {
                return 0;
            }
        
        }
         
        return (sortedIsAscending ? 1 : -1) * date1.CompareTo(date2);
    }

    //shouldn't happen
    private int sortToothNumberParse(OpenDental.UI.ODGridRow row1, OpenDental.UI.ODGridRow row2) throws Exception {
        //remember that teeth could be in international format.
        //fail gracefully
        String raw1 = row1.getCells()[sortedByColumnIdx].Text;
        String raw2 = row2.getCells()[sortedByColumnIdx].Text;
        if (!Tooth.isValidEntry(raw1) && !Tooth.isValidEntry(raw2))
        {
            return 0;
        }
         
        //both invalid
        int retVal = 0;
        if (!Tooth.isValidEntry(raw1))
        {
            //only first invalid
            retVal = -1;
                ;
        }
        else if (!Tooth.isValidEntry(raw2))
        {
            //only second invalid
            retVal = 1;
                ;
        }
        else
        {
            //both valid
            String tooth1 = Tooth.fromInternat(raw1);
            String tooth2 = Tooth.fromInternat(raw2);
            int toothInt1 = Tooth.toInt(tooth1);
            int toothInt2 = Tooth.toInt(tooth2);
            retVal = toothInt1.CompareTo(toothInt2);
        }  
        return (sortedIsAscending ? 1 : -1) * retVal;
    }

    private int sortAmountParse(OpenDental.UI.ODGridRow row1, OpenDental.UI.ODGridRow row2) throws Exception {
        String raw1 = row1.getCells()[sortedByColumnIdx].Text;
        String raw2 = row2.getCells()[sortedByColumnIdx].Text;
        Decimal amt1 = 0;
        Decimal amt2 = 0;
        if (!StringSupport.equals(raw1, ""))
        {
            try
            {
                amt1 = Decimal.Parse(raw1);
            }
            catch (Exception __dummyCatchVar2)
            {
                return 0;
            }
        
        }
         
        //shouldn't happen
        if (!StringSupport.equals(raw2, ""))
        {
            try
            {
                amt2 = Decimal.Parse(raw2);
            }
            catch (Exception __dummyCatchVar3)
            {
                return 0;
            }
        
        }
         
        return (sortedIsAscending ? 1 : -1) * amt1.CompareTo(amt2);
    }

    //shouldn't happen
    /**
    * 
    */
    protected void onMouseDown(MouseEventArgs e) throws Exception {
        super.OnMouseDown(e);
        lastButtonPressed = e.Button;
        //used in the click event.
        if (e.Button == MouseButtons.Right)
        {
            if (selectedIndices.Count > 0)
            {
                return ;
            }
             
        }
         
        //if there are already rows selected, then ignore right click
        //otherwise, row will be selected. Useful when using context menu.
        MouseIsDown = true;
        MouseDownRow = PointToRow(e.Y);
        MouseDownCol = PointToCol(e.X);
        if (e.Y < 1 + titleHeight)
        {
            return ;
        }
         
        //mouse down was in the title section
        if (e.Y < 1 + titleHeight + headerHeight)
        {
            //mouse down was on a column header
            mouseIsDownInHeader = true;
            if (allowSortingByColumn)
            {
                if (MouseDownCol == -1)
                {
                    return ;
                }
                 
                sortByColumn(MouseDownCol);
                Invalidate();
                return ;
            }
            else
            {
                return ;
            } 
        }
         
        if (MouseDownRow == -1)
        {
            return ;
        }
         
        //mouse down was below the grid rows
        if (MouseDownCol == -1)
        {
            return ;
        }
         
        //mouse down was to the right of columns
        if (!allowSelection)
        {
            return ;
        }
         
        //clicks do not trigger selection of rows, but cell click event still gets fired
        switch(selectionMode)
        {
            case None: 
                return ;
            case One: 
                selectedIndices.Clear();
                selectedIndices.Add(MouseDownRow);
                break;
            case OneCell: 
                selectedIndices.Clear();
                //Point oldSelectedCell=selectedCell;
                //if(oldSelectedCell.X!=selectedCell.X || oldSelectedCell.Y!=selectedCell.Y){
                if (editBox != null)
                {
                    editBox.Dispose();
                }
                 
                //a lot happens right here, including a FillGrid() which sets selectedCell to -1,-1
                selectedCell = new Point(MouseDownCol, MouseDownRow);
                if (getColumns()[selectedCell.X].IsEditable)
                {
                    createEditBox();
                }
                 
                break;
            case MultiExtended: 
                //}
                if (ControlIsDown)
                {
                    //we need to remember exactly which rows were selected the moment the mouse down started.
                    //Then, if the mouse gets dragged up or down, the rows between mouse start and mouse end
                    //will be set to the opposite of these remembered values.
                    selectedIndicesWhenMouseDown = new ArrayList(selectedIndices);
                    if (selectedIndices.Contains(MouseDownRow))
                    {
                        selectedIndices.Remove(MouseDownRow);
                    }
                    else
                    {
                        selectedIndices.Add(MouseDownRow);
                    } 
                }
                else if (ShiftIsDown)
                {
                    if (selectedIndices.Count == 0)
                    {
                        selectedIndices.Add(MouseDownRow);
                    }
                    else
                    {
                        int fromRow = (int)selectedIndices[0];
                        selectedIndices.Clear();
                        if (MouseDownRow < fromRow)
                        {
                            for (int i = MouseDownRow;i <= fromRow;i++)
                            {
                                //dragging down
                                selectedIndices.Add(i);
                            }
                        }
                        else
                        {
                            for (int i = fromRow;i <= MouseDownRow;i++)
                            {
                                selectedIndices.Add(i);
                            }
                        } 
                    } 
                }
                else
                {
                    //ctrl or shift not down
                    selectedIndices.Clear();
                    selectedIndices.Add(MouseDownRow);
                }  
                break;
        
        }
        Invalidate();
    }

    /**
    * 
    */
    protected void onMouseUp(MouseEventArgs e) throws Exception {
        super.OnMouseUp(e);
        //if(e.Button==MouseButtons.Right){
        //	return;
        //}
        MouseIsDown = false;
        mouseIsDownInHeader = false;
    }

    /**
    * When selection mode is OneCell, and user clicks in a column that isEditable, then this edit box will appear.  Pass in the location from the click event so that we can determine where to place the text cursor in the box.
    */
    private void createEditBox() throws Exception {
        editBox = new TextBox();
        //The problem is that it ignores the height.
        editBox.Multiline = true;
        editBox.Font = new Font(FontFamily.GenericSansSerif, cellFontSize);
        editBox.Size = new Size(getColumns()[selectedCell.X].ColWidth + 1, RowHeights[selectedCell.Y] + 1);
        editBox.Location = new Point(-hScroll.Value + 1 + ColPos[selectedCell.X], -vScroll.Value + 1 + titleHeight + headerHeight + RowLocs[selectedCell.Y]);
        editBox.Text = getRows()[selectedCell.Y].Cells[selectedCell.X].Text;
        editBox.TextChanged += new EventHandler(editBox_TextChanged);
        editBox.LostFocus += new EventHandler(editBox_LostFocus);
        editBox.KeyDown += new KeyEventHandler(editBox_KeyDown);
        editBox.KeyUp += new KeyEventHandler(editBox_KeyUp);
        if (getColumns()[selectedCell.X].TextAlign == HorizontalAlignment.Right)
        {
            editBox.TextAlign = HorizontalAlignment.Right;
        }
         
        editBox.AcceptsTab = true;
        //Allow tab navigation in the ODGrid. Necessary when enter and up/down keys navigate within a cell (EditableAcceptsCR).
        if (editableAcceptsCR)
        {
            //Allow the edit box to handle carriage returns/multiline text.
            editBox.AcceptsReturn = true;
        }
         
        this.Controls.Add(editBox);
        if (!editableAcceptsCR)
        {
            editBox.SelectAll();
        }
         
        //Only select all when not multiline (editableAcceptsCR) i.e. proc list for editing fees selects all for easy overwriting.
        editBox.Focus();
        //Set the cell of the current editBox so that the value of that cell is saved when it looses focus (used for mouse click).
        oldSelectedCell = new Point(selectedCell.X, selectedCell.Y);
    }

    void editBox_LostFocus(Object sender, EventArgs e) throws Exception {
        //editBox_Leave wouldn't catch all scenarios
        OnCellLeave(oldSelectedCell.X, oldSelectedCell.Y);
        //this is the only place where OnCellLeave gets called.
        if (!editBox.Disposing || !editBox.IsDisposed)
        {
            editBox.Dispose();
            editBox = null;
        }
         
    }

    void editBox_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.Shift && e.KeyCode == Keys.Enter)
        {
            getRows()[selectedCell.Y].Cells[selectedCell.X].Text += "\r\n";
            return ;
        }
         
        if (e.KeyCode == Keys.Enter)
        {
            //usually move to the next cell
            if (editableAcceptsCR)
            {
                return ;
            }
             
            //When multiline it inserts a carriage return instead of moving to the next cell.
            editBox_NextCell();
        }
         
        if (e.KeyCode == Keys.Down)
        {
            if (editableAcceptsCR)
            {
                return ;
            }
             
            //When multiline it moves down inside the text instead of down to the next cell.
            if (selectedCell.Y < rows.Count - 1)
            {
                editBox.Dispose();
                editBox = null;
                //OnCellLeave(selectedCell.X,selectedCell.Y);
                selectedCell = new Point(selectedCell.X, selectedCell.Y + 1);
                createEditBox();
            }
             
        }
         
        if (e.KeyCode == Keys.Up)
        {
            if (editableAcceptsCR)
            {
                return ;
            }
             
            //When multiline it moves up inside the text instead of up to the next cell.
            if (selectedCell.Y > 0)
            {
                editBox.Dispose();
                editBox = null;
                //OnCellLeave(selectedCell.X,selectedCell.Y);
                selectedCell = new Point(selectedCell.X, selectedCell.Y - 1);
                createEditBox();
            }
             
        }
         
        if (e.KeyCode == Keys.Tab)
        {
            editBox_NextCell();
        }
         
    }

    private void editBox_NextCell() throws Exception {
        editBox.Dispose();
        //This fires editBox_LostFocus, which is where we call OnCellLeave.
        editBox = null;
        //OnCellLeave(selectedCell.X,selectedCell.Y);
        //find the next editable cell to the right.
        int nextCellToRight = -1;
        for (int i = selectedCell.X + 1;i < columns.Count;i++)
        {
            if (columns.get___idx(i).getIsEditable())
            {
                nextCellToRight = i;
                break;
            }
             
        }
        if (nextCellToRight != -1)
        {
            selectedCell = new Point(nextCellToRight, selectedCell.Y);
            createEditBox();
            return ;
        }
         
        //can't move to the right, so attempt to move down.
        if (selectedCell.Y == rows.Count - 1)
        {
            return ;
        }
         
        //can't move down
        nextCellToRight = -1;
        for (int i = 0;i < columns.Count;i++)
        {
            if (columns.get___idx(i).getIsEditable())
            {
                nextCellToRight = i;
                break;
            }
             
        }
        //guaranteed to have a value
        selectedCell = new Point(nextCellToRight, selectedCell.Y + 1);
        createEditBox();
    }

    void editBox_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        if (editBox == null)
        {
            return ;
        }
         
        if (StringSupport.equals(editBox.Text, ""))
        {
            return ;
        }
         
        Graphics g = CreateGraphics();
        Font cellFont = new Font(FontFamily.GenericSansSerif, cellFontSize);
        int cellH = (int)((1.03) * (float)(g.MeasureString(editBox.Text + "\r\n", cellFont, editBox.Width).Height)) + 4;
        if (cellH < EDITABLE_ROW_HEIGHT)
        {
            //if it's less than one line
            cellH = EDITABLE_ROW_HEIGHT;
        }
         
        //set it to one line
        if (cellH > editBox.Height)
        {
            //it needs to grow so redraw it. Only measures the text of this one cell so checking here for shrinking would cause unnecessary redraws and other bugs.
            rows[selectedCell.Y].Cells[selectedCell.X].Text = editBox.Text;
            Point cellSelected = new Point(selectedCell.X, selectedCell.Y);
            int selectionStart = editBox.SelectionStart;
            List<OpenDental.UI.ODGridColumn> listCols = new List<OpenDental.UI.ODGridColumn>();
            for (int i = 0;i < columns.Count;i++)
            {
                listCols.Add(new OpenDental.UI.ODGridColumn(columns.get___idx(i).getHeading(),columns.get___idx(i).getColWidth(),columns.get___idx(i).getIsEditable()));
                listCols[i].TextAlign = columns.get___idx(i).getTextAlign();
            }
            List<OpenDental.UI.ODGridRow> listRows = new List<OpenDental.UI.ODGridRow>();
            OpenDental.UI.ODGridRow row;
            for (int i = 0;i < rows.Count;i++)
            {
                row = new OpenDental.UI.ODGridRow();
                for (int j = 0;j < rows.get___idx(i).getCells().Count;j++)
                {
                    row.getCells().Add(new OpenDental.UI.ODGridCell(rows.get___idx(i).getCells()[j].Text));
                }
                row.setTag(rows.get___idx(i).getTag());
                listRows.Add(row);
            }
            beginUpdate();
            columns.Clear();
            for (int i = 0;i < listCols.Count;i++)
            {
                columns.add(new OpenDental.UI.ODGridColumn(listCols[i].Heading, listCols[i].ColWidth, listCols[i].IsEditable));
                columns.get___idx(i).setTextAlign(listCols[i].TextAlign);
            }
            rows.Clear();
            for (int i = 0;i < listRows.Count;i++)
            {
                row = new OpenDental.UI.ODGridRow();
                for (int j = 0;j < listRows[i].Cells.Count;j++)
                {
                    row.getCells().Add(listRows[i].Cells[j].Text);
                }
                row.setTag(listRows[i].Tag);
                rows.add(row);
            }
            endUpdate();
            if (editBox != null)
            {
                editBox.Dispose();
            }
             
            selectedCell = cellSelected;
            createEditBox();
            if (editBox != null)
            {
                editBox.SelectionStart = selectionStart;
                editBox.SelectionLength = 0;
            }
             
        }
         
        g.Dispose();
        cellFont.Dispose();
    }

    void editBox_TextChanged(Object sender, EventArgs e) throws Exception {
        if (editBox != null)
        {
            getRows()[selectedCell.Y].Cells[selectedCell.X].Text = editBox.Text;
        }
         
        onCellTextChanged();
    }

    /**
    * The purpose of this is to allow dragging to select multiple rows.  Only makes sense if selectionMode==MultiExtended.  Doesn't matter whether ctrl is down, because that only affects the mouse down event.
    */
    protected void onMouseMove(MouseEventArgs e) throws Exception {
        super.OnMouseMove(e);
        MouseIsOver = true;
        if (!MouseIsDown)
        {
            return ;
        }
         
        if (selectionMode != OpenDental.UI.GridSelectionMode.MultiExtended)
        {
            return ;
        }
         
        if (!allowSelection)
        {
            return ;
        }
         
        //dragging does not change selection of rows
        if (mouseIsDownInHeader)
        {
            return ;
        }
         
        //started drag in header, so not allowed to select anything.
        int curRow = PointToRow(e.Y);
        if (curRow == -1 || curRow == MouseDownRow)
        {
            return ;
        }
         
        //because mouse might have moved faster than computer could keep up, we have to loop through all rows between
        if (ControlIsDown)
        {
            if (selectedIndicesWhenMouseDown == null)
            {
                selectedIndices = new ArrayList();
            }
            else
            {
                selectedIndices = new ArrayList(selectedIndicesWhenMouseDown);
            } 
        }
        else
        {
            selectedIndices = new ArrayList();
        } 
        if (MouseDownRow < curRow)
        {
            for (int i = MouseDownRow;i <= curRow;i++)
            {
                //dragging down
                if (i == -1)
                {
                    continue;
                }
                 
                if (selectedIndices.Contains(i))
                {
                    selectedIndices.Remove(i);
                }
                else
                {
                    selectedIndices.Add(i);
                } 
            }
        }
        else
        {
            for (int i = curRow;i <= MouseDownRow;i++)
            {
                //dragging up
                if (selectedIndices.Contains(i))
                {
                    selectedIndices.Remove(i);
                }
                else
                {
                    selectedIndices.Add(i);
                } 
            }
        } 
        Invalidate();
    }

    /**
    * 
    */
    protected void onMouseEnter(EventArgs e) throws Exception {
        super.OnMouseEnter(e);
        MouseIsOver = true;
    }

    /**
    * 
    */
    protected void onMouseLeave(EventArgs e) throws Exception {
        super.OnMouseLeave(e);
        MouseIsOver = false;
    }

    private void vScroll_MouseEnter(Object sender, EventArgs e) throws Exception {
        MouseIsOver = true;
    }

    private void vScroll_MouseLeave(Object sender, EventArgs e) throws Exception {
        MouseIsOver = false;
    }

    private void vScroll_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        MouseIsOver = true;
    }

    private void hScroll_MouseEnter(Object sender, EventArgs e) throws Exception {
        MouseIsOver = true;
    }

    private void hScroll_MouseLeave(Object sender, EventArgs e) throws Exception {
        MouseIsOver = false;
    }

    private void hScroll_MouseMove(Object sender, MouseEventArgs e) throws Exception {
        MouseIsOver = true;
    }

    private void parent_MouseWheel(Object sender, MouseEventArgs e) throws Exception {
        if (MouseIsOver)
        {
            //this.ac
            this.Select();
        }
         
    }

    //?
    //this.Focus();
    /**
    * 
    */
    protected void onMouseWheel(MouseEventArgs e) throws Exception {
        super.OnMouseWheel(e);
        setScrollValue(getScrollValue() - e.Delta / 3);
    }

    /**
    * 
    */
    protected void onKeyDown(KeyEventArgs e) throws Exception {
        super.OnKeyDown(e);
        if (e.KeyCode == Keys.ControlKey)
        {
            ControlIsDown = true;
        }
         
        if (e.KeyCode == Keys.ShiftKey)
        {
            ShiftIsDown = true;
        }
         
    }

    /**
    * 
    */
    protected void onKeyUp(KeyEventArgs e) throws Exception {
        super.OnKeyUp(e);
        if (e.KeyCode == Keys.ControlKey)
        {
            ControlIsDown = false;
        }
         
        if (e.KeyCode == Keys.ShiftKey)
        {
            ShiftIsDown = false;
        }
         
    }

    /**
    * If the Ctrl key down is not being captured by the grid because it doesn't have focus, then this automatically handles it.  The only thing you have to do to make it work is to turn on KeyPreview for the parent form.
    */
    private void parent_KeyDown(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.ControlKey)
        {
            ControlIsDown = true;
        }
         
        if (e.KeyCode == Keys.ShiftKey)
        {
            ShiftIsDown = true;
        }
         
        if (selectionMode == OpenDental.UI.GridSelectionMode.One)
        {
            if (e.KeyCode == Keys.Down)
            {
                if (selectedIndices.Count > 0 && (int)selectedIndices[0] < rows.Count - 1)
                {
                    int prevRow = (int)selectedIndices[0];
                    selectedIndices.Clear();
                    selectedIndices.Add(prevRow + 1);
                    hScroll.Value = hScroll.Minimum;
                }
                 
            }
            else if (e.KeyCode == Keys.Up)
            {
                if (selectedIndices.Count > 0 && (int)selectedIndices[0] > 0)
                {
                    int prevRow = (int)selectedIndices[0];
                    selectedIndices.Clear();
                    selectedIndices.Add(prevRow - 1);
                }
                 
            }
              
        }
         
    }

    /**
    * If the Ctrl key down is not being captured by the grid because it doesn't have focus, then this automatically handles it.  The only thing you have to do to make it work is to turn on KeyPreview for the parent form.
    */
    private void parent_KeyUp(Object sender, KeyEventArgs e) throws Exception {
        if (e.KeyCode == Keys.ControlKey)
        {
            ControlIsDown = false;
        }
         
        if (e.KeyCode == Keys.ShiftKey)
        {
            ShiftIsDown = false;
        }
         
    }

    //if(e.KeyCode==Keys.Down | e.KeyCode==Keys.Up){
    //	UpDownKey=false;
    //	hScroll.Value=hScroll.Minimum;
    //}
    protected void onCellTextChanged() throws Exception {
        if (CellTextChanged != null)
        {
            CellTextChanged(this, new EventArgs());
        }
         
    }

    protected void onCellLeave(int col, int row) throws Exception {
        if (CellLeave != null)
        {
            CellLeave.invoke(this,new OpenDental.UI.ODGridClickEventArgs(col, row, MouseButtons.None));
        }
         
    }

}


