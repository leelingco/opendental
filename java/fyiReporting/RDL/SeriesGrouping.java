//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DynamicSeries;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.StaticSeries;
import fyiReporting.RDL.Style;

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
* Chart Series grouping (both dynamic and static).
*/
public class SeriesGrouping  extends ReportLink 
{
    DynamicSeries _DynamicSeries;
    // Dynamic Series headings for this grouping
    StaticSeries _StaticSeries;
    // Static Series headings for this grouping
    Style _Style;
    // border and background properties for series legend itmes and data points
    //   when dynamic exprs are evaluated per group instance
    public SeriesGrouping(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _DynamicSeries = null;
        _StaticSeries = null;
        _Style = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("DynamicSeries"))
            {
                _DynamicSeries = new DynamicSeries(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("StaticSeries"))
            {
                _StaticSeries = new StaticSeries(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(OwnerReport,this,xNodeLoop);
                OwnerReport.rl.logError(4,"Style element in SeriesGrouping is currently ignored.");
            }
            else
            {
                // TODO
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown SeriesGrouping element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
    }

    public void finalPass() throws Exception {
        if (_DynamicSeries != null)
            _DynamicSeries.finalPass();
         
        if (_StaticSeries != null)
            _StaticSeries.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        return ;
    }

    public DynamicSeries getDynamicSeries() throws Exception {
        return _DynamicSeries;
    }

    public void setDynamicSeries(DynamicSeries value) throws Exception {
        _DynamicSeries = value;
    }

    public StaticSeries getStaticSeries() throws Exception {
        return _StaticSeries;
    }

    public void setStaticSeries(StaticSeries value) throws Exception {
        _StaticSeries = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

}


