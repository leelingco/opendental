//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:38 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Contact;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Contacts   
{
    //<summary></summary>
    //public static Contact[] List;//for one category only. Not refreshed with local data
    /**
    * 
    */
    public static Contact[] refresh(long category) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Contact[]>GetObject(MethodBase.GetCurrentMethod(), category);
        }
         
        String command = "SELECT * from contact WHERE category = '" + category + "'" + " ORDER BY LName";
        return Crud.ContactCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static long insert(Contact Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.ContactNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.ContactNum;
        }
         
        return Crud.ContactCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void update(Contact Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.ContactCrud.Update(Cur);
    }

    /**
    * 
    */
    public static void delete(Contact Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE FROM contact WHERE contactnum = '" + Cur.ContactNum.ToString() + "'";
        Db.nonQ(command);
    }

}


