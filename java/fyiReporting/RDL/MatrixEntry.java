//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ColumnGrouping;
import fyiReporting.RDL.MatrixEntry;
import fyiReporting.RDL.RowGrouping;
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
public class MatrixEntry   
{
    Hashtable _HashData = new Hashtable();
    // Hash table of data values
    SortedList _SortedData = new SortedList();
    //  SortedList version of the data
    BitArray _Rows = new BitArray();
    // rows
    MatrixEntry _Parent;
    // parent
    ColumnGrouping _ColumnGroup;
    //   Column grouping
    RowGrouping _RowGroup;
    // Row grouping
    int _FirstRow = new int();
    // First row in _Rows marked true
    int _LastRow = new int();
    // Last row in _Rows marked true
    int _rowCount = new int();
    //   we save the rowCount so we can delay creating bitArray
    int _StaticColumn = 0;
    // this is the index to which column to use (always 0 when dynamic)
    int _StaticRow = 0;
    // this is the index to which row to use (always 0 when dynamic)
    Rows _Data;
    // set dynamically when needed
    public MatrixEntry(MatrixEntry p, int rowCount) throws Exception {
        _HashData = new Hashtable();
        _ColumnGroup = null;
        _RowGroup = null;
        _SortedData = null;
        _Data = null;
        _rowCount = rowCount;
        _Rows = null;
        _Parent = p;
        _FirstRow = -1;
        _LastRow = -1;
    }

    public Hashtable getHashData() throws Exception {
        return _HashData;
    }

    public Rows getData() throws Exception {
        return _Data;
    }

    public void setData(Rows value) throws Exception {
        _Data = value;
    }

    public SortedList getSortedData() throws Exception {
        if (_SortedData == null && _HashData != null)
        {
            if (_HashData.Count > 0)
                _SortedData = new SortedList(_HashData);
             
            // TODO provide comparer
            _HashData = null;
        }
         
        return _SortedData;
    }

    // we only keep one
    public MatrixEntry getParent() throws Exception {
        return _Parent;
    }

    public ColumnGrouping getColumnGroup() throws Exception {
        return _ColumnGroup;
    }

    public void setColumnGroup(ColumnGrouping value) throws Exception {
        _ColumnGroup = value;
    }

    public int getStaticRow() throws Exception {
        return _StaticRow;
    }

    public void setStaticRow(int value) throws Exception {
        _StaticRow = value;
    }

    public int getStaticColumn() throws Exception {
        return _StaticColumn;
    }

    public void setStaticColumn(int value) throws Exception {
        _StaticColumn = value;
    }

    public RowGrouping getRowGroup() throws Exception {
        return _RowGroup;
    }

    public void setRowGroup(RowGrouping value) throws Exception {
        _RowGroup = value;
    }

    public int getFirstRow() throws Exception {
        return _FirstRow;
    }

    public void setFirstRow(int value) throws Exception {
        if (_FirstRow == -1)
            _FirstRow = value;
         
    }

    public int getLastRow() throws Exception {
        return _LastRow;
    }

    public void setLastRow(int value) throws Exception {
        if (value >= _LastRow)
            _LastRow = value;
         
    }

    public BitArray getRows() throws Exception {
        if (_Rows == null)
            _Rows = new BitArray(_rowCount);
         
        return _Rows;
    }

    public void setRows(BitArray value) throws Exception {
        _Rows = value;
    }

}


