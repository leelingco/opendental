//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Row;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General public License for more details.
    You should have received a copy of the GNU Lesser General public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Identifier
*/
public class Identifier   implements IExpr
{
    String _Value = new String();
    // value of the identifier
    /**
    * passed class name, function name, and args for evaluation
    */
    public Identifier(String v) throws Exception {
        String lv = v.ToLower();
        if (StringSupport.equals(lv, "null") || StringSupport.equals(lv, "nothing"))
            _Value = null;
        else
            _Value = v; 
    }

    public boolean getIsNothing() throws Exception {
        return _Value == null ? true : false;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Object;
    }

    // TODO
    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return _Value;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDouble(evaluate(rpt,row));
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDecimal(evaluate(rpt,row));
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToString(evaluate(rpt,row));
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDateTime(evaluate(rpt,row));
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToBoolean(evaluate(rpt,row));
    }

}


