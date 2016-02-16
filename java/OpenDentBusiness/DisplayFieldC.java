//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DisplayField;
import OpenDentBusiness.DisplayFields;

public class DisplayFieldC   
{
    private static List<DisplayField> listt = new List<DisplayField>();
    /**
    * A list of all DisplayFields, sorted by ItemOrder, but not by Category.
    */
    public static List<DisplayField> getListt() throws Exception {
        if (listt == null)
        {
            DisplayFields.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<DisplayField> value) throws Exception {
        listt = value;
    }

}


