//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCodeItem;
import OpenDentBusiness.AutoCodeItems;

public class AutoCodeItemC   
{
    /**
    * 
    */
    private static AutoCodeItem[] list = new AutoCodeItem[]();
    private static Hashtable hList = new Hashtable();
    public static AutoCodeItem[] getList() throws Exception {
        if (list == null)
        {
            AutoCodeItems.refreshCache();
        }
         
        return list;
    }

    public static void setList(AutoCodeItem[] value) throws Exception {
        list = value;
    }

    /**
    * key=CodeNum,value=AutoCodeNum
    */
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            AutoCodeItems.refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

}


