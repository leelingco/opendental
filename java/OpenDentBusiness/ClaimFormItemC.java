//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimFormItem;
import OpenDentBusiness.ClaimFormItems;

public class ClaimFormItemC   
{
    /**
    * 
    */
    private static ClaimFormItem[] listt = new ClaimFormItem[]();
    public static ClaimFormItem[] getListt() throws Exception {
        if (listt == null)
        {
            ClaimFormItems.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(ClaimFormItem[] value) throws Exception {
        listt = value;
    }

}


