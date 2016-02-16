//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PayorType;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Sops;

/**
* 
*/
public class PayorTypes   
{
    /**
    * 
    */
    public static List<PayorType> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PayorType>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM payortype WHERE PatNum = " + POut.long(patNum) + " ORDER BY DateStart";
        return Crud.PayorTypeCrud.SelectMany(command);
    }

    /**
    * This will return "SopCode - Description" or empty string if the patient does not have a payor type entered.
    */
    public static String getCurrentDescription(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetString(MethodBase.GetCurrentMethod(), patNum);
        }
         
        PayorType payorType = getCurrentType(patNum);
        if (payorType == null)
        {
            return "";
        }
         
        return payorType.SopCode + " - " + Sops.getDescriptionFromCode(payorType.SopCode);
    }

    /**
    * Gets most recent PayorType for a patient.
    */
    public static PayorType getCurrentType(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PayorType>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = DbHelper.limitOrderBy("SELECT * FROM payortype WHERE PatNum=" + POut.long(patNum) + " ORDER BY DateStart DESC",1);
        return Crud.PayorTypeCrud.SelectOne(command);
    }

    /**
    * Gets one PayorType from the db.
    */
    public static PayorType getOne(long payorTypeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PayorType>GetObject(MethodBase.GetCurrentMethod(), payorTypeNum);
        }
         
        return Crud.PayorTypeCrud.SelectOne(payorTypeNum);
    }

    /**
    * 
    */
    public static long insert(PayorType payorType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            payorType.PayorTypeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), payorType);
            return payorType.PayorTypeNum;
        }
         
        return Crud.PayorTypeCrud.Insert(payorType);
    }

    /**
    * 
    */
    public static void update(PayorType payorType) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), payorType);
            return ;
        }
         
        Crud.PayorTypeCrud.Update(payorType);
    }

    /**
    * 
    */
    public static void delete(long payorTypeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), payorTypeNum);
            return ;
        }
         
        String command = "DELETE FROM payortype WHERE PayorTypeNum = " + POut.long(payorTypeNum);
        Db.nonQ(command);
    }

}


