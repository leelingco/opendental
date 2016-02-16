//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CovSpan;
import OpenDentBusiness.CovSpans;

public class CovSpanC   
{
    /**
    * 
    */
    private static CovSpan[] list = new CovSpan[]();
    public static CovSpan[] getList() throws Exception {
        if (list == null)
        {
            CovSpans.refreshCache();
        }
         
        return list;
    }

    public static void setList(CovSpan[] value) throws Exception {
        list = value;
    }

}


