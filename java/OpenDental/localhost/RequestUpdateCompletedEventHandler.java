//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.RequestUpdateCompletedEventArgs;
import OpenDental.localhost.RequestUpdateCompletedEventHandler;

public interface RequestUpdateCompletedEventHandler   
{
    void invoke(Object sender, RequestUpdateCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<RequestUpdateCompletedEventHandler> getInvocationList() throws Exception ;

}


