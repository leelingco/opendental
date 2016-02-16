//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import ODR.ParamValueType;
import OpenDentBusiness.EnumType;
import OpenDentBusiness.POut;

/**
* Holds information about a parameter used in the report.A parameter is a string that can be used in the WHERE clause of a query that will be replaced by user-provided data before the query is sent.  For instance, "?date1" might be replaced with "(ProcDate = '2004-02-17' OR ProcDate = '2004-02-18')".  The output value can be multiple items connected with OR's as in the example, or it can be a single value.  The Snippet represents one of the multiple values.  In this example, it would be "ProcDate = '?'".  The ? in the Snippet will be replaced by the values provided by the user.
*/
public class Parameter   
{
    private String name = new String();
    private String outputValue = new String();
    private ParamValueType valueType = ParamValueType.Date;
    private ArrayList currentValues = new ArrayList();
    //private ArrayList defaultValues;
    private String prompt = new String();
    private String snippet = new String();
    private EnumType enumerationType = EnumType.YN;
    private String queryText = new String();
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
    public ParamValueType getValueType() throws Exception {
        return valueType;
    }

    public void setValueType(ParamValueType value) throws Exception {
        valueType = value;
    }

    /**
    * The values of the parameter, typically just one. Each value can be a string, date, number, currency, Boolean, etc.  If the length of the ArrayList is 0, then the value is blank and will not be used in the query.  CurrentValues can be set ahead of time in the report, so in this usage, they might be thought of as default values.
    */
    public ArrayList getCurrentValues() throws Exception {
        return currentValues;
    }

    public void setCurrentValues(ArrayList value) throws Exception {
        currentValues = value;
    }

    /*
    		///<summary>These values will show in the dialog preselected when it asks the user for values.  The length of the ArrayList can be 0 to specify that the initial value is blank.</summary>
    		public ArrayList DefaultValues{
    			get{
    				return defaultValues;
    			}
    			set{
    				defaultValues=value;
    			}
    		}*/
    /**
    * The text that prompts the user what to enter for this parameter.
    */
    public String getPrompt() throws Exception {
        return prompt;
    }

    public void setPrompt(String value) throws Exception {
        prompt = value;
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
    * If the ValueType is Enum, then this specifies which type of enum. It is the string name of the type.
    */
    public EnumType getEnumerationType() throws Exception {
        return enumerationType;
    }

    public void setEnumerationType(EnumType value) throws Exception {
        enumerationType = value;
    }

    /**
    * If ValueType is QueryData, then this contains the query to use to get the parameter list.
    */
    public String getQueryText() throws Exception {
        return queryText;
    }

    public void setQueryText(String value) throws Exception {
        queryText = value;
    }

    /**
    * Default constructor.
    */
    public Parameter() throws Exception {
        name = "";
        outputValue = "1";
        valueType = ParamValueType.String;
        currentValues = new ArrayList();
        //defaultValues=new ArrayList();
        prompt = "";
        snippet = "";
        enumerationType = EnumType.ApptStatus;
        //arbitrary
        queryText = "";
    }

    /**
    * CurrentValues must be set first.  Then, this applies the values to the parameter to create the outputValue.  The currentValues is usually just a single value.  The only time there will be multiple values is for an Enum or QueryText.  For example, if a user selects multiple items from a listbox for this parameter, then each item is connected by an OR.  The entire OutputValue is surrounded by parentheses.
    */
    public void fillOutputValue() throws Exception {
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
                outputValue += " OR ";
            }
             
            if (valueType == ParamValueType.Boolean)
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
            if (valueType == ParamValueType.Date)
            {
                outputValue += Regex.Replace(snippet, "\\?", POut.date((DateTime)currentValues[i],false));
            }
            else if (valueType == ParamValueType.Enum)
            {
                outputValue += Regex.Replace(snippet, "\\?", POut.Long((int)currentValues[i]));
            }
            else if (valueType == ParamValueType.Integer)
            {
                outputValue += Regex.Replace(snippet, "\\?", POut.Long((int)currentValues[i]));
            }
            else if (valueType == ParamValueType.String)
            {
                outputValue += Regex.Replace(snippet, "\\?", POut.string((String)currentValues[i]));
            }
            else if (valueType == ParamValueType.Number)
            {
                outputValue += Regex.Replace(snippet, "\\?", POut.double((double)currentValues[i]));
            }
            else if (valueType == ParamValueType.QueryData)
            {
                outputValue += Regex.Replace(snippet, "\\?", POut.string((String)currentValues[i]));
            }
                   
        }
        outputValue += ")";
    }

}


