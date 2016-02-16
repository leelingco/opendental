//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.Row;
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
* A Row in a data set.
*/
public class Row   
{
    int _RowNumber = new int();
    // Original row #
    int _Level = new int();
    // Usually 0; set when row is part of group with ParentGroup (ie recursive hierarchy)
    GroupEntry _GroupEntry;
    //   like level;
    Rows _R;
    // Owner of row collection
    Object[] _Data = new Object[]();
    // Row of data
    public Row(Rows r, Row rd) throws Exception {
        // Constructor that uses existing Row data
        _R = r;
        _Data = rd.getData();
        _Level = rd.getLevel();
    }

    public Row(Rows r, int columnCount) throws Exception {
        _R = r;
        _Data = new Object[columnCount];
        _Level = 0;
    }

    public Object[] getData() throws Exception {
        return _Data;
    }

    public void setData(Object[] value) throws Exception {
        _Data = value;
    }

    public Rows getR() throws Exception {
        return _R;
    }

    public void setR(Rows value) throws Exception {
        _R = value;
    }

    public GroupEntry getGroupEntry() throws Exception {
        return _GroupEntry;
    }

    public void setGroupEntry(GroupEntry value) throws Exception {
        _GroupEntry = value;
    }

    public int getLevel() throws Exception {
        return _Level;
    }

    public void setLevel(int value) throws Exception {
        _Level = value;
    }

    public int getRowNumber() throws Exception {
        return _RowNumber;
    }

    public void setRowNumber(int value) throws Exception {
        _RowNumber = value;
    }

}


