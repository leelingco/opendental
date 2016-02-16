//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import fyiReporting.RDL.FunctionAggr;
import fyiReporting.RDL.ICacheData;
import fyiReporting.RDL.IExpr;
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
* Aggregate function: Varp
*/
public class FunctionAggrVarp  extends FunctionAggr implements IExpr, ICacheData
{
    String _key = new String();
    // key for cache when scope is dataset we can cache the result
    /**
    * Aggregate function: Varp = (n sum(square(x)) - square((sum(x))) / n*n
    * Stdev assumes values are a sample of the population of data.  If the data
    * is the entire representation then use Stdevp.
    */
    public FunctionAggrVarp(List<ICacheData> dataCache, IExpr e, Object scp) throws Exception {
        super(e, scp);
        RefSupport refVar___0 = new RefSupport(Parser.Counter);
        _key = "aggrvar" + Interlocked.Increment(refVar___0).ToString();
        Parser.Counter = refVar___0.getValue();
        dataCache.Add(this);
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Double;
    }

    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double d = evaluateDouble(rpt,row);
        if (d.CompareTo(double.NaN) == 0)
            return null;
         
        return (Object)d;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        boolean bSave = true;
        RefSupport<boolean> refVar___1 = new RefSupport<boolean>();
        IEnumerable re = this.getDataScope(rpt,row,refVar___1);
        bSave = refVar___1.getValue();
        if (re == null)
            return double.NaN;
         
        ODouble v = getValue(rpt);
        if (v != null)
            return v.d;
         
        double sum = 0;
        double sum2 = 0;
        int count = 0;
        double temp = new double();
        for (Object __dummyForeachVar0 : re)
        {
            Row r = (Row)__dummyForeachVar0;
            temp = _Expr.evaluateDouble(rpt,r);
            if (temp.CompareTo(double.NaN) != 0)
            {
                sum += temp;
                sum2 += (temp * temp);
                count++;
            }
             
        }
        double result = new double();
        if (count > 0)
            result = ((count * sum2 - sum * sum) / (count * count));
        else
            result = double.NaN; 
        if (bSave)
            setValue(rpt,result);
         
        return result;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double d = evaluateDouble(rpt,row);
        if (d.CompareTo(double.NaN) == 0)
            return double.MinValue;
         
        return Convert.ToDecimal(d);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        double result = evaluateDouble(rpt,row);
        if (result.CompareTo(double.NaN) == 0)
            return null;
         
        return Convert.ToString(result);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object result = evaluate(rpt,row);
        return Convert.ToDateTime(result);
    }

    private ODouble getValue(fyiReporting.RDL.Report rpt) throws Exception {
        return rpt.getCache().get(_key) instanceof ODouble ? (ODouble)rpt.getCache().get(_key) : (ODouble)null;
    }

    private void setValue(fyiReporting.RDL.Report rpt, double d) throws Exception {
        rpt.getCache().addReplace(_key,new ODouble(d));
    }

    public void clearCache(fyiReporting.RDL.Report rpt) throws Exception {
        rpt.getCache().remove(_key);
    }

}


