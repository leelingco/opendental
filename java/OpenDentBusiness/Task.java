//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.TableBase;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.TaskStatusEnum;

/**
* A task is a single todo item.
*/
public class Task  extends TableBase 
{
    /**
    * Primary key.
    */
    public long TaskNum = new long();
    /**
    * FK to tasklist.TaskListNum.  If 0, then it will show in the trunk of a section.
    */
    public long TaskListNum = new long();
    /**
    * Only used if this task is assigned to a dated category.  Children are NOT dated.  Only dated if they should show in the trunk for a date category.  They can also have a parent if they are in the main list as well.
    */
    public DateTime DateTask = new DateTime();
    /**
    * FK to patient.PatNum or appointment.AptNum. Only used when ObjectType is not 0.
    */
    public long KeyNum = new long();
    /**
    * The description of this task.  Might be very long.
    */
    public String Descript = new String();
    /**
    * Enum:TaskStatusEnum New,Viewed,Done.  We may want to put an index on this column someday.
    */
    public TaskStatusEnum TaskStatus = TaskStatusEnum.New;
    /**
    * True if it is to show in the repeating section.  There should be no date.  All children and parents should also be set to IsRepeating=true.
    */
    public boolean IsRepeating = new boolean();
    /**
    * Enum:TaskDateType  None, Day, Week, Month.  If IsRepeating, then setting to None effectively disables the repeating feature.
    */
    public TaskDateType DateType = TaskDateType.None;
    /**
    * FK to task.TaskNum  If this is derived from a repeating task, then this will hold the TaskNum of that task.  It helps automate the adding and deleting of tasks.  It might be deleted automatically if not are marked complete.
    */
    public long FromNum = new long();
    /**
    * Enum:TaskObjectType  0=none,1=Patient,2=Appointment.  More will be added later. If a type is selected, then the KeyNum will contain the primary key of the corresponding Patient or Appointment.  Does not really have anything to do with the ObjectType of the parent tasklist, although they tend to match.
    */
    public TaskObjectType ObjectType = TaskObjectType.None;
    /**
    * The date and time that this task was added.  Used to sort the list by the order entered.
    */
    public DateTime DateTimeEntry = new DateTime();
    /**
    * FK to user.UserNum.  The person who created the task.
    */
    public long UserNum = new long();
    /**
    * The date and time that this task was marked "done".
    */
    public DateTime DateTimeFinished = new DateTime();
    /**
    * Only used when tracking unread status by user instead of by task.  This gets set to true to indicate it has not yet been read.
    */
    public boolean IsUnread = new boolean();
    /**
    * Not a database column.  A string description of the parent of this task.  It will only include the immediate parent.  Only useful in the New and OpenTicket tabs.
    */
    public String ParentDesc = new String();
    /**
    * Not a database column.  Attached patient's name (NameLF) if there is an attached patient.
    */
    public String PatientName = new String();
    /**
    * 
    */
    public Task() throws Exception {
        Descript = "";
        ParentDesc = "";
    }

    //Might not be necessary.
    /**
    * 
    */
    public Task copy() throws Exception {
        return (Task)MemberwiseClone();
    }

    public boolean equals(Object obj) {
        try
        {
            if (TaskNum == ((Task)obj).TaskNum && TaskListNum == ((Task)obj).TaskListNum && DateTask == ((Task)obj).DateTask && KeyNum == ((Task)obj).KeyNum && StringSupport.equals(Descript, ((Task)obj).Descript) && TaskStatus == ((Task)obj).TaskStatus && IsRepeating == ((Task)obj).IsRepeating && DateType == ((Task)obj).DateType && FromNum == ((Task)obj).FromNum && ObjectType == ((Task)obj).ObjectType && DateTimeEntry == ((Task)obj).DateTimeEntry && UserNum == ((Task)obj).UserNum && DateTimeFinished == ((Task)obj).DateTimeFinished)
            {
                return true;
            }
             
            return false;
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

    //return base.Equals(obj);
    public int hashCode() {
        try
        {
            return super.GetHashCode();
        }
        catch (RuntimeException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception __dummyCatchVar1)
        {
            throw new RuntimeException(__dummyCatchVar1);
        }
    
    }

}


