//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Crud.PharmacymCrud;
import OpenDentBusiness.Mobile.Pharmacym;
import OpenDentBusiness.Pharmacies;
import OpenDentBusiness.Pharmacy;
import OpenDentBusiness.POut;

/**
* 
*/
public class Pharmacyms   
{
    /**
    * Gets one Pharmacym from the db.
    */
    public static Pharmacym getOne(long customerNum, long pharmacyNum) throws Exception {
        return PharmacymCrud.selectOne(customerNum,pharmacyNum);
    }

    /**
    * Gets all Appointmentm for a single patient.
    */
    public static List<Pharmacym> getPharmacyms(long customerNum) throws Exception {
        String command = "SELECT * from pharmacym " + "WHERE CustomerNum = " + POut.long(customerNum);
        return PharmacymCrud.selectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSincePharmacyNums(DateTime changedSince) throws Exception {
        return Pharmacies.getChangedSincePharmacyNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Pharmacym> getMultPharmacyms(List<long> PharmacyNums) throws Exception {
        List<Pharmacy> pharmacyList = Pharmacies.GetMultPharmacies(PharmacyNums);
        List<Pharmacym> pharmacymList = ConvertListToM(pharmacyList);
        return pharmacymList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Pharmacym> convertListToM(List<Pharmacy> list) throws Exception {
        List<Pharmacym> retVal = new List<Pharmacym>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(PharmacymCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Pharmacym> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Pharmacym pharmacym = PharmacymCrud.SelectOne(customerNum, list[i].PharmacyNum);
            if (pharmacym == null)
            {
                //not in db
                PharmacymCrud.Insert(list[i], true);
            }
            else
            {
                PharmacymCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM pharmacym WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Pharmacym> Refresh(long patNum){
			string command="SELECT * FROM pharmacym WHERE PatNum = "+POut.Long(patNum);
			return Crud.PharmacymCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(Pharmacym pharmacym){
			return Crud.PharmacymCrud.Insert(pharmacym,true);
		}
		///<summary></summary>
		public static void Update(Pharmacym pharmacym){
			Crud.PharmacymCrud.Update(pharmacym);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long pharmacyNum) {
			string command= "DELETE FROM pharmacym WHERE CustomerNum = "+POut.Long(customerNum)+" AND PharmacyNum = "+POut.Long(pharmacyNum);
			Db.NonQ(command);
		}
		*/