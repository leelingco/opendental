//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.MountItem;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class MountItems   
{
    public static long insert(MountItem mountItem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            mountItem.MountItemNum = Meth.GetLong(MethodBase.GetCurrentMethod(), mountItem);
            return mountItem.MountItemNum;
        }
         
        return Crud.MountItemCrud.Insert(mountItem);
    }

    /*
    		public static void Update(MountItem mountItem) {
    			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
    				Meth.GetVoid(MethodBase.GetCurrentMethod(),mountItem);
    				return;
    			}
    			Crud.MountItemCrud.Update(mountItem);
    		}*/
    public static void delete(MountItem mountItem) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mountItem);
            return ;
        }
         
        String command = "DELETE FROM mountitem WHERE MountItemNum='" + POut.long(mountItem.MountItemNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Returns the list of mount items associated with the given mount key.
    */
    public static List<MountItem> getItemsForMount(long mountNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<MountItem>>GetObject(MethodBase.GetCurrentMethod(), mountNum);
        }
         
        String command = "SELECT * FROM mountitem WHERE MountNum='" + POut.long(mountNum) + "' ORDER BY OrdinalPos";
        return Crud.MountItemCrud.SelectMany(command);
    }

}


