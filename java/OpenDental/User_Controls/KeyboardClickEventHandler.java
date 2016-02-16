//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.User_Controls;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface KeyboardClickEventHandler   
{
    void invoke(Object sender, OpenDental.User_Controls.KeyboardClickEventArgs e) throws Exception ;

    System.Collections.Generic.IList<OpenDental.User_Controls.KeyboardClickEventHandler> getInvocationList() throws Exception ;

}


