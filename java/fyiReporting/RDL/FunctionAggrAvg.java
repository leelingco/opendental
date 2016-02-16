//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.FunctionAggr;
import fyiReporting.RDL.ICacheData;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.ODecimal;
import fyiReporting.RDL.ODouble;
import fyiReporting.RDL.Parser;
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
* Aggregate function: average
*/
public class FunctionAggrAvg  extends FunctionAggr implements IExpr, ICacheData
{
    private TypeCode _tc = new TypeCode();
    // type of result: decimal or double
    String _key = new String();
    /**
    * Aggregate function: Sum returns the sum of all values of the
    * expression within the scope
    * Return type is decimal for decimal expressions and double for all
    * other expressions.
    */
    public FunctionAggrAvg(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "aggravg" + Interlocked.Increment(refVar___0).ToString();
        Parser.Counter = refVar___0.getValue();
        // Determine the result
        _tc = e.getTypeCode();
        if (_tc != TypeCode.Decimal)
            // if not decimal
            _tc = TypeCode.Double;
         
        // force result to double
        dataCache.Add(this);
    }

    public TypeCode getTypeCode() throws Exception {
        return _tc;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return _tc == TypeCode.Decimal ? (Object)evaluateDecimal(rpt,row) : (Object)evaluateDouble(rpt,row);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean bSave = true;
        RefSupport<boolean> refVar___1 = new RefSupport<boolean>();
        IEnumerable re = this.getDataScope(rpt,row,refVar___1);
        bSave = refVar___1.getValue();
        if (re == null)
            return double.NaN;
         
        ODouble v = getValueDouble(rpt);
        if (v != null)
            return v.d;
         
        double sum = 0;
        int count = 0;
        double temp = new double();
        for (Object __dummyForeachVar0 : re)
        {
            Row r = (Row)__dummyForeachVar0;
            temp = _Expr.evaluateDouble(rpt,r);
            if (temp.CompareTo(double.NaN) != 0)
            {
                sum += temp;
                count++;
            }
             
        }
        double result = new double();
        if (count > 0)
            result = (sum / count);
        else
            result = double.NaN; 
        if (bSave)
            setValue(rpt,result);
         
        return result;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean bSave = new boolean();
        RefSupport<boolean> refVar___2 = new RefSupport<boolean>();
        IEnumerable re = this.getDataScope(rpt,row,refVar___2);
        bSave = refVar___2.getValue();
        if (re == null)
            return double.MinValue;
         
        ODecimal v = getValueDecimal(rpt);
        if (v != null)
            return v.d;
         
        double sum = 0;
        int count = 0;
        double temp = new double();
        for (Object __dummyForeachVar1 : re)
        {
            Row r = (Row)__dummyForeachVar1;
            temp = _Expr.evaluateDecimal(rpt,r);
            if (temp != double.MinValue)
            {
                // indicate null value
                sum += temp;
                count++;
            }
             
        }
        double result = new double();
        if (count > 0)
            result = (sum / count);
        else
            result = double.MinValue; 
        if (bSave)
            setValue(rpt,result);
         
        return result;
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToString(result);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDateTime(result);
    }

    private ODecimal getValueDecimal(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(_key) instanceof ODecimal ? (ODecimal)rpt.getCache().get(_key) : (ODecimal)null;
    }

    private ODouble getValueDouble(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(_key) instanceof ODouble ? (ODouble)rpt.getCache().get(_key) : (ODouble)null;
    }

    private void setValue(fyiReporting.RDL.Report rpt, double d) throws Exception {
        rpt.getCache().addReplace(_key,new ODouble(d));
    }

    private void setValue(fyiReporting.RDL.Report rpt, double d) throws Exception {
        rpt.getCache().addReplace(_key,new ODecimal(d));
    }

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(_key);
    }

}


