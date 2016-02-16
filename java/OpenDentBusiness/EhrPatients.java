//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.EhrPatient;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrPatients   
{
    /**
    * Only call when EHR is enabled.  Creates the ehrpatient record for the patient if a record does not already exist.  Always returns a non-null EhrPatient.
    */
    public static EhrPatient refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EhrPatient>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT COUNT(*) FROM ehrpatient WHERE patnum='" + POut.long(patNum) + "'";
        if (StringSupport.equals(Db.getCount(command), "0"))
        {
            //A record does not exist for this patient yet.
            insert(patNum);
        }
         
        //Create a new record.
        command = "SELECT * FROM ehrpatient WHERE patnum ='" + POut.long(patNum) + "'";
        return Crud.EhrPatientCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void update(EhrPatient ehrPatient) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrPatient);
            return ;
        }
         
        Crud.EhrPatientCrud.Update(ehrPatient);
    }

    /**
    * 
    */
    private static void insert(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), patNum);
            return ;
        }
         
        //Random keys not necessary to check because of 1:1 patNum.
        //However, this is a lazy insert, so multiple locations might attempt it.
        //Just in case, we will have it fail silently.
        EhrPatient ehrPatient = new EhrPatient();
        ehrPatient.PatNum = patNum;
        try
        {
            Crud.EhrPatientCrud.Insert(ehrPatient, true);
        }
        catch (Exception __dummyCatchVar0)
        {
        }
    
    }

}


//Fail Silently.
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrPatient> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrPatient>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrpatient WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrPatientCrud.SelectMany(command);
		}
		///<summary>Gets one EhrPatient from the db.</summary>
		public static EhrPatient GetOne(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrPatient>(MethodBase.GetCurrentMethod(),patNum);
			}
			return Crud.EhrPatientCrud.SelectOne(patNum);
		}
		///<summary></summary>
		public static void Delete(long patNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),patNum);
				return;
			}
			string command= "DELETE FROM ehrpatient WHERE PatNum = "+POut.Long(patNum);
			Db.NonQ(command);
		}
		*/