//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.getMerchantInfoCompletedEventArgs;
import OpenDental.PayConnectService.getMerchantInfoCompletedEventHandler;

public interface getMerchantInfoCompletedEventHandler   
{
    void invoke(Object sender, getMerchantInfoCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<getMerchantInfoCompletedEventHandler> getInvocationList() throws Exception ;

}


