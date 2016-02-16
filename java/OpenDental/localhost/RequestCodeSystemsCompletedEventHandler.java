//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.RequestCodeSystemsCompletedEventArgs;
import OpenDental.localhost.RequestCodeSystemsCompletedEventHandler;

public interface RequestCodeSystemsCompletedEventHandler   
{
    void invoke(Object sender, RequestCodeSystemsCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<RequestCodeSystemsCompletedEventHandler> getInvocationList() throws Exception ;

}


