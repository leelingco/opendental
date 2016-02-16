//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IPresent;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.TableColumn;

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
* TableColumns definition and processing.
*/
public class TableColumns  extends ReportLink 
{
    List<TableColumn> _Items = new List<TableColumn>();
    // list of TableColumn
    public TableColumns(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        TableColumn tc;
        _Items = new List<TableColumn>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("TableColumn"))
            {
                tc = new TableColumn(r,this,xNodeLoop);
            }
            else
            {
                tc = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown TableColumns element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (tc != null)
                _Items.Add(tc);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"For TableColumns at least one TableColumn is required.");
        else
            _Items.TrimExcess(); 
    }

    public TableColumn get___idx(int ci) throws Exception {
        return _Items[ci] instanceof TableColumn ? (TableColumn)_Items[ci] : (TableColumn)null;
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            TableColumn tc = (TableColumn)__dummyForeachVar1;
            tc.finalPass();
        }
        return ;
    }

    public void run(IPresent ip, Row row) throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            TableColumn tc = (TableColumn)__dummyForeachVar2;
            tc.run(ip,row);
        }
        return ;
    }

    // calculate the XPositions of all the columns
    public void calculateXPositions(fyiReporting.RDL.Report rpt, float startpos, Row row) throws Exception {
        float x = startpos;
        for (Object __dummyForeachVar3 : _Items)
        {
            TableColumn tc = (TableColumn)__dummyForeachVar3;
            if (tc.isHidden(rpt,row))
                continue;
             
            tc.setXPosition(rpt,x);
            x += tc.getWidth().getPoints();
        }
        return ;
    }

    public List<TableColumn> getItems() throws Exception {
        return _Items;
    }

}


