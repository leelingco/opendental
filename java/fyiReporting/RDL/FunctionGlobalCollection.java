//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.FunctionExecutionTime;
import fyiReporting.RDL.FunctionPageNumber;
import fyiReporting.RDL.FunctionReportFolder;
import fyiReporting.RDL.FunctionReportName;
import fyiReporting.RDL.FunctionTotalPages;
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
* Global fields accessed dynamically. i.e. Globals(expr).
*/
public class FunctionGlobalCollection   implements IExpr
{
    private IDictionary _Globals = new IDictionary();
    private IExpr _ArgExpr;
    /**
    * obtain value of Field
    */
    public FunctionGlobalCollection(IDictionary globals, IExpr arg) throws Exception {
        _Globals = globals;
        _ArgExpr = arg;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.Object;
    }

    // we don't know the typecode until we run the function
    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        _ArgExpr = _ArgExpr.constantOptimization();
        if (_ArgExpr.isConstant())
        {
            String o = _ArgExpr.evaluateString(null,null);
            if (o == null)
                throw new Exception("Globals collection argument is null");
             
            System.String.APPLY __dummyScrutVar0 = o.ToLower();
            if (__dummyScrutVar0.equals("pagenumber"))
            {
                return new FunctionPageNumber();
            }
            else if (__dummyScrutVar0.equals("totalpages"))
            {
                return new FunctionTotalPages();
            }
            else if (__dummyScrutVar0.equals("executiontime"))
            {
                return new FunctionExecutionTime();
            }
            else if (__dummyScrutVar0.equals("reportfolder"))
            {
                return new FunctionReportFolder();
            }
            else if (__dummyScrutVar0.equals("reportname"))
            {
                return new FunctionReportName();
            }
            else
            {
                throw new Exception(String.Format("Globals collection argument '{0}' is unknown.", o));
            }     
        }
         
        return this;
    }

    //
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (rpt == null)
            return null;
         
        String g = _ArgExpr.evaluateString(rpt,row);
        if (g == null)
            return null;
         
        System.String.APPLY __dummyScrutVar1 = g.ToLower();
        if (__dummyScrutVar1.equals("pagenumber"))
        {
            return rpt.PageNumber;
        }
        else if (__dummyScrutVar1.equals("totalpages"))
        {
            return rpt.TotalPages;
        }
        else if (__dummyScrutVar1.equals("executiontime"))
        {
            return rpt.ExecutionTime;
        }
        else if (__dummyScrutVar1.equals("reportfolder"))
        {
            return rpt.getFolder();
        }
        else if (__dummyScrutVar1.equals("reportname"))
        {
            return rpt.getName();
        }
        else
        {
            return null;
        }     
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return Double.NaN;
         
        return Convert.ToDouble(evaluate(rpt,row), NumberFormatInfo.InvariantInfo);
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return double.MinValue;
         
        return Convert.ToDecimal(evaluate(rpt,row), NumberFormatInfo.InvariantInfo);
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return null;
         
        return Convert.ToString(evaluate(rpt,row));
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return DateTime.MinValue;
         
        return Convert.ToDateTime(evaluate(rpt,row));
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (row == null)
            return false;
         
        return Convert.ToBoolean(evaluate(rpt,row));
    }

}


