//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.FormPat;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class FormPats   
{
    /**
    * 
    */
    public static long insert(FormPat Cur) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Cur.FormPatNum = Meth.GetLong(MethodBase.GetCurrentMethod(), Cur);
            return Cur.FormPatNum;
        }
         
        return Crud.FormPatCrud.Insert(Cur);
    }

    public static FormPat getOne(long formPatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<FormPat>GetObject(MethodBase.GetCurrentMethod(), formPatNum);
        }
         
        String command = "SELECT * FROM formpat WHERE FormPatNum=" + POut.long(formPatNum);
        FormPat formpat = Crud.FormPatCrud.SelectOne(formPatNum);
        if (formpat == null)
        {
            return null;
        }
         
        //should never happen.
        command = "SELECT * FROM question WHERE FormPatNum=" + POut.long(formPatNum);
        formpat.QuestionList = Crud.QuestionCrud.SelectMany(command);
        return formpat;
    }

    /**
    * 
    */
    public static void delete(long formPatNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), formPatNum);
            return ;
        }
         
        String command = "DELETE FROM formpat WHERE FormPatNum=" + POut.long(formPatNum);
        Db.nonQ(command);
        command = "DELETE FROM question WHERE FormPatNum=" + POut.long(formPatNum);
        Db.nonQ(command);
    }

}


