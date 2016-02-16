//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:09 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.Reports;

/**
* 
*/
public class ReportSimpleGrid   
{
    /**
    * 
    */
    public DataTable TableQ = new DataTable();
    /**
    * 
    */
    public String Query = new String();
    /**
    * 
    */
    public String Title = new String();
    /**
    * 
    */
    public List<String> SubTitle = new List<String>();
    /**
    * 
    */
    private int[] colPos = new int[]();
    /**
    * 
    */
    private String[] colCaption = new String[]();
    /**
    * 
    */
    private HorizontalAlignment[] colAlign = new HorizontalAlignment[]();
    /**
    * 
    */
    public double[] ColTotal = new double[]();
    /**
    * 
    */
    private int[] colWidth = new int[]();
    /**
    * 
    */
    public List<String> Summary = new List<String>();
    /**
    * This is a quick hack to allow printing account numbers on deposit slips in a different font.  It will typically be null.  It will be gone soon.
    */
    public String SummaryFont = new String();
    /**
    * If there are just too many columns for portrait.
    */
    public boolean IsLandscape = new boolean();
    public ReportSimpleGrid() throws Exception {
        SubTitle = new List<String>();
        Summary = new List<String>();
    }

    public int[] getColWidth() throws Exception {
        return colWidth;
    }

    /**
    * This is a little flakey and will be improved later.  The first value is always 0.  They represent the left edge of each column, so there must be one more than the number of columns in order to record the position of the right edge of the last column.
    */
    public int[] getColPos() throws Exception {
        return colPos;
    }

    public String[] getColCaption() throws Exception {
        return colCaption;
    }

    public HorizontalAlignment[] getColAlign() throws Exception {
        return colAlign;
    }

    /**
    * Sends the query to the database to retrieve the table.  Then initializes the column objects.
    */
    public void submitQuery() throws Exception {
        TableQ = Reports.getTable(Query);
        initializeColumns();
    }

    /**
    * This is typically used when the data table is to be manually created.  Before calling this, be sure to create TableQ and add columns to it.
    */
    public void initializeColumns() throws Exception {
        colWidth = new int[TableQ.Columns.Count];
        colPos = new int[TableQ.Columns.Count + 1];
        colPos[0] = 0;
        colCaption = new String[TableQ.Columns.Count];
        colAlign = new HorizontalAlignment[TableQ.Columns.Count];
        ColTotal = new double[TableQ.Columns.Count];
    }

    /*
    		///<summary>This is typically used when we want to define the columns before we run the query.  If the query has already been run, then this method is not necessary.</summary>
    		public void InitializeColumns(int colcount) {
    			colWidth=new int[colcount];
    			colPos=new int[colcount+1];
    			colPos[0]=0;
    			colCaption=new string[colcount];
    			colAlign=new HorizontalAlignment[colcount];
    			ColTotal=new double[colcount];
    		}*/
    /**
    * Runs the query and returns the result.  An improvement would be to pass in the query, but no time to rewrite.
    */
    public DataTable getTempTable() throws Exception {
        return Reports.getTable(Query);
    }

    /**
    * Assumes that the columns will be set in sequential order.  Automatically runs the language translation.
    */
    public void setColumn(Object sender, int idx, String colCaption, int colWidth) throws Exception {
        SetColumn(sender, idx, colCaption, colWidth, HorizontalAlignment.Left);
    }

    /**
    * Assumes that the columns will be set in sequential order.  Automatically runs the language translation.
    */
    public void setColumn(Object sender, int idx, String colCaption, int colWidth, HorizontalAlignment colAlign) throws Exception {
        this.colCaption[idx] = Lan.g(sender,colCaption);
        this.colWidth[idx] = colWidth;
        this.colPos[idx + 1] = this.colPos[idx] + colWidth;
        this.colAlign[idx] = colAlign;
    }

    /**
    * This is an alternative to SetColumn.  Used when we want to set absolute column positions instead of widths.  Mostly used for older reports so that we don't have to sit down with a calculator and refigure each column width.  SetColumn is the newer better way to do it.  When using this, set the RIGHT position of each column.  Column 1 always has a left position of 0.
    */
    public void setColumnPos(Object sender, int idx, String colCaption, int colPos) throws Exception {
        SetColumnPos(this, idx, colCaption, colPos, HorizontalAlignment.Left);
    }

    /**
    * This is an alternative to SetColumn.  Used when we want to set absolute column positions instead of widths.  Mostly used for older reports so that we don't have to sit down with a calculator and refigure each column width.  SetColumn is the newer better way to do it.  When using this, set the RIGHT position of each column.  Column 1 always has a left position of 0.
    */
    public void setColumnPos(Object sender, int idx, String colCaption, int colPos, HorizontalAlignment colAlign) throws Exception {
        this.colCaption[idx] = Lan.g(sender,colCaption);
        this.colPos[idx + 1] = colPos;
        this.colWidth[idx] = this.colPos[idx + 1] - this.colPos[idx];
        this.colAlign[idx] = colAlign;
    }

}


