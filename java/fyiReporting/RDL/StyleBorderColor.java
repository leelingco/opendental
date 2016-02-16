//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
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
* The style of the border colors.  Expressions for all sides as well as default expression.
*/
public class StyleBorderColor  extends ReportLink 
{
    Expression _Default;
    // (Color) Color of the border (unless overridden for a specific
    //   side). Default: Black.
    Expression _Left;
    // (Color) Color of the left border
    Expression _Right;
    // (Color) Color of the right border
    Expression _Top;
    // (Color) Color of the top border
    Expression _Bottom;
    // (Color) Color of the bottom border
    public StyleBorderColor(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Default = null;
        _Left = null;
        _Right = null;
        _Top = null;
        _Bottom = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Default"))
            {
                _Default = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("Left"))
            {
                _Left = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("Right"))
            {
                _Right = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("Top"))
            {
                _Top = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else if (__dummyScrutVar0.equals("Bottom"))
            {
                _Bottom = new Expression(r,this,xNodeLoop,ExpressionType.Color);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown BorderColor element '" + xNodeLoop.Name + "' ignored.");
            }     
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Default != null)
            _Default.finalPass();
         
        if (_Left != null)
            _Left.finalPass();
         
        if (_Right != null)
            _Right.finalPass();
         
        if (_Top != null)
            _Top.finalPass();
         
        if (_Bottom != null)
            _Bottom.finalPass();
         
        return ;
    }

    // Generate a CSS string from the specified styles
    public String getCSS(fyiReporting.RDL.Report rpt, Row row, boolean bDefaults) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (_Default != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "border-color:{0};", _Default.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("border-color:black;");
          
        if (_Left != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "border-left:{0};", _Left.evaluateString(rpt,row));
         
        if (_Right != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "border-right:{0};", _Right.evaluateString(rpt,row));
         
        if (_Top != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "border-top:{0};", _Top.evaluateString(rpt,row));
         
        if (_Bottom != null)
            sb.AppendFormat(NumberFormatInfo.InvariantInfo, "border-bottom:{0};", _Bottom.evaluateString(rpt,row));
         
        return sb.ToString();
    }

    public boolean isConstant() throws Exception {
        boolean rc = true;
        if (_Default != null)
            rc = _Default.isConstant();
         
        if (!rc)
            return false;
         
        if (_Left != null)
            rc = _Left.isConstant();
         
        if (!rc)
            return false;
         
        if (_Right != null)
            rc = _Right.isConstant();
         
        if (!rc)
            return false;
         
        if (_Top != null)
            rc = _Top.isConstant();
         
        if (!rc)
            return false;
         
        if (_Bottom != null)
            rc = _Bottom.isConstant();
         
        return rc;
    }

    static public String getCSSDefaults() throws Exception {
        return "border-color:black;";
    }

    public Expression getDefault() throws Exception {
        return _Default;
    }

    public void setDefault(Expression value) throws Exception {
        _Default = value;
    }

    public Color evalDefault(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Default == null)
            return System.Drawing.Color.Black;
         
        String c = _Default.evaluateString(rpt,r);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
    }

    public Expression getLeft() throws Exception {
        return _Left;
    }

    public void setLeft(Expression value) throws Exception {
        _Left = value;
    }

    public Color evalLeft(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Left == null)
            return evalDefault(rpt,r);
         
        String c = _Left.evaluateString(rpt,r);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
    }

    public Expression getRight() throws Exception {
        return _Right;
    }

    public void setRight(Expression value) throws Exception {
        _Right = value;
    }

    public Color evalRight(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Right == null)
            return evalDefault(rpt,r);
         
        String c = _Right.evaluateString(rpt,r);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
    }

    public Expression getTop() throws Exception {
        return _Top;
    }

    public void setTop(Expression value) throws Exception {
        _Top = value;
    }

    public Color evalTop(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Top == null)
            return evalDefault(rpt,r);
         
        String c = _Top.evaluateString(rpt,r);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
    }

    public Expression getBottom() throws Exception {
        return _Bottom;
    }

    public void setBottom(Expression value) throws Exception {
        _Bottom = value;
    }

    public Color evalBottom(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Bottom == null)
            return evalDefault(rpt,r);
         
        String c = _Bottom.evaluateString(rpt,r);
        return XmlUtil.ColorFromHtml(c, System.Drawing.Color.Black, rpt);
    }

}


