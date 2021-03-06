//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:01 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EtransMessageTextCrud   
{
    /**
    * Gets one EtransMessageText object from the database using the primary key.  Returns null if not found.
    */
    public static EtransMessageText selectOne(long etransMessageTextNum) throws Exception {
        String command = "SELECT * FROM etransmessagetext " + "WHERE EtransMessageTextNum = " + POut.Long(etransMessageTextNum);
        List<EtransMessageText> list = TableToList(Db.GetTable(command));
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
         
        List<EtransMessageText> list = TableToList(Db.GetTable(command));
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
         
        List<EtransMessageText> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EtransMessageText> tableToList(DataTable table) throws Exception {
        List<EtransMessageText> retVal = new List<EtransMessageText>();
        EtransMessageText etransMessageText = new EtransMessageText();
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
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            etransMessageText.EtransMessageTextNum = DbHelper.GetNextOracleKey("etransmessagetext", "EtransMessageTextNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(etransMessageText,true);
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
            return insert(etransMessageText,false);
        } 
    }

    /**
    * Inserts one EtransMessageText into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EtransMessageText etransMessageText, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            etransMessageText.EtransMessageTextNum = ReplicationServers.GetKey("etransmessagetext", "EtransMessageTextNum");
        }
         
        String command = "INSERT INTO etransmessagetext (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EtransMessageTextNum,";
        }
         
        command += "MessageText) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(etransMessageText.EtransMessageTextNum) + ",";
        }
         
        command += DbHelper.ParamChar + "paramMessageText)";
        if (etransMessageText.MessageText == null)
        {
            etransMessageText.MessageText = "";
        }
         
        OdSqlParameter paramMessageText = new OdSqlParameter("paramMessageText", OdDbType.Text, etransMessageText.MessageText);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramMessageText);
        }
        else
        {
            etransMessageText.EtransMessageTextNum = Db.NonQ(command, true, paramMessageText);
        } 
        return etransMessageText.EtransMessageTextNum;
    }

    /**
    * Updates one EtransMessageText in the database.
    */
    public static void update(EtransMessageText etransMessageText) throws Exception {
        String command = "UPDATE etransmessagetext SET " + "MessageText         =  " + DbHelper.ParamChar + "paramMessageText " + "WHERE EtransMessageTextNum = " + POut.Long(etransMessageText.EtransMessageTextNum);
        if (etransMessageText.MessageText == null)
        {
            etransMessageText.MessageText = "";
        }
         
        OdSqlParameter paramMessageText = new OdSqlParameter("paramMessageText", OdDbType.Text, etransMessageText.MessageText);
        Db.NonQ(command, paramMessageText);
    }

    /**
    * Updates one EtransMessageText in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EtransMessageText etransMessageText, EtransMessageText oldEtransMessageText) throws Exception {
        String command = "";
        if (etransMessageText.MessageText != oldEtransMessageText.MessageText)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MessageText = " + DbHelper.ParamChar + "paramMessageText";
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
        command = "UPDATE etransmessagetext SET " + command + " WHERE EtransMessageTextNum = " + POut.Long(etransMessageText.EtransMessageTextNum);
        Db.NonQ(command, paramMessageText);
    }

    /**
    * Deletes one EtransMessageText from the database.
    */
    public static void delete(long etransMessageTextNum) throws Exception {
        String command = "DELETE FROM etransmessagetext " + "WHERE EtransMessageTextNum = " + POut.Long(etransMessageTextNum);
        Db.NonQ(command);
    }

}


