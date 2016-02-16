//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.LabPanel;
import OpenDentBusiness.LabPanels;
import OpenDentBusiness.Mobile.Crud.LabPanelmCrud;
import OpenDentBusiness.Mobile.LabPanelm;
import OpenDentBusiness.POut;

/**
* 
*/
public class LabPanelms   
{
    /**
    * Gets all LabPanelm for a single patient
    */
    public static List<LabPanelm> getLabPanelms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from labpanelm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return LabPanelmCrud.selectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceLabPanelNums(DateTime changedSince, List<long> eligibleForUploadPatNumList) throws Exception {
        return LabPanels.GetChangedSinceLabPanelNums(changedSince, eligibleForUploadPatNumList);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<LabPanelm> getMultLabPanelms(List<long> labPanelNums) throws Exception {
        List<LabPanel> LabPanelList = LabPanels.GetMultLabPanels(labPanelNums);
        List<LabPanelm> LabPanelmList = ConvertListToM(LabPanelList);
        return LabPanelmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<LabPanelm> convertListToM(List<LabPanel> list) throws Exception {
        List<LabPanelm> retVal = new List<LabPanelm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(LabPanelmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    public static void updateFromChangeList(List<LabPanelm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            LabPanelm labPanelm = LabPanelmCrud.SelectOne(customerNum, list[i].LabPanelNum);
            if (labPanelm == null)
            {
                //not in db
                LabPanelmCrud.Insert(list[i], true);
            }
            else
            {
                LabPanelmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM labpanelm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

    /**
    * Delete all labpanels of a particular patient
    */
    public static void delete(long customerNum, long PatNum) throws Exception {
        String command = "DELETE FROM labpanelm WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(PatNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<LabPanelm> Refresh(long patNum){
			string command="SELECT * FROM labpanelm WHERE PatNum = "+POut.Long(patNum);
			return Crud.LabPanelmCrud.SelectMany(command);
		}
		///<summary></summary>
		public static long Insert(LabPanelm labPanelm){
			return Crud.LabPanelmCrud.Insert(labPanelm,true);
		}
		///<summary></summary>
		public static void Update(LabPanelm labPanelm){
			Crud.LabPanelmCrud.Update(labPanelm);
		}
		///<summary></summary>
		public static void Delete(long customerNum,long labPanelNum) {
			string command= "DELETE FROM labpanelm WHERE CustomerNum = "+POut.Long(customerNum)+" AND LabPanelNum = "+POut.Long(labPanelNum);
			Db.NonQ(command);
		}
		*/