//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLabSpecimenCondition;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabSpecimenConditions   
{
    /**
    * 
    */
    public static List<EhrLabSpecimenCondition> getForEhrLabSpecimen(long ehrLabSpecimenNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabSpecimenCondition>>GetObject(MethodBase.GetCurrentMethod(), ehrLabSpecimenNum);
        }
         
        String command = "SELECT * FROM ehrlabspecimencondition WHERE EhrLabSpecimenNum=" + POut.long(ehrLabSpecimenNum);
        return Crud.EhrLabSpecimenConditionCrud.SelectMany(command);
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
         
        String command = "DELETE FROM ehrlabspecimencondition WHERE EhrLabSpecimenNum IN (SELECT EhrLabSpecimenNum FROM ehrlabspecimen WHERE EhrLabNum=" + POut.long(ehrLabNum) + ")";
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
         
        String command = "DELETE FROM ehrlabspecimencondition WHERE EhrLabSpecimenNum=" + POut.long(ehrLabSpecimenNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(EhrLabSpecimenCondition ehrLabSpecimenCondition) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabSpecimenCondition.EhrLabSpecimenConditionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabSpecimenCondition);
            return ehrLabSpecimenCondition.EhrLabSpecimenConditionNum;
        }
         
        return Crud.EhrLabSpecimenConditionCrud.Insert(ehrLabSpecimenCondition);
    }

}


//If this table type will exist as cached data, uncomment the CachePattern region below and edit.
/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all EhrLabSpecimenConditions.</summary>
		private static List<EhrLabSpecimenCondition> listt;
		///<summary>A list of all EhrLabSpecimenConditions.</summary>
		public static List<EhrLabSpecimenCondition> Listt{
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
			string command="SELECT * FROM ehrlabspecimencondition ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="EhrLabSpecimenCondition";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.EhrLabSpecimenConditionCrud.TableToList(table);
		}
		#endregion
		*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrLabSpecimenCondition> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrLabSpecimenCondition>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrlabspecimencondition WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrLabSpecimenConditionCrud.SelectMany(command);
		}
		///<summary>Gets one EhrLabSpecimenCondition from the db.</summary>
		public static EhrLabSpecimenCondition GetOne(long ehrLabSpecimenConditionNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabSpecimenCondition>(MethodBase.GetCurrentMethod(),ehrLabSpecimenConditionNum);
			}
			return Crud.EhrLabSpecimenConditionCrud.SelectOne(ehrLabSpecimenConditionNum);
		}
		///<summary></summary>
		public static long Insert(EhrLabSpecimenCondition ehrLabSpecimenCondition){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				ehrLabSpecimenCondition.EhrLabSpecimenConditionNum=Meth.GetLong(MethodBase.GetCurrentMethod(),ehrLabSpecimenCondition);
				return ehrLabSpecimenCondition.EhrLabSpecimenConditionNum;
			}
			return Crud.EhrLabSpecimenConditionCrud.Insert(ehrLabSpecimenCondition);
		}
		///<summary></summary>
		public static void Update(EhrLabSpecimenCondition ehrLabSpecimenCondition){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabSpecimenCondition);
				return;
			}
			Crud.EhrLabSpecimenConditionCrud.Update(ehrLabSpecimenCondition);
		}
		///<summary></summary>
		public static void Delete(long ehrLabSpecimenConditionNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabSpecimenConditionNum);
				return;
			}
			string command= "DELETE FROM ehrlabspecimencondition WHERE EhrLabSpecimenConditionNum = "+POut.Long(ehrLabSpecimenConditionNum);
			Db.NonQ(command);
		}
		*/