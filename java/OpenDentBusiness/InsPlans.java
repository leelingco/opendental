//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitLogic;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.Carrier;
import OpenDentBusiness.Carriers;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EnumCobRule;
import OpenDentBusiness.Family;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class InsPlans   
{
    /**
    * Also fills PlanNum from db.
    */
    public static long insert(InsPlan plan) throws Exception {
        return insert(plan,false);
    }

    /**
    * Also fills PlanNum from db.
    */
    public static long insert(InsPlan plan, boolean useExistingPK) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            plan.PlanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), plan, useExistingPK);
            return plan.PlanNum;
        }
         
        return Crud.InsPlanCrud.Insert(plan, useExistingPK);
    }

    /**
    * 
    */
    public static void update(InsPlan plan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), plan);
            return ;
        }
         
        Crud.InsPlanCrud.Update(plan);
    }

    /**
    * It's fastest if you supply a plan list that contains the plan, but it also works just fine if it can't initally locate the plan in the list.  You can supply an list of length 0 or null.  If still not found, returns null.
    */
    public static InsPlan getPlan(long planNum, List<InsPlan> planList) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsPlan retPlan = new InsPlan();
        if (planNum == 0)
        {
            return null;
        }
         
        if (planList == null)
        {
            planList = new List<InsPlan>();
        }
         
        boolean found = false;
        for (int i = 0;i < planList.Count;i++)
        {
            if (planList[i].PlanNum == planNum)
            {
                found = true;
                retPlan = planList[i];
            }
             
        }
        if (!found)
        {
            retPlan = refreshOne(planNum);
        }
         
        //retPlan will now be null if not found
        if (retPlan == null)
        {
            return new InsPlan();
        }
         
        //MessageBox.Show(Lans.g("InsPlans","Database is inconsistent.  Please run the database maintenance tool."));
        if (retPlan == null)
        {
            return null;
        }
         
        return retPlan;
    }

    /*
    		///<summary>Will return null if no active plan for that ordinal.  Ordinal means primary, secondary, etc.</summary>
    		public static InsPlan GetPlanByOrdinal(int patNum,int ordinal) {
    			string command="SELECT * FROM insplan WHERE EXISTS "
    				+"(SELECT * FROM patplan WHERE insplan.PlanNum=patplan.PlanNum "
    				+"AND patplan.PatNum="+POut.PInt(patNum)
    				+" AND patplan.Ordinal="+POut.PInt(ordinal);
    			//num = '"+planNum+"'";
    		}*/
    public static InsPlan[] getByTrojanID(String trojanID) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InsPlan[]>GetObject(MethodBase.GetCurrentMethod(), trojanID);
        }
         
        String command = "SELECT * FROM insplan WHERE TrojanID = '" + POut.string(trojanID) + "'";
        return Crud.InsPlanCrud.SelectMany(command).ToArray();
    }

    /**
    * Only loads one plan from db. Can return null.
    */
    public static InsPlan refreshOne(long planNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InsPlan>GetObject(MethodBase.GetCurrentMethod(), planNum);
        }
         
        if (planNum == 0)
        {
            return null;
        }
         
        String command = "SELECT * FROM insplan WHERE plannum = '" + planNum + "'";
        return Crud.InsPlanCrud.SelectOne(command);
    }

    //<summary>Deprecated.  Instead, use RefreshForSubList.</summary>
    //public static List<InsPlan> RefreshForFam(){//Family Fam) {
    //	return null;
    //}
    /**
    * Gets List of plans based on the subList.  The list won't be in the same order.
    */
    public static List<InsPlan> refreshForSubList(List<InsSub> subList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<InsPlan>>GetObject(MethodBase.GetCurrentMethod(), subList);
        }
         
        if (subList.Count == 0)
        {
            return new List<InsPlan>();
        }
         
        String command = "SELECT * FROM insplan WHERE";
        for (int i = 0;i < subList.Count;i++)
        {
            if (i > 0)
            {
                command += " OR";
            }
             
            command += " PlanNum=" + POut.Long(subList[i].PlanNum);
        }
        return Crud.InsPlanCrud.SelectMany(command);
    }

    /**
    * Tests all fields for equality.
    */
    public static boolean areEqualValue(InsPlan plan1, InsPlan plan2) throws Exception {
        if (plan1.PlanNum == plan2.PlanNum && StringSupport.equals(plan1.GroupName, plan2.GroupName) && StringSupport.equals(plan1.GroupNum, plan2.GroupNum) && StringSupport.equals(plan1.PlanNote, plan2.PlanNote) && plan1.FeeSched == plan2.FeeSched && StringSupport.equals(plan1.PlanType, plan2.PlanType) && plan1.ClaimFormNum == plan2.ClaimFormNum && plan1.UseAltCode == plan2.UseAltCode && plan1.ClaimsUseUCR == plan2.ClaimsUseUCR && plan1.CopayFeeSched == plan2.CopayFeeSched && plan1.EmployerNum == plan2.EmployerNum && plan1.CarrierNum == plan2.CarrierNum && plan1.AllowedFeeSched == plan2.AllowedFeeSched && StringSupport.equals(plan1.TrojanID, plan2.TrojanID) && StringSupport.equals(plan1.DivisionNo, plan2.DivisionNo) && plan1.IsMedical == plan2.IsMedical && plan1.FilingCode == plan2.FilingCode && plan1.DentaideCardSequence == plan2.DentaideCardSequence && plan1.ShowBaseUnits == plan2.ShowBaseUnits && plan1.CodeSubstNone == plan2.CodeSubstNone && plan1.IsHidden == plan2.IsHidden && plan1.MonthRenew == plan2.MonthRenew && plan1.FilingCodeSubtype == plan2.FilingCodeSubtype && StringSupport.equals(plan1.CanadianPlanFlag, plan2.CanadianPlanFlag) && plan1.CobRule == plan2.CobRule)
        {
            return true;
        }
         
        return false;
    }

    /*
    		///<summary>Called from FormInsPlan when applying changes to all identical insurance plans. This updates the synchronized fields for all plans like the specified insPlan.  Current InsPlan must be set to the new values that we want.  BenefitNotes and SubscNote are specific to subscriber and are not changed.  PlanNotes are handled separately in a different function after this one is complete.</summary>
    		public static void UpdateForLike(InsPlan like, InsPlan plan) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),like,plan);
    				return;
    			}
    			string command= "UPDATE insplan SET "
    				+"EmployerNum = '"     +POut.Long   (plan.EmployerNum)+"'"
    				+",GroupName = '"      +POut.String(plan.GroupName)+"'"
    				+",GroupNum = '"       +POut.String(plan.GroupNum)+"'"
    				+",DivisionNo = '"     +POut.String(plan.DivisionNo)+"'"
    				+",CarrierNum = '"     +POut.Long   (plan.CarrierNum)+"'"
    				+",PlanType = '"       +POut.String(plan.PlanType)+"'"
    				+",UseAltCode = '"     +POut.Bool  (plan.UseAltCode)+"'"
    				+",IsMedical = '"      +POut.Bool  (plan.IsMedical)+"'"
    				+",ClaimsUseUCR = '"   +POut.Bool  (plan.ClaimsUseUCR)+"'"
    				+",FeeSched = '"       +POut.Long   (plan.FeeSched)+"'"
    				+",CopayFeeSched = '"  +POut.Long   (plan.CopayFeeSched)+"'"
    				+",ClaimFormNum = '"   +POut.Long   (plan.ClaimFormNum)+"'"
    				+",AllowedFeeSched= '" +POut.Long   (plan.AllowedFeeSched)+"'"
    				+",TrojanID = '"       +POut.String(plan.TrojanID)+"'"
    				+",FilingCode = '"     +POut.Long   (plan.FilingCode)+"'"
    				+",FilingCodeSubtype = '"+POut.Long(plan.FilingCodeSubtype)+"'"
    				+",ShowBaseUnits = '"  +POut.Bool  (plan.ShowBaseUnits)+"'"
    				//+",DedBeforePerc = '"  +POut.PBool  (plan.DedBeforePerc)+"'"
    				+",CodeSubstNone='"    +POut.Bool  (plan.CodeSubstNone)+"'"
    				+",IsHidden='"         +POut.Bool  (plan.IsHidden)+"'"
    				+",MonthRenew='"       +POut.Int   (plan.MonthRenew)+"'"
    				//It is most likely that MonthRenew would be the same for everyone on the same plan.  If we get complaints, we might have to add an option.
    				+" WHERE "
    				+"EmployerNum = '"        +POut.Long   (like.EmployerNum)+"' "
    				+"AND GroupName = '"      +POut.String(like.GroupName)+"' "
    				+"AND GroupNum = '"       +POut.String(like.GroupNum)+"' "
    				+"AND DivisionNo = '"     +POut.String(like.DivisionNo)+"'"
    				+"AND CarrierNum = '"     +POut.Long   (like.CarrierNum)+"' "
    				+"AND IsMedical = '"      +POut.Bool  (like.IsMedical)+"'";
    			Db.NonQ(command);
    		}*/
    /**
    * Gets a description of the specified plan, including carrier name and subscriber. It's fastest if you supply a plan list that contains the plan, but it also works just fine if it can't initally locate the plan in the list.  You can supply an array of length 0 for both family and planlist.
    */
    public static String getDescript(long planNum, Family family, List<InsPlan> planList, long insSubNum, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (planNum == 0)
        {
            return "";
        }
         
        InsPlan plan = GetPlan(planNum, planList);
        if (plan == null || plan.PlanNum == 0)
        {
            return "";
        }
         
        InsSub sub = InsSubs.GetSub(insSubNum, subList);
        if (sub == null || sub.InsSubNum == 0)
        {
            return "";
        }
         
        String subscriber = family.getNameInFamFL(sub.Subscriber);
        if (StringSupport.equals(subscriber, ""))
        {
            //subscriber from another family
            subscriber = Patients.getLim(sub.Subscriber).getNameLF();
        }
         
        String retStr = "";
        //loop just to get the index of the plan in the family list
        boolean otherFam = true;
        for (int i = 0;i < planList.Count;i++)
        {
            if (planList[i].PlanNum == planNum)
            {
                otherFam = false;
            }
             
        }
        //retStr += (i+1).ToString()+": ";
        if (otherFam)
        {
            //retStr=="")
            retStr = "(other fam):";
        }
         
        Carrier carrier = Carriers.getCarrier(plan.CarrierNum);
        String carrierName = carrier.CarrierName;
        if (carrierName.Length > 20)
        {
            carrierName = carrierName.Substring(0, 20) + "...";
        }
         
        retStr += carrierName;
        retStr += " (" + subscriber + ")";
        return retStr;
    }

    /**
    * Used in Ins lines in Account module and in Family module.
    */
    public static String getCarrierName(long planNum, List<InsPlan> planList) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsPlan plan = GetPlan(planNum, planList);
        if (plan == null)
        {
            return "";
        }
         
        Carrier carrier = Carriers.getCarrier(plan.CarrierNum);
        if (carrier.CarrierNum == 0)
        {
            return "";
        }
         
        return carrier.CarrierName;
    }

    //if corrupted
    /**
    * Only used once in Claims.cs.  Gets insurance benefits remaining for one benefit year.  Returns actual remaining insurance based on ClaimProc data, taking into account inspaid and ins pending. Must supply all claimprocs for the patient.  Date used to determine which benefit year to calc.  Usually today's date.  The insplan.PlanNum is the plan to get value for.  ExcludeClaim is the ClaimNum to exclude, or enter -1 to include all.  This does not yet handle calculations where ortho max is different from regular max.  Just takes the most general annual max, and subtracts all benefits used from all categories.
    */
    public static double getInsRem(List<ClaimProcHist> histList, DateTime asofDate, long planNum, long patPlanNum, long excludeClaim, List<InsPlan> planList, List<Benefit> benList, long patNum, long insSubNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double insUsed = GetInsUsedDisplay(histList, asofDate, planNum, patPlanNum, excludeClaim, planList, benList, patNum, insSubNum);
        InsPlan plan = InsPlans.GetPlan(planNum, planList);
        double insPending = GetPendingDisplay(histList, asofDate, plan, patPlanNum, excludeClaim, patNum, insSubNum, benList);
        double annualMaxFam = Benefits.GetAnnualMaxDisplay(benList, planNum, patPlanNum, true);
        double annualMaxInd = Benefits.GetAnnualMaxDisplay(benList, planNum, patPlanNum, false);
        double annualMax = annualMaxInd;
        if (annualMaxFam > annualMaxInd)
        {
            annualMax = annualMaxFam;
        }
         
        if (annualMax < 0)
        {
            return 999999;
        }
         
        if (annualMax - insUsed - insPending < 0)
        {
            return 0;
        }
         
        return annualMax - insUsed - insPending;
    }

    /**
    * Only for display purposes rather than for calculations.  Get pending insurance for a given plan for one benefit year. Include a history list for the patient/family.  asofDate used to determine which benefit year to calc.  Usually the date of service for a claim.  The planNum is the plan to get value for.
    */
    public static double getPendingDisplay(List<ClaimProcHist> histList, DateTime asofDate, InsPlan curPlan, long patPlanNum, long excludeClaim, long patNum, long insSubNum, List<Benefit> benefitList) throws Exception {
        //No need to check RemotingRole; no call to db.
        //InsPlan curPlan=GetPlan(planNum,PlanList);
        if (curPlan == null)
        {
            return 0;
        }
         
        //get the most recent renew date, possibly including today:
        DateTime renewDate = BenefitLogic.ComputeRenewDate(asofDate, curPlan.MonthRenew);
        DateTime stopDate = renewDate.AddYears(1);
        double retVal = 0;
        for (int i = 0;i < histList.Count;i++)
        {
            //CovCat generalCat=CovCats.GetForEbenCat(EbenefitCategory.General);
            //CovSpan[] covSpanArray=null;
            //if(generalCat!=null) {
            //  covSpanArray=CovSpans.GetForCat(generalCat.CovCatNum);
            //}
            //if(generalCat!=null) {//If there is a general category, then we only consider codes within it.  This is how we exclude ortho.
            //  if(!CovSpans.IsCodeInSpans(histList[i].StrProcCode,covSpanArray)) {//for example, ortho
            //    continue;
            //  }
            //}
            if (Benefits.LimitationExistsNotGeneral(benefitList, curPlan.PlanNum, patPlanNum, histList[i].StrProcCode))
            {
                continue;
            }
             
            //enum ClaimProcStatus{NotReceived,Received,Preauth,Adjustment,Supplemental}
            if (histList[i].PlanNum == curPlan.PlanNum && histList[i].InsSubNum == insSubNum && histList[i].ClaimNum != excludeClaim && histList[i].ProcDate < stopDate && histList[i].ProcDate >= renewDate && histList[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived && histList[i].PatNum == patNum)
            {
                //Status Adjustment has no insPayEst, so can ignore it here.
                retVal += histList[i].Amount;
            }
             
        }
        return retVal;
    }

    /**
    * Only for display purposes rather than for calculations.  Get insurance benefits used for one benefit year.  Must supply all relevant hist for the patient.  asofDate is used to determine which benefit year to calc.  Usually date of service for a claim.  The insplan.PlanNum is the plan to get value for.  ExcludeClaim is the ClaimNum to exclude, or enter -1 to include all.  The behavior of this changed in 7.1.  It now only includes values that apply towards annual max.  So if there is a limitation override for a category like ortho or preventive, then completed procedures in those categories will be excluded.  The benefitList passed in might very well have benefits from other insurance plans included.
    */
    public static double getInsUsedDisplay(List<ClaimProcHist> histList, DateTime asofDate, long planNum, long patPlanNum, long excludeClaim, List<InsPlan> planList, List<Benefit> benefitList, long patNum, long insSubNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsPlan curPlan = GetPlan(planNum, planList);
        if (curPlan == null)
        {
            return 0;
        }
         
        //get the most recent renew date, possibly including today:
        DateTime renewDate = BenefitLogic.ComputeRenewDate(asofDate, curPlan.MonthRenew);
        DateTime stopDate = renewDate.AddYears(1);
        double retVal = 0;
        for (int i = 0;i < histList.Count;i++)
        {
            //CovCat generalCat=CovCats.GetForEbenCat(EbenefitCategory.General);
            //CovSpan[] covSpanArray=null;
            //if(generalCat!=null) {
            //  covSpanArray=CovSpans.GetForCat(generalCat.CovCatNum);
            //}
            if (histList[i].PlanNum != planNum || histList[i].InsSubNum != insSubNum || histList[i].ClaimNum == excludeClaim || histList[i].ProcDate.Date >= stopDate || histList[i].ProcDate.Date < renewDate || histList[i].PatNum != patNum)
            {
                continue;
            }
             
            if (Benefits.LimitationExistsNotGeneral(benefitList, planNum, patPlanNum, histList[i].StrProcCode))
            {
                continue;
            }
             
            //if(generalCat!=null){//If there is a general category, then we only consider codes within it.  This is how we exclude ortho.
            //	if(histList[i].StrProcCode!="" && !CovSpans.IsCodeInSpans(histList[i].StrProcCode,covSpanArray)){//for example, ortho
            //		continue;
            //	}
            //}
            //enum ClaimProcStatus{NotReceived,Received,Preauth,Adjustment,Supplemental}
            if (histList[i].Status == OpenDentBusiness.ClaimProcStatus.Received || histList[i].Status == OpenDentBusiness.ClaimProcStatus.Adjustment || histList[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                retVal += histList[i].Amount;
            }
             
        }
        return retVal;
    }

    /**
    * Only for display purposes rather than for calculations.  Get insurance deductible used for one benefit year.  Must supply a history list for the patient/family.  asofDate is used to determine which benefit year to calc.  Usually date of service for a claim.  The planNum is the plan to get value for.  ExcludeClaim is the ClaimNum to exclude, or enter -1 to include all.  It includes pending deductibles in the result.
    */
    public static double getDedUsedDisplay(List<ClaimProcHist> histList, DateTime asofDate, long planNum, long patPlanNum, long excludeClaim, List<InsPlan> planList, BenefitCoverageLevel coverageLevel, long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsPlan curPlan = GetPlan(planNum, planList);
        if (curPlan == null)
        {
            return 0;
        }
         
        //get the most recent renew date, possibly including today. Date based on annual max.
        DateTime renewDate = BenefitLogic.ComputeRenewDate(asofDate, curPlan.MonthRenew);
        DateTime stopDate = renewDate.AddYears(1);
        double retVal = 0;
        for (int i = 0;i < histList.Count;i++)
        {
            if (histList[i].PlanNum != planNum || histList[i].ClaimNum == excludeClaim || histList[i].ProcDate >= stopDate || histList[i].ProcDate < renewDate)
            {
                continue;
            }
             
            //no need to check status, because only the following statuses will be part of histlist:
            //Adjustment,NotReceived,Received,Supplemental
            if (coverageLevel != BenefitCoverageLevel.Family && histList[i].PatNum != patNum)
            {
                continue;
            }
             
            //to exclude histList items from other family members
            retVal += histList[i].Deduct;
        }
        return retVal;
    }

    /**
    * Only for display purposes rather than for calculations.  Get insurance deductible used for one benefit year.  Must supply a history list for the patient/family.  asofDate is used to determine which benefit year to calc.  Usually date of service for a claim.  The planNum is the plan to get value for.  ExcludeClaim is the ClaimNum to exclude, or enter -1 to include all.  It includes pending deductibles in the result. The ded and dedFam variables are the individual and family deductibles respectively. This function assumes that the individual deductible 'ded' is always available, but that the family deductible 'dedFam' is optional (set to -1 if not available).
    */
    public static double getDedRemainDisplay(List<ClaimProcHist> histList, DateTime asofDate, long planNum, long patPlanNum, long excludeClaim, List<InsPlan> planList, long patNum, double ded, double dedFam) throws Exception {
        //No need to check RemotingRole; no call to db.
        InsPlan curPlan = GetPlan(planNum, planList);
        if (curPlan == null)
        {
            return 0;
        }
         
        //get the most recent renew date, possibly including today. Date based on annual max.
        DateTime renewDate = BenefitLogic.ComputeRenewDate(asofDate, curPlan.MonthRenew);
        DateTime stopDate = renewDate.AddYears(1);
        double dedRemInd = ded;
        double dedRemFam = dedFam;
        for (int i = 0;i < histList.Count;i++)
        {
            if (histList[i].PlanNum != planNum || histList[i].ClaimNum == excludeClaim || histList[i].ProcDate >= stopDate || histList[i].ProcDate < renewDate)
            {
                continue;
            }
             
            //no need to check status, because only the following statuses will be part of histlist:
            //Adjustment,NotReceived,Received,Supplemental
            dedRemFam -= histList[i].Deduct;
            if (histList[i].PatNum == patNum)
            {
                dedRemInd -= histList[i].Deduct;
            }
             
        }
        if (dedFam >= 0)
        {
            return Math.Max(0, Math.Min(dedRemInd, dedRemFam));
        }
         
        return Math.Max(0, dedRemInd);
    }

    //never negative
    //never negative
    /*
    		///<summary>Used once from Claims and also in ContrTreat.  Gets insurance deductible remaining for one benefit year which includes the given date.  Must supply all claimprocs for the patient.  Must supply all benefits for patient so that we know if it's a service year or a calendar year.  Date used to determine which benefit year to calc.  Usually today's date.  The insplan.PlanNum is the plan to get value for.  ExcludeClaim is the ClaimNum to exclude, or enter -1 to include all.  The supplied procCode is needed because some deductibles, for instance, do not apply to preventive.</summary>
    		public static double GetDedRem(List<ClaimProc> claimProcList,DateTime date,int planNum,int patPlanNum,int excludeClaim,List<InsPlan> PlanList,List<Benefit> benList,string procCode) {
    			//No need to check RemotingRole; no call to db.
    			double dedTot=Benefits.GetDeductibleByCode(benList,planNum,patPlanNum,procCode);
    			double dedUsed=GetDedUsed(claimProcList,date,planNum,patPlanNum,excludeClaim,PlanList,benList);
    			if(dedTot-dedUsed<0){
    				return 0;
    			}
    			return dedTot-dedUsed;
    		}*/
    /*
    		///<Summary>Only used in TP to calculate discount for PPO procedure.  Will return -1 if no fee found.</Summary>
    		public static double GetPPOAllowed(int codeNum,InsPlan plan){
    			//plan has already been tested to not be null and to be a PPO plan.
    			double fee=Fees.GetAmount(codeNum,plan.FeeSched);//could be -1
    		}*/
    /**
    * This is used in FormQuery.SubmitQuery to allow display of carrier names.
    */
    public static Hashtable getHListAll() throws Exception {
        //No need to check RemotingRole; no call to db.
        DataTable table = getCarrierTable();
        Hashtable HListAll = new Hashtable(table.Rows.Count);
        long plannum = new long();
        String carrierName = new String();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            plannum = PIn.Long(table.Rows[i][0].ToString());
            carrierName = PIn.String(table.Rows[i][1].ToString());
            HListAll.Add(plannum, carrierName);
        }
        return HListAll;
    }

    public static DataTable getCarrierTable() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT insplan.PlanNum,carrier.CarrierName " + "FROM insplan,carrier " + "WHERE insplan.CarrierNum=carrier.CarrierNum";
        return Db.getTable(command);
    }

    /*
    		///<summary>Used by Trojan.  Gets all distinct notes for the planNums supplied.  Includes blank notes.</summary>
    		public static string[] GetNotesForPlans(List<long> planNums) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<string[]>(MethodBase.GetCurrentMethod(),planNums,excludePlanNum);
    			}
    			if(planNums.Count==0) {//this should never happen, but just in case...
    				return new string[0];
    			}
    			if(planNums.Count==1 && planNums[0]==excludePlanNum){
    				return new string[0];
    			}
    			string s="";
    			for(int i=0;i<planNums.Count;i++) {
    				if(planNums[i]==excludePlanNum){
    					continue;
    				}
    				if(s!="") {
    					s+=" OR";
    				}
    				s+=" PlanNum="+POut.Long(planNums[i]);
    			}
    			string command="SELECT DISTINCT PlanNote FROM insplan WHERE"+s;
    			DataTable table=Db.GetTable(command);
    			string[] retVal=new string[table.Rows.Count];
    			for(int i=0;i<table.Rows.Count;i++) {
    				retVal[i]=PIn.String(table.Rows[i][0].ToString());
    			}
    			return retVal;
    		}
    		///<summary>Used by Trojan.  Sets the PlanNote for multiple plans at once.</summary>
    		public static void UpdateNoteForPlans(List<long> planNums,string newNote) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),planNums,newNote);
    				return;
    			}
    			if(planNums.Count==0){
    				return;
    			}
    			string s="";
    			for(int i=0;i<planNums.Count;i++){
    				if(i>0){
    					s+=" OR";
    				}
    				s+=" PlanNum="+POut.Long(planNums[i]);
    			}
    			string command="UPDATE insplan SET PlanNote='"+POut.String(newNote)+"' "
    				+"WHERE"+s;
    			Db.NonQ(command);
    		}*/
    /*
    		///<summary>Called from FormInsPlan when user wants to view a benefit note for similar plans.  Should never include the current plan that the user is editing.  This function will get one note from the database, not including blank notes.  If no note can be found, then it returns empty string.</summary>
    		public static string GetBenefitNotes(List<long> planNums) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetString(MethodBase.GetCurrentMethod(),planNums);
    			}
    			if(planNums.Count==0){
    				return "";
    			}
    			string s="";
    			for(int i=0;i<planNums.Count;i++) {
    				if(i>0) {
    					s+=" OR";
    				}
    				s+=" PlanNum="+POut.Long(planNums[i]);
    			}
    			string command="SELECT BenefitNotes FROM insplan WHERE BenefitNotes != '' AND ("+s+") "+DbHelper.LimitAnd(1);
    			DataTable table=Db.GetTable(command);
    			//string[] retVal=new string[];
    			if(table.Rows.Count==0){
    				return "";
    			}
    			return PIn.String(table.Rows[0][0].ToString());
    		}*/
    /*
    		///<summary>Gets a list of PlanNums from the database of plans that have identical info as this one. Used to perform updates to benefits, etc.  Note that you have the option to include the current plan in the list.</summary>
    		public static List<long> GetPlanNumsOfSamePlans(string employerName,string groupName,string groupNum,
    				string divisionNo,string carrierName,bool isMedical,long planNum,bool includePlanNum) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<long>>(MethodBase.GetCurrentMethod(),employerName,groupName,groupNum,divisionNo,carrierName,isMedical,planNum,includePlanNum);
    			}
    			string command="SELECT PlanNum FROM insplan "
    				+"LEFT JOIN carrier ON carrier.CarrierNum = insplan.CarrierNum "
    				+"LEFT JOIN employer ON employer.EmployerNum = insplan.EmployerNum ";
    			if(employerName==""){
    				command+="WHERE employer.EmpName IS NULL ";
    			}
    			else{
    				command+="WHERE employer.EmpName = '"+POut.String(employerName)+"' ";
    			}
    			command+="AND insplan.GroupName = '"  +POut.String(groupName)+"' "
    				+"AND insplan.GroupNum = '"   +POut.String(groupNum)+"' "
    				+"AND insplan.DivisionNo = '" +POut.String(divisionNo)+"' "
    				+"AND carrier.CarrierName = '"+POut.String(carrierName)+"' "
    				+"AND insplan.IsMedical = '"  +POut.Bool  (isMedical)+"'"
    				+"AND insplan.PlanNum != "+POut.Long(planNum);
    			DataTable table=Db.GetTable(command);
    			List<long> retVal=new List<long>();
    			//if(includePlanNum){
    			//	retVal=new int[table.Rows.Count+1];
    			//}
    			//else{
    			//	retVal=new int[table.Rows.Count];
    			//}
    			for(int i=0;i<table.Rows.Count;i++) {
    				retVal.Add(PIn.Long(table.Rows[i][0].ToString()));
    			}
    			if(includePlanNum){
    				retVal.Add(planNum);
    			}
    			return retVal;
    		}*/
    /**
    * Used from FormInsPlans to get a big list of many plans, organized by carrier name or by employer.
    */
    public static DataTable getBigList(boolean byEmployer, String empName, String carrierName, String groupName, String groupNum, String trojanID, boolean showHidden) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), byEmployer, empName, carrierName, groupName, groupNum, trojanID, showHidden);
        }
         
        DataTable table = new DataTable();
        DataRow row = new DataRow();
        table.Columns.Add("Address");
        table.Columns.Add("City");
        table.Columns.Add("CarrierName");
        table.Columns.Add("ElectID");
        table.Columns.Add("EmpName");
        table.Columns.Add("GroupName");
        table.Columns.Add("GroupNum");
        table.Columns.Add("noSendElect");
        table.Columns.Add("Phone");
        table.Columns.Add("PlanNum");
        table.Columns.Add("State");
        table.Columns.Add("subscribers");
        table.Columns.Add("trojanID");
        table.Columns.Add("Zip");
        List<DataRow> rows = new List<DataRow>();
        //for Oracle
        //+"(SELECT COUNT(*) FROM employer WHERE insplan.EmployerNum=employer.EmployerNum) haveName "//for Oracle. Could be higher than 1?
        //for Oracle
        String command = "SELECT carrier.Address,carrier.City,CarrierName,ElectID,EmpName,GroupName,GroupNum,NoSendElect," + "carrier.Phone,PlanNum," + "(SELECT COUNT(DISTINCT Subscriber) FROM inssub WHERE insplan.PlanNum=inssub.PlanNum) subscribers," + "carrier.State,TrojanID,carrier.Zip, " + "CASE WHEN (EmpName IS NULL) THEN 1 ELSE 0 END as haveName " + "FROM insplan " + "LEFT JOIN employer ON employer.EmployerNum = insplan.EmployerNum " + "LEFT JOIN carrier ON carrier.CarrierNum = insplan.CarrierNum " + "WHERE CarrierName LIKE '%" + POut.string(carrierName) + "%' ";
        if (!StringSupport.equals(empName, ""))
        {
            command += "AND EmpName LIKE '%" + POut.string(empName) + "%' ";
        }
         
        if (!StringSupport.equals(groupName, ""))
        {
            command += "AND GroupName LIKE '%" + POut.string(groupName) + "%' ";
        }
         
        if (!StringSupport.equals(groupNum, ""))
        {
            command += "AND GroupNum LIKE '%" + POut.string(groupNum) + "%' ";
        }
         
        if (!StringSupport.equals(trojanID, ""))
        {
            command += "AND TrojanID LIKE '%" + POut.string(trojanID) + "%' ";
        }
         
        if (!showHidden)
        {
            command += "AND insplan.IsHidden=0 ";
        }
         
        if (byEmployer)
        {
            command += "ORDER BY haveName,EmpName,CarrierName";
        }
        else
        {
            //not by employer
            command += "ORDER BY CarrierName";
        } 
        DataTable rawT = Db.getTable(command);
        for (int i = 0;i < rawT.Rows.Count;i++)
        {
            row = table.NewRow();
            row["Address"] = rawT.Rows[i]["Address"].ToString();
            row["City"] = rawT.Rows[i]["City"].ToString();
            row["CarrierName"] = rawT.Rows[i]["CarrierName"].ToString();
            row["ElectID"] = rawT.Rows[i]["ElectID"].ToString();
            row["EmpName"] = rawT.Rows[i]["EmpName"].ToString();
            row["GroupName"] = rawT.Rows[i]["GroupName"].ToString();
            row["GroupNum"] = rawT.Rows[i]["GroupNum"].ToString();
            if (StringSupport.equals(rawT.Rows[i]["NoSendElect"].ToString(), "0"))
            {
                row["noSendElect"] = "";
            }
            else
            {
                row["noSendElect"] = "X";
            } 
            row["Phone"] = rawT.Rows[i]["Phone"].ToString();
            row["PlanNum"] = rawT.Rows[i]["PlanNum"].ToString();
            row["State"] = rawT.Rows[i]["State"].ToString();
            row["subscribers"] = rawT.Rows[i]["subscribers"].ToString();
            row["TrojanID"] = rawT.Rows[i]["TrojanID"].ToString();
            row["Zip"] = rawT.Rows[i]["Zip"].ToString();
            rows.Add(row);
        }
        for (int i = 0;i < rows.Count;i++)
        {
            table.Rows.Add(rows[i]);
        }
        return table;
    }

    /**
    * Used in FormFeesForIns
    */
    public static DataTable getListFeeCheck(String carrierName, String carrierNameNot, long feeSchedWithout, long feeSchedWith, FeeScheduleType feeSchedType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), carrierName, carrierNameNot, feeSchedWithout, feeSchedWith, feeSchedType);
        }
         
        String pFeeSched = "FeeSched";
        if (feeSchedType == FeeScheduleType.Allowed)
        {
            pFeeSched = "AllowedFeeSched";
        }
         
        if (feeSchedType == FeeScheduleType.CoPay)
        {
            pFeeSched = "CopayFeeSched";
        }
         
        String command = "SELECT insplan.GroupName,insplan.GroupNum,employer.EmpName,carrier.CarrierName," + "insplan.EmployerNum,insplan.CarrierNum,feesched.Description AS FeeSchedName,insplan.PlanType," + "insplan." + pFeeSched + " feeSched " + "FROM insplan " + "LEFT JOIN employer ON employer.EmployerNum = insplan.EmployerNum " + "LEFT JOIN carrier ON carrier.CarrierNum = insplan.CarrierNum " + "LEFT JOIN feesched ON feesched.FeeSchedNum = insplan." + pFeeSched + " " + "WHERE carrier.CarrierName LIKE '%" + POut.string(carrierName) + "%' ";
        if (!StringSupport.equals(carrierNameNot, ""))
        {
            command += "AND carrier.CarrierName NOT LIKE '%" + POut.string(carrierNameNot) + "%' ";
        }
         
        if (feeSchedWithout != 0)
        {
            command += "AND insplan." + pFeeSched + " !=" + POut.long(feeSchedWithout) + " ";
        }
         
        if (feeSchedWith != 0)
        {
            command += "AND insplan." + pFeeSched + " =" + POut.long(feeSchedWith) + " ";
        }
         
        command += "ORDER BY carrier.CarrierName,employer.EmpName,insplan.GroupNum";
        return Db.getTable(command);
    }

    /**
    * Based on the four supplied parameters, it updates all similar plans.  Used in a specific tool: FormFeesForIns.
    */
    public static long setFeeSched(long employerNum, String carrierName, String groupNum, String groupName, long feeSchedNum, FeeScheduleType feeSchedType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), employerNum, carrierName, groupNum, groupName, feeSchedNum, feeSchedType);
        }
         
        //FIXME:UPDATE-MULTIPLE-TABLES
        /*string command="UPDATE insplan,carrier SET insplan.FeeSched="+POut.PInt(feeSchedNum)
        				+" WHERE carrier.CarrierNum = insplan.CarrierNum "//employer.EmployerNum = insplan.EmployerNum "
        				+"AND insplan.EmployerNum='"+POut.PInt(employerNum)+"' "
        				+"AND carrier.CarrierName='"+POut.PString(carrierName)+"' "
        				+"AND insplan.GroupNum='"+POut.PString(groupNum)+"' "
        				+"AND insplan.GroupName='"+POut.PString(groupName)+"'";
        			 return Db.NonQ(command);
        			 */
        //Code rewritten so that it is not only MySQL compatible, but Oracle compatible as well.
        //employer.EmployerNum = insplan.EmployerNum "
        String command = "SELECT insplan.PlanNum FROM insplan,carrier " + "WHERE carrier.CarrierNum = insplan.CarrierNum " + "AND insplan.EmployerNum='" + POut.long(employerNum) + "' " + "AND carrier.CarrierName='" + POut.string(carrierName) + "' " + "AND insplan.GroupNum='" + POut.string(groupNum) + "' " + "AND insplan.GroupName='" + POut.string(groupName) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return 0;
        }
         
        command = "UPDATE insplan SET ";
        if (feeSchedType == FeeScheduleType.Normal)
        {
            command += "insplan.FeeSched =" + POut.long(feeSchedNum) + " WHERE insplan.FeeSched !=" + POut.long(feeSchedNum);
        }
        else if (feeSchedType == FeeScheduleType.Allowed)
        {
            command += "insplan.AllowedFeeSched =" + POut.long(feeSchedNum) + " WHERE insplan.AllowedFeeSched !=" + POut.long(feeSchedNum);
        }
        else if (feeSchedType == FeeScheduleType.CoPay)
        {
            command += "insplan.CopayFeeSched =" + POut.long(feeSchedNum) + " WHERE insplan.CopayFeeSched !=" + POut.long(feeSchedNum);
        }
           
        command += " AND (";
        for (int i = 0;i < table.Rows.Count;i++)
        {
            command += "PlanNum=" + table.Rows[i][0].ToString();
            if (i < table.Rows.Count - 1)
            {
                command += " OR ";
            }
             
        }
        command += ")";
        return Db.nonQ(command);
    }

    /**
    * Returns number of rows affected.
    */
    public static long convertToNewClaimform(long oldClaimFormNum, long newClaimFormNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), oldClaimFormNum, newClaimFormNum);
        }
         
        String command = "UPDATE insplan SET ClaimFormNum=" + POut.long(newClaimFormNum) + " WHERE ClaimFormNum=" + POut.long(oldClaimFormNum);
        return Db.nonQ(command);
    }

    /**
    * Returns the number of fee schedules added.  It doesn't inform the user of how many plans were affected, but there will obviously be a certain number of plans for every new fee schedule.
    */
    public static long generateAllowedFeeSchedules() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        //get carrier names for all plans without an allowed fee schedule that are also not hidden.
        String command = "SELECT carrier.CarrierName " + "FROM insplan,carrier " + "WHERE carrier.CarrierNum=insplan.CarrierNum " + "AND insplan.AllowedFeeSched=0 " + "AND insplan.PlanType='' " + "AND insplan.IsHidden='0' " + "GROUP BY carrier.CarrierName";
        DataTable table = Db.getTable(command);
        //loop through all the carrier names
        String carrierName = new String();
        FeeSched sched;
        int itemOrder = FeeSchedC.getListLong().Count;
        DataTable tableCarrierNums = new DataTable();
        long retVal = 0;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            carrierName = PIn.String(table.Rows[i]["CarrierName"].ToString());
            if (StringSupport.equals(carrierName, "") || StringSupport.equals(carrierName, " "))
            {
                continue;
            }
             
            //add a fee schedule if needed
            sched = FeeScheds.getByExactName(carrierName,FeeScheduleType.Allowed);
            if (sched == null)
            {
                sched = new FeeSched();
                sched.Description = carrierName;
                sched.FeeSchedType = FeeScheduleType.Allowed;
                //sched.IsNew=true;
                sched.ItemOrder = itemOrder;
                FeeScheds.insert(sched);
                itemOrder++;
            }
             
            //assign the fee sched to many plans
            //for compatibility with Oracle, get a list of all carrierNums that use the carriername
            command = "SELECT CarrierNum FROM carrier WHERE CarrierName='" + POut.string(carrierName) + "'";
            tableCarrierNums = Db.getTable(command);
            if (tableCarrierNums.Rows.Count == 0)
            {
                continue;
            }
             
            //I don't see how this could happen
            command = "UPDATE insplan " + "SET AllowedFeeSched=" + POut.long(sched.FeeSchedNum) + " " + "WHERE AllowedFeeSched=0 " + "AND PlanType='' " + "AND IsHidden='0' " + "AND (";
            for (int c = 0;c < tableCarrierNums.Rows.Count;c++)
            {
                if (c > 0)
                {
                    command += " OR ";
                }
                 
                command += "CarrierNum=" + tableCarrierNums.Rows[c]["CarrierNum"].ToString();
            }
            command += ")";
            retVal += Db.nonQ(command);
        }
        return retVal;
    }

    public static int unusedGetCount() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT COUNT(*) FROM insplan WHERE IsHidden=0 " + "AND NOT EXISTS (SELECT * FROM inssub WHERE inssub.PlanNum=insplan.PlanNum)";
        int count = PIn.int(Db.getCount(command));
        return count;
    }

    public static void unusedHideAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "UPDATE insplan SET IsHidden=1 " + "WHERE IsHidden=0 " + "AND NOT EXISTS (SELECT * FROM inssub WHERE inssub.PlanNum=insplan.PlanNum)";
        Db.nonQ(command);
    }

    //public static int GenerateOneAllowedFeeSchedule(){
    //}
    /**
    * Returns -1 if no copay feeschedule.  Can return -1 if copay amount is blank.
    */
    public static double getCopay(String myCode, InsPlan plan) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (plan == null)
        {
            return -1;
        }
         
        if (plan.CopayFeeSched == 0)
        {
            return -1;
        }
         
        double retVal = Fees.getAmount(ProcedureCodes.getCodeNum(myCode),plan.CopayFeeSched);
        if (retVal == -1)
        {
            //blank co-pay
            if (PrefC.getBool(PrefName.CoPay_FeeSchedule_BlankLikeZero))
            {
                return -1;
            }
            else
            {
                return Fees.getAmount(ProcedureCodes.getCodeNum(myCode),plan.FeeSched);
            } 
        }
         
        return retVal;
    }

    //will act like zero.  No patient co-pay.
    //The amount from the regular fee schedule
    //In other words, the patient is responsible for procs that are not specified in a managed care fee schedule.
    /**
    * Returns -1 if no copay feeschedule.  Can return -1 if copay amount is blank.
    */
    public static double getCopay(long codeNum, long feeSched, long copayFeeSched, boolean codeSubstNone, String toothNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (copayFeeSched == 0)
        {
            return -1;
        }
         
        long substCodeNum = codeNum;
        //codeSubstNone, true if the insplan does not allow procedure code downgrade substitution.
        if (!codeSubstNone)
        {
            //Plan allows substitution codes.  Get the substitution code if one exists.
            substCodeNum = ProcedureCodes.getSubstituteCodeNum(ProcedureCodes.getStringProcCode(codeNum),toothNum);
        }
         
        //for posterior composites
        double retVal = Fees.getAmount(substCodeNum,copayFeeSched);
        if (retVal == -1)
        {
            //blank co-pay
            if (PrefC.getBool(PrefName.CoPay_FeeSchedule_BlankLikeZero))
            {
                return -1;
            }
            else
            {
                return Fees.getAmount(substCodeNum,feeSched);
            } 
        }
         
        return retVal;
    }

    //will act like zero.  No patient co-pay.
    //The amount from the regular fee schedule
    //In other words, the patient is responsible for procs that are not specified in a managed care fee schedule.
    /**
    * Returns -1 if no allowed feeschedule or fee unknown for this procCode. Otherwise, returns the allowed fee including 0. Can handle a planNum of 0.  Tooth num is used for posterior composites.  It can be left blank in some situations.  Provider must be supplied in case plan has no assigned fee schedule.  Then it will use the fee schedule for the provider.
    */
    public static double getAllowed(String procCodeStr, long feeSched, long allowedFeeSched, boolean codeSubstNone, String planType, String toothNum, long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //if(planNum==0) {
        //	return -1;
        //}
        //InsPlan plan=InsPlans.GetPlan(planNum,PlanList);
        //if(plan==null) {
        //	return -1;
        //}
        long codeNum = ProcedureCodes.getCodeNum(procCodeStr);
        long substCodeNum = codeNum;
        if (!codeSubstNone)
        {
            substCodeNum = ProcedureCodes.getSubstituteCodeNum(procCodeStr,toothNum);
        }
         
        //for posterior composites
        //PPO always returns the PPO fee for the code or substituted code.
        if (StringSupport.equals(planType, "p"))
        {
            return Fees.getAmount(substCodeNum,feeSched);
        }
         
        //or, if not PPO, and an allowed fee schedule exists, then we use that.
        if (allowedFeeSched != 0)
        {
            return Fees.getAmount(substCodeNum,allowedFeeSched);
        }
         
        //whether post composite or not
        //must be an ordinary fee schedule, so if no substitution code, then no allowed override
        if (codeNum == substCodeNum)
        {
            return -1;
        }
         
        //must be posterior composite with an ordinary fee schedule
        //Although it won't happen very often, it's possible that there is no fee schedule assigned to the plan.
        if (feeSched == 0)
        {
            if (provNum == 0)
            {
                return Fees.getAmount(substCodeNum,Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv)).FeeSched);
            }
             
            return Fees.getAmount(substCodeNum,Providers.getProv(provNum).FeeSched);
        }
         
        return Fees.getAmount(substCodeNum,feeSched);
    }

    //slight corruption
    public static List<InsPlan> getByInsSubs(List<long> insSubNums) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<InsPlan>>GetObject(MethodBase.GetCurrentMethod(), insSubNums);
        }
         
        String insSubString = "";
        for (int i = 0;i < insSubNums.Count;i++)
        {
            insSubString += insSubNums[i].ToString();
            if (i < insSubNums.Count - 1)
            {
                insSubString += ",";
            }
             
        }
        String command = "SELECT DISTINCT insplan.* FROM insplan,inssub " + "WHERE insplan.PlanNum=inssub.PlanNum " + "AND inssub.InsSubNum IN (" + insSubString + ")";
        return Crud.InsPlanCrud.SelectMany(command);
    }

    /**
    * Used when closing the edit plan window to find all patients using this plan and to update all claimProcs for each patient.  This keeps estimates correct.
    */
    public static void computeEstimatesForTrojanPlan(long planNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), planNum);
            return ;
        }
         
        //string command="SELECT PatNum FROM patplan WHERE PlanNum="+POut.Long(planNum);
        //The left join will get extra info about each plan, namely the PlanNum.  No need for a GROUP BY.  The PlanNum is used to filter.
        String command = "SELECT PatNum FROM patplan \r\n" + 
        "\t\t\t\t\tLEFT JOIN inssub ON patplan.InsSubNum=inssub.InsSubNum\r\n" + 
        "\t\t\t\t\tWHERE inssub.PlanNum=" + POut.long(planNum);
        DataTable table = Db.getTable(command);
        List<long> patnums = new List<long>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            patnums.Add(PIn.Long(table.Rows[i][0].ToString()));
        }
        ComputeEstimatesForPatNums(patnums);
    }

    /**
    * Used when closing the edit plan window to find all patients using this plan and to update all claimProcs for each patient.  This keeps estimates correct.
    */
    public static void computeEstimatesForSubscriber(long subscriber) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), subscriber);
            return ;
        }
         
        String command = "SELECT PatNum FROM patplan,inssub WHERE Subscriber=" + POut.long(subscriber) + " AND patplan.InsSubNum=inssub.InsSubNum";
        DataTable table = Db.getTable(command);
        List<long> patnums = new List<long>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            patnums.Add(PIn.Long(table.Rows[i][0].ToString()));
        }
        ComputeEstimatesForPatNums(patnums);
    }

    private static void computeEstimatesForPatNums(List<long> patnums) throws Exception {
        long patNum = 0;
        for (int i = 0;i < patnums.Count;i++)
        {
            patNum = patnums[i];
            Family fam = Patients.getFamily(patNum);
            Patient pat = fam.getPatient(patNum);
            List<ClaimProc> claimProcs = ClaimProcs.refresh(patNum);
            List<Procedure> procs = Procedures.refresh(patNum);
            List<InsSub> subs = InsSubs.refreshForFam(fam);
            List<InsPlan> plans = InsPlans.RefreshForSubList(subs);
            List<PatPlan> patPlans = PatPlans.refresh(patNum);
            List<Benefit> benefitList = Benefits.Refresh(patPlans, subs);
            Procedures.ComputeEstimatesForAll(patNum, claimProcs, procs, plans, patPlans, benefitList, pat.getAge(), subs);
            Patients.setHasIns(patNum);
        }
    }

    /**
    * Only used from FormInsPlan. Throws ApplicationException if any dependencies. This is quite complex, because it also must update all claimprocs for all patients affected by the deletion.  Also deletes patplans, benefits, claimprocs, and possibly inssubs.
    */
    public static void delete(InsPlan plan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), plan);
            return ;
        }
         
        //first, check claims
        String command = "SELECT PatNum FROM claim " + "WHERE plannum = '" + plan.PlanNum.ToString() + "' " + DbHelper.limitAnd(1);
        DataTable table = Db.getTable(command);
        if (table.Rows.Count != 0)
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Not allowed to delete a plan with existing claims."));
        }
         
        //then, check claimprocs
        //ignore estimates
        command = "SELECT PatNum FROM claimproc " + "WHERE PlanNum = " + POut.long(plan.PlanNum) + " AND Status != " + POut.int(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal()) + " " + DbHelper.limitAnd(1);
        table = Db.getTable(command);
        if (table.Rows.Count != 0)
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Not allowed to delete a plan attached to procedures."));
        }
         
        //throw error if more than one inssub
        command = "SELECT InsSubNum FROM inssub WHERE PlanNum = " + POut.long(plan.PlanNum);
        table = Db.getTable(command);
        if (table.Rows.Count > 1)
        {
            throw new ApplicationException(Lans.g("FormInsPlan","Not allowed to delete a plan with more than one subscriber."));
        }
        else if (table.Rows.Count == 1)
        {
            //if there's only one inssub, delete it.
            long insSubNum = PIn.Long(table.Rows[0]["InsSubNum"].ToString());
            InsSubs.delete(insSubNum);
        }
          
        //Checks dependencies first;  If none, deletes the inssub, claimprocs, patplans, and recomputes all estimates.
        command = "DELETE FROM benefit WHERE PlanNum=" + POut.long(plan.PlanNum);
        Db.nonQ(command);
        command = "DELETE FROM insplan " + "WHERE PlanNum = '" + plan.PlanNum.ToString() + "'";
        Db.nonQ(command);
    }

    /**
    * Used from FormInsPlan and InsPlans.Merge. Does not check any dependencies.  Used when a new plan is created and then is no longer needed.  Also used if all dependencies have already been fixed.  Does not affect any other objects.
    */
    public static void delete(long planNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), planNum);
            return ;
        }
         
        Crud.InsPlanCrud.Delete(planNum);
    }

    /**
    * This changes PlanNum in every place in database where it's used.  It also deletes benefits for the old planNum.
    */
    public static void changeReferences(long planNum, long planNumTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), planNum, planNumTo);
            return ;
        }
         
        String command = new String();
        //change all references to the old plan to point to the new plan.
        //appointment.InsPlan1/2
        command = "UPDATE appointment SET InsPlan1=" + POut.long(planNumTo) + " WHERE InsPlan1=" + POut.long(planNum);
        Db.nonQ(command);
        command = "UPDATE appointment SET InsPlan2=" + POut.long(planNumTo) + " WHERE InsPlan2=" + POut.long(planNum);
        Db.nonQ(command);
        //benefit.PlanNum -- DELETE unused
        command = "DELETE FROM benefit WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        //claim.PlanNum/PlanNum2
        command = "UPDATE claim SET PlanNum=" + POut.long(planNumTo) + " WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        command = "UPDATE claim SET PlanNum2=" + POut.long(planNumTo) + " WHERE PlanNum2=" + POut.long(planNum);
        Db.nonQ(command);
        //claimproc.PlanNum
        command = "UPDATE claimproc SET PlanNum=" + POut.long(planNumTo) + " WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        //etrans.PlanNum
        command = "UPDATE etrans SET PlanNum=" + POut.long(planNumTo) + " WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        //inssub.PlanNum
        command = "UPDATE inssub SET PlanNum=" + POut.long(planNumTo) + " WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
        //payplan.PlanNum
        command = "UPDATE payplan SET PlanNum=" + POut.long(planNumTo) + " WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
    }

    //the old plan should then be deleted.
    /**
    * Returns the number of plans affected.
    */
    public static long setAllPlansToShowUCR() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        String command = "UPDATE insplan SET ClaimsUseUCR=1 WHERE PlanType=''";
        return Db.nonQ(command);
    }

    public static InsPlan getByCarrierName(String carrierName) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InsPlan>GetObject(MethodBase.GetCurrentMethod(), carrierName);
        }
         
        String command = "SELECT * FROM insplan WHERE CarrierNum=(SELECT CarrierNum FROM carrier WHERE CarrierName='" + POut.string(carrierName) + "')";
        InsPlan plan = Crud.InsPlanCrud.SelectOne(command);
        if (plan != null)
        {
            return plan;
        }
         
        Carrier carrier = Carriers.getByNameAndPhone(carrierName,"");
        plan = new InsPlan();
        plan.CarrierNum = carrier.CarrierNum;
        InsPlans.insert(plan);
        return plan;
    }

    public static List<long> getPlanNumsByCarrierNum(long carrierNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<long>>GetObject(MethodBase.GetCurrentMethod(), carrierNum);
        }
         
        String command = "SELECT PlanNum FROM insplan WHERE CarrierNum=" + POut.long(carrierNum);
        DataTable table = Db.getTable(command);
        List<long> planNums = new List<long>();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            planNums.Add(PIn.Long(table.Rows[i]["PlanNum"].ToString()));
        }
        return planNums;
    }

    public static void updateCobRuleForAll(EnumCobRule cobRule) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cobRule);
            return ;
        }
         
        String command = "UPDATE insplan SET CobRule=" + POut.int(((Enum)cobRule).ordinal());
        Db.nonQ(command);
    }

}


