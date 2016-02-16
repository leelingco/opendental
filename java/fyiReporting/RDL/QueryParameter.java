//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.QueryParameter;
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
* Represent query parameter.
*/
public class QueryParameter  extends ReportLink implements IComparable
{
    Name _Name;
    // Name of the parameter
    Expression _Value;
    // (Variant or Variant Array)
    //An expression that evaluates to the value to
    //hand to the data source. The expression can
    //refer to report parameters but cannot contain
    //references to report elements, fields in the data
    //model or aggregate functions.
    //In the case of a parameter to a Values or
    //DefaultValue query, the expression can only
    //refer to report parameters that occur earlier in
    //the parameters list. The value for this query
    //parameter is then taken from the user selection
    //for that earlier report parameter.
    public QueryParameter(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _Value = null;
        for (Object __dummyForeachVar0 : xNode.Attributes)
        {
            // Run thru the attributes
            XmlAttribute xAttr = (XmlAttribute)__dummyForeachVar0;
            Name __dummyScrutVar0 = xAttr.Name;
            if (__dummyScrutVar0.equals("Name"))
            {
                _Name = new Name(xAttr.Value);
            }
             
        }
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("Value"))
            {
                _Value = new Expression(r,this,xNodeLoop,ExpressionType.Variant);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown QueryParameter element '" + xNodeLoop.Name + "' ignored.");
            } 
        }
        if (_Name == null)
            OwnerReport.rl.logError(8,"QueryParameter name is required but not specified.");
         
        if (_Value == null)
            OwnerReport.rl.logError(8,"QueryParameter Value is required but not specified or invalid for " + _Name == null ? "<unknown name>" : _Name.getNm());
         
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Value != null)
            _Value.finalPass();
         
        return ;
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public Expression getValue() throws Exception {
        return _Value;
    }

    public void setValue(Expression value) throws Exception {
        _Value = value;
    }

    public boolean getIsArray() throws Exception {
        if (_Value == null)
            return false;
         
        return (_Value.getTypeCode() == TypeCode.Object);
    }

    // when null; usually means a parsing error
    //   but we want to continue as far as we can
    public int compareTo(Object obj) throws Exception {
        QueryParameter qp = obj instanceof QueryParameter ? (QueryParameter)obj : (QueryParameter)null;
        if (qp == null)
            return 0;
         
        String tname = this.getName().getNm();
        String qpname = qp.getName().getNm();
        int length_diff = qpname.Length - tname.Length;
        if (length_diff == 0)
            return qpname.CompareTo(tname);
         
        return length_diff;
    }

}


