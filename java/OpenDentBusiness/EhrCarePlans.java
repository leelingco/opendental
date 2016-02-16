//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrCarePlan;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrCarePlans   
{
    /**
    * 
    */
    public static List<EhrCarePlan> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrCarePlan>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM ehrcareplan WHERE PatNum = " + POut.long(patNum) + " ORDER BY DatePlanned";
        return Crud.EhrCarePlanCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EhrCarePlan ehrCarePlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrCarePlan.EhrCarePlanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrCarePlan);
            return ehrCarePlan.EhrCarePlanNum;
        }
         
        return Crud.EhrCarePlanCrud.Insert(ehrCarePlan);
    }

    /**
    * 
    */
    public static void update(EhrCarePlan ehrCarePlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrCarePlan);
            return ;
        }
         
        Crud.EhrCarePlanCrud.Update(ehrCarePlan);
    }

    /**
    * 
    */
    public static void delete(long ehrCarePlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrCarePlanNum);
            return ;
        }
         
        String command = "DELETE FROM ehrcareplan WHERE EhrCarePlanNum = " + POut.long(ehrCarePlanNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one EhrCarePlan from the db.</summary>
		public static EhrCarePlan GetOne(long ehrCarePlanNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrCarePlan>(MethodBase.GetCurrentMethod(),ehrCarePlanNum);
			}
			return Crud.EhrCarePlanCrud.SelectOne(ehrCarePlanNum);
		}
		*/