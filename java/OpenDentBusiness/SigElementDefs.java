//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SigElementDef;
import OpenDentBusiness.SignalElementType;

/**
* 
*/
public class SigElementDefs   
{
    /**
    * A list of all SigElementDefs.
    */
    private static SigElementDef[] list = new SigElementDef[]();
    //No need to check RemotingRole; no call to db.
    public static SigElementDef[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(SigElementDef[] value) throws Exception {
        list = value;
    }

    /**
    * Gets a list of all SigElementDefs when program first opens.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM sigelementdef ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "SigElementDef";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.SigElementDefCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static void update(SigElementDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        Crud.SigElementDefCrud.Update(def);
    }

    /**
    * 
    */
    public static long insert(SigElementDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            def.SigElementDefNum = Meth.GetLong(MethodBase.GetCurrentMethod(), def);
            return def.SigElementDefNum;
        }
         
        return Crud.SigElementDefCrud.Insert(def);
    }

    /**
    * No need to surround with try/catch, because all deletions are allowed.  This routine, deletes references in the SigButDefElement table.  References in the SigElement table are left hanging.  The user interface needs to be able to handle missing elementdefs.
    */
    public static void delete(SigElementDef def) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), def);
            return ;
        }
         
        String command = "DELETE FROM sigbutdefelement WHERE SigElementDefNum=" + POut.long(def.SigElementDefNum);
        Db.nonQ(command);
        command = "DELETE FROM sigelementdef WHERE SigElementDefNum =" + POut.long(def.SigElementDefNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static SigElementDef[] getSubList(SignalElementType sigElementType) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList AL = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (sigElementType == getList()[i].SigElementType)
            {
                AL.Add(getList()[i]);
            }
             
        }
        SigElementDef[] retVal = new SigElementDef[AL.Count];
        AL.CopyTo(retVal);
        return retVal;
    }

    /**
    * Moves the selected item up in the supplied sub list.
    */
    public static void moveUp(int selected, SigElementDef[] subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected < 0)
        {
            throw new ApplicationException(Lans.g("SigElementDefs","Please select an item first."));
        }
         
        if (selected == 0)
        {
            return ;
        }
         
        //already at top
        if (selected > subList.Length - 1)
        {
            throw new ApplicationException(Lans.g("SigElementDefs","Invalid selection."));
        }
         
        SetOrder(selected - 1, subList[selected].ItemOrder, subList);
        SetOrder(selected, subList[selected].ItemOrder - 1, subList);
    }

    //Selected-=1;
    /**
    * 
    */
    public static void moveDown(int selected, SigElementDef[] subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (selected < 0)
        {
            throw new ApplicationException(Lans.g("SigElementDefs","Please select an item first."));
        }
         
        if (selected == subList.Length - 1)
        {
            return ;
        }
         
        //already at bottom
        if (selected > subList.Length - 1)
        {
            throw new ApplicationException(Lans.g("SigElementDefs","Invalid selection."));
        }
         
        SetOrder(selected + 1, subList[selected].ItemOrder, subList);
        SetOrder(selected, subList[selected].ItemOrder + 1, subList);
    }

    //selected+=1;
    /**
    * Used by MoveUp and MoveDown.
    */
    private static void setOrder(int mySelNum, int myItemOrder, SigElementDef[] subList) throws Exception {
        //No need to check RemotingRole; no call to db.
        SigElementDef temp = subList[mySelNum];
        temp.ItemOrder = myItemOrder;
        update(temp);
    }

    /**
    * Returns the SigElementDef with the specified num.
    */
    public static SigElementDef getElement(long SigElementDefNum) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getList()[i].SigElementDefNum == SigElementDefNum)
            {
                return getList()[i].Copy();
            }
             
        }
        return null;
    }

}


