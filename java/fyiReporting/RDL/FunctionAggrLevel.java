//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.FunctionAggr;
import fyiReporting.RDL.Grouping;
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
* Aggregate function: Level
*/
public class FunctionAggrLevel  extends FunctionAggr implements IExpr
{
    /**
    * Aggregate function: Level
    * 
    * Return type is double
    */
    public FunctionAggrLevel(Object scp) throws Exception {
        super(null, scp);
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Double;
    }

    // although it is always an integer
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return (Object)evaluateDouble(rpt,row);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null || this._Scope == null)
            return 0;
         
        Grouping g = this._Scope instanceof Grouping ? (Grouping)this._Scope : (Grouping)null;
        if (g == null || g.getParentGroup() == null)
            return 0;
         
        return row.getLevel();
    }

    //			GroupEntry ge = row.R.CurrentGroups[g.Index];	// current group entry
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

}


