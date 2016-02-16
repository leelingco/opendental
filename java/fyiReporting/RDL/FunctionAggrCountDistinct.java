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
* Aggregate function: CountDistinct
*/
public class FunctionAggrCountDistinct  extends FunctionAggr implements IExpr, ICacheData
{
    String _key = new String();
    // key used for caching value
    /**
    * Aggregate function: CountDistinct
    * 
    * Return type is double
    */
    public FunctionAggrCountDistinct(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "countdistinct" + Interlocked.Increment(refVar___0).ToString();
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
        RowEnumerable re = this.getDataScope(rpt,row,refVar___1);
        bSave = refVar___1.getValue();
        if (re == null)
            return 0;
         
        int v = getValue(rpt);
        if (v < 0)
        {
            Object temp = new Object();
            int count = Math.Max(2, re.getLastRow() - re.getFirstRow());
            Hashtable ht = new Hashtable(count);
            for (Object __dummyForeachVar0 : re)
            {
                Row r = (Row)__dummyForeachVar0;
                temp = _Expr.evaluate(rpt,r);
                if (temp != null)
                {
                    Object o = ht[temp];
                    // search for it
                    if (o == null)
                    {
                        // if not found; add it to the hash table
                        ht.Add(temp, temp);
                    }
                     
                }
                 
            }
            v = ht.Count;
            if (bSave)
                setValue(rpt,v);
             
        }
         
        return (double)v;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double d = evaluateDouble(rpt,row);
        return Convert.ToDecimal(d);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDouble(rpt,row);
        return Convert.ToString(result);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDateTime(result);
    }

    private int getValue(fyiReporting.RDL.Report rpt) throws Exception {
        OInt oi = rpt.getCache().get(_key) instanceof OInt ? (OInt)rpt.getCache().get(_key) : (OInt)null;
        return oi == null ? -1 : oi.i;
    }

    private void setValue(fyiReporting.RDL.Report rpt, int i) throws Exception {
        rpt.getCache().addReplace(_key,new OInt(i));
    }

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(_key);
    }

}


