//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RxDef;

/**
* 
*/
public class RxDefs   
{
    /**
    * 
    */
    public static RxDef[] refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RxDef[]>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM rxdef ORDER BY Drug";
        return Crud.RxDefCrud.SelectMany(command).ToArray();
    }

    public static RxDef getOne(long rxDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RxDef>GetObject(MethodBase.GetCurrentMethod(), rxDefNum);
        }
         
        return Crud.RxDefCrud.SelectOne(rxDefNum);
    }

    /**
    * 
    */
    public static void update(RxDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.RxDefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(RxDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.RxDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.RxDefNum;
        }
         
        return Crud.RxDefCrud.Insert(def);
    }

    /**
    * 
    */
    public static List<RxDef> tableToList(DataTable table) throws Exception {
        return Crud.RxDefCrud.TableToList(table);
    }

    //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
    /**
    * Also deletes all RxAlerts that were attached.
    */
    public static void delete(RxDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        String command = "DELETE FROM rxalert WHERE RxDefNum=" + POut.long(def.RxDefNum);
        Db.nonQ(command);
        command = "DELETE FROM rxdef WHERE RxDefNum = " + POut.long(def.RxDefNum);
        Db.nonQ(command);
    }

}


