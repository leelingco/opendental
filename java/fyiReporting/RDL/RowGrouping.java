//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DynamicRows;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.StaticRows;

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
* Matrix row grouping definition.
*/
public class RowGrouping  extends ReportLink 
{
    RSize _Width;
    // Width of the row header
    DynamicRows _DynamicRows;
    // Dynamic row headings for this grouping
    StaticRows _StaticRows;
    // Static row headings for this grouping
    public RowGrouping(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Width = null;
        _DynamicRows = null;
        _StaticRows = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Width"))
            {
                _Width = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("DynamicRows"))
            {
                _DynamicRows = new DynamicRows(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("StaticRows"))
            {
                _StaticRows = new StaticRows(r,this,xNodeLoop);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown RowGrouping element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
        if (_Width == null)
            OwnerReport.rl.logError(8,"RowGrouping requires the Width element.");
         
    }

    public void finalPass() throws Exception {
        if (_DynamicRows != null)
            _DynamicRows.finalPass();
         
        if (_StaticRows != null)
            _StaticRows.finalPass();
         
        return ;
    }

    public RSize getWidth() throws Exception {
        return _Width;
    }

    public void setWidth(RSize value) throws Exception {
        _Width = value;
    }

    public DynamicRows getDynamicRows() throws Exception {
        return _DynamicRows;
    }

    public void setDynamicRows(DynamicRows value) throws Exception {
        _DynamicRows = value;
    }

    public StaticRows getStaticRows() throws Exception {
        return _StaticRows;
    }

    public void setStaticRows(StaticRows value) throws Exception {
        _StaticRows = value;
    }

}


