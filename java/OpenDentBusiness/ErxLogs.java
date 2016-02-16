//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DbHelper;
import OpenDentBusiness.ErxLog;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ErxLogs   
{
    /**
    * 
    */
    public static long insert(ErxLog erxLog) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            erxLog.ErxLogNum = Meth.GetLong(MethodBase.GetCurrentMethod(), erxLog);
            return erxLog.ErxLogNum;
        }
         
        return Crud.ErxLogCrud.Insert(erxLog);
    }

    /**
    * Returns the latest ErxLog entry for the specified patient and before the specified dateTimeMax. Can return null.
    * Called from Chart when fetching prescriptions from NewCrop to determine the provider on incoming prescriptions.
    */
    public static ErxLog getLatestForPat(long patNum, DateTime dateTimeMax) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ErxLog>GetObject(MethodBase.GetCurrentMethod(), patNum, dateTimeMax);
        }
         
        String command = DbHelper.limitOrderBy("SELECT * FROM erxlog WHERE PatNum=" + POut.long(patNum) + " AND DateTStamp<" + POut.dateT(dateTimeMax) + " ORDER BY DateTStamp DESC",1);
        List<ErxLog> listErxLog = Crud.ErxLogCrud.SelectMany(command);
        if (listErxLog.Count == 0)
        {
            return null;
        }
         
        return listErxLog[0];
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ErxLog> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<ErxLog>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM erxlog WHERE PatNum = "+POut.Long(patNum);
			return Crud.ErxLogCrud.SelectMany(command);
		}
		///<summary>Gets one ErxLog from the db.</summary>
		public static ErxLog GetOne(long erxLogNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<ErxLog>(MethodBase.GetCurrentMethod(),erxLogNum);
			}
			return Crud.ErxLogCrud.SelectOne(erxLogNum);
		}
		///<summary></summary>
		public static void Update(ErxLog erxLog){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),erxLog);
				return;
			}
			Crud.ErxLogCrud.Update(erxLog);
		}
		///<summary></summary>
		public static void Delete(long erxLogNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),erxLogNum);
				return;
			}
			string command= "DELETE FROM erxlog WHERE ErxLogNum = "+POut.Long(erxLogNum);
			Db.NonQ(command);
		}
		*/