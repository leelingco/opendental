//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:06 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.MountItemDef;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class MountItemDefCrud   
{
    /**
    * Gets one MountItemDef object from the database using the primary key.  Returns null if not found.
    */
    public static MountItemDef selectOne(long mountItemDefNum) throws Exception {
        String command = "SELECT * FROM mountitemdef " + "WHERE MountItemDefNum = " + POut.long(mountItemDefNum);
        List<MountItemDef> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one MountItemDef object from the database using a query.
    */
    public static MountItemDef selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MountItemDef> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of MountItemDef objects from the database using a query.
    */
    public static List<MountItemDef> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MountItemDef> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<MountItemDef> tableToList(DataTable table) throws Exception {
        List<MountItemDef> retVal = new List<MountItemDef>();
        MountItemDef mountItemDef;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            mountItemDef = new MountItemDef();
            mountItemDef.MountItemDefNum = PIn.Long(table.Rows[i]["MountItemDefNum"].ToString());
            mountItemDef.MountDefNum = PIn.Long(table.Rows[i]["MountDefNum"].ToString());
            mountItemDef.Xpos = PIn.Int(table.Rows[i]["Xpos"].ToString());
            mountItemDef.Ypos = PIn.Int(table.Rows[i]["Ypos"].ToString());
            mountItemDef.Width = PIn.Int(table.Rows[i]["Width"].ToString());
            mountItemDef.Height = PIn.Int(table.Rows[i]["Height"].ToString());
            retVal.Add(mountItemDef);
        }
        return retVal;
    }

    /**
    * Inserts one MountItemDef into the database.  Returns the new priKey.
    */
    public static long insert(MountItemDef mountItemDef) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            mountItemDef.MountItemDefNum = DbHelper.getNextOracleKey("mountitemdef","MountItemDefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(mountItemDef, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        mountItemDef.MountItemDefNum++;
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
            return Insert(mountItemDef, false);
        } 
    }

    /**
    * Inserts one MountItemDef into the database.  Provides option to use the existing priKey.
    */
    public static long insert(MountItemDef mountItemDef, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            mountItemDef.MountItemDefNum = ReplicationServers.getKey("mountitemdef","MountItemDefNum");
        }
         
        String command = "INSERT INTO mountitemdef (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "MountItemDefNum,";
        }
         
        command += "MountDefNum,Xpos,Ypos,Width,Height) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(mountItemDef.MountItemDefNum) + ",";
        }
         
        command += POut.long(mountItemDef.MountDefNum) + "," + POut.int(mountItemDef.Xpos) + "," + POut.int(mountItemDef.Ypos) + "," + POut.int(mountItemDef.Width) + "," + POut.int(mountItemDef.Height) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            mountItemDef.MountItemDefNum = Db.nonQ(command,true);
        } 
        return mountItemDef.MountItemDefNum;
    }

    /**
    * Updates one MountItemDef in the database.
    */
    public static void update(MountItemDef mountItemDef) throws Exception {
        String command = "UPDATE mountitemdef SET " + "MountDefNum    =  " + POut.long(mountItemDef.MountDefNum) + ", " + "Xpos           =  " + POut.int(mountItemDef.Xpos) + ", " + "Ypos           =  " + POut.int(mountItemDef.Ypos) + ", " + "Width          =  " + POut.int(mountItemDef.Width) + ", " + "Height         =  " + POut.int(mountItemDef.Height) + " " + "WHERE MountItemDefNum = " + POut.long(mountItemDef.MountItemDefNum);
        Db.nonQ(command);
    }

    /**
    * Updates one MountItemDef in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(MountItemDef mountItemDef, MountItemDef oldMountItemDef) throws Exception {
        String command = "";
        if (mountItemDef.MountDefNum != oldMountItemDef.MountDefNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MountDefNum = " + POut.long(mountItemDef.MountDefNum) + "";
        }
         
        if (mountItemDef.Xpos != oldMountItemDef.Xpos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Xpos = " + POut.int(mountItemDef.Xpos) + "";
        }
         
        if (mountItemDef.Ypos != oldMountItemDef.Ypos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Ypos = " + POut.int(mountItemDef.Ypos) + "";
        }
         
        if (mountItemDef.Width != oldMountItemDef.Width)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Width = " + POut.int(mountItemDef.Width) + "";
        }
         
        if (mountItemDef.Height != oldMountItemDef.Height)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Height = " + POut.int(mountItemDef.Height) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE mountitemdef SET " + command + " WHERE MountItemDefNum = " + POut.long(mountItemDef.MountItemDefNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one MountItemDef from the database.
    */
    public static void delete(long mountItemDefNum) throws Exception {
        String command = "DELETE FROM mountitemdef " + "WHERE MountItemDefNum = " + POut.long(mountItemDefNum);
        Db.nonQ(command);
    }

}


