//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.ClockEvent;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.Db;
import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.TimeAdjust;
import OpenDentBusiness.TimeAdjusts;
import OpenDentBusiness.TimeCardRule;
import OpenDentBusiness.TimeCardRules;

/**
* 
*/
public class TimeCardRules   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all TimeCardRules.
    */
    private static List<TimeCardRule> listt = new List<TimeCardRule>();
    /**
    * A list of all TimeCardRules.
    */
    public static List<TimeCardRule> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<TimeCardRule> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM timecardrule";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "TimeCardRule";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.TimeCardRuleCrud.TableToList(table);
    }

    /*
    		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
    		///<summary></summary>
    		public static List<TimeCardRule> Refresh(long patNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<TimeCardRule>>(MethodBase.GetCurrentMethod(),patNum);
    			}
    			string command="SELECT * FROM timecardrule WHERE PatNum = "+POut.Long(patNum);
    			return Crud.TimeCardRuleCrud.SelectMany(command);
    		}
    		///<summary>Gets one TimeCardRule from the db.</summary>
    		public static TimeCardRule GetOne(long timeCardRuleNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				return Meth.GetObject<TimeCardRule>(MethodBase.GetCurrentMethod(),timeCardRuleNum);
    			}
    			return Crud.TimeCardRuleCrud.SelectOne(timeCardRuleNum);
    		}*/
    /**
    * 
    */
    public static long insert(TimeCardRule timeCardRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            timeCardRule.TimeCardRuleNum = Meth.GetLong(MethodBase.GetCurrentMethod(), timeCardRule);
            return timeCardRule.TimeCardRuleNum;
        }
         
        return Crud.TimeCardRuleCrud.Insert(timeCardRule);
    }

    /**
    * 
    */
    public static void update(TimeCardRule timeCardRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), timeCardRule);
            return ;
        }
         
        Crud.TimeCardRuleCrud.Update(timeCardRule);
    }

    /**
    * 
    */
    public static void delete(long timeCardRuleNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), timeCardRuleNum);
            return ;
        }
         
        String command = "DELETE FROM timecardrule WHERE TimeCardRuleNum = " + POut.long(timeCardRuleNum);
        Db.nonQ(command);
    }

    /**
    * will be deprecated with overhaul 9/13/2013. Validates pay period before making any adjustments.
    */
    public static String validatePayPeriod(Employee EmployeeCur, DateTime StartDate, DateTime StopDate) throws Exception {
        List<ClockEvent> breakList = ClockEvents.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate,true);
        List<ClockEvent> ClockEventList = ClockEvents.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate,false);
        boolean errorFound = false;
        String retVal = "Timecard errors for employee : " + Employees.getNameFL(EmployeeCur) + "\r\n";
        for (Object __dummyForeachVar0 : ClockEventList)
        {
            //Validate clock events
            ClockEvent cCur = (ClockEvent)__dummyForeachVar0;
            if (cCur.TimeDisplayed2.Year < 1880)
            {
                retVal += "  " + cCur.TimeDisplayed1.ToShortDateString() + " : Employee not clocked out.\r\n";
                errorFound = true;
            }
            else if (cCur.TimeDisplayed1.Date != cCur.TimeDisplayed2.Date)
            {
                retVal += "  " + cCur.TimeDisplayed1.ToShortDateString() + " : Clock entry spans multiple days.\r\n";
                errorFound = true;
            }
              
        }
        for (Object __dummyForeachVar1 : breakList)
        {
            //Validate Breaks
            ClockEvent bCur = (ClockEvent)__dummyForeachVar1;
            if (bCur.TimeDisplayed2.Year < 1880)
            {
                retVal += "  " + bCur.TimeDisplayed1.ToShortDateString() + " : Employee not clocked in from break.\r\n";
                errorFound = true;
            }
             
            if (bCur.TimeDisplayed1.Date != bCur.TimeDisplayed2.Date)
            {
                retVal += "  " + bCur.TimeDisplayed1.ToShortDateString() + " : One break spans multiple days.\r\n";
                errorFound = true;
            }
             
            for (int c = ClockEventList.Count - 1;c >= 0;c--)
            {
                if (ClockEventList[c].TimeDisplayed1.Date == bCur.TimeDisplayed1.Date)
                {
                    break;
                }
                 
                if (c == 0)
                {
                    //we never found a match
                    retVal += "  " + bCur.TimeDisplayed1.ToShortDateString() + " : Break found during non-working day.\r\n";
                    errorFound = true;
                }
                 
            }
        }
        //Validate OT Rules
        boolean amRuleFound = false;
        boolean pmRuleFound = false;
        boolean hourRuleFound = false;
        TimeCardRules.refreshCache();
        for (Object __dummyForeachVar2 : TimeCardRules.listt)
        {
            TimeCardRule tcrCur = (TimeCardRule)__dummyForeachVar2;
            if (tcrCur.EmployeeNum != EmployeeCur.EmployeeNum)
            {
                continue;
            }
             
            //Does not apply to this employee.
            if (tcrCur.AfterTimeOfDay > TimeSpan.Zero)
            {
                if (pmRuleFound)
                {
                    retVal += "  Multiple timecard rules for after time of day found. Only one allowed.\r\n";
                    errorFound = true;
                }
                 
                pmRuleFound = true;
            }
             
            if (tcrCur.BeforeTimeOfDay > TimeSpan.Zero)
            {
                if (amRuleFound)
                {
                    retVal += "  Multiple timecard rules for before time of day found. Only one allowed.\r\n";
                    errorFound = true;
                }
                 
                amRuleFound = true;
            }
             
            if (tcrCur.OverHoursPerDay > TimeSpan.Zero)
            {
                if (hourRuleFound)
                {
                    retVal += "  Multiple timecard rules for hours per day found. Only one allowed.\r\n";
                    errorFound = true;
                }
                 
                hourRuleFound = true;
            }
             
            if (pmRuleFound && hourRuleFound)
            {
                retVal += "  Both an OverHoursPerDay and an AfterTimeOfDay found for this employee.  Only one or the other is allowed.\r\n";
                errorFound = true;
            }
             
            if (amRuleFound && hourRuleFound)
            {
                retVal += "  Both an OverHoursPerDay and an BeforeTimeOfDay found for this employee.  Only one or the other is allowed.\r\n";
                errorFound = true;
            }
             
        }
        retVal += "\r\n";
        return (errorFound ? retVal : "");
    }

    /**
    * Clears automatic adjustment/adjustOT values and deletes automatic TimeAdjusts for period.
    */
    public static void clearAuto(long employeeNum, DateTime dateStart, DateTime dateStop) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), employeeNum, dateStart, dateStop);
            return ;
        }
         
        List<ClockEvent> ListCE = ClockEvents.getSimpleList(employeeNum,dateStart,dateStop);
        for (int i = 0;i < ListCE.Count;i++)
        {
            ListCE[i].AdjustAuto = TimeSpan.Zero;
            ListCE[i].OTimeAuto = TimeSpan.Zero;
            ListCE[i].Rate2Auto = TimeSpan.Zero;
            ClockEvents.Update(ListCE[i]);
        }
        List<TimeAdjust> ListTA = TimeAdjusts.getSimpleListAuto(employeeNum,dateStart,dateStop);
        for (int i = 0;i < ListTA.Count;i++)
        {
            TimeAdjusts.Delete(ListTA[i]);
        }
    }

    /**
    * Clears all manual adjustments/Adjust OT values from clock events. Does not alter adjustments to clockevent.TimeDisplayed1/2 nor does it delete or alter any TimeAdjusts.
    */
    public static void clearManual(long employeeNum, DateTime dateStart, DateTime dateStop) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), employeeNum, dateStart, dateStop);
            return ;
        }
         
        List<ClockEvent> ListCE = ClockEvents.getSimpleList(employeeNum,dateStart,dateStop);
        for (int i = 0;i < ListCE.Count;i++)
        {
            ListCE[i].Adjust = TimeSpan.Zero;
            ListCE[i].AdjustIsOverridden = false;
            ListCE[i].OTimeHours = TimeSpan.FromHours(-1);
            ListCE[i].Rate2Hours = TimeSpan.FromHours(-1);
            ClockEvents.Update(ListCE[i]);
        }
    }

    /**
    * Validates list and throws exceptions.  Gets a list of time card rules for a given employee.
    */
    public static List<TimeCardRule> getValidList(Employee employeeCur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<TimeCardRule>>GetObject(MethodBase.GetCurrentMethod(), employeeCur);
        }
         
        List<TimeCardRule> retVal = new List<TimeCardRule>();
        List<TimeSpan> listTimeSpansAM = new List<TimeSpan>();
        List<TimeSpan> listTimeSpansPM = new List<TimeSpan>();
        List<TimeSpan> listTimeSpansOver = new List<TimeSpan>();
        refreshCache();
        String errors = "";
        for (int i = 0;i < listt.Count;i++)
        {
            //Fill Rules list and time span list-------------------------------------------------------------------------------------------
            if (listt[i].EmployeeNum == employeeCur.EmployeeNum || listt[i].EmployeeNum == 0)
            {
                //specific rule for employee or rules that apply to all employees.
                retVal.Add(listt[i]);
                if (listt[i].BeforeTimeOfDay > TimeSpan.FromHours(0))
                {
                    listTimeSpansAM.Add(listt[i].BeforeTimeOfDay);
                }
                 
                if (listt[i].AfterTimeOfDay > TimeSpan.FromHours(0))
                {
                    listTimeSpansPM.Add(listt[i].AfterTimeOfDay);
                }
                 
                if (listt[i].OverHoursPerDay > TimeSpan.FromHours(0))
                {
                    listTimeSpansOver.Add(listt[i].OverHoursPerDay);
                }
                 
            }
             
        }
        //Validate Rules---------------------------------------------------------------------------------------------------------------
        if (listTimeSpansAM.Count > 1)
        {
            errors += "Multiple matches of BeforeTimeOfDay found, only one allowed.\r\n";
        }
         
        if (listTimeSpansPM.Count > 1)
        {
            errors += "Multiple matches of AfterTimeOfDay found, only one allowed.\r\n";
        }
         
        if (listTimeSpansOver.Count > 1)
        {
            errors += "Multiple matches of OverHoursPerDay found, only one allowed.\r\n";
        }
         
        if (listTimeSpansAM.Count + listTimeSpansPM.Count > 0 && listTimeSpansOver.Count > 0)
        {
            errors += "Both OverHoursPerDay and Rate2 rules found.\r\n";
        }
         
        if (!StringSupport.equals(errors, ""))
        {
            throw new Exception("Time card rule errors:\r\n" + errors);
        }
         
        return retVal;
    }

    /**
    * Calculates daily overtime. Throws exceptions when encountering errors, though all errors SHOULD have been caught already by using the ValidatePayPeriod() function and generating a msgbox.
    */
    public static void calculateDailyOvertime_Old(Employee EmployeeCur, DateTime StartDate, DateTime StopDate) throws Exception {
        DateTime previousDate = new DateTime();
        List<ClockEvent> ClockEventList = ClockEvents.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate,false);
        //PIn.Date(textDateStart.Text),PIn.Date(textDateStop.Text),IsBreaks);
        //Over breaks-------------------------------------------------------------------------------------------------
        if (PrefC.getBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks))
        {
            for (int i = 0;i < ClockEventList.Count;i++)
            {
                //set adj auto to zero for all.
                ClockEventList[i].AdjustAuto = TimeSpan.Zero;
                ClockEvents.Update(ClockEventList[i]);
            }
            List<ClockEvent> breakList = ClockEvents.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate,true);
            //PIn.Date(textDateStart.Text),PIn.Date(textDateStop.Text),true);
            TimeSpan totalToday = TimeSpan.Zero;
            TimeSpan totalOne = TimeSpan.Zero;
            previousDate = DateTime.MinValue;
            for (int b = 0;b < breakList.Count;b++)
            {
                if (breakList[b].TimeDisplayed2.Year < 1880)
                {
                    throw new Exception("Error. Employee break malformed.");
                }
                 
                if (breakList[b].TimeDisplayed1.Date != breakList[b].TimeDisplayed2.Date)
                {
                    throw new Exception("Error. One break spans multiple dates.");
                }
                 
                //calc time for the one break
                totalOne = breakList[b].TimeDisplayed2 - breakList[b].TimeDisplayed1;
                //calc daily total
                if (previousDate.Date != breakList[b].TimeDisplayed1.Date)
                {
                    //if date changed, this is the first pair of the day
                    totalToday = TimeSpan.Zero;
                    //new day
                    previousDate = breakList[b].TimeDisplayed1.Date;
                }
                 
                //for the next loop
                totalToday += totalOne;
                //decide if breaks for the day went over 30 min.
                if (totalToday > TimeSpan.FromMinutes(31))
                {
                    for (int c = ClockEventList.Count - 1;c >= 0;c--)
                    {
                        //31 to prevent silly fractions less than 1.
                        //loop through all ClockEvents in this grid to find one to adjust.
                        //Go backwards to find the last entry for a given date.
                        if (ClockEventList[c].TimeDisplayed1.Date == breakList[b].TimeDisplayed1.Date)
                        {
                            ClockEventList[c].AdjustAuto -= (totalToday - TimeSpan.FromMinutes(30));
                            ClockEvents.Update(ClockEventList[c]);
                            totalToday = TimeSpan.FromMinutes(30);
                            break;
                        }
                         
                        //reset to 30.  Therefore, any additional breaks will be wholly adjustments.
                        if (c == 0)
                        {
                            throw new Exception("Error. Over breaks, but could not adjust because not regular time entered for date:" + breakList[b].TimeDisplayed1.Date.ToShortDateString());
                        }
                         
                    }
                }
                 
            }
        }
         
        //we never found a match
        //OT-------------------------------------------------------------------------------------------------------------
        TimeCardRule afterTimeRule = null;
        TimeCardRule beforeTimeRule = null;
        TimeCardRule overHoursRule = null;
        for (int i = 0;i < TimeCardRules.getListt().Count;i++)
        {
            //loop through timecardrules to find one rule of each kind.
            if (TimeCardRules.getListt()[i].EmployeeNum != 0 && TimeCardRules.getListt()[i].EmployeeNum != EmployeeCur.EmployeeNum)
            {
                continue;
            }
             
            if (TimeCardRules.getListt()[i].AfterTimeOfDay > TimeSpan.Zero)
            {
                if (afterTimeRule != null)
                {
                    throw new Exception("Error.  Multiple matches of AfterTimeOfDay found for this employee.  Only one allowed.");
                }
                 
                //already found a match, and this is a second match
                //return;
                afterTimeRule = TimeCardRules.getListt()[i];
            }
            else if (TimeCardRules.getListt()[i].OverHoursPerDay > TimeSpan.Zero)
            {
                if (overHoursRule != null)
                {
                    throw new Exception("Error.  Multiple matches of OverHoursPerDay found for this employee.  Only one allowed.");
                }
                 
                //already found a match, and this is a second match
                //return;
                overHoursRule = TimeCardRules.getListt()[i];
            }
              
            if (afterTimeRule != null && overHoursRule != null)
            {
                throw new Exception("Error.  Both an OverHoursPerDay and an AfterTimeOfDay found for this employee.  Only one or the other is allowed.");
            }
             
            //return;
            if (beforeTimeRule != null && overHoursRule != null)
            {
                throw new Exception("Error.  Both an OverHoursPerDay and an BeforeTimeOfDay found for this employee.  Only one or the other is allowed.");
            }
             
            //return;
            if (TimeCardRules.getListt()[i].BeforeTimeOfDay > TimeSpan.Zero)
            {
                if (beforeTimeRule != null)
                {
                    throw new Exception("Error.  Multiple matches of BeforeTimeOfDay found for this employee.  Only one allowed.");
                }
                 
                //already found a match, and this is a second match
                //return;
                beforeTimeRule = TimeCardRules.getListt()[i];
            }
             
        }
        //loop through all ClockEvents in this grid.
        TimeSpan dailyTotal = TimeSpan.Zero;
        TimeSpan pairTotal = TimeSpan.Zero;
        previousDate = DateTime.MinValue;
        for (int i = 0;i < ClockEventList.Count;i++)
        {
            if (ClockEventList[i].TimeDisplayed2.Year < 1880)
            {
                throw new Exception("Error. Employee not clocked out.");
            }
             
            //return;
            if (ClockEventList[i].TimeDisplayed1.Date != ClockEventList[i].TimeDisplayed2.Date)
            {
                throw new Exception("Error. One clock pair spans multiple dates.");
            }
             
            //return;
            pairTotal = ClockEventList[i].TimeDisplayed2 - ClockEventList[i].TimeDisplayed1;
            //add any adjustments, manual or overrides.
            if (ClockEventList[i].AdjustIsOverridden)
            {
                pairTotal += ClockEventList[i].Adjust;
            }
            else
            {
                pairTotal += ClockEventList[i].AdjustAuto;
            } 
            //calc daily total
            if (previousDate.Date != ClockEventList[i].TimeDisplayed1.Date)
            {
                //if date changed
                dailyTotal = TimeSpan.Zero;
                //new day
                previousDate = ClockEventList[i].TimeDisplayed1.Date;
            }
             
            //for the next loop
            dailyTotal += pairTotal;
            //handle OT
            ClockEventList[i].OTimeAuto = TimeSpan.Zero;
            //set auto OT to zero.
            if (ClockEventList[i].OTimeHours != TimeSpan.FromHours(-1))
            {
                //if OT is overridden
                //don't try to calc a time.
                ClockEvents.Update(ClockEventList[i]);
                //just to possibly clear autoOT, even though it doesn't count.
                //but still need to subtract OT from dailyTotal
                dailyTotal -= ClockEventList[i].OTimeHours;
                continue;
            }
             
            if (afterTimeRule != null)
            {
                //test to see if this span is after specified time
                TimeSpan afterTime = TimeSpan.Zero;
                if (ClockEventList[i].TimeDisplayed1.TimeOfDay > afterTimeRule.AfterTimeOfDay)
                {
                    //the start time is after time, so the whole pairTotal is OT
                    afterTime = pairTotal;
                }
                else if (ClockEventList[i].TimeDisplayed2.TimeOfDay > afterTimeRule.AfterTimeOfDay)
                {
                    //only the second time is after time
                    afterTime = ClockEventList[i].TimeDisplayed2.TimeOfDay - afterTimeRule.AfterTimeOfDay;
                }
                  
                //only a portion of the pairTotal is OT
                ClockEventList[i].OTimeAuto = afterTime;
            }
             
            if (beforeTimeRule != null)
            {
                //test to see if this span is after specified time
                TimeSpan beforeTime = TimeSpan.Zero;
                if (ClockEventList[i].TimeDisplayed2.TimeOfDay < beforeTimeRule.BeforeTimeOfDay)
                {
                    //the end time is before time, so the whole pairTotal is OT
                    beforeTime += pairTotal;
                }
                else if (ClockEventList[i].TimeDisplayed1.TimeOfDay < beforeTimeRule.BeforeTimeOfDay)
                {
                    //only the first time is before time
                    beforeTime += beforeTimeRule.BeforeTimeOfDay - ClockEventList[i].TimeDisplayed1.TimeOfDay;
                }
                  
                //only a portion of the pairTotal is OT
                ClockEventList[i].OTimeAuto += beforeTime;
            }
             
            if (overHoursRule != null)
            {
                //test dailyTotal
                TimeSpan overHours = TimeSpan.Zero;
                if (dailyTotal > overHoursRule.OverHoursPerDay)
                {
                    overHours = dailyTotal - overHoursRule.OverHoursPerDay;
                    dailyTotal = overHoursRule.OverHoursPerDay;
                    //e.g. reset to 8.  Any further pairs on this date will be wholly OT
                    ClockEventList[i].OTimeAuto += overHours;
                }
                 
            }
             
            ClockEvents.Update(ClockEventList[i]);
        }
        adjustBreaksHelper(EmployeeCur,StartDate,StopDate);
    }

    /**
    * Calculates daily overtime.  Daily overtime does not take into account any time adjust events.  All manually entered time adjust events are assumed to be entered correctly and should not be used in calculating automatic totals.  Throws exceptions when encountering errors.
    */
    public static void calculateDailyOvertime(Employee employee, DateTime dateStart, DateTime dateStop) throws Exception {
        List<ClockEvent> listClockEvent = new List<ClockEvent>();
        List<ClockEvent> listClockEventBreak = new List<ClockEvent>();
        List<TimeCardRule> listTimeCardRule = new List<TimeCardRule>();
        String errors = "";
        String clockErrors = "";
        String breakErrors = "";
        String ruleErrors = "";
        try
        {
            //Fill lists and catch validation error messages------------------------------------------------------------------------------------------------------------
            listClockEvent = ClockEvents.getValidList(employee.EmployeeNum,dateStart,dateStop,false);
        }
        catch (Exception ex)
        {
            clockErrors += ex.Message;
        }

        try
        {
            listClockEventBreak = ClockEvents.getValidList(employee.EmployeeNum,dateStart,dateStop,true);
        }
        catch (Exception ex)
        {
            breakErrors += ex.Message;
        }

        try
        {
            listTimeCardRule = TimeCardRules.getValidList(employee);
        }
        catch (Exception ex)
        {
            ruleErrors += ex.Message;
        }

        for (int b = 0;b < listClockEventBreak.Count;b++)
        {
            //Validation between two or more lists above----------------------------------------------------------------------------------------------------------------
            boolean isValidBreak = false;
            for (int c = 0;c < listClockEvent.Count;c++)
            {
                if (timeClockEventsOverlapHelper(listClockEventBreak[b], listClockEvent[c]))
                {
                    //break started after work did
                    if (listClockEventBreak[b].TimeDisplayed1 > listClockEvent[c].TimeDisplayed1 && listClockEventBreak[b].TimeDisplayed2 < listClockEvent[c].TimeDisplayed2)
                    {
                        //break ended before working hours
                        //valid break.
                        isValidBreak = true;
                        break;
                    }
                     
                    //invalid break.
                    isValidBreak = false;
                    break;
                }
                 
            }
            //redundant, but harmless. Makes code more readable.
            if (isValidBreak)
            {
                continue;
            }
             
            breakErrors += "  " + listClockEventBreak[b].TimeDisplayed1.ToString() + " : break found during non-working hours.\r\n";
        }
        //ToString() instead of ToShortDateString() to show time.
        //Report Errors---------------------------------------------------------------------------------------------------------------------------------------------
        errors = ruleErrors + clockErrors + breakErrors;
        if (!StringSupport.equals(errors, ""))
        {
            throw new Exception(Employees.getNameFL(employee) + " has the following errors:\r\n" + errors);
        }
         
        //Begin calculations=========================================================================================================================================
        TimeSpan tsHoursWorkedTotal = new TimeSpan();
        TimeSpan tsOvertimeHoursRule = new TimeSpan(24, 0, 0);
        //Example 10:00 for overtime rule after 10 hours per day.
        TimeSpan tsDifferentialAMRule = new TimeSpan();
        //Example 06:00 for differential rule before 6am.
        TimeSpan tsDifferentialPMRule = new TimeSpan(24, 0, 0);
        for (int i = 0;i < listTimeCardRule.Count;i++)
        {
            //Example 17:00 for differential rule after  5pm.
            //Fill over hours rule from list-------------------------------------------------------------------------------------
            //loop through rules for this one employee, including any that apply to all emps.
            if (listTimeCardRule[i].OverHoursPerDay != TimeSpan.Zero)
            {
                //OverHours Rule
                tsOvertimeHoursRule = listTimeCardRule[i].OverHoursPerDay;
            }
             
            //at most, one non-zero OverHours rule available at this point.
            if (listTimeCardRule[i].BeforeTimeOfDay != TimeSpan.Zero)
            {
                //AM Rule
                tsDifferentialAMRule = listTimeCardRule[i].BeforeTimeOfDay;
            }
             
            //at most, one non-zero AM rule available at this point.
            if (listTimeCardRule[i].AfterTimeOfDay != TimeSpan.Zero)
            {
                //PM Rule
                tsDifferentialPMRule = listTimeCardRule[i].AfterTimeOfDay;
            }
             
        }
        //at most, one non-zero PM rule available at this point.
        //Calculations: Regular Time, Overtime, Rate2 time---------------------------------------------------------------------------------------------------
        TimeSpan tsDailyBreaksAdjustTotal = new TimeSpan();
        //used to adjust the clock event
        TimeSpan tsDailyBreaksTotal = new TimeSpan();
        //used in calculating breaks over 30 minutes per day.
        TimeSpan tsDailyDifferentialTotal = new TimeSpan();
        for (int i = 0;i < listClockEvent.Count;i++)
        {
            //hours before and after AM/PM diff rules. Adjusted for overbreaks.
            //Note: If TimeCardsMakesAdjustmentsForOverBreaks is true, only the first 30 minutes of break per day are paid.
            //All breaktime thereafter will be calculated as if the employee was clocked out at that time.
            if (i == 0 || listClockEvent[i].TimeDisplayed1.Date != listClockEvent[i - 1].TimeDisplayed1.Date)
            {
                tsDailyDifferentialTotal = TimeSpan.Zero;
            }
             
            //AM-----------------------------------
            if (listClockEvent[i].TimeDisplayed1.TimeOfDay < tsDifferentialAMRule)
            {
                //clocked in before AM differential rule
                tsDailyDifferentialTotal += tsDifferentialAMRule - listClockEvent[i].TimeDisplayed1.TimeOfDay;
                if (listClockEvent[i].TimeDisplayed2.TimeOfDay < tsDifferentialAMRule)
                {
                    //clocked out before AM differential rule also
                    tsDailyDifferentialTotal += listClockEvent[i].TimeDisplayed1.TimeOfDay - tsDifferentialAMRule;
                }
                 
                //add a negative timespan
                //Adjust AM differential by overbreaks-----
                TimeSpan tsAMBreakTimeCounter = new TimeSpan();
                for (int b = 0;b < listClockEventBreak.Count;b++)
                {
                    if (tsAMBreakTimeCounter > TimeSpan.FromMinutes(30))
                    {
                        tsAMBreakTimeCounter = TimeSpan.FromMinutes(30);
                    }
                     
                    //reset overages for next calculation.
                    if (listClockEventBreak[b].TimeDisplayed1.Date != listClockEvent[i].TimeDisplayed1.Date)
                    {
                        continue;
                    }
                     
                    //skip breaks for other days.
                    tsAMBreakTimeCounter += listClockEventBreak[b].TimeDisplayed2 - listClockEventBreak[b].TimeDisplayed1;
                    if (tsAMBreakTimeCounter < TimeSpan.FromMinutes(30))
                    {
                        continue;
                    }
                     
                    //not over thirty minutes yet.
                    if (timeClockEventsOverlapHelper(listClockEvent[i], listClockEventBreak[b]))
                    {
                        continue;
                    }
                     
                    //There must be multiple clock events for this day, and we have gone over breaks during a later clock event period
                    if (listClockEventBreak[b].TimeDisplayed1.TimeOfDay > tsDifferentialAMRule)
                    {
                        continue;
                    }
                     
                    //this break started after the AM differential so there is nothing left to do in this loop. break out of the entire loop.
                    if (listClockEventBreak[b].TimeDisplayed2.TimeOfDay - (tsAMBreakTimeCounter - TimeSpan.FromMinutes(30)) > tsDifferentialAMRule)
                    {
                        continue;
                    }
                     
                    //entirety of break overage occured after AM differential time.
                    //Make adjustments because: 30+ minutes of break, break occured during clockEvent, break started before the AM rule.
                    TimeSpan tsAMAdjustAmount = TimeSpan.Zero;
                    tsAMAdjustAmount += tsDifferentialAMRule - (listClockEventBreak[b].TimeDisplayed2.TimeOfDay - (tsAMBreakTimeCounter - TimeSpan.FromMinutes(30)));
                    //should be negative timespan
                    tsDailyDifferentialTotal += tsAMAdjustAmount;
                }
            }
             
            //PM-------------------------------------
            if (listClockEvent[i].TimeDisplayed2.TimeOfDay > tsDifferentialPMRule)
            {
                //clocked out after PM differential rule
                tsDailyDifferentialTotal += listClockEvent[i].TimeDisplayed2.TimeOfDay - tsDifferentialPMRule;
                if (listClockEvent[i].TimeDisplayed1.TimeOfDay > tsDifferentialPMRule)
                {
                    //clocked in after PM differential rule also
                    tsDailyDifferentialTotal += tsDifferentialPMRule - listClockEvent[i].TimeDisplayed1.TimeOfDay;
                }
                 
                //add a negative timespan
                //Adjust PM differential by overbreaks-----
                TimeSpan tsPMBreakTimeCounter = new TimeSpan();
                for (int b = 0;b < listClockEventBreak.Count;b++)
                {
                    if (tsPMBreakTimeCounter > TimeSpan.FromMinutes(30))
                    {
                        tsPMBreakTimeCounter = TimeSpan.FromMinutes(30);
                    }
                     
                    //reset overages for next calculation.
                    if (listClockEventBreak[b].TimeDisplayed1.Date != listClockEvent[i].TimeDisplayed1.Date)
                    {
                        continue;
                    }
                     
                    //skip breaks for other days.
                    tsPMBreakTimeCounter += listClockEventBreak[b].TimeDisplayed2 - listClockEventBreak[b].TimeDisplayed1;
                    if (tsPMBreakTimeCounter < TimeSpan.FromMinutes(30))
                    {
                        continue;
                    }
                     
                    //not over thirty minutes yet.
                    if (!timeClockEventsOverlapHelper(listClockEvent[i], listClockEventBreak[b]))
                    {
                        continue;
                    }
                     
                    //There must be multiple clock events for this day, and we have gone over breaks during a different clock event period
                    if (listClockEventBreak[b].TimeDisplayed2.TimeOfDay < tsDifferentialPMRule)
                    {
                        continue;
                    }
                     
                    //this break ended before the PM differential so there is nothing left to do in this loop. break out of the entire loop.
                    if (listClockEventBreak[b].TimeDisplayed2.TimeOfDay < tsDifferentialPMRule)
                    {
                        continue;
                    }
                     
                    //entirety of break overage occured before PM differential time.
                    //Make adjustments because: 30+ minutes of break, break occured during clockEvent, break ended after the PM rule.
                    TimeSpan tsPMAdjustAmount = TimeSpan.Zero;
                    tsPMAdjustAmount += tsDifferentialPMRule - (listClockEventBreak[b].TimeDisplayed2.TimeOfDay - (tsPMBreakTimeCounter - TimeSpan.FromMinutes(30)));
                    //should be negative timespan
                    tsDailyDifferentialTotal += tsPMAdjustAmount;
                }
            }
             
            //Apply differential to clock event-----------------------------------------------------------------------------------
            if (tsDailyDifferentialTotal < TimeSpan.Zero)
            {
                throw new Exception(" - " + listClockEvent[i].TimeDisplayed1.Date.ToShortDateString() + " : calculated differential hours was negative.");
            }
             
            //this should never happen. If it ever does, we need to know about it, because that means some math has been miscalculated.
            listClockEvent[i].Rate2Auto = tsDailyDifferentialTotal;
            //should be zero or greater.
            listClockEvent[i].OTimeAuto = TimeSpan.Zero;
            listClockEvent[i].AdjustAuto = TimeSpan.Zero;
            if (i == 0 || listClockEvent[i].TimeDisplayed1.Date != listClockEvent[i - 1].TimeDisplayed1.Date)
            {
                tsHoursWorkedTotal = TimeSpan.Zero;
                tsDailyBreaksAdjustTotal = TimeSpan.Zero;
                tsDailyBreaksTotal = TimeSpan.Zero;
                tsDailyDifferentialTotal = TimeSpan.Zero;
            }
             
            tsHoursWorkedTotal += listClockEvent[i].TimeDisplayed2 - listClockEvent[i].TimeDisplayed1;
            //Hours worked
            if (tsHoursWorkedTotal > tsOvertimeHoursRule)
            {
                //if OverHoursPerDay then make AutoOTAdjustments.
                listClockEvent[i].OTimeAuto += tsHoursWorkedTotal - tsOvertimeHoursRule;
            }
             
            //++OTimeAuto
            //listClockEvent[i].AdjustAuto-=tsHoursWorkedTotal-tsOvertimeHoursRule;//--AdjustAuto
            if (i == listClockEvent.Count - 1 || listClockEvent[i].TimeDisplayed1.Date != listClockEvent[i + 1].TimeDisplayed1.Date)
            {
                //Either the last clock event in the list or last clock event for the day.
                //OVERBREAKS--------------------------------------------------------------------------------------------------------
                if (PrefC.getBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks))
                {
                    //Apply overbreaks to this clockEvent.
                    tsDailyBreaksAdjustTotal = new TimeSpan();
                    //used to adjust the clock event
                    tsDailyBreaksTotal = new TimeSpan();
                    for (int b = 0;b < listClockEventBreak.Count;b++)
                    {
                        //used in calculating breaks over 30 minutes per day.
                        //check all breaks for current day.
                        if (listClockEventBreak[b].TimeDisplayed1.Date != listClockEvent[i].TimeDisplayed1.Date)
                        {
                            continue;
                        }
                         
                        //skip breaks for other dates than current ClockEvent
                        tsDailyBreaksTotal += (listClockEventBreak[b].TimeDisplayed2.TimeOfDay - listClockEventBreak[b].TimeDisplayed1.TimeOfDay);
                        if (tsDailyBreaksTotal > TimeSpan.FromMinutes(31))
                        {
                            //over 31 to avoid adjustments less than 1 minutes.
                            listClockEventBreak[b].AdjustAuto = TimeSpan.FromMinutes(30) - tsDailyBreaksTotal;
                            ClockEvents.Update(listClockEventBreak[b]);
                            //save adjustments to breaks.
                            tsDailyBreaksAdjustTotal += listClockEventBreak[b].AdjustAuto;
                            tsDailyBreaksTotal = TimeSpan.FromMinutes(30);
                        }
                         
                    }
                    //reset daily breaks to 30 minutes so the next break is all adjustment.
                    //end overBreaks>31 minutes
                    //end checking all breaks for current day
                    //OverBreaks applies to overtime and then to RegularTime
                    listClockEvent[i].OTimeAuto += tsDailyBreaksAdjustTotal;
                    //tsDailyBreaksTotal<=TimeSpan.Zero
                    listClockEvent[i].AdjustAuto += tsDailyBreaksAdjustTotal;
                    //tsDailyBreaksTotal is less than or equal to zero
                    if (listClockEvent[i].OTimeAuto < TimeSpan.Zero)
                    {
                        //we have adjusted OT too far
                        //listClockEvent[i].AdjustAuto+=listClockEvent[i].OTimeAuto;
                        listClockEvent[i].OTimeAuto = TimeSpan.Zero;
                    }
                     
                    tsDailyBreaksTotal = TimeSpan.Zero;
                    //zero out for the next day.
                    tsHoursWorkedTotal = TimeSpan.Zero;
                }
                 
            }
             
            //zero out for next day.
            //end overbreaks
            ClockEvents.Update(listClockEvent[i]);
        }
    }

    //end clockEvent loop.
    /**
    * Returns true if two clock events overlap. Useful for determining if a break applies to a given clock event.
    * Does not matter which order clock events are provided.
    */
    private static boolean timeClockEventsOverlapHelper(ClockEvent clockEvent1, ClockEvent clockEvent2) throws Exception {
        //Visual representation
        //ClockEvent1:            o----------------o
        //ClockEvent2:o---------------o   or  o-------------------o
        if (clockEvent2.TimeDisplayed2 > clockEvent1.TimeDisplayed1 && clockEvent2.TimeDisplayed1 < clockEvent1.TimeDisplayed2)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Updates OTimeAuto, AdjustAuto (calculated and set above., and Rate2Auto based on the rules passed in, and calculated break time overages.
    */
    private static void adjustAutoClockEventEntriesHelper(List<ClockEvent> listClockEvent, List<ClockEvent> listClockEventBreak, TimeSpan tsDifferentialAMRule, TimeSpan tsDifferentialPMRule, TimeSpan tsOvertimeHoursRule) throws Exception {
        for (int i = 0;i < listClockEvent.Count;i++)
        {
            //listClockEvent[i].OTimeAuto	=TimeSpan.Zero;
            listClockEvent[i].AdjustAuto = TimeSpan.Zero;
            listClockEvent[i].Rate2Auto = TimeSpan.Zero;
            //OTimeAuto and AdjustAuto---------------------------------------------------------------------------------
            //if((listClockEvent[i].TimeDisplayed2.TimeOfDay-listClockEvent[i].TimeDisplayed1.TimeOfDay)>tsOvertimeHoursRule) {
            //	listClockEvent[i].OTimeAuto+=listClockEvent[i].TimeDisplayed2.TimeOfDay-listClockEvent[i].TimeDisplayed1.TimeOfDay-tsOvertimeHoursRule;
            //listClockEvent[i].AdjustAuto+=-listClockEvent[i].OTimeAuto;
            //}
            //AdjustAuto due to break overages-------------------------------------------------------------------------
            if (PrefC.getBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks))
            {
                if (i == listClockEvent.Count - 1 || listClockEvent[i].TimeDisplayed1.Date != listClockEvent[i + 1].TimeDisplayed1.Date)
                {
                    //last item or last item for a given day.
                    TimeSpan tsTotalBreaksToday = TimeSpan.Zero;
                    for (int j = 0;j < listClockEventBreak.Count;j++)
                    {
                        if (listClockEventBreak[j].TimeDisplayed1.Date != listClockEvent[i].TimeDisplayed1.Date)
                        {
                            continue;
                        }
                         
                        //skip breaks that occured on different days.
                        tsTotalBreaksToday += listClockEventBreak[j].TimeDisplayed2.TimeOfDay - listClockEventBreak[j].TimeDisplayed1.TimeOfDay;
                    }
                    if (tsTotalBreaksToday > TimeSpan.FromMinutes(31))
                    {
                        listClockEvent[i].AdjustAuto += TimeSpan.FromMinutes(30) - tsTotalBreaksToday;
                        //should add a negative time span.
                        listClockEvent[i].OTimeAuto += TimeSpan.FromMinutes(30) - tsTotalBreaksToday;
                        //should add a negative time span.
                        if (listClockEvent[i].OTimeAuto < TimeSpan.Zero)
                        {
                            //if we removed too much overbreak from otAuto, remove it from adjust auto instead and set otauto to zero
                            listClockEvent[i].AdjustAuto += listClockEvent[i].OTimeAuto;
                            listClockEvent[i].OTimeAuto = TimeSpan.Zero;
                        }
                         
                        tsTotalBreaksToday = TimeSpan.FromMinutes(30);
                    }
                     
                }
                 
            }
             
            //reset break today to 30 minutes, so next break is entirely overBreak.
            //Rate2Auto-------------------------------------------------------------------------------------------------
            if (listClockEvent[i].TimeDisplayed1.TimeOfDay < tsDifferentialAMRule)
            {
                //AM, example rule before 8am, work from 5am to 7am
                listClockEvent[i].Rate2Auto += tsDifferentialAMRule - listClockEvent[i].TimeDisplayed1.TimeOfDay;
                //8am-5am=3hrs
                if (listClockEvent[i].TimeDisplayed2.TimeOfDay < tsDifferentialAMRule)
                {
                    listClockEvent[i].Rate2Auto += listClockEvent[i].TimeDisplayed2.TimeOfDay - tsDifferentialAMRule;
                }
                 
            }
             
            //8am-7am=-1hr =>2hrs total
            if (listClockEvent[i].TimeDisplayed2.TimeOfDay > tsDifferentialPMRule)
            {
                //PM, example diffRule after 8pm, work from 9 to 11pm.
                listClockEvent[i].Rate2Auto += listClockEvent[i].TimeDisplayed2.TimeOfDay - tsDifferentialPMRule;
                //11pm-8pm = 3hrs
                if (listClockEvent[i].TimeDisplayed1.TimeOfDay > tsDifferentialPMRule)
                {
                    listClockEvent[i].Rate2Auto += tsDifferentialPMRule - listClockEvent[i].TimeDisplayed1.TimeOfDay;
                }
                 
            }
             
            //8pm-9pm = -1hr =>2hrs total
            ClockEvents.Update(listClockEvent[i]);
        }
    }

    //end ClockEvent list
    /**
    * Deprecated.  This function is aesthetic and has no bearing on actual OT calculations. It adds adjustments to breaks so that when viewing them you can see if they went over 30 minutes.
    */
    private static void adjustBreaksHelper(Employee EmployeeCur, DateTime StartDate, DateTime StopDate) throws Exception {
        if (!PrefC.getBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks))
        {
            return ;
        }
         
        //Only adjust breaks if preference is set.
        List<ClockEvent> breakList = ClockEvents.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate,true);
        //PIn.Date(textDateStart.Text),PIn.Date(textDateStop.Text),true);
        TimeSpan totalToday = TimeSpan.Zero;
        TimeSpan totalOne = TimeSpan.Zero;
        DateTime previousDate = DateTime.MinValue;
        for (int b = 0;b < breakList.Count;b++)
        {
            if (breakList[b].TimeDisplayed2.Year < 1880)
            {
                return ;
            }
             
            if (breakList[b].TimeDisplayed1.Date != breakList[b].TimeDisplayed2.Date)
            {
                return ;
            }
             
            //MsgBox.Show(this,"Error. One break spans multiple dates.");
            //calc time for the one break
            totalOne = breakList[b].TimeDisplayed2 - breakList[b].TimeDisplayed1;
            //calc daily total
            if (previousDate.Date != breakList[b].TimeDisplayed1.Date)
            {
                //if date changed, this is the first pair of the day
                totalToday = TimeSpan.Zero;
                //new day
                previousDate = breakList[b].TimeDisplayed1.Date;
            }
             
            //for the next loop
            totalToday += totalOne;
            //decide if breaks for the day went over 30 min.
            if (totalToday > TimeSpan.FromMinutes(31))
            {
                //31 to prevent silly fractions less than 1.
                breakList[b].AdjustAuto = -(totalToday - TimeSpan.FromMinutes(30));
                ClockEvents.Update(breakList[b]);
                totalToday = TimeSpan.FromMinutes(30);
            }
             
        }
    }

    //reset to 30.  Therefore, any additional breaks will be wholly adjustments.
    //end breaklist
    /**
    * Calculates weekly overtime and inserts TimeAdjustments accordingly.
    */
    public static void calculateWeeklyOvertime_Old(Employee EmployeeCur, DateTime StartDate, DateTime StopDate) throws Exception {
        List<TimeAdjust> TimeAdjustList = TimeAdjusts.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate);
        List<ClockEvent> ClockEventList = ClockEvents.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate,false);
        for (int i = 0;i < TimeAdjustList.Count;i++)
        {
            //first, delete all existing overtime entries
            if (TimeAdjustList[i].OTimeHours == TimeSpan.Zero)
            {
                continue;
            }
             
            if (!TimeAdjustList[i].IsAuto)
            {
                continue;
            }
             
            TimeAdjusts.Delete(TimeAdjustList[i]);
        }
        //refresh list after it has been cleaned up.
        TimeAdjustList = TimeAdjusts.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate);
        ArrayList mergedAL = new ArrayList();
        for (Object __dummyForeachVar3 : ClockEventList)
        {
            ClockEvent clockEvent = (ClockEvent)__dummyForeachVar3;
            mergedAL.Add(clockEvent);
        }
        for (Object __dummyForeachVar4 : TimeAdjustList)
        {
            TimeAdjust timeAdjust = (TimeAdjust)__dummyForeachVar4;
            mergedAL.Add(timeAdjust);
        }
        //then, fill grid
        Calendar cal = CultureInfo.CurrentCulture.Calendar;
        CalendarWeekRule rule = CalendarWeekRule.FirstFullWeek;
        //CultureInfo.CurrentCulture.DateTimeFormat.CalendarWeekRule;
        //rule=CalendarWeekRule.FirstFullWeek;//CalendarWeekRule is an Enum. For these calculations, we want to use FirstFullWeek, not FirstDay;
        List<TimeSpan> WeeklyTotals = new List<TimeSpan>();
        WeeklyTotals = fillWeeklyTotalsHelper(true,EmployeeCur,mergedAL);
        for (int i = 0;i < mergedAL.Count;i++)
        {
            //loop through all rows
            //ignore rows that aren't weekly totals
            //if not the last row
            //if the next row has the same week as this row
            //Default is 0-Sunday
            if (i < mergedAL.Count - 1 && cal.GetWeekOfYear(getDateForRowHelper(mergedAL[i + 1]), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) == cal.GetWeekOfYear(getDateForRowHelper(mergedAL[i]), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
            {
                continue;
            }
             
            if (WeeklyTotals[i] <= TimeSpan.FromHours(40))
            {
                continue;
            }
             
            //found a weekly total over 40 hours
            TimeAdjust adjust = new TimeAdjust();
            adjust.IsAuto = true;
            adjust.EmployeeNum = EmployeeCur.EmployeeNum;
            adjust.TimeEntry = getDateForRowHelper(mergedAL[i]).AddHours(20);
            //puts it at 8pm on the same day.
            adjust.OTimeHours = WeeklyTotals[i] - TimeSpan.FromHours(40);
            adjust.RegHours = -adjust.OTimeHours;
            TimeAdjusts.insert(adjust);
        }
    }

    /**
    * Calculates weekly overtime and inserts TimeAdjustments accordingly.
    */
    public static void calculateWeeklyOvertime(Employee EmployeeCur, DateTime StartDate, DateTime StopDate) throws Exception {
        List<ClockEvent> listClockEvent = new List<ClockEvent>();
        List<TimeAdjust> listTimeAdjust = new List<TimeAdjust>();
        String errors = "";
        String clockErrors = "";
        String timeAdjustErrors = "";
        try
        {
            //Fill lists and catch validation error messages------------------------------------------------------------------------------------------------------------
            listClockEvent = ClockEvents.getValidList(EmployeeCur.EmployeeNum,StartDate,StopDate,false);
        }
        catch (Exception ex)
        {
            clockErrors += ex.Message;
        }

        try
        {
            listTimeAdjust = TimeAdjusts.getValidList(EmployeeCur.EmployeeNum,StartDate,StopDate);
        }
        catch (Exception ex)
        {
            timeAdjustErrors += ex.Message;
        }

        //Report Errors---------------------------------------------------------------------------------------------------------------------------------------------
        errors = clockErrors + timeAdjustErrors;
        if (!StringSupport.equals(errors, ""))
        {
            throw new Exception(Employees.getNameFL(EmployeeCur) + " has the following errors:\r\n" + errors);
        }
         
        for (int i = 0;i < listTimeAdjust.Count;i++)
        {
            //first, delete all existing non manual overtime entries
            if (listTimeAdjust[i].IsAuto)
            {
                TimeAdjusts.Delete(listTimeAdjust[i]);
            }
             
        }
        //refresh list after it has been cleaned up.
        listTimeAdjust = TimeAdjusts.refresh(EmployeeCur.EmployeeNum,StartDate,StopDate);
        ArrayList mergedAL = new ArrayList();
        for (Object __dummyForeachVar5 : listClockEvent)
        {
            ClockEvent clockEvent = (ClockEvent)__dummyForeachVar5;
            mergedAL.Add(clockEvent);
        }
        for (Object __dummyForeachVar6 : listTimeAdjust)
        {
            TimeAdjust timeAdjust = (TimeAdjust)__dummyForeachVar6;
            mergedAL.Add(timeAdjust);
        }
        //then, fill grid
        Calendar cal = CultureInfo.CurrentCulture.Calendar;
        CalendarWeekRule rule = CalendarWeekRule.FirstFullWeek;
        //CultureInfo.CurrentCulture.DateTimeFormat.CalendarWeekRule;
        //rule=CalendarWeekRule.FirstFullWeek;//CalendarWeekRule is an Enum. For these calculations, we want to use FirstFullWeek, not FirstDay;
        List<TimeSpan> WeeklyTotals = new List<TimeSpan>();
        WeeklyTotals = fillWeeklyTotalsHelper(true,EmployeeCur,mergedAL);
        for (int i = 0;i < mergedAL.Count;i++)
        {
            //loop through all rows
            //ignore rows that aren't weekly totals
            //if not the last row
            //if the next row has the same week as this row
            //Default is 0-Sunday
            if (i < mergedAL.Count - 1 && cal.GetWeekOfYear(getDateForRowHelper(mergedAL[i + 1]), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) == cal.GetWeekOfYear(getDateForRowHelper(mergedAL[i]), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
            {
                continue;
            }
             
            if (WeeklyTotals[i] <= TimeSpan.FromHours(40))
            {
                continue;
            }
             
            //found a weekly total over 40 hours
            TimeAdjust adjust = new TimeAdjust();
            adjust.IsAuto = true;
            adjust.EmployeeNum = EmployeeCur.EmployeeNum;
            adjust.TimeEntry = getDateForRowHelper(mergedAL[i]).AddHours(20);
            //puts it at 8pm on the same day.
            adjust.OTimeHours = WeeklyTotals[i] - TimeSpan.FromHours(40);
            adjust.RegHours = -adjust.OTimeHours;
            TimeAdjusts.insert(adjust);
        }
    }

    /**
    * This was originally analogous to the FormTimeCard.FillGrid(), before this logic was moved to the business layer.
    */
    private static List<TimeSpan> fillWeeklyTotalsHelper(boolean fromDB, Employee EmployeeCur, ArrayList mergedAL) throws Exception {
        List<TimeSpan> retVal = new List<TimeSpan>();
        TimeSpan[] WeeklyTotals = new TimeSpan[mergedAL.Count];
        TimeSpan alteredSpan = new TimeSpan(0);
        //used to display altered times
        TimeSpan oneSpan = new TimeSpan(0);
        //used to sum one pair of clock-in/clock-out
        TimeSpan oneAdj = new TimeSpan();
        TimeSpan oneOT = new TimeSpan();
        TimeSpan daySpan = new TimeSpan(0);
        //used for daily totals.
        TimeSpan weekSpan = new TimeSpan(0);
        //used for weekly totals.
        if (mergedAL.Count > 0)
        {
            weekSpan = ClockEvents.getWeekTotal(EmployeeCur.EmployeeNum,getDateForRowHelper(mergedAL[0]));
        }
         
        TimeSpan periodSpan = new TimeSpan(0);
        //used to add up totals for entire page.
        TimeSpan otspan = new TimeSpan(0);
        //overtime for the entire period
        Calendar cal = CultureInfo.CurrentCulture.Calendar;
        CalendarWeekRule rule = CalendarWeekRule.FirstFullWeek;
        // CultureInfo.CurrentCulture.DateTimeFormat.CalendarWeekRule;
        DateTime curDate = DateTime.MinValue;
        DateTime previousDate = DateTime.MinValue;
        Type type = new Type();
        ClockEvent clock;
        TimeAdjust adjust;
        for (int i = 0;i < mergedAL.Count;i++)
        {
            type = mergedAL[i].GetType();
            previousDate = curDate;
            //clock event row---------------------------------------------------------------------------------------------
            if (type == ClockEvent.class)
            {
                clock = (ClockEvent)mergedAL[i];
                curDate = clock.TimeDisplayed1.Date;
                if (clock.TimeDisplayed2.Year < 1880)
                {
                }
                else
                {
                    oneSpan = clock.TimeDisplayed2 - clock.TimeDisplayed1;
                    daySpan += oneSpan;
                    weekSpan += oneSpan;
                    periodSpan += oneSpan;
                } 
                //Adjust---------------------------------
                oneAdj = TimeSpan.Zero;
                if (clock.AdjustIsOverridden)
                {
                    oneAdj += clock.Adjust;
                }
                else
                {
                    oneAdj += clock.AdjustAuto;
                } 
                //typically zero
                daySpan += oneAdj;
                weekSpan += oneAdj;
                periodSpan += oneAdj;
                //Overtime------------------------------
                oneOT = TimeSpan.Zero;
                if (clock.OTimeHours != TimeSpan.FromHours(-1))
                {
                    //overridden
                    oneOT = clock.OTimeHours;
                }
                else
                {
                    oneOT = clock.OTimeAuto;
                } 
                //typically zero
                otspan += oneOT;
                daySpan -= oneOT;
                weekSpan -= oneOT;
                periodSpan -= oneOT;
                //Daily-----------------------------------
                //if this is the last entry for a given date
                //if this is the last row
                if (i == mergedAL.Count - 1 || getDateForRowHelper(mergedAL[i + 1]) != curDate)
                {
                    //or the next row is a different date
                    daySpan = new TimeSpan(0);
                }
                else
                {
                } 
                //not the last entry for the day
                //Weekly-------------------------------------
                WeeklyTotals[i] = weekSpan;
                //if this is the last entry for a given week
                //if this is the last row
                //or the next row has a
                if (i == mergedAL.Count - 1 || cal.GetWeekOfYear(getDateForRowHelper(mergedAL[i + 1]), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) != cal.GetWeekOfYear(clock.TimeDisplayed1.Date, rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
                {
                    //different week of year
                    weekSpan = new TimeSpan(0);
                }
                 
            }
            else //adjustment row--------------------------------------------------------------------------------------
            if (type == TimeAdjust.class)
            {
                adjust = (TimeAdjust)mergedAL[i];
                curDate = adjust.TimeEntry.Date;
                //Adjust------------------------------
                daySpan += adjust.RegHours;
                //might be negative
                weekSpan += adjust.RegHours;
                periodSpan += adjust.RegHours;
                //Overtime------------------------------
                otspan += adjust.OTimeHours;
                //Daily-----------------------------------
                //if this is the last entry for a given date
                //if this is the last row
                if (i == mergedAL.Count - 1 || getDateForRowHelper(mergedAL[i + 1]) != curDate)
                {
                    //or the next row is a different date
                    daySpan = new TimeSpan(0);
                }
                 
                //Weekly-------------------------------------
                WeeklyTotals[i] = weekSpan;
                //if this is the last entry for a given week
                //if this is the last row
                //or the next row has a
                if (i == mergedAL.Count - 1 || cal.GetWeekOfYear(getDateForRowHelper(mergedAL[i + 1]), rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)) != cal.GetWeekOfYear(adjust.TimeEntry.Date, rule, (DayOfWeek)PrefC.getInt(PrefName.TimeCardOvertimeFirstDayOfWeek)))
                {
                    //different week of year
                    weekSpan = new TimeSpan(0);
                }
                 
            }
              
        }
        for (Object __dummyForeachVar7 : WeeklyTotals)
        {
            TimeSpan week = (TimeSpan)__dummyForeachVar7;
            retVal.Add(week);
        }
        return retVal;
    }

    private static DateTime getDateForRowHelper(Object timeEvent) throws Exception {
        if (timeEvent.GetType() == ClockEvent.class)
        {
            return ((ClockEvent)timeEvent).TimeDisplayed1.Date;
        }
        else if (timeEvent.GetType() == TimeAdjust.class)
        {
            return ((TimeAdjust)timeEvent).TimeEntry.Date;
        }
          
        return DateTime.MinValue;
    }

}


