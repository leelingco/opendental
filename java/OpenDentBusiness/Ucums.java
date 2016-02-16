//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DataCore;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Ucum;

/**
* 
*/
public class Ucums   
{
    //If this table type will exist as cached data, uncomment the CachePattern region below and edit.
    //#region CachePattern
    /**
    * /This region can be eliminated if this is not a table type with cached data.
    * /If leaving this region in place, be sure to add RefreshCache and FillCache
    * /to the Cache.cs file with all the other Cache types.
    * //A list of all UCUMs.
    */
    //private static List<Ucum> listt;
    /**
    * //A list of all UCUMs.
    */
    //public static List<Ucum> Listt{
    //	get {
    //		if(listt==null) {
    //			RefreshCache();
    //		}
    //		return listt;
    //	}
    //	set {
    //		listt=value;
    //	}
    //}
    /**
    * //
    */
    //public static DataTable RefreshCache(){
    //	//No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    //	string command="SELECT * FROM ucum ORDER BY ItemOrder";//stub query probably needs to be changed
    //	DataTable table=Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(),command);
    //	table.TableName="UCUM";
    //	FillCache(table);
    //	return table;
    //}
    /**
    * //
    */
    //public static void FillCache(DataTable table){
    //	//No need to check RemotingRole; no call to db.
    //	listt=Crud.UcumCrud.TableToList(table);
    //}
    //#endregion
    /**
    * 
    */
    public static long insert(Ucum ucum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ucum.UcumNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ucum);
            return ucum.UcumNum;
        }
         
        return Crud.UcumCrud.Insert(ucum);
    }

    public static List<Ucum> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Ucum>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM ucum ORDER BY UcumCode";
        return Crud.UcumCrud.SelectMany(command);
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
        String command = "SELECT UcumCode FROM ucum";
        DataTable table = DataCore.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(table.Rows[i].ItemArray[0].ToString());
        }
        return retVal;
    }

    public static Ucum getByCode(String ucumCode) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Ucum>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM ucum WHERE UcumCode='" + POut.string(ucumCode) + "'";
        return Crud.UcumCrud.SelectOne(command);
    }

    public static List<Ucum> getBySearchText(String searchText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Ucum>>GetObject(MethodBase.GetCurrentMethod(), searchText);
        }
         
        String[] searchTokens = searchText.Split(' ');
        String command = "SELECT * FROM ucum ";
        for (int i = 0;i < searchTokens.Length;i++)
        {
            command += (i == 0 ? "WHERE " : "AND ") + "(UcumCode LIKE '%" + POut.String(searchTokens[i]) + "%' OR Description LIKE '%" + POut.String(searchTokens[i]) + "%') ";
        }
        return Crud.UcumCrud.SelectMany(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<UCUM> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<UCUM>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM ucum WHERE PatNum = "+POut.Long(patNum);
			return Crud.UCUMCrud.SelectMany(command);
		}
		///<summary>Gets one UCUM from the db.</summary>
		public static UCUM GetOne(long uCUMNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<UCUM>(MethodBase.GetCurrentMethod(),uCUMNum);
			}
			return Crud.UCUMCrud.SelectOne(uCUMNum);
		}
		///<summary></summary>
		public static void Update(UCUM uCUM){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),uCUM);
				return;
			}
			Crud.UCUMCrud.Update(uCUM);
		}
		///<summary></summary>
		public static void Delete(long uCUMNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),uCUMNum);
				return;
			}
			string command= "DELETE FROM ucum WHERE UCUMNum = "+POut.Long(uCUMNum);
			Db.NonQ(command);
		}
		*/