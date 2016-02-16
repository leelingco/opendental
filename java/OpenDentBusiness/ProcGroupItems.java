//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcGroupItem;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* In ProcGroupItems the ProcNum is a procedure in a group and GroupNum is the group the procedure is in. GroupNum is a FK to the Procedure table. There is a special type of procedure with the procedure code "~GRP~" that is used to indicate this is a group Procedure.
*/
public class ProcGroupItems   
{
    /**
    * 
    */
    public static List<ProcGroupItem> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ProcGroupItem>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM procgroupitem WHERE GroupNum IN (SELECT ProcNum FROM procedurelog WHERE PatNum = " + POut.long(patNum) + ")";
        return Crud.ProcGroupItemCrud.SelectMany(command);
    }

    /**
    * Gets all the ProcGroupItems for a Procedure Group.
    */
    public static List<ProcGroupItem> getForGroup(long groupNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ProcGroupItem>>GetObject(MethodBase.GetCurrentMethod(), groupNum);
        }
         
        String command = "SELECT * FROM procgroupitem WHERE GroupNum = " + POut.long(groupNum) + " ORDER BY ProcNum ASC";
        return Crud.ProcGroupItemCrud.SelectMany(command);
    }

    //Order is important for creating signature key in FormProcGroup.cs.
    /**
    * Adds a procedure to a group.
    */
    public static long insert(ProcGroupItem procGroupItem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            procGroupItem.ProcGroupItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), procGroupItem);
            return procGroupItem.ProcGroupItemNum;
        }
         
        return Crud.ProcGroupItemCrud.Insert(procGroupItem);
    }

    /**
    * Deletes a ProcGroupItem based on its procGroupItemNum.
    */
    public static void delete(long procGroupItemNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procGroupItemNum);
            return ;
        }
         
        String command = "DELETE FROM procgroupitem WHERE ProcGroupItemNum = " + POut.long(procGroupItemNum);
        Db.nonQ(command);
    }

}


/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all ProcGroupItems.</summary>
		private static List<ProcGroupItem> listt;
		///<summary>A list of all ProcGroupItems.</summary>
		public static List<ProcGroupItem> Listt{
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
			string command="SELECT * FROM procgroupitem ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="ProcGroupItem";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.ProcGroupItemCrud.TableToList(table);
		}
		#endregion*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		
		///<summary>Gets one ProcGroupItem from the db.</summary>
		public static ProcGroupItem GetOne(long procGroupItemNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<ProcGroupItem>(MethodBase.GetCurrentMethod(),procGroupItemNum);
			}
			return Crud.ProcGroupItemCrud.SelectOne(procGroupItemNum);
		}
		///<summary></summary>
		public static void Update(ProcGroupItem procGroupItem){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),procGroupItem);
				return;
			}
			Crud.ProcGroupItemCrud.Update(procGroupItem);
		}
		///<summary></summary>
		public static void Delete(long procGroupItemNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),procGroupItemNum);
				return;
			}
			string command= "DELETE FROM procgroupitem WHERE ProcGroupItemNum = "+POut.Long(procGroupItemNum);
			Db.NonQ(command);
		}
		*/