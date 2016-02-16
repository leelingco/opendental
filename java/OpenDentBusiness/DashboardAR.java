//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DashboardAR;
import OpenDentBusiness.TableBase;

/**
* A table just used by the dashboard to store historical AR because it never changes and it takes too long (1 second for each of the 12 dates) to compute on the fly.  One entry per month going back at least 12 months.  This table gets automatically filled the first time that the dashboard is used.  The most recent month also gets added by using the dashboard.
*/
public class DashboardAR  extends TableBase 
{
    /**
    * Primary key.
    */
    public long DashboardARNum = new long();
    /**
    * This date will always be the last day of a month.
    */
    public DateTime DateCalc = new DateTime();
    /**
    * Bal_0_30+Bal_31_60+Bal_61_90+BalOver90 for all patients.  This should also exactly equal BalTotal for all patients with positive amounts.  Negative BalTotals are credits, not A/R.
    */
    public double BalTotal = new double();
    /**
    * Sum of all InsEst for all patients for the month.
    */
    public double InsEst = new double();
    /**
    * 
    */
    public DashboardAR copy() throws Exception {
        return (DashboardAR)this.MemberwiseClone();
    }

}


