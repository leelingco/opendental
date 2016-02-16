//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;

import OpenDentBusiness.LabTurnaround;
import OpenDentBusiness.TableBase;

/**
* The amount of time it takes for a lab case to be processed at the lab.  Used to compute due dates.
*/
public class LabTurnaround  extends TableBase 
{
    /**
    * Primary key.
    */
    public long LabTurnaroundNum = new long();
    /**
    * FK to laboratory.LaboratoryNum. The lab that this item is attached to.
    */
    public long LaboratoryNum = new long();
    /**
    * The description of the service that the lab is performing.
    */
    public String Description = new String();
    /**
    * The number of days that the lab publishes as the turnaround time for the service.
    */
    public int DaysPublished = new int();
    /**
    * The actual number of days.  Might be longer than DaysPublished due to travel time.  This is what the actual calculations will be done on.
    */
    public int DaysActual = new int();
    public LabTurnaround copy() throws Exception {
        return (LabTurnaround)this.MemberwiseClone();
    }

}


