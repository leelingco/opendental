//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.Lans;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PayPlan;
import OpenDentBusiness.PayPlanCharge;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PayPlans   
{
    /**
    * Gets a list of all payplans for a given patient, whether they are the patient or the guarantor.  This is only used in one place, when deleting a patient to check dependencies.
    */
    public static int getDependencyCount(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetInt(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT COUNT(*) FROM payplan" + " WHERE PatNum = " + POut.long(patNum) + " OR Guarantor = " + POut.long(patNum);
        return PIn.int(Db.getScalar(command));
    }

    public static PayPlan getOne(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PayPlan>GetObject(MethodBase.GetCurrentMethod(), payPlanNum);
        }
         
        return Crud.PayPlanCrud.SelectOne(payPlanNum);
    }

    /**
    * Determines if there are any valid plans with that patient as the guarantor.
    */
    public static List<PayPlan> getValidPlansNoIns(long guarNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PayPlan>>GetObject(MethodBase.GetCurrentMethod(), guarNum);
        }
         
        String command = "SELECT * FROM payplan" + " WHERE Guarantor = " + POut.long(guarNum) + " AND PlanNum = 0" + " ORDER BY payplandate";
        return Crud.PayPlanCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(PayPlan payPlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            payPlan.PayPlanNum = Meth.GetLong(MethodBase.GetCurrentMethod(), payPlan);
            return payPlan.PayPlanNum;
        }
         
        return Crud.PayPlanCrud.Insert(payPlan);
    }

    /**
    * 
    */
    public static void update(PayPlan payPlan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), payPlan);
            return ;
        }
         
        Crud.PayPlanCrud.Update(payPlan);
    }

    /**
    * Called from FormPayPlan.  Also deletes all attached payplancharges.  Throws exception if there are any paysplits attached.
    */
    public static void delete(PayPlan plan) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), plan);
            return ;
        }
         
        String command = "SELECT COUNT(*) FROM paysplit WHERE PayPlanNum=" + plan.PayPlanNum.ToString();
        if (!StringSupport.equals(Db.getCount(command), "0"))
        {
            throw new ApplicationException(Lans.g("PayPlans","You cannot delete a payment plan with payments attached.  Unattach the payments first."));
        }
         
        command = "DELETE FROM payplancharge WHERE PayPlanNum=" + plan.PayPlanNum.ToString();
        Db.nonQ(command);
        command = "DELETE FROM payplan WHERE PayPlanNum =" + plan.PayPlanNum.ToString();
        Db.nonQ(command);
    }

    /**
    * Gets info directly from database. Used from PayPlan and Account windows to get the amount paid so far on one payment plan.
    */
    public static double getAmtPaid(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<double>GetObject(MethodBase.GetCurrentMethod(), payPlanNum);
        }
         
        String command = "SELECT SUM(paysplit.SplitAmt) FROM paysplit " + "WHERE paysplit.PayPlanNum = '" + payPlanNum.ToString() + "' " + "GROUP BY paysplit.PayPlanNum";
        DataTable table = Db.getTable(command);
        if (table.Rows.Count == 0)
        {
            return 0;
        }
         
        return PIn.Double(table.Rows[0][0].ToString());
    }

    /**
    * Gets info directly from database. Used when adding a payment.
    */
    public static boolean planIsPaidOff(long payPlanNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetBool(MethodBase.GetCurrentMethod(), payPlanNum);
        }
         
        String command = "SELECT SUM(paysplit.SplitAmt) FROM paysplit " + "WHERE PayPlanNum = " + POut.long(payPlanNum);
        // +"' "
        //+" GROUP BY paysplit.PayPlanNum";
        double amtPaid = PIn.double(Db.getScalar(command));
        command = "SELECT SUM(Principal+Interest) FROM payplancharge " + "WHERE PayPlanNum = " + POut.long(payPlanNum);
        double totalCost = PIn.double(Db.getScalar(command));
        if (totalCost - amtPaid < .01)
        {
            return true;
        }
         
        return false;
    }

    /**
    * Used from FormPayPlan, Account, and ComputeBal to get the accumulated amount due for a payment plan based on today's date.  Includes interest, but does not include payments made so far.  The chargelist must include all charges for this payplan, but it can include more as well.
    */
    public static double getAccumDue(long payPlanNum, List<PayPlanCharge> chargeList) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < chargeList.Count;i++)
        {
            if (chargeList[i].PayPlanNum != payPlanNum)
            {
                continue;
            }
             
            if (chargeList[i].ChargeDate > DateTime.Today)
            {
                continue;
            }
             
            //not due yet
            retVal += chargeList[i].Principal + chargeList[i].Interest;
        }
        return retVal;
    }

    /**
    * Used from Account window to get the amount paid so far on one payment plan.  Must pass in the total amount paid and the returned value will not be more than this.  The chargelist must include all charges for this payplan, but it can include more as well.  It will loop sequentially through the charges to get just the principal portion.
    */
    public static double getPrincPaid(double amtPaid, long payPlanNum, List<PayPlanCharge> chargeList) throws Exception {
        //No need to check RemotingRole; no call to db.
        //amtPaid gets reduced to 0 throughout this loop.
        double retVal = 0;
        for (int i = 0;i < chargeList.Count;i++)
        {
            if (chargeList[i].PayPlanNum != payPlanNum)
            {
                continue;
            }
             
            //For this charge, first apply payment to interest
            if (amtPaid > chargeList[i].Interest)
            {
                amtPaid -= chargeList[i].Interest;
            }
            else
            {
                //interest will eat up the remainder of the payment
                amtPaid = 0;
                break;
            } 
            //Then, apply payment to principal
            if (amtPaid > chargeList[i].Principal)
            {
                retVal += chargeList[i].Principal;
                amtPaid -= chargeList[i].Principal;
            }
            else
            {
                //principal will eat up the remainder of the payment
                retVal += amtPaid;
                amtPaid = 0;
                break;
            } 
        }
        return retVal;
    }

    /**
    * Used from Account and ComputeBal to get the total amount of the original principal for one payment plan.  Does not include any interest.The chargelist must include all charges for this payplan, but it can include more as well.
    */
    public static double getTotalPrinc(long payPlanNum, List<PayPlanCharge> chargeList) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < chargeList.Count;i++)
        {
            if (chargeList[i].PayPlanNum != payPlanNum)
            {
                continue;
            }
             
            retVal += chargeList[i].Principal;
        }
        return retVal;
    }

    /**
    * Returns the sum of all payment plan entries for guarantor and/or patient.
    */
    public static double computeBal(long patNum, PayPlan[] list, List<PayPlanCharge> chargeList) throws Exception {
        //No need to check RemotingRole; no call to db.
        double retVal = 0;
        for (int i = 0;i < list.Length;i++)
        {
            //one or both of these conditions may be met:
            if (list[i].Guarantor == patNum)
            {
                retVal += GetAccumDue(list[i].PayPlanNum, chargeList);
            }
             
            if (list[i].PatNum == patNum)
            {
                retVal -= GetTotalPrinc(list[i].PayPlanNum, chargeList);
            }
             
        }
        return retVal;
    }

}


