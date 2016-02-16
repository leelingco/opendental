//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ODDataTable;

/**
* 
*/
public class ODDataTableCollection  extends System.Collections.ObjectModel.Collection<ODDataTable> 
{
    /**
    * 
    */
    public ODDataTable get___idx(String name) throws Exception {
        for (Object __dummyForeachVar0 : this)
        {
            ODDataTable table = (ODDataTable)__dummyForeachVar0;
            if (StringSupport.equals(table.Name, name))
            {
                return table;
            }
             
        }
        ODDataTable tbl = new ODDataTable();
        tbl.Name = name;
        return tbl;
    }

}


