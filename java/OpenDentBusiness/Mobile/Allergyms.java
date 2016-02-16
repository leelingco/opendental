//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Allergies;
import OpenDentBusiness.Allergy;
import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Allergym;
import OpenDentBusiness.Mobile.Crud.AllergymCrud;
import OpenDentBusiness.POut;

/**
* 
*/
public class Allergyms   
{
    /**
    * Gets all Allergym for a single patient
    */
    public static List<Allergym> getAllergyms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from allergym " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return AllergymCrud.selectMany(command);
    }

    public static DataTable getAllergymDetails(long customerNum, long patNum) throws Exception {
        // get only active allergies
        String command = "SELECT  allergydefm.Description, allergym.Reaction from allergym  LEFT JOIN allergydefm on allergym.AllergyDefNum=allergydefm.AllergyDefNum " + "WHERE allergym.CustomerNum = " + POut.long(customerNum) + " AND allergym.PatNum = " + POut.long(patNum) + " AND allergym.StatusIsActive = " + POut.bool(true) + " AND allergydefm.CustomerNum = " + POut.long(customerNum);
        return Db.getTable(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceAllergyNums(DateTime changedSince) throws Exception {
        return Allergies.getChangedSinceAllergyNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Allergym> getMultAllergyms(List<long> allergyNums) throws Exception {
        List<Allergy> allergyList = Allergies.GetMultAllergies(allergyNums);
        List<Allergym> allergymList = ConvertListToM(allergyList);
        return allergymList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Allergym> convertListToM(List<Allergy> list) throws Exception {
        List<Allergym> retVal = new List<Allergym>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(AllergymCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Allergym> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Allergym allergym = AllergymCrud.SelectOne(customerNum, list[i].AllergyNum);
            if (allergym == null)
            {
                //not in db
                AllergymCrud.Insert(list[i], true);
            }
            else
            {
                AllergymCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM allergym WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

    /**
    * Delete all allergies of a particular patient
    */
    public static void delete(long customerNum, long PatNum) throws Exception {
        String command = "DELETE FROM allergym WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(PatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Allergym> Refresh(long patNum){
			string command="SELECT * FROM allergym WHERE PatNum = "+POut.Long(patNum);
			return Crud.AllergymCrud.SelectMany(command);
		}
		///<summary>Gets one Allergym from the db.</summary>
		public static Allergym GetOne(long customerNum,long allergyNum){
			return Crud.AllergymCrud.SelectOne(customerNum,allergyNum);
		}
		///<summary></summary>
		public static long Insert(Allergym allergym){
			return Crud.AllergymCrud.Insert(allergym,true);
		}
		///<summary></summary>
		public static void Update(Allergym allergym){
			Crud.AllergymCrud.Update(allergym);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long allergyNum) {
			string command= "DELETE FROM allergym WHERE CustomerNum = "+POut.Long(customerNum)+" AND AllergyNum = "+POut.Long(allergyNum);
			Db.NonQ(command);
		}
		*/