//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.Hcpcs;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Hcpcses   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all Hcpcses.</summary>
    		private static List<Hcpcs> listt;
    		///<summary>A list of all Hcpcses.</summary>
    		public static List<Hcpcs> Listt{
    			get {
    				if(listt==null) {
    					RefreshCache();
    				}
    				return listt;
    			}
    			set {
    				listt=value;
    			}
    		}
    		///<summary></summary>
    		public static DataTable RefreshCache(){
    			//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    			string command="SELECT * FROM hcpcs";
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="Hcpcs";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.HcpcsCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static long insert(Hcpcs hcpcs) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            hcpcs.HcpcsNum = Meth.GetLong(MethodBase.GetCurrentMethod(), hcpcs);
            return hcpcs.HcpcsNum;
        }
         
        return Crud.HcpcsCrud.Insert(hcpcs);
    }

    /**
    * Returns a list of just the codes for use in update or insert logic.
    */
    public static List<String> getAllCodes() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<String>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        List<String> retVal = new List<String>();
        String command = "SELECT HcpcsCode FROM hcpcs";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Returns the Hcpcs of the code passed in by looking in cache.  If code does not exist, returns null.
    */
    public static Hcpcs getByCode(String hcpcsCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Hcpcs>GetObject(MethodBase.GetCurrentMethod(), hcpcsCode);
        }
         
        String command = "SELECT * FROM hcpcs WHERE HcpcsCode='" + POut.string(hcpcsCode) + "'";
        return Crud.HcpcsCrud.SelectOne(command);
    }

    /**
    * Directly from db.
    */
    public static boolean codeExists(String hcpcsCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), hcpcsCode);
        }
         
        String command = "SELECT COUNT(*) FROM hcpcs WHERE HcpcsCode='" + POut.string(hcpcsCode) + "'";
        String count = Db.getCount(command);
        if (StringSupport.equals(count, "0"))
        {
            return false;
        }
         
        return true;
    }

    public static List<Hcpcs> getBySearchText(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Hcpcs>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String[] searchTokens = searchText.Split(' ');
        String command = "SELECT * FROM hcpcs ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            command += (i == 0 ? "WHERE " : "AND ") + "(HcpcsCode LIKE '%" + POut.String(searchTokens[i]) + "%' OR DescriptionShort LIKE '%" + POut.String(searchTokens[i]) + "%') ";
        }
        return Crud.HcpcsCrud.SelectMany(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Hcpcs> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Hcpcs>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM hcpcs WHERE PatNum = "+POut.Long(patNum);
			return Crud.HcpcsCrud.SelectMany(command);
		}
		///<summary></summary>
		public static void Update(Hcpcs hcpcs){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),hcpcs);
				return;
			}
			Crud.HcpcsCrud.Update(hcpcs);
		}
		///<summary></summary>
		public static void Delete(long hcpcsNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),hcpcsNum);
				return;
			}
			string command= "DELETE FROM hcpcs WHERE HcpcsNum = "+POut.Long(hcpcsNum);
			Db.NonQ(command);
		}
		*/