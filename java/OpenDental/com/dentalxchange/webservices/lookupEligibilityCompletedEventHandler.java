//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.lookupEligibilityCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.lookupEligibilityCompletedEventHandler;

public interface lookupEligibilityCompletedEventHandler   
{
    void invoke(Object sender, lookupEligibilityCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<lookupEligibilityCompletedEventHandler> getInvocationList() throws Exception ;

}


