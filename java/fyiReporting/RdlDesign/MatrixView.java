//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:20 PM
//

package fyiReporting.RdlDesign;

import CS2JNet.System.StringSupport;
import fyiReporting.RdlDesign.DesignXmlDraw;
import fyiReporting.RdlDesign.MatrixItem;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* MatrixView:  builds a simplfied representation of the matrix so that it
* can be drawn or hit test in a simplified fashion.
*/
public class MatrixView   
{
    DesignXmlDraw _Draw;
    XmlNode _MatrixNode = new XmlNode();
    int _Rows = new int();
    int _HeaderRows = new int();
    int _Columns = new int();
    int _HeaderColumns = new int();
    float _Height = new float();
    float _Width = new float();
    MatrixItem[][] _MatrixView = new MatrixItem[][]();
    String _ViewBuilt = null;
    public MatrixView(DesignXmlDraw dxDraw, XmlNode matrix) throws Exception {
        _Draw = dxDraw;
        _MatrixNode = matrix;
        try
        {
            buildView();
        }
        catch (Exception e)
        {
            _ViewBuilt = e.Message;
        }
    
    }

    public MatrixItem get___idx(int row, int column) throws Exception {
        return _MatrixView[row, column];
    }

    public int getColumns() throws Exception {
        return _Columns;
    }

    public int getRows() throws Exception {
        return _Rows;
    }

    public int getHeaderColumns() throws Exception {
        return _HeaderColumns;
    }

    public int getHeaderRows() throws Exception {
        return _HeaderRows;
    }

    public float getHeight() throws Exception {
        return _Height;
    }

    public float getWidth() throws Exception {
        return _Width;
    }

    void buildView() throws Exception {
        countRowColumns();
        // get the total count of rows and columns
        _MatrixView = new MatrixItem[_Rows, _Columns];
        // allocate the 2-dimensional array
        fillMatrix();
    }

    void countRowColumns() throws Exception {
        int mcc = countMatrixColumns();
        int mrc = countMatrixRows();
        int iColumnGroupings = this.countColumnGroupings();
        int iRowGroupings = this.countRowGroupings();
        _Rows = mrc + this.countColumnGroupings() + countRowGroupingSubtotals() * mrc;
        _Columns = mcc + iRowGroupings + countColumnGroupingSubtotals() * mcc;
        _HeaderRows = iColumnGroupings;
        _HeaderColumns = iRowGroupings;
    }

    void fillMatrix() throws Exception {
        fillMatrixColumnGroupings();
        fillMatrixRowGroupings();
        fillMatrixCorner();
        fillMatrixCells();
        fillMatrixHeights();
        fillMatrixWidths();
        fillMatrixCornerHeightWidth();
    }

    void fillMatrixHeights() throws Exception {
        // fill out the heights for each row
        this._Height = 0;
        for (int row = 0;row < this.getRows();row++)
        {
            float height = 0;
            for (int col = 0;col < this.getColumns();col++)
            {
                MatrixItem mi = _MatrixView[row, col];
                height = Math.Max(height, mi.getHeight());
            }
            for (int col = 0;col < this.getColumns();col++)
                _MatrixView[row, col].Height = height;
            this._Height += height;
        }
    }

    void fillMatrixWidths() throws Exception {
        // fill out the widths for each column
        this._Width = 0;
        for (int col = 0;col < this.getColumns();col++)
        {
            float width = 0;
            for (int row = 0;row < this.getRows();row++)
            {
                MatrixItem mi = _MatrixView[row, col];
                width = Math.Max(width, mi.getWidth());
            }
            for (int row = 0;row < this.getRows();row++)
                _MatrixView[row, col].Width = width;
            this._Width += width;
        }
    }

    void fillMatrixCornerHeightWidth() throws Exception {
        if (this.getColumns() == 0 || this.getRows() == 0)
            return ;
         
        // set the height and width for the corner
        MatrixItem mi = _MatrixView[0, 0];
        mi.setHeight(0);
        for (int row = 0;row < this._HeaderRows;row++)
            mi.setHeight(mi.getHeight() + _MatrixView[row, 1].Height);
        mi.setWidth(0);
        for (int col = 0;col < this._HeaderColumns;col++)
            mi.setWidth(mi.getWidth() + _MatrixView[1, col].Width);
    }

    void fillMatrixCells() throws Exception {
        // get a collection with the matrix cells
        int staticRows = this.countMatrixRows();
        int staticCols = this.countMatrixColumns();
        XmlNode[][] rc = new XmlNode[staticRows, staticCols];
        XmlNode mrows = DesignXmlDraw.findNextInHierarchy(_MatrixNode,"MatrixRows");
        int ri = 0;
        for (Object __dummyForeachVar1 : mrows.ChildNodes)
        {
            XmlNode mrow = (XmlNode)__dummyForeachVar1;
            int ci = 0;
            XmlNode mcells = DesignXmlDraw.findNextInHierarchy(mrow,"MatrixCells");
            for (Object __dummyForeachVar0 : mcells.ChildNodes)
            {
                XmlNode mcell = (XmlNode)__dummyForeachVar0;
                // obtain the matrix cell
                XmlNode repi = DesignXmlDraw.findNextInHierarchy(mcell,"ReportItems");
                rc[ri, ci] = repi;
                ci++;
            }
            ri++;
        }
        // now fill out the rest of the matrix with empty entries
        MatrixItem mi;
        for (int row = _HeaderRows;row < this.getRows();row++)
        {
            // Fill the middle (MatrixCells) with the contents of MatrixCells repeated
            int rowcell = staticRows == 0 ? 0 : (row - _HeaderRows) % staticRows;
            int mcellCount = 0;
            for (int col = _HeaderColumns;col < this.getColumns();col++)
            {
                if (_MatrixView[row, col] == null)
                {
                    float width = getMatrixColumnWidth(mcellCount);
                    float height = getMatrixRowHeight(rowcell);
                    XmlNode n = rc[rowcell, mcellCount++] instanceof XmlNode ? (XmlNode)rc[rowcell, mcellCount++] : (XmlNode)null;
                    if (mcellCount >= staticCols)
                        mcellCount = 0;
                     
                    mi = new MatrixItem(n);
                    mi.setWidth(width);
                    mi.setHeight(height);
                    _MatrixView[row, col] = mi;
                }
                 
            }
        }
        for (int row = 0;row < this.getRows();row++)
        {
            for (int col = 0;col < this.getColumns();col++)
            {
                // Make sure we have no null entries
                if (_MatrixView[row, col] == null)
                {
                    mi = new MatrixItem(null);
                    _MatrixView[row, col] = mi;
                }
                 
            }
        }
    }

    void fillMatrixCorner() throws Exception {
        XmlNode corner = _Draw.getNamedChildNode(_MatrixNode,"Corner");
        if (corner == null)
            return ;
         
        XmlNode ris = DesignXmlDraw.findNextInHierarchy(corner,"ReportItems");
        MatrixItem mi = new MatrixItem(ris);
        _MatrixView[0, 0] = mi;
    }

    float getMatrixColumnWidth(int count) throws Exception {
        XmlNode mcs = DesignXmlDraw.findNextInHierarchy(_MatrixNode,"MatrixColumns");
        for (Object __dummyForeachVar2 : mcs.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar2;
            if (!StringSupport.equals(c.Name, "MatrixColumn"))
                continue;
             
            if (count == 0)
                return _Draw.getSize(c,"Width");
             
            count--;
        }
        return 0;
    }

    void fillMatrixColumnGroupings() throws Exception {
        XmlNode cGroupings = _Draw.getNamedChildNode(_MatrixNode,"ColumnGroupings");
        if (cGroupings == null)
            return ;
         
        int rows = 0;
        int cols = this._HeaderColumns;
        MatrixItem mi;
        XmlNode ris = new XmlNode();
        // work variable to hold reportitems
        int staticCols = this.countMatrixColumns();
        int subTotalCols = DesignXmlDraw.countChildren(cGroupings,"ColumnGrouping","DynamicColumns","Subtotal");
        for (Object __dummyForeachVar4 : cGroupings.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar4;
            if (!StringSupport.equals(c.Name, "ColumnGrouping"))
                continue;
             
            XmlNode scol = DesignXmlDraw.findNextInHierarchy(c,"StaticColumns");
            if (scol != null)
            {
                // Static columns
                int ci = 0;
                for (Object __dummyForeachVar3 : scol.ChildNodes)
                {
                    XmlNode sc = (XmlNode)__dummyForeachVar3;
                    if (!StringSupport.equals(sc.Name, "StaticColumn"))
                        continue;
                     
                    ris = DesignXmlDraw.findNextInHierarchy(sc,"ReportItems");
                    mi = new MatrixItem(ris);
                    mi.setHeight(_Draw.getSize(c,"Height"));
                    mi.setWidth(getMatrixColumnWidth(ci));
                    _MatrixView[rows, _HeaderColumns + ci] = mi;
                    ci++;
                }
            }
            else
            {
                // Dynamic Columns
                ris = DesignXmlDraw.findNextInHierarchy(c,"DynamicColumns","ReportItems");
                mi = new MatrixItem(ris);
                mi.setHeight(_Draw.getSize(c,"Height"));
                mi.setWidth(getMatrixColumnWidth(0));
                _MatrixView[rows, _HeaderColumns] = mi;
                XmlNode subtotal = DesignXmlDraw.findNextInHierarchy(c,"DynamicColumns","Subtotal");
                if (subtotal != null)
                {
                    ris = DesignXmlDraw.findNextInHierarchy(subtotal,"ReportItems");
                    mi = new MatrixItem(ris);
                    mi.setHeight(_Draw.getSize(c,"Height"));
                    mi.setWidth(getMatrixColumnWidth(0));
                    // TODO this is wrong!! should be total of all static widths
                    _MatrixView[rows, _HeaderColumns + (staticCols - 1) + subTotalCols] = mi;
                    subTotalCols--;
                }
                 
            } 
            rows++;
        }
    }

    // add a row per ColumnGrouping
    float getMatrixRowHeight(int count) throws Exception {
        XmlNode mcs = DesignXmlDraw.findNextInHierarchy(_MatrixNode,"MatrixRows");
        for (Object __dummyForeachVar5 : mcs.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar5;
            if (!StringSupport.equals(c.Name, "MatrixRow"))
                continue;
             
            if (count == 0)
                return _Draw.getSize(c,"Height");
             
            count--;
        }
        return 0;
    }

    void fillMatrixRowGroupings() throws Exception {
        XmlNode rGroupings = _Draw.getNamedChildNode(_MatrixNode,"RowGroupings");
        if (rGroupings == null)
            return ;
         
        float height = _Draw.getSize(DesignXmlDraw.findNextInHierarchy(_MatrixNode,"MatrixRows","MatrixRow"),"Height");
        int cols = 0;
        int staticRows = this.countMatrixRows();
        int subtotalrows = DesignXmlDraw.countChildren(rGroupings,"RowGrouping","DynamicRows","Subtotal");
        MatrixItem mi;
        for (Object __dummyForeachVar7 : rGroupings.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar7;
            if (!StringSupport.equals(c.Name, "RowGrouping"))
                continue;
             
            XmlNode srow = DesignXmlDraw.findNextInHierarchy(c,"StaticRows");
            if (srow != null)
            {
                // Static rows
                int ri = 0;
                for (Object __dummyForeachVar6 : srow.ChildNodes)
                {
                    XmlNode sr = (XmlNode)__dummyForeachVar6;
                    if (!StringSupport.equals(sr.Name, "StaticRow"))
                        continue;
                     
                    XmlNode ris = DesignXmlDraw.findNextInHierarchy(sr,"ReportItems");
                    mi = new MatrixItem(ris);
                    mi.setWidth(_Draw.getSize(c,"Width"));
                    mi.setHeight(getMatrixRowHeight(ri));
                    _MatrixView[_HeaderRows + ri, cols] = mi;
                    ri++;
                }
            }
            else
            {
                XmlNode ris = DesignXmlDraw.findNextInHierarchy(c,"DynamicRows","ReportItems");
                mi = new MatrixItem(ris);
                mi.setWidth(_Draw.getSize(c,"Width"));
                mi.setHeight(height);
                _MatrixView[_HeaderRows, cols] = mi;
                XmlNode subtotal = DesignXmlDraw.findNextInHierarchy(c,"DynamicRows","Subtotal");
                if (subtotal != null)
                {
                    ris = DesignXmlDraw.findNextInHierarchy(subtotal,"ReportItems");
                    mi = new MatrixItem(ris);
                    mi.setWidth(_Draw.getSize(c,"Width"));
                    mi.setHeight(height);
                    _MatrixView[_HeaderRows + (staticRows - 1) + subtotalrows, cols] = mi;
                    subtotalrows--;
                }
                 
            } 
            // these go backwards
            cols++;
        }
    }

    // add a column per RowGrouping
    /**
    * Returns the count of static columns or 1
    * 
    *  @return
    */
    int countMatrixColumns() throws Exception {
        XmlNode cGroupings = _Draw.getNamedChildNode(_MatrixNode,"ColumnGroupings");
        if (cGroupings == null)
            return 1;
         
        for (Object __dummyForeachVar9 : cGroupings.ChildNodes)
        {
            // 1 column
            // Get the number of static columns
            XmlNode c = (XmlNode)__dummyForeachVar9;
            if (!StringSupport.equals(c.Name, "ColumnGrouping"))
                continue;
             
            XmlNode scol = DesignXmlDraw.findNextInHierarchy(c,"StaticColumns");
            if (scol == null)
                continue;
             
            // must be dynamic column
            int ci = 0;
            for (Object __dummyForeachVar8 : scol.ChildNodes)
            {
                XmlNode sc = (XmlNode)__dummyForeachVar8;
                if (StringSupport.equals(sc.Name, "StaticColumn"))
                    ci++;
                 
            }
            return ci;
        }
        return 1;
    }

    // only one StaticColumns allowed in a column grouping
    // 1 column
    /**
    * Returns the count of static rows or 1
    * 
    *  @return
    */
    int countMatrixRows() throws Exception {
        XmlNode rGroupings = _Draw.getNamedChildNode(_MatrixNode,"RowGroupings");
        if (rGroupings == null)
            return 1;
         
        for (Object __dummyForeachVar11 : rGroupings.ChildNodes)
        {
            // 1 row
            // Get the number of static columns
            XmlNode c = (XmlNode)__dummyForeachVar11;
            if (!StringSupport.equals(c.Name, "RowGrouping"))
                continue;
             
            XmlNode scol = DesignXmlDraw.findNextInHierarchy(c,"StaticRows");
            if (scol == null)
                continue;
             
            // must be dynamic column
            int ci = 0;
            for (Object __dummyForeachVar10 : scol.ChildNodes)
            {
                XmlNode sc = (XmlNode)__dummyForeachVar10;
                if (StringSupport.equals(sc.Name, "StaticRow"))
                    ci++;
                 
            }
            return ci;
        }
        return 1;
    }

    // only one StaticRows allowed in a row grouping
    // 1 row
    /**
    * Returns the count of ColumnGroupings
    * 
    *  @return
    */
    int countColumnGroupings() throws Exception {
        XmlNode cGroupings = _Draw.getNamedChildNode(_MatrixNode,"ColumnGroupings");
        if (cGroupings == null)
            return 0;
         
        // Get the number of column groups
        int ci = 0;
        for (Object __dummyForeachVar12 : cGroupings.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar12;
            if (!StringSupport.equals(c.Name, "ColumnGrouping"))
                continue;
             
            ci++;
        }
        return ci;
    }

    /**
    * Returns the count of row grouping
    * 
    *  @return
    */
    int countRowGroupings() throws Exception {
        XmlNode rGroupings = _Draw.getNamedChildNode(_MatrixNode,"RowGroupings");
        if (rGroupings == null)
            return 0;
         
        // 1 row
        // Get the number of row groupings
        int ri = 0;
        for (Object __dummyForeachVar13 : rGroupings.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar13;
            if (!StringSupport.equals(c.Name, "RowGrouping"))
                continue;
             
            ri++;
        }
        return ri;
    }

    // row groupings
    /**
    * Returns the count of ColumnGroupings with subtotals
    * 
    *  @return
    */
    int countColumnGroupingSubtotals() throws Exception {
        XmlNode cGroupings = _Draw.getNamedChildNode(_MatrixNode,"ColumnGroupings");
        if (cGroupings == null)
            return 0;
         
        // Get the number of column groups with subtotals
        int ci = 0;
        for (Object __dummyForeachVar14 : cGroupings.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar14;
            if (!StringSupport.equals(c.Name, "ColumnGrouping"))
                continue;
             
            XmlNode subtotal = DesignXmlDraw.findNextInHierarchy(c,"DynamicColumns","Subtotal");
            if (subtotal != null)
                ci++;
             
        }
        return ci;
    }

    /**
    * Returns the count of row grouping subtotals
    * 
    *  @return
    */
    int countRowGroupingSubtotals() throws Exception {
        XmlNode rGroupings = _Draw.getNamedChildNode(_MatrixNode,"RowGroupings");
        if (rGroupings == null)
            return 0;
         
        // 1 row
        // Get the number of row groupings
        int ri = 0;
        for (Object __dummyForeachVar15 : rGroupings.ChildNodes)
        {
            XmlNode c = (XmlNode)__dummyForeachVar15;
            if (!StringSupport.equals(c.Name, "RowGrouping"))
                continue;
             
            XmlNode subtotal = DesignXmlDraw.findNextInHierarchy(c,"DynamicRows","Subtotal");
            if (subtotal != null)
                ri++;
             
        }
        return ri;
    }

}


