//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 5:58:03 PM
//

package OpenDentBusiness.Crud;

import CS2JNet.System.StringSupport;
import OpenDentBusiness.Db;
import OpenDentBusiness.DbHelper;
import OpenDentBusiness.Employer;
import OpenDentBusiness.PIn;
import OpenDentBusiness.POut;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.RemotingClient;
import OpenDentBusiness.RemotingRole;
import OpenDentBusiness.ReplicationServers;

//This file is automatically generated.
//Do not attempt to make changes to this file because the changes will be erased and overwritten.
public class EmployerCrud   
{
    /**
    * Gets one Employer object from the database using the primary key.  Returns null if not found.
    */
    public static Employer selectOne(long employerNum) throws Exception {
        String command = "SELECT * FROM employer " + "WHERE EmployerNum = " + POut.long(employerNum);
        List<Employer> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets one Employer object from the database using a query.
    */
    public static Employer selectOne(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Employer> list = tableToList(Db.getTable(command));
        if (list.Count == 0)
        {
            return null;
        }
         
        return list[0];
    }

    /**
    * Gets a list of Employer objects from the database using a query.
    */
    public static List<Employer> selectMany(String command) throws Exception {
        if (RemotingClient.RemotingRole == RemotingRole.ClientWeb)
        {
            throw new ApplicationException("Not allowed to send sql directly.  Rewrite the calling class to not use this query:\r\n" + command);
        }
         
        List<Employer> list = tableToList(Db.getTable(command));
        return list;
    }

    /**
    * Converts a DataTable to a list of objects.
    */
    public static List<Employer> tableToList(DataTable table) throws Exception {
        List<Employer> retVal = new List<Employer>();
        Employer employer;
        for (int i = 0;i < table.Rows.Count;i++)
        {
            employer = new Employer();
            employer.EmployerNum = PIn.Long(table.Rows[i]["EmployerNum"].ToString());
            employer.EmpName = PIn.String(table.Rows[i]["EmpName"].ToString());
            employer.Address = PIn.String(table.Rows[i]["Address"].ToString());
            employer.Address2 = PIn.String(table.Rows[i]["Address2"].ToString());
            employer.City = PIn.String(table.Rows[i]["City"].ToString());
            employer.State = PIn.String(table.Rows[i]["State"].ToString());
            employer.Zip = PIn.String(table.Rows[i]["Zip"].ToString());
            employer.Phone = PIn.String(table.Rows[i]["Phone"].ToString());
            retVal.Add(employer);
        }
        return retVal;
    }

    /**
    * Inserts one Employer into the database.  Returns the new priKey.
    */
    public static long insert(Employer employer) throws Exception {
        if (OpenDentBusiness.DataConnection.DBtype == OpenDentBusiness.DatabaseType.Oracle)
        {
            employer.EmployerNum = DbHelper.getNextOracleKey("employer","EmployerNum");
            int loopcount = 0;
            while (loopcount < 100)
            {
                try
                {
                    return Insert(employer, true);
                }
                catch (Oracle.DataAccess.Client.OracleException ex)
                {
                    if (ex.Number == 1 && ex.Message.ToLower().Contains("unique constraint") && ex.Message.ToLower().Contains("violated"))
                    {
                        employer.EmployerNum++;
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
            return Insert(employer, false);
        } 
    }

    /**
    * Inserts one Employer into the database.  Provides option to use the existing priKey.
    */
    public static long insert(Employer employer, boolean useExistingPK) throws Exception {
        if (!useExistingPK && PrefC.getRandomKeys())
        {
            employer.EmployerNum = ReplicationServers.getKey("employer","EmployerNum");
        }
         
        String command = "INSERT INTO employer (";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += "EmployerNum,";
        }
         
        command += "EmpName,Address,Address2,City,State,Zip,Phone) VALUES(";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            command += POut.long(employer.EmployerNum) + ",";
        }
         
        command += "'" + POut.string(employer.EmpName) + "'," + "'" + POut.string(employer.Address) + "'," + "'" + POut.string(employer.Address2) + "'," + "'" + POut.string(employer.City) + "'," + "'" + POut.string(employer.State) + "'," + "'" + POut.string(employer.Zip) + "'," + "'" + POut.string(employer.Phone) + "')";
        if (useExistingPK || PrefC.getRandomKeys())
        {
            Db.nonQ(command);
        }
        else
        {
            employer.EmployerNum = Db.nonQ(command,true);
        } 
        return employer.EmployerNum;
    }

    /**
    * Updates one Employer in the database.
    */
    public static void update(Employer employer) throws Exception {
        String command = "UPDATE employer SET " + "EmpName    = '" + POut.string(employer.EmpName) + "', " + "Address    = '" + POut.string(employer.Address) + "', " + "Address2   = '" + POut.string(employer.Address2) + "', " + "City       = '" + POut.string(employer.City) + "', " + "State      = '" + POut.string(employer.State) + "', " + "Zip        = '" + POut.string(employer.Zip) + "', " + "Phone      = '" + POut.string(employer.Phone) + "' " + "WHERE EmployerNum = " + POut.long(employer.EmployerNum);
        Db.nonQ(command);
    }

    /**
    * Updates one Employer in the database.  Uses an old object to compare to, and only alters changed fields.  This prevents collisions and concurrency problems in heavily used tables.
    */
    public static void update(Employer employer, Employer oldEmployer) throws Exception {
        String command = "";
        if (!StringSupport.equals(employer.EmpName, oldEmployer.EmpName))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "EmpName = '" + POut.string(employer.EmpName) + "'";
        }
         
        if (!StringSupport.equals(employer.Address, oldEmployer.Address))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Address = '" + POut.string(employer.Address) + "'";
        }
         
        if (!StringSupport.equals(employer.Address2, oldEmployer.Address2))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Address2 = '" + POut.string(employer.Address2) + "'";
        }
         
        if (!StringSupport.equals(employer.City, oldEmployer.City))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "City = '" + POut.string(employer.City) + "'";
        }
         
        if (!StringSupport.equals(employer.State, oldEmployer.State))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "State = '" + POut.string(employer.State) + "'";
        }
         
        if (!StringSupport.equals(employer.Zip, oldEmployer.Zip))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Zip = '" + POut.string(employer.Zip) + "'";
        }
         
        if (!StringSupport.equals(employer.Phone, oldEmployer.Phone))
        {
            if (!StringSupport.equals(command, ""))
            {
                command += ",";
            }
             
            command += "Phone = '" + POut.string(employer.Phone) + "'";
        }
         
        if (StringSupport.equals(command, ""))
        {
            return ;
        }
         
        command = "UPDATE employer SET " + command + " WHERE EmployerNum = " + POut.long(employer.EmployerNum);
        Db.nonQ(command);
    }

    /**
    * Deletes one Employer from the database.
    */
    public static void delete(long employerNum) throws Exception {
        String command = "DELETE FROM employer " + "WHERE EmployerNum = " + POut.long(employerNum);
        Db.nonQ(command);
    }

}


