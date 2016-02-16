//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:31 PM
//

package fyiReporting.RDL;

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
* Obtain the runtime value of a Report parameter
*/
public class FunctionReportParameter   implements IExpr
{
    protected ReportParameter p;
    ReportParameterMethodEnum _type = ReportParameterMethodEnum.Value;
    IExpr _arg;
    // when MultiValue parameter; methods may need arguments
    /**
    * obtain value of ReportParameter
    */
    public FunctionReportParameter(ReportParameter parm) throws Exception {
        p = parm;
        _type = ReportParameterMethodEnum.Value;
        _arg = null;
    }

    public ReportParameterMethodEnum getParameterMethod() throws Exception {
        return _type;
    }

    public void setParameterMethod(ReportParameterMethodEnum value) throws Exception {
        _type = value;
    }

    public void setParameterMethod(String pm, IExpr[] args) throws Exception {
        if (!this.p.getMultiValue())
            throw new ArgumentException(String.Format("{0} must be a MultiValue parameter to accept methods", this.p.getName().getNm()));
         
        if (pm == null)
        {
            _type = ReportParameterMethodEnum.Index;
        }
        else
        {
            System.String __dummyScrutVar0 = pm;
            if (__dummyScrutVar0.equals("Contains"))
            {
                _type = ReportParameterMethodEnum.Contains;
            }
            else if (__dummyScrutVar0.equals("BinarySearch"))
            {
                _type = ReportParameterMethodEnum.BinarySearch;
            }
            else if (__dummyScrutVar0.equals("Count"))
            {
                _type = ReportParameterMethodEnum.Count;
                if (args != null)
                    throw new ArgumentException("Count does not have any arguments.");
                 
            }
            else if (__dummyScrutVar0.equals("IndexOf"))
            {
                _type = ReportParameterMethodEnum.IndexOf;
            }
            else if (__dummyScrutVar0.equals("LastIndexOf"))
            {
                _type = ReportParameterMethodEnum.LastIndexOf;
            }
            else if (__dummyScrutVar0.equals("Value"))
            {
                _type = ReportParameterMethodEnum.Value;
            }
            else
            {
                throw new ArgumentException(String.Format("{0} is an unknown array method.", pm));
            }      
        } 
        if (_type != ReportParameterMethodEnum.Count)
        {
            if (args == null || args.Length != 1)
                throw new ArgumentException(String.Format("{0} must have exactly one argument.", pm));
             
            _arg = args[0];
        }
         
        return ;
    }

    public TypeCode getTypeCode() throws Exception {
        switch(_type)
        {
            case Contains: 
                return TypeCode.Boolean;
            case BinarySearch: 
            case Count: 
            case IndexOf: 
            case LastIndexOf: 
                return TypeCode.Int32;
            case Value: 
                return p.getMultiValue() ? TypeCode.Object : p.getdt();
            case Index: 
            default: 
                return p.getdt();
        
        }
    }

    // MultiValue type is really ArrayList
    public boolean isConstant() throws Exception {
        return false;
    }

    public IExpr constantOptimization() throws Exception {
        return this;
    }

    // not a constant expression
    // Evaluate is for interpretation  (and is relatively slow)
    public Object evaluate(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        return this.p.getMultiValue() ? evaluateMV(rpt,row) : p.getRuntimeValue(rpt);
    }

    private Object evaluateMV(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        ArrayList ar = p.getRuntimeValues(rpt);
        Object va = this._arg == null ? null : _arg.evaluate(rpt,row);
        switch(_type)
        {
            case Value: 
                return ar;
            case Contains: 
                return ar.Contains(va);
            case BinarySearch: 
                return ar.BinarySearch(va);
            case Count: 
                return ar.Count;
            case IndexOf: 
                return ar.IndexOf(va);
            case LastIndexOf: 
                return ar.LastIndexOf(va);
            case Index: 
                int i = Convert.ToInt32(va);
                return ar[i];
            default: 
                throw new Exception("Internal error: unknown Report Parameter method");
        
        }
    }

    public double evaluateDouble(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object rtv = evaluate(rpt,row);
        if (rtv == null)
            return Double.NaN;
         
        TypeCode __dummyScrutVar3 = this.getTypeCode();
        if (__dummyScrutVar3.equals(TypeCode.Double))
        {
            return ((Double)rtv);
        }
        else if (__dummyScrutVar3.equals(TypeCode.Object))
        {
            return Double.NaN;
        }
        else if (__dummyScrutVar3.equals(TypeCode.Int32))
        {
            return (double)((Integer)rtv);
        }
        else if (__dummyScrutVar3.equals(TypeCode.Boolean))
        {
            return Convert.ToDouble((Boolean)rtv);
        }
        else if (__dummyScrutVar3.equals(TypeCode.String))
        {
            return Convert.ToDouble((String)rtv);
        }
        else if (__dummyScrutVar3.equals(TypeCode.DateTime))
        {
            return Convert.ToDouble((DateTime)rtv);
        }
        else
        {
            return Double.NaN;
        }      
    }

    public double evaluateDecimal(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object rtv = evaluate(rpt,row);
        if (rtv == null)
            return Decimal.MinValue;
         
        TypeCode __dummyScrutVar4 = this.getTypeCode();
        if (__dummyScrutVar4.equals(TypeCode.Double))
        {
            return Convert.ToDecimal((Double)rtv);
        }
        else if (__dummyScrutVar4.equals(TypeCode.Object))
        {
            return Decimal.MinValue;
        }
        else if (__dummyScrutVar4.equals(TypeCode.Int32))
        {
            return Convert.ToDecimal((Integer)rtv);
        }
        else if (__dummyScrutVar4.equals(TypeCode.Boolean))
        {
            return Convert.ToDecimal((Boolean)rtv);
        }
        else if (__dummyScrutVar4.equals(TypeCode.String))
        {
            return Convert.ToDecimal((String)rtv);
        }
        else if (__dummyScrutVar4.equals(TypeCode.DateTime))
        {
            return Convert.ToDecimal((DateTime)rtv);
        }
        else
        {
            return Decimal.MinValue;
        }      
    }

    public String evaluateString(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object rtv = evaluate(rpt,row);
        if (rtv == null)
            return null;
         
        return rtv.ToString();
    }

    public DateTime evaluateDateTime(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object rtv = evaluate(rpt,row);
        if (rtv == null)
            return DateTime.MinValue;
         
        TypeCode __dummyScrutVar5 = this.getTypeCode();
        if (__dummyScrutVar5.equals(TypeCode.Double))
        {
            return Convert.ToDateTime((Double)rtv);
        }
        else if (__dummyScrutVar5.equals(TypeCode.Object))
        {
            return DateTime.MinValue;
        }
        else if (__dummyScrutVar5.equals(TypeCode.Int32))
        {
            return Convert.ToDateTime((Integer)rtv);
        }
        else if (__dummyScrutVar5.equals(TypeCode.Boolean))
        {
            return Convert.ToDateTime((Boolean)rtv);
        }
        else if (__dummyScrutVar5.equals(TypeCode.String))
        {
            return Convert.ToDateTime((String)rtv);
        }
        else if (__dummyScrutVar5.equals(TypeCode.DateTime))
        {
            return (DateTime)rtv;
        }
        else
        {
            return DateTime.MinValue;
        }      
    }

    public boolean evaluateBoolean(fyiReporting.RDL.Report rpt, Row row) throws Exception {
        Object rtv = evaluate(rpt,row);
        if (rtv == null)
            return false;
         
        TypeCode __dummyScrutVar6 = this.getTypeCode();
        if (__dummyScrutVar6.equals(TypeCode.Double))
        {
            return Convert.ToBoolean((Double)rtv);
        }
        else if (__dummyScrutVar6.equals(TypeCode.Object))
        {
            return false;
        }
        else if (__dummyScrutVar6.equals(TypeCode.Int32))
        {
            return Convert.ToBoolean((Integer)rtv);
        }
        else if (__dummyScrutVar6.equals(TypeCode.Boolean))
        {
            return (Boolean)rtv;
        }
        else if (__dummyScrutVar6.equals(TypeCode.String))
        {
            return Convert.ToBoolean((String)rtv);
        }
        else if (__dummyScrutVar6.equals(TypeCode.DateTime))
        {
            return Convert.ToBoolean((DateTime)rtv);
        }
        else
        {
            return false;
        }      
    }

}


