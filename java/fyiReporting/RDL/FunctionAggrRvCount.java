//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.FunctionAggr;
import fyiReporting.RDL.ICacheData;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.OInt;
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
* Aggregate function: RunningValue count
*/
public class FunctionAggrRvCount  extends FunctionAggr implements IExpr, ICacheData
{
    String _key = new String();
    // key to cached between invocations
    /**
    * Aggregate function: RunningValue Sum returns the sum of all values of the
    * expression within the scope up to that row
    * Return type is decimal for decimal expressions and double for all
    * other expressions.
    */
    public FunctionAggrRvCount(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "aggrcount" + Interlocked.Increment(refVar___0).ToString();
        Parser.Counter = refVar___0.getValue();
        dataCache.Add(this);
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Double;
    }

    // Evaluate is for interpretation  (and is relatively slow)
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
        int count = new int();
        Object currentValue = _Expr.evaluate(rpt,row);
        int incr = currentValue == null ? 0 : 1;
        if (row == startrow)
        {
            // must be the start of a new group
            count = incr;
        }
        else
        {
            count = getValue(rpt) + incr;
        } 
        setValue(rpt,count);
        return (double)count;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return (double)evaluateDouble(rpt,row);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluateDouble(rpt,row);
        return Convert.ToString(result);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDateTime(result);
    }

    private int getValue(fyiReporting.RDL.Report rpt) throws Exception {
        OInt oi = rpt.getCache().get(_key) instanceof OInt ? (OInt)rpt.getCache().get(_key) : (OInt)null;
        return oi == null ? 0 : oi.i;
    }

    private void setValue(fyiReporting.RDL.Report rpt, int i) throws Exception {
        rpt.getCache().addReplace(_key,new OInt(i));
    }

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(_key);
    }

}


