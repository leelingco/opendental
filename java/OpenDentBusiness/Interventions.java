//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Intervention;
import OpenDentBusiness.InterventionCodeSet;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Interventions   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all Interventions.</summary>
    		private static List<Intervention> listt;
    		///<summary>A list of all Interventions.</summary>
    		public static List<Intervention> Listt{
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
    			string command="SELECT * FROM intervention ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="Intervention";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.InterventionCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static long insert(Intervention intervention) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            intervention.InterventionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), intervention);
            return intervention.InterventionNum;
        }
         
        return Crud.InterventionCrud.Insert(intervention);
    }

    /**
    * 
    */
    public static void update(Intervention intervention) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), intervention);
            return ;
        }
         
        Crud.InterventionCrud.Update(intervention);
    }

    /**
    * 
    */
    public static void delete(long interventionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), interventionNum);
            return ;
        }
         
        String command = "DELETE FROM intervention WHERE InterventionNum = " + POut.long(interventionNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static List<Intervention> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Intervention>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM intervention WHERE PatNum = " + POut.long(patNum);
        return Crud.InterventionCrud.SelectMany(command);
    }

    public static List<Intervention> refresh(long patNum, InterventionCodeSet codeSet) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Intervention>>GetObject(MethodBase.GetCurrentMethod(), codeSet);
        }
         
        String command = "SELECT * FROM intervention WHERE PatNum = " + POut.long(patNum) + " AND CodeSet = " + POut.int(((Enum)codeSet).ordinal());
        return Crud.InterventionCrud.SelectMany(command);
    }

    /**
    * Gets one Intervention from the db.
    */
    public static Intervention getOne(long interventionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Intervention>GetObject(MethodBase.GetCurrentMethod(), interventionNum);
        }
         
        return Crud.InterventionCrud.SelectOne(interventionNum);
    }

}


