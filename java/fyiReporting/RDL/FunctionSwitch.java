//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
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
* switch function like this example: Switch(a=1, "a1", a=2, "a2", true, "other")
*/
public class FunctionSwitch   implements IExpr
{
    IExpr[] _expr = new IExpr[]();
    // boolean expression
    TypeCode _tc = new TypeCode();
    /**
    * Switch function.  Evaluates boolean expression in order and returns result of
    * the first true.  For example, Switch(a=1, "a1", a=2, "a2", true, "other")
    */
    public FunctionSwitch(IExpr[] expr) throws Exception {
        _expr = expr;
        _tc = _expr[1].GetTypeCode();
    }

    public TypeCode getTypeCode() throws Exception {
        return _tc;
    }

    public boolean isConstant() throws Exception {
        return false;
    }

    // we could be more sophisticated here; but not much benefit
    public IExpr constantOptimization() throws Exception {
        for (int i = 0;i < _expr.Length;i++)
        {
            // simplify all expression if possible
            _expr[i] = _expr[i].ConstantOptimization();
        }
        return this;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean result = new boolean();
        for (int i = 0;i < _expr.Length;i = i + 2)
        {
            result = _expr[i].EvaluateBoolean(rpt, row);
            if (result)
            {
                Object o = _expr[i + 1].Evaluate(rpt, row);
                // We may need to convert to same type as first type
                if (i == 0 || _tc == _expr[i + 1].GetTypeCode())
                    return o;
                 
                return Convert.ChangeType(o, _tc);
            }
             
        }
        return null;
    }

    // first typecode will always match
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


