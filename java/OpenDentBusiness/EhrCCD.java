//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.Cvx;
import OpenDentBusiness.Cvxs;
import OpenDentBusiness.DentalSpecialty;
import OpenDentBusiness.Disease;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.EhrCarePlan;
import OpenDentBusiness.EhrCarePlans;
import OpenDentBusiness.EhrCCD;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.EhrLabResults;
import OpenDentBusiness.EhrLabs;
import OpenDentBusiness.EhrMeasureEvent;
import OpenDentBusiness.EhrMeasureEvents;
import OpenDentBusiness.EhrMeasureEventType;
import OpenDentBusiness.EmailAttach;
import OpenDentBusiness.EmailMessage;
import OpenDentBusiness.EmailMessages;
import OpenDentBusiness.Encounter;
import OpenDentBusiness.Encounters;
import OpenDentBusiness.FunctionalStatus;
import OpenDentBusiness.IdentifierType;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.Medication;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Medications;
import OpenDentBusiness.OIDInternals;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientPosition;
import OpenDentBusiness.PatientRace;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatRace;
import OpenDentBusiness.PIn;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.ProblemStatus;
import OpenDentBusiness.Procedure;
import OpenDentBusiness.ProcedureCode;
import OpenDentBusiness.ProcedureCodes;
import OpenDentBusiness.Procedures;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SecurityLogs;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.SnomedAllergy;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.VaccineCompletionStatus;
import OpenDentBusiness.VaccineDef;
import OpenDentBusiness.VaccineDefs;
import OpenDentBusiness.VaccinePat;
import OpenDentBusiness.VaccinePats;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;

public class EhrCCD   
{
    /**
    * OID: 2.16.840.1.113883.6.96
    */
    private static final String strCodeSystemSnomed = "2.16.840.1.113883.6.96";
    /**
    * SNOMED CT
    */
    private static final String strCodeSystemNameSnomed = "SNOMED CT";
    /**
    * OID: 2.16.840.1.113883.6.88
    */
    private static final String strCodeSystemRxNorm = "2.16.840.1.113883.6.88";
    /**
    * RxNorm
    */
    private static final String strCodeSystemNameRxNorm = "RxNorm";
    /**
    * OID: 2.16.840.1.113883.6.1
    */
    private static final String strCodeSystemLoinc = "2.16.840.1.113883.6.1";
    /**
    * LOINC
    */
    private static final String strCodeSystemNameLoinc = "LOINC";
    /**
    * OID: 2.16.840.1.113883.12.292
    */
    private static final String strCodeSystemCvx = "2.16.840.1.113883.12.292";
    /**
    * CVX
    */
    private static final String strCodeSystemNameCvx = "CVX";
    /**
    * OID: 2.16.840.1.113883.6.12
    */
    private static final String strCodeSystemCpt4 = "2.16.840.1.113883.6.12";
    /**
    * CPT-4
    */
    private static final String strCodeSystemNameCpt4 = "CPT-4";
    /**
    * OID: 2.16.840.1.113883.6.104
    */
    private static final String strCodeSystemIcd9 = "2.16.840.1.113883.6.104";
    /**
    * ICD9
    */
    private static final String strCodeSystemNameIcd9 = "ICD9";
    /**
    * OID: 2.16.840.1.113883.6.4
    */
    private static final String strCodeSystemIcd10 = "2.16.840.1.113883.6.4";
    /**
    * ICD10
    */
    private static final String strCodeSystemNameIcd10 = "ICD10";
    /**
    * OID: 2.16.840.1.113883.6.101
    */
    private static final String strCodeSystemNucc = "2.16.840.1.113883.6.101";
    /**
    * NUCC
    */
    private static final String strCodeSystemNameNucc = "NUCC";
    /**
    * OID: 2.16.840.1.113883.4.9
    */
    private static final String strCodeSystemUnii = "2.16.840.1.113883.4.9";
    /**
    * UNII
    */
    private static final String strCodeSystemNameUnii = "UNII";
    /**
    * Set each time GenerateCCD() is called. Used by helper functions to avoid sending the patient as a parameter to each helper function.
    */
    private Patient _patOutCcd = null;
    /**
    * Set each time ValidateAll and ValidateAllergy is called.
    */
    private static List<Allergy> _listAllergiesFiltered = new List<Allergy>();
    /**
    * Set each time ValidateAll and ValidateEncounter is called.
    */
    private static List<Encounter> _listEncountersFiltered = new List<Encounter>();
    /**
    * Set each time ValidateAll and ValidateFunctionalStatus is called.
    */
    private static List<Disease> _listProblemsFuncFiltered = new List<Disease>();
    /**
    * Set each time ValidateAll and ValidateImmunization is called.
    */
    private static List<VaccinePat> _listVaccinePatsFiltered = new List<VaccinePat>();
    /**
    * Set each time ValidateAll and ValidateMedication is called.
    */
    private static List<MedicationPat> _listMedPatsFiltered = new List<MedicationPat>();
    /**
    * Set each time ValidateAll and ValidatePlanOfCare is called.
    */
    private static List<EhrCarePlan> _listEhrCarePlansFiltered = new List<EhrCarePlan>();
    /**
    * Set each time ValidateAll and ValidateProblem is called.
    */
    private static List<Disease> _listProblemsFiltered = new List<Disease>();
    /**
    * Set each time ValidateAll and ValidateProcedure is called.
    */
    private static List<Procedure> _listProcsFiltered = new List<Procedure>();
    /**
    * Set each time ValidateAll and ValidateLabResult is called.
    */
    private static List<EhrLabResult> _listLabResultFiltered = new List<EhrLabResult>();
    /**
    * Set each time ValidateAll and ValidateSocialHistory is called.
    */
    private static List<EhrMeasureEvent> _listEhrMeasureEventsFiltered = new List<EhrMeasureEvent>();
    /**
    * Set each time ValidateAll and ValidateVitalSign is called.
    */
    private static List<Vitalsign> _listVitalSignsFiltered = new List<Vitalsign>();
    /**
    * Instantiated each time GenerateCCD() is called. Used by helper functions to avoid sending the writer as a parameter to each helper function.
    */
    private XmlWriter _w = null;
    /**
    * Instantiated each time GenerateCCD() is called. Used to generate unique "id" element "root" attribute identifiers. The Ids in this list are random alpha-numeric and 32 characters in length.
    */
    private HashSet<String> _hashCcdIds = new HashSet<String>();
    /**
    * Instantiated each time GenerateCCD() is called. Used to generate unique "id" element "root" attribute identifiers. The Ids in this list are random GUIDs which are 36 characters in length.
    */
    private HashSet<String> _hashCcdGuids = new HashSet<String>();
    /**
    * Constructor is private to limit instantiation to internal use only. All access to this class is static, however, there are private member variables which are used by each instance for ease of access.
    */
    private EhrCCD() throws Exception {
    }

    /**
    * Generates a Clinical Summary XML document with an appropriate referral string. Throws an exception if validation fails.
    */
    public static String generateClinicalSummary(Patient pat, boolean hasAllergy, boolean hasEncounter, boolean hasFunctionalStatus, boolean hasImmunization, boolean hasMedication, boolean hasPlanOfCare, boolean hasProblem, boolean hasProcedure, boolean hasReferral, boolean hasResult, boolean hasSocialHistory, boolean hasVitalSign, String instructions) throws Exception {
        String referralReason = "Summary of previous appointment requested.";
        EhrCCD ccd = new EhrCCD();
        return ccd.generateCCD(pat,referralReason,hasAllergy,hasEncounter,hasFunctionalStatus,hasImmunization,hasMedication,hasPlanOfCare,hasProblem,hasProcedure,hasReferral,hasResult,hasSocialHistory,hasVitalSign,instructions);
    }

    /**
    * Generates a Summary of Care XML document with an appropriate referral string. Throws an exception if validation fails.
    */
    public static String generateSummaryOfCare(Patient pat) throws Exception {
        String referralReason = "Transfer of care to another provider.";
        return generateCCD(pat,referralReason);
    }

    /**
    * Generates an Electronic Copy XML document with an appropriate referral string. Throws an exception if validation fails.
    */
    public static String generateElectronicCopy(Patient pat) throws Exception {
        String referralReason = "Patient requested a copy of medical records.";
        return generateCCD(pat,referralReason);
    }

    /**
    * Generates a Patient Export XML document with an appropriate referral string. Throws an exception if validation fails.
    */
    public static String generatePatientExport(Patient pat) throws Exception {
        String referralReason = "Patient requested export.";
        return generateCCD(pat,referralReason);
    }

    /**
    * Throws an exception if validation fails.
    */
    private static String generateCCD(Patient pat, String referralReason) throws Exception {
        EhrCCD ccd = new EhrCCD();
        return ccd.generateCCD(pat,referralReason,true,true,true,true,true,true,true,true,true,true,true,true,null);
    }

    /**
    * Throws an exception if validation fails.
    */
    private String generateCCD(Patient pat, String referralReason, boolean hasAllergy, boolean hasEncounter, boolean hasFunctionalStatus, boolean hasImmunization, boolean hasMedication, boolean hasPlanOfCare, boolean hasProblem, boolean hasProcedure, boolean hasReferral, boolean hasResult, boolean hasSocialHistory, boolean hasVitalSign, String instructions) throws Exception {
        Medications.refresh();
        String strErrors = validateAll(pat);
        if (!StringSupport.equals(strErrors, ""))
        {
            throw new ApplicationException(strErrors);
        }
         
        _patOutCcd = pat;
        _hashCcdIds = new HashSet<String>();
        //The IDs only need to be unique within each CCD document.
        _hashCcdGuids = new HashSet<String>();
        //The UUIDs only need to be unique within each CCD document.
        XmlWriterSettings xmlSettings = new XmlWriterSettings();
        xmlSettings.Encoding = Encoding.UTF8;
        xmlSettings.OmitXmlDeclaration = true;
        xmlSettings.Indent = true;
        xmlSettings.IndentChars = "   ";
        StringBuilder strBuilder = new StringBuilder();
        IDisposable __newVar0 = _w = XmlWriter.Create(strBuilder, xmlSettings);
        try
        {
            {
                //Begin Clinical Document
                _w.WriteRaw("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                _w.WriteProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"ccd.xsl\"");
                _w.WriteWhitespace("\r\n");
                _w.WriteStartElement("ClinicalDocument", "urn:hl7-org:v3");
                _w.WriteAttributeString("xmlns", "xsi", null, "http://www.w3.org/2001/XMLSchema-instance");
                _w.WriteAttributeString("xsi", "noNamespaceSchemaLocation", null, "Registry_Payment.xsd");
                _w.WriteAttributeString("xsi", "schemaLocation", null, "urn:hl7-org:v3 http://xreg2.nist.gov:8080/hitspValidation/schema/cdar2c32/infrastructure/cda/C32_CDA.xsd");
                startAndEnd("realmCode","code","US");
                startAndEnd("typeId","root","2.16.840.1.113883.1.3","extension","POCD_HD000040");
                //template id to assert use of the CCD standard
                _w.WriteComment("US General Header Template");
                templateId("2.16.840.1.113883.10.20.22.1.1");
                _w.WriteComment("Conforms to CCD requirements");
                templateId("2.16.840.1.113883.10.20.22.1.2");
                guid();
                startAndEnd("code","code","34133-9","codeSystemName",strCodeSystemNameLoinc,"codeSystem",strCodeSystemLoinc,"displayName","Summarization of Episode Note");
                _w.WriteElementString("title", "Continuity of Care Document");
                TimeElement("effectiveTime", DateTime.Now);
                startAndEnd("confidentialityCode","code","N","codeSystem","2.16.840.1.113883.5.25");
                //Fixed value.  Confidentiality Code System.  Codes: N=(Normal), R=(Restricted),V=(Very Restricted)
                startAndEnd("languageCode","code","en-US");
                start("recordTarget");
                start("patientRole");
                //TODO: We might need to assign a global GUID for each office so that the patient can be uniquely identified anywhere in the world.
                StartAndEnd("id", "extension", pat.PatNum.ToString(), "root", OIDInternals.getForType(IdentifierType.Patient).IDRoot);
                if (pat.SSN.Length == 9)
                {
                    startAndEnd("id","extension",pat.SSN,"root","2.16.840.1.113883.4.1");
                }
                 
                //TODO: We might need to assign a global GUID for each office so that the patient can be uniquely identified anywhere in the world.
                addressUnitedStates(pat.Address,pat.Address2,pat.City,pat.State);
                //Validated
                if (!StringSupport.equals(pat.WirelessPhone.Trim(), ""))
                {
                    //There is at least one phone, due to validation.
                    startAndEnd("telecom","use","HP","value","tel:" + pat.WirelessPhone.Trim());
                }
                else if (!StringSupport.equals(pat.HmPhone.Trim(), ""))
                {
                    startAndEnd("telecom","use","HP","value","tel:" + pat.HmPhone.Trim());
                }
                else if (!StringSupport.equals(pat.WkPhone.Trim(), ""))
                {
                    startAndEnd("telecom","use","HP","value","tel:" + pat.WkPhone.Trim());
                }
                   
                start("patient");
                start("name","use","L");
                _w.WriteElementString("given", pat.FName);
                //Validated
                if (!StringSupport.equals(pat.MiddleI, ""))
                {
                    _w.WriteElementString("given", pat.MiddleI);
                }
                 
                _w.WriteElementString("family", pat.LName);
                //Validated
                if (!StringSupport.equals(pat.Title, ""))
                {
                    start("suffix","qualifier","TITLE");
                    _w.WriteString(pat.Title);
                    end("suffix");
                }
                 
                end("name");
                String strGender = "UN";
                //Undifferentiated
                if (pat.Gender == PatientGender.Female)
                {
                    strGender = "F";
                }
                else if (pat.Gender == PatientGender.Male)
                {
                    strGender = "M";
                }
                  
                startAndEnd("administrativeGenderCode","code",strGender,"codeSystem","2.16.840.1.113883.5.1");
                //Will always be present, because there are only 3 options and the user is forced to make a choice in the UI.
                dateElement("birthTime",pat.Birthdate);
                //Validated
                if (pat.Position == PatientPosition.Divorced)
                {
                    startAndEnd("maritalStatusCode","code","D","displayName","Divorced","codeSystem","2.16.840.1.113883.5.2","codeSystemName","MaritalStatusCode");
                }
                else if (pat.Position == PatientPosition.Married)
                {
                    startAndEnd("maritalStatusCode","code","M","displayName","Married","codeSystem","2.16.840.1.113883.5.2","codeSystemName","MaritalStatusCode");
                }
                else if (pat.Position == PatientPosition.Widowed)
                {
                    startAndEnd("maritalStatusCode","code","W","displayName","Widowed","codeSystem","2.16.840.1.113883.5.2","codeSystemName","MaritalStatusCode");
                }
                else
                {
                    //Single and child
                    startAndEnd("maritalStatusCode","code","S","displayName","Never Married","codeSystem","2.16.840.1.113883.5.2","codeSystemName","MaritalStatusCode");
                }   
                boolean isRaceFound = true;
                PatRace patRace = PatRace.DeclinedToSpecifyRace;
                boolean isHispanicOrLatino = false;
                List<PatientRace> listPatientRaces = PatientRaces.getForPatient(pat.PatNum);
                for (int i = 0;i < listPatientRaces.Count;i++)
                {
                    if (listPatientRaces[i].Race == PatRace.Hispanic)
                    {
                        isHispanicOrLatino = true;
                    }
                    else if (listPatientRaces[i].Race == PatRace.NotHispanic)
                    {
                    }
                    else //Nothing to do. Flag is set to false by default.
                    if (listPatientRaces[i].Race == PatRace.DeclinedToSpecifyRace)
                    {
                        isRaceFound = false;
                    }
                    else if (patRace == PatRace.DeclinedToSpecifyRace)
                    {
                        //Only once race can be specified in the CCD document.
                        patRace = listPatientRaces[i].Race;
                    }
                        
                }
                if (isRaceFound)
                {
                    //The patient did not decline to specify.
                    if (patRace == PatRace.AfricanAmerican)
                    {
                        startAndEnd("raceCode","code","2054-5","displayName","Black or African American","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                    }
                    else if (patRace == PatRace.AmericanIndian)
                    {
                        startAndEnd("raceCode","code","1002-5","displayName","American Indian or Alaska Native","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                    }
                    else if (patRace == PatRace.Asian)
                    {
                        startAndEnd("raceCode","code","2028-9","displayName","Asian","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                    }
                    else if (patRace == PatRace.HawaiiOrPacIsland)
                    {
                        startAndEnd("raceCode","code","2076-8","displayName","Native Hawaiian or Other Pacific Islander","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                    }
                    else if (patRace == PatRace.White)
                    {
                        startAndEnd("raceCode","code","2106-3","displayName","White","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                    }
                    else
                    {
                        //Aboriginal, Other, Multiracial
                        startAndEnd("raceCode","code","2131-1","displayName","Other Race","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                    }     
                }
                 
                if (isHispanicOrLatino)
                {
                    startAndEnd("ethnicGroupCode","code","2135-2","displayName","Hispanic or Latino","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                }
                else
                {
                    //Not hispanic
                    startAndEnd("ethnicGroupCode","code","2186-5","displayName","Not Hispanic or Latino","codeSystem","2.16.840.1.113883.6.238","codeSystemName","Race &amp; Ethnicity - CDC");
                } 
                if (_patOutCcd.Language.Trim().Length == 3)
                {
                    //This segment is optional, but we added it because it seems to be important to Drummond.
                    //We can only allow 3 letter ISO-3 codes. It is possible that the user manually typed something which is 3 characters and is not an ISO code,
                    //but we will enhance for that situation later. This should catch 98% of all situations for now.
                    start("languageCommunication");
                    StartAndEnd("languageCode", "code", pat.Language.Trim().ToLower());
                    end("languageCommunication");
                }
                 
                end("patient");
                end("patientRole");
                end("recordTarget");
                //author--------------------------------------------------------------------------------------------------
                //The author element represents the creator of the clinical document.  The author may be a device, or a person.  Section 2.1.2, page 65
                Provider provAuthor = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                start("author");
                TimeElement("time", DateTime.Now);
                start("assignedAuthor");
                startAndEnd("id","extension",provAuthor.NationalProvID,"root","2.16.840.1.113883.4.6");
                //Validated NPI. TODO: We might need to assign a global GUID for each office so that the provider can be uniquely identified anywhere in the world.
                startAndEnd("code","code",getTaxonomy(provAuthor),"codeSystem",strCodeSystemNucc,"codeSystemName",strCodeSystemNameNucc);
                addressUnitedStates(PrefC.getString(PrefName.PracticeAddress),PrefC.getString(PrefName.PracticeAddress2),PrefC.getString(PrefName.PracticeCity),PrefC.getString(PrefName.PracticeST));
                //Validated
                String strPracticePhone = PrefC.getString(PrefName.PracticePhone);
                //Validated
                strPracticePhone = strPracticePhone.Substring(0, 3) + "-" + strPracticePhone.Substring(3, 3) + "-" + strPracticePhone.Substring(6);
                startAndEnd("telecom","use","WP","value","tel:" + strPracticePhone);
                //Validated
                start("assignedPerson");
                start("name");
                _w.WriteElementString("given", provAuthor.FName);
                //Validated
                _w.WriteElementString("family", provAuthor.LName);
                //Validated
                end("name");
                end("assignedPerson");
                end("assignedAuthor");
                end("author");
                //custodian------------------------------------------------------------------------------------------------
                //"Represents the organization in charge of maintaining the document." Section 2.1.5, page 72
                //The custodian is the steward that is entrusted with the care of the document. Every CDA document has exactly one custodian.
                Provider provCustodian = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                start("custodian");
                start("assignedCustodian");
                start("representedCustodianOrganization");
                startAndEnd("id","extension",provCustodian.NationalProvID,"root","2.16.840.1.113883.4.6");
                //Validated NPI. We might need to assign a global GUID for each office so that the provider can be uniquely identified anywhere in the world.
                String custodianTitle = PrefC.getString(PrefName.PracticeTitle);
                String custodianAddress = PrefC.getString(PrefName.PracticeAddress);
                //Validated
                String custodianAddress2 = PrefC.getString(PrefName.PracticeAddress2);
                //Validated
                String custodianCity = PrefC.getString(PrefName.PracticeCity);
                //Validated
                String custodianState = PrefC.getString(PrefName.PracticeST);
                //Validated
                String custodianPhone = strPracticePhone;
                if (!PrefC.getBool(PrefName.EasyNoClinics) && _patOutCcd.ClinicNum != 0)
                {
                    Clinic clinicCustodian = Clinics.getClinic(_patOutCcd.ClinicNum);
                    custodianTitle = clinicCustodian.Description;
                    custodianAddress = clinicCustodian.Address;
                    //Validated
                    custodianAddress2 = clinicCustodian.Address2;
                    //Validated
                    custodianCity = clinicCustodian.City;
                    //Validated
                    custodianState = clinicCustodian.State;
                    //Validated
                    custodianPhone = clinicCustodian.Phone;
                }
                 
                //Validated
                _w.WriteElementString("name", custodianTitle);
                //Validated
                startAndEnd("telecom","use","WP","value","tel:" + custodianPhone);
                //Validated
                addressUnitedStates(custodianAddress,custodianAddress2,custodianCity,custodianState);
                //Validated
                end("representedCustodianOrganization");
                end("assignedCustodian");
                end("custodian");
                //legalAuthenticator--------------------------------------------------------------------------------------
                //This element identifies the single person legally responsible for the document and must be present if the document has been legally authenticated.
                start("legalAuthenticator");
                TimeElement("time", DateTime.Now);
                startAndEnd("signatureCode","code","S");
                start("assignedEntity");
                Provider provLegal = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                if (pat.PriProv > 0)
                {
                    provLegal = Providers.getProv(pat.PriProv);
                }
                 
                startAndEnd("id","root","2.16.840.1.113883.4.6","extension",provLegal.NationalProvID);
                //Validated NPI. We might need to assign a global GUID for each office so that the provider can be uniquely identified anywhere in the world.
                String legalAuthAddress = PrefC.getString(PrefName.PracticeAddress);
                //Validated
                String legalAuthAddress2 = PrefC.getString(PrefName.PracticeAddress2);
                //Validated
                String legalAuthCity = PrefC.getString(PrefName.PracticeCity);
                //Validated
                String legalAuthState = PrefC.getString(PrefName.PracticeST);
                //Validated
                String legalAuthPhone = strPracticePhone;
                if (!PrefC.getBool(PrefName.EasyNoClinics) && _patOutCcd.ClinicNum != 0)
                {
                    Clinic clinicAuth = Clinics.getClinic(_patOutCcd.ClinicNum);
                    legalAuthAddress = clinicAuth.Address;
                    //Validated
                    legalAuthAddress2 = clinicAuth.Address2;
                    //Validated
                    legalAuthCity = clinicAuth.City;
                    //Validated
                    legalAuthState = clinicAuth.State;
                    //Validated
                    legalAuthPhone = clinicAuth.Phone;
                }
                 
                //Validated
                addressUnitedStates(legalAuthAddress,legalAuthAddress2,legalAuthCity,legalAuthState);
                //Validated
                startAndEnd("telecom","use","WP","value","tel:" + legalAuthPhone);
                //Validated
                start("assignedPerson");
                start("name");
                _w.WriteElementString("given", provLegal.FName);
                //Validated
                _w.WriteElementString("family", provLegal.LName);
                //Validated
                end("name");
                end("assignedPerson");
                end("assignedEntity");
                end("legalAuthenticator");
                start("documentationOf","typeCode","DOC");
                start("serviceEvent","classCode","PCPR");
                start("effectiveTime");
                TimeElement("low", DateTime.Now);
                TimeElement("high", DateTime.Now);
                end("effectiveTime");
                start("performer","typeCode","PRF");
                start("assignedEntity");
                Provider provPri = Providers.getProv(_patOutCcd.PriProv);
                //Could be 0.
                if (provPri == null)
                {
                    provPri = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
                }
                 
                startAndEnd("id","root","2.16.840.1.113883.4.6","extension",provPri.NationalProvID);
                //Validated NPI. We might need to assign a global GUID for each office so that the provider can be uniquely identified anywhere in the world.
                addressUnitedStates(PrefC.getString(PrefName.PracticeAddress),PrefC.getString(PrefName.PracticeAddress2),PrefC.getString(PrefName.PracticeCity),PrefC.getString(PrefName.PracticeST));
                //Validated
                startAndEnd("telecom","use","WP","value","tel:" + strPracticePhone);
                //Validated
                start("assignedPerson");
                start("name");
                _w.WriteElementString("given", provPri.FName);
                //Validated
                _w.WriteElementString("family", provPri.LName);
                //Validated
                end("name");
                end("assignedPerson");
                end("assignedEntity");
                end("performer");
                end("serviceEvent");
                end("documentationOf");
                _w.WriteComment("\r\n" + 
                "=====================================================================================================\r\n" + 
                "Body\r\n" + 
                "=====================================================================================================");
                start("component");
                start("structuredBody");
                generateCcdSectionAllergies(hasAllergy);
                generateCcdSectionEncounters(hasEncounter);
                generateCcdSectionFunctionalStatus(hasFunctionalStatus);
                generateCcdSectionImmunizations(hasImmunization);
                generateCcdSectionInstructions(instructions);
                generateCcdSectionMedications(hasMedication);
                generateCcdSectionPlanOfCare(hasPlanOfCare);
                generateCcdSectionProblems(hasProblem);
                generateCcdSectionProcedures(hasProcedure);
                generateCcdSectionReasonForReferral(hasReferral,referralReason);
                generateCcdSectionResults(hasResult);
                //Lab Results
                generateCcdSectionSocialHistory(hasSocialHistory);
                generateCcdSectionVitalSigns(hasVitalSign);
                end("structuredBody");
                end("component");
                end("ClinicalDocument");
            }
        }
        finally
        {
            if (__newVar0 != null)
                Disposable.mkDisposable(__newVar0).dispose();
             
        }
        SecurityLogs.makeLogEntry(OpenDentBusiness.Permissions.Copy,pat.PatNum,"CCD generated");
        return strBuilder.ToString();
    }

    //Create audit log entry.
    /**
    * Helper for GenerateCCD() and GenerateCcdSectionEncounters(). Exactly the same taxonomy codes used for X12 in eclaims.
    */
    public static String getTaxonomy(Provider provider) throws Exception {
        if (!StringSupport.equals(provider.TaxonomyCodeOverride, ""))
        {
            return provider.TaxonomyCodeOverride;
        }
         
        String spec = "1223G0001X";
        //general
        switch(provider.Specialty)
        {
            case General: 
                spec = "1223G0001X";
                break;
            case Hygienist: 
                spec = "124Q00000X";
                break;
            case PublicHealth: 
                spec = "1223D0001X";
                break;
            case Endodontics: 
                spec = "1223E0200X";
                break;
            case Pathology: 
                spec = "1223P0106X";
                break;
            case Radiology: 
                spec = "1223X0008X";
                break;
            case Surgery: 
                spec = "1223S0112X";
                break;
            case Ortho: 
                spec = "1223X0400X";
                break;
            case Pediatric: 
                spec = "1223P0221X";
                break;
            case Perio: 
                spec = "1223P0300X";
                break;
            case Prosth: 
                spec = "1223P0700X";
                break;
            case Denturist: 
                spec = "122400000X";
                break;
            case Assistant: 
                spec = "126800000X";
                break;
            case LabTech: 
                spec = "126900000X";
                break;
        
        }
        return spec;
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionAllergies(boolean hasAllergy) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Allergies\r\n" + 
        "=====================================================================================================");
        AllergyDef allergyDef;
        List<Allergy> listAllergiesFiltered = new List<Allergy>();
        if (!hasAllergy)
        {
            listAllergiesFiltered = new List<Allergy>();
        }
        else
        {
            listAllergiesFiltered = _listAllergiesFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.6.1");
        //page 230 Allergy template with required entries.
        _w.WriteComment("Allergies section template");
        startAndEnd("code","code","48765-2","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Allergies");
        _w.WriteElementString("title", "Allergies and Adverse Reactions");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listAllergiesFiltered.Count > 0 && hasAllergy)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Substance");
            _w.WriteElementString("th", "Reaction");
            _w.WriteElementString("th", "Allergy Type");
            _w.WriteElementString("th", "Status");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listAllergiesFiltered.Count;i++)
            {
                Allergy allergy = listAllergiesFiltered[i];
                if (allergy.PatNum == 0)
                {
                    allergyDef = new AllergyDef();
                }
                else
                {
                    allergyDef = AllergyDefs.getOne(allergy.AllergyDefNum);
                } 
                start("tr");
                //if(allergyDef.SnomedAllergyTo!="") {//Is Snomed allergy.
                //	Snomed snomedAllergyTo=Snomeds.GetByCode(allergyDef.SnomedAllergyTo);
                //	_w.WriteElementString("td",snomedAllergyTo.SnomedCode+" - "+snomedAllergyTo.Description);
                //}
                //else {//Medication allergy
                Medication med;
                if (allergyDef.MedicationNum == 0)
                {
                    if (StringSupport.equals(allergyDef.UniiCode, ""))
                    {
                        _w.WriteElementString("td", "");
                    }
                    else
                    {
                        _w.WriteElementString("td", allergyDef.UniiCode + " - " + allergyDef.Description);
                    } 
                }
                else
                {
                    med = Medications.getMedication(allergyDef.MedicationNum);
                    _w.WriteElementString("td", med.RxCui.ToString() + " - " + med.MedName);
                } 
                //}
                _w.WriteElementString("td", allergy.Reaction);
                _w.WriteElementString("td", AllergyDefs.getSnomedAllergyDesc(allergyDef.SnomedType));
                _w.WriteElementString("td", allergy.StatusIsActive ? "Active" : "Inactive");
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listAllergiesFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            Allergy al = new Allergy();
            listAllergiesFiltered.Add(al);
        }
         
        for (int i = 0;i < listAllergiesFiltered.Count;i++)
        {
            Allergy allergy = listAllergiesFiltered[i];
            if (allergy.PatNum == 0)
            {
                allergyDef = new AllergyDef();
            }
            else
            {
                allergyDef = AllergyDefs.getOne(allergy.AllergyDefNum);
            } 
            String allergyType = "";
            String allergyTypeName = "";
            if (allergyDef.SnomedType == SnomedAllergy.AdverseReactionsToDrug)
            {
                allergyType = "419511003";
                allergyTypeName = "Propensity to adverse reaction to drug";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.AdverseReactionsToFood)
            {
                allergyType = "418471000";
                allergyTypeName = "Propensity to adverse reaction to food";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.AdverseReactionsToSubstance)
            {
                allergyType = "419199007";
                allergyTypeName = "Propensity to adverse reaction to substance";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.AllergyToSubstance)
            {
                allergyType = "418038007";
                allergyTypeName = "Allergy to substance";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.DrugAllergy)
            {
                allergyType = "416098002";
                allergyTypeName = "Drug allergy";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.DrugIntolerance)
            {
                allergyType = "59037007";
                allergyTypeName = "Drug intolerance";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.FoodAllergy)
            {
                allergyType = "414285001";
                allergyTypeName = "Food allergy";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.FoodIntolerance)
            {
                allergyType = "235719002";
                allergyTypeName = "Food intolerance";
            }
            else if (allergyDef.SnomedType == SnomedAllergy.AdverseReactions)
            {
                allergyType = "420134006";
                allergyTypeName = "Adverse reaction";
            }
            else
            {
                allergyType = "";
                allergyTypeName = "None";
            }         
            start("entry","typeCode","DRIV");
            start("act","classCode","ACT","moodCode","EVN");
            templateId("2.16.840.1.113883.10.20.22.4.30");
            //Allergy Problem Act template
            guid();
            startAndEnd("code","code","48765-2","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Allergies and adverse reactions");
            //statusCode values allowed: active, suspended, aborted, completed.
            if (allergy.StatusIsActive)
            {
                startAndEnd("statusCode","code","active");
            }
            else
            {
                startAndEnd("statusCode","code","completed");
            } 
            start("effectiveTime");
            if (allergy.DateTStamp.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
                startAndEnd("high","nullFlavor","UNK");
            }
            else if (allergy.StatusIsActive)
            {
                StartAndEnd("low", "value", allergy.DateTStamp.ToString("yyyyMMdd"));
                startAndEnd("high","nullFlavor","UNK");
            }
            else
            {
                startAndEnd("low","nullFlavor","UNK");
                StartAndEnd("high", "value", allergy.DateTStamp.ToString("yyyyMMdd"));
            }  
            end("effectiveTime");
            start("entryRelationship","typeCode","SUBJ");
            start("observation","classCode","OBS","moodCode","EVN");
            _w.WriteComment("Allergy Observation template");
            templateId("2.16.840.1.113883.10.20.22.4.7");
            guid();
            startAndEnd("code","code","ASSERTION","codeSystem","2.16.840.1.113883.5.4");
            //Fixed Value
            startAndEnd("statusCode","code","completed");
            //fixed value (required)
            startAndEnd("effectiveTime","nullFlavor","UNK");
            //We have no field to store the date the allergy became active. DateTStamp is not the same as the active date.
            start("value");
            _w.WriteAttributeString("xsi", "type", null, "CD");
            if (allergyDef.SnomedType == SnomedAllergy.None)
            {
                attribs("nullFlavor","UNK");
            }
            else
            {
                attribs("code",allergyType,"displayName",allergyTypeName,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
            } 
            end("value");
            start("participant","typeCode","CSM");
            start("participantRole","classCode","MANU");
            start("playingEntity","classCode","MMAT");
            //pg. 331 item 9:
            //In an allergy to a specific medication the code SHALL be selected from the ValueSet 2.16.840.1.113883.3.88.12.80.16 Medication Brand Name (code system: RxNorm 2.16.840.1.113883.6.88); Example: 205734		RxNorm	Amoxicillin 25 MG/ML Oral Suspension [Amoxil]
            //Or the ValueSet 2.16.840.1.113883.3.88.12.80.17 Medication Clinical Drug (code system: RxNorm 2.16.840.1.113883.6.88). Example: 313850	RxNorm	Amoxicillin 40 MG/ML Oral Suspensionv
            //In an allergy to a class of medications the code SHALL be selected from the ValueSet 2.16.840.1.113883.3.88.12.80.18 Medication Drug Class (code system: NDF-RT 2.16.840.1.113883.3.26.1.5). Example: 2-Propanol
            //In an allergy to a food or other substance the code SHALL be selected from the ValueSet 2.16.840.1.113883.3.88.12.80.20 Ingredient Name (code system: Unique Ingredient Identifier (UNII) 2.16.840.1.113883.4.9). Example: Peanut.
            //if(allergyDef.SnomedAllergyTo!="") {//Is Snomed allergy.
            //	Snomed snomedAllergyTo=Snomeds.GetByCode(allergyDef.SnomedAllergyTo);
            //	StartAndEnd("code","code",snomedAllergyTo.SnomedCode,"displayName",snomedAllergyTo.Description,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
            //}
            //else {//Medication allergy
            //TODO: We must handle allergies to classes of medications as well as food/substance allergies in the near future.
            if (allergyDef.MedicationNum == 0)
            {
                if (StringSupport.equals(allergyDef.UniiCode, ""))
                {
                    startAndEnd("code","nullFlavor","UNK");
                }
                else
                {
                    startAndEnd("code","code",allergyDef.UniiCode,"displayName",allergyDef.Description,"codeSystem",strCodeSystemUnii,"codeSystemName",strCodeSystemNameUnii);
                } 
            }
            else
            {
                Medication med = Medications.getMedication(allergyDef.MedicationNum);
                StartAndEnd("code", "code", med.RxCui.ToString(), "displayName", med.MedName, "codeSystem", strCodeSystemRxNorm, "codeSystemName", strCodeSystemNameRxNorm);
            } 
            //}
            end("playingEntity");
            end("participantRole");
            end("participant");
            start("entryRelationship","typeCode","SUBJ","inversionInd","true");
            start("observation","classCode","OBS","moodCode","EVN");
            _w.WriteComment("Allergy Status Observation template");
            templateId("2.16.840.1.113883.10.20.22.4.28");
            startAndEnd("code","code","33999-4","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Status");
            startAndEnd("statusCode","code","completed");
            //fixed value (required)
            String status = allergy.StatusIsActive ? "Active" : "Inactive";
            if (allergy.AllergyNum == 0)
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CE");
                attribs("nullFlavor","UNK");
                end("value");
            }
            else if (StringSupport.equals(status, "Active"))
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CE");
                attribs("code","55561003","codeSystem",strCodeSystemSnomed,"displayName",status);
                end("value");
            }
            else
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CE");
                attribs("code","73425007","codeSystem",strCodeSystemSnomed,"displayName",status);
                end("value");
            }  
            end("observation");
            end("entryRelationship");
            start("entryRelationship","typeCode","SUBJ","inversionInd","true");
            start("observation","classCode","OBS","moodCode","EVN");
            _w.WriteComment("Reaction Observation template");
            templateId("2.16.840.1.113883.10.20.22.4.9");
            guid();
            startAndEnd("code","code","ASSERTION","codeSystem","2.16.840.1.113883.5.4");
            startAndEnd("statusCode","code","completed");
            //fixed value (required)
            start("effectiveTime");
            if (allergy.DateTStamp.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else if (allergy.StatusIsActive)
            {
                StartAndEnd("low", "value", allergy.DateTStamp.ToString("yyyyMMdd"));
            }
            else
            {
                startAndEnd("low","nullFlavor","UNK");
            }  
            end("effectiveTime");
            if (String.IsNullOrEmpty(allergy.SnomedReaction))
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CD");
                attribs("nullFlavor","UNK");
                end("value");
            }
            else
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CD");
                attribs("code",allergy.SnomedReaction,"codeSystem",strCodeSystemSnomed,"displayName",allergy.Reaction);
                end("value");
            } 
            end("observation");
            end("entryRelationship");
            end("observation");
            end("entryRelationship");
            end("act");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionEncounters(boolean hasEncounter) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Encounters\r\n" + 
        "=====================================================================================================");
        List<Encounter> listEncountersFiltered = new List<Encounter>();
        if (!hasEncounter)
        {
            listEncountersFiltered = new List<Encounter>();
        }
        else
        {
            listEncountersFiltered = _listEncountersFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.22.1");
        //Encounters section with coded entries required.
        _w.WriteComment("Encounters section template");
        //(Page 227)
        startAndEnd("code","code","46240-8","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","History of encounters");
        _w.WriteElementString("title", "Encounters");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listEncountersFiltered.Count > 0 && hasEncounter)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Performer");
            _w.WriteElementString("th", "Observation");
            _w.WriteElementString("th", "Date");
            _w.WriteElementString("th", "Notes");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listEncountersFiltered.Count;i++)
            {
                start("tr");
                if (listEncountersFiltered[i].ProvNum == 0)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", Providers.GetProv(listEncountersFiltered[i].ProvNum).GetFormalName());
                } 
                Snomed snomedDiagnosis = Snomeds.GetByCode(listEncountersFiltered[i].CodeValue);
                if (snomedDiagnosis == null)
                {
                    //Could be null if the code was imported from another EHR.
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", snomedDiagnosis.SnomedCode + " - " + snomedDiagnosis.Description);
                } 
                if (listEncountersFiltered[i].DateEncounter.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    DateText("td", listEncountersFiltered[i].DateEncounter);
                } 
                _w.WriteElementString("td", listEncountersFiltered[i].Note);
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listEncountersFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            Encounter enc = new Encounter();
            listEncountersFiltered.Add(enc);
        }
         
        for (int i = 0;i < listEncountersFiltered.Count;i++)
        {
            start("entry","typeCode","DRIV");
            start("encounter","classCode","ENC","moodCode","EVN");
            templateId("2.16.840.1.113883.10.20.22.4.49");
            _w.WriteComment("Encounter Activity Template");
            //(Page 358)
            guid();
            startAndEnd("code","code","99212","displayName","Outpatient Visit","codeSystemName","CPT-4");
            //CPT-4 is required. Valid codes are 99201 through 99607.
            if (listEncountersFiltered[i].DateEncounter.Year < 1880)
            {
                startAndEnd("effectiveTime","nullFlavor","UNK");
            }
            else
            {
                StartAndEnd("effectiveTime", "value", listEncountersFiltered[i].DateEncounter.ToString("yyyyMMdd"));
            } 
            start("performer");
            start("assignedEntity");
            guid();
            _w.WriteComment("Performer Information");
            Provider prov = null;
            if (listEncountersFiltered[i].ProvNum == 0)
            {
                startAndEnd("code","nullFlavor","UNK");
            }
            else
            {
                prov = Providers.GetProv(listEncountersFiltered[i].ProvNum);
                startAndEnd("code","code",getTaxonomy(prov),"codeSystem",strCodeSystemNucc,"codeSystemName",strCodeSystemNameNucc);
            } 
            //The assignedPerson element might not be allowed here. If that is the case, then performer is useless, because it would only contain the specialty code. Our HTML output shows the prov name.
            start("assignedPerson");
            if (listEncountersFiltered[i].ProvNum == 0)
            {
                startAndEnd("name","nullFlavor","UNK");
            }
            else
            {
                start("name");
                _w.WriteElementString("given", prov.FName);
                _w.WriteElementString("family", prov.LName);
                end("name");
            } 
            end("assignedPerson");
            end("assignedEntity");
            end("performer");
            //Possibly add an Instructions Template
            boolean isInversion = false;
            //Specifies that the problem was or was not observed. All problems are "observed" for now.
            start("entryRelationship","typeCode","SUBJ","inversionInd",isInversion ? "true" : "false");
            start("act","classCode","ACT","moodCode","EVN");
            _w.WriteComment("Encounter Diagnosis Template");
            templateId("2.16.840.1.113883.10.20.22.4.80");
            //(Page 362)
            guid();
            start("code");
            _w.WriteAttributeString("xsi", "type", null, "CE");
            attribs("code","29308-4","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Encounter Diagnosis");
            end("code");
            startAndEnd("statusCode","code","completed");
            start("effectiveTime");
            if (listEncountersFiltered[i].DateEncounter.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else
            {
                DateElement("low", listEncountersFiltered[i].DateEncounter);
            } 
            end("effectiveTime");
            start("entryRelationship","typeCode","SUBJ","inversionInd",isInversion ? "true" : "false");
            start("observation","classCode","OBS","moodCode","EVN","negationInd",isInversion ? "true" : "false");
            _w.WriteComment("Problem Observation Template");
            templateId("2.16.840.1.113883.10.20.22.4.4");
            //(Page 466)
            guid();
            startAndEnd("code","code","409586006","codeSystem",strCodeSystemSnomed,"displayName","Complaint");
            startAndEnd("statusCode","code","completed");
            start("effectiveTime");
            if (listEncountersFiltered[i].DateEncounter.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else
            {
                DateElement("low", listEncountersFiltered[i].DateEncounter);
            } 
            //"If the problem is known to be resolved, but the date of resolution is not known,
            //then the high element SHALL be present, and the nullFlavor attribute SHALL be set to 'UNK'.
            //Therefore, the existence of an high element within a problem does indicate that the problem has been resolved."
            end("effectiveTime");
            Snomed snomedDiagnosis = Snomeds.GetByCode(listEncountersFiltered[i].CodeValue);
            if (snomedDiagnosis == null)
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CD");
                attribs("nullFlavor","UNK");
                end("value");
            }
            else
            {
                start("value");
                _w.WriteAttributeString("xsi", "type", null, "CD");
                //The format only allows SNOMED and ICD10 code systems. If we support ICD10 in the future, then the value must be specified in a special manner. SNOMED appears to be preferred. See the guide for details.
                //Snomed snomedDiagnosis=Snomeds.GetByCode(listEncountersFiltered[i].CodeValue);
                attribs("code",snomedDiagnosis.SnomedCode,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed,"displayName",snomedDiagnosis.Description);
                end("value");
            } 
            end("observation");
            end("entryRelationship");
            end("act");
            end("entryRelationship");
            end("encounter");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionFunctionalStatus(boolean hasFunctionalStatus) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Functional and Cognitive Status\r\n" + 
        "=====================================================================================================");
        List<Disease> listProblemsFiltered = new List<Disease>();
        if (!hasFunctionalStatus)
        {
            listProblemsFiltered = new List<Disease>();
        }
        else
        {
            listProblemsFiltered = _listProblemsFuncFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.14");
        //Functional Status section. There is only one allowed template id.
        _w.WriteComment("Functional Status section template");
        //(Page 232)
        startAndEnd("code","code","47420-5","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Functional Status");
        _w.WriteElementString("title", "Functional Status");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listProblemsFiltered.Count > 0 && hasFunctionalStatus)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Condition");
            _w.WriteElementString("th", "Effective Dates");
            _w.WriteElementString("th", "Condition Status");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listProblemsFiltered.Count;i++)
            {
                DiseaseDef diseaseDef = null;
                Snomed snomedProblem = null;
                if (listProblemsFiltered[i].DiseaseDefNum > 0)
                {
                    diseaseDef = DiseaseDefs.GetItem(listProblemsFiltered[i].DiseaseDefNum);
                    if (diseaseDef != null && !String.IsNullOrEmpty(diseaseDef.SnomedCode))
                    {
                        snomedProblem = Snomeds.getByCode(diseaseDef.SnomedCode);
                    }
                     
                }
                 
                start("tr");
                if (diseaseDef == null || snomedProblem == null)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", snomedProblem.SnomedCode + " - " + snomedProblem.Description);
                } 
                if (listProblemsFiltered[i].FunctionStatus == FunctionalStatus.FunctionalResult || listProblemsFiltered[i].FunctionStatus == FunctionalStatus.CognitiveResult)
                {
                    DateText("td", listProblemsFiltered[i].DateStart);
                }
                else
                {
                    //functional problem and cognitive problem
                    if (listProblemsFiltered[i].DateStop.Year > 1880)
                    {
                        _w.WriteElementString("td", listProblemsFiltered[i].DateStart.ToString("yyyyMMdd") + " to " + listProblemsFiltered[i].DateStop.ToString("yyyyMMdd"));
                    }
                    else
                    {
                        DateText("td", listProblemsFiltered[i].DateStart);
                    } 
                } 
                _w.WriteElementString("td", "Completed");
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listProblemsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            Disease dis = new Disease();
            dis.FunctionStatus = FunctionalStatus.FunctionalProblem;
            //Just needs a version other than problem.
            listProblemsFiltered.Add(dis);
        }
         
        for (int i = 0;i < listProblemsFiltered.Count;i++)
        {
            DiseaseDef diseaseDef = null;
            Snomed snomedProblem = null;
            if (listProblemsFiltered[i].PatNum != 0)
            {
                diseaseDef = DiseaseDefs.GetItem(listProblemsFiltered[i].DiseaseDefNum);
                snomedProblem = Snomeds.getByCode(diseaseDef.SnomedCode);
            }
             
            if (diseaseDef == null)
            {
                diseaseDef = new DiseaseDef();
            }
             
            if (snomedProblem == null)
            {
                snomedProblem = new Snomed();
            }
             
            start("entry","typeCode","DRIV");
            start("observation","classCode","OBS","moodCode","EVN");
            if (listProblemsFiltered[i].FunctionStatus == FunctionalStatus.FunctionalResult)
            {
                templateId("2.16.840.1.113883.10.20.22.4.67");
                //(Page 383)
                _w.WriteComment("Functional Status Result Observation");
                guid();
                startAndEnd("code","code","54744-8","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc);
                startAndEnd("statusCode","code","completed");
                if (listProblemsFiltered[i].DateStart.Year < 1880)
                {
                    startAndEnd("effectiveTime","nullFlavor","UNK");
                }
                else
                {
                    DateElement("effectiveTime", listProblemsFiltered[i].DateStart);
                } 
                if (String.IsNullOrEmpty(snomedProblem.SnomedCode))
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("nullFlavor","UNK");
                    end("value");
                }
                else
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("code",snomedProblem.SnomedCode,"displayName",snomedProblem.Description,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
                    end("value");
                } 
            }
            else if (listProblemsFiltered[i].FunctionStatus == FunctionalStatus.CognitiveResult)
            {
                templateId("2.16.840.1.113883.10.20.22.4.74");
                //(Page 342)
                _w.WriteComment("Cognitive Status Result Observation");
                guid();
                startAndEnd("code","code","5249-2","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc);
                startAndEnd("statusCode","code","completed");
                if (listProblemsFiltered[i].DateStart.Year < 1880)
                {
                    startAndEnd("effectiveTime","nullFlavor","UNK");
                }
                else
                {
                    DateElement("effectiveTime", listProblemsFiltered[i].DateStart);
                } 
                if (String.IsNullOrEmpty(snomedProblem.SnomedCode))
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("nullFlavor","UNK");
                    end("value");
                }
                else
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("code",snomedProblem.SnomedCode,"displayName",snomedProblem.Description,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
                    end("value");
                } 
            }
            else if (listProblemsFiltered[i].FunctionStatus == FunctionalStatus.FunctionalProblem)
            {
                templateId("2.16.840.1.113883.10.20.22.4.68");
                //(Page 379)
                _w.WriteComment("Functional Status Problem Observation");
                guid();
                startAndEnd("code","code","404684003","codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed,"displayName","Finding of Functional Performance and Activity");
                startAndEnd("statusCode","code","completed");
                start("effectiveTime");
                if (listProblemsFiltered[i].DateStart.Year < 1880)
                {
                    startAndEnd("low","nullFlavor","UNK");
                }
                else
                {
                    DateElement("low", listProblemsFiltered[i].DateStart);
                } 
                //"If the problem is known to be resolved, but the date of resolution is not known, then the high element SHALL be present, and
                //the nullFlavor attribute SHALL be set to 'UNK'. Therefore, the existence of an high element within a problem does indicate that the problem has been resolved."
                if (listProblemsFiltered[i].DateStop.Year < 1880)
                {
                    startAndEnd("high","nullFlavor","UNK");
                }
                else
                {
                    DateElement("high", listProblemsFiltered[i].DateStop);
                } 
                end("effectiveTime");
                if (String.IsNullOrEmpty(snomedProblem.SnomedCode))
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("nullFlavor","UNK");
                    end("value");
                }
                else
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("code",snomedProblem.SnomedCode,"displayName",snomedProblem.Description,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
                    end("value");
                } 
            }
            else if (listProblemsFiltered[i].FunctionStatus == FunctionalStatus.CognitiveProblem)
            {
                templateId("2.16.840.1.113883.10.20.22.4.73");
                //(Page 336)
                _w.WriteComment("Cognitive Status Problem Observation");
                guid();
                startAndEnd("code","code","373930000","codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed,"displayName","Cognitive Function Finding");
                startAndEnd("statusCode","code","completed");
                //"If the problem is known to be resolved, but the date of resolution is not known, then the high element SHALL be present, and
                //the nullFlavor attribute SHALL be set to 'UNK'. Therefore, the existence of a high element within a problem does indicate that the problem has been resolved."
                start("effectiveTime");
                if (listProblemsFiltered[i].DateStart.Year < 1880)
                {
                    startAndEnd("low","nullFlavor","UNK");
                }
                else
                {
                    DateElement("low", listProblemsFiltered[i].DateStart);
                } 
                if (listProblemsFiltered[i].DateStop.Year < 1880)
                {
                    startAndEnd("high","nullFlavor","UNK");
                }
                else
                {
                    DateElement("high", listProblemsFiltered[i].DateStop);
                } 
                end("effectiveTime");
                if (String.IsNullOrEmpty(snomedProblem.SnomedCode))
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("nullFlavor","UNK");
                    end("value");
                }
                else
                {
                    start("value");
                    _w.WriteAttributeString("xsi", "type", null, "CD");
                    attribs("code",snomedProblem.SnomedCode,"displayName",snomedProblem.Description,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
                    end("value");
                } 
            }
                
            end("observation");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionImmunizations(boolean hasImmunization) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Immunizations\r\n" + 
        "=====================================================================================================");
        List<VaccinePat> listVaccinePatsFiltered = new List<VaccinePat>();
        if (!hasImmunization)
        {
            listVaccinePatsFiltered = new List<VaccinePat>();
        }
        else
        {
            listVaccinePatsFiltered = _listVaccinePatsFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.2.1");
        //Immunizations section with coded entries required.
        _w.WriteComment("Immunizations section template");
        startAndEnd("code","code","11369-6","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","History of immunizations");
        _w.WriteElementString("title", "Immunizations");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listVaccinePatsFiltered.Count > 0 && hasImmunization)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Vaccine");
            _w.WriteElementString("th", "Date");
            _w.WriteElementString("th", "Status");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listVaccinePatsFiltered.Count;i++)
            {
                VaccineDef vaccineDef;
                if (listVaccinePatsFiltered[i].VaccineDefNum == 0)
                {
                    vaccineDef = new VaccineDef();
                }
                else
                {
                    vaccineDef = VaccineDefs.GetOne(listVaccinePatsFiltered[i].VaccineDefNum);
                } 
                start("tr");
                Cvx cvx;
                if (String.IsNullOrEmpty(vaccineDef.CVXCode))
                {
                    cvx = new Cvx();
                }
                else
                {
                    cvx = Cvxs.getOneFromDb(vaccineDef.CVXCode);
                } 
                if (String.IsNullOrEmpty(cvx.CvxCode))
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", cvx.CvxCode + " - " + cvx.Description);
                } 
                if (listVaccinePatsFiltered[i].DateTimeStart.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    DateText("td", listVaccinePatsFiltered[i].DateTimeStart);
                } 
                _w.WriteElementString("td", "Completed");
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listVaccinePatsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            VaccinePat vacPat = new VaccinePat();
            listVaccinePatsFiltered.Add(vacPat);
        }
         
        for (int i = 0;i < listVaccinePatsFiltered.Count;i++)
        {
            VaccineDef vaccineDef;
            if (listVaccinePatsFiltered[i].VaccinePatNum == 0)
            {
                vaccineDef = new VaccineDef();
            }
            else
            {
                vaccineDef = VaccineDefs.GetOne(listVaccinePatsFiltered[i].VaccineDefNum);
            } 
            start("entry","typeCode","DRIV");
            start("substanceAdministration","classCode","SBADM","moodCode","EVN","negationInd",(listVaccinePatsFiltered[i].CompletionStatus == VaccineCompletionStatus.NotAdministered) ? "true" : "false");
            templateId("2.16.840.1.113883.10.20.22.4.52");
            _w.WriteComment("Immunization Activity Template");
            guid();
            startAndEnd("statusCode","code","completed");
            start("effectiveTime");
            _w.WriteAttributeString("xsi", "type", null, "IVL_TS");
            if (listVaccinePatsFiltered[i].DateTimeStart.Year < 1880)
            {
                attribs("nullFlavor","UNK");
            }
            else
            {
                Attribs("value", listVaccinePatsFiltered[i].DateTimeStart.ToString("yyyyMMdd"));
            } 
            end("effectiveTime");
            start("consumable");
            start("manufacturedProduct","classCode","MANU");
            templateId("2.16.840.1.113883.10.20.22.4.54");
            _w.WriteComment("Immunization Medication Information");
            start("manufacturedMaterial");
            if (String.IsNullOrEmpty(vaccineDef.CVXCode))
            {
                startAndEnd("code","nullFlavor","UNK");
            }
            else
            {
                Cvx cvx = Cvxs.getOneFromDb(vaccineDef.CVXCode);
                startAndEnd("code","code",cvx.CvxCode,"codeSystem",strCodeSystemCvx,"displayName",cvx.Description,"codeSystemName",strCodeSystemNameCvx);
            } 
            end("manufacturedMaterial");
            end("manufacturedProduct");
            end("consumable");
            //Possibly add an Instructions Template
            end("substanceAdministration");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionInstructions(String instructions) throws Exception {
        if (instructions == null)
        {
            return ;
        }
         
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Instructions\r\n" + 
        "=====================================================================================================");
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.45");
        //Instructions template
        _w.WriteComment("Instructions section template");
        startAndEnd("code","code","69730-0","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","History of immunizations");
        _w.WriteElementString("title", "Instructions");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.\
        if (StringSupport.equals(instructions, ""))
        {
            _w.WriteString("No instructions given");
        }
        else
        {
            _w.WriteString(instructions);
        } 
        end("text");
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionMedications(boolean hasMedication) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Medications\r\n" + 
        "=====================================================================================================");
        List<MedicationPat> listMedPatsFiltered = new List<MedicationPat>();
        if (!hasMedication)
        {
            listMedPatsFiltered = new List<MedicationPat>();
        }
        else
        {
            listMedPatsFiltered = _listMedPatsFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.1.1");
        //Medication section with coded entries required.
        _w.WriteComment("Medications section template");
        startAndEnd("code","code","10160-0","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","History of medication use");
        _w.WriteElementString("title", "Medications");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listMedPatsFiltered.Count > 0 && hasMedication)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Medication");
            _w.WriteElementString("th", "Directions");
            _w.WriteElementString("th", "Start Date");
            _w.WriteElementString("th", "End Date");
            _w.WriteElementString("th", "Status");
            _w.WriteElementString("th", "Indications");
            _w.WriteElementString("th", "Fill Instructions");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listMedPatsFiltered.Count;i++)
            {
                String strMedName = listMedPatsFiltered[i].MedDescript;
                if (listMedPatsFiltered[i].MedicationNum != 0)
                {
                    strMedName = Medications.GetNameOnly(listMedPatsFiltered[i].MedicationNum);
                }
                 
                start("tr");
                if (listMedPatsFiltered[i].RxCui == 0)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", listMedPatsFiltered[i].RxCui + " - " + strMedName);
                } 
                //Medication
                _w.WriteElementString("td", listMedPatsFiltered[i].PatNote);
                //Directions
                if (listMedPatsFiltered[i].DateStart.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    //Directions
                    DateText("td", listMedPatsFiltered[i].DateStart);
                } 
                //Start Date
                if (listMedPatsFiltered[i].DateStop.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    //Directions
                    DateText("td", listMedPatsFiltered[i].DateStop);
                } 
                //End Date
                _w.WriteElementString("td", MedicationPats.IsMedActive(listMedPatsFiltered[i]) ? "Active" : "Inactive");
                //Status
                _w.WriteElementString("td", "");
                //Indications (The conditions which make the medication necessary). We do not record this information anywhere.
                _w.WriteElementString("td", "");
                //Fill Instructions (Generic substitution allowed or not). We do not record this information anywhere.
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listMedPatsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            MedicationPat medPat = new MedicationPat();
            listMedPatsFiltered.Add(medPat);
        }
         
        for (int i = 0;i < listMedPatsFiltered.Count;i++)
        {
            String strMedName = "";
            if (listMedPatsFiltered[i].MedDescript != null)
            {
                strMedName = listMedPatsFiltered[i].MedDescript;
            }
             
            //This might be blank, for example not from NewCrop.
            if (listMedPatsFiltered[i].MedicationNum != 0)
            {
                //If NewCrop, this will be 0.  Also might be zero in the future when we start allowing freeform medications.
                strMedName = Medications.GetNameOnly(listMedPatsFiltered[i].MedicationNum);
            }
             
            start("entry","typeCode","DRIV");
            start("substanceAdministration","classCode","SBADM","moodCode","EVN");
            templateId("2.16.840.1.113883.10.20.22.4.16");
            _w.WriteComment("Medication activity template");
            guid();
            if (String.IsNullOrEmpty(listMedPatsFiltered[i].PatNote))
            {
                startAndEnd("text","nullFlavor","UNK");
            }
            else
            {
                _w.WriteElementString("text", listMedPatsFiltered[i].PatNote);
            } 
            startAndEnd("statusCode","code","active");
            start("effectiveTime");
            _w.WriteAttributeString("xsi", "type", null, "IVL_TS");
            if (listMedPatsFiltered[i].DateStart.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else
            {
                DateElement("low", listMedPatsFiltered[i].DateStart);
            } 
            //Only one of these dates can be null, because of our filter above.
            if (listMedPatsFiltered[i].DateStop.Year < 1880)
            {
                startAndEnd("high","nullFlavor","UNK");
            }
            else
            {
                DateElement("high", listMedPatsFiltered[i].DateStop);
            } 
            //Only one of these dates can be null, because of our filter above.
            end("effectiveTime");
            start("consumable");
            start("manufacturedProduct","classCode","MANU");
            templateId("2.16.840.1.113883.10.20.22.4.23");
            //Medication Information template.
            guid();
            start("manufacturedMaterial");
            //The code must be an RxNorm.
            if (listMedPatsFiltered[i].RxCui == 0)
            {
                start("code","nullFlavor","UNK");
            }
            else
            {
                Start("code", "code", listMedPatsFiltered[i].RxCui.ToString(), "codeSystem", strCodeSystemRxNorm, "displayName", strMedName, "codeSystemName", strCodeSystemNameRxNorm);
            } 
            end("code");
            end("manufacturedMaterial");
            end("manufacturedProduct");
            end("consumable");
            end("substanceAdministration");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionPlanOfCare(boolean hasPlanOfCare) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Care Plan\r\n" + 
        "=====================================================================================================");
        List<EhrCarePlan> listEhrCarePlansAll = EhrCarePlans.refresh(_patOutCcd.PatNum);
        List<EhrCarePlan> listEhrCarePlansFiltered = new List<EhrCarePlan>();
        if (!hasPlanOfCare)
        {
            listEhrCarePlansFiltered = new List<EhrCarePlan>();
        }
        else
        {
            listEhrCarePlansFiltered = _listEhrCarePlansFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.10");
        //Only one template id allowed (unlike other sections).
        _w.WriteComment("Plan of Care section template");
        startAndEnd("code","code","18776-5","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Treatment plan");
        _w.WriteElementString("title", "Care Plan");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listEhrCarePlansFiltered.Count > 0 && hasPlanOfCare)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Planned Activity");
            _w.WriteElementString("th", "Planned Date");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listEhrCarePlansFiltered.Count;i++)
            {
                start("tr");
                Snomed snomedEducation;
                snomedEducation = Snomeds.GetByCode(listEhrCarePlansFiltered[i].SnomedEducation);
                if (snomedEducation == null)
                {
                    snomedEducation = new Snomed();
                }
                 
                if (String.IsNullOrEmpty(snomedEducation.Description))
                {
                    if (String.IsNullOrEmpty(listEhrCarePlansFiltered[i].Instructions))
                    {
                        _w.WriteElementString("td", "");
                    }
                    else
                    {
                        //Planned Activity
                        _w.WriteElementString("td", "Goal: ; Instructions: " + listEhrCarePlansFiltered[i].Instructions);
                    } 
                }
                else
                {
                    //Planned Activity
                    _w.WriteElementString("td", "Goal: " + snomedEducation.SnomedCode + " - " + snomedEducation.Description + "; Instructions: " + listEhrCarePlansFiltered[i].Instructions);
                } 
                //Planned Activity
                if (listEhrCarePlansFiltered[i].DatePlanned.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    DateText("td", listEhrCarePlansFiltered[i].DatePlanned);
                } 
                //Planned Date
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listEhrCarePlansFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            EhrCarePlan eCP = new EhrCarePlan();
            listEhrCarePlansFiltered.Add(eCP);
        }
         
        for (int i = 0;i < listEhrCarePlansFiltered.Count;i++)
        {
            start("entry","typeCode","DRIV");
            start("act","classCode","ACT","moodCode","INT");
            templateId("2.16.840.1.113883.10.20.22.4.20");
            _w.WriteComment("Instructions template");
            start("code");
            _w.WriteAttributeString("xsi", "type", null, "CE");
            Snomed snomedEducation = Snomeds.GetByCode(listEhrCarePlansFiltered[i].SnomedEducation);
            if (snomedEducation == null)
            {
                attribs("nullFlavor","UNK");
            }
            else
            {
                attribs("code",snomedEducation.SnomedCode,"codeSystem",strCodeSystemSnomed,"displayName",snomedEducation.Description);
            } 
            end("code");
            if (StringSupport.equals(listEhrCarePlansFiltered[i].Instructions, ""))
            {
                startAndEnd("text","nullFlavor","UNK");
            }
            else
            {
                _w.WriteElementString("text", listEhrCarePlansFiltered[i].Instructions);
            } 
            startAndEnd("statusCode","code","completed");
            end("act");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().  Problem section.
    */
    private void generateCcdSectionProblems(boolean hasProblem) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Problems\r\n" + 
        "=====================================================================================================");
        String snomedProblemType = "55607006";
        List<Disease> listProblemsFiltered = new List<Disease>();
        if (!hasProblem)
        {
            listProblemsFiltered = new List<Disease>();
        }
        else
        {
            listProblemsFiltered = _listProblemsFiltered;
        } 
        String status = "Inactive";
        String statusCode = "73425007";
        String statusOther = "active";
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.5.1");
        //Problems section with coded entries required.
        _w.WriteComment("Problems section template");
        startAndEnd("code","code","11450-4","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Problem list");
        _w.WriteElementString("title", "Problems");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listProblemsFiltered.Count > 0 && hasProblem)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Problem");
            _w.WriteElementString("th", "Date Start");
            _w.WriteElementString("th", "Date End");
            _w.WriteElementString("th", "Status");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listProblemsFiltered.Count;i++)
            {
                DiseaseDef diseaseDef;
                if (listProblemsFiltered[i].DiseaseDefNum == 0)
                {
                    diseaseDef = new DiseaseDef();
                }
                else
                {
                    diseaseDef = DiseaseDefs.GetItem(listProblemsFiltered[i].DiseaseDefNum);
                } 
                start("tr");
                if (String.IsNullOrEmpty(diseaseDef.SnomedCode))
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", diseaseDef.SnomedCode + " - " + diseaseDef.DiseaseName);
                } 
                if (listProblemsFiltered[i].DateStart.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    //Directions
                    DateText("td", listProblemsFiltered[i].DateStart);
                } 
                //Start Date
                if (listProblemsFiltered[i].DateStop.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    //Directions
                    DateText("td", listProblemsFiltered[i].DateStop);
                } 
                //End Date
                if (listProblemsFiltered[i].ProbStatus == ProblemStatus.Active)
                {
                    status = "Active";
                    statusCode = "55561003";
                    statusOther = "active";
                }
                else if (listProblemsFiltered[i].ProbStatus == ProblemStatus.Inactive)
                {
                    status = "Inactive";
                    statusCode = "73425007";
                    statusOther = "completed";
                }
                else
                {
                    status = "Resolved";
                    statusCode = "413322009";
                    statusOther = "completed";
                }  
                _w.WriteElementString("td", status);
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        //Start("text");
        //StartAndEnd("content","ID","problems");
        //Start("list","listType","ordered");
        //for(int i=0;i<listProblemsFiltered.Count;i++) {//Fill Problems Table
        //	DiseaseDef diseaseDef=DiseaseDefs.GetItem(listProblemsFiltered[i].DiseaseDefNum);
        //	Start("item");
        //	_w.WriteString(diseaseDef.SnomedCode+" - "+diseaseDef.DiseaseName+" : "+"Status - ");
        //	if(listProblemsFiltered[i].ProbStatus==ProblemStatus.Active) {
        //		_w.WriteString("Active");
        //		status="Active";
        //		statusCode="55561003";
        //		statusOther="active";
        //	}
        //	else if(listProblemsFiltered[i].ProbStatus==ProblemStatus.Inactive) {
        //		_w.WriteString("Inactive");
        //		status="Inactive";
        //		statusCode="73425007";
        //		statusOther="completed";
        //	}
        //	else {
        //		_w.WriteString("Resolved");
        //		status="Resolved";
        //		statusCode="413322009";
        //		statusOther="completed";
        //	}
        //	End("item");
        //}
        //End("list");
        //End("text");
        if (listProblemsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            Disease dis = new Disease();
            listProblemsFiltered.Add(dis);
        }
         
        for (int i = 0;i < listProblemsFiltered.Count;i++)
        {
            //Fill Problems Info
            DiseaseDef diseaseDef;
            if (listProblemsFiltered[i].DiseaseDefNum == 0)
            {
                diseaseDef = new DiseaseDef();
            }
            else
            {
                diseaseDef = DiseaseDefs.GetItem(listProblemsFiltered[i].DiseaseDefNum);
            } 
            start("entry","typeCode","DRIV");
            start("act","classCode","ACT","moodCode","EVN");
            _w.WriteComment("Problem Concern Act template");
            //Concern Act Section
            templateId("2.16.840.1.113883.10.20.22.4.3");
            guid();
            startAndEnd("code","code","CONC","codeSystem","2.16.840.1.113883.5.6","displayName","Concern");
            startAndEnd("statusCode","code",statusOther);
            //Allowed values: active, suspended, aborted, completed.
            start("effectiveTime");
            if (listProblemsFiltered[i].DateStart.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else
            {
                DateElement("low", listProblemsFiltered[i].DateStart);
            } 
            if (listProblemsFiltered[i].DateStop.Year < 1880)
            {
                startAndEnd("high","nullFlavor","UNK");
            }
            else
            {
                DateElement("high", listProblemsFiltered[i].DateStop);
            } 
            end("effectiveTime");
            start("entryRelationship","typeCode","SUBJ");
            start("observation","classCode","OBS","moodCode","EVN");
            _w.WriteComment("Problem Observation template");
            //Observation Section
            templateId("2.16.840.1.113883.10.20.22.4.4");
            guid();
            startAndEnd("code","code",snomedProblemType,"codeSystem",strCodeSystemSnomed,"displayName","Problem");
            startAndEnd("statusCode","code","completed");
            //Allowed values: completed.
            start("effectiveTime");
            if (listProblemsFiltered[i].DateStart.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else
            {
                DateElement("low", listProblemsFiltered[i].DateStart);
            } 
            end("effectiveTime");
            start("value");
            _w.WriteAttributeString("xsi", "type", null, "CD");
            if (String.IsNullOrEmpty(diseaseDef.SnomedCode))
            {
                attribs("nullFlavor","UNK");
            }
            else
            {
                attribs("code",diseaseDef.SnomedCode,"codeSystem",strCodeSystemSnomed,"displayName",diseaseDef.DiseaseName);
            } 
            end("value");
            start("entryRelationship","typeCode","REFR");
            start("observation","classCode","OBS","moodCode","EVN");
            _w.WriteComment("Status Observation template");
            //Status Observation Section
            templateId("2.16.840.1.113883.10.20.22.4.6");
            start("code");
            _w.WriteAttributeString("xsi", "type", null, "CE");
            attribs("code","33999-4","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Status");
            end("code");
            startAndEnd("statusCode","code","completed");
            //Allowed values: completed.
            start("value");
            _w.WriteAttributeString("xsi", "type", null, "CD");
            attribs("code",statusCode,"codeSystem",strCodeSystemSnomed,"displayName",status);
            end("value");
            end("observation");
            end("entryRelationship");
            end("observation");
            end("entryRelationship");
            end("act");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionProcedures(boolean hasProcedure) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Procedures\r\n" + 
        "=====================================================================================================");
        List<Procedure> listProcsFiltered = new List<Procedure>();
        if (!hasProcedure)
        {
            listProcsFiltered = new List<Procedure>();
        }
        else
        {
            listProcsFiltered = _listProcsFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.7.1");
        //Procedures section with coded entries required (Page 285).
        _w.WriteComment("Procedures section template");
        startAndEnd("code","code","47519-4","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","History of procedures");
        _w.WriteElementString("title", "Procedures");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listProcsFiltered.Count > 0 && hasProcedure)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Procedure");
            _w.WriteElementString("th", "Body Site");
            _w.WriteElementString("th", "Date");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listProcsFiltered.Count;i++)
            {
                ProcedureCode procCode;
                Snomed bodySite = Snomeds.GetByCode(listProcsFiltered[i].SnomedBodySite);
                Snomed procCodeSnomed;
                if (listProcsFiltered[i].CodeNum == 0)
                {
                    procCode = new ProcedureCode();
                    procCodeSnomed = new Snomed();
                }
                else
                {
                    procCode = ProcedureCodes.GetProcCode(listProcsFiltered[i].CodeNum);
                    procCodeSnomed = Snomeds.getByCode(procCode.ProcCode);
                } 
                if (procCodeSnomed == null)
                {
                    procCodeSnomed = new Snomed();
                }
                 
                start("tr");
                if (!String.IsNullOrEmpty(procCodeSnomed.SnomedCode))
                {
                    _w.WriteElementString("td", procCodeSnomed.SnomedCode + " - " + procCode.Descript);
                }
                else if (!String.IsNullOrEmpty(procCode.MedicalCode))
                {
                    _w.WriteElementString("td", procCode.MedicalCode + " - " + procCode.Descript);
                }
                else
                {
                    _w.WriteElementString("td", "");
                }  
                if (bodySite == null || String.IsNullOrEmpty(bodySite.SnomedCode))
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", bodySite.SnomedCode + " - " + bodySite.Description);
                } 
                if (listProcsFiltered[i].ProcDate.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    DateText("td", listProcsFiltered[i].ProcDate);
                } 
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listProcsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            Procedure proc = new Procedure();
            listProcsFiltered.Add(proc);
        }
         
        for (int i = 0;i < listProcsFiltered.Count;i++)
        {
            ProcedureCode procCode;
            Snomed procCodeSnomed;
            if (listProcsFiltered[i].CodeNum == 0)
            {
                procCode = new ProcedureCode();
                procCodeSnomed = new Snomed();
            }
            else
            {
                procCode = ProcedureCodes.GetProcCode(listProcsFiltered[i].CodeNum);
                procCodeSnomed = Snomeds.getByCode(procCode.ProcCode);
            } 
            if (procCodeSnomed == null)
            {
                procCodeSnomed = new Snomed();
            }
             
            start("entry","typeCode","DRIV");
            start("procedure","classCode","PROC","moodCode","EVN");
            templateId("2.16.840.1.113883.10.20.22.4.14");
            //Procedure Activity Section (Page 487).
            _w.WriteComment("Procedure Activity Template");
            guid();
            //"This code in a procedure activity SHOULD be selected from LOINC (codeSystem 2.16.840.1.113883.6.1) or SNOMED CT (CodeSystem: 2.16.840.1.113883.6.96),
            //and MAY be selected from CPT-4 (CodeSystem: 2.16.840.1.113883.6.12), ICD9 Procedures (CodeSystem: 2.16.840.1.113883.6.104),
            //ICD10 Procedure Coding System (CodeSystem: 2.16.840.1.113883.6.4) (CONF:7657)."
            //We already have a place for CPT codes, and that is ProcedureCode.MedicalCode. We will simply use this field for now.
            if (!String.IsNullOrEmpty(procCodeSnomed.SnomedCode))
            {
                startAndEnd("code","code",procCodeSnomed.SnomedCode,"codeSystem",strCodeSystemSnomed,"displayName",procCode.Descript,"codeSystemName",strCodeSystemNameSnomed);
            }
            else if (!String.IsNullOrEmpty(procCode.MedicalCode))
            {
                startAndEnd("code","code",procCode.MedicalCode,"codeSystem",strCodeSystemCpt4,"displayName",procCode.Descript,"codeSystemName",strCodeSystemNameCpt4);
            }
            else
            {
                startAndEnd("code","nullFlavor","UNK");
            }  
            startAndEnd("statusCode","code","completed");
            //Allowed values: completed, active, aborted, cancelled.
            if (listProcsFiltered[i].ProcDate.Year < 1880)
            {
                startAndEnd("effectiveTime","nullFlavor","UNK");
            }
            else
            {
                DateElement("effectiveTime", listProcsFiltered[i].ProcDate);
            } 
            end("procedure");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionReasonForReferral(boolean hasReferral, String referralReason) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Reason for Referral\r\n" + 
        "=====================================================================================================");
        start("component");
        start("section");
        templateId("1.3.6.1.4.1.19376.1.5.3.1.3.1");
        _w.WriteComment("Reason for Referral Section Template");
        startAndEnd("code","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"code","42349-1","displayName","Reason for Referral");
        _w.WriteElementString("title", "Reason for Referral");
        start("text");
        if (!hasReferral)
        {
            _w.WriteElementString("paragraph", "None");
        }
        else
        {
            _w.WriteElementString("paragraph", referralReason);
        } 
        end("text");
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().  Exports Labs.
    */
    private void generateCcdSectionResults(boolean hasSectionResult) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Laboratory Test Results\r\n" + 
        "=====================================================================================================");
        List<EhrLabResult> listLabResultFiltered = new List<EhrLabResult>();
        if (!hasSectionResult)
        {
            listLabResultFiltered = new List<EhrLabResult>();
        }
        else
        {
            listLabResultFiltered = _listLabResultFiltered;
        } 
        EhrLab labPanel;
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.3.1");
        //page 309 Results section with coded entries required.
        _w.WriteComment("Diagnostic Results section template");
        startAndEnd("code","code","30954-2","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Results");
        _w.WriteElementString("title", "Diagnostic Results");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listLabResultFiltered.Count > 0 && hasSectionResult)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "LOINC Code");
            _w.WriteElementString("th", "Test");
            _w.WriteElementString("th", "Result");
            _w.WriteElementString("th", "Abnormal Flag");
            _w.WriteElementString("th", "Date Performed");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listLabResultFiltered.Count;i++)
            {
                Loinc labLoinc = Loincs.GetByCode(listLabResultFiltered[i].ObservationIdentifierID);
                String value = "";
                ValueType __dummyScrutVar1 = listLabResultFiltered[i].ValueType;
                if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.CE))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.CWE))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.DT))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.FT))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.NM))
                {
                    value = listLabResultFiltered[i].ObservationValueNumeric.ToString();
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.SN))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.ST))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.TM))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.TS))
                {
                }
                else if (__dummyScrutVar1.equals(EhrLaboratories.HL70125.TX))
                {
                }
                          
                start("tr");
                _w.WriteElementString("td", listLabResultFiltered[i].ObservationIdentifierID);
                //LOINC Code
                if (labLoinc == null)
                {
                    _w.WriteElementString("td", listLabResultFiltered[i].ObservationIdentifierText);
                }
                else
                {
                    //Test
                    _w.WriteElementString("td", labLoinc.NameShort);
                } 
                //Test
                _w.WriteElementString("td", value + " " + listLabResultFiltered[i].UnitsID);
                //Result
                _w.WriteElementString("td", listLabResultFiltered[i].AbnormalFlags);
                //Abnormal Flag
                if (String.Compare(Regex.Match("input string", "^\\d{0,4}").Value.PadLeft(4, '0'), "1880") != -1)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    //Test
                    _w.WriteElementString("td", listLabResultFiltered[i].ObservationDateTime);
                } 
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listLabResultFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            EhrLabResult labR = new EhrLabResult();
            listLabResultFiltered.Add(labR);
        }
         
        for (int i = 0;i < listLabResultFiltered.Count;i++)
        {
            if (listLabResultFiltered[i].EhrLabNum == 0)
            {
                labPanel = new EhrLab();
            }
            else
            {
                labPanel = EhrLabs.GetOne(listLabResultFiltered[i].EhrLabNum);
            } 
            Loinc labLoinc = Loincs.GetByCode(listLabResultFiltered[i].ObservationIdentifierID);
            String value = "";
            ValueType __dummyScrutVar2 = listLabResultFiltered[i].ValueType;
            if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.CE))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.CWE))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.DT))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.FT))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.NM))
            {
                value = listLabResultFiltered[i].ObservationValueNumeric.ToString();
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.SN))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.ST))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.TM))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.TS))
            {
            }
            else if (__dummyScrutVar2.equals(EhrLaboratories.HL70125.TX))
            {
            }
                      
            start("entry","typeCode","DRIV");
            start("organizer","classCode","BATTERY","moodCode","EVN");
            startAndEnd("templateId","root","2.16.840.1.113883.10.20.22.4.1");
            _w.WriteComment("Result organizer template");
            guid();
            if (String.IsNullOrEmpty(labPanel.UsiID))
            {
                startAndEnd("code","nullFlavor","NA");
            }
            else
            {
                //Null allowed for this code.
                startAndEnd("code","code",labPanel.UsiID,"codeSystem",strCodeSystemLoinc,"displayName",labPanel.UsiText);
            } 
            //Code systems allowed: LOINC, or other "local codes".
            startAndEnd("statusCode","code","completed");
            //page 532 Allowed values: aborted, active, cancelled, completed, held, suspended.
            start("component");
            start("observation","classCode","OBS","moodCode","EVN");
            templateId("2.16.840.1.113883.10.20.22.4.2");
            _w.WriteComment("Result observation template");
            guid();
            if (String.IsNullOrEmpty(listLabResultFiltered[i].ObservationIdentifierID))
            {
                startAndEnd("code","nullFlavor","UNK");
            }
            else if (labLoinc == null)
            {
                StartAndEnd("code", "code", listLabResultFiltered[i].ObservationIdentifierID, "displayName", listLabResultFiltered[i].ObservationIdentifierText, "codeSystem", strCodeSystemLoinc, "codeSystemName", strCodeSystemNameLoinc);
            }
            else
            {
                StartAndEnd("code", "code", listLabResultFiltered[i].ObservationIdentifierID, "displayName", labLoinc.NameLongCommon, "codeSystem", strCodeSystemLoinc, "codeSystemName", strCodeSystemNameLoinc);
            }  
            startAndEnd("statusCode","code","completed");
            //Allowed values: aborted, active, cancelled, completed, held, or suspended.
            DateTime dateTimeEffective = DateTimeFromString(listLabResultFiltered[i].ObservationDateTime);
            if (dateTimeEffective.Year < 1880)
            {
                startAndEnd("effectiveTime","nullFlavor","UNK");
            }
            else
            {
                StartAndEnd("effectiveTime", "value", listLabResultFiltered[i].ObservationDateTime);
            } 
            start("value");
            _w.WriteAttributeString("xsi", "type", null, "PQ");
            if (value == null || StringSupport.equals(value, ""))
            {
                attribs("nullFlavor","UNK");
            }
            else
            {
                Attribs("value", value, "unit", listLabResultFiltered[i].UnitsID);
            } 
            end("value");
            startAndEnd("interpretationCode","code","N","codeSystem","2.16.840.1.113883.5.83");
            start("referenceRange");
            start("observationRange");
            if (String.IsNullOrEmpty(listLabResultFiltered[i].referenceRange))
            {
                startAndEnd("text","nullFlavor","UNK");
            }
            else
            {
                _w.WriteElementString("text", listLabResultFiltered[i].referenceRange);
            } 
            end("observationRange");
            end("referenceRange");
            end("observation");
            end("component");
            end("organizer");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCCD().  Exports smoking and pregnancy information.
    */
    private void generateCcdSectionSocialHistory(boolean hasSocialHistory) throws Exception {
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Social History\r\n" + 
        "=====================================================================================================");
        List<EhrMeasureEvent> listEhrMeasureEventsFiltered = new List<EhrMeasureEvent>();
        if (!hasSocialHistory)
        {
            listEhrMeasureEventsFiltered = new List<EhrMeasureEvent>();
        }
        else
        {
            listEhrMeasureEventsFiltered = _listEhrMeasureEventsFiltered;
        } 
        listEhrMeasureEventsFiltered.Sort(CompareEhrMeasureEvents);
        //The pattern for this section is special. We do not have any lists to use in this section.
        //The section will always be present with at least one entry, beacuse we know the patient smoking status (which includes the UnknownIfEver option).
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.17");
        //Social History section (page 311). Only one template. No entries are required.
        _w.WriteComment("Social History section template");
        startAndEnd("code","code","29762-2","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Social History");
        _w.WriteElementString("title", "Social History");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listEhrMeasureEventsFiltered.Count > 0 && hasSocialHistory)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Social History Element");
            _w.WriteElementString("th", "Description");
            _w.WriteElementString("th", "Effective Dates");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listEhrMeasureEventsFiltered.Count;i++)
            {
                start("tr");
                _w.WriteElementString("td", "Smoking");
                Snomed snomedSmoking = Snomeds.GetByCode(listEhrMeasureEventsFiltered[i].CodeValueResult);
                if (snomedSmoking == null)
                {
                    //Could be null if the code was imported from another EHR.
                    _w.WriteElementString("td", "");
                }
                else
                {
                    _w.WriteElementString("td", snomedSmoking.SnomedCode + " - " + snomedSmoking.Description);
                } 
                DateTime dateTimeLow = listEhrMeasureEventsFiltered[i].DateTEvent;
                DateTime dateTimeHigh = DateTime.Now;
                if (i < listEhrMeasureEventsFiltered.Count - 1)
                {
                    //There is another smoking event after this one (remember, they are sorted by date).
                    dateTimeHigh = listEhrMeasureEventsFiltered[i + 1].DateTEvent.AddDays(-1);
                    //The day before the next smoking event.
                    if (dateTimeHigh < dateTimeLow)
                    {
                        dateTimeHigh = dateTimeLow;
                    }
                     
                }
                 
                //Just in case the user entered two measures for the same date.
                if (dateTimeLow.Year < 1880)
                {
                    if (dateTimeHigh.Year < 1880)
                    {
                        _w.WriteElementString("td", "");
                    }
                    else
                    {
                        _w.WriteElementString("td", dateTimeHigh.ToString("yyyyMMdd"));
                    } 
                }
                else if (dateTimeHigh.Year < 1880)
                {
                    if (dateTimeLow.Year < 1880)
                    {
                        _w.WriteElementString("td", "");
                    }
                    else
                    {
                        _w.WriteElementString("td", dateTimeLow.ToString("yyyyMMdd"));
                    } 
                }
                else
                {
                    _w.WriteElementString("td", dateTimeLow.ToString("yyyyMMdd") + " to " + dateTimeHigh.ToString("yyyyMMdd"));
                }  
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listEhrMeasureEventsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            EhrMeasureEvent eME = new EhrMeasureEvent();
            listEhrMeasureEventsFiltered.Add(eME);
        }
         
        for (int i = 0;i < listEhrMeasureEventsFiltered.Count;i++)
        {
            //Pregnancy Observation Template could easily be added in the future, but for now it is skipped (Page 453)
            start("entry","typeCode","DRIV");
            start("observation","classCode","OBS","moodCode","EVN");
            templateId("2.16.840.1.113883.10.20.22.4.78");
            //Smoking Status Observation Section (Page 519).
            _w.WriteComment("Smoking Status Observation Template");
            startAndEnd("code","code","ASSERTION","codeSystem","2.16.840.1.113883.5.4");
            startAndEnd("statusCode","code","completed");
            //Allowed values: completed.
            //The effectiveTime/low element must be present. If the patient is an ex-smoker (8517006), the effectiveTime/high element must also be present. For simplicity, we always export both low and high dates.
            DateTime dateTimeLow = listEhrMeasureEventsFiltered[i].DateTEvent;
            DateTime dateTimeHigh = DateTime.Now;
            if (i < listEhrMeasureEventsFiltered.Count - 1)
            {
                //There is another smoking event after this one (remember, they are sorted by date).
                dateTimeHigh = listEhrMeasureEventsFiltered[i + 1].DateTEvent.AddDays(-1);
                //The day before the next smoking event.
                if (dateTimeHigh < dateTimeLow)
                {
                    dateTimeHigh = dateTimeLow;
                }
                 
            }
             
            //Just in case the user entered two measures for the same date.
            start("effectiveTime");
            if (dateTimeLow.Year < 1880)
            {
                startAndEnd("low","nullFlavor","UNK");
            }
            else
            {
                dateElement("low",dateTimeLow);
            } 
            if (dateTimeHigh.Year < 1880)
            {
                startAndEnd("high","nullFlavor","UNK");
            }
            else
            {
                dateElement("high",dateTimeHigh);
            } 
            end("effectiveTime");
            start("value");
            _w.WriteAttributeString("xsi", "type", null, "CD");
            Snomed snomedSmoking = Snomeds.GetByCode(listEhrMeasureEventsFiltered[i].CodeValueResult);
            if (snomedSmoking == null)
            {
                attribs("nullFlavor","UNK");
            }
            else
            {
                attribs("code",snomedSmoking.SnomedCode,"displayName",snomedSmoking.Description,"codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed);
            } 
            end("value");
            end("observation");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCcdSectionSocialHistory().  Sort function.  Currently sorts by date ascending.
    */
    private int compareEhrMeasureEvents(EhrMeasureEvent ehrMeasureEventL, EhrMeasureEvent ehrMeasureEventR) throws Exception {
        if (ehrMeasureEventL.DateTEvent < ehrMeasureEventR.DateTEvent)
        {
            return -1;
        }
        else if (ehrMeasureEventL.DateTEvent > ehrMeasureEventR.DateTEvent)
        {
            return 1;
        }
          
        return 0;
    }

    //equal
    /**
    * Helper for GenerateCCD().
    */
    private void generateCcdSectionVitalSigns(boolean hasVitalSign) throws Exception {
        //Currently just a skeleton
        _w.WriteComment("\r\n" + 
        "=====================================================================================================\r\n" + 
        "Vital Signs\r\n" + 
        "=====================================================================================================");
        List<Vitalsign> listVitalSignsFiltered = new List<Vitalsign>();
        if (!hasVitalSign)
        {
            listVitalSignsFiltered = new List<Vitalsign>();
        }
        else
        {
            listVitalSignsFiltered = _listVitalSignsFiltered;
        } 
        start("component");
        start("section");
        templateId("2.16.840.1.113883.10.20.22.2.4.1");
        //Vital signs section with coded entries required.
        _w.WriteComment("Vital Signs section template");
        startAndEnd("code","code","8716-3","codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Vital Signs");
        _w.WriteElementString("title", "Vital Signs");
        start("text");
        //The following text will be parsed as html with a style sheet to be human readable.
        if (listVitalSignsFiltered.Count > 0 && hasVitalSign)
        {
            start("table","width","100%","border","1");
            start("thead");
            start("tr");
            _w.WriteElementString("th", "Date");
            _w.WriteElementString("th", "Height");
            _w.WriteElementString("th", "Weight");
            _w.WriteElementString("th", "BMI");
            _w.WriteElementString("th", "Blood Pressure");
            end("tr");
            end("thead");
            start("tbody");
            for (int i = 0;i < listVitalSignsFiltered.Count;i++)
            {
                Vitalsign vitalsign = listVitalSignsFiltered[i];
                start("tr");
                if (vitalsign.DateTaken.Year < 1880)
                {
                    _w.WriteElementString("td", "");
                }
                else
                {
                    dateText("td",vitalsign.DateTaken);
                } 
                if (vitalsign.Height > 0)
                {
                    _w.WriteElementString("td", vitalsign.Height.ToString("f0") + " in");
                }
                else
                {
                    _w.WriteElementString("td", "");
                } 
                if (vitalsign.Weight > 0)
                {
                    _w.WriteElementString("td", vitalsign.Weight.ToString("f0") + " lbs");
                }
                else
                {
                    _w.WriteElementString("td", "");
                } 
                float bmi = Vitalsigns.calcBMI(vitalsign.Weight,vitalsign.Height);
                //will be 0 if either wight is 0 or height is 0.
                if (bmi > 0)
                {
                    _w.WriteElementString("td", bmi.ToString("f1") + " lbs/in");
                }
                else
                {
                    _w.WriteElementString("td", "");
                } 
                if (vitalsign.BpSystolic > 0 && vitalsign.BpDiastolic > 0)
                {
                    _w.WriteElementString("td", vitalsign.BpSystolic.ToString("f0") + "/" + vitalsign.BpDiastolic.ToString("f0"));
                }
                else
                {
                    _w.WriteElementString("td", "");
                } 
                end("tr");
            }
            end("tbody");
            end("table");
        }
        else
        {
            _w.WriteString("None");
        } 
        end("text");
        if (listVitalSignsFiltered.Count == 0)
        {
            //If there are no entries in the filtered list, then we want to add a dummy entry since at least one is required.
            Vitalsign viS = new Vitalsign();
            listVitalSignsFiltered.Add(viS);
        }
         
        for (int i = 0;i < listVitalSignsFiltered.Count;i++)
        {
            //Fill Vital Signs Info
            Vitalsign vitalsign = listVitalSignsFiltered[i];
            start("entry","typeCode","DRIV");
            start("organizer","classCode","CLUSTER","moodCode","EVN");
            _w.WriteComment("Vital Signs Organizer template");
            //Vital Signs Organizer
            templateId("2.16.840.1.113883.10.20.22.4.26");
            guid();
            startAndEnd("code","code","46680005","codeSystem",strCodeSystemSnomed,"codeSystemName",strCodeSystemNameSnomed,"displayName","Vital signs");
            startAndEnd("statusCode","code","completed");
            if (vitalsign.DateTaken.Year < 1880)
            {
                startAndEnd("effectiveTime","nullFlavor","UNK");
            }
            else
            {
                dateElement("effectiveTime",vitalsign.DateTaken);
            } 
            generateCcdVitalSign("8302-2",vitalsign.DateTaken,vitalsign.Height,"in");
            //Height
            generateCcdVitalSign("3141-9",vitalsign.DateTaken,vitalsign.Weight,"lbs");
            //Weight
            float bmi = Vitalsigns.calcBMI(vitalsign.Weight,vitalsign.Height);
            //will be 0 if either wight is 0 or height is 0.
            generateCcdVitalSign("39156-5",vitalsign.DateTaken,bmi,"lbs/in");
            //BMI
            GenerateCcdVitalSign("8480-6", vitalsign.DateTaken, vitalsign.BpSystolic, "mmHg");
            //Blood Pressure Systolic
            GenerateCcdVitalSign("8462-4", vitalsign.DateTaken, vitalsign.BpDiastolic, "mmHg");
            //Blood Pressure Diastolic
            end("organizer");
            end("entry");
        }
        end("section");
        end("component");
    }

    /**
    * Helper for GenerateCcdSectionVitalSigns(). Writes on observation.
    * Allowed vital sign observation template LOINC codes (strLoincObservationCode):
    * 9279-1		Respiratory Rate
    * 8867-4		Heart Rate
    * 2710-2		O2 % BldC Oximetry,
    * 8480-6		BP Systolic
    * 8462-4		BP Diastolic
    * 8310-5		Body Temperature,
    * 8302-2		Height
    * 8306-3		Height (Lying)
    * 8287-5		Head Circumference,
    * 3141-9		Weight Measured
    * 39156-5	BMI (Body Mass Index)
    * 3140-1 BSA (Body Surface Area)
    */
    private void generateCcdVitalSign(String strLoincObservationCode, DateTime dateTimeObservation, float observationValue, String observationUnits) throws Exception {
        start("component");
        start("observation","classCode","OBS","moodCode","EVN");
        _w.WriteComment("Vital Sign Observation template");
        //Vital Sign Observation Section
        templateId("2.16.840.1.113883.10.20.22.4.27");
        guid();
        startAndEnd("code","code",strLoincObservationCode,"codeSystem",strCodeSystemLoinc,"codeSystemName",strCodeSystemNameLoinc,"displayName","Height");
        startAndEnd("statusCode","code","completed");
        //Allowed values: completed.
        if (dateTimeObservation.Year < 1880)
        {
            startAndEnd("effectiveTime","nullFlavor","UNK");
        }
        else
        {
            dateElement("effectiveTime",dateTimeObservation);
        } 
        start("value");
        _w.WriteAttributeString("xsi", "type", null, "PQ");
        if (observationValue == 0)
        {
            attribs("nullFlavor","UNK");
        }
        else
        {
            Attribs("value", observationValue.ToString("f0"), "unit", observationUnits);
        } 
        end("value");
        end("observation");
        end("component");
    }

    /**
    * Helper for GenerateCCD(). Builds an "id" element and writes a random 32 character alpha-numeric string into the "root" attribute.
    */
    private void id() throws Exception {
        String id = CodeBase.MiscUtils.createRandomAlphaNumericString(32);
        while (_hashCcdIds.Contains(id))
        {
            id = CodeBase.MiscUtils.createRandomAlphaNumericString(32);
        }
        _hashCcdIds.Add(id);
        startAndEnd("id","root",id);
    }

    /**
    * Helper for GenerateCCD(). Builds an "id" element and writes a 36 character GUID string into the "root" attribute.
    * An example of how the uid might look: "20cf14fb-B65c-4c8c-A54d-b0cca834C18c"
    */
    private void guid() throws Exception {
        Guid uuid = System.Guid.NewGuid();
        while (_hashCcdGuids.Contains(uuid.ToString()))
        {
            uuid = System.Guid.NewGuid();
        }
        _hashCcdGuids.Add(uuid.ToString());
        StartAndEnd("id", "root", uuid.ToString());
    }

    /**
    * Helper for GenerateCCD().
    */
    private void templateId(String rootNumber) throws Exception {
        _w.WriteStartElement("templateId");
        _w.WriteAttributeString("root", rootNumber);
        _w.WriteEndElement();
    }

    /**
    * Helper for GenerateCCD().
    */
    private void templateId(String rootNumber, String authorityName) throws Exception {
        _w.WriteStartElement("templateId");
        _w.WriteAttributeString("root", rootNumber);
        _w.WriteAttributeString("assigningAuthorityName", authorityName);
        _w.WriteEndElement();
    }

    /**
    * Helper for GenerateCCD().  Performs a WriteStartElement, followed by any attributes.  Attributes must be in pairs: name, value.
    */
    private void start(String elementName, String... attributes) throws Exception {
        _w.WriteStartElement(elementName);
        for (int i = 0;i < attributes.Length;i += 2)
        {
            _w.WriteAttributeString(attributes[i], attributes[i + 1]);
        }
    }

    /**
    * Helper for GenerateCCD().  Performs a WriteEndElement.  The specified elementName is for readability only.
    */
    private void end(String elementName) throws Exception {
        _w.WriteEndElement();
    }

    /**
    * Helper for GenerateCCD().  Performs a WriteStartElement, followed by any attributes, followed by a WriteEndElement.  Attributes must be in pairs: name, value.
    */
    private void startAndEnd(String elementName, String... attributes) throws Exception {
        _w.WriteStartElement(elementName);
        for (int i = 0;i < attributes.Length;i += 2)
        {
            _w.WriteAttributeString(attributes[i], attributes[i + 1]);
        }
        _w.WriteEndElement();
    }

    /**
    * Helper for GenerateCCD().  Performs a WriteAttributeString for each attribute.  Attributes must be in pairs: name, value.
    */
    private void attribs(String... attributes) throws Exception {
        for (int i = 0;i < attributes.Length;i += 2)
        {
            _w.WriteAttributeString(attributes[i], attributes[i + 1]);
        }
    }

    /**
    * Use for HTML tables. Writes the element strElement name and writes the dateTime string in the required date format.  Will not write if year is before 1880.
    */
    private void dateText(String strElementName, DateTime dateTime) throws Exception {
        start(strElementName);
        if (dateTime.Year > 1880)
        {
            _w.WriteString(dateTime.ToString("yyyyMMdd"));
        }
         
        end(strElementName);
    }

    /**
    * Use for XML. Writes the element strElement name and writes the dateTime in the required date format into the value attribute.
    * Will write nullFlavor="UNK" instead of value if year is before 1880.
    */
    private void dateElement(String strElementName, DateTime dateTime) throws Exception {
        start(strElementName);
        if (dateTime.Year < 1880)
        {
            attribs("nullFlavor","UNK");
        }
        else
        {
            Attribs("value", dateTime.ToString("yyyyMMdd"));
        } 
        end(strElementName);
    }

    /**
    * Writes the element strElement name and writes the dateTime in the required date format into the value attribute.
    * Will write nullFlavor="UNK" instead of value if year is before 1880.
    */
    private void timeElement(String strElementName, DateTime dateTime) throws Exception {
        start(strElementName);
        if (dateTime.Year < 1880)
        {
            attribs("nullFlavor","UNK");
        }
        else
        {
            Attribs("value", dateTime.ToString("yyyyMMddHHmmsszzz").Replace(":", ""));
        } 
        end(strElementName);
    }

    private void addressUnitedStates(String strAddress1, String strAddress2, String strCity, String strState) throws Exception {
        start("addr","use","HP");
        _w.WriteElementString("streetAddressLine", strAddress1);
        if (!StringSupport.equals(strAddress2, ""))
        {
            _w.WriteElementString("streetAddressLine", strAddress2);
        }
         
        _w.WriteElementString("city", strCity);
        _w.WriteElementString("state", strState);
        _w.WriteElementString("country", "United States");
        end("addr");
    }

    /**
    * Does validation on the filtered lists. NEEDS TO BE ENHANCED.
    */
    private static String validateAll(Patient pat) throws Exception {
        String err = "";
        err = err + validateSettings();
        err = err + validatePatient(pat);
        err = err + validateAllergy(pat);
        err = err + validateEncounter(pat);
        err = err + validateFunctionalStatus(pat);
        err = err + validateImmunization(pat);
        err = err + validateLabResults(pat);
        err = err + validateMedication(pat);
        err = err + validatePlanOfCare(pat);
        err = err + validateProblem(pat);
        err = err + validateProcedure(pat);
        err = err + validateSocialHistory(pat);
        err = err + validateVitalsSign(pat);
        return err;
    }

    /**
    * Checks data values for preferences and provider information to ensure required data is available for CCD creation.
    * Returns empty string if no errors, otherwise returns a string containing error messages.
    */
    public static String validateSettings() throws Exception {
        String strErrors = "";
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeTitle).Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing practice title.";
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticePhone).Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing practice phone.";
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeAddress).Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing practice address line 1.";
        }
         
        if (StringSupport.equals(PrefC.getString(PrefName.PracticeCity).Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing practice city.";
        }
         
        if (PrefC.getString(PrefName.PracticeST).Trim().Length != 2)
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Invalid practice state.  Must be two letters.";
        }
         
        Provider provDefault = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        if (StringSupport.equals(provDefault.FName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing provider " + provDefault.Abbr + " first name.";
        }
         
        if (StringSupport.equals(provDefault.LName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing provider " + provDefault.Abbr + " last name.";
        }
         
        if (StringSupport.equals(provDefault.NationalProvID.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing provider " + provDefault.Abbr + " NPI.";
        }
         
        if (Snomeds.getCodeCount() == 0)
        {
            //TODO: We need to replace this check with a more extensive check which validates the SNOMED codes that will actually go out on the CCD to ensure they are in our database.
            //One way a SNOMED code could get into the patinet record without being in the snomed table, would be via an imported CCD. We might get around this issue by simply
            //exporting the code without the description, because the descriptions are usually optional.
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing SNOMED codes.  Go to Setup | EHR | Code System Importer to download.";
        }
         
        if (Cvxs.getCodeCount() == 0)
        {
            //TODO: We need to replace this check with a more extensive check which validates the CVX codes that will actually go out on the CCD to ensure they are in our database.
            //One way a CVX code could get into the patinet record without being in the snomed table, would be via an imported CCD. We might get around this issue by simply
            //exporting the code without the description, because the descriptions are usually optional.
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing CVX codes.  Go to Setup | EHR | Code System Importer to download.";
        }
         
        return strErrors;
    }

    /**
    * Checks data values for pat as well as primary provider information to ensure required data is available for CCD creation.
    * Returns empty string if no errors, otherwise returns a string containing error messages.
    */
    public static String validatePatient(Patient pat) throws Exception {
        String strErrors = "";
        if (StringSupport.equals(pat.FName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient first name.";
        }
         
        if (StringSupport.equals(pat.LName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient last name.";
        }
         
        if (StringSupport.equals(pat.Address.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient address line 1.";
        }
         
        if (StringSupport.equals(pat.City.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient city.";
        }
         
        if (pat.State.Trim().Length != 2)
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Invalid patient state.  Must be two letters.";
        }
         
        if (pat.Birthdate.Year < 1880)
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient birth date.";
        }
         
        if (StringSupport.equals(pat.HmPhone.Trim(), "") && StringSupport.equals(pat.WirelessPhone.Trim(), "") && StringSupport.equals(pat.WkPhone.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing patient phone. Must have home, wireless, or work phone.";
        }
         
        if (!PrefC.getBool(PrefName.EasyNoClinics) && pat.ClinicNum != 0)
        {
            Clinic clinic = Clinics.getClinic(pat.ClinicNum);
            if (StringSupport.equals(clinic.Description.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing clinic description.";
            }
             
            if (StringSupport.equals(clinic.Phone.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing clinic '" + clinic.Description + "' phone.";
            }
             
            if (StringSupport.equals(clinic.Address.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing clinic '" + clinic.Description + "' address line 1.";
            }
             
            if (StringSupport.equals(clinic.City.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing clinic '" + clinic.Description + "' city.";
            }
             
            if (clinic.State.Trim().Length != 2)
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Invalid clinic '" + clinic.Description + "' state.  Must be two letters.";
            }
             
        }
         
        Provider provPractice = Providers.getProv(PrefC.getLong(PrefName.PracticeDefaultProv));
        if (StringSupport.equals(provPractice.FName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing provider " + provPractice.Abbr + " first name.";
        }
         
        if (StringSupport.equals(provPractice.LName.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing provider " + provPractice.Abbr + " last name.";
        }
         
        if (StringSupport.equals(provPractice.NationalProvID.Trim(), ""))
        {
            if (!StringSupport.equals(strErrors, ""))
            {
                strErrors += "\r\n";
            }
             
            strErrors += "Missing provider " + provPractice.Abbr + " NPI.";
        }
         
        if (pat.PriProv > 0 && pat.PriProv != PrefC.getLong(PrefName.PracticeDefaultProv))
        {
            Provider provPri = Providers.getProv(pat.PriProv);
            if (StringSupport.equals(provPri.FName.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing provider " + provPri.Abbr + " first name.";
            }
             
            if (StringSupport.equals(provPri.LName.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing provider " + provPri.Abbr + " last name.";
            }
             
            if (StringSupport.equals(provPri.NationalProvID.Trim(), ""))
            {
                if (!StringSupport.equals(strErrors, ""))
                {
                    strErrors += "\r\n";
                }
                 
                strErrors += "Missing provider " + provPri.Abbr + " NPI.";
            }
             
        }
         
        return strErrors;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateAllergy(Patient pat) throws Exception {
        filterAllergy(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of allergies. Also runs validation.
    */
    private static void filterAllergy(Patient patCur) throws Exception {
        //TODO: Add validation for UNII codes once the table has been implemented.
        AllergyDef allergyDef;
        List<Allergy> listAllergiesAll = Allergies.refresh(patCur.PatNum);
        List<Allergy> listAllergiesFiltered = new List<Allergy>();
        for (int i = 0;i < listAllergiesAll.Count;i++)
        {
            allergyDef = AllergyDefs.GetOne(listAllergiesAll[i].AllergyDefNum);
            boolean isMedAllergy = false;
            if (allergyDef.MedicationNum != 0)
            {
                Medication med = Medications.getMedication(allergyDef.MedicationNum);
                if (med.RxCui != 0)
                {
                    isMedAllergy = true;
                }
                 
            }
             
            if (allergyDef.SnomedType != SnomedAllergy.AdverseReactionsToDrug && allergyDef.SnomedType != SnomedAllergy.DrugAllergy && allergyDef.SnomedType != SnomedAllergy.DrugIntolerance)
            {
                isMedAllergy = false;
            }
             
            //bool isSnomedAllergy=false;
            //if(allergyDef.SnomedAllergyTo!="") {
            //	isSnomedAllergy=true;
            //}
            if (!isMedAllergy)
            {
                // && !isSnomedAllergy) {
                if (StringSupport.equals(allergyDef.UniiCode, ""))
                {
                    continue;
                }
                 
            }
             
            //TODO: We need to add support for Ndf-RT
            listAllergiesFiltered.Add(listAllergiesAll[i]);
        }
        _listAllergiesFiltered = listAllergiesFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateEncounter(Patient pat) throws Exception {
        filterEncounter(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of encounters. Also runs validation.
    */
    private static void filterEncounter(Patient patCur) throws Exception {
        List<Encounter> listEncountersAll = Encounters.refresh(patCur.PatNum);
        List<Encounter> listEncountersFiltered = new List<Encounter>();
        for (int i = 0;i < listEncountersAll.Count;i++)
        {
            if (!StringSupport.equals(listEncountersAll[i].CodeSystem, "SNOMEDCT"))
            {
                continue;
            }
             
            //The format only allows SNOMED codes and ICD10 codes. We do not have a way to enter ICD10 codes, and SNOMED appears to be the preferred code system anyway.
            listEncountersFiltered.Add(listEncountersAll[i]);
        }
        _listEncountersFiltered = listEncountersFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateFunctionalStatus(Patient pat) throws Exception {
        filterFunctionalStatus(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of encounters. Also runs validation.
    */
    private static void filterFunctionalStatus(Patient patCur) throws Exception {
        String snomedProblemType = "55607006";
        List<Disease> listProblemsAll = Diseases.refresh(patCur.PatNum);
        List<Disease> listProblemsFiltered = new List<Disease>();
        for (int i = 0;i < listProblemsAll.Count;i++)
        {
            if (!StringSupport.equals(listProblemsAll[i].SnomedProblemType, "") && !StringSupport.equals(listProblemsAll[i].SnomedProblemType, snomedProblemType))
            {
                continue;
            }
             
            //Not a "problem".
            if (listProblemsAll[i].FunctionStatus == FunctionalStatus.Problem)
            {
                continue;
            }
             
            //Is a standard problem, not a cognitive or functional problem.
            listProblemsFiltered.Add(listProblemsAll[i]);
        }
        _listProblemsFuncFiltered = listProblemsFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateImmunization(Patient pat) throws Exception {
        filterImmunization(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of vaccines. Also runs validation.
    */
    private static void filterImmunization(Patient patCur) throws Exception {
        List<VaccinePat> listVaccinePatsAll = VaccinePats.refresh(patCur.PatNum);
        List<VaccinePat> listVaccinePatsFiltered = new List<VaccinePat>();
        for (int i = 0;i < listVaccinePatsAll.Count;i++)
        {
            //No Filters for this
            listVaccinePatsFiltered.Add(listVaccinePatsAll[i]);
        }
        _listVaccinePatsFiltered = listVaccinePatsFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateMedication(Patient pat) throws Exception {
        filterMedication(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of medications. Also runs validation.
    */
    private static void filterMedication(Patient patCur) throws Exception {
        List<MedicationPat> listMedPatsAll = MedicationPats.refresh(patCur.PatNum,true);
        List<MedicationPat> listMedPatsFiltered = new List<MedicationPat>();
        for (int i = 0;i < listMedPatsAll.Count;i++)
        {
            //No filters currently
            listMedPatsFiltered.Add(listMedPatsAll[i]);
        }
        _listMedPatsFiltered = listMedPatsFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validatePlanOfCare(Patient pat) throws Exception {
        filterPlanOfCare(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of care plans. Also runs validation.
    */
    private static void filterPlanOfCare(Patient patCur) throws Exception {
        List<EhrCarePlan> listEhrCarePlansAll = EhrCarePlans.refresh(patCur.PatNum);
        List<EhrCarePlan> listEhrCarePlansFiltered = new List<EhrCarePlan>();
        for (int i = 0;i < listEhrCarePlansAll.Count;i++)
        {
            //No filters yet. This loop is here to match our pattern. If we need to add filters later, the change will be safer and more obvious.
            listEhrCarePlansFiltered.Add(listEhrCarePlansAll[i]);
        }
        _listEhrCarePlansFiltered = listEhrCarePlansFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateProblem(Patient pat) throws Exception {
        filterProblem(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of problems. Also runs validation.
    */
    private static void filterProblem(Patient patCur) throws Exception {
        String snomedProblemType = "55607006";
        List<Disease> listProblemsAll = Diseases.refresh(patCur.PatNum);
        List<Disease> listProblemsFiltered = new List<Disease>();
        for (int i = 0;i < listProblemsAll.Count;i++)
        {
            if (!StringSupport.equals(listProblemsAll[i].SnomedProblemType, "") && !StringSupport.equals(listProblemsAll[i].SnomedProblemType, snomedProblemType))
            {
                continue;
            }
             
            //Not a "problem".
            if (listProblemsAll[i].FunctionStatus != FunctionalStatus.Problem)
            {
                continue;
            }
             
            //Not a "problem".
            listProblemsFiltered.Add(listProblemsAll[i]);
        }
        _listProblemsFiltered = listProblemsFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateProcedure(Patient pat) throws Exception {
        filterProcedure(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of procedures. Also runs validation.
    */
    private static void filterProcedure(Patient patCur) throws Exception {
        List<Procedure> listProcsAll = Procedures.refresh(patCur.PatNum);
        List<Procedure> listProcsFiltered = new List<Procedure>();
        for (int i = 0;i < listProcsAll.Count;i++)
        {
            ProcedureCode procCode = ProcedureCodes.GetProcCode(listProcsAll[i].CodeNum);
            if (listProcsAll[i].ProcStatus == OpenDentBusiness.ProcStat.D)
            {
                continue;
            }
             
            //Ignore deleted procedures.
            if (listProcsAll[i].ProcStatus == OpenDentBusiness.ProcStat.TP)
            {
                continue;
            }
             
            //Ignore treatment planned procedures.  These procedures should be sent out in the Care Plan section in the future.  We are not required to send treatment planned items.
            if (listProcsAll[i].ProcStatus == OpenDentBusiness.ProcStat.R)
            {
                continue;
            }
             
            //Ignore procedures referred out.  It is the responsibility of the treating dentist to record work they have performed.
            listProcsFiltered.Add(listProcsAll[i]);
        }
        _listProcsFiltered = listProcsFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateLabResults(Patient pat) throws Exception {
        filterLabResults(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of lab results. Also runs validation.
    */
    private static void filterLabResults(Patient patCur) throws Exception {
        List<EhrLabResult> listLabResultAll = EhrLabResults.getAllForPatient(patCur.PatNum);
        List<EhrLabResult> listLabResultFiltered = new List<EhrLabResult>();
        for (int i = 0;i < listLabResultAll.Count;i++)
        {
            if (StringSupport.equals(listLabResultAll[i].ObservationIdentifierID, ""))
            {
                continue;
            }
             
            //Blank codes not allowed in format.
            listLabResultFiltered.Add(listLabResultAll[i]);
        }
        _listLabResultFiltered = listLabResultFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateSocialHistory(Patient pat) throws Exception {
        filterSocialHistory(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of procedures. Also runs validation.
    */
    private static void filterSocialHistory(Patient patCur) throws Exception {
        List<EhrMeasureEvent> listEhrMeasureEventsAll = EhrMeasureEvents.refresh(patCur.PatNum);
        List<EhrMeasureEvent> listEhrMeasureEventsFiltered = new List<EhrMeasureEvent>();
        for (int i = 0;i < listEhrMeasureEventsAll.Count;i++)
        {
            if (listEhrMeasureEventsAll[i].EventType != EhrMeasureEventType.TobaccoUseAssessed)
            {
                continue;
            }
             
            if (!StringSupport.equals(listEhrMeasureEventsAll[i].CodeSystemResult, "SNOMEDCT"))
            {
                continue;
            }
             
            //The user is currently only allowed to pick SNOMED smoking statuses. This is here in case we add more code system in the future, to prevent the format from breaking until we enhance.
            listEhrMeasureEventsFiltered.Add(listEhrMeasureEventsAll[i]);
        }
        _listEhrMeasureEventsFiltered = listEhrMeasureEventsFiltered;
    }

    /**
    * Does validation on the filtered list. NEEDS TO BE ENHANCED.
    */
    private static String validateVitalsSign(Patient pat) throws Exception {
        filterVitalSign(pat);
        return "";
    }

    //TODO: Add validation
    /**
    * Filters list of procedures. Also runs validation.
    */
    private static void filterVitalSign(Patient patCur) throws Exception {
        List<Vitalsign> listVitalSignsAll = Vitalsigns.refresh(patCur.PatNum);
        List<Vitalsign> listVitalSignsFiltered = new List<Vitalsign>();
        for (int i = 0;i < listVitalSignsAll.Count;i++)
        {
            Vitalsign vitalsign = listVitalSignsAll[i];
            //Each of the vital sign values are optional, so we must skip filter out empty vital signs.
            float bmi = Vitalsigns.calcBMI(vitalsign.Weight,vitalsign.Height);
            //will be 0 if either wight is 0 or height is 0.
            if (vitalsign.Height == 0 && vitalsign.Weight == 0 && bmi == 0 && (vitalsign.BpSystolic == 0 || vitalsign.BpDiastolic == 0))
            {
                continue;
            }
             
            //Nothing to report.
            listVitalSignsFiltered.Add(listVitalSignsAll[i]);
        }
        _listVitalSignsFiltered = listVitalSignsFiltered;
    }

    /**
    * Returns the PatNum for the unique patient who matches the patient name and birthdate within the CCD document xmlDocCcd.
    * Returns 0 if there are no patient matches.  Returns the first match if there are multiple matches (unlikely).
    */
    public static long getCCDpat(XmlDocument xmlDocCcd) throws Exception {
        XmlNodeList xmlNodeList = xmlDocCcd.GetElementsByTagName("patient");
        //According to the CCD documentation, there should be one patient node, or no patient node.
        if (xmlNodeList.Count == 0)
        {
            return 0;
        }
         
        //No patient node, therefore no patient to match.
        XmlNode xmlNodePat = xmlNodeList[0];
        String fName = "";
        String lName = "";
        DateTime birthDate = DateTime.MinValue;
        for (int i = 0;i < xmlNodePat.ChildNodes.Count;i++)
        {
            if (StringSupport.equals(xmlNodePat.ChildNodes[i].Name.Trim().ToLower(), "name"))
            {
                XmlNode xmlNodeName = xmlNodePat.ChildNodes[i];
                for (int j = 0;j < xmlNodeName.ChildNodes.Count;j++)
                {
                    if (StringSupport.equals(xmlNodeName.ChildNodes[j].Name.Trim().ToLower(), "given"))
                    {
                        if (StringSupport.equals(fName, ""))
                        {
                            //The first and middle names are both referred to as "given" name.  The first given name is the patient's first name.
                            fName = xmlNodeName.ChildNodes[j].InnerText.Trim();
                        }
                         
                    }
                    else if (StringSupport.equals(xmlNodeName.ChildNodes[j].Name.Trim().ToLower(), "family"))
                    {
                        lName = xmlNodeName.ChildNodes[j].InnerText.Trim();
                    }
                      
                }
            }
            else if (StringSupport.equals(xmlNodePat.ChildNodes[i].Name.Trim().ToLower(), "birthtime"))
            {
                XmlNode xmlNodeBirthtime = xmlNodePat.ChildNodes[i];
                for (int j = 0;j < xmlNodeBirthtime.Attributes.Count;j++)
                {
                    if (!StringSupport.equals(xmlNodeBirthtime.Attributes[j].Name.Trim().ToLower(), "value"))
                    {
                        continue;
                    }
                     
                    String strBirthTimeVal = xmlNodeBirthtime.Attributes[j].Value;
                    int birthYear = int.Parse(strBirthTimeVal.Substring(0, 4));
                    //Year will always be in the first 4 digits of the date, for all flavors of the HL7 TS type.
                    int birthMonth = 1;
                    if (strBirthTimeVal.Length >= 6)
                    {
                        birthMonth = int.Parse(strBirthTimeVal.Substring(4, 2));
                    }
                     
                    //If specified, month will always be in the 5th-6th digits of the date, for all flavors of the HL7 TS type.
                    int birthDay = 1;
                    if (strBirthTimeVal.Length >= 8)
                    {
                        birthDay = int.Parse(strBirthTimeVal.Substring(6, 2));
                    }
                     
                    //If specified, day will always be in the 7th-8th digits of the date, for all flavors of the HL7 TS type.
                    birthDate = new DateTime(birthYear, birthMonth, birthDay);
                }
            }
              
        }
        //A match cannot be made unless the CCD message includes first and last name as well as a specified birthdate,
        //because we do not want to automatically attach Direct messages to patients unless we are certain that the match makes sense.
        //The user can always manually attach the incoming Direct message to a patient if the automatic matching fails, so it is good that the automatic matching is strict.
        //Automatic matching is not required for EHR, but it is "highly recommended when possible" according to Drummond.
        if (StringSupport.equals(lName, "") || StringSupport.equals(fName, "") || birthDate.Year < 1880)
        {
            return 0;
        }
         
        return Patients.getPatNumByNameAndBirthday(lName,fName,birthDate);
    }

    /**
    * Recursive. Returns all nodes matching the specified tag name (case insensitive) which also have all of the specified attributes (case sensitive names).
    * Attributes must be listed in pairs by attribute name then attribute value.
    */
    private static List<XmlNode> getNodesByTagNameAndAttributes(XmlNode xmlNode, String strTagName, String... arrayAttributes) throws Exception {
        //Test the current node for tag name and attributes.
        List<XmlNode> retVal = new List<XmlNode>();
        if (xmlNode.Name.Trim().ToLower() == strTagName.Trim().ToLower())
        {
            //Tag name match.
            boolean isAttributeMatch = true;
            for (int i = 0;i < arrayAttributes.Length;i += 2)
            {
                String strAttributeName = arrayAttributes[i];
                String strAttributeValue = arrayAttributes[i + 1];
                if (xmlNode.Attributes[strAttributeName].Value.Trim().ToLower() != strAttributeValue.Trim().ToLower())
                {
                    isAttributeMatch = false;
                    break;
                }
                 
            }
            if (isAttributeMatch)
            {
                retVal.Add(xmlNode);
            }
             
        }
         
        for (int i = 0;i < xmlNode.ChildNodes.Count;i++)
        {
            //Test child nodes.
            retVal.AddRange(GetNodesByTagNameAndAttributes(xmlNode.ChildNodes[i], strTagName, arrayAttributes));
        }
        return retVal;
    }

    private static List<XmlNode> getParentNodes(List<XmlNode> listXmlNodes) throws Exception {
        List<XmlNode> retVal = new List<XmlNode>();
        for (int i = 0;i < listXmlNodes.Count;i++)
        {
            retVal.Add(listXmlNodes[i].ParentNode);
        }
        return retVal;
    }

    /**
    * Calls GetNodesByTagNameAndAttributes() for each item in listXmlNode.
    */
    private static List<XmlNode> getNodesByTagNameAndAttributesFromList(List<XmlNode> listXmlNode, String strTagName, String... arrayAttributes) throws Exception {
        List<XmlNode> retVal = new List<XmlNode>();
        for (int i = 0;i < listXmlNode.Count;i++)
        {
            retVal.AddRange(GetNodesByTagNameAndAttributes(listXmlNode[i], strTagName, arrayAttributes));
        }
        return retVal;
    }

    private static DateTime dateTimeFromString(String strDateTime) throws Exception {
        if (strDateTime == null)
        {
            return DateTime.MinValue;
        }
         
        String strDateTimeFormat = "";
        if (strDateTime.Length == 19)
        {
            strDateTimeFormat = "yyyyMMddHHmmsszzz";
        }
        else if (strDateTime.Length == 17)
        {
            strDateTimeFormat = "yyyyMMddHHmmzzz";
        }
        else if (strDateTime.Length == 8)
        {
            strDateTimeFormat = "yyyyMMdd";
        }
        else if (strDateTime.Length == 6)
        {
            strDateTimeFormat = "yyyyMM";
        }
        else if (strDateTime.Length == 4)
        {
            strDateTimeFormat = "yyyy";
        }
             
        try
        {
            return DateTime.ParseExact(strDateTime, strDateTimeFormat, CultureInfo.CurrentCulture.DateTimeFormat);
        }
        catch (Exception __dummyCatchVar0)
        {
        }

        return DateTime.MinValue;
    }

    /**
    * Gets the start date (aka low node) from the effectiveTime node passed in.  Returns the date time value set in the low node if present.  If low node does not exist, it returns the value within the effectiveTime node.  Returns MinValue if low attribute is "nullflavor" or if parsing fails.
    */
    private static DateTime getEffectiveTimeLow(XmlNode xmlNodeEffectiveTime) throws Exception {
        DateTime strEffectiveTimeValue = DateTime.MinValue;
        List<XmlNode> listLowVals = getNodesByTagNameAndAttributes(xmlNodeEffectiveTime,"low");
        if (listLowVals.Count > 0 && listLowVals[0].Attributes["nullFlavor"] != null)
        {
            return strEffectiveTimeValue;
        }
         
        if (listLowVals.Count > 0 && listLowVals[0].Attributes["value"] != null)
        {
            strEffectiveTimeValue = DateTimeFromString(listLowVals[0].Attributes["value"].Value);
        }
        else if (xmlNodeEffectiveTime.Attributes["value"] != null)
        {
            strEffectiveTimeValue = DateTimeFromString(xmlNodeEffectiveTime.Attributes["value"].Value);
        }
          
        return strEffectiveTimeValue;
    }

    private static DateTime getEffectiveTimeHigh(XmlNode xmlNodeEffectiveTime) throws Exception {
        DateTime strEffectiveTimeValue = DateTime.MinValue;
        List<XmlNode> listLowVals = getNodesByTagNameAndAttributes(xmlNodeEffectiveTime,"high");
        if (listLowVals.Count > 0 && listLowVals[0].Attributes["nullFlavor"] != null)
        {
            return strEffectiveTimeValue;
        }
         
        if (listLowVals.Count > 0 && listLowVals[0].Attributes["value"] != null)
        {
            strEffectiveTimeValue = DateTimeFromString(listLowVals[0].Attributes["value"].Value);
        }
        else
        {
        } 
        return strEffectiveTimeValue;
    }

    //We do not take the string from the xmlNodeEffectiveTime value attribute, because we need to be careful importing high/stop dates.
    //The examples suggest that th xmlNodeEffectiveTime value attribute will contain the minimum datetime.
    /**
    * Fills listMedicationPats and listMedications using the information found in the CCD document xmlDocCcd.  Does NOT insert any records into the db.
    */
    public static void getListMedicationPats(XmlDocument xmlDocCcd, List<MedicationPat> listMedicationPats) throws Exception {
        //The length of listMedicationPats and listMedications will be the same. The information in listMedications might have duplicates.
        //Neither list of objects will be inserted into the db, so there will be no primary or foreign keys.
        //List<XmlNode> listMedicationDispenseTemplates=GetNodesByTagNameAndAttributes(xmlDocCcd,"templateId","root","2.16.840.1.113883.10.20.22.4.18");//Medication Dispense template.
        List<XmlNode> listMedicationDispenseTemplates = GetNodesByTagNameAndAttributes(xmlDocCcd, "templateId", "root", "2.16.840.1.113883.10.20.22.4.16");
        //Medication Activity template.
        List<XmlNode> listSupply = GetParentNodes(listMedicationDispenseTemplates);
        for (int i = 0;i < listSupply.Count;i++)
        {
            //POCD_HD00040.xls line 485
            //We have to start fairly high in the tree so that we can get the effective time if it is available.
            List<XmlNode> xmlNodeEffectiveTimes = GetNodesByTagNameAndAttributes(listSupply[i], "effectiveTime");
            //POCD_HD00040.xls line 492. Not required.
            DateTime dateTimeEffectiveLow = DateTime.MinValue;
            DateTime dateTimeEffectiveHigh = DateTime.MinValue;
            if (xmlNodeEffectiveTimes.Count > 0)
            {
                XmlNode xmlNodeEffectiveTime = xmlNodeEffectiveTimes[0];
                dateTimeEffectiveLow = getEffectiveTimeLow(xmlNodeEffectiveTime);
                dateTimeEffectiveHigh = getEffectiveTimeHigh(xmlNodeEffectiveTime);
            }
             
            List<XmlNode> listMedicationActivityTemplates = GetNodesByTagNameAndAttributes(listSupply[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.23");
            //Medication Activity template.
            List<XmlNode> listProducts = GetParentNodes(listMedicationActivityTemplates);
            //List of manufaturedProduct and/or manufacturedLabeledDrug. POCD_HD00040.xls line 472.
            List<XmlNode> listCodes = GetNodesByTagNameAndAttributesFromList(listProducts, "code");
            for (int j = 0;j < listCodes.Count;j++)
            {
                XmlNode xmlNodeCode = listCodes[j];
                String strCode = xmlNodeCode.Attributes["code"].Value;
                String strMedDescript = xmlNodeCode.Attributes["displayName"].Value;
                if (!StringSupport.equals(xmlNodeCode.Attributes["codeSystem"].Value, strCodeSystemRxNorm))
                {
                    continue;
                }
                 
                //We can only import RxNorms, because we have nowhere to pull in any other codes at this time (for example, SNOMED).
                MedicationPat medicationPat = new MedicationPat();
                medicationPat.setIsNew(true);
                //Needed for reconcile window to know this record is not in the db yet.
                medicationPat.RxCui = PIn.long(strCode);
                medicationPat.MedDescript = strMedDescript;
                medicationPat.DateStart = dateTimeEffectiveLow;
                medicationPat.DateStop = dateTimeEffectiveHigh;
                listMedicationPats.Add(medicationPat);
            }
        }
        List<MedicationPat> listMedicationPatsNoDupes = new List<MedicationPat>();
        boolean isDupe = new boolean();
        for (int index = 0;index < listMedicationPats.Count;index++)
        {
            isDupe = false;
            for (int index2 = 0;index2 < listMedicationPatsNoDupes.Count;index2++)
            {
                if (listMedicationPatsNoDupes[index2].RxCui == listMedicationPats[index].RxCui)
                {
                    isDupe = true;
                    break;
                }
                 
            }
            if (!isDupe)
            {
                listMedicationPatsNoDupes.Add(listMedicationPats[index]);
            }
             
        }
        listMedicationPats.Clear();
        for (int dupeIndex = 0;dupeIndex < listMedicationPatsNoDupes.Count;dupeIndex++)
        {
            listMedicationPats.Add(listMedicationPatsNoDupes[dupeIndex]);
        }
    }

    /**
    * Fills listDiseases and listDiseaseDef using the information found in the CCD document xmlDocCcd.  Does NOT insert any records into the db.
    */
    public static void getListDiseases(XmlDocument xmlDocCcd, List<Disease> listDiseases, List<DiseaseDef> listDiseaseDef) throws Exception {
        //The length of listDiseases and listDiseaseDef will be the same. The information in listDiseaseDef might have duplicates.
        //Neither list of objects will be inserted into the db, so there will be no primary or foreign keys.
        List<XmlNode> listProblemActTemplate = GetNodesByTagNameAndAttributes(xmlDocCcd, "templateId", "root", "2.16.840.1.113883.10.20.22.4.3");
        // problem act template.
        List<XmlNode> listProbs = GetParentNodes(listProblemActTemplate);
        for (int i = 0;i < listProbs.Count;i++)
        {
            //We have to start fairly high in the tree so that we can get the effective time if it is available.
            List<XmlNode> xmlNodeEffectiveTimes = GetNodesByTagNameAndAttributes(listProbs[i], "effectiveTime");
            DateTime dateTimeEffectiveLow = DateTime.MinValue;
            DateTime dateTimeEffectiveHigh = DateTime.MinValue;
            if (xmlNodeEffectiveTimes.Count > 0)
            {
                XmlNode xmlNodeEffectiveTime = xmlNodeEffectiveTimes[0];
                dateTimeEffectiveLow = getEffectiveTimeLow(xmlNodeEffectiveTime);
                dateTimeEffectiveHigh = getEffectiveTimeHigh(xmlNodeEffectiveTime);
            }
             
            List<XmlNode> listProblemObservTemplate = GetNodesByTagNameAndAttributes(listProbs[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.4");
            // problem act template.
            List<XmlNode> listProbObs = GetParentNodes(listProblemObservTemplate);
            List<XmlNode> listTypeCodes = GetNodesByTagNameAndAttributesFromList(listProbObs, "code");
            List<XmlNode> listCodes = GetNodesByTagNameAndAttributesFromList(listProbObs, "value");
            String probType = listTypeCodes[0].Attributes["code"].Value;
            String probCode = listCodes[0].Attributes["code"].Value;
            String probName = listCodes[0].Attributes["displayName"].Value;
            List<XmlNode> listStatusObservTemplate = GetNodesByTagNameAndAttributes(listProbs[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.6");
            // Status Observation template.
            List<XmlNode> listStatusObs = GetParentNodes(listStatusObservTemplate);
            List<XmlNode> listActive = GetNodesByTagNameAndAttributesFromList(listStatusObs, "value");
            Disease dis = new Disease();
            dis.SnomedProblemType = probType;
            dis.DateStart = dateTimeEffectiveLow;
            dis.setIsNew(true);
            if (listActive.Count > 0 && StringSupport.equals(listActive[0].Attributes["code"].Value, "55561003"))
            {
                //Active (qualifier value)
                dis.ProbStatus = ProblemStatus.Active;
            }
            else if (listActive.Count > 0 && StringSupport.equals(listActive[0].Attributes["code"].Value, "413322009"))
            {
                //Problem resolved (finding)
                dis.ProbStatus = ProblemStatus.Resolved;
                dis.DateStop = dateTimeEffectiveHigh;
            }
            else
            {
                dis.ProbStatus = ProblemStatus.Inactive;
                dis.DateStop = dateTimeEffectiveHigh;
            }  
            listDiseases.Add(dis);
            DiseaseDef disD = new DiseaseDef();
            disD.IsHidden = false;
            disD.setIsNew(true);
            disD.SnomedCode = probCode;
            disD.DiseaseName = probName;
            listDiseaseDef.Add(disD);
        }
    }

    /**
    * Fills listAllergies and listAllergyDefs using the information found in the CCD document xmlDocCcd.  Inserts a medication in the db corresponding to the allergy.
    */
    public static void getListAllergies(XmlDocument xmlDocCcd, List<Allergy> listAllergies, List<AllergyDef> listAllergyDefs) throws Exception {
        //The length of listAllergies and listAllergyDefs will be the same. The information in listAllergyDefs might have duplicates.
        //Neither list of objects will be inserted into the db, so there will be no primary or foreign keys.
        List<XmlNode> listAllergyProblemActTemplate = GetNodesByTagNameAndAttributes(xmlDocCcd, "templateId", "root", "2.16.840.1.113883.10.20.22.4.30");
        //Allergy problem act template.
        List<XmlNode> listActs = GetParentNodes(listAllergyProblemActTemplate);
        for (int i = 0;i < listActs.Count;i++)
        {
            //We have to start fairly high in the tree so that we can get the effective time if it is available.
            List<XmlNode> xmlNodeEffectiveTimes = GetNodesByTagNameAndAttributes(listActs[i], "effectiveTime");
            //POCD_HD00040.xls line 492. Not required.
            DateTime dateTimeEffectiveLow = DateTime.MinValue;
            DateTime dateTimeEffectiveHigh = DateTime.MinValue;
            if (xmlNodeEffectiveTimes.Count > 0)
            {
                XmlNode xmlNodeEffectiveTime = xmlNodeEffectiveTimes[0];
                dateTimeEffectiveLow = getEffectiveTimeLow(xmlNodeEffectiveTime);
                dateTimeEffectiveHigh = getEffectiveTimeHigh(xmlNodeEffectiveTime);
            }
             
            List<XmlNode> listAllergyObservationTemplates = GetNodesByTagNameAndAttributes(listActs[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.7");
            //Allergy observation template.
            List<XmlNode> listAllergy = GetParentNodes(listAllergyObservationTemplates);
            //List of Allergy Observations.
            List<XmlNode> listCodes = GetNodesByTagNameAndAttributesFromList(listAllergy, "value");
            boolean isActive = true;
            String strStatus = "";
            List<XmlNode> listAllergyObservationTemplatesActive = GetNodesByTagNameAndAttributes(listActs[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.28");
            //Allergy observation template.
            List<XmlNode> listAllergyActive = GetParentNodes(listAllergyObservationTemplatesActive);
            //List of Allergy Observations.
            List<XmlNode> listCodesActive = GetNodesByTagNameAndAttributesFromList(listAllergyActive, "value");
            if (listCodesActive.Count > 0)
            {
                listCodes.Remove(listCodesActive[0]);
                XmlNode xmlNodeCode = listCodesActive[0];
                strStatus = xmlNodeCode.Attributes["code"].Value;
                if (!StringSupport.equals(xmlNodeCode.Attributes["codeSystem"].Value, strCodeSystemSnomed))
                {
                    continue;
                }
                 
                //We can only import Snomeds
                isActive = (StringSupport.equals(strStatus, "55561003"));
            }
             
            //Active (qualifier value)
            List<XmlNode> listAllergyStatusObservationTemplates = GetNodesByTagNameAndAttributes(listActs[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.9");
            //Allergy status observation template.
            List<XmlNode> listAllergyStatus = GetParentNodes(listAllergyStatusObservationTemplates);
            //List of Allergy Observations.
            List<XmlNode> listAlgCodes = GetNodesByTagNameAndAttributesFromList(listAllergyStatus, "value");
            for (int j = 0;j < listAlgCodes.Count;j++)
            {
                listCodes.Remove(listAlgCodes[j]);
                XmlNode xmlNodeCode = listAlgCodes[j];
                String strCodeReaction = xmlNodeCode.Attributes["code"].Value;
                String strAlgStatusDescript = xmlNodeCode.Attributes["displayName"].Value;
                if (!StringSupport.equals(xmlNodeCode.Attributes["codeSystem"].Value, strCodeSystemSnomed))
                {
                    continue;
                }
                 
                //We can only import Snomeds
                Allergy allergy = new Allergy();
                allergy.setIsNew(true);
                //Needed for reconcile window to know this record is not in the db yet.
                allergy.SnomedReaction = PIn.string(strCodeReaction);
                allergy.Reaction = PIn.string(strAlgStatusDescript);
                allergy.DateAdverseReaction = dateTimeEffectiveLow;
                allergy.StatusIsActive = isActive;
                listAllergies.Add(allergy);
            }
            List<XmlNode> listAllergySevereTemplates = GetNodesByTagNameAndAttributes(listActs[i], "templateId", "root", "2.16.840.1.113883.10.20.22.4.8");
            //Allergy observation template.
            List<XmlNode> listAllergySevere = GetParentNodes(listAllergySevereTemplates);
            //List of Allergy Observations.
            List<XmlNode> listCodesSevere = GetNodesByTagNameAndAttributesFromList(listAllergySevere, "value");
            for (int j = 0;j < listCodesSevere.Count;j++)
            {
                listCodes.Remove(listCodesSevere[j]);
                XmlNode xmlNodeCode = listCodesSevere[j];
                String strCodeReaction = xmlNodeCode.Attributes["code"].Value;
                String strAlgStatusDescript = xmlNodeCode.Attributes["displayName"].Value;
                if (!StringSupport.equals(xmlNodeCode.Attributes["codeSystem"].Value, strCodeSystemSnomed))
                {
                    continue;
                }
                 
            }
            //We can only import Snomeds
            String allergyDefName = "";
            Medication med = new Medication();
            List<XmlNode> listRxCodes = GetNodesByTagNameAndAttributesFromList(listAllergy, "code");
            List<Medication> allergyMeds = new List<Medication>();
            for (int j = 0;j < listRxCodes.Count;j++)
            {
                XmlNode xmlNodeCode = listRxCodes[j];
                if (!StringSupport.equals(xmlNodeCode.Attributes[0].Name, "code"))
                {
                    continue;
                }
                 
                if (!StringSupport.equals(xmlNodeCode.Attributes["codeSystem"].Value, strCodeSystemRxNorm))
                {
                    continue;
                }
                 
                //We only want RxNorms here.
                String strCodeRx = xmlNodeCode.Attributes["code"].Value;
                String strRxName = xmlNodeCode.Attributes["displayName"].Value;
                //Look into this being required or not.
                allergyDefName = strRxName;
                med = Medications.getMedicationFromDbByRxCui(PIn.long(strCodeRx));
                if (med == null)
                {
                    med = new Medication();
                    med.MedName = strRxName;
                    med.RxCui = PIn.long(strCodeRx);
                    Medications.insert(med);
                    med.GenericNum = med.MedicationNum;
                    Medications.update(med);
                }
                 
                allergyMeds.Add(med);
            }
            for (int j = 0;j < listCodes.Count;j++)
            {
                XmlNode xmlNodeCode = listCodes[j];
                String strCode = xmlNodeCode.Attributes["code"].Value;
                if (!StringSupport.equals(xmlNodeCode.Attributes["codeSystem"].Value, strCodeSystemSnomed))
                {
                    continue;
                }
                 
                //We can only import Snomeds
                AllergyDef allergyDef = new AllergyDef();
                allergyDef.setIsNew(true);
                //Needed for reconcile window to know this record is not in the db yet.
                if (med.MedicationNum != 0)
                {
                    allergyDef.MedicationNum = med.MedicationNum;
                }
                 
                //else {TODO: Change to Unii
                //	allergyDef.SnomedAllergyTo=PIn.String(strCode);
                //}
                allergyDef.Description = allergyDefName;
                allergyDef.IsHidden = false;
                allergyDef.MedicationNum = allergyMeds[j].MedicationNum;
                if (StringSupport.equals(strCode, "419511003"))
                {
                    allergyDef.SnomedType = SnomedAllergy.AdverseReactionsToDrug;
                }
                else if (StringSupport.equals(strCode, "418471000"))
                {
                    allergyDef.SnomedType = SnomedAllergy.AdverseReactionsToFood;
                }
                else if (StringSupport.equals(strCode, "419199007"))
                {
                    allergyDef.SnomedType = SnomedAllergy.AdverseReactionsToSubstance;
                }
                else if (StringSupport.equals(strCode, "418038007"))
                {
                    allergyDef.SnomedType = SnomedAllergy.AllergyToSubstance;
                }
                else if (StringSupport.equals(strCode, "416098002"))
                {
                    allergyDef.SnomedType = SnomedAllergy.DrugAllergy;
                }
                else if (StringSupport.equals(strCode, "59037007"))
                {
                    allergyDef.SnomedType = SnomedAllergy.DrugIntolerance;
                }
                else if (StringSupport.equals(strCode, "414285001"))
                {
                    allergyDef.SnomedType = SnomedAllergy.FoodAllergy;
                }
                else if (StringSupport.equals(strCode, "235719002"))
                {
                    allergyDef.SnomedType = SnomedAllergy.FoodIntolerance;
                }
                else if (StringSupport.equals(strCode, "420134006"))
                {
                    allergyDef.SnomedType = SnomedAllergy.AdverseReactions;
                }
                else
                {
                    allergyDef.SnomedType = SnomedAllergy.None;
                }         
                listAllergyDefs.Add(allergyDef);
            }
        }
    }

    public static boolean isCCD(String strXml) throws Exception {
        XmlDocument doc = new XmlDocument();
        try
        {
            doc.LoadXml(strXml);
        }
        catch (Exception __dummyCatchVar1)
        {
            return false;
        }

        if (StringSupport.equals(doc.DocumentElement.Name.ToLower(), "clinicaldocument"))
        {
            return true;
        }
        else //CCD and CCDA
        if (StringSupport.equals(doc.DocumentElement.Name.ToLower(), "continuityofcarerecord") || StringSupport.equals(doc.DocumentElement.Name.ToLower(), "ccr:continuityofcarerecord"))
        {
            return true;
        }
          
        return false;
    }

    //CCR
    public static boolean isCcdEmailAttachment(EmailAttach emailAttach) throws Exception {
        String strFilePathAttach = CodeBase.ODFileUtils.combinePaths(EmailMessages.getEmailAttachPath(),emailAttach.ActualFileName);
        if (!StringSupport.equals(Path.GetExtension(strFilePathAttach).ToLower(), ".xml"))
        {
            return false;
        }
         
        String strTextXml = File.ReadAllText(strFilePathAttach);
        if (!EhrCCD.isCCD(strTextXml))
        {
            return false;
        }
         
        return true;
    }

    public static boolean hasCcdEmailAttachment(EmailMessage emailMessage) throws Exception {
        if (emailMessage.Attachments == null)
        {
            return false;
        }
         
        for (int i = 0;i < emailMessage.Attachments.Count;i++)
        {
            if (EhrCCD.IsCcdEmailAttachment(emailMessage.Attachments[i]))
            {
                return true;
            }
             
        }
        return false;
    }

}


