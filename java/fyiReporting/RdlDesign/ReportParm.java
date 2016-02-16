//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:16 PM
//

package fyiReporting.RdlDesign;

import fyiReporting.RdlDesign.ParameterValueItem;

public class ReportParm   
{
    String _Name = new String();
    String _Prompt = new String();
    String _DataType = new String();
    boolean _bDefault = true;
    // use default value if true otherwise DataSetName
    List<String> _DefaultValue = new List<String>();
    // list of strings
    String _DefaultDSRDataSetName = new String();
    // DefaultValues DataSetReference DataSetName
    String _DefaultDSRValueField = new String();
    // DefaultValues DataSetReference ValueField
    boolean _bValid = true;
    // use valid value if true otherwise DataSetName
    List<ParameterValueItem> _ValidValues = new List<ParameterValueItem>();
    // list of ParameterValueItem
    String _ValidValuesDSRDataSetName = new String();
    // ValidValues DataSetReference DataSetName
    String _ValidValuesDSRValueField = new String();
    // ValidValues DataSetReference ValueField
    String _ValidValuesDSRLabelField = new String();
    // ValidValues DataSetReference LabelField
    boolean _AllowNull = new boolean();
    boolean _AllowBlank = new boolean();
    boolean _MultiValue = new boolean();
    public ReportParm(String name) throws Exception {
        _Name = name;
        _DataType = "String";
    }

    public String getName() throws Exception {
        return _Name;
    }

    public void setName(String value) throws Exception {
        _Name = value;
    }

    public String getPrompt() throws Exception {
        return _Prompt;
    }

    public void setPrompt(String value) throws Exception {
        _Prompt = value;
    }

    public String getDataType() throws Exception {
        return _DataType;
    }

    public void setDataType(String value) throws Exception {
        _DataType = value;
    }

    public boolean getValid() throws Exception {
        return _bValid;
    }

    public void setValid(boolean value) throws Exception {
        _bValid = value;
    }

    public List<ParameterValueItem> getValidValues() throws Exception {
        return _ValidValues;
    }

    public void setValidValues(List<ParameterValueItem> value) throws Exception {
        _ValidValues = value;
    }

    public String getValidValuesDisplay() throws Exception {
        if (_ValidValues == null || _ValidValues.Count == 0)
            return "";
         
        StringBuilder sb = new StringBuilder();
        boolean bFirst = true;
        for (Object __dummyForeachVar0 : _ValidValues)
        {
            ParameterValueItem pvi = (ParameterValueItem)__dummyForeachVar0;
            if (bFirst)
                bFirst = false;
            else
                sb.Append(", "); 
            if (pvi.Label != null)
                sb.AppendFormat("{0}={1}", pvi.Value, pvi.Label);
            else
                sb.Append(pvi.Value); 
        }
        return sb.ToString();
    }

    public boolean getDefault() throws Exception {
        return _bDefault;
    }

    public void setDefault(boolean value) throws Exception {
        _bDefault = value;
    }

    public List<String> getDefaultValue() throws Exception {
        return _DefaultValue;
    }

    public void setDefaultValue(List<String> value) throws Exception {
        _DefaultValue = value;
    }

    public String getDefaultValueDisplay() throws Exception {
        if (_DefaultValue == null || _DefaultValue.Count == 0)
            return "";
         
        StringBuilder sb = new StringBuilder();
        boolean bFirst = true;
        for (Object __dummyForeachVar1 : _DefaultValue)
        {
            String dv = (String)__dummyForeachVar1;
            if (bFirst)
                bFirst = false;
            else
                sb.Append(", "); 
            sb.Append(dv);
        }
        return sb.ToString();
    }

    public boolean getAllowNull() throws Exception {
        return _AllowNull;
    }

    public void setAllowNull(boolean value) throws Exception {
        _AllowNull = value;
    }

    public boolean getAllowBlank() throws Exception {
        return _AllowBlank;
    }

    public void setAllowBlank(boolean value) throws Exception {
        _AllowBlank = value;
    }

    public boolean getMultiValue() throws Exception {
        return _MultiValue;
    }

    public void setMultiValue(boolean value) throws Exception {
        _MultiValue = value;
    }

    public String getDefaultDSRDataSetName() throws Exception {
        return _DefaultDSRDataSetName;
    }

    public void setDefaultDSRDataSetName(String value) throws Exception {
        _DefaultDSRDataSetName = value;
    }

    public String getDefaultDSRValueField() throws Exception {
        return _DefaultDSRValueField;
    }

    public void setDefaultDSRValueField(String value) throws Exception {
        _DefaultDSRValueField = value;
    }

    public String getValidValuesDSRDataSetName() throws Exception {
        return _ValidValuesDSRDataSetName;
    }

    public void setValidValuesDSRDataSetName(String value) throws Exception {
        _ValidValuesDSRDataSetName = value;
    }

    public String getValidValuesDSRValueField() throws Exception {
        return _ValidValuesDSRValueField;
    }

    public void setValidValuesDSRValueField(String value) throws Exception {
        _ValidValuesDSRValueField = value;
    }

    public String getValidValuesDSRLabelField() throws Exception {
        return _ValidValuesDSRLabelField;
    }

    public void setValidValuesDSRLabelField(String value) throws Exception {
        _ValidValuesDSRLabelField = value;
    }

    public String toString() {
        try
        {
            return _Name;
        }
        catch (RuntimeException __dummyCatchVar5)
        {
            throw __dummyCatchVar5;
        }
        catch (Exception __dummyCatchVar5)
        {
            throw new RuntimeException(__dummyCatchVar5);
        }
    
    }

}


