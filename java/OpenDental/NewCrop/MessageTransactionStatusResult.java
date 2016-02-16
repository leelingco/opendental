//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.MessageTransactionStatusDetail;
import OpenDental.NewCrop.Result;


/**
* 
*/
public class MessageTransactionStatusResult   
{

    private Result resultField;
    private MessageTransactionStatusDetail messageTransactionStatusDetailField;
    /**
    * 
    */
    public Result getresult() throws Exception {
        return this.resultField;
    }

    public void setresult(Result value) throws Exception {
        this.resultField = value;
    }

    /**
    * 
    */
    public MessageTransactionStatusDetail getmessageTransactionStatusDetail() throws Exception {
        return this.messageTransactionStatusDetailField;
    }

    public void setmessageTransactionStatusDetail(MessageTransactionStatusDetail value) throws Exception {
        this.messageTransactionStatusDetailField = value;
    }

}


