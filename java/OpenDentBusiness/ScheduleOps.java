//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleOp;

/**
* 
*/
public class ScheduleOps   
{
    /**
    * 
    */
    public static long insert(ScheduleOp op) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            op.ScheduleOpNum = Meth.GetLong(MethodBase.GetCurrentMethod(), op);
            return op.ScheduleOpNum;
        }
         
        return Crud.ScheduleOpCrud.Insert(op);
    }

    /**
    * 
    */
    public static List<ScheduleOp> getForSched(long scheduleNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ScheduleOp>>GetObject(MethodBase.GetCurrentMethod(), scheduleNum);
        }
         
        String command = "SELECT * FROM scheduleop ";
        command += "WHERE schedulenum = " + scheduleNum;
        return Crud.ScheduleOpCrud.SelectMany(command);
    }

    /**
    * Gets all the ScheduleOps for the list of schedules.
    */
    public static List<ScheduleOp> getForSchedList(List<Schedule> schedules) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ScheduleOp>>GetObject(MethodBase.GetCurrentMethod(), schedules);
        }
         
        if (schedules.Count == 0)
        {
            return new List<ScheduleOp>();
        }
         
        String command = "SELECT * FROM scheduleop " + "WHERE ScheduleNum IN ( ";
        for (int i = 0;i < schedules.Count;i++)
        {
            if (i > 0)
            {
                command += ",";
            }
             
            command += schedules[i].ScheduleNum.ToString();
        }
        command += ")";
        return Crud.ScheduleOpCrud.SelectMany(command);
    }

}


