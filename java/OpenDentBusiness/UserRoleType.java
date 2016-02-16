//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CurrentFeatureType;
import OpenDentBusiness.RoleType;
import OpenDentBusiness.UserType;


/**
* 
*/
public class UserRoleType   
{

    private UserType userField = UserType.LicensedPrescriber;
    private RoleType roleField = RoleType.doctor;
    private String nameField = new String();
    private String passwordField = new String();
    private CurrentFeatureType[] featuresField = new CurrentFeatureType[]();
    /**
    * 
    */
    public UserType getuser() throws Exception {
        return this.userField;
    }

    public void setuser(UserType value) throws Exception {
        this.userField = value;
    }

    /**
    * 
    */
    public RoleType getrole() throws Exception {
        return this.roleField;
    }

    public void setrole(RoleType value) throws Exception {
        this.roleField = value;
    }

    /**
    * 
    */
    public String getname() throws Exception {
        return this.nameField;
    }

    public void setname(String value) throws Exception {
        this.nameField = value;
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
    public CurrentFeatureType[] getFeatures() throws Exception {
        return this.featuresField;
    }

    public void setFeatures(CurrentFeatureType[] value) throws Exception {
        this.featuresField = value;
    }

}


