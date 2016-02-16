//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Automation;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Automations   
{
    private static List<Automation> listt = new List<Automation>();
    public static List<Automation> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<Automation> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM automation";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Automation";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.AutomationCrud.TableToList(table);
    }

    /**
    * 
    */
    public static long insert(Automation auto) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            auto.AutomationNum = Meth.GetLong(MethodBase.GetCurrentMethod(), auto);
            return auto.AutomationNum;
        }
         
        return Crud.AutomationCrud.Insert(auto);
    }

    /**
    * 
    */
    public static void update(Automation auto) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), auto);
            return ;
        }
         
        Crud.AutomationCrud.Update(auto);
    }

    /**
    * 
    */
    public static void delete(Automation auto) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), auto);
            return ;
        }
         
        String command = "DELETE FROM automation" + " WHERE AutomationNum = " + POut.long(auto.AutomationNum);
        Db.nonQ(command);
    }

}


