//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.InsFilingCode;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class InsFilingCodeCrud   
{
    /**
    * Gets one InsFilingCode object from the database using the primary key.  Returns null if not found.
    */
    public static InsFilingCode selectOne(long insFilingCodeNum) throws Exception {
        String command = "SELECT * FROM insfilingcode " + "WHERE InsFilingCodeNum = " + POut.long(insFilingCodeNum);
        List<InsFilingCode> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one InsFilingCode object from the database using a query.
    */
    public static InsFilingCode selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<InsFilingCode> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of InsFilingCode objects from the database using a query.
    */
    public static List<InsFilingCode> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<InsFilingCode> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<InsFilingCode> tableToList(DataTable table) throws Exception {
        List<InsFilingCode> retVal = new List<InsFilingCode>();
        InsFilingCode insFilingCode;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            insFilingCode = new InsFilingCode();
            insFilingCode.InsFilingCodeNum = PIn.Long(table.Rows[i]["InsFilingCodeNum"].ToString());
            insFilingCode.Descript = PIn.String(table.Rows[i]["Descript"].ToString());
            insFilingCode.EclaimCode = PIn.String(table.Rows[i]["EclaimCode"].ToString());
            insFilingCode.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            retVal.Add(insFilingCode);
        }
        return retVal;
    }

    /**
    * Inserts one InsFilingCode into the database.  Returns the new priKey.
    */
    public static long insert(InsFilingCode insFilingCode) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            insFilingCode.InsFilingCodeNum = DbHelper.getNextOracleKey("insfilingcode","InsFilingCodeNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(insFilingCode, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        insFilingCode.InsFilingCodeNum++;
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
            return Insert(insFilingCode, false);
        } 
    }

    /**
    * Inserts one InsFilingCode into the database.  Provides option to use the existing priKey.
    */
    public static long insert(InsFilingCode insFilingCode, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            insFilingCode.InsFilingCodeNum = ReplicationServers.getKey("insfilingcode","InsFilingCodeNum");
        }
         
        String command = "INSERT INTO insfilingcode (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "InsFilingCodeNum,";
        }
         
        command += "Descript,EclaimCode,ItemOrder) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(insFilingCode.InsFilingCodeNum) + ",";
        }
         
        command += "'" + POut.string(insFilingCode.Descript) + "'," + "'" + POut.string(insFilingCode.EclaimCode) + "'," + POut.int(insFilingCode.ItemOrder) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            insFilingCode.InsFilingCodeNum = Db.nonQ(command,true);
        } 
        return insFilingCode.InsFilingCodeNum;
    }

    /**
    * Updates one InsFilingCode in the database.
    */
    public static void update(InsFilingCode insFilingCode) throws Exception {
        String command = "UPDATE insfilingcode SET " + "Descript        = '" + POut.string(insFilingCode.Descript) + "', " + "EclaimCode      = '" + POut.string(insFilingCode.EclaimCode) + "', " + "ItemOrder       =  " + POut.int(insFilingCode.ItemOrder) + " " + "WHERE InsFilingCodeNum = " + POut.long(insFilingCode.InsFilingCodeNum);
        Db.nonQ(command);
    }

    /**
    * Updates one InsFilingCode in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(InsFilingCode insFilingCode, InsFilingCode oldInsFilingCode) throws Exception {
        String command = "";
        if (!StringSupport.equals(insFilingCode.Descript, oldInsFilingCode.Descript))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Descript = '" + POut.string(insFilingCode.Descript) + "'";
        }
         
        if (!StringSupport.equals(insFilingCode.EclaimCode, oldInsFilingCode.EclaimCode))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EclaimCode = '" + POut.string(insFilingCode.EclaimCode) + "'";
        }
         
        if (insFilingCode.ItemOrder != oldInsFilingCode.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.int(insFilingCode.ItemOrder) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE insfilingcode SET " + command + " WHERE InsFilingCodeNum = " + POut.long(insFilingCode.InsFilingCodeNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one InsFilingCode from the database.
    */
    public static void delete(long insFilingCodeNum) throws Exception {
        String command = "DELETE FROM insfilingcode " + "WHERE InsFilingCodeNum = " + POut.long(insFilingCodeNum);
        Db.nonQ(command);
    }

}


