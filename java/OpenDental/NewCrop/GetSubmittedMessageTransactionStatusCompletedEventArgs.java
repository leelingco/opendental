//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.MessageTransactionStatusResult;


/**
* 
*/
public class GetSubmittedMessageTransactionStatusCompletedEventArgs  extends System.ComponentModel.AsyncCompletedEventArgs 
{

    private Object[] results = new Object[]();
    public GetSubmittedMessageTransactionStatusCompletedEventArgs(Object[] results, System.Exception exception, boolean cancelled, Object userState) throws Exception {
        super(exception, cancelled, userState);
        this.results = results;
    }

    /**
    * 
    */
    public MessageTransactionStatusResult getResult() throws Exception {
        this.RaiseExceptionIfNecessary();
        return ((MessageTransactionStatusResult)(this.results[0]));
    }

}


