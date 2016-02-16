//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.TableBase;

/**
* Links one planned appointment to one patient.  Allows multiple planned appointments per patient.  Also see the PlannedIsDone field. A planned appointment is an appointment that will show in the Chart module and in the Planned appointment tracker. It will never show in the Appointments module. In other words, it is the suggested next appoinment rather than an appointment that has already been scheduled.
*/
public class PlannedAppt  extends TableBase 
{
    /**
    * Primary key.
    */
    public long PlannedApptNum = new long();
    /**
    * FK to patient.PatNum.
    */
    public long PatNum = new long();
    /**
    * FK to appointment.AptNum.
    */
    public long AptNum = new long();
    /**
    * One-indexed order of item in group of planned appts.
    */
    public int ItemOrder = new int();
    public PlannedAppt copy() throws Exception {
        return (PlannedAppt)this.MemberwiseClone();
    }

}


