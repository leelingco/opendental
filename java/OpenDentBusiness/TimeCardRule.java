//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.TimeCardRule;

/**
* 
*/
public class TimeCardRule  extends TableBase 
{
    /**
    * A rule for automation of timecard overtime.  Can apply to one employee or all.
    */
    public long TimeCardRuleNum = new long();
    /**
    * FK to employee.EmployeeNum. If zero, then this rule applies to all employees.
    */
    public long EmployeeNum = new long();
    /**
    * Typical example is 8:00.  In California, any work after the first 8 hours is overtime.
    */
    public TimeSpan OverHoursPerDay = new TimeSpan();
    /**
    * Typical example is 16:00 to indicate that all time worked after 4pm for specific employees is at differential rate.
    */
    public TimeSpan AfterTimeOfDay = new TimeSpan();
    /**
    * Typical example is 6:00 to indicate that all time worked before 6am for specific employees is at differential rate.
    */
    public TimeSpan BeforeTimeOfDay = new TimeSpan();
    /**
    * Used only for serialization purposes
    */
    public long getOverHoursPerDayXml() throws Exception {
        return OverHoursPerDay.Ticks;
    }

    public void setOverHoursPerDayXml(long value) throws Exception {
        OverHoursPerDay = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getAfterTimeOfDayXml() throws Exception {
        return AfterTimeOfDay.Ticks;
    }

    public void setAfterTimeOfDayXml(long value) throws Exception {
        AfterTimeOfDay = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getBeforeTimeOfDayXml() throws Exception {
        return BeforeTimeOfDay.Ticks;
    }

    public void setBeforeTimeOfDayXml(long value) throws Exception {
        BeforeTimeOfDay = TimeSpan.FromTicks(value);
    }

    /**
    * 
    */
    public TimeCardRule clone() {
        try
        {
            return (TimeCardRule)this.MemberwiseClone();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

}


