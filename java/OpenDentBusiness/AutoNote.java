//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:08 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AutoNote;
import OpenDentBusiness.TableBase;

/**
* A single autonote template.
*/
public class AutoNote  extends TableBase 
{
    /**
    * Primary key
    */
    public long AutoNoteNum = new long();
    /**
    * Name of AutoNote
    */
    public String AutoNoteName = new String();
    /**
    * Was 'ControlsToInc' in previous versions.
    */
    public String MainText = new String();
    // <summary></summary>
    //public string AutoNoteOutput;
    /**
    * 
    */
    public AutoNote copy() throws Exception {
        return (AutoNote)this.MemberwiseClone();
    }

}


