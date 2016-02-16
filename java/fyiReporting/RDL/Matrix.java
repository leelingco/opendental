//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.JavaSupport.language.ReturnPreOrPostValue;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.ColumnGrouping;
import fyiReporting.RDL.ColumnGroupings;
import fyiReporting.RDL.Corner;
import fyiReporting.RDL.DataRegion;
import fyiReporting.RDL.GroupExpression;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.Matrix;
import fyiReporting.RDL.MatrixCell;
import fyiReporting.RDL.MatrixCellDataElementOutput;
import fyiReporting.RDL.MatrixCellDataElementOutputEnum;
import fyiReporting.RDL.MatrixCellEntry;
import fyiReporting.RDL.MatrixCells;
import fyiReporting.RDL.MatrixColumn;
import fyiReporting.RDL.MatrixColumns;
import fyiReporting.RDL.MatrixEntry;
import fyiReporting.RDL.MatrixLayoutDirection;
import fyiReporting.RDL.MatrixLayoutDirectionEnum;
import fyiReporting.RDL.MatrixRow;
import fyiReporting.RDL.MatrixRows;
import fyiReporting.RDL.Page;
import fyiReporting.RDL.Pages;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RowGrouping;
import fyiReporting.RDL.RowGroupings;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.StaticColumn;
import fyiReporting.RDL.StaticRow;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.XmlUtil;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Represents the report item (and Data region) for a matrix (cross-tabulation)
*/
public class Matrix  extends DataRegion 
{
    Corner _Corner;
    // The region that contains the elements of
    // the upper left corner area of the matrix.
    // If omitted, no report items are output in
    // the corner.
    ColumnGroupings _ColumnGroupings;
    // The set of column groupings for the matrix
    RowGroupings _RowGroupings;
    // The set of row groupings for the matrix
    MatrixRows _MatrixRows;
    // The rows contained in each detail cell
    // of the matrix layout
    MatrixColumns _MatrixColumns;
    // The columns contained in each detail
    // cell of the matrix layout
    MatrixLayoutDirectionEnum _LayoutDirection = MatrixLayoutDirectionEnum.LTR;
    // Indicates whether the matrix columns
    // grow left-to-right (with headers on the
    // left) or right-to-left (with headers on the
    // right).
    int _GroupsBeforeRowHeaders = new int();
    // The number of instances of the
    // outermost column group that should
    // appear to the left of the row headers
    // (right of the row headers for RTL
    // matrixes). Default is 0.
    String _CellDataElementName = new String();
    // The name to use for the cell element. Default: “Cell”
    MatrixCellDataElementOutputEnum _CellDataElementOutput = MatrixCellDataElementOutputEnum.Output;
    // Indicates whether the cell contents
    //should appear in a data rendering.  Default is Output.
    static String nullterminal = '\ufffe'.ToString();
    static String terminal = '\uffff'.ToString();
    public Matrix(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p, xNode);
        _Corner = null;
        _ColumnGroupings = null;
        _RowGroupings = null;
        _MatrixRows = null;
        _MatrixColumns = null;
        _LayoutDirection = MatrixLayoutDirectionEnum.LTR;
        _GroupsBeforeRowHeaders = 0;
        _CellDataElementName = null;
        _CellDataElementOutput = MatrixCellDataElementOutputEnum.Output;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Corner"))
            {
                _Corner = new Corner(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("ColumnGroupings"))
            {
                _ColumnGroupings = new ColumnGroupings(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("RowGroupings"))
            {
                _RowGroupings = new RowGroupings(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("MatrixRows"))
            {
                _MatrixRows = new MatrixRows(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("MatrixColumns"))
            {
                _MatrixColumns = new MatrixColumns(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("LayoutDirection"))
            {
                _LayoutDirection = MatrixLayoutDirection.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("GroupsBeforeRowHeaders"))
            {
                _GroupsBeforeRowHeaders = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else if (__dummyScrutVar0.equals("CellDataElementName"))
            {
                _CellDataElementName = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar0.equals("CellDataElementOutput"))
            {
                _CellDataElementOutput = MatrixCellDataElementOutput.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                if (dataRegionElement(xNodeLoop))
                    break;
                 
                // try at DataRegion level
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Matrix element '" + xNodeLoop.Name + "' ignored.");
            }         
        }
        dataRegionFinish();
        // Tidy up the DataRegion
        if (_ColumnGroupings == null)
            OwnerReport.rl.logError(8,"Matrix element ColumnGroupings not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
        if (_RowGroupings == null)
            OwnerReport.rl.logError(8,"Matrix element RowGroupings not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
        if (_MatrixRows == null)
            OwnerReport.rl.logError(8,"Matrix element MatrixRows not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
        if (_MatrixColumns == null)
            OwnerReport.rl.logError(8,"Matrix element MatrixColumns not specified for " + (this.getName() == null ? "'name not specified'" : this.getName().getNm()));
         
        // MatrixCells count must be the same as the number of StaticColumns.
        //   If there are no StaticColumns it must be 1
        if (OwnerReport.rl.getMaxSeverity() > 4)
            return ;
         
        // don't perform this check if we've already go errors
        int mc = _MatrixRows.getCellCount();
        // MatrixCells
        int sc = Math.Max(1, _ColumnGroupings.getStaticCount());
        if (mc != sc)
        {
            OwnerReport.rl.logError(8,"The count of MatrixCells must be 1 or equal to the number of StaticColumns if there are any.  Matrix " + (this.getName() == null ? "unknown." : this.getName().getNm()));
        }
         
        // matrix columns must also equal the static count (or 1 if no static columns)
        mc = this.getCountMatrixColumns();
        if (mc != sc)
        {
            OwnerReport.rl.logError(8,"The count of MatrixColumns must be 1 or equal to the number of StaticColumns if there are any.  Matrix " + (this.getName() == null ? "unknown." : this.getName().getNm()));
        }
         
        // matrix rows must also equal the static count (or 1 if no static rows)
        int mr = this.getCountMatrixRows();
        int sr = Math.Max(1, _RowGroupings.getStaticCount());
        if (mr != sr)
        {
            OwnerReport.rl.logError(8,"The count of MatrixRows must be 1 or equal to the number of StaticRows if there are any.  Matrix " + (this.getName() == null ? "unknown." : this.getName().getNm()));
        }
         
    }

    public void finalPass() throws Exception {
        super.finalPass();
        float totalHeight = 0;
        if (_Corner != null)
            _Corner.finalPass();
         
        if (_ColumnGroupings != null)
        {
            _ColumnGroupings.finalPass();
            totalHeight += _ColumnGroupings.defnHeight();
        }
         
        if (_RowGroupings != null)
            _RowGroupings.finalPass();
         
        if (_MatrixRows != null)
        {
            _MatrixRows.finalPass();
            totalHeight += _MatrixRows.defnHeight();
        }
         
        if (_MatrixColumns != null)
            _MatrixColumns.finalPass();
         
        if (this.getHeight() == null)
        {
            // Calculate a height based on the sum of the TableRows
            this.setHeight(new RSize(this.OwnerReport, String.Format(NumberFormatInfo.InvariantInfo, "{0:0.00}pt", totalHeight)));
        }
         
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        fyiReporting.RDL.Report rpt = ip.report();
        WorkClass wc = getValue(rpt);
        wc.FullData = wc.Data = getFilteredData(rpt,row);
        if (!anyRows(ip,wc.Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        int maxColumns = new int();
        int maxRows = new int();
        RefSupport<int> refVar___0 = new RefSupport<int>();
        RefSupport<int> refVar___1 = new RefSupport<int>();
        MatrixCellEntry[][] matrix = runBuild(rpt,refVar___0,refVar___1);
        maxRows = refVar___0.getValue();
        maxColumns = refVar___1.getValue();
        // Now run thru the rows and columns of the matrix passing the information
        //   on to the rendering engine
        if (!ip.matrixStart(this,row))
            return ;
         
        for (int iRow = 0;iRow < maxRows;iRow++)
        {
            ip.matrixRowStart(this,iRow,row);
            for (int iColumn = 0;iColumn < maxColumns;iColumn++)
            {
                MatrixCellEntry mce = matrix[iRow, iColumn];
                if (mce == null)
                {
                    ip.MatrixCellStart(this, null, iRow, iColumn, row, float.MinValue, float.MinValue, 1);
                    ip.matrixCellEnd(this,null,iRow,iColumn,row);
                }
                else
                {
                    wc.Data = mce.getData();
                    // Must set this for evaluation
                    Row lrow = wc.Data.getData().Count > 0 ? wc.Data.getData()[0] : null;
                    mce.getDisplayItem().setMC(rpt,mce);
                    // set for use by the display item
                    setGroupingValues(rpt,mce);
                    ip.matrixCellStart(this,mce.getDisplayItem(),iRow,iColumn,lrow,mce.getHeight(),mce.getWidth(),mce.getColSpan());
                    mce.getDisplayItem().run(ip,lrow);
                    ip.matrixCellEnd(this,mce.getDisplayItem(),iRow,iColumn,lrow);
                } 
            }
            ip.matrixRowEnd(this,iRow,row);
        }
        ip.matrixEnd(this,row);
        removeValue(rpt);
    }

    public void runPage(Pages pgs, Row row) throws Exception {
        fyiReporting.RDL.Report r = pgs.getReport();
        if (isHidden(r,row))
            return ;
         
        WorkClass wc = getValue(r);
        wc.FullData = wc.Data = getFilteredData(r,row);
        setPagePositionBegin(pgs);
        if (!anyRowsPage(pgs,wc.Data))
            return ;
         
        // if no rows return
        //   nothing left to do
        int maxColumns = new int();
        int maxRows = new int();
        int headerRows = _ColumnGroupings.getItems().Count;
        // number of column headers we have
        RefSupport<int> refVar___2 = new RefSupport<int>();
        RefSupport<int> refVar___3 = new RefSupport<int>();
        MatrixCellEntry[][] matrix = runBuild(r,refVar___2,refVar___3);
        maxRows = refVar___2.getValue();
        maxColumns = refVar___3.getValue();
        // Now run thru the rows and columns of the matrix creating the pages
        runPageRegionBegin(pgs);
        Page p = pgs.getCurrentPage();
        p.setYOffset(p.getYOffset() + this.relativeY(r));
        for (int iRow = 0;iRow < maxRows;iRow++)
        {
            float h = HeightOfRow(pgs, matrix, iRow);
            if (h <= 0)
                continue;
             
            // there were no cells in row
            //     skip the row
            if (p.getYOffset() + h > pgs.getBottomOfPage())
            {
                p = runPageNew(pgs,p);
                for (int aRow = 0;aRow < headerRows;aRow++)
                {
                    // run thru the headers again
                    RunPageColumns(pgs, wc, matrix, aRow, maxColumns);
                    p.setYOffset(p.getYOffset() + HeightOfRow(pgs, matrix, aRow));
                }
            }
             
            RunPageColumns(pgs, wc, matrix, iRow, maxColumns);
            p.setYOffset(p.getYOffset() + h);
        }
        runPageRegionEnd(pgs);
        setPagePositionEnd(pgs,pgs.getCurrentPage().getYOffset());
        removeValue(r);
    }

    public void runReset(fyiReporting.RDL.Report rpt) throws Exception {
        removeValue(rpt);
    }

    float heightOfRow(Pages pgs, MatrixCellEntry[][] matrix, int iRow) throws Exception {
        fyiReporting.RDL.Report rpt = pgs.getReport();
        WorkClass wc = getValue(rpt);
        int maxColumns = matrix.GetLength(1);
        float height = 0;
        boolean bResetAllHeights = false;
        // Handle the corner;  it might span rows & columns
        boolean bCorner = false;
        float cornerHeight = 0;
        if (iRow == 0 && matrix[0, 0] != null && (this.getColumnGroupings().getItems().Count > 1 || this.getRowGroupings().getItems().Count > 1))
        {
            bCorner = true;
        }
         
        for (int iCol = 0;iCol < maxColumns;iCol++)
        {
            MatrixCellEntry mce = matrix[iRow, iCol];
            if (mce == null)
                continue;
             
            if (mce.getDisplayItem() instanceof Textbox)
            {
                Textbox tb = mce.getDisplayItem() instanceof Textbox ? (Textbox)mce.getDisplayItem() : (Textbox)null;
                if (tb.getCanGrow())
                {
                    wc.Data = mce.getData();
                    // Must set this for evaluation
                    Row lrow = wc.Data.getData().Count > 0 ? wc.Data.getData()[0] : null;
                    mce.getDisplayItem().setMC(rpt,mce);
                    // set for use by the display item
                    setGroupingValues(rpt,mce);
                    float tbh = tb.runTextCalcHeight(rpt,pgs.getG(),lrow);
                    if (height < tbh)
                    {
                        if (bCorner && iCol == 0)
                        {
                            cornerHeight = tbh;
                        }
                        else
                        {
                            bResetAllHeights = true;
                            height = tbh;
                        } 
                    }
                     
                }
                 
            }
             
            if (bCorner && iCol == 0)
                continue;
             
            if (height < mce.getHeight())
                height = mce.getHeight();
             
        }
        if (bResetAllHeights)
        {
            for (int iCol = 0;iCol < maxColumns;iCol++)
            {
                // If any text forces the row to grow; all heights must be fixed
                if (bCorner && iCol == 0)
                    continue;
                 
                MatrixCellEntry mce = matrix[iRow, iCol];
                if (mce != null)
                    mce.setHeight(height);
                 
            }
        }
         
        // Even with expansion room; we might need more space for the corner
        if (bCorner && cornerHeight > matrix[0, 0].Height)
        {
            // add the additional space needed to the first row's height
            float newRow0Height = new float();
            if (getColumnGroupings().getItems().Count == 1)
                newRow0Height = cornerHeight;
            else if (matrix[0, 1] != null)
                newRow0Height = matrix[0, 1].Height + (cornerHeight - matrix[0, 0].Height);
            else
                newRow0Height = (cornerHeight - matrix[0, 0].Height);  
            height = newRow0Height;
            matrix[0, 0].Height = cornerHeight;
            for (int iCol = 1;iCol < maxColumns;iCol++)
            {
                MatrixCellEntry mce = matrix[0, iCol];
                if (mce != null)
                    mce.setHeight(newRow0Height);
                 
            }
        }
         
        return height;
    }

    float widthOfColumn(MatrixCellEntry[][] matrix, int iCol) throws Exception {
        int maxRows = matrix.GetLength(0);
        for (int iRow = 0;iRow < maxRows;iRow++)
        {
            if (matrix[iRow, iCol] != null && matrix[iRow, iCol].ColSpan == 1)
                return matrix[iRow, iCol].Width;
             
        }
        return 0;
    }

    void runPageColumns(Pages pgs, WorkClass wc, MatrixCellEntry[][] matrix, int iRow, int maxColumns) throws Exception {
        fyiReporting.RDL.Report rpt = pgs.getReport();
        float xpos = getOffsetCalc(pgs.getReport()) + leftCalc(rpt);
        for (int iColumn = 0;iColumn < maxColumns;iColumn++)
        {
            MatrixCellEntry mce = matrix[iRow, iColumn];
            if (mce == null)
            {
                // have a null column but we need to fill column space
                xpos += WidthOfColumn(matrix, iColumn);
                continue;
            }
             
            wc.Data = mce.getData();
            // Must set this for evaluation
            Row lrow = wc.Data.getData().Count > 0 ? wc.Data.getData()[0] : null;
            setGroupingValues(rpt,mce);
            mce.getDisplayItem().setMC(rpt,mce);
            // set for use by the display item
            mce.setXPosition(xpos);
            mce.getDisplayItem().runPage(pgs,lrow);
            xpos += mce.getWidth();
            iColumn += (mce.getColSpan() - 1);
        }
    }

    // skip columns already accounted for
    // RunBuild is used by both Matrix.Run and Chart.Run to obtain the necessary data
    //   used by their respective rendering interfaces
    public MatrixCellEntry[][] runBuild(fyiReporting.RDL.Report rpt, RefSupport<int> numRows, RefSupport<int> numCols) throws Exception {
        WorkClass wc = getValue(rpt);
        Rows _Data = wc.Data;
        // loop thru all the data;
        //    form bitmap arrays for each unique data value of each grouping (row and column) value
        int maxColumns = _RowGroupings.getItems().Count;
        // maximum # of columns in matrix
        // at top we need a row per column grouping
        int maxRows = _ColumnGroupings.getItems().Count;
        // maximum # of rows in matrix
        // at left we need a column per row grouping
        MatrixEntry mcg = new MatrixEntry(null, _Data.getData().Count);
        _ColumnGroupings.setME(rpt,mcg);
        mcg.setFirstRow(0);
        mcg.setLastRow(_Data.getData().Count - 1);
        mcg.setRows(new BitArray(_Data.getData().Count, true));
        // all data
        MatrixEntry mrg = new MatrixEntry(null, _Data.getData().Count);
        _RowGroupings.setME(rpt,mrg);
        mrg.setFirstRow(0);
        mrg.setLastRow(_Data.getData().Count - 1);
        mrg.setRows(new BitArray(_Data.getData().Count, true));
        // all data
        int iRow = 0;
        for (Object __dummyForeachVar1 : _Data.getData())
        {
            // row counter
            Row r = (Row)__dummyForeachVar1;
            // Handle the column values
            RefSupport<int> refVar___4 = new RefSupport<int>(maxColumns);
            handleColumnGrouping(rpt,wc,_Data,r,mcg,0,iRow,refVar___4);
            maxColumns = refVar___4.getValue();
            // Handle the row values
            RefSupport<int> refVar___5 = new RefSupport<int>(maxRows);
            handleRowGrouping(rpt,wc,_Data,r,mrg,0,iRow,refVar___5);
            maxRows = refVar___5.getValue();
            iRow++;
        }
        // Determine how many subtotal columns are needed
        maxColumns += runCountSubtotalColumns(wc,mcg,0);
        // Determine how many subtotal rows are needed
        maxRows += runCountSubtotalRows(wc,mrg,0);
        /**
        * //
        */
        // Build and populate the 2 dimensional table of MatrixCellEntry
        //    that constitute the matrix
        /**
        * //
        */
        MatrixCellEntry[][] matrix = new MatrixCellEntry[maxRows, maxColumns];
        // Do the column headings
        int iColumn = _RowGroupings.getItems().Count;
        RefSupport<int> refVar___6 = new RefSupport<int>(iColumn);
        RunColumnHeaders(rpt, wc, mcg, matrix, _Data, 0, refVar___6, 0);
        iColumn = refVar___6.getValue();
        // Do the row headings
        iRow = _ColumnGroupings.getItems().Count;
        RefSupport<int> refVar___7 = new RefSupport<int>(iRow);
        RunRowHeaders(rpt, wc, mrg, matrix, _Data, refVar___7, 0, 0);
        iRow = refVar___7.getValue();
        // Do the row/column data
        iRow = _ColumnGroupings.getItems().Count;
        RefSupport<int> refVar___8 = new RefSupport<int>(iRow);
        RunDataRow(rpt, wc, mrg, mcg, matrix, _Data, refVar___8, _RowGroupings.getItems().Count, 0);
        iRow = refVar___8.getValue();
        // Do the corner
        matrix[0, 0] = runCorner(_Data);
        // now return the matrix data
        numRows.setValue(maxRows);
        numCols.setValue(maxColumns);
        return matrix;
    }

    int getCountMatrixCells() throws Exception {
        MatrixRow mr = this.getMatrixRows().getItems()[0] instanceof MatrixRow ? (MatrixRow)this.getMatrixRows().getItems()[0] : (MatrixRow)null;
        return mr.getMatrixCells().getItems().Count;
    }

    int getCountMatrixColumns() throws Exception {
        return this.getMatrixColumns().getItems().Count;
    }

    int getCountMatrixRows() throws Exception {
        return this.getMatrixRows().getItems().Count;
    }

    ColumnGrouping getLastCg() throws Exception {
        return (ColumnGrouping)(_ColumnGroupings.getItems()[_ColumnGroupings.getItems().Count - 1]);
    }

    RowGrouping getLastRg() throws Exception {
        return (RowGrouping)(_RowGroupings.getItems()[_RowGroupings.getItems().Count - 1]);
    }

    /**
    * Get the last (dynamic) ColumnGrouping
    * 
    *  @return
    */
    ColumnGrouping getLastDynColumnGrouping() throws Exception {
        for (int i = _ColumnGroupings.getItems().Count - 1;i >= 0;i--)
        {
            ColumnGrouping cg = (ColumnGrouping)(_ColumnGroupings.getItems()[i]);
            if (cg.getStaticColumns() == null)
                return cg;
             
        }
        return (ColumnGrouping)(_ColumnGroupings.getItems()[_ColumnGroupings.getItems().Count - 1]);
    }

    /**
    * Get the last (dynamic) RowGrouping
    * 
    *  @return
    */
    RowGrouping getLastDynRowGrouping() throws Exception {
        for (int i = _RowGroupings.getItems().Count - 1;i >= 0;i--)
        {
            RowGrouping rg = (RowGrouping)(_RowGroupings.getItems()[i]);
            if (rg.getStaticRows() == null)
                return rg;
             
        }
        return (RowGrouping)(_RowGroupings.getItems()[_RowGroupings.getItems().Count - 1]);
    }

    void handleRowGrouping(fyiReporting.RDL.Report rpt, WorkClass wc, Rows rows, Row r, MatrixEntry m, int rgi, int iRow, RefSupport<int> maxRows) throws Exception {
        while (rgi < _RowGroupings.getItems().Count)
        {
            RowGrouping rg = _RowGroupings.getItems()[rgi] instanceof RowGrouping ? (RowGrouping)_RowGroupings.getItems()[rgi] : (RowGrouping)null;
            Grouping grp = null;
            String result = new String();
            if (rg.getStaticRows() != null)
            {
                for (int sri = 0;sri < rg.getStaticRows().getItems().Count;sri++)
                {
                    // handle static rows
                    result = Convert.ToChar(Convert.ToInt32('a') + sri).ToString() + terminal;
                    // static row; put all data in it
                    StaticRow sr = rg.getStaticRows().getItems()[sri] instanceof StaticRow ? (StaticRow)rg.getStaticRows().getItems()[sri] : (StaticRow)null;
                    MatrixEntry ame = (MatrixEntry)(m.getHashData()[result]);
                    if (ame == null)
                    {
                        ame = new MatrixEntry(m, rows.getData().Count);
                        ame.setRowGroup(rg);
                        ame.setStaticRow(sri);
                        m.getHashData().Add(result, ame);
                        if (rg == getLastRg())
                            // Add a row when we add data at lowest level
                            maxRows.setValue(maxRows.getValue() + 1, ReturnPreOrPostValue.POST);
                         
                    }
                     
                    ame.getRows().Set(iRow, true);
                    // Logic in FirstRow and Last row determine whether value gets set
                    ame.setFirstRow(iRow);
                    ame.setLastRow(iRow);
                    RefSupport<int> refVar___9 = new RefSupport<int>(maxRows.getValue());
                    handleRowGrouping(rpt,wc,rows,r,ame,rgi + 1,iRow,refVar___9);
                    maxRows.setValue(refVar___9.getValue());
                }
                break;
            }
            else
            {
                // handled ones below it recursively
                // handle dynamic columns
                grp = rg.getDynamicRows().getGrouping();
                StringBuilder sb = new StringBuilder();
                for (Object __dummyForeachVar2 : grp.getGroupExpressions().getItems())
                {
                    GroupExpression ge = (GroupExpression)__dummyForeachVar2;
                    String temp = ge.getExpression().evaluateString(rpt,r);
                    if (temp == null || StringSupport.equals(temp, ""))
                        sb.Append(nullterminal);
                    else
                        sb.Append(temp); 
                    sb.Append(terminal);
                }
                // mark end of group
                result = sb.ToString();
                MatrixEntry ame = (MatrixEntry)(m.getHashData()[result]);
                if (ame == null)
                {
                    ame = new MatrixEntry(m, rows.getData().Count);
                    ame.setRowGroup(rg);
                    m.getHashData().Add(result, ame);
                    if (rg == getLastRg())
                        // Add a row when we add data at lowest level
                        maxRows.setValue(maxRows.getValue() + 1, ReturnPreOrPostValue.POST);
                     
                }
                 
                ame.getRows().Set(iRow, true);
                // Logic in FirstRow and Last row determine whether value gets set
                ame.setFirstRow(iRow);
                ame.setLastRow(iRow);
                m = ame;
                // now go down a level
                rgi++;
            } 
        }
    }

    void handleColumnGrouping(fyiReporting.RDL.Report rpt, WorkClass wc, Rows rows, Row r, MatrixEntry m, int cgi, int iRow, RefSupport<int> maxColumns) throws Exception {
        while (cgi < _ColumnGroupings.getItems().Count)
        {
            ColumnGrouping cg = _ColumnGroupings.getItems()[cgi] instanceof ColumnGrouping ? (ColumnGrouping)_ColumnGroupings.getItems()[cgi] : (ColumnGrouping)null;
            Grouping grp = null;
            String result = new String();
            if (cg.getStaticColumns() != null)
            {
                for (int sci = 0;sci < cg.getStaticColumns().getItems().Count;sci++)
                {
                    // handle static columns
                    result = Convert.ToChar(Convert.ToInt32('a') + sci).ToString() + terminal;
                    // static column; put all data in it
                    StaticColumn sc = cg.getStaticColumns().getItems()[sci] instanceof StaticColumn ? (StaticColumn)cg.getStaticColumns().getItems()[sci] : (StaticColumn)null;
                    MatrixEntry ame = (MatrixEntry)(m.getHashData()[result]);
                    if (ame == null)
                    {
                        ame = new MatrixEntry(m, rows.getData().Count);
                        ame.setColumnGroup(cg);
                        ame.setStaticColumn(sci);
                        m.getHashData().Add(result, ame);
                        if (cg == getLastCg())
                            // Add a column when we add data at lowest level
                            maxColumns.setValue(maxColumns.getValue() + 1, ReturnPreOrPostValue.POST);
                         
                    }
                     
                    ame.getRows().Set(iRow, true);
                    // Logic in FirstRow and Last row determine whether value gets set
                    ame.setFirstRow(iRow);
                    ame.setLastRow(iRow);
                    RefSupport<int> refVar___10 = new RefSupport<int>(maxColumns.getValue());
                    handleColumnGrouping(rpt,wc,rows,r,ame,cgi + 1,iRow,refVar___10);
                    maxColumns.setValue(refVar___10.getValue());
                }
                break;
            }
            else
            {
                // handled ones below it recursively
                // handle dynamic columns
                grp = cg.getDynamicColumns().getGrouping();
                StringBuilder sb = new StringBuilder();
                for (Object __dummyForeachVar3 : grp.getGroupExpressions().getItems())
                {
                    GroupExpression ge = (GroupExpression)__dummyForeachVar3;
                    String temp = ge.getExpression().evaluateString(rpt,r);
                    if (temp == null || StringSupport.equals(temp, ""))
                        sb.Append(nullterminal);
                    else
                        sb.Append(temp); 
                    sb.Append(terminal);
                }
                // mark end of group
                result = sb.ToString();
                MatrixEntry ame = (MatrixEntry)(m.getHashData()[result]);
                if (ame == null)
                {
                    ame = new MatrixEntry(m, rows.getData().Count);
                    ame.setColumnGroup(cg);
                    m.getHashData().Add(result, ame);
                    if (cg == getLastCg())
                        // Add a column when we add data at lowest level
                        maxColumns.setValue(maxColumns.getValue() + 1, ReturnPreOrPostValue.POST);
                     
                }
                 
                ame.getRows().Set(iRow, true);
                // Logic in FirstRow and Last row determine whether value gets set
                ame.setFirstRow(iRow);
                ame.setLastRow(iRow);
                m = ame;
                // now go down a level
                cgi++;
            } 
        }
    }

    int runCountSubtotalColumns(WorkClass wc, MatrixEntry m, int level) throws Exception {
        // Get the number of static columns
        int scCount = Math.Max(1, this._ColumnGroupings.getStaticCount());
        int count = 0;
        // Increase the column count when subtotal is requested at this level
        ColumnGrouping cg = (ColumnGrouping)(_ColumnGroupings.getItems()[level]);
        if (cg.getDynamicColumns() != null && cg.getDynamicColumns().getSubtotal() != null)
            count = scCount;
         
        if (m.getSortedData() == null || level + 1 >= _ColumnGroupings.getItems().Count)
            return count;
         
        for (Object __dummyForeachVar4 : m.getSortedData().Values)
        {
            // Now dive into the data
            MatrixEntry ame = (MatrixEntry)__dummyForeachVar4;
            count += runCountSubtotalColumns(wc,ame,level + 1);
        }
        return count;
    }

    int runCountSubtotalRows(WorkClass wc, MatrixEntry m, int level) throws Exception {
        // Get the number of static columns
        int srCount = Math.Max(1, this._RowGroupings.getStaticCount());
        int count = 0;
        // Increase the row count when subtotal is requested at this level
        RowGrouping rg = (RowGrouping)(_RowGroupings.getItems()[level]);
        if (rg.getDynamicRows() != null && rg.getDynamicRows().getSubtotal() != null)
            count = srCount;
         
        if (m.getSortedData() == null || level + 1 >= _RowGroupings.getItems().Count)
            return count;
         
        for (Object __dummyForeachVar5 : m.getSortedData().Values)
        {
            // Now dive into the data
            MatrixEntry ame = (MatrixEntry)__dummyForeachVar5;
            count += runCountSubtotalRows(wc,ame,level + 1);
        }
        return count;
    }

    void runColumnHeaders(fyiReporting.RDL.Report rpt, WorkClass wc, MatrixEntry m, MatrixCellEntry[][] matrix, Rows _Data, int iRow, RefSupport<int> iColumn, int level) throws Exception {
        for (Object __dummyForeachVar6 : m.getSortedData().Values)
        {
            MatrixEntry ame = (MatrixEntry)__dummyForeachVar6;
            matrix[iRow, iColumn.getValue()] = runGetColumnHeader(rpt,ame,_Data);
            matrix[iRow, iColumn.getValue()].Width = RunGetColumnWidth(matrix[iRow, iColumn.getValue()]);
            matrix[iRow, iColumn.getValue()].Height = ame.getColumnGroup().getHeight() == null ? 0 : ame.getColumnGroup().getHeight().getPoints();
            if (ame.getSortedData() != null)
            {
                RefSupport<int> refVar___11 = new RefSupport<int>(iColumn.getValue());
                RunColumnHeaders(rpt, wc, ame, matrix, _Data, iRow + 1, refVar___11, level + 1);
                iColumn.setValue(refVar___11.getValue());
            }
            else
                iColumn.setValue(iColumn.getValue() + 1, ReturnPreOrPostValue.POST); 
        }
        ColumnGrouping cg = (ColumnGrouping)(_ColumnGroupings.getItems()[level]);
        // if we need subtotal on the group
        if (cg.getDynamicColumns() != null && cg.getDynamicColumns().getSubtotal() != null)
        {
            ReportItem ri = cg.getDynamicColumns().getSubtotal().getReportItems().getItems()[0];
            matrix[iRow, iColumn.getValue()] = new MatrixCellEntry(_Data,ri);
            matrix[iRow, iColumn.getValue()].Height = cg.getHeight().getPoints();
            matrix[iRow, iColumn.getValue()].Width = RunGetColumnWidth(matrix[iRow, iColumn.getValue()]);
            RunColumnStaticHeaders(rpt, wc, matrix, _Data, iRow, iColumn.getValue(), level);
            iColumn.setValue(iColumn.getValue() + this.getCountMatrixCells());
        }
         
    }

    void runColumnStaticHeaders(fyiReporting.RDL.Report rpt, WorkClass wc, MatrixCellEntry[][] matrix, Rows _Data, int iRow, int iColumn, int level) throws Exception {
        ColumnGrouping cg = null;
        for (int i = level + 1;i < _ColumnGroupings.getItems().Count;i++)
        {
            iRow++;
            // the row will below the headers
            cg = (ColumnGrouping)(_ColumnGroupings.getItems()[i]);
            if (cg.getStaticColumns() != null)
                break;
             
        }
        if (cg == null || cg.getStaticColumns() == null)
            return ;
         
        for (Object __dummyForeachVar7 : cg.getStaticColumns().getItems())
        {
            StaticColumn sc = (StaticColumn)__dummyForeachVar7;
            ReportItem ri = sc.getReportItems().getItems()[0];
            matrix[iRow, iColumn] = new MatrixCellEntry(_Data,ri);
            matrix[iRow, iColumn].Height = cg.getHeight().getPoints();
            matrix[iRow, iColumn].Width = RunGetColumnWidth(matrix[iRow, iColumn]);
            iColumn++;
        }
        return ;
    }

    float runGetColumnWidth(MatrixCellEntry mce) throws Exception {
        if (this.getMatrixColumns() == null)
            return 0;
         
        // We use this routine for chart(s) and they don't build the matrix columns
        MatrixColumn mcol;
        // work variable to hold a MatrixColumn
        mcol = this.getMatrixColumns().getItems()[0] instanceof MatrixColumn ? (MatrixColumn)this.getMatrixColumns().getItems()[0] : (MatrixColumn)null;
        float defWidth = mcol.getWidth().getPoints();
        if (getCountMatrixColumns() == 1)
            return defWidth;
         
        // if only one column width is easy
        // find out which static column it is.
        ColumnGrouping cg = null;
        MatrixCells mcells = null;
        ReportItem ri = mce.getDisplayItem();
        for (ReportLink rl = ri.Parent;rl != null;rl = rl.Parent)
        {
            if (rl instanceof ColumnGrouping)
            {
                cg = rl instanceof ColumnGrouping ? (ColumnGrouping)rl : (ColumnGrouping)null;
                break;
            }
             
            if (rl instanceof MatrixCells)
            {
                mcells = rl instanceof MatrixCells ? (MatrixCells)rl : (MatrixCells)null;
                break;
            }
             
            if (rl instanceof Matrix)
                break;
             
        }
        int offset = 0;
        // If the item is one of the MatrixCell; then use same offset
        if (mcells != null)
        {
            for (Object __dummyForeachVar8 : mcells.getItems())
            {
                MatrixCell mcell = (MatrixCell)__dummyForeachVar8;
                ReportItem ric = mcell.getReportItems().getItems()[0] instanceof ReportItem ? (ReportItem)mcell.getReportItems().getItems()[0] : (ReportItem)null;
                if (ric == ri)
                {
                    mcol = this.getMatrixColumns().getItems()[offset] instanceof MatrixColumn ? (MatrixColumn)this.getMatrixColumns().getItems()[offset] : (MatrixColumn)null;
                    return mcol.getWidth().getPoints();
                }
                 
                offset++;
            }
            return defWidth;
        }
         
        if (cg == null || cg.getStaticColumns() == null)
            return defWidth;
         
        // Otherwise find the same relative Matrix Column from the static columns
        mcol = null;
        for (Object __dummyForeachVar9 : cg.getStaticColumns().getItems())
        {
            StaticColumn sc = (StaticColumn)__dummyForeachVar9;
            ReportItem cri = sc.getReportItems().getItems()[0] instanceof ReportItem ? (ReportItem)sc.getReportItems().getItems()[0] : (ReportItem)null;
            if (ri == cri)
            {
                mcol = this.getMatrixColumns().getItems()[offset] instanceof MatrixColumn ? (MatrixColumn)this.getMatrixColumns().getItems()[offset] : (MatrixColumn)null;
                break;
            }
             
            offset++;
        }
        return mcol == null ? defWidth : mcol.getWidth().getPoints();
    }

    MatrixCellEntry runGetColumnHeader(fyiReporting.RDL.Report rpt, MatrixEntry me, Rows _Data) throws Exception {
        ReportItem ri;
        if (me.getColumnGroup().getStaticColumns() != null)
        {
            // Handle static column reference
            StaticColumn sc = me.getColumnGroup().getStaticColumns().getItems()[me.getStaticColumn()] instanceof StaticColumn ? (StaticColumn)me.getColumnGroup().getStaticColumns().getItems()[me.getStaticColumn()] : (StaticColumn)null;
            ri = sc.getReportItems().getItems()[0];
        }
        else
            ri = me.getColumnGroup().getDynamicColumns().getReportItems().getItems()[0]; 
        // dynamic column
        Rows subData = new Rows(rpt,_Data,me.getFirstRow(),me.getLastRow(),me.getRows());
        MatrixCellEntry mce = new MatrixCellEntry(subData,ri);
        return mce;
    }

    MatrixCellEntry runCorner(Rows d) throws Exception {
        if (_Corner == null)
            return null;
         
        ReportItem ri = _Corner.getReportItems().getItems()[0];
        MatrixCellEntry mce = new MatrixCellEntry(d,ri);
        float height = 0;
        for (Object __dummyForeachVar10 : this.getColumnGroupings().getItems())
        {
            ColumnGrouping cg = (ColumnGrouping)__dummyForeachVar10;
            height += cg.getHeight().getPoints();
        }
        mce.setHeight(height);
        float width = 0;
        for (Object __dummyForeachVar11 : this.getRowGroupings().getItems())
        {
            RowGrouping rg = (RowGrouping)__dummyForeachVar11;
            width += rg.getWidth().getPoints();
        }
        mce.setWidth(width);
        mce.setColSpan(getRowGroupings().getItems().Count);
        return mce;
    }

    void runDataColumn(fyiReporting.RDL.Report rpt, WorkClass wc, MatrixEntry rm, MatrixEntry cm, MatrixCellEntry[][] matrix, Rows _Data, int iRow, RefSupport<int> iColumn, int level, int rowcell) throws Exception {
        BitArray andData = new BitArray();
        MatrixRow mr = this.getMatrixRows().getItems()[rowcell] instanceof MatrixRow ? (MatrixRow)this.getMatrixRows().getItems()[rowcell] : (MatrixRow)null;
        float height = mr.getHeight() == null ? 0 : mr.getHeight().getPoints();
        for (Object __dummyForeachVar12 : cm.getSortedData().Values)
        {
            MatrixEntry ame = (MatrixEntry)__dummyForeachVar12;
            if (ame.getColumnGroup() != getLastCg())
            {
                RefSupport<int> refVar___12 = new RefSupport<int>(iColumn.getValue());
                RunDataColumn(rpt, wc, rm, ame, matrix, _Data, iRow, refVar___12, level + 1, rowcell);
                iColumn.setValue(refVar___12.getValue());
                continue;
            }
             
            andData = new BitArray(ame.getRows());
            // copy the data
            andData.And(rm.getRows());
            //  because And is destructive
            matrix[iRow, iColumn.getValue()] = RunGetMatrixCell(rpt, ame, iRow, _Data, andData, Math.Max(rm.getFirstRow(), ame.getFirstRow()), Math.Min(rm.getLastRow(), ame.getLastRow()));
            matrix[iRow, iColumn.getValue()].Height = height;
            matrix[iRow, iColumn.getValue()].Width = RunGetColumnWidth(matrix[iRow, iColumn.getValue()]);
            matrix[iRow, iColumn.getValue()].ColumnME = ame;
            matrix[iRow, iColumn.getValue()].RowME = rm;
            iColumn.setValue(iColumn.getValue() + 1, ReturnPreOrPostValue.POST);
        }
        // do we need to subtotal this?
        ColumnGrouping cg = (ColumnGrouping)(_ColumnGroupings.getItems()[level]);
        if (cg.getDynamicColumns() != null && cg.getDynamicColumns().getSubtotal() != null)
        {
            andData = new BitArray(cm.getRows());
            // copy the data
            andData.And(rm.getRows());
            for (int i = 0;i < this.getCountMatrixCells();i++)
            {
                //  because And is destructive
                matrix[iRow, iColumn.getValue()] = RunGetMatrixCell(rpt, cm, rowcell, i, _Data, andData, Math.Max(rm.getFirstRow(), cm.getFirstRow()), Math.Min(rm.getLastRow(), cm.getLastRow()));
                matrix[iRow, iColumn.getValue()].Height = height;
                matrix[iRow, iColumn.getValue()].Width = RunGetColumnWidth(matrix[iRow, iColumn.getValue()]);
                matrix[iRow, iColumn.getValue()].ColumnME = cm;
                matrix[iRow, iColumn.getValue()].RowME = rm;
                iColumn.setValue(iColumn.getValue() + 1, ReturnPreOrPostValue.POST);
            }
        }
         
    }

    void runDataRow(fyiReporting.RDL.Report rpt, WorkClass wc, MatrixEntry rm, MatrixEntry cm, MatrixCellEntry[][] matrix, Rows _Data, RefSupport<int> iRow, int iColumn, int level) throws Exception {
        int saveColumn = new int();
        int headerRows = _ColumnGroupings.getItems().Count;
        // number of column headers we have
        int rgsCount = this.getRowGroupings().getStaticCount();
        for (Object __dummyForeachVar13 : rm.getSortedData().Values)
        {
            // count of static row groups
            MatrixEntry ame = (MatrixEntry)__dummyForeachVar13;
            if (ame.getRowGroup() != getLastRg())
            {
                RefSupport<int> refVar___13 = new RefSupport<int>(iRow.getValue());
                RunDataRow(rpt, wc, ame, cm, matrix, _Data, refVar___13, iColumn, level + 1);
                iRow.setValue(refVar___13.getValue());
                continue;
            }
             
            saveColumn = iColumn;
            int rowcell = rgsCount == 0 ? 0 : (iRow.getValue() - headerRows) % rgsCount;
            RefSupport<int> refVar___14 = new RefSupport<int>(saveColumn);
            RunDataColumn(rpt, wc, ame, cm, matrix, _Data, iRow.getValue(), refVar___14, 0, rowcell);
            saveColumn = refVar___14.getValue();
            iRow.setValue(iRow.getValue() + 1, ReturnPreOrPostValue.POST);
        }
        // do we need to subtotal this?
        RowGrouping rg = (RowGrouping)(_RowGroupings.getItems()[level]);
        if (rg.getDynamicRows() != null && rg.getDynamicRows().getSubtotal() != null)
        {
            for (int i = 0;i < this.getCountMatrixRows();i++)
            {
                saveColumn = iColumn;
                RefSupport<int> refVar___15 = new RefSupport<int>(saveColumn);
                RunDataColumn(rpt, wc, rm, cm, matrix, _Data, iRow.getValue(), refVar___15, 0, i);
                saveColumn = refVar___15.getValue();
                iRow.setValue(iRow.getValue() + 1, ReturnPreOrPostValue.POST);
            }
        }
         
    }

    void runRowHeaders(fyiReporting.RDL.Report rpt, WorkClass wc, MatrixEntry m, MatrixCellEntry[][] matrix, Rows _Data, RefSupport<int> iRow, int iColumn, int level) throws Exception {
        for (Object __dummyForeachVar14 : m.getSortedData().Values)
        {
            MatrixEntry ame = (MatrixEntry)__dummyForeachVar14;
            matrix[iRow.getValue(), iColumn] = runGetRowHeader(rpt,ame,_Data);
            matrix[iRow.getValue(), iColumn].Height = runRowHeight(iRow.getValue());
            matrix[iRow.getValue(), iColumn].Width = ame.getRowGroup().getWidth() == null ? 0 : ame.getRowGroup().getWidth().getPoints();
            if (ame.getSortedData() != null)
            {
                RefSupport<int> refVar___16 = new RefSupport<int>(iRow.getValue());
                RunRowHeaders(rpt, wc, ame, matrix, _Data, refVar___16, iColumn + 1, level + 1);
                iRow.setValue(refVar___16.getValue());
            }
            else
                iRow.setValue(iRow.getValue() + 1, ReturnPreOrPostValue.POST); 
        }
        RowGrouping rg = (RowGrouping)(_RowGroupings.getItems()[level]);
        // do we need to subtotal this
        if (rg.getDynamicRows() != null && rg.getDynamicRows().getSubtotal() != null)
        {
            // TODO need to loop thru static??
            ReportItem ri = rg.getDynamicRows().getSubtotal().getReportItems().getItems()[0];
            matrix[iRow.getValue(), iColumn] = new MatrixCellEntry(_Data,ri);
            matrix[iRow.getValue(), iColumn].Width = rg.getWidth().getPoints();
            matrix[iRow.getValue(), iColumn].Height = runRowHeight(iRow.getValue());
            RunRowStaticHeaders(rpt, wc, matrix, _Data, iRow.getValue(), level);
            iRow.setValue(iRow.getValue() + Math.Max(1, this.getRowGroupings().getStaticCount()));
        }
         
    }

    float runRowHeight(int iRow) throws Exception {
        // calculate the height of this row
        int headerRows = _ColumnGroupings.getItems().Count;
        // number of column headers we have
        int rgsCount = this.getRowGroupings().getStaticCount();
        // count of static row groups
        int rowcell = rgsCount == 0 ? 0 : (iRow - headerRows) % rgsCount;
        MatrixRow mr = this.getMatrixRows().getItems()[rowcell] instanceof MatrixRow ? (MatrixRow)this.getMatrixRows().getItems()[rowcell] : (MatrixRow)null;
        // get height
        float height = mr.getHeight() == null ? 0 : mr.getHeight().getPoints();
        return height;
    }

    void runRowStaticHeaders(fyiReporting.RDL.Report rpt, WorkClass wc, MatrixCellEntry[][] matrix, Rows _Data, int iRow, int level) throws Exception {
        RowGrouping rg = null;
        int i = new int();
        int iColumn = 0;
        for (i = level + 1;i < _RowGroupings.getItems().Count;i++)
        {
            iColumn++;
            // Column for the row static headers
            rg = (RowGrouping)(_RowGroupings.getItems()[i]);
            if (rg.getStaticRows() != null)
                break;
             
        }
        if (rg == null || rg.getStaticRows() == null)
            return ;
         
        i = 0;
        for (Object __dummyForeachVar15 : rg.getStaticRows().getItems())
        {
            StaticRow sr = (StaticRow)__dummyForeachVar15;
            ReportItem ri = sr.getReportItems().getItems()[0];
            matrix[iRow, iColumn] = new MatrixCellEntry(_Data,ri);
            matrix[iRow, iColumn].Width = rg.getWidth().getPoints();
            MatrixRow mr = this.getMatrixRows().getItems()[i++] instanceof MatrixRow ? (MatrixRow)this.getMatrixRows().getItems()[i++] : (MatrixRow)null;
            float height = mr.getHeight() == null ? 0 : mr.getHeight().getPoints();
            matrix[iRow, iColumn].Height = height;
            iRow++;
        }
        return ;
    }

    MatrixCellEntry runGetRowHeader(fyiReporting.RDL.Report rpt, MatrixEntry me, Rows _Data) throws Exception {
        ReportItem ri;
        if (me.getRowGroup().getStaticRows() != null)
        {
            // Handle static row reference
            StaticRow sr = me.getRowGroup().getStaticRows().getItems()[me.getStaticRow()] instanceof StaticRow ? (StaticRow)me.getRowGroup().getStaticRows().getItems()[me.getStaticRow()] : (StaticRow)null;
            ri = sr.getReportItems().getItems()[0];
        }
        else
            // handle dynamic row reference
            ri = me.getRowGroup().getDynamicRows().getReportItems().getItems()[0]; 
        Rows subData = new Rows(rpt,_Data,me.getFirstRow(),me.getLastRow(),me.getRows());
        MatrixCellEntry mce = new MatrixCellEntry(subData,ri);
        return mce;
    }

    MatrixCellEntry runGetMatrixCell(fyiReporting.RDL.Report rpt, MatrixEntry me, int iRow, Rows _Data, BitArray rows, int firstRow, int lastRow) throws Exception {
        int headerRows = _ColumnGroupings.getItems().Count;
        // number of column headers we have
        int rgsCount = this.getRowGroupings().getStaticCount();
        // count of static row groups
        int rowcell = rgsCount == 0 ? 0 : (iRow - headerRows) % rgsCount;
        return runGetMatrixCell(rpt,me,rowcell,me.getStaticColumn(),_Data,rows,firstRow,lastRow);
    }

    MatrixCellEntry runGetMatrixCell(fyiReporting.RDL.Report rpt, MatrixEntry me, int rcell, int ccell, Rows _Data, BitArray rows, int firstRow, int lastRow) throws Exception {
        MatrixRow mr = this._MatrixRows.getItems()[rcell];
        MatrixCell mc = mr.getMatrixCells().getItems()[ccell];
        ReportItem ri = mc.getReportItems().getItems()[0];
        Rows subData = new Rows(rpt,_Data,firstRow,lastRow,rows);
        MatrixCellEntry mce = new MatrixCellEntry(subData,ri);
        return mce;
    }

    public Corner getCorner() throws Exception {
        return _Corner;
    }

    public void setCorner(Corner value) throws Exception {
        _Corner = value;
    }

    public ColumnGroupings getColumnGroupings() throws Exception {
        return _ColumnGroupings;
    }

    public void setColumnGroupings(ColumnGroupings value) throws Exception {
        _ColumnGroupings = value;
    }

    public Rows getMyData(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = getValue(rpt);
        return wc.Data;
    }

    public void setMyData(fyiReporting.RDL.Report rpt, Rows data) throws Exception {
        WorkClass wc = getValue(rpt);
        wc.Data = data;
    }

    public RowGroupings getRowGroupings() throws Exception {
        return _RowGroupings;
    }

    public void setRowGroupings(RowGroupings value) throws Exception {
        _RowGroupings = value;
    }

    public MatrixRows getMatrixRows() throws Exception {
        return _MatrixRows;
    }

    public void setMatrixRows(MatrixRows value) throws Exception {
        _MatrixRows = value;
    }

    public MatrixColumns getMatrixColumns() throws Exception {
        return _MatrixColumns;
    }

    public void setMatrixColumns(MatrixColumns value) throws Exception {
        _MatrixColumns = value;
    }

    public MatrixLayoutDirectionEnum getLayoutDirection() throws Exception {
        return _LayoutDirection;
    }

    public void setLayoutDirection(MatrixLayoutDirectionEnum value) throws Exception {
        _LayoutDirection = value;
    }

    public int getGroupsBeforeRowHeaders() throws Exception {
        return _GroupsBeforeRowHeaders;
    }

    public void setGroupsBeforeRowHeaders(int value) throws Exception {
        _GroupsBeforeRowHeaders = value;
    }

    public String getCellDataElementName() throws Exception {
        return _CellDataElementName;
    }

    public void setCellDataElementName(String value) throws Exception {
        _CellDataElementName = value;
    }

    private void setGroupingValues(fyiReporting.RDL.Report rpt, MatrixCellEntry mce) throws Exception {
        WorkClass wc = getValue(rpt);
        Rows data = wc.FullData;
        setGroupingValuesInit(rpt,data,mce.getRowME(),mce.getColumnME());
        setGroupingValuesMe(rpt,data,mce.getRowME());
        setGroupingValuesMe(rpt,data,mce.getColumnME());
        return ;
    }

    private void setGroupingValuesInit(fyiReporting.RDL.Report rpt, Rows data, MatrixEntry rme, MatrixEntry cme) throws Exception {
        // handle the column grouping
        if (cme != null)
        {
            for (Object __dummyForeachVar16 : this.getColumnGroupings().getItems())
            {
                ColumnGrouping cg = (ColumnGrouping)__dummyForeachVar16;
                if (cg.getDynamicColumns() != null)
                    setGrouping(rpt,cg.getDynamicColumns().getGrouping(),cme,data);
                 
            }
        }
         
        // handle the row grouping
        if (rme != null)
        {
            for (Object __dummyForeachVar17 : this.getRowGroupings().getItems())
            {
                RowGrouping rg = (RowGrouping)__dummyForeachVar17;
                if (rg.getDynamicRows() != null)
                    setGrouping(rpt,rg.getDynamicRows().getGrouping(),rme,data);
                 
            }
        }
         
    }

    private void setGroupingValuesMe(fyiReporting.RDL.Report rpt, Rows data, MatrixEntry me) throws Exception {
        if (me == null)
            return ;
         
        // handle the column grouping
        if (me.getColumnGroup() != null && me.getColumnGroup().getDynamicColumns() != null)
            setGrouping(rpt,me.getColumnGroup().getDynamicColumns().getGrouping(),me,data);
         
        // handle the row grouping
        if (me.getRowGroup() != null && me.getRowGroup().getDynamicRows() != null)
            setGrouping(rpt,me.getRowGroup().getDynamicRows().getGrouping(),me,data);
         
        if (me.getParent() != null)
            // go up the tree??
            setGroupingValuesMe(rpt,data,me.getParent());
         
    }

    private void setGrouping(fyiReporting.RDL.Report rpt, Grouping g, MatrixEntry me, Rows data) throws Exception {
        if (g == null)
            return ;
         
        if (me.getData() == null)
            me.setData(new Rows(rpt,data,me.getFirstRow(),me.getLastRow(),me.getRows()));
         
        g.setRows(rpt,me.getData());
    }

    public MatrixCellDataElementOutputEnum getCellDataElementOutput() throws Exception {
        return _CellDataElementOutput;
    }

    public void setCellDataElementOutput(MatrixCellDataElementOutputEnum value) throws Exception {
        _CellDataElementOutput = value;
    }

    private WorkClass getValue(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(this,"wc") instanceof WorkClass ? (WorkClass)rpt.getCache().get(this,"wc") : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(this,"wc",wc);
        }
         
        return wc;
    }

    private void removeValue(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(this,"wc");
    }

    static class WorkClass   
    {
        public Rows Data;
        public Rows FullData;
        public WorkClass() throws Exception {
            Data = null;
            FullData = null;
        }
    
    }

}


