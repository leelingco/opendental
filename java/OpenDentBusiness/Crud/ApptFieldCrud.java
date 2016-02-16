//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:57:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ApptField;
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
public class ApptFieldCrud   
{
    /**
    * Gets one ApptField object from the database using the primary key.  Returns null if not found.
    */
    public static ApptField selectOne(long apptFieldNum) throws Exception {
        String command = "SELECT * FROM apptfield " + "WHERE ApptFieldNum = " + POut.long(apptFieldNum);
        List<ApptField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ApptField object from the database using a query.
    */
    public static ApptField selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ApptField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ApptField objects from the database using a query.
    */
    public static List<ApptField> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ApptField> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ApptField> tableToList(DataTable table) throws Exception {
        List<ApptField> retVal = new List<ApptField>();
        ApptField apptField;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            apptField = new ApptField();
            apptField.ApptFieldNum = PIn.Long(table.Rows[i]["ApptFieldNum"].ToString());
            apptField.AptNum = PIn.Long(table.Rows[i]["AptNum"].ToString());
            apptField.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            apptField.FieldValue = PIn.String(table.Rows[i]["FieldValue"].ToString());
            retVal.Add(apptField);
        }
        return retVal;
    }

    /**
    * Inserts one ApptField into the database.  Returns the new priKey.
    */
    public static long insert(ApptField apptField) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            apptField.ApptFieldNum = DbHelper.getNextOracleKey("apptfield","ApptFieldNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(apptField, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        apptField.ApptFieldNum++;
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
            return Insert(apptField, false);
        } 
    }

    /**
    * Inserts one ApptField into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ApptField apptField, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            apptField.ApptFieldNum = ReplicationServers.getKey("apptfield","ApptFieldNum");
        }
         
        String command = "INSERT INTO apptfield (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "ApptFieldNum,";
        }
         
        command += "AptNum,FieldName,FieldValue) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(apptField.ApptFieldNum) + ",";
        }
         
        command += POut.long(apptField.AptNum) + "," + "'" + POut.string(apptField.FieldName) + "'," + "'" + POut.string(apptField.FieldValue) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            apptField.ApptFieldNum = Db.nonQ(command,true);
        } 
        return apptField.ApptFieldNum;
    }

    /**
    * Updates one ApptField in the database.
    */
    public static void update(ApptField apptField) throws Exception {
        String command = "UPDATE apptfield SET " + "AptNum      =  " + POut.long(apptField.AptNum) + ", " + "FieldName   = '" + POut.string(apptField.FieldName) + "', " + "FieldValue  = '" + POut.string(apptField.FieldValue) + "' " + "WHERE ApptFieldNum = " + POut.long(apptField.ApptFieldNum);
        Db.nonQ(command);
    }

    /**
    * Updates one ApptField in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ApptField apptField, ApptField oldApptField) throws Exception {
        String command = "";
        if (apptField.AptNum != oldApptField.AptNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AptNum = " + POut.long(apptField.AptNum) + "";
        }
         
        if (!StringSupport.equals(apptField.FieldName, oldApptField.FieldName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.string(apptField.FieldName) + "'";
        }
         
        if (!StringSupport.equals(apptField.FieldValue, oldApptField.FieldValue))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldValue = '" + POut.string(apptField.FieldValue) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE apptfield SET " + command + " WHERE ApptFieldNum = " + POut.long(apptField.ApptFieldNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one ApptField from the database.
    */
    public static void delete(long apptFieldNum) throws Exception {
        String command = "DELETE FROM apptfield " + "WHERE ApptFieldNum = " + POut.long(apptFieldNum);
        Db.nonQ(command);
    }

}

