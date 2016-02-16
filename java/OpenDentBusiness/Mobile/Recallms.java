//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Crud.RecallmCrud;
import OpenDentBusiness.Mobile.Recallm;
import OpenDentBusiness.POut;
import OpenDentBusiness.Recall;
import OpenDentBusiness.Recalls;

/**
* 
*/
public class Recallms   
{
    /**
    * Gets all Recallm for a single patient
    */
    public static List<Recallm> getRecallms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from recallm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return RecallmCrud.selectMany(command);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceRecallNums(DateTime changedSince) throws Exception {
        return Recalls.getChangedSinceRecallNums(changedSince);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Recallm> getMultRecallms(List<long> recallNums) throws Exception {
        List<Recall> recallList = Recalls.GetMultRecalls(recallNums);
        List<Recallm> recallmList = ConvertListToM(recallList);
        return recallmList;
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Recallm> convertListToM(List<Recall> list) throws Exception {
        List<Recallm> retVal = new List<Recallm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(RecallmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Recallm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Recallm recallm = RecallmCrud.SelectOne(customerNum, list[i].RecallNum);
            if (recallm == null)
            {
                //not in db
                RecallmCrud.Insert(list[i], true);
            }
            else
            {
                RecallmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM recallm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


