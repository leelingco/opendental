//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:39 PM
//

package UnitTests;

import OpenDentBusiness.PayPeriod;
import OpenDentBusiness.PayPeriods;

public class PayPeriodT   
{
    /**
    * Returns existing payperiod if it exists, otherwise inserts and returns a new payperiod. Throws exception if payperiod overlaps existing payperiod.
    */
    public static PayPeriod createTwoWeekPayPeriodIfNotExists(DateTime start) throws Exception {
        PayPeriod ppNew = new PayPeriod();
        ppNew.DateStart = start;
        ppNew.DateStop = start.AddDays(13);
        ppNew.DatePaycheck = start.AddDays(16);
        //check for identical or overlapping pay periods
        PayPeriods.refreshCache();
        for (Object __dummyForeachVar0 : PayPeriods.getList())
        {
            PayPeriod ppInDb = (PayPeriod)__dummyForeachVar0;
            if (ppInDb.DateStart == ppNew.DateStart && ppInDb.DateStop == ppNew.DateStop && ppInDb.DatePaycheck == ppNew.DatePaycheck)
            {
                return ppInDb;
            }
             
            //identical pay period already exists.
            //if(pp.DateStart == payP.DateStart && pp.DateStop == payP.DateStop	&& pp.DatePaycheck != payP.DatePaycheck) {
            //  //identical pay period already exists, just with a different pay check date.
            //  //This is a seperate check because it may be important in the future.
            //  continue;
            //}
            if (ppInDb.DateStop > ppNew.DateStart && ppInDb.DateStart < ppNew.DateStop)
            {
                throw new Exception("Error inserting pay period. New Pay period overlaps existing pay period.\r\n");
            }
             
        }
        //pay periods overlap
        PayPeriods.insert(ppNew);
        return ppNew;
    }

}


