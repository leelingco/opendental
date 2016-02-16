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
import fyiReporting.RDL.RowEnumerable;

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
* Aggregate function: last
*/
public class FunctionAggrLast  extends FunctionAggr implements IExpr, ICacheData
{
    private TypeCode _tc = new TypeCode();
    // type of result: decimal or double
    String _key = new String();
    /**
    * Aggregate function: Last returns the last value in the group
    * Return type is same as input expression
    */
    public FunctionAggrLast(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "aggrfirst" + Interlocked.Increment(refVar___0).ToString();
        Parser.Counter = refVar___0.getValue();
        // Determine the result
        _tc = e.getTypeCode();
        dataCache.Add(this);
    }

    public TypeCode getTypeCode() throws Exception {
        return _tc;
    }

    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean bSave = true;
        RefSupport<boolean> refVar___1 = new RefSupport<boolean>();
        RowEnumerable re = this.getDataScope(rpt,row,refVar___1);
        bSave = refVar___1.getValue();
        if (re == null)
            return null;
         
        Object v = getValue(rpt);
        if (v == null)
        {
            Row saver = null;
            if (re.getData().Count > 0)
                saver = re.getData()[re.getLastRow()] instanceof Row ? (Row)re.getData()[re.getLastRow()] : (Row)null;
             
            for (Object __dummyForeachVar0 : re)
            {
                Row r = (Row)__dummyForeachVar0;
                // loop thru to end
                saver = r;
            }
            v = _Expr.evaluate(rpt,saver);
            if (bSave)
                setValue(rpt,v);
             
        }
         
        return v;
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

    private Object getValue(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(_key);
    }

    private void setValue(fyiReporting.RDL.Report rpt, Object o) throws Exception {
        rpt.getCache().addReplace(_key,o);
    }

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(_key);
    }

}


