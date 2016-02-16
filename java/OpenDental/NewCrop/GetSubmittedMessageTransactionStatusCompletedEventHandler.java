//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:34 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.GetSubmittedMessageTransactionStatusCompletedEventArgs;
import OpenDental.NewCrop.GetSubmittedMessageTransactionStatusCompletedEventHandler;

public interface GetSubmittedMessageTransactionStatusCompletedEventHandler   
{
    void invoke(Object sender, GetSubmittedMessageTransactionStatusCompletedEventArgs e) throws Exception ;

    System.Collections.Generic.IList<GetSubmittedMessageTransactionStatusCompletedEventHandler> getInvocationList() throws Exception ;

}


