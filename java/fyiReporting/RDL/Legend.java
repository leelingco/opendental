//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.LegendLayout;
import fyiReporting.RDL.LegendLayoutEnum;
import fyiReporting.RDL.LegendPosition;
import fyiReporting.RDL.LegendPositionEnum;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.XmlUtil;

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
* Chart legend definition (style, position, ...)
*/
public class Legend  extends ReportLink 
{
    boolean _Visible = new boolean();
    // Specifies whether a legend is displayed.
    // Defaults to false.
    Style _Style;
    // Defines text, border and background style
    // properties for the legend. All Textbox properties apply.
    LegendPositionEnum _Position = LegendPositionEnum.TopLeft;
    // The position of the legend
    // Default: RightTop
    LegendLayoutEnum _Layout = LegendLayoutEnum.Column;
    // The arrangement of labels within the legend
    // Default: Column
    boolean _InsidePlotArea = new boolean();
    //Boolean If true, draw legend inside plot area, otherwise
    // draw outside plot area (default).
    public Legend(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Visible = false;
        _Style = null;
        _Position = LegendPositionEnum.RightTop;
        _Layout = LegendLayoutEnum.Column;
        _InsidePlotArea = false;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Visible"))
            {
                _Visible = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Position"))
            {
                _Position = LegendPosition.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Layout"))
            {
                _Layout = LegendLayout.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("InsidePlotArea"))
            {
                _InsidePlotArea = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
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

    public boolean getVisible() throws Exception {
        return _Visible;
    }

    public void setVisible(boolean value) throws Exception {
        _Visible = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public LegendPositionEnum getPosition() throws Exception {
        return _Position;
    }

    public void setPosition(LegendPositionEnum value) throws Exception {
        _Position = value;
    }

    public LegendLayoutEnum getLayout() throws Exception {
        return _Layout;
    }

    public void setLayout(LegendLayoutEnum value) throws Exception {
        _Layout = value;
    }

    public boolean getInsidePlotArea() throws Exception {
        return _InsidePlotArea;
    }

    public void setInsidePlotArea(boolean value) throws Exception {
        _InsidePlotArea = value;
    }

}


