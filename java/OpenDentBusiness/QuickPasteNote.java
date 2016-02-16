//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Template for quick pasted note feature.
*/
public class QuickPasteNote  extends TableBase 
{
    /**
    * Primary key.
    */
    public long QuickPasteNoteNum = new long();
    /**
    * FK to quickpastecat.QuickPasteCatNum.  Keeps track of which category this note is in.
    */
    public long QuickPasteCatNum = new long();
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
}


