//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.EmailAddress;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EmailAddressCrud   
{
    /**
    * Gets one EmailAddress object from the database using the primary key.  Returns null if not found.
    */
    public static EmailAddress selectOne(long emailAddressNum) throws Exception {
        String command = "SELECT * FROM emailaddress " + "WHERE EmailAddressNum = " + POut.long(emailAddressNum);
        List<EmailAddress> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one EmailAddress object from the database using a query.
    */
    public static EmailAddress selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EmailAddress> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of EmailAddress objects from the database using a query.
    */
    public static List<EmailAddress> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<EmailAddress> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<EmailAddress> tableToList(DataTable table) throws Exception {
        List<EmailAddress> retVal = new List<EmailAddress>();
        EmailAddress emailAddress;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            emailAddress = new EmailAddress();
            emailAddress.EmailAddressNum = PIn.Long(table.Rows[i]["EmailAddressNum"].ToString());
            emailAddress.SMTPserver = PIn.String(table.Rows[i]["SMTPserver"].ToString());
            emailAddress.EmailUsername = PIn.String(table.Rows[i]["EmailUsername"].ToString());
            emailAddress.EmailPassword = PIn.String(table.Rows[i]["EmailPassword"].ToString());
            emailAddress.ServerPort = PIn.Int(table.Rows[i]["ServerPort"].ToString());
            emailAddress.UseSSL = PIn.Bool(table.Rows[i]["UseSSL"].ToString());
            emailAddress.SenderAddress = PIn.String(table.Rows[i]["SenderAddress"].ToString());
            emailAddress.Pop3ServerIncoming = PIn.String(table.Rows[i]["Pop3ServerIncoming"].ToString());
            emailAddress.ServerPortIncoming = PIn.Int(table.Rows[i]["ServerPortIncoming"].ToString());
            retVal.Add(emailAddress);
        }
        return retVal;
    }

    /**
    * Inserts one EmailAddress into the database.  Returns the new priKey.
    */
    public static long insert(EmailAddress emailAddress) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            emailAddress.EmailAddressNum = DbHelper.getNextOracleKey("emailaddress","EmailAddressNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(emailAddress, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        emailAddress.EmailAddressNum++;
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
            return Insert(emailAddress, false);
        } 
    }

    /**
    * Inserts one EmailAddress into the database.  Provides option to use the existing priKey.
    */
    public static long insert(EmailAddress emailAddress, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            emailAddress.EmailAddressNum = ReplicationServers.getKey("emailaddress","EmailAddressNum");
        }
         
        String command = "INSERT INTO emailaddress (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EmailAddressNum,";
        }
         
        command += "SMTPserver,EmailUsername,EmailPassword,ServerPort,UseSSL,SenderAddress,Pop3ServerIncoming,ServerPortIncoming) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(emailAddress.EmailAddressNum) + ",";
        }
         
        command += "'" + POut.string(emailAddress.SMTPserver) + "'," + "'" + POut.string(emailAddress.EmailUsername) + "'," + "'" + POut.string(emailAddress.EmailPassword) + "'," + POut.int(emailAddress.ServerPort) + "," + POut.bool(emailAddress.UseSSL) + "," + "'" + POut.string(emailAddress.SenderAddress) + "'," + "'" + POut.string(emailAddress.Pop3ServerIncoming) + "'," + POut.int(emailAddress.ServerPortIncoming) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            emailAddress.EmailAddressNum = Db.nonQ(command,true);
        } 
        return emailAddress.EmailAddressNum;
    }

    /**
    * Updates one EmailAddress in the database.
    */
    public static void update(EmailAddress emailAddress) throws Exception {
        String command = "UPDATE emailaddress SET " + "SMTPserver        = '" + POut.string(emailAddress.SMTPserver) + "', " + "EmailUsername     = '" + POut.string(emailAddress.EmailUsername) + "', " + "EmailPassword     = '" + POut.string(emailAddress.EmailPassword) + "', " + "ServerPort        =  " + POut.int(emailAddress.ServerPort) + ", " + "UseSSL            =  " + POut.bool(emailAddress.UseSSL) + ", " + "SenderAddress     = '" + POut.string(emailAddress.SenderAddress) + "', " + "Pop3ServerIncoming= '" + POut.string(emailAddress.Pop3ServerIncoming) + "', " + "ServerPortIncoming=  " + POut.int(emailAddress.ServerPortIncoming) + " " + "WHERE EmailAddressNum = " + POut.long(emailAddress.EmailAddressNum);
        Db.nonQ(command);
    }

    /**
    * Updates one EmailAddress in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(EmailAddress emailAddress, EmailAddress oldEmailAddress) throws Exception {
        String command = "";
        if (!StringSupport.equals(emailAddress.SMTPserver, oldEmailAddress.SMTPserver))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SMTPserver = '" + POut.string(emailAddress.SMTPserver) + "'";
        }
         
        if (!StringSupport.equals(emailAddress.EmailUsername, oldEmailAddress.EmailUsername))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailUsername = '" + POut.string(emailAddress.EmailUsername) + "'";
        }
         
        if (!StringSupport.equals(emailAddress.EmailPassword, oldEmailAddress.EmailPassword))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmailPassword = '" + POut.string(emailAddress.EmailPassword) + "'";
        }
         
        if (emailAddress.ServerPort != oldEmailAddress.ServerPort)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ServerPort = " + POut.int(emailAddress.ServerPort) + "";
        }
         
        if (emailAddress.UseSSL != oldEmailAddress.UseSSL)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UseSSL = " + POut.bool(emailAddress.UseSSL) + "";
        }
         
        if (!StringSupport.equals(emailAddress.SenderAddress, oldEmailAddress.SenderAddress))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SenderAddress = '" + POut.string(emailAddress.SenderAddress) + "'";
        }
         
        if (!StringSupport.equals(emailAddress.Pop3ServerIncoming, oldEmailAddress.Pop3ServerIncoming))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Pop3ServerIncoming = '" + POut.string(emailAddress.Pop3ServerIncoming) + "'";
        }
         
        if (emailAddress.ServerPortIncoming != oldEmailAddress.ServerPortIncoming)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ServerPortIncoming = " + POut.int(emailAddress.ServerPortIncoming) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE emailaddress SET " + command + " WHERE EmailAddressNum = " + POut.long(emailAddress.EmailAddressNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one EmailAddress from the database.
    */
    public static void delete(long emailAddressNum) throws Exception {
        String command = "DELETE FROM emailaddress " + "WHERE EmailAddressNum = " + POut.long(emailAddressNum);
        Db.nonQ(command);
    }

}

