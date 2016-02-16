//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import ODR.FormParameterInput;
import ODR.Parameter;
import ODR.ParameterCollection;
import ODR.ParamValueType;
import OpenDentBusiness.EnumType;

/**
* 
*/
public class Query   
{
    /**
    * 
    */
    private ParameterCollection Parameters;
    private boolean Cancel = new boolean();
    /**
    * Constructor
    */
    public Query() throws Exception {
        Parameters = new ParameterCollection();
    }

    /**
    * Parameters only get displayed the first time this function is run.  After that, those parameters are used repeatedly without reprompting the user again.
    */
    public String get(String query, String parameters) throws Exception {
        if (Cancel)
        {
            return "";
        }
         
        if (!StringSupport.equals(parameters, "") && Parameters.Count == 0)
        {
            //if this is the first time through
            //assemble parameters
            String[] paramList = parameters.Split(new char[]{ '~' });
            String[] paramFields = new String[]();
            Parameter param;
            for (int i = 0;i < paramList.Length;i++)
            {
                //string fieldValue;
                paramFields = paramList[i].Split(new char[]{ ';' });
                param = new Parameter();
                for (int f = 0;f < paramFields.Length;f++)
                {
                    if (paramFields[f].StartsWith("Name="))
                    {
                        param.setName(paramFields[f].Replace("Name=", ""));
                    }
                    else //param.OutputValue="1";//never passed in
                    if (paramFields[f].StartsWith("ValueType="))
                    {
                        System.Array<System.String>.INDEXER.APPLY __dummyScrutVar0 = paramFields[f].Replace("ValueType=", "");
                        if (__dummyScrutVar0.equals("Date"))
                        {
                            param.setValueType(ParamValueType.Date);
                        }
                        else if (__dummyScrutVar0.equals("String"))
                        {
                            param.setValueType(ParamValueType.String);
                        }
                        else if (__dummyScrutVar0.equals("Boolean"))
                        {
                            param.setValueType(ParamValueType.Boolean);
                        }
                        else if (__dummyScrutVar0.equals("Integer"))
                        {
                            param.setValueType(ParamValueType.Integer);
                        }
                        else if (__dummyScrutVar0.equals("Number"))
                        {
                            param.setValueType(ParamValueType.Number);
                        }
                        else if (__dummyScrutVar0.equals("Enum"))
                        {
                            param.setValueType(ParamValueType.Enum);
                        }
                        else if (__dummyScrutVar0.equals("QueryData"))
                        {
                            param.setValueType(ParamValueType.QueryData);
                        }
                        else
                        {
                            MessageBox.Show("Error in ValueType field: " + paramFields[f]);
                            return "";
                        }       
                    }
                    else //param.CurrentValues=new ArrayList();//never passed in
                    //param.defaultValues=new ArrayList();//not functional yet
                    if (paramFields[f].StartsWith("Prompt="))
                    {
                        param.setPrompt(paramFields[f].Replace("Prompt=", ""));
                    }
                    else if (paramFields[f].StartsWith("Snippet="))
                    {
                        param.setSnippet(paramFields[f].Replace("Snippet=", ""));
                    }
                    else if (paramFields[f].StartsWith("EnumerationType="))
                    {
                        System.Array<System.String>.INDEXER.APPLY __dummyScrutVar1 = paramFields[f].Replace("EnumerationType=", "");
                        if (__dummyScrutVar1.equals("PatientStatus"))
                        {
                            param.setEnumerationType(EnumType.PatientStatus);
                        }
                         
                    }
                    else
                    {
                        MessageBox.Show("Error. Unrecognized field: " + paramFields[f]);
                        return "";
                    }     
                }
                Parameters.add(param);
            }
            //pass parameters to a form for display
            RefSupport<ParameterCollection> refVar___0 = new RefSupport<ParameterCollection>(Parameters);
            FormParameterInput FormP = new FormParameterInput(refVar___0);
            Parameters = refVar___0.getValue();
            FormP.ShowDialog();
            if (FormP.DialogResult == DialogResult.Cancel)
            {
                Cancel = true;
                return "";
            }
             
        }
         
        return get(query);
    }

    //MessageBox.Show("parameter info: "+parameters);
    /**
    * This is used for subsequent queries in a report since only one query passes in parameters.
    */
    public String get(String query) throws Exception {
        if (Cancel)
        {
            return "";
        }
         
        String outputQuery = query;
        String replacement = "";
        //the replacement value to put into the outputQuery for each match
        //first replace all parameters with values:
        MatchCollection mc = new MatchCollection();
        Regex regex = new Regex("\\?\\w+");
        //? followed by one or more text characters
        mc = regex.Matches(outputQuery);
        for (int i = 0;i < mc.Count;i++)
        {
            //loop through each occurance of "?etc"
            replacement = Parameters[mc[i].Value.Substring(1)].OutputValue;
            regex = new Regex("\\" + mc[i].Value);
            outputQuery = regex.Replace(outputQuery, replacement);
        }
        return outputQuery;
    }

    //MessageBox.Show("output query: "+outputQuery);
    //string retVal="SELECT * FROM patient "+DbHelper.LimitWhere(10);
    public String getParameterValue(String name) throws Exception {
        if (Parameters.Count == 0)
        {
            return "";
        }
         
        for (Object __dummyForeachVar0 : Parameters)
        {
            Parameter p = (Parameter)__dummyForeachVar0;
            if (StringSupport.equals(p.getName(), name))
            {
                String retVal = "";
                for (int i = 0;i < p.getCurrentValues().Count;i++)
                {
                    if (i > 0)
                    {
                        retVal += ", ";
                    }
                     
                    switch(p.getValueType())
                    {
                        case Date: 
                            retVal += ((DateTime)p.getCurrentValues()[i]).ToShortDateString();
                            break;
                        case String: 
                            retVal += p.getCurrentValues()[i].ToString();
                            break;
                        case Boolean: 
                            if (((boolean)p.getCurrentValues()[i]))
                            {
                                retVal += "true";
                            }
                            else
                            {
                                retVal += "false";
                            } 
                            break;
                        case Integer: 
                            retVal += ((int)p.getCurrentValues()[i]).ToString();
                            break;
                        case Number: 
                            retVal += ((Double)p.getCurrentValues()[i]).ToString("n");
                            break;
                        case Enum: 
                            retVal += ((int)p.getCurrentValues()[i]).ToString();
                            break;
                        case QueryData: 
                            //needs some work to convert # to string
                            retVal += ((int)p.getCurrentValues()[i]).ToString();
                            break;
                    
                    }
                }
                return retVal;
            }
             
        }
        return "";
    }

}


//again, we should convert this to string
//switch
//for CurrentValues