//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:44 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.LabTurnaround;
import OpenDentBusiness.Meth;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.Schedules;

/**
* 
*/
public class LabTurnarounds   
{
    /**
    * 
    */
    public static List<LabTurnaround> getForLab(long laboratoryNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<LabTurnaround>>GetObject(MethodBase.GetCurrentMethod(), laboratoryNum);
        }
         
        String command = "SELECT * FROM labturnaround WHERE LaboratoryNum=" + POut.long(laboratoryNum);
        DataTable table = Db.getTable(command);
        List<LabTurnaround> retVal = new List<LabTurnaround>();
        LabTurnaround lab;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            lab = new LabTurnaround();
            lab.LabTurnaroundNum = PIn.Long(table.Rows[i][0].ToString());
            lab.LaboratoryNum = PIn.Long(table.Rows[i][1].ToString());
            lab.Description = PIn.String(table.Rows[i][2].ToString());
            lab.DaysPublished = PIn.Int(table.Rows[i][3].ToString());
            lab.DaysActual = PIn.Int(table.Rows[i][4].ToString());
            retVal.Add(lab);
        }
        return retVal;
    }

    /**
    * This is used when saving a laboratory.  All labturnarounds for the lab are deleted and recreated.  So the list that's passed in will not have the correct keys set.  The key columns will be ignored.
    */
    public static void setForLab(long labNum, List<LabTurnaround> lablist) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), labNum, lablist);
            return ;
        }
         
        String command = "DELETE FROM labturnaround WHERE LaboratoryNum=" + POut.long(labNum);
        Db.nonQ(command);
        for (int i = 0;i < lablist.Count;i++)
        {
            lablist[i].LaboratoryNum = labNum;
            Insert(lablist[i]);
        }
    }

    /**
    * 
    */
    public static long insert(LabTurnaround lab) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            lab.LabTurnaroundNum = Meth.GetLong(MethodBase.GetCurrentMethod(), lab);
            return lab.LabTurnaroundNum;
        }
         
        return Crud.LabTurnaroundCrud.Insert(lab);
    }

    /**
    * Calculates the due date by adding the number of business days listed.  Adds an additional day for each office holiday.
    */
    public static DateTime computeDueDate(DateTime startDate, int days) throws Exception {
        //No need to check RemotingRole; no call to db.
        DateTime date = startDate;
        int counter = 0;
        while (counter < days)
        {
            date = date.AddDays(1);
            if (date.DayOfWeek == DayOfWeek.Saturday || date.DayOfWeek == DayOfWeek.Sunday)
            {
                continue;
            }
             
            if (Schedules.dateIsHoliday(date))
            {
                continue;
            }
             
            counter++;
        }
        return date + TimeSpan.FromHours(17);
    }

}


//always due at 5pm on day specified.