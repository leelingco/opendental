//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DrugDatabaseType;
import OpenDentBusiness.DrugSubstitutionType;
import OpenDentBusiness.NewPrescriptionImageType;


/**
* 
*/
public class NewPrescriptionWithImagesType   
{

    private String pharmacyIdentifierField = new String();
    private String drugNameField = new String();
    private String drugStrengthField = new String();
    private String drugStrengthUOMField = new String();
    private String drugRouteField = new String();
    private String drugFormField = new String();
    private String drugIdentifierField = new String();
    private DrugDatabaseType drugIdentifierTypeField = DrugDatabaseType.FDA;
    private boolean drugIdentifierTypeFieldSpecified = new boolean();
    private String dispenseNumberField = new String();
    private String dosageField = new String();
    private String refillCountField = new String();
    private DrugSubstitutionType substitutionField = DrugSubstitutionType.DispenseAsWritten;
    private boolean substitutionFieldSpecified = new boolean();
    private String pharmacistMessageField = new String();
    private NewPrescriptionImageType[] imagesField = new NewPrescriptionImageType[]();
    private String idField = new String();
    /**
    * 
    */
    public String getpharmacyIdentifier() throws Exception {
        return this.pharmacyIdentifierField;
    }

    public void setpharmacyIdentifier(String value) throws Exception {
        this.pharmacyIdentifierField = value;
    }

    /**
    * 
    */
    public String getdrugName() throws Exception {
        return this.drugNameField;
    }

    public void setdrugName(String value) throws Exception {
        this.drugNameField = value;
    }

    /**
    * 
    */
    public String getdrugStrength() throws Exception {
        return this.drugStrengthField;
    }

    public void setdrugStrength(String value) throws Exception {
        this.drugStrengthField = value;
    }

    /**
    * 
    */
    public String getdrugStrengthUOM() throws Exception {
        return this.drugStrengthUOMField;
    }

    public void setdrugStrengthUOM(String value) throws Exception {
        this.drugStrengthUOMField = value;
    }

    /**
    * 
    */
    public String getdrugRoute() throws Exception {
        return this.drugRouteField;
    }

    public void setdrugRoute(String value) throws Exception {
        this.drugRouteField = value;
    }

    /**
    * 
    */
    public String getdrugForm() throws Exception {
        return this.drugFormField;
    }

    public void setdrugForm(String value) throws Exception {
        this.drugFormField = value;
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
    public String getdispenseNumber() throws Exception {
        return this.dispenseNumberField;
    }

    public void setdispenseNumber(String value) throws Exception {
        this.dispenseNumberField = value;
    }

    /**
    * 
    */
    public String getdosage() throws Exception {
        return this.dosageField;
    }

    public void setdosage(String value) throws Exception {
        this.dosageField = value;
    }

    /**
    * 
    */
    public String getrefillCount() throws Exception {
        return this.refillCountField;
    }

    public void setrefillCount(String value) throws Exception {
        this.refillCountField = value;
    }

    /**
    * 
    */
    public DrugSubstitutionType getsubstitution() throws Exception {
        return this.substitutionField;
    }

    public void setsubstitution(DrugSubstitutionType value) throws Exception {
        this.substitutionField = value;
    }

    /**
    * 
    */
    public boolean getsubstitutionSpecified() throws Exception {
        return this.substitutionFieldSpecified;
    }

    public void setsubstitutionSpecified(boolean value) throws Exception {
        this.substitutionFieldSpecified = value;
    }

    /**
    * 
    */
    public String getpharmacistMessage() throws Exception {
        return this.pharmacistMessageField;
    }

    public void setpharmacistMessage(String value) throws Exception {
        this.pharmacistMessageField = value;
    }

    /**
    * 
    */
    public NewPrescriptionImageType[] getimages() throws Exception {
        return this.imagesField;
    }

    public void setimages(NewPrescriptionImageType[] value) throws Exception {
        this.imagesField = value;
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


