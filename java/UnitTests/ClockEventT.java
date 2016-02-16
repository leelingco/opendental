//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.TimeClockStatus;

public class ClockEventT   
{
    public static long insertWorkPeriod(long emp, DateTime start, DateTime stop) throws Exception {
        ClockEvent ce = new ClockEvent();
        ce.ClockStatus = TimeClockStatus.Home;
        ce.EmployeeNum = emp;
        ce.TimeDisplayed1 = start;
        ce.TimeEntered1 = start;
        ce.TimeDisplayed2 = stop;
        ce.TimeEntered2 = stop;
        ce.ClockEventNum = ClockEvents.insert(ce);
        ClockEvents.update(ce);
        return ce.ClockEventNum;
    }

    //Updates TimeDisplayed1 because it defaults to now().
    public static long insertBreak(long emp, DateTime start, int minutes) throws Exception {
        ClockEvent ce = new ClockEvent();
        ce.ClockStatus = TimeClockStatus.Break;
        ce.EmployeeNum = emp;
        ce.TimeDisplayed1 = start;
        ce.TimeEntered1 = start;
        ce.TimeDisplayed2 = start.AddMinutes(minutes);
        ce.TimeEntered2 = start.AddMinutes(minutes);
        ce.ClockEventNum = ClockEvents.insert(ce);
        ClockEvents.update(ce);
        return ce.ClockEventNum;
    }

}


//Updates TimeDisplayed1 because it defaults to now().