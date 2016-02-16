//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:11 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.SigElementDef;
import OpenDentBusiness.SignalElementType;
import OpenDentBusiness.TableBase;

/**
* This defines the items that will be available for clicking when composing a manual message.  Also, these are referred to in the button definitions as a sequence of elements.
*/
public class SigElementDef  extends TableBase 
{
    /**
    * Primary key.
    */
    public long SigElementDefNum = new long();
    /**
    * If this element should cause a button to light up, this would be the row.  0 means none.
    */
    public byte LightRow = new byte();
    /**
    * If a light row is set, this is the color it will turn when triggered.  Ack sets it back to white.  Note that color and row can be in two separate elements of the same signal.
    */
    public Color LightColor = new Color();
    /**
    * Enum:SignalElementType  0=User,1=Extra,2=Message.
    */
    public SignalElementType SigElementType = SignalElementType.User;
    /**
    * The text that shows for the element, like the user name or the two word message.  No long text is stored here.
    */
    public String SigText = new String();
    /**
    * The sound to play for this element.  Wav file stored in the database in string format until "played".  If empty string, then no sound.
    */
    public String Sound = new String();
    /**
    * The order of this element within the list of the same type.
    */
    public int ItemOrder = new int();
    /**
    * 
    */
    public SigElementDef copy() throws Exception {
        SigElementDef s = new SigElementDef();
        s.SigElementDefNum = SigElementDefNum;
        s.LightRow = LightRow;
        s.LightColor = LightColor;
        s.SigElementType = SigElementType;
        s.SigText = SigText;
        s.Sound = Sound;
        s.ItemOrder = ItemOrder;
        return s;
    }

}


