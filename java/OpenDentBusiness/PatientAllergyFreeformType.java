//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AllergySeverityType;


/**
* 
*/
public class PatientAllergyFreeformType   
{

    private String allergyNameField = new String();
    private AllergySeverityType allergySeverityTypeIDField = AllergySeverityType.Unspecified;
    private boolean allergySeverityTypeIDFieldSpecified = new boolean();
    private String allergyCommentField = new String();
    private String idField = new String();
    /**
    * 
    */
    public String getallergyName() throws Exception {
        return this.allergyNameField;
    }

    public void setallergyName(String value) throws Exception {
        this.allergyNameField = value;
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


