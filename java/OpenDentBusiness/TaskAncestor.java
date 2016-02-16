//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Represents one ancestor of one task.  Each task will have at least one ancestor unless it is directly on a main trunk.  An ancestor is defined as a tasklist that is higher in the heirarchy for the task, regardless of how many levels up it is.  This allows us to mark task lists as having "new" tasks, and it allows us to quickly check for new tasks for a user on startup.
*/
public class TaskAncestor  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TaskAncestorNum = new long();
    /**
    * FK to task.TaskNum
    */
    public long TaskNum = new long();
    /**
    * FK to tasklist.TaskListNum
    */
    public long TaskListNum = new long();
}


