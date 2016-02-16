//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SecurityLog;
import OpenDentBusiness.SecurityLogHash;
import OpenDentBusiness.SecurityLogs;

/**
* 
*/
public class SecurityLogHashes   
{
    /**
    * Inserts securityloghash into Db.
    */
    public static long insert(SecurityLogHash securityLogHash) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            securityLogHash.SecurityLogHashNum = Meth.GetLong(MethodBase.GetCurrentMethod(), securityLogHash);
            return securityLogHash.SecurityLogHashNum;
        }
         
        return Crud.SecurityLogHashCrud.Insert(securityLogHash);
    }

    /**
    * Creates a new SecurityLogHash entry in the Db.
    */
    public static void insertSecurityLogHash(long securityLogNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), securityLogNum);
            return ;
        }
         
        SecurityLog securityLog = SecurityLogs.getOne(securityLogNum);
        //need a fresh copy because of time stamps, etc.
        SecurityLogHash securityLogHash = new SecurityLogHash();
        //Set the FK
        securityLogHash.SecurityLogNum = securityLog.SecurityLogNum;
        //Hash the securityLog
        securityLogHash.LogHash = getHashString(securityLog);
        insert(securityLogHash);
    }

    /**
    * Does not make a call to the db.  Returns a SHA-256 hash of the entire security log.  Length of 32 bytes.  Only called from CreateSecurityLogHash() and FormAudit.FillGrid()
    */
    public static String getHashString(SecurityLog securityLog) throws Exception {
        //No need to check RemotingRole; no call to db.
        HashAlgorithm algorithm = SHA256.Create();
        //Build string to hash
        String logString = "";
        //logString+=securityLog.SecurityLogNum;
        logString += (((Enum)securityLog.PermType).ordinal()).ToString();
        logString += securityLog.UserNum;
        logString += POut.dateT(securityLog.LogDateTime,false);
        logString += securityLog.LogText;
        //logString+=securityLog.CompName;
        logString += securityLog.PatNum;
        //logString+=securityLog.FKey.ToString();
        byte[] unicodeBytes = Encoding.Unicode.GetBytes(logString);
        byte[] hashbytes = algorithm.ComputeHash(unicodeBytes);
        return Convert.ToBase64String(hashbytes);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<SecurityLogHash> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<SecurityLogHash>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM securityloghash WHERE PatNum = "+POut.Long(patNum);
			return Crud.SecurityLogHashCrud.SelectMany(command);
		}
		///<summary>Gets one SecurityLogHash from the db.</summary>
		public static SecurityLogHash GetOne(long securityLogHashNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<SecurityLogHash>(MethodBase.GetCurrentMethod(),securityLogHashNum);
			}
			return Crud.SecurityLogHashCrud.SelectOne(securityLogHashNum);
		}
		///<summary></summary>
		public static void Update(SecurityLogHash securityLogHash){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),securityLogHash);
				return;
			}
			Crud.SecurityLogHashCrud.Update(securityLogHash);
		}
		///<summary></summary>
		public static void Delete(long securityLogHashNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),securityLogHashNum);
				return;
			}
			string command= "DELETE FROM securityloghash WHERE SecurityLogHashNum = "+POut.Long(securityLogHashNum);
			Db.NonQ(command);
		}
		*/