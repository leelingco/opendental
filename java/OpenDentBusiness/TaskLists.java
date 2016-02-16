//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskList;
import OpenDentBusiness.TaskObjectType;

/**
* 
*/
public class TaskLists   
{
    /**
    * Gets all task lists for the trunk of the user tab.
    */
    public static List<TaskList> refreshUserTrunk(long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskList>>GetObject(MethodBase.GetCurrentMethod(), userNum);
        }
         
        String command = "SELECT tasklist.*," + "(SELECT COUNT(*) FROM taskancestor,task WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum ";
        if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
        {
            command += "AND EXISTS(SELECT * FROM taskunread WHERE taskunread.TaskNum=task.TaskNum " + "AND taskunread.UserNum=" + POut.long(userNum) + ") " + "AND task.TaskStatus !=2 ";
        }
        else
        {
            //not done
            command += "AND task.TaskStatus=0 ";
        } 
        //+"LEFT JOIN taskancestor ON taskancestor.TaskList=tasklist.TaskList "
        //+"LEFT JOIN task ON task.TaskNum=taskancestor.TaskNum AND task.TaskStatus=0 "
        command += ")," + "t2.Descript,t3.Descript FROM tasksubscription " + "LEFT JOIN tasklist ON tasklist.TaskListNum=tasksubscription.TaskListNum " + "LEFT JOIN tasklist t2 ON t2.TaskListNum=tasklist.Parent " + "LEFT JOIN tasklist t3 ON t3.TaskListNum=t2.Parent " + "WHERE tasksubscription.UserNum=" + POut.long(userNum) + " AND tasksubscription.TaskListNum!=0 " + "ORDER BY tasklist.DateTimeEntry";
        return tableToList(Db.getTable(command));
    }

    /**
    * Gets all task lists for the main trunk.  Pass in the current user.
    */
    public static List<TaskList> refreshMainTrunk(long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskList>>GetObject(MethodBase.GetCurrentMethod(), userNum);
        }
         
        String command = "SELECT tasklist.*,IFNULL(A.Num,0) " + "FROM tasklist " + "LEFT JOIN (SELECT tasklist.TaskListNum,COUNT(*) Num FROM tasklist,taskancestor,task WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum ";
        if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
        {
            command += "AND EXISTS(SELECT * FROM taskunread WHERE taskunread.TaskNum=task.TaskNum ";
            //if a list is someone's inbox,
            command += "AND (CASE WHEN EXISTS(SELECT * FROM userod WHERE userod.TaskListInBox=tasklist.TaskListNum) ";
            //then restrict by that user
            command += "THEN (taskunread.UserNum=(SELECT UserNum FROM userod WHERE userod.TaskListInBox=tasklist.TaskListNum LIMIT 1)) ";
            //otherwise, restrict by current user
            command += "ELSE taskunread.UserNum=" + POut.long(userNum) + " END)) " + "AND task.TaskStatus !=2 ";
        }
        else
        {
            //not done
            command += "AND task.TaskStatus=0 ";
        } 
        command += "GROUP BY tasklist.TaskListNum) A ON A.TaskListNum=tasklist.TaskListNum " + "WHERE Parent=0 " + "AND DateTL < " + POut.date(new DateTime(1880, 01, 01)) + " " + "AND IsRepeating=0 " + "ORDER BY Descript";
        return tableToList(Db.getTable(command));
    }

    //DateTimeEntry";
    /**
    * Gets all task lists for the repeating trunk.
    */
    public static List<TaskList> refreshRepeatingTrunk() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskList>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        //I don't think the repeating trunk would ever track by user, so no special treatment here.
        //Acutual behavior in both cases needs to be tested.
        String command = "SELECT tasklist.*," + "(SELECT COUNT(*) FROM taskancestor,task WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum AND task.TaskStatus=0) " + "FROM tasklist " + "WHERE Parent=0 " + "AND DateTL < " + POut.date(new DateTime(1880, 01, 01)) + " " + "AND IsRepeating=1 " + "ORDER BY DateTimeEntry";
        return tableToList(Db.getTable(command));
    }

    /**
    * 0 is not allowed, because that would be a trunk.  Pass in the current user.  Also, if this is in someone's inbox, then pass in the userNum whose inbox it is in.  If not in an inbox, pass in 0.
    */
    public static List<TaskList> refreshChildren(long parent, long userNum, long userNumInbox) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskList>>GetObject(MethodBase.GetCurrentMethod(), parent, userNum, userNumInbox);
        }
         
        String command = "SELECT tasklist.*," + "(SELECT COUNT(*) FROM taskancestor,task WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum ";
        if (PrefC.getBool(PrefName.TasksNewTrackedByUser))
        {
            command += "AND EXISTS(SELECT * FROM taskunread WHERE taskunread.TaskNum=task.TaskNum ";
            //if a list is someone's inbox,
            if (userNumInbox > 0)
            {
                //then restrict by that user
                command += "AND taskunread.UserNum=" + POut.long(userNumInbox) + ") ";
            }
            else
            {
                //otherwise, restrict by current user
                command += "AND taskunread.UserNum=" + POut.long(userNum) + ") ";
            } 
        }
        else
        {
            command += "AND task.TaskStatus=0";
        } 
        command += ") " + "FROM tasklist " + "WHERE Parent=" + POut.long(parent) + " ORDER BY DateTimeEntry";
        return tableToList(Db.getTable(command));
    }

    /**
    * All repeating items for one date type with no heirarchy.
    */
    public static List<TaskList> refreshRepeating(TaskDateType dateType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskList>>GetObject(MethodBase.GetCurrentMethod(), dateType);
        }
         
        //See the note in RefreshRepeatingTrunk.  Behavior needs to be tested.
        String command = "SELECT tasklist.*," + "(SELECT COUNT(*) FROM taskancestor,task WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum AND task.TaskStatus=0) " + "FROM tasklist " + "WHERE IsRepeating=1 " + "AND DateType=" + POut.Long(((Enum)dateType).ordinal()) + " " + "ORDER BY DateTimeEntry";
        return tableToList(Db.getTable(command));
    }

    /**
    * Gets all task lists for one of the 3 dated trunks.
    */
    public static List<TaskList> refreshDatedTrunk(DateTime date, TaskDateType dateType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TaskList>>GetObject(MethodBase.GetCurrentMethod(), date, dateType);
        }
         
        DateTime dateFrom = DateTime.MinValue;
        DateTime dateTo = DateTime.MaxValue;
        if (dateType == TaskDateType.Day)
        {
            dateFrom = date;
            dateTo = date;
        }
        else if (dateType == TaskDateType.Week)
        {
            dateFrom = date.AddDays(-((int)date.DayOfWeek));
            dateTo = dateFrom.AddDays(6);
        }
        else if (dateType == TaskDateType.Month)
        {
            dateFrom = new DateTime(date.Year, date.Month, 1);
            dateTo = dateFrom.AddMonths(1).AddDays(-1);
        }
           
        String command = "SELECT tasklist.*," + "(SELECT COUNT(*) FROM taskancestor,task WHERE taskancestor.TaskListNum=tasklist.TaskListNum " + "AND task.TaskNum=taskancestor.TaskNum ";
        //if(PrefC.GetBool(PrefName.TasksNewTrackedByUser)) {
        //	command+="AND EXISTS(SELECT * FROM taskunread WHERE taskunread.TaskNum=task.TaskNum)";
        //}
        //else {
        command += "AND task.TaskStatus=0";
        //}
        command += ") " + "FROM tasklist " + "WHERE DateTL >= " + POut.date(dateFrom) + " AND DateTL <= " + POut.date(dateTo) + " AND DateType=" + POut.Long(((Enum)dateType).ordinal()) + " ORDER BY DateTimeEntry";
        return tableToList(Db.getTable(command));
    }

    /**
    * 
    */
    public static TaskList getOne(long taskListNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<TaskList>GetObject(MethodBase.GetCurrentMethod(), taskListNum);
        }
         
        if (taskListNum == 0)
        {
            return null;
        }
         
        String command = "SELECT * FROM tasklist WHERE TaskListNum=" + POut.long(taskListNum);
        return Crud.TaskListCrud.SelectOne(command);
    }

    /*
    		///<Summary>Gets all task lists in the general tab with no heirarchy.  This allows us to loop through the list to grab useful heirarchy info.  Only used when viewing user tab.  Not guaranteed to get all tasklists, because we exclude those with a DateType.</Summary>
    		public static List<TaskList> GetAllGeneral(){
    //THIS WON'T WORK BECAUSE THERE ARE TOO MANY REPEATING TASKLISTS.
    			string command="SELECT * FROM tasklist WHERE DateType=0 ";
    		}*/
    private static List<TaskList> tableToList(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<TaskList> retVal = Crud.TaskListCrud.TableToList(table);
        TaskList tasklist;
        String desc = new String();
        for (int i = 0;i < retVal.Count;i++)
        {
            if (table.Columns.Count > 9)
            {
                retVal[i].NewTaskCount = PIn.Int(table.Rows[i][9].ToString());
            }
             
            if (table.Columns.Count > 10)
            {
                desc = PIn.String(table.Rows[i][10].ToString());
                if (!StringSupport.equals(desc, ""))
                {
                    retVal[i].ParentDesc = desc + "/";
                }
                 
                desc = PIn.String(table.Rows[i][11].ToString());
                if (!StringSupport.equals(desc, ""))
                {
                    retVal[i].ParentDesc = desc + "/" + retVal[i].ParentDesc;
                }
                 
            }
             
        }
        return retVal;
    }

    /**
    * Gets all task lists with the give object type.  Used in TaskListSelect when assigning an object to a task list.
    */
    public static TaskList[] getForObjectType(TaskObjectType oType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<TaskList[]>GetObject(MethodBase.GetCurrentMethod(), oType);
        }
         
        String command = "SELECT * FROM tasklist " + "WHERE ObjectType=" + POut.Long(((Enum)oType).ordinal()) + " ORDER BY Descript";
        return Crud.TaskListCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(TaskList tlist) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), tlist);
            return ;
        }
         
        if (tlist.IsRepeating && tlist.DateTL.Year > 1880)
        {
            throw new Exception(Lans.g("TaskLists","TaskList cannot be tagged repeating and also have a date."));
        }
         
        if (tlist.Parent == 0 && tlist.DateTL.Year > 1880 && tlist.DateType == TaskDateType.None)
        {
            throw new Exception(Lans.g("TaskLists","A TaskList with a date must also have a type selected."));
        }
         
        //it would not show anywhere, so it would be 'lost'
        if (tlist.IsRepeating && tlist.Parent != 0 && tlist.DateType != TaskDateType.None)
        {
            throw new Exception(Lans.g("TaskLists","In repeating tasklists, only the main parents can have a task status."));
        }
         
        //In repeating, children not allowed to repeat.
        Crud.TaskListCrud.Update(tlist);
    }

    /**
    * 
    */
    public static long insert(TaskList tlist) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            tlist.TaskListNum = Meth.GetLong(MethodBase.GetCurrentMethod(), tlist);
            return tlist.TaskListNum;
        }
         
        if (tlist.IsRepeating && tlist.DateTL.Year > 1880)
        {
            throw new Exception(Lans.g("TaskLists","TaskList cannot be tagged repeating and also have a date."));
        }
         
        if (tlist.Parent == 0 && tlist.DateTL.Year > 1880 && tlist.DateType == TaskDateType.None)
        {
            throw new Exception(Lans.g("TaskLists","A TaskList with a date must also have a type selected."));
        }
         
        //it would not show anywhere, so it would be 'lost'
        if (tlist.IsRepeating && tlist.Parent != 0 && tlist.DateType != TaskDateType.None)
        {
            throw new Exception(Lans.g("TaskLists","In repeating tasklists, only the main parents can have a task status."));
        }
         
        return Crud.TaskListCrud.Insert(tlist);
    }

    //In repeating, children not allowed to repeat.
    /**
    * Throws exception if any child tasklists or tasks.
    */
    public static void delete(TaskList tlist) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), tlist);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM tasklist WHERE Parent=" + POut.long(tlist.TaskListNum);
        DataTable table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            throw new Exception(Lans.g("TaskLists","Not allowed to delete task list because it still has child lists attached."));
        }
         
        command = "SELECT COUNT(*) FROM task WHERE TaskListNum=" + POut.long(tlist.TaskListNum);
        table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            throw new Exception(Lans.g("TaskLists","Not allowed to delete task list because it still has child tasks attached."));
        }
         
        command = "SELECT COUNT(*) FROM userod WHERE TaskListInBox=" + POut.long(tlist.TaskListNum);
        table = Db.getTable(command);
        if (!StringSupport.equals(table.Rows[0][0].ToString(), "0"))
        {
            throw new Exception(Lans.g("TaskLists","Not allowed to delete task list because it is attached to a user inbox."));
        }
         
        command = "DELETE from tasklist WHERE TaskListNum = '" + POut.long(tlist.TaskListNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Will return 0 if not anyone's inbox.
    */
    public static long getMailboxUserNum(long taskListNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), taskListNum);
        }
         
        String command = "SELECT UserNum FROM userod WHERE TaskListInBox=" + POut.long(taskListNum);
        return PIn.long(Db.getScalar(command));
    }

    /**
    * Checks all ancestors of a task.  Will return 0 if no ancestor is anyone's inbox.
    */
    public static long getMailboxUserNumByAncestor(long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), taskNum);
        }
         
        String command = "SELECT UserNum FROM taskancestor,userod " + "WHERE taskancestor.TaskListNum=userod.TaskListInBox " + "AND taskancestor.TaskNum=" + POut.long(taskNum);
        return PIn.long(Db.getScalar(command));
    }

}


