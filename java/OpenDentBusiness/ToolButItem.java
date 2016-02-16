//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.TableBase;
import OpenDentBusiness.ToolBarsAvail;

/**
* Each row represents one toolbar button to be placed on a toolbar and linked to a program.
*/
public class ToolButItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ToolButItemNum = new long();
    /**
    * FK to program.ProgramNum.
    */
    public long ProgramNum = new long();
    /**
    * Enum:ToolBarsAvail The toolbar to show the button on.
    */
    public ToolBarsAvail ToolBar = ToolBarsAvail.AccountModule;
    /**
    * The text to show on the toolbar button.
    */
    public String ButtonText = new String();
}


//later include ComputerName.  If blank, then show on all computers.
//also later, include an image.