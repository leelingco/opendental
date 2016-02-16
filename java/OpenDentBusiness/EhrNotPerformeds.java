//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrNotPerformed;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrNotPerformeds   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all EhrNotPerformeds.</summary>
    		private static List<EhrNotPerformed> listt;
    		///<summary>A list of all EhrNotPerformeds.</summary>
    		public static List<EhrNotPerformed> Listt{
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
    			string command="SELECT * FROM ehrnotperformed ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="EhrNotPerformed";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.EhrNotPerformedCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static List<EhrNotPerformed> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrNotPerformed>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM ehrnotperformed WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateEntry";
        return Crud.EhrNotPerformedCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EhrNotPerformed ehrNotPerformed) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrNotPerformed.EhrNotPerformedNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrNotPerformed);
            return ehrNotPerformed.EhrNotPerformedNum;
        }
         
        return Crud.EhrNotPerformedCrud.Insert(ehrNotPerformed);
    }

    /**
    * 
    */
    public static void update(EhrNotPerformed ehrNotPerformed) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrNotPerformed);
            return ;
        }
         
        Crud.EhrNotPerformedCrud.Update(ehrNotPerformed);
    }

    /**
    * 
    */
    public static void delete(long ehrNotPerformedNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrNotPerformedNum);
            return ;
        }
         
        String command = "DELETE FROM ehrnotperformed WHERE EhrNotPerformedNum = " + POut.long(ehrNotPerformedNum);
        Db.nonQ(command);
    }

    /**
    * Gets one EhrNotPerformed from the db.
    */
    public static EhrNotPerformed getOne(long ehrNotPerformedNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EhrNotPerformed>GetObject(MethodBase.GetCurrentMethod(), ehrNotPerformedNum);
        }
         
        return Crud.EhrNotPerformedCrud.SelectOne(ehrNotPerformedNum);
    }

}


