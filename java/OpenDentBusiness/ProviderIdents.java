//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProviderIdent;
import OpenDentBusiness.ProviderSupplementalID;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Refreshed with local data.
*/
public class ProviderIdents   
{
    /**
    * This is the list of all id's for all providers. They are extracted as needed.
    */
    private static ProviderIdent[] list = new ProviderIdent[]();
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM providerident";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ProviderIdent";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ProviderIdentCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static void update(ProviderIdent pi) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pi);
            return ;
        }
         
        Crud.ProviderIdentCrud.Update(pi);
    }

    /**
    * 
    */
    public static long insert(ProviderIdent pi) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pi.ProviderIdentNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pi);
            return pi.ProviderIdentNum;
        }
         
        return Crud.ProviderIdentCrud.Insert(pi);
    }

    /**
    * 
    */
    public static void delete(ProviderIdent pi) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pi);
            return ;
        }
         
        String command = "DELETE FROM providerident " + "WHERE ProviderIdentNum = " + POut.long(pi.ProviderIdentNum);
        Db.nonQ(command);
    }

    /**
    * Gets all supplemental identifiers that have been attached to this provider. Used in the provider edit window.
    */
    public static ProviderIdent[] getForProv(long provNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (list == null)
        {
            refreshCache();
        }
         
        ArrayList arrayL = new ArrayList();
        for (int i = 0;i < list.Length;i++)
        {
            if (list[i].ProvNum == provNum)
            {
                arrayL.Add(list[i]);
            }
             
        }
        ProviderIdent[] ForProv = new ProviderIdent[arrayL.Count];
        for (int i = 0;i < arrayL.Count;i++)
        {
            ForProv[i] = (ProviderIdent)arrayL[i];
        }
        return ForProv;
    }

    /**
    * Gets all supplemental identifiers that have been attached to this provider and for this particular payorID.  Called from X12 when creating a claim file.  Also used now on printed claims.
    */
    public static ProviderIdent[] getForPayor(long provNum, String payorID) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (list == null)
        {
            refreshCache();
        }
         
        ArrayList arrayL = new ArrayList();
        for (int i = 0;i < list.Length;i++)
        {
            if (list[i].ProvNum == provNum && StringSupport.equals(list[i].PayorID, payorID))
            {
                arrayL.Add(list[i]);
            }
             
        }
        ProviderIdent[] ForPayor = new ProviderIdent[arrayL.Count];
        for (int i = 0;i < arrayL.Count;i++)
        {
            ForPayor[i] = (ProviderIdent)arrayL[i];
        }
        return ForPayor;
    }

    /**
    * Called from FormProvEdit if cancel on a new provider.
    */
    public static void deleteAllForProv(long provNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), provNum);
            return ;
        }
         
        String command = "DELETE from providerident WHERE provnum = '" + POut.long(provNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static boolean identExists(ProviderSupplementalID type, long provNum, String payorID) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (list == null)
        {
            refreshCache();
        }
         
        for (int i = 0;i < list.Length;i++)
        {
            if (list[i].ProvNum == provNum && list[i].SuppIDType == type && StringSupport.equals(list[i].PayorID, payorID))
            {
                return true;
            }
             
        }
        return false;
    }

}


