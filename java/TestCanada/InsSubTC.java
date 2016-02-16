//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;

public class InsSubTC   
{
    public static void setAssignBen(boolean assignBen, long subNum) throws Exception {
        InsSub sub = InsSubs.getOne(subNum);
        if (sub.AssignBen == assignBen)
        {
            return ;
        }
         
        //no change needed
        sub.AssignBen = assignBen;
        InsSubs.update(sub);
    }

}


