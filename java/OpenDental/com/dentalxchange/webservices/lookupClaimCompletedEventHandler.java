//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.lookupClaimCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupClaimCompletedEventHandler;

public interface lookupClaimCompletedEventHandler   
{
    void invoke(Object sender, lookupClaimCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<lookupClaimCompletedEventHandler> getInvocationList() throws Exception ;

}


