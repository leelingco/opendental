//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Benefit;
import OpenDentBusiness.BenefitCoverageLevel;
import OpenDentBusiness.BenefitLogic;
import OpenDentBusiness.Benefits;
import OpenDentBusiness.BenefitTimePeriod;
import OpenDentBusiness.ClaimProc;
import OpenDentBusiness.ClaimProcHist;
import OpenDentBusiness.ClaimProcs;
import OpenDentBusiness.Db;
import OpenDentBusiness.EnumCobRule;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ClaimProcs   
{
    /**
    * 
    */
    public static List<ClaimProc> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimProc>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * from claimproc " + "WHERE PatNum = '" + patNum.ToString() + "' ORDER BY LineNumber";
        return Crud.ClaimProcCrud.SelectMany(command);
    }

    /**
    * When using family deduct or max, this gets all claimprocs for the given plan.  This info is needed to compute used and pending insurance.
    */
    public static List<ClaimProc> refreshFam(long planNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimProc>>GetObject(MethodBase.GetCurrentMethod(), planNum);
        }
         
        String command = "SELECT * FROM claimproc " + "WHERE PlanNum = " + POut.long(planNum);
        return Crud.ClaimProcCrud.SelectMany(command);
    }

    //+" OR PatPlanNum = "+POut.PInt(patPlanNum);
    /**
    * Gets a list of ClaimProcs for one claim.
    */
    public static List<ClaimProc> refreshForClaim(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimProc>>GetObject(MethodBase.GetCurrentMethod(), claimNum);
        }
         
        String command = "SELECT * FROM claimproc " + "WHERE ClaimNum = " + POut.long(claimNum) + " ORDER BY LineNumber";
        return Crud.ClaimProcCrud.SelectMany(command);
    }

    /**
    * Gets a list of ClaimProcs with status of estimate.
    */
    public static List<ClaimProc> refreshForTP(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimProc>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM claimproc " + "WHERE (Status=" + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.Estimate).ordinal()) + " OR Status=" + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.CapEstimate).ordinal()) + ") " + "AND PatNum = " + POut.long(patNum);
        return Crud.ClaimProcCrud.SelectMany(command);
    }

    /**
    * Gets a list of ClaimProcs for one proc.
    */
    public static List<ClaimProc> refreshForProc(long procNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimProc>>GetObject(MethodBase.GetCurrentMethod(), procNum);
        }
         
        String command = "SELECT * FROM claimproc " + "WHERE ProcNum=" + POut.long(procNum);
        return Crud.ClaimProcCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(ClaimProc cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cp.ClaimProcNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cp);
            return cp.ClaimProcNum;
        }
         
        return Crud.ClaimProcCrud.Insert(cp);
    }

    /**
    * 
    */
    public static void update(ClaimProc cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cp);
            return ;
        }
         
        Crud.ClaimProcCrud.Update(cp);
    }

    /**
    * 
    */
    public static void delete(ClaimProc cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cp);
            return ;
        }
         
        String command = "DELETE FROM claimproc WHERE ClaimProcNum = " + POut.long(cp.ClaimProcNum);
        Db.nonQ(command);
    }

    /**
    * Surround with try/catch.  If there are any dependencies, then this will throw an exception.  This is currently only called from FormClaimProc.
    */
    public static void deleteAfterValidating(ClaimProc cp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cp);
            return ;
        }
         
        String command = new String();
        //Validate: make sure this is not the last claimproc on the claim.  If cp is not attached to a claim no need to validate.
        if (cp.ClaimNum != 0)
        {
            command = "SELECT COUNT(*) FROM claimproc WHERE ClaimNum= " + POut.long(cp.ClaimNum) + " AND ClaimProcNum!= " + POut.long(cp.ClaimProcNum);
            long remainingCP = PIn.long(Db.getCount(command));
            if (remainingCP == 0)
            {
                throw new ApplicationException(Lans.g("ClaimProcs","Not allowed to delete the last procedure from a claim.  The entire claim would have to be deleted."));
            }
             
        }
         
        //end of validation
        command = "DELETE FROM claimproc WHERE ClaimProcNum = " + POut.long(cp.ClaimProcNum);
        Db.nonQ(command);
    }

    /**
    * Used when creating a claim to create any missing claimProcs. Also used in FormProcEdit if click button to add Estimate.  Inserts it into db. It will still be altered after this to fill in the fields that actually attach it to the claim.
    */
    public static void createEst(ClaimProc cp, Procedure proc, InsPlan plan, InsSub sub) throws Exception {
        //No need to check RemotingRole; no call to db.
        cp.ProcNum = proc.ProcNum;
        //claimnum
        cp.PatNum = proc.PatNum;
        cp.ProvNum = proc.ProvNum;
        if (StringSupport.equals(plan.PlanType, "c"))
        {
            //capitation
            if (proc.ProcStatus == OpenDentBusiness.ProcStat.C)
            {
                //complete
                cp.Status = OpenDentBusiness.ClaimProcStatus.CapComplete;
            }
            else
            {
                //in this case, a copy will be made later.
                //usually TP status
                cp.Status = OpenDentBusiness.ClaimProcStatus.CapEstimate;
            } 
        }
        else
        {
            cp.Status = OpenDentBusiness.ClaimProcStatus.Estimate;
        } 
        cp.PlanNum = plan.PlanNum;
        cp.InsSubNum = sub.InsSubNum;
        cp.DateCP = proc.ProcDate;
        //Writeoff=0
        cp.AllowedOverride = -1;
        cp.Percentage = -1;
        cp.PercentOverride = -1;
        cp.CopayAmt = -1;
        cp.NoBillIns = false;
        cp.PaidOtherIns = -1;
        cp.BaseEst = 0;
        cp.DedEst = -1;
        cp.DedEstOverride = -1;
        cp.InsEstTotal = 0;
        cp.InsEstTotalOverride = -1;
        cp.CopayOverride = -1;
        cp.PaidOtherInsOverride = -1;
        cp.ProcDate = proc.ProcDate;
        cp.WriteOffEst = -1;
        cp.WriteOffEstOverride = -1;
        cp.ClinicNum = proc.ClinicNum;
        insert(cp);
    }

    /**
    * This compares the two lists and saves all the changes to the database.  It also removes all the items marked doDelete.
    */
    public static void synch(RefSupport<List<ClaimProc>> ClaimProcList, List<ClaimProc> claimProcListOld) throws Exception {
        for (int i = 0;i < ClaimProcList.getValue().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (ClaimProcList.getValue()[i].DoDelete)
            {
                ClaimProcs.Delete(ClaimProcList.getValue()[i]);
                continue;
            }
             
            //new procs
            if (i >= claimProcListOld.Count)
            {
                ClaimProcs.Insert(ClaimProcList.getValue()[i]);
                continue;
            }
             
            //this should properly update the ClaimProcNum
            //changed procs
            if (!ClaimProcList.getValue()[i].Equals(claimProcListOld[i]))
            {
                ClaimProcs.Update(ClaimProcList.getValue()[i]);
            }
             
        }
        for (int i = ClaimProcList.getValue().Count - 1;i >= 0;i--)
        {
            //go backwards to actually remove the deleted items.
            if (ClaimProcList.getValue()[i].DoDelete)
            {
                ClaimProcList.getValue().RemoveAt(i);
            }
             
        }
    }

    /**
    * When sending or printing a claim, this converts the supplied list into a list of ClaimProcs that need to be sent.
    */
    public static List<ClaimProc> getForSendClaim(List<ClaimProc> claimProcList, long claimNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //MessageBox.Show(List.Length.ToString());
        List<ClaimProc> retVal = new List<ClaimProc>();
        boolean includeThis = new boolean();
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ClaimNum != claimNum)
            {
                continue;
            }
             
            if (claimProcList[i].ProcNum == 0)
            {
                continue;
            }
             
            //skip payments
            includeThis = true;
            for (int j = 0;j < retVal.Count;j++)
            {
                //loop through existing claimprocs
                if (retVal[j].ProcNum == claimProcList[i].ProcNum)
                {
                    includeThis = false;
                }
                 
            }
            //skip duplicate procedures
            if (includeThis)
            {
                retVal.Add(claimProcList[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Gets all ClaimProcs for the current Procedure. The List must be all ClaimProcs for this patient.
    */
    public static List<ClaimProc> getForProc(List<ClaimProc> claimProcList, long procNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //MessageBox.Show(List.Length.ToString());
        //ArrayList ALForProc=new ArrayList();
        List<ClaimProc> retVal = new List<ClaimProc>();
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ProcNum == procNum)
            {
                retVal.Add(claimProcList[i]);
            }
             
        }
        return retVal;
    }

    //need to sort by pri, sec, etc.  BUT,
    //the only way to do it would be to add an ordinal field to claimprocs or something similar.
    //Then a sorter could be built.  Otherwise, we don't know which order to put them in.
    //Maybe supply PatPlanList to this function, because it's ordered.
    //But, then if patient changes ins, it will 'forget' which is pri and which is sec.
    //ClaimProc[] ForProc=new ClaimProc[ALForProc.Count];
    //for(int i=0;i<ALForProc.Count;i++){
    //	ForProc[i]=(ClaimProc)ALForProc[i];
    //}
    //return ForProc;
    /**
    * Used in TP module to get one estimate. The List must be all ClaimProcs for this patient. If estimate can't be found, then return null.  The procedure is always status TP, so there shouldn't be more than one estimate for one plan.
    */
    public static ClaimProc getEstimate(List<ClaimProc> claimProcList, long procNum, long planNum, long subNum) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.Preauth)
            {
                continue;
            }
             
            if (claimProcList[i].ProcNum == procNum && claimProcList[i].PlanNum == planNum && claimProcList[i].InsSubNum == subNum)
            {
                return claimProcList[i];
            }
             
        }
        return null;
    }

    /**
    * Used once in Account.  The insurance estimate based on all claimprocs with this procNum that are attached to claims. Includes status of NotReceived,Received, and Supplemental. The list can be all ClaimProcs for patient, or just those for this procedure.
    */
    public static String procDisplayInsEst(ClaimProc[] List, long procNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < List.Length;i++)
        {
            //adj ignored
            //capClaim has no insEst yet
            if (List[i].ProcNum == procNum && (List[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived || List[i].Status == OpenDentBusiness.ClaimProcStatus.Received || List[i].Status == OpenDentBusiness.ClaimProcStatus.Supplemental))
            {
                retVal += List[i].InsPayEst;
            }
             
        }
        return retVal.ToString("F");
    }

    /**
    * Used in Account and in PaySplitEdit. The insurance estimate based on all claimprocs with this procNum, but only for those claimprocs that are not received yet. The list can be all ClaimProcs for patient, or just those for this procedure.
    */
    public static double procEstNotReceived(List<ClaimProc> claimProcList, long procNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ProcNum == procNum && claimProcList[i].Status == OpenDentBusiness.ClaimProcStatus.NotReceived)
            {
                retVal += claimProcList[i].InsPayEst;
            }
             
        }
        return retVal;
    }

    /**
    * Used in PaySplitEdit. The insurance amount paid based on all claimprocs with this procNum. The list can be all ClaimProcs for patient, or just those for this procedure.
    */
    public static double procInsPay(List<ClaimProc> claimProcList, long procNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //&& List[i].InsPayAmt > 0//ins paid
            if (claimProcList[i].ProcNum == procNum && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapEstimate && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapComplete && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                retVal += claimProcList[i].InsPayAmt;
            }
             
        }
        return retVal;
    }

    /**
    * Used in PaySplitEdit. The insurance writeoff based on all claimprocs with this procNum. The list can be all ClaimProcs for patient, or just those for this procedure.
    */
    public static double procWriteoff(List<ClaimProc> claimProcList, long procNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //&& List[i].InsPayAmt > 0//ins paid
            if (claimProcList[i].ProcNum == procNum && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapEstimate && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapComplete && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                retVal += claimProcList[i].WriteOff;
            }
             
        }
        return retVal;
    }

    /**
    * Used in E-claims to get the amount paid by primary. The insurance amount paid by other subNums based on all claimprocs with this procNum. The list can be all ClaimProcs for patient, or just those for this procedure.
    */
    public static double procInsPayPri(List<ClaimProc> claimProcList, long procNum, long subNumExclude) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ProcNum == procNum && claimProcList[i].InsSubNum != subNumExclude && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapEstimate && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapComplete && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                retVal += claimProcList[i].InsPayAmt;
            }
             
        }
        return retVal;
    }

    public static boolean isValidClaimAdj(ClaimProc claimProc, long procNum, long subNumExclude) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (claimProc.ProcNum != procNum)
        {
            return false;
        }
         
        if (claimProc.InsSubNum == subNumExclude)
        {
            return false;
        }
         
        //|| claimProc.Status==ClaimProcStatus.NotReceived //7/9/2013 Was causing paid amounts to show on primary claims when the patient had secondary insurance, because this is the starting status of secondary claimprocs when the New Claim button is pressed.
        if (claimProc.Status == OpenDentBusiness.ClaimProcStatus.CapClaim || claimProc.Status == OpenDentBusiness.ClaimProcStatus.Received || claimProc.Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
        {
            return true;
        }
        else
        {
            return false;
        } 
    }

    //Adjustment never attached to proc. Preauth, CapEstimate, CapComplete, and Estimate never paid.
    /**
    * Used in E-claims to get the most recent date paid (by primary?). The insurance amount paid by the planNum based on all claimprocs with this procNum. The list can be all ClaimProcs for patient, or just those for this procedure.
    */
    public static DateTime getDatePaid(List<ClaimProc> claimProcList, long procNum, long planNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        DateTime retVal = DateTime.MinValue;
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ProcNum == procNum && claimProcList[i].PlanNum == planNum && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapEstimate && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.CapComplete && claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                if (claimProcList[i].DateCP > retVal)
                {
                    retVal = claimProcList[i].DateCP;
                }
                 
            }
             
        }
        return retVal;
    }

    /**
    * Used once in Account on the Claim line.  The amount paid on a claim only by total, not including by procedure.  The list can be all ClaimProcs for patient, or just those for this claim.
    */
    public static double claimByTotalOnly(ClaimProc[] List, long claimNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ClaimNum == claimNum && List[i].ProcNum == 0 && List[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth)
            {
                retVal += List[i].InsPayAmt;
            }
             
        }
        return retVal;
    }

    /**
    * Used once in Account on the Claim line.  The writeoff amount on a claim only by total, not including by procedure.  The list can be all ClaimProcs for patient, or just those for this claim.
    */
    public static double claimWriteoffByTotalOnly(ClaimProc[] List, long claimNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ClaimNum == claimNum && List[i].ProcNum == 0 && List[i].Status != OpenDentBusiness.ClaimProcStatus.Preauth)
            {
                retVal += List[i].WriteOff;
            }
             
        }
        return retVal;
    }

    /**
    * Attaches or detaches claimprocs from the specified claimPayment. Updates all claimprocs on a claim with one query.  It also updates their DateCP's to match the claimpayment date.
    */
    public static void setForClaimOld(long claimNum, long claimPaymentNum, DateTime date, boolean setAttached) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimNum, claimPaymentNum, date, setAttached);
            return ;
        }
         
        String command = "UPDATE claimproc SET ClaimPaymentNum = ";
        if (setAttached)
        {
            command += "" + POut.long(claimPaymentNum) + " ";
        }
        else
        {
            command += "0 ";
        } 
        command += ",DateCP=" + POut.date(date) + " " + "WHERE ClaimNum=" + POut.long(claimNum) + " AND " + "InsPayAmt!=0 AND (" + "ClaimPaymentNum=" + POut.long(claimPaymentNum) + " OR ClaimPaymentNum=0)";
        //MessageBox.Show(string command);
        Db.nonQ(command);
    }

    /**
    * Attaches claimprocs to the specified claimPayment. Updates all claimprocs on a claim with one query.  It also updates their DateCP's to match the claimpayment date.
    */
    public static void attachToPayment(long claimNum, long claimPaymentNum, DateTime date, int paymentRow) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimNum, claimPaymentNum, date, paymentRow);
            return ;
        }
         
        String command = "UPDATE claimproc SET ClaimPaymentNum=" + POut.long(claimPaymentNum) + ", " + "DateCP=" + POut.date(date) + ", " + "PaymentRow=" + POut.int(paymentRow) + " " + "WHERE ClaimNum=" + POut.long(claimNum) + " " + "AND InsPayAmt!=0 " + "AND ClaimPaymentNum=0";
        Db.nonQ(command);
    }

    /**
    * Detaches claimprocs from the specified claimPayment. Updates all claimprocs on a claim with one query.
    */
    public static void detachFromPayment(long claimNum, long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimNum, claimPaymentNum);
            return ;
        }
         
        //+"DateCP="+POut.Date(DateTime.MinValue)+", "
        String command = "UPDATE claimproc SET ClaimPaymentNum=0, " + "PaymentRow=0 " + "WHERE ClaimNum=" + POut.long(claimNum) + " " + "AND ClaimPaymentNum=" + POut.long(claimPaymentNum);
        Db.nonQ(command);
    }

    /**
    * Synchs all claimproc DateCP's attached to the claim payment.  Used when an insurance check's date is changed.
    */
    public static void synchDateCP(long claimPaymentNum, DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimPaymentNum, date);
            return ;
        }
         
        String command = "UPDATE claimproc SET " + "DateCP=" + POut.date(date) + " " + "WHERE ClaimPaymentNum=" + POut.long(claimPaymentNum);
        Db.nonQ(command);
    }

    /*
    		///<summary>Detaches claimprocs from the specified claimPayment. Updates all claimprocs on a claim with one query.  Sets DateCP to min and InsPayAmt to 0.</summary>
    		public static void DettachClaimPayment(long claimNum,long claimPaymentNum) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),claimNum,claimPaymentNum);
    				return;
    			}
    			string command="UPDATE claimproc "
    				+"SET ClaimPaymentNum = 0,"
    				+"DateCP = "+POut.Date(DateTime.MinValue)+","
    				+"InsPayAmt = 0, "
    				+"Status = "+POut.Int((int)ClaimProcStatus.NotReceived)+" "
    				+"WHERE ClaimNum="+POut.Long(claimNum)+" "
    				+"AND ClaimPaymentNum="+POut.Long(claimPaymentNum);
     			Db.NonQ(command);
    			command="UPDATE claim "
    				+"SET ClaimStatus = 'S' "
    				+"WHERE ClaimNum="+POut.Long(claimNum);
    			Db.NonQ(command);
    		}*/
    /*
    		///<summary></summary>
    		public static double ComputeBal(ClaimProc[] List){
    			//No need to check RemotingRole; no call to db.
    			double retVal=0;
    			//double pat;
    			for(int i=0;i<List.Length;i++){
    				if(List[i].Status==ClaimProcStatus.Adjustment//ins adjustments do not affect patient balance
    					|| List[i].Status==ClaimProcStatus.Preauth//preauthorizations do not affect patient balance
    					|| List[i].Status==ClaimProcStatus.Estimate//estimates do not affect patient balance
    					|| List[i].Status==ClaimProcStatus.CapEstimate//CapEstimates do not affect patient balance
    					){
    					continue;
    				}
    				if(List[i].Status==ClaimProcStatus.Received
    					|| List[i].Status==ClaimProcStatus.Supplemental//because supplemental are always received
    					|| List[i].Status==ClaimProcStatus.CapClaim)//would only have a payamt if received
    				{
    					retVal-=List[i].InsPayAmt;
    					retVal-=List[i].WriteOff;
    				}
    				else if(List[i].Status==ClaimProcStatus.NotReceived) {
    					if(!PrefC.GetBool(PrefName.BalancesDontSubtractIns")) {//this typically happens
    						retVal-=List[i].InsPayEst;
    						retVal-=List[i].WriteOff;
    					}
    				}
    			}
    			return retVal;
    		}*/
    /**
    * After entering estimates from a preauth, this routine is called for each proc to override the ins est.
    */
    public static void setInsEstTotalOverride(long procNum, long planNum, double insPayEst, List<ClaimProc> claimProcList) throws Exception {
        for (int i = 0;i < claimProcList.Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (procNum != claimProcList[i].ProcNum)
            {
                continue;
            }
             
            if (planNum != claimProcList[i].PlanNum)
            {
                continue;
            }
             
            if (claimProcList[i].Status != OpenDentBusiness.ClaimProcStatus.Estimate)
            {
                continue;
            }
             
            claimProcList[i].InsEstTotalOverride = insPayEst;
            Update(claimProcList[i]);
        }
    }

    /**
    * Calculates the Base estimate, InsEstTotal, and all the other insurance numbers for a single claimproc.  This is is not done on the fly.  Use Procedure.GetEst to later retrieve the estimate. This function replaces all of the upper estimating logic that was within FormClaimProc.  BaseEst=((fee or allowedOverride)-Copay) x (percentage or percentOverride).  The calling class must have already created the claimProc, and this function simply updates the BaseEst field of that claimproc. pst.Tot not used.  For Estimate and CapEstimate, all the estimate fields will be recalculated except the overrides.  histList and loopList can be null.  If so, then deductible and annual max will not be recalculated.  histList and loopList may only make sense in TP module and claimEdit.  loopList contains all claimprocs in the current list (TP or claim) that come before this procedure.  PaidOtherInsTot should only contain sum of InsEstTotal/Override, or paid, depending on the status.  PaidOtherInsBase also includes actual payments.
    */
    public static void computeBaseEst(ClaimProc cp, double procFee, String toothNum, long codeNum, InsPlan plan, long patPlanNum, List<Benefit> benList, List<ClaimProcHist> histList, List<ClaimProcHist> loopList, List<PatPlan> patPlanList, double paidOtherInsTot, double paidOtherInsBase, int patientAge, double writeOffOtherIns) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.CapClaim || cp.Status == OpenDentBusiness.ClaimProcStatus.CapComplete || cp.Status == OpenDentBusiness.ClaimProcStatus.Preauth || cp.Status == OpenDentBusiness.ClaimProcStatus.Supplemental)
        {
            return ;
        }
         
        //never compute estimates for those types listed above.
        //if capitation plan
        if (StringSupport.equals(plan.PlanType, "c") && cp.Status == OpenDentBusiness.ClaimProcStatus.Estimate)
        {
            //and ordinary estimate
            cp.Status = OpenDentBusiness.ClaimProcStatus.CapEstimate;
        }
         
        //if not capitation plan
        if (!StringSupport.equals(plan.PlanType, "c") && cp.Status == OpenDentBusiness.ClaimProcStatus.CapEstimate)
        {
            //and estimate is a capitation estimate
            cp.Status = OpenDentBusiness.ClaimProcStatus.Estimate;
        }
         
        //NoBillIns is only calculated when creating the claimproc, even if resetAll is true.
        //If user then changes a procCode, it does not cause an update of all procedures with that code.
        if (cp.NoBillIns)
        {
            cp.AllowedOverride = -1;
            cp.CopayAmt = 0;
            cp.CopayOverride = -1;
            cp.Percentage = -1;
            cp.PercentOverride = -1;
            cp.DedEst = -1;
            cp.DedEstOverride = -1;
            cp.PaidOtherIns = -1;
            cp.BaseEst = 0;
            cp.InsEstTotal = 0;
            cp.InsEstTotalOverride = -1;
            cp.WriteOff = 0;
            cp.PaidOtherInsOverride = -1;
            cp.WriteOffEst = -1;
            cp.WriteOffEstOverride = -1;
            return ;
        }
         
        cp.EstimateNote = "";
        //This function is called every time a ProcFee is changed,
        //so the BaseEst does reflect the new ProcFee.
        //ProcFee----------------------------------------------------------------------------------------------
        cp.BaseEst = procFee;
        cp.InsEstTotal = procFee;
        //Allowed----------------------------------------------------------------------------------------------
        double allowed = procFee;
        //could be fee, or could be a little less.  Used further down in paidOtherIns.
        if (cp.AllowedOverride != -1)
        {
            if (cp.AllowedOverride > procFee)
            {
                cp.AllowedOverride = procFee;
            }
             
            allowed = cp.AllowedOverride;
            cp.BaseEst = cp.AllowedOverride;
            cp.InsEstTotal = cp.AllowedOverride;
        }
        else if (StringSupport.equals(plan.PlanType, "c"))
        {
            //capitation estimate.  No allowed fee sched.  No substitute codes.
            allowed = procFee;
            cp.BaseEst = procFee;
            cp.InsEstTotal = procFee;
        }
        else
        {
            //no point in wasting time calculating this unless it's needed.
            double carrierAllowed = InsPlans.getAllowed(ProcedureCodes.getProcCode(codeNum).ProcCode,plan.FeeSched,plan.AllowedFeeSched,plan.CodeSubstNone,plan.PlanType,toothNum,cp.ProvNum);
            if (carrierAllowed != -1)
            {
                if (carrierAllowed > procFee)
                {
                    allowed = procFee;
                    cp.BaseEst = procFee;
                    cp.InsEstTotal = procFee;
                }
                else
                {
                    allowed = carrierAllowed;
                    cp.BaseEst = carrierAllowed;
                    cp.InsEstTotal = carrierAllowed;
                } 
            }
             
        }  
        //Copay----------------------------------------------------------------------------------------------
        cp.CopayAmt = InsPlans.getCopay(codeNum,plan.FeeSched,plan.CopayFeeSched,plan.CodeSubstNone,toothNum);
        if (cp.CopayAmt > allowed)
        {
            //if the copay is greater than the allowed fee calculated above
            cp.CopayAmt = allowed;
        }
         
        //reduce the copay
        if (cp.CopayOverride > allowed)
        {
            //or if the copay override is greater than the allowed fee calculated above
            cp.CopayOverride = allowed;
        }
         
        //reduce the override
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.CapEstimate)
        {
            //this does automate the Writeoff. If user does not want writeoff automated,
            //then they will have to complete the procedure first. (very rare)
            if (cp.CopayAmt == -1)
            {
                cp.CopayAmt = 0;
            }
             
            if (cp.CopayOverride != -1)
            {
                //override the copay
                cp.WriteOffEst = cp.BaseEst - cp.CopayOverride;
            }
            else if (cp.CopayAmt != -1)
            {
                //use the calculated copay
                cp.WriteOffEst = cp.BaseEst - cp.CopayAmt;
            }
              
            if (cp.WriteOffEst < 0)
            {
                cp.WriteOffEst = 0;
            }
             
            cp.WriteOff = cp.WriteOffEst;
            cp.DedApplied = 0;
            cp.DedEst = 0;
            cp.Percentage = -1;
            cp.PercentOverride = -1;
            cp.BaseEst = 0;
            cp.InsEstTotal = 0;
            return ;
        }
         
        if (cp.CopayOverride != -1)
        {
            //subtract copay if override
            cp.BaseEst -= cp.CopayOverride;
            cp.InsEstTotal -= cp.CopayOverride;
        }
        else if (cp.CopayAmt != -1)
        {
            //otherwise subtract calculated copay
            cp.BaseEst -= cp.CopayAmt;
            cp.InsEstTotal -= cp.CopayAmt;
        }
          
        //Deductible----------------------------------------------------------------------------------------
        //The code below handles partial usage of available deductible.
        DateTime procDate = new DateTime();
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.Estimate)
        {
            procDate = DateTime.Today;
        }
        else
        {
            procDate = cp.ProcDate;
        } 
        if (loopList != null && histList != null)
        {
            cp.DedEst = Benefits.GetDeductibleByCode(benList, plan.PlanNum, patPlanNum, procDate, ProcedureCodes.getStringProcCode(codeNum), histList, loopList, plan, cp.PatNum);
        }
         
        if (Benefits.GetPercent(ProcedureCodes.getProcCode(codeNum).ProcCode, plan.PlanType, plan.PlanNum, patPlanNum, benList) == 0)
        {
            //this is binary
            cp.DedEst = 0;
        }
         
        //Procedure is not covered. Do not apply deductible. This does not take into account percent override.
        if (cp.DedEst > cp.InsEstTotal)
        {
            //if the deductible is more than the fee
            cp.DedEst = cp.InsEstTotal;
        }
         
        //reduce the deductible
        if (cp.DedEstOverride > cp.InsEstTotal)
        {
            //if the deductible override is more than the fee
            cp.DedEstOverride = cp.InsEstTotal;
        }
         
        //reduce the override.
        if (cp.DedEstOverride != -1)
        {
            //use the override
            cp.InsEstTotal -= cp.DedEstOverride;
        }
        else //subtract
        if (cp.DedEst != -1)
        {
            //use the calculated deductible
            cp.InsEstTotal -= cp.DedEst;
        }
          
        //Percentage----------------------------------------------------------------------------------------
        cp.Percentage = Benefits.GetPercent(ProcedureCodes.getProcCode(codeNum).ProcCode, plan.PlanType, plan.PlanNum, patPlanNum, benList);
        //will never =-1
        if (cp.PercentOverride != -1)
        {
            //override, so use PercentOverride
            cp.BaseEst = cp.BaseEst * (double)cp.PercentOverride / 100d;
            cp.InsEstTotal = cp.InsEstTotal * (double)cp.PercentOverride / 100d;
        }
        else if (cp.Percentage != -1)
        {
            //use calculated Percentage
            cp.BaseEst = cp.BaseEst * (double)cp.Percentage / 100d;
            cp.InsEstTotal = cp.InsEstTotal * (double)cp.Percentage / 100d;
        }
          
        //PaidOtherIns----------------------------------------------------------------------------------------
        //double paidOtherInsActual=GetPaidOtherIns(cp,patPlanList,patPlanNum,histList);//can return -1 for primary
        PatPlan pp = PatPlans.GetFromList(patPlanList.ToArray(), patPlanNum);
        //if -1, that indicates primary ins, not a proc, or no histlist.  We should not alter it in this case.
        //if(paidOtherInsActual!=-1) {
        //An older restriction was that histList must not be null.  But since this is now straight from db, that's not restriction.
        if (pp == null)
        {
        }
        else //corruption.  Do nothing.
        if (pp.Ordinal == 1 || cp.ProcNum == 0)
        {
            cp.PaidOtherIns = 0;
        }
        else
        {
            //if secondary or greater
            //The normal calculation uses the InsEstTotal from the primary ins.
            //But in TP module, if not using max and deduct, then the amount estimated to be paid by primary will be different.
            //It will use the primary BaseEst instead of the primary InsEstTotal.
            //Since the only use of BaseEst here is to handle this alternate viewing in the TP,
            //the secondary BaseEst should use the primary BaseEst when calculating paidOtherIns.
            //The BaseEst will, however, use PaidOtherInsOverride, if user has entered one.
            //This calculation doesn't need to be accurate unless viewing TP,
            //so it's ok to pass in a dummy value, like paidOtherInsTotal.
            //We do InsEstTotal first
            //cp.PaidOtherIns=paidOtherInsActual+paidOtherInsEstTotal;
            cp.PaidOtherIns = paidOtherInsTot;
            double paidOtherInsTotTemp = cp.PaidOtherIns;
            if (cp.PaidOtherInsOverride != -1)
            {
                //use the override
                paidOtherInsTotTemp = cp.PaidOtherInsOverride;
            }
             
            //example: Fee:200, InsEstT:80, BaseEst:100, PaidOI:110.
            //So... MaxPtP:90.
            //Since InsEstT is not greater than MaxPtoP, no change.
            //Since BaseEst is greater than MaxPtoP, BaseEst changed to 90.
            if (paidOtherInsTotTemp != -1)
            {
                double maxPossibleToPay = 0;
                if (plan.CobRule == EnumCobRule.Basic)
                {
                    maxPossibleToPay = allowed - paidOtherInsTotTemp;
                }
                else if (plan.CobRule == EnumCobRule.Standard)
                {
                    double patPortionTot = procFee - paidOtherInsTotTemp - writeOffOtherIns;
                    //patPortion for InsEstTotal
                    maxPossibleToPay = Math.Min(cp.BaseEst, patPortionTot);
                }
                else
                {
                    //The lesser of what insurance would pay if they were primary, and the patient portion.
                    //plan.CobRule==EnumCobRule.CarveOut
                    maxPossibleToPay = cp.BaseEst - paidOtherInsTotTemp;
                }  
                if (maxPossibleToPay < 0)
                {
                    maxPossibleToPay = 0;
                }
                 
                if (cp.InsEstTotal > maxPossibleToPay)
                {
                    cp.InsEstTotal = maxPossibleToPay;
                }
                 
            }
             
            //reduce the estimate
            //Then, we do BaseEst
            double paidOtherInsBaseTemp = paidOtherInsBase;
            //paidOtherInsActual+paidOtherInsBaseEst;
            if (cp.PaidOtherInsOverride != -1)
            {
                //use the override
                paidOtherInsBaseTemp = cp.PaidOtherInsOverride;
            }
             
            if (paidOtherInsBaseTemp != -1)
            {
                double maxPossibleToPay = 0;
                if (plan.CobRule == EnumCobRule.Basic)
                {
                    maxPossibleToPay = allowed - paidOtherInsBaseTemp;
                }
                else if (plan.CobRule == EnumCobRule.Standard)
                {
                    double patPortionBase = procFee - paidOtherInsBaseTemp - writeOffOtherIns;
                    //patPortion for BaseEst
                    maxPossibleToPay = Math.Min(cp.BaseEst, patPortionBase);
                }
                else
                {
                    //plan.CobRule==EnumCobRule.CarveOut
                    maxPossibleToPay = cp.BaseEst - paidOtherInsBaseTemp;
                }  
                if (maxPossibleToPay < 0)
                {
                    maxPossibleToPay = 0;
                }
                 
                if (cp.BaseEst > maxPossibleToPay)
                {
                    cp.BaseEst = maxPossibleToPay;
                }
                 
            }
             
        }  
        //reduce the base est
        //Exclusions---------------------------------------------------------------------------------------
        //We are not going to consider date of proc.  Just simple exclusions
        if (Benefits.IsExcluded(ProcedureCodes.getStringProcCode(codeNum), benList, plan.PlanNum, patPlanNum))
        {
            cp.BaseEst = 0;
            cp.InsEstTotal = 0;
            if (!StringSupport.equals(cp.EstimateNote, ""))
            {
                cp.EstimateNote += ", ";
            }
             
            cp.EstimateNote += Lans.g("ClaimProcs","Exclusion");
        }
         
        //base estimate is now done and will not be altered further.  From here out, we are only altering insEstTotal
        //annual max and other limitations--------------------------------------------------------------------------------
        if (loopList != null && histList != null)
        {
            String note = "";
            RefSupport<String> refVar___0 = new RefSupport<String>();
            cp.InsEstTotal = Benefits.GetLimitationByCode(benList, plan.PlanNum, patPlanNum, procDate, ProcedureCodes.getStringProcCode(codeNum), histList, loopList, plan, cp.PatNum, refVar___0, cp.InsEstTotal, patientAge, cp.InsSubNum);
            note = refVar___0.getValue();
            if (!StringSupport.equals(note, ""))
            {
                if (!StringSupport.equals(cp.EstimateNote, ""))
                {
                    cp.EstimateNote += ", ";
                }
                 
                cp.EstimateNote += note;
            }
             
        }
         
        //procDate;//was already calculated in the deductible section.
        //Writeoff Estimate------------------------------------------------------------------------------------------
        if (StringSupport.equals(plan.PlanType, "p"))
        {
            //PPO
            //we can't use the allowed previously calculated, because it might be the allowed of a substituted code.
            //so we will calculate the allowed all over again, but this time, without using a substitution code.
            //AllowedFeeSched and toothNum do not need to be passed in.  codeSubstNone is set to true to not subst.
            double carrierAllowedNoSubst = InsPlans.GetAllowed(ProcedureCodes.getProcCode(codeNum).ProcCode, plan.FeeSched, 0, true, "p", "", cp.ProvNum);
            double allowedNoSubst = procFee;
            if (carrierAllowedNoSubst != -1)
            {
                if (carrierAllowedNoSubst > procFee)
                {
                    allowedNoSubst = procFee;
                }
                else
                {
                    allowedNoSubst = carrierAllowedNoSubst;
                } 
            }
             
            double normalWriteOff = procFee - allowedNoSubst;
            //This is what the normal writeoff would be if no other insurance was involved.
            if (normalWriteOff < 0)
            {
                normalWriteOff = 0;
            }
             
            double remainingWriteOff = procFee - paidOtherInsTot - writeOffOtherIns;
            //This is the fee minus whatever other ins has already paid or written off.
            if (remainingWriteOff < 0)
            {
                remainingWriteOff = 0;
            }
             
            if (writeOffOtherIns > 0)
            {
                //no secondary writeoff estimates allowed
                cp.WriteOffEst = 0;
            }
            else //The reasoning for this is covered in the manual under Unit Test #1 and COB.
            //We can't go over either number.  We must use the smaller of the two.  If one of them is zero, then the writeoff is zero.
            if (remainingWriteOff == 0 || normalWriteOff == 0)
            {
                cp.WriteOffEst = 0;
            }
            else if (remainingWriteOff <= normalWriteOff)
            {
                cp.WriteOffEst = remainingWriteOff;
            }
            else
            {
                cp.WriteOffEst = normalWriteOff;
            }   
        }
        else
        {
            //capitation calculation never makes it this far:
            //else if(plan.PlanType=="c") {//capitation
            //	cp.WriteOffEst=cp.WriteOff;//this probably needs to change
            //}
            cp.WriteOffEst = -1;
        } 
    }

    /*
    		///<summary>We don't care about a looplist because those would be for different procedures.  So this calculation really only makes sense when calculating secondary insurance in the claim edit window or when calculating secondary estimates in the TP module.  HistList will include actual payments and estimated pending payments for this proc, but it will not include primary estimates.  Estimates are not handled here, but are instead passed in to ComputeBaseEst</summary>
    		private static double GetPaidOtherIns(ClaimProc cp,List<PatPlan> patPlanList,long patPlanNum,List<ClaimProcHist> histList) {
    			if(cp.ProcNum==0) {
    				return -1;
    			}
    			if(histList==null) {
    				return -1;
    			}
    			PatPlan pp=PatPlans.GetFromList(patPlanList.ToArray(),patPlanNum);
    			if(pp==null) {
    				return -1;
    			}
    			int thisOrdinal=pp.Ordinal;
    			if(thisOrdinal==1) {
    				return -1;
    			}
    			double retVal=0;
    			int ordinal;
    			for(int i=0;i<histList.Count;i++) {
    				ordinal=PatPlans.GetOrdinal(patPlanList,cp.PlanNum);
    				if(ordinal >= thisOrdinal){
    					continue;
    				}
    				retVal+=histList[i].Amount;
    			}
    			return retVal;
    		}*/
    /**
    * Only useful if secondary ins or greater.  For one procedure, it gets the sum of InsEstTotal/Override for other insurances with lower ordinals.  Either estimates or actual payments.  Will return 0 if ordinal of this claimproc is 1.
    */
    public static double getPaidOtherInsTotal(ClaimProc cp, List<PatPlan> patPlanList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), cp, patPlanList);
        }
         
        if (cp.ProcNum == 0)
        {
            return 0;
        }
         
        int thisOrdinal = PatPlans.GetOrdinal(cp.InsSubNum, patPlanList);
        if (thisOrdinal == 1)
        {
            return 0;
        }
         
        String command = "SELECT InsSubNum,InsEstTotal,InsEstTotalOverride,InsPayAmt,Status FROM claimproc WHERE ProcNum=" + POut.long(cp.ProcNum);
        DataTable table = Db.getTable(command);
        double retVal = 0;
        long subNum = new long();
        int ordinal = new int();
        double insEstTotal = new double();
        double insEstTotalOverride = new double();
        double insPayAmt = new double();
        OpenDentBusiness.ClaimProcStatus status = OpenDentBusiness.ClaimProcStatus.NotReceived;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            subNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
            ordinal = PatPlans.GetOrdinal(subNum, patPlanList);
            if (ordinal >= thisOrdinal)
            {
                continue;
            }
             
            insEstTotal = PIn.Double(table.Rows[i]["InsEstTotal"].ToString());
            insEstTotalOverride = PIn.Double(table.Rows[i]["InsEstTotalOverride"].ToString());
            insPayAmt = PIn.Double(table.Rows[i]["InsPayAmt"].ToString());
            status = (OpenDentBusiness.ClaimProcStatus)PIn.Int(table.Rows[i]["Status"].ToString());
            if (status == OpenDentBusiness.ClaimProcStatus.Received || status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                retVal += insPayAmt;
            }
             
            if (status == OpenDentBusiness.ClaimProcStatus.Estimate || status == OpenDentBusiness.ClaimProcStatus.NotReceived)
            {
                if (insEstTotalOverride != -1)
                {
                    retVal += insEstTotalOverride;
                }
                else
                {
                    retVal += insEstTotal;
                } 
            }
             
        }
        return retVal;
    }

    /**
    * Only useful if secondary ins or greater.  For one procedure, it gets the sum of BaseEst for other insurances with lower ordinals.  Either estimates or actual payments.  Will return 0 if ordinal of this claimproc is 1.
    */
    public static double getPaidOtherInsBaseEst(ClaimProc cp, List<PatPlan> patPlanList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), cp, patPlanList);
        }
         
        if (cp.ProcNum == 0)
        {
            return 0;
        }
         
        int thisOrdinal = PatPlans.GetOrdinal(cp.InsSubNum, patPlanList);
        if (thisOrdinal == 1)
        {
            return 0;
        }
         
        String command = "SELECT InsSubNum,BaseEst,InsPayAmt,Status FROM claimproc WHERE ProcNum=" + POut.long(cp.ProcNum);
        DataTable table = Db.getTable(command);
        double retVal = 0;
        long subNum = new long();
        int ordinal = new int();
        double baseEst = new double();
        double insPayAmt = new double();
        OpenDentBusiness.ClaimProcStatus status = OpenDentBusiness.ClaimProcStatus.NotReceived;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            subNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
            ordinal = PatPlans.GetOrdinal(subNum, patPlanList);
            if (ordinal >= thisOrdinal)
            {
                continue;
            }
             
            baseEst = PIn.Double(table.Rows[i]["BaseEst"].ToString());
            insPayAmt = PIn.Double(table.Rows[i]["InsPayAmt"].ToString());
            status = (OpenDentBusiness.ClaimProcStatus)PIn.Int(table.Rows[i]["Status"].ToString());
            if (status == OpenDentBusiness.ClaimProcStatus.Received || status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                retVal += insPayAmt;
            }
             
            if (status == OpenDentBusiness.ClaimProcStatus.Estimate || status == OpenDentBusiness.ClaimProcStatus.NotReceived)
            {
                retVal += baseEst;
            }
             
        }
        return retVal;
    }

    /**
    * Only useful if secondary ins or greater.  For one procedure, it gets the sum of WriteOffEstimates/Override for other insurances with lower ordinals.  Either estimates or actual writeoffs.  Will return 0 if ordinal of this claimproc is 1.
    */
    public static double getWriteOffOtherIns(ClaimProc cp, List<PatPlan> patPlanList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), cp, patPlanList);
        }
         
        if (cp.ProcNum == 0)
        {
            return 0;
        }
         
        int thisOrdinal = PatPlans.GetOrdinal(cp.InsSubNum, patPlanList);
        if (thisOrdinal == 1)
        {
            return 0;
        }
         
        String command = "SELECT InsSubNum,WriteOffEst,WriteOffEstOverride,WriteOff,Status FROM claimproc WHERE ProcNum=" + POut.long(cp.ProcNum);
        DataTable table = Db.getTable(command);
        double retVal = 0;
        long subNum = new long();
        int ordinal = new int();
        double writeOffEst = new double();
        double writeOffEstOverride = new double();
        double writeOff = new double();
        OpenDentBusiness.ClaimProcStatus status = OpenDentBusiness.ClaimProcStatus.NotReceived;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            subNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
            ordinal = PatPlans.GetOrdinal(subNum, patPlanList);
            if (ordinal >= thisOrdinal)
            {
                continue;
            }
             
            writeOffEst = PIn.Double(table.Rows[i]["WriteOffEst"].ToString());
            writeOffEstOverride = PIn.Double(table.Rows[i]["WriteOffEstOverride"].ToString());
            writeOff = PIn.Double(table.Rows[i]["WriteOff"].ToString());
            status = (OpenDentBusiness.ClaimProcStatus)PIn.Int(table.Rows[i]["Status"].ToString());
            if (status == OpenDentBusiness.ClaimProcStatus.Received || status == OpenDentBusiness.ClaimProcStatus.Supplemental)
            {
                retVal += writeOff;
            }
             
            if (status == OpenDentBusiness.ClaimProcStatus.Estimate || status == OpenDentBusiness.ClaimProcStatus.NotReceived)
            {
                if (writeOffEstOverride != -1)
                {
                    retVal += writeOffEstOverride;
                }
                else if (writeOffEst != -1)
                {
                    retVal += writeOffEst;
                }
                  
            }
             
        }
        return retVal;
    }

    /**
    * //Only useful if secondary ins or greater.  For one procedure, it gets the sum of WriteOffEstimates/Override for other insurances with lower ordinals.  Either estimates or actual writeoffs.  Will return 0 if ordinal of this claimproc is 1.
    */
    //public static double GetDeductibleOtherIns(ClaimProc cp,List<PatPlan> patPlanList) {
    //  if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    //    return Meth.GetObject<double>(MethodBase.GetCurrentMethod(),cp,patPlanList);
    //  }
    //  if(cp.ProcNum==0) {
    //    return 0;
    //  }
    //  int thisOrdinal=PatPlans.GetOrdinal(cp.InsSubNum,patPlanList);
    //  if(thisOrdinal==1) {
    //    return 0;
    //  }
    //  string command="SELECT InsSubNum,DedEst,DedEstOverride,DedApplied,Status FROM claimproc WHERE ProcNum="+POut.Long(cp.ProcNum);
    //  DataTable table=Db.GetTable(command);
    //  double retVal=0;
    //  long subNum;
    //  int ordinal;
    //  double dedEst;
    //  double dedEstOverride;
    //  double dedApplied;
    //  ClaimProcStatus status;
    //  for(int i=0;i<table.Rows.Count;i++) {
    //    subNum=PIn.Long(table.Rows[i]["InsSubNum"].ToString());
    //    ordinal=PatPlans.GetOrdinal(subNum,patPlanList);
    //    if(ordinal >= thisOrdinal) {
    //      continue;
    //    }
    //    dedEst=PIn.Double(table.Rows[i]["DedEst"].ToString());
    //    dedEstOverride=PIn.Double(table.Rows[i]["DedEstOverride"].ToString());
    //    dedApplied=PIn.Double(table.Rows[i]["DedApplied"].ToString());
    //    status=(ClaimProcStatus)PIn.Int(table.Rows[i]["Status"].ToString());
    //    if(status==ClaimProcStatus.Received || status==ClaimProcStatus.Supplemental) {
    //      retVal+=dedApplied;
    //    }
    //    if(status==ClaimProcStatus.Estimate || status==ClaimProcStatus.NotReceived) {
    //      if(dedEstOverride != -1) {
    //        retVal+=dedEst;
    //      }
    //      else if(dedEst !=-1){
    //        retVal+=dedEst;
    //      }
    //    }
    //  }
    //  return retVal;
    //}
    /**
    * Simply gets insEstTotal or its override if applicable.
    */
    public static double getInsEstTotal(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.InsEstTotalOverride != -1)
        {
            return cp.InsEstTotalOverride;
        }
         
        return cp.InsEstTotal;
    }

    /**
    * Simply gets dedEst or its override if applicable.  Can return 0, but never -1.
    */
    public static double getDedEst(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.DedEstOverride != -1)
        {
            return cp.DedEstOverride;
        }
        else if (cp.DedEst != -1)
        {
            return cp.DedEst;
        }
          
        return 0;
    }

    /**
    * Gets either the override or the calculated writeoff estimate.  Or zero if neither.
    */
    public static double getWriteOffEstimate(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.WriteOffEstOverride != -1)
        {
            return cp.WriteOffEstOverride;
        }
        else if (cp.WriteOffEst != -1)
        {
            return cp.WriteOffEst;
        }
          
        return 0;
    }

    public static String getPercentageDisplay(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.CapEstimate || cp.Status == OpenDentBusiness.ClaimProcStatus.CapComplete)
        {
            return "";
        }
         
        if (cp.PercentOverride != -1)
        {
            return cp.PercentOverride.ToString();
        }
        else if (cp.Percentage != -1)
        {
            return cp.Percentage.ToString();
        }
          
        return "";
    }

    public static String getCopayDisplay(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.CopayOverride != -1)
        {
            return cp.CopayOverride.ToString("f");
        }
        else if (cp.CopayAmt != -1)
        {
            return cp.CopayAmt.ToString("f");
        }
          
        return "";
    }

    /**
    * 
    */
    public static String getWriteOffEstimateDisplay(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.WriteOffEstOverride != -1)
        {
            return cp.WriteOffEstOverride.ToString("f");
        }
        else if (cp.WriteOffEst != -1)
        {
            return cp.WriteOffEst.ToString("f");
        }
          
        return "";
    }

    public static String getEstimateDisplay(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.CapEstimate || cp.Status == OpenDentBusiness.ClaimProcStatus.CapComplete)
        {
            return "";
        }
         
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.Estimate)
        {
            if (cp.InsEstTotalOverride != -1)
            {
                return cp.InsEstTotalOverride.ToString("f");
            }
            else
            {
                return cp.InsEstTotal.ToString("f");
            } 
        }
         
        return cp.InsPayEst.ToString("f");
    }

    //shows even if 0.
    /**
    * Returns 0 or -1 if no deduct.
    */
    public static double getDeductibleDisplay(ClaimProc cp) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.CapEstimate || cp.Status == OpenDentBusiness.ClaimProcStatus.CapComplete)
        {
            return -1;
        }
         
        if (cp.Status == OpenDentBusiness.ClaimProcStatus.Estimate)
        {
            if (cp.DedEstOverride != -1)
            {
                return cp.DedEstOverride;
            }
             
            return cp.DedEst;
        }
         
        return cp.DedApplied;
    }

    //else if(cp.DedEst > 0) {
    //could be -1
    //}
    //else {
    //	return "";
    //}
    /**
    * Used in TP module.  Gets all estimate notes for this proc.
    */
    public static String getEstimateNotes(long procNum, List<ClaimProc> cpList) throws Exception {
        String retVal = "";
        for (int i = 0;i < cpList.Count;i++)
        {
            if (cpList[i].ProcNum != procNum)
            {
                continue;
            }
             
            if (StringSupport.equals(cpList[i].EstimateNote, ""))
            {
                continue;
            }
             
            if (!StringSupport.equals(retVal, ""))
            {
                retVal += ", ";
            }
             
            retVal += cpList[i].EstimateNote;
        }
        return retVal;
    }

    public static double getTotalWriteOffEstimateDisplay(List<ClaimProc> cpList, long procNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < cpList.Count;i++)
        {
            if (cpList[i].ProcNum != procNum)
            {
                continue;
            }
             
            if (cpList[i].WriteOffEstOverride != -1)
            {
                retVal += cpList[i].WriteOffEstOverride;
            }
            else if (cpList[i].WriteOffEst != -1)
            {
                retVal += cpList[i].WriteOffEst;
            }
              
        }
        return retVal;
    }

    public static List<ClaimProcHist> getHistList(long patNum, List<Benefit> benList, List<PatPlan> patPlanList, List<InsPlan> planList, DateTime procDate, List<InsSub> subList) throws Exception {
        return GetHistList(patNum, benList, patPlanList, planList, -1, procDate, subList);
    }

    //No need to check RemotingRole; no call to db.
    /**
    * We pass in the benefit list so that we know whether to include family.  We are getting a simplified list of claimprocs.  History of payments and pending payments.  If the patient has multiple insurance, then this info will be for all of their insurance plans.  It runs a separate query for each plan because that's the only way to handle family history.  For some plans, the benefits will indicate entire family, but not for other plans.  And the date ranges can be different as well.   When this list is processed later, it is again filtered, but it can't have missing information.  Use excludeClaimNum=-1 to not exclude a claim.  A claim is excluded if editing from inside that claim.
    */
    public static List<ClaimProcHist> getHistList(long patNum, List<Benefit> benList, List<PatPlan> patPlanList, List<InsPlan> planList, long excludeClaimNum, DateTime procDate, List<InsSub> subList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ClaimProcHist>>GetObject(MethodBase.GetCurrentMethod(), patNum, benList, patPlanList, planList, excludeClaimNum, procDate, subList);
        }
         
        List<ClaimProcHist> retVal = new List<ClaimProcHist>();
        InsSub sub;
        InsPlan plan;
        boolean isFam = new boolean();
        boolean isLife = new boolean();
        DateTime dateStart = new DateTime();
        DataTable table = new DataTable();
        ClaimProcHist cph;
        for (int p = 0;p < patPlanList.Count;p++)
        {
            //loop through each plan that this patient is covered by
            sub = InsSubs.GetSub(patPlanList[p].InsSubNum, subList);
            //get the plan for the given patPlan
            plan = InsPlans.GetPlan(sub.PlanNum, planList);
            //test benefits for fam and life
            isFam = false;
            isLife = false;
            for (int i = 0;i < benList.Count;i++)
            {
                if (benList[i].PlanNum == 0 && benList[i].PatPlanNum != patPlanList[p].PatPlanNum)
                {
                    continue;
                }
                 
                if (benList[i].PatPlanNum == 0 && benList[i].PlanNum != plan.PlanNum)
                {
                    continue;
                }
                else if (benList[i].TimePeriod == BenefitTimePeriod.Lifetime)
                {
                    isLife = true;
                }
                  
                if (benList[i].CoverageLevel == BenefitCoverageLevel.Family)
                {
                    isFam = true;
                }
                 
            }
            if (isLife)
            {
                dateStart = new DateTime(1880, 1, 1);
            }
            else
            {
                //unsure what date to use to start.  DateTime.Today?  That might miss procs from late last year when doing secondary claim, InsPaidOther.
                //If we use the proc date, then it will indeed get an accurate history.  And future procedures just don't matter when calculating things.
                dateStart = BenefitLogic.ComputeRenewDate(procDate, plan.MonthRenew);
            } 
            //to get the codenum
            //claimproc.PlanNum="+POut.Long(plan.PlanNum)
            //no upper limit on date.
            //insPayAmt and DedApplied
            String command = "SELECT claimproc.ProcDate,CodeNum,InsPayEst,InsPayAmt,DedApplied,claimproc.PatNum,Status,ClaimNum,claimproc.InsSubNum,claimproc.ProcNum  " + "FROM claimproc " + "LEFT JOIN procedurelog on claimproc.ProcNum=procedurelog.ProcNum " + "WHERE " + "claimproc.InsSubNum=" + POut.Long(patPlanList[p].InsSubNum) + " AND claimproc.ProcDate >= " + POut.date(dateStart) + " AND claimproc.Status IN(" + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.NotReceived).ordinal()) + "," + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.Adjustment).ordinal()) + "," + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.Received).ordinal()) + "," + POut.Long(((Enum)OpenDentBusiness.ClaimProcStatus.Supplemental).ordinal()) + ")";
            if (!isFam)
            {
                //we include patnum because this one query can get results for multiple patients that all have this one subsriber.
                command += " AND claimproc.PatNum=" + POut.long(patNum);
            }
             
            if (excludeClaimNum != -1)
            {
                command += " AND claimproc.ClaimNum != " + POut.long(excludeClaimNum);
            }
             
            table = Db.getTable(command);
            for (int i = 0;i < table.Rows.Count;i++)
            {
                cph = new ClaimProcHist();
                cph.ProcDate = PIn.Date(table.Rows[i]["ProcDate"].ToString());
                cph.StrProcCode = ProcedureCodes.GetStringProcCode(PIn.Long(table.Rows[i]["CodeNum"].ToString()));
                cph.Status = (OpenDentBusiness.ClaimProcStatus)PIn.Long(table.Rows[i]["Status"].ToString());
                if (cph.Status == OpenDentBusiness.ClaimProcStatus.NotReceived)
                {
                    cph.Amount = PIn.Double(table.Rows[i]["InsPayEst"].ToString());
                }
                else
                {
                    cph.Amount = PIn.Double(table.Rows[i]["InsPayAmt"].ToString());
                } 
                cph.Deduct = PIn.Double(table.Rows[i]["DedApplied"].ToString());
                cph.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
                cph.ClaimNum = PIn.Long(table.Rows[i]["ClaimNum"].ToString());
                cph.InsSubNum = PIn.Long(table.Rows[i]["InsSubNum"].ToString());
                cph.ProcNum = PIn.Long(table.Rows[i]["ProcNum"].ToString());
                cph.PlanNum = plan.PlanNum;
                retVal.Add(cph);
            }
        }
        return retVal;
    }

    /**
    * Used in creation of the loopList.  Used in TP list estimation and in claim creation.  Some of the items in the claimProcList passed in will not have been saved to the database yet.
    */
    public static List<ClaimProcHist> getHistForProc(List<ClaimProc> claimProcList, long procNum, long codeNum) throws Exception {
        List<ClaimProcHist> retVal = new List<ClaimProcHist>();
        ClaimProcHist cph;
        for (int i = 0;i < claimProcList.Count;i++)
        {
            if (claimProcList[i].ProcNum != procNum)
            {
                continue;
            }
             
            cph = new ClaimProcHist();
            cph.Amount = ClaimProcs.GetInsEstTotal(claimProcList[i]);
            cph.ClaimNum = 0;
            if (claimProcList[i].DedEstOverride != -1)
            {
                cph.Deduct = claimProcList[i].DedEstOverride;
            }
            else
            {
                cph.Deduct = claimProcList[i].DedEst;
            } 
            cph.PatNum = claimProcList[i].PatNum;
            cph.PlanNum = claimProcList[i].PlanNum;
            cph.InsSubNum = claimProcList[i].InsSubNum;
            cph.ProcDate = DateTime.Today;
            cph.Status = OpenDentBusiness.ClaimProcStatus.Estimate;
            cph.StrProcCode = ProcedureCodes.getStringProcCode(codeNum);
            retVal.Add(cph);
        }
        return retVal;
    }

    /**
    * Does not make call to db unless necessary.
    */
    public static void setProvForProc(Procedure proc, List<ClaimProc> ClaimProcList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), proc, ClaimProcList);
            return ;
        }
         
        for (int i = 0;i < ClaimProcList.Count;i++)
        {
            if (ClaimProcList[i].ProcNum != proc.ProcNum)
            {
                continue;
            }
             
            if (ClaimProcList[i].ProvNum == proc.ProvNum)
            {
                continue;
            }
             
            //no change needed
            ClaimProcList[i].ProvNum = proc.ProvNum;
            Update(ClaimProcList[i]);
        }
    }

    /**
    * For moving rows up and down the batch insurance window.
    */
    public static void setPaymentRow(long claimNum, long claimPaymentNum, int paymentRow) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimNum, claimPaymentNum, paymentRow);
            return ;
        }
         
        String command = "UPDATE claimproc SET PaymentRow=" + POut.int(paymentRow) + " " + "WHERE ClaimNum=" + POut.long(claimNum) + " " + "AND ClaimPaymentNum=" + POut.long(claimPaymentNum);
        Db.nonQ(command);
    }

    /**
    * Attaches all claimprocs that have an InsPayAmt entered to the specified ClaimPayment, and then returns the sum amount of all the attached payments.  The claimprocs must be currently unattached.  Used from FormClaimEdit when user is not doing the batch entry.
    */
    public static double attachAllOutstandingToPayment(long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), claimPaymentNum);
        }
         
        String command = "UPDATE claimproc SET ClaimPaymentNum=" + POut.long(claimPaymentNum) + " " + "WHERE ClaimPaymentNum=0 " + "AND (claimproc.Status = '1' OR claimproc.Status = '4' OR claimproc.Status='5') " + "AND InsPayAmt != 0";
        //received or supplemental or capclaim
        Db.nonQ(command);
        command = "SELECT SUM(InsPayAmt) FROM claimproc WHERE ClaimPaymentNum=" + POut.long(claimPaymentNum);
        return PIn.double(Db.getScalar(command));
    }

}


