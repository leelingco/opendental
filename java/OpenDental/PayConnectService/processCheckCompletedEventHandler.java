//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.processCheckCompletedEventArgs;
import OpenDental.PayConnectService.processCheckCompletedEventHandler;

public interface processCheckCompletedEventHandler   
{
    void invoke(Object sender, processCheckCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<processCheckCompletedEventHandler> getInvocationList() throws Exception ;

}


