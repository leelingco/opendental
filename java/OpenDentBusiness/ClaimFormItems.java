//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.ClaimFormItem;
import OpenDentBusiness.ClaimFormItemC;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ClaimFormItems   
{
    /**
    * Gets all claimformitems for all claimforms.  Items for individual claimforms can later be extracted as needed.
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM claimformitem ORDER BY imagefilename desc";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ClaimFormItem";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ClaimFormItemC.setListt(Crud.ClaimFormItemCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static long insert(ClaimFormItem item) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            item.ClaimFormItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), item);
            return item.ClaimFormItemNum;
        }
         
        return Crud.ClaimFormItemCrud.Insert(item);
    }

    /**
    * 
    */
    public static void update(ClaimFormItem item) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), item);
            return ;
        }
         
        Crud.ClaimFormItemCrud.Update(item);
    }

    /**
    * 
    */
    public static void delete(ClaimFormItem item) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), item);
            return ;
        }
         
        String command = "DELETE FROM claimformitem " + "WHERE ClaimFormItemNum = '" + POut.long(item.ClaimFormItemNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Gets all claimformitems for the specified claimform from the preloaded List.
    */
    public static ClaimFormItem[] getListForForm(long claimFormNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        ArrayList tempAL = new ArrayList();
        for (int i = 0;i < ClaimFormItemC.getListt().Length;i++)
        {
            if (ClaimFormItemC.getListt()[i].ClaimFormNum == claimFormNum)
            {
                tempAL.Add(ClaimFormItemC.getListt()[i]);
            }
             
        }
        ClaimFormItem[] ListForForm = new ClaimFormItem[tempAL.Count];
        tempAL.CopyTo(ListForForm);
        return ListForForm;
    }

}


