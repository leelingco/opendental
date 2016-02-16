//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.FamilyHealth;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class FamilyHealths   
{
    /**
    * 
    */
    public static void delete(long familyHealthNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), familyHealthNum);
            return ;
        }
         
        String command = "DELETE FROM familyhealth WHERE FamilyHealthNum = " + POut.long(familyHealthNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(FamilyHealth familyHealth) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            familyHealth.FamilyHealthNum = Meth.GetLong(MethodBase.GetCurrentMethod(), familyHealth);
            return familyHealth.FamilyHealthNum;
        }
         
        return Crud.FamilyHealthCrud.Insert(familyHealth);
    }

    /**
    * 
    */
    public static void update(FamilyHealth familyHealth) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), familyHealth);
            return ;
        }
         
        Crud.FamilyHealthCrud.Update(familyHealth);
    }

    /**
    * 
    */
    public static List<FamilyHealth> getFamilyHealthForPat(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<FamilyHealth>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM familyhealth WHERE PatNum = " + POut.long(patNum);
        return Crud.FamilyHealthCrud.SelectMany(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one FamilyHealth from the db.</summary>
		public static FamilyHealth GetOne(long familyHealthNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<FamilyHealth>(MethodBase.GetCurrentMethod(),familyHealthNum);
			}
			return Crud.FamilyHealthCrud.SelectOne(familyHealthNum);
		}
		*/