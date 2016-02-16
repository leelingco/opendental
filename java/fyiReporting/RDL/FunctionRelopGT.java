//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ConstantBoolean;
import fyiReporting.RDL.Filter;
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
* Relational operator equal of form lhs > rhs
*/
public class FunctionRelopGT  extends FunctionBinary implements IExpr
{
    /**
    * Do relational equal operation
    */
    public FunctionRelopGT(IExpr lhs, IExpr rhs) throws Exception {
        _lhs = lhs;
        _rhs = rhs;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Boolean;
    }

    public IExpr constantOptimization() throws Exception {
        _lhs = _lhs.constantOptimization();
        _rhs = _rhs.constantOptimization();
        if (_lhs.isConstant() && _rhs.isConstant())
        {
            boolean b = evaluateBoolean(null,null);
            return new ConstantBoolean(b);
        }
         
        return this;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateBoolean(rpt,row);
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object left = _lhs.evaluate(rpt,row);
        Object right = _rhs.evaluate(rpt,row);
        if (Filter.applyCompare(_lhs.getTypeCode(),left,right) > 0)
            return true;
        else
            return false; 
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return double.NaN;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return double.MinValue;
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean result = evaluateBoolean(rpt,row);
        return result.ToString();
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return DateTime.MinValue;
    }

}


