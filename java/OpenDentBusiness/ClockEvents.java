//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.ClockStatusEnum;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MiscData;
import OpenDentBusiness.Phone;
import OpenDentBusiness.PhoneEmpDefault;
import OpenDentBusiness.PhoneEmpDefaults;
import OpenDentBusiness.Phones;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TimeAdjust;
import OpenDentBusiness.TimeAdjusts;
import OpenDentBusiness.TimeClockStatus;

/**
* 
*/
public class ClockEvents   
{
    /**
    * 
    */
    public static List<ClockEvent> refresh(long empNum, DateTime fromDate, DateTime toDate, boolean isBreaks) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClockEvent>>GetObject(MethodBase.GetCurrentMethod(), empNum, fromDate, toDate, isBreaks);
        }
         
        //adding a day takes it to midnight of the specified toDate
        String command = "SELECT * FROM clockevent WHERE" + " EmployeeNum = '" + POut.long(empNum) + "'" + " AND TimeDisplayed1 >= " + POut.date(fromDate) + " AND TimeDisplayed1 < " + POut.Date(toDate.AddDays(1));
        if (isBreaks)
        {
            command += " AND ClockStatus = '2'";
        }
        else
        {
            command += " AND (ClockStatus = '0' OR ClockStatus = '1')";
        } 
        command += " ORDER BY TimeDisplayed1";
        return Crud.ClockEventCrud.SelectMany(command);
    }

    /**
    * Validates list and throws exceptions.  Returns a list of clock events within the date range for employee.
    */
    public static List<ClockEvent> getValidList(long empNum, DateTime fromDate, DateTime toDate, boolean isBreaks) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClockEvent>>GetObject(MethodBase.GetCurrentMethod(), empNum, fromDate, toDate, isBreaks);
        }
         
        List<ClockEvent> retVal = new List<ClockEvent>();
        String errors = "";
        //Fill list-----------------------------------------------------------------------------------------------------------------------------
        //adding a day takes it to midnight of the specified toDate
        String command = "SELECT * FROM clockevent WHERE" + " EmployeeNum = '" + POut.long(empNum) + "'" + " AND TimeDisplayed1 >= " + POut.date(fromDate) + " AND TimeDisplayed1 < " + POut.Date(toDate.AddDays(1));
        if (isBreaks)
        {
            command += " AND ClockStatus = '2'";
        }
        else
        {
            command += " AND (ClockStatus = '0' OR ClockStatus = '1')";
        } 
        command += " ORDER BY TimeDisplayed1";
        retVal = Crud.ClockEventCrud.SelectMany(command);
        for (int i = 0;i < retVal.Count;i++)
        {
            //Validate Pay Period------------------------------------------------------------------------------------------------------------------
            if (retVal[i].TimeDisplayed2.Year < 1880)
            {
                errors += "  " + retVal[i].TimeDisplayed1.ToShortDateString() + " : the employee did not clock " + (isBreaks ? "in from break" : "out") + ".\r\n";
            }
            else if (retVal[i].TimeDisplayed1.Date != retVal[i].TimeDisplayed2.Date)
            {
                errors += "  " + retVal[i].TimeDisplayed1.ToShortDateString() + " : " + (isBreaks ? "break" : "entry") + " spans multiple days.\r\n";
            }
              
        }
        if (!StringSupport.equals(errors, ""))
        {
            throw new Exception((isBreaks ? "Break" : "Clock") + " event errors :\r\n" + errors);
        }
         
        return retVal;
    }

    /**
    * Validates list and throws exceptions.  Returns a list of clock events (not breaks) within the date range for employee. No option for breaks because this is just used in summing for time card report; use GetValidList instead.
    */
    public static List<ClockEvent> getListForTimeCardManage(long empNum, DateTime fromDate, DateTime toDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClockEvent>>GetObject(MethodBase.GetCurrentMethod(), empNum, fromDate, toDate);
        }
         
        List<ClockEvent> retVal = new List<ClockEvent>();
        String errors = "";
        //Fill list-----------------------------------------------------------------------------------------------------------------------------
        String command = "SELECT * FROM clockevent WHERE" + " EmployeeNum = '" + POut.long(empNum) + "'" + " AND TimeDisplayed1 >= " + POut.date(fromDate) + " AND TimeDisplayed1 < " + POut.Date(toDate.AddDays(1)) + " AND (ClockStatus = 0 OR ClockStatus = 1)" + " ORDER BY TimeDisplayed1";
        //adding a day takes it to midnight of the specified toDate
        retVal = Crud.ClockEventCrud.SelectMany(command);
        for (int i = 0;i < retVal.Count;i++)
        {
            //Validate Pay Period------------------------------------------------------------------------------------------------------------------
            if (retVal[i].TimeDisplayed2.Year < 1880)
            {
                errors += "  " + retVal[i].TimeDisplayed1.ToShortDateString() + " : the employee did not clock out.\r\n";
            }
            else if (retVal[i].TimeDisplayed1.Date != retVal[i].TimeDisplayed2.Date)
            {
                errors += "  " + retVal[i].TimeDisplayed1.ToShortDateString() + " : entry spans multiple days.\r\n";
            }
              
        }
        if (!StringSupport.equals(errors, ""))
        {
            throw new Exception("Clock event errors :\r\n" + errors);
        }
         
        return retVal;
    }

    /**
    * Gets one ClockEvent from the db.
    */
    public static ClockEvent getOne(long clockEventNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClockEvent>GetObject(MethodBase.GetCurrentMethod(), clockEventNum);
        }
         
        return Crud.ClockEventCrud.SelectOne(clockEventNum);
    }

    /**
    * 
    */
    public static long insert(ClockEvent clockEvent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            clockEvent.ClockEventNum = Meth.GetLong(MethodBase.GetCurrentMethod(), clockEvent);
            return clockEvent.ClockEventNum;
        }
         
        return Crud.ClockEventCrud.Insert(clockEvent);
    }

    /**
    * 
    */
    public static void update(ClockEvent clockEvent) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clockEvent);
            return ;
        }
         
        Crud.ClockEventCrud.Update(clockEvent);
    }

    /**
    * 
    */
    public static void delete(long clockEventNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), clockEventNum);
            return ;
        }
         
        String command = "DELETE FROM clockevent WHERE ClockEventNum = " + POut.long(clockEventNum);
        Db.nonQ(command);
    }

    /**
    * Gets directly from the database.  If the last event is a completed break, then it instead grabs the half-finished clock in.  Other possibilities include half-finished clock in which truly was the last event, a finished clock in/out, a half-finished clock out for break, or null for a new employee.
    */
    public static ClockEvent getLastEvent(long employeeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClockEvent>GetObject(MethodBase.GetCurrentMethod(), employeeNum);
        }
         
        String command = "SELECT * FROM clockevent WHERE EmployeeNum=" + POut.long(employeeNum) + " ORDER BY TimeDisplayed1 DESC";
        command = DbHelper.limitOrderBy(command,1);
        ClockEvent ce = Crud.ClockEventCrud.SelectOne(command);
        if (ce != null && ce.ClockStatus == TimeClockStatus.Break && ce.TimeDisplayed2.Year > 1880)
        {
            command = "SELECT * FROM clockevent WHERE EmployeeNum=" + POut.long(employeeNum) + " " + "AND ClockStatus != 2 " + "ORDER BY TimeDisplayed1 DESC";
            //not a break
            command = DbHelper.limitOrderBy(command,1);
            ce = Crud.ClockEventCrud.SelectOne(command);
            return ce;
        }
        else
        {
            return ce;
        } 
    }

    /**
    * 
    */
    public static boolean isClockedIn(long employeeNum) throws Exception {
        ClockEvent clockEvent = ClockEvents.getLastEvent(employeeNum);
        if (clockEvent == null)
        {
            return false;
        }
        else //new employee
        if (clockEvent.ClockStatus == TimeClockStatus.Break)
        {
            return false;
        }
        else
        {
            //only incomplete breaks will have been returned.
            //so currently on break
            //normal clock in/out row found
            if (clockEvent.TimeDisplayed2.Year < 1880)
            {
                return true;
            }
            else
            {
                return false;
            } 
        }  
    }

    //already clocked in
    //clocked out for home or lunch.
    /**
    * Will throw an exception if already clocked in.
    */
    public static void clockIn(long employeeNum) throws Exception {
        //we'll get this again, because it may have been a while and may be out of date
        ClockEvent clockEvent = ClockEvents.getLastEvent(employeeNum);
        if (clockEvent == null)
        {
            //new employee clocking in
            clockEvent = new ClockEvent();
            clockEvent.EmployeeNum = employeeNum;
            clockEvent.ClockStatus = TimeClockStatus.Home;
            ClockEvents.insert(clockEvent);
        }
        else //times handled
        if (clockEvent.ClockStatus == TimeClockStatus.Break)
        {
            //only incomplete breaks will have been returned.
            //clocking back in from break
            clockEvent.TimeEntered2 = MiscData.getNowDateTime();
            clockEvent.TimeDisplayed2 = clockEvent.TimeEntered2;
            ClockEvents.update(clockEvent);
        }
        else
        {
            //normal clock in/out
            if (clockEvent.TimeDisplayed2.Year < 1880)
            {
                throw new Exception(Lans.g("ClockEvents","Error.  Already clocked in."));
            }
            else
            {
                //already clocked in
                //clocked out for home or lunch.  Need to clock back in by starting a new row.
                TimeClockStatus tcs = clockEvent.ClockStatus;
                clockEvent = new ClockEvent();
                clockEvent.EmployeeNum = employeeNum;
                clockEvent.ClockStatus = tcs;
                ClockEvents.insert(clockEvent);
            } 
        }  
    }

    //times handled
    /**
    * Will throw an exception if already clocked out.
    */
    public static void clockOut(long employeeNum, TimeClockStatus clockStatus) throws Exception {
        ClockEvent clockEvent = ClockEvents.getLastEvent(employeeNum);
        if (clockEvent == null)
        {
            throw new Exception(Lans.g("ClockEvents","Error.  New employee never clocked in."));
        }
        else //new employee never clocked in
        if (clockEvent.ClockStatus == TimeClockStatus.Break)
        {
            throw new Exception(Lans.g("ClockEvents","Error.  Already clocked out for break."));
        }
          
        //only incomplete breaks will have been returned.
        //normal clock in/out
        if (clockEvent.TimeDisplayed2.Year > 1880)
        {
            throw new Exception(Lans.g("ClockEvents","Error.  Already clocked out."));
        }
         
        //clocked out for home or lunch.
        //clocked in.
        if (clockStatus == TimeClockStatus.Break)
        {
            //clocking out on break
            //leave the half-finished event alone and start a new one
            clockEvent = new ClockEvent();
            clockEvent.EmployeeNum = employeeNum;
            clockEvent.ClockStatus = TimeClockStatus.Break;
            ClockEvents.insert(clockEvent);
        }
        else
        {
            //times handled
            //finish the existing event
            clockEvent.TimeEntered2 = MiscData.getNowDateTime();
            clockEvent.TimeDisplayed2 = clockEvent.TimeEntered2;
            clockEvent.ClockStatus = clockStatus;
            //whatever the user selected
            ClockEvents.update(clockEvent);
            if (PrefC.getBool(PrefName.DockPhonePanelShow) && clockEvent.ClockStatus == TimeClockStatus.Home)
            {
                //only applies to HQ
                clockOutForHQ(employeeNum);
            }
             
        } 
    }

    /**
    * Special logic needs to be run for the phone system when users clock out.
    */
    private static void clockOutForHQ(long employeeNum) throws Exception {
        //The name showing for this extension might change to a different user.
        //It would only need to change if the employee clocking out is not assigned to the current extension. (assigned ext set in the employee table)
        //Get the information corresponding to the employee clocking out.
        PhoneEmpDefault pedClockingOut = PhoneEmpDefaults.getOne(employeeNum);
        if (pedClockingOut == null)
        {
            return ;
        }
         
        //This should never happen.
        //Get the employee that is normally assigned to this extension (assigned ext set in the employee table).
        long permanentLinkageEmployeeNum = Employees.getEmpNumAtExtension(pedClockingOut.PhoneExt);
        if (permanentLinkageEmployeeNum >= 1)
        {
            //Extension is nomrally assigned to an employee.
            if (employeeNum != permanentLinkageEmployeeNum)
            {
                //This is not the normally linked employee so let's revert back to the proper employee.
                PhoneEmpDefault pedRevertTo = PhoneEmpDefaults.getOne(permanentLinkageEmployeeNum);
                //Make sure the employee we are about to revert is not logged in at yet a different workstation. This would be rare but it's worth checking.
                if (pedRevertTo != null && !ClockEvents.isClockedIn(pedRevertTo.EmployeeNum))
                {
                    //Revert to the permanent extension for this PhoneEmpDefault.
                    pedRevertTo.PhoneExt = pedClockingOut.PhoneExt;
                    PhoneEmpDefaults.update(pedRevertTo);
                    //Update phone table to match this change.
                    Phones.setPhoneStatus(ClockStatusEnum.Home,pedRevertTo.PhoneExt,pedRevertTo.EmployeeNum);
                }
                 
            }
             
        }
         
        //Now let's switch this employee back to his normal extension.
        Employee employeeClockingOut = Employees.getEmp(employeeNum);
        if (employeeClockingOut == null)
        {
            return ;
        }
         
        //should not get here
        if (employeeClockingOut.PhoneExt != pedClockingOut.PhoneExt)
        {
            //Revert PhoneEmpDefault and Phone to the normally assigned extension for this employee.
            //Start by setting this employee back to their normally assigned extension.
            pedClockingOut.PhoneExt = employeeClockingOut.PhoneExt;
            //Now check to see if we are about to steal yet a third employee's extension.
            Phone phoneCurrentlyOccupiedBy = Phones.getPhoneForExtension(Phones.getPhoneList(),employeeClockingOut.PhoneExt);
            //There is yet a third employee who is currently occupying this extension.
            if (phoneCurrentlyOccupiedBy != null && ClockEvents.isClockedIn(phoneCurrentlyOccupiedBy.EmployeeNum))
            {
                //The third employee is clocked in so set our employee extension to 0.
                //The currently clocked in employee will retain the extension for now.
                //Our employee will retain the proper extension next time they clock in.
                pedClockingOut.PhoneExt = 0;
                //Update the phone table accordingly.
                Phones.updatePhoneToEmpty(pedClockingOut.EmployeeNum,-1);
            }
             
            PhoneEmpDefaults.update(pedClockingOut);
        }
         
        //Update phone table to match this change.
        Phones.setPhoneStatus(ClockStatusEnum.Home,pedClockingOut.PhoneExt,employeeClockingOut.EmployeeNum);
    }

    /**
    * Used in the timecard to track hours worked per week when the week started in a previous time period.  This gets all the hours of the first week before the date listed.  Also adds in any adjustments for that week.
    */
    public static TimeSpan getWeekTotal(long empNum, DateTime date) throws Exception {
        //No need to check RemotingRole; no call to db.
        TimeSpan retVal = new TimeSpan(0);
        //If the first day of the pay period is the starting date for the overtime, then there is no need to retrieve any times from the previous pay period.
        if (date.DayOfWeek == (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek))
        {
            return retVal;
        }
         
        List<ClockEvent> events = Refresh(empNum, date.AddDays(-6), date.AddDays(-1), false);
        for (int i = 0;i < events.Count;i++)
        {
            //eg, if this is Thursday, then we are getting last Friday through this Wed.
            if (events[i].TimeDisplayed1.DayOfWeek > date.DayOfWeek)
            {
                continue;
            }
             
            //eg, Friday > Thursday, so ignore
            retVal += events[i].TimeDisplayed2 - events[i].TimeDisplayed1;
            if (events[i].AdjustIsOverridden)
            {
                retVal += events[i].Adjust;
            }
            else
            {
                retVal += events[i].AdjustAuto;
            } 
            //typically zero
            //ot
            if (events[i].OTimeHours != TimeSpan.FromHours(-1))
            {
                //overridden
                retVal -= events[i].OTimeHours;
            }
            else
            {
                retVal -= events[i].OTimeAuto;
            } 
        }
        //typically zero
        //now, adjustments
        List<TimeAdjust> TimeAdjustList = TimeAdjusts.Refresh(empNum, date.AddDays(-6), date.AddDays(-1));
        for (int i = 0;i < TimeAdjustList.Count;i++)
        {
            if (TimeAdjustList[i].TimeEntry.DayOfWeek > date.DayOfWeek)
            {
                continue;
            }
             
            //eg, Friday > Thursday, so ignore
            retVal += TimeAdjustList[i].RegHours;
        }
        return retVal;
    }

    /**
    * -hh:mm or -hh.mm, depending on the pref.TimeCardsUseDecimalInsteadOfColon.  Blank if zero.
    */
    public static String format(TimeSpan span) throws Exception {
        if (PrefC.getBool(PrefName.TimeCardsUseDecimalInsteadOfColon))
        {
            if (span == TimeSpan.Zero)
            {
                return "";
            }
             
            return span.TotalHours.ToString("n");
        }
        else
        {
            return span.ToStringHmm();
        } 
    }

    //blank if zero
    /**
    * Returns clockevent information for all non-hidden employees.  Used only in the time card manage window.
    *  @param IsPrintReport Only applicable to ODHQ. If true, will add ADP pay numer and note. The query takes about 9 seconds if this is set top true vs. about 2 seconds if set to false.
    */
    public static DataTable getTimeCardManage(DateTime startDate, DateTime stopDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DataTable>GetObject(MethodBase.GetCurrentMethod(), startDate, stopDate);
        }
         
        //Construct empty table------------------------------------------------------------------------------------------------------------------------
        DataTable retVal = new DataTable();
        retVal.Columns.Add("PayrollID");
        retVal.Columns.Add("EmployeeNum");
        retVal.Columns.Add("firstName");
        retVal.Columns.Add("lastName");
        retVal.Columns.Add("totalHours");
        //should be sum of RegularHours and OvertimeHours
        retVal.Columns.Add("rate1Hours");
        retVal.Columns.Add("rate1OTHours");
        retVal.Columns.Add("rate2Hours");
        retVal.Columns.Add("rate2OTHours");
        //retVal.Columns.Add("ClockEventAdjustTotal");
        //retVal.Columns.Add("TimeAdjust");//adjustments to regular time from timeAdjust entries. Not a net zero effect on total hours worked.
        //retVal.Columns.Add("TimeAdjustOT");//OT adjustments from timeAdjust entries. OT time adjust has net zero effect. Non-OT time adjust alters total hours worked.
        //retVal.Columns.Add("PaidBreakTime");//paid breaks. Is adjusted by pressing calcDaily.
        retVal.Columns.Add("Note");
        //Loop through employees.  Each employee adds one row to table --------------------------------------------------------------------------------
        List<ClockEvent> listClockEventsAllPlusPevWeek = new List<ClockEvent>();
        List<ClockEvent> listClockEventsBreakAllPlusPrevWeek = new List<ClockEvent>();
        List<TimeAdjust> listTimeAdjustAllPlusPrevWeek = new List<TimeAdjust>();
        List<Employee> listEmployees = Employees.getForTimeCard();
        for (int e = 0;e < listEmployees.Count;e++)
        {
            String employeeErrors = "";
            String note = "";
            DataRow dataRowCur = retVal.NewRow();
            dataRowCur.ItemArray.Initialize();
            //changes all nulls to blanks and zeros.
            //PayrollID-------------------------------------------------------------------------------------------------------------------------------------
            dataRowCur["PayrollID"] = listEmployees[e].PayrollID;
            //EmployeeNum and Name----------------------------------------------------------------------------------------------------------------------------------
            dataRowCur["EmployeeNum"] = listEmployees[e].EmployeeNum;
            dataRowCur["firstName"] = listEmployees[e].FName;
            dataRowCur["lastName"] = listEmployees[e].LName;
            //Begin calculations------------------------------------------------------------------------------------------------------------------------------------
            //each list below will contain one entry per week.
            List<TimeSpan> listTsRegularHoursWeekly = new List<TimeSpan>();
            //Total non-overtime hours.  Used for calculation, not displayed or part of dataTable.
            List<TimeSpan> listTsOTHoursWeekly = new List<TimeSpan>();
            //Total overtime hours.  Used for calculation, not displayed or part of dataTable.
            List<TimeSpan> listTsDifferentialHoursWeekly = new List<TimeSpan>();
            //Not included in total hours worked.  tsDifferentialHours is differant than r2Hours and r2OTHours
            //TimeSpan tsClockEventNetAdjust    =new TimeSpan();
            //TimeSpan tsTimeAdjust             =new TimeSpan();
            //TimeSpan tsTimeAdjustOT           =new TimeSpan();
            //TimeSpan tsPaidBreakTime          =new TimeSpan();
            List<ClockEvent> listClockEvents = new List<ClockEvent>();
            List<TimeAdjust> listTimeAdjusts = new List<TimeAdjust>();
            try
            {
                listClockEvents = ClockEvents.GetListForTimeCardManage(listEmployees[e].EmployeeNum, startDate, stopDate);
            }
            catch (Exception ex)
            {
                employeeErrors += ex.Message;
            }

            try
            {
                listTimeAdjusts = TimeAdjusts.GetListForTimeCardManage(listEmployees[e].EmployeeNum, startDate, stopDate);
            }
            catch (Exception ex)
            {
                employeeErrors += ex.Message;
            }

            //report errors in note column and move to next employee.----------------------------------------------------------------
            if (!StringSupport.equals(employeeErrors, ""))
            {
                dataRowCur["Note"] = employeeErrors.Trim();
                retVal.Rows.Add(dataRowCur);
                continue;
            }
             
            //display employee errors in note field for employee. All columns will be blank for just this employee.
            //sum values for each week----------------------------------------------------------------------------------------------------
            List<DateTime> weekStartDates = weekStartHelper(startDate,stopDate);
            for (int i = 0;i < weekStartDates.Count;i++)
            {
                listTsRegularHoursWeekly.Add(TimeSpan.Zero);
                listTsOTHoursWeekly.Add(TimeSpan.Zero);
                listTsDifferentialHoursWeekly.Add(TimeSpan.Zero);
            }
            int weekCur = 0;
            for (int i = 0;i < listClockEvents.Count;i++)
            {
                for (int j = 0;j < weekStartDates.Count;j++)
                {
                    //set current week for clock event
                    if (listClockEvents[i].TimeDisplayed1 < weekStartDates[j].AddDays(7))
                    {
                        weekCur = j;
                        break;
                    }
                     
                }
                //clock event occurs during the week "j"
                if (i == 0)
                {
                    //we only want the comment from the first clock event entry.
                    note = listClockEvents[i].Note;
                }
                 
                //TimeDisplayed-----
                listTsRegularHoursWeekly[weekCur] += listClockEvents[i].TimeDisplayed2 - listClockEvents[i].TimeDisplayed1;
                //Adjusts-----
                if (listClockEvents[i].AdjustIsOverridden)
                {
                    listTsRegularHoursWeekly[weekCur] += listClockEvents[i].Adjust;
                }
                else
                {
                    listTsRegularHoursWeekly[weekCur] += listClockEvents[i].AdjustAuto;
                } 
                //OverTime-----
                if (listClockEvents[i].OTimeHours != TimeSpan.FromHours(-1))
                {
                    //Manual override
                    listTsOTHoursWeekly[weekCur] += listClockEvents[i].OTimeHours;
                    listTsRegularHoursWeekly[weekCur] += -listClockEvents[i].OTimeHours;
                }
                else
                {
                    listTsOTHoursWeekly[weekCur] += listClockEvents[i].OTimeAuto;
                    listTsRegularHoursWeekly[weekCur] += -listClockEvents[i].OTimeAuto;
                } 
                //Differential/Rate2
                if (listClockEvents[i].Rate2Hours != TimeSpan.FromHours(-1))
                {
                    //Manual override
                    listTsDifferentialHoursWeekly[weekCur] += listClockEvents[i].Rate2Hours;
                }
                else
                {
                    listTsDifferentialHoursWeekly[weekCur] += listClockEvents[i].Rate2Auto;
                } 
            }
            //reset current week to itterate through time adjusts
            weekCur = 0;
            for (int i = 0;i < listTimeAdjusts.Count;i++)
            {
                for (int j = 0;j < weekStartDates.Count;j++)
                {
                    //list of timeAdjusts have already been filtered. All timeAdjusts in this list are applicable.
                    //set current week for time adjusts-----
                    if (listTimeAdjusts[i].TimeEntry < weekStartDates[j].AddDays(7))
                    {
                        weekCur = j;
                        break;
                    }
                     
                }
                //clock event occurs during the week "j"
                listTsRegularHoursWeekly[weekCur] += listTimeAdjusts[i].RegHours;
                listTsOTHoursWeekly[weekCur] += listTimeAdjusts[i].OTimeHours;
            }
            //Overtime should have already been calculated by CalcWeekly(); No calculations needed, just sum values.------------------------------------------------------
            double totalHoursWorked = 0;
            double totalRegularHoursWorked = 0;
            double totalOTHoursWorked = 0;
            double totalDiffHoursWorked = 0;
            for (int i = 0;i < weekStartDates.Count;i++)
            {
                //sum weekly totals.
                totalHoursWorked += listTsRegularHoursWeekly[i].TotalHours;
                totalHoursWorked += listTsOTHoursWeekly[i].TotalHours;
                totalRegularHoursWorked += listTsRegularHoursWeekly[i].TotalHours;
                totalOTHoursWorked += listTsOTHoursWeekly[i].TotalHours;
                totalDiffHoursWorked += listTsDifferentialHoursWeekly[i].TotalHours;
            }
            //Regular time at R1 and R2
            double rate1ratio = 0;
            if (totalHoursWorked != 0)
            {
                rate1ratio = 1 - totalDiffHoursWorked / totalHoursWorked;
            }
             
            dataRowCur["totalHours"] = TimeSpan.FromHours(totalHoursWorked).ToString();
            double r1Hours = rate1ratio * totalRegularHoursWorked;
            double r2Hours = totalRegularHoursWorked - r1Hours;
            double r1OTHours = rate1ratio * totalOTHoursWorked;
            double r2OTHours = totalHoursWorked - r1Hours - r2Hours - r1OTHours;
            //"self correcting math" uses guaranteed to total correctly.
            dataRowCur["rate1Hours"] = TimeSpan.FromHours(r1Hours).ToString();
            dataRowCur["rate2Hours"] = TimeSpan.FromHours(r2Hours).ToString();
            dataRowCur["rate1OTHours"] = TimeSpan.FromHours(r1OTHours).ToString();
            dataRowCur["rate2OTHours"] = TimeSpan.FromHours(r2OTHours).ToString();
            dataRowCur["Note"] = note;
            retVal.Rows.Add(dataRowCur);
            continue;
        }
        return retVal;
    }

    /**
    * Used to sum a partial weeks worth of regular hours from clock events and time spans.
    */
    private static TimeSpan prevWeekRegHoursHelper(long employeeNum, DateTime startDate, DateTime endDate) throws Exception {
        String errors = "";
        List<ClockEvent> listCE = new List<ClockEvent>();
        List<TimeAdjust> listTA = new List<TimeAdjust>();
        try
        {
            listCE = ClockEvents.getListForTimeCardManage(employeeNum,startDate,endDate);
        }
        catch (Exception ex)
        {
            errors += ex.Message;
        }

        try
        {
            listTA = TimeAdjusts.getListForTimeCardManage(employeeNum,startDate,endDate);
        }
        catch (Exception ex)
        {
            errors += ex.Message;
        }

        TimeSpan retVal = TimeSpan.Zero;
        for (int i = 0;i < listCE.Count;i++)
        {
            retVal += listCE[i].TimeDisplayed2 - listCE[i].TimeDisplayed1;
            if (listCE[i].AdjustIsOverridden)
            {
                //Manual override
                retVal += listCE[i].Adjust;
            }
            else
            {
                retVal += listCE[i].AdjustAuto;
            } 
        }
        for (int i = 0;i < listTA.Count;i++)
        {
            retVal += listTA[i].RegHours;
        }
        return retVal;
    }

    /**
    * Used to sum a partial weeks worth of OT hours from clock events and time spans.
    */
    private static TimeSpan prevWeekOTHoursHelper(long employeeNum, DateTime startDate, DateTime endDate) throws Exception {
        List<ClockEvent> listCE = ClockEvents.getListForTimeCardManage(employeeNum,startDate,endDate);
        List<TimeAdjust> listTA = TimeAdjusts.getListForTimeCardManage(employeeNum,startDate,endDate);
        TimeSpan retVal = TimeSpan.Zero;
        for (int i = 0;i < listCE.Count;i++)
        {
            if (listCE[i].OTimeHours != TimeSpan.FromHours(-1))
            {
                //Manual override
                retVal += listCE[i].OTimeHours;
            }
            else
            {
                retVal += listCE[i].OTimeAuto;
            } 
        }
        for (int i = 0;i < listTA.Count;i++)
        {
            retVal += listTA[i].OTimeHours;
        }
        return retVal;
    }

    /**
    * Used to sum a partial weeks worth of rate2 hours from clock events.
    */
    private static TimeSpan prevWeekDiffHoursHelper(long employeeNum, DateTime startDate, DateTime endDate) throws Exception {
        List<ClockEvent> listCE = ClockEvents.getListForTimeCardManage(employeeNum,startDate,endDate);
        TimeSpan retVal = TimeSpan.Zero;
        for (int i = 0;i < listCE.Count;i++)
        {
            if (listCE[i].Rate2Hours != TimeSpan.FromHours(-1))
            {
                //Manual override
                retVal += listCE[i].Rate2Hours;
            }
            else
            {
                retVal += listCE[i].Rate2Auto;
            } 
        }
        return retVal;
    }

    /**
    * Returns number of work weeks spanned by dates.  Example: "11-01-2013"(Friday), to "11-14-2013"(Thursday) spans 3 weeks, if the workweek starts on Sunday it would
    * return a list containing "10-27-2013"(Sunday),"11-03-2013"(Sunday),and"11-10-2013"(Sunday).  Used to determine which week time adjustments and clock events belong to when totalling timespans.
    */
    private static List<DateTime> weekStartHelper(DateTime startDate, DateTime stopDate) throws Exception {
        List<DateTime> retVal = new List<DateTime>();
        DayOfWeek fdow = (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek);
        for (int i = 0;i < 7;i++)
        {
            //start date of first week.
            if (startDate.AddDays(-i).DayOfWeek == fdow)
            {
                retVal.Add(startDate.AddDays(-i));
                break;
            }
             
        }
        while (retVal[retVal.Count - 1].AddDays(7) < stopDate)
        {
            //found and added start date of first week.
            //add start of each workweek until we are past the dateStop
            retVal.Add(retVal[retVal.Count - 1].AddDays(7));
        }
        return retVal;
    }

    /**
    * @param isPrintReport Only applicable to ODHQ. If true, will add ADP pay numer and note. The query takes about 9 seconds if this is set top true vs. about 2 seconds if set to false.
    */
    public static String getTimeCardManageCommand(DateTime startDate, DateTime stopDate, boolean isPrintReport) throws Exception {
        String command = "SELECT clockevent.EmployeeNum,";
        if (PrefC.getBool(PrefName.DistributorKey) && isPrintReport)
        {
            //OD HQ
            command += "COALESCE(wikilist_employees.ADPNum,'NotInList') AS ADPNum,";
        }
         
        command += "employee.FName,employee.LName,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME((((TIME_TO_SEC(tempclockevent.TotalTime)-TIME_TO_SEC(tempclockevent.OverTime))\r\n" + 
        "\t\t\t\t\t\t+TIME_TO_SEC(tempclockevent.AdjEvent))+TIME_TO_SEC(IFNULL(temptimeadjust.AdjReg,0)))\r\n" + 
        "\t\t\t\t\t\t+(TIME_TO_SEC(tempclockevent.OverTime)+TIME_TO_SEC(IFNULL(temptimeadjust.AdjOTime,0)))) AS tempTotalTime,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME((TIME_TO_SEC(tempclockevent.TotalTime)-TIME_TO_SEC(tempclockevent.OverTime))\r\n" + 
        "\t\t\t\t\t\t+TIME_TO_SEC(tempclockevent.AdjEvent)+TIME_TO_SEC(IFNULL(temptimeadjust.AdjReg,0))) AS tempRegHrs,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME(TIME_TO_SEC(tempclockevent.OverTime)+TIME_TO_SEC(IFNULL(temptimeadjust.AdjOTime,0))) AS tempOverTime,\r\n" + 
        "\t\t\t\t\ttempclockevent.AdjEvent,\r\n" + 
        "\t\t\t\t\ttemptimeadjust.AdjReg,\r\n" + 
        "\t\t\t\t\ttemptimeadjust.AdjOTime,\r\n" + 
        "\t\t\t\t\tIFNULL(tempclockevent.Rate2Hours,\'00:00:00\') AS differential,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME(TIME_TO_SEC(tempbreak.BreakTime)+TIME_TO_SEC(AdjEvent)) AS BreakTime ";
        if (isPrintReport)
        {
            command += ",tempclockevent.Note ";
        }
         
        command += "FROM clockevent\t\r\n" + 
        "\t\t\t\tLEFT JOIN (SELECT ce.EmployeeNum,SEC_TO_TIME(IFNULL(SUM(UNIX_TIMESTAMP(ce.TimeDisplayed2)),0)-IFNULL(SUM(UNIX_TIMESTAMP(ce.TimeDisplayed1)),0)) AS TotalTime,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME(IFNULL(SUM(TIME_TO_SEC(CASE WHEN ce.OTimeHours=\'-01:00:00\' THEN ce.OTimeAuto ELSE ce.OTimeHours END)),0)) AS OverTime,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME(IFNULL(SUM(TIME_TO_SEC(CASE WHEN ce.AdjustIsOverridden=\'1\' THEN ce.Adjust ELSE ce.AdjustAuto END)),0)) AS AdjEvent,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME(SUM(UNIX_TIMESTAMP(CASE WHEN ce.Rate2Hours=\'-01:00:00\' THEN ce.Rate2Auto ELSE ce.Rate2Hours END))) AS Rate2Hours";
        if (isPrintReport)
        {
            command += ",\r\n" + 
            "\t\t\t\t\t(SELECT CASE WHEN cev.Note !=\"\" THEN cev.Note ELSE \"\" END FROM clockevent cev \r\n" + 
            "\t\t\t\t\t\tWHERE cev.TimeDisplayed1 >= " + POut.date(startDate) + "\r\n" + 
            "\t\t\t\t\t\tAND cev.TimeDisplayed1 <= " + POut.Date(stopDate.AddDays(1)) + " \r\n" + 
            "\t\t\t\t\t\tAND cev.TimeDisplayed2 > " + POut.date(new DateTime(0001, 1, 1)) + "\r\n" + 
            "\t\t\t\t\t\tAND (cev.ClockStatus = \'0\' OR cev.ClockStatus = \'1\')\r\n" + 
            "\t\t\t\t\t\tAND cev.EmployeeNum=ce.EmployeeNum\r\n" + 
            "\t\t\t\t\t\tORDER BY cev.TimeDisplayed2 LIMIT 1) AS Note";
        }
         
        command += "\r\n" + 
        "\t\t\t\t\tFROM clockevent ce\r\n" + 
        "\t\t\t\t\tWHERE ce.TimeDisplayed1 >= " + POut.date(startDate) + "\r\n" + 
        "\t\t\t\t\tAND ce.TimeDisplayed1 <= " + POut.Date(stopDate.AddDays(1)) + " \r\n" + 
        "\t\t\t\t\tAND ce.TimeDisplayed2 > " + POut.date(new DateTime(0001, 1, 1)) + "\r\n" + 
        "\t\t\t\t\tAND (ce.ClockStatus = \'0\' OR ce.ClockStatus = \'1\')\r\n" + 
        "\t\t\t\t\tGROUP BY ce.EmployeeNum) tempclockevent ON clockevent.EmployeeNum=tempclockevent.EmployeeNum\r\n" + 
        "\t\t\t\tLEFT JOIN (SELECT timeadjust.EmployeeNum,SEC_TO_TIME(SUM(TIME_TO_SEC(timeadjust.RegHours))) AS AdjReg,\r\n" + 
        "\t\t\t\t\tSEC_TO_TIME(SUM(TIME_TO_SEC(timeadjust.OTimeHours))) AdjOTime \r\n" + 
        "\t\t\t\t\tFROM timeadjust \r\n" + 
        "\t\t\t\t\tWHERE " + DbHelper.dateColumn("TimeEntry") + " >= " + POut.date(startDate) + " \r\n" + 
        "\t\t\t\t\tAND " + DbHelper.dateColumn("TimeEntry") + " <= " + POut.date(stopDate) + "\r\n" + 
        "\t\t\t\t\tGROUP BY timeadjust.EmployeeNum) temptimeadjust ON clockevent.EmployeeNum=temptimeadjust.EmployeeNum\r\n" + 
        "\t\t\t\tLEFT JOIN (SELECT ceb.EmployeeNum,SEC_TO_TIME(IFNULL(SUM(UNIX_TIMESTAMP(ceb.TimeDisplayed2)),0)-IFNULL(SUM(UNIX_TIMESTAMP(ceb.TimeDisplayed1)),0)) AS BreakTime\r\n" + 
        "\t\t\t\t\tFROM clockevent ceb\r\n" + 
        "\t\t\t\t\tWHERE ceb.TimeDisplayed1 >= " + POut.date(startDate) + "\r\n" + 
        "\t\t\t\t\tAND ceb.TimeDisplayed1 <= " + POut.Date(stopDate.AddDays(1)) + " \r\n" + 
        "\t\t\t\t\tAND ceb.TimeDisplayed2 > " + POut.date(new DateTime(0001, 1, 1)) + "\r\n" + 
        "\t\t\t\t\tAND ceb.ClockStatus = \'2\'\r\n" + 
        "\t\t\t\t\tGROUP BY ceb.EmployeeNum) tempbreak ON clockevent.EmployeeNum=tempbreak.EmployeeNum\r\n" + 
        "\t\t\t\tINNER JOIN employee ON clockevent.EmployeeNum=employee.EmployeeNum AND IsHidden=0 ";
        if (PrefC.getBool(PrefName.DistributorKey) && isPrintReport)
        {
            //OD HQ
            command += "LEFT JOIN wikilist_employees ON wikilist_employees.EmployeeNum=employee.EmployeeNum ";
        }
         
        //TODO:add Rate2Hours and Rate2Auto Columns to report.
        command += "GROUP BY EmployeeNum\r\n" + 
        "\t\t\t\tORDER BY employee.LName";
        return command;
    }

    /**
    * Returns all clock events, of all statuses, for a given employee between the date range (inclusive).
    */
    public static List<ClockEvent> getSimpleList(long employeeNum, DateTime StartDate, DateTime StopDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClockEvent>>GetObject(MethodBase.GetCurrentMethod(), employeeNum, StartDate, StopDate);
        }
         
        //Fill list-----------------------------------------------------------------------------------------------------------------------------
        String command = "SELECT * FROM clockevent WHERE" + " EmployeeNum = '" + POut.long(employeeNum) + "'" + " AND TimeDisplayed1 >= " + POut.date(StartDate) + " AND TimeDisplayed1 < " + POut.Date(StopDate.AddDays(1)) + " ORDER BY TimeDisplayed1";
        return Crud.ClockEventCrud.SelectMany(command);
    }

}


//adding a day takes it to midnight of the specified toDate