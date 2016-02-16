//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.SmartCards;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.SmartCards.SmartCardStateChangedEventArgs;
import OpenDental.SmartCards.SmartCardStateChangedEventHandler;

public interface SmartCardStateChangedEventHandler   
{
    void invoke(Object sender, SmartCardStateChangedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<SmartCardStateChangedEventHandler> getInvocationList() throws Exception ;

}


