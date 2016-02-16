//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:42 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.EhrSummaryCcd;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrSummaryCcds   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all EhrSummaryCcds.
    */
    private static List<EhrSummaryCcd> listt = new List<EhrSummaryCcd>();
    /**
    * A list of all EhrSummaryCcds.
    */
    public static List<EhrSummaryCcd> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<EhrSummaryCcd> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM ehrsummaryccd";
        //stub query probably needs to be changed
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "EhrSummaryCcd";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.EhrSummaryCcdCrud.TableToList(table);
    }

    /**
    * 
    */
    public static List<EhrSummaryCcd> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrSummaryCcd>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM ehrsummaryccd WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateSummary";
        return Crud.EhrSummaryCcdCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EhrSummaryCcd ehrSummaryCcd) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrSummaryCcd.EhrSummaryCcdNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrSummaryCcd);
            return ehrSummaryCcd.EhrSummaryCcdNum;
        }
         
        return Crud.EhrSummaryCcdCrud.Insert(ehrSummaryCcd);
    }

    /**
    * Returns null if no record is found.
    */
    public static EhrSummaryCcd getOneForEmailAttach(long emailAttachNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EhrSummaryCcd>GetObject(MethodBase.GetCurrentMethod(), emailAttachNum);
        }
         
        String command = "SELECT * FROM ehrsummaryccd WHERE EmailAttachNum=" + POut.long(emailAttachNum) + " LIMIT 1";
        return Crud.EhrSummaryCcdCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void update(EhrSummaryCcd ehrSummaryCcd) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrSummaryCcd);
            return ;
        }
         
        Crud.EhrSummaryCcdCrud.Update(ehrSummaryCcd);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one EhrSummaryCcd from the db.</summary>
		public static EhrSummaryCcd GetOne(long ehrSummaryCcdNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EhrSummaryCcd>(MethodBase.GetCurrentMethod(),ehrSummaryCcdNum);
			}
			return Crud.EhrSummaryCcdCrud.SelectOne(ehrSummaryCcdNum);
		}
		///<summary></summary>
		public static void Delete(long ehrSummaryCcdNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),ehrSummaryCcdNum);
				return;
			}
			string command= "DELETE FROM ehrsummaryccd WHERE EhrSummaryCcdNum = "+POut.Long(ehrSummaryCcdNum);
			Db.NonQ(command);
		}
		*/