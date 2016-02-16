//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class Credentials   
{

    private String partnerNameField = new String();
    private String nameField = new String();
    private String passwordField = new String();
    /**
    * 
    */
    public String getPartnerName() throws Exception {
        return this.partnerNameField;
    }

    public void setPartnerName(String value) throws Exception {
        this.partnerNameField = value;
    }

    /**
    * 
    */
    public String getName() throws Exception {
        return this.nameField;
    }

    public void setName(String value) throws Exception {
        this.nameField = value;
    }

    /**
    * 
    */
    public String getPassword() throws Exception {
        return this.passwordField;
    }

    public void setPassword(String value) throws Exception {
        this.passwordField = value;
    }

}


