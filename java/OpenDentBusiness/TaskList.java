//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskObjectType;

/**
* A tasklist is like a folder system, where it can have child tasklists as well as tasks.
*/
public class TaskList  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TaskListNum = new long();
    /**
    * The description of this tasklist.  Might be very long, but not usually.
    */
    public String Descript = new String();
    /**
    * FK to tasklist.TaskListNum  The parent task list to which this task list is assigned.  If zero, then this task list is on the main trunk of one of the sections.
    */
    public long Parent = new long();
    /**
    * Optional. Set to 0001-01-01 for no date.  If a date is assigned, then this list will also be available from the date section.
    */
    public DateTime DateTL = new DateTime();
    /**
    * True if it is to show in the repeating section.  There should be no date.  All children should also be set to IsRepeating=true.
    */
    public boolean IsRepeating = new boolean();
    /**
    * Enum:TaskDateType  None, Day, Week, Month.  If IsRepeating, then setting to None effectively disables the repeating feature.
    */
    public TaskDateType DateType = TaskDateType.None;
    /**
    * FK to tasklist.TaskListNum  If this is derived from a repeating list, then this will hold the TaskListNum of that list.  It helps automate the adding and deleting of lists.  It might be deleted automatically if no tasks are marked complete.
    */
    public long FromNum = new long();
    /**
    * Enum:TaskObjectType  0=none, 1=Patient, 2=Appointment.  More will be added later. If a type is selected, then this list will be visible in the appropriate places for attaching the correct type of object.  The type is not copied to a task when created.  Tasks in this list do not have to be of the same type.  You can only attach an object to a task, not a tasklist.
    */
    public TaskObjectType ObjectType = TaskObjectType.None;
    /**
    * The date and time that this list was added.  Used to sort the list by the order entered.
    */
    public DateTime DateTimeEntry = new DateTime();
    /**
    * Not a database column.  A string description of the parents of this list.  Might look like this: MegaParent/Parent/  This string may then be tacked on before the Descript to indicate the heirarchy.  It will extend a max of 3 levels.  Only useful in the User tab.
    */
    public String ParentDesc = new String();
    /**
    * Not a database column.  The number of new tasks found within a tasklist.  Used in the user tab to turn the tasklist orange, indicating that tasks are present.
    */
    public int NewTaskCount = new int();
    /**
    * 
    */
    public TaskList copy() throws Exception {
        return (TaskList)MemberwiseClone();
    }

}


