//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;

public class PatPlanT   
{
    public static PatPlan createPatPlan(byte ordinal, long patNum, long subNum) throws Exception {
        PatPlan patPlan = new PatPlan();
        patPlan.Ordinal = ordinal;
        patPlan.PatNum = patNum;
        patPlan.InsSubNum = subNum;
        PatPlans.insert(patPlan);
        return patPlan;
    }

}


