//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ConstantString;
import fyiReporting.RDL.FunctionBinary;
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
* Plus operator  of form lhs + rhs where operands are strings
*/
public class FunctionPlusString  extends FunctionBinary implements IExpr
{
    /**
    * append two strings together
    */
    public FunctionPlusString(IExpr lhs, IExpr rhs) throws Exception {
        _lhs = lhs;
        _rhs = rhs;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.String;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateString(rpt,row);
    }

    public IExpr constantOptimization() throws Exception {
        _lhs = _lhs.constantOptimization();
        _rhs = _rhs.constantOptimization();
        if (_lhs.isConstant() && _rhs.isConstant())
        {
            String s = evaluateString(null,null);
            return new ConstantString(s);
        }
         
        return this;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String result = evaluateString(rpt,row);
        return Convert.ToDouble(result);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String result = evaluateString(rpt,row);
        return Convert.ToDecimal(result);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String lhs = _lhs.evaluateString(rpt,row);
        String rhs = _rhs.evaluateString(rpt,row);
        if (lhs != null && rhs != null)
            return lhs + rhs;
        else
            return null; 
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String result = evaluateString(rpt,row);
        return Convert.ToDateTime(result);
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String result = evaluateString(rpt,row);
        return Convert.ToBoolean(result);
    }

}


