//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:56 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.AccountTypeRx;
import OpenDentBusiness.AddressOptionalType;
import OpenDentBusiness.AddressType;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.ContactType;
import OpenDentBusiness.CredentialsType;
import OpenDentBusiness.Db;
import OpenDentBusiness.DestinationType;
import OpenDentBusiness.Employee;
import OpenDentBusiness.GenderType;
import OpenDentBusiness.LicensedPrescriberType;
import OpenDentBusiness.LocationType;
import OpenDentBusiness.NCScript;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientCharacteristicsType;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientType;
import OpenDentBusiness.PersonNameSuffix;
import OpenDentBusiness.PersonNameType;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.RequestedPageType;
import OpenDentBusiness.RoleType;
import OpenDentBusiness.StaffType;
import OpenDentBusiness.UserRoleType;
import OpenDentBusiness.UserType;

public class ErxXml   
{
    public static String getNewCropPartnerName() throws Exception {
        String newCropName = PrefC.getString(PrefName.NewCropName);
        if (StringSupport.equals(newCropName, ""))
        {
            return "OpenDental";
        }
         
        return PrefC.getString(PrefName.NewCropPartnerName);
    }

    //Resellers use this field to send different credentials. Thus, if blank, then send OD credentials.
    //Reseller.
    public static String getNewCropAccountName() throws Exception {
        String newCropName = PrefC.getString(PrefName.NewCropName);
        if (StringSupport.equals(newCropName, ""))
        {
            return CodeBase.MiscUtils.decrypt("HumacKlUtM1MLCHsZY/PH86W10AY5u2bukFp15lEKhT6zD/aa9nG//zYzbYgpH8+");
        }
         
        return newCropName;
    }

    //Resellers use this field to send different credentials. Thus, if blank, then send OD credentials.
    //Assigned by NewCrop. Used globally for all customers.
    //Reseller.
    public static String getNewCropAccountPasssword() throws Exception {
        String newCropName = PrefC.getString(PrefName.NewCropName);
        if (StringSupport.equals(newCropName, ""))
        {
            return CodeBase.MiscUtils.decrypt("I0Itlo5F3ZOYUSwMKpgbY5X6++XpUetMvrqj0vVB7bKzYwJlWtsLiFFgpMBplLaH");
        }
         
        return PrefC.getString(PrefName.NewCropPassword);
    }

    //Resellers use this field to send different credentials. Thus, if blank, then send OD credentials.
    //Assigned by NewCrop. Used globally for all customers.
    //Reseller.
    public static String getNewCropProductName() throws Exception {
        return "OpenDental";
    }

    public static String getNewCropProductVersion() throws Exception {
        return Assembly.GetAssembly(Db.class).GetName().Version.ToString();
    }

    /**
    * Only called from Chart for now.  No validation is performed here.  Validate before calling.  There are many validtion checks, including the NPI must be exactly 10 digits.
    */
    public static String buildClickThroughXml(Provider prov, Employee emp, Patient pat) throws Exception {
        NCScript ncScript = new NCScript();
        ncScript.setCredentials(new CredentialsType());
        ncScript.getCredentials().setpartnerName(getNewCropPartnerName());
        ncScript.getCredentials().setname(getNewCropAccountName());
        ncScript.getCredentials().setpassword(getNewCropAccountPasssword());
        ncScript.getCredentials().setproductName(getNewCropProductName());
        ncScript.getCredentials().setproductVersion(getNewCropProductVersion());
        ncScript.setUserRole(new UserRoleType());
        if (emp == null)
        {
            ncScript.getUserRole().setuser(UserType.LicensedPrescriber);
            ncScript.getUserRole().setrole(RoleType.doctor);
        }
        else
        {
            ncScript.getUserRole().setuser(UserType.Staff);
            ncScript.getUserRole().setrole(RoleType.nurse);
        } 
        ncScript.setDestination(new DestinationType());
        ncScript.getDestination().setrequestedPage(RequestedPageType.compose);
        //This is the tab that the user will want 90% of the time.
        String practiceTitle = PrefC.getString(PrefName.PracticeTitle);
        //May be blank.
        String practicePhone = PrefC.getString(PrefName.PracticePhone);
        //Validated to be 10 digits within the chart.
        String practiceFax = PrefC.getString(PrefName.PracticeFax);
        //Validated to be 10 digits within the chart.
        String practiceAddress = PrefC.getString(PrefName.PracticeAddress);
        //Validated to exist in chart.
        String practiceAddress2 = PrefC.getString(PrefName.PracticeAddress2);
        //May be blank.
        String practiceCity = PrefC.getString(PrefName.PracticeCity);
        //Validated to exist in chart.
        String practiceState = PrefC.getString(PrefName.PracticeST);
        //Validated to be a US state code in chart.
        String practiceZip = Regex.Replace(PrefC.getString(PrefName.PracticeZip), "[^0-9]*", "");
        //Zip with all non-numeric characters removed. Validated to be 9 digits in chart.
        String practiceZip4 = practiceZip.Substring(5);
        //Last 4 digits of zip.
        practiceZip = practiceZip.Substring(0, 5);
        //First 5 digits of zip.
        String country = "US";
        //Always United States for now.
        //if(CultureInfo.CurrentCulture.Name.Length>=2) {
        //  country=CultureInfo.CurrentCulture.Name.Substring(CultureInfo.CurrentCulture.Name.Length-2);
        //}
        ncScript.setAccount(new AccountTypeRx());
        //Each LicensedPrescriberID must be unique within an account. Since we send ProvNum for LicensedPrescriberID, each OD database must have a unique AccountID.
        ncScript.getAccount().setID(PrefC.getString(PrefName.NewCropAccountId));
        //Customer account number then a dash then a random alpha-numeric string of 3 characters, followed by 2 digits.
        ncScript.getAccount().setaccountName(practiceTitle);
        //May be blank.
        ncScript.getAccount().setsiteID("1");
        //Always send 1.  For each AccountID/SiteID pair, a separate database will be created in NewCrop.
        ncScript.getAccount().setAccountAddress(new AddressType());
        ncScript.getAccount().getAccountAddress().setaddress1(practiceAddress);
        //Validated to exist in chart.
        ncScript.getAccount().getAccountAddress().setaddress2(practiceAddress2);
        //May be blank.
        ncScript.getAccount().getAccountAddress().setcity(practiceCity);
        //Validated to exist in chart.
        ncScript.getAccount().getAccountAddress().setstate(practiceState);
        //Validated to be a US state code in chart.
        ncScript.getAccount().getAccountAddress().setzip(practiceZip);
        //Validated to be 9 digits in chart. First 5 digits go in this field.
        ncScript.getAccount().getAccountAddress().setzip4(practiceZip4);
        //Validated to be 9 digits in chart. Last 4 digits go in this field.
        ncScript.getAccount().getAccountAddress().setcountry(country);
        //Validated above.
        ncScript.getAccount().setaccountPrimaryPhoneNumber(practicePhone);
        //Validated to be 10 digits within the chart.
        ncScript.getAccount().setaccountPrimaryFaxNumber(practiceFax);
        //Validated to be 10 digits within the chart.
        ncScript.setLocation(new LocationType());
        if (PrefC.getBool(PrefName.EasyNoClinics) || pat.ClinicNum == 0)
        {
            //No clinics.
            ncScript.getLocation().setID("0");
            //Always 0, since clinicnums must be >= 1, will never overlap with a clinic if the office turns clinics on after first use.
            ncScript.getLocation().setlocationName(practiceTitle);
            //May be blank.
            ncScript.getLocation().setLocationAddress(new AddressType());
            ncScript.getLocation().getLocationAddress().setaddress1(practiceAddress);
            //Validated to exist in chart.
            ncScript.getLocation().getLocationAddress().setaddress2(practiceAddress2);
            //May be blank.
            ncScript.getLocation().getLocationAddress().setcity(practiceCity);
            //Validated to exist in chart.
            ncScript.getLocation().getLocationAddress().setstate(practiceState);
            //Validated to be a US state code in chart.
            ncScript.getLocation().getLocationAddress().setzip(practiceZip);
            //Validated to be 9 digits in chart. First 5 digits go in this field.
            ncScript.getLocation().getLocationAddress().setzip4(practiceZip4);
            //Validated to be 9 digits in chart. Last 4 digits go in this field.
            ncScript.getLocation().getLocationAddress().setcountry(country);
            //Validated above.
            ncScript.getLocation().setprimaryPhoneNumber(practicePhone);
            //Validated to be 10 digits within the chart.
            ncScript.getLocation().setprimaryFaxNumber(practiceFax);
            //Validated to be 10 digits within the chart.
            ncScript.getLocation().setpharmacyContactNumber(practicePhone);
        }
        else
        {
            //Validated to be 10 digits within the chart.
            //Using clinics.
            Clinic clinic = Clinics.getClinic(pat.ClinicNum);
            ncScript.getLocation().setID(clinic.ClinicNum.ToString());
            //A positive integer.
            ncScript.getLocation().setlocationName(clinic.Description);
            //May be blank.
            ncScript.getLocation().setLocationAddress(new AddressType());
            ncScript.getLocation().getLocationAddress().setaddress1(clinic.Address);
            //Validated to exist in chart.
            ncScript.getLocation().getLocationAddress().setaddress2(clinic.Address2);
            //May be blank.
            ncScript.getLocation().getLocationAddress().setcity(clinic.City);
            //Validated to exist in chart.
            ncScript.getLocation().getLocationAddress().setstate(clinic.State);
            //Validated to be a US state code in chart.
            String clinicZip = Regex.Replace(clinic.Zip, "[^0-9]*", "");
            //Zip with all non-numeric characters removed. Validated to be 9 digits in chart.
            String clinicZip4 = clinicZip.Substring(5);
            //Last 4 digits of zip.
            clinicZip = clinicZip.Substring(0, 5);
            //First 5 digits of zip.
            ncScript.getLocation().getLocationAddress().setzip(clinicZip);
            //Validated to be 9 digits in chart. First 5 digits go in this field.
            ncScript.getLocation().getLocationAddress().setzip4(clinicZip4);
            //Validated to be 9 digits in chart. Last 4 digits go in this field.
            ncScript.getLocation().getLocationAddress().setcountry(country);
            //Validated above.
            ncScript.getLocation().setprimaryPhoneNumber(clinic.Phone);
            //Validated to be 10 digits within the chart.
            ncScript.getLocation().setprimaryFaxNumber(clinic.Fax);
            //Validated to be 10 digits within the chart.
            ncScript.getLocation().setpharmacyContactNumber(clinic.Phone);
        } 
        //Validated to be 10 digits within the chart.
        ncScript.setLicensedPrescriber(new LicensedPrescriberType());
        //Each unique provider ID sent to NewCrop will cause a billing charge.
        //Some customer databases have provider duplicates, because they have one provider record per clinic with matching NPIs.
        //We send NPI as the ID to prevent extra NewCrop charges.
        //Conversation with NewCrop:
        //Question: If one of our customers clicks through to NewCrop with 2 different LicensedPrescriber.ID values,
        //          but with the same provider name and NPI, will Open Dental be billed twice or just one time for the NPI used?
        //Answer:   "They would be billed twice. The IDs you send us should always be maintained and unique.
        //          Users are always identified by LicensedPrescriber ID, since their name or credentials could potentially change."
        ncScript.getLicensedPrescriber().setID(prov.NationalProvID);
        //UPIN is obsolete
        ncScript.getLicensedPrescriber().setLicensedPrescriberName(new PersonNameType());
        ncScript.getLicensedPrescriber().getLicensedPrescriberName().setlast(prov.LName.Trim());
        //Cannot be blank.
        ncScript.getLicensedPrescriber().getLicensedPrescriberName().setfirst(prov.FName.Trim());
        //Cannot be blank.
        ncScript.getLicensedPrescriber().getLicensedPrescriberName().setmiddle(prov.MI);
        //May be blank.
        ncScript.getLicensedPrescriber().getLicensedPrescriberName().setsuffix(PersonNameSuffix.DDS);
        //There is no blank or none option, so we have to pick a default value. DDS=0, so would be default anyway.
        String[] suffixes = prov.Suffix.ToUpper().Split(' ', '.');
        for (int i = 0;i < suffixes.Length;i++)
        {
            if (StringSupport.equals(suffixes[i], "I"))
            {
                ncScript.getLicensedPrescriber().getLicensedPrescriberName().setsuffix(PersonNameSuffix.I);
                break;
            }
            else if (StringSupport.equals(suffixes[i], "II"))
            {
                ncScript.getLicensedPrescriber().getLicensedPrescriberName().setsuffix(PersonNameSuffix.II);
                break;
            }
            else if (StringSupport.equals(suffixes[i], "III"))
            {
                ncScript.getLicensedPrescriber().getLicensedPrescriberName().setsuffix(PersonNameSuffix.III);
                break;
            }
            else if (StringSupport.equals(suffixes[i], "JR"))
            {
                ncScript.getLicensedPrescriber().getLicensedPrescriberName().setsuffix(PersonNameSuffix.Jr);
                break;
            }
            else if (StringSupport.equals(suffixes[i], "SR"))
            {
                ncScript.getLicensedPrescriber().getLicensedPrescriberName().setsuffix(PersonNameSuffix.Sr);
                break;
            }
                 
        }
        if (StringSupport.equals(prov.DEANum.ToLower(), "none"))
        {
            ncScript.getLicensedPrescriber().setdea("NONE");
        }
        else
        {
            ncScript.getLicensedPrescriber().setdea(prov.DEANum);
        } 
        ncScript.getLicensedPrescriber().setlicenseState(prov.StateWhereLicensed);
        //Validated to be a US state code in the chart.
        ncScript.getLicensedPrescriber().setlicenseNumber(prov.StateLicense);
        //Validated to exist in chart.
        ncScript.getLicensedPrescriber().setnpi(prov.NationalProvID);
        //Validated to be 10 digits in chart.
        //ncScript.LicensedPrescriber.freeformCredentials=;//This is where DDS and DMD should go, but we don't support this yet. Probably not necessary anyway.
        if (emp != null)
        {
            ncScript.setStaff(new StaffType());
            ncScript.getStaff().setID("emp" + emp.EmployeeNum.ToString());
            //A positive integer. Returned in the ExternalUserID field when retreiving prescriptions from NewCrop. Also, provider ID is returned in the same field if a provider created the prescription, so that we can create a distintion between employee IDs and provider IDs.
            ncScript.getStaff().setStaffName(new PersonNameType());
            ncScript.getStaff().getStaffName().setfirst(emp.FName);
            //First name or last name will not be blank. Validated in Chart.
            ncScript.getStaff().getStaffName().setlast(emp.LName);
            //First name or last name will not be blank. Validated in Chart.
            ncScript.getStaff().getStaffName().setmiddle(emp.MiddleI);
        }
         
        //May be blank.
        ncScript.setPatient(new PatientType());
        ncScript.getPatient().setID(pat.PatNum.ToString());
        //A positive integer.
        ncScript.getPatient().setPatientName(new PersonNameType());
        ncScript.getPatient().getPatientName().setlast(pat.LName);
        //Validated to exist in Patient Edit window.
        ncScript.getPatient().getPatientName().setfirst(pat.FName);
        //May be blank.
        ncScript.getPatient().getPatientName().setmiddle(pat.MiddleI);
        //May be blank.
        ncScript.getPatient().setmedicalRecordNumber(pat.PatNum.ToString());
        //A positive integer.
        //NewCrop specifically requested that we do not send SSN.
        //ncScript.Patient.socialSecurityNumber=Regex.Replace(pat.SSN,"[^0-9]*","");//Removes all non-numerical characters.
        ncScript.getPatient().setPatientAddress(new AddressOptionalType());
        ncScript.getPatient().getPatientAddress().setaddress1(pat.Address);
        //May be blank.
        ncScript.getPatient().getPatientAddress().setaddress2(pat.Address2);
        //May be blank.
        ncScript.getPatient().getPatientAddress().setcity(pat.City);
        //May be blank.
        ncScript.getPatient().getPatientAddress().setstate(pat.State);
        //May be blank. Validated in chart to be blank or to be a valid US state code.
        ncScript.getPatient().getPatientAddress().setzip(pat.Zip);
        //May be blank.
        ncScript.getPatient().getPatientAddress().setcountry(country);
        //Validated above.
        ncScript.getPatient().setPatientContact(new ContactType());
        ncScript.getPatient().getPatientContact().sethomeTelephone(pat.HmPhone);
        //May be blank. Does not need to be 10 digits.
        ncScript.getPatient().setPatientCharacteristics(new PatientCharacteristicsType());
        ncScript.getPatient().getPatientCharacteristics().setdob(pat.Birthdate.ToString("yyyyMMdd"));
        //DOB must be in CCYYMMDD format.
        if (pat.Gender == PatientGender.Male)
        {
            ncScript.getPatient().getPatientCharacteristics().setgender(GenderType.M);
        }
        else if (pat.Gender == PatientGender.Female)
        {
            ncScript.getPatient().getPatientCharacteristics().setgender(GenderType.F);
        }
        else
        {
            ncScript.getPatient().getPatientCharacteristics().setgender(GenderType.U);
        }  
        ncScript.getPatient().getPatientCharacteristics().setgenderSpecified(true);
        //NewCrop programmer's comments regarding other fields we are not currently using (these fields are sent back when fetching prescriptions in the Chart):
        //ExternalPrescriptionId = your unique identifier for the prescription, only to be used if you are generating the prescription on your own UI.
        //	This is referenced by NewCrop, and cannot be populated with any other value.
        //EncounterIdentifier = unique ID for the patient visit (e.g. John Doe, 11/11/2013).
        //	This is used by NewCrop for reporting events against a visit, but otherwise does not impact the session.
        //EpisodeIdentifier = unique ID for the patient’s issue (e.g. John Doe’s broken leg) which may include multiple visits.
        //	Currently not used by NewCrop except for being echoed back; it is possible this functionality would be expanded in the future based on its intent as noted.
        //ExternalSource = a codified field noting the origin of the prescription. This may not be used.
        //Serialize
        MemoryStream memoryStream = new MemoryStream();
        XmlSerializer xmlSerializer = new XmlSerializer(NCScript.class);
        xmlSerializer.Serialize(memoryStream, ncScript);
        byte[] memoryStreamInBytes = memoryStream.ToArray();
        return Encoding.UTF8.GetString(memoryStreamInBytes, 0, memoryStreamInBytes.Length);
    }

}


