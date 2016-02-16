//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.WebSheets;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.WebSheets.ServiceExistsCompletedEventArgs;
import OpenDental.WebSheets.ServiceExistsCompletedEventHandler;

public interface ServiceExistsCompletedEventHandler   
{
    void invoke(Object sender, ServiceExistsCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<ServiceExistsCompletedEventHandler> getInvocationList() throws Exception ;

}


