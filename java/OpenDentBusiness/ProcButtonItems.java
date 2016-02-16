//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcButtonItem;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ProcButtonItems   
{
    /**
    * All procbuttonitems for all buttons.
    */
    private static ProcButtonItem[] list = new ProcButtonItem[]();
    //No need to check RemotingRole; no call to db.
    public static ProcButtonItem[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(ProcButtonItem[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM procbuttonitem";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ProcButtonItem";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ProcButtonItemCrud.TableToList(table).ToArray();
    }

    /**
    * Must have already checked procCode for nonduplicate.
    */
    public static long insert(ProcButtonItem item) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            item.ProcButtonItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), item);
            return item.ProcButtonItemNum;
        }
         
        return Crud.ProcButtonItemCrud.Insert(item);
    }

    /**
    * 
    */
    public static void update(ProcButtonItem item) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), item);
            return ;
        }
         
        Crud.ProcButtonItemCrud.Update(item);
    }

    /**
    * 
    */
    public static void delete(ProcButtonItem item) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), item);
            return ;
        }
         
        String command = "DELETE FROM procbuttonitem WHERE ProcButtonItemNum = '" + POut.long(item.ProcButtonItemNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static long[] getCodeNumListForButton(long procButtonNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList ALCodes = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ProcButtonNum == procButtonNum && getList()[i].AutoCodeNum == 0)
            {
                ALCodes.Add(getList()[i].CodeNum);
            }
             
        }
        long[] codeList = new long[ALCodes.Count];
        if (ALCodes.Count > 0)
        {
            ALCodes.CopyTo(codeList);
        }
         
        return codeList;
    }

    /**
    * 
    */
    public static long[] getAutoListForButton(long procButtonNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList ALautoCodes = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ProcButtonNum == procButtonNum && getList()[i].AutoCodeNum > 0)
            {
                ALautoCodes.Add(getList()[i].AutoCodeNum);
            }
             
        }
        long[] autoCodeList = new long[ALautoCodes.Count];
        if (ALautoCodes.Count > 0)
        {
            ALautoCodes.CopyTo(autoCodeList);
        }
         
        return autoCodeList;
    }

    /**
    * 
    */
    public static void deleteAllForButton(long procButtonNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procButtonNum);
            return ;
        }
         
        String command = "DELETE from procbuttonitem WHERE procbuttonnum = '" + POut.long(procButtonNum) + "'";
        Db.nonQ(command);
    }

}


