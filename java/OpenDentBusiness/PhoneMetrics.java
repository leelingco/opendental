//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:58 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Meth;
import OpenDentBusiness.PhoneMetric;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class PhoneMetrics   
{
    /**
    * 
    */
    public static long insert(PhoneMetric phoneMetric) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            phoneMetric.PhoneMetricNum = Meth.GetLong(MethodBase.GetCurrentMethod(), phoneMetric);
            return phoneMetric.PhoneMetricNum;
        }
         
        return Crud.PhoneMetricCrud.Insert(phoneMetric);
    }

    /**
    * Returns the average number of minutes behind rounded down for each half hour from 5:00 AM - 7:00 PM.
    */
    public static int[] averageMinutesBehind(DateTime date) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<int[]>GetObject(MethodBase.GetCurrentMethod(), date);
        }
         
        DateTime startTime = new DateTime(date.Year, date.Month, date.Day, 5, 0, 0);
        DateTime endTime = new DateTime(date.Year, date.Month, date.Day, 19, 0, 0);
        String command = "SELECT * FROM phonemetric WHERE DateTimeEntry BETWEEN " + POut.dateT(startTime) + " AND " + POut.dateT(endTime);
        List<PhoneMetric> listPhoneMetrics = Crud.PhoneMetricCrud.SelectMany(command);
        int[] avgMinBehind = new int[28];
        //Used in FormGraphEmployeeTime. One "bucket" every half hour.
        int numerator = new int();
        int denominator = new int();
        startTime = new DateTime(date.Year, date.Month, date.Day, 5, 0, 0);
        endTime = new DateTime(date.Year, date.Month, date.Day, 5, 30, 0);
        for (int i = 0;i < 28;i++)
        {
            numerator = 0;
            denominator = 0;
            for (int j = 0;j < listPhoneMetrics.Count;j++)
            {
                //reuse startTime and endTime for 30 minute intervals
                if (startTime < listPhoneMetrics[j].DateTimeEntry && listPhoneMetrics[j].DateTimeEntry < endTime)
                {
                    //startTime < time < endTime
                    numerator += listPhoneMetrics[j].MinutesBehind;
                    denominator++;
                }
                 
            }
            if (denominator > 0)
            {
                avgMinBehind[i] = numerator / denominator;
            }
            else
            {
                //denominator should usually be 30. Result will be rounded down due to integer math.
                avgMinBehind[i] = 0;
            } 
            startTime = startTime.AddMinutes(30);
            endTime = endTime.AddMinutes(30);
        }
        return avgMinBehind;
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<PhoneMetric> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<PhoneMetric>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM phonemetric WHERE PatNum = "+POut.Long(patNum);
			return Crud.PhoneMetricCrud.SelectMany(command);
		}
		///<summary>Gets one PhoneMetric from the db.</summary>
		public static PhoneMetric GetOne(long phoneMetricNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<PhoneMetric>(MethodBase.GetCurrentMethod(),phoneMetricNum);
			}
			return Crud.PhoneMetricCrud.SelectOne(phoneMetricNum);
		}
		///<summary></summary>
		public static void Update(PhoneMetric phoneMetric){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),phoneMetric);
				return;
			}
			Crud.PhoneMetricCrud.Update(phoneMetric);
		}
		///<summary></summary>
		public static void Delete(long phoneMetricNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),phoneMetricNum);
				return;
			}
			string command= "DELETE FROM phonemetric WHERE PhoneMetricNum = "+POut.Long(phoneMetricNum);
			Db.NonQ(command);
		}
		*/