//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.CodifiedSigType;
import OpenDentBusiness.DrugDatabaseType;
import OpenDentBusiness.DrugSubstitutionType;
import OpenDentBusiness.DrugTakeAsNeededType;
import OpenDentBusiness.ExternalDrugOverrideType;
import OpenDentBusiness.PrescriptionArchiveType;
import OpenDentBusiness.PrescriptionStatusType;
import OpenDentBusiness.PrescriptionSubStatusType;


/**
* 
*/
public class OutsidePrescriptionType   
{

    private String externalIdField = new String();
    private String pharmacyIdentifierField = new String();
    private String pharmacyPhoneField = new String();
    private String pharmacyFaxField = new String();
    private String dateField = new String();
    private String doctorNameField = new String();
    private String drugField = new String();
    private String dosageField = new String();
    private String dispenseNumberField = new String();
    private String sigField = new String();
    private String refillCountField = new String();
    private DrugSubstitutionType substitutionField = DrugSubstitutionType.DispenseAsWritten;
    private boolean substitutionFieldSpecified = new boolean();
    private String pharmacistMessageField = new String();
    private String drugIdentifierField = new String();
    private DrugDatabaseType drugIdentifierTypeField = DrugDatabaseType.FDA;
    private boolean drugIdentifierTypeFieldSpecified = new boolean();
    private String prescriptionTypeField = new String();
    private ExternalDrugOverrideType externalOverrideDrugField;
    private String renewalRequestIdentifierField = new String();
    private CodifiedSigType codifiedSigTypeField;
    private DrugTakeAsNeededType prnField = DrugTakeAsNeededType.Yes;
    private boolean prnFieldSpecified = new boolean();
    private PrescriptionStatusType prescriptionStatusField = PrescriptionStatusType.Current;
    private boolean prescriptionStatusFieldSpecified = new boolean();
    private PrescriptionSubStatusType prescriptionSubStatusField = PrescriptionSubStatusType.InProcess;
    private boolean prescriptionSubStatusFieldSpecified = new boolean();
    private PrescriptionArchiveType prescriptionArchiveStatusField = PrescriptionArchiveType.Yes;
    private boolean prescriptionArchiveStatusFieldSpecified = new boolean();
    private String idField = new String();
    /**
    * 
    */
    public String getexternalId() throws Exception {
        return this.externalIdField;
    }

    public void setexternalId(String value) throws Exception {
        this.externalIdField = value;
    }

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
    public String getpharmacyPhone() throws Exception {
        return this.pharmacyPhoneField;
    }

    public void setpharmacyPhone(String value) throws Exception {
        this.pharmacyPhoneField = value;
    }

    /**
    * 
    */
    public String getpharmacyFax() throws Exception {
        return this.pharmacyFaxField;
    }

    public void setpharmacyFax(String value) throws Exception {
        this.pharmacyFaxField = value;
    }

    /**
    * 
    */
    public String getdate() throws Exception {
        return this.dateField;
    }

    public void setdate(String value) throws Exception {
        this.dateField = value;
    }

    /**
    * 
    */
    public String getdoctorName() throws Exception {
        return this.doctorNameField;
    }

    public void setdoctorName(String value) throws Exception {
        this.doctorNameField = value;
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
    public String getdosage() throws Exception {
        return this.dosageField;
    }

    public void setdosage(String value) throws Exception {
        this.dosageField = value;
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
    public String getsig() throws Exception {
        return this.sigField;
    }

    public void setsig(String value) throws Exception {
        this.sigField = value;
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
    public String getprescriptionType() throws Exception {
        return this.prescriptionTypeField;
    }

    public void setprescriptionType(String value) throws Exception {
        this.prescriptionTypeField = value;
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
    public PrescriptionStatusType getprescriptionStatus() throws Exception {
        return this.prescriptionStatusField;
    }

    public void setprescriptionStatus(PrescriptionStatusType value) throws Exception {
        this.prescriptionStatusField = value;
    }

    /**
    * 
    */
    public boolean getprescriptionStatusSpecified() throws Exception {
        return this.prescriptionStatusFieldSpecified;
    }

    public void setprescriptionStatusSpecified(boolean value) throws Exception {
        this.prescriptionStatusFieldSpecified = value;
    }

    /**
    * 
    */
    public PrescriptionSubStatusType getprescriptionSubStatus() throws Exception {
        return this.prescriptionSubStatusField;
    }

    public void setprescriptionSubStatus(PrescriptionSubStatusType value) throws Exception {
        this.prescriptionSubStatusField = value;
    }

    /**
    * 
    */
    public boolean getprescriptionSubStatusSpecified() throws Exception {
        return this.prescriptionSubStatusFieldSpecified;
    }

    public void setprescriptionSubStatusSpecified(boolean value) throws Exception {
        this.prescriptionSubStatusFieldSpecified = value;
    }

    /**
    * 
    */
    public PrescriptionArchiveType getprescriptionArchiveStatus() throws Exception {
        return this.prescriptionArchiveStatusField;
    }

    public void setprescriptionArchiveStatus(PrescriptionArchiveType value) throws Exception {
        this.prescriptionArchiveStatusField = value;
    }

    /**
    * 
    */
    public boolean getprescriptionArchiveStatusSpecified() throws Exception {
        return this.prescriptionArchiveStatusFieldSpecified;
    }

    public void setprescriptionArchiveStatusSpecified(boolean value) throws Exception {
        this.prescriptionArchiveStatusFieldSpecified = value;
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


