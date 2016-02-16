//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.MapArea;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class MapAreas   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below and edit.
    /*
    		#region CachePattern
    		//This region can be eliminated if this is not a table type with cached data.
    		//If leaving this region in place, be sure to add RefreshCache and FillCache 
    		//to the Cache.cs file with all the other Cache types.
    		///<summary>A list of all MapAreas.</summary>
    		private static List<MapArea> listt;
    		///<summary>A list of all MapAreas.</summary>
    		public static List<MapArea> Listt{
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
    			string command="SELECT * FROM maparea ORDER BY ItemOrder";//stub query probably needs to be changed
    			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    			table.TableName="MapArea";
    			FillCache(table);
    			return table;
    		}
    		///<summary></summary>
    		public static void FillCache(DataTable table){
    			//No need to check RemotingRole; no call to db.
    			listt=Crud.MapAreaCrud.TableToList(table);
    		}
    		#endregion
    		*/
    /**
    * 
    */
    public static List<MapArea> refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MapArea>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM maparea";
        return Crud.MapAreaCrud.SelectMany(command);
    }

    /*		
    		///<summary>Gets one MapArea from the db.</summary>
    		public static MapArea GetOne(long mapAreaNum){
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
    				return Meth.GetObject<MapArea>(MethodBase.GetCurrentMethod(),mapAreaNum);
    			}
    			return Crud.MapAreaCrud.SelectOne(mapAreaNum);
    		}
    		*/
    /**
    * 
    */
    public static long insert(MapArea mapArea) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            mapArea.MapAreaNum = Meth.GetLong(MethodBase.GetCurrentMethod(), mapArea);
            return mapArea.MapAreaNum;
        }
         
        return Crud.MapAreaCrud.Insert(mapArea);
    }

    /**
    * 
    */
    public static void update(MapArea mapArea) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mapArea);
            return ;
        }
         
        Crud.MapAreaCrud.Update(mapArea);
    }

    /**
    * 
    */
    public static void delete(long mapAreaNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mapAreaNum);
            return ;
        }
         
        String command = "DELETE FROM maparea WHERE MapAreaNum = " + POut.long(mapAreaNum);
        Db.nonQ(command);
    }

}


