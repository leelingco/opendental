//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.DataTypeHL7;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.HL7DefField;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class HL7DefFieldCrud   
{
    /**
    * Gets one HL7DefField object from the database using the primary key.  Returns null if not found.
    */
    public static HL7DefField selectOne(long hL7DefFieldNum) throws Exception {
        String command = "SELECT * FROM hl7deffield " + "WHERE HL7DefFieldNum = " + POut.long(hL7DefFieldNum);
        List<HL7DefField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one HL7DefField object from the database using a query.
    */
    public static HL7DefField selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<HL7DefField> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of HL7DefField objects from the database using a query.
    */
    public static List<HL7DefField> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<HL7DefField> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<HL7DefField> tableToList(DataTable table) throws Exception {
        List<HL7DefField> retVal = new List<HL7DefField>();
        HL7DefField hL7DefField;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            hL7DefField = new HL7DefField();
            hL7DefField.HL7DefFieldNum = PIn.Long(table.Rows[i]["HL7DefFieldNum"].ToString());
            hL7DefField.HL7DefSegmentNum = PIn.Long(table.Rows[i]["HL7DefSegmentNum"].ToString());
            hL7DefField.OrdinalPos = PIn.Int(table.Rows[i]["OrdinalPos"].ToString());
            hL7DefField.TableId = PIn.String(table.Rows[i]["TableId"].ToString());
            String dataType = table.Rows[i]["DataType"].ToString();
            if (StringSupport.equals(dataType, ""))
            {
                hL7DefField.DataType = DataTypeHL7.values()[0];
            }
            else
                try
                {
                    hL7DefField.DataType = (DataTypeHL7)Enum.Parse(DataTypeHL7.class, dataType);
                }
                catch (Exception __dummyCatchVar0)
                {
                    hL7DefField.DataType = DataTypeHL7.values()[0];
                }
             
            hL7DefField.FieldName = PIn.String(table.Rows[i]["FieldName"].ToString());
            hL7DefField.FixedText = PIn.String(table.Rows[i]["FixedText"].ToString());
            retVal.Add(hL7DefField);
        }
        return retVal;
    }

    /**
    * Inserts one HL7DefField into the database.  Returns the new priKey.
    */
    public static long insert(HL7DefField hL7DefField) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            hL7DefField.HL7DefFieldNum = DbHelper.getNextOracleKey("hl7deffield","HL7DefFieldNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(hL7DefField, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        hL7DefField.HL7DefFieldNum++;
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
            return Insert(hL7DefField, false);
        } 
    }

    /**
    * Inserts one HL7DefField into the database.  Provides option to use the existing priKey.
    */
    public static long insert(HL7DefField hL7DefField, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            hL7DefField.HL7DefFieldNum = ReplicationServers.getKey("hl7deffield","HL7DefFieldNum");
        }
         
        String command = "INSERT INTO hl7deffield (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "HL7DefFieldNum,";
        }
         
        command += "HL7DefSegmentNum,OrdinalPos,TableId,DataType,FieldName,FixedText) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(hL7DefField.HL7DefFieldNum) + ",";
        }
         
        command += POut.long(hL7DefField.HL7DefSegmentNum) + "," + POut.int(hL7DefField.OrdinalPos) + "," + "'" + POut.string(hL7DefField.TableId) + "'," + "'" + POut.String(hL7DefField.DataType.ToString()) + "'," + "'" + POut.string(hL7DefField.FieldName) + "'," + "'" + POut.string(hL7DefField.FixedText) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            hL7DefField.HL7DefFieldNum = Db.nonQ(command,true);
        } 
        return hL7DefField.HL7DefFieldNum;
    }

    /**
    * Updates one HL7DefField in the database.
    */
    public static void update(HL7DefField hL7DefField) throws Exception {
        String command = "UPDATE hl7deffield SET " + "HL7DefSegmentNum=  " + POut.long(hL7DefField.HL7DefSegmentNum) + ", " + "OrdinalPos      =  " + POut.int(hL7DefField.OrdinalPos) + ", " + "TableId         = '" + POut.string(hL7DefField.TableId) + "', " + "DataType        = '" + POut.String(hL7DefField.DataType.ToString()) + "', " + "FieldName       = '" + POut.string(hL7DefField.FieldName) + "', " + "FixedText       = '" + POut.string(hL7DefField.FixedText) + "' " + "WHERE HL7DefFieldNum = " + POut.long(hL7DefField.HL7DefFieldNum);
        Db.nonQ(command);
    }

    /**
    * Updates one HL7DefField in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(HL7DefField hL7DefField, HL7DefField oldHL7DefField) throws Exception {
        String command = "";
        if (hL7DefField.HL7DefSegmentNum != oldHL7DefField.HL7DefSegmentNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HL7DefSegmentNum = " + POut.long(hL7DefField.HL7DefSegmentNum) + "";
        }
         
        if (hL7DefField.OrdinalPos != oldHL7DefField.OrdinalPos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OrdinalPos = " + POut.int(hL7DefField.OrdinalPos) + "";
        }
         
        if (!StringSupport.equals(hL7DefField.TableId, oldHL7DefField.TableId))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TableId = '" + POut.string(hL7DefField.TableId) + "'";
        }
         
        if (hL7DefField.DataType != oldHL7DefField.DataType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DataType = '" + POut.String(hL7DefField.DataType.ToString()) + "'";
        }
         
        if (!StringSupport.equals(hL7DefField.FieldName, oldHL7DefField.FieldName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FieldName = '" + POut.string(hL7DefField.FieldName) + "'";
        }
         
        if (!StringSupport.equals(hL7DefField.FixedText, oldHL7DefField.FixedText))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FixedText = '" + POut.string(hL7DefField.FixedText) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE hl7deffield SET " + command + " WHERE HL7DefFieldNum = " + POut.long(hL7DefField.HL7DefFieldNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one HL7DefField from the database.
    */
    public static void delete(long hL7DefFieldNum) throws Exception {
        String command = "DELETE FROM hl7deffield " + "WHERE HL7DefFieldNum = " + POut.long(hL7DefFieldNum);
        Db.nonQ(command);
    }

}


