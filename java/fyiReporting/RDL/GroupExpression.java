//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:26 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;

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
* Definition of an expression within a group.
*/
public class GroupExpression  extends ReportLink 
{
    Expression _Expression;
    //
    public GroupExpression(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Expression = new Expression(r,this,xNode,ExpressionType.Variant);
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Expression != null)
            _Expression.finalPass();
         
        return ;
    }

    public Expression getExpression() throws Exception {
        return _Expression;
    }

    public void setExpression(Expression value) throws Exception {
        _Expression = value;
    }

}


