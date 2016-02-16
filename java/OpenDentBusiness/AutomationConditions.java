//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutomationCondition;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class AutomationConditions   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all AutomationConditions.
    */
    private static List<AutomationCondition> listt = new List<AutomationCondition>();
    /**
    * A list of all AutomationConditions.
    */
    public static List<AutomationCondition> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<AutomationCondition> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM automationcondition";
        //stub query probably needs to be changed
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AutomationCondition";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.AutomationConditionCrud.TableToList(table);
    }

    /**
    * Gets one AutomationCondition from the db.
    */
    public static AutomationCondition getOne(long automationConditionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<AutomationCondition>GetObject(MethodBase.GetCurrentMethod(), automationConditionNum);
        }
         
        return Crud.AutomationConditionCrud.SelectOne(automationConditionNum);
    }

    /**
    * Gets a list of AutomationConditions from the db by AutomationNum.
    */
    public static List<AutomationCondition> getListByAutomationNum(long automationNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<AutomationCondition>>GetObject(MethodBase.GetCurrentMethod(), automationNum);
        }
         
        String command = "SELECT * FROM automationcondition WHERE AutomationNum = " + POut.long(automationNum);
        return Crud.AutomationConditionCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(AutomationCondition automationCondition) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            automationCondition.AutomationConditionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), automationCondition);
            return automationCondition.AutomationConditionNum;
        }
         
        return Crud.AutomationConditionCrud.Insert(automationCondition);
    }

    /**
    * 
    */
    public static void update(AutomationCondition automationCondition) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), automationCondition);
            return ;
        }
         
        Crud.AutomationConditionCrud.Update(automationCondition);
    }

    /**
    * 
    */
    public static void delete(long automationConditionNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), automationConditionNum);
            return ;
        }
         
        String command = "DELETE FROM automationcondition WHERE AutomationConditionNum = " + POut.long(automationConditionNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void deleteByAutomationNum(long automationNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), automationNum);
            return ;
        }
         
        String command = "DELETE FROM automationcondition WHERE AutomationNum = " + POut.long(automationNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<AutomationCondition> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<AutomationCondition>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM automationcondition WHERE PatNum = "+POut.Long(patNum);
			return Crud.AutomationConditionCrud.SelectMany(command);
		}
		
		*/