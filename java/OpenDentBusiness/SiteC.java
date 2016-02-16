//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Site;
import OpenDentBusiness.Sites;

public class SiteC   
{
    /**
    * A list of all sites.
    */
    private static Site[] list = new Site[]();
    public static Site[] getList() throws Exception {
        if (list == null)
        {
            Sites.refreshCache();
        }
         
        return list;
    }

    public static void setList(Site[] value) throws Exception {
        list = value;
    }

}


