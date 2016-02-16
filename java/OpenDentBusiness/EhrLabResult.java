//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import EhrLaboratories.HL70085;
import EhrLaboratories.HL70190;
import EhrLaboratories.HL70200;
import EhrLaboratories.HL70203;
import EhrLaboratories.USPSAlphaStateCode;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrLabNote;
import OpenDentBusiness.EhrLabNotes;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.TableBase;

/**
* For EHR module, lab result that contains all required fields for HL7 Lab Reporting Interface (LRI).  OBX
*/
public class EhrLabResult  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabResultNum = new long();
    /**
    * FK to EhrLab.EhrLabNum.
    */
    public long EhrLabNum = new long();
    /**
    * Enumerates the OBX segments within a single message starting with 1.  OBX.1
    */
    public long SetIdOBX = new long();
    /**
    * This field identifies the data type used for ObservationValue (OBX-5).  OBX.2
    */
    public EhrLaboratories.HL70125 ValueType = EhrLaboratories.HL70125.CE;
    /**
    * "LOINC shall be used as the standard coding system for this field if an appropriate LOINC code exists. Appropriate status is defined in the LOINC Manual Section 11.2 Classification of LOINC Term Status. If a local coding system is in use, a local code should also be sent to help with identification of coding issues. When no valid LOINC exists the local code may be the only code sent.  When populating this field with values, this guide does not give preference to the triplet in which the standard (LOINC) code should appear." OBX.3.1
    */
    public String ObservationIdentifierID = new String();
    /**
    * Description of ObservationIdentifierId. OBX.3.2
    */
    public String ObservationIdentifierText = new String();
    /**
    * CodeSystem that ObservationIdentifierId came from. Should be "LN".  OBX.3.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ObservationIdentifierCodeSystemName = new String();
    /**
    * Probably a LoincCode or empty.  OBX.3.4
    */
    public String ObservationIdentifierIDAlt = new String();
    /**
    * Description of ObservationIdentifierIdAlt.  OBX.3.5
    */
    public String ObservationIdentifierTextAlt = new String();
    /**
    * CodeSystem that ObservationIdentifierId came from. Should be "LN" or empty.  OBX.3.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ObservationIdentifierCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  OBX.3.7
    */
    public String ObservationIdentifierTextOriginal = new String();
    /**
    * OBX.4
    */
    public String ObservationIdentifierSub = new String();
    /**
    * OBX.5.1
    */
    public String ObservationValueCodedElementID = new String();
    /**
    * Description of ObservationValueCodedElementId.  OBX.5.2
    */
    public String ObservationValueCodedElementText = new String();
    /**
    * CodeSystem that ObservationValueCodedElementId came from.  OBX.5.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ObservationValueCodedElementCodeSystemName = new String();
    /**
    * OBX.5.4
    */
    public String ObservationValueCodedElementIDAlt = new String();
    /**
    * Description of ObservationValueCodedElementIdAlt.  OBX.5.5
    */
    public String ObservationValueCodedElementTextAlt = new String();
    /**
    * CodeSystem that ObservationValueCodedElementId came from.  OBX.5.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ObservationValueCodedElementCodeSystemNameAlt = new String();
    /**
    * CWE only.  Optional text that describes the original text used to encode the values above.  OBX.5.7
    */
    public String ObservationValueCodedElementTextOriginal = new String();
    /**
    * Stored as string in the formatYYYY[MM[DD]] for DT and YYYYMMDDHHMMSS for TS. Note: this is the lab result value, not the DT the test was performed. OBX.5.1
    */
    public String ObservationValueDateTime = new String();
    /**
    * Note: this is the lab result value, not the time the test was performed. OBX.5.1
    */
    public TimeSpan ObservationValueTime = new TimeSpan();
    /**
    * OBX.5.1
    */
    public String ObservationValueComparator = new String();
    /**
    * OBX.5.2
    */
    public double ObservationValueNumber1 = new double();
    /**
    * OBX.5.3
    */
    public String ObservationValueSeparatorOrSuffix = new String();
    /**
    * OBX.5.4
    */
    public double ObservationValueNumber2 = new double();
    /**
    * OBX.5.1
    */
    public double ObservationValueNumeric = new double();
    /**
    * OBX.5.1
    */
    public String ObservationValueText = new String();
    /**
    * "UCUM (Unified Code for Units of Measure) will be evaluated during the pilot for potential subsequent inclusion. As part of the pilot test, for dimensionless units the UCUM representation could be {string}, e.g., for titer the pilot might use {titer} to test feasibility. When sending units of measure as text, they must be placed in the correct component of OBX-6 (CWE_CRE.9)."  OBX.6.1
    */
    public String UnitsID = new String();
    /**
    * Description of UnitsId.  OBX.6.2
    */
    public String UnitsText = new String();
    /**
    * CodeSystem that UnitsId came from. Should be "UCUM".  OBX.6.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String UnitsCodeSystemName = new String();
    /**
    * OBX.6.4
    */
    public String UnitsIDAlt = new String();
    /**
    * Description of UnitsIdAlt.  OBX.6.5
    */
    public String UnitsTextAlt = new String();
    /**
    * CodeSystem that UnitsId came from.  OBX.6.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String UnitsCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  OBX.6.7
    */
    public String UnitsTextOriginal = new String();
    /**
    * "Guidance: It is not appropriate to send the reference range for a result in an associated NTE segment. It would be appropriate to send additional information clarifying the reference range in an NTE associated with this OBX-"  OBX.7
    */
    public String referenceRange = new String();
    /**
    * Comma Delimited list of Abnormal Flags using HL70078 enum values.  OBX.8.*
    */
    public String AbnormalFlags = new String();
    /**
    * [0..*] This is not a data column but is stored in a seperate table named EhrLabResult. OBX.*
    */
    private List<EhrLabNote> _listEhrLabResultNotes = new List<EhrLabNote>();
    /**
    * Coded status of result.  OBX.11
    */
    public HL70085 ObservationResultStatus = HL70085.D;
    /**
    * Stored as string in the format YYYYMMDD[HH[MM[SS]]]. "For specimen based test, if it is valued it must be the same as SPM-17.
    * If SPM-17 is present and relates to the same observation, then OBX-14 must be within the DR range."  OBX.14.1
    */
    public String ObservationDateTime = new String();
    /**
    * Stored as string in the format YYYYMMDD[HH[MM[SS]]].  "Be as precise as appropriate and available."  OBX.19.1
    */
    public String AnalysisDateTime = new String();
    /**
    * OBX.23.1
    */
    public String PerformingOrganizationName = new String();
    /**
    * OBX.23.6.1
    */
    public String PerformingOrganizationNameAssigningAuthorityNamespaceId = new String();
    /**
    * The Assigning Authority component is used to identify the system, application, organization, etc. that assigned the ID in component 10.  OBX.23.6.2
    */
    public String PerformingOrganizationNameAssigningAuthorityUniversalId = new String();
    /**
    * Should always be "ISO", unless importing.  OBX.23.6.3
    */
    public String PerformingOrganizationNameAssigningAuthorityUniversalIdType = new String();
    /**
    * OBX.23.7
    */
    public HL70203 PerformingOrganizationIdentifierTypeCode = HL70203.AN;
    /**
    * OBX.23.10
    */
    public String PerformingOrganizationIdentifier = new String();
    /**
    * OBX.24.1.1
    */
    public String PerformingOrganizationAddressStreet = new String();
    /**
    * OBX.24.2
    */
    public String PerformingOrganizationAddressOtherDesignation = new String();
    /**
    * OBX.24.3
    */
    public String PerformingOrganizationAddressCity = new String();
    /**
    * USPS Alpha State Codes.  OBX.24.4
    */
    public USPSAlphaStateCode PerformingOrganizationAddressStateOrProvince = USPSAlphaStateCode.AL;
    /**
    * OBX.24.5
    */
    public String PerformingOrganizationAddressZipOrPostalCode = new String();
    /**
    * Should be the three letter Alpha Code derived from ISO 3166 alpha-3 code set. http://www.nationsonline.org/oneworld/country_code_list.htm OBX.24.6
    */
    public String PerformingOrganizationAddressCountryCode = new String();
    /**
    * OBX.24.7
    */
    public HL70190 PerformingOrganizationAddressAddressType = HL70190.BA;
    /**
    * Should be based on FIPS 6-4. We are just importing the string as is. OBX.24.8
    */
    public String PerformingOrganizationAddressCountyOrParishCode = new String();
    /**
    * May be provnum or NPI num or any other num, when combined with MedicalDirectorIdAssigningAuthority should uniquely identify the provider.  OBX.25.1
    */
    public String MedicalDirectorID = new String();
    /**
    * OBX.25.2
    */
    public String MedicalDirectorLName = new String();
    /**
    * OBX.25.3
    */
    public String MedicalDirectorFName = new String();
    /**
    * Middle names or initials therof.  OBX.25.4
    */
    public String MedicalDirectorMiddleNames = new String();
    /**
    * Example: JR or III.  OBX.25.5
    */
    public String MedicalDirectorSuffix = new String();
    /**
    * Example: DR, Not MD, MD would be stored in an optional field that was not implemented called MedicalDirectorDegree.  OBX.25.6
    */
    public String MedicalDirectorPrefix = new String();
    /**
    * Usually empty, "The value of [this field] reflects a local code that represents the combination of [the next two fields]."  OBX.25.9.1
    */
    public String MedicalDirectorAssigningAuthorityNamespaceID = new String();
    /**
    * ISO compliant OID that represents the organization that assigned the unique provider ID.  OBX.25.9.2
    */
    public String MedicalDirectorAssigningAuthorityUniversalID = new String();
    /**
    * Always "ISO", unless importing from outside source.  OBX.25.9.3
    */
    public String MedicalDirectorAssigningAuthorityIDType = new String();
    /**
    * Describes the type of name used.  OBX.25.10
    */
    public HL70200 MedicalDirectorNameTypeCode = HL70200.C;
    /**
    * Must be value from HL70203 code set, see note at bottom of EhrLab.cs for usage.  OBX.25.13
    */
    public HL70203 MedicalDirectorIdentifierTypeCode = HL70203.AN;
    /**
    * 
    */
    public EhrLabResult copy() throws Exception {
        return (EhrLabResult)MemberwiseClone();
    }

    public EhrLabResult() throws Exception {
        AbnormalFlags = "";
    }

    /**
    * Only filled with EhrLabResultNotes when value is used.  To refresh ListEhrLabResults, set it equal to null or explicitly reassign it using EhrLabNotes.GetForLabResult(EhrLabResultNum).
    */
    public List<EhrLabNote> getListEhrLabResultNotes() throws Exception {
        if (_listEhrLabResultNotes == null)
        {
            _listEhrLabResultNotes = EhrLabNotes.getForLabResult(EhrLabResultNum);
        }
         
        return _listEhrLabResultNotes;
    }

    public void setListEhrLabResultNotes(List<EhrLabNote> value) throws Exception {
        _listEhrLabResultNotes = value;
    }

}


