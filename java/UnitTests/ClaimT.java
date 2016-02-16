//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import CS2JNet.System.StringSupport;
import OpenDental.ClaimL;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Claims;
import OpenDentBusiness.EtransType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Providers;
import OpenDentBusiness.Relat;
import OpenDentBusiness.YN;

public class ClaimT   
{
    /**
    * claimType="P" or "S".
    */
    public static Claim createClaim(String claimType, List<PatPlan> PatPlanList, List<InsPlan> InsPlanList, List<ClaimProc> ClaimProcList, List<Procedure> procsForPat, Patient pat, List<Procedure> procsForClaim, List<Benefit> benefitList, List<InsSub> SubList) throws Exception {
        //Claim ClaimCur=CreateClaim("P",PatPlanList,InsPlanList,ClaimProcList,procsForPat);
        long claimFormNum = 0;
        EtransType eFormat = 0;
        InsPlan PlanCur1 = new InsPlan();
        InsSub SubCur1 = new InsSub();
        InsPlan PlanCur2 = new InsPlan();
        InsSub SubCur2 = new InsSub();
        Relat relatOther = Relat.Self;
        System.String __dummyScrutVar0 = claimType;
        if (__dummyScrutVar0.equals("P"))
        {
            SubCur1 = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, 1), SubList);
            PlanCur1 = InsPlans.GetPlan(SubCur1.PlanNum, InsPlanList);
            SubCur2 = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, 2), SubList);
        }
        else //PlanCur2=InsPlans.GetPlan(SubCur.PlanNum,InsPlanList);//can end up null
        if (__dummyScrutVar0.equals("S"))
        {
            SubCur1 = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, 2), SubList);
            PlanCur1 = InsPlans.GetPlan(SubCur1.PlanNum, InsPlanList);
            SubCur2 = InsSubs.GetSub(PatPlans.GetInsSubNum(PatPlanList, 1), SubList);
        }
          
        //PlanCur2=InsPlans.GetPlan(SubCur.PlanNum,InsPlanList);//can end up null
        //DataTable table=DataSetMain.Tables["account"];
        Procedure proc;
        //proc=Procedures.GetProcFromList(procsForPat,PIn.Long(table.Rows[gridAccount.SelectedIndices[0]]["ProcNum"].ToString()));
        //long clinicNum=proc.ClinicNum;
        ClaimProc[] claimProcs = new ClaimProc[procsForClaim.Count];
        //1:1 with procs
        long procNum = new long();
        for (int i = 0;i < procsForClaim.Count;i++)
        {
            //loop through selected procs
            //and try to find an estimate that can be used
            procNum = procsForClaim[i].ProcNum;
            claimProcs[i] = Procedures.GetClaimProcEstimate(procNum, ClaimProcList, PlanCur1, SubCur1.InsSubNum);
        }
        for (int i = 0;i < claimProcs.Length;i++)
        {
            //loop through each claimProc
            //and create any missing estimates. This handles claims to 3rd and 4th ins co's.
            if (claimProcs[i] == null)
            {
                claimProcs[i] = new ClaimProc();
                proc = procsForClaim[i];
                ClaimProcs.CreateEst(claimProcs[i], proc, PlanCur1, SubCur1);
            }
             
        }
        Claim claim = new Claim();
        Claims.insert(claim);
        //to retreive a key for new Claim.ClaimNum
        claim.PatNum = pat.PatNum;
        claim.DateService = claimProcs[claimProcs.Length - 1].ProcDate;
        claim.ClinicNum = procsForClaim[0].ClinicNum;
        claim.DateSent = DateTime.Today;
        claim.ClaimStatus = "S";
        //datereceived
        System.String __dummyScrutVar1 = claimType;
        if (__dummyScrutVar1.equals("P"))
        {
            claim.PlanNum = SubCur1.PlanNum;
            claim.InsSubNum = PatPlans.GetInsSubNum(PatPlanList, 1);
            claim.PatRelat = PatPlans.GetRelat(PatPlanList, 1);
            claim.ClaimType = "P";
            claim.PlanNum2 = SubCur2.PlanNum;
            //might be 0 if no sec ins
            claim.InsSubNum2 = PatPlans.GetInsSubNum(PatPlanList, 2);
            claim.PatRelat2 = PatPlans.GetRelat(PatPlanList, 2);
        }
        else if (__dummyScrutVar1.equals("S"))
        {
            claim.PlanNum = SubCur1.PlanNum;
            claim.InsSubNum = PatPlans.GetInsSubNum(PatPlanList, 2);
            claim.PatRelat = PatPlans.GetRelat(PatPlanList, 2);
            claim.ClaimType = "S";
            claim.PlanNum2 = SubCur2.PlanNum;
            claim.InsSubNum2 = PatPlans.GetInsSubNum(PatPlanList, 1);
            claim.PatRelat2 = PatPlans.GetRelat(PatPlanList, 1);
        }
          
        claim.ProvTreat = procsForClaim[0].ProvNum;
        claim.IsProsthesis = "I";
        claim.ProvBill = Providers.getBillingProvNum(claim.ProvTreat,claim.ClinicNum);
        claim.EmployRelated = YN.No;
        //attach procedures
        Procedure ProcCur;
        for (int i = 0;i < claimProcs.Length;i++)
        {
            ProcCur = procsForClaim[i];
            claimProcs[i].ClaimNum = claim.ClaimNum;
            claimProcs[i].Status = OpenDentBusiness.ClaimProcStatus.NotReceived;
            //status for claims unsent or sent.
            //writeoff handled in ClaimL.CalculateAndUpdate()
            claimProcs[i].CodeSent = ProcedureCodes.getProcCode(ProcCur.CodeNum).ProcCode;
            if (claimProcs[i].CodeSent.Length > 5 && StringSupport.equals(claimProcs[i].CodeSent.Substring(0, 1), "D"))
            {
                claimProcs[i].CodeSent = claimProcs[i].CodeSent.Substring(0, 5);
            }
             
            claimProcs[i].LineNumber = (byte)(i + 1);
            ClaimProcs.Update(claimProcs[i]);
        }
        ClaimProcList = ClaimProcs.refresh(pat.PatNum);
        ClaimL.CalculateAndUpdate(procsForPat, InsPlanList, claim, PatPlanList, benefitList, pat.getAge(), SubList);
        return claim;
    }

}


