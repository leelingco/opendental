//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.CommItemMode;
import OpenDentBusiness.Commlog;
import OpenDentBusiness.CommSentOrReceived;
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
public class CommlogCrud   
{
    /**
    * Gets one Commlog object from the database using the primary key.  Returns null if not found.
    */
    public static Commlog selectOne(long commlogNum) throws Exception {
        String command = "SELECT * FROM commlog " + "WHERE CommlogNum = " + POut.long(commlogNum);
        List<Commlog> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Commlog object from the database using a query.
    */
    public static Commlog selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Commlog> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Commlog objects from the database using a query.
    */
    public static List<Commlog> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Commlog> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Commlog> tableToList(DataTable table) throws Exception {
        List<Commlog> retVal = new List<Commlog>();
        Commlog commlog;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            commlog = new Commlog();
            commlog.CommlogNum = PIn.Long(table.Rows[i]["CommlogNum"].ToString());
            commlog.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            commlog.CommDateTime = PIn.DateT(table.Rows[i]["CommDateTime"].ToString());
            commlog.CommType = PIn.Long(table.Rows[i]["CommType"].ToString());
            commlog.Note = PIn.String(table.Rows[i]["Note"].ToString());
            commlog.Mode_ = (CommItemMode)PIn.Int(table.Rows[i]["Mode_"].ToString());
            commlog.SentOrReceived = (CommSentOrReceived)PIn.Int(table.Rows[i]["SentOrReceived"].ToString());
            commlog.UserNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
            commlog.Signature = PIn.String(table.Rows[i]["Signature"].ToString());
            commlog.SigIsTopaz = PIn.Bool(table.Rows[i]["SigIsTopaz"].ToString());
            commlog.DateTStamp = PIn.DateT(table.Rows[i]["DateTStamp"].ToString());
            commlog.DateTimeEnd = PIn.DateT(table.Rows[i]["DateTimeEnd"].ToString());
            retVal.Add(commlog);
        }
        return retVal;
    }

    /**
    * Inserts one Commlog into the database.  Returns the new priKey.
    */
    public static long insert(Commlog commlog) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            commlog.CommlogNum = DbHelper.getNextOracleKey("commlog","CommlogNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(commlog, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        commlog.CommlogNum++;
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
            return Insert(commlog, false);
        } 
    }

    /**
    * Inserts one Commlog into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Commlog commlog, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            commlog.CommlogNum = ReplicationServers.getKey("commlog","CommlogNum");
        }
         
        String command = "INSERT INTO commlog (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "CommlogNum,";
        }
         
        command += "PatNum,CommDateTime,CommType,Note,Mode_,SentOrReceived,UserNum,Signature,SigIsTopaz,DateTimeEnd) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(commlog.CommlogNum) + ",";
        }
         
        //DateTStamp can only be set by MySQL
        command += POut.long(commlog.PatNum) + "," + POut.dateT(commlog.CommDateTime) + "," + POut.long(commlog.CommType) + "," + "'" + POut.string(commlog.Note) + "'," + POut.int(((Enum)commlog.Mode_).ordinal()) + "," + POut.int(((Enum)commlog.SentOrReceived).ordinal()) + "," + POut.long(commlog.UserNum) + "," + "'" + POut.string(commlog.Signature) + "'," + POut.bool(commlog.SigIsTopaz) + "," + POut.dateT(commlog.DateTimeEnd) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            commlog.CommlogNum = Db.nonQ(command,true);
        } 
        return commlog.CommlogNum;
    }

    /**
    * Updates one Commlog in the database.
    */
    public static void update(Commlog commlog) throws Exception {
        //DateTStamp can only be set by MySQL
        String command = "UPDATE commlog SET " + "PatNum        =  " + POut.long(commlog.PatNum) + ", " + "CommDateTime  =  " + POut.dateT(commlog.CommDateTime) + ", " + "CommType      =  " + POut.long(commlog.CommType) + ", " + "Note          = '" + POut.string(commlog.Note) + "', " + "Mode_         =  " + POut.int(((Enum)commlog.Mode_).ordinal()) + ", " + "SentOrReceived=  " + POut.int(((Enum)commlog.SentOrReceived).ordinal()) + ", " + "UserNum       =  " + POut.long(commlog.UserNum) + ", " + "Signature     = '" + POut.string(commlog.Signature) + "', " + "SigIsTopaz    =  " + POut.bool(commlog.SigIsTopaz) + ", " + "DateTimeEnd   =  " + POut.dateT(commlog.DateTimeEnd) + " " + "WHERE CommlogNum = " + POut.long(commlog.CommlogNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Commlog in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Commlog commlog, Commlog oldCommlog) throws Exception {
        String command = "";
        if (commlog.PatNum != oldCommlog.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(commlog.PatNum) + "";
        }
         
        if (commlog.CommDateTime != oldCommlog.CommDateTime)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CommDateTime = " + POut.dateT(commlog.CommDateTime) + "";
        }
         
        if (commlog.CommType != oldCommlog.CommType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CommType = " + POut.long(commlog.CommType) + "";
        }
         
        if (!StringSupport.equals(commlog.Note, oldCommlog.Note))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.string(commlog.Note) + "'";
        }
         
        if (commlog.Mode_ != oldCommlog.Mode_)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Mode_ = " + POut.int(((Enum)commlog.Mode_).ordinal()) + "";
        }
         
        if (commlog.SentOrReceived != oldCommlog.SentOrReceived)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SentOrReceived = " + POut.int(((Enum)commlog.SentOrReceived).ordinal()) + "";
        }
         
        if (commlog.UserNum != oldCommlog.UserNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UserNum = " + POut.long(commlog.UserNum) + "";
        }
         
        if (!StringSupport.equals(commlog.Signature, oldCommlog.Signature))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Signature = '" + POut.string(commlog.Signature) + "'";
        }
         
        if (commlog.SigIsTopaz != oldCommlog.SigIsTopaz)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SigIsTopaz = " + POut.bool(commlog.SigIsTopaz) + "";
        }
         
        //DateTStamp can only be set by MySQL
        if (commlog.DateTimeEnd != oldCommlog.DateTimeEnd)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateTimeEnd = " + POut.dateT(commlog.DateTimeEnd) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE commlog SET " + command + " WHERE CommlogNum = " + POut.long(commlog.CommlogNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Commlog from the database.
    */
    public static void delete(long commlogNum) throws Exception {
        String command = "DELETE FROM commlog " + "WHERE CommlogNum = " + POut.long(commlogNum);
        Db.nonQ(command);
    }

}


