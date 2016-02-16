//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Letter;
import OpenDentBusiness.Meth;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* Letters are refreshed as local data.
*/
public class Letters   
{
    /**
    * List of
    */
    private static Letter[] list = new Letter[]();
    //No need to check RemotingRole; no call to db.
    public static Letter[] getList() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setList(Letter[] value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from letter ORDER BY Description";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "Letter";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.LetterCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static void update(Letter Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        Crud.LetterCrud.Update(Cur);
    }

    /**
    * 
    */
    public static long insert(Letter Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.LetterNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.LetterNum;
        }
         
        return Crud.LetterCrud.Insert(Cur);
    }

    /**
    * 
    */
    public static void delete(Letter Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), Cur);
            return ;
        }
         
        String command = "DELETE from letter WHERE LetterNum = '" + Cur.LetterNum.ToString() + "'";
        Db.nonQ(command);
    }

}


