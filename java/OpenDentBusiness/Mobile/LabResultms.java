//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.LabResult;
import OpenDentBusiness.LabResults;
import OpenDentBusiness.Mobile.Crud.LabResultmCrud;
import OpenDentBusiness.Mobile.LabResultm;
import OpenDentBusiness.POut;

/**
* 
*/
public class LabResultms   
{
    /**
    * Gets one LabResultm from the db.
    */
    public static LabResultm getOne(long customerNum, long labResultNum) throws Exception {
        return LabResultmCrud.selectOne(customerNum,labResultNum);
    }

    /**
    * Gets one LabResultm from the db.
    */
    public static List<LabResultm> getLabResultms(long customerNum, long labPanelNum) throws Exception {
        String command = "SELECT * from labresultm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND LabPanelNum = " + POut.long(labPanelNum);
        return LabResultmCrud.selectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceLabResultNums(DateTime changedSince) throws Exception {
        return LabResults.getChangedSinceLabResultNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<LabResultm> getMultLabResultms(List<long> labResultNums) throws Exception {
        List<LabResult> LabResultList = LabResults.GetMultLabResults(labResultNums);
        List<LabResultm> LabResultmList = ConvertListToM(LabResultList);
        return LabResultmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<LabResultm> convertListToM(List<LabResult> list) throws Exception {
        List<LabResultm> retVal = new List<LabResultm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(LabResultmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    public static void updateFromChangeList(List<LabResultm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            LabResultm labresultm = LabResultmCrud.SelectOne(customerNum, list[i].LabResultNum);
            if (labresultm == null)
            {
                //not in db
                LabResultmCrud.Insert(list[i], true);
            }
            else
            {
                LabResultmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM labresultm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<LabResultm> Refresh(long patNum){
			string command="SELECT * FROM labresultm WHERE PatNum = "+POut.Long(patNum);
			return Crud.LabResultmCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(LabResultm labResultm){
			return Crud.LabResultmCrud.Insert(labResultm,true);
		}
		///<summary></summary>
		public static void Update(LabResultm labResultm){
			Crud.LabResultmCrud.Update(labResultm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long labResultNum) {
			string command= "DELETE FROM labresultm WHERE CustomerNum = "+POut.Long(customerNum)+" AND LabResultNum = "+POut.Long(labResultNum);
			Db.NonQ(command);
		}
		*/