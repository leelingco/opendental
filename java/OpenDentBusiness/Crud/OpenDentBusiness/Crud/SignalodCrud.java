//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:07 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class SignalodCrud   
{
    /**
    * Gets one Signalod object from the database using the primary key.  Returns null if not found.
    */
    public static Signalod selectOne(long signalNum) throws Exception {
        String command = "SELECT * FROM signalod " + "WHERE SignalNum = " + POut.Long(signalNum);
        List<Signalod> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Signalod object from the database using a query.
    */
    public static Signalod selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Signalod> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Signalod objects from the database using a query.
    */
    public static List<Signalod> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Signalod> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Signalod> tableToList(DataTable table) throws Exception {
        List<Signalod> retVal = new List<Signalod>();
        Signalod signalod = new Signalod();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            signalod = new Signalod();
            signalod.SignalNum = PIn.Long(table.Rows[i]["SignalNum"].ToString());
            signalod.FromUser = PIn.String(table.Rows[i]["FromUser"].ToString());
            signalod.ITypes = PIn.String(table.Rows[i]["ITypes"].ToString());
            signalod.DateViewing = PIn.Date(table.Rows[i]["DateViewing"].ToString());
            signalod.SigType = (SignalType)PIn.Int(table.Rows[i]["SigType"].ToString());
            signalod.SigText = PIn.String(table.Rows[i]["SigText"].ToString());
            signalod.SigDateTime = PIn.DateT(table.Rows[i]["SigDateTime"].ToString());
            signalod.ToUser = PIn.String(table.Rows[i]["ToUser"].ToString());
            signalod.AckTime = PIn.DateT(table.Rows[i]["AckTime"].ToString());
            signalod.TaskNum = PIn.Long(table.Rows[i]["TaskNum"].ToString());
            retVal.Add(signalod);
        }
        return retVal;
    }

    /**
    * Inserts one Signalod into the database.  Returns the new priKey.
    */
    public static long insert(Signalod signalod) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            signalod.SignalNum = DbHelper.GetNextOracleKey("signalod", "SignalNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(signalod,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        signalod.SignalNum++;
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
            return insert(signalod,false);
        } 
    }

    /**
    * Inserts one Signalod into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Signalod signalod, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            signalod.SignalNum = ReplicationServers.GetKey("signalod", "SignalNum");
        }
         
        String command = "INSERT INTO signalod (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "SignalNum,";
        }
         
        command += "FromUser,ITypes,DateViewing,SigType,SigText,SigDateTime,ToUser,AckTime,TaskNum) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(signalod.SignalNum) + ",";
        }
         
        command += "'" + POut.String(signalod.FromUser) + "'," + "'" + POut.String(signalod.ITypes) + "'," + POut.Date(signalod.DateViewing) + "," + POut.Int((int)signalod.SigType) + "," + "'" + POut.String(signalod.SigText) + "'," + POut.DateT(signalod.SigDateTime) + "," + "'" + POut.String(signalod.ToUser) + "'," + POut.DateT(signalod.AckTime) + "," + POut.Long(signalod.TaskNum) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            signalod.SignalNum = Db.NonQ(command, true);
        } 
        return signalod.SignalNum;
    }

    /**
    * Updates one Signalod in the database.
    */
    public static void update(Signalod signalod) throws Exception {
        String command = "UPDATE signalod SET " + "FromUser   = '" + POut.String(signalod.FromUser) + "', " + "ITypes     = '" + POut.String(signalod.ITypes) + "', " + "DateViewing=  " + POut.Date(signalod.DateViewing) + ", " + "SigType    =  " + POut.Int((int)signalod.SigType) + ", " + "SigText    = '" + POut.String(signalod.SigText) + "', " + "SigDateTime=  " + POut.DateT(signalod.SigDateTime) + ", " + "ToUser     = '" + POut.String(signalod.ToUser) + "', " + "AckTime    =  " + POut.DateT(signalod.AckTime) + ", " + "TaskNum    =  " + POut.Long(signalod.TaskNum) + " " + "WHERE SignalNum = " + POut.Long(signalod.SignalNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Signalod in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Signalod signalod, Signalod oldSignalod) throws Exception {
        String command = "";
        if (signalod.FromUser != oldSignalod.FromUser)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FromUser = '" + POut.String(signalod.FromUser) + "'";
        }
         
        if (signalod.ITypes != oldSignalod.ITypes)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ITypes = '" + POut.String(signalod.ITypes) + "'";
        }
         
        if (signalod.DateViewing != oldSignalod.DateViewing)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateViewing = " + POut.Date(signalod.DateViewing) + "";
        }
         
        if (signalod.SigType != oldSignalod.SigType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SigType = " + POut.Int((int)signalod.SigType) + "";
        }
         
        if (signalod.SigText != oldSignalod.SigText)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SigText = '" + POut.String(signalod.SigText) + "'";
        }
         
        if (signalod.SigDateTime != oldSignalod.SigDateTime)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "SigDateTime = " + POut.DateT(signalod.SigDateTime) + "";
        }
         
        if (signalod.ToUser != oldSignalod.ToUser)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ToUser = '" + POut.String(signalod.ToUser) + "'";
        }
         
        if (signalod.AckTime != oldSignalod.AckTime)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AckTime = " + POut.DateT(signalod.AckTime) + "";
        }
         
        if (signalod.TaskNum != oldSignalod.TaskNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskNum = " + POut.Long(signalod.TaskNum) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE signalod SET " + command + " WHERE SignalNum = " + POut.Long(signalod.SignalNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Signalod from the database.
    */
    public static void delete(long signalNum) throws Exception {
        String command = "DELETE FROM signalod " + "WHERE SignalNum = " + POut.Long(signalNum);
        Db.NonQ(command);
    }

}


