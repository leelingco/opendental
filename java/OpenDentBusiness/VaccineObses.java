//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.VaccineObs;

/**
* 
*/
public class VaccineObses   
{
    /**
    * 
    */
    public static long insert(VaccineObs vaccineObs) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            vaccineObs.VaccineObsNum = Meth.GetLong(MethodBase.GetCurrentMethod(), vaccineObs);
            return vaccineObs.VaccineObsNum;
        }
         
        return Crud.VaccineObsCrud.Insert(vaccineObs);
    }

    /**
    * Gets one VaccineObs from the db.
    */
    public static List<VaccineObs> getForVaccine(long vaccinePatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<VaccineObs>>GetObject(MethodBase.GetCurrentMethod(), vaccinePatNum);
        }
         
        String command = "SELECT * FROM vaccineobs WHERE VaccinePatNum=" + POut.long(vaccinePatNum) + " ORDER BY VaccineObsNumGroup";
        return Crud.VaccineObsCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void update(VaccineObs vaccineObs) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccineObs);
            return ;
        }
         
        Crud.VaccineObsCrud.Update(vaccineObs);
    }

    /**
    * 
    */
    public static void delete(long vaccineObsNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccineObsNum);
            return ;
        }
         
        String command = "DELETE FROM vaccineobs WHERE VaccineObsNum = " + POut.long(vaccineObsNum);
        Db.nonQ(command);
    }

    public static void deleteForVaccinePat(long vaccinePatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccinePatNum);
            return ;
        }
         
        String command = "DELETE FROM vaccineobs WHERE VaccinePatNum=" + POut.long(vaccinePatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<VaccineObs> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<VaccineObs>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM vaccineobs WHERE PatNum = "+POut.Long(patNum);
			return Crud.VaccineObsCrud.SelectMany(command);
		}
		///<summary>Gets one VaccineObs from the db.</summary>
		public static VaccineObs GetOne(long vaccineObsNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<VaccineObs>(MethodBase.GetCurrentMethod(),vaccineObsNum);
			}
			return Crud.VaccineObsCrud.SelectOne(vaccineObsNum);
		}
		*/