//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:29 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.com.dentalxchange.webservices.updateTerminalCompletedEventArgs;
import OpenDental.com.dentalxchange.webservices.updateTerminalCompletedEventHandler;

public interface updateTerminalCompletedEventHandler   
{
    void invoke(Object sender, updateTerminalCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<updateTerminalCompletedEventHandler> getInvocationList() throws Exception ;

}


