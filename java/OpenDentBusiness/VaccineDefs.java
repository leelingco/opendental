//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.VaccineDef;

/**
* 
*/
public class VaccineDefs   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all VaccineDefs.
    */
    private static List<VaccineDef> listt = new List<VaccineDef>();
    /**
    * A list of all VaccineDefs.
    */
    public static List<VaccineDef> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<VaccineDef> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM vaccinedef ORDER BY CVXCode";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "VaccineDef";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.VaccineDefCrud.TableToList(table);
    }

    /**
    * Gets one VaccineDef from the db.
    */
    public static VaccineDef getOne(long vaccineDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<VaccineDef>GetObject(MethodBase.GetCurrentMethod(), vaccineDefNum);
        }
         
        return Crud.VaccineDefCrud.SelectOne(vaccineDefNum);
    }

    /**
    * 
    */
    public static long insert(VaccineDef vaccineDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            vaccineDef.VaccineDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), vaccineDef);
            return vaccineDef.VaccineDefNum;
        }
         
        return Crud.VaccineDefCrud.Insert(vaccineDef);
    }

    /**
    * 
    */
    public static void update(VaccineDef vaccineDef) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccineDef);
            return ;
        }
         
        Crud.VaccineDefCrud.Update(vaccineDef);
    }

    /**
    * 
    */
    public static void delete(long vaccineDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), vaccineDefNum);
            return ;
        }
         
        //validation
        String command = new String();
        command = "SELECT COUNT(*) FROM VaccinePat WHERE VaccineDefNum=" + POut.long(vaccineDefNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormDrugUnitEdit","Cannot delete: VaccineDef is in use by VaccinePat."));
        }
         
        command = "DELETE FROM vaccinedef WHERE VaccineDefNum = " + POut.long(vaccineDefNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<VaccineDef> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<VaccineDef>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM vaccinedef WHERE PatNum = "+POut.Long(patNum);
			return Crud.VaccineDefCrud.SelectMany(command);
		}
		*/