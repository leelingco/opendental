//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitArraySorter;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitLogic;
import OpenDentBusiness.BenefitQuantity;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.CovCat;
import OpenDentBusiness.CovCats;
import OpenDentBusiness.CovSpan;
import OpenDentBusiness.CovSpans;
import OpenDentBusiness.Db;
import OpenDentBusiness.EbenefitCategory;
import OpenDentBusiness.FrequencyType;
import OpenDentBusiness.InsBenefitType;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Benefits   
{
    /**
    * Gets a list of all benefits for a given list of patplans for one patient.
    */
    public static List<Benefit> refresh(List<PatPlan> listForPat, List<InsSub> subList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Benefit>>GetObject(MethodBase.GetCurrentMethod(), listForPat, subList);
        }
         
        if (listForPat.Count == 0)
        {
            return new List<Benefit>();
        }
         
        InsSub sub;
        String s = "";
        for (int i = 0;i < listForPat.Count;i++)
        {
            if (i > 0)
            {
                s += " OR";
            }
             
            sub = InsSubs.GetSub(listForPat[i].InsSubNum, subList);
            s += " PlanNum=" + POut.long(sub.PlanNum);
            s += " OR";
            s += " PatPlanNum=" + POut.Long(listForPat[i].PatPlanNum);
        }
        String command = "SELECT * FROM benefit" + " WHERE" + s;
        List<Benefit> list = Crud.BenefitCrud.SelectMany(command);
        list.Sort(SortBenefits);
        return list;
    }

    public static int sortBenefits(Benefit b1, Benefit b2) throws Exception {
        return b1.toString().CompareTo(b2.toString());
    }

    /**
    * Used in the PlanEdit and FormClaimProc to get a list of benefits for specified plan and patPlan.  patPlanNum can be 0.
    */
    public static List<Benefit> refreshForPlan(long planNum, long patPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Benefit>>GetObject(MethodBase.GetCurrentMethod(), planNum, patPlanNum);
        }
         
        //,IFNULL(covcat.CovCatNum,0) AS covorder "
        //+" LEFT JOIN covcat ON covcat.CovCatNum=benefit.CovCatNum"
        String command = "SELECT *" + " FROM benefit" + " WHERE PlanNum = " + POut.long(planNum);
        if (patPlanNum != 0)
        {
            command += " OR PatPlanNum = " + POut.long(patPlanNum);
        }
         
        List<Benefit> list = Crud.BenefitCrud.SelectMany(command);
        list.Sort(SortBenefits);
        return list;
    }

    /*
    		///<summary>Used in the Plan edit window to get a typical list of benefits for all identical plans.  It used to exclude the supplied plan from the benefits, but no longer does that.  This behavior needs to be watched closely for possible bugs.</summary>
    		public static List<Benefit> RefreshForAll(InsPlan like) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				return Meth.GetObject<List<Benefit>>(MethodBase.GetCurrentMethod(),like);
    			}
    			if(like.CarrierNum==0){
    				return new List<Benefit>();
    			}
    			//We might try creating a temporary table out of the matched insurance plans, then join with
    			//the benefits table so that the query could be sped up.
    			//Get benefits for all identical plans
    			string command="SELECT b.BenefitNum,b.PlanNum,b.PatPlanNum,b.CovCatNum,b.BenefitType,"
    				+"b.Percent,b.MonetaryAmt,b.TimePeriod,b.QuantityQualifier,b.Quantity,b.CodeNum,b.CoverageLevel "
    				+"FROM insplan i,benefit b "
    				//+"WHERE PlanNum != "   +POut.PInt(like.PlanNum)+" "
    				+"WHERE i.PlanNum=b.PlanNum "
    				+"AND i.EmployerNum = '"+POut.Long(like.EmployerNum)+"' "
    				+"AND i.GroupName = '"+POut.String(like.GroupName)+"' "
    				+"AND i.GroupNum = '"+POut.String(like.GroupNum)+"' "
    				+"AND i.DivisionNo = '"+POut.String(like.DivisionNo)+"'"
    				+"AND i.CarrierNum = '"+POut.Long(like.CarrierNum)+"' "
    				+"AND i.IsMedical = '"+POut.Bool(like.IsMedical)+"' ";
    			Benefit[] benList=Crud.BenefitCrud.SelectMany(command).ToArray();
    			////Get planNums for all identical plans
    			//string command="SELECT PlanNum FROM insplan "
    			//  //+"WHERE PlanNum != "   +POut.PInt(like.PlanNum)+" "
    			//  +"WHERE EmployerNum = '" +POut.PLong(like.EmployerNum)+"' "
    			//  +"AND GroupName = '"   +POut.PString(like.GroupName)+"' "
    			//  +"AND GroupNum = '"    +POut.PString(like.GroupNum)+"' "
    			//  +"AND DivisionNo = '"  +POut.PString(like.DivisionNo)+"'"
    			//  +"AND CarrierNum = '"  +POut.PLong(like.CarrierNum)+"' "
    			//  +"AND IsMedical = '"   +POut.PBool(like.IsMedical)+"' ";
    			//DataTable table=Db.GetTable(command);
    			//string planNums="";
    			//for(int i=0;i<table.Rows.Count;i++) {
    			//  if(i>0) {
    			//    planNums+=" OR";
    			//  }
    			//  planNums+=" PlanNum="+table.Rows[i][0].ToString();
    			//}
    			//Benefit[] benList=new Benefit[0];
    			//if(table.Rows.Count>0){
    			//  //Get all benefits for all those plans
    			//  command="SELECT * FROM benefit WHERE"+planNums;
    			//  table=Db.GetTable(command);
    			//  benList=new Benefit[table.Rows.Count];
    			//  for(int i=0;i<table.Rows.Count;i++) {
    			//    benList[i]=new Benefit();
    			//    benList[i].BenefitNum       = PIn.PLong(table.Rows[i][0].ToString());
    			//    benList[i].PlanNum          = PIn.PLong(table.Rows[i][1].ToString());
    			//    benList[i].PatPlanNum       = PIn.PLong(table.Rows[i][2].ToString());
    			//    benList[i].CovCatNum        = PIn.PLong(table.Rows[i][3].ToString());
    			//    benList[i].BenefitType      = (InsBenefitType)PIn.PLong(table.Rows[i][4].ToString());
    			//    benList[i].Percent          = PIn.PInt(table.Rows[i][5].ToString());
    			//    benList[i].MonetaryAmt      = PIn.PDouble(table.Rows[i][6].ToString());
    			//    benList[i].TimePeriod       = (BenefitTimePeriod)PIn.PLong(table.Rows[i][7].ToString());
    			//    benList[i].QuantityQualifier= (BenefitQuantity)PIn.PLong(table.Rows[i][8].ToString());
    			//    benList[i].Quantity         = PIn.PInt(table.Rows[i][9].ToString());
    			//    benList[i].CodeNum          = PIn.PLong(table.Rows[i][10].ToString());
    			//    benList[i].CoverageLevel    = (BenefitCoverageLevel)PIn.PLong(table.Rows[i][11].ToString());
    			//  }
    			//}
    			//We could probably turn this last part into a group by within the query above in order to make this portion faster.
    			List<Benefit> retVal=new List<Benefit>();
    			//Loop through all benefits
    			bool matchFound;
    			for(int i=0;i<benList.Length;i++) {
    				//For each benefit, loop through retVal and compare.
    				matchFound=false;
    				for(int j=0;j<retVal.Count;j++) {
    					if(benList[i].CompareTo(retVal[j])==0) {//if the type is equal
    						matchFound=true;
    						break;
    					}
    				}
    				if(matchFound) {
    					continue;
    				}
    				//If no match found, then add it to the return list
    				retVal.Add(benList[i]);
    			}
    			for(int i=0;i<retVal.Count;i++) {
    				retVal[i].PlanNum=like.PlanNum;//change all the planNums to match the current plan
    				//all set to 0 if the plan IsForIdentical.
    			}
    			return retVal;
    		}*/
    /**
    * 
    */
    public static void update(Benefit ben) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ben);
            return ;
        }
         
        Crud.BenefitCrud.Update(ben);
    }

    /**
    * 
    */
    public static long insert(Benefit ben) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ben.BenefitNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ben);
            return ben.BenefitNum;
        }
         
        return Crud.BenefitCrud.Insert(ben);
    }

    /**
    * 
    */
    public static void delete(Benefit ben) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ben);
            return ;
        }
         
        String command = "DELETE FROM benefit WHERE BenefitNum =" + POut.long(ben.BenefitNum);
        Db.nonQ(command);
    }

    /**
    * Only for display purposes rather than any calculations.  Gets an annual max from the supplied list of benefits.  Ignores benefits that do not match either the planNum or the patPlanNum.  Because it starts at the top of the benefit list, it will get the most general limitation first.  Returns -1 if none found.  Usually, set isFam to false unless we are specifically interested in that value.
    */
    public static double getAnnualMaxDisplay(List<Benefit> benList, long planNum, long patPlanNum, boolean isFam) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<Benefit> matchingBens = new List<Benefit>();
        for (int i = 0;i < benList.Count;i++)
        {
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.Limitations)
            {
                continue;
            }
             
            if (benList[i].QuantityQualifier != BenefitQuantity.None)
            {
                continue;
            }
             
            if (benList[i].TimePeriod != BenefitTimePeriod.CalendarYear && benList[i].TimePeriod != BenefitTimePeriod.ServiceYear)
            {
                continue;
            }
             
            if (isFam)
            {
                if (benList[i].CoverageLevel != BenefitCoverageLevel.Family)
                {
                    continue;
                }
                 
            }
            else
            {
                //individ or none
                if (benList[i].CoverageLevel == BenefitCoverageLevel.Family)
                {
                    continue;
                }
                 
            } 
            //coverage level?
            if (benList[i].CodeNum != 0)
            {
                continue;
            }
             
            if (benList[i].CovCatNum != 0)
            {
                EbenefitCategory eben = CovCats.GetEbenCat(benList[i].CovCatNum);
                if (eben != EbenefitCategory.General && eben != EbenefitCategory.None)
                {
                    continue;
                }
                 
            }
             
            matchingBens.Add(benList[i]);
        }
        if (matchingBens.Count == 0)
        {
            return -1;
        }
         
        if (matchingBens.Count == 1)
        {
            return matchingBens[0].MonetaryAmt;
        }
         
        double minBen = new double();
        minBen = matchingBens[0].MonetaryAmt;
        for (int i = 1;i < matchingBens.Count;i++)
        {
            //Start counting at 1 to compare to 0.
            if (minBen > matchingBens[i].MonetaryAmt)
            {
                minBen = matchingBens[i].MonetaryAmt;
            }
             
        }
        return minBen;
    }

    /**
    * Only for display purposes rather than any calculations.  Gets a general deductible from the supplied list of benefits.  Ignores benefits that do not match either the planNum or the patPlanNum.
    */
    public static double getDeductGeneralDisplay(List<Benefit> benList, long planNum, long patPlanNum, BenefitCoverageLevel level) throws Exception {
        for (int i = 0;i < benList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.Deductible)
            {
                continue;
            }
             
            if (benList[i].QuantityQualifier != BenefitQuantity.None)
            {
                continue;
            }
             
            if (benList[i].TimePeriod != BenefitTimePeriod.CalendarYear && benList[i].TimePeriod != BenefitTimePeriod.ServiceYear)
            {
                continue;
            }
             
            if (benList[i].CoverageLevel != level)
            {
                continue;
            }
             
            if (benList[i].CodeNum != 0)
            {
                continue;
            }
             
            if (benList[i].CovCatNum != 0)
            {
                EbenefitCategory eben = CovCats.GetEbenCat(benList[i].CovCatNum);
                if (eben != EbenefitCategory.General && eben != EbenefitCategory.None)
                {
                    continue;
                }
                 
            }
             
            return benList[i].MonetaryAmt;
        }
        return -1;
    }

    /**
    * Used only in ClaimProcs.ComputeBaseEst.  Gets a deductible amount from the supplied list of benefits.  Ignores benefits that do not match either the planNum or the patPlanNum.  It figures out how much was already used and reduces the returned value by that amount.  Both individual and family deductibles will reduce the returned value independently.  Works for individual procs, categories, and general.
    */
    public static double getDeductibleByCode(List<Benefit> benList, long planNum, long patPlanNum, DateTime procDate, String procCode, List<ClaimProcHist> histList, List<ClaimProcHist> loopList, InsPlan plan, long patNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //first, create a much shorter list with only relevant benefits in it.
        List<Benefit> listShort = new List<Benefit>();
        for (int i = 0;i < benList.Count;i++)
        {
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.Deductible)
            {
                continue;
            }
             
            //if(benList[i].QuantityQualifier!=BenefitQuantity.None) {
            //	continue;
            //}
            if (benList[i].TimePeriod != BenefitTimePeriod.CalendarYear && benList[i].TimePeriod != BenefitTimePeriod.ServiceYear && benList[i].TimePeriod != BenefitTimePeriod.Lifetime)
            {
                continue;
            }
             
            //this is probably only going to be used in annual max, though
            listShort.Add(benList[i]);
        }
        //look for the best matching individual deduct----------------------------------------------------------------
        Benefit benIndGeneral = null;
        Benefit benInd = null;
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel == BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum == 0)
            {
                benInd = listShort[i];
                //This deductible must be a general deductible since it has no associated category
                benIndGeneral = listShort[i];
            }
             
        }
        //sum of deductibles should not exceed this amount, even if benInd is less.
        CovSpan[] spansForCat = new CovSpan[]();
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel == BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum != 0)
            {
                //see if the span matches
                spansForCat = CovSpans.GetForCat(listShort[i].CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(procCode, spansForCat[j].FromCode) >= 0 && String.Compare(procCode, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
                //no match
                if (benInd != null && benInd.CovCatNum != 0)
                {
                    //must compare
                    //only use the new one if the item order is larger
                    if (CovCats.GetOrderShort(listShort[i].CovCatNum) > CovCats.getOrderShort(benInd.CovCatNum))
                    {
                        benInd = listShort[i];
                    }
                     
                }
                else
                {
                    //first one encountered for a category
                    benInd = listShort[i];
                } 
            }
             
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel == BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum == 0)
            {
                continue;
            }
             
            if (StringSupport.equals(procCode, ProcedureCodes.GetStringProcCode(listShort[i].CodeNum)))
            {
                benInd = listShort[i];
            }
             
        }
        //look for the best matching family deduct----------------------------------------------------------------
        Benefit benFam = null;
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel != BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum == 0)
            {
                benFam = listShort[i];
            }
             
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel != BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum != 0)
            {
                //see if the span matches
                spansForCat = CovSpans.GetForCat(listShort[i].CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(procCode, spansForCat[j].FromCode) >= 0 && String.Compare(procCode, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
                //no match
                if (benFam != null && benFam.CovCatNum != 0)
                {
                    //must compare
                    //only use the new one if the item order is larger
                    if (CovCats.GetOrderShort(listShort[i].CovCatNum) > CovCats.getOrderShort(benFam.CovCatNum))
                    {
                        benFam = listShort[i];
                    }
                     
                }
                else
                {
                    //first one encountered for a category
                    benFam = listShort[i];
                } 
            }
             
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel != BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum == 0)
            {
                continue;
            }
             
            if (StringSupport.equals(procCode, ProcedureCodes.GetStringProcCode(listShort[i].CodeNum)))
            {
                benFam = listShort[i];
            }
             
        }
        //example. $50 individual deduct, $150 family deduct.
        //Only individual deductibles make sense as the starting point.
        //Family deductible just limits the sum of individual deductibles.
        //If there is no individual deductible that matches, then return 0.
        if (benInd == null || benInd.MonetaryAmt == -1 || benInd.MonetaryAmt == 0)
        {
            return 0;
        }
         
        double retVal = benInd.MonetaryAmt;
        //reduce by amount individual already paid this year--------------------------------------------------------------------
        //establish date range for procedures to consider
        DateTime dateStart = BenefitLogic.ComputeRenewDate(procDate, plan.MonthRenew);
        DateTime dateEnd = procDate;
        //I guess we don't want to consider anything after the date of this procedure.
        if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
        {
            dateStart = DateTime.MinValue;
        }
         
        for (int i = 0;i < histList.Count;i++)
        {
            if (histList[i].PlanNum != planNum)
            {
                continue;
            }
             
            //different plan
            if (histList[i].ProcDate < dateStart || histList[i].ProcDate > dateEnd)
            {
                continue;
            }
             
            if (histList[i].PatNum != patNum)
            {
                continue;
            }
            else //this is for someone else in the family
            //Procedure specific deductibles need to take all deductibles into consideration, not just deductibles that have been applied towards the specific procedure.
            //if(benInd.CodeNum!=0) {//specific code
            //	if(ProcedureCodes.GetStringProcCode(benInd.CodeNum)!=histList[i].StrProcCode) {
            //		continue;
            //	}
            //}
            if (benInd.CovCatNum != 0)
            {
                //specific category
                spansForCat = CovSpans.getForCat(benInd.CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(histList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(histList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
            }
              
            //if no category, then benefits are not restricted by proc code.
            if (histList[i].Deduct == -1)
            {
                continue;
            }
             
            retVal -= histList[i].Deduct;
        }
        //now, do a similar thing with loopList, individ-----------------------------------------------------------------------
        //There is a kludgey workaround in the loop below.
        //It handles the very specific problem of a single diagnostic/preventive deductible even though we have two separate benefits for it.
        //The better solution will be benefits with multiple categories or spans.
        //This workaround is only applied if there are both a diagnostic and a preventive deductible for the same amount.
        //If the benefit is diagnostic, then also check the spans of the preventive category
        CovSpan[] otherSpans = null;
        if (CovCats.getEbenCat(benInd.CovCatNum) == EbenefitCategory.Diagnostic)
        {
            for (int i = 0;i < listShort.Count;i++)
            {
                //look through the benefits again
                if (listShort[i].CoverageLevel != BenefitCoverageLevel.Individual)
                {
                    continue;
                }
                 
                if (CovCats.GetEbenCat(listShort[i].CovCatNum) != EbenefitCategory.RoutinePreventive)
                {
                    continue;
                }
                 
                otherSpans = CovSpans.GetForCat(listShort[i].CovCatNum);
            }
        }
         
        //if the benefit is preventive, then also check the spans of the diagnostic category
        if (CovCats.getEbenCat(benInd.CovCatNum) == EbenefitCategory.RoutinePreventive)
        {
            for (int i = 0;i < listShort.Count;i++)
            {
                //look through the benefits again
                if (listShort[i].CoverageLevel != BenefitCoverageLevel.Individual)
                {
                    continue;
                }
                 
                if (CovCats.GetEbenCat(listShort[i].CovCatNum) != EbenefitCategory.Diagnostic)
                {
                    continue;
                }
                 
                otherSpans = CovSpans.GetForCat(listShort[i].CovCatNum);
            }
        }
         
        for (int i = 0;i < loopList.Count;i++)
        {
            //no date restriction, since all TP or part of current claim
            //if(histList[i].ProcDate<dateStart || histList[i].ProcDate>dateEnd) {
            //	continue;
            //}
            if (loopList[i].PlanNum != planNum)
            {
                continue;
            }
             
            //different plan.  Even the loop list can contain info for multiple plans.
            if (loopList[i].PatNum != patNum)
            {
                continue;
            }
             
            //this is for someone else in the family
            //Loop list needs to consider procedure specific codes so that deductibles apply to other procedures within a TP if necessary.  E.g. Unit Test 16
            if (benInd.CodeNum != 0)
            {
                //specific code
                if (!StringSupport.equals(ProcedureCodes.getStringProcCode(benInd.CodeNum), loopList[i].StrProcCode))
                {
                    continue;
                }
                 
            }
            else if (benInd.CovCatNum != 0)
            {
                //specific category
                spansForCat = CovSpans.getForCat(benInd.CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(loopList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(loopList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (otherSpans != null)
                {
                    for (int j = 0;j < otherSpans.Length;j++)
                    {
                        if (String.Compare(loopList[i].StrProcCode, otherSpans[j].FromCode) >= 0 && String.Compare(loopList[i].StrProcCode, otherSpans[j].ToCode) <= 0)
                        {
                            isMatch = true;
                            break;
                        }
                         
                    }
                }
                 
                if (!isMatch)
                {
                    continue;
                }
                 
            }
              
            //if no category, then benefits are not restricted by proc code.
            if (loopList[i].Deduct == -1)
            {
                continue;
            }
             
            retVal -= loopList[i].Deduct;
        }
        if (retVal <= 0)
        {
            return 0;
        }
         
        double deductUsedInLoopList = 0;
        for (int i = 0;i < loopList.Count;i++)
        {
            //sum of deductibles in looplist
            deductUsedInLoopList += loopList[i].Deduct;
        }
        if (benIndGeneral != null)
        {
            //if there exists a general deductible
            if ((retVal + deductUsedInLoopList) > benIndGeneral.MonetaryAmt)
            {
                //if this would put us over the general deductible, instead make that amount
                //examples: if (25+45) > 50, then return 50-45=5.
                retVal = benIndGeneral.MonetaryAmt - deductUsedInLoopList;
            }
             
        }
         
        // (fix for Unit Test 16)
        //if there is still a deductible, we might still reduce it based on family ded used.
        if (benFam == null || benFam.MonetaryAmt == -1)
        {
            return retVal;
        }
         
        double famded = benFam.MonetaryAmt;
        for (int i = 0;i < histList.Count;i++)
        {
            //reduce the family deductible by amounts already used----------------------------------------------------------
            if (histList[i].ProcDate < dateStart || histList[i].ProcDate > dateEnd)
            {
                continue;
            }
             
            if (histList[i].PlanNum != planNum)
            {
                continue;
            }
            else //different plan
            //now, we do want to see all family members.
            //if(histList[i].PatNum != patNum) {
            //	continue;//this is for someone else in the family
            //}
            //Procedure specific deductibles need to take all deductibles into consideration, not just deductibles that have been applied towards the specific procedure.
            //if(benFam.CodeNum!=0) {//specific code
            //	if(ProcedureCodes.GetStringProcCode(benFam.CodeNum)!=histList[i].StrProcCode) {
            //		continue;
            //	}
            //}
            if (benFam.CovCatNum != 0)
            {
                //specific category
                spansForCat = CovSpans.getForCat(benFam.CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(histList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(histList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
            }
              
            //if no category, then benefits are not restricted by proc code.
            if (histList[i].Deduct == -1)
            {
                continue;
            }
             
            famded -= histList[i].Deduct;
        }
        for (int i = 0;i < loopList.Count;i++)
        {
            //reduce family ded by amounts already used in loop---------------------------------------------------------------
            if (loopList[i].PlanNum != planNum)
            {
                continue;
            }
             
            //different plan
            //Loop list needs to consider procedure specific codes so that deductibles apply to other procedures within a TP if necessary.
            if (benFam.CodeNum != 0)
            {
                //specific code
                if (!StringSupport.equals(ProcedureCodes.getStringProcCode(benFam.CodeNum), loopList[i].StrProcCode))
                {
                    continue;
                }
                 
            }
            else if (benFam.CovCatNum != 0)
            {
                //specific category
                spansForCat = CovSpans.getForCat(benFam.CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(loopList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(loopList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
            }
              
            //if no category, then benefits are not restricted by proc code.
            if (loopList[i].Deduct == -1)
            {
                continue;
            }
             
            famded -= loopList[i].Deduct;
        }
        //if the family deductible has all been used up on other procs
        if (famded <= 0)
        {
            return 0;
        }
         
        //then no deductible, regardless of what we computed for individual
        if (retVal > famded)
        {
            return famded;
        }
         
        return retVal;
    }

    //example; retInd=$50, but 120 of 150 family ded has been used.  famded=30.  We need to return 30.
    /**
    * Used only in ClaimProcs.ComputeBaseEst.  Calculates the most specific limitation for the specified code.  This is usually an annual max, ortho max, or fluoride limitation (only if age match).  Ignores benefits that do not match either the planNum or the patPlanNum.  It figures out how much was already used and reduces the returned value by that amount.  Both individual and family limitations will reduce the returned value independently.  Works for individual procs, categories, and general.  Also outputs a string description of the limitation.  There don't seem to be any situations where multiple limitations would each partially reduce coverage for a single code, other than ind/fam.  The returned value will be the original insEstTotal passed in unless there was some limitation that reduced it.
    */
    public static double getLimitationByCode(List<Benefit> benList, long planNum, long patPlanNum, DateTime procDate, String procCodeStr, List<ClaimProcHist> histList, List<ClaimProcHist> loopList, InsPlan plan, long patNum, RefSupport<String> note, double insEstTotal, int patientAge, long insSubNum) throws Exception {
        //No need to check RemotingRole;no call to db.
        note.setValue("");
        //first, create a much shorter list with only relevant benefits in it.
        List<Benefit> listShort = new List<Benefit>();
        for (int i = 0;i < benList.Count;i++)
        {
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.Limitations)
            {
                continue;
            }
             
            //if(benList[i].TimePeriod!=BenefitTimePeriod.CalendarYear
            //	&& benList[i].TimePeriod!=BenefitTimePeriod.ServiceYear
            //	&& benList[i].TimePeriod!=BenefitTimePeriod.Lifetime)
            //{
            //	continue;
            //}
            listShort.Add(benList[i]);
        }
        //look for the best matching individual limitation----------------------------------------------------------------
        Benefit benInd = null;
        for (int i = 0;i < listShort.Count;i++)
        {
            //start with no category
            if (listShort[i].CoverageLevel == BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum == 0)
            {
                benInd = listShort[i];
            }
             
        }
        //then, specific category.
        CovSpan[] spansForCat = new CovSpan[]();
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CoverageLevel == BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum != 0)
            {
                //see if the span matches
                spansForCat = CovSpans.GetForCat(listShort[i].CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(procCodeStr, spansForCat[j].FromCode) >= 0 && String.Compare(procCodeStr, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
                //no match
                if (listShort[i].QuantityQualifier == BenefitQuantity.NumberOfServices || listShort[i].QuantityQualifier == BenefitQuantity.Months || listShort[i].QuantityQualifier == BenefitQuantity.Years)
                {
                    continue;
                }
                 
                //exclude frequencies
                //If it's an age based limitation, then make sure the patient age matches.
                //If we have an age match, then we exit the method right here.
                if (listShort[i].QuantityQualifier == BenefitQuantity.AgeLimit && listShort[i].Quantity > 0)
                {
                    if (patientAge > listShort[i].Quantity)
                    {
                        note.setValue(Lans.g("Benefits","Age limitation:") + " " + listShort[i].Quantity.ToString());
                        return 0;
                    }
                     
                }
                 
                //not covered if too old.
                if (benInd != null && benInd.CovCatNum != 0)
                {
                    //must compare
                    //only use the new one if the item order is larger
                    if (CovCats.GetOrderShort(listShort[i].CovCatNum) > CovCats.getOrderShort(benInd.CovCatNum))
                    {
                        benInd = listShort[i];
                    }
                     
                }
                else
                {
                    //first one encountered for a category
                    benInd = listShort[i];
                } 
            }
             
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            //then, specific code
            if (listShort[i].CoverageLevel == BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum == 0)
            {
                continue;
            }
             
            if (!StringSupport.equals(procCodeStr, ProcedureCodes.GetStringProcCode(listShort[i].CodeNum)))
            {
                continue;
            }
             
            if (listShort[i].QuantityQualifier == BenefitQuantity.NumberOfServices || listShort[i].QuantityQualifier == BenefitQuantity.Months || listShort[i].QuantityQualifier == BenefitQuantity.Years)
            {
                continue;
            }
             
            //exclude frequencies
            //if it's an age based limitation, then make sure the patient age matches.
            //If we have an age match, then we exit the method right here.
            if (listShort[i].QuantityQualifier == BenefitQuantity.AgeLimit && listShort[i].Quantity > 0)
            {
                if (patientAge > listShort[i].Quantity)
                {
                    note.setValue(Lans.g("Benefits","Age limitation:") + " " + listShort[i].Quantity.ToString());
                    return 0;
                }
                 
            }
            else
            {
                //not covered if too old.
                //anything but an age limit
                benInd = listShort[i];
            } 
        }
        //look for the best matching family limitation----------------------------------------------------------------
        Benefit benFam = null;
        for (int i = 0;i < listShort.Count;i++)
        {
            //start with no category
            if (listShort[i].CoverageLevel != BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum == 0)
            {
                benFam = listShort[i];
            }
             
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            //then, specific category.
            if (listShort[i].CoverageLevel != BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum != 0)
            {
                //see if the span matches
                spansForCat = CovSpans.GetForCat(listShort[i].CovCatNum);
                boolean isMatch = false;
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    if (String.Compare(procCodeStr, spansForCat[j].FromCode) >= 0 && String.Compare(procCodeStr, spansForCat[j].ToCode) <= 0)
                    {
                        isMatch = true;
                        break;
                    }
                     
                }
                if (!isMatch)
                {
                    continue;
                }
                 
                //no match
                if (benFam != null && benFam.CovCatNum != 0)
                {
                    //must compare
                    //only use the new one if the item order is larger
                    if (CovCats.GetOrderShort(listShort[i].CovCatNum) > CovCats.getOrderShort(benFam.CovCatNum))
                    {
                        benFam = listShort[i];
                    }
                     
                }
                else
                {
                    //first one encountered for a category
                    benFam = listShort[i];
                } 
            }
             
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            //then, specific code
            if (listShort[i].CoverageLevel != BenefitCoverageLevel.Family)
            {
                continue;
            }
             
            if (listShort[i].CodeNum == 0)
            {
                continue;
            }
             
            if (StringSupport.equals(procCodeStr, ProcedureCodes.GetStringProcCode(listShort[i].CodeNum)))
            {
                benFam = listShort[i];
            }
             
        }
        //example. $1000 individual max, $3000 family max.
        //Only individual max makes sense as the starting point.								Only family max is now being considered as well.
        //Family max just limits the sum of individual maxes.										Family max may be the only cap being used.
        //If there is no individual limitation that matches, then return 0.     No longer valid. Return amount covered by ins, whether individual or family max.
        //fluoride age limit already handled, so all that's left is maximums.   ...
        if ((benInd == null || benInd.MonetaryAmt == -1 || benInd.MonetaryAmt == 0) && (benFam == null || benFam.MonetaryAmt == -1 || benFam.MonetaryAmt == 0))
        {
            return insEstTotal;
        }
         
        //if(benInd==null || benInd.MonetaryAmt==-1 || benInd.MonetaryAmt==0) {
        //no max found for this code.
        double maxInd = 0;
        if (benInd != null)
        {
            maxInd = benInd.MonetaryAmt;
        }
         
        //reduce individual max by amount already paid this year/lifetime---------------------------------------------------
        //establish date range for procedures to consider
        DateTime dateStart = BenefitLogic.ComputeRenewDate(procDate, plan.MonthRenew);
        DateTime dateEnd = procDate;
        //don't consider anything after the date of this procedure.
        if (benInd != null)
        {
            if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
            {
                dateStart = DateTime.MinValue;
            }
             
            for (int i = 0;i < histList.Count;i++)
            {
                if (histList[i].InsSubNum != insSubNum)
                {
                    continue;
                }
                 
                //different plan
                if (histList[i].ProcDate < dateStart || histList[i].ProcDate > dateEnd)
                {
                    continue;
                }
                 
                if (histList[i].PatNum != patNum)
                {
                    continue;
                }
                 
                //this is for someone else in the family //SHOULD PROBABLY NOT SKIP THIS IN THE CASE OF FAM BUT NO IND MAX. :(
                if (benInd.CodeNum != 0)
                {
                    //specific code
                    //Enhance this later when code spans are supported.
                    if (!StringSupport.equals(ProcedureCodes.getStringProcCode(benInd.CodeNum), histList[i].StrProcCode))
                    {
                        continue;
                    }
                     
                }
                else if (benInd.CovCatNum != 0)
                {
                    //specific category
                    spansForCat = CovSpans.getForCat(benInd.CovCatNum);
                    boolean isMatch = false;
                    if (StringSupport.equals(histList[i].StrProcCode, ""))
                    {
                        //If this was a 'total' payment that was not attached to a procedure
                        if (CovCats.getEbenCat(benInd.CovCatNum) == EbenefitCategory.General)
                        {
                            //And if this is the general category
                            //Then it should affect this max.
                            isMatch = true;
                        }
                         
                    }
                    else
                    {
                        for (int j = 0;j < spansForCat.Length;j++)
                        {
                            //If the payment was attached to a proc, then the proc must be in the coderange of this annual max benefit
                            if (String.Compare(histList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(histList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                            {
                                isMatch = true;
                                break;
                            }
                             
                        }
                    } 
                    if (!isMatch)
                    {
                        continue;
                    }
                     
                }
                  
                //if no category, then benefits are not restricted by proc code.
                //In other words, the benefit applies to all codes.
                //At this point, we know that the proc in the loopList falls within this max benefit.
                //But it may also fall within a more restrictive benefit which would take precedence over this one.
                if (TighterLimitExists(listShort, benInd, histList[i]))
                {
                    continue;
                }
                 
                maxInd -= histList[i].Amount;
            }
        }
         
        //reduce individual max by amount in loop ------------------------------------------------------------------
        if (benInd != null)
        {
            for (int i = 0;i < loopList.Count;i++)
            {
                //no date restriction, since all TP or part of current claim
                //if(histList[i].ProcDate<dateStart || histList[i].ProcDate>dateEnd) {
                //	continue;
                //}
                if (loopList[i].InsSubNum != insSubNum)
                {
                    continue;
                }
                 
                //different plan.  Even the loop list can contain info for multiple plans.
                if (loopList[i].PatNum != patNum)
                {
                    continue;
                }
                 
                //this is for someone else in the family
                if (benInd.CodeNum != 0)
                {
                    //specific code
                    //Enhance this later when code spans are supported.
                    if (!StringSupport.equals(ProcedureCodes.getStringProcCode(benInd.CodeNum), loopList[i].StrProcCode))
                    {
                        continue;
                    }
                     
                }
                else if (benInd.CovCatNum != 0)
                {
                    //specific category
                    spansForCat = CovSpans.getForCat(benInd.CovCatNum);
                    boolean isMatch = false;
                    if (StringSupport.equals(loopList[i].StrProcCode, ""))
                    {
                        //If this was a 'total' payment that was not attached to a procedure
                        if (CovCats.getEbenCat(benInd.CovCatNum) == EbenefitCategory.General)
                        {
                            //And if this is the general category
                            //Then it should affect this max.
                            isMatch = true;
                        }
                         
                    }
                    else
                    {
                        for (int j = 0;j < spansForCat.Length;j++)
                        {
                            //If the payment was attached to a proc, then the proc must be in the coderange of this annual max benefit
                            if (String.Compare(loopList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(loopList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                            {
                                isMatch = true;
                                break;
                            }
                             
                        }
                    } 
                    if (!isMatch)
                    {
                        continue;
                    }
                     
                }
                else
                {
                    //if no category, then benefits are not normally restricted by proc code.
                    //The problem is that if the amount in the loop is from an ortho proc, then the general category will exclude ortho.
                    //But sometimes, the annual max is in the system as no category instead of general category.
                    CovCat generalCat = CovCats.getForEbenCat(EbenefitCategory.General);
                    if (generalCat != null)
                    {
                        //If there is a general category, then we only consider codes within it.  This is how we exclude ortho.
                        CovSpan[] covSpanArray = CovSpans.getForCat(generalCat.CovCatNum);
                        if (!StringSupport.equals(loopList[i].StrProcCode, "") && !CovSpans.IsCodeInSpans(loopList[i].StrProcCode, covSpanArray))
                        {
                            continue;
                        }
                         
                    }
                     
                }  
                //for example, ortho
                //At this point, we know that the proc in the loopList falls within this max benefit.
                //But it may also fall within a more restrictive benefit which would take precedence over this one.
                if (TighterLimitExists(listShort, benInd, loopList[i]))
                {
                    continue;
                }
                 
                maxInd -= loopList[i].Amount;
            }
        }
         
        if (benInd != null)
        {
            if (maxInd <= 0)
            {
                //then patient has used up all of their annual max, so no coverage.
                if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over lifetime max"));
                }
                else if (benInd.TimePeriod == BenefitTimePeriod.CalendarYear || benInd.TimePeriod == BenefitTimePeriod.ServiceYear)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over annual max"));
                }
                  
                return 0;
            }
             
        }
         
        double retVal = insEstTotal;
        if (benInd != null)
        {
            if (maxInd < insEstTotal)
            {
                //if there's not enough left in the individual annual max to cover this proc.
                retVal = maxInd;
            }
             
        }
         
        //insurance will only cover up to the remaining annual max
        //Three situations:
        //1. Ind only.
        //Handled in the next 10 lines, then return.
        //
        //2. Ind and Fam max present.
        //There seems to be enough to cover at least part of this procedure.
        //There may also be a family max that has been met which may partially or completely reduce coverage of this proc.
        //
        //3. Fam only.  We don't know how much is left.
        //maxInd=-1
        //benInd=null
        if (benFam == null || benFam.MonetaryAmt == -1)
        {
            //if no family max.  Ind only.
            if (retVal != insEstTotal)
            {
                //and procedure is not fully covered by ind max
                if (benInd != null)
                {
                    //redundant
                    if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
                    {
                        note.setValue(note.getValue() + Lans.g("Benefits","Over lifetime max"));
                    }
                    else if (benInd.TimePeriod == BenefitTimePeriod.CalendarYear || benInd.TimePeriod == BenefitTimePeriod.ServiceYear)
                    {
                        note.setValue(note.getValue() + Lans.g("Benefits","Over annual max"));
                    }
                      
                }
                 
            }
             
            return retVal;
        }
         
        //no family max anyway, so no need to go further.
        double maxFam = benFam.MonetaryAmt;
        for (int i = 0;i < histList.Count;i++)
        {
            //reduce the family max by amounts already used----------------------------------------------------------
            if (histList[i].ProcDate < dateStart || histList[i].ProcDate > dateEnd)
            {
                continue;
            }
             
            if (histList[i].PlanNum != planNum)
            {
                continue;
            }
             
            //different plan
            //now, we do want to see all family members.
            //if(histList[i].PatNum != patNum) {
            //	continue;//this is for someone else in the family
            //}
            if (benFam.CodeNum != 0)
            {
                //specific code
                if (!StringSupport.equals(ProcedureCodes.getStringProcCode(benFam.CodeNum), histList[i].StrProcCode))
                {
                    continue;
                }
                 
            }
            else if (benFam.CovCatNum != 0)
            {
                //specific category
                spansForCat = CovSpans.getForCat(benFam.CovCatNum);
                boolean isMatch = false;
                if (StringSupport.equals(histList[i].StrProcCode, ""))
                {
                    //If this was a 'total' payment that was not attached to a procedure
                    if (CovCats.getEbenCat(benFam.CovCatNum) == EbenefitCategory.General)
                    {
                        //And if this is the general category
                        //Then it should affect this max.
                        isMatch = true;
                    }
                     
                }
                else
                {
                    for (int j = 0;j < spansForCat.Length;j++)
                    {
                        if (String.Compare(histList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(histList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                        {
                            isMatch = true;
                            break;
                        }
                         
                    }
                } 
                if (!isMatch)
                {
                    continue;
                }
                 
            }
              
            //if no category, then benefits are not restricted by proc code.
            maxFam -= histList[i].Amount;
        }
        for (int i = 0;i < loopList.Count;i++)
        {
            //reduce family max by amounts already used in loop---------------------------------------------------------------
            if (loopList[i].PlanNum != planNum)
            {
                continue;
            }
             
            //different plan
            if (benFam.CodeNum != 0)
            {
                //specific code
                if (!StringSupport.equals(ProcedureCodes.getStringProcCode(benFam.CodeNum), loopList[i].StrProcCode))
                {
                    continue;
                }
                 
            }
            else if (benFam.CovCatNum != 0)
            {
                //specific category
                spansForCat = CovSpans.getForCat(benFam.CovCatNum);
                boolean isMatch = false;
                if (StringSupport.equals(loopList[i].StrProcCode, ""))
                {
                    //If this was a 'total' payment that was not attached to a procedure
                    if (CovCats.getEbenCat(benFam.CovCatNum) == EbenefitCategory.General)
                    {
                        //And if this is the general category
                        //Then it should affect this max.
                        isMatch = true;
                    }
                     
                }
                else
                {
                    for (int j = 0;j < spansForCat.Length;j++)
                    {
                        if (String.Compare(loopList[i].StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(loopList[i].StrProcCode, spansForCat[j].ToCode) <= 0)
                        {
                            isMatch = true;
                            break;
                        }
                         
                    }
                } 
                if (!isMatch)
                {
                    continue;
                }
                 
            }
              
            //if no category, then benefits are not restricted by proc code.
            maxFam -= loopList[i].Amount;
        }
        //if the family max has all been used up on other procs
        if (maxFam <= 0)
        {
            if (benInd == null)
            {
                note.setValue(note.getValue() + Lans.g("Benefits","Over family max"));
            }
            else
            {
                //and there is an individual max.
                if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over family lifetime max"));
                }
                else if (benInd.TimePeriod == BenefitTimePeriod.CalendarYear || benInd.TimePeriod == BenefitTimePeriod.ServiceYear)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over family annual max"));
                }
                  
            } 
            return 0;
        }
         
        //then no coverage, regardless of what we computed for individual
        //This section was causing a bug. I'm really not even sure what it was attempting to do, since it's common for family max to be greater than indiv max
        /*if(maxFam > maxInd) {//restrict by maxInd
        				//which we already calculated
        				if(benInd.TimePeriod==BenefitTimePeriod.Lifetime) {
        					note+=Lans.g("Benefits","Over lifetime max");
        				}
        				else if(benInd.TimePeriod==BenefitTimePeriod.CalendarYear
        					|| benInd.TimePeriod==BenefitTimePeriod.ServiceYear) {
        					note+=Lans.g("Benefits","Over annual max");
        				}
        				return retVal;
        			}*/
        if ((benInd == null) || (maxFam < maxInd))
        {
            //restrict by maxFam
            if (maxFam < retVal)
            {
                //if there's not enough left in the annual max to cover this proc.
                //example. retVal=$70.  But 2970 of 3000 family max has been used.  maxFam=30.  We need to return 30.
                if (benInd == null)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over family max"));
                }
                else
                {
                    //both ind and fam
                    if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
                    {
                        note.setValue(note.getValue() + Lans.g("Benefits","Over family lifetime max"));
                    }
                    else if (benInd.TimePeriod == BenefitTimePeriod.CalendarYear || benInd.TimePeriod == BenefitTimePeriod.ServiceYear)
                    {
                        note.setValue(note.getValue() + Lans.g("Benefits","Over family annual max"));
                    }
                      
                } 
                return maxFam;
            }
             
        }
         
        //insurance will only cover up to the remaining annual max
        if (retVal < insEstTotal)
        {
            //must have been an individual restriction
            if (benInd == null)
            {
                //js I don't understand this situation. It will probably not happen, but this is safe.
                note.setValue(note.getValue() + Lans.g("Benefits","Over annual max"));
            }
            else
            {
                if (benInd.TimePeriod == BenefitTimePeriod.Lifetime)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over lifetime max"));
                }
                else if (benInd.TimePeriod == BenefitTimePeriod.CalendarYear || benInd.TimePeriod == BenefitTimePeriod.ServiceYear)
                {
                    note.setValue(note.getValue() + Lans.g("Benefits","Over annual max"));
                }
                  
            } 
        }
         
        return retVal;
    }

    private static boolean tighterLimitExists(List<Benefit> listShort, Benefit benefit, ClaimProcHist claimProcHist) throws Exception {
        if (StringSupport.equals(claimProcHist.StrProcCode, ""))
        {
            return false;
        }
         
        //If this was a 'total' payment that was not attached to a procedure
        //there won't be anything more restrictive.
        //The list is not in order by restrictive/broad, so tests will need to be done.
        if (benefit.CodeNum != 0)
        {
            return false;
        }
         
        for (int b = 0;b < listShort.Count;b++)
        {
            //The benefit is already restrictive.  There isn't currently a way to have a more restrictive benefit, although in the future when code ranges are supported, a tighter code range would be more restrictive.
            if (listShort[b].BenefitNum == benefit.BenefitNum)
            {
                continue;
            }
             
            //skip self.
            if (listShort[b].QuantityQualifier != BenefitQuantity.None)
            {
                continue;
            }
             
            //it must be some other kind of limitation other than an amount limit.  For example, BW frequency.
            if (listShort[b].CodeNum != 0)
            {
                if (listShort[b].CodeNum == ProcedureCodes.getCodeNum(claimProcHist.StrProcCode))
                {
                    return true;
                }
                 
            }
            else //Enhance later for code ranges when supported by program
            //a tighter limitation benefit exists for this specific procedure code.
            if (listShort[b].CovCatNum != 0)
            {
                //specific category
                if (benefit.CovCatNum != 0)
                {
                    //If benefit is a category limitation,
                    //then we can only consider categories that are more restrictive (further down on list).
                    //either of these could be -1 if isHidden
                    int orderBenefit = CovCats.getOrderShort(benefit.CovCatNum);
                    int orderTest = CovCats.GetOrderShort(listShort[b].CovCatNum);
                    if (orderBenefit == -1)
                    {
                    }
                    else //nothing to do here.  Treat it like a general limitation
                    if (orderTest < orderBenefit)
                    {
                        continue;
                    }
                      
                }
                else
                {
                } 
                //the CovCat of listShort is further up in the list and less restrictive, so should not be considered.
                //this handles orderTest=-1, skipping the hidden category
                //But if this is a general limitation,
                //then we don't need to do the above check because any match can be considered more restrictive.
                //see if the claimProcHist is in this more restrictive category
                CovSpan[] spansForCat = CovSpans.GetForCat(listShort[b].CovCatNum);
                for (int j = 0;j < spansForCat.Length;j++)
                {
                    //get the spans
                    //This must be a payment that was attached to a proc, so test the proc to be in the coderange of this annual max benefit
                    if (String.Compare(claimProcHist.StrProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(claimProcHist.StrProcCode, spansForCat[j].ToCode) <= 0)
                    {
                        return true;
                    }
                     
                }
            }
              
        }
        return false;
    }

    /**
    * Only used from InsPlans.GetInsUsedDisplay.  If a procedure is handled by some limitation other than a general annual max, then we don't want it to count towards the annual max.
    */
    public static boolean limitationExistsNotGeneral(List<Benefit> benList, long planNum, long patPlanNum, String strProcCode) throws Exception {
        EbenefitCategory eben = EbenefitCategory.None;
        CovSpan[] covSpanArray = new CovSpan[]();
        for (int i = 0;i < benList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.Limitations)
            {
                continue;
            }
             
            if (benList[i].QuantityQualifier != BenefitQuantity.None)
            {
                continue;
            }
             
            if (benList[i].TimePeriod != BenefitTimePeriod.CalendarYear && benList[i].TimePeriod != BenefitTimePeriod.ServiceYear && benList[i].TimePeriod != BenefitTimePeriod.Lifetime)
            {
                continue;
            }
             
            //allows ortho max to be caught further down
            //coverage level?
            if (benList[i].CodeNum != 0)
            {
                if (benList[i].CodeNum == ProcedureCodes.getCodeNum(strProcCode))
                {
                    return true;
                }
                 
            }
             
            //Enhance later for code ranges when supported by program
            //a limitation benefit exists for this specific procedure code.
            if (benList[i].CovCatNum != 0)
            {
                eben = CovCats.GetEbenCat(benList[i].CovCatNum);
                if (eben == EbenefitCategory.General || eben == EbenefitCategory.None)
                {
                    continue;
                }
                 
                //ignore this general benefit
                covSpanArray = CovSpans.GetForCat(benList[i].CovCatNum);
                if (CovSpans.IsCodeInSpans(strProcCode, covSpanArray))
                {
                    return true;
                }
                 
            }
             
        }
        return false;
    }

    //a limitation benefit exists for a category that contains this procedure code.
    /**
    * Used from ClaimProc.ComputeBaseEst and in sheet output. This is a low level function to get the percent to store in a claimproc.  It does not consider any percentOverride.  Always returns a number between 0 and 100.  Handles general, category, or procedure level.  Does not handle pat vs family coveragelevel.  Does handle patient override by using patplan.  Does not need to be aware of procedure history or loop history.
    */
    public static int getPercent(String procCodeStr, String planType, long planNum, long patPlanNum, List<Benefit> benList) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (StringSupport.equals(planType, "f") || StringSupport.equals(planType, "c"))
        {
            return 100;
        }
         
        //flat and cap are always covered 100%
        //first, create a much shorter list with only relevant benefits in it.
        List<Benefit> listShort = new List<Benefit>();
        for (int i = 0;i < benList.Count;i++)
        {
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.CoInsurance)
            {
                continue;
            }
             
            if (benList[i].Percent == -1)
            {
                continue;
            }
             
            listShort.Add(benList[i]);
        }
        //Find the best benefit matches.
        //Plan and Pat here indicate patplan override and have nothing to do with pat vs family coverage level.
        Benefit benPlan = null;
        Benefit benPat = null;
        for (int i = 0;i < listShort.Count;i++)
        {
            //start with no category
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum != 0)
            {
                continue;
            }
             
            if (listShort[i].PatPlanNum != 0)
            {
                benPat = listShort[i];
            }
            else
            {
                benPlan = listShort[i];
            } 
        }
        //then, specific category.
        CovSpan[] spansForCat = new CovSpan[]();
        for (int i = 0;i < listShort.Count;i++)
        {
            if (listShort[i].CodeNum > 0)
            {
                continue;
            }
             
            if (listShort[i].CovCatNum == 0)
            {
                continue;
            }
             
            //see if the span matches
            spansForCat = CovSpans.GetForCat(listShort[i].CovCatNum);
            boolean isMatch = false;
            for (int j = 0;j < spansForCat.Length;j++)
            {
                if (String.Compare(procCodeStr, spansForCat[j].FromCode) >= 0 && String.Compare(procCodeStr, spansForCat[j].ToCode) <= 0)
                {
                    isMatch = true;
                    break;
                }
                 
            }
            if (!isMatch)
            {
                continue;
            }
             
            //no match
            if (listShort[i].PatPlanNum != 0)
            {
                if (benPat != null && benPat.CovCatNum != 0)
                {
                    //must compare
                    //only use the new one if the item order is larger
                    if (CovCats.GetOrderShort(listShort[i].CovCatNum) > CovCats.getOrderShort(benPat.CovCatNum))
                    {
                        benPat = listShort[i];
                    }
                     
                }
                else
                {
                    //first one encountered for a category
                    benPat = listShort[i];
                } 
            }
            else
            {
                if (benPlan != null && benPlan.CovCatNum != 0)
                {
                    //must compare
                    //only use the new one if the item order is larger
                    if (CovCats.GetOrderShort(listShort[i].CovCatNum) > CovCats.getOrderShort(benPlan.CovCatNum))
                    {
                        benPlan = listShort[i];
                    }
                     
                }
                else
                {
                    //first one encountered for a category
                    benPlan = listShort[i];
                } 
            } 
        }
        for (int i = 0;i < listShort.Count;i++)
        {
            //then, specific code
            if (listShort[i].CodeNum == 0)
            {
                continue;
            }
             
            if (!StringSupport.equals(procCodeStr, ProcedureCodes.GetStringProcCode(listShort[i].CodeNum)))
            {
                continue;
            }
             
            if (benList[i].PatPlanNum != 0)
            {
                benPat = listShort[i];
            }
            else
            {
                benPlan = listShort[i];
            } 
        }
        if (benPat != null)
        {
            return benPat.Percent;
        }
         
        if (benPlan != null)
        {
            return benPlan.Percent;
        }
         
        return 0;
    }

    /**
    * Only used from ClaimProc.ComputeBaseEst. This is a low level function to determine if a given procedure is completely excluded from coverage.  It does not consider any dates of service or history.
    */
    public static boolean isExcluded(String strProcCode, List<Benefit> benList, long planNum, long patPlanNum) throws Exception {
        for (int i = 0;i < benList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanNum)
            {
                continue;
            }
             
            if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != planNum)
            {
                continue;
            }
             
            if (benList[i].BenefitType != InsBenefitType.Exclusions)
            {
                continue;
            }
             
            if (benList[i].CodeNum > 0)
            {
                if (StringSupport.equals(strProcCode, ProcedureCodes.GetStringProcCode(benList[i].CodeNum)))
                {
                    return true;
                }
                 
                continue;
            }
             
            //specific procedure code excluded
            if (benList[i].CovCatNum == 0)
            {
                continue;
            }
             
            //General exclusion with no category.  This is considered an unsupported type of benefit.
            //Nobody should be able to exclude everything with one benefit entry.
            //see if the span matches
            CovSpan[] spansForCat = CovSpans.GetForCat(benList[i].CovCatNum);
            for (int j = 0;j < spansForCat.Length;j++)
            {
                //bool isMatch=false;
                if (String.Compare(strProcCode, spansForCat[j].FromCode) >= 0 && String.Compare(strProcCode, spansForCat[j].ToCode) <= 0)
                {
                    return true;
                }
                 
            }
        }
        return false;
    }

    //span matches
    //no exclusions found for this code
    /**
    * Used in FormInsPlan to sych database with changes user made to the benefit list for a plan.  Must supply an old list for comparison.  Only the differences are saved.
    */
    public static void updateList(List<Benefit> oldBenefitList, List<Benefit> newBenefitList) throws Exception {
        //No need to check RemotingRole; no call to db.
        Benefit newBenefit;
        for (int i = 0;i < oldBenefitList.Count;i++)
        {
            //loop through the old list
            newBenefit = null;
            for (int j = 0;j < newBenefitList.Count;j++)
            {
                if (newBenefitList[j] == null || newBenefitList[j].BenefitNum == 0)
                {
                    continue;
                }
                 
                if (oldBenefitList[i].BenefitNum == newBenefitList[j].BenefitNum)
                {
                    newBenefit = newBenefitList[j];
                    break;
                }
                 
            }
            if (newBenefit == null)
            {
                //benefit with matching benefitNum was not found, so it must have been deleted
                Delete(oldBenefitList[i]);
                continue;
            }
             
            //benefit was found with matching benefitNum, so check for changes
            if (newBenefit.PlanNum != oldBenefitList[i].PlanNum || newBenefit.PatPlanNum != oldBenefitList[i].PatPlanNum || newBenefit.CovCatNum != oldBenefitList[i].CovCatNum || newBenefit.BenefitType != oldBenefitList[i].BenefitType || newBenefit.Percent != oldBenefitList[i].Percent || newBenefit.MonetaryAmt != oldBenefitList[i].MonetaryAmt || newBenefit.TimePeriod != oldBenefitList[i].TimePeriod || newBenefit.QuantityQualifier != oldBenefitList[i].QuantityQualifier || newBenefit.Quantity != oldBenefitList[i].Quantity || newBenefit.CodeNum != oldBenefitList[i].CodeNum || newBenefit.CoverageLevel != oldBenefitList[i].CoverageLevel)
            {
                Benefits.update(newBenefit);
            }
             
        }
        for (int i = 0;i < newBenefitList.Count;i++)
        {
            //loop through the new list
            if (newBenefitList[i] == null)
            {
                continue;
            }
             
            if (((Benefit)newBenefitList[i]).BenefitNum != 0)
            {
                continue;
            }
             
            //benefit with benefitNum=0, so it's new
            Benefits.insert((Benefit)newBenefitList[i]);
        }
    }

    /*
    		///<summary>Was used in FormInsPlan when applying changes to all identical plans.  Needs to be removed. 1. Deletes any benefits where the benefitNum is not found in the new list.  2. Adds any new Benefits (BenefitNum=0) found in the new list.  It does not test to see whether any benefits with the same BenefitNum have changed, because FormInsBenefits never changes existing benefits.</summary>
    		public static void UpdateListForIdentical(List<Benefit> oldBenefitList,List<Benefit> newBenefitList,List<long> planNums) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),oldBenefitList,newBenefitList,planNums);
    				return;
    			}
    			string command;
    			Benefit newBenefit;
    			string plansInString="";//comma delimited
    			for(int p=0;p<planNums.Count;p++){
    				if(p>0){
    					plansInString+=",";
    				}
    				plansInString+=planNums[p].ToString();
    			}
    			//1. Delete any benefits where the benefitNum is not found in the new list.--------------------------------------------
    			for(int i=0;i<oldBenefitList.Count;i++) {//loop through the old list
    				newBenefit=null;
    				for(int j=0;j<newBenefitList.Count;j++) {
    					if(newBenefitList[j]==null || newBenefitList[j].BenefitNum==0) {
    						continue;
    					}
    					if(oldBenefitList[i].BenefitNum==newBenefitList[j].BenefitNum) {
    						newBenefit=newBenefitList[j];//a matching benefitNum was found in the new list
    						break;
    					}
    				}
    				if(newBenefit==null) {
    					//benefit with matching benefitNum was not found, so it must have been deleted
    					//delete all identical benefits from other plans and this plan
    					command="DELETE FROM benefit WHERE PlanNum IN("+plansInString+") "
    						+"AND CovCatNum="+POut.Long(oldBenefitList[i].CovCatNum)+" "
    						+"AND BenefitType="+POut.Int((int)oldBenefitList[i].BenefitType)+" "
    						+"AND Percent="+POut.Int(oldBenefitList[i].Percent)+" "
    						+"AND MonetaryAmt="+POut.Double(oldBenefitList[i].MonetaryAmt)+" "
    						+"AND TimePeriod="+POut.Int((int)oldBenefitList[i].TimePeriod)+" "
    						+"AND QuantityQualifier="+POut.Int((int)oldBenefitList[i].QuantityQualifier)+" "
    						+"AND Quantity="+POut.Int(oldBenefitList[i].Quantity)+" "
    						+"AND CodeNum="+POut.Long(oldBenefitList[i].CodeNum)+" "
    						+"AND CoverageLevel="+POut.Int((int)oldBenefitList[i].CoverageLevel);
    					Db.NonQ(command);
    				}
    			}
    			//2. Add any new Benefits (BenefitNum=0) found in the new list.-------------------------------------------------------
    			for(int i=0;i<newBenefitList.Count;i++) {//loop through the new list
    				if(newBenefitList[i].BenefitNum==0 && newBenefitList[i].PlanNum!=0) {//the benefit is new, and it is a plan benefit rather than a patient benefit.
    					for(int p=0;p<planNums.Count;p++){//loop through each plan
    						newBenefit=newBenefitList[i].Copy();//we need to leave the one in the list with BenefitNum=0 for testing further down.
    						newBenefit.PlanNum=planNums[p];
    						Insert(newBenefit);
    					}
    				}
    			}
    			//3. Alter any changed benefits.----------------------------------------------------------------------------------------
    			//These will only be from the Other Benefits list, because the normal benefits are changed by using a delete and insert.
    			for(int i=0;i<oldBenefitList.Count;i++) {//loop through the old list
    				newBenefit=null;
    				for(int j=0;j<newBenefitList.Count;j++) {
    					if(newBenefitList[j]==null || newBenefitList[j].BenefitNum==0) {
    						continue;
    					}
    					if(oldBenefitList[i].BenefitNum==newBenefitList[j].BenefitNum) {
    						newBenefit=newBenefitList[j];//a matching benefitNum was found in the new list
    						break;
    					}
    				}
    				if(newBenefit==null){
    					continue;//no match found
    				}
    				if(//newBenefit.PlanNum             != oldBenefitList[i].PlanNum
    					//|| newBenefit.PatPlanNum        != oldBenefitList[i].PatPlanNum
    						 newBenefit.CovCatNum         != oldBenefitList[i].CovCatNum
    					|| newBenefit.BenefitType       != oldBenefitList[i].BenefitType
    					|| newBenefit.Percent           != oldBenefitList[i].Percent
    					|| newBenefit.MonetaryAmt       != oldBenefitList[i].MonetaryAmt
    					|| newBenefit.TimePeriod        != oldBenefitList[i].TimePeriod
    					|| newBenefit.QuantityQualifier != oldBenefitList[i].QuantityQualifier
    					|| newBenefit.Quantity          != oldBenefitList[i].Quantity
    					|| newBenefit.CodeNum           != oldBenefitList[i].CodeNum 
    					|| newBenefit.CoverageLevel     != oldBenefitList[i].CoverageLevel) 
    				{
    					//changed=true;
    					//break;
    					//change the identical benefit for all other plans
    					command="UPDATE benefit SET " 
    						//+"PlanNum = '"          +POut.Long   (ben.PlanNum)+"'"
    						//+",PatPlanNum = '"      +POut.Long   (ben.PatPlanNum)+"'"
    						+"CovCatNum = '"        +POut.Long   (newBenefit.CovCatNum)+"'"
    						+",BenefitType = '"     +POut.Long   ((int)newBenefit.BenefitType)+"'"
    						+",Percent = '"         +POut.Long   (newBenefit.Percent)+"'"
    						+",MonetaryAmt = '"     +POut.Double (newBenefit.MonetaryAmt)+"'"
    						+",TimePeriod = '"      +POut.Long   ((int)newBenefit.TimePeriod)+"'"
    						+",QuantityQualifier ='"+POut.Long   ((int)newBenefit.QuantityQualifier)+"'"
    						+",Quantity = '"        +POut.Long   (newBenefit.Quantity)+"'"
    						+",CodeNum = '"         +POut.Long   (newBenefit.CodeNum)+"'"
    						+",CoverageLevel = '"   +POut.Long   ((int)newBenefit.CoverageLevel)+"' "
    						+"WHERE PlanNum IN("+plansInString+") "
    						+"AND CovCatNum="+POut.Long(oldBenefitList[i].CovCatNum)+" "
    						+"AND BenefitType="+POut.Int((int)oldBenefitList[i].BenefitType)+" "
    						+"AND Percent="+POut.Int(oldBenefitList[i].Percent)+" "
    						+"AND MonetaryAmt="+POut.Double(oldBenefitList[i].MonetaryAmt)+" "
    						+"AND TimePeriod="+POut.Int((int)oldBenefitList[i].TimePeriod)+" "
    						+"AND QuantityQualifier="+POut.Int((int)oldBenefitList[i].QuantityQualifier)+" "
    						+"AND Quantity="+POut.Int(oldBenefitList[i].Quantity)+" "
    						+"AND CodeNum="+POut.Long(oldBenefitList[i].CodeNum)+" "
    						+"AND CoverageLevel="+POut.Int((int)oldBenefitList[i].CoverageLevel);
    					Db.NonQ(command);
    				}
    			}
    			//might be a good idea to compute estimates for each plan now.
    		}*/
    /**
    * Used in family module display to get a list of benefits.  The main purpose of this function is to group similar benefits for each plan on the same row, making it easier to display in a simple grid.  Supply a list of all benefits for the patient, and the patPlans for the patient.
    */
    public static Benefit[][] getDisplayMatrix(List<Benefit> bensForPat, List<PatPlan> patPlanList, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        //each object is a Benefit[]
        Benefit[] row = new Benefit[]();
        ArrayList refAL = new ArrayList();
        //each object is a Benefit from any random column. Used when searching for a type.
        InsSub sub;
        for (int i = 0;i < bensForPat.Count;i++)
        {
            for (int j = 0;j < patPlanList.Count;j++)
            {
                //loop through columns
                sub = InsSubs.GetSub(patPlanList[j].InsSubNum, subList);
                if (patPlanList[j].PatPlanNum != bensForPat[i].PatPlanNum && sub.PlanNum != bensForPat[i].PlanNum)
                {
                    continue;
                }
                 
                //Benefit doesn't apply to this column
                //search refAL for a matching type that already exists
                row = null;
                for (int k = 0;k < refAL.Count;k++)
                {
                    if (((Benefit)refAL[k]).compareTo(bensForPat[i]) == 0)
                    {
                        //if the type is equivalent
                        row = (Benefit[])AL[k];
                        break;
                    }
                     
                }
                //if no matching type found, add a row, and use that row
                if (row == null)
                {
                    refAL.Add(bensForPat[i].Copy());
                    row = new Benefit[patPlanList.Count];
                    row[j] = bensForPat[i].Copy();
                    AL.Add(row);
                    continue;
                }
                 
                //if the column for the matching row is null, then use that row
                if (row[j] == null)
                {
                    row[j] = bensForPat[i].Copy();
                    continue;
                }
                 
                //if not null, then add another row.
                refAL.Add(bensForPat[i].Copy());
                row = new Benefit[patPlanList.Count];
                row[j] = bensForPat[i].Copy();
                AL.Add(row);
            }
        }
        IComparer myComparer = new BenefitArraySorter();
        AL.Sort(myComparer);
        Benefit[][] retVal = new Benefit[patPlanList.Count, AL.Count];
        for (int y = 0;y < AL.Count;y++)
        {
            for (int x = 0;x < patPlanList.Count;x++)
            {
                if (((Benefit[])AL[y])[x] != null)
                {
                    retVal[x, y] = ((Benefit[])AL[y])[x].Copy();
                }
                 
            }
        }
        return retVal;
    }

    /**
    * Deletes all benefits for a plan from the database.  Only used in FormInsPlan when picking a plan from the list.  Need to clear out benefits so that they won't be picked up when choosing benefits for all.
    */
    public static void deleteForPlan(long planNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), planNum);
            return ;
        }
         
        String command = "DELETE FROM benefit WHERE PlanNum=" + POut.long(planNum);
        Db.nonQ(command);
    }

    public static String getFrequencyDisplay(FrequencyType freqType, List<Benefit> benList) throws Exception {
        String retVal = "";
        for (int i = 0;i < benList.Count;i++)
        {
            switch(freqType)
            {
                case BW: 
                    //4BW
                    //&& ben.CovCatNum==CovCats.GetForEbenCat(EbenefitCategory.Db).CovCatNum//ignored
                    if (StringSupport.equals(ProcedureCodes.GetStringProcCode(benList[i].CodeNum), "D0274") && benList[i].BenefitType == InsBenefitType.Limitations && benList[i].MonetaryAmt == -1 && benList[i].PatPlanNum == 0 && benList[i].Percent == -1 && (benList[i].QuantityQualifier == BenefitQuantity.Months || benList[i].QuantityQualifier == BenefitQuantity.Years || benList[i].QuantityQualifier == BenefitQuantity.NumberOfServices))
                    {
                        //&& ben.TimePeriod might be none, or calYear, or ServiceYear, or Years.
                        if (benList[i].QuantityQualifier == BenefitQuantity.Months)
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once every month.\r\n");
                            }
                            else
                            {
                                retVal += Lans.g(null,"Once every ") + benList[i].Quantity + Lans.g(null," months.\r\n");
                            } 
                        }
                        else if (benList[i].QuantityQualifier == BenefitQuantity.Years)
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once every year.\r\n");
                            }
                            else
                            {
                                retVal += Lans.g(null,"Once every ") + benList[i].Quantity + Lans.g(null," years.\r\n");
                            } 
                        }
                        else
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once per year.\r\n");
                            }
                            else
                            {
                                retVal += benList[i].Quantity + Lans.g(null," times per year.\r\n");
                            } 
                        }  
                    }
                     
                    continue;
                case PanoFMX: 
                    //Pano
                    //&& ben.CovCatNum==CovCats.GetForEbenCat(EbenefitCategory.Db).CovCatNum//ignored
                    if (StringSupport.equals(ProcedureCodes.GetStringProcCode(benList[i].CodeNum), "D0330") && benList[i].BenefitType == InsBenefitType.Limitations && benList[i].MonetaryAmt == -1 && benList[i].PatPlanNum == 0 && benList[i].Percent == -1 && (benList[i].QuantityQualifier == BenefitQuantity.Months || benList[i].QuantityQualifier == BenefitQuantity.Years || benList[i].QuantityQualifier == BenefitQuantity.NumberOfServices))
                    {
                        //&& ben.TimePeriod might be none, or calYear, or ServiceYear, or Years.
                        if (benList[i].QuantityQualifier == BenefitQuantity.Months)
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once every month.\r\n");
                            }
                            else
                            {
                                retVal += Lans.g(null,"Once every ") + benList[i].Quantity + Lans.g(null," months.\r\n");
                            } 
                        }
                        else if (benList[i].QuantityQualifier == BenefitQuantity.Years)
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once every year.\r\n");
                            }
                            else
                            {
                                retVal += Lans.g(null,"Once every ") + benList[i].Quantity + Lans.g(null," years.\r\n");
                            } 
                        }
                        else
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once per year.\r\n");
                            }
                            else
                            {
                                retVal += benList[i].Quantity + Lans.g(null," times per year.\r\n");
                            } 
                        }  
                    }
                     
                    continue;
                case Exam: 
                    if (benList[i].CodeNum == 0 && benList[i].BenefitType == InsBenefitType.Limitations && CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive) != null && benList[i].CovCatNum == CovCats.getForEbenCat(EbenefitCategory.RoutinePreventive).CovCatNum && benList[i].MonetaryAmt == -1 && benList[i].PatPlanNum == 0 && benList[i].Percent == -1 && (benList[i].QuantityQualifier == BenefitQuantity.Months || benList[i].QuantityQualifier == BenefitQuantity.Years || benList[i].QuantityQualifier == BenefitQuantity.NumberOfServices))
                    {
                        //&& ben.TimePeriod might be none, or calYear, or ServiceYear, or Years.
                        if (benList[i].QuantityQualifier == BenefitQuantity.Months)
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once every month.\r\n");
                            }
                            else
                            {
                                retVal += Lans.g(null,"Once every ") + benList[i].Quantity + Lans.g(null," months.\r\n");
                            } 
                        }
                        else if (benList[i].QuantityQualifier == BenefitQuantity.Years)
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once every year.\r\n");
                            }
                            else
                            {
                                retVal += Lans.g(null,"Once every ") + benList[i].Quantity + Lans.g(null," years.\r\n");
                            } 
                        }
                        else
                        {
                            if (benList[i].Quantity == 1)
                            {
                                retVal += Lans.g(null,"Once per year.\r\n");
                            }
                            else
                            {
                                retVal += benList[i].Quantity + Lans.g(null," times per year.\r\n");
                            } 
                        }  
                    }
                     
                    continue;
            
            }
        }
        return retVal;
    }

}


//end switch
//end i loop