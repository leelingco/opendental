//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.InstallmentPlan;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class InstallmentPlans   
{
    /**
    * Gets the installment plan for this family.  If none, returns null.
    */
    public static InstallmentPlan getOneForFam(long guarNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InstallmentPlan>GetObject(MethodBase.GetCurrentMethod(), guarNum);
        }
         
        String command = "SELECT * FROM installmentplan WHERE PatNum = " + POut.long(guarNum) + " LIMIT 1";
        return Crud.InstallmentPlanCrud.SelectOne(command);
    }

    /**
    * Gets one InstallmentPlan from the db.
    */
    public static InstallmentPlan getOne(long installmentPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<InstallmentPlan>GetObject(MethodBase.GetCurrentMethod(), installmentPlanNum);
        }
         
        return Crud.InstallmentPlanCrud.SelectOne(installmentPlanNum);
    }

    /**
    * 
    */
    public static long insert(InstallmentPlan installmentPlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            installmentPlan.InstallmentPlanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), installmentPlan);
            return installmentPlan.InstallmentPlanNum;
        }
         
        return Crud.InstallmentPlanCrud.Insert(installmentPlan);
    }

    /**
    * 
    */
    public static void update(InstallmentPlan installmentPlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), installmentPlan);
            return ;
        }
         
        Crud.InstallmentPlanCrud.Update(installmentPlan);
    }

    /**
    * 
    */
    public static void delete(long installmentPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), installmentPlanNum);
            return ;
        }
         
        String command = "DELETE FROM installmentplan WHERE InstallmentPlanNum = " + POut.long(installmentPlanNum);
        Db.nonQ(command);
    }

}


