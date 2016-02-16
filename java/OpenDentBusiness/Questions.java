//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:47 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.Question;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class Questions   
{
    /**
    * Gets a list of all Questions for a given patient.  Sorted by ItemOrder.
    */
    public static Question[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<Question[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM question WHERE PatNum=" + POut.long(patNum) + " ORDER BY ItemOrder";
        return Crud.QuestionCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(Question quest) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), quest);
            return ;
        }
         
        Crud.QuestionCrud.Update(quest);
    }

    /**
    * 
    */
    public static long insert(Question quest) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            quest.QuestionNum = Meth.GetLong(MethodBase.GetCurrentMethod(), quest);
            return quest.QuestionNum;
        }
         
        return Crud.QuestionCrud.Insert(quest);
    }

}


