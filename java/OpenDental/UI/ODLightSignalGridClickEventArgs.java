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
public class ODLightSignalGridClickEventArgs   
{
    private int buttonIndex = new int();
    private SigButDef buttonDef;
    private Signalod activeSignal;
    /**
    * @param myButton
    */
    public ODLightSignalGridClickEventArgs(int myButton, SigButDef myDef, Signalod mySignal) throws Exception {
        buttonIndex = myButton;
        buttonDef = myDef;
        activeSignal = mySignal;
    }

    /**
    * Remember that this is the 0-based index, but the database uses 1-based.
    */
    public int getButtonIndex() throws Exception {
        return buttonIndex;
    }

    /**
    * 
    */
    public SigButDef getButtonDef() throws Exception {
        return buttonDef;
    }

    /**
    * 
    */
    public Signalod getActiveSignal() throws Exception {
        return activeSignal;
    }

}


