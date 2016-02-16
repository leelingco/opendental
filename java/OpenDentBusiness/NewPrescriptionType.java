//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CodifiedSigType;
import OpenDentBusiness.DrugDatabaseType;
import OpenDentBusiness.DrugSubstitutionType;
import OpenDentBusiness.DrugTakeAsNeededType;
import OpenDentBusiness.ExternalDrugOverrideType;


/**
* 
*/
public class NewPrescriptionType   
{

    private String pharmacyIdentifierField = new String();
    private String drugIdentifierField = new String();
    private DrugDatabaseType drugIdentifierTypeField = DrugDatabaseType.FDA;
    private String dispenseNumberField = new String();
    private String dosageField = new String();
    private String refillCountField = new String();
    private DrugSubstitutionType substitutionField = DrugSubstitutionType.DispenseAsWritten;
    private String pharmacistMessageField = new String();
    private ExternalDrugOverrideType externalOverrideDrugField;
    private String renewalRequestIdentifierField = new String();
    private CodifiedSigType codifiedSigTypeField;
    private DrugTakeAsNeededType prnField = DrugTakeAsNeededType.Yes;
    private boolean prnFieldSpecified = new boolean();
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
    public String getpharmacistMessage() throws Exception {
        return this.pharmacistMessageField;
    }

    public void setpharmacistMessage(String value) throws Exception {
        this.pharmacistMessageField = value;
    }

    /**
    * 
    */
    public ExternalDrugOverrideType getexternalOverrideDrug() throws Exception {
        return this.externalOverrideDrugField;
    }

    public void setexternalOverrideDrug(ExternalDrugOverrideType value) throws Exception {
        this.externalOverrideDrugField = value;
    }

    /**
    * 
    */
    public String getrenewalRequestIdentifier() throws Exception {
        return this.renewalRequestIdentifierField;
    }

    public void setrenewalRequestIdentifier(String value) throws Exception {
        this.renewalRequestIdentifierField = value;
    }

    /**
    * 
    */
    public CodifiedSigType getcodifiedSigType() throws Exception {
        return this.codifiedSigTypeField;
    }

    public void setcodifiedSigType(CodifiedSigType value) throws Exception {
        this.codifiedSigTypeField = value;
    }

    /**
    * 
    */
    public DrugTakeAsNeededType getprn() throws Exception {
        return this.prnField;
    }

    public void setprn(DrugTakeAsNeededType value) throws Exception {
        this.prnField = value;
    }

    /**
    * 
    */
    public boolean getprnSpecified() throws Exception {
        return this.prnFieldSpecified;
    }

    public void setprnSpecified(boolean value) throws Exception {
        this.prnFieldSpecified = value;
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


