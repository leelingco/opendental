//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoCode;
import OpenDentBusiness.AutoCodes;

public class AutoCodeC   
{
    /**
    * 
    */
    private static AutoCode[] list = new AutoCode[]();
    /**
    * 
    */
    private static AutoCode[] listShort = new AutoCode[]();
    private static Hashtable hList = new Hashtable();
    public static AutoCode[] getList() throws Exception {
        if (list == null)
        {
            AutoCodes.refreshCache();
        }
         
        return list;
    }

    public static void setList(AutoCode[] value) throws Exception {
        list = value;
    }

    public static AutoCode[] getListShort() throws Exception {
        if (listShort == null)
        {
            AutoCodes.refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(AutoCode[] value) throws Exception {
        listShort = value;
    }

    /**
    * key=AutoCodeNum, value=AutoCode
    */
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            AutoCodes.refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

}


