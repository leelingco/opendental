//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Style;
import fyiReporting.RDL.TitlePosition;
import fyiReporting.RDL.TitlePositionEnum;

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
* Chart (or axis) title definition.
*/
public class Title  extends ReportLink 
{
    Expression _Caption;
    //(string) Caption of the title
    Style _Style;
    // Defines text, border and background style
    // properties for the title.
    // All Textbox properties apply.
    TitlePositionEnum _Position = TitlePositionEnum.Center;
    // The position of the title; Default: center
    public Title(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Caption = null;
        _Style = null;
        _Position = TitlePositionEnum.Center;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Caption"))
            {
                _Caption = new Expression(r,this,xNodeLoop,ExpressionType.String);
            }
            else if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Position"))
            {
                _Position = TitlePosition.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Title element '" + xNodeLoop.Name + "' ignored.");
            }   
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Caption != null)
            _Caption.finalPass();
         
        if (_Style != null)
            _Style.finalPass();
         
        return ;
    }

    public Expression getCaption() throws Exception {
        return _Caption;
    }

    public void setCaption(Expression value) throws Exception {
        _Caption = value;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public TitlePositionEnum getPosition() throws Exception {
        return _Position;
    }

    public void setPosition(TitlePositionEnum value) throws Exception {
        _Position = value;
    }

}


