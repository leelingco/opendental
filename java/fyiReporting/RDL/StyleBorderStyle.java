//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.BorderStyleEnum;
import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;

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
* The type (dotted, solid, ...) of border.  Expressions for all sides as well as default expression.
*/
public class StyleBorderStyle  extends ReportLink 
{
    Expression _Default;
    // (Enum BorderStyle) Style of the border (unless overridden for a specific side)
    // Default: none
    Expression _Left;
    // (Enum BorderStyle) Style of the left border
    Expression _Right;
    // (Enum BorderStyle) Style of the right border
    Expression _Top;
    // (Enum BorderStyle) Style of the top border
    Expression _Bottom;
    // (Enum BorderStyle) Style of the bottom border
    public StyleBorderStyle(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
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
                _Default = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Left"))
            {
                _Left = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Right"))
            {
                _Right = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Top"))
            {
                _Top = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else if (__dummyScrutVar0.equals("Bottom"))
            {
                _Bottom = new Expression(r,this,xNodeLoop,ExpressionType.Enum);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown BorderStyle element '" + xNodeLoop.Name + "' ignored.");
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
            sb.AppendFormat("border-style:{0};", _Default.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("border-style:none;");
          
        if (_Left != null)
            sb.AppendFormat("border-left-style:{0};", _Left.evaluateString(rpt,row));
         
        if (_Right != null)
            sb.AppendFormat("border-right-style:{0};", _Right.evaluateString(rpt,row));
         
        if (_Top != null)
            sb.AppendFormat("border-top-style:{0};", _Top.evaluateString(rpt,row));
         
        if (_Bottom != null)
            sb.AppendFormat("border-bottom-style:{0};", _Bottom.evaluateString(rpt,row));
         
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
        return "border-style:none;";
    }

    public Expression getDefault() throws Exception {
        return _Default;
    }

    public void setDefault(Expression value) throws Exception {
        _Default = value;
    }

    public BorderStyleEnum evalDefault(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Default == null)
            return BorderStyleEnum.None;
         
        String bs = _Default.evaluateString(rpt,r);
        return getBorderStyle(bs,BorderStyleEnum.Solid);
    }

    public Expression getLeft() throws Exception {
        return _Left;
    }

    public void setLeft(Expression value) throws Exception {
        _Left = value;
    }

    public BorderStyleEnum evalLeft(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Left == null)
            return evalDefault(rpt,r);
         
        String bs = _Left.evaluateString(rpt,r);
        return getBorderStyle(bs,BorderStyleEnum.Solid);
    }

    public Expression getRight() throws Exception {
        return _Right;
    }

    public void setRight(Expression value) throws Exception {
        _Right = value;
    }

    public BorderStyleEnum evalRight(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Right == null)
            return evalDefault(rpt,r);
         
        String bs = _Right.evaluateString(rpt,r);
        return getBorderStyle(bs,BorderStyleEnum.Solid);
    }

    public Expression getTop() throws Exception {
        return _Top;
    }

    public void setTop(Expression value) throws Exception {
        _Top = value;
    }

    public BorderStyleEnum evalTop(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Top == null)
            return evalDefault(rpt,r);
         
        String bs = _Top.evaluateString(rpt,r);
        return getBorderStyle(bs,BorderStyleEnum.Solid);
    }

    public Expression getBottom() throws Exception {
        return _Bottom;
    }

    public void setBottom(Expression value) throws Exception {
        _Bottom = value;
    }

    public BorderStyleEnum evalBottom(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Bottom == null)
            return evalDefault(rpt,r);
         
        String bs = _Bottom.evaluateString(rpt,r);
        return getBorderStyle(bs,BorderStyleEnum.Solid);
    }

    // return the BorderStyleEnum given a particular string value
    static public BorderStyleEnum getBorderStyle(String v, BorderStyleEnum def) throws Exception {
        BorderStyleEnum bs = BorderStyleEnum.None;
        System.String __dummyScrutVar1 = v;
        if (__dummyScrutVar1.equals("None"))
        {
            bs = BorderStyleEnum.None;
        }
        else if (__dummyScrutVar1.equals("Dotted"))
        {
            bs = BorderStyleEnum.Dotted;
        }
        else if (__dummyScrutVar1.equals("Dashed"))
        {
            bs = BorderStyleEnum.Dashed;
        }
        else if (__dummyScrutVar1.equals("Solid"))
        {
            bs = BorderStyleEnum.Solid;
        }
        else if (__dummyScrutVar1.equals("Double"))
        {
            bs = BorderStyleEnum.Double;
        }
        else if (__dummyScrutVar1.equals("Groove"))
        {
            bs = BorderStyleEnum.Groove;
        }
        else if (__dummyScrutVar1.equals("Ridge"))
        {
            bs = BorderStyleEnum.Ridge;
        }
        else if (__dummyScrutVar1.equals("Inset"))
        {
            bs = BorderStyleEnum.Inset;
        }
        else if (__dummyScrutVar1.equals("WindowInset"))
        {
            bs = BorderStyleEnum.WindowInset;
        }
        else if (__dummyScrutVar1.equals("Outset"))
        {
            bs = BorderStyleEnum.Outset;
        }
        else
        {
            bs = def;
        }          
        return bs;
    }

}


