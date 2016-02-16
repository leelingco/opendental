//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.ProcButtonItem;
import OpenDentBusiness.TableBase;

/**
* Attached to procbuttons.  These tell the program what to do when a user clicks on a button.  There are two types: proccodes or autocodes.
*/
public class ProcButtonItem  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ProcButtonItemNum = new long();
    /**
    * FK to procbutton.ProcButtonNum.
    */
    public long ProcButtonNum = new long();
    /**
    * Do not use.
    */
    public String OldCode = new String();
    /**
    * FK to autocode.AutoCodeNum.  0 if this is a procedure code.
    */
    public long AutoCodeNum = new long();
    /**
    * FK to procedurecode.CodeNum.  0 if this is an autocode.
    */
    public long CodeNum = new long();
    /**
    * 
    */
    public ProcButtonItem copy() throws Exception {
        ProcButtonItem p = new ProcButtonItem();
        p.ProcButtonItemNum = ProcButtonItemNum;
        p.ProcButtonNum = ProcButtonNum;
        //p.OldCode=OldCode;
        p.AutoCodeNum = AutoCodeNum;
        p.CodeNum = CodeNum;
        return p;
    }

}


