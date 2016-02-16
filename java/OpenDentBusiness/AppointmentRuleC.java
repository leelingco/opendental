//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AppointmentRule;
import OpenDentBusiness.AppointmentRules;

public class AppointmentRuleC   
{
    /**
    * 
    */
    private static AppointmentRule[] list = new AppointmentRule[]();
    public static AppointmentRule[] getList() throws Exception {
        if (list == null)
        {
            AppointmentRules.refreshCache();
        }
         
        return list;
    }

    public static void setList(AppointmentRule[] value) throws Exception {
        list = value;
    }

}


