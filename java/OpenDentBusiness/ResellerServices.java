//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ResellerService;

/**
* 
*/
public class ResellerServices   
{
    /**
    * 
    */
    public static List<ResellerService> getServicesForReseller(long resellerNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ResellerService>>GetObject(MethodBase.GetCurrentMethod(), resellerNum);
        }
         
        String command = "SELECT * FROM resellerservice WHERE ResellerNum = " + POut.long(resellerNum);
        return Crud.ResellerServiceCrud.SelectMany(command);
    }

    /**
    * Gets one ResellerService from the db.
    */
    public static ResellerService getOne(long resellerServiceNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ResellerService>GetObject(MethodBase.GetCurrentMethod(), resellerServiceNum);
        }
         
        return Crud.ResellerServiceCrud.SelectOne(resellerServiceNum);
    }

    /**
    * 
    */
    public static long insert(ResellerService resellerService) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            resellerService.ResellerServiceNum = Meth.GetLong(MethodBase.GetCurrentMethod(), resellerService);
            return resellerService.ResellerServiceNum;
        }
         
        return Crud.ResellerServiceCrud.Insert(resellerService);
    }

    /**
    * 
    */
    public static void update(ResellerService resellerService) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), resellerService);
            return ;
        }
         
        Crud.ResellerServiceCrud.Update(resellerService);
    }

    /**
    * 
    */
    public static void delete(long resellerServiceNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), resellerServiceNum);
            return ;
        }
         
        String command = "DELETE FROM resellerservice WHERE ResellerServiceNum = " + POut.long(resellerServiceNum);
        Db.nonQ(command);
    }

}


