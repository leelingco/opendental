//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.TimeCardRule;
import OpenDentBusiness.TimeCardRules;

public class TimeCardRuleT   
{
    public static void createAMTimeRule(long emp, TimeSpan beforeTime) throws Exception {
        TimeCardRule tcr = new TimeCardRule();
        tcr.EmployeeNum = emp;
        tcr.BeforeTimeOfDay = beforeTime;
        TimeCardRules.insert(tcr);
        return ;
    }

    public static void createPMTimeRule(long emp, TimeSpan afterTime) throws Exception {
        TimeCardRule tcr = new TimeCardRule();
        tcr.EmployeeNum = emp;
        tcr.AfterTimeOfDay = afterTime;
        TimeCardRules.insert(tcr);
        return ;
    }

    public static void createHoursTimeRule(long emp, TimeSpan hoursPerDay) throws Exception {
        TimeCardRule tcr = new TimeCardRule();
        tcr.EmployeeNum = emp;
        tcr.OverHoursPerDay = hoursPerDay;
        TimeCardRules.insert(tcr);
        return ;
    }

}


