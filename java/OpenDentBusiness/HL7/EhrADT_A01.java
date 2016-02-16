//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness.HL7;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Appointment;
import OpenDentBusiness.Clinic;
import OpenDentBusiness.Clinics;
import OpenDentBusiness.EhrAptObs;
import OpenDentBusiness.EhrAptObses;
import OpenDentBusiness.EhrAptObsIdentifier;
import OpenDentBusiness.EhrAptObsType;
import OpenDentBusiness.HL7.MessageHL7;
import OpenDentBusiness.HL7.SegmentHL7;
import OpenDentBusiness.Icd10;
import OpenDentBusiness.Icd10s;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Loinc;
import OpenDentBusiness.Loincs;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.Patient;
import OpenDentBusiness.PatientGender;
import OpenDentBusiness.PatientRace;
import OpenDentBusiness.PatientRaces;
import OpenDentBusiness.Patients;
import OpenDentBusiness.PatientStatus;
import OpenDentBusiness.PatRace;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;
import OpenDentBusiness.SegmentNameHL7;
import OpenDentBusiness.Snomed;
import OpenDentBusiness.Snomeds;
import OpenDentBusiness.Ucum;
import OpenDentBusiness.Ucums;

/**
* Syndromic surveillance messaging.  Written using the PHIN HL7 2.5.1 guide.
* Since we are only certifying ambulatory, we have much fewer requirements to meet certification.
* Ambulatory implementations must fill all required fields, but can ignore all "required if known" fields and can also ignore all optional fields.
* Inpatient implementations must fill all required fields and all "required if known" fields, but are allowed to skip optional fields.
* For inpatient, if a field is required if known, then UI would be needed if there was no way to enter the data.  Basically required if known fields become required fields if implementing inpatient.
*/
public class EhrADT_A01   
{
    /**
    * Set in constructor and must not be modified.
    */
    private Appointment _appt;
    /**
    * Set in constructor and must not be modified.
    */
    private Patient _pat;
    /**
    * The entire message object after it is successfully built.
    */
    private MessageHL7 _msg;
    /**
    * Helper variable.
    */
    private SegmentHL7 _seg;
    private String _sendingFacilityNpi = new String();
    private String _sendingFacilityName = new String();
    private String _sendingFacilityAddress1 = new String();
    private String _sendingFacilityAddress2 = new String();
    private String _sendingFacilityCity = new String();
    private String _sendingFacilityState = new String();
    private String _sendingFacilityZip = new String();
    /**
    * Creates the Message object and fills it with data.
    */
    public EhrADT_A01(Appointment appt) throws Exception {
        String errors = validate(appt);
        if (!StringSupport.equals(errors, ""))
        {
            throw new Exception(errors);
        }
         
        _appt = appt;
        _pat = Patients.getPat(appt.PatNum);
        initializeVariables();
        buildMessage();
    }

    private void initializeVariables() throws Exception {
        Provider provFacility = Providers.GetProv(PrefC.getInt(PrefName.PracticeDefaultProv));
        _sendingFacilityNpi = provFacility.NationalProvID;
        _sendingFacilityName = PrefC.getString(PrefName.PracticeTitle);
        _sendingFacilityAddress1 = PrefC.getString(PrefName.PracticeAddress);
        _sendingFacilityAddress2 = PrefC.getString(PrefName.PracticeAddress2);
        _sendingFacilityCity = PrefC.getString(PrefName.PracticeCity);
        _sendingFacilityState = PrefC.getString(PrefName.PracticeST);
        _sendingFacilityZip = PrefC.getString(PrefName.PracticeZip);
        if (!PrefC.getBool(PrefName.EasyNoClinics) && _appt.ClinicNum != 0)
        {
            //Using clinics and a clinic is assigned.
            Clinic clinic = Clinics.getClinic(_appt.ClinicNum);
            _sendingFacilityName = clinic.Description;
            _sendingFacilityAddress1 = clinic.Address;
            _sendingFacilityAddress2 = clinic.Address2;
            _sendingFacilityCity = clinic.City;
            _sendingFacilityState = clinic.State;
            _sendingFacilityZip = clinic.Zip;
        }
         
    }

    private void buildMessage() throws Exception {
        _msg = new MessageHL7(MessageTypeHL7.ADT);
        mSH();
        //MSH segment. Required.  Cardinality [1..1].
        eVN();
        //EVN segment.  Required.  Cardinality [1..1].
        pID();
        //PID segment.  Required.  Cardinality [1..1].
        pV1();
        //PV1 segment.  Required.  Cardinality [1..1].
        //PV2 segment.  Required if known.  Cardinality [0..1].  Not required for ambulatory.
        //DG1 segment.  Required if known.  Cardinality [0..*].  Not required for ambulatory.
        //PR1 segment.  Optional.  Cardinality [0..*].
        oBX();
    }

    //OBX segment.  Required.  Cardinality [1..*].
    //IN1 segment.  Optional.  Cardinality [0..*].
    /**
    * Event Type segment.  Used to communicate trigger event information to receiving applications.  Guide page 38.
    */
    private void eVN() throws Exception {
        _seg = new SegmentHL7(SegmentNameHL7.EVN);
        _seg.setField(0,"EVN");
        //EVN-1 Event Type Code.  No longer used.
        _seg.SetField(2, DateTime.Now.ToString("yyyyMMddHHmmss"));
        //EVN-2 Recorded Date/Time.  Required (length 12..26).
        //EVN-3 Date/Time Planned Event.  No longer used.
        //EVN-4 Event Reason Code.  No longer used.
        //EVN-5 Operator ID.  No longer used.
        //EVN-6 Event Occurred.  No longer used.
        //EVN-7 Event Facility.  Required (length 1..241).  This is the location where the patient received treatment.
        //EVN-7.1 Namespace ID.  The name of the originating facility.
        _seg.setField(7,_sendingFacilityName,_sendingFacilityNpi,"NPI");
        //EVN-7.2 Universal ID.  Suggested value is NPI.
        //EVN-7.3 Universal ID Type.
        _msg.Segments.Add(_seg);
    }

    /**
    * Message Segment Header segment.  Required.  Defines intent, source, destination and syntax of the message.  Guide page 33.
    */
    private void mSH() throws Exception {
        //MSH-1 Field Separator (|).  Required (length 1..1).
        //MSH-2 Encoding Characters.  Required (length 4..4).  Component separator (^), then field repetition separator (~), then escape character (\), then Sub-component separator (&).
        //MSH-3 Sending Application.  Optional (length 1..227).
        //MSH-4 Sending Facility.  Required (length 1..227).  NPI is suggested, but not required.
        //MSH-5 Receiving Application.  Optional (length 1..227).  Value set HL70361.
        //MSH-6 Receiving Facility.  Optional (length 1..227).  Value set HL70362.
        //MSH-7 Date/Time of Message.  Required (length 12..26).
        //MSH-8 Security.  No longer used.
        //MSH-9 Message Type.  Required (1..15).  The guide suggests that the format is ADT_A03, but the testing tool requires ADT_A01.
        _seg = new SegmentHL7("MSH" + "|" + "^~\\&" + "|Open Dental" + "|" + _sendingFacilityName + "^" + _sendingFacilityNpi + "^NPI" + "|" + "|EHR Facility" + "|" + DateTime.Now.ToString("yyyyMMddHHmmss") + "|" + "|ADT^A01^ADT_A01" + "|OD-" + DateTime.Now.ToString("yyyyMMddHHmmss") + "-" + CodeBase.MiscUtils.createRandomAlphaNumericString(14) + "|P" + "|2.5.1" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|PH_SS-NoAck^SS Sender^2.16.840.1.114222.4.10.3^ISO");
        //MSH-10 Message Control ID.  Required (length 1..199).  Our IDs are 32 characters.
        //MSH-11 Processing ID.  Required (1..3).  P=production.
        //MSH-12 Version ID.  Required (1..5).  Must be exactly "2.5.1".
        //MSH-13 Sequence Number.  No longer used.
        //MSH-14 Continuation Pointer.  No longer used.
        //MSH-15 Accept Acknowledgement Type.  No longer used.
        //MSH-16 Application Acknowledgement Type.  No longer used.
        //MSH-17 Country Code.  No longer used.
        //MSH-18 Character Set.  No longer used.
        //MSH-19 Principal Language Of Message.  No longer used.
        //MSH-20 Alternate Character Set Handling Scheme.  No longer used.
        //MSH-21 Message Profile Identifier.  Optional in guide, but required by testing tool.
        _msg.Segments.Add(_seg);
    }

    /**
    * Observation/result segment.  Used to transmit observations related to the patient and visit.  Guide page 64.
    */
    private void oBX() throws Exception {
        List<EhrAptObs> listObservations = EhrAptObses.refresh(_appt.AptNum);
        for (int i = 0;i < listObservations.Count;i++)
        {
            EhrAptObs obs = listObservations[i];
            _seg = new SegmentHL7(SegmentNameHL7.OBX);
            _seg.setField(0,"OBX");
            _seg.SetField(1, (i + 1).ToString());
            //OBX-1 Set ID - OBX.  Required (length 1..4).  Must start at 1 and increment.
            //OBX-2 Value Type.  Required (length 1..3).  Cardinality [1..1].  Identifies the structure of data in observation value OBX-5.  Values allowed: TS=Time Stamp (Date and/or Time),TX=Text,NM=Numeric,CWE=Coded with exceptions,XAD=Address.
            if (obs.ValType == EhrAptObsType.Coded)
            {
                _seg.setField(2,"CWE");
            }
            else if (obs.ValType == EhrAptObsType.DateAndTime)
            {
                _seg.setField(2,"TS");
            }
            else if (obs.ValType == EhrAptObsType.Numeric)
            {
                _seg.setField(2,"NM");
            }
            else
            {
                //obs.ValType==EhrAptObsType.Text
                _seg.setField(2,"TX");
            }   
            //OBX-3 Observation Identifier.  Required (length up to 478).  Cardinality [1..1].  Value set is HL7 table named "Observation Identifier".  Type CE.  We use LOINC codes because the testing tool used LOINC codes and so do vaccines.
            String obsIdCode = "";
            String obsIdCodeDescript = "";
            String obsIdCodeSystem = "LN";
            if (obs.IdentifyingCode == EhrAptObsIdentifier.BodyTemp)
            {
                obsIdCode = "11289-6";
                obsIdCodeDescript = "Body temperature:Temp:Enctrfrst:Patient:Qn:";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.CheifComplaint)
            {
                obsIdCode = "8661-1";
                obsIdCodeDescript = "Chief complaint:Find:Pt:Patient:Nom:Reported";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.DateIllnessOrInjury)
            {
                obsIdCode = "11368-8";
                obsIdCodeDescript = "Illness or injury onset date and time:TmStp:Pt:Patient:Qn:";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.OxygenSaturation)
            {
                obsIdCode = "59408-5";
                obsIdCodeDescript = "Oxygen saturation:MFr:Pt:BldA:Qn:Pulse oximetry";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.PatientAge)
            {
                obsIdCode = "21612-7";
                obsIdCodeDescript = "Age Time Patient Reported";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.PrelimDiag)
            {
                obsIdCode = "44833-2";
                obsIdCodeDescript = "Diagnosis.preliminary:Imp:Pt:Patient:Nom:";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.TreatFacilityID)
            {
                obsIdCode = "SS001";
                obsIdCodeDescript = "Treating Facility Identifier";
                obsIdCodeSystem = "PHINQUESTION";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.TreatFacilityLocation)
            {
                obsIdCode = "SS002";
                obsIdCodeDescript = "Treating Facility Location";
                obsIdCodeSystem = "PHINQUESTION";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.TriageNote)
            {
                obsIdCode = "54094-8";
                obsIdCodeDescript = "Triage note:Find:Pt:Emergency department:Doc:";
            }
            else if (obs.IdentifyingCode == EhrAptObsIdentifier.VisitType)
            {
                obsIdCode = "SS003";
                obsIdCodeDescript = "Facility / Visit Type";
                obsIdCodeSystem = "PHINQUESTION";
            }
                      
            writeCE(3,obsIdCode,obsIdCodeDescript,obsIdCodeSystem);
            //OBX-4 Observation Sub-ID.  No longer used.
            //OBX-5 Observation Value.  Required if known (length 1..99999).  Value must match type in OBX-2.
            if (obs.ValType == EhrAptObsType.Address)
            {
                writeXAD(5,_sendingFacilityAddress1,_sendingFacilityAddress2,_sendingFacilityCity,_sendingFacilityState,_sendingFacilityZip);
            }
            else if (obs.ValType == EhrAptObsType.Coded)
            {
                String codeDescript = "";
                String codeSystemAbbrev = "";
                if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "LOINC"))
                {
                    Loinc loincVal = Loincs.getByCode(obs.ValReported);
                    codeDescript = loincVal.NameShort;
                    codeSystemAbbrev = "LN";
                }
                else if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "SNOMEDCT"))
                {
                    Snomed snomedVal = Snomeds.getByCode(obs.ValReported);
                    codeDescript = snomedVal.Description;
                    codeSystemAbbrev = "SCT";
                }
                else if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "ICD9"))
                {
                    ICD9 icd9Val = ICD9s.getByCode(obs.ValReported);
                    codeDescript = icd9Val.Description;
                    codeSystemAbbrev = "I9";
                }
                else if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "ICD10"))
                {
                    Icd10 icd10Val = Icd10s.getByCode(obs.ValReported);
                    codeDescript = icd10Val.Description;
                    codeSystemAbbrev = "I10";
                }
                    
                WriteCE(5, obs.ValReported.Trim(), codeDescript, codeSystemAbbrev);
            }
            else if (obs.ValType == EhrAptObsType.DateAndTime)
            {
                DateTime dateVal = DateTime.Parse(obs.ValReported.Trim());
                String strDateOut = dateVal.ToString("yyyyMMdd");
                //The testing tool threw errors when there were trailing zeros, even though technically valid.
                if (dateVal.Second > 0)
                {
                    strDateOut += dateVal.ToString("HHmmss");
                }
                else if (dateVal.Minute > 0)
                {
                    strDateOut += dateVal.ToString("HHmm");
                }
                else if (dateVal.Hour > 0)
                {
                    strDateOut += dateVal.ToString("HH");
                }
                   
                _seg.setField(5,strDateOut);
            }
            else if (obs.ValType == EhrAptObsType.Numeric)
            {
                _seg.SetField(5, obs.ValReported.Trim());
            }
            else
            {
                //obs.ValType==EhrAptObsType.Text
                _seg.setField(5,obs.ValReported);
            }    
            //OBX-6 Units.  Required if OBX-2 is NM=Numeric.  Cardinality [0..1].  Type CE.  The guide suggests value sets: Pulse Oximetry Unit, Temperature Unit, or Age Unit.  However, the testing tool used UCUM, so we will use UCUM.
            if (obs.ValType == EhrAptObsType.Numeric)
            {
                if (String.IsNullOrEmpty(obs.UcumCode))
                {
                    //If units are required but known, we must send a null flavor.
                    writeCE(6,"UNK","","NULLFL");
                }
                else
                {
                    Ucum ucum = Ucums.getByCode(obs.UcumCode);
                    writeCE(6,ucum.UcumCode,ucum.Description,"UCUM");
                } 
            }
             
            //OBX-7 References Range.  No longer used.
            //OBX-8 Abnormal Flags.  No longer used.
            //OBX-9 Probability.  No longer used.
            //OBX-10 Nature of Abnormal Test.  No longer used.
            _seg.setField(11,"F");
            //OBX-11 Observation Result Status.  Required (length 1..1).  Expected value is "F".
            //OBX-12 Effective Date of Reference Range.  No longer used.
            //OBX-13 User Defined Access Checks.  No longer used.
            //OBX-14 Date/Time of the Observation.  Optional.
            //OBX-15 Producer's ID.  No longer used.
            //OBX-16 Responsible Observer.  No longer used.
            //OBX-17 Observation Method.  No longer used.
            //OBX-18 Equipment Instance Identifier.  No longer used.
            //OBX-19 Date/Time of the Analysis.  No longer used.
            _msg.Segments.Add(_seg);
        }
    }

    /**
    * Patient Identifier segment.  Required.  Guide page 40.
    */
    private void pID() throws Exception {
        _seg = new SegmentHL7(SegmentNameHL7.PID);
        _seg.setField(0,"PID");
        _seg.setField(1,"1");
        //PID-1 Set ID - PID.  Optional.  Cardinality [0..1].  Must be "1" for the first occurrence.  Not sure why there would ever be more than one.
        //PID-2 Patient ID.  No longer used.
        //PID-3 Patient Identifier List.  Required (length up to 478).  Cardinality [1..*].  Type CX.
        _seg.SetField(3, _pat.PatNum.ToString(), "", "", "Open Dental", "MR");
        //PID-3.1 ID Number.  Required (length 1..15).
        //PID-3.2 Check Digit.  Optional (length 1..1).
        //PID-3.3 Check Digit Scheme.  No longer used.
        //PID-3.4 Assigning Authority.  Required (1..227).  Value set HL70363.
        //PID-3.5 Identifier Type Code.  Required (length 2..5).  Value set HL70203.  MR=medical record number.
        //PID-3.6 Assigning Facility.  Optional (length 1..227).
        //PID-3.7 Effective Date.  No longer used.
        //PID-3.8 Expiration Date.  No longer used.
        //PID-3.9 Assigning Jurisdiction.  No longer used.
        //PID-3.10 Assigning Facility.  No longer used.
        if (!StringSupport.equals(_pat.SSN.Trim(), ""))
        {
            _seg.RepeatField(3, _pat.SSN.Trim(), "", "", "Open Dental", "SS");
        }
         
        //PID-3.1 ID Number.  Required (length 1..15).
        //PID-3.2 Check Digit.  Optional (length 1..1).
        //PID-3.3 Check Digit Scheme.  No longer used.
        //PID-3.4 Assigning Authority.  Required (length 1..227).  Value set HL70363.
        //PID-3.5 Identifier Type Code.  Required (length 2..5).  Value set HL70203.  SS=Social Security Number.
        //PID-3.6 Assigning Facility.  Optional (length 1..227).
        //PID-3.7 Effective Date.  No longer used.
        //PID-3.8 Expiration Date.  No longer used.
        //PID-3.9 Assigning Jurisdiction.  No longer used.
        //PID-3.10 Assigning Facility.  No longer used.
        //PID-4 Alternate Patient ID.  No longer used.
        //PID-5 Patient Name.  Required (length up to 294).  Cardinality [1..*].  Type XPN.  The first repetition must contain the legal name.
        writeXPN(5,_pat.FName,_pat.LName,_pat.MiddleI,"L");
        //PID-6 Mother's Maiden Name.  No longer used.
        //PID-7 Date/Time of Birth.  Optional.  Cardinality [0..1].
        if (_pat.Birthdate.Year > 1880)
        {
            _seg.SetField(7, _pat.Birthdate.ToString("yyyyMMdd"));
        }
         
        writeGender(8,_pat.Gender);
        //PID-8 Administrative Sex.  Required if known.  Cardinality [0..1].  Value set HL70001.
        //PID-9 Patient Alias.  No longer used.
        //PID-10 Race.  Required if known.  Cardinality [0..*].  Value set HL70005.  Each race definition must be type CE.
        List<PatientRace> listPatientRaces = PatientRaces.getForPatient(_pat.PatNum);
        List<PatRace> listPatRacesFiltered = new List<PatRace>();
        boolean isHispanicOrLatino = false;
        for (int i = 0;i < listPatientRaces.Count;i++)
        {
            PatRace patRace = listPatientRaces[i].Race;
            if (patRace == PatRace.Hispanic)
            {
                isHispanicOrLatino = true;
            }
            else if (patRace == PatRace.NotHispanic)
            {
            }
            else //Nothing to do. Flag is set to false by default.
            if (patRace == PatRace.DeclinedToSpecifyRace)
            {
                listPatRacesFiltered.Clear();
                break;
            }
            else
            {
                listPatRacesFiltered.Add(patRace);
            }   
        }
        for (int i = 0;i < listPatRacesFiltered.Count;i++)
        {
            PatRace patRace = listPatRacesFiltered[i];
            String strRaceCode = "";
            String strRaceName = "";
            if (patRace == PatRace.AfricanAmerican)
            {
                strRaceCode = "2054-5";
                strRaceName = "Black or African American";
            }
            else if (patRace == PatRace.AmericanIndian)
            {
                strRaceCode = "1002-5";
                strRaceName = "American Indian or Alaska Native";
            }
            else if (patRace == PatRace.Asian)
            {
                strRaceCode = "2028-9";
                strRaceName = "Asian";
            }
            else if (patRace == PatRace.HawaiiOrPacIsland)
            {
                strRaceCode = "2076-8";
                strRaceName = "Native Hawaiian or Other Pacific Islander";
            }
            else if (patRace == PatRace.White)
            {
                strRaceCode = "2106-3";
                strRaceName = "White";
            }
            else
            {
                //Aboriginal, Other, Multiracial
                strRaceCode = "2131-1";
                strRaceName = "Other Race";
            }     
            //PID-10.1 Identifier.  Required (length 1..50).
            _seg.setOrRepeatField(10,strRaceCode,strRaceName,"CDCREC");
        }
        //PID-10.2  Text.  Required if known (length 1..999). Human readable text that is not further used.
        //PID-10.3 Name of Coding System.  Required (length 1..20).
        //PID-10.4 Alternate Identifier.  Required if known (length 1..50).
        //PID-10.5 Alternate Text.  Required if known (length 1..999).
        //PID-10.6 Name of Alternate Coding system.  Required if PID-10.4 is not blank.
        //PID-11 Patient Address.  Required if known (length up to 513).  Cardinality [0..*].  Type XAD.
        writeXAD(11,_pat.Address,_pat.Address2,_pat.City,_pat.State,_pat.Zip);
        //PID-12 County Code.  No longer used.
        //PID-13 Phone Number - Home.  No longer used.
        //PID-14 Phone Number - Business.  No longer used.
        //PID-15 Primary Language.  No longer used.
        //PID-16 Marital Status.  No longer used.
        //PID-17 Religion.  No longer used.
        //PID-18 Patient Account Number.  Optional.  However, we already sent this above.  Why do they have another field for this data?
        //PID-19 SSN Number - Patient.  No longer used.  We send SSN in PID-3.
        //PID-20 Driver's License Number - Patient.  No longer used.
        //PID-21 Mother's Identifier.  No longer used.
        //PID-22 Ethnic Group.  Required if known (length up to 478).  Cardinality [0..1].  Value set HL70189 (guide page 201).  Type CE.
        if (listPatRacesFiltered.Count > 0)
        {
            //The user specified a race and ethnicity and did not select the decline to specify option.
            if (isHispanicOrLatino)
            {
                writeCE(22,"2135-2","Hispanic or Latino","CDCREC");
            }
            else
            {
                //Not hispanic or latino.
                writeCE(22,"2186-5","not Hispanic or Latino","CDCREC");
            } 
        }
         
        //PID-23 Birth Place.  No longer used.
        //PID-24 Multiple Birth Indicator.  No longer used.
        //PID-25 Birth Order.  No longer used.
        //PID-26 Citizenship.  No longer used.
        //PID-27 Veterans Military Status.  No longer used.
        //PID-28 Nationality.  No longer used.
        //PID-29 Patient Death Date and Time.  Required if PID-30 is set to "Y" (length 12..26).  Cardinaility [0..1].
        if (_pat.DateTimeDeceased.Year > 1880)
        {
            _seg.SetField(29, _pat.DateTimeDeceased.ToString("yyyyMMddhhmmss"));
        }
         
        //PID-30 Patient Death Indicator.  Required if known.  Cardinaility [0..1].  Value set HL70136.
        if (_pat.DateTimeDeceased.Year > 1880)
        {
            _seg.setField(30,"Y");
        }
         
        //PID-31 Identity Unknown.  No longer used.
        //PID-32 Identity Reliability Code.  No longer used.
        //PID-33 Last Update Date/Time.  Optional.  Cardinaility [0..1].
        //PID-34 Last Update Facility.  Optional.  Cardinaility [0..1].
        //PID-35 Species Code.  No longer used.
        //PID-36 Breed Code.  No longer used.
        //PID-37 Strain.  No longer used.
        //PID-38 Production Class Code.  No longer used.
        //PID-39 Tribal Citizenship.  No longer used.
        _msg.Segments.Add(_seg);
    }

    /**
    * Patient Visit segment.  Used by Registration/Patient Administration applications to communicate information on a visit-specific basis.  Guide page 51.
    */
    private void pV1() throws Exception {
        _seg = new SegmentHL7(SegmentNameHL7.PV1);
        _seg.setField(0,"PV1");
        _seg.setField(1,"1");
        //PV1-1 SET ID - PV1.  Required if known (length 1..4).  Must be set to "1".
        //PV1-2 Patient Class.  Optional.
        //PV1-3 Assigned Patient Location.  Optional.
        //PV1-4 Admission Type.  Optional.
        //PV1-5 Pre-admit Number.  No longer used.
        //PV1-6 Prior Patient Location.  No longer used.
        //PV1-7 Attending Doctor.  No longer used.
        //PV1-8 Referring Doctor.  No longer used.
        //PV1-9 Consulting Doctor.  No longer used.
        //PV1-10 Hospital Service.  Optional.
        //PV1-11 Temporary Location.  No longer used.
        //PV1-12 Preadmit Test Indicator.  No longer used.
        //PV1-13 Re-admission Indicator.  No longer used.
        //PV1-14 Admit Source.  Optional.
        //PV1-15 Ambulatory Status.  Optional.
        //PV1-16 VIP Indicator.  No longer used.
        //PV1-17 Admitting Doctor.  No longer used.
        //PV1-18 Patient Type.  No longer used.
        //PV1-19 Visit Number.  Required (length up to 478).  Cardinality [1..1].
        _seg.SetField(19, _appt.AptNum.ToString(), "", "", "", "VN");
        //PV1-19.1 ID Number.  Required (length 1..15).  Unique identifier for patient visit.
        //PV1-19.2 Check Digit.  No longer used.
        //PV1-19.3 Check Digit Scheme.  No longer used.
        //PV1-19.4 Assigning Authority.  Optional.
        //PV1-19.5 Identifier Type Code.  Required (length 1..5).  Identifier type corresponding to the ID number from PV1-19.1.  VN=Visit Number.
        //PV1-19.6 Assigning Facility.  Optional.
        //PV1-19.7 Effective Date.  No longer used.
        //PV1-19.8 Expiration Date.  No longer used.
        //PV1-19.9 Assigning Jurisdiction.  No longer used.
        //PV1-19.10 Assigning Facility.  No longer used.
        //PV1-20 Financial Class.  No longer used.
        //PV1-21 Charge Price Indicator.  No longer used.
        //PV1-22 Courtesy Code.  No longer used.
        //PV1-23 Credit Rating.  No longer used.
        //PV1-24 Contract Code.  No longer used.
        //PV1-25 Contract Effective Date.  No longer used.
        //PV1-26 Contract Amount.  No longer used.
        //PV1-27 Contract Period.  No longer used.
        //PV1-28 Interest Code.  No longer used.
        //PV1-29 Transfer to Bad Debt Code.  No longer used.
        //PV1-30 Transfer to Bad Debt Date.  No longer used.
        //PV1-31 Bad Debt Agency Code.  No longer used.
        //PV1-32 Bad Debt Transfer Amount.  No longer used.
        //PV1-33 Bad Debt Recovery Amount.  No longer used.
        //PV1-34 Delete Account Indicator.  No longer used.
        //PV1-35 Delete Account Date.  No longer used.
        //PV1-36 Discharge Disposition.  Required if known (length 1..3).  Cardinality [0..1].  Discharge Disposition HL7 table.
        //PV1-37 Discharged to Location.  No longer used.
        //PV1-38 Diet Type.  No longer used.
        //PV1-39 Servicing Facility.  No longer used.
        //PV1-40 Bed Status.  No longer used.
        //PV1-41 Account Status.  No longer used.
        //PV1-42 Pending Location.  No longer used.
        //PV1-43 Prior Temporary Location.  No longer used.
        _seg.SetField(44, _appt.AptDateTime.ToString("yyyyMMddhhmm"));
        //PV1-44 Admit Date/Time.  Required (length 12..26).  Date and time of the patient presentation.
        //PV1-45 Discharge Date/Time.  Optional.
        //PV1-46 Current Patient Balance.  No longer used.
        //PV1-47 Total Charges.  No longer used.
        //PV1-48 Total Adjustments.  No longer used.
        //PV1-49 Total Payments.  No longer used.
        //PV1-50 Alternate Visit ID.  No longer used.
        //PV1-51 Visit Indicator.  No longer used.
        //PV1-52 Other Healthcare Provider.  No longer used.
        _msg.Segments.Add(_seg);
    }

    public String generateMessage() throws Exception {
        return _msg.toString();
    }

    /**
    * Type CE.  Writes a coded element into the fieldIndex field of the current segment.
    */
    private void writeCE(int fieldIndex, String strIdentifier, String strText, String strNameCodeSystem) throws Exception {
        //CE.1 Identifier.  Required (length 1..20).
        //CE.2 Text.  Required if known (length 1..199).  Standardized description associated with CE.1.
        _seg.setField(fieldIndex,strIdentifier,strText,strNameCodeSystem);
    }

    //CE.3 Name of Coding System.  Required (length 1..20).
    //CE.4 Alternate Identifier.  No longer used.
    //CE.5 Alternate Text.  No longer used.
    //CE.6 Name of Alternate Coding system.  No longer used.
    /**
    * Type IS.  Writes a string corresponding to table HL70001 into the fieldIndex field for the current segment.
    */
    private void writeGender(int fieldIndex, PatientGender gender) throws Exception {
        String strGenderCode = "U";
        //unknown
        if (gender == PatientGender.Female)
        {
            strGenderCode = "F";
        }
         
        if (gender == PatientGender.Male)
        {
            strGenderCode = "M";
        }
         
        _seg.setField(fieldIndex,strGenderCode);
    }

    /**
    * Type XAD.  Writes an extended address into the fieldIndex field for the current segment.
    */
    private void writeXAD(int fieldIndex, String address1, String address2, String city, String state, String zip) throws Exception {
        //XAD.1 Street Address.  Optional (length 1..184).  Data type SAD.  The SAD type only requires the first sub-component.
        //XAD.2 Other Designation.  Optional (length 1..120).
        //XAD.3 City.  Optional (length 1..50).
        //XAD.4 State or Province.  Optional (length 1..50).
        _seg.setField(fieldIndex,address1,address2,city,state,zip,"USA","C");
    }

    //XAD.5 Zip or Postal Code.  Required if known (length 1..12).
    //XAD.6 Country.  Optional (length 3..3).  Value set HL70399.  Defaults to USA.
    //XAD.7 Address Type.  Required (length 1..3).  Value set HL70190.  Value "C" is expected.
    //XAD.8 Other Geographic Designation.  Optional.
    //XAD.9 County/Parish Code.  Required if known.
    //XAD.10 Census Tract.  No longer used.
    //XAD.11 Address Representation Code.  No longer used.
    //XAD.12 Address Validity Range.  No longer used.
    //XAD.13 Effective Date.  No longer used.
    //XAD.14 Expiration Date.  No longer used.
    /**
    * Type XPN (guide page 82).  Writes an person's name into the fieldIndex field for the current segment.
    * The fName and lName cannot be blank.
    * The middleI may be blank.
    * nameTypeCode can be one of: L=Legal Name,S=Pseudonym,U=Unspecified.
    */
    private void writeXPN(int fieldIndex, String fName, String lName, String middleI, String nameTypeCode) throws Exception {
        if (StringSupport.equals(fName.Trim(), "") && StringSupport.equals(lName.Trim(), ""))
        {
            nameTypeCode = "U";
        }
         
        //Reporting the patient as unspecified is recommended by the guide.
        //XPN.1 Family Name.  Optinal (length 1..194).  Type FN (guide page 64).  Cardinality [1..1].  The FN type only requires the last name field and it is the first field.
        //XPN.2 Given Name.  Optional (length 1..30).  Cardinality [1..1].
        //XPN.3 Second and Further Given Names or Initials Thereof (middle name).  Optional (length 1..30).
        //XPN.4 Suffix.  Optional.
        //XPN.5 Prefix.  Optional.
        //XPN.6 Degree.  No longer used.
        _seg.SetField(fieldIndex, lName.Trim(), fName.Trim(), middleI, "", "", "", nameTypeCode);
    }

    //XPN.7 Name Type Code.  Required if known (length 1..1).  Value set HL70200 (guide page 203).  L=Legal Name,S=Pseudonym,U=Unspecified.
    //XPN.8 Name Representation Code.  No longer used.
    //XPN.9 Name Context.  No longer used.
    //XPN.10 Name Validity Range.  No longer used.
    //XPN.11 Name Assembly Order.  No longer used.
    //XPN.12 Effective Date.  No longer used.
    //XPN.13 Expiration Date.  No longer used.
    //XPN.14 Professional Suffix.  No longer used.
    public static String validate(Appointment appt) throws Exception {
        StringBuilder sb = new StringBuilder();
        Provider provFacility = Providers.GetProv(PrefC.getInt(PrefName.PracticeDefaultProv));
        if (!Regex.IsMatch(provFacility.NationalProvID, "^(80840)?[0-9]{10}$"))
        {
            writeError(sb,"Invalid NPI for provider '" + provFacility.Abbr + "'");
        }
         
        if (!PrefC.getBool(PrefName.EasyNoClinics) && appt.ClinicNum != 0)
        {
            //Using clinics and a clinic is assigned.
            Clinic clinic = Clinics.getClinic(appt.ClinicNum);
            if (StringSupport.equals(clinic.Description, ""))
            {
                writeError(sb,"Missing clinic description for clinic attached to appointment.");
            }
             
        }
        else
        {
            //Not using clinics for this patient
            if (StringSupport.equals(PrefC.getString(PrefName.PracticeTitle), ""))
            {
                writeError(sb,"Missing practice title.");
            }
             
        } 
        Patient pat = Patients.getPat(appt.PatNum);
        if (pat.PatStatus == PatientStatus.Deceased && pat.DateTimeDeceased.Year < 1880)
        {
            writeError(sb,"Missing date time deceased.");
        }
         
        List<EhrAptObs> listObservations = EhrAptObses.refresh(appt.AptNum);
        for (int i = 0;i < listObservations.Count;i++)
        {
            EhrAptObs obs = listObservations[i];
            if (obs.ValType == EhrAptObsType.Coded)
            {
                if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "LOINC"))
                {
                    Loinc loincVal = Loincs.getByCode(obs.ValReported);
                    if (loincVal == null)
                    {
                        writeError(sb,"Loinc code not found '" + loincVal.LoincCode + "'.  Please add by going to Setup | EHR.");
                    }
                     
                }
                else if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "SNOMEDCT"))
                {
                    Snomed snomedVal = Snomeds.getByCode(obs.ValReported);
                    if (snomedVal == null)
                    {
                        writeError(sb,"Snomed code not found '" + snomedVal.SnomedCode + "'.  Please add by going to Setup | EHR.");
                    }
                     
                }
                else if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "ICD9"))
                {
                    ICD9 icd9Val = ICD9s.getByCode(obs.ValReported);
                    if (icd9Val == null)
                    {
                        writeError(sb,"ICD9 code not found '" + icd9Val.ICD9Code + "'.  Please add by going to Setup | EHR.");
                    }
                     
                }
                else if (StringSupport.equals(obs.ValCodeSystem.Trim().ToUpper(), "ICD10"))
                {
                    Icd10 icd10Val = Icd10s.getByCode(obs.ValReported);
                    if (icd10Val == null)
                    {
                        writeError(sb,"ICD10 code not found '" + icd10Val.Icd10Code + "'.  Please add by going to Setup | EHR.");
                    }
                     
                }
                    
            }
            else if (obs.ValType == EhrAptObsType.Numeric && !StringSupport.equals(obs.UcumCode, ""))
            {
                //We only validate the ucum code if it will be sent out.  Blank units allowed.
                Ucum ucum = Ucums.getByCode(obs.UcumCode);
                if (ucum == null)
                {
                    writeError(sb,"Invalid unit code '" + obs.UcumCode + "' for observation (must be UCUM code).");
                }
                 
            }
              
        }
        return sb.ToString();
    }

    private static void writeError(StringBuilder sb, String message) throws Exception {
        if (sb.Length > 0)
        {
            sb.Append("\r\n");
        }
         
        sb.Append(message);
    }

}


