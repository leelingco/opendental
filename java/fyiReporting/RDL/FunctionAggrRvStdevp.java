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
* Aggregate function: RunningValue stdevp
*/
public class FunctionAggrRvStdevp  extends FunctionAggr implements IExpr, ICacheData
{
    String _key = new String();
    // key for caching holding between invocations
    /**
    * Aggregate function: RunningValue Stdevp returns the Stdevp of all values of the
    * expression within the scope up to that row
    * Return type is double for all expressions.
    */
    public FunctionAggrRvStdevp(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "aggrrvstdevp" + Interlocked.Increment(refVar___0).ToString();
        Parser.Counter = refVar___0.getValue();
        dataCache.Add(this);
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Double;
    }

    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return (Object)evaluateDouble(rpt,row);
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
        WorkClass wc = getValue(rpt);
        double currentValue = _Expr.evaluateDouble(rpt,row);
        if (row == startrow)
        {
            // restart the group
            wc.Sum = wc.Sum2 = 0;
            wc.Count = 0;
        }
         
        if (currentValue.CompareTo(double.NaN) != 0)
        {
            wc.Sum += currentValue;
            wc.Sum2 += (currentValue * currentValue);
            wc.Count++;
        }
         
        double result = new double();
        if (wc.Count > 0)
            result = Math.Sqrt((wc.Count * wc.Sum2 - wc.Sum * wc.Sum) / (wc.Count * wc.Count));
        else
            result = double.NaN; 
        return result;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double d = evaluateDouble(rpt,row);
        if (d.CompareTo(double.NaN) == 0)
            return double.MinValue;
         
        return Convert.ToDecimal(d);
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
        public double Sum = new double();
        public double Sum2 = new double();
        public int Count = new int();
        public WorkClass() throws Exception {
            Sum = Sum2 = 0;
            Count = -1;
        }
    
    }

}


