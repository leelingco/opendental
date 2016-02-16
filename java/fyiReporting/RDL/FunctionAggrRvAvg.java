//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.FunctionAggr;
import fyiReporting.RDL.ICacheData;
import fyiReporting.RDL.IExpr;
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
* Aggregate function: RunningValue avg
*/
public class FunctionAggrRvAvg  extends FunctionAggr implements IExpr, ICacheData
{
    private TypeCode _tc = new TypeCode();
    // type of result: decimal or double
    String _key = new String();
    // key for retaining prior rows values
    /**
    * Aggregate function: RunningValue Sum returns the sum of all values of the
    * expression within the scope up to that row
    * Return type is decimal for decimal expressions and double for all
    * other expressions.
    */
    public FunctionAggrRvAvg(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "aggrrvavg" + Interlocked.Increment(refVar___0).ToString();
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
         
        Row startrow = null;
        for (Object __dummyForeachVar0 : re)
        {
            Row r = (Row)__dummyForeachVar0;
            startrow = r;
            break;
        }
        // We just want the first row
        double currentValue = _Expr.evaluateDouble(rpt,row);
        WorkClass wc = getValue(rpt);
        if (row == startrow)
        {
            // must be the start of a new group
            wc.Value = currentValue;
            wc.Count = 1;
        }
        else
        {
            wc.Value = ((Double)wc.Value + currentValue);
            wc.Count++;
        } 
        return (Double)wc.Value / wc.Count;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean bSave = new boolean();
        RefSupport<boolean> refVar___2 = new RefSupport<boolean>();
        IEnumerable re = this.getDataScope(rpt,row,refVar___2);
        bSave = refVar___2.getValue();
        if (re == null)
            return double.MinValue;
         
        Row startrow = null;
        for (Object __dummyForeachVar1 : re)
        {
            Row r = (Row)__dummyForeachVar1;
            startrow = r;
            break;
        }
        // We just want the first row
        double currentValue = _Expr.evaluateDecimal(rpt,row);
        WorkClass wc = getValue(rpt);
        if (row == startrow)
        {
            // must be the start of a new group
            wc.Value = currentValue;
            wc.Count = 1;
        }
        else
        {
            wc.Value = ((double)wc.Value + currentValue);
            wc.Count++;
        } 
        return (double)wc.Value / wc.Count;
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToString(result);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDateTime(result);
    }

    private WorkClass getValue(fyiReporting.RDL.Report rpt) throws Exception {
        WorkClass wc = rpt.getCache().get(_key) instanceof WorkClass ? (WorkClass)rpt.getCache().get(_key) : (WorkClass)null;
        if (wc == null)
        {
            wc = new WorkClass();
            rpt.getCache().add(_key,wc);
        }
         
        return wc;
    }

    private void setValue(fyiReporting.RDL.Report rpt, WorkClass w) throws Exception {
        rpt.getCache().addReplace(_key,w);
    }

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(_key);
    }

    static class WorkClass   
    {
        public Object Value = new Object();
        public int Count = new int();
        public WorkClass() throws Exception {
            Value = null;
            Count = 0;
        }
    
    }

}


