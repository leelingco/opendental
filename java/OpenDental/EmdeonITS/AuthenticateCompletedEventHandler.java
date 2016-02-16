//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:30 PM
//

package OpenDental.EmdeonITS;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.EmdeonITS.AuthenticateCompletedEventArgs;
import OpenDental.EmdeonITS.AuthenticateCompletedEventHandler;

public interface AuthenticateCompletedEventHandler   
{
    void invoke(Object sender, AuthenticateCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<AuthenticateCompletedEventHandler> getInvocationList() throws Exception ;

}


