//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:42 PM
//

package OpenDental.com.dentalxchange.webservices;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
* 
*/
public class Credentials   
{

    private String clientField = new String();
    private String passwordField = new String();
    private String serviceIDField = new String();
    private String usernameField = new String();
    private String versionField = new String();
    /**
    * 
    */
    public String getclient() throws Exception {
        return this.clientField;
    }

    public void setclient(String value) throws Exception {
        this.clientField = value;
    }

    /**
    * 
    */
    public String getpassword() throws Exception {
        return this.passwordField;
    }

    public void setpassword(String value) throws Exception {
        this.passwordField = value;
    }

    /**
    * 
    */
    public String getserviceID() throws Exception {
        return this.serviceIDField;
    }

    public void setserviceID(String value) throws Exception {
        this.serviceIDField = value;
    }

    /**
    * 
    */
    public String getusername() throws Exception {
        return this.usernameField;
    }

    public void setusername(String value) throws Exception {
        this.usernameField = value;
    }

    /**
    * 
    */
    public String getversion() throws Exception {
        return this.versionField;
    }

    public void setversion(String value) throws Exception {
        this.versionField = value;
    }

}


