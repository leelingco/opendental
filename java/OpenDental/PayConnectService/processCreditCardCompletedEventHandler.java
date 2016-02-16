//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.processCreditCardCompletedEventArgs;
import OpenDental.PayConnectService.processCreditCardCompletedEventHandler;

public interface processCreditCardCompletedEventHandler   
{
    void invoke(Object sender, processCreditCardCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<processCreditCardCompletedEventHandler> getInvocationList() throws Exception ;

}


