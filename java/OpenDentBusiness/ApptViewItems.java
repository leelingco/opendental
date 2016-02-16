//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViewItem;
import OpenDentBusiness.ApptViewItemC;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Operatories;
import OpenDentBusiness.Operatory;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Handles database commands related to the apptviewitem table in the database.
*/
public class ApptViewItems   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from apptviewitem ORDER BY ElementOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ApptViewItem";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ApptViewItemC.setList(Crud.ApptViewItemCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static long insert(ApptViewItem apptViewItem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            apptViewItem.ApptViewItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), apptViewItem);
            return apptViewItem.ApptViewItemNum;
        }
         
        return Crud.ApptViewItemCrud.Insert(apptViewItem);
    }

    /**
    * 
    */
    public static void update(ApptViewItem apptViewItem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptViewItem);
            return ;
        }
         
        Crud.ApptViewItemCrud.Update(apptViewItem);
    }

    /**
    * 
    */
    public static void delete(ApptViewItem apptViewItem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptViewItem);
            return ;
        }
         
        String command = "DELETE from apptviewitem WHERE ApptViewItemNum = '" + POut.long(apptViewItem.ApptViewItemNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Deletes all apptviewitems for the current apptView.
    */
    public static void deleteAllForView(ApptView view) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), view);
            return ;
        }
         
        String command = "DELETE from apptviewitem WHERE ApptViewNum = '" + POut.long(view.ApptViewNum) + "'";
        Db.nonQ(command);
    }

    public static List<long> getOpsForView(long apptViewNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        //ArrayList AL=new ArrayList();
        List<long> retVal = new List<long>();
        for (int i = 0;i < ApptViewItemC.getList().Length;i++)
        {
            if (ApptViewItemC.getList()[i].ApptViewNum == apptViewNum && ApptViewItemC.getList()[i].OpNum != 0)
            {
                retVal.Add(ApptViewItemC.getList()[i].OpNum);
            }
             
            if (apptViewNum == 0 && ApptViewItemC.getList()[i].OpNum != 0)
            {
                //No view selected so return all operatories that are not hidden.
                Operatory op = Operatories.GetOperatory(ApptViewItemC.getList()[i].OpNum);
                if (!op.IsHidden)
                {
                    retVal.Add(ApptViewItemC.getList()[i].OpNum);
                }
                 
            }
             
        }
        return retVal;
    }

}


//int[] retVal=new int[AL.Count]();
//(int[])AL.ToArray(typeof(int));