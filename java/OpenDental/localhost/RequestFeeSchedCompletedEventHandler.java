//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:31 PM
//

package OpenDental.localhost;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.localhost.RequestFeeSchedCompletedEventArgs;
import OpenDental.localhost.RequestFeeSchedCompletedEventHandler;

public interface RequestFeeSchedCompletedEventHandler   
{
    void invoke(Object sender, RequestFeeSchedCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<RequestFeeSchedCompletedEventHandler> getInvocationList() throws Exception ;

}


