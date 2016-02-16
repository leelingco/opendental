//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProcCodeNote;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ProcCodeNotes   
{
    /**
    * All notes for all procedurecodes.
    */
    private static List<ProcCodeNote> list = new List<ProcCodeNote>();
    //No need to check RemotingRole; no call to db.
    public static List<ProcCodeNote> getListt() throws Exception {
        if (list == null)
        {
            refreshCache();
        }
         
        return list;
    }

    public static void setListt(List<ProcCodeNote> value) throws Exception {
        list = value;
    }

    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM proccodenote";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ProcCodeNote";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        list = Crud.ProcCodeNoteCrud.TableToList(table);
    }

    /**
    * 
    */
    public static List<ProcCodeNote> getList(long codeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<ProcCodeNote>>GetObject(MethodBase.GetCurrentMethod(), codeNum);
        }
         
        String command = "SELECT * FROM proccodenote WHERE CodeNum=" + POut.long(codeNum);
        return Crud.ProcCodeNoteCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(ProcCodeNote note) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            note.ProcCodeNoteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), note);
            return note.ProcCodeNoteNum;
        }
         
        return Crud.ProcCodeNoteCrud.Insert(note);
    }

    /**
    * 
    */
    public static void update(ProcCodeNote note) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), note);
            return ;
        }
         
        Crud.ProcCodeNoteCrud.Update(note);
    }

    public static void delete(long procCodeNoteNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), procCodeNoteNum);
            return ;
        }
         
        String command = "DELETE FROM proccodenote WHERE ProcCodeNoteNum = " + POut.long(procCodeNoteNum);
        Db.nonQ(command);
    }

    /**
    * Gets the note for the given provider, if one exists.  Otherwise, gets the proccode.defaultnote.
    */
    public static String getNote(long provNum, long codeNum) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListt()[i].ProvNum != provNum)
            {
                continue;
            }
             
            if (getListt()[i].CodeNum != codeNum)
            {
                continue;
            }
             
            return getListt()[i].Note;
        }
        return ProcedureCodes.getProcCode(codeNum).DefaultNote;
    }

    /**
    * Gets the time pattern for the given provider, if one exists.  Otherwise, gets the proccode.ProcTime.
    */
    public static String getTimePattern(long provNum, long codeNum) throws Exception {
        for (int i = 0;i < getListt().Count;i++)
        {
            //No need to check RemotingRole; no call to db.
            if (getListt()[i].ProvNum != provNum)
            {
                continue;
            }
             
            if (getListt()[i].CodeNum != codeNum)
            {
                continue;
            }
             
            return getListt()[i].ProcTime;
        }
        return ProcedureCodes.getProcCode(codeNum).ProcTime;
    }

}


