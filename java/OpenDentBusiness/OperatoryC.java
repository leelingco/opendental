//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;

public class OperatoryC   
{
    /**
    * 
    */
    private static List<Operatory> listt = new List<Operatory>();
    /**
    * A list of only those operatories that are visible.
    */
    private static List<Operatory> listShort = new List<Operatory>();
    public static List<Operatory> getListt() throws Exception {
        if (listt == null)
        {
            Operatories.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Operatory> value) throws Exception {
        listt = value;
    }

    public static List<Operatory> getListShort() throws Exception {
        if (listShort == null)
        {
            Operatories.refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(List<Operatory> value) throws Exception {
        listShort = value;
    }

}


