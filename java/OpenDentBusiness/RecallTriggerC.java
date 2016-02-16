//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RecallTrigger;
import OpenDentBusiness.RecallTriggers;

public class RecallTriggerC   
{
    /**
    * A list of all recall triggers.
    */
    private static List<RecallTrigger> listt = new List<RecallTrigger>();
    public static List<RecallTrigger> getListt() throws Exception {
        if (listt == null)
        {
            RecallTriggers.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<RecallTrigger> value) throws Exception {
        listt = value;
    }

}


