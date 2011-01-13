using System;
using System.Collections.Generic;
using System.Data;
using System.Reflection;
using System.Text;

namespace OpenDentBusiness.Mobile {
	///<summary></summary>
	public class Patientms {

		///<summary>Gets one Patientm from the db.</summary>
		public static Patientm GetOne(long customerNum,long patNum) {
			return Crud.PatientmCrud.SelectOne(customerNum,patNum);
		}

		///<summary>Gets Patientms from the db as specified by the search string. Limit to 20 </summary>
		public static List<Patientm> GetPatientms(long customerNum,string searchterm) {
			string command="SELECT * FROM patientm "
				+"WHERE CustomerNum = "+POut.Long(customerNum)+ " "
				+" AND LName like '"+searchterm+"%'"+" LIMIT 20";
			return Crud.PatientmCrud.SelectMany(command);
		}

		public static List<long> GetChangedSincePatNums(DateTime changedSince) {
			return Patients.GetChangedSincePatNums(changedSince);
		}

		///<summary>The values returned are sent to the webserver. Used if GetChanged returns large recordsets.</summary>
		public static List<Patientm> GetMultPats(List<long> patNums) {
			Patient[]  patientArray=Patients.GetMultPats(patNums);
			List<Patient> patientList=new List<Patient>(patientArray);
			List<Patientm> patientmList=ConvertListToM(patientList);
			return patientmList;
		}
		///<summary>First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.</summary>
		public static List<Patientm> ConvertListToM(List<Patient> list) {
			List<Patientm> retVal=new List<Patientm>();
			for(int i=0;i<list.Count;i++) {
				retVal.Add(Crud.PatientmCrud.ConvertToM(list[i]));
			}
			return retVal;
		}

		///<summary>Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.</summary>
		public static void UpdateFromChangeList(List<Patientm> list,long customerNum) {
			for(int i=0;i<list.Count;i++) {
				list[i].CustomerNum=customerNum;
				Patientm patientm=Crud.PatientmCrud.SelectOne(customerNum,list[i].PatNum);
				if(patientm==null) {//not in db
					Crud.PatientmCrud.Insert(list[i],true);
				}
				else {
					Crud.PatientmCrud.Update(list[i]);
				}
			}
		}

		///<summary>Converts a date to an age. If age is over 115, then returns 0.</summary>
		public static int DateToAge(DateTime date) {
			return Patients.DateToAge(date);
		}

		/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.

		///<summary></summary>
		public static List<Patientm> Refresh(long patNum){
			string command="SELECT * FROM patientm WHERE PatNum = "+POut.Long(patNum);
			return Crud.PatientmCrud.SelectMany(command);
		}

		 ///<summary>This would be executed on the webserver only</summary>
		public static long Insert(Patientm patientm) {
			return Crud.PatientmCrud.Insert(patientm,true);
		}

		///<summary>This would be executed on the webserver only</summary>
		public static void Update(Patientm patientm) {
			Crud.PatientmCrud.Update(patientm);
		}

		///<summary>This would be executed on the webserver only</summary>
		public static void Delete(long customerNum,long patNum) {
			Crud.PatientmCrud.Delete(customerNum, patNum);
		}

	
		///<summary>The values returned are sent to the webserver.</summary>
		public static List<Patientm> GetChanged(DateTime changedSince) {
			List<Patient> ChangedPatientList=Patients.GetChangedSince(changedSince);
			List<Patientm> ChangedPatientmList=ConvertListToM(ChangedPatientList);
			return ChangedPatientmList;
		}

		*/



	}
}