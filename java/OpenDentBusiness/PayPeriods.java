//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PayPeriod;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PayPeriods   
{
    /**
    * A list of all payperiods.
    */
    private static PayPeriod[] list = new PayPeriod[]();
    //No need to check RemotingRole; no call to db.
    public static PayPeriod[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(PayPeriod[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from payperiod ORDER BY DateStart";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "PayPeriod";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.PayPeriodCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static long insert(PayPeriod pp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            pp.PayPeriodNum = Meth.GetLong(MethodBase.GetCurrentMethod(), pp);
            return pp.PayPeriodNum;
        }
         
        return Crud.PayPeriodCrud.Insert(pp);
    }

    /**
    * 
    */
    public static void update(PayPeriod pp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pp);
            return ;
        }
         
        Crud.PayPeriodCrud.Update(pp);
    }

    /**
    * 
    */
    public static void delete(PayPeriod pp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), pp);
            return ;
        }
         
        String command = "DELETE FROM payperiod WHERE PayPeriodNum = " + POut.long(pp.PayPeriodNum);
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static int getForDate(DateTime date) throws Exception {
        for (int i = 0;i < getList().Length;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (date.Date >= getList()[i].DateStart.Date && date.Date <= getList()[i].DateStop.Date)
            {
                return i;
            }
             
        }
        return getList().Length - 1;
    }

}


//if we can't find a match, just return the last index