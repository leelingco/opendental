//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Task;
import OpenDentBusiness.TaskAncestors;
import OpenDentBusiness.TaskDateType;
import OpenDentBusiness.TaskObjectType;
import OpenDentBusiness.TaskStatusEnum;

/**
* Not part of cache refresh.
*/
public class Tasks   
{
    /**
    * Only used from UI.
    */
    public static ArrayList LastOpenList = new ArrayList();
    /**
    * Only used from UI.  The index of the last open tab.
    */
    public static int LastOpenGroup = new int();
    /**
    * Only used from UI.
    */
    public static DateTime LastOpenDate = new DateTime();
    /**
    * This is needed because of the extra column that is not part of the database.
    */
    private static List<Task> tableToList(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Task> retVal = Crud.TaskCrud.TableToList(table);
        for (int i = 0;i < retVal.Count;i++)
        {
            if (table.Columns.Contains("IsUnread"))
            {
                retVal[i].IsUnread = PIn.Bool(table.Rows[i]["IsUnread"].ToString());
            }
             
            //1 or more will result in true.
            if (table.Columns.Contains("ParentDesc"))
            {
                retVal[i].ParentDesc = PIn.String(table.Rows[i]["ParentDesc"].ToString());
            }
             
            if (table.Columns.Contains("LName") && table.Columns.Contains("FName") && table.Columns.Contains("Preferred"))
            {
                String lname = PIn.String(table.Rows[i]["LName"].ToString());
                String fname = PIn.String(table.Rows[i]["FName"].ToString());
                String preferred = PIn.String(table.Rows[i]["Preferred"].ToString());
                retVal[i].PatientName = Patients.getNameLF(lname,fname,preferred,"");
            }
             
        }
        return retVal;
    }

    /*
    		///<summary>There are NO tasks on the user trunk, so this is not needed.</summary>
    		public static List<Task> RefreshUserTrunk(int userNum) {
    			string command="SELECT task.* FROM tasksubscription "
    				+"LEFT JOIN task ON task.TaskNum=tasksubscription.TaskNum "
    				+"WHERE tasksubscription.UserNum="+POut.PInt(userNum)
    				+" AND tasksubscription.TaskNum!=0 "
    				+"ORDER BY DateTimeEntry";
    			return RefreshAndFill(command);
    		}*/
    /**
    * Gets one Task from database.
    */
    public static Task getOne(long TaskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Task>GetObject(MethodBase.GetCurrentMethod(), TaskNum);
        }
         
        String command = "SELECT * FROM task WHERE TaskNum = " + POut.long(TaskNum);
        return Crud.TaskCrud.SelectOne(command);
    }

    /**
    * Gets all tasks for the main trunk.
    */
    public static List<Task> refreshMainTrunk(boolean showDone, DateTime startDate, long currentUserNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), showDone, startDate, currentUserNum);
        }
         
        //startDate only applies if showing Done tasks.
        String command = "SELECT task.*," + "(SELECT COUNT(*) FROM taskunread WHERE task.TaskNum=taskunread.TaskNum " + "AND taskunread.UserNum=" + POut.long(currentUserNum) + ") IsUnread, " + "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred " + "FROM task " + "WHERE TaskListNum=0 " + "AND DateTask < " + POut.date(new DateTime(1880, 01, 01)) + " " + "AND IsRepeating=0";
        if (showDone)
        {
            command += " AND (TaskStatus !=" + POut.Long(((Enum)TaskStatusEnum.Done).ordinal()) + " OR DateTimeFinished > " + POut.date(startDate) + ")";
        }
        else
        {
            //of if done, then restrict date
            command += " AND TaskStatus !=" + POut.Long(((Enum)TaskStatusEnum.Done).ordinal());
        } 
        command += " ORDER BY DateTimeEntry";
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * Gets all 'new' tasks for a user.
    */
    public static List<Task> refreshUserNew(long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), userNum);
        }
         
        //we fill the IsUnread column with 1's because we already know that they are all unread
        String command = "SELECT task.*,1 AS IsUnread, " + "(SELECT tasklist.Descript FROM tasklist WHERE task.TaskListNum=tasklist.TaskListNum) ParentDesc, " + "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred " + "FROM task,taskunread " + "WHERE task.TaskNum=taskunread.TaskNum " + "AND taskunread.UserNum = " + POut.long(userNum) + " " + "GROUP BY task.TaskNum " + "ORDER BY task.DateTimeEntry";
        //in case there are duplicate unreads
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * Gets all 'open ticket' tasks for a user.  An open ticket is a task that was created by this user, is attached to a patient, and is not done.
    */
    public static List<Task> refreshOpenTickets(long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), userNum);
        }
         
        //if any ancestor is a dated list, then we don't want that task
        //+"AND NOT EXISTS(SELECT * FROM taskancestor,tasksubscription "//a different set of ancestors
        //+"WHERE taskancestor.TaskNum=task.TaskNum "
        //+"AND tasksubscription.TaskListNum=taskancestor.TaskListNum "
        //+"AND tasksubscription.UserNum="+POut.Long(userNum)+") "//if this user is subscribed to any ancestor list, then we won't include it
        //this only handles tasks directly in the dated trunks
        String command = "SELECT task.*, " + "(SELECT COUNT(*) FROM taskunread WHERE task.TaskNum=taskunread.TaskNum " + "AND taskunread.UserNum=" + POut.long(userNum) + ") AS IsUnread, " + "(SELECT tasklist.Descript FROM tasklist WHERE task.TaskListNum=tasklist.TaskListNum) ParentDesc, " + "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred " + "FROM task " + "WHERE NOT EXISTS(SELECT * FROM taskancestor,tasklist " + "WHERE taskancestor.TaskNum=task.TaskNum " + "AND tasklist.TaskListNum=taskancestor.TaskListNum " + "AND tasklist.DateType!=0) " + "AND task.DateType=0 " + "AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + " " + "AND task.IsRepeating=0 " + "AND task.UserNum=" + POut.long(userNum) + " " + "AND TaskStatus != " + POut.int(((Enum)TaskStatusEnum.Done).ordinal()) + " " + "ORDER BY DateTimeEntry";
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * Gets all tasks for the repeating trunk.  Always includes "done".
    */
    public static List<Task> refreshRepeatingTrunk() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT task.*, " + "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred " + "FROM task " + "WHERE TaskListNum=0 " + "AND DateTask < " + POut.date(new DateTime(1880, 01, 01)) + " " + "AND IsRepeating=1 " + "ORDER BY DateTimeEntry";
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * 0 is not allowed, because that would be a trunk.  Also, if this is in someone's inbox, then pass in the userNum whose inbox it is in.  If not in an inbox, pass in 0.
    */
    public static List<Task> refreshChildren(long listNum, boolean showDone, DateTime startDate, long currentUserNum, long userNumInbox) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), listNum, showDone, startDate, currentUserNum, userNumInbox);
        }
         
        //startDate only applies if showing Done tasks.
        String command = "SELECT task.*, " + "(SELECT COUNT(*) FROM taskunread WHERE task.TaskNum=taskunread.TaskNum ";
        //the count turns into a bool
        //if(PrefC.GetBool(PrefName.TasksNewTrackedByUser)) {//we don't bother with this.  Always get IsUnread
        //if a task is someone's inbox,
        if (userNumInbox > 0)
        {
            //then restrict by that user
            command += "AND taskunread.UserNum=" + POut.long(userNumInbox) + ") IsUnread, ";
        }
        else
        {
            //otherwise, restrict by current user
            command += "AND taskunread.UserNum=" + POut.long(currentUserNum) + ") IsUnread, ";
        } 
        command += "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred ";
        command += "FROM task " + "WHERE TaskListNum=" + POut.long(listNum);
        if (showDone)
        {
            command += " AND (TaskStatus !=" + POut.Long(((Enum)TaskStatusEnum.Done).ordinal()) + " OR DateTimeFinished > " + POut.date(startDate) + ")";
        }
        else
        {
            //of if done, then restrict date
            command += " AND TaskStatus !=" + POut.Long(((Enum)TaskStatusEnum.Done).ordinal());
        } 
        command += " ORDER BY DateTimeEntry";
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * All repeating items for one date type with no heirarchy.
    */
    public static List<Task> refreshRepeating(TaskDateType dateType, long currentUserNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), dateType, currentUserNum);
        }
         
        //Not sure if this makes sense here
        String command = "SELECT task.*, " + "(SELECT COUNT(*) FROM taskunread WHERE task.TaskNum=taskunread.TaskNum " + "AND taskunread.UserNum=" + POut.long(currentUserNum) + ") IsUnread, " + "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred " + "FROM task " + "WHERE IsRepeating=1 " + "AND DateType=" + POut.Long(((Enum)dateType).ordinal()) + " " + "ORDER BY DateTimeEntry";
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * Gets all tasks for one of the 3 dated trunks. startDate only applies if showing Done.
    */
    public static List<Task> refreshDatedTrunk(DateTime date, TaskDateType dateType, boolean showDone, DateTime startDate, long currentUserNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod(), date, dateType, showDone, startDate, currentUserNum);
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
           
        //Not sure if this makes sense here
        String command = "SELECT task.*, " + "(SELECT COUNT(*) FROM taskunread WHERE task.TaskNum=taskunread.TaskNum " + "AND taskunread.UserNum=" + POut.long(currentUserNum) + ") IsUnread, " + "(SELECT LName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") LName, " + "(SELECT FName FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") FName, " + "(SELECT Preferred FROM patient WHERE task.KeyNum=patient.PatNum AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + ") Preferred " + "FROM task " + "WHERE DateTask >= " + POut.date(dateFrom) + " AND DateTask <= " + POut.date(dateTo) + " AND DateType=" + POut.Long(((Enum)dateType).ordinal());
        if (showDone)
        {
            command += " AND (TaskStatus !=" + POut.Long(((Enum)TaskStatusEnum.Done).ordinal()) + " OR DateTimeFinished > " + POut.date(startDate) + ")";
        }
        else
        {
            //of if done, then restrict date
            command += " AND TaskStatus !=" + POut.Long(((Enum)TaskStatusEnum.Done).ordinal());
        } 
        command += " ORDER BY DateTimeEntry";
        DataTable table = Db.getTable(command);
        return tableToList(table);
    }

    /**
    * The full refresh is only used once when first synching all the tasks for taskAncestors.
    */
    public static List<Task> refreshAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Task>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM task WHERE TaskListNum != 0";
        return Crud.TaskCrud.SelectMany(command);
    }

    /*
    		public static List<Task> RefreshAndFill(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			List<Task> retVal=new List<Task>();
    			Task task;
    			for(int i=0;i<table.Rows.Count;i++) {
    				task=new Task();
    				task.TaskNum        = PIn.Long(table.Rows[i][0].ToString());
    				task.TaskListNum    = PIn.Long(table.Rows[i][1].ToString());
    				task.DateTask       = PIn.Date(table.Rows[i][2].ToString());
    				task.KeyNum         = PIn.Long(table.Rows[i][3].ToString());
    				task.Descript       = PIn.String(table.Rows[i][4].ToString());
    				task.TaskStatus     = (TaskStatusEnum)PIn.Long(table.Rows[i][5].ToString());
    				task.IsRepeating    = PIn.Bool(table.Rows[i][6].ToString());
    				task.DateType       = (TaskDateType)PIn.Long(table.Rows[i][7].ToString());
    				task.FromNum        = PIn.Long(table.Rows[i][8].ToString());
    				task.ObjectType     = (TaskObjectType)PIn.Long(table.Rows[i][9].ToString());
    				task.DateTimeEntry  = PIn.DateT(table.Rows[i][10].ToString());
    				task.UserNum        = PIn.Long(table.Rows[i][11].ToString());
    				task.DateTimeFinished= PIn.DateT(table.Rows[i][12].ToString());
    				retVal.Add(task);
    			}
    			return retVal;
    		}*/
    /**
    * Must supply the supposedly unaltered oldTask.  The update will fail if oldTask does not exactly match the database state.  Keeps users from overwriting each other's changes.
    */
    public static void update(Task task, Task oldTask) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), task, oldTask);
            return ;
        }
         
        if (task.IsRepeating && task.DateTask.Year > 1880)
        {
            throw new Exception(Lans.g("Tasks","Task cannot be tagged repeating and also have a date."));
        }
         
        if (task.IsRepeating && task.TaskStatus != TaskStatusEnum.New)
        {
            throw new Exception(Lans.g("Tasks","Tasks that are repeating must have a status of New."));
        }
         
        //and any status but new
        if (task.IsRepeating && task.TaskListNum != 0 && task.DateType != TaskDateType.None)
        {
            throw new Exception(Lans.g("Tasks","In repeating tasks, only the main parents can have a task status."));
        }
         
        //In repeating, children not allowed to repeat.
        if (wasTaskAltered(oldTask))
        {
            throw new Exception(Lans.g("Tasks","Not allowed to save changes because the task has been altered by someone else."));
        }
         
        Crud.TaskCrud.Update(task);
        if (task.TaskListNum != oldTask.TaskListNum)
        {
            TaskAncestors.synch(task);
        }
         
    }

    /**
    * 
    */
    public static long insert(Task task) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            task.TaskNum = Meth.GetLong(MethodBase.GetCurrentMethod(), task);
            return task.TaskNum;
        }
         
        if (task.IsRepeating && task.DateTask.Year > 1880)
        {
            throw new Exception(Lans.g("Tasks","Task cannot be tagged repeating and also have a date."));
        }
         
        if (task.IsRepeating && task.TaskStatus != TaskStatusEnum.New)
        {
            throw new Exception(Lans.g("Tasks","Tasks that are repeating must have a status of New."));
        }
         
        //and any status but new
        if (task.IsRepeating && task.TaskListNum != 0 && task.DateType != TaskDateType.None)
        {
            throw new Exception(Lans.g("Tasks","In repeating tasks, only the main parents can have a task status."));
        }
         
        //In repeating, children not allowed to repeat.
        Crud.TaskCrud.Insert(task);
        TaskAncestors.synch(task);
        return task.TaskNum;
    }

    /**
    * 
    */
    public static boolean wasTaskAltered(Task task) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), task);
        }
         
        String command = "SELECT * FROM task WHERE TaskNum=" + POut.long(task.TaskNum);
        Task oldtask = Crud.TaskCrud.SelectOne(command);
        if (oldtask == null || oldtask.DateTask != task.DateTask || oldtask.DateType != task.DateType || !StringSupport.equals(oldtask.Descript, task.Descript) || oldtask.FromNum != task.FromNum || oldtask.IsRepeating != task.IsRepeating || oldtask.KeyNum != task.KeyNum || oldtask.ObjectType != task.ObjectType || oldtask.TaskListNum != task.TaskListNum || oldtask.TaskStatus != task.TaskStatus || oldtask.UserNum != task.UserNum || oldtask.DateTimeEntry != task.DateTimeEntry || oldtask.DateTimeFinished != task.DateTimeFinished)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Deleting a task never causes a problem, so no dependencies are checked.
    */
    public static void delete(long taskNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), taskNum);
            return ;
        }
         
        String command = "DELETE from task WHERE TaskNum = " + POut.long(taskNum);
        Db.nonQ(command);
        command = "DELETE from taskancestor WHERE TaskNum = " + POut.long(taskNum);
        Db.nonQ(command);
        command = "DELETE from tasknote WHERE TaskNum = " + POut.long(taskNum);
        Db.nonQ(command);
    }

    /**
    * Gets a count of unread tasks to notify user when first logging in.
    */
    public static int userTasksCount(long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), userNum);
        }
         
        String command = "SELECT task.TaskNum FROM task,taskunread " + "WHERE task.TaskNum=taskunread.TaskNum " + "AND taskunread.UserNum = " + POut.long(userNum) + " " + "GROUP BY task.TaskNum";
        //this handles duplicate taskunread entries.
        /*
        			string command="SELECT COUNT(*) FROM taskancestor,task,tasklist,tasksubscription "
        				+"WHERE taskancestor.TaskListNum=tasklist.TaskListNum "
        				+"AND task.TaskNum=taskancestor.TaskNum "
        				+"AND tasksubscription.TaskListNum=tasklist.TaskListNum "
        				+"AND tasksubscription.UserNum="+POut.Long(userNum)
        				+" AND task.TaskStatus="+POut.Long((int)TaskStatusEnum.New);*/
        DataTable table = Db.getTable(command);
        return table.Rows.Count;
    }

    //return PIn.Int(Db.GetScalar(command));//GetCount failed if no new tasks.
    /*
    		///<summary>Appends a carriage return as well as the text to any task.  If a taskListNum is specified, then it also changes the taskList.</summary>
    		public static void Append(long taskNum,string text) {
    			//No need to check RemotingRole; no call to db.
    			Append(taskNum,text,-1);
    		}
    		///<summary>Appends a carriage return as well as the text to any task.  If a taskListNum is specified, then it also changes the taskList.    Must call TaskAncestors.Synch after this.</summary>
    		public static void Append(long taskNum,string text,long taskListNum) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),taskNum,text,taskListNum);
    				return;
    			}
    			string command;
    			if(taskListNum==-1) {
    				command="UPDATE task SET Descript=CONCAT(Descript,'"+POut.String("\r\n"+text)+"') WHERE TaskNum="+POut.Long(taskNum);
    			}
    			else {
    				command="UPDATE task SET Descript=CONCAT(Descript,'"+POut.String("\r\n"+text)+"'), "
    					+"TaskListNum="+POut.Long(taskListNum)+" "
    					+"WHERE TaskNum="+POut.Long(taskNum);
    			}
    			Db.NonQ(command);
    		}*/
    public static int getCountOpenTickets(long userNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), userNum);
        }
         
        //if any ancestor is a dated list, then we don't want that task
        //this only handles tasks directly in the dated trunks
        String command = "SELECT COUNT(*) " + "FROM task " + "WHERE NOT EXISTS(SELECT * FROM taskancestor,tasklist " + "WHERE taskancestor.TaskNum=task.TaskNum " + "AND tasklist.TaskListNum=taskancestor.TaskListNum " + "AND tasklist.DateType!=0) " + "AND task.DateType=0 " + "AND task.ObjectType=" + POut.int(((Enum)TaskObjectType.Patient).ordinal()) + " " + "AND task.IsRepeating=0 " + "AND task.UserNum=" + POut.long(userNum) + " " + "AND TaskStatus != " + POut.int(((Enum)TaskStatusEnum.Done).ordinal());
        return PIn.int(Db.getCount(command));
    }

}


