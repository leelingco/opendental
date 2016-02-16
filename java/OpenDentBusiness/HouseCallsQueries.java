//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:05 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

public class HouseCallsQueries   
{
    public static DataTable getHouseCalls(DateTime FromDate, DateTime ToDate) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.GetTable(MethodBase.GetCurrentMethod(), FromDate, ToDate);
        }
         
        //now, the query--------------------------------------------------------------------------
        //Appointment Reminder Fields- numbers are as they come back from db-----------------------
        //0-LastName
        //1-FirstName (or we substitute 2-Preferred Name if exists)
        // PatientNumber (Can be 3-PatNum or 4-ChartNumber, depending on what user selected)
        //5-HomePhone
        //6-WorkNumber
        //7-EmailAddress
        // SendEmail (this will be true if email address exists. Might change later)
        //8-Address
        //9-Address2 (although they did not offer this as an option)
        //10-City
        //11-State
        //12-Zip
        //13-ApptDate
        //13-ApptTime
        //14-ApptReason (procedures descriptions-user can't edit)
        //15-DoctorNumber (for the Doctor, we currently use the patient primary provider. Otherwise, we would run into trouble with appointments assigned to a specific hygienist.)
        //15-DoctorName
        //16-IsNewPatient
        //17-WirelessPhone
        //sched or ASAP
        //> midnight
        String command = "SELECT patient.LName,patient.FName,patient.Preferred\r\n" + 
        "\t\t\t\t,patient.PatNum,patient.ChartNumber,patient.HmPhone,patient.WkPhone\r\n" + 
        "\t\t\t\t,patient.Email,patient.Address,patient.Address2,patient.City,patient.State\r\n" + 
        "\t\t\t\t,patient.Zip\r\n" + 
        "\t\t\t\t,appointment.AptDateTime,appointment.ProcDescript\r\n" + 
        "\t\t\t\t,patient.PriProv\r\n" + 
        "\t\t\t\t,appointment.IsNewPatient,\r\n" + 
        "\t\t\t\tpatient.WirelessPhone\r\n" + 
        "\t\t\t\tFROM patient,appointment \r\n" + 
        "\t\t\t\tWHERE patient.PatNum=appointment.PatNum " + "AND (appointment.AptStatus=1 OR appointment.AptStatus=4) " + "AND appointment.AptDateTime > " + POut.date(FromDate) + " AND appointment.AptDateTime < " + POut.Date(ToDate.AddDays(1));
        return Db.getTable(command);
    }

}


//< midnight