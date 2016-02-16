//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:46 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PlannedAppt;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PlannedAppts   
{
    /**
    * Gets all planned appt objects for a patient.
    */
    public static List<PlannedAppt> refresh(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<PlannedAppt>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM plannedappt WHERE PatNum=" + POut.long(patNum);
        return Crud.PlannedApptCrud.SelectMany(command);
    }

    /**
    * Gets one plannedAppt from the database.
    */
    public static PlannedAppt getOne(long plannedApptNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PlannedAppt>GetObject(MethodBase.GetCurrentMethod(), plannedApptNum);
        }
         
        return Crud.PlannedApptCrud.SelectOne(plannedApptNum);
    }

    /**
    * Gets one plannedAppt by patient, ordered by ItemOrder
    */
    public static PlannedAppt getOneOrderedByItemOrder(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<PlannedAppt>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM plannedappt WHERE PatNum=" + POut.long(patNum) + " ORDER BY ItemOrder";
        command = DbHelper.limitOrderBy(command,1);
        return Crud.PlannedApptCrud.SelectOne(command);
    }

    /**
    * 
    */
    public static long insert(PlannedAppt plannedAppt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            plannedAppt.PlannedApptNum = Meth.GetLong(MethodBase.GetCurrentMethod(), plannedAppt);
            return plannedAppt.PlannedApptNum;
        }
         
        return Crud.PlannedApptCrud.Insert(plannedAppt);
    }

    /**
    * 
    */
    public static void update(PlannedAppt plannedAppt) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), plannedAppt);
            return ;
        }
         
        Crud.PlannedApptCrud.Update(plannedAppt);
    }

}


