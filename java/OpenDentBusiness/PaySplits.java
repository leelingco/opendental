//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PaySplit;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PaySplits   
{
    /**
    * Returns all paySplits for the given patNum, organized by procDate.  WARNING! Also includes related paysplits that aren't actually attached to patient.  Includes any split where payment is for this patient.
    */
    public static PaySplit[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PaySplit[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        /*This query was too slow
        			string command=
        				"SELECT DISTINCT paysplit.* FROM paysplit,payment "
        				+"WHERE paysplit.PayNum=payment.PayNum "
        				+"AND (paysplit.PatNum = '"+POut.Long(patNum)+"' OR payment.PatNum = '"+POut.Long(patNum)+"') "
        				+"ORDER BY ProcDate";*/
        //this query goes 10 times faster for very large databases
        String command = "select DISTINCT paysplitunion.* FROM " + "(SELECT DISTINCT paysplit.* FROM paysplit,payment " + "WHERE paysplit.PayNum=payment.PayNum and payment.PatNum='" + POut.long(patNum) + "' " + "UNION " + "SELECT DISTINCT paysplit.* FROM paysplit,payment " + "WHERE paysplit.PayNum = payment.PayNum AND paysplit.PatNum='" + POut.long(patNum) + "') AS paysplitunion " + "ORDER BY paysplitunion.ProcDate";
        return Crud.PaySplitCrud.SelectMany(command).ToArray();
    }

    /**
    * Used from payment window to get all paysplits for the payment.
    */
    public static List<PaySplit> getForPayment(long payNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PaySplit>>GetObject(MethodBase.GetCurrentMethod(), payNum);
        }
         
        String command = "SELECT * FROM paysplit " + "WHERE PayNum=" + POut.long(payNum);
        return Crud.PaySplitCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(PaySplit split) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), split);
            return ;
        }
         
        Crud.PaySplitCrud.Update(split);
    }

    /**
    * 
    */
    public static long insert(PaySplit split) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            split.SplitNum = Meth.GetLong(MethodBase.GetCurrentMethod(), split);
            return split.SplitNum;
        }
         
        return Crud.PaySplitCrud.Insert(split);
    }

    /**
    * Deletes the paysplit.
    */
    public static void delete(PaySplit split) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), split);
            return ;
        }
         
        String command = "DELETE from paysplit WHERE SplitNum = " + POut.long(split.SplitNum);
        Db.nonQ(command);
    }

    /**
    * Returns all paySplits for the given procNum. Must supply a list of all paysplits for the patient.
    */
    public static ArrayList getForProc(long procNum, PaySplit[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList retVal = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProcNum == procNum)
            {
                retVal.Add(List[i]);
            }
             
        }
        return retVal;
    }

    /**
    * Used from ContrAccount and ProcEdit to display and calculate payments attached to procs. Used once in FormProcEdit
    */
    public static double getTotForProc(long procNum, PaySplit[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].ProcNum == procNum)
            {
                retVal += List[i].SplitAmt;
            }
             
        }
        return retVal;
    }

    /**
    * Used from FormPaySplitEdit.  Returns total payments for a procedure for all paysplits other than the supplied excluded paysplit.
    */
    public static double getTotForProc(long procNum, PaySplit[] List, long excludeSplitNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].SplitNum == excludeSplitNum)
            {
                continue;
            }
             
            if (List[i].ProcNum == procNum)
            {
                retVal += List[i].SplitAmt;
            }
             
        }
        return retVal;
    }

    /**
    * Used once in ContrAccount.  WARNING!  The returned list of 'paysplits' are not real paysplits.  They are actually grouped by patient and date.  Only the ProcDate, SplitAmt, PatNum, and ProcNum(one of many) are filled. Must supply a list which would include all paysplits for this payment.
    */
    public static ArrayList getGroupedForPayment(long payNum, PaySplit[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList retVal = new ArrayList();
        int matchI = new int();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].PayNum == payNum)
            {
                //find a 'paysplit' with matching procdate and patnum
                matchI = -1;
                for (int j = 0;j < retVal.Count;j++)
                {
                    if (((PaySplit)retVal[j]).ProcDate == List[i].ProcDate && ((PaySplit)retVal[j]).PatNum == List[i].PatNum)
                    {
                        matchI = j;
                        break;
                    }
                     
                }
                if (matchI == -1)
                {
                    retVal.Add(new PaySplit());
                    matchI = retVal.Count - 1;
                    ((PaySplit)retVal[matchI]).ProcDate = List[i].ProcDate;
                    ((PaySplit)retVal[matchI]).PatNum = List[i].PatNum;
                }
                 
                if (((PaySplit)retVal[matchI]).ProcNum == 0 && List[i].ProcNum != 0)
                {
                    ((PaySplit)retVal[matchI]).ProcNum = List[i].ProcNum;
                }
                 
                ((PaySplit)retVal[matchI]).SplitAmt += List[i].SplitAmt;
            }
             
        }
        return retVal;
    }

    /**
    * Only those amounts that have the same paynum, procDate, and patNum as the payment, and are not attached to procedures.
    */
    public static double getAmountForPayment(long payNum, DateTime payDate, long patNum, PaySplit[] paySplitList) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < paySplitList.Length;i++)
        {
            if (paySplitList[i].PayNum != payNum)
            {
                continue;
            }
             
            if (paySplitList[i].PatNum != patNum)
            {
                continue;
            }
             
            if (paySplitList[i].ProcDate != payDate)
            {
                continue;
            }
             
            if (paySplitList[i].ProcNum != 0)
            {
                continue;
            }
             
            retVal += paySplitList[i].SplitAmt;
        }
        return retVal;
    }

    /**
    * Used once in ContrAccount to just get the splits for a single patient.  The supplied list also contains splits that are not necessarily for this one patient.
    */
    public static PaySplit[] getForPatient(long patNum, PaySplit[] List) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList retVal = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].PatNum == patNum)
            {
                retVal.Add(List[i]);
            }
             
        }
        PaySplit[] retList = new PaySplit[retVal.Count];
        retVal.CopyTo(retList);
        return retList;
    }

    /**
    * Used once in ContrAccount.  Usually returns 0 unless there is a payplan for this payment and patient.
    */
    public static long getPayPlanNum(long payNum, long patNum, PaySplit[] List) throws Exception {
        for (int i = 0;i < List.Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (List[i].PayNum == payNum && List[i].PatNum == patNum && List[i].PayPlanNum != 0)
            {
                return List[i].PayPlanNum;
            }
             
        }
        return 0;
    }

    /*
    		///<summary>Used in ComputeBalances to compute balance for a single patient. Supply a list of all paysplits for the patient.</summary>
    		public static double ComputeBal(PaySplit[] list){//
    			double retVal=0;
    			for(int i=0;i<list.Length;i++){
    				retVal+=list[i].SplitAmt;
    			}
    			return retVal;
    		}*/
    /**
    * Used in FormPayment to sych database with changes user made to the paySplit list for a payment.  Must supply an old list for comparison.  Only the differences are saved.
    */
    public static void updateList(List<PaySplit> oldSplitList, List<PaySplit> newSplitList) throws Exception {
        //No need to check RemotingRole; no call to db.
        PaySplit newPaySplit;
        for (int i = 0;i < oldSplitList.Count;i++)
        {
            //loop through the old list
            newPaySplit = null;
            for (int j = 0;j < newSplitList.Count;j++)
            {
                if (newSplitList[j] == null || newSplitList[j].SplitNum == 0)
                {
                    continue;
                }
                 
                if (((PaySplit)oldSplitList[i]).SplitNum == ((PaySplit)newSplitList[j]).SplitNum)
                {
                    newPaySplit = newSplitList[j];
                    break;
                }
                 
            }
            if (newPaySplit == null)
            {
                //PaySplit with matching SplitNum was not found, so it must have been deleted
                Delete(oldSplitList[i]);
                continue;
            }
             
            //PaySplit was found with matching SplitNum, so check for changes
            if (newPaySplit.DateEntry != oldSplitList[i].DateEntry || newPaySplit.DatePay != oldSplitList[i].DatePay || newPaySplit.PatNum != oldSplitList[i].PatNum || newPaySplit.PayNum != oldSplitList[i].PayNum || newPaySplit.PayPlanNum != oldSplitList[i].PayPlanNum || newPaySplit.ProcDate != oldSplitList[i].ProcDate || newPaySplit.ProcNum != oldSplitList[i].ProcNum || newPaySplit.ProvNum != oldSplitList[i].ProvNum || newPaySplit.SplitAmt != oldSplitList[i].SplitAmt || newPaySplit.UnearnedType != oldSplitList[i].UnearnedType || newPaySplit.ClinicNum != oldSplitList[i].ClinicNum)
            {
                update(newPaySplit);
            }
             
        }
        for (int i = 0;i < newSplitList.Count;i++)
        {
            //loop through the new list
            if (newSplitList[i] == null)
            {
                continue;
            }
             
            if (newSplitList[i].SplitNum != 0)
            {
                continue;
            }
             
            //entry with SplitNum=0, so it's new
            Insert(newSplitList[i]);
        }
    }

}


