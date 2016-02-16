//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

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
* String constant
*/
public class ConstantString   implements IExpr
{
    String _Value = new String();
    // value of the constant
    /**
    * passed class name, function name, and args for evaluation
    */
    public ConstantString(String v) throws Exception {
        _Value = v;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.String;
    }

    public boolean isConstant() throws Exception {
        return true;
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    // already constant expression
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return _Value;
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return _Value;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDouble(_Value);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDecimal(_Value);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDateTime(_Value);
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToBoolean(_Value);
    }

}


