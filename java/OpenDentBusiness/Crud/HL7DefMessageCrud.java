//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:04 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EventTypeHL7;
import OpenDentBusiness.HL7DefMessage;
import OpenDentBusiness.InOutHL7;
import OpenDentBusiness.MessageTypeHL7;
import OpenDentBusiness.OdDbType;
import OpenDentBusiness.OdSqlParameter;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class HL7DefMessageCrud   
{
    /**
    * Gets one HL7DefMessage object from the database using the primary key.  Returns null if not found.
    */
    public static HL7DefMessage selectOne(long hL7DefMessageNum) throws Exception {
        String command = "SELECT * FROM hl7defmessage " + "WHERE HL7DefMessageNum = " + POut.long(hL7DefMessageNum);
        List<HL7DefMessage> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one HL7DefMessage object from the database using a query.
    */
    public static HL7DefMessage selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<HL7DefMessage> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of HL7DefMessage objects from the database using a query.
    */
    public static List<HL7DefMessage> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<HL7DefMessage> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<HL7DefMessage> tableToList(DataTable table) throws Exception {
        List<HL7DefMessage> retVal = new List<HL7DefMessage>();
        HL7DefMessage hL7DefMessage;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            hL7DefMessage = new HL7DefMessage();
            hL7DefMessage.HL7DefMessageNum = PIn.Long(table.Rows[i]["HL7DefMessageNum"].ToString());
            hL7DefMessage.HL7DefNum = PIn.Long(table.Rows[i]["HL7DefNum"].ToString());
            String messageType = table.Rows[i]["MessageType"].ToString();
            if (StringSupport.equals(messageType, ""))
            {
                hL7DefMessage.MessageType = MessageTypeHL7.values()[0];
            }
            else
                try
                {
                    hL7DefMessage.MessageType = (MessageTypeHL7)Enum.Parse(MessageTypeHL7.class, messageType);
                }
                catch (Exception __dummyCatchVar0)
                {
                    hL7DefMessage.MessageType = MessageTypeHL7.values()[0];
                }
             
            String eventType = table.Rows[i]["EventType"].ToString();
            if (StringSupport.equals(eventType, ""))
            {
                hL7DefMessage.EventType = EventTypeHL7.values()[0];
            }
            else
                try
                {
                    hL7DefMessage.EventType = (EventTypeHL7)Enum.Parse(EventTypeHL7.class, eventType);
                }
                catch (Exception __dummyCatchVar1)
                {
                    hL7DefMessage.EventType = EventTypeHL7.values()[0];
                }
             
            hL7DefMessage.InOrOut = (InOutHL7)PIn.Int(table.Rows[i]["InOrOut"].ToString());
            hL7DefMessage.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            hL7DefMessage.Note = PIn.String(table.Rows[i]["Note"].ToString());
            retVal.Add(hL7DefMessage);
        }
        return retVal;
    }

    /**
    * Inserts one HL7DefMessage into the database.  Returns the new priKey.
    */
    public static long insert(HL7DefMessage hL7DefMessage) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            hL7DefMessage.HL7DefMessageNum = DbHelper.getNextOracleKey("hl7defmessage","HL7DefMessageNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(hL7DefMessage, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        hL7DefMessage.HL7DefMessageNum++;
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
            return Insert(hL7DefMessage, false);
        } 
    }

    /**
    * Inserts one HL7DefMessage into the database.  Provides option to use the existing priKey.
    */
    public static long insert(HL7DefMessage hL7DefMessage, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            hL7DefMessage.HL7DefMessageNum = ReplicationServers.getKey("hl7defmessage","HL7DefMessageNum");
        }
         
        String command = "INSERT INTO hl7defmessage (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "HL7DefMessageNum,";
        }
         
        command += "HL7DefNum,MessageType,EventType,InOrOut,ItemOrder,Note) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(hL7DefMessage.HL7DefMessageNum) + ",";
        }
         
        command += POut.long(hL7DefMessage.HL7DefNum) + "," + "'" + POut.String(hL7DefMessage.MessageType.ToString()) + "'," + "'" + POut.String(hL7DefMessage.EventType.ToString()) + "'," + POut.int(((Enum)hL7DefMessage.InOrOut).ordinal()) + "," + POut.int(hL7DefMessage.ItemOrder) + "," + DbHelper.getParamChar() + "paramNote)";
        if (hL7DefMessage.Note == null)
        {
            hL7DefMessage.Note = "";
        }
         
        OdSqlParameter paramNote = new OdSqlParameter("paramNote", OdDbType.Text, hL7DefMessage.Note);
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command,paramNote);
        }
        else
        {
            hL7DefMessage.HL7DefMessageNum = Db.nonQ(command,true,paramNote);
        } 
        return hL7DefMessage.HL7DefMessageNum;
    }

    /**
    * Updates one HL7DefMessage in the database.
    */
    public static void update(HL7DefMessage hL7DefMessage) throws Exception {
        String command = "UPDATE hl7defmessage SET " + "HL7DefNum       =  " + POut.long(hL7DefMessage.HL7DefNum) + ", " + "MessageType     = '" + POut.String(hL7DefMessage.MessageType.ToString()) + "', " + "EventType       = '" + POut.String(hL7DefMessage.EventType.ToString()) + "', " + "InOrOut         =  " + POut.int(((Enum)hL7DefMessage.InOrOut).ordinal()) + ", " + "ItemOrder       =  " + POut.int(hL7DefMessage.ItemOrder) + ", " + "Note            =  " + DbHelper.getParamChar() + "paramNote " + "WHERE HL7DefMessageNum = " + POut.long(hL7DefMessage.HL7DefMessageNum);
        if (hL7DefMessage.Note == null)
        {
            hL7DefMessage.Note = "";
        }
         
        OdSqlParameter paramNote = new OdSqlParameter("paramNote", OdDbType.Text, hL7DefMessage.Note);
        Db.nonQ(command,paramNote);
    }

    /**
    * Updates one HL7DefMessage in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(HL7DefMessage hL7DefMessage, HL7DefMessage oldHL7DefMessage) throws Exception {
        String command = "";
        if (hL7DefMessage.HL7DefNum != oldHL7DefMessage.HL7DefNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "HL7DefNum = " + POut.long(hL7DefMessage.HL7DefNum) + "";
        }
         
        if (hL7DefMessage.MessageType != oldHL7DefMessage.MessageType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MessageType = '" + POut.String(hL7DefMessage.MessageType.ToString()) + "'";
        }
         
        if (hL7DefMessage.EventType != oldHL7DefMessage.EventType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EventType = '" + POut.String(hL7DefMessage.EventType.ToString()) + "'";
        }
         
        if (hL7DefMessage.InOrOut != oldHL7DefMessage.InOrOut)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InOrOut = " + POut.int(((Enum)hL7DefMessage.InOrOut).ordinal()) + "";
        }
         
        if (hL7DefMessage.ItemOrder != oldHL7DefMessage.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.int(hL7DefMessage.ItemOrder) + "";
        }
         
        if (!StringSupport.equals(hL7DefMessage.Note, oldHL7DefMessage.Note))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = " + DbHelper.getParamChar() + "paramNote";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (hL7DefMessage.Note == null)
        {
            hL7DefMessage.Note = "";
        }
         
        OdSqlParameter paramNote = new OdSqlParameter("paramNote", OdDbType.Text, hL7DefMessage.Note);
        command = "UPDATE hl7defmessage SET " + command + " WHERE HL7DefMessageNum = " + POut.long(hL7DefMessage.HL7DefMessageNum);
        Db.nonQ(command,paramNote);
    }

    /**
    * Deletes one HL7DefMessage from the database.
    */
    public static void delete(long hL7DefMessageNum) throws Exception {
        String command = "DELETE FROM hl7defmessage " + "WHERE HL7DefMessageNum = " + POut.long(hL7DefMessageNum);
        Db.nonQ(command);
    }

}


