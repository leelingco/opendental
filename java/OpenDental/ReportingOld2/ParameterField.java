//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import OpenDental.POut;
import OpenDental.ReportingOld2.FieldValueType;
import OpenDentBusiness.DefCat;
import OpenDentBusiness.EnumType;
import OpenDentBusiness.ReportFKType;

/**
* Holds information about a parameter used in the report.A parameter is a string that can be used in a query that will be replaced by user-provided data before the query is sent.  For instance, "?date1" might be replaced with "(ProcDate = '2004-02-17' OR ProcDate = '2004-02-18')".  The output value can be multiple items connected with OR's as in the example, or it can be a single value.  The Snippet represents one of the multiple values.  In this example, it would be "ProcDate = '?'".  The ? in the Snippet will be replaced by the values provided by the user.
*/
public class ParameterField   
{
    private String name = new String();
    private String outputValue = new String();
    private FieldValueType valueType = FieldValueType.Date;
    private ArrayList currentValues = new ArrayList();
    private ArrayList defaultValues = new ArrayList();
    private String promptingText = new String();
    private String snippet = new String();
    private EnumType enumerationType = EnumType.YN;
    private DefCat defCategory = DefCat.AccountColors;
    private ReportFKType fKeyType = ReportFKType.None;
    /**
    * This is the name as it will show in the query, but without the preceding question mark.
    */
    public String getName() throws Exception {
        return name;
    }

    public void setName(String value) throws Exception {
        name = value;
    }

    /**
    * The value, in text form, as it will be substituted into the main query and sent to the database.
    */
    public String getOutputValue() throws Exception {
        return outputValue;
    }

    public void setOutputValue(String value) throws Exception {
        outputValue = value;
    }

    /**
    * The type of value that the parameter can accept.
    */
    public FieldValueType getValueType() throws Exception {
        return valueType;
    }

    public void setValueType(FieldValueType value) throws Exception {
        valueType = value;
    }

    /**
    * The values of the parameter, typically just one. Each value can be a string, date, number, currency, Boolean, etc.  If the length of the ArrayList is 0, then the value is blank and will not be used in the query.
    */
    public ArrayList getCurrentValues() throws Exception {
        return currentValues;
    }

    public void setCurrentValues(ArrayList value) throws Exception {
        currentValues = value;
    }

    /**
    * These values are stored between sessions in the database and will show in the dialog prefilled when it asks the user for values.  The length of the ArrayList can be 0 to specify that the initial value is blank.
    */
    public ArrayList getDefaultValues() throws Exception {
        return defaultValues;
    }

    public void setDefaultValues(ArrayList value) throws Exception {
        defaultValues = value;
    }

    /**
    * The text that prompts the user what to enter for this parameter.
    */
    public String getPromptingText() throws Exception {
        return promptingText;
    }

    public void setPromptingText(String value) throws Exception {
        promptingText = value;
    }

    /**
    * The snippet of SQL that will be repeated once for each value supplied by the user, connected with OR's, and surrounded by parentheses.
    */
    public String getSnippet() throws Exception {
        return snippet;
    }

    public void setSnippet(String value) throws Exception {
        snippet = value;
    }

    /**
    * If the ValueKind is EnumField, then this specifies which type of enum. It is the string name of the type.
    */
    public EnumType getEnumerationType() throws Exception {
        return enumerationType;
    }

    public void setEnumerationType(EnumType value) throws Exception {
        enumerationType = value;
    }

    /**
    * If ValueKind is DefParameter, then this specifies which DefCat.
    */
    public DefCat getDefCategory() throws Exception {
        return defCategory;
    }

    public void setDefCategory(DefCat value) throws Exception {
        defCategory = value;
    }

    /**
    * If ValueKind is ForeignKey, then this specifies which one.
    */
    public ReportFKType getFKeyType() throws Exception {
        return fKeyType;
    }

    public void setFKeyType(ReportFKType value) throws Exception {
        fKeyType = value;
    }

    /**
    * Default constructor. Used when retrieving data from db.
    */
    public ParameterField() throws Exception {
    }

    /**
    * This is how parameters are generally added.  The currentValues and outputValue will be determined during the Report.SubmitQuery call.
    */
    public ParameterField(String thisName, FieldValueType thisValueType, Object thisDefaultValue, String thisPromptingText, String thisSnippet) throws Exception {
        name = thisName;
        valueType = thisValueType;
        defaultValues = new ArrayList();
        defaultValues.Add(thisDefaultValue);
        promptingText = thisPromptingText;
        snippet = thisSnippet;
        enumerationType = EnumType.ApptStatus;
        //arbitrary
        defCategory = DefCat.AccountColors;
        //arbitrary
        fKeyType = ReportFKType.None;
    }

    /**
    * Overload for ValueKind Enum.
    */
    public ParameterField(String thisName, FieldValueType thisValueType, ArrayList theseDefaultValues, String thisPromptingText, String thisSnippet, EnumType thisEnumerationType) throws Exception {
        name = thisName;
        valueType = thisValueType;
        defaultValues = theseDefaultValues;
        promptingText = thisPromptingText;
        snippet = thisSnippet;
        enumerationType = thisEnumerationType;
        defCategory = DefCat.AccountColors;
        //arbitrary
        fKeyType = ReportFKType.None;
    }

    /**
    * Overload for ValueKind DefCat.
    */
    public ParameterField(String thisName, FieldValueType thisValueType, ArrayList theseDefaultValues, String thisPromptingText, String thisSnippet, DefCat thisDefCategory) throws Exception {
        name = thisName;
        valueType = thisValueType;
        defaultValues = theseDefaultValues;
        promptingText = thisPromptingText;
        snippet = thisSnippet;
        enumerationType = EnumType.ApptStatus;
        //arbitrary
        defCategory = thisDefCategory;
        fKeyType = ReportFKType.None;
    }

    /**
    * Overload for ValueKind ForeignKey.
    */
    public ParameterField(String thisName, FieldValueType thisValueType, ArrayList theseDefaultValues, String thisPromptingText, String thisSnippet, ReportFKType thisReportFKType) throws Exception {
        name = thisName;
        valueType = thisValueType;
        defaultValues = theseDefaultValues;
        promptingText = thisPromptingText;
        snippet = thisSnippet;
        enumerationType = EnumType.ApptStatus;
        //arbitrary
        defCategory = DefCat.AccountColors;
        //arbitrary
        fKeyType = thisReportFKType;
    }

    /*
    		///<summary></summary>
    		public ParameterDef(string name, ParameterValueKind valueKind, ArrayList myValues){
    			Name=name;
    			ParamValueKind=valueKind;
    			if(!ApplyParamValue(myValues))
    				MessageBox.Show("Invalid values.");
    		}*/
    /**
    * Applies a value to the specified parameter field of a report.  The currentValues is usually just a single value.  The only time there will be multiple values is for a def or an enum.  For example, if a user selects multiple items from a dropdown box for this parameter, then each item is connected by an OR.  The entire output value is surrounded by parentheses.
    */
    public void applyParamValues() throws Exception {
        outputValue = "(";
        if (currentValues.Count == 0)
        {
            //if there are no values
            outputValue += "1";
        }
         
        for (int i = 0;i < currentValues.Count;i++)
        {
            //display a 1 (true) to effectively exclude this snippet
            if (i > 0)
            {
                outputValue += " OR";
            }
             
            if (valueType == FieldValueType.Boolean)
            {
                if ((boolean)currentValues[i])
                {
                    outputValue += snippet;
                }
                else
                {
                    //snippet will show. There is no ? substitution
                    outputValue += "1";
                } 
            }
            else //instead of the snippet, a 1 will show
            if (valueType == FieldValueType.Date)
            {
                outputValue += " " + Regex.Replace(snippet, "\\?", POut.Date((DateTime)currentValues[i], false));
            }
            else if (valueType == FieldValueType.Def)
            {
                outputValue += " " + Regex.Replace(snippet, "\\?", POut.Long((int)currentValues[i]));
            }
            else if (valueType == FieldValueType.Enum)
            {
                outputValue += " " + Regex.Replace(snippet, "\\?", POut.Long((int)currentValues[i]));
            }
            else if (valueType == FieldValueType.Integer)
            {
                outputValue += " " + Regex.Replace(snippet, "\\?", POut.Long((int)currentValues[i]));
            }
            else if (valueType == FieldValueType.String)
            {
                outputValue += " " + Regex.Replace(snippet, "\\?", POut.String((String)currentValues[i]));
            }
            else if (valueType == FieldValueType.Number)
            {
                outputValue += " " + Regex.Replace(snippet, "\\?", POut.Double((double)currentValues[i]));
            }
                   
        }
        //for i
        outputValue += ")";
    }

}


