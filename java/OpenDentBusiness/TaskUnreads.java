//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TaskStatusEnum;
import OpenDentBusiness.TaskUnread;

/**
* 
*/
public class TaskUnreads   
{
    /**
    * 
    */
    public static long insert(TaskUnread taskUnread) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            taskUnread.TaskUnreadNum = Meth.GetLong(MethodBase.GetCurrentMethod(), taskUnread);
            return taskUnread.TaskUnreadNum;
        }
         
        return Crud.TaskUnreadCrud.Insert(taskUnread);
    }

    /**
    * Sets a task read by a user by deleting all the matching taskunreads.  Quick and efficient to run any time.
    */
    public static void setRead(long userNum, long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), userNum, taskNum);
            return ;
        }
         
        String command = "DELETE FROM taskunread WHERE UserNum = " + POut.long(userNum) + " " + "AND TaskNum = " + POut.long(taskNum);
        Db.nonQ(command);
    }

    public static void addUnreads(long taskNum, long curUserNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskNum, curUserNum);
            return ;
        }
         
        //if the task is done, don't add unreads
        String command = "SELECT TaskStatus,UserNum FROM task WHERE TaskNum = " + POut.long(taskNum);
        DataTable table = Db.getTable(command);
        TaskStatusEnum taskStatus = (TaskStatusEnum)PIn.Int(table.Rows[0]["TaskStatus"].ToString());
        long userNumOwner = PIn.Long(table.Rows[0]["UserNum"].ToString());
        if (taskStatus == TaskStatusEnum.Done)
        {
            return ;
        }
         
        //
        //Set it unread for the original owner of the task.
        if (userNumOwner != curUserNum)
        {
            //but only if it's some other user
            setUnread(userNumOwner,taskNum);
        }
         
        //Then, for anyone subscribed
        long userNum = new long();
        //task subscriptions are not cached yet, so we use a query.
        //Get a list of all subscribers to this task
        command = "SELECT tasksubscription.UserNum " + "FROM tasksubscription,taskancestor,tasklist " + "WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND taskancestor.TaskNum = " + POut.long(taskNum) + " " + "AND tasksubscription.TaskListNum=tasklist.TaskListNum";
        table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            //Crud.TaskSubscriptionCrud.SelectMany(
            userNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
            if (userNum == userNumOwner)
            {
                continue;
            }
             
            //already set
            if (userNum == curUserNum)
            {
                continue;
            }
             
            //If the current user is subscribed to this task.
            //User has obviously already read it.
            setUnread(userNum,taskNum);
        }
    }

    //This no longer results in duplicates like it used to
    public static boolean isUnread(long userNum, long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), userNum, taskNum);
        }
         
        String command = "SELECT COUNT(*) FROM taskunread WHERE UserNum = " + POut.long(userNum) + " " + "AND TaskNum = " + POut.long(taskNum);
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Sets unread for a single user.  Works well without duplicates, whether it's already set to Unread(new) or not.
    */
    public static void setUnread(long userNum, long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), userNum, taskNum);
            return ;
        }
         
        if (isUnread(userNum,taskNum))
        {
            return ;
        }
         
        //Already set to unread, so nothing else to do
        TaskUnread taskUnread = new TaskUnread();
        taskUnread.TaskNum = taskNum;
        taskUnread.UserNum = userNum;
        insert(taskUnread);
    }

    public static void deleteForTask(long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskNum);
            return ;
        }
         
        String command = "DELETE FROM taskunread WHERE TaskNum = " + POut.long(taskNum);
        Db.nonQ(command);
    }

}


