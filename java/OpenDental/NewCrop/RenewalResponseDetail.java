//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class RenewalResponseDetail   
{

    private String renewalRequestIdentifierField = new String();
    private String statusField = new String();
    private String messageField = new String();
    private String sentTimestampField = new String();
    /**
    * 
    */
    public String getRenewalRequestIdentifier() throws Exception {
        return this.renewalRequestIdentifierField;
    }

    public void setRenewalRequestIdentifier(String value) throws Exception {
        this.renewalRequestIdentifierField = value;
    }

    /**
    * 
    */
    public String getStatus() throws Exception {
        return this.statusField;
    }

    public void setStatus(String value) throws Exception {
        this.statusField = value;
    }

    /**
    * 
    */
    public String getMessage() throws Exception {
        return this.messageField;
    }

    public void setMessage(String value) throws Exception {
        this.messageField = value;
    }

    /**
    * 
    */
    public String getSentTimestamp() throws Exception {
        return this.sentTimestampField;
    }

    public void setSentTimestamp(String value) throws Exception {
        this.sentTimestampField = value;
    }

}


