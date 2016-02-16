//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.AddressType;


/**
* 
*/
public class AccountType   
{

    private String accountNameField = new String();
    private String siteIDField = new String();
    private AddressType accountAddressField;
    private String accountPrimaryPhoneNumberField = new String();
    private String accountPrimaryFaxNumberField = new String();
    private String idField = new String();
    /**
    * 
    */
    public String getaccountName() throws Exception {
        return this.accountNameField;
    }

    public void setaccountName(String value) throws Exception {
        this.accountNameField = value;
    }

    /**
    * 
    */
    public String getsiteID() throws Exception {
        return this.siteIDField;
    }

    public void setsiteID(String value) throws Exception {
        this.siteIDField = value;
    }

    /**
    * 
    */
    public AddressType getAccountAddress() throws Exception {
        return this.accountAddressField;
    }

    public void setAccountAddress(AddressType value) throws Exception {
        this.accountAddressField = value;
    }

    /**
    * 
    */
    public String getaccountPrimaryPhoneNumber() throws Exception {
        return this.accountPrimaryPhoneNumberField;
    }

    public void setaccountPrimaryPhoneNumber(String value) throws Exception {
        this.accountPrimaryPhoneNumberField = value;
    }

    /**
    * 
    */
    public String getaccountPrimaryFaxNumber() throws Exception {
        return this.accountPrimaryFaxNumberField;
    }

    public void setaccountPrimaryFaxNumber(String value) throws Exception {
        this.accountPrimaryFaxNumberField = value;
    }

    /**
    * 
    */
    public String getID() throws Exception {
        return this.idField;
    }

    public void setID(String value) throws Exception {
        this.idField = value;
    }

}


