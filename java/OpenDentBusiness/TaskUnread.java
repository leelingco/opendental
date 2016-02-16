//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Account;
import OpenDentBusiness.TableBase;

/**
* When a task is created or a comment made, a series of these taskunread objects are created, one for each user who is subscribed to the tasklist.  Duplicates are intelligently avoided.  Rows are deleted once user reads the task.
*/
public class TaskUnread  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TaskUnreadNum = new long();
    /**
    * FK to task.TaskNum.
    */
    public long TaskNum = new long();
    /**
    * FK to userod.UserNum.
    */
    public long UserNum = new long();
    /**
    * 
    */
    public Account clone() {
        try
        {
            return (Account)this.MemberwiseClone();
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


