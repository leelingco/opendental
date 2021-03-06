//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:06 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.ImageType;
import OpenDentBusiness.Mount;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class MountCrud   
{
    /**
    * Gets one Mount object from the database using the primary key.  Returns null if not found.
    */
    public static Mount selectOne(long mountNum) throws Exception {
        String command = "SELECT * FROM mount " + "WHERE MountNum = " + POut.long(mountNum);
        List<Mount> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Mount object from the database using a query.
    */
    public static Mount selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Mount> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Mount objects from the database using a query.
    */
    public static List<Mount> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Mount> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Mount> tableToList(DataTable table) throws Exception {
        List<Mount> retVal = new List<Mount>();
        Mount mount;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            mount = new Mount();
            mount.MountNum = PIn.Long(table.Rows[i]["MountNum"].ToString());
            mount.PatNum = PIn.Long(table.Rows[i]["PatNum"].ToString());
            mount.DocCategory = PIn.Long(table.Rows[i]["DocCategory"].ToString());
            mount.DateCreated = PIn.Date(table.Rows[i]["DateCreated"].ToString());
            mount.Description = PIn.String(table.Rows[i]["Description"].ToString());
            mount.Note = PIn.String(table.Rows[i]["Note"].ToString());
            mount.ImgType = (ImageType)PIn.Int(table.Rows[i]["ImgType"].ToString());
            mount.Width = PIn.Int(table.Rows[i]["Width"].ToString());
            mount.Height = PIn.Int(table.Rows[i]["Height"].ToString());
            retVal.Add(mount);
        }
        return retVal;
    }

    /**
    * Inserts one Mount into the database.  Returns the new priKey.
    */
    public static long insert(Mount mount) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            mount.MountNum = DbHelper.getNextOracleKey("mount","MountNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(mount, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        mount.MountNum++;
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
            return Insert(mount, false);
        } 
    }

    /**
    * Inserts one Mount into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Mount mount, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            mount.MountNum = ReplicationServers.getKey("mount","MountNum");
        }
         
        String command = "INSERT INTO mount (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "MountNum,";
        }
         
        command += "PatNum,DocCategory,DateCreated,Description,Note,ImgType,Width,Height) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(mount.MountNum) + ",";
        }
         
        command += POut.long(mount.PatNum) + "," + POut.long(mount.DocCategory) + "," + POut.date(mount.DateCreated) + "," + "'" + POut.string(mount.Description) + "'," + "'" + POut.string(mount.Note) + "'," + POut.int(((Enum)mount.ImgType).ordinal()) + "," + POut.int(mount.Width) + "," + POut.int(mount.Height) + ")";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            mount.MountNum = Db.nonQ(command,true);
        } 
        return mount.MountNum;
    }

    /**
    * Updates one Mount in the database.
    */
    public static void update(Mount mount) throws Exception {
        String command = "UPDATE mount SET " + "PatNum     =  " + POut.long(mount.PatNum) + ", " + "DocCategory=  " + POut.long(mount.DocCategory) + ", " + "DateCreated=  " + POut.date(mount.DateCreated) + ", " + "Description= '" + POut.string(mount.Description) + "', " + "Note       = '" + POut.string(mount.Note) + "', " + "ImgType    =  " + POut.int(((Enum)mount.ImgType).ordinal()) + ", " + "Width      =  " + POut.int(mount.Width) + ", " + "Height     =  " + POut.int(mount.Height) + " " + "WHERE MountNum = " + POut.long(mount.MountNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Mount in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Mount mount, Mount oldMount) throws Exception {
        String command = "";
        if (mount.PatNum != oldMount.PatNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PatNum = " + POut.long(mount.PatNum) + "";
        }
         
        if (mount.DocCategory != oldMount.DocCategory)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DocCategory = " + POut.long(mount.DocCategory) + "";
        }
         
        if (mount.DateCreated != oldMount.DateCreated)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DateCreated = " + POut.date(mount.DateCreated) + "";
        }
         
        if (!StringSupport.equals(mount.Description, oldMount.Description))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.string(mount.Description) + "'";
        }
         
        if (!StringSupport.equals(mount.Note, oldMount.Note))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Note = '" + POut.string(mount.Note) + "'";
        }
         
        if (mount.ImgType != oldMount.ImgType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ImgType = " + POut.int(((Enum)mount.ImgType).ordinal()) + "";
        }
         
        if (mount.Width != oldMount.Width)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Width = " + POut.int(mount.Width) + "";
        }
         
        if (mount.Height != oldMount.Height)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Height = " + POut.int(mount.Height) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE mount SET " + command + " WHERE MountNum = " + POut.long(mount.MountNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Mount from the database.
    */
    public static void delete(long mountNum) throws Exception {
        String command = "DELETE FROM mount " + "WHERE MountNum = " + POut.long(mountNum);
        Db.nonQ(command);
    }

}


