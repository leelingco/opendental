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
public class Credentials   
{

    private String clientField = new String();
    private String serviceIDField = new String();
    private String usernameField = new String();
    private String passwordField = new String();
    private String versionField = new String();
    /**
    * 
    */
    public String getClient() throws Exception {
        return this.clientField;
    }

    public void setClient(String value) throws Exception {
        this.clientField = value;
    }

    /**
    * 
    */
    public String getServiceID() throws Exception {
        return this.serviceIDField;
    }

    public void setServiceID(String value) throws Exception {
        this.serviceIDField = value;
    }

    /**
    * 
    */
    public String getUsername() throws Exception {
        return this.usernameField;
    }

    public void setUsername(String value) throws Exception {
        this.usernameField = value;
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


