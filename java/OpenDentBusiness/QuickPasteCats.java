//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.QuickPasteCat;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class QuickPasteCats   
{
    /**
    * 
    */
    private static QuickPasteCat[] list = new QuickPasteCat[]();
    //No need to check RemotingRole; no call to db.
    public static QuickPasteCat[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(QuickPasteCat[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from quickpastecat " + "ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "QuickPasteCat";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.QuickPasteCatCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static long insert(QuickPasteCat cat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            cat.QuickPasteCatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), cat);
            return cat.QuickPasteCatNum;
        }
         
        return Crud.QuickPasteCatCrud.Insert(cat);
    }

    /**
    * 
    */
    public static void update(QuickPasteCat cat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cat);
            return ;
        }
         
        Crud.QuickPasteCatCrud.Update(cat);
    }

    /**
    * 
    */
    public static void delete(QuickPasteCat cat) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), cat);
            return ;
        }
         
        String command = "DELETE from quickpastecat WHERE QuickPasteCatNum = '" + POut.long(cat.QuickPasteCatNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Called from FormQuickPaste and from QuickPasteNotes.Substitute(). Returns the index of the default category for the specified type. If user has entered more than one, only one is returned.
    */
    public static int getDefaultType(OpenDentBusiness.QuickPasteType type) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (getList().Length == 0)
        {
            return -1;
        }
         
        if (type == OpenDentBusiness.QuickPasteType.None)
        {
            return 0;
        }
         
        //default to first line
        String[] types = new String[]();
        for (int i = 0;i < getList().Length;i++)
        {
            if (StringSupport.equals(getList()[i].DefaultForTypes, ""))
            {
                types = new String[0];
            }
            else
            {
                types = getList()[i].DefaultForTypes.Split(',');
            } 
            for (int j = 0;j < types.Length;j++)
            {
                if ((((Enum)type).ordinal()).ToString() == types[j])
                {
                    return i;
                }
                 
            }
        }
        return 0;
    }

}


