//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ODToolBarButtonClickEventHandler   
{
    void invoke(Object sender, OpenDental.UI.ODToolBarButtonClickEventArgs e) throws Exception ;

    System.Collections.Generic.IList<OpenDental.UI.ODToolBarButtonClickEventHandler> getInvocationList() throws Exception ;

}


