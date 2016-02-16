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
public class LocationType   
{

    private String locationNameField = new String();
    private String locationShortNameField = new String();
    private AddressType locationAddressField;
    private String primaryPhoneNumberField = new String();
    private String primaryFaxNumberField = new String();
    private String pharmacyContactNumberField = new String();
    private String idField = new String();
    /**
    * 
    */
    public String getlocationName() throws Exception {
        return this.locationNameField;
    }

    public void setlocationName(String value) throws Exception {
        this.locationNameField = value;
    }

    /**
    * 
    */
    public String getlocationShortName() throws Exception {
        return this.locationShortNameField;
    }

    public void setlocationShortName(String value) throws Exception {
        this.locationShortNameField = value;
    }

    /**
    * 
    */
    public AddressType getLocationAddress() throws Exception {
        return this.locationAddressField;
    }

    public void setLocationAddress(AddressType value) throws Exception {
        this.locationAddressField = value;
    }

    /**
    * 
    */
    public String getprimaryPhoneNumber() throws Exception {
        return this.primaryPhoneNumberField;
    }

    public void setprimaryPhoneNumber(String value) throws Exception {
        this.primaryPhoneNumberField = value;
    }

    /**
    * 
    */
    public String getprimaryFaxNumber() throws Exception {
        return this.primaryFaxNumberField;
    }

    public void setprimaryFaxNumber(String value) throws Exception {
        this.primaryFaxNumberField = value;
    }

    /**
    * 
    */
    public String getpharmacyContactNumber() throws Exception {
        return this.pharmacyContactNumberField;
    }

    public void setpharmacyContactNumber(String value) throws Exception {
        this.pharmacyContactNumberField = value;
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


