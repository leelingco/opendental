//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoNote;
import OpenDentBusiness.TableBase;

/**
* In the program, this is now called an autonote prompt.
*/
public class AutoNoteControl  extends TableBase 
{
    /**
    * Primary key
    */
    public long AutoNoteControlNum = new long();
    /**
    * The description of the prompt as it will be referred to from other windows.
    */
    public String Descript = new String();
    /**
    * 'Text', 'OneResponse', or 'MultiResponse'.  More types to be added later.
    */
    public String ControlType = new String();
    /**
    * The prompt text.
    */
    public String ControlLabel = new String();
    /**
    * For TextBox, this is the default text.  For a ComboBox, this is the list of possible responses, one per line.
    */
    public String ControlOptions = new String();
    /**
    * 
    */
    public AutoNote copy() throws Exception {
        return (AutoNote)this.MemberwiseClone();
    }

}


