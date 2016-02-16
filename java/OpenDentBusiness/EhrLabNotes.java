//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrLabNote;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrLabNotes   
{
    /**
    * 
    */
    public static List<EhrLabNote> getForLab(long ehrLabNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabNote>>GetObject(MethodBase.GetCurrentMethod(), ehrLabNum);
        }
         
        String command = "SELECT * FROM ehrlabnote WHERE EhrLabNum = " + POut.long(ehrLabNum) + " AND EhrLabResultNum=0";
        return Crud.EhrLabNoteCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static List<EhrLabNote> getForLabResult(long ehrLabResultNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrLabNote>>GetObject(MethodBase.GetCurrentMethod(), ehrLabResultNum);
        }
         
        String command = "SELECT * FROM ehrlabnote WHERE EhrLabResultNum=" + POut.long(ehrLabResultNum);
        return Crud.EhrLabNoteCrud.SelectMany(command);
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
         
        String command = "DELETE FROM ehrlabnote WHERE EhrLabNum = " + POut.long(ehrLabNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long insert(EhrLabNote ehrLabNote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrLabNote.EhrLabNoteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrLabNote);
            return ehrLabNote.EhrLabNoteNum;
        }
         
        return Crud.EhrLabNoteCrud.Insert(ehrLabNote);
    }

}


//If this table type will exist as cached data, uncomment the CachePattern region below and edit.
/*
		#region CachePattern
		//This region can be eliminated if this is not a table type with cached data.
		//If leaving this region in place, be sure to add RefreshCache and FillCache 
		//to the Cache.cs file with all the other Cache types.
		///<summary>A list of all EhrLabNotes.</summary>
		private static List<EhrLabNote> listt;
		///<summary>A list of all EhrLabNotes.</summary>
		public static List<EhrLabNote> Listt{
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
			string command="SELECT * FROM ehrlabnote ORDER BY ItemOrder";//stub query probably needs to be changed
			DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
			table.TableName="EhrLabNote";
			FillCache(table);
			return table;
		}
		///<summary></summary>
		public static void FillCache(DataTable table){
			//No need to check RemotingRole; no call to db.
			listt=Crud.EhrLabNoteCrud.TableToList(table);
		}
		#endregion
		*/
/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<EhrLabNote> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<EhrLabNote>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ehrlabnote WHERE PatNum = "+POut.Long(patNum);
			return Crud.EhrLabNoteCrud.SelectMany(command);
		}
		///<summary>Gets one EhrLabNote from the db.</summary>
		public static EhrLabNote GetOne(long ehrLabNoteNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrLabNote>(MethodBase.GetCurrentMethod(),ehrLabNoteNum);
			}
			return Crud.EhrLabNoteCrud.SelectOne(ehrLabNoteNum);
		}
		///<summary></summary>
		public static void Update(EhrLabNote ehrLabNote){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabNote);
				return;
			}
			Crud.EhrLabNoteCrud.Update(ehrLabNote);
		}
		///<summary></summary>
		public static void Delete(long ehrLabNoteNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrLabNoteNum);
				return;
			}
			string command= "DELETE FROM ehrlabnote WHERE EhrLabNoteNum = "+POut.Long(ehrLabNoteNum);
			Db.NonQ(command);
		}
		*/