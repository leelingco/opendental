//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.AllergyDef;
import OpenDentBusiness.AllergyDefs;
import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.AllergyDefm;
import OpenDentBusiness.Mobile.Crud.AllergyDefmCrud;
import OpenDentBusiness.POut;

/**
* 
*/
public class AllergyDefms   
{
    /**
    * Gets one Medicationm from the db.
    */
    public static AllergyDefm getOne(long customerNum, long allergyNum) throws Exception {
        return AllergyDefmCrud.selectOne(customerNum,allergyNum);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceAllergyDefNums(DateTime changedSince) throws Exception {
        return AllergyDefs.getChangedSinceAllergyDefNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<AllergyDefm> getMultAllergyDefms(List<long> allergyDefNums) throws Exception {
        List<AllergyDef> AllergyDefList = AllergyDefs.GetMultAllergyDefs(allergyDefNums);
        List<AllergyDefm> AllergyDefmList = ConvertListToM(AllergyDefList);
        return AllergyDefmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<AllergyDefm> convertListToM(List<AllergyDef> list) throws Exception {
        List<AllergyDefm> retVal = new List<AllergyDefm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(AllergyDefmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<AllergyDefm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            AllergyDefm allergyDefm = AllergyDefmCrud.SelectOne(customerNum, list[i].AllergyDefNum);
            if (allergyDefm == null)
            {
                //not in db
                AllergyDefmCrud.Insert(list[i], true);
            }
            else
            {
                AllergyDefmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM allergydefm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<AllergyDefm> Refresh(long patNum){
			string command="SELECT * FROM allergydefm WHERE PatNum = "+POut.Long(patNum);
			return Crud.AllergyDefmCrud.SelectMany(command);
		}
		///<summary>Gets one AllergyDefm from the db.</summary>
		public static AllergyDefm GetOne(long customerNum,long allergyDefNum){
			return Crud.AllergyDefmCrud.SelectOne(customerNum,allergyDefNum);
		}
		///<summary></summary>
		public static long Insert(AllergyDefm allergyDefm){
			return Crud.AllergyDefmCrud.Insert(allergyDefm,true);
		}
		///<summary></summary>
		public static void Update(AllergyDefm allergyDefm){
			Crud.AllergyDefmCrud.Update(allergyDefm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long allergyDefNum) {
			string command= "DELETE FROM allergydefm WHERE CustomerNum = "+POut.Long(customerNum)+" AND AllergyDefNum = "+POut.Long(allergyDefNum);
			Db.NonQ(command);
		}
		*/