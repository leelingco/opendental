//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.FunctionReportParameter;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.ReportParameter;
import fyiReporting.RDL.ReportParameterMethodEnum;
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
* Obtain the runtime value of a report parameter label.
*/
public class FunctionReportParameterLabel  extends FunctionReportParameter 
{
    /**
    * obtain value of ReportParameter
    */
    public FunctionReportParameterLabel(ReportParameter parm) throws Exception {
        super(parm);
    }

    public TypeCode getTypeCode() throws Exception {
        if (this.getParameterMethod() == ReportParameterMethodEnum.Value)
            return TypeCode.String;
        else
            return super.getTypeCode(); 
    }

    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    // not a constant expression
    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String v = super.evaluateString(rpt,row);
        if (p.getValidValues() == null)
            return v;
         
        String[] displayValues = p.getValidValues().displayValues(rpt);
        Object[] dataValues = p.getValidValues().dataValues(rpt);
        for (int i = 0;i < dataValues.Length;i++)
        {
            if (StringSupport.equals(dataValues[i].ToString(), v))
                return displayValues[i];
             
        }
        return v;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String r = evaluateString(rpt,row);
        return r == null ? double.MinValue : Convert.ToDouble(r);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String r = evaluateString(rpt,row);
        return r == null ? double.MinValue : Convert.ToDecimal(r);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return (String)evaluate(rpt,row);
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String r = evaluateString(rpt,row);
        return r == null ? DateTime.MinValue : Convert.ToDateTime(r);
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        String r = evaluateString(rpt,row);
        return StringSupport.equals(r.ToLower(), "true") ? true : false;
    }

}


