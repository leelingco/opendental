//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DrugSubstitutionType;


/**
* 
*/
public class PrescriptionRenewalRequestType   
{

    private String pharmacyIdentifierField = new String();
    private String drugNDCField = new String();
    private String drugField = new String();
    private String dispenseNumberField = new String();
    private String dosageField = new String();
    private String lastFillDateField = new String();
    private String writtenDateField = new String();
    private String daysSupplyField = new String();
    private DrugSubstitutionType substitutionField = DrugSubstitutionType.DispenseAsWritten;
    private String refillsField = new String();
    private String pharmacistMessageField = new String();
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
    public String getdrugNDC() throws Exception {
        return this.drugNDCField;
    }

    public void setdrugNDC(String value) throws Exception {
        this.drugNDCField = value;
    }

    /**
    * 
    */
    public String getdrug() throws Exception {
        return this.drugField;
    }

    public void setdrug(String value) throws Exception {
        this.drugField = value;
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
    public String getlastFillDate() throws Exception {
        return this.lastFillDateField;
    }

    public void setlastFillDate(String value) throws Exception {
        this.lastFillDateField = value;
    }

    /**
    * 
    */
    public String getwrittenDate() throws Exception {
        return this.writtenDateField;
    }

    public void setwrittenDate(String value) throws Exception {
        this.writtenDateField = value;
    }

    /**
    * 
    */
    public String getdaysSupply() throws Exception {
        return this.daysSupplyField;
    }

    public void setdaysSupply(String value) throws Exception {
        this.daysSupplyField = value;
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
    public String getrefills() throws Exception {
        return this.refillsField;
    }

    public void setrefills(String value) throws Exception {
        this.refillsField = value;
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
    public String getID() throws Exception {
        return this.idField;
    }

    public void setID(String value) throws Exception {
        this.idField = value;
    }

}


