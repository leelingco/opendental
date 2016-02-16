//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SheetDef;
import OpenDentBusiness.SheetDefs;

public class SheetDefC   
{
    /**
    * A list of all sheetdefs.
    */
    private static List<SheetDef> listt = new List<SheetDef>();
    public static List<SheetDef> getListt() throws Exception {
        if (listt == null)
        {
            SheetDefs.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<SheetDef> value) throws Exception {
        listt = value;
    }

}


