//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.SigButDef;
import OpenDentBusiness.Signalod;

/**
* 
*/
public class SignalButtonState   
{
    /**
    * This is also present in the def, but this makes it easier to access.
    */
    public String Text = new String();
    /**
    * The def assigned to this index.
    */
    public SigButDef ButDef;
    /**
    * 
    */
    public Color CurrentColor = new Color();
    /**
    * 
    */
    public OpenDental.UI.ToolBarButtonState State = OpenDental.UI.ToolBarButtonState.Normal;
    /**
    * If this button is lit up, then this will contain the signal that caused it.  That way, when user clicks on the button to ack, the signal object in the db can be ack'd properly.
    */
    public Signalod ActiveSignal;
}


