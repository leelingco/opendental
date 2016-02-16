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
public class RenewalSummaryV4   
{

    private String renewalRequestGuidField = new String();
    private String receivedTimestampField = new String();
    /**
    * 
    */
    public String getRenewalRequestGuid() throws Exception {
        return this.renewalRequestGuidField;
    }

    public void setRenewalRequestGuid(String value) throws Exception {
        this.renewalRequestGuidField = value;
    }

    /**
    * 
    */
    public String getReceivedTimestamp() throws Exception {
        return this.receivedTimestampField;
    }

    public void setReceivedTimestamp(String value) throws Exception {
        this.receivedTimestampField = value;
    }

}


