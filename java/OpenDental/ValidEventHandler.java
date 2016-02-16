//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:22 PM
//

package OpenDental;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ValidEventHandler   
{
    void invoke(OpenDental.ValidEventArgs e) throws Exception ;

    System.Collections.Generic.IList<OpenDental.ValidEventHandler> getInvocationList() throws Exception ;

}


