//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.customerUpdates;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.customerUpdates.PracticeTitleResetCompletedEventHandler;

public interface PracticeTitleResetCompletedEventHandler   
{
    void invoke(Object sender, System.ComponentModel.AsyncCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<PracticeTitleResetCompletedEventHandler> getInvocationList() throws Exception ;

}


