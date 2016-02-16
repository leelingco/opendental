//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.PayConnectService;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class Status   
{

    private int codeField = new int();
    private String descriptionField = new String();
    /**
    * 
    */
    public int getcode() throws Exception {
        return this.codeField;
    }

    public void setcode(int value) throws Exception {
        this.codeField = value;
    }

    /**
    * 
    */
    public String getdescription() throws Exception {
        return this.descriptionField;
    }

    public void setdescription(String value) throws Exception {
        this.descriptionField = value;
    }

}


