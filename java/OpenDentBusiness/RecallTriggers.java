//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RecallTrigger;
import OpenDentBusiness.RecallTriggerC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class RecallTriggers   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM recalltrigger";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "RecallTrigger";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        RecallTriggerC.setListt(Crud.RecallTriggerCrud.TableToList(table));
    }

    /**
    * 
    */
    public static long insert(RecallTrigger trigger) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            trigger.RecallTriggerNum = Meth.GetLong(MethodBase.GetCurrentMethod(), trigger);
            return trigger.RecallTriggerNum;
        }
         
        return Crud.RecallTriggerCrud.Insert(trigger);
    }

    /*
    		///<summary></summary>
    		public static void Update(RecallTrigger trigger) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),trigger);
    				return;
    			}
    			Crud.RecallTriggerCrud.Update(trigger);
    		}*/
    public static List<RecallTrigger> getForType(long recallTypeNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        List<RecallTrigger> triggerList = new List<RecallTrigger>();
        if (recallTypeNum == 0)
        {
            return triggerList;
        }
         
        for (int i = 0;i < RecallTriggerC.getListt().Count;i++)
        {
            if (RecallTriggerC.getListt()[i].RecallTypeNum == recallTypeNum)
            {
                triggerList.Add(RecallTriggerC.getListt()[i].Copy());
            }
             
        }
        return triggerList;
    }

    public static void setForType(long recallTypeNum, List<RecallTrigger> triggerList) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), recallTypeNum, triggerList);
            return ;
        }
         
        String command = "DELETE FROM recalltrigger WHERE RecallTypeNum=" + POut.long(recallTypeNum);
        Db.nonQ(command);
        for (int i = 0;i < triggerList.Count;i++)
        {
            triggerList[i].RecallTypeNum = recallTypeNum;
            Insert(triggerList[i]);
        }
    }

}


