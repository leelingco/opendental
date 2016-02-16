//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import CS2JNet.System.StringSupport;
import fyiReporting.RDL.DataType;
import fyiReporting.RDL.DefaultValue;
import fyiReporting.RDL.Name;
import fyiReporting.RDL.ReportDefn;
import fyiReporting.RDL.ReportLink;
import fyiReporting.RDL.TrueFalseAuto;
import fyiReporting.RDL.TrueFalseAutoEnum;
import fyiReporting.RDL.ValidValues;
import fyiReporting.RDL.XmlUtil;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Represent a report parameter (name, default value, runtime value,
*/
public class ReportParameter  extends ReportLink 
{
    Name _Name;
    // Name of the parameter
    // Note: Parameter names need only be
    // unique within the containing Parameters collection
    TypeCode _dt = new TypeCode();
    // The data type of the parameter
    boolean _NumericType = false;
    // true if _dt is a numeric type
    boolean _Nullable = new boolean();
    // Indicates the value for this parameter is allowed to be Null.
    DefaultValue _DefaultValue;
    // Default value to use for the parameter (if not provided by the user)
    // If no value is provided as a part of the
    //	  definition or by the user, the value is
    // null. Required if there is no Prompt and
    //  either Nullable is False or a ValidValues
    // list is provided that does not contain Null
    // (an omitted Value).
    boolean _AllowBlank = new boolean();
    // Indicates the value for this parameter is
    // allowed to be the empty string. Ignored
    // if DataType is not string.
    String _Prompt = new String();
    // The user prompt to display when asking
    // for parameter values
    // If omitted, the user should not be
    // prompted for a value for this parameter.
    ValidValues _ValidValues;
    // Possible values for the parameter (for an
    //	end-user prompting interface)
    boolean _Hidden = false;
    // indicates parameter should not be showed to user
    boolean _MultiValue = false;
    // indicates parameter is a collection - expressed as 0 based arrays Parameters!p1.Value(0)
    TrueFalseAutoEnum _UsedInQuery = TrueFalseAutoEnum.True;
    // Enum True | False | Auto (default)
    //	Indicates whether or not the parameter is
    //	used in a query in the report. This is
    //	needed to determine if the queries need
    //	to be re-executed if the parameter
    //	changes. Auto indicates the
    //	UsedInQuery setting should be
    //	autodetected as follows: True if the
    //	parameter is referenced in any query
    //	value expression.
    public ReportParameter(ReportDefn r, ReportLink p, XmlNode xNode) throws Exception {
        super(r, p);
        _Name = null;
        _dt = TypeCode.Object;
        _Nullable = true;
        _DefaultValue = null;
        _AllowBlank = true;
        _Prompt = null;
        _ValidValues = null;
        _UsedInQuery = TrueFalseAutoEnum.Auto;
        for (Object __dummyForeachVar0 : xNode.Attributes)
        {
            // Run thru the attributes
            XmlAttribute xAttr = (XmlAttribute)__dummyForeachVar0;
            Name __dummyScrutVar0 = xAttr.Name;
            if (__dummyScrutVar0.equals("Name"))
            {
                _Name = new Name(xAttr.Value);
            }
             
        }
        for (Object __dummyForeachVar1 : xNode.ChildNodes)
        {
            // Loop thru all the child nodes
            XmlNode xNodeLoop = (XmlNode)__dummyForeachVar1;
            if (xNodeLoop.NodeType != XmlNodeType.Element)
                continue;
             
            Name __dummyScrutVar1 = xNodeLoop.Name;
            if (__dummyScrutVar1.equals("DataType"))
            {
                _dt = DataType.GetStyle(xNodeLoop.InnerText, this.OwnerReport);
                _NumericType = DataType.isNumeric(_dt);
            }
            else if (__dummyScrutVar1.equals("Nullable"))
            {
                _Nullable = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("DefaultValue"))
            {
                _DefaultValue = new DefaultValue(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("AllowBlank"))
            {
                _AllowBlank = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("Prompt"))
            {
                _Prompt = xNodeLoop.InnerText;
            }
            else if (__dummyScrutVar1.equals("Hidden"))
            {
                _Hidden = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
                OwnerReport.rl.logError(4,"ReportParameter element Hidden is currently ignored.");
            }
            else // TODO
            if (__dummyScrutVar1.equals("MultiValue"))
            {
                _MultiValue = XmlUtil.Boolean(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else if (__dummyScrutVar1.equals("ValidValues"))
            {
                _ValidValues = new ValidValues(r,this,xNodeLoop);
            }
            else if (__dummyScrutVar1.equals("UsedInQuery"))
            {
                _UsedInQuery = TrueFalseAuto.GetStyle(xNodeLoop.InnerText, OwnerReport.rl);
            }
            else
            {
                // don't know this element - log it
                OwnerReport.rl.logError(4,"Unknown ReportParameter element '" + xNodeLoop.Name + "' ignored.");
            }         
        }
        if (_Name == null)
            OwnerReport.rl.logError(8,"ReportParameter name is required but not specified.");
         
        if (_dt == TypeCode.Object)
            OwnerReport.rl.LogError(8, String.Format("ReportParameter DataType is required but not specified or invalid for {0}.", _Name == null ? "<unknown name>" : _Name.getNm()));
         
    }

    public void finalPass() throws Exception {
        if (_DefaultValue != null)
            _DefaultValue.finalPass();
         
        if (_ValidValues != null)
            _ValidValues.finalPass();
         
        return ;
    }

    public Name getName() throws Exception {
        return _Name;
    }

    public void setName(Name value) throws Exception {
        _Name = value;
    }

    public Object getRuntimeValue(fyiReporting.RDL.Report rpt) throws Exception {
        Object rtv = rpt == null ? null : rpt.getCache().get(this,"runtimevalue");
        if (rtv != null)
            return rtv;
         
        if (_DefaultValue == null)
            return null;
         
        Object[] result = _DefaultValue.getValue(rpt);
        if (result == null)
            return null;
         
        Object v = result[0];
        if (v instanceof String && _NumericType)
            v = convertStringToNumber((String)v);
         
        rtv = Convert.ChangeType(v, _dt);
        if (rpt != null)
            rpt.getCache().add(this,"runtimevalue",rtv);
         
        return rtv;
    }

    public ArrayList getRuntimeValues(fyiReporting.RDL.Report rpt) throws Exception {
        ArrayList rtv = rpt == null ? null : (ArrayList)rpt.getCache().get(this,"rtvs");
        if (rtv != null)
            return rtv;
         
        if (_DefaultValue == null)
            return null;
         
        Object[] result = _DefaultValue.getValue(rpt);
        if (result == null)
            return null;
         
        ArrayList ar = new ArrayList(result.Length);
        for (Object __dummyForeachVar2 : result)
        {
            Object v = (Object)__dummyForeachVar2;
            Object nv = v;
            if (nv instanceof String && _NumericType)
                nv = convertStringToNumber((String)nv);
             
            ar.Add(Convert.ChangeType(nv, _dt));
        }
        if (rpt != null)
            rpt.getCache().add(this,"rtvs",ar);
         
        return ar;
    }

    public void setRuntimeValue(fyiReporting.RDL.Report rpt, Object v) throws Exception {
        if (this.getMultiValue())
        {
            // ok to only set one parameter of multiValue;  but we still save as MultiValue
            ArrayList ar = new ArrayList(1);
            ar.Add(v);
            setRuntimeValues(rpt,ar);
            return ;
        }
         
        Object rtv = new Object();
        if (!getAllowBlank() && _dt == TypeCode.String && StringSupport.equals((String)v, ""))
            throw new ArgumentException(String.Format("Empty string isn't allowed for {0}.", getName().getNm()));
         
        try
        {
            if (v instanceof String && _NumericType)
                v = convertStringToNumber((String)v);
             
            rtv = Convert.ChangeType(v, _dt);
        }
        catch (Exception e)
        {
            // illegal parameter passed
            String err = "Illegal parameter value for '" + getName().getNm() + "' provided.  Value =" + v.ToString();
            if (rpt == null)
                OwnerReport.rl.logError(4,err);
            else
                rpt.rl.logError(4,err); 
            throw new ArgumentException(String.Format("Unable to convert '{0}' to {1} for {2}", v, _dt, getName().getNm()), e);
        }

        rpt.getCache().addReplace(this,"runtimevalue",rtv);
    }

    public void setRuntimeValues(fyiReporting.RDL.Report rpt, ArrayList vs) throws Exception {
        if (!this.getMultiValue())
            throw new ArgumentException(String.Format("{0} is not a MultiValue parameter. SetRuntimeValues only valid for MultiValue parameters", this.getName().getNm()));
         
        ArrayList ar = new ArrayList(vs.Count);
        for (Object __dummyForeachVar3 : vs)
        {
            Object v = (Object)__dummyForeachVar3;
            Object rtv = new Object();
            if (!getAllowBlank() && _dt == TypeCode.String && StringSupport.equals((String)v, ""))
                throw new ArgumentException(String.Format("Empty string isn't allowed for {0}.", getName().getNm()));
             
            try
            {
                Object nv = v;
                if (nv instanceof String && _NumericType)
                    nv = convertStringToNumber((String)nv);
                 
                rtv = Convert.ChangeType(nv, _dt);
                ar.Add(rtv);
            }
            catch (Exception e)
            {
                // illegal parameter passed
                String err = "Illegal parameter value for '" + getName().getNm() + "' provided.  Value =" + v.ToString();
                if (rpt == null)
                    OwnerReport.rl.logError(4,err);
                else
                    rpt.rl.logError(4,err); 
                throw new ArgumentException(String.Format("Unable to convert '{0}' to {1} for {2}", v, _dt, getName().getNm()), e);
            }
        
        }
        rpt.getCache().addReplace(this,"rtvs",ar);
    }

    private Object convertStringToNumber(String newv) throws Exception {
        // remove any commas, currency symbols (internationalized)
        NumberFormatInfo nfi = NumberFormatInfo.CurrentInfo;
        newv = newv.Replace(nfi.NumberGroupSeparator, "");
        newv = newv.Replace(nfi.CurrencySymbol, "");
        return newv;
    }

    public TypeCode getdt() throws Exception {
        return _dt;
    }

    public void setdt(TypeCode value) throws Exception {
        _dt = value;
    }

    public boolean getNullable() throws Exception {
        return _Nullable;
    }

    public void setNullable(boolean value) throws Exception {
        _Nullable = value;
    }

    public boolean getHidden() throws Exception {
        return _Hidden;
    }

    public void setHidden(boolean value) throws Exception {
        _Hidden = value;
    }

    public boolean getMultiValue() throws Exception {
        return _MultiValue;
    }

    public void setMultiValue(boolean value) throws Exception {
        _MultiValue = value;
    }

    public DefaultValue getDefaultValue() throws Exception {
        return _DefaultValue;
    }

    public void setDefaultValue(DefaultValue value) throws Exception {
        _DefaultValue = value;
    }

    public boolean getAllowBlank() throws Exception {
        return _AllowBlank;
    }

    public void setAllowBlank(boolean value) throws Exception {
        _AllowBlank = value;
    }

    public String getPrompt() throws Exception {
        return _Prompt;
    }

    public void setPrompt(String value) throws Exception {
        _Prompt = value;
    }

    public ValidValues getValidValues() throws Exception {
        return _ValidValues;
    }

    public void setValidValues(ValidValues value) throws Exception {
        _ValidValues = value;
    }

    public TrueFalseAutoEnum getUsedInQuery() throws Exception {
        return _UsedInQuery;
    }

    public void setUsedInQuery(TrueFalseAutoEnum value) throws Exception {
        _UsedInQuery = value;
    }

}


