//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskAncestor;
import OpenDentBusiness.Tasks;

/**
* 
*/
public class TaskAncestors   
{
    /**
    * 
    */
    public static long insert(TaskAncestor ancestor) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ancestor.TaskAncestorNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ancestor);
            return ancestor.TaskAncestorNum;
        }
         
        return Crud.TaskAncestorCrud.Insert(ancestor);
    }

    /*
    		///<summary></summary>
    		public static void Update(TaskAncestor ancestor) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),ancestor);
    				return;
    			}
    			Crud.TaskAncestorCrud.Update(ancestor);
    		}*/
    public static void synch(Task task) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), task);
            return ;
        }
         
        String command = "DELETE FROM taskancestor WHERE TaskNum=" + POut.long(task.TaskNum);
        Db.nonQ(command);
        long taskListNum = 0;
        long parentNum = task.TaskListNum;
        DataTable table = new DataTable();
        TaskAncestor ancestor;
        while (true)
        {
            if (parentNum == 0)
            {
                break;
            }
             
            //no parent to mark
            //get the parent
            command = "SELECT TaskListNum,Parent FROM tasklist WHERE TaskListNum=" + POut.long(parentNum);
            table = Db.getTable(command);
            if (table.Rows.Count == 0)
            {
                break;
            }
             
            //in case of database inconsistency
            taskListNum = PIn.Long(table.Rows[0]["TaskListNum"].ToString());
            parentNum = PIn.Long(table.Rows[0]["Parent"].ToString());
            ancestor = new TaskAncestor();
            ancestor.TaskNum = task.TaskNum;
            ancestor.TaskListNum = taskListNum;
            insert(ancestor);
        }
    }

    /**
    * Only run once after the upgrade to version 5.5.
    */
    public static void synchAll() throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Task> listTasks = Tasks.refreshAll();
        for (int i = 0;i < listTasks.Count;i++)
        {
            Synch(listTasks[i]);
        }
    }

}


