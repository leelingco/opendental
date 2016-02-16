//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Expression;
import fyiReporting.RDL.ExpressionType;
import fyiReporting.RDL.Name;
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
* A drillthrough parameter.
*/
public class DrillthroughParameter  extends ReportLink 
{
    Name _Name;
    // Name of the parameter
    Expression _Value;
    // (Variant) An expression that evaluates to the value to
    // hand in for the parameter to the Drillthough.
    Expression _Omit;
    // (Boolean) Indicates the parameter should be skipped.
    public DrillthroughParameter(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _Value = null;
        _Omit = null;
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
        if (_Name == null)
        {
            // Name is required for parameters
            OwnerReport.rl.logError(8,"Parameter Name attribute required.'");
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
            else if (__dummyScrutVar1.equals("Omit"))
            {
                _Omit = new Expression(r,this,xNodeLoop,ExpressionType.Boolean);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown Parameter element '" + xNodeLoop.Name + "' ignored.");
            }  
        }
    }

    // Handle parsing of function in final pass
    public void finalPass() throws Exception {
        if (_Value != null)
            _Value.finalPass();
         
        if (_Omit != null)
            _Omit.finalPass();
         
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

    public String valueValue(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Value == null)
            return "";
         
        return _Value.evaluateString(rpt,r);
    }

    public Expression getOmit() throws Exception {
        return _Omit;
    }

    public void setOmit(Expression value) throws Exception {
        _Omit = value;
    }

    public boolean omitValue(fyiReporting.RDL.Report rpt, Row r) throws Exception {
        if (_Omit == null)
            return false;
         
        return _Omit.evaluateBoolean(rpt,r);
    }

}


