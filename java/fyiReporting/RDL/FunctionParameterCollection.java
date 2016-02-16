//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.FunctionReportParameter;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.ReportParameter;
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
* Parameters referenced via the collection.  For example, Parameters(expr)
*/
public class FunctionParameterCollection   implements IExpr
{
    private IDictionary _Parameters = new IDictionary();
    private IExpr _ArgExpr;
    /**
    * obtain value of Field
    */
    public FunctionParameterCollection(IDictionary parameters, IExpr arg) throws Exception {
        _Parameters = parameters;
        _ArgExpr = arg;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Object;
    }

    // we don't know the typecode until we run the function
    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        _ArgExpr = _ArgExpr.constantOptimization();
        if (_ArgExpr.isConstant())
        {
            String o = _ArgExpr.evaluateString(null,null);
            if (o == null)
                throw new Exception("Parameter collection argument is null");
             
            ReportParameter rp = _Parameters[o] instanceof ReportParameter ? (ReportParameter)_Parameters[o] : (ReportParameter)null;
            if (rp == null)
                throw new Exception(String.Format("Parameter collection argument {0} is invalid", o));
             
            return new FunctionReportParameter(rp);
        }
         
        return this;
    }

    //
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String o = _ArgExpr.evaluateString(rpt,row);
        if (o == null)
            return null;
         
        ReportParameter rp = _Parameters[o] instanceof ReportParameter ? (ReportParameter)_Parameters[o] : (ReportParameter)null;
        if (rp == null)
            return null;
         
        return rp.getRuntimeValue(rpt);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return Double.NaN;
         
        return Convert.ToDouble(evaluate(rpt,row), NumberFormatInfo.InvariantInfo);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return double.MinValue;
         
        return Convert.ToDecimal(evaluate(rpt,row), NumberFormatInfo.InvariantInfo);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return null;
         
        return Convert.ToString(evaluate(rpt,row));
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return DateTime.MinValue;
         
        return Convert.ToDateTime(evaluate(rpt,row));
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return false;
         
        return Convert.ToBoolean(evaluate(rpt,row));
    }

}


