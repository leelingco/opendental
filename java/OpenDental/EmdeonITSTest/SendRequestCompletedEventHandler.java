//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITSTest;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITSTest.SendRequestCompletedEventArgs;
import OpenDental.EmdeonITSTest.SendRequestCompletedEventHandler;

public interface SendRequestCompletedEventHandler   
{
    void invoke(Object sender, SendRequestCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<SendRequestCompletedEventHandler> getInvocationList() throws Exception ;

}


