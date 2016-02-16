//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.PersonNameType;


/**
* 
*/
public class StaffType   
{

    private PersonNameType staffNameField;
    private String licenseField = new String();
    private String npiField = new String();
    private String idField = new String();
    /**
    * 
    */
    public PersonNameType getStaffName() throws Exception {
        return this.staffNameField;
    }

    public void setStaffName(PersonNameType value) throws Exception {
        this.staffNameField = value;
    }

    /**
    * 
    */
    public String getlicense() throws Exception {
        return this.licenseField;
    }

    public void setlicense(String value) throws Exception {
        this.licenseField = value;
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


