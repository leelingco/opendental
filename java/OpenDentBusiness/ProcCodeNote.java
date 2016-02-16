//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProcCodeNote;
import OpenDentBusiness.TableBase;

/**
* Stores the default note and time increments for one procedure code for one provider.  That way, an unlimited number of providers can each have different notes and times.  These notes and times override the defaults which are part of the procedurecode table.  So, for single provider offices, there will be no change to the current interface.
*/
public class ProcCodeNote  extends TableBase 
{
    /**
    * Primary Key.
    */
    public long ProcCodeNoteNum = new long();
    /**
    * FK to procedurecode.CodeNum.
    */
    public long CodeNum = new long();
    /**
    * FK to provider.ProvNum.
    */
    public long ProvNum = new long();
    /**
    * The note.
    */
    public String Note = new String();
    /**
    * X's and /'s describe Dr's time and assistant's time in the same increments as the user has set.
    */
    public String ProcTime = new String();
    /**
    * Returns a copy of this ProcCodeNote
    */
    public ProcCodeNote copy() throws Exception {
        return (ProcCodeNote)MemberwiseClone();
    }

}


