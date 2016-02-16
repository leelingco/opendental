//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.TimeClockStatus;

/**
* One clock-in / clock-out pair.  Of, if the pair is a break, then it's an out/in pair.  With normal clock in/out pairs, we want to know how long the employee was working.  It's the opposite with breaks.  We want to know how long they were not working, so the pair is backwards.  This means that a normal clock in is left incomplete when the clock out for break is created.  And once both are finished, the regular in/out will surround the break.  Breaks cannot be viewed easily on the same grid as regular clock events for this reason.  And since breaks do not affect pay, they should not clutter the normal grid.
*/
public class ClockEvent  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ClockEventNum = new long();
    /**
    * FK to employee.EmployeeNum
    */
    public long EmployeeNum = new long();
    /**
    * The actual time that this entry was entered.  Cannot be 01-01-0001.
    */
    public DateTime TimeEntered1 = new DateTime();
    /**
    * The time to display and to use in all calculations.  Cannot be 01-01-0001.
    */
    public DateTime TimeDisplayed1 = new DateTime();
    /**
    * Enum:TimeClockStatus  Home, Lunch, or Break.  The status really only applies to the clock out.  Except the Break status applies to both out and in.
    */
    public TimeClockStatus ClockStatus = TimeClockStatus.Home;
    /**
    * .
    */
    public String Note = new String();
    /**
    * The user can never edit this, but the program has to be able to edit this when user clocks out.  Can be 01-01-0001 if not clocked out yet.
    */
    public DateTime TimeEntered2 = new DateTime();
    /**
    * User can edit. Can be 01-01-0001 if not clocked out yet.
    */
    public DateTime TimeDisplayed2 = new DateTime();
    /**
    * This is a manual override for OTimeAuto.  Typically -1 hour (-01:00:00) to indicate no override.  When used as override, allowed values are zero or positive.  This is an alternative to using a TimeAdjust row.
    */
    public TimeSpan OTimeHours = new TimeSpan();
    /**
    * Automatically calculated OT.  Will be zero if none.
    */
    public TimeSpan OTimeAuto = new TimeSpan();
    /**
    * This is a manual override of AdjustAuto.  Ignored unless AdjustIsOverridden set to true.  When used as override, it's typically negative, although zero and positive are also allowed.
    */
    public TimeSpan Adjust = new TimeSpan();
    /**
    * Automatically calculated Adjust.  Will be zero if none.
    */
    public TimeSpan AdjustAuto = new TimeSpan();
    /**
    * True if AdjustAuto is overridden by Adjust.
    */
    public boolean AdjustIsOverridden = new boolean();
    /**
    * This is a manual override for Rate2Auto.  Typically -1 hour (-01:00:00) to indicate no override.  When used as override, allowed values are zero or positive.  This is the portion of the hours worked which are at Rate2, so it's not in addition to the hours worked.  Also used to calculate the Rate2 OT.
    */
    public TimeSpan Rate2Hours = new TimeSpan();
    /**
    * Automatically calculated rate2 pay.  Will be zero if none.
    */
    public TimeSpan Rate2Auto = new TimeSpan();
    /**
    * Used only for serialization purposes
    */
    public long getOTimeHoursXml() throws Exception {
        return OTimeHours.Ticks;
    }

    public void setOTimeHoursXml(long value) throws Exception {
        OTimeHours = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getOTimeAutoXml() throws Exception {
        return OTimeAuto.Ticks;
    }

    public void setOTimeAutoXml(long value) throws Exception {
        OTimeAuto = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getAdjustXml() throws Exception {
        return Adjust.Ticks;
    }

    public void setAdjustXml(long value) throws Exception {
        Adjust = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getAdjustAutoXml() throws Exception {
        return AdjustAuto.Ticks;
    }

    public void setAdjustAutoXml(long value) throws Exception {
        AdjustAuto = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getDiffHoursXml() throws Exception {
        return Rate2Hours.Ticks;
    }

    public void setDiffHoursXml(long value) throws Exception {
        Rate2Hours = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getDiffAutoXml() throws Exception {
        return Rate2Auto.Ticks;
    }

    public void setDiffAutoXml(long value) throws Exception {
        Rate2Auto = TimeSpan.FromTicks(value);
    }

    public ClockEvent() throws Exception {
        OTimeHours = TimeSpan.FromHours(-1);
        Rate2Hours = TimeSpan.FromHours(-1);
    }

    /**
    * 
    */
    public ClockEvent copy() throws Exception {
        return (ClockEvent)MemberwiseClone();
    }

}


