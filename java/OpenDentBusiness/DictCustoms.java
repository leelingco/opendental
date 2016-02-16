//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.DictCustom;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DictCustoms   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all DictCustoms.
    */
    private static List<DictCustom> listt = new List<DictCustom>();
    /**
    * A list of all DictCustoms.
    */
    public static List<DictCustom> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<DictCustom> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM dictcustom ORDER BY WordText";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "DictCustom";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.DictCustomCrud.TableToList(table);
    }

    /**
    * 
    */
    public static long insert(DictCustom dictCustom) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            dictCustom.DictCustomNum = Meth.GetLong(MethodBase.GetCurrentMethod(), dictCustom);
            return dictCustom.DictCustomNum;
        }
         
        return Crud.DictCustomCrud.Insert(dictCustom);
    }

    /**
    * 
    */
    public static void update(DictCustom dictCustom) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dictCustom);
            return ;
        }
         
        Crud.DictCustomCrud.Update(dictCustom);
    }

    /**
    * 
    */
    public static void delete(long dictCustomNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), dictCustomNum);
            return ;
        }
         
        Crud.DictCustomCrud.Delete(dictCustomNum);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<DictCustom> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<DictCustom>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM dictcustom WHERE PatNum = "+POut.Long(patNum);
			return Crud.DictCustomCrud.SelectMany(command);
		}
		
		///<summary>Gets one DictCustom from the db.</summary>
		public static DictCustom GetOne(long dictCustomNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<DictCustom>(MethodBase.GetCurrentMethod(),dictCustomNum);
			}
			return Crud.DictCustomCrud.SelectOne(dictCustomNum);
		}
		 
		
		*/