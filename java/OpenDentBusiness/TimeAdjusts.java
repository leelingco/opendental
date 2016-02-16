//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TimeAdjust;

/**
* 
*/
public class TimeAdjusts   
{
    /**
    * 
    */
    public static List<TimeAdjust> refresh(long empNum, DateTime fromDate, DateTime toDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TimeAdjust>>GetObject(MethodBase.GetCurrentMethod(), empNum, fromDate, toDate);
        }
         
        String command = "SELECT * FROM timeadjust WHERE " + "EmployeeNum = " + POut.long(empNum) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " >= " + POut.date(fromDate) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " <= " + POut.date(toDate) + " " + "ORDER BY TimeEntry";
        return Crud.TimeAdjustCrud.SelectMany(command);
    }

    /**
    * Validates and throws exceptions. Gets all time adjusts between date range and time adjusts made during the current work week.
    */
    public static List<TimeAdjust> getValidList(long empNum, DateTime fromDate, DateTime toDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TimeAdjust>>GetObject(MethodBase.GetCurrentMethod(), empNum, fromDate, toDate);
        }
         
        List<TimeAdjust> retVal = new List<TimeAdjust>();
        String command = "SELECT * FROM timeadjust WHERE " + "EmployeeNum = " + POut.long(empNum) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " >= " + POut.date(fromDate) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " <= " + POut.date(toDate) + " " + "ORDER BY TimeEntry";
        retVal = Crud.TimeAdjustCrud.SelectMany(command);
        return retVal;
    }

    //Validate---------------------------------------------------------------------------------------------------------------
    //none necessary at this time.
    /**
    * Validates and throws exceptions.  Deletes automatic adjustments that fall within the pay period.
    */
    public static List<TimeAdjust> getListForTimeCardManage(long empNum, DateTime fromDate, DateTime toDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TimeAdjust>>GetObject(MethodBase.GetCurrentMethod(), empNum, fromDate, toDate);
        }
         
        List<TimeAdjust> retVal = new List<TimeAdjust>();
        //List<TimeAdjust> listTimeAdjusts=new List<TimeAdjust>();
        String command = "SELECT * FROM timeadjust WHERE " + "EmployeeNum = " + POut.long(empNum) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " >= " + POut.date(fromDate) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " <= " + POut.date(toDate) + " " + "ORDER BY TimeEntry";
        return Crud.TimeAdjustCrud.SelectMany(command);
    }

    //listTimeAdjusts=Crud.TimeAdjustCrud.SelectMany(command);
    //Delete automatic adjustments.------------------------------------------------------------------------------------------
    //for(int i=0;i<listTimeAdjusts.Count;i++) {
    //	if(!listTimeAdjusts[i].IsAuto) {//skip and never delete manual adjustments
    //		retVal.Add(listTimeAdjusts[i]);
    //		continue;
    //	}
    //	TimeAdjusts.Delete(listTimeAdjusts[i]);//delete auto adjustments for current pay period
    //}
    //Validate---------------------------------------------------------------------------------------------------------------
    //none necessary at this time.
    //return retVal;
    /**
    * 
    */
    public static long insert(TimeAdjust timeAdjust) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            timeAdjust.TimeAdjustNum = Meth.GetLong(MethodBase.GetCurrentMethod(), timeAdjust);
            return timeAdjust.TimeAdjustNum;
        }
         
        return Crud.TimeAdjustCrud.Insert(timeAdjust);
    }

    /**
    * 
    */
    public static void update(TimeAdjust timeAdjust) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), timeAdjust);
            return ;
        }
         
        Crud.TimeAdjustCrud.Update(timeAdjust);
    }

    /**
    * 
    */
    public static void delete(TimeAdjust adj) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), adj);
            return ;
        }
         
        String command = "DELETE FROM timeadjust WHERE TimeAdjustNum = " + POut.long(adj.TimeAdjustNum);
        Db.nonQ(command);
    }

    /**
    * Returns all automatically generated timeAdjusts for a given employee between the date range (inclusive).
    */
    public static List<TimeAdjust> getSimpleListAuto(long employeeNum, DateTime startDate, DateTime stopDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TimeAdjust>>GetObject(MethodBase.GetCurrentMethod(), employeeNum, startDate, stopDate);
        }
         
        List<TimeAdjust> retVal = new List<TimeAdjust>();
        //List<TimeAdjust> listTimeAdjusts=new List<TimeAdjust>();
        String command = "SELECT * FROM timeadjust WHERE " + "EmployeeNum = " + POut.long(employeeNum) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " >= " + POut.date(startDate) + " " + "AND " + DbHelper.dateColumn("TimeEntry") + " < " + POut.Date(stopDate.AddDays(1)) + " " + "AND IsAuto=1";
        return Crud.TimeAdjustCrud.SelectMany(command);
    }

}


//add one day to go the end of the specified date.
//listTimeAdjusts=Crud.TimeAdjustCrud.SelectMany(command);