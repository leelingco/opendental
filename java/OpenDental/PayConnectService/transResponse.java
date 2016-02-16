//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.PayConnectService.Response;


/**
* 
*/
public class transResponse  extends Response 
{

    private String authCodeField = new String();
    private String refNumberField = new String();
    /**
    * 
    */
    public String getAuthCode() throws Exception {
        return this.authCodeField;
    }

    public void setAuthCode(String value) throws Exception {
        this.authCodeField = value;
    }

    /**
    * 
    */
    public String getRefNumber() throws Exception {
        return this.refNumberField;
    }

    public void setRefNumber(String value) throws Exception {
        this.refNumberField = value;
    }

}


