//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.HL7DefSegments;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class HL7DefMessages   
{
    /**
    * A list of all HL7DefMessages.
    */
    private static List<HL7DefMessage> listt = new List<HL7DefMessage>();
    /**
    * A list of all HL7DefMessages.
    */
    public static List<HL7DefMessage> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<HL7DefMessage> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM hl7defmessage ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "HL7DefMessage";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.HL7DefMessageCrud.TableToList(table);
    }

    /**
    * Gets a list of all Messages for this def from the database. No child objects included.
    */
    public static List<HL7DefMessage> getShallowFromDb(long hl7DefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<HL7DefMessage>>GetObject(MethodBase.GetCurrentMethod(), hl7DefNum);
        }
         
        String command = "SELECT * FROM hl7defmessage WHERE HL7DefNum='" + POut.long(hl7DefNum) + "' ORDER BY ItemOrder";
        return Crud.HL7DefMessageCrud.SelectMany(command);
    }

    /**
    * Gets a full deep list of all Messages for this def from cache.
    */
    public static List<HL7DefMessage> getDeepFromCache(long hl7DefNum) throws Exception {
        List<HL7DefMessage> list = new List<HL7DefMessage>();
        for (int i = 0;i < getListt().Count;i++)
        {
            if (getListt()[i].HL7DefNum == hl7DefNum)
            {
                list.Add(getListt()[i]);
                list[list.Count - 1].hl7DefSegments = HL7DefSegments.GetDeepFromCache(getListt()[i].HL7DefMessageNum);
            }
             
        }
        return list;
    }

    /**
    * Gets a full deep list of all Messages for this def from the database.
    */
    public static List<HL7DefMessage> getDeepFromDb(long hl7DefNum) throws Exception {
        List<HL7DefMessage> hl7defmsgs = new List<HL7DefMessage>();
        hl7defmsgs = getShallowFromDb(hl7DefNum);
        for (Object __dummyForeachVar0 : hl7defmsgs)
        {
            HL7DefMessage m = (HL7DefMessage)__dummyForeachVar0;
            m.hl7DefSegments = HL7DefSegments.getDeepFromDb(m.HL7DefMessageNum);
        }
        return hl7defmsgs;
    }

    /**
    * 
    */
    public static long insert(HL7DefMessage hL7DefMessage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            hL7DefMessage.HL7DefMessageNum = Meth.GetLong(MethodBase.GetCurrentMethod(), hL7DefMessage);
            return hL7DefMessage.HL7DefMessageNum;
        }
         
        return Crud.HL7DefMessageCrud.Insert(hL7DefMessage);
    }

    /**
    * 
    */
    public static void update(HL7DefMessage hL7DefMessage) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefMessage);
            return ;
        }
         
        Crud.HL7DefMessageCrud.Update(hL7DefMessage);
    }

    /**
    * 
    */
    public static void delete(long hL7DefMessageNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), hL7DefMessageNum);
            return ;
        }
         
        String command = "DELETE FROM hl7defmessage WHERE HL7DefMessageNum = " + POut.long(hL7DefMessageNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<HL7DefMessage> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<HL7DefMessage>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM hl7defmessage WHERE PatNum = "+POut.Long(patNum);
			return Crud.HL7DefMessageCrud.SelectMany(command);
		}
		///<summary>Gets one HL7DefMessage from the db.</summary>
		public static HL7DefMessage GetOne(long hL7DefMessageNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<HL7DefMessage>(MethodBase.GetCurrentMethod(),hL7DefMessageNum);
			}
			return Crud.HL7DefMessageCrud.SelectOne(hL7DefMessageNum);
		}
		*/