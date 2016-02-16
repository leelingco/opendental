//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDental.FormClaimProc;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Claim;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.ClockEvents;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.Employee;
import OpenDentBusiness.EnumCobRule;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fee;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PayPeriod;
import OpenDentBusiness.PayPeriods;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Prefs;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.TimeAdjust;
import OpenDentBusiness.TimeAdjusts;
import OpenDentBusiness.TimeCardRules;
import UnitTests.BenefitT;
import UnitTests.CarrierT;
import UnitTests.ClaimProcT;
import UnitTests.ClaimT;
import UnitTests.ClockEventT;
import UnitTests.EmployeeT;
import UnitTests.FeeSchedT;
import UnitTests.InsPlanT;
import UnitTests.InsSubT;
import UnitTests.PatientT;
import UnitTests.PatPlanT;
import UnitTests.PayPeriodT;
import UnitTests.ProcedureT;
import UnitTests.TimeCardRuleT;

public class AllTests   
{
    /**
    * 
    */
    public static String testOneTwo(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 1 && specificTest != 2)
        {
            return "";
        }
         
        String suffix = "1";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        long feeSchedNum1 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix);
        long feeSchedNum2 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix + "b");
        //Standard Fee
        Fees.refreshCache();
        long codeNum = ProcedureCodes.getCodeNum("D2750");
        Fee fee = Fees.GetFee(codeNum, 53);
        if (fee == null)
        {
            fee = new Fee();
            fee.CodeNum = codeNum;
            fee.FeeSched = 53;
            fee.Amount = 1200;
            Fees.insert(fee);
        }
        else
        {
            fee.Amount = 1200;
            Fees.update(fee);
        } 
        //PPO fees
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum1;
        fee.Amount = 900;
        Fees.insert(fee);
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum2;
        fee.Amount = 650;
        Fees.insert(fee);
        Fees.refreshCache();
        //Carrier
        Carrier carrier = CarrierT.createCarrier(suffix);
        long planNum1 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum1).PlanNum;
        long planNum2 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum2).PlanNum;
        InsSub sub1 = InsSubT.createInsSub(pat.PatNum,planNum1);
        long subNum1 = sub1.InsSubNum;
        InsSub sub2 = InsSubT.createInsSub(pat.PatNum,planNum2);
        long subNum2 = sub2.InsSubNum;
        BenefitT.createCategoryPercent(planNum1,EbenefitCategory.Crowns,50);
        BenefitT.createCategoryPercent(planNum2,EbenefitCategory.Crowns,50);
        PatPlanT.CreatePatPlan(1, patNum, subNum1);
        PatPlanT.CreatePatPlan(2, patNum, subNum2);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "8", Fees.GetAmount0(codeNum, 53));
        //crown on 8
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //Validate
        String retVal = "";
        ClaimProc claimProc;
        if (specificTest == 0 || specificTest == 1)
        {
            RefSupport<List<ClaimProc>> refVar___0 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(proc, patNum, refVar___0, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
            claimProcs = refVar___0.getValue();
            claimProcs = ClaimProcs.refresh(patNum);
            claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum1, subNum1);
            //I don't think allowed can be easily tested on the fly, and it's not that important.
            if (claimProc.InsEstTotal != 450)
            {
                throw new Exception("Should be 450. \r\n");
            }
             
            if (claimProc.WriteOffEst != 300)
            {
                throw new Exception("Should be 300. \r\n");
            }
             
            claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum2, subNum2);
            if (claimProc.InsEstTotal != 200)
            {
                throw new Exception("Should be 200. \r\n");
            }
             
            if (claimProc.WriteOffEst != 0)
            {
                throw new Exception("Should be 0. \r\n");
            }
             
            retVal += "1: Passed.  Claim proc estimates for dual PPO ins.  Allowed1 greater than Allowed2.\r\n";
        }
         
        //Test 2----------------------------------------------------------------------------------------------------
        if (specificTest == 0 || specificTest == 2)
        {
            //switch the fees
            fee = Fees.getFee(codeNum,feeSchedNum1);
            fee.Amount = 650;
            Fees.update(fee);
            fee = Fees.getFee(codeNum,feeSchedNum2);
            fee.Amount = 900;
            Fees.update(fee);
            Fees.refreshCache();
            RefSupport<List<ClaimProc>> refVar___1 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(proc, patNum, refVar___1, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
            claimProcs = refVar___1.getValue();
            //Validate
            claimProcs = ClaimProcs.refresh(patNum);
            claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum1, subNum1);
            if (claimProc.InsEstTotal != 325)
            {
                throw new Exception("Should be 325. \r\n");
            }
             
            if (claimProc.WriteOffEst != 425)
            {
                throw new Exception("Should be 425.\r\n");
            }
             
            claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum2, subNum2);
            if (claimProc.InsEstTotal != 450)
            {
                throw new Exception("Should be 450.\r\n");
            }
             
            if (claimProc.WriteOffEst != 0)
            {
                throw new Exception("Should be 0. \r\n");
            }
             
            retVal += "2: Passed.  Basic COB with PPOs.  Allowed2 greater than Allowed1.\r\n";
        }
         
        return retVal;
    }

    /**
    * 
    */
    public static String testThree(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 3)
        {
            return "";
        }
         
        String suffix = "3";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        //guarantor is subscriber
        BenefitT.CreateAnnualMax(plan.PlanNum, 1000);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Crowns,100);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Diagnostic,100);
        BenefitT.CreateFrequencyProc(plan.PlanNum, "D0274", BenefitQuantity.Years, 1);
        //BW frequency every 1 year
        PatPlanT.CreatePatPlan(1, pat.PatNum, sub.InsSubNum);
        //proc1 - Crown
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "8", 1100);
        ProcedureT.setPriority(proc1,0);
        //proc2 - 4BW
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D0274", OpenDentBusiness.ProcStat.TP, "8", 50);
        ProcedureT.setPriority(proc2,1);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___2 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___2, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___2.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___3 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___3, claimProcListOld);
        claimProcs = refVar___3.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, sub.InsSubNum);
        //I don't think allowed can be easily tested on the fly, and it's not that important.
        if (claimProc.InsEstTotal != 0)
        {
            throw new Exception("Should be 0. \r\n");
        }
         
        //Insurance should not cover because over annual max.
        retVal += "3: Passed.  Insurance show zero coverage over annual max.  Not affected by a frequency.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testFour(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 4)
        {
            return "";
        }
         
        String suffix = "4";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        Patient pat2 = PatientT.createPatient(suffix);
        PatientT.setGuarantor(pat2,pat.PatNum);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        long planNum = plan.PlanNum;
        InsSub sub = InsSubT.createInsSub(pat.PatNum,planNum);
        //guarantor is subscriber
        long subNum = sub.InsSubNum;
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        PatPlanT.CreatePatPlan(1, pat2.PatNum, subNum);
        //both patients have the same plan
        BenefitT.CreateAnnualMax(planNum, 1000);
        BenefitT.CreateAnnualMaxFamily(planNum, 2500);
        BenefitT.createCategoryPercent(planNum,EbenefitCategory.Crowns,100);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "8", 830);
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //Validate
        RefSupport<List<ClaimProc>> refVar___4 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(proc, patNum, refVar___4, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
        claimProcs = refVar___4.getValue();
        claimProcs = ClaimProcs.refresh(patNum);
        ClaimProc claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum, subNum);
        if (claimProc.InsEstTotal != 830)
        {
            throw new Exception("Should be 830. \r\n");
        }
         
        if (!StringSupport.equals(claimProc.EstimateNote, ""))
        {
            throw new Exception("EstimateNote should be blank.");
        }
         
        return "4: Passed.  When family benefits, does not show 'over annual max' until max reached.\r\n";
    }

    /**
    * 
    */
    public static String testFive(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 5)
        {
            return "";
        }
         
        String suffix = "5";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        Patient pat2 = PatientT.createPatient(suffix);
        PatientT.setGuarantor(pat2,pat.PatNum);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        long planNum = plan.PlanNum;
        InsSub sub = InsSubT.createInsSub(pat.PatNum,planNum);
        //guarantor is subscriber
        long subNum = sub.InsSubNum;
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        PatPlanT.CreatePatPlan(1, pat2.PatNum, subNum);
        //both patients have the same plan
        BenefitT.CreateAnnualMax(planNum, 1000);
        BenefitT.CreateAnnualMaxFamily(planNum, 2500);
        BenefitT.createCategoryPercent(planNum,EbenefitCategory.Crowns,100);
        ClaimProcT.AddInsUsedAdjustment(pat2.PatNum, planNum, 2000, subNum, 0);
        //Adjustment goes on the second patient
        Procedure proc = ProcedureT.CreateProcedure(pat2, "D2750", OpenDentBusiness.ProcStat.TP, "8", 830);
        //crown and testing is for the first patient
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = ClaimProcs.GetHistList(patNum, benefitList, patPlans, planList, DateTime.Today, subList);
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //Validate
        RefSupport<List<ClaimProc>> refVar___5 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(proc, patNum, refVar___5, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
        claimProcs = refVar___5.getValue();
        claimProcs = ClaimProcs.refresh(patNum);
        ClaimProc claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum, subNum);
        if (claimProc.InsEstTotal != 500)
        {
            throw new Exception("Should be 500. \r\n");
        }
         
        if (!StringSupport.equals(claimProc.EstimateNote, "Over family annual max"))
        {
            throw new Exception("EstimateNote not matching expected.");
        }
         
        return "5: Passed.  Both individual and family max taken into account.\r\n";
    }

    //this explains estimate was reduced.
    /**
    * 
    */
    public static String testSix(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 6)
        {
            return "";
        }
         
        String suffix = "6";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        long planNum = plan.PlanNum;
        InsSub sub = InsSubT.createInsSub(pat.PatNum,planNum);
        //guarantor is subscriber
        long subNum = sub.InsSubNum;
        long patPlanNum = PatPlanT.CreatePatPlan(1, pat.PatNum, subNum).PatPlanNum;
        BenefitT.CreateAnnualMax(planNum, 1000);
        BenefitT.CreateLimitation(planNum, EbenefitCategory.Diagnostic, 1000);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D0120", OpenDentBusiness.ProcStat.C, "", 50);
        //An exam
        long procNum = proc.ProcNum;
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.C, "8", 830);
        //create a crown
        ClaimProcT.AddInsPaid(patNum, planNum, procNum, 50, subNum, 0, 0);
        ClaimProcT.AddInsPaid(patNum, planNum, proc2.ProcNum, 400, subNum, 0, 0);
        //Lists
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = ClaimProcs.GetHistList(patNum, benefitList, patPlans, planList, DateTime.Today, subList);
        //Validate
        double insUsed = InsPlans.GetInsUsedDisplay(histList, DateTime.Today, planNum, patPlanNum, -1, planList, benefitList, patNum, subNum);
        if (insUsed != 400)
        {
            throw new Exception("Should be 400. \r\n");
        }
         
        return "6: Passed.  Limitations override more general limitations.\r\n";
    }

    //Patient has one insurance plan, subscriber self. Benefits: annual max 1000, diagnostic max 1000. One completed procedure, an exam for $50. Sent to insurance and insurance paid $50. Ins used should still show 0 because the ins used value should only be concerned with annual max .
    /**
    * 
    */
    public static String testSeven(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 7)
        {
            return "";
        }
         
        String suffix = "7";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 1000);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.RoutinePreventive,100);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Diagnostic,100);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 50);
        BenefitT.CreateDeductible(plan.PlanNum, EbenefitCategory.RoutinePreventive, 25);
        BenefitT.CreateDeductible(plan.PlanNum, EbenefitCategory.Diagnostic, 25);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //proc1 - PerExam
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D0120", OpenDentBusiness.ProcStat.TP, "", 60);
        ProcedureT.setPriority(proc1,0);
        //proc2 - Prophy
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D1110", OpenDentBusiness.ProcStat.TP, "", 70);
        ProcedureT.setPriority(proc2,1);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___6 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___6, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___6.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___7 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___7, claimProcListOld);
        claimProcs = refVar___7.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        if (claimProc.DedEst != 0)
        {
            throw new Exception("Should be 0. \r\n");
        }
         
        //Second procedure should show no deductible.
        retVal += "7: Passed.  A deductible for preventive/diagnostic is only included once.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testEight(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 8)
        {
            return "";
        }
         
        String suffix = "8";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        long feeSchedNum1 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix);
        long feeSchedNum2 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix + "b");
        //Standard Fee
        Fees.refreshCache();
        long codeNum = ProcedureCodes.getCodeNum("D2750");
        Fee fee = Fees.GetFee(codeNum, 53);
        if (fee == null)
        {
            fee = new Fee();
            fee.CodeNum = codeNum;
            fee.FeeSched = 53;
            fee.Amount = 1200;
            Fees.insert(fee);
        }
        else
        {
            fee.Amount = 1200;
            Fees.update(fee);
        } 
        //PPO fees
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum1;
        fee.Amount = 600;
        Fees.insert(fee);
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum2;
        fee.Amount = 800;
        Fees.insert(fee);
        Fees.refreshCache();
        //Carrier
        Carrier carrier = CarrierT.createCarrier(suffix);
        long planNum1 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum1).PlanNum;
        long planNum2 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum2).PlanNum;
        InsSub sub1 = InsSubT.createInsSub(pat.PatNum,planNum1);
        long subNum1 = sub1.InsSubNum;
        InsSub sub2 = InsSubT.createInsSub(pat.PatNum,planNum2);
        long subNum2 = sub2.InsSubNum;
        BenefitT.createCategoryPercent(planNum1,EbenefitCategory.Crowns,50);
        BenefitT.createCategoryPercent(planNum2,EbenefitCategory.Crowns,50);
        BenefitT.CreateAnnualMax(planNum1, 1000);
        BenefitT.CreateAnnualMax(planNum2, 1000);
        PatPlanT.CreatePatPlan(1, patNum, subNum1);
        PatPlanT.CreatePatPlan(2, patNum, subNum2);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "8", Fees.GetAmount0(codeNum, 53));
        //crown on 8
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<Procedure> procList = Procedures.refresh(patNum);
        //Set complete and attach to claim
        ProcedureT.SetComplete(proc, pat, planList, patPlans, claimProcs, benefitList, subList);
        claimProcs = ClaimProcs.refresh(patNum);
        List<Procedure> procsForClaim = new List<Procedure>();
        procsForClaim.Add(proc);
        Claim claim = ClaimT.CreateClaim("P", patPlans, planList, claimProcs, procList, pat, procsForClaim, benefitList, subList);
        //Validate
        String retVal = "";
        if (claim.WriteOff != 500)
        {
            throw new Exception("Should be 500. \r\n");
        }
         
        retVal += "8: Passed.  Completed writeoffs same as estimates for dual PPO ins when Allowed2 greater than Allowed1.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testNine(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 9)
        {
            return "";
        }
         
        String suffix = "9";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 200);
        BenefitT.CreateLimitationProc(plan.PlanNum, "D2161", 2000);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Restorative,80);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //proc1 - D2161 (4-surf amalgam)
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D2161", OpenDentBusiness.ProcStat.TP, "3", 300);
        ProcedureT.setPriority(proc1,0);
        //proc2 - D2160 (3-surf amalgam)
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2160", OpenDentBusiness.ProcStat.TP, "4", 300);
        ProcedureT.setPriority(proc2,1);
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___8 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___8, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___8.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___9 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___9, claimProcListOld);
        claimProcs = refVar___9.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        if (claimProc.InsEstTotal != 200)
        {
            throw new Exception("Should be 200. \r\n");
        }
         
        //Insurance should cover.
        retVal += "9: Passed.  Limitations should override more general limitations for any benefit.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 10)
        {
            return "";
        }
         
        String suffix = "10";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 400);
        BenefitT.CreateFrequencyCategory(plan.PlanNum, EbenefitCategory.RoutinePreventive, BenefitQuantity.Years, 2);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.RoutinePreventive,100);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //procs - D1515 (space maintainers)
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D1515", OpenDentBusiness.ProcStat.TP, "3", 500);
        ProcedureT.setPriority(proc1,0);
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D1515", OpenDentBusiness.ProcStat.TP, "3", 500);
        ProcedureT.setPriority(proc2,1);
        //Procedure proc3=ProcedureT.CreateProcedure(pat,"D1515",ProcStat.TP,"3",500);
        //ProcedureT.SetPriority(proc3,2);
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___10 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___10, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___10.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___11 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___11, claimProcListOld);
        claimProcs = refVar___11.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, subNum);
        if (claimProc1.InsEstTotal != 400)
        {
            throw new Exception("Should be 400. \r\n");
        }
         
        //Insurance should partially cover.
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        if (claimProc2.InsEstTotal != 0)
        {
            throw new Exception("Should be 0. \r\n");
        }
         
        //Insurance should not cover.
        retVal += "10: Passed.  Once max is reached, additional procs show 0 coverage even if preventive frequency exists.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testEleven(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 11)
        {
            return "";
        }
         
        String suffix = "11";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMaxFamily(plan.PlanNum, 400);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Restorative,100);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //procs - D2140 (amalgum fillings)
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D2140", OpenDentBusiness.ProcStat.TP, "18", 500);
        //Procedure proc1=ProcedureT.CreateProcedure(pat,"D1515",ProcStat.TP,"3",500);
        ProcedureT.setPriority(proc1,0);
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2140", OpenDentBusiness.ProcStat.TP, "19", 500);
        //Procedure proc2=ProcedureT.CreateProcedure(pat,"D1515",ProcStat.TP,"3",500);
        ProcedureT.setPriority(proc2,1);
        //Procedure proc3=ProcedureT.CreateProcedure(pat,"D1515",ProcStat.TP,"3",500);
        //ProcedureT.SetPriority(proc3,2);
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___12 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___12, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___12.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___13 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___13, claimProcListOld);
        claimProcs = refVar___13.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, patPlans[0].InsSubNum);
        if (claimProc1.InsEstTotal != 400)
        {
            throw new Exception("Claim 1 was " + claimProc1.InsEstTotal + ", should be 400.\r\n");
        }
         
        //Insurance should partially cover.
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, patPlans[0].InsSubNum);
        if (claimProc2.InsEstTotal != 0)
        {
            throw new Exception("Claim 2 was " + claimProc2.InsEstTotal + ", should be 0.\r\n");
        }
         
        //Insurance should not cover.
        retVal += "11: Passed.  Once family max is reached, additional procs show 0 coverage.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwelve(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 12)
        {
            return "";
        }
         
        String suffix = "12";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        Patient pat2 = PatientT.createPatient(suffix);
        PatientT.setGuarantor(pat2,pat.PatNum);
        Carrier carrier = CarrierT.createCarrier(suffix);
        long feeSchedNum = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix);
        //Standard Fee
        Fees.refreshCache();
        long codeNum = ProcedureCodes.getCodeNum("D2750");
        Fee fee = Fees.GetFee(codeNum, 53);
        if (fee == null)
        {
            fee = new Fee();
            fee.CodeNum = codeNum;
            fee.FeeSched = 53;
            fee.Amount = 1400;
            Fees.insert(fee);
        }
        else
        {
            fee.Amount = 1400;
            Fees.update(fee);
        } 
        //PPO fees
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum;
        fee.Amount = 1100;
        Fees.insert(fee);
        Fees.refreshCache();
        InsPlan plan = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum);
        long planNum = plan.PlanNum;
        InsSub sub = InsSubT.createInsSub(pat.PatNum,planNum);
        //patient is subscriber for plan 1
        long subNum = sub.InsSubNum;
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        InsSub sub2 = InsSubT.createInsSub(pat2.PatNum,planNum);
        //spouse is subscriber for plan 2
        long subNum2 = sub2.InsSubNum;
        PatPlanT.CreatePatPlan(2, pat.PatNum, subNum2);
        //patient also has spouse's coverage
        BenefitT.CreateAnnualMax(planNum, 1200);
        BenefitT.CreateDeductibleGeneral(planNum, BenefitCoverageLevel.Individual, 0);
        BenefitT.createCategoryPercent(planNum,EbenefitCategory.Crowns,100);
        //2700-2799
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "19", 1400);
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        //empty, not used for calcs.
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //empty, not used for calcs.
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        RefSupport<List<ClaimProc>> refVar___14 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(ProcListTP[0], pat.PatNum, refVar___14, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
        claimProcs = refVar___14.getValue();
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___15 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___15, claimProcListOld);
        claimProcs = refVar___15.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc.ProcNum, plan.PlanNum, subNum);
        if (claimProc1.InsEstTotal != 1100)
        {
            throw new Exception("Primary Estimate was " + claimProc1.InsEstTotal + ", should be 1100.\r\n");
        }
         
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc.ProcNum, plan.PlanNum, subNum2);
        if (claimProc2.InsEstTotal != 0)
        {
            throw new Exception("Secondary Estimate was " + claimProc2.InsEstTotal + ", should be 0.\r\n");
        }
         
        //Insurance should not cover.
        retVal += "12: Passed.  Once family max is reached, additional procs show 0 coverage.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testThirteen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 13)
        {
            return "";
        }
         
        String suffix = "13";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        PatPlan patPlan = PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        BenefitT.CreateAnnualMax(plan.PlanNum, 100);
        BenefitT.CreateOrthoMax(plan.PlanNum, 500);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Diagnostic,100);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Orthodontics,100);
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D0140", OpenDentBusiness.ProcStat.C, "", 59);
        //limEx
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D8090", OpenDentBusiness.ProcStat.C, "", 348);
        //Comprehensive ortho
        ClaimProcT.AddInsPaid(pat.PatNum, plan.PlanNum, proc1.ProcNum, 59, subNum, 0, 0);
        ClaimProcT.AddInsPaid(pat.PatNum, plan.PlanNum, proc2.ProcNum, 348, subNum, 0, 0);
        //Lists
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = ClaimProcs.GetHistList(pat.PatNum, benefitList, patPlans, planList, DateTime.Today, subList);
        //Validate
        double insUsed = InsPlans.GetInsUsedDisplay(histList, DateTime.Today, plan.PlanNum, patPlan.PatPlanNum, -1, planList, benefitList, pat.PatNum, subNum);
        if (insUsed != 59)
        {
            throw new Exception("Should be 59. \r\n");
        }
         
        return "13: Passed.  Ortho procedures should not affect insurance used section at lower right of TP module.\r\n";
    }

    //public static string TestFourteen(int specificTest) {//This was taken out of the manual because it was a duplicate of Unit Test 1 but expected a different result.
    //  if(specificTest != 0 && specificTest !=14){
    //    return"";
    //  }
    //  string suffix="14";
    //  Patient pat=PatientT.CreatePatient(suffix);
    //  long patNum=pat.PatNum;
    //  long feeSchedNum1=FeeSchedT.CreateFeeSched(FeeScheduleType.Normal,suffix);
    //  long feeSchedNum2=FeeSchedT.CreateFeeSched(FeeScheduleType.Normal,suffix+"b");
    //  //Standard Fee
    //  Fees.RefreshCache();
    //  long codeNum=ProcedureCodes.GetCodeNum("D7140");
    //  Fee fee=Fees.GetFee(codeNum,53);
    //  if(fee==null) {
    //    fee=new Fee();
    //    fee.CodeNum=codeNum;
    //    fee.FeeSched=53;
    //    fee.Amount=140;
    //    Fees.Insert(fee);
    //  }
    //  else {
    //    fee.Amount=140;
    //    Fees.Update(fee);
    //  }
    //  //PPO fees
    //  fee=new Fee();
    //  fee.CodeNum=codeNum;
    //  fee.FeeSched=feeSchedNum1;
    //  fee.Amount=136;
    //  Fees.Insert(fee);
    //  fee=new Fee();
    //  fee.CodeNum=codeNum;
    //  fee.FeeSched=feeSchedNum2;
    //  fee.Amount=77;
    //  Fees.Insert(fee);
    //  Fees.RefreshCache();
    //  //Carrier
    //  Carrier carrier=CarrierT.CreateCarrier(suffix);
    //  long planNum1=InsPlanT.CreateInsPlanPPO(carrier.CarrierNum,feeSchedNum1).PlanNum;
    //  long planNum2=InsPlanT.CreateInsPlanPPO(carrier.CarrierNum,feeSchedNum2).PlanNum;
    //  InsSub sub1=InsSubT.CreateInsSub(pat.PatNum,planNum1);
    //  long subNum1=sub1.InsSubNum;
    //  InsSub sub2=InsSubT.CreateInsSub(pat.PatNum,planNum2);
    //  long subNum2=sub2.InsSubNum;
    //  BenefitT.CreateCategoryPercent(planNum1,EbenefitCategory.OralSurgery,50);
    //  BenefitT.CreateCategoryPercent(planNum2,EbenefitCategory.OralSurgery,100);
    //  PatPlanT.CreatePatPlan(1,patNum,subNum1);
    //  PatPlanT.CreatePatPlan(2,patNum,subNum2);
    //  Procedure proc=ProcedureT.CreateProcedure(pat,"D7140",ProcStat.TP,"8",Fees.GetAmount0(codeNum,53));//extraction on 8
    //  long procNum=proc.ProcNum;
    //  //Lists
    //  List<ClaimProc> claimProcs=ClaimProcs.Refresh(patNum);
    //  Family fam=Patients.GetFamily(patNum);
    //  List<InsSub> subList=InsSubs.RefreshForFam(fam);
    //  List<InsPlan> planList=InsPlans.RefreshForSubList(subList);
    //  List<PatPlan> patPlans=PatPlans.Refresh(patNum);
    //  List<Benefit> benefitList=Benefits.Refresh(patPlans,subList);
    //  List<ClaimProcHist> histList=new List<ClaimProcHist>();
    //  List<ClaimProcHist> loopList=new List<ClaimProcHist>();
    //  //Validate
    //  ClaimProc claimProc;
    //  Procedures.ComputeEstimates(proc,patNum,ref claimProcs,false,planList,patPlans,benefitList,histList,loopList,true,pat.Age,subList);
    //  claimProcs=ClaimProcs.Refresh(patNum);
    //  claimProc=ClaimProcs.GetEstimate(claimProcs,procNum,planNum1,subNum1);
    //  if(claimProc.InsEstTotal!=68) {
    //    throw new Exception("Should be 68. \r\n");
    //  }
    //  if(claimProc.WriteOffEst!=4) {
    //    throw new Exception("Should be 4. \r\n");
    //  }
    //  claimProc=ClaimProcs.GetEstimate(claimProcs,procNum,planNum2,subNum2);
    //  if(claimProc.InsEstTotal!=9) {
    //    throw new Exception("Should be 9. \r\n");
    //  }
    //  if(claimProc.WriteOffEst!=59) {
    //    throw new Exception("Writeoff should be 59. \r\n");
    //  }
    //  return "14: Passed.  Claim proc estimates for dual PPO ins.  Writeoff2 not zero.\r\n";
    //}
    /**
    * 
    */
    public static String testFourteen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 15)
        {
            return "";
        }
         
        String suffix = "14";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        long feeSchedNum1 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix);
        long feeSchedNum2 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix + "b");
        //Standard Fee
        Fees.refreshCache();
        long codeNum = ProcedureCodes.getCodeNum("D2160");
        Fee fee = Fees.GetFee(codeNum, 53);
        if (fee == null)
        {
            fee = new Fee();
            fee.CodeNum = codeNum;
            fee.FeeSched = 53;
            fee.Amount = 1279;
            Fees.insert(fee);
        }
        else
        {
            fee.Amount = 1279;
            Fees.update(fee);
        } 
        //PPO fees
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum1;
        fee.Amount = 1279;
        Fees.insert(fee);
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum2;
        fee.Amount = 110;
        Fees.insert(fee);
        Fees.refreshCache();
        //Carrier
        Carrier carrier = CarrierT.createCarrier(suffix);
        long planNum1 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum1).PlanNum;
        long planNum2 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum2).PlanNum;
        InsSub sub1 = InsSubT.createInsSub(pat.PatNum,planNum1);
        long subNum1 = sub1.InsSubNum;
        InsSub sub2 = InsSubT.createInsSub(pat.PatNum,planNum2);
        long subNum2 = sub2.InsSubNum;
        BenefitT.createCategoryPercent(planNum1,EbenefitCategory.Restorative,80);
        BenefitT.createCategoryPercent(planNum2,EbenefitCategory.Restorative,80);
        BenefitT.CreateAnnualMax(planNum1, 1200);
        BenefitT.CreateAnnualMax(planNum2, 1200);
        PatPlanT.CreatePatPlan(1, patNum, subNum1);
        PatPlanT.CreatePatPlan(2, patNum, subNum2);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2160", OpenDentBusiness.ProcStat.TP, "19", Fees.GetAmount0(codeNum, 53));
        //amalgam on 19
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        //empty, not used for calcs.
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //empty, not used for calcs.
        List<Procedure> procList = Procedures.refresh(patNum);
        Procedure[] ProcListTP = Procedures.GetListTP(procList);
        //sorted by priority, then toothnum
        //Set complete and attach to claim
        ProcedureT.SetComplete(proc, pat, planList, patPlans, claimProcs, benefitList, subList);
        claimProcs = ClaimProcs.refresh(patNum);
        List<Procedure> procsForClaim = new List<Procedure>();
        procsForClaim.Add(proc);
        Claim claim1 = ClaimT.CreateClaim("P", patPlans, planList, claimProcs, procList, pat, procsForClaim, benefitList, subList);
        Claim claim2 = ClaimT.CreateClaim("S", patPlans, planList, claimProcs, procList, pat, procsForClaim, benefitList, subList);
        //Validate
        String retVal = "";
        RefSupport<List<ClaimProc>> refVar___16 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(ProcListTP[0], pat.PatNum, refVar___16, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
        claimProcs = refVar___16.getValue();
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___17 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___17, claimProcListOld);
        claimProcs = refVar___17.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc.ProcNum, planNum1, subNum1);
        if (claimProc1.InsEstTotal != 1023.20)
        {
            throw new Exception("Primary Estimate was " + claimProc1.InsEstTotal + ", should be 1023.20.\r\n");
        }
         
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc.ProcNum, planNum2, subNum2);
        /*Is this ok, or do we need to take another look?
        			if(claimProc2.WriteOff!=0) {//Insurance should not cover.
        				throw new Exception("Secondary writeoff was "+claimProc2.WriteOff+", should be 0.\r\n");
        			}
        			if(claimProc2.InsEstTotal!=0) {//Insurance should not cover.
        				throw new Exception("Secondary Estimate was "+claimProc2.InsEstTotal+", should be 0.\r\n");
        			}*/
        retVal += "14: Passed. Primary estimate are not affected by secondary claim.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testFifteen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 15)
        {
            return "";
        }
         
        String suffix = "15";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        BenefitT.CreateAnnualMax(plan.PlanNum, 1000);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 50);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.RoutinePreventive,100);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Diagnostic,100);
        BenefitT.CreateDeductible(plan.PlanNum, EbenefitCategory.RoutinePreventive, 0);
        BenefitT.CreateDeductible(plan.PlanNum, EbenefitCategory.Diagnostic, 0);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Restorative,80);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Endodontics,80);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Periodontics,80);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.OralSurgery,80);
        BenefitT.CreateDeductible(plan.PlanNum, "D0330", 45);
        PatPlanT.CreatePatPlan(1, pat.PatNum, sub.InsSubNum);
        //proc1 - Pano
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D0330", OpenDentBusiness.ProcStat.TP, "", 95);
        ProcedureT.setPriority(proc1,0);
        //proc2 - Amalg
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2150", OpenDentBusiness.ProcStat.TP, "30", 200);
        ProcedureT.setPriority(proc2,1);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___18 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___18, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___18.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___19 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___19, claimProcListOld);
        claimProcs = refVar___19.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, sub.InsSubNum);
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, sub.InsSubNum);
        if (claimProc1.DedEst != 45)
        {
            throw new Exception("Estimate 1 should be 45. Is " + claimProc1.DedEst + ".\r\n");
        }
         
        if (claimProc2.DedEst != 5)
        {
            throw new Exception("Estimate 2 should be 5. Is " + claimProc2.DedEst + ".\r\n");
        }
         
        retVal += "15: Passed. Deductibles can be created to override the regular deductible.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testSixteen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 16)
        {
            return "";
        }
         
        String suffix = "16";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        //guarantor is subscriber
        //BenefitT.CreateAnnualMax(plan.PlanNum,1000);//Irrelevant benefits bog down debugging.
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 50);
        //BenefitT.CreateCategoryPercent(plan.PlanNum,EbenefitCategory.RoutinePreventive,100);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Diagnostic,100);
        //BenefitT.CreateDeductible(plan.PlanNum,EbenefitCategory.RoutinePreventive,0);
        BenefitT.CreateDeductible(plan.PlanNum, EbenefitCategory.Diagnostic, 0);
        //BenefitT.CreateCategoryPercent(plan.PlanNum,EbenefitCategory.Restorative,80);
        //BenefitT.CreateCategoryPercent(plan.PlanNum,EbenefitCategory.Endodontics,80);
        //BenefitT.CreateCategoryPercent(plan.PlanNum,EbenefitCategory.Periodontics,80);
        //BenefitT.CreateCategoryPercent(plan.PlanNum,EbenefitCategory.OralSurgery,80);
        BenefitT.CreateDeductible(plan.PlanNum, "D0330", 45);
        BenefitT.CreateDeductible(plan.PlanNum, "D0220", 25);
        PatPlanT.CreatePatPlan(1, pat.PatNum, sub.InsSubNum);
        //proc1 - Pano
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D0330", OpenDentBusiness.ProcStat.TP, "", 100);
        ProcedureT.setPriority(proc1,0);
        //proc2 - Intraoral - periapical first film
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D0220", OpenDentBusiness.ProcStat.TP, "", 75);
        ProcedureT.setPriority(proc2,1);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___20 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___20, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___20.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___21 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___21, claimProcListOld);
        claimProcs = refVar___21.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, sub.InsSubNum);
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, sub.InsSubNum);
        //
        if (claimProc1.DedEst != 45)
        {
            throw new Exception("Estimate 1 should be 45. Is " + claimProc1.DedEst + ".\r\n");
        }
         
        if (claimProc2.DedEst != 5)
        {
            throw new Exception("Estimate 2 should be 5. Is " + claimProc2.DedEst + ".\r\n");
        }
         
        retVal += "16: Passed. Multiple deductibles for categories do not exceed the regular deductible.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testSeventeen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 17)
        {
            return "";
        }
         
        String suffix = "17";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        long feeSchedNum1 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix);
        long feeSchedNum2 = FeeSchedT.createFeeSched(FeeScheduleType.Normal,suffix + "b");
        //Standard Fee
        Fees.refreshCache();
        long codeNum = ProcedureCodes.getCodeNum("D2750");
        Fee fee = Fees.GetFee(codeNum, 53);
        if (fee == null)
        {
            fee = new Fee();
            fee.CodeNum = codeNum;
            fee.FeeSched = 53;
            fee.Amount = 1200;
            Fees.insert(fee);
        }
        else
        {
            fee.Amount = 1200;
            Fees.update(fee);
        } 
        //PPO fees
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum1;
        fee.Amount = 900;
        Fees.insert(fee);
        fee = new Fee();
        fee.CodeNum = codeNum;
        fee.FeeSched = feeSchedNum2;
        fee.Amount = 650;
        Fees.insert(fee);
        Fees.refreshCache();
        //Carrier
        Carrier carrier = CarrierT.createCarrier(suffix);
        long planNum1 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum1,EnumCobRule.Standard).PlanNum;
        long planNum2 = InsPlanT.createInsPlanPPO(carrier.CarrierNum,feeSchedNum2,EnumCobRule.Standard).PlanNum;
        InsSub sub1 = InsSubT.createInsSub(pat.PatNum,planNum1);
        long subNum1 = sub1.InsSubNum;
        InsSub sub2 = InsSubT.createInsSub(pat.PatNum,planNum2);
        long subNum2 = sub2.InsSubNum;
        BenefitT.createCategoryPercent(planNum1,EbenefitCategory.Crowns,50);
        BenefitT.createCategoryPercent(planNum2,EbenefitCategory.Crowns,50);
        PatPlanT.CreatePatPlan(1, patNum, subNum1);
        PatPlanT.CreatePatPlan(2, patNum, subNum2);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "8", Fees.GetAmount0(codeNum, 53));
        //crown on 8
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //Validate
        String retVal = "";
        ClaimProc claimProc;
        //Test 17 Part 1 (copied from Unit Test 1)----------------------------------------------------------------------------------------------------
        RefSupport<List<ClaimProc>> refVar___22 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(proc, patNum, refVar___22, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
        claimProcs = refVar___22.getValue();
        claimProcs = ClaimProcs.refresh(patNum);
        claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum1, subNum1);
        //I don't think allowed can be easily tested on the fly, and it's not that important.
        if (claimProc.InsEstTotal != 450)
        {
            throw new Exception("Should be 450. \r\n");
        }
         
        if (claimProc.WriteOffEst != 300)
        {
            throw new Exception("Should be 300. \r\n");
        }
         
        claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum2, subNum2);
        if (claimProc.InsEstTotal != 325)
        {
            throw new Exception("Should be 325. \r\n");
        }
         
        if (claimProc.WriteOffEst != 0)
        {
            throw new Exception("Should be 0. \r\n");
        }
         
        //Test 17 Part 2 (copied from Unit Test 2)----------------------------------------------------------------------------------------------------
        //switch the fees
        fee = Fees.getFee(codeNum,feeSchedNum1);
        fee.Amount = 650;
        Fees.update(fee);
        fee = Fees.getFee(codeNum,feeSchedNum2);
        fee.Amount = 900;
        Fees.update(fee);
        Fees.refreshCache();
        RefSupport<List<ClaimProc>> refVar___23 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(proc, patNum, refVar___23, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
        claimProcs = refVar___23.getValue();
        //Validate
        claimProcs = ClaimProcs.refresh(patNum);
        claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum1, subNum1);
        if (claimProc.InsEstTotal != 325)
        {
            throw new Exception("Should be 325. \r\n");
        }
         
        if (claimProc.WriteOffEst != 550)
        {
            throw new Exception("Should be 550. \r\n");
        }
         
        claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum2, subNum2);
        if (claimProc.InsEstTotal != 325)
        {
            throw new Exception("Should be 325. \r\n");
        }
         
        if (claimProc.WriteOffEst != 0)
        {
            throw new Exception("Should be 0. \r\n");
        }
         
        retVal += "17: Passed.  Standard COB with PPOs.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testEighteen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 18)
        {
            return "";
        }
         
        String suffix = "18";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        //long feeSchedNum1=FeeSchedT.CreateFeeSched(FeeScheduleType.Normal,suffix);
        //long feeSchedNum2=FeeSchedT.CreateFeeSched(FeeScheduleType.Normal,suffix+"b");
        //Carrier
        Carrier carrier = CarrierT.createCarrier(suffix);
        long planNum1 = InsPlanT.createInsPlan(carrier.CarrierNum,EnumCobRule.CarveOut).PlanNum;
        long planNum2 = InsPlanT.createInsPlan(carrier.CarrierNum,EnumCobRule.CarveOut).PlanNum;
        InsSub sub1 = InsSubT.createInsSub(pat.PatNum,planNum1);
        long subNum1 = sub1.InsSubNum;
        InsSub sub2 = InsSubT.createInsSub(pat.PatNum,planNum2);
        long subNum2 = sub2.InsSubNum;
        BenefitT.createCategoryPercent(planNum1,EbenefitCategory.Crowns,50);
        BenefitT.createCategoryPercent(planNum2,EbenefitCategory.Crowns,75);
        PatPlanT.CreatePatPlan(1, patNum, subNum1);
        PatPlanT.CreatePatPlan(2, patNum, subNum2);
        Procedure proc = ProcedureT.CreateProcedure(pat, "D2750", OpenDentBusiness.ProcStat.TP, "8", 1200);
        //crown on 8
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //Validate
        String retVal = "";
        ClaimProc claimProc;
        //Test 18 Part 1 (copied from Unit Test 1)----------------------------------------------------------------------------------------------------
        RefSupport<List<ClaimProc>> refVar___24 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(proc, patNum, refVar___24, false, planList, patPlans, benefitList, histList, loopList, true, pat.getAge(), subList);
        claimProcs = refVar___24.getValue();
        claimProcs = ClaimProcs.refresh(patNum);
        claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum1, subNum1);
        //I don't think allowed can be easily tested on the fly, and it's not that important.
        if (claimProc.InsEstTotal != 600)
        {
            throw new Exception("Should be 600. \r\n");
        }
         
        claimProc = ClaimProcs.GetEstimate(claimProcs, procNum, planNum2, subNum2);
        if (claimProc.InsEstTotal != 300)
        {
            throw new Exception("Should be 300. \r\n");
        }
         
        retVal += "18: Passed. CarveOut using category percentage.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testNineteen(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 19)
        {
            return "";
        }
         
        String suffix = "19";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier1 = CarrierT.createCarrier(suffix);
        Carrier carrier2 = CarrierT.createCarrier(suffix);
        InsPlan plan1 = InsPlanT.createInsPlan(carrier1.CarrierNum);
        InsPlan plan2 = InsPlanT.createInsPlan(carrier2.CarrierNum);
        InsSub sub1 = InsSubT.createInsSub(pat.PatNum,plan1.PlanNum);
        InsSub sub2 = InsSubT.createInsSub(pat.PatNum,plan2.PlanNum);
        long subNum1 = sub1.InsSubNum;
        long subNum2 = sub2.InsSubNum;
        //plans
        BenefitT.createCategoryPercent(plan1.PlanNum,EbenefitCategory.Diagnostic,50);
        BenefitT.createCategoryPercent(plan2.PlanNum,EbenefitCategory.Diagnostic,50);
        BenefitT.CreateDeductibleGeneral(plan1.PlanNum, BenefitCoverageLevel.Individual, 50);
        BenefitT.CreateDeductibleGeneral(plan2.PlanNum, BenefitCoverageLevel.Individual, 50);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum1);
        PatPlanT.CreatePatPlan(2, pat.PatNum, subNum2);
        //proc1 - PerExam
        Procedure proc = ProcedureT.CreateProcedure(pat, "D0120", OpenDentBusiness.ProcStat.TP, "", 150);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___25 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___25, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___25.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___26 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___26, claimProcListOld);
        claimProcs = refVar___26.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc.ProcNum, plan1.PlanNum, subNum1);
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc.ProcNum, plan2.PlanNum, subNum2);
        if (claimProc1.DedEst != 50)
        {
            throw new Exception("Should be 50. \r\n");
        }
         
        //$50 deductible
        if (claimProc1.InsEstTotal != 50)
        {
            throw new Exception("Should be 50. \r\n");
        }
         
        //Ins1 pays 40% of (fee - deductible) = .4 * (150 - 50)
        if (claimProc2.DedEst != 50)
        {
            throw new Exception("Should be 50. \r\n");
        }
         
        //$50 deductible
        if (claimProc2.InsEstTotal != 50)
        {
            throw new Exception("Should be 50. \r\n");
        }
         
        //Ins2 pays
        retVal += "19: Passed.  Multiple deductibles are accounted for.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwenty(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 20)
        {
            return "";
        }
         
        String suffix = "20";
        Patient pat = PatientT.createPatient(suffix);
        //guarantor
        long patNum = pat.PatNum;
        Patient pat2 = PatientT.createPatient(suffix);
        PatientT.setGuarantor(pat2,pat.PatNum);
        Patient pat3 = PatientT.createPatient(suffix);
        PatientT.setGuarantor(pat3,pat.PatNum);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        long planNum = plan.PlanNum;
        InsSub sub = InsSubT.createInsSub(pat.PatNum,planNum);
        //guarantor is subscriber
        long subNum = sub.InsSubNum;
        PatPlan patPlan = PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //all three patients have the same plan
        PatPlan patPlan2 = PatPlanT.CreatePatPlan(1, pat2.PatNum, subNum);
        //all three patients have the same plan
        PatPlan patPlan3 = PatPlanT.CreatePatPlan(1, pat3.PatNum, subNum);
        //all three patients have the same plan
        BenefitT.CreateDeductibleGeneral(planNum, BenefitCoverageLevel.Individual, 75);
        BenefitT.CreateDeductibleGeneral(planNum, BenefitCoverageLevel.Family, 150);
        ClaimProcT.AddInsUsedAdjustment(pat3.PatNum, planNum, 0, subNum, 75);
        //Adjustment goes on the third patient
        Procedure proc = ProcedureT.CreateProcedure(pat2, "D2750", OpenDentBusiness.ProcStat.C, "20", 1280);
        //proc for second patient with a deductible already applied.
        ClaimProcT.AddInsPaid(pat2.PatNum, planNum, proc.ProcNum, 304, subNum, 50, 597);
        proc = ProcedureT.CreateProcedure(pat, "D4355", OpenDentBusiness.ProcStat.TP, "", 135);
        //proc is for the first patient
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = ClaimProcs.GetHistList(patNum, benefitList, patPlans, planList, DateTime.Today, subList);
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        //Validate
        List<ClaimProcHist> HistList = ClaimProcs.GetHistList(pat.PatNum, benefitList, patPlans, planList, DateTime.Today, subList);
        double dedFam = Benefits.GetDeductGeneralDisplay(benefitList, planNum, patPlan.PatPlanNum, BenefitCoverageLevel.Family);
        double ded = Benefits.GetDeductGeneralDisplay(benefitList, planNum, patPlan.PatPlanNum, BenefitCoverageLevel.Individual);
        double dedRem = InsPlans.GetDedRemainDisplay(HistList, DateTime.Today, planNum, patPlan.PatPlanNum, -1, planList, pat.PatNum, ded, dedFam);
        //test family and individual deductible together
        if (dedRem != 25)
        {
            throw new Exception("Guarantor combination deductible remaining should be 25.\r\n");
        }
         
        //guarantor deductible
        dedRem = InsPlans.GetDedRemainDisplay(HistList, DateTime.Today, planNum, patPlan.PatPlanNum, -1, planList, pat.PatNum, ded, -1);
        //test individual deductible by itself
        if (dedRem != 75)
        {
            throw new Exception("Guarantor individual deductible remaining should be 75.\r\n");
        }
         
        return "20: Passed.  Both individual and family deductibles taken into account.\r\n";
    }

    //guarantor deductible
    /**
    * 
    */
    public static String testTwentyOne(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 21)
        {
            return "";
        }
         
        String suffix = "21";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        //guarantor is subscriber
        BenefitT.CreateAnnualMax(plan.PlanNum, 1000);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 50);
        PatPlanT.CreatePatPlan(1, pat.PatNum, sub.InsSubNum);
        //proc - Exam
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D0120", OpenDentBusiness.ProcStat.TP, "", 55);
        ProcedureT.setPriority(proc1,0);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___27 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___27, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___27.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___28 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___28, claimProcListOld);
        claimProcs = refVar___28.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, sub.InsSubNum);
        //Check
        if (claimProc1.DedEst != 0)
        {
            throw new Exception("Estimated deduction should be 0, is " + claimProc1.DedEst + ".\r\n");
        }
         
        retVal += "21: Passed. Deductibles are not applied to procedures that are not covered.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwentyTwo(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 22)
        {
            return "";
        }
         
        return "22: Deprecated";
        String suffix = "22";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        Prefs.updateBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks,true);
        Prefs.refreshCache();
        TimeCardRuleT.CreatePMTimeRule(emp.EmployeeNum, TimeSpan.FromHours(16));
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddHours(8), startDate.AddHours(16).AddMinutes(40));
        ClockEventT.InsertBreak(emp.EmployeeNum, startDate.AddHours(11), 40);
        TimeCardRules.calculateDailyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        if (ClockEvents.getOne(clockEvent1).AdjustAuto != TimeSpan.FromMinutes(-10))
        {
            throw new Exception("Clock adjustment should be -10 minutes, instead it is " + ClockEvents.getOne(clockEvent1).AdjustAuto.TotalMinutes + " minutes.\r\n");
        }
         
        if (ClockEvents.getOne(clockEvent1).OTimeAuto != TimeSpan.FromMinutes(40))
        {
            throw new Exception("Clock ovetime should be 40 minutes, instead it is " + ClockEvents.getOne(clockEvent1).OTimeAuto.TotalMinutes + " minutes.\r\n");
        }
         
        retVal += "22: Passed. Overtime calculated properly after time of day.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwentyThree(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 23)
        {
            return "";
        }
         
        return "23: Deprecated";
        String suffix = "23";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        Prefs.updateBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks,true);
        TimeCardRuleT.CreateAMTimeRule(emp.EmployeeNum, TimeSpan.FromHours(7.5));
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddHours(6), startDate.AddHours(16));
        ClockEventT.InsertBreak(emp.EmployeeNum, startDate.AddHours(11), 40);
        TimeCardRules.calculateDailyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        if (ClockEvents.getOne(clockEvent1).AdjustAuto != TimeSpan.FromMinutes(-10))
        {
            throw new Exception("Clock adjustment should be -10 minutes, instead it is " + ClockEvents.getOne(clockEvent1).AdjustAuto.TotalMinutes + " minutes.\r\n");
        }
         
        if (ClockEvents.getOne(clockEvent1).OTimeAuto != TimeSpan.FromMinutes(90))
        {
            throw new Exception("Clock ovetime should be 90 minutes, instead it is " + ClockEvents.getOne(clockEvent1).OTimeAuto.TotalMinutes + " minutes.\r\n");
        }
         
        retVal += "23: Passed. Overtime calculated properly before time of day.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwentyFour(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 24)
        {
            return "";
        }
         
        String suffix = "24";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        Prefs.updateBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks,true);
        TimeCardRuleT.CreateHoursTimeRule(emp.EmployeeNum, TimeSpan.FromHours(10));
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddHours(8), startDate.AddHours(13));
        long clockEvent2 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddHours(14), startDate.AddHours(21));
        ClockEventT.InsertBreak(emp.EmployeeNum, startDate.AddHours(10), 20);
        ClockEventT.InsertBreak(emp.EmployeeNum, startDate.AddHours(16), 20);
        TimeCardRules.calculateDailyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        if (ClockEvents.getOne(clockEvent2).AdjustAuto != TimeSpan.FromMinutes(-10))
        {
            throw new Exception("Clock adjustment should be -10 minutes, instead it is " + ClockEvents.getOne(clockEvent2).AdjustAuto.TotalMinutes + " minutes.\r\n");
        }
         
        if (ClockEvents.getOne(clockEvent2).OTimeAuto != TimeSpan.FromMinutes(110))
        {
            throw new Exception("Clock ovetime should be 110 minutes, instead it is " + ClockEvents.getOne(clockEvent2).OTimeAuto.TotalMinutes + " minutes.\r\n");
        }
         
        retVal += "24: Passed. Overtime calculated properly for total hours per day.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwentyFive(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 25)
        {
            return "";
        }
         
        String suffix = "25";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(0).AddHours(6), startDate.AddDays(0).AddHours(17));
        long clockEvent2 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(1).AddHours(6), startDate.AddDays(1).AddHours(17));
        long clockEvent3 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(2).AddHours(6), startDate.AddDays(2).AddHours(17));
        long clockEvent4 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(3).AddHours(6), startDate.AddDays(3).AddHours(17));
        TimeCardRules.calculateWeeklyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        TimeAdjust result = TimeAdjusts.Refresh(emp.EmployeeNum, startDate, startDate.AddDays(13))[0];
        if (result.RegHours != TimeSpan.FromHours(-4))
        {
            throw new Exception("Time adjustment to regular hours should be -4 hours, instead it is " + result.RegHours.TotalHours + " hours.\r\n");
        }
         
        if (result.OTimeHours != TimeSpan.FromHours(4))
        {
            throw new Exception("Time adjustment to OT hours should be 4 hours, instead it is " + result.OTimeHours.TotalHours + " hours.\r\n");
        }
         
        retVal += "25: Passed. Overtime calculated properly for normal 40 hour work week.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwentySix(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 26)
        {
            return "";
        }
         
        String suffix = "26";
        DateTime startDate = DateTime.Parse("2001-02-01");
        //This will create a pay period that splits a work week.
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriod payP2 = PayPeriodT.CreateTwoWeekPayPeriodIfNotExists(startDate.AddDays(14));
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(10).AddHours(6), startDate.AddDays(10).AddHours(17));
        long clockEvent2 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(11).AddHours(6), startDate.AddDays(11).AddHours(17));
        long clockEvent3 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(12).AddHours(6), startDate.AddDays(12).AddHours(17));
        //new pay period
        long clockEvent4 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(14).AddHours(6), startDate.AddDays(14).AddHours(17));
        TimeCardRules.calculateWeeklyOvertime(emp,payP1.DateStart,payP1.DateStop);
        TimeCardRules.calculateWeeklyOvertime(emp,payP2.DateStart,payP2.DateStop);
        //Validate
        String retVal = "";
        //Check
        TimeAdjust result = TimeAdjusts.Refresh(emp.EmployeeNum, startDate, startDate.AddDays(28))[0];
        if (result.RegHours != TimeSpan.FromHours(-4))
        {
            throw new Exception("Time adjustment to regular hours should be -4 hours, instead it is " + result.RegHours.TotalHours + " hours.\r\n");
        }
         
        if (result.OTimeHours != TimeSpan.FromHours(4))
        {
            throw new Exception("Time adjustment to OT hours should be 4 hours, instead it is " + result.OTimeHours.TotalHours + " hours.\r\n");
        }
         
        retVal += "26: Passed. Overtime calculated properly for work week spanning 2 pay periods.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testTwentySeven(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 27)
        {
            return "";
        }
         
        String suffix = "27";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,3);
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(0).AddHours(6), startDate.AddDays(0).AddHours(17));
        long clockEvent2 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(1).AddHours(6), startDate.AddDays(1).AddHours(17));
        //new work week
        long clockEvent3 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(2).AddHours(6), startDate.AddDays(2).AddHours(17));
        long clockEvent4 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(3).AddHours(6), startDate.AddDays(3).AddHours(17));
        long clockEvent5 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(4).AddHours(6), startDate.AddDays(4).AddHours(17));
        long clockEvent6 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddDays(5).AddHours(6), startDate.AddDays(5).AddHours(17));
        TimeCardRules.calculateWeeklyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        TimeAdjust result = TimeAdjusts.Refresh(emp.EmployeeNum, startDate, startDate.AddDays(28))[0];
        if (result.RegHours != TimeSpan.FromHours(-4))
        {
            throw new Exception("Time adjustment to regular hours should be -4 hours, instead it is " + result.RegHours.TotalHours + " hours.\r\n");
        }
         
        if (result.OTimeHours != TimeSpan.FromHours(4))
        {
            throw new Exception("Time adjustment to OT hours should be 4 hours, instead it is " + result.OTimeHours.TotalHours + " hours.\r\n");
        }
         
        retVal += "27: Passed. Overtime calculated properly for work week not starting on Sunday.\r\n";
        return retVal;
    }

    /**
    * This unit test is the first one that looks at the values showing in the claimproc window.  This catches situations where the only "bug" is just a display issue in that window. Validates the values in the claimproc window when opened from the Chart module.
    */
    public static String testTwentyEight(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 28)
        {
            return "";
        }
         
        String suffix = "28";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 1300);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Crowns,50);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 25);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //proc1 - crown
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D2790", OpenDentBusiness.ProcStat.TP, "1", 800);
        //Tooth 1
        ProcedureT.setPriority(proc1,0);
        //Priority 1
        //proc2 - crown
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2790", OpenDentBusiness.ProcStat.TP, "9", 800);
        //Tooth 9
        ProcedureT.setPriority(proc2,1);
        //Priority 2
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        //Mimick the TP module estimate calculations when the TP module is loaded. We expect the user to refresh the TP module to calculate insurance estimates for all other areas of the program.
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___29 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___29, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___29.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //Save changes in the list to the database, just like the TP module does when loaded. Then the values can be referenced elsewhere in the program instead of recalculating.
        RefSupport<List<ClaimProc>> refVar___30 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___30, claimProcListOld);
        claimProcs = refVar___30.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        //Validate the estimates within the Edit Claim Proc window are correct when opened from inside of the Chart module by passing in a null histlist and a null looplist just like the Chart module would.
        List<ClaimProcHist> histListNull = null;
        List<ClaimProcHist> loopListNull = null;
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, subNum);
        RefSupport<List<ClaimProcHist>> refVar___31 = new RefSupport<List<ClaimProcHist>>(loopListNull);
        FormClaimProc formCP1 = new FormClaimProc(claimProc1, proc1, fam, pat, planList, histListNull, refVar___31, patPlans, false, subList);
        loopListNull = refVar___31.getValue();
        formCP1.initialize();
        String dedEst1 = formCP1.getTextValue("textDedEst");
        if (!StringSupport.equals(dedEst1, "25.00"))
        {
            throw new Exception("Deductible estimate in Claim Proc Edit window is $" + dedEst1 + " but should be $25.00 for proc1 from Chart module. \r\n");
        }
         
        String patPortCP1 = formCP1.getTextValue("textPatPortion1");
        if (!StringSupport.equals(patPortCP1, "412.50"))
        {
            throw new Exception("Estimated patient portion in Claim Proc Edit window is $" + patPortCP1 + " but should be $412.50 for proc1 from Chart module. \r\n");
        }
         
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        RefSupport<List<ClaimProcHist>> refVar___32 = new RefSupport<List<ClaimProcHist>>(loopListNull);
        FormClaimProc formCP2 = new FormClaimProc(claimProc2, proc2, fam, pat, planList, histListNull, refVar___32, patPlans, false, subList);
        loopListNull = refVar___32.getValue();
        formCP2.initialize();
        String dedEst2 = formCP2.getTextValue("textDedEst");
        if (!StringSupport.equals(dedEst2, "0.00"))
        {
            throw new Exception("Deductible estimate in Claim Proc Edit window is $" + dedEst2 + " but should be $0.00 for proc2 from Chart module. \r\n");
        }
         
        String patPortCP2 = formCP2.getTextValue("textPatPortion1");
        if (!StringSupport.equals(patPortCP2, "400.00"))
        {
            throw new Exception("Estimated patient portion in Claim Proc Edit window is $" + patPortCP2 + " but should be $400.00 for proc2 from Chart module. \r\n");
        }
         
        retVal += "28: Passed.  Claim Procedure Edit window estimates correct from Chart module.\r\n";
        return retVal;
    }

    /**
    * Validates the values in the claimproc window when opened from the Claim Edit window.
    */
    public static String testTwentyNine(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 29)
        {
            return "";
        }
         
        String suffix = "29";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 1300);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Crowns,50);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 25);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //proc1 - crown
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D2790", OpenDentBusiness.ProcStat.C, "1", 800);
        //Tooth 1
        ProcedureT.setPriority(proc1,0);
        //Priority 1
        //proc2 - crown
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2790", OpenDentBusiness.ProcStat.C, "9", 800);
        //Tooth 9
        ProcedureT.setPriority(proc2,1);
        //Priority 2
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        String retVal = "";
        Claim claim = ClaimT.CreateClaim("P", patPlans, planList, claimProcs, ProcList, pat, ProcList, benefitList, subList);
        //Creates the claim in the same manner as the account module, including estimates.
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        //Validate the estimates as they would appear inside of the Claim Proc Edit window when opened from inside of the Edit Claim window by passing in the null histlist and null looplist that the Claim Edit window would send in.
        List<ClaimProcHist> histList = null;
        List<ClaimProcHist> loopList = null;
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, subNum);
        RefSupport<List<ClaimProcHist>> refVar___33 = new RefSupport<List<ClaimProcHist>>(loopList);
        FormClaimProc formCP1 = new FormClaimProc(claimProc1, proc1, fam, pat, planList, histList, refVar___33, patPlans, false, subList);
        loopList = refVar___33.getValue();
        formCP1.IsInClaim = true;
        formCP1.initialize();
        String dedEst1 = formCP1.getTextValue("textDedEst");
        if (!StringSupport.equals(dedEst1, "25.00"))
        {
            throw new Exception("Deductible estimate in Claim Proc Edit window is $" + dedEst1 + " but should be $25.00 for proc1 from Edit Claim Window. \r\n");
        }
         
        String patPortCP1 = formCP1.getTextValue("textPatPortion1");
        if (!StringSupport.equals(patPortCP1, "412.50"))
        {
            throw new Exception("Estimated patient portion in Claim Proc Edit window is $" + patPortCP1 + " but should be $412.50 for proc1 from Edit Claim Window. \r\n");
        }
         
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        RefSupport<List<ClaimProcHist>> refVar___34 = new RefSupport<List<ClaimProcHist>>(loopList);
        FormClaimProc formCP2 = new FormClaimProc(claimProc2, proc2, fam, pat, planList, histList, refVar___34, patPlans, false, subList);
        loopList = refVar___34.getValue();
        formCP2.IsInClaim = true;
        formCP2.initialize();
        String dedEst2 = formCP2.getTextValue("textDedEst");
        if (!StringSupport.equals(dedEst2, "0.00"))
        {
            throw new Exception("Deductible estimate in Claim Proc Edit window is $" + dedEst2 + " but should be $0.00 for proc2 from Edit Claim Window. \r\n");
        }
         
        String patPortCP2 = formCP2.getTextValue("textPatPortion1");
        if (!StringSupport.equals(patPortCP2, "400.00"))
        {
            throw new Exception("Estimated patient portion in Claim Proc Edit window is $" + patPortCP2 + " but should be $400.00 for proc2 from Edit Claim Window. \r\n");
        }
         
        retVal += "29: Passed.  Claim Procedure Edit window estimates correct from Claim Edit window.\r\n";
        return retVal;
    }

    /**
    * Validates the values in the claimproc window when opened from the TP module.
    */
    public static String testThirty(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 30)
        {
            return "";
        }
         
        String suffix = "30";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 1300);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Crowns,50);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 25);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //proc1 - crown
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D2790", OpenDentBusiness.ProcStat.TP, "1", 800);
        //Tooth 1
        ProcedureT.setPriority(proc1,0);
        //Priority 1
        //proc2 - crown
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D2790", OpenDentBusiness.ProcStat.TP, "9", 800);
        //Tooth 9
        ProcedureT.setPriority(proc2,1);
        //Priority 2
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Validate
        //Mimick the TP module estimate calculations when the TP module is loaded.
        String retVal = "";
        for (int i = 0;i < ProcListTP.Length;i++)
        {
            RefSupport<List<ClaimProc>> refVar___35 = new RefSupport<List<ClaimProc>>(claimProcs);
            Procedures.ComputeEstimates(ProcListTP[i], pat.PatNum, refVar___35, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
            claimProcs = refVar___35.getValue();
            //then, add this information to loopList so that the next procedure is aware of it.
            loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, ProcListTP[i].ProcNum, ProcListTP[i].CodeNum));
        }
        //Save changes in the list to the database, just like the TP module does when loaded.
        RefSupport<List<ClaimProc>> refVar___36 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___36, claimProcListOld);
        claimProcs = refVar___36.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        //Validate the estimates within the Edit Claim Proc window are correct when opened from inside of the TP module by passing in same histlist and loop list that the TP module would.
        histList = ClaimProcs.GetHistList(pat.PatNum, benefitList, patPlans, planList, DateTime.Today, subList);
        //The history list is fetched when the TP module is loaded and is passed in the same for all claimprocs.
        loopList = new List<ClaimProcHist>();
        //Always empty for the first claimproc.
        ClaimProc claimProc1 = ClaimProcs.GetEstimate(claimProcs, proc1.ProcNum, plan.PlanNum, subNum);
        RefSupport<List<ClaimProcHist>> refVar___37 = new RefSupport<List<ClaimProcHist>>(loopList);
        FormClaimProc formCP1 = new FormClaimProc(claimProc1, proc1, fam, pat, planList, histList, refVar___37, patPlans, false, subList);
        loopList = refVar___37.getValue();
        formCP1.initialize();
        String dedEst1 = formCP1.getTextValue("textDedEst");
        if (!StringSupport.equals(dedEst1, "25.00"))
        {
            throw new Exception("Deductible estimate in Claim Proc Edit window is $" + dedEst1 + " but should be $25.00 for proc1 from TP module. \r\n");
        }
         
        String patPortCP1 = formCP1.getTextValue("textPatPortion1");
        if (!StringSupport.equals(patPortCP1, "412.50"))
        {
            throw new Exception("Estimated patient portion in Claim Proc Edit window is $" + patPortCP1 + " but should be $412.50 for proc1 from TP module. \r\n");
        }
         
        ClaimProc claimProc2 = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        histList = ClaimProcs.GetHistList(pat.PatNum, benefitList, patPlans, planList, DateTime.Today, subList);
        //The history list is fetched when the TP module is loaded and is passed in the same for all claimprocs.
        loopList = new List<ClaimProcHist>();
        loopList.AddRange(ClaimProcs.GetHistForProc(claimProcs, proc1.ProcNum, proc1.CodeNum));
        RefSupport<List<ClaimProcHist>> refVar___38 = new RefSupport<List<ClaimProcHist>>(loopList);
        FormClaimProc formCP2 = new FormClaimProc(claimProc2, proc2, fam, pat, planList, histList, refVar___38, patPlans, false, subList);
        loopList = refVar___38.getValue();
        formCP2.initialize();
        String dedEst2 = formCP2.getTextValue("textDedEst");
        if (!StringSupport.equals(dedEst2, "0.00"))
        {
            throw new Exception("Deductible estimate in Claim Proc Edit window is $" + dedEst2 + " but should be $0.00 for proc2 from TP module. \r\n");
        }
         
        String patPortCP2 = formCP2.getTextValue("textPatPortion1");
        if (!StringSupport.equals(patPortCP2, "400.00"))
        {
            throw new Exception("Estimated patient portion in Claim Proc Edit window is $" + patPortCP2 + " but should be $400.00 for proc2 from TP module. \r\n");
        }
         
        retVal += "30: Passed.  Claim Procedure Edit window estimates correct from TP module.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testThirtyOne(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 31)
        {
            return "";
        }
         
        String suffix = "31";
        Patient pat = PatientT.createPatient(suffix);
        long patNum = pat.PatNum;
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        long planNum = plan.PlanNum;
        InsSub sub = InsSubT.createInsSub(pat.PatNum,planNum);
        //guarantor is subscriber
        long subNum = sub.InsSubNum;
        long patPlanNum = PatPlanT.CreatePatPlan(1, pat.PatNum, subNum).PatPlanNum;
        BenefitT.CreateAnnualMax(planNum, 1000);
        BenefitT.createCategoryPercent(planNum,EbenefitCategory.RoutinePreventive,100);
        BenefitT.CreateLimitation(planNum, EbenefitCategory.RoutinePreventive, 1000);
        //Changing this amount would affect patient portion vs ins portion.  But regardless of the amount, this should prevent any pending from showing in the box, which is for general pending only.
        Procedure proc = ProcedureT.CreateProcedure(pat, "D1110", OpenDentBusiness.ProcStat.C, "", 125);
        //Prophy
        long procNum = proc.ProcNum;
        //Lists
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        Family fam = Patients.getFamily(patNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(patNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Claim claim = ClaimT.CreateClaim("P", patPlans, planList, claimProcs, ProcList, pat, ProcList, benefitList, subList);
        //Creates the claim in the same manner as the account module, including estimates and status NotReceived.
        List<ClaimProcHist> histList = ClaimProcs.GetHistList(patNum, benefitList, patPlans, planList, DateTime.Today, subList);
        //Validate
        double pending = InsPlans.GetPendingDisplay(histList, DateTime.Today, plan, patPlanNum, -1, patNum, subNum, benefitList);
        //The a limitation for preventive should override the general limitation.
        //The 125 should apply to preventive, not general.
        //This display box that we are looking at is only supposed to represent general.
        if (pending != 0)
        {
            throw new Exception("Pending amount should be 0.\r\n");
        }
         
        return "31: Passed.  Limitations override more general limitations for pending insurance.\r\n";
    }

    /**
    * 
    */
    public static String testThirtyTwo(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 32)
        {
            return "";
        }
         
        String suffix = "32";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        Prefs.updateBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks,true);
        Prefs.refreshCache();
        TimeCardRuleT.CreatePMTimeRule(emp.EmployeeNum, TimeSpan.FromHours(16));
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddHours(8), startDate.AddHours(16).AddMinutes(40));
        ClockEventT.InsertBreak(emp.EmployeeNum, startDate.AddHours(11), 40);
        TimeCardRules.calculateDailyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        if (ClockEvents.getOne(clockEvent1).AdjustAuto != TimeSpan.FromMinutes(-10))
        {
            throw new Exception("Clock adjustment should be -10 minutes, instead it is " + ClockEvents.getOne(clockEvent1).AdjustAuto.TotalMinutes + " minutes.\r\n");
        }
         
        if (ClockEvents.getOne(clockEvent1).Rate2Auto != TimeSpan.FromMinutes(40))
        {
            throw new Exception("Clock differential should be 40 minutes, instead it is " + ClockEvents.getOne(clockEvent1).Rate2Auto.TotalMinutes + " minutes.\r\n");
        }
         
        retVal += "32: Passed. Differential calculated properly after time of day.\r\n";
        return retVal;
    }

    /**
    * 
    */
    public static String testThirtyThree(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 33)
        {
            return "";
        }
         
        String suffix = "33";
        DateTime startDate = DateTime.Parse("2001-01-01");
        Employee emp = EmployeeT.createEmployee(suffix);
        PayPeriod payP1 = PayPeriodT.createTwoWeekPayPeriodIfNotExists(startDate);
        PayPeriods.refreshCache();
        Prefs.updateInt(PrefName.TimeCardOvertimeFirstDayOfWeek,0);
        Prefs.updateBool(PrefName.TimeCardsMakesAdjustmentsForOverBreaks,true);
        TimeCardRuleT.CreateAMTimeRule(emp.EmployeeNum, TimeSpan.FromHours(7.5));
        TimeCardRules.refreshCache();
        long clockEvent1 = ClockEventT.InsertWorkPeriod(emp.EmployeeNum, startDate.AddHours(6), startDate.AddHours(16));
        ClockEventT.InsertBreak(emp.EmployeeNum, startDate.AddHours(11), 40);
        TimeCardRules.calculateDailyOvertime(emp,payP1.DateStart,payP1.DateStop);
        //Validate
        String retVal = "";
        //Check
        if (ClockEvents.getOne(clockEvent1).AdjustAuto != TimeSpan.FromMinutes(-10))
        {
            throw new Exception("Clock adjustment should be -10 minutes, instead it is " + ClockEvents.getOne(clockEvent1).AdjustAuto.TotalMinutes + " minutes.\r\n");
        }
         
        if (ClockEvents.getOne(clockEvent1).Rate2Auto != TimeSpan.FromMinutes(90))
        {
            throw new Exception("Clock differential should be 90 minutes, instead it is " + ClockEvents.getOne(clockEvent1).Rate2Auto.TotalMinutes + " minutes.\r\n");
        }
         
        retVal += "33: Passed. Differential calculated properly before time of day.\r\n";
        return retVal;
    }

    /**
    * Validates that procedure specific deductibles take general deductibles into consideration.
    */
    public static String testThirtyFour(int specificTest) throws Exception {
        if (specificTest != 0 && specificTest != 34)
        {
            return "";
        }
         
        String suffix = "34";
        Patient pat = PatientT.createPatient(suffix);
        Carrier carrier = CarrierT.createCarrier(suffix);
        InsPlan plan = InsPlanT.createInsPlan(carrier.CarrierNum);
        InsSub sub = InsSubT.createInsSub(pat.PatNum,plan.PlanNum);
        long subNum = sub.InsSubNum;
        BenefitT.CreateAnnualMax(plan.PlanNum, 1000);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.Diagnostic,100);
        BenefitT.createCategoryPercent(plan.PlanNum,EbenefitCategory.RoutinePreventive,100);
        BenefitT.CreateDeductible(plan.PlanNum, EbenefitCategory.RoutinePreventive, 0);
        BenefitT.CreateDeductibleGeneral(plan.PlanNum, BenefitCoverageLevel.Individual, 50);
        BenefitT.CreateDeductible(plan.PlanNum, "D1351", 50);
        PatPlanT.CreatePatPlan(1, pat.PatNum, subNum);
        //proc1 - PerExam
        Procedure proc1 = ProcedureT.CreateProcedure(pat, "D0120", OpenDentBusiness.ProcStat.C, "", 45);
        //proc2 - Sealant
        Procedure proc2 = ProcedureT.CreateProcedure(pat, "D1351", OpenDentBusiness.ProcStat.TP, "5", 54);
        //Lists:
        List<ClaimProc> claimProcs = ClaimProcs.refresh(pat.PatNum);
        List<ClaimProc> claimProcListOld = new List<ClaimProc>();
        Family fam = Patients.getFamily(pat.PatNum);
        List<InsSub> subList = InsSubs.refreshForFam(fam);
        List<InsPlan> planList = InsPlans.RefreshForSubList(subList);
        List<PatPlan> patPlans = PatPlans.refresh(pat.PatNum);
        List<Benefit> benefitList = Benefits.Refresh(patPlans, subList);
        List<ClaimProcHist> histList = new List<ClaimProcHist>();
        List<ClaimProcHist> loopList = new List<ClaimProcHist>();
        List<Procedure> ProcList = Procedures.refresh(pat.PatNum);
        Procedure[] ProcListTP = Procedures.GetListTP(ProcList);
        //sorted by priority, then toothnum
        //Attach to claim
        ClaimProcT.AddInsPaid(pat.PatNum, plan.PlanNum, proc1.ProcNum, 0, subNum, 45, 0);
        //Validate
        String retVal = "";
        histList = ClaimProcs.GetHistList(pat.PatNum, benefitList, patPlans, planList, DateTime.Today, subList);
        RefSupport<List<ClaimProc>> refVar___39 = new RefSupport<List<ClaimProc>>(claimProcs);
        Procedures.ComputeEstimates(proc2, pat.PatNum, refVar___39, false, planList, patPlans, benefitList, histList, loopList, false, pat.getAge(), subList);
        claimProcs = refVar___39.getValue();
        //save changes in the list to the database
        RefSupport<List<ClaimProc>> refVar___40 = new RefSupport<List<ClaimProc>>(claimProcs);
        ClaimProcs.Synch(refVar___40, claimProcListOld);
        claimProcs = refVar___40.getValue();
        claimProcs = ClaimProcs.refresh(pat.PatNum);
        ClaimProc claimProc = ClaimProcs.GetEstimate(claimProcs, proc2.ProcNum, plan.PlanNum, subNum);
        if (claimProc.DedEst != 5)
        {
            throw new Exception("Should be 5. \r\n");
        }
         
        //Second procedure should show deductible of 5.
        retVal += "34: Passed.  General deductibles are taken into consideration when computing procedure specific deductibles.\r\n";
        return retVal;
    }

}


