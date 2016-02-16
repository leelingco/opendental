//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.InsFilingCodeSubtype;
import OpenDentBusiness.InsFilingCodeSubtypes;

public class InsFilingCodeSubtypeC   
{
    /**
    * A list of all insurance filing code subtypes.
    */
    private static List<InsFilingCodeSubtype> listt = new List<InsFilingCodeSubtype>();
    public static List<InsFilingCodeSubtype> getListt() throws Exception {
        if (listt == null)
        {
            InsFilingCodeSubtypes.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<InsFilingCodeSubtype> value) throws Exception {
        listt = value;
    }

}


