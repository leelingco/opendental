//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:33 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetAccountStatusDetailCompletedEventArgs;
import OpenDental.NewCrop.GetAccountStatusDetailCompletedEventHandler;

public interface GetAccountStatusDetailCompletedEventHandler   
{
    void invoke(Object sender, GetAccountStatusDetailCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetAccountStatusDetailCompletedEventHandler> getInvocationList() throws Exception ;

}


