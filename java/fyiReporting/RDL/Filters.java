//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Filter;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
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
* Collection of Filters for a DataSet.
*/
public class Filters  extends ReportLink 
{
    List<Filter> _Items = new List<Filter>();
    // list of Filter
    public Filters(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        Filter f;
        _Items = new List<Filter>();
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Filter"))
            {
                f = new Filter(r,this,xNodeLoop);
            }
            else
            {
                f = null;
                // don't know what this is
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Filters element '" + xNodeLoop.Name + "' ignored.");
            } 
            if (f != null)
                _Items.Add(f);
             
        }
        if (_Items.Count == 0)
            OwnerReport.rl.logError(8,"Filters require at least one Filter be defined.");
        else
            _Items.TrimExcess(); 
    }

    public void finalPass() throws Exception {
        for (Object __dummyForeachVar1 : _Items)
        {
            Filter f = (Filter)__dummyForeachVar1;
            f.finalPass();
        }
        return ;
    }

    public boolean apply(fyiReporting.RDL.Report rpt, Row datarow) throws Exception {
        for (Object __dummyForeachVar2 : _Items)
        {
            Filter f = (Filter)__dummyForeachVar2;
            if (!f.getFilterOperatorSingleRow())
                return true;
             
            // have to handle Top/Bottom in ApplyFinalFilters
            if (!f.apply(rpt,datarow))
                return false;
             
        }
        return true;
    }

    public void applyFinalFilters(fyiReporting.RDL.Report rpt, Rows data, boolean makeCopy) throws Exception {
        // Need to apply the Top/Bottom and then the rest of the data
        // Loop to the first top/bottom (Apply has already handled the SingleRow filters prior to
        //   the first top/bottom
        int iFilter = new int();
        for (iFilter = 0;iFilter < _Items.Count;iFilter++)
        {
            Filter f = (Filter)_Items[iFilter];
            if (!f.getFilterOperatorSingleRow())
                break;
             
        }
        if (iFilter >= _Items.Count)
            return ;
         
        // nothing left to do?
        // good this is a lot cheaper
        // make copy of data if necessary
        if (makeCopy)
        {
            List<Row> ar = new List<Row>(data.getData());
            // Make a copy of the data!
            data.setData(ar);
        }
         
        for (;iFilter < _Items.Count && data.getData().Count > 0;iFilter++)
        {
            // Handling the remaining filters
            Filter f = (Filter)_Items[iFilter];
            f.apply(rpt,data);
        }
        // trim the space
        data.getData().TrimExcess();
        // reset the row numbers
        int rowCount = 0;
        for (Object __dummyForeachVar3 : data.getData())
        {
            Row r = (Row)__dummyForeachVar3;
            r.setRowNumber(rowCount++);
        }
        return ;
    }

    public List<Filter> getItems() throws Exception {
        return _Items;
    }

}


