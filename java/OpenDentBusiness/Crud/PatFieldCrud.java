//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:48:21 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.PatField;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class PatFieldCrud   
{
    /**
    * Gets one PatField object from the database using the primary key.  Returns null if not found.
    */
    public static PatField selectOne(long patFieldNum) throws Exception {
        String command = "SELECT * FROM patfield " + "WHERE PatFieldNum = " + POut.long(patFieldNum);
        List<PatField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one PatField object from the database using a query.
    */
    public static PatField selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PatField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of PatField objects from the database using a query.
    */
    public static List<PatField> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<PatField> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<PatField> tableToList(DataTable table) throws Exception {
        List<PatField> retVal = new List<PatField>();
        PatField patField;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            patField = new PatField();
            patField.PatFieldNum = PIn.Long(table.Rows[i]["PatFieldNum"].ToString());
            patField.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            patField.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            patField.FieldValue = PIn.String(table.Rows[i]["FieldValue"].ToString());
            retVal.Add(patField);
        }
        return retVal;
    }

    /**
    * Inserts one PatField into the database.  Returns the new priKey.
    */
    public static long insert(PatField patField) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            patField.PatFieldNum = DbHelper.getNextOracleKey("patfield","PatFieldNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(patField, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        patField.PatFieldNum++;
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
            return Insert(patField, false);
        } 
    }

    /**
    * Inserts one PatField into the database.  Provides option to use the existing priKey.
    */
    public static long insert(PatField patField, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            patField.PatFieldNum = ReplicationServers.getKey("patfield","PatFieldNum");
        }
         
        String command = "INSERT INTO patfield (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "PatFieldNum,";
        }
         
        command += "PatNum,FieldName,FieldValue) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(patField.PatFieldNum) + ",";
        }
         
        command += POut.long(patField.PatNum) + "," + "'" + POut.string(patField.FieldName) + "'," + "'" + POut.string(patField.FieldValue) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            patField.PatFieldNum = Db.nonQ(command,true);
        } 
        return patField.PatFieldNum;
    }

    /**
    * Updates one PatField in the database.
    */
    public static void update(PatField patField) throws Exception {
        String command = "UPDATE patfield SET " + "PatNum     =  " + POut.long(patField.PatNum) + ", " + "FieldName  = '" + POut.string(patField.FieldName) + "', " + "FieldValue = '" + POut.string(patField.FieldValue) + "' " + "WHERE PatFieldNum = " + POut.long(patField.PatFieldNum);
        Db.nonQ(command);
    }

    /**
    * Updates one PatField in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(PatField patField, PatField oldPatField) throws Exception {
        String command = "";
        if (patField.PatNum != oldPatField.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(patField.PatNum) + "";
        }
         
        if (!StringSupport.equals(patField.FieldName, oldPatField.FieldName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.string(patField.FieldName) + "'";
        }
         
        if (!StringSupport.equals(patField.FieldValue, oldPatField.FieldValue))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldValue = '" + POut.string(patField.FieldValue) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE patfield SET " + command + " WHERE PatFieldNum = " + POut.long(patField.PatFieldNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one PatField from the database.
    */
    public static void delete(long patFieldNum) throws Exception {
        String command = "DELETE FROM patfield " + "WHERE PatFieldNum = " + POut.long(patFieldNum);
        Db.nonQ(command);
    }

}

