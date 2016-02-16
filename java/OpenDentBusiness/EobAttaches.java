//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:43 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.EobAttach;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class EobAttaches   
{
    /**
    * Gets all EobAttaches for a given claimpayment.
    */
    public static List<EobAttach> refresh(long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<EobAttach>>GetObject(MethodBase.GetCurrentMethod(), claimPaymentNum);
        }
         
        String command = "SELECT * FROM eobattach WHERE ClaimPaymentNum=" + POut.long(claimPaymentNum) + " " + "ORDER BY DateTCreated";
        return Crud.EobAttachCrud.SelectMany(command);
    }

    /**
    * Gets one EobAttach from the db.
    */
    public static EobAttach getOne(long eobAttachNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<EobAttach>GetObject(MethodBase.GetCurrentMethod(), eobAttachNum);
        }
         
        return Crud.EobAttachCrud.SelectOne(eobAttachNum);
    }

    /**
    * Tests to see whether an attachment exists on this claimpayment.
    */
    public static boolean exists(long claimPaymentNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), claimPaymentNum);
        }
         
        String command = "SELECT COUNT(*) FROM eobattach WHERE ClaimPaymentNum=" + POut.long(claimPaymentNum);
        if (StringSupport.equals(Db.getScalar(command), "0"))
        {
            return false;
        }
         
        return true;
    }

    /**
    * Set the extension before calling.  Inserts a new eobattach into db, creates a filename based on EobAttachNum, and then updates the db with this filename.  Should always refresh the eobattach after calling this method in order to get the correct filename for RemotingRole.ClientWeb.
    */
    public static long insert(EobAttach eobAttach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            eobAttach.EobAttachNum = Meth.GetLong(MethodBase.GetCurrentMethod(), eobAttach);
            return eobAttach.EobAttachNum;
        }
         
        eobAttach.EobAttachNum = Crud.EobAttachCrud.Insert(eobAttach);
        //If the current filename is just an extension, then assign it a unique name.
        if (StringSupport.equals(eobAttach.FileName, Path.GetExtension(eobAttach.FileName)))
        {
            String extension = eobAttach.FileName;
            eobAttach.FileName = DateTime.Now.ToString("yyyyMMdd_HHmmss_") + eobAttach.EobAttachNum.ToString() + extension;
            update(eobAttach);
        }
         
        return eobAttach.EobAttachNum;
    }

    /**
    * 
    */
    public static void update(EobAttach eobAttach) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), eobAttach);
            return ;
        }
         
        Crud.EobAttachCrud.Update(eobAttach);
    }

    /**
    * 
    */
    public static void delete(long eobAttachNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), eobAttachNum);
            return ;
        }
         
        String command = "DELETE FROM eobattach WHERE EobAttachNum = " + POut.long(eobAttachNum);
        Db.nonQ(command);
    }

}


