//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PayPlanCharges   
{
    /**
    * Gets all PayPlanCharges for a guarantor or patient, ordered by date.
    */
    public static List<PayPlanCharge> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PayPlanCharge>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM payplancharge " + "WHERE Guarantor='" + POut.long(patNum) + "' " + "OR PatNum='" + POut.long(patNum) + "' " + "ORDER BY ChargeDate";
        return Crud.PayPlanChargeCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static List<PayPlanCharge> getForPayPlan(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PayPlanCharge>>GetObject(MethodBase.GetCurrentMethod(), payPlanNum);
        }
         
        String command = "SELECT * FROM payplancharge " + "WHERE PayPlanNum=" + POut.long(payPlanNum) + " ORDER BY ChargeDate";
        return Crud.PayPlanChargeCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static PayPlanCharge getOne(long payPlanChargeNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PayPlanCharge>GetObject(MethodBase.GetCurrentMethod(), payPlanChargeNum);
        }
         
        String command = "SELECT * FROM payplancharge " + "WHERE PayPlanChargeNum=" + POut.long(payPlanChargeNum);
        return Crud.PayPlanChargeCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static void update(PayPlanCharge charge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), charge);
            return ;
        }
         
        Crud.PayPlanChargeCrud.Update(charge);
    }

    /**
    * 
    */
    public static long insert(PayPlanCharge charge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            charge.PayPlanChargeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), charge);
            return charge.PayPlanChargeNum;
        }
         
        return Crud.PayPlanChargeCrud.Insert(charge);
    }

    /**
    * 
    */
    public static void delete(PayPlanCharge charge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), charge);
            return ;
        }
         
        String command = "DELETE from payplancharge WHERE PayPlanChargeNum = '" + POut.long(charge.PayPlanChargeNum) + "'";
        Db.nonQ(command);
    }

    /**
    * 
    */
    public static void deleteAllInPlan(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), payPlanNum);
            return ;
        }
         
        String command = "DELETE FROM payplancharge WHERE PayPlanNum=" + payPlanNum.ToString();
        Db.nonQ(command);
    }

    /**
    * When closing the payplan window, this sets all the charges to the appropriate provider and clinic.  This is the only way to set those fields.
    */
    public static void setProvAndClinic(long payPlanNum, long provNum, long clinicNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), payPlanNum, provNum, clinicNum);
            return ;
        }
         
        String command = "UPDATE payplancharge SET ProvNum=" + POut.long(provNum) + ", " + "ClinicNum=" + POut.long(clinicNum) + " " + "WHERE PayPlanNum=" + POut.long(payPlanNum);
        Db.nonQ(command);
    }

}


