//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLabSpecimenRejectReason;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabSpecimenRejectReasons   
{
    /**
    * 
    */
    public static List<EhrLabSpecimenRejectReason> getForEhrLabSpecimen(long ehrLabSpecimenNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabSpecimenRejectReason>>GetObject(MethodBase.GetCurrentMethod(), ehrLabSpecimenNum);
        }
         
        String command = "SELECT * FROM ehrlabspecimenrejectreason WHERE EhrLabSpecimenNum = " + POut.long(ehrLabSpecimenNum);
        return Crud.EhrLabSpecimenRejectReasonCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static void deleteForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrLabNum);
            return ;
        }
         
        String command = "DELETE FROM ehrlabspecimenrejectreason WHERE EhrLabSpecimenNum IN (SELECT EhrLabSpecimenNum FROM ehrlabspecimen WHERE EhrLabNum=" + POut.long(ehrLabNum) + ")";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void deleteForLabSpecimen(long ehrLabSpecimenNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrLabSpecimenNum);
            return ;
        }
         
        String command = "DELETE FROM ehrlabspecimenrejectreason WHERE EhrLabSpecimenNum=" + POut.long(ehrLabSpecimenNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(EhrLabSpecimenRejectReason ehrLabSpecimenRejectReason) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabSpecimenRejectReason.EhrLabSpecimenRejectReasonNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabSpecimenRejectReason);
            return ehrLabSpecimenRejectReason.EhrLabSpecimenRejectReasonNum;
        }
         
        return Crud.EhrLabSpecimenRejectReasonCrud.Insert(ehrLabSpecimenRejectReason);
    }

}


//If this table type will exist as cached data, uncomment the CachePattern region below and edit.
/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all EhrLabSpecimenRejectReasons.</summary>
		private static List<EhrLabSpecimenRejectReason> listt;
		///<summary>A list of all EhrLabSpecimenRejectReasons.</summary>
		public static List<EhrLabSpecimenRejectReason> Listt{
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
			string command="SELECT * FROM ehrlabspecimenrejectreason ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="EhrLabSpecimenRejectReason";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.EhrLabSpecimenRejectReasonCrud.TableToList(table);
		}
		#endregion
		*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrLabSpecimenRejectReason> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrLabSpecimenRejectReason>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrlabspecimenrejectreason WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrLabSpecimenRejectReasonCrud.SelectMany(command);
		}
		///<summary>Gets one EhrLabSpecimenRejectReason from the db.</summary>
		public static EhrLabSpecimenRejectReason GetOne(long ehrLabSpecimenRejectReasonNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabSpecimenRejectReason>(MethodBase.GetCurrentMethod(),ehrLabSpecimenRejectReasonNum);
			}
			return Crud.EhrLabSpecimenRejectReasonCrud.SelectOne(ehrLabSpecimenRejectReasonNum);
		}
		///<summary></summary>
		public static long Insert(EhrLabSpecimenRejectReason ehrLabSpecimenRejectReason){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				ehrLabSpecimenRejectReason.EhrLabSpecimenRejectReasonNum=Meth.GetLong(MethodBase.GetCurrentMethod(),ehrLabSpecimenRejectReason);
				return ehrLabSpecimenRejectReason.EhrLabSpecimenRejectReasonNum;
			}
			return Crud.EhrLabSpecimenRejectReasonCrud.Insert(ehrLabSpecimenRejectReason);
		}
		///<summary></summary>
		public static void Update(EhrLabSpecimenRejectReason ehrLabSpecimenRejectReason){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabSpecimenRejectReason);
				return;
			}
			Crud.EhrLabSpecimenRejectReasonCrud.Update(ehrLabSpecimenRejectReason);
		}
		///<summary></summary>
		public static void Delete(long ehrLabSpecimenRejectReasonNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabSpecimenRejectReasonNum);
				return;
			}
			string command= "DELETE FROM ehrlabspecimenrejectreason WHERE EhrLabSpecimenRejectReasonNum = "+POut.Long(ehrLabSpecimenRejectReasonNum);
			Db.NonQ(command);
		}
		*/