//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;

import OpenDentBusiness.OdSqlParameter;

/**
* You don't generally use this class.  Use Db instead except in special situations.
*/
public class DataCore   
{
    /**
    * 
    */
    public static DataTable getTable(String command) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        DataTable table = dcon.getTable(command);
        return table;
    }

    //DataSet retVal=new DataSet();
    //table.TableName="table";
    //retVal.Tables.Add(table);
    //retVal.Tables[0].TableName="";
    //retVal;
    /**
    * Only used if using the server component.  This is used for queries written by the user.  It uses the user with lower privileges  to prevent injection attack.
    */
    public static DataTable getTableLow(String command) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection(true);
        DataTable table = dcon.getTable(command);
        return table;
    }

    //DataSet retVal=new DataSet();
    //retVal.Tables.Add(table);
    /**
    * This query is run with full privileges.  This is for commands generated by the main program, and the user will not have access for injection attacks.  Result is usually number of rows changed, or can be insert id if requested.
    */
    public static long nonQ(String command, boolean getInsertID, OdSqlParameter... parameters) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        long rowsChanged = dcon.NonQ(command, getInsertID, parameters);
        if (getInsertID)
        {
            return (long)dcon.InsertID;
        }
        else
        {
            return rowsChanged;
        } 
    }

    public static long nonQ(String command, OdSqlParameter... parameters) throws Exception {
        return NonQ(command, false, parameters);
    }

    /*
    		///<summary>This is for multiple queries all concatenated together with ;</summary>
    		public static DataSet GetDataSet(string commands){
    			DataConnection dcon=new DataConnection();
    			//DataTable table=dcon.GetTable(command);
    			DataSet retVal=dcon.GetDs(commands);
    			//retVal.Tables.Add(table);
    			return retVal;
    		}*/
    /**
    * Get one single value.
    */
    public static String getScalar(String command) throws Exception {
        OpenDentBusiness.DataConnection dcon = new OpenDentBusiness.DataConnection();
        return dcon.getScalar(command);
    }

}

