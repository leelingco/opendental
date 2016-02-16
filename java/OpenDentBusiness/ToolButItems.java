//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.Programs;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ToolBarsAvail;
import OpenDentBusiness.ToolButItem;

/**
* 
*/
public class ToolButItems   
{
    /**
    * 
    */
    private static ToolButItem[] list = new ToolButItem[]();
    //<summary></summary>
    //public static ArrayList ForProgram;
    //No need to check RemotingRole; no call to db.
    public static ToolButItem[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(ToolButItem[] value) throws Exception {
        list = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from toolbutitem";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ToolButItem";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ToolButItemCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static long insert(ToolButItem Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ToolButItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ToolButItemNum;
        }
         
        return Crud.ToolButItemCrud.Insert(Cur);
    }

    /**
    * This in not currently being used.
    */
    public static void update(ToolButItem Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ToolButItemCrud.Update(Cur);
    }

    /**
    * This is not currently being used.
    */
    public static void delete(ToolButItem Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from toolbutitem WHERE ToolButItemNum = '" + POut.long(Cur.ToolButItemNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Deletes all ToolButItems for the Programs.Cur.  This is used regularly when saving a Program link because of the way the user interface works.
    */
    public static void deleteAllForProgram(long programNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), programNum);
            return ;
        }
         
        String command = "DELETE from toolbutitem WHERE ProgramNum = '" + POut.long(programNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Fills ForProgram with toolbutitems attached to the Programs.Cur
    */
    public static List<ToolButItem> getForProgram(long programNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getList() == null)
        {
            refreshCache();
        }
         
        List<ToolButItem> ForProgram = new List<ToolButItem>();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ProgramNum == programNum)
            {
                ForProgram.Add(getList()[i]);
            }
             
        }
        return ForProgram;
    }

    /**
    * Returns a list of toolbutitems for the specified toolbar. Used when laying out toolbars.
    */
    public static ArrayList getForToolBar(ToolBarsAvail toolbar) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getList() == null)
        {
            refreshCache();
        }
         
        ArrayList retVal = new ArrayList();
        for (int i = 0;i < getList().Length;i++)
        {
            if (getList()[i].ToolBar == toolbar && Programs.IsEnabled(getList()[i].ProgramNum))
            {
                retVal.Add(getList()[i]);
            }
             
        }
        return retVal;
    }

}


