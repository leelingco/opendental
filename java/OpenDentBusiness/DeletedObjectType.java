//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum DeletedObjectType
{
    /**
    * 0
    */
    Appointment,
    /**
    * 1 - A schedule object.  Only provider schedules are tracked for deletion.
    */
    ScheduleProv,
    /**
    * 2 - When a recall row is deleted, this records the PatNum for which it was deleted.
    */
    RecallPatNum
}

