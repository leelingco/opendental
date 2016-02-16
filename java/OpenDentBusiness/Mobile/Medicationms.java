//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Medication;
import OpenDentBusiness.Medications;
import OpenDentBusiness.Mobile.Crud.MedicationmCrud;
import OpenDentBusiness.Mobile.Medicationm;
import OpenDentBusiness.POut;

/**
* 
*/
public class Medicationms   
{
    /**
    * Gets one Medicationm from the db.
    */
    public static Medicationm getOne(long customerNum, long medicationNum) throws Exception {
        return MedicationmCrud.selectOne(customerNum,medicationNum);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceMedicationNums(DateTime changedSince) throws Exception {
        return Medications.getChangedSinceMedicationNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Medicationm> getMultMedicationms(List<long> medicationNums) throws Exception {
        List<Medication> MedicationList = Medications.GetMultMedications(medicationNums);
        List<Medicationm> MedicationmList = ConvertListToM(MedicationList);
        return MedicationmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Medicationm> convertListToM(List<Medication> list) throws Exception {
        List<Medicationm> retVal = new List<Medicationm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(MedicationmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    public static void updateFromChangeList(List<Medicationm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Medicationm medicationm = MedicationmCrud.SelectOne(customerNum, list[i].MedicationNum);
            if (medicationm == null)
            {
                //not in db
                MedicationmCrud.Insert(list[i], true);
            }
            else
            {
                MedicationmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM medicationm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Medicationm> Refresh(long patNum){
			string command="SELECT * FROM medicationm WHERE PatNum = "+POut.Long(patNum);
			return Crud.MedicationmCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(Medicationm medicationm){
			return Crud.MedicationmCrud.Insert(medicationm,true);
		}
		///<summary></summary>
		public static void Update(Medicationm medicationm){
			Crud.MedicationmCrud.Update(medicationm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long medicationNum) {
			string command= "DELETE FROM medicationm WHERE CustomerNum = "+POut.Long(customerNum)+" AND MedicationNum = "+POut.Long(medicationNum);
			Db.NonQ(command);
		}
		*/