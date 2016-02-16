//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* 
*/
public class ODToolBarButtonClickEventArgs   
{
    //private OutlookButton outlookButton = null;//this is how to do it if a class instead of struct
    private OpenDental.UI.ODToolBarButton button;
    /**
    * @param myButton
    */
    public ODToolBarButtonClickEventArgs(OpenDental.UI.ODToolBarButton myButton) throws Exception {
        button = myButton;
    }

    /**
    * 
    */
    public OpenDental.UI.ODToolBarButton getButton() throws Exception {
        return button;
    }

}


