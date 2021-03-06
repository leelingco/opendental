//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class MountItemCrud   
{
    /**
    * Gets one MountItem object from the database using the primary key.  Returns null if not found.
    */
    public static MountItem selectOne(long mountItemNum) throws Exception {
        String command = "SELECT * FROM mountitem " + "WHERE MountItemNum = " + POut.Long(mountItemNum);
        List<MountItem> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one MountItem object from the database using a query.
    */
    public static MountItem selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MountItem> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of MountItem objects from the database using a query.
    */
    public static List<MountItem> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<MountItem> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<MountItem> tableToList(DataTable table) throws Exception {
        List<MountItem> retVal = new List<MountItem>();
        MountItem mountItem = new MountItem();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            mountItem = new MountItem();
            mountItem.MountItemNum = PIn.Long(table.Rows[i]["MountItemNum"].ToString());
            mountItem.MountNum = PIn.Long(table.Rows[i]["MountNum"].ToString());
            mountItem.Xpos = PIn.Int(table.Rows[i]["Xpos"].ToString());
            mountItem.Ypos = PIn.Int(table.Rows[i]["Ypos"].ToString());
            mountItem.OrdinalPos = PIn.Int(table.Rows[i]["OrdinalPos"].ToString());
            mountItem.Width = PIn.Int(table.Rows[i]["Width"].ToString());
            mountItem.Height = PIn.Int(table.Rows[i]["Height"].ToString());
            retVal.Add(mountItem);
        }
        return retVal;
    }

    /**
    * Inserts one MountItem into the database.  Returns the new priKey.
    */
    public static long insert(MountItem mountItem) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            mountItem.MountItemNum = DbHelper.GetNextOracleKey("mountitem", "MountItemNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(mountItem,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        mountItem.MountItemNum++;
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
            return insert(mountItem,false);
        } 
    }

    /**
    * Inserts one MountItem into the database.  Provides option to use the existing priKey.
    */
    public static long insert(MountItem mountItem, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            mountItem.MountItemNum = ReplicationServers.GetKey("mountitem", "MountItemNum");
        }
         
        String command = "INSERT INTO mountitem (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "MountItemNum,";
        }
         
        command += "MountNum,Xpos,Ypos,OrdinalPos,Width,Height) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(mountItem.MountItemNum) + ",";
        }
         
        command += POut.Long(mountItem.MountNum) + "," + POut.Int(mountItem.Xpos) + "," + POut.Int(mountItem.Ypos) + "," + POut.Int(mountItem.OrdinalPos) + "," + POut.Int(mountItem.Width) + "," + POut.Int(mountItem.Height) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            mountItem.MountItemNum = Db.NonQ(command, true);
        } 
        return mountItem.MountItemNum;
    }

    /**
    * Updates one MountItem in the database.
    */
    public static void update(MountItem mountItem) throws Exception {
        String command = "UPDATE mountitem SET " + "MountNum    =  " + POut.Long(mountItem.MountNum) + ", " + "Xpos        =  " + POut.Int(mountItem.Xpos) + ", " + "Ypos        =  " + POut.Int(mountItem.Ypos) + ", " + "OrdinalPos  =  " + POut.Int(mountItem.OrdinalPos) + ", " + "Width       =  " + POut.Int(mountItem.Width) + ", " + "Height      =  " + POut.Int(mountItem.Height) + " " + "WHERE MountItemNum = " + POut.Long(mountItem.MountItemNum);
        Db.NonQ(command);
    }

    /**
    * Updates one MountItem in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(MountItem mountItem, MountItem oldMountItem) throws Exception {
        String command = "";
        if (mountItem.MountNum != oldMountItem.MountNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MountNum = " + POut.Long(mountItem.MountNum) + "";
        }
         
        if (mountItem.Xpos != oldMountItem.Xpos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Xpos = " + POut.Int(mountItem.Xpos) + "";
        }
         
        if (mountItem.Ypos != oldMountItem.Ypos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Ypos = " + POut.Int(mountItem.Ypos) + "";
        }
         
        if (mountItem.OrdinalPos != oldMountItem.OrdinalPos)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "OrdinalPos = " + POut.Int(mountItem.OrdinalPos) + "";
        }
         
        if (mountItem.Width != oldMountItem.Width)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Width = " + POut.Int(mountItem.Width) + "";
        }
         
        if (mountItem.Height != oldMountItem.Height)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Height = " + POut.Int(mountItem.Height) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE mountitem SET " + command + " WHERE MountItemNum = " + POut.Long(mountItem.MountItemNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one MountItem from the database.
    */
    public static void delete(long mountItemNum) throws Exception {
        String command = "DELETE FROM mountitem " + "WHERE MountItemNum = " + POut.Long(mountItemNum);
        Db.NonQ(command);
    }

}


