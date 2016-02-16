//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:25 PM
//

package OpenDental;

import OpenDental.POut;
import OpenDentBusiness.MiscData;

/**
* 
*/
public class QuickPasteNote   
{
    /**
    * Primary key.
    */
    public int QuickPasteNoteNum = new int();
    /**
    * FK to quickpastecat.QuickPasteCatNum.  Keeps track of which category this note is in.
    */
    public int QuickPasteCatNum = new int();
    /**
    * The order of this note within it's category. 0-based.
    */
    public int ItemOrder = new int();
    /**
    * The actual note. Can be multiple lines and possibly very long.
    */
    public String Note = new String();
    /**
    * The abbreviation which will automatically substitute when preceded by a ?.
    */
    public String Abbreviation = new String();
    /**
    * 
    */
    public void insert() throws Exception {
        if (PrefB.RandomKeys)
        {
            QuickPasteNoteNum = MiscData.GetKey("quickpastenote", "QuickPasteNoteNum");
        }
         
        String command = "INSERT INTO quickpastenote (";
        if (PrefB.RandomKeys)
        {
            command += "QuickPasteNoteNum,";
        }
         
        command += "QuickPasteCatNum,ItemOrder,Note,Abbreviation) VALUES(";
        if (PrefB.RandomKeys)
        {
            command += "'" + POut.pInt(QuickPasteNoteNum) + "', ";
        }
         
        command += "'" + POut.pInt(QuickPasteCatNum) + "', " + "'" + POut.pInt(ItemOrder) + "', " + "'" + POut.pString(Note) + "', " + "'" + POut.pString(Abbreviation) + "')";
        if (PrefB.RandomKeys)
        {
            General.NonQ(command);
        }
        else
        {
            QuickPasteNoteNum = General.NonQ(command, true);
        } 
    }

    /**
    * 
    */
    public void update() throws Exception {
        String command = "UPDATE quickpastenote SET " + "QuickPasteCatNum='" + POut.pInt(QuickPasteCatNum) + "'" + ",ItemOrder = '" + POut.pInt(ItemOrder) + "'" + ",Note = '" + POut.pString(Note) + "'" + ",Abbreviation = '" + POut.pString(Abbreviation) + "'" + " WHERE QuickPasteNoteNum = '" + POut.pInt(QuickPasteNoteNum) + "'";
        General.NonQ(command);
    }

    /**
    * 
    */
    public void delete() throws Exception {
        String command = "DELETE from quickpastenote WHERE QuickPasteNoteNum = '" + POut.pInt(QuickPasteNoteNum) + "'";
        General.NonQ(command);
    }

    /**
    * When saving an abbrev, this makes sure that the abbreviation is not already in use.
    */
    public boolean abbrAlreadyInUse() throws Exception {
        String command = "SELECT * FROM quickpastenote WHERE " + "Abbreviation='" + POut.pString(Abbreviation) + "' " + "AND QuickPasteNoteNum != '" + POut.pInt(QuickPasteNoteNum) + "'";
        DataTable table = General.GetTable(command);
        if (table.Rows.Count == 0)
        {
            return false;
        }
         
        return true;
    }

}


