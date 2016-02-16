//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Disease;
import OpenDentBusiness.Diseases;
import OpenDentBusiness.Mobile.Crud.DiseasemCrud;
import OpenDentBusiness.Mobile.Diseasem;
import OpenDentBusiness.POut;
import OpenDentBusiness.ProblemStatus;

/**
* 
*/
public class Diseasems   
{
    /**
    * Gets all Diseasem for a single patient
    */
    public static List<Diseasem> getDiseasems(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from diseasem " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return DiseasemCrud.selectMany(command);
    }

    public static DataTable getDiseasemDetails(long customerNum, long patNum) throws Exception {
        // get only active diseases
        //get only ICD9NUM which are not zero. ICD9NUM and DiseaseDefNum are mutually exculsive. If one is zero the other is not.
        String command = "SELECT  icd9m.Description from diseasem  LEFT JOIN icd9m on icd9m.ICD9Num=diseasem.ICD9Num " + "WHERE diseasem.CustomerNum = " + POut.long(customerNum) + " AND diseasem.PatNum = " + POut.long(patNum) + " AND diseasem.ProbStatus = " + POut.int(((Enum)ProblemStatus.Active).ordinal()) + " AND diseasem.ICD9Num !=0" + " AND icd9m.CustomerNum = " + POut.long(customerNum);
        return Db.getTable(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceDiseaseNums(DateTime changedSince, List<long> eligibleForUploadPatNumList) throws Exception {
        return Diseases.GetChangedSinceDiseaseNums(changedSince, eligibleForUploadPatNumList);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Diseasem> getMultDiseasems(List<long> diseaseNums) throws Exception {
        List<Disease> diseaseList = Diseases.GetMultDiseases(diseaseNums);
        List<Diseasem> diseasemList = ConvertListToM(diseaseList);
        return diseasemList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Diseasem> convertListToM(List<Disease> list) throws Exception {
        List<Diseasem> retVal = new List<Diseasem>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(DiseasemCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Diseasem> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Diseasem diseasem = DiseasemCrud.SelectOne(customerNum, list[i].DiseaseNum);
            if (diseasem == null)
            {
                //not in db
                DiseasemCrud.Insert(list[i], true);
            }
            else
            {
                DiseasemCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM diseasem WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

    /**
    * Delete all diseases of a particular patient
    */
    public static void delete(long customerNum, long PatNum) throws Exception {
        String command = "DELETE FROM diseasem WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(PatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Diseasem> Refresh(long patNum){
			string command="SELECT * FROM diseasem WHERE PatNum = "+POut.Long(patNum);
			return Crud.DiseasemCrud.SelectMany(command);
		}
		///<summary>Gets one Diseasem from the db.</summary>
		public static Diseasem GetOne(long customerNum,long diseaseNum){
			return Crud.DiseasemCrud.SelectOne(customerNum,diseaseNum);
		}
		///<summary></summary>
		public static long Insert(Diseasem diseasem){
			return Crud.DiseasemCrud.Insert(diseasem,true);
		}
		///<summary></summary>
		public static void Update(Diseasem diseasem){
			Crud.DiseasemCrud.Update(diseasem);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long diseaseNum) {
			string command= "DELETE FROM diseasem WHERE CustomerNum = "+POut.Long(customerNum)+" AND DiseaseNum = "+POut.Long(diseaseNum);
			Db.NonQ(command);
		}
		*/