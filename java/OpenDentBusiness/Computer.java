//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;

/**
* Keeps track of the computers in an office.  The list will eventually become cluttered with the names of old computers that are no longer in service.  The old rows can be safely deleted.  Although the primary key is used in at least one table, this will probably be changed, and the computername will become the primary key.
*/
public class Computer  extends TableBase 
{
    //
    /**
    * Primary key.
    */
    public long ComputerNum = new long();
    /**
    * Name of the computer.
    */
    public String CompName = new String();
    /**
    * Allows use to tell which computers are running.  All workstations record a heartbeat here at an interval of 3 minutes.  And when they shut down, they set this value to min.  So if the heartbeat is fairly fresh, then that's an accurate indicator of whether Open Dental is running on that computer.
    */
    public DateTime LastHeartBeat = new DateTime();
}


