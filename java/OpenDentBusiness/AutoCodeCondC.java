//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCodeCond;
import OpenDentBusiness.AutoCodeConds;

public class AutoCodeCondC   
{
    /**
    * 
    */
    private static AutoCodeCond[] list = new AutoCodeCond[]();
    public static AutoCodeCond[] getList() throws Exception {
        if (list == null)
        {
            AutoCodeConds.refreshCache();
        }
         
        return list;
    }

    public static void setList(AutoCodeCond[] value) throws Exception {
        list = value;
    }

}


