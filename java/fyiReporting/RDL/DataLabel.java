//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.DataLabelPosition;
import fyiReporting.RDL.DataLabelPositionEnum;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
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
* DataLabel definition and processing.
*/
public class DataLabel  extends ReportLink 
{
    Style _Style;
    // Defines text, border and background style
    // properties for the labels
    Expression _Value;
    //(Variant) Expression for the value labels. If omitted,
    // values of in the ValueAxis are used for labels.
    boolean _Visible = new boolean();
    // Whether the data label is displayed on the
    // chart. Defaults to False.
    DataLabelPositionEnum _Position = DataLabelPositionEnum.Auto;
    // Position of the label.  Default: auto
    int _Rotation = new int();
    // Angle of rotation of the label text
    public DataLabel(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Style = null;
        _Value = null;
        _Visible = false;
        _Position = DataLabelPositionEnum.Auto;
        _Rotation = 0;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Style"))
            {
                _Style = new Style(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar0.equals("Value"))
            {
                _Value = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else if (__dummyScrutVar0.equals("Visible"))
            {
                _Visible = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Position"))
            {
                _Position = DataLabelPosition.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar0.equals("Rotation"))
            {
                _Rotation = XmlUtil.Integer(xNodeLoop.InnerText);
            }
            else
            {
            }     
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Style != null)
            _Style.finalPass();
         
        if (_Value != null)
            _Value.finalPass();
         
        return ;
    }

    public Style getStyle() throws Exception {
        return _Style;
    }

    public void setStyle(Style value) throws Exception {
        _Style = value;
    }

    public Expression getValue() throws Exception {
        return _Value;
    }

    public void setValue(Expression value) throws Exception {
        _Value = value;
    }

    public boolean getVisible() throws Exception {
        return _Visible;
    }

    public void setVisible(boolean value) throws Exception {
        _Visible = value;
    }

    public DataLabelPositionEnum getPosition() throws Exception {
        return _Position;
    }

    public void setPosition(DataLabelPositionEnum value) throws Exception {
        _Position = value;
    }

    public int getRotation() throws Exception {
        return _Rotation;
    }

    public void setRotation(int value) throws Exception {
        _Rotation = value;
    }

}


