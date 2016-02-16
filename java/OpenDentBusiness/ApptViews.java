//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:36 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ApptView;
import OpenDentBusiness.ApptViewC;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Handles database commands related to the apptview table in the database.
*/
public class ApptViews   
{
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String c = "SELECT * FROM apptview ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), c);
        table.TableName = "ApptView";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        ApptViewC.setList(Crud.ApptViewCrud.TableToList(table).ToArray());
    }

    /**
    * 
    */
    public static long insert(ApptView apptView) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            apptView.ApptViewNum = Meth.GetLong(MethodBase.GetCurrentMethod(), apptView);
            return apptView.ApptViewNum;
        }
         
        return Crud.ApptViewCrud.Insert(apptView);
    }

    /**
    * 
    */
    public static void update(ApptView apptView) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), apptView);
            return ;
        }
         
        Crud.ApptViewCrud.Update(apptView);
    }

    /**
    * 
    */
    public static void delete(ApptView Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE FROM apptview WHERE ApptViewNum = '" + POut.long(Cur.ApptViewNum) + "'";
        Db.nonQ(command);
    }

}


