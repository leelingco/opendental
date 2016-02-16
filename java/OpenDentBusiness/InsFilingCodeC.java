//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.InsFilingCode;
import OpenDentBusiness.InsFilingCodes;

public class InsFilingCodeC   
{
    /**
    * A list of all insurance filing codes.
    */
    private static List<InsFilingCode> listt = new List<InsFilingCode>();
    public static List<InsFilingCode> getListt() throws Exception {
        if (listt == null)
        {
            InsFilingCodes.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<InsFilingCode> value) throws Exception {
        listt = value;
    }

}


