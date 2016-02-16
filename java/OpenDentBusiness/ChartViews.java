//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:37 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Cache;
import OpenDentBusiness.ChartView;
import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class ChartViews   
{
    //This region can be eliminated if this is not a table type with cached data.
    //If leaving this region in place, be sure to add RefreshCache and FillCache
    //to the Cache.cs file with all the other Cache types.
    /**
    * A list of all ChartViews.
    */
    private static List<ChartView> listt = new List<ChartView>();
    /**
    * A list of all ChartViews.
    */
    public static List<ChartView> getListt() throws Exception {
        if (listt == null)
        {
            refreshCache();
        }
         
        return listt;
    }

    public static void setListt(List<ChartView> value) throws Exception {
        listt = value;
    }

    /**
    * 
    */
    public static DataTable refreshCache() throws Exception {
        //No need to check RemotingRole; Calls GetTableRemotelyIfNeeded().
        String command = "SELECT * FROM chartview ORDER BY ItemOrder";
        //stub query probably needs to be changed
        DataTable table = Cache.GetTableRemotelyIfNeeded(MethodBase.GetCurrentMethod(), command);
        table.TableName = "ChartView";
        fillCache(table);
        return table;
    }

    /**
    * 
    */
    public static void fillCache(DataTable table) throws Exception {
        //No need to check RemotingRole; no call to db.
        listt = Crud.ChartViewCrud.TableToList(table);
    }

    /**
    * 
    */
    public static long insert(ChartView chartView) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            chartView.ChartViewNum = Meth.GetLong(MethodBase.GetCurrentMethod(), chartView);
            return chartView.ChartViewNum;
        }
         
        return Crud.ChartViewCrud.Insert(chartView);
    }

    /**
    * 
    */
    public static void update(ChartView chartView) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), chartView);
            return ;
        }
         
        Crud.ChartViewCrud.Update(chartView);
    }

    /**
    * 
    */
    public static void delete(long chartViewNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), chartViewNum);
            return ;
        }
         
        String command = "DELETE FROM chartview WHERE ChartViewNum = " + POut.long(chartViewNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<ChartView> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<ChartView>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM chartview WHERE PatNum = "+POut.Long(patNum);
			return Crud.ChartViewCrud.SelectMany(command);
		}
		///<summary>Gets one ChartView from the db.</summary>
		public static ChartView GetOne(long chartViewNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<ChartView>(MethodBase.GetCurrentMethod(),chartViewNum);
			}
			return Crud.ChartViewCrud.SelectOne(chartViewNum);
		}
		*/