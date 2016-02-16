//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoNote;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class AutoNotes   
{
    /**
    * A list of all Auto Notes.  Caching could be handled better for fewer refreshes.
    */
    public static List<AutoNote> Listt = new List<AutoNote>();
    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM autonote ORDER BY AutoNoteName";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "AutoNote";
        fillCache(table);
        return table;
    }

    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        Listt = Crud.AutoNoteCrud.TableToList(table);
    }

    /**
    * 
    */
    public static long insert(AutoNote autonote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            autonote.AutoNoteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), autonote);
            return autonote.AutoNoteNum;
        }
         
        return Crud.AutoNoteCrud.Insert(autonote);
    }

    /**
    * 
    */
    public static void update(AutoNote autonote) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), autonote);
            return ;
        }
         
        Crud.AutoNoteCrud.Update(autonote);
    }

    /**
    * 
    */
    public static void delete(long autoNoteNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), autoNoteNum);
            return ;
        }
         
        String command = "DELETE FROM autonote " + "WHERE AutoNoteNum = " + POut.long(autoNoteNum);
        Db.nonQ(command);
    }

}


