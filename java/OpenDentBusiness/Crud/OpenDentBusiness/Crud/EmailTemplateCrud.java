//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EmailTemplateCrud   
{
    /**
    * Gets one EmailTemplate object from the database using the primary key.  Returns null if not found.
    */
    public static EmailTemplate selectOne(long emailTemplateNum) throws Exception {
        String command = "SELECT * FROM emailtemplate " + "WHERE EmailTemplateNum = " + POut.Long(emailTemplateNum);
        List<EmailTemplate> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EmailTemplate object from the database using a query.
    */
    public static EmailTemplate selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EmailTemplate> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EmailTemplate objects from the database using a query.
    */
    public static List<EmailTemplate> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EmailTemplate> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EmailTemplate> tableToList(DataTable table) throws Exception {
        List<EmailTemplate> retVal = new List<EmailTemplate>();
        EmailTemplate emailTemplate = new EmailTemplate();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            emailTemplate = new EmailTemplate();
            emailTemplate.EmailTemplateNum = PIn.Long(table.Rows[i]["EmailTemplateNum"].ToString());
            emailTemplate.Subject = PIn.String(table.Rows[i]["Subject"].ToString());
            emailTemplate.BodyText = PIn.String(table.Rows[i]["BodyText"].ToString());
            retVal.Add(emailTemplate);
        }
        return retVal;
    }

    /**
    * Inserts one EmailTemplate into the database.  Returns the new priKey.
    */
    public static long insert(EmailTemplate emailTemplate) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            emailTemplate.EmailTemplateNum = DbHelper.GetNextOracleKey("emailtemplate", "EmailTemplateNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(emailTemplate,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        emailTemplate.EmailTemplateNum++;
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
            return insert(emailTemplate,false);
        } 
    }

    /**
    * Inserts one EmailTemplate into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EmailTemplate emailTemplate, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            emailTemplate.EmailTemplateNum = ReplicationServers.GetKey("emailtemplate", "EmailTemplateNum");
        }
         
        String command = "INSERT INTO emailtemplate (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EmailTemplateNum,";
        }
         
        command += "Subject,BodyText) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(emailTemplate.EmailTemplateNum) + ",";
        }
         
        command += "'" + POut.String(emailTemplate.Subject) + "'," + "'" + POut.String(emailTemplate.BodyText) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            emailTemplate.EmailTemplateNum = Db.NonQ(command, true);
        } 
        return emailTemplate.EmailTemplateNum;
    }

    /**
    * Updates one EmailTemplate in the database.
    */
    public static void update(EmailTemplate emailTemplate) throws Exception {
        String command = "UPDATE emailtemplate SET " + "Subject         = '" + POut.String(emailTemplate.Subject) + "', " + "BodyText        = '" + POut.String(emailTemplate.BodyText) + "' " + "WHERE EmailTemplateNum = " + POut.Long(emailTemplate.EmailTemplateNum);
        Db.NonQ(command);
    }

    /**
    * Updates one EmailTemplate in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EmailTemplate emailTemplate, EmailTemplate oldEmailTemplate) throws Exception {
        String command = "";
        if (emailTemplate.Subject != oldEmailTemplate.Subject)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Subject = '" + POut.String(emailTemplate.Subject) + "'";
        }
         
        if (emailTemplate.BodyText != oldEmailTemplate.BodyText)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "BodyText = '" + POut.String(emailTemplate.BodyText) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE emailtemplate SET " + command + " WHERE EmailTemplateNum = " + POut.Long(emailTemplate.EmailTemplateNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one EmailTemplate from the database.
    */
    public static void delete(long emailTemplateNum) throws Exception {
        String command = "DELETE FROM emailtemplate " + "WHERE EmailTemplateNum = " + POut.Long(emailTemplateNum);
        Db.NonQ(command);
    }

}


