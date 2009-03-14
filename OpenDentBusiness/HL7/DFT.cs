﻿using System;
using System.Collections.Generic;
using System.Text;
using OpenDentBusiness;

namespace OpenDentBusiness.HL7 {
	public class DFT {
		private MessageHL7 msg;

		///<summary>The constructor has all the info necessary to create the Message object.</summary>
		public DFT(Appointment apt,Patient pat) {
			msg=new MessageHL7(MessageType.DFT);
			SegmentHL7 seg;
			seg=new SegmentHL7(@"MSH|^~\&|OD||ECW||"+DateTime.Now.ToString("yyyyMMddHHmmss")+"||DFT^P03||P|2.3");
			msg.Segments.Add(seg);
			seg=new SegmentHL7("EVN|P03|"+DateTime.Now.ToString("yyyyMMddHHmmss")+"|");
			msg.Segments.Add(seg);
			//PID--------------------------------------------------------------
			seg=new SegmentHL7(SegmentName.PID);
			seg.SetField(0,"PID");
			seg.SetField(1,"1");
			seg.SetField(2,pat.PatNum.ToString());
			seg.SetField(3,pat.PatNum.ToString());//??what is this MRN?
			seg.SetField(5,pat.LName,pat.FName,pat.MiddleI);
			//we assume that dob is always valid because eCW should always pass us a dob.
			seg.SetField(7,pat.Birthdate.ToString("yyyyMMdd"));
			seg.SetField(8,ConvertGender(pat.Gender));
			seg.SetField(10,ConvertRace(pat.Race));
			seg.SetField(11,pat.Address,pat.Address2,pat.City,pat.State,pat.Zip);
			seg.SetField(13,ConvertPhone(pat.HmPhone));
			seg.SetField(14,ConvertPhone(pat.WkPhone));
			seg.SetField(16,ConvertMaritalStatus(pat.Position));
			seg.SetField(19,pat.SSN);
			msg.Segments.Add(seg);
			//PV1 (appointment)-------------------------------------------------
			seg=new SegmentHL7(SegmentName.PV1);
			seg.SetField(0,"PV1");
			seg.SetField(7,"");//attending provider. Required, but we'll work on it later.
			seg.SetField(19,apt.AptNum.ToString());
			msg.Segments.Add(seg);
			//FT1----------------------------------------------------------
			List<Procedure> procs=Procedures.GetProcsForSingle(apt.AptNum,false);
			for(int i=0;i<procs.Count;i++) {
				seg=new SegmentHL7(SegmentName.FT1);
				seg.SetField(0,"FT1");
				seg.SetField(1,(i+1).ToString());
				seg.SetField(4,procs[i].ProcDate.ToString("yyyyMMddHHmmss"));
				seg.SetField(5,procs[i].ProcDate.ToString("yyyyMMddHHmmss"));
				seg.SetField(6,"CG");
				seg.SetField(10,"1.0");
				seg.SetField(16,"");//????
				seg.SetField(19,procs[i].DiagnosticCode);
				seg.SetField(20,"");//performed by provider.  Required. Incomplete.
				seg.SetField(21,"");//ordering provider. Required. Incomplete.
				seg.SetField(22,"");//copay, optional
				//seg.SetField(25,ProcedureCodes.  procs[i].CodeNum);
				//seg.SetField(,"");
				//seg.SetField(,"");
				//seg.SetField(,"");



				msg.Segments.Add(seg);
			}
			seg=new SegmentHL7(SegmentName.DG1);
			msg.Segments.Add(seg);
		}

		public string GenerateMessage() {
			return msg.GenerateMessage();
		}

		private string ConvertGender(PatientGender gender){
			if(gender==PatientGender.Female) {
				return "F";
			}
			if(gender==PatientGender.Male) {
				return "M";
			}
			return "U";
		}

		private string ConvertRace(PatientRace race) {
			switch(race) {
				case PatientRace.AmericanIndian:
					return "American Indian Or Alaska Native";
				case PatientRace.Asian:
					return "Asian";
				case PatientRace.HawaiiOrPacIsland:
					return "Native Hawaiian or Other Pacific";
				case PatientRace.AfricanAmerican:
					return "Black or African American";
				case PatientRace.White:
					return "White";
				case PatientRace.HispanicLatino:
					return "Hispanic";
				case PatientRace.Other:
					return "Other Race";
				default:
					return "Other Race";
			}
		}

		private string ConvertPhone(string phone) {
			string retVal="";
			for(int i=0;i<phone.Length;i++){
				if(Char.IsNumber(phone,i)){
					if(retVal=="" && phone.Substring(i,1)=="1"){
						continue;//skip leading 1.
					}
					retVal+=phone.Substring(i,1);
				}
				if(retVal.Length==10){
					return retVal;
				}
			}
			//never made it to 10
			return "";
		}

		private string ConvertMaritalStatus(PatientPosition patpos) {
			switch(patpos){
				case PatientPosition.Single:
					return "Single";
				case PatientPosition.Married:
					return "Married";
				case PatientPosition.Divorced:
					return "Divorced";
				case PatientPosition.Widowed:
					return "Widowed";
				case PatientPosition.Child:
					return "Single";
				default:
					return "Single";
			}
		}






	}
}
