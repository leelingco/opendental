//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cvx;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Cvxs   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all Cvxs.</summary>
    		private static List<Cvx> listt;
    		///<summary>A list of all Cvxs.</summary>
    		public static List<Cvx> Listt{
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
    			string command="SELECT * FROM cvx ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="Cvx";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.CvxCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static long insert(Cvx cvx) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cvx.CvxNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cvx);
            return cvx.CvxNum;
        }
         
        return Crud.CvxCrud.Insert(cvx);
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
        String command = "SELECT CvxCode FROM cvx";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i][0].ToString());
        }
        return retVal;
    }

    /**
    * Gets one Cvx object directly from the database by CodeValue.  If code does not exist, returns null.
    */
    public static Cvx getByCode(String cvxCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Cvx>GetObject(MethodBase.GetCurrentMethod(), cvxCode);
        }
         
        String command = "SELECT * FROM Cvx WHERE CvxCode='" + POut.string(cvxCode) + "'";
        return Crud.CvxCrud.SelectOne(command);
    }

    /**
    * Gets one Cvx by CvxNum directly from the db.
    */
    public static Cvx getOneFromDb(String cvxCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Cvx>GetObject(MethodBase.GetCurrentMethod(), cvxCode);
        }
         
        String command = "SELECT * FROM cvx WHERE CvxCode='" + POut.string(cvxCode) + "'";
        return Crud.CvxCrud.SelectOne(command);
    }

    /**
    * Directly from db.
    */
    public static boolean codeExists(String cvxCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), cvxCode);
        }
         
        String command = "SELECT COUNT(*) FROM cvx WHERE CvxCode='" + POut.string(cvxCode) + "'";
        String count = Db.getCount(command);
        if (StringSupport.equals(count, "0"))
        {
            return false;
        }
         
        return true;
    }

    public static long getCodeCount() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<int>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT COUNT(*) FROM cvx";
        return PIn.long(Db.getCount(command));
    }

    public static List<Cvx> getBySearchText(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Cvx>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String[] searchTokens = searchText.Split(' ');
        String command = "SELECT * FROM cvx ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            command += (i == 0 ? "WHERE " : "AND ") + "(CvxCode LIKE '%" + POut.String(searchTokens[i]) + "%' OR Description LIKE '%" + POut.String(searchTokens[i]) + "%') ";
        }
        return Crud.CvxCrud.SelectMany(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Cvx> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Cvx>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM cvx WHERE PatNum = "+POut.Long(patNum);
			return Crud.CvxCrud.SelectMany(command);
		}
		///<summary>Gets one Cvx from the db.</summary>
		public static Cvx GetOne(long cvxNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<Cvx>(MethodBase.GetCurrentMethod(),cvxNum);
			}
			return Crud.CvxCrud.SelectOne(cvxNum);
		}
		///<summary></summary>
		public static void Update(Cvx cvx){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),cvx);
				return;
			}
			Crud.CvxCrud.Update(cvx);
		}
		///<summary></summary>
		public static void Delete(long cvxNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),cvxNum);
				return;
			}
			string command= "DELETE FROM cvx WHERE CvxNum = "+POut.Long(cvxNum);
			Db.NonQ(command);
		}
		*/