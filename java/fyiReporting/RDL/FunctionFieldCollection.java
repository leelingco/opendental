//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.Field;
import fyiReporting.RDL.FunctionField;
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
* Fields referenced dynamically (e.g. Fields(expr) are handled by this class.
*/
public class FunctionFieldCollection   implements IExpr
{
    private IDictionary _Fields = new IDictionary();
    private IExpr _ArgExpr;
    /**
    * obtain value of Field
    */
    public FunctionFieldCollection(IDictionary fields, IExpr arg) throws Exception {
        _Fields = fields;
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
                throw new Exception("Field collection argument is null");
             
            Field f = _Fields[o] instanceof Field ? (Field)_Fields[o] : (Field)null;
            if (f == null)
                throw new Exception(String.Format("Field collection argument {0} is invalid", o));
             
            return new FunctionField(f);
        }
         
        return this;
    }

    //
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return null;
         
        Field f;
        String field = _ArgExpr.evaluateString(rpt,row);
        if (field == null)
            return null;
         
        f = _Fields[field] instanceof Field ? (Field)_Fields[field] : (Field)null;
        if (f == null)
            return null;
         
        Object o = new Object();
        if (f.getValue() != null)
            o = f.getValue().evaluate(rpt,row);
        else
            o = row.getData()[f.getColumnNumber()]; 
        if (o == DBNull.Value)
            return null;
         
        if (f.getRunType() == TypeCode.String && o instanceof char)
            // work around; mono odbc driver confuses string and char
            o = Convert.ChangeType(o, TypeCode.String);
         
        return o;
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


