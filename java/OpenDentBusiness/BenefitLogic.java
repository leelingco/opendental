//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:59 PM
//

package OpenDentBusiness;


public class BenefitLogic   
{
    /**
    * This function is unit tested for accuracy because it has been a repeated source of bugs in the past.
    */
    public static DateTime computeRenewDate(DateTime asofDate, int monthRenew) throws Exception {
        if (asofDate.Year < 1880)
        {
            return DateTime.Today;
        }
         
        //this clause is not unit tested.
        if (monthRenew == 0)
        {
            return new DateTime(asofDate.Year, 1, 1);
        }
         
        //now, for benefit year not beginning on Jan 1.
        //if(insStartDate.Year<1880) {//if no start date was entered.
        //	return new DateTime(asofDate.Year,1,1);
        //}
        if (monthRenew == asofDate.Month)
        {
            return new DateTime(asofDate.Year, monthRenew, 1);
        }
         
        //any day this month
        //if(monthRenew==asofDate.Month && 1 < asofDate.Day) {//current month, before today
        //	return new DateTime(asofDate.Year,monthRenew,1);
        //}
        if (monthRenew < asofDate.Month)
        {
            return new DateTime(asofDate.Year, monthRenew, 1);
        }
         
        return new DateTime(asofDate.Year - 1, monthRenew, 1);
    }

}


//previous month
//late last year