//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.SortDirection;
import fyiReporting.RDL.SortDirectionEnum;

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
* A single sort expression and direction.
*/
public class SortBy  extends ReportLink 
{
    Expression _SortExpression;
    // (Variant) The expression to sort the groups by.
    // The functions RunningValue and RowNumber
    // are not allowed in SortExpression.
    // References to report items are not allowed.
    SortDirectionEnum _Direction = SortDirectionEnum.Ascending;
    // Indicates the direction of the sort
    // Ascending (Default) | Descending
    public SortBy(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _SortExpression = null;
        _Direction = SortDirectionEnum.Ascending;
        for (Object __dummyForeachVar0 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar0;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar0 = xNodeLoop.Name;
            if (__dummyScrutVar0.equals("SortExpression"))
            {
                _SortExpression = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else if (__dummyScrutVar0.equals("Direction"))
            {
                _Direction = SortDirection.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown SortBy element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
        if (_SortExpression == null)
            OwnerReport.rl.logError(8,"SortBy requires the SortExpression element.");
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_SortExpression != null)
            _SortExpression.finalPass();
         
        return ;
    }

    public Expression getSortExpression() throws Exception {
        return _SortExpression;
    }

    public void setSortExpression(Expression value) throws Exception {
        _SortExpression = value;
    }

    public SortDirectionEnum getDirection() throws Exception {
        return _Direction;
    }

    public void setDirection(SortDirectionEnum value) throws Exception {
        _Direction = value;
    }

}


