//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:36 PM
//

package TestCanada;

import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitials;
import OpenDentBusiness.ToothInitialType;

public class ToothInitialTC   
{
    /**
    * This helps to visually confirm which teeth have been set to extracted.  It also allows setting missing teeth for NIHB without entering any extractions.  The tooth number passed in should be in international format.
    */
    public static void setMissing(String toothNumInternat, long patNum) throws Exception {
        ToothInitial ti = new ToothInitial();
        ti.PatNum = patNum;
        ti.InitialType = ToothInitialType.Missing;
        ti.ToothNum = OpenDentBusiness.Tooth.fromInternat(toothNumInternat);
        ToothInitials.insert(ti);
    }

}


