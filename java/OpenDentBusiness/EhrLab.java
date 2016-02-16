//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import EhrLaboratories.HL70065;
import EhrLaboratories.HL70119;
import EhrLaboratories.HL70123;
import EhrLaboratories.HL70200;
import EhrLaboratories.HL70203;
import OpenDentBusiness.CrudSpecialColType;
import OpenDentBusiness.EhrLab;
import OpenDentBusiness.EhrLabClinicalInfo;
import OpenDentBusiness.EhrLabClinicalInfos;
import OpenDentBusiness.EhrLabNote;
import OpenDentBusiness.EhrLabNotes;
import OpenDentBusiness.EhrLabResult;
import OpenDentBusiness.EhrLabResults;
import OpenDentBusiness.EhrLabResultsCopyTo;
import OpenDentBusiness.EhrLabResultsCopyTos;
import OpenDentBusiness.EhrLabSpecimen;
import OpenDentBusiness.EhrLabSpecimens;
import OpenDentBusiness.TableBase;

/**
* For EHR module, lab request that contains all required fields for HL7 Lab Reporting Interface (LRI).
*/
public class EhrLab  extends TableBase 
{
    /**
    * Primary key.
    */
    public long EhrLabNum = new long();
    /**
    * FK to patient.PatNum.  PID-3.1
    */
    public long PatNum = new long();
    /**
    * //FK to EhrLabMessage.EhrLabMessageNum.  Internal use.
    */
    //public long EhrLabMessageNum;
    /**
    * Always RE unless importing from outside sources.
    */
    public HL70119 OrderControlCode = HL70119.CA;
    /**
    * Placer order number assigned to this lab order, usually assigned by the dental office.  Not the same as EhrLabNum, but similar.  OBR.2.1 and ORC.2.1.
    */
    public String PlacerOrderNum = new String();
    /**
    * Usually empty, only used if PlacerOrderNum+PlacerUniversalID cannot uniquely identify the lab order.  OBR.2.2 and ORC.2.2.
    */
    public String PlacerOrderNamespace = new String();
    /**
    * Usually OID root that uniquely identifies the context that makes PlacerOrderNum globally unique.  May be GUID if importing from other sources.   OBR.2.3 and ORC.2.3.
    */
    public String PlacerOrderUniversalID = new String();
    /**
    * Always "ISO", unless importing from other sources.  OBR.2.4 and ORC.2.4
    */
    public String PlacerOrderUniversalIDType = new String();
    /**
    * Filler order number assigned to this lab order, usually assigned by the laboratory.  Not the same as EhrLabNum, but similar.  OBR.3.1 and ORC.3.1.
    */
    public String FillerOrderNum = new String();
    /**
    * Usually empty, only used if FillerOrderNum+FillerUniversalID cannot uniquely identify the lab order.  OBR.3.2 and ORC.3.2.
    */
    public String FillerOrderNamespace = new String();
    /**
    * Usually OID root that uniquely identifies the context that makes FillerOrderNum globally unique.  May be GUID if importing from other sources.  OBR.3.2 and ORC.3.3.
    */
    public String FillerOrderUniversalID = new String();
    /**
    * Always "ISO", unless importing from other sources.  OBR.3.4 and ORC.3.4
    */
    public String FillerOrderUniversalIDType = new String();
    /**
    * [0..1] May be empty.  Placer group number assigned to this lab order, usually assigned by the dental office.  ORC.4.1.
    */
    public String PlacerGroupNum = new String();
    /**
    * [0..1] Usually empty, only used if PlacerGroupNum+PlacerUniversalID cannot uniquely identify the Group Num.  ORC.4.2.
    */
    public String PlacerGroupNamespace = new String();
    /**
    * [0..1] Usually OID root that uniquely identifies the context that makes PlacerGroupNum globally unique.  May be GUID if importing from other sources.   ORC.4.3.
    */
    public String PlacerGroupUniversalID = new String();
    /**
    * [0..1] Always "ISO", unless importing from other sources.  ORC.4.4
    */
    public String PlacerGroupUniversalIDType = new String();
    /**
    * May be provnum or NPI num or any other num, when combined with OrderingProviderIdAssigningAuthority should uniquely identify the provider.  ORC.12.1
    */
    public String OrderingProviderID = new String();
    /**
    * ORC.12.2
    */
    public String OrderingProviderLName = new String();
    /**
    * ORC.12.3
    */
    public String OrderingProviderFName = new String();
    /**
    * Middle names or initials therof.  ORC.12.4
    */
    public String OrderingProviderMiddleNames = new String();
    /**
    * Example: JR or III.  ORC.12.5
    */
    public String OrderingProviderSuffix = new String();
    /**
    * Example: DR, Not MD, MD would be stored in an optional field that was not implemented called OrderingProviderDegree.  ORC.12.6
    */
    public String OrderingProviderPrefix = new String();
    /**
    * Usually empty, "The value of [this field] reflects a local code that represents the combination of [the next two fields]."  ORC.12.9.1
    */
    public String OrderingProviderAssigningAuthorityNamespaceID = new String();
    /**
    * ISO compliant OID that represents the organization that assigned the unique provider ID.  ORC.12.9.2
    */
    public String OrderingProviderAssigningAuthorityUniversalID = new String();
    /**
    * Always "ISO", unless importing from outside source.  ORC.12.9.3
    */
    public String OrderingProviderAssigningAuthorityIDType = new String();
    /**
    * Describes the type of name used.  ORC.12.10
    */
    public HL70200 OrderingProviderNameTypeCode = HL70200.C;
    /**
    * Must be value from HL70203 code set, see note at bottom of EhrLab.cs for usage.  ORC.12.13
    */
    public HL70203 OrderingProviderIdentifierTypeCode = HL70203.AN;
    /**
    * Enumerates the OBR segments within a single message starting with 1.  OBR.1
    */
    public long SetIdOBR = new long();
    //OBR.PlacerOrderNumber same as above.
    //OBR.FillerOrderNumber same as above.
    /**
    * OBR.4.1
    */
    public String UsiID = new String();
    /**
    * Description of UsiId.  OBR.4.2
    */
    public String UsiText = new String();
    /**
    * CodeSystem that UsiId came from.  OBR.4.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String UsiCodeSystemName = new String();
    /**
    * OBR.4.4
    */
    public String UsiIDAlt = new String();
    /**
    * Description of UsiIdAlt.  OBR.4.5
    */
    public String UsiTextAlt = new String();
    /**
    * CodeSystem that UsiId came from.  OBR.4.6
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String UsiCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  OBR.4.9
    */
    public String UsiTextOriginal = new String();
    /**
    * Stored as string in the format YYYY[MM[DD[HH[MM[SS]]]]] where bracketed values are optional.  When time is not known will be valued "0000".  OBR.7.1
    */
    public String ObservationDateTimeStart = new String();
    /**
    * May be empty.  Stored as string in the format YYYY[MM[DD[HH[MM[SS]]]]] where bracketed values are optional.  OBR.8.1
    */
    public String ObservationDateTimeEnd = new String();
    /**
    * OBR.11
    */
    public HL70065 SpecimenActionCode = HL70065.A;
    /**
    * [0..*]This is not a data column but is stored in a seperate table named EhrLabClinicalInfo.  OBR.13.*
    */
    private List<EhrLabClinicalInfo> _listRelevantClinicalInformation = new List<EhrLabClinicalInfo>();
    //OBR.OrderingProvider same as above.
    /**
    * Date Time that the result was stored or last updated.  Stored in the format YYYYMMDDHHmmss.  Required to be accurate to the second.  OBR.22.1
    */
    public String ResultDateTime = new String();
    /**
    * OBR.25
    */
    public HL70123 ResultStatus = HL70123.A;
    /**
    * OBR.26.1.1
    */
    public String ParentObservationID = new String();
    /**
    * Description of ParentObservationId.  OBR.26.1.2
    */
    public String ParentObservationText = new String();
    /**
    * CodeSystem that ParentObservationId came from.  OBR.26.1.3
    */
    //[CrudColumn(SpecialType=CrudSpecialColType.EnumAsString)]
    public String ParentObservationCodeSystemName = new String();
    /**
    * OBR.26.1.4
    */
    public String ParentObservationIDAlt = new String();
    /**
    * Description of ParentObservationIdAlt.  OBR.26.1.5
    */
    public String ParentObservationTextAlt = new String();
    /**
    * CodeSystem that ParentObservationIdAlt came from.  OBR.26.1.6
    */
    public String ParentObservationCodeSystemNameAlt = new String();
    /**
    * Optional text that describes the original text used to encode the values above.  OBR.26.1.9
    */
    public String ParentObservationTextOriginal = new String();
    /**
    * OBR.26.2
    */
    public String ParentObservationSubID = new String();
    /**
    * [0..*]This is not a data column but is stored in a seperate table named EhrLabResultsCopyTo. OBR.28.*
    */
    private List<EhrLabResultsCopyTo> _listEhrLabResultsCopyTo = new List<EhrLabResultsCopyTo>();
    /**
    * Placer order number assigned to this lab order, usually assigned by the dental office.  Not the same as EhrLabNum, but similar.  OBR.29.1.1.
    */
    public String ParentPlacerOrderNum = new String();
    /**
    * Usually empty, only used if PlacerOrderNum+PlacerUniversalID cannot uniquely identify the lab order.  OBR.29.1.2
    */
    public String ParentPlacerOrderNamespace = new String();
    /**
    * Usually OID root that uniquely identifies the context that makes PlacerOrderNum globally unique.  May be GUID if importing from other sources.   OBR.29.1.3
    */
    public String ParentPlacerOrderUniversalID = new String();
    /**
    * Always "ISO", unless importing from other sources.  OBR.29.1.4
    */
    public String ParentPlacerOrderUniversalIDType = new String();
    /**
    * Filler order number assigned to this lab order, usually assigned by the laboratory.  Not the same as EhrLabNum, but similar.  OBR.29.2.1
    */
    public String ParentFillerOrderNum = new String();
    /**
    * Usually empty, only used if FillerOrderNum+FillerUniversalID cannot uniquely identify the lab order.  OBR.29.2.2
    */
    public String ParentFillerOrderNamespace = new String();
    /**
    * Usually OID root that uniquely identifies the context that makes FillerOrderNum globally unique.  May be GUID if importing from other sources.  OBR.29.2.3
    */
    public String ParentFillerOrderUniversalID = new String();
    /**
    * Always "ISO", unless importing from other sources.  OBR.29.2.4
    */
    public String ParentFillerOrderUniversalIDType = new String();
    /**
    * "Film with patient."  Technically a coded value from HL70507.  Stored as a bool instead of 7 seperate columns. OBR.49.* is used to set both ListEhrLabResultsHandlingF and ListEhrLabResultsHandlingN.  OBR.49.*
    */
    public boolean ListEhrLabResultsHandlingF = new boolean();
    /**
    * "Notify provider when ready."  Technically a coded value from HL70507.  Stored as a bool instead of 7 seperate columns. OBR.49.* is used to set both ListEhrLabResultsHandlingF and ListEhrLabResultsHandlingN.  OBR.49.*
    */
    public boolean ListEhrLabResultsHandlingN = new boolean();
    /**
    * [0..*]This is not a data column but is stored in a seperate table named EhrLabNote. NTE.*
    */
    private List<EhrLabNote> _listEhrLabNotes = new List<EhrLabNote>();
    /**
    * Enumerates the TQ1 segments within a single message starting with 1.  TQ1.1
    */
    public long TQ1SetId = new long();
    /**
    * Stored as string in the format YYYY[MM[DD[HH[MM[SS]]]]] where bracketed values are optional.  TQ1.7
    */
    public String TQ1DateTimeStart = new String();
    /**
    * Stored as string in the format YYYY[MM[DD[HH[MM[SS]]]]] where bracketed values are optional.  TQ1.8
    */
    public String TQ1DateTimeEnd = new String();
    /**
    * This gets set when a provider is logged in with a valid EHR key and then creates a lab.
    */
    public boolean IsCpoe = new boolean();
    /**
    * The PID Segment from the HL7 message used to generate or update the lab order.
    */
    public String OriginalPIDSegment = new String();
    /**
    * [0..*] This is not a data column but is stored in a seperate table named EhrLabResult. OBX.*
    */
    private List<EhrLabResult> _listEhrLabResults = new List<EhrLabResult>();
    /**
    * [0..*] This is not a data column but is stored in a seperate table named EhrLabSpecimen. SPM.*
    */
    private List<EhrLabSpecimen> _listEhrLabSpecimen = new List<EhrLabSpecimen>();
    /**
    * 
    */
    public EhrLab copy() throws Exception {
        return (EhrLab)MemberwiseClone();
    }

    /**
    * Only filled with EhrLabNotes when value is used.  To refresh ListEhrLabResults, set it equal to null or explicitly reassign it using EhrLabResults.GetForLab(EhrLabNum).
    */
    public List<EhrLabNote> getListEhrLabNotes() throws Exception {
        if (_listEhrLabNotes == null)
        {
            if (EhrLabNum == 0)
            {
                _listEhrLabNotes = new List<EhrLabNote>();
            }
            else
            {
                _listEhrLabNotes = EhrLabNotes.getForLab(EhrLabNum);
            } 
        }
         
        return _listEhrLabNotes;
    }

    public void setListEhrLabNotes(List<EhrLabNote> value) throws Exception {
        _listEhrLabNotes = value;
    }

    /**
    * Only filled with EhrLabResults when value is used.  To refresh ListEhrLabResults, set it equal to null or explicitly reassign it using EhrLabResults.GetForLab(EhrLabNum).
    */
    public List<EhrLabResult> getListEhrLabResults() throws Exception {
        if (_listEhrLabResults == null)
        {
            if (EhrLabNum == 0)
            {
                _listEhrLabResults = new List<EhrLabResult>();
            }
            else
            {
                _listEhrLabResults = EhrLabResults.getForLab(EhrLabNum);
            } 
        }
         
        return _listEhrLabResults;
    }

    public void setListEhrLabResults(List<EhrLabResult> value) throws Exception {
        _listEhrLabResults = value;
    }

    /**
    * Only filled with EhrLabSpecimens when value is used.  To refresh ListEhrLabSpecimens, set it equal to null or explicitly reassign it using EhrLabSpecimens.GetForLab(EhrLabNum).
    */
    public List<EhrLabSpecimen> getListEhrLabSpecimens() throws Exception {
        if (_listEhrLabSpecimen == null)
        {
            if (EhrLabNum == 0)
            {
                _listEhrLabSpecimen = new List<EhrLabSpecimen>();
            }
            else
            {
                _listEhrLabSpecimen = EhrLabSpecimens.getForLab(EhrLabNum);
            } 
        }
         
        return _listEhrLabSpecimen;
    }

    public void setListEhrLabSpecimens(List<EhrLabSpecimen> value) throws Exception {
        _listEhrLabSpecimen = value;
    }

    public List<EhrLabResultsCopyTo> getListEhrLabResultsCopyTo() throws Exception {
        if (_listEhrLabResultsCopyTo == null)
        {
            if (EhrLabNum == 0)
            {
                _listEhrLabResultsCopyTo = new List<EhrLabResultsCopyTo>();
            }
            else
            {
                _listEhrLabResultsCopyTo = EhrLabResultsCopyTos.getForLab(EhrLabNum);
            } 
        }
         
        return _listEhrLabResultsCopyTo;
    }

    public void setListEhrLabResultsCopyTo(List<EhrLabResultsCopyTo> value) throws Exception {
        _listEhrLabResultsCopyTo = value;
    }

    public List<EhrLabClinicalInfo> getListRelevantClinicalInformations() throws Exception {
        if (_listRelevantClinicalInformation == null)
        {
            if (EhrLabNum == 0)
            {
                _listRelevantClinicalInformation = new List<EhrLabClinicalInfo>();
            }
            else
            {
                _listRelevantClinicalInformation = EhrLabClinicalInfos.getForLab(EhrLabNum);
            } 
        }
         
        return _listRelevantClinicalInformation;
    }

    public void setListRelevantClinicalInformations(List<EhrLabClinicalInfo> value) throws Exception {
        _listRelevantClinicalInformation = value;
    }

    public static String formatDateFromHL7(String hl7dt) throws Exception {
        //hl7date time yyyyMMDDhhmmssssss-zzzz
        if (hl7dt == null || StringSupport.equals(hl7dt, ""))
        {
            return "";
        }
         
        if (!Regex.IsMatch(hl7dt, "^\\d{4}(\\d\\d(\\d\\d(\\d\\d(\\d\\d(\\d\\d(\\.\\d(\\d(\\d(\\d)?)?)?)?)?)?)?)?)?([\\+-]\\d{4})?$"))
        {
            return hl7dt;
        }
         
        //                         yyyy   MM   dd   hh   mm   ss   .s  s  s  s                     +/- zzzz
        /**
        * does not conform. Return whatever garbage was input.
        */
        String retVal = "";
        String zone = "";
        if (hl7dt.Contains("+") || hl7dt.Contains("-"))
        {
            //value contains a time zone.
            zone = hl7dt.Substring(hl7dt.Length - 5);
            //sign plus 4 digits
            hl7dt = hl7dt.Replace(zone, "");
        }
         
        if (hl7dt.Length > 3)
        {
            retVal += hl7dt.Substring(0, 4);
        }
         
        //yyyy
        if (hl7dt.Length > 5)
        {
            retVal += "-" + hl7dt.Substring(4, 2);
        }
         
        //MM
        if (hl7dt.Length > 7)
        {
            retVal += "-" + hl7dt.Substring(6, 2);
        }
         
        //dd
        if (hl7dt.Length > 9)
        {
            retVal += " " + hl7dt.Substring(8, 2);
        }
         
        //hh
        if (hl7dt.Length > 11)
        {
            retVal += ":" + hl7dt.Substring(10, 2);
        }
         
        //mm
        if (hl7dt.Length > 13)
        {
            retVal += ":" + hl7dt.Substring(12);
        }
         
        return retVal + " " + zone;
    }

    //ss.ssss
    public static String formatDateToHL7(String hrdt) throws Exception {
        //human readable date time
        hrdt = hrdt.Trim();
        if (!Regex.IsMatch(hrdt, "^\\d{4}(-\\d\\d(-\\d\\d(\\s\\d\\d(:\\d\\d(:\\d\\d(\\.\\d(\\d(\\d(\\d)?)?)?)?)?)?)?)?)?(\\s[\\+-]\\d{4})?$"))
        {
            return hrdt;
        }
         
        //                      yyyy -  MM -  dd    hh :  mm :  ss   .s  s  s  s                       +/- zzzz
        //does not conform. Return whatever garbage was input.
        String zone = "";
        if (hrdt[hrdt.Length - 5] == '+' || hrdt[hrdt.Length - 5] == '-')
        {
            //value contains a time zone.
            zone = hrdt.Substring(hrdt.Length - 5);
            //sign plus 4 digits
            hrdt = hrdt.Replace(zone, "");
        }
         
        return hrdt.Replace("-", "").Replace(" ", "").Replace(":", "") + zone;
    }

}


