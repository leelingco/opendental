//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.DiseaseDef;
import OpenDentBusiness.DiseaseDefs;
import OpenDentBusiness.Mobile.Crud.DiseaseDefmCrud;
import OpenDentBusiness.Mobile.DiseaseDefm;
import OpenDentBusiness.POut;

/**
* 
*/
public class DiseaseDefms   
{
    /**
    * Gets one Medicationm from the db.
    */
    public static DiseaseDefm getOne(long customerNum, long diseaseNum) throws Exception {
        return DiseaseDefmCrud.selectOne(customerNum,diseaseNum);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceDiseaseDefNums(DateTime changedSince) throws Exception {
        return DiseaseDefs.getChangedSinceDiseaseDefNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<DiseaseDefm> getMultDiseaseDefms(List<long> diseaseDefNums) throws Exception {
        List<DiseaseDef> DiseaseDefList = DiseaseDefs.GetMultDiseaseDefs(diseaseDefNums);
        List<DiseaseDefm> DiseaseDefmList = ConvertListToM(DiseaseDefList);
        return DiseaseDefmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<DiseaseDefm> convertListToM(List<DiseaseDef> list) throws Exception {
        List<DiseaseDefm> retVal = new List<DiseaseDefm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(DiseaseDefmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<DiseaseDefm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            DiseaseDefm diseaseDefm = DiseaseDefmCrud.SelectOne(customerNum, list[i].DiseaseDefNum);
            if (diseaseDefm == null)
            {
                //not in db
                DiseaseDefmCrud.Insert(list[i], true);
            }
            else
            {
                DiseaseDefmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM diseasedefm WHERE CustomerNum = " + POut.long(customerNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<DiseaseDefm> Refresh(long patNum){
			string command="SELECT * FROM diseasedefm WHERE PatNum = "+POut.Long(patNum);
			return Crud.DiseaseDefmCrud.SelectMany(command);
		}
		///<summary>Gets one DiseaseDefm from the db.</summary>
		public static DiseaseDefm GetOne(long customerNum,long diseaseDefNum){
			return Crud.DiseaseDefmCrud.SelectOne(customerNum,diseaseDefNum);
		}
		///<summary></summary>
		public static long Insert(DiseaseDefm diseaseDefm){
			return Crud.DiseaseDefmCrud.Insert(diseaseDefm,true);
		}
		///<summary></summary>
		public static void Update(DiseaseDefm diseaseDefm){
			Crud.DiseaseDefmCrud.Update(diseaseDefm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long diseaseDefNum) {
			string command= "DELETE FROM diseasedefm WHERE CustomerNum = "+POut.Long(customerNum)+" AND DiseaseDefNum = "+POut.Long(diseaseDefNum);
			Db.NonQ(command);
		}
		*/