//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:07:58 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class DunningCrud   
{
    /**
    * Gets one Dunning object from the database using the primary key.  Returns null if not found.
    */
    public static Dunning selectOne(long dunningNum) throws Exception {
        String command = "SELECT * FROM dunning " + "WHERE DunningNum = " + POut.Long(dunningNum);
        List<Dunning> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Dunning object from the database using a query.
    */
    public static Dunning selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Dunning> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Dunning objects from the database using a query.
    */
    public static List<Dunning> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Dunning> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Dunning> tableToList(DataTable table) throws Exception {
        List<Dunning> retVal = new List<Dunning>();
        Dunning dunning = new Dunning();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            dunning = new Dunning();
            dunning.DunningNum = PIn.Long(table.Rows[i]["DunningNum"].ToString());
            dunning.DunMessage = PIn.String(table.Rows[i]["DunMessage"].ToString());
            dunning.BillingType = PIn.Long(table.Rows[i]["BillingType"].ToString());
            dunning.AgeAccount = PIn.Byte(table.Rows[i]["AgeAccount"].ToString());
            dunning.InsIsPending = (YN)PIn.Int(table.Rows[i]["InsIsPending"].ToString());
            dunning.MessageBold = PIn.String(table.Rows[i]["MessageBold"].ToString());
            dunning.EmailSubject = PIn.String(table.Rows[i]["EmailSubject"].ToString());
            dunning.EmailBody = PIn.String(table.Rows[i]["EmailBody"].ToString());
            retVal.Add(dunning);
        }
        return retVal;
    }

    /**
    * Inserts one Dunning into the database.  Returns the new priKey.
    */
    public static long insert(Dunning dunning) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            dunning.DunningNum = DbHelper.GetNextOracleKey("dunning", "DunningNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(dunning,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        dunning.DunningNum++;
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
            return insert(dunning,false);
        } 
    }

    /**
    * Inserts one Dunning into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Dunning dunning, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            dunning.DunningNum = ReplicationServers.GetKey("dunning", "DunningNum");
        }
         
        String command = "INSERT INTO dunning (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "DunningNum,";
        }
         
        command += "DunMessage,BillingType,AgeAccount,InsIsPending,MessageBold,EmailSubject,EmailBody) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(dunning.DunningNum) + ",";
        }
         
        command += "'" + POut.String(dunning.DunMessage) + "'," + POut.Long(dunning.BillingType) + "," + POut.Byte(dunning.AgeAccount) + "," + POut.Int((int)dunning.InsIsPending) + "," + "'" + POut.String(dunning.MessageBold) + "'," + "'" + POut.String(dunning.EmailSubject) + "'," + DbHelper.ParamChar + "paramEmailBody)";
        if (dunning.EmailBody == null)
        {
            dunning.EmailBody = "";
        }
         
        OdSqlParameter paramEmailBody = new OdSqlParameter("paramEmailBody", OdDbType.Text, dunning.EmailBody);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramEmailBody);
        }
        else
        {
            dunning.DunningNum = Db.NonQ(command, true, paramEmailBody);
        } 
        return dunning.DunningNum;
    }

    /**
    * Updates one Dunning in the database.
    */
    public static void update(Dunning dunning) throws Exception {
        String command = "UPDATE dunning SET " + "DunMessage  = '" + POut.String(dunning.DunMessage) + "', " + "BillingType =  " + POut.Long(dunning.BillingType) + ", " + "AgeAccount  =  " + POut.Byte(dunning.AgeAccount) + ", " + "InsIsPending=  " + POut.Int((int)dunning.InsIsPending) + ", " + "MessageBold = '" + POut.String(dunning.MessageBold) + "', " + "EmailSubject= '" + POut.String(dunning.EmailSubject) + "', " + "EmailBody   =  " + DbHelper.ParamChar + "paramEmailBody " + "WHERE DunningNum = " + POut.Long(dunning.DunningNum);
        if (dunning.EmailBody == null)
        {
            dunning.EmailBody = "";
        }
         
        OdSqlParameter paramEmailBody = new OdSqlParameter("paramEmailBody", OdDbType.Text, dunning.EmailBody);
        Db.NonQ(command, paramEmailBody);
    }

    /**
    * Updates one Dunning in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Dunning dunning, Dunning oldDunning) throws Exception {
        String command = "";
        if (dunning.DunMessage != oldDunning.DunMessage)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DunMessage = '" + POut.String(dunning.DunMessage) + "'";
        }
         
        if (dunning.BillingType != oldDunning.BillingType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BillingType = " + POut.Long(dunning.BillingType) + "";
        }
         
        if (dunning.AgeAccount != oldDunning.AgeAccount)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AgeAccount = " + POut.Byte(dunning.AgeAccount) + "";
        }
         
        if (dunning.InsIsPending != oldDunning.InsIsPending)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "InsIsPending = " + POut.Int((int)dunning.InsIsPending) + "";
        }
         
        if (dunning.MessageBold != oldDunning.MessageBold)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MessageBold = '" + POut.String(dunning.MessageBold) + "'";
        }
         
        if (dunning.EmailSubject != oldDunning.EmailSubject)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailSubject = '" + POut.String(dunning.EmailSubject) + "'";
        }
         
        if (dunning.EmailBody != oldDunning.EmailBody)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailBody = " + DbHelper.ParamChar + "paramEmailBody";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (dunning.EmailBody == null)
        {
            dunning.EmailBody = "";
        }
         
        OdSqlParameter paramEmailBody = new OdSqlParameter("paramEmailBody", OdDbType.Text, dunning.EmailBody);
        command = "UPDATE dunning SET " + command + " WHERE DunningNum = " + POut.Long(dunning.DunningNum);
        Db.NonQ(command, paramEmailBody);
    }

    /**
    * Deletes one Dunning from the database.
    */
    public static void delete(long dunningNum) throws Exception {
        String command = "DELETE FROM dunning " + "WHERE DunningNum = " + POut.Long(dunningNum);
        Db.NonQ(command);
    }

}

