//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TaskNote;

/**
* 
*/
public class TaskNotes   
{
    /**
    * A list of notes for one task, ordered by datetime.
    */
    public static List<TaskNote> getForTask(long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskNote>>GetObject(MethodBase.GetCurrentMethod(), taskNum);
        }
         
        String command = "SELECT * FROM tasknote WHERE TaskNum = " + POut.long(taskNum) + " ORDER BY DateTimeNote";
        return Crud.TaskNoteCrud.SelectMany(command);
    }

    /**
    * A list of notes for multiple tasks, ordered by date time.
    */
    public static List<TaskNote> refreshForTasks(List<long> taskNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskNote>>GetObject(MethodBase.GetCurrentMethod(), taskNums);
        }
         
        if (taskNums.Count == 0)
        {
            return new List<TaskNote>();
        }
         
        String command = "SELECT * FROM tasknote WHERE TaskNum IN (";
        for (int i = 0;i < taskNums.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += POut.Long(taskNums[i]);
        }
        command += ") ORDER BY DateTimeNote";
        return Crud.TaskNoteCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(TaskNote taskNote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            taskNote.TaskNoteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), taskNote);
            return taskNote.TaskNoteNum;
        }
         
        return Crud.TaskNoteCrud.Insert(taskNote);
    }

    /**
    * 
    */
    public static void update(TaskNote taskNote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskNote);
            return ;
        }
         
        Crud.TaskNoteCrud.Update(taskNote);
    }

    /**
    * 
    */
    public static void delete(long taskNoteNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskNoteNum);
            return ;
        }
         
        String command = "DELETE FROM tasknote WHERE TaskNoteNum = " + POut.long(taskNoteNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one TaskNote from the db.</summary>
		public static TaskNote GetOne(long taskNoteNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<TaskNote>(MethodBase.GetCurrentMethod(),taskNoteNum);
			}
			return Crud.TaskNoteCrud.SelectOne(taskNoteNum);
		}
		
	
		*/