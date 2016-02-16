//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.ApptSearchOperatorySchedule;
import OpenDental.ApptSearchProviderSchedule;
import OpenDental.FormProcEdit;
import OpenDental.Lan;
import OpenDental.PIn;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.ApptStatus;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.Fees;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.OperatoryC;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProcApptColor;
import OpenDentBusiness.ProcApptColors;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProcStat;
import OpenDentBusiness.Programs;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Recall;
import OpenDentBusiness.Recalls;
import OpenDentBusiness.RecallTypeC;
import OpenDentBusiness.RecallTypes;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleOp;
import OpenDentBusiness.ScheduleOps;
import OpenDentBusiness.Schedules;
import OpenDentBusiness.SearchBehaviorCriteria;
import OpenDentBusiness.UI.ApptDrawing;
import OpenDentBusiness.UI.ApptSingleDrawing;

public class AppointmentL   
{
    /**
    * The date currently selected in the appointment module.
    */
    public static DateTime DateSelected = new DateTime();
    /**
    * copy of function above for testing purposes.
    */
    public static List<DateTime> getSearchResults(long aptNum, DateTime afterDate, List<long> providerNums, int resultCount, TimeSpan beforeTime, TimeSpan afterTime) throws Exception {
        if (beforeTime == TimeSpan.FromSeconds(0))
        {
            //if they didn't set a before time, set it to a large timespan so that we can use the same logic for checking appointment times.
            beforeTime = TimeSpan.FromHours(25);
        }
         
        //bigger than any time of day.
        SearchBehaviorCriteria SearchType = SearchBehaviorCriteria.values()[PrefC.getInt(PrefName.AppointmentSearchBehavior)];
        List<DateTime> retVal = new List<DateTime>();
        DateTime dayEvaluating = afterDate.AddDays(1);
        Appointment appointmentToAdd = Appointments.getOneApt(aptNum);
        List<DateTime> potentialProvAppointmentTime = new List<DateTime>();
        List<DateTime> potentialOpAppointmentTime = new List<DateTime>();
        List<Operatory> opsListAll = OperatoryC.getListt();
        //all operatory Numbers
        List<Schedule> scheduleListAll = Schedules.getTwoYearPeriod(dayEvaluating);
        // Schedules for the given day.
        List<Appointment> appointmentListAll = Appointments.GetForPeriodList(dayEvaluating, dayEvaluating.AddYears(2));
        List<ScheduleOp> schedOpListAll = ScheduleOps.GetForSchedList(scheduleListAll);
        List<ApptSearchProviderSchedule> provScheds = new List<ApptSearchProviderSchedule>();
        //Provider Bar, ProviderSched Bar, Date and Provider
        List<ApptSearchOperatorySchedule> operatrorySchedules = new List<ApptSearchOperatorySchedule>();
        //filtered based on SearchType
        List<long> operatoryNums = new List<long>();
        for (int i = 0;i < opsListAll.Count;i++)
        {
            //more usefull than a list of operatories.
            operatoryNums.Add(opsListAll[i].OperatoryNum);
        }
        while (retVal.Count < resultCount && dayEvaluating < afterDate.AddYears(2))
        {
            potentialOpAppointmentTime = new List<DateTime>();
            //clear or create
            //Providers-------------------------------------------------------------------------------------------------------------------------------------
            potentialProvAppointmentTime = new List<DateTime>();
            //clear or create
            provScheds = GetForProvidersAndDate(providerNums, dayEvaluating, scheduleListAll, appointmentListAll);
            for (int i = 0;i < provScheds.Count;i++)
            {
                for (int j = 0;j < 288;j++)
                {
                    //search every 5 minute increment per day
                    if (j + appointmentToAdd.Pattern.Length > 288)
                    {
                        break;
                    }
                     
                    if (potentialProvAppointmentTime.Contains(dayEvaluating.AddMinutes(j * 5)))
                    {
                        continue;
                    }
                     
                    boolean addDateTime = true;
                    for (int k = 0;k < appointmentToAdd.Pattern.Length;k++)
                    {
                        if ((provScheds[i].ProvBar[j + k] == false && appointmentToAdd.Pattern[k] == 'X') || provScheds[i].ProvSchedule[j + k] == false)
                        {
                            addDateTime = false;
                            break;
                        }
                         
                    }
                    if (addDateTime)
                    {
                        potentialProvAppointmentTime.Add(dayEvaluating.AddMinutes(j * 5));
                    }
                     
                }
            }
            if (SearchType == SearchBehaviorCriteria.ProviderTimeOperatory)
            {
                //Handle Operatories here----------------------------------------------------------------------------
                operatrorySchedules = GetAllForDate(dayEvaluating, scheduleListAll, appointmentListAll, schedOpListAll, operatoryNums, providerNums);
                potentialOpAppointmentTime = new List<DateTime>();
                for (int i = 0;i < 288;i++)
                {
                    //create or clear
                    //for(int j=0;j<operatrorySchedules.Count;j++) {//for each operatory
                    //search every 5 minute increment per day
                    if (i + appointmentToAdd.Pattern.Length > 288)
                    {
                        break;
                    }
                     
                    for (int j = 0;j < operatrorySchedules.Count;j++)
                    {
                        //skip if appointment would span across midnight
                        //for each operatory
                        //if(potentialOpAppointmentTime.Contains(dayEvaluating.AddMinutes(i*5))) {//skip if we already have this dateTime
                        //  break;
                        //}
                        boolean addDateTime = true;
                        for (int k = 0;k < appointmentToAdd.Pattern.Length;k++)
                        {
                            //check appointment against operatories
                            if (operatrorySchedules[j].OperatorySched[i + k] == false)
                            {
                                addDateTime = false;
                                break;
                            }
                             
                        }
                        if (!addDateTime)
                        {
                            break;
                        }
                         
                        if (addDateTime)
                        {
                            // && SearchType==SearchBehaviorCriteria.ProviderTimeOperatory) {//check appointment against providers available for the given operatory
                            boolean provAvail = false;
                            for (int k = 0;k < providerNums.Count;k++)
                            {
                                if (!operatrorySchedules[j].ProviderNums.Contains(providerNums[k]))
                                {
                                    continue;
                                }
                                 
                                provAvail = true;
                                for (int m = 0;m < appointmentToAdd.Pattern.Length;m++)
                                {
                                    if ((provScheds[k].ProvBar[i + m] == false && appointmentToAdd.Pattern[m] == 'X') || provScheds[k].ProvSchedule[i + m] == false)
                                    {
                                        //if provider bar time slot
                                        provAvail = false;
                                        break;
                                    }
                                     
                                }
                                if (provAvail)
                                {
                                    break;
                                }
                                 
                            }
                            //found a provider with an available operatory
                            if (provAvail && addDateTime)
                            {
                                //operatory and provider are available
                                potentialOpAppointmentTime.Add(dayEvaluating.AddMinutes(i * 5));
                            }
                             
                        }
                        else
                        {
                            //not using SearchBehaviorCriteria.ProviderTimeOperatory
                            if (addDateTime)
                            {
                                potentialOpAppointmentTime.Add(dayEvaluating.AddMinutes(i * 5));
                            }
                             
                        } 
                    }
                }
            }
             
            //At this point the potentialOpAppointmentTime is already filtered and only contains appointment times that match both provider time and operatory time.
            switch(SearchType)
            {
                case ProviderTime: 
                    for (int i = 0;i < potentialProvAppointmentTime.Count;i++)
                    {
                        //Add based on provider bars
                        if (potentialProvAppointmentTime[i].TimeOfDay > beforeTime || potentialProvAppointmentTime[i].TimeOfDay < afterTime)
                        {
                            continue;
                        }
                         
                        retVal.Add(potentialProvAppointmentTime[i]);
                        break;
                    }
                    break;
                case ProviderTimeOperatory: 
                    for (int i = 0;i < potentialOpAppointmentTime.Count;i++)
                    {
                        //add one for this day
                        //stop looking through potential times for today.
                        //add based on provider bar and operatory bar
                        if (potentialOpAppointmentTime[i].TimeOfDay > beforeTime || potentialOpAppointmentTime[i].TimeOfDay < afterTime)
                        {
                            continue;
                        }
                         
                        retVal.Add(potentialOpAppointmentTime[i]);
                        break;
                    }
                    break;
            
            }
            //add one for this day
            //stop looking through potential times for today.
            dayEvaluating = dayEvaluating.AddDays(1);
        }
        return retVal;
    }

    /**
    * Uses the input parameters to construct a List<ProviderSchedule>. It is written to reduce the number of queries to the database.
    *  @param ProviderNums PrimaryKeys to Provider.
    *  @param ScheduleDate The date to construct the schedule for.
    *  @param ScheduleList A List of Schedules containing all of the schedules for the given day, or possibly more.
    * Intended to be all schedules between search start date and search start date plus 2 years. This is to reduce queries to DB.
    *  @param AppointmentList A List of Appointments containing all of the schedules for the given day, or possibly more.
    * Intended to be all Appointments between search start date and search start date plus 2 years. This is to reduce queries to DB.
    *  @param ScheduleOpList A List of all ScheduleOps .
    * Intended to be all Appointments between search start date and search start date plus 2 years. This is to reduce queries to DB.
    *  @param OperatoryList A List of all operatories. This is to reduce queries to DB.
    */
    private static List<ApptSearchProviderSchedule> getForProvidersAndDate(List<long> ProviderNums, DateTime ScheduleDate, List<Schedule> ScheduleList, List<Appointment> AppointmentList) throws Exception {
        //Not working properly when scheduled but no ops are set.
        List<ApptSearchProviderSchedule> retVal = new List<ApptSearchProviderSchedule>();
        ScheduleDate = ScheduleDate.Date;
        for (int i = 0;i < ProviderNums.Count;i++)
        {
            retVal.Add(new ApptSearchProviderSchedule());
            retVal[i].ProviderNum = ProviderNums[i];
            retVal[i].SchedDate = ScheduleDate;
        }
        for (int s = 0;s < ScheduleList.Count;s++)
        {
            if (ScheduleList[s].SchedDate.Date != ScheduleDate)
            {
                continue;
            }
             
            //ignore schedules for different dates.
            if (ProviderNums.Contains(ScheduleList[s].ProvNum))
            {
                //schedule applies to one of the selected providers
                int indexOfProvider = ProviderNums.IndexOf(ScheduleList[s].ProvNum);
                //cache the provider index
                int scheduleStartBlock = (int)ScheduleList[s].StartTime.TotalMinutes / 5;
                //cache the start time of the schedule
                int scheduleLengthInBlocks = (int)(ScheduleList[s].StopTime - ScheduleList[s].StartTime).TotalMinutes / 5;
                for (int i = 0;i < scheduleLengthInBlocks;i++)
                {
                    //cache the length of the schedule
                    retVal[indexOfProvider].ProvBar[scheduleStartBlock + i] = true;
                    //provider may have an appointment here
                    retVal[indexOfProvider].ProvSchedule[scheduleStartBlock + i] = true;
                }
            }
             
        }
        for (int a = 0;a < AppointmentList.Count;a++)
        {
            //provider is scheduled today
            if (AppointmentList[a].AptDateTime.Date != ScheduleDate)
            {
                continue;
            }
             
            if (!AppointmentList[a].IsHygiene && ProviderNums.Contains(AppointmentList[a].ProvNum))
            {
                //Not hygiene Modify provider bar based on ProvNum
                int indexOfProvider = ProviderNums.IndexOf(AppointmentList[a].ProvNum);
                int appointmentCurStartBlock = (int)AppointmentList[a].AptDateTime.TimeOfDay.TotalMinutes / 5;
                for (int i = 0;i < AppointmentList[a].Pattern.Length;i++)
                {
                    if (AppointmentList[a].Pattern[i] == 'X')
                    {
                        retVal[indexOfProvider].ProvBar[appointmentCurStartBlock + i] = false;
                    }
                     
                }
            }
            else if (AppointmentList[a].IsHygiene && ProviderNums.Contains(AppointmentList[a].ProvHyg))
            {
                //Modify provider bar based on ProvHyg
                int indexOfProvider = ProviderNums.IndexOf(AppointmentList[a].ProvHyg);
                int appointmentStartBlock = (int)AppointmentList[a].AptDateTime.TimeOfDay.TotalMinutes / 5;
                for (int i = 0;i < AppointmentList[a].Pattern.Length;i++)
                {
                    if (AppointmentList[a].Pattern[i] == 'X')
                    {
                        retVal[indexOfProvider].ProvBar[appointmentStartBlock + i] = false;
                    }
                     
                }
            }
              
        }
        return retVal;
    }

    /**
    * Uses Inputs to construct a List<OperatorySchedule>. It is written to reduce the number of queries to the database.
    */
    private static List<ApptSearchOperatorySchedule> getAllForDate(DateTime ScheduleDate, List<Schedule> ScheduleList, List<Appointment> AppointmentList, List<ScheduleOp> ScheduleOpList, List<long> OperatoryNums, List<long> ProviderNums) throws Exception {
        List<ApptSearchOperatorySchedule> retVal = new List<ApptSearchOperatorySchedule>();
        List<ApptSearchOperatorySchedule> opSchedListAll = new List<ApptSearchOperatorySchedule>();
        List<Operatory> opsListAll = OperatoryC.getListt();
        opsListAll.Sort(compareOpsByOpNum);
        //sort by Operatory Num Ascending
        OperatoryNums.Sort();
        //Sort by operatory Num Ascending to match
        List<List<long>> opsProvPerSchedules = new List<List<long>>();
        //opsProvPerSchedules[<opIndex>][ProviderNums] based solely on schedules, lists of providers 'allowed' to work in the given operatory
        List<List<long>> opsProvPerOperatories = new List<List<long>>();
        //opsProvPerSchedules[<opIndex>][ProviderNums] based solely on operatories, lists of providers 'allowed' to work in the given operatory
        List<List<long>> opsProvIntersect = new List<List<long>>();
        ////opsProvPerSchedules[<opIndex>][ProviderNums] based on the intersection of the two data sets above.
        ScheduleDate = ScheduleDate.Date;
        for (int i = 0;i < OperatoryNums.Count;i++)
        {
            //remove time component
            opSchedListAll.Add(new ApptSearchOperatorySchedule());
            opSchedListAll[i].SchedDate = ScheduleDate;
            opSchedListAll[i].ProviderNums = new List<long>();
            opSchedListAll[i].OperatoryNum = OperatoryNums[i];
            opSchedListAll[i].OperatorySched = new boolean[288];
            for (int j = 0;j < 288;j++)
            {
                opSchedListAll[i].OperatorySched[j] = true;
            }
            //Set entire operatory schedule to true. True=available.
            opsProvPerSchedules.Add(new List<long>());
            opsProvPerOperatories.Add(new List<long>());
            opsProvIntersect.Add(new List<long>());
        }
        for (int i = 0;i < ScheduleList.Count;i++)
        {
            //use this loop to fill opsProvPerSchedules
            if (ScheduleList[i].SchedDate.Date != ScheduleDate)
            {
                continue;
            }
             
            //only schedules for the applicable day.
            int schedopsforschedule = 0;
            for (int j = 0;j < ScheduleOpList.Count;j++)
            {
                if (ScheduleOpList[j].ScheduleNum != ScheduleList[i].ScheduleNum)
                {
                    continue;
                }
                 
                //ScheduleOp does not apply to this schedule
                schedopsforschedule++;
                int indexofop = OperatoryNums.IndexOf(ScheduleOpList[j].OperatoryNum);
                //cache to increase speed
                if (opsProvPerSchedules[indexofop].Contains(ScheduleList[i].ProvNum))
                {
                    continue;
                }
                 
                //only add ones that have not been added.
                opsProvPerSchedules[indexofop].Add(ScheduleList[i].ProvNum);
            }
            if (schedopsforschedule == 0)
            {
                for (int k = 0;k < opsProvPerSchedules.Count;k++)
                {
                    //Provider is scheduled to work, but not limited to any specific operatory so add provider num to all operatories in opsProvPerSchedules
                    if (opsProvPerSchedules[k].Contains(ScheduleList[i].ProvNum))
                    {
                        continue;
                    }
                     
                    opsProvPerSchedules[k].Add(ScheduleList[i].ProvNum);
                }
            }
             
        }
        for (int i = 0;i < opsListAll.Count;i++)
        {
            //use this loop to fill opsProvPerOperatories
            opsProvPerOperatories[i].Add(opsListAll[i].ProvDentist);
            opsProvPerOperatories[i].Add(opsListAll[i].ProvHygienist);
        }
        for (int i = 0;i < opsProvPerSchedules.Count;i++)
        {
            for (int j = 0;j < opsProvPerSchedules[i].Count;j++)
            {
                //Use this loop to fill opsProvIntersect by finding matching pairs in opsProvPerSchedules and opsProvPerOperatories
                if (opsProvPerOperatories[i][0] == 0 && opsProvPerOperatories[i][1] == 0)
                {
                    //There are no providers set for this operatory, use all the provider nums from the schedules.
                    opsProvIntersect[i].Add(opsProvPerSchedules[i][j]);
                    opSchedListAll[i].ProviderNums.Add(opsProvPerSchedules[i][j]);
                    continue;
                }
                 
                if (opsProvPerSchedules[i][j] == 0)
                {
                    continue;
                }
                 
                //just in case a non valid prov num got through.
                if (opsProvPerOperatories[i].Contains(opsProvPerSchedules[i][j]))
                {
                    //if a provider was assigned and matches
                    opsProvIntersect[i].Add(opsProvPerSchedules[i][j]);
                    opSchedListAll[i].ProviderNums.Add(opsProvPerSchedules[i][j]);
                }
                 
            }
        }
        for (int i = 0;i < AppointmentList.Count;i++)
        {
            //use this loop to set all operatory schedules.
            if (AppointmentList[i].AptDateTime.Date != ScheduleDate)
            {
                continue;
            }
             
            //skip appointments that do not apply to this date
            int indexofop = OperatoryNums.IndexOf(AppointmentList[i].Op);
            int aptstartindex = (int)AppointmentList[i].AptDateTime.TimeOfDay.TotalMinutes / 5;
            for (int j = 0;j < AppointmentList[i].Pattern.Length;j++)
            {
                //make unavailable all blocks of time during this appointment.
                opSchedListAll[indexofop].OperatorySched[aptstartindex + j] = false;
            }
        }
        for (int i = 0;i < opSchedListAll.Count;i++)
        {
            //Set time block to false, meaning something is scheduled here.
            //Filter out operatory schedules for ops that our selected providers don't work in.
            if (retVal.Contains(opSchedListAll[i]))
            {
                continue;
            }
             
            for (int j = 0;j < opSchedListAll[i].ProviderNums.Count;j++)
            {
                if (ProviderNums.Contains(opSchedListAll[i].ProviderNums[j]))
                {
                    retVal.Add(opSchedListAll[i]);
                    break;
                }
                 
            }
        }
        return retVal;
    }

    //For Future Use When adding third search behavior:
    //if((SearchBehaviorCriteria)PrefC.GetInt(PrefName.AppointmentSearchBehavior)==SearchBehaviorCriteria.OperatoryOnly) {
    //  return opSchedListAll;
    //}
    private static int compareOpsByOpNum(Operatory op1, Operatory op2) throws Exception {
        return (int)op1.OperatoryNum - (int)op2.OperatoryNum;
    }

    /*
    		///<summary>Only used in GetSearchResults.  All times between start and stop get set to true in provBarSched.</summary>
    		private static void SetProvBarSched(ref bool[] provBarSched,TimeSpan timeStart,TimeSpan timeStop){
    			int startI=GetProvBarIndex(timeStart);
    			int stopI=GetProvBarIndex(timeStop);
    			for(int i=startI;i<=stopI;i++){
    				provBarSched[i]=true;
    			}
    		}
    		private static int GetProvBarIndex(TimeSpan time) {
    			return (int)(((double)time.Hours*(double)60/(double)PrefC.GetLong(PrefName.AppointmentTimeIncrement)//aptTimeIncr=minutesPerIncr
    				+(double)time.Minutes/(double)PrefC.GetLong(PrefName.AppointmentTimeIncrement))
    				*(double)ApptDrawing.LineH*ApptDrawing.RowsPerIncr)
    				/ApptDrawing.LineH;//rounds down
    		}*/
    /**
    * Used by UI when it needs a recall appointment placed on the pinboard ready to schedule.  This method creates the appointment and attaches all appropriate procedures.  It's up to the calling class to then place the appointment on the pinboard.  If the appointment doesn't get scheduled, it's important to delete it.  If a recallNum is not 0 or -1, then it will create an appt of that recalltype.
    */
    public static Appointment createRecallApt(Patient patCur, List<Procedure> procList, List<InsPlan> planList, long recallNum, List<InsSub> subList) throws Exception {
        List<Recall> recallList = Recalls.getList(patCur.PatNum);
        Recall recallCur = null;
        if (recallNum > 0)
        {
            recallCur = Recalls.getRecall(recallNum);
        }
        else
        {
            for (int i = 0;i < recallList.Count;i++)
            {
                if (recallList[i].RecallTypeNum == RecallTypes.getPerioType() || recallList[i].RecallTypeNum == RecallTypes.getProphyType())
                {
                    if (!recallList[i].IsDisabled)
                    {
                        recallCur = recallList[i];
                    }
                     
                    break;
                }
                 
            }
        } 
        if (recallCur == null)
        {
            throw new ApplicationException(Lan.g("AppointmentL","No special type recall is due."));
        }
         
        // || recallCur.DateDue.Year<1880){
        //Typically never happens because everyone has a recall.  However, it can happen when patients have custom recalls due
        if (recallCur.DateScheduled.Date > DateTime.Today)
        {
            throw new ApplicationException(Lan.g("AppointmentL","Recall has already been scheduled for ") + recallCur.DateScheduled.ToShortDateString());
        }
         
        Appointment AptCur = new Appointment();
        AptCur.PatNum = patCur.PatNum;
        AptCur.AptStatus = ApptStatus.UnschedList;
        //In all places where this is used, the unsched status with no aptDateTime will cause the appt to be deleted when the pinboard is cleared.
        if (patCur.PriProv == 0)
        {
            AptCur.ProvNum = PrefC.getLong(PrefName.PracticeDefaultProv);
        }
        else
        {
            AptCur.ProvNum = patCur.PriProv;
        } 
        AptCur.ProvHyg = patCur.SecProv;
        if (AptCur.ProvHyg != 0)
        {
            AptCur.IsHygiene = true;
        }
         
        AptCur.ClinicNum = patCur.ClinicNum;
        //whether perio or prophy:
        List<String> procs = RecallTypes.getProcs(recallCur.RecallTypeNum);
        String recallPattern = RecallTypes.getTimePattern(recallCur.RecallTypeNum);
        if (RecallTypes.isSpecialRecallType(recallCur.RecallTypeNum) && patCur.Birthdate.AddYears(PrefC.getInt(PrefName.RecallAgeAdult)) > ((recallCur.DateDue > DateTime.Today) ? recallCur.DateDue : DateTime.Today))
        {
            for (int i = 0;i < RecallTypeC.getListt().Count;i++)
            {
                //For example, if pt's 12th birthday falls after recall date.
                if (RecallTypeC.getListt()[i].RecallTypeNum == RecallTypes.getChildProphyType())
                {
                    List<String> childprocs = RecallTypes.GetProcs(RecallTypeC.getListt()[i].RecallTypeNum);
                    if (childprocs.Count > 0)
                    {
                        procs = childprocs;
                    }
                     
                    //overrides adult procs.
                    String childpattern = RecallTypes.GetTimePattern(RecallTypeC.getListt()[i].RecallTypeNum);
                    if (!StringSupport.equals(childpattern, ""))
                    {
                        recallPattern = childpattern;
                    }
                     
                }
                 
            }
        }
         
        //overrides adult pattern.
        //convert time pattern to 5 minute increment
        StringBuilder savePattern = new StringBuilder();
        for (int i = 0;i < recallPattern.Length;i++)
        {
            savePattern.Append(recallPattern.Substring(i, 1));
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 10)
            {
                savePattern.Append(recallPattern.Substring(i, 1));
            }
             
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 15)
            {
                savePattern.Append(recallPattern.Substring(i, 1));
                savePattern.Append(recallPattern.Substring(i, 1));
            }
             
        }
        if (StringSupport.equals(savePattern.ToString(), ""))
        {
            if (PrefC.getLong(PrefName.AppointmentTimeIncrement) == 15)
            {
                savePattern.Append("///XXX///");
            }
            else
            {
                savePattern.Append("//XX//");
            } 
        }
         
        AptCur.Pattern = savePattern.ToString();
        //Add films------------------------------------------------------------------------------------------------------
        if (RecallTypes.isSpecialRecallType(recallCur.RecallTypeNum))
        {
            for (int i = 0;i < recallList.Count;i++)
            {
                //if this is a prophy or perio
                if (recallCur.RecallNum == recallList[i].RecallNum)
                {
                    continue;
                }
                 
                //already handled.
                if (recallList[i].IsDisabled)
                {
                    continue;
                }
                 
                if (recallList[i].DateDue.Year < 1880)
                {
                    continue;
                }
                 
                //if film due date is after prophy due date
                if (recallList[i].DateDue > recallCur.DateDue && recallList[i].DateDue > DateTime.Today)
                {
                    continue;
                }
                 
                //and not overdue
                //incomplete: exclude manual recall types
                procs.AddRange(RecallTypes.GetProcs(recallList[i].RecallTypeNum));
            }
        }
         
        AptCur.ProcDescript = "";
        AptCur.ProcsColored = "";
        for (int i = 0;i < procs.Count;i++)
        {
            String procDescOne = "";
            if (i > 0)
            {
                AptCur.ProcDescript += ", ";
            }
             
            procDescOne += ProcedureCodes.GetProcCode(procs[i]).AbbrDesc;
            AptCur.ProcDescript += procDescOne;
            //Color and previous date are determined by ProcApptColor object
            ProcApptColor pac = ProcApptColors.GetMatch(procs[i]);
            System.Drawing.Color pColor = System.Drawing.Color.Black;
            String prevDateString = "";
            if (pac != null)
            {
                pColor = pac.ColorText;
                if (pac.ShowPreviousDate)
                {
                    prevDateString = Procedures.getRecentProcDateString(AptCur.PatNum,AptCur.AptDateTime,pac.CodeRange);
                    if (!StringSupport.equals(prevDateString, ""))
                    {
                        prevDateString = " (" + prevDateString + ")";
                    }
                     
                }
                 
            }
             
            AptCur.ProcsColored += "<span color=\"" + pColor.ToArgb().ToString() + "\">" + procDescOne + prevDateString + "</span>";
        }
        AptCur.TimeLocked = PrefC.getBool(PrefName.AppointmentTimeIsLocked);
        Appointments.insert(AptCur);
        Procedure ProcCur;
        List<PatPlan> patPlanList = PatPlans.refresh(patCur.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlanList, subList);
        InsPlan priplan = null;
        InsSub prisub = null;
        if (patPlanList.Count > 0)
        {
            prisub = InsSubs.GetSub(patPlanList[0].InsSubNum, subList);
            priplan = InsPlans.GetPlan(prisub.PlanNum, planList);
        }
         
        double insfee = new double();
        double standardfee = new double();
        for (int i = 0;i < procs.Count;i++)
        {
            ProcCur = new Procedure();
            //this will be an insert
            //procnum
            ProcCur.PatNum = patCur.PatNum;
            ProcCur.AptNum = AptCur.AptNum;
            ProcCur.CodeNum = ProcedureCodes.GetCodeNum(procs[i]);
            ProcCur.ProcDate = DateTime.Now;
            ProcCur.DateTP = DateTime.Now;
            //Check if it's a medical procedure.
            boolean isMed = false;
            ProcCur.MedicalCode = ProcedureCodes.getProcCode(ProcCur.CodeNum).MedicalCode;
            if (ProcCur.MedicalCode != null && !StringSupport.equals(ProcCur.MedicalCode, ""))
            {
                isMed = true;
            }
             
            //Get fee schedule for medical or dental.
            long feeSch = new long();
            if (isMed)
            {
                feeSch = Fees.GetMedFeeSched(patCur, planList, patPlanList, subList);
            }
            else
            {
                feeSch = Fees.GetFeeSched(patCur, planList, patPlanList, subList);
            } 
            //Get the fee amount for medical or dental.
            if (PrefC.getBool(PrefName.MedicalFeeUsedForNewProcs) && isMed)
            {
                insfee = Fees.getAmount0(ProcedureCodes.getProcCode(ProcCur.MedicalCode).CodeNum,feeSch);
            }
            else
            {
                insfee = Fees.getAmount0(ProcCur.CodeNum,feeSch);
            } 
            if (priplan != null && StringSupport.equals(priplan.PlanType, "p"))
            {
                //PPO
                standardfee = Fees.getAmount0(ProcCur.CodeNum,Providers.getProv(Patients.getProvNum(patCur)).FeeSched);
                if (standardfee > insfee)
                {
                    ProcCur.ProcFee = standardfee;
                }
                else
                {
                    ProcCur.ProcFee = insfee;
                } 
            }
            else
            {
                ProcCur.ProcFee = insfee;
            } 
            //surf
            //toothnum
            //Procedures.Cur.ToothRange="";
            //ProcCur.NoBillIns=ProcedureCodes.GetProcCode(ProcCur.CodeNum).NoBillIns;
            //priority
            ProcCur.ProcStatus = ProcStat.TP;
            ProcCur.Note = "";
            //Procedures.Cur.PriEstim=
            //Procedures.Cur.SecEstim=
            //claimnum
            ProcCur.ProvNum = patCur.PriProv;
            //Procedures.Cur.Dx=
            ProcCur.ClinicNum = patCur.ClinicNum;
            //nextaptnum
            ProcCur.BaseUnits = ProcedureCodes.getProcCode(ProcCur.CodeNum).BaseUnits;
            ProcCur.DiagnosticCode = PrefC.getString(PrefName.ICD9DefaultForNewProcs);
            Procedures.insert(ProcCur);
            //no recall synch required
            Procedures.ComputeEstimates(ProcCur, patCur.PatNum, new List<ClaimProc>(), false, planList, patPlanList, benefitList, patCur.getAge(), subList);
            if (Programs.getUsingOrion())
            {
                FormProcEdit FormP = new FormProcEdit(ProcCur,patCur.copy(),Patients.getFamily(patCur.PatNum));
                FormP.IsNew = true;
                FormP.ShowDialog();
                if (FormP.DialogResult == DialogResult.Cancel)
                {
                    try
                    {
                        //any created claimprocs are automatically deleted from within procEdit window.
                        Procedures.delete(ProcCur.ProcNum);
                    }
                    catch (Exception ex)
                    {
                        //also deletes the claimprocs
                        MessageBox.Show(ex.Message);
                    }
                
                }
                else
                {
                } 
            }
             
        }
        return AptCur;
    }

    //Do not synch. Recalls based on ScheduleByDate reports in Orion mode.
    //Recalls.Synch(PatCur.PatNum);
    /**
    * Tests to see if this appointment will create a double booking. Returns arrayList with no items in it if no double bookings for this appt.  But if double booking, then it returns an arrayList of codes which would be double booked.  You must supply the appointment being scheduled as well as a list of all appointments for that day.  The list can include the appointment being tested if user is moving it to a different time on the same day.  The ProcsForOne list of procedures needs to contain the procedures for the apt becauese procsMultApts won't necessarily, especially if it's a planned appt on the pinboard.
    */
    public static ArrayList getDoubleBookedCodes(Appointment apt, DataTable dayTable, List<Procedure> procsMultApts, Procedure[] procsForOne) throws Exception {
        ArrayList retVal = new ArrayList();
        //codes
        //figure out which provider we are testing for
        long provNum = new long();
        if (apt.IsHygiene)
        {
            provNum = apt.ProvHyg;
        }
        else
        {
            provNum = apt.ProvNum;
        } 
        //compute the starting row of this appt
        int convertToY = (int)(((double)apt.AptDateTime.Hour * (double)60 / (double)PrefC.getLong(PrefName.AppointmentTimeIncrement) + (double)apt.AptDateTime.Minute / (double)PrefC.getLong(PrefName.AppointmentTimeIncrement)) * (double)ApptDrawing.LineH * ApptDrawing.RowsPerIncr);
        int startIndex = convertToY / ApptDrawing.LineH;
        //rounds down
        String pattern = ApptSingleDrawing.getPatternShowing(apt.Pattern);
        //keep track of which rows in the entire day would be occupied by provider time for this appt
        ArrayList aptProvTime = new ArrayList();
        for (int k = 0;k < pattern.Length;k++)
        {
            if (StringSupport.equals(pattern.Substring(k, 1), "X"))
            {
                aptProvTime.Add(startIndex + k);
            }
             
        }
        //even if it extends past midnight, we don't care
        //Now, loop through all the other appointments for the day, and see if any would overlap this one
        boolean overlaps = new boolean();
        Procedure[] procs = new Procedure[]();
        boolean doubleBooked = false;
        //applies to all appts, not just one at a time.
        DateTime aptDateTime = new DateTime();
        for (int i = 0;i < dayTable.Rows.Count;i++)
        {
            if (dayTable.Rows[i]["AptNum"].ToString() == apt.AptNum.ToString())
            {
                continue;
            }
             
            //ignore current apt in its old location
            //ignore other providers
            if (StringSupport.equals(dayTable.Rows[i]["IsHygiene"].ToString(), "1") && dayTable.Rows[i]["ProvHyg"].ToString() != provNum.ToString())
            {
                continue;
            }
             
            if (StringSupport.equals(dayTable.Rows[i]["IsHygiene"].ToString(), "0") && dayTable.Rows[i]["ProvNum"].ToString() != provNum.ToString())
            {
                continue;
            }
             
            if (dayTable.Rows[i]["AptStatus"].ToString() == (((Enum)ApptStatus.Broken).ordinal()).ToString())
            {
                continue;
            }
             
            //ignore broken appts
            aptDateTime = PIn.DateT(dayTable.Rows[i]["AptDateTime"].ToString());
            if (ApptDrawing.IsWeeklyView && aptDateTime.Date == apt.AptDateTime.Date)
            {
                continue;
            }
             
            //calculate starting row
            //this math is copied from another section of the program, so it's sloppy. Safer than trying to rewrite it:
            convertToY = (int)(((double)aptDateTime.Hour * (double)60 / (double)PrefC.getLong(PrefName.AppointmentTimeIncrement) + (double)aptDateTime.Minute / (double)PrefC.getLong(PrefName.AppointmentTimeIncrement)) * (double)ApptDrawing.LineH * ApptDrawing.RowsPerIncr);
            startIndex = convertToY / ApptDrawing.LineH;
            //rounds down
            pattern = ApptSingleDrawing.GetPatternShowing(dayTable.Rows[i]["Pattern"].ToString());
            //now compare it to apt
            overlaps = false;
            for (int k = 0;k < pattern.Length;k++)
            {
                if (StringSupport.equals(pattern.Substring(k, 1), "X"))
                {
                    if (aptProvTime.Contains(startIndex + k))
                    {
                        overlaps = true;
                        doubleBooked = true;
                    }
                     
                }
                 
            }
            if (overlaps)
            {
                //we need to add all codes for this appt to retVal
                procs = Procedures.GetProcsOneApt(PIn.Long(dayTable.Rows[i]["AptNum"].ToString()), procsMultApts);
                for (int j = 0;j < procs.Length;j++)
                {
                    retVal.Add(ProcedureCodes.GetStringProcCode(procs[j].CodeNum));
                }
            }
             
        }
        //now, retVal contains all double booked procs except for this appt
        //need to all procs for this appt.
        if (doubleBooked)
        {
            for (int j = 0;j < procsForOne.Length;j++)
            {
                retVal.Add(ProcedureCodes.GetStringProcCode(procsForOne[j].CodeNum));
            }
        }
         
        return retVal;
    }

}


