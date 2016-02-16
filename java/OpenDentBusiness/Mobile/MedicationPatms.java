//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.MedicationPat;
import OpenDentBusiness.MedicationPats;
import OpenDentBusiness.Mobile.Crud.MedicationPatmCrud;
import OpenDentBusiness.Mobile.MedicationPatm;
import OpenDentBusiness.POut;

/**
* 
*/
public class MedicationPatms   
{
    /**
    * Gets all MedicationPatm for a single patient
    */
    public static List<MedicationPatm> getMedicationPatms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from medicationpatm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return MedicationPatmCrud.selectMany(command);
    }

    public static DataTable getMedicationmDetails(long customerNum, long patNum) throws Exception {
        // filter out discontinued medications.
        String command = "SELECT  medicationm.MedName from medicationpatm  LEFT JOIN medicationm on medicationpatm.MedicationNum=medicationm.MedicationNum " + "WHERE medicationpatm.CustomerNum = " + POut.long(customerNum) + " AND medicationpatm.PatNum = " + POut.long(patNum) + " AND medicationpatm.DateStop = " + POut.Date(DateTime.MinValue) + " AND medicationm.CustomerNum = " + POut.long(customerNum);
        return Db.getTable(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceMedicationPatNums(DateTime changedSince, List<long> eligibleForUploadPatNumList) throws Exception {
        return MedicationPats.GetChangedSinceMedicationPatNums(changedSince, eligibleForUploadPatNumList);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<MedicationPatm> getMultMedicationPatms(List<long> rxNums) throws Exception {
        List<MedicationPat> medicationPatList = MedicationPats.GetMultMedicationPats(rxNums);
        List<MedicationPatm> medicationPatmList = ConvertListToM(medicationPatList);
        return medicationPatmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<MedicationPatm> convertListToM(List<MedicationPat> list) throws Exception {
        List<MedicationPatm> retVal = new List<MedicationPatm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(MedicationPatmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<MedicationPatm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            MedicationPatm medicationPatm = MedicationPatmCrud.SelectOne(customerNum, list[i].MedicationPatNum);
            if (medicationPatm == null)
            {
                //not in db
                MedicationPatmCrud.Insert(list[i], true);
            }
            else
            {
                MedicationPatmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM medicationpatm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

    /**
    * Delete all medicationpats of a particular patient
    */
    public static void delete(long customerNum, long PatNum) throws Exception {
        String command = "DELETE FROM medicationpatm WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(PatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<MedicationPatm> Refresh(long patNum){
			string command="SELECT * FROM medicationpatm WHERE PatNum = "+POut.Long(patNum);
			return Crud.MedicationPatmCrud.SelectMany(command);
		}
		///<summary>Gets one MedicationPatm from the db.</summary>
		public static MedicationPatm GetOne(long customerNum,long medicationPatNum){
			return Crud.MedicationPatmCrud.SelectOne(customerNum,medicationPatNum);
		}
		///<summary></summary>
		public static long Insert(MedicationPatm medicationPatm){
			return Crud.MedicationPatmCrud.Insert(medicationPatm,true);
		}
		///<summary></summary>
		public static void Update(MedicationPatm medicationPatm){
			Crud.MedicationPatmCrud.Update(medicationPatm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long medicationPatNum) {
			string command= "DELETE FROM medicationpatm WHERE CustomerNum = "+POut.Long(customerNum)+" AND MedicationPatNum = "+POut.Long(medicationPatNum);
			Db.NonQ(command);
		}
		*/