//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:06 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.MountDef;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class MountDefCrud   
{
    /**
    * Gets one MountDef object from the database using the primary key.  Returns null if not found.
    */
    public static MountDef selectOne(long mountDefNum) throws Exception {
        String command = "SELECT * FROM mountdef " + "WHERE MountDefNum = " + POut.long(mountDefNum);
        List<MountDef> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one MountDef object from the database using a query.
    */
    public static MountDef selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MountDef> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of MountDef objects from the database using a query.
    */
    public static List<MountDef> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MountDef> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<MountDef> tableToList(DataTable table) throws Exception {
        List<MountDef> retVal = new List<MountDef>();
        MountDef mountDef;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            mountDef = new MountDef();
            mountDef.MountDefNum = PIn.Long(table.Rows[i]["MountDefNum"].ToString());
            mountDef.Description = PIn.String(table.Rows[i]["Description"].ToString());
            mountDef.ItemOrder = PIn.Int(table.Rows[i]["ItemOrder"].ToString());
            mountDef.IsRadiograph = PIn.Bool(table.Rows[i]["IsRadiograph"].ToString());
            mountDef.Width = PIn.Int(table.Rows[i]["Width"].ToString());
            mountDef.Height = PIn.Int(table.Rows[i]["Height"].ToString());
            retVal.Add(mountDef);
        }
        return retVal;
    }

    /**
    * Inserts one MountDef into the database.  Returns the new priKey.
    */
    public static long insert(MountDef mountDef) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            mountDef.MountDefNum = DbHelper.getNextOracleKey("mountdef","MountDefNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(mountDef, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        mountDef.MountDefNum++;
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
            return Insert(mountDef, false);
        } 
    }

    /**
    * Inserts one MountDef into the database.  Provides option to use the existing priKey.
    */
    public static long insert(MountDef mountDef, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            mountDef.MountDefNum = ReplicationServers.getKey("mountdef","MountDefNum");
        }
         
        String command = "INSERT INTO mountdef (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "MountDefNum,";
        }
         
        command += "Description,ItemOrder,IsRadiograph,Width,Height) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(mountDef.MountDefNum) + ",";
        }
         
        command += "'" + POut.string(mountDef.Description) + "'," + POut.int(mountDef.ItemOrder) + "," + POut.bool(mountDef.IsRadiograph) + "," + POut.int(mountDef.Width) + "," + POut.int(mountDef.Height) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            mountDef.MountDefNum = Db.nonQ(command,true);
        } 
        return mountDef.MountDefNum;
    }

    /**
    * Updates one MountDef in the database.
    */
    public static void update(MountDef mountDef) throws Exception {
        String command = "UPDATE mountdef SET " + "Description = '" + POut.string(mountDef.Description) + "', " + "ItemOrder   =  " + POut.int(mountDef.ItemOrder) + ", " + "IsRadiograph=  " + POut.bool(mountDef.IsRadiograph) + ", " + "Width       =  " + POut.int(mountDef.Width) + ", " + "Height      =  " + POut.int(mountDef.Height) + " " + "WHERE MountDefNum = " + POut.long(mountDef.MountDefNum);
        Db.nonQ(command);
    }

    /**
    * Updates one MountDef in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(MountDef mountDef, MountDef oldMountDef) throws Exception {
        String command = "";
        if (!StringSupport.equals(mountDef.Description, oldMountDef.Description))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.string(mountDef.Description) + "'";
        }
         
        if (mountDef.ItemOrder != oldMountDef.ItemOrder)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ItemOrder = " + POut.int(mountDef.ItemOrder) + "";
        }
         
        if (mountDef.IsRadiograph != oldMountDef.IsRadiograph)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsRadiograph = " + POut.bool(mountDef.IsRadiograph) + "";
        }
         
        if (mountDef.Width != oldMountDef.Width)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Width = " + POut.int(mountDef.Width) + "";
        }
         
        if (mountDef.Height != oldMountDef.Height)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Height = " + POut.int(mountDef.Height) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE mountdef SET " + command + " WHERE MountDefNum = " + POut.long(mountDef.MountDefNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one MountDef from the database.
    */
    public static void delete(long mountDefNum) throws Exception {
        String command = "DELETE FROM mountdef " + "WHERE MountDefNum = " + POut.long(mountDefNum);
        Db.nonQ(command);
    }

}

