//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PersonNameType;
import OpenDentBusiness.PrescriberNetwork;
import OpenDentBusiness.PrescriberStatus;


/**
* 
*/
public class MidlevelPrescriberType   
{

    private PersonNameType licensedPrescriberNameField;
    private String deaField = new String();
    private PrescriberStatus prescriberStatusField = PrescriberStatus.Active;
    private boolean prescriberStatusFieldSpecified = new boolean();
    private String upinField = new String();
    private String licenseStateField = new String();
    private String licenseNumberField = new String();
    private PrescriberNetwork prescriberNetworkField = PrescriberNetwork.SureScripts;
    private boolean prescriberNetworkFieldSpecified = new boolean();
    private String prescriberStartDateTimeField = new String();
    private String prescriberStopDateTimeField = new String();
    private String npiField = new String();
    private String idField = new String();
    /**
    * 
    */
    public PersonNameType getLicensedPrescriberName() throws Exception {
        return this.licensedPrescriberNameField;
    }

    public void setLicensedPrescriberName(PersonNameType value) throws Exception {
        this.licensedPrescriberNameField = value;
    }

    /**
    * 
    */
    public String getdea() throws Exception {
        return this.deaField;
    }

    public void setdea(String value) throws Exception {
        this.deaField = value;
    }

    /**
    * 
    */
    public PrescriberStatus getprescriberStatus() throws Exception {
        return this.prescriberStatusField;
    }

    public void setprescriberStatus(PrescriberStatus value) throws Exception {
        this.prescriberStatusField = value;
    }

    /**
    * 
    */
    public boolean getprescriberStatusSpecified() throws Exception {
        return this.prescriberStatusFieldSpecified;
    }

    public void setprescriberStatusSpecified(boolean value) throws Exception {
        this.prescriberStatusFieldSpecified = value;
    }

    /**
    * 
    */
    public String getupin() throws Exception {
        return this.upinField;
    }

    public void setupin(String value) throws Exception {
        this.upinField = value;
    }

    /**
    * 
    */
    public String getlicenseState() throws Exception {
        return this.licenseStateField;
    }

    public void setlicenseState(String value) throws Exception {
        this.licenseStateField = value;
    }

    /**
    * 
    */
    public String getlicenseNumber() throws Exception {
        return this.licenseNumberField;
    }

    public void setlicenseNumber(String value) throws Exception {
        this.licenseNumberField = value;
    }

    /**
    * 
    */
    public PrescriberNetwork getprescriberNetwork() throws Exception {
        return this.prescriberNetworkField;
    }

    public void setprescriberNetwork(PrescriberNetwork value) throws Exception {
        this.prescriberNetworkField = value;
    }

    /**
    * 
    */
    public boolean getprescriberNetworkSpecified() throws Exception {
        return this.prescriberNetworkFieldSpecified;
    }

    public void setprescriberNetworkSpecified(boolean value) throws Exception {
        this.prescriberNetworkFieldSpecified = value;
    }

    /**
    * 
    */
    public String getprescriberStartDateTime() throws Exception {
        return this.prescriberStartDateTimeField;
    }

    public void setprescriberStartDateTime(String value) throws Exception {
        this.prescriberStartDateTimeField = value;
    }

    /**
    * 
    */
    public String getprescriberStopDateTime() throws Exception {
        return this.prescriberStopDateTimeField;
    }

    public void setprescriberStopDateTime(String value) throws Exception {
        this.prescriberStopDateTimeField = value;
    }

    /**
    * 
    */
    public String getnpi() throws Exception {
        return this.npiField;
    }

    public void setnpi(String value) throws Exception {
        this.npiField = value;
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


