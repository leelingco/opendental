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
public class PatientInformationRequester   
{

    private String userTypeField = new String();
    private String userIdField = new String();
    /**
    * 
    */
    public String getUserType() throws Exception {
        return this.userTypeField;
    }

    public void setUserType(String value) throws Exception {
        this.userTypeField = value;
    }

    /**
    * 
    */
    public String getUserId() throws Exception {
        return this.userIdField;
    }

    public void setUserId(String value) throws Exception {
        this.userIdField = value;
    }

}


