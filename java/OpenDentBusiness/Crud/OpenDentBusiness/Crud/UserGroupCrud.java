//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:09 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class UserGroupCrud   
{
    /**
    * Gets one UserGroup object from the database using the primary key.  Returns null if not found.
    */
    public static UserGroup selectOne(long userGroupNum) throws Exception {
        String command = "SELECT * FROM usergroup " + "WHERE UserGroupNum = " + POut.Long(userGroupNum);
        List<UserGroup> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one UserGroup object from the database using a query.
    */
    public static UserGroup selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<UserGroup> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of UserGroup objects from the database using a query.
    */
    public static List<UserGroup> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<UserGroup> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<UserGroup> tableToList(DataTable table) throws Exception {
        List<UserGroup> retVal = new List<UserGroup>();
        UserGroup userGroup = new UserGroup();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            userGroup = new UserGroup();
            userGroup.UserGroupNum = PIn.Long(table.Rows[i]["UserGroupNum"].ToString());
            userGroup.Description = PIn.String(table.Rows[i]["Description"].ToString());
            retVal.Add(userGroup);
        }
        return retVal;
    }

    /**
    * Inserts one UserGroup into the database.  Returns the new priKey.
    */
    public static long insert(UserGroup userGroup) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            userGroup.UserGroupNum = DbHelper.GetNextOracleKey("usergroup", "UserGroupNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(userGroup,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        userGroup.UserGroupNum++;
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
            return insert(userGroup,false);
        } 
    }

    /**
    * Inserts one UserGroup into the database.  Provides option to use the existing priKey.
    */
    public static long insert(UserGroup userGroup, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            userGroup.UserGroupNum = ReplicationServers.GetKey("usergroup", "UserGroupNum");
        }
         
        String command = "INSERT INTO usergroup (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "UserGroupNum,";
        }
         
        command += "Description) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(userGroup.UserGroupNum) + ",";
        }
         
        command += "'" + POut.String(userGroup.Description) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            userGroup.UserGroupNum = Db.NonQ(command, true);
        } 
        return userGroup.UserGroupNum;
    }

    /**
    * Updates one UserGroup in the database.
    */
    public static void update(UserGroup userGroup) throws Exception {
        String command = "UPDATE usergroup SET " + "Description = '" + POut.String(userGroup.Description) + "' " + "WHERE UserGroupNum = " + POut.Long(userGroup.UserGroupNum);
        Db.NonQ(command);
    }

    /**
    * Updates one UserGroup in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(UserGroup userGroup, UserGroup oldUserGroup) throws Exception {
        String command = "";
        if (userGroup.Description != oldUserGroup.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(userGroup.Description) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE usergroup SET " + command + " WHERE UserGroupNum = " + POut.Long(userGroup.UserGroupNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one UserGroup from the database.
    */
    public static void delete(long userGroupNum) throws Exception {
        String command = "DELETE FROM usergroup " + "WHERE UserGroupNum = " + POut.Long(userGroupNum);
        Db.NonQ(command);
    }

}


