//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.TaskNote;

/**
* A tasknote is a note that may be added to a task. Many notes may be attached to a task. A user may only edit their own tasknotes within a task.
*/
public class TaskNote  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TaskNoteNum = new long();
    /**
    * FK to task. The task this tasknote is attached to.
    */
    public long TaskNum = new long();
    /**
    * FK to user. The user who created this tasknote.
    */
    public long UserNum = new long();
    /**
    * Date and time the note was created (editable).
    */
    public DateTime DateTimeNote = new DateTime();
    /**
    * Note. Text that the user wishes to show on the task.
    */
    public String Note = new String();
    /**
    * 
    */
    public TaskNote copy() throws Exception {
        return (TaskNote)MemberwiseClone();
    }

}


