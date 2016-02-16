//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Mobile.Crud.UsermCrud;
import OpenDentBusiness.Mobile.Userm;

/**
* 
*/
public class Userms   
{
    /**
    * Gets one Userm from the db.
    */
    public static Userm getOne(long customerNum, long usermNum) throws Exception {
        return UsermCrud.selectOne(customerNum,usermNum);
    }

    public static Userm getOne(String command) throws Exception {
        return UsermCrud.selectOne(command);
    }

    /**
    * 
    */
    public static long insert(Userm userm) throws Exception {
        return UsermCrud.Insert(userm, true);
    }

    /**
    * 
    */
    public static void update(Userm userm) throws Exception {
        UsermCrud.Update(userm);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<Userm> Refresh(long patNum){
			string command="SELECT * FROM userm WHERE PatNum = "+POut.Long(patNum);
			return Crud.UsermCrud.SelectMany(command);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long usermNum) {
			string command= "DELETE FROM userm WHERE CustomerNum = "+POut.Long(customerNum)+" AND UsermNum = "+POut.Long(usermNum);
			Db.NonQ(command);
		}
		///<summary>First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.</summary>
		public static List<Userm> ConvertListToM(List<User> list) {
			List<Userm> retVal=new List<Userm>();
			for(int i=0;i<list.Count;i++){
				retVal.Add(Crud.UsermCrud.ConvertToM(list[i]));
			}
			return retVal;
		}
		///<summary>Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().</summary>
		public static void UpdateFromChangeList(List<Userm> list,long customerNum) {
			for(int i=0;i<list.Count;i++){
				list[i].CustomerNum=customerNum;
				Userm userm=Crud.UsermCrud.SelectOne(customerNum,list[i].UsermNum);
				if(userm==null){//not in db
					Crud.UsermCrud.Insert(list[i],true);
				}
				else{
					Crud.UsermCrud.Update(list[i]);
				}
			}
		}
		*/