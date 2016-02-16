//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.VaccineObses;
import OpenDentBusiness.VaccinePat;

/**
* 
*/
public class VaccinePats   
{
    /**
    * 
    */
    public static List<VaccinePat> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<VaccinePat>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM vaccinepat WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateTimeStart";
        return Crud.VaccinePatCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(VaccinePat vaccinePat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            vaccinePat.VaccinePatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), vaccinePat);
            return vaccinePat.VaccinePatNum;
        }
         
        return Crud.VaccinePatCrud.Insert(vaccinePat);
    }

    /**
    * 
    */
    public static void update(VaccinePat vaccinePat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccinePat);
            return ;
        }
         
        Crud.VaccinePatCrud.Update(vaccinePat);
    }

    /**
    * 
    */
    public static void delete(long vaccinePatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccinePatNum);
            return ;
        }
         
        String command = "DELETE FROM vaccinepat WHERE VaccinePatNum = " + POut.long(vaccinePatNum);
        Db.nonQ(command);
        //Delete any attached observations.
        VaccineObses.deleteForVaccinePat(vaccinePatNum);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		
		///<summary>Gets one VaccinePat from the db.</summary>
		public static VaccinePat GetOne(long vaccinePatNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<VaccinePat>(MethodBase.GetCurrentMethod(),vaccinePatNum);
			}
			return Crud.VaccinePatCrud.SelectOne(vaccinePatNum);
		}
		*/