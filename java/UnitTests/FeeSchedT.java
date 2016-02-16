//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;

public class FeeSchedT   
{
    /**
    * Returns feeSchedNum
    */
    public static long createFeeSched(FeeScheduleType feeSchedType, String suffix) throws Exception {
        FeeSched feeSched = new FeeSched();
        feeSched.FeeSchedType = feeSchedType;
        feeSched.Description = "FeeSched" + suffix;
        FeeScheds.insert(feeSched);
        FeeScheds.refreshCache();
        return feeSched.FeeSchedNum;
    }

}


