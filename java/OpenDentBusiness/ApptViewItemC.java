//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptViewItem;
import OpenDentBusiness.ApptViewItems;

public class ApptViewItemC   
{
    private static ApptViewItem[] list = new ApptViewItem[]();
    /**
    * A list of all ApptViewItems.
    */
    public static ApptViewItem[] getList() throws Exception {
        if (list == null)
        {
            ApptViewItems.refreshCache();
        }
         
        return list;
    }

    public static void setList(ApptViewItem[] value) throws Exception {
        list = value;
    }

}


