//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EtransMessageText;
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
public class EtransMessageTextCrud   
{
    /**
    * Gets one EtransMessageText object from the database using the primary key.  Returns null if not found.
    */
    public static EtransMessageText selectOne(long etransMessageTextNum) throws Exception {
        String command = "SELECT * FROM etransmessagetext " + "WHERE EtransMessageTextNum = " + POut.long(etransMessageTextNum);
        List<EtransMessageText> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EtransMessageText object from the database using a query.
    */
    public static EtransMessageText selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EtransMessageText> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EtransMessageText objects from the database using a query.
    */
    public static List<EtransMessageText> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EtransMessageText> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EtransMessageText> tableToList(DataTable table) throws Exception {
        List<EtransMessageText> retVal = new List<EtransMessageText>();
        EtransMessageText etransMessageText;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            etransMessageText = new EtransMessageText();
            etransMessageText.EtransMessageTextNum = PIn.Long(table.Rows[i]["EtransMessageTextNum"].ToString());
            etransMessageText.MessageText = PIn.String(table.Rows[i]["MessageText"].ToString());
            retVal.Add(etransMessageText);
        }
        return retVal;
    }

    /**
    * Inserts one EtransMessageText into the database.  Returns the new priKey.
    */
    public static long insert(EtransMessageText etransMessageText) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            etransMessageText.EtransMessageTextNum = DbHelper.getNextOracleKey("etransmessagetext","EtransMessageTextNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(etransMessageText, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        etransMessageText.EtransMessageTextNum++;
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
            return Insert(etransMessageText, false);
        } 
    }

    /**
    * Inserts one EtransMessageText into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EtransMessageText etransMessageText, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            etransMessageText.EtransMessageTextNum = ReplicationServers.getKey("etransmessagetext","EtransMessageTextNum");
        }
         
        String command = "INSERT INTO etransmessagetext (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EtransMessageTextNum,";
        }
         
        command += "MessageText) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(etransMessageText.EtransMessageTextNum) + ",";
        }
         
        command += DbHelper.getParamChar() + "paramMessageText)";
        if (etransMessageText.MessageText == null)
        {
            etransMessageText.MessageText = "";
        }
         
        OdSqlParameter paramMessageText = new OdSqlParameter("paramMessageText", OdDbType.Text, etransMessageText.MessageText);
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command,paramMessageText);
        }
        else
        {
            etransMessageText.EtransMessageTextNum = Db.nonQ(command,true,paramMessageText);
        } 
        return etransMessageText.EtransMessageTextNum;
    }

    /**
    * Updates one EtransMessageText in the database.
    */
    public static void update(EtransMessageText etransMessageText) throws Exception {
        String command = "UPDATE etransmessagetext SET " + "MessageText         =  " + DbHelper.getParamChar() + "paramMessageText " + "WHERE EtransMessageTextNum = " + POut.long(etransMessageText.EtransMessageTextNum);
        if (etransMessageText.MessageText == null)
        {
            etransMessageText.MessageText = "";
        }
         
        OdSqlParameter paramMessageText = new OdSqlParameter("paramMessageText", OdDbType.Text, etransMessageText.MessageText);
        Db.nonQ(command,paramMessageText);
    }

    /**
    * Updates one EtransMessageText in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EtransMessageText etransMessageText, EtransMessageText oldEtransMessageText) throws Exception {
        String command = "";
        if (!StringSupport.equals(etransMessageText.MessageText, oldEtransMessageText.MessageText))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MessageText = " + DbHelper.getParamChar() + "paramMessageText";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (etransMessageText.MessageText == null)
        {
            etransMessageText.MessageText = "";
        }
         
        OdSqlParameter paramMessageText = new OdSqlParameter("paramMessageText", OdDbType.Text, etransMessageText.MessageText);
        command = "UPDATE etransmessagetext SET " + command + " WHERE EtransMessageTextNum = " + POut.long(etransMessageText.EtransMessageTextNum);
        Db.nonQ(command,paramMessageText);
    }

    /**
    * Deletes one EtransMessageText from the database.
    */
    public static void delete(long etransMessageTextNum) throws Exception {
        String command = "DELETE FROM etransmessagetext " + "WHERE EtransMessageTextNum = " + POut.long(etransMessageTextNum);
        Db.nonQ(command);
    }

}


