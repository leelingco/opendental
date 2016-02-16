//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:13 PM
//

package OpenDentBusiness.OpenDentalServer;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDentBusiness.OpenDentalServer.ProcessRequestCompletedEventArgs;
import OpenDentBusiness.OpenDentalServer.ProcessRequestCompletedEventHandler;

public interface ProcessRequestCompletedEventHandler   
{
    void invoke(Object sender, ProcessRequestCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<ProcessRequestCompletedEventHandler> getInvocationList() throws Exception ;

}


