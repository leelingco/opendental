//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.MountDef;
import OpenDentBusiness.MountDefs;

public class MountDefC   
{
    /**
    * A list of all MountDefs.
    */
    private static List<MountDef> listt = new List<MountDef>();
    public static List<MountDef> getListt() throws Exception {
        if (listt == null)
        {
            MountDefs.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<MountDef> value) throws Exception {
        listt = value;
    }

}


