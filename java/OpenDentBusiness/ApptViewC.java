//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViews;

public class ApptViewC   
{
    private static ApptView[] list = new ApptView[]();
    /**
    * A list of all apptviews, in order.
    */
    public static ApptView[] getList() throws Exception {
        if (list == null)
        {
            ApptViews.refreshCache();
        }
         
        return list;
    }

    public static void setList(ApptView[] value) throws Exception {
        list = value;
    }

}


