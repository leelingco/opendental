//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.DeletedObjects;
import OpenDentBusiness.DeletedObjectType;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.PIn;
import OpenDentBusiness.Plugins;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SchedStatus;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleOp;
import OpenDentBusiness.ScheduleOps;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.ScheduleType;

/**
* 
*/
public class Schedules   
{
    /**
    * Gets a list of Schedule items for one date.
    */
    public static List<Schedule> getDayList(DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Schedule>>GetObject(MethodBase.GetCurrentMethod(), date);
        }
         
        String command = "SELECT * FROM schedule " + "WHERE SchedDate = " + POut.date(date) + " " + "ORDER BY StartTime";
        return refreshAndFill(command);
    }

    /**
    * Used in the Schedules edit window to get a filtered list of schedule items in preparation for paste or repeat.
    */
    public static List<Schedule> refreshPeriod(DateTime dateStart, DateTime dateEnd, List<long> provNums, List<long> empNums, boolean includePractice) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Schedule>>GetObject(MethodBase.GetCurrentMethod(), dateStart, dateEnd, provNums, empNums, includePractice);
        }
         
        if (provNums.Count == 0 && empNums.Count == 0 && !includePractice)
        {
            return new List<Schedule>();
        }
         
        String command = "SELECT * FROM schedule " + "WHERE SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " " + "AND (";
        String orClause = "";
        //this is guaranteed to be non empty by the time the command is assembled.
        if (includePractice)
        {
            orClause = "SchedType=0 ";
        }
         
        for (int i = 0;i < provNums.Count;i++)
        {
            if (!StringSupport.equals(orClause, ""))
            {
                orClause += "OR ";
            }
             
            orClause += "schedule.ProvNum=" + POut.Long(provNums[i]) + " ";
        }
        for (int i = 0;i < empNums.Count;i++)
        {
            if (!StringSupport.equals(orClause, ""))
            {
                orClause += "OR ";
            }
             
            orClause += "schedule.EmployeeNum=" + POut.Long(empNums[i]) + " ";
        }
        command += orClause + ")";
        return refreshAndFill(command);
    }

    /**
    * 
    */
    public static List<Schedule> refreshPeriodBlockouts(DateTime dateStart, DateTime dateEnd, List<long> opNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Schedule>>GetObject(MethodBase.GetCurrentMethod(), dateStart, dateEnd, opNums);
        }
         
        if (opNums.Count == 0)
        {
            return new List<Schedule>();
        }
         
        String command = "SELECT schedule.ScheduleNum,SchedDate,StartTime,StopTime,SchedType," + "ProvNum,BlockoutType,Note,Status,EmployeeNum,DateTStamp " + "FROM schedule,scheduleop " + "WHERE schedule.ScheduleNum=scheduleop.ScheduleNum " + "AND SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " " + "AND SchedType=2 " + "AND (";
        for (int i = 0;i < opNums.Count;i++)
        {
            //blockouts
            //OperatoryNum=0 ";
            if (i > 0)
            {
                command += " OR ";
            }
             
            command += "OperatoryNum=" + POut.Long(opNums[i]);
        }
        command += ") GROUP BY schedule.ScheduleNum,SchedDate,StartTime,StopTime,SchedType," + "ProvNum,BlockoutType,Note,Status,EmployeeNum,DateTStamp";
        return refreshAndFill(command);
    }

    /**
    * 
    */
    public static List<Schedule> refreshDayEdit(DateTime dateSched) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Schedule>>GetObject(MethodBase.GetCurrentMethod(), dateSched);
        }
         
        //,provider "
        String command = "SELECT schedule.* " + "FROM schedule " + "WHERE SchedDate = " + POut.date(dateSched) + " " + "AND (SchedType=0 OR SchedType=1 OR SchedType=3)";
        return refreshAndFill(command);
    }

    //Practice or Provider or Employee
    /**
    * 
    */
    public static List<Schedule> getTwoYearPeriod(DateTime startDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Schedule>>GetObject(MethodBase.GetCurrentMethod(), startDate);
        }
         
        //,provider "
        String command = "SELECT schedule.* " + "FROM schedule " + "WHERE SchedDate BETWEEN " + POut.date(startDate) + " AND " + POut.Date(startDate.AddYears(2)) + " " + "AND (SchedType=0 OR SchedType=1 OR SchedType=3)";
        return refreshAndFill(command);
    }

    //Practice or Provider or Employee
    /**
    * Used in the check database integrity tool.
    */
    public static Schedule[] refreshAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Schedule[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM schedule";
        return refreshAndFill(command).ToArray();
    }

    public static List<Schedule> getChangedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Schedule>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * schedule WHERE DateTStamp > " + POut.dateT(changedSince) + " AND SchedType=" + POut.Long(((Enum)ScheduleType.Provider).ordinal());
        return refreshAndFill(command);
    }

    /**
    * This is only allowed because it's PRIVATE.
    */
    private static List<Schedule> refreshAndFill(String command) throws Exception {
        //Not a typical refreshandfill, as it contains a query.
        //The GROUP_CONCAT() function returns a comma separated list of items.
        //In this case, the ops column is filled with a comma separated list of
        //operatories for the corresponding schedule record.
        command = "SELECT s.*," + DbHelper.ifNull(DbHelper.castToChar("(SELECT " + DbHelper.groupConcat("so.OperatoryNum") + "FROM scheduleop so " + "WHERE so.ScheduleNum=s.ScheduleNum " + "GROUP BY so.ScheduleNum)"),"") + " ops " + "FROM (" + command + ") s";
        DataTable table = Db.getTable(command);
        return convertTableToList(table);
    }

    public static List<Schedule> convertTableToList(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Schedule> retVal = Crud.ScheduleCrud.TableToList(table);
        String opstr = new String();
        String[] oparray = new String[]();
        if (table.Columns.Contains("ops"))
        {
            for (int i = 0;i < retVal.Count;i++)
            {
                retVal[i].Ops = new List<long>();
                opstr = PIn.String(table.Rows[i]["ops"].ToString());
                if (!StringSupport.equals(opstr, ""))
                {
                    oparray = opstr.Split(',');
                    for (int o = 0;o < oparray.Length;o++)
                    {
                        retVal[i].Ops.Add(PIn.Long(oparray[o]));
                    }
                }
                 
            }
        }
         
        return retVal;
    }

    /**
    * 
    */
    public static void update(Schedule sched) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sched);
            return ;
        }
         
        validate(sched);
        Crud.ScheduleCrud.Update(sched);
        String command = "DELETE FROM scheduleop WHERE ScheduleNum=" + POut.long(sched.ScheduleNum);
        Db.nonQ(command);
        ScheduleOp op;
        for (int i = 0;i < sched.Ops.Count;i++)
        {
            op = new ScheduleOp();
            op.ScheduleNum = sched.ScheduleNum;
            op.OperatoryNum = sched.Ops[i];
            ScheduleOps.insert(op);
        }
    }

    /**
    * Set validate to true to throw an exception if start and stop times need to be validated.  If validate is set to false, then the calling code is responsible for the validation.
    */
    public static long insert(Schedule sched, boolean validate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            sched.ScheduleNum = Meth.GetLong(MethodBase.GetCurrentMethod(), sched, validate);
            return sched.ScheduleNum;
        }
         
        if (validate)
        {
            validate(sched);
        }
         
        Crud.ScheduleCrud.Insert(sched);
        ScheduleOp op;
        for (int i = 0;i < sched.Ops.Count;i++)
        {
            op = new ScheduleOp();
            op.ScheduleNum = sched.ScheduleNum;
            op.OperatoryNum = sched.Ops[i];
            ScheduleOps.insert(op);
        }
        return sched.ScheduleNum;
    }

    /**
    * 
    */
    private static void validate(Schedule sched) throws Exception {
        if (sched.StopTime > TimeSpan.FromDays(1))
        {
            throw new Exception(Lans.g("Schedule","Stop time must be later than start time."));
        }
         
        //if pasting to late afternoon, the stop time might be calculated as early the next morning.
        if (sched.StartTime > sched.StopTime)
        {
            throw new Exception(Lans.g("Schedule","Stop time must be later than start time."));
        }
         
        if (sched.StartTime + TimeSpan.FromMinutes(5) > sched.StopTime && sched.Status == SchedStatus.Open)
        {
            throw new Exception(Lans.g("Schedule","Stop time cannot be the same as the start time."));
        }
         
    }

    /**
    * Goes to the db to look for overlaps.  Implemented for blockouts, but should work for other types, too.
    */
    public static boolean overlaps(Schedule sched) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Schedule> SchedListDay = Schedules.getDayList(sched.SchedDate);
        Schedule[] ListForType = Schedules.GetForType(SchedListDay, sched.SchedType, sched.ProvNum);
        //blockouts
        boolean opsMatch = new boolean();
        for (int i = 0;i < ListForType.Length;i++)
        {
            if (ListForType[i].SchedType == ScheduleType.Blockout)
            {
                //only look in the same ops
                opsMatch = false;
                for (int s1 = 0;s1 < sched.Ops.Count;s1++)
                {
                    if (ListForType[i].Ops.Contains(sched.Ops[s1]))
                    {
                        opsMatch = true;
                        break;
                    }
                     
                }
                if (!opsMatch)
                {
                    continue;
                }
                 
            }
             
            if (sched.ScheduleNum != ListForType[i].ScheduleNum && sched.StartTime >= ListForType[i].StartTime && sched.StartTime < ListForType[i].StopTime)
            {
                return true;
            }
             
            if (sched.ScheduleNum != ListForType[i].ScheduleNum && sched.StopTime > ListForType[i].StartTime && sched.StopTime <= ListForType[i].StopTime)
            {
                return true;
            }
             
            if (sched.ScheduleNum != ListForType[i].ScheduleNum && sched.StartTime <= ListForType[i].StartTime && sched.StopTime >= ListForType[i].StopTime)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * 
    */
    public static void delete(Schedule sched) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), sched);
            return ;
        }
         
        String command = "DELETE from schedule WHERE schedulenum = '" + POut.long(sched.ScheduleNum) + "'";
        Db.nonQ(command);
        if (sched.SchedType == ScheduleType.Provider)
        {
            DeletedObjects.setDeleted(DeletedObjectType.ScheduleProv,sched.ScheduleNum);
        }
         
    }

    /**
    * Supply a list of all Schedule for one day. Then, this filters out for one type.
    */
    public static Schedule[] getForType(List<Schedule> ListDay, ScheduleType schedType, long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < ListDay.Count;i++)
        {
            if (ListDay[i].SchedType == schedType && ListDay[i].ProvNum == provNum)
            {
                AL.Add(ListDay[i]);
            }
             
        }
        Schedule[] retVal = new Schedule[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    /**
    * Supply a list of Schedule . Then, this filters out for an employee.
    */
    public static List<Schedule> getForEmployee(List<Schedule> ListDay, long employeeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Schedule> retVal = new List<Schedule>();
        for (int i = 0;i < ListDay.Count;i++)
        {
            if (ListDay[i].SchedType == ScheduleType.Employee && ListDay[i].EmployeeNum == employeeNum)
            {
                retVal.Add(ListDay[i]);
            }
             
        }
        return retVal;
    }

    /**
    * This overload is for when the listForPeriod includes multiple days.
    */
    public static List<Schedule> getSchedsForOp(List<Schedule> listForPeriod, DayOfWeek dayOfWeek, Operatory op) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Schedule> listForDay = new List<Schedule>();
        for (int i = 0;i < listForPeriod.Count;i++)
        {
            //if day of week doesn't match, then skip
            if (listForPeriod[i].SchedDate.DayOfWeek != dayOfWeek)
            {
                continue;
            }
             
            listForDay.Add(listForPeriod[i].Copy());
        }
        return GetSchedsForOp(listForDay, op);
    }

    /**
    * This overload is for when the listForPeriod includes only one day.
    */
    public static List<Schedule> getSchedsForOp(List<Schedule> listForPeriod, Operatory op) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Schedule> retVal = new List<Schedule>();
        for (int i = 0;i < listForPeriod.Count;i++)
        {
            //if a schedule is not a provider type, then skip it
            if (listForPeriod[i].SchedType != ScheduleType.Provider)
            {
                continue;
            }
             
            //if the schedule has ops set, then only apply the schedule to those ops
            if (listForPeriod[i].Ops.Count > 0)
            {
                if (listForPeriod[i].Ops.Contains(op.OperatoryNum))
                {
                    retVal.Add(listForPeriod[i].Copy());
                }
                 
            }
            else
            {
                //but if the schedule does not have ops set, then look at the op settings to determine whether to use it.
                if (op.ProvDentist != 0 && !op.IsHygiene)
                {
                    //op uses dentist
                    if (listForPeriod[i].ProvNum == op.ProvDentist)
                    {
                        retVal.Add(listForPeriod[i].Copy());
                    }
                     
                }
                else if (op.ProvHygienist != 0 && op.IsHygiene)
                {
                    //op uses hygienist
                    if (listForPeriod[i].ProvNum == op.ProvHygienist)
                    {
                        retVal.Add(listForPeriod[i].Copy());
                    }
                     
                }
                else
                {
                    //op has no provider set
                    //so use the provider that's set for unassigned ops
                    if (listForPeriod[i].ProvNum == PrefC.getLong(PrefName.ScheduleProvUnassigned))
                    {
                        retVal.Add(listForPeriod[i].Copy());
                    }
                     
                }  
            } 
        }
        return retVal;
    }

    /**
    * If no provider is found for spot then the operatory provider is returned.
    */
    public static long getAssignedProvNumForSpot(List<Schedule> listForPeriod, Operatory op, boolean isSecondary, DateTime aptDateTime) throws Exception {
        for (int i = 0;i < listForPeriod.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            //first, look for a sched assigned specifically to that spot
            if (listForPeriod[i].SchedType != ScheduleType.Provider)
            {
                continue;
            }
             
            if (aptDateTime.Date != listForPeriod[i].SchedDate)
            {
                continue;
            }
             
            if (!listForPeriod[i].Ops.Contains(op.OperatoryNum))
            {
                continue;
            }
             
            if (isSecondary && !Providers.GetIsSec(listForPeriod[i].ProvNum))
            {
                continue;
            }
             
            if (!isSecondary && Providers.GetIsSec(listForPeriod[i].ProvNum))
            {
                continue;
            }
             
            //for the time, if the sched starts later than the apt starts
            if (listForPeriod[i].StartTime > aptDateTime.TimeOfDay)
            {
                continue;
            }
             
            //or if the sched ends (before or at same time) as the apt starts
            if (listForPeriod[i].StopTime <= aptDateTime.TimeOfDay)
            {
                continue;
            }
             
            //matching sched found
            Plugins.hookAddCode(null,"Schedules.GetAssignedProvNumForSpot_Found",isSecondary);
            return listForPeriod[i].ProvNum;
        }
        //if no matching sched found, then use the operatory
        Plugins.hookAddCode(null,"Schedules.GetAssignedProvNumForSpot_None",isSecondary);
        if (isSecondary)
        {
            return op.ProvHygienist;
        }
        else
        {
            return op.ProvDentist;
        } 
    }

    //return 0;//none
    /**
    * Comma delimits multiple schedules and creates a nice clean sting for screen ledgibility
    */
    public static String getCommaDelimStringForScheds(List<Schedule> listSchedules) throws Exception {
        String retVal = "";
        for (int s = 0;s < listSchedules.Count;s++)
        {
            if (s > 0)
            {
                retVal += ",";
            }
             
            retVal += " " + listSchedules[s].StartTime.ToShortTimeString() + " - " + listSchedules[s].StopTime.ToShortTimeString();
        }
        return retVal;
    }

    /**
    * Clears all blockouts for day.
    */
    public static void clearBlockoutsForDay(DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), date);
            return ;
        }
         
        String command = "DELETE from schedule WHERE " + "SchedDate=" + POut.date(date) + " " + "AND SchedType='" + POut.Long(((Enum)ScheduleType.Blockout).ordinal()) + "' ";
        Db.nonQ(command);
    }

    public static boolean dateIsHoliday(DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), date);
        }
         
        //holiday
        //practice
        String command = "SELECT COUNT(*) FROM schedule WHERE Status=2 " + "AND SchedType=0 " + "AND SchedDate= " + POut.date(date);
        String result = Db.getCount(command);
        if (StringSupport.equals(result, "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Returns a 7 column data table in a calendar layout so all you have to do is draw it on the screen.  If includePractice is true, then practice notes and holidays will be included.
    */
    public static DataTable getPeriod(DateTime dateStart, DateTime dateEnd, List<long> provNums, List<long> empNums, boolean includePractice) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), dateStart, dateEnd, provNums, empNums, includePractice);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        table.Columns.Add("sun");
        table.Columns.Add("mon");
        table.Columns.Add("tues");
        table.Columns.Add("wed");
        table.Columns.Add("thurs");
        table.Columns.Add("fri");
        table.Columns.Add("sat");
        if (provNums.Count == 0 && empNums.Count == 0 && !includePractice)
        {
            return table;
        }
         
        String command = "SELECT Abbr,employee.FName,Note,SchedDate,SchedType,Status,StartTime,StopTime " + "FROM schedule " + "LEFT JOIN provider ON schedule.ProvNum=provider.ProvNum " + "LEFT JOIN employee ON schedule.EmployeeNum=employee.EmployeeNum " + "WHERE SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " " + "AND (";
        String orClause = "";
        //this is guaranteed to be non empty by the time the command is assembled.
        if (includePractice)
        {
            orClause = "SchedType=0 ";
        }
         
        for (int i = 0;i < provNums.Count;i++)
        {
            if (!StringSupport.equals(orClause, ""))
            {
                orClause += "OR ";
            }
             
            orClause += "schedule.ProvNum=" + POut.Long(provNums[i]) + " ";
        }
        for (int i = 0;i < empNums.Count;i++)
        {
            if (!StringSupport.equals(orClause, ""))
            {
                orClause += "OR ";
            }
             
            orClause += "schedule.EmployeeNum=" + POut.Long(empNums[i]) + " ";
        }
        command += orClause + ") ";
        //if(FormChooseDatabase.DBtype==DatabaseType.Oracle){
        //	command+="";
        //}
        //else{
        command += "ORDER BY SchedDate,employee.FName,provider.ItemOrder,StartTime";
        //}
        DataTable raw = Db.getTable(command);
        DateTime dateSched = new DateTime();
        DateTime startTime = new DateTime();
        DateTime stopTime = new DateTime();
        int rowsInGrid = getRowCal(dateStart,dateEnd) + 1;
        for (int i = 0;i < rowsInGrid;i++)
        {
            //because 0-based
            row = table.NewRow();
            table.Rows.Add(row);
        }
        dateSched = dateStart;
        while (dateSched <= dateEnd)
        {
            table.Rows[getRowCal(dateStart,dateSched)][(int)dateSched.DayOfWeek] = dateSched.ToString("MMM d, yyyy");
            dateSched = dateSched.AddDays(1);
        }
        int rowI = new int();
        for (int i = 0;i < raw.Rows.Count;i++)
        {
            dateSched = PIn.Date(raw.Rows[i]["SchedDate"].ToString());
            startTime = PIn.DateT(raw.Rows[i]["StartTime"].ToString());
            stopTime = PIn.DateT(raw.Rows[i]["StopTime"].ToString());
            rowI = getRowCal(dateStart,dateSched);
            //not first row
            //same provider as previous row
            //same employee as previous row
            if (i != 0 && raw.Rows[i - 1]["Abbr"].ToString() == raw.Rows[i]["Abbr"].ToString() && raw.Rows[i - 1]["FName"].ToString() == raw.Rows[i]["FName"].ToString() && raw.Rows[i - 1]["SchedDate"].ToString() == raw.Rows[i]["SchedDate"].ToString())
            {
                //and same date as previous row
                table.Rows[rowI][(int)dateSched.DayOfWeek] += ", ";
                if (startTime.TimeOfDay == PIn.dateT("12 AM").TimeOfDay && stopTime.TimeOfDay == PIn.dateT("12 AM").TimeOfDay)
                {
                    if (StringSupport.equals(raw.Rows[i]["Status"].ToString(), "2"))
                    {
                        //if holiday
                        table.Rows[rowI][(int)dateSched.DayOfWeek] += Lans.g("Schedules","Holiday:");
                    }
                     
                }
                else
                {
                    table.Rows[rowI][(int)dateSched.DayOfWeek] += startTime.ToString("h:mm") + "-" + stopTime.ToString("h:mm");
                } 
            }
            else
            {
                table.Rows[rowI][(int)dateSched.DayOfWeek] += "\r\n";
                if (startTime.TimeOfDay == PIn.dateT("12 AM").TimeOfDay && stopTime.TimeOfDay == PIn.dateT("12 AM").TimeOfDay)
                {
                    if (StringSupport.equals(raw.Rows[i]["Status"].ToString(), "2"))
                    {
                        //if holiday
                        table.Rows[rowI][(int)dateSched.DayOfWeek] += Lans.g("Schedules","Holiday:");
                    }
                    else
                    {
                        //+raw.Rows[i]["Note"].ToString();
                        //note
                        if (!StringSupport.equals(raw.Rows[i]["Abbr"].ToString(), ""))
                        {
                            table.Rows[rowI][(int)dateSched.DayOfWeek] += raw.Rows[i]["Abbr"].ToString() + " ";
                        }
                         
                        if (!StringSupport.equals(raw.Rows[i]["FName"].ToString(), ""))
                        {
                            table.Rows[rowI][(int)dateSched.DayOfWeek] += raw.Rows[i]["FName"].ToString() + " ";
                        }
                         
                    } 
                }
                else
                {
                    //table.Rows[rowI][(int)dateSched.DayOfWeek]+=raw.Rows[i]["Note"].ToString();
                    if (!StringSupport.equals(raw.Rows[i]["Abbr"].ToString(), ""))
                    {
                        table.Rows[rowI][(int)dateSched.DayOfWeek] += raw.Rows[i]["Abbr"].ToString() + " ";
                    }
                     
                    if (!StringSupport.equals(raw.Rows[i]["FName"].ToString(), ""))
                    {
                        table.Rows[rowI][(int)dateSched.DayOfWeek] += raw.Rows[i]["FName"].ToString() + " ";
                    }
                     
                    table.Rows[rowI][(int)dateSched.DayOfWeek] += startTime.ToString("h:mm") + "-" + stopTime.ToString("h:mm");
                } 
            } 
            if (!StringSupport.equals(raw.Rows[i]["Note"].ToString(), ""))
            {
                table.Rows[rowI][(int)dateSched.DayOfWeek] += " " + raw.Rows[i]["Note"].ToString();
            }
             
        }
        return table;
    }

    /**
    * Returns the 0-based row where endDate will fall in a calendar grid.  It is not necessary to have a function to retrieve the column, because that is simply (int)myDate.DayOfWeek
    */
    public static int getRowCal(DateTime startDate, DateTime endDate) throws Exception {
        //No need to check RemotingRole; no call to db.
        TimeSpan span = endDate - startDate;
        int dayInterval = span.Days;
        int daysFirstWeek = 7 - (int)startDate.DayOfWeek;
        //eg Monday=7-1=6.  or Sat=7-6=1.
        dayInterval = dayInterval - daysFirstWeek;
        if (dayInterval < 0)
        {
            return 0;
        }
         
        return (int)Math.Ceiling((dayInterval + 1) / 7d);
    }

    /**
    * When click on a calendar grid, this is used to calculate the date clicked on.  StartDate is the first date in the Calendar, which does not have to be Sun.
    */
    public static DateTime getDateCal(DateTime startDate, int row, int col) throws Exception {
        //No need to check RemotingRole; no call to db.
        DateTime dateFirstRow = new DateTime();
        //the first date of row 0. Typically a few days before startDate. Always a Sun.
        dateFirstRow = startDate.AddDays(-((int)startDate.DayOfWeek));
        //example: (Tues,May 9).AddDays(-2)=Sun,May 7.
        int days = row * 7 + col;
        //peculiar bug.  When days=211 (startDate=4/1/10, row=30, col=1
        //and dateFirstRow=3/28/2010 and the current computer date is 4/14/10, and OS is Win7(possibly others),
        //dateFirstRow.AddDays(days)=10/24/10 00:59:58 (off by two seconds)
        //Spent hours trying to duplicate in isolated environment, but it behaves fine outside of this program.
        //Ticks are same, but result is different.
        //Commenting out the CultureInfo changes in FormOpenDental_Load did not help.
        //Not worth further debugging, so:
        DateTime retVal = dateFirstRow.AddDays(days).AddSeconds(5);
        return retVal.Date;
    }

    /**
    * Surround with try/catch.  Deletes all existing practice, provider, and employee schedules for a day and then saves the provided list.
    */
    public static void setForDay(List<Schedule> SchedList, DateTime schedDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), SchedList, schedDate);
            return ;
        }
         
        for (int i = 0;i < SchedList.Count;i++)
        {
            if (SchedList[i].StartTime > SchedList[i].StopTime)
            {
                throw new Exception(Lans.g("Schedule","Stop time must be later than start time."));
            }
             
        }
        //make deleted entries for synch purposes:
        String command = "SELECT ScheduleNum FROM schedule WHERE SchedDate= " + POut.date(schedDate) + " " + "AND SchedType=" + POut.Long(((Enum)ScheduleType.Provider).ordinal());
        DataTable table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            DeletedObjects.SetDeleted(DeletedObjectType.ScheduleProv, PIn.Long(table.Rows[i][0].ToString()));
        }
        //Then, bulk delete.
        command = "DELETE FROM schedule WHERE SchedDate= " + POut.date(schedDate) + " " + "AND (SchedType=0 OR SchedType=1 OR SchedType=3)";
        Db.nonQ(command);
        for (int i = 0;i < SchedList.Count;i++)
        {
            Insert(SchedList[i], false);
        }
    }

    /**
    * Clears all schedule entries for the given date range and the given providers, employees, and practice.
    */
    public static void clear(DateTime dateStart, DateTime dateEnd, List<long> provNums, List<long> empNums, boolean includePractice) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dateStart, dateEnd, provNums, empNums, includePractice);
            return ;
        }
         
        if (provNums.Count == 0 && empNums.Count == 0 && !includePractice)
        {
            return ;
        }
         
        String command = new String();
        String orClause = "";
        //make deleted entries for synch purposes:
        if (provNums.Count > 0)
        {
            for (int i = 0;i < provNums.Count;i++)
            {
                if (!StringSupport.equals(orClause, ""))
                {
                    orClause += "OR ";
                }
                 
                orClause += "schedule.ProvNum=" + POut.Long(provNums[i]) + " ";
            }
            command = "SELECT ScheduleNum FROM schedule " + "WHERE SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " " + "AND SchedType=" + POut.Long(((Enum)ScheduleType.Provider).ordinal()) + " AND (" + orClause + ")";
            DataTable table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                DeletedObjects.SetDeleted(DeletedObjectType.ScheduleProv, PIn.Long(table.Rows[i][0].ToString()));
            }
        }
         
        //Then, the usual deletion for everything
        command = "DELETE FROM schedule " + "WHERE SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " " + "AND (";
        orClause = "";
        //this is guaranteed to be non empty by the time the command is assembled.
        if (includePractice)
        {
            orClause = "SchedType=0 ";
        }
         
        for (int i = 0;i < provNums.Count;i++)
        {
            if (!StringSupport.equals(orClause, ""))
            {
                orClause += "OR ";
            }
             
            orClause += "schedule.ProvNum=" + POut.Long(provNums[i]) + " ";
        }
        for (int i = 0;i < empNums.Count;i++)
        {
            if (!StringSupport.equals(orClause, ""))
            {
                orClause += "OR ";
            }
             
            orClause += "schedule.EmployeeNum=" + POut.Long(empNums[i]) + " ";
        }
        command += orClause + ")";
        Db.nonQ(command);
    }

    /**
    * Clears all Blockout schedule entries for the given date range and the given ops.
    */
    public static void clearBlockouts(DateTime dateStart, DateTime dateEnd, List<long> opNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dateStart, dateEnd, opNums);
            return ;
        }
         
        String command = "SELECT * FROM schedule WHERE " + "SchedDate >= " + POut.date(dateStart) + " " + "AND SchedDate <= " + POut.date(dateEnd) + " " + "AND SchedType=2";
        //blockouts
        List<Schedule> listSched = refreshAndFill(command);
        for (int i = 0;i < listSched.Count;i++)
        {
            for (int o = 0;o < opNums.Count;o++)
            {
                //First, remove all the given ScheduleOps.
                if (listSched[i].Ops.Contains(opNums[o]))
                {
                    command = "DELETE FROM scheduleop " + "WHERE ScheduleNum=" + POut.Long(listSched[i].ScheduleNum) + " " + "AND OperatoryNum=" + POut.Long(opNums[o]);
                    Db.nonQ(command);
                    listSched[i].Ops.Remove(opNums[o]);
                }
                 
            }
        }
        for (int i = 0;i < listSched.Count;i++)
        {
            //Then, delete any blockouts that no longer have any opnums.
            if (listSched[i].Ops.Count > 0)
            {
                continue;
            }
             
            command = "DELETE FROM schedule WHERE ScheduleNum=" + POut.Long(listSched[i].ScheduleNum);
            Db.nonQ(command);
        }
    }

    public static int getDuplicateBlockoutCount() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        //MAX on id as to not change query behavior from prior to MySQL/Oracle independence.
        String command = "SELECT COUNT(*) countDups,SchedDate,MAX(schedule.ScheduleNum) ScheduleNum," + "(SELECT " + DbHelper.groupConcat("so1.OperatoryNum",false,true) + " FROM scheduleop so1 WHERE so1.ScheduleNum=schedule.ScheduleNum) AS ops\t\t\t\t\r\n" + 
        "\t\t\t\tFROM schedule\r\n" + 
        "\t\t\t\tWHERE SchedType=2\r\n" + 
        "\t\t\t\tGROUP BY SchedDate,ops,StartTime,StopTime\r\n" + 
        "\t\t\t\tHAVING countDups > 1";
        DataTable table = Db.getTable(command);
        int retVal = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal += PIn.Int(table.Rows[i][0].ToString()) - 1;
        }
        return retVal;
    }

    /**
    * 
    */
    public static void clearDuplicates() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        //First, create a temp table with operatories for all blockouts
        String command = "DROP TABLE IF EXISTS tempBlockoutOps";
        Db.nonQ(command);
        command = "CREATE TABLE tempBlockoutOps\r\n" + 
        "\t\t\t\tSELECT ScheduleNum,\r\n" + 
        "\t\t\t\t(SELECT " + DbHelper.groupConcat("so1.OperatoryNum",false,true) + " FROM scheduleop so1 WHERE so1.ScheduleNum=schedule.ScheduleNum) AS ops\r\n" + 
        "\t\t\t\tFROM schedule\r\n" + 
        "\t\t\t\tWHERE SchedType=2\r\n" + 
        "\t\t\t\tGROUP BY ScheduleNum";
        Db.nonQ(command);
        command = "ALTER TABLE tempBlockoutOps ADD INDEX (ScheduleNum)";
        Db.nonQ(command);
        //Get a list of scheduleNums that have duplicates
        //A duplicate is defined as a matching opsList and matching times
        //The result list will contain one random scheduleNum out of the many duplicates
        command = "SELECT SchedDate,MAX(ScheduleNum),StartTime,StopTime," + "(SELECT ops FROM tempBlockoutOps WHERE tempBlockoutOps.ScheduleNum=schedule.ScheduleNum) ops_______________ops,\r\n" + 
        "\t\t\t\tCOUNT(*) countDups\r\n" + 
        "\t\t\t\tFROM schedule\r\n" + 
        "\t\t\t\tWHERE SchedType=2\r\n" + 
        "\t\t\t\tGROUP BY SchedDate,ops_______________ops,StartTime,StopTime\r\n" + 
        "\t\t\t\tHAVING countDups > 1";
        //MAX on id. Cannot GROUP BY id without splitting up duplicates.
        DataTable table = Db.getTable(command);
        DateTime schedDate = new DateTime();
        DateTime startTime = new DateTime();
        DateTime stopTime = new DateTime();
        String ops = new String();
        long scheduleNum = new long();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            schedDate = PIn.Date(table.Rows[i][0].ToString());
            scheduleNum = PIn.Long(table.Rows[i][1].ToString());
            startTime = PIn.DateT(table.Rows[i][2].ToString());
            stopTime = PIn.DateT(table.Rows[i][3].ToString());
            ops = PIn.byteArray(table.Rows[i][4]);
            command = "DELETE FROM schedule WHERE " + "SchedDate = " + POut.date(schedDate) + " " + "AND ScheduleNum != " + POut.long(scheduleNum) + " " + "AND StartTime = " + POut.Time(startTime.TimeOfDay) + " " + "AND StopTime = " + POut.Time(stopTime.TimeOfDay) + " " + "AND (SELECT ops FROM tempBlockoutOps WHERE tempBlockoutOps.ScheduleNum=schedule.ScheduleNum) = '" + POut.string(ops) + "' ";
            Db.nonQ(command);
        }
        command = "DROP TABLE IF EXISTS tempBlockoutOps";
        Db.nonQ(command);
        //clear all the orphaned scheduleops
        command = "DELETE FROM scheduleop WHERE NOT EXISTS(SELECT * FROM schedule WHERE scheduleop.ScheduleNum=schedule.ScheduleNum)";
        long result = Db.nonQ(command);
    }

}


//we can test the result in debug