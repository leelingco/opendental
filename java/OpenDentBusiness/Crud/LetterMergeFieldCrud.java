//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:05 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.LetterMergeField;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class LetterMergeFieldCrud   
{
    /**
    * Gets one LetterMergeField object from the database using the primary key.  Returns null if not found.
    */
    public static LetterMergeField selectOne(long fieldNum) throws Exception {
        String command = "SELECT * FROM lettermergefield " + "WHERE FieldNum = " + POut.long(fieldNum);
        List<LetterMergeField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one LetterMergeField object from the database using a query.
    */
    public static LetterMergeField selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<LetterMergeField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of LetterMergeField objects from the database using a query.
    */
    public static List<LetterMergeField> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<LetterMergeField> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<LetterMergeField> tableToList(DataTable table) throws Exception {
        List<LetterMergeField> retVal = new List<LetterMergeField>();
        LetterMergeField letterMergeField;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            letterMergeField = new LetterMergeField();
            letterMergeField.FieldNum = PIn.Long(table.Rows[i]["FieldNum"].ToString());
            letterMergeField.LetterMergeNum = PIn.Long(table.Rows[i]["LetterMergeNum"].ToString());
            letterMergeField.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            retVal.Add(letterMergeField);
        }
        return retVal;
    }

    /**
    * Inserts one LetterMergeField into the database.  Returns the new priKey.
    */
    public static long insert(LetterMergeField letterMergeField) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            letterMergeField.FieldNum = DbHelper.getNextOracleKey("lettermergefield","FieldNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(letterMergeField, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        letterMergeField.FieldNum++;
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
            return Insert(letterMergeField, false);
        } 
    }

    /**
    * Inserts one LetterMergeField into the database.  Provides option to use the existing priKey.
    */
    public static long insert(LetterMergeField letterMergeField, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            letterMergeField.FieldNum = ReplicationServers.getKey("lettermergefield","FieldNum");
        }
         
        String command = "INSERT INTO lettermergefield (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "FieldNum,";
        }
         
        command += "LetterMergeNum,FieldName) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(letterMergeField.FieldNum) + ",";
        }
         
        command += POut.long(letterMergeField.LetterMergeNum) + "," + "'" + POut.string(letterMergeField.FieldName) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            letterMergeField.FieldNum = Db.nonQ(command,true);
        } 
        return letterMergeField.FieldNum;
    }

    /**
    * Updates one LetterMergeField in the database.
    */
    public static void update(LetterMergeField letterMergeField) throws Exception {
        String command = "UPDATE lettermergefield SET " + "LetterMergeNum=  " + POut.long(letterMergeField.LetterMergeNum) + ", " + "FieldName     = '" + POut.string(letterMergeField.FieldName) + "' " + "WHERE FieldNum = " + POut.long(letterMergeField.FieldNum);
        Db.nonQ(command);
    }

    /**
    * Updates one LetterMergeField in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(LetterMergeField letterMergeField, LetterMergeField oldLetterMergeField) throws Exception {
        String command = "";
        if (letterMergeField.LetterMergeNum != oldLetterMergeField.LetterMergeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LetterMergeNum = " + POut.long(letterMergeField.LetterMergeNum) + "";
        }
         
        if (!StringSupport.equals(letterMergeField.FieldName, oldLetterMergeField.FieldName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.string(letterMergeField.FieldName) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE lettermergefield SET " + command + " WHERE FieldNum = " + POut.long(letterMergeField.FieldNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one LetterMergeField from the database.
    */
    public static void delete(long fieldNum) throws Exception {
        String command = "DELETE FROM lettermergefield " + "WHERE FieldNum = " + POut.long(fieldNum);
        Db.nonQ(command);
    }

}

