//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.OutlookButton;

/**
* 
*/
public class ButtonClicked_EventArgs   
{
    private OutlookButton outlookButton = new OutlookButton();
    private boolean cancel = new boolean();
    /**
    * 
    */
    public ButtonClicked_EventArgs(OutlookButton myButton, boolean myCancel) throws Exception {
        outlookButton = myButton;
    }

    /**
    * 
    */
    public OutlookButton getOutlookButton() throws Exception {
        return outlookButton;
    }

    /**
    * Set true to cancel the event.
    */
    public boolean getCancel() throws Exception {
        return cancel;
    }

    public void setCancel(boolean value) throws Exception {
        cancel = value;
    }

}


