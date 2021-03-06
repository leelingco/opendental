//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Computer;
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
public class ComputerCrud   
{
    /**
    * Gets one Computer object from the database using the primary key.  Returns null if not found.
    */
    public static Computer selectOne(long computerNum) throws Exception {
        String command = "SELECT * FROM computer " + "WHERE ComputerNum = " + POut.long(computerNum);
        List<Computer> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Computer object from the database using a query.
    */
    public static Computer selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Computer> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Computer objects from the database using a query.
    */
    public static List<Computer> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Computer> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Computer> tableToList(DataTable table) throws Exception {
        List<Computer> retVal = new List<Computer>();
        Computer computer;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            computer = new Computer();
            computer.ComputerNum = PIn.Long(table.Rows[i]["ComputerNum"].ToString());
            computer.CompName = PIn.String(table.Rows[i]["CompName"].ToString());
            computer.LastHeartBeat = PIn.DateT(table.Rows[i]["LastHeartBeat"].ToString());
            retVal.Add(computer);
        }
        return retVal;
    }

    /**
    * Inserts one Computer into the database.  Returns the new priKey.
    */
    public static long insert(Computer computer) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            computer.ComputerNum = DbHelper.getNextOracleKey("computer","ComputerNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(computer, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        computer.ComputerNum++;
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
            return Insert(computer, false);
        } 
    }

    /**
    * Inserts one Computer into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Computer computer, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            computer.ComputerNum = ReplicationServers.getKey("computer","ComputerNum");
        }
         
        String command = "INSERT INTO computer (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "ComputerNum,";
        }
         
        command += "CompName,LastHeartBeat) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(computer.ComputerNum) + ",";
        }
         
        command += "'" + POut.string(computer.CompName) + "'," + POut.dateT(computer.LastHeartBeat) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            computer.ComputerNum = Db.nonQ(command,true);
        } 
        return computer.ComputerNum;
    }

    /**
    * Updates one Computer in the database.
    */
    public static void update(Computer computer) throws Exception {
        String command = "UPDATE computer SET " + "CompName     = '" + POut.string(computer.CompName) + "', " + "LastHeartBeat=  " + POut.dateT(computer.LastHeartBeat) + " " + "WHERE ComputerNum = " + POut.long(computer.ComputerNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Computer in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Computer computer, Computer oldComputer) throws Exception {
        String command = "";
        if (!StringSupport.equals(computer.CompName, oldComputer.CompName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "CompName = '" + POut.string(computer.CompName) + "'";
        }
         
        if (computer.LastHeartBeat != oldComputer.LastHeartBeat)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LastHeartBeat = " + POut.dateT(computer.LastHeartBeat) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE computer SET " + command + " WHERE ComputerNum = " + POut.long(computer.ComputerNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Computer from the database.
    */
    public static void delete(long computerNum) throws Exception {
        String command = "DELETE FROM computer " + "WHERE ComputerNum = " + POut.long(computerNum);
        Db.nonQ(command);
    }

}


