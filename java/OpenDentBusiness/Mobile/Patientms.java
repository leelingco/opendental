//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Crud.PatientmCrud;
import OpenDentBusiness.Mobile.Patientm;
import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.POut;

/**
* 
*/
public class Patientms   
{
    /**
    * Gets one Patientm from the db.
    */
    public static Patientm getOne(long customerNum, long patNum) throws Exception {
        return PatientmCrud.selectOne(customerNum,patNum);
    }

    /**
    * Gets one Patientm from the db based on username.
    */
    public static Patientm getOne(long customerNum, String patUserName, String OnlinePassword) throws Exception {
        String command = "SELECT * FROM patientm " + "WHERE CustomerNum = " + POut.long(customerNum) + " " + " AND OnlinePassword= '" + POut.string(OnlinePassword) + "' " + " AND LCASE(Concat(FName,PatNum))= '" + POut.String(patUserName.ToLower()) + "'";
        List<Patientm> list = PatientmCrud.selectMany(command);
        if (list.Count > 0)
        {
            return list[0];
        }
         
        return null;
    }

    /**
    * Gets Patientms from the db as specified by the search string. Limit to 20
    */
    public static List<Patientm> getPatientms(long customerNum, String searchterm) throws Exception {
        String command = "SELECT * FROM patientm " + "WHERE CustomerNum = " + POut.long(customerNum) + " " + " AND LName like '" + POut.string(searchterm) + "%'" + " LIMIT 30";
        return PatientmCrud.selectMany(command);
    }

    /**
    * Gets Family Members who are patients
    */
    public static List<Patientm> getPatientmsOfFamily(long customerNum, long patNum) throws Exception {
        String command = "SELECT * FROM patientm " + "WHERE CustomerNum = " + POut.long(customerNum) + " " + "AND guarantor in " + "(SELECT guarantor FROM patientm " + "WHERE CustomerNum = " + POut.long(customerNum) + " " + "AND PatNum =" + POut.long(patNum) + ")";
        return PatientmCrud.selectMany(command);
    }

    /**
    * Converts a date to an age. If age is over 115, then returns 0.
    */
    public static int dateToAge(DateTime date) throws Exception {
        return Patients.dateToAge(date);
    }

    public static List<long> getChangedSincePatNums(DateTime changedSince) throws Exception {
        return Patients.getChangedSincePatNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver. Used if GetChanged returns large recordsets.
    */
    public static List<Patientm> getMultPats(List<long> patNums) throws Exception {
        Patient[] patientArray = Patients.GetMultPats(patNums);
        List<Patient> patientList = new List<Patient>(patientArray);
        List<Patientm> patientmList = ConvertListToM(patientList);
        return patientmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Patientm> convertListToM(List<Patient> list) throws Exception {
        List<Patientm> retVal = new List<Patientm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(PatientmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Gets PatNums of patients whose online password is not blank
    */
    public static List<long> getPatNumsEligibleForSynch() throws Exception {
        return Patients.getPatNumsEligibleForSynch();
    }

    /**
    * Gets PatNums of patients whose online password is blank
    */
    public static List<long> getPatNumsForDeletion() throws Exception {
        return Patients.getPatNumsForDeletion();
    }

    /**
    * Takes the list of changes from the dental office and makes updates to those items in the mobile server db.
    */
    public static void updateFromChangeList(List<Patientm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Patientm patientm = PatientmCrud.SelectOne(customerNum, list[i].PatNum);
            if (patientm == null)
            {
                //not in db
                PatientmCrud.Insert(list[i], true);
            }
            else
            {
                PatientmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM patientm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

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