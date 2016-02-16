//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:29 PM
//

package OpenDental;

import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViewC;
import OpenDentBusiness.ApptViewItem;
import OpenDentBusiness.ApptViewItemC;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.OperatoryC;
import OpenDentBusiness.Provider;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Schedule;
import OpenDentBusiness.ScheduleType;
import OpenDentBusiness.UI.ApptDrawing;

public class ApptViewItemL   
{
    /**
    * A list of the ApptViewItems for the current view.
    */
    public static List<ApptViewItem> ForCurView = new List<ApptViewItem>();
    /**
    * Subset of ForCurView. Just items for rowElements, including apptfielddefs. If no view is selected, then the elements are filled with default info.
    */
    public static List<ApptViewItem> ApptRows = new List<ApptViewItem>();
    public static ApptView ApptViewCur;
    public static void getForCurView(int indexInList, boolean isWeekly, List<Schedule> dailySched) throws Exception {
        if (indexInList < 0)
        {
            //might be -1 or -2
            GetForCurView(null, isWeekly, dailySched);
        }
        else
        {
            GetForCurView(ApptViewC.getList()[indexInList], isWeekly, dailySched);
        } 
    }

    /**
    * Gets (list)ForCurView, ApptDrawing.VisOps, ApptDrawing.VisProvs, and ApptRows.  Also sets TwoRows. Works even if supply -1 to indicate no apptview is selected.  Pass in null for the dailySched if this is a weekly view or if in FormApptViewEdit.
    */
    public static void getForCurView(ApptView av, boolean isWeekly, List<Schedule> dailySched) throws Exception {
        ApptViewCur = av;
        ForCurView = new List<ApptViewItem>();
        ApptDrawing.VisProvs = new List<Provider>();
        ApptDrawing.VisOps = new List<Operatory>();
        ApptRows = new List<ApptViewItem>();
        int index = new int();
        //If there are no appointment views set up (therefore, none selected), then use a hard-coded default view.
        if (ApptViewCur == null)
        {
            for (int i = 0;i < OperatoryC.getListShort().Count;i++)
            {
                //MessageBox.Show("apptcategorynum:"+ApptCategories.Cur.ApptCategoryNum.ToString());
                //make visible ops exactly the same as the short ops list (all except hidden)
                ApptDrawing.VisOps.Add(OperatoryC.getListShort()[i]);
            }
            for (int i = 0;i < ProviderC.getListShort().Count;i++)
            {
                //make visible provs exactly the same as the prov list (all except hidden)
                ApptDrawing.VisProvs.Add(ProviderC.getListShort()[i]);
            }
            //Hard coded elements showing
            ApptRows.Add(new ApptViewItem("PatientName", 0, Color.Black));
            ApptRows.Add(new ApptViewItem("ASAP", 1, Color.DarkRed));
            ApptRows.Add(new ApptViewItem("MedUrgNote", 2, Color.DarkRed));
            ApptRows.Add(new ApptViewItem("PremedFlag", 3, Color.DarkRed));
            ApptRows.Add(new ApptViewItem("Lab", 4, Color.DarkRed));
            ApptRows.Add(new ApptViewItem("Procs", 5, Color.Black));
            ApptRows.Add(new ApptViewItem("Note", 6, Color.Black));
            ApptDrawing.RowsPerIncr = 1;
        }
        else
        {
            for (int i = 0;i < ApptViewItemC.getList().Length;i++)
            {
                //An appointment view is selected, so add provs and ops from the view to our lists of indexes.
                if (ApptViewItemC.getList()[i].ApptViewNum == ApptViewCur.ApptViewNum)
                {
                    ForCurView.Add(ApptViewItemC.getList()[i]);
                    if (ApptViewItemC.getList()[i].OpNum > 0)
                    {
                        //op
                        if (ApptViewCur.OnlyScheduledProvs && !isWeekly)
                        {
                            continue;
                        }
                         
                        //handled below
                        index = Operatories.GetOrder(ApptViewItemC.getList()[i].OpNum);
                        if (index != -1)
                        {
                            ApptDrawing.VisOps.Add(OperatoryC.getListShort()[index]);
                        }
                         
                    }
                    else if (ApptViewItemC.getList()[i].ProvNum > 0)
                    {
                        //prov
                        index = Providers.GetIndex(ApptViewItemC.getList()[i].ProvNum);
                        if (index != -1)
                        {
                            ApptDrawing.VisProvs.Add(ProviderC.getListShort()[index]);
                        }
                         
                    }
                    else
                    {
                        //element or apptfielddef
                        ApptRows.Add(ApptViewItemC.getList()[i]);
                    }  
                }
                 
            }
            ApptDrawing.RowsPerIncr = ApptViewCur.RowsPerIncr;
        } 
        //if this appt view has the option to show only scheduled providers and this is daily view.
        //Remember that there is no intelligence in weekly view for this option, and it behaves just like it always did.
        if (ApptViewCur != null && ApptViewCur.OnlyScheduledProvs && !isWeekly)
        {
            //intelligently decide what ops to show.  It's based on the schedule for the day.
            //VisOps will be totally empty right now because it looped out of the above section of code.
            List<long> listSchedOps = new List<long>();
            boolean opAdded = new boolean();
            int indexOp = new int();
            for (int i = 0;i < OperatoryC.getListShort().Count;i++)
            {
                //loop through all ops for all views (except the hidden ones, of course)
                //find any applicable sched for the op
                opAdded = false;
                for (int s = 0;s < dailySched.Count;s++)
                {
                    if (dailySched[s].SchedType != ScheduleType.Provider)
                    {
                        continue;
                    }
                     
                    if (dailySched[s].StartTime == new TimeSpan(0))
                    {
                        continue;
                    }
                     
                    //skip if block starts at midnight.
                    if (dailySched[s].StartTime == dailySched[s].StopTime)
                    {
                        continue;
                    }
                     
                    //skip if block has no length.
                    if (ApptViewCur.OnlySchedAfterTime > new TimeSpan(0, 0, 0))
                    {
                        if (dailySched[s].StartTime < ApptViewCur.OnlySchedAfterTime || dailySched[s].StopTime < ApptViewCur.OnlySchedAfterTime)
                        {
                            continue;
                        }
                         
                    }
                     
                    if (ApptViewCur.OnlySchedBeforeTime > new TimeSpan(0, 0, 0))
                    {
                        if (dailySched[s].StartTime > ApptViewCur.OnlySchedBeforeTime || dailySched[s].StopTime > ApptViewCur.OnlySchedBeforeTime)
                        {
                            continue;
                        }
                         
                    }
                     
                    //this 'sched' must apply to this situation.
                    //listSchedOps is the ops for this 'sched'.
                    listSchedOps = dailySched[s].Ops;
                    for (int p = 0;p < listSchedOps.Count;p++)
                    {
                        //Add all the ops for this 'sched' to the list of visible ops
                        //Filter the ops if the clinic option was set for the appt view.
                        if (ApptViewCur.ClinicNum > 0 && ApptViewCur.ClinicNum != Operatories.GetOperatory(listSchedOps[p]).ClinicNum)
                        {
                            continue;
                        }
                         
                        if (listSchedOps[p] == OperatoryC.getListShort()[i].OperatoryNum)
                        {
                            Operatory op = OperatoryC.getListShort()[i];
                            indexOp = Operatories.GetOrder(listSchedOps[p]);
                            if (indexOp != -1 && !ApptDrawing.VisOps.Contains(op))
                            {
                                //prevents adding duplicate ops
                                ApptDrawing.VisOps.Add(op);
                                opAdded = true;
                                break;
                            }
                             
                        }
                         
                    }
                    //If the provider is not scheduled to any op(s), add their default op(s).
                    if (OperatoryC.getListShort()[i].ProvDentist == dailySched[s].ProvNum && listSchedOps.Count == 0)
                    {
                        //only if the sched does not specify any ops
                        //Only add the op if the clinic option was not set in the appt view or if the op is assigned to that clinic.
                        if (ApptViewCur.ClinicNum == 0 || ApptViewCur.ClinicNum == OperatoryC.getListShort()[i].ClinicNum)
                        {
                            indexOp = Operatories.GetOrder(OperatoryC.getListShort()[i].OperatoryNum);
                            if (indexOp != -1 && !ApptDrawing.VisOps.Contains(OperatoryC.getListShort()[i]))
                            {
                                ApptDrawing.VisOps.Add(OperatoryC.getListShort()[i]);
                                opAdded = true;
                            }
                             
                        }
                         
                    }
                     
                    if (opAdded)
                    {
                        break;
                    }
                     
                }
            }
        }
         
        //break out of the loop of schedules.  Continue with the next op.
        ApptDrawing.VisOps.Sort(CompareOps);
        ApptDrawing.VisProvs.Sort(CompareProvs);
    }

    /**
    * Sorts list of operatories by ItemOrder.
    */
    private static int compareOps(Operatory op1, Operatory op2) throws Exception {
        if (op1.ItemOrder < op2.ItemOrder)
        {
            return -1;
        }
        else if (op1.ItemOrder > op2.ItemOrder)
        {
            return 1;
        }
          
        return 0;
    }

    /**
    * Sorts list of providers by ItemOrder.
    */
    private static int compareProvs(Provider prov1, Provider prov2) throws Exception {
        if (prov1.ItemOrder < prov2.ItemOrder)
        {
            return -1;
        }
        else if (prov1.ItemOrder > prov2.ItemOrder)
        {
            return 1;
        }
          
        return 0;
    }

    /**
    * Only used in FormApptViewEdit. Must have run GetForCurView first.
    */
    public static boolean opIsInView(long opNum) throws Exception {
        for (int i = 0;i < ForCurView.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ForCurView[i].OpNum == opNum)
                return true;
             
        }
        return false;
    }

    /**
    * Only used in ApptViewItem setup and ContrAppt (for search function - search for appt with prov in this view). Must have run GetForCurView first.
    */
    public static boolean provIsInView(long provNum) throws Exception {
        for (int i = 0;i < ForCurView.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ForCurView[i].ProvNum == provNum)
                return true;
             
        }
        return false;
    }

}


