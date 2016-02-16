//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLabSpecimen;
import OpenDentBusiness.EhrLabSpecimenConditions;
import OpenDentBusiness.EhrLabSpecimenRejectReasons;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabSpecimens   
{
    /**
    * 
    */
    public static List<EhrLabSpecimen> getForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabSpecimen>>GetObject(MethodBase.GetCurrentMethod(), ehrLabNum);
        }
         
        String command = "SELECT * FROM ehrlabspecimen WHERE EhrLabNum = " + POut.long(ehrLabNum);
        return Crud.EhrLabSpecimenCrud.SelectMany(command);
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
         
        EhrLabSpecimenConditions.deleteForLab(ehrLabNum);
        EhrLabSpecimenRejectReasons.deleteForLab(ehrLabNum);
        String command = "DELETE FROM ehrlabspecimen WHERE EhrLabNum = " + POut.long(ehrLabNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static EhrLabSpecimen insertItem(EhrLabSpecimen ehrLabSpecimen) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabSpecimen.EhrLabSpecimenNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabSpecimen);
            return ehrLabSpecimen;
        }
         
        ehrLabSpecimen.EhrLabNum = Crud.EhrLabSpecimenCrud.Insert(ehrLabSpecimen);
        for (int i = 0;i < ehrLabSpecimen.getListEhrLabSpecimenCondition().Count;i++)
        {
            ehrLabSpecimen.getListEhrLabSpecimenCondition()[i].EhrLabSpecimenNum = ehrLabSpecimen.EhrLabSpecimenNum;
            EhrLabSpecimenConditions.Insert(ehrLabSpecimen.getListEhrLabSpecimenCondition()[i]);
        }
        for (int i = 0;i < ehrLabSpecimen.getListEhrLabSpecimenRejectReason().Count;i++)
        {
            ehrLabSpecimen.getListEhrLabSpecimenRejectReason()[i].EhrLabSpecimenNum = ehrLabSpecimen.EhrLabSpecimenNum;
            EhrLabSpecimenRejectReasons.Insert(ehrLabSpecimen.getListEhrLabSpecimenRejectReason()[i]);
        }
        return ehrLabSpecimen;
    }

}


//If this table type will exist as cached data, uncomment the CachePattern region below and edit.
/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all EhrLabSpecimens.</summary>
		private static List<EhrLabSpecimen> listt;
		///<summary>A list of all EhrLabSpecimens.</summary>
		public static List<EhrLabSpecimen> Listt{
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
			string command="SELECT * FROM ehrlabspecimen ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="EhrLabSpecimen";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.EhrLabSpecimenCrud.TableToList(table);
		}
		#endregion
		*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrLabSpecimen> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrLabSpecimen>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrlabspecimen WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrLabSpecimenCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(EhrLabSpecimen ehrLabSpecimen) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				ehrLabSpecimen.EhrLabSpecimenNum=Meth.GetLong(MethodBase.GetCurrentMethod(),ehrLabSpecimen);
				return ehrLabSpecimen.EhrLabSpecimenNum;
			}
			return Crud.EhrLabSpecimenCrud.Insert(ehrLabSpecimen);
		}
		///<summary>Gets one EhrLabSpecimen from the db.</summary>
		public static EhrLabSpecimen GetOne(long ehrLabSpecimenNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabSpecimen>(MethodBase.GetCurrentMethod(),ehrLabSpecimenNum);
			}
			return Crud.EhrLabSpecimenCrud.SelectOne(ehrLabSpecimenNum);
		}
		///<summary></summary>
		public static long Insert(EhrLabSpecimen ehrLabSpecimen){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				ehrLabSpecimen.EhrLabSpecimenNum=Meth.GetLong(MethodBase.GetCurrentMethod(),ehrLabSpecimen);
				return ehrLabSpecimen.EhrLabSpecimenNum;
			}
			return Crud.EhrLabSpecimenCrud.Insert(ehrLabSpecimen);
		}
		///<summary></summary>
		public static void Update(EhrLabSpecimen ehrLabSpecimen){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabSpecimen);
				return;
			}
			Crud.EhrLabSpecimenCrud.Update(ehrLabSpecimen);
		}
		///<summary></summary>
		public static void Delete(long ehrLabSpecimenNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabSpecimenNum);
				return;
			}
			string command= "DELETE FROM ehrlabspecimen WHERE EhrLabSpecimenNum = "+POut.Long(ehrLabSpecimenNum);
			Db.NonQ(command);
		}
		*/