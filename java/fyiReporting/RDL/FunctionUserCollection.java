//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.FunctionUserID;
import fyiReporting.RDL.FunctionUserLanguage;
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
* Handle references to the User collection.  e.g. User("UserID")
*/
public class FunctionUserCollection   implements IExpr
{
    private IDictionary _User = new IDictionary();
    private IExpr _ArgExpr;
    /**
    * obtain value of Field
    */
    public FunctionUserCollection(IDictionary user, IExpr arg) throws Exception {
        _User = user;
        _ArgExpr = arg;
    }

    public TypeCode getTypeCode() throws Exception {
        return TypeCode.String;
    }

    // all the user types happen to be string
    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        _ArgExpr = _ArgExpr.constantOptimization();
        if (_ArgExpr.isConstant())
        {
            String o = _ArgExpr.evaluateString(null,null);
            if (o == null)
                throw new Exception("User collection argument is null");
             
            String lo = o.ToLower();
            if (StringSupport.equals(lo, "userid"))
                return new FunctionUserID();
             
            if (StringSupport.equals(lo, "language"))
                return new FunctionUserLanguage();
             
            throw new Exception(String.Format("User collection argument {0} is invalid.", o));
        }
         
        return this;
    }

    //
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        if (rpt == null)
            return null;
         
        String u = _ArgExpr.evaluateString(rpt,row);
        if (u == null)
            return null;
         
        System.String.APPLY __dummyScrutVar0 = u.ToLower();
        if (__dummyScrutVar0.equals("userid"))
        {
            return rpt.getUserID();
        }
        else if (__dummyScrutVar0.equals("language"))
        {
            return rpt.getClientLanguage() == null ? CultureInfo.CurrentCulture.ThreeLetterISOLanguageName : rpt.getClientLanguage();
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


