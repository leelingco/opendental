//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:07 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class Reports   
{
    /**
    * Gets a table of data using low permissions.
    */
    public static DataTable getTable(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.getTableLow(command);
        }
         
        return Db.getTableLow(command);
    }

}


