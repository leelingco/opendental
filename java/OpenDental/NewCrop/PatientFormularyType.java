//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.DrugDatabaseType;


/**
* 
*/
public class PatientFormularyType   
{

    private String eligibilityGuidField = new String();
    private String eligibilityIndexField = new String();
    private String drugIdentifierField = new String();
    private DrugDatabaseType drugIdentifierTypeField = DrugDatabaseType.FDA;
    private boolean drugIdentifierTypeFieldSpecified = new boolean();
    private String statusDisplayedField = new String();
    /**
    * 
    */
    public String geteligibilityGuid() throws Exception {
        return this.eligibilityGuidField;
    }

    public void seteligibilityGuid(String value) throws Exception {
        this.eligibilityGuidField = value;
    }

    /**
    * 
    */
    public String geteligibilityIndex() throws Exception {
        return this.eligibilityIndexField;
    }

    public void seteligibilityIndex(String value) throws Exception {
        this.eligibilityIndexField = value;
    }

    /**
    * 
    */
    public String getdrugIdentifier() throws Exception {
        return this.drugIdentifierField;
    }

    public void setdrugIdentifier(String value) throws Exception {
        this.drugIdentifierField = value;
    }

    /**
    * 
    */
    public DrugDatabaseType getdrugIdentifierType() throws Exception {
        return this.drugIdentifierTypeField;
    }

    public void setdrugIdentifierType(DrugDatabaseType value) throws Exception {
        this.drugIdentifierTypeField = value;
    }

    /**
    * 
    */
    public boolean getdrugIdentifierTypeSpecified() throws Exception {
        return this.drugIdentifierTypeFieldSpecified;
    }

    public void setdrugIdentifierTypeSpecified(boolean value) throws Exception {
        this.drugIdentifierTypeFieldSpecified = value;
    }

    /**
    * 
    */
    public String getstatusDisplayed() throws Exception {
        return this.statusDisplayedField;
    }

    public void setstatusDisplayed(String value) throws Exception {
        this.statusDisplayedField = value;
    }

}


