//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.RequestCustomerIDCompletedEventArgs;
import OpenDental.localhost.RequestCustomerIDCompletedEventHandler;

public interface RequestCustomerIDCompletedEventHandler   
{
    void invoke(Object sender, RequestCustomerIDCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<RequestCustomerIDCompletedEventHandler> getInvocationList() throws Exception ;

}


