//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.RecallType;
import OpenDentBusiness.RecallTypes;

public class RecallTypeC   
{
    private static List<RecallType> listt = new List<RecallType>();
    /**
    * A list of all recall Types.
    */
    public static List<RecallType> getListt() throws Exception {
        if (listt == null)
        {
            RecallTypes.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<RecallType> value) throws Exception {
        listt = value;
    }

}


