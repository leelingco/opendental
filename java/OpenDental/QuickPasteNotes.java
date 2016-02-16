//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.PIn;
import OpenDental.QuickPasteNote;
import OpenDentBusiness.QuickPasteCats;
import OpenDentBusiness.QuickPasteType;

/*=========================================================================================
	=================================== class QuickPasteNotes======================================*/
/**
* 
*/
public class QuickPasteNotes   
{
    /**
    * list of all notes for all categories. Not very useful.
    */
    private static QuickPasteNote[] List = new QuickPasteNote[]();
    /**
    * 
    */
    public static void refresh() throws Exception {
        String command = "SELECT * from quickpastenote " + "ORDER BY ItemOrder";
        DataTable table = General.GetTable(command);
        List = new QuickPasteNote[table.Rows.Count];
        for (int i = 0;i < List.Length;i++)
        {
            List[i] = new QuickPasteNote();
            List[i].QuickPasteNoteNum = PIn.PInt(table.Rows[i][0].ToString());
            List[i].QuickPasteCatNum = PIn.PInt(table.Rows[i][1].ToString());
            List[i].ItemOrder = PIn.PInt(table.Rows[i][2].ToString());
            List[i].Note = PIn.PString(table.Rows[i][3].ToString());
            List[i].Abbreviation = PIn.PString(table.Rows[i][4].ToString());
        }
    }

    /**
    * Only used from FormQuickPaste to get all notes for the selected cat.
    */
    public static QuickPasteNote[] getForCat(int cat) throws Exception {
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
    public static String substitute(String text, QuickPasteType type) throws Exception {
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


