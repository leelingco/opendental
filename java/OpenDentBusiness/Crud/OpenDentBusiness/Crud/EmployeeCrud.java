//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:08:00 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EmployeeCrud   
{
    /**
    * Gets one Employee object from the database using the primary key.  Returns null if not found.
    */
    public static Employee selectOne(long employeeNum) throws Exception {
        String command = "SELECT * FROM employee " + "WHERE EmployeeNum = " + POut.Long(employeeNum);
        List<Employee> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Employee object from the database using a query.
    */
    public static Employee selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Employee> list = TableToList(Db.GetTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Employee objects from the database using a query.
    */
    public static List<Employee> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Employee> list = TableToList(Db.GetTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Employee> tableToList(DataTable table) throws Exception {
        List<Employee> retVal = new List<Employee>();
        Employee employee = new Employee();
        for (int i = 0;i < table.Rows.Count;i++)
        {
            employee = new Employee();
            employee.EmployeeNum = PIn.Long(table.Rows[i]["EmployeeNum"].ToString());
            employee.LName = PIn.String(table.Rows[i]["LName"].ToString());
            employee.FName = PIn.String(table.Rows[i]["FName"].ToString());
            employee.MiddleI = PIn.String(table.Rows[i]["MiddleI"].ToString());
            employee.IsHidden = PIn.Bool(table.Rows[i]["IsHidden"].ToString());
            employee.ClockStatus = PIn.String(table.Rows[i]["ClockStatus"].ToString());
            employee.PhoneExt = PIn.Int(table.Rows[i]["PhoneExt"].ToString());
            employee.PayrollID = PIn.String(table.Rows[i]["PayrollID"].ToString());
            retVal.Add(employee);
        }
        return retVal;
    }

    /**
    * Inserts one Employee into the database.  Returns the new priKey.
    */
    public static long insert(Employee employee) throws Exception {
        if (DataConnection.DBtype == DatabaseType.Oracle)
        {
            employee.EmployeeNum = DbHelper.GetNextOracleKey("employee", "EmployeeNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return insert(employee,true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        employee.EmployeeNum++;
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
            return insert(employee,false);
        } 
    }

    /**
    * Inserts one Employee into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Employee employee, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.RandomKeys)
        {
            employee.EmployeeNum = ReplicationServers.GetKey("employee", "EmployeeNum");
        }
         
        String command = "INSERT INTO employee (";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += "EmployeeNum,";
        }
         
        command += "LName,FName,MiddleI,IsHidden,ClockStatus,PhoneExt,PayrollID) VALUES(";
        if (useExistingPK || PrefC.RandomKeys)
        {
            command += POut.Long(employee.EmployeeNum) + ",";
        }
         
        command += "'" + POut.String(employee.LName) + "'," + "'" + POut.String(employee.FName) + "'," + "'" + POut.String(employee.MiddleI) + "'," + POut.Bool(employee.IsHidden) + "," + "'" + POut.String(employee.ClockStatus) + "'," + POut.Int(employee.PhoneExt) + "," + "'" + POut.String(employee.PayrollID) + "')";
        if (useExistingPK || PrefC.RandomKeys)
        {
            Db.NonQ(command);
        }
        else
        {
            employee.EmployeeNum = Db.NonQ(command, true);
        } 
        return employee.EmployeeNum;
    }

    /**
    * Updates one Employee in the database.
    */
    public static void update(Employee employee) throws Exception {
        String command = "UPDATE employee SET " + "LName      = '" + POut.String(employee.LName) + "', " + "FName      = '" + POut.String(employee.FName) + "', " + "MiddleI    = '" + POut.String(employee.MiddleI) + "', " + "IsHidden   =  " + POut.Bool(employee.IsHidden) + ", " + "ClockStatus= '" + POut.String(employee.ClockStatus) + "', " + "PhoneExt   =  " + POut.Int(employee.PhoneExt) + ", " + "PayrollID  = '" + POut.String(employee.PayrollID) + "' " + "WHERE EmployeeNum = " + POut.Long(employee.EmployeeNum);
        Db.NonQ(command);
    }

    /**
    * Updates one Employee in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Employee employee, Employee oldEmployee) throws Exception {
        String command = "";
        if (employee.LName != oldEmployee.LName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "LName = '" + POut.String(employee.LName) + "'";
        }
         
        if (employee.FName != oldEmployee.FName)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "FName = '" + POut.String(employee.FName) + "'";
        }
         
        if (employee.MiddleI != oldEmployee.MiddleI)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "MiddleI = '" + POut.String(employee.MiddleI) + "'";
        }
         
        if (employee.IsHidden != oldEmployee.IsHidden)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "IsHidden = " + POut.Bool(employee.IsHidden) + "";
        }
         
        if (employee.ClockStatus != oldEmployee.ClockStatus)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "ClockStatus = '" + POut.String(employee.ClockStatus) + "'";
        }
         
        if (employee.PhoneExt != oldEmployee.PhoneExt)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PhoneExt = " + POut.Int(employee.PhoneExt) + "";
        }
         
        if (employee.PayrollID != oldEmployee.PayrollID)
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "PayrollID = '" + POut.String(employee.PayrollID) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE employee SET " + command + " WHERE EmployeeNum = " + POut.Long(employee.EmployeeNum);
        Db.NonQ(command);
    }

    /**
    * Deletes one Employee from the database.
    */
    public static void delete(long employeeNum) throws Exception {
        String command = "DELETE FROM employee " + "WHERE EmployeeNum = " + POut.Long(employeeNum);
        Db.NonQ(command);
    }

}


