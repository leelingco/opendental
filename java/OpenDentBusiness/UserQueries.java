//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:50 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.UserQuery;

/**
* 
*/
public class UserQueries   
{
    /**
    * 
    */
    public static List<UserQuery> refresh() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<UserQuery>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM userquery ORDER BY description";
        return Crud.UserQueryCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(UserQuery Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.QueryNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.QueryNum;
        }
         
        return Crud.UserQueryCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void delete(UserQuery Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from userquery WHERE querynum = '" + POut.long(Cur.QueryNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void update(UserQuery Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.UserQueryCrud.Update(Cur);
    }

}


