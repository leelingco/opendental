//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import CS2JNet.System.StringSupport;
import OpenDental.ReportingOld2.ParameterField;

/**
* Strongly typed collection of type ParameterDef.
*/
public class ParameterFieldCollection  extends CollectionBase 
{
    /**
    * Returns the ParameterField with the given index.
    */
    public ParameterField get___idx(int index) throws Exception {
        return ((ParameterField)List[index]);
    }

    public void set___idx(int index, ParameterField value) throws Exception {
        List[index] = value;
    }

    /**
    * Returns the ParameterDefinition with the given name.
    */
    public ParameterField get___idx(String name) throws Exception {
        for (Object __dummyForeachVar0 : List)
        {
            ParameterField pf = (ParameterField)__dummyForeachVar0;
            if (StringSupport.equals(pf.getName(), name))
                return pf;
             
        }
        return null;
    }

    /**
    * 
    */
    public int add(ParameterField value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(ParameterField value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, ParameterField value) throws Exception {
        List.Insert(index, value);
    }

}


//public int GetIndexOfType(SectionType sectType){
//	return -1;
//}