//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;

public class InsSubT   
{
    /**
    * 
    */
    public static InsSub createInsSub(long subscriberNum, long planNum) throws Exception {
        InsSub sub = new InsSub();
        sub.Subscriber = subscriberNum;
        sub.PlanNum = planNum;
        sub.SubscriberID = "1234";
        InsSubs.insert(sub);
        return sub;
    }

}


