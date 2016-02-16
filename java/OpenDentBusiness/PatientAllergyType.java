//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AllergySeverityType;
import OpenDentBusiness.DrugDatabaseType;


/**
* 
*/
public class PatientAllergyType   
{

    private String allergyIDField = new String();
    private DrugDatabaseType allergyTypeIDField = DrugDatabaseType.FDA;
    private AllergySeverityType allergySeverityTypeIDField = AllergySeverityType.Unspecified;
    private boolean allergySeverityTypeIDFieldSpecified = new boolean();
    private String allergyCommentField = new String();
    /**
    * 
    */
    public String getallergyID() throws Exception {
        return this.allergyIDField;
    }

    public void setallergyID(String value) throws Exception {
        this.allergyIDField = value;
    }

    /**
    * 
    */
    public DrugDatabaseType getallergyTypeID() throws Exception {
        return this.allergyTypeIDField;
    }

    public void setallergyTypeID(DrugDatabaseType value) throws Exception {
        this.allergyTypeIDField = value;
    }

    /**
    * 
    */
    public AllergySeverityType getallergySeverityTypeID() throws Exception {
        return this.allergySeverityTypeIDField;
    }

    public void setallergySeverityTypeID(AllergySeverityType value) throws Exception {
        this.allergySeverityTypeIDField = value;
    }

    /**
    * 
    */
    public boolean getallergySeverityTypeIDSpecified() throws Exception {
        return this.allergySeverityTypeIDFieldSpecified;
    }

    public void setallergySeverityTypeIDSpecified(boolean value) throws Exception {
        this.allergySeverityTypeIDFieldSpecified = value;
    }

    /**
    * 
    */
    public String getallergyComment() throws Exception {
        return this.allergyCommentField;
    }

    public void setallergyComment(String value) throws Exception {
        this.allergyCommentField = value;
    }

}


