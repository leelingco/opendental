//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ScheduleOp;
import OpenDentBusiness.TableBase;

/**
* Links one schedule block to one operatory.  So for a schedule block to show, it must be linked to one or more operatories.
*/
public class ScheduleOp  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ScheduleOpNum = new long();
    /**
    * FK to schedule.ScheduleNum.
    */
    public long ScheduleNum = new long();
    /**
    * FK to operatory.OperatoryNum.
    */
    public long OperatoryNum = new long();
    public ScheduleOp copy() throws Exception {
        return (ScheduleOp)this.MemberwiseClone();
    }

}


