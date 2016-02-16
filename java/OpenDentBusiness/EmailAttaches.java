//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EmailAttaches   
{
    public static long insert(EmailAttach attach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            attach.EmailAttachNum = Meth.GetLong(MethodBase.GetCurrentMethod(), attach);
            return attach.EmailAttachNum;
        }
         
        return Crud.EmailAttachCrud.Insert(attach);
    }

    public static List<EmailAttach> getForEmail(long emailMessageNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EmailAttach>>GetObject(MethodBase.GetCurrentMethod(), emailMessageNum);
        }
         
        String command = "SELECT * FROM emailattach WHERE EmailMessageNum=" + POut.long(emailMessageNum);
        return Crud.EmailAttachCrud.SelectMany(command);
    }

}


