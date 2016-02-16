//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxAlert;

/**
* 
*/
public class RxAlerts   
{
    /**
    * Gets a list of all RxAlerts for one RxDef.
    */
    public static List<RxAlert> refresh(long rxDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RxAlert>>GetObject(MethodBase.GetCurrentMethod(), rxDefNum);
        }
         
        String command = "SELECT * FROM rxalert WHERE RxDefNum=" + POut.long(rxDefNum);
        return Crud.RxAlertCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static List<RxAlert> tableToList(DataTable table) throws Exception {
        return Crud.RxAlertCrud.TableToList(table);
    }

    //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    /**
    * 
    */
    public static void update(RxAlert alert) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), alert);
            return ;
        }
         
        Crud.RxAlertCrud.Update(alert);
    }

    /**
    * 
    */
    public static long insert(RxAlert alert) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            alert.RxAlertNum = Meth.GetLong(MethodBase.GetCurrentMethod(), alert);
            return alert.RxAlertNum;
        }
         
        return Crud.RxAlertCrud.Insert(alert);
    }

    /**
    * 
    */
    public static void delete(RxAlert alert) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), alert);
            return ;
        }
         
        String command = "DELETE FROM rxalert WHERE RxAlertNum =" + POut.long(alert.RxAlertNum);
        Db.nonQ(command);
    }

}


