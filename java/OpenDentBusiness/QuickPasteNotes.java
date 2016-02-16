//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Cache;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.QuickPasteCats;
import OpenDentBusiness.QuickPasteNote;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class QuickPasteNotes   
{
    /**
    * list of all notes for all categories. Not very useful.
    */
    private static QuickPasteNote[] List = new QuickPasteNote[]();
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * from quickpastenote " + "ORDER BY ItemOrder";
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "QuickPasteNote";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        List = Crud.QuickPasteNoteCrud.TableToList(table).ToArray();
    }

    /**
    * 
    */
    public static long insert(QuickPasteNote note) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            note.QuickPasteNoteNum = Meth.GetLong(MethodBase.GetCurrentMethod(), note);
            return note.QuickPasteNoteNum;
        }
         
        return Crud.QuickPasteNoteCrud.Insert(note);
    }

    /**
    * 
    */
    public static void update(QuickPasteNote note) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), note);
            return ;
        }
         
        Crud.QuickPasteNoteCrud.Update(note);
    }

    /**
    * 
    */
    public static void delete(QuickPasteNote note) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), note);
            return ;
        }
         
        String command = "DELETE from quickpastenote WHERE QuickPasteNoteNum = '" + POut.long(note.QuickPasteNoteNum) + "'";
        Db.nonQ(command);
    }

    /**
    * When saving an abbrev, this makes sure that the abbreviation is not already in use.
    */
    public static boolean abbrAlreadyInUse(QuickPasteNote note) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), note);
        }
         
        String command = "SELECT * FROM quickpastenote WHERE " + "Abbreviation='" + POut.string(note.Abbreviation) + "' " + "AND QuickPasteNoteNum != '" + POut.long(note.QuickPasteNoteNum) + "'";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return false;
        }
         
        return true;
    }

    /**
    * Only used from FormQuickPaste to get all notes for the selected cat.
    */
    public static QuickPasteNote[] getForCat(long cat) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (List == null)
        {
            refreshCache();
        }
         
        ArrayList ALnotes = new ArrayList();
        for (int i = 0;i < List.Length;i++)
        {
            if (List[i].QuickPasteCatNum == cat)
            {
                ALnotes.Add(List[i]);
            }
             
        }
        QuickPasteNote[] retArray = new QuickPasteNote[ALnotes.Count];
        for (int i = 0;i < ALnotes.Count;i++)
        {
            retArray[i] = (QuickPasteNote)ALnotes[i];
        }
        return retArray;
    }

    /**
    * Called on KeyUp from various textBoxes in the program to look for a ?abbrev and attempt to substitute.  Substitutes the text if found.
    */
    public static String substitute(String text, OpenDentBusiness.QuickPasteType type) throws Exception {
        //No need to check RemotingRole; no call to db.
        if (List == null)
        {
            refreshCache();
        }
         
        int typeIndex = QuickPasteCats.getDefaultType(type);
        for (int i = 0;i < List.Length;i++)
        {
            if (StringSupport.equals(List[i].Abbreviation, ""))
            {
                continue;
            }
             
            if (List[i].QuickPasteCatNum != QuickPasteCats.getList()[typeIndex].QuickPasteCatNum)
            {
                continue;
            }
             
            text = Regex.Replace(text, "\\?" + List[i].Abbreviation, List[i].Note);
        }
        return text;
    }

}


