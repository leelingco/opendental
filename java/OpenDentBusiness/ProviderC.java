//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

public class ProviderC   
{
    private static List<Provider> listLong = new List<Provider>();
    private static List<Provider> list = new List<Provider>();
    /**
    * Rarely used. Includes all providers, even if hidden.
    */
    public static List<Provider> getListLong() throws Exception {
        if (listLong == null)
        {
            Providers.refreshCache();
        }
         
        return listLong;
    }

    public static void setListLong(List<Provider> value) throws Exception {
        listLong = value;
    }

    /**
    * This is the list used most often. It does not include hidden providers.
    */
    public static List<Provider> getListShort() throws Exception {
        if (list == null)
        {
            Providers.refreshCache();
        }
         
        return list;
    }

    public static void setListShort(List<Provider> value) throws Exception {
        list = value;
    }

}


