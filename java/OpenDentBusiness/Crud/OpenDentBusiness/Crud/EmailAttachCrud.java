//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EmailAttachCrud   
{
    /**
    * Gets one EmailAttach object from the database using the primary key.  Returns null if not found.
    */
    public static EmailAttach selectOne(long emailAttachNum) throws Exception {
        String command = "SELECT * FROM emailattach " + "WHERE EmailAttachNum = " + POut.Long(emailAttachNum);
        List<EmailAttach> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EmailAttach object from the database using a query.
    */
    public static EmailAttach selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EmailAttach> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EmailAttach objects from the database using a query.
    */
    public static List<EmailAttach> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EmailAttach> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EmailAttach> tableToList(DataTable table) throws Exception {
        List<EmailAttach> retVal = new List<EmailAttach>();
        EmailAttach emailAttach = new EmailAttach();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            emailAttach = new EmailAttach();
            emailAttach.EmailAttachNum = PIn.Long(table.Rows[i]["EmailAttachNum"].ToString());
            emailAttach.EmailMessageNum = PIn.Long(table.Rows[i]["EmailMessageNum"].ToString());
            emailAttach.DisplayedFileName = PIn.String(table.Rows[i]["DisplayedFileName"].ToString());
            emailAttach.ActualFileName = PIn.String(table.Rows[i]["ActualFileName"].ToString());
            retVal.Add(emailAttach);
        }
        return retVal;
    }

    /**
    * Inserts one EmailAttach into the database.  Returns the new priKey.
    */
    public static long insert(EmailAttach emailAttach) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            emailAttach.EmailAttachNum = DbHelper.GetNextOracleKey("emailattach", "EmailAttachNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(emailAttach,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        emailAttach.EmailAttachNum++;
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
            return insert(emailAttach,false);
        } 
    }

    /**
    * Inserts one EmailAttach into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EmailAttach emailAttach, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            emailAttach.EmailAttachNum = ReplicationServers.GetKey("emailattach", "EmailAttachNum");
        }
         
        String command = "INSERT INTO emailattach (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EmailAttachNum,";
        }
         
        command += "EmailMessageNum,DisplayedFileName,ActualFileName) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(emailAttach.EmailAttachNum) + ",";
        }
         
        command += POut.Long(emailAttach.EmailMessageNum) + "," + "'" + POut.String(emailAttach.DisplayedFileName) + "'," + "'" + POut.String(emailAttach.ActualFileName) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            emailAttach.EmailAttachNum = Db.NonQ(command, true);
        } 
        return emailAttach.EmailAttachNum;
    }

    /**
    * Updates one EmailAttach in the database.
    */
    public static void update(EmailAttach emailAttach) throws Exception {
        String command = "UPDATE emailattach SET " + "EmailMessageNum  =  " + POut.Long(emailAttach.EmailMessageNum) + ", " + "DisplayedFileName= '" + POut.String(emailAttach.DisplayedFileName) + "', " + "ActualFileName   = '" + POut.String(emailAttach.ActualFileName) + "' " + "WHERE EmailAttachNum = " + POut.Long(emailAttach.EmailAttachNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EmailAttach in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EmailAttach emailAttach, EmailAttach oldEmailAttach) throws Exception {
        String command = "";
        if (emailAttach.EmailMessageNum != oldEmailAttach.EmailMessageNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailMessageNum = " + POut.Long(emailAttach.EmailMessageNum) + "";
        }
         
        if (emailAttach.DisplayedFileName != oldEmailAttach.DisplayedFileName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DisplayedFileName = '" + POut.String(emailAttach.DisplayedFileName) + "'";
        }
         
        if (emailAttach.ActualFileName != oldEmailAttach.ActualFileName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ActualFileName = '" + POut.String(emailAttach.ActualFileName) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE emailattach SET " + command + " WHERE EmailAttachNum = " + POut.Long(emailAttach.EmailAttachNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EmailAttach from the database.
    */
    public static void delete(long emailAttachNum) throws Exception {
        String command = "DELETE FROM emailattach " + "WHERE EmailAttachNum = " + POut.Long(emailAttachNum);
        Db.NonQ(command);
    }

}

