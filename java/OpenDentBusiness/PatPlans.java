//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Benefit;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Db;
import OpenDentBusiness.Family;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PriSecMed;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Relat;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PatPlans   
{
    /**
    * Gets a list of all patplans for a given patient
    */
    public static List<PatPlan> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PatPlan>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * from patplan" + " WHERE PatNum = " + patNum.ToString() + " ORDER BY Ordinal";
        return Crud.PatPlanCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(PatPlan patPlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patPlan);
            return ;
        }
         
        //ordinal was already set using SetOrdinal, but it's harmless to set it again.
        Crud.PatPlanCrud.Update(patPlan);
    }

    /**
    * 
    */
    public static long insert(PatPlan patPlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            patPlan.PatPlanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), patPlan);
            return patPlan.PatPlanNum;
        }
         
        return Crud.PatPlanCrud.Insert(patPlan);
    }

    /*
    		///<summary>Supply a PatPlan list.  This function loops through the list and returns the plan num of the specified ordinal.  If ordinal not valid, then it returns 0.  The main purpose of this function is so we don't have to check the length of the list.</summary>
    		public static long GetPlanNum(List<PatPlan> list,int ordinal) {
    			//No need to check RemotingRole; no call to db.
    			for(int i=0;i<list.Count;i++){
    				if(list[i].Ordinal==ordinal){
    					return list[i].PlanNum;
    				}
    			}
    			return 0;
    		}*/
    /**
    * Supply a PatPlan list.  This function loops through the list and returns the insSubNum of the specified ordinal.  If ordinal not valid, then it returns 0.  The main purpose of this function is so we don't have to check the length of the list.
    */
    public static long getInsSubNum(List<PatPlan> list, int ordinal) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (list[i].Ordinal == ordinal)
            {
                return list[i].InsSubNum;
            }
             
        }
        return 0;
    }

    /**
    * Supply a PatPlan list.  This function loops through the list and returns the relationship of the specified ordinal.  If ordinal not valid, then it returns self (0).
    */
    public static Relat getRelat(List<PatPlan> list, int ordinal) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (list[i].Ordinal == ordinal)
            {
                return list[i].Relationship;
            }
             
        }
        return Relat.Self;
    }

    public static String getPatID(long subNum, List<PatPlan> patPlans) throws Exception {
        for (int p = 0;p < patPlans.Count;p++)
        {
            //No need to check RemotingRole; no call to db.
            if (patPlans[p].InsSubNum == subNum)
            {
                return patPlans[p].PatID;
            }
             
        }
        return "";
    }

    /**
    * Will return 1 for primary insurance, etc.  Will return 0 if planNum not found in the list.
    */
    public static int getOrdinal(long subNum, List<PatPlan> patPlans) throws Exception {
        for (int p = 0;p < patPlans.Count;p++)
        {
            //No need to check RemotingRole; no call to db.
            if (patPlans[p].InsSubNum == subNum)
            {
                return patPlans[p].Ordinal;
            }
             
        }
        return 0;
    }

    /**
    * Returns the ordinal (1-based) for the patplan matching the given PriSecMed. Returns 0 if no match.
    */
    public static int getOrdinal(PriSecMed priSecMed, List<PatPlan> PatPlanList, List<InsPlan> planList, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        int dentalOrdinal = 0;
        for (int i = 0;i < PatPlanList.Count;i++)
        {
            InsSub sub = InsSubs.GetSub(PatPlanList[i].InsSubNum, subList);
            InsPlan plan = InsPlans.GetPlan(sub.PlanNum, planList);
            if (plan.IsMedical)
            {
                if (priSecMed == PriSecMed.Medical)
                {
                    return PatPlanList[i].Ordinal;
                }
                 
            }
            else
            {
                //dental
                dentalOrdinal++;
                if (dentalOrdinal == 1)
                {
                    if (priSecMed == PriSecMed.Primary)
                    {
                        return PatPlanList[i].Ordinal;
                    }
                     
                }
                else if (dentalOrdinal == 2)
                {
                    if (priSecMed == PriSecMed.Secondary)
                    {
                        return PatPlanList[i].Ordinal;
                    }
                     
                }
                  
            } 
        }
        return 0;
    }

    /**
    * Will return null if subNum not found in the list.
    */
    public static PatPlan getFromList(List<PatPlan> patPlans, long subNum) throws Exception {
        for (int p = 0;p < patPlans.Count;p++)
        {
            //No need to check RemotingRole; no call to db.
            if (patPlans[p].InsSubNum == subNum)
            {
                return patPlans[p];
            }
             
        }
        return null;
    }

    /**
    * Sets the ordinal of the specified patPlan.  Rearranges the other patplans for the patient to keep the ordinal sequence contiguous.  Estimates must be recomputed after this.  FormInsPlan currently updates estimates every time it closes.  Only used in one place.  Returns the new ordinal.
    */
    public static int setOrdinal(long patPlanNum, int newOrdinal) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), patPlanNum, newOrdinal);
        }
         
        String command = "SELECT PatNum FROM patplan WHERE PatPlanNum=" + POut.long(patPlanNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return 1;
        }
         
        long patNum = PIn.Long(table.Rows[0][0].ToString());
        List<PatPlan> patPlans = refresh(patNum);
        //int oldOrdinal=GetFromList(patPlans,patPlanNum).Ordinal;
        if (newOrdinal > patPlans.Count)
        {
            newOrdinal = patPlans.Count;
        }
         
        if (newOrdinal < 1)
        {
            newOrdinal = 1;
        }
         
        int curOrdinal = 1;
        for (int i = 0;i < patPlans.Count;i++)
        {
            //Loop through each patPlan.
            if (patPlans[i].PatPlanNum == patPlanNum)
            {
                continue;
            }
             
            //the one we are setting will be handled later
            if (curOrdinal == newOrdinal)
            {
                curOrdinal++;
            }
             
            //skip the newOrdinal when setting the sequence for the others.
            command = "UPDATE patplan SET Ordinal=" + POut.Long(curOrdinal) + " WHERE PatPlanNum=" + POut.Long(patPlans[i].PatPlanNum);
            Db.nonQ(command);
            curOrdinal++;
        }
        command = "UPDATE patplan SET Ordinal=" + POut.Long(newOrdinal) + " WHERE PatPlanNum=" + POut.long(patPlanNum);
        Db.nonQ(command);
        return newOrdinal;
    }

    /**
    * Loops through the supplied list to find the one patplan needed.
    */
    public static PatPlan getFromList(PatPlan[] patPlans, long patPlanNum) throws Exception {
        for (int i = 0;i < patPlans.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (patPlans[i].PatPlanNum == patPlanNum)
            {
                return patPlans[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Loops through the supplied list to find the one patplanNum needed based on the planNum.  Returns 0 if patient is not currently covered by the planNum supplied.
    */
    public static long getPatPlanNum(long subNum, List<PatPlan> patPlanList) throws Exception {
        for (int i = 0;i < patPlanList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (patPlanList[i].InsSubNum == subNum)
            {
                return patPlanList[i].PatPlanNum;
            }
             
        }
        return 0;
    }

    /*Deprecated
    		///<summary>Gets one patPlanNum directly from database.  Only used once in FormClaimProc.</summary>
    		public static long GetPatPlanNum(long patNum,long planNum) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetLong(MethodBase.GetCurrentMethod(),patNum,planNum);
    			}
    			string command="SELECT PatPlanNum FROM patplan WHERE PatNum="+POut.Long(patNum)+" AND PlanNum="+POut.Long(planNum);
    			return PIn.Long(Db.GetScalar(command));
    		}*/
    /**
    * Gets directly from database.  Used by Trojan.
    */
    public static PatPlan[] getByPlanNum(long planNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PatPlan[]>GetObject(MethodBase.GetCurrentMethod(), planNum);
        }
         
        //string command="SELECT * FROM patplan WHERE PlanNum='"+POut.Long(planNum)+"'";
        //The left join will get extra info about each plan, namely the PlanNum.  No need for a GROUP BY.  The PlanNum is used to filter.
        String command = "SELECT * FROM patplan \r\n" + 
        "\t\t\t\tLEFT JOIN inssub ON patplan.InsSubNum=inssub.InsSubNum\r\n" + 
        "\t\t\t\tWHERE inssub.PlanNum=" + POut.long(planNum);
        return Crud.PatPlanCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static int getCountBySubNum(long insSubNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), insSubNum);
        }
         
        String command = "SELECT COUNT(*) FROM patplan WHERE InsSubNum='" + POut.long(insSubNum) + "'";
        return PIn.int(Db.getCount(command));
    }

    /**
    * Will return null if none exists.
    */
    public static PatPlan getPatPlan(long patNum, int ordinal) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PatPlan>GetObject(MethodBase.GetCurrentMethod(), patNum, ordinal);
        }
         
        String command = "SELECT * FROM patplan WHERE PatNum=" + POut.long(patNum) + " AND Ordinal=" + POut.Long(ordinal);
        return Crud.PatPlanCrud.SelectOne(command);
    }

    /**
    * Deletes the patplan with the specified patPlanNum.  Rearranges the other patplans for the patient to keep the ordinal sequence contiguous.  Then, recomputes all estimates for this patient because their coverage is now different.  Also sets patient.HasIns to the correct value.
    */
    public static void delete(long patPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patPlanNum);
            return ;
        }
         
        String command = "SELECT PatNum FROM patplan WHERE PatPlanNum=" + POut.long(patPlanNum);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return ;
        }
         
        long patNum = PIn.Long(table.Rows[0][0].ToString());
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        boolean doDecrement = false;
        for (int i = 0;i < patPlans.Count;i++)
        {
            if (doDecrement)
            {
                //patPlan has already been deleted, so decrement the rest.
                command = "UPDATE patplan SET Ordinal=" + POut.Long(patPlans[i].Ordinal - 1) + " WHERE PatPlanNum=" + POut.Long(patPlans[i].PatPlanNum);
                Db.nonQ(command);
                continue;
            }
             
            if (patPlans[i].PatPlanNum == patPlanNum)
            {
                command = "DELETE FROM patplan WHERE PatPlanNum=" + POut.long(patPlanNum);
                Db.nonQ(command);
                command = "DELETE FROM benefit WHERE PatPlanNum=" + POut.long(patPlanNum);
                Db.nonQ(command);
                doDecrement = true;
            }
             
        }
        Family fam = Patients.getFamily(patNum);
        Patient pat = fam.getPatient(patNum);
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        List<Procedure> procs = Procedures.refresh(patNum);
        patPlans = PatPlans.refresh(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<Benefit> benList = Benefits.Refresh(patPlans, subList);
        Procedures.ComputeEstimatesForAll(patNum, claimProcs, procs, planList, patPlans, benList, pat.getAge(), subList);
        Patients.setHasIns(patNum);
    }

    /**
    * Deletes the patplan and benefits with the specified patPlanNum.  Does not rearrange the other patplans for the patient.  A patplan must be inserted after this function is called to take the place of the patplan being deleted.
    */
    public static void deleteNonContiguous(long patPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patPlanNum);
            return ;
        }
         
        String command = "DELETE FROM patplan WHERE PatPlanNum=" + POut.long(patPlanNum);
        Db.nonQ(command);
        command = "DELETE FROM benefit WHERE PatPlanNum=" + POut.long(patPlanNum);
        Db.nonQ(command);
    }

}


