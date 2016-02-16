//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.AddressOptionalType;
import OpenDentBusiness.ContactType;
import OpenDentBusiness.PatientAllergyFreeformType;
import OpenDentBusiness.PatientAllergyType;
import OpenDentBusiness.PatientCharacteristicsType;
import OpenDentBusiness.PatientDiagnosisType;
import OpenDentBusiness.PatientHealthplanFreeformType;
import OpenDentBusiness.PatientHealthplanType;
import OpenDentBusiness.PatientIdentifierType;
import OpenDentBusiness.PersonNameType;


/**
* 
*/
public class PatientType   
{

    private PersonNameType patientNameField;
    private String medicalRecordNumberField = new String();
    private String socialSecurityNumberField = new String();
    private String memoField = new String();
    private AddressOptionalType patientAddressField;
    private ContactType patientContactField;
    private PatientCharacteristicsType patientCharacteristicsField;
    private PatientAllergyType[] patientAllergiesField = new PatientAllergyType[]();
    private PatientHealthplanType[] patientHealthplansField = new PatientHealthplanType[]();
    private PatientDiagnosisType[] patientDiagnosisField = new PatientDiagnosisType[]();
    private String patientDiagnosisSearchField = new String();
    private PatientIdentifierType[] patientIdentifierField = new PatientIdentifierType[]();
    private PatientHealthplanFreeformType[] patientFreeformHealthplansField = new PatientHealthplanFreeformType[]();
    private String episodeIdentifierField = new String();
    private String encounterIdentifierField = new String();
    private PatientAllergyFreeformType[] patientFreeformAllergyField = new PatientAllergyFreeformType[]();
    private String idField = new String();
    /**
    * 
    */
    public PersonNameType getPatientName() throws Exception {
        return this.patientNameField;
    }

    public void setPatientName(PersonNameType value) throws Exception {
        this.patientNameField = value;
    }

    /**
    * 
    */
    public String getmedicalRecordNumber() throws Exception {
        return this.medicalRecordNumberField;
    }

    public void setmedicalRecordNumber(String value) throws Exception {
        this.medicalRecordNumberField = value;
    }

    /**
    * 
    */
    public String getsocialSecurityNumber() throws Exception {
        return this.socialSecurityNumberField;
    }

    public void setsocialSecurityNumber(String value) throws Exception {
        this.socialSecurityNumberField = value;
    }

    /**
    * 
    */
    public String getmemo() throws Exception {
        return this.memoField;
    }

    public void setmemo(String value) throws Exception {
        this.memoField = value;
    }

    /**
    * 
    */
    public AddressOptionalType getPatientAddress() throws Exception {
        return this.patientAddressField;
    }

    public void setPatientAddress(AddressOptionalType value) throws Exception {
        this.patientAddressField = value;
    }

    /**
    * 
    */
    public ContactType getPatientContact() throws Exception {
        return this.patientContactField;
    }

    public void setPatientContact(ContactType value) throws Exception {
        this.patientContactField = value;
    }

    /**
    * 
    */
    public PatientCharacteristicsType getPatientCharacteristics() throws Exception {
        return this.patientCharacteristicsField;
    }

    public void setPatientCharacteristics(PatientCharacteristicsType value) throws Exception {
        this.patientCharacteristicsField = value;
    }

    /**
    * 
    */
    public PatientAllergyType[] getPatientAllergies() throws Exception {
        return this.patientAllergiesField;
    }

    public void setPatientAllergies(PatientAllergyType[] value) throws Exception {
        this.patientAllergiesField = value;
    }

    /**
    * 
    */
    public PatientHealthplanType[] getPatientHealthplans() throws Exception {
        return this.patientHealthplansField;
    }

    public void setPatientHealthplans(PatientHealthplanType[] value) throws Exception {
        this.patientHealthplansField = value;
    }

    /**
    * 
    */
    public PatientDiagnosisType[] getPatientDiagnosis() throws Exception {
        return this.patientDiagnosisField;
    }

    public void setPatientDiagnosis(PatientDiagnosisType[] value) throws Exception {
        this.patientDiagnosisField = value;
    }

    /**
    * 
    */
    public String getPatientDiagnosisSearch() throws Exception {
        return this.patientDiagnosisSearchField;
    }

    public void setPatientDiagnosisSearch(String value) throws Exception {
        this.patientDiagnosisSearchField = value;
    }

    /**
    * 
    */
    public PatientIdentifierType[] getPatientIdentifier() throws Exception {
        return this.patientIdentifierField;
    }

    public void setPatientIdentifier(PatientIdentifierType[] value) throws Exception {
        this.patientIdentifierField = value;
    }

    /**
    * 
    */
    public PatientHealthplanFreeformType[] getPatientFreeformHealthplans() throws Exception {
        return this.patientFreeformHealthplansField;
    }

    public void setPatientFreeformHealthplans(PatientHealthplanFreeformType[] value) throws Exception {
        this.patientFreeformHealthplansField = value;
    }

    /**
    * 
    */
    public String getEpisodeIdentifier() throws Exception {
        return this.episodeIdentifierField;
    }

    public void setEpisodeIdentifier(String value) throws Exception {
        this.episodeIdentifierField = value;
    }

    /**
    * 
    */
    public String getEncounterIdentifier() throws Exception {
        return this.encounterIdentifierField;
    }

    public void setEncounterIdentifier(String value) throws Exception {
        this.encounterIdentifierField = value;
    }

    /**
    * 
    */
    public PatientAllergyFreeformType[] getPatientFreeformAllergy() throws Exception {
        return this.patientFreeformAllergyField;
    }

    public void setPatientFreeformAllergy(PatientAllergyFreeformType[] value) throws Exception {
        this.patientFreeformAllergyField = value;
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


