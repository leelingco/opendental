//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:30 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.CodeModules;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Row;
import fyiReporting.RDL.XmlUtil;

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
* Process a custom static method invokation.
*/
public class FunctionCustomStatic   implements IExpr
{
    String _Cls = new String();
    // class name
    String _Func = new String();
    // function/operator
    IExpr[] _Args = new IExpr[]();
    // arguments
    CodeModules _Cm;
    // the loaded assemblies
    TypeCode _ReturnTypeCode = new TypeCode();
    // the return type
    Type[] _ArgTypes = new Type[]();
    // argument types
    /**
    * passed class name, function name, and args for evaluation
    */
    public FunctionCustomStatic(CodeModules cm, String c, String f, IExpr[] a, TypeCode type) throws Exception {
        _Cls = c;
        _Func = f;
        _Args = a;
        _Cm = cm;
        _ReturnTypeCode = type;
        _ArgTypes = new Type[a.Length];
        int i = 0;
        for (Object __dummyForeachVar0 : a)
        {
            IExpr ex = (IExpr)__dummyForeachVar0;
            _ArgTypes[i++] = XmlUtil.getTypeFromTypeCode(ex.getTypeCode());
        }
    }

    public TypeCode getTypeCode() throws Exception {
        return _ReturnTypeCode;
    }

    public boolean isConstant() throws Exception {
        return false;
    }

    // Can't know what the function does
    public IExpr constantOptimization() throws Exception {
        for (int i = 0;i < _Args.GetLength(0);i++)
        {
            // Do constant optimization on all the arguments
            IExpr e = (IExpr)_Args[i];
            _Args[i] = e.constantOptimization();
        }
        return this;
    }

    // Can't assume that the function doesn't vary
    //   based on something other than the args e.g. Now()
    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        // get the results
        Object[] argResults = new Object[_Args.Length];
        int i = 0;
        boolean bUseArg = true;
        for (Object __dummyForeachVar1 : _Args)
        {
            IExpr a = (IExpr)__dummyForeachVar1;
            argResults[i] = a.evaluate(rpt,row);
            if (argResults[i] != null && argResults[i].GetType() != _ArgTypes[i])
                bUseArg = false;
             
            i++;
        }
        // we build the arguments based on the type
        Type[] argTypes = bUseArg ? _ArgTypes : Type.GetTypeArray(argResults);
        // We can definitely optimize this by caching some info TODO
        // Get ready to call the function
        Object returnVal = new Object();
        Type theClassType = _Cm.get___idx(_Cls);
        MethodInfo mInfo = theClassType.GetMethod(_Func, argTypes);
        returnVal = mInfo.Invoke(theClassType, argResults);
        return returnVal;
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDouble(evaluate(rpt,row));
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDecimal(evaluate(rpt,row));
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToString(evaluate(rpt,row));
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToDateTime(evaluate(rpt,row));
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return Convert.ToBoolean(evaluate(rpt,row));
    }

    public String getCls() throws Exception {
        return _Cls;
    }

    public void setCls(String value) throws Exception {
        _Cls = value;
    }

    public String getFunc() throws Exception {
        return _Func;
    }

    public void setFunc(String value) throws Exception {
        _Func = value;
    }

    public IExpr[] getArgs() throws Exception {
        return _Args;
    }

    public void setArgs(IExpr[] value) throws Exception {
        _Args = value;
    }

}


