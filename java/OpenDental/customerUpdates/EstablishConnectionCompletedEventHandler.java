//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.EstablishConnectionCompletedEventArgs;
import OpenDental.customerUpdates.EstablishConnectionCompletedEventHandler;

public interface EstablishConnectionCompletedEventHandler   
{
    void invoke(Object sender, EstablishConnectionCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<EstablishConnectionCompletedEventHandler> getInvocationList() throws Exception ;

}


