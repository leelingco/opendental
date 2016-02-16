//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package ODR;

import CS2JNet.System.StringSupport;
import ODR.Parameter;

/**
* Strongly typed collection of type Parameter.
*/
public class ParameterCollection  extends CollectionBase 
{
    /**
    * Returns the Parameter with the given index.
    */
    public Parameter get___idx(int index) throws Exception {
        return ((Parameter)List[index]);
    }

    public void set___idx(int index, Parameter value) throws Exception {
        List[index] = value;
    }

    /**
    * Returns the Parameter with the given name.
    */
    public Parameter get___idx(String name) throws Exception {
        for (Object __dummyForeachVar0 : List)
        {
            Parameter p = (Parameter)__dummyForeachVar0;
            if (StringSupport.equals(p.getName(), name))
                return p;
             
        }
        return null;
    }

    /**
    * 
    */
    public int add(Parameter value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(Parameter value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, Parameter value) throws Exception {
        List.Insert(index, value);
    }

}


//public int GetIndexOfType(SectionType sectType){
//	return -1;
//}