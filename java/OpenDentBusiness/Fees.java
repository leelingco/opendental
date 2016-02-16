//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Fee;
import OpenDentBusiness.Fees;
import OpenDentBusiness.FeeScheds;
import OpenDentBusiness.InsPlan;
import OpenDentBusiness.InsPlans;
import OpenDentBusiness.InsSub;
import OpenDentBusiness.InsSubs;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatPlan;
import OpenDentBusiness.PatPlans;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.ProviderC;
import OpenDentBusiness.Providers;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Fees   
{
    private static List<Fee> listt = new List<Fee>();
    /**
    * A list of all Fees.
    */
    public static List<Fee> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Fee> value) throws Exception {
        listt = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM fee";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Fee";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.FeeCrud.TableToList(table);
    }

    /*
    			Dict=new Dictionary<FeeKey,Fee>();
    			FeeKey key;
    			for(int i=0;i<Listt.Count;i++) {
    				key=new FeeKey();
    				key.codeNum=Listt[i].CodeNum;
    				key.feeSchedNum=Listt[i].FeeSched;
    				if(Dict.ContainsKey(key)) {
    					//Older versions used to delete duplicates here
    				}
    				else {
    					Dict.Add(key,Listt[i]);
    				}
    			}*/
    /**
    * 
    */
    public static void update(Fee fee) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), fee);
            return ;
        }
         
        Crud.FeeCrud.Update(fee);
    }

    /**
    * 
    */
    public static long insert(Fee fee) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            fee.FeeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), fee);
            return fee.FeeNum;
        }
         
        return Crud.FeeCrud.Insert(fee);
    }

    /**
    * 
    */
    public static void delete(Fee fee) throws Exception {
        //No need to check RemotingRole; no call to db.
        delete(fee.FeeNum);
    }

    /**
    * 
    */
    public static void delete(long feeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), feeNum);
            return ;
        }
         
        String command = "DELETE FROM fee WHERE FeeNum=" + feeNum;
        Db.nonQ(command);
    }

    /**
    * Returns null if no fee exists for code/feeSched combo.
    */
    public static Fee getFee(long codeNum, long feeSchedNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (codeNum == 0)
        {
            return null;
        }
         
        if (feeSchedNum == 0)
        {
            return null;
        }
         
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].CodeNum == codeNum && getListt()[i].FeeSched == feeSchedNum)
            {
                return getListt()[i];
            }
             
        }
        return null;
    }

    /**
    * Returns an amount if a fee has been entered.  Otherwise returns -1.  Not usually used directly.
    */
    public static double getAmount(long codeNum, long feeSchedNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (codeNum == 0)
        {
            return -1;
        }
         
        if (feeSchedNum == 0)
        {
            return -1;
        }
         
        if (FeeScheds.getIsHidden(feeSchedNum))
        {
            return -1;
        }
         
        for (int i = 0;i < getListt().Count;i++)
        {
            //you cannot obtain fees for hidden fee schedules
            if (getListt()[i].CodeNum == codeNum && getListt()[i].FeeSched == feeSchedNum)
            {
                return getListt()[i].Amount;
            }
             
        }
        return -1;
    }

    //code not found
    /**
    * Almost the same as GetAmount.  But never returns -1;  Returns an amount if a fee has been entered.  Returns 0 if code can't be found.
    * TODO: =js 6/19/13 There are many places where this is used to get the fee for a proc.  This results in approx 12 identical chunks of code throughout the program.
    * We need to build a method to eliminate all those identical sections.  This will prevent bugs from cropping up when these sections get out of synch.
    */
    public static double getAmount0(long codeNum, long feeSched) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = getAmount(codeNum,feeSched);
        if (retVal == -1)
        {
            return 0;
        }
         
        return retVal;
    }

    /**
    * Gets the fee schedule from the first insplan, the patient, or the provider in that order.  Either returns a fee schedule (fk to definition.DefNum) or 0.
    */
    public static long getFeeSched(Patient pat, List<InsPlan> planList, List<PatPlan> patPlans, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        //there's not really a good place to put this function, so it's here.
        long retVal = 0;
        if (PatPlans.GetInsSubNum(patPlans, 1) != 0)
        {
            InsSub SubCur = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlans, 1), subList);
            InsPlan PlanCur = InsPlans.GetPlan(SubCur.PlanNum, planList);
            if (PlanCur == null)
            {
                retVal = 0;
            }
            else
            {
                retVal = PlanCur.FeeSched;
            } 
        }
         
        if (retVal == 0)
        {
            if (pat.FeeSched != 0)
            {
                retVal = pat.FeeSched;
            }
            else
            {
                if (pat.PriProv == 0)
                {
                    retVal = ProviderC.getListShort()[0].FeeSched;
                }
                else
                {
                    //MessageBox.Show(Providers.GetIndex(Patients.Cur.PriProv).ToString());
                    retVal = ProviderC.getListLong()[Providers.getIndexLong(pat.PriProv)].FeeSched;
                } 
            } 
        }
         
        return retVal;
    }

    /**
    * A simpler version of the same function above.  The required numbers can be obtained in a fairly simple query.  Might return a 0 if the primary provider does not have a fee schedule set.
    */
    public static long getFeeSched(long priPlanFeeSched, long patFeeSched, long patPriProvNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (priPlanFeeSched != 0)
        {
            return priPlanFeeSched;
        }
         
        if (patFeeSched != 0)
        {
            return patFeeSched;
        }
         
        return ProviderC.getListLong()[Providers.getIndexLong(patPriProvNum)].FeeSched;
    }

    /**
    * Gets the fee schedule from the primary MEDICAL insurance plan, the patient, or the provider in that order.
    */
    public static long getMedFeeSched(Patient pat, List<InsPlan> planList, List<PatPlan> patPlans, List<InsSub> subList) throws Exception {
        //No need to check RemotingRole; no call to db. ??
        long retVal = 0;
        if (PatPlans.GetInsSubNum(patPlans, 1) != 0)
        {
            //Pick the medinsplan with the ordinal closest to zero
            int planOrdinal = 10;
            //This is a hack, but I doubt anyone would have more than 10 plans
            InsSub subCur;
            for (Object __dummyForeachVar0 : patPlans)
            {
                PatPlan patplan = (PatPlan)__dummyForeachVar0;
                subCur = InsSubs.GetSub(patplan.InsSubNum, subList);
                if (patplan.Ordinal < planOrdinal && InsPlans.GetPlan(subCur.PlanNum, planList).IsMedical)
                {
                    planOrdinal = patplan.Ordinal;
                }
                 
            }
            subCur = InsSubs.GetSub(PatPlans.GetInsSubNum(patPlans, planOrdinal), subList);
            InsPlan PlanCur = InsPlans.GetPlan(subCur.PlanNum, planList);
            if (PlanCur == null)
            {
                retVal = 0;
            }
            else
            {
                retVal = PlanCur.FeeSched;
            } 
        }
         
        if (retVal == 0)
        {
            if (pat.FeeSched != 0)
            {
                retVal = pat.FeeSched;
            }
            else
            {
                if (pat.PriProv == 0)
                {
                    retVal = ProviderC.getListShort()[0].FeeSched;
                }
                else
                {
                    //MessageBox.Show(Providers.GetIndex(Patients.Cur.PriProv).ToString());
                    retVal = ProviderC.getListLong()[Providers.getIndexLong(pat.PriProv)].FeeSched;
                } 
            } 
        }
         
        return retVal;
    }

    /**
    * Clears all fees from one fee schedule.  Supply the FeeSchedNum of the feeSchedule.
    */
    public static void clearFeeSched(long schedNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), schedNum);
            return ;
        }
         
        String command = "DELETE FROM fee WHERE FeeSched=" + schedNum;
        Db.nonQ(command);
    }

    /**
    * Copies any fee objects over to the new fee schedule.  Usually run ClearFeeSched first.  Be careful exactly which int's you supply.
    */
    public static void copyFees(long fromFeeSched, long toFeeSched) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getListt() == null)
        {
            refreshCache();
        }
         
        Fee fee;
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].FeeSched != fromFeeSched)
            {
                continue;
            }
             
            fee = getListt()[i].Copy();
            fee.FeeSched = toFeeSched;
            Fees.insert(fee);
        }
    }

    /**
    * Increases the fee schedule by percent.  Round should be the number of decimal places, either 0,1,or 2.
    */
    public static void increase(long feeSched, int percent, int round) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getListt() == null)
        {
            refreshCache();
        }
         
        Fee fee;
        double newVal = new double();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].FeeSched != feeSched)
            {
                continue;
            }
             
            if (getListt()[i].Amount == 0)
            {
                continue;
            }
             
            fee = getListt()[i].Copy();
            newVal = (double)fee.Amount * (1 + (double)percent / 100);
            if (round > 0)
            {
                fee.Amount = Math.Round(newVal, round);
            }
            else
            {
                fee.Amount = Math.Round(newVal, MidpointRounding.AwayFromZero);
            } 
            Fees.update(fee);
        }
    }

    /**
    * schedI is the currently displayed index of the fee schedule to save to.  If an amt of -1 is passed in, then it indicates a "blank" entry which will cause deletion of any existing fee.
    */
    public static void import(String codeText, double amt, long feeSchedNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (!ProcedureCodes.isValidCode(codeText))
        {
            return ;
        }
         
        //skip for now. Possibly insert a code in a future version.
        long feeNum = getFeeNum(ProcedureCodes.getCodeNum(codeText),feeSchedNum);
        if (feeNum > 0)
        {
            delete(feeNum);
        }
         
        if (amt == -1)
        {
            return ;
        }
         
        //RefreshCache();
        Fee fee = new Fee();
        fee.Amount = amt;
        fee.FeeSched = feeSchedNum;
        fee.CodeNum = ProcedureCodes.getCodeNum(codeText);
        insert(fee);
    }

    //RefreshCache();//moved this outside the loop
    /**
    * Gets the FeeNum from the database, returns 0 if none found.
    */
    public static long getFeeNum(long codeNum, long feeSchedNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetLong(MethodBase.GetCurrentMethod(), codeNum, feeSchedNum);
        }
         
        String command = "SELECT FeeNum FROM fee WHERE CodeNum=" + POut.long(codeNum) + " AND FeeSched=" + POut.long(feeSchedNum);
        return PIn.long(Db.getScalar(command));
    }

}


