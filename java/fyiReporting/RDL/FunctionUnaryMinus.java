//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ConstantDouble;
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
* Unary minus operator
*/
public class FunctionUnaryMinus   implements IExpr
{
    IExpr _rhs;
    // rhs
    /**
    * Do division on double data types
    */
    public FunctionUnaryMinus() throws Exception {
        _rhs = null;
    }

    public FunctionUnaryMinus(IExpr r) throws Exception {
        _rhs = r;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Double;
    }

    public boolean isConstant() throws Exception {
        return _rhs.isConstant();
    }

    public IExpr constantOptimization() throws Exception {
        _rhs.constantOptimization();
        if (_rhs.isConstant())
            return new ConstantDouble(evaluateDouble(null,null));
        else
            return this; 
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateDouble(rpt,row);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double rhs = _rhs.evaluateDouble(rpt,row);
        return -rhs;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDouble(rpt,row);
        return Convert.ToDecimal(result);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDouble(rpt,row);
        return result.ToString();
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDouble(rpt,row);
        return Convert.ToDateTime(result);
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return false;
    }

    public IExpr getRhs() throws Exception {
        return _rhs;
    }

    public void setRhs(IExpr value) throws Exception {
        _rhs = value;
    }

}


