//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.FeatureExpirationType;
import OpenDentBusiness.FeatureStatusType;
import OpenDentBusiness.FeatureType;


/**
* 
*/
public class CurrentFeatureType   
{

    private FeatureType featureField = FeatureType.Connect;
    private FeatureStatusType featureStatusField = FeatureStatusType.Active;
    private FeatureExpirationType featureExpirationField = FeatureExpirationType.ExpirationDate;
    private boolean featureExpirationFieldSpecified = new boolean();
    private String featureExpirationDateField = new String();
    /**
    * 
    */
    public FeatureType getfeature() throws Exception {
        return this.featureField;
    }

    public void setfeature(FeatureType value) throws Exception {
        this.featureField = value;
    }

    /**
    * 
    */
    public FeatureStatusType getfeatureStatus() throws Exception {
        return this.featureStatusField;
    }

    public void setfeatureStatus(FeatureStatusType value) throws Exception {
        this.featureStatusField = value;
    }

    /**
    * 
    */
    public FeatureExpirationType getfeatureExpiration() throws Exception {
        return this.featureExpirationField;
    }

    public void setfeatureExpiration(FeatureExpirationType value) throws Exception {
        this.featureExpirationField = value;
    }

    /**
    * 
    */
    public boolean getfeatureExpirationSpecified() throws Exception {
        return this.featureExpirationFieldSpecified;
    }

    public void setfeatureExpirationSpecified(boolean value) throws Exception {
        this.featureExpirationFieldSpecified = value;
    }

    /**
    * 
    */
    public String getfeatureExpirationDate() throws Exception {
        return this.featureExpirationDateField;
    }

    public void setfeatureExpirationDate(String value) throws Exception {
        this.featureExpirationDateField = value;
    }

}


