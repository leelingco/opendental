//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.EmailMessageUid;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EmailMessageUids   
{
    /**
    * Gets all unique email ids for the given recipient email address.  The result is used to determine which emails to download for a particular inbox address.
    */
    public static List<EmailMessageUid> getForRecipientAddress(String strRecipientAddress) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EmailMessageUid>>GetObject(MethodBase.GetCurrentMethod(), strRecipientAddress);
        }
         
        String command = "SELECT * FROM emailmessageuid WHERE RecipientAddress='" + POut.string(strRecipientAddress) + "'";
        return Crud.EmailMessageUidCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(EmailMessageUid emailMessageUid) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            emailMessageUid.EmailMessageUidNum = Meth.GetLong(MethodBase.GetCurrentMethod(), emailMessageUid);
            return emailMessageUid.EmailMessageUidNum;
        }
         
        return Crud.EmailMessageUidCrud.Insert(emailMessageUid);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one EmailMessageUid from the db.</summary>
		public static EmailMessageUid GetOne(long emailMessageUidNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<EmailMessageUid>(MethodBase.GetCurrentMethod(),emailMessageUidNum);
			}
			return Crud.EmailMessageUidCrud.SelectOne(emailMessageUidNum);
		}
		///<summary></summary>
		public static void Update(EmailMessageUid emailMessageUid){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),emailMessageUid);
				return;
			}
			Crud.EmailMessageUidCrud.Update(emailMessageUid);
		}
		///<summary></summary>
		public static void Delete(long emailMessageUidNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),emailMessageUidNum);
				return;
			}
			string command= "DELETE FROM emailmessageuid WHERE EmailMessageUidNum = "+POut.Long(emailMessageUidNum);
			Db.NonQ(command);
		}
		*/