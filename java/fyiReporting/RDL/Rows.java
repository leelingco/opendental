//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Filter;
import fyiReporting.RDL.GroupEntry;
import fyiReporting.RDL.GroupExpression;
import fyiReporting.RDL.Grouping;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.Rows;
import fyiReporting.RDL.RowsSortExpression;
import fyiReporting.RDL.SortBy;
import fyiReporting.RDL.SortDirectionEnum;
import fyiReporting.RDL.Sorting;
import fyiReporting.RDL.TableGroup;
import fyiReporting.RDL.TableGroups;

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
* A collection of rows.
*/
public class Rows  extends System.Collections.Generic.IComparer<Row> 
{
    List<Row> _Data = new List<Row>();
    // array of Row object;
    List<RowsSortExpression> _SortBy = new List<RowsSortExpression>();
    // array of expressions used to sort the data
    GroupEntry[] _CurrentGroups = new GroupEntry[]();
    // group
    fyiReporting.RDL.Report _Rpt;
    public Rows(fyiReporting.RDL.Report rpt) throws Exception {
        _Rpt = rpt;
        _SortBy = null;
        _CurrentGroups = null;
    }

    // Constructor that takes existing Rows; a start, end and bitArray with the rows wanted
    public Rows(fyiReporting.RDL.Report rpt, Rows r, int start, int end, BitArray ba) throws Exception {
        _Rpt = rpt;
        _SortBy = null;
        _CurrentGroups = null;
        if (end - start < 0)
        {
            // null set?
            _Data = new List<Row>(1);
            _Data.TrimExcess();
            return ;
        }
         
        _Data = new List<Row>(end - start + 1);
        for (int iRow = start;iRow <= end;iRow++)
        {
            if (ba == null || ba.Get(iRow))
            {
                Row or = r.getData()[iRow];
                Row nr = new Row(this,or);
                nr.setRowNumber(or.getRowNumber());
                _Data.Add(nr);
            }
             
        }
        _Data.TrimExcess();
    }

    // Constructor that takes existing Rows
    public Rows(fyiReporting.RDL.Report rpt, Rows r) throws Exception {
        _Rpt = rpt;
        _SortBy = null;
        _CurrentGroups = null;
        if (r.getData() == null || r.getData().Count <= 0)
        {
            // null set?
            _Data = new List<Row>(1);
            _Data.TrimExcess();
            return ;
        }
         
        _Data = new List<Row>(r.getData().Count);
        for (int iRow = 0;iRow < r.getData().Count;iRow++)
        {
            Row or = r.getData()[iRow];
            Row nr = new Row(this,or);
            nr.setRowNumber(or.getRowNumber());
            _Data.Add(nr);
        }
        _Data.TrimExcess();
    }

    public Rows(fyiReporting.RDL.Report rpt, TableGroups tg, Grouping g, Sorting s) throws Exception {
        _Rpt = rpt;
        _SortBy = new List<RowsSortExpression>();
        // Pull all the sort expression together
        if (tg != null)
        {
            for (Object __dummyForeachVar1 : tg.getItems())
            {
                TableGroup t = (TableGroup)__dummyForeachVar1;
                for (Object __dummyForeachVar0 : t.getGrouping().getGroupExpressions().getItems())
                {
                    GroupExpression ge = (GroupExpression)__dummyForeachVar0;
                    _SortBy.Add(new RowsSortExpression(ge.getExpression()));
                }
            }
        }
         
        // TODO what to do with the sort expressions!!!!
        if (g != null)
        {
            if (g.getParentGroup() != null)
                _SortBy.Add(new RowsSortExpression(g.getParentGroup()));
            else if (g.getGroupExpressions() != null)
            {
                for (Object __dummyForeachVar2 : g.getGroupExpressions().getItems())
                {
                    GroupExpression ge = (GroupExpression)__dummyForeachVar2;
                    _SortBy.Add(new RowsSortExpression(ge.getExpression()));
                }
            }
              
        }
         
        if (s != null)
        {
            for (Object __dummyForeachVar3 : s.getItems())
            {
                SortBy sb = (SortBy)__dummyForeachVar3;
                _SortBy.Add(new RowsSortExpression(sb.getSortExpression(),sb.getDirection() == SortDirectionEnum.Ascending));
            }
        }
         
        if (_SortBy.Count > 0)
            _SortBy.TrimExcess();
        else
            _SortBy = null; 
    }

    public fyiReporting.RDL.Report getReport() throws Exception {
        return this._Rpt;
    }

    public void sort() throws Exception {
        // sort the data array by the data.
        _Data.Sort(this);
    }

    public List<Row> getData() throws Exception {
        return _Data;
    }

    public void setData(List<Row> value) throws Exception {
        _Data = value;
        for (Object __dummyForeachVar4 : _Data)
        {
            // Assign the new value
            Row r = (Row)__dummyForeachVar4;
            // Updata all rows
            r.setR(this);
        }
    }

    public List<RowsSortExpression> getSortBy() throws Exception {
        return _SortBy;
    }

    public void setSortBy(List<RowsSortExpression> value) throws Exception {
        _SortBy = value;
    }

    public GroupEntry[] getCurrentGroups() throws Exception {
        return _CurrentGroups;
    }

    public void setCurrentGroups(GroupEntry[] value) throws Exception {
        _CurrentGroups = value;
    }

    public int compare(Row r1, Row r2) throws Exception {
        if (r1 == r2)
            return 0;
         
        // why does the sort routine do this??
        Object o1 = null, o2 = null;
        TypeCode tc = TypeCode.Object;
        int rc = new int();
        try
        {
            for (Object __dummyForeachVar5 : _SortBy)
            {
                RowsSortExpression se = (RowsSortExpression)__dummyForeachVar5;
                o1 = se.expr.evaluate(this._Rpt,r1);
                o2 = se.expr.evaluate(this._Rpt,r2);
                tc = se.expr.getTypeCode();
                rc = Filter.applyCompare(tc,o1,o2);
                if (rc != 0)
                    return se.bAscending ? rc : -rc;
                 
            }
        }
        catch (Exception e)
        {
            // this really shouldn't happen
            _Rpt.rl.LogError(8, String.Format("Sort rows exception\r\nArguments: {0} {1}\r\nTypecode: {2}\r\n{3}\r\n{4}", o1, o2, tc.ToString(), e.Message, e.StackTrace));
        }

        return r1.getRowNumber() - r2.getRowNumber();
    }

}


