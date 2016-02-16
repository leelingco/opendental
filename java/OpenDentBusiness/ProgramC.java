//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Program;
import OpenDentBusiness.Programs;

public class ProgramC   
{
    /**
    * 
    */
    private static Hashtable hList = new Hashtable();
    /**
    * A list of all Program links.
    */
    private static List<Program> listt = new List<Program>();
    public static Hashtable getHList() throws Exception {
        if (hList == null)
        {
            Programs.refreshCache();
        }
         
        return hList;
    }

    public static void setHList(Hashtable value) throws Exception {
        hList = value;
    }

    public static List<Program> getListt() throws Exception {
        if (listt == null)
        {
            Programs.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Program> value) throws Exception {
        listt = value;
    }

    public static boolean hListIsNull() throws Exception {
        return hList == null;
    }

}


