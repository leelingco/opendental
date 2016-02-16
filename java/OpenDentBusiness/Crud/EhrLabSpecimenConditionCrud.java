//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:02 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EhrLabSpecimenCondition;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EhrLabSpecimenConditionCrud   
{
    /**
    * Gets one EhrLabSpecimenCondition object from the database using the primary key.  Returns null if not found.
    */
    public static EhrLabSpecimenCondition selectOne(long ehrLabSpecimenConditionNum) throws Exception {
        String command = "SELECT * FROM ehrlabspecimencondition " + "WHERE EhrLabSpecimenConditionNum = " + POut.long(ehrLabSpecimenConditionNum);
        List<EhrLabSpecimenCondition> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EhrLabSpecimenCondition object from the database using a query.
    */
    public static EhrLabSpecimenCondition selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrLabSpecimenCondition> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EhrLabSpecimenCondition objects from the database using a query.
    */
    public static List<EhrLabSpecimenCondition> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EhrLabSpecimenCondition> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EhrLabSpecimenCondition> tableToList(DataTable table) throws Exception {
        List<EhrLabSpecimenCondition> retVal = new List<EhrLabSpecimenCondition>();
        EhrLabSpecimenCondition ehrLabSpecimenCondition;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            ehrLabSpecimenCondition = new EhrLabSpecimenCondition();
            ehrLabSpecimenCondition.EhrLabSpecimenConditionNum = PIn.Long(table.Rows[i]["EhrLabSpecimenConditionNum"].ToString());
            ehrLabSpecimenCondition.EhrLabSpecimenNum = PIn.Long(table.Rows[i]["EhrLabSpecimenNum"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionID = PIn.String(table.Rows[i]["SpecimenConditionID"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionText = PIn.String(table.Rows[i]["SpecimenConditionText"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionCodeSystemName = PIn.String(table.Rows[i]["SpecimenConditionCodeSystemName"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionIDAlt = PIn.String(table.Rows[i]["SpecimenConditionIDAlt"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionTextAlt = PIn.String(table.Rows[i]["SpecimenConditionTextAlt"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionCodeSystemNameAlt = PIn.String(table.Rows[i]["SpecimenConditionCodeSystemNameAlt"].ToString());
            ehrLabSpecimenCondition.SpecimenConditionTextOriginal = PIn.String(table.Rows[i]["SpecimenConditionTextOriginal"].ToString());
            retVal.Add(ehrLabSpecimenCondition);
        }
        return retVal;
    }

    /**
    * Inserts one EhrLabSpecimenCondition into the database.  Returns the new priKey.
    */
    public static long insert(EhrLabSpecimenCondition ehrLabSpecimenCondition) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            ehrLabSpecimenCondition.EhrLabSpecimenConditionNum = DbHelper.getNextOracleKey("ehrlabspecimencondition","EhrLabSpecimenConditionNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(ehrLabSpecimenCondition, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        ehrLabSpecimenCondition.EhrLabSpecimenConditionNum++;
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
            return Insert(ehrLabSpecimenCondition, false);
        } 
    }

    /**
    * Inserts one EhrLabSpecimenCondition into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EhrLabSpecimenCondition ehrLabSpecimenCondition, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            ehrLabSpecimenCondition.EhrLabSpecimenConditionNum = ReplicationServers.getKey("ehrlabspecimencondition","EhrLabSpecimenConditionNum");
        }
         
        String command = "INSERT INTO ehrlabspecimencondition (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EhrLabSpecimenConditionNum,";
        }
         
        command += "EhrLabSpecimenNum,SpecimenConditionID,SpecimenConditionText,SpecimenConditionCodeSystemName,SpecimenConditionIDAlt,SpecimenConditionTextAlt,SpecimenConditionCodeSystemNameAlt,SpecimenConditionTextOriginal) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(ehrLabSpecimenCondition.EhrLabSpecimenConditionNum) + ",";
        }
         
        command += POut.long(ehrLabSpecimenCondition.EhrLabSpecimenNum) + "," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionID) + "'," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionText) + "'," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionCodeSystemName) + "'," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionIDAlt) + "'," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionTextAlt) + "'," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionCodeSystemNameAlt) + "'," + "'" + POut.string(ehrLabSpecimenCondition.SpecimenConditionTextOriginal) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            ehrLabSpecimenCondition.EhrLabSpecimenConditionNum = Db.nonQ(command,true);
        } 
        return ehrLabSpecimenCondition.EhrLabSpecimenConditionNum;
    }

    /**
    * Updates one EhrLabSpecimenCondition in the database.
    */
    public static void update(EhrLabSpecimenCondition ehrLabSpecimenCondition) throws Exception {
        String command = "UPDATE ehrlabspecimencondition SET " + "EhrLabSpecimenNum                 =  " + POut.long(ehrLabSpecimenCondition.EhrLabSpecimenNum) + ", " + "SpecimenConditionID               = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionID) + "', " + "SpecimenConditionText             = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionText) + "', " + "SpecimenConditionCodeSystemName   = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionCodeSystemName) + "', " + "SpecimenConditionIDAlt            = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionIDAlt) + "', " + "SpecimenConditionTextAlt          = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionTextAlt) + "', " + "SpecimenConditionCodeSystemNameAlt= '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionCodeSystemNameAlt) + "', " + "SpecimenConditionTextOriginal     = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionTextOriginal) + "' " + "WHERE EhrLabSpecimenConditionNum = " + POut.long(ehrLabSpecimenCondition.EhrLabSpecimenConditionNum);
        Db.nonQ(command);
    }

    /**
    * Updates one EhrLabSpecimenCondition in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EhrLabSpecimenCondition ehrLabSpecimenCondition, EhrLabSpecimenCondition oldEhrLabSpecimenCondition) throws Exception {
        String command = "";
        if (ehrLabSpecimenCondition.EhrLabSpecimenNum != oldEhrLabSpecimenCondition.EhrLabSpecimenNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EhrLabSpecimenNum = " + POut.long(ehrLabSpecimenCondition.EhrLabSpecimenNum) + "";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionID, oldEhrLabSpecimenCondition.SpecimenConditionID))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionID = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionID) + "'";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionText, oldEhrLabSpecimenCondition.SpecimenConditionText))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionText = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionText) + "'";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionCodeSystemName, oldEhrLabSpecimenCondition.SpecimenConditionCodeSystemName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionCodeSystemName = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionCodeSystemName) + "'";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionIDAlt, oldEhrLabSpecimenCondition.SpecimenConditionIDAlt))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionIDAlt = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionIDAlt) + "'";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionTextAlt, oldEhrLabSpecimenCondition.SpecimenConditionTextAlt))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionTextAlt = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionTextAlt) + "'";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionCodeSystemNameAlt, oldEhrLabSpecimenCondition.SpecimenConditionCodeSystemNameAlt))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionCodeSystemNameAlt = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionCodeSystemNameAlt) + "'";
        }
         
        if (!StringSupport.equals(ehrLabSpecimenCondition.SpecimenConditionTextOriginal, oldEhrLabSpecimenCondition.SpecimenConditionTextOriginal))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SpecimenConditionTextOriginal = '" + POut.string(ehrLabSpecimenCondition.SpecimenConditionTextOriginal) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE ehrlabspecimencondition SET " + command + " WHERE EhrLabSpecimenConditionNum = " + POut.long(ehrLabSpecimenCondition.EhrLabSpecimenConditionNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one EhrLabSpecimenCondition from the database.
    */
    public static void delete(long ehrLabSpecimenConditionNum) throws Exception {
        String command = "DELETE FROM ehrlabspecimencondition " + "WHERE EhrLabSpecimenConditionNum = " + POut.long(ehrLabSpecimenConditionNum);
        Db.nonQ(command);
    }

}

