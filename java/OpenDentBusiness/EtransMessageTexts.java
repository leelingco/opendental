//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EtransMessageText;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.X12object;

/**
* 
*/
public class EtransMessageTexts   
{
    /**
    * 
    */
    public static long insert(EtransMessageText etransMessageText) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            etransMessageText.EtransMessageTextNum = Meth.GetLong(MethodBase.GetCurrentMethod(), etransMessageText);
            return etransMessageText.EtransMessageTextNum;
        }
         
        return Crud.EtransMessageTextCrud.Insert(etransMessageText);
    }

    /**
    * If the message text is X12, then it always normalizes it to include carriage returns for better readability.
    */
    public static String getMessageText(long etransMessageTextNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), etransMessageTextNum);
        }
         
        if (etransMessageTextNum == 0)
        {
            return "";
        }
         
        String command = "SELECT MessageText FROM etransmessagetext WHERE EtransMessageTextNum=" + POut.long(etransMessageTextNum);
        String msgText = Db.getScalar(command);
        if (!X12object.isX12(msgText))
        {
            return msgText;
        }
         
        Match match = Regex.Match(msgText, "~[^(\n)(\r)]");
        while (match.Success)
        {
            msgText = msgText.Substring(0, match.Index) + "~\r\n" + msgText.Substring(match.Index + 1);
            match = Regex.Match(msgText, "~[^(\n)(\r)]");
        }
        return msgText;
    }

    //MatchCollection matches=Regex.Matches(msgText,"~[^(\n)(\r)]");
    //for(int i=0;i<matches.Count;i++) {
    //Regex.
    //	matches[i].
    //}
    //msgText=Regex.Replace(msgText,"~[^(\r\n)(\n)(\r)]","~\r\n");
    /*
    		///<summary></summary>
    		public static void Update(EtransMessageText EtransMessageText) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),EtransMessageText);
    				return;
    			}
    			string command= "UPDATE EtransMessageText SET "
    				+"ClearingHouseNum = '"   +POut.PInt   (EtransMessageText.ClearingHouseNum)+"', "
    				+"Etype= '"               +POut.PInt   ((int)EtransMessageText.Etype)+"', "
    				+"Note= '"                +POut.PString(EtransMessageText.Note)+"', "
    				+"EtransMessageTextMessageTextNum= '"+POut.PInt   (EtransMessageText.EtransMessageTextMessageTextNum)+"' "
    				+"WHERE EtransMessageTextNum = "+POut.PInt(EtransMessageText.EtransMessageTextNum);
    			Db.NonQ(command);
    		}
    */
    /**
    * 
    */
    public static void delete(long etransMessageTextNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), etransMessageTextNum);
            return ;
        }
         
        if (etransMessageTextNum == 0)
        {
            return ;
        }
         
        String command = new String();
        command = "DELETE FROM etransmessagetext WHERE EtransMessageTextNum=" + POut.long(etransMessageTextNum);
        Db.nonQ(command);
    }

}


