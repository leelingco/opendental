//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:57:59 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.ClaimAttach;
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
public class ClaimAttachCrud   
{
    /**
    * Gets one ClaimAttach object from the database using the primary key.  Returns null if not found.
    */
    public static ClaimAttach selectOne(long claimAttachNum) throws Exception {
        String command = "SELECT * FROM claimattach " + "WHERE ClaimAttachNum = " + POut.long(claimAttachNum);
        List<ClaimAttach> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one ClaimAttach object from the database using a query.
    */
    public static ClaimAttach selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ClaimAttach> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of ClaimAttach objects from the database using a query.
    */
    public static List<ClaimAttach> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<ClaimAttach> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<ClaimAttach> tableToList(DataTable table) throws Exception {
        List<ClaimAttach> retVal = new List<ClaimAttach>();
        ClaimAttach claimAttach;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            claimAttach = new ClaimAttach();
            claimAttach.ClaimAttachNum = PIn.Long(table.Rows[i]["ClaimAttachNum"].ToString());
            claimAttach.ClaimNum = PIn.Long(table.Rows[i]["ClaimNum"].ToString());
            claimAttach.DisplayedFileName = PIn.String(table.Rows[i]["DisplayedFileName"].ToString());
            claimAttach.ActualFileName = PIn.String(table.Rows[i]["ActualFileName"].ToString());
            retVal.Add(claimAttach);
        }
        return retVal;
    }

    /**
    * Inserts one ClaimAttach into the database.  Returns the new priKey.
    */
    public static long insert(ClaimAttach claimAttach) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            claimAttach.ClaimAttachNum = DbHelper.getNextOracleKey("claimattach","ClaimAttachNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(claimAttach, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        claimAttach.ClaimAttachNum++;
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
            return Insert(claimAttach, false);
        } 
    }

    /**
    * Inserts one ClaimAttach into the database.  Provides option to use the existing priKey.
    */
    public static long insert(ClaimAttach claimAttach, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            claimAttach.ClaimAttachNum = ReplicationServers.getKey("claimattach","ClaimAttachNum");
        }
         
        String command = "INSERT INTO claimattach (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "ClaimAttachNum,";
        }
         
        command += "ClaimNum,DisplayedFileName,ActualFileName) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(claimAttach.ClaimAttachNum) + ",";
        }
         
        command += POut.long(claimAttach.ClaimNum) + "," + "'" + POut.string(claimAttach.DisplayedFileName) + "'," + "'" + POut.string(claimAttach.ActualFileName) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            claimAttach.ClaimAttachNum = Db.nonQ(command,true);
        } 
        return claimAttach.ClaimAttachNum;
    }

    /**
    * Updates one ClaimAttach in the database.
    */
    public static void update(ClaimAttach claimAttach) throws Exception {
        String command = "UPDATE claimattach SET " + "ClaimNum         =  " + POut.long(claimAttach.ClaimNum) + ", " + "DisplayedFileName= '" + POut.string(claimAttach.DisplayedFileName) + "', " + "ActualFileName   = '" + POut.string(claimAttach.ActualFileName) + "' " + "WHERE ClaimAttachNum = " + POut.long(claimAttach.ClaimAttachNum);
        Db.nonQ(command);
    }

    /**
    * Updates one ClaimAttach in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(ClaimAttach claimAttach, ClaimAttach oldClaimAttach) throws Exception {
        String command = "";
        if (claimAttach.ClaimNum != oldClaimAttach.ClaimNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClaimNum = " + POut.long(claimAttach.ClaimNum) + "";
        }
         
        if (!StringSupport.equals(claimAttach.DisplayedFileName, oldClaimAttach.DisplayedFileName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DisplayedFileName = '" + POut.string(claimAttach.DisplayedFileName) + "'";
        }
         
        if (!StringSupport.equals(claimAttach.ActualFileName, oldClaimAttach.ActualFileName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ActualFileName = '" + POut.string(claimAttach.ActualFileName) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE claimattach SET " + command + " WHERE ClaimAttachNum = " + POut.long(claimAttach.ClaimAttachNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one ClaimAttach from the database.
    */
    public static void delete(long claimAttachNum) throws Exception {
        String command = "DELETE FROM claimattach " + "WHERE ClaimAttachNum = " + POut.long(claimAttachNum);
        Db.nonQ(command);
    }

}


