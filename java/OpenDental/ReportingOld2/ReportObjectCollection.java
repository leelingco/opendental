//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import CS2JNet.System.StringSupport;
import OpenDental.ReportingOld2.ReportObject;

/**
* Contains the ReportObject objects for every report object in the report.
*/
public class ReportObjectCollection  extends CollectionBase 
{
    /**
    * Returns the ReportObject with the given index.
    */
    public ReportObject get___idx(int index) throws Exception {
        return ((ReportObject)List[index]);
    }

    public void set___idx(int index, ReportObject value) throws Exception {
        List[index] = value;
    }

    /**
    * Returns the ReportObject with the given name.
    */
    public ReportObject get___idx(String name) throws Exception {
        for (Object __dummyForeachVar0 : List)
        {
            ReportObject ro = (ReportObject)__dummyForeachVar0;
            if (StringSupport.equals(ro.getName(), name))
                return ro;
             
        }
        return null;
    }

    /**
    * 
    */
    public int add(ReportObject value) throws Exception {
        return (List.Add(value));
    }

    /**
    * 
    */
    public int indexOf(ReportObject value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public void insert(int index, ReportObject value) throws Exception {
        List.Insert(index, value);
    }

}


