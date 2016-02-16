//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.EnumCobRule;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;

public class InsPlanT   
{
    /**
    * Creats an insurance plan with the default fee schedule of 53.
    */
    public static InsPlan createInsPlan(long carrierNum) throws Exception {
        return createInsPlan(carrierNum,EnumCobRule.Basic);
    }

    /**
    * Creates an insurance plan with the default fee schedule of 53.
    */
    public static InsPlan createInsPlan(long carrierNum, EnumCobRule cobRule) throws Exception {
        InsPlan plan = new InsPlan();
        plan.CarrierNum = carrierNum;
        plan.PlanType = "";
        plan.FeeSched = 53;
        plan.CobRule = cobRule;
        InsPlans.insert(plan);
        return plan;
    }

    /**
    * Creats an insurance plan with the default fee schedule of 53.
    */
    public static InsPlan createInsPlanPPO(long carrierNum, long feeSchedNum) throws Exception {
        return createInsPlanPPO(carrierNum,feeSchedNum,EnumCobRule.Basic);
    }

    /**
    * Creats an insurance plan with the default fee schedule of 53.
    */
    public static InsPlan createInsPlanPPO(long carrierNum, long feeSchedNum, EnumCobRule cobRule) throws Exception {
        InsPlan plan = new InsPlan();
        plan.CarrierNum = carrierNum;
        plan.PlanType = "p";
        plan.FeeSched = feeSchedNum;
        plan.CobRule = cobRule;
        InsPlans.insert(plan);
        return plan;
    }

}


