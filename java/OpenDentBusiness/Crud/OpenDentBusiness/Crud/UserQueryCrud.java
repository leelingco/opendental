//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:09 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class UserQueryCrud   
{
    /**
    * Gets one UserQuery object from the database using the primary key.  Returns null if not found.
    */
    public static UserQuery selectOne(long queryNum) throws Exception {
        String command = "SELECT * FROM userquery " + "WHERE QueryNum = " + POut.Long(queryNum);
        List<UserQuery> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one UserQuery object from the database using a query.
    */
    public static UserQuery selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<UserQuery> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of UserQuery objects from the database using a query.
    */
    public static List<UserQuery> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<UserQuery> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<UserQuery> tableToList(DataTable table) throws Exception {
        List<UserQuery> retVal = new List<UserQuery>();
        UserQuery userQuery = new UserQuery();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            userQuery = new UserQuery();
            userQuery.QueryNum = PIn.Long(table.Rows[i]["QueryNum"].ToString());
            userQuery.Description = PIn.String(table.Rows[i]["Description"].ToString());
            userQuery.FileName = PIn.String(table.Rows[i]["FileName"].ToString());
            userQuery.QueryText = PIn.String(table.Rows[i]["QueryText"].ToString());
            retVal.Add(userQuery);
        }
        return retVal;
    }

    /**
    * Inserts one UserQuery into the database.  Returns the new priKey.
    */
    public static long insert(UserQuery userQuery) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            userQuery.QueryNum = DbHelper.GetNextOracleKey("userquery", "QueryNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(userQuery,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        userQuery.QueryNum++;
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
            return insert(userQuery,false);
        } 
    }

    /**
    * Inserts one UserQuery into the database.  Provides option to use the existing priKey.
    */
    public static long insert(UserQuery userQuery, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            userQuery.QueryNum = ReplicationServers.GetKey("userquery", "QueryNum");
        }
         
        String command = "INSERT INTO userquery (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "QueryNum,";
        }
         
        command += "Description,FileName,QueryText) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(userQuery.QueryNum) + ",";
        }
         
        command += "'" + POut.String(userQuery.Description) + "'," + "'" + POut.String(userQuery.FileName) + "'," + DbHelper.ParamChar + "paramQueryText)";
        if (userQuery.QueryText == null)
        {
            userQuery.QueryText = "";
        }
         
        OdSqlParameter paramQueryText = new OdSqlParameter("paramQueryText", OdDbType.Text, userQuery.QueryText);
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command, paramQueryText);
        }
        else
        {
            userQuery.QueryNum = Db.NonQ(command, true, paramQueryText);
        } 
        return userQuery.QueryNum;
    }

    /**
    * Updates one UserQuery in the database.
    */
    public static void update(UserQuery userQuery) throws Exception {
        String command = "UPDATE userquery SET " + "Description= '" + POut.String(userQuery.Description) + "', " + "FileName   = '" + POut.String(userQuery.FileName) + "', " + "QueryText  =  " + DbHelper.ParamChar + "paramQueryText " + "WHERE QueryNum = " + POut.Long(userQuery.QueryNum);
        if (userQuery.QueryText == null)
        {
            userQuery.QueryText = "";
        }
         
        OdSqlParameter paramQueryText = new OdSqlParameter("paramQueryText", OdDbType.Text, userQuery.QueryText);
        Db.NonQ(command, paramQueryText);
    }

    /**
    * Updates one UserQuery in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(UserQuery userQuery, UserQuery oldUserQuery) throws Exception {
        String command = "";
        if (userQuery.Description != oldUserQuery.Description)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Description = '" + POut.String(userQuery.Description) + "'";
        }
         
        if (userQuery.FileName != oldUserQuery.FileName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FileName = '" + POut.String(userQuery.FileName) + "'";
        }
         
        if (userQuery.QueryText != oldUserQuery.QueryText)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "QueryText = " + DbHelper.ParamChar + "paramQueryText";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        if (userQuery.QueryText == null)
        {
            userQuery.QueryText = "";
        }
         
        OdSqlParameter paramQueryText = new OdSqlParameter("paramQueryText", OdDbType.Text, userQuery.QueryText);
        command = "UPDATE userquery SET " + command + " WHERE QueryNum = " + POut.Long(userQuery.QueryNum);
        Db.NonQ(command, paramQueryText);
    }

    /**
    * Deletes one UserQuery from the database.
    */
    public static void delete(long queryNum) throws Exception {
        String command = "DELETE FROM userquery " + "WHERE QueryNum = " + POut.Long(queryNum);
        Db.NonQ(command);
    }

}


