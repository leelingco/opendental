//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.ICD9;
import OpenDentBusiness.ICD9s;
import OpenDentBusiness.Mobile.Crud.ICD9mCrud;
import OpenDentBusiness.Mobile.ICD9m;
import OpenDentBusiness.POut;

/**
* 
*/
public class ICD9ms   
{
    /**
    * Gets one Medicationm from the db.
    */
    public static ICD9m getOne(long customerNum, long icd9Num) throws Exception {
        return ICD9mCrud.selectOne(customerNum,icd9Num);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceICD9Nums(DateTime changedSince) throws Exception {
        return ICD9s.getChangedSinceICD9Nums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<ICD9m> getMultICD9ms(List<long> icd9Nums) throws Exception {
        List<ICD9> ICD9List = ICD9s.GetMultICD9s(icd9Nums);
        List<ICD9m> ICD9mList = ConvertListToM(ICD9List);
        return ICD9mList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<ICD9m> convertListToM(List<ICD9> list) throws Exception {
        List<ICD9m> retVal = new List<ICD9m>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(ICD9mCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<ICD9m> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            ICD9m iCD9m = ICD9mCrud.SelectOne(customerNum, list[i].ICD9Num);
            if (iCD9m == null)
            {
                //not in db
                ICD9mCrud.Insert(list[i], true);
            }
            else
            {
                ICD9mCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM icd9m WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ICD9m> Refresh(long patNum){
			string command="SELECT * FROM icd9m WHERE PatNum = "+POut.Long(patNum);
			return Crud.ICD9mCrud.SelectMany(command);
		}
		///<summary>Gets one ICD9m from the db.</summary>
		public static ICD9m GetOne(long customerNum,long iCD9Num){
			return Crud.ICD9mCrud.SelectOne(customerNum,iCD9Num);
		}
		///<summary></summary>
		public static long Insert(ICD9m iCD9m){
			return Crud.ICD9mCrud.Insert(iCD9m,true);
		}
		///<summary></summary>
		public static void Update(ICD9m iCD9m){
			Crud.ICD9mCrud.Update(iCD9m);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long iCD9Num) {
			string command= "DELETE FROM icd9m WHERE CustomerNum = "+POut.Long(customerNum)+" AND ICD9Num = "+POut.Long(iCD9Num);
			Db.NonQ(command);
		}
		*/