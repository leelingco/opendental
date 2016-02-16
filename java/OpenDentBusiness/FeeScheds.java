//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.FeeSched;
import OpenDentBusiness.FeeSchedC;
import OpenDentBusiness.FeeScheduleType;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class FeeScheds   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM feesched ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "FeeSched";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        //FeeSchedC.ListLong=new List<FeeSched>();
        FeeSchedC.setListShort(new List<FeeSched>());
        FeeSchedC.setListLong(Crud.FeeSchedCrud.TableToList(table));
        for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
        {
            if (!FeeSchedC.getListLong()[i].IsHidden)
            {
                FeeSchedC.getListShort().Add(FeeSchedC.getListLong()[i]);
            }
             
        }
    }

    /**
    * 
    */
    public static long insert(FeeSched feeSched) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            feeSched.FeeSchedNum = Meth.GetLong(MethodBase.GetCurrentMethod(), feeSched);
            return feeSched.FeeSchedNum;
        }
         
        return Crud.FeeSchedCrud.Insert(feeSched);
    }

    /**
    * 
    */
    public static void update(FeeSched feeSched) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), feeSched);
            return ;
        }
         
        Crud.FeeSchedCrud.Update(feeSched);
    }

    public static String getDescription(long feeSchedNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (feeSchedNum == 0)
        {
            return "";
        }
         
        for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
        {
            if (FeeSchedC.getListLong()[i].FeeSchedNum == feeSchedNum)
            {
                return FeeSchedC.getListLong()[i].Description;
            }
             
        }
        return "";
    }

    public static boolean getIsHidden(long feeSchedNum) throws Exception {
        for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (FeeSchedC.getListLong()[i].FeeSchedNum == feeSchedNum)
            {
                return FeeSchedC.getListLong()[i].IsHidden;
            }
             
        }
        return true;
    }

    /**
    * Will return null if exact name not found.
    */
    public static FeeSched getByExactName(String description) throws Exception {
        for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (StringSupport.equals(FeeSchedC.getListLong()[i].Description, description))
            {
                return FeeSchedC.getListLong()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Will return null if exact name not found.
    */
    public static FeeSched getByExactName(String description, FeeScheduleType feeSchedType) throws Exception {
        for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (FeeSchedC.getListLong()[i].FeeSchedType != feeSchedType)
            {
                continue;
            }
             
            if (StringSupport.equals(FeeSchedC.getListLong()[i].Description, description))
            {
                return FeeSchedC.getListLong()[i].Copy();
            }
             
        }
        return null;
    }

    /**
    * Only used in FormInsPlan and FormFeeScheds.
    */
    public static List<FeeSched> getListForType(FeeScheduleType feeSchedType, boolean includeHidden) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<FeeSched> retVal = new List<FeeSched>();
        for (int i = 0;i < FeeSchedC.getListLong().Count;i++)
        {
            if (!includeHidden && FeeSchedC.getListLong()[i].IsHidden)
            {
                continue;
            }
             
            if (FeeSchedC.getListLong()[i].FeeSchedType == feeSchedType)
            {
                retVal.Add(FeeSchedC.getListLong()[i].Copy());
            }
             
        }
        return retVal;
    }

    /**
    * Deletes FeeScheds that are hidden and not attached to any insurance plans.  Returns the number of deleted fee scheds.
    */
    public static long cleanupAllowedScheds() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetLong(MethodBase.GetCurrentMethod());
        }
         
        long result = new long();
        //Detach allowed FeeSchedules from any hidden InsPlans.
        String command = "UPDATE insplan " + "SET AllowedFeeSched=0 " + "WHERE IsHidden=1";
        Db.nonQ(command);
        //Delete unattached FeeSchedules.
        command = "DELETE FROM feesched " + "WHERE FeeSchedNum NOT IN (SELECT AllowedFeeSched FROM insplan) " + "AND FeeSchedType=" + POut.int(((Enum)FeeScheduleType.Allowed).ordinal());
        result = Db.nonQ(command);
        //Delete all orphaned fees.
        command = "DELETE FROM fee " + "WHERE FeeSched NOT IN (SELECT FeeSchedNum FROM feesched)";
        Db.nonQ(command);
        return result;
    }

}


