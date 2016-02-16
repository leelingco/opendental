//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cdcrec;
import OpenDentBusiness.DataCore;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PatRace;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Cache pattern only used for updates.
*/
public class Cdcrecs   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all Cdcrecs.</summary>
    		private static List<Cdcrec> listt;
    		///<summary>A list of all Cdcrecs.</summary>
    		public static List<Cdcrec> Listt{
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
    			string command="SELECT * FROM cdcrec ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="Cdcrec";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.CdcrecCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static long insert(Cdcrec cdcrec) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cdcrec.CdcrecNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cdcrec);
            return cdcrec.CdcrecNum;
        }
         
        return Crud.CdcrecCrud.Insert(cdcrec);
    }

    /**
    * 
    */
    public static void update(Cdcrec cdcrec) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cdcrec);
            return ;
        }
         
        Crud.CdcrecCrud.Update(cdcrec);
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
        String command = "SELECT CdcRecCode FROM cdcrec";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i].ItemArray[0].ToString());
        }
        return retVal;
    }

    /**
    * 
    */
    public static void truncateAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "TRUNCATE TABLE cdcrec";
        //Oracle compatible
        DataCore.nonQ(command);
    }

    public static String getByPatRace(PatRace patRace) throws Exception {
        String retval = "";
        switch(patRace)
        {
            case AfricanAmerican: 
                retval = "2054-5";
                break;
            case AmericanIndian: 
                //R3 BLACK OR AFRICAN AMERICAN
                retval = "1002-5";
                break;
            case Asian: 
                //R1 AMERICAN INDIAN OR ALASKA NATIVE
                retval = "2028-9";
                break;
            case HawaiiOrPacIsland: 
                //R2 ASIAN
                retval = "2076-8";
                break;
            case Hispanic: 
                //R4 NATIVE HAWAIIAN OR OTHER PACIFIC ISLANDER
                retval = "2135-2";
                break;
            case NotHispanic: 
                //E1 HISPANIC OR LATINO
                retval = "2186-5";
                break;
            case Other: 
                //E2 NOT HISPANIC OR LATINO
                retval = "2131-1";
                break;
            case White: 
                //R9 OTHER RACE
                retval = "2106-3";
                break;
        
        }
        return retval;
    }

}


//R5 WHITE
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Cdcrec> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<Cdcrec>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM cdcrec WHERE PatNum = "+POut.Long(patNum);
			return Crud.CdcrecCrud.SelectMany(command);
		}
		///<summary>Gets one Cdcrec from the db.</summary>
		public static Cdcrec GetOne(long cdcrecNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<Cdcrec>(MethodBase.GetCurrentMethod(),cdcrecNum);
			}
			return Crud.CdcrecCrud.SelectOne(cdcrecNum);
		}
		///<summary></summary>
		public static void Delete(long cdcrecNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),cdcrecNum);
				return;
			}
			string command= "DELETE FROM cdcrec WHERE CdcrecNum = "+POut.Long(cdcrecNum);
			Db.NonQ(command);
		}
		*/