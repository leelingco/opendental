//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ConstantString;
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
* Format function: Format(expr, string expr format)
*/
public class FunctionFormat   implements IExpr
{
    IExpr _Formatee;
    // object to format
    IExpr _Format;
    // format string
    /**
    * Format an object
    */
    public FunctionFormat(IExpr formatee, IExpr format) throws Exception {
        _Formatee = formatee;
        _Format = format;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.String;
    }

    //
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateString(rpt,row);
    }

    public boolean isConstant() throws Exception {
        if (_Formatee.isConstant())
            return _Format.isConstant();
         
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        _Formatee = _Formatee.constantOptimization();
        _Format = _Format.constantOptimization();
        if (_Formatee.isConstant() && _Format.isConstant())
        {
            String s = evaluateString(null,null);
            return new ConstantString(s);
        }
         
        return this;
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String result = evaluateString(rpt,row);
        return Convert.ToBoolean(result);
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
        Object o = _Formatee.evaluate(rpt,row);
        if (o == null)
            return null;
         
        String format = _Format.evaluateString(rpt,row);
        if (format == null)
            return o.ToString();
         
        // just return string version of object
        String result = null;
        try
        {
            result = String.Format("{0:" + format + "}", o);
        }
        catch (Exception __dummyCatchVar0)
        {
            // invalid format string specified
            //    treat as a weak error
            result = o.ToString();
        }

        return result;
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String result = evaluateString(rpt,row);
        return Convert.ToDateTime(result);
    }

}


