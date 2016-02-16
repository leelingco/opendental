//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:28 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.RSize;

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
* The width of the border.  Expressions for all sides as well as default expression.
*/
public class StyleBorderWidth  extends ReportLink 
{
    Expression _Default;
    //(Size) Width of the border (unless overridden for a specific side)
    // Borders are centered on the edge of the object
    // Default: 1 pt Max: 20 pt Min: 0.25 pt
    Expression _Left;
    //(Size) Width of the left border. Max: 20 pt Min: 0.25 pt
    Expression _Right;
    //(Size) Width of the right border. Max: 20 pt Min: 0.25 pt
    Expression _Top;
    //(Size) Width of the top border. Max: 20 pt Min: 0.25 pt
    Expression _Bottom;
    //(Size) Width of the bottom border. Max: 20 pt Min: 0.25 pt
    public StyleBorderWidth(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Default = null;
        _Left = null;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("Default"))
            {
                _Default = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("Left"))
            {
                _Left = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("Right"))
            {
                _Right = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("Top"))
            {
                _Top = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else if (__dummyScrutVar0.equals("Bottom"))
            {
                _Bottom = new Expression(r,this,xNodeLoop,ExpressionType.ReportUnit);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown BorderWidth element '" + xNodeLoop.Name + "' ignored.");
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
            sb.AppendFormat("border-width:{0};", _Default.evaluateString(rpt,row));
        else if (bDefaults)
            sb.Append("border-width:1pt;");
          
        if (_Left != null)
            sb.AppendFormat("border-left-width:{0};", _Left.evaluateString(rpt,row));
         
        if (_Right != null)
            sb.AppendFormat("border-right-width:{0};", _Right.evaluateString(rpt,row));
         
        if (_Top != null)
            sb.AppendFormat("border-top-width:{0};", _Top.evaluateString(rpt,row));
         
        if (_Bottom != null)
            sb.AppendFormat("border-bottom-width:{0};", _Bottom.evaluateString(rpt,row));
         
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
        return "border-width:1pt;";
    }

    public Expression getDefault() throws Exception {
        return _Default;
    }

    public void setDefault(Expression value) throws Exception {
        _Default = value;
    }

    public float evalDefault(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // return points
        if (_Default == null)
            return 1;
         
        String sw = new String();
        sw = _Default.evaluateString(rpt,r);
        RSize rs = new RSize(this.OwnerReport,sw);
        return rs.getPoints();
    }

    public Expression getLeft() throws Exception {
        return _Left;
    }

    public void setLeft(Expression value) throws Exception {
        _Left = value;
    }

    public float evalLeft(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // return points
        if (_Left == null)
            return evalDefault(rpt,r);
         
        String sw = _Left.evaluateString(rpt,r);
        RSize rs = new RSize(this.OwnerReport,sw);
        return rs.getPoints();
    }

    public Expression getRight() throws Exception {
        return _Right;
    }

    public void setRight(Expression value) throws Exception {
        _Right = value;
    }

    public float evalRight(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // return points
        if (_Right == null)
            return evalDefault(rpt,r);
         
        String sw = _Right.evaluateString(rpt,r);
        RSize rs = new RSize(this.OwnerReport,sw);
        return rs.getPoints();
    }

    public Expression getTop() throws Exception {
        return _Top;
    }

    public void setTop(Expression value) throws Exception {
        _Top = value;
    }

    public float evalTop(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // return points
        if (_Top == null)
            return evalDefault(rpt,r);
         
        String sw = _Top.evaluateString(rpt,r);
        RSize rs = new RSize(this.OwnerReport,sw);
        return rs.getPoints();
    }

    public Expression getBottom() throws Exception {
        return _Bottom;
    }

    public void setBottom(Expression value) throws Exception {
        _Bottom = value;
    }

    public float evalBottom(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        // return points
        if (_Bottom == null)
            return evalDefault(rpt,r);
         
        String sw = _Bottom.evaluateString(rpt,r);
        RSize rs = new RSize(this.OwnerReport,sw);
        return rs.getPoints();
    }

}


