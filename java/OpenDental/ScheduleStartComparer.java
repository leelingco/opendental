//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:28 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import OpenDentBusiness.Schedule;

/**
* Sort a list of Schedules given a sort criteria
*/
public class ScheduleStartComparer  extends IComparer<Schedule> 
{
    public int compare(Schedule x, Schedule y) throws Exception {
        return x.StartTime.CompareTo(y.StartTime);
    }

}


