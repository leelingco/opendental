//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.ButtonClickedEventHandler;

public interface ButtonClickedEventHandler   
{
    void invoke(Object sender, OpenDental.ButtonClicked_EventArgs e) throws Exception ;

    System.Collections.Generic.IList<ButtonClickedEventHandler> getInvocationList() throws Exception ;

}


