//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptViewStackBehavior;
import OpenDentBusiness.TableBase;

/**
* Enables viewing a variety of operatories or providers.  This table holds the views that the user picks between.  The apptviewitem table holds the items attached to each view.
*/
public class ApptView  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ApptViewNum = new long();
    /**
    * Description of this view.  Gets displayed in Appt module.
    */
    public String Description = new String();
    /**
    * 0-based order to display in lists. Every view must have a unique itemorder, but it is acceptable to have some missing itemorders in the sequence.
    */
    public int ItemOrder = new int();
    /**
    * Number of rows per time increment.  Usually 1 or 2.  Programming note: Value updated to ApptDrawing.RowsPerIncr to track current state.
    */
    public byte RowsPerIncr = new byte();
    /**
    * If set to true, then the only operatories that will show will be for providers that have schedules for the day, ops with no provs assigned.
    */
    public boolean OnlyScheduledProvs = new boolean();
    /**
    * If OnlyScheduledProvs is set to true, and this time is not 0:00, then only provider schedules with start or stop time before this time will be included.
    */
    public TimeSpan OnlySchedBeforeTime = new TimeSpan();
    /**
    * If OnlyScheduledProvs is set to true, and this time is not 0:00, then only provider schedules with start or stop time after this time will be included.
    */
    public TimeSpan OnlySchedAfterTime = new TimeSpan();
    /**
    * Enum:ApptViewStackBehavior
    */
    public ApptViewStackBehavior StackBehavUR = ApptViewStackBehavior.Vertical;
    /**
    * Enum:ApptViewStackBehavior
    */
    public ApptViewStackBehavior StackBehavLR = ApptViewStackBehavior.Vertical;
    /**
    * FK to clinic.ClinicNum.  0=All clinics.  If OnlyScheduledProvs is set to true, then only provider schedules with matching clinic will be included.
    */
    public long ClinicNum = new long();
    /**
    * Used only for serialization purposes
    */
    public long getOnlySchedBeforeTimeXml() throws Exception {
        return OnlySchedBeforeTime.Ticks;
    }

    public void setOnlySchedBeforeTimeXml(long value) throws Exception {
        OnlySchedBeforeTime = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getOnlySchedAfterTimeXml() throws Exception {
        return OnlySchedAfterTime.Ticks;
    }

    public void setOnlySchedAfterTimeXml(long value) throws Exception {
        OnlySchedAfterTime = TimeSpan.FromTicks(value);
    }

    public ApptView() throws Exception {
    }

}


