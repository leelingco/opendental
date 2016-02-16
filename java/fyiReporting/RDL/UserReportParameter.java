//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:27 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.ParameterLexer;
import fyiReporting.RDL.ReportParameter;

/**
* Public class used to pass user provided report parameters.
*/
public class UserReportParameter   
{
    fyiReporting.RDL.Report _rpt;
    ReportParameter _rp;
    Object[] _DefaultValue = new Object[]();
    String[] _DisplayValues = new String[]();
    Object[] _DataValues = new Object[]();
    public UserReportParameter(fyiReporting.RDL.Report rpt, ReportParameter rp) throws Exception {
        _rpt = rpt;
        _rp = rp;
    }

    /**
    * Name of the report paramenter.
    */
    public String getName() throws Exception {
        return _rp.getName().getNm();
    }

    /**
    * Type of the report parameter.
    */
    public TypeCode getdt() throws Exception {
        return _rp.getdt();
    }

    /**
    * Is parameter allowed to be null.
    */
    public boolean getNullable() throws Exception {
        return _rp.getNullable();
    }

    /**
    * Default value(s) of the parameter.
    */
    public Object[] getDefaultValue() throws Exception {
        if (_DefaultValue == null)
        {
            if (_rp.getDefaultValue() != null)
                _DefaultValue = _rp.getDefaultValue().valuesCalc(null);
             
        }
         
        return _DefaultValue;
    }

    /**
    * Is parameter allowed to be the empty string?
    */
    public boolean getAllowBlank() throws Exception {
        return _rp.getAllowBlank();
    }

    /**
    * Does parameters accept multiple values?
    */
    public boolean getMultiValue() throws Exception {
        return _rp.getMultiValue();
    }

    /**
    * Text used to prompt for the parameter.
    */
    public String getPrompt() throws Exception {
        return _rp.getPrompt();
    }

    /**
    * The display values for the parameter.  These may differ from the data values.
    */
    public String[] getDisplayValues() throws Exception {
        if (_DisplayValues == null)
        {
            if (_rp.getValidValues() != null)
                _DisplayValues = _rp.getValidValues().displayValues(_rpt);
             
        }
         
        return _DisplayValues;
    }

    /**
    * The data values of the parameter.
    */
    public Object[] getDataValues() throws Exception {
        if (_DataValues == null)
        {
            if (_rp.getValidValues() != null)
                _DataValues = _rp.getValidValues().dataValues(this._rpt);
             
        }
         
        return _DataValues;
    }

    /**
    * Obtain the data value from a (potentially) display value
    * 
    *  @param dvalue Display value
    *  @return The data value cooresponding to the display value.
    */
    private Object getDataValueFromDisplay(Object dvalue) throws Exception {
        Object val = dvalue;
        if (dvalue != null && getDisplayValues() != null && getDataValues() != null && getDisplayValues().Length == getDataValues().Length)
        {
            // this should always be true
            // if display values are provided then we may need to
            //  use the provided value with a display value and use
            //  the cooresponding data value
            String sval = dvalue.ToString();
            for (int index = 0;index < getDisplayValues().Length;index++)
            {
                if (getDisplayValues()[index].CompareTo(sval) == 0)
                {
                    val = getDataValues()[index];
                    break;
                }
                 
            }
        }
         
        return val;
    }

    /**
    * The runtime value of the parameter.
    */
    public Object getValue() throws Exception {
        return _rp.getRuntimeValue(this._rpt);
    }

    public void setValue(Object value) throws Exception {
        if (this.getMultiValue() && value instanceof String)
        {
            // treat this as a multiValue request
            setValues(parseValue(value instanceof String ? (String)value : (String)null));
            return ;
        }
         
        Object dvalue = getDataValueFromDisplay(value);
        _rp.setRuntimeValue(_rpt,dvalue);
    }

    /**
    * Take a string and parse it into multiple values
    * 
    *  @param v 
    *  @return
    */
    private ArrayList parseValue(String v) throws Exception {
        ParameterLexer pl = new ParameterLexer(v);
        return pl.lex();
    }

    /**
    * The runtime values of the parameter when MultiValue.
    */
    public ArrayList getValues() throws Exception {
        return _rp.getRuntimeValues(this._rpt);
    }

    public void setValues(ArrayList value) throws Exception {
        ArrayList ar = new ArrayList(value.Count);
        for (Object __dummyForeachVar0 : value)
        {
            Object v = (Object)__dummyForeachVar0;
            ar.Add(getDataValueFromDisplay(v));
        }
        _rp.setRuntimeValues(_rpt,ar);
    }

}


