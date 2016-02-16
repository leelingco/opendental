//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Tooth;

/**
* A generic comparison that puts primary teeth after perm teeth.
*/
public class ToothComparer  extends IComparer<String> 
{
    public ToothComparer() throws Exception {
    }

    /**
    * A generic comparison that puts primary teeth after perm teeth.
    */
    public int compare(String toothA, String toothB) throws Exception {
        return Tooth.toOrdinal(toothA).CompareTo(Tooth.toOrdinal(toothB));
    }

}


