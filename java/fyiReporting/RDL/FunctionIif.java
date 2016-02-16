//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
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
* iif function of the form iif(boolean, expr1, expr2)
*/
public class FunctionIif   implements IExpr
{
    IExpr _If;
    // boolean expression
    IExpr _IfTrue;
    // result if true
    IExpr _IfFalse;
    // result if false
    /**
    * Handle iif operator
    */
    public FunctionIif(IExpr ife, IExpr ifTrue, IExpr ifFalse) throws Exception {
        _If = ife;
        _IfTrue = ifTrue;
        _IfFalse = ifFalse;
    }

    public TypeCode getTypeCode() throws Exception {
        return _IfTrue.getTypeCode();
    }

    public boolean isConstant() throws Exception {
        return _If.isConstant() && _IfTrue.isConstant() && _IfFalse.isConstant();
    }

    public IExpr constantOptimization() throws Exception {
        _If = _If.constantOptimization();
        _IfTrue = _IfTrue.constantOptimization();
        _IfFalse = _IfFalse.constantOptimization();
        if (_If.isConstant())
        {
            boolean result = _If.evaluateBoolean(null,null);
            return result ? _IfTrue : _IfFalse;
        }
         
        return this;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean result = _If.evaluateBoolean(rpt,row);
        if (result)
            return _IfTrue.evaluate(rpt,row);
         
        Object o = _IfFalse.evaluate(rpt,row);
        // We may need to convert IfFalse to same type as IfTrue
        if (_IfTrue.getTypeCode() == _IfFalse.getTypeCode())
            return o;
         
        return Convert.ChangeType(o, _IfTrue.getTypeCode());
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToBoolean(result);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDouble(result);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDecimal(result);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToString(result);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDateTime(result);
    }

}


