//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MatrixEntry;
import fyiReporting.RDL.ReportItem;
import fyiReporting.RDL.Rows;

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
* Runtime data structure representing the group hierarchy
*/
public class MatrixCellEntry   
{
    Rows _Data;
    // Rows matching this cell entry
    ReportItem _DisplayItem;
    // Report item to display in report
    double _Value = double.MinValue;
    // used by Charts to optimize data request
    float _XPosition = new float();
    // position of cell
    float _Height = new float();
    // height of cell
    float _Width = new float();
    // width of cell
    MatrixEntry _RowME;
    // MatrixEntry for the row that made cell entry
    MatrixEntry _ColumnME;
    // MatrixEntry for the column that made cell entry
    int _ColSpan = new int();
    // # of columns spanned
    public MatrixCellEntry(Rows d, ReportItem ri) throws Exception {
        _Data = d;
        _DisplayItem = ri;
        _ColSpan = 1;
    }

    public Rows getData() throws Exception {
        return _Data;
    }

    public int getColSpan() throws Exception {
        return _ColSpan;
    }

    public void setColSpan(int value) throws Exception {
        _ColSpan = value;
    }

    public ReportItem getDisplayItem() throws Exception {
        return _DisplayItem;
    }

    public float getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(float value) throws Exception {
        _Height = value;
    }

    public MatrixEntry getColumnME() throws Exception {
        return _ColumnME;
    }

    public void setColumnME(MatrixEntry value) throws Exception {
        _ColumnME = value;
    }

    public MatrixEntry getRowME() throws Exception {
        return _RowME;
    }

    public void setRowME(MatrixEntry value) throws Exception {
        _RowME = value;
    }

    public double getValue() throws Exception {
        return _Value;
    }

    public void setValue(double value) throws Exception {
        _Value = value;
    }

    public float getWidth() throws Exception {
        return _Width;
    }

    public void setWidth(float value) throws Exception {
        _Width = value;
    }

    public float getXPosition() throws Exception {
        return _XPosition;
    }

    public void setXPosition(float value) throws Exception {
        _XPosition = value;
    }

}


