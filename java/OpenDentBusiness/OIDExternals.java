//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.OIDExternal;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class OIDExternals   
{
    /**
    * Will return an OIDExternal if both the root and extension match exactly, returns null if not found.
    *  @param root The OID of the object.
    *  @param extension If object is identified by only the root, this value should be an empty string.
    */
    public static OIDExternal getByRootAndExtention(String root, String extension) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<OIDExternal>GetObject(MethodBase.GetCurrentMethod(), root, extension);
        }
         
        String command = "SELECT * FROM oidexternal WHERE rootExternal='" + POut.string(root) + "' AND IDExternal='" + POut.string(extension) + "'";
        return Crud.OIDExternalCrud.SelectOne(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<OIDExternal> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<OIDExternal>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM oidexternal WHERE PatNum = "+POut.Long(patNum);
			return Crud.OIDExternalCrud.SelectMany(command);
		}
		///<summary>Gets one OIDExternal from the db.</summary>
		public static OIDExternal GetOne(long oIDExternalNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<OIDExternal>(MethodBase.GetCurrentMethod(),oIDExternalNum);
			}
			return Crud.OIDExternalCrud.SelectOne(oIDExternalNum);
		}
		///<summary></summary>
		public static long Insert(OIDExternal oIDExternal){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				oIDExternal.OIDExternalNum=Meth.GetLong(MethodBase.GetCurrentMethod(),oIDExternal);
				return oIDExternal.OIDExternalNum;
			}
			return Crud.OIDExternalCrud.Insert(oIDExternal);
		}
		///<summary></summary>
		public static void Update(OIDExternal oIDExternal){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),oIDExternal);
				return;
			}
			Crud.OIDExternalCrud.Update(oIDExternal);
		}
		///<summary></summary>
		public static void Delete(long oIDExternalNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),oIDExternalNum);
				return;
			}
			string command= "DELETE FROM oidexternal WHERE OIDExternalNum = "+POut.Long(oIDExternalNum);
			Db.NonQ(command);
		}
		*/