//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CanadianNetwork;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class CanadianNetworks   
{
    private static List<CanadianNetwork> listt = new List<CanadianNetwork>();
    //No need to check RemotingRole; no call to db.
    public static List<CanadianNetwork> getListt() throws Exception {
        if (listt == null)
        {
            refresh();
        }
         
        return listt;
    }

    /**
    * 
    */
    public static void refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod());
            return ;
        }
         
        String command = "SELECT * FROM canadiannetwork ORDER BY Descript";
        DataTable table = Db.getTable(command);
        listt = Crud.CanadianNetworkCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(CanadianNetwork canadianNetwork) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            canadianNetwork.CanadianNetworkNum = Meth.GetLong(MethodBase.GetCurrentMethod(), canadianNetwork);
            return canadianNetwork.CanadianNetworkNum;
        }
         
        return Crud.CanadianNetworkCrud.Insert(canadianNetwork);
    }

    /**
    * 
    */
    public static void update(CanadianNetwork canadianNetwork) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), canadianNetwork);
            return ;
        }
         
        Crud.CanadianNetworkCrud.Update(canadianNetwork);
    }

    /**
    * 
    */
    public static void delete(int networkNum) throws Exception {
    }

    /**
    * 
    */
    public static CanadianNetwork getNetwork(long networkNum) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (listt == null)
        {
            refresh();
        }
         
        for (int i = 0;i < listt.Count;i++)
        {
            if (listt[i].CanadianNetworkNum == networkNum)
            {
                return listt[i];
            }
             
        }
        return null;
    }

}


