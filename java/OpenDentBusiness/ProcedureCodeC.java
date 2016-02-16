//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;

public class ProcedureCodeC   
{
    private static List<ProcedureCode> list = new List<ProcedureCode>();
    private static Hashtable hList = new Hashtable();
    public static List<ProcedureCode> getListt() throws Exception {
        if (list == null)
        {
            ProcedureCodes.refreshCache();
        }
         
        return list;
    }

    public static void setListt(List<ProcedureCode> value) throws Exception {
        list = value;
    }

    /**
    * key:ProcCode, value:ProcedureCode
    */
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            ProcedureCodes.refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

}


