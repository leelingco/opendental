//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.SchedStatus;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleType;
import OpenDentBusiness.TableBase;

/**
* One block of time.  Either for practice, provider, or blockout.
*/
public class Schedule  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ScheduleNum = new long();
    /**
    * Date for this timeblock.
    */
    public DateTime SchedDate = new DateTime();
    /**
    * Start time for this timeblock.
    */
    public TimeSpan StartTime = new TimeSpan();
    /**
    * Stop time for this timeblock.
    */
    public TimeSpan StopTime = new TimeSpan();
    /**
    * Enum:ScheduleType 0=Practice,1=Provider,2=Blockout,3=Employee.  Practice is used as a way to indicate holidays and as a way to put a note in for the entire practice for one day.  But whenever type is Practice, times will be ignored.
    */
    public ScheduleType SchedType = ScheduleType.Practice;
    /**
    * FK to provider.ProvNum if a provider type.
    */
    public long ProvNum = new long();
    /**
    * FK to definition.DefNum if blockout.  eg. HighProduction, RCT Only, Emerg.
    */
    public long BlockoutType = new long();
    /**
    * This contains various types of text entered by the user.
    */
    public String Note = new String();
    /**
    * Enum:SchedStatus enumeration 0=Open,1=Closed,2=Holiday.  All blocks have a status of Open, but user doesn't see the status.  The "closed" status was previously used to override the defaults when the last timeblock was deleted.  But it's nearly phased out now.  Still used by blockouts.  Holidays are a special type of practice schedule item which do not have providers attached.
    */
    public SchedStatus Status = SchedStatus.Open;
    /**
    * FK to employee.EmployeeNum.
    */
    public long EmployeeNum = new long();
    /**
    * Last datetime that this row was inserted or updated.
    */
    public DateTime DateTStamp = new DateTime();
    /**
    * Not a db column.  Holds a list of ops that this schedule is assigned to.
    */
    public List<long> Ops = new List<long>();
    /**
    * Used only for serialization purposes
    */
    public long getStartTimeXml() throws Exception {
        return StartTime.Ticks;
    }

    public void setStartTimeXml(long value) throws Exception {
        StartTime = TimeSpan.FromTicks(value);
    }

    /**
    * Used only for serialization purposes
    */
    public long getStopTimeXml() throws Exception {
        return StopTime.Ticks;
    }

    public void setStopTimeXml(long value) throws Exception {
        StopTime = TimeSpan.FromTicks(value);
    }

    public Schedule copy() throws Exception {
        Schedule retVal = (Schedule)this.MemberwiseClone();
        retVal.Ops = new List<long>(Ops);
        return retVal;
    }

    public Schedule() throws Exception {
        Ops = new List<long>();
    }

}


/*
		public Schedule(long scheduleNum,DateTime schedDate,TimeSpan startTime,TimeSpan stopTime,ScheduleType schedType,
			long provNum,long blockoutType,string note,SchedStatus status,long employeeNum)
		{
			ScheduleNum=scheduleNum;
			SchedDate=schedDate;
			StartTime=startTime;
			StopTime=stopTime;
			SchedType=schedType;
			ProvNum=provNum;
			BlockoutType=blockoutType;
			Note=note;
			Status=status;
			EmployeeNum=employeeNum;
		}*/