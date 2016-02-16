//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TaskSubscription;

/**
* 
*/
public class TaskSubscriptions   
{
    /**
    * 
    */
    public static long insert(TaskSubscription subsc) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            subsc.TaskSubscriptionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), subsc);
            return subsc.TaskSubscriptionNum;
        }
         
        return Crud.TaskSubscriptionCrud.Insert(subsc);
    }

    /*
    		///<summary></summary>
    		public static void Update(TaskSubscription subsc) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),subsc);
    				return;
    			}
    			Crud.TaskSubscriptionCrud.Update(subsc);
    		}*/
    /**
    * Creates a subscription to a list.
    */
    public static void subscList(long taskListNum, long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskListNum, userNum);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM tasksubscription " + "WHERE UserNum=" + POut.long(userNum) + " AND TaskListNum=" + POut.long(taskListNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("TaskSubscriptions","User already subscribed."));
        }
         
        TaskSubscription subsc = new TaskSubscription();
        subsc.setIsNew(true);
        subsc.UserNum = userNum;
        subsc.TaskListNum = taskListNum;
        insert(subsc);
    }

    /**
    * Removes a subscription to a list.
    */
    public static void unsubscList(long taskListNum, long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskListNum, userNum);
            return ;
        }
         
        String command = "DELETE FROM tasksubscription " + "WHERE UserNum=" + POut.long(userNum) + " AND TaskListNum=" + POut.long(taskListNum);
        Db.nonQ(command);
    }

    /**
    * Moves all subscriptions from taskListOld to taskListNew. Used when cutting and pasting a tasklist. Can also be used when deleting a tasklist to remove all subscriptions from the tasklist by sending in 0 as taskListNumNew.
    */
    public static void updateAllSubs(long taskListNumOld, long taskListNumNew) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskListNumOld, taskListNumNew);
            return ;
        }
         
        String command = "";
        if (taskListNumNew == 0)
        {
            command = "DELETE FROM tasksubscription WHERE TaskListNum=" + POut.long(taskListNumOld);
        }
        else
        {
            command = "UPDATE tasksubscription SET TaskListNum=" + POut.long(taskListNumNew) + " WHERE TaskListNum=" + POut.long(taskListNumOld);
        } 
        Db.nonQ(command);
    }

}


