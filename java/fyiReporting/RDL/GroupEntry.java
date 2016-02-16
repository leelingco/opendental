//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.FunctionField;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.GroupExpression;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.SortBy;
import fyiReporting.RDL.SortDirectionEnum;
import fyiReporting.RDL.Sorting;

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
public class GroupEntry   
{
    Grouping _Group;
    // Group this represents
    Sorting _Sort;
    // Sort cooresponding to this group
    int _StartRow = new int();
    // Starting row of the group (inclusive)
    int _EndRow = new int();
    // Endding row of the group (inclusive)
    List<GroupEntry> _NestedGroup = new List<GroupEntry>();
    // group one hierarchy below
    public GroupEntry(Grouping g, Sorting s, int start) throws Exception {
        _Group = g;
        _Sort = s;
        _StartRow = start;
        _EndRow = -1;
        _NestedGroup = new List<GroupEntry>();
        // Check to see if grouping and sorting are the same
        if (g == null || s == null)
            return ;
         
        // nothing to check if either is null
        if (s.getItems().Count != g.getGroupExpressions().getItems().Count)
            return ;
         
        for (int i = 0;i < s.getItems().Count;i++)
        {
            SortBy sb = s.getItems()[i] instanceof SortBy ? (SortBy)s.getItems()[i] : (SortBy)null;
            if (sb.getDirection() == SortDirectionEnum.Descending)
                return ;
             
            // TODO we could optimize this
            FunctionField ff = sb.getSortExpression().getExpr() instanceof FunctionField ? (FunctionField)sb.getSortExpression().getExpr() : (FunctionField)null;
            if (ff == null || ff.getTypeCode() != TypeCode.String)
                return ;
             
            GroupExpression ge = g.getGroupExpressions().getItems()[i] instanceof GroupExpression ? (GroupExpression)g.getGroupExpressions().getItems()[i] : (GroupExpression)null;
            FunctionField ff2 = ge.getExpression().getExpr() instanceof FunctionField ? (FunctionField)ge.getExpression().getExpr() : (FunctionField)null;
            if (ff2 == null || ff.getFld() != ff2.getFld())
                return ;
             
        }
        _Sort = null;
    }

    // we won't need to sort since the groupby will handle it correctly
    public int getStartRow() throws Exception {
        return _StartRow;
    }

    public void setStartRow(int value) throws Exception {
        _StartRow = value;
    }

    public int getEndRow() throws Exception {
        return _EndRow;
    }

    public void setEndRow(int value) throws Exception {
        _EndRow = value;
    }

    public List<GroupEntry> getNestedGroup() throws Exception {
        return _NestedGroup;
    }

    public void setNestedGroup(List<GroupEntry> value) throws Exception {
        _NestedGroup = value;
    }

    public Grouping getGroup() throws Exception {
        return _Group;
    }

    public Sorting getSort() throws Exception {
        return _Sort;
    }

}


