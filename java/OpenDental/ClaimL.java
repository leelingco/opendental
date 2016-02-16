//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClaimProcStatus;
import OpenDentBusiness.Claims;
import OpenDentBusiness.Fees;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;

/**
* 
*/
public class ClaimL   
{
    /**
    * Updates all claimproc estimates and also updates claim totals to db. Must supply procList which includes all procedures that this claim is linked to.  Will also need to refresh afterwards to see the results
    */
    public static void calculateAndUpdate(List<Procedure> procList, List<InsPlan> planList, Claim claimCur, List<PatPlan> patPlans, List<Benefit> benefitList, int patientAge, List<InsSub> subList) throws Exception {
        //we need more than just the claimprocs for this claim.
        //in order to run Procedures.ComputeEstimates, we need all claimprocs for all procedures attached to this claim
        List<ClaimProc> ClaimProcsAll = ClaimProcs.refresh(claimCur.PatNum);
        List<ClaimProc> ClaimProcsForClaim = ClaimProcs.refreshForClaim(claimCur.ClaimNum);
        //will be ordered by line number.
        double claimFee = 0;
        double dedApplied = 0;
        double insPayEst = 0;
        double insPayAmt = 0;
        double writeoff = 0;
        InsPlan plan = InsPlans.GetPlan(claimCur.PlanNum, planList);
        if (plan == null)
        {
            return ;
        }
         
        long patPlanNum = PatPlans.GetPatPlanNum(claimCur.InsSubNum, patPlans);
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            //first loop handles totals for received items.
            if (ClaimProcsForClaim[i].Status != ClaimProcStatus.Received)
            {
                continue;
            }
             
            //disregard any status except Receieved.
            claimFee += ClaimProcsForClaim[i].FeeBilled;
            dedApplied += ClaimProcsForClaim[i].DedApplied;
            insPayEst += ClaimProcsForClaim[i].InsPayEst;
            insPayAmt += ClaimProcsForClaim[i].InsPayAmt;
            writeoff += ClaimProcsForClaim[i].WriteOff;
        }
        //loop again only for procs not received.
        //And for preauth.
        Procedure ProcCur;
        //InsPlan plan=InsPlans.GetPlan(claimCur.PlanNum,planList);
        List<ClaimProcHist> histList = ClaimProcs.GetHistList(claimCur.PatNum, benefitList, patPlans, planList, claimCur.ClaimNum, claimCur.DateService, subList);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        for (int i = 0;i < ClaimProcsAll.Count;i++)
        {
            //make a copy
            claimProcListOld.Add(ClaimProcsAll[i].Copy());
        }
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            //loop through each proc
            ProcCur = Procedures.GetProcFromList(procList, ClaimProcsForClaim[i].ProcNum);
            for (int j = 0;j < ClaimProcsAll.Count;j++)
            {
                //in order for ComputeEstimates to give accurate Writeoff when creating a claim, InsPayEst must be filled for the claimproc with status of NotReceived.
                //So, we must set it here.  We need to set it in the claimProcsAll list.  Find the matching one.
                if (ClaimProcsAll[j].ClaimProcNum == ClaimProcsForClaim[i].ClaimProcNum)
                {
                    //same claimproc in a different list
                    if (ClaimProcsForClaim[i].Status == ClaimProcStatus.NotReceived && ProcCur != null)
                    {
                        //ignores payments, etc
                        ClaimProcsAll[j].InsPayEst = ClaimProcs.GetInsEstTotal(ClaimProcsAll[j]);
                    }
                     
                }
                 
            }
            for (int h = histList.Count - 1;h >= 0;h--)
            {
                //When this is the secondary claim, HistList includes the primary estimates, which is something we don't want because the primary calculations gets confused.
                //So, we must remove those bad entries from histList.
                //loop through the histList backwards
                if (histList[h].ProcNum != ProcCur.ProcNum)
                {
                    continue;
                }
                 
                //Makes sure we will only be excluding histList entries for procs on this claim.
                //we already excluded this claimNum when getting the histList.
                if (histList[h].Status != ClaimProcStatus.NotReceived)
                {
                    continue;
                }
                 
                //The only ones that are a problem are the ones on the primary claim not received yet.
                histList.RemoveAt(h);
            }
            RefSupport<List<ClaimProc>> refVar___0 = new RefSupport<List<ClaimProc>>(ClaimProcsAll);
            Procedures.ComputeEstimates(ProcCur, claimCur.PatNum, refVar___0, false, planList, patPlans, benefitList, histList, loopList, false, patientAge, subList);
            ClaimProcsAll = refVar___0.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(ClaimProcsAll, ProcCur.ProcNum, ProcCur.CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___1 = new RefSupport<List<ClaimProc>>(ClaimProcsAll);
        ClaimProcs.Synch(refVar___1, claimProcListOld);
        ClaimProcsAll = refVar___1.getValue();
        ClaimProcsForClaim = ClaimProcs.refreshForClaim(claimCur.ClaimNum);
        for (int i = 0;i < ClaimProcsForClaim.Count;i++)
        {
            //But ClaimProcsAll has not been refreshed.
            if (ClaimProcsForClaim[i].Status != ClaimProcStatus.NotReceived && ClaimProcsForClaim[i].Status != ClaimProcStatus.Preauth && ClaimProcsForClaim[i].Status != ClaimProcStatus.CapClaim)
            {
                continue;
            }
             
            ProcCur = Procedures.GetProcFromList(procList, ClaimProcsForClaim[i].ProcNum);
            if (ProcCur.ProcNum == 0)
            {
                continue;
            }
             
            //ignores payments, etc
            //fee:
            int qty = ProcCur.UnitQty + ProcCur.BaseUnits;
            if (qty == 0)
            {
                qty = 1;
            }
             
            if (plan.ClaimsUseUCR)
            {
                //use UCR for the provider of the procedure
                long provNum = ProcCur.ProvNum;
                if (provNum == 0)
                {
                    //if no prov set, then use practice default.
                    provNum = PrefC.getLong(PrefName.PracticeDefaultProv);
                }
                 
                //get the fee based on code and prov fee sched
                double feebilled = Fees.GetAmount0(ProcCur.CodeNum, ProviderC.getListLong()[Providers.getIndexLong(provNum)].FeeSched);
                if (feebilled > ProcCur.ProcFee)
                {
                    ClaimProcsForClaim[i].FeeBilled = qty * feebilled;
                }
                else
                {
                    ClaimProcsForClaim[i].FeeBilled = qty * ProcCur.ProcFee;
                } 
            }
            else
            {
                //else if(claimCur.ClaimType=="Cap") {//Even for capitation, use the proc fee.
                //	ClaimProcsForClaim[i].FeeBilled=0;
                //}
                //don't use ucr.  Use the procedure fee instead.
                ClaimProcsForClaim[i].FeeBilled = qty * ProcCur.ProcFee;
            } 
            claimFee += ClaimProcsForClaim[i].FeeBilled;
            if (StringSupport.equals(claimCur.ClaimType, "PreAuth") || StringSupport.equals(claimCur.ClaimType, "Other") || StringSupport.equals(claimCur.ClaimType, "Cap"))
            {
                //only the fee gets calculated, the rest does not
                ClaimProcs.Update(ClaimProcsForClaim[i]);
                continue;
            }
             
            //ClaimProcs.ComputeBaseEst(ClaimProcsForClaim[i],ProcCur.ProcFee,ProcCur.ToothNum,ProcCur.CodeNum,plan,patPlanNum,benefitList,histList,loopList);
            ClaimProcsForClaim[i].InsPayEst = ClaimProcs.GetInsEstTotal(ClaimProcsForClaim[i]);
            //Yes, this is duplicated from further up.
            ClaimProcsForClaim[i].DedApplied = ClaimProcs.GetDedEst(ClaimProcsForClaim[i]);
            if (ClaimProcsForClaim[i].Status == ClaimProcStatus.NotReceived)
            {
                //(vs preauth)
                ClaimProcsForClaim[i].WriteOff = ClaimProcs.GetWriteOffEstimate(ClaimProcsForClaim[i]);
                writeoff += ClaimProcsForClaim[i].WriteOff;
            }
             
            /*
            					ClaimProcsForClaim[i].WriteOff=0;
            					if(claimCur.ClaimType=="P" && plan.PlanType=="p") {//Primary && PPO
            						double insplanAllowed=Fees.GetAmount(ProcCur.CodeNum,plan.FeeSched);
            						if(insplanAllowed!=-1) {
            							ClaimProcsForClaim[i].WriteOff=ProcCur.ProcFee-insplanAllowed;
            						}
            						//else, if -1 fee not found, then do not show a writeoff. User can change writeoff if they disagree.
            					}
            					writeoff+=ClaimProcsForClaim[i].WriteOff;*/
            dedApplied += ClaimProcsForClaim[i].DedApplied;
            insPayEst += ClaimProcsForClaim[i].InsPayEst;
            ClaimProcsForClaim[i].ProcDate = ProcCur.ProcDate.Date;
            //this solves a rare bug. Keeps dates synched.
            //It's rare enough that I'm not goint to add it to the db maint tool.
            ClaimProcs.Update(ClaimProcsForClaim[i]);
        }
        //but notice that the ClaimProcs lists are not refreshed until the loop is finished.
        //for claimprocs.forclaim
        claimCur.ClaimFee = claimFee;
        claimCur.DedApplied = dedApplied;
        claimCur.InsPayEst = insPayEst;
        claimCur.InsPayAmt = insPayAmt;
        claimCur.WriteOff = writeoff;
        //Cur=ClaimCur;
        Claims.update(claimCur);
    }

}


