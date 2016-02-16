//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:06 PM
//

package OpenDentBusiness.Mobile;

import OpenDentBusiness.Appointment;
import OpenDentBusiness.Appointments;
import OpenDentBusiness.Db;
import OpenDentBusiness.Mobile.Appointmentm;
import OpenDentBusiness.Mobile.Crud.AppointmentmCrud;
import OpenDentBusiness.POut;

/**
* 
*/
public class Appointmentms   
{
    /**
    * Gets one Appointmentm from the db.
    */
    public static Appointmentm getOne(long customerNum, long aptNum) throws Exception {
        return AppointmentmCrud.selectOne(customerNum,aptNum);
    }

    /**
    * Gets Appointmentm from the db as specified by the date range and provider.
    */
    public static List<Appointmentm> getAppointmentms(long customerNum, long provNum, DateTime startDate, DateTime endDate) throws Exception {
        String command = "SELECT * from appointmentm " + "WHERE AptDateTime BETWEEN '" + POut.date(startDate,false) + "' AND '" + POut.Date(endDate.AddDays(1), false) + "' " + "AND CustomerNum = " + POut.long(customerNum) + " " + "AND (ProvNum = " + POut.long(provNum) + " " + "OR (IsHygiene = 1 AND ProvHyg = " + POut.long(provNum) + ")) " + "ORDER BY AptDateTime";
        return AppointmentmCrud.selectMany(command);
    }

    /**
    * Gets Appointmentm from the db as specified by the date range.
    */
    public static List<Appointmentm> getAppointmentms(long customerNum, DateTime startDate, DateTime endDate) throws Exception {
        String command = "SELECT * from appointmentm " + "WHERE AptDateTime BETWEEN '" + POut.date(startDate,false) + "' AND '" + POut.Date(endDate.AddDays(1), false) + "' " + "AND CustomerNum = " + POut.long(customerNum) + " " + "ORDER BY AptDateTime";
        return AppointmentmCrud.selectMany(command);
    }

    /**
    * Gets all Appointmentm for a single patient.
    */
    public static List<Appointmentm> getAppointmentms(long customerNum, long patNum) throws Exception {
        String command = "SELECT * from appointmentm " + "WHERE CustomerNum = " + POut.long(customerNum) + " AND PatNum = " + POut.long(patNum);
        return AppointmentmCrud.selectMany(command);
    }

    /**
    * First use GetChangedSince.  Then, use this to convert the list a list of 'm' objects.
    */
    public static List<Appointmentm> convertListToM(List<Appointment> list) throws Exception {
        List<Appointmentm> retVal = new List<Appointmentm>();
        for (int i = 0;i < list.Count;i++)
        {
            retVal.Add(AppointmentmCrud.ConvertToM(list[i]));
        }
        return retVal;
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<long> getChangedSinceAptNums(DateTime changedSince, DateTime excludeOlderThan) throws Exception {
        return Appointments.getChangedSinceAptNums(changedSince,excludeOlderThan);
    }

    /**
    * The values returned are sent to the webserver.
    */
    public static List<Appointmentm> getMultApts(List<long> aptNums) throws Exception {
        List<Appointment> aptList = Appointments.GetMultApts(aptNums);
        List<Appointmentm> aptmList = ConvertListToM(aptList);
        return aptmList;
    }

    /**
    * Only run on server for mobile.  Takes the list of changes from the dental office and makes updates to those items in the mobile server db.  Also, make sure to run DeletedObjects.DeleteForMobile().
    */
    public static void updateFromChangeList(List<Appointmentm> list, long customerNum) throws Exception {
        for (int i = 0;i < list.Count;i++)
        {
            list[i].CustomerNum = customerNum;
            Appointmentm appointmentm = AppointmentmCrud.SelectOne(customerNum, list[i].AptNum);
            if (appointmentm == null)
            {
                //not in db
                AppointmentmCrud.Insert(list[i], true);
            }
            else
            {
                AppointmentmCrud.Update(list[i]);
            } 
        }
    }

    /**
    * used in tandem with Full synch
    */
    public static void deleteAll(long customerNum) throws Exception {
        String command = "DELETE FROM appointmentm WHERE CustomerNum = " + POut.long(customerNum);
            ;
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static long Insert(Appointmentm appointmentm) {
			return Crud.AppointmentmCrud.Insert(appointmentm,true);
		}
		
		///<summary></summary>
		public static void Update(Appointmentm appointmentm) {
			Crud.AppointmentmCrud.Update(appointmentm);
		}
		 
		///<summary></summary>
		public static void Delete(long customerNum,long aptNum) {
			string command= "DELETE FROM appointmentm WHERE CustomerNum = "+POut.Long(customerNum)+" AND AptNum = "+POut.Long(aptNum);
			Db.NonQ(command);
		}
		 
		///<summary></summary>
		public static List<Appointmentm> Refresh(long patNum){
			string command="SELECT * FROM appointmentm WHERE PatNum = "+POut.Long(patNum);
			return Crud.AppointmentmCrud.SelectMany(command);
		}
		
		
		*/