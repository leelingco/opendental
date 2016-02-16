//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PayPeriod;
import OpenDentBusiness.TableBase;

/**
* Used to view employee timecards.  Timecard entries are not linked to a pay period.  Instead, payperiods are setup, and the user can only view specific pay periods.  So it feels like they are linked, but it's date based.
*/
public class PayPeriod  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PayPeriodNum = new long();
    /**
    * The first day of the payperiod
    */
    public DateTime DateStart = new DateTime();
    /**
    * The last day of the payperiod.
    */
    public DateTime DateStop = new DateTime();
    /**
    * The date that paychecks will be dated.  A few days after the dateStop.  Optional.
    */
    public DateTime DatePaycheck = new DateTime();
    /**
    * 
    */
    public PayPeriod copy() throws Exception {
        return (PayPeriod)this.MemberwiseClone();
    }

}


