//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.ProcButton;
import OpenDentBusiness.TableBase;

/**
* The 'buttons' to show in the Chart module.  They must have items attached in order to do anything.
*/
public class ProcButton  extends TableBase 
{
    /**
    * Primary key
    */
    public long ProcButtonNum = new long();
    /**
    * The text to show on the button.
    */
    public String Description = new String();
    /**
    * Order that they will show in the Chart module.
    */
    public int ItemOrder = new int();
    /**
    * FK to definition.DefNum.
    */
    public long Category = new long();
    /**
    * If no image, then the clob will be an empty string.  In this case, the bitmap will be null when loaded from the database.
    */
    public String ButtonImage = new String();
    /**
    * 
    */
    public ProcButton copy() throws Exception {
        ProcButton p = new ProcButton();
        p.ProcButtonNum = ProcButtonNum;
        p.Description = Description;
        p.ItemOrder = ItemOrder;
        p.Category = Category;
        p.ButtonImage = ButtonImage;
        return p;
    }

}


