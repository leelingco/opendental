//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.Mount;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class Mounts   
{
    public static long insert(Mount mount) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            mount.MountNum = Meth.GetLong(MethodBase.GetCurrentMethod(), mount);
            return mount.MountNum;
        }
         
        return Crud.MountCrud.Insert(mount);
    }

    public static void update(Mount mount) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mount);
            return ;
        }
         
        Crud.MountCrud.Update(mount);
    }

    public static void delete(Mount mount) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), mount);
            return ;
        }
         
        String command = "DELETE FROM mount WHERE MountNum='" + POut.long(mount.MountNum) + "'";
        Db.nonQ(command);
    }

    /**
    * Returns a single mount object corresponding to the given mount number key.
    */
    public static Mount getByNum(long mountNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Mount>GetObject(MethodBase.GetCurrentMethod(), mountNum);
        }
         
        Mount mount = Crud.MountCrud.SelectOne(mountNum);
        if (mount == null)
        {
            return new Mount();
        }
         
        return mount;
    }

}


