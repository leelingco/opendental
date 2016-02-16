//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimCondCodeLog;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class ClaimCondCodeLogs   
{
    /**
    * Will be null if this claim has no condition codes.
    */
    public static ClaimCondCodeLog getByClaimNum(long claimNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ClaimCondCodeLog>GetObject(MethodBase.GetCurrentMethod(), claimNum);
        }
         
        String command = "SELECT * FROM claimcondcodelog WHERE ClaimNum=" + POut.long(claimNum);
        return Crud.ClaimCondCodeLogCrud.SelectOne(command);
    }

    //ClaimCondCodeLog claimCondCodeLog =
    //if(claimCondCodeLog==null){
    //	return new ClaimCondCodeLog();
    //}
    //return claimCondCodeLog;
    public static void update(ClaimCondCodeLog claimCondCodeLog) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimCondCodeLog);
            return ;
        }
         
        Crud.ClaimCondCodeLogCrud.Update(claimCondCodeLog);
    }

    public static void insert(ClaimCondCodeLog claimCondCodeLog) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), claimCondCodeLog);
            return ;
        }
         
        Crud.ClaimCondCodeLogCrud.Insert(claimCondCodeLog);
    }

}


