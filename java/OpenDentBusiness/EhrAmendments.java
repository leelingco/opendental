//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:41 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.EhrAmendment;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EhrAmendments   
{
    /**
    * Gets list of all amendments for a specific patient and orders them by DateTRequest
    */
    public static List<EhrAmendment> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EhrAmendment>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM ehramendment WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateTRequest";
        return Crud.EhrAmendmentCrud.SelectMany(command);
    }

    /**
    * Gets one EhrAmendment from the db.
    */
    public static EhrAmendment getOne(long ehrAmendmentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EhrAmendment>GetObject(MethodBase.GetCurrentMethod(), ehrAmendmentNum);
        }
         
        return Crud.EhrAmendmentCrud.SelectOne(ehrAmendmentNum);
    }

    /**
    * 
    */
    public static long insert(EhrAmendment ehrAmendment) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            ehrAmendment.EhrAmendmentNum = Meth.GetLong(MethodBase.GetCurrentMethod(), ehrAmendment);
            return ehrAmendment.EhrAmendmentNum;
        }
         
        return Crud.EhrAmendmentCrud.Insert(ehrAmendment);
    }

    /**
    * 
    */
    public static void update(EhrAmendment ehrAmendment) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrAmendment);
            return ;
        }
         
        Crud.EhrAmendmentCrud.Update(ehrAmendment);
    }

    /**
    * 
    */
    public static void delete(long ehrAmendmentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), ehrAmendmentNum);
            return ;
        }
         
        String command = "DELETE FROM ehramendment WHERE EhrAmendmentNum = " + POut.long(ehrAmendmentNum);
        Db.nonQ(command);
    }

}


