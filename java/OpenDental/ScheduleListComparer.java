//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:28 PM
//

package OpenDental;

import CS2JNet.System.LCC.Disposable;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleType;

/**
* Sort a list of Schedules given a sort criteria
*/
public class ScheduleListComparer  extends IComparer<List<Schedule>> 
{
    public SortBy Sort = SortBy.StopTime;
    public int compare(List<Schedule> collectionX, List<Schedule> collectionY) throws Exception {
        //default sort by first schedule in the collection
        Schedule x = collectionX[0];
        Schedule y = collectionY[0];
        if (Sort == SortBy.StopTime)
        {
            //stop time means sort by last schedule in collection
            x = collectionX[collectionX.Count - 1];
            y = collectionY[collectionY.Count - 1];
        }
         
        int ret = 0;
        if (x.SchedType != y.SchedType)
        {
            return x.SchedType.CompareTo(y.SchedType);
        }
        else //send this orders by 1) practice notes, 2) providers 3) employees
        if ((x.SchedType == ScheduleType.Practice && y.SchedType == ScheduleType.Practice) || (x.SchedType == ScheduleType.Blockout && y.SchedType == ScheduleType.Blockout))
        {
            return x.Note.CompareTo(y.Note);
        }
        else //this is a note only so order alapha
        if (Sort == SortBy.StartTime)
        {
            ret = x.StartTime.CompareTo(y.StartTime);
            if (ret == 0)
            {
                return compareNames(x,y);
            }
             
            return ret;
        }
        else //if start times are same then default to alpha
        if (Sort == SortBy.StopTime)
        {
            ret = x.StopTime.CompareTo(y.StopTime);
            if (ret == 0)
            {
                return compareNames(x,y);
            }
             
            return ret;
        }
        else
        {
            //if stop times are same then default to alpha
            //we got this far so sort by name
            ret = compareNames(x,y);
            if (ret == 0)
            {
                return x.StartTime.CompareTo(y.StartTime);
            }
             
            return ret;
        }    
    }

    //if names are same then default to start time
    ///<summary>Name sort order differs according to ScheduleType. This sorts accordingly.<returns></returns>
    private int compareNames(Schedule x, Schedule y) throws Exception {
        if (x.ProvNum != y.ProvNum)
        {
            return Providers.getProv(x.ProvNum).ItemOrder.CompareTo(Providers.getProv(y.ProvNum).ItemOrder);
        }
         
        //we are dealing with a provider
        if (x.EmployeeNum != y.EmployeeNum)
        {
            return Employees.getEmp(x.EmployeeNum).FName.CompareTo(Employees.getEmp(y.EmployeeNum).FName);
        }
         
        return 0;
    }

    //we are dealing with an employee
    public enum SortBy
    {
        Name,
        StartTime,
        StopTime
    }
}


