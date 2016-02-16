//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.TimeAdjust;

/**
* Used on employee timecards to make adjustments.  Used to make the end-of-the week OT entries.  Can be used instead of a clock event by admin so that a clock event doesn't have to be created.
*/
public class TimeAdjust  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TimeAdjustNum = new long();
    /**
    * FK to employee.EmployeeNum
    */
    public long EmployeeNum = new long();
    /**
    * The date and time that this entry will show on timecard.
    */
    public DateTime TimeEntry = new DateTime();
    /**
    * The number of regular hours to adjust timecard by.  Can be + or -.
    */
    public TimeSpan RegHours = new TimeSpan();
    /**
    * Overtime hours. Usually +.  Automatically combined with a - adj to RegHours.  Another option is clockevent.OTimeHours.
    */
    public TimeSpan OTimeHours = new TimeSpan();
    /**
    * .
    */
    public String Note = new String();
    /**
    * Set to true if this adjustment was automatically made by the system.  When the calc weekly OT tool is run, these types of adjustments are fair game for deletion.  Other adjustments are preserved.
    */
    public boolean IsAuto = new boolean();
    /**
    * Used only for serialization purposes
    */
    public long getRegHoursXml() throws Exception {
        return RegHours.Ticks;
    }

    public void setRegHoursXml(long value) throws Exception {
        RegHours = TimeSpan.FromTicks(value);
    }

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
    * 
    */
    public TimeAdjust copy() throws Exception {
        return (TimeAdjust)MemberwiseClone();
    }

}


