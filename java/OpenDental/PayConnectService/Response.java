//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.merchantInfoResponse;
import OpenDental.PayConnectService.Status;
import OpenDental.PayConnectService.transResponse;


/**
* 
*/
public abstract class Response   
{

    private Status statusField;
    private String[] messagesField = new String[]();
    /**
    * 
    */
    public Status getStatus() throws Exception {
        return this.statusField;
    }

    public void setStatus(Status value) throws Exception {
        this.statusField = value;
    }

    /**
    * 
    */
    public String[] getMessages() throws Exception {
        return this.messagesField;
    }

    public void setMessages(String[] value) throws Exception {
        this.messagesField = value;
    }

}


