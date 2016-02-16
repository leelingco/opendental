//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLabResultsCopyTo;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabResultsCopyTos   
{
    /**
    * 
    */
    public static List<EhrLabResultsCopyTo> getForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabResultsCopyTo>>GetObject(MethodBase.GetCurrentMethod(), ehrLabNum);
        }
         
        String command = "SELECT * FROM ehrlabresultscopyto WHERE EhrLabNum = " + POut.long(ehrLabNum);
        return Crud.EhrLabResultsCopyToCrud.SelectMany(command);
    }

    /**
    * Deletes notes for lab results too.
    */
    public static void deleteForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrLabNum);
            return ;
        }
         
        String command = "DELETE FROM ehrlabresultscopyto WHERE EhrLabNum = " + POut.long(ehrLabNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(EhrLabResultsCopyTo ehrLabResultsCopyTo) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabResultsCopyTo.EhrLabResultsCopyToNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabResultsCopyTo);
            return ehrLabResultsCopyTo.EhrLabResultsCopyToNum;
        }
         
        return Crud.EhrLabResultsCopyToCrud.Insert(ehrLabResultsCopyTo);
    }

}


//If this table type will exist as cached data, uncomment the CachePattern region below and edit.
/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all EhrLabResultsCopyTos.</summary>
		private static List<EhrLabResultsCopyTo> listt;
		///<summary>A list of all EhrLabResultsCopyTos.</summary>
		public static List<EhrLabResultsCopyTo> Listt{
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
			string command="SELECT * FROM ehrlabresultscopyto ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="EhrLabResultsCopyTo";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.EhrLabResultsCopyToCrud.TableToList(table);
		}
		#endregion
		*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrLabResultsCopyTo> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrLabResultsCopyTo>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrlabresultscopyto WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrLabResultsCopyToCrud.SelectMany(command);
		}
		///<summary>Gets one EhrLabResultsCopyTo from the db.</summary>
		public static EhrLabResultsCopyTo GetOne(long ehrLabResultsCopyToNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabResultsCopyTo>(MethodBase.GetCurrentMethod(),ehrLabResultsCopyToNum);
			}
			return Crud.EhrLabResultsCopyToCrud.SelectOne(ehrLabResultsCopyToNum);
		}
		///<summary></summary>
		public static void Update(EhrLabResultsCopyTo ehrLabResultsCopyTo){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabResultsCopyTo);
				return;
			}
			Crud.EhrLabResultsCopyToCrud.Update(ehrLabResultsCopyTo);
		}
		///<summary></summary>
		public static void Delete(long ehrLabResultsCopyToNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabResultsCopyToNum);
				return;
			}
			string command= "DELETE FROM ehrlabresultscopyto WHERE EhrLabResultsCopyToNum = "+POut.Long(ehrLabResultsCopyToNum);
			Db.NonQ(command);
		}
		*/