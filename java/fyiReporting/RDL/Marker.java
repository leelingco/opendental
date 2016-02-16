//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.MarkerType;
import fyiReporting.RDL.MarkerTypeEnum;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.RSize;
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
* Represents a marker on a chart.
*/
public class Marker  extends ReportLink 
{
    MarkerTypeEnum _Type = MarkerTypeEnum.None;
    // Defines the marker type for values. Default: none
    RSize _Size;
    // Represents the height and width of the
    //  plotting area of marker(s).
    Style _Style;
    // Defines the border and background style
    //  properties for the marker(s).
    public Marker(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Type = MarkerTypeEnum.None;
        _Size = null;
        _Style = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Type"))
            {
                _Type = MarkerType.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Size"))
            {
                _Size = new RSize(r,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else
            {
            }   
        }
    }

    public void finalPass() throws Exception {
        if (_Style != null)
            _Style.finalPass();
         
        return ;
    }

    public MarkerTypeEnum getType() throws Exception {
        return _Type;
    }

    public void setType(MarkerTypeEnum value) throws Exception {
        _Type = value;
    }

    public RSize getSize() throws Exception {
        return _Size;
    }

    public void setSize(RSize value) throws Exception {
        _Size = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

}


