//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:49 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.SupplyNeeded;

/**
* 
*/
public class SupplyNeededs   
{
    /**
    * Gets all SupplyNeededs.
    */
    public static List<SupplyNeeded> createObjects() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<SupplyNeeded>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM supplyneeded ORDER BY DateAdded";
        return Crud.SupplyNeededCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(SupplyNeeded supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            supp.SupplyNeededNum = Meth.GetLong(MethodBase.GetCurrentMethod(), supp);
            return supp.SupplyNeededNum;
        }
         
        return Crud.SupplyNeededCrud.Insert(supp);
    }

    /**
    * 
    */
    public static void update(SupplyNeeded supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        Crud.SupplyNeededCrud.Update(supp);
    }

    /**
    * 
    */
    public static void deleteObject(SupplyNeeded supp) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), supp);
            return ;
        }
         
        Crud.SupplyNeededCrud.Delete(supp.SupplyNeededNum);
    }

}


