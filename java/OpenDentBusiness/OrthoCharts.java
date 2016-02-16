//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:45 PM
//

package OpenDentBusiness;

import OpenDentBusiness.Db;
import OpenDentBusiness.Meth;
import OpenDentBusiness.OrthoChart;
import OpenDentBusiness.POut;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;

/**
* 
*/
public class OrthoCharts   
{
    /**
    * 
    */
    public static List<OrthoChart> getAll() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<OrthoChart>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        String command = "SELECT * FROM orthochart";
        return Crud.OrthoChartCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static List<OrthoChart> getAllForPatient(long patNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<OrthoChart>>GetObject(MethodBase.GetCurrentMethod(), patNum);
        }
         
        String command = "SELECT * FROM orthochart WHERE PatNum =" + POut.long(patNum);
        return Crud.OrthoChartCrud.SelectMany(command);
    }

    /**
    * Useful for distinct display fields.
    */
    public static List<OrthoChart> getByDistinctFieldNames() throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            return Meth.<List<OrthoChart>>GetObject(MethodBase.GetCurrentMethod());
        }
         
        //This is the simple querry that doesn't work with oracle
        //string command="SELECT * FROM orthochart GROUP BY FieldName";
        //This query was rewritten for Oracle support, it will provide the same results weather it is run in MySql or Oracle.
        String command = "SELECT * FROM orthochart, (SELECT MAX(OrthoChartNum) OrthoChartNum, FieldName FROM orthochart GROUP BY FieldName) uniqueSubTable WHERE orthochart.OrthoChartNum = uniqueSubTable.OrthoChartNum";
        return Crud.OrthoChartCrud.SelectMany(command);
    }

    /**
    * 
    */
    public static long insert(OrthoChart orthoChart) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            orthoChart.OrthoChartNum = Meth.GetLong(MethodBase.GetCurrentMethod(), orthoChart);
            return orthoChart.OrthoChartNum;
        }
         
        return Crud.OrthoChartCrud.Insert(orthoChart);
    }

    /**
    * 
    */
    public static void update(OrthoChart orthoChart) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), orthoChart);
            return ;
        }
         
        Crud.OrthoChartCrud.Update(orthoChart);
    }

    /**
    * 
    */
    public static void delete(long orthoChartNum) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            Meth.GetVoid(MethodBase.GetCurrentMethod(), orthoChartNum);
            return ;
        }
         
        String command = "DELETE FROM orthochart WHERE OrthoChartNum = " + POut.long(orthoChartNum);
        Db.nonQ(command);
    }

}


/*
		Only pull out the methods below as you need them.  Otherwise, leave them commented out.
		///<summary></summary>
		public static List<OrthoChart> Refresh(long patNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				return Meth.GetObject<List<OrthoChart>>(MethodBase.GetCurrentMethod(),patNum);
			}
			string command="SELECT * FROM orthochart WHERE PatNum = "+POut.Long(patNum);
			return Crud.OrthoChartCrud.SelectMany(command);
		}
		///<summary>Gets one OrthoChart from the db.</summary>
		public static OrthoChart GetOne(long orthoChartNum){
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb){
				return Meth.GetObject<OrthoChart>(MethodBase.GetCurrentMethod(),orthoChartNum);
			}
			return Crud.OrthoChartCrud.SelectOne(orthoChartNum);
		}
		
		*/