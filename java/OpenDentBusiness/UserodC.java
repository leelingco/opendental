//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Userod;
import OpenDentBusiness.Userods;

public class UserodC   
{
    /**
    * A list of all users.
    */
    private static List<Userod> listt = new List<Userod>();
    public static List<Userod> getListt() throws Exception {
        if (listt == null)
        {
            Userods.refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Userod> value) throws Exception {
        listt = value;
    }

    public static List<Userod> getShortList() throws Exception {
        if (listt == null)
        {
            Userods.refreshCache();
        }
         
        List<Userod> shortList = new List<Userod>();
        for (int i = 0;i < listt.Count;i++)
        {
            if (!listt[i].IsHidden)
            {
                shortList.Add(listt[i]);
            }
             
        }
        return shortList;
    }

}


