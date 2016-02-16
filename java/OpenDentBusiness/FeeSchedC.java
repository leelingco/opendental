//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeScheds;

public class FeeSchedC   
{
    /**
    * A list of all feescheds.
    */
    private static List<FeeSched> listLong = new List<FeeSched>();
    /**
    * A list of feescheds that are not hidden.
    */
    private static List<FeeSched> listShort = new List<FeeSched>();
    public static List<FeeSched> getListLong() throws Exception {
        if (listLong == null)
        {
            FeeScheds.refreshCache();
        }
         
        return listLong;
    }

    public static void setListLong(List<FeeSched> value) throws Exception {
        listLong = value;
    }

    public static List<FeeSched> getListShort() throws Exception {
        if (listShort == null)
        {
            FeeScheds.refreshCache();
        }
         
        return listShort;
    }

    public static void setListShort(List<FeeSched> value) throws Exception {
        listShort = value;
    }

}


