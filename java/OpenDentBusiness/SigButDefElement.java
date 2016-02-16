//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.SigButDefElement;
import OpenDentBusiness.TableBase;

/**
* This table stores references to the sequence of sounds and lights that should get sent out with a button push.
*/
public class SigButDefElement  extends TableBase 
{
    /**
    * Primary key.
    */
    public long ElementNum = new long();
    /**
    * FK to sigbutdef.SigButDefNum  A few elements are usually attached to a single button.
    */
    public long SigButDefNum = new long();
    /**
    * FK to sigelementdef.SigElementDefNum, which contains the actual sound or light.
    */
    public long SigElementDefNum = new long();
    /**
    * 
    */
    public SigButDefElement copy() throws Exception {
        SigButDefElement s = new SigButDefElement();
        s.ElementNum = ElementNum;
        s.SigButDefNum = SigButDefNum;
        s.SigElementDefNum = SigElementDefNum;
        return s;
    }

}


