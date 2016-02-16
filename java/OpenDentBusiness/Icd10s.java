//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Icd10s   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all Icd10s.</summary>
    		private static List<Icd10> listt;
    		///<summary>A list of all Icd10s.</summary>
    		public static List<Icd10> Listt{
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
    			string command="SELECT * FROM icd10 ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="Icd10";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.Icd10Crud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static long insert(Icd10 icd10) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            icd10.Icd10Num = Meth.GetLong(MethodBase.GetCurrentMethod(), icd10);
            return icd10.Icd10Num;
        }
         
        return Crud.Icd10Crud.Insert(icd10);
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
        String command = "SELECT Icd10Code FROM icd10";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Gets one ICD10 object directly from the database by CodeValue.  If code does not exist, returns null.
    */
    public static Icd10 getByCode(String Icd10Code) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Icd10>GetObject(MethodBase.GetCurrentMethod(), Icd10Code);
        }
         
        String command = "SELECT * FROM icd10 WHERE Icd10Code='" + POut.string(Icd10Code) + "'";
        return Crud.Icd10Crud.SelectOne(command);
    }

    /**
    * Directly from db.
    */
    public static boolean codeExists(String Icd10Code) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), Icd10Code);
        }
         
        String command = "SELECT COUNT(*) FROM icd10 WHERE Icd10Code='" + POut.string(Icd10Code) + "'";
        String count = Db.getCount(command);
        if (StringSupport.equals(count, "0"))
        {
            return false;
        }
         
        return true;
    }

    public static List<Icd10> getBySearchText(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Icd10>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String[] searchTokens = searchText.Split(' ');
        String command = "SELECT * FROM icd10 ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            command += (i == 0 ? "WHERE " : "AND ") + "(Icd10Code LIKE '%" + POut.String(searchTokens[i]) + "%' OR Description LIKE '%" + POut.String(searchTokens[i]) + "%') ";
        }
        return Crud.Icd10Crud.SelectMany(command);
    }

    /**
    * Gets one Icd10 from the db.
    */
    public static Icd10 getOne(long icd10Num) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Icd10>GetObject(MethodBase.GetCurrentMethod(), icd10Num);
        }
         
        return Crud.Icd10Crud.SelectOne(icd10Num);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Icd10> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Icd10>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM icd10 WHERE PatNum = "+POut.Long(patNum);
			return Crud.Icd10Crud.SelectMany(command);
		}
		///<summary></summary>
		public static void Update(Icd10 icd10){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),icd10);
				return;
			}
			Crud.Icd10Crud.Update(icd10);
		}
		///<summary></summary>
		public static void Delete(long icd10Num) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),icd10Num);
				return;
			}
			string command= "DELETE FROM icd10 WHERE Icd10Num = "+POut.Long(icd10Num);
			Db.NonQ(command);
		}
		*/