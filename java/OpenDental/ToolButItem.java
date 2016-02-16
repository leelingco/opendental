//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:26 PM
//

package OpenDental;

import OpenDentBusiness.ToolBarsAvail;

/**
* Each row represents one toolbar button to be placed on a toolbar and linked to a program.
*/
public class ToolButItem   
{
    public ToolButItem() {
    }

    /**
    * Primary key.
    */
    public int ToolButItemNum = new int();
    /**
    * FK to program.ProgramNum.
    */
    public int ProgramNum = new int();
    /**
    * Enum:ToolBarsAvail The toolbar to show the button on.
    */
    public ToolBarsAvail ToolBar = ToolBarsAvail.AccountModule;
    /**
    * The text to show on the toolbar button.
    */
    public String ButtonText = new String();
}


