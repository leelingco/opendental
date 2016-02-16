//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SigElement;
import OpenDentBusiness.TableBase;

/**
* These are the actual elements attached to each signal that is sent.  They contain references to the sounds and lights that should result.
*/
public class SigElement  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SigElementNum = new long();
    /**
    * FK to sigelementdef.SigElementDefNum
    */
    public long SigElementDefNum = new long();
    /**
    * FK to signalod.SignalNum.
    */
    public long SignalNum = new long();
    /**
    * 
    */
    public SigElement copy() throws Exception {
        SigElement s = new SigElement();
        s.SigElementNum = SigElementNum;
        s.SigElementDefNum = SigElementDefNum;
        s.SignalNum = SignalNum;
        return s;
    }

}


