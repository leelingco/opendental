//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ConstantDecimal;
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
* Division operator  of form lhs / rhs where both arguments are decimal.
*/
public class FunctionDivDecimal  extends FunctionBinary implements IExpr
{
    /**
    * Do division on decimal data types
    */
    public FunctionDivDecimal() throws Exception {
        _lhs = null;
        _rhs = null;
    }

    public FunctionDivDecimal(IExpr lhs, IExpr rhs) throws Exception {
        _lhs = lhs;
        _rhs = rhs;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Decimal;
    }

    public IExpr constantOptimization() throws Exception {
        _lhs = _lhs.constantOptimization();
        _rhs = _rhs.constantOptimization();
        boolean bLeftConst = _lhs.isConstant();
        boolean bRightConst = _rhs.isConstant();
        if (bLeftConst && bRightConst)
        {
            double d = evaluateDecimal(null,null);
            return new ConstantDecimal(d);
        }
        else if (bRightConst)
        {
            double d = _rhs.evaluateDecimal(null,null);
            if (d == 1m)
                return _lhs;
             
        }
        else if (bLeftConst)
        {
            double d = _lhs.evaluateDecimal(null,null);
            if (d == 0)
                return new ConstantDecimal(0m);
             
        }
           
        return this;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateDecimal(rpt,row);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDecimal(rpt,row);
        return Convert.ToDouble(result);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double lhs = _lhs.evaluateDecimal(rpt,row);
        double rhs = _rhs.evaluateDecimal(rpt,row);
        return (double)(lhs / rhs);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDecimal(rpt,row);
        return result.ToString();
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDecimal(rpt,row);
        return Convert.ToDateTime(result);
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDouble(rpt,row);
        return Convert.ToBoolean(result);
    }

}


