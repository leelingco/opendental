//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.DrugManufacturer;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DrugManufacturers   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types. (Done.)
    /**
    * A list of all DrugManufacturers.
    */
    private static List<DrugManufacturer> listt = new List<DrugManufacturer>();
    /**
    * A list of all DrugManufacturers.
    */
    public static List<DrugManufacturer> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<DrugManufacturer> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM drugmanufacturer ORDER BY ManufacturerCode";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "DrugManufacturer";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.DrugManufacturerCrud.TableToList(table);
    }

    /**
    * Gets one DrugManufacturer from the db.
    */
    public static DrugManufacturer getOne(long drugManufacturerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<DrugManufacturer>GetObject(MethodBase.GetCurrentMethod(), drugManufacturerNum);
        }
         
        return Crud.DrugManufacturerCrud.SelectOne(drugManufacturerNum);
    }

    /**
    * 
    */
    public static long insert(DrugManufacturer drugManufacturer) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            drugManufacturer.DrugManufacturerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), drugManufacturer);
            return drugManufacturer.DrugManufacturerNum;
        }
         
        return Crud.DrugManufacturerCrud.Insert(drugManufacturer);
    }

    /**
    * 
    */
    public static void update(DrugManufacturer drugManufacturer) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), drugManufacturer);
            return ;
        }
         
        Crud.DrugManufacturerCrud.Update(drugManufacturer);
    }

    /**
    * 
    */
    public static void delete(long drugManufacturerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), drugManufacturerNum);
            return ;
        }
         
        //validation
        String command = new String();
        command = "SELECT COUNT(*) FROM VaccineDef WHERE drugManufacturerNum=" + POut.long(drugManufacturerNum);
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("FormDrugUnitEdit","Cannot delete: DrugManufacturer is in use by VaccineDef."));
        }
         
        command = "DELETE FROM drugmanufacturer WHERE DrugManufacturerNum = " + POut.long(drugManufacturerNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<DrugManufacturer> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<DrugManufacturer>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM drugmanufacturer WHERE PatNum = "+POut.Long(patNum);
			return Crud.DrugManufacturerCrud.SelectMany(command);
		}
		*/