//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SheetFieldDef;
import OpenDentBusiness.SheetFieldDefs;

public class SheetFieldDefC   
{
    /**
    * A list of all sheetfielddefs.
    */
    private static List<SheetFieldDef> listt = new List<SheetFieldDef>();
    public static List<SheetFieldDef> getListt() throws Exception {
        if (listt == null)
        {
            SheetFieldDefs.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<SheetFieldDef> value) throws Exception {
        listt = value;
    }

}


