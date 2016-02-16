//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Keeps track of column widths in Wiki Lists.
*/
public class WikiListHeaderWidth  extends TableBase 
{
    /**
    * Primary key.
    */
    public long WikiListHeaderWidthNum = new long();
    /**
    * Name of the list that this header belongs to.  Tablename without the prefix.
    */
    public String ListName = new String();
    /**
    * Name of the column that this header belongs to.
    */
    public String ColName = new String();
    /**
    * Width in pixels of column.
    */
    public int ColWidth = new int();
}


/**
* //Text to be displayed, less restrictive than MySQL column names.  We may add this some day.
*/
//public string DisplayText;