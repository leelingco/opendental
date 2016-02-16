//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class OrthoChartCrud   
{
    /**
    * Gets one OrthoChart object from the database using the primary key.  Returns null if not found.
    */
    public static OrthoChart selectOne(long orthoChartNum) throws Exception {
        String command = "SELECT * FROM orthochart " + "WHERE OrthoChartNum = " + POut.Long(orthoChartNum);
        List<OrthoChart> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one OrthoChart object from the database using a query.
    */
    public static OrthoChart selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<OrthoChart> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of OrthoChart objects from the database using a query.
    */
    public static List<OrthoChart> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<OrthoChart> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<OrthoChart> tableToList(DataTable table) throws Exception {
        List<OrthoChart> retVal = new List<OrthoChart>();
        OrthoChart orthoChart = new OrthoChart();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            orthoChart = new OrthoChart();
            orthoChart.OrthoChartNum = PIn.Long(table.Rows[i]["OrthoChartNum"].ToString());
            orthoChart.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            orthoChart.DateService = PIn.Date(table.Rows[i]["DateService"].ToString());
            orthoChart.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            orthoChart.FieldValue = PIn.String(table.Rows[i]["FieldValue"].ToString());
            retVal.Add(orthoChart);
        }
        return retVal;
    }

    /**
    * Inserts one OrthoChart into the database.  Returns the new priKey.
    */
    public static long insert(OrthoChart orthoChart) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            orthoChart.OrthoChartNum = DbHelper.GetNextOracleKey("orthochart", "OrthoChartNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(orthoChart,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        orthoChart.OrthoChartNum++;
                        loopcount++;
                    }
                    else
                    {
                        throw ex;
                    } 
                }
            
            }
            throw new ApplicationException("Insert failed.  Could not generate primary key.");
        }
        else
        {
            return insert(orthoChart,false);
        } 
    }

    /**
    * Inserts one OrthoChart into the database.  Provides option to use the existing priKey.
    */
    public static long insert(OrthoChart orthoChart, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            orthoChart.OrthoChartNum = ReplicationServers.GetKey("orthochart", "OrthoChartNum");
        }
         
        String command = "INSERT INTO orthochart (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "OrthoChartNum,";
        }
         
        command += "PatNum,DateService,FieldName,FieldValue) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(orthoChart.OrthoChartNum) + ",";
        }
         
        command += POut.Long(orthoChart.PatNum) + "," + POut.Date(orthoChart.DateService) + "," + "'" + POut.String(orthoChart.FieldName) + "'," + "'" + POut.String(orthoChart.FieldValue) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            orthoChart.OrthoChartNum = Db.NonQ(command, true);
        } 
        return orthoChart.OrthoChartNum;
    }

    /**
    * Updates one OrthoChart in the database.
    */
    public static void update(OrthoChart orthoChart) throws Exception {
        String command = "UPDATE orthochart SET " + "PatNum       =  " + POut.Long(orthoChart.PatNum) + ", " + "DateService  =  " + POut.Date(orthoChart.DateService) + ", " + "FieldName    = '" + POut.String(orthoChart.FieldName) + "', " + "FieldValue   = '" + POut.String(orthoChart.FieldValue) + "' " + "WHERE OrthoChartNum = " + POut.Long(orthoChart.OrthoChartNum);
        Db.NonQ(command);
    }

    /**
    * Updates one OrthoChart in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(OrthoChart orthoChart, OrthoChart oldOrthoChart) throws Exception {
        String command = "";
        if (orthoChart.PatNum != oldOrthoChart.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.Long(orthoChart.PatNum) + "";
        }
         
        if (orthoChart.DateService != oldOrthoChart.DateService)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateService = " + POut.Date(orthoChart.DateService) + "";
        }
         
        if (orthoChart.FieldName != oldOrthoChart.FieldName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.String(orthoChart.FieldName) + "'";
        }
         
        if (orthoChart.FieldValue != oldOrthoChart.FieldValue)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldValue = '" + POut.String(orthoChart.FieldValue) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE orthochart SET " + command + " WHERE OrthoChartNum = " + POut.Long(orthoChart.OrthoChartNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one OrthoChart from the database.
    */
    public static void delete(long orthoChartNum) throws Exception {
        String command = "DELETE FROM orthochart " + "WHERE OrthoChartNum = " + POut.Long(orthoChartNum);
        Db.NonQ(command);
    }

}


