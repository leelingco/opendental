//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Interval;
import OpenDentBusiness.RecallType;
import OpenDentBusiness.TableBase;

/**
* All recalls are based on these recall types.  Recall triggers are in their own table.
*/
public class RecallType  extends TableBase 
{
    /**
    * Primary key.
    */
    public long RecallTypeNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * The interval between recalls.  The Interval struct combines years, months, weeks, and days into a single integer value.
    */
    public Interval DefaultInterval = new Interval();
    /**
    * For scheduling the appointment.
    */
    public String TimePattern = new String();
    /**
    * What procedures to put on the recall appointment.  Comma delimited set of ProcCodes.  (We may change this to CodeNums).
    */
    public String Procedures = new String();
    public RecallType copy() throws Exception {
        return (RecallType)this.MemberwiseClone();
    }

}


