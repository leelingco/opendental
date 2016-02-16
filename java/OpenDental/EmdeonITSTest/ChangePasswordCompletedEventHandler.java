//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITSTest;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITSTest.ChangePasswordCompletedEventArgs;
import OpenDental.EmdeonITSTest.ChangePasswordCompletedEventHandler;

public interface ChangePasswordCompletedEventHandler   
{
    void invoke(Object sender, ChangePasswordCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<ChangePasswordCompletedEventHandler> getInvocationList() throws Exception ;

}


