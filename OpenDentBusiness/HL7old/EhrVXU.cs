﻿using System;
using System.Collections.Generic;
using System.Text;
using OpenDentBusiness;

namespace OpenDentBusiness.HL7 {
	///<summary>A VXU message is an Unsolicited Vaccination Record Update.  It is a message sent out from Open Dental detailing vaccinations that were given.
	///Implementation based on HL7 version 2.5.1 Immunization Messaging Release 1.4 08/01/2012.  Data types defined on page 52.
	///To view specific HL7 table definitions, see http://hl7.org/implement/standards/fhir/terminologies-v2.html. </summary>
	public class EhrVXU {

		///<summary>Set in constructor and must not be modified.</summary>
		private Patient _pat;
		///<summary>Set in constructor and must not be modified.</summary>
		private List<VaccinePat> _vaccines;
		///<summary>The entire message object after it is successfully built.</summary>
		private MessageHL7 _msg;
		///<summary>Helper variable.</summary>
		private SegmentHL7 _seg;
		///<summary>A variable which is used in multiple places but always has the same value. At class level for convenience.</summary>
		private string cityWhereEntered="";
		///<summary>A variable which is used in multiple places but always has the same value. At class level for convenience.</summary>
		private string stateWhereEntered="";

		///<summary>Creates the Message object and fills it with data.  Vaccines must all be for the same patient.  A list of vaccines is passed in so the user can select a subset of vaccines to send for the patient.</summary>
		public EhrVXU(Patient pat,List<VaccinePat> vaccines) {
			if(vaccines.Count==0) {
				throw new ApplicationException("Must be at least one vaccine.");
			}
			_pat=pat;
			_vaccines=vaccines;
			InitializeVariables();
			BuildMessage();
		}

		private void InitializeVariables() {
			cityWhereEntered=PrefC.GetString(PrefName.PracticeCity);
			stateWhereEntered=PrefC.GetString(PrefName.PracticeST);
			if(_pat.ClinicNum!=0) {
				Clinic clinic=Clinics.GetClinic(_pat.ClinicNum);
				cityWhereEntered=clinic.City;
				stateWhereEntered=clinic.State;
			}
		}
		
		private void BuildMessage() {
			_msg=new MessageHL7(MessageTypeHL7.VXU);//Message format for VXU is on guide page 160.
			MSH();//MSH segment. Required.  Cardinality [1..1].
			//SFT segment. Optional. Cardinality [0..*].  Undefined and may be locally specified.
			PID();//PID segment.  Required.  Cardinality [1..1].
			PD1();//PD1 segment.  Required if known.  Cardinality [0..1].
			NK1();//NK1 segment.  Required if known.  Cardinality [0..1].
			//PV1 segment.  Optional.  Cardinality [0..1].  Undefined and may be locally specified.
			//PV2 segment.  Optional.  Cardinality [0..1].  Undefined and may be locally specified.
			//GT1 segment.  Optional.  Cardinality [0..*].  Undefined and may be locally specified.
			//Begin Insurance group.  Optional.  Cardinality [0..*].
			//IN1 segment.  Optional.  Cardinality [0..1].  Undefined and may be locally specified.  Guide page 102.
			//IN2 segment.  Optional.  Cardinality [0..1].  Undefined and may be locally specified.  Guide page 102.
			//IN3 segment.  Optional.  Cardinality [0..1].  Undefined and may be locally specified.  Guide page 102.
			//End Insurance group.
			//Begin Order group.
			for(int i=0;i<_vaccines.Count;i++) {
				ORC(_vaccines[i]);//ORC segment.  Required.  Cardinality [1..1].
				//TQ1 segment.  Optional.  Cardinality [0..1]. Undefined and may be locally specified.
				//TQ2 segment.  Optional.  Cardinality [0..1]. Undefined and may be locally specified.
				RXA(_vaccines[i]);//RXA segment.  Required.  Cardinality [1..1].
				RXR(_vaccines[i]);//RXR segment.  Required if known.  Cardinality [0..1].
				OBX();//OBX segment.  Required if known.  Cardinality [0..*].
				NTE();//NTE segment.  Required if known.  Cardinality [0..1].
			}
			//End Order group.
		}
		
		///<summary>Message Segment Header segment.  Required.  Defines intent, source, destination and syntax of the message.  Guide page 104.</summary>
		private void MSH() {
			string strSendingFacilityName=PrefC.GetString(PrefName.PracticeTitle);
			if(_pat.ClinicNum!=0) {
				Clinic clinic=Clinics.GetClinic(_pat.ClinicNum);
				strSendingFacilityName=clinic.Description;
			}
			_seg=new SegmentHL7("MSH"
				+"|"//MSH-1 Field Separator (|).  Required (length 1..1).
				+@"^~\&"//MSH-2 Encoding Characters.  Required (length 4..4).  Component separator (^), then field repetition separator (~), then escape character (\), then Sub-component separator (&).
				+"|Open Dental"//MSH-3 Sending Application.  Required if known (length unspecified).  Value set HL70361  (guide page 229, "no suggested values defined").  Type HD (guide page 65).
				+"|"+strSendingFacilityName//MSH-4 Sending Facility.  Required if known (length unspecified).  Value set HL70362 (guide page 229, "no suggested values defined").  Type HD (guide page 65).
				+"|"//MSH-5 Receiving Application.  Required if known (length unspecified).  Value set HL70361 (guide page 229, "no suggested values defined").  Type HD (guide page 65).
				+"|"//MSH-6 Receiving Facility.  Required if known (length unspecified).  Value set HL70362 (guide page 229, "no suggested values defined").  Type HD (guide page 65).  TODO: We need a UI where user can type this in when sending.
				+"|"+DateTime.Now.ToString("yyyyMMddHHmmss")//MSH-7 Date/Time of Message.  Required (length 12..19).
				+"|"//MSH-8 Security.  Optional (length unspecified).
				+"|VXU^VO4^VXU_V04"//MSH-9 Message Type. Required (length unspecified).
				+"|OD-"+DateTime.Now.ToString("yyyyMMddHHmmss")+"-"+CodeBase.MiscUtils.CreateRandomAlphaNumericString(14)//MSH-10 Message Control ID.  Required (length 1..199).  Our IDs are 32 characters.
				+"|P"//MSH-11 Processing ID.  Required (length unspecified).  P=production.
				+"|2.5.1"//MSH-12 Version ID.  Required (length unspecified).  Must be exactly "2.5.1".
				+"|"//MSH-13 Sequence Number.  Optional (length unspecified).
				+"|"//MSH-14 Continuation Pointer.  Optional (length unspecified).
				+"|NE"//MSH-15 Accept Acknowledgement Type.  Required if known (length unspecified).  Value set HL70155 (AL=Always, ER=Error/rejection conditions only, NE=Never, SU=Successful completion only).
				+"|NE"//MSH-16 Application Acknowledgement Type.  Required if known (length unspecified).  Value set HL70155 (AL=Always, ER=Error/rejection conditions only, NE=Never, SU=Successful completion only).
				//MSH-17 Country Code.  Optional (length unspecified).  Value set HL70399.  Default value is USA.
				//MSH-18 Character Set.  Optional (length unspecified).
				//MSH-19 Principal Language Of Message.  Optional (length unspecified).
				//MSH-20 Alternate Character Set Handling Scheme.  Optional (length unspecified).
				//MSH-21 Message Profile Identifier.  Required if MSH9 component 1 is set to "QBP" or "RSP.  In our case, this field is not required.
			);
			_msg.Segments.Add(_seg);
		}

		///<summary>Next of Kin segment.  Required if known.  Guide page 111.</summary>
		private void NK1() {
			List<Guardian> listGuardians=Guardians.Refresh(_pat.PatNum);
			for(int i=0;i<listGuardians.Count;i++) {//One NK1 segment for each relationship.
				_seg=new SegmentHL7(SegmentNameHL7.NK1);
				_seg.SetField(0,"NK1");
				_seg.SetField(1,i.ToString());//NK1-1 Set ID.  Required (length unspecified).  Cardinality [1..1].
				//NK-2 Name.  Required (length unspecified).  Type XPN (guide page 82).  Cardinarlity [1..1].
				Patient patNextOfKin=Patients.GetPat(listGuardians[i].PatNumGuardian);
				WriteXPN(2,patNextOfKin.FName,patNextOfKin.LName,patNextOfKin.MiddleI);
				//NK1-3 Relationship.  Required.  Cardinality [1..1].  Value set HL70063 (guide page 196).  Type CE.
				GuardianRelationship relat=listGuardians[i].Relationship;
				string strRelatCode="";
				if(relat==GuardianRelationship.Brother) {
					strRelatCode="BRO";
				}
				else if(relat==GuardianRelationship.CareGiver) {
					strRelatCode="CGV";
				}
				else if(relat==GuardianRelationship.Child) {
					strRelatCode="CHD";//This relationship type is not documented in the guide, but it is defined as part of HL7 0063 in a more recent publication.  We added because it seemed like a basic relationship type.
				}
				else if(relat==GuardianRelationship.Father) {
					strRelatCode="FTH";
				}
				else if(relat==GuardianRelationship.FosterChild) {
					strRelatCode="FCH";
				}
				else if(relat==GuardianRelationship.Friend) {
					strRelatCode="FND";//This relationship type is not documented in the guide, but it is defined as part of HL7 0063 in a more recent publication.  We added because it seemed like a basic relationship type.
				}
				else if(relat==GuardianRelationship.Grandchild) {
					strRelatCode="GCH";//This relationship type is not documented in the guide, but it is defined as part of HL7 0063 in a more recent publication.  We added because it seemed like a basic relationship type.
				}
				else if(relat==GuardianRelationship.Grandfather) { //This status is from our older guardian implementation.
					strRelatCode="GRD";//Grandparent
				}
				else if(relat==GuardianRelationship.Grandmother) { //This status is from our older guardian implementation.
					strRelatCode="GRD";//Grandparent
				}
				else if(relat==GuardianRelationship.Grandparent) {
					strRelatCode="GRD";
				}
				else if(relat==GuardianRelationship.Guardian) {
					strRelatCode="GRD";
				}
				else if(relat==GuardianRelationship.LifePartner) {
					strRelatCode="DOM";//This relationship type is not documented in the guide, but it is defined as part of HL7 0063 in a more recent publication.  We added because it seemed like a basic relationship type.
				}
				else if(relat==GuardianRelationship.Mother) {
					strRelatCode="MTH";
				}
				else if(relat==GuardianRelationship.Other) {
					strRelatCode="OTH";
				}
				else if(relat==GuardianRelationship.Parent) {
					strRelatCode="PAR";
				}
				else if(relat==GuardianRelationship.Self) {
					strRelatCode="SEL";
				}
				else if(relat==GuardianRelationship.Sibling) {
					strRelatCode="SIB";
				}
				else if(relat==GuardianRelationship.Sister) {
					strRelatCode="SIS";
				}
				else if(relat==GuardianRelationship.Sitter) { //This status is from our older guardian implementation.
					strRelatCode="CGV";//Caregiver
				}
				else if(relat==GuardianRelationship.Spouse) {
					strRelatCode="SPO";
				}
				else if(relat==GuardianRelationship.Stepchild) {
					strRelatCode="SCH";
				}
				else if(relat==GuardianRelationship.Stepfather) { //This status is from our older guardian implementation.
					strRelatCode="PAR";//parent
				}
				else if(relat==GuardianRelationship.Stepmother) { //This status is from our older guardian implementation.
					strRelatCode="PAR";//parent
				}
				else {
					continue;//Skip the entire segment if the relationship is not known.
				}
				WriteCE(3,strRelatCode,relat.ToString(),"HL70063");
				//NK-4 Address.  Required if known (length unspecified).  Cardinality [0..1].  Type XAD (guide page 74).  The first instance must be the primary address.
				WriteXAD(4,patNextOfKin.Address,patNextOfKin.Address2,patNextOfKin.City,patNextOfKin.State,patNextOfKin.Zip);
				//NK-5 Phone Number.  Required if known.  Cardinality [0..*].  Type XTN (guide page 84).  The first instance shall be the primary phone number.
				WriteXTN(5,"PRN","PH",patNextOfKin.HmPhone,patNextOfKin.WirelessPhone);
				//NK-6 Business Phone Number.  Optional.  Type XTN (guide page 84).
				WriteXTN(6,"PRN","PH",patNextOfKin.WkPhone);
				//NK-7 Contact Role.  Optional.
				//NK-8 Start Date.  Optional.
				//NK-9 End Date.  Optional.
				//NK-10 Next of Kin/Associated Parties Job Title.  Optional.
				//NK-11 Next of Kin/Associated Parties Job Code/Class.  Optional.
				//NK-12 Next of Kin/Associated Parties Employee Number.  Optional.
				//NK-13 Organization Name - NK1.  Optional.
				//NK-14 Marital Status.  Optional.
				//NK-15 Administrative Sex.  Optional.
				//NK-16 Date/Time of Birth.  Optional.
				//NK-17 Living Dependency.  Optional.
				//NK-18 Ambulatory Status.  Optional.
				//NK-19 Citizenship.  Optional.
				//NK-20 Primary Language.  Optional.
				//NK-21 Living Arrangement.  Optional.
				//NK-22 Publicity Code.  Optional.
				//NK-23 Protection Indicator.  Optional.
				//NK-24 Student Indicator.  Optional.
				//NK-25 Religion.  Optional.
				//NK-26 Mother's Maiden Name.  Optional.
				//NK-27 Nationality.  Optional.
				//NK-28 Ethnic Group.  Optional.
				//NK-29 Contact Reason.  Optional.
				//NK-30 Contact Person's Name.  Optional.
				//NK-31 Contact Person's Telephone Number.  Optional.
				//NK-32 Contact Person's Address.  Optional.
				//NK-33 Next of Kin/Associated Party's Identifiers.  Optional.
				//NK-34 Job Status.  Optional.
				//NK-35 Race.  Optional.
				//NK-36 Handicap.  Optional.
				//NK-37 Contact Person Social Security Number.  Optional.
				//NK-38 Next of Kin Birth Place.  Optional.
				//NK-39 VIP Indicator.  Optional.
			}
			_msg.Segments.Add(_seg);
		}

		///<summary>Note segment.  Required if known.  Guide page 116.</summary>
		private void NTE() {
		}

		///<summary>Observation Result segment.  Required if known.  The basic format is question and answer.  Guide page 116.</summary>
		private void OBX() {
			int i=0;//TODO: Create a loop with variable i, once there is a list to loop through.
			_seg=new SegmentHL7(SegmentNameHL7.OBX);
			_seg.SetField(0,"OBX");
			_seg.SetField(1,i.ToString());//OBX-1 Set ID - OBX.  Required (length 1..4).  Cardinality [1..1].
			_seg.SetField(2,"CE");//OBX-2 Value Type.  Required (length 2..3).  Cardinality [1..1].  Value Set HL70125 (constrained, not in guide).  CE=Coded Entry,DT=Date,NM=Numberic,ST=String,TS=Time Stamp (Date & Time).  TODO: We need UI for this.
			//OBX-3 Observation Identifier.  Required.  Cardinality [1..1].  Value set NIP003 (25 items).  Type CE.  Purpose is to pose the question that is answered by OBX-5.  TODO: We need UI for this.
			WriteCE(3,"","","LN");
			//OBX-4 Observation Sub-ID.  Required (length 1..20).  Cardinality [1..1].  We need UI for this.
			//OBX-5 Observation Value.  Required. Cardinality [1..1].  Value set varies, depending on the value of OBX-2 (Use type CE if OBX-2 is "CE", otherwise treat as a string).  Purpose is to answer the quesiton posed by OBX-3.  TODO: UI needed.
			//OBX-6 Units.  Required if OBX-2 is "NM" or "SN".
			//OBX-7 References Range.  Optional.
			//OBX-8 Abnormal Flags.  Optional.
			//OBX-9 Probability.  Optional.
			//OBX-10 Nature of Abnormal Test.  Optional.
			_seg.SetField(11,"F");//OBX-11 Observation Result Status.  Required (length 1..1).  Cardinality [1..1].  Value set HL70085 (constrained, guide page 198).  We are expected to use value F=Final.
			//OBX-12 Effective Date of Reference Range Values.  Optional.
			//OBX-13 User Defined Access Checks.  Optional.
			//OBX-14 Date/Time of the Observation.  Required if known.  Cardinality [0..1].  TODO: UI needed.
			//OBX-15 Producer's Reference.  Optional.
			//OBX-16 Responsible Observer.  Optional.
			//OBX-17 Observation Method.  Required if OBX-3.1 is “64994-7”.  Value set CDCPHINVS. Type CE.  TODO: UI needed.
			//OBX-18 Equipment Instance Identifier.  Optional.
			//OBX-19 Date/Time of the Analysis.  Optional.
			//OBX-20 Reserved for harmonization with V2.6.  Optional.
			//OBX-21 Reserved for harmonization with V2.6.  Optional.
			//OBX-22 Reserved for harmonization with V2.6.  Optional.
			//OBX-23 Performing Organization Name.  Optional.
			//OBX-24 Performing Organization Address.  Optional.
			//OBX-25 Performing Organization Medical Director.  Optional.
			_msg.Segments.Add(_seg);
		}
		
		///<summary>Order Request segment.  Required.  Guide page 126.</summary>
		private void ORC(VaccinePat vaccine) {
			_seg=new SegmentHL7(SegmentNameHL7.ORC);
			_seg.SetField(0,"ORC");
			_seg.SetField(1,"RE");//ORC-1 Order Control.  Required (length 2).  Cardinality [1..1].  Value set HL70119.  The only allowed value is "RE".
			//ORC-2 Placer Order Number.  Required if known.  Cardinality [0..1].  Type EI (guid page 62).
			//ORC-3 Filler Order Number.  Required.  Cardinality [0..1].  Type EI (guid page 62).  "Shall be the unique ID of the sending system."  The city and state are used to record the region where the vaccine record was filled.
			WriteEI(3,vaccine.VaccinePatNum.ToString(),cityWhereEntered,stateWhereEntered);
			//ORC-4 Placer Group Number.  Optional.
			//ORC-5 Order Status.  Optional.
			//ORD-6 Response Flag.  Optional.
			//ORD-7 Quantity/Timing.  No longer used.
			//ORD-8 Parent.  Optional.
			//ORD-9 Date/Time of Transaction.  Optional.
			//ORD-10 Entered By.  Required if known.  Cardinality [0..1].  Type XCN.  This is the person that entered the immunization record into the system.  TODO: We need UI for user entered by (maintain city/state logic).
			string fName="";
			string lName="";
			string middleI="";
			if(Security.CurUser.EmployeeNum!=0) {
				Employee employee=Employees.GetEmp(Security.CurUser.EmployeeNum);
				fName=employee.FName;
				lName=employee.LName;
				middleI=employee.MiddleI;
			}
			WriteXCN(10,fName,lName,middleI,Security.CurUser.UserNum.ToString(),cityWhereEntered,stateWhereEntered);
			//ORD-11 Verified By.  Optional.
			//ORD-12 Ordering Provider.  Required if known. Cardinality [0..1].  Type XCN.  This shall be the provider ordering the immunization.  It is expected to be empty if the immunization record is transcribed from a historical record.
			//TODO: We need provider picker UI for ordering provider.
			long provNumOrdering=Security.CurUser.ProvNum;
			if(provNumOrdering==0) {
				provNumOrdering=_pat.PriProv;
			}
			if(provNumOrdering!=0) {
				Provider provOrdering=Providers.GetProv(provNumOrdering);
				WriteXCN(12,provOrdering.FName,provOrdering.LName,provOrdering.MI,provNumOrdering.ToString(),cityWhereEntered,stateWhereEntered);
			}
			//ORD-13 Enterer's Location.  Optional.
			//ORD-14 Call Back Phone Number.  Optional.
			//ORD-15 Order Effective Date/Time.  Optional.
			//ORD-16 Order Control Code Reason.  Optional.
			//ORD-17 Entering Organization.  Optional.
			//ORD-18 Entering Device.  Optional.
			//ORD-19 Action By.  Optional.
			//ORD-20 Advanced Beneficiary Notice Code.  Optional.
			//ORD-21 Ordering Facility Name.  Optional.
			//ORD-22 Ordering Facility Address.  Optional.
			//ORD-23 Ordering Facility.  Optional.
			//ORD-24 Order Provider Address.  Optional.
			//ORD-25 Order Status Modifier.  Optional.
			//ORD-26 Advanced Beneficiary Notice Override Reason.  Optional.
			//ORD-27 Filler's Expected Availability Date/Time.  Optional.
			//ORD-28 Confidentiality Code.  Optional.
			//ORD-29 Order Type.  Optional.
			//ORD-30 Enterer Authorization Mode.  Optional.
			//ORD-31 Parent Universal Service Modifier.  Optional.
			_msg.Segments.Add(_seg);
		}

		///<summary>Patient Demographic segment.  Required if known.  Additional demographics.  Guide page 132.</summary>
		private void PD1() {
			_seg=new SegmentHL7(SegmentNameHL7.PD1);
			_seg.SetField(0,"PD1");
			//PD1-1 Living Dependency.  Optional.  Cardinality [0..1].
			//PD1-2 Living Arrangement.  Optional.  Cardinality [0..1].
			//PD1-3 Patient Primary Facility.  Optional.  Cardinality [0..1].
			//PD1-4 Patient Primary Care Provider Name & ID Number.  Optional.  Cardinality [0..1].
			//PD1-5 Student Indicator.  Optional.  Cardinality [0..1].
			//PD1-6 Handicap.  Optional.  Cardinality [0..1].
			//PD1-7 Living Will Code.  Optional.  Cardinality [0..1].
			//PD1-8 Organ Donor Code.  Optional.  Cardinality [0..1].
			//PD1-9 Separate Bill.  Optional.  Cardinality [0..1].
			//PD1-10 Duplicate Patient.  Optional.  Cardinality [0..1].
			//PD1-11 Publicity Code.  Required if known (length 2..2).  Cardinality [0..1].  Value set HL70215 (guide page 209).  Type CE.
			if(_pat.PreferRecallMethod==ContactMethod.DoNotCall) {
				WriteCE(11,"07","Recall only - no calls","HL70215");
			}
			else if(_pat.PreferRecallMethod==ContactMethod.None) {
				WriteCE(11,"01","No reminder/recall","HL70215");
			}
			else {
				WriteCE(11,"02","Reminder/recall - any method","HL70215");
			}
			//PD1-12 Protection Indicator.  Required if known (length 1..1).  Cardinality [0..1].  Value set HL70136 (guide page 199).  Allowed values are "Y" for yes, "N" for no, or blank for unknown.
			//PD1-13 Protection Indicator.  Required if PD1-12 is not blank (length unspecified).  Cardinality [0..1].
			//PD1-14 Place of Worship.  Optional (length unspecified).  Cardinality [0..1].
			//PD1-15 Advance Directive Code.  Optional (length unspecified).  Cardinality [0..1].
			_seg.SetField(16,"A");//PD1-16 Immunization Registry Status.  Required if known (length unspecified).  Cardinality [0..1].  Value set HL70441 (guide page 232).  TODO: May need UI for this field.
			//PD1-17 Immunization Registry Status Effective Date.  Required if PD1-16 is not blank.  Cardinality [0..1].
			_seg.SetField(17,DateTime.Today.ToString("yyyyMMdd"));
			//PD1-18 Publicity Code Effective Date.  Required if PD1-11 is not blank.
			_seg.SetField(18,DateTime.Today.ToString("yyyyMMdd"));
			//PD1-19 Military Branch.  Optional.
			//PD1-20 Military Rank/Grade.  Optional.
			//PD1-21 Military Status.  Optional.
			_msg.Segments.Add(_seg);
		}

		///<summary>Patient Identifier segment.  Required.  Guide page 137.</summary>
		private void PID() {
			_seg=new SegmentHL7(SegmentNameHL7.PID);
			_seg.SetField(0,"PID");
			_seg.SetField(1,"1");//PID-1 Set ID - PID.  Required if known.  Cardinality [0..1].  Must be "1" for the first occurrence.  Not sure why there would ever be more than one.
			//PID-2 Patient ID.  No longer used.
			_seg.SetField(3,//PID-3 Patient Identifier List.  Required.  Cardinality [1..*].  Type CX (see guide page 58 for type definition).
				_pat.PatNum.ToString(),//PID-3.1 ID Number.  Required (length 1..15).  
				"",//PID-3.2 Check Digit.  Optional (length 1..1).
				"",//PID-3.3 Check Digit Scheme.  Required if PID-3.2 is specified.  Not required for our purposes.  Value set HL70061.
				"Open Dental",//PID-3.4 Assigning Authority.  Required.  Value set HL70363.
				"MR"//PID-3.5 Identifier Type Code.  Required (length 2..5).  Value set HL70203.  MR=medical record number.
				//PID-3.6 Assigning Facility.  Optional (length undefined).
				//PID-3.7 Effective Date.  Optional (length 4..8).
				//PID-3.8 Expiration Date.  Optional (length 4..8).
				//PID-3.9 Assigning Jurisdiction.  Optional (length undefined).
				//PID-3.10 Assigning Agency or Department.  Optional (length undefined).
			);
			//PID-4 Alternate Patient ID - 00106.  No longer used.
			WriteXPN(5,_pat.FName,_pat.LName,_pat.MiddleI);//PID-5 Patient Name.  Required (length unspecified).  Cardinality [1..*].  Type XPN.  The first repetition must contain the legal name.
			//WriteXPN(6,);//PID-6 Mother's Maiden Name.  Required if known (length unspecified).  Cardinality [0..1].  Type XPN.  TODO: We need a textbox in the patient edit window to enter this information.
			//PID-7 Date/Time of Birth.  Required.  Cardinality [1..1].  We must specify "UNK" if unknown.
			if(_pat.Birthdate.Year<1880) {
				_seg.SetField(7,"UNK");
			}
			else {
				_seg.SetField(7,_pat.Birthdate.ToString("yyyyMMdd"));
			}
			WriteGender(8,_pat.Gender);//PID-8 Administrative Sex.  Required if known.  Cardinality [0..1].  Value set HL70001.
			//PID-9 Patient Alias.  No longer used.
			//PID-10 Race.  Required if known.  Cardinality [0..*].  Value set HL70005 (guide page 194).  Each race definition must be type CE.  Type CE is defined on guide page 53.
			List<PatientRace> listPatientRaces=PatientRaces.GetForPatient(_pat.PatNum);
			List<PatRace> listPatRacesFiltered=new List<PatRace>();
			bool isHispanicOrLatino=false;
			for(int i=0;i<listPatientRaces.Count;i++) {
				PatRace patRace=listPatientRaces[i].Race;
				if(patRace==PatRace.Hispanic) {
					isHispanicOrLatino=true;
				}
				else if(patRace==PatRace.NotHispanic) {
					//Nothing to do. Flag is set to false by default.
				}
				else if(patRace==PatRace.DeclinedToSpecify) {
					listPatRacesFiltered.Clear();
					break;
				}
				listPatRacesFiltered.Add(patRace);
			}
			string hl7Race="";//TODO: Test a patient with multiple races.
			if(listPatRacesFiltered.Count==0) {//No selection or declined to specify.
				hl7Race+=""//PID-10.1 Identifier.  Required (length 1..50).  Blank for unknown.
					+"^Unknown/undetermined"//PID-10.2  Text.  Required if known (length 1..999). Human readable text that is not further used.
					+"^Race and Ethnicity";//PID-10.3 Name of Coding System.  Required (length 1..20).  The full name is actually "Race &amp; Ethnicity - CDC", but it is more than 20 characters.
					//PID-10.4 Alternate Identifier.  Required if known (length 1..50).
					//PID-10.5 Alternate Text.  Required if known (length 1..999).
					//PID-10.6 Name of Alternate Coding system.  Required if PID-10.4 is not blank.
			}
			else {
				for(int i=0;i<listPatRacesFiltered.Count;i++) {
					if(i>0) {
						hl7Race+="~";//field repetition separator
					}
					PatRace patRace=listPatRacesFiltered[i];
					string strRaceCode="";
					string strRaceName="";
					if(patRace==PatRace.AfricanAmerican) {
						strRaceCode="2054-5";
						strRaceName="Black or African American";
					}
					else if(patRace==PatRace.AmericanIndian) {
						strRaceCode="1002-5";
						strRaceName="American Indian or Alaska Native";
					}
					else if(patRace==PatRace.Asian) {
						strRaceCode="2028-9";
						strRaceName="Asian";
					}
					else if(patRace==PatRace.HawaiiOrPacIsland) {
						strRaceCode="2076-8";
						strRaceName="Native Hawaiian or Other Pacific Islander";
					}
					else if(patRace==PatRace.White) {
						strRaceCode="2106-3";
						strRaceName="White";
					}
					else {//Aboriginal, Other, Multiracial
						strRaceCode="2131-1";
						strRaceName="Other Race";
					}
					hl7Race+=strRaceCode//PID-10.1 Identifier.  Required (length 1..50).
						+"^"+strRaceName//PID-10.2  Text.  Required if known (length 1..999). Human readable text that is not further used.
						+"^HL70005";//PID-10.3 Name of Coding System.  Required (length 1..20).
						//PID-10.4 Alternate Identifier.  Required if known (length 1..50).
						//PID-10.5 Alternate Text.  Required if known (length 1..999).
						//PID-10.6 Name of Alternate Coding system.  Required if PID-10.4 is not blank.
				}
			}
			_seg.SetField(10,hl7Race);
			//PID-11 Patient Address.  Required if known (length unspecified).  Cardinality [0..*].  Type XAD (guide page 74).  First repetition must be the primary address.
			WriteXAD(11,_pat.Address,_pat.Address,_pat.City,_pat.State,_pat.Zip);
			//PID-12 County Code.  No longer used.
			WriteXTN(13,"PRN","PH",_pat.HmPhone,_pat.WirelessPhone,_pat.WkPhone);//PID-13 Phone Number - Home.  Required if known (length unspecified).  Cardinality [0..*].  Type XTN (guide page 84).
			//PID-14 Phone Number - Business.  Optional.
			//PID-15 Primary Language.  Optional.
			//PID-16 Marital Status.  Optional.
			//PID-17 Religion.  Optional.
			//PID-18 Patient Account Number.  Optional.
			//PID-19 SSN Number - Patient.  No longer used.
			//PID-20 Driver's License Number - Patient.  No longer used.
			//PID-21 Mother's Identifier.  No longer used.
			//PID-22 Ethnic Group.  Required if known (length unspecified).  Cardinality [0..1].  Value set HL70189 (guide page 201).  Type CE.
			if(listPatRacesFiltered.Count>0) {//The user specified a race and ethnicity and did not select the decline to specify option.
				if(isHispanicOrLatino) {
					WriteCE(22,"2135-2","Hispanic or Latino","CDCREC");
				}
				else {//Not hispanic or latino.
					WriteCE(22,"2186-5","not Hispanic or Latino","CDCREC");
				}
			}
			//PID-23 Birth Place.  Optional.  Cardinaility [0..1].
			//PID-24 Multiple Birth Indicator.  Optional.  Cardinaility [0..1].
			//PID-25 Birth Order.  Required when PID-24 is set to "Y".  Cardinaility [0..1].
			//PID-26 Citizenship.  Optional.  Cardinaility [0..1].
			//PID-27 Veterans Military Status.  Optional.  Cardinaility [0..1].
			//PID-28 Nationality.  Optional.  Cardinaility [0..1].
			//PID-29 Patient Death Date and Time.  Required if PID-30 is set to "Y".  Cardinaility [0..1].  TODO: We need a UI for this information as required for EHR.  Date is required, time is not.
			//PID-30 Patient Death Indicator.  Required if known.  Cardinaility [0..1].  Value set HL70136.  TODO: Set this field to "Y" if the death date and time year is greater than 1880, otherwise do not set.
			//PID-31 Identity Unknown.  Optional.  Cardinaility [0..1].
			//PID-32 Identity Reliability Code.  Optional.  Cardinaility [0..1].
			//PID-33 Last Update Date/Time.  Optional.  Cardinaility [0..1].
			//PID-34 Last Update Facility.  Optional.  Cardinaility [0..1].
			//PID-35 Species Code.  Optional.  Cardinaility [0..1].
			//PID-36 Breed Code.  Optional.  Cardinaility [0..1].
			//PID-37 Strain.  Optional.  Cardinaility [0..1].
			//PID-38 Production Class Code.  Optional.  Cardinaility [0..1].
			//PID-39 Tribal Citizenship.  Optional.  Cardinaility [0..1].
			_msg.Segments.Add(_seg);
		}

		///<summary>Pharmacy/Treatment Administration segment.  Required.  Guide page 149.</summary>
		private void RXA(VaccinePat vaccine) {
			VaccineDef vaccineDef=VaccineDefs.GetOne(vaccine.VaccineDefNum);
			_seg=new SegmentHL7(SegmentNameHL7.RXA);
			_seg.SetField(0,"RXA");
			_seg.SetField(1,"0");//RXA-1 Give Sub-ID Counter.  Required.  Must be "0".
			_seg.SetField(2,"1");//RXA-2 Administration Sub-ID Counter.  Required.  Must be "1".
			_seg.SetField(3,vaccine.DateTimeStart.ToString("yyyyMMddHHmm"));//RXA-3 Date/Time Start of Administration.  Required.  This segment can also be used to planned vaccinations.
			_seg.SetField(4,vaccine.DateTimeEnd.ToString("yyyyMMddHHmm"));//RXA-4 Date/Time End of Administration.  Required if known.  Must be same as RXA-3 or blank.  UI forces RXA-4 and RXA-3 to be equal.  This would be blank if for a planned vaccine.
			//RXA-5 Administered Code.  Required.  Cardinality [1..1].  Type CE (guide page 53).  Must be a CVX code.
			WriteCE(5,vaccineDef.CVXCode,vaccineDef.VaccineName,"CVX");
			//RXA-6 Administered Amount.  Required (length 1..20).  If amount is not known or not meaningful, then use "999".
			if(vaccine.AdministeredAmt>0){
				_seg.SetField(6,"999");//Registries that do not collect administered amount should record the value as "999".
			}
			else{
				_seg.SetField(6,vaccine.AdministeredAmt.ToString());
			}
			//RXA-7 Administered Units.  Required if RXA-6 is not "999".  Cadinality [0..1].  Type CE (guide page 53).  Value set HL70396 (guide page 231).  Must be UCUM coding.
			if(vaccine.AdministeredAmt>0 && vaccine.DrugUnitNum!=0) {
				DrugUnit drugUnit=DrugUnits.GetOne(vaccine.DrugUnitNum);
				WriteCE(7,drugUnit.UnitIdentifier,drugUnit.UnitText,"UCUM");//UCUM is not in table HL70396, but it there was a note stating that it was required in the guide and this value was required in the test cases.
			}
			//RXA-8 Administered Dosage Form.  Optional.
			//RXA-9 Administration Notes.  Required if RXA-20 is "CP" or "PA".  Value set NIP 0001.  Type CE.  TODO: We need a UI for Admininstration Note, and the code system NIP 0001 needs to be built in.
			WriteCE(9,"","","");
			//RXA-10 Administering Provider.  Required if known.  This is the person who gave the administration or the vaccinaton.  It is not the ordering clinician.  TODO: We need a provider picker UI for the administering provider.
			//RXA-11 Administered-at Location.  Required if known.  Type LA2 (guide page 68).  This is the clinic/site where the vaccine was administered.
			WriteLA2(11,cityWhereEntered,stateWhereEntered);
			//RXA-12 Administered Per (Time Unit).  Optional.
			//RXA-13 Administered Strength.  Optional.
			//RXA-14 Administered Strength Units.  Optional.
			//RXA-15 Substance Lot Number.  Required if the value in RXA-9.1 is "00".
			_seg.SetField(15,vaccine.LotNumber);
			//RXA-16 Substance Expiration Date.  Required if RXA-15 is not blank.  TODO: We need UI for substance expiration date.
			//RXA-17 Substance Manufacturer Name.  Requred if RXA-9.1 is "00".  Cardinality [0..*].  Value set MVX.  Type CE.  TODO: Consider limiting the UI choices to code set MVX, since only MVX codes are allowed here.
			if(vaccineDef.DrugManufacturerNum!=0) {
				DrugManufacturer manufacturer=DrugManufacturers.GetOne(vaccineDef.DrugManufacturerNum);
				WriteCE(17,manufacturer.ManufacturerCode,manufacturer.ManufacturerName,"HL70227");
			}
			//RXA-18 Substance/Treatment Refusal Reason.  Required if RXA-20 is "RE".  Cardinality [0..*].  Required when RXA-20 is "RE", otherwise do not send.  Value set NIP002.  Type CE.  TODO: We need UI for refusal reason.
			//RXA-19 Indication.  Optional.
			//RXA-20 Completion Status.  Required if known (length 2..2).  Value set HL70322 (guide page 225).  CP=Complete, RE=Refused, NA=Not Administered, PA=Partially Administered.  TODO: We need UI for completion status.
			//RXA-21 Action code.  Required if known (length 2..2).  Value set HL70323 (guide page 225).  A=Add, D=Delete, U=Update.  TODO: We need UI for action code.
			//RXA-22 System Entry Date/Time.  Optional.
			//RXA-23 Administered Drug Strength.  Optional.
			//RXA-24 Administered Drug Strength Volume Units.  Optional.
			//RXA-25 Administered Barcode Identifier.  Optional.
			//RXA-26 Pharmacy Order Type.  Optional.
			_msg.Segments.Add(_seg);
		}

		///<summary>Pharmacy/Treatment Route segment.  Required if known (no way to enter in UI).  Guide page 158.</summary>
		private void RXR(VaccinePat vaccine) {
			_seg=new SegmentHL7(SegmentNameHL7.RXR);
			_seg.SetField(0,"RXR");
			//RXR-1 Route.  Required.  Cardinality [1..1].  Value set HL70162 (guide page 200). Type CE (guide page 53).  TODO: Need UI for Administration Route.
			WriteCE(1,"","","HL70162");
			//RXR-2 Administration Site.  Required if known.  Cardinality [0..1].  Value set HL70163 (guide page 201, details where the vaccine was physically administered on the patient's body).  Type CE.  TODO: We need UI for this field.
			WriteCE(2,"","","HL70163");
			//RXR-3 Administration Device.  Optional.
			//RXR-4 Administration Method.  Optional.
			//RXR-5 Routing Instruction.  Optional.
			//RXR-6 Administration Site Modifier.  Optional.
			_msg.Segments.Add(_seg);
		}

		public string GenerateMessage() {
			return _msg.ToString();
		}

		#region Field Helpers

		///<summary>Type CE (guide page 53).  Writes a coded element into the fieldIndex field of the current segment.</summary>
		private void WriteCE(int fieldIndex,string strIdentifier,string strText,string strNameCodeSystem) {
			_seg.SetField(fieldIndex,
				strIdentifier,//CE.1 Identifier.  Required (length 1..50).
				strText,//CE.2 Text.  Required if known (length 1..999). Human readable text that is not further used.
				strNameCodeSystem//CE.3 Name of Coding System.  Required (length 1..20).
				//CE.4 Alternate Identifier.  Required if known (length 1..50).
				//CE.5 Alternate Text.  Required if known (length 1..999).
				//CE.6 Name of Alternate Coding system.  Required if CE.4 is not blank.
			);
		}

		///<summary>Type EI (guid page 62).  Writes an Entity Identifier (order number) into the fieldIndex field of the current segment.</summary>
		private void WriteEI(int fieldIndex,string identifier,string city,string state) {
			_seg.SetField(fieldIndex,
				identifier,//EI.1 Entity Identifier.  Required (length 1..199).
				GetAssigningAuthority(city,state),//EI.2 Namespace ID.  Required if EI.3 is blank (length 1..20).  Value set HL70363 (guide page 229, 3 letter abbreviation for US state, US city, or US territory).
				"",//EI.3 Universal ID.  Required if EI.1 is blank (length 1..199).
				"");//EI.4 Universal ID Type.  Required if EI.3 is not blank (length 6..6).  Value set HL70301 (guide page 224).  Must be "ISO" or blank.
		}

		///<summary>Corresponds to table HL70363 (guide page 229).</summary>
		private string GetAssigningAuthority(string city,string state) {
			if(city.Length!=2) {
				return "";
			}
			//TODO: Validate state code somewhere before the export begins.  Same state codes as for NewCrop, except excludes "UM" (U.S. Minor Outlying Islands).
			string code="";//A value from Value set HL70363 (guide page 229, 3 letter abbreviation for US state, US city, or US territory).
			string st=state.ToUpper();
			string c=city.ToUpper().Replace(" ","");
			code=st+"A";//Most of the codes are just the state code followed by an 'A'.  This includes American territories and districts. http://www.itl.nist.gov/fipspubs/fip5-2.htm
			if(st=="IL" && c=="CHICAGO") { //CHICAGO ILLINOIS
				code="CHA";//CHICAGO has thier own code.
			}
			else if(st=="NY" && c=="NEWYORK") { //NEW YORK NEW YORK
				code="BAA";//NEW YORK CITY has their own code.
			}
			else if(st=="PA" && c=="PHILADELPHIA") { //PHILADELPHIA PENNSYLVANIA
				code="PHA";//Philadelphia has their own code.
			}
			else if(st=="PW") { //REPUBLIC PALAU (American territory)
				code="RPA";//This code is one that does not match the pattern for the rest of the codes.
			}
			else if(st=="TX" && c=="SANANTONIO") { //SAN ANTONIO TEXAS
				code="TBA";//SAN ANTONIO has their own code.
			}
			else if(st=="TX" && c=="HOUSTON") { //HOUSTON TEXAS
				code="THA";//HOUSTON has their own code.
			}
			return code;
		}

		///<summary>Type IS (guide page 68).  Writes a string corresponding to table HL70001 (guide page 193) into the fieldIndex field for the current segment.</summary>
		private void WriteGender(int fieldIndex,PatientGender gender) {
			string strGenderCode="U";//unknown
			if(gender==PatientGender.Female) {
				strGenderCode="F";
			}
			if(gender==PatientGender.Male) {
				strGenderCode="M";
			}
			_seg.SetField(fieldIndex,strGenderCode);
		}

		///<summary>Type LA2 (guide page 68).  Writes facility information into the fieldIndex field of the current segment.</summary>
		private void WriteLA2(int fieldIndex,string city,string state) {
			_seg.SetField(fieldIndex,
				"",//LA2.1 Point of Care.  Optional.
				"",//LA2.2 Room.  Optional.
				"",//LA2.3 Bed.  Optional.
				//LA2.4 Facility.  Required.  Type HD (guide page 66).
				GetAssigningAuthority(city,state)//LA2.4.1 Namespace ID.  Required when LA2.4.2 is blank.  Value sets HL70300 (guide page 224), HL70361 (guide page 229), HL70362 (guide page 229), HL70363 (guide page 229).  Value set used depends on usage.
				//LA2.4.2 Universal ID.  Required when LA2.4.1 is blank.
				//LA2.4.3 Universal ID Type.  Required when LA2.4.2 is not blank.  Value set HL70301 (guide page 224).
				//LA2.5 Location Status.  Optional.
				//LA2.6 Patient Location Type.  Optional.
				//LA2.7 Building.  Optional.
				//LA2.8 Floor.  Optional.
				//LA2.9 Street Address.  Optional.
				//LA2.10 Other Designation.  Optional.
				//LA2.11 City.  Optional.
				//LA2.12 State or Province.  Optional.
				//LA2.13 Zip or Postal Code.  Optional.
				//LA2.14 Country.  Optional.
				//LA2.15 Address Type.  Optional.
				//LA2.16 Other Geographic Designation.  Optional.
				);
		}

		///<summary>Type XAD (guide page 74).  Writes an extended address into the fieldIndex field for the current segment.</summary>
		private void WriteXAD(int fieldIndex,string address1,string address2,string city,string state,string zip) {
			_seg.SetField(fieldIndex,
				address1,//XAD.1 Street Address.  Required if known (length unspecified).  Data type SAD (guide page 72).  The SAD type only requires the first sub-component.
				address2,//XAD.2 Other Designation.  Required if known (length 1..120).
				city,//XAD.3 City.  Required if known (length 1..50).
				state,//XAD.4 State or Province.  Required if known (length 1..50).
				zip,//XAD.5 Zip or Postal Code.  Required if known (length 1..12).
				"USA",//XAD.6 Country.  Required if known (length 3..3).  Value set HL70399.  Defaults to USA.
				"L"//XAD.7 Address Type.  Required (length 1..3).  Value set HL70190 (guide page 202).  C=Current or temporary,P=Permanent,M=Mailing,B=Firm/Business,O=Office,H=Home,L=Legal address,etc...
				//XAD.8 Other Geographic Designation.  Optional.
				//XAD.9 County/Parish Code.  Optional.
				//XAD.10 Census Tract.  Optional.
				//XAD.11 Address Representation Code.  Optional.
				//XAD.12 Address Validity Range.  No longer used.
				//XAD.13 Effective Date.  Optional.
				//XAD.14 Expiration Date.  Optional.
			);
		}

		///<summary>Type XCN (guide page 77).  Writes user name and id into the fieldIndex field for the current segment.
		///Either the fName and lName must be specified, or id and city and state must be specified. All fields may be specified.</summary>
		private void WriteXCN(int fieldIndex,string fName,string lName,string middleI,string id,string city,string state) {
			bool hasName=false;
			if(fName!="" && lName!="") {
				hasName=true;
			}
			bool hasId=false;
			string idModified="";
			string assigningAuthority="";
			if(id!="" && city!="" && state!="") {//All 3 fields must be present, or none of them should be sent.
				hasId=true;
				idModified=id;
				assigningAuthority=GetAssigningAuthority(city,state);
			}
			if(!hasName && !hasId) {
				return;//Nothing valid to write.
			}
			_seg.SetField(fieldIndex,
				idModified,//XCN.1 ID Number.  Required if XCN.2 and XCN.3 are blank.
				lName,//XCN.2 Family Name.  Required if known.
				fName,//XCN.3 Given Name.  Required if known (length 1..30).
				middleI,//XCN.4 Second and Further Given Names or Initials Thereof.  Required if known (length 1..30).
				//XCN.5 Suffix.  Optional.
				//XCN.6 Prefix.  Optional.
				//XCN.7 Degree.  No longer used.
				//XCN.8 Source Table.  Optional.
				assigningAuthority,//XCN.9 Assigning Authority.  Required if XCN.1 is not blank.  Value set HL70363 (guide page 229).
				"L"//XCN.10 Name Type Code.  Required if known (length 1..1).  Value set HL70200 (guide page 203).  A=Alias name,L=Legal name,D=Display name,M=Maiden name,C=Adopted name,B=Name at birth,P=Name of partner/spouse,U=Unspecified.
				//XCN.11 Identifier Check Digit.  Optional.
				//XCN.12 Check Digit Scheme.  Required if XCN.11 is not blank.
				//XCN.13 Identifier Type Code.  Optional.
				//XCN.14 Assigning Facility.  Optional.
				//XCN.15 Name Representation Code.  Optional.
				//XCN.16 Name Context.  Optional.
				//XCN.17 Name Validity Range.  No longer used.
				//XCN.18 Name Assembly Order.  No longer used.
				//XCN.19 Effective Date.  Optional.
				//XCN.20 Expiration Date.  Optional.
				//XCN.21 Professional Suffix.  Optional.
				//XCN.22 Assigning Jurisdiction.  Optional.
				//XCN.23 Assinging Agency or Department.  Optional.
				);
		}

		///<summary>Type XTN (guide page 84).  Writes a phone number into the fieldIndex field for the current segment.
		///strTeleUseCode must be from value set HL70201 (guide page 203).
		///strTeleEquipType must be from value set HL70202 (guide page 203).
		///Can specify 0 or more phone numbers. The first valid phone number in the list will be written and the other phone numbers will be ignored.</summary>
		private void WriteXTN(int fieldIndex,string strTeleUseCode,string strTeleEquipType,params string[] arrayPhoneNumbers) {
			for(int i=0;i<arrayPhoneNumbers.Length;i++) {
				string phone=arrayPhoneNumbers[i];
				string digits="";
				for(int j=0;j<phone.Length;j++) {
					if(!Char.IsNumber(phone,j)) {
						continue;
					}
					if(digits=="" && phone.Substring(j,1)=="1") {
						continue;//skip leading 1.
					}
					digits+=phone.Substring(j,1);
				}
				if(digits.Length!=10) {
					continue;//The current phone number is invalid. Skip it and try the next number.
				}
				_seg.SetField(fieldIndex,
					"",//XTN.1 Telephone Number.  No longer used.
					strTeleUseCode,//XTN.2 Telecommunication Use Code.  Required.  Value set HL70201 (guide page 203).
					strTeleEquipType,//XTN.3 Telecommunication Equipment Type.  Required if known.  Value set HL70202 (guide page 203).
					"",//XTN.4 Email Address.  Required when XTN.2 is set to "NET" (length 1..199).
					"",//XTN.5 Country Code.  Optional.
					digits.Substring(0,3),//XTN.6 Area/City Code.  Required when XTN.2 is NOT set to "NET" (length 5..5).
					digits.Substring(3)//XTN.7 Local Number.  Required when XTN.2 is NOT set to "NET" (length 9..9).  I think the length is probably recorded wrong in the documentation.  It should be 7.
					//XTN.8 Extension.  Optional.
					//XTN.9 Any Text.  Optional.
					//XTN.10 Extension Prefix.  Optional.
					//XTN.11 Speed Dial Code.  Optional.
					//XTN.12 Unformatted Telephone Number.  Optional.
					);
				return;//We can only write one phone number per phone field. We must exist after the first valid phone number is written.
			}
		}

		///<summary>Type XPN (guide page 82).  Writes an person's name into the fieldIndex field for the current segment.
		///The fName and lName cannot be blank.
		///The middleI may be blank.</summary>
		private void WriteXPN(int fieldIndex,string fName,string lName,string middleI) {
			_seg.SetField(fieldIndex,
				lName,//XPN.1 Family Name.  Required (length 1..50).  Type FN (guide page 64).  Cardinality [1..1].  The FN type only requires the last name field and it is the first field.
				fName,//XPN.2 Given Name.  Required (length 1..30).  Cardinality [1..1].
				middleI,//XPN.3 Second and Further Given Names or Initials Thereof (middle name).  Required if known (length 1..30). 
				"",//XPN.4 Suffix.  Optional.
				"",//XPN.5 Prefix.  Optional.
				"",//XPN.6 Degree.  No longer used.
				"L"//XPN.7 Name Type Code.  Required if known (length 1..1).  Value set HL70200 (guide page 203).  A=Alias Name,L=Legal Name,D=Display Name,M=Maiden Name,C=Adopted Name,B=Name at birth,P=Name of partner/spouse,U=Unspecified.
				//XPN.8 Name Representation Code.  Optional.
				//XPN.9 Name Context.  Optional.
				//XPN.10 Name Validity Range.  No longer used.
				//XPN.11 Name Assembly Order.  Optional.
				//XPN.12 Effective Date.  Optional.
				//XPN.13 Expiration Date.  Optional.
				//XPN.14 Professional Suffix.  Optional.
				);
		}

		#endregion Field Helpers

		//The 2 examples below have been edited slightly for our purposes.  They still pass validation.

		//example 1:
		/*
MSH|^~\&|Open Dental||||20110316102457||VXU^V04^VXU_V04|OD-110316102457117|P|2.5.1
PID|||9817566735^^^MPI&2.16.840.1.113883.19.3.2.1&ISO^MR||Johnson^Philip||20070526|M||2106-3^White^HL70005|3345 Elm Street^^Aurora^CO^80011^^M||^PRN^^^^303^5548889|||||||||N^Not Hispanic or Latino^HL70189
ORC|RE
RXA|0|1|201004051600|201004051600|33^Pneumococcal Polysaccharide^CVX|0.5|ml^milliliter^ISO+||||||||1039A||MSD^Merck^HL70227||||A
		 */

		//example7 has two vaccines:
		/*
MSH|^~\&|EHR Application|EHR Facility|PH Application|PH Facility|20110316102838||VXU^V04^VXU_V04|NIST-110316102838387|P|2.5.1
PID|||787478017^^^MPI&2.16.840.1.113883.19.3.2.1&ISO^MR||James^Wanda||19810430|F||2106-3^White^HL70005|574 Wilkins Road^^Shawville^Pennsylvania^16873^^M||^PRN^^^^814^5752819|||||||||N^Not Hispanic or Latino^HL70189
ORC|RE
RXA|0|1|201004051600|201004051600|52^Hepatitis A^HL70292|1|ml^milliliter^ISO+||||||||HAB9678V1||SKB^GLAXOSMITHKLINE^HL70227||||A
ORC|RE
RXA|0|1|201007011330|201007011330|03^Measles Mumps Rubella^HL70292|999|||||||||||||||A
		 */

	}
}
