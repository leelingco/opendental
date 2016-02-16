//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MountDef;
import OpenDentBusiness.MountDefC;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class MountDefs   
{
    /**
    * Gets a list of all MountDefs when program first opens.  Also refreshes MountItemDefs and attaches all items to the appropriate mounts.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        //MountItemDefs.Refresh();
        String command = "SELECT * FROM mountdef ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "MountDef";
        fillCache(table);
        return table;
    }

    private static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        MountDefC.setListt(Crud.MountDefCrud.TableToList(table));
    }

    /**
    * 
    */
    public static void update(MountDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.MountDefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(MountDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.MountDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.MountDefNum;
        }
         
        return Crud.MountDefCrud.Insert(def);
    }

    /**
    * No need to surround with try/catch, because all deletions are allowed.
    */
    public static void delete(long mountDefNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mountDefNum);
            return ;
        }
         
        String command = "DELETE FROM mountdef WHERE MountDefNum=" + POut.long(mountDefNum);
        Db.nonQ(command);
        command = "DELETE FROM mountitemdef WHERE MountDefNum =" + POut.long(mountDefNum);
        Db.nonQ(command);
    }

}


