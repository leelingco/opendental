//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DynamicColumns;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.RSize;
import fyiReporting.RDL.StaticColumns;

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
* ColumnGrouping definition and processing.
*/
public class ColumnGrouping  extends ReportLink 
{
    RSize _Height;
    // Height of the column header
    DynamicColumns _DynamicColumns;
    // Dynamic column headings for this grouping
    StaticColumns _StaticColumns;
    // Static column headings for this grouping
    public ColumnGrouping(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Height = null;
        _DynamicColumns = null;
        _StaticColumns = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Height"))
            {
                _Height = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("DynamicColumns"))
            {
                _DynamicColumns = new DynamicColumns(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("StaticColumns"))
            {
                _StaticColumns = new StaticColumns(r,this,xNodeLoop);
            }
            else
            {
            }   
        }
        if (_Height == null)
            OwnerReport.rl.logError(8,"ColumnGrouping requires the Height element to be specified.");
         
        if ((_DynamicColumns != null && _StaticColumns != null) || (_DynamicColumns == null && _StaticColumns == null))
            OwnerReport.rl.logError(8,"ColumnGrouping requires either the DynamicColumns element or StaticColumns element but not both.");
         
    }

    public void finalPass() throws Exception {
        if (_DynamicColumns != null)
            _DynamicColumns.finalPass();
         
        if (_StaticColumns != null)
            _StaticColumns.finalPass();
         
        return ;
    }

    public RSize getHeight() throws Exception {
        return _Height;
    }

    public void setHeight(RSize value) throws Exception {
        _Height = value;
    }

    public DynamicColumns getDynamicColumns() throws Exception {
        return _DynamicColumns;
    }

    public void setDynamicColumns(DynamicColumns value) throws Exception {
        _DynamicColumns = value;
    }

    public StaticColumns getStaticColumns() throws Exception {
        return _StaticColumns;
    }

    public void setStaticColumns(StaticColumns value) throws Exception {
        _StaticColumns = value;
    }

}


