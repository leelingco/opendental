//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SigButDef;
import OpenDentBusiness.SigButDefElement;
import OpenDentBusiness.TableBase;

/**
* This defines the light buttons on the left of the main screen.
*/
public class SigButDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SigButDefNum = new long();
    /**
    * The text on the button
    */
    public String ButtonText = new String();
    /**
    * 0-based index defines the order of the buttons.
    */
    public int ButtonIndex = new int();
    /**
    * 0=none, or 1-9. The cell in the 3x3 tic-tac-toe main program icon that is to be synched with this button.  It will light up or clear whenever this button lights or clears.
    */
    public byte SynchIcon = new byte();
    /**
    * Blank for the default buttons.  Or contains the computer name for the buttons that override the defaults.
    */
    public String ComputerName = new String();
    /**
    * Not a database field.  The sounds and lights attached to the button.
    */
    public SigButDefElement[] ElementList = new SigButDefElement[]();
    /**
    * 
    */
    public SigButDef copy() throws Exception {
        SigButDef s = new SigButDef();
        s.SigButDefNum = SigButDefNum;
        s.ButtonText = ButtonText;
        s.ButtonIndex = ButtonIndex;
        s.SynchIcon = SynchIcon;
        s.ComputerName = ComputerName;
        s.ElementList = new SigButDefElement[ElementList.Length];
        ElementList.CopyTo(s.ElementList, 0);
        return s;
    }

}


