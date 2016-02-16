//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:39 PM
//

package OpenDentBusiness;

import OpenDentBusiness.DashboardAR;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class DashboardARs   
{
    /**
    * Gets all rows gt= dateFrom.
    */
    public static List<DashboardAR> refresh(DateTime dateFrom) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<DashboardAR>>GetObject(MethodBase.GetCurrentMethod(), dateFrom);
        }
         
        String command = "SELECT * FROM dashboardar WHERE DateCalc >= " + POut.date(dateFrom);
        return Crud.DashboardARCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(DashboardAR dashboardAR) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            dashboardAR.DashboardARNum = Meth.GetLong(MethodBase.GetCurrentMethod(), dashboardAR);
            return dashboardAR.DashboardARNum;
        }
         
        return Crud.DashboardARCrud.Insert(dashboardAR);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary>Gets one DashboardAR from the db.</summary>
		public static DashboardAR GetOne(long dashboardARNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<DashboardAR>(MethodBase.GetCurrentMethod(),dashboardARNum);
			}
			return Crud.DashboardARCrud.SelectOne(dashboardARNum);
		}
		///<summary></summary>
		public static void Update(DashboardAR dashboardAR){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				Meth.GetVoid(MethodBase.GetCurrentMethod(),dashboardAR);
				return;
			}
			Crud.DashboardARCrud.Update(dashboardAR);
		}
		///<summary></summary>
		public static void Delete(long dashboardARNum) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				Meth.GetVoid(MethodBase.GetCurrentMethod(),dashboardARNum);
				return;
			}
			string command= "DELETE FROM dashboardar WHERE DashboardARNum = "+POut.Long(dashboardARNum);
			Db.NonQ(command);
		}
		*/