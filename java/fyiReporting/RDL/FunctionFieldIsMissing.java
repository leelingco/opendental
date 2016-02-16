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
* IsMissing attribute
*/
public class FunctionFieldIsMissing  extends FunctionField 
{
    /**
    * Determine if value of Field is available
    */
    public FunctionFieldIsMissing(Field fld) throws Exception {
        super(fld);
    }

    public FunctionFieldIsMissing(String method) throws Exception {
        super(method);
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Boolean;
    }

    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    // not a constant
    //
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateBoolean(rpt,row);
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateBoolean(rpt,row) ? 1 : 0;
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateBoolean(rpt,row) ? 1m : 0m;
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return evaluateBoolean(rpt,row) ? "True" : "False";
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return DateTime.MinValue;
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object o = super.evaluate(rpt,row);
        return o == null ? true : false;
    }

}


