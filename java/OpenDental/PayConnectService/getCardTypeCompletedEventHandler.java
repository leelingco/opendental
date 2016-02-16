//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.getCardTypeCompletedEventArgs;
import OpenDental.PayConnectService.getCardTypeCompletedEventHandler;

public interface getCardTypeCompletedEventHandler   
{
    void invoke(Object sender, getCardTypeCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<getCardTypeCompletedEventHandler> getInvocationList() throws Exception ;

}


