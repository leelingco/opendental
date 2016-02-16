//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* A subscription of one user to either a tasklist or to a task.
*/
public class TaskSubscription  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TaskSubscriptionNum = new long();
    /**
    * FK to userod.UserNum
    */
    public long UserNum = new long();
    /**
    * FK to tasklist.TaskListNum
    */
    public long TaskListNum = new long();
}


