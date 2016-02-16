//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:48 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.RepeatCharge;

/**
* 
*/
public class RepeatCharges   
{
    /**
    * Gets a list of all RepeatCharges for a given patient.  Supply 0 to get a list for all patients.
    */
    public static RepeatCharge[] refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<RepeatCharge[]>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM repeatcharge";
        if (patNum != 0)
        {
            command += " WHERE PatNum = " + POut.long(patNum);
        }
         
        command += " ORDER BY DateStart";
        return Crud.RepeatChargeCrud.SelectMany(command).ToArray();
    }

    /**
    * 
    */
    public static void update(RepeatCharge charge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), charge);
            return ;
        }
         
        Crud.RepeatChargeCrud.Update(charge);
    }

    /**
    * 
    */
    public static long insert(RepeatCharge charge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            charge.RepeatChargeNum = Meth.GetLong(MethodBase.GetCurrentMethod(), charge);
            return charge.RepeatChargeNum;
        }
         
        return Crud.RepeatChargeCrud.Insert(charge);
    }

    /**
    * Called from FormRepeatCharge.
    */
    public static void delete(RepeatCharge charge) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), charge);
            return ;
        }
         
        String command = "UPDATE procedurelog SET RepeatChargeNum=0 WHERE RepeatChargeNum=" + POut.long(charge.RepeatChargeNum);
        Db.nonQ(command);
        command = "DELETE FROM repeatcharge WHERE RepeatChargeNum =" + POut.long(charge.RepeatChargeNum);
        Db.nonQ(command);
    }

    /**
    * Used in FormRepeatChargesUpdate to get a list of the dates of procedures that have the proccode and patnum specified.
    */
    public static ArrayList getDates(long codeNum, long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<ArrayList>GetObject(MethodBase.GetCurrentMethod(), codeNum, patNum);
        }
         
        ArrayList retVal = new ArrayList();
        String command = "SELECT ProcDate FROM procedurelog " + "WHERE PatNum=" + POut.long(patNum) + " AND CodeNum=" + POut.long(codeNum) + " AND ProcStatus=2";
        //complete
        DataTable table = Db.getTable(command);
        for (int i = 0;i < table.Rows.Count;i++)
        {
            retVal.Add(PIn.Date(table.Rows[i][0].ToString()));
        }
        return retVal;
    }

    /**
    * For internal use only. Returns the NewCrop repeating charges on the specified customer account. The NPI does not have its own field, it is stored in the repeating charge note.
    */
    public static List<RepeatCharge> getForNewCrop(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<RepeatCharge>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM repeatcharge " + "WHERE PatNum=" + POut.long(patNum) + " AND ProcCode LIKE 'NewCrop%'";
        return Crud.RepeatChargeCrud.SelectMany(command);
    }

}


