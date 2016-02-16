//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Crud.ProvidermCrud;
import OpenDentBusiness.Mobile.Providerm;
import OpenDentBusiness.POut;
import OpenDentBusiness.Provider;
import OpenDentBusiness.Providers;

/**
* 
*/
public class Providerms   
{
    /**
    * Gets one Providerm from the db.
    */
    public static Providerm getOne(long customerNum, long provNum) throws Exception {
        return ProvidermCrud.selectOne(customerNum,provNum);
    }

    public static List<Providerm> getProviderms(long customerNum) throws Exception {
        String command = "SELECT * from providerm " + "WHERE CustomerNum = " + POut.long(customerNum) + " " + "ORDER BY Abbr";
        return ProvidermCrud.selectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceProvNums(DateTime changedSince) throws Exception {
        return Providers.getChangedSinceProvNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Providerm> getMultProviderms(List<long> ProvNums) throws Exception {
        List<Provider> rxList = Providers.GetMultProviders(ProvNums);
        List<Providerm> RxmList = ConvertListToM(rxList);
        return RxmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Providerm> convertListToM(List<Provider> list) throws Exception {
        List<Providerm> retVal = new List<Providerm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(ProvidermCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Providerm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Providerm providerm = ProvidermCrud.SelectOne(customerNum, list[i].ProvNum);
            if (providerm == null)
            {
                //not in db
                ProvidermCrud.Insert(list[i], true);
            }
            else
            {
                ProvidermCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM providerm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Providerm> Refresh(long patNum){
			string command="SELECT * FROM providerm WHERE PatNum = "+POut.Long(patNum);
			return Crud.ProvidermCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(Providerm providerm){
			return Crud.ProvidermCrud.Insert(providerm,true);
		}
		///<summary></summary>
		public static void Update(Providerm providerm){
			Crud.ProvidermCrud.Update(providerm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long provNum) {
			string command= "DELETE FROM providerm WHERE CustomerNum = "+POut.Long(customerNum)+" AND ProvNum = "+POut.Long(provNum);
			Db.NonQ(command);
		}
*/