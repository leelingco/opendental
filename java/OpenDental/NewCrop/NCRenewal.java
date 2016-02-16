//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDental.NewCrop;

import CS2JNet.JavaSupport.util.ListSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import OpenDental.NewCrop.AccountType;
import OpenDental.NewCrop.CredentialsType;
import OpenDental.NewCrop.LicensedPrescriberType;
import OpenDental.NewCrop.LocationType;
import OpenDental.NewCrop.PatientType;
import OpenDental.NewCrop.PrescriptionRenewalRequestType;
import OpenDental.NewCrop.StaffType;


/**
* 
*/
public class NCRenewal   
{

    private CredentialsType credentialsField;
    private AccountType accountField;
    private LocationType locationField;
    private LicensedPrescriberType licensedPrescriberField;
    private StaffType staffField;
    private PatientType patientField;
    private PrescriptionRenewalRequestType prescriptionRenewalRequestField;
    /**
    * 
    */
    public CredentialsType getCredentials() throws Exception {
        return this.credentialsField;
    }

    public void setCredentials(CredentialsType value) throws Exception {
        this.credentialsField = value;
    }

    /**
    * 
    */
    public AccountType getAccount() throws Exception {
        return this.accountField;
    }

    public void setAccount(AccountType value) throws Exception {
        this.accountField = value;
    }

    /**
    * 
    */
    public LocationType getLocation() throws Exception {
        return this.locationField;
    }

    public void setLocation(LocationType value) throws Exception {
        this.locationField = value;
    }

    /**
    * 
    */
    public LicensedPrescriberType getLicensedPrescriber() throws Exception {
        return this.licensedPrescriberField;
    }

    public void setLicensedPrescriber(LicensedPrescriberType value) throws Exception {
        this.licensedPrescriberField = value;
    }

    /**
    * 
    */
    public StaffType getStaff() throws Exception {
        return this.staffField;
    }

    public void setStaff(StaffType value) throws Exception {
        this.staffField = value;
    }

    /**
    * 
    */
    public PatientType getPatient() throws Exception {
        return this.patientField;
    }

    public void setPatient(PatientType value) throws Exception {
        this.patientField = value;
    }

    /**
    * 
    */
    public PrescriptionRenewalRequestType getPrescriptionRenewalRequest() throws Exception {
        return this.prescriptionRenewalRequestField;
    }

    public void setPrescriptionRenewalRequest(PrescriptionRenewalRequestType value) throws Exception {
        this.prescriptionRenewalRequestField = value;
    }

}


