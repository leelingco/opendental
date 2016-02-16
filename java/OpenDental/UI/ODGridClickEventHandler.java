//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:12 PM
//

package OpenDental.UI;

import CS2JNet.JavaSupport.util.ListSupport;
import CS2JNet.System.LCC.Disposable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ODGridClickEventHandler   
{
    void invoke(Object sender, OpenDental.UI.ODGridClickEventArgs e) throws Exception ;

    System.Collections.Generic.IList<OpenDental.UI.ODGridClickEventHandler> getInvocationList() throws Exception ;

}


