//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.EmdeonITS;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class ITSReturn   
{

    private int errorCodeField = new int();
    private String responseField = new String();
    /**
    * 
    */
    public int getErrorCode() throws Exception {
        return this.errorCodeField;
    }

    public void setErrorCode(int value) throws Exception {
        this.errorCodeField = value;
    }

    /**
    * 
    */
    public String getResponse() throws Exception {
        return this.responseField;
    }

    public void setResponse(String value) throws Exception {
        this.responseField = value;
    }

}


