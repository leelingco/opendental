//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:09 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class UserodCrud   
{
    /**
    * Gets one Userod object from the database using the primary key.  Returns null if not found.
    */
    public static Userod selectOne(long userNum) throws Exception {
        String command = "SELECT * FROM userod " + "WHERE UserNum = " + POut.Long(userNum);
        List<Userod> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Userod object from the database using a query.
    */
    public static Userod selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Userod> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Userod objects from the database using a query.
    */
    public static List<Userod> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Userod> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Userod> tableToList(DataTable table) throws Exception {
        List<Userod> retVal = new List<Userod>();
        Userod userod = new Userod();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            userod = new Userod();
            userod.UserNum = PIn.Long(table.Rows[i]["UserNum"].ToString());
            userod.UserName = PIn.String(table.Rows[i]["UserName"].ToString());
            userod.Password = PIn.String(table.Rows[i]["Password"].ToString());
            userod.UserGroupNum = PIn.Long(table.Rows[i]["UserGroupNum"].ToString());
            userod.EmployeeNum = PIn.Long(table.Rows[i]["EmployeeNum"].ToString());
            userod.ClinicNum = PIn.Long(table.Rows[i]["ClinicNum"].ToString());
            userod.ProvNum = PIn.Long(table.Rows[i]["ProvNum"].ToString());
            userod.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            userod.TaskListInBox = PIn.Long(table.Rows[i]["TaskListInBox"].ToString());
            userod.AnesthProvType = PIn.Int(table.Rows[i]["AnesthProvType"].ToString());
            userod.DefaultHidePopups = PIn.Bool(table.Rows[i]["DefaultHidePopups"].ToString());
            userod.PasswordIsStrong = PIn.Bool(table.Rows[i]["PasswordIsStrong"].ToString());
            userod.ClinicIsRestricted = PIn.Bool(table.Rows[i]["ClinicIsRestricted"].ToString());
            retVal.Add(userod);
        }
        return retVal;
    }

    /**
    * Inserts one Userod into the database.  Returns the new priKey.
    */
    public static long insert(Userod userod) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            userod.UserNum = DbHelper.GetNextOracleKey("userod", "UserNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(userod,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        userod.UserNum++;
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
            return insert(userod,false);
        } 
    }

    /**
    * Inserts one Userod into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Userod userod, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            userod.UserNum = ReplicationServers.GetKey("userod", "UserNum");
        }
         
        String command = "INSERT INTO userod (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "UserNum,";
        }
         
        command += "UserName,Password,UserGroupNum,EmployeeNum,ClinicNum,ProvNum,IsHidden,TaskListInBox,AnesthProvType,DefaultHidePopups,PasswordIsStrong,ClinicIsRestricted) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(userod.UserNum) + ",";
        }
         
        command += "'" + POut.String(userod.UserName) + "'," + "'" + POut.String(userod.Password) + "'," + POut.Long(userod.UserGroupNum) + "," + POut.Long(userod.EmployeeNum) + "," + POut.Long(userod.ClinicNum) + "," + POut.Long(userod.ProvNum) + "," + POut.Bool(userod.IsHidden) + "," + POut.Long(userod.TaskListInBox) + "," + POut.Int(userod.AnesthProvType) + "," + POut.Bool(userod.DefaultHidePopups) + "," + POut.Bool(userod.PasswordIsStrong) + "," + POut.Bool(userod.ClinicIsRestricted) + ")";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            userod.UserNum = Db.NonQ(command, true);
        } 
        return userod.UserNum;
    }

    /**
    * Updates one Userod in the database.
    */
    public static void update(Userod userod) throws Exception {
        String command = "UPDATE userod SET " + "UserName          = '" + POut.String(userod.UserName) + "', " + "Password          = '" + POut.String(userod.Password) + "', " + "UserGroupNum      =  " + POut.Long(userod.UserGroupNum) + ", " + "EmployeeNum       =  " + POut.Long(userod.EmployeeNum) + ", " + "ClinicNum         =  " + POut.Long(userod.ClinicNum) + ", " + "ProvNum           =  " + POut.Long(userod.ProvNum) + ", " + "IsHidden          =  " + POut.Bool(userod.IsHidden) + ", " + "TaskListInBox     =  " + POut.Long(userod.TaskListInBox) + ", " + "AnesthProvType    =  " + POut.Int(userod.AnesthProvType) + ", " + "DefaultHidePopups =  " + POut.Bool(userod.DefaultHidePopups) + ", " + "PasswordIsStrong  =  " + POut.Bool(userod.PasswordIsStrong) + ", " + "ClinicIsRestricted=  " + POut.Bool(userod.ClinicIsRestricted) + " " + "WHERE UserNum = " + POut.Long(userod.UserNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Userod in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Userod userod, Userod oldUserod) throws Exception {
        String command = "";
        if (userod.UserName != oldUserod.UserName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UserName = '" + POut.String(userod.UserName) + "'";
        }
         
        if (userod.Password != oldUserod.Password)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Password = '" + POut.String(userod.Password) + "'";
        }
         
        if (userod.UserGroupNum != oldUserod.UserGroupNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "UserGroupNum = " + POut.Long(userod.UserGroupNum) + "";
        }
         
        if (userod.EmployeeNum != oldUserod.EmployeeNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmployeeNum = " + POut.Long(userod.EmployeeNum) + "";
        }
         
        if (userod.ClinicNum != oldUserod.ClinicNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClinicNum = " + POut.Long(userod.ClinicNum) + "";
        }
         
        if (userod.ProvNum != oldUserod.ProvNum)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ProvNum = " + POut.Long(userod.ProvNum) + "";
        }
         
        if (userod.IsHidden != oldUserod.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.Bool(userod.IsHidden) + "";
        }
         
        if (userod.TaskListInBox != oldUserod.TaskListInBox)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "TaskListInBox = " + POut.Long(userod.TaskListInBox) + "";
        }
         
        if (userod.AnesthProvType != oldUserod.AnesthProvType)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "AnesthProvType = " + POut.Int(userod.AnesthProvType) + "";
        }
         
        if (userod.DefaultHidePopups != oldUserod.DefaultHidePopups)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "DefaultHidePopups = " + POut.Bool(userod.DefaultHidePopups) + "";
        }
         
        if (userod.PasswordIsStrong != oldUserod.PasswordIsStrong)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PasswordIsStrong = " + POut.Bool(userod.PasswordIsStrong) + "";
        }
         
        if (userod.ClinicIsRestricted != oldUserod.ClinicIsRestricted)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClinicIsRestricted = " + POut.Bool(userod.ClinicIsRestricted) + "";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE userod SET " + command + " WHERE UserNum = " + POut.Long(userod.UserNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Userod from the database.
    */
    public static void delete(long userNum) throws Exception {
        String command = "DELETE FROM userod " + "WHERE UserNum = " + POut.Long(userNum);
        Db.NonQ(command);
    }

}


