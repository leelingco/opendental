//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CovSpan;
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
public class CovSpanCrud   
{
    /**
    * Gets one CovSpan object from the database using the primary key.  Returns null if not found.
    */
    public static CovSpan selectOne(long covSpanNum) throws Exception {
        String command = "SELECT * FROM covspan " + "WHERE CovSpanNum = " + POut.long(covSpanNum);
        List<CovSpan> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one CovSpan object from the database using a query.
    */
    public static CovSpan selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CovSpan> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of CovSpan objects from the database using a query.
    */
    public static List<CovSpan> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<CovSpan> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<CovSpan> tableToList(DataTable table) throws Exception {
        List<CovSpan> retVal = new List<CovSpan>();
        CovSpan covSpan;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            covSpan = new CovSpan();
            covSpan.CovSpanNum = PIn.Long(table.Rows[i]["CovSpanNum"].ToString());
            covSpan.CovCatNum = PIn.Long(table.Rows[i]["CovCatNum"].ToString());
            covSpan.FromCode = PIn.String(table.Rows[i]["FromCode"].ToString());
            covSpan.ToCode = PIn.String(table.Rows[i]["ToCode"].ToString());
            retVal.Add(covSpan);
        }
        return retVal;
    }

    /**
    * Inserts one CovSpan into the database.  Returns the new priKey.
    */
    public static long insert(CovSpan covSpan) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            covSpan.CovSpanNum = DbHelper.getNextOracleKey("covspan","CovSpanNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(covSpan, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        covSpan.CovSpanNum++;
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
            return Insert(covSpan, false);
        } 
    }

    /**
    * Inserts one CovSpan into the database.  Provides option to use the existing priKey.
    */
    public static long insert(CovSpan covSpan, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            covSpan.CovSpanNum = ReplicationServers.getKey("covspan","CovSpanNum");
        }
         
        String command = "INSERT INTO covspan (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "CovSpanNum,";
        }
         
        command += "CovCatNum,FromCode,ToCode) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(covSpan.CovSpanNum) + ",";
        }
         
        command += POut.long(covSpan.CovCatNum) + "," + "'" + POut.string(covSpan.FromCode) + "'," + "'" + POut.string(covSpan.ToCode) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            covSpan.CovSpanNum = Db.nonQ(command,true);
        } 
        return covSpan.CovSpanNum;
    }

    /**
    * Updates one CovSpan in the database.
    */
    public static void update(CovSpan covSpan) throws Exception {
        String command = "UPDATE covspan SET " + "CovCatNum =  " + POut.long(covSpan.CovCatNum) + ", " + "FromCode  = '" + POut.string(covSpan.FromCode) + "', " + "ToCode    = '" + POut.string(covSpan.ToCode) + "' " + "WHERE CovSpanNum = " + POut.long(covSpan.CovSpanNum);
        Db.nonQ(command);
    }

    /**
    * Updates one CovSpan in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(CovSpan covSpan, CovSpan oldCovSpan) throws Exception {
        String command = "";
        if (covSpan.CovCatNum != oldCovSpan.CovCatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CovCatNum = " + POut.long(covSpan.CovCatNum) + "";
        }
         
        if (!StringSupport.equals(covSpan.FromCode, oldCovSpan.FromCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FromCode = '" + POut.string(covSpan.FromCode) + "'";
        }
         
        if (!StringSupport.equals(covSpan.ToCode, oldCovSpan.ToCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ToCode = '" + POut.string(covSpan.ToCode) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE covspan SET " + command + " WHERE CovSpanNum = " + POut.long(covSpan.CovSpanNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one CovSpan from the database.
    */
    public static void delete(long covSpanNum) throws Exception {
        String command = "DELETE FROM covspan " + "WHERE CovSpanNum = " + POut.long(covSpanNum);
        Db.nonQ(command);
    }

}

