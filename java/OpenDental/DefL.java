//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import OpenDental.Lan;
import OpenDentBusiness.Def;
import OpenDentBusiness.Defs;

/**
* Handles database commands related to the definition table in the db.  The related DefB class is referenced frequently from many different areas of the program.
*/
public class DefL   
{
    /**
    * Returns the new selected.
    */
    public static int moveUp(boolean isSelected, int selected, Def[] list) throws Exception {
        if (isSelected == false)
        {
            MessageBox.Show(Lan.g("Defs","Please select an item first."));
            return selected;
        }
         
        if (selected == 0)
        {
            return selected;
        }
         
        Defs.SetOrder(selected - 1, list[selected].ItemOrder, list);
        Defs.SetOrder(selected, list[selected].ItemOrder - 1, list);
        selected -= 1;
        return selected;
    }

    /**
    * 
    */
    public static int moveDown(boolean isSelected, int selected, Def[] list) throws Exception {
        if (isSelected == false)
        {
            MessageBox.Show(Lan.g("Defs","Please select an item first."));
            return selected;
        }
         
        if (selected == list.Length - 1)
        {
            return selected;
        }
         
        Defs.SetOrder(selected + 1, list[selected].ItemOrder, list);
        Defs.SetOrder(selected, list[selected].ItemOrder + 1, list);
        selected += 1;
        return selected;
    }

}


