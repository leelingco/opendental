//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Crud.RxPatmCrud;
import OpenDentBusiness.Mobile.RxPatm;
import OpenDentBusiness.POut;
import OpenDentBusiness.RxPat;
import OpenDentBusiness.RxPats;

/**
* 
*/
public class RxPatms   
{
    public static RxPatm getOne(long customerNum, long rxNum) throws Exception {
        return RxPatmCrud.selectOne(customerNum,rxNum);
    }

    /**
    * Gets all RxPatm for a single patient
    */
    public static List<RxPatm> getRxPatms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from rxpatm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return RxPatmCrud.selectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceRxNums(DateTime changedSince) throws Exception {
        return RxPats.getChangedSinceRxNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<RxPatm> getMultRxPats(List<long> rxNums) throws Exception {
        List<RxPat> rxList = RxPats.GetMultRxPats(rxNums);
        List<RxPatm> RxmList = ConvertListToM(rxList);
        return RxmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<RxPatm> convertListToM(List<RxPat> list) throws Exception {
        List<RxPatm> retVal = new List<RxPatm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(RxPatmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<RxPatm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            RxPatm rxPatm = RxPatmCrud.SelectOne(customerNum, list[i].RxNum);
            if (rxPatm == null)
            {
                //not in db
                RxPatmCrud.Insert(list[i], true);
            }
            else
            {
                RxPatmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM rxpatm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<RxPatm> Refresh(long patNum){
			string command="SELECT * FROM rxpatm WHERE PatNum = "+POut.Long(patNum);
			return Crud.RxPatmCrud.SelectMany(command);
		}
		///<summary>Gets one RxPatm from the db.</summary>
		///<summary></summary>
		public static long Insert(RxPatm rxPatm){
			return Crud.RxPatmCrud.Insert(rxPatm,true);
		}
		///<summary></summary>
		public static void Update(RxPatm rxPatm){
			Crud.RxPatmCrud.Update(rxPatm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long rxNum) {
			string command= "DELETE FROM rxpatm WHERE CustomerNum = "+POut.Long(customerNum)+" AND RxNum = "+POut.Long(rxNum);
			Db.NonQ(command);
		}
		*/