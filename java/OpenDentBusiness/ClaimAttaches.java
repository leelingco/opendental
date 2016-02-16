//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ClaimAttach;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ClaimAttaches   
{
    public static long insert(ClaimAttach attach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            attach.ClaimAttachNum = Meth.GetLong(MethodBase.GetCurrentMethod(), attach);
            return attach.ClaimAttachNum;
        }
         
        return Crud.ClaimAttachCrud.Insert(attach);
    }

}


