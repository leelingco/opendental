//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;

/**
* Quick paste categories are used by the quick paste notes feature.
*/
public class QuickPasteCat  extends TableBase 
{
    /**
    * Primary key.
    */
    public long QuickPasteCatNum = new long();
    /**
    * .
    */
    public String Description = new String();
    /**
    * The order of this category within the list. 0-based.
    */
    public int ItemOrder = new int();
    /**
    * Enum:QuickPasteType  Each Category can be set to be the default category for multiple types of notes. Stored as integers separated by commas.
    */
    public String DefaultForTypes = new String();
}


