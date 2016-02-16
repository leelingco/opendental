//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CovCat;
import OpenDentBusiness.CovCats;

public class CovCatC   
{
    private static List<CovCat> listt = new List<CovCat>();
    private static List<CovCat> listShort = new List<CovCat>();
    /**
    * All CovCats
    */
    public static List<CovCat> getListt() throws Exception {
        if (listt == null)
        {
            CovCats.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<CovCat> value) throws Exception {
        listt = value;
    }

    /**
    * Only CovCats that are not hidden.
    */
    public static List<CovCat> getListShort() throws Exception {
        if (listShort == null)
        {
            CovCats.refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(List<CovCat> value) throws Exception {
        listShort = value;
    }

    /**
    * 
    */
    public static int getOrderLong(long covCatNum) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            if (covCatNum == getListt()[i].CovCatNum)
            {
                return (byte)i;
            }
             
        }
        return -1;
    }

}


