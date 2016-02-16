//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.OperatoryC;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Operatories   
{
    /**
    * Refresh all operatories
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM operatory " + "ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Operatory";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        OperatoryC.setListt(Crud.OperatoryCrud.TableToList(table));
        OperatoryC.setListShort(new List<Operatory>());
        for (int i = 0;i < OperatoryC.getListt().Count;i++)
        {
            if (!OperatoryC.getListt()[i].IsHidden)
            {
                OperatoryC.getListShort().Add(OperatoryC.getListt()[i]);
            }
             
        }
    }

    /**
    * 
    */
    public static long insert(Operatory operatory) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            operatory.OperatoryNum = Meth.GetLong(MethodBase.GetCurrentMethod(), operatory);
            return operatory.OperatoryNum;
        }
         
        return Crud.OperatoryCrud.Insert(operatory);
    }

    /**
    * 
    */
    public static void update(Operatory operatory) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), operatory);
            return ;
        }
         
        Crud.OperatoryCrud.Update(operatory);
    }

    //<summary>Checks dependencies first.  Throws exception if can't delete.</summary>
    //public void Delete(){//no such thing as delete.  Hide instead
    //}
    public static List<Operatory> getChangedSince(DateTime changedSince) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<Operatory>>GetObject(MethodBase.GetCurrentMethod(), changedSince);
        }
         
        String command = "SELECT * FROM operatory WHERE DateTStamp > " + POut.dateT(changedSince);
        return Crud.OperatoryCrud.SelectMany(command);
    }

    public static String getAbbrev(long operatoryNum) throws Exception {
        for (int i = 0;i < OperatoryC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (OperatoryC.getListt()[i].OperatoryNum == operatoryNum)
            {
                return OperatoryC.getListt()[i].Abbrev;
            }
             
        }
        return "";
    }

    /**
    * Gets the order of the op within ListShort or -1 if not found.
    */
    public static int getOrder(long opNum) throws Exception {
        for (int i = 0;i < OperatoryC.getListShort().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (OperatoryC.getListShort()[i].OperatoryNum == opNum)
            {
                return i;
            }
             
        }
        return -1;
    }

    /**
    * Gets operatory from the cache.
    */
    public static Operatory getOperatory(long operatoryNum) throws Exception {
        for (int i = 0;i < OperatoryC.getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (OperatoryC.getListt()[i].OperatoryNum == operatoryNum)
            {
                return OperatoryC.getListt()[i].Copy();
            }
             
        }
        return null;
    }

}


