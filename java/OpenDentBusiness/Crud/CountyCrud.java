//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.County;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class CountyCrud   
{
    /**
    * Gets one County object from the database using the primary key.  Returns null if not found.
    */
    public static County selectOne(long countyNum) throws Exception {
        String command = "SELECT * FROM county " + "WHERE CountyNum = " + POut.long(countyNum);
        List<County> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one County object from the database using a query.
    */
    public static County selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<County> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of County objects from the database using a query.
    */
    public static List<County> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<County> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<County> tableToList(DataTable table) throws Exception {
        List<County> retVal = new List<County>();
        County county;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            county = new County();
            county.CountyNum = PIn.Long(table.Rows[i]["CountyNum"].ToString());
            county.CountyName = PIn.String(table.Rows[i]["CountyName"].ToString());
            county.CountyCode = PIn.String(table.Rows[i]["CountyCode"].ToString());
            retVal.Add(county);
        }
        return retVal;
    }

    /**
    * Inserts one County into the database.  Returns the new priKey.
    */
    public static long insert(County county) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            county.CountyNum = DbHelper.getNextOracleKey("county","CountyNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(county, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        county.CountyNum++;
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
            return Insert(county, false);
        } 
    }

    /**
    * Inserts one County into the database.  Provides option to use the existing priKey.
    */
    public static long insert(County county, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            county.CountyNum = ReplicationServers.getKey("county","CountyNum");
        }
         
        String command = "INSERT INTO county (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "CountyNum,";
        }
         
        command += "CountyName,CountyCode) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(county.CountyNum) + ",";
        }
         
        command += "'" + POut.string(county.CountyName) + "'," + "'" + POut.string(county.CountyCode) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            county.CountyNum = Db.nonQ(command,true);
        } 
        return county.CountyNum;
    }

    /**
    * Updates one County in the database.
    */
    public static void update(County county) throws Exception {
        String command = "UPDATE county SET " + "CountyName= '" + POut.string(county.CountyName) + "', " + "CountyCode= '" + POut.string(county.CountyCode) + "' " + "WHERE CountyNum = " + POut.long(county.CountyNum);
        Db.nonQ(command);
    }

    /**
    * Updates one County in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(County county, County oldCounty) throws Exception {
        String command = "";
        if (!StringSupport.equals(county.CountyName, oldCounty.CountyName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CountyName = '" + POut.string(county.CountyName) + "'";
        }
         
        if (!StringSupport.equals(county.CountyCode, oldCounty.CountyCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CountyCode = '" + POut.string(county.CountyCode) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE county SET " + command + " WHERE CountyNum = " + POut.long(county.CountyNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one County from the database.
    */
    public static void delete(long countyNum) throws Exception {
        String command = "DELETE FROM county " + "WHERE CountyNum = " + POut.long(countyNum);
        Db.nonQ(command);
    }

}

